package org.yeastrc.limelight.xml.magnum.reader;

import static java.lang.Math.toIntExact;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.yeastrc.limelight.xml.magnum.constants.MagnumConstants;
import org.yeastrc.limelight.xml.magnum.objects.MagnumPSM;
import org.yeastrc.limelight.xml.magnum.objects.MagnumParameters;
import org.yeastrc.limelight.xml.magnum.objects.MagnumResults;
import org.yeastrc.limelight.xml.magnum.objects.OpenModification;
import org.yeastrc.limelight.xml.magnum.utils.ModParsingUtils;

import net.systemsbiology.regis_web.pepxml.ModInfoDataType;
import net.systemsbiology.regis_web.pepxml.ModInfoDataType.ModAminoacidMass;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis.MsmsRunSummary;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis.MsmsRunSummary.SearchSummary;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis.MsmsRunSummary.SpectrumQuery;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis.MsmsRunSummary.SpectrumQuery.SearchResult;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis.MsmsRunSummary.SpectrumQuery.SearchResult.SearchHit;
import net.systemsbiology.regis_web.pepxml.NameValueType;

public class MagnumPEPXMLResultsReader {

	/**
	 * Get the parsed magnum results for the given magnum txt data file
	 * @param pepXMLFile
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	public static MagnumResults getMagnumResults( File pepXMLFile, MagnumParameters params ) throws Throwable {

		Map<String,Map<Integer,Collection<MagnumPSM>>> resultMap = new HashMap<>();
				
		MsmsPipelineAnalysis msAnalysis = null;
		try {
			msAnalysis = getMSmsPipelineAnalysis( pepXMLFile );
		} catch( Throwable t ) {
			System.err.println( "Got an error parsing the pep XML file. Error: " + t.getMessage() );
			throw t;
		}
		
		
		MagnumResults results = new MagnumResults();
		results.setMagnumResultMap( resultMap );
		results.setMagnumVersion( getMagnumVersionFromXML( msAnalysis ) );
		
		for( MsmsRunSummary runSummary : msAnalysis.getMsmsRunSummary() ) {
			for( SpectrumQuery spectrumQuery : runSummary.getSpectrumQuery() ) {
				
				int charge = getChargeFromSpectrumQuery( spectrumQuery );
				int scanNumber = getScanNumberFromSpectrumQuery( spectrumQuery );
				double obsMass = getObservedMassFromSpectrumQuery( spectrumQuery );
				double retentionTime = getRetentionTimeFromSpectrumQuery( spectrumQuery );
				
				for( SearchResult searchResult : spectrumQuery.getSearchResult() ) {
					for( SearchHit searchHit : searchResult.getSearchHit() ) {

						MagnumPSM psm = null;
						
						try {
							psm = getPsmFromSearchHit( searchHit, charge, scanNumber, obsMass, retentionTime );
							
						} catch( Throwable t) {
							
							System.err.println( "Error reading PSM from pepXML. Error: " + t.getMessage() );
							throw t;
							
						}

						String psmReportedPeptide = ModParsingUtils.getRoundedReportedPeptideString( psm.getPeptideSequence(), psm.getModifications() );

						if( !results.getMagnumResultMap().containsKey( psmReportedPeptide ) )
							results.getMagnumResultMap().put( psmReportedPeptide, new HashMap<>() );
						
						if( !results.getMagnumResultMap().get( psmReportedPeptide ).containsKey( scanNumber ) )
							results.getMagnumResultMap().get( psmReportedPeptide ).put( scanNumber, new HashSet<>() );
						
						results.getMagnumResultMap().get( psmReportedPeptide ).get( scanNumber ).add( psm );						
					}
				}
			}
		}
		
		return results;
	}
	
	private static String getMagnumVersionFromXML( MsmsPipelineAnalysis msAnalysis ) {
		
		for( MsmsRunSummary runSummary : msAnalysis.getMsmsRunSummary() ) {
			for( SearchSummary searchSummary : runSummary.getSearchSummary() ) {
				
				if( searchSummary.getSearchEngine().value().equals( "Magnum" ) ) {
					return searchSummary.getSearchEngineVersion();
				}
			
			}
		}
		
		return "Unknown";
	}

	private static double getRetentionTimeFromSpectrumQuery( SpectrumQuery spectrumQuery ) {
		return spectrumQuery.getRetentionTimeSec().doubleValue();
	}
	
	private static double getObservedMassFromSpectrumQuery( SpectrumQuery spectrumQuery ) {
		return spectrumQuery.getPrecursorNeutralMass().doubleValue();
	}
	
	private static int getScanNumberFromSpectrumQuery( SpectrumQuery spectrumQuery ) {
		return toIntExact( spectrumQuery.getStartScan() );
	}
	
	private static int getChargeFromSpectrumQuery( SpectrumQuery spectrumQuery ) {
		return spectrumQuery.getAssumedCharge().intValue();
	}
	
	private static MagnumPSM getPsmFromSearchHit(
			SearchHit searchHit,
			int charge,
			int scanNumber,
			double obsMass,
			double retentionTime ) throws Throwable {
		
		MagnumPSM psm = new MagnumPSM();
		
		psm.setCharge( charge );
		psm.setScanNumber( scanNumber );
		psm.setObservedMass( obsMass );
		psm.setRetentionTime( retentionTime );
		psm.setMassDiff(searchHit.getMassdiff());

		psm.setPeptideSequence( searchHit.getPeptide() );
		
		psm.setmScore( getScoreForType( searchHit, MagnumConstants.PSM_SCORE_MAGNUM_SCORE ) );
		psm.setdScore( getScoreForType( searchHit, MagnumConstants.PSM_SCORE_DELTA_SCORE ) );
		psm.setPpmError( getScoreForType( searchHit, MagnumConstants.PSM_SCORE_PPM_ERROR ) );
		psm.seteValue( getScoreForType( searchHit, MagnumConstants.PSM_SCORE_E_VALUE ) );

		psm.setReporterIons( getReporterIonsForSearchHit( searchHit ) );
		psm.setOpenModification(getOpenModificationForSearchHit(searchHit));

		try {
			psm.setModifications( getModificationsForSearchHit( searchHit ) );
		} catch( Throwable t ) {
			
			System.err.println( "Error getting mods for PSM. Error was: " + t.getMessage() );
			throw t;
		}
		
		return psm;
	}
	
	/**
	 * Get the mods reported for a searchHit
	 * 
	 * Example syntax in pep XML:
	 * 
	 *       <mod_aminoacid_mass position="1" mass="147.035379" variable="15.994900" source="param"/>
	 *       <mod_aminoacid_mass position="3" mass="160.030640" static="57.021460" source="param"/>
	 *       <mod_aminoacid_mass position="7" mass="162.594409" variable="75.562385" source="adduct"/>
	 * @param searchHit
	 * @return
	 * @throws Throwable
	 */
	private static Map<Integer, BigDecimal> getModificationsForSearchHit( SearchHit searchHit ) throws Throwable {
		
		Map<Integer, BigDecimal> modMap = new HashMap<>();
		
		ModInfoDataType mofo = searchHit.getModificationInfo();
		if( mofo != null ) {
			for( ModAminoacidMass mod : mofo.getModAminoacidMass() ) {
				
				// skip static mods
				if( mod.getStatic() != null ) {
					continue;
				}

				// skip open mods
				if(isOpenMod(mod)) {
					continue;
				}

				BigDecimal modMass = BigDecimal.valueOf( mod.getVariable() );
								
				modMap.put( mod.getPosition().intValueExact(), modMass );
			}
		}
		
		return modMap;
	}

	/**
	 * Get open modification for search hit, if any
	 *
	 * @param searchHit
	 * @return The OpenModification, null if none was found
	 */
	private static OpenModification getOpenModificationForSearchHit( SearchHit searchHit ) {

		ModInfoDataType mofo = searchHit.getModificationInfo();
		if( mofo != null ) {
			for( ModAminoacidMass mod : mofo.getModAminoacidMass() ) {

				if(isOpenMod(mod)) {
					BigDecimal modMass = BigDecimal.valueOf( mod.getVariable() );
					int position = mod.getPosition().intValueExact();

					Collection<Integer> positions = new HashSet<>();
					positions.add(position);

					return new OpenModification(modMass, positions);
				}
			}
		}

		// if we get here, just return the mass diff as an unlocalized open mod
		BigDecimal massDiff = searchHit.getMassdiff();
		return new OpenModification(massDiff, null);

	}


	/**
	 * Return true if the mod is an open mod
	 *
	 * @param xmlModAminoAcidMass
	 * @return
	 */
	private static boolean isOpenMod(ModAminoacidMass xmlModAminoAcidMass) {
		return xmlModAminoAcidMass.getSource().equals("adduct");
	}

	private static Collection<BigDecimal> getReporterIonsForSearchHit( SearchHit searchHit ) throws Throwable {

		Collection<BigDecimal> reporterIons = new HashSet<>();

		for( NameValueType searchScore : searchHit.getSearchScore() ) {
			if( searchScore.getName().equals( MagnumConstants.PSM_SCORE_REPORTER_ION ) ) {

				String reportedReporterIon = searchScore.getValueAttribute();
				reporterIons.add( new BigDecimal( reportedReporterIon ) );

			}
		}

		return reporterIons;
	}

	private static Double getScoreForType( SearchHit searchHit, String type ) throws Throwable {
				
		for( NameValueType searchScore : searchHit.getSearchScore() ) {
			if( searchScore.getName().equals( type ) ) {
				
				return Double.valueOf( searchScore.getValueAttribute() );
			}
		}
		
		throw new Exception( "Could not find a score of name: " + type + " for PSM..." );		
	}
	
	
	private static MsmsPipelineAnalysis getMSmsPipelineAnalysis( File file ) throws Throwable {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(MsmsPipelineAnalysis.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		MsmsPipelineAnalysis msAnalysis = (MsmsPipelineAnalysis)jaxbUnmarshaller.unmarshal( file );
		
		return msAnalysis;
	}
	
}

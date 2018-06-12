package org.yeastrc.limelight.xml.magnum.reader;

import static java.lang.Math.toIntExact;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
	 * 
	 * @param magnumFile
	 * @return
	 * @throws Throwable
	 */
	public static MagnumResults getMagnumResults( File pepXMLFile, MagnumParameters params ) throws Throwable {

		Map<Integer,Collection<MagnumPSM>> resultMap = new HashMap<>();
		
		System.err.println( params );
		
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
						
						if( !resultMap.containsKey( scanNumber ) )
							resultMap.put( scanNumber, new HashSet<>() );
						
						resultMap.get( scanNumber ).add( psm );
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
		
		psm.setPeptideSequence( searchHit.getPeptide() );
		
		psm.setScore( getScoreForType( searchHit, MagnumConstants.PSM_SCORE_MAGNUM_SCORE ) );
		psm.setdScore( getScoreForType( searchHit, MagnumConstants.PSM_SCORE_DELTA_SCORE ) );
		psm.setPpmError( getScoreForType( searchHit, MagnumConstants.PSM_SCORE_PPM_ERROR ) );
		psm.seteValue( getScoreForType( searchHit, MagnumConstants.PSM_SCORE_E_VALUE ) );
		
		try {
			psm.setModifications( getModificationsForSearchHit( searchHit ) );
		} catch( Throwable t ) {
			
			System.err.println( "Error getting mods for PSM. Error was: " + t.getMessage() );
			throw t;
		}
		
		return psm;
	}
	
	private static Map<Integer, Double> getModificationsForSearchHit( SearchHit searchHit ) throws Throwable {
		
		String peptideSequence = searchHit.getPeptide();
		Map<Integer, Double> modMap = new HashMap<>();
		
		ModInfoDataType mofo = searchHit.getModificationInfo();
		if( mofo != null ) {
			for( ModAminoacidMass mod : mofo.getModAminoacidMass() ) {
				
				String aminoAcid = peptideSequence.substring( mod.getPosition().intValue() - 1, mod.getPosition().intValue() );
				Double modMass = mod.getMass() - MagnumConstants.AA_MASS.get( aminoAcid );
				
				modMap.put( mod.getPosition().intValueExact(), modMass );
			}
		}
		
		return modMap;
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

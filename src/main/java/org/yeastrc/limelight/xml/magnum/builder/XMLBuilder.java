package org.yeastrc.limelight.xml.magnum.builder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collection;

import org.yeastrc.limelight.limelight_import.api.xml_dto.*;
import org.yeastrc.limelight.limelight_import.api.xml_dto.ReportedPeptide.ReportedPeptideAnnotations;
import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchProgram.PsmAnnotationTypes;
import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchProgram.ReportedPeptideAnnotationTypes;
import org.yeastrc.limelight.limelight_import.create_import_file_from_java_objects.main.CreateImportFileFromJavaObjectsMain;
import org.yeastrc.limelight.xml.magnum.annotation.PSMAnnotationTypeSortOrder;
import org.yeastrc.limelight.xml.magnum.annotation.PSMAnnotationTypes;
import org.yeastrc.limelight.xml.magnum.annotation.PSMDefaultVisibleAnnotationTypes;
import org.yeastrc.limelight.xml.magnum.annotation.PeptideAnnotationTypeSortOrder;
import org.yeastrc.limelight.xml.magnum.annotation.PeptideAnnotationTypes;
import org.yeastrc.limelight.xml.magnum.annotation.PeptideDefaultVisibleAnnotationTypes;
import org.yeastrc.limelight.xml.magnum.constants.Constants;
import org.yeastrc.limelight.xml.magnum.objects.ConversionParameters;
import org.yeastrc.limelight.xml.magnum.objects.MagnumPSM;
import org.yeastrc.limelight.xml.magnum.objects.MagnumParameters;
import org.yeastrc.limelight.xml.magnum.objects.MagnumResults;
import org.yeastrc.limelight.xml.magnum.objects.ParsedPeptide;
import org.yeastrc.limelight.xml.magnum.objects.PercolatorPSM;
import org.yeastrc.limelight.xml.magnum.objects.PercolatorPeptideResult;
import org.yeastrc.limelight.xml.magnum.objects.PercolatorPeptideStats;
import org.yeastrc.limelight.xml.magnum.objects.PercolatorResults;
import org.yeastrc.limelight.xml.magnum.utils.MassUtils;
import org.yeastrc.limelight.xml.magnum.utils.ReportedPeptideParsingUtils;


public class XMLBuilder {

	public void buildAndSaveXML( ConversionParameters conversionParameters,
			                     MagnumResults magnumResults,
			                     MagnumParameters magnumParameters,
			                     PercolatorResults percolatorResults )
    throws Exception {

		LimelightInput limelightInputRoot = new LimelightInput();

		limelightInputRoot.setFastaFilename( conversionParameters.getFastaFile().getName() );
		
		// add in the conversion program (this program) information
		ConversionProgramBuilder.createInstance().buildConversionProgramSection( limelightInputRoot, conversionParameters);
		
		SearchProgramInfo searchProgramInfo = new SearchProgramInfo();
		limelightInputRoot.setSearchProgramInfo( searchProgramInfo );
		
		SearchPrograms searchPrograms = new SearchPrograms();
		searchProgramInfo.setSearchPrograms( searchPrograms );
		
		{
			SearchProgram searchProgram = new SearchProgram();
			searchPrograms.getSearchProgram().add( searchProgram );
				
			searchProgram.setName( Constants.PROGRAM_NAME_MAGNUM );
			searchProgram.setDisplayName( Constants.PROGRAM_NAME_MAGNUM );
			searchProgram.setVersion( magnumResults.getMagnumVersion() );
			
			
			//
			// Define the annotation types present in magnum data
			//
			PsmAnnotationTypes psmAnnotationTypes = new PsmAnnotationTypes();
			searchProgram.setPsmAnnotationTypes( psmAnnotationTypes );
			
			FilterablePsmAnnotationTypes filterablePsmAnnotationTypes = new FilterablePsmAnnotationTypes();
			psmAnnotationTypes.setFilterablePsmAnnotationTypes( filterablePsmAnnotationTypes );
			
			for( FilterablePsmAnnotationType annoType : PSMAnnotationTypes.getFilterablePsmAnnotationTypes( Constants.PROGRAM_NAME_MAGNUM, conversionParameters ) ) {
				filterablePsmAnnotationTypes.getFilterablePsmAnnotationType().add( annoType );
			}
		}
		
		{
			SearchProgram searchProgram = new SearchProgram();
			searchPrograms.getSearchProgram().add( searchProgram );
				
			searchProgram.setName( Constants.PROGRAM_NAME_PERCOLATOR );
			searchProgram.setDisplayName( Constants.PROGRAM_NAME_PERCOLATOR );
			searchProgram.setVersion( percolatorResults.getPercolatorVersion() );
			
			
			//
			// Define the annotation types present in percolator data
			//
			PsmAnnotationTypes psmAnnotationTypes = new PsmAnnotationTypes();
			searchProgram.setPsmAnnotationTypes( psmAnnotationTypes );
			
			FilterablePsmAnnotationTypes filterablePsmAnnotationTypes = new FilterablePsmAnnotationTypes();
			psmAnnotationTypes.setFilterablePsmAnnotationTypes( filterablePsmAnnotationTypes );
			
			for( FilterablePsmAnnotationType annoType : PSMAnnotationTypes.getFilterablePsmAnnotationTypes( Constants.PROGRAM_NAME_PERCOLATOR, conversionParameters ) ) {
				filterablePsmAnnotationTypes.getFilterablePsmAnnotationType().add( annoType );
			}
			
			if(!conversionParameters.getOpenModsSeparate()) {
				ReportedPeptideAnnotationTypes reportedPeptideAnnotationTypes = new ReportedPeptideAnnotationTypes();
				searchProgram.setReportedPeptideAnnotationTypes(reportedPeptideAnnotationTypes);

				FilterableReportedPeptideAnnotationTypes filterableReportedPeptideAnnotationTypes = new FilterableReportedPeptideAnnotationTypes();
				reportedPeptideAnnotationTypes.setFilterableReportedPeptideAnnotationTypes(filterableReportedPeptideAnnotationTypes);

				for (FilterableReportedPeptideAnnotationType annoType : PeptideAnnotationTypes.getFilterablePeptideAnnotationTypes(Constants.PROGRAM_NAME_PERCOLATOR)) {
					filterableReportedPeptideAnnotationTypes.getFilterableReportedPeptideAnnotationType().add(annoType);
				}
			}
		}
		
		
		//
		// Define which annotation types are visible by default
		//
		DefaultVisibleAnnotations xmlDefaultVisibleAnnotations = new DefaultVisibleAnnotations();
		searchProgramInfo.setDefaultVisibleAnnotations( xmlDefaultVisibleAnnotations );
		
		VisiblePsmAnnotations xmlVisiblePsmAnnotations = new VisiblePsmAnnotations();
		xmlDefaultVisibleAnnotations.setVisiblePsmAnnotations( xmlVisiblePsmAnnotations );

		for( SearchAnnotation sa : PSMDefaultVisibleAnnotationTypes.getDefaultVisibleAnnotationTypes() ) {
			xmlVisiblePsmAnnotations.getSearchAnnotation().add( sa );
		}

		if(!conversionParameters.getOpenModsSeparate()) {
			VisibleReportedPeptideAnnotations xmlVisibleReportedPeptideAnnotations = new VisibleReportedPeptideAnnotations();
			xmlDefaultVisibleAnnotations.setVisibleReportedPeptideAnnotations(xmlVisibleReportedPeptideAnnotations);

			for (SearchAnnotation sa : PeptideDefaultVisibleAnnotationTypes.getDefaultVisibleAnnotationTypes()) {
				xmlVisibleReportedPeptideAnnotations.getSearchAnnotation().add(sa);
			}
		}
		
		//
		// Define the default display order in proxl
		//
		AnnotationSortOrder xmlAnnotationSortOrder = new AnnotationSortOrder();
		searchProgramInfo.setAnnotationSortOrder( xmlAnnotationSortOrder );
		
		PsmAnnotationSortOrder xmlPsmAnnotationSortOrder = new PsmAnnotationSortOrder();
		xmlAnnotationSortOrder.setPsmAnnotationSortOrder( xmlPsmAnnotationSortOrder );
		
		for( SearchAnnotation xmlSearchAnnotation : PSMAnnotationTypeSortOrder.getPSMAnnotationTypeSortOrder() ) {
			xmlPsmAnnotationSortOrder.getSearchAnnotation().add( xmlSearchAnnotation );
		}

		if(!conversionParameters.getOpenModsSeparate()) {
			ReportedPeptideAnnotationSortOrder xmlReportedPeptideAnnotationSortOrder = new ReportedPeptideAnnotationSortOrder();
			xmlAnnotationSortOrder.setReportedPeptideAnnotationSortOrder(xmlReportedPeptideAnnotationSortOrder);

			for (SearchAnnotation xmlSearchAnnotation : PeptideAnnotationTypeSortOrder.getPeptideAnnotationTypeSortOrder()) {
				xmlReportedPeptideAnnotationSortOrder.getSearchAnnotation().add(xmlSearchAnnotation);
			}
		}

		
		//
		// Define the static mods
		//
		if( magnumParameters.getStaticMods() != null && magnumParameters.getStaticMods().keySet().size() > 0 ) {
			StaticModifications smods = new StaticModifications();
			limelightInputRoot.setStaticModifications( smods );
			
			
			for( char residue : magnumParameters.getStaticMods().keySet() ) {
				
				StaticModification xmlSmod = new StaticModification();
				xmlSmod.setAminoAcid( String.valueOf( residue ) );
				xmlSmod.setMassChange( BigDecimal.valueOf( magnumParameters.getStaticMods().get( residue ) ) );
				
				smods.getStaticModification().add( xmlSmod );
			}
		}
		
		
		//
		// Define the peptide and PSM data
		//
		ReportedPeptides reportedPeptides = new ReportedPeptides();
		limelightInputRoot.setReportedPeptides( reportedPeptides );
		
		// iterate over each distinct reported peptide
		for( String percolatorReportedPeptide : percolatorResults.getPeptideResults().keySet() ) {
			
			PercolatorPeptideResult peptideResult = percolatorResults.getPeptideResults().get( percolatorReportedPeptide );
			
			PercolatorPeptideStats peptideStats = peptideResult.getPercolatorPeptideStats();
			
			ParsedPeptide parsedPeptide = ReportedPeptideParsingUtils.parsePeptide( percolatorReportedPeptide );
			
			ReportedPeptide xmlReportedPeptide = new ReportedPeptide();
			reportedPeptides.getReportedPeptide().add( xmlReportedPeptide );
			
			xmlReportedPeptide.setReportedPeptideString( percolatorReportedPeptide );
			xmlReportedPeptide.setSequence( parsedPeptide.getNakedSequence() );

			if(!conversionParameters.getOpenModsSeparate()) {

				// add in the filterable peptide annotations (e.g., q-value)
				ReportedPeptideAnnotations xmlReportedPeptideAnnotations = new ReportedPeptideAnnotations();
				xmlReportedPeptide.setReportedPeptideAnnotations(xmlReportedPeptideAnnotations);

				FilterableReportedPeptideAnnotations xmlFilterableReportedPeptideAnnotations = new FilterableReportedPeptideAnnotations();
				xmlReportedPeptideAnnotations.setFilterableReportedPeptideAnnotations(xmlFilterableReportedPeptideAnnotations);

				// handle q-value
				{
					FilterableReportedPeptideAnnotation xmlFilterableReportedPeptideAnnotation = new FilterableReportedPeptideAnnotation();
					xmlFilterableReportedPeptideAnnotations.getFilterableReportedPeptideAnnotation().add(xmlFilterableReportedPeptideAnnotation);

					xmlFilterableReportedPeptideAnnotation.setAnnotationName(PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_QVALUE);
					xmlFilterableReportedPeptideAnnotation.setSearchProgram(Constants.PROGRAM_NAME_PERCOLATOR);
					xmlFilterableReportedPeptideAnnotation.setValue(BigDecimal.valueOf(peptideStats.getqValue()));
				}
				// handle p-value
				{
					FilterableReportedPeptideAnnotation xmlFilterableReportedPeptideAnnotation = new FilterableReportedPeptideAnnotation();
					xmlFilterableReportedPeptideAnnotations.getFilterableReportedPeptideAnnotation().add(xmlFilterableReportedPeptideAnnotation);

					xmlFilterableReportedPeptideAnnotation.setAnnotationName(PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_PVALUE);
					xmlFilterableReportedPeptideAnnotation.setSearchProgram(Constants.PROGRAM_NAME_PERCOLATOR);
					xmlFilterableReportedPeptideAnnotation.setValue(BigDecimal.valueOf(peptideStats.getpValue()));
				}
				// handle pep
				{
					FilterableReportedPeptideAnnotation xmlFilterableReportedPeptideAnnotation = new FilterableReportedPeptideAnnotation();
					xmlFilterableReportedPeptideAnnotations.getFilterableReportedPeptideAnnotation().add(xmlFilterableReportedPeptideAnnotation);

					xmlFilterableReportedPeptideAnnotation.setAnnotationName(PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_PEP);
					xmlFilterableReportedPeptideAnnotation.setSearchProgram(Constants.PROGRAM_NAME_PERCOLATOR);
					xmlFilterableReportedPeptideAnnotation.setValue(BigDecimal.valueOf(peptideStats.getPep()));
				}
				// handle svm score
				{
					FilterableReportedPeptideAnnotation xmlFilterableReportedPeptideAnnotation = new FilterableReportedPeptideAnnotation();
					xmlFilterableReportedPeptideAnnotations.getFilterableReportedPeptideAnnotation().add(xmlFilterableReportedPeptideAnnotation);

					xmlFilterableReportedPeptideAnnotation.setAnnotationName(PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_SVMSCORE);
					xmlFilterableReportedPeptideAnnotation.setSearchProgram(Constants.PROGRAM_NAME_PERCOLATOR);
					xmlFilterableReportedPeptideAnnotation.setValue(BigDecimal.valueOf(peptideStats.getSvmScore()));
				}
			}
			

			// add in the mods for this peptide
			if( parsedPeptide.getModMap() != null && parsedPeptide.getModMap().keySet().size() > 0 ) {
					
				PeptideModifications xmlModifications = new PeptideModifications();
				xmlReportedPeptide.setPeptideModifications( xmlModifications );
					
				for( int position : parsedPeptide.getModMap().keySet() ) {
					PeptideModification xmlModification = new PeptideModification();
					xmlModifications.getPeptideModification().add( xmlModification );
							
					xmlModification.setMass( BigDecimal.valueOf( parsedPeptide.getModMap().get( position ) ).stripTrailingZeros().setScale( 0 ) );
					xmlModification.setPosition( new BigInteger( String.valueOf( position ) ) );
				}
			}

			
			// add in the PSMs and annotations
			Psms xmlPsms = new Psms();
			xmlReportedPeptide.setPsms( xmlPsms );
			
			// iterate over all PSMs for this reported peptide
			for( int scanNumber : peptideResult.getPsmsIndexedByScanNumber().keySet() ) {
				
				Collection<MagnumPSM> magnumPSMs = magnumResults.getMagnumResultMap().get( percolatorReportedPeptide ).get( scanNumber );

				/*
				 * PSMs listed by percolator cannot be reliably matched to specific PSMs listed by Magnum.
				 * 
				 * This is because Magnum may list multiple PSMs for the same scan that have very slightly different mod
				 * masses (one from open mod, one from variable mod) on the same peptide position. When converted to ints
				 * for obtaining peptide-level scoring in percolator, both PSMs are listed as PSMs for that aggregate peptide,
				 * and they are listed by scan number.
				 * 
				 * Since scan number is ambiguous (multiple PSMs for same scan number), these cannot be mapped reliably on
				 * a specific PSM in the magnum data. So, in an effort to not toss data out, we are allowing more than one
				 * percolator PSM for a given scan number for a given peptide match--and we are allowing more than one
				 * magnum PSM for a given scan number for the same peptide match. Since we can't tell exactly which goes with
				 * which, we will have a PSM entry for all combinations of percolator and magnum PSMs for this peptide and scan
				 * number.
				 * 
				 * So, if there are two magnum PSMs for this scan number for this percolator reported peptide, this will
				 * result in 4 PSMs in the limelight XML file (2 percolator PSMs * 2 magnum PSMs). Ideally Magnum would
				 * not list multiple PSMs for the same scan number for the same peptide match--and if that is changed
				 * in the future, this code will not need changing.
				 * 
				 */
				
				
				for( PercolatorPSM percolatorPSM : peptideResult.getPsmsIndexedByScanNumber().get( scanNumber ) ) {
					
					for( MagnumPSM magnumPSM : magnumPSMs ) {
					
						Psm xmlPsm = new Psm();
						xmlPsms.getPsm().add( xmlPsm );
						
						xmlPsm.setScanNumber( new BigInteger( String.valueOf( scanNumber ) ) );
						xmlPsm.setPrecursorCharge( new BigInteger( String.valueOf( magnumPSM.getCharge() ) ) );
						xmlPsm.setPrecursorMZ(MassUtils.getObservedMoverZForPsm(magnumPSM));
						xmlPsm.setPrecursorRetentionTime(BigDecimal.valueOf(magnumPSM.getRetentionTime()));

						// add in the filterable PSM annotations (e.g., score)
						FilterablePsmAnnotations xmlFilterablePsmAnnotations = new FilterablePsmAnnotations();
						xmlPsm.setFilterablePsmAnnotations( xmlFilterablePsmAnnotations );
						
						// handle magnum scores
						{
							FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
							xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
							
							xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.MAGNUM_ANNOTATION_TYPE_EVALUE );
							xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_MAGNUM );
							xmlFilterablePsmAnnotation.setValue( BigDecimal.valueOf( magnumPSM.geteValue()) );
						}
						{
							FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
							xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
							
							xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.MAGNUM_ANNOTATION_TYPE_MSCORE );
							xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_MAGNUM );
							xmlFilterablePsmAnnotation.setValue( BigDecimal.valueOf( magnumPSM.getmScore()) );
						}
						{
							FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
							xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
							
							xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.MAGNUM_ANNOTATION_TYPE_DSCORE );
							xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_MAGNUM );
							xmlFilterablePsmAnnotation.setValue( BigDecimal.valueOf( magnumPSM.getdScore() ) );
						}
						{
							FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
							xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
							
							xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.MAGNUM_ANNOTATION_TYPE_PPMERROR );
							xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_MAGNUM );
							xmlFilterablePsmAnnotation.setValue( BigDecimal.valueOf( magnumPSM.getPpmError()) );
						}
						{
							FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
							xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );

							xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.MAGNUM_ANNOTATION_TYPE_MASSDIFF );
							xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_MAGNUM );
							xmlFilterablePsmAnnotation.setValue( magnumPSM.getMassDiff() );
						}
						
						
						// handle percolator scores
						{
							FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
							xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
							
							xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_PEP );
							xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
							xmlFilterablePsmAnnotation.setValue( BigDecimal.valueOf( percolatorPSM.getPep() ) );
						}
						{
							FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
							xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
							
							xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_PVALUE );
							xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
							xmlFilterablePsmAnnotation.setValue( BigDecimal.valueOf( percolatorPSM.getpValue() ) );
						}
						{
							FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
							xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
							
							xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_QVALUE );
							xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
							xmlFilterablePsmAnnotation.setValue( BigDecimal.valueOf( percolatorPSM.getqValue() ) );
						}
						{
							FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
							xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
							
							xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_SVMSCORE );
							xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
							xmlFilterablePsmAnnotation.setValue( BigDecimal.valueOf( percolatorPSM.getSvmScore() ) );
						}
						
						
						// add in open mod
						if( (!(conversionParameters.getOpenModsSeparate()) || percolatorPSM.isOpenModResult()) && magnumPSM.getOpenModification() != null ) {

							PsmOpenModification xmlPsmOpenModifcation = new PsmOpenModification();
							xmlPsm.setPsmOpenModification( xmlPsmOpenModifcation );
							xmlPsmOpenModifcation.setMass(magnumPSM.getOpenModification().getMass());

							if( magnumPSM.getOpenModification().getPositions() != null) {
								for (int position : magnumPSM.getOpenModification().getPositions()) {
									PsmOpenModificationPosition xmlPsmOpenModifcationPosition = new PsmOpenModificationPosition();
									xmlPsmOpenModifcation.getPsmOpenModificationPosition().add(xmlPsmOpenModifcationPosition);
									xmlPsmOpenModifcationPosition.setPosition(BigInteger.valueOf(position));
								}
							}
						}

						// add in any reporter ions for this psm
						if( magnumPSM.getReporterIons() != null && magnumPSM.getReporterIons().size() > 0 ) {

							ReporterIons xReporterIons = new ReporterIons();
							xmlPsm.setReporterIons( xReporterIons );

							for( BigDecimal ri : magnumPSM.getReporterIons() ) {
								ReporterIon xReporterIon = new ReporterIon();
								xReporterIons.getReporterIon().add( xReporterIon );

								xReporterIon.setMass( ri );
							}
						}

					}
				}
				
			}//end iterating over all PSMs for a reported peptide
			
			
		}// end iterating over distinct reported peptides

		
		
		// add in the matched proteins section
		MatchedProteinsBuilder.getInstance().buildMatchedProteins(
				                                                   limelightInputRoot,
				                                                   conversionParameters.getFastaFile(),
				                                                   percolatorResults.getPeptideResults().keySet(),
				                                                   magnumParameters.getDecoyPrefix()
				                                                  );
		
		
		// add in the config file(s)
		ConfigurationFiles xmlConfigurationFiles = new ConfigurationFiles();
		limelightInputRoot.setConfigurationFiles( xmlConfigurationFiles );
		
		ConfigurationFile xmlConfigurationFile = new ConfigurationFile();
		xmlConfigurationFiles.getConfigurationFile().add( xmlConfigurationFile );
		
		xmlConfigurationFile.setSearchProgram( Constants.PROGRAM_NAME_MAGNUM );
		xmlConfigurationFile.setFileName( conversionParameters.getMagnumParametersFile().getName() );
		xmlConfigurationFile.setFileContent( Files.readAllBytes( FileSystems.getDefault().getPath( conversionParameters.getMagnumParametersFile().getAbsolutePath() ) ) );
		
		
		//make the xml file
		CreateImportFileFromJavaObjectsMain.getInstance().createImportFileFromJavaObjectsMain( conversionParameters.getLimelightXMLOutputFile(), limelightInputRoot);
		
	}
	
	
}

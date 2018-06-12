package org.yeastrc.limelight.xml.magnum.builder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.yeastrc.limelight.limelight_import.api.xml_dto.AnnotationSortOrder;
import org.yeastrc.limelight.limelight_import.api.xml_dto.ConfigurationFile;
import org.yeastrc.limelight.limelight_import.api.xml_dto.ConfigurationFiles;
import org.yeastrc.limelight.limelight_import.api.xml_dto.DefaultVisibleAnnotations;
import org.yeastrc.limelight.limelight_import.api.xml_dto.LimelightInput;
import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterablePsmAnnotation;
import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterablePsmAnnotationType;
import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterablePsmAnnotationTypes;
import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterablePsmAnnotations;
import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterableReportedPeptideAnnotation;
import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterableReportedPeptideAnnotationType;
import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterableReportedPeptideAnnotationTypes;
import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterableReportedPeptideAnnotations;
import org.yeastrc.limelight.limelight_import.api.xml_dto.PeptideModification;
import org.yeastrc.limelight.limelight_import.api.xml_dto.PeptideModifications;
import org.yeastrc.limelight.limelight_import.api.xml_dto.Psm;
import org.yeastrc.limelight.limelight_import.api.xml_dto.PsmAnnotationSortOrder;
import org.yeastrc.limelight.limelight_import.api.xml_dto.PsmModification;
import org.yeastrc.limelight.limelight_import.api.xml_dto.PsmModifications;
import org.yeastrc.limelight.limelight_import.api.xml_dto.Psms;
import org.yeastrc.limelight.limelight_import.api.xml_dto.ReportedPeptide;
import org.yeastrc.limelight.limelight_import.api.xml_dto.ReportedPeptide.ReportedPeptideAnnotations;
import org.yeastrc.limelight.limelight_import.api.xml_dto.ReportedPeptideAnnotationSortOrder;
import org.yeastrc.limelight.limelight_import.api.xml_dto.ReportedPeptides;
import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchAnnotation;
import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchProgram;
import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchProgram.PsmAnnotationTypes;
import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchProgram.ReportedPeptideAnnotationTypes;
import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchProgramInfo;
import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchPrograms;
import org.yeastrc.limelight.limelight_import.api.xml_dto.StaticModification;
import org.yeastrc.limelight.limelight_import.api.xml_dto.StaticModifications;
import org.yeastrc.limelight.limelight_import.api.xml_dto.VisiblePsmAnnotations;
import org.yeastrc.limelight.limelight_import.api.xml_dto.VisibleReportedPeptideAnnotations;
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
import org.yeastrc.limelight.xml.magnum.objects.PercolatorPeptide;
import org.yeastrc.limelight.xml.magnum.objects.PercolatorResults;
import org.yeastrc.limelight.xml.magnum.utils.ModParsingUtils;
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
			
			for( FilterablePsmAnnotationType annoType : PSMAnnotationTypes.getFilterablePsmAnnotationTypes( Constants.PROGRAM_NAME_MAGNUM ) ) {
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
			
			for( FilterablePsmAnnotationType annoType : PSMAnnotationTypes.getFilterablePsmAnnotationTypes( Constants.PROGRAM_NAME_PERCOLATOR ) ) {
				filterablePsmAnnotationTypes.getFilterablePsmAnnotationType().add( annoType );
			}
			
			
			ReportedPeptideAnnotationTypes reportedPeptideAnnotationTypes = new ReportedPeptideAnnotationTypes();
			searchProgram.setReportedPeptideAnnotationTypes( reportedPeptideAnnotationTypes );

			FilterableReportedPeptideAnnotationTypes filterableReportedPeptideAnnotationTypes = new FilterableReportedPeptideAnnotationTypes();
			reportedPeptideAnnotationTypes.setFilterableReportedPeptideAnnotationTypes( filterableReportedPeptideAnnotationTypes );
			
			for( FilterableReportedPeptideAnnotationType annoType : PeptideAnnotationTypes.getFilterablePeptideAnnotationTypes( Constants.PROGRAM_NAME_PERCOLATOR ) ) {
				filterableReportedPeptideAnnotationTypes.getFilterableReportedPeptideAnnotationType().add( annoType );
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

		VisibleReportedPeptideAnnotations xmlVisibleReportedPeptideAnnotations = new VisibleReportedPeptideAnnotations();
		xmlDefaultVisibleAnnotations.setVisibleReportedPeptideAnnotations( xmlVisibleReportedPeptideAnnotations );

		for( SearchAnnotation sa : PeptideDefaultVisibleAnnotationTypes.getDefaultVisibleAnnotationTypes() ) {
			xmlVisibleReportedPeptideAnnotations.getSearchAnnotation().add( sa );
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
		
		ReportedPeptideAnnotationSortOrder xmlReportedPeptideAnnotationSortOrder = new ReportedPeptideAnnotationSortOrder();
		xmlAnnotationSortOrder.setReportedPeptideAnnotationSortOrder( xmlReportedPeptideAnnotationSortOrder );
		
		for( SearchAnnotation xmlSearchAnnotation : PeptideAnnotationTypeSortOrder.getPeptideAnnotationTypeSortOrder() ) {
			xmlReportedPeptideAnnotationSortOrder.getSearchAnnotation().add( xmlSearchAnnotation );
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
		for( PercolatorPeptide percolatorPeptide : percolatorResults.getReportedPeptidePSMMap().keySet() ) {
			
			ParsedPeptide parsedPeptide = ReportedPeptideParsingUtils.parsePeptide( percolatorPeptide.getReportedPeptide() );
			
			ReportedPeptide xmlReportedPeptide = new ReportedPeptide();
			reportedPeptides.getReportedPeptide().add( xmlReportedPeptide );
			
			xmlReportedPeptide.setReportedPeptideString( percolatorPeptide.getReportedPeptide() );
			xmlReportedPeptide.setSequence( parsedPeptide.getNakedSequence() );

			
			// add in the filterable peptide annotations (e.g., q-value)
			ReportedPeptideAnnotations xmlReportedPeptideAnnotations = new ReportedPeptideAnnotations();
			xmlReportedPeptide.setReportedPeptideAnnotations( xmlReportedPeptideAnnotations );
			
			FilterableReportedPeptideAnnotations xmlFilterableReportedPeptideAnnotations = new FilterableReportedPeptideAnnotations();
			xmlReportedPeptideAnnotations.setFilterableReportedPeptideAnnotations( xmlFilterableReportedPeptideAnnotations );
			
			// handle q-value
			{
				FilterableReportedPeptideAnnotation xmlFilterableReportedPeptideAnnotation = new FilterableReportedPeptideAnnotation();
				xmlFilterableReportedPeptideAnnotations.getFilterableReportedPeptideAnnotation().add( xmlFilterableReportedPeptideAnnotation );
				
				xmlFilterableReportedPeptideAnnotation.setAnnotationName( PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_QVALUE );
				xmlFilterableReportedPeptideAnnotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
				xmlFilterableReportedPeptideAnnotation.setValue( BigDecimal.valueOf( percolatorPeptide.getqValue()) );
			}
			// handle p-value
			{
				FilterableReportedPeptideAnnotation xmlFilterableReportedPeptideAnnotation = new FilterableReportedPeptideAnnotation();
				xmlFilterableReportedPeptideAnnotations.getFilterableReportedPeptideAnnotation().add( xmlFilterableReportedPeptideAnnotation );
				
				xmlFilterableReportedPeptideAnnotation.setAnnotationName( PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_PVALUE );
				xmlFilterableReportedPeptideAnnotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
				xmlFilterableReportedPeptideAnnotation.setValue( BigDecimal.valueOf( percolatorPeptide.getpValue()) );
			}
			// handle pep
			{
				FilterableReportedPeptideAnnotation xmlFilterableReportedPeptideAnnotation = new FilterableReportedPeptideAnnotation();
				xmlFilterableReportedPeptideAnnotations.getFilterableReportedPeptideAnnotation().add( xmlFilterableReportedPeptideAnnotation );
				
				xmlFilterableReportedPeptideAnnotation.setAnnotationName( PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_PEP );
				xmlFilterableReportedPeptideAnnotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
				xmlFilterableReportedPeptideAnnotation.setValue( BigDecimal.valueOf( percolatorPeptide.getPep()) );
			}
			// handle svm score
			{
				FilterableReportedPeptideAnnotation xmlFilterableReportedPeptideAnnotation = new FilterableReportedPeptideAnnotation();
				xmlFilterableReportedPeptideAnnotations.getFilterableReportedPeptideAnnotation().add( xmlFilterableReportedPeptideAnnotation );
				
				xmlFilterableReportedPeptideAnnotation.setAnnotationName( PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_SVMSCORE );
				xmlFilterableReportedPeptideAnnotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
				xmlFilterableReportedPeptideAnnotation.setValue( BigDecimal.valueOf( percolatorPeptide.getSvmScore()) );
			}
			

			// add in the mods for this peptide
			if( parsedPeptide.getModMap() != null && parsedPeptide.getModMap().keySet().size() > 0 ) {
					
				PeptideModifications xmlModifications = new PeptideModifications();
				xmlReportedPeptide.setPeptideModifications( xmlModifications );
					
				for( int position : parsedPeptide.getModMap().keySet() ) {
					PeptideModification xmlModification = new PeptideModification();
					xmlModifications.getPeptideModification().add( xmlModification );
							
					xmlModification.setMass( BigDecimal.valueOf( parsedPeptide.getModMap().get( position ) ) );
					xmlModification.setPosition( new BigInteger( String.valueOf( position ) ) );

					xmlModification.setHavePsmLevelData( true ); // assume all mods have psm-level data for magnum/perc data
				}
			}

			
			// add in the PSMs and annotations
			Psms xmlPsms = new Psms();
			xmlReportedPeptide.setPsms( xmlPsms );
			
			// iterate over all PSMs for this reported peptide
			for( int scanNumber : percolatorResults.getReportedPeptidePSMMap().get( percolatorPeptide ).keySet() ) {
				
				PercolatorPSM percolatorPSM = percolatorResults.getReportedPeptidePSMMap().get( percolatorPeptide ).get( scanNumber );
				MagnumPSM magnumPSM = getMagnumPSMForScanNumberAndPercolatorReportedPeptide( scanNumber, percolatorPeptide, magnumResults );
				
				Psm xmlPsm = new Psm();
				xmlPsms.getPsm().add( xmlPsm );
				
				xmlPsm.setScanNumber( new BigInteger( String.valueOf( scanNumber ) ) );
				xmlPsm.setPrecursorCharge( new BigInteger( String.valueOf( magnumPSM.getCharge() ) ) );
				
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
					
					xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.MAGNUM_ANNOTATION_TYPE_SCORE );
					xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_MAGNUM );
					xmlFilterablePsmAnnotation.setValue( BigDecimal.valueOf( magnumPSM.getScore()) );
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
				
				
				// add in the mods for this psm
				if( magnumPSM.getModifications() != null && magnumPSM.getModifications().keySet().size() > 0 ) {
						
					PsmModifications xmlPSMModifications = new PsmModifications();
					xmlPsm.setPsmModifications( xmlPSMModifications );
						
					for( int position : magnumPSM.getModifications().keySet() ) {
						PsmModification xmlPSMModification = new PsmModification();
						xmlPSMModifications.getPsmModification().add( xmlPSMModification );
								
						xmlPSMModification.setMass( BigDecimal.valueOf( magnumPSM.getModifications().get( position ) ) );
						xmlPSMModification.setPosition( new BigInteger( String.valueOf( position ) ) );
					}
				}
				
			}//end iterating over all PSMs for a reported peptide
			
			
		}// end iterating over distinct reported peptides

		
		
		// add in the matched proteins section
		MatchedProteinsBuilder.getInstance().buildMatchedProteins(
				                                                   limelightInputRoot,
				                                                   conversionParameters.getFastaFile(),
				                                                   percolatorResults.getReportedPeptidePSMMap().keySet()
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
	
	
	private MagnumPSM getMagnumPSMForScanNumberAndPercolatorReportedPeptide( int scanNumber, PercolatorPeptide percolatorPeptide, MagnumResults magnumResults ) throws Exception {
		
		if( magnumResults.getMagnumResultMap() == null || !magnumResults.getMagnumResultMap().containsKey( scanNumber ) )
			throw new Exception( "Had no magnum results for scan number: " + scanNumber );
		
		String percRP = ModParsingUtils.getRoundedReportedPeptideString( percolatorPeptide );
		
		for( MagnumPSM psm : magnumResults.getMagnumResultMap().get( scanNumber ) ) {
			if( percRP.equals( ModParsingUtils.getRoundedReportedPeptideString( psm ) ) )
				return psm;
		}
		
		throw new Exception( "Could not find a PSM in Magnum results for scanNumber: " + scanNumber + " and reported peptide: " + percolatorPeptide.getReportedPeptide() );
	}
	
}

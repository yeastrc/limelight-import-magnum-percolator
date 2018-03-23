package org.yeastrc.emozi.xml.magnum.builder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.yeastrc.emozi.emozi_import.api.xml_dto.ConfigurationFile;
import org.yeastrc.emozi.emozi_import.api.xml_dto.ConfigurationFiles;
import org.yeastrc.emozi.emozi_import.api.xml_dto.DefaultVisibleAnnotations;
import org.yeastrc.emozi.emozi_import.api.xml_dto.EmoziInput;
import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterablePsmAnnotation;
import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterablePsmAnnotationType;
import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterablePsmAnnotationTypes;
import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterablePsmAnnotations;
import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterableReportedPeptideAnnotation;
import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterableReportedPeptideAnnotationType;
import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterableReportedPeptideAnnotationTypes;
import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterableReportedPeptideAnnotations;
import org.yeastrc.emozi.emozi_import.api.xml_dto.Modification;
import org.yeastrc.emozi.emozi_import.api.xml_dto.Modifications;
import org.yeastrc.emozi.emozi_import.api.xml_dto.Psm;
import org.yeastrc.emozi.emozi_import.api.xml_dto.Psms;
import org.yeastrc.emozi.emozi_import.api.xml_dto.ReportedPeptide;
import org.yeastrc.emozi.emozi_import.api.xml_dto.ReportedPeptide.ReportedPeptideAnnotations;
import org.yeastrc.emozi.emozi_import.api.xml_dto.ReportedPeptides;
import org.yeastrc.emozi.emozi_import.api.xml_dto.SearchAnnotation;
import org.yeastrc.emozi.emozi_import.api.xml_dto.SearchProgram;
import org.yeastrc.emozi.emozi_import.api.xml_dto.SearchProgram.PsmAnnotationTypes;
import org.yeastrc.emozi.emozi_import.api.xml_dto.SearchProgram.ReportedPeptideAnnotationTypes;
import org.yeastrc.emozi.emozi_import.api.xml_dto.SearchProgramInfo;
import org.yeastrc.emozi.emozi_import.api.xml_dto.SearchPrograms;
import org.yeastrc.emozi.emozi_import.api.xml_dto.StaticModification;
import org.yeastrc.emozi.emozi_import.api.xml_dto.StaticModifications;
import org.yeastrc.emozi.emozi_import.api.xml_dto.VisiblePsmAnnotations;
import org.yeastrc.emozi.emozi_import.create_import_file_from_java_objects.main.CreateImportFileFromJavaObjectsMain;
import org.yeastrc.emozi.xml.magnum.annotation.PSMAnnotationTypes;
import org.yeastrc.emozi.xml.magnum.annotation.PSMDefaultVisibleAnnotationTypes;
import org.yeastrc.emozi.xml.magnum.annotation.PeptideAnnotationTypes;
import org.yeastrc.emozi.xml.magnum.constants.Constants;
import org.yeastrc.emozi.xml.magnum.objects.ConversionParameters;
import org.yeastrc.emozi.xml.magnum.objects.MagnumPSM;
import org.yeastrc.emozi.xml.magnum.objects.MagnumParameters;
import org.yeastrc.emozi.xml.magnum.objects.MagnumResults;
import org.yeastrc.emozi.xml.magnum.objects.ParsedPeptide;
import org.yeastrc.emozi.xml.magnum.objects.PercolatorPSM;
import org.yeastrc.emozi.xml.magnum.objects.PercolatorPeptide;
import org.yeastrc.emozi.xml.magnum.objects.PercolatorResults;
import org.yeastrc.emozi.xml.magnum.utils.ModParsingUtils;
import org.yeastrc.emozi.xml.magnum.utils.ReportedPeptideParsingUtils;


public class XMLBuilder {

	public void buildAndSaveXML( ConversionParameters conversionParameters,
			                     MagnumResults magnumResults,
			                     MagnumParameters magnumParameters,
			                     PercolatorResults percolatorResults )
    throws Exception {

		EmoziInput emoziInputRoot = new EmoziInput();

		emoziInputRoot.setFastaFilename( conversionParameters.getFastaFile().getName() );
		
		// add in the conversion program (this program) information
		ConversionProgramBuilder.createInstance().buildConversionProgramSection( emoziInputRoot, conversionParameters);
		
		SearchProgramInfo searchProgramInfo = new SearchProgramInfo();
		emoziInputRoot.setSearchProgramInfo( searchProgramInfo );
		
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
				

		
		//
		// Define the static mods
		//
		if( magnumParameters.getStaticMods() != null && magnumParameters.getStaticMods().keySet().size() > 0 ) {
			StaticModifications smods = new StaticModifications();
			emoziInputRoot.setStaticModifications( smods );
			
			
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
		emoziInputRoot.setReportedPeptides( reportedPeptides );
		
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
					
				Modifications xmlModifications = new Modifications();
				xmlReportedPeptide.setModifications( xmlModifications );
					
				for( int position : parsedPeptide.getModMap().keySet() ) {
					Modification xmlModification = new Modification();
					xmlModifications.getModification().add( xmlModification );
							
					xmlModification.setMass( BigDecimal.valueOf( parsedPeptide.getModMap().get( position ) ) );
					xmlModification.setPosition( new BigInteger( String.valueOf( position ) ) );

					// see if this was a "search-for" modification, if not set "predicted" to true
					if( ModParsingUtils.isPredictedMod( magnumParameters, parsedPeptide.getNakedSequence(), position, parsedPeptide.getModMap().get( position ) ) ) {							
						xmlModification.setPredicted( true );							
					}
				}
			}

			
			// add in the PSMs and annotations
			Psms xmlPsms = new Psms();
			xmlReportedPeptide.setPsms( xmlPsms );
			
			// iterate over all PSMs for this reported peptide
			for( int scanNumber : percolatorResults.getReportedPeptidePSMMap().get( percolatorPeptide ).keySet() ) {
				
				PercolatorPSM percolatorPSM = percolatorResults.getReportedPeptidePSMMap().get( percolatorPeptide ).get( scanNumber );
				MagnumPSM magnumPSM = magnumResults.getReportedPeptidePSMMap().get( percolatorPeptide.getReportedPeptide() ).get( scanNumber );
				
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
				
			}//end iterating over all PSMs for a reported peptide
			
			
		}// end iterating over distinct reported peptides

		
		
		// add in the matched proteins section
		MatchedProteinsBuilder.getInstance().buildMatchedProteins(
				                                                   emoziInputRoot,
				                                                   conversionParameters.getFastaFile(),
				                                                   percolatorResults.getReportedPeptidePSMMap().keySet()
				                                                  );
		
		
		// add in the config file(s)
		ConfigurationFiles xmlConfigurationFiles = new ConfigurationFiles();
		emoziInputRoot.setConfigurationFiles( xmlConfigurationFiles );
		
		ConfigurationFile xmlConfigurationFile = new ConfigurationFile();
		xmlConfigurationFiles.getConfigurationFile().add( xmlConfigurationFile );
		
		xmlConfigurationFile.setSearchProgram( Constants.PROGRAM_NAME_MAGNUM );
		xmlConfigurationFile.setFileName( conversionParameters.getMagnumParametersFile().getName() );
		xmlConfigurationFile.setFileContent( Files.readAllBytes( FileSystems.getDefault().getPath( conversionParameters.getMagnumParametersFile().getAbsolutePath() ) ) );
		
		
		//make the xml file
		CreateImportFileFromJavaObjectsMain.getInstance().createImportFileFromJavaObjectsMain( conversionParameters.getEmoziXMLOutputFile(), emoziInputRoot);
		
	}
	
}

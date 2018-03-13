package org.yeastrc.emozi.xml.magnum.annotation;

import java.util.ArrayList;
import java.util.List;

import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterDirectionType;
import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterableReportedPeptideAnnotationType;
import org.yeastrc.emozi.xml.magnum.constants.Constants;

public class PeptideAnnotationTypes {
	
	// percolator scores
	public static final String PERCOLATOR_ANNOTATION_TYPE_QVALUE = "q-value";
	public static final String PERCOLATOR_ANNOTATION_TYPE_PVALUE = "p-value";
	public static final String PERCOLATOR_ANNOTATION_TYPE_PEP = "PEP";
	public static final String PERCOLATOR_ANNOTATION_TYPE_SVMSCORE = "SVM Score";

	
	
	public static List<FilterableReportedPeptideAnnotationType> getFilterablePsmAnnotationTypes( String programName ) {
		List<FilterableReportedPeptideAnnotationType> types = new ArrayList<FilterableReportedPeptideAnnotationType>();

		if( programName.equals( Constants.PROGRAM_NAME_PERCOLATOR ) ) {
			{
				FilterableReportedPeptideAnnotationType type = new FilterableReportedPeptideAnnotationType();
				type.setName( PERCOLATOR_ANNOTATION_TYPE_QVALUE );
				type.setDescription( "Q-value" );
				type.setFilterDirection( FilterDirectionType.BELOW );
				type.setDefaultFilter( true );
	
				types.add( type );
			}
			
			{
				FilterableReportedPeptideAnnotationType type = new FilterableReportedPeptideAnnotationType();
				type.setName( PERCOLATOR_ANNOTATION_TYPE_PVALUE );
				type.setDescription( "P-value" );
				type.setFilterDirection( FilterDirectionType.BELOW );
				type.setDefaultFilter( false );
	
				types.add( type );
			}
			
			{
				FilterableReportedPeptideAnnotationType type = new FilterableReportedPeptideAnnotationType();
				type.setName( PERCOLATOR_ANNOTATION_TYPE_PEP );
				type.setDescription( "Posterior error probability" );
				type.setFilterDirection( FilterDirectionType.BELOW );
				type.setDefaultFilter( false );
	
				types.add( type );
			}
			
			{
				FilterableReportedPeptideAnnotationType type = new FilterableReportedPeptideAnnotationType();
				type.setName( PERCOLATOR_ANNOTATION_TYPE_SVMSCORE );
				type.setDescription( "SVN Score from kernel function" );
				type.setFilterDirection( FilterDirectionType.ABOVE );
				type.setDefaultFilter( false );
	
				types.add( type );
			}
		} else {
			
			throw new IllegalArgumentException( "Unknown program name: " + programName );
			
		}

		
		return types;
	}
	
	
}

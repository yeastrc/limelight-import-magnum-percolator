package org.yeastrc.emozi.xml.magnum.annotation;

import java.util.ArrayList;
import java.util.List;

import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterDirectionType;
import org.yeastrc.emozi.emozi_import.api.xml_dto.FilterablePsmAnnotationType;
import org.yeastrc.emozi.xml.magnum.constants.Constants;



public class PSMAnnotationTypes {

	// comet scores
	public static final String MAGNUM_ANNOTATION_TYPE_SCORE = "Score";
	public static final String MAGNUM_ANNOTATION_TYPE_DSCORE = "dScore";
	public static final String MAGNUM_ANNOTATION_TYPE_PPMERROR = "PPM Error";

	// percolator scores
	public static final String PERCOLATOR_ANNOTATION_TYPE_QVALUE = "q-value";
	public static final String PERCOLATOR_ANNOTATION_TYPE_PVALUE = "p-value";
	public static final String PERCOLATOR_ANNOTATION_TYPE_PEP = "PEP";
	public static final String PERCOLATOR_ANNOTATION_TYPE_SVMSCORE = "SVM Score";

	
	
	public static List<FilterablePsmAnnotationType> getFilterablePsmAnnotationTypes( String programName ) {
		List<FilterablePsmAnnotationType> types = new ArrayList<FilterablePsmAnnotationType>();

		if( programName.equals( Constants.PROGRAM_NAME_MAGNUM ) ) {
			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( MAGNUM_ANNOTATION_TYPE_SCORE );
				type.setDescription( "Cross-correlation coefficient" );
				type.setFilterDirection( FilterDirectionType.ABOVE );
				type.setDefaultFilter( false );
	
				types.add( type );
			}
			
			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( MAGNUM_ANNOTATION_TYPE_DSCORE );
				type.setDescription( "Difference between the XCorr of this PSM and the next best PSM (with a dissimilar peptide)" );
				type.setDefaultFilter( false );
				type.setFilterDirection( FilterDirectionType.ABOVE );
				
				types.add( type );
			}
			
			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( MAGNUM_ANNOTATION_TYPE_PPMERROR );
				type.setDescription( "PPM Error, as calculated by " + Constants.PROGRAM_NAME_MAGNUM );
				type.setDefaultFilter( false );
				type.setFilterDirection( FilterDirectionType.ABOVE );
				
				types.add( type );
			}
			
		}

		else if( programName.equals( Constants.PROGRAM_NAME_PERCOLATOR ) ) {
			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( PERCOLATOR_ANNOTATION_TYPE_QVALUE );
				type.setDescription( "Q-value" );
				type.setFilterDirection( FilterDirectionType.BELOW );
				type.setDefaultFilter( true );
	
				types.add( type );
			}
			
			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( PERCOLATOR_ANNOTATION_TYPE_PVALUE );
				type.setDescription( "P-value" );
				type.setFilterDirection( FilterDirectionType.BELOW );
				type.setDefaultFilter( false );
	
				types.add( type );
			}
			
			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( PERCOLATOR_ANNOTATION_TYPE_PEP );
				type.setDescription( "Posterior error probability" );
				type.setFilterDirection( FilterDirectionType.BELOW );
				type.setDefaultFilter( false );
	
				types.add( type );
			}
			
			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( PERCOLATOR_ANNOTATION_TYPE_SVMSCORE );
				type.setDescription( "SVN Score from kernel function" );
				type.setFilterDirection( FilterDirectionType.ABOVE );
				type.setDefaultFilter( false );
	
				types.add( type );
			}
		}

		
		return types;
	}
	
	
}

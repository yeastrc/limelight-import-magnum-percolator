package org.yeastrc.limelight.xml.magnum.annotation;

import java.util.ArrayList;
import java.util.List;

import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchAnnotation;
import org.yeastrc.limelight.xml.magnum.constants.Constants;

public class PSMAnnotationTypeSortOrder {

	public static List<SearchAnnotation> getPSMAnnotationTypeSortOrder() {
		List<SearchAnnotation> annotations = new ArrayList<SearchAnnotation>();
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PSMAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_QVALUE );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
			annotations.add( annotation );
		}
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PSMAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_PEP );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
			annotations.add( annotation );
		}
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PSMAnnotationTypes.MAGNUM_ANNOTATION_TYPE_EVALUE );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_MAGNUM );
			annotations.add( annotation );
		}
		
		return annotations;
	}
}

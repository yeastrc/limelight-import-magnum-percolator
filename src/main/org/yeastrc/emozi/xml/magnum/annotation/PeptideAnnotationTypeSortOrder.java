package org.yeastrc.emozi.xml.magnum.annotation;

import java.util.ArrayList;
import java.util.List;

import org.yeastrc.emozi.emozi_import.api.xml_dto.SearchAnnotation;
import org.yeastrc.emozi.xml.magnum.constants.Constants;

public class PeptideAnnotationTypeSortOrder {

	public static List<SearchAnnotation> getPeptideAnnotationTypeSortOrder() {
		List<SearchAnnotation> annotations = new ArrayList<SearchAnnotation>();
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_QVALUE );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
			annotations.add( annotation );
		}
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_PEP );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
			annotations.add( annotation );
		}
		
		return annotations;
	}
}
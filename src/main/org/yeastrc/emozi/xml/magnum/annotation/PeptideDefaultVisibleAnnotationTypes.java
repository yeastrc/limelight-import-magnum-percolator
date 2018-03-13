package org.yeastrc.emozi.xml.magnum.annotation;

import java.util.ArrayList;
import java.util.List;

import org.yeastrc.emozi.emozi_import.api.xml_dto.SearchAnnotation;
import org.yeastrc.emozi.xml.magnum.constants.Constants;

public class PeptideDefaultVisibleAnnotationTypes {

	/**
	 * Get the default visibile annotation types for Magnum data
	 * @return
	 */
	public static List<SearchAnnotation> getDefaultVisibleAnnotationTypes() {
		List<SearchAnnotation> annotations = new ArrayList<SearchAnnotation>();
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PeptideAnnotationTypes.PERCOLATOR_ANNOTATION_TYPE_QVALUE );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_PERCOLATOR );
			annotations.add( annotation );
		}
		
		return annotations;
	}
}

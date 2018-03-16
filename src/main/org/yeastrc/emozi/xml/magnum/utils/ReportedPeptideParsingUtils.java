package org.yeastrc.emozi.xml.magnum.utils;

import java.util.HashMap;
import java.util.Map;

import org.yeastrc.emozi.xml.magnum.objects.ParsedPeptide;

public class ReportedPeptideParsingUtils {

	
	public static ParsedPeptide parsePeptide( String peptideSequence ) {
		
		StringBuilder nakedSequence = new StringBuilder();
		Map<Integer, Double> modMap = new HashMap<>();

		int peptidePosition = 0;
		StringBuilder numberBuild = null;
		
		for (int i = 0; i < peptideSequence.length(); i++){
		    char c = peptideSequence.charAt(i);
		    
		    if( c == '[' ) {
		    
		    	numberBuild = new StringBuilder();
		    	
		    } else if( c == ']' ) {
		    	
		    	modMap.put( peptidePosition, Double.parseDouble( numberBuild.toString() ) );
		    	numberBuild = null;
		    	
		    } else if( numberBuild != null ) {
		    	
		    	numberBuild.append( c );
		    	
		    } else {
		    	
		    	peptidePosition++;
		    	nakedSequence.append( c );
		    }
		}
		
		ParsedPeptide pp = new ParsedPeptide();
		pp.setModMap( modMap );
		pp.setNakedSequence( nakedSequence.toString() );
		
		return pp;
	}
	
}

/*
 * Original author: Michael Riffle <mriffle .at. uw.edu>
 *                  
 * Copyright 2018 University of Washington - Seattle, WA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

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

package org.yeastrc.limelight.xml.magnum.utils;

import org.yeastrc.limelight.xml.magnum.objects.MagnumResults;
import org.yeastrc.limelight.xml.magnum.objects.PercolatorResults;

public class DataComparer {

	/**
	 * Ensure that for every reported peptide and psm reported by percolator, that the same peptide and
	 * psm could be found in the magnum data. It is assumed that the percolator data is a subset of the
	 * magnum data, as percolator does not report all target PSMs (for some reason)
	 * 
	 * @param magnumResults
	 * @param percolatorResults
	 * @throws Exception if no magnum result could be found for a percolator result
	 */
	public static void compareData( MagnumResults magnumResults, PercolatorResults percolatorResults ) throws Exception {
		
		for( String reportedPeptideString : percolatorResults.getPeptideResults().keySet() ) {
			
			if( !magnumResults.getMagnumResultMap().containsKey( reportedPeptideString ) ) {
				
				throw new Exception( "Could not find reported peptide (" + reportedPeptideString + ") in magnum results." );
			}
			
			/*
			 * check that all scan numbers reported for this reported peptide by percolator were
			 * also reported for this reported peptide by magnum
			 */
			for( int scanNumber : percolatorResults.getPeptideResults().get( reportedPeptideString ).getPsmsIndexedByScanNumber().keySet() ) {
				
				if( !magnumResults.getMagnumResultMap().get( reportedPeptideString ).containsKey( scanNumber ) )
					throw new Exception( "Could not find reported peptide (" + reportedPeptideString + ") for scan number (" + scanNumber + ") in magnum results." );

				if( magnumResults.getMagnumResultMap().get( reportedPeptideString ).get( scanNumber ).size() < 1 )
					throw new Exception( "Could not find reported peptide (" + reportedPeptideString + ") for scan number (" + scanNumber + ") in magnum results." );

			}
		}		
	}
	
	
}

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

import org.yeastrc.limelight.xml.magnum.objects.MagnumPSM;
import org.yeastrc.limelight.xml.magnum.objects.MagnumResults;
import org.yeastrc.limelight.xml.magnum.objects.PercolatorPeptide;
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
		
		for( PercolatorPeptide percolatorPeptide : percolatorResults.getReportedPeptidePSMMap().keySet() ) {
			
			String reportedPeptideString = percolatorPeptide.getReportedPeptide();
			
			/*
			 * check that all scan numbers reported for this reported peptide by percolator were
			 * also reported for this reported peptide by magnum
			 */
			for( int scanNumber : percolatorResults.getReportedPeptidePSMMap().get( percolatorPeptide ).keySet() ) {
				
				if( !magnumResultsContainUnambiguousPSMMatch( magnumResults, percolatorPeptide, scanNumber ) )
					throw new Exception( "Could not find single magnum PSM for reported peptide: " + reportedPeptideString + ", scan number: " + scanNumber );
				
			}
			
		}		
	}
	
	/**
	 * Return true if the magnum results contain a single PSM for the given scan number that would match
	 * the given percolator reported peptide, when rounding all mod masses to integers.
	 * 
	 * @param magnumResults
	 * @param percPeptide
	 * @param scanNumber
	 * @return
	 * @throws Exception 
	 */
	private static boolean magnumResultsContainUnambiguousPSMMatch( MagnumResults magnumResults, PercolatorPeptide percPeptide, int scanNumber ) throws Exception {
		
		if( !magnumResults.getMagnumResultMap().containsKey( scanNumber ) )
			return false;
		
		if( magnumResults.getMagnumResultMap().get( scanNumber ).size() == 0 ) {
			return false;
		}

		String percRP = ModParsingUtils.getRoundedReportedPeptideString( percPeptide );

		int numMatches = 0;
		for( MagnumPSM psm : magnumResults.getMagnumResultMap().get( scanNumber ) ) {
			
			String magRP = ModParsingUtils.getRoundedReportedPeptideString( psm );
			
			if( percRP.equals( magRP ) ) {
				System.out.println( psm );
				numMatches++;
			}
		}
		
		if( numMatches == 1 ) {
			return true;
		}
		
		
		if( numMatches > 1 ) {
			throw new Exception( "Found more than one magnum PSM with scan number " + scanNumber + " that matches reported peptide: " + percRP );
		}
		
		return false;
	}
	
}

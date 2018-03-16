package org.yeastrc.emozi.xml.magnum.utils;

import org.yeastrc.emozi.xml.magnum.objects.MagnumResults;
import org.yeastrc.emozi.xml.magnum.objects.PercolatorPeptide;
import org.yeastrc.emozi.xml.magnum.objects.PercolatorResults;

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
			
			// check that magnum also reported this reported peptide
			if( !magnumResults.getReportedPeptidePSMMap().containsKey( reportedPeptideString ) )
				throw new Exception( "Could not find any magnum entry for reported peptide: " + reportedPeptideString );
		
			/*
			 * check that all scan numbers reported for this reported peptide by percolator were
			 * also reported for this reported peptide by magnum
			 */
			for( int scanNumber : percolatorResults.getReportedPeptidePSMMap().get( percolatorPeptide ).keySet() ) {
				
				if( !magnumResults.getReportedPeptidePSMMap().get( reportedPeptideString ).containsKey( scanNumber ) )
					throw new Exception( "Could not find a magnum PSM for reported peptide: " + reportedPeptideString + ", scan number: " + scanNumber );
				
			}
			
		}		
	}
	
}

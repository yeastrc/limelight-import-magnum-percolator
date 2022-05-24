package org.yeastrc.limelight.xml.magnum.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.yeastrc.limelight.xml.magnum.objects.*;

public class MagnumParsingUtils {

	/**
	 * Take in the magnumResults and re-map every PSM to a percolator reported peptide string. This
	 * is necessary because of rounding errors that may occur when Magnum creates mod masses to
	 * report in percolator reported peptide strings. Rarely, this rounding error results in a reported
	 * mass in percolator that can't be mapped to any peptide with that mass in the magnum results, when
	 * assuming magnum used exact math (not floating point math).
	 * 
	 * @param magnumResults
	 * @param percolatorResults
	 * @return
	 * @throws Exception
	 */
	public static void mapMagnumPSMsToPercolatorReportedPeptides( MagnumResults magnumResults, PercolatorResults percolatorResults ) throws Exception {

		Map<ReportedPeptide, Map<SubSearchName, Map<ScanNumber,Collection<MagnumPSM>>>> returnMap = new HashMap<>();
		
		for( ReportedPeptide oldReportedPeptide : magnumResults.getMagnumResultMap().keySet() ) {

			for(SubSearchName subSearchName : magnumResults.getMagnumResultMap().get(oldReportedPeptide).keySet()) {

				for( ScanNumber scanNumber : magnumResults.getMagnumResultMap().get(oldReportedPeptide).get(subSearchName).keySet() ) {

					Collection<MagnumPSM> magnumPSMs = magnumResults.getMagnumResultMap().get(oldReportedPeptide).get(subSearchName).get(scanNumber);

					for (MagnumPSM magnumPSM : magnumPSMs) {

						String reportedPeptideStringToUse = MagnumParsingUtils.getPercolatorReportedPeptideStringForMagnumPSM(percolatorResults, magnumPSM);
						//this will be null for decoys

						if (reportedPeptideStringToUse != null) {

							ReportedPeptide newReportedPeptide = new ReportedPeptide(reportedPeptideStringToUse);

							if (!returnMap.containsKey(newReportedPeptide)) {
								returnMap.put(newReportedPeptide, new HashMap<>());
							}

							if(!returnMap.get(newReportedPeptide).containsKey(subSearchName)) {
								returnMap.get(newReportedPeptide).put(subSearchName, new HashMap<>());
							}

							if(!returnMap.get(newReportedPeptide).get(subSearchName).containsKey(scanNumber)) {
								returnMap.get(newReportedPeptide).get(subSearchName).put(scanNumber, new HashSet<>());
							}

							returnMap.get(newReportedPeptide).get(subSearchName).get(scanNumber).add(magnumPSM);
						}
					}
				}
			}
		}
		
		magnumResults.setMagnumResultMap( returnMap );
	}
	
	/**
	 * Get the reported peptide string that should be used for a MagnumPSM, given that there may be rounding
	 * errors in the conversion of mod masses to integers and given the actual reported peptide strings given
	 * to percolator.
	 * 
	 * @param percolatorResults
	 * @param magnumPSM
	 * @return
	 * @throws Exception
	 */
	public static String getPercolatorReportedPeptideStringForMagnumPSM( PercolatorResults percolatorResults, MagnumPSM magnumPSM ) throws Exception {
		
		Collection<String> reportedPeptideStringCandidates = new HashSet<>();
		MagnumParsingUtils.buildReportedPeptideStringsWithRoundingErrors( "", magnumPSM.getPeptideSequence(), magnumPSM.getModifications(), 1, reportedPeptideStringCandidates );
		
		for( String candidateReportedPeptide : reportedPeptideStringCandidates ) {

			ReportedPeptide candidateReportedPeptideObject = new ReportedPeptide(candidateReportedPeptide);
			ScanNumber scanNumberObject = new ScanNumber(magnumPSM.getScanNumber());
			SubSearchName subSearchNameObject = new SubSearchName(magnumPSM.getSubSearchName());

			if(percolatorResults.getPeptideResults().containsKey(candidateReportedPeptideObject)
					&& percolatorResults.getPeptideResults().get(candidateReportedPeptideObject).getPsmMap().containsKey(subSearchNameObject)
					&& percolatorResults.getPeptideResults().get(candidateReportedPeptideObject).getPsmMap().get(subSearchNameObject).containsKey(scanNumberObject)) {
				return candidateReportedPeptide;
			}

		}

//		System.err.println("Warning: Could not find candidate percolator peptide for magnum PSM.");
//		System.err.println("Magnum PSM: " + magnumPSM);
//		System.err.println("Candidate peptides (none of which were found: " + reportedPeptideStringCandidates);
		
		return null;
	}
	
	/**
	 * Get all reported peptide strings that could be used for a given peptide sequence and
	 * set of dynamic mods, given possible rounding errors.
	 * 
	 * @param growingPeptideString The reported peptide sequence being built
	 * @param nakedPeptide The peptide sequence, to which we are adding mods
	 * @param mods The mods we are adding to the peptide sequence
	 * @param positionToTest The position we are currently on in the nakedPeptide (starting at 1)
	 * @param reportedPeptideStrings The collection of final reported peptide strings
	 */
	private static void buildReportedPeptideStringsWithRoundingErrors( String growingPeptideString,
																	   String nakedPeptide,
																	   Map<Integer,BigDecimal> mods,
																	   int positionToTest,
																	   Collection<String> reportedPeptideStrings ) {
		
		if( mods == null || mods.size() < 1 ) {
			reportedPeptideStrings.add( nakedPeptide );
			return;
		}
		
		if( positionToTest < 1 || positionToTest > nakedPeptide.length() + 1 ) {
			throw new RuntimeException( "Position to test (" + positionToTest + ") is not in the peptide we're testing..." );
		}
		
		if( positionToTest == nakedPeptide.length() + 1 ) {
			reportedPeptideStrings.add( growingPeptideString );
			return;
		}
				
		String r = String.valueOf( nakedPeptide.charAt( positionToTest - 1 ) );
		String newSequence = growingPeptideString + r;

		if( !mods.containsKey( positionToTest ) ) {
			buildReportedPeptideStringsWithRoundingErrors( newSequence, nakedPeptide, mods, positionToTest + 1, reportedPeptideStrings );
			return;
		}
		
		BigDecimal mass = mods.get( positionToTest );
		
		{
	    	String exactSequence = newSequence + "[" + mass.setScale( 0, RoundingMode.HALF_UP ).toString() + "]";
			buildReportedPeptideStringsWithRoundingErrors( exactSequence, nakedPeptide, mods, positionToTest + 1, reportedPeptideStrings );
		}
		
		BigDecimal roundedMass = mass.setScale( 4, RoundingMode.DOWN );
		String stringMass = roundedMass.toString();
		
		
		if( stringMass.endsWith( ".4999" ) ) {
			
			BigDecimal massUp = mass.setScale( 0, RoundingMode.HALF_UP ).add( BigDecimal.valueOf( 1 ) );
			
	    	String finalSequence = newSequence + "[" + massUp.toString() + "]";
			buildReportedPeptideStringsWithRoundingErrors( finalSequence, nakedPeptide, mods, positionToTest + 1, reportedPeptideStrings );
		}
		
		if( stringMass.endsWith( ".5000" ) ) {
			
			BigDecimal massDown = mass.setScale( 0, RoundingMode.HALF_UP ).subtract( BigDecimal.valueOf( 1 ) );
			
	    	String finalSequence = newSequence + "[" + massDown.toString() + "]";
			buildReportedPeptideStringsWithRoundingErrors( finalSequence, nakedPeptide, mods, positionToTest + 1, reportedPeptideStrings );
		}
		
	}
	
	
}

package org.yeastrc.emozi.xml.magnum.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.yeastrc.emozi.xml.magnum.objects.ParsedPeptide;

public class ReportedPeptideParsingUtils_TestParsePeptideTwoMods {

	@Test
	public void test_twoMods() {

		ParsedPeptide parsedPeptide = ReportedPeptideParsingUtils.parsePeptide( "PEP[329.39]TID[199.221]E" );
		
		Map<Integer, Double> testMods = new HashMap<>();
		testMods.put( 3, 329.39 );
		testMods.put( 6, 199.221 );
		
		assertEquals( "PEPTIDE", parsedPeptide.getNakedSequence() );
		assertEquals( testMods, parsedPeptide.getModMap() );
		
	}
	
}

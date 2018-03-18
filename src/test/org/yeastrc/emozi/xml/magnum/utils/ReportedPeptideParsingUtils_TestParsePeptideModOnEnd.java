package org.yeastrc.emozi.xml.magnum.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.yeastrc.emozi.xml.magnum.objects.ParsedPeptide;

public class ReportedPeptideParsingUtils_TestParsePeptideModOnEnd {
	
	@Test
	public void test_modOnEnd() {
		
		ParsedPeptide parsedPeptide = ReportedPeptideParsingUtils.parsePeptide( "PEP[329.39]TIDE[125]" );
		
		Map<Integer, Double> testMods = new HashMap<>();
		testMods.put( 3, 329.39 );
		testMods.put( 7, 125.0 );
	
		assertEquals( "PEPTIDE", parsedPeptide.getNakedSequence() );
		assertEquals( testMods, parsedPeptide.getModMap() );
	}
}

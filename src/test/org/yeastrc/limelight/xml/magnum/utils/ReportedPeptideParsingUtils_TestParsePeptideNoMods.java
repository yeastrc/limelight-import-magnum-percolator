package org.yeastrc.limelight.xml.magnum.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yeastrc.limelight.xml.magnum.objects.ParsedPeptide;
import org.yeastrc.limelight.xml.magnum.utils.ReportedPeptideParsingUtils;

public class ReportedPeptideParsingUtils_TestParsePeptideNoMods {

	@Test
	public void test_noMods() {

		ParsedPeptide parsedPeptide = ReportedPeptideParsingUtils.parsePeptide( "PEPTIDE" );
		
		assertEquals( "PEPTIDE", parsedPeptide.getNakedSequence() );
		assertEquals( 0, parsedPeptide.getModMap().size() );
		
	}
	
}

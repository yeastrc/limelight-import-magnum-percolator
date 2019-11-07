package org.yeastrc.limelight.xml.magnum.utils;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.yeastrc.limelight.xml.magnum.utils.ReportedPeptideParsingUtils;

public class ReportedPeptideParsingUtils_TestParsePeptideInvalidSyntax {

	@Test
	public void test_invalidModStrings() {

		try {
			ReportedPeptideParsingUtils.parsePeptide( "PE[329.39PEPT[125]IDE" );	// no closing bracket for mod before next mod
			fail( "Should have failed." );
		
		} catch( Exception e ) { ; }
		
		try {
			ReportedPeptideParsingUtils.parsePeptide( "PE[329.39PEPT[125" );	// no closing bracket for mod at end
			fail( "Should have failed." );
		
		} catch( Exception e ) { ; }
		
		try {
			ReportedPeptideParsingUtils.parsePeptide( "PE[329.39PEPT[125PE" );	// no closing bracket for final mod
			fail( "Should have failed." );
		
		} catch( Exception e ) { ; }
		
		try {
			ReportedPeptideParsingUtils.parsePeptide( "PE[329.39PEPT125]IDE" );	// non numeric values in mod
			fail( "Should have failed." );
		
		} catch( Exception e ) { ; }
		
		try {
			ReportedPeptideParsingUtils.parsePeptide( "PE[329.3.9]PEPT[125]IDE" );	// invalid decimal (two periods) in mod
			fail( "Should have failed." );
		
		} catch( Exception e ) { ; }
		
		try {
			ReportedPeptideParsingUtils.parsePeptide( "PE[329.39]P]EPT[125]IDE" );	// closing bracket w/o starting bracket
			fail( "Should have failed." );
		
		} catch( Exception e ) { ; }
		
		try {
			ReportedPeptideParsingUtils.parsePeptide( "PE[32DD9.39]P]EPT[125]IDE" );	// non numeric values in mod
			fail( "Should have failed." );
		
		} catch( Exception e ) { ; }
		
		try {
			ReportedPeptideParsingUtils.parsePeptide( "PE[329.39]P]Ept[125]IDE" );		// non capital letters in sequence
			fail( "Should have failed." );
		
		} catch( Exception e ) { ; }
		
	}
	
}

package org.yeastrc.emozi.xml.magnum.utils;

import static org.junit.Assert.fail;

import org.junit.Test;

public class ReportedPeptideParsingUtils_TestParsePeptideModOnStart {
	
	@Test
	public void test_modOnStart() {

		try {
			ReportedPeptideParsingUtils.parsePeptide( "[329.39]PEPT[125]IDE" );
			fail( "Should have failed with mod at beginning of string." );
		
		} catch( Exception e ) { ; }
		
	}
		
	
}

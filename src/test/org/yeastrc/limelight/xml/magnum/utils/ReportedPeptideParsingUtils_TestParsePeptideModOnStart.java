package org.yeastrc.limelight.xml.magnum.utils;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.yeastrc.limelight.xml.magnum.utils.ReportedPeptideParsingUtils;

public class ReportedPeptideParsingUtils_TestParsePeptideModOnStart {
	
	@Test
	public void test_modOnStart() {

		try {
			ReportedPeptideParsingUtils.parsePeptide( "[329.39]PEPT[125]IDE" );
			fail( "Should have failed with mod at beginning of string." );
		
		} catch( Exception e ) { ; }
		
	}
		
	
}

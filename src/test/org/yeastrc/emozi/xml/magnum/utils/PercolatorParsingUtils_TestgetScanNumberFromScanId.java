package org.yeastrc.emozi.xml.magnum.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

public class PercolatorParsingUtils_TestgetScanNumberFromScanId {

	@Test
	public void test_getScanNumberFromScanId() throws IOException {

		int scanNumber = PercolatorParsingUtils.getScanNumberFromScanId( "T-63436-128.70" );
		assertEquals( 63436, scanNumber );
		
		scanNumber = PercolatorParsingUtils.getScanNumberFromScanId( "T-69291-141.13-2" );
		assertEquals( 69291, scanNumber );
		
		try {
			scanNumber = PercolatorParsingUtils.getScanNumberFromScanId( "T-634asdf36-128.70" );
			fail( "Should have gotten an error." );
		} catch( Exception e ) { ; }

		try {
			scanNumber = PercolatorParsingUtils.getScanNumberFromScanId( "" );
			fail( "Should have gotten an error." );
		} catch( Exception e ) { ; }
		
		try {
			scanNumber = PercolatorParsingUtils.getScanNumberFromScanId( "asdf" );
			fail( "Should have gotten an error." );
		} catch( Exception e ) { ; }
		

	}
	
}

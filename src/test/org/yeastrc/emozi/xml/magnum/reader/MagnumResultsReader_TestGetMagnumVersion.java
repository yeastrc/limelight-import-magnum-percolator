package org.yeastrc.emozi.xml.magnum.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

public class MagnumResultsReader_TestGetMagnumVersion {

	@Test
	public void test_getMagnumVersion() throws IOException {

		String version = MagnumResultsReader.getMagnumVersion( "Magnum version 1.0-dev" );
		
		assertEquals( "1.0-dev", version );

		try {
			version = MagnumResultsReader.getMagnumVersion( "version 1.0-dev" );
			fail( "Should have gotten an error." );
		} catch( Exception e ) { ; }

		try {
			version = MagnumResultsReader.getMagnumVersion( "version 1.0-dev " );
			fail( "Should have gotten an error." );
		} catch( Exception e ) { ; }

	}
}

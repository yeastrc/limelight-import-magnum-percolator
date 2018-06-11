/*
 * Original author: Michael Riffle <mriffle .at. uw.edu>
 *                  
 * Copyright 2018 University of Washington - Seattle, WA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yeastrc.limelight.xml.magnum.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.yeastrc.limelight.xml.magnum.reader.MagnumTextResultsReader;

public class MagnumResultsReader_TestGetMagnumVersion {

	@Test
	public void test_getMagnumVersion() throws IOException {

		String version = MagnumTextResultsReader.getMagnumVersion( "Magnum version 1.0-dev" );
		
		assertEquals( "1.0-dev", version );

		try {
			version = MagnumTextResultsReader.getMagnumVersion( "version 1.0-dev" );
			fail( "Should have gotten an error." );
		} catch( Exception e ) { ; }

		try {
			version = MagnumTextResultsReader.getMagnumVersion( "version 1.0-dev " );
			fail( "Should have gotten an error." );
		} catch( Exception e ) { ; }

	}
}

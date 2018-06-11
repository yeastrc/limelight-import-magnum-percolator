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

package org.yeastrc.limelight.xml.magnum.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.yeastrc.limelight.xml.magnum.utils.PercolatorParsingUtils;

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

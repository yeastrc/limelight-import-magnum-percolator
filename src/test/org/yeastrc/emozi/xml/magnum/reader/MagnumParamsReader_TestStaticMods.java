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

package org.yeastrc.emozi.xml.magnum.reader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class MagnumParamsReader_TestStaticMods {

	private InputStream _IS = null;
	
	@Before
	public void setUp() {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "# Fixed modifications. Add as many as necessary.\n" );
		sb.append( "#\n" );
		sb.append( "\n" );
		sb.append( "#fixed_modification = C   57.0834846\n" );
		sb.append( "fixed_modification = C   47.032146\n" );
		sb.append( "fixed_modification = W   92.3944 #dkdkdkf dkdfdfk\n" );
		sb.append( "\n" );
		sb.append( "\n" );

		sb.append( "#modification = M 15.9949\n" );
		sb.append( "modification = C   57.02146 #don't search this, creates too many mathematical problems\n" );
		sb.append( "modification = L   80.321146\n" );
		sb.append( "\n" );
		sb.append( "\n" );

		_IS = IOUtils.toInputStream( sb.toString(), Charset.defaultCharset() );	
		
	}

	
	@Test
	public void testGetStaticMods() throws IOException {

		Map<Character, Double> staticMods = MagnumParamsReader.getStaticModsFromParamsFile( _IS );
		
		Map<Character, Double> testStaticMods = new HashMap<>();
		testStaticMods.put( 'C', 47.032146);
		testStaticMods.put( 'W', 92.3944 );
		
		assertEquals( testStaticMods, staticMods );

	}
	
}

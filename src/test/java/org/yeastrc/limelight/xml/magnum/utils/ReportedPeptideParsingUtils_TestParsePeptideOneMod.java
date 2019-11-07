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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.yeastrc.limelight.xml.magnum.objects.ParsedPeptide;
import org.yeastrc.limelight.xml.magnum.utils.ReportedPeptideParsingUtils;

public class ReportedPeptideParsingUtils_TestParsePeptideOneMod {

	@Test
	public void test_oneMod() {

		ParsedPeptide parsedPeptide = ReportedPeptideParsingUtils.parsePeptide( "PEP[329.39]TIDE" );
		
		Map<Integer, Double> testMods = new HashMap<>();
		testMods.put( 3, 329.39 );
		
		assertEquals( "PEPTIDE", parsedPeptide.getNakedSequence() );
		assertEquals( testMods, parsedPeptide.getModMap() );
		
	}
	
}

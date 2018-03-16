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

import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.yeastrc.emozi.xml.magnum.objects.MagnumResults;

public class MagnumResultsReader_TestGetMagnumResults {

	private InputStream _IS = null;
	
	@Before
	public void setUp() {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "Magnum version 1.0-dev\n" );
		sb.append( "Scan Number	Ret Time	Obs Mass	Charge	PSM Mass	PPM Error	Score	dScore	e-value	Peptide #1 Score	Peptide #1	Linked AA #1	Protein #1	Protein #1 Site	Peptide #2 Score	Peptide #2	Linked AA #2	Protein #2	Protein #2 Site	Linker Mass\n" );
		sb.append( "783	2.9861	1248.6122	3	1248.6122	0.0000	0.8050	0.0900	9.963e+000	0.8050	YAC[71]TVNSAGHR	3	random_seq_187	163\n" );
		sb.append( "806	3.0657	1192.5952	2	1192.5948	-0.3228	1.3300	0.7800	1.701e-004	1.3300	DTHKSEIAHR	-1	BSA	-\n" );
		sb.append( "809	3.0735	1249.6155	3	1249.6155	0.0000	1.0000	0.1150	5.177e+000	1.0000	YAC[72]TVNSAGHR	3	random_seq_187	163\n" );
		sb.append( "835	3.1597	1249.6155	3	1249.6155	0.0000	1.1950	0.1900	2.034e+000	1.1950	YAC[72]TVNSAGHR	3	random_seq_187	163\n" );
		sb.append( "877	3.2952	1165.4865	2	1165.4855	-0.8195	2.0450	0.3150	1.134e-003	2.0450	C[57.02]C[57.02]TKPESER	-1	BSA	-\n" );

		_IS = IOUtils.toInputStream( sb.toString(), Charset.defaultCharset() );	
		
	}

	
	@Test
	public void testGetDynamicMods() throws Throwable {

		MagnumResults magnumResults = MagnumResultsReader.getMagnumResults( _IS );
		
		assertEquals( "1.0-dev", magnumResults.getMagnumVersion() );
		assertEquals( 4, magnumResults.getReportedPeptidePSMMap().keySet().size() );
		assertEquals( 2, magnumResults.getReportedPeptidePSMMap().get( "YAC[72]TVNSAGHR" ).keySet().size() );
		assertEquals( 1, magnumResults.getReportedPeptidePSMMap().get( "YAC[71]TVNSAGHR" ).keySet().size() );
		assertEquals( 1, magnumResults.getReportedPeptidePSMMap().get( "DTHKSEIAHR" ).keySet().size() );
		assertEquals( 1, magnumResults.getReportedPeptidePSMMap().get( "C[57.02]C[57.02]TKPESER" ).keySet().size() );
		
		assertEquals( 783, magnumResults.getReportedPeptidePSMMap().get( "YAC[71]TVNSAGHR" ).get( 783 ).getScanNumber() );
		assertEquals( 806, magnumResults.getReportedPeptidePSMMap().get( "DTHKSEIAHR" ).get( 806 ).getScanNumber() );
		assertEquals( 809, magnumResults.getReportedPeptidePSMMap().get( "YAC[72]TVNSAGHR" ).get( 809 ).getScanNumber() );
		assertEquals( 835, magnumResults.getReportedPeptidePSMMap().get( "YAC[72]TVNSAGHR" ).get( 835 ).getScanNumber() );
		assertEquals( 877, magnumResults.getReportedPeptidePSMMap().get( "C[57.02]C[57.02]TKPESER" ).get( 877 ).getScanNumber() );

	}
	
}

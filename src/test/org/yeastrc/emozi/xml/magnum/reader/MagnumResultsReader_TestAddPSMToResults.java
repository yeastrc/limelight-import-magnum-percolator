package org.yeastrc.emozi.xml.magnum.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.yeastrc.emozi.xml.magnum.objects.MagnumPSM;
import org.yeastrc.emozi.xml.magnum.objects.MagnumResults;

public class MagnumResultsReader_TestAddPSMToResults {

	MagnumResults magnumResults = null;
	Map<String, Integer> columnHeaderMap = null;
	
	@Before
	public void setUp() throws Throwable {
		magnumResults = new MagnumResults();
		
		String headerLine = "Scan Number	Ret Time	Obs Mass	Charge	PSM Mass	PPM Error	Score	dScore	e-value	Peptide #1 Score	Peptide #1	Linked AA #1	Protein #1	Protein #1 Site	Peptide #2 Score	Peptide #2	Linked AA #2	Protein #2	Protein #2 Site	Linker Mass";
		columnHeaderMap = MagnumResultsReader.getHeaderColumns( headerLine );

		
		{
			String PSMLine = "783	2.9861	1248.6122	3	1248.6122	0.0000	0.8050	0.0900	9.963e+000	0.8050	YAC[71]TVNSAGHR	3	random_seq_187	163";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			MagnumResultsReader.addPSMToMagnumResults( psm,  magnumResults );
		}
		
		
		{
			String PSMLine = "823	3.1215	1165.4864	2	1165.4864	0.0000	1.7300	0.0050	3.531e-006	1.7300	C[114]CTKPESER	1	BSA	436";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			MagnumResultsReader.addPSMToMagnumResults( psm,  magnumResults );
		}
		
		
		{
			String PSMLine = "880	3.3035	1187.4721	2	1187.4721	0.0000	0.3300	0.0050	1.100e+001	0.3300	SEKRMMAC[77]R	8	random_seq_198	38";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			MagnumResultsReader.addPSMToMagnumResults( psm,  magnumResults );
		}
		
		{
			String PSMLine = "9833	4.3035	2187.4721	3	1987.4721	3.3300	1.3300	2.0050	1.700e+001	0.5300	SEKRMMAC[77]R	8	random_seq_198	38";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			MagnumResultsReader.addPSMToMagnumResults( psm,  magnumResults );
		}
		
	}
	
	@Test
	public void test_getMagnumPSM() throws Throwable {

		Map<String, Map<Integer,MagnumPSM>> testMap = new HashMap<>();
		
		testMap.put( "YAC[71]TVNSAGHR", new HashMap<>() );
		testMap.put( "C[114]CTKPESER", new HashMap<>() );
		testMap.put( "SEKRMMAC[77]R", new HashMap<>() );
		
		{
			String PSMLine = "783	2.9861	1248.6122	3	1248.6122	0.0000	0.8050	0.0900	9.963e+000	0.8050	YAC[71]TVNSAGHR	3	random_seq_187	163";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			testMap.get( "YAC[71]TVNSAGHR" ).put( 783, psm );
		}
		

		{
			String PSMLine = "823	3.1215	1165.4864	2	1165.4864	0.0000	1.7300	0.0050	3.531e-006	1.7300	C[114]CTKPESER	1	BSA	436";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			testMap.get( "C[114]CTKPESER" ).put( 823, psm );
		}
		
		
		{
			String PSMLine = "880	3.3035	1187.4721	2	1187.4721	0.0000	0.3300	0.0050	1.100e+001	0.3300	SEKRMMAC[77]R	8	random_seq_198	38";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			testMap.get( "SEKRMMAC[77]R" ).put( 880, psm );
		}
		
		{
			String PSMLine = "9833	4.3035	2187.4721	3	1987.4721	3.3300	1.3300	2.0050	1.700e+001	0.5300	SEKRMMAC[77]R	8	random_seq_198	38";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			testMap.get( "SEKRMMAC[77]R" ).put( 9833, psm );
		}
		
		assertEquals( testMap, magnumResults.getReportedPeptidePSMMap() );
		
		
		
		{
			String PSMLine = "880	3.3035	1187.4721	2	1187.4721	0.0000	0.3300	0.0050	1.100e+001	0.3300	SEKRMMAC[77]R	8	random_seq_198	38";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			try {
				MagnumResultsReader.addPSMToMagnumResults( psm,  magnumResults );
				fail( "Should have failed." );
			} catch( Exception e ) { ; }
		}
		
	}
	
}

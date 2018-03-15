package org.yeastrc.emozi.xml.magnum.reader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.yeastrc.emozi.xml.magnum.objects.MagnumPSM;

public class MagnumResultsReader_TestGetMagnumPSM {

	@Test
	public void test_getMagnumPSM() throws IOException {

		String headerLine = "Scan Number	Ret Time	Obs Mass	Charge	PSM Mass	PPM Error	Score	dScore	e-value	Peptide #1 Score	Peptide #1	Linked AA #1	Protein #1	Protein #1 Site	Peptide #2 Score	Peptide #2	Linked AA #2	Protein #2	Protein #2 Site	Linker Mass";
		Map<String, Integer> columnHeaderMap = MagnumResultsReader.getHeaderColumns( headerLine );
		
		{
			String PSMLine = "783	2.9861	1248.6122	3	1248.6122	0.0000	0.8050	0.0900	9.963e+000	0.8050	YAC[71]TVNSAGHR	3	random_seq_187	163";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			assertEquals( 783, psm.getScanNumber() );
			assertEquals( 3, psm.getCharge() );
			assertEquals( "YAC[71]TVNSAGHR", psm.getReportedPeptide() );
	
			assertEquals( 2.9861, psm.getRetentionTime(), 0.0001 );
			assertEquals( 1248.6122, psm.getObservedMass(), 0.0001 );
			assertEquals( 0.0000, psm.getPpmError(), 0.0001 );
			assertEquals( 0.8050, psm.getScore(), 0.0001 );
			assertEquals( 0.0900, psm.getdScore(), 0.0001 );
			assertEquals( 9.963, psm.geteValue(), 0.0001 );
		}
		
		
		{
			String PSMLine = "823	3.1215	1165.4864	2	1165.4864	0.0000	1.7300	0.0050	3.531e-006	1.7300	C[114]CTKPESER	1	BSA	436";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			assertEquals( 823, psm.getScanNumber() );
			assertEquals( 2, psm.getCharge() );
			assertEquals( "C[114]CTKPESER", psm.getReportedPeptide() );
	
			assertEquals( 3.1215, psm.getRetentionTime(), 0.0001 );
			assertEquals( 1165.4864, psm.getObservedMass(), 0.0001 );
			assertEquals( 0.0000, psm.getPpmError(), 0.0001 );
			assertEquals( 1.7300, psm.getScore(), 0.0001 );
			assertEquals( 0.0050, psm.getdScore(), 0.0001 );
			assertEquals( 0.000003531, psm.geteValue(), 0.000000001 );
		}
		
		
		{
			String PSMLine = "880	3.3035	1187.4721	2	1187.4721	0.0000	0.3300	0.0050	1.100e+001	0.3300	SEKRMMAC[77]R	8	random_seq_198	38";
			MagnumPSM psm = MagnumResultsReader.getMagnumPSM( PSMLine,  columnHeaderMap );
			
			assertEquals( 880, psm.getScanNumber() );
			assertEquals( 2, psm.getCharge() );
			assertEquals( "SEKRMMAC[77]R", psm.getReportedPeptide() );
	
			assertEquals( 3.3035, psm.getRetentionTime(), 0.0001 );
			assertEquals( 1187.4721, psm.getObservedMass(), 0.0001 );
			assertEquals( 0.0000, psm.getPpmError(), 0.0001 );
			assertEquals( 0.3300, psm.getScore(), 0.0001 );
			assertEquals( 0.0050, psm.getdScore(), 0.0001 );
			assertEquals( 11, psm.geteValue(), 0.000000001 );
		}
	}
}

package org.yeastrc.emozi.xml.magnum.reader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.yeastrc.emozi.xml.magnum.objects.MagnumPSM;

public class MagnumResultsReader_TestGetMagnumPSM {

	@Test
	public void test_getMagnumPSM() throws IOException {

		String headerLine = "Scan Number	Ret Time	Obs Mass	Charge	PSM Mass	PPM Error	Score	dScore	e-value	Peptide #1 Score	Peptide #1	Linked AA #1	Protein #1	Protein #1 Site	Peptide #2 Score	Peptide #2	Linked AA #2	Protein #2	Protein #2 Site	Linker Mass";
		Map<String, Integer> columnHeaderMap = MagnumResultsReader.getHeaderColumns( headerLine );
		
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
}

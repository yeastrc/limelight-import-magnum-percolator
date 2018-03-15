package org.yeastrc.emozi.xml.magnum.reader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MagnumResultsReader_TestGetHeaderColumns {

	@Test
	public void test_getMagnumVersion() throws IOException {

		String headerLine = "Scan Number	Ret Time	Obs Mass	Charge	PSM Mass	PPM Error	Score	dScore	e-value	Peptide #1 Score	Peptide #1	Linked AA #1	Protein #1	Protein #1 Site	Peptide #2 Score	Peptide #2	Linked AA #2	Protein #2	Protein #2 Site	Linker Mass";
		Map<String, Integer> columnHeaderMap = MagnumResultsReader.getHeaderColumns( headerLine );
		
		Map<String, Integer> testHeaderMap = new HashMap<>();
		
		testHeaderMap.put( "Scan Number", 0 );
		testHeaderMap.put( "Ret Time", 1 );
		testHeaderMap.put( "Obs Mass", 2 );
		testHeaderMap.put( "Charge", 3 );
		testHeaderMap.put( "PSM Mass", 4 );
		testHeaderMap.put( "PPM Error", 5 );
		testHeaderMap.put( "Score", 6 );
		testHeaderMap.put( "dScore", 7 );
		testHeaderMap.put( "e-value", 8 );
		testHeaderMap.put( "Peptide #1 Score", 9 );
		testHeaderMap.put( "Peptide #1", 10 );
		testHeaderMap.put( "Linked AA #1", 11 );
		testHeaderMap.put( "Protein #1", 12 );
		testHeaderMap.put( "Protein #1 Site", 13 );
		testHeaderMap.put( "Peptide #2 Score", 14 );
		testHeaderMap.put( "Peptide #2", 15 );
		testHeaderMap.put( "Linked AA #2", 16 );
		testHeaderMap.put( "Protein #2", 17 );
		testHeaderMap.put( "Protein #2 Site", 18 );
		testHeaderMap.put( "Linker Mass", 19 );
		
		
		assertEquals( testHeaderMap, columnHeaderMap );

	}
}

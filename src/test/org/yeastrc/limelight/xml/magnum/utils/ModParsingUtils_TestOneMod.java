package org.yeastrc.limelight.xml.magnum.utils;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ModParsingUtils_TestOneMod {

	private Map<Integer, BigDecimal> _MODS = null;
	private String _SEQUENCE = "PEPTIDE";
	
	@Before
	public void setUp() {

		_MODS = new HashMap<>();
		
		_MODS.put( 5, new BigDecimal( "38.538222" ) );
		
	}
	
	@Test
	public void test() throws Exception {

		assertEquals( "PEPTI[39]DE", ModParsingUtils.getRoundedReportedPeptideString( _SEQUENCE, _MODS ) );

	}
}

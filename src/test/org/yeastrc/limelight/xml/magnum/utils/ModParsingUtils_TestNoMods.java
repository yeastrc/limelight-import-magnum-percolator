package org.yeastrc.limelight.xml.magnum.utils;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ModParsingUtils_TestNoMods {

	private Map<Integer, BigDecimal> _MODS = null;
	private String _SEQUENCE = "PEPTIDE";
	
	@Before
	public void setUp() {

		_MODS = new HashMap<>();
	}
	
	@Test
	public void test() throws Exception {

		assertEquals( "PEPTIDE", ModParsingUtils.getRoundedReportedPeptideString( _SEQUENCE, _MODS ) );
		
		// if the mods are null
		assertEquals( "PEPTIDE", ModParsingUtils.getRoundedReportedPeptideString( _SEQUENCE, null ) );

	}
	
}

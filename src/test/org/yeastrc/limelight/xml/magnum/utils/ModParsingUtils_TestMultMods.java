package org.yeastrc.limelight.xml.magnum.utils;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ModParsingUtils_TestMultMods {

	private Map<Integer, BigDecimal> _MODS = null;
	private String _SEQUENCE = "PEPTIDE";
	
	@Before
	public void setUp() {

		_MODS = new HashMap<>();
		
		_MODS.put( 1, new BigDecimal( "17.33234" ) );
		_MODS.put( 5, new BigDecimal( "38.538222" ) );
		_MODS.put( 7, new BigDecimal( "-0.901199" ) );
		
	}
	
	@Test
	public void test() throws Exception {
		
		assertEquals( "P[17]EPTI[39]DE[-1]", ModParsingUtils.getRoundedReportedPeptideString( _SEQUENCE, _MODS ) );

	}
	
}

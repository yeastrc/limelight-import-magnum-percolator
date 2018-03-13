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

public class MagnamParamsReader_TestDynamicMods {

	private InputStream _IS = null;
	
	@Before
	public void setUp() {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "# Fixed modifications. Add as many as necessary.\n" );
		sb.append( "#\n" );
		sb.append( "\n" );
		sb.append( "#fixed_modification = C   57.0834846\n" );
		sb.append( "fixed_modification = C   47.032146\n" );
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
	public void testGetDynamicMods() throws IOException {

		Map<Character, Double> staticMods = MagnumParamsReader.getDynamicModsFromParamsFile( _IS );
		
		Map<Character, Double> testStaticMods = new HashMap<>();
		testStaticMods.put( 'C', 57.02146 );
		testStaticMods.put( 'L', 80.321146 );
		
		assertEquals( testStaticMods, staticMods );

	}
	
}

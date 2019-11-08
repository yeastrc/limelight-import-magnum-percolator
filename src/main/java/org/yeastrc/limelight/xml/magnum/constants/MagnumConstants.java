package org.yeastrc.limelight.xml.magnum.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MagnumConstants {

	/**
	 * A map of amino acid single letter codes to masses used by Kojak, as found in
	 * https://github.com/mhoopmann/kojak/blob/master/KIons.cpp
	 */
	public static final Map<String, BigDecimal> AA_MASS;
	
	static {
		
		AA_MASS = new HashMap<String, BigDecimal>();
		
		AA_MASS.put( "A", BigDecimal.valueOf( 71.0371103 ) );
		AA_MASS.put( "C", BigDecimal.valueOf( 103.0091803 ) );
		AA_MASS.put( "D", BigDecimal.valueOf( 115.0269385 ) );
		AA_MASS.put( "E", BigDecimal.valueOf( 129.0425877 ) );
		AA_MASS.put( "F", BigDecimal.valueOf( 147.0684087 ) );
		AA_MASS.put( "G", BigDecimal.valueOf( 57.0214611 ) );
		AA_MASS.put( "H", BigDecimal.valueOf( 137.0589059 ) );
		AA_MASS.put( "I", BigDecimal.valueOf( 113.0840579 ) );
		AA_MASS.put( "K", BigDecimal.valueOf( 128.0949557 ) );
		AA_MASS.put( "L", BigDecimal.valueOf( 113.0840579 ) );
		AA_MASS.put( "M", BigDecimal.valueOf( 131.0404787 ) );
		AA_MASS.put( "N", BigDecimal.valueOf( 114.0429222 ) );
		AA_MASS.put( "P", BigDecimal.valueOf( 97.0527595 ) );
		AA_MASS.put( "Q", BigDecimal.valueOf( 128.0585714 ) );
		AA_MASS.put( "R", BigDecimal.valueOf( 156.1011021 ) );
		AA_MASS.put( "S", BigDecimal.valueOf( 87.0320244 ) );
		AA_MASS.put( "T", BigDecimal.valueOf( 101.0476736 ) );
		AA_MASS.put( "V", BigDecimal.valueOf( 99.0684087 ) );
		AA_MASS.put( "W", BigDecimal.valueOf( 186.0793065 ) );
		AA_MASS.put( "Y", BigDecimal.valueOf( 163.0633228 ) );
		
	}
	
	
	public static final String PSM_SCORE_MAGNUM_SCORE = "magnum_score";
	public static final String PSM_SCORE_DELTA_SCORE = "delta_score";
	public static final String PSM_SCORE_PPM_ERROR = "ppm_error";
	public static final String PSM_SCORE_E_VALUE = "e_value";
	public static final String PSM_SCORE_REPORTER_IONS = "reporter_ions";

	
}

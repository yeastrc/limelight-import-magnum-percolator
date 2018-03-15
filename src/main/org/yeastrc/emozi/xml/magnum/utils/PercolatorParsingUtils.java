package org.yeastrc.emozi.xml.magnum.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PercolatorParsingUtils {

	/**
	 * Examples:
	 * T-46425-94.65
	 * T-44282-90.35-2
	 * @param scanId
	 * @return
	 */
	public static int getScanNumberFromScanId( String scanId ) {
		
		Pattern p = Pattern.compile( "^[TD]-(\\d+)-.+$" );
		Matcher m = p.matcher( scanId );

		if( !m.matches() ) {
			throw new IllegalArgumentException( "Scan id is not of the expected syntax. Got: " + scanId + ", expected something like: T-46425-94.6" );
		}
		
		return Integer.parseInt( m.group( 1 ) );
	}
	
}

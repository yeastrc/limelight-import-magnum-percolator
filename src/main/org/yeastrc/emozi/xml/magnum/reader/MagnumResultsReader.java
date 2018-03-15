package org.yeastrc.emozi.xml.magnum.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.emozi.xml.magnum.objects.MagnumPSM;
import org.yeastrc.emozi.xml.magnum.objects.MagnumResults;

public class MagnumResultsReader {

	public static MagnumResults getMagnumResults( File magnumFile ) throws Throwable {
		
	    try ( InputStream is = new FileInputStream( magnumFile ) ) {
	    	return getMagnumResults( is );
	    }
		
	}
	
	protected static MagnumResults getMagnumResults( InputStream is ) throws Throwable {
		
		MagnumResults magnumResults = new MagnumResults();
		
		try( BufferedReader br = new BufferedReader( new InputStreamReader( is ) ) ) {

			int lineNumber = 0;
			Map<String, Integer> headerColumns = null;		// store which columns contain which data
			
			
			// read each line
		    for (String line = br.readLine(); line != null; line = br.readLine()) {

		    	lineNumber++;
		    	
		    	if( lineNumber == 1 ) {  // assume first line contains magnum version
		    		
		    		magnumResults.setMagnumVersion( getMagnumVersion( line ) );

		    	} else if( lineNumber == 2 ) {	// assume second line contains headers
		    	
		    		headerColumns = getHeaderColumns( line );
		    		
		    	} else {		// actual PSM data!
		    	
		    		MagnumPSM magnumPSM = null;
		    		
		    		try { magnumPSM = getMagnumPSM( line, headerColumns ); }
		    		catch( Throwable t ) {
		    			System.err.println( "Error parsing PSM line in magnum output!" );
		    			System.err.println( "Error message: " + t.getMessage() );
		    			System.err.println( "Line: " + line );
		    			
		    			throw t;
		    		}

		    		
		    	}

		     }
			
			
		}
		
	}
	
	
	protected static MagnumPSM getMagnumPSM( String line, Map<String, Integer> headerColumns ) {
		
		String[] fields = line.split( "\t" );
		
		if( fields.length != headerColumns.size() )
			throw new IllegalArgumentException( "Number of fields did not match number of headers for line: " + line + ". Expected " + headerColumns.size() + " fields." );

		
		MagnumPSM psm = new MagnumPSM();
		
		psm.setdScore( Double.parseDouble( fields[ headerColumns.get( "dScore" ) ] ) );
		
		
		return psm;
	}
	
	
	/**
	 * Get the column (starting at 0) for each column header and return the map
	 * of column header to column location
	 * 
	 * @param line
	 * @return
	 */
	protected static Map<String, Integer> getHeaderColumns( String line ) {
		
		Map<String, Integer> columnHeaderMap = new HashMap<>();
		
		String[] columnHeaders = line.split( "\t" );
		for( int i = 0; i < columnHeaders.length; i++ ) {
			columnHeaderMap.put( columnHeaders[ i ], i );
		}
		
		return columnHeaderMap;
	}
	
	/**
	 * Get magnum version from line 
	 * 
	 * @param line
	 * @return
	 */
	protected static String getMagnumVersion( String line ) {
		
		if( !line.startsWith( "Magnum version" ) )
			throw new IllegalArgumentException( "Invalid syntax for magnum results header line. Got: " + line );
		
		String[] fields = line.split( " " );
		
		if( fields.length != 3 )
			throw new IllegalArgumentException( "Invalid syntax for magnum results header line. Got: " + line );

		if( fields[ 2 ].length() < 1 )
			throw new IllegalArgumentException( "Invalid syntax for magnum results header line. Got: " + line );

		
		return fields[ 2 ];		
	}
	
}

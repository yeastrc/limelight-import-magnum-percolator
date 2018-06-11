/*
 * Original author: Michael Riffle <mriffle .at. uw.edu>
 *                  
 * Copyright 2018 University of Washington - Seattle, WA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yeastrc.limelight.xml.magnum.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.limelight.xml.magnum.objects.MagnumPSM;
import org.yeastrc.limelight.xml.magnum.objects.MagnumResults;

public class MagnumTextResultsReader {

	/**
	 * Get the parsed magnum results for the given magnum txt data file
	 * 
	 * @param magnumFile
	 * @return
	 * @throws Throwable
	 */
	public static MagnumResults getMagnumResults( File magnumFile ) throws Throwable {
		
	    try ( InputStream is = new FileInputStream( magnumFile ) ) {
	    	return getMagnumResults( is );
	    }
		
	}

	/**
	 * Get the parsed magnum results for the given inputstream
	 * 
	 * @param is
	 * @return
	 * @throws Throwable
	 */
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
		    		
		    		addPSMToMagnumResults( magnumPSM, magnumResults );
		    	}

		     }
			
			
		}
		
		return magnumResults;
	}

	/**
	 * Add the given psm to the given magnum results
	 * 
	 * @param psm
	 * @param results
	 * @throws Throwable
	 */
	protected static void addPSMToMagnumResults( MagnumPSM psm, MagnumResults results ) throws Throwable {
		
		if( results.getReportedPeptidePSMMap() == null )
			results.setReportedPeptidePSMMap( new HashMap<>() );		

		Map<String, Map<Integer, MagnumPSM>> peptidePsmMap = results.getReportedPeptidePSMMap();

		if( !peptidePsmMap.containsKey( psm.getReportedPeptide() ) )
			peptidePsmMap.put( psm.getReportedPeptide(), new HashMap<>() );
		
		Map<Integer, MagnumPSM> scanMapForReportedPeptide = peptidePsmMap.get( psm.getReportedPeptide() );
		
		if( scanMapForReportedPeptide.containsKey( psm.getScanNumber() ) )
			throw new Exception( "Found more than one instance of the same scan matching the same"
					+ " reported peptide. Scan number: " + psm.getScanNumber()
					+ ", peptide: " + psm.getReportedPeptide() );
		
		scanMapForReportedPeptide.put( psm.getScanNumber(), psm );
	}
	
	/**
	 * Get a MagnumPSM which is parsed from the given line of text reported by magnum, using
	 * the supplied header columns.
	 * 
	 * @param line
	 * @param headerColumns
	 * @return
	 */
	protected static MagnumPSM getMagnumPSM( String line, Map<String, Integer> headerColumns ) {
		
		String[] fields = line.split( "\t" );
		
		MagnumPSM psm = new MagnumPSM();
		
		psm.setdScore( Double.parseDouble( fields[ headerColumns.get( "dScore" ) ] ) );
		psm.seteValue( Double.parseDouble( fields[ headerColumns.get( "e-value" ) ] ) );
		psm.setObservedMass( Double.parseDouble( fields[ headerColumns.get( "Obs Mass" ) ] ) );
		psm.setPpmError( Double.parseDouble( fields[ headerColumns.get( "PPM Error" ) ] ) );
		psm.setReportedPeptide( fields[ headerColumns.get( "Peptide #1" ) ] );
		psm.setRetentionTime( Double.parseDouble( fields[ headerColumns.get( "Ret Time" ) ] ) );
		psm.setScanNumber( Integer.parseInt( fields[ headerColumns.get( "Scan Number" ) ] ) );
		psm.setScore( Double.parseDouble( fields[ headerColumns.get( "Score" ) ] ) );
		psm.setCharge( Integer.parseInt( fields[ headerColumns.get( "Charge" ) ] ) );

		
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

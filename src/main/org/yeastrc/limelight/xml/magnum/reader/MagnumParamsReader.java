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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yeastrc.limelight.xml.magnum.objects.MagnumParameters;

public class MagnumParamsReader {

	/**
	 * Get the relevant parameters from the magnum params file
	 * 
	 * @param paramsFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static MagnumParameters getMagnumParameters( File paramsFile ) throws FileNotFoundException, IOException {
		
		MagnumParameters magParams = new MagnumParameters();
		
		try ( InputStream is = new FileInputStream( paramsFile ) ) {
			magParams.setStaticMods( getStaticModsFromParamsFile( is ) );
		}
		
		
		return magParams;
	}
	
	
	/**
	 * 
	 * Example line: modification = C   57.02146 #foo comments
	 * 
	 * @param paramsInputStream
	 * @return
	 * @throws IOException
	 */
	public static Map<Character, Double> getStaticModsFromParamsFile( InputStream paramsInputStream ) throws IOException {
		
		Map<Character, Double> staticMods = new HashMap<>();
		
		Pattern p = Pattern.compile( "^fixed_modification\\s+=\\s+([A-Z])\\s+([0-9]+(\\.[0-9]+)?).*$" );
		
	    try (BufferedReader br = new BufferedReader( new InputStreamReader( paramsInputStream ) ) ) {
	    	
			for ( String line = br.readLine(); line != null; line = br.readLine() ) {		

				// skip immediately if it's not a line we want
				if( !line.startsWith( "fixed_modification" ) )
						continue;
				
				Matcher m = p.matcher( line );
				if( m.matches() ) {
					char residue = m.group( 1 ).charAt( 0 );					
					double d = Double.valueOf( m.group( 2 ) );
					
					if( d >= 0.000001 )
						staticMods.put( residue, d );
				}
			}
	    	
	    }
		
		return staticMods;
	}
	
}

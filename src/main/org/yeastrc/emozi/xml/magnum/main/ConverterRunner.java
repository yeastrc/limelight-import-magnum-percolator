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

package org.yeastrc.emozi.xml.magnum.main;

import org.yeastrc.emozi.xml.magnum.objects.ConversionParameters;
import org.yeastrc.emozi.xml.magnum.objects.MagnumParameters;
import org.yeastrc.emozi.xml.magnum.objects.MagnumResults;
import org.yeastrc.emozi.xml.magnum.objects.PercolatorResults;
import org.yeastrc.emozi.xml.magnum.reader.MagnumParamsReader;
import org.yeastrc.emozi.xml.magnum.reader.MagnumResultsReader;
import org.yeastrc.emozi.xml.magnum.reader.PercolatorResultsReader;
import org.yeastrc.emozi.xml.magnum.utils.DataComparer;

public class ConverterRunner {

	// quickly get a new instance of this class
	public static ConverterRunner createInstance() { return new ConverterRunner(); }
	
	
	public void convertMagnumToEmoziXML( ConversionParameters conversionParameters ) throws Throwable {
	
		System.err.print( "Reading Magnum params into memory..." );
		MagnumParameters magParams = MagnumParamsReader.getMagnumParameters( conversionParameters.getMagnumParametersFile() );
		System.err.println( " Done." );
		
		
		System.err.print( "Reading Magnum data into memory..." );
		MagnumResults magnumResults = MagnumResultsReader.getMagnumResults( conversionParameters.getMagnumOutputFile() );
		System.err.println( " Done." );
		
		
		System.err.print( "Reading Percolator data into memory..." );
		PercolatorResults percolatorResults = PercolatorResultsReader.getPercolatorResults( conversionParameters.getPercolatorXMLOutputFile() );
		System.err.println( " Done." );
		
		
		System.err.print( "Ensuring Percolator and magnum data match up..." );
		DataComparer.compareData( magnumResults, percolatorResults );
		System.err.println( " Done." );
		
		
		
		
	}
	
	
}

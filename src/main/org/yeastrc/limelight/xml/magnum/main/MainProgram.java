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

package org.yeastrc.limelight.xml.magnum.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.yeastrc.limelight.xml.magnum.objects.ConversionParameters;
import org.yeastrc.limelight.xml.magnum.objects.ConversionProgramInfo;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;

public class MainProgram {

	public static void main( String[] args ) throws Throwable {
		
		if( args.length < 1 || args[ 0 ].equals( "-h" ) ) {
			printHelp();
			System.exit( 0 );
		}
		
		CmdLineParser cmdLineParser = new CmdLineParser();
        
		CmdLineParser.Option magnumParamOpt = cmdLineParser.addStringOption( 'c', "config" );	
		CmdLineParser.Option outfileOpt = cmdLineParser.addStringOption( 'o', "out" );	
		CmdLineParser.Option magnumDataOpt = cmdLineParser.addStringOption( 'm', "magnum" );	
		CmdLineParser.Option percolatorDataOpt = cmdLineParser.addStringOption( 'p', "percolator" );	
		CmdLineParser.Option fastaFileOpt = cmdLineParser.addStringOption( 'f', "fasta" );

        // parse command line options
        try { cmdLineParser.parse(args); }
        catch (IllegalOptionValueException e) {
        	printHelp();
            System.exit( 1 );
        }
        catch (UnknownOptionException e) {
           printHelp();
           System.exit( 1 );
        }
		
        String magnumParamFilePath = (String)cmdLineParser.getOptionValue( magnumParamOpt );
        String outFilePath = (String)cmdLineParser.getOptionValue( outfileOpt );
        String magnumDataFilePath = (String)cmdLineParser.getOptionValue( magnumDataOpt );
        String percolatorDataFilePath = (String)cmdLineParser.getOptionValue( percolatorDataOpt );
        String fastaFilePath = (String)cmdLineParser.getOptionValue( fastaFileOpt );
        
        File magnumParamFile = new File( magnumParamFilePath );
        if( !magnumParamFile.exists() ) {
        	System.err.println( "Could not find magnum params file: " + magnumParamFilePath );
        	System.exit( 1 );
        }
        
        File magnumDataFile = new File( magnumDataFilePath );
        if( !magnumDataFile.exists() ) {
        	System.err.println( "Could not find magnum data file: " + magnumDataFilePath );
        	System.exit( 1 );
        }
        
        File percolatorDataFile = new File( percolatorDataFilePath );
        if( !percolatorDataFile.exists() ) {
        	System.err.println( "Could not find percolator data file: " + percolatorDataFilePath );
        	System.exit( 1 );
        }
        
        File fastaFile = new File( fastaFilePath );
        if( !fastaFile.exists() ) {
        	System.err.println( "Could not find fasta file: " + fastaFilePath );
        	System.exit( 1 );
        }
        
        ConversionProgramInfo cpi = ConversionProgramInfo.createInstance( String.join( " ",  args ) );        
        
        ConversionParameters cp = new ConversionParameters();
        cp.setConversionProgramInfo( cpi );
        cp.setFastaFile( fastaFile );
        cp.setMagnumParametersFile( magnumParamFile );
        cp.setMagnumOutputFile( magnumDataFile );
        cp.setPercolatorXMLOutputFile( percolatorDataFile );
        cp.setLimelightXMLOutputFile( new File( outFilePath ) );
        
        ConverterRunner.createInstance().convertMagnumToLimelightXML( cp );
        System.exit( 0 );
	}
	
	/**
	 * Print help to STD OUT
	 */
	public static void printHelp() {
		
		try( BufferedReader br = new BufferedReader( new InputStreamReader( MainProgram.class.getResourceAsStream( "help.txt" ) ) ) ) {
			
			String line = null;
			while ( ( line = br.readLine() ) != null )
				System.out.println( line );				
			
		} catch ( Exception e ) {
			System.out.println( "Error printing help." );
		}
	}
	
	
}

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
import java.math.BigDecimal;

import org.yeastrc.limelight.xml.magnum.constants.Constants;
import org.yeastrc.limelight.xml.magnum.objects.ConversionParameters;
import org.yeastrc.limelight.xml.magnum.objects.ConversionProgramInfo;

import picocli.CommandLine;

@CommandLine.Command(name = "java -jar " + Constants.CONVERSION_PROGRAM_NAME,
		mixinStandardHelpOptions = true,
		version = Constants.CONVERSION_PROGRAM_NAME + " " + Constants.CONVERSION_PROGRAM_VERSION,
		sortOptions = false,
		synopsisHeading = "%n",
		descriptionHeading = "%n@|bold,underline Description:|@%n%n",
		optionListHeading = "%n@|bold,underline Options:|@%n",
		description = "Convert the results of a Magnum + Percolator analysis to a Limelight XML file suitable for import into Limelight.\n\n" +
				"More info at: " + Constants.CONVERSION_PROGRAM_URI
)
public class MainProgram implements Runnable {

	@CommandLine.Option(names = { "-m", "--magnum" }, required = true, description = "Full path to the PepXML file generated by Magnum. This may be repeated if there are multiple Magnum data files for a single percolator run.")
	private File[] magnumFiles;

	@CommandLine.Option(names = { "-p", "--percolator" }, required = false, description = "Full path to the XML file generated by Percolator. Do not include this parameter if --open-mods-separate is set.")
	private File percolatorFile;

	@CommandLine.Option(names = { "--open-mods-separate" }, required = false, description = "Include this parameter if Magnum was run to output Percolator input files separately for open mod PSMs and standard PSMs.")
	private Boolean openModsSeparate = false;

	@CommandLine.Option(names = { "--open-mods-percolator" }, required = false, description = "If --open-mods-separate is set, this is the full path to the XML file generated by Percolator containing the open mod hits.")
	private File openModPercolatorFile;

	@CommandLine.Option(names = { "--standard-percolator" }, required = false, description = "If --open-mods-separate is set, this is the full path to the XML file generated by Percolator containing the standard hits.")
	private File standardPercolatorFile;

	@CommandLine.Option(names = { "-f", "--fasta-file" }, required = true, description = "Full path to FASTA file used in the experiment.")
	private File fastaFile;

	@CommandLine.Option(names = { "-d", "--import-decoys" }, required = false, description = "(Optional) If this parameter is set, decoys will be imported. Note, percolator must be run with -Z to output decoys.")
	private boolean importDecoys;

	@CommandLine.Option(names = { "-i", "--independent-decoy-prefix" }, required = false, description = "If present, any hits to proteins that begin with this string will be considered \"independent decoys,\" for the purpose of error estimation. See: https://pubmed.ncbi.nlm.nih.gov/21876204/")
	private String independentDecoyPrefix;;

	@CommandLine.Option(names = { "-q", "--q-value" }, required = false, description = "(Optional) Override the default q-value cutoff to this value.")
	private BigDecimal qValueOverride;

	@CommandLine.Option(names = { "-c", "--conf" },  required = true, description = "Full path to the Magnum params file. May be repeated if there are multiple conf files.")
	private File[] confFiles;

	@CommandLine.Option(names = { "-o", "--out-file" }, required = true, description = "Full path to use for the Limelight XML output file (including file name).")
	private File outFile;

	@CommandLine.Option(names = { "-v", "--verbose" }, required = false, description = "If this parameter is present, error messages will include a full stacktrace.")
	private boolean verboseRequested = false;

	private String[] args;

	public void run() {
		
		printRuntimeInfo();

		for(File confFile : confFiles) {
			if (!confFile.exists()) {
				System.err.println("Could not find magnum params file: " + confFile.getAbsolutePath());
				System.exit(1);
			}
		}

        for(File magnumFile : magnumFiles) {
			if (!magnumFile.exists()) {
				System.err.println("Could not find magnum data file: " + magnumFile.getAbsolutePath());
				System.exit(1);
			}
		}

        if( openModsSeparate ) {
			if(openModPercolatorFile == null) {
				System.err.println( "Please specify an open mod percolator output file." );
				System.exit( 1 );
			}

			if(standardPercolatorFile == null) {
				System.err.println( "Please specify a standard percolator output file." );
				System.exit( 1 );
			}

			if( !openModPercolatorFile.exists() ) {
				System.err.println( "Could not find open mods percolator data file: " + openModPercolatorFile.getAbsolutePath() );
				System.exit( 1 );
			}

			if( !standardPercolatorFile.exists() ) {
				System.err.println( "Could not find standard percolator data file: " + standardPercolatorFile.getAbsolutePath() );
				System.exit( 1 );
			}
		} else {
        	if(percolatorFile == null) {
				System.err.println( "Please specify a percolator output file." );
				System.exit( 1 );
        	}

			if( !percolatorFile.exists() ) {
				System.err.println( "Could not find percolator data file: " + percolatorFile.getAbsolutePath() );
				System.exit( 1 );
			}
		}

        
        if( !fastaFile.exists() ) {
        	System.err.println( "Could not find fasta file: " + fastaFile.getAbsolutePath() );
        	System.exit( 1 );
        }
        
        ConversionProgramInfo cpi = ConversionProgramInfo.createInstance( String.join( " ",  args ) );        
        
        ConversionParameters cp = new ConversionParameters();
        cp.setConversionProgramInfo( cpi );
        cp.setFastaFile( fastaFile );
        cp.setMagnumParametersFiles( confFiles );
        cp.setMagnumOutputFiles( magnumFiles );
        cp.setPercolatorXMLOutputFile( percolatorFile );
        cp.setLimelightXMLOutputFile( outFile );
        cp.setqValueOverride( qValueOverride );
        cp.setOpenModsSeparate(openModsSeparate);
        cp.setOpenModsPercolatorXMLOutputFile(openModPercolatorFile);
        cp.setStandardPercolatorXMLOutputFile(standardPercolatorFile);
		cp.setImportDecoys(importDecoys);
		cp.setIndependentDecoyPrefix(independentDecoyPrefix);

        try {
			ConverterRunner.createInstance().convertMagnumToLimelightXML(cp);
		} catch (Throwable t) {

        	if( verboseRequested ) {
        		t.printStackTrace();
			}

        	System.err.println( t.getMessage() );
        	System.exit( 1 );
		}

        System.exit( 0 );
	}

	public static void main( String[] args ) {

		MainProgram mp = new MainProgram();
		mp.args = args;

		CommandLine.run(mp, args);
	}
	
	/**
	 * Print runtime info to STD ERR
	 * @throws Exception 
	 */
	public void printRuntimeInfo() {

		try( BufferedReader br = new BufferedReader( new InputStreamReader( MainProgram.class.getResourceAsStream( "run.txt" ) ) ) ) {

			String line = null;
			while ( ( line = br.readLine() ) != null ) {

				line = line.replace( "{{URL}}", Constants.CONVERSION_PROGRAM_URI );
				line = line.replace( "{{VERSION}}", Constants.CONVERSION_PROGRAM_VERSION );

				System.err.println( line );
				
			}
			
			System.err.println( "" );

		} catch ( Exception e ) {
			System.out.println( "Error printing runtime information." );
		}
	}
	
}

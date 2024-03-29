package org.yeastrc.limelight.xml.magnum.builder;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

import org.yeastrc.limelight.limelight_import.api.xml_dto.LimelightInput;
import org.yeastrc.limelight.limelight_import.api.xml_dto.MatchedProtein;
import org.yeastrc.limelight.limelight_import.api.xml_dto.MatchedProteinLabel;
import org.yeastrc.limelight.limelight_import.api.xml_dto.MatchedProteins;
import org.yeastrc.limelight.xml.magnum.utils.ReportedPeptideParsingUtils;

import org.yeastrc.proteomics.fasta.*;

/**
 * Build the MatchedProteins section of the limelight XML docs. This is done by finding all proteins in the FASTA
 * file that contains any of the peptide sequences found in the experiment. 
 * 
 * This is generalized enough to be usable by any pipeline
 * 
 * @author mriffle
 *
 */
public class MatchedProteinsBuilder {

	public static MatchedProteinsBuilder getInstance() { return new MatchedProteinsBuilder(); }

	/**
	 * Add all target proteins from the FASTA file that contain any of the peptides found in the experiment
	 * to the limelight xml document in the matched proteins section.
	 * @param limelightInputRoot
	 * @param fastaFile
	 * @param reportedPeptides
	 * @param decoyPrefix
	 * @throws Exception
	 */
	public void buildMatchedProteins( LimelightInput limelightInputRoot, File fastaFile, Collection<String> reportedPeptides, boolean importDecoys, String decoyPrefix, boolean importIndependentDecoys, String independentDecoyPrefix ) throws Exception {


		System.err.print( " Matching peptides to proteins..." );

		// process the reported peptides to get naked peptide objects
		Collection<PeptideObject> nakedPeptideObjects = getNakedPeptideObjectsForPercolatorReportedPeptides( reportedPeptides );
		
		// find the proteins matched by any of these peptides
		Map<String, Collection<FastaProteinAnnotation>> proteins = getProteins( nakedPeptideObjects, fastaFile );
		
		// create the XML and add to root element
		buildAndAddMatchedProteinsToXML( limelightInputRoot, proteins, importDecoys, decoyPrefix, importIndependentDecoys, independentDecoyPrefix );
		
	}
	
	
	private Collection<PeptideObject> getNakedPeptideObjectsForPercolatorReportedPeptides( Collection< String > percolatorPeptides ) {
		
		Collection<PeptideObject> nakedPeptideObjects = new HashSet<>();
		
		for( String reportedPeptide : percolatorPeptides ) {
			
			PeptideObject nakedPeptideObject = new PeptideObject();
			nakedPeptideObject.setFoundMatchingProtein( false );
			nakedPeptideObject.setPeptideSequence( ReportedPeptideParsingUtils.parsePeptide( reportedPeptide ).getNakedSequence() );
			
			nakedPeptideObjects.add( nakedPeptideObject );
		}
		
		
		return nakedPeptideObjects;
	}
	
	/* ***************** REST OF THIS CAN BE MOVED TO CENTRALIZED LIB **************************** */
	
	/**
	 * Do the work of building the matched peptides element and adding to limelight xml root
	 * 
	 * @param limelightInputRoot
	 * @param proteins
	 * @throws Exception
	 */
	private void buildAndAddMatchedProteinsToXML( LimelightInput limelightInputRoot, Map<String, Collection<FastaProteinAnnotation>> proteins, boolean importDecoys, String decoyPrefix, boolean importIndependentDecoys, String independentDecoyPrefix ) throws Exception {
		
		MatchedProteins xmlMatchedProteins = new MatchedProteins();
		limelightInputRoot.setMatchedProteins( xmlMatchedProteins );
		
		for( String sequence : proteins.keySet() ) {
			
			if( proteins.get( sequence ).isEmpty() ) continue;

			// don't import this protein at all if it's a decoy and we're not importing decoys
			if(!importDecoys && isProteinDecoy(proteins.get( sequence ), decoyPrefix)) {
				continue;
			}

			MatchedProtein xmlProtein = new MatchedProtein();
        	xmlMatchedProteins.getMatchedProtein().add( xmlProtein );
        	
        	xmlProtein.setSequence( sequence );

			if(importDecoys) {
				if(isProteinDecoy(proteins.get( sequence ), decoyPrefix)) {
					xmlProtein.setIsDecoy(true);
				} else {
					xmlProtein.setIsDecoy(false);
				}
			}

			if(importIndependentDecoys) {
				if(isProteinIndependentDecoy(proteins.get( sequence ), independentDecoyPrefix)) {
					xmlProtein.setIsIndependentDecoy(true);
				} else {
					xmlProtein.setIsIndependentDecoy(false);
				}
			}

        	for( FastaProteinAnnotation anno : proteins.get( sequence ) ) {

				// do not add decoy annotations if we are not importing decoys
				if(!importDecoys && anno.getName().startsWith(decoyPrefix)) {
					continue;
				}

        		MatchedProteinLabel xmlMatchedProteinLabel = new MatchedProteinLabel();
        		xmlProtein.getMatchedProteinLabel().add( xmlMatchedProteinLabel );
        		
        		xmlMatchedProteinLabel.setName( anno.getName() );
        		
        		if( anno.getDescription() != null )
        			xmlMatchedProteinLabel.setDescription( anno.getDescription() );
        			
        		if( anno.getTaxonomId() != null )
        			xmlMatchedProteinLabel.setNcbiTaxonomyId( new BigInteger( anno.getTaxonomId().toString() ) );
        	}
		}
	}

	/**
	 * Return true if all fasta annotation protein names start with decoy prefix
	 *
	 * @param proteins
	 * @param decoyPrefix
	 * @return
	 */
	private boolean isProteinDecoy(Collection<FastaProteinAnnotation> proteins, String decoyPrefix) {

		if(decoyPrefix == null) { return false; }

		for(FastaProteinAnnotation anno : proteins) {
			if(!(anno.getName().startsWith(decoyPrefix))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return true if all fasta annotation protein names start with independent decoy prefix
	 *
	 * @param proteins
	 * @param independentDecoyPrefix
	 * @return
	 */
	private boolean isProteinIndependentDecoy(Collection<FastaProteinAnnotation> proteins, String independentDecoyPrefix) {

		if(independentDecoyPrefix == null) { return false; }

		for(FastaProteinAnnotation anno : proteins) {
			if(!(anno.getName().startsWith(independentDecoyPrefix))) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Get a map of the distinct target protein sequences mapped to a collection of target annotations for that sequence
	 * from the given fasta file, where the sequence contains any of the supplied peptide sequences
	 * @param nakedPeptideObjects
	 * @param fastaFile
	 * @return
	 * @throws Exception
	 */
	private Map<String, Collection<FastaProteinAnnotation>> getProteins( Collection<PeptideObject> nakedPeptideObjects, File fastaFile ) throws Exception {
		
		Map<String, Collection<FastaProteinAnnotation>> proteinAnnotations = new HashMap<>();


		try ( FASTAFileParser parser = FASTAFileParserFactory.getInstance().getFASTAFileParser( fastaFile ) ) {
			
			int count = 0;
			System.err.println( "" );

			//for( FASTAEntry entry = fastaReader.readNext(); entry != null; entry = fastaReader.readNext() ) {
			for ( FASTAEntry entry = parser.getNextEntry(); entry != null; entry = parser.getNextEntry() ) {
				count++;
				boolean foundPeptideForFASTAEntry = false;
				
				// use this sequence to determine if it contains a peptide sequence
				String fastaSequence = entry.getSequence().replaceAll( "L", "I" );
				
				System.err.print( "\tTested " + count + " FASTA entries...\r" );
				
				for( PeptideObject nakedPeptideObject : nakedPeptideObjects ) {
					
					// optimization: if we already know we're including this protein and
					// we have already mapped this peptide to any protein, we can skip
					// this peptide...
					if( foundPeptideForFASTAEntry && nakedPeptideObject.foundMatchingProtein ) {
						continue;
					}
					
					if( nakedPeptideObject.getSearchSequence() == null ) {
						nakedPeptideObject.setSearchSequence( nakedPeptideObject.getPeptideSequence().replaceAll( "L", "I" ) );
					}
					
					String peptideSearchSequence = nakedPeptideObject.getSearchSequence();
					
					
					if( fastaSequence.contains( peptideSearchSequence ) ) {
						
						// this protein has a matching peptide
						
						if( !foundPeptideForFASTAEntry ) {
							for( FASTAHeader header : entry.getHeaders() ) {
								
								if( !proteinAnnotations.containsKey( entry.getSequence() ) )
									proteinAnnotations.put( entry.getSequence(), new HashSet<FastaProteinAnnotation>() );
								
								FastaProteinAnnotation anno = new FastaProteinAnnotation();
								anno.setName( header.getName() );
								anno.setDescription( header.getDescription() );
			            		
								proteinAnnotations.get( entry.getSequence() ).add( anno );
	
							}//end iterating over fasta headers
							
							foundPeptideForFASTAEntry = true;
						}
						
						nakedPeptideObject.setFoundMatchingProtein( true );
						
					} // end if statement for protein containing peptide

				} // end iterating over peptide sequences
				
			}// end iterating over fasta entries
			
			if( nakedPeptideObjectsContainsUnmatchedPeptides( nakedPeptideObjects ) ) {
				System.err.println( "\nError: Not all peptides in the results could be matched to a protein in the FASTA file." );
				System.err.println( "\tUnmatched peptides:" );
				for( PeptideObject nakedPeptideObject : nakedPeptideObjects ) {
					if( !nakedPeptideObject.isFoundMatchingProtein() ) {
						System.err.println( nakedPeptideObject.getPeptideSequence() );
					}
				}
				
				throw new Exception( "Could not match all peptides to a protein in the FASTA file." );
			}
			
			System.err.print( "\n" );
		}
		
		return proteinAnnotations;
	}
	
	private boolean nakedPeptideObjectsContainsUnmatchedPeptides( Collection<PeptideObject> nakedPeptideObjects ) {
		
		for( PeptideObject nakedPeptideObject : nakedPeptideObjects ) {
			if( !nakedPeptideObject.isFoundMatchingProtein() ) {
				return true;
			}
		}
		
		return false;
	}
	
	private class PeptideObject {
		
		private String peptideSequence;
		private String searchSequence;
		private boolean foundMatchingProtein;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((peptideSequence == null) ? 0 : peptideSequence.hashCode());
			return result;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof PeptideObject))
				return false;
			PeptideObject other = (PeptideObject) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (peptideSequence == null) {
				if (other.peptideSequence != null)
					return false;
			} else if (!peptideSequence.equals(other.peptideSequence))
				return false;
			return true;
		}
		
		private MatchedProteinsBuilder getOuterType() {
			return MatchedProteinsBuilder.this;
		}

		/**
		 * @return the peptideSequence
		 */
		public String getPeptideSequence() {
			return peptideSequence;
		}

		/**
		 * @param peptideSequence the peptideSequence to set
		 */
		public void setPeptideSequence(String peptideSequence) {
			this.peptideSequence = peptideSequence;
		}

		/**
		 * @return the foundMatchingProtein
		 */
		public boolean isFoundMatchingProtein() {
			return foundMatchingProtein;
		}

		/**
		 * @param foundMatchingProtein the foundMatchingProtein to set
		 */
		public void setFoundMatchingProtein(boolean foundMatchingProtein) {
			this.foundMatchingProtein = foundMatchingProtein;
		}

		/**
		 * @return the searchSequence
		 */
		public String getSearchSequence() {
			return searchSequence;
		}

		/**
		 * @param searchSequence the searchSequence to set
		 */
		public void setSearchSequence(String searchSequence) {
			this.searchSequence = searchSequence;
		}		
		
	}
	
	
	/**
	 * An annotation for a protein in a Fasta file
	 * 
	 * @author mriffle
	 *
	 */
	private class FastaProteinAnnotation {

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			FastaProteinAnnotation that = (FastaProteinAnnotation) o;
			return name.equals(that.name) &&
					Objects.equals(description, that.description) &&
					Objects.equals(taxonomId, that.taxonomId);
		}

		@Override
		public int hashCode() {
			return Objects.hash(name, description, taxonomId);
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Integer getTaxonomId() {
			return taxonomId;
		}
		public void setTaxonomId(Integer taxonomId) {
			this.taxonomId = taxonomId;
		}

		
		
		private String name;
		private String description;
		private Integer taxonomId;
		private MatchedProteinsBuilder getOuterType() {
			return MatchedProteinsBuilder.this;
		}
		
	}

	
}

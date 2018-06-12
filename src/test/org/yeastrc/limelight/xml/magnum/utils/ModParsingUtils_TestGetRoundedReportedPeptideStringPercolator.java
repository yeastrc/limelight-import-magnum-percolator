package org.yeastrc.limelight.xml.magnum.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yeastrc.limelight.xml.magnum.objects.PercolatorPeptide;

public class ModParsingUtils_TestGetRoundedReportedPeptideStringPercolator {

	@Test
	public void test() {

		{
			PercolatorPeptide rp = new PercolatorPeptide();
			rp.setReportedPeptide( "PEPT[45.08]IDE");
			
			assertEquals( "PEPT[45]IDE", ModParsingUtils.getRoundedReportedPeptideString( rp ) );
		}
		
		{
			PercolatorPeptide rp = new PercolatorPeptide();
			rp.setReportedPeptide( "PEPT[-45.08]IDE");
			
			assertEquals( "PEPT[-45]IDE", ModParsingUtils.getRoundedReportedPeptideString( rp ) );
		}
		
		{
			PercolatorPeptide rp = new PercolatorPeptide();
			rp.setReportedPeptide( "PEPT[45.58]IDE");
			
			assertEquals( "PEPT[46]IDE", ModParsingUtils.getRoundedReportedPeptideString( rp ) );
		}
		
		{
			PercolatorPeptide rp = new PercolatorPeptide();
			rp.setReportedPeptide( "PEPT[45.58]ID[45.58]E");
			
			assertEquals( "PEPT[46]ID[46]E", ModParsingUtils.getRoundedReportedPeptideString( rp ) );
		}
		
		{
			PercolatorPeptide rp = new PercolatorPeptide();
			rp.setReportedPeptide( "PEPT[45.58]ID[43.38]E");
			
			assertEquals( "PEPT[46]ID[43]E", ModParsingUtils.getRoundedReportedPeptideString( rp ) );
		}
		
		{
			PercolatorPeptide rp = new PercolatorPeptide();
			rp.setReportedPeptide( "PEPTIDE");
			
			assertEquals( "PEPTIDE", ModParsingUtils.getRoundedReportedPeptideString( rp ) );
		}
		
	}
	
}

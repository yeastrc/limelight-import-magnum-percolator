package org.yeastrc.limelight.xml.magnum.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yeastrc.limelight.xml.magnum.objects.MagnumPSM;
import org.yeastrc.limelight.xml.magnum.objects.PercolatorPeptide;

public class ModParsingUtils {

	public static String getRoundedReportedPeptideString( MagnumPSM psm ) {
		
		System.out.println( psm.getModifications().size() );
		
		if( psm.getModifications() == null && psm.getModifications().size() < 1 )
			return psm.getPeptideSequence();
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < psm.getPeptideSequence().length(); i++){
		    String r = String.valueOf( psm.getPeptideSequence().charAt(i) );
		    sb.append( r );
		    
		    if( psm.getModifications().containsKey( i + 1 ) ) {

		    	double mass = psm.getModifications().get( i + 1 );
		    	
		    	sb.append( "[" );
		    	sb.append( Math.toIntExact( Math.round( mass ) ) );		    			    	
		    	sb.append( "]" );
		    	
		    }
		}
		
		System.out.println( sb.toString() );
		
		return sb.toString();
	}
	
	public static String getRoundedReportedPeptideString( PercolatorPeptide peptide ) {
		
		String sequence = peptide.getReportedPeptide();
		
		Pattern p = Pattern.compile( "(\\-)?([0-9]+(\\.[0-9]+))" );
		Matcher m = p.matcher( sequence );
		
		while( m.find() ) {
				
			String n = m.group();				
			sequence = sequence.replace( n, String.valueOf( Math.round( Double.parseDouble( n ) ) ) );
				
		}
				
		return sequence;
	}
	
}

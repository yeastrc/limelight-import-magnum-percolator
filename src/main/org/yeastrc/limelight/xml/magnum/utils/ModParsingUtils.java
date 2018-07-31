package org.yeastrc.limelight.xml.magnum.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.yeastrc.limelight.xml.magnum.objects.MagnumPSM;

public class ModParsingUtils {

	public static String getRoundedReportedPeptideString( MagnumPSM psm ) {
				
		if( psm.getModifications() == null && psm.getModifications().size() < 1 )
			return psm.getPeptideSequence();
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < psm.getPeptideSequence().length(); i++){
		    String r = String.valueOf( psm.getPeptideSequence().charAt(i) );
		    sb.append( r );
		    
		    if( psm.getModifications().containsKey( i + 1 ) ) {

		    	BigDecimal mass = psm.getModifications().get( i + 1 );
		    	
		    	sb.append( "[" );
		    	sb.append( mass.setScale( 0, RoundingMode.HALF_UP ).toString() );
		    	sb.append( "]" );
		    	
		    	if( psm.getPeptideSequence().equals( "NDGGSPVTHYIVECLAWDPTGK" ) ) {
		    		System.out.println( mass );
		    		System.out.println( mass.setScale( 0, RoundingMode.HALF_UP ).toString() );
		    		System.out.println( psm );
		    		System.out.println( "" );
		    	}
		    	
		    }
		}
				
		return sb.toString();
	}

}

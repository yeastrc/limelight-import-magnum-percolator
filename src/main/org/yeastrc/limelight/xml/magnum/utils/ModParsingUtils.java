package org.yeastrc.limelight.xml.magnum.utils;

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

		    	double mass = psm.getModifications().get( i + 1 );
		    	
		    	sb.append( "[" );
		    	sb.append( Math.round( mass ) );
		    	sb.append( "]" );
		    	
		    }
		}
				
		return sb.toString();
	}

}

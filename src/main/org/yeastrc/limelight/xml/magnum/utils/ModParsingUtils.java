package org.yeastrc.limelight.xml.magnum.utils;

import org.yeastrc.limelight.xml.magnum.objects.MagnumParameters;

public class ModParsingUtils {

	/**
	 * If the given mod wasn't explicitly searched for in the given
	 * magnum parameters, we declare this as "predicted"
	 * 
	 * @param magnumParameters
	 * @param peptideSequence
	 * @param position
	 * @param modMass
	 * @return true if not explicitly searched for, false if it was explicitly searched for
	 */
	public static boolean isPredictedMod( MagnumParameters magnumParameters, String peptideSequence, int position, double modMass ) {
		
		// no mods searched for, must be predicted
		if( magnumParameters.getDynamicMods() == null )
			return true;
		
		// no mods searched for, must be predicted
		if( magnumParameters.getDynamicMods().keySet().size() < 1 )
			return true;
		

		char residue = peptideSequence.charAt( position - 1 );
		if( !magnumParameters.getDynamicMods().containsKey( residue ) )
			return true;
		
		// consider these to be the same if within 0.01
		if( Math.abs( modMass - magnumParameters.getDynamicMods().get( residue ) ) <= 0.01 ) {
			return false;
		}
		
		return true;
	}
	
	

}

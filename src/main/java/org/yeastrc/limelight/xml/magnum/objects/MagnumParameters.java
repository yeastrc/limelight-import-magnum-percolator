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

package org.yeastrc.limelight.xml.magnum.objects;

import java.util.Map;

public class MagnumParameters {

	@Override
	public String toString() {

		String str = "MagnumParameters: ";
		
		if( this.staticMods != null ) {
			str += " Static mods: [";
			for( char r : this.staticMods.keySet() ) {
				str += "(";
				str += r + "," + this.staticMods.get( r );
				str += ")";
			}
			str += "]";
		}
		
		if( this.decoyPrefix != null ) {
			str += " decoyPrefix:";
			str += this.getDecoyPrefix();
		} else {
			str += " decoyPrefix:null";
		}

		return str;
		
	}

	/**
	 * @return the staticMods
	 */
	public Map<Character, Double> getStaticMods() {
		return staticMods;
	}
	/**
	 * @param staticMods the staticMods to set
	 */
	public void setStaticMods(Map<Character, Double> staticMods) {
		this.staticMods = staticMods;
	}
	
	
	public String getDecoyPrefix() {
		return decoyPrefix;
	}

	public void setDecoyPrefix(String decoyPrefix) {
		this.decoyPrefix = decoyPrefix;
	}


	private Map<Character, Double> staticMods;
	private String decoyPrefix;
}

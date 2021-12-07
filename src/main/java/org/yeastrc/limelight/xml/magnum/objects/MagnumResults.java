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

import java.util.Collection;
import java.util.Map;

public class MagnumResults {
	
	/**
	 * @return the magnumVersion
	 */
	public String getMagnumVersion() {
		return magnumVersion;
	}
	/**
	 * @param magnumVersion the magnumVersion to set
	 */
	public void setMagnumVersion(String magnumVersion) {
		this.magnumVersion = magnumVersion;
	}

	public Map<ReportedPeptide, Map<SubSearchName, Map<ScanNumber, Collection<MagnumPSM>>>> getMagnumResultMap() {
		return magnumResultMap;
	}

	public void setMagnumResultMap(Map<ReportedPeptide, Map<SubSearchName, Map<ScanNumber, Collection<MagnumPSM>>>> magnumResultMap) {
		this.magnumResultMap = magnumResultMap;
	}

	private String magnumVersion;
	private Map<ReportedPeptide, Map<SubSearchName, Map<ScanNumber, Collection<MagnumPSM>>>> magnumResultMap;
	
}

package org.yeastrc.emozi.xml.magnum.objects;

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
	/**
	 * @return the reportedPeptidePSMMap
	 */
	public Map<String, Map<Integer, MagnumPSM>> getReportedPeptidePSMMap() {
		return reportedPeptidePSMMap;
	}
	/**
	 * @param reportedPeptidePSMMap the reportedPeptidePSMMap to set
	 */
	public void setReportedPeptidePSMMap(Map<String, Map<Integer, MagnumPSM>> reportedPeptidePSMMap) {
		this.reportedPeptidePSMMap = reportedPeptidePSMMap;
	}
	
	public String magnumVersion;
	
	// map of reported peptide to scan numbers to psm (a reported peptde + scan number is unique)
	public Map<String, Map<Integer, MagnumPSM>> reportedPeptidePSMMap;
	
}

package org.yeastrc.emozi.xml.magnum.objects;

import java.util.Map;

public class PercolatorResults {

	/**
	 * @return the percolatorVersion
	 */
	public String getPercolatorVersion() {
		return percolatorVersion;
	}
	/**
	 * @param percolatorVersion the percolatorVersion to set
	 */
	public void setPercolatorVersion(String percolatorVersion) {
		this.percolatorVersion = percolatorVersion;
	}
	/**
	 * @return the reportedPeptidePSMMap
	 */
	public Map<PercolatorPeptide, Map<Integer, PercolatorPSM>> getReportedPeptidePSMMap() {
		return reportedPeptidePSMMap;
	}
	/**
	 * @param reportedPeptidePSMMap the reportedPeptidePSMMap to set
	 */
	public void setReportedPeptidePSMMap(Map<PercolatorPeptide, Map<Integer, PercolatorPSM>> reportedPeptidePSMMap) {
		this.reportedPeptidePSMMap = reportedPeptidePSMMap;
	}
	
	private String percolatorVersion;
	
	// map of reported peptide to scan numbers to psm (a reported peptde + scan number is unique)
	private Map<PercolatorPeptide, Map<Integer, PercolatorPSM>> reportedPeptidePSMMap;	
	
}

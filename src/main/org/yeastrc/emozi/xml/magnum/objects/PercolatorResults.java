package org.yeastrc.emozi.xml.magnum.objects;

import java.util.Collection;
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
	 * @return the psms
	 */
	public Collection<PercolatorPSM> getPsms() {
		return psms;
	}
	/**
	 * @param psms the psms to set
	 */
	public void setPsms(Collection<PercolatorPSM> psms) {
		this.psms = psms;
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
	private Collection<PercolatorPSM> psms;
	
	// map of reported peptide to scan numbers to psm (a reported peptde + scan number is unique)
	private Map<PercolatorPeptide, Map<Integer, PercolatorPSM>> reportedPeptidePSMMap;	
	
}

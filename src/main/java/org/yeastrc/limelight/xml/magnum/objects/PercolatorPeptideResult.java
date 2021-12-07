package org.yeastrc.limelight.xml.magnum.objects;

import java.util.Collection;
import java.util.Map;

public class PercolatorPeptideResult {

	private Map<SubSearchName, Map<ScanNumber, Collection<PercolatorPSM>>> psmMap;
	private PercolatorPeptideStats percolatorPeptideStats;
	private String reportedPeptide;


	public Map<SubSearchName, Map<ScanNumber, Collection<PercolatorPSM>>> getPsmMap() {
		return psmMap;
	}

	public void setPsmMap(Map<SubSearchName, Map<ScanNumber, Collection<PercolatorPSM>>> psmMap) {
		this.psmMap = psmMap;
	}

	/**
	 * @return the percolatorPeptideStats
	 */
	public PercolatorPeptideStats getPercolatorPeptideStats() {
		return percolatorPeptideStats;
	}
	/**
	 * @param percolatorPeptideStats the percolatorPeptideStats to set
	 */
	public void setPercolatorPeptideStats(PercolatorPeptideStats percolatorPeptideStats) {
		this.percolatorPeptideStats = percolatorPeptideStats;
	}
	/**
	 * @return the reportedPeptide
	 */
	public String getReportedPeptide() {
		return reportedPeptide;
	}
	/**
	 * @param reportedPeptide the reportedPeptide to set
	 */
	public void setReportedPeptide(String reportedPeptide) {
		this.reportedPeptide = reportedPeptide;
	}
	
}

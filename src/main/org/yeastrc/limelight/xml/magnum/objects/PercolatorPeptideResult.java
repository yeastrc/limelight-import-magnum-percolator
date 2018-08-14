package org.yeastrc.limelight.xml.magnum.objects;

import java.util.Collection;
import java.util.Map;

public class PercolatorPeptideResult {

	private Map<Integer, Collection<PercolatorPSM>> psmsIndexedByScanNumber;	
	private PercolatorPeptideStats percolatorPeptideStats;
	private String reportedPeptide;
	

	/**
	 * @return the all PSMs associated with this peptide
	 */
	public Map<Integer, Collection<PercolatorPSM>> getPsmsIndexedByScanNumber() {
		return psmsIndexedByScanNumber;
	}
	/**
	 * @param psmsIndexedByScanNumber the psmsIndexedByScanNumber to set
	 */
	public void setPsmsIndexedByScanNumber(Map<Integer, Collection<PercolatorPSM>> psmsIndexedByScanNumber) {
		this.psmsIndexedByScanNumber = psmsIndexedByScanNumber;
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

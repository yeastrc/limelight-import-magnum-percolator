package org.yeastrc.emozi.xml.magnum.objects;

import java.util.Collection;

public class PercolatorPeptide {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reportedPeptide == null) ? 0 : reportedPeptide.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PercolatorPeptide other = (PercolatorPeptide) obj;
		if (reportedPeptide == null) {
			if (other.reportedPeptide != null)
				return false;
		} else if (!reportedPeptide.equals(other.reportedPeptide))
			return false;
		return true;
	}
	
	/**
	 * @return the svmScore
	 */
	public double getSvmScore() {
		return svmScore;
	}
	/**
	 * @param svmScore the svmScore to set
	 */
	public void setSvmScore(double svmScore) {
		this.svmScore = svmScore;
	}
	/**
	 * @return the qValue
	 */
	public double getqValue() {
		return qValue;
	}
	/**
	 * @param qValue the qValue to set
	 */
	public void setqValue(double qValue) {
		this.qValue = qValue;
	}
	/**
	 * @return the pValue
	 */
	public double getpValue() {
		return pValue;
	}
	/**
	 * @param pValue the pValue to set
	 */
	public void setpValue(double pValue) {
		this.pValue = pValue;
	}
	/**
	 * @return the pep
	 */
	public double getPep() {
		return pep;
	}
	/**
	 * @param pep the pep to set
	 */
	public void setPep(double pep) {
		this.pep = pep;
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
	
	private double svmScore;
	private double qValue;
	private double pValue;
	private double pep;
	private String reportedPeptide;
	private Collection<PercolatorPSM> psms;
	
}

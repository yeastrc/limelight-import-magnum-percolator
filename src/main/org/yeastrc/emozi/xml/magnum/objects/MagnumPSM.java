package org.yeastrc.emozi.xml.magnum.objects;

public class MagnumPSM {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reportedPeptide == null) ? 0 : reportedPeptide.hashCode());
		result = prime * result + scanNumber;
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
		MagnumPSM other = (MagnumPSM) obj;
		if (reportedPeptide == null) {
			if (other.reportedPeptide != null)
				return false;
		} else if (!reportedPeptide.equals(other.reportedPeptide))
			return false;
		if (scanNumber != other.scanNumber)
			return false;
		return true;
	}
	
	/**
	 * @return the retentionTime
	 */
	public double getRetentionTime() {
		return retentionTime;
	}
	/**
	 * @param retentionTime the retentionTime to set
	 */
	public void setRetentionTime(double retentionTime) {
		this.retentionTime = retentionTime;
	}
	/**
	 * @return the scanNumber
	 */
	public int getScanNumber() {
		return scanNumber;
	}
	/**
	 * @param scanNumber the scanNumber to set
	 */
	public void setScanNumber(int scanNumber) {
		this.scanNumber = scanNumber;
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
	 * @return the observedMass
	 */
	public double getObservedMass() {
		return observedMass;
	}
	/**
	 * @param observedMass the observedMass to set
	 */
	public void setObservedMass(double observedMass) {
		this.observedMass = observedMass;
	}
	/**
	 * @return the ppmError
	 */
	public double getPpmError() {
		return ppmError;
	}
	/**
	 * @param ppmError the ppmError to set
	 */
	public void setPpmError(double ppmError) {
		this.ppmError = ppmError;
	}
	/**
	 * @return the eValue
	 */
	public double geteValue() {
		return eValue;
	}
	/**
	 * @param eValue the eValue to set
	 */
	public void seteValue(double eValue) {
		this.eValue = eValue;
	}
	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}
	/**
	 * @return the dScore
	 */
	public double getdScore() {
		return dScore;
	}
	/**
	 * @param dScore the dScore to set
	 */
	public void setdScore(double dScore) {
		this.dScore = dScore;
	}
	
	private double retentionTime;
	private int scanNumber;
	private String reportedPeptide;
	private double observedMass;
	private double ppmError;
	private double eValue;
	private double score;
	private double dScore;
	
}

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

import java.math.BigDecimal;
import java.util.Map;

public class MagnumPSM {
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MagnumPSM [retentionTime=" + retentionTime + ", scanNumber=" + scanNumber + ", charge=" + charge
				+ ", reportedPeptide=" + reportedPeptide + ", observedMass=" + observedMass + ", ppmError=" + ppmError
				+ ", eValue=" + eValue + ", score=" + score + ", dScore=" + dScore + ", peptideSequence="
				+ peptideSequence + ", modifications=" + modifications + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + charge;
		long temp;
		temp = Double.doubleToLongBits(dScore);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(eValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((modifications == null) ? 0 : modifications.hashCode());
		temp = Double.doubleToLongBits(observedMass);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((peptideSequence == null) ? 0 : peptideSequence.hashCode());
		temp = Double.doubleToLongBits(ppmError);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((reportedPeptide == null) ? 0 : reportedPeptide.hashCode());
		temp = Double.doubleToLongBits(retentionTime);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + scanNumber;
		temp = Double.doubleToLongBits(score);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (!(obj instanceof MagnumPSM))
			return false;
		MagnumPSM other = (MagnumPSM) obj;
		if (charge != other.charge)
			return false;
		if (Double.doubleToLongBits(dScore) != Double.doubleToLongBits(other.dScore))
			return false;
		if (Double.doubleToLongBits(eValue) != Double.doubleToLongBits(other.eValue))
			return false;
		if (modifications == null) {
			if (other.modifications != null)
				return false;
		} else if (!modifications.equals(other.modifications))
			return false;
		if (Double.doubleToLongBits(observedMass) != Double.doubleToLongBits(other.observedMass))
			return false;
		if (peptideSequence == null) {
			if (other.peptideSequence != null)
				return false;
		} else if (!peptideSequence.equals(other.peptideSequence))
			return false;
		if (Double.doubleToLongBits(ppmError) != Double.doubleToLongBits(other.ppmError))
			return false;
		if (reportedPeptide == null) {
			if (other.reportedPeptide != null)
				return false;
		} else if (!reportedPeptide.equals(other.reportedPeptide))
			return false;
		if (Double.doubleToLongBits(retentionTime) != Double.doubleToLongBits(other.retentionTime))
			return false;
		if (scanNumber != other.scanNumber)
			return false;
		if (Double.doubleToLongBits(score) != Double.doubleToLongBits(other.score))
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
	/**
	 * @return the charge
	 */
	public int getCharge() {
		return charge;
	}
	/**
	 * @param charge the charge to set
	 */
	public void setCharge(int charge) {
		this.charge = charge;
	}

	/**
	 * @return the peptideSequence
	 */
	public String getPeptideSequence() {
		return peptideSequence;
	}

	/**
	 * @param peptideSequence the peptideSequence to set
	 */
	public void setPeptideSequence(String peptideSequence) {
		this.peptideSequence = peptideSequence;
	}



	/**
	 * @return the modifications
	 */
	public Map<Integer, BigDecimal> getModifications() {
		return modifications;
	}
	/**
	 * @param modifications the modifications to set
	 */
	public void setModifications(Map<Integer, BigDecimal> modifications) {
		this.modifications = modifications;
	}



	private double retentionTime;
	private int scanNumber;
	private int charge;
	private String reportedPeptide;
	private double observedMass;
	private double ppmError;
	private double eValue;
	private double score;
	private double dScore;
	private String peptideSequence;
	private Map<Integer, BigDecimal> modifications;
	
}

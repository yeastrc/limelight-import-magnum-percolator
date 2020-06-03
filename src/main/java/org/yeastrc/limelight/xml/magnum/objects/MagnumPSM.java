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
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class MagnumPSM {

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

	public Collection<BigDecimal> getReporterIons() {
		return reporterIons;
	}

	public void setReporterIons(Collection<BigDecimal> reporterIons) {
		this.reporterIons = reporterIons;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MagnumPSM magnumPSM = (MagnumPSM) o;
		return Double.compare(magnumPSM.retentionTime, retentionTime) == 0 &&
				scanNumber == magnumPSM.scanNumber &&
				charge == magnumPSM.charge &&
				Double.compare(magnumPSM.observedMass, observedMass) == 0 &&
				Double.compare(magnumPSM.ppmError, ppmError) == 0 &&
				Double.compare(magnumPSM.eValue, eValue) == 0 &&
				Double.compare(magnumPSM.score, score) == 0 &&
				Double.compare(magnumPSM.dScore, dScore) == 0 &&
				reportedPeptide.equals(magnumPSM.reportedPeptide) &&
				peptideSequence.equals(magnumPSM.peptideSequence) &&
				Objects.equals(modifications, magnumPSM.modifications) &&
				Objects.equals(reporterIons, magnumPSM.reporterIons) &&
				Objects.equals(openModification, magnumPSM.openModification);
	}

	@Override
	public int hashCode() {
		return Objects.hash(retentionTime, scanNumber, charge, reportedPeptide, observedMass, ppmError, eValue, score, dScore, peptideSequence, modifications, reporterIons, openModification);
	}

	@Override
	public String toString() {
		return "MagnumPSM{" +
				"retentionTime=" + retentionTime +
				", scanNumber=" + scanNumber +
				", charge=" + charge +
				", reportedPeptide='" + reportedPeptide + '\'' +
				", observedMass=" + observedMass +
				", ppmError=" + ppmError +
				", eValue=" + eValue +
				", score=" + score +
				", dScore=" + dScore +
				", peptideSequence='" + peptideSequence + '\'' +
				", modifications=" + modifications +
				", reporterIons=" + reporterIons +
				", openModification=" + openModification +
				'}';
	}

	public OpenModification getOpenModification() {
		return openModification;
	}

	public void setOpenModification(OpenModification openModification) {
		this.openModification = openModification;
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
	private Collection<BigDecimal> reporterIons;
	private OpenModification openModification;
}

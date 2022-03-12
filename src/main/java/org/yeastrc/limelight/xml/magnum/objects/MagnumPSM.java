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

	@Override
	public String toString() {
		return "MagnumPSM{" +
				"retentionTime=" + retentionTime +
				", scanNumber=" + scanNumber +
				", charge=" + charge +
				", observedMass=" + observedMass +
				", ppmError=" + ppmError +
				", eValue=" + eValue +
				", mScore=" + mScore +
				", dScore=" + dScore +
				", peptideSequence='" + peptideSequence + '\'' +
				", modifications=" + modifications +
				", reporterIons=" + reporterIons +
				", openModification=" + openModification +
				", massDiff=" + massDiff +
				", subSearchName='" + subSearchName + '\'' +
				", scanFilename='" + scanFilename + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MagnumPSM magnumPSM = (MagnumPSM) o;
		return scanNumber == magnumPSM.scanNumber &&
				charge == magnumPSM.charge &&
				peptideSequence.equals(magnumPSM.peptideSequence) &&
				Objects.equals(modifications, magnumPSM.modifications) &&
				Objects.equals(reporterIons, magnumPSM.reporterIons) &&
				Objects.equals(openModification, magnumPSM.openModification) &&
				Objects.equals(massDiff, magnumPSM.massDiff) &&
				Objects.equals(subSearchName, magnumPSM.subSearchName) &&
				Objects.equals(scanFilename, magnumPSM.scanFilename);
	}

	@Override
	public int hashCode() {
		return Objects.hash(scanNumber, charge, peptideSequence, modifications, reporterIons, openModification, massDiff, subSearchName, scanFilename);
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
	public double getmScore() {
		return mScore;
	}
	/**
	 * @param mScore the score to set
	 */
	public void setmScore(double mScore) {
		this.mScore = mScore;
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

	public OpenModification getOpenModification() {
		return openModification;
	}

	public void setOpenModification(OpenModification openModification) {
		this.openModification = openModification;
	}

	public BigDecimal getMassDiff() {
		return massDiff;
	}

	public void setMassDiff(BigDecimal massDiff) {
		this.massDiff = massDiff;
	}

	public String getSubSearchName() {
		return subSearchName;
	}

	public void setSubSearchName(String subSearchName) {
		this.subSearchName = subSearchName;
	}

	public String getScanFilename() {
		return scanFilename;
	}

	public void setScanFilename(String scanFilename) {
		this.scanFilename = scanFilename;
	}

	public boolean isDecoy() {
		return isDecoy;
	}

	public void setDecoy(boolean decoy) {
		isDecoy = decoy;
	}

	public boolean isIndependentDecoy() {
		return isIndependentDecoy;
	}

	public void setIndependentDecoy(boolean independentDecoy) {
		isIndependentDecoy = independentDecoy;
	}

	private double retentionTime;
	private int scanNumber;
	private int charge;

	private double observedMass;
	private double ppmError;
	private double eValue;
	private double mScore;
	private double dScore;
	private String peptideSequence;
	private Map<Integer, BigDecimal> modifications;
	private Collection<BigDecimal> reporterIons;
	private OpenModification openModification;
	private BigDecimal massDiff;
	private String subSearchName;
	private String scanFilename;
	private boolean isDecoy = false;
	private boolean isIndependentDecoy = false;

}

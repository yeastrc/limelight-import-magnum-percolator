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

import java.util.Objects;

public class PercolatorPSM {

	@Override
	public String toString() {
		return "PercolatorPSM{" +
				"svmScore=" + svmScore +
				", qValue=" + qValue +
				", pValue=" + pValue +
				", pep=" + pep +
				", reportedPeptide='" + reportedPeptide + '\'' +
				", psmId='" + psmId + '\'' +
				", scanNumber=" + scanNumber +
				", isOpenModResult=" + isOpenModResult +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PercolatorPSM that = (PercolatorPSM) o;
		return scanNumber == that.scanNumber &&
				isOpenModResult == that.isOpenModResult &&
				Objects.equals(reportedPeptide, that.reportedPeptide) &&
				psmId.equals(that.psmId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(reportedPeptide, psmId, scanNumber, isOpenModResult);
	}

	public boolean isOpenModResult() {
		return isOpenModResult;
	}

	public void setOpenModResult(boolean openModResult) {
		isOpenModResult = openModResult;
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
	 * @return the psmId
	 */
	public String getPsmId() {
		return psmId;
	}
	/**
	 * @param psmId the psmId to set
	 */
	public void setPsmId(String psmId) {
		this.psmId = psmId;
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

	public String getSubSearchName() {
		return subSearchName;
	}

	public void setSubSearchName(String subSearchName) {
		this.subSearchName = subSearchName;
	}

	public boolean isDecoy() {
		return isDecoy;
	}

	public void setDecoy(boolean decoy) {
		isDecoy = decoy;
	}

	private double svmScore;
	private double qValue;
	private double pValue;
	private double pep;
	private String reportedPeptide;
	private String psmId;
	private int scanNumber;
	private boolean isOpenModResult = false;
	private boolean isDecoy = false;
	private String subSearchName;
}

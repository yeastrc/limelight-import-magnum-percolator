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

import java.io.File;
import java.math.BigDecimal;

public class ConversionParameters {

	public File getOpenModsPercolatorXMLOutputFile() {
		return openModsPercolatorXMLOutputFile;
	}

	public void setOpenModsPercolatorXMLOutputFile(File openModsPercolatorXMLOutputFile) {
		this.openModsPercolatorXMLOutputFile = openModsPercolatorXMLOutputFile;
	}

	public File getStandardPercolatorXMLOutputFile() {
		return standardPercolatorXMLOutputFile;
	}

	public void setStandardPercolatorXMLOutputFile(File standardPercolatorXMLOutputFile) {
		this.standardPercolatorXMLOutputFile = standardPercolatorXMLOutputFile;
	}

	public Boolean getOpenModsSeparate() {
		return isOpenModsSeparate;
	}

	public void setOpenModsSeparate(Boolean openModsSeparate) {
		isOpenModsSeparate = openModsSeparate;
	}

	/**
	 * @return the fastaFile
	 */
	public File getFastaFile() {
		return fastaFile;
	}
	/**
	 * @param fastaFile the fastaFile to set
	 */
	public void setFastaFile(File fastaFile) {
		this.fastaFile = fastaFile;
	}
	/**
	 * @return the magnumParametersFile
	 */
	public File[] getMagnumParametersFiles() {
		return magnumParametersFiles;
	}
	/**
	 * @param magnumParametersFiles the magnumParametersFile to set
	 */
	public void setMagnumParametersFiles(File[] magnumParametersFiles) {
		this.magnumParametersFiles = magnumParametersFiles;
	}
	/**
	 * @return the percolatorXMLOutputFile
	 */
	public File getPercolatorXMLOutputFile() {
		return percolatorXMLOutputFile;
	}
	/**
	 * @param percolatorXMLOutputFile the percolatorXMLOutputFile to set
	 */
	public void setPercolatorXMLOutputFile(File percolatorXMLOutputFile) {
		this.percolatorXMLOutputFile = percolatorXMLOutputFile;
	}
	/**
	 * @return the magnumOutputFile
	 */
	public File[] getMagnumOutputFiles() {
		return magnumOutputFiles;
	}
	/**
	 * @param magnumOutputFiles the magnumOutputFile to set
	 */
	public void setMagnumOutputFiles(File[] magnumOutputFiles) {
		this.magnumOutputFiles = magnumOutputFiles;
	}
	
	/**
	 * @return the conversionProgramInfo
	 */
	public ConversionProgramInfo getConversionProgramInfo() {
		return conversionProgramInfo;
	}
	/**
	 * @param conversionProgramInfo the conversionProgramInfo to set
	 */
	public void setConversionProgramInfo(ConversionProgramInfo conversionProgramInfo) {
		this.conversionProgramInfo = conversionProgramInfo;
	}	
	/**
	 * @return the limelightXMLOutputFile
	 */
	public File getLimelightXMLOutputFile() {
		return limelightXMLOutputFile;
	}
	/**
	 * @param limelightXMLOutputFile the limelightXMLOutputFile to set
	 */
	public void setLimelightXMLOutputFile(File limelightXMLOutputFile) {
		this.limelightXMLOutputFile = limelightXMLOutputFile;
	}

	public BigDecimal getqValueOverride() {
		return qValueOverride;
	}

	public void setqValueOverride(BigDecimal qValueOverride) {
		this.qValueOverride = qValueOverride;
	}

	private File fastaFile;
	private File[] magnumParametersFiles;
	private File percolatorXMLOutputFile;
	private File openModsPercolatorXMLOutputFile;
	private File standardPercolatorXMLOutputFile;
	private File[] magnumOutputFiles;
	private File limelightXMLOutputFile;
	private ConversionProgramInfo conversionProgramInfo;
	private BigDecimal qValueOverride;
	private Boolean isOpenModsSeparate;
	
}

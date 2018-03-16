package org.yeastrc.emozi.xml.magnum.objects;

import java.io.File;

public class ConversionParameters {
		
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
	public File getMagnumParametersFile() {
		return magnumParametersFile;
	}
	/**
	 * @param magnumParametersFile the magnumParametersFile to set
	 */
	public void setMagnumParametersFile(File magnumParametersFile) {
		this.magnumParametersFile = magnumParametersFile;
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
	public File getMagnumOutputFile() {
		return magnumOutputFile;
	}
	/**
	 * @param magnumOutputFile the magnumOutputFile to set
	 */
	public void setMagnumOutputFile(File magnumOutputFile) {
		this.magnumOutputFile = magnumOutputFile;
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
	 * @return the emoziXMLOutputFile
	 */
	public File getEmoziXMLOutputFile() {
		return emoziXMLOutputFile;
	}
	/**
	 * @param emoziXMLOutputFile the emoziXMLOutputFile to set
	 */
	public void setEmoziXMLOutputFile(File emoziXMLOutputFile) {
		this.emoziXMLOutputFile = emoziXMLOutputFile;
	}



	private File fastaFile;
	private File magnumParametersFile;
	private File percolatorXMLOutputFile;
	private File magnumOutputFile;
	private File emoziXMLOutputFile;
	private ConversionProgramInfo conversionProgramInfo;
	
}

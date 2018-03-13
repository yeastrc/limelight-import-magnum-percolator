package org.yeastrc.emozi.xml.magnum.objects;

import java.time.LocalDateTime;

import org.yeastrc.emozi.xml.magnum.constants.Constants;

public class ConversionProgramInfo {
	
	public static ConversionProgramInfo createInstance( String arguments ) {
		
		ConversionProgramInfo cpi = new ConversionProgramInfo();
		
		cpi.setName( Constants.CONVERSION_PROGRAM_NAME );
		cpi.setURI( Constants.CONVERSION_PROGRAM_URI );
		cpi.setVersion( Constants.CONVERSION_PROGRAM_VERSION );
		
		cpi.setArguments( arguments );
		cpi.setConversionDate( LocalDateTime.now() );
		
		return cpi;
		
	}
	
	private ConversionProgramInfo() { }
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the conversionDate
	 */
	public LocalDateTime getConversionDate() {
		return conversionDate;
	}
	/**
	 * @param conversionDate the conversionDate to set
	 */
	public void setConversionDate(LocalDateTime conversionDate) {
		this.conversionDate = conversionDate;
	}
	/**
	 * @return the arguments
	 */
	public String getArguments() {
		return arguments;
	}
	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}
	/**
	 * @return the uRI
	 */
	public String getURI() {
		return URI;
	}
	/**
	 * @param uRI the uRI to set
	 */
	public void setURI(String uRI) {
		URI = uRI;
	}
	
	private String name;
	private String version;
	private LocalDateTime conversionDate;
	private String arguments;
	private String URI;
	
}

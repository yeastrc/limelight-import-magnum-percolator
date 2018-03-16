package org.yeastrc.emozi.xml.magnum.objects;

import java.util.Map;

public class MagnumParameters {

	/**
	 * @return the dynamicMods
	 */
	public Map<Character, Double> getDynamicMods() {
		return dynamicMods;
	}
	/**
	 * @param dynamicMods the dynamicMods to set
	 */
	public void setDynamicMods(Map<Character, Double> dynamicMods) {
		this.dynamicMods = dynamicMods;
	}
	/**
	 * @return the staticMods
	 */
	public Map<Character, Double> getStaticMods() {
		return staticMods;
	}
	/**
	 * @param staticMods the staticMods to set
	 */
	public void setStaticMods(Map<Character, Double> staticMods) {
		this.staticMods = staticMods;
	}
	
	private Map<Character, Double> dynamicMods;
	private Map<Character, Double> staticMods;
}

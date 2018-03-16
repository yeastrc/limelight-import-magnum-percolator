package org.yeastrc.emozi.xml.magnum.objects;

import java.util.Map;


/**
 * Represents a peptide parsed from a reported peptide, which had the form of:
 * PEP[123.22]TIDE. Includes the naked sequence and the parsed modifications
 * 
 * @author mriffle
 *
 */
public class ParsedPeptide {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modMap == null) ? 0 : modMap.hashCode());
		result = prime * result + ((nakedSequence == null) ? 0 : nakedSequence.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParsedPeptide other = (ParsedPeptide) obj;
		if (modMap == null) {
			if (other.modMap != null)
				return false;
		} else if (!modMap.equals(other.modMap))
			return false;
		if (nakedSequence == null) {
			if (other.nakedSequence != null)
				return false;
		} else if (!nakedSequence.equals(other.nakedSequence))
			return false;
		return true;
	}
	
	private String nakedSequence;
	private Map<Integer, Double> modMap;
	
	public String getNakedSequence() {
		return nakedSequence;
	}
	public void setNakedSequence(String nakedSequence) {
		this.nakedSequence = nakedSequence;
	}
	public Map<Integer, Double> getModMap() {
		return modMap;
	}
	public void setModMap(Map<Integer, Double> modMap) {
		this.modMap = modMap;
	}
	
}

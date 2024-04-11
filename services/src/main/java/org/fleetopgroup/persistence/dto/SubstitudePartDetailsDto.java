package org.fleetopgroup.persistence.dto;

public class SubstitudePartDetailsDto {

	private Long	substitudePartDetailsId;
	
	private Long	mainPartId;

	private Long	partId;
	
	private String 	mainPartName;
	
	private String	childPartName;

	public Long getSubstitudePartDetailsId() {
		return substitudePartDetailsId;
	}

	public void setSubstitudePartDetailsId(Long substitudePartDetailsId) {
		this.substitudePartDetailsId = substitudePartDetailsId;
	}

	public Long getMainPartId() {
		return mainPartId;
	}

	public void setMainPartId(Long mainPartId) {
		this.mainPartId = mainPartId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public String getMainPartName() {
		return mainPartName;
	}

	public void setMainPartName(String mainPartName) {
		this.mainPartName = mainPartName;
	}

	public String getChildPartName() {
		return childPartName;
	}

	public void setChildPartName(String childPartName) {
		this.childPartName = childPartName;
	}
	
	
}

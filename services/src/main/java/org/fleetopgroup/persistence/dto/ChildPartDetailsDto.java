package org.fleetopgroup.persistence.dto;

public class ChildPartDetailsDto {

	private Long 	childPartDetailsId;
	private Long	mainPartId;
	private Long	partId;
	private Integer companyId;
	private String 	mainPartName;
	private String	childPartName;
	
	public Long getChildPartDetailsId() {
		return childPartDetailsId;
	}
	public void setChildPartDetailsId(Long childPartDetailsId) {
		this.childPartDetailsId = childPartDetailsId;
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
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

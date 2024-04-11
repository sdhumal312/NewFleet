
package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class DriverBasicDetailsTypeDto {

	private Long driverBasicDetailsTypeId;
	
	private String	driverBasicDetailsType;
	
	private String	description;

	private Long createdById;

	private String createdBy;
	
	private Long lastUpdatedById;

	private String lastUpdatedBy;
	
	private Date creationDate;

	private String creationDateStr;
	
	private Date lastUpdatedDate;
	
	private String lastUpdatedDateStr;
	
	private Integer companyId;
	
	private boolean markForDelete;

	public DriverBasicDetailsTypeDto() {
		super();
	}

	public DriverBasicDetailsTypeDto(Long driverBasicDetailsTypeId, String driverBasicDetailsType, String description,
			Long createdById, String createdBy, Long lastUpdatedById, String lastUpdatedBy, Date creationDate,
			String creationDateStr, Date lastUpdatedDate, String lastUpdatedDateStr, Integer companyId,
			boolean markForDelete) {
		super();
		this.driverBasicDetailsTypeId = driverBasicDetailsTypeId;
		this.driverBasicDetailsType = driverBasicDetailsType;
		this.description = description;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.lastUpdatedById = lastUpdatedById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.creationDate = creationDate;
		this.creationDateStr = creationDateStr;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedDateStr = lastUpdatedDateStr;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getDriverBasicDetailsTypeId() {
		return driverBasicDetailsTypeId;
	}

	public String getDriverBasicDetailsType() {
		return driverBasicDetailsType;
	}

	public String getDescription() {
		return description;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getCreationDateStr() {
		return creationDateStr;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public String getLastUpdatedDateStr() {
		return lastUpdatedDateStr;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setDriverBasicDetailsTypeId(Long driverBasicDetailsTypeId) {
		this.driverBasicDetailsTypeId = driverBasicDetailsTypeId;
	}

	public void setDriverBasicDetailsType(String driverBasicDetailsType) {
		this.driverBasicDetailsType = driverBasicDetailsType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setCreationDateStr(String creationDateStr) {
		this.creationDateStr = creationDateStr;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public void setLastUpdatedDateStr(String lastUpdatedDateStr) {
		this.lastUpdatedDateStr = lastUpdatedDateStr;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "DriverBasicDetailsTypeDto [driverBasicDetailsTypeId=" + driverBasicDetailsTypeId
				+ ", driverBasicDetailsType=" + driverBasicDetailsType + ", description=" + description
				+ ", createdById=" + createdById + ", createdBy=" + createdBy + ", lastUpdatedById=" + lastUpdatedById
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate=" + creationDate + ", creationDateStr="
				+ creationDateStr + ", lastUpdatedDate=" + lastUpdatedDate + ", lastUpdatedDateStr="
				+ lastUpdatedDateStr + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}


	  

}

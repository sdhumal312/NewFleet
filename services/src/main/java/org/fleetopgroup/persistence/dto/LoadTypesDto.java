package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class LoadTypesDto {
	
	private Long	loadTypesId;
	
	private String	loadTypeName;
	
	private String	description;
	
	private Date	creationDate;
	
	private Date	lastUpdatedOn;
	
	private Long	createdById;
	
	private Long	lastUpdatedBy;
	
	private Integer	companyId;
	
	private boolean markForDelete;

	public Long getLoadTypesId() {
		return loadTypesId;
	}

	public String getLoadTypeName() {
		return loadTypeName;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setLoadTypesId(Long loadTypesId) {
		this.loadTypesId = loadTypesId;
	}

	public void setLoadTypeName(String loadTypeName) {
		this.loadTypeName = loadTypeName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoadTypesDto [loadTypesId=");
		builder.append(loadTypesId);
		builder.append(", loadTypeName=");
		builder.append(loadTypeName);
		builder.append(", description=");
		builder.append(description);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastUpdatedOn=");
		builder.append(lastUpdatedOn);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastUpdatedBy=");
		builder.append(lastUpdatedBy);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}
	
}

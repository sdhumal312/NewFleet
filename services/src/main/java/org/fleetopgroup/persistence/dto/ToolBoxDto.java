package org.fleetopgroup.persistence.dto;

import java.util.Date;



public class ToolBoxDto
{
	
	private Long	toolBoxId;
	
	private String	toolBoxName;
	
	private String	description;
	
	private Date	creationDate;
	
	private Date	lastUpdatedOn;
	
	private Long	createdById;
	
	private Long	lastUpdatedBy;
	
	private Integer	companyId;
	
	private boolean markForDelete;

	public Long getToolBoxId() {
		return toolBoxId;
	}

	public void setToolBoxId(Long toolBoxId) {
		this.toolBoxId = toolBoxId;
	}

	public String getToolBoxName() {
		return toolBoxName;
	}

	public void setToolBoxName(String toolBoxName) {
		this.toolBoxName = toolBoxName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "ToolBoxDto [toolBoxId=" + toolBoxId + ", toolBoxName=" + toolBoxName + ", description=" + description
				+ ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", createdById=" + createdById
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}
	
	

}

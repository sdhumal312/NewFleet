package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class CommentTypeDto {

	private Long commentTypeId;

	private String commentTypeName;

	private String description;
	
	private Integer companyId;
	
	private Long createdById;
	
	private Timestamp createdOn;
	
	private Long lastModifiedById;
	
	private Timestamp lastModifiedOn;
	
	private boolean markForDelete;

	public Long getCommentTypeId() {
		return commentTypeId;
	}

	public void setCommentTypeId(Long commentTypeId) {
		this.commentTypeId = commentTypeId;
	}

	public String getCommentTypeName() {
		return commentTypeName;
	}

	public void setCommentTypeName(String commentTypeName) {
		this.commentTypeName = commentTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	
	
}

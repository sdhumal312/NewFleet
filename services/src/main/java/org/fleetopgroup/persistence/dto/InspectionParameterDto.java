package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import javax.persistence.Column;

import org.fleetopgroup.persistence.model.InspectionParameter;

public class InspectionParameterDto {
	
	private Long		inspectionParameterId;
	
	private String		parameterName;
	
	private Long		parameterPhotoId;
	
	private Long		createdById;
	
	private Long		lastModifiedById;
	
	private Timestamp	createdOn;
	
	private Timestamp	lastModifiedOn;
	
	private boolean		markForDelete;
	


	public Long getInspectionParameterId() {
		return inspectionParameterId;
	}

	public void setInspectionParameterId(Long inspectionParameterId) {
		this.inspectionParameterId = inspectionParameterId;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public Long getParameterPhotoId() {
		return parameterPhotoId;
	}

	public void setParameterPhotoId(Long parameterPhotoId) {
		this.parameterPhotoId = parameterPhotoId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
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

	

	@Override
	public String toString() {
		return "InspectionParameter [inspectionParameterId=" + inspectionParameterId + ", parameterName="
				+ parameterName + ", parameterPhotoId=" + parameterPhotoId + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", createdOn=" + createdOn + ", lastModifiedOn="
				+ lastModifiedOn + ", markForDelete=" + markForDelete + "]";
	}
	
	
}
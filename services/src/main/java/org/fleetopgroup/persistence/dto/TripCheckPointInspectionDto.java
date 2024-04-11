/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

/**
 * @author fleetop
 *
 */
public class TripCheckPointInspectionDto {

	private Long tripCheckPointInspectionId;
	
	private Long tripCheckPointParameterInspectionId;
	
	private String tripCheckPointName;
	
	private String tripCheckPointParameterName;
	
	private boolean checkPointParameterInspectStatusId;
	
	private String checkPointParameterInspectStatus;
	
	private String remark;
	
	private Long createdById;

	private Long lastUpdatedById;
	
	private String createdBy;

	private String lastUpdatedBy;
	
	private Timestamp createdOn;

	private Timestamp lastUpdatedOn;
	
	private String creationDate;

	private String lastUpdatedDate;

	boolean markForDelete;

	public TripCheckPointInspectionDto() {
		super();
		
	}

	public TripCheckPointInspectionDto(Long tripCheckPointInspectionId, Long tripCheckPointParameterInspectionId,
			String tripCheckPointName, String tripCheckPointParameterName, boolean checkPointParameterInspectStatusId,
			String checkPointParameterInspectStatus, String remark, Long createdById, Long lastUpdatedById,
			String createdBy, String lastUpdatedBy, Timestamp createdOn, Timestamp lastUpdatedOn, String creationDate,
			String lastUpdatedDate, boolean markForDelete) {
		super();
		this.tripCheckPointInspectionId = tripCheckPointInspectionId;
		this.tripCheckPointParameterInspectionId = tripCheckPointParameterInspectionId;
		this.tripCheckPointName = tripCheckPointName;
		this.tripCheckPointParameterName = tripCheckPointParameterName;
		this.checkPointParameterInspectStatusId = checkPointParameterInspectStatusId;
		this.checkPointParameterInspectStatus = checkPointParameterInspectStatus;
		this.remark = remark;
		this.createdById = createdById;
		this.lastUpdatedById = lastUpdatedById;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.createdOn = createdOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.creationDate = creationDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.markForDelete = markForDelete;
	}

	public Long getTripCheckPointInspectionId() {
		return tripCheckPointInspectionId;
	}

	public void setTripCheckPointInspectionId(Long tripCheckPointInspectionId) {
		this.tripCheckPointInspectionId = tripCheckPointInspectionId;
	}

	public Long getTripCheckPointParameterInspectionId() {
		return tripCheckPointParameterInspectionId;
	}

	public void setTripCheckPointParameterInspectionId(Long tripCheckPointParameterInspectionId) {
		this.tripCheckPointParameterInspectionId = tripCheckPointParameterInspectionId;
	}

	public String getTripCheckPointName() {
		return tripCheckPointName;
	}

	public void setTripCheckPointName(String tripCheckPointName) {
		this.tripCheckPointName = tripCheckPointName;
	}

	public String getTripCheckPointParameterName() {
		return tripCheckPointParameterName;
	}

	public void setTripCheckPointParameterName(String tripCheckPointParameterName) {
		this.tripCheckPointParameterName = tripCheckPointParameterName;
	}

	public boolean isCheckPointParameterInspectStatusId() {
		return checkPointParameterInspectStatusId;
	}

	public void setCheckPointParameterInspectStatusId(boolean checkPointParameterInspectStatusId) {
		this.checkPointParameterInspectStatusId = checkPointParameterInspectStatusId;
	}
	
	public String getCheckPointParameterInspectStatus() {
		return checkPointParameterInspectStatus;
	}

	public void setCheckPointParameterInspectStatus(String checkPointParameterInspectStatus) {
		this.checkPointParameterInspectStatus = checkPointParameterInspectStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "TripCheckPointInspectionDto [tripCheckPointInspectionId=" + tripCheckPointInspectionId
				+ ", tripCheckPointParameterInspectionId=" + tripCheckPointParameterInspectionId
				+ ", tripCheckPointName=" + tripCheckPointName + ", tripCheckPointParameterName="
				+ tripCheckPointParameterName + ", checkPointParameterInspectStatusId="
				+ checkPointParameterInspectStatusId + ", checkPointParameterInspectStatus="
				+ checkPointParameterInspectStatus + ", remark=" + remark + ", createdById=" + createdById
				+ ", lastUpdatedById=" + lastUpdatedById + ", createdBy=" + createdBy + ", lastUpdatedBy="
				+ lastUpdatedBy + ", createdOn=" + createdOn + ", lastUpdatedOn=" + lastUpdatedOn + ", creationDate="
				+ creationDate + ", lastUpdatedDate=" + lastUpdatedDate + ", markForDelete=" + markForDelete + "]";
	}

	

	
}

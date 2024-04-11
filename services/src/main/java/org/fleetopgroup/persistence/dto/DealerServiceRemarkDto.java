package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class DealerServiceRemarkDto {

	private Long	dealerServiceEntriesRemarkId;
	
	private String	remark;
	
	private Long	dealerServiceEntriesId;
	
	private short	remarkTypeId;
	
	private String	remarkType;
	
	private Integer	companyId;
	
	private Integer driverId;
	
	private String driverFirstName;
	
	private String driverMiddleName;
	
	private String driverLastName;
	
	private String driverName;
	
	private Long	assignee;
	
	private String	assigneeName;
	
	private Boolean	markForDelete;
	
	private Long createdById;
	
	private String createdBy;

	private Date creationOn;
	
	private String creationDate;

	public Long getDealerServiceEntriesRemarkId() {
		return dealerServiceEntriesRemarkId;
	}

	public void setDealerServiceEntriesRemarkId(Long dealerServiceEntriesRemarkId) {
		this.dealerServiceEntriesRemarkId = dealerServiceEntriesRemarkId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public short getRemarkTypeId() {
		return remarkTypeId;
	}

	public void setRemarkTypeId(short remarkTypeId) {
		this.remarkTypeId = remarkTypeId;
	}

	public String getRemarkType() {
		return remarkType;
	}

	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Long getAssignee() {
		return assignee;
	}

	public void setAssignee(Long assignee) {
		this.assignee = assignee;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public Boolean getMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(Boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getDriverFirstName() {
		return driverFirstName;
	}

	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}

	public String getDriverMiddleName() {
		return driverMiddleName;
	}

	public void setDriverMiddleName(String driverMiddleName) {
		this.driverMiddleName = driverMiddleName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	@Override
	public String toString() {
		return "DealerServiceEntriesRemarkDto [dealerServiceEntriesRemarkId=" + dealerServiceEntriesRemarkId
				+ ", remark=" + remark + ", dealerServiceEntriesId=" + dealerServiceEntriesId + ", remarkTypeId="
				+ remarkTypeId + ", remarkType=" + remarkType + ", companyId=" + companyId + ", driverId=" + driverId
				+ ", driverName=" + driverName + ", assignee=" + assignee + ", assigneeName=" + assigneeName
				+ ", markForDelete=" + markForDelete + ", createdById=" + createdById + ", createdBy=" + createdBy
				+ ", creationOn=" + creationOn + ", creationDate=" + creationDate + "]";
	}

	
}

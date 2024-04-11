package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class StatusChangeRemarkDto {

	private Long statusChangeRemarkId;
	
	private String statusChangeRemark;

	private Integer transactionId;
	
	private String vehicleRegistration;
	
	private short	currentStatusId;
	
	private String	currentStatus;
	
	private short	changeToStatusId;
	
	private String	changeToStatus;
	
	private Long createdById;
	
	private String createdBy;

	private Date creationOn;
	
	private String creationDate;

	private Integer companyId;
	
	private boolean markForDelete;
	
	private String mobileNumber;
	
	private String partyName;
	
	private Double soldAmount;
	
	private Date soldOn;
	
	private String soldOnstr;

	public Long getStatusChangeRemarkId() {
		return statusChangeRemarkId;
	}

	public void setStatusChangeRemarkId(Long statusChangeRemarkId) {
		this.statusChangeRemarkId = statusChangeRemarkId;
	}

	public String getStatusChangeRemark() {
		return statusChangeRemark;
	}

	public void setStatusChangeRemark(String statusChangeRemark) {
		this.statusChangeRemark = statusChangeRemark;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public String getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(String vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}

	public short getCurrentStatusId() {
		return currentStatusId;
	}

	public void setCurrentStatusId(short currentStatusId) {
		this.currentStatusId = currentStatusId;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public short getChangeToStatusId() {
		return changeToStatusId;
	}

	public void setChangeToStatusId(short changeToStatusId) {
		this.changeToStatusId = changeToStatusId;
	}

	public String getChangeToStatus() {
		return changeToStatus;
	}

	public void setChangeToStatus(String changeToStatus) {
		this.changeToStatus = changeToStatus;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public Double getSoldAmount() {
		return soldAmount;
	}

	public void setSoldAmount(Double soldAmount) {
		this.soldAmount = Utility.round(soldAmount, 2);
	}

	public Date getSoldOn() {
		return soldOn;
	}

	public void setSoldOn(Date soldOn) {
		this.soldOn = soldOn;
	}

	public String getSoldOnstr() {
		return soldOnstr;
	}

	public void setSoldOnstr(String soldOnstr) {
		this.soldOnstr = soldOnstr;
	}

	@Override
	public String toString() {
		return "StatusChangeRemarkDto [statusChangeRemarkId=" + statusChangeRemarkId + ", statusChangeRemark="
				+ statusChangeRemark + ", transactionId=" + transactionId + ", vehicleRegistration="
				+ vehicleRegistration + ", currentStatusId=" + currentStatusId + ", currentStatus=" + currentStatus
				+ ", changeToStatusId=" + changeToStatusId + ", changeToStatus=" + changeToStatus + ", createdById="
				+ createdById + ", createdBy=" + createdBy + ", creationOn=" + creationOn + ", creationDate="
				+ creationDate + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}

	
	
	

	
}

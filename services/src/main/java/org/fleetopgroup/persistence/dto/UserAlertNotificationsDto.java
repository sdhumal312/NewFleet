package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class UserAlertNotificationsDto {
	
	private Long	userAlertNotificationsId;
	
	private Long	userId;
	
	private Long	transactionId;
	
	private short	txnTypeId;
	
	private String	txnTypeName;
	
	private short	status;
	
	private String	alertMsg;
	
	private Integer	companyId;
	
	private Long	createdById;
	
	private Date	createdOn;
	
	private String	createdOnStr;
	
	private boolean	markForDelete;

	private Long	partRequisitionNumber;
	
	private Long	requisitionNumber;
	
	private String	sendBy;
	
	private String	statusStr;
	
	public Long getUserAlertNotificationsId() {
		return userAlertNotificationsId;
	}

	public void setUserAlertNotificationsId(Long userAlertNotificationsId) {
		this.userAlertNotificationsId = userAlertNotificationsId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public short getTxnTypeId() {
		return txnTypeId;
	}

	public void setTxnTypeId(short txnTypeId) {
		this.txnTypeId = txnTypeId;
	}

	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
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

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Long getPartRequisitionNumber() {
		return partRequisitionNumber;
	}

	public void setPartRequisitionNumber(Long partRequisitionNumber) {
		this.partRequisitionNumber = partRequisitionNumber;
	}

	public String getSendBy() {
		return sendBy;
	}

	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}

	public String getTxnTypeName() {
		return txnTypeName;
	}

	public void setTxnTypeName(String txnTypeName) {
		this.txnTypeName = txnTypeName;
	}

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public Long getRequisitionNumber() {
		return requisitionNumber;
	}

	public void setRequisitionNumber(Long requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}



}

package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "StatusChangeRemark")
public class StatusChangeRemark implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "statusChangeRemarkId")
	private Long statusChangeRemarkId;
	
	@Column(name = "statusRemark")
	private String statusRemark;

	@Column(name = "transactionId")
	private Integer transactionId;
	
	@Column(name = "currentStatusId")
	private short	currentStatusId;
	
	@Column(name = "changeToStatusId")
	private short	changeToStatusId;
	
	@Column(name = "typeId")
	private short	typeId;
	
	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "mobileNumber")
	private String mobileNumber;
	
	@Column(name = "partyName")
	private String partyName;
	
	@Column(name = "soldAmount")
	private Double soldAmount;
	
	@Column(name = "soldOn")
	private Date soldOn;
	
	public Long getStatusChangeRemarkId() {
		return statusChangeRemarkId;
	}

	public void setStatusChangeRemarkId(Long statusChangeRemarkId) {
		this.statusChangeRemarkId = statusChangeRemarkId;
	}

	public String getStatusRemark() {
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}

	public short getCurrentStatusId() {
		return currentStatusId;
	}

	public void setCurrentStatusId(short currentStatusId) {
		this.currentStatusId = currentStatusId;
	}

	public short getChangeToStatusId() {
		return changeToStatusId;
	}

	public void setChangeToStatusId(short changeToStatusId) {
		this.changeToStatusId = changeToStatusId;
	}

	public short getTypeId() {
		return typeId;
	}

	public void setTypeId(short typeId) {
		this.typeId = typeId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
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

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
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
		this.soldAmount = soldAmount;
	}

	public Date getSoldOn() {
		return soldOn;
	}

	public void setSoldOn(Date soldOn) {
		this.soldOn = soldOn;
	}

	@Override
	public String toString() {
		return "StatusChangeRemark [statusChangeRemarkId=" + statusChangeRemarkId + ", statusRemark=" + statusRemark
				+ ", TransactionId=" + transactionId + ", currentStatusId=" + currentStatusId + ", changeToStatusId=" + changeToStatusId
				+ ", typeId=" + typeId + ", createdById=" + createdById + ", creationOn=" + creationOn + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
	
}

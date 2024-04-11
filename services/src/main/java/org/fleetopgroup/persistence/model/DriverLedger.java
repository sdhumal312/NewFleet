package org.fleetopgroup.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DriverLedger")
public class DriverLedger {

	@Id
	@Column(name = "driverLedgerId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long driverLedgerId;
	
	@Column(name = "driverId")
	private Long driverId;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "transactionAmount")
	private Double transactionAmount;
	
	@Column(name = "openingBalance")
	private Double openingBalance;
	
	@Column(name = "closingBalance")
	private Double closingBalance;
	
	@Column(name = "transactionId")
	private Long transactionId;
	
	@Column(name = "txnTypeId")
	private short txnTypeId;
	
	@Column(name = "txnDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date	txnDateTime;
	
	@Column(name = "subTransactionId")
	private Long subTransactionId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "updatedById")
	private Long updatedById;
	
	@Column(name = "createdOn", updatable = false)
	private Date	createdOn;
	
	@Column(name = "modifiedOn")
	private Date	modifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	
	public Long getDriverLedgerId() {
		return driverLedgerId;
	}

	public void setDriverLedgerId(Long driverLedgerId) {
		this.driverLedgerId = driverLedgerId;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public Double getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(Double closingBalance) {
		this.closingBalance = closingBalance;
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

	public Long getSubTransactionId() {
		return subTransactionId;
	}

	public void setSubTransactionId(Long subTransactionId) {
		this.subTransactionId = subTransactionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(Long updatedById) {
		this.updatedById = updatedById;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public Date getTxnDateTime() {
		return txnDateTime;
	}

	public void setTxnDateTime(Date txnDateTime) {
		this.txnDateTime = txnDateTime;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "DriverLedger [driverLedgerId=" + driverLedgerId + ", driverId=" + driverId + ", companyId=" + companyId
				+ ", transactionAmount=" + transactionAmount + ", openingBalance=" + openingBalance
				+ ", closingBalance=" + closingBalance + ", transactionId=" + transactionId + ", txnTypeId=" + txnTypeId
				+ ", txnDateTime=" + txnDateTime + ", subTransactionId=" + subTransactionId + ", description="
				+ description + ", createdById=" + createdById + ", updatedById=" + updatedById + ", createdOn="
				+ createdOn + ", modifiedOn=" + modifiedOn + ", markForDelete=" + markForDelete + "]";
	}
	
	
		
}

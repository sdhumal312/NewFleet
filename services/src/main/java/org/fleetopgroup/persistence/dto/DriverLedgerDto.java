package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fleetopgroup.web.util.Utility;

public class DriverLedgerDto {

	private String 	tripSheetNumber;
	
	private Long driverLedgerId;
	
	private Long driverId;
	
	private Integer companyId;
	
	private Double transactionAmount;
	
	private Double openingBalance;
	
	private Double closingBalance;
	
	private Long transactionId;
	
	private short txnTypeId;
	
	private String txnTypeStr;
	
	private Date	txnDateTime;
	
	private String txnDateStr;
	
	private Long subTransactionId;
	
	private String description;
	
	private Long createdById;
	
	private Long updatedById;
	
	private Date	createdOn;
	
	private Date	modifiedOn;
	
	private boolean markForDelete;

	private String  driverName;
	
	private Double creditAmount;
	
	private Double debitAmount;
	
	private String transactionNo;
	
	private Long tripsheetId;
	
	private String	txnDateTimeStr;
	
	public DriverLedgerDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DriverLedgerDto(Long driverLedgerId, Long driverId, Integer companyId, Double transactionAmount,
			Double openingBalance, Double closingBalance, Long transactionId, short txnTypeId, Date txnDateTime,
			Long subTransactionId, String description, Long createdById, Long updatedById, Date createdOn,
			Date modifiedOn, boolean markForDelete) {
		super();
		this.driverLedgerId = driverLedgerId;
		this.driverId = driverId;
		this.companyId = companyId;
		this.transactionAmount = transactionAmount;
		this.openingBalance = openingBalance;
		this.closingBalance = closingBalance;
		this.transactionId = transactionId;
		this.txnTypeId = txnTypeId;
		this.txnDateTime = txnDateTime;
		this.subTransactionId = subTransactionId;
		this.description = description;
		this.createdById = createdById;
		this.updatedById = updatedById;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.markForDelete = markForDelete;
	}

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

	public Date getTxnDateTime() {
		return txnDateTime;
	}

	public void setTxnDateTime(Date txnDateTime) {
		this.txnDateTime = txnDateTime;
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

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	
	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}

	
	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	
	
	public String getTxnTypeStr() {
		return txnTypeStr;
	}

	public void setTxnTypeStr(String txnTypeStr) {
		this.txnTypeStr = txnTypeStr;
	}

	
	public Long getTripsheetId() {
		return tripsheetId;
	}

	public void setTripsheetId(Long tripsheetId) {
		this.tripsheetId = tripsheetId;
	}
	
	public String getTxnDateStr() {
		return txnDateStr;
	}

	public void setTxnDateStr(String txnDateStr) {
		this.txnDateStr = txnDateStr;
	}

	
	public String getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(String tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	
	public String getTxnDateTimeStr() {
		return txnDateTimeStr;
	}

	public void setTxnDateTimeStr(String txnDateTimeStr) {
		this.txnDateTimeStr = txnDateTimeStr;
	}

	@Override
	public String toString() {
		return "DriverLedgerDto [tripSheetNumber=" + tripSheetNumber + ", driverLedgerId=" + driverLedgerId
				+ ", driverId=" + driverId + ", companyId=" + companyId + ", transactionAmount=" + transactionAmount
				+ ", openingBalance=" + openingBalance + ", closingBalance=" + closingBalance + ", transactionId="
				+ transactionId + ", txnTypeId=" + txnTypeId + ", txnTypeStr=" + txnTypeStr + ", txnDateTime="
				+ txnDateTime + ", txnDateStr=" + txnDateStr + ", subTransactionId=" + subTransactionId
				+ ", description=" + description + ", createdById=" + createdById + ", updatedById=" + updatedById
				+ ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", markForDelete=" + markForDelete
				+ ", driverName=" + driverName + ", creditAmount=" + creditAmount + ", debitAmount=" + debitAmount
				+ ", transactionNo=" + transactionNo + ", tripsheetId=" + tripsheetId + ", txnDateTimeStr="
				+ txnDateTimeStr + "]";
	}
}

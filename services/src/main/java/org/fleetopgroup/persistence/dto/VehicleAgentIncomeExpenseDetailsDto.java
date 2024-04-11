package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class VehicleAgentIncomeExpenseDetailsDto {
	
	private Long	agentIncomeExpenseDetailsId;
	private Integer	vid;
	private short	txnTypeId;
	private Double	creditAmount;
	private Double	debitAmount;
	private short	identifier;
	private Long	transactionId;
	private String	accountName;
	private String	numberWithtype;
	private String	remark;
	private Date 	transactionDateTime;
	private Integer companyId;
	private Date 	createdOn;
	private Date 	lastUpdatedOn;
	private Long 	createdById;
	private boolean isPaymentDone;
	private boolean markForDelete;
	private String	txnTypeName;
	private String	identifierName;
	private String	transactionDateStr;
	

	public Long getAgentIncomeExpenseDetailsId() {
		return agentIncomeExpenseDetailsId;
	}

	public void setAgentIncomeExpenseDetailsId(Long agentIncomeExpenseDetailsId) {
		this.agentIncomeExpenseDetailsId = agentIncomeExpenseDetailsId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public short getTxnTypeId() {
		return txnTypeId;
	}

	public void setTxnTypeId(short txnTypeId) {
		this.txnTypeId = txnTypeId;
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount =Utility.round(creditAmount, 2);
	}

	public Double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = Utility.round(debitAmount, 2);
	}

	public short getIdentifier() {
		return identifier;
	}

	public void setIdentifier(short identifier) {
		this.identifier = identifier;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getNumberWithtype() {
		return numberWithtype;
	}

	public void setNumberWithtype(String numberWithtype) {
		this.numberWithtype = numberWithtype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Date transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public boolean isPaymentDone() {
		return isPaymentDone;
	}

	public void setPaymentDone(boolean isPaymentDone) {
		this.isPaymentDone = isPaymentDone;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getTxnTypeName() {
		return txnTypeName;
	}

	public void setTxnTypeName(String txnTypeName) {
		this.txnTypeName = txnTypeName;
	}

	public String getIdentifierName() {
		return identifierName;
	}

	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	public String getTransactionDateStr() {
		return transactionDateStr;
	}

	public void setTransactionDateStr(String transactionDateStr) {
		this.transactionDateStr = transactionDateStr;
	}

}

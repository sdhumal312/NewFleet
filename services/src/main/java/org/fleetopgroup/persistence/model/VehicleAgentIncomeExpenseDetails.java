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
@Table(name = "VehicleAgentIncomeExpenseDetails")
public class VehicleAgentIncomeExpenseDetails  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agentIncomeExpenseDetailsId")
	private Long	agentIncomeExpenseDetailsId;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "txnTypeId")
	private short	txnTypeId;
	
	@Column(name = "creditAmount")
	private Double	creditAmount;
	
	@Column(name = "debitAmount")
	private Double	debitAmount;
	
	@Column(name = "identifier")
	private short	identifier;
	
	@Column(name = "transactionId")
	private Long	transactionId;
	
	@Column(name = "accountName")
	private String	accountName;
	
	@Column(name = "numberWithtype")
	private String	numberWithtype;
	
	@Column(name = "remark")
	private String	remark;
	
	@Column(name = "transactionDateTime")
	private Date transactionDateTime;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "createdOn", updatable = false)
	private Date createdOn;
	
	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "isPaymentDone")
	private boolean isPaymentDone;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;
	

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
		this.creditAmount = creditAmount;
	}

	public Double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
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
}

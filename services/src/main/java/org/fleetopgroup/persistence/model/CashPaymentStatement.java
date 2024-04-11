package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CashPaymentStatement")
public class CashPaymentStatement implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cashPaymentStatementId;
	
	private Long moduleId;
	
	private String moduleNo;
	
	private String details;
	
	private short moduleIdentifier;
	
	private Integer branchId;
	
	private Integer companyId;

	private String transactionDateStr;
	
	private double incomeAmount;

	private double expenseAmount;
	
	public String remark;

	private boolean markForDelete;
	
	private Long createdBy;
	
	private Date createdOn;
	
	private String bankAccount;
	
	private short paymentTypeId;

	private String chequeNumber;
	
	public void setIncomeAmount(double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}


	public Long getCashPaymentStatementId() {
		return cashPaymentStatementId;
	}

	public void setCashPaymentStatementId(Long cashPaymentStatementId) {
		this.cashPaymentStatementId = cashPaymentStatementId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public short getModuleIdentifier() {
		return moduleIdentifier;
	}

	public void setModuleIdentifier(short moduleIdentifier) {
		this.moduleIdentifier = moduleIdentifier;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getIncomeAmount() {
		return incomeAmount;
	}

//	public void setIncomeAmount(Double incomeAmount) {
//		this.incomeAmount = incomeAmount;
//	}

	public double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public String getModuleNo() {
		return moduleNo;
	}

	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getTransactionDateStr() {
		return transactionDateStr;
	}

	public void setTransactionDateStr(String transactionDateStr) {
		this.transactionDateStr = transactionDateStr;
	}
	public String getChequeNumber() {
		return chequeNumber;
	}


	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}


	@Override
	public String toString() {
		return "CashPaymentStatement [cashPaymentStatementId=" + cashPaymentStatementId + ", moduleId=" + moduleId
				+ ", moduleNo=" + moduleNo + ", details=" + details + ", moduleIdentifier=" + moduleIdentifier
				+ ", branchId=" + branchId + ", companyId=" + companyId + ", transactionDateStr=" + transactionDateStr
				+ ", incomeAmount=" + incomeAmount + ", expenseAmount=" + expenseAmount + ", remark=" + remark
				+ ", markForDelete=" + markForDelete + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", bankAccount=" + bankAccount + ", paymentTypeId=" + paymentTypeId
				+ "]";
	}

}

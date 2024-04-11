package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BankPaymetDetailsToIv")
public class BankPaymetDetailsToIv implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bankPaymetDetailsToIvId;
	
	private Long moduleId;
	
	private String moduleNo;
	
	private String details;
	
	private short moduleIdentifier;
	
	private short bankPaymentTypeId;

	private Integer branchId;
	
	private Integer companyId;
	
	private String transactionNumber;

	private String transactionDateStr;
	
	private Date transactionDate;
	
	private String upiId;
	
	private Long partyBankId;
	
	private String partyAccountNumber;
	
	private String payerName;
	
	private String cardNo;
	
	private String mobileNumber;
	
	private String payeeName;
	
	private String chequeGivenTo;
	
	private String chequeGivenBy;
	
	private String providerReferenceId;
	
	private double incomeAmount;
	
	private double expenseAmount;
	
	private String bankAccountNumber;
	
	private String remark;
	
	private String bankAccount; // party Bank Name
	
	private boolean markForDelete;
	
	public Long getBankPaymetDetailsToIvId() {
		return bankPaymetDetailsToIvId;
	}

	public void setBankPaymetDetailsToIvId(Long bankPaymetDetailsToIvId) {
		this.bankPaymetDetailsToIvId = bankPaymetDetailsToIvId;
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

	public short getBankPaymentTypeId() {
		return bankPaymentTypeId;
	}

	public void setBankPaymentTypeId(short bankPaymentTypeId) {
		this.bankPaymentTypeId = bankPaymentTypeId;
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

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getTransactionDateStr() {
		return transactionDateStr;
	}

	public void setTransactionDateStr(String transactionDateStr) {
		this.transactionDateStr = transactionDateStr;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public Long getPartyBankId() {
		return partyBankId;
	}

	public void setPartyBankId(Long partyBankId) {
		this.partyBankId = partyBankId;
	}

	public String getPartyAccountNumber() {
		return partyAccountNumber;
	}

	public void setPartyAccountNumber(String partyAccountNumber) {
		this.partyAccountNumber = partyAccountNumber;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getChequeGivenTo() {
		return chequeGivenTo;
	}

	public void setChequeGivenTo(String chequeGivenTo) {
		this.chequeGivenTo = chequeGivenTo;
	}

	public String getChequeGivenBy() {
		return chequeGivenBy;
	}

	public void setChequeGivenBy(String chequeGivenBy) {
		this.chequeGivenBy = chequeGivenBy;
	}

	public String getProviderReferenceId() {
		return providerReferenceId;
	}

	public void setProviderReferenceId(String providerReferenceId) {
		this.providerReferenceId = providerReferenceId;
	}

	public double getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}


	@Override
	public String toString() {
		return "BankPaymetDetailsToIv [bankPaymetDetailsToIvId=" + bankPaymetDetailsToIvId + ", moduleId=" + moduleId
				+ ", moduleNo=" + moduleNo + ", details=" + details + ", moduleIdentifier=" + moduleIdentifier
				+ ", bankPaymentTypeId=" + bankPaymentTypeId + ", branchId=" + branchId + ", companyId=" + companyId
				+ ", transactionNumber=" + transactionNumber + ", transactionDateStr=" + transactionDateStr
				+ ", transactionDate=" + transactionDate + ", upiId=" + upiId + ", partyBankId=" + partyBankId
				+ ", partyAccountNumber=" + partyAccountNumber + ", payerName=" + payerName + ", cardNo=" + cardNo
				+ ", mobileNumber=" + mobileNumber + ", payeeName=" + payeeName + ", chequeGivenTo=" + chequeGivenTo
				+ ", chequeGivenBy=" + chequeGivenBy + ", providerReferenceId=" + providerReferenceId
				+ ", incomeAmount=" + incomeAmount + ", expenseAmount=" + expenseAmount + ", bankAccountNumber="
				+ bankAccountNumber + ", remark=" + remark + ", markForDelete=" + markForDelete + "]";
	}


}

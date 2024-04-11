package org.fleetopgroup.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BankPayment")
public class BankPayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bankPaymentId;
	
	private Long moduleId;
	
	private Long moduleNo;
	
	private short moduleIdentifier;
	
	private short paymentTypeId;
	
	private Integer branchId;
	
	private Integer companyId;
	
	private String transactionNumber;

	private Date transactionDate;
	
	private Date chequeTransactionDate;
	
	private Long bankAccountId;
	
	private String upiId;
	
	private Date createOn;
	
	private Long createdBy;
	
	private Date lastUpdatedOn;
	
	private Long lastUpdatedBy;
	
	private Double amount;
	
	//private String cardNo;
	
	private String mobileNumber;

	private String payerName;
	
	//private String payeeName;
	
	private Long partyBankId;
	
	private String partyAccountNumber;
	
	//private String chequeGivenTo;
	
	private String chequeGivenBy;
	
	private boolean markForDelete;

	
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Long getBankPaymentId() {
		return bankPaymentId;
	}

	public void setBankPaymentId(Long bankPaymentId) {
		this.bankPaymentId = bankPaymentId;
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

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

//	public String getCardNo() {
//		return cardNo;
//	}
//
//	public void setCardNo(String cardNo) {
//		this.cardNo = cardNo;
//	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

//	public String getPayeeName() {
//		return payeeName;
//	}
//
//	public void setPayeeName(String payeeName) {
//		this.payeeName = payeeName;
//	}

//	public String getChequeGivenTo() {
//		return chequeGivenTo;
//	}
//
//	public void setChequeGivenTo(String chequeGivenTo) {
//		this.chequeGivenTo = chequeGivenTo;
//	}

	public String getChequeGivenBy() {
		return chequeGivenBy;
	}

	public void setChequeGivenBy(String chequeGivenBy) {
		this.chequeGivenBy = chequeGivenBy;
	}

	public String getPartyAccountNumber() {
		return partyAccountNumber;
	}

	public void setPartyAccountNumber(String partyAccountNumber) {
		this.partyAccountNumber = partyAccountNumber;
	}

	public Long getPartyBankId() {
		return partyBankId;
	}

	public void setPartyBankId(Long partyBankId) {
		this.partyBankId = partyBankId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getModuleNo() {
		return moduleNo;
	}

	public void setModuleNo(Long moduleNo) {
		this.moduleNo = moduleNo;
	}

	public Date getChequeTransactionDate() {
		return chequeTransactionDate;
	}

	public void setChequeTransactionDate(Date chequeTransactionDate) {
		this.chequeTransactionDate = chequeTransactionDate;
	}

	@Override
	public String toString() {
		return "BankPayment [bankPaymentId=" + bankPaymentId + ", moduleId=" + moduleId + ", moduleIdentifier="
				+ moduleIdentifier + ", paymentTypeId=" + paymentTypeId + ", branchId=" + branchId + ", companyId="
				+ companyId + ", transactionNumber=" + transactionNumber + ", transactionDate=" + transactionDate
				+ ", bankAccountId=" + bankAccountId + ", upiId=" + upiId + ", createOn=" + createOn + ", createdBy="
				+ createdBy + ", lastUpdatedOn=" + lastUpdatedOn + ", lastUpdatedBy=" + lastUpdatedBy + ", amount="
				+ amount + ", mobileNumber=" + mobileNumber + ", payerName=" + payerName + ", partyBankId="
				+ partyBankId + ", partyAccountNumber=" + partyAccountNumber + ", chequeGivenBy=" + chequeGivenBy
				+ ", markForDelete=" + markForDelete + "]";
	}

	

}
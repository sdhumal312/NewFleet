package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class BankPaymentDto {

	private Long bankPaymentId;
	
	private Long moduleId;
	
	private Long moduleNo;

	private String moduleNoStr;
	
	private String details;
	
	private short moduleIdentifier;
	
	private String bankPaymentType;
	
	private short bankPaymentTypeId;
	
	private Integer branchId;
	
	private Integer companyId;
	
	private String transactionNumber;

	private Date transactionDate;
	
	private String transactionDateStr;
	
	private Long bankAccountId;
	
	private String bankAccount; // party Bank Name
	
	private String upiId;
	
	private Date createOn;
	
	private Double amount;
	
	private Long partyBankId;
	
	private String partyAccountNumber;
	
	private String payerName;
	
	private String cardNo;
	
	private String mobileNumber;
	
	private String payeeName;
	
	private String chequeGivenTo;
	
	private String chequeGivenBy;
	
	private boolean isPaymentModeChange;
	
	private String providerReferenceId;
	
	private Long createdBy;
	
	private Date lastUpdatedOn;
	
	private Long lastUpdatedBy;
	
	private double incomeAmount;
	
	private double expenseAmount;
	
	private String bankAccountNumber;
	
	private boolean markForDelete;
	
	private boolean allowBankPaymentDetails;
	
	private Long ivBranchId;
	
	private Integer ivGroupId; 
	
	private String remark;
	
	private Date chequeTransactionDate;
	
	private String chequeTransactionDateStr;

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

	public Long getPartyBankId() {
		return partyBankId;
	}

	public void setPartyBankId(Long partyBankId) {
		this.partyBankId = partyBankId;
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

	public boolean isPaymentModeChange() {
		return isPaymentModeChange;
	}

	public void setPaymentModeChange(boolean isPaymentModeChange) {
		this.isPaymentModeChange = isPaymentModeChange;
	}

	public String getProviderReferenceId() {
		return providerReferenceId;
	}

	public void setProviderReferenceId(String providerReferenceId) {
		this.providerReferenceId = providerReferenceId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getTransactionDateStr() {
		return transactionDateStr;
	}

	public void setTransactionDateStr(String transactionDateStr) {
		this.transactionDateStr = transactionDateStr;
	}

	public String getBankPaymentType() {
		return bankPaymentType;
	}

	public void setBankPaymentType(String bankPaymentType) {
		this.bankPaymentType = bankPaymentType;
	}

	public String getPartyAccountNumber() {
		return partyAccountNumber;
	}

	public void setPartyAccountNumber(String partyAccountNumber) {
		this.partyAccountNumber = partyAccountNumber;
	}

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


	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
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

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public boolean isAllowBankPaymentDetails() {
		return allowBankPaymentDetails;
	}

	public void setAllowBankPaymentDetails(boolean allowBankPaymentDetails) {
		this.allowBankPaymentDetails = allowBankPaymentDetails;
	}

	public Long getModuleNo() {
		return moduleNo;
	}

	public void setModuleNo(Long moduleNo) {
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

	public String getModuleNoStr() {
		return moduleNoStr;
	}

	public void setModuleNoStr(String moduleNoStr) {
		this.moduleNoStr = moduleNoStr;
	}

	public Long getIvBranchId() {
		return ivBranchId;
	}

	public void setIvBranchId(Long ivBranchId) {
		this.ivBranchId = ivBranchId;
	}

	public Integer getIvGroupId() {
		return ivGroupId;
	}

	public void setIvGroupId(Integer ivGroupId) {
		this.ivGroupId = ivGroupId;
	}

	public Date getChequeTransactionDate() {
		return chequeTransactionDate;
	}

	public void setChequeTransactionDate(Date chequeTransactionDate) {
		this.chequeTransactionDate = chequeTransactionDate;
	}

	public String getChequeTransactionDateStr() {
		return chequeTransactionDateStr;
	}

	public void setChequeTransactionDateStr(String chequeTransactionDateStr) {
		this.chequeTransactionDateStr = chequeTransactionDateStr;
	}

	@Override
	public String toString() {
		return "BankPaymentDto [bankPaymentId=" + bankPaymentId + ", moduleId=" + moduleId + ", moduleIdentifier="
				+ moduleIdentifier + ", bankPaymentTypeId=" + bankPaymentTypeId + ", branchId=" + branchId + ", companyId="
				+ companyId + ", transactionNumber=" + transactionNumber + ", transactionDate=" + transactionDate
				+ ", transactionDateStr=" + transactionDateStr + ", bankAccountId=" + bankAccountId + ", upiId=" + upiId
				+ ", createOn=" + createOn + ", amount=" + amount + ", partyBankId="
				+ partyBankId + ", payerName=" + payerName + ", cardNo=" + cardNo + ", mobileNumber=" + mobileNumber
				+ ", payeeName=" + payeeName + ", chequeGivenTo=" + chequeGivenTo + ", chequeGivenBy=" + chequeGivenBy
				+ ", isPaymentModeChange=" + isPaymentModeChange + ", providerReferenceId=" + providerReferenceId
				+ ", markForDelete=" + markForDelete + "]";
	}
	

}

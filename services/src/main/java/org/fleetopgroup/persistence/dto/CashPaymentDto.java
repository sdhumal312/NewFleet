package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class CashPaymentDto {
	
	private Long cashPaymentId;
	
	private Long moduleId;
	
	private Long moduleNo;
	
	private short moduleIdentifier;
	
	private Integer branchId;
	
	private Integer companyId;

	private Date transactionDate;
	
	private Date createOn;
	
	private Long createdBy;
	
	private Date lastUpdatedOn;
	
	private Long lastUpdatedBy;
	
	private Long ivBranchId;
	
	private Integer ivGroupId; 
	
	private Double amount;
	
	private boolean markForDelete;

	public Long getCashPaymentId() {
		return cashPaymentId;
	}

	public void setCashPaymentId(Long cashPaymentId) {
		this.cashPaymentId = cashPaymentId;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getModuleNo() {
		return moduleNo;
	}

	public void setModuleNo(Long moduleNo) {
		this.moduleNo = moduleNo;
	}

	public Long getIvBranchId() {
		return ivBranchId;
	}

	@Override
	public String toString() {
		return "CashPaymentDto [cashPaymentId=" + cashPaymentId + ", moduleId=" + moduleId + ", moduleNo=" + moduleNo
				+ ", moduleIdentifier=" + moduleIdentifier + ", branchId=" + branchId + ", companyId=" + companyId
				+ ", transactionDate=" + transactionDate + ", createOn=" + createOn + ", createdBy=" + createdBy
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", lastUpdatedBy=" + lastUpdatedBy + ", ivBranchId=" + ivBranchId
				+ ", ivGroupId=" + ivGroupId + ", amount=" + amount + ", markForDelete=" + markForDelete + "]";
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	
}

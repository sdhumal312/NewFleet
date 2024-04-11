package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class BankAccountDto {


	private Long bankAccountId;
	
	private Long bankId;
	
	private String bankName;
	
	private String name;
	
	private String accountNumber;
	
	private String IFSCCode;
	
	private	String MICRCode;
	
	private String contactNumber;
	
	private String description;
	
	private Integer companyId;
	
	private Long createdById;
	
	private Long lastModifiedById;
	
	private Timestamp	createdOn;
	
	private Timestamp	lastModifiedOn;
	

	
	public Long getBankAccountId() {
		return bankAccountId;
	}



	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}



	public Long getBankId() {
		return bankId;
	}



	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}



	public String getBankName() {
		return bankName;
	}



	public void setBankName(String bankName) {
		this.bankName = bankName;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getAccountNumber() {
		return accountNumber;
	}



	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}



	public String getIFSCCode() {
		return IFSCCode;
	}



	public void setIFSCCode(String iFSCCode) {
		IFSCCode = iFSCCode;
	}



	public String getMICRCode() {
		return MICRCode;
	}



	public void setMICRCode(String mICRCode) {
		MICRCode = mICRCode;
	}



	public String getContactNumber() {
		return contactNumber;
	}



	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Integer getCompanyId() {
		return companyId;
	}



	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}



	public Long getCreatedById() {
		return createdById;
	}



	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}



	public Long getLastModifiedById() {
		return lastModifiedById;
	}



	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}



	public Timestamp getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}



	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}



	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}



	@Override
	public String toString() {
		return "BankAccount [bankAccountId=" + bankAccountId + ", bankId=" + bankId + ", name=" + name
				+ ", accountNumber=" + accountNumber + ", IFSCCode=" + IFSCCode + ", MICRCode=" + MICRCode
				+ ", contactNumber=" + contactNumber + ", description=" + description + ", companyId=" + companyId
				+ ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById + ", createdOn=" + createdOn
				+ ", lastModifiedOn=" + lastModifiedOn + "]";
	}
	
	

}

package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BankAccount")
public class BankAccount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bankAccountId")
	private Long bankAccountId;
	
	@Column(name = "bankId", nullable = false)
	private Long bankId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "accountNumber")
	private String accountNumber;
	
	@Column(name = "IFSCCode")
	private String IFSCCode;
	
	@Column(name = "MICRCode")
	private	String MICRCode;
	
	@Column(name = "contactNumber")
	private String contactNumber;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "createdOn", nullable = false, updatable = false)
	private Timestamp	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Timestamp	lastModifiedOn;
	
	private boolean  markForDelete;
	
	
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public BankAccount() {
		super();
	}

	public BankAccount(Long bankAccountId, Long bankId, String name, String accountNumber, String iFSCCode,
			String mICRCode, String contactNumber, String description, Integer companyId, Long createdById,
			Long lastModifiedById, Timestamp createdOn, Timestamp lastModifiedOn) {
		super();
		this.bankAccountId = bankAccountId;
		this.bankId = bankId;
		this.name = name;
		this.accountNumber = accountNumber;
		IFSCCode = iFSCCode;
		MICRCode = mICRCode;
		this.contactNumber = contactNumber;
		this.description = description;
		this.companyId = companyId;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IFSCCode == null) ? 0 : IFSCCode.hashCode());
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((bankAccountId == null) ? 0 : bankAccountId.hashCode());
		result = prime * result + ((bankId == null) ? 0 : bankId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		if (IFSCCode == null) {
			if (other.IFSCCode != null)
				return false;
		} else if (!IFSCCode.equals(other.IFSCCode))
			return false;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (bankAccountId == null) {
			if (other.bankAccountId != null)
				return false;
		} else if (!bankAccountId.equals(other.bankAccountId))
			return false;
		if (bankId == null) {
			if (other.bankId != null)
				return false;
		} else if (!bankId.equals(other.bankId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
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

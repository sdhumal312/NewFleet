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
@Table(name = "SubCompany")
public class SubCompany implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subCompanyId")
	private Long subCompanyId;

	@Column(name = "subCompanyName", nullable = false, unique = true, length = 250)
	private String subCompanyName;

	@Column(name = "subCompanyAddress", length = 200)
	private String subCompanyAddress;

	@Column(name = "subCompanyCity", length = 100)
	private String subCompanyCity;

	@Column(name = "subCompanyState", length = 100)
	private String subCompanyState;

	@Column(name = "subCompanyCountry", length = 100)
	private String subCompanyCountry;

	@Column(name = "subCompanyPinCode", length = 6)
	private String subCompanyPinCode;

	@Column(name = "subCompanyWebsite", length = 150)
	private String subCompanyWebsite;

	@Column(name = "subCompanyEmail", length = 150)
	private String subCompanyEmail;

	@Column(name = "subCompanyMobileNumber", length = 15)
	private String subCompanyMobileNumber;

	@Column(name = "subCompanyType", length = 200)
	private String subCompanyType;

	@Column(name = "subCompanyPanNo", length = 50)
	private String subCompanyPanNo;

	@Column(name = "subCompanyTanNo", length = 50)
	private String subCompanyTanNo;

	@Column(name = "subCompanyTaxNo", length = 50)
	private String subCompanyTaxNo;

	@Column(name = "subCompanyTinNo", length = 50)
	private String subCompanyTinNo;

	@Column(name = "subCompanyCinNo", length = 50)
	private String subCompanyCinNo;

	@Column(name = "subCompanyAbout", length = 500)
	private String subCompanyAbout;

	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedBy")
	private Long lastUpdatedBy;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public SubCompany() {
		super();
	}

	public SubCompany(Long subCompanyId, String subCompanyName, String subCompanyAddress, String subCompanyCity,
			String subCompanyState, String subCompanyCountry, String subCompanyPinCode, String subCompanyWebsite,
			String subCompanyEmail, String subCompanyMobileNumber, String subCompanyType, String subCompanyPanNo,
			String subCompanyTanNo, String subCompanyTaxNo, String subCompanyTinNo, String subCompanyCinNo,
			String subCompanyAbout, Long createdById, Long lastUpdatedBy, Date creationDate, Date lastUpdatedOn,
			Integer companyId, boolean markForDelete) {
		super();
		this.subCompanyId = subCompanyId;
		this.subCompanyName = subCompanyName;
		this.subCompanyAddress = subCompanyAddress;
		this.subCompanyCity = subCompanyCity;
		this.subCompanyState = subCompanyState;
		this.subCompanyCountry = subCompanyCountry;
		this.subCompanyPinCode = subCompanyPinCode;
		this.subCompanyWebsite = subCompanyWebsite;
		this.subCompanyEmail = subCompanyEmail;
		this.subCompanyMobileNumber = subCompanyMobileNumber;
		this.subCompanyType = subCompanyType;
		this.subCompanyPanNo = subCompanyPanNo;
		this.subCompanyTanNo = subCompanyTanNo;
		this.subCompanyTaxNo = subCompanyTaxNo;
		this.subCompanyTinNo = subCompanyTinNo;
		this.subCompanyCinNo = subCompanyCinNo;
		this.subCompanyAbout = subCompanyAbout;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((subCompanyAbout == null) ? 0 : subCompanyAbout.hashCode());
		result = prime * result + ((subCompanyAddress == null) ? 0 : subCompanyAddress.hashCode());
		result = prime * result + ((subCompanyCinNo == null) ? 0 : subCompanyCinNo.hashCode());
		result = prime * result + ((subCompanyCity == null) ? 0 : subCompanyCity.hashCode());
		result = prime * result + ((subCompanyCountry == null) ? 0 : subCompanyCountry.hashCode());
		result = prime * result + ((subCompanyEmail == null) ? 0 : subCompanyEmail.hashCode());
		result = prime * result + ((subCompanyId == null) ? 0 : subCompanyId.hashCode());
		result = prime * result + ((subCompanyMobileNumber == null) ? 0 : subCompanyMobileNumber.hashCode());
		result = prime * result + ((subCompanyName == null) ? 0 : subCompanyName.hashCode());
		result = prime * result + ((subCompanyPanNo == null) ? 0 : subCompanyPanNo.hashCode());
		result = prime * result + ((subCompanyPinCode == null) ? 0 : subCompanyPinCode.hashCode());
		result = prime * result + ((subCompanyState == null) ? 0 : subCompanyState.hashCode());
		result = prime * result + ((subCompanyTanNo == null) ? 0 : subCompanyTanNo.hashCode());
		result = prime * result + ((subCompanyTaxNo == null) ? 0 : subCompanyTaxNo.hashCode());
		result = prime * result + ((subCompanyTinNo == null) ? 0 : subCompanyTinNo.hashCode());
		result = prime * result + ((subCompanyType == null) ? 0 : subCompanyType.hashCode());
		result = prime * result + ((subCompanyWebsite == null) ? 0 : subCompanyWebsite.hashCode());
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
		SubCompany other = (SubCompany) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (lastUpdatedBy == null) {
			if (other.lastUpdatedBy != null)
				return false;
		} else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (subCompanyAbout == null) {
			if (other.subCompanyAbout != null)
				return false;
		} else if (!subCompanyAbout.equals(other.subCompanyAbout))
			return false;
		if (subCompanyAddress == null) {
			if (other.subCompanyAddress != null)
				return false;
		} else if (!subCompanyAddress.equals(other.subCompanyAddress))
			return false;
		if (subCompanyCinNo == null) {
			if (other.subCompanyCinNo != null)
				return false;
		} else if (!subCompanyCinNo.equals(other.subCompanyCinNo))
			return false;
		if (subCompanyCity == null) {
			if (other.subCompanyCity != null)
				return false;
		} else if (!subCompanyCity.equals(other.subCompanyCity))
			return false;
		if (subCompanyCountry == null) {
			if (other.subCompanyCountry != null)
				return false;
		} else if (!subCompanyCountry.equals(other.subCompanyCountry))
			return false;
		if (subCompanyEmail == null) {
			if (other.subCompanyEmail != null)
				return false;
		} else if (!subCompanyEmail.equals(other.subCompanyEmail))
			return false;
		if (subCompanyId == null) {
			if (other.subCompanyId != null)
				return false;
		} else if (!subCompanyId.equals(other.subCompanyId))
			return false;
		if (subCompanyMobileNumber == null) {
			if (other.subCompanyMobileNumber != null)
				return false;
		} else if (!subCompanyMobileNumber.equals(other.subCompanyMobileNumber))
			return false;
		if (subCompanyName == null) {
			if (other.subCompanyName != null)
				return false;
		} else if (!subCompanyName.equals(other.subCompanyName))
			return false;
		if (subCompanyPanNo == null) {
			if (other.subCompanyPanNo != null)
				return false;
		} else if (!subCompanyPanNo.equals(other.subCompanyPanNo))
			return false;
		if (subCompanyPinCode == null) {
			if (other.subCompanyPinCode != null)
				return false;
		} else if (!subCompanyPinCode.equals(other.subCompanyPinCode))
			return false;
		if (subCompanyState == null) {
			if (other.subCompanyState != null)
				return false;
		} else if (!subCompanyState.equals(other.subCompanyState))
			return false;
		if (subCompanyTanNo == null) {
			if (other.subCompanyTanNo != null)
				return false;
		} else if (!subCompanyTanNo.equals(other.subCompanyTanNo))
			return false;
		if (subCompanyTaxNo == null) {
			if (other.subCompanyTaxNo != null)
				return false;
		} else if (!subCompanyTaxNo.equals(other.subCompanyTaxNo))
			return false;
		if (subCompanyTinNo == null) {
			if (other.subCompanyTinNo != null)
				return false;
		} else if (!subCompanyTinNo.equals(other.subCompanyTinNo))
			return false;
		if (subCompanyType == null) {
			if (other.subCompanyType != null)
				return false;
		} else if (!subCompanyType.equals(other.subCompanyType))
			return false;
		if (subCompanyWebsite == null) {
			if (other.subCompanyWebsite != null)
				return false;
		} else if (!subCompanyWebsite.equals(other.subCompanyWebsite))
			return false;
		return true;
	}

	public Long getSubCompanyId() {
		return subCompanyId;
	}

	public void setSubCompanyId(Long subCompanyId) {
		this.subCompanyId = subCompanyId;
	}

	public String getSubCompanyName() {
		return subCompanyName;
	}

	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}

	public String getSubCompanyAddress() {
		return subCompanyAddress;
	}

	public void setSubCompanyAddress(String subCompanyAddress) {
		this.subCompanyAddress = subCompanyAddress;
	}

	public String getSubCompanyCity() {
		return subCompanyCity;
	}

	public void setSubCompanyCity(String subCompanyCity) {
		this.subCompanyCity = subCompanyCity;
	}

	public String getSubCompanyState() {
		return subCompanyState;
	}

	public void setSubCompanyState(String subCompanyState) {
		this.subCompanyState = subCompanyState;
	}

	public String getSubCompanyCountry() {
		return subCompanyCountry;
	}

	public void setSubCompanyCountry(String subCompanyCountry) {
		this.subCompanyCountry = subCompanyCountry;
	}

	public String getSubCompanyPinCode() {
		return subCompanyPinCode;
	}

	public void setSubCompanyPinCode(String subCompanyPinCode) {
		this.subCompanyPinCode = subCompanyPinCode;
	}

	public String getSubCompanyWebsite() {
		return subCompanyWebsite;
	}

	public void setSubCompanyWebsite(String subCompanyWebsite) {
		this.subCompanyWebsite = subCompanyWebsite;
	}

	public String getSubCompanyEmail() {
		return subCompanyEmail;
	}

	public void setSubCompanyEmail(String subCompanyEmail) {
		this.subCompanyEmail = subCompanyEmail;
	}

	public String getSubCompanyMobileNumber() {
		return subCompanyMobileNumber;
	}

	public void setSubCompanyMobileNumber(String subCompanyMobileNumber) {
		this.subCompanyMobileNumber = subCompanyMobileNumber;
	}

	public String getSubCompanyType() {
		return subCompanyType;
	}

	public void setSubCompanyType(String subCompanyType) {
		this.subCompanyType = subCompanyType;
	}

	public String getSubCompanyPanNo() {
		return subCompanyPanNo;
	}

	public void setSubCompanyPanNo(String subCompanyPanNo) {
		this.subCompanyPanNo = subCompanyPanNo;
	}

	public String getSubCompanyTanNo() {
		return subCompanyTanNo;
	}

	public void setSubCompanyTanNo(String subCompanyTanNo) {
		this.subCompanyTanNo = subCompanyTanNo;
	}

	public String getSubCompanyTaxNo() {
		return subCompanyTaxNo;
	}

	public void setSubCompanyTaxNo(String subCompanyTaxNo) {
		this.subCompanyTaxNo = subCompanyTaxNo;
	}

	public String getSubCompanyTinNo() {
		return subCompanyTinNo;
	}

	public void setSubCompanyTinNo(String subCompanyTinNo) {
		this.subCompanyTinNo = subCompanyTinNo;
	}

	public String getSubCompanyCinNo() {
		return subCompanyCinNo;
	}

	public void setSubCompanyCinNo(String subCompanyCinNo) {
		this.subCompanyCinNo = subCompanyCinNo;
	}

	public String getSubCompanyAbout() {
		return subCompanyAbout;
	}

	public void setSubCompanyAbout(String subCompanyAbout) {
		this.subCompanyAbout = subCompanyAbout;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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

	@Override
	public String toString() {
		return "SubCompany [subCompanyId=" + subCompanyId + ", subCompanyName=" + subCompanyName
				+ ", subCompanyAddress=" + subCompanyAddress + ", subCompanyCity=" + subCompanyCity
				+ ", subCompanyState=" + subCompanyState + ", subCompanyCountry=" + subCompanyCountry
				+ ", subCompanyPinCode=" + subCompanyPinCode + ", subCompanyWebsite=" + subCompanyWebsite
				+ ", subCompanyEmail=" + subCompanyEmail + ", subCompanyMobileNumber=" + subCompanyMobileNumber
				+ ", subCompanyType=" + subCompanyType + ", subCompanyPanNo=" + subCompanyPanNo + ", subCompanyTanNo="
				+ subCompanyTanNo + ", subCompanyTaxNo=" + subCompanyTaxNo + ", subCompanyTinNo=" + subCompanyTinNo
				+ ", subCompanyCinNo=" + subCompanyCinNo + ", subCompanyAbout=" + subCompanyAbout + ", createdById="
				+ createdById + ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate=" + creationDate
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}



}

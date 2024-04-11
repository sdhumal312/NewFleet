package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class SubCompanyDto{
	private Long subCompanyId;
	
	private String subCompanyName;
	
	private String subCompanyAddress;
	
	private String subCompanyCity;
	
	private String subCompanyState;
	
	private String subCompanyCountry;
	
	private String subCompanyPinCode;
	
	private String subCompanyWebsite;
	
	private String subCompanyEmail;
	
	private String subCompanyMobileNumber;
	
	private String subCompanyType;
	
	private String subCompanyPanNo;
	
	private String subCompanyTanNo;
	
	private String subCompanyTaxNo;
	
	private String subCompanyTinNo;
	
	private String subCompanyCinNo;
	
	private String subCompanyAbout;
	
	private Long createdById;

	private Long lastUpdatedById;
	
	private String createdBy;

	private String lastUpdatedBy;
	
	private Timestamp createdOn;

	private Timestamp lastUpdatedOn;
	
	private String creationDate;

	private String lastUpdatedDate;
	
	private Integer companyId;
	
	private String companyName;

	boolean markForDelete;

	public SubCompanyDto() {
		super();
	}

	public SubCompanyDto(Long subCompanyId, String subCompanyName, String subCompanyAddress, String subCompanyCity,
			String subCompanyState, String subCompanyCountry, String subCompanyPinCode, String subCompanyWebsite,
			String subCompanyEmail, String subCompanyMobileNumber, String subCompanyType, String subCompanyPanNo,
			String subCompanyTanNo, String subCompanyTaxNo, String subCompanyTinNo, String subCompanyCinNo,
			String subCompanyAbout, Long createdById, Long lastUpdatedById, String createdBy, String lastUpdatedBy,
			Timestamp createdOn, Timestamp lastUpdatedOn, String creationDate, String lastUpdatedDate,
			Integer companyId, String companyName, boolean markForDelete) {
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
		this.lastUpdatedById = lastUpdatedById;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.createdOn = createdOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.creationDate = creationDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.companyId = companyId;
		this.companyName = companyName;
		this.markForDelete = markForDelete;
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

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "SubCompanyDto [subCompanyId=" + subCompanyId + ", subCompanyName=" + subCompanyName
				+ ", subCompanyAddress=" + subCompanyAddress + ", subCompanyCity=" + subCompanyCity
				+ ", subCompanyState=" + subCompanyState + ", subCompanyCountry=" + subCompanyCountry
				+ ", subCompanyPinCode=" + subCompanyPinCode + ", subCompanyWebsite=" + subCompanyWebsite
				+ ", subCompanyEmail=" + subCompanyEmail + ", subCompanyMobileNumber=" + subCompanyMobileNumber
				+ ", subCompanyType=" + subCompanyType + ", subCompanyPanNo=" + subCompanyPanNo + ", subCompanyTanNo="
				+ subCompanyTanNo + ", subCompanyTaxNo=" + subCompanyTaxNo + ", subCompanyTinNo=" + subCompanyTinNo
				+ ", subCompanyCinNo=" + subCompanyCinNo + ", subCompanyAbout=" + subCompanyAbout + ", createdById="
				+ createdById + ", lastUpdatedById=" + lastUpdatedById + ", createdBy=" + createdBy + ", lastUpdatedBy="
				+ lastUpdatedBy + ", createdOn=" + createdOn + ", lastUpdatedOn=" + lastUpdatedOn + ", creationDate="
				+ creationDate + ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId + ", companyName="
				+ companyName + ", markForDelete=" + markForDelete + "]";
	}
	

	

}

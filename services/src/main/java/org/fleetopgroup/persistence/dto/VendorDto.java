package org.fleetopgroup.persistence.dto;

import java.util.Date;

/**
 * @author fleetop
 *
 *
 *
 */

public class VendorDto {

	private Integer vendorId;
	
	private Integer vendorNumber;
	
	private String vendorType;
	
	private String vendorLocation;
	
	private String vendorTerm;
	
	private short  vendorTermId;

	private String vendorGSTNO;

	private String vendorPanNO;
	
	private String vendorGSTRegistered;
	
	private short  vendorGSTRegisteredId;
	
	private String vendorCreditLimit;

	private String vendorAdvancePaid;

	private String vendorName;

	private String vendorPhone;

	private String vendorWebsite;

	private String vendorAddress1;

	private String vendorAddress2;

	private String vendorCity;

	private String vendorState;

	private String vendorCountry;

	private String vendorPincode;

	private String vendorRemarks;

	private String vendorFirName;

	private String vendorFirPhone;

	private String vendorFirEmail;

	private String vendorSecName;

	private String vendorSecPhone;

	private String vendorSecEmail;

	private String vendorBankName;

	private String vendorBankBranch;

	private String vendorBankAccno;

	private String vendorBankIfsc;

	private String createdBy;
	
	private Long createdById;
	
	private String created;
	
	private String lastModifiedBy;
	
	private Long lastModifiedById;
	
	private String lastupdated;
	
	private Date  createdOn;
	
	private Date  lastupdatedOn;
	
	private Long vendorTypeId;
	
	private Integer	companyId;
	
	private short  typeOfPaymentId;
	
	private Long  paidAmount;
	
	private Long  balanceAmount;
	
	private Long SelectService_id;
	
	private short	ownPetrolPump;
	
	private String	ownPetrolPumpStr;
	
	private Double vendorTDSPercent;
	
	
	public VendorDto() {
		super();
	}


	public VendorDto(Integer vendorId, String vendorName) {
		super();
		this.vendorId = vendorId;
		this.vendorName = vendorName;
	}

	public short getTypeOfPaymentId() {
		return typeOfPaymentId;
	}

	public void setTypeOfPaymentId(short typeOfPaymentId) {
		this.typeOfPaymentId = typeOfPaymentId;
	}

	public Long getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Long paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Long getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Long balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId
	 *            the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getVendorNumber() {
		return vendorNumber;
	}

	public void setVendorNumber(Integer vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	/**
	 * @return the vendorType
	 */
	public String getVendorType() {
		return vendorType;
	}

	/**
	 * @param vendorType
	 *            the vendorType to set
	 */
	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	/**
	 * @return the vendorLocation
	 */
	public String getVendorLocation() {
		return vendorLocation;
	}

	/**
	 * @param vendorLocation
	 *            the vendorLocation to set
	 */
	public void setVendorLocation(String vendorLocation) {
		this.vendorLocation = vendorLocation;
	}

	/**
	 * @return the vendorTerm
	 */
	public String getVendorTerm() {
		return vendorTerm;
	}

	/**
	 * @param vendorTerm
	 *            the vendorTerm to set
	 */
	public void setVendorTerm(String vendorTerm) {
		this.vendorTerm = vendorTerm;
	}

	/**
	 * @return the vendorPanNO
	 */
	public String getVendorPanNO() {
		return vendorPanNO;
	}

	/**
	 * @param vendorPanNO
	 *            the vendorPanNO to set
	 */
	public void setVendorPanNO(String vendorPanNO) {
		this.vendorPanNO = vendorPanNO;
	}

	/**
	 * @return the vendorGSTNO
	 */
	public String getVendorGSTNO() {
		return vendorGSTNO;
	}

	/**
	 * @param vendorGSTNO
	 *            the vendorGSTNO to set
	 */
	public void setVendorGSTNO(String vendorGSTNO) {
		this.vendorGSTNO = vendorGSTNO;
	}

	/**
	 * @return the vendorGSTRegistered
	 */
	public String getVendorGSTRegistered() {
		return vendorGSTRegistered;
	}

	/**
	 * @return the vendorTermId
	 */
	public short getVendorTermId() {
		return vendorTermId;
	}

	/**
	 * @param vendorTermId the vendorTermId to set
	 */
	public void setVendorTermId(short vendorTermId) {
		this.vendorTermId = vendorTermId;
	}

	/**
	 * @return the vendorGSTRegisteredId
	 */
	public short getVendorGSTRegisteredId() {
		return vendorGSTRegisteredId;
	}

	/**
	 * @param vendorGSTRegisteredId the vendorGSTRegisteredId to set
	 */
	public void setVendorGSTRegisteredId(short vendorGSTRegisteredId) {
		this.vendorGSTRegisteredId = vendorGSTRegisteredId;
	}

	/**
	 * @param vendorGSTRegistered
	 *            the vendorGSTRegistered to set
	 */
	public void setVendorGSTRegistered(String vendorGSTRegistered) {
		this.vendorGSTRegistered = vendorGSTRegistered;
	}

	/**
	 * @return the vendorCreditLimit
	 */
	public String getVendorCreditLimit() {
		return vendorCreditLimit;
	}

	/**
	 * @param vendorCreditLimit
	 *            the vendorCreditLimit to set
	 */
	public void setVendorCreditLimit(String vendorCreditLimit) {
		this.vendorCreditLimit = vendorCreditLimit;
	}

	/**
	 * @return the vendorAdvancePaid
	 */
	public String getVendorAdvancePaid() {
		return vendorAdvancePaid;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	/**
	 * @param vendorAdvancePaid
	 *            the vendorAdvancePaid to set
	 */
	public void setVendorAdvancePaid(String vendorAdvancePaid) {
		this.vendorAdvancePaid = vendorAdvancePaid;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName
	 *            the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @return the vendorPhone
	 */
	public String getVendorPhone() {
		return vendorPhone;
	}

	/**
	 * @param vendorPhone
	 *            the vendorPhone to set
	 */
	public void setVendorPhone(String vendorPhone) {
		this.vendorPhone = vendorPhone;
	}

	/**
	 * @return the vendorWebsite
	 */
	public String getVendorWebsite() {
		return vendorWebsite;
	}

	/**
	 * @param vendorWebsite
	 *            the vendorWebsite to set
	 */
	public void setVendorWebsite(String vendorWebsite) {
		this.vendorWebsite = vendorWebsite;
	}

	/**
	 * @return the vendorAddress1
	 */
	public String getVendorAddress1() {
		return vendorAddress1;
	}

	/**
	 * @param vendorAddress1
	 *            the vendorAddress1 to set
	 */
	public void setVendorAddress1(String vendorAddress1) {
		this.vendorAddress1 = vendorAddress1;
	}

	/**
	 * @return the vendorAddress2
	 */
	public String getVendorAddress2() {
		return vendorAddress2;
	}

	/**
	 * @param vendorAddress2
	 *            the vendorAddress2 to set
	 */
	public void setVendorAddress2(String vendorAddress2) {
		this.vendorAddress2 = vendorAddress2;
	}

	/**
	 * @return the vendorCity
	 */
	public String getVendorCity() {
		return vendorCity;
	}

	/**
	 * @param vendorCity
	 *            the vendorCity to set
	 */
	public void setVendorCity(String vendorCity) {
		this.vendorCity = vendorCity;
	}

	/**
	 * @return the vendorState
	 */
	public String getVendorState() {
		return vendorState;
	}

	/**
	 * @param vendorState
	 *            the vendorState to set
	 */
	public void setVendorState(String vendorState) {
		this.vendorState = vendorState;
	}

	/**
	 * @return the vendorCountry
	 */
	public String getVendorCountry() {
		return vendorCountry;
	}

	/**
	 * @param vendorCountry
	 *            the vendorCountry to set
	 */
	public void setVendorCountry(String vendorCountry) {
		this.vendorCountry = vendorCountry;
	}

	/**
	 * @return the vendorPincode
	 */
	public String getVendorPincode() {
		return vendorPincode;
	}

	/**
	 * @param vendorPincode
	 *            the vendorPincode to set
	 */
	public void setVendorPincode(String vendorPincode) {
		this.vendorPincode = vendorPincode;
	}

	/**
	 * @return the vendorRemarks
	 */
	public String getVendorRemarks() {
		return vendorRemarks;
	}

	/**
	 * @param vendorRemarks
	 *            the vendorRemarks to set
	 */
	public void setVendorRemarks(String vendorRemarks) {
		this.vendorRemarks = vendorRemarks;
	}

	/**
	 * @return the vendorFirName
	 */
	public String getVendorFirName() {
		return vendorFirName;
	}

	/**
	 * @param vendorFirName
	 *            the vendorFirName to set
	 */
	public void setVendorFirName(String vendorFirName) {
		this.vendorFirName = vendorFirName;
	}

	/**
	 * @return the vendorFirPhone
	 */
	public String getVendorFirPhone() {
		return vendorFirPhone;
	}

	/**
	 * @param vendorFirPhone
	 *            the vendorFirPhone to set
	 */
	public void setVendorFirPhone(String vendorFirPhone) {
		this.vendorFirPhone = vendorFirPhone;
	}

	/**
	 * @return the vendorFirEmail
	 */
	public String getVendorFirEmail() {
		return vendorFirEmail;
	}

	/**
	 * @param vendorFirEmail
	 *            the vendorFirEmail to set
	 */
	public void setVendorFirEmail(String vendorFirEmail) {
		this.vendorFirEmail = vendorFirEmail;
	}

	/**
	 * @return the vendorSecName
	 */
	public String getVendorSecName() {
		return vendorSecName;
	}

	/**
	 * @param vendorSecName
	 *            the vendorSecName to set
	 */
	public void setVendorSecName(String vendorSecName) {
		this.vendorSecName = vendorSecName;
	}

	/**
	 * @return the vendorSecPhone
	 */
	public String getVendorSecPhone() {
		return vendorSecPhone;
	}

	/**
	 * @param vendorSecPhone
	 *            the vendorSecPhone to set
	 */
	public void setVendorSecPhone(String vendorSecPhone) {
		this.vendorSecPhone = vendorSecPhone;
	}

	/**
	 * @return the vendorSecEmail
	 */
	public String getVendorSecEmail() {
		return vendorSecEmail;
	}

	/**
	 * @param vendorSecEmail
	 *            the vendorSecEmail to set
	 */
	public void setVendorSecEmail(String vendorSecEmail) {
		this.vendorSecEmail = vendorSecEmail;
	}

	/**
	 * @return the vendorBankName
	 */
	public String getVendorBankName() {
		return vendorBankName;
	}

	/**
	 * @param vendorBankName
	 *            the vendorBankName to set
	 */
	public void setVendorBankName(String vendorBankName) {
		this.vendorBankName = vendorBankName;
	}

	/**
	 * @return the vendorBankBranch
	 */
	public String getVendorBankBranch() {
		return vendorBankBranch;
	}

	/**
	 * @param vendorBankBranch
	 *            the vendorBankBranch to set
	 */
	public void setVendorBankBranch(String vendorBankBranch) {
		this.vendorBankBranch = vendorBankBranch;
	}

	/**
	 * @return the vendorBankAccno
	 */
	public String getVendorBankAccno() {
		return vendorBankAccno;
	}

	/**
	 * @param vendorBankAccno
	 *            the vendorBankAccno to set
	 */
	public void setVendorBankAccno(String vendorBankAccno) {
		this.vendorBankAccno = vendorBankAccno;
	}

	/**
	 * @return the vendorBankIfsc
	 */
	public String getVendorBankIfsc() {
		return vendorBankIfsc;
	}

	/**
	 * @param vendorBankIfsc
	 *            the vendorBankIfsc to set
	 */
	public void setVendorBankIfsc(String vendorBankIfsc) {
		this.vendorBankIfsc = vendorBankIfsc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Long getVendorTypeId() {
		return vendorTypeId;
	}

	public void setVendorTypeId(Long vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the lastupdatedOn
	 */
	public Date getLastupdatedOn() {
		return lastupdatedOn;
	}

	/**
	 * @param lastupdatedOn the lastupdatedOn to set
	 */
	public void setLastupdatedOn(Date lastupdatedOn) {
		this.lastupdatedOn = lastupdatedOn;
	}

	public short getOwnPetrolPump() {
		return ownPetrolPump;
	}

	public void setOwnPetrolPump(short ownPetrolPump) {
		this.ownPetrolPump = ownPetrolPump;
	}

	public String getOwnPetrolPumpStr() {
		return ownPetrolPumpStr;
	}

	public void setOwnPetrolPumpStr(String ownPetrolPumpStr) {
		this.ownPetrolPumpStr = ownPetrolPumpStr;
	}

	public Double getVendorTDSPercent() {
		return vendorTDSPercent;
	}


	public void setVendorTDSPercent(Double vendorTDSPercent) {
		this.vendorTDSPercent = vendorTDSPercent;
	}


	@Override
	public String toString() {
		return "VendorDto [vendorId=" + vendorId + ", vendorNumber=" + vendorNumber + ", vendorType=" + vendorType
				+ ", vendorLocation=" + vendorLocation + ", vendorTerm=" + vendorTerm + ", vendorTermId=" + vendorTermId
				+ ", vendorGSTNO=" + vendorGSTNO + ", vendorPanNO=" + vendorPanNO + ", vendorGSTRegistered="
				+ vendorGSTRegistered + ", vendorGSTRegisteredId=" + vendorGSTRegisteredId + ", vendorCreditLimit="
				+ vendorCreditLimit + ", vendorAdvancePaid=" + vendorAdvancePaid + ", vendorName=" + vendorName
				+ ", vendorPhone=" + vendorPhone + ", vendorWebsite=" + vendorWebsite + ", vendorAddress1="
				+ vendorAddress1 + ", vendorAddress2=" + vendorAddress2 + ", vendorCity=" + vendorCity
				+ ", vendorState=" + vendorState + ", vendorCountry=" + vendorCountry + ", vendorPincode="
				+ vendorPincode + ", vendorRemarks=" + vendorRemarks + ", vendorFirName=" + vendorFirName
				+ ", vendorFirPhone=" + vendorFirPhone + ", vendorFirEmail=" + vendorFirEmail + ", vendorSecName="
				+ vendorSecName + ", vendorSecPhone=" + vendorSecPhone + ", vendorSecEmail=" + vendorSecEmail
				+ ", vendorBankName=" + vendorBankName + ", vendorBankBranch=" + vendorBankBranch + ", vendorBankAccno="
				+ vendorBankAccno + ", vendorBankIfsc=" + vendorBankIfsc + ", createdBy=" + createdBy + ", createdById="
				+ createdById + ", created=" + created + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedById="
				+ lastModifiedById + ", lastupdated=" + lastupdated + ", createdOn=" + createdOn + ", lastupdatedOn="
				+ lastupdatedOn + ", vendorTypeId=" + vendorTypeId + ", companyId=" + companyId + ", typeOfPaymentId="
				+ typeOfPaymentId + ", paidAmount=" + paidAmount + ", balanceAmount=" + balanceAmount
				+ ", SelectService_id=" + SelectService_id + ", ownPetrolPump=" + ownPetrolPump + ", ownPetrolPumpStr="
				+ ownPetrolPumpStr + ", vendorTDSPercent=" + vendorTDSPercent + "]";
	}

	public Long getSelectService_id() {
		return SelectService_id;
	}

	public void setSelectService_id(Long selectService_id) {
		SelectService_id = selectService_id;
	}


}

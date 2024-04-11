package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vendor")
public class Vendor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendorId")
	private Integer vendorId;
	
	@Column(name = "vendorNumber", nullable = false)
	private Integer vendorNumber;

	@Column(name = "vendorName", length = 150)
	private String vendorName;

	@Column(name = "vendorLocation", length = 50)
	private String vendorLocation;

	@Column(name = "vendorTermId", nullable = false)
	private short vendorTermId;

	@Column(name = "vendorGSTNo", length = 50)
	private String vendorGSTNO;
	
	@Column(name = "vendorGSTRegisteredId", nullable = false)
	private short vendorGSTRegisteredId;

	@Column(name = "vendorPanNO", length = 50)
	private String vendorPanNO;

	@Column(name = "vendorCreditLimit", length = 50)
	private String vendorCreditLimit;

	@Column(name = "vendorAdvancePaid", length = 50)
	private String vendorAdvancePaid;

	@Column(name = "vendorPhone", length = 15)
	private String vendorPhone;

	@Column(name = "vendorWebsite", length = 150)
	private String vendorWebsite;

	@Column(name = "vendorAddress1", length = 150)
	private String vendorAddress1;

	@Column(name = "vendorAddress2", length = 150)
	private String vendorAddress2;

	@Column(name = "vendorCity", length = 50)
	private String vendorCity;

	@Column(name = "vendorState", length = 50)
	private String vendorState;

	@Column(name = "vendorCountry", length = 50)
	private String vendorCountry;

	@Column(name = "vendorPincode", length = 6)
	private String vendorPincode;

	@Column(name = "vendorRemarks", length = 200)
	private String vendorRemarks;

	@Column(name = "vendorFirName", length = 50)
	private String vendorFirName;

	@Column(name = "vendorFirPhone", length = 15)
	private String vendorFirPhone;

	@Column(name = "vendorFirEmail", length = 150)
	private String vendorFirEmail;

	@Column(name = "vendorSecName", length = 50)
	private String vendorSecName;

	@Column(name = "vendorSecPhone", length = 15)
	private String vendorSecPhone;

	@Column(name = "vendorSecEmail", length = 150)
	private String vendorSecEmail;

	@Column(name = "vendorBankName", length = 150)
	private String vendorBankName;

	@Column(name = "vendorBankBranch", length = 150)
	private String vendorBankBranch;

	@Column(name = "vendorBankAccno", length = 25)
	private String vendorBankAccno;

	@Column(name = "vendorBankIfsc", length = 25)
	private String vendorBankIfsc;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	@Column(name = "companyId" , nullable = false)
	private Integer companyId;
	
	@Column(name = "vendorTypeId" , nullable = false)
	private long vendorTypeId;
	
	@Column(name = "createdById" , nullable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "markForDelete" , nullable = false)
	private boolean markForDelete;
	
	@Column(name = "autoVendor" , nullable = false)
	private boolean autoVendor;
	
	@Column(name = "ownPetrolPump" , nullable = false)
	private short	ownPetrolPump;

	@Column(name = "vendorTDSPercent", length = 25)
	private Double vendorTDSPercent;
	
	public Vendor() {
		super();

	}

	public Vendor(Integer vendorId, Integer vendorNumber , String vendorName,String vendorLocation,
			String vendorGSTNO, short vendorGSTRegisteredId, String vendorPanNO, String vendorCreditLimit,
			String vendorAdvancePaid, String vendorPhone, String vendorWebsite, String vendorAddress1,
			String vendorAddress2, String vendorCity, String vendorState, String vendorCountry, String vendorPincode,
			String vendorRemarks, String vendorFirName, String vendorFirPhone, String vendorFirEmail,
			String vendorSecName, String vendorSecPhone, String vendorSecEmail, String vendorBankName,
			String vendorBankBranch, String vendorBankAccno, String vendorBankIfsc, Date created, Date lastupdated, 
			Integer companyId, long vendorTypeId) {
		super();
		this.vendorId = vendorId;
		this.vendorNumber = vendorNumber;
		this.vendorName = vendorName;
		this.vendorLocation = vendorLocation;
		this.vendorGSTNO = vendorGSTNO;
		this.vendorGSTRegisteredId = vendorGSTRegisteredId;
		this.vendorPanNO = vendorPanNO;
		this.vendorCreditLimit = vendorCreditLimit;
		this.vendorAdvancePaid = vendorAdvancePaid;
		this.vendorPhone = vendorPhone;
		this.vendorWebsite = vendorWebsite;
		this.vendorAddress1 = vendorAddress1;
		this.vendorAddress2 = vendorAddress2;
		this.vendorCity = vendorCity;
		this.vendorState = vendorState;
		this.vendorCountry = vendorCountry;
		this.vendorPincode = vendorPincode;
		this.vendorRemarks = vendorRemarks;
		this.vendorFirName = vendorFirName;
		this.vendorFirPhone = vendorFirPhone;
		this.vendorFirEmail = vendorFirEmail;
		this.vendorSecName = vendorSecName;
		this.vendorSecPhone = vendorSecPhone;
		this.vendorSecEmail = vendorSecEmail;
		this.vendorBankName = vendorBankName;
		this.vendorBankBranch = vendorBankBranch;
		this.vendorBankAccno = vendorBankAccno;
		this.vendorBankIfsc = vendorBankIfsc;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId	= companyId;
		this.vendorTypeId = vendorTypeId;
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
	 * @param vendorAdvancePaid
	 *            the vendorAdvancePaid to set
	 */
	public void setVendorAdvancePaid(String vendorAdvancePaid) {
		this.vendorAdvancePaid = vendorAdvancePaid;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}
	
	

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public long getVendorTypeId() {
		return vendorTypeId;
	}

	public void setVendorTypeId(long vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
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
	 * @return the autoVendor
	 */
	public boolean isAutoVendor() {
		return autoVendor;
	}

	/**
	 * @param autoVendor the autoVendor to set
	 */
	public void setAutoVendor(boolean autoVendor) {
		this.autoVendor = autoVendor;
	}

	public short getOwnPetrolPump() {
		return ownPetrolPump;
	}

	public void setOwnPetrolPump(short ownPetrolPump) {
		this.ownPetrolPump = ownPetrolPump;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	public Double getVendorTDSPercent() {
		return vendorTDSPercent;
	}

	public void setVendorTDSPercent(Double vendorTDSPercent) {
		this.vendorTDSPercent = vendorTDSPercent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vendorLocation == null) ? 0 : vendorLocation.hashCode());
		result = prime * result + ((vendorName == null) ? 0 : vendorName.hashCode());
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
		Vendor other = (Vendor) obj;
		if (vendorLocation == null) {
			if (other.vendorLocation != null)
				return false;
		} else if (!vendorLocation.equals(other.vendorLocation))
			return false;
		if (vendorName == null) {
			if (other.vendorName != null)
				return false;
		} else if (!vendorName.equals(other.vendorName))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vendor [vendorId=");
		builder.append(vendorId);
		builder.append(", vendorNumber=");
		builder.append(vendorNumber);
		builder.append(", vendorName=");
		builder.append(vendorName);
		builder.append(", vendorLocation=");
		builder.append(vendorLocation);
		builder.append(", vendorTermId=");
		builder.append(vendorTermId);
		builder.append(", vendorGSTNO=");
		builder.append(vendorGSTNO);
		builder.append(", vendorGSTRegisteredId=");
		builder.append(vendorGSTRegisteredId);
		builder.append(", vendorPanNO=");
		builder.append(vendorPanNO);
		builder.append(", vendorCreditLimit=");
		builder.append(vendorCreditLimit);
		builder.append(", vendorAdvancePaid=");
		builder.append(vendorAdvancePaid);
		builder.append(", vendorPhone=");
		builder.append(vendorPhone);
		builder.append(", vendorWebsite=");
		builder.append(vendorWebsite);
		builder.append(", vendorAddress1=");
		builder.append(vendorAddress1);
		builder.append(", vendorAddress2=");
		builder.append(vendorAddress2);
		builder.append(", vendorCity=");
		builder.append(vendorCity);
		builder.append(", vendorState=");
		builder.append(vendorState);
		builder.append(", vendorCountry=");
		builder.append(vendorCountry);
		builder.append(", vendorPincode=");
		builder.append(vendorPincode);
		builder.append(", vendorRemarks=");
		builder.append(vendorRemarks);
		builder.append(", vendorFirName=");
		builder.append(vendorFirName);
		builder.append(", vendorFirPhone=");
		builder.append(vendorFirPhone);
		builder.append(", vendorFirEmail=");
		builder.append(vendorFirEmail);
		builder.append(", vendorSecName=");
		builder.append(vendorSecName);
		builder.append(", vendorSecPhone=");
		builder.append(vendorSecPhone);
		builder.append(", vendorSecEmail=");
		builder.append(vendorSecEmail);
		builder.append(", vendorBankName=");
		builder.append(vendorBankName);
		builder.append(", vendorBankBranch=");
		builder.append(vendorBankBranch);
		builder.append(", vendorBankAccno=");
		builder.append(vendorBankAccno);
		builder.append(", vendorBankIfsc=");
		builder.append(vendorBankIfsc);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", vendorTypeId=");
		builder.append(vendorTypeId);
		builder.append("]");
		return builder.toString();
	}

}

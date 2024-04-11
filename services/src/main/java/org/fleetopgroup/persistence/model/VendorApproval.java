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
@Table(name = "vendorapproval")
public class VendorApproval implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "approvalId")
	private Long approvalId;
	
	@Column(name = "approvalNumber", nullable = false)
	private Long approvalNumber;

	@Column(name = "approvalvendorID")
	private Integer approvalvendorID;

	@Column(name = "approvalTotal")
	private Double approvalTotal;
	
	@Column(name = "approvalPaidTotal")
	private Double approvalPaidTotal;

	@Column(name = "approvalCreateDate")
	private Date approvalCreateDate;

	@Column(name = "approvalPayNumber", length = 25)
	private String approvalPayNumber;

	@Column(name = "approvalDateofpayment")
	private Date approvalDateofpayment;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "invoiceId", nullable = false)
	private Long invoiceId;

	@Column(name = "approvalStatusId", nullable = false)
	private short approvalStatusId;
	
	@Column(name = "approvalPlaceId", nullable = false)
	private short approvalPlaceId;
	
	@Column(name = "approvalPaymentTypeId", nullable = false)
	private short approvalPaymentTypeId;
	
	@Column(name = "approvalpaidbyId")
	private Long approvalpaidbyId;
	
	@Column(name = "approvalCreateById", nullable = false)
	private Long approvalCreateById;
	
	@Column(name = "createdById", nullable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "tallyCompanyId")
	private Long	tallyCompanyId;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;

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
	
	@Column(name = "approvalTDSAmount")
	private Double approvalTDSAmount;
	

	public VendorApproval() {
		super();
	}

	public VendorApproval(Long approvalNumber, Integer approvalvendorID,  Double approvalTotal, Date approvalCreateDate,  String approvalPayNumber, Date approvalDateofpayment,
			String approvalpaidby, Integer companyId, Long createdBy, Long lastModifiedBy, Date created, Date lastupdated, Long invoiceId) {
		super();
		this.approvalNumber = approvalNumber;
		this.approvalvendorID = approvalvendorID;
		this.approvalTotal = approvalTotal;
		this.approvalCreateDate = approvalCreateDate;
		this.approvalPayNumber = approvalPayNumber;
		this.approvalDateofpayment = approvalDateofpayment;
		this.companyId = companyId;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.created = created;
		this.lastupdated = lastupdated;
		this.invoiceId = invoiceId;
	}
	
	
	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * @return the approvalId
	 */
	public Long getApprovalId() {
		return approvalId;
	}

	/**
	 * @param approvalId
	 *            the approvalId to set
	 */
	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public Long getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(Long approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	/**
	 * @return the approvalvendorID
	 */
	public Integer getApprovalvendorID() {
		return approvalvendorID;
	}

	/**
	 * @param approvalvendorID
	 *            the approvalvendorID to set
	 */
	public void setApprovalvendorID(Integer approvalvendorID) {
		this.approvalvendorID = approvalvendorID;
	}

	/**
	 * @return the approvalTotal
	 */
	public Double getApprovalTotal() {
		return approvalTotal;
	}

	/**
	 * @param approvalTotal
	 *            the approvalTotal to set
	 */
	public void setApprovalTotal(Double approvalTotal) {
		this.approvalTotal = approvalTotal;
	}

	/**
	 * @return the approvalCreateDate
	 */
	public Date getApprovalCreateDate() {
		return approvalCreateDate;
	}

	/**
	 * @param approvalCreateDate
	 *            the approvalCreateDate to set
	 */
	public void setApprovalCreateDate(Date approvalCreateDate) {
		this.approvalCreateDate = approvalCreateDate;
	}

	/**
	 * @return the approvalPayNumber
	 */
	public String getApprovalPayNumber() {
		return approvalPayNumber;
	}

	/**
	 * @param approvalPayNumber
	 *            the approvalPayNumber to set
	 */
	public void setApprovalPayNumber(String approvalPayNumber) {
		this.approvalPayNumber = approvalPayNumber;
	}

	/**
	 * @return the approvalDateofpayment
	 */
	public Date getApprovalDateofpayment() {
		return approvalDateofpayment;
	}

	/**
	 * @param approvalDateofpayment
	 *            the approvalDateofpayment to set
	 */
	public void setApprovalDateofpayment(Date approvalDateofpayment) {
		this.approvalDateofpayment = approvalDateofpayment;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	/**
	 * @return the approvalStatusId
	 */
	public short getApprovalStatusId() {
		return approvalStatusId;
	}

	/**
	 * @param approvalStatusId the approvalStatusId to set
	 */
	public void setApprovalStatusId(short approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	/**
	 * @return the approvalPlaceId
	 */
	public short getApprovalPlaceId() {
		return approvalPlaceId;
	}

	/**
	 * @param approvalPlaceId the approvalPlaceId to set
	 */
	public void setApprovalPlaceId(short approvalPlaceId) {
		this.approvalPlaceId = approvalPlaceId;
	}


	/**
	 * @return the approvalPaymentTypeId
	 */
	public short getApprovalPaymentTypeId() {
		return approvalPaymentTypeId;
	}

	/**
	 * @param approvalPaymentTypeId the approvalPaymentTypeId to set
	 */
	public void setApprovalPaymentTypeId(short approvalPaymentTypeId) {
		this.approvalPaymentTypeId = approvalPaymentTypeId;
	}

	/**
	 * @return the approvalpaidbyId
	 */
	public Long getApprovalpaidbyId() {
		return approvalpaidbyId;
	}

	/**
	 * @param approvalpaidbyId the approvalpaidbyId to set
	 */
	public void setApprovalpaidbyId(Long approvalpaidbyId) {
		this.approvalpaidbyId = approvalpaidbyId;
	}

	/**
	 * @return the approvalCreateById
	 */
	public Long getApprovalCreateById() {
		return approvalCreateById;
	}

	/**
	 * @param approvalCreateById the approvalCreateById to set
	 */
	public void setApprovalCreateById(Long approvalCreateById) {
		this.approvalCreateById = approvalCreateById;
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
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	public Double getApprovalPaidTotal() {
		return approvalPaidTotal;
	}

	public void setApprovalPaidTotal(Double approvalPaidTotal) {
		this.approvalPaidTotal = approvalPaidTotal;
	}
	
	
	public Double getApprovalTDSAmount() {
		return approvalTDSAmount;
	}

	public void setApprovalTDSAmount(Double approvalTDSAmount) {
		this.approvalTDSAmount = approvalTDSAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvalPayNumber == null) ? 0 : approvalPayNumber.hashCode());
		result = prime * result + ((approvalTotal == null) ? 0 : approvalTotal.hashCode());
		result = prime * result + ((approvalvendorID == null) ? 0 : approvalvendorID.hashCode());
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
		VendorApproval other = (VendorApproval) obj;
		if (approvalPayNumber == null) {
			if (other.approvalPayNumber != null)
				return false;
		} else if (!approvalPayNumber.equals(other.approvalPayNumber))
			return false;
		if (approvalTotal == null) {
			if (other.approvalTotal != null)
				return false;
		} else if (!approvalTotal.equals(other.approvalTotal))
			return false;
		if (approvalvendorID == null) {
			if (other.approvalvendorID != null)
				return false;
		} else if (!approvalvendorID.equals(other.approvalvendorID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VendorApproval [approvalId=" + approvalId + ", approvalNumber=" + approvalNumber + ", approvalvendorID="
				+ approvalvendorID + ", approvalTotal=" + approvalTotal + ", approvalCreateDate=" + approvalCreateDate
				+ ", approvalPayNumber=" + approvalPayNumber + ", approvalDateofpayment=" + approvalDateofpayment
				+ ", companyId=" + companyId + ", invoiceId=" + invoiceId + ", approvalStatusId=" + approvalStatusId
				+ ", approvalPlaceId=" + approvalPlaceId + ", approvalPaymentTypeId=" + approvalPaymentTypeId
				+ ", approvalpaidbyId=" + approvalpaidbyId + ", approvalCreateById=" + approvalCreateById
				+ ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById + ", markForDelete="
				+ markForDelete + ", created=" + created + ", lastupdated=" + lastupdated + "]";
	}

	
	

}

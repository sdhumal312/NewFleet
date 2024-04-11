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
@Table(name = "renewalreminder")
public class RenewalReminder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "renewal_id")
	private Long renewal_id;
	
	@Column(name = "renewal_R_Number")
	private Long renewal_R_Number;

	@Column(name = "vid", length = 7)
	private Integer vid;

	@Column(name = "renewalTypeId")
	private Integer renewalTypeId;
	
	@Column(name = "renewal_Subid")
	private Integer renewal_Subid;
	
	@Column(name = "renewal_from")
	private Date renewal_from;

	@Column(name = "renewal_to")
	private Date renewal_to;

	@Column(name = "renewal_periedthreshold", length = 5)
	private Integer renewal_periedthreshold;

	@Column(name = "renewal_timethreshold", length = 5)
	private Integer renewal_timethreshold;

	
	@Column(name = "dateofRenewal")
	private Date dateofRenewal;

	@Column(name = "renewal_receipt", length = 25)
	private String renewal_receipt;

	@Column(name = "renewal_Amount")
	private Double renewal_Amount;

	@Column(name = "renewal_dateofpayment")
	private Date renewal_dateofpayment;
	
	@Column(name = "renewal_paidbyId")
	private Long renewal_paidbyId;

	@Column(name = "paymentTypeId")
	private short paymentTypeId;

	@Column(name = "renewal_PayNumber", length = 25)
	private String renewal_PayNumber;

	@Column(name = "renewal_authorization", length = 25)
	private String renewal_authorization;

	@Column(name = "renewal_number", length = 50)
	private String renewal_number;

	/** The value for the Document available field */
	@Basic(optional = false)
	@Column(name = "renewal_document", nullable = false)
	private boolean renewal_document = false;

	/** The value for the Document available field */
	@Column(name = "renewal_document_id")
	private Long renewal_document_id;

	@Column(name = "renewal_staus_id")
	private short renewal_staus_id;
	
	@Column(name = "renewal_approvedbyId")
	private Long renewal_approvedbyId;

	@Column(name = "renewal_approvedComment", length = 200)
	private String renewal_approvedComment;

	/** The value for the renewal_approvedID available field */
	@Column(name = "renewal_approvedID")
	private Long renewal_approvedID;

	@Column(name = "renewal_approveddate")
	private Date renewal_approveddate;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

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
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	@Column(name = "newRRCreated", nullable = false)
	private boolean		newRRCreated;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	@Column(name = "tallyCompanyId")
	private Long tallyCompanyId;
	
	@Column(name = "vendorId")
	private Integer vendorId;
	
	@Column(name = "ignored",nullable = false)
	private boolean ignored;
	
	@Column(name = "igRemark", length = 1000)
	private String igRemark;

	public RenewalReminder() {
		super();
	}

	public RenewalReminder(Long renewal_id,Long renewal_R_Number, Integer vid,  Date renewal_from, Date renewal_to, Integer renewal_periedthreshold,
			Integer renewal_timethreshold, String renewal_dateofRenewal, String renewal_receipt, Double renewal_Amount,
			Date renewal_dateofpayment, Long renewal_paidby,  String renewal_PayNumber,
			String renewal_authorization, String renewal_number,  Long renewal_approvedby,
			String renewal_approvedComment, Date renewal_approveddate, Long createdBy, Long lastModifiedBy,
			boolean MarkForDelete, Date created, Date lastupdated, Integer companyId) {
		super();
		this.renewal_id = renewal_id;
		this.renewal_R_Number = renewal_R_Number;
		this.vid = vid;
		this.renewal_from = renewal_from;
		this.renewal_to = renewal_to;
		this.renewal_periedthreshold = renewal_periedthreshold;
		this.renewal_timethreshold = renewal_timethreshold;
		//this.renewal_dateofRenewal = renewal_dateofRenewal;
		this.renewal_receipt = renewal_receipt;
		this.renewal_Amount = renewal_Amount;
		this.renewal_dateofpayment = renewal_dateofpayment;
		this.renewal_paidbyId = renewal_paidby;
		this.renewal_PayNumber = renewal_PayNumber;
		this.renewal_authorization = renewal_authorization;
		this.renewal_number = renewal_number;
		this.renewal_approvedbyId = renewal_approvedby;
		this.renewal_approvedComment = renewal_approvedComment;
		this.renewal_approveddate = renewal_approveddate;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
	}

	/**
	 * @return the renewal_id
	 */
	public Long getRenewal_id() {
		return renewal_id;
	}

	/**
	 * @param renewal_id
	 *            the renewal_id to set
	 */
	public void setRenewal_id(Long renewal_id) {
		this.renewal_id = renewal_id;
	}

	public Long getRenewal_R_Number() {
		return renewal_R_Number;
	}

	public void setRenewal_R_Number(Long renewal_R_Number) {
		this.renewal_R_Number = renewal_R_Number;
	}

	/**
	 * @return the vid
	 */
	public Integer getVid() {
		return vid;
	}

	/**
	 * @param vid
	 *            the vid to set
	 */
	public void setVid(Integer vid) {
		this.vid = vid;
	}

	
	public Integer getRenewalTypeId() {
		return renewalTypeId;
	}

	public void setRenewalTypeId(Integer renewalTypeId) {
		this.renewalTypeId = renewalTypeId;
	}

	public Integer getRenewal_Subid() {
		return renewal_Subid;
	}

	public void setRenewal_Subid(Integer renewal_Subid) {
		this.renewal_Subid = renewal_Subid;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public short getRenewal_staus_id() {
		return renewal_staus_id;
	}

	public void setRenewal_staus_id(short renewal_staus_id) {
		this.renewal_staus_id = renewal_staus_id;
	}
	/**
	 * @return the renewal_periedthreshold
	 */
	public Integer getRenewal_periedthreshold() {
		return renewal_periedthreshold;
	}

	/**
	 * @param renewal_periedthreshold
	 *            the renewal_periedthreshold to set
	 */
	public void setRenewal_periedthreshold(Integer renewal_periedthreshold) {
		this.renewal_periedthreshold = renewal_periedthreshold;
	}

	/**
	 * @return the renewal_timethreshold
	 */
	public Integer getRenewal_timethreshold() {
		return renewal_timethreshold;
	}

	/**
	 * @param renewal_timethreshold
	 *            the renewal_timethreshold to set
	 */
	public void setRenewal_timethreshold(Integer renewal_timethreshold) {
		this.renewal_timethreshold = renewal_timethreshold;
	}

	/**
	 * @return the renewal_dateofRenewal
	 *//*
	public String getRenewal_dateofRenewal() {
		return renewal_dateofRenewal;
	}

	*//**
	 * @param renewal_dateofRenewal
	 *            the renewal_dateofRenewal to set
	 *//*
	public void setRenewal_dateofRenewal(String renewal_dateofRenewal) {
		this.renewal_dateofRenewal = renewal_dateofRenewal;
	}*/

	/**
	 * @return the renewal_receipt
	 */
	public String getRenewal_receipt() {
		return renewal_receipt;
	}

	/**
	 * @param renewal_receipt
	 *            the renewal_receipt to set
	 */
	public void setRenewal_receipt(String renewal_receipt) {
		this.renewal_receipt = renewal_receipt;
	}

	/**
	 * @return the renewal_Amount
	 */
	public Double getRenewal_Amount() {
		return renewal_Amount;
	}

	/**
	 * @param renewal_Amount
	 *            the renewal_Amount to set
	 */
	public void setRenewal_Amount(Double renewal_Amount) {
		this.renewal_Amount = renewal_Amount;
	}

	/**
	 * @return the renewal_dateofpayment
	 */
	public Date getRenewal_dateofpayment() {
		return renewal_dateofpayment;
	}

	/**
	 * @param renewal_dateofpayment
	 *            the renewal_dateofpayment to set
	 */
	public void setRenewal_dateofpayment(Date renewal_dateofpayment) {
		this.renewal_dateofpayment = renewal_dateofpayment;
	}

	/**
	 * @return the renewal_PayNumber
	 */
	public String getRenewal_PayNumber() {
		return renewal_PayNumber;
	}

	/**
	 * @param renewal_PayNumber
	 *            the renewal_PayNumber to set
	 */
	public void setRenewal_PayNumber(String renewal_PayNumber) {
		this.renewal_PayNumber = renewal_PayNumber;
	}

	/**
	 * @return the renewal_authorization
	 */
	public String getRenewal_authorization() {
		return renewal_authorization;
	}

	/**
	 * @param renewal_authorization
	 *            the renewal_authorization to set
	 */
	public void setRenewal_authorization(String renewal_authorization) {
		this.renewal_authorization = renewal_authorization;
	}

	/**
	 * @return the renewal_number
	 */
	public String getRenewal_number() {
		return renewal_number;
	}

	/**
	 * @param renewal_number
	 *            the renewal_number to set
	 */
	public void setRenewal_number(String renewal_number) {
		this.renewal_number = renewal_number;
	}

	/**
	 * @return the renewal_from
	 */
	public Date getRenewal_from() {
		return renewal_from;
	}

	/**
	 * @param renewal_from
	 *            the renewal_from to set
	 */
	public void setRenewal_from(Date renewal_from) {
		this.renewal_from = renewal_from;
	}

	/**
	 * @return the renewal_to
	 */
	public Date getRenewal_to() {
		return renewal_to;
	}

	/**
	 * @param renewal_to
	 *            the renewal_to to set
	 */
	public void setRenewal_to(Date renewal_to) {
		this.renewal_to = renewal_to;
	}

	/**
	 * @return the renewal_approveddate
	 */
	public Date getRenewal_approveddate() {
		return renewal_approveddate;
	}

	/**
	 * @param renewal_approveddate
	 *            the renewal_approveddate to set
	 */
	public void setRenewal_approveddate(Date renewal_approveddate) {
		this.renewal_approveddate = renewal_approveddate;
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
	 * @return the renewal_approvedComment
	 */
	public String getRenewal_approvedComment() {
		return renewal_approvedComment;
	}

	/**
	 * @param renewal_approvedComment
	 *            the renewal_approvedComment to set
	 */
	public void setRenewal_approvedComment(String renewal_approvedComment) {
		this.renewal_approvedComment = renewal_approvedComment;
	}

	/**
	 * @return the renewal_document
	 */
	public boolean isRenewal_document() {
		return renewal_document;
	}

	/**
	 * @param renewal_document
	 *            the renewal_document to set
	 */
	public void setRenewal_document(boolean renewal_document) {
		this.renewal_document = renewal_document;
	}

	/**
	 * @return the renewal_document_id
	 */
	public Long getRenewal_document_id() {
		return renewal_document_id;
	}

	/**
	 * @param renewal_document_id
	 *            the renewal_document_id to set
	 */
	public void setRenewal_document_id(Long renewal_document_id) {
		this.renewal_document_id = renewal_document_id;
	}


	/**
	 * @return the renewal_approvedID
	 */
	public Long getRenewal_approvedID() {
		return renewal_approvedID;
	}

	/**
	 * @param renewal_approvedID
	 *            the renewal_approvedID to set
	 */
	public void setRenewal_approvedID(Long renewal_approvedID) {
		this.renewal_approvedID = renewal_approvedID;
	}

	/**
	 * @return the renewal_paidbyId
	 */
	public Long getRenewal_paidbyId() {
		return renewal_paidbyId;
	}

	/**
	 * @param renewal_paidbyId the renewal_paidbyId to set
	 */
	public void setRenewal_paidbyId(Long renewal_paidbyId) {
		this.renewal_paidbyId = renewal_paidbyId;
	}

	/**
	 * @return the renewal_approvedbyId
	 */
	public Long getRenewal_approvedbyId() {
		return renewal_approvedbyId;
	}

	/**
	 * @param renewal_approvedbyId the renewal_approvedbyId to set
	 */
	public void setRenewal_approvedbyId(Long renewal_approvedbyId) {
		this.renewal_approvedbyId = renewal_approvedbyId;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	

	/**
	 * @return the dateofRenewal
	 */
	public Date getDateofRenewal() {
		return dateofRenewal;
	}

	/**
	 * @param dateofRenewal the dateofRenewal to set
	 */
	public void setDateofRenewal(Date dateofRenewal) {
		this.dateofRenewal = dateofRenewal;
	}

	public boolean isNewRRCreated() {
		return newRRCreated;
	}

	public void setNewRRCreated(boolean newRRCreated) {
		this.newRRCreated = newRRCreated;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}


	public boolean isIgnored() {
		return ignored;
	}

	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}

	public String getIgRemark() {
		return igRemark;
	}

	public void setIgRemark(String igRemark) {
		this.igRemark = igRemark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((renewal_PayNumber == null) ? 0 : renewal_PayNumber.hashCode());
		result = prime * result + ((renewal_approvedbyId == null) ? 0 : renewal_approvedbyId.hashCode());
		result = prime * result + ((renewal_from == null) ? 0 : renewal_from.hashCode());
		result = prime * result + ((renewal_number == null) ? 0 : renewal_number.hashCode());
		result = prime * result + ((renewal_receipt == null) ? 0 : renewal_receipt.hashCode());
		result = prime * result + ((renewal_to == null) ? 0 : renewal_to.hashCode());
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
		RenewalReminder other = (RenewalReminder) obj;
		if (renewal_PayNumber == null) {
			if (other.renewal_PayNumber != null)
				return false;
		} else if (!renewal_PayNumber.equals(other.renewal_PayNumber))
			return false;
		if (renewal_approvedbyId == null) {
			if (other.renewal_approvedbyId != null)
				return false;
		} else if (!renewal_approvedbyId.equals(other.renewal_approvedbyId))
			return false;
		if (renewal_from == null) {
			if (other.renewal_from != null)
				return false;
		} else if (!renewal_from.equals(other.renewal_from))
			return false;
		if (renewal_number == null) {
			if (other.renewal_number != null)
				return false;
		} else if (!renewal_number.equals(other.renewal_number))
			return false;
		if (renewal_receipt == null) {
			if (other.renewal_receipt != null)
				return false;
		} else if (!renewal_receipt.equals(other.renewal_receipt))
			return false;
		
		if (renewal_to == null) {
			if (other.renewal_to != null)
				return false;
		} else if (!renewal_to.equals(other.renewal_to))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "RenewalReminder [renewal_id=" + renewal_id + ", renewal_R_Number=" + renewal_R_Number + ", vid=" + vid
				+ ", renewalTypeId=" + renewalTypeId + ", renewal_Subid=" + renewal_Subid + ", renewal_from="
				+ renewal_from + ", renewal_to=" + renewal_to + ", renewal_periedthreshold=" + renewal_periedthreshold
				+ ", renewal_timethreshold=" + renewal_timethreshold + ", dateofRenewal=" + dateofRenewal
				+ ", renewal_receipt=" + renewal_receipt + ", renewal_Amount=" + renewal_Amount
				+ ", renewal_dateofpayment=" + renewal_dateofpayment + ", renewal_paidbyId=" + renewal_paidbyId
				+ ", paymentTypeId=" + paymentTypeId + ", renewal_PayNumber=" + renewal_PayNumber
				+ ", renewal_authorization=" + renewal_authorization + ", renewal_number=" + renewal_number
				+ ", renewal_document=" + renewal_document + ", renewal_document_id=" + renewal_document_id
				+ ", renewal_staus_id=" + renewal_staus_id + ", renewal_approvedbyId=" + renewal_approvedbyId
				+ ", renewal_approvedComment=" + renewal_approvedComment + ", renewal_approvedID=" + renewal_approvedID
				+ ", renewal_approveddate=" + renewal_approveddate + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", created=" + created + ", lastupdated=" + lastupdated
				+ ", companyId=" + companyId + ", markForDelete=" + markForDelete + ", newRRCreated=" + newRRCreated
				+ ", isPendingForTally=" + isPendingForTally + ", vendorId=" + vendorId + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	

}
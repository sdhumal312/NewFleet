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
@Table(name = "RenewalReminderApproval")
public class RenewalReminderApproval implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the renewalApproval_id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "renewalApproval_id")
	private Long renewalApproval_id;
	
	@Column(name = "renewalApproval_Number", nullable = false)
	private Long renewalApproval_Number;
	
	@Column(name = "companyId" , nullable = false)
	private Integer companyId;
	
	@Column(name = "approvalCreated_ById")
	private Long approvalCreated_ById;

	/** The value for the approvalCreated_Date DATE field */
	@Basic(optional = false)
	@Column(name = "approvalCreated_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvalCreated_Date;

	
	@Column(name = "paymentTypeId", nullable = false)
	private short paymentTypeId;
	
	@Column(name = "approvalStatusId", nullable = false)
	private short approvalStatusId;

	@Column(name = "approvalPay_Number", length = 25)
	private String approvalPay_Number;
	
	@Column(name = "approvalPayment_ById")
	private Long approvalPayment_ById;

	/** The value for the approvalCreated_Date DATE field */
	@Basic(optional = false)
	@Column(name = "approvalPayment_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvalPayment_Date;

	/**
	 * The value for the approvalPayment_Amount name field using back end
	 * process
	 */
	@Column(name = "approvalPayment_Amount")
	private Double approvalPayment_Amount;

	/**
	 * The value for the approval_Amount name field using back end process
	 */
	@Column(name = "approvalApproved_Amount")
	private Double approvalApproved_Amount;

	/**
	 * The value for the approvalPending_Amount name field using back end
	 * process
	 */
	@Column(name = "approvalPending_Amount")
	private Double approvalPending_Amount;

	/**
	 * The value for the approvalPending_Amount name field using back end
	 * process
	 */
	@Column(name = "approvalCancel_Amount")
	private Double approvalCancel_Amount;

	/** The value for the Document available field */
	@Basic(optional = false)
	@Column(name = "approval_document", nullable = false)
	private boolean approval_document = false;

	/** The value for the Document available field */
	@Column(name = "approval_document_id")
	private Long approval_document_id;
	
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
	
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public RenewalReminderApproval() {
		super();
	}

	public RenewalReminderApproval(Long renewalApproval_id, Long renewalApproval_Number, Integer companyId, Date approvalCreated_Date,
			 Date approvalPayment_Date, Double approvalPayment_Amount, 
			boolean approval_document, Long approval_document_id,
			boolean MarkForDelete, Date created, Date lastupdated, short paymentTypeId, short approvalStatusId) {
		super();
		this.renewalApproval_id = renewalApproval_id;
		this.renewalApproval_Number = renewalApproval_Number;
		this.companyId = companyId;
		this.approvalCreated_Date = approvalCreated_Date;
		this.approvalPayment_Date = approvalPayment_Date;
		this.approvalPayment_Amount = approvalPayment_Amount;
		this.approval_document = approval_document;
		this.approval_document_id = approval_document_id;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
		this.paymentTypeId = paymentTypeId;
		this.approvalStatusId	= approvalStatusId;
	}

	/**
	 * @return the renewalApproval_id
	 */
	public Long getRenewalApproval_id() {
		return renewalApproval_id;
	}

	/**
	 * @param renewalApproval_id
	 *            the renewalApproval_id to set
	 */
	public void setRenewalApproval_id(Long renewalApproval_id) {
		this.renewalApproval_id = renewalApproval_id;
	}

	public Long getRenewalApproval_Number() {
		return renewalApproval_Number;
	}

	public void setRenewalApproval_Number(Long renewalApproval_Number) {
		this.renewalApproval_Number = renewalApproval_Number;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	
	/**
	 * @return the approvalCreated_Date
	 */
	public Date getApprovalCreated_Date() {
		return approvalCreated_Date;
	}

	/**
	 * @param approvalCreated_Date
	 *            the approvalCreated_Date to set
	 */
	public void setApprovalCreated_Date(Date approvalCreated_Date) {
		this.approvalCreated_Date = approvalCreated_Date;
	}


	/**
	 * @return the approvalPayment_Date
	 */
	public Date getApprovalPayment_Date() {
		return approvalPayment_Date;
	}

	/**
	 * @param approvalPayment_Date
	 *            the approvalPayment_Date to set
	 */
	public void setApprovalPayment_Date(Date approvalPayment_Date) {
		this.approvalPayment_Date = approvalPayment_Date;
	}

	/**
	 * @return the approvalPayment_Amount
	 */
	public Double getApprovalPayment_Amount() {
		return approvalPayment_Amount;
	}

	/**
	 * @param approvalPayment_Amount
	 *            the approvalPayment_Amount to set
	 */
	public void setApprovalPayment_Amount(Double approvalPayment_Amount) {
		this.approvalPayment_Amount = approvalPayment_Amount;
	}

	/**
	 * @return the approval_document
	 */
	public boolean isApproval_document() {
		return approval_document;
	}

	/**
	 * @param approval_document
	 *            the approval_document to set
	 */
	public void setApproval_document(boolean approval_document) {
		this.approval_document = approval_document;
	}

	/**
	 * @return the approval_document_id
	 */
	public Long getApproval_document_id() {
		return approval_document_id;
	}

	/**
	 * @param approval_document_id
	 *            the approval_document_id to set
	 */
	public void setApproval_document_id(Long approval_document_id) {
		this.approval_document_id = approval_document_id;
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

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public short getApprovalStatusId() {
		return approvalStatusId;
	}

	public void setApprovalStatusId(short approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	/**
	 * @return the approvalPay_Number
	 */
	public String getApprovalPay_Number() {
		return approvalPay_Number;
	}

	/**
	 * @param approvalPay_Number
	 *            the approvalPay_Number to set
	 */
	public void setApprovalPay_Number(String approvalPay_Number) {
		this.approvalPay_Number = approvalPay_Number;
	}

	/**
	 * @return the approvalApproved_Amount
	 */
	public Double getApprovalApproved_Amount() {
		return approvalApproved_Amount;
	}

	/**
	 * @param approvalApproved_Amount
	 *            the approvalApproved_Amount to set
	 */
	public void setApprovalApproved_Amount(Double approvalApproved_Amount) {
		this.approvalApproved_Amount = approvalApproved_Amount;
	}

	/**
	 * @return the approvalPending_Amount
	 */
	public Double getApprovalPending_Amount() {
		return approvalPending_Amount;
	}

	/**
	 * @param approvalPending_Amount
	 *            the approvalPending_Amount to set
	 */
	public void setApprovalPending_Amount(Double approvalPending_Amount) {
		this.approvalPending_Amount = approvalPending_Amount;
	}

	/**
	 * @return the approvalCancel_Amount
	 */
	public Double getApprovalCancel_Amount() {
		return approvalCancel_Amount;
	}

	/**
	 * @param approvalCancel_Amount
	 *            the approvalCancel_Amount to set
	 */
	public void setApprovalCancel_Amount(Double approvalCancel_Amount) {
		this.approvalCancel_Amount = approvalCancel_Amount;
	}


	/**
	 * @return the approvalCreated_ById
	 */
	public Long getApprovalCreated_ById() {
		return approvalCreated_ById;
	}

	/**
	 * @param approvalCreated_ById the approvalCreated_ById to set
	 */
	public void setApprovalCreated_ById(Long approvalCreated_ById) {
		this.approvalCreated_ById = approvalCreated_ById;
	}

	/**
	 * @return the approvalPayment_ById
	 */
	public Long getApprovalPayment_ById() {
		return approvalPayment_ById;
	}

	/**
	 * @param approvalPayment_ById the approvalPayment_ById to set
	 */
	public void setApprovalPayment_ById(Long approvalPayment_ById) {
		this.approvalPayment_ById = approvalPayment_ById;
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



	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((renewalApproval_id == null) ? 0 : renewalApproval_id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenewalReminderApproval other = (RenewalReminderApproval) obj;
		
		if (renewalApproval_id == null) {
			if (other.renewalApproval_id != null)
				return false;
		} else if (!renewalApproval_id.equals(other.renewalApproval_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RenewalReminderApproval [renewalApproval_id=" + renewalApproval_id + ", renewalApproval_Number="
				+ renewalApproval_Number + ", companyId=" + companyId + ", approvalCreated_ById=" + approvalCreated_ById
				+ ", approvalCreated_Date=" + approvalCreated_Date + ", paymentTypeId=" + paymentTypeId
				+ ", approvalStatusId=" + approvalStatusId + ", approvalPay_Number=" + approvalPay_Number
				+ ", approvalPayment_ById=" + approvalPayment_ById + ", approvalPayment_Date=" + approvalPayment_Date
				+ ", approvalPayment_Amount=" + approvalPayment_Amount + ", approvalApproved_Amount="
				+ approvalApproved_Amount + ", approvalPending_Amount=" + approvalPending_Amount
				+ ", approvalCancel_Amount=" + approvalCancel_Amount + ", approval_document=" + approval_document
				+ ", approval_document_id=" + approval_document_id + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", created=" + created + ", lastupdated=" + lastupdated
				+ ", markForDelete=" + markForDelete + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */


}
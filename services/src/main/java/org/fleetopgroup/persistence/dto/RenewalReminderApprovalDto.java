package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class RenewalReminderApprovalDto {

	/** The value for the renewalApproval_id field */

	private Long renewalApproval_id;
	
	private Long renewalApproval_Number;

	/** The value for the DEPARTNMENT name field using back end process */
	private String approvalCreated_By;
	
	private Long approvalCreated_ById;

	/** The value for the approvalCreated_Date DATE field */
	private String approvalCreated_Date;
	
	private Date approvalCreated_DateOn;

	/**
	 * The value for the approvalPayment_Type name field using back end process
	 */
	private String approvalPayment_Type;

	/**
	 * The value for the approvalPayment_Type name field using back end process
	 */
	private String approvalPay_Number;

	/** The value for the DEPARTNMENT name field using back end process */
	private String approvalPayment_By;
	
	private Long approvalPayment_ById;

	/** The value for the approvalCreated_Date DATE field */
	private String approvalPayment_Date;

	/**
	 * The value for the approvalPayment_Amount name field using back end
	 * process
	 */
	private Double approvalPayment_Amount;

	/**
	 * The value for the approval_Amount name field using back end process
	 */
	private Double approvalApproved_Amount;

	/**
	 * The value for the approvalPending_Amount name field using back end
	 * process
	 */
	private Double approvalPending_Amount;

	/** The value for the approval_Status name field using back end process */
	private String approval_Status;

	/** The value for the Document available field */
	private boolean approval_document = false;

	/** The value for the Document available field */
	private Long approval_document_id;

	/** The value for the created by field */
	private String createdBy;
	
	private Long createdById;

	/** The value for the lastUpdated by field */
	private String lastModifiedBy;
	
	private Long lastModifiedById;

	boolean markForDelete;

	/** The value for the created DATE field */
	private String created;

	/** The value for the lastUpdated DATE field */
	private String lastupdated;
	
	private short paymentTypeId;
	
	private short approvalStatusId;
	
	private Double approvalCancel_Amount;
	private Date approvalPayment_DateOn;
	private Date createdOn;
	private Date lastupdatedOn;

	public RenewalReminderApprovalDto() {
		super();
	}

	public RenewalReminderApprovalDto(Long renewalApproval_id, String approvalCreated_By, String approvalCreated_Date,
			String approvalPayment_By, String approvalPayment_Date, Double approvalPayment_Amount,
			String approval_Status, boolean approval_document, Long approval_document_id, String createdBy,
			String lastModifiedBy, boolean MarkForDelete, String created, String lastupdated) {
		super();
		this.renewalApproval_id = renewalApproval_id;
		this.approvalCreated_By = approvalCreated_By;
		this.approvalCreated_Date = approvalCreated_Date;
		this.approvalPayment_By = approvalPayment_By;
		this.approvalPayment_Date = approvalPayment_Date;
		this.approvalPayment_Amount = approvalPayment_Amount;
		this.approval_Status = approval_Status;
		this.approval_document = approval_document;
		this.approval_document_id = approval_document_id;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
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

	/**
	 * @return the approvalCreated_By
	 */
	public String getApprovalCreated_By() {
		return approvalCreated_By;
	}

	/**
	 * @param approvalCreated_By
	 *            the approvalCreated_By to set
	 */
	public void setApprovalCreated_By(String approvalCreated_By) {
		this.approvalCreated_By = approvalCreated_By;
	}

	/**
	 * @return the approvalCreated_Date
	 */
	public String getApprovalCreated_Date() {
		return approvalCreated_Date;
	}

	/**
	 * @param approvalCreated_Date
	 *            the approvalCreated_Date to set
	 */
	public void setApprovalCreated_Date(String approvalCreated_Date) {
		this.approvalCreated_Date = approvalCreated_Date;
	}

	/**
	 * @return the approvalPayment_By
	 */
	public String getApprovalPayment_By() {
		return approvalPayment_By;
	}

	/**
	 * @param approvalPayment_By
	 *            the approvalPayment_By to set
	 */
	public void setApprovalPayment_By(String approvalPayment_By) {
		this.approvalPayment_By = approvalPayment_By;
	}

	/**
	 * @return the approvalPayment_Type
	 */
	public String getApprovalPayment_Type() {
		return approvalPayment_Type;
	}

	/**
	 * @param approvalPayment_Type
	 *            the approvalPayment_Type to set
	 */
	public void setApprovalPayment_Type(String approvalPayment_Type) {
		this.approvalPayment_Type = approvalPayment_Type;
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
	 * @return the approvalPayment_Date
	 */
	public String getApprovalPayment_Date() {
		return approvalPayment_Date;
	}

	/**
	 * @param approvalPayment_Date
	 *            the approvalPayment_Date to set
	 */
	public void setApprovalPayment_Date(String approvalPayment_Date) {
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
		this.approvalPayment_Amount = Utility.round(approvalPayment_Amount, 2) ;
	}

	/**
	 * @return the approval_Status
	 */
	public String getApproval_Status() {
		return approval_Status;
	}

	/**
	 * @param approval_Status
	 *            the approval_Status to set
	 */
	public void setApproval_Status(String approval_Status) {
		this.approval_Status = approval_Status;
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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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

	/**
	 * @return the approvalCancel_Amount
	 */
	public Double getApprovalCancel_Amount() {
		return approvalCancel_Amount;
	}

	/**
	 * @param approvalCancel_Amount the approvalCancel_Amount to set
	 */
	public void setApprovalCancel_Amount(Double approvalCancel_Amount) {
		this.approvalCancel_Amount = Utility.round(approvalCancel_Amount, 2);
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
		this.approvalApproved_Amount = Utility.round(approvalApproved_Amount, 2);
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
		this.approvalPending_Amount = Utility.round(approvalPending_Amount, 2);
	}

	/**
	 * @return the approvalCreated_DateOn
	 */
	public Date getApprovalCreated_DateOn() {
		return approvalCreated_DateOn;
	}

	/**
	 * @param approvalCreated_DateOn the approvalCreated_DateOn to set
	 */
	public void setApprovalCreated_DateOn(Date approvalCreated_DateOn) {
		this.approvalCreated_DateOn = approvalCreated_DateOn;
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

	/**
	 * @return the approvalPayment_DateOn
	 */
	public Date getApprovalPayment_DateOn() {
		return approvalPayment_DateOn;
	}

	/**
	 * @param approvalPayment_DateOn the approvalPayment_DateOn to set
	 */
	public void setApprovalPayment_DateOn(Date approvalPayment_DateOn) {
		this.approvalPayment_DateOn = approvalPayment_DateOn;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approval_Status == null) ? 0 : approval_Status.hashCode());
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
		RenewalReminderApprovalDto other = (RenewalReminderApprovalDto) obj;
		if (approval_Status == null) {
			if (other.approval_Status != null)
				return false;
		} else if (!approval_Status.equals(other.approval_Status))
			return false;
		if (renewalApproval_id == null) {
			if (other.renewalApproval_id != null)
				return false;
		} else if (!renewalApproval_id.equals(other.renewalApproval_id))
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
		builder.append("RenewalReminderApprovalDto [renewalApproval_id=");
		builder.append(renewalApproval_id);
		builder.append(", approvalCreated_By=");
		builder.append(approvalCreated_By);
		builder.append(", approvalCreated_Date=");
		builder.append(approvalCreated_Date);
		builder.append(", approvalPayment_Type=");
		builder.append(approvalPayment_Type);
		builder.append(", approvalPay_Number=");
		builder.append(approvalPay_Number);
		builder.append(", approvalPayment_By=");
		builder.append(approvalPayment_By);
		builder.append(", approvalPayment_Date=");
		builder.append(approvalPayment_Date);
		builder.append(", approvalPayment_Amount=");
		builder.append(approvalPayment_Amount);
		builder.append(", approvalApproved_Amount=");
		builder.append(approvalApproved_Amount);
		builder.append(", approvalPending_Amount=");
		builder.append(approvalPending_Amount);
		builder.append(", approval_Status=");
		builder.append(approval_Status);
		builder.append(", approval_document=");
		builder.append(approval_document);
		builder.append(", approval_document_id=");
		builder.append(approval_document_id);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append("]");
		return builder.toString();
	}

}
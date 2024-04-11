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

@Entity
@Table(name = "renewalreminderhistory")
public class RenewalReminderHistory implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "renewalhis_id")
	private Long renewalhis_id;

	@Column(name = "vid")
	private Integer vid;

	/*@Column(name = "vehicle_registration")
	private String vehicle_registration;

	@Column(name = "renewalhis_type")
	private String renewalhis_type;
	
	@Column(name = "renewalhis_subtype")
	private String renewalhis_subtype;*/
	
	@Column(name = "renewalhis_typeId")
	private Integer renewalhis_typeId;
	
	
	@Column(name = "renewalhis_subtypeId")
	private Integer renewalhis_subtypeId;

	@Column(name = "renewalhis_from")
	private Date renewalhis_from;

	@Column(name = "renewalhis_to")
	private Date renewalhis_to;

	@Column(name = "renewalhis_periedthreshold")
	private Integer renewalhis_periedthreshold;

	@Column(name = "renewalhis_timethreshold")
	private Integer renewalhis_timethreshold;

	/*@Column(name = "renewalhis_dateofRenewal")
	private String renewalhis_dateofRenewal;*/
	
	@Column(name = "his_dateofRenewal")
	private Date his_dateofRenewal;

	@Column(name = "renewalhis_receipt")
	private String renewalhis_receipt;

	@Column(name = "renewalhis_Amount")
	private Double renewalhis_Amount;

	@Column(name = "renewalhis_dateofpayment")
	private Date renewalhis_dateofpayment;

	/*@Column(name = "renewalhis_paidby")
	private String renewalhis_paidby;*/
	
	@Column(name = "renewalhis_paidbyId")
	private Long renewalhis_paidbyId;

	/*@Column(name = "renewalhis_paymentType")
	private String renewalhis_paymentType;*/
	
	@Column(name = "renewalhis_paymentTypeId")
	private short renewalhis_paymentTypeId;

	@Column(name = "renewalhis_PayNumber")
	private String renewalhis_PayNumber;

	@Column(name = "renewalhis_authorization")
	private String renewalhis_authorization;

	@Column(name = "renewalhis_number")
	private String renewalhis_number;

	/** The value for the Document available field */
	@Basic(optional = false)
	@Column(name = "renewal_document", nullable = false)
	private boolean renewal_document = false;

	/** The value for the Document available field */
	@Column(name = "renewal_document_id")
	private Long renewal_document_id;

	/*@Column(name = "createdBy", length = 150)
	private String createdBy;

	@Column(name = "lastModifiedBy", length = 150)
	private String lastModifiedBy;*/
	
	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = false, updatable = true)
	private Date lastupdated;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;

	public RenewalReminderHistory() {
		super();
	}

	public RenewalReminderHistory(Long renewalhis_id, Integer vid,  Integer renewalhis_type,
			Integer renewalhis_subtype, Date renewalhis_from, Date renewalhis_to, Integer renewalhis_periedthreshold,
			Integer renewalhis_timethreshold, Date renewalhis_dateofRenewal, String renewalhis_receipt,
			Double renewalhis_Amount, Date renewalhis_dateofpayment, String renewalhis_PayNumber, String renewalhis_authorization,
			String renewalhis_number, Long createdBy, Long lastModifiedBy, boolean MarkForDelete, Date created,
			Date lastupdated, Integer companyId) {
		super();
		this.renewalhis_id = renewalhis_id;
		this.vid = vid;
		this.renewalhis_typeId = renewalhis_type;
		this.renewalhis_subtypeId = renewalhis_subtype;
		this.renewalhis_from = renewalhis_from;
		this.renewalhis_to = renewalhis_to;
		this.renewalhis_periedthreshold = renewalhis_periedthreshold;
		this.renewalhis_timethreshold = renewalhis_timethreshold;
		this.his_dateofRenewal = renewalhis_dateofRenewal;
		this.renewalhis_receipt = renewalhis_receipt;
		this.renewalhis_Amount = renewalhis_Amount;
		this.renewalhis_dateofpayment = renewalhis_dateofpayment;
		this.renewalhis_PayNumber = renewalhis_PayNumber;
		this.renewalhis_authorization = renewalhis_authorization;
		this.renewalhis_number = renewalhis_number;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
	}

	/**
	 * @return the renewalhis_id
	 */
	public Long getRenewalhis_id() {
		return renewalhis_id;
	}

	/**
	 * @param renewalhis_id
	 *            the renewalhis_id to set
	 */
	public void setRenewalhis_id(Long renewalhis_id) {
		this.renewalhis_id = renewalhis_id;
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

	/**
	 * @return the vehicle_registration
	 *//*
	public String getVehicle_registration() {
		return vehicle_registration;
	}

	*//**
	 * @param vehicle_registration
	 *            the vehicle_registration to set
	 *//*
	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getRenewalhis_type() {
		return renewalhis_type;
	}

	public void setRenewalhis_type(String renewalhis_type) {
		this.renewalhis_type = renewalhis_type;
	}

	public String getRenewalhis_subtype() {
		return renewalhis_subtype;
	}

	public void setRenewalhis_subtype(String renewalhis_subtype) {
		this.renewalhis_subtype = renewalhis_subtype;
	}*/

	public Date getRenewalhis_from() {
		return renewalhis_from;
	}

	public void setRenewalhis_from(Date renewalhis_from) {
		this.renewalhis_from = renewalhis_from;
	}

	public Date getRenewalhis_to() {
		return renewalhis_to;
	}

	public void setRenewalhis_to(Date renewalhis_to) {
		this.renewalhis_to = renewalhis_to;
	}

	public Integer getRenewalhis_periedthreshold() {
		return renewalhis_periedthreshold;
	}

	public void setRenewalhis_periedthreshold(Integer renewalhis_periedthreshold) {
		this.renewalhis_periedthreshold = renewalhis_periedthreshold;
	}

	public Integer getRenewalhis_timethreshold() {
		return renewalhis_timethreshold;
	}

	public void setRenewalhis_timethreshold(Integer renewalhis_timethreshold) {
		this.renewalhis_timethreshold = renewalhis_timethreshold;
	}

	/*public String getRenewalhis_dateofRenewal() {
		return renewalhis_dateofRenewal;
	}

	public void setRenewalhis_dateofRenewal(String renewalhis_dateofRenewal) {
		this.renewalhis_dateofRenewal = renewalhis_dateofRenewal;
	}*/

	public String getRenewalhis_receipt() {
		return renewalhis_receipt;
	}

	public void setRenewalhis_receipt(String renewalhis_receipt) {
		this.renewalhis_receipt = renewalhis_receipt;
	}

	public Double getRenewalhis_Amount() {
		return renewalhis_Amount;
	}

	public void setRenewalhis_Amount(Double renewalhis_Amount) {
		this.renewalhis_Amount = renewalhis_Amount;
	}

	public Date getRenewalhis_dateofpayment() {
		return renewalhis_dateofpayment;
	}

	public void setRenewalhis_dateofpayment(Date renewalhis_dateofpayment) {
		this.renewalhis_dateofpayment = renewalhis_dateofpayment;
	}

	/*public String getRenewalhis_paidby() {
		return renewalhis_paidby;
	}

	public void setRenewalhis_paidby(String renewalhis_paidby) {
		this.renewalhis_paidby = renewalhis_paidby;
	}

	public String getRenewalhis_paymentType() {
		return renewalhis_paymentType;
	}

	public void setRenewalhis_paymentType(String renewalhis_paymentType) {
		this.renewalhis_paymentType = renewalhis_paymentType;
	}*/

	public String getRenewalhis_PayNumber() {
		return renewalhis_PayNumber;
	}

	public void setRenewalhis_PayNumber(String renewalhis_PayNumber) {
		this.renewalhis_PayNumber = renewalhis_PayNumber;
	}

	public String getRenewalhis_authorization() {
		return renewalhis_authorization;
	}

	public void setRenewalhis_authorization(String renewalhis_authorization) {
		this.renewalhis_authorization = renewalhis_authorization;
	}

	public String getRenewalhis_number() {
		return renewalhis_number;
	}

	public void setRenewalhis_number(String renewalhis_number) {
		this.renewalhis_number = renewalhis_number;
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

	/*public String getCreatedBy() {
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
	}*/

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
	 * @return the renewalhis_typeId
	 */
	public Integer getRenewalhis_typeId() {
		return renewalhis_typeId;
	}

	/**
	 * @param renewalhis_typeId the renewalhis_typeId to set
	 */
	public void setRenewalhis_typeId(Integer renewalhis_typeId) {
		this.renewalhis_typeId = renewalhis_typeId;
	}

	/**
	 * @return the renewalhis_subtypeId
	 */
	public Integer getRenewalhis_subtypeId() {
		return renewalhis_subtypeId;
	}

	/**
	 * @param renewalhis_subtypeId the renewalhis_subtypeId to set
	 */
	public void setRenewalhis_subtypeId(Integer renewalhis_subtypeId) {
		this.renewalhis_subtypeId = renewalhis_subtypeId;
	}

	/**
	 * @return the his_dateofRenewal
	 */
	public Date getHis_dateofRenewal() {
		return his_dateofRenewal;
	}

	/**
	 * @param his_dateofRenewal the his_dateofRenewal to set
	 */
	public void setHis_dateofRenewal(Date his_dateofRenewal) {
		this.his_dateofRenewal = his_dateofRenewal;
	}

	/**
	 * @return the renewalhis_paidbyId
	 */
	public Long getRenewalhis_paidbyId() {
		return renewalhis_paidbyId;
	}

	/**
	 * @param renewalhis_paidbyId the renewalhis_paidbyId to set
	 */
	public void setRenewalhis_paidbyId(Long renewalhis_paidbyId) {
		this.renewalhis_paidbyId = renewalhis_paidbyId;
	}

	/**
	 * @return the renewalhis_paymentTypeId
	 */
	public short getRenewalhis_paymentTypeId() {
		return renewalhis_paymentTypeId;
	}

	/**
	 * @param renewalhis_paymentTypeId the renewalhis_paymentTypeId to set
	 */
	public void setRenewalhis_paymentTypeId(short renewalhis_paymentTypeId) {
		this.renewalhis_paymentTypeId = renewalhis_paymentTypeId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((renewalhis_receipt == null) ? 0 : renewalhis_receipt.hashCode());
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
		RenewalReminderHistory other = (RenewalReminderHistory) obj;
		if (renewalhis_receipt == null) {
			if (other.renewalhis_receipt != null)
				return false;
		} else if (!renewalhis_receipt.equals(other.renewalhis_receipt))
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
		builder.append("RenewalReminderHistory [renewalhis_id=");
		builder.append(renewalhis_id);
		builder.append(", vid=");
		builder.append(vid);
		/*builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);*/
		/*builder.append(", renewalhis_type=");
		builder.append(renewalhis_type);
		builder.append(", renewalhis_subtype=");
		builder.append(renewalhis_subtype);*/
		builder.append(", renewalhis_from=");
		builder.append(renewalhis_from);
		builder.append(", renewalhis_to=");
		builder.append(renewalhis_to);
		builder.append(", renewalhis_periedthreshold=");
		builder.append(renewalhis_periedthreshold);
		builder.append(", renewalhis_timethreshold=");
		builder.append(renewalhis_timethreshold);
		/*builder.append(", renewalhis_dateofRenewal=");
		builder.append(renewalhis_dateofRenewal);*/
		builder.append(", renewalhis_receipt=");
		builder.append(renewalhis_receipt);
		builder.append(", renewalhis_Amount=");
		builder.append(renewalhis_Amount);
		builder.append(", renewalhis_dateofpayment=");
		builder.append(renewalhis_dateofpayment);
		/*builder.append(", renewalhis_paidby=");
		builder.append(renewalhis_paidby);
		builder.append(", renewalhis_paymentType=");
		builder.append(renewalhis_paymentType);*/
		builder.append(", renewalhis_PayNumber=");
		builder.append(renewalhis_PayNumber);
		builder.append(", renewalhis_authorization=");
		builder.append(renewalhis_authorization);
		builder.append(", renewalhis_number=");
		builder.append(renewalhis_number);
		builder.append(", renewal_document=");
		builder.append(renewal_document);
		builder.append(", renewal_document_id=");
		builder.append(renewal_document_id);
		/*builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);*/
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

}

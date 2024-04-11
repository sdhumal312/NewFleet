package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 *
 *
 */

public class RenewalReminderHistoryDto {

	private Long renewalhis_id;
	private Integer vid;
	private String vehicle_registration;
	private String renewalhis_type;
	private String renewalhis_subtype;
	private String renewalhis_from;
	private String renewalhis_to;
	private Integer renewalhis_periedthreshold;
	private Integer renewalhis_timethreshold;
	private String renewalhis_dateofRenewal;
	private String renewalhis_receipt;
	private Double renewalhis_Amount;
	private String renewalhis_dateofpayment;
	private String renewalhis_paidby;
	private String renewalhis_paymentType;
	private String renewalhis_PayNumber;
	private String renewalhis_authorization;
	private String renewalhis_number;

	private boolean renewal_document;
	/** The value for the Document available field */
	private Long renewal_document_id;

	private String createdBy;

	private String lastModifiedBy;
	
	private Date renewalhis_fromDate;
	private Date renewalhis_toDate;

	public Long getRenewalhis_id() {
		return renewalhis_id;
	}

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
	 */
	public String getVehicle_registration() {
		return vehicle_registration;
	}

	/**
	 * @param vehicle_registration
	 *            the vehicle_registration to set
	 */
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
	}

	public String getRenewalhis_from() {
		return renewalhis_from;
	}

	public void setRenewalhis_from(String renewalhis_from) {
		this.renewalhis_from = renewalhis_from;
	}

	public String getRenewalhis_to() {
		return renewalhis_to;
	}

	public void setRenewalhis_to(String renewalhis_to) {
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

	public String getRenewalhis_dateofRenewal() {
		return renewalhis_dateofRenewal;
	}

	public void setRenewalhis_dateofRenewal(String renewalhis_dateofRenewal) {
		this.renewalhis_dateofRenewal = renewalhis_dateofRenewal;
	}

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
		this.renewalhis_Amount = Utility.round(renewalhis_Amount, 2);
	}

	public String getRenewalhis_dateofpayment() {
		return renewalhis_dateofpayment;
	}

	public void setRenewalhis_dateofpayment(String renewalhis_dateofpayment) {
		this.renewalhis_dateofpayment = renewalhis_dateofpayment;
	}

	public String getRenewalhis_paidby() {
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
	}

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
	 * @return the renewalhis_fromDate
	 */
	public Date getRenewalhis_fromDate() {
		return renewalhis_fromDate;
	}

	/**
	 * @param renewalhis_fromDate the renewalhis_fromDate to set
	 */
	public void setRenewalhis_fromDate(Date renewalhis_fromDate) {
		this.renewalhis_fromDate = renewalhis_fromDate;
	}

	/**
	 * @return the renewalhis_toDate
	 */
	public Date getRenewalhis_toDate() {
		return renewalhis_toDate;
	}

	/**
	 * @param renewalhis_toDate the renewalhis_toDate to set
	 */
	public void setRenewalhis_toDate(Date renewalhis_toDate) {
		this.renewalhis_toDate = renewalhis_toDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenewalReminderHistoryDto [renewalhis_id=");
		builder.append(renewalhis_id);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", renewalhis_type=");
		builder.append(renewalhis_type);
		builder.append(", renewalhis_subtype=");
		builder.append(renewalhis_subtype);
		builder.append(", renewalhis_from=");
		builder.append(renewalhis_from);
		builder.append(", renewalhis_to=");
		builder.append(renewalhis_to);
		builder.append(", renewalhis_periedthreshold=");
		builder.append(renewalhis_periedthreshold);
		builder.append(", renewalhis_timethreshold=");
		builder.append(renewalhis_timethreshold);
		builder.append(", renewalhis_dateofRenewal=");
		builder.append(renewalhis_dateofRenewal);
		builder.append(", renewalhis_receipt=");
		builder.append(renewalhis_receipt);
		builder.append(", renewalhis_Amount=");
		builder.append(renewalhis_Amount);
		builder.append(", renewalhis_dateofpayment=");
		builder.append(renewalhis_dateofpayment);
		builder.append(", renewalhis_paidby=");
		builder.append(renewalhis_paidby);
		builder.append(", renewalhis_paymentType=");
		builder.append(renewalhis_paymentType);
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
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append("]");
		return builder.toString();
	}

}

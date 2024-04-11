package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class TripsheetDueAmountPaymentDto {
	
	private Long	TripsheetDueAmountPaymentId;
	
	private Long tripsheetDueAmountId;
	
	private short paymentModeId;
	
	private short	paymentTypeId;
	
	private double	paidAmount;
	
	private Date	paidDate;
	
	private Long	createdById;
	
	private Date	creationDate;
	
	private Long	lastUpdatedBy;
	
	private Date	lastUpdatedOn;
	
	private Integer	companyId;
	
	private boolean markForDelete;
	
	private String	reference;
	
	public TripsheetDueAmountPaymentDto() {
		super();
	}

	public TripsheetDueAmountPaymentDto(Long tripsheetDueAmountPaymentId, Long tripsheetDueAmountId, short paymentModeId,
			short paymentTypeId, double paidAmount, Date paidDate, short dueStatus, Long createdById,
			Date creationDate, Long lastUpdatedBy, Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		TripsheetDueAmountPaymentId = tripsheetDueAmountPaymentId;
		this.tripsheetDueAmountId = tripsheetDueAmountId;
		this.paymentModeId = paymentModeId;
		this.paymentTypeId = paymentTypeId;
		this.paidAmount = paidAmount;
		this.paidDate = paidDate;
		this.createdById = createdById;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getTripsheetDueAmountPaymentId() {
		return TripsheetDueAmountPaymentId;
	}

	public void setTripsheetDueAmountPaymentId(Long tripsheetDueAmountPaymentId) {
		TripsheetDueAmountPaymentId = tripsheetDueAmountPaymentId;
	}

	public Long getTripsheetDueAmountId() {
		return tripsheetDueAmountId;
	}

	public void setTripsheetDueAmountId(Long tripsheetDueAmountId) {
		this.tripsheetDueAmountId = tripsheetDueAmountId;
	}

	public short getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(short paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public double getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = Utility.round(paidAmount, 2);
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		return "TripsheetDueAmountPaymentDto [TripsheetDueAmountPaymentId=" + TripsheetDueAmountPaymentId
				+ ", tripsheetDueAmountId=" + tripsheetDueAmountId + ", paymentModeId=" + paymentModeId
				+ ", paymentTypeId=" + paymentTypeId + ", paidAmount=" + paidAmount + ", paidDate=" + paidDate
				+ ", createdById=" + createdById + ", creationDate=" + creationDate + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}

	
	
}
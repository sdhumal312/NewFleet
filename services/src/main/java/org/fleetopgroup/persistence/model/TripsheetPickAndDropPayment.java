package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TripsheetPickAndDropPayment")
public class TripsheetPickAndDropPayment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripsheetPickAndDropPaymentId")
	private Long tripsheetPickAndDropPaymentId;
	
	@Column(name = "tripsheetPickAndDropInvoiceSummaryId")
	private Long tripsheetPickAndDropInvoiceSummaryId;
	
	@Column(name = "paymentType")
	private short paymentType;
	
	@Column(name = "transactionNumber")
	private String transactionNumber;
	
	@Column(name = "paymentMode")
	private short paymentMode;
	
	@Column(name = "paidDate")
	private Date	paidDate;
	
	@Column(name = "paidAmount")
	private Double paidAmount;
	
	@Column(name = "creationDate")
	private Date	creationDate;
	
	@Column(name = "lastUpdatedOn")
	private Date	lastUpdatedOn;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "lastUpdatedBy")
	private Long	lastUpdatedBy;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public TripsheetPickAndDropPayment(Long tripsheetPickAndDropPaymentId, Long tripsheetPickAndDropInvoiceSummaryId,
			short paymentType, short paymentMode, Date paidDate, Double paidAmount, Date creationDate,
			Date lastUpdatedOn, Long createdById, Long lastUpdatedBy, Integer companyId, boolean markForDelete) {
		super();
		this.tripsheetPickAndDropPaymentId = tripsheetPickAndDropPaymentId;
		this.tripsheetPickAndDropInvoiceSummaryId = tripsheetPickAndDropInvoiceSummaryId;
		this.paymentType = paymentType;
		this.paymentMode = paymentMode;
		this.paidDate = paidDate;
		this.paidAmount = paidAmount;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public TripsheetPickAndDropPayment() {
		super();
	}

	public Long getTripsheetPickAndDropPaymentId() {
		return tripsheetPickAndDropPaymentId;
	}

	public void setTripsheetPickAndDropPaymentId(Long tripsheetPickAndDropPaymentId) {
		this.tripsheetPickAndDropPaymentId = tripsheetPickAndDropPaymentId;
	}

	public Long getTripsheetPickAndDropInvoiceSummaryId() {
		return tripsheetPickAndDropInvoiceSummaryId;
	}

	public void setTripsheetPickAndDropInvoiceSummaryId(Long tripsheetPickAndDropInvoiceSummaryId) {
		this.tripsheetPickAndDropInvoiceSummaryId = tripsheetPickAndDropInvoiceSummaryId;
	}

	public short getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(short paymentType) {
		this.paymentType = paymentType;
	}

	public short getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(short paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	@Override
	public String toString() {
		return "TripsheetPickAndDropPayment [tripsheetPickAndDropPaymentId=" + tripsheetPickAndDropPaymentId
				+ ", tripsheetPickAndDropInvoiceSummaryId=" + tripsheetPickAndDropInvoiceSummaryId + ", paymentType="
				+ paymentType + ", paymentMode=" + paymentMode + ", paidDate=" + paidDate + ", paidAmount=" + paidAmount
				+ ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", createdById=" + createdById
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}
	
}
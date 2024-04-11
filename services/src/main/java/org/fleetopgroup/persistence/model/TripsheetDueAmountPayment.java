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
@Table(name ="TripsheetDueAmountPayment")
public class TripsheetDueAmountPayment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TripsheetDueAmountPaymentId")
	private Long	TripsheetDueAmountPaymentId;
	
	@Column(name = "tripsheetDueAmountId")
	private Long tripsheetDueAmountId;
	
	@Column(name = "paymentModeId")
	private short paymentModeId;
	
	@Column(name = "paymentTypeId")
	private short	paymentTypeId;
	
	@Column(name = "transactionMode")
	private short	transactionMode;
	
	@Column(name = "paidAmount")
	private double	paidAmount;
	
	@Column(name = "paidDate")
	private Date	paidDate;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "creationDate")
	private Date	creationDate;
	
	@Column(name = "lastUpdatedBy")
	private Long	lastUpdatedBy;
	
	@Column(name = "lastUpdatedOn")
	private Date	lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "transactionNo")
	private String	transactionNo;
	
	@Column(name = "reference")
	private String	reference;
	
	@Column(name="billSelectionId")
   	private Short billSelectionId;
	
	
	public TripsheetDueAmountPayment() {
		super();
		//constructor
	}

	public TripsheetDueAmountPayment(Long tripsheetDueAmountPaymentId, Long tripsheetDueAmountId, short paymentModeId,
			short paymentTypeId, double paidAmount, Date paidDate, short dueStatus, Long createdById,
			Date creationDate, Long lastUpdatedBy, Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		TripsheetDueAmountPaymentId = tripsheetDueAmountPaymentId;
		this.tripsheetDueAmountId = tripsheetDueAmountId;
		this.paymentModeId = paymentModeId;
		this.paymentTypeId = paymentTypeId;
		this.paidAmount = paidAmount;
		this.createdById = createdById;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.billSelectionId = billSelectionId;
	}

	public Short getBillSelectionId() {
		return billSelectionId;
	}

	public void setBillSelectionId(Short billSelectionId) {
		this.billSelectionId = billSelectionId;
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
		this.paidAmount = paidAmount;
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

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public short getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(short transactionMode) {
		this.transactionMode = transactionMode;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	@Override
	public String toString() {
		return "TripsheetDueAmountPayment [TripsheetDueAmountPaymentId=" + TripsheetDueAmountPaymentId
				+ ", tripsheetDueAmountId=" + tripsheetDueAmountId + ", paymentModeId=" + paymentModeId
				+ ", paymentTypeId=" + paymentTypeId + ", transactionMode=" + transactionMode + ", paidAmount="
				+ paidAmount + ", paidDate=" + paidDate + ", createdById=" + createdById + ", creationDate="
				+ creationDate + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedOn=" + lastUpdatedOn
				+ ", companyId=" + companyId + ", markForDelete=" + markForDelete + ", transactionNo=" + transactionNo
				+ ", reference=" + reference + ", billSelectionId=" + billSelectionId + "]";
	}
	
}	

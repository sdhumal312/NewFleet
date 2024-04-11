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
@Table(name = "TripsheetPickAndDropInvoiceSummary")
public class TripsheetPickAndDropInvoiceSummary implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripsheetPickAndDropInvoiceSummaryId")
	private Long tripsheetPickAndDropInvoiceSummaryId;
	
	@Column(name = "invoiceNumber")
	private Long invoiceNumber;
	
	@Column(name = "vendorId")
	private Integer vendorId;
	
	@Column(name = "totalKm")
	private Integer totalKm;
	
	@Column(name = "totalAmount")
	private Double totalAmount;
	
	@Column(name = "balanceAmount")
	private Double balanceAmount;
	
	@Column(name = "totalAdvance")
	private Double totalAdvance;
	
	@Column(name = "invoiceDate")
	private Date	invoiceDate;
	
	@Column(name = "paymentStatus")
	private short	paymentStatus;
	
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
	

	public TripsheetPickAndDropInvoiceSummary() {
		super();
	}


	public TripsheetPickAndDropInvoiceSummary(Long tripsheetPickAndDropInvoiceSummaryId, Long invoiceNumber,
			Integer vendorId, Integer totalKm, Double totalAmount, Double balanceAmount, Double totalAdvance,
			Date invoiceDate, short paymentStatus, Date creationDate, Date lastUpdatedOn, Long createdById,
			Long lastUpdatedBy, Integer companyId, boolean markForDelete) {
		super();
		this.tripsheetPickAndDropInvoiceSummaryId = tripsheetPickAndDropInvoiceSummaryId;
		this.invoiceNumber = invoiceNumber;
		this.vendorId = vendorId;
		this.totalKm = totalKm;
		this.totalAmount = totalAmount;
		this.balanceAmount = balanceAmount;
		this.totalAdvance = totalAdvance;
		this.invoiceDate = invoiceDate;
		this.paymentStatus = paymentStatus;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}





	public Long getTripsheetPickAndDropInvoiceSummaryId() {
		return tripsheetPickAndDropInvoiceSummaryId;
	}


	public void setTripsheetPickAndDropInvoiceSummaryId(Long tripsheetPickAndDropInvoiceSummaryId) {
		this.tripsheetPickAndDropInvoiceSummaryId = tripsheetPickAndDropInvoiceSummaryId;
	}


	public Long getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public Integer getVendorId() {
		return vendorId;
	}


	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}


	public Integer getTotalKm() {
		return totalKm;
	}


	public void setTotalKm(Integer totalKm) {
		this.totalKm = totalKm;
	}


	public Double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}


	public Double getTotalAdvance() {
		return totalAdvance;
	}


	public void setTotalAdvance(Double totalAdvance) {
		this.totalAdvance = totalAdvance;
	}


	public Date getInvoiceDate() {
		return invoiceDate;
	}


	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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
	

	public Double getBalanceAmount() {
		return balanceAmount;
	}


	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}


	public short getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(short paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	@Override
	public String toString() {
		return "TripsheetPickAndDropInvoiceSummary [tripsheetPickAndDropInvoiceSummaryId="
				+ tripsheetPickAndDropInvoiceSummaryId + ", invoiceNumber=" + invoiceNumber + ", vendorId=" + vendorId
				+ ", totalKm=" + totalKm + ", totalAmount=" + totalAmount + ", balanceAmount=" + balanceAmount
				+ ", totalAdvance=" + totalAdvance + ", invoiceDate=" + invoiceDate + ", paymentStatus=" + paymentStatus
				+ ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", createdById=" + createdById
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}
	
	
}	
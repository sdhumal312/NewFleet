package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

import ch.qos.logback.classic.pattern.Util;

public class TripsheetPickAndDropInvoiceSummaryDto {
	
	private Long tripsheetPickAndDropInvoiceSummaryId;
	
	private Long invoiceNumber;
	
	private Integer vendorId;
	
	private Integer totalKm;
	
	private Double totalAmount;
	
	private Double balanceAmount;
	
	private Double totalAdvance;
	
	private Date	invoiceDate;
	
	private String	invoiceDateStr;
	
	private short	paymentStatus;
	
	private Date	creationDate;
	
	private String	creationDateStr;
	
	private Date	lastUpdatedOn;
	
	private String	lastUpdatedOnStr;
	
	private Long	createdById;
	
	private Long	lastUpdatedBy;
	
	private Integer	companyId;
	
	private boolean markForDelete;
	
	private String partyName;
	
	private short paymentModeId;
	
	private String paymentModeName;
	
	
	public TripsheetPickAndDropInvoiceSummaryDto() {
		super();
	}
	
	
	public TripsheetPickAndDropInvoiceSummaryDto(short paymentModeId, String paymentModeName) {
		super();
		this.paymentModeId = paymentModeId;
		this.paymentModeName = paymentModeName;
	}

	public TripsheetPickAndDropInvoiceSummaryDto(Long tripsheetPickAndDropInvoiceSummaryId, Long invoiceNumber,
			Integer vendorId, Integer totalKm, Double totalAmount, Double totalAdvance, Date invoiceDate,
			Date creationDate, Date lastUpdatedOn, Long createdById, Long lastUpdatedBy, Integer companyId,
			boolean markForDelete) {
		super();
		this.tripsheetPickAndDropInvoiceSummaryId = tripsheetPickAndDropInvoiceSummaryId;
		this.invoiceNumber = invoiceNumber;
		this.vendorId = vendorId;
		this.totalKm = totalKm;
		this.totalAmount = totalAmount;
		this.totalAdvance = totalAdvance;
		this.invoiceDate = invoiceDate;
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
		this.totalAmount = Utility.round(totalAmount, 2) ;
	}


	public Double getTotalAdvance() {
		return totalAdvance;
	}


	public void setTotalAdvance(Double totalAdvance) {
		this.totalAdvance = Utility.round(totalAdvance, 2);
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
	

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}


	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}


	public String getCreationDateStr() {
		return creationDateStr;
	}


	public void setCreationDateStr(String creationDateStr) {
		this.creationDateStr = creationDateStr;
	}


	public String getLastUpdatedOnStr() {
		return lastUpdatedOnStr;
	}


	public void setLastUpdatedOnStr(String lastUpdatedOnStr) {
		this.lastUpdatedOnStr = lastUpdatedOnStr;
	}

	public String getPartyName() {
		return partyName;
	}


	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}


	public short getPaymentModeId() {
		return paymentModeId;
	}


	public void setPaymentModeId(short paymentModeId) {
		this.paymentModeId = paymentModeId;
	}


	public String getPaymentModeName() {
		return paymentModeName;
	}


	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}
	

	public Double getBalanceAmount() {
		return balanceAmount;
	}


	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2);
	}


	public short getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(short paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	@Override
	public String toString() {
		return "TripsheetPickAndDropInvoiceSummaryDto [tripsheetPickAndDropInvoiceSummaryId="
				+ tripsheetPickAndDropInvoiceSummaryId + ", invoiceNumber=" + invoiceNumber + ", vendorId=" + vendorId
				+ ", totalKm=" + totalKm + ", totalAmount=" + totalAmount + ", totalAdvance=" + totalAdvance
				+ ", invoiceDate=" + invoiceDate + ", invoiceDateStr=" + invoiceDateStr + ", creationDate="
				+ creationDate + ", creationDateStr=" + creationDateStr + ", lastUpdatedOn=" + lastUpdatedOn
				+ ", lastUpdatedOnStr=" + lastUpdatedOnStr + ", createdById=" + createdById + ", lastUpdatedBy="
				+ lastUpdatedBy + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
}
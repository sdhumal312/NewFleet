package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;


public class TripsheetPickAndDropPaymentDto {
	
	private Long tripsheetPickAndDropPaymentId;
	
	private Long tripsheetPickAndDropInvoiceSummaryId;
	
	private short paymentType;
	
	private String paymentTypeStr;
	
	private short paymentMode;
	
	private String paymentModeStr;
	
	private Date	paidDate;
	
	private String	paidDateStr;
	
	private Double paidAmount;
	
	private Date	creationDate;
	
	private Date	lastUpdatedOn;
	
	private Long	createdById;
	
	private Long	lastUpdatedBy;
	
	private Integer	companyId;
	
	private boolean markForDelete;
	
	private Long invoiceNumber;
	
	private Integer vendorId;
	
	private Double totalAmount;
	
	private String	invoiceDateStr;
	
	private String	vendorName;
	
	private String invoiceNumStr;
	
	
	public TripsheetPickAndDropPaymentDto(Long tripsheetPickAndDropPaymentId, Long tripsheetPickAndDropInvoiceSummaryId,
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

	public TripsheetPickAndDropPaymentDto() {
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
		this.paidAmount = Utility.round(paidAmount, 2);
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

	public String getPaidDateStr() {
		return paidDateStr;
	}

	public void setPaidDateStr(String paidDateStr) {
		this.paidDateStr = paidDateStr;
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

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = Utility.round(totalAmount, 2) ;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getPaymentTypeStr() {
		return paymentTypeStr;
	}

	public void setPaymentTypeStr(String paymentTypeStr) {
		this.paymentTypeStr = paymentTypeStr;
	}

	public String getPaymentModeStr() {
		return paymentModeStr;
	}

	public void setPaymentModeStr(String paymentModeStr) {
		this.paymentModeStr = paymentModeStr;
	}
	
	public String getInvoiceNumStr() {
		return invoiceNumStr;
	}

	public void setInvoiceNumStr(String invoiceNumStr) {
		this.invoiceNumStr = invoiceNumStr;
	}

	@Override
	public String toString() {
		return "TripsheetPickAndDropPaymentDto [tripsheetPickAndDropPaymentId=" + tripsheetPickAndDropPaymentId
				+ ", tripsheetPickAndDropInvoiceSummaryId=" + tripsheetPickAndDropInvoiceSummaryId + ", paymentType="
				+ paymentType + ", paymentMode=" + paymentMode + ", paidDate=" + paidDate + ", paidDateStr="
				+ paidDateStr + ", paidAmount=" + paidAmount + ", creationDate=" + creationDate + ", lastUpdatedOn="
				+ lastUpdatedOn + ", createdById=" + createdById + ", lastUpdatedBy=" + lastUpdatedBy + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + ", invoiceNumber=" + invoiceNumber + ", vendorId="
				+ vendorId + ", totalAmount=" + totalAmount + ", invoiceDateStr=" + invoiceDateStr + ", vendorName="
				+ vendorName + "]";
	}
	
	
}
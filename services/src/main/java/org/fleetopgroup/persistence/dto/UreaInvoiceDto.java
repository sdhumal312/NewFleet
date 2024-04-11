package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class UreaInvoiceDto {

	
	private Long 		ureaInvoiceId;
	
	private Long 		ureaInvoiceNumber;
	
	private Integer		wareHouseLocation;
	
	private Long		purchaseOrderId;
	
	private String		invoiceNumber;
	
	private Timestamp	invoiceDate;
	
	private Double		invoiceAmount;
	
	private Integer		vendorId;
	
	private short		paymentTypeId;
	
	private String		paymentNumber;
	
	private Double 		totalAmount;
	
	private String 		description;
	
	private String 		ureaManufacturer;
	
	private short		vendorPaymentStatus;
	
	private String		vendorPaymentStatusStr;
	
	private Long 		ureaApprovalId;
	
	private Timestamp 	vendorPaymentDate;
	
	private Long 		createdById;
	
	private Long	 	lastModifiedById;

	private boolean			 markForDelete;
	
	private Timestamp		createdOn;
	
	private Timestamp		lastModifiedBy;
	
	private Integer			companyId;
	
	private String			poNumber;
	
	private Double 			quantity;
	
	private String			vendorName;
	
	private String			locationName;
	
	private String			createdBy;
	
	private String			invoiceDateStr;
	
	private String			lastUpdatedBy;
	
	private Long			purchaseOrderNumber;
	
	private String			vendorLocation;

	private String			paymentType;
	
	private String			createdOnStr;
	
	private String			lastModifiedOnStr;
	
	private Long tallyCompanyId;
	
	private String tallyCompanyName;
	
	private boolean urea_document;
	
	private Long urea_document_id;
	
	private Integer subLocationId;
	
	private String subLocation;
	
	private Long approvalId;
	
	private String approvalNumber;
	
	public Long getUreaInvoiceId() {
		return ureaInvoiceId;
	}

	public void setUreaInvoiceId(Long ureaInvoiceId) {
		this.ureaInvoiceId = ureaInvoiceId;
	}

	public Long getUreaInvoiceNumber() {
		return ureaInvoiceNumber;
	}

	public void setUreaInvoiceNumber(Long ureaInvoiceNumber) {
		this.ureaInvoiceNumber = ureaInvoiceNumber;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = Utility.round( invoiceAmount, 2);
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = Utility.round( totalAmount, 2);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}

	public Long getUreaApprovalId() {
		return ureaApprovalId;
	}

	public void setUreaApprovalId(Long ureaApprovalId) {
		this.ureaApprovalId = ureaApprovalId;
	}

	public Timestamp getVendorPaymentDate() {
		return vendorPaymentDate;
	}

	public void setVendorPaymentDate(Timestamp vendorPaymentDate) {
		this.vendorPaymentDate = vendorPaymentDate;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Timestamp lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Long getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(Long purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getVendorLocation() {
		return vendorLocation;
	}

	public void setVendorLocation(String vendorLocation) {
		this.vendorLocation = vendorLocation;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public String getLastModifiedOnStr() {
		return lastModifiedOnStr;
	}

	public void setLastModifiedOnStr(String lastModifiedOnStr) {
		this.lastModifiedOnStr = lastModifiedOnStr;
	}

	public String getUreaManufacturer() {
		return ureaManufacturer;
	}

	public void setUreaManufacturer(String ureaManufacturer) {
		this.ureaManufacturer = ureaManufacturer;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public String getTallyCompanyName() {
		return tallyCompanyName;
	}

	public void setTallyCompanyName(String tallyCompanyName) {
		this.tallyCompanyName = tallyCompanyName;
	}
	
	public boolean isUrea_document() {
		return urea_document;
	}

	public void setUrea_document(boolean urea_document) {
		this.urea_document = urea_document;
	}

	public Long getUrea_document_id() {
		return urea_document_id;
	}

	public void setUrea_document_id(Long urea_document_id) {
		this.urea_document_id = urea_document_id;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	public String getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
	}

	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	
	public String getVendorPaymentStatusStr() {
		return vendorPaymentStatusStr;
	}

	public void setVendorPaymentStatusStr(String vendorPaymentStatusStr) {
		this.vendorPaymentStatusStr = vendorPaymentStatusStr;
	}

	@Override
	public String toString() {
		return "UreaInvoiceDto [ureaInvoiceId=" + ureaInvoiceId + ", ureaInvoiceNumber=" + ureaInvoiceNumber
				+ ", wareHouseLocation=" + wareHouseLocation + ", purchaseOrderId=" + purchaseOrderId
				+ ", invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate + ", invoiceAmount="
				+ invoiceAmount + ", vendorId=" + vendorId + ", paymentTypeId=" + paymentTypeId + ", paymentNumber="
				+ paymentNumber + ", totalAmount=" + totalAmount + ", description=" + description
				+ ", vendorPaymentStatus=" + vendorPaymentStatus + ", ureaApprovalId=" + ureaApprovalId
				+ ", vendorPaymentDate=" + vendorPaymentDate + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", markForDelete=" + markForDelete + ", createdOn=" + createdOn
				+ ", lastModifiedBy=" + lastModifiedBy + ", companyId=" + companyId + ", poNumber=" + poNumber
				+ ", quantity=" + quantity + ", vendorName=" + vendorName + ", locationName=" + locationName
				+ ", createdBy=" + createdBy + ", invoiceDateStr=" + invoiceDateStr + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", purchaseOrderNumber=" + purchaseOrderNumber + ", vendorLocation=" + vendorLocation
				+ ", paymentType=" + paymentType + ", createdOnStr=" + createdOnStr + ", lastModifiedOnStr="
				+ lastModifiedOnStr + ", ureaManufacturer=" + ureaManufacturer + "]";
	}

	
}

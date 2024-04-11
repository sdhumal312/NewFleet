package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class ClothInvoiceDto {

	private Long 		clothInvoiceId; 
	
	private Long 		clothInvoiceNumber;
	
	private Integer		wareHouseLocation;
	
	private Long		purchaseOrderId;
	
	private String		invoiceNumber;
	
	private Timestamp	invoiceDate;
	
	private Double		invoiceAmount;
	
	private Integer		vendorId;
	
	private short		paymentTypeId;
	
	private String		paymentNumber;
	
	private Double 		totalClothAmount;
	
	private String 		description;
	
	private short		vendorPaymentStatus;
	
	private String 		vendorPaymentStatusStr;
	
	private Long 		clothApprovalId;
	
	private Timestamp 	vendorPaymentDate;
	
	private Long createdById;

	private Long	 lastModifiedById;

	private boolean			 markForDelete;
	
	private Timestamp		createdOn;
	
	private Timestamp		lastModifiedBy;
	
	private Integer			companyId;
	
	private String			poNumber;
	
	private String			vendorName;
	
	private String			clothLocation;
	
	private String			invoiceDateStr;
	
	private short			clothTypeId;
	
	private String			vendorLocation;
	
	private String			createdBy;
	
	private String			lastUpdatedBy;
	
	private String			createdOnStr;
	
	private String			lastModifiedOnStr;
	
	private Long			purchaseOrderNumber;
	private String			paymentType;
	private Double			quantity;
	private String			clothTypeStr;
	
	private Double			paidAmount;
	private Double			balanceAmount;
	private Double			discountAmount;
	private Long	tallyCompanyId;
	private String	tallyCompanyName;
	private boolean	cloth_document;
	private Long	cloth_document_id;
	private Integer	subLocationId;
	private String	subLocation;
	private Long	approvalId;
	private String	approvalNumber;
	

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = Utility.round(paidAmount, 2);
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2);
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = Utility.round(discountAmount, 2);
	}

	public Long getClothInvoiceId() {
		return clothInvoiceId;
	}

	public Long getClothInvoiceNumber() {
		return clothInvoiceNumber;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public Double getTotalClothAmount() {
		return totalClothAmount;
	}

	public String getDescription() {
		return description;
	}

	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public Long getClothApprovalId() {
		return clothApprovalId;
	}

	public Timestamp getVendorPaymentDate() {
		return vendorPaymentDate;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public Timestamp getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setClothInvoiceId(Long clothInvoiceId) {
		this.clothInvoiceId = clothInvoiceId;
	}

	public void setClothInvoiceNumber(Long clothInvoiceNumber) {
		this.clothInvoiceNumber = clothInvoiceNumber;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = Utility.round(invoiceAmount, 2);
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public void setTotalClothAmount(Double totalClothAmount) {
		this.totalClothAmount = Utility.round(totalClothAmount, 2);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}

	public void setClothApprovalId(Long clothApprovalId) {
		this.clothApprovalId = clothApprovalId;
	}

	public void setVendorPaymentDate(Timestamp vendorPaymentDate) {
		this.vendorPaymentDate = vendorPaymentDate;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setLastModifiedBy(Timestamp lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getVendorName() {
		return vendorName;
	}

	public String getClothLocation() {
		return clothLocation;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setClothLocation(String clothLocation) {
		this.clothLocation = clothLocation;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}

	public short getClothTypeId() {
		return clothTypeId;
	}

	public void setClothTypeId(short clothTypeId) {
		this.clothTypeId = clothTypeId;
	}

	public String getVendorLocation() {
		return vendorLocation;
	}

	public void setVendorLocation(String vendorLocation) {
		this.vendorLocation = vendorLocation;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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

	public Long getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(Long purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
	}

	public String getClothTypeStr() {
		return clothTypeStr;
	}

	public void setClothTypeStr(String clothTypeStr) {
		this.clothTypeStr = clothTypeStr;
	}
	
	public String getVendorPaymentStatusStr() {
		return vendorPaymentStatusStr;
	}

	public void setVendorPaymentStatusStr(String vendorPaymentStatusStr) {
		this.vendorPaymentStatusStr = vendorPaymentStatusStr;
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

	public boolean isCloth_document() {
		return cloth_document;
	}

	public void setCloth_document(boolean cloth_document) {
		this.cloth_document = cloth_document;
	}

	public Long getCloth_document_id() {
		return cloth_document_id;
	}

	public void setCloth_document_id(Long cloth_document_id) {
		this.cloth_document_id = cloth_document_id;
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

	@Override
	public String toString() {
		return "ClothInvoiceDto [clothInvoiceId=" + clothInvoiceId + ", clothInvoiceNumber=" + clothInvoiceNumber
				+ ", wareHouseLocation=" + wareHouseLocation + ", purchaseOrderId=" + purchaseOrderId
				+ ", invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate + ", invoiceAmount="
				+ invoiceAmount + ", vendorId=" + vendorId + ", paymentTypeId=" + paymentTypeId + ", paymentNumber="
				+ paymentNumber + ", totalClothAmount=" + totalClothAmount + ", description=" + description
				+ ", vendorPaymentStatus=" + vendorPaymentStatus + ", clothApprovalId=" + clothApprovalId
				+ ", vendorPaymentDate=" + vendorPaymentDate + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", markForDelete=" + markForDelete + ", createdOn=" + createdOn
				+ ", lastModifiedBy=" + lastModifiedBy + ", companyId=" + companyId + ", poNumber=" + poNumber
				+ ", vendorName=" + vendorName + ", clothLocation=" + clothLocation + ", invoiceDateStr="
				+ invoiceDateStr + ", clothTypeId=" + clothTypeId + ", vendorLocation=" + vendorLocation
				+ ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", createdOnStr=" + createdOnStr
				+ ", lastModifiedOnStr=" + lastModifiedOnStr + ", purchaseOrderNumber=" + purchaseOrderNumber
				+ ", paymentType=" + paymentType + ", quantity=" + quantity + ", clothTypeStr=" + clothTypeStr
				+ ", paidAmount=" + paidAmount + ", balanceAmount=" + balanceAmount + ", discountAmount="
				+ discountAmount + ",vendorPaymentStatusStr="+vendorPaymentStatusStr+"]";
	}

	

	

	
}

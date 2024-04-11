package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class FuelInvoiceDto {

	private Long	fuelInvoiceId;
	
	private Long	fuelInvoiceNumber;
	
	private Double	quantity;
	
	private Double	rate;
	
	private Double	discount;
	
	private Double	gst;
	
	private short	discountGstTypeId;
	
	private Double	totalAmount;
	
	private Date	invoiceDate;
	
	private String	invoiceNumber;
	
	private Double	invoiceAmount;
	
	private Integer vendorId;
	
	private short	paymentTypeId;
	
	private Long	tallyCompanyId;
	
	private String	paymentNumber;
	
	private String	poNumber;
	
	private String	description;
	
	private Integer	companyId;
	
	private Long	documentId;
	
	private Long	createdById;
	
	private Long	lastUpdatedById;
	
	private Date	createdOn;
	
	private Date	lastUpdatedOn;
	
	private boolean	markForDelete;
	
	private Integer petrolPumpId;
	
	private String petrolPumpName;
	
	private String	paymentType;
	
	private String	vendorName;
	
	private String	invoiceDateStr;
	
	private String	tallyCompanyName;
	
	private String	createdBy;
	
	private String	lastUpdatedBy;
	
	private String	createdOnStr;
	
	private String	lastUpdatedOnStr;
	
	private String	vendorLocation;
	
	private String	discountGstType;
	
	private double	balanceStock;
	
	private double	transferedQuantity;
	
	private double	updatedStock;
	
	private short	stockTypeId;

	private String	stockType;
	
	private Long	transferedFromInvoiceId;
	
	private Long	transferedFromInvoiceNumber;
	
	private boolean createdAsTransfered;

	public String getPetrolPumpName() {
		return petrolPumpName;
	}

	public void setPetrolPumpName(String petrolPumpName) {
		this.petrolPumpName = petrolPumpName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}

	public String getTallyCompanyName() {
		return tallyCompanyName;
	}

	public void setTallyCompanyName(String tallyCompanyName) {
		this.tallyCompanyName = tallyCompanyName;
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

	public Long getFuelInvoiceId() {
		return fuelInvoiceId;
	}

	public void setFuelInvoiceId(Long fuelInvoiceId) {
		this.fuelInvoiceId = fuelInvoiceId;
	}

	public Long getFuelInvoiceNumber() {
		return fuelInvoiceNumber;
	}

	public void setFuelInvoiceNumber(Long fuelInvoiceNumber) {
		this.fuelInvoiceNumber = fuelInvoiceNumber;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = Utility.round(rate,2);
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = Utility.round(totalAmount, 2);
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getPetrolPumpId() {
		return petrolPumpId;
	}

	public void setPetrolPumpId(Integer petrolPumpId) {
		this.petrolPumpId = petrolPumpId;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = Utility.round(discount, 2);
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = Utility.round(gst, 2);
	}

	public short getDiscountGstTypeId() {
		return discountGstTypeId;
	}

	public void setDiscountGstTypeId(short discountGstTypeId) {
		this.discountGstTypeId = discountGstTypeId;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = Utility.round(invoiceAmount, 2);
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

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public String getLastUpdatedOnStr() {
		return lastUpdatedOnStr;
	}

	public void setLastUpdatedOnStr(String lastUpdatedOnStr) {
		this.lastUpdatedOnStr = lastUpdatedOnStr;
	}

	public String getVendorLocation() {
		return vendorLocation;
	}

	public void setVendorLocation(String vendorLocation) {
		this.vendorLocation = vendorLocation;
	}

	public String getDiscountGstType() {
		return discountGstType;
	}

	public void setDiscountGstType(String discountGstType) {
		this.discountGstType = discountGstType;
	}

	public double getBalanceStock() {
		return balanceStock;
	}

	public void setBalanceStock(double balanceStock) {
		this.balanceStock = Utility.round(balanceStock, 2);
	}

	public short getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(short stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public double getUpdatedStock() {
		return updatedStock;
	}

	public void setUpdatedStock(double updatedStock) {
		this.updatedStock = Utility.round(updatedStock, 2) ;
	}

	public boolean isCreatedAsTransfered() {
		return createdAsTransfered;
	}

	public void setCreatedAsTransfered(boolean createdAsTransfered) {
		this.createdAsTransfered = createdAsTransfered;
	}

	public Long getTransferedFromInvoiceId() {
		return transferedFromInvoiceId;
	}

	public void setTransferedFromInvoiceId(Long transferedFromInvoiceId) {
		this.transferedFromInvoiceId = transferedFromInvoiceId;
	}

	public Long getTransferedFromInvoiceNumber() {
		return transferedFromInvoiceNumber;
	}

	public void setTransferedFromInvoiceNumber(Long transferedFromInvoiceNumber) {
		this.transferedFromInvoiceNumber = transferedFromInvoiceNumber;
	}
	public double getTransferedQuantity() {
		return transferedQuantity;
	}
	public void setTransferedQuantity(double transferedQuantity) {
		this.transferedQuantity = Utility.round(transferedQuantity, 2);
	}

	@Override
	public String toString() {
		return "FuelInvoiceDto [fuelInvoiceId=" + fuelInvoiceId + ", fuelInvoiceNumber=" + fuelInvoiceNumber
				+ ", quantity=" + quantity + ", rate=" + rate + ", discount=" + discount + ", gst=" + gst
				+ ", discountGstTypeId=" + discountGstTypeId + ", totalAmount=" + totalAmount + ", invoiceDate="
				+ invoiceDate + ", invoiceNumber=" + invoiceNumber + ", invoiceAmount=" + invoiceAmount + ", vendorId="
				+ vendorId + ", paymentTypeId=" + paymentTypeId + ", tallyCompanyId=" + tallyCompanyId
				+ ", paymentNumber=" + paymentNumber + ", poNumber=" + poNumber + ", description=" + description
				+ ", companyId=" + companyId + ", documentId=" + documentId + ", createdById=" + createdById
				+ ", lastUpdatedById=" + lastUpdatedById + ", createdOn=" + createdOn + ", lastUpdatedOn="
				+ lastUpdatedOn + ", markForDelete=" + markForDelete + ", petrolPumpId=" + petrolPumpId
				+ ", petrolPumpName=" + petrolPumpName + ", paymentType=" + paymentType + ", vendorName=" + vendorName
				+ ", invoiceDateStr=" + invoiceDateStr + ", tallyCompanyName=" + tallyCompanyName + ", createdBy="
				+ createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", createdOnStr=" + createdOnStr
				+ ", lastUpdatedOnStr=" + lastUpdatedOnStr + ", vendorLocation=" + vendorLocation + ", discountGstType="
				+ discountGstType + ", balanceStock=" + balanceStock + ", updatedStock=" + updatedStock
				+ ", stockTypeId=" + stockTypeId + ", stockType=" + stockType + "]";
	}

	 
	

}

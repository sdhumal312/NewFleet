package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class PartInvoiceDto {
	
	private Long partInvoiceId;

	private Long partInvoiceNumber;

	private Integer	wareHouseLocation;

	private String	invoiceNumber;

	private Timestamp	invoiceDate;

	private String	invoiceAmount;

	private Integer	vendorId;

	private short	paymentTypeId;

	private String description;
	
	private String	vendorIdStr;

	private Double quantity;

	private Long createdById;

	private Long	lastModifiedById;

	private boolean	markForDelete;

	private Timestamp	createdOn;

	private Timestamp	lastModifiedBy;

	private Integer	companyId;
	
	private String		partLocation;
	
	private String		vendorName;
	
	private String		vendorLocation;
	
	private String invoiceDateOn;
	
	private Double	labourCharge;
	
	private Double TotalPartAmount;
	private Timestamp lastUpdated_Date;
	
	private boolean	anyPartNumberAsign;
	
	private String warehouseLocation;
	
	private String firstName;

	private Long partApprovalId;
	private String lastName;

	private Long purchaseorder_id;
	
	private String partInvoiceDateStr;
	
	private short typeOfPaymentId;
	
	private double paidAmount;
	
	private double balanceAmount;
	
	private String paymentType;
	
	private boolean part_document = false;
	
	private Long part_document_id;
	
	private Long tallyCompanyId;
	
	private String	tallyCompanyName;
	
	private Integer	subLocationId;
	
	private String	subLocation;
	
	private String[] inventory_id;
	
	private String[] partid;
	
	private String[] make;
	
	private String[] quantity_many;
	
	private String[] unitprice_many;
	
	private String[] discount_many;
	
	private String[] tax_many;
	
	private String[] finalDiscountTaxTypId;
	
	private String poNumber;
	
	private String approvalNumber;
	
	
	
	public Long getPartApprovalId() {
		return partApprovalId;
	}

	public void setPartApprovalId(Long partApprovalId) {
		this.partApprovalId = partApprovalId;
	}

	public boolean isPart_document() {
		return part_document;
	}

	public void setPart_document(boolean part_document) {
		this.part_document = part_document;
	}

	public Long getPart_document_id() {
		return part_document_id;
	}

	public void setPart_document_id(Long part_document_id) {
		this.part_document_id = part_document_id;
	}

	public Timestamp getVendorPaymentDate() {
		return vendorPaymentDate;
	}

	public void setVendorPaymentDate(Timestamp vendorPaymentDate) {
		this.vendorPaymentDate = vendorPaymentDate;
	}

	private Timestamp	vendorPaymentDate;
	
	
	private short vendorPaymentStatus;

	private String vendorPaymentStatusStr;
	
	public String getVendorPaymentStatusStr() {
		return vendorPaymentStatusStr;
	}

	public void setVendorPaymentStatusStr(String vendorPaymentStatusStr) {
		this.vendorPaymentStatusStr = vendorPaymentStatusStr;
	}

	private String paymentStatus;
	

	public String getInvoiceDateOn() {
		return invoiceDateOn;
	}

	public void setInvoiceDateOn(String invoiceDateOn) {
		this.invoiceDateOn = invoiceDateOn;
	}

	public String getPartLocation() {
		return partLocation;
	}

	public void setPartLocation(String partLocation) {
		this.partLocation = partLocation;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorLocation() {
		return vendorLocation;
	}

	public void setVendorLocation(String vendorLocation) {
		this.vendorLocation = vendorLocation;
	}

	public Long getPartInvoiceId() {
		return partInvoiceId;
	}

	public void setPartInvoiceId(Long partInvoiceId) {
		this.partInvoiceId = partInvoiceId;
	}

	public Long getPartInvoiceNumber() {
		return partInvoiceNumber;
	}

	public void setPartInvoiceNumber(Long partInvoiceNumber) {
		this.partInvoiceNumber = partInvoiceNumber;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
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

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
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
	
	public Timestamp getLastUpdated_Date() {
		return lastUpdated_Date;
	}

	public void setLastUpdated_Date(Timestamp lastUpdated_Date) {
		this.lastUpdated_Date = lastUpdated_Date;
	}

	public boolean isAnyPartNumberAsign() {
		return anyPartNumberAsign;
	}

	public void setAnyPartNumberAsign(boolean anyPartNumberAsign) {
		this.anyPartNumberAsign = anyPartNumberAsign;
	}

	public String getWarehouseLocation() {
		return warehouseLocation;
	}

	public void setWarehouseLocation(String warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}

	

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getTotalPartAmount() {
		return TotalPartAmount;
	}

	public void setTotalPartAmount(Double TotalPartAmount) {
		this.TotalPartAmount = Utility.round(TotalPartAmount, 2);
	}

	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}


	public String getPaymentStatus() {
		return paymentStatus;
	}
	
	
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Double getLabourCharge() {
		return labourCharge;
	}

	public void setLabourCharge(Double labourCharge) {
		this.labourCharge = Utility.round(labourCharge, 2);
	}

	public String getPartInvoiceDateStr() {
		return partInvoiceDateStr;
	}

	public void setPartInvoiceDateStr(String partInvoiceDateStr) {
		this.partInvoiceDateStr = partInvoiceDateStr;
	}

	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}

	@Override
	public String toString() {
		return "PartInvoiceDto [partInvoiceId=" + partInvoiceId + ", partInvoiceNumber=" + partInvoiceNumber
				+ ", wareHouseLocation=" + wareHouseLocation + ", invoiceNumber=" + invoiceNumber + ", invoiceDate="
				+ invoiceDate + ", invoiceAmount=" + invoiceAmount + ", vendorId=" + vendorId + ", paymentTypeId="
				+ paymentTypeId + ", description=" + description + ", quantity=" + quantity + ", createdById="
				+ createdById + ", lastModifiedById=" + lastModifiedById + ", markForDelete=" + markForDelete
				+ ", createdOn=" + createdOn + ", lastModifiedBy=" + lastModifiedBy + ", companyId=" + companyId
				+ ", partLocation=" + partLocation + ", vendorName=" + vendorName + ", vendorLocation=" + vendorLocation
				+ ", invoiceDateOn=" + invoiceDateOn + ", TotalPartAmount=" + TotalPartAmount + ", lastUpdated_Date="
				+ lastUpdated_Date + ", anyPartNumberAsign=" + anyPartNumberAsign + ", warehouseLocation="
				+ warehouseLocation + ", firstName=" + firstName + ", partApprovalId=" + partApprovalId + ", lastName="
				+ lastName + ", purchaseorder_id=" + purchaseorder_id + ", partInvoiceDateStr=" + partInvoiceDateStr
				+ ", vendorPaymentDate=" + vendorPaymentDate + ", vendorPaymentStatus=" + vendorPaymentStatus
				+ ", vendorPaymentStatusStr=" + vendorPaymentStatusStr + ", paymentStatus=" + paymentStatus + ", paymentType=" + paymentType + "]";
	}

	public short getTypeOfPaymentId() {
		return typeOfPaymentId;
	}

	public void setTypeOfPaymentId(short typeOfPaymentId) {
		this.typeOfPaymentId = typeOfPaymentId;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount =Utility.round(paidAmount, 2);
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2) ;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getVendorIdStr() {
		return vendorIdStr;
	}

	public void setVendorIdStr(String vendorIdStr) {
		this.vendorIdStr = vendorIdStr;
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

	public String[] getInventory_id() {
		return inventory_id;
	}

	public void setInventory_id(String[] inventory_id) {
		this.inventory_id = inventory_id;
	}

	public String[] getPartid() {
		return partid;
	}

	public void setPartid(String[] partid) {
		this.partid = partid;
	}

	public String[] getMake() {
		return make;
	}

	public void setMake(String[] make) {
		this.make = make;
	}

	public String[] getQuantity_many() {
		return quantity_many;
	}

	public void setQuantity_many(String[] quantity_many) {
		this.quantity_many = quantity_many;
	}

	public String[] getUnitprice_many() {
		return unitprice_many;
	}

	public void setUnitprice_many(String[] unitprice_many) {
		this.unitprice_many = unitprice_many;
	}

	public String[] getDiscount_many() {
		return discount_many;
	}

	public void setDiscount_many(String[] discount_many) {
		this.discount_many = discount_many;
	}

	public String[] getTax_many() {
		return tax_many;
	}

	public void setTax_many(String[] tax_many) {
		this.tax_many = tax_many;
	}

	public String[] getFinalDiscountTaxTypId() {
		return finalDiscountTaxTypId;
	}

	public void setFinalDiscountTaxTypId(String[] finalDiscountTaxTypId) {
		this.finalDiscountTaxTypId = finalDiscountTaxTypId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	
	
}

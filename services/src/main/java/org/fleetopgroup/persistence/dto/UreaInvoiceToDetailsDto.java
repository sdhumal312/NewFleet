package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class UreaInvoiceToDetailsDto {

	private Long	ureaInvoiceToDetailsId;
	
	private Long 	ureaInvoiceId;
	
	private Long	ureaInvoiceNumber;
	
	private Long	manufacturerId;
	
	private Double 	quantity;
	
	private Double 	usedQuantity;
	
	private Double	stockQuantity;
	
	private Double  unitprice;
	
	private Double  discount;
	
	private Double  tax;
	
	private Double  total;
	
	private Long 	purchaseorder_id;
	
	private Integer vendor_id;
	
	private Integer	wareHouseLocation;
	
	private Integer	companyId;
	
	private boolean	markForDelete;
	
	private Long createdById;
	
	private Long lastModifiedById;
	
	private Date created;
	
	private Date lastupdated;
	
	private String	vendorName;
	
	private String	locationName;
	
	private String	manufacturerName;
	
	private Integer	subLocationId;
	
	private String	subLocation;
	
	private short discountTaxTypeId;
	
	private Double 	transferQuantity;


	private Long 	ureaTransferDetailsId;

	private Long 	ureaTransferId;
	
	public Long getUreaInvoiceToDetailsId() {
		return ureaInvoiceToDetailsId;
	}

	public void setUreaInvoiceToDetailsId(Long ureaInvoiceToDetailsId) {
		this.ureaInvoiceToDetailsId = ureaInvoiceToDetailsId;
	}

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

	public Long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
	}

	public Double getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Double stockQuantity) {
		this.stockQuantity = Utility.round(stockQuantity, 2);
	}

	public Double getUsedQuantity() {
		return usedQuantity;
	}

	public void setUsedQuantity(Double usedQuantity) {
		this.usedQuantity = Utility.round(usedQuantity, 2);
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = Utility.round(unitprice, 2);
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = Utility.round(discount, 2);
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = Utility.round(tax, 2);
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = Utility.round(total, 2) ;
	}

	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}

	public Integer getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
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

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
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

	public short getDiscountTaxTypeId() {
		return discountTaxTypeId;
	}

	public void setDiscountTaxTypeId(short discountTaxTypeId) {
		this.discountTaxTypeId = discountTaxTypeId;
	}

	public Double getTransferQuantity() {
		return transferQuantity;
	}

	public void setTransferQuantity(Double transferQuantity) {
		this.transferQuantity = Utility.round(transferQuantity, 2);
	}

	public Long getUreaTransferDetailsId() {
		return ureaTransferDetailsId;
	}

	public void setUreaTransferDetailsId(Long ureaTransferDetailsId) {
		this.ureaTransferDetailsId = ureaTransferDetailsId;
	}

	public Long getUreaTransferId() {
		return ureaTransferId;
	}

	public void setUreaTransferId(Long ureaTransferId) {
		this.ureaTransferId = ureaTransferId;
	}
	
	@Override
	public String toString() {
		return "UreaInvoiceToDetailsDto [ureaInvoiceToDetailsId=" + ureaInvoiceToDetailsId + ", ureaInvoiceId="
				+ ureaInvoiceId + ", ureaInvoiceNumber=" + ureaInvoiceNumber + ", manufacturerId=" + manufacturerId
				+ ", quantity=" + quantity + ", usedQuantity=" + usedQuantity + ", stockQuantity=" + stockQuantity
				+ ", unitprice=" + unitprice + ", discount=" + discount + ", tax=" + tax + ", total=" + total
				+ ", purchaseorder_id=" + purchaseorder_id + ", vendor_id=" + vendor_id + ", wareHouseLocation="
				+ wareHouseLocation + ", companyId=" + companyId + ", markForDelete=" + markForDelete + ", createdById="
				+ createdById + ", lastModifiedById=" + lastModifiedById + ", created=" + created + ", lastupdated="
				+ lastupdated + ", vendorName=" + vendorName + ", locationName=" + locationName + ", manufacturerName="
				+ manufacturerName + ", subLocationId=" + subLocationId + ", subLocation=" + subLocation
				+ ", discountTaxTypeId=" + discountTaxTypeId + ", transferQuantity=" + transferQuantity
				+ ", ureaTransferDetailsId=" + ureaTransferDetailsId + ", ureaTransferId=" + ureaTransferId + "]";
	}

}

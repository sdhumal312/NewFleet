package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class ClothInventoryDetailsDto {

	private Long	clothInventoryDetailsId;
	
	private Long	clothInvoiceId;
	
	private Long	clothTypesId;
	
	private Double 	quantity;
	
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
	
	private String	clothTypesName;
	
	private Long 	clothInvoiceNumber;
	
	private String		invoiceNumber;
	
	private Timestamp	invoiceDate;
	
	private Double		invoiceAmount;
	
	private String		invDate;
	
	private short discountTaxTypeId;

	public Long getClothInventoryDetailsId() {
		return clothInventoryDetailsId;
	}

	public Long getClothInvoiceId() {
		return clothInvoiceId;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public Double getDiscount() {
		return discount;
	}

	public Double getTax() {
		return tax;
	}

	public Double getTotal() {
		return total;
	}

	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	public Integer getVendor_id() {
		return vendor_id;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public Date getCreated() {
		return created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setClothInventoryDetailsId(Long clothInventoryDetailsId) {
		this.clothInventoryDetailsId = clothInventoryDetailsId;
	}

	public void setClothInvoiceId(Long clothInvoiceId) {
		this.clothInvoiceId = clothInvoiceId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = Utility.round(unitprice, 2);
	}

	public void setDiscount(Double discount) {
		this.discount = Utility.round(discount, 2);
	}

	public void setTax(Double tax) {
		this.tax = Utility.round(tax, 2);
	}

	public void setTotal(Double total) {
		this.total = Utility.round(total, 2);
	}

	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	public String getClothTypesName() {
		return clothTypesName;
	}

	public void setClothTypesName(String clothTypesName) {
		this.clothTypesName = clothTypesName;
	}
	

	public Long getClothInvoiceNumber() {
		return clothInvoiceNumber;
	}

	public void setClothInvoiceNumber(Long clothInvoiceNumber) {
		this.clothInvoiceNumber = clothInvoiceNumber;
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
		this.invoiceAmount = Utility.round(invoiceAmount, 2);
	}

	public String getInvDate() {
		return invDate;
	}

	public void setInvDate(String invDate) {
		this.invDate = invDate;
	}

	public short getDiscountTaxTypeId() {
		return discountTaxTypeId;
	}

	public void setDiscountTaxTypeId(short discountTaxTypeId) {
		this.discountTaxTypeId = discountTaxTypeId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClothInventoryDetailsDto [clothInventoryDetailsId=");
		builder.append(clothInventoryDetailsId);
		builder.append(", clothInvoiceId=");
		builder.append(clothInvoiceId);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unitprice=");
		builder.append(unitprice);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", tax=");
		builder.append(tax);
		builder.append(", total=");
		builder.append(total);
		builder.append(", purchaseorder_id=");
		builder.append(purchaseorder_id);
		builder.append(", vendor_id=");
		builder.append(vendor_id);
		builder.append(", wareHouseLocation=");
		builder.append(wareHouseLocation);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", created=");
		builder.append(created);
		builder.append(", clothInvoiceNumber=");
		builder.append(clothInvoiceNumber);
		builder.append(", invoiceNumber=");
		builder.append(invoiceNumber);
		builder.append(", invoiceDate=");
		builder.append(invoiceDate); 
		builder.append(", invoiceAmount=");
		builder.append(invoiceAmount);
		builder.append(", invDate=");
		builder.append(invDate);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}

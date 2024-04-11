package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 *
 *
 */

public class InventoryDto {

	private Long inventory_id;
	
	private Long inventory_Number;

	private Long partid;

	private String partnumber;

	private String partname;

	private String parttype;

	private short partTypeId;
	
	private Long part_photoid;

	private String category;

	private String make;

	private String location;
	
	private Integer locationId;

	private Double quantity;

	private Double	labourCharge;
	
	private Double history_quantity;
	
	private Double mainUnitprice;
	
	private Double mainQuantity;

	private Double unitprice;

	private Long purchaseorder_Number;

	private Long purchaseorder_id;
	
	private String invoice_number;

	private String invoice_date;
	
	private Date invoiceDate;

	private String invoice_amount;

	private String unittype;

	private Double discount;

	private Double tax;

	private Double total;

	private Integer vendor_id;

	private String vendor_name;

	private String vendor_location;

	private String description;

	private Long inventory_location_id;

	private Long inventory_all_id;

	private String createdBy;

	private String lastModifiedBy;

	boolean markForDelete;

	private String created;

	private String lastupdated;
	
	private long vendorTypeId;
	
	private Long partInvoiceNumber;
	
	private Long  partInvoiceId;
	
	private short	paymentTypeId;
	
	private String PAYMENT_NUMBER;
	
	private short PAYMENT_TYPE_ID;

	private Date inv_date;
	
	private Long createdById;
	
	private String firstName;
	
	private String lastName;
	
	private String vendorIdStr;
	
	private Long tallyCompanyId;
	
	private String tallyCompanyName;
	
	private Integer	subLocationId;
	
	private String	subLocation;
	
	private short discountTaxTypeId;
	
	private boolean	isWarranty;
	
	private Integer serialNoAddedForParts;
	
	private Integer	convertTo;
	
	private String	convertToStr;
	
	private long	unitTypeId;
	
	private boolean assetIdRequired;
       
	private String aisle;
	
	private String bin;
	
	private String row;
	
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

	/**
	 * @return the inventory_id
	 */
	public Long getInventory_id() {
		return inventory_id;
	}

	/**
	 * @param inventory_id
	 *            the inventory_id to set
	 */
	public void setInventory_id(Long inventory_id) {
		this.inventory_id = inventory_id;
	}

	public Long getInventory_Number() {
		return inventory_Number;
	}

	public void setInventory_Number(Long inventory_Number) {
		this.inventory_Number = inventory_Number;
	}

	/**
	 * @return the partid
	 */
	public Long getPartid() {
		return partid;
	}

	/**
	 * @param partid
	 *            the partid to set
	 */
	public void setPartid(Long partid) {
		this.partid = partid;
	}

	/**
	 * @return the partnumber
	 */
	public String getPartnumber() {
		return partnumber;
	}

	/**
	 * @param partnumber
	 *            the partnumber to set
	 */
	public void setPartnumber(String partnumber) {
		this.partnumber = partnumber;
	}

	/**
	 * @return the partname
	 */
	public String getPartname() {
		return partname;
	}

	/**
	 * @param partname
	 *            the partname to set
	 */
	public void setPartname(String partname) {
		this.partname = partname;
	}

	public String getParttype() {
		return parttype;
	}

	public void setParttype(String parttype) {
		this.parttype = parttype;
	}

	public short getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(short partTypeId) {
		this.partTypeId = partTypeId;
	}

	/**
	 * @return the part_photoid
	 */
	public Long getPart_photoid() {
		return part_photoid;
	}

	/**
	 * @param part_photoid
	 *            the part_photoid to set
	 */
	public void setPart_photoid(Long part_photoid) {
		this.part_photoid = part_photoid;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make
	 *            the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the quantity
	 */
	public Double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
	}

	/**
	 * @return the unitprice
	 */
	public Double getUnitprice() {
		return unitprice;
	}

	/**
	 * @param unitprice
	 *            the unitprice to set
	 */
	public void setUnitprice(Double unitprice) {
		this.unitprice = Utility.round(unitprice, 2);
	}

	
	public Long getPurchaseorder_Number() {
		return purchaseorder_Number;
	}

	public void setPurchaseorder_Number(Long purchaseorder_Number) {
		this.purchaseorder_Number = purchaseorder_Number;
	}

	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}

	/**
	 * @return the invoice_number
	 */
	public String getInvoice_number() {
		return invoice_number;
	}

	/**
	 * @param invoice_number
	 *            the invoice_number to set
	 */
	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	/**
	 * @return the unittype
	 */
	public String getUnittype() {
		return unittype;
	}

	/**
	 * @param unittype
	 *            the unittype to set
	 */
	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}

	/**
	 * @return the vendor_id
	 */
	public Integer getVendor_id() {
		return vendor_id;
	}

	/**
	 * @param vendor_id
	 *            the vendor_id to set
	 */
	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	/**
	 * @return the vendor_name
	 */
	public String getVendor_name() {
		return vendor_name;
	}

	/**
	 * @param vendor_name
	 *            the vendor_name to set
	 */
	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	/**
	 * @return the vendor_location
	 */
	public String getVendor_location() {
		return vendor_location;
	}

	/**
	 * @param vendor_location
	 *            the vendor_location to set
	 */
	public void setVendor_location(String vendor_location) {
		this.vendor_location = vendor_location;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the inventory_location_id
	 */
	public Long getInventory_location_id() {
		return inventory_location_id;
	}

	/**
	 * @param inventory_location_id
	 *            the inventory_location_id to set
	 */
	public void setInventory_location_id(Long inventory_location_id) {
		this.inventory_location_id = inventory_location_id;
	}

	/**
	 * @return the inventory_all_id
	 */
	public Long getInventory_all_id() {
		return inventory_all_id;
	}

	/**
	 * @param inventory_all_id
	 *            the inventory_all_id to set
	 */
	public void setInventory_all_id(Long inventory_all_id) {
		this.inventory_all_id = inventory_all_id;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the history_quantity
	 */
	public Double getHistory_quantity() {
		return history_quantity;
	}

	/**
	 * @param history_quantity
	 *            the history_quantity to set
	 */
	public void setHistory_quantity(Double history_quantity) {
		this.history_quantity = Utility.round(history_quantity, 2);
	}

	/**
	 * @return the invoice_date
	 */
	public String getInvoice_date() {
		return invoice_date;
	}

	/**
	 * @param invoice_date
	 *            the invoice_date to set
	 */
	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	/**
	 * @return the invoice_amount
	 */
	public String getInvoice_amount() {
		return invoice_amount;
	}

	/**
	 * @param invoice_amount
	 *            the invoice_amount to set
	 */
	public void setInvoice_amount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public boolean isWarranty() {
		return isWarranty;
	}

	public void setWarranty(boolean isWarranty) {
		this.isWarranty = isWarranty;
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
		this.tax = Utility.round(tax, 2) ;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = Utility.round(total, 2);
	}

	/**
	 * @return the vendorTypeId
	 */
	public long getVendorTypeId() {
		return vendorTypeId;
	}

	/**
	 * @param vendorTypeId the vendorTypeId to set
	 */
	public void setVendorTypeId(long vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Date getInv_date() {
		return inv_date;
	}

	public void setInv_date(Date inv_date) {
		this.inv_date = inv_date;
	}
	

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
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

	public short getDiscountTaxTypeId() {
		return discountTaxTypeId;
	}

	public void setDiscountTaxTypeId(short discountTaxTypeId) {
		this.discountTaxTypeId = discountTaxTypeId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryDto [inventory_id=");
		builder.append(inventory_id);
		builder.append(", inventory_Number=");
		builder.append(inventory_Number);
		builder.append(", partid=");
		builder.append(partid);
		builder.append(", partnumber=");
		builder.append(partnumber);
		builder.append(", partname=");
		builder.append(partname);
		builder.append(", parttype=");
		builder.append(parttype);
		builder.append(", partTypeId=");
		builder.append(partTypeId);
		builder.append(", part_photoid=");
		builder.append(part_photoid);
		builder.append(", category=");
		builder.append(category);
		builder.append(", make=");
		builder.append(make);
		builder.append(", location=");
		builder.append(location);
		builder.append(", locationId=");
		builder.append(locationId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", history_quantity=");
		builder.append(history_quantity);
		builder.append(", unitprice=");
		builder.append(unitprice);
		builder.append(", purchaseorder_Number=");
		builder.append(purchaseorder_Number);
		builder.append(", purchaseorder_id=");
		builder.append(purchaseorder_id);
		builder.append(", invoice_number=");
		builder.append(invoice_number);
		builder.append(", invoice_date=");
		builder.append(invoice_date);
		builder.append(", invoice_amount=");
		builder.append(invoice_amount);
		builder.append(", unittype=");
		builder.append(unittype);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", tax=");
		builder.append(tax);
		builder.append(", total=");
		builder.append(total);
		builder.append(", vendor_id=");
		builder.append(vendor_id);
		builder.append(", vendor_name=");
		builder.append(vendor_name);
		builder.append(", vendor_location=");
		builder.append(vendor_location);
		builder.append(", description=");
		builder.append(description);
		builder.append(", inventory_location_id=");
		builder.append(inventory_location_id);
		builder.append(", inventory_all_id=");
		builder.append(inventory_all_id);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated="); 
		builder.append(lastupdated);
		builder.append(", partInvoiceId=");
		builder.append(partInvoiceId); 
		builder.append(", paymentTypeId=");
		builder.append(paymentTypeId);
		builder.append(", inv_date=");
		builder.append(inv_date);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append("]");
		return builder.toString();
	}

	public short getPAYMENT_TYPE_ID() {
		return PAYMENT_TYPE_ID;
	}

	public void setPAYMENT_TYPE_ID(short pAYMENT_TYPE_ID) {
		PAYMENT_TYPE_ID = pAYMENT_TYPE_ID;
	}

	public String getPAYMENT_NUMBER() {
		return PAYMENT_NUMBER;
	}

	public void setPAYMENT_NUMBER(String pAYMENT_NUMBER) {
		PAYMENT_NUMBER = pAYMENT_NUMBER;
	}

	public Double getLabourCharge() {
		return labourCharge;
	}

	public void setLabourCharge(Double labourCharge) {
		this.labourCharge = Utility.round(labourCharge, 2);
	}

	public String getVendorIdStr() {
		return vendorIdStr;
	}

	public void setVendorIdStr(String vendorIdStr) {
		this.vendorIdStr = vendorIdStr;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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

	public Integer getSerialNoAddedForParts() {
		return serialNoAddedForParts;
	}

	public void setSerialNoAddedForParts(Integer serialNoAddedForParts) {
		this.serialNoAddedForParts = serialNoAddedForParts;
	}

	public Integer getConvertTo() {
		return convertTo;
	}

	public void setConvertTo(Integer convertTo) {
		this.convertTo = convertTo;
	}

	public String getConvertToStr() {
		return convertToStr;
	}

	public void setConvertToStr(String convertToStr) {
		this.convertToStr = convertToStr;
	}

	public long getUnitTypeId() {
		return unitTypeId;
	}

	public void setUnitTypeId(long unitTypeId) {
		this.unitTypeId = unitTypeId;
	}

	public Double getMainUnitprice() {
		return mainUnitprice;
	}

	public void setMainUnitprice(Double mainUnitprice) {
		this.mainUnitprice = mainUnitprice;
	}

	public Double getMainQuantity() {
		return mainQuantity;
	}

	public void setMainQuantity(Double mainQuantity) {
		this.mainQuantity = mainQuantity;
	}

	public boolean isAssetIdRequired() {
		return assetIdRequired;
	}

	public void setAssetIdRequired(boolean assetIdRequired) {
		this.assetIdRequired = assetIdRequired;
	}

	/**
	 * @return the aisle
	 */
	public String getAisle() {
		return aisle;
	}

	/**
	 * @param aisle the aisle to set
	 */
	public void setAisle(String aisle) {
		this.aisle = aisle;
	}

	/**
	 * @return the bin
	 */
	public String getBin() {
		return bin;
	}

	/**
	 * @param bin the bin to set
	 */
	public void setBin(String bin) {
		this.bin = bin;
	}

	/**
	 * @return the row
	 */
	public String getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(String row) {
		this.row = row;
	}

}
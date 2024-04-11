package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class BatteryAmountDto {


	private Long batteryAmountId;
	
	private Long batteryManufacturerId;
	
	private Long batteryTypeId;
	
	private Long batteryQuantity;
	
	private Double unitCost;
	
	private Double discount;
	
	private Double tax;
	
	private Double totalAmount;
	
	private Integer wareHouseLocation;
	
	private Long	batteryInvoiceId;
	
	private Integer batteryAsignNumber;
	
	private Integer companyId;
	
	private boolean markForDelete;
	
	private String locationName;
	
	private String manufacturerName;
	
	private String batteryType;
	
	private String batteryCapacity;
	
	private Long   batteryCapacityId;
	
	private String partNumber;
	
	private Integer warrantyPeriod;
	
	private short	warrantyTypeId;
	
	private String warrantyType;
	
	private Long 		batteryInvoiceNumber;
	
	private String		invoiceNumber;
	
	private String		invoiceDate;
	
	private Double		invoiceAmount;
	
	private Integer		vendorId;
	
	private String 		vendorName;
	
	private short 		discountTaxTypeId;

	public Long getBatteryAmountId() {
		return batteryAmountId;
	}

	public Long getBatteryManufacturerId() {
		return batteryManufacturerId;
	}

	public Long getBatteryTypeId() {
		return batteryTypeId;
	}

	public Long getBatteryQuantity() {
		return batteryQuantity;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public Double getDiscount() {
		return discount;
	}

	public Double getTax() {
		return tax;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public Long getBatteryInvoiceId() {
		return batteryInvoiceId;
	}

	public Integer getBatteryAsignNumber() {
		return batteryAsignNumber;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setBatteryAmountId(Long batteryAmountId) {
		this.batteryAmountId = batteryAmountId;
	}

	public void setBatteryManufacturerId(Long batteryManufacturerId) {
		this.batteryManufacturerId = batteryManufacturerId;
	}

	public void setBatteryTypeId(Long batteryTypeId) {
		this.batteryTypeId = batteryTypeId;
	}

	public void setBatteryQuantity(Long batteryQuantity) {
		this.batteryQuantity = batteryQuantity;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = Utility.round(unitCost, 2);
	}

	public void setDiscount(Double discount) {
		this.discount = Utility.round(discount, 2);
	}

	public void setTax(Double tax) {
		this.tax = Utility.round(tax, 2);
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = Utility.round(totalAmount, 2);
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public void setBatteryInvoiceId(Long batteryInvoiceId) {
		this.batteryInvoiceId = batteryInvoiceId;
	}

	public void setBatteryAsignNumber(Integer batteryAsignNumber) {
		this.batteryAsignNumber = batteryAsignNumber;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public String getBatteryType() {
		return batteryType;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getBatteryCapacity() {
		return batteryCapacity;
	}

	public Long getBatteryCapacityId() {
		return batteryCapacityId;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public Integer getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public short getWarrantyTypeId() {
		return warrantyTypeId;
	}

	public String getWarrantyType() {
		return warrantyType;
	}

	public void setBatteryCapacity(String batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public void setBatteryCapacityId(Long batteryCapacityId) {
		this.batteryCapacityId = batteryCapacityId;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public void setWarrantyPeriod(Integer warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public void setWarrantyTypeId(short warrantyTypeId) {
		this.warrantyTypeId = warrantyTypeId;
	}

	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}

	public void setBatteryType(String batteryType) {
		this.batteryType = batteryType;
	}
	

	public Long getBatteryInvoiceNumber() {
		return batteryInvoiceNumber;
	}

	public void setBatteryInvoiceNumber(Long batteryInvoiceNumber) {
		this.batteryInvoiceNumber = batteryInvoiceNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
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

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public short getDiscountTaxTypeId() {
		return discountTaxTypeId;
	}

	public void setDiscountTaxTypeId(short discountTaxTypeId) {
		this.discountTaxTypeId = discountTaxTypeId;
	}

	@Override
	public String toString() {
		return "BatteryAmountDto [batteryAmountId=" + batteryAmountId + ", batteryManufacturerId="
				+ batteryManufacturerId + ", batteryTypeId=" + batteryTypeId + ", batteryQuantity=" + batteryQuantity
				+ ", unitCost=" + unitCost + ", discount=" + discount + ", tax=" + tax + ", totalAmount=" + totalAmount
				+ ", wareHouseLocation=" + wareHouseLocation + ", batteryInvoiceId=" + batteryInvoiceId
				+ ", batteryAsignNumber=" + batteryAsignNumber + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + ", locationName=" + locationName + ", manufacturerName=" + manufacturerName
				+ ", batteryType=" + batteryType + ", batteryCapacity=" + batteryCapacity + ", batteryCapacityId="
				+ batteryCapacityId + ", partNumber=" + partNumber + ", warrantyPeriod=" + warrantyPeriod
				+ ", warrantyTypeId=" + warrantyTypeId + ", warrantyType=" + warrantyType + ", batteryInvoiceNumber="
				+ batteryInvoiceNumber + ", invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate
				+ ", invoiceAmount=" + invoiceAmount + ", vendorId=" + vendorId + ", vendorName=" + vendorName + "]";
	}


}

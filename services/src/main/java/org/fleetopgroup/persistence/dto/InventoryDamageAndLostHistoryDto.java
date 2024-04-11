package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Column;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class InventoryDamageAndLostHistoryDto {
	
	
	private Long	InventoryDamageAndLostHistoryId;
	
	private Long	clothTypesId;

	private Integer	wareHouseLocation;

	private Double	damagedQuantity;
	
	private Long laundryInvoiceId;

	private Double	losedQuantity;

	private Integer vendor_id;

	private Long	receiveById;

	private Integer	companyId;

	private boolean	markForDelete;

	private Date	createdDate;
	
	private short	stockTypeId;
	
	private String	remark;
	
	private String	clothName;
	
	private String	locationName;
	
	private String	receiveByStr;
	
	private String	createdDateStr;
	
	private Long	lastModifiedById;
	
	private Date	lastModifiedOn;
	
	private Integer	vid;
	
	private Integer driverId;
	
	private String vehicle_registration;
	
	private String driver_firstname;
	
	private String partlocation_name;

	public Long getInventoryDamageAndLostHistoryId() {
		return InventoryDamageAndLostHistoryId;
	}

	public void setInventoryDamageAndLostHistoryId(Long inventoryDamageAndLostHistoryId) {
		InventoryDamageAndLostHistoryId = inventoryDamageAndLostHistoryId;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public Double getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(Double damagedQuantity) {
		this.damagedQuantity = Utility.round(damagedQuantity, 2);
	}

	public Long getLaundryInvoiceId() {
		return laundryInvoiceId;
	}

	public void setLaundryInvoiceId(Long laundryInvoiceId) {
		this.laundryInvoiceId = laundryInvoiceId;
	}

	public Double getLosedQuantity() {
		return losedQuantity;
	}

	public void setLosedQuantity(Double losedQuantity) {
		this.losedQuantity =Utility.round(losedQuantity, 2);
	}

	public Integer getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	public Long getReceiveById() {
		return receiveById;
	}

	public void setReceiveById(Long receiveById) {
		this.receiveById = receiveById;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	public short getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(short stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public String getClothName() {
		return clothName;
	}

	public void setClothName(String clothName) {
		this.clothName = clothName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getReceiveByStr() {
		return receiveByStr;
	}

	public void setReceiveByStr(String receiveByStr) {
		this.receiveByStr = receiveByStr;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}
	

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public Integer getVid() {
		return vid;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}
	
	public String getDriver_firstname() {
		return driver_firstname;
	}

	public void setDriver_firstname(String driver_firstname) {
		this.driver_firstname = driver_firstname;
	}
	
	public String getPartlocation_name() {
		return partlocation_name;
	}

	public void setPartlocation_name(String partlocation_name) {
		this.partlocation_name = partlocation_name;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryDamageAndLostHistoryDto [InventoryDamageAndLostHistoryId=");
		builder.append(InventoryDamageAndLostHistoryId);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", wareHouseLocation=");
		builder.append(wareHouseLocation);
		builder.append(", damagedQuantity=");
		builder.append(damagedQuantity);
		builder.append(", laundryInvoiceId=");
		builder.append(laundryInvoiceId);
		builder.append(", losedQuantity=");
		builder.append(losedQuantity);
		builder.append(", vendor_id=");
		builder.append(vendor_id);
		builder.append(", receiveById=");
		builder.append(receiveById);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", stockTypeId=");
		builder.append(stockTypeId);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", clothName=");
		builder.append(clothName);
		builder.append(", locationName=");
		builder.append(locationName);
		builder.append(", receiveByStr=");
		builder.append(receiveByStr);
		builder.append(", createdDateStr=");
		builder.append(createdDateStr);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", driverId=");
		builder.append(driverId);
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", driver_firstname=");
		builder.append(driver_firstname);
		builder.append(", partlocation_name=");
		builder.append(partlocation_name);
		builder.append("]");
		return builder.toString();
	}

	

}
package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class BatteryTypeDto {

	public static final short	WARRANTY_TYPE_MONTH	= 1;
	public static final short	WARRANTY_TYPE_YEAR	= 2;
	
	public static final String	WARRANTY_TYPE_MONTH_NAME	= "MONTH";
	public static final String	WARRANTY_TYPE_YEAR_NAME		= "YEAR";
	
	
	private Long 		batteryTypeId;
	
	private String		batteryType;
	
	private Long		batteryManufacturerId;
	
	private String		description;
	
	private Long		createdBy;
	
	private Long		lastModifiedBy;
	
	private Date		createdOn;
	
	private Date		lastModifiedOn;
	
	private String		manufacturerName;
	
	private String		partNumber;
	
	private	Integer		warrantyPeriod;
	
	private short		warrantyTypeId;
	
	private String		warrantyType;
	
	private String		warrentyterm;
	
	private Long		vehicleCostFixingId;
	
	private Double		costPerDay;


	public String getWarrentyterm() {
		return warrentyterm;
	}

	public void setWarrentyterm(String warrentyterm) {
		this.warrentyterm = warrentyterm;
	}

	public Long getBatteryTypeId() {
		return batteryTypeId;
	}

	public String getBatteryType() {
		return batteryType;
	}

	public Long getBatteryManufacturerId() {
		return batteryManufacturerId;
	}

	public String getDescription() {
		return description;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setBatteryTypeId(Long batteryTypeId) {
		this.batteryTypeId = batteryTypeId;
	}

	public void setBatteryType(String batteryType) {
		this.batteryType = batteryType;
	}

	public void setBatteryManufacturerId(Long batteryManufacturerId) {
		this.batteryManufacturerId = batteryManufacturerId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	


	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
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

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public void setWarrantyPeriod(Integer warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public void setWarrantyTypeId(short warrantyTypeId) {
		this.warrantyTypeId = warrantyTypeId;
	}

	public String getWarrantyType() {
		return warrantyType;
	}

	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}

	public Long getVehicleCostFixingId() {
		return vehicleCostFixingId;
	}

	public void setVehicleCostFixingId(Long vehicleCostFixingId) {
		this.vehicleCostFixingId = vehicleCostFixingId;
	}

	public Double getCostPerDay() {
		return costPerDay;
	}

	public void setCostPerDay(Double costPerDay) {
		this.costPerDay = Utility.round(costPerDay, 2);
	}

	@Override
	public String toString() {
		return "BatteryType [batteryTypeId=" + batteryTypeId + ", batteryType=" + batteryType
				+ ", batteryManufacturerId=" + batteryManufacturerId + ", description=" + description + ", createdBy="
				+ createdBy + ", lastModifiedBy=" + lastModifiedBy + ", createdOn=" + createdOn + ", lastModifiedOn="
				+ lastModifiedOn + "]";
	}
	
	
	public static  String  getWarrantyTypeName(short type) throws Exception {

		String statusString = null;
		switch (type) {
		case WARRANTY_TYPE_MONTH:
			statusString = WARRANTY_TYPE_MONTH_NAME;
			break;
		case WARRANTY_TYPE_YEAR:
			statusString = WARRANTY_TYPE_YEAR_NAME;
			break;
		
		default:
			statusString = "--";
			break;
		}
		return statusString;
	
	} 

}

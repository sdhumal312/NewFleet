package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class BatteryDto {

	private Long batteryId;
	
	private String batterySerialNumber;
	
	private Long batteryManufacturerId;
	
	private Long batteryTypeId;
	
	private Double batteryAmount;
	
	private Integer wareHouseLocationId;
	
	private Long  batteryAmountId;
	
	private Long  batteryInvoiceId;
	
	private Integer vid;
	
	private Integer batteryUsesOdometer;
	
	private Long   usesNoOfTime;
	
	private Integer openOdometer;
	
	private Integer closedOdometer;
	
	private Timestamp batteryPurchaseDate;
	
	private Timestamp batteryAsignedDate;
	
	private String	 purchaseDate;
	
	private String	 asignedDate;
	
	private short	  batteryStatusId;
	
	private short	 batteryUsesStatusId;
	
	private Timestamp	batteryScrapedDate;
	
	private String		scrapedDate;
	
	private Long 		battryScrapedById;
	
	private Long		createdById;
	
	private Long		lastModifiedById;
	
	private String		createdBy;
	
	private String		lastmodifiedBy;
	
	private Timestamp	createdOn;
	
	private Timestamp	lastModifiedOn;
	
	private String		creationDate;
	
	private String		lastModifiedDate;
	
	private Integer		companyId;
	
	private boolean		markForDelete;
	
	private String		manufacturerName;
	
	private String		batteryType;
	
	private String		locationName;
	
	private String		vehicle_registration;
	
	private String 		Vehicle_Location;
	
	private String      previousBatteryManufacturer;
	
	private String 		previousSerialNumber;
	
	private String 		previousAssignDate;
	
	private int 		previousUsesOdometer;
	
	private Integer 	subLocationId;
	
	private String 		subLocation;
	
	private short 		dismountBatteryStatusId;
	
	private String 		dismountBatteryStatus;
	
	
	public String getVehicle_Location() {
		return Vehicle_Location;
	}

	public void setVehicle_Location(String vehicle_Location) {
		Vehicle_Location = vehicle_Location;
	}

	private String		batteryStatus;
	
	private String batteryCapacity;
	
	private Long   batteryCapacityId;
	
	private String partNumber;
	
	private Integer warrantyPeriod;
	
	private short	warrantyTypeId;
	
	private String warrantyType;
	
	private String batteryUsesStatus;

	private String batteryScrapBy;
	
	private Long layoutId;
	
	private short layoutPosition;
	
	private Integer vehicle_Odometer;
	
	private Timestamp	batteryFirstAsignedDate;
	
	private String		availableWarrantyType;
	
	private String	warrantyStatus;
	
	private String	warrantyPeriodStr;
	
	private Double	costPerDay;
	
	private Double	costPerOdometer;
	
	public String getScrapreason() {
		return scrapreason;
	}

	public void setScrapreason(String scrapreason) {
		this.scrapreason = scrapreason;
	}

	private Short	warrantyStatusId;
	
	private String	scrapreason;
	
	

	public Double getCostPerDay() {
		return costPerDay;
	}

	public void setCostPerDay(Double costPerDay) {
		this.costPerDay = Utility.round( costPerDay, 2);
	}

	public Double getCostPerOdometer() {
		return costPerOdometer;
	}

	public void setCostPerOdometer(Double costPerOdometer) {
		this.costPerOdometer = Utility.round(costPerOdometer, 2);
	}

	public Long getBatteryId() {
		return batteryId;
	}

	public String getBatterySerialNumber() {
		return batterySerialNumber;
	}

	public Long getBatteryManufacturerId() {
		return batteryManufacturerId;
	}

	public Long getBatteryTypeId() {
		return batteryTypeId;
	}

	public Double getBatteryAmount() {
		return batteryAmount;
	}

	public Integer getWareHouseLocationId() {
		return wareHouseLocationId;
	}

	public Long getBatteryAmountId() {
		return batteryAmountId;
	}

	public Long getBatteryInvoiceId() {
		return batteryInvoiceId;
	}

	public Integer getVid() {
		return vid;
	}

	public Integer getBatteryUsesOdometer() {
		return batteryUsesOdometer;
	}

	public Long getUsesNoOfTime() {
		return usesNoOfTime;
	}

	public Integer getOpenOdometer() {
		return openOdometer;
	}

	public Integer getClosedOdometer() {
		return closedOdometer;
	}

	public Timestamp getBatteryPurchaseDate() {
		return batteryPurchaseDate;
	}

	public Timestamp getBatteryAsignedDate() {
		return batteryAsignedDate;
	}

	public short getBatteryStatusId() {
		return batteryStatusId;
	}

	public short getBatteryUsesStatusId() {
		return batteryUsesStatusId;
	}

	public Timestamp getBatteryScrapedDate() {
		return batteryScrapedDate;
	}

	public Long getBattryScrapedById() {
		return battryScrapedById;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setBatteryId(Long batteryId) {
		this.batteryId = batteryId;
	}

	public void setBatterySerialNumber(String batterySerialNumber) {
		this.batterySerialNumber = batterySerialNumber;
	}

	public String getWarrantyPeriodStr() {
		return warrantyPeriodStr;
	}

	public void setWarrantyPeriodStr(String warrantyPeriodStr) {
		this.warrantyPeriodStr = warrantyPeriodStr;
	}

	public void setBatteryManufacturerId(Long batteryManufacturerId) {
		this.batteryManufacturerId = batteryManufacturerId;
	}

	public void setBatteryTypeId(Long batteryTypeId) {
		this.batteryTypeId = batteryTypeId;
	}

	public String getAvailableWarrantyType() {
		return availableWarrantyType;
	}

	public void setAvailableWarrantyType(String availableWarrantyType) {
		this.availableWarrantyType = availableWarrantyType;
	}

	public void setBatteryAmount(Double batteryAmount) {
		this.batteryAmount = Utility.round( batteryAmount, 2);
	}

	public void setWareHouseLocationId(Integer wareHouseLocationId) {
		this.wareHouseLocationId = wareHouseLocationId;
	}

	public void setBatteryAmountId(Long batteryAmountId) {
		this.batteryAmountId = batteryAmountId;
	}

	public void setBatteryInvoiceId(Long batteryInvoiceId) {
		this.batteryInvoiceId = batteryInvoiceId;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setBatteryUsesOdometer(Integer batteryUsesOdometer) {
		this.batteryUsesOdometer = batteryUsesOdometer;
	}

	public void setUsesNoOfTime(Long usesNoOfTime) {
		this.usesNoOfTime = usesNoOfTime;
	}

	public void setOpenOdometer(Integer openOdometer) {
		this.openOdometer = openOdometer;
	}

	public void setClosedOdometer(Integer closedOdometer) {
		this.closedOdometer = closedOdometer;
	}

	public void setBatteryPurchaseDate(Timestamp batteryPurchaseDate) {
		this.batteryPurchaseDate = batteryPurchaseDate;
	}

	public void setBatteryAsignedDate(Timestamp batteryAsignedDate) {
		this.batteryAsignedDate = batteryAsignedDate;
	}

	public void setBatteryStatusId(short batteryStatusId) {
		this.batteryStatusId = batteryStatusId;
	}

	public void setBatteryUsesStatusId(short batteryUsesStatusId) {
		this.batteryUsesStatusId = batteryUsesStatusId;
	}

	public void setBatteryScrapedDate(Timestamp batteryScrapedDate) {
		this.batteryScrapedDate = batteryScrapedDate;
	}

	public void setBattryScrapedById(Long battryScrapedById) {
		this.battryScrapedById = battryScrapedById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Integer getVehicle_Odometer() {
		return vehicle_Odometer;
	}

	public void setVehicle_Odometer(Integer vehicle_Odometer) {
		this.vehicle_Odometer = vehicle_Odometer;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public String getManufacturerName() {
		return manufacturerName;
	}

	public String getBatteryType() {
		return batteryType;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public void setBatteryType(String batteryType) {
		this.batteryType = batteryType;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getBatteryStatus() {
		return batteryStatus;
	}

	public void setBatteryStatus(String batteryStatus) {
		this.batteryStatus = batteryStatus;
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

	public String getBatteryUsesStatus() {
		return batteryUsesStatus;
	}

	public void setBatteryUsesStatus(String batteryUsesStatus) {
		this.batteryUsesStatus = batteryUsesStatus;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public String getAsignedDate() {
		return asignedDate;
	}

	public String getScrapedDate() {
		return scrapedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getLastmodifiedBy() {
		return lastmodifiedBy;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setAsignedDate(String asignedDate) {
		this.asignedDate = asignedDate;
	}

	public void setScrapedDate(String scrapedDate) {
		this.scrapedDate = scrapedDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastmodifiedBy(String lastmodifiedBy) {
		this.lastmodifiedBy = lastmodifiedBy;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getBatteryScrapBy() {
		return batteryScrapBy;
	}

	public void setBatteryScrapBy(String batteryScrapBy) {
		this.batteryScrapBy = batteryScrapBy;
	}

	public Long getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Long layoutId) {
		this.layoutId = layoutId;
	}

	public short getLayoutPosition() {
		return layoutPosition;
	}

	public void setLayoutPosition(short layoutPosition) {
		this.layoutPosition = layoutPosition;
	}

	public Timestamp getBatteryFirstAsignedDate() {
		return batteryFirstAsignedDate;
	}

	public void setBatteryFirstAsignedDate(Timestamp batteryFirstAsignedDate) {
		this.batteryFirstAsignedDate = batteryFirstAsignedDate;
	}

	public String getWarrantyStatus() {
		return warrantyStatus;
	}

	public void setWarrantyStatus(String warrantyStatus) {
		this.warrantyStatus = warrantyStatus;
	}

	public Short getWarrantyStatusId() {
		return warrantyStatusId;
	}

	public void setWarrantyStatusId(Short warrantyStatusId) {
		this.warrantyStatusId = warrantyStatusId;
	}
	
	public String getPreviousBatteryManufacturer() {
		return previousBatteryManufacturer;
	}

	public void setPreviousBatteryManufacturer(String previousBatteryManufacturer) {
		this.previousBatteryManufacturer = previousBatteryManufacturer;
	}

	public String getPreviousSerialNumber() {
		return previousSerialNumber;
	}

	public void setPreviousSerialNumber(String previousSerialNumber) {
		this.previousSerialNumber = previousSerialNumber;
	}

	public String getPreviousAssignDate() {
		return previousAssignDate;
	}

	public void setPreviousAssignDate(String previousAssignDate) {
		this.previousAssignDate = previousAssignDate;
	}

	public int getPreviousUsesOdometer() {
		return previousUsesOdometer;
	}

	public void setPreviousUsesOdometer(int previousUsesOdometer) {
		this.previousUsesOdometer = previousUsesOdometer;
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
	
	public short getDismountBatteryStatusId() {
		return dismountBatteryStatusId;
	}

	public void setDismountBatteryStatusId(short dismountBatteryStatusId) {
		this.dismountBatteryStatusId = dismountBatteryStatusId;
	}

	public String getDismountBatteryStatus() {
		return dismountBatteryStatus;
	}

	public void setDismountBatteryStatus(String dismountBatteryStatus) {
		this.dismountBatteryStatus = dismountBatteryStatus;
	}

	@Override
	public String toString() {
		return "BatteryDto [batteryId=" + batteryId + ", batterySerialNumber=" + batterySerialNumber
				+ ", batteryManufacturerId=" + batteryManufacturerId + ", batteryTypeId=" + batteryTypeId
				+ ", batteryAmount=" + batteryAmount + ", wareHouseLocationId=" + wareHouseLocationId
				+ ", batteryAmountId=" + batteryAmountId + ", batteryInvoiceId=" + batteryInvoiceId + ", vid=" + vid
				+ ", batteryUsesOdometer=" + batteryUsesOdometer + ", usesNoOfTime=" + usesNoOfTime + ", openOdometer="
				+ openOdometer + ", closedOdometer=" + closedOdometer + ", batteryPurchaseDate=" + batteryPurchaseDate
				+ ", batteryAsignedDate=" + batteryAsignedDate + ", purchaseDate=" + purchaseDate + ", asignedDate="
				+ asignedDate + ", batteryStatusId=" + batteryStatusId + ", batteryUsesStatusId=" + batteryUsesStatusId
				+ ", batteryScrapedDate=" + batteryScrapedDate + ", scrapedDate=" + scrapedDate + ", battryScrapedById="
				+ battryScrapedById + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", createdBy=" + createdBy + ", lastmodifiedBy=" + lastmodifiedBy + ", createdOn=" + createdOn
				+ ", lastModifiedOn=" + lastModifiedOn + ", creationDate=" + creationDate + ", lastModifiedDate="
				+ lastModifiedDate + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", manufacturerName=" + manufacturerName + ", batteryType=" + batteryType + ", locationName="
				+ locationName + ", vehicle_registration=" + vehicle_registration + ", Vehicle_Location="
				+ Vehicle_Location + ", previousBatteryManufacturer=" + previousBatteryManufacturer
				+ ", previousSerialNumber=" + previousSerialNumber + ", previousAssignDate=" + previousAssignDate
				+ ", previousUsesOdometer=" + previousUsesOdometer + ", subLocationId=" + subLocationId
				+ ", subLocation=" + subLocation + ", dismountBatteryStatusId=" + dismountBatteryStatusId
				+ ", dismountBatteryStatus=" + dismountBatteryStatus + ", batteryStatus=" + batteryStatus
				+ ", batteryCapacity=" + batteryCapacity + ", batteryCapacityId=" + batteryCapacityId + ", partNumber="
				+ partNumber + ", warrantyPeriod=" + warrantyPeriod + ", warrantyTypeId=" + warrantyTypeId
				+ ", warrantyType=" + warrantyType + ", batteryUsesStatus=" + batteryUsesStatus + ", batteryScrapBy="
				+ batteryScrapBy + ", layoutId=" + layoutId + ", layoutPosition=" + layoutPosition
				+ ", vehicle_Odometer=" + vehicle_Odometer + ", batteryFirstAsignedDate=" + batteryFirstAsignedDate
				+ ", availableWarrantyType=" + availableWarrantyType + ", warrantyStatus=" + warrantyStatus
				+ ", warrantyPeriodStr=" + warrantyPeriodStr + ", costPerDay=" + costPerDay + ", costPerOdometer="
				+ costPerOdometer + ", warrantyStatusId=" + warrantyStatusId + ", scrapreason=" + scrapreason + "]";
	}

	

	

}

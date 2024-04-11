package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;


public class VehicleClothInventoryHistoryDto {
	
private Long vehicleClothInventoryHistoryId;
	
	private Integer	vid;
	
	private Long 	clothTypesId;
	
	private Double	quantity;
	
	private Long	createdById;
	
	private Date	createdOn;
	
	private Integer	companyId;
	
	private boolean	markForDelete;
	
	private short	asignType;
	
	private String	asignTypeStr;
	
	private Integer	locationId;
	
	private short	stockTypeId;
	
	private String createdByName;
	
	private String locationName;
	
	private String clothTypeName;
	
	private String stockTypeName;
	
	private String createdOnStr;
	
	private Double preAsignedQuantity;
	
	private Date PreAsignedDate;
	
	private String PreAsignedDateStr;
	
	private String vehicleRegistration;
	
	private long vehicleGroupId;
	
	private String vehicleGroup;
	
	private Double asignedQuantity;
	
	private Double removedQuantity;
	
	private Double newStockQuantity;
	
	private Double totalAssignQuantity;//VehicleClothInventoryDetails(Quantity)
	
	public Double getNewStockQuantity() {
		return newStockQuantity;
	}

	public void setNewStockQuantity(Double newStockQuantity) {
		this.newStockQuantity = Utility.round(newStockQuantity, 2);
	}

	public Double getOldStockQuantity() {
		return oldStockQuantity;
	}

	public void setOldStockQuantity(Double oldStockQuantity) {
		this.oldStockQuantity = Utility.round(oldStockQuantity, 2);
	}

	private Double oldStockQuantity;
	
	public Long getVehicleClothInventoryHistoryId() {
		return vehicleClothInventoryHistoryId;
	}

	public void setVehicleClothInventoryHistoryId(Long vehicleClothInventoryHistoryId) {
		this.vehicleClothInventoryHistoryId = vehicleClothInventoryHistoryId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity,2 );
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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

	public short getAsignType() {
		return asignType;
	}

	public void setAsignType(short asignType) {
		this.asignType = asignType;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public short getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(short stockTypeId) {
		this.stockTypeId = stockTypeId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getClothTypeName() {
		return clothTypeName;
	}

	public void setClothTypeName(String clothTypeName) {
		this.clothTypeName = clothTypeName;
	}

	public String getStockTypeName() {
		return stockTypeName;
	}

	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public String getAsignTypeStr() {
		return asignTypeStr;
	}

	public void setAsignTypeStr(String asignTypeStr) {
		this.asignTypeStr = asignTypeStr;
	}

	public Double getPreAsignedQuantity() {
		return preAsignedQuantity;
	}

	public void setPreAsignedQuantity(Double preAsignedQuantity) {
		this.preAsignedQuantity = Utility.round(preAsignedQuantity, 2);
	}

	public Date getPreAsignedDate() {
		return PreAsignedDate;
	}

	public void setPreAsignedDate(Date preAsignedDate) {
		PreAsignedDate = preAsignedDate;
	}

	public String getPreAsignedDateStr() {
		return PreAsignedDateStr;
	}

	public void setPreAsignedDateStr(String preAsignedDateStr) {
		PreAsignedDateStr = preAsignedDateStr;
	}
	
	public String getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(String vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}

	@Override
	public String toString() {
		return "VehicleClothInventoryHistoryDto [vehicleClothInventoryHistoryId=" + vehicleClothInventoryHistoryId
				+ ", vid=" + vid + ", clothTypesId=" + clothTypesId + ", quantity=" + quantity + ", createdById="
				+ createdById + ", createdOn=" + createdOn + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + ", asignType=" + asignType + ", asignTypeStr=" + asignTypeStr + ", locationId="
				+ locationId + ", stockTypeId=" + stockTypeId + ", createdByName=" + createdByName + ", locationName="
				+ locationName + ", clothTypeName=" + clothTypeName + ", stockTypeName=" + stockTypeName
				+ ", createdOnStr=" + createdOnStr + ", preAsignedQuantity=" + preAsignedQuantity + ", PreAsignedDate="
				+ PreAsignedDate + ", PreAsignedDateStr=" + PreAsignedDateStr + ", vehicleRegistration="
				+ vehicleRegistration + ", vehicleGroupId=" + vehicleGroupId + ", vehicleGroup=" + vehicleGroup
				+ ", asignedQuantity=" + asignedQuantity + ", removedQuantity=" + removedQuantity
				+ ", newStockQuantity=" + newStockQuantity + ", oldStockQuantity=" + oldStockQuantity + "]";
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public String getVehicleGroup() {
		return vehicleGroup;
	}

	public void setVehicleGroup(String vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
	}

	public Double getAsignedQuantity() {
		return asignedQuantity;
	}

	public void setAsignedQuantity(Double asignedQuantity) {
		this.asignedQuantity = Utility.round(asignedQuantity, 2);
	}

	public Double getRemovedQuantity() {
		return removedQuantity;
	}

	public void setRemovedQuantity(Double removedQuantity) {
		this.removedQuantity = Utility.round(removedQuantity, 2);
	}

	public Double getTotalAssignQuantity() {
		return totalAssignQuantity;
	}

	public void setTotalAssignQuantity(Double totalAssignQuantity) {
		this.totalAssignQuantity = Utility.round(totalAssignQuantity, 2);
	}

	



}
package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class VehicleClothInventoryDetailsDto {

	private Long	vehicleClothInventoryDetailsId;
	
	private Integer	vid;
	
	private Long 	clothTypesId;
	
	private Double	quantity;
	
	private Long	createdById;
	
	private Long	lastModifiedById;
	
	private Date	createdOn;
	
	private Date	lastModifiedOn;
	
	private Integer	companyId;
	
	private boolean	markForDelete;
	
	private String	vehicle_registration;
	
	private String	clothTypesName;
	
	private String	createdBy;
	
	private String	lastModifiedBy;
	
	private String	createdOnStr;
	
	private String	lastModifiedOnstr;
	
	private String	locationName;
	
	private Integer	locationId;
	
	private Double maxAllowedQuantity;
	

	public Double getMaxAllowedQuantity() {
		return maxAllowedQuantity;
	}

	public void setMaxAllowedQuantity(Double maxAllowedQuantity) {
		this.maxAllowedQuantity =Utility.round(maxAllowedQuantity, 2);
	}

	public Long getVehicleClothInventoryDetailsId() {
		return vehicleClothInventoryDetailsId;
	}

	public void setVehicleClothInventoryDetailsId(Long vehicleClothInventoryDetailsId) {
		this.vehicleClothInventoryDetailsId = vehicleClothInventoryDetailsId;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
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

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getClothTypesName() {
		return clothTypesName;
	}

	public void setClothTypesName(String clothTypesName) {
		this.clothTypesName = clothTypesName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public String getLastModifiedOnstr() {
		return lastModifiedOnstr;
	}

	public void setLastModifiedOnstr(String lastModifiedOnstr) {
		this.lastModifiedOnstr = lastModifiedOnstr;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleClothInventoryDetailsDto [vehicleClothInventoryDetailsId=");
		builder.append(vehicleClothInventoryDetailsId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", clothTypesName=");
		builder.append(clothTypesName);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", createdOnStr="); 
		builder.append(createdOnStr);
		builder.append(", maxAllowedQuantity="); 
		builder.append(maxAllowedQuantity);
		builder.append(", lastModifiedOnstr=");
		builder.append(lastModifiedOnstr);
		builder.append("]");
		return builder.toString();
	}
	
	
}

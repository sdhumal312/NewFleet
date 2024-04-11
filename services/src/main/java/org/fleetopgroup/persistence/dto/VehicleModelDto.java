/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

/**
 * @author fleetop
 *
 */
public class VehicleModelDto {

	private Long vehicleModelId;
	
	private String vehicleModelName;
	
	private String description;
	
	private Long createdById;

	private Long lastUpdatedById;
	
	private String createdBy;

	private String lastUpdatedBy;
	
	private Timestamp createdOn;

	private Timestamp lastUpdatedOn;
	
	private String creationDate;

	private String lastUpdatedDate;

	boolean markForDelete;
	
	private String vehicleManufacturer;
	
	private Long	vehicleManufacturerId;
	
	private Long	vehicleModelTyreLayoutId;

	public VehicleModelDto() {
		super();
		
	}

	public VehicleModelDto(Long vehicleModelId, String vehicleModelName, String description, Long createdById,
			Long lastUpdatedById, String createdBy, String lastUpdatedBy, Timestamp createdOn, Timestamp lastUpdatedOn,
			String creationDate, String lastUpdatedDate, boolean markForDelete) {
		super();
		this.vehicleModelId = vehicleModelId;
		this.vehicleModelName = vehicleModelName;
		this.description = description;
		this.createdById = createdById;
		this.lastUpdatedById = lastUpdatedById;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.createdOn = createdOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.creationDate = creationDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.markForDelete = markForDelete;
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public String getVehicleModelName() {
		return vehicleModelName;
	}

	public void setVehicleModelName(String vehicleModelName) {
		this.vehicleModelName = vehicleModelName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getVehicleManufacturer() {
		return vehicleManufacturer;
	}

	public void setVehicleManufacturer(String vehicleManufacturer) {
		this.vehicleManufacturer = vehicleManufacturer;
	}

	public Long getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(Long vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

	public Long getVehicleModelTyreLayoutId() {
		return vehicleModelTyreLayoutId;
	}

	public void setVehicleModelTyreLayoutId(Long vehicleModelTyreLayoutId) {
		this.vehicleModelTyreLayoutId = vehicleModelTyreLayoutId;
	}

	@Override
	public String toString() {
		return "VehicleModelDto [vehicleModelId=" + vehicleModelId + ", vehicleModelName=" + vehicleModelName
				+ ", description=" + description + ", createdById=" + createdById + ", lastUpdatedById="
				+ lastUpdatedById + ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", createdOn="
				+ createdOn + ", lastUpdatedOn=" + lastUpdatedOn + ", creationDate=" + creationDate
				+ ", lastUpdatedDate=" + lastUpdatedDate + ", markForDelete=" + markForDelete + ", vehicleManufacturer="
				+ vehicleManufacturer + ", vehicleManufacturerId=" + vehicleManufacturerId
				+ ", vehicleModelTyreLayoutId=" + vehicleModelTyreLayoutId + "]";
	}

	
	
	

	
}

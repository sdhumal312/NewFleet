/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

/**
 * @author fleetop
 *
 */
public class VehicleManufacturerDto {

	private Long vehicleManufacturerId;
	
	private String vehicleManufacturerName;
	
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

	public VehicleManufacturerDto() {
		super();
		
	}

	public VehicleManufacturerDto(Long vehicleManufacturerId, String vehicleManufacturerName, String description,
			Long createdById, Long lastUpdatedById, String createdBy, String lastUpdatedBy, Timestamp createdOn,
			Timestamp lastUpdatedOn, String creationDate, String lastUpdatedDate, boolean markForDelete) {
		super();
		this.vehicleManufacturerId = vehicleManufacturerId;
		this.vehicleManufacturerName = vehicleManufacturerName;
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

	public Long getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(Long vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

	public String getVehicleManufacturerName() {
		return vehicleManufacturerName;
	}

	public void setVehicleManufacturerName(String vehicleManufacturerName) {
		this.vehicleManufacturerName = vehicleManufacturerName;
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

	@Override
	public String toString() {
		return "VehicleManufacturerDto [vehicleManufacturerId=" + vehicleManufacturerId + ", vehicleManufacturerName="
				+ vehicleManufacturerName + ", description=" + description + ", createdById=" + createdById
				+ ", lastUpdatedById=" + lastUpdatedById + ", createdBy=" + createdBy + ", lastUpdatedBy="
				+ lastUpdatedBy + ", createdOn=" + createdOn + ", lastUpdatedOn=" + lastUpdatedOn + ", creationDate="
				+ creationDate + ", lastUpdatedDate=" + lastUpdatedDate + ", markForDelete=" + markForDelete + "]";
	}
	
	
	
}

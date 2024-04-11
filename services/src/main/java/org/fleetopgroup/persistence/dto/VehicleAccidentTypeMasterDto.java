package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class VehicleAccidentTypeMasterDto {

	private Long	VehicleAccidentTypeMasterId;
	
	private String	VehicleAccidentTypeMasterName;
	
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

	public Long getVehicleAccidentTypeMasterId() {
		return VehicleAccidentTypeMasterId;
	}

	public void setVehicleAccidentTypeMasterId(Long vehicleAccidentTypeMasterId) {
		VehicleAccidentTypeMasterId = vehicleAccidentTypeMasterId;
	}

	public String getVehicleAccidentTypeMasterName() {
		return VehicleAccidentTypeMasterName;
	}

	public void setVehicleAccidentTypeMasterName(String vehicleAccidentTypeMasterName) {
		VehicleAccidentTypeMasterName = vehicleAccidentTypeMasterName;
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
	

	
}

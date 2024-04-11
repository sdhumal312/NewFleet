package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class BatteryManufacturerDto {

	private Long		batteryManufacturerId;
	
	private String 		manufacturerName;
	
	private String		description;
	
	private String		createdBy;

	private String 		lastModifiedBy;

	
	private boolean 	markForDelete;

	private Date 		creationOn;

	private Date 		lastModifiedOn;
	

	public Long getBatteryManufacturerId() {
		return batteryManufacturerId;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public String getDescription() {
		return description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setBatteryManufacturerId(Long batteryManufacturerId) {
		this.batteryManufacturerId = batteryManufacturerId;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}


	@Override
	public String toString() {
		return "BatteryManufacturer [batteryManufacturerId=" + batteryManufacturerId + ", manufacturerName="
				+ manufacturerName + ", description=" + description + ", createdBy="
				+ createdBy + ", lastModifiedBy=" + lastModifiedBy + ", markForDelete=" + markForDelete
				+ ", creationOn=" + creationOn + ", lastModifiedOn=" + lastModifiedOn + "]";
	}

	

}

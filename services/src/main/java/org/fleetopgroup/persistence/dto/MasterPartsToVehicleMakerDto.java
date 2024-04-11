package org.fleetopgroup.persistence.dto;

public class MasterPartsToVehicleMakerDto {

	private Long	masterPartsToMakerId;
	
	private Long	partId;
	
	private Long vehicleManufacturerId;
	
	private String vehicleManufacturer;

	public Long getMasterPartsToMakerId() {
		return masterPartsToMakerId;
	}

	public void setMasterPartsToMakerId(Long masterPartsToMakerId) {
		this.masterPartsToMakerId = masterPartsToMakerId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public Long getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(Long vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

	public String getVehicleManufacturer() {
		return vehicleManufacturer;
	}

	public void setVehicleManufacturer(String vehicleManufacturer) {
		this.vehicleManufacturer = vehicleManufacturer;
	}
}

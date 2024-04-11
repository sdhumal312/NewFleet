package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class MasterPartsToVehicleModalDto {
	
	private Long	masterPartsToModalId;
	
	private Long	partId;
	
	private Long 	vehicleModelId;
	
	private String 	vehicleModel;
	
	private Integer	companyId;
	
	private boolean	markForDelete;
	
	private Long 	createdById;
	
	private Date	createdOn;

	public Long getMasterPartsToModalId() {
		return masterPartsToModalId;
	}

	public void setMasterPartsToModalId(Long masterPartsToModalId) {
		this.masterPartsToModalId = masterPartsToModalId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
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
}

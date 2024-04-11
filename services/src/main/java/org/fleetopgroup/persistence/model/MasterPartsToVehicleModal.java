package org.fleetopgroup.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MasterPartsToVehicleModal")
public class MasterPartsToVehicleModal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "masterPartsToModalId")
	private Long	masterPartsToModalId;
	
	@Column(name = "partId")
	private Long	partId;
	
	@Column(name = "vehicleModelId")
	private Long vehicleModelId;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;
	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;
	
	@Column(name = "createdOn", nullable = false, updatable = false)
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

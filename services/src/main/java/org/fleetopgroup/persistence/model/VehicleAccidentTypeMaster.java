package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleAccidentTypeMaster")
public class VehicleAccidentTypeMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleAccidentTypeMasterId")
	private Long vehicleAccidentTypeMasterId;

	@Column(name = "vehicleAccidentTypeMasterName")
	private String vehicleAccidentTypeMasterName;
	
	@Column(name = "description")
	private String	description;

	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedBy")
	private Long lastUpdatedBy;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public VehicleAccidentTypeMaster() {
		super();
		
	}

	public Long getVehicleAccidentTypeMasterId() {
		return vehicleAccidentTypeMasterId;
	}

	public void setVehicleAccidentTypeMasterId(Long vehicleAccidentTypeMasterId) {
		this.vehicleAccidentTypeMasterId = vehicleAccidentTypeMasterId;
	}

	public String getVehicleAccidentTypeMasterName() {
		return vehicleAccidentTypeMasterName;
	}

	public void setVehicleAccidentTypeMasterName(String vehicleAccidentTypeMasterName) {
		this.vehicleAccidentTypeMasterName = vehicleAccidentTypeMasterName;
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

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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


}
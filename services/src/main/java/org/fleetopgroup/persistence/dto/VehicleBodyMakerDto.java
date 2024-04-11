package org.fleetopgroup.persistence.dto;
import java.util.Date;

public class VehicleBodyMakerDto{
	
	private Integer vehicleBodyMakerId;

	private String vehicleBodyMakerName;
	
	private Integer companyId;

	private String createdBy;

	private Long createdById;
	
	private Date createdOn;
	
	private Date createdOnStr;
	
	private String lastModifiedBy;

	private Long lastModifiedById;
	
	private Date lastModifiedOn;
	
	private Date lastModifiedOnStr;
	
	public Integer getVehicleBodyMakerId() {
		return vehicleBodyMakerId;
	}

	public void setVehicleBodyMakerId(Integer vehicleBodyMakerId) {
		this.vehicleBodyMakerId = vehicleBodyMakerId;
	}

	public String getVehicleBodyMakerName() {
		return vehicleBodyMakerName;
	}

	public void setVehicleBodyMakerName(String vehicleBodyMakerName) {
		this.vehicleBodyMakerName = vehicleBodyMakerName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public Date getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(Date createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public Date getLastModifiedOnStr() {
		return lastModifiedOnStr;
	}

	public void setLastModifiedOnStr(Date lastModifiedOnStr) {
		this.lastModifiedOnStr = lastModifiedOnStr;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	boolean markForDelete;
	
	
}
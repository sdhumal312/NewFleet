package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class VehicleServiceProgramDto {

	private Long	vehicleServiceProgramId;
	
	private String	programName;
	
	private String	description;
	
	private Integer companyId;
	
	private boolean	isVendorProgram;
	
	private Long	createdById;
	
	private Long	lastModifiedById;
	
	private Date	createdOn;
	
	private Date	lastModifiedOn;
	
	private String createdOnStr;
	
	private String lastModifiedOnOnStr;
	
	private String createdBy;
	
	private String lastModifedBy;
	
	private Long vehicleTypeId;
	
	private Long serviceReminderCount;
	

	public Long getVehicleServiceProgramId() {
		return vehicleServiceProgramId;
	}

	public void setVehicleServiceProgramId(Long vehicleServiceProgramId) {
		this.vehicleServiceProgramId = vehicleServiceProgramId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isVendorProgram() {
		return isVendorProgram;
	}

	public void setVendorProgram(boolean isVendorProgram) {
		this.isVendorProgram = isVendorProgram;
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

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public String getLastModifiedOnOnStr() {
		return lastModifiedOnOnStr;
	}

	public void setLastModifiedOnOnStr(String lastModifiedOnOnStr) {
		this.lastModifiedOnOnStr = lastModifiedOnOnStr;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifedBy() {
		return lastModifedBy;
	}

	public void setLastModifedBy(String lastModifedBy) {
		this.lastModifedBy = lastModifedBy;
	}

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public Long getServiceReminderCount() {
		return serviceReminderCount;
	}

	public void setServiceReminderCount(Long serviceReminderCount) {
		this.serviceReminderCount = serviceReminderCount;
	}
	
}

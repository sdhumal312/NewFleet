package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class VehicleInspectionSheetDto {

	private Long		vehicleInspectionSheetId;
	
	private String		inspectionSheetName;
	
	private String		vehicleGroupId;
	
	private int			noOfVehicleAsigned;
	
	private Long		createdById;
	
	private Timestamp	createdOn;
	
	private boolean		markForDelete;
	
	private Long		lastModifiedById;
	
	private Timestamp	lastModifiedOn;
	
	private Integer		companyId;
	
	private String		vehicleGroup;
	
	private String		vehicleType;
	
	private Long		vehicleTypeId;
	
	private String createdByName;
	
	private String createdOnStr;
	
	private String assignByName;
	
	private String assignOnStr;
	
	private Integer	branchId;
	
	private String branchName;
	
	

	public String getAssignByName() {
		return assignByName;
	}

	public void setAssignByName(String assignByName) {
		this.assignByName = assignByName;
	}

	public String getAssignOnStr() {
		return assignOnStr;
	}

	public void setAssignOnStr(String assignOnStr) {
		this.assignOnStr = assignOnStr;
	}

	public Long getVehicleInspectionSheetId() {
		return vehicleInspectionSheetId;
	}

	public void setVehicleInspectionSheetId(Long vehicleInspectionSheetId) {
		this.vehicleInspectionSheetId = vehicleInspectionSheetId;
	}

	public String getInspectionSheetName() {
		return inspectionSheetName;
	}

	public void setInspectionSheetName(String inspectionSheetName) {
		this.inspectionSheetName = inspectionSheetName;
	}

	public String getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(String vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public int getNoOfVehicleAsigned() {
		return noOfVehicleAsigned;
	}

	public void setNoOfVehicleAsigned(int noOfVehicleAsigned) {
		this.noOfVehicleAsigned = noOfVehicleAsigned;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getVehicleGroup() {
		return vehicleGroup;
	}

	public void setVehicleGroup(String vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Override
	public String toString() {
		return "VehicleInspectionSheetDto [vehicleInspectionSheetId=" + vehicleInspectionSheetId
				+ ", inspectionSheetName=" + inspectionSheetName + ", vehicleGroupId=" + vehicleGroupId
				+ ", noOfVehicleAsigned=" + noOfVehicleAsigned + ", createdById=" + createdById + ", createdOn="
				+ createdOn + ", markForDelete=" + markForDelete + ", lastModifiedById=" + lastModifiedById
				+ ", lastModifiedOn=" + lastModifiedOn + ", companyId=" + companyId + ", vehicleGroup=" + vehicleGroup
				+ ", vehicleType=" + vehicleType + ", vehicleTypeId=" + vehicleTypeId + "]";
	}


	
	
}

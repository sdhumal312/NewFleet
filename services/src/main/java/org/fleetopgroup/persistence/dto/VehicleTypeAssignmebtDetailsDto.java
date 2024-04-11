package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class VehicleTypeAssignmebtDetailsDto {
	
	
	private Long		vehicleTypeAssignmentDetailsId;
	
	
	private Long		assignById;
	
	private String		assignByStr;
	
	private Timestamp	inspectionStartDate;
	
	private Timestamp	assignedOn;
	
	private String	assignedOnStr;
	
	private Long		inspectionSheetId;
	
	private Long		vehicleTypeId;
	
	private Integer		companyId;
	
	private Long		lastUpdatedBy;
	
	private Timestamp		lastUpdatedDate;
	
	private boolean		markForDelete;
	
	private Integer branchId ;
	
	private String BranchName;

	public Long getVehicleTypeAssignmentDetailsId() {
		return vehicleTypeAssignmentDetailsId;
	}

	public void setVehicleTypeAssignmentDetailsId(Long vehicleTypeAssignmentDetailsId) {
		this.vehicleTypeAssignmentDetailsId = vehicleTypeAssignmentDetailsId;
	}

	public Long getAssignById() {
		return assignById;
	}

	public void setAssignById(Long assignById) {
		this.assignById = assignById;
	}

	public String getAssignByStr() {
		return assignByStr;
	}

	public void setAssignByStr(String assignByStr) {
		this.assignByStr = assignByStr;
	}

	public Timestamp getInspectionStartDate() {
		return inspectionStartDate;
	}

	public void setInspectionStartDate(Timestamp inspectionStartDate) {
		this.inspectionStartDate = inspectionStartDate;
	}

	public Timestamp getAssignedOn() {
		return assignedOn;
	}

	public void setAssignedOn(Timestamp assignedOn) {
		this.assignedOn = assignedOn;
	}

	public String getAssignedOnStr() {
		return assignedOnStr;
	}

	public void setAssignedOnStr(String assignedOnStr) {
		this.assignedOnStr = assignedOnStr;
	}

	public Long getInspectionSheetId() {
		return inspectionSheetId;
	}

	public void setInspectionSheetId(Long inspectionSheetId) {
		this.inspectionSheetId = inspectionSheetId;
	}

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return BranchName;
	}

	public void setBranchName(String branchName) {
		BranchName = branchName;
	}

	@Override
	public String toString() {
		return "VehicleTypeAssignmebtDetailsDto [vehicleTypeAssignmentDetailsId=" + vehicleTypeAssignmentDetailsId
				+ ", assignById=" + assignById + ", assignByStr=" + assignByStr + ", inspectionStartDate="
				+ inspectionStartDate + ", assignedOn=" + assignedOn + ", assignedOnStr=" + assignedOnStr
				+ ", inspectionSheetId=" + inspectionSheetId + ", vehicleTypeId=" + vehicleTypeId + ", companyId="
				+ companyId + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", markForDelete=" + markForDelete + "]";
	}
	
	

}

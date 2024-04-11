package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class VehicleInspctionSheetAsingmentDto {

	private Long		vehicleInspctionSheetAsingmentId;
	
	private Integer		vid;
	
	private Timestamp	inspectionStartDate;
	
	private Long		inspectionSheetId;
	
	private	Timestamp	assignDate;
	
	private Long		assignById;
	
	private Integer		companyId;
	
	private String		inspectionStartDateStr;
	
	private String		assignDateStr;
	
	private String		inspectionSheetName;
	
	private String		assignByName;
	
	private String		vehicle_registration;
	
	private short		inspectionStatusId;
	
	private String		inspectionStatusName;
	
	private String		vehicleGroupName;
	
	private Long		completionDetailsId;
	
	private Long		inspectedById;
	
	private String		inspectedBy;
	
	private Double     penaltyAmount;
	
	private Long  vehicleTypeId;
	
	private Integer  branchId;
	
	

	public Long getVehicleInspctionSheetAsingmentId() {
		return vehicleInspctionSheetAsingmentId;
	}

	public void setVehicleInspctionSheetAsingmentId(Long vehicleInspctionSheetAsingmentId) {
		this.vehicleInspctionSheetAsingmentId = vehicleInspctionSheetAsingmentId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Timestamp getInspectionStartDate() {
		return inspectionStartDate;
	}

	public void setInspectionStartDate(Timestamp inspectionStartDate) {
		this.inspectionStartDate = inspectionStartDate;
	}

	public Long getInspectionSheetId() {
		return inspectionSheetId;
	}

	public void setInspectionSheetId(Long inspectionSheetId) {
		this.inspectionSheetId = inspectionSheetId;
	}

	public Timestamp getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Timestamp assignDate) {
		this.assignDate = assignDate;
	}

	public Long getAssignById() {
		return assignById;
	}

	public void setAssignById(Long assignById) {
		this.assignById = assignById;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getInspectionStartDateStr() {
		return inspectionStartDateStr;
	}

	public String getAssignDateStr() {
		return assignDateStr;
	}

	public void setInspectionStartDateStr(String inspectionStartDateStr) {
		this.inspectionStartDateStr = inspectionStartDateStr;
	}

	public void setAssignDateStr(String assignDateStr) {
		this.assignDateStr = assignDateStr;
	}

	public String getInspectionSheetName() {
		return inspectionSheetName;
	}

	public void setInspectionSheetName(String inspectionSheetName) {
		this.inspectionSheetName = inspectionSheetName;
	}

	public String getAssignByName() {
		return assignByName;
	}

	public void setAssignByName(String assignByName) {
		this.assignByName = assignByName;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public short getInspectionStatusId() {
		return inspectionStatusId;
	}

	public void setInspectionStatusId(short inspectionStatusId) {
		this.inspectionStatusId = inspectionStatusId;
	}

	public String getInspectionStatusName() {
		return inspectionStatusName;
	}

	public void setInspectionStatusName(String inspectionStatusName) {
		this.inspectionStatusName = inspectionStatusName;
	}

	public String getVehicleGroupName() {
		return vehicleGroupName;
	}

	public void setVehicleGroupName(String vehicleGroupName) {
		this.vehicleGroupName = vehicleGroupName;
	}

	public Long getCompletionDetailsId() {
		return completionDetailsId;
	}

	public void setCompletionDetailsId(Long completionDetailsId) {
		this.completionDetailsId = completionDetailsId;
	}
	public Long getInspectedById() {
		return inspectedById;
	}

	public void setInspectedById(Long inspectedById) {
		this.inspectedById = inspectedById;
	}

	public String getInspectedBy() {
		return inspectedBy;
	}

	public void setInspectedBy(String inspectedBy) {
		this.inspectedBy = inspectedBy;
	}

	public Double getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(Double penaltyAmount) {
		this.penaltyAmount = Utility.round(penaltyAmount, 2);
	}

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	@Override
	public String toString() {
		return "VehicleInspctionSheetAsingmentDto [vehicleInspctionSheetAsingmentId=" + vehicleInspctionSheetAsingmentId
				+ ", vid=" + vid + ", inspectionStartDate=" + inspectionStartDate + ", inspectionSheetId="
				+ inspectionSheetId + ", assignDate=" + assignDate + ", assignById=" + assignById + ", companyId="
				+ companyId + ", inspectionStartDateStr=" + inspectionStartDateStr + ", assignDateStr=" + assignDateStr
				+ ", inspectionSheetName=" + inspectionSheetName + ", assignByName=" + assignByName
				+ ", vehicle_registration=" + vehicle_registration + ", inspectionStatusId=" + inspectionStatusId
				+ ", inspectionStatusName=" + inspectionStatusName + "]";
	}

	
	
}

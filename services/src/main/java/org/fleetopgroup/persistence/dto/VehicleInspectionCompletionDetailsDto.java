package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class VehicleInspectionCompletionDetailsDto {

	private Long		completionDetailsId;
	
	private Integer		vid;
	
	private Long		inspectionSheetId;
	
	private Timestamp	completionDateTime;
	
	private Timestamp	inspectionDate;
	
	private Long		inspectedById;
	
	private Integer 	branchId;
	
	private Double		inspectionResult;
	
	private	short		inspectionStatusId;
	
	private Integer		companyId;
	
	private boolean		markForDelete;
	
	private String		vehicle_registration;
	
	private Long		vehicleInspctionSheetAsingmentId;
	
	private String		inspectionStatusName;
	
	private String	completionDateTimeStr;
	
	private String	inspectionDateStr;
	
	private String	inspectedBy;
	
	private Double	totalPenalty;

	public Long getCompletionDetailsId() {
		return completionDetailsId;
	}

	public Integer getVid() {
		return vid;
	}

	public Long getInspectionSheetId() {
		return inspectionSheetId;
	}

	public Timestamp getCompletionDateTime() {
		return completionDateTime;
	}

	public Timestamp getInspectionDate() {
		return inspectionDate;
	}

	public Long getInspectedById() {
		return inspectedById;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public Double getInspectionResult() {
		return inspectionResult;
	}

	public short getInspectionStatusId() {
		return inspectionStatusId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setCompletionDetailsId(Long completionDetailsId) {
		this.completionDetailsId = completionDetailsId;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setInspectionSheetId(Long inspectionSheetId) {
		this.inspectionSheetId = inspectionSheetId;
	}

	public void setCompletionDateTime(Timestamp completionDateTime) {
		this.completionDateTime = completionDateTime;
	}

	public void setInspectionDate(Timestamp inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public void setInspectedById(Long inspectedById) {
		this.inspectedById = inspectedById;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public void setInspectionResult(Double inspectionResult) {
		this.inspectionResult =Utility.round(inspectionResult, 2);
	}

	public void setInspectionStatusId(short inspectionStatusId) {
		this.inspectionStatusId = inspectionStatusId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public Long getVehicleInspctionSheetAsingmentId() {
		return vehicleInspctionSheetAsingmentId;
	}

	public void setVehicleInspctionSheetAsingmentId(Long vehicleInspctionSheetAsingmentId) {
		this.vehicleInspctionSheetAsingmentId = vehicleInspctionSheetAsingmentId;
	}

	public String getInspectionStatusName() {
		return inspectionStatusName;
	}

	public void setInspectionStatusName(String inspectionStatusName) {
		this.inspectionStatusName = inspectionStatusName;
	}

	public String getCompletionDateTimeStr() {
		return completionDateTimeStr;
	}

	public void setCompletionDateTimeStr(String completionDateTimeStr) {
		this.completionDateTimeStr = completionDateTimeStr;
	}

	public String getInspectionDateStr() {
		return inspectionDateStr;
	}

	public void setInspectionDateStr(String inspectionDateStr) {
		this.inspectionDateStr = inspectionDateStr;
	}

	public String getInspectedBy() {
		return inspectedBy;
	}

	public void setInspectedBy(String inspectedBy) {
		this.inspectedBy = inspectedBy;
	}

	public Double getTotalPenalty() {
		return totalPenalty;
	}

	public void setTotalPenalty(Double totalPenalty) {
		this.totalPenalty = Utility.round(totalPenalty, 2);
	}

	@Override
	public String toString() {
		return "VehicleInspectionCompletionDetailsDto [completionDetailsId=" + completionDetailsId + ", vid=" + vid
				+ ", inspectionSheetId=" + inspectionSheetId + ", completionDateTime=" + completionDateTime
				+ ", inspectionDate=" + inspectionDate + ", inspectedById=" + inspectedById + ", branchId=" + branchId
				+ ", inspectionResult=" + inspectionResult + ", inspectionStatusId=" + inspectionStatusId
				+ ", companyId=" + companyId + ", markForDelete=" + markForDelete + ", vehicle_registration="
				+ vehicle_registration + "]";
	}
	
	
}

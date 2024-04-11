package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;
import java.util.Date;


public class VehicleDailyInspectionDto {
	
	private Long	vehicleDailyInspectionId;
	
	private Long	inspectionSheetId;
	
	private Integer	vid;
	
	private Date	inspectionDate;
	
	private short	inspectionStatusId;
	
	private Integer	companyId;
	
	private boolean	markForDelete;
	
	private Long	vehicleInspctionSheetAsingmentId;
	
	private Timestamp	inspectionStartDate;
	
	private	Timestamp	assignDate;
	
	private Long		assignById;
	
	private String	inspectionStartDateStr;
	
	private String	assignDateStr;
	
	private String	inspectionSheetName;
	
	private String	assignByName;
	
	private String	vehicle_registration;
	
	private String	inspectionStatusName;
	
	private String	vehicleGroupName;
	
	private String	skipedRemark;
	
	private Long	completionDetailsId;
	

	public Long getVehicleDailyInspectionId() {
		return vehicleDailyInspectionId;
	}

	public void setVehicleDailyInspectionId(Long vehicleDailyInspectionId) {
		this.vehicleDailyInspectionId = vehicleDailyInspectionId;
	}

	public Long getInspectionSheetId() {
		return inspectionSheetId;
	}

	public void setInspectionSheetId(Long inspectionSheetId) {
		this.inspectionSheetId = inspectionSheetId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public short getInspectionStatusId() {
		return inspectionStatusId;
	}

	public void setInspectionStatusId(short inspectionStatusId) {
		this.inspectionStatusId = inspectionStatusId;
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

	public String getInspectionStartDateStr() {
		return inspectionStartDateStr;
	}

	public void setInspectionStartDateStr(String inspectionStartDateStr) {
		this.inspectionStartDateStr = inspectionStartDateStr;
	}

	public String getAssignDateStr() {
		return assignDateStr;
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

	public Long getVehicleInspctionSheetAsingmentId() {
		return vehicleInspctionSheetAsingmentId;
	}

	public void setVehicleInspctionSheetAsingmentId(Long vehicleInspctionSheetAsingmentId) {
		this.vehicleInspctionSheetAsingmentId = vehicleInspctionSheetAsingmentId;
	}

	public Timestamp getInspectionStartDate() {
		return inspectionStartDate;
	}

	public void setInspectionStartDate(Timestamp inspectionStartDate) {
		this.inspectionStartDate = inspectionStartDate;
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

	public String getSkipedRemark() {
		return skipedRemark;
	}

	public void setSkipedRemark(String skipedRemark) {
		this.skipedRemark = skipedRemark;
	}

	@Override
	public String toString() {
		return "VehicleDailyInspectionDto [vehicleDailyInspectionId=" + vehicleDailyInspectionId
				+ ", inspectionSheetId=" + inspectionSheetId + ", vid=" + vid + ", inspectionDate=" + inspectionDate
				+ ", inspectionStatusId=" + inspectionStatusId + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + ", vehicleInspctionSheetAsingmentId=" + vehicleInspctionSheetAsingmentId
				+ ", inspectionStartDate=" + inspectionStartDate + ", assignDate=" + assignDate + ", assignById="
				+ assignById + ", inspectionStartDateStr=" + inspectionStartDateStr + ", assignDateStr=" + assignDateStr
				+ ", inspectionSheetName=" + inspectionSheetName + ", assignByName=" + assignByName
				+ ", vehicle_registration=" + vehicle_registration + ", inspectionStatusName=" + inspectionStatusName
				+ ", vehicleGroupName=" + vehicleGroupName + ", completionDetailsId=" + completionDetailsId + "]";
	}
	
}
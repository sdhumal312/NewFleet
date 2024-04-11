package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

/*import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.fleetopgroup.persistence.model.VehicleInspectionSheetAssignment;*/

public class VehicleInspectionSheetAssignmentDto {
	
	
	private Long		vehicleInspctionSheetAsingmentId;
	
	private Integer		vid;
	
	private Timestamp	inspectionStartDate;
	
	private Long		inspectionSheetId;
	
	private	Timestamp	assignDate;
	
	private Long		assignById;
	
	private Integer		companyId;
	
	private boolean		markForDelete;
	
	private short	inspectionStatusId;
	
	private String vehicle_registration;
	
	
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


	public boolean isMarkForDelete() {
		return markForDelete;
	}



	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}



	public short getInspectionStatusId() {
		return inspectionStatusId;
	}



	public void setInspectionStatusId(short inspectionStatusId) {
		this.inspectionStatusId = inspectionStatusId;
	}

	@Override
	public String toString() {
		return "VehicleInspectionSheetAssignment [vehicleInspctionSheetAsingmentId=" + vehicleInspctionSheetAsingmentId
				+ ", vid=" + vid + ", inspectionStartDate=" + inspectionStartDate + ", inspectionSheetId="
				+ inspectionSheetId + ", assignDate=" + assignDate + ", assignById=" + assignById + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + ", inspectionStatusId=" + inspectionStatusId + ", vehicle_registration=" + vehicle_registration +"]";
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}
	
}

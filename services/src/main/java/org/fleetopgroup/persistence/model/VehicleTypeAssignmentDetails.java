package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VehicleTypeAssignmentDetails")
public class VehicleTypeAssignmentDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleTypeAssignmentDetailsId")
	private Long		vehicleTypeAssignmentDetailsId;
	
	
	@Column(name = "assignById")
	private Long		assignById;
	
	@Column(name = "inspectionStartDate")
	private Timestamp	inspectionStartDate;
	
	@Column(name = "assignedOn")
	private Timestamp	assignedOn;
	
	@Column(name = "inspectionSheetId")
	private Long		inspectionSheetId;
	
	@Column(name = "vehicleTypeId")
	private Long		vehicleTypeId;
	
	@Column(name = "branchId")
	private Integer		branchId;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "lastUpdatedBy")
	private Long		lastUpdatedBy;
	
	@Column(name = "lastUpdatedDate")
	private Timestamp		lastUpdatedDate;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;

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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	
	
	
}

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
@Table(name="VehicleInspectionSheetAssignment")
public class VehicleInspectionSheetAssignment		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleInspctionSheetAsingmentId")
	private Long		vehicleInspctionSheetAsingmentId;
	
	@Column(name = "vid")
	private Integer		vid;
	
	@Column(name = "inspectionStartDate")
	private Timestamp	inspectionStartDate;
	
	@Column(name = "inspectionSheetId")
	private Long		inspectionSheetId;
	
	@Column(name = "assignDate")
	private	Timestamp	assignDate;
	
	@Column(name = "assignById")
	private Long		assignById;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	@Column(name = "inspectionStatusId", nullable = false)
	private short	inspectionStatusId;
	
	
	@Column(name = "vehicleTypeId")
	private Long		vehicleTypeId;
	
	@Column(name = "branchId")
	private Integer		branchId;
	
	public VehicleInspectionSheetAssignment() {
		super();
	}
	
	

	public VehicleInspectionSheetAssignment(Long vehicleInspctionSheetAsingmentId, Integer vid,
			Timestamp inspectionStartDate, Long inspectionSheetId, Timestamp assignDate, Long assignById,
			Integer companyId) {
		super();
		this.vehicleInspctionSheetAsingmentId = vehicleInspctionSheetAsingmentId;
		this.vid = vid;
		this.inspectionStartDate = inspectionStartDate;
		this.inspectionSheetId = inspectionSheetId;
		this.assignDate = assignDate;
		this.assignById = assignById;
		this.companyId = companyId;
	}



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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignById == null) ? 0 : assignById.hashCode());
		result = prime * result + ((inspectionSheetId == null) ? 0 : inspectionSheetId.hashCode());
		result = prime * result + ((inspectionStartDate == null) ? 0 : inspectionStartDate.hashCode());
		result = prime * result
				+ ((vehicleInspctionSheetAsingmentId == null) ? 0 : vehicleInspctionSheetAsingmentId.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleInspectionSheetAssignment other = (VehicleInspectionSheetAssignment) obj;
		if (assignById == null) {
			if (other.assignById != null)
				return false;
		} else if (!assignById.equals(other.assignById))
			return false;
		if (inspectionSheetId == null) {
			if (other.inspectionSheetId != null)
				return false;
		} else if (!inspectionSheetId.equals(other.inspectionSheetId))
			return false;
		if (inspectionStartDate == null) {
			if (other.inspectionStartDate != null)
				return false;
		} else if (!inspectionStartDate.equals(other.inspectionStartDate))
			return false;
		if (vehicleInspctionSheetAsingmentId == null) {
			if (other.vehicleInspctionSheetAsingmentId != null)
				return false;
		} else if (!vehicleInspctionSheetAsingmentId.equals(other.vehicleInspctionSheetAsingmentId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
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
		return "VehicleInspectionSheetAssignment [vehicleInspctionSheetAsingmentId=" + vehicleInspctionSheetAsingmentId
				+ ", vid=" + vid + ", inspectionStartDate=" + inspectionStartDate + ", inspectionSheetId="
				+ inspectionSheetId + ", assignDate=" + assignDate + ", assignById=" + assignById + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + ", inspectionStatusId=" + inspectionStatusId + "]";
	}



}

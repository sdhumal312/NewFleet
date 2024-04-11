package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VehicleDailyInspection")
public class VehicleDailyInspection implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleDailyInspectionId")
	private Long	vehicleDailyInspectionId;
	
	@Column(name = "inspectionSheetId")
	private Long	inspectionSheetId;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "inspectionDate")
	private Date	inspectionDate;
	
	@Column(name = "inspectionStatusId", nullable = false)
	private short	inspectionStatusId;

	@Column(name = "branchId")
	private Integer	branchId;
	
	@Column(name = "vehicleTypeId")
	private Long	vehicleTypeId;
	
	@Column(name = "isSkiped" ,nullable = false)
	private boolean		isSkiped;
	
	@Column(name = "skipedRemark", length = 255)
	private String skipedRemark;
	
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	

	public VehicleDailyInspection() {
		super();
		
	}


	public VehicleDailyInspection(Long vehicleDailyInspectionId, Long inspectionSheetId, Integer vid,
			Date inspectionDate, short inspectionStatusId, Integer companyId, boolean markForDelete) {
		super();
		this.vehicleDailyInspectionId = vehicleDailyInspectionId;
		this.inspectionSheetId = inspectionSheetId;
		this.vid = vid;
		this.inspectionDate = inspectionDate;
		this.inspectionStatusId = inspectionStatusId;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inspectionSheetId == null) ? 0 : inspectionSheetId.hashCode());
		result = prime * result + ((vehicleDailyInspectionId == null) ? 0 : vehicleDailyInspectionId.hashCode());
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
		VehicleDailyInspection other = (VehicleDailyInspection) obj;
		if (inspectionSheetId == null) {
			if (other.inspectionSheetId != null)
				return false;
		} else if (!inspectionSheetId.equals(other.inspectionSheetId))
			return false;
		if (vehicleDailyInspectionId == null) {
			if (other.vehicleDailyInspectionId != null)
				return false;
		} else if (!vehicleDailyInspectionId.equals(other.vehicleDailyInspectionId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}


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


	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}


	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}


	public boolean isSkiped() {
		return isSkiped;
	}


	public void setSkiped(boolean isSkiped) {
		this.isSkiped = isSkiped;
	}


	public Integer getBranchId() {
		return branchId;
	}


	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}


	@Override
	public String toString() {
		return "VehicleDailyInspection [vehicleDailyInspectionId=" + vehicleDailyInspectionId + ", inspectionSheetId="
				+ inspectionSheetId + ", vid=" + vid + ", inspectionDate=" + inspectionDate + ", inspectionStatusId="
				+ inspectionStatusId + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
}
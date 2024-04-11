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
@Table(name="VehicleInspectionCompletionDetails")
public class VehicleInspectionCompletionDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "completionDetailsId")
	private Long		completionDetailsId;
	
	@Column(name = "vid")
	private Integer		vid;
	
	@Column(name = "inspectionSheetId")
	private Long		inspectionSheetId;
	
	@Column(name = "completionDateTime")
	private Timestamp	completionDateTime;
	
	@Column(name = "inspectionDate")
	private Timestamp	inspectionDate;
	
	@Column(name = "inspectedById")
	private Long		inspectedById;
	
	@Column(name = "branchId")
	private Integer 	branchId;
	
	@Column(name = "inspectionResult")
	private Double		inspectionResult;
	
	@Column(name = "inspectionStatusId")
	private	short		inspectionStatusId;
	
	@Column(name = "vehicleType")
	private Long		vehicleType;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	@Column(name = "totalPenalty")
	private Double	totalPenalty;
	
	@Column(name = "vehicleInspctionSheetAsingmentId")
	private Long	vehicleInspctionSheetAsingmentId;
	
	public VehicleInspectionCompletionDetails() {
		super();
	}

	public VehicleInspectionCompletionDetails(Long completionDetailsId, Integer vid, Long inspectionSheetId,
			Timestamp completionDateTime, Long inspectedById, Integer branchId, Double inspectionResult,
			Integer companyId, boolean markForDelete) {
		super();
		this.completionDetailsId = completionDetailsId;
		this.vid = vid;
		this.inspectionSheetId = inspectionSheetId;
		this.completionDateTime = completionDateTime;
		this.inspectedById = inspectedById;
		this.branchId = branchId;
		this.inspectionResult = inspectionResult;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

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

	public Long getInspectedById() {
		return inspectedById;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public Double getInspectionResult() {
		return inspectionResult;
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

	public void setInspectedById(Long inspectedById) {
		this.inspectedById = inspectedById;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public void setInspectionResult(Double inspectionResult) {
		this.inspectionResult = inspectionResult;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	public Timestamp getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Timestamp inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public short getInspectionStatusId() {
		return inspectionStatusId;
	}

	public void setInspectionStatusId(short inspectionStatusId) {
		this.inspectionStatusId = inspectionStatusId;
	}

	public Long getVehicleInspctionSheetAsingmentId() {
		return vehicleInspctionSheetAsingmentId;
	}

	public void setVehicleInspctionSheetAsingmentId(Long vehicleInspctionSheetAsingmentId) {
		this.vehicleInspctionSheetAsingmentId = vehicleInspctionSheetAsingmentId;
	}

	public Long getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Long vehicleType) {
		this.vehicleType = vehicleType;
	}



	public Double getTotalPenalty() {
		return totalPenalty;
	}

	public void setTotalPenalty(Double totalPenalty) {
		this.totalPenalty = totalPenalty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((completionDetailsId == null) ? 0 : completionDetailsId.hashCode());
		result = prime * result + ((inspectedById == null) ? 0 : inspectedById.hashCode());
		result = prime * result + ((inspectionSheetId == null) ? 0 : inspectionSheetId.hashCode());
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
		VehicleInspectionCompletionDetails other = (VehicleInspectionCompletionDetails) obj;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (completionDetailsId == null) {
			if (other.completionDetailsId != null)
				return false;
		} else if (!completionDetailsId.equals(other.completionDetailsId))
			return false;
		if (inspectedById == null) {
			if (other.inspectedById != null)
				return false;
		} else if (!inspectedById.equals(other.inspectedById))
			return false;
		if (inspectionSheetId == null) {
			if (other.inspectionSheetId != null)
				return false;
		} else if (!inspectionSheetId.equals(other.inspectionSheetId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleInspectionCompletionDetails [completionDetailsId=" + completionDetailsId + ", vid=" + vid
				+ ", inspectionSheetId=" + inspectionSheetId + ", completionDateTime=" + completionDateTime
				+ ", inspectionDate=" + inspectionDate + ", inspectedById=" + inspectedById + ", branchId=" + branchId
				+ ", inspectionResult=" + inspectionResult + ", inspectionStatusId=" + inspectionStatusId
				+ ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}


}

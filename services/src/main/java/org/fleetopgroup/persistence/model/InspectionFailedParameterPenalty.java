package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="InspectionFailedParameterPenalty")
public class InspectionFailedParameterPenalty implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parameterPenaltyId")
	private Long  parameterPenaltyId;
	
	@Column(name = "vehicleInspctionSheetAsingmentId")
	private Long		vehicleInspctionSheetAsingmentId;
	
	@Column(name = "vid")
	private Integer			vid;
	
	
	@Column(name = "completionToParameterId")
	private Long			completionToParameterId;
	
	@Column(name = "inspectionSheetId")
	private Long			inspectionSheetId;
	
	
	@Column(name = "penaltyAmount")
	private Double			penaltyAmount;
	
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	

	public Long getParameterPenaltyId() {
		return parameterPenaltyId;
	}

	public void setParameterPenaltyId(Long parameterPenaltyId) {
		this.parameterPenaltyId = parameterPenaltyId;
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

	public Long getCompletionToParameterId() {
		return completionToParameterId;
	}

	public void setCompletionToParameterId(Long completionToParameterId) {
		this.completionToParameterId = completionToParameterId;
	}

	public Long getInspectionSheetId() {
		return inspectionSheetId;
	}

	public void setInspectionSheetId(Long inspectionSheetId) {
		this.inspectionSheetId = inspectionSheetId;
	}

	public Double getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(Double penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	

}

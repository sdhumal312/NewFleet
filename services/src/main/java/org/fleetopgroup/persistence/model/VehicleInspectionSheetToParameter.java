package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VehicleInspectionSheetToParameter")
public class VehicleInspectionSheetToParameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inspectionSheetToParameterId")
	private Long	inspectionSheetToParameterId;
	
	@Column(name = "inspectionSheetId")
	private Long	inspectionSheetId;
	
	@Column(name = "inspectionParameterId")
	private Long	inspectionParameterId;
	
	@Column(name = "frequency")
	private int		frequency;
	
	@Column(name = "isMandatory")
	private boolean	isMandatory;
	
	@Column(name = "isPhotoRequired")
	private boolean	isPhotoRequired;
	
	@Column(name = "isTextRequired")
	private boolean isTextRequired;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	
	public VehicleInspectionSheetToParameter() {
		super();
	}
	
	public VehicleInspectionSheetToParameter(Long inspectionSheetToParameterId, Long inspectionSheetId,
			Long inspectionParameterId, int frequency, boolean isMandatory, boolean isPhotoRequired,
			boolean isTextRequired, boolean markForDelete) {
		super();
		this.inspectionSheetToParameterId = inspectionSheetToParameterId;
		this.inspectionSheetId = inspectionSheetId;
		this.inspectionParameterId = inspectionParameterId;
		this.frequency = frequency;
		this.isMandatory = isMandatory;
		this.isPhotoRequired = isPhotoRequired;
		this.isTextRequired = isTextRequired;
		this.markForDelete = markForDelete;
	}

	public Long getInspectionSheetToParameterId() {
		return inspectionSheetToParameterId;
	}

	public void setInspectionSheetToParameterId(Long inspectionSheetToParameterId) {
		this.inspectionSheetToParameterId = inspectionSheetToParameterId;
	}

	public Long getInspectionSheetId() {
		return inspectionSheetId;
	}

	public void setInspectionSheetId(Long inspectionSheetId) {
		this.inspectionSheetId = inspectionSheetId;
	}

	public Long getInspectionParameterId() {
		return inspectionParameterId;
	}

	public void setInspectionParameterId(Long inspectionParameterId) {
		this.inspectionParameterId = inspectionParameterId;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public boolean isPhotoRequired() {
		return isPhotoRequired;
	}

	public void setPhotoRequired(boolean isPhotoRequired) {
		this.isPhotoRequired = isPhotoRequired;
	}

	public boolean isTextRequired() {
		return isTextRequired;
	}

	public void setTextRequired(boolean isTextRequired) {
		this.isTextRequired = isTextRequired;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + frequency;
		result = prime * result + ((inspectionParameterId == null) ? 0 : inspectionParameterId.hashCode());
		result = prime * result + ((inspectionSheetId == null) ? 0 : inspectionSheetId.hashCode());
		result = prime * result
				+ ((inspectionSheetToParameterId == null) ? 0 : inspectionSheetToParameterId.hashCode());
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
		VehicleInspectionSheetToParameter other = (VehicleInspectionSheetToParameter) obj;
		if (frequency != other.frequency)
			return false;
		if (inspectionParameterId == null) {
			if (other.inspectionParameterId != null)
				return false;
		} else if (!inspectionParameterId.equals(other.inspectionParameterId))
			return false;
		if (inspectionSheetId == null) {
			if (other.inspectionSheetId != null)
				return false;
		} else if (!inspectionSheetId.equals(other.inspectionSheetId))
			return false;
		if (inspectionSheetToParameterId == null) {
			if (other.inspectionSheetToParameterId != null)
				return false;
		} else if (!inspectionSheetToParameterId.equals(other.inspectionSheetToParameterId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleInspectionSheetToParameter [inspectionSheetToParameterId=" + inspectionSheetToParameterId
				+ ", inspectionSheetId=" + inspectionSheetId + ", inspectionParameterId=" + inspectionParameterId
				+ ", frequency=" + frequency + ", isMandatory=" + isMandatory + ", isPhotoRequired=" + isPhotoRequired
				+ ", isTextRequired=" + isTextRequired + ", markForDelete=" + markForDelete + "]";
	}


}

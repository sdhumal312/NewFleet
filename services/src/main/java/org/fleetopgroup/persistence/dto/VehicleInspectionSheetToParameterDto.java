package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class VehicleInspectionSheetToParameterDto {

	private Long	inspectionSheetToParameterId;
	
	private Long	inspectionSheetId;
	
	private Long	inspectionParameterId;
	
	private int		frequency;
	
	private boolean	isMandatory;
	
	private boolean	isPhotoRequired;
	
	private boolean isTextRequired;
	
	private boolean	markForDelete;
	
	private Integer	companyId;
	
	private String	parameterName;
	
	private String	mandatoryText;
	
	private String	photoRequiredText;
	
	private String	textRequiredText;
	
	private Timestamp	inspectionStartDate;
	
	private Timestamp	inspectionAssignDate;
	
	private Integer		vid;
	
	private String		fromDate;
	
	private String		toDate;
	
	private long 	parameterPhotoId;
	
	private long 	gid;
	
	private String	vGroup;
	

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

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getMandatoryText() {
		return mandatoryText;
	}

	public void setMandatoryText(String mandatoryText) {
		this.mandatoryText = mandatoryText;
	}

	public String getPhotoRequiredText() {
		return photoRequiredText;
	}

	public void setPhotoRequiredText(String photoRequiredText) {
		this.photoRequiredText = photoRequiredText;
	}

	public String getTextRequiredText() {
		return textRequiredText;
	}

	public void setTextRequiredText(String textRequiredText) {
		this.textRequiredText = textRequiredText;
	}

	public Timestamp getInspectionStartDate() {
		return inspectionStartDate;
	}

	public void setInspectionStartDate(Timestamp inspectionStartDate) {
		this.inspectionStartDate = inspectionStartDate;
	}

	public Integer getVid() {
		return vid;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	

	public long getParameterPhotoId() {
		return parameterPhotoId;
	}

	public void setParameterPhotoId(long parameterPhotoId) {
		this.parameterPhotoId = parameterPhotoId;
	}

	@Override
	public String toString() {
		return "VehicleInspectionSheetToParameterDto [inspectionSheetToParameterId=" + inspectionSheetToParameterId
				+ ", inspectionSheetId=" + inspectionSheetId + ", inspectionParameterId=" + inspectionParameterId
				+ ", frequency=" + frequency + ", isMandatory=" + isMandatory + ", isPhotoRequired=" + isPhotoRequired
				+ ", isTextRequired=" + isTextRequired + ", markForDelete=" + markForDelete + ", companyId=" + companyId
				+ ", parameterName=" + parameterName + ", mandatoryText=" + mandatoryText + ", photoRequiredText="
				+ photoRequiredText + ", textRequiredText=" + textRequiredText + ", inspectionStartDate="
				+ inspectionStartDate + ", vid=" + vid + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", parameterPhotoId=" + parameterPhotoId + "]";
	}

	public String getvGroup() {
		return vGroup;
	}

	public void setvGroup(String vGroup) {
		this.vGroup = vGroup;
	}

	public long getGid() {
		return gid;
	}

	public void setGid(long gid) {
		this.gid = gid;
	}

	public Timestamp getInspectionAssignDate() {
		return inspectionAssignDate;
	}

	public void setInspectionAssignDate(Timestamp inspectionAssignDate) {
		this.inspectionAssignDate = inspectionAssignDate;
	}

	
	
}

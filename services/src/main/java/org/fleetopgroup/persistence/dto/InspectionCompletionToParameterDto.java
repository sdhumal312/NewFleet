package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class InspectionCompletionToParameterDto {

	private Long			completionToParameterId;
	
	private Long			completionDetailsId;
	
	private Long			inspectionSheetId;
	
	private Long			inspectionParameterId;
	
	private short			isInspectionSuccess;

	private boolean			isInspectionDone;
	
	private Timestamp		completionDateTime;
	
	private boolean			isDocumentUploaded;
	
	private Long			documentId;
	
	private String			description;
	
	private Integer			vid;
	
	private Integer			companyId;
	
	private Long			inspectedById;
	
	private boolean			markForDelete;
	
	private String			parameterName;
	
	private String			inspectionSucessStr;
	
	private String			fromDate;
	
	private String			toDate;
	
	private Timestamp		inspectionDate;
	
	private String			inspectionDateStr;
	
	private int				frequency;
	
	private String			inspectedBy;
	
	private long			vehicleGroupId;
	
	private String			documentUrl;
	
	private String vehicle_registration;
	
	private Double penaltyAmount;
	
	

	public Double getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(Double penaltyAmount) {
		this.penaltyAmount = Utility.round(penaltyAmount,2);
	}

	public Long getCompletionToParameterId() {
		return completionToParameterId;
	}

	public Long getCompletionDetailsId() {
		return completionDetailsId;
	}

	public Long getInspectionSheetId() {
		return inspectionSheetId;
	}

	public Long getInspectionParameterId() {
		return inspectionParameterId;
	}

	public short getIsInspectionSuccess() {
		return isInspectionSuccess;
	}

	public boolean isInspectionDone() {
		return isInspectionDone;
	}

	public Timestamp getCompletionDateTime() {
		return completionDateTime;
	}

	public boolean isDocumentUploaded() {
		return isDocumentUploaded;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public String getDescription() {
		return description;
	}

	public Integer getVid() {
		return vid;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public Long getInspectedById() {
		return inspectedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setCompletionToParameterId(Long completionToParameterId) {
		this.completionToParameterId = completionToParameterId;
	}

	public void setCompletionDetailsId(Long completionDetailsId) {
		this.completionDetailsId = completionDetailsId;
	}

	public void setInspectionSheetId(Long inspectionSheetId) {
		this.inspectionSheetId = inspectionSheetId;
	}

	public void setInspectionParameterId(Long inspectionParameterId) {
		this.inspectionParameterId = inspectionParameterId;
	}

	public void setIsInspectionSuccess(short isInspectionSuccess) {
		this.isInspectionSuccess = isInspectionSuccess;
	}

	public void setInspectionDone(boolean isInspectionDone) {
		this.isInspectionDone = isInspectionDone;
	}

	public void setCompletionDateTime(Timestamp completionDateTime) {
		this.completionDateTime = completionDateTime;
	}

	public void setDocumentUploaded(boolean isDocumentUploaded) {
		this.isDocumentUploaded = isDocumentUploaded;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setInspectedById(Long inspectedById) {
		this.inspectedById = inspectedById;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getInspectionSucessStr() {
		return inspectionSucessStr;
	}

	public void setInspectionSucessStr(String inspectionSucessStr) {
		this.inspectionSucessStr = inspectionSucessStr;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Timestamp getInspectionDate() {
		return inspectionDate;
	}

	public String getInspectionDateStr() {
		return inspectionDateStr;
	}

	public void setInspectionDate(Timestamp inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public void setInspectionDateStr(String inspectionDateStr) {
		this.inspectionDateStr = inspectionDateStr;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public String getInspectedBy() {
		return inspectedBy;
	}

	public void setInspectedBy(String inspectedBy) {
		this.inspectedBy = inspectedBy;
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	@Override
	public String toString() {
		return "InspectionCompletionToParameterDto [completionToParameterId=" + completionToParameterId
				+ ", completionDetailsId=" + completionDetailsId + ", inspectionSheetId=" + inspectionSheetId
				+ ", inspectionParameterId=" + inspectionParameterId + ", isInspectionSuccess=" + isInspectionSuccess
				+ ", isInspectionDone=" + isInspectionDone + ", completionDateTime=" + completionDateTime
				+ ", isDocumentUploaded=" + isDocumentUploaded + ", documentId=" + documentId + ", description="
				+ description + ", vid=" + vid + ", companyId=" + companyId + ", inspectedById=" + inspectedById
				+ ", markForDelete=" + markForDelete + ", parameterName=" + parameterName + ", inspectionSucessStr="
				+ inspectionSucessStr + ", fromDate=" + fromDate + ", toDate=" + toDate + ", inspectionDate="
				+ inspectionDate + ", inspectionDateStr=" + inspectionDateStr + ", frequency=" + frequency + "]";
	}

	
}

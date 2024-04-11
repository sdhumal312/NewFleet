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
@Table(name="InspectionCompletionToParameter")
public class InspectionCompletionToParameter  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "completionToParameterId")
	private Long			completionToParameterId;
	
	@Column(name = "completionDetailsId")
	private Long			completionDetailsId;
	
	@Column(name = "inspectionSheetId")
	private Long			inspectionSheetId;
	
	@Column(name = "inspectionParameterId")
	private Long			inspectionParameterId;
	
	@Column(name = "isInspectionSuccess")
	private short			isInspectionSuccess;

	@Column(name = "isInspectionDone")
	private boolean			isInspectionDone;
	
	@Column(name = "completionDateTime")
	private Timestamp		completionDateTime;
	
	@Column(name = "isDocumentUploaded")
	private boolean			isDocumentUploaded;
	
	@Column(name = "documentId")
	private Long			documentId;
	
	@Column(name = "description")
	private String			description;
	
	@Column(name = "vid")
	private Integer			vid;
	
	@Column(name = "companyId")
	private Integer			companyId;
	
	@Column(name = "inspectedById")
	private Long			inspectedById;
	
	@Column(name = "markForDelete")
	private boolean			markForDelete;
	
	@Column(name = "issueId")
	private Long 			issueId;
	
	public InspectionCompletionToParameter() {
		super();
	}

	public InspectionCompletionToParameter(Long completionToParameterId, Long completionDetailsId,
			Long inspectionSheetId, Long inspectionParameterId, short isInspectionSuccess, boolean isInspectionDone,
			Timestamp completionDateTime, boolean isDocumentUploaded, Long documentId, String description, Integer vid,
			Integer companyId, boolean markForDelete) {
		super();
		this.completionToParameterId = completionToParameterId;
		this.completionDetailsId = completionDetailsId;
		this.inspectionSheetId = inspectionSheetId;
		this.inspectionParameterId = inspectionParameterId;
		this.isInspectionSuccess = isInspectionSuccess;
		this.isInspectionDone = isInspectionDone;
		this.completionDateTime = completionDateTime;
		this.isDocumentUploaded = isDocumentUploaded;
		this.documentId = documentId;
		this.description = description;
		this.vid = vid;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
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

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getInspectedById() {
		return inspectedById;
	}

	public void setInspectedById(Long inspectedById) {
		this.inspectedById = inspectedById;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((completionDetailsId == null) ? 0 : completionDetailsId.hashCode());
		result = prime * result + ((completionToParameterId == null) ? 0 : completionToParameterId.hashCode());
		result = prime * result + ((inspectionParameterId == null) ? 0 : inspectionParameterId.hashCode());
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
		InspectionCompletionToParameter other = (InspectionCompletionToParameter) obj;
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
		if (completionToParameterId == null) {
			if (other.completionToParameterId != null)
				return false;
		} else if (!completionToParameterId.equals(other.completionToParameterId))
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
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InspectionCompletionToParameter [completionToParameterId=" + completionToParameterId
				+ ", completionDetailsId=" + completionDetailsId + ", inspectionSheetId=" + inspectionSheetId
				+ ", inspectionParameterId=" + inspectionParameterId + ", isInspectionSuccess=" + isInspectionSuccess
				+ ", isInspectionDone=" + isInspectionDone + ", completionDateTime=" + completionDateTime
				+ ", isDocumentUploaded=" + isDocumentUploaded + ", documentId=" + documentId + ", description="
				+ description + ", vid=" + vid + ", companyId=" + companyId + ", inspectedById=" + inspectedById
				+ ", markForDelete=" + markForDelete + "]";
	}

	
}

package org.fleetopgroup.persistence.document;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="WorkOTaskToPartDocument")
public class WorkOTaskToPartDocument {
	
	@Id
	private Long _id;

	private Long  workordertasktoPartid;
	
	private String documentFilename;

	private byte[] documentContent;

	private String documentContentType;
	
	private Integer companyId;

	private Long createdById;
	
	private Long lastModifiedById;
	
	private Date created;
	
	private Date lastupdated;

	private boolean markForDelete;
	
	private String description;



	public Long getWorkordertasktoPartid() {
		return workordertasktoPartid;
	}

	public void setWorkordertasktoPartid(Long workordertasktoPartid) {
		this.workordertasktoPartid = workordertasktoPartid;
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}



	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public Date getLastupdated() {
		return lastupdated;
	}


	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getDocumentFilename() {
		return documentFilename;
	}

	public void setDocumentFilename(String documentFilename) {
		this.documentFilename = documentFilename;
	}

	public byte[] getDocumentContent() {
		return documentContent;
	}

	public void setDocumentContent(byte[] documentContent) {
		this.documentContent = documentContent;
	}

	public String getDocumentContentType() {
		return documentContentType;
	}

	public void setDocumentContentType(String documentContentType) {
		this.documentContentType = documentContentType;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "WorkOTaskToPartDocument [_id=" + _id + ", workordertaskto_partid=" + workordertasktoPartid
				+ ", documentFilename=" + documentFilename + ", documentContent=" + Arrays.toString(documentContent)
				+ ", documentContentType=" + documentContentType + ", companyId=" + companyId + ", createdById="
				+ createdById + ", lastModifiedById=" + lastModifiedById + ", created=" + created + ", lastupdated="
				+ lastupdated + ", markForDelete=" + markForDelete + "]";
	}

}

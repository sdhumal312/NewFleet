package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

public class VehicleAccidentDocumentDto {

	private Long _id;
	private Long accidentId;
	private byte[] content;
	private String contentType;
	private String documentType;
	private String fileName;
	private Integer companyId;
	private Long createdById;
	private Date created;
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public Long getAccidentId() {
		return accidentId;
	}
	public void setAccidentId(Long accidentId) {
		this.accidentId = accidentId;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}

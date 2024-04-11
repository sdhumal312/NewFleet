package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "VehicleAccidentDocument")
public class VehicleAccidentDocument  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long _id;
	@Indexed
	private Long accidentId;
	private byte[] content;
	private String contentType;
	private Integer documentTypeId;
	private short documentStatusId;
	private String documentType;
	private String fileName;
	private Integer companyId;
	private Long createdById;
	private Date created;
	private boolean markForDelete;
	
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
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public Integer getDocumentTypeId() {
		return documentTypeId;
	}
	public void setDocumentTypeId(Integer documentTypeId) {
		this.documentTypeId = documentTypeId;
	}
	public short getDocumentStatusId() {
		return documentStatusId;
	}
	public void setDocumentStatusId(short documentStatusId) {
		this.documentStatusId = documentStatusId;
	}
	
	
	
}

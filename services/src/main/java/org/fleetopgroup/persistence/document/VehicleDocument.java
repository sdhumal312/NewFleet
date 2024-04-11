package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "VehicleDocument")
public class VehicleDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;

	
	private Long docTypeId;

	
	private String filename;

	  @Indexed(name = "vehid")
	  private Integer vehid;

	
	private byte[] content;

	
	private String contentType;
	
	@Indexed(name = "companyId")
	private Integer companyId;
	
	
	private Date created;
	
	
	private Long createdById;
	
	
	private boolean markForDelete;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Long getDocTypeId() {
		return docTypeId;
	}


	public void setDocTypeId(Long docTypeId) {
		this.docTypeId = docTypeId;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public Integer getVehid() {
		return vehid;
	}


	public void setVehid(Integer vehid) {
		this.vehid = vehid;
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


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public Long getCreatedById() {
		return createdById;
	}


	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	@Override
	public String toString() {
		return "VehicleDocument [id=" + id + ", docTypeId=" + docTypeId + ", filename=" + filename + ", vehid=" + vehid
				+ ", content=" + Arrays.toString(content) + ", contentType=" + contentType + ", companyId=" + companyId
				+ ", created=" + created + ", createdById=" + createdById + ", markForDelete=" + markForDelete + "]";
	}


}

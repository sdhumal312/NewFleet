package org.fleetopgroup.persistence.dto;

import java.util.Arrays;
import java.util.Date;

public class InspectionParameterDocumentDto {
	
	private Integer id;

	
	private String filename;

	  
	private byte[] content;

	
	private String contentType;
	
	
	private Date created;
	
	
	private Long createdById;
	
	
	private boolean markForDelete;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
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
		return "InspectionParameterDocument [id=" + id + ", filename=" + filename + ", content="
				+ Arrays.toString(content) + ", contentType=" + contentType + ", created=" + created + ", createdById="
				+ createdById + ", markForDelete=" + markForDelete + "]";
	}

}
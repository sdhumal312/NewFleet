package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class VehicleDocumentDto {
	
	private Integer id;

	private String name;
	
	private Long docTypeId;

	private String uploaddate;

	private String filename;

	private Integer vehid;

	private byte[] content;

	private String contentType;
	
	private Integer companyId;
	
	private Date created;
	
	private Long createdById;
	
	private String createdBy;
	
	
	private boolean markForDelete;



	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFilename() {
		return filename;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

/*	public String getUploaddate() {
		return uploaddate;
	}

	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}
	*/
	

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * @return the vehid
	 */
	public Integer getVehid() {
		return vehid;
	}

	/**
	 * @param vehid
	 *            the vehid to set
	 */
	public void setVehid(Integer vehid) {
		this.vehid = vehid;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the docTypeId
	 */
	public Long getDocTypeId() {
		return docTypeId;
	}

	/**
	 * @param docTypeId the docTypeId to set
	 */
	public void setDocTypeId(Long docTypeId) {
		this.docTypeId = docTypeId;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the uploaddate
	 */
	public String getUploaddate() {
		return uploaddate;
	}

	/**
	 * @param uploaddate the uploaddate to set
	 */
	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleDocument [docTypeId=");
		builder.append(docTypeId);
		/*builder.append(", uploaddate=");
		builder.append(uploaddate);*/
		builder.append(", filename=");
		builder.append(filename);
		builder.append(", vehid=");
		builder.append(vehid);
		builder.append(", content=");
		builder.append(content);
		builder.append(", contentType=");
		builder.append(contentType);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

}

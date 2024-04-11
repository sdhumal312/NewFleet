package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class VehiclePhotoDto {

	private Integer id;

	private String name;

	private String uploaddate;
	
	private Date uploaddateOn;
	
	private Long photoTypeId;

	private String filename;

	private Integer vehid;

	private byte[] content;

	private String contentType;

	private Integer companyId;
	
	private Long createdById;
	
	private boolean markForDelete;
	
	private String createdBy;

	
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

	
	public String getUploaddate() {
		return uploaddate;
	}

	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}

	public byte[] getContent() {
		return content;
	}

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
	 * @return the uploaddateOn
	 */
	public Date getUploaddateOn() {
		return uploaddateOn;
	}

	/**
	 * @param uploaddateOn the uploaddateOn to set
	 */
	public void setUploaddateOn(Date uploaddateOn) {
		this.uploaddateOn = uploaddateOn;
	}

	/**
	 * @return the photoTypeId
	 */
	public Long getPhotoTypeId() {
		return photoTypeId;
	}

	/**
	 * @param photoTypeId the photoTypeId to set
	 */
	public void setPhotoTypeId(Long photoTypeId) {
		this.photoTypeId = photoTypeId;
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
		builder.append("VehiclePhoto [photoTypeId=");
		builder.append(photoTypeId);
		builder.append(", uploaddateOn=");
		builder.append(uploaddateOn);
		/*builder.append(", revdate=");
		builder.append(revdate);*/
		builder.append(", filename=");
		builder.append(filename);
		builder.append(", vehid=");
		builder.append(vehid);
		builder.append(", content=");
		builder.append(content);
		builder.append(", contentType=");
		builder.append(contentType);
	/*	builder.append(", startTime=");
		builder.append(startTime);*/
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}


}

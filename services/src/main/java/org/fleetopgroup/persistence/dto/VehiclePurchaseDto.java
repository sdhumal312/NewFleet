package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class VehiclePurchaseDto {

	private Integer id;
	
	private String name;
	
	private Long vPurchaseTypeId;
	
	private String uploaddate;
	
	private Date uploaddateOn;
	
	private String filename;
	private Integer vehid;
	
	private byte[] content;
	
	private String contentType;

	private Integer companyId;
	
	private Long  createdById;
	
	private boolean markForDelete;

	private String createdBy;
	
	public Integer getId() {
		return id;
	}


	public String getFilename() {
		return filename;
	}

	public void setId(Integer id) {
		this.id = id;
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
	 * @return the vPurchaseTypeId
	 */
	public Long getvPurchaseTypeId() {
		return vPurchaseTypeId;
	}

	/**
	 * @param vPurchaseTypeId the vPurchaseTypeId to set
	 */
	public void setvPurchaseTypeId(Long vPurchaseTypeId) {
		this.vPurchaseTypeId = vPurchaseTypeId;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
		builder.append("VehiclePurchase [vPurchaseTypeId=");
		builder.append(vPurchaseTypeId);
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
		/*builder.append(", startTime=");
		builder.append(startTime);*/
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}


}

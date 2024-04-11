package org.fleetopgroup.persistence.dto;

import java.util.Date;

/**
 * @author fleetop
 *
 *
 *
 */

public class DriverDocumentDto {

	private Integer driver_documentid;

	private String driver_documentname;

	private String driver_docFrom;
	
	private Date driver_docFromDate;

	private String driver_docTo;

	private Date driver_docToDate;
	
	private Date uploaddate;
	
	private Date revdate;
	
	private String driver_uploaddate;

	private String driver_revdate;

	private String driver_filename;

	private Integer driver_id;

	private byte[] driver_content;

	private String driver_contentType;
	
	private String createdBy;
	
	private Long createdById;

	public Integer getDriver_documentid() {
		return driver_documentid;
	}

	public void setDriver_documentid(Integer driver_documentid) {
		this.driver_documentid = driver_documentid;
	}

	public String getDriver_documentname() {
		return driver_documentname;
	}

	public void setDriver_documentname(String driver_documentname) {
		this.driver_documentname = driver_documentname;
	}

	public String getDriver_uploaddate() {
		return driver_uploaddate;
	}

	public void setDriver_uploaddate(String driver_uploaddate) {
		this.driver_uploaddate = driver_uploaddate;
	}

	public String getDriver_docFrom() {
		return driver_docFrom;
	}

	public void setDriver_docFrom(String driver_docFrom) {
		this.driver_docFrom = driver_docFrom;
	}

	public String getDriver_docTo() {
		return driver_docTo;
	}

	public void setDriver_docTo(String driver_docTo) {
		this.driver_docTo = driver_docTo;
	}

	public String getDriver_revdate() {
		return driver_revdate;
	}

	public void setDriver_revdate(String driver_revdate) {
		this.driver_revdate = driver_revdate;
	}

	public String getDriver_filename() {
		return driver_filename;
	}

	public void setDriver_filename(String driver_filename) {
		this.driver_filename = driver_filename;
	}

	public Integer getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	

	public byte[] getDriver_content() {
		return driver_content;
	}

	public void setDriver_content(byte[] driver_content) {
		this.driver_content = driver_content;
	}

	public String getDriver_contentType() {
		return driver_contentType;
	}

	public void setDriver_contentType(String driver_contentType) {
		this.driver_contentType = driver_contentType;
	}

	/**
	 * @return the driver_docFromDate
	 */
	public Date getDriver_docFromDate() {
		return driver_docFromDate;
	}

	/**
	 * @param driver_docFromDate the driver_docFromDate to set
	 */
	public void setDriver_docFromDate(Date driver_docFromDate) {
		this.driver_docFromDate = driver_docFromDate;
	}

	/**
	 * @return the driver_docToDate
	 */
	public Date getDriver_docToDate() {
		return driver_docToDate;
	}

	/**
	 * @param driver_docToDate the driver_docToDate to set
	 */
	public void setDriver_docToDate(Date driver_docToDate) {
		this.driver_docToDate = driver_docToDate;
	}

	/**
	 * @return the uploaddate
	 */
	public Date getUploaddate() {
		return uploaddate;
	}

	/**
	 * @param uploaddate the uploaddate to set
	 */
	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}

	/**
	 * @return the revdate
	 */
	public Date getRevdate() {
		return revdate;
	}

	/**
	 * @param revdate the revdate to set
	 */
	public void setRevdate(Date revdate) {
		this.revdate = revdate;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverDocumentDto [driver_documentid=");
		builder.append(driver_documentid);
		builder.append(", driver_documentname=");
		builder.append(driver_documentname);
		builder.append(", driver_docFrom=");
		builder.append(driver_docFrom);
		builder.append(", driver_docTo=");
		builder.append(driver_docTo);
		builder.append(", driver_uploaddate=");
		builder.append(driver_uploaddate);
		builder.append(", driver_revdate=");
		builder.append(driver_revdate);
		builder.append(", driver_filename=");
		builder.append(driver_filename);
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", driver_content=");
		builder.append(driver_content);
		builder.append(", driver_contentType=");
		builder.append(driver_contentType);
		builder.append("]");
		return builder.toString();
	}
	
	

}

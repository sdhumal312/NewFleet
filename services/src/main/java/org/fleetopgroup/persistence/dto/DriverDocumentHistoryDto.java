package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 *
 *
 */
import java.sql.Blob;
import java.util.Date;

public class DriverDocumentHistoryDto {

	private Integer driver_doHisid;

	private String driver_docHisname;

	private String driver_docHisFrom;

	private String driver_docHisTo;

	private String driver_uploaddate;

	private String driver_filename;

	private Integer driver_id;

	private Blob driver_content;

	private String driver_contentType;
	
	private Date driver_docHisFromDate;
	
	private Date driver_docHisToDate;
	
	private Date uploaddate;
	
	private String createdBy;
	
	private Long createdById;
	
	

	public Integer getDriver_doHisid() {
		return driver_doHisid;
	}

	public void setDriver_doHisid(Integer driver_doHisid) {
		this.driver_doHisid = driver_doHisid;
	}

	public String getDriver_docHisname() {
		return driver_docHisname;
	}

	public void setDriver_docHisname(String driver_docHisname) {
		this.driver_docHisname = driver_docHisname;
	}

	public String getDriver_docHisFrom() {
		return driver_docHisFrom;
	}

	public void setDriver_docHisFrom(String driver_docHisFrom) {
		this.driver_docHisFrom = driver_docHisFrom;
	}

	public String getDriver_docHisTo() {
		return driver_docHisTo;
	}

	public void setDriver_docHisTo(String driver_docHisTo) {
		this.driver_docHisTo = driver_docHisTo;
	}

	public String getDriver_uploaddate() {
		return driver_uploaddate;
	}

	public void setDriver_uploaddate(String driver_uploaddate) {
		this.driver_uploaddate = driver_uploaddate;
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

	public Blob getDriver_content() {
		return driver_content;
	}

	public void setDriver_content(Blob driver_content) {
		this.driver_content = driver_content;
	}

	public String getDriver_contentType() {
		return driver_contentType;
	}

	public void setDriver_contentType(String driver_contentType) {
		this.driver_contentType = driver_contentType;
	}

	/**
	 * @return the driver_docHisFromDate
	 */
	public Date getDriver_docHisFromDate() {
		return driver_docHisFromDate;
	}

	/**
	 * @param driver_docHisFromDate the driver_docHisFromDate to set
	 */
	public void setDriver_docHisFromDate(Date driver_docHisFromDate) {
		this.driver_docHisFromDate = driver_docHisFromDate;
	}

	/**
	 * @return the driver_docHisToDate
	 */
	public Date getDriver_docHisToDate() {
		return driver_docHisToDate;
	}

	/**
	 * @param driver_docHisToDate the driver_docHisToDate to set
	 */
	public void setDriver_docHisToDate(Date driver_docHisToDate) {
		this.driver_docHisToDate = driver_docHisToDate;
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
		builder.append("DriverDocumentHistoryDto [driver_doHisid=");
		builder.append(driver_doHisid);
		builder.append(", driver_docHisname=");
		builder.append(driver_docHisname);
		builder.append(", driver_docHisFrom=");
		builder.append(driver_docHisFrom);
		builder.append(", driver_docHisTo=");
		builder.append(driver_docHisTo);
		builder.append(", driver_uploaddate=");
		builder.append(driver_uploaddate);
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

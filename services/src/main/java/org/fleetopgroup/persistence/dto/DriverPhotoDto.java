package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 *
 *
 */
import java.sql.Blob;
import java.util.Date;

public class DriverPhotoDto {

	private Integer driver_photoid;

	private String driver_photoname;

	private String driver_uploaddate;
	
	private Date uploaddate;

	private String driver_filename;

	private Integer driver_id;

	private Blob driver_content;

	private String driver_contentType;

	public Integer getDriver_photoid() {
		return driver_photoid;
	}

	public void setDriver_photoid(Integer driver_photoid) {
		this.driver_photoid = driver_photoid;
	}

	public String getDriver_photoname() {
		return driver_photoname;
	}

	public void setDriver_photoname(String driver_photoname) {
		this.driver_photoname = driver_photoname;
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

	public void setDriver_content(Blob driver_content) {
		this.driver_content = driver_content;
	}

	public String getDriver_contentType() {
		return driver_contentType;
	}

	public void setDriver_contentType(String driver_contentType) {
		this.driver_contentType = driver_contentType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverPhotoDto [driver_photoid=");
		builder.append(driver_photoid);
		builder.append(", driver_photoname=");
		builder.append(driver_photoname);
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

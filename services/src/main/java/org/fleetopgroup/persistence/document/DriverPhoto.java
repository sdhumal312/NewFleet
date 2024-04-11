package org.fleetopgroup.persistence.document;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DriverPhoto")

public class DriverPhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Integer 	_id;
	private String 		driver_photoname;
	private Date 		uploaddate;
	private String 		driver_filename;
	private Integer 	driver_id;
	private byte[] 		driver_content;
	private String 		driver_contentType;
	private Integer		companyId;
	private Long 		createdById;
	private boolean 	markForDelete;
	private String 		driver_uploaddate;
	
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public String getDriver_photoname() {
		return driver_photoname;
	}
	public void setDriver_photoname(String driver_photoname) {
		this.driver_photoname = driver_photoname;
	}
	public Date getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
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
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	public String getDriver_uploaddate() {
		return driver_uploaddate;
	}
	public void setDriver_uploaddate(String driver_uploaddate) {
		this.driver_uploaddate = driver_uploaddate;
	}
}

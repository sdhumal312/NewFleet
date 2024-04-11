package org.fleetopgroup.persistence.document;

/**
 * @author Ashish Tiwari
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UserProfilePhoto")

public class UserProfilePhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long 	_id;
	private String 	photoname;
	private Date 	uploaddate;
	private String 	userprofile_filename;
	private long 	userprofile_id;
	private byte[] 	userprofile_content;
	private String 	userprofile_contentType;
	
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	public String getPhotoname() {
		return photoname;
	}
	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}
	public Date getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}
	public String getUserprofile_filename() {
		return userprofile_filename;
	}
	public void setUserprofile_filename(String userprofile_filename) {
		this.userprofile_filename = userprofile_filename;
	}
	public long getUserprofile_id() {
		return userprofile_id;
	}
	public void setUserprofile_id(long userprofile_id) {
		this.userprofile_id = userprofile_id;
	}
	public byte[] getUserprofile_content() {
		return userprofile_content;
	}
	public void setUserprofile_content(byte[] userprofile_content) {
		this.userprofile_content = userprofile_content;
	}
	public String getUserprofile_contentType() {
		return userprofile_contentType;
	}
	public void setUserprofile_contentType(String userprofile_contentType) {
		this.userprofile_contentType = userprofile_contentType;
	}
}
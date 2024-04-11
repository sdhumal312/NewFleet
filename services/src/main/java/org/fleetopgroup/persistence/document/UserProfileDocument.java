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

@Document(collection = "UserProfileDocument")

public class UserProfileDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long 	_id;
	private String 	documentname;
	private Date 	uploaddate;
	private String 	userprofile_filename;
	private Long 	userprofile_id;
	private byte[] 	userprofile_content;
	private String 	userprofile_contentType;
	private Integer companyId;
	private boolean markForDelete;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public String getDocumentname() {
		return documentname;
	}
	public void setDocumentname(String documentname) {
		this.documentname = documentname;
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
	public Long getUserprofile_id() {
		return userprofile_id;
	}
	public void setUserprofile_id(Long userprofile_id) {
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
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}
package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "userprofilephoto")
public class UserProfilePhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "photoid")
	private long photoid;

	@Column(name = "photoname", length = 25)
	private String photoname;

	@Column(name = "uploaddate")
	private Date uploaddate;

	@Column(name = "userprofile_filename")
	private String userprofile_filename;

	@Column(name = "userprofile_id")
	private long userprofile_id;

	@Lob
	@Column(name = "userprofile_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] userprofile_content;

	@Column(name = "userprofile_contenttype")
	private String userprofile_contentType;

	public UserProfilePhoto() {
		super();
	}

	public UserProfilePhoto(Long photoid, Long userprofile_id) {
		super();
		this.photoid = photoid;
		this.userprofile_id = userprofile_id;
	}

	public long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(Long photoid) {
		this.photoid = photoid;
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

	public Long getUserprofile_id() {
		return userprofile_id;
	}

	public void setUserprofile_id(Integer userprofile_id) {
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

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((photoname == null) ? 0 : photoname.hashCode());
		result = prime * result + (int) (userprofile_id ^ (userprofile_id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfilePhoto other = (UserProfilePhoto) obj;
		if (photoname == null) {
			if (other.photoname != null)
				return false;
		} else if (!photoname.equals(other.photoname))
			return false;
		if (userprofile_id != other.userprofile_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserProfilePhoto [photoid=");
		builder.append(photoid);
		builder.append(", photoname=");
		builder.append(photoname);
		builder.append(", uploaddate=");
		builder.append(uploaddate);
		builder.append(", userprofile_id=");
		builder.append(userprofile_id);
		builder.append("]");
		return builder.toString();
	}

	
}

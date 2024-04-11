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
@Table(name = "userprofiledocument")
public class UserProfileDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "documentid")
	private Long documentid;

	@Column(name = "documentname", length = 45)
	private String documentname;

	@Column(name = "uploaddate")
	private Date uploaddate;

	
	@Column(name = "userprofile_filename")
	private String userprofile_filename;

	@Column(name = "userprofile_id")
	private Long userprofile_id;

	@Lob
	@Column(name = "userprofile_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] userprofile_content;

	@Column(name = "userprofile_content_type")
	private String userprofile_contentType;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	
	public UserProfileDocument() {
		super();
	}


	public UserProfileDocument(Long documentid, String documentname, Long userprofile_id) {
		super();
		this.documentid = documentid;
		this.documentname = documentname;
		this.userprofile_id = userprofile_id;
	}


	public Long getDocumentid() {
		return documentid;
	}


	public void setDocumentid(Long documentid) {
		this.documentid = documentid;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userprofile_id == null) ? 0 : userprofile_id.hashCode());
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
		UserProfileDocument other = (UserProfileDocument) obj;
		if (userprofile_id == null) {
			if (other.userprofile_id != null)
				return false;
		} else if (!userprofile_id.equals(other.userprofile_id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserProfileDocument [documentid=");
		builder.append(documentid);
		builder.append(", documentname=");
		builder.append(documentname);
		builder.append(", uploaddate=");
		builder.append(uploaddate);
		builder.append(", userprofile_id=");
		builder.append(userprofile_id);
		builder.append("]");
		return builder.toString();
	}

	
	

}

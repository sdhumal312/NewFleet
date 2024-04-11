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
@Table(name = "branchphoto")
public class BranchPhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branch_photoid")
	private Integer branch_photoid;

	@Column(name = "branch_photoname", length = 25)
	private String branch_photoname;

	@Column(name = "branch_uploaddate")
	private Date branch_uploaddate;

	@Column(name = "branch_filename")
	private String branch_filename;

	@Column(name = "branch_id")
	private Integer branch_id;
	
	@Column(name = "companyId")
	private Integer companyId;


	@Lob
	@Column(name = "branch_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] branch_content;

	@Column(name = "branch_contenttype")
	private String branch_contentType;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public BranchPhoto() {
		super();
	}

	public BranchPhoto(Integer branch_photoid, String branch_photoname, Date branch_uploaddate) {
		super();
		this.branch_photoid = branch_photoid;
		this.branch_photoname = branch_photoname;
		this.branch_uploaddate = branch_uploaddate;
	}
	
	

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getBranch_photoid() {
		return branch_photoid;
	}

	public void setBranch_photoid(Integer branch_photoid) {
		this.branch_photoid = branch_photoid;
	}

	public String getBranch_photoname() {
		return branch_photoname;
	}

	public void setBranch_photoname(String branch_photoname) {
		this.branch_photoname = branch_photoname;
	}

	
	public Date getBranch_uploaddate() {
		return branch_uploaddate;
	}

	public void setBranch_uploaddate(Date branch_uploaddate) {
		this.branch_uploaddate = branch_uploaddate;
	}

	public String getBranch_filename() {
		return branch_filename;
	}

	public void setBranch_filename(String branch_filename) {
		this.branch_filename = branch_filename;
	}

	public Integer getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}

	public byte[] getBranch_content() {
		return branch_content;
	}

	public void setBranch_content(byte[] branch_content) {
		this.branch_content = branch_content;
	}

	public String getBranch_contentType() {
		return branch_contentType;
	}

	public void setBranch_contentType(String branch_contentType) {
		this.branch_contentType = branch_contentType;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branch_id == null) ? 0 : branch_id.hashCode());
		result = prime * result + ((branch_photoid == null) ? 0 : branch_photoid.hashCode());
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
		BranchPhoto other = (BranchPhoto) obj;
		if (branch_id == null) {
			if (other.branch_id != null)
				return false;
		} else if (!branch_id.equals(other.branch_id))
			return false;
		if (branch_photoid == null) {
			if (other.branch_photoid != null)
				return false;
		} else if (!branch_photoid.equals(other.branch_photoid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("branchPhoto [branch_photoid=");
		builder.append(branch_photoid);
		builder.append(", branch_photoname=");
		builder.append(branch_photoname);
		builder.append(", branch_uploaddate=");
		builder.append(branch_uploaddate);
		builder.append(", branch_filename=");
		builder.append(branch_filename);
		builder.append(", branch_id=");
		builder.append(branch_id);
		builder.append(", branch_content=");
		builder.append(branch_content);
		builder.append(", branch_contentType=");
		builder.append(branch_contentType);
		builder.append("]");
		return builder.toString();
	}

}

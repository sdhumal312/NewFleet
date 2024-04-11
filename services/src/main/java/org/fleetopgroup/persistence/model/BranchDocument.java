package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Arrays;
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
@Table(name = "branchdocument")
public class BranchDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branch_documentid")
	private Integer branch_documentid;

	@Column(name = "branch_documentname", length = 45)
	private String branch_documentname;

	@Column(name = "branch_docFrom")
	private Date branch_docFrom;

	@Column(name = "branch_docTo")
	private Date branch_docTo;

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

	@Column(name = "branch_content_type")
	private String branch_contentType;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Integer getbranch_documentid() {
		return branch_documentid;
	}

	public BranchDocument() {
		super();
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

	public Integer getBranch_documentid() {
		return branch_documentid;
	}

	public void setBranch_documentid(Integer branch_documentid) {
		this.branch_documentid = branch_documentid;
	}

	public String getBranch_documentname() {
		return branch_documentname;
	}

	public void setBranch_documentname(String branch_documentname) {
		this.branch_documentname = branch_documentname;
	}

	
	public Date getBranch_docFrom() {
		return branch_docFrom;
	}

	public void setBranch_docFrom(Date branch_docFrom) {
		this.branch_docFrom = branch_docFrom;
	}

	public Date getBranch_docTo() {
		return branch_docTo;
	}

	public void setBranch_docTo(Date branch_docTo) {
		this.branch_docTo = branch_docTo;
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
		result = prime * result + ((branch_documentid == null) ? 0 : branch_documentid.hashCode());
		result = prime * result + ((branch_id == null) ? 0 : branch_id.hashCode());
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
		BranchDocument other = (BranchDocument) obj;
		if (branch_documentid == null) {
			if (other.branch_documentid != null)
				return false;
		} else if (!branch_documentid.equals(other.branch_documentid))
			return false;
		if (branch_id == null) {
			if (other.branch_id != null)
				return false;
		} else if (!branch_id.equals(other.branch_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("BranchDocument [branch_documentid=");
		builder.append(branch_documentid);
		builder.append(", branch_documentname=");
		builder.append(branch_documentname);
		builder.append(", branch_docFrom=");
		builder.append(branch_docFrom);
		builder.append(", branch_docTo=");
		builder.append(branch_docTo);
		builder.append(", branch_uploaddate=");
		builder.append(branch_uploaddate);
		builder.append(", branch_filename=");
		builder.append(branch_filename);
		builder.append(", branch_id=");
		builder.append(branch_id);
		builder.append(", branch_content=");
		builder.append(branch_content != null
				? Arrays.toString(Arrays.copyOf(branch_content, Math.min(branch_content.length, maxLen))) : null);
		builder.append(", branch_contentType=");
		builder.append(branch_contentType);
		builder.append("]");
		return builder.toString();
	}

}

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

@Document(collection = "BranchPhoto")
public class BranchPhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer _id;
	private String branch_photoname;
	private Date branch_uploaddate;
	private String branch_filename;
	private Integer branch_id;
	private Integer companyId;
	private byte[] branch_content;
	private String branch_contentType;
	boolean markForDelete;

	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
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
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}

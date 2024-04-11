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

@Document(collection = "BranchDocument")
public class BranchDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer	_id;
	private String 	branch_documentname;
	private Date 	branch_docFrom;
	private Date 	branch_docTo;
	private Date 	branch_uploaddate;
	private String 	branch_filename;
	private Integer branch_id;
	private Integer companyId;
	private byte[] 	branch_content;
	private String 	branch_contentType;
	private boolean	markForDelete;
	
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
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

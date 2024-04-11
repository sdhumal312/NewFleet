package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class BranchDocumentDto  {
	
	
	private Integer branch_documentid;
    private String branch_documentname;
    private String branch_docFrom;
    private String branch_docTo;
    private Date branch_uploaddate;
    private String branch_revdate;

	private String branch_filename;

	private Integer branch_id;

	private byte[] branch_content;

	private String branch_contentType;

	public Integer getbranch_documentid() {
		return branch_documentid;
	}

	public BranchDocumentDto() {
		super();
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

	
	

	public String getBranch_docFrom() {
		return branch_docFrom;
	}

	public void setBranch_docFrom(String branch_docFrom) {
		this.branch_docFrom = branch_docFrom;
	}

	public String getBranch_docTo() {
		return branch_docTo;
	}

	public void setBranch_docTo(String branch_docTo) {
		this.branch_docTo = branch_docTo;
	}

	public Date getBranch_uploaddate() {
		return branch_uploaddate;
	}

	public void setBranch_uploaddate(Date branch_uploaddate) {
		this.branch_uploaddate = branch_uploaddate;
	}

	public String getBranch_revdate() {
		return branch_revdate;
	}

	public void setBranch_revdate(String branch_revdate) {
		this.branch_revdate = branch_revdate;
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

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("branchDocument [branch_documentid=");
		builder.append(branch_documentid);
		builder.append(", branch_documentname=");
		builder.append(branch_documentname);
		builder.append(", branch_docFrom=");
		builder.append(branch_docFrom);
		builder.append(", branch_docTo=");
		builder.append(branch_docTo);
		builder.append(", branch_uploaddate=");
		builder.append(branch_uploaddate);
		builder.append(", branch_revdate=");
		builder.append(branch_revdate);
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

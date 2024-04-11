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

@Document(collection = "IssuesPhoto")

public class IssuesPhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long _id;
	private String ISSUE_PHOTONAME;
	private Date ISSUE_UPLOADDATE;
	private String ISSUE_FILENAME;
	private Long ISSUE_ID;
	private byte[] ISSUE_CONTENT;
	private String ISSUE_CONTENTTYPE;
	private Integer COMPANY_ID;
	private Long CREATEDBYID;
	private boolean markForDelete;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public String getISSUE_PHOTONAME() {
		return ISSUE_PHOTONAME;
	}
	public void setISSUE_PHOTONAME(String iSSUE_PHOTONAME) {
		ISSUE_PHOTONAME = iSSUE_PHOTONAME;
	}
	public Date getISSUE_UPLOADDATE() {
		return ISSUE_UPLOADDATE;
	}
	public void setISSUE_UPLOADDATE(Date iSSUE_UPLOADDATE) {
		ISSUE_UPLOADDATE = iSSUE_UPLOADDATE;
	}
	public String getISSUE_FILENAME() {
		return ISSUE_FILENAME;
	}
	public void setISSUE_FILENAME(String iSSUE_FILENAME) {
		ISSUE_FILENAME = iSSUE_FILENAME;
	}
	public Long getISSUE_ID() {
		return ISSUE_ID;
	}
	public void setISSUE_ID(Long iSSUE_ID) {
		ISSUE_ID = iSSUE_ID;
	}
	public byte[] getISSUE_CONTENT() {
		return ISSUE_CONTENT;
	}
	public void setISSUE_CONTENT(byte[] iSSUE_CONTENT) {
		ISSUE_CONTENT = iSSUE_CONTENT;
	}
	public String getISSUE_CONTENTTYPE() {
		return ISSUE_CONTENTTYPE;
	}
	public void setISSUE_CONTENTTYPE(String iSSUE_CONTENTTYPE) {
		ISSUE_CONTENTTYPE = iSSUE_CONTENTTYPE;
	}
	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}
	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}
	public Long getCREATEDBYID() {
		return CREATEDBYID;
	}
	public void setCREATEDBYID(Long cREATEDBYID) {
		CREATEDBYID = cREATEDBYID;
	}
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}

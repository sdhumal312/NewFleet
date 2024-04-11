package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MailboxDocument")
public class MailboxDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long _id;

	@Indexed(name = "MAILBOX_ID")
	private long MAILBOX_ID;

	
	private byte[] MAIL_CONTENT;

	
	private String MAIL_CONTENT_TYPE;

	
	private String MAIL_FILENAME;

	
	private String CREATEDBY;

	
	private String LASTMODIFIEDBY;

	
	boolean markForDelete;

	
	private Date CREATED_DATE;

	
	private Date LASTUPDATED_DATE;


	public Long get_id() {
		return _id;
	}


	public void set_id(Long _id) {
		this._id = _id;
	}


	public long getMAILBOX_ID() {
		return MAILBOX_ID;
	}


	public void setMAILBOX_ID(long mAILBOX_ID) {
		MAILBOX_ID = mAILBOX_ID;
	}


	public byte[] getMAIL_CONTENT() {
		return MAIL_CONTENT;
	}


	public void setMAIL_CONTENT(byte[] mAIL_CONTENT) {
		MAIL_CONTENT = mAIL_CONTENT;
	}


	public String getMAIL_CONTENT_TYPE() {
		return MAIL_CONTENT_TYPE;
	}


	public void setMAIL_CONTENT_TYPE(String mAIL_CONTENT_TYPE) {
		MAIL_CONTENT_TYPE = mAIL_CONTENT_TYPE;
	}


	public String getMAIL_FILENAME() {
		return MAIL_FILENAME;
	}


	public void setMAIL_FILENAME(String mAIL_FILENAME) {
		MAIL_FILENAME = mAIL_FILENAME;
	}


	public String getCREATEDBY() {
		return CREATEDBY;
	}


	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}


	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}


	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}


	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}


	public Date getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}


	public void setLASTUPDATED_DATE(Date lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}
	
	

}

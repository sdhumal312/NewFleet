package org.fleetopgroup.persistence.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CashBookDocument")

public class CashBookDocument {

	@Id
	private Long 		_id;
	private long 		CASHID;
	private byte[] 		CASH_CONTENT;
	private String 		CASH_CONTENT_TYPE;
	private String 		CASH_FILENAME;
	
	@Indexed(name = "COMPANY_ID")
	private Integer 	COMPANY_ID;
	
	private String 		CREATEDBY;
	private String 		LASTMODIFIEDBY;
	private boolean 	markForDelete;
	private Date 		CREATED_DATE;
	private Date 		LASTUPDATED_DATE;

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public long getCASHID() {
		return CASHID;
	}

	public void setCASHID(long cASHID) {
		CASHID = cASHID;
	}

	public byte[] getCASH_CONTENT() {
		return CASH_CONTENT;
	}

	public void setCASH_CONTENT(byte[] cASH_CONTENT) {
		CASH_CONTENT = cASH_CONTENT;
	}

	public String getCASH_CONTENT_TYPE() {
		return CASH_CONTENT_TYPE;
	}

	public void setCASH_CONTENT_TYPE(String cASH_CONTENT_TYPE) {
		CASH_CONTENT_TYPE = cASH_CONTENT_TYPE;
	}

	public String getCASH_FILENAME() {
		return CASH_FILENAME;
	}

	public void setCASH_FILENAME(String cASH_FILENAME) {
		CASH_FILENAME = cASH_FILENAME;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
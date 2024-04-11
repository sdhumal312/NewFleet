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

@Document(collection = "VendorDocument")

public class VendorDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long 	_id;
	private String 	VENDOR_DOCNAME;
	private String 	VENDOR_FILENAME;
	private Integer VENDORID;
	private byte[] 	VENDOR_CONTENT;
	private String 	VENDOR_CONTENTTYPE;
	private Integer COMPANY_ID;
	private Long 	CREATEDBYID;
	private Long 	LASTMODIFIEDBYID;
	private boolean markForDelete;
	private Date 	CREATED_DATE;
	private Date 	LASTUPDATED_DATE;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public String getVENDOR_DOCNAME() {
		return VENDOR_DOCNAME;
	}
	public void setVENDOR_DOCNAME(String vENDOR_DOCNAME) {
		VENDOR_DOCNAME = vENDOR_DOCNAME;
	}
	public String getVENDOR_FILENAME() {
		return VENDOR_FILENAME;
	}
	public void setVENDOR_FILENAME(String vENDOR_FILENAME) {
		VENDOR_FILENAME = vENDOR_FILENAME;
	}
	public Integer getVENDORID() {
		return VENDORID;
	}
	public void setVENDORID(Integer vENDORID) {
		VENDORID = vENDORID;
	}
	public byte[] getVENDOR_CONTENT() {
		return VENDOR_CONTENT;
	}
	public void setVENDOR_CONTENT(byte[] vENDOR_CONTENT) {
		VENDOR_CONTENT = vENDOR_CONTENT;
	}
	public String getVENDOR_CONTENTTYPE() {
		return VENDOR_CONTENTTYPE;
	}
	public void setVENDOR_CONTENTTYPE(String vENDOR_CONTENTTYPE) {
		VENDOR_CONTENTTYPE = vENDOR_CONTENTTYPE;
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
	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}
	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
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

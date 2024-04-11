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

@Document(collection = "InventoryTyreRetreadDocument")

public class InventoryTyreRetreadDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long 	_id;
	private Long 	TRID;
	private byte[] 	TR_CONTENT;
	private String 	TR_CONTENT_TYRE;
	private String 	TR_FILENAME;
	private String 	CREATED_BY;
	private Date 	CREATED_DATE;
	private Integer COMPANY_ID;
	private boolean markForDelete;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public Long getTRID() {
		return TRID;
	}
	public void setTRID(Long tRID) {
		TRID = tRID;
	}
	public byte[] getTR_CONTENT() {
		return TR_CONTENT;
	}
	public void setTR_CONTENT(byte[] tR_CONTENT) {
		TR_CONTENT = tR_CONTENT;
	}
	public String getTR_CONTENT_TYRE() {
		return TR_CONTENT_TYRE;
	}
	public void setTR_CONTENT_TYRE(String tR_CONTENT_TYRE) {
		TR_CONTENT_TYRE = tR_CONTENT_TYRE;
	}
	public String getTR_FILENAME() {
		return TR_FILENAME;
	}
	public void setTR_FILENAME(String tR_FILENAME) {
		TR_FILENAME = tR_FILENAME;
	}
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}
	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}
	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}
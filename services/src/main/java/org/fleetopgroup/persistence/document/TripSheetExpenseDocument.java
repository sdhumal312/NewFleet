package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author fleetop
 *
 *
 *
 */


@Document(collection = "TripSheetExpenseDocument")
public class TripSheetExpenseDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long _id;
	private Long tripSheetExpenseId;
	private byte[] tripSheetExpenseContent;
	private String tripSheetExpenseContentType;
	private String tripSheetExpenseFileName;
	private Integer companyId;
	private Long createdById;
	private Long lastModifiedById;
	private boolean markForDelete;
	private Date created;
	private Date lastupdated;
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public Long getTripSheetExpenseId() {
		return tripSheetExpenseId;
	}
	public void setTripSheetExpenseId(Long tripSheetExpenseId) {
		this.tripSheetExpenseId = tripSheetExpenseId;
	}
	public byte[] getTripSheetExpenseContent() {
		return tripSheetExpenseContent;
	}
	public void setTripSheetExpenseContent(byte[] tripSheetExpenseContent) {
		this.tripSheetExpenseContent = tripSheetExpenseContent;
	}
	public String getTripSheetExpenseContentType() {
		return tripSheetExpenseContentType;
	}
	public void setTripSheetExpenseContentType(String tripSheetExpenseContentType) {
		this.tripSheetExpenseContentType = tripSheetExpenseContentType;
	}
	public String getTripSheetExpenseFileName() {
		return tripSheetExpenseFileName;
	}
	public void setTripSheetExpenseFileName(String tripSheetExpenseFileName) {
		this.tripSheetExpenseFileName = tripSheetExpenseFileName;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Long getCreatedById() {
		return createdById;
	}
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}
	public Long getLastModifiedById() {
		return lastModifiedById;
	}
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastupdated() {
		return lastupdated;
	}
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}

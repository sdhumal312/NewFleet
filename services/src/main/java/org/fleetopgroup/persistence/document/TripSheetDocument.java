package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TripSheetDocument")
public class TripSheetDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long _id;
	private Long tripSheetId;
	private byte[] tripSheetContent;
	private String tripSheetContentType;
	private String tripSheetFileName;
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
	public Long getTripSheetId() {
		return tripSheetId;
	}
	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}
	public byte[] getTripSheetContent() {
		return tripSheetContent;
	}
	public void setTripSheetContent(byte[] tripSheetContent) {
		this.tripSheetContent = tripSheetContent;
	}
	public String getTripSheetContentType() {
		return tripSheetContentType;
	}
	public void setTripSheetContentType(String tripSheetContentType) {
		this.tripSheetContentType = tripSheetContentType;
	}
	public String getTripSheetFileName() {
		return tripSheetFileName;
	}
	public void setTripSheetFileName(String tripSheetFileName) {
		this.tripSheetFileName = tripSheetFileName;
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

package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TripCheckPointParameterInspectionPhoto")
public class TripCheckPointParameterInspectionPhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long _id;
	private Long tripCheckPointParameterInspectionId;
	private byte[] tripCheckPointParameterInspectionContent;
	private String tripCheckPointParameterInspectionContentType;
	private String tripCheckPointParameterInspectionFileName;
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
	public Long getTripCheckPointParameterInspectionId() {
		return tripCheckPointParameterInspectionId;
	}
	public void setTripCheckPointParameterInspectionId(Long tripCheckPointParameterInspectionId) {
		this.tripCheckPointParameterInspectionId = tripCheckPointParameterInspectionId;
	}
	public byte[] getTripCheckPointParameterInspectionContent() {
		return tripCheckPointParameterInspectionContent;
	}
	public void setTripCheckPointParameterInspectionContent(byte[] tripCheckPointParameterInspectionContent) {
		this.tripCheckPointParameterInspectionContent = tripCheckPointParameterInspectionContent;
	}
	public String getTripCheckPointParameterInspectionContentType() {
		return tripCheckPointParameterInspectionContentType;
	}
	public void setTripCheckPointParameterInspectionContentType(String tripCheckPointParameterInspectionContentType) {
		this.tripCheckPointParameterInspectionContentType = tripCheckPointParameterInspectionContentType;
	}
	public String getTripCheckPointParameterInspectionFileName() {
		return tripCheckPointParameterInspectionFileName;
	}
	public void setTripCheckPointParameterInspectionFileName(String tripCheckPointParameterInspectionFileName) {
		this.tripCheckPointParameterInspectionFileName = tripCheckPointParameterInspectionFileName;
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
	
}

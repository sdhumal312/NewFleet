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

@Document(collection = "VehiclePhoto")

public class VehiclePhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Integer _id;
	private Date 	uploaddateOn;
	private Long 	photoTypeId;
	private String 	filename;
	private Integer vehid;
	private byte[] 	content;
	private String 	contentType;
	private Integer companyId;
	private Long 	createdById;
	private boolean markForDelete;
	
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public Date getUploaddateOn() {
		return uploaddateOn;
	}
	public void setUploaddateOn(Date uploaddateOn) {
		this.uploaddateOn = uploaddateOn;
	}
	public Long getPhotoTypeId() {
		return photoTypeId;
	}
	public void setPhotoTypeId(Long photoTypeId) {
		this.photoTypeId = photoTypeId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getVehid() {
		return vehid;
	}
	public void setVehid(Integer vehid) {
		this.vehid = vehid;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
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
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}
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

@Document(collection = "FuelDocument")
public class FuelDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long _id;
	private Long fuel_id;
	private byte[] fuel_content;
	private String fuel_contentType;
	private String fuel_filename;
	private Integer companyId;
	private Long createdById;
	private Long lastModifiedById;
	boolean markForDelete;
	private Date created;
	private Date lastupdated;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public Long getFuel_id() {
		return fuel_id;
	}
	public void setFuel_id(Long fuel_id) {
		this.fuel_id = fuel_id;
	}
	public byte[] getFuel_content() {
		return fuel_content;
	}
	public void setFuel_content(byte[] fuel_content) {
		this.fuel_content = fuel_content;
	}
	public String getFuel_contentType() {
		return fuel_contentType;
	}
	public void setFuel_contentType(String fuel_contentType) {
		this.fuel_contentType = fuel_contentType;
	}
	public String getFuel_filename() {
		return fuel_filename;
	}
	public void setFuel_filename(String fuel_filename) {
		this.fuel_filename = fuel_filename;
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

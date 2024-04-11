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

@Document(collection = "TyreDocument")
public class TyreDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long _id;
	public Long getTyre_id() {
		return tyre_id;
	}
	public void setTyre_id(Long tyre_id) {
		this.tyre_id = tyre_id;
	}
	public byte[] getTyre_content() {
		return tyre_content;
	}
	public void setTyre_content(byte[] tyre_content) {
		this.tyre_content = tyre_content;
	}
	public String getTyre_contentType() {
		return tyre_contentType;
	}
	public void setTyre_contentType(String tyre_contentType) {
		this.tyre_contentType = tyre_contentType;
	}
	public String getTyre_filename() {
		return tyre_filename;
	}
	public void setTyre_filename(String tyre_filename) {
		this.tyre_filename = tyre_filename;
	}
	private Long tyre_id;
	private byte[] tyre_content;
	private String tyre_contentType;
	private String tyre_filename;
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

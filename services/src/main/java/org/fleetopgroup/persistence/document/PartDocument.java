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

@Document(collection = "PartDocument")
public class PartDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long _id;
	public Long getPart_id() {
		return part_id;
	}
	public void setPart_id(Long part_id) {
		this.part_id = part_id;
	}
	public byte[] getPart_content() {
		return part_content;
	}
	public void setPart_content(byte[] part_content) {
		this.part_content = part_content;
	}
	public String getPart_contentType() {
		return part_contentType;
	}
	public void setPart_contentType(String part_contentType) {
		this.part_contentType = part_contentType;
	}
	public String getPart_filename() {
		return part_filename;
	}
	public void setPart_filename(String part_filename) {
		this.part_filename = part_filename;
	}
	private Long part_id;
	private byte[] part_content;
	private String part_contentType;
	private String part_filename;
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

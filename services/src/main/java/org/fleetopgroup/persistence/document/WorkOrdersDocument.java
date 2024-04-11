package org.fleetopgroup.persistence.document;

/**
 * @author Ashish Tiwari
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WorkOrdersDocument")
public class WorkOrdersDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long 	_id;
	@Indexed(name = "workorder_id")
	private Long 	workorder_id;
	private byte[] 	workorder_content;
	private String 	workorder_contentType;
	private String 	workorder_filename;
	@Indexed(name = "companyId")
	private Integer companyId;
	private String 	createdBy;
	private String 	lastModifiedBy;
	private Date 	created;
	private Date 	lastupdated;
	private boolean markForDelete;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public Long getWorkorder_id() {
		return workorder_id;
	}
	public void setWorkorder_id(Long workorder_id) {
		this.workorder_id = workorder_id;
	}
	public byte[] getWorkorder_content() {
		return workorder_content;
	}
	public void setWorkorder_content(byte[] workorder_content) {
		this.workorder_content = workorder_content;
	}
	public String getWorkorder_contentType() {
		return workorder_contentType;
	}
	public void setWorkorder_contentType(String workorder_contentType) {
		this.workorder_contentType = workorder_contentType;
	}
	public String getWorkorder_filename() {
		return workorder_filename;
	}
	public void setWorkorder_filename(String workorder_filename) {
		this.workorder_filename = workorder_filename;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}
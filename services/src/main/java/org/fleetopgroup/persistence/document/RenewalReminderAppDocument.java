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

@Document(collection = "RenewalReminderAppDocument")

public class RenewalReminderAppDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long 	_id;
	private Long 	renewalApproval_id;
	private String 	renewal_filename;
	private byte[] 	renewal_content;
	private String 	renewal_contentType;
	private Integer	companyId;
	private Long 	createdById;
	private Long 	lastModifiedById;
	private boolean markForDelete;
	private Date 	created;
	private Date 	lastupdated;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public Long getRenewalApproval_id() {
		return renewalApproval_id;
	}
	public void setRenewalApproval_id(Long renewalApproval_id) {
		this.renewalApproval_id = renewalApproval_id;
	}
	public String getRenewal_filename() {
		return renewal_filename;
	}
	public void setRenewal_filename(String renewal_filename) {
		this.renewal_filename = renewal_filename;
	}
	public byte[] getRenewal_content() {
		return renewal_content;
	}
	public void setRenewal_content(byte[] renewal_content) {
		this.renewal_content = renewal_content;
	}
	public String getRenewal_contentType() {
		return renewal_contentType;
	}
	public void setRenewal_contentType(String renewal_contentType) {
		this.renewal_contentType = renewal_contentType;
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

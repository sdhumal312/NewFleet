package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "RenewalReminderDocumentHistory")

public class RenewalReminderDocumentHistory implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	private Long 	_id;
	private Long 	rendoc_id;
	private Long 	renewal_id;
	private Long 	renewal_R_Number;
	private Long 	renewalHis_id;
	private String 	renewal_filename;
	private byte[] 	renewal_content;
	private String 	renewal_contentType;
	private Integer companyId;
	private Long 	createdById;
	private Long 	lastModifiedById;
	private Date 	created;
	private Date 	lastupdated;
	private boolean markForDelete;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public Long getRendoc_id() {
		return rendoc_id;
	}
	public void setRendoc_id(Long rendoc_id) {
		this.rendoc_id = rendoc_id;
	}
	public Long getRenewal_id() {
		return renewal_id;
	}
	public void setRenewal_id(Long renewal_id) {
		this.renewal_id = renewal_id;
	}
	public Long getRenewal_R_Number() {
		return renewal_R_Number;
	}
	public void setRenewal_R_Number(Long renewal_R_Number) {
		this.renewal_R_Number = renewal_R_Number;
	}
	public Long getRenewalHis_id() {
		return renewalHis_id;
	}
	public void setRenewalHis_id(Long renewalHis_id) {
		this.renewalHis_id = renewalHis_id;
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

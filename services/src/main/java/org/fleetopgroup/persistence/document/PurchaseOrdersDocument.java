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

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PurchaseOrdersDocument")

public class PurchaseOrdersDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long 	_id;
	@Indexed(name="purchaseorder_id", background = true)
	private Long 	purchaseorder_id;
	private String 	purchaseorder_document;
	private byte[] 	purchaseorder_content;
	private String 	purchaseorder_contentType;
	private String 	purchaseorder_filename;
	private Long 	createdById;
	private Long 	lastModifiedById;
	private Date 	created;
	private Date 	lastupdated;
	private Integer companyId;
	private boolean markForDelete;
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}
	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}
	public String getPurchaseorder_document() {
		return purchaseorder_document;
	}
	public void setPurchaseorder_document(String purchaseorder_document) {
		this.purchaseorder_document = purchaseorder_document;
	}
	public byte[] getPurchaseorder_content() {
		return purchaseorder_content;
	}
	public void setPurchaseorder_content(byte[] purchaseorder_content) {
		this.purchaseorder_content = purchaseorder_content;
	}
	public String getPurchaseorder_contentType() {
		return purchaseorder_contentType;
	}
	public void setPurchaseorder_contentType(String purchaseorder_contentType) {
		this.purchaseorder_contentType = purchaseorder_contentType;
	}
	public String getPurchaseorder_filename() {
		return purchaseorder_filename;
	}
	public void setPurchaseorder_filename(String purchaseorder_filename) {
		this.purchaseorder_filename = purchaseorder_filename;
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
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}
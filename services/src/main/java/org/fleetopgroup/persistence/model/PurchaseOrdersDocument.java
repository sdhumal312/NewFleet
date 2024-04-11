package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "purchaseordersdocument")
public class PurchaseOrdersDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchaseorder_documentid")
	private Long purchaseorder_documentid;

	@Column(name = "purchaseorder_id")
	private Long purchaseorder_id;

	@Column(name = "purchaseorder_document")
	private String purchaseorder_document;

	
	@Lob
	@Column(name = "purchaseorder_content", length = 31457280)
	@Basic(fetch = FetchType.LAZY)
	private byte[] purchaseorder_content;

	@Column(name = "purchaseorder_contenttype")
	private String purchaseorder_contentType;

	@Column(name = "purchaseorder_filename")
	private String purchaseorder_filename;

	/*@Column(name = "createdBy", length = 25)
	private String createdBy;
	@Column(name = "lastModifiedBy", length = 25)
	private String lastModifiedBy;*/
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = false, updatable = false)
	private Date lastupdated;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public PurchaseOrdersDocument() {
		super();
	}

	

	public PurchaseOrdersDocument(Long purchaseorder_documentid, Long purchaseorder_id, byte[] purchaseorder_content,
			String purchaseorder_contentType, String purchaseorder_filename, Long createdBy, Long lastModifiedBy,
			Date created, Date lastupdated, Integer companyId) {
		super();
		this.purchaseorder_documentid = purchaseorder_documentid;
		this.purchaseorder_id = purchaseorder_id;
		this.purchaseorder_content = purchaseorder_content;
		this.purchaseorder_contentType = purchaseorder_contentType;
		this.purchaseorder_filename = purchaseorder_filename;
		this.createdById = createdBy;
		this.lastModifiedById = lastModifiedBy;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
	}



	/**
	 * @return the purchaseorder_documentid
	 */
	public Long getPurchaseorder_documentid() {
		return purchaseorder_documentid;
	}

	/**
	 * @param purchaseorder_documentid
	 *            the purchaseorder_documentid to set
	 */
	public void setPurchaseorder_documentid(Long purchaseorder_documentid) {
		this.purchaseorder_documentid = purchaseorder_documentid;
	}

	/**
	 * @return the purchaseorder_id
	 */
	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	/**
	 * @param purchaseorder_id
	 *            the purchaseorder_id to set
	 */
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



	/**
	 * @return the purchaseorder_contentType
	 */
	public String getPurchaseorder_contentType() {
		return purchaseorder_contentType;
	}

	/**
	 * @param purchaseorder_contentType
	 *            the purchaseorder_contentType to set
	 */
	public void setPurchaseorder_contentType(String purchaseorder_contentType) {
		this.purchaseorder_contentType = purchaseorder_contentType;
	}

	/**
	 * @return the purchaseorder_filename
	 */
	public String getPurchaseorder_filename() {
		return purchaseorder_filename;
	}

	/**
	 * @param purchaseorder_filename
	 *            the purchaseorder_filename to set
	 */
	public void setPurchaseorder_filename(String purchaseorder_filename) {
		this.purchaseorder_filename = purchaseorder_filename;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}



	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}



	/**
	 * @return the createdBy
	 *//*
	public String getCreatedBy() {
		return createdBy;
	}

	*//**
	 * @param createdBy
	 *            the createdBy to set
	 *//*
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	*//**
	 * @return the lastModifiedBy
	 *//*
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	*//**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 *//*
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}*/

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getCompanyId() {
		return companyId;
	}



	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}



	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}



	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}



	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}



	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((purchaseorder_documentid == null) ? 0 : purchaseorder_documentid.hashCode());
		result = prime * result + ((purchaseorder_id == null) ? 0 : purchaseorder_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseOrdersDocument other = (PurchaseOrdersDocument) obj;
		if (purchaseorder_documentid == null) {
			if (other.purchaseorder_documentid != null)
				return false;
		} else if (!purchaseorder_documentid.equals(other.purchaseorder_documentid))
			return false;
		if (purchaseorder_id == null) {
			if (other.purchaseorder_id != null)
				return false;
		} else if (!purchaseorder_id.equals(other.purchaseorder_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseOrdersDocument [purchaseorder_documentid=");
		builder.append(purchaseorder_documentid);
		builder.append(", purchaseorder_id=");
		builder.append(purchaseorder_id);
		builder.append(", purchaseorder_content=");
		builder.append(purchaseorder_content);
		builder.append(", purchaseorder_contentType=");
		builder.append(purchaseorder_contentType);
		builder.append(", purchaseorder_filename=");
		builder.append(purchaseorder_filename);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

}
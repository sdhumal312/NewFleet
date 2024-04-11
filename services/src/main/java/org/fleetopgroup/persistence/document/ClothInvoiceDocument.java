package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author fleetop
 *
 *
 *
 */


@Document(collection = "ClothInvoiceDocument")
public class ClothInvoiceDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long _id;
	private Long clothInvoiceId;
	private byte[] clothInvoiceContent;
	private String clothInvoiceContentType;
	private String clothInvoiceFileName;
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
	public Long getClothInvoiceId() {
		return clothInvoiceId;
	}
	public void setClothInvoiceId(Long clothInvoiceId) {
		this.clothInvoiceId = clothInvoiceId;
	}
	public byte[] getClothInvoiceContent() {
		return clothInvoiceContent;
	}
	public void setClothInvoiceContent(byte[] clothInvoiceContent) {
		this.clothInvoiceContent = clothInvoiceContent;
	}
	public String getClothInvoiceContentType() {
		return clothInvoiceContentType;
	}
	public void setClothInvoiceContentType(String clothInvoiceContentType) {
		this.clothInvoiceContentType = clothInvoiceContentType;
	}
	public String getClothInvoiceFileName() {
		return clothInvoiceFileName;
	}
	public void setClothInvoiceFileName(String clothInvoiceFileName) {
		this.clothInvoiceFileName = clothInvoiceFileName;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}

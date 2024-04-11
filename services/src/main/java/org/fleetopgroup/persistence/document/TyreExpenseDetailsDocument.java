package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TyreExpenseDetailsDocument")
public class TyreExpenseDetailsDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long _id;
	private Long tyreExpenseDetailsId;
	private byte[] tyreExpenseDetailsContent;
	private String tyreExpenseDetailsContentType;
	private String tyreExpenseDetailsFileName;
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
	public Long getTyreExpenseDetailsId() {
		return tyreExpenseDetailsId;
	}
	public void setTyreExpenseDetailsId(Long tyreExpenseDetailsId) {
		this.tyreExpenseDetailsId = tyreExpenseDetailsId;
	}
	public byte[] getTyreExpenseDetailsContent() {
		return tyreExpenseDetailsContent;
	}
	public void setTyreExpenseDetailsContent(byte[] tyreExpenseDetailsContent) {
		this.tyreExpenseDetailsContent = tyreExpenseDetailsContent;
	}
	public String getTyreExpenseDetailsContentType() {
		return tyreExpenseDetailsContentType;
	}
	public void setTyreExpenseDetailsContentType(String tyreExpenseDetailsContentType) {
		this.tyreExpenseDetailsContentType = tyreExpenseDetailsContentType;
	}
	public String getTyreExpenseDetailsFileName() {
		return tyreExpenseDetailsFileName;
	}
	public void setTyreExpenseDetailsFileName(String tyreExpenseDetailsFileName) {
		this.tyreExpenseDetailsFileName = tyreExpenseDetailsFileName;
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

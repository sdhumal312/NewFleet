package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TyreExpenseDetails")
public class TyreExpenseDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tyreExpenseDetailsId")
	private Long tyreExpenseDetailsId;
	
	@Column(name = "tyreId")
	private Long tyreId;

	@Column(name = "tyreExpenseId")
	private Integer tyreExpenseId;
	
	@Column(name = "tyreExpenseAmount")
	private Double tyreExpenseAmount;
	
	@Column(name = "tyreExpenseDate")
	private Date tyreExpenseDate;
	
	@Column(name = "discount")
	private Double discount;
	
	@Column(name = "gst")
	private Double gst;
	
	@Column(name = "totalTyreExpenseAmount")
	private Double totalTyreExpenseAmount;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "tyreTypeId")
	private short tyreTypeId;

	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedBy")
	private Long lastUpdatedBy;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId" )
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Basic(optional = false)
	@Column(name = "tyreExpenseDetailsDocument", nullable = false)
	private boolean tyreExpenseDetailsDocument = false;

	@Column(name = "tyreExpenseDetailsDocumentId")
	private Long tyreExpenseDetailsDocumentId;

	@Column(name = "vendorId")
	private Integer vendorId;
	
	public TyreExpenseDetails() {
		super();
		
	}

	public TyreExpenseDetails(Long tyreExpenseDetailsId, Long tyreId, Integer tyreExpenseId, Double tyreExpenseAmount,
			Long createdById, Long lastUpdatedBy, Date creationDate, Date lastUpdatedOn, Integer companyId,
			boolean markForDelete) {
		super();
		this.tyreExpenseDetailsId = tyreExpenseDetailsId;
		this.tyreId = tyreId;
		this.tyreExpenseId = tyreExpenseId;
		this.tyreExpenseAmount = tyreExpenseAmount;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((tyreExpenseAmount == null) ? 0 : tyreExpenseAmount.hashCode());
		result = prime * result + ((tyreExpenseDetailsId == null) ? 0 : tyreExpenseDetailsId.hashCode());
		result = prime * result + ((tyreExpenseId == null) ? 0 : tyreExpenseId.hashCode());
		result = prime * result + ((tyreId == null) ? 0 : tyreId.hashCode());
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
		TyreExpenseDetails other = (TyreExpenseDetails) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (lastUpdatedBy == null) {
			if (other.lastUpdatedBy != null)
				return false;
		} else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (tyreExpenseAmount == null) {
			if (other.tyreExpenseAmount != null)
				return false;
		} else if (!tyreExpenseAmount.equals(other.tyreExpenseAmount))
			return false;
		if (tyreExpenseDetailsId == null) {
			if (other.tyreExpenseDetailsId != null)
				return false;
		} else if (!tyreExpenseDetailsId.equals(other.tyreExpenseDetailsId))
			return false;
		if (tyreExpenseId == null) {
			if (other.tyreExpenseId != null)
				return false;
		} else if (!tyreExpenseId.equals(other.tyreExpenseId))
			return false;
		if (tyreId == null) {
			if (other.tyreId != null)
				return false;
		} else if (!tyreId.equals(other.tyreId))
			return false;
		return true;
	}

	public Long getTyreExpenseDetailsId() {
		return tyreExpenseDetailsId;
	}

	public void setTyreExpenseDetailsId(Long tyreExpenseDetailsId) {
		this.tyreExpenseDetailsId = tyreExpenseDetailsId;
	}

	public Long getTyreId() {
		return tyreId;
	}

	public void setTyreId(Long tyreId) {
		this.tyreId = tyreId;
	}

	public Integer getTyreExpenseId() {
		return tyreExpenseId;
	}

	public void setTyreExpenseId(Integer tyreExpenseId) {
		this.tyreExpenseId = tyreExpenseId;
	}

	public Double getTyreExpenseAmount() {
		return tyreExpenseAmount;
	}

	public void setTyreExpenseAmount(Double tyreExpenseAmount) {
		this.tyreExpenseAmount = tyreExpenseAmount;
	}

	public Date getTyreExpenseDate() {
		return tyreExpenseDate;
	}

	public void setTyreExpenseDate(Date tyreExpenseDate) {
		this.tyreExpenseDate = tyreExpenseDate;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getTotalTyreExpenseAmount() {
		return totalTyreExpenseAmount;
	}

	public void setTotalTyreExpenseAmount(Double totalTyreExpenseAmount) {
		this.totalTyreExpenseAmount = totalTyreExpenseAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getTyreTypeId() {
		return tyreTypeId;
	}

	public void setTyreTypeId(short tyreTypeId) {
		this.tyreTypeId = tyreTypeId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isTyreExpenseDetailsDocument() {
		return tyreExpenseDetailsDocument;
	}

	public void setTyreExpenseDetailsDocument(boolean tyreExpenseDetailsDocument) {
		this.tyreExpenseDetailsDocument = tyreExpenseDetailsDocument;
	}

	public Long getTyreExpenseDetailsDocumentId() {
		return tyreExpenseDetailsDocumentId;
	}

	public void setTyreExpenseDetailsDocumentId(Long tyreExpenseDetailsDocumentId) {
		this.tyreExpenseDetailsDocumentId = tyreExpenseDetailsDocumentId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	@Override
	public String toString() {
		return "TyreExpenseDetails [tyreExpenseDetailsId=" + tyreExpenseDetailsId + ", tyreId=" + tyreId
				+ ", tyreExpenseId=" + tyreExpenseId + ", tyreExpenseAmount=" + tyreExpenseAmount + ", tyreExpenseDate="
				+ tyreExpenseDate + ", discount=" + discount + ", gst=" + gst + ", totalTyreExpenseAmount="
				+ totalTyreExpenseAmount + ", description=" + description + ", tyreTypeId=" + tyreTypeId
				+ ", createdById=" + createdById + ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate=" + creationDate
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", tyreExpenseDetailsDocument=" + tyreExpenseDetailsDocument + ", tyreExpenseDetailsDocumentId="
				+ tyreExpenseDetailsDocumentId + "]";
	}

	
	

}
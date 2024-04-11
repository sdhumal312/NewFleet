package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TyreSoldDetails")
public class TyreSoldDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tyreSoldDetailsId")
	private Long tyreSoldDetailsId;
	
	@Column(name = "tyreId")
	private Long tyreId;
	
	@Column(name = "discount")
	private Double discount;
	
	@Column(name = "gst")
	private Double gst;

	@Column(name = "tyreSoldAmount")
	private Double tyreSoldAmount;
	
	@Column(name = "tyreSoldNetAmount")
	private Double tyreSoldNetAmount;
	
	@Column(name = "tyreSoldInvoiceId")
	private Long tyreSoldInvoiceId;
	
	@Column(name = "description")
	private String description;
	
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

	public TyreSoldDetails() {
		super();
		
	}

	public TyreSoldDetails(Long tyreSoldDetailsId, Long tyreId, Double discount, Double gst, Double tyreSoldAmount,Double tyreSoldNetAmount,
			Long tyreSoldInvoiceId, String description, Long createdById, Long lastUpdatedBy, Date creationDate,
			Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.tyreSoldDetailsId = tyreSoldDetailsId;
		this.tyreId = tyreId;
		this.discount = discount;
		this.gst = gst;
		this.tyreSoldAmount = tyreSoldAmount;
		this.tyreSoldNetAmount = tyreSoldNetAmount;
		this.tyreSoldInvoiceId = tyreSoldInvoiceId;
		this.description = description;
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((gst == null) ? 0 : gst.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((tyreId == null) ? 0 : tyreId.hashCode());
		result = prime * result + ((tyreSoldAmount == null) ? 0 : tyreSoldAmount.hashCode());
		result = prime * result + ((tyreSoldNetAmount == null) ? 0 : tyreSoldNetAmount.hashCode());
		result = prime * result + ((tyreSoldDetailsId == null) ? 0 : tyreSoldDetailsId.hashCode());
		result = prime * result + ((tyreSoldInvoiceId == null) ? 0 : tyreSoldInvoiceId.hashCode());
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
		TyreSoldDetails other = (TyreSoldDetails) obj;
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (gst == null) {
			if (other.gst != null)
				return false;
		} else if (!gst.equals(other.gst))
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
		if (tyreId == null) {
			if (other.tyreId != null)
				return false;
		} else if (!tyreId.equals(other.tyreId))
			return false;
		if (tyreSoldAmount == null) {
			if (other.tyreSoldAmount != null)
				return false;
		} else if (!tyreSoldAmount.equals(other.tyreSoldAmount))
			return false;
		if (tyreSoldNetAmount == null) {
			if (other.tyreSoldNetAmount != null)
				return false;
		} else if (!tyreSoldNetAmount.equals(other.tyreSoldNetAmount))
			return false;
		if (tyreSoldDetailsId == null) {
			if (other.tyreSoldDetailsId != null)
				return false;
		} else if (!tyreSoldDetailsId.equals(other.tyreSoldDetailsId))
			return false;
		if (tyreSoldInvoiceId == null) {
			if (other.tyreSoldInvoiceId != null)
				return false;
		} else if (!tyreSoldInvoiceId.equals(other.tyreSoldInvoiceId))
			return false;
		return true;
	}

	public Long getTyreSoldDetailsId() {
		return tyreSoldDetailsId;
	}

	public void setTyreSoldDetailsId(Long tyreSoldDetailsId) {
		this.tyreSoldDetailsId = tyreSoldDetailsId;
	}

	public Long getTyreId() {
		return tyreId;
	}

	public void setTyreId(Long tyreId) {
		this.tyreId = tyreId;
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

	public Double getTyreSoldAmount() {
		return tyreSoldAmount;
	}

	public void setTyreSoldAmount(Double tyreSoldAmount) {
		this.tyreSoldAmount = tyreSoldAmount;
	}

	public Double getTyreSoldNetAmount() {
		return tyreSoldNetAmount;
	}

	public void setTyreSoldNetAmount(Double tyreSoldNetAmount) {
		this.tyreSoldNetAmount = tyreSoldNetAmount;
	}

	public Long getTyreSoldInvoiceId() {
		return tyreSoldInvoiceId;
	}

	public void setTyreSoldInvoiceId(Long tyreSoldInvoiceId) {
		this.tyreSoldInvoiceId = tyreSoldInvoiceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		return "TyreSoldDetails [tyreSoldDetailsId=" + tyreSoldDetailsId + ", tyreId=" + tyreId + ", discount="
				+ discount + ", gst=" + gst + ", tyreSoldAmount=" + tyreSoldAmount + ", tyreSoldNetAmount="
				+ tyreSoldNetAmount + ", tyreSoldInvoiceId=" + tyreSoldInvoiceId + ", description=" + description
				+ ", createdById=" + createdById + ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate=" + creationDate
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}

	
	
	
	

}
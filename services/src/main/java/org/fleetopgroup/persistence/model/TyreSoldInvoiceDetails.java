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
@Table(name = "TyreSoldInvoiceDetails")
public class TyreSoldInvoiceDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tyreSoldInvoiceId")
	private Long tyreSoldInvoiceId;
	
	@Column(name = "tyreSoldInvoiceNumber")
	private String tyreSoldInvoiceNumber;
	
	@Column(name = "soldTyreWeight")
	private Double soldTyreWeight;
	
	@Column(name = "discount")
	private Double discount;
	
	@Column(name = "gst")
	private Double gst;

	@Column(name = "tyreSoldInvoiceAmount")
	private Double tyreSoldInvoiceAmount;
	
	@Column(name = "tyreSoldInvoiceNetAmount")
	private Double tyreSoldInvoiceNetAmount;
	
	@Column(name = "tyreSoldInvoiceDate")
	private Date tyreSoldInvoiceDate;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "soldType")// this is for ("Available to sold" OR "scraped to sold")
	private short soldType;
	
	@Column(name = "tyreStatus")// this is for ("Process to sold" OR "Sold")
	private short tyreStatus;
	
	@Column(name = "tyreQuantity")
	private Long tyreQuantity;

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

	public TyreSoldInvoiceDetails() {
		super();
		
	}

	public TyreSoldInvoiceDetails(Long tyreSoldInvoiceId, String tyreSoldInvoiceNumber,
			Double discount,Double gst,Double tyreSoldInvoiceAmount,Double tyreSoldInvoiceNetAmount,Double soldTyreWeight,
			Date tyreSoldInvoiceDate, String description, short soldType,short tyreStatus, Long createdById, Long lastUpdatedBy,
			Date creationDate, Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.tyreSoldInvoiceId = tyreSoldInvoiceId;
		this.tyreSoldInvoiceNumber = tyreSoldInvoiceNumber;
		this.discount = discount;
		this.gst = gst;
		this.tyreSoldInvoiceAmount = tyreSoldInvoiceAmount;
		this.tyreSoldInvoiceNetAmount = tyreSoldInvoiceNetAmount;
		this.soldTyreWeight = soldTyreWeight;
		this.tyreSoldInvoiceDate = tyreSoldInvoiceDate;
		this.description = description;
		this.soldType = soldType;
		this.tyreStatus = tyreStatus;
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
		result = prime * result + soldType;
		result = prime * result + tyreStatus;
		result = prime * result + ((soldTyreWeight == null) ? 0 : soldTyreWeight.hashCode());
		result = prime * result + ((tyreSoldInvoiceAmount == null) ? 0 : tyreSoldInvoiceAmount.hashCode());
		result = prime * result + ((tyreSoldInvoiceNetAmount == null) ? 0 : tyreSoldInvoiceNetAmount.hashCode());
		result = prime * result + ((tyreSoldInvoiceDate == null) ? 0 : tyreSoldInvoiceDate.hashCode());
		result = prime * result + ((tyreSoldInvoiceId == null) ? 0 : tyreSoldInvoiceId.hashCode());
		result = prime * result + ((tyreSoldInvoiceNumber == null) ? 0 : tyreSoldInvoiceNumber.hashCode());
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
		TyreSoldInvoiceDetails other = (TyreSoldInvoiceDetails) obj;
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
		if (soldTyreWeight == null) {
			if (other.soldTyreWeight != null)
				return false;
		} else if (!soldTyreWeight.equals(other.soldTyreWeight))
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
		if (soldType != other.soldType)
			return false;
		if (tyreStatus != other.tyreStatus)
			return false;
		if (tyreSoldInvoiceAmount == null) {
			if (other.tyreSoldInvoiceAmount != null)
				return false;
		} else if (!tyreSoldInvoiceAmount.equals(other.tyreSoldInvoiceAmount))
			return false;
		if (tyreSoldInvoiceNetAmount == null) {
			if (other.tyreSoldInvoiceNetAmount != null)
				return false;
		} else if (!tyreSoldInvoiceNetAmount.equals(other.tyreSoldInvoiceNetAmount))
			return false;
		if (tyreSoldInvoiceDate == null) {
			if (other.tyreSoldInvoiceDate != null)
				return false;
		} else if (!tyreSoldInvoiceDate.equals(other.tyreSoldInvoiceDate))
			return false;
		if (tyreSoldInvoiceId == null) {
			if (other.tyreSoldInvoiceId != null)
				return false;
		} else if (!tyreSoldInvoiceId.equals(other.tyreSoldInvoiceId))
			return false;
		if (tyreSoldInvoiceNumber == null) {
			if (other.tyreSoldInvoiceNumber != null)
				return false;
		} else if (!tyreSoldInvoiceNumber.equals(other.tyreSoldInvoiceNumber))
			return false;
		return true;
	}

	public Long getTyreSoldInvoiceId() {
		return tyreSoldInvoiceId;
	}

	public void setTyreSoldInvoiceId(Long tyreSoldInvoiceId) {
		this.tyreSoldInvoiceId = tyreSoldInvoiceId;
	}

	public String getTyreSoldInvoiceNumber() {
		return tyreSoldInvoiceNumber;
	}

	public void setTyreSoldInvoiceNumber(String tyreSoldInvoiceNumber) {
		this.tyreSoldInvoiceNumber = tyreSoldInvoiceNumber;
	}

	public Double getSoldTyreWeight() {
		return soldTyreWeight;
	}

	public void setSoldTyreWeight(Double soldTyreWeight) {
		this.soldTyreWeight = soldTyreWeight;
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

	public Double getTyreSoldInvoiceAmount() {
		return tyreSoldInvoiceAmount;
	}

	public void setTyreSoldInvoiceAmount(Double tyreSoldInvoiceAmount) {
		this.tyreSoldInvoiceAmount = tyreSoldInvoiceAmount;
	}

	public Double getTyreSoldInvoiceNetAmount() {
		return tyreSoldInvoiceNetAmount;
	}

	public void setTyreSoldInvoiceNetAmount(Double tyreSoldInvoiceNetAmount) {
		this.tyreSoldInvoiceNetAmount = tyreSoldInvoiceNetAmount;
	}

	public Date getTyreSoldInvoiceDate() {
		return tyreSoldInvoiceDate;
	}

	public void setTyreSoldInvoiceDate(Date tyreSoldInvoiceDate) {
		this.tyreSoldInvoiceDate = tyreSoldInvoiceDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getSoldType() {
		return soldType;
	}

	public void setSoldType(short soldType) {
		this.soldType = soldType;
	}
	

	public short getTyreStatus() {
		return tyreStatus;
	}

	public void setTyreStatus(short tyreStatus) {
		this.tyreStatus = tyreStatus;
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

	
	public Long getTyreQuantity() {
		return tyreQuantity;
	}

	public void setTyreQuantity(Long tyreQuantity) {
		this.tyreQuantity = tyreQuantity;
	}

	@Override
	public String toString() {
		return "TyreSoldInvoiceDetails [tyreSoldInvoiceId=" + tyreSoldInvoiceId + ", tyreSoldInvoiceNumber="
				+ tyreSoldInvoiceNumber + ", soldTyreWeight=" + soldTyreWeight + ", discount=" + discount + ", gst="
				+ gst + ", tyreSoldInvoiceAmount=" + tyreSoldInvoiceAmount + ", tyreSoldInvoiceNetAmount="
				+ tyreSoldInvoiceNetAmount + ", tyreSoldInvoiceDate=" + tyreSoldInvoiceDate + ", description="
				+ description + ", soldType=" + soldType + ", tyreStatus=" + tyreStatus + ", tyreQuantity="
				+ tyreQuantity + ", createdById=" + createdById + ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate="
				+ creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + "]";
	}

	

	

	

	
	

}
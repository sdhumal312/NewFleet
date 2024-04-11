package org.fleetopgroup.persistence.model;

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
@Table(name="VendorFixedLaundryRate")
public class VendorFixedLaundryRate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendorLaundryRateId")
	private Long vendorLaundryRateId;

	/** The value for the VENDORID by email field */
	@Column(name = "vendorId")
	private Integer vendorId;

	@Column(name = "clothTypesId")
	private Long clothTypesId;

	@Column(name = "clothEachCost")
	private Double clothEachCost;

	/** The value for the PARTDISCOUNT by email field */
	@Column(name = "clothDiscount")
	private Double clothDiscount;

	/** The value for the PARTGST by email field */
	@Column(name = "clothGst")
	private Double clothGst;

	/** The value for the PARTTOTAL by email field */
	@Column(name = "clothTotal")
	private Double clothTotal;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the created DATE field */
	@Column(name = "creationDate", updatable = false)
	private Date creationDate;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastUpdated")
	private Date lastUpdated;

	public Long getVendorLaundryRateId() {
		return vendorLaundryRateId;
	}

	public void setVendorLaundryRateId(Long vendorLaundryRateId) {
		this.vendorLaundryRateId = vendorLaundryRateId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Double getClothEachCost() {
		return clothEachCost;
	}

	public void setClothEachCost(Double clothEachCost) {
		this.clothEachCost = clothEachCost;
	}

	public Double getClothDiscount() {
		return clothDiscount;
	}

	public void setClothDiscount(Double clothDiscount) {
		this.clothDiscount = clothDiscount;
	}

	public Double getClothGst() {
		return clothGst;
	}

	public void setClothGst(Double clothGst) {
		this.clothGst = clothGst;
	}

	public Double getClothTotal() {
		return clothTotal;
	}

	public void setClothTotal(Double clothTotal) {
		this.clothTotal = clothTotal;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clothTypesId == null) ? 0 : clothTypesId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((vendorLaundryRateId == null) ? 0 : vendorLaundryRateId.hashCode());
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
		VendorFixedLaundryRate other = (VendorFixedLaundryRate) obj;
		if (clothTypesId == null) {
			if (other.clothTypesId != null)
				return false;
		} else if (!clothTypesId.equals(other.clothTypesId))
			return false;
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
		if (vendorLaundryRateId == null) {
			if (other.vendorLaundryRateId != null)
				return false;
		} else if (!vendorLaundryRateId.equals(other.vendorLaundryRateId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VendorFixedLaundryRate [vendorLaundryRateId=");
		builder.append(vendorLaundryRateId);
		builder.append(", vendorId=");
		builder.append(vendorId);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", clothEachCost=");
		builder.append(clothEachCost);
		builder.append(", clothDiscount=");
		builder.append(clothDiscount);
		builder.append(", clothGst=");
		builder.append(clothGst);
		builder.append(", clothTotal=");
		builder.append(clothTotal);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastUpdated=");
		builder.append(lastUpdated);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

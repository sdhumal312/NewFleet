package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LowStockInventory")
public class LowStockInventory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lowStockInventoryId")
	private Long lowStockInventoryId;
	
	@Column(name = "partid", nullable = false)
	private Long partid;
	
	@Column(name = "lowstocklevel", length = 10)
	private Integer lowstocklevel;

	@Column(name = "reorderquantity", length = 10)
	private Integer reorderquantity;
	
	@Column(name = "locationId")
	private Integer locationId;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "createdById",  updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "created", updatable = false)
	private Timestamp created;

	@Column(name = "lastupdated")
	private Timestamp lastupdated;
	
	@Column(name = "unitCost")
	private Double unitCost;
	
	@Column(name = "discount")
	private Double discount;
	
	@Column(name = "tax")
	private Double tax;
	
	
	public LowStockInventory() {
		super();
	}

	public LowStockInventory(Long lowStockInventoryId, Long partid, Integer lowstocklevel, Integer reorderquantity,
			Integer companyId, boolean markForDelete, Long createdById, Long lastModifiedById, Timestamp created,
			Timestamp lastupdated, Double unitCost, Double discount, Double tax) {
		super();
		this.lowStockInventoryId = lowStockInventoryId;
		this.partid = partid;
		this.lowstocklevel = lowstocklevel;
		this.reorderquantity = reorderquantity;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.created = created;
		this.lastupdated = lastupdated;
		this.unitCost=unitCost;
		this.discount=discount;
		this.tax=tax;
	}
	
	
	

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Long getLowStockInventoryId() {
		return lowStockInventoryId;
	}

	public void setLowStockInventoryId(Long lowStockInventoryId) {
		this.lowStockInventoryId = lowStockInventoryId;
	}

	public Long getPartid() {
		return partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
	}

	public Integer getLowstocklevel() {
		return lowstocklevel;
	}

	public void setLowstocklevel(Integer lowstocklevel) {
		this.lowstocklevel = lowstocklevel;
	}

	public Integer getReorderquantity() {
		return reorderquantity;
	}

	public void setReorderquantity(Integer reorderquantity) {
		this.reorderquantity = reorderquantity;
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

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Timestamp lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((lowStockInventoryId == null) ? 0 : lowStockInventoryId.hashCode());
		result = prime * result + ((lowstocklevel == null) ? 0 : lowstocklevel.hashCode());
		result = prime * result + ((partid == null) ? 0 : partid.hashCode());
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
		LowStockInventory other = (LowStockInventory) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (lowStockInventoryId == null) {
			if (other.lowStockInventoryId != null)
				return false;
		} else if (!lowStockInventoryId.equals(other.lowStockInventoryId))
			return false;
		if (lowstocklevel == null) {
			if (other.lowstocklevel != null)
				return false;
		} else if (!lowstocklevel.equals(other.lowstocklevel))
			return false;
		if (partid == null) {
			if (other.partid != null)
				return false;
		} else if (!partid.equals(other.partid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LowStockInventory [lowStockInventoryId=" + lowStockInventoryId + ", partid=" + partid
				+ ", lowstocklevel=" + lowstocklevel + ", reorderquantity=" + reorderquantity + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", created=" + created + ", lastupdated=" + lastupdated
				+ ", unitCost=" + unitCost + ", discount=" + discount + ", tax=" + tax
				+ "]";
	}
	
}

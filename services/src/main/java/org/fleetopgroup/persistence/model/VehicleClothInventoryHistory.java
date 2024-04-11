package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleClothInventoryHistory")
public class VehicleClothInventoryHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleClothInventoryHistoryId")
	private Long vehicleClothInventoryHistoryId;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "clothTypesId")
	private Long 	clothTypesId;
	
	@Column(name = "quantity")
	private Double	quantity;
	
	@Column(name = "preAsignedQuantity")
	private Double	preAsignedQuantity;
	
	@Column(name = "createdById" , updatable = false)
	private Long	createdById;
	
	@Column(name = "createdOn", updatable = false)
	private Date	createdOn;
	
	@Column(name = "PreAsignedDate", updatable = false)
	private Date	PreAsignedDate;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;
	
	@Column(name = "asignType")
	private short	asignType;
	
	@Column(name = "locationId")
	private Integer	locationId;
	
	@Column(name = "stockTypeId")
	private short	stockTypeId;

	public Long getVehicleClothInventoryHistoryId() {
		return vehicleClothInventoryHistoryId;
	}

	public void setVehicleClothInventoryHistoryId(Long vehicleClothInventoryHistoryId) {
		this.vehicleClothInventoryHistoryId = vehicleClothInventoryHistoryId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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

	public short getAsignType() {
		return asignType;
	}

	public void setAsignType(short asignType) {
		this.asignType = asignType;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public short getStockTypeId() {
		return stockTypeId;
	}

	public void setStockTypeId(short stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public Double getPreAsignedQuantity() {
		return preAsignedQuantity;
	}

	public void setPreAsignedQuantity(Double preAsignedQuantity) {
		this.preAsignedQuantity = preAsignedQuantity;
	}

	public Date getPreAsignedDate() {
		return PreAsignedDate;
	}

	public void setPreAsignedDate(Date preAsignedDate) {
		PreAsignedDate = preAsignedDate;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clothTypesId == null) ? 0 : clothTypesId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
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
		VehicleClothInventoryHistory other = (VehicleClothInventoryHistory) obj;
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
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleClothInventoryHistory [vehicleClothInventoryHistoryId=" + vehicleClothInventoryHistoryId
				+ ", vid=" + vid + ", clothTypesId=" + clothTypesId + ", quantity=" + quantity + ", preAsignedQuantity="
				+ preAsignedQuantity + ", createdById=" + createdById + ", createdOn=" + createdOn + ", PreAsignedDate="
				+ PreAsignedDate + ", companyId=" + companyId + ", markForDelete=" + markForDelete + ", asignType="
				+ asignType + ", locationId=" + locationId + ", stockTypeId=" + stockTypeId + "]";
	}

	
	
	
}

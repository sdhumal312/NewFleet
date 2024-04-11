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
@Table(name="VehicleClothInventoryDetails")
public class VehicleClothInventoryDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleClothInventoryDetailsId")
	private Long	vehicleClothInventoryDetailsId;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "clothTypesId")
	private Long 	clothTypesId;
	
	@Column(name = "quantity")
	private Double	quantity;
	
	@Column(name = "locationId")
	private Integer	locationId;
	
	@Column(name = "createdById" , updatable = false)
	private Long	createdById;
	
	@Column(name = "lastModifiedById")
	private Long	lastModifiedById;
	
	@Column(name = "createdOn", updatable = false)
	private Date	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Date	lastModifiedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;

	public Long getVehicleClothInventoryDetailsId() {
		return vehicleClothInventoryDetailsId;
	}

	public void setVehicleClothInventoryDetailsId(Long vehicleClothInventoryDetailsId) {
		this.vehicleClothInventoryDetailsId = vehicleClothInventoryDetailsId;
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

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clothTypesId == null) ? 0 : clothTypesId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result
				+ ((vehicleClothInventoryDetailsId == null) ? 0 : vehicleClothInventoryDetailsId.hashCode());
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
		VehicleClothInventoryDetails other = (VehicleClothInventoryDetails) obj;
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
		if (vehicleClothInventoryDetailsId == null) {
			if (other.vehicleClothInventoryDetailsId != null)
				return false;
		} else if (!vehicleClothInventoryDetailsId.equals(other.vehicleClothInventoryDetailsId))
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
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleClothInventoryDetails [vehicleClothInventoryDetailsId=");
		builder.append(vehicleClothInventoryDetailsId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", locationId=");
		builder.append(locationId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}


	
}

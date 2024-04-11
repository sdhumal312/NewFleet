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
@Table(name="BatteryManufacturer")
public class BatteryManufacturer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batteryManufacturerId")
	private Long		batteryManufacturerId;
	
	@Column(name = "manufacturerName", length = 200)
	private String 		manufacturerName;
	
	@Column(name = "description", length = 200)
	private String		description;
	

	@Column(name = "createdBy", length = 200, nullable = false, updatable = false)
	private Long createdBy;

	@Column(name = "lastModifiedBy", length = 200)
	private Long lastModifiedBy;

	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Column(name = "creationOn", nullable = false, updatable = false)
	private Date creationOn;

	@Column(name = "lastModifiedOn", nullable = false)
	private Date lastModifiedOn;
	
	public BatteryManufacturer() {
		super();
	}

	public BatteryManufacturer(Long batteryManufacturerId, String manufacturerName, String description,
			Integer companyId, Long createdBy, Long lastModifiedBy, boolean markForDelete, Date creationOn,
			Date lastModifiedOn) {
		super();
		this.batteryManufacturerId = batteryManufacturerId;
		this.manufacturerName = manufacturerName;
		this.description = description;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.markForDelete = markForDelete;
		this.creationOn = creationOn;
		this.lastModifiedOn = lastModifiedOn;
	}
	
	
	

	public Long getBatteryManufacturerId() {
		return batteryManufacturerId;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public String getDescription() {
		return description;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setBatteryManufacturerId(Long batteryManufacturerId) {
		this.batteryManufacturerId = batteryManufacturerId;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batteryManufacturerId == null) ? 0 : batteryManufacturerId.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((manufacturerName == null) ? 0 : manufacturerName.hashCode());
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
		BatteryManufacturer other = (BatteryManufacturer) obj;
		if (batteryManufacturerId == null) {
			if (other.batteryManufacturerId != null)
				return false;
		} else if (!batteryManufacturerId.equals(other.batteryManufacturerId))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (manufacturerName == null) {
			if (other.manufacturerName != null)
				return false;
		} else if (!manufacturerName.equals(other.manufacturerName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatteryManufacturer [batteryManufacturerId=" + batteryManufacturerId + ", manufacturerName="
				+ manufacturerName + ", description=" + description + ", createdBy="
				+ createdBy + ", lastModifiedBy=" + lastModifiedBy + ", markForDelete=" + markForDelete
				+ ", creationOn=" + creationOn + ", lastModifiedOn=" + lastModifiedOn + "]";
	}

	
}

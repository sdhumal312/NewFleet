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
@Table(name = "BatteryCapacity")
public class BatteryCapacity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batteryCapacityId")
	private Long	batteryCapacityId;
	
	@Column(name = "batteryCapacity")
	private String	batteryCapacity;
	
	@Column(name = "description")
	private String		description;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name = "createdOn")
	private Date		createdOn;
	
	@Column(name = "lastModifiedOn")
	private Date		lastModifiedOn;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	
	public BatteryCapacity() {
		super();
	}

	public Long getBatteryCapacityId() {
		return batteryCapacityId;
	}

	public String getBatteryCapacity() {
		return batteryCapacity;
	}

	public String getDescription() {
		return description;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setBatteryCapacityId(Long batteryCapacityId) {
		this.batteryCapacityId = batteryCapacityId;
	}

	public void setBatteryCapacity(String batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	
	

	public BatteryCapacity(Long batteryCapacityId, String batteryCapacity, String description, Long createdById,
			Long lastModifiedById, Date createdOn, Date lastModifiedOn, boolean markForDelete) {
		super();
		this.batteryCapacityId = batteryCapacityId;
		this.batteryCapacity = batteryCapacity;
		this.description = description;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batteryCapacity == null) ? 0 : batteryCapacity.hashCode());
		result = prime * result + ((batteryCapacityId == null) ? 0 : batteryCapacityId.hashCode());
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
		BatteryCapacity other = (BatteryCapacity) obj;
		if (batteryCapacity == null) {
			if (other.batteryCapacity != null)
				return false;
		} else if (!batteryCapacity.equals(other.batteryCapacity))
			return false;
		if (batteryCapacityId == null) {
			if (other.batteryCapacityId != null)
				return false;
		} else if (!batteryCapacityId.equals(other.batteryCapacityId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatteryCapacity [batteryCapacityId=" + batteryCapacityId + ", batteryCapacity=" + batteryCapacity
				+ ", description=" + description + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", createdOn=" + createdOn + ", lastModifiedOn=" + lastModifiedOn
				+ ", markForDelete=" + markForDelete + "]";
	}
	
	
}

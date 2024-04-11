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
@Table(name="VehicleCostFixing")
public class VehicleCostFixing implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleCostFixingId")
	private Long 		vehicleCostFixingId;
	
	@Column(name = "vid")
	private Integer		vid;
	
	@Column(name = "costType")
	private short		costType;
	
	@Column(name = "costPerDay")
	private Double		costPerDay;
	
	@Column(name = "costPerKM")
	private Double		costPerKM;
	
	@Column(name = "tyreSubTypeId")
	private Integer 	tyreSubTypeId;
	
	@Column(name = "batteryTypeId")
	private Long 		batteryTypeId;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "createdById", updatable = false)
	private Long		createdById;
	
	@Column(name = "lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name = "createdOn", updatable = false)
	private Timestamp	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Timestamp	lastModifiedOn;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	public VehicleCostFixing() {
		super();
	}

	public VehicleCostFixing(Long vehicleCostFixingId, Integer vid, short costType, Double costPerDay, Double costPerKM,
			Integer tyreSubTypeId, Long batteryTypeId, Integer companyId, Long createdById, Long lastModifiedById,
			Timestamp createdOn, Timestamp lastModifiedOn, boolean markForDelete) {
		super();
		this.vehicleCostFixingId = vehicleCostFixingId;
		this.vid = vid;
		this.costType = costType;
		this.costPerDay = costPerDay;
		this.costPerKM = costPerKM;
		this.tyreSubTypeId = tyreSubTypeId;
		this.batteryTypeId = batteryTypeId;
		this.companyId = companyId;
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
		result = prime * result + ((batteryTypeId == null) ? 0 : batteryTypeId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + costType;
		result = prime * result + ((tyreSubTypeId == null) ? 0 : tyreSubTypeId.hashCode());
		result = prime * result + ((vehicleCostFixingId == null) ? 0 : vehicleCostFixingId.hashCode());
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
		VehicleCostFixing other = (VehicleCostFixing) obj;
		if (batteryTypeId == null) {
			if (other.batteryTypeId != null)
				return false;
		} else if (!batteryTypeId.equals(other.batteryTypeId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (costType != other.costType)
			return false;
		if (tyreSubTypeId == null) {
			if (other.tyreSubTypeId != null)
				return false;
		} else if (!tyreSubTypeId.equals(other.tyreSubTypeId))
			return false;
		if (vehicleCostFixingId == null) {
			if (other.vehicleCostFixingId != null)
				return false;
		} else if (!vehicleCostFixingId.equals(other.vehicleCostFixingId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	
	
	
	public Long getVehicleCostFixingId() {
		return vehicleCostFixingId;
	}

	public void setVehicleCostFixingId(Long vehicleCostFixingId) {
		this.vehicleCostFixingId = vehicleCostFixingId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public short getCostType() {
		return costType;
	}

	public void setCostType(short costType) {
		this.costType = costType;
	}

	public Double getCostPerDay() {
		return costPerDay;
	}

	public void setCostPerDay(Double costPerDay) {
		this.costPerDay = costPerDay;
	}

	public Double getCostPerKM() {
		return costPerKM;
	}

	public void setCostPerKM(Double costPerKM) {
		this.costPerKM = costPerKM;
	}

	public Integer getTyreSubTypeId() {
		return tyreSubTypeId;
	}

	public void setTyreSubTypeId(Integer tyreSubTypeId) {
		this.tyreSubTypeId = tyreSubTypeId;
	}

	public Long getBatteryTypeId() {
		return batteryTypeId;
	}

	public void setBatteryTypeId(Long batteryTypeId) {
		this.batteryTypeId = batteryTypeId;
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

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "VehicleCostFixing [vehicleCostFixingId=" + vehicleCostFixingId + ", vid=" + vid + ", costType="
				+ costType + ", costPerDay=" + costPerDay + ", costPerKM=" + costPerKM + ", tyreSubTypeId="
				+ tyreSubTypeId + ", batteryTypeId=" + batteryTypeId + ", companyId=" + companyId + ", createdById="
				+ createdById + ", lastModifiedById=" + lastModifiedById + ", createdOn=" + createdOn
				+ ", lastModifiedOn=" + lastModifiedOn + ", markForDelete=" + markForDelete + "]";
	}

	
	
}

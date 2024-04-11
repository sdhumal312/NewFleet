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
@Table(name = "VehicleBatteryLayout")
public class VehicleBatteryLayout  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "layoutId")
	private Long layoutId;

	@Column(name = "vid", nullable = false)
	private Integer vid;
	
	@Column(name = "noOfBattery", nullable = false)
	private Integer noOfBattery;

	@Column(name = "batteryCapacityId")
	private Long batteryCapacityId;
	
	@Column(name = "batteryId")
	private  Long batteryId;
	
	@Column(name = "layoutPosition")
	private short layoutPosition;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;
	
	@Column(name = "createdOn", updatable = false)
	private Timestamp createdOn;
	
	@Column(name = "batteryAsigned", nullable = false)
	private boolean batteryAsigned;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public VehicleBatteryLayout() {
		super();
	}
	

	public VehicleBatteryLayout(Long layoutId, Integer vid, Integer noOfBattery, Long batteryCapacity,
			Integer companyId, Long createdById, Timestamp createdOn, boolean markForDelete) {
		super();
		this.layoutId = layoutId;
		this.vid = vid;
		this.noOfBattery = noOfBattery;
		this.batteryCapacityId = batteryCapacity;
		this.companyId = companyId;
		this.createdById = createdById;
		this.createdOn = createdOn;
		this.markForDelete = markForDelete;
	}



	public Long getLayoutId() {
		return layoutId;
	}


	public Integer getVid() {
		return vid;
	}


	public Integer getNoOfBattery() {
		return noOfBattery;
	}


	public Long getBatteryCapacityId() {
		return batteryCapacityId;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public Long getCreatedById() {
		return createdById;
	}


	public Timestamp getCreatedOn() {
		return createdOn;
	}


	public void setLayoutId(Long layoutId) {
		this.layoutId = layoutId;
	}


	public void setVid(Integer vid) {
		this.vid = vid;
	}


	public void setNoOfBattery(Integer noOfBattery) {
		this.noOfBattery = noOfBattery;
	}


	public void setBatteryCapacityId(Long batteryCapacity) {
		this.batteryCapacityId = batteryCapacity;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}


	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}



	public Long getBatteryId() {
		return batteryId;
	}


	public short getLayoutPosition() {
		return layoutPosition;
	}


	public void setBatteryId(Long batteryId) {
		this.batteryId = batteryId;
	}


	public void setLayoutPosition(short layoutPosition) {
		this.layoutPosition = layoutPosition;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}



	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public boolean isBatteryAsigned() {
		return batteryAsigned;
	}


	public void setBatteryAsigned(boolean batteryAsigned) {
		this.batteryAsigned = batteryAsigned;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batteryCapacityId == null) ? 0 : batteryCapacityId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((layoutId == null) ? 0 : layoutId.hashCode());
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
		VehicleBatteryLayout other = (VehicleBatteryLayout) obj;
		if (batteryCapacityId == null) {
			if (other.batteryCapacityId != null)
				return false;
		} else if (!batteryCapacityId.equals(other.batteryCapacityId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (layoutId == null) {
			if (other.layoutId != null)
				return false;
		} else if (!layoutId.equals(other.layoutId))
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
		return "VehicleBatteryLayout [layoutId=" + layoutId + ", vid=" + vid + ", noOfBattery=" + noOfBattery
				+ ", batteryCapacityId=" + batteryCapacityId + ", companyId=" + companyId + ", createdById=" + createdById
				+ ", createdOn=" + createdOn + ", markForDelete=" + markForDelete + "]";
	}

}

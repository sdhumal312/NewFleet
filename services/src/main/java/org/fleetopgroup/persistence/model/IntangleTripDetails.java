package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IntangleTripDetails")
public class IntangleTripDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IntangleTripId")
	private Long IntangleTripId;
	
	@Column(name = "intangleVid")
	private String intangleVid;

	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "vehicleNumber")
	private String vehicleNumber;
	
	@Column(name = "mileage")
	private Double mileage;
	
	@Column(name = "distance")
	private Double distance;
	
	@Column(name = "fuelLiter")
	private Double fuelLiter;
	
	@Column(name = "duration")
	private Long duration;
	
	@Column(name = "tripDate")
	private Timestamp	tripDate;
	
	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public IntangleTripDetails() {
		super();
	}

	public IntangleTripDetails(Long intangleTripId, String intangleVid, Integer vid, String vehicleNumber,
			Double mileage, Double distance, Double fuelLiter, Long duration, Timestamp tripDate, Date creationDate,
			Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		IntangleTripId = intangleTripId;
		this.intangleVid = intangleVid;
		this.vid = vid;
		this.vehicleNumber = vehicleNumber;
		this.mileage = mileage;
		this.distance = distance;
		this.fuelLiter = fuelLiter;
		this.duration = duration;
		this.tripDate = tripDate;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IntangleTripId == null) ? 0 : IntangleTripId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((distance == null) ? 0 : distance.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((fuelLiter == null) ? 0 : fuelLiter.hashCode());
		result = prime * result + ((intangleVid == null) ? 0 : intangleVid.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((mileage == null) ? 0 : mileage.hashCode());
		result = prime * result + ((tripDate == null) ? 0 : tripDate.hashCode());
		result = prime * result + ((vehicleNumber == null) ? 0 : vehicleNumber.hashCode());
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
		IntangleTripDetails other = (IntangleTripDetails) obj;
		if (IntangleTripId == null) {
			if (other.IntangleTripId != null)
				return false;
		} else if (!IntangleTripId.equals(other.IntangleTripId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (fuelLiter == null) {
			if (other.fuelLiter != null)
				return false;
		} else if (!fuelLiter.equals(other.fuelLiter))
			return false;
		if (intangleVid == null) {
			if (other.intangleVid != null)
				return false;
		} else if (!intangleVid.equals(other.intangleVid))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (mileage == null) {
			if (other.mileage != null)
				return false;
		} else if (!mileage.equals(other.mileage))
			return false;
		if (tripDate == null) {
			if (other.tripDate != null)
				return false;
		} else if (!tripDate.equals(other.tripDate))
			return false;
		if (vehicleNumber == null) {
			if (other.vehicleNumber != null)
				return false;
		} else if (!vehicleNumber.equals(other.vehicleNumber))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	public Long getIntangleTripId() {
		return IntangleTripId;
	}

	public void setIntangleTripId(Long intangleTripId) {
		IntangleTripId = intangleTripId;
	}

	public String getIntangleVid() {
		return intangleVid;
	}

	public void setIntangleVid(String intangleVid) {
		this.intangleVid = intangleVid;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getFuelLiter() {
		return fuelLiter;
	}

	public void setFuelLiter(Double fuelLiter) {
		this.fuelLiter = fuelLiter;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Timestamp getTripDate() {
		return tripDate;
	}

	public void setTripDate(Timestamp tripDate) {
		this.tripDate = tripDate;
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

	@Override
	public String toString() {
		return "IntangleTripDetails [IntangleTripId=" + IntangleTripId + ", intangleVid=" + intangleVid + ", vid=" + vid
				+ ", vehicleNumber=" + vehicleNumber + ", mileage=" + mileage + ", distance=" + distance
				+ ", fuelLiter=" + fuelLiter + ", duration=" + duration + ", tripDate=" + tripDate + ", creationDate="
				+ creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + "]";
	}

	
	
		
	
	

	


}
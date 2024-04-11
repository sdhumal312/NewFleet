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
@Table(name = "IntangleFuelEntryDetails")
public class IntangleFuelEntryDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IntangleFuelEntryDetailsId")
	private Long IntangleFuelEntryDetailsId;
	
	@Column(name = "IntangleFuelApiId")
	private String IntangleFuelApiId;

	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;
	
	@Column(name = "FuelDate")
	private Timestamp	FuelDate;
	
	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "odometer")
	private Long odometer;
	
	@Column(name = "verified")
	private boolean verified;

	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedBy")
	private Long lastUpdatedBy;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public IntangleFuelEntryDetails() {
		super();
		
	}

	public IntangleFuelEntryDetails(Long intangleFuelEntryDetailsId, String intangleFuelApiId, String latitude,
			String longitude, Timestamp fuelDate, Integer vid, String address, String type, Double amount,
			Long odometer, boolean verified, Long createdById, Long lastUpdatedBy, Date creationDate,
			Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		IntangleFuelEntryDetailsId = intangleFuelEntryDetailsId;
		IntangleFuelApiId = intangleFuelApiId;
		this.latitude = latitude;
		this.longitude = longitude;
		FuelDate = fuelDate;
		this.vid = vid;
		this.address = address;
		this.type = type;
		this.amount = amount;
		this.odometer = odometer;
		this.verified = verified;
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
		result = prime * result + ((FuelDate == null) ? 0 : FuelDate.hashCode());
		result = prime * result + ((IntangleFuelApiId == null) ? 0 : IntangleFuelApiId.hashCode());
		result = prime * result + ((IntangleFuelEntryDetailsId == null) ? 0 : IntangleFuelEntryDetailsId.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((odometer == null) ? 0 : odometer.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (verified ? 1231 : 1237);
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
		IntangleFuelEntryDetails other = (IntangleFuelEntryDetails) obj;
		if (FuelDate == null) {
			if (other.FuelDate != null)
				return false;
		} else if (!FuelDate.equals(other.FuelDate))
			return false;
		if (IntangleFuelApiId == null) {
			if (other.IntangleFuelApiId != null)
				return false;
		} else if (!IntangleFuelApiId.equals(other.IntangleFuelApiId))
			return false;
		if (IntangleFuelEntryDetailsId == null) {
			if (other.IntangleFuelEntryDetailsId != null)
				return false;
		} else if (!IntangleFuelEntryDetailsId.equals(other.IntangleFuelEntryDetailsId))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
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
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
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
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (odometer == null) {
			if (other.odometer != null)
				return false;
		} else if (!odometer.equals(other.odometer))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (verified != other.verified)
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	public Long getIntangleFuelEntryDetailsId() {
		return IntangleFuelEntryDetailsId;
	}

	public void setIntangleFuelEntryDetailsId(Long intangleFuelEntryDetailsId) {
		IntangleFuelEntryDetailsId = intangleFuelEntryDetailsId;
	}

	public String getIntangleFuelApiId() {
		return IntangleFuelApiId;
	}

	public void setIntangleFuelApiId(String intangleFuelApiId) {
		IntangleFuelApiId = intangleFuelApiId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Timestamp getFuelDate() {
		return FuelDate;
	}

	public void setFuelDate(Timestamp fuelDate) {
		FuelDate = fuelDate;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getOdometer() {
		return odometer;
	}

	public void setOdometer(Long odometer) {
		this.odometer = odometer;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
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

	@Override
	public String toString() {
		return "IntangleFuelEntryDetails [IntangleFuelEntryDetailsId=" + IntangleFuelEntryDetailsId
				+ ", IntangleFuelApiId=" + IntangleFuelApiId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", FuelDate=" + FuelDate + ", vid=" + vid + ", address=" + address + ", type=" + type + ", amount="
				+ amount + ", odometer=" + odometer + ", verified=" + verified + ", createdById=" + createdById
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate=" + creationDate + ", lastUpdatedOn="
				+ lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}

	
	

	


}
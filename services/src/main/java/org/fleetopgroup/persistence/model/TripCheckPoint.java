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
@Table(name = "TripCheckPoint")
public class TripCheckPoint implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripCheckPointId")
	private Long tripCheckPointId;
	
	@Column(name = "tripRouteId")
	private Long tripRouteId;

	@Column(name = "tripCheckPointName")
	private String tripCheckPointName;
	
	@Column(name = "description")
	private String	description;

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

	public TripCheckPoint() {
		super();
		
	}

	public TripCheckPoint(Long tripCheckPointId, Long tripRouteId, String tripCheckPointName, String description,
			Long createdById, Long lastUpdatedBy, Date creationDate, Date lastUpdatedOn, Integer companyId,
			boolean markForDelete) {
		super();
		this.tripCheckPointId = tripCheckPointId;
		this.tripRouteId = tripRouteId;
		this.tripCheckPointName = tripCheckPointName;
		this.description = description;
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
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((tripCheckPointId == null) ? 0 : tripCheckPointId.hashCode());
		result = prime * result + ((tripCheckPointName == null) ? 0 : tripCheckPointName.hashCode());
		result = prime * result + ((tripRouteId == null) ? 0 : tripRouteId.hashCode());
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
		TripCheckPoint other = (TripCheckPoint) obj;
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		if (markForDelete != other.markForDelete)
			return false;
		if (tripCheckPointId == null) {
			if (other.tripCheckPointId != null)
				return false;
		} else if (!tripCheckPointId.equals(other.tripCheckPointId))
			return false;
		if (tripCheckPointName == null) {
			if (other.tripCheckPointName != null)
				return false;
		} else if (!tripCheckPointName.equals(other.tripCheckPointName))
			return false;
		if (tripRouteId == null) {
			if (other.tripRouteId != null)
				return false;
		} else if (!tripRouteId.equals(other.tripRouteId))
			return false;
		return true;
	}

	public Long getTripCheckPointId() {
		return tripCheckPointId;
	}

	public void setTripCheckPointId(Long tripCheckPointId) {
		this.tripCheckPointId = tripCheckPointId;
	}

	public Long getTripRouteId() {
		return tripRouteId;
	}

	public void setTripRouteId(Long tripRouteId) {
		this.tripRouteId = tripRouteId;
	}

	public String getTripCheckPointName() {
		return tripCheckPointName;
	}

	public void setTripCheckPointName(String tripCheckPointName) {
		this.tripCheckPointName = tripCheckPointName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "TripCheckPoint [tripCheckPointId=" + tripCheckPointId + ", tripRouteId=" + tripRouteId
				+ ", tripCheckPointName=" + tripCheckPointName + ", description=" + description + ", createdById="
				+ createdById + ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate=" + creationDate
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}

	


}
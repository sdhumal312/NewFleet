package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "TripCheckPointInspection")
public class TripCheckPointInspection implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripCheckPointInspectionId")
	private Long tripCheckPointInspectionId;
	
	@Column(name = "tripCheckPointId")
	private Long tripCheckPointId;
	
	@Column(name = "tripSheetId")
	private Long tripSheetId;
	
	@Column(name = "checkPointWayTypeId")
	private short checkPointWayTypeId;
	
	@Column(name = "tripCheckPointInspectionDate")
	private Date tripCheckPointInspectionDate;
	
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
	
    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "tripCheckPointInspection", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<TripCheckPointParameterInspection> tripCheckPointParameterInspectionList;
	
	public TripCheckPointInspection() {
		super();
		
	}

	public TripCheckPointInspection(Long tripCheckPointInspectionId, Long tripCheckPointId, Long tripSheetId,
			Long createdById, Long lastUpdatedBy, Date creationDate, Date lastUpdatedOn, Integer companyId,
			boolean markForDelete) {
		super();
		this.tripCheckPointInspectionId = tripCheckPointInspectionId;
		this.tripCheckPointId = tripCheckPointId;
		this.tripSheetId = tripSheetId;
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
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((tripCheckPointId == null) ? 0 : tripCheckPointId.hashCode());
		result = prime * result + ((tripCheckPointInspectionId == null) ? 0 : tripCheckPointInspectionId.hashCode());
		result = prime * result + ((tripSheetId == null) ? 0 : tripSheetId.hashCode());
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
		TripCheckPointInspection other = (TripCheckPointInspection) obj;
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
		if (markForDelete != other.markForDelete)
			return false;
		if (tripCheckPointId == null) {
			if (other.tripCheckPointId != null)
				return false;
		} else if (!tripCheckPointId.equals(other.tripCheckPointId))
			return false;
		if (tripCheckPointInspectionId == null) {
			if (other.tripCheckPointInspectionId != null)
				return false;
		} else if (!tripCheckPointInspectionId.equals(other.tripCheckPointInspectionId))
			return false;
		if (tripSheetId == null) {
			if (other.tripSheetId != null)
				return false;
		} else if (!tripSheetId.equals(other.tripSheetId))
			return false;
		return true;
	}

	public Long getTripCheckPointInspectionId() {
		return tripCheckPointInspectionId;
	}

	public void setTripCheckPointInspectionId(Long tripCheckPointInspectionId) {
		this.tripCheckPointInspectionId = tripCheckPointInspectionId;
	}

	public Long getTripCheckPointId() {
		return tripCheckPointId;
	}

	public void setTripCheckPointId(Long tripCheckPointId) {
		this.tripCheckPointId = tripCheckPointId;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public short getCheckPointWayTypeId() {
		return checkPointWayTypeId;
	}

	public void setCheckPointWayTypeId(short checkPointWayTypeId) {
		this.checkPointWayTypeId = checkPointWayTypeId;
	}

	public Date getTripCheckPointInspectionDate() {
		return tripCheckPointInspectionDate;
	}

	public void setTripCheckPointInspectionDate(Date tripCheckPointInspectionDate) {
		this.tripCheckPointInspectionDate = tripCheckPointInspectionDate;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
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
	
	public Set<TripCheckPointParameterInspection> getTripCheckPointParameterInspectionList() {
		return tripCheckPointParameterInspectionList;
	}

	public void setTripCheckPointParameterInspectionList(
			Set<TripCheckPointParameterInspection> tripCheckPointParameterInspectionList) {
		this.tripCheckPointParameterInspectionList = tripCheckPointParameterInspectionList;
	}

	@Override
	public String toString() {
		return "TripCheckPointInspection [tripCheckPointInspectionId=" + tripCheckPointInspectionId
				+ ", tripCheckPointId=" + tripCheckPointId + ", tripSheetId=" + tripSheetId + ", createdById="
				+ createdById + ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate=" + creationDate
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}

	
	


}
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "TripCheckPointParameterInspection")
public class TripCheckPointParameterInspection implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripCheckPointParameterInspectionId")
	private Long tripCheckPointParameterInspectionId;
	
	@Column(name = "tripCheckPointParameterId")
	private Long tripCheckPointParameterId;
	
	@Column(name = "checkPointParameterInspectStatusId")
	private boolean checkPointParameterInspectStatusId;

	@Basic(optional = false)
	@Column(name = "checkPointParameterPhoto", nullable = false)
	private boolean checkPointParameterPhoto = false;
	
	@Column(name = "remark")
	private String	remark;

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

	@ManyToOne
    @JoinColumn(name = "tripCheckPointInspectionId")
    @JsonBackReference
    private TripCheckPointInspection tripCheckPointInspection;
	
	public TripCheckPointParameterInspection() {
		super();
		
	}

	public TripCheckPointParameterInspection(Long tripCheckPointParameterInspectionId, Long tripCheckPointInspectionId,
			boolean checkPointParameterInspectStatusId, boolean checkPointParameterPhoto,
			Long checkPointParameterPhotoId, String remark, Long createdById, Long lastUpdatedBy, Date creationDate,
			Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.tripCheckPointParameterInspectionId = tripCheckPointParameterInspectionId;
		this.checkPointParameterInspectStatusId = checkPointParameterInspectStatusId;
		this.checkPointParameterPhoto = checkPointParameterPhoto;
		this.remark = remark;
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
		result = prime * result + (checkPointParameterInspectStatusId ? 1231 : 1237);
		result = prime * result + (checkPointParameterPhoto ? 1231 : 1237);
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((tripCheckPointInspection == null) ? 0 : tripCheckPointInspection.hashCode());
		result = prime * result + ((tripCheckPointParameterId == null) ? 0 : tripCheckPointParameterId.hashCode());
		result = prime * result
				+ ((tripCheckPointParameterInspectionId == null) ? 0 : tripCheckPointParameterInspectionId.hashCode());
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
		TripCheckPointParameterInspection other = (TripCheckPointParameterInspection) obj;
		if (checkPointParameterInspectStatusId != other.checkPointParameterInspectStatusId)
			return false;
		if (checkPointParameterPhoto != other.checkPointParameterPhoto)
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
		if (markForDelete != other.markForDelete)
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (tripCheckPointInspection == null) {
			if (other.tripCheckPointInspection != null)
				return false;
		} else if (!tripCheckPointInspection.equals(other.tripCheckPointInspection))
			return false;
		if (tripCheckPointParameterId == null) {
			if (other.tripCheckPointParameterId != null)
				return false;
		} else if (!tripCheckPointParameterId.equals(other.tripCheckPointParameterId))
			return false;
		if (tripCheckPointParameterInspectionId == null) {
			if (other.tripCheckPointParameterInspectionId != null)
				return false;
		} else if (!tripCheckPointParameterInspectionId.equals(other.tripCheckPointParameterInspectionId))
			return false;
		return true;
	}

	public Long getTripCheckPointParameterInspectionId() {
		return tripCheckPointParameterInspectionId;
	}

	public void setTripCheckPointParameterInspectionId(Long tripCheckPointParameterInspectionId) {
		this.tripCheckPointParameterInspectionId = tripCheckPointParameterInspectionId;
	}


	public Long getTripCheckPointParameterId() {
		return tripCheckPointParameterId;
	}

	public void setTripCheckPointParameterId(Long tripCheckPointParameterId) {
		this.tripCheckPointParameterId = tripCheckPointParameterId;
	}

	public boolean isCheckPointParameterInspectStatusId() {
		return checkPointParameterInspectStatusId;
	}

	public void setCheckPointParameterInspectStatusId(boolean checkPointParameterInspectStatusId) {
		this.checkPointParameterInspectStatusId = checkPointParameterInspectStatusId;
	}

	public boolean isCheckPointParameterPhoto() {
		return checkPointParameterPhoto;
	}

	public void setCheckPointParameterPhoto(boolean checkPointParameterPhoto) {
		this.checkPointParameterPhoto = checkPointParameterPhoto;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public TripCheckPointInspection getTripCheckPointInspection() {
		return tripCheckPointInspection;
	}

	public void setTripCheckPointInspection(TripCheckPointInspection tripCheckPointInspection) {
		this.tripCheckPointInspection = tripCheckPointInspection;
	}

	@Override
	public String toString() {
		return "TripCheckPointParameterInspection [tripCheckPointParameterInspectionId="
				+ tripCheckPointParameterInspectionId + ", tripCheckPointParameterId=" + tripCheckPointParameterId
				+ ", checkPointParameterInspectStatusId=" + checkPointParameterInspectStatusId
				+ ", checkPointParameterPhoto=" + checkPointParameterPhoto + ", remark=" + remark + ", createdById="
				+ createdById + ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate=" + creationDate
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", tripCheckPointInspection=" + tripCheckPointInspection + "]";
	}
	


	
	


}
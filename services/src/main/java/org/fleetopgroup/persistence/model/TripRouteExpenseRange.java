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
@Table(name = "TripRouteExpenseRange")
public class TripRouteExpenseRange implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripRouteExpenseRangeId")
	private Long tripRouteExpenseRangeId;

	@Column(name = "routeId")
	private Integer routeId;
	
	@Column(name = "expenseId")
	private Integer	expenseId;

	@Column(name = "expenseMaxLimt")
	private Double expenseMaxLimt;

	@Column(name = "createdBy")
	private Long createdBy;
	
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

	public TripRouteExpenseRange() {
		super();
	}

	public TripRouteExpenseRange(Long tripRouteExpenseRangeId, Integer routeId, Integer expenseId, Double expenseMaxLimt,
			Long createdBy, Long lastUpdatedBy, Date creationDate, Date lastUpdatedOn, Integer companyId,
			boolean markForDelete) {
		super();
		this.tripRouteExpenseRangeId = tripRouteExpenseRangeId;
		this.routeId = routeId;
		this.expenseId = expenseId;
		this.expenseMaxLimt = expenseMaxLimt;
		this.createdBy = createdBy;
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
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((expenseId == null) ? 0 : expenseId.hashCode());
		result = prime * result + ((expenseMaxLimt == null) ? 0 : expenseMaxLimt.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
		result = prime * result + ((tripRouteExpenseRangeId == null) ? 0 : tripRouteExpenseRangeId.hashCode());
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
		TripRouteExpenseRange other = (TripRouteExpenseRange) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (expenseId == null) {
			if (other.expenseId != null)
				return false;
		} else if (!expenseId.equals(other.expenseId))
			return false;
		if (expenseMaxLimt == null) {
			if (other.expenseMaxLimt != null)
				return false;
		} else if (!expenseMaxLimt.equals(other.expenseMaxLimt))
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
		if (routeId == null) {
			if (other.routeId != null)
				return false;
		} else if (!routeId.equals(other.routeId))
			return false;
		if (tripRouteExpenseRangeId == null) {
			if (other.tripRouteExpenseRangeId != null)
				return false;
		} else if (!tripRouteExpenseRangeId.equals(other.tripRouteExpenseRangeId))
			return false;
		return true;
	}

	public Long getTripRouteExpenseRangeId() {
		return tripRouteExpenseRangeId;
	}

	public void setTripRouteExpenseRangeId(Long tripRouteExpenseRangeId) {
		this.tripRouteExpenseRangeId = tripRouteExpenseRangeId;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public Double getExpenseMaxLimt() {
		return expenseMaxLimt;
	}

	public void setExpenseMaxLimt(Double expenseMaxLimt) {
		this.expenseMaxLimt = expenseMaxLimt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TripRouteExpenseRange [tripRouteExpenseRangeId=" + tripRouteExpenseRangeId + ", routeId=" + routeId
				+ ", expenseId=" + expenseId + ", expenseMaxLimt=" + expenseMaxLimt + ", createdBy=" + createdBy
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate=" + creationDate + ", lastUpdatedOn="
				+ lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}

	

}
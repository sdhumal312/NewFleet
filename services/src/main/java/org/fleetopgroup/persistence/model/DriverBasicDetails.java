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
@Table(name = "DriverBasicDetails")
public class DriverBasicDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driverBasicDetailsId")
	private Long driverBasicDetailsId;

	@Column(name = "driverId")
	private int driverId;
	
	@Column(name = "detailsTypeId")
	private Long detailsTypeId;
	
	@Column(name = "quantity")
	private Double quantity;
	
	@Column(name = "assignDate")
	private Date assignDate;
	
	@Column(name = "remark")
	private String	remark;

	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedById")
	private Long lastUpdatedById;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastUpdatedDate")
	private Date lastUpdatedDate;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public DriverBasicDetails() {
		super();
	}

	public DriverBasicDetails(Long driverBasicDetailsId, Long detailsTypeId, int driverId, Double quantity, Date assignDate,
			String remark, Long createdById, Long lastUpdatedById, Date creationDate, Date lastUpdatedDate,
			Integer companyId, boolean markForDelete) {
		super();
		this.driverBasicDetailsId = driverBasicDetailsId;
		this.detailsTypeId = detailsTypeId;
		this.driverId = driverId;
		this.quantity = quantity;
		this.assignDate = assignDate;
		this.remark = remark;
		this.createdById = createdById;
		this.lastUpdatedById = lastUpdatedById;
		this.creationDate = creationDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignDate == null) ? 0 : assignDate.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((detailsTypeId == null) ? 0 : detailsTypeId.hashCode());
		result = prime * result + ((driverBasicDetailsId == null) ? 0 : driverBasicDetailsId.hashCode());
		result = prime * result + driverId;
		result = prime * result + ((lastUpdatedById == null) ? 0 : lastUpdatedById.hashCode());
		result = prime * result + ((lastUpdatedDate == null) ? 0 : lastUpdatedDate.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		DriverBasicDetails other = (DriverBasicDetails) obj;
		if (assignDate == null) {
			if (other.assignDate != null)
				return false;
		} else if (!assignDate.equals(other.assignDate))
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
		if (detailsTypeId != other.detailsTypeId)
			return false;
		if (driverBasicDetailsId == null) {
			if (other.driverBasicDetailsId != null)
				return false;
		} else if (!driverBasicDetailsId.equals(other.driverBasicDetailsId))
			return false;
		if (driverId != other.driverId)
			return false;
		if (lastUpdatedById == null) {
			if (other.lastUpdatedById != null)
				return false;
		} else if (!lastUpdatedById.equals(other.lastUpdatedById))
			return false;
		if (lastUpdatedDate == null) {
			if (other.lastUpdatedDate != null)
				return false;
		} else if (!lastUpdatedDate.equals(other.lastUpdatedDate))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

	public Long getDriverBasicDetailsId() {
		return driverBasicDetailsId;
	}
	
	public int getDriverId() {
		return driverId;
	}

	public Long getDetailsTypeId() {
		return detailsTypeId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public String getRemark() {
		return remark;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setDriverBasicDetailsId(Long driverBasicDetailsId) {
		this.driverBasicDetailsId = driverBasicDetailsId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	
	public void setDetailsTypeId(Long detailsTypeId) {
		this.detailsTypeId = detailsTypeId;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "DriverBasicDetails [driverBasicDetailsId=" + driverBasicDetailsId + ", driverId=" + driverId
				+ ", detailsTypeId=" + detailsTypeId + ", quantity=" + quantity + ", assignDate=" + assignDate
				+ ", remark=" + remark + ", createdById=" + createdById + ", lastUpdatedById=" + lastUpdatedById
				+ ", creationDate=" + creationDate + ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}

	

	


}
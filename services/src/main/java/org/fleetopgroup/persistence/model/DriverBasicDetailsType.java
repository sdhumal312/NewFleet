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
@Table(name = "DriverBasicDetailsType")
public class DriverBasicDetailsType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driverBasicDetailsTypeId")
	private Long driverBasicDetailsTypeId;
	
	@Column(name = "driverBasicDetailsTypeName")
	private String	driverBasicDetailsTypeName;
	
	@Column(name = "description")
	private String	description;

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

	public DriverBasicDetailsType() {
		super();
	}

	public DriverBasicDetailsType(Long driverBasicDetailsTypeId, String driverBasicDetailsTypeName, String description,
			Long createdById, Long lastUpdatedById, Date creationDate, Date lastUpdatedDate, Integer companyId,
			boolean markForDelete) {
		super();
		this.driverBasicDetailsTypeId = driverBasicDetailsTypeId;
		this.driverBasicDetailsTypeName = driverBasicDetailsTypeName;
		this.description = description;
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
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((driverBasicDetailsTypeName == null) ? 0 : driverBasicDetailsTypeName.hashCode());
		result = prime * result + ((driverBasicDetailsTypeId == null) ? 0 : driverBasicDetailsTypeId.hashCode());
		result = prime * result + ((lastUpdatedById == null) ? 0 : lastUpdatedById.hashCode());
		result = prime * result + ((lastUpdatedDate == null) ? 0 : lastUpdatedDate.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
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
		DriverBasicDetailsType other = (DriverBasicDetailsType) obj;
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
		if (driverBasicDetailsTypeName == null) {
			if (other.driverBasicDetailsTypeName != null)
				return false;
		} else if (!driverBasicDetailsTypeName.equals(other.driverBasicDetailsTypeName))
			return false;
		if (driverBasicDetailsTypeId == null) {
			if (other.driverBasicDetailsTypeId != null)
				return false;
		} else if (!driverBasicDetailsTypeId.equals(other.driverBasicDetailsTypeId))
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
		return true;
	}

	public Long getDriverBasicDetailsTypeId() {
		return driverBasicDetailsTypeId;
	}

	public String getDriverBasicDetailsTypeName() {
		return driverBasicDetailsTypeName;
	}

	public String getDescription() {
		return description;
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

	public void setDriverBasicDetailsTypeId(Long driverBasicDetailsTypeId) {
		this.driverBasicDetailsTypeId = driverBasicDetailsTypeId;
	}

	public void setDriverBasicDetailsTypeName(String driverBasicDetailsTypeName) {
		this.driverBasicDetailsTypeName = driverBasicDetailsTypeName;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "DriverBasicDetailsType [driverBasicDetailsTypeId=" + driverBasicDetailsTypeId
				+ ", driverBasicDetailsTypeName=" + driverBasicDetailsTypeName + ", description=" + description
				+ ", createdById=" + createdById + ", lastUpdatedById=" + lastUpdatedById + ", creationDate="
				+ creationDate + ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}

	
	


}
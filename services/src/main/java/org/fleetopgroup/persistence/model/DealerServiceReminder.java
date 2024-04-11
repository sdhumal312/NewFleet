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
@Table(name = "DealerServiceReminder")
public class DealerServiceReminder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dealerServiceReminderId")
	private Long dealerServiceReminderId;
	
	@Column(name = "dealerServiceEntriesId", nullable = false)
	private Long dealerServiceEntriesId;

	@Column(name = "serviceId")
	private Long serviceId;

	@Column(name = "serviceTypeId")
	private Integer serviceTypeId;
	
	@Column(name = "serviceSubTypeId")
	private Integer serviceSubTypeId;
	
	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	
	public DealerServiceReminder() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DealerServiceReminder(Long dealerServiceReminderId, Long dealerServiceEntriesId, Long serviceId,
			Integer serviceTypeId, Integer serviceSubTypeId, Long createdById, Long lastModifiedById, Date creationOn,
			Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.dealerServiceReminderId = dealerServiceReminderId;
		this.dealerServiceEntriesId = dealerServiceEntriesId;
		this.serviceId = serviceId;
		this.serviceTypeId = serviceTypeId;
		this.serviceSubTypeId = serviceSubTypeId;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.creationOn = creationOn;
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
		result = prime * result + ((creationOn == null) ? 0 : creationOn.hashCode());
		result = prime * result + ((dealerServiceEntriesId == null) ? 0 : dealerServiceEntriesId.hashCode());
		result = prime * result + ((dealerServiceReminderId == null) ? 0 : dealerServiceReminderId.hashCode());
		result = prime * result + ((lastModifiedById == null) ? 0 : lastModifiedById.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((serviceSubTypeId == null) ? 0 : serviceSubTypeId.hashCode());
		result = prime * result + ((serviceTypeId == null) ? 0 : serviceTypeId.hashCode());
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
		DealerServiceReminder other = (DealerServiceReminder) obj;
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
		if (creationOn == null) {
			if (other.creationOn != null)
				return false;
		} else if (!creationOn.equals(other.creationOn))
			return false;
		if (dealerServiceEntriesId == null) {
			if (other.dealerServiceEntriesId != null)
				return false;
		} else if (!dealerServiceEntriesId.equals(other.dealerServiceEntriesId))
			return false;
		if (dealerServiceReminderId == null) {
			if (other.dealerServiceReminderId != null)
				return false;
		} else if (!dealerServiceReminderId.equals(other.dealerServiceReminderId))
			return false;
		if (lastModifiedById == null) {
			if (other.lastModifiedById != null)
				return false;
		} else if (!lastModifiedById.equals(other.lastModifiedById))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (serviceSubTypeId == null) {
			if (other.serviceSubTypeId != null)
				return false;
		} else if (!serviceSubTypeId.equals(other.serviceSubTypeId))
			return false;
		if (serviceTypeId == null) {
			if (other.serviceTypeId != null)
				return false;
		} else if (!serviceTypeId.equals(other.serviceTypeId))
			return false;
		return true;
	}


	public Long getDealerServiceReminderId() {
		return dealerServiceReminderId;
	}


	public void setDealerServiceReminderId(Long dealerServiceReminderId) {
		this.dealerServiceReminderId = dealerServiceReminderId;
	}


	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}


	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}


	public Long getServiceId() {
		return serviceId;
	}


	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}


	public Integer getServiceTypeId() {
		return serviceTypeId;
	}


	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}


	public Integer getServiceSubTypeId() {
		return serviceSubTypeId;
	}


	public void setServiceSubTypeId(Integer serviceSubTypeId) {
		this.serviceSubTypeId = serviceSubTypeId;
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


	public Date getCreationOn() {
		return creationOn;
	}


	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
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
		return "DealerServiceReminder [dealerServiceReminderId=" + dealerServiceReminderId + ", dealerServiceEntriesId="
				+ dealerServiceEntriesId + ", serviceId=" + serviceId + ", serviceTypeId=" + serviceTypeId
				+ ", serviceSubTypeId=" + serviceSubTypeId + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", creationOn=" + creationOn + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + "]";
	}

	

	
	

	
}

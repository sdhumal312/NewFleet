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
@Table(name = "DealerServiceExtraIssue")
public class DealerServiceExtraIssue implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dealerServiceExtraIssueId")
	private Long dealerServiceExtraIssueId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "dealerServiceEntriesId")
	private Long dealerServiceEntriesId;
	
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
	
	public DealerServiceExtraIssue() {
		super();
	}

	public DealerServiceExtraIssue(Long dealerServiceExtraIssueId, String description, Long createdById,
			Long lastModifiedById, Date creationOn, Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.dealerServiceExtraIssueId = dealerServiceExtraIssueId;
		this.description = description;
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
		result = prime * result + ((dealerServiceExtraIssueId == null) ? 0 : dealerServiceExtraIssueId.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((lastModifiedById == null) ? 0 : lastModifiedById.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
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
		DealerServiceExtraIssue other = (DealerServiceExtraIssue) obj;
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
		if (dealerServiceExtraIssueId == null) {
			if (other.dealerServiceExtraIssueId != null)
				return false;
		} else if (!dealerServiceExtraIssueId.equals(other.dealerServiceExtraIssueId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		return true;
	}

	public Long getDealerServiceExtraIssueId() {
		return dealerServiceExtraIssueId;
	}

	public void setDealerServiceExtraIssueId(Long dealerServiceExtraIssueId) {
		this.dealerServiceExtraIssueId = dealerServiceExtraIssueId;
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

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	@Override
	public String toString() {
		return "DealerServiceExtraIssue [dealerServiceExtraIssueId=" + dealerServiceExtraIssueId + ", description="
				+ description + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", creationOn=" + creationOn + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}

	

	
	

	
}

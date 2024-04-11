package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DealerServiceExtraIssueDto {

	private Long dealerServiceExtraIssueId;
	
	private String description;
	
	private Long dealerServiceEntriesId;
	
	private Long createdById;
	
	private String createdBy;
	
	private Long lastModifiedById;
	
	private String lastModifiedBy;
	
	private Date creationOn;
	
	private String creationDate;
	
	private Date lastUpdatedOn;
	
	private String lastUpdatedDate;
	
	private Integer companyId;
	
	private boolean markForDelete;

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

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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
	
	
}

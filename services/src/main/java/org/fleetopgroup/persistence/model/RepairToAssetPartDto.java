package org.fleetopgroup.persistence.model;

import java.util.Date;

public class RepairToAssetPartDto {

	private Long repairToAssetPartId;
	
	private Long assetId;
	
	private String assetNumber;
	
	private Long repairStockToPartDetailsId;

	private Long createdById;

	private Long lastModifiedById;

	private Date creationOn;

	private Date lastUpdatedOn;
	
	private Integer companyId;
	
	private boolean markForDelete;

	public Long getRepairToAssetPartId() {
		return repairToAssetPartId;
	}

	public void setRepairToAssetPartId(Long repairToAssetPartId) {
		this.repairToAssetPartId = repairToAssetPartId;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public Long getRepairStockToPartDetailsId() {
		return repairStockToPartDetailsId;
	}

	public void setRepairStockToPartDetailsId(Long repairStockToPartDetailsId) {
		this.repairStockToPartDetailsId = repairStockToPartDetailsId;
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
	
	
	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	@Override
	public String toString() {
		return "RepairToAssetPartDto [repairToAssetPartId=" + repairToAssetPartId + ", assetId=" + assetId
				+ ", repairStockToPartDetailsId=" + repairStockToPartDetailsId + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", creationOn=" + creationOn + ", lastUpdatedOn="
				+ lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
}

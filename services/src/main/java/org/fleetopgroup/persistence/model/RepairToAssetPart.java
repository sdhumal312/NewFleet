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
@Table(name = "RepairToAssetPart")
public class RepairToAssetPart implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repairToAssetPartId")
	private Long repairToAssetPartId;
	
	@Column(name = "assetId")
	private Long assetId;
	
	@Column(name = "repairStockToPartDetailsId")
	private Long repairStockToPartDetailsId;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RepairToAssetPart [repairToAssetPartId=" + repairToAssetPartId + ", assetId=" + assetId
				+ ", repairStockToPartDetailsId=" + repairStockToPartDetailsId + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", creationOn=" + creationOn + ", lastUpdatedOn="
				+ lastUpdatedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}

	

	
}

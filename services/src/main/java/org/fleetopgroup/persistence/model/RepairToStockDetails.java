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
@Table(name = "RepairToStockDetails")
public class RepairToStockDetails implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repairToStockDetailsId")
	private Long repairToStockDetailsId;
	
	@Column(name = "repairStockId")
	private Long repairStockId;
	
	@Column(name = "repairStockPartId")//part,battery,tyre id
	private Long repairStockPartId;
	
	@Column(name = "isRepairInWarranty")
	private boolean isRepairInWarranty;
	
	@Column(name = "repairStockStatusId")//inprocess, repaired, scraped
	private short repairStockStatusId;
	
	@Column(name = "workDetails")
	private String workDetails;
	
	@Column(name = "dateOfRemoved")// auto
	private Date dateOfRemoved;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "inventoryLocationPartId")
	private Long inventoryLocationPartId;
	
	@Column(name = "inventoryLocationId")
	private Long inventoryLocationId;
	
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
	
	@Column(name = "transferedInventoryId")
	private Long transferedInventoryId;

	public Long getRepairToStockDetailsId() {
		return repairToStockDetailsId;
	}

	public void setRepairToStockDetailsId(Long repairToStockDetailsId) {
		this.repairToStockDetailsId = repairToStockDetailsId;
	}

	public Long getRepairStockId() {
		return repairStockId;
	}

	public void setRepairStockId(Long repairStockId) {
		this.repairStockId = repairStockId;
	}

	public Long getRepairStockPartId() {
		return repairStockPartId;
	}

	public void setRepairStockPartId(Long repairStockPartId) {
		this.repairStockPartId = repairStockPartId;
	}

	public boolean isRepairInWarranty() {
		return isRepairInWarranty;
	}

	public void setRepairInWarranty(boolean isRepairInWarranty) {
		this.isRepairInWarranty = isRepairInWarranty;
	}

	public short getRepairStockStatusId() {
		return repairStockStatusId;
	}

	public void setRepairStockStatusId(short repairStockStatusId) {
		this.repairStockStatusId = repairStockStatusId;
	}

	public String getWorkDetails() {
		return workDetails;
	}

	public void setWorkDetails(String workDetails) {
		this.workDetails = workDetails;
	}

	public Date getDateOfRemoved() {
		return dateOfRemoved;
	}

	public void setDateOfRemoved(Date dateOfRemoved) {
		this.dateOfRemoved = dateOfRemoved;
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

	public Long getInventoryLocationId() {
		return inventoryLocationId;
	}

	public void setInventoryLocationId(Long inventoryLocationId) {
		this.inventoryLocationId = inventoryLocationId;
	}

	public Long getInventoryLocationPartId() {
		return inventoryLocationPartId;
	}

	public void setInventoryLocationPartId(Long inventoryLocationPartId) {
		this.inventoryLocationPartId = inventoryLocationPartId;
	}

	public Long getTransferedInventoryId() {
		return transferedInventoryId;
	}

	public void setTransferedInventoryId(Long transferedInventoryId) {
		this.transferedInventoryId = transferedInventoryId;
	}
	

}

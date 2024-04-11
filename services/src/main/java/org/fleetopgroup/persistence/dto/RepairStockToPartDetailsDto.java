package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class RepairStockToPartDetailsDto {

	private Long repairStockToPartDetailsId;
	
	private Long repairToStockDetailsId;
	
	private String repairToStockName;
	
	private Long partId;
	
	private String  partName;
	
	private Double quantity;
	
	private Double unitCost;
	
	private Double gst;
	
	private Double discount;
	
	private Double totalCost;
	
	private String remark;
	
	private Long inventoryLocationId;
	
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

	

	public Long getRepairStockToPartDetailsId() {
		return repairStockToPartDetailsId;
	}

	public void setRepairStockToPartDetailsId(Long repairStockToPartDetailsId) {
		this.repairStockToPartDetailsId = repairStockToPartDetailsId;
	}

	public Long getRepairToStockDetailsId() {
		return repairToStockDetailsId;
	}

	public void setRepairToStockDetailsId(Long repairToStockDetailsId) {
		this.repairToStockDetailsId = repairToStockDetailsId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
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

	public String getRepairToStockName() {
		return repairToStockName;
	}

	public void setRepairToStockName(String repairToStockName) {
		this.repairToStockName = repairToStockName;
	}

	public Long getInventoryLocationId() {
		return inventoryLocationId;
	}

	public void setInventoryLocationId(Long inventoryLocationId) {
		this.inventoryLocationId = inventoryLocationId;
	}

	@Override
	public String toString() {
		return "RepairStockToPartDetailsDto [repairStockToPartDetailsId=" + repairStockToPartDetailsId
				+ ", repairToStockDetailsId=" + repairToStockDetailsId + ", repairToStockName=" + repairToStockName
				+ ", partId=" + partId + ", partName=" + partName + ", quantity=" + quantity + ", unitCost=" + unitCost
				+ ", gst=" + gst + ", discount=" + discount + ", totalCost=" + totalCost + ", remark=" + remark
				+ ", inventoryLocationId=" + inventoryLocationId + ", createdById=" + createdById + ", createdBy="
				+ createdBy + ", lastModifiedById=" + lastModifiedById + ", lastModifiedBy=" + lastModifiedBy
				+ ", creationOn=" + creationOn + ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn
				+ ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + "]";
	}
	
	
	
}

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
@Table(name = "RepairStockToPartDetails")
public class RepairStockToPartDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repairStockToPartDetailsId")
	private Long repairStockToPartDetailsId;
	
	@Column(name = "repairToStockDetailsId")
	private Long repairToStockDetailsId;
	
	@Column(name = "partId")
	private Long partId;
	
	@Column(name = "quantity")
	private Double quantity;
	
	@Column(name = "unitCost")
	private Double unitCost;
	
	@Column(name = "gst")
	private Double gst;
	
	@Column(name = "discount")
	private Double discount;
	
	@Column(name = "totalCost")
	private Double totalCost;
	
	@Column(name = "remark")
	private String remark;
	
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

	public Long getInventoryLocationId() {
		return inventoryLocationId;
	}

	public void setInventoryLocationId(Long inventoryLocationId) {
		this.inventoryLocationId = inventoryLocationId;
	}

	
		
	


}

package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class PurchasePartForVehicleDto {


	private Long purchasePartForVehicleId;

	private Integer vid;
	
	private Long	purchaseOrderId;
	
	private Long	purchaseorderToPartId;
	
	private Double	partQuantity;
	
	private Long createdById;

	private Long lastUpdatedBy;

	private Date creationDate;

	private Date lastUpdatedOn;
	
	private Integer companyId;
	
	private boolean markForDelete;
	
	private String vehicleRegistration;

	public PurchasePartForVehicleDto() {
		super();
	}

	public Long getPurchasePartForVehicleId() {
		return purchasePartForVehicleId;
	}

	public void setPurchasePartForVehicleId(Long purchasePartForVehicleId) {
		this.purchasePartForVehicleId = purchasePartForVehicleId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public Long getPurchaseorderToPartId() {
		return purchaseorderToPartId;
	}

	public void setPurchaseorderToPartId(Long purchaseorderToPartId) {
		this.purchaseorderToPartId = purchaseorderToPartId;
	}

	public Double getPartQuantity() {
		return partQuantity;
	}

	public void setPartQuantity(Double partQuantity) {
		this.partQuantity = Utility.round(partQuantity, 2);
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	
	public String getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(String vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}

	@Override
	public String toString() {
		return "PurchasePartForVehicleDto [purchasePartForVehicleId=" + purchasePartForVehicleId + ", vid=" + vid
				+ ", purchaseOrderId=" + purchaseOrderId + ", purchaseorderToPartId=" + purchaseorderToPartId
				+ ", partQuantity=" + partQuantity + ", createdById=" + createdById + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}

	

	

}

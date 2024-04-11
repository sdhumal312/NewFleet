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
@Table(name = "PurchasePartForVehicle")
public class PurchasePartForVehicle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchasePartForVehicleId")
	private Long purchasePartForVehicleId;

	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "purchaseOrderId")
	private Long	purchaseOrderId;
	
	@Column(name = "purchaseorderToPartId")
	private Long	purchaseorderToPartId;
	
	@Column(name = "partQuantity")
	private Double	partQuantity;
	
	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedBy")
	private Long lastUpdatedBy;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public PurchasePartForVehicle() {
		super();
	}

	public PurchasePartForVehicle(Long purchasePartForVehicleId, Integer vid, Long purchaseOrderId,
			Long purchaseorderToPartId, Double partQuantity, Long createdById, Long lastUpdatedBy, Date creationDate,
			Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.purchasePartForVehicleId = purchasePartForVehicleId;
		this.vid = vid;
		this.purchaseOrderId = purchaseOrderId;
		this.purchaseorderToPartId = purchaseorderToPartId;
		this.partQuantity = partQuantity;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
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
		this.partQuantity = partQuantity;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "PurchasePartForVehicle [purchasePartForVehicleId=" + purchasePartForVehicleId + ", vid=" + vid
				+ ", purchaseOrderId=" + purchaseOrderId + ", purchaseorderToPartId=" + purchaseorderToPartId
				+ ", partQuantity=" + partQuantity + ", createdById=" + createdById + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}

	

	


}
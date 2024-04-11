package org.fleetopgroup.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PartWarrantyDetailsHistory")
public class PartWarrantyDetailsHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	warrantyDetailsHistoryId;
	
	@Column(name = "partWarrantyDetailsId")
	private Long	partWarrantyDetailsId;
	
	@Column(name = "partId")
	private Long	partId;
	
	@Column(name = "inventoryId")
	private Long inventoryId;
	
	@Column(name = "partSerialNumber")
	private String	partSerialNumber;
	
	@Column(name = "partAsignDate")
	private Date	partAsignDate;
	
	@Column(name = "warrantyEndDate")
	private Date	warrantyEndDate;
	
	@Column(name = "status")
	private	short	status;
	
	@Column(name = "locationId")
	private Integer locationId;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "created", updatable = false)
	private Date	created;
	
	
	@Column(name = "createdById", updatable = false)
	private Long	createdById;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "serviceId")
	private Long	serviceId;
	
	@Column(name = "servicePartId")
	private Long	servicePartId;
	
	@Column(name = "replaceInServiceId")
	private Long	replaceInServiceId;
	
	@Column(name = "replacePartWarrantyDetailsId")
	private Long	replacePartWarrantyDetailsId;
	
	@Column(name = "partWarrantyStatusId")
	private	short	partWarrantyStatusId;
	
	@Column(name = "assignedFrom", nullable = false)
	private short	assignedFrom;
	
	@Column(name = "historyTypeId", nullable = false)
	private short	historyTypeId;

	public Long getWarrantyDetailsHistoryId() {
		return warrantyDetailsHistoryId;
	}

	public void setWarrantyDetailsHistoryId(Long warrantyDetailsHistoryId) {
		this.warrantyDetailsHistoryId = warrantyDetailsHistoryId;
	}

	public Long getPartWarrantyDetailsId() {
		return partWarrantyDetailsId;
	}

	public void setPartWarrantyDetailsId(Long partWarrantyDetailsId) {
		this.partWarrantyDetailsId = partWarrantyDetailsId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getPartSerialNumber() {
		return partSerialNumber;
	}

	public void setPartSerialNumber(String partSerialNumber) {
		this.partSerialNumber = partSerialNumber;
	}

	public Date getPartAsignDate() {
		return partAsignDate;
	}

	public void setPartAsignDate(Date partAsignDate) {
		this.partAsignDate = partAsignDate;
	}

	public Date getWarrantyEndDate() {
		return warrantyEndDate;
	}

	public void setWarrantyEndDate(Date warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}


	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getServicePartId() {
		return servicePartId;
	}

	public void setServicePartId(Long servicePartId) {
		this.servicePartId = servicePartId;
	}

	public Long getReplaceInServiceId() {
		return replaceInServiceId;
	}

	public void setReplaceInServiceId(Long replaceInServiceId) {
		this.replaceInServiceId = replaceInServiceId;
	}

	public Long getReplacePartWarrantyDetailsId() {
		return replacePartWarrantyDetailsId;
	}

	public void setReplacePartWarrantyDetailsId(Long replacePartWarrantyDetailsId) {
		this.replacePartWarrantyDetailsId = replacePartWarrantyDetailsId;
	}

	public short getPartWarrantyStatusId() {
		return partWarrantyStatusId;
	}

	public void setPartWarrantyStatusId(short partWarrantyStatusId) {
		this.partWarrantyStatusId = partWarrantyStatusId;
	}

	public short getAssignedFrom() {
		return assignedFrom;
	}

	public void setAssignedFrom(short assignedFrom) {
		this.assignedFrom = assignedFrom;
	}

	public short getHistoryTypeId() {
		return historyTypeId;
	}

	public void setHistoryTypeId(short historyTypeId) {
		this.historyTypeId = historyTypeId;
	}
	
	
}

package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PartWarrantyDetailsDto {
	
	private Long	partWarrantyDetailsId;
	
	private Long	partId;
	
	private String	partName;
	
	private String	partNumber;
	
	private Long 	inventoryId;
	
	private String	partSerialNumber;
	
	private Date	partAsignDate;

	private String	partAsignDateStr;
	
	private Date	warrantyEndDate;
	
	private String	warrantyEndDateStr;
	
	private	short	statusId;

	private	String	status;
	
	private Integer locationId;
	
	private Integer	companyId;
	
	private Date	created;
	
	private Date	lastUpdated;
	
	private Long	createdById;
	
	private Long	lastUpdatedById;
	
	private boolean	markForDelete;
	
	private Integer	vid;
	
	private String	vehicleRegistration;
	
	private Long	serviceId;
	
	private String	serviceNumber;
	
	private Long	replaceInServiceId;
	
	private String	replaceInServiceNumber;
	
	private Long	replacePartWarrantyDetailsId;
	
	private String	replacePartSerialNumber;
	
	private boolean	isOldPartReceived;
	
	private String	isOldPartReceivedStr;
	
	public short repairStatusId;

	public String repairStatus;

	public PartWarrantyDetailsDto() {
		super();
	}

	public PartWarrantyDetailsDto(Long partWarrantyDetailsId, Long partId, String partName, String partNumber,
			Long inventoryId, String partSerialNumber, Date partAsignDate, String partAsignDateStr,
			Date warrantyEndDate, String warrantyEndDateStr, short statusId, String status, Integer locationId,
			Integer companyId, Date created, Date lastUpdated, Long createdById, Long lastUpdatedById,
			boolean markForDelete, Integer vid, String vehicleRegistration, Long serviceId, String serviceNumber,
			Long replaceInServiceId, String replaceInServiceNumber) {
		super();
		this.partWarrantyDetailsId = partWarrantyDetailsId;
		this.partId = partId;
		this.partName = partName;
		this.partNumber = partNumber;
		this.inventoryId = inventoryId;
		this.partSerialNumber = partSerialNumber;
		this.partAsignDate = partAsignDate;
		this.partAsignDateStr = partAsignDateStr;
		this.warrantyEndDate = warrantyEndDate;
		this.warrantyEndDateStr = warrantyEndDateStr;
		this.statusId = statusId;
		this.status = status;
		this.locationId = locationId;
		this.companyId = companyId;
		this.created = created;
		this.lastUpdated = lastUpdated;
		this.createdById = createdById;
		this.lastUpdatedById = lastUpdatedById;
		this.markForDelete = markForDelete;
		this.vid = vid;
		this.vehicleRegistration = vehicleRegistration;
		this.serviceId = serviceId;
		this.serviceNumber = serviceNumber;
		this.replaceInServiceId = replaceInServiceId;
		this.replaceInServiceNumber = replaceInServiceNumber;
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

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
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

	public String getPartAsignDateStr() {
		return partAsignDateStr;
	}

	public void setPartAsignDateStr(String partAsignDateStr) {
		this.partAsignDateStr = partAsignDateStr;
	}

	public Date getWarrantyEndDate() {
		return warrantyEndDate;
	}

	public void setWarrantyEndDate(Date warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
	}

	public String getWarrantyEndDateStr() {
		return warrantyEndDateStr;
	}

	public void setWarrantyEndDateStr(String warrantyEndDateStr) {
		this.warrantyEndDateStr = warrantyEndDateStr;
	}

	public short getStatusId() {
		return statusId;
	}

	public void setStatusId(short statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
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

	public String getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(String vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public Long getReplaceInServiceId() {
		return replaceInServiceId;
	}

	public void setReplaceInServiceId(Long replaceInServiceId) {
		this.replaceInServiceId = replaceInServiceId;
	}

	public String getReplaceInServiceNumber() {
		return replaceInServiceNumber;
	}

	public void setReplaceInServiceNumber(String replaceInServiceNumber) {
		this.replaceInServiceNumber = replaceInServiceNumber;
	}

	public Long getReplacePartWarrantyDetailsId() {
		return replacePartWarrantyDetailsId;
	}

	public void setReplacePartWarrantyDetailsId(Long replacePartWarrantyDetailsId) {
		this.replacePartWarrantyDetailsId = replacePartWarrantyDetailsId;
	}

	public String getReplacePartSerialNumber() {
		return replacePartSerialNumber;
	}

	public void setReplacePartSerialNumber(String replacePartSerialNumber) {
		this.replacePartSerialNumber = replacePartSerialNumber;
	}

	public boolean isOldPartReceived() {
		return isOldPartReceived;
	}

	public void setOldPartReceived(boolean isOldPartReceived) {
		this.isOldPartReceived = isOldPartReceived;
	}

	public String getIsOldPartReceivedStr() {
		return isOldPartReceivedStr;
	}

	public void setIsOldPartReceivedStr(String isOldPartReceivedStr) {
		this.isOldPartReceivedStr = isOldPartReceivedStr;
	}
	
	public short getRepairStatusId() {
		return repairStatusId;
	}

	public void setRepairStatusId(short repairStatusId) {
		this.repairStatusId = repairStatusId;
	}

	public String getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}

	@Override
	public String toString() {
		return "PartWarrantyDetailsDto [partWarrantyDetailsId=" + partWarrantyDetailsId + ", partId=" + partId
				+ ", partName=" + partName + ", partNumber=" + partNumber + ", inventoryId=" + inventoryId
				+ ", partSerialNumber=" + partSerialNumber + ", partAsignDate=" + partAsignDate + ", partAsignDateStr="
				+ partAsignDateStr + ", warrantyEndDate=" + warrantyEndDate + ", warrantyEndDateStr="
				+ warrantyEndDateStr + ", statusId=" + statusId + ", status=" + status + ", locationId=" + locationId
				+ ", companyId=" + companyId + ", created=" + created + ", lastUpdated=" + lastUpdated
				+ ", createdById=" + createdById + ", lastUpdatedById=" + lastUpdatedById + ", markForDelete="
				+ markForDelete + ", vid=" + vid + ", vehicleRegistration=" + vehicleRegistration + ", serviceId="
				+ serviceId + ", serviceNumber=" + serviceNumber + ", replaceInServiceId=" + replaceInServiceId
				+ ", replaceInServiceNumber=" + replaceInServiceNumber + ", replacePartWarrantyDetailsId="
				+ replacePartWarrantyDetailsId + ", replacePartSerialNumber=" + replacePartSerialNumber
				+ ", isOldPartReceived=" + isOldPartReceived + ", isOldPartReceivedStr=" + isOldPartReceivedStr + "]";
	}

	

	
	
	
}

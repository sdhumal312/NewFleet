package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class RepairToStockDetailsDto {

	private Long repairStockId;
	
	private Long repairNumber;

	private String repairNumberStr;
	
	private String repairType;
	
	private short repairTypeId;
	
	private short repairWorkshopId;
	
	private String repairWorkshop;
	
	private Integer vendorId;
	
	private String vendorName;

	private short repairStatusId;
	
	private String repairStatus;
	
	private String refNumber;
	
	private Double totalRepairingCost;
	
	private Date openDate;
	
	private String openDateStr;
	
	private Date requiredDate;
	
	private String requiredDateStr;
	
	private String description;
	
	private Long repairToStockDetailsId;
	
	private Long repairStockPartId;
	
	private String repairToStockName;
	
	private String repairStockPart;
	
	private String repairStockBattery;
	
	private String repairStockTyre;
	
	private boolean isRepairInWarranty;
	
	private String repairInWarrantyStatus;
	
	private short repairStockStatusId;
	
	private String repairStockStatus;
	
	private String workDetails;
	
	private Date dateOfRemoved;
	
	private String dateOfRemovedStr;
	
	private Date dateOfSent;
	
	private String dateOfSentStr;
	
	private Date dateOfReceived;
	
	private String dateOfReceivedStr;
	
	private Long assignToId;
	
	private String remark;
	
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
	
	private Long inventoryLocationPartId;
	
	private Long inventoryLocationId;
	
	private int	serialNoAddedForParts;
	
	private Long transferedInventoryId;
	
	private String additionalPartDetails;
	
	private Integer additionalPartLocationId;
	
	private String additionalPartLocation;
	
	private String labourDetails;

	public Long getRepairStockId() {
		return repairStockId;
	}

	public void setRepairStockId(Long repairStockId) {
		this.repairStockId = repairStockId;
	}

	public Long getRepairNumber() {
		return repairNumber;
	}

	public void setRepairNumber(Long repairNumber) {
		this.repairNumber = repairNumber;
	}

	public String getRepairNumberStr() {
		return repairNumberStr;
	}

	public void setRepairNumberStr(String repairNumberStr) {
		this.repairNumberStr = repairNumberStr;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public short getRepairTypeId() {
		return repairTypeId;
	}

	public void setRepairTypeId(short repairTypeId) {
		this.repairTypeId = repairTypeId;
	}

	public short getRepairWorkshopId() {
		return repairWorkshopId;
	}

	public void setRepairWorkshopId(short repairWorkshopId) {
		this.repairWorkshopId = repairWorkshopId;
	}

	public String getRepairWorkshop() {
		return repairWorkshop;
	}

	public void setRepairWorkshop(String repairWorkshop) {
		this.repairWorkshop = repairWorkshop;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public Double getTotalRepairingCost() {
		return totalRepairingCost;
	}

	public void setTotalRepairingCost(Double totalRepairingCost) {
		this.totalRepairingCost = totalRepairingCost;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getOpenDateStr() {
		return openDateStr;
	}

	public void setOpenDateStr(String openDateStr) {
		this.openDateStr = openDateStr;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	public String getRequiredDateStr() {
		return requiredDateStr;
	}

	public void setRequiredDateStr(String requiredDateStr) {
		this.requiredDateStr = requiredDateStr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Long getRepairToStockDetailsId() {
		return repairToStockDetailsId;
	}

	public void setRepairToStockDetailsId(Long repairToStockDetailsId) {
		this.repairToStockDetailsId = repairToStockDetailsId;
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

	public String getRepairStockStatus() {
		return repairStockStatus;
	}

	public void setRepairStockStatus(String repairStockStatus) {
		this.repairStockStatus = repairStockStatus;
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

	public String getDateOfRemovedStr() {
		return dateOfRemovedStr;
	}

	public void setDateOfRemovedStr(String dateOfRemovedStr) {
		this.dateOfRemovedStr = dateOfRemovedStr;
	}

	public Date getDateOfSent() {
		return dateOfSent;
	}

	public void setDateOfSent(Date dateOfSent) {
		this.dateOfSent = dateOfSent;
	}

	public String getDateOfSentStr() {
		return dateOfSentStr;
	}

	public void setDateOfSentStr(String dateOfSentStr) {
		this.dateOfSentStr = dateOfSentStr;
	}

	public Date getDateOfReceived() {
		return dateOfReceived;
	}

	public void setDateOfReceived(Date dateOfReceived) {
		this.dateOfReceived = dateOfReceived;
	}

	public String getDateOfReceivedStr() {
		return dateOfReceivedStr;
	}

	public void setDateOfReceivedStr(String dateOfReceivedStr) {
		this.dateOfReceivedStr = dateOfReceivedStr;
	}

	public Long getAssignToId() {
		return assignToId;
	}

	public void setAssignToId(Long assignToId) {
		this.assignToId = assignToId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRepairStockPart() {
		return repairStockPart;
	}

	public void setRepairStockPart(String repairStockPart) {
		this.repairStockPart = repairStockPart;
	}

	public String getRepairStockBattery() {
		return repairStockBattery;
	}

	public void setRepairStockBattery(String repairStockBattery) {
		this.repairStockBattery = repairStockBattery;
	}

	public String getRepairStockTyre() {
		return repairStockTyre;
	}

	public void setRepairStockTyre(String repairStockTyre) {
		this.repairStockTyre = repairStockTyre;
	}

	public String getRepairInWarrantyStatus() {
		return repairInWarrantyStatus;
	}

	public void setRepairInWarrantyStatus(String repairInWarrantyStatus) {
		this.repairInWarrantyStatus = repairInWarrantyStatus;
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

	public Long getInventoryLocationPartId() {
		return inventoryLocationPartId;
	}

	public void setInventoryLocationPartId(Long inventoryLocationPartId) {
		this.inventoryLocationPartId = inventoryLocationPartId;
	}

	public int getSerialNoAddedForParts() {
		return serialNoAddedForParts;
	}

	public void setSerialNoAddedForParts(int serialNoAddedForParts) {
		this.serialNoAddedForParts = serialNoAddedForParts;
	}

	public Long getTransferedInventoryId() {
		return transferedInventoryId;
	}

	public void setTransferedInventoryId(Long transferedInventoryId) {
		this.transferedInventoryId = transferedInventoryId;
	}

	public String getAdditionalPartDetails() {
		return additionalPartDetails;
	}

	public void setAdditionalPartDetails(String additionalPartDetails) {
		this.additionalPartDetails = additionalPartDetails;
	}

	public String getLabourDetails() {
		return labourDetails;
	}

	public void setLabourDetails(String labourDetails) {
		this.labourDetails = labourDetails;
	}
	
	public Integer getAdditionalPartLocationId() {
		return additionalPartLocationId;
	}

	public void setAdditionalPartLocationId(Integer additionalPartLocationId) {
		this.additionalPartLocationId = additionalPartLocationId;
	}

	public String getAdditionalPartLocation() {
		return additionalPartLocation;
	}

	public void setAdditionalPartLocation(String additionalPartLocation) {
		this.additionalPartLocation = additionalPartLocation;
	}

	@Override
	public String toString() {
		return "RepairToStockDetailsDto [repairStockId=" + repairStockId + ", repairNumber=" + repairNumber
				+ ", repairNumberStr=" + repairNumberStr + ", repairType=" + repairType + ", repairTypeId="
				+ repairTypeId + ", repairWorkshopId=" + repairWorkshopId + ", repairWorkshop=" + repairWorkshop
				+ ", vendorId=" + vendorId + ", vendorName=" + vendorName + ", repairStatusId=" + repairStatusId
				+ ", repairStatus=" + repairStatus + ", refNumber=" + refNumber + ", totalRepairingCost="
				+ totalRepairingCost + ", openDate=" + openDate + ", openDateStr=" + openDateStr + ", requiredDate="
				+ requiredDate + ", requiredDateStr=" + requiredDateStr + ", description=" + description
				+ ", repairToStockDetailsId=" + repairToStockDetailsId + ", repairStockPartId=" + repairStockPartId
				+ ", repairToStockName=" + repairToStockName + ", repairStockPart=" + repairStockPart
				+ ", repairStockBattery=" + repairStockBattery + ", repairStockTyre=" + repairStockTyre
				+ ", isRepairInWarranty=" + isRepairInWarranty + ", repairInWarrantyStatus=" + repairInWarrantyStatus
				+ ", repairStockStatusId=" + repairStockStatusId + ", repairStockStatus=" + repairStockStatus
				+ ", workDetails=" + workDetails + ", dateOfRemoved=" + dateOfRemoved + ", dateOfRemovedStr="
				+ dateOfRemovedStr + ", dateOfSent=" + dateOfSent + ", dateOfSentStr=" + dateOfSentStr
				+ ", dateOfReceived=" + dateOfReceived + ", dateOfReceivedStr=" + dateOfReceivedStr + ", assignToId="
				+ assignToId + ", remark=" + remark + ", createdById=" + createdById + ", createdBy=" + createdBy
				+ ", lastModifiedById=" + lastModifiedById + ", lastModifiedBy=" + lastModifiedBy + ", creationOn="
				+ creationOn + ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn
				+ ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + ", inventoryLocationPartId=" + inventoryLocationPartId + ", inventoryLocationId="
				+ inventoryLocationId + ", serialNoAddedForParts=" + serialNoAddedForParts + ", transferedInventoryId="
				+ transferedInventoryId + ", additionalPartDetails=" + additionalPartDetails + ", labourDetails="
				+ labourDetails + "]";
	}

	

}

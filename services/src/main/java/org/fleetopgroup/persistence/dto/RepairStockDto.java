package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class RepairStockDto {

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
	
	private Date dateOfSent;
	
	private String dateOfSentStr;
	
	private Date completedDate;
	
	private String completedDateStr;
	
	private String description;
	
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
	
	private Integer locationId;
	
	private String location;
	
	private short partDiscountTaxTypeId;

	private short labourDiscountTaxTypeId;

	private String partDiscountTaxType;

	private String labourDiscountTaxType;
	
	private Integer additionalPartLocationId;
	
	private String additionalPartLocation;
	
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

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getCompletedDateStr() {
		return completedDateStr;
	}

	public void setCompletedDateStr(String completedDateStr) {
		this.completedDateStr = completedDateStr;
	}

	public short getPartDiscountTaxTypeId() {
		return partDiscountTaxTypeId;
	}

	public void setPartDiscountTaxTypeId(short partDiscountTaxTypeId) {
		this.partDiscountTaxTypeId = partDiscountTaxTypeId;
	}

	public short getLabourDiscountTaxTypeId() {
		return labourDiscountTaxTypeId;
	}

	public void setLabourDiscountTaxTypeId(short labourDiscountTaxTypeId) {
		this.labourDiscountTaxTypeId = labourDiscountTaxTypeId;
	}

	public String getPartDiscountTaxType() {
		return partDiscountTaxType;
	}

	public void setPartDiscountTaxType(String partDiscountTaxType) {
		this.partDiscountTaxType = partDiscountTaxType;
	}

	public String getLabourDiscountTaxType() {
		return labourDiscountTaxType;
	}

	public void setLabourDiscountTaxType(String labourDiscountTaxType) {
		this.labourDiscountTaxType = labourDiscountTaxType;
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
	
	
}

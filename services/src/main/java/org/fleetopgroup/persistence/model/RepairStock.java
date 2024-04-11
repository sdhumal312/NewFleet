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
@Table(name = "RepairStock")
public class RepairStock implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repairStockId")
	private Long repairStockId;
	
	@Column(name = "repairNumber")
	private Long repairNumber;
	
	@Column(name = "repairTypeId")
	private short repairTypeId;
	
	@Column(name = "repairWorkshopId")// Inhouse Repair/ Outside Repair
	private short repairWorkshopId;
	
	@Column(name = "vendorId")
	private Integer vendorId;

	@Column(name = "repairStatusId")
	private short repairStatusId;
	
	@Column(name = "refNumber", length = 50)
	private String refNumber;
	
	@Column(name = "totalRepairingCost")
	private Double totalRepairingCost;
	
	@Column(name = "openDate")
	private Date openDate;
	
	@Column(name = "requiredDate")
	private Date requiredDate;
	
	@Column(name = "dateOfSent")
	private Date dateOfSent;
	
	@Column(name = "completedDate")
	private Date completedDate;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "assignToId")
	private Long assignToId;
	
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
	
	@Column(name = "sentRemark")
	private String sentRemark;
	
	@Column(name = "receivedRemark")
	private String receivedRemark;

	@Column(name = "locationId")
	private Integer locationId;
	
	@Column(name = "additionalPartLocationId")// this will use only on Own repair type
	private Integer additionalPartLocationId;
	
	@Column(name = "labourDiscountTaxTypeId")
	private short labourDiscountTaxTypeId;
	
	@Column(name = "partDiscountTaxTypeId")
	private short partDiscountTaxTypeId;

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

	public short getRepairStatusId() {
		return repairStatusId;
	}

	public void setRepairStatusId(short repairStatusId) {
		this.repairStatusId = repairStatusId;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
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

	public short getRepairTypeId() {
		return repairTypeId;
	}

	public void setRepairTypeId(short repairTypeId) {
		this.repairTypeId = repairTypeId;
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

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}
	
	public short getRepairWorkshopId() {
		return repairWorkshopId;
	}

	public void setRepairWorkshopId(short repairWorkshopId) {
		this.repairWorkshopId = repairWorkshopId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	public Date getDateOfSent() {
		return dateOfSent;
	}

	public void setDateOfSent(Date dateOfSent) {
		this.dateOfSent = dateOfSent;
	}

	
	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Long getAssignToId() {
		return assignToId;
	}

	public void setAssignToId(Long assignToId) {
		this.assignToId = assignToId;
	}

	public String getSentRemark() {
		return sentRemark;
	}

	public void setSentRemark(String sentRemark) {
		this.sentRemark = sentRemark;
	}

	public String getReceivedRemark() {
		return receivedRemark;
	}

	public void setReceivedRemark(String receivedRemark) {
		this.receivedRemark = receivedRemark;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public short getLabourDiscountTaxTypeId() {
		return labourDiscountTaxTypeId;
	}

	public void setLabourDiscountTaxTypeId(short labourDiscountTaxTypeId) {
		this.labourDiscountTaxTypeId = labourDiscountTaxTypeId;
	}

	public short getPartDiscountTaxTypeId() {
		return partDiscountTaxTypeId;
	}

	public void setPartDiscountTaxTypeId(short partDiscountTaxTypeId) {
		this.partDiscountTaxTypeId = partDiscountTaxTypeId;
	}

	public Integer getAdditionalPartLocationId() {
		return additionalPartLocationId;
	}

	public void setAdditionalPartLocationId(Integer additionalPartLocationId) {
		this.additionalPartLocationId = additionalPartLocationId;
	}
	
	
	
}

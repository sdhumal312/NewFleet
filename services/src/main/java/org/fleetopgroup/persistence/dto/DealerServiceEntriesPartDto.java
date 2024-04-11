package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class DealerServiceEntriesPartDto {
	private Long dealerServiceEntriesPartId;

	private Long dealerServiceEntriesId;
	
	private Long partId;
	
	private String partName;
	
	private Double partQuantity;
	
	private Double partEchCost;
	
	private Double partDiscount;
	
	private Double partTax;
	
	private Double partTotalCost;
	
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
	
	private Double partDiscountCost;
	
	private Double partTaxCost;
	
	private Long dealerServiceEntriesNumber;
	
	private String dealerServiceEntriesNumberStr;

	private Integer vehicleOdometer;

	private String invoiceDateStr;

	private Long lastOccurredDseId;
	
	private Long lastOccurredDseNumber;
	
	private String partNumber;
	
	private short partDiscountTaxTypeId;
	
	private String partDiscountTaxType;
	
	private boolean isWarrantyApplicable;
	
	private Double	warrantyInMonths;
	
	public DealerServiceEntriesPartDto() {
		super();
	}

	public Long getDealerServiceEntriesPartId() {
		return dealerServiceEntriesPartId;
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public Long getPartId() {
		return partId;
	}

	public String getPartName() {
		return partName;
	}

	public Double getPartQuantity() {
		return partQuantity;
	}

	public Double getPartEchCost() {
		return partEchCost;
	}

	public Double getPartDiscount() {
		return partDiscount;
	}

	public Double getPartTax() {
		return partTax;
	}

	public Double getPartTotalCost() {
		return partTotalCost;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setDealerServiceEntriesPartId(Long dealerServiceEntriesPartId) {
		this.dealerServiceEntriesPartId = dealerServiceEntriesPartId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public void setPartQuantity(Double partQuantity) {
		this.partQuantity = Utility.round(partQuantity, 2);
	}

	public void setPartEchCost(Double partEchCost) {
		this.partEchCost = Utility.round(partEchCost, 2);
	}

	public void setPartDiscount(Double partDiscount) {
		this.partDiscount = Utility.round(partDiscount, 2);
	}

	public void setPartTax(Double partTax) {
		this.partTax = Utility.round(partTax, 2);
	}

	public void setPartTotalCost(Double partTotalCost) {
		this.partTotalCost = Utility.round(partTotalCost, 2);
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Double getPartDiscountCost() {
		return partDiscountCost;
	}

	public Double getPartTaxCost() {
		return partTaxCost;
	}

	public void setPartDiscountCost(Double partDiscountCost) {
		this.partDiscountCost = partDiscountCost;
	}

	public void setPartTaxCost(Double partTaxCost) {
		this.partTaxCost = partTaxCost;
	}

	public Long getDealerServiceEntriesNumber() {
		return dealerServiceEntriesNumber;
	}

	public String getDealerServiceEntriesNumberStr() {
		return dealerServiceEntriesNumberStr;
	}

	public void setDealerServiceEntriesNumber(Long dealerServiceEntriesNumber) {
		this.dealerServiceEntriesNumber = dealerServiceEntriesNumber;
	}

	public void setDealerServiceEntriesNumberStr(String dealerServiceEntriesNumberStr) {
		this.dealerServiceEntriesNumberStr = dealerServiceEntriesNumberStr;
	}

	public Integer getVehicleOdometer() {
		return vehicleOdometer;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}

	public void setVehicleOdometer(Integer vehicleOdometer) {
		this.vehicleOdometer = vehicleOdometer;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}

	public Long getLastOccurredDseId() {
		return lastOccurredDseId;
	}

	public void setLastOccurredDseId(Long lastOccurredDseId) {
		this.lastOccurredDseId = lastOccurredDseId;
	}

	public Long getLastOccurredDseNumber() {
		return lastOccurredDseNumber;
	}

	public void setLastOccurredDseNumber(Long lastOccurredDseNumber) {
		this.lastOccurredDseNumber = lastOccurredDseNumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	public short getPartDiscountTaxTypeId() {
		return partDiscountTaxTypeId;
	}

	public void setPartDiscountTaxTypeId(short partDiscountTaxTypeId) {
		this.partDiscountTaxTypeId = partDiscountTaxTypeId;
	}

	public String getPartDiscountTaxType() {
		return partDiscountTaxType;
	}

	public void setPartDiscountTaxType(String partDiscountTaxType) {
		this.partDiscountTaxType = partDiscountTaxType;
	}
	
	public boolean isWarrantyApplicable() {
		return isWarrantyApplicable;
	}

	public void setWarrantyApplicable(boolean isWarrantyApplicable) {
		this.isWarrantyApplicable = isWarrantyApplicable;
	}

	public Double getWarrantyInMonths() {
		return warrantyInMonths;
	}

	public void setWarrantyInMonths(Double warrantyInMonths) {
		this.warrantyInMonths = warrantyInMonths;
	}

	@Override
	public String toString() {
		return "DealerServiceEntriesPartDto [dealerServiceEntriesPartId=" + dealerServiceEntriesPartId
				+ ", dealerServiceEntriesId=" + dealerServiceEntriesId + ", partId=" + partId + ", partName=" + partName
				+ ", partQuantity=" + partQuantity + ", partEchCost=" + partEchCost + ", partDiscount=" + partDiscount
				+ ", partTax=" + partTax + ", partTotalCost=" + partTotalCost + ", createdById=" + createdById
				+ ", createdBy=" + createdBy + ", lastModifiedById=" + lastModifiedById + ", lastModifiedBy="
				+ lastModifiedBy + ", creationOn=" + creationOn + ", creationDate=" + creationDate + ", lastUpdatedOn="
				+ lastUpdatedOn + ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + ", partDiscountCost=" + partDiscountCost + ", partTaxCost="
				+ partTaxCost + ", dealerServiceEntriesNumber=" + dealerServiceEntriesNumber
				+ ", dealerServiceEntriesNumberStr=" + dealerServiceEntriesNumberStr + ", vehicleOdometer="
				+ vehicleOdometer + ", invoiceDateStr=" + invoiceDateStr + ", lastOccurredDseId=" + lastOccurredDseId
				+ ", lastOccurredDseNumber=" + lastOccurredDseNumber + ", partNumber=" + partNumber
				+ ", partDiscountTaxTypeId=" + partDiscountTaxTypeId + ", partDiscountTaxType=" + partDiscountTaxType
				+ ", isWarrantyApplicable=" + isWarrantyApplicable + ", warrantyInMonths=" + warrantyInMonths + "]";
	}

	
	
}

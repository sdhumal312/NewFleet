package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class VendorFixedLaundryRateDto {

	private Long vendorLaundryRateId;

	private Integer vendorId;

	private Long clothTypesId;

	private Double clothEachCost;

	private Double clothDiscount;

	private Double clothGst;

	private Double clothTotal;
	
	private Integer companyId;

	private Long createdById;
	
	private Long lastModifiedById;

	boolean markForDelete;

	private Date creationDate;

	private Date lastUpdated;
	
	private String	clothTypeName;
	
	private String	createdBy;
	
	private Double	newStockQuantity;
	
	private Double	usedStockQuantity;
	
	private long clothInventoryStockTypeDetailsId;
	

	public long getClothInventoryStockTypeDetailsId() {
		return clothInventoryStockTypeDetailsId;
	}

	public void setClothInventoryStockTypeDetailsId(long clothInventoryStockTypeDetailsId) {
		this.clothInventoryStockTypeDetailsId = clothInventoryStockTypeDetailsId;
	}

	public Long getVendorLaundryRateId() {
		return vendorLaundryRateId;
	}

	public void setVendorLaundryRateId(Long vendorLaundryRateId) {
		this.vendorLaundryRateId = vendorLaundryRateId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Double getClothEachCost() {
		return clothEachCost;
	}

	public void setClothEachCost(Double clothEachCost) {
		this.clothEachCost = Utility.round(clothEachCost, 2);
	}

	public Double getClothDiscount() {
		return clothDiscount;
	}

	public void setClothDiscount(Double clothDiscount) {
		this.clothDiscount = Utility.round( clothDiscount, 2);
	}

	public Double getClothGst() {
		return clothGst;
	}

	public void setClothGst(Double clothGst) {
		this.clothGst = Utility.round(clothGst, 2);
	}

	public Double getClothTotal() {
		return clothTotal;
	}

	public void setClothTotal(Double clothTotal) {
		this.clothTotal = Utility.round(clothTotal, 2);
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getClothTypeName() {
		return clothTypeName;
	}

	public void setClothTypeName(String clothTypeName) {
		this.clothTypeName = clothTypeName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Double getNewStockQuantity() {
		return newStockQuantity;
	}

	public void setNewStockQuantity(Double newStockQuantity) {
		this.newStockQuantity = Utility.round(newStockQuantity, 2);
	}

	public Double getUsedStockQuantity() {
		return usedStockQuantity;
	}

	public void setUsedStockQuantity(Double usedStockQuantity) {
		this.usedStockQuantity = Utility.round(usedStockQuantity,2);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VendorFixedLaundryRateDto [vendorLaundryRateId=");
		builder.append(vendorLaundryRateId);
		builder.append(", vendorId=");
		builder.append(vendorId);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", clothEachCost=");
		builder.append(clothEachCost);
		builder.append(", clothDiscount=");
		builder.append(clothDiscount);
		builder.append(", clothGst=");
		builder.append(clothGst);
		builder.append(", clothTotal=");
		builder.append(clothTotal);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastUpdated=");
		builder.append(lastUpdated);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

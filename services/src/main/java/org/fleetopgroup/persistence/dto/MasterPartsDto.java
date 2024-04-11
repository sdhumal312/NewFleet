package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class MasterPartsDto {

	private Long partid;

	private String partnumber;

	private String partname;

	private String parttype;
	
	private short partTypeId;
	
	private long categoryId;
	
	private long makerId;
	
	private long unitTypeId;

	private String category;

	private String make;

	private String description;

	private Integer lowstocklevel;

	private Integer reorderquantity;

	private String unittype;

	private Long part_photoid;

	/** The value for the created by email field */
	private String createdBy;

	/** The value for the lastUpdated By email field */
	private String lastModifiedBy;

	boolean markForDelete;

	/** The value for the created DATE field */
	private String created;

	/** The value for the lastUpdated DATE field */
	private String lastupdated;
	
	private Date createdOn;
	
	private Date lastupdatedOn;
	
	private short partManufacturerType;
	
	private boolean isOwnMaterParts;
	
	private Long lowStockInventoryId;
	
	private double unitCost;
	
	private double discount;
	
	private double tax;
	
	private boolean isRefreshment;
	
	private String subCategory;
	private boolean isWarrantyApplicable;
	private boolean isCouponAvailable;
	private boolean isRepairable;
	private boolean isScrapAvilable;
	private Double	warrantyInMonths;
	private String	couponDetails;
	private String	localPartName;
	private String	partNameOnBill;
	private String	partSubCategoryName;
	private boolean	isChildPart;
	private Integer locationId;
	private boolean	autoMasterPart;
	private short	partTypeCategoryId;
	private String	partTypeCategory;
	private boolean assetIdRequired;
	private String assetIdRequiredStr;
	


	public MasterPartsDto() {
		super();
	}

	public MasterPartsDto(String partnumber, String partname, String category, String make, String description,
			Integer lowstocklevel, Integer reorderquantity, String unittype, Long part_photoid, Double unitCost, Double discount, Double tax) {
		super();
		this.partnumber = partnumber;
		this.partname = partname;
		this.category = category;
		this.make = make;
		this.description = description;
		this.lowstocklevel = lowstocklevel;
		this.reorderquantity = reorderquantity;
		this.unittype = unittype;
		this.part_photoid = part_photoid;
		this.unitCost=unitCost;
		this.discount=discount;
		this.tax=tax;
	}

	
	
	
	
	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = Utility.round(unitCost, 2);
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = Utility.round(discount, 2);
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = Utility.round(tax,2);
	}

	/**
	 * @return the partid
	 */
	public Long getPartid() {
		return partid;
	}

	/**
	 * @param partid
	 *            the partid to set
	 */
	public void setPartid(Long partid) {
		this.partid = partid;
	}

	/**
	 * @return the partnumber
	 */
	public String getPartnumber() {
		return partnumber;
	}

	/**
	 * @param partnumber
	 *            the partnumber to set
	 */
	public void setPartnumber(String partnumber) {
		this.partnumber = partnumber;
	}

	/**
	 * @return the partname
	 */
	public String getPartname() {
		return partname;
	}

	/**
	 * @param partname
	 *            the partname to set
	 */
	public void setPartname(String partname) {
		this.partname = partname;
	}

	public String getParttype() {
		return parttype;
	}

	public void setParttype(String parttype) {
		this.parttype = parttype;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make
	 *            the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the lowstocklevel
	 */
	public Integer getLowstocklevel() {
		return lowstocklevel;
	}

	/**
	 * @param lowstocklevel
	 *            the lowstocklevel to set
	 */
	public void setLowstocklevel(Integer lowstocklevel) {
		this.lowstocklevel = lowstocklevel;
	}

	/**
	 * @return the reorderquantity
	 */
	public Integer getReorderquantity() {
		return reorderquantity;
	}

	/**
	 * @param reorderquantity
	 *            the reorderquantity to set
	 */
	public void setReorderquantity(Integer reorderquantity) {
		this.reorderquantity = reorderquantity;
	}

	/**
	 * @return the unittype
	 */
	public String getUnittype() {
		return unittype;
	}

	/**
	 * @param unittype
	 *            the unittype to set
	 */
	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the location
	 */

	/**
	 * @return the part_photoid
	 */
	public Long getPart_photoid() {
		return part_photoid;
	}

	/**
	 * @param part_photoid
	 *            the part_photoid to set
	 */
	public void setPart_photoid(Long part_photoid) {
		this.part_photoid = part_photoid;
	}

	public short getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(short partTypeId) {
		this.partTypeId = partTypeId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getMakerId() {
		return makerId;
	}

	public void setMakerId(long makerId) {
		this.makerId = makerId;
	}

	public long getUnitTypeId() {
		return unitTypeId;
	}

	public void setUnitTypeId(long unitTypeId) {
		this.unitTypeId = unitTypeId;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the lastupdatedOn
	 */
	public Date getLastupdatedOn() {
		return lastupdatedOn;
	}

	/**
	 * @param lastupdatedOn the lastupdatedOn to set
	 */
	public void setLastupdatedOn(Date lastupdatedOn) {
		this.lastupdatedOn = lastupdatedOn;
	}

	public short getPartManufacturerType() {
		return partManufacturerType;
	}

	public void setPartManufacturerType(short partManufacturerType) {
		this.partManufacturerType = partManufacturerType;
	}

	public boolean isOwnMaterParts() {
		return isOwnMaterParts;
	}

	public void setOwnMaterParts(boolean isOwnMaterParts) {
		this.isOwnMaterParts = isOwnMaterParts;
	}

	public Long getLowStockInventoryId() {
		return lowStockInventoryId;
	}

	public void setLowStockInventoryId(Long lowStockInventoryId) {
		this.lowStockInventoryId = lowStockInventoryId;
	}

	public boolean isRefreshment() {
		return isRefreshment;
	}

	public void setRefreshment(boolean isRefreshment) {
		this.isRefreshment = isRefreshment;
	}

	
	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public boolean isWarrantyApplicable() {
		return isWarrantyApplicable;
	}

	public void setWarrantyApplicable(boolean isWarrantyApplicable) {
		this.isWarrantyApplicable = isWarrantyApplicable;
	}

	public boolean isCouponAvailable() {
		return isCouponAvailable;
	}

	public void setCouponAvailable(boolean isCouponAvailable) {
		this.isCouponAvailable = isCouponAvailable;
	}

	public boolean isRepairable() {
		return isRepairable;
	}

	public void setRepairable(boolean isRepairable) {
		this.isRepairable = isRepairable;
	}

	public boolean isScrapAvilable() {
		return isScrapAvilable;
	}

	public void setScrapAvilable(boolean isScrapAvilable) {
		this.isScrapAvilable = isScrapAvilable;
	}

	public Double getWarrantyInMonths() {
		return warrantyInMonths;
	}

	public void setWarrantyInMonths(Double warrantyInMonths) {
		this.warrantyInMonths = Utility.round(warrantyInMonths, 2);
	}

	public String getCouponDetails() {
		return couponDetails;
	}

	public void setCouponDetails(String couponDetails) {
		this.couponDetails = couponDetails;
	}

	public String getLocalPartName() {
		return localPartName;
	}

	public void setLocalPartName(String localPartName) {
		this.localPartName = localPartName;
	}

	public String getPartNameOnBill() {
		return partNameOnBill;
	}

	public void setPartNameOnBill(String partNameOnBill) {
		this.partNameOnBill = partNameOnBill;
	}

	public String getPartSubCategoryName() {
		return partSubCategoryName;
	}

	public void setPartSubCategoryName(String partSubCategoryName) {
		this.partSubCategoryName = partSubCategoryName;
	}

	public boolean isChildPart() {
		return isChildPart;
	}

	public void setChildPart(boolean isChildPart) {
		this.isChildPart = isChildPart;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partname == null) ? 0 : partname.hashCode());
		result = prime * result + ((partnumber == null) ? 0 : partnumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterPartsDto other = (MasterPartsDto) obj;
		if (partname == null) {
			if (other.partname != null)
				return false;
		} else if (!partname.equals(other.partname))
			return false;
		if (partnumber == null) {
			if (other.partnumber != null)
				return false;
		} else if (!partnumber.equals(other.partnumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MasterPartsDto [partid=");
		builder.append(partid);
		builder.append(", partnumber=");
		builder.append(partnumber);
		builder.append(", partname=");
		builder.append(partname);
		builder.append(", parttype=");
		builder.append(parttype);
		builder.append(", category=");
		builder.append(category);
		builder.append(", make=");
		builder.append(make);
		builder.append(", description=");
		builder.append(description);
		builder.append(", lowstocklevel=");
		builder.append(lowstocklevel);
		builder.append(", reorderquantity=");
		builder.append(reorderquantity);
		builder.append(", unittype=");
		builder.append(unittype);
		builder.append(", part_photoid=");
		builder.append(part_photoid);
		builder.append(", partTypeId=");
		builder.append(partTypeId);
		builder.append(", categoryId=");
		builder.append(categoryId);
		builder.append(", makerId=");
		builder.append(makerId);
		builder.append(", unitTypeId=");
		builder.append(unitTypeId);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", unitCost=");
		builder.append(unitCost);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", tax=");
		builder.append(tax);
		builder.append("]");
		return builder.toString();
	}

	public boolean isAutoMasterPart() {
		return autoMasterPart;
	}

	public void setAutoMasterPart(boolean autoMasterPart) {
		this.autoMasterPart = autoMasterPart;
	}

	public short getPartTypeCategoryId() {
		return partTypeCategoryId;
	}

	public void setPartTypeCategoryId(short partTypeCategoryId) {
		this.partTypeCategoryId = partTypeCategoryId;
	}

	public boolean isAssetIdRequired() {
		return assetIdRequired;
	}

	public void setAssetIdRequired(boolean assetIdRequired) {
		this.assetIdRequired = assetIdRequired;
	}

	public String getAssetIdRequiredStr() {
		return assetIdRequiredStr;
	}

	public void setAssetIdRequiredStr(String assetIdRequiredStr) {
		this.assetIdRequiredStr = assetIdRequiredStr;
	}

	public String getPartTypeCategory() {
		return partTypeCategory;
	}

	public void setPartTypeCategory(String partTypeCategory) {
		this.partTypeCategory = partTypeCategory;
	}

}
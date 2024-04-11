package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "masterParts")
public class MasterParts implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partid")
	private Long partid;

	@Column(name = "partnumber", unique = false, nullable = false, length = 250)
	private String partnumber;

	
	@Column(name = "partname", nullable = false, length = 250)
	private String partname;
	
	@Column(name = "localPartName", length = 250)
	private String localPartName;
	
	@Column(name = "partNameOnBill", length = 250)
	private String partNameOnBill;

	@Column(name = "partTypeId", nullable = false)
	private short partTypeId;

	@Column(name = "categoryId", nullable = false)
	private long categoryId;
	
	@Column(name = "subCategory")
	private String	subCategory;
	
	@Column(name = "makerId", nullable = false)
	private long makerId;

	@Column(name = "description", length = 150)
	private String description;

	@Column(name = "unitTypeId", nullable = false)
	private long unitTypeId;

	@Column(name = "part_photoid")
	private Long part_photoid;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@OneToMany(mappedBy = "masterparts")
	private Set<MasterPartsLocation> MasterPartsLocation;

	@Column(name = "createdById",  updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "isRefreshment", nullable = false)
	private boolean isRefreshment;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	@Column(name = "partManufacturerType", nullable = false)
	private short partManufacturerType;
	
	@Column(name = "isOwnMaterParts", nullable = false)
	private boolean isOwnMaterParts;
	
	@Column(name = "isWarrantyApplicable", nullable = false)
	private boolean isWarrantyApplicable;
	
	@Column(name = "isCouponAvailable", nullable = false)
	private boolean isCouponAvailable;
	
	@Column(name = "isRepairable", nullable = false)
	private boolean isRepairable;
	
	@Column(name = "isScrapAvilable", nullable = false)
	private boolean isScrapAvilable;
	
	@Column(name = "warrantyInMonths")
	private Double	warrantyInMonths;
	
	@Column(name = "couponDetails")
	private String	couponDetails;
	
	@Column(name = "isChildPart", nullable = false)
	private boolean	isChildPart;
	
	@Column(name = "autoMasterPart", nullable = false)
	private boolean autoMasterPart;
	
	@Column(name = "partTypeCategoryId")
	private short	partTypeCategoryId;
	
	@Column(name = "assetIdRequired", nullable = false)
	private boolean assetIdRequired;
	
	
	public MasterParts() {
		super();
	}

	public MasterParts(String partnumber, String partname,  String description,
			 Long part_photoid, Integer companyId) {
		super();
		this.partnumber = partnumber;
		this.partname = partname;
		this.description = description;
		//this.lowstocklevel = lowstocklevel;
		//this.reorderquantity = reorderquantity;
		this.part_photoid = part_photoid;
		this.companyId	= companyId;
	}

	public MasterParts(Set<org.fleetopgroup.persistence.model.MasterPartsLocation> masterPartsLocation) {
		super();
		MasterPartsLocation = masterPartsLocation;
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

	

	public short getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(short partTypeId) {
		this.partTypeId = partTypeId;
	}

	

	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the masterPartsLocation
	 */
	public Set<MasterPartsLocation> getMasterPartsLocation() {
		return MasterPartsLocation;
	}

	/**
	 * @param masterPartsLocation
	 *            the masterPartsLocation to set
	 */
	public void setMasterPartsLocation(Set<MasterPartsLocation> masterPartsLocation) {
		MasterPartsLocation = masterPartsLocation;
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

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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

	public boolean isRefreshment() {
		return isRefreshment;
	}

	public void setRefreshment(boolean isRefreshment) {
		this.isRefreshment = isRefreshment;
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

	/*
	 * public Integer getSubCategoryId() { return subCategoryId; }
	 * 
	 * public void setSubCategoryId(Integer subCategoryId) { this.subCategoryId =
	 * subCategoryId; }
	 */
	
	

	public boolean isWarrantyApplicable() {
		return isWarrantyApplicable;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public void setChildPart(boolean isChildPart) {
		this.isChildPart = isChildPart;
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

	public Double getWarrantyInMonths() {
		return warrantyInMonths;
	}

	public void setWarrantyInMonths(Double warrantyInMonths) {
		this.warrantyInMonths = warrantyInMonths;
	}

	public String getCouponDetails() {
		return couponDetails;
	}

	public void setCouponDetails(String couponDetails) {
		this.couponDetails = couponDetails;
	}

	public boolean isScrapAvilable() {
		return isScrapAvilable;
	}

	public void setScrapAvilable(boolean isScrapAvilable) {
		this.isScrapAvilable = isScrapAvilable;
	}

	public boolean getIsChildPart() {
		return isChildPart;
	}

	public void setIsChildPart(boolean isChildPart) {
		this.isChildPart = isChildPart;
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
		MasterParts other = (MasterParts) obj;
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
		builder.append("MasterParts [partid=");
		builder.append(partid);
		builder.append(", partnumber=");
		builder.append(partnumber);
		builder.append(", partname=");
		builder.append(partname);
		builder.append(", partTypeId=");
		builder.append(partTypeId);
		builder.append(", categoryId=");
		builder.append(categoryId);
		builder.append(", makerId=");
		builder.append(makerId);
		builder.append(", description=");
		builder.append(description);
		builder.append(", unitTypeId=");
		builder.append(unitTypeId);
		builder.append(", part_photoid=");
		builder.append(part_photoid);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", MasterPartsLocation=");
		builder.append(MasterPartsLocation);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append("]");
		return builder.toString();
	}

}
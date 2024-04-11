package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class ClothTypesDto {

	private Long	clothTypesId;
	
	private String	clothTypeName;
	
	private String	description;
	
	private Date	creationDate;
	
	private Date	lastUpdatedOn;
	
	private Long	createdById;
	
	private Long	lastUpdatedBy;
	
	private Integer	companyId;
	
	private boolean markForDelete;
	
	private Double	usedStockQuantity;
	
	private Double	newStockQuantity;
	
	private Double	inServiceQuantity;

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public String getClothTypeName() {
		return clothTypeName;
	}

	public void setClothTypeName(String clothTypeName) {
		this.clothTypeName = clothTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Double getUsedStockQuantity() {
		return usedStockQuantity;
	}

	public void setUsedStockQuantity(Double usedStockQuantity) {
		this.usedStockQuantity = Utility.round(usedStockQuantity, 2);
	}

	public Double getNewStockQuantity() {
		return newStockQuantity;
	}

	public void setNewStockQuantity(Double newStockQuantity) {
		this.newStockQuantity = Utility.round(newStockQuantity, 2);
	}

	public Double getInServiceQuantity() {
		return inServiceQuantity;
	}

	public void setInServiceQuantity(Double inServiceQuantity) {
		this.inServiceQuantity = Utility.round(inServiceQuantity, 2);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClothTypesDto [clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", clothTypeName=");
		builder.append(clothTypeName);
		builder.append(", description=");
		builder.append(description);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastUpdatedOn=");
		builder.append(lastUpdatedOn);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastUpdatedBy=");
		builder.append(lastUpdatedBy);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", usedStockQuantity=");
		builder.append(usedStockQuantity);
		builder.append(", newStockQuantity=");
		builder.append(newStockQuantity);
		builder.append(", inServiceQuantity=");
		builder.append(inServiceQuantity);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

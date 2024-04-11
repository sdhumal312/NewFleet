package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class ClothInventoryStockTypeDetailsDto {
	
	private Long	clothInventoryStockTypeDetailsId;
	
	private Double	usedStockQuantity;
	
	private Double	newStockQuantity;
	
	private Double	inWashingQuantity;
	
	private Long	clothTypesId;
	
	private Integer wareHouseLocationId;
	
	private Integer companyId;
	
	private boolean	markForDelete;
	
	private String	ClothTypeName;
	
	private String	locationName;
	
	private Double	inServiceQuantity;
	
	private Double	damagedQuantity;
	
	private Double	losedQuantity;
	
	private Double	inTransferQuantity;
	

	public Long getClothInventoryStockTypeDetailsId() {
		return clothInventoryStockTypeDetailsId;
	}

	public Double getUsedStockQuantity() {
		return usedStockQuantity;
	}

	public Double getNewStockQuantity() {
		return newStockQuantity;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public Integer getWareHouseLocationId() {
		return wareHouseLocationId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public String getClothTypeName() {
		return ClothTypeName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setClothInventoryStockTypeDetailsId(Long clothInventoryStockTypeDetailsId) {
		this.clothInventoryStockTypeDetailsId = clothInventoryStockTypeDetailsId;
	}

	public void setUsedStockQuantity(Double usedStockQuantity) {
		this.usedStockQuantity = Utility.round(usedStockQuantity, 2);
	}

	public void setNewStockQuantity(Double newStockQuantity) {
		this.newStockQuantity = Utility.round(newStockQuantity, 2);
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public void setWareHouseLocationId(Integer wareHouseLocationId) {
		this.wareHouseLocationId = wareHouseLocationId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setClothTypeName(String clothTypeName) {
		ClothTypeName = clothTypeName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Double getInServiceQuantity() {
		return inServiceQuantity;
	}

	public void setInServiceQuantity(Double inServiceQuantity) {
		this.inServiceQuantity = Utility.round(inServiceQuantity, 2);
	}

	public Double getInWashingQuantity() {
		return inWashingQuantity;
	}

	public void setInWashingQuantity(Double inWashingQuantity) {
		this.inWashingQuantity = Utility.round(inWashingQuantity, 2);
	}
	

	public Double getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(Double damagedQuantity) {
		this.damagedQuantity = Utility.round(damagedQuantity, 2);
	}

	public Double getLosedQuantity() {
		return losedQuantity;
	}

	public void setLosedQuantity(Double losedQuantity) {
		this.losedQuantity = Utility.round(losedQuantity, 2);
	}

	public Double getInTransferQuantity() {
		return inTransferQuantity;
	}

	public void setInTransferQuantity(Double inTransferQuantity) {
		this.inTransferQuantity = Utility.round(inTransferQuantity, 2);;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClothInventoryStockTypeDetailsDto [clothInventoryStockTypeDetailsId=");
		builder.append(clothInventoryStockTypeDetailsId);
		builder.append(", usedStockQuantity=");
		builder.append(usedStockQuantity);
		builder.append(", newStockQuantity=");
		builder.append(newStockQuantity);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", wareHouseLocationId=");
		builder.append(wareHouseLocationId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", ClothTypeName=");
		builder.append(ClothTypeName);
		builder.append(", locationName=");
		builder.append(locationName);
		builder.append(", losedQuantity=");
		builder.append(losedQuantity);
		builder.append(", damagedQuantity=");
		builder.append(damagedQuantity);
		builder.append(", inTransferQuantity=");
		builder.append(inTransferQuantity);
		builder.append("]");
		return builder.toString();
	}
	
	
}


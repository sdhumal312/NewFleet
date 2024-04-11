package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ClothInventoryStockTypeDetails")
public class ClothInventoryStockTypeDetails implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clothInventoryStockTypeDetailsId")
	private Long	clothInventoryStockTypeDetailsId;
	
	@Column(name = "usedStockQuantity")
	private Double	usedStockQuantity;
	
	@Column(name = "newStockQuantity")
	private Double	newStockQuantity;
	
	@Column(name = "inServiceQuantity")
	private Double	inServiceQuantity;
	
	@Column(name = "inWashingQuantity")
	private Double	inWashingQuantity;
	
	@Column(name = "clothTypesId")
	private Long	clothTypesId;
	
	@Column(name = "wareHouseLocationId")
	private Integer wareHouseLocationId;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;
	
	@Column(name = "losedQuantity")
	private Double		losedQuantity;
	
	@Column(name = "damagedQuantity")
	private Double		damagedQuantity;
	
	@Column(name = "inTransferQuantity")
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

	public Double getInServiceQuantity() {
		return inServiceQuantity;
	}

	public Double getInWashingQuantity() {
		return inWashingQuantity;
	}

	public void setInWashingQuantity(Double inWashingQuantity) {
		this.inWashingQuantity = inWashingQuantity;
	}

	public void setInServiceQuantity(Double inServiceQuantity) {
		this.inServiceQuantity = inServiceQuantity;
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

	public void setClothInventoryStockTypeDetailsId(Long clothInventoryStockTypeDetailsId) {
		this.clothInventoryStockTypeDetailsId = clothInventoryStockTypeDetailsId;
	}

	public void setUsedStockQuantity(Double usedStockQuantity) {
		this.usedStockQuantity = usedStockQuantity;
	}

	public void setNewStockQuantity(Double newStockQuantity) {
		this.newStockQuantity = newStockQuantity;
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

	public Double getLosedQuantity() {
		return losedQuantity;
	}

	public void setLosedQuantity(Double losedQuantity) {
		this.losedQuantity = losedQuantity;
	}

	public Double getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(Double damagedQuantity) {
		this.damagedQuantity = damagedQuantity;
	}

	public Double getInTransferQuantity() {
		return inTransferQuantity;
	}

	public void setInTransferQuantity(Double inTransferQuantity) {
		this.inTransferQuantity = inTransferQuantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clothInventoryStockTypeDetailsId == null) ? 0 : clothInventoryStockTypeDetailsId.hashCode());
		result = prime * result + ((clothTypesId == null) ? 0 : clothTypesId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((wareHouseLocationId == null) ? 0 : wareHouseLocationId.hashCode());
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
		ClothInventoryStockTypeDetails other = (ClothInventoryStockTypeDetails) obj;
		if (clothInventoryStockTypeDetailsId == null) {
			if (other.clothInventoryStockTypeDetailsId != null)
				return false;
		} else if (!clothInventoryStockTypeDetailsId.equals(other.clothInventoryStockTypeDetailsId))
			return false;
		if (clothTypesId == null) {
			if (other.clothTypesId != null)
				return false;
		} else if (!clothTypesId.equals(other.clothTypesId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (wareHouseLocationId == null) {
			if (other.wareHouseLocationId != null)
				return false;
		} else if (!wareHouseLocationId.equals(other.wareHouseLocationId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClothInventoryStockTypeDetails [clothInventoryStockTypeDetailsId=");
		builder.append(clothInventoryStockTypeDetailsId);
		builder.append(", usedStockQuantity=");
		builder.append(usedStockQuantity);
		builder.append(", newStockQuantity=");
		builder.append(newStockQuantity);
		builder.append(", inServiceQuantity=");
		builder.append(inServiceQuantity);
		builder.append(", inWashingQuantity=");
		builder.append(inWashingQuantity);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", wareHouseLocationId=");
		builder.append(wareHouseLocationId);
		builder.append(", companyId="); 
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", damagedQuantity=");
		builder.append(damagedQuantity);
		builder.append(", losedQuantity=");
		builder.append(losedQuantity);
		builder.append(", inTransferQuantity=");
		builder.append(inTransferQuantity);
		builder.append("]");
		return builder.toString();
	}

	
}

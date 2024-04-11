package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BatteryAmount")
public class BatteryAmount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batteryAmountId")
	private Long batteryAmountId;
	
	@Column(name = "batteryManufacturerId")
	private Long batteryManufacturerId;
	
	@Column(name = "batteryTypeId")
	private Long batteryTypeId;
	
	@Column(name = "batteryCapacityId")
	private Long batteryCapacityId;
	
	
	@Column(name = "batteryQuantity")
	private Long batteryQuantity;
	
	@Column(name = "unitCost")
	private Double unitCost;
	
	@Column(name = "discount")
	private Double discount;
	
	@Column(name = "tax")
	private Double tax;
	
	@Column(name = "totalAmount")
	private Double totalAmount;
	
	@Column(name = "wareHouseLocation")
	private Integer wareHouseLocation;
	
	@Column(name = "batteryInvoiceId", nullable = false)
	private Long	batteryInvoiceId;
	
	@Column(name = "batteryAsignNumber")
	private Integer batteryAsignNumber;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;
	
	@Column(name = "discountTaxTypeId")
	private short discountTaxTypeId;
	
	public BatteryAmount() {
		super();
	}

	public BatteryAmount(Long batteryAmountId, Long batteryManufacturerId, Long batteryTypeId, Long batteryQuantity,
			Double unitCost, Double discount, Double tax, Double totalAmount, Integer wareHouseLocation,
			Long batteryInvoiceId, Integer batteryAsignNumber, Integer companyId, boolean markForDelete, short discountTaxTypeId) {
		super();
		this.batteryAmountId = batteryAmountId;
		this.batteryManufacturerId = batteryManufacturerId;
		this.batteryTypeId = batteryTypeId;
		this.batteryQuantity = batteryQuantity;
		this.unitCost = unitCost;
		this.discount = discount;
		this.tax = tax;
		this.totalAmount = totalAmount;
		this.wareHouseLocation = wareHouseLocation;
		this.batteryInvoiceId = batteryInvoiceId;
		this.batteryAsignNumber = batteryAsignNumber;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.discountTaxTypeId = discountTaxTypeId;
	}

	public Long getBatteryAmountId() {
		return batteryAmountId;
	}

	public Long getBatteryManufacturerId() {
		return batteryManufacturerId;
	}

	public Long getBatteryTypeId() {
		return batteryTypeId;
	}

	public Long getBatteryQuantity() {
		return batteryQuantity;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public Double getDiscount() {
		return discount;
	}

	public Double getTax() {
		return tax;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public Long getBatteryInvoiceId() {
		return batteryInvoiceId;
	}

	public Integer getBatteryAsignNumber() {
		return batteryAsignNumber;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setBatteryAmountId(Long batteryAmountId) {
		this.batteryAmountId = batteryAmountId;
	}

	public void setBatteryManufacturerId(Long batteryManufacturerId) {
		this.batteryManufacturerId = batteryManufacturerId;
	}

	public void setBatteryTypeId(Long batteryTypeId) {
		this.batteryTypeId = batteryTypeId;
	}

	public void setBatteryQuantity(Long batteryQuantity) {
		this.batteryQuantity = batteryQuantity;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public void setBatteryInvoiceId(Long batteryInvoiceId) {
		this.batteryInvoiceId = batteryInvoiceId;
	}

	public void setBatteryAsignNumber(Integer batteryAsignNumber) {
		this.batteryAsignNumber = batteryAsignNumber;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getBatteryCapacityId() {
		return batteryCapacityId;
	}

	public void setBatteryCapacityId(Long batteryCapacityId) {
		this.batteryCapacityId = batteryCapacityId;
	}

	public short getDiscountTaxTypeId() {
		return discountTaxTypeId;
	}

	public void setDiscountTaxTypeId(short discountTaxTypeId) {
		this.discountTaxTypeId = discountTaxTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batteryAmountId == null) ? 0 : batteryAmountId.hashCode());
		result = prime * result + ((batteryInvoiceId == null) ? 0 : batteryInvoiceId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
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
		BatteryAmount other = (BatteryAmount) obj;
		if (batteryAmountId == null) {
			if (other.batteryAmountId != null)
				return false;
		} else if (!batteryAmountId.equals(other.batteryAmountId))
			return false;
		if (batteryInvoiceId == null) {
			if (other.batteryInvoiceId != null)
				return false;
		} else if (!batteryInvoiceId.equals(other.batteryInvoiceId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatteryAmount [batteryAmountId=" + batteryAmountId + ", batteryManufacturerId=" + batteryManufacturerId
				+ ", batteryTypeId=" + batteryTypeId + ", batteryCapacityId=" + batteryCapacityId + ", batteryQuantity="
				+ batteryQuantity + ", unitCost=" + unitCost + ", discount=" + discount + ", tax=" + tax
				+ ", totalAmount=" + totalAmount + ", wareHouseLocation=" + wareHouseLocation + ", batteryInvoiceId="
				+ batteryInvoiceId + ", batteryAsignNumber=" + batteryAsignNumber + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + ", discountTaxTypeId=" + discountTaxTypeId + "]";
	}
	
	
}

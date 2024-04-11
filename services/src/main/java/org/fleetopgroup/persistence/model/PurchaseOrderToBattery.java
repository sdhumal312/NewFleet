package org.fleetopgroup.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PurchaseOrderToBattery")
public class PurchaseOrderToBattery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchaseOrderToBatteryId")
	private Long purchaseOrderToBatteryId;
	
	/** The value for the batterySerialNumber field */
	@Column(name = "batterySerialNumber", length = 50)
	private String batterySerialNumber;

	@Column(name = "receivedRemark", length = 250)
	private String receivedRemark;

	/** PurchaseOrder Id Data values  */
	@Column(name = "purchaseOrderId")
	private Long purchaseOrderId;
	
	/** PurchaseOrderToTyre Id Data values  */
	@Column(name = "purchaseOrderToPartId")
	private Long purchaseOrderToPartId;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	public PurchaseOrderToBattery() {
		super();
	}

	public PurchaseOrderToBattery(Long purchaseOrderToBatteryId, String batterySerialNumber, String receivedRemark,
			Long purchaseOrderId, Long purchaseOrderToPartId, Integer companyId, boolean markForDelete) {
		super();
		this.purchaseOrderToBatteryId = purchaseOrderToBatteryId;
		this.batterySerialNumber = batterySerialNumber;
		this.receivedRemark = receivedRemark;
		this.purchaseOrderId = purchaseOrderId;
		this.purchaseOrderToPartId = purchaseOrderToPartId;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	
	
	
	public Long getPurchaseOrderToBatteryId() {
		return purchaseOrderToBatteryId;
	}

	public void setPurchaseOrderToBatteryId(Long purchaseOrderToBatteryId) {
		this.purchaseOrderToBatteryId = purchaseOrderToBatteryId;
	}

	public String getBatterySerialNumber() {
		return batterySerialNumber;
	}

	public void setBatterySerialNumber(String batterySerialNumber) {
		this.batterySerialNumber = batterySerialNumber;
	}

	public String getReceivedRemark() {
		return receivedRemark;
	}

	public void setReceivedRemark(String receivedRemark) {
		this.receivedRemark = receivedRemark;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public Long getPurchaseOrderToPartId() {
		return purchaseOrderToPartId;
	}

	public void setPurchaseOrderToPartId(Long purchaseOrderToPartId) {
		this.purchaseOrderToPartId = purchaseOrderToPartId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batterySerialNumber == null) ? 0 : batterySerialNumber.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((purchaseOrderId == null) ? 0 : purchaseOrderId.hashCode());
		result = prime * result + ((purchaseOrderToBatteryId == null) ? 0 : purchaseOrderToBatteryId.hashCode());
		result = prime * result + ((purchaseOrderToPartId == null) ? 0 : purchaseOrderToPartId.hashCode());
		result = prime * result + ((receivedRemark == null) ? 0 : receivedRemark.hashCode());
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
		PurchaseOrderToBattery other = (PurchaseOrderToBattery) obj;
		if (batterySerialNumber == null) {
			if (other.batterySerialNumber != null)
				return false;
		} else if (!batterySerialNumber.equals(other.batterySerialNumber))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (purchaseOrderId == null) {
			if (other.purchaseOrderId != null)
				return false;
		} else if (!purchaseOrderId.equals(other.purchaseOrderId))
			return false;
		if (purchaseOrderToBatteryId == null) {
			if (other.purchaseOrderToBatteryId != null)
				return false;
		} else if (!purchaseOrderToBatteryId.equals(other.purchaseOrderToBatteryId))
			return false;
		if (purchaseOrderToPartId == null) {
			if (other.purchaseOrderToPartId != null)
				return false;
		} else if (!purchaseOrderToPartId.equals(other.purchaseOrderToPartId))
			return false;
		if (receivedRemark == null) {
			if (other.receivedRemark != null)
				return false;
		} else if (!receivedRemark.equals(other.receivedRemark))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PurchaseOrderToBattery [purchaseOrderToBatteryId=" + purchaseOrderToBatteryId + ", batterySerialNumber="
				+ batterySerialNumber + ", receivedRemark=" + receivedRemark + ", purchaseOrderId=" + purchaseOrderId
				+ ", purchaseOrderToPartId=" + purchaseOrderToPartId + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + "]";
	}
	
	

}

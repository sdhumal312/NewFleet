package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Battery")
public class Battery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batteryId")
	private Long batteryId;
	
	@Column(name = "batterySerialNumber")
	private String batterySerialNumber;
	
	@Column(name = "batteryManufacturerId")
	private Long batteryManufacturerId;
	
	@Column(name = "batteryTypeId")
	private Long batteryTypeId;
	
	@Column(name = "batteryCapacityId")
	private Long batteryCapacityId;
	
	@Column(name = "batteryAmount")
	private Double batteryAmount;
	
	@Column(name = "wareHouseLocationId")
	private Integer wareHouseLocationId;
	
	@Column(name = "batteryAmountId")
	private Long  batteryAmountId;
	
	@Column(name = "batteryInvoiceId")
	private Long  batteryInvoiceId;
	
	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "batteryUsesOdometer")
	private Integer batteryUsesOdometer;
	
	@Column(name = "usesNoOfTime")
	private Long   usesNoOfTime;
	
	@Column(name = "openOdometer")
	private Integer openOdometer;
	
	@Column(name = "closedOdometer")
	private Integer closedOdometer;
	
	@Column(name = "batteryPurchaseDate")
	private Timestamp batteryPurchaseDate;
	
	@Column(name = "batteryAsignedDate")
	private Timestamp batteryAsignedDate;
	
	@Column(name = "batteryStatusId")
	private short	  batteryStatusId;
	
	@Column(name = "batteryUsesStatusId")
	private short	 batteryUsesStatusId;
	
	@Column(name = "batteryScrapedDate")
	private Timestamp	batteryScrapedDate;
	
	@Column(name = "batteryFirstAsignedDate")
	private Timestamp batteryFirstAsignedDate;
	
	@Column(name = "battryScrapedById")
	private Long 		battryScrapedById;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Timestamp	lastModifiedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	@Column(name = "batteryScrapRemark")
	private String batteryScrapRemark;
	
	@Column(name = "subLocationId")
	private Integer subLocationId;
	
	@Column(name = "dismountedBatteryStatusId")
	private short dismountedBatteryStatusId;
	

	public Long getBatteryId() {
		return batteryId;
	}

	public String getBatterySerialNumber() {
		return batterySerialNumber;
	}

	public Long getBatteryManufacturerId() {
		return batteryManufacturerId;
	}

	public Long getBatteryTypeId() {
		return batteryTypeId;
	}

	public Double getBatteryAmount() {
		return batteryAmount;
	}

	public Integer getWareHouseLocationId() {
		return wareHouseLocationId;
	}

	public Long getBatteryAmountId() {
		return batteryAmountId;
	}

	public Long getBatteryInvoiceId() {
		return batteryInvoiceId;
	}

	public Integer getVid() {
		return vid;
	}

	public Integer getBatteryUsesOdometer() {
		return batteryUsesOdometer;
	}

	public Long getUsesNoOfTime() {
		return usesNoOfTime;
	}

	public Integer getOpenOdometer() {
		return openOdometer;
	}

	public Integer getClosedOdometer() {
		return closedOdometer;
	}

	public Timestamp getBatteryPurchaseDate() {
		return batteryPurchaseDate;
	}

	public Timestamp getBatteryAsignedDate() {
		return batteryAsignedDate;
	}

	public short getBatteryStatusId() {
		return batteryStatusId;
	}

	public short getBatteryUsesStatusId() {
		return batteryUsesStatusId;
	}

	public Timestamp getBatteryScrapedDate() {
		return batteryScrapedDate;
	}

	public Timestamp getBatteryFirstAsignedDate() {
		return batteryFirstAsignedDate;
	}

	public void setBatteryFirstAsignedDate(Timestamp batteryFirstAsignedDate) {
		this.batteryFirstAsignedDate = batteryFirstAsignedDate;
	}

	public Long getBattryScrapedById() {
		return battryScrapedById;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setBatteryId(Long batteryId) {
		this.batteryId = batteryId;
	}

	public void setBatterySerialNumber(String batterySerialNumber) {
		this.batterySerialNumber = batterySerialNumber;
	}

	public void setBatteryManufacturerId(Long batteryManufacturerId) {
		this.batteryManufacturerId = batteryManufacturerId;
	}

	public void setBatteryTypeId(Long batteryTypeId) {
		this.batteryTypeId = batteryTypeId;
	}

	public void setBatteryAmount(Double batteryAmount) {
		this.batteryAmount = batteryAmount;
	}

	public void setWareHouseLocationId(Integer wareHouseLocationId) {
		this.wareHouseLocationId = wareHouseLocationId;
	}

	public void setBatteryAmountId(Long batteryAmountId) {
		this.batteryAmountId = batteryAmountId;
	}

	public void setBatteryInvoiceId(Long batteryInvoiceId) {
		this.batteryInvoiceId = batteryInvoiceId;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setBatteryUsesOdometer(Integer batteryUsesOdometer) {
		this.batteryUsesOdometer = batteryUsesOdometer;
	}

	public void setUsesNoOfTime(Long usesNoOfTime) {
		this.usesNoOfTime = usesNoOfTime;
	}

	public void setOpenOdometer(Integer openOdometer) {
		this.openOdometer = openOdometer;
	}

	public void setClosedOdometer(Integer closedOdometer) {
		this.closedOdometer = closedOdometer;
	}

	public void setBatteryPurchaseDate(Timestamp batteryPurchaseDate) {
		this.batteryPurchaseDate = batteryPurchaseDate;
	}

	public void setBatteryAsignedDate(Timestamp batteryAsignedDate) {
		this.batteryAsignedDate = batteryAsignedDate;
	}

	public void setBatteryStatusId(short batteryStatusId) {
		this.batteryStatusId = batteryStatusId;
	}

	public void setBatteryUsesStatusId(short batteryUsesStatusId) {
		this.batteryUsesStatusId = batteryUsesStatusId;
	}

	public void setBatteryScrapedDate(Timestamp batteryScrapedDate) {
		this.batteryScrapedDate = batteryScrapedDate;
	}

	public void setBattryScrapedById(Long battryScrapedById) {
		this.battryScrapedById = battryScrapedById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
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

	public String getBatteryScrapRemark() {
		return batteryScrapRemark;
	}

	public void setBatteryScrapRemark(String batteryScrapRemark) {
		this.batteryScrapRemark = batteryScrapRemark;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	
	public short getDismountedBatteryStatusId() {
		return dismountedBatteryStatusId;
	}

	public void setDismountedBatteryStatusId(short dismountedBatteryStatusId) {
		this.dismountedBatteryStatusId = dismountedBatteryStatusId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batteryAmountId == null) ? 0 : batteryAmountId.hashCode());
		result = prime * result + ((batteryId == null) ? 0 : batteryId.hashCode());
		result = prime * result + ((batteryInvoiceId == null) ? 0 : batteryInvoiceId.hashCode());
		result = prime * result + ((batteryManufacturerId == null) ? 0 : batteryManufacturerId.hashCode());
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
		Battery other = (Battery) obj;
		if (batteryAmountId == null) {
			if (other.batteryAmountId != null)
				return false;
		} else if (!batteryAmountId.equals(other.batteryAmountId))
			return false;
		if (batteryId == null) {
			if (other.batteryId != null)
				return false;
		} else if (!batteryId.equals(other.batteryId))
			return false;
		if (batteryInvoiceId == null) {
			if (other.batteryInvoiceId != null)
				return false;
		} else if (!batteryInvoiceId.equals(other.batteryInvoiceId))
			return false;
		if (batteryManufacturerId == null) {
			if (other.batteryManufacturerId != null)
				return false;
		} else if (!batteryManufacturerId.equals(other.batteryManufacturerId))
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
		return "Battery [batteryId=" + batteryId + ", batterySerialNumber=" + batterySerialNumber
				+ ", batteryManufacturerId=" + batteryManufacturerId + ", batteryTypeId=" + batteryTypeId
				+ ", batteryCapacityId=" + batteryCapacityId + ", batteryAmount=" + batteryAmount
				+ ", wareHouseLocationId=" + wareHouseLocationId + ", batteryAmountId=" + batteryAmountId
				+ ", batteryInvoiceId=" + batteryInvoiceId + ", vid=" + vid + ", batteryUsesOdometer="
				+ batteryUsesOdometer + ", usesNoOfTime=" + usesNoOfTime + ", openOdometer=" + openOdometer
				+ ", closedOdometer=" + closedOdometer + ", batteryPurchaseDate=" + batteryPurchaseDate
				+ ", batteryAsignedDate=" + batteryAsignedDate + ", batteryStatusId=" + batteryStatusId
				+ ", batteryUsesStatusId=" + batteryUsesStatusId + ", batteryScrapedDate=" + batteryScrapedDate
				+ ", batteryFirstAsignedDate=" + batteryFirstAsignedDate + ", battryScrapedById=" + battryScrapedById
				+ ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById + ", createdOn=" + createdOn
				+ ", lastModifiedOn=" + lastModifiedOn + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", batteryScrapRemark=" + batteryScrapRemark + ", subLocationId=" + subLocationId + "]";
	}


	
	
	
}

package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BatteryType")
public class BatteryType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batteryTypeId")
	private Long 		batteryTypeId;
	
	@Column(name = "batteryType", nullable = false)
	private String		batteryType;
	
	@Column(name = "batteryManufacturerId", nullable = false)
	private Long		batteryManufacturerId;
	
	@Column(name = "partNumber")
	private String		partNumber;
	
	@Column(name = "warrantyPeriod")
	private	Integer		warrantyPeriod;
	
	@Column(name = "warrantyTypeId")
	private short		warrantyTypeId;
	
	@Column(name = "description")
	private String		description;
	
	@Column(name = "createdBy")
	private Long		createdBy;
	
	@Column(name = "lastModifiedBy")
	private Long		lastModifiedBy;
	
	

	@Column(name = "createdOn")
	private Date		createdOn;
	
	@Column(name = "lastModifiedOn")
	private Date		lastModifiedOn;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	@Column(name = "warrentyterm")
	private String		warrentyterm;
	
	public BatteryType() {
		super();
	}

	public BatteryType(Long batteryTypeId, String batteryType, Long batteryManufacturerId, String description,
			Long createdBy, Long lastModifiedBy, String warrentyterm, Date createdOn, Date lastModifiedOn) {
		super();
		this.batteryTypeId = batteryTypeId;
		this.batteryType = batteryType;
		this.batteryManufacturerId = batteryManufacturerId;
		this.description = description;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
		this.warrentyterm	= warrentyterm;
	}
	
	public String getWarrentyterm() {
		return warrentyterm;
	}

	public void setWarrentyterm(String warrentyterm) {
		this.warrentyterm = warrentyterm;
	}

	public Long getBatteryTypeId() {
		return batteryTypeId;
	}

	public String getBatteryType() {
		return batteryType;
	}

	public Long getBatteryManufacturerId() {
		return batteryManufacturerId;
	}

	public String getDescription() {
		return description;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setBatteryTypeId(Long batteryTypeId) {
		this.batteryTypeId = batteryTypeId;
	}

	public void setBatteryType(String batteryType) {
		this.batteryType = batteryType;
	}

	public void setBatteryManufacturerId(Long batteryManufacturerId) {
		this.batteryManufacturerId = batteryManufacturerId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	
	

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public Integer getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public short getWarrantyTypeId() {
		return warrantyTypeId;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public void setWarrantyPeriod(Integer warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public void setWarrantyTypeId(short warrantyTypeId) {
		this.warrantyTypeId = warrantyTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batteryManufacturerId == null) ? 0 : batteryManufacturerId.hashCode());
		result = prime * result + ((batteryType == null) ? 0 : batteryType.hashCode());
		result = prime * result + ((batteryTypeId == null) ? 0 : batteryTypeId.hashCode());
		result = prime * result + ((lastModifiedBy == null) ? 0 : lastModifiedBy.hashCode());
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
		BatteryType other = (BatteryType) obj;
		if (batteryManufacturerId == null) {
			if (other.batteryManufacturerId != null)
				return false;
		} else if (!batteryManufacturerId.equals(other.batteryManufacturerId))
			return false;
		if (batteryType == null) {
			if (other.batteryType != null)
				return false;
		} else if (!batteryType.equals(other.batteryType))
			return false;
		if (batteryTypeId == null) {
			if (other.batteryTypeId != null)
				return false;
		} else if (!batteryTypeId.equals(other.batteryTypeId))
			return false;
		if (lastModifiedBy == null) {
			if (other.lastModifiedBy != null)
				return false;
		} else if (!lastModifiedBy.equals(other.lastModifiedBy))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatteryType [batteryTypeId=" + batteryTypeId + ", batteryType=" + batteryType
				+ ", batteryManufacturerId=" + batteryManufacturerId + ", description=" + description + ", createdBy="
				+ createdBy + ", lastModifiedBy=" + lastModifiedBy + ", createdOn=" + createdOn + ", lastModifiedOn="
				+ lastModifiedOn + "]";
	}
	
	
}

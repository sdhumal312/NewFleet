package org.fleetopgroup.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PartWarrantyDetails")
public class PartWarrantyDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partWarrantyDetailsId")
	private Long	partWarrantyDetailsId;
	
	@Column(name = "partId")
	private Long	partId;
	
	@Column(name = "inventoryId")
	private Long inventoryId;
	
	@Column(name = "partSerialNumber")
	private String	partSerialNumber;
	
	@Column(name = "partAsignDate")
	private Date	partAsignDate;
	
	@Column(name = "warrantyEndDate")
	private Date	warrantyEndDate;
	
	@Column(name = "status")
	private	short	status;
	
	@Column(name = "locationId")
	private Integer locationId;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "created", updatable = false)
	private Date	created;
	
	@Column(name = "lastUpdated")
	private Date	lastUpdated;
	
	@Column(name = "createdById", updatable = false)
	private Long	createdById;
	
	@Column(name = "lastUpdatedById")
	private Long	lastUpdatedById;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "serviceId")
	private Long	serviceId;
	
	@Column(name = "servicePartId")
	private Long	servicePartId;
	
	@Column(name = "replaceInServiceId")
	private Long	replaceInServiceId;
	
	@Column(name = "replacePartWarrantyDetailsId")
	private Long	replacePartWarrantyDetailsId;
	
	@Column(name = "partWarrantyStatusId")
	private	short	partWarrantyStatusId;
	
	@Column(name = "isOldPartReceived", nullable = false)
	private boolean	isOldPartReceived;
	
	@Column(name = "assignedFrom", nullable = false)
	private short	assignedFrom;
	
	@Column(name = "repairStatusId")
	private short repairStatusId;
	
	@Column(name = "repairToStockDetailsId")
	private Long repairToStockDetailsId;

	public PartWarrantyDetails() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((inventoryId == null) ? 0 : inventoryId.hashCode());
		result = prime * result + ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime * result + ((lastUpdatedById == null) ? 0 : lastUpdatedById.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((partAsignDate == null) ? 0 : partAsignDate.hashCode());
		result = prime * result + ((partId == null) ? 0 : partId.hashCode());
		result = prime * result + ((partSerialNumber == null) ? 0 : partSerialNumber.hashCode());
		result = prime * result + ((partWarrantyDetailsId == null) ? 0 : partWarrantyDetailsId.hashCode());
		result = prime * result + ((replaceInServiceId == null) ? 0 : replaceInServiceId.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + status;
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		result = prime * result + ((warrantyEndDate == null) ? 0 : warrantyEndDate.hashCode());
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
		PartWarrantyDetails other = (PartWarrantyDetails) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (inventoryId == null) {
			if (other.inventoryId != null)
				return false;
		} else if (!inventoryId.equals(other.inventoryId))
			return false;
		if (lastUpdated == null) {
			if (other.lastUpdated != null)
				return false;
		} else if (!lastUpdated.equals(other.lastUpdated))
			return false;
		if (lastUpdatedById == null) {
			if (other.lastUpdatedById != null)
				return false;
		} else if (!lastUpdatedById.equals(other.lastUpdatedById))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (partAsignDate == null) {
			if (other.partAsignDate != null)
				return false;
		} else if (!partAsignDate.equals(other.partAsignDate))
			return false;
		if (partId == null) {
			if (other.partId != null)
				return false;
		} else if (!partId.equals(other.partId))
			return false;
		if (partSerialNumber == null) {
			if (other.partSerialNumber != null)
				return false;
		} else if (!partSerialNumber.equals(other.partSerialNumber))
			return false;
		if (partWarrantyDetailsId == null) {
			if (other.partWarrantyDetailsId != null)
				return false;
		} else if (!partWarrantyDetailsId.equals(other.partWarrantyDetailsId))
			return false;
		if (replaceInServiceId == null) {
			if (other.replaceInServiceId != null)
				return false;
		} else if (!replaceInServiceId.equals(other.replaceInServiceId))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (status != other.status)
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		if (warrantyEndDate == null) {
			if (other.warrantyEndDate != null)
				return false;
		} else if (!warrantyEndDate.equals(other.warrantyEndDate))
			return false;
		return true;
	}

	public Long getPartWarrantyDetailsId() {
		return partWarrantyDetailsId;
	}

	public void setPartWarrantyDetailsId(Long partWarrantyDetailsId) {
		this.partWarrantyDetailsId = partWarrantyDetailsId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getPartSerialNumber() {
		return partSerialNumber;
	}

	public void setPartSerialNumber(String partSerialNumber) {
		this.partSerialNumber = partSerialNumber;
	}

	public Date getPartAsignDate() {
		return partAsignDate;
	}

	public void setPartAsignDate(Date partAsignDate) {
		this.partAsignDate = partAsignDate;
	}

	public Date getWarrantyEndDate() {
		return warrantyEndDate;
	}

	public void setWarrantyEndDate(Date warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getReplaceInServiceId() {
		return replaceInServiceId;
	}

	public void setReplaceInServiceId(Long replaceInServiceId) {
		this.replaceInServiceId = replaceInServiceId;
	}

	public Long getReplacePartWarrantyDetailsId() {
		return replacePartWarrantyDetailsId;
	}

	public void setReplacePartWarrantyDetailsId(Long replacePartWarrantyDetailsId) {
		this.replacePartWarrantyDetailsId = replacePartWarrantyDetailsId;
	}

	public short getPartWarrantyStatusId() {
		return partWarrantyStatusId;
	}

	public void setPartWarrantyStatusId(short partWarrantyStatusId) {
		this.partWarrantyStatusId = partWarrantyStatusId;
	}

	public boolean isOldPartReceived() {
		return isOldPartReceived;
	}

	public void setOldPartReceived(boolean isOldPartReceived) {
		this.isOldPartReceived = isOldPartReceived;
	}

	public Long getServicePartId() {
		return servicePartId;
	}

	public void setServicePartId(Long servicePartId) {
		this.servicePartId = servicePartId;
	}

	public short getAssignedFrom() {
		return assignedFrom;
	}

	public void setAssignedFrom(short assignedFrom) {
		this.assignedFrom = assignedFrom;
	}

	public short getRepairStatusId() {
		return repairStatusId;
	}

	public void setRepairStatusId(short repairStatusId) {
		this.repairStatusId = repairStatusId;
	}
	
	public Long getRepairToStockDetailsId() {
		return repairToStockDetailsId;
	}

	public void setRepairToStockDetailsId(Long repairToStockDetailsId) {
		this.repairToStockDetailsId = repairToStockDetailsId;
	}

	@Override
	public String toString() {
		return "PartWarrantyDetails [partWarrantyDetailsId=" + partWarrantyDetailsId + ", partId=" + partId
				+ ", inventoryId=" + inventoryId + ", partSerialNumber=" + partSerialNumber + ", partAsignDate="
				+ partAsignDate + ", warrantyEndDate=" + warrantyEndDate + ", status=" + status + ", locationId="
				+ locationId + ", companyId=" + companyId + ", created=" + created + ", lastUpdated=" + lastUpdated
				+ ", createdById=" + createdById + ", lastUpdatedById=" + lastUpdatedById + ", markForDelete="
				+ markForDelete + ", vid=" + vid + ", serviceId=" + serviceId + ", replaceInServiceId="
				+ replaceInServiceId + ", replacePartWarrantyDetailsId=" + replacePartWarrantyDetailsId
				+ ", partWarrantyStatusId=" + partWarrantyStatusId + ", isOldPartReceived=" + isOldPartReceived + "]";
	}
}

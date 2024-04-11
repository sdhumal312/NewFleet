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
@Table(name = "UreaTransfer")
public class UreaTransfer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ureaTransferId")
	private Long ureaTransferId;
	
	@Column(name = "ureaRequisitionId")
	private Long ureaRequisitionId;
	
	@Column(name = "ureaTransferDate")
	private Date ureaTransferDate;
	
	@Column(name = "ureaTransferFromLocationId")
	private Integer ureaTransferFromLocationId;

	@Column(name = "ureaTransferToLoactionId")
	private Integer ureaTransferToLoactionId;
	
	@Column(name = "ureaRequisitionSenderId")
	private Long ureaRequisitionSenderId;
	
	@Column(name = "ureaTransferById")
	private Long ureaTransferById;
	
	@Column(name = "ureaTransferStatusId")
	private short ureaTransferStatusId;
	
	@Column(name = "ureaTransferQuantity")
	private Double ureaTransferQuantity;
	
	@Column(name = "ureaTransferRemark")
	private String	ureaTransferRemark;

	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastUpdatedById")
	private Long lastUpdatedById;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastUpdatedDate")
	private Date lastUpdatedDate;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public UreaTransfer() {
		super();
		
	}

	public UreaTransfer(Long ureaTransferId, Long ureaRequisitionId, Date ureaTransferDate,
			Integer ureaTransferFromLocationId, Integer ureaTransferToLoactionId, Long ureaRequisitionSenderId,
			Long ureaTransferById, short ureaTransferStatusId, Double ureaTransferQuantity, String ureaTransferRemark,
			Long createdById, Long lastUpdatedById, Date creationDate, Date lastUpdatedDate, Integer companyId,
			boolean markForDelete) {
		super();
		this.ureaTransferId = ureaTransferId;
		this.ureaRequisitionId = ureaRequisitionId;
		this.ureaTransferDate = ureaTransferDate;
		this.ureaTransferFromLocationId = ureaTransferFromLocationId;
		this.ureaTransferToLoactionId = ureaTransferToLoactionId;
		this.ureaRequisitionSenderId = ureaRequisitionSenderId;
		this.ureaTransferById = ureaTransferById;
		this.ureaTransferStatusId = ureaTransferStatusId;
		this.ureaTransferQuantity = ureaTransferQuantity;
		this.ureaTransferRemark = ureaTransferRemark;
		this.createdById = createdById;
		this.lastUpdatedById = lastUpdatedById;
		this.creationDate = creationDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((lastUpdatedById == null) ? 0 : lastUpdatedById.hashCode());
		result = prime * result + ((lastUpdatedDate == null) ? 0 : lastUpdatedDate.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((ureaRequisitionId == null) ? 0 : ureaRequisitionId.hashCode());
		result = prime * result + ((ureaRequisitionSenderId == null) ? 0 : ureaRequisitionSenderId.hashCode());
		result = prime * result + ((ureaTransferById == null) ? 0 : ureaTransferById.hashCode());
		result = prime * result + ((ureaTransferDate == null) ? 0 : ureaTransferDate.hashCode());
		result = prime * result + ((ureaTransferFromLocationId == null) ? 0 : ureaTransferFromLocationId.hashCode());
		result = prime * result + ((ureaTransferId == null) ? 0 : ureaTransferId.hashCode());
		result = prime * result + ((ureaTransferQuantity == null) ? 0 : ureaTransferQuantity.hashCode());
		result = prime * result + ((ureaTransferRemark == null) ? 0 : ureaTransferRemark.hashCode());
		result = prime * result + ureaTransferStatusId;
		result = prime * result + ((ureaTransferToLoactionId == null) ? 0 : ureaTransferToLoactionId.hashCode());
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
		UreaTransfer other = (UreaTransfer) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (lastUpdatedById == null) {
			if (other.lastUpdatedById != null)
				return false;
		} else if (!lastUpdatedById.equals(other.lastUpdatedById))
			return false;
		if (lastUpdatedDate == null) {
			if (other.lastUpdatedDate != null)
				return false;
		} else if (!lastUpdatedDate.equals(other.lastUpdatedDate))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (ureaRequisitionId == null) {
			if (other.ureaRequisitionId != null)
				return false;
		} else if (!ureaRequisitionId.equals(other.ureaRequisitionId))
			return false;
		if (ureaRequisitionSenderId == null) {
			if (other.ureaRequisitionSenderId != null)
				return false;
		} else if (!ureaRequisitionSenderId.equals(other.ureaRequisitionSenderId))
			return false;
		if (ureaTransferById == null) {
			if (other.ureaTransferById != null)
				return false;
		} else if (!ureaTransferById.equals(other.ureaTransferById))
			return false;
		if (ureaTransferDate == null) {
			if (other.ureaTransferDate != null)
				return false;
		} else if (!ureaTransferDate.equals(other.ureaTransferDate))
			return false;
		if (ureaTransferFromLocationId == null) {
			if (other.ureaTransferFromLocationId != null)
				return false;
		} else if (!ureaTransferFromLocationId.equals(other.ureaTransferFromLocationId))
			return false;
		if (ureaTransferId == null) {
			if (other.ureaTransferId != null)
				return false;
		} else if (!ureaTransferId.equals(other.ureaTransferId))
			return false;
		if (ureaTransferQuantity == null) {
			if (other.ureaTransferQuantity != null)
				return false;
		} else if (!ureaTransferQuantity.equals(other.ureaTransferQuantity))
			return false;
		if (ureaTransferRemark == null) {
			if (other.ureaTransferRemark != null)
				return false;
		} else if (!ureaTransferRemark.equals(other.ureaTransferRemark))
			return false;
		if (ureaTransferStatusId != other.ureaTransferStatusId)
			return false;
		if (ureaTransferToLoactionId == null) {
			if (other.ureaTransferToLoactionId != null)
				return false;
		} else if (!ureaTransferToLoactionId.equals(other.ureaTransferToLoactionId))
			return false;
		return true;
	}

	public Long getUreaTransferId() {
		return ureaTransferId;
	}

	public void setUreaTransferId(Long ureaTransferId) {
		this.ureaTransferId = ureaTransferId;
	}

	public Long getUreaRequisitionId() {
		return ureaRequisitionId;
	}

	public void setUreaRequisitionId(Long ureaRequisitionId) {
		this.ureaRequisitionId = ureaRequisitionId;
	}

	public Date getUreaTransferDate() {
		return ureaTransferDate;
	}

	public void setUreaTransferDate(Date ureaTransferDate) {
		this.ureaTransferDate = ureaTransferDate;
	}

	public Integer getUreaTransferFromLocationId() {
		return ureaTransferFromLocationId;
	}

	public void setUreaTransferFromLocationId(Integer ureaTransferFromLocationId) {
		this.ureaTransferFromLocationId = ureaTransferFromLocationId;
	}

	public Integer getUreaTransferToLoactionId() {
		return ureaTransferToLoactionId;
	}

	public void setUreaTransferToLoactionId(Integer ureaTransferToLoactionId) {
		this.ureaTransferToLoactionId = ureaTransferToLoactionId;
	}

	public Long getUreaRequisitionSenderId() {
		return ureaRequisitionSenderId;
	}

	public void setUreaRequisitionSenderId(Long ureaRequisitionSenderId) {
		this.ureaRequisitionSenderId = ureaRequisitionSenderId;
	}

	public Long getUreaTransferById() {
		return ureaTransferById;
	}

	public void setUreaTransferById(Long ureaTranferById) {
		this.ureaTransferById = ureaTranferById;
	}

	public short getUreaTransferStatusId() {
		return ureaTransferStatusId;
	}

	public void setUreaTransferStatusId(short ureaTransferStatusId) {
		this.ureaTransferStatusId = ureaTransferStatusId;
	}

	public Double getUreaTransferQuantity() {
		return ureaTransferQuantity;
	}

	public void setUreaTransferQuantity(Double ureaTransferQuantity) {
		this.ureaTransferQuantity = ureaTransferQuantity;
	}

	public String getUreaTransferRemark() {
		return ureaTransferRemark;
	}

	public void setUreaTransferRemark(String ureaTransferRemark) {
		this.ureaTransferRemark = ureaTransferRemark;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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
	public String toString() {
		return "UreaTransfer [ureaTransferId=" + ureaTransferId 
				+ ", ureaRequisitionId=" + ureaRequisitionId + ", ureaTransferDate=" + ureaTransferDate
				+ ", ureaTransferFromLocationId=" + ureaTransferFromLocationId + ", ureaTransferToLoactionId="
				+ ureaTransferToLoactionId + ", ureaRequisitionSenderId=" + ureaRequisitionSenderId
				+ ", ureaTransferById=" + ureaTransferById + ", ureaTransferStatusId=" + ureaTransferStatusId
				+ ", ureaTransferQuantity=" + ureaTransferQuantity + ", ureaTransferRemark=" + ureaTransferRemark
				+ ", createdById=" + createdById + ", lastUpdatedById=" + lastUpdatedById + ", creationDate="
				+ creationDate + ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}

	



}
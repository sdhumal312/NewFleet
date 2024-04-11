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
@Table(name = "UreaRequisition")
public class UreaRequisition implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ureaRequisitionId")
	private Long ureaRequisitionId;

	@Column(name = "ureaRequiredDate")
	private Date ureaRequiredDate;
	
	@Column(name = "ureaRequiredLocationId")
	private Integer ureaRequiredLocationId;
	
	@Column(name = "ureaTransferFromLocationId")
	private Integer ureaTransferFromLocationId;
	
	@Column(name = "ureaRequisitionSenderId")
	private Long ureaRequisitionSenderId;
	
	@Column(name = "ureaRequisitionReceiverId")
	private Long ureaRequisitionReceiverId;
	
	@Column(name = "ureaRequisitionStatusId")
	private short ureaRequisitionStatusId;
	
	@Column(name = "ureaRequiredQuantity")
	private Double ureaRequiredQuantity;
	
	@Column(name = "ureaReceivedQuantity")
	private Double ureaReceivedQuantity;
	
	@Column(name = "ureaRejectedQuantity")
	private Double ureaRejectedQuantity;
	
	@Column(name = "ureaRequisitionRemark")
	private String	ureaRequisitionRemark;

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

	public UreaRequisition() {
		super();
		
	}

	public UreaRequisition(Long ureaRequisitionId, Date ureaRequiredDate, Integer ureaRequiredLocationId,
			Long ureaRequisitionSenderId, Long ureaRequisitionReceiverId,
			short ureaRequisitionStatusId, Double ureaRequiredQuantity, Double ureaReceivedQuantity,
			Double ureaRejectedQuantity, String ureaRequisitionRemark, Long createdById, Long lastUpdatedById,
			Date creationDate, Date lastUpdatedDate, Integer companyId, boolean markForDelete) {
		super();
		this.ureaRequisitionId = ureaRequisitionId;
		this.ureaRequiredDate = ureaRequiredDate;
		this.ureaRequiredLocationId = ureaRequiredLocationId;
		this.ureaRequisitionSenderId = ureaRequisitionSenderId;
		this.ureaRequisitionReceiverId = ureaRequisitionReceiverId;
		this.ureaRequisitionStatusId = ureaRequisitionStatusId;
		this.ureaRequiredQuantity = ureaRequiredQuantity;
		this.ureaReceivedQuantity = ureaReceivedQuantity;
		this.ureaRejectedQuantity = ureaRejectedQuantity;
		this.ureaRequisitionRemark = ureaRequisitionRemark;
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
		result = prime * result + ((ureaReceivedQuantity == null) ? 0 : ureaReceivedQuantity.hashCode());
		result = prime * result + ((ureaRejectedQuantity == null) ? 0 : ureaRejectedQuantity.hashCode());
		result = prime * result + ((ureaRequiredDate == null) ? 0 : ureaRequiredDate.hashCode());
		result = prime * result + ((ureaRequiredLocationId == null) ? 0 : ureaRequiredLocationId.hashCode());
		result = prime * result + ((ureaRequiredQuantity == null) ? 0 : ureaRequiredQuantity.hashCode());
		result = prime * result + ((ureaRequisitionId == null) ? 0 : ureaRequisitionId.hashCode());
		result = prime * result + ((ureaRequisitionReceiverId == null) ? 0 : ureaRequisitionReceiverId.hashCode());
		result = prime * result + ((ureaRequisitionRemark == null) ? 0 : ureaRequisitionRemark.hashCode());
		result = prime * result + ((ureaRequisitionSenderId == null) ? 0 : ureaRequisitionSenderId.hashCode());
		result = prime * result + ureaRequisitionStatusId;
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
		UreaRequisition other = (UreaRequisition) obj;
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
		if (ureaReceivedQuantity == null) {
			if (other.ureaReceivedQuantity != null)
				return false;
		} else if (!ureaReceivedQuantity.equals(other.ureaReceivedQuantity))
			return false;
		if (ureaRejectedQuantity == null) {
			if (other.ureaRejectedQuantity != null)
				return false;
		} else if (!ureaRejectedQuantity.equals(other.ureaRejectedQuantity))
			return false;
		if (ureaRequiredDate == null) {
			if (other.ureaRequiredDate != null)
				return false;
		} else if (!ureaRequiredDate.equals(other.ureaRequiredDate))
			return false;
		if (ureaRequiredLocationId == null) {
			if (other.ureaRequiredLocationId != null)
				return false;
		} else if (!ureaRequiredLocationId.equals(other.ureaRequiredLocationId))
			return false;
		if (ureaRequiredQuantity == null) {
			if (other.ureaRequiredQuantity != null)
				return false;
		} else if (!ureaRequiredQuantity.equals(other.ureaRequiredQuantity))
			return false;
		if (ureaRequisitionId == null) {
			if (other.ureaRequisitionId != null)
				return false;
		} else if (!ureaRequisitionId.equals(other.ureaRequisitionId))
			return false;
		if (ureaRequisitionReceiverId == null) {
			if (other.ureaRequisitionReceiverId != null)
				return false;
		} else if (!ureaRequisitionReceiverId.equals(other.ureaRequisitionReceiverId))
			return false;
		if (ureaRequisitionRemark == null) {
			if (other.ureaRequisitionRemark != null)
				return false;
		} else if (!ureaRequisitionRemark.equals(other.ureaRequisitionRemark))
			return false;
		if (ureaRequisitionSenderId == null) {
			if (other.ureaRequisitionSenderId != null)
				return false;
		} else if (!ureaRequisitionSenderId.equals(other.ureaRequisitionSenderId))
			return false;
		if (ureaRequisitionStatusId != other.ureaRequisitionStatusId)
			return false;
		return true;
	}

	public Long getUreaRequisitionId() {
		return ureaRequisitionId;
	}

	public void setUreaRequisitionId(Long ureaRequisitionId) {
		this.ureaRequisitionId = ureaRequisitionId;
	}

	public Date getUreaRequiredDate() {
		return ureaRequiredDate;
	}

	public void setUreaRequiredDate(Date ureaRequiredDate) {
		this.ureaRequiredDate = ureaRequiredDate;
	}

	public Integer getUreaRequiredLocationId() {
		return ureaRequiredLocationId;
	}

	public void setUreaRequiredLocationId(Integer ureaRequiredLocationId) {
		this.ureaRequiredLocationId = ureaRequiredLocationId;
	}


	public Long getUreaRequisitionSenderId() {
		return ureaRequisitionSenderId;
	}

	public void setUreaRequisitionSenderId(Long ureaRequisitionSenderId) {
		this.ureaRequisitionSenderId = ureaRequisitionSenderId;
	}

	public Long getUreaRequisitionReceiverId() {
		return ureaRequisitionReceiverId;
	}

	public void setUreaRequisitionReceiverId(Long ureaRequisitionReceiverId) {
		this.ureaRequisitionReceiverId = ureaRequisitionReceiverId;
	}

	public short getUreaRequisitionStatusId() {
		return ureaRequisitionStatusId;
	}

	public void setUreaRequisitionStatusId(short ureaRequisitionStatusId) {
		this.ureaRequisitionStatusId = ureaRequisitionStatusId;
	}

	public Double getUreaRequiredQuantity() {
		return ureaRequiredQuantity;
	}

	public void setUreaRequiredQuantity(Double ureaRequiredQuantity) {
		this.ureaRequiredQuantity = ureaRequiredQuantity;
	}

	public Double getUreaReceivedQuantity() {
		return ureaReceivedQuantity;
	}

	public void setUreaReceivedQuantity(Double ureaReceivedQuantity) {
		this.ureaReceivedQuantity = ureaReceivedQuantity;
	}

	public Double getUreaRejectedQuantity() {
		return ureaRejectedQuantity;
	}

	public void setUreaRejectedQuantity(Double ureaRejectedQuantity) {
		this.ureaRejectedQuantity = ureaRejectedQuantity;
	}

	public String getUreaRequisitionRemark() {
		return ureaRequisitionRemark;
	}

	public void setUreaRequisitionRemark(String ureaRequisitionRemark) {
		this.ureaRequisitionRemark = ureaRequisitionRemark;
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

	public Integer getUreaTransferFromLocationId() {
		return ureaTransferFromLocationId;
	}

	public void setUreaTransferFromLocationId(Integer ureaTransferFromLocationId) {
		this.ureaTransferFromLocationId = ureaTransferFromLocationId;
	}

	@Override
	public String toString() {
		return "UreaRequisition [ureaRequisitionId=" + ureaRequisitionId + ", ureaRequiredDate=" + ureaRequiredDate
				+ ", ureaRequiredLocationId=" + ureaRequiredLocationId + ",  ureaRequisitionSenderId=" + ureaRequisitionSenderId
				+ ", ureaRequisitionReceiverId=" + ureaRequisitionReceiverId + ", ureaRequisitionStatusId="
				+ ureaRequisitionStatusId + ", ureaRequiredQuantity=" + ureaRequiredQuantity + ", ureaReceivedQuantity="
				+ ureaReceivedQuantity + ", ureaRejectedQuantity=" + ureaRejectedQuantity + ", ureaRequisitionRemark="
				+ ureaRequisitionRemark + ", createdById=" + createdById + ", lastUpdatedById=" + lastUpdatedById
				+ ", creationDate=" + creationDate + ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + ", ureaTransferFromLocationId=" + ureaTransferFromLocationId + "]";
	}

	
	


}
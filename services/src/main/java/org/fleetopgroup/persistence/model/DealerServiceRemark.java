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
@Table(name = "DealerServiceRemark")
public class DealerServiceRemark implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "dealerServiceEntriesRemarkId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	dealerServiceEntriesRemarkId;
	
	@Column(name = "remark", length = 1000)
	private String	remark;
	
	@Column(name = "dealerServiceEntriesId")
	private Long	dealerServiceEntriesId;
	
	@Column(name = "remarkTypeId",nullable = false)
	private short	remarkTypeId;
	
	@Column(name = "createdOn")
	private Date	createdOn;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "driverId")
	private Integer driverId;
	
	@Column(name = "assignee")
	private Long	assignee;
	
	@Column(name = "markForDelete")
	private Boolean	markForDelete;

	public DealerServiceRemark() {
		super();
	}

	public DealerServiceRemark(Long dealerServiceEntriesRemarkId, String remark, Long dealerServiceEntriesId,
			short remarkTypeId, Date createdOn, Long createdById, Integer companyId, Integer driverId, Long assignee,
			Boolean markForDelete) {
		super();
		this.dealerServiceEntriesRemarkId = dealerServiceEntriesRemarkId;
		this.remark = remark;
		this.dealerServiceEntriesId = dealerServiceEntriesId;
		this.remarkTypeId = remarkTypeId;
		this.createdOn = createdOn;
		this.createdById = createdById;
		this.companyId = companyId;
		this.driverId = driverId;
		this.assignee = assignee;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignee == null) ? 0 : assignee.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((dealerServiceEntriesId == null) ? 0 : dealerServiceEntriesId.hashCode());
		result = prime * result
				+ ((dealerServiceEntriesRemarkId == null) ? 0 : dealerServiceEntriesRemarkId.hashCode());
		result = prime * result + ((driverId == null) ? 0 : driverId.hashCode());
		result = prime * result + ((markForDelete == null) ? 0 : markForDelete.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + remarkTypeId;
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
		DealerServiceRemark other = (DealerServiceRemark) obj;
		if (assignee == null) {
			if (other.assignee != null)
				return false;
		} else if (!assignee.equals(other.assignee))
			return false;
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
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (dealerServiceEntriesId == null) {
			if (other.dealerServiceEntriesId != null)
				return false;
		} else if (!dealerServiceEntriesId.equals(other.dealerServiceEntriesId))
			return false;
		if (dealerServiceEntriesRemarkId == null) {
			if (other.dealerServiceEntriesRemarkId != null)
				return false;
		} else if (!dealerServiceEntriesRemarkId.equals(other.dealerServiceEntriesRemarkId))
			return false;
		if (driverId == null) {
			if (other.driverId != null)
				return false;
		} else if (!driverId.equals(other.driverId))
			return false;
		if (markForDelete == null) {
			if (other.markForDelete != null)
				return false;
		} else if (!markForDelete.equals(other.markForDelete))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (remarkTypeId != other.remarkTypeId)
			return false;
		return true;
	}

	public Long getDealerServiceEntriesRemarkId() {
		return dealerServiceEntriesRemarkId;
	}

	public void setDealerServiceEntriesRemarkId(Long dealerServiceEntriesRemarkId) {
		this.dealerServiceEntriesRemarkId = dealerServiceEntriesRemarkId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public short getRemarkTypeId() {
		return remarkTypeId;
	}

	public void setRemarkTypeId(short remarkTypeId) {
		this.remarkTypeId = remarkTypeId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Long getAssignee() {
		return assignee;
	}

	public void setAssignee(Long assignee) {
		this.assignee = assignee;
	}

	public Boolean getMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(Boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "DealerServiceEntriesRemark [dealerServiceEntriesRemarkId=" + dealerServiceEntriesRemarkId + ", remark="
				+ remark + ", dealerServiceEntriesId=" + dealerServiceEntriesId + ", remarkTypeId=" + remarkTypeId
				+ ", createdOn=" + createdOn + ", createdById=" + createdById + ", companyId=" + companyId
				+ ", driverId=" + driverId + ", assignee=" + assignee + ", markForDelete=" + markForDelete + "]";
	}

	
	
}

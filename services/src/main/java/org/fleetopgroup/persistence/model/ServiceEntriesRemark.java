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
@Table(name = "ServiceEntriesRemark")
public class ServiceEntriesRemark implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "serviceEntryRemarkId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	serviceEntryRemarkId;
	
	@Column(name = "remark", length = 1000)
	private String	remark;
	
	@Column(name = "serviceEntryId")
	private Long	serviceEntryId;
	
	@Column(name = "remarkTypeId")
	private short	remarkTypeId;
	
	@Column(name = "createdOn")
	private Date	createdOn;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "driverId")
	private Integer driverId;
	
	@Column(name = "markForDelete")
	private Boolean	markForDelete;

	public Long getServiceEntryRemarkId() {
		return serviceEntryRemarkId;
	}

	public void setServiceEntryRemarkId(Long serviceEntryRemarkId) {
		this.serviceEntryRemarkId = serviceEntryRemarkId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getServiceEntryId() {
		return serviceEntryId;
	}

	public void setServiceEntryId(Long serviceEntryId) {
		this.serviceEntryId = serviceEntryId;
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

	public Boolean getMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(Boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ServiceEntriesRemark [serviceEntryRemarkId=" + serviceEntryRemarkId + ", remark=" + remark
				+ ", serviceEntryId=" + serviceEntryId + ", remarkTypeId=" + remarkTypeId + ", createdOn=" + createdOn
				+ ", createdById=" + createdById + ", companyId=" + companyId + ", driverId=" + driverId
				+ ", markForDelete=" + markForDelete + "]";
	}
	
	
	
}

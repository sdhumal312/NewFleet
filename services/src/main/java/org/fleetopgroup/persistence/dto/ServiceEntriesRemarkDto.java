package org.fleetopgroup.persistence.dto;

/*
 * @author Manish Singh
 * @since  08-07-2021
 */

public class ServiceEntriesRemarkDto {
	
	private Long	serviceEntryRemarkId;
	
	private String	remark;
	
	private Long	serviceEntryId;
	
	private short	remarkTypeId;
	
	private String	remarkType;
	
	private String	createdOn;
	
	private String	createdBy;
	
	private Integer	companyId;
		
	private Integer driverId;
	
	private  String driverName;

	public ServiceEntriesRemarkDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public ServiceEntriesRemarkDto(Long serviceEntryRemarkId, String remark, Long serviceEntryId, short remarkTypeId,
			String remarkType, String createdOn, String createdBy, Integer companyId, Integer driverId,
			String driverName) {
		super();
		this.serviceEntryRemarkId = serviceEntryRemarkId;
		this.remark = remark;
		this.serviceEntryId = serviceEntryId;
		this.remarkTypeId = remarkTypeId;
		this.remarkType = remarkType;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.companyId = companyId;
		this.driverId = driverId;
		this.driverName = driverName;
	}



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

	public String getRemarkType() {
		return remarkType;
	}

	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}



	@Override
	public String toString() {
		return "ServiceEntriesRemarkDto [serviceEntryRemarkId=" + serviceEntryRemarkId + ", remark=" + remark
				+ ", serviceEntryId=" + serviceEntryId + ", remarkTypeId=" + remarkTypeId + ", remarkType=" + remarkType
				+ ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", companyId=" + companyId + ", driverId="
				+ driverId + ", driverName=" + driverName + "]";
	}
	
	
	
}

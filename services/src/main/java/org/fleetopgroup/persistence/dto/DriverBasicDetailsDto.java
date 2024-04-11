package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class DriverBasicDetailsDto {

	private Long driverBasicDetailsId;
	
	private int driverId;
	
	private String driverName;

	private Long detailsTypeId;
	
	private String detailsType;
	
	private Double quantity;
	
	private Date assignDate;
	
	private String assignDateStr;
	
	private String	remark;

	private Long createdById;

	private String createdBy;
	
	private Long lastUpdatedById;

	private String lastUpdatedBy;
	
	private Date creationDate;

	private String creationDateStr;
	
	private Date lastUpdatedDate;
	
	private String lastUpdatedDateStr;
	
	private Integer companyId;
	
	private boolean markForDelete;

	public DriverBasicDetailsDto() {
		super();
	}

	public DriverBasicDetailsDto(Long driverBasicDetailsId, Long detailsTypeId, String detailsType, Double quantity,
			Date assignDate, String assignDateStr, String remark, Long createdById, String createdBy,
			Long lastUpdatedById, String lastUpdatedBy, Date creationDate, String creationDateStr, Date lastUpdatedDate,
			String lastUpdatedDateStr, Integer companyId, boolean markForDelete) {
		super();
		this.driverBasicDetailsId = driverBasicDetailsId;
		this.detailsTypeId = detailsTypeId;
		this.detailsType = detailsType;
		this.quantity = quantity;
		this.assignDate = assignDate;
		this.assignDateStr = assignDateStr;
		this.remark = remark;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.lastUpdatedById = lastUpdatedById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.creationDate = creationDate;
		this.creationDateStr = creationDateStr;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedDateStr = lastUpdatedDateStr;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getDriverBasicDetailsId() {
		return driverBasicDetailsId;
	}

	public Long getDetailsTypeId() {
		return detailsTypeId;
	}

	public String getDetailsType() {
		return detailsType;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public String getAssignDateStr() {
		return assignDateStr;
	}

	public String getRemark() {
		return remark;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getCreationDateStr() {
		return creationDateStr;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public String getLastUpdatedDateStr() {
		return lastUpdatedDateStr;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setDriverBasicDetailsId(Long driverBasicDetailsId) {
		this.driverBasicDetailsId = driverBasicDetailsId;
	}

	public void setDetailsTypeId(Long detailsTypeId) {
		this.detailsTypeId = detailsTypeId;
	}

	public void setDetailsType(String detailsType) {
		this.detailsType = detailsType;
	}

	public void setQuantity(Double quantity) {
		this.quantity =Utility.round(quantity,2);
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public void setAssignDateStr(String assignDateStr) {
		this.assignDateStr = assignDateStr;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setCreationDateStr(String creationDateStr) {
		this.creationDateStr = creationDateStr;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public void setLastUpdatedDateStr(String lastUpdatedDateStr) {
		this.lastUpdatedDateStr = lastUpdatedDateStr;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	
	public int getDriverId() {
		return driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	@Override
	public String toString() {
		return "DriverBasicDetailsDto [driverBasicDetailsId=" + driverBasicDetailsId + ", driverId=" + driverId
				+ ", driverName=" + driverName + ", detailsTypeId=" + detailsTypeId + ", detailsType=" + detailsType
				+ ", quantity=" + quantity + ", assignDate=" + assignDate + ", assignDateStr=" + assignDateStr
				+ ", remark=" + remark + ", createdById=" + createdById + ", createdBy=" + createdBy
				+ ", lastUpdatedById=" + lastUpdatedById + ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate="
				+ creationDate + ", creationDateStr=" + creationDateStr + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", lastUpdatedDateStr=" + lastUpdatedDateStr + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + "]";
	}

	

	
	  

}

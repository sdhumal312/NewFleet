package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class UreaTransferDto {

	private Long ureaTransferId;
	
	private Long ureaRequisitionId;
	
	private Integer ureaTransferFromLocationId;
	
	private String ureaTransferFromLocation;
	
	private Integer ureaTransferToLoactionId;
	
	private String ureaTransferToLoaction;

	private Long ureaRequisitionSenderId;
	
	private String ureaRequisitionSender;
	
	private Long ureaTransferById;
	
	private String ureaTransferBy;
	
	private short ureaTransferStatusId;
	
	private String ureaTransferStatus;
	
	private Double ureaTransferQuantity;
	
	private Date ureaTransferDate;
	
	private String ureaTransferDateStr;
	
	private String	ureaTransferRemark;

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

	public UreaTransferDto() {
		super();
		
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

	public Integer getUreaTransferFromLocationId() {
		return ureaTransferFromLocationId;
	}

	public void setUreaTransferFromLocationId(Integer ureaTransferFromLocationId) {
		this.ureaTransferFromLocationId = ureaTransferFromLocationId;
	}

	public String getUreaTransferFromLocation() {
		return ureaTransferFromLocation;
	}

	public void setUreaTransferFromLocation(String ureaTransferFromLocation) {
		this.ureaTransferFromLocation = ureaTransferFromLocation;
	}

	public Integer getUreaTransferToLoactionId() {
		return ureaTransferToLoactionId;
	}

	public void setUreaTransferToLoactionId(Integer ureaTransferToLoactionId) {
		this.ureaTransferToLoactionId = ureaTransferToLoactionId;
	}

	public String getUreaTransferToLoaction() {
		return ureaTransferToLoaction;
	}

	public void setUreaTransferToLoaction(String ureaTransferToLoaction) {
		this.ureaTransferToLoaction = ureaTransferToLoaction;
	}

	public Long getUreaRequisitionSenderId() {
		return ureaRequisitionSenderId;
	}

	public void setUreaRequisitionSenderId(Long ureaRequisitionSenderId) {
		this.ureaRequisitionSenderId = ureaRequisitionSenderId;
	}

	public String getUreaRequisitionSender() {
		return ureaRequisitionSender;
	}

	public void setUreaRequisitionSender(String ureaRequisitionSender) {
		this.ureaRequisitionSender = ureaRequisitionSender;
	}

	public Long getUreaTransferById() {
		return ureaTransferById;
	}

	public void setUreaTransferById(Long ureaTransferById) {
		this.ureaTransferById = ureaTransferById;
	}

	public String getUreaTransferBy() {
		return ureaTransferBy;
	}

	public void setUreaTransferBy(String ureaTransferBy) {
		this.ureaTransferBy = ureaTransferBy;
	}

	public short getUreaTransferStatusId() {
		return ureaTransferStatusId;
	}

	public void setUreaTransferStatusId(short ureaTransferStatusId) {
		this.ureaTransferStatusId = ureaTransferStatusId;
	}

	public String getUreaTransferStatus() {
		return ureaTransferStatus;
	}

	public void setUreaTransferStatus(String ureaTransferStatus) {
		this.ureaTransferStatus = ureaTransferStatus;
	}

	public Double getUreaTransferQuantity() {
		return ureaTransferQuantity;
	}

	public void setUreaTransferQuantity(Double ureaTransferQuantity) {
		this.ureaTransferQuantity  = Utility.round(ureaTransferQuantity, 2);
	}

	public Date getUreaTransferDate() {
		return ureaTransferDate;
	}

	public void setUreaTransferDate(Date ureaTransferDate) {
		this.ureaTransferDate = ureaTransferDate;
	}

	public String getUreaTransferDateStr() {
		return ureaTransferDateStr;
	}

	public void setUreaTransferDateStr(String ureaTransferDateStr) {
		this.ureaTransferDateStr = ureaTransferDateStr;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationDateStr() {
		return creationDateStr;
	}

	public void setCreationDateStr(String creationDateStr) {
		this.creationDateStr = creationDateStr;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLastUpdatedDateStr() {
		return lastUpdatedDateStr;
	}

	public void setLastUpdatedDateStr(String lastUpdatedDateStr) {
		this.lastUpdatedDateStr = lastUpdatedDateStr;
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
		return "UreaTransferDto [ureaTransferId=" + ureaTransferId + ", ureaRequisitionId=" + ureaRequisitionId
				+ ", ureaTransferFromLocationId=" + ureaTransferFromLocationId + ", ureaTransferFromLocation="
				+ ureaTransferFromLocation + ", ureaTransferToLoactionId=" + ureaTransferToLoactionId
				+ ", ureaTransferToLoaction=" + ureaTransferToLoaction + ", ureaRequisitionSenderId="
				+ ureaRequisitionSenderId + ", ureaRequisitionSender=" + ureaRequisitionSender + ", ureaTranferById="
				+ ureaTransferById + ", ureaTranferBy=" + ureaTransferBy + ", ureaTransferStatusId="
				+ ureaTransferStatusId + ", ureaTransferStatus=" + ureaTransferStatus + ", ureaTransferQuantity="
				+ ureaTransferQuantity + ", ureaTransferDate=" + ureaTransferDate + ", ureaTransferDateStr="
				+ ureaTransferDateStr + ", ureaTransferRemark=" + ureaTransferRemark + ", createdById=" + createdById
				+ ", createdBy=" + createdBy + ", lastUpdatedById=" + lastUpdatedById + ", lastUpdatedBy="
				+ lastUpdatedBy + ", creationDate=" + creationDate + ", creationDateStr=" + creationDateStr
				+ ", lastUpdatedDate=" + lastUpdatedDate + ", lastUpdatedDateStr=" + lastUpdatedDateStr + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + "]";
	}

	
	

	
	  

}

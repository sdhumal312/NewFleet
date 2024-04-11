package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class UreaRequisitionDto {

	private Long ureaRequisitionId;
	
	private Integer ureaRequiredLocationId;
	
	private String ureaRequiredLocation;
	
	private Integer ureaTransferFromLocationId;

	private String ureaTransferFromLocation;
	
	private Long ureaRequisitionSenderId;
	
	private String ureaRequisitionSender;
	
	private Long ureaRequisitionReceiverId;
	
	private String ureaRequisitionReceiver;
	
	private short ureaRequisitionStatusId;
	
	private String ureaRequisitionStatus;
	
	private Double ureaRequiredQuantity;
	
	private Double ureaReceivedQuantity;
	
	private Double ureaRejectedQuantity;
	
	private Date ureaRequiredDate;
	
	private String ureaRequiredDateStr;
	
	private String	ureaRequisitionRemark;

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
	
	private Double ureaTransferQuantity;

	public UreaRequisitionDto() {
		super();
		
	}

	public Long getUreaRequisitionId() {
		return ureaRequisitionId;
	}

	public void setUreaRequisitionId(Long ureaRequisitionId) {
		this.ureaRequisitionId = ureaRequisitionId;
	}

	public Integer getUreaRequiredLocationId() {
		return ureaRequiredLocationId;
	}

	public void setUreaRequiredLocationId(Integer ureaRequiredLocationId) {
		this.ureaRequiredLocationId = ureaRequiredLocationId;
	}

	public String getUreaRequiredLocation() {
		return ureaRequiredLocation;
	}

	public void setUreaRequiredLocation(String ureaRequiredLocation) {
		this.ureaRequiredLocation = ureaRequiredLocation;
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

	public Long getUreaRequisitionReceiverId() {
		return ureaRequisitionReceiverId;
	}

	public void setUreaRequisitionReceiverId(Long ureaRequisitionReceiverId) {
		this.ureaRequisitionReceiverId = ureaRequisitionReceiverId;
	}

	public String getUreaRequisitionReceiver() {
		return ureaRequisitionReceiver;
	}

	public void setUreaRequisitionReceiver(String ureaRequisitionReceiver) {
		this.ureaRequisitionReceiver = ureaRequisitionReceiver;
	}

	public short getUreaRequisitionStatusId() {
		return ureaRequisitionStatusId;
	}

	public void setUreaRequisitionStatusId(short ureaRequisitionStatusId) {
		this.ureaRequisitionStatusId = ureaRequisitionStatusId;
	}

	public String getUreaRequisitionStatus() {
		return ureaRequisitionStatus;
	}

	public void setUreaRequisitionStatus(String ureaRequisitionStatus) {
		this.ureaRequisitionStatus = ureaRequisitionStatus;
	}

	public Double getUreaRequiredQuantity() {
		return ureaRequiredQuantity;
	}

	public void setUreaRequiredQuantity(Double ureaRequiredQuantity) {
		this.ureaRequiredQuantity = Utility.round(ureaRequiredQuantity, 2);
	}

	public Double getUreaReceivedQuantity() {
		return ureaReceivedQuantity;
	}

	public void setUreaReceivedQuantity(Double ureaReceivedQuantity) {
		this.ureaReceivedQuantity = Utility.round(ureaReceivedQuantity, 2);
	}

	public Double getUreaRejectedQuantity() {
		return ureaRejectedQuantity;
	}

	public void setUreaRejectedQuantity(Double ureaRejectedQuantity) {
		this.ureaRejectedQuantity = Utility.round(ureaRejectedQuantity, 2);
	}

	public Date getUreaRequiredDate() {
		return ureaRequiredDate;
	}

	public void setUreaRequiredDate(Date ureaRequiredDate) {
		this.ureaRequiredDate = ureaRequiredDate;
	}

	public String getUreaRequiredDateStr() {
		return ureaRequiredDateStr;
	}

	public void setUreaRequiredDateStr(String ureaRequiredDateStr) {
		this.ureaRequiredDateStr = ureaRequiredDateStr;
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
	
	public Double getUreaTransferQuantity() {
		return ureaTransferQuantity;
	}

	public void setUreaTransferQuantity(Double ureaTransferQuantity) {
		this.ureaTransferQuantity = Utility.round(ureaTransferQuantity, 2);
	}

	@Override
	public String toString() {
		return "UreaRequisitionDto [ureaRequisitionId=" + ureaRequisitionId + ", ureaRequiredLocationId="
				+ ureaRequiredLocationId + ", ureaRequiredLocation=" + ureaRequiredLocation
				+ ", ureaTransferFromLocationId=" + ureaTransferFromLocationId + ", ureaTransferFromLocation="
				+ ureaTransferFromLocation + ", ureaRequisitionSenderId=" + ureaRequisitionSenderId
				+ ", ureaRequisitionSender=" + ureaRequisitionSender + ", ureaRequisitionReceiverId="
				+ ureaRequisitionReceiverId + ", ureaRequisitionReceiver=" + ureaRequisitionReceiver
				+ ", ureaRequisitionStatusId=" + ureaRequisitionStatusId + ", ureaRequisitionStatus="
				+ ureaRequisitionStatus + ", ureaRequiredQuantity=" + ureaRequiredQuantity + ", ureaReceivedQuantity="
				+ ureaReceivedQuantity + ", ureaRejectedQuantity=" + ureaRejectedQuantity + ", ureaRequiredDate="
				+ ureaRequiredDate + ", ureaRequiredDateStr=" + ureaRequiredDateStr + ", ureaRequisitionRemark="
				+ ureaRequisitionRemark + ", createdById=" + createdById + ", createdBy=" + createdBy
				+ ", lastUpdatedById=" + lastUpdatedById + ", lastUpdatedBy=" + lastUpdatedBy + ", creationDate="
				+ creationDate + ", creationDateStr=" + creationDateStr + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", lastUpdatedDateStr=" + lastUpdatedDateStr + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + ", ureaTransferQuantity=" + ureaTransferQuantity + "]";
	}



	

	
	  

}

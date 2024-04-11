package org.fleetopgroup.persistence.dto;

import java.util.Date;


public class RequisitionApprovalDetailsDto {

	private Long approvedRequisitionId;
	
	private Long subRequisitionId;

	private short approvedTypeId;
	
	private String approvedTypeName;
	
	private short approvedStatus;
	
	private String approvedStatusName;
	
	private String remark;
	
	private String rejectRemark;
	
	private String finalRemark;
	
	private String userName;
	
	private String receiverName;
	
	private Long receiverId;
	
	private Long assignToId;
	
	private Long createdById;
	
	private Long branchId;
	
	private String branchName;
	
	private Long poId;
	
	private Long poNumber;
	
	private Integer approvedQuantity;
	
	private Integer transferedQuantity;
	
	private Integer receivedQuantity;
	
	private Integer returnedQuantity;
	
	private Long lastModifiedById;

	private Date creationOn;

	private Date lastUpdatedOn;
	
	private Integer companyId;
	
	private boolean markForDelete;

	private String manufacturerName;
	
	private Long manufacturerId;
	
	private String partName;

	private String pmuName;
	
	private String partNumber;
	
	private String pmName;
	
	private String transferedDateStr;
    

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPmuName() {
		return pmuName;
	}

	public void setPmuName(String pmuName) {
		this.pmuName = pmuName;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public Long getApprovedRequisitionId() {
		return approvedRequisitionId;
	}

	public void setApprovedRequisitionId(Long approvedRequisitionId) {
		this.approvedRequisitionId = approvedRequisitionId;
	}

	public Long getSubRequisitionId() {
		return subRequisitionId;
	}

	public void setSubRequisitionId(Long subRequisitionId) {
		this.subRequisitionId = subRequisitionId;
	}

	public short getApprovedTypeId() {
		return approvedTypeId;
	}

	public void setApprovedTypeId(short approvedTypeId) {
		this.approvedTypeId = approvedTypeId;
	}

	public String getApprovedTypeName() {
		return approvedTypeName;
	}

	public void setApprovedTypeName(String approvedTypeName) {
		this.approvedTypeName = approvedTypeName;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getPoId() {
		return poId;
	}

	public void setPoId(Long poId) {
		this.poId = poId;
	}

	public Integer getApprovedQuantity() {
		return approvedQuantity;
	}

	public void setApprovedQuantity(Integer approvedQuantity) {
		this.approvedQuantity = approvedQuantity;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getAssignToId() {
		return assignToId;
	}

	public void setAssignToId(Long assignToId) {
		this.assignToId = assignToId;
	}

	public Integer getTransferedQuantity() {
		return transferedQuantity;
	}

	public void setTransferedQuantity(Integer transferedQuantity) {
		this.transferedQuantity = transferedQuantity;
	}

	public String getApprovedStatusName() {
		return approvedStatusName;
	}

	public void setApprovedStatusName(String approvedStatusName) {
		this.approvedStatusName = approvedStatusName;
	}

	public short getApprovedStatus() {
		return approvedStatus;
	}

	public void setApprovedStatus(short approvedStatus) {
		this.approvedStatus = approvedStatus;
	}

	public Integer getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(Integer receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Long getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(Long poNumber) {
		this.poNumber = poNumber;
	}

	public String getRejectRemark() {
		return rejectRemark;
	}

	public void setRejectRemark(String rejectRemark) {
		this.rejectRemark = rejectRemark;
	}

	public Integer getReturnedQuantity() {
		return returnedQuantity;
	}

	public void setReturnedQuantity(Integer returnedQuantity) {
		this.returnedQuantity = returnedQuantity ;
	}

	public String getFinalRemark() {
		return finalRemark;
	}

	public void setFinalRemark(String finalRemark) {
		this.finalRemark = finalRemark;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public Long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequisitionApprovalDetailsDto [approvedRequisitionId=");
		builder.append(approvedRequisitionId);
		builder.append(", subRequisitionId=");
		builder.append(subRequisitionId);
		builder.append(", approvedTypeId=");
		builder.append(approvedTypeId);
		builder.append(", approvedTypeName=");
		builder.append(approvedTypeName);
		builder.append(", approvedStatus=");
		builder.append(approvedStatus);
		builder.append(", approvedStatusName=");
		builder.append(approvedStatusName);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", rejectRemark=");
		builder.append(rejectRemark);
		builder.append(", finalRemark=");
		builder.append(finalRemark);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", receiverName=");
		builder.append(receiverName);
		builder.append(", receiverId=");
		builder.append(receiverId);
		builder.append(", assignToId=");
		builder.append(assignToId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", branchId=");
		builder.append(branchId);
		builder.append(", branchName=");
		builder.append(branchName);
		builder.append(", poId=");
		builder.append(poId);
		builder.append(", poNumber=");
		builder.append(poNumber);
		builder.append(", approvedQuantity=");
		builder.append(approvedQuantity);
		builder.append(", transferedQuantity=");
		builder.append(transferedQuantity);
		builder.append(", receivedQuantity=");
		builder.append(receivedQuantity);
		builder.append(", returnedQuantity=");
		builder.append(returnedQuantity);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", creationOn=");
		builder.append(creationOn);
		builder.append(", lastUpdatedOn=");
		builder.append(lastUpdatedOn);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", manufacturerName=");
		builder.append(manufacturerName);
		builder.append(", manufacturerId=");
		builder.append(manufacturerId);
		builder.append(", partName=");
		builder.append(partName);
		builder.append(", pmuName=");
		builder.append(pmuName);
		builder.append(", partNumber=");
		builder.append(partNumber);
		builder.append(", pmName=");
		builder.append(pmName);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the transferedDateStr
	 */
	public String getTransferedDateStr() {
		return transferedDateStr;
	}

	/**
	 * @param transferedDateStr the transferedDateStr to set
	 */
	public void setTransferedDateStr(String transferedDateStr) {
		this.transferedDateStr = transferedDateStr;
	}


}

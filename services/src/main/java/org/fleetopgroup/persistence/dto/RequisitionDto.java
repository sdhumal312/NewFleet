package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class RequisitionDto {
	
	private Long requisitionId;
	
	private Long requisitionNum;
	
	private String requisitionNumStr;

	private short requisitionStatusId;
	
	private String requisitionStatusName;
	
	private Long createdById;
	
	private Long location;
	
	private String locationName;

	private Long assignTo;
	
	private String assignToName;
	
	private String refNumber;
	
	private String remark;
	
	private Long lastModifiedById;
	
	private Date requireOn;

	private String requireOnStr;

	private Date creationOn;
	
	private String creationOnStr;

	private Date lastUpdatedOn;
	
	private String lastUpdatedOnStr;
	
	private Integer companyId;
	
	private boolean markForDelete;
	
	

	private short requisitionType;
	
	private String requisitionTypeName;
	
	public Long getRequisitionId() {
		return requisitionId;
	}

	public void setRequisitionId(Long requisitionId) {
		this.requisitionId = requisitionId;
	}

	public Long getRequisitionNum() {
		return requisitionNum;
	}

	public void setRequisitionNum(Long requisitionNum) {
		this.requisitionNum = requisitionNum;
	}

	public String getRequisitionNumStr() {
		return requisitionNumStr;
	}

	public void setRequisitionNumStr(String requisitionNumStr) {
		this.requisitionNumStr = requisitionNumStr;
	}

	public short getRequisitionStatusId() {
		return requisitionStatusId;
	}

	public void setRequisitionStatusId(short requisitionStatusId) {
		this.requisitionStatusId = requisitionStatusId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
		this.location = location;
	}

	public Long getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(Long assignTo) {
		this.assignTo = assignTo;
	}

	public String getAssignToName() {
		return assignToName;
	}

	public void setAssignToName(String assignToName) {
		this.assignToName = assignToName;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getRequireOn() {
		return requireOn;
	}

	public void setRequireOn(Date requireOn) {
		this.requireOn = requireOn;
	}

	public String getRequireOnStr() {
		return requireOnStr;
	}

	public void setRequireOnStr(String requireOnStr) {
		this.requireOnStr = requireOnStr;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public String getCreationOnStr() {
		return creationOnStr;
	}

	public void setCreationOnStr(String creationOnStr) {
		this.creationOnStr = creationOnStr;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastUpdatedOnStr() {
		return lastUpdatedOnStr;
	}

	public void setLastUpdatedOnStr(String lastUpdatedOnStr) {
		this.lastUpdatedOnStr = lastUpdatedOnStr;
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

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getRequisitionStatusName() {
		return requisitionStatusName;
	}

	public void setRequisitionStatusName(String requisitionStatusName) {
		this.requisitionStatusName = requisitionStatusName;
	}

	
	public short getRequisitionType() {
		return requisitionType;
	}

	public void setRequisitionType(short requisitionType) {
		this.requisitionType = requisitionType;
	}

	public String getRequisitionTypeName() {
		return requisitionTypeName;
	}

	public void setRequisitionTypeName(String requisitionTypeName) {
		this.requisitionTypeName = requisitionTypeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignTo == null) ? 0 : assignTo.hashCode());
		result = prime * result + ((assignToName == null) ? 0 : assignToName.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationOn == null) ? 0 : creationOn.hashCode());
		result = prime * result + ((creationOnStr == null) ? 0 : creationOnStr.hashCode());
		result = prime * result + ((lastModifiedById == null) ? 0 : lastModifiedById.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + ((lastUpdatedOnStr == null) ? 0 : lastUpdatedOnStr.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((locationName == null) ? 0 : locationName.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((refNumber == null) ? 0 : refNumber.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((requireOn == null) ? 0 : requireOn.hashCode());
		result = prime * result + ((requireOnStr == null) ? 0 : requireOnStr.hashCode());
		result = prime * result + ((requisitionId == null) ? 0 : requisitionId.hashCode());
		result = prime * result + ((requisitionNum == null) ? 0 : requisitionNum.hashCode());
		result = prime * result + ((requisitionNumStr == null) ? 0 : requisitionNumStr.hashCode());
		result = prime * result + requisitionStatusId;
		result = prime * result + ((requisitionStatusName == null) ? 0 : requisitionStatusName.hashCode());
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
		RequisitionDto other = (RequisitionDto) obj;
		if (assignTo == null) {
			if (other.assignTo != null)
				return false;
		} else if (!assignTo.equals(other.assignTo))
			return false;
		if (assignToName == null) {
			if (other.assignToName != null)
				return false;
		} else if (!assignToName.equals(other.assignToName))
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
		if (creationOn == null) {
			if (other.creationOn != null)
				return false;
		} else if (!creationOn.equals(other.creationOn))
			return false;
		if (creationOnStr == null) {
			if (other.creationOnStr != null)
				return false;
		} else if (!creationOnStr.equals(other.creationOnStr))
			return false;
		if (lastModifiedById == null) {
			if (other.lastModifiedById != null)
				return false;
		} else if (!lastModifiedById.equals(other.lastModifiedById))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (lastUpdatedOnStr == null) {
			if (other.lastUpdatedOnStr != null)
				return false;
		} else if (!lastUpdatedOnStr.equals(other.lastUpdatedOnStr))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (locationName == null) {
			if (other.locationName != null)
				return false;
		} else if (!locationName.equals(other.locationName))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (refNumber == null) {
			if (other.refNumber != null)
				return false;
		} else if (!refNumber.equals(other.refNumber))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (requireOn == null) {
			if (other.requireOn != null)
				return false;
		} else if (!requireOn.equals(other.requireOn))
			return false;
		if (requireOnStr == null) {
			if (other.requireOnStr != null)
				return false;
		} else if (!requireOnStr.equals(other.requireOnStr))
			return false;
		if (requisitionId == null) {
			if (other.requisitionId != null)
				return false;
		} else if (!requisitionId.equals(other.requisitionId))
			return false;
		if (requisitionNum == null) {
			if (other.requisitionNum != null)
				return false;
		} else if (!requisitionNum.equals(other.requisitionNum))
			return false;
		if (requisitionNumStr == null) {
			if (other.requisitionNumStr != null)
				return false;
		} else if (!requisitionNumStr.equals(other.requisitionNumStr))
			return false;
		if (requisitionStatusId != other.requisitionStatusId)
			return false;
		if (requisitionStatusName == null) {
			if (other.requisitionStatusName != null)
				return false;
		} else if (!requisitionStatusName.equals(other.requisitionStatusName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequisitionDto [requisitionId=");
		builder.append(requisitionId);
		builder.append(", requisitionNum=");
		builder.append(requisitionNum);
		builder.append(", requisitionNumStr=");
		builder.append(requisitionNumStr);
		builder.append(", requisitionStatusId=");
		builder.append(requisitionStatusId);
		builder.append(", requisitionStatusName=");
		builder.append(requisitionStatusName);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", location=");
		builder.append(location);
		builder.append(", locationName=");
		builder.append(locationName);
		builder.append(", assignTo=");
		builder.append(assignTo);
		builder.append(", assignToName=");
		builder.append(assignToName);
		builder.append(", refNumber=");
		builder.append(refNumber);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", requireOn=");
		builder.append(requireOn);
		builder.append(", requireOnStr=");
		builder.append(requireOnStr);
		builder.append(", creationOn=");
		builder.append(creationOn);
		builder.append(", creationOnStr=");
		builder.append(creationOnStr);
		builder.append(", lastUpdatedOn=");
		builder.append(lastUpdatedOn);
		builder.append(", lastUpdatedOnStr=");
		builder.append(lastUpdatedOnStr);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", getRequisitionId()=");
		builder.append(getRequisitionId());
		builder.append(", getRequisitionNum()=");
		builder.append(getRequisitionNum());
		builder.append(", getRequisitionNumStr()=");
		builder.append(getRequisitionNumStr());
		builder.append(", getRequisitionStatusId()=");
		builder.append(getRequisitionStatusId());
		builder.append(", getCreatedById()=");
		builder.append(getCreatedById());
		builder.append(", getLocation()=");
		builder.append(getLocation());
		builder.append(", getAssignTo()=");
		builder.append(getAssignTo());
		builder.append(", getAssignToName()=");
		builder.append(getAssignToName());
		builder.append(", getRefNumber()=");
		builder.append(getRefNumber());
		builder.append(", getRemark()=");
		builder.append(getRemark());
		builder.append(", getLastModifiedById()=");
		builder.append(getLastModifiedById());
		builder.append(", getRequireOn()=");
		builder.append(getRequireOn());
		builder.append(", getRequireOnStr()=");
		builder.append(getRequireOnStr());
		builder.append(", getCreationOn()=");
		builder.append(getCreationOn());
		builder.append(", getCreationOnStr()=");
		builder.append(getCreationOnStr());
		builder.append(", getLastUpdatedOn()=");
		builder.append(getLastUpdatedOn());
		builder.append(", getLastUpdatedOnStr()=");
		builder.append(getLastUpdatedOnStr());
		builder.append(", getCompanyId()=");
		builder.append(getCompanyId());
		builder.append(", isMarkForDelete()=");
		builder.append(isMarkForDelete());
		builder.append(", getLocationName()=");
		builder.append(getLocationName());
		builder.append(", getRequisitionStatusName()=");
		builder.append(getRequisitionStatusName());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}


}

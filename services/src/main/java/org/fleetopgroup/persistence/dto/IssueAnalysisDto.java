package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class IssueAnalysisDto {

	private Long	issueAnalysisId;
	
	private Long	issueId;
	
	private Long	issueNumber;
	
	private String	reason;
	
	private boolean isAvoidable;
	
	private String isAvoidableStr;
	
	private String	tempSolution;
	
	private String fixSolution;
	
	private String futurePlan;
	
	private Long createdById;
	
	private String createdBy;

	private Long lastModifiedById;
	
	private Long permanentIssueId;
	
	private String lastModifiedBy;

	private Date creationOn;
	
	private String creationDate;

	private Date lastUpdatedOn;
	
	private String lastUpdatedDate;
	
	
	private Integer companyId;
	
	private boolean isTemporary;
	
	private boolean markForDelete;

	public IssueAnalysisDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIssueAnalysisId() {
		return issueAnalysisId;
	}

	public void setIssueAnalysisId(Long issueAnalysisId) {
		this.issueAnalysisId = issueAnalysisId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Long getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(Long issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isAvoidable() {
		return isAvoidable;
	}

	public void setAvoidable(boolean isAvoidable) {
		this.isAvoidable = isAvoidable;
	}

	public String getIsAvoidableStr() {
		return isAvoidableStr;
	}

	public void setIsAvoidableStr(String isAvoidableStr) {
		this.isAvoidableStr = isAvoidableStr;
	}

	public String getTempSolution() {
		return tempSolution;
	}

	public void setTempSolution(String tempSolution) {
		this.tempSolution = tempSolution;
	}

	public String getFixSolution() {
		return fixSolution;
	}

	public void setFixSolution(String fixSolution) {
		this.fixSolution = fixSolution;
	}

	public String getFuturePlan() {
		return futurePlan;
	}

	public void setFuturePlan(String futurePlan) {
		this.futurePlan = futurePlan;
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

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isTemporary() {
		return isTemporary;
	}

	public void setTemporary(boolean isTemporary) {
		this.isTemporary = isTemporary;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getPermanentIssueId() {
		return permanentIssueId;
	}

	public void setPermanentIssueId(Long permanentIssueId) {
		this.permanentIssueId = permanentIssueId;
	}

	@Override
	public String toString() {
		return "IssueAnalysisDto [issueAnalysisId=" + issueAnalysisId + ", issueId=" + issueId + ", issueNumber="
				+ issueNumber + ", reason=" + reason + ", isAvoidable=" + isAvoidable + ", isAvoidableStr="
				+ isAvoidableStr + ", tempSolution=" + tempSolution + ", fixSolution=" + fixSolution + ", futurePlan="
				+ futurePlan + ", createdById=" + createdById + ", createdBy=" + createdBy + ", lastModifiedById="
				+ lastModifiedById + ", lastModifiedBy=" + lastModifiedBy + ", creationOn=" + creationOn
				+ ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn + ", lastUpdatedDate="
				+ lastUpdatedDate + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}

	
	

}

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
@Table(name = "IssueAnalysis")
public class IssueAnalysis implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "issueAnalysisId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	issueAnalysisId;
	
	@Column(name = "issueId")
	private Long	issueId;
	
	@Column(name = "reason", length = 500)
	private String	reason;
	
	@Column(name = "isAvoidable", nullable = false)
	private boolean isAvoidable;
	
	@Column(name = "tempSolution", length = 500)
	private String	tempSolution;
	
	@Column(name = "fixSolution", length = 500)
	private String fixSolution;
	
	@Column(name = "futurePlan", length = 500)
	private String futurePlan;
	
	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "isTemporary")
	private boolean isTemporary;
	
	@Column(name = "permanentIssueId")
	private Long permanentIssueId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public IssueAnalysis() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IssueAnalysis(Long issueAnalysisId, Long issueId, String reason, boolean isAvoidable, String tempSolution,
			String fixSolution, String futurePlan, Long createdById, Long lastModifiedById, Date creationOn,
			Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.issueAnalysisId = issueAnalysisId;
		this.issueId = issueId;
		this.reason = reason;
		this.isAvoidable = isAvoidable;
		this.tempSolution = tempSolution;
		this.fixSolution = fixSolution;
		this.futurePlan = futurePlan;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.creationOn = creationOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationOn == null) ? 0 : creationOn.hashCode());
		result = prime * result + ((fixSolution == null) ? 0 : fixSolution.hashCode());
		result = prime * result + ((futurePlan == null) ? 0 : futurePlan.hashCode());
		result = prime * result + (isAvoidable ? 1231 : 1237);
		result = prime * result + ((issueAnalysisId == null) ? 0 : issueAnalysisId.hashCode());
		result = prime * result + ((issueId == null) ? 0 : issueId.hashCode());
		result = prime * result + ((lastModifiedById == null) ? 0 : lastModifiedById.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((tempSolution == null) ? 0 : tempSolution.hashCode());
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
		IssueAnalysis other = (IssueAnalysis) obj;
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
		if (fixSolution == null) {
			if (other.fixSolution != null)
				return false;
		} else if (!fixSolution.equals(other.fixSolution))
			return false;
		if (futurePlan == null) {
			if (other.futurePlan != null)
				return false;
		} else if (!futurePlan.equals(other.futurePlan))
			return false;
		if (isAvoidable != other.isAvoidable)
			return false;
		if (issueAnalysisId == null) {
			if (other.issueAnalysisId != null)
				return false;
		} else if (!issueAnalysisId.equals(other.issueAnalysisId))
			return false;
		if (issueId == null) {
			if (other.issueId != null)
				return false;
		} else if (!issueId.equals(other.issueId))
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
		if (markForDelete != other.markForDelete)
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (tempSolution == null) {
			if (other.tempSolution != null)
				return false;
		} else if (!tempSolution.equals(other.tempSolution))
			return false;
		return true;
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

	public boolean isTemporary() {
		return isTemporary;
	}

	public void setTemporary(boolean isTemporary) {
		this.isTemporary = isTemporary;
	}

	public Long getPermanentIssueId() {
		return permanentIssueId;
	}

	public void setPermanentIssueId(Long permanentIssueId) {
		this.permanentIssueId = permanentIssueId;
	}

	@Override
	public String toString() {
		return "IssueAnalysis [issueAnalysisId=" + issueAnalysisId + ", issueId=" + issueId + ", reason=" + reason
				+ ", isAvoidable=" + isAvoidable + ", tempSolution=" + tempSolution + ", fixSolution=" + fixSolution
				+ ", futurePlan=" + futurePlan + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", creationOn=" + creationOn + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + "]";
	}
	

}

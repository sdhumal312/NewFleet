package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Jobtype")
public class JobType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "Job_Type", unique = false, nullable = false, length = 150)
	private String Job_Type;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Job_id")
	private Integer Job_id;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdBy", nullable = true)
	private String createdBy;

	@Column(name = "createdById", nullable = false)
	private Long createdById;
	
	@Column(name = "createdOn", nullable = false)
	private Timestamp createdOn;
	
	@Column(name = "lastModifiedBy", nullable = true)
	private String lastModifiedBy;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public JobType() {
		super();
	}

	public JobType(String job_Type) {
		super();
		Job_Type = job_Type;
	}

	public String getJob_Type() {
		return Job_Type;
	}

	public void setJob_Type(String Job_Type) {
		this.Job_Type = Job_Type;
	}

	public Integer getJob_id() {
		return Job_id;
	}

	public void setJob_id(Integer Job_id) {
		this.Job_id = Job_id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Job_Type == null) ? 0 : Job_Type.hashCode());
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
		JobType other = (JobType) obj;
		if (Job_Type == null) {
			if (other.Job_Type != null)
				return false;
		} else if (!Job_Type.equals(other.Job_Type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobType [Job_Type=");
		builder.append(Job_Type);
		builder.append(", company_id=");
		builder.append(companyId);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append(", Job_id=");
		builder.append(Job_id);
		builder.append("]");
		return builder.toString();
	}

}
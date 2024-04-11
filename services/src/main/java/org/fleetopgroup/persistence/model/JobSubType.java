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
@Table(name = "Jobsubtype")
public class JobSubType implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Job_Subid")
	private Integer Job_Subid;
		
	@Column(name = "Job_Type", nullable = true, length = 150)
	private String Job_Type;
	
	@Column(name = "Job_TypeId", nullable = true)
	private Integer	Job_TypeId;

	@Column(name = "Job_ROT", unique = false, nullable = true, length = 150)
	private String Job_ROT;

	@Column(name = "Job_ROT_number", length = 30)
	private String Job_ROT_number;

	@Column(name = "Job_ROT_hour", length = 6)
	private String Job_ROT_hour;

	@Column(name = "Job_ROT_amount", length = 6)
	private String Job_ROT_amount;
	
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
	
	@Column(name = "Job_ROT_Service_Reminder", nullable = false)
	Boolean Job_ROT_Service_Reminder;
	

	public JobSubType() {
		super();
	}
	
	public JobSubType(Integer job_Subid, String job_Type, String job_ROT, String job_ROT_number, String job_ROT_hour,
			String job_ROT_amount,Integer companyId, String createdBy, Timestamp createdOn, String lastModifiedBy, Timestamp lastModifiedOn,
			Boolean Job_ROT_Service_Reminder) {
		super();
		Job_Subid = job_Subid;
		Job_Type = job_Type;
		Job_ROT = job_ROT;
		Job_ROT_number = job_ROT_number;
		Job_ROT_hour = job_ROT_hour;
		Job_ROT_amount = job_ROT_amount;
		this.companyId = companyId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedOn = lastModifiedOn;
		this.Job_ROT_Service_Reminder = Job_ROT_Service_Reminder;
	}


	public String getJob_Type() {
		return Job_Type;
	}

	public String getJob_ROT() {
		return Job_ROT;
	}

	public void setJob_ROT(String job_ROT) {
		Job_ROT = job_ROT;
	}

	public String getJob_ROT_number() {
		return Job_ROT_number;
	}

	public void setJob_ROT_number(String job_ROT_number) {
		Job_ROT_number = job_ROT_number;
	}

	public String getJob_ROT_hour() {
		return Job_ROT_hour;
	}

	public void setJob_ROT_hour(String job_ROT_hour) {
		Job_ROT_hour = job_ROT_hour;
	}

	public String getJob_ROT_amount() {
		return Job_ROT_amount;
	}

	public void setJob_ROT_amount(String job_ROT_amount) {
		Job_ROT_amount = job_ROT_amount;
	}

	public void setJob_Type(String Job_Type) {
		this.Job_Type = Job_Type;
	}

	public Integer getJob_Subid() {
		return Job_Subid;
	}

	public void setJob_Subid(Integer Job_Subid) {
		this.Job_Subid = Job_Subid;
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

	/**
	 * @return the job_TypeId
	 */
	public Integer getJob_TypeId() {
		return Job_TypeId;
	}

	/**
	 * @param job_TypeId the job_TypeId to set
	 */
	public void setJob_TypeId(Integer job_TypeId) {
		Job_TypeId = job_TypeId;
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
	

	public Boolean isJob_ROT_Service_Reminder() {
		return Job_ROT_Service_Reminder;
	}

	public void setJob_ROT_Service_Reminder(Boolean job_ROT_Service_Reminder) {
		Job_ROT_Service_Reminder = job_ROT_Service_Reminder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Job_ROT == null) ? 0 : Job_ROT.hashCode());
		result = prime * result + ((Job_ROT_amount == null) ? 0 : Job_ROT_amount.hashCode());
		result = prime * result + ((Job_ROT_number == null) ? 0 : Job_ROT_number.hashCode());
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
		JobSubType other = (JobSubType) obj;
		if (Job_ROT == null) {
			if (other.Job_ROT != null)
				return false;
		} else if (!Job_ROT.equals(other.Job_ROT))
			return false;
		if (Job_ROT_amount == null) {
			if (other.Job_ROT_amount != null)
				return false;
		} else if (!Job_ROT_amount.equals(other.Job_ROT_amount))
			return false;
		if (Job_ROT_number == null) {
			if (other.Job_ROT_number != null)
				return false;
		} else if (!Job_ROT_number.equals(other.Job_ROT_number))
			return false;
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
		builder.append("JobSubType [Job_Subid=");
		builder.append(Job_Subid);
		builder.append(", Job_Type=");
		builder.append(Job_Type);
		builder.append(", Job_ROT=");
		builder.append(Job_ROT);
		builder.append(", Job_ROT_number=");
		builder.append(Job_ROT_number);
		builder.append(", Job_ROT_hour=");
		builder.append(Job_ROT_hour);
		builder.append(", Job_ROT_amount=");
		builder.append(Job_ROT_amount);
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
		builder.append(", Job_ROT_Service_Reminder=");
		builder.append(Job_ROT_Service_Reminder);
		builder.append("]");
		return builder.toString();
	}
	
	
}
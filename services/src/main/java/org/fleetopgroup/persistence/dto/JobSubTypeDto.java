package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 *
 *
 */
public class JobSubTypeDto {
	
	
	private Integer Job_Subid;
	
	@NotNull
	@Size(min = 1)
	private String Job_Type;
	
	private String Job_ROT;

	private String Job_ROT_number;

	private String Job_ROT_hour;

	private String Job_ROT_amount;
	
	private Integer	Job_TypeId;
	
	private Boolean ROT_Service_Reminder;
	
	private Integer companyId;
	
	

	public Boolean getROT_Service_Reminder() {
		return ROT_Service_Reminder;
	}

	public void setROT_Service_Reminder(Boolean rOT_Service_Reminder) {
		ROT_Service_Reminder = rOT_Service_Reminder;
	}

	public Integer getJob_Subid() {
		return Job_Subid;
	}

	public void setJob_Subid(Integer Job_Subid) {
		this.Job_Subid = Job_Subid;
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

	public String getJob_Type() {
		return Job_Type;
	}

	public void setJob_Type(String Job_Type) {
		this.Job_Type = Job_Type;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobSubTypeDto [Job_Subid=");
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
		builder.append(", ROT_Service_Reminder=");
		builder.append(ROT_Service_Reminder);
		builder.append("]");
		return builder.toString();
	}

	
}

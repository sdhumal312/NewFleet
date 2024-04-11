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
@Table(name = "JobSubTypeHistory")
public class JobSubTypeHistory implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Job_Sub_Type_History_Id")
	private Integer Job_Sub_Type_History_Id;

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

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "Job_ROT_Service_Reminder", nullable = false)
	boolean Job_ROT_Service_Reminder;

	
	public Integer getJob_Sub_Type_History_Id() {
		return Job_Sub_Type_History_Id;
	}

	public void setJob_Sub_Type_History_Id(Integer job_Sub_Type_History_Id) {
		Job_Sub_Type_History_Id = job_Sub_Type_History_Id;
	}

	public Integer getJob_Subid() {
		return Job_Subid;
	}

	public void setJob_Subid(Integer job_Subid) {
		Job_Subid = job_Subid;
	}

	public String getJob_Type() {
		return Job_Type;
	}

	public void setJob_Type(String job_Type) {
		Job_Type = job_Type;
	}

	public Integer getJob_TypeId() {
		return Job_TypeId;
	}

	public void setJob_TypeId(Integer job_TypeId) {
		Job_TypeId = job_TypeId;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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

	public Boolean getJob_ROT_Service_Reminder() {
		return Job_ROT_Service_Reminder;
	}

	public void setJob_ROT_Service_Reminder(Boolean job_ROT_Service_Reminder) {
		Job_ROT_Service_Reminder = job_ROT_Service_Reminder;
	}
	
	
}
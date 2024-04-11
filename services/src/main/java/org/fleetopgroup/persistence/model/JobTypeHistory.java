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
@Table(name = "JobTypeHistory")
public class JobTypeHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Job_Type_History_Id")
	private Integer Job_Type_History_Id;

	@Column(name = "Job_id")
	private Integer Job_id;

	@Column(name = "Job_Type", unique = false, nullable = false, length = 150)
	private String Job_Type;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Integer getJob_Type_History_Id() {
		return Job_Type_History_Id;
	}

	public void setJob_Type_History_Id(Integer job_Type_History_Id) {
		Job_Type_History_Id = job_Type_History_Id;
	}

	public Integer getJob_id() {
		return Job_id;
	}

	public void setJob_id(Integer job_id) {
		Job_id = job_id;
	}

	public String getJob_Type() {
		return Job_Type;
	}

	public void setJob_Type(String job_Type) {
		Job_Type = job_Type;
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
}
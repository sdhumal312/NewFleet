package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DailyTripCountMailConfiguration")
public class DailyTripCountMailConfiguration implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripconfigId")
	private Long tripconfigId;
	
	@Column(name = "emailId")
	private String emailId;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Timestamp	lastModifiedOn;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	
	public DailyTripCountMailConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DailyTripCountMailConfiguration(Long tripconfigId, String emailId, Integer companyId, Timestamp createdOn,
			Timestamp lastModifiedOn, Long createdById, Long lastModifiedById, boolean markForDelete) {
		super();
		this.tripconfigId = tripconfigId;
		this.emailId = emailId;
		this.companyId = companyId;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.markForDelete = markForDelete;
	}

	public Long getTripconfigId() {
		return tripconfigId;
	}

	public void setTripconfigId(Long tripconfigId) {
		this.tripconfigId = tripconfigId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DailyTripCountMailConfiguration [tripconfigId=" + tripconfigId + ", emailId=" + emailId + ", companyId="
				+ companyId + ", createdOn=" + createdOn + ", lastModifiedOn=" + lastModifiedOn + ", createdById="
				+ createdById + ", lastModifiedById=" + lastModifiedById + ", markForDelete=" + markForDelete + "]";
	}
}

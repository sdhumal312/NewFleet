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
@Table(name = "SpotSurveyorDetails")
public class SpotSurveyorDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "spotSurveyorDetailsId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	spotSurveyorDetailsId;
	
	@Column(name = "accidentId")
	private Long	accidentId;
	
	@Column(name = "spotSurveyorName")
	private String	spotSurveyorName;
	
	@Column(name = "spotSurveyorMobile")
	private String	spotSurveyorMobile;
	
	@Column(name = "spotSurveyorCompany")
	private String	spotSurveyorCompany;
	
	@Column(name = "spotSurveyorRemark", length = 512)
	private String spotSurveyorRemark;
	
	@Column(name = "spotSurveyorDate")
	private Date	spotSurveyorDate;
	
	@Column(name = "spotSurveyorCompletionRemark")
	private String	spotSurveyorCompletionRemark;
	
	@Column(name = "spotSurveyorCompletionDate")
	private Date	spotSurveyorCompletionDate;
	
	@Column(name = "created", nullable = false, updatable = false)
	private Date	created;
	
	@Column(name = "lastUpdated")
	private Date	lastUpdated;
	
	@Column(name = "createdById" , nullable = false, updatable = false)
	private Long	createdById;
	
	@Column(name = "lastUpdatedById")
	private Long	lastUpdatedById;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;

	public Long getSpotSurveyorDetailsId() {
		return spotSurveyorDetailsId;
	}

	public void setSpotSurveyorDetailsId(Long spotSurveyorDetailsId) {
		this.spotSurveyorDetailsId = spotSurveyorDetailsId;
	}

	public Long getAccidentId() {
		return accidentId;
	}

	public void setAccidentId(Long accidentId) {
		this.accidentId = accidentId;
	}

	public String getSpotSurveyorName() {
		return spotSurveyorName;
	}

	public void setSpotSurveyorName(String spotSurveyorName) {
		this.spotSurveyorName = spotSurveyorName;
	}

	public String getSpotSurveyorMobile() {
		return spotSurveyorMobile;
	}

	public void setSpotSurveyorMobile(String spotSurveyorMobile) {
		this.spotSurveyorMobile = spotSurveyorMobile;
	}

	public String getSpotSurveyorCompany() {
		return spotSurveyorCompany;
	}

	public void setSpotSurveyorCompany(String spotSurveyorCompany) {
		this.spotSurveyorCompany = spotSurveyorCompany;
	}

	public String getSpotSurveyorRemark() {
		return spotSurveyorRemark;
	}

	public void setSpotSurveyorRemark(String spotSurveyorRemark) {
		this.spotSurveyorRemark = spotSurveyorRemark;
	}

	public Date getSpotSurveyorDate() {
		return spotSurveyorDate;
	}

	public void setSpotSurveyorDate(Date spotSurveyorDate) {
		this.spotSurveyorDate = spotSurveyorDate;
	}

	public String getSpotSurveyorCompletionRemark() {
		return spotSurveyorCompletionRemark;
	}

	public void setSpotSurveyorCompletionRemark(String spotSurveyorCompletionRemark) {
		this.spotSurveyorCompletionRemark = spotSurveyorCompletionRemark;
	}

	public Date getSpotSurveyorCompletionDate() {
		return spotSurveyorCompletionDate;
	}

	public void setSpotSurveyorCompletionDate(Date spotSurveyorCompletionDate) {
		this.spotSurveyorCompletionDate = spotSurveyorCompletionDate;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
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
	

	}

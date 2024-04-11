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
@Table(name = "IssueBreakDownDetails")
public class IssueBreakDownDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "issueBreakDownDetailsId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	issueBreakDownDetailsId;
	
	@Column(name = "issueId")
	private Long	issueId;
	
	@Column(name = "tripNumber")
	private String tripNumber;
	
	@Column(name = "breakDownLocation")
	private String breakDownLocation;
	
	@Column(name = "isVehicleReplaced")
	private Boolean isVehicleReplaced;
	
	@Column(name = "replacedWithVid")
	private Integer	replacedWithVid;
	
	@Column(name = "vehicleReplacedLocation")
	private String vehicleReplacedLocation;
	
	@Column(name = "isTripCancelled")
	private Boolean isTripCancelled;
	
	@Column(name = "cancelledKM")
	private Double	cancelledKM; 
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "changedToBreakDown",nullable = false)
	private boolean changedToBreakDown;
	
	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "markForDelete")
	private boolean markForDelete;
	

	public Long getIssueBreakDownDetailsId() {
		return issueBreakDownDetailsId;
	}

	public void setIssueBreakDownDetailsId(Long issueBreakDownDetailsId) {
		this.issueBreakDownDetailsId = issueBreakDownDetailsId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getTripNumber() {
		return tripNumber;
	}

	public void setTripNumber(String tripNumber) {
		this.tripNumber = tripNumber;
	}

	public String getBreakDownLocation() {
		return breakDownLocation;
	}

	public void setBreakDownLocation(String breakDownLocation) {
		this.breakDownLocation = breakDownLocation;
	}

	public Boolean getIsVehicleReplaced() {
		return isVehicleReplaced;
	}

	public void setIsVehicleReplaced(Boolean isVehicleReplaced) {
		this.isVehicleReplaced = isVehicleReplaced;
	}

	public Integer getReplacedWithVid() {
		return replacedWithVid;
	}

	public void setReplacedWithVid(Integer replacedWithVid) {
		this.replacedWithVid = replacedWithVid;
	}

	public String getVehicleReplacedLocation() {
		return vehicleReplacedLocation;
	}

	public void setVehicleReplacedLocation(String vehicleReplacedLocation) {
		this.vehicleReplacedLocation = vehicleReplacedLocation;
	}

	public Boolean getIsTripCancelled() {
		return isTripCancelled;
	}

	public void setIsTripCancelled(Boolean isTripCancelled) {
		this.isTripCancelled = isTripCancelled;
	}

	public Double getCancelledKM() {
		return cancelledKM;
	}

	public void setCancelledKM(Double cancelledKM) {
		this.cancelledKM = cancelledKM;
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

	public boolean isChangedToBreakDown() {
		return changedToBreakDown;
	}

	public void setChangedToBreakDown(boolean changedToBreakDown) {
		this.changedToBreakDown = changedToBreakDown;
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
	
	
	
	
}

package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class TripRouteExpenseRangeDto {


	private Long tripRouteExpenseRangeId;
	
	private Long routeId;
	
	private String routeName;
	
	private Long expenseId;
	
	private String expenseName;
	
	private Double expenseMaxLimt;
	
	private Integer companyId;
	
	private Long createdById;
	
	private Long lastModifiedById;
	
	private String createdBy;
	
	private Long lastModifiedBy;
	
	private Timestamp	createdOn;
	
	private Timestamp	lastModifiedOn;
	
	private String	createdDate;
	
	private String	lastModifiedDate;
	
	private Integer tripExpenseId;

	public TripRouteExpenseRangeDto() {
		super();
	}

	public Long getTripRouteExpenseRangeId() {
		return tripRouteExpenseRangeId;
	}

	public void setTripRouteExpenseRangeId(Long tripRouteExpenseRangeId) {
		this.tripRouteExpenseRangeId = tripRouteExpenseRangeId;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public Double getExpenseMaxLimt() {
		return expenseMaxLimt;
	}

	public void setExpenseMaxLimt(Double expenseMaxLimt) {
		this.expenseMaxLimt = Utility.round(expenseMaxLimt, 2);
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getTripExpenseId() {
		return tripExpenseId;
	}

	public void setTripExpenseId(Integer tripExpenseId) {
		this.tripExpenseId = tripExpenseId;
	}

	@Override
	public String toString() {
		return "TripRouteExpenseRangeDto [tripRouteExpenseRangeId=" + tripRouteExpenseRangeId + ", routeId=" + routeId
				+ ", routeName=" + routeName + ", expenseId=" + expenseId + ", expenseName=" + expenseName
				+ ", expenseMaxLimt=" + expenseMaxLimt + ", companyId=" + companyId + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", createdBy=" + createdBy + ", lastModifiedBy="
				+ lastModifiedBy + ", createdOn=" + createdOn + ", lastModifiedOn=" + lastModifiedOn + ", createdDate="
				+ createdDate + ", lastModifiedDate=" + lastModifiedDate + "]";
	}

	
	

}

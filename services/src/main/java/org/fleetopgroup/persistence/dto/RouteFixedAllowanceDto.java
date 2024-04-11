package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class RouteFixedAllowanceDto {

	private Long routeFixedAllowanceId;
	
	private Integer expenseId;
	
	private Double	amount;
	
	private Integer driJobId;
	
	private Integer routeId;
	
	private Integer	companyId;
	
	private Date	created;
	
	private Date	lastUpdatdOn;
	
	private Long	createdById;
	
	private Long	lastUpdatdById;
	
	private String driverJobType;
	
	private String	routeName;
	
	private String	expenseType;

	public Long getRouteFixedAllowanceId() {
		return routeFixedAllowanceId;
	}

	public void setRouteFixedAllowanceId(Long routeFixedAllowanceId) {
		this.routeFixedAllowanceId = routeFixedAllowanceId;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount =Utility.round(amount, 2);
	}

	public Integer getDriJobId() {
		return driJobId;
	}

	public void setDriJobId(Integer driJobId) {
		this.driJobId = driJobId;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastUpdatdOn() {
		return lastUpdatdOn;
	}

	public void setLastUpdatdOn(Date lastUpdatdOn) {
		this.lastUpdatdOn = lastUpdatdOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatdById() {
		return lastUpdatdById;
	}

	public void setLastUpdatdById(Long lastUpdatdById) {
		this.lastUpdatdById = lastUpdatdById;
	}

	public String getDriverJobType() {
		return driverJobType;
	}

	public void setDriverJobType(String driverJobType) {
		this.driverJobType = driverJobType;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((driJobId == null) ? 0 : driJobId.hashCode());
		result = prime * result + ((expenseId == null) ? 0 : expenseId.hashCode());
		result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
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
		RouteFixedAllowanceDto other = (RouteFixedAllowanceDto) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (driJobId == null) {
			if (other.driJobId != null)
				return false;
		} else if (!driJobId.equals(other.driJobId))
			return false;
		if (expenseId == null) {
			if (other.expenseId != null)
				return false;
		} else if (!expenseId.equals(other.expenseId))
			return false;
		if (routeId == null) {
			if (other.routeId != null)
				return false;
		} else if (!routeId.equals(other.routeId))
			return false;
		return true;
	}
	
	
	
	
}

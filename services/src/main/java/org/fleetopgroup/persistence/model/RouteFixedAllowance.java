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
@Table(name = "RouteFixedAllowance")
public class RouteFixedAllowance	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "routeFixedAllowanceId")
	private Long routeFixedAllowanceId;
	
	@Column(name = "expenseId")
	private Integer expenseId;
	
	@Column(name = "amount")
	private Double	amount;
	
	@Column(name = "driJobId")
	private Integer driJobId;
	
	@Column(name = "routeId")
	private Integer routeId;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;
	
	@Column(name = "created", nullable = false, updatable = false)
	private Date	created;
	
	@Column(name = "lastUpdatdOn")
	private Date	lastUpdatdOn;
	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long	createdById;
	
	@Column(name = "lastUpdatdById")
	private Long	lastUpdatdById;

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
		this.amount = amount;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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
		RouteFixedAllowance other = (RouteFixedAllowance) obj;
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

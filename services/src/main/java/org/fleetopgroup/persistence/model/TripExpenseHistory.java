package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TripExpenseHistory")
public class TripExpenseHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TripExpenseHistoryId")
	private Integer TripExpenseHistoryId;

	@Column(name = "expenseID")
	private Integer expenseID;

	@Column(name = "expenseName", unique = false, nullable = false, length = 200)
	private String expenseName;

	@Column(name = "expenseRemarks", length = 200)
	private String expenseRemarks;

	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "lastupdated", nullable = true)
	private Date lastupdated;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Integer getTripExpenseHistoryId() {
		return TripExpenseHistoryId;
	}

	public void setTripExpenseHistoryId(Integer tripExpenseHistoryId) {
		TripExpenseHistoryId = tripExpenseHistoryId;
	}

	public Integer getExpenseID() {
		return expenseID;
	}

	public void setExpenseID(Integer expenseID) {
		this.expenseID = expenseID;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public String getExpenseRemarks() {
		return expenseRemarks;
	}

	public void setExpenseRemarks(String expenseRemarks) {
		this.expenseRemarks = expenseRemarks;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
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
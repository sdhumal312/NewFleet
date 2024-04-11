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
@Table(name = "tripexpense")
public class TripExpense implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "expenseID")
	private Integer expenseID;

	@Column(name = "expenseName", unique = false, nullable = false, length = 200)
	private String expenseName;

	@Column(name = "expenseRemarks", length = 200)
	private String expenseRemarks;

	@Column(name = "createdBy", nullable = false , length = 150)
	private String createdBy;

	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "lastModifiedBy", length = 150)
	private String lastModifiedBy;

	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = true)
	private Date lastupdated;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "incldriverbalance", nullable = false)
	private boolean incldriverbalance;
	
	public TripExpense() {
		super();
	}

	public TripExpense(Integer expenseID, String expenseName, String expenseRemarks, String createdBy, Long createdById,
			String lastModifiedBy, Long lastModifiedById, Date created, Date lastupdated, Integer companyId,
			boolean markForDelete, boolean incldriverbalance) {
		super();
		this.expenseID = expenseID;
		this.expenseName = expenseName;
		this.expenseRemarks = expenseRemarks;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedById = lastModifiedById;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.incldriverbalance = incldriverbalance;
	}

	/**
	 * @return the expenseID
	 */
	public Integer getExpenseID() {
		return expenseID;
	}

	/**
	 * @param expenseID the expenseID to set
	 */
	public void setExpenseID(Integer expenseID) {
		this.expenseID = expenseID;
	}

	/**
	 * @return the expenseName
	 */
	public String getExpenseName() {
		return expenseName;
	}

	/**
	 * @param expenseName the expenseName to set
	 */
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	/**
	 * @return the expenseRemarks
	 */
	public String getExpenseRemarks() {
		return expenseRemarks;
	}

	/**
	 * @param expenseRemarks the expenseRemarks to set
	 */
	public void setExpenseRemarks(String expenseRemarks) {
		this.expenseRemarks = expenseRemarks;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated the lastupdated to set
	 */
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
	
	
	public Boolean getIncldriverbalance() {
		return incldriverbalance;
	}

	public void setIncldriverbalance(Boolean incldriverbalance) {
		this.incldriverbalance = incldriverbalance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expenseName == null) ? 0 : expenseName.hashCode());
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
		TripExpense other = (TripExpense) obj;
		if (expenseName == null) {
			if (other.expenseName != null)
				return false;
		} else if (!expenseName.equals(other.expenseName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripExpense [expenseID=");
		builder.append(expenseID);
		builder.append(", expenseName=");
		builder.append(expenseName);
		builder.append(", expenseRemarks=");
		builder.append(expenseRemarks);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", Incldriverbalance=");
		builder.append(incldriverbalance);
		builder.append(", expenseID=");
		builder.append(expenseID);
		builder.append("]");
		return builder.toString();
	}

	
}
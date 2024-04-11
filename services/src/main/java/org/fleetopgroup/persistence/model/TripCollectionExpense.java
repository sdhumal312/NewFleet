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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TripCollectionExpense")
public class TripCollectionExpense implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripExpenseID")
	private Long tripExpenseID;

	/*@Column(name = "expenseName", length = 150)
	private String expenseName;*/
	
	@Column(name = "expenseId")
	private Integer expenseId;

	@Column(name = "expenseAmount")
	private Double expenseAmount;

	@Column(name = "expenseRefence", length = 20)
	private String expenseRefence;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	/*@Column(name = "createdBy", length = 150)
	private String createdBy;*/
	
	@Column(name = "createdById")
	private Long createdById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@ManyToOne
	@JoinColumn(name = "TRIPCOLLID")
	private TripCollectionSheet tripCollectionsheet;

	public TripCollectionExpense() {
		super();
	}

	public TripCollectionExpense(Long tripExpenseID, String expenseName, Double expenseAmount, String expenseRefence,
			String createdBy, Date created, TripCollectionSheet tripCollectionsheet, Integer companyId) {
		super();
		this.tripExpenseID = tripExpenseID;
		//this.expenseName = expenseName;
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
	//	this.createdBy = createdBy;
		this.created = created;
		this.tripCollectionsheet = tripCollectionsheet;
		this.companyId = companyId;
	}

	public TripCollectionExpense(String expenseName, Double expenseAmount, String expenseRefence) {
		super();
		//this.expenseName = expenseName;
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
	}

	/**
	 * @return the tripExpenseID
	 */
	public Long getTripExpenseID() {
		return tripExpenseID;
	}

	/**
	 * @param tripExpenseID
	 *            the tripExpenseID to set
	 */
	public void setTripExpenseID(Long tripExpenseID) {
		this.tripExpenseID = tripExpenseID;
	}

	/**
	 * @return the expenseName
	 *//*
	public String getExpenseName() {
		return expenseName;
	}

	*//**
	 * @param expenseName
	 *            the expenseName to set
	 *//*
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}
*/
	/**
	 * @return the expenseAmount
	 */
	public Double getExpenseAmount() {
		return expenseAmount;
	}

	/**
	 * @param expenseAmount
	 *            the expenseAmount to set
	 */
	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	/**
	 * @return the expenseRefence
	 */
	public String getExpenseRefence() {
		return expenseRefence;
	}

	/**
	 * @param expenseRefence
	 *            the expenseRefence to set
	 */
	public void setExpenseRefence(String expenseRefence) {
		this.expenseRefence = expenseRefence;
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

	/**
	 * @return the createdBy
	 *//*
	public String getCreatedBy() {
		return createdBy;
	}

	*//**
	 * @param createdBy
	 *            the createdBy to set
	 *//*
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}*/

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the expenseId
	 */
	public Integer getExpenseId() {
		return expenseId;
	}

	/**
	 * @param expenseId the expenseId to set
	 */
	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the tripCollectionsheet
	 */
	public TripCollectionSheet getTripCollectionsheet() {
		return tripCollectionsheet;
	}

	/**
	 * @param tripCollectionsheet
	 *            the tripCollectionsheet to set
	 */
	public void setTripCollectionsheet(TripCollectionSheet tripCollectionsheet) {
		this.tripCollectionsheet = tripCollectionsheet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expenseAmount == null) ? 0 : expenseAmount.hashCode());
		//result = prime * result + ((expenseName == null) ? 0 : expenseName.hashCode());
		result = prime * result + ((tripExpenseID == null) ? 0 : tripExpenseID.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripCollectionExpense other = (TripCollectionExpense) obj;
		if (expenseAmount == null) {
			if (other.expenseAmount != null)
				return false;
		} else if (!expenseAmount.equals(other.expenseAmount))
			return false;
		if (tripExpenseID == null) {
			if (other.tripExpenseID != null)
				return false;
		} else if (!tripExpenseID.equals(other.tripExpenseID))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripCollectionExpense [tripExpenseID=");
		builder.append(tripExpenseID);
		/*builder.append(", expenseName=");
		builder.append(expenseName);*/
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expenseRefence=");
		builder.append(expenseRefence);
		builder.append(", companyId=");
		builder.append(companyId);
		/*builder.append(", createdBy=");
		builder.append(createdBy);*/
		builder.append(", created=");
		builder.append(created);
		builder.append(", tripCollectionsheet=");
		builder.append(tripCollectionsheet);
		builder.append("]");
		return builder.toString();
	}

}
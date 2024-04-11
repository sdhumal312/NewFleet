package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "triproutefixedexpense")
public class TripRoutefixedExpense implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "routefixedID")
	private Integer routefixedID;

	@Column(name = "expenseName", length = 200)
	@Deprecated
	private String expenseName;
	
	@Column(name = "expenseId", nullable = false)
	private Integer expenseId;

	@Column(name = "expenseAmount")
	private Double expenseAmount;

	@Column(name = "expensePlace", length = 150)
	@Deprecated
	private String expensePlace;
	
	@Column(name = "expensePlaceId", nullable = false)
	private Integer expensePlaceId;

	@Column(name = "expenseRefence", length = 20)
	private String expenseRefence;

	@Column(name = "expenseFixed", length = 25)
	@Deprecated
	private String expenseFixed;
	
	@Column(name = "fixedTypeId", nullable = false)
	private short	fixedTypeId;

	@ManyToOne
	@JoinColumn(name = "routeID")
	private TripRoute triproute;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	public TripRoutefixedExpense() {
		super();
	}

	public TripRoutefixedExpense( Double expenseAmount,  String expenseRefence) {
		super();
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
	}

	public TripRoutefixedExpense( Double expenseAmount,  String expenseRefence,
			 boolean markForDelete) {
		super();
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
		this.markForDelete = markForDelete;
	}

	public TripRoutefixedExpense(double expenseAmount,  String expenseRefence,  short fixed) {
		super();
		this.expenseAmount = expenseAmount;
		this.expenseRefence = expenseRefence;
		this.fixedTypeId = fixed;
	}

	/**
	 * @return the routefixedID
	 */
	public Integer getRoutefixedID() {
		return routefixedID;
	}

	/**
	 * @param routefixedID
	 *            the routefixedID to set
	 */
	public void setRoutefixedID(Integer routefixedID) {
		this.routefixedID = routefixedID;
	}

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

	/**
	 * @return the triproute
	 */
	public TripRoute getTriproute() {
		return triproute;
	}

	/**
	 * @param triproute
	 *            the triproute to set
	 */
	public void setTriproute(TripRoute triproute) {
		this.triproute = triproute;
	}

	public short getFixedTypeId() {
		return fixedTypeId;
	}

	public void setFixedTypeId(short fixedTypeId) {
		this.fixedTypeId = fixedTypeId;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public Integer getExpensePlaceId() {
		return expensePlaceId;
	}

	public void setExpensePlaceId(Integer expensePlaceId) {
		this.expensePlaceId = expensePlaceId;
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

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expenseRefence == null) ? 0 : expenseRefence.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		TripRoutefixedExpense other = (TripRoutefixedExpense) obj;
		if (expenseRefence == null) {
			if (other.expenseRefence != null)
				return false;
		} else if (!expenseRefence.equals(other.expenseRefence))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripRoutefixedExpense [routefixedID=");
		builder.append(routefixedID);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expenseRefence=");
		builder.append(expenseRefence);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	
}
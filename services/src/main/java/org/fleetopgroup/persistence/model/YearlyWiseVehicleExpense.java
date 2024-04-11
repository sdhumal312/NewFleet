package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "YearlyWiseVehicleExpense")
public class YearlyWiseVehicleExpense implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "yearlyWiseVehicleExpenseId")
	private Long		yearlyWiseVehicleExpenseId;

	@Column(name = "vid")
	private Integer 	vid;
	
	@Column(name = "expenseAmount")
	private Double		expenseAmount;
	
	@Column(name = "expenseType")
	private short		expenseType;
	
	@Column(name = "expenseId")
	private Integer 	expenseId;
	
	@Column(name = "txnTypeId")
	private Long		txnTypeId;
	
	@Column(name = "expenseYear")
	private int			expenseYear;
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	public YearlyWiseVehicleExpense() {
		super();
	}

	public YearlyWiseVehicleExpense(Long yearlyWiseVehicleExpenseId, Integer vid, Double expenseAmount,
			short expenseType, Integer expenseId, Long txnTypeId, int expenseYear, Integer companyId,
			boolean markForDelete) {
		super();
		this.yearlyWiseVehicleExpenseId = yearlyWiseVehicleExpenseId;
		this.vid = vid;
		this.expenseAmount = expenseAmount;
		this.expenseType = expenseType;
		this.expenseId = expenseId;
		this.txnTypeId = txnTypeId;
		this.expenseYear = expenseYear;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((expenseId == null) ? 0 : expenseId.hashCode());
		result = prime * result + expenseYear;
		result = prime * result + ((txnTypeId == null) ? 0 : txnTypeId.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		result = prime * result + ((yearlyWiseVehicleExpenseId == null) ? 0 : yearlyWiseVehicleExpenseId.hashCode());
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
		YearlyWiseVehicleExpense other = (YearlyWiseVehicleExpense) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (expenseId == null) {
			if (other.expenseId != null)
				return false;
		} else if (!expenseId.equals(other.expenseId))
			return false;
		if (expenseYear != other.expenseYear)
			return false;
		if (txnTypeId == null) {
			if (other.txnTypeId != null)
				return false;
		} else if (!txnTypeId.equals(other.txnTypeId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		if (yearlyWiseVehicleExpenseId == null) {
			if (other.yearlyWiseVehicleExpenseId != null)
				return false;
		} else if (!yearlyWiseVehicleExpenseId.equals(other.yearlyWiseVehicleExpenseId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "YearlyWiseVehicleExpense [yearlyWiseVehicleExpenseId=" + yearlyWiseVehicleExpenseId + ", vid=" + vid
				+ ", expenseAmount=" + expenseAmount + ", expenseType=" + expenseType + ", expenseId=" + expenseId
				+ ", txnTypeId=" + txnTypeId + ", expenseYear=" + expenseYear + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}
	
	

}

package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MonthWiseVehicleExpense")
public class MonthWiseVehicleExpense implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "monthWiseVehicleExpenseId")
	private Long		monthWiseVehicleExpenseId;

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

	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	@Column(name = "startDateOfMonth")
	private Timestamp		startDateOfMonth;
	
	
	public MonthWiseVehicleExpense() {
		super();
	}


	public MonthWiseVehicleExpense(Long monthWiseVehicleExpenseId, Integer vid, Double expenseAmount, short expenseType,
			Integer expenseId, Long txnTypeId, Integer companyId, boolean markForDelete) {
		super();
		this.monthWiseVehicleExpenseId = monthWiseVehicleExpenseId;
		this.vid = vid;
		this.expenseAmount = expenseAmount;
		this.expenseType = expenseType;
		this.expenseId = expenseId;
		this.txnTypeId = txnTypeId;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}


	public Long getMonthWiseVehicleExpenseId() {
		return monthWiseVehicleExpenseId;
	}


	public void setMonthWiseVehicleExpenseId(Long monthWiseVehicleExpenseId) {
		this.monthWiseVehicleExpenseId = monthWiseVehicleExpenseId;
	}


	public Integer getVid() {
		return vid;
	}


	public void setVid(Integer vid) {
		this.vid = vid;
	}


	public Double getExpenseAmount() {
		return expenseAmount;
	}


	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}


	public short getExpenseType() {
		return expenseType;
	}


	public void setExpenseType(short expenseType) {
		this.expenseType = expenseType;
	}


	public Integer getExpenseId() {
		return expenseId;
	}


	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}


	public Long getTxnTypeId() {
		return txnTypeId;
	}


	public void setTxnTypeId(Long txnTypeId) {
		this.txnTypeId = txnTypeId;
	}


	/*public int getExpenseMonth() {
		return expenseMonth;
	}


	public void setExpenseMonth(int expenseMonth) {
		this.expenseMonth = expenseMonth;
	}


	public int getExpenseYear() {
		return expenseYear;
	}


	public void setExpenseYear(int expenseYear) {
		this.expenseYear = expenseYear;
	}
*/

	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public Timestamp getStartDateOfMonth() {
		return startDateOfMonth;
	}


	public void setStartDateOfMonth(Timestamp startDateOfMonth) {
		this.startDateOfMonth = startDateOfMonth;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + expenseType;
		result = prime * result + ((monthWiseVehicleExpenseId == null) ? 0 : monthWiseVehicleExpenseId.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
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
		MonthWiseVehicleExpense other = (MonthWiseVehicleExpense) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (expenseType != other.expenseType)
			return false;
		if (monthWiseVehicleExpenseId == null) {
			if (other.monthWiseVehicleExpenseId != null)
				return false;
		} else if (!monthWiseVehicleExpenseId.equals(other.monthWiseVehicleExpenseId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MonthWiseVehicleExpense [monthWiseVehicleExpenseId=");
		builder.append(monthWiseVehicleExpenseId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", expenseAmount=");
		builder.append(expenseAmount);
		builder.append(", expenseType=");
		builder.append(expenseType);
		builder.append(", expenseId=");
		builder.append(expenseId);
		builder.append(", txnTypeId=");
		builder.append(txnTypeId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", startDateOfMonth=");
		builder.append(startDateOfMonth);
		builder.append("]");
		return builder.toString();
	}



}

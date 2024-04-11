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
@Table(name = "DateWiseVehicleExpense")
public class DateWiseVehicleExpense implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dateWiseVehicleExpenseId")
	private Long		dateWiseVehicleExpenseId;
	
	@Column(name = "vid")
	private Integer 	vid;
	
	@Column(name = "expenseAmount")
	private Double		expenseAmount;
	
	@Column(name = "expenseType")
	private short		expenseType;
	
	@Column(name = "expenseId")
	private Integer 	expenseId;
	
	@Column(name = "expenseDate", nullable = false)
	private Timestamp	expenseDate;
	
	@Column(name = "txnTypeId")
	private Long		txnTypeId;
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	public DateWiseVehicleExpense() {
		super();
	}

	public DateWiseVehicleExpense(Long dateWiseVehicleExpenseId, Integer vid, Double expenseAmount, short expenseType,
			Integer expenseId, Timestamp expenseDate, Long txnTypeId, Integer companyId, boolean markForDelete) {
		super();
		this.dateWiseVehicleExpenseId = dateWiseVehicleExpenseId;
		this.vid = vid;
		this.expenseAmount = expenseAmount;
		this.expenseType = expenseType;
		this.expenseId = expenseId;
		this.expenseDate = expenseDate;
		this.txnTypeId = txnTypeId;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getDateWiseVehicleExpenseId() {
		return dateWiseVehicleExpenseId;
	}

	public void setDateWiseVehicleExpenseId(Long dateWiseVehicleExpenseId) {
		this.dateWiseVehicleExpenseId = dateWiseVehicleExpenseId;
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

	public Timestamp getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Timestamp expenseDate) {
		this.expenseDate = expenseDate;
	}

	public Long getTxnTypeId() {
		return txnTypeId;
	}

	public void setTxnTypeId(Long txnTypeId) {
		this.txnTypeId = txnTypeId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((dateWiseVehicleExpenseId == null) ? 0 : dateWiseVehicleExpenseId.hashCode());
		result = prime * result + ((expenseDate == null) ? 0 : expenseDate.hashCode());
		result = prime * result + ((expenseId == null) ? 0 : expenseId.hashCode());
		result = prime * result + expenseType;
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
		DateWiseVehicleExpense other = (DateWiseVehicleExpense) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (dateWiseVehicleExpenseId == null) {
			if (other.dateWiseVehicleExpenseId != null)
				return false;
		} else if (!dateWiseVehicleExpenseId.equals(other.dateWiseVehicleExpenseId))
			return false;
		if (expenseDate == null) {
			if (other.expenseDate != null)
				return false;
		} else if (!expenseDate.equals(other.expenseDate))
			return false;
		if (expenseId == null) {
			if (other.expenseId != null)
				return false;
		} else if (!expenseId.equals(other.expenseId))
			return false;
		if (expenseType != other.expenseType)
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
		return "DateWiseVehicleExpense [dateWiseVehicleExpenseId=" + dateWiseVehicleExpenseId + ", vid=" + vid
				+ ", expenseAmount=" + expenseAmount + ", expenseType=" + expenseType + ", expenseId=" + expenseId
				+ ", expenseDate=" + expenseDate + ", txnTypeId=" + txnTypeId + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}
	
	
}

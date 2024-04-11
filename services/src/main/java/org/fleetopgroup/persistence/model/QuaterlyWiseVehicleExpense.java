package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QuaterlyWiseVehicleExpense")
public class QuaterlyWiseVehicleExpense implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quaterlyWiseVehicleExpenseId")
	private Long		quaterlyWiseVehicleExpenseId;

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
	
	@Column(name = "expenseQuater")
	private int			expenseQuater;
	
	@Column(name = "expenseYear")
	private int			expenseYear;
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	public QuaterlyWiseVehicleExpense() {
		super();
	}

	public QuaterlyWiseVehicleExpense(Long quaterlyWiseVehicleExpenseId, Integer vid, Double expenseAmount,
			short expenseType, Integer expenseId, Long txnTypeId, int expenseQuater, int expenseYear, Integer companyId,
			boolean markForDelete) {
		super();
		this.quaterlyWiseVehicleExpenseId = quaterlyWiseVehicleExpenseId;
		this.vid = vid;
		this.expenseAmount = expenseAmount;
		this.expenseType = expenseType;
		this.expenseId = expenseId;
		this.txnTypeId = txnTypeId;
		this.expenseQuater = expenseQuater;
		this.expenseYear = expenseYear;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getQuaterlyWiseVehicleExpenseId() {
		return quaterlyWiseVehicleExpenseId;
	}

	public void setQuaterlyWiseVehicleExpenseId(Long quaterlyWiseVehicleExpenseId) {
		this.quaterlyWiseVehicleExpenseId = quaterlyWiseVehicleExpenseId;
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

	public int getExpenseQuater() {
		return expenseQuater;
	}

	public void setExpenseQuater(int expenseQuater) {
		this.expenseQuater = expenseQuater;
	}

	public int getExpenseYear() {
		return expenseYear;
	}

	public void setExpenseYear(int expenseYear) {
		this.expenseYear = expenseYear;
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
		result = prime * result + ((expenseId == null) ? 0 : expenseId.hashCode());
		result = prime * result + expenseQuater;
		result = prime * result + expenseYear;
		result = prime * result
				+ ((quaterlyWiseVehicleExpenseId == null) ? 0 : quaterlyWiseVehicleExpenseId.hashCode());
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
		QuaterlyWiseVehicleExpense other = (QuaterlyWiseVehicleExpense) obj;
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
		if (expenseQuater != other.expenseQuater)
			return false;
		if (expenseYear != other.expenseYear)
			return false;
		if (quaterlyWiseVehicleExpenseId == null) {
			if (other.quaterlyWiseVehicleExpenseId != null)
				return false;
		} else if (!quaterlyWiseVehicleExpenseId.equals(other.quaterlyWiseVehicleExpenseId))
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
		return "QuaterlyWiseVehicleExpense [quaterlyWiseVehicleExpenseId=" + quaterlyWiseVehicleExpenseId + ", vid="
				+ vid + ", expenseAmount=" + expenseAmount + ", expenseType=" + expenseType + ", expenseId=" + expenseId
				+ ", txnTypeId=" + txnTypeId + ", expenseQuater=" + expenseQuater + ", expenseYear=" + expenseYear
				+ ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
	
}

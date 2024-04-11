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
@Table(name = "MonthWiseVehicleIncome")
public class MonthWiseVehicleIncome implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "monthWiseVehicleIncomeId")
	private Long	monthWiseVehicleIncomeId;
	
	@Column(name = "vid")
	private Integer 	vid;
	
	@Column(name = "incomeAmount")
	private Double		incomeAmount;
	
	@Column(name = "incomeType")
	private short		incomeType;
	
	@Column(name = "incomeId")
	private Integer 	incomeId;
	
	@Column(name = "txnTypeId")
	private Long		txnTypeId;
	
	/*@Column(name = "incomeMonth")
	private int			incomeMonth;
	
	@Column(name = "incomeYear")
	private int			incomeYear;*/
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;

	@Column(name = "startDateOfMonth")
	private Timestamp	startDateOfMonth;
	
	public MonthWiseVehicleIncome() {
		super();
	}
	
	


	public MonthWiseVehicleIncome(Long monthWiseVehicleIncomeId, Integer vid, Double incomeAmount, short incomeType,
			Integer incomeId, Long txnTypeId, Integer companyId, boolean markForDelete) {
		super();
		this.monthWiseVehicleIncomeId = monthWiseVehicleIncomeId;
		this.vid = vid;
		this.incomeAmount = incomeAmount;
		this.incomeType = incomeType;
		this.incomeId = incomeId;
		this.txnTypeId = txnTypeId;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}




	public Long getMonthWiseVehicleIncomeId() {
		return monthWiseVehicleIncomeId;
	}


	public void setMonthWiseVehicleIncomeId(Long monthWiseVehicleIncomeId) {
		this.monthWiseVehicleIncomeId = monthWiseVehicleIncomeId;
	}


	public Integer getVid() {
		return vid;
	}


	public void setVid(Integer vid) {
		this.vid = vid;
	}


	public Double getIncomeAmount() {
		return incomeAmount;
	}


	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}


	public short getIncomeType() {
		return incomeType;
	}


	public void setIncomeType(short incomeType) {
		this.incomeType = incomeType;
	}


	public Integer getIncomeId() {
		return incomeId;
	}


	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}


	public Long getTxnTypeId() {
		return txnTypeId;
	}


	public void setTxnTypeId(Long txnTypeId) {
		this.txnTypeId = txnTypeId;
	}


	/*public int getIncomeMonth() {
		return incomeMonth;
	}




	public void setIncomeMonth(int incomeMonth) {
		this.incomeMonth = incomeMonth;
	}




	public int getIncomeYear() {
		return incomeYear;
	}




	public void setIncomeYear(int incomeYear) {
		this.incomeYear = incomeYear;
	}
*/



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




	public Timestamp getStartDateOfMonth() {
		return startDateOfMonth;
	}




	public void setStartDateOfMonth(Timestamp startDateOfMonth) {
		this.startDateOfMonth = startDateOfMonth;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((incomeId == null) ? 0 : incomeId.hashCode());
		result = prime * result + ((monthWiseVehicleIncomeId == null) ? 0 : monthWiseVehicleIncomeId.hashCode());
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
		MonthWiseVehicleIncome other = (MonthWiseVehicleIncome) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (incomeId == null) {
			if (other.incomeId != null)
				return false;
		} else if (!incomeId.equals(other.incomeId))
			return false;
		if (monthWiseVehicleIncomeId == null) {
			if (other.monthWiseVehicleIncomeId != null)
				return false;
		} else if (!monthWiseVehicleIncomeId.equals(other.monthWiseVehicleIncomeId))
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
		return "MonthWiseVehicleIncome [monthWiseVehicleIncomeId=" + monthWiseVehicleIncomeId + ", vid=" + vid
				+ ", incomeAmount=" + incomeAmount + ", incomeType=" + incomeType + ", incomeId=" + incomeId
				+ ", txnTypeId=" + txnTypeId + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", startDateOfMonth=" + startDateOfMonth + "]";
	}


	
	
}

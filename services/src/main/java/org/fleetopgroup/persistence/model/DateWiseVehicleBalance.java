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
@Table(name = "DateWiseVehicleBalance")
public class DateWiseVehicleBalance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dateWiseVehicleBalanceId")
	private Long	dateWiseVehicleBalanceId;
	
	@Column(name = "vid", nullable = false)
	private Integer		vid;
	
	@Column(name = "totalIncomeAmount")
	private Double		totalIncomeAmount;
	
	@Column(name = "totalExpenseAmount")
	private Double		totalExpenseAmount;
	
	@Column(name = "dayBalanceAmount")
	private Double		dayBalanceAmount;
	
	@Column(name = "AllDayBalanceAmount")
	private Double		AllDayBalanceAmount;
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "transactionDate")
	private Timestamp	transactionDate;
	
	@Column(name = "systemDateAndTime")
	private Timestamp	systemDateAndTime;
	
	@Column(name = "markForDelete" , nullable = false)
	private boolean		markForDelete;
	
	public DateWiseVehicleBalance() {
		super();
	}

	public DateWiseVehicleBalance(Long dateWiseVehicleBalanceId, Integer vid, Double totalIncomeAmount,
			Double totalExpenseAmount, Double dayBalanceAmount, Double allDayBalanceAmount, Integer companyId,
			boolean markForDelete) {
		super();
		this.dateWiseVehicleBalanceId = dateWiseVehicleBalanceId;
		this.vid = vid;
		this.totalIncomeAmount = totalIncomeAmount;
		this.totalExpenseAmount = totalExpenseAmount;
		this.dayBalanceAmount = dayBalanceAmount;
		AllDayBalanceAmount = allDayBalanceAmount;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getDateWiseVehicleBalanceId() {
		return dateWiseVehicleBalanceId;
	}

	public void setDateWiseVehicleBalanceId(Long dateWiseVehicleBalanceId) {
		this.dateWiseVehicleBalanceId = dateWiseVehicleBalanceId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Double getTotalIncomeAmount() {
		return totalIncomeAmount;
	}

	public void setTotalIncomeAmount(Double totalIncomeAmount) {
		this.totalIncomeAmount = totalIncomeAmount;
	}

	public Double getTotalExpenseAmount() {
		return totalExpenseAmount;
	}

	public void setTotalExpenseAmount(Double totalExpenseAmount) {
		this.totalExpenseAmount = totalExpenseAmount;
	}

	public Double getDayBalanceAmount() {
		return dayBalanceAmount;
	}

	public void setDayBalanceAmount(Double dayBalanceAmount) {
		this.dayBalanceAmount = dayBalanceAmount;
	}

	public Double getAllDayBalanceAmount() {
		return AllDayBalanceAmount;
	}

	public void setAllDayBalanceAmount(Double allDayBalanceAmount) {
		AllDayBalanceAmount = allDayBalanceAmount;
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
	
	

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Timestamp getSystemDateAndTime() {
		return systemDateAndTime;
	}

	public void setSystemDateAndTime(Timestamp systemDateAndTime) {
		this.systemDateAndTime = systemDateAndTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((AllDayBalanceAmount == null) ? 0 : AllDayBalanceAmount.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
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
		DateWiseVehicleBalance other = (DateWiseVehicleBalance) obj;
		if (AllDayBalanceAmount == null) {
			if (other.AllDayBalanceAmount != null)
				return false;
		} else if (!AllDayBalanceAmount.equals(other.AllDayBalanceAmount))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
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
		return "DateWiseVehicleBalance [dateWiseVehicleBalanceId=" + dateWiseVehicleBalanceId + ", vid=" + vid
				+ ", totalIncomeAmount=" + totalIncomeAmount + ", totalExpenseAmount=" + totalExpenseAmount
				+ ", dayBalanceAmount=" + dayBalanceAmount + ", AllDayBalanceAmount=" + AllDayBalanceAmount
				+ ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}

	
}

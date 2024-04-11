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
@Table(name = "MonthWiseVehicleBalance")
public class MonthWiseVehicleBalance implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "monthWiseVehicleBalanceId")
	private Long		monthWiseVehicleBalanceId;
	
	@Column(name = "vid", nullable = false)
	private Integer		vid;
	
	@Column(name = "totalIncomeAmount")
	private Double		totalIncomeAmount;
	
	@Column(name = "totalExpenseAmount")
	private Double		totalExpenseAmount;
	
	@Column(name = "balanceAmount")
	private Double		balanceAmount;
	
	@Column(name = "monthBalance")
	private Double		monthBalance;
	
	/*@Column(name = "expenseMonth")
	private int			expenseMonth;
	
	@Column(name = "expenseYear")
	private int			expenseYear;*/
	
	@Column(name = "toatlDiesel")
	private Double		toatlDiesel;
	
	@Column(name = "toatlDieselAmount")
	private Double		toatlDieselAmount;
	
	@Column(name = "totalNoOfKMPL")
	private Integer		totalNoOfKMPL;
	
	@Column(name = "totalRfid")
	private Integer 	totalRfid;
	
	@Column(name = "totalRfidAmount")
	private Double		totalRfidAmount;
	
	@Column(name = "companyId", nullable = false)
	private	Integer		companyId;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	@Column(name = "totalCollection")
	private Double		totalCollection;
	
	@Column(name = "monthStartDate")
	private Timestamp	monthStartDate;
	
	public MonthWiseVehicleBalance() {
		super();
	}

	public MonthWiseVehicleBalance(Long monthWiseVehicleBalanceId, Integer vid, Double totalIncomeAmount,
			Double totalExpenseAmount, Double balanceAmount, Double monthBalance, Double toatlDiesel,
			Double toatlDieselAmount, Integer totalNoOfKMPL, Integer totalRfid, Double totalRfidAmount,
			Integer companyId, boolean markForDelete, Double totalCollection) {
		super();
		this.monthWiseVehicleBalanceId = monthWiseVehicleBalanceId;
		this.vid = vid;
		this.totalIncomeAmount = totalIncomeAmount;
		this.totalExpenseAmount = totalExpenseAmount;
		this.balanceAmount = balanceAmount;
		this.monthBalance = monthBalance;
		this.toatlDiesel = toatlDiesel;
		this.toatlDieselAmount = toatlDieselAmount;
		this.totalNoOfKMPL = totalNoOfKMPL;
		this.totalRfid = totalRfid;
		this.totalRfidAmount = totalRfidAmount;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.totalCollection = totalCollection;
	}

	public Long getMonthWiseVehicleBalanceId() {
		return monthWiseVehicleBalanceId;
	}

	public void setMonthWiseVehicleBalanceId(Long monthWiseVehicleBalanceId) {
		this.monthWiseVehicleBalanceId = monthWiseVehicleBalanceId;
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

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getMonthBalance() {
		return monthBalance;
	}

	public void setMonthBalance(Double monthBalance) {
		this.monthBalance = monthBalance;
	}

	public Double getToatlDiesel() {
		return toatlDiesel;
	}

	public void setToatlDiesel(Double toatlDiesel) {
		this.toatlDiesel = toatlDiesel;
	}

	public Double getToatlDieselAmount() {
		return toatlDieselAmount;
	}

	public void setToatlDieselAmount(Double toatlDieselAmount) {
		this.toatlDieselAmount = toatlDieselAmount;
	}

	public Integer getTotalNoOfKMPL() {
		return totalNoOfKMPL;
	}

	public void setTotalNoOfKMPL(Integer totalNoOfKMPL) {
		this.totalNoOfKMPL = totalNoOfKMPL;
	}

	public Integer getTotalRfid() {
		return totalRfid;
	}

	public void setTotalRfid(Integer totalRfid) {
		this.totalRfid = totalRfid;
	}

	public Double getTotalRfidAmount() {
		return totalRfidAmount;
	}

	public void setTotalRfidAmount(Double totalRfidAmount) {
		this.totalRfidAmount = totalRfidAmount;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Timestamp getMonthStartDate() {
		return monthStartDate;
	}

	public void setMonthStartDate(Timestamp monthStartDate) {
		this.monthStartDate = monthStartDate;
	}

	public Double getTotalCollection() {
		return totalCollection;
	}

	public void setTotalCollection(Double totalCollection) {
		this.totalCollection = totalCollection;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((monthWiseVehicleBalanceId == null) ? 0 : monthWiseVehicleBalanceId.hashCode());
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
		MonthWiseVehicleBalance other = (MonthWiseVehicleBalance) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (monthWiseVehicleBalanceId == null) {
			if (other.monthWiseVehicleBalanceId != null)
				return false;
		} else if (!monthWiseVehicleBalanceId.equals(other.monthWiseVehicleBalanceId))
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
		return "MonthWiseVehicleBalance [monthWiseVehicleBalanceId=" + monthWiseVehicleBalanceId + ", vid=" + vid
				+ ", totalIncomeAmount=" + totalIncomeAmount + ", totalExpenseAmount=" + totalExpenseAmount
				+ ", balanceAmount=" + balanceAmount + ", monthBalance=" + monthBalance + ", toatlDiesel=" + toatlDiesel
				+ ", toatlDieselAmount=" + toatlDieselAmount + ", totalNoOfKMPL=" + totalNoOfKMPL + ", totalRfid="
				+ totalRfid + ", totalRfidAmount=" + totalRfidAmount + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + ", totalCollection=" + totalCollection + ", monthStartDate=" + monthStartDate + "]";
	}

	
	
	

}

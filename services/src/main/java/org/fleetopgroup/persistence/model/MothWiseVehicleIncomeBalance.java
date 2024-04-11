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
@Table(name = "MothWiseVehicleIncomeBalance")
public class MothWiseVehicleIncomeBalance implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mothWiseVehicleIncomeBalanceId")
	private Long mothWiseVehicleIncomeBalanceId;
	
	@Column(name = "vid")
	private Integer		vid;
	
	@Column(name = "startDateOfMonth")
	private Timestamp	startDateOfMonth;
	
	@Column(name = "totalIncome")
	private Double		totalIncome;
	
	@Column(name = "totalBalanceIncome")
	private Double		totalBalanceIncome;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	public MothWiseVehicleIncomeBalance() {
		super();
	}

	public MothWiseVehicleIncomeBalance(Long mothWiseVehicleIncomeBalanceId, Integer vid, Timestamp startDateOfMonth,
			Double totalIncome, Double previousMonthIncome, boolean markForDelete) {
		super();
		this.mothWiseVehicleIncomeBalanceId = mothWiseVehicleIncomeBalanceId;
		this.vid = vid;
		this.startDateOfMonth = startDateOfMonth;
		this.totalIncome = totalIncome;
		this.totalBalanceIncome = previousMonthIncome;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result
				+ ((mothWiseVehicleIncomeBalanceId == null) ? 0 : mothWiseVehicleIncomeBalanceId.hashCode());
		result = prime * result + ((startDateOfMonth == null) ? 0 : startDateOfMonth.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		return result;
	}
	
	

	public Long getMothWiseVehicleIncomeBalanceId() {
		return mothWiseVehicleIncomeBalanceId;
	}

	public void setMothWiseVehicleIncomeBalanceId(Long mothWiseVehicleIncomeBalanceId) {
		this.mothWiseVehicleIncomeBalanceId = mothWiseVehicleIncomeBalanceId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Timestamp getStartDateOfMonth() {
		return startDateOfMonth;
	}

	public void setStartDateOfMonth(Timestamp startDateOfMonth) {
		this.startDateOfMonth = startDateOfMonth;
	}

	public Double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
	}

	

	public Double getTotalBalanceIncome() {
		return totalBalanceIncome;
	}

	public void setTotalBalanceIncome(Double totalBalanceIncome) {
		this.totalBalanceIncome = totalBalanceIncome;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MothWiseVehicleIncomeBalance other = (MothWiseVehicleIncomeBalance) obj;
		if (markForDelete != other.markForDelete)
			return false;
		if (mothWiseVehicleIncomeBalanceId == null) {
			if (other.mothWiseVehicleIncomeBalanceId != null)
				return false;
		} else if (!mothWiseVehicleIncomeBalanceId.equals(other.mothWiseVehicleIncomeBalanceId))
			return false;
		if (startDateOfMonth == null) {
			if (other.startDateOfMonth != null)
				return false;
		} else if (!startDateOfMonth.equals(other.startDateOfMonth))
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
		return "MothWiseVehicleIncomeBalance [mothWiseVehicleIncomeBalanceId=" + mothWiseVehicleIncomeBalanceId
				+ ", vid=" + vid + ", startDateOfMonth=" + startDateOfMonth + ", totalIncome=" + totalIncome
				+ ", totalBalanceIncome=" + totalBalanceIncome + ", markForDelete=" + markForDelete + "]";
	}
	
	
}

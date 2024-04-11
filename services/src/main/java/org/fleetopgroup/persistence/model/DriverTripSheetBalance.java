package org.fleetopgroup.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DriverTripSheetBalance")
public class DriverTripSheetBalance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dTripSheetBalanceId")
	private Long dTripSheetBalanceId;
	
	@Column(name = "driverId")
	private Long driverId;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "balanceAmount")
	private Double	balanceAmount;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;

	public Long getdTripSheetBalanceId() {
		return dTripSheetBalanceId;
	}

	public void setdTripSheetBalanceId(Long dTripSheetBalanceId) {
		this.dTripSheetBalanceId = dTripSheetBalanceId;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "DriverTripSheetBalance [dTripSheetBalanceId=" + dTripSheetBalanceId + ", driverId=" + driverId
				+ ", companyId=" + companyId + ", balanceAmount=" + balanceAmount + ", markForDelete=" + markForDelete
				+ "]";
	}
	
}

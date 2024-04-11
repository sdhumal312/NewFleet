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
@Table(name = "VehicleProfitAndLossIncomeTxnChecker")
public class VehicleProfitAndLossIncomeTxnChecker implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleProfitAndLossTxnCheckerId")
	private Long	vehicleProfitAndLossTxnCheckerId;
	
	@Column(name = "transactionId", nullable = false)
	private Long		transactionId;
	
	@Column(name = "vid", nullable = false)
	private Integer		vid;
	
	@Column(name = "isVehicleIncomeAdded")
	private boolean		isVehicleIncomeAdded;
	
	
	@Column(name = "isMonthWiseVehicleIncomeAdded")
	private boolean		isMonthWiseVehicleIncomeAdded;
	
	
	@Column(name = "isMonthWiseVehicleBalanceUpdated")
	private boolean isMonthWiseVehicleBalanceUpdated;
	
	@Column(name = "txnInsertionDateTime", nullable = false)
	private Timestamp	txnInsertionDateTime;
	
	@Column(name = "isFinallyEntered")
	private boolean		isFinallyEntered;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "transactionTypeId")
	private short 	transactionTypeId;
	
	@Column(name = "transactionSubTypeId")
	private Short	transactionSubTypeId;
	
	@Column(name = "incomeId")
	private Long		incomeId;

	@Column(name = "tripIncomeId")
	private Long		tripIncomeId;
	
	public VehicleProfitAndLossIncomeTxnChecker() {
		super();
	}

	public VehicleProfitAndLossIncomeTxnChecker(Long vehicleProfitAndLossTxnCheckerId, Long transactionId, Integer vid,
			boolean isVehicleIncomeAdded, boolean isMonthWiseVehicleIncomeAdded,
			boolean isMonthWiseVehicleBalanceUpdated,
			Timestamp txnInsertionDateTime, boolean isFinallyEntered, Integer companyId, short transactionTypeId,
			Short transactionSubTypeId, Long incomeId) {
		super();
		this.vehicleProfitAndLossTxnCheckerId = vehicleProfitAndLossTxnCheckerId;
		this.transactionId = transactionId;
		this.vid = vid;
		this.isVehicleIncomeAdded = isVehicleIncomeAdded;
		this.isMonthWiseVehicleIncomeAdded = isMonthWiseVehicleIncomeAdded;
		this.isMonthWiseVehicleBalanceUpdated = isMonthWiseVehicleBalanceUpdated;
		this.txnInsertionDateTime = txnInsertionDateTime;
		this.isFinallyEntered = isFinallyEntered;
		this.companyId = companyId;
		this.transactionTypeId = transactionTypeId;
		this.transactionSubTypeId = transactionSubTypeId;
		this.incomeId = incomeId;
	}

	public Long getVehicleProfitAndLossTxnCheckerId() {
		return vehicleProfitAndLossTxnCheckerId;
	}

	public void setVehicleProfitAndLossTxnCheckerId(Long vehicleProfitAndLossTxnCheckerId) {
		this.vehicleProfitAndLossTxnCheckerId = vehicleProfitAndLossTxnCheckerId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public boolean isVehicleIncomeAdded() {
		return isVehicleIncomeAdded;
	}

	public void setVehicleIncomeAdded(boolean isVehicleIncomeAdded) {
		this.isVehicleIncomeAdded = isVehicleIncomeAdded;
	}

	public boolean isMonthWiseVehicleIncomeAdded() {
		return isMonthWiseVehicleIncomeAdded;
	}

	public void setMonthWiseVehicleIncomeAdded(boolean isMonthWiseVehicleIncomeAdded) {
		this.isMonthWiseVehicleIncomeAdded = isMonthWiseVehicleIncomeAdded;
	}


	public boolean isMonthWiseVehicleBalanceUpdated() {
		return isMonthWiseVehicleBalanceUpdated;
	}

	public void setMonthWiseVehicleBalanceUpdated(boolean isMonthWiseVehicleBalanceUpdated) {
		this.isMonthWiseVehicleBalanceUpdated = isMonthWiseVehicleBalanceUpdated;
	}

	public Timestamp getTxnInsertionDateTime() {
		return txnInsertionDateTime;
	}

	public void setTxnInsertionDateTime(Timestamp txnInsertionDateTime) {
		this.txnInsertionDateTime = txnInsertionDateTime;
	}

	public boolean isFinallyEntered() {
		return isFinallyEntered;
	}

	public void setFinallyEntered(boolean isFinallyEntered) {
		this.isFinallyEntered = isFinallyEntered;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public short getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(short transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public Short getTransactionSubTypeId() {
		return transactionSubTypeId;
	}

	public void setTransactionSubTypeId(Short transactionSubTypeId) {
		this.transactionSubTypeId = transactionSubTypeId;
	}

	public Long getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Long incomeId) {
		this.incomeId = incomeId;
	}

	public Long getTripIncomeId() {
		return tripIncomeId;
	}

	public void setTripIncomeId(Long tripIncomeId) {
		this.tripIncomeId = tripIncomeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((incomeId == null) ? 0 : incomeId.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		result = prime * result + ((txnInsertionDateTime == null) ? 0 : txnInsertionDateTime.hashCode());
		result = prime * result
				+ ((vehicleProfitAndLossTxnCheckerId == null) ? 0 : vehicleProfitAndLossTxnCheckerId.hashCode());
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
		VehicleProfitAndLossIncomeTxnChecker other = (VehicleProfitAndLossIncomeTxnChecker) obj;
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
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		if (txnInsertionDateTime == null) {
			if (other.txnInsertionDateTime != null)
				return false;
		} else if (!txnInsertionDateTime.equals(other.txnInsertionDateTime))
			return false;
		if (vehicleProfitAndLossTxnCheckerId == null) {
			if (other.vehicleProfitAndLossTxnCheckerId != null)
				return false;
		} else if (!vehicleProfitAndLossTxnCheckerId.equals(other.vehicleProfitAndLossTxnCheckerId))
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
		builder.append("VehicleProfitAndLossIncomeTxnChecker [vehicleProfitAndLossTxnCheckerId=");
		builder.append(vehicleProfitAndLossTxnCheckerId);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", isVehicleIncomeAdded=");
		builder.append(isVehicleIncomeAdded);
		builder.append(", isMonthWiseVehicleIncomeAdded=");
		builder.append(isMonthWiseVehicleIncomeAdded);
		builder.append(", isMonthWiseVehicleBalanceUpdated=");
		builder.append(isMonthWiseVehicleBalanceUpdated);
		builder.append(", txnInsertionDateTime=");
		builder.append(txnInsertionDateTime);
		builder.append(", isFinallyEntered=");
		builder.append(isFinallyEntered);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", transactionTypeId=");
		builder.append(transactionTypeId);
		builder.append(", transactionSubTypeId=");
		builder.append(transactionSubTypeId);
		builder.append(", incomeId=");
		builder.append(incomeId);
		builder.append(", tripIncomeId=");
		builder.append(tripIncomeId);
		builder.append("]");
		return builder.toString();
	}

	
}

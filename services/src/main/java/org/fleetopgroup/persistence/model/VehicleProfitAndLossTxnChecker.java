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
@Table(name = "VehicleProfitAndLossTxnChecker")
public class VehicleProfitAndLossTxnChecker implements Serializable{

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
	
	@Column(name = "isVehicleExpenseAdded")
	private boolean		isVehicleExpenseAdded;
	
	@Column(name = "isDateWiseVehicleExpenseAdded")
	private boolean		isDateWiseVehicleExpenseAdded;
	
	@Column(name = "isMonthWiseVehicleExpenseAdded")
	private boolean		isMonthWiseVehicleExpenseAdded;
	
	@Column(name = "isDateWiseVehicleBalanceUpdated")
	private boolean	isDateWiseVehicleBalanceUpdated;
	
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
	
	@Column(name = "expenseId")
	private Long		expenseId;
	
	@Column(name = "tripExpenseId")
	private Long		tripExpenseId;
	
	

	public VehicleProfitAndLossTxnChecker(Long vehicleProfitAndLossTxnCheckerId, Long transactionId, Integer vid,
			boolean isVehicleExpenseAdded, boolean isDateWiseVehicleExpenseAdded,
			boolean isMonthWiseVehicleExpenseAdded, boolean isDateWiseVehicleBalanceUpdated,
			boolean isMonthWiseVehicleBalanceUpdated, Timestamp txnInsertionDateTime, boolean isFinallyEntered,
			Integer companyId, short transactionTypeId, Short transactionSubTypeId) {
		super();
		this.vehicleProfitAndLossTxnCheckerId = vehicleProfitAndLossTxnCheckerId;
		this.transactionId = transactionId;
		this.vid = vid;
		this.isVehicleExpenseAdded = isVehicleExpenseAdded;
		this.isDateWiseVehicleExpenseAdded = isDateWiseVehicleExpenseAdded;
		this.isMonthWiseVehicleExpenseAdded = isMonthWiseVehicleExpenseAdded;
		this.isDateWiseVehicleBalanceUpdated = isDateWiseVehicleBalanceUpdated;
		this.isMonthWiseVehicleBalanceUpdated = isMonthWiseVehicleBalanceUpdated;
		this.txnInsertionDateTime = txnInsertionDateTime;
		this.isFinallyEntered = isFinallyEntered;
		this.companyId = companyId;
		this.transactionTypeId = transactionTypeId;
		this.transactionSubTypeId = transactionSubTypeId;
	}
	
	public VehicleProfitAndLossTxnChecker() {
		super();
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

	public boolean isVehicleExpenseAdded() {
		return isVehicleExpenseAdded;
	}

	public void setVehicleExpenseAdded(boolean isVehicleExpenseAdded) {
		this.isVehicleExpenseAdded = isVehicleExpenseAdded;
	}

	public boolean isDateWiseVehicleExpenseAdded() {
		return isDateWiseVehicleExpenseAdded;
	}

	public void setDateWiseVehicleExpenseAdded(boolean isDateWiseVehicleExpenseAdded) {
		this.isDateWiseVehicleExpenseAdded = isDateWiseVehicleExpenseAdded;
	}

	public boolean isMonthWiseVehicleExpenseAdded() {
		return isMonthWiseVehicleExpenseAdded;
	}

	public void setMonthWiseVehicleExpenseAdded(boolean isMonthWiseVehicleExpenseAdded) {
		this.isMonthWiseVehicleExpenseAdded = isMonthWiseVehicleExpenseAdded;
	}

	public boolean isDateWiseVehicleBalanceUpdated() {
		return isDateWiseVehicleBalanceUpdated;
	}

	public void setDateWiseVehicleBalanceUpdated(boolean isDateWiseVehicleBalanceUpdated) {
		this.isDateWiseVehicleBalanceUpdated = isDateWiseVehicleBalanceUpdated;
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

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	

	public Long getTripExpenseId() {
		return tripExpenseId;
	}

	public void setTripExpenseId(Long tripExpenseId) {
		this.tripExpenseId = tripExpenseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		result = prime * result + ((transactionSubTypeId == null) ? 0 : transactionSubTypeId.hashCode());
		result = prime * result + transactionTypeId;
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
		VehicleProfitAndLossTxnChecker other = (VehicleProfitAndLossTxnChecker) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		if (transactionSubTypeId == null) {
			if (other.transactionSubTypeId != null)
				return false;
		} else if (!transactionSubTypeId.equals(other.transactionSubTypeId))
			return false;
		if (transactionTypeId != other.transactionTypeId)
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
		builder.append("VehicleProfitAndLossTxnChecker [vehicleProfitAndLossTxnCheckerId=");
		builder.append(vehicleProfitAndLossTxnCheckerId);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", isVehicleExpenseAdded=");
		builder.append(isVehicleExpenseAdded);
		builder.append(", isDateWiseVehicleExpenseAdded=");
		builder.append(isDateWiseVehicleExpenseAdded);
		builder.append(", isMonthWiseVehicleExpenseAdded=");
		builder.append(isMonthWiseVehicleExpenseAdded);
		builder.append(", isDateWiseVehicleBalanceUpdated=");
		builder.append(isDateWiseVehicleBalanceUpdated);
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
		builder.append(", expenseId=");
		builder.append(expenseId);
		builder.append(", tripExpenseId=");
		builder.append(tripExpenseId);
		builder.append("]");
		return builder.toString();
	}


}

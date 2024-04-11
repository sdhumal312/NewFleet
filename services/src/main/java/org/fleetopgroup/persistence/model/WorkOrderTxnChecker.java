package org.fleetopgroup.persistence.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WorkOrderTxnChecker")
public class WorkOrderTxnChecker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "workOrderTxnCheckerId")
	private Long	workOrderTxnCheckerId;
	
	@Column(name = "workOrderId", nullable = false)
	private Long		workOrderId;
	
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

	public Long getWorkOrderTxnCheckerId() {
		return workOrderTxnCheckerId;
	}

	public void setWorkOrderTxnCheckerId(Long workOrderTxnCheckerId) {
		this.workOrderTxnCheckerId = workOrderTxnCheckerId;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
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

	@Override
	public String toString() {
		return "WorkOrderTxnChecker [workOrderTxnCheckerId=" + workOrderTxnCheckerId + ", workOrderId=" + workOrderId
				+ ", vid=" + vid + ", isVehicleExpenseAdded=" + isVehicleExpenseAdded
				+ ", isDateWiseVehicleExpenseAdded=" + isDateWiseVehicleExpenseAdded
				+ ", isMonthWiseVehicleExpenseAdded=" + isMonthWiseVehicleExpenseAdded
				+ ", isDateWiseVehicleBalanceUpdated=" + isDateWiseVehicleBalanceUpdated
				+ ", isMonthWiseVehicleBalanceUpdated=" + isMonthWiseVehicleBalanceUpdated + ", txnInsertionDateTime="
				+ txnInsertionDateTime + ", isFinallyEntered=" + isFinallyEntered + ", companyId=" + companyId + "]";
	}
	
	
	
	
}

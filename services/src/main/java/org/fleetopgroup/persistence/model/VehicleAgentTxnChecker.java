package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleAgentTxnChecker")
public class VehicleAgentTxnChecker implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleAgentTxnCheckerId")
	private Long	vehicleAgentTxnCheckerId;

	
	@Column(name = "transactionId", nullable = false)
	private Long		transactionId;
	
	@Column(name = "vid", nullable = false)
	private Integer		vid;
	
	@Column(name = "txnInsertionDateTime")
	private Date  txnInsertionDateTime;
	
	@Column(name = "systemDateTime")
	private Date  systemDateTime;
	
	@Column(name = "isIncomeExpenseAdded")
	private boolean isIncomeExpenseAdded;
	
	@Column(name = "isFinallyEntered")
	private boolean		isFinallyEntered;
	
	@Column(name = "txnTypeId")
	private short txnTypeId;
	
	@Column(name = "identifier")
	private short	identifier;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	

	public Long getVehicleAgentTxnCheckerId() {
		return vehicleAgentTxnCheckerId;
	}

	public void setVehicleAgentTxnCheckerId(Long vehicleAgentTxnCheckerId) {
		this.vehicleAgentTxnCheckerId = vehicleAgentTxnCheckerId;
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

	public Date getTxnInsertionDateTime() {
		return txnInsertionDateTime;
	}

	public void setTxnInsertionDateTime(Date txnInsertionDateTime) {
		this.txnInsertionDateTime = txnInsertionDateTime;
	}

	public Date getSystemDateTime() {
		return systemDateTime;
	}

	public void setSystemDateTime(Date systemDateTime) {
		this.systemDateTime = systemDateTime;
	}

	public boolean isIncomeExpenseAdded() {
		return isIncomeExpenseAdded;
	}

	public void setIncomeExpenseAdded(boolean isIncomeExpenseAdded) {
		this.isIncomeExpenseAdded = isIncomeExpenseAdded;
	}

	public boolean isFinallyEntered() {
		return isFinallyEntered;
	}

	public void setFinallyEntered(boolean isFinallyEntered) {
		this.isFinallyEntered = isFinallyEntered;
	}

	public short getTxnTypeId() {
		return txnTypeId;
	}

	public void setTxnTypeId(short txnTypeId) {
		this.txnTypeId = txnTypeId;
	}

	public short getIdentifier() {
		return identifier;
	}

	public void setIdentifier(short identifier) {
		this.identifier = identifier;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "VehicleAgentTxnChecker [vehicleAgentTxnCheckerId=" + vehicleAgentTxnCheckerId + ", transactionId="
				+ transactionId + ", vid=" + vid + ", txnInsertionDateTime=" + txnInsertionDateTime
				+ ", systemDateTime=" + systemDateTime + ", isIncomeExpenseAdded=" + isIncomeExpenseAdded
				+ ", isFinallyEntered=" + isFinallyEntered + ", txnTypeId=" + txnTypeId + ", identifier=" + identifier
				+ ", companyId=" + companyId + "]";
	}
	
	
	
}

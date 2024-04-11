package org.fleetopgroup.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CashPaymentTxnChecker")
public class CashPaymentTxnChecker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cashPaymentTxnCheckerId;
	
	private Long cashPaymentStatementId;
	
	private boolean dataSyncWithIvCargo;
	
	private boolean markForDelete;
	
	private LocalDateTime createdOn;

	public boolean isDataSyncWithIvCargo() {
		return dataSyncWithIvCargo;
	}

	public void setDataSyncWithIvCargo(boolean dataSyncWithIvCargo) {
		this.dataSyncWithIvCargo = dataSyncWithIvCargo;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getCashPaymentTxnCheckerId() {
		return cashPaymentTxnCheckerId;
	}

	public void setCashPaymentTxnCheckerId(Long cashPaymentTxnCheckerId) {
		this.cashPaymentTxnCheckerId = cashPaymentTxnCheckerId;
	}

	public Long getCashPaymentStatementId() {
		return cashPaymentStatementId;
	}

	public void setCashPaymentStatementId(Long cashPaymentStatementId) {
		this.cashPaymentStatementId = cashPaymentStatementId;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
}

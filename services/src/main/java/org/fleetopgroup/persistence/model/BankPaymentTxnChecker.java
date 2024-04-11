package org.fleetopgroup.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BankPaymentTxnChecker")
public class BankPaymentTxnChecker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bankPaymentTxnCheckerId;
	
	private Long bankPaymetDetailsToIvId;
	
	private boolean dataSyncWithIvCargo;
	
	private boolean markForDelete;
	
	private LocalDateTime createdOn;

	public Long getBankPaymentTxnCheckerId() {
		return bankPaymentTxnCheckerId;
	}

	public void setBankPaymentTxnCheckerId(Long bankPaymentTxnCheckerId) {
		this.bankPaymentTxnCheckerId = bankPaymentTxnCheckerId;
	}

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

	public Long getBankPaymetDetailsToIvId() {
		return bankPaymetDetailsToIvId;
	}

	public void setBankPaymetDetailsToIvId(Long bankPaymetDetailsToIvId) {
		this.bankPaymetDetailsToIvId = bankPaymetDetailsToIvId;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
}

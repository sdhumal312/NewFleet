package org.fleetopgroup.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CashBookVoucherSequenceCounter")
public class CashBookVoucherSequenceCounter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="voucherSequenceCounterId")
	private Long	voucherSequenceCounterId;
	
	@Column(name="cashBookId", nullable = false)
	private Integer cashBookId;
	
	@Column(name="nextVal")
	private Long	nextVal;
	
	@Column(name="companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name="markForDelete", nullable = false)
	private boolean	markForDelete;

	public Long getVoucherSequenceCounterId() {
		return voucherSequenceCounterId;
	}

	public void setVoucherSequenceCounterId(Long voucherSequenceCounterId) {
		this.voucherSequenceCounterId = voucherSequenceCounterId;
	}

	public Integer getCashBookId() {
		return cashBookId;
	}

	public void setCashBookId(Integer cashBookId) {
		this.cashBookId = cashBookId;
	}

	public Long getNextVal() {
		return nextVal;
	}

	public void setNextVal(Long nextVal) {
		this.nextVal = nextVal;
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
	
	public CashBookVoucherSequenceCounter() {
		super();
	}
	
	public CashBookVoucherSequenceCounter(Integer cashBookId, Long nextVal, Integer companyId) {
		super();
		this.cashBookId = cashBookId;
		this.nextVal = nextVal;
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashBookVoucherSequenceCounter [voucherSequenceCounterId=");
		builder.append(voucherSequenceCounterId);
		builder.append(", cashBookId=");
		builder.append(cashBookId);
		builder.append(", nextVal=");
		builder.append(nextVal);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}
	
	
}

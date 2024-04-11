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
@Table(name = "CashBookBalanceTemp")
public class CashBookBalanceTemp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long	id;
	
	@Column(name="cashBookId")
	private Integer cashBookId;
	
	@Column(name="cashDate")
	private Date	cashDate;
	
	@Column(name="currentAmount")
	private Double	currentAmount;
	
	@Column(name="actualAmount")
	private Double	actualAmount;
	
	@Column(name="diffAmount")
	private Double	diffAmount;
	
	@Column(name="markForDelete", nullable = false)
	private boolean markForDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCashBookId() {
		return cashBookId;
	}

	public void setCashBookId(Integer cashBookId) {
		this.cashBookId = cashBookId;
	}

	public Date getCashDate() {
		return cashDate;
	}

	public void setCashDate(Date cashDate) {
		this.cashDate = cashDate;
	}

	public Double getCurrentAmount() {
		return currentAmount;
	}

	public Double getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}

	public void setCurrentAmount(Double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashBookBalanceTemp [id=");
		builder.append(id);
		builder.append(", cashBookId=");
		builder.append(cashBookId);
		builder.append(", cashDate=");
		builder.append(cashDate);
		builder.append(", currentAmount=");
		builder.append(currentAmount);
		builder.append(", actualAmount=");
		builder.append(actualAmount);
		builder.append("]");
		return builder.toString();
	}
	
	
}

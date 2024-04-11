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
@Table(name = "LorryHirePaymentApprovalToHireDetails")
public class LorryHirePaymentApprovalToHireDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "approvalToHireDetailsId")
	private Long		approvalToHireDetailsId;
	
	@Column(name = "lorryHirePaymentId")
	private Long		lorryHirePaymentId;
	
	@Column(name = "lorryHireDetailsId")
	private Long		lorryHireDetailsId;
	
	@Column(name = "receiveAmount")
	private Double		receiveAmount;
	
	@Column(name = "paymentTypeId")
	private short		paymentTypeId;
	
	@Column(name = "transactionNumber")
	private String		transactionNumber;
	
	@Column(name = "transactionDate")
	private Timestamp	transactionDate;
	
	@Column(name = "remark")
	private String		remark;
	
	@Column(name = "paymentStatusId")
	private short		paymentStatusId;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	
	public LorryHirePaymentApprovalToHireDetails() {
		super();
	}


	public LorryHirePaymentApprovalToHireDetails(Long approvalToHireDetailsId, Long lorryHirePaymentId,
			Long lorryHireDetailsId, Double receiveAmount, short paymentTypeId, String transactionNumber,
			Timestamp transactionDate, String remark, short paymentStatusId, Timestamp createdOn, Integer companyId,
			boolean markForDelete) {
		super();
		this.approvalToHireDetailsId = approvalToHireDetailsId;
		this.lorryHirePaymentId = lorryHirePaymentId;
		this.lorryHireDetailsId = lorryHireDetailsId;
		this.receiveAmount = receiveAmount;
		this.paymentTypeId = paymentTypeId;
		this.transactionNumber = transactionNumber;
		this.transactionDate = transactionDate;
		this.remark = remark;
		this.paymentStatusId = paymentStatusId;
		this.createdOn = createdOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}


	public Long getApprovalToHireDetailsId() {
		return approvalToHireDetailsId;
	}


	public void setApprovalToHireDetailsId(Long approvalToHireDetailsId) {
		this.approvalToHireDetailsId = approvalToHireDetailsId;
	}


	public Long getLorryHirePaymentId() {
		return lorryHirePaymentId;
	}


	public void setLorryHirePaymentId(Long lorryHirePaymentId) {
		this.lorryHirePaymentId = lorryHirePaymentId;
	}


	public Long getLorryHireDetailsId() {
		return lorryHireDetailsId;
	}


	public void setLorryHireDetailsId(Long lorryHireDetailsId) {
		this.lorryHireDetailsId = lorryHireDetailsId;
	}


	public Double getReceiveAmount() {
		return receiveAmount;
	}


	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}


	public short getPaymentTypeId() {
		return paymentTypeId;
	}


	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}


	public String getTransactionNumber() {
		return transactionNumber;
	}


	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}


	public Timestamp getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public short getPaymentStatusId() {
		return paymentStatusId;
	}


	public void setPaymentStatusId(short paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}


	public Timestamp getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvalToHireDetailsId == null) ? 0 : approvalToHireDetailsId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((lorryHireDetailsId == null) ? 0 : lorryHireDetailsId.hashCode());
		result = prime * result + ((lorryHirePaymentId == null) ? 0 : lorryHirePaymentId.hashCode());
		result = prime * result + ((receiveAmount == null) ? 0 : receiveAmount.hashCode());
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
		LorryHirePaymentApprovalToHireDetails other = (LorryHirePaymentApprovalToHireDetails) obj;
		if (approvalToHireDetailsId == null) {
			if (other.approvalToHireDetailsId != null)
				return false;
		} else if (!approvalToHireDetailsId.equals(other.approvalToHireDetailsId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (lorryHireDetailsId == null) {
			if (other.lorryHireDetailsId != null)
				return false;
		} else if (!lorryHireDetailsId.equals(other.lorryHireDetailsId))
			return false;
		if (lorryHirePaymentId == null) {
			if (other.lorryHirePaymentId != null)
				return false;
		} else if (!lorryHirePaymentId.equals(other.lorryHirePaymentId))
			return false;
		if (receiveAmount == null) {
			if (other.receiveAmount != null)
				return false;
		} else if (!receiveAmount.equals(other.receiveAmount))
			return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LorryHirePaymentApprovalToHireDetails [approvalToHireDetailsId=");
		builder.append(approvalToHireDetailsId);
		builder.append(", lorryHirePaymentId=");
		builder.append(lorryHirePaymentId);
		builder.append(", lorryHireDetailsId=");
		builder.append(lorryHireDetailsId);
		builder.append(", receiveAmount=");
		builder.append(receiveAmount);
		builder.append(", paymentTypeId=");
		builder.append(paymentTypeId);
		builder.append(", transactionNumber=");
		builder.append(transactionNumber);
		builder.append(", transactionDate=");
		builder.append(transactionDate);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", paymentStatusId=");
		builder.append(paymentStatusId);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

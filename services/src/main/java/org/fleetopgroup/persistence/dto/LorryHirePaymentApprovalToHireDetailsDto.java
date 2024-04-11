package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class LorryHirePaymentApprovalToHireDetailsDto {

	private Long		approvalToHireDetailsId;
	
	private Long		lorryHirePaymentId;
	
	private Long		lorryHireDetailsId;
	
	private Double		receiveAmount;
	
	private short		paymentTypeId;
	
	private String		transactionNumber;
	
	private Timestamp	transactionDate;
	
	private String		remark;
	
	private short		paymentStatusId;
	
	private Timestamp	createdOn;
	
	private Integer		companyId;
	
	private String		createdOnStr;
	
	private String		transactionDateStr;
	
	private Long		lorryHireDetailsNumber;
	
	private Long		lorryHirePaymentNumber;
	
	private String		vendorName;
	
	private String		paymentType;
	
	private String		paymentStatus;

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
		this.receiveAmount = Utility.round(receiveAmount, 2) ;
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

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public String getTransactionDateStr() {
		return transactionDateStr;
	}

	public void setTransactionDateStr(String transactionDateStr) {
		this.transactionDateStr = transactionDateStr;
	}

	public Long getLorryHireDetailsNumber() {
		return lorryHireDetailsNumber;
	}

	public void setLorryHireDetailsNumber(Long lorryHireDetailsNumber) {
		this.lorryHireDetailsNumber = lorryHireDetailsNumber;
	}

	public Long getLorryHirePaymentNumber() {
		return lorryHirePaymentNumber;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public void setLorryHirePaymentNumber(Long lorryHirePaymentNumber) {
		this.lorryHirePaymentNumber = lorryHirePaymentNumber;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LorryHirePaymentApprovalToHireDetailsDto [approvalToHireDetailsId=");
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
		builder.append(", createdOnStr=");
		builder.append(createdOnStr);
		builder.append(", transactionDateStr=");
		builder.append(transactionDateStr);
		builder.append(", lorryHireDetailsNumber=");
		builder.append(lorryHireDetailsNumber);
		builder.append(", lorryHirePaymentNumber=");
		builder.append(lorryHirePaymentNumber);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

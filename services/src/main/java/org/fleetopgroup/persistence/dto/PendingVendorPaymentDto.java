package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class PendingVendorPaymentDto {
	
	private Long pendingPaymentId;
	
	private Integer vendorId;
	
	private Long transactionId;
	
	private short txnTypeId;
	
	private Double transactionAmount;
	
	private Double balanceAmount;
	
	private Double discount;
	
	private short	paymentStatusId;
	
	private Long approvalId;
	
	private Integer	companyId;
	
	private boolean	markForDelete;
	
	private String	transactionNumber;
	
	private String	vendorName;
	
	private Long	approvalNumber;
	
	private Date	transactionDate;
	
	private String	transactionDateStr;
	
	private String	paymentStatus;
	
	private String	invoiceNumber;
	
	private String	vehicleNumber;
	
	private Long	documentId;
	
	private Integer vid;

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Long getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(Long approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public Long getPendingPaymentId() {
		return pendingPaymentId;
	}

	public void setPendingPaymentId(Long pendingPaymentId) {
		this.pendingPaymentId = pendingPaymentId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public short getTxnTypeId() {
		return txnTypeId;
	}

	public void setTxnTypeId(short txnTypeId) {
		this.txnTypeId = txnTypeId;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = Utility.round(transactionAmount, 2);
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount,2);
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = Utility.round(discount, 2);
	}

	public short getPaymentStatusId() {
		return paymentStatusId;
	}

	public void setPaymentStatusId(short paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}

	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionDateStr() {
		return transactionDateStr;
	}

	public void setTransactionDateStr(String transactionDateStr) {
		this.transactionDateStr = transactionDateStr;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	
}

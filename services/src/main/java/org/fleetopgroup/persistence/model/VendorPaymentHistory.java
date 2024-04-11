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
@Table(name = "VendorPaymentHistory")
public class VendorPaymentHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendorPaymentHistoryId")
	private Long vendorPaymentHistoryId;

	@Column(name = "vendorPaymentId")
	private Long vendorPaymentId;
	
	@Column(name = "modifiedDate", nullable = true)
	private Date modifiedDate;

	@Column(name = "modifiedById" , nullable = true)
	private Long modifiedById;
	
	@Column(name = "transactionAmount" , nullable = false)
	private double transactionAmount;

	@Column(name = "gstAmount" , nullable = true)
	private double gstAmount;

	@Column(name = "invoiceNumber" , nullable = true)
	private String invoiceNumber;
	
	@Column(name = "invoiceDate", nullable = true)
	private Date invoiceDate;

	@Column(name = "paymentTypeId" , nullable = true)
	private short paymentTypeId;

	@Column(name = "cashVoucherNumber" , nullable = true)
	private String cashVoucherNumber;

	@Column(name = "chequeNumber" , nullable = true)
	private String chequeNumber;

	@Column(name = "chequeDate", nullable = true)
	private Date chequeDate;

	@Column(name = "markForDelete" , nullable = false)
	private boolean markForDelete;

	public Long getVendorPaymentHistoryId() {
		return vendorPaymentHistoryId;
	}

	public void setVendorPaymentHistoryId(Long vendorPaymentHistoryId) {
		this.vendorPaymentHistoryId = vendorPaymentHistoryId;
	}

	public Long getVendorPaymentId() {
		return vendorPaymentId;
	}

	public void setVendorPaymentId(Long vendorPaymentId) {
		this.vendorPaymentId = vendorPaymentId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(Long modifiedById) {
		this.modifiedById = modifiedById;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public double getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(double gstAmount) {
		this.gstAmount = gstAmount;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getCashVoucherNumber() {
		return cashVoucherNumber;
	}

	public void setCashVoucherNumber(String cashVoucherNumber) {
		this.cashVoucherNumber = cashVoucherNumber;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}
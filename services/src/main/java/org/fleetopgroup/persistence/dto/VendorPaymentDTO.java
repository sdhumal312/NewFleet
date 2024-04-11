package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class VendorPaymentDTO {
	
	private long 	vendorPaymentId;
	private long 	vendorPaymentNo;
	private String 	fromDate;
	private String 	toDate;
	private Date 	createdDate;
	private long 	createdById;
	private long 	companyId;
	private long 	vendorId;
	private long 	vendorTypeId;
	private String 	vendorName;
	private short 	transactionTypeId;
	private double 	transactionAmount;
	private double 	gstAmount;
	private double 	openingAmount;
	private String 	invoiceNumber;
	private Date 	invoiceDate;
	private String 	invoiceDateStr;
	private Date 	paymentDate;
	private String 	paymentDateStr;
	private short 	paymentTypeId;
	private String 	paymentTypeStr;
	private String 	cashVoucherNumber;
	private String 	chequeNumber;
	private Date 	chequeDate;
	private String 	chequeDateStr;
	private boolean markForDelete;
	private String gstNumber;
	private String vehicleGroup;
	private double 	totalAmount;
	
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount =  Utility.round(totalAmount, 2);
	}
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	
	
	public String getVehicleGroup() {
		return vehicleGroup;
	}
	public void setVehicleGroup(String vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
	}
	
	public long getVendorPaymentId() {
		return vendorPaymentId;
	}
	public void setVendorPaymentId(long vendorPaymentId) {
		this.vendorPaymentId = vendorPaymentId;
	}
	public long getVendorPaymentNo() {
		return vendorPaymentNo;
	}
	public void setVendorPaymentNo(long vendorPaymentNo) {
		this.vendorPaymentNo = vendorPaymentNo;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public long getCreatedById() {
		return createdById;
	}
	public void setCreatedById(long createdById) {
		this.createdById = createdById;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public long getVendorId() {
		return vendorId;
	}
	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}
	public long getVendorTypeId() {
		return vendorTypeId;
	}
	public void setVendorTypeId(long vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public short getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(short transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = Utility.round(transactionAmount, 2);
	}
	public double getGstAmount() {
		return gstAmount;
	}
	public void setGstAmount(double gstAmount) {
		this.gstAmount = Utility.round(gstAmount, 2);
	}
	public double getOpeningAmount() {
		return openingAmount;
	}
	public void setOpeningAmount(double openingAmount) {
		this.openingAmount = Utility.round(openingAmount, 2);
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
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}
	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}
	public short getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public String getPaymentDateStr() {
		return paymentDateStr;
	}
	public void setPaymentDateStr(String paymentDateStr) {
		this.paymentDateStr = paymentDateStr;
	}
	public String getPaymentTypeStr() {
		return paymentTypeStr;
	}
	public void setPaymentTypeStr(String paymentTypeStr) {
		this.paymentTypeStr = paymentTypeStr;
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
	public String getChequeDateStr() {
		return chequeDateStr;
	}
	public void setChequeDateStr(String chequeDateStr) {
		this.chequeDateStr = chequeDateStr;
	}
	public boolean isMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}
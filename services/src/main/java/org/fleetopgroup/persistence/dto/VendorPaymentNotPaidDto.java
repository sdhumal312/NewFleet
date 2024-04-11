package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class VendorPaymentNotPaidDto{
	
	private Long serialNumberId;
	
	private Long serialNumber;
	
	private String serialNumberStr;
	
	private String invoiceNumber;
	
	private Date invoiceDate;
	
	private String invoiceDateStr;
	
	private String vendorName;
	
	private int vid;
	
	private String vehicleRegistration;
	
	private String paymentType;
	
	private double invoiceAmount;
	
	private double balanceAmount;
	

	public VendorPaymentNotPaidDto() {
		super();
		
	}


	public VendorPaymentNotPaidDto(Long serialNumberId, Long serialNumber, String invoiceNumber, Date invoiceDate,
			String invoiceDateStr, String vendorName, int vid, String vehicleRegistration, String paymentType,
			double invoiceAmount, double balanceAmount) {
		super();
		this.serialNumberId = serialNumberId;
		this.serialNumber = serialNumber;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.invoiceDateStr = invoiceDateStr;
		this.vendorName = vendorName;
		this.vid = vid;
		this.vehicleRegistration = vehicleRegistration;
		this.paymentType = paymentType;
		this.invoiceAmount = invoiceAmount;
		this.balanceAmount = balanceAmount;
	}


	public Long getSerialNumberId() {
		return serialNumberId;
	}


	public void setSerialNumberId(Long serialNumberId) {
		this.serialNumberId = serialNumberId;
	}


	public Long getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}


	public int getVid() {
		return vid;
	}


	public void setVid(int vid) {
		this.vid = vid;
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


	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}


	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}


	public String getVendorName() {
		return vendorName;
	}


	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	public String getVehicleRegistration() {
		return vehicleRegistration;
	}


	public void setVehicleRegistration(String vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}


	public String getPaymentType() {
		return paymentType;
	}


	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


	public double getInvoiceAmount() {
		return invoiceAmount;
	}


	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = Utility.round(invoiceAmount, 2);
	}


	public double getBalanceAmount() {
		return balanceAmount;
	}


	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2);
	}


	public String getSerialNumberStr() {
		return serialNumberStr;
	}


	public void setSerialNumberStr(String serialNumberStr) {
		this.serialNumberStr = serialNumberStr;
	}


	@Override
	public String toString() {
		return "VendorPaymentNotPaidDto [serialNumberId=" + serialNumberId + ", serialNumber=" + serialNumber
				+ ", invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate + ", invoiceDateStr="
				+ invoiceDateStr + ", vendorName=" + vendorName + ", vid=" + vid + ", vehicleRegistration="
				+ vehicleRegistration + ", paymentType=" + paymentType + ", invoiceAmount=" + invoiceAmount
				+ ", balanceAmount=" + balanceAmount + "]";
	}
	
	
}
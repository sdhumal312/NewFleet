package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class UpholsterySendLaundryInvoiceDto {

	private Long laundryInvoiceId;
	
	private Long laundryInvoiceNumber;
	
	private Integer		wareHouseLocationId;
	
	private Integer		vendorId;
	
	private Double		totalQuantity;
	
	private Double		receivedQuantity;
	
	private Double		losedQuantity;
	
	private Double		damagedQuantity;
	
	private Double		totalCost;
	
	private Date		sentDate;
	
	private Date		expectedReceiveDate;
	
	private	short		paymentTypeId;
	
	private String		paymentNumber;
	
	private String		quoteNumber;
	
	private String		manualNumber;
	
	private String		description;
	
	private Integer		companyId;
	
	private Long		createdById;
	
	private Long		lastModifiedById;
	
	private Date		creationDate;
	
	private Date		lastModifiedDate;
	
	private String		vendorName;
	
	private String		locationName;
	
	private String		sentDateStr;
	
	private String		expectedReceiveDateStr;
	
	private String		createdBy;
	
	private String		lastModifiedBy;
	
	private String		vendorLocation;
	
	private String		paymentType;
	
	private String		creationDateStr;
	
	private String		lastModifiedOnStr;
	
	private double		finalReceivedQty;
	
	private String		clothName;
	
	private double		remainingQuantity;
	
	private String      partlocation_name;   
	
	private Long		clothTypesId; 
	
	private Double      clothEachCost; 
	
	private Long 		tallyCompanyId;
	
	private String 		tallyCompanyName;
	
	private String firstName; 

	private String lastName; 
	
	private Double clothTotal;
	
	private Double quantity;
	
	private short vendorPaymentStatus;
	
	private Double	paidAmount;
	
	private Double	balanceAmount;
	
	private String	vendorPaymentStatusStr;
	
	private String	invoiceDateStr;
	
	
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity =  Utility.round(quantity, 2);
	}

	public Double getClothTotal() {
		return clothTotal;
	}

	public void setClothTotal(Double clothTotal) {
		this.clothTotal = Utility.round(clothTotal, 2);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getClothEachCost() {
		return clothEachCost;
	}

	public void setClothEachCost(Double clothEachCost) {
		this.clothEachCost = Utility.round(clothEachCost, 2);
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public String getPartlocation_name() {
		return partlocation_name;
	}

	public void setPartlocation_name(String partlocation_name) {
		this.partlocation_name = partlocation_name;
	}

	public Long getLaundryInvoiceId() {
		return laundryInvoiceId;
	}

	public void setLaundryInvoiceId(Long laundryInvoiceId) {
		this.laundryInvoiceId = laundryInvoiceId;
	}

	public Long getLaundryInvoiceNumber() {
		return laundryInvoiceNumber;
	}

	public void setLaundryInvoiceNumber(Long laundryInvoiceNumber) {
		this.laundryInvoiceNumber = laundryInvoiceNumber;
	}

	public Integer getWareHouseLocationId() {
		return wareHouseLocationId;
	}

	public void setWareHouseLocationId(Integer wareHouseLocationId) {
		this.wareHouseLocationId = wareHouseLocationId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = Utility.round(totalQuantity, 2);
	}

	public Double getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(Double receivedQuantity) {
		this.receivedQuantity = Utility.round( receivedQuantity,2);
	}

	public Double getLosedQuantity() {
		return losedQuantity;
	}

	public void setLosedQuantity(Double losedQuantity) {
		this.losedQuantity = Utility.round(losedQuantity, 2);
	}

	public Double getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(Double damagedQuantity) {
		this.damagedQuantity = Utility.round( damagedQuantity, 2);
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = Utility.round(totalCost, 2);
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Date getExpectedReceiveDate() {
		return expectedReceiveDate;
	}

	public void setExpectedReceiveDate(Date expectedReceiveDate) {
		this.expectedReceiveDate = expectedReceiveDate;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public String getQuoteNumber() {
		return quoteNumber;
	}

	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}

	public String getManualNumber() {
		return manualNumber;
	}

	public void setManualNumber(String manualNumber) {
		this.manualNumber = manualNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getSentDateStr() {
		return sentDateStr;
	}

	public void setSentDateStr(String sentDateStr) {
		this.sentDateStr = sentDateStr;
	}

	public String getExpectedReceiveDateStr() {
		return expectedReceiveDateStr;
	}

	public void setExpectedReceiveDateStr(String expectedReceiveDateStr) {
		this.expectedReceiveDateStr = expectedReceiveDateStr;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getVendorLocation() {
		return vendorLocation;
	}

	public void setVendorLocation(String vendorLocation) {
		this.vendorLocation = vendorLocation;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCreationDateStr() {
		return creationDateStr;
	}

	public void setCreationDateStr(String creationDateStr) {
		this.creationDateStr = creationDateStr;
	}

	public String getLastModifiedOnStr() {
		return lastModifiedOnStr;
	}

	public void setLastModifiedOnStr(String lastModifiedOnStr) {
		this.lastModifiedOnStr = lastModifiedOnStr;
	}

	public double getFinalReceivedQty() {
		return finalReceivedQty;
	}

	public void setFinalReceivedQty(double finalReceivedQty) {
		this.finalReceivedQty = Utility.round(finalReceivedQty,2);
	}

	public String getClothName() {
		return clothName;
	}

	public void setClothName(String clothName) {
		this.clothName = clothName;
	}
	
	public double getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(double remainingQuantity) {
		this.remainingQuantity = Utility.round(remainingQuantity, 2);
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public String getTallyCompanyName() {
		return tallyCompanyName;
	}

	public void setTallyCompanyName(String tallyCompanyName) {
		this.tallyCompanyName = tallyCompanyName;
	}

	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount =  Utility.round(paidAmount, 2);
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2);
	}

	public String getVendorPaymentStatusStr() {
		return vendorPaymentStatusStr;
	}

	public void setVendorPaymentStatusStr(String vendorPaymentStatusStr) {
		this.vendorPaymentStatusStr = vendorPaymentStatusStr;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpholsterySendLaundryInvoiceDto [laundryInvoiceId=");
		builder.append(laundryInvoiceId);
		builder.append(", laundryInvoiceNumber=");
		builder.append(laundryInvoiceNumber);
		builder.append(", wareHouseLocationId=");
		builder.append(wareHouseLocationId);
		builder.append(", vendorId=");
		builder.append(vendorId);
		builder.append(", totalQuantity=");
		builder.append(totalQuantity);
		builder.append(", receivedQuantity=");
		builder.append(receivedQuantity);
		builder.append(", losedQuantity=");
		builder.append(losedQuantity);
		builder.append(", damagedQuantity=");
		builder.append(damagedQuantity);
		builder.append(", totalCost=");
		builder.append(totalCost);
		builder.append(", sentDate=");
		builder.append(sentDate);
		builder.append(", expectedReceiveDate=");
		builder.append(expectedReceiveDate);
		builder.append(", paymentTypeId=");
		builder.append(paymentTypeId);
		builder.append(", paymentNumber=");
		builder.append(paymentNumber);
		builder.append(", quoteNumber=");
		builder.append(quoteNumber);
		builder.append(", manualNumber=");
		builder.append(manualNumber);
		builder.append(", description=");
		builder.append(description);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastModifiedDate=");
		builder.append(lastModifiedDate);
		builder.append(", vendorName=");
		builder.append(vendorName);
		builder.append(", locationName=");
		builder.append(locationName);
		builder.append(", sentDateStr=");
		builder.append(sentDateStr);
		builder.append(", expectedReceiveDateStr=");
		builder.append(expectedReceiveDateStr);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", vendorLocation=");
		builder.append(vendorLocation);
		builder.append(", paymentType=");
		builder.append(paymentType);
		builder.append(", creationDateStr=");
		builder.append(creationDateStr);
		builder.append(", lastModifiedOnStr=");
		builder.append(lastModifiedOnStr);
		builder.append(", finalReceivedQty=");
		builder.append(finalReceivedQty);
		builder.append(", clothName=");
		builder.append(clothName);
		builder.append(", remainingQuantity=");
		builder.append(remainingQuantity);
		builder.append(", partlocation_name=");
		builder.append(partlocation_name);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", clothEachCost=");
		builder.append(clothEachCost);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", clothTotal=");
		builder.append(clothTotal);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}

}

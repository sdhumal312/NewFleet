package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class BatteryInvoiceDto {

	private Long 		batteryInvoiceId;
	
	private Long 		batteryInvoiceNumber;
	
	private Integer		wareHouseLocation;
	
	private String		batteryLocation;
	
	private Long		purchaseOrderId;
	
	private String		invoiceNumber;
	
	private Timestamp	invoiceDate;
	
	private Double		invoiceAmount;
	
	private Integer		vendorId;
	
	private String		vendorName;
	
	private String		vendorLocation;
	
	private short		paymentTypeId;
	
	private String		paymentNumber;
	
	private Double 		totalBatteryAmount;
	
	private String 		description;
	
	private short		vendorPaymentStatus;
	
	private String 		vendorPaymentStatusStr;
	
	private String		invoiceDateStr;
	
	private String		vendorPaymentDateStr;
	
	private String		paymentStatus;
	
	private Long 		batteryApprovalId;
	
	private Timestamp 	vendorPaymentDate;
	
	private Long 		createdById;
	
	private Long	 	lastModifiedById;

	private boolean			 markForDelete;
	
	private Timestamp		createdOn;
	
	private Timestamp		lastModifiedBy;
	
	private String			created;
	
	private String		   lastModified;
	
	private Integer			companyId;
	
	private String			poNumber;
	
	private String firstName;

	private String lastName;
	private short typeOfPaymentId;
	private double paidAmount;
	private double balanceAmount;
	private Long	tallyCompanyId;
	private String	tallyCompanyName;
	private boolean battery_document;
	private Long battery_document_id;
	private Integer subLocationId;
	private String subLocation;
	
	private Long approvalId;
	private String approvalNumber;
	
	
	public short getTypeOfPaymentId() {
		return typeOfPaymentId;
	}

	public void setTypeOfPaymentId(short typeOfPaymentId) {
		this.typeOfPaymentId = typeOfPaymentId;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = Utility.round(paidAmount, 2);
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2);
	}

	

	public Long getBatteryInvoiceId() {
		return batteryInvoiceId;
	}

	public Long getBatteryInvoiceNumber() {
		return batteryInvoiceNumber;
	}

	public Integer getWareHouseLocation() {
		return wareHouseLocation;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public Double getTotalBatteryAmount() {
		return totalBatteryAmount;
	}

	public String getDescription() {
		return description;
	}

	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public Long getBatteryApprovalId() {
		return batteryApprovalId;
	}

	public Timestamp getVendorPaymentDate() {
		return vendorPaymentDate;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public Timestamp getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setBatteryInvoiceId(Long batteryInvoiceId) {
		this.batteryInvoiceId = batteryInvoiceId;
	}

	public void setBatteryInvoiceNumber(Long batteryInvoiceNumber) {
		this.batteryInvoiceNumber = batteryInvoiceNumber;
	}

	public void setWareHouseLocation(Integer wareHouseLocation) {
		this.wareHouseLocation = wareHouseLocation;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = Utility.round(invoiceAmount, 2);
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public void setTotalBatteryAmount(Double totalBatteryAmount) {
		this.totalBatteryAmount = Utility.round(totalBatteryAmount, 2);
	}

	public String getVendorPaymentStatusStr() {
		return vendorPaymentStatusStr;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}

	public String getVendorPaymentDateStr() {
		return vendorPaymentDateStr;
	}

	public void setVendorPaymentStatusStr(String vendorPaymentStatusStr) {
		this.vendorPaymentStatusStr = vendorPaymentStatusStr;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}

	public void setVendorPaymentDateStr(String vendorPaymentDateStr) {
		this.vendorPaymentDateStr = vendorPaymentDateStr;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCreated() {
		return created;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public void setBatteryApprovalId(Long batteryApprovalId) {
		this.batteryApprovalId = batteryApprovalId;
	}

	public void setVendorPaymentDate(Timestamp vendorPaymentDate) {
		this.vendorPaymentDate = vendorPaymentDate;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setLastModifiedBy(Timestamp lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getBatteryLocation() {
		return batteryLocation;
	}

	public void setBatteryLocation(String batteryLocation) {
		this.batteryLocation = batteryLocation;
	}

	public String getVendorName() {
		return vendorName;
	}

	public String getVendorLocation() {
		return vendorLocation;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setVendorLocation(String vendorLocation) {
		this.vendorLocation = vendorLocation;
	}	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public boolean isBattery_document() {
		return battery_document;
	}

	public void setBattery_document(boolean battery_document) {
		this.battery_document = battery_document;
	}

	public Long getBattery_document_id() {
		return battery_document_id;
	}

	public void setBattery_document_id(Long battery_document_id) {
		this.battery_document_id = battery_document_id;
	}

	public Integer getSubLocationId() {
		return subLocationId;
	}

	public void setSubLocationId(Integer subLocationId) {
		this.subLocationId = subLocationId;
	}

	public String getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
	}

	
	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	@Override
	public String toString() {
		return "BatteryInvoiceDto [batteryInvoiceId=" + batteryInvoiceId + ", batteryInvoiceNumber="
				+ batteryInvoiceNumber + ", wareHouseLocation=" + wareHouseLocation + ", batteryLocation="
				+ batteryLocation + ", purchaseOrderId=" + purchaseOrderId + ", invoiceNumber=" + invoiceNumber
				+ ", invoiceDate=" + invoiceDate + ", invoiceAmount=" + invoiceAmount + ", vendorId=" + vendorId
				+ ", vendorName=" + vendorName + ", vendorLocation=" + vendorLocation + ", paymentTypeId="
				+ paymentTypeId + ", paymentNumber=" + paymentNumber + ", totalBatteryAmount=" + totalBatteryAmount
				+ ", description=" + description + ", vendorPaymentStatus=" + vendorPaymentStatus
				+ ", vendorPaymentStatusStr=" + vendorPaymentStatusStr + ", invoiceDateStr=" + invoiceDateStr
				+ ", vendorPaymentDateStr=" + vendorPaymentDateStr + ", paymentStatus=" + paymentStatus
				+ ", batteryApprovalId=" + batteryApprovalId + ", vendorPaymentDate=" + vendorPaymentDate
				+ ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById + ", markForDelete="
				+ markForDelete + ", createdOn=" + createdOn + ", lastModifiedBy=" + lastModifiedBy + ", created="
				+ created + ", lastModified=" + lastModified + ", companyId=" + companyId + ", poNumber=" + poNumber
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", typeOfPaymentId=" + typeOfPaymentId
				+ ", paidAmount=" + paidAmount + ", balanceAmount=" + balanceAmount + ", tallyCompanyId="
				+ tallyCompanyId + ", tallyCompanyName=" + tallyCompanyName + ", battery_document=" + battery_document
				+ ", battery_document_id=" + battery_document_id + ", subLocationId=" + subLocationId + ", subLocation="
				+ subLocation + "]";
	}



		

}

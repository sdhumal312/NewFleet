package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class VendorSubApprovalDetailsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long subApprovalId;
	private Long approvalId;
	private Long approvalNumber;
	private Integer approvalvendorID;
	private Double subApprovalTotal;
	private Long invoiceId;
	private short approvalStatusId;
	private short approvalPlaceId;
	private short approvedPaymentStatusId;
	private short approvalPaymentTypeId;
	private Date expectedPaymentDate;
	private String expectedPaymentDateStr;
	private Long createdById;
	private Long lastModifiedById;
	private Boolean markForDelete;
	private Integer companyId;
	private Date created;
	private String createdStr;
	private Date lastupdated;
	private String lastupdatedStr;
	private String vendorName;
	private String approvalvendorType;
	private String approvalvendorLocation;
	private String approvalStatus;
	private String approvalPlace;
	private String approvalCreateBy;
	private Double subApprovalPaidAmount;
	private String approvedPaymentStatus;
	private Long partInvoiceNumber;
	private Long batteryInvoiceNumber;
	private Long tyreInvoiceNumber;
	private Long tyreRetreadInvoiceNumber;
	private Long clothInvoiceNumber;
	private Long fuelNumber;
	private Long serviceEntriesNumber;
	private Long purchaseOrderNumber;
	private String vehicle;
	private String invoicePartNumber;
	private String invoiceBatteryNumber;
	private String invoiceTyreNumber;
	private String invoiceTyreRetreadNumber;
	private String invoiceClothNumber;
	private String invoiceSENumber;
	private String invoicePONumber;
	private String partInvoiceDate;
	private String batteryInvoiceDate;
	private String tyreInvoiceDate;
	private String tyreRetreadInvoiceDate;
	private String clothInvoiceDate;
	private String serviceEntriesDate;
	private String purchaseOrderDate;
	private String jobNumber;
	private Long	laundryInvoiceId;
	private Long	laundryInvoiceNumber;
	private String	sentDateStr;
	private String 	invoiceLINumber;
	private String	transactionNumber;
	private String	invoiceNumber;
	private String	invoiceDateStr;
	private Integer	vid;
	private String	vehicleNumber;
	private String	transactionUrl;
	         
	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getApprovalvendorType() {
		return approvalvendorType;
	}
	public void setApprovalvendorType(String approvalvendorType) {
		this.approvalvendorType = approvalvendorType;
	}
	public String getApprovalvendorLocation() {
		return approvalvendorLocation;
	}
	public void setApprovalvendorLocation(String approvalvendorLocation) {
		this.approvalvendorLocation = approvalvendorLocation;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getApprovalPlace() {
		return approvalPlace;
	}
	public void setApprovalPlace(String approvalPlace) {
		this.approvalPlace = approvalPlace;
	}
	public String getApprovalCreateBy() {
		return approvalCreateBy;
	}
	public void setApprovalCreateBy(String approvalCreateBy) {
		this.approvalCreateBy = approvalCreateBy;
	}
	public Long getSubApprovalId() {
		return subApprovalId;
	}
	public void setSubApprovalId(Long subApprovalId) {
		this.subApprovalId = subApprovalId;
	}
	public Long getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}
	public Long getApprovalNumber() {
		return approvalNumber;
	}
	public void setApprovalNumber(Long approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	public Integer getApprovalvendorID() {
		return approvalvendorID;
	}
	public void setApprovalvendorID(Integer approvalvendorID) {
		this.approvalvendorID = approvalvendorID;
	}
	public Double getSubApprovalTotal() {
		return subApprovalTotal;
	}
	public void setSubApprovalTotal(Double subApprovalTotal) {
		this.subApprovalTotal = Utility.round(subApprovalTotal, 2) ;
	}
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public short getApprovalStatusId() {
		return approvalStatusId;
	}
	public void setApprovalStatusId(short approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}
	public short getApprovalPlaceId() {
		return approvalPlaceId;
	}
	public void setApprovalPlaceId(short approvalPlaceId) {
		this.approvalPlaceId = approvalPlaceId;
	}
	public short getApprovedPaymentStatusId() {
		return approvedPaymentStatusId;
	}
	public void setApprovedPaymentStatusId(short approvedPaymentStatusId) {
		this.approvedPaymentStatusId = approvedPaymentStatusId;
	}
	public Date getExpectedPaymentDate() {
		return expectedPaymentDate;
	}
	public void setExpectedPaymentDate(Date expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}
	public String getExpectedPaymentDateStr() {
		return expectedPaymentDateStr;
	}
	public void setExpectedPaymentDateStr(String expectedPaymentDateStr) {
		this.expectedPaymentDateStr = expectedPaymentDateStr;
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
	public Boolean getMarkForDelete() {
		return markForDelete;
	}
	public void setMarkForDelete(Boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getCreatedStr() {
		return createdStr;
	}
	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}
	public Date getLastupdated() {
		return lastupdated;
	}
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}
	public String getLastupdatedStr() {
		return lastupdatedStr;
	}
	public void setLastupdatedStr(String lastupdatedStr) {
		this.lastupdatedStr = lastupdatedStr;
	}
	public Double getSubApprovalPaidAmount() {
		return subApprovalPaidAmount;
	}
	public void setSubApprovalPaidAmount(Double subApprovalPaidAmount) {
		this.subApprovalPaidAmount = Utility.round( subApprovalPaidAmount, 2);
	}
	public String getApprovedPaymentStatus() {
		return approvedPaymentStatus;
	}
	public void setApprovedPaymentStatus(String approvedPaymentStatus) {
		this.approvedPaymentStatus = approvedPaymentStatus;
	}
	public Long getPartInvoiceNumber() {
		return partInvoiceNumber;
	}
	public void setPartInvoiceNumber(Long partInvoiceNumber) {
		this.partInvoiceNumber = partInvoiceNumber;
	}
	
	public Long getBatteryInvoiceNumber() {
		return batteryInvoiceNumber;
	}
	public void setBatteryInvoiceNumber(Long batteryInvoiceNumber) {
		this.batteryInvoiceNumber = batteryInvoiceNumber;
	}
	public Long getTyreInvoiceNumber() {
		return tyreInvoiceNumber;
	}
	public void setTyreInvoiceNumber(Long tyreInvoiceNumber) {
		this.tyreInvoiceNumber = tyreInvoiceNumber;
	}
	public Long getTyreRetreadInvoiceNumber() {
		return tyreRetreadInvoiceNumber;
	}
	public void setTyreRetreadInvoiceNumber(Long tyreRetreadInvoiceNumber) {
		this.tyreRetreadInvoiceNumber = tyreRetreadInvoiceNumber;
	}
	public Long getClothInvoiceNumber() {
		return clothInvoiceNumber;
	}
	public void setClothInvoiceNumber(Long clothInvoiceNumber) {
		this.clothInvoiceNumber = clothInvoiceNumber;
	}
	public Long getFuelNumber() {
		return fuelNumber;
	}
	public void setFuelNumber(Long fuelNumber) {
		this.fuelNumber = fuelNumber;
	}
	public Long getServiceEntriesNumber() {
		return serviceEntriesNumber;
	}
	public void setServiceEntriesNumber(Long serviceEntriesNumber) {
		this.serviceEntriesNumber = serviceEntriesNumber;
	}
	public Long getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(Long purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public String getInvoicePartNumber() {
		return invoicePartNumber;
	}
	public void setInvoicePartNumber(String invoicePartNumber) {
		this.invoicePartNumber = invoicePartNumber;
	}
	public String getInvoiceBatteryNumber() {
		return invoiceBatteryNumber;
	}
	public void setInvoiceBatteryNumber(String invoiceBatteryNumber) {
		this.invoiceBatteryNumber = invoiceBatteryNumber;
	}
	public String getInvoiceTyreNumber() {
		return invoiceTyreNumber;
	}
	public void setInvoiceTyreNumber(String invoiceTyreNumber) {
		this.invoiceTyreNumber = invoiceTyreNumber;
	}
	public String getInvoiceTyreRetreadNumber() {
		return invoiceTyreRetreadNumber;
	}
	public void setInvoiceTyreRetreadNumber(String invoiceTyreRetreadNumber) {
		this.invoiceTyreRetreadNumber = invoiceTyreRetreadNumber;
	}
	public String getInvoiceClothNumber() {
		return invoiceClothNumber;
	}
	public void setInvoiceClothNumber(String invoiceClothNumber) {
		this.invoiceClothNumber = invoiceClothNumber;
	}
	public String getInvoiceSENumber() {
		return invoiceSENumber;
	}
	public void setInvoiceSENumber(String invoiceSENumber) {
		this.invoiceSENumber = invoiceSENumber;
	}
	public String getInvoicePONumber() {
		return invoicePONumber;
	}
	public void setInvoicePONumber(String invoicePONumber) {
		this.invoicePONumber = invoicePONumber;
	}
	public String getPartInvoiceDate() {
		return partInvoiceDate;
	}
	public void setPartInvoiceDate(String partInvoiceDate) {
		this.partInvoiceDate = partInvoiceDate;
	}
	public String getBatteryInvoiceDate() {
		return batteryInvoiceDate;
	}
	public void setBatteryInvoiceDate(String batteryInvoiceDate) {
		this.batteryInvoiceDate = batteryInvoiceDate;
	}
	public String getTyreInvoiceDate() {
		return tyreInvoiceDate;
	}
	public void setTyreInvoiceDate(String tyreInvoiceDate) {
		this.tyreInvoiceDate = tyreInvoiceDate;
	}
	public String getTyreRetreadInvoiceDate() {
		return tyreRetreadInvoiceDate;
	}
	public void setTyreRetreadInvoiceDate(String tyreRetreadInvoiceDate) {
		this.tyreRetreadInvoiceDate = tyreRetreadInvoiceDate;
	}
	public String getClothInvoiceDate() {
		return clothInvoiceDate;
	}
	public void setClothInvoiceDate(String clothInvoiceDate) {
		this.clothInvoiceDate = clothInvoiceDate;
	}
	public String getServiceEntriesDate() {
		return serviceEntriesDate;
	}
	public void setServiceEntriesDate(String serviceEntriesDate) {
		this.serviceEntriesDate = serviceEntriesDate;
	}
	public String getPurchaseOrderDate() {
		return purchaseOrderDate;
	}
	public void setPurchaseOrderDate(String purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
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
	public String getSentDateStr() {
		return sentDateStr;
	}
	public void setSentDateStr(String sentDateStr) {
		this.sentDateStr = sentDateStr;
	}
	public String getInvoiceLINumber() {
		return invoiceLINumber;
	}
	public void setInvoiceLINumber(String invoiceLINumber) {
		this.invoiceLINumber = invoiceLINumber;
	}
	public String getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}
	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}
	public short getApprovalPaymentTypeId() {
		return approvalPaymentTypeId;
	}
	public void setApprovalPaymentTypeId(short approvalPaymentTypeId) {
		this.approvalPaymentTypeId = approvalPaymentTypeId;
	}
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getTransactionUrl() {
		return transactionUrl;
	}
	public void setTransactionUrl(String transactionUrl) {
		this.transactionUrl = transactionUrl;
	}
	@Override
	public String toString() {
		return "VendorSubApprovalDetailsDto [subApprovalId=" + subApprovalId + ", approvalId=" + approvalId
				+ ", approvalNumber=" + approvalNumber + ", approvalvendorID=" + approvalvendorID
				+ ", subApprovalTotal=" + subApprovalTotal + ", invoiceId=" + invoiceId + ", approvalStatusId="
				+ approvalStatusId + ", approvalPlaceId=" + approvalPlaceId + ", approvedPaymentStatusId="
				+ approvedPaymentStatusId + ", expectedPaymentDate=" + expectedPaymentDate + ", expectedPaymentDateStr="
				+ expectedPaymentDateStr + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", markForDelete=" + markForDelete + ", companyId=" + companyId + ", created=" + created
				+ ", createdStr=" + createdStr + ", lastupdated=" + lastupdated + ", lastupdatedStr=" + lastupdatedStr
				+ ", vendorName=" + vendorName + ", approvalvendorType=" + approvalvendorType
				+ ", approvalvendorLocation=" + approvalvendorLocation + ", approvalStatus=" + approvalStatus
				+ ", approvalPlace=" + approvalPlace + ", approvalCreateBy=" + approvalCreateBy
				+ ", subApprovalPaidAmount=" + subApprovalPaidAmount + ", approvedPaymentStatus="
				+ approvedPaymentStatus + ", partInvoiceNumber=" + partInvoiceNumber + ", batteryInvoiceNumber="
				+ batteryInvoiceNumber + ", tyreInvoiceNumber=" + tyreInvoiceNumber + ", tyreRetreadInvoiceNumber="
				+ tyreRetreadInvoiceNumber + ", clothInvoiceNumber=" + clothInvoiceNumber + ", fuelNumber=" + fuelNumber
				+ ", serviceEntriesNumber=" + serviceEntriesNumber + ", purchaseOrderNumber=" + purchaseOrderNumber
				+ "]";
	}

	

	
}

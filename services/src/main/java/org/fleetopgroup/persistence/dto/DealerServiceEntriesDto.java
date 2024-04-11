package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class DealerServiceEntriesDto {
	private Long dealerServiceEntriesId;
	
	private Long dealerServiceEntriesNumber;
	
	private String dealerServiceEntriesNumberStr;

	private Integer vid;
	
	private String vehicleNumber;

	private Integer vehicleOdometer;
	
	private	Double	gpsOdometer;

	private String invoiceNumber;

	private String jobNumber;
	
	private Date invoiceDate;
	
	private String invoiceDateStr;

	private short paymentTypeId;
	
	private String paymentType;

	private String payNumber;

	private Long paidById;
	
	private String paidBy;

	private Date paidDate;
	
	private String paidDateStr;
	
	private String labourStatus;

	private Double totalInvoiceCost;
	
	private Double totalServiceCost;

	private Double totalServiceRoundCost;

	private short serviceTypeId;
	
	private String serviceType;
	
	private short statusId;
	
	private String status;

	private Date completedDate;
	
	private String completedDateStr;
	
	private Date	expectedPaymentDate;
	
	private String expectedPaymentDateStr;
	
	private Integer vendorId;
	
	private String vendorName;
	
	private Date vendorPaymentDate;
	
	private String vendorPaymentDateStr;
	
	private short vendorPaymodeId;
	
	private String vendorPaymode;
	
	private Long vendorApprovalId;
	
	private Double paidAmount;
	
	private Double balanceAmount;
	
	private Double taxableAmount;
	
	private Double discountAmount;
	
	private Long createdById;
	
	private String createdBy;

	private Long lastModifiedById;
	
	private String lastModifiedBy;

	private Date creationOn;
	
	private String creationDate;

	private Date lastUpdatedOn;
	
	private String lastUpdatedDate;
	
	private Integer companyId;
	
	private boolean markForDelete;
	
	private Long dealerServiceDocumentId;
	
	private String vehicleEngineNumber;
	
	private String vehicleChasisNumber;

	private String vendorAddress;
	
	private String serviceReminderIds;
	
	private long issueId;

	private String issueEncryptId;
	
	private String issue;
	
	private long issueNumber;
	
	private String issueNumberStr;
	
	private String issueSummary;
	
	private Integer driverId;
	
	private String driverFirstName;
	
	private String driverLastName;
	
	private String driverFatherName;
	
	private String driverFullName;

	private String remark;
	
	private boolean isPartApplicable;
	
	private boolean isLabourApplicable;
	
	private String dsePartStatus;

	private String dseExtraIssue;
	
	private Long assignToId;
	
	private String assignTo;
	
	private Long serviceReminder;
	
	private Long vehicleModelId;
	
	private String accident;
	
	private Long accidentId;
	
	private Double totalPartCost;
	
	private Double totalLabourCost;
	
	private String totalPartstr;
	
	private String totalLabourstr;
	
	private Long dsePartId;

	private Long dseabourId;
	
	private boolean meterNotWorkingId;
	
	private String meterNotWorking;
	
	private String issueIds;
	
	public DealerServiceEntriesDto() {
		super();
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public Long getDealerServiceEntriesNumber() {
		return dealerServiceEntriesNumber;
	}

	public void setDealerServiceEntriesNumber(Long dealerServiceEntriesNumber) {
		this.dealerServiceEntriesNumber = dealerServiceEntriesNumber;
	}

	public String getDealerServiceEntriesNumberStr() {
		return dealerServiceEntriesNumberStr;
	}

	public void setDealerServiceEntriesNumberStr(String dealerServiceEntriesNumberStr) {
		this.dealerServiceEntriesNumberStr = dealerServiceEntriesNumberStr;
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

	public Integer getVehicleOdometer() {
		return vehicleOdometer;
	}

	public void setVehicleOdometer(Integer vehicleOdometer) {
		this.vehicleOdometer = vehicleOdometer;
	}

	public Double getGpsOdometer() {
		return gpsOdometer;
	}

	public void setGpsOdometer(Double gpsOdometer) {
		this.gpsOdometer = gpsOdometer;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
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

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public Long getPaidById() {
		return paidById;
	}

	public void setPaidById(Long paidById) {
		this.paidById = paidById;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public String getPaidDateStr() {
		return paidDateStr;
	}

	public void setPaidDateStr(String paidDateStr) {
		this.paidDateStr = paidDateStr;
	}

	public Double getTotalInvoiceCost() {
		return totalInvoiceCost;
	}

	public void setTotalInvoiceCost(Double totalInvoiceCost) {
		this.totalInvoiceCost = Utility.round(totalInvoiceCost, 2) ;
	}

	public Double getTotalServiceCost() {
		return totalServiceCost;
	}

	public void setTotalServiceCost(Double totalServiceCost) {
		this.totalServiceCost = totalServiceCost;
	}

	public Double getTotalServiceRoundCost() {
		return totalServiceRoundCost;
	}

	public void setTotalServiceRoundCost(Double totalServiceRoundCost) {
		this.totalServiceRoundCost = totalServiceRoundCost;
	}

	public short getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(short serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public short getStatusId() {
		return statusId;
	}

	public void setStatusId(short statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getCompletedDateStr() {
		return completedDateStr;
	}

	public void setCompletedDateStr(String completedDateStr) {
		this.completedDateStr = completedDateStr;
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

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Date getVendorPaymentDate() {
		return vendorPaymentDate;
	}

	public void setVendorPaymentDate(Date vendorPaymentDate) {
		this.vendorPaymentDate = vendorPaymentDate;
	}

	public String getVendorPaymentDateStr() {
		return vendorPaymentDateStr;
	}

	public void setVendorPaymentDateStr(String vendorPaymentDateStr) {
		this.vendorPaymentDateStr = vendorPaymentDateStr;
	}

	public short getVendorPaymodeId() {
		return vendorPaymodeId;
	}

	public void setVendorPaymodeId(short vendorPaymodeId) {
		this.vendorPaymodeId = vendorPaymodeId;
	}

	public String getVendorPaymode() {
		return vendorPaymode;
	}

	public void setVendorPaymode(String vendorPaymode) {
		this.vendorPaymode = vendorPaymode;
	}

	public Long getVendorApprovalId() {
		return vendorApprovalId;
	}

	public void setVendorApprovalId(Long vendorApprovalId) {
		this.vendorApprovalId = vendorApprovalId;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(Double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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

	public Long getDealerServiceDocumentId() {
		return dealerServiceDocumentId;
	}

	public void setDealerServiceDocumentId(Long dealerServiceDocumentId) {
		this.dealerServiceDocumentId = dealerServiceDocumentId;
	}

	public String getVehicleEngineNumber() {
		return vehicleEngineNumber;
	}

	public void setVehicleEngineNumber(String vehicleEngineNumber) {
		this.vehicleEngineNumber = vehicleEngineNumber;
	}

	public String getVehicleChasisNumber() {
		return vehicleChasisNumber;
	}

	public void setVehicleChasisNumber(String vehicleChasisNumber) {
		this.vehicleChasisNumber = vehicleChasisNumber;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public String getServiceReminderIds() {
		return serviceReminderIds;
	}

	public void setServiceReminderIds(String serviceReminderIds) {
		this.serviceReminderIds = serviceReminderIds;
	}

	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}

	public String getIssueEncryptId() {
		return issueEncryptId;
	}

	public void setIssueEncryptId(String issueEncryptId) {
		this.issueEncryptId = issueEncryptId;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public long getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(long issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getIssueNumberStr() {
		return issueNumberStr;
	}

	public void setIssueNumberStr(String issueNumberStr) {
		this.issueNumberStr = issueNumberStr;
	}

	public String getIssueSummary() {
		return issueSummary;
	}

	public void setIssueSummary(String issueSummary) {
		this.issueSummary = issueSummary;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getDriverFirstName() {
		return driverFirstName;
	}

	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	public String getDriverFatherName() {
		return driverFatherName;
	}

	public void setDriverFatherName(String driverFatherName) {
		this.driverFatherName = driverFatherName;
	}

	public String getDriverFullName() {
		return driverFullName;
	}

	public void setDriverFullName(String driverFullName) {
		this.driverFullName = driverFullName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

	public boolean isPartApplicable() {
		return isPartApplicable;
	}

	public void setPartApplicable(boolean isPartApplicable) {
		this.isPartApplicable = isPartApplicable;
	}

	public String getDsePartStatus() {
		return dsePartStatus;
	}

	public void setDsePartStatus(String dsePartStatus) {
		this.dsePartStatus = dsePartStatus;
	}
	
	public String getDseExtraIssue() {
		return dseExtraIssue;
	}

	public void setDseExtraIssue(String dseExtraIssue) {
		this.dseExtraIssue = dseExtraIssue;
	}

	public Long getAssignToId() {
		return assignToId;
	}

	public void setAssignToId(Long assignToId) {
		this.assignToId = assignToId;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	

	public Long getServiceReminder() {
		return serviceReminder;
	}

	public void setServiceReminder(Long serviceReminder) {
		this.serviceReminder = serviceReminder;
	}

	public boolean isLabourApplicable() {
		return isLabourApplicable;
	}

	public void setLabourApplicable(boolean isLabourApplicable) {
		this.isLabourApplicable = isLabourApplicable;
	}

	public String getLabourStatus() {
		return labourStatus;
	}

	public void setLabourStatus(String labourStatus) {
		this.labourStatus = labourStatus;
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public String getAccident() {
		return accident;
	}

	public void setAccident(String accident) {
		this.accident = accident;
	}

	public Long getAccidentId() {
		return accidentId;
	}

	public void setAccidentId(Long accidentId) {
		this.accidentId = accidentId;
	}
	
	public boolean isMeterNotWorkingId() {
		return meterNotWorkingId;
	}

	public Double getTotalPartCost() {
		return totalPartCost;
	}

	public void setTotalPartCost(Double totalPartCost) {
		this.totalPartCost = Utility.round(totalPartCost, 2);
	}

	public Double getTotalLabourCost() {
		return totalLabourCost;
	}

	public void setTotalLabourCost(Double totalLabourCost) {
		this.totalLabourCost = Utility.round(totalLabourCost, 2);
	}

	public String getTotalPartstr() {
		return totalPartstr;
	}

	public void setTotalPartstr(String totalPartstr) {
		this.totalPartstr = totalPartstr;
	}

	public String getTotalLabourstr() {
		return totalLabourstr;
	}

	public void setTotalLabourstr(String totalLabourstr) {
		this.totalLabourstr = totalLabourstr;
	}

	public Long getDsePartId() {
		return dsePartId;
	}

	public void setDsePartId(Long dsePartId) {
		this.dsePartId = dsePartId;
	}

	public Long getDseabourId() {
		return dseabourId;
	}

	public void setDseabourId(Long dseabourId) {
		this.dseabourId = dseabourId;
	}

	public void setMeterNotWorkingId(boolean meterNotWorkingId) {
		this.meterNotWorkingId = meterNotWorkingId;
	}

	public String getMeterNotWorking() {
		return meterNotWorking;
	}

	public void setMeterNotWorking(String meterNotWorking) {
		this.meterNotWorking = meterNotWorking;
	}

	public String getIssueIds() {
		return issueIds;
	}

	public void setIssueIds(String issueIds) {
		this.issueIds = issueIds;
	}

	@Override
	public String toString() {
		return "DealerServiceEntriesDto [dealerServiceEntriesId=" + dealerServiceEntriesId
				+ ", dealerServiceEntriesNumber=" + dealerServiceEntriesNumber + ", dealerServiceEntriesNumberStr="
				+ dealerServiceEntriesNumberStr + ", vid=" + vid + ", vehicleNumber=" + vehicleNumber
				+ ", vehicleOdometer=" + vehicleOdometer + ", gpsOdometer=" + gpsOdometer + ", invoiceNumber="
				+ invoiceNumber + ", jobNumber=" + jobNumber + ", invoiceDate=" + invoiceDate + ", invoiceDateStr="
				+ invoiceDateStr + ", paymentTypeId=" + paymentTypeId + ", paymentType=" + paymentType + ", payNumber="
				+ payNumber + ", paidById=" + paidById + ", paidBy=" + paidBy + ", paidDate=" + paidDate
				+ ", paidDateStr=" + paidDateStr + ", totalInvoiceCost=" + totalInvoiceCost + ", totalServiceCost="
				+ totalServiceCost + ", totalServiceRoundCost=" + totalServiceRoundCost + ", serviceTypeId="
				+ serviceTypeId + ", serviceType=" + serviceType + ", statusId=" + statusId + ", status=" + status
				+ ", completedDate=" + completedDate + ", completedDateStr=" + completedDateStr
				+ ", expectedPaymentDate=" + expectedPaymentDate + ", expectedPaymentDateStr=" + expectedPaymentDateStr
				+ ", vendorId=" + vendorId + ", vendorName=" + vendorName + ", vendorPaymentDate=" + vendorPaymentDate
				+ ", vendorPaymentDateStr=" + vendorPaymentDateStr + ", vendorPaymodeId=" + vendorPaymodeId
				+ ", vendorPaymode=" + vendorPaymode + ", vendorApprovalId=" + vendorApprovalId + ", paidAmount="
				+ paidAmount + ", balanceAmount=" + balanceAmount + ", taxableAmount=" + taxableAmount
				+ ", discountAmount=" + discountAmount + ", createdById=" + createdById + ", createdBy=" + createdBy
				+ ", lastModifiedById=" + lastModifiedById + ", lastModifiedBy=" + lastModifiedBy + ", creationOn="
				+ creationOn + ", creationDate=" + creationDate + ", lastUpdatedOn=" + lastUpdatedOn
				+ ", lastUpdatedDate=" + lastUpdatedDate + ", companyId=" + companyId + ", markForDelete="
				+ markForDelete + ", dealerServiceDocumentId=" + dealerServiceDocumentId + ", vehicleEngineNumber="
				+ vehicleEngineNumber + ", vehicleChasisNumber=" + vehicleChasisNumber + ", vendorAddress="
				+ vendorAddress + ", serviceReminderIds=" + serviceReminderIds + ", issueId=" + issueId
				+ ", issueEncryptId=" + issueEncryptId + ", issue=" + issue + ", issueNumber=" + issueNumber
				+ ", issueNumberStr=" + issueNumberStr + ", issueSummary=" + issueSummary + ", driverId=" + driverId
				+ ", driverFirstName=" + driverFirstName + ", driverLastName=" + driverLastName + ", driverFatherName="
				+ driverFatherName + ", driverFullName=" + driverFullName + "]";
	}
	
	
	
	
	
}

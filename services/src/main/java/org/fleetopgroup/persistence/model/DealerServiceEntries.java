package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DealerServiceEntries")
public class DealerServiceEntries implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dealerServiceEntriesId")
	private Long dealerServiceEntriesId;
	
	@Column(name = "dealerServiceEntriesNumber", nullable = false)
	private Long dealerServiceEntriesNumber;

	@Column(name = "vid", length = 7)
	private Integer vid;

	@Column(name = "vehicleOdometer", length = 10)
	private Integer vehicleOdometer;
	
	@Column(name = "gpsOdometer")
	private	Double	gpsOdometer;

	@Column(name = "invoiceNumber", length = 150)
	private String invoiceNumber;

	@Column(name = "jobNumber", length = 25)
	private String jobNumber;
	
	@Column(name = "invoiceDate")
	private Date invoiceDate;

	@Column(name = "paymentTypeId", nullable = false) // cash credit neft rtgs 
	private short paymentTypeId;

	@Column(name = "totalInvoiceCost")
	private Double totalInvoiceCost;
	
	@Column(name = "totalServiceCost", length = 10)
	private Double totalServiceCost;

	@Column(name = "totalServiceRoundCost")
	private Double totalServiceRoundCost;
	
	@Column(name = "statusId", nullable = false)
	private short statusId;

	@Column(name = "completedDate")
	private Date completedDate;
	
	@Column(name = "expectedPaymentDate")
	private Date	expectedPaymentDate;
	
	@Column(name = "vendorId")
	private Integer vendorId;
	
	@Column(name = "vendorPaymentStatusId", nullable = false) // notpaid approv paid when payment type is credit
	private short vendorPaymentStatusId;
	
	@Column(name = "vendorApprovalId") // approval id
	private Long vendorApprovalId;
	
	@Column(name = "payNumber", length = 25)
	private String payNumber;

	@Column(name = "paidById")
	private Long paidById;

	@Column(name = "paidDate")
	private Date paidDate;
	
	@Column(name = "paidAmount")
	private Double paidAmount;
	
	@Column(name = "balanceAmount")
	private Double balanceAmount;
	
	@Column(name = "taxableAmount")
	private Double taxableAmount;
	
	@Column(name = "discountAmount")
	private Double discountAmount;
	
	@Column(name = "createdById", nullable = false)
	private Long createdById;

	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "creationOn")
	private Date creationOn;

	@Column(name = "lastUpdatedOn")
	private Date lastUpdatedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Basic(optional = false)
	@Column(name = "dealerServiceDocument", nullable = false)
	private boolean dealerServiceDocument = false;

	@Column(name = "dealerServiceDocumentId")
	private Long dealerServiceDocumentId;
	
	@Column(name = "serviceReminderIds")
	private String serviceReminderIds;
	
	@Column(name = "issueId")
	private Long issueId;

	@Column(name = "issueIds")
	private String issueIds;
	
	@Column(name = "driverId")
	private Integer driverId;
	
	@Column(name = "isPartApplicable", nullable = false)
	private boolean isPartApplicable;
	
	@Column(name = "isLabourApplicable", nullable = false)
	private boolean isLabourApplicable;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "assignToId")
	private Long assignToId;
	
	@Column(name = "accidentId")
	private Long accidentId;

	@Column(name = "meterNotWorkingId")
	private boolean meterNotWorkingId;
	

	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	
	
	public DealerServiceEntries() {
		super();
	}
	

	

	public DealerServiceEntries(Long dealerServiceEntriesId, Long dealerServiceEntriesNumber, Integer vid,
			Integer vehicleOdometer, Double gpsOdometer, String invoiceNumber, String jobNumber, Date invoiceDate,
			short paymentTypeId, Double totalInvoiceCost, Double totalServiceCost, Double totalServiceRoundCost,
			short statusId, Date completedDate, Date expectedPaymentDate, Integer vendorId, short vendorPaymentStatusId,
			Long vendorApprovalId, String payNumber, Long paidById, Date paidDate, Double paidAmount,
			Double balanceAmount, Double taxableAmount, Double discountAmount, Long createdById, Long lastModifiedById,
			Date creationOn, Date lastUpdatedOn, Integer companyId, boolean markForDelete,
			boolean dealerServiceDocument, Long dealerServiceDocumentId, String serviceReminderIds, Long issueId,
			Integer driverId, boolean isPartApplicable) {
		super();
		this.dealerServiceEntriesId = dealerServiceEntriesId;
		this.dealerServiceEntriesNumber = dealerServiceEntriesNumber;
		this.vid = vid;
		this.vehicleOdometer = vehicleOdometer;
		this.gpsOdometer = gpsOdometer;
		this.invoiceNumber = invoiceNumber;
		this.jobNumber = jobNumber;
		this.invoiceDate = invoiceDate;
		this.paymentTypeId = paymentTypeId;
		this.totalInvoiceCost = totalInvoiceCost;
		this.totalServiceCost = totalServiceCost;
		this.totalServiceRoundCost = totalServiceRoundCost;
		this.statusId = statusId;
		this.completedDate = completedDate;
		this.expectedPaymentDate = expectedPaymentDate;
		this.vendorId = vendorId;
		this.vendorPaymentStatusId = vendorPaymentStatusId;
		this.vendorApprovalId = vendorApprovalId;
		this.payNumber = payNumber;
		this.paidById = paidById;
		this.paidDate = paidDate;
		this.paidAmount = paidAmount;
		this.balanceAmount = balanceAmount;
		this.taxableAmount = taxableAmount;
		this.discountAmount = discountAmount;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.creationOn = creationOn;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.dealerServiceDocument = dealerServiceDocument;
		this.dealerServiceDocumentId = dealerServiceDocumentId;
		this.serviceReminderIds = serviceReminderIds;
		this.issueId = issueId;
		this.driverId = driverId;
		this.isPartApplicable = isPartApplicable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balanceAmount == null) ? 0 : balanceAmount.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((completedDate == null) ? 0 : completedDate.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((creationOn == null) ? 0 : creationOn.hashCode());
		result = prime * result + (dealerServiceDocument ? 1231 : 1237);
		result = prime * result + ((dealerServiceDocumentId == null) ? 0 : dealerServiceDocumentId.hashCode());
		result = prime * result + ((dealerServiceEntriesId == null) ? 0 : dealerServiceEntriesId.hashCode());
		result = prime * result + ((dealerServiceEntriesNumber == null) ? 0 : dealerServiceEntriesNumber.hashCode());
		result = prime * result + ((discountAmount == null) ? 0 : discountAmount.hashCode());
		result = prime * result + ((driverId == null) ? 0 : driverId.hashCode());
		result = prime * result + ((expectedPaymentDate == null) ? 0 : expectedPaymentDate.hashCode());
		result = prime * result + ((gpsOdometer == null) ? 0 : gpsOdometer.hashCode());
		result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result + ((invoiceNumber == null) ? 0 : invoiceNumber.hashCode());
		result = prime * result + (isPartApplicable ? 1231 : 1237);
		result = prime * result + ((issueId == null) ? 0 : issueId.hashCode());
		result = prime * result + ((jobNumber == null) ? 0 : jobNumber.hashCode());
		result = prime * result + ((lastModifiedById == null) ? 0 : lastModifiedById.hashCode());
		result = prime * result + ((lastUpdatedOn == null) ? 0 : lastUpdatedOn.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + ((paidAmount == null) ? 0 : paidAmount.hashCode());
		result = prime * result + ((paidById == null) ? 0 : paidById.hashCode());
		result = prime * result + ((paidDate == null) ? 0 : paidDate.hashCode());
		result = prime * result + ((payNumber == null) ? 0 : payNumber.hashCode());
		result = prime * result + paymentTypeId;
		result = prime * result + ((serviceReminderIds == null) ? 0 : serviceReminderIds.hashCode());
		result = prime * result + statusId;
		result = prime * result + ((taxableAmount == null) ? 0 : taxableAmount.hashCode());
		result = prime * result + ((totalInvoiceCost == null) ? 0 : totalInvoiceCost.hashCode());
		result = prime * result + ((totalServiceCost == null) ? 0 : totalServiceCost.hashCode());
		result = prime * result + ((totalServiceRoundCost == null) ? 0 : totalServiceRoundCost.hashCode());
		result = prime * result + ((vehicleOdometer == null) ? 0 : vehicleOdometer.hashCode());
		result = prime * result + ((vendorApprovalId == null) ? 0 : vendorApprovalId.hashCode());
		result = prime * result + ((vendorId == null) ? 0 : vendorId.hashCode());
		result = prime * result + vendorPaymentStatusId;
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
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
		DealerServiceEntries other = (DealerServiceEntries) obj;
		if (balanceAmount == null) {
			if (other.balanceAmount != null)
				return false;
		} else if (!balanceAmount.equals(other.balanceAmount))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (completedDate == null) {
			if (other.completedDate != null)
				return false;
		} else if (!completedDate.equals(other.completedDate))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (creationOn == null) {
			if (other.creationOn != null)
				return false;
		} else if (!creationOn.equals(other.creationOn))
			return false;
		if (dealerServiceDocument != other.dealerServiceDocument)
			return false;
		if (dealerServiceDocumentId == null) {
			if (other.dealerServiceDocumentId != null)
				return false;
		} else if (!dealerServiceDocumentId.equals(other.dealerServiceDocumentId))
			return false;
		if (dealerServiceEntriesId == null) {
			if (other.dealerServiceEntriesId != null)
				return false;
		} else if (!dealerServiceEntriesId.equals(other.dealerServiceEntriesId))
			return false;
		if (dealerServiceEntriesNumber == null) {
			if (other.dealerServiceEntriesNumber != null)
				return false;
		} else if (!dealerServiceEntriesNumber.equals(other.dealerServiceEntriesNumber))
			return false;
		if (discountAmount == null) {
			if (other.discountAmount != null)
				return false;
		} else if (!discountAmount.equals(other.discountAmount))
			return false;
		if (driverId == null) {
			if (other.driverId != null)
				return false;
		} else if (!driverId.equals(other.driverId))
			return false;
		if (expectedPaymentDate == null) {
			if (other.expectedPaymentDate != null)
				return false;
		} else if (!expectedPaymentDate.equals(other.expectedPaymentDate))
			return false;
		if (gpsOdometer == null) {
			if (other.gpsOdometer != null)
				return false;
		} else if (!gpsOdometer.equals(other.gpsOdometer))
			return false;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (invoiceNumber == null) {
			if (other.invoiceNumber != null)
				return false;
		} else if (!invoiceNumber.equals(other.invoiceNumber))
			return false;
		if (isPartApplicable != other.isPartApplicable)
			return false;
		if (issueId == null) {
			if (other.issueId != null)
				return false;
		} else if (!issueId.equals(other.issueId))
			return false;
		if (jobNumber == null) {
			if (other.jobNumber != null)
				return false;
		} else if (!jobNumber.equals(other.jobNumber))
			return false;
		if (lastModifiedById == null) {
			if (other.lastModifiedById != null)
				return false;
		} else if (!lastModifiedById.equals(other.lastModifiedById))
			return false;
		if (lastUpdatedOn == null) {
			if (other.lastUpdatedOn != null)
				return false;
		} else if (!lastUpdatedOn.equals(other.lastUpdatedOn))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (paidAmount == null) {
			if (other.paidAmount != null)
				return false;
		} else if (!paidAmount.equals(other.paidAmount))
			return false;
		if (paidById == null) {
			if (other.paidById != null)
				return false;
		} else if (!paidById.equals(other.paidById))
			return false;
		if (paidDate == null) {
			if (other.paidDate != null)
				return false;
		} else if (!paidDate.equals(other.paidDate))
			return false;
		if (payNumber == null) {
			if (other.payNumber != null)
				return false;
		} else if (!payNumber.equals(other.payNumber))
			return false;
		if (paymentTypeId != other.paymentTypeId)
			return false;
		if (serviceReminderIds == null) {
			if (other.serviceReminderIds != null)
				return false;
		} else if (!serviceReminderIds.equals(other.serviceReminderIds))
			return false;
		if (statusId != other.statusId)
			return false;
		if (taxableAmount == null) {
			if (other.taxableAmount != null)
				return false;
		} else if (!taxableAmount.equals(other.taxableAmount))
			return false;
		if (totalInvoiceCost == null) {
			if (other.totalInvoiceCost != null)
				return false;
		} else if (!totalInvoiceCost.equals(other.totalInvoiceCost))
			return false;
		if (totalServiceCost == null) {
			if (other.totalServiceCost != null)
				return false;
		} else if (!totalServiceCost.equals(other.totalServiceCost))
			return false;
		if (totalServiceRoundCost == null) {
			if (other.totalServiceRoundCost != null)
				return false;
		} else if (!totalServiceRoundCost.equals(other.totalServiceRoundCost))
			return false;
		if (vehicleOdometer == null) {
			if (other.vehicleOdometer != null)
				return false;
		} else if (!vehicleOdometer.equals(other.vehicleOdometer))
			return false;
		if (vendorApprovalId == null) {
			if (other.vendorApprovalId != null)
				return false;
		} else if (!vendorApprovalId.equals(other.vendorApprovalId))
			return false;
		if (vendorId == null) {
			if (other.vendorId != null)
				return false;
		} else if (!vendorId.equals(other.vendorId))
			return false;
		if (vendorPaymentStatusId != other.vendorPaymentStatusId)
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	public Long getDealerServiceEntriesId() {
		return dealerServiceEntriesId;
	}

	public Long getDealerServiceEntriesNumber() {
		return dealerServiceEntriesNumber;
	}

	public Integer getVid() {
		return vid;
	}

	public Integer getVehicleOdometer() {
		return vehicleOdometer;
	}

	public Double getGpsOdometer() {
		return gpsOdometer;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}




	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}




	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public Double getTotalInvoiceCost() {
		return totalInvoiceCost;
	}

	public Double getTotalServiceCost() {
		return totalServiceCost;
	}

	public Double getTotalServiceRoundCost() {
		return totalServiceRoundCost;
	}

	public short getStatusId() {
		return statusId;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public Date getExpectedPaymentDate() {
		return expectedPaymentDate;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public short getVendorPaymentStatusId() {
		return vendorPaymentStatusId;
	}

	public Long getVendorApprovalId() {
		return vendorApprovalId;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public Long getPaidById() {
		return paidById;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public Double getTaxableAmount() {
		return taxableAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public Date getCreationOn() {
		return creationOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public boolean isDealerServiceDocument() {
		return dealerServiceDocument;
	}

	public Long getDealerServiceDocumentId() {
		return dealerServiceDocumentId;
	}

	public String getServiceReminderIds() {
		return serviceReminderIds;
	}

	public Long getIssueId() {
		return issueId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public boolean isPartApplicable() {
		return isPartApplicable;
	}

	public void setDealerServiceEntriesId(Long dealerServiceEntriesId) {
		this.dealerServiceEntriesId = dealerServiceEntriesId;
	}

	public void setDealerServiceEntriesNumber(Long dealerServiceEntriesNumber) {
		this.dealerServiceEntriesNumber = dealerServiceEntriesNumber;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setVehicleOdometer(Integer vehicleOdometer) {
		this.vehicleOdometer = vehicleOdometer;
	}

	public void setGpsOdometer(Double gpsOdometer) {
		this.gpsOdometer = gpsOdometer;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public void setTotalInvoiceCost(Double totalInvoiceCost) {
		this.totalInvoiceCost = totalInvoiceCost;
	}

	public void setTotalServiceCost(Double totalServiceCost) {
		this.totalServiceCost = totalServiceCost;
	}

	public void setTotalServiceRoundCost(Double totalServiceRoundCost) {
		this.totalServiceRoundCost = totalServiceRoundCost;
	}

	public void setStatusId(short statusId) {
		this.statusId = statusId;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public void setExpectedPaymentDate(Date expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setVendorPaymentStatusId(short vendorPaymentStatusId) {
		this.vendorPaymentStatusId = vendorPaymentStatusId;
	}

	public void setVendorApprovalId(Long vendorApprovalId) {
		this.vendorApprovalId = vendorApprovalId;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public void setPaidById(Long paidById) {
		this.paidById = paidById;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public void setTaxableAmount(Double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setCreationOn(Date creationOn) {
		this.creationOn = creationOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setDealerServiceDocument(boolean dealerServiceDocument) {
		this.dealerServiceDocument = dealerServiceDocument;
	}

	public void setDealerServiceDocumentId(Long dealerServiceDocumentId) {
		this.dealerServiceDocumentId = dealerServiceDocumentId;
	}

	public void setServiceReminderIds(String serviceReminderIds) {
		this.serviceReminderIds = serviceReminderIds;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public void setPartApplicable(boolean isPartApplicable) {
		this.isPartApplicable = isPartApplicable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getAssignToId() {
		return assignToId;
	}

	public void setAssignToId(Long assignToId) {
		this.assignToId = assignToId;
	}

	public boolean isLabourApplicable() {
		return isLabourApplicable;
	}

	public void setLabourApplicable(boolean isLabourApplicable) {
		this.isLabourApplicable = isLabourApplicable;
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

	public void setMeterNotWorkingId(boolean meterNotWorkingId) {
		this.meterNotWorkingId = meterNotWorkingId;
	}

	public String getIssueIds() {
		return issueIds;
	}

	public void setIssueIds(String issueIds) {
		this.issueIds = issueIds;
	}

	@Override
	public String toString() {
		return "DealerServiceEntries [dealerServiceEntriesId=" + dealerServiceEntriesId
				+ ", dealerServiceEntriesNumber=" + dealerServiceEntriesNumber + ", vid=" + vid + ", vehicleOdometer="
				+ vehicleOdometer + ", gpsOdometer=" + gpsOdometer + ", invoiceNumber=" + invoiceNumber + ", jobNumber="
				+ jobNumber + ", invoiceDate=" + invoiceDate + ", paymentTypeId=" + paymentTypeId
				+ ", totalInvoiceCost=" + totalInvoiceCost + ", totalServiceCost=" + totalServiceCost
				+ ", totalServiceRoundCost=" + totalServiceRoundCost + ", statusId=" + statusId + ", completedDate="
				+ completedDate + ", expectedPaymentDate=" + expectedPaymentDate + ", vendorId=" + vendorId
				+ ", vendorPaymentStatusId=" + vendorPaymentStatusId + ", vendorApprovalId=" + vendorApprovalId
				+ ", payNumber=" + payNumber + ", paidById=" + paidById + ", paidDate=" + paidDate + ", paidAmount="
				+ paidAmount + ", balanceAmount=" + balanceAmount + ", taxableAmount=" + taxableAmount
				+ ", discountAmount=" + discountAmount + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", creationOn=" + creationOn + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + ", dealerServiceDocument=" + dealerServiceDocument
				+ ", dealerServiceDocumentId=" + dealerServiceDocumentId + ", serviceReminderIds=" + serviceReminderIds
				+ ", issueId=" + issueId + ", driverId=" + driverId + ", isPartApplicable=" + isPartApplicable + "]";
	}
	

	
	

	
}

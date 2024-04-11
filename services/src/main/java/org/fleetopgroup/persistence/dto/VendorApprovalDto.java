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

public class VendorApprovalDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long approvalId;
	private Long approvalNumber;
	private Integer approvalvendorID;
	private String approvalvendorName;
	private String approvalvendorType;
	private String approvalvendorLocation;
	private Double approvalTotal;
	private String approvalTotalStr;
	private Date approvalCreateDate;
	private String approvalCreateBy;
	private String approvalStatus;
	private String approvalPlace;
	private String approvalPaymentType;
	private String approvalPayNumber;
	private String approvalDateofpayment;
	private Date approvalDateofpaymentOn;
	private String approvalpaidby;
	private String createdBy;
	private String lastModifiedBy;
	private String created;
	private String lastupdated;
	private short approvalStatusId;
	private short approvalPlaceId;
	private Long approvalvendorTypeId;
	private short approvalPaymentTypeId;
//	private short typeOfPaymentId;
	private String typeOfPaymentStatus;
	private Double paidAmount;
	private Double discountAmount;
	private Long approvalpaidbyId;
	private short vendorPaymentStatus;
	private String expectedPaymentDate;
	private String approvalNumbers;
	public String approvalCreateDateStr;
	private Long approvalCreateById;
	private Long createdById;
	private Long lastModifiedById;
	private Date createdOn;
	private Date lastupdatedOn;
	private short approvedPaymentStatusId;
	private String approvedPaymentStatusStr;
	private Double totalApprovalPaidAmount;
	private Long	tallyCompanyId;
	private Double subApprovalTotal;
	private String subApprovalTotalStr;
	private Double subApprovalpaidAmount;
	private Double	approvalPaidTotal;
	private Double VendorTDSPercent;
	private Double  TDSAmount;
	private Double  PayableAmount;
	
	public Double getApprovalPaidTotal() {
		return approvalPaidTotal;
	}

	public void setApprovalPaidTotal(Double approvalPaidTotal) {
		this.approvalPaidTotal = Utility.round( approvalPaidTotal, 2);
	}

	/**
	 * @return the approvalId
	 */
	public Long getApprovalId() {
		return approvalId;
	}

	/**
	 * @param approvalId
	 *            the approvalId to set
	 */
	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public Long getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(Long approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	/**
	 * @return the approvalvendorID
	 */
	public Integer getApprovalvendorID() {
		return approvalvendorID;
	}

	/**
	 * @param approvalvendorID
	 *            the approvalvendorID to set
	 */
	public void setApprovalvendorID(Integer approvalvendorID) {
		this.approvalvendorID = approvalvendorID;
	}

	/**
	 * @return the approvalvendorName
	 */
	public String getApprovalvendorName() {
		return approvalvendorName;
	}

	/**
	 * @param approvalvendorName
	 *            the approvalvendorName to set
	 */
	public void setApprovalvendorName(String approvalvendorName) {
		this.approvalvendorName = approvalvendorName;
	}

	/**
	 * @return the approvalvendorType
	 */
	public String getApprovalvendorType() {
		return approvalvendorType;
	}

	/**
	 * @param approvalvendorType
	 *            the approvalvendorType to set
	 */
	public void setApprovalvendorType(String approvalvendorType) {
		this.approvalvendorType = approvalvendorType;
	}

	/**
	 * @return the approvalvendorLocation
	 */
	public String getApprovalvendorLocation() {
		return approvalvendorLocation;
	}

	/**
	 * @param approvalvendorLocation
	 *            the approvalvendorLocation to set
	 */
	public void setApprovalvendorLocation(String approvalvendorLocation) {
		this.approvalvendorLocation = approvalvendorLocation;
	}

	/**
	 * @return the approvalTotal
	 */
	public Double getApprovalTotal() {
		return approvalTotal;
	}

	/**
	 * @param approvalTotal
	 *            the approvalTotal to set
	 */
	public void setApprovalTotal(Double approvalTotal) {
		this.approvalTotal = Utility.round(approvalTotal, 2);
	}

	/**
	 * @return the approvalCreateDate
	 */
	public Date getApprovalCreateDate() {
		return approvalCreateDate;
	}

	/**
	 * @param approvalCreateDate
	 *            the approvalCreateDate to set
	 */
	public void setApprovalCreateDate(Date approvalCreateDate) {
		this.approvalCreateDate = approvalCreateDate;
	}

	/**
	 * @return the approvalCreateBy
	 */
	public String getApprovalCreateBy() {
		return approvalCreateBy;
	}

	/**
	 * @param approvalCreateBy
	 *            the approvalCreateBy to set
	 */
	public void setApprovalCreateBy(String approvalCreateBy) {
		this.approvalCreateBy = approvalCreateBy;
	}

	/**
	 * @return the approvalStatus
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param approvalStatus
	 *            the approvalStatus to set
	 */
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/**
	 * @return the approvalPaymentType
	 */
	public String getApprovalPaymentType() {
		return approvalPaymentType;
	}

	/**
	 * @param approvalPaymentType
	 *            the approvalPaymentType to set
	 */
	public void setApprovalPaymentType(String approvalPaymentType) {
		this.approvalPaymentType = approvalPaymentType;
	}

	/**
	 * @return the approvalPayNumber
	 */
	public String getApprovalPayNumber() {
		return approvalPayNumber;
	}

	/**
	 * @param approvalPayNumber
	 *            the approvalPayNumber to set
	 */
	public void setApprovalPayNumber(String approvalPayNumber) {
		this.approvalPayNumber = approvalPayNumber;
	}

	/**
	 * @return the approvalDateofpayment
	 */
	public String getApprovalDateofpayment() {
		return approvalDateofpayment;
	}

	/**
	 * @param approvalDateofpayment
	 *            the approvalDateofpayment to set
	 */
	public void setApprovalDateofpayment(String approvalDateofpayment) {
		this.approvalDateofpayment = approvalDateofpayment;
	}

	/**
	 * @return the approvalpaidby
	 */
	public String getApprovalpaidby() {
		return approvalpaidby;
	}

	/**
	 * @param approvalpaidby
	 *            the approvalpaidby to set
	 */
	public void setApprovalpaidby(String approvalpaidby) {
		this.approvalpaidby = approvalpaidby;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	public Double getTotalApprovalPaidAmount() {
		return totalApprovalPaidAmount;
	}

	public void setTotalApprovalPaidAmount(Double totalApprovalPaidAmount) {
		this.totalApprovalPaidAmount = Utility.round( totalApprovalPaidAmount, 2);
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getApprovalPlace() {
		return approvalPlace;
	}

	public void setApprovalPlace(String approvalPlace) {
		this.approvalPlace = approvalPlace;
	}

	/**
	 * @return the approvalStatusId
	 */
	public short getApprovalStatusId() {
		return approvalStatusId;
	}

	/**
	 * @param approvalStatusId the approvalStatusId to set
	 */
	public void setApprovalStatusId(short approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	/**
	 * @return the approvalPlaceId
	 */
	public short getApprovalPlaceId() {
		return approvalPlaceId;
	}

	/**
	 * @param approvalPlaceId the approvalPlaceId to set
	 */
	public void setApprovalPlaceId(short approvalPlaceId) {
		this.approvalPlaceId = approvalPlaceId;
	}

	/**
	 * @return the approvalvendorTypeId
	 */
	public Long getApprovalvendorTypeId() {
		return approvalvendorTypeId;
	}

	/**
	 * @param approvalvendorTypeId the approvalvendorTypeId to set
	 */
	public void setApprovalvendorTypeId(Long approvalvendorTypeId) {
		this.approvalvendorTypeId = approvalvendorTypeId;
	}

	/**
	 * @return the approvalPaymentTypeId
	 */
	public short getApprovalPaymentTypeId() {
		return approvalPaymentTypeId;
	}

	/**
	 * @param approvalPaymentTypeId the approvalPaymentTypeId to set
	 */
	public void setApprovalPaymentTypeId(short approvalPaymentTypeId) {
		this.approvalPaymentTypeId = approvalPaymentTypeId;
	}

	/**
	 * @return the approvalpaidbyId
	 */
	public Long getApprovalpaidbyId() {
		return approvalpaidbyId;
	}

	/**
	 * @param approvalpaidbyId the approvalpaidbyId to set
	 */
	public void setApprovalpaidbyId(Long approvalpaidbyId) {
		this.approvalpaidbyId = approvalpaidbyId;
	}

	/**
	 * @return the approvalCreateById
	 */
	public Long getApprovalCreateById() {
		return approvalCreateById;
	}

	/**
	 * @param approvalCreateById the approvalCreateById to set
	 */
	public void setApprovalCreateById(Long approvalCreateById) {
		this.approvalCreateById = approvalCreateById;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the lastupdatedOn
	 */
	public Date getLastupdatedOn() {
		return lastupdatedOn;
	}

	/**
	 * @param lastupdatedOn the lastupdatedOn to set
	 */
	public void setLastupdatedOn(Date lastupdatedOn) {
		this.lastupdatedOn = lastupdatedOn;
	}

	/**
	 * @return the approvalDateofpaymentOn
	 */
	public Date getApprovalDateofpaymentOn() {
		return approvalDateofpaymentOn;
	}

	/**
	 * @param approvalDateofpaymentOn the approvalDateofpaymentOn to set
	 */
	public void setApprovalDateofpaymentOn(Date approvalDateofpaymentOn) {
		this.approvalDateofpaymentOn = approvalDateofpaymentOn;
	}

	
	public String getApprovalCreateDateStr() {
		return approvalCreateDateStr;
	}

	public void setApprovalCreateDateStr(String approvalCreateDateStr) {
		this.approvalCreateDateStr = approvalCreateDateStr;
	}
	
	

	public String getApprovalNumbers() {
		return approvalNumbers;
	}

	public void setApprovalNumbers(String approvalNumbers) {
		this.approvalNumbers = approvalNumbers;
	}


	public String getTypeOfPaymentStatus() {
		return typeOfPaymentStatus;
	}

	public void setTypeOfPaymentStatus(String typeOfPaymentStatus) {
		this.typeOfPaymentStatus = typeOfPaymentStatus;
	}

	public short getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	public void setVendorPaymentStatus(short vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = Utility.round(discountAmount, 2);
	}

	public String getExpectedPaymentDate() {
		return expectedPaymentDate;
	}

	public void setExpectedPaymentDate(String expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}
	
	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = Utility.round(paidAmount, 2);
	}
	
	public String getApprovalTotalStr() {
		return approvalTotalStr;
	}

	public void setApprovalTotalStr(String approvalTotalStr) {
		this.approvalTotalStr = approvalTotalStr;
	}

	public short getApprovedPaymentStatusId() {
		return approvedPaymentStatusId;
	}

	public void setApprovedPaymentStatusId(short approvedPaymentStatusId) {
		this.approvedPaymentStatusId = approvedPaymentStatusId;
	}


	public String getApprovedPaymentStatusStr() {
		return approvedPaymentStatusStr;
	}

	public void setApprovedPaymentStatusStr(String approvedPaymentStatusStr) {
		this.approvedPaymentStatusStr = approvedPaymentStatusStr;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public Double getSubApprovalTotal() {
		return subApprovalTotal;
	}

	public void setSubApprovalTotal(Double subApprovalTotal) {
		this.subApprovalTotal = Utility.round( subApprovalTotal, 2);
	}

	public Double getSubApprovalpaidAmount() {
		return subApprovalpaidAmount;
	}

	public void setSubApprovalpaidAmount(Double subApprovalpaidAmount) {
		this.subApprovalpaidAmount = Utility.round(subApprovalpaidAmount, 2);
	}

	public String getSubApprovalTotalStr() {
		return subApprovalTotalStr;
	}

	public void setSubApprovalTotalStr(String subApprovalTotalStr) {
		this.subApprovalTotalStr = subApprovalTotalStr;
	}
	
	public Double getVendorTDSPercent() {
		return VendorTDSPercent;
	}

	public void setVendorTDSPercent(Double vendorTDSPercent) {
		VendorTDSPercent = vendorTDSPercent;
	}

	public Double getTDSAmount() {
		return TDSAmount;
	}

	public void setTDSAmount(Double tDSAmount) {
		TDSAmount = tDSAmount;
	}

	public Double getPayableAmount() {
		return PayableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		PayableAmount = payableAmount;
	}

	@Override
	public String toString() {
		return "VendorApprovalDto [approvalId=" + approvalId + ", approvalNumber=" + approvalNumber
				+ ", approvalvendorID=" + approvalvendorID + ", approvalvendorName=" + approvalvendorName
				+ ", approvalvendorType=" + approvalvendorType + ", approvalvendorLocation=" + approvalvendorLocation
				+ ", approvalTotal=" + approvalTotal + ", approvalTotalStr=" + approvalTotalStr
				+ ", approvalCreateDate=" + approvalCreateDate + ", approvalCreateBy=" + approvalCreateBy
				+ ", approvalStatus=" + approvalStatus + ", approvalPlace=" + approvalPlace + ", approvalPaymentType="
				+ approvalPaymentType + ", approvalPayNumber=" + approvalPayNumber + ", approvalDateofpayment="
				+ approvalDateofpayment + ", approvalDateofpaymentOn=" + approvalDateofpaymentOn + ", approvalpaidby="
				+ approvalpaidby + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy + ", created="
				+ created + ", lastupdated=" + lastupdated + ", approvalStatusId=" + approvalStatusId
				+ ", approvalPlaceId=" + approvalPlaceId + ", approvalvendorTypeId=" + approvalvendorTypeId
				+ ", approvalPaymentTypeId=" + approvalPaymentTypeId + ", typeOfPaymentStatus=" + typeOfPaymentStatus
				+ ", paidAmount=" + paidAmount + ", discountAmount=" + discountAmount + ", approvalpaidbyId="
				+ approvalpaidbyId + ", vendorPaymentStatus=" + vendorPaymentStatus + ", expectedPaymentDate="
				+ expectedPaymentDate + ", approvalNumbers=" + approvalNumbers + ", approvalCreateDateStr="
				+ approvalCreateDateStr + ", approvalCreateById=" + approvalCreateById + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", createdOn=" + createdOn + ", lastupdatedOn="
				+ lastupdatedOn + ", approvedPaymentStatusId=" + approvedPaymentStatusId + ", approvedPaymentStatusStr="
				+ approvedPaymentStatusStr + ", totalApprovalPaidAmount=" + totalApprovalPaidAmount
				+ ", tallyCompanyId=" + tallyCompanyId + ", subApprovalTotal=" + subApprovalTotal
				+ ", subApprovalTotalStr=" + subApprovalTotalStr + ", subApprovalpaidAmount=" + subApprovalpaidAmount
				+ ", approvalPaidTotal=" + approvalPaidTotal + ", VendorTDSPercent=" + VendorTDSPercent + ", TDSAmount="
				+ TDSAmount + ", PayableAmount=" + PayableAmount + "]";
	}

	
	
	
}

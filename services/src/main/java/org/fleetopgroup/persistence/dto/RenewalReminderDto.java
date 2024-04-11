package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 *
 *
 */

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class RenewalReminderDto {
	private Long renewal_id;
	private Integer vid;
	private String vehicle_registration;
	private String renewal_type;
	private String renewal_subtype;
	private String renewal_from;
	private String renewal_to;
	private Date renewal_D_from;
	private Date renewal_D_to;
	private Integer renewal_periedthreshold;
	private Integer renewal_timethreshold;
	private String renewal_dateofRenewal;
	private String renewal_receipt;
	private Double renewal_Amount;
	private String renewal_dateofpayment;
	private Date renewal_payment_Date;
	private String renewal_paidby;
	private String renewal_paymentType;
	private String renewal_PayNumber;
	private String renewal_authorization;

	private String renewal_number;
	private boolean renewal_document;
	private boolean ignored;
	private boolean allowToIgnored;
	private String ignoredRemark;
	
	private short renewal_staus_id;

	/** The value for the Document available field */
	private Long renewal_document_id;

	private String renewal_status;
	private String renewal_approvedby;
	private String renewal_approvedComment;
	/** The value for the renewal_approvedID available field */
	private Long renewal_approvedID;

	private Date renewal_approveddate;
	private String renewalApproveddate;
	private Date dateofRenewal;

	/* Show Date field */
	private String renewal_vehicleShow;
	private String renewal_dayofRenewal;
	private String renewal_dueEndDate;
	private String renewal_dueRemDate;
	private String renewal_dueEndCalendar;
	private String renewal_dueDifference;

	private String createdBy;
	private String created;
	private Date createdOn;
	private Date lastupdatedOn;
	private String vehicleGroup;
	
	private Long renewal_paidbyId;
	private Long renewal_approvedbyId;
	private Long createdById;
	private Long lastModifiedById;
	private boolean		newRRCreated;
	private Integer	vendorId;
	private String vendorName;
	
	private int renewalPrdThreshold;
	private String renewalPrdThresholdStr;
	private String		renewalBase64Document;
	private long countOfSROnEachVehicle;
	private Long	tallyCompanyId;
	private String	tallyCompanyName;
	private String		fileExtension;
	private String vehicleStatus;
	private Long renewalCount;
	private short vehicleStatusId;
	private Long userId;
	private String userName;
	private String branchName;
	private String vehicleType;
	private String renewalDueStatus;
	private Long  count;
	
	
	public String getRenewalApproveddate() {
		return renewalApproveddate;
	}

	public void setRenewalApproveddate(String renewalApproveddate) {
		this.renewalApproveddate = renewalApproveddate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public RenewalReminderDto(int renewalPrdThreshold, String renewalPrdThresholdStr) {
		super();
		this.renewalPrdThreshold = renewalPrdThreshold;
		this.renewalPrdThresholdStr = renewalPrdThresholdStr;
	}

	public RenewalReminderDto() {
		super();
	}


	boolean markForDelete;
	public Date getRenewal_D_from() {
		return renewal_D_from;
	}

	public void setRenewal_D_from(Date renewal_D_from) {
		this.renewal_D_from = renewal_D_from;
	}

	public Date getRenewal_D_to() {
		return renewal_D_to;
	}

	public void setRenewal_D_to(Date renewal_D_to) {
		this.renewal_D_to = renewal_D_to;
	}

	private String lastModifiedBy;
	private String lastupdated;
	private Long renewal_R_Number;
	private Long renewalAproval_Number;
	private Date renewal_from_Date;
	private Date renewal_To_Date;
	
	private short 	paymentTypeId;
	private Integer renewal_Subid;
	private Integer renewalTypeId;
	private Integer companyId;
	
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the renewal_id
	 */
	public Long getRenewal_id() {
		return renewal_id;
	}

	/**
	 * @param renewal_id
	 *            the renewal_id to set
	 */
	public void setRenewal_id(Long renewal_id) {
		this.renewal_id = renewal_id;
	}

	/**
	 * @return the vid
	 */
	public Integer getVid() {
		return vid;
	}

	/**
	 * @param vid
	 *            the vid to set
	 */
	public void setVid(Integer vid) {
		this.vid = vid;
	}

	/**
	 * @return the vehicle_registration
	 */
	public String getVehicle_registration() {
		return vehicle_registration;
	}

	/**
	 * @param vehicle_registration
	 *            the vehicle_registration to set
	 */
	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	/**
	 * @return the renewal_type
	 */
	public String getRenewal_type() {
		return renewal_type;
	}

	/**
	 * @param renewal_type
	 *            the renewal_type to set
	 */
	public void setRenewal_type(String renewal_type) {
		this.renewal_type = renewal_type;
	}

	/**
	 * @return the renewal_subtype
	 */
	public String getRenewal_subtype() {
		return renewal_subtype;
	}

	/**
	 * @param renewal_subtype
	 *            the renewal_subtype to set
	 */
	public void setRenewal_subtype(String renewal_subtype) {
		this.renewal_subtype = renewal_subtype;
	}

	/**
	 * @return the renewal_from
	 */
	public String getRenewal_from() {
		return renewal_from;
	}

	/**
	 * @param renewal_from
	 *            the renewal_from to set
	 */
	public void setRenewal_from(String renewal_from) {
		this.renewal_from = renewal_from;
	}

	/**
	 * @return the renewal_to
	 */
	public String getRenewal_to() {
		return renewal_to;
	}

	/**
	 * @param renewal_to
	 *            the renewal_to to set
	 */
	public void setRenewal_to(String renewal_to) {
		this.renewal_to = renewal_to;
	}

	/**
	 * @return the renewal_periedthreshold
	 */
	public Integer getRenewal_periedthreshold() {
		return renewal_periedthreshold;
	}

	/**
	 * @param renewal_periedthreshold
	 *            the renewal_periedthreshold to set
	 */
	public void setRenewal_periedthreshold(Integer renewal_periedthreshold) {
		this.renewal_periedthreshold = renewal_periedthreshold;
	}

	/**
	 * @return the renewal_timethreshold
	 */
	public Integer getRenewal_timethreshold() {
		return renewal_timethreshold;
	}

	/**
	 * @param renewal_timethreshold
	 *            the renewal_timethreshold to set
	 */
	public void setRenewal_timethreshold(Integer renewal_timethreshold) {
		this.renewal_timethreshold = renewal_timethreshold;
	}

	/**
	 * @return the renewal_dateofRenewal
	 */
	public String getRenewal_dateofRenewal() {
		return renewal_dateofRenewal;
	}

	/**
	 * @param renewal_dateofRenewal
	 *            the renewal_dateofRenewal to set
	 */
	public void setRenewal_dateofRenewal(String renewal_dateofRenewal) {
		this.renewal_dateofRenewal = renewal_dateofRenewal;
	}

	/**
	 * @return the renewal_receipt
	 */
	public String getRenewal_receipt() {
		return renewal_receipt;
	}

	/**
	 * @param renewal_receipt
	 *            the renewal_receipt to set
	 */
	public void setRenewal_receipt(String renewal_receipt) {
		this.renewal_receipt = renewal_receipt;
	}

	/**
	 * @return the renewal_Amount
	 */
	public Double getRenewal_Amount() {
		return renewal_Amount;
	}

	/**
	 * @param renewal_Amount
	 *            the renewal_Amount to set
	 */
	public void setRenewal_Amount(Double renewal_Amount) {
		this.renewal_Amount = Utility.round(renewal_Amount, 2) ;
	}

	/**
	 * @return the renewal_dateofpayment
	 */
	public String getRenewal_dateofpayment() {
		return renewal_dateofpayment;
	}

	/**
	 * @param renewal_dateofpayment
	 *            the renewal_dateofpayment to set
	 */
	public void setRenewal_dateofpayment(String renewal_dateofpayment) {
		this.renewal_dateofpayment = renewal_dateofpayment;
	}

	/**
	 * @return the renewal_paidby
	 */
	public String getRenewal_paidby() {
		return renewal_paidby;
	}

	/**
	 * @param renewal_paidby
	 *            the renewal_paidby to set
	 */
	public void setRenewal_paidby(String renewal_paidby) {
		this.renewal_paidby = renewal_paidby;
	}

	/**
	 * @return the renewal_paymentType
	 */
	public String getRenewal_paymentType() {
		return renewal_paymentType;
	}

	/**
	 * @param renewal_paymentType
	 *            the renewal_paymentType to set
	 */
	public void setRenewal_paymentType(String renewal_paymentType) {
		this.renewal_paymentType = renewal_paymentType;
	}

	/**
	 * @return the renewal_PayNumber
	 */
	public String getRenewal_PayNumber() {
		return renewal_PayNumber;
	}

	/**
	 * @param renewal_PayNumber
	 *            the renewal_PayNumber to set
	 */
	public void setRenewal_PayNumber(String renewal_PayNumber) {
		this.renewal_PayNumber = renewal_PayNumber;
	}

	/**
	 * @return the renewal_authorization
	 */
	public String getRenewal_authorization() {
		return renewal_authorization;
	}

	/**
	 * @param renewal_authorization
	 *            the renewal_authorization to set
	 */
	public void setRenewal_authorization(String renewal_authorization) {
		this.renewal_authorization = renewal_authorization;
	}

	public boolean isNewRRCreated() {
		return newRRCreated;
	}

	public void setNewRRCreated(boolean newRRCreated) {
		this.newRRCreated = newRRCreated;
	}

	/**
	 * @return the renewal_number
	 */
	public String getRenewal_number() {
		return renewal_number;
	}

	/**
	 * @param renewal_number
	 *            the renewal_number to set
	 */
	public void setRenewal_number(String renewal_number) {
		this.renewal_number = renewal_number;
	}

	/**
	 * @return the renewal_document
	 */
	public boolean getRenewal_document() {
		return renewal_document;
	}

	/**
	 * @param renewal_document
	 *            the renewal_document to set
	 */
	public void setRenewal_document(boolean renewal_document) {
		this.renewal_document = renewal_document;
	}

	/**
	 * @return the renewal_dueEndDate
	 */
	public String getRenewal_dueEndDate() {
		return renewal_dueEndDate;
	}

	/**
	 * @param renewal_dueEndDate
	 *            the renewal_dueEndDate to set
	 */
	public void setRenewal_dueEndDate(String renewal_dueEndDate) {
		this.renewal_dueEndDate = renewal_dueEndDate;
	}

	/**
	 * @return the renewal_dueRemDate
	 */
	public String getRenewal_dueRemDate() {
		return renewal_dueRemDate;
	}

	/**
	 * @param renewal_dueRemDate
	 *            the renewal_dueRemDate to set
	 */
	public void setRenewal_dueRemDate(String renewal_dueRemDate) {
		this.renewal_dueRemDate = renewal_dueRemDate;
	}

	/**
	 * @return the renewal_dueEndCalendar
	 */
	public String getRenewal_dueEndCalendar() {
		return renewal_dueEndCalendar;
	}

	/**
	 * @param renewal_dueEndCalendar
	 *            the renewal_dueEndCalendar to set
	 */
	public void setRenewal_dueEndCalendar(String renewal_dueEndCalendar) {
		this.renewal_dueEndCalendar = renewal_dueEndCalendar;
	}

	/**
	 * @return the renewal_dueDifference
	 */
	public String getRenewal_dueDifference() {
		return renewal_dueDifference;
	}

	/**
	 * @param renewal_dueDifference
	 *            the renewal_dueDifference to set
	 */
	public void setRenewal_dueDifference(String renewal_dueDifference) {
		this.renewal_dueDifference = renewal_dueDifference;
	}

	/**
	 * @return the renewal_dayofRenewal
	 */
	public String getRenewal_dayofRenewal() {
		return renewal_dayofRenewal;
	}

	/**
	 * @param renewal_dayofRenewal
	 *            the renewal_dayofRenewal to set
	 */
	public void setRenewal_dayofRenewal(String renewal_dayofRenewal) {
		this.renewal_dayofRenewal = renewal_dayofRenewal;
	}

	/**
	 * @return the renewal_vehicleShow
	 */
	public String getRenewal_vehicleShow() {
		return renewal_vehicleShow;
	}

	/**
	 * @param renewal_vehicleShow
	 *            the renewal_vehicleShow to set
	 */
	public void setRenewal_vehicleShow(String renewal_vehicleShow) {
		this.renewal_vehicleShow = renewal_vehicleShow;
	}

	public Date getRenewal_payment_Date() {
		return renewal_payment_Date;
	}

	public void setRenewal_payment_Date(Date renewal_payment_Date) {
		this.renewal_payment_Date = renewal_payment_Date;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
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
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	/**
	 * @return the renewal_status
	 */
	public String getRenewal_status() {
		return renewal_status;
	}

	/**
	 * @param renewal_status
	 *            the renewal_status to set
	 */
	public void setRenewal_status(String renewal_status) {
		this.renewal_status = renewal_status;
	}

	/**
	 * @return the renewal_approvedID
	 */
	public Long getRenewal_approvedID() {
		return renewal_approvedID;
	}

	/**
	 * @param renewal_approvedID
	 *            the renewal_approvedID to set
	 */
	public void setRenewal_approvedID(Long renewal_approvedID) {
		this.renewal_approvedID = renewal_approvedID;
	}

	/**
	 * @return the renewal_approvedby
	 */
	public String getRenewal_approvedby() {
		return renewal_approvedby;
	}

	/**
	 * @param renewal_approvedby
	 *            the renewal_approvedby to set
	 */
	public void setRenewal_approvedby(String renewal_approvedby) {
		this.renewal_approvedby = renewal_approvedby;
	}

	/**
	 * @return the renewal_approvedComment
	 */
	public String getRenewal_approvedComment() {
		return renewal_approvedComment;
	}

	/**
	 * @param renewal_approvedComment
	 *            the renewal_approvedComment to set
	 */
	public void setRenewal_approvedComment(String renewal_approvedComment) {
		this.renewal_approvedComment = renewal_approvedComment;
	}

	/**
	 * @return the renewal_approveddate
	 */
	public Date getRenewal_approveddate() {
		return renewal_approveddate;
	}

	/**
	 * @param renewal_approveddate
	 *            the renewal_approveddate to set
	 */
	public void setRenewal_approveddate(Date renewal_approveddate) {
		this.renewal_approveddate = renewal_approveddate;
	}

	/**
	 * @return the renewal_document_id
	 */
	public Long getRenewal_document_id() {
		return renewal_document_id;
	}

	/**
	 * @param renewal_document_id
	 *            the renewal_document_id to set
	 */
	public void setRenewal_document_id(Long renewal_document_id) {
		this.renewal_document_id = renewal_document_id;
	}

	public Long getRenewal_R_Number() {
		return renewal_R_Number;
	}

	public void setRenewal_R_Number(Long renewal_R_Number) {
		this.renewal_R_Number = renewal_R_Number;
	}

	public Long getRenewalAproval_Number() {
		return renewalAproval_Number;
	}

	public void setRenewalAproval_Number(Long renewalAproval_Number) {
		this.renewalAproval_Number = renewalAproval_Number;
	}

	public Date getRenewal_from_Date() {
		return renewal_from_Date;
	}

	public void setRenewal_from_Date(Date renewal_from_Date) {
		this.renewal_from_Date = renewal_from_Date;
	}

	public Date getRenewal_To_Date() {
		return renewal_To_Date;
	}

	public void setRenewal_To_Date(Date renewal_To_Date) {
		this.renewal_To_Date = renewal_To_Date;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Integer getRenewal_Subid() {
		return renewal_Subid;
	}

	public void setRenewal_Subid(Integer renewal_Subid) {
		this.renewal_Subid = renewal_Subid;
	}

	public Integer getRenewalTypeId() {
		return renewalTypeId;
	}

	public void setRenewalTypeId(Integer renewalTypeId) {
		this.renewalTypeId = renewalTypeId;
	}

	public short getRenewal_staus_id() {
		return renewal_staus_id;
	}

	public void setRenewal_staus_id(short renewal_staus_id) {
		this.renewal_staus_id = renewal_staus_id;
	}

	public Date getLastupdatedOn() {
		return lastupdatedOn;
	}

	public void setLastupdatedOn(Date lastupdatedOn) {
		this.lastupdatedOn = lastupdatedOn;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getVehicleGroup() {
		return vehicleGroup;
	}

	public void setVehicleGroup(String vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
	}

	/**
	 * @return the renewal_paidbyId
	 */
	public Long getRenewal_paidbyId() {
		return renewal_paidbyId;
	}

	/**
	 * @param renewal_paidbyId the renewal_paidbyId to set
	 */
	public void setRenewal_paidbyId(Long renewal_paidbyId) {
		this.renewal_paidbyId = renewal_paidbyId;
	}

	/**
	 * @return the renewal_approvedbyId
	 */
	public Long getRenewal_approvedbyId() {
		return renewal_approvedbyId;
	}

	/**
	 * @param renewal_approvedbyId the renewal_approvedbyId to set
	 */
	public void setRenewal_approvedbyId(Long renewal_approvedbyId) {
		this.renewal_approvedbyId = renewal_approvedbyId;
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

	public Date getDateofRenewal() {
		return dateofRenewal;
	}

	public void setDateofRenewal(Date dateofRenewal) {
		this.dateofRenewal = dateofRenewal;
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

	public int getRenewalPrdThreshold() {
		return renewalPrdThreshold;
	}

	public void setRenewalPrdThreshold(int renewalPrdThreshold) {
		this.renewalPrdThreshold = renewalPrdThreshold;
	}

	public String getRenewalPrdThresholdStr() {
		return renewalPrdThresholdStr;
	}

	public void setRenewalPrdThresholdStr(String renewalPrdThresholdStr) {
		this.renewalPrdThresholdStr = renewalPrdThresholdStr;
	}

	public String getRenewalBase64Document() {
		return renewalBase64Document;
	}

	public void setRenewalBase64Document(String renewalBase64Document) {
		this.renewalBase64Document = renewalBase64Document;
	}
	
	
	public long getCountOfSROnEachVehicle() {
		return countOfSROnEachVehicle;
	}

	public void setCountOfSROnEachVehicle(long countOfSROnEachVehicle) {
		this.countOfSROnEachVehicle = countOfSROnEachVehicle;
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
	
	public String getFileExtension() {
		return fileExtension;
	}


	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public Long getRenewalCount() {
		return renewalCount;
	}

	public void setRenewalCount(Long renewalCount) {
		this.renewalCount = renewalCount;
	}

	public short getVehicleStatusId() {
		return vehicleStatusId;
	}

	public void setVehicleStatusId(short vehicleStatusId) {
		this.vehicleStatusId = vehicleStatusId;
	}

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public boolean isIgnored() {
		return ignored;
	}

	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}

	public String getIgnoredRemark() {
		return ignoredRemark;
	}

	public void setIgnoredRemark(String ignoredRemark) {
		this.ignoredRemark = ignoredRemark;
	}

	public boolean isAllowToIgnored() {
		return allowToIgnored;
	}

	public void setAllowToIgnored(boolean allowToIgnored) {
		this.allowToIgnored = allowToIgnored;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getRenewalDueStatus() {
		return renewalDueStatus;
	}

	public void setRenewalDueStatus(String renewalDueStatus) {
		this.renewalDueStatus = renewalDueStatus;
	}
	
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "RenewalReminderDto [renewal_id=" + renewal_id + ", vid=" + vid + ", vehicle_registration="
				+ vehicle_registration + ", renewal_type=" + renewal_type + ", renewal_subtype=" + renewal_subtype
				+ ", renewal_from=" + renewal_from + ", renewal_to=" + renewal_to + ", renewal_D_from=" + renewal_D_from
				+ ", renewal_D_to=" + renewal_D_to + ", renewal_periedthreshold=" + renewal_periedthreshold
				+ ", renewal_timethreshold=" + renewal_timethreshold + ", renewal_dateofRenewal="
				+ renewal_dateofRenewal + ", renewal_receipt=" + renewal_receipt + ", renewal_Amount=" + renewal_Amount
				+ ", renewal_dateofpayment=" + renewal_dateofpayment + ", renewal_payment_Date=" + renewal_payment_Date
				+ ", renewal_paidby=" + renewal_paidby + ", renewal_paymentType=" + renewal_paymentType
				+ ", renewal_PayNumber=" + renewal_PayNumber + ", renewal_authorization=" + renewal_authorization
				+ ", renewal_number=" + renewal_number + ", renewal_document=" + renewal_document + ", ignored="
				+ ignored + ", allowToIgnored=" + allowToIgnored + ", ignoredRemark=" + ignoredRemark
				+ ", renewal_staus_id=" + renewal_staus_id + ", renewal_document_id=" + renewal_document_id
				+ ", renewal_status=" + renewal_status + ", renewal_approvedby=" + renewal_approvedby
				+ ", renewal_approvedComment=" + renewal_approvedComment + ", renewal_approvedID=" + renewal_approvedID
				+ ", renewal_approveddate=" + renewal_approveddate + ", renewalApproveddate=" + renewalApproveddate
				+ ", dateofRenewal=" + dateofRenewal + ", renewal_vehicleShow=" + renewal_vehicleShow
				+ ", renewal_dayofRenewal=" + renewal_dayofRenewal + ", renewal_dueEndDate=" + renewal_dueEndDate
				+ ", renewal_dueRemDate=" + renewal_dueRemDate + ", renewal_dueEndCalendar=" + renewal_dueEndCalendar
				+ ", renewal_dueDifference=" + renewal_dueDifference + ", createdBy=" + createdBy + ", created="
				+ created + ", createdOn=" + createdOn + ", lastupdatedOn=" + lastupdatedOn + ", vehicleGroup="
				+ vehicleGroup + ", renewal_paidbyId=" + renewal_paidbyId + ", renewal_approvedbyId="
				+ renewal_approvedbyId + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", newRRCreated=" + newRRCreated + ", vendorId=" + vendorId + ", vendorName=" + vendorName
				+ ", renewalPrdThreshold=" + renewalPrdThreshold + ", renewalPrdThresholdStr=" + renewalPrdThresholdStr
				+ ", renewalBase64Document=" + renewalBase64Document + ", countOfSROnEachVehicle="
				+ countOfSROnEachVehicle + ", tallyCompanyId=" + tallyCompanyId + ", tallyCompanyName="
				+ tallyCompanyName + ", fileExtension=" + fileExtension + ", vehicleStatus=" + vehicleStatus
				+ ", renewalCount=" + renewalCount + ", vehicleStatusId=" + vehicleStatusId + ", userId=" + userId
				+ ", userName=" + userName + ", branchName=" + branchName + ", vehicleType=" + vehicleType
				+ ", renewalDueStatus=" + renewalDueStatus + ", count=" + count + ", markForDelete=" + markForDelete
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastupdated=" + lastupdated + ", renewal_R_Number="
				+ renewal_R_Number + ", renewalAproval_Number=" + renewalAproval_Number + ", renewal_from_Date="
				+ renewal_from_Date + ", renewal_To_Date=" + renewal_To_Date + ", paymentTypeId=" + paymentTypeId
				+ ", renewal_Subid=" + renewal_Subid + ", renewalTypeId=" + renewalTypeId + ", companyId=" + companyId
				+ "]";
	}

}
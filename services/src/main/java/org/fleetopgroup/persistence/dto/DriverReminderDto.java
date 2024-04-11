package org.fleetopgroup.persistence.dto;

import java.util.Date;

/**
 * @author fleetop
 *
 *
 *
 */

public class DriverReminderDto {

	private int driver_remid;

	private Integer driver_id;

	private String driver_firstname;

	private String driver_Lastname;
	
	private String driverFatherName;

	private String driver_empnumber;

	private String driver_remindertype;
	
	private Long driverRenewalTypeId;
	private String driver_dlnumber;
	private Date driver_dlfrom;
	private Date driver_dlto;

	private String driver_dlfrom_show;
	private String driver_dlto_show;

	private Integer driver_timethreshold;
	private Integer driver_periedthreshold;
	private String driver_renewaldate;
	private String driver_revdate;

	/* Show Date field */

	private String driver_dueEndDate;
	private String driver_dueRemDate;
	private String driver_dueEndCalendar;
	private String driver_dueDifference;

	private String driver_filename;
	private byte[] driver_content;

	private String driver_contentType;
	
	private Long createdById;
	
	private String createdBy;
	
	private Date created;
	
	private Integer companyId;
	
	private String		renewalBase64Document;
	
	private String		driver_periedthresholdStr;
	
	private String		fileExtension;
	
	private Long		driverCount;
	
	private short		driverStatusId;
	
	private Long renewalRecieptId;
	
	private String driverAuthorised;
	
	private String drivedBadgeNo;
	
	private String driverAddress;
	
	private String homeNumber;

	private String mobileNumber;
	
	private String receiptNumber;
	
	private String receiptDate;
	
	private Long 	receiptId;
	
	private String receiptDoc;
	
	private String fullName;

	private String dlStatus;
	
	private String driverStatus;
	
	private Long driverRenewalReceiptId;
	
	public String getDlStatus() {
		return dlStatus;
	}

	public void setDlStatus(String dlStatus) {
		this.dlStatus = dlStatus;
	}

	private boolean newDriverReminder;
	

	public String getDriver_filename() {
		return driver_filename;
	}

	public String getDriver_dlfrom_show() {
		return driver_dlfrom_show;
	}

	public void setDriver_dlfrom_show(String driver_dlfrom_show) {
		this.driver_dlfrom_show = driver_dlfrom_show;
	}

	public String getDriver_dlto_show() {
		return driver_dlto_show;
	}

	public void setDriver_dlto_show(String driver_dlto_show) {
		this.driver_dlto_show = driver_dlto_show;
	}

	public void setDriver_dlfrom(Date driver_dlfrom) {
		this.driver_dlfrom = driver_dlfrom;
	}

	public void setDriver_dlto(Date driver_dlto) {
		this.driver_dlto = driver_dlto;
	}

	public void setDriver_filename(String driver_filename) {
		this.driver_filename = driver_filename;
	}

	public byte[] getDriver_content() {
		return driver_content;
	}

	public void setDriver_content(byte[] driver_content) {
		this.driver_content = driver_content;
	}

	public String getDriver_contentType() {
		return driver_contentType;
	}

	public void setDriver_contentType(String driver_contentType) {
		this.driver_contentType = driver_contentType;
	}

	public String getDriver_dueEndDate() {
		return driver_dueEndDate;
	}

	public void setDriver_dueEndDate(String driver_dueEndDate) {
		this.driver_dueEndDate = driver_dueEndDate;
	}

	public String getDriver_dueRemDate() {
		return driver_dueRemDate;
	}

	public void setDriver_dueRemDate(String driver_dueRemDate) {
		this.driver_dueRemDate = driver_dueRemDate;
	}

	public String getDriver_revdate() {
		return driver_revdate;
	}

	public void setDriver_revdate(String driver_revdate) {
		this.driver_revdate = driver_revdate;
	}

	public Date getDriver_dlfrom() {
		return driver_dlfrom;
	}

	public Date getDriver_dlto() {
		return driver_dlto;
	}

	public String getDriver_dueEndCalendar() {
		return driver_dueEndCalendar;
	}

	public void setDriver_dueEndCalendar(String driver_dueEndCalendar) {
		this.driver_dueEndCalendar = driver_dueEndCalendar;
	}

	public String getDriver_dueDifference() {
		return driver_dueDifference;
	}

	public void setDriver_dueDifference(String driver_dueDifference) {
		this.driver_dueDifference = driver_dueDifference;
	}

	public int getDriver_remid() {
		return driver_remid;
	}

	public void setDriver_remid(int driver_remid) {
		this.driver_remid = driver_remid;
	}

	public Integer getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	public String getDriver_remindertype() {
		return driver_remindertype;
	}

	public void setDriver_remindertype(String driver_remindertype) {
		this.driver_remindertype = driver_remindertype;
	}

	public String getDriver_dlnumber() {
		return driver_dlnumber;
	}

	public void setDriver_dlnumber(String driver_dlnumber) {
		this.driver_dlnumber = driver_dlnumber;
	}

	public Integer getDriver_timethreshold() {
		return driver_timethreshold;
	}

	public void setDriver_timethreshold(Integer driver_timethreshold) {
		this.driver_timethreshold = driver_timethreshold;
	}

	public Integer getDriver_periedthreshold() {
		return driver_periedthreshold;
	}

	public void setDriver_periedthreshold(Integer driver_periedthreshold) {
		this.driver_periedthreshold = driver_periedthreshold;
	}

	public String getDriver_renewaldate() {
		return driver_renewaldate;
	}

	public void setDriver_renewaldate(String driver_renewaldate) {
		this.driver_renewaldate = driver_renewaldate;
	}

	public Long getDriverRenewalTypeId() {
		return driverRenewalTypeId;
	}

	public void setDriverRenewalTypeId(Long driverRenewalTypeId) {
		this.driverRenewalTypeId = driverRenewalTypeId;
	}

	public String getDriver_firstname() {
		return driver_firstname;
	}

	public void setDriver_firstname(String driver_firstname) {
		this.driver_firstname = driver_firstname;
	}

	public String getDriver_Lastname() {
		return driver_Lastname;
	}

	public void setDriver_Lastname(String driver_Lastname) {
		this.driver_Lastname = driver_Lastname;
	}

	public String getDriver_empnumber() {
		return driver_empnumber;
	}

	public void setDriver_empnumber(String driver_empnumber) {
		this.driver_empnumber = driver_empnumber;
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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public String getRenewalBase64Document() {
		return renewalBase64Document;
	}

	public void setRenewalBase64Document(String renewalBase64Document) {
		this.renewalBase64Document = renewalBase64Document;
	}

	public String getDriver_periedthresholdStr() {
		return driver_periedthresholdStr;
	}

	public void setDriver_periedthresholdStr(String driver_periedthresholdStr) {
		this.driver_periedthresholdStr = driver_periedthresholdStr;
	}
	
	public String getFileExtension() {
		return fileExtension;
	}


	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getDriverFatherName() {
		return driverFatherName;
	}

	public void setDriverFatherName(String driverFatherName) {
		this.driverFatherName = driverFatherName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverReminderDto [driver_remid=");
		builder.append(driver_remid);
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", driver_remindertype=");
		builder.append(driver_remindertype);
		builder.append(", driver_dlnumber=");
		builder.append(driver_dlnumber);
		builder.append(", driver_dlfrom=");
		builder.append(driver_dlfrom);
		builder.append(", driver_dlto=");
		builder.append(driver_dlto);
		builder.append(", driver_timethreshold=");
		builder.append(driver_timethreshold);
		builder.append(", driver_periedthreshold=");
		builder.append(driver_periedthreshold);
		builder.append(", driver_renewaldate=");
		builder.append(driver_renewaldate);
		builder.append(", driver_dueEndDate=");
		builder.append(driver_dueEndDate);
		builder.append(", driver_dueRemDate=");
		builder.append(driver_dueRemDate);
		builder.append(", driver_dueEndCalendar=");
		builder.append(driver_dueEndCalendar);
		builder.append(", driver_dueDifference=");
		builder.append(driver_dueDifference);
		builder.append(", driver_filename=");
		builder.append(driver_filename);
		builder.append(", driver_content=");
		builder.append(driver_content);
		builder.append(", driver_contentType=");
		builder.append(driver_contentType);
		builder.append(", driverRenewalTypeId=");
		builder.append(driverRenewalTypeId);
		builder.append(", driver_dlfrom_show=");
		builder.append(driver_dlfrom_show);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", renewalBase64Document=");
		builder.append(renewalBase64Document);
		builder.append("]");
		return builder.toString();
	}

	public Long getDriverCount() {
		return driverCount;
	}

	public void setDriverCount(Long driverCount) {
		this.driverCount = driverCount;
	}

	public short getDriverStatusId() {
		return driverStatusId;
	}

	public void setDriverStatusId(short driverStatusId) {
		this.driverStatusId = driverStatusId;
	}

	public Long getRenewalRecieptId() {
		return renewalRecieptId;
	}

	public void setRenewalRecieptId(Long renewalRecieptId) {
		this.renewalRecieptId = renewalRecieptId;
	}

	public boolean isNewDriverReminder() {
		return newDriverReminder;
	}

	public void setNewDriverReminder(boolean newDriverReminder) {
		this.newDriverReminder = newDriverReminder;
	}

	public String getDriverAuthorised() {
		return driverAuthorised;
	}

	public void setDriverAuthorised(String driverAuthorised) {
		this.driverAuthorised = driverAuthorised;
	}

	public String getDrivedBadgeNo() {
		return drivedBadgeNo;
	}

	public void setDrivedBadgeNo(String drivedBadgeNo) {
		this.drivedBadgeNo = drivedBadgeNo;
	}

	public String getDriverAddress() {
		return driverAddress;
	}

	public void setDriverAddress(String driverAddress) {
		this.driverAddress = driverAddress;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public String getReceiptDoc() {
		return receiptDoc;
	}

	public void setReceiptDoc(String receiptDoc) {
		this.receiptDoc = receiptDoc;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDriverStatus() {
		return driverStatus;
	}

	public void setDriverStatus(String driverStatus) {
		this.driverStatus = driverStatus;
	}

	public Long getDriverRenewalReceiptId() {
		return driverRenewalReceiptId;
	}

	public void setDriverRenewalReceiptId(Long driverRenewalReceiptId) {
		this.driverRenewalReceiptId = driverRenewalReceiptId;
	}

	

}

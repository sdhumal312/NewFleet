package org.fleetopgroup.persistence.dto;

import java.math.BigInteger;
import java.util.Date;

public class DriverRenewalRecieptDto {
	
	
	private long driverRenewalReceiptId;
	
	private BigInteger driverRenewalReceiptIdBig;
	
	private int reminderId;
	
	private long documentId;
	
	private BigInteger documentIdBig;
	
	private String applicationNo;
	
	private String receiptNo;
	
	private Date ReceiptDate;
	
	private String ReceiptDateStr;
	
	private Long driverRenewalTypeId;
	
	private Long createdById;
	
	private Long lastModifiedById;

	private Date created;

	private Date lastupdated;
	
	private Integer companyId;
	
	private int driver_id;
	

	private String driver_firstname;

	private String driver_Lastname;
	
	private String driverFatherName;

	private String driver_empnumber;

	private String driverRenewalType;
	
	private String driver_dlnumber;
	
	boolean	markForDelete;

	public long getDriverRenewalReceiptId() {
		return driverRenewalReceiptId;
	}

	public void setDriverRenewalReceiptId(long driverRenewalReceiptId) {
		this.driverRenewalReceiptId = driverRenewalReceiptId;
	}

	public int getReminderId() {
		return reminderId;
	}

	public void setReminderId(int reminderId) {
		this.reminderId = reminderId;
	}

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Date getReceiptDate() {
		return ReceiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		ReceiptDate = receiptDate;
	}

	public String getReceiptDateEtr() {
		return ReceiptDateStr;
	}

	public void setReceiptDateEtr(String receiptDateEtr) {
		ReceiptDateStr = receiptDateEtr;
	}

	public Long getDriverRenewalTypeId() {
		return driverRenewalTypeId;
	}

	public void setDriverRenewalTypeId(Long driverRenewalTypeId) {
		this.driverRenewalTypeId = driverRenewalTypeId;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public int getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	public String getDriverFatherName() {
		return driverFatherName;
	}

	public void setDriverFatherName(String driverFatherName) {
		this.driverFatherName = driverFatherName;
	}

	public String getDriver_empnumber() {
		return driver_empnumber;
	}

	public void setDriver_empnumber(String driver_empnumber) {
		this.driver_empnumber = driver_empnumber;
	}

	public String getDriverRenewalType() {
		return driverRenewalType;
	}

	public void setDriverRenewalType(String driverRenewalType) {
		this.driverRenewalType = driverRenewalType;
	}

	public String getDriver_dlnumber() {
		return driver_dlnumber;
	}

	public void setDriver_dlnumber(String driver_dlnumber) {
		this.driver_dlnumber = driver_dlnumber;
	}

	public BigInteger getDocumentIdBig() {
		return documentIdBig;
	}

	public void setDocumentIdBig(BigInteger documentIdBig) {
		this.documentIdBig = documentIdBig;
	}

	public BigInteger getDriverRenewalReceiptIdBig() {
		return driverRenewalReceiptIdBig;
	}

	public void setDriverRenewalReceiptIdBig(BigInteger driverRenewalReceiptIdBig) {
		this.driverRenewalReceiptIdBig = driverRenewalReceiptIdBig;
	}

	@Override
	public String toString() {
		return "DriverRenewalRecieptDto [driverRenewalReceiptId=" + driverRenewalReceiptId + ", reminderId="
				+ reminderId + ", documentId=" + documentId + ", applicationNo=" + applicationNo + ", receiptNo="
				+ receiptNo + ", ReceiptDate=" + ReceiptDate + ", ReceiptDateEtr=" + ReceiptDateStr
				+ ", driverRenewalTypeId=" + driverRenewalTypeId + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", created=" + created + ", lastupdated=" + lastupdated
				+ ", companyId=" + companyId + ", driver_id=" + driver_id + ", markForDelete=" + markForDelete + "]";
	}


}

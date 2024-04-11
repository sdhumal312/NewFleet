package org.fleetopgroup.persistence.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DriverRenewalReceipt")
public class DriverRenewalReceipt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driverRenewalReceiptId")
	private long driverRenewalReceiptId;
	
	@Column(name = "reminderId")
	private int reminderId;
	
	@Column(name = "documentId")
	private long documentId;
	
	@Column(name = "applicationNo", length = 25)
	private String applicationNo;
	
	@Column(name = "receiptNo", length = 25)
	private String receiptNo;
	
	@Basic(optional = false)
	@Column(name = "ReceiptDate", updatable = false)
	private Date ReceiptDate;
	
	@Column(name = "driverRenewalTypeId", nullable = false)
	private Long driverRenewalTypeId;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	@Column(name ="companyId" , nullable = false)
	private Integer companyId;
	
	
	@Column(name ="driver_id")
	private int driver_id;
	
	@Column(name="newReceiptStatus", nullable = false)
	private boolean	newReceiptStatus;
	
	@Column(name="markForDelete", nullable = false)
	private boolean	markForDelete;

	public long getDriverRenewalReceiptId() {
		return driverRenewalReceiptId;
	}

	public void setDriverRenewalReceiptId(long driverRenewalReceiptId) {
		this.driverRenewalReceiptId = driverRenewalReceiptId;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public int getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}

	public Long getDriverRenewalTypeId() {
		return driverRenewalTypeId;
	}

	public void setDriverRenewalTypeId(Long driverRenewalTypeId) {
		this.driverRenewalTypeId = driverRenewalTypeId;
	}

	public int getReminderId() {
		return reminderId;
	}

	public void setReminderId(int reminderId) {
		this.reminderId = reminderId;
	}

	public boolean isnewReceiptStatus() {
		return newReceiptStatus;
	}

	public void setnewReceiptStatus(boolean newReceiptStatus) {
		this.newReceiptStatus = newReceiptStatus;
	}
	

}

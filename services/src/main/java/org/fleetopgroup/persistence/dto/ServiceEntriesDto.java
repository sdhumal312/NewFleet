package org.fleetopgroup.persistence.dto;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.fleetopgroup.persistence.model.ServiceEntriesTasks;
import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 *
 *
 */

public class ServiceEntriesDto {
	
	

	private Long serviceEntries_id;
	private Long serviceEntries_Number;
	private String serviceEntriesNumberstr;
	private Integer vid;
	private String vehicle_registration;
	private String vehicle_Group;
	private Integer vehicle_Odometer;
	private String service_type;
	private String service_subtype;
	private Integer driver_id;
	private String driver_name;
	private Integer vendor_id;
	private String vendor_name;
	private String vendor_location;
	private String invoiceNumber;
	private String invoiceDate;
	private Date invoiceDate_On;
	private String jobNumber;
	private String service_paymentType;
	private long vehicleGroupId;
	private Date invoiceDateOn;
	private Date service_vendor_paymentdate;
	private String service_vendor_paymode;
	private Long service_vendor_approvalID;

	private String service_PayNumber;
	private String service_paidby;
	private String serviceEntries_status;
	private String service_paiddate;
	private Date service_paiddateOn;
	private Double totalservice_cost;
	private Long	accidentId;
	private Double totalserviceROUND_cost;
	private String completed_date;
	private Date completed_dateOn;

	private boolean service_document;

	private Long service_document_id;

	private Set<ServiceEntriesTasks> serviceentriestasks;

	/** The value for the created by email field */
	private String createdBy;
	private String lastModifiedBy;
	private String servicestatus;
	private String created;
	private String lastupdated;
	
	private short service_paymentTypeId;
	private Long service_paidbyId;
	private short serviceEntries_statusId;
	private short service_vendor_paymodeId;
	private Long createdById;
	private Long lastModifiedById;
	private Date createdOn;
	private Date lastupdatedOn;
	private Integer companyId;
	private String fromDate;
	private String toDate;
	private String serviceDate;
	private String serviceMonth;
	private String serviceYear;
	private String invoiceDateStr;
	private	Double	gpsOdometer;
	private short typeOfPaymentId;
	private	Double	workshopInvoiceAmount;
	private Long tripSheetId;
	private String typeOfPaymentStr;
	private double paidAmount;
	private Long tallyCompanyId;
	private String tallyCompanyName;
	private Long	tripSheetNumber;
	private boolean isPendingForTally;
	private Long	ISSUES_ID;
	private String	tallyExpenseName;
	private Integer	tallyExpenseId;
	private boolean	markForDelete;
	private Long userID;
	private String userName;
	private Long issueNumber;
	private String	issueSummary;
	
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public short getTypeOfPaymentId() {
		return typeOfPaymentId;
	}

	public void setTypeOfPaymentId(short typeOfPaymentId) {
		this.typeOfPaymentId = typeOfPaymentId;
	}

	public String getTypeOfPaymentStr() {
		return typeOfPaymentStr;
	}

	public void setTypeOfPaymentStr(String typeOfPaymentStr) {
		this.typeOfPaymentStr = typeOfPaymentStr;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = Utility.round( paidAmount,2);
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2);
	}

	private double balanceAmount;

	/**
	 * @return the serviceEntries_id
	 */
	public Long getServiceEntries_id() {
		return serviceEntries_id;
	}

	/**
	 * @param serviceEntries_id
	 *            the serviceEntries_id to set
	 */
	public void setServiceEntries_id(Long serviceEntries_id) {
		this.serviceEntries_id = serviceEntries_id;
	}

	public Long getServiceEntries_Number() {
		return serviceEntries_Number;
	}

	public void setServiceEntries_Number(Long serviceEntries_Number) {
		this.serviceEntries_Number = serviceEntries_Number;
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
	 * @return the vehicle_Group
	 */
	public String getVehicle_Group() {
		return vehicle_Group;
	}

	/**
	 * @param vehicle_Group
	 *            the vehicle_Group to set
	 */
	public void setVehicle_Group(String vehicle_Group) {
		this.vehicle_Group = vehicle_Group;
	}

	/**
	 * @return the vehicle_Odometer
	 */
	public Integer getVehicle_Odometer() {
		return vehicle_Odometer;
	}

	/**
	 * @param vehicle_Odometer
	 *            the vehicle_Odometer to set
	 */
	public void setVehicle_Odometer(Integer vehicle_Odometer) {
		this.vehicle_Odometer = vehicle_Odometer;
	}

	/**
	 * @return the completed_dateOn
	 */
	public Date getCompleted_dateOn() {
		return completed_dateOn;
	}

	/**
	 * @param completed_dateOn the completed_dateOn to set
	 */
	public void setCompleted_dateOn(Date completed_dateOn) {
		this.completed_dateOn = completed_dateOn;
	}

	/**
	 * @return the service_type
	 */
	public String getService_type() {
		return service_type;
	}

	/**
	 * @param service_type
	 *            the service_type to set
	 */
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	/**
	 * @return the service_subtype
	 */
	public String getService_subtype() {
		return service_subtype;
	}

	/**
	 * @param service_subtype
	 *            the service_subtype to set
	 */
	public void setService_subtype(String service_subtype) {
		this.service_subtype = service_subtype;
	}

	/**
	 * @return the vendor_id
	 */
	public Integer getVendor_id() {
		return vendor_id;
	}

	/**
	 * @param vendor_id
	 *            the vendor_id to set
	 */
	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	/**
	 * @return the vendor_name
	 */
	public String getVendor_name() {
		return vendor_name;
	}

	/**
	 * @param vendor_name
	 *            the vendor_name to set
	 */
	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	/**
	 * @return the vendor_location
	 */
	public String getVendor_location() {
		return vendor_location;
	}

	/**
	 * @param vendor_location
	 *            the vendor_location to set
	 */
	public void setVendor_location(String vendor_location) {
		this.vendor_location = vendor_location;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the invoiceDate
	 */
	public String getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate
	 *            the invoiceDate to set
	 */
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return the jobNumber
	 */
	public String getJobNumber() {
		return jobNumber;
	}

	/**
	 * @param jobNumber
	 *            the jobNumber to set
	 */
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	/**
	 * @return the service_paymentType
	 */
	public String getService_paymentType() {
		return service_paymentType;
	}

	/**
	 * @param service_paymentType
	 *            the service_paymentType to set
	 */
	public void setService_paymentType(String service_paymentType) {
		this.service_paymentType = service_paymentType;
	}

	/**
	 * @return the service_PayNumber
	 */
	public String getService_PayNumber() {
		return service_PayNumber;
	}

	/**
	 * @param service_PayNumber
	 *            the service_PayNumber to set
	 */
	public void setService_PayNumber(String service_PayNumber) {
		this.service_PayNumber = service_PayNumber;
	}

	/**
	 * @return the service_paidby
	 */
	public String getService_paidby() {
		return service_paidby;
	}

	/**
	 * @param service_paidby
	 *            the service_paidby to set
	 */
	public void setService_paidby(String service_paidby) {
		this.service_paidby = service_paidby;
	}

	/**
	 * @return the totalservice_cost
	 */
	public Double getTotalservice_cost() {
		return totalservice_cost;
	}

	/**
	 * @param totalservice_cost
	 *            the totalservice_cost to set
	 */
	public void setTotalservice_cost(Double totalservice_cost) {
		this.totalservice_cost = Utility.round(totalservice_cost, 2);
	}

	/**
	 * @return the serviceentriestasks
	 */
	public Set<ServiceEntriesTasks> getServiceentriestasks() {
		return serviceentriestasks;
	}

	/**
	 * @return the invoiceDateOn
	 */
	public Date getInvoiceDateOn() {
		return invoiceDateOn;
	}

	/**
	 * @param invoiceDateOn the invoiceDateOn to set
	 */
	public void setInvoiceDateOn(Date invoiceDateOn) {
		this.invoiceDateOn = invoiceDateOn;
	}

	/**
	 * @param serviceentriestasks
	 *            the serviceentriestasks to set
	 */
	public void setServiceentriestasks(Set<ServiceEntriesTasks> serviceentriestasks) {
		this.serviceentriestasks = serviceentriestasks;
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
	 * @return the servicestatus
	 */
	public String getServicestatus() {
		return servicestatus;
	}

	/**
	 * @param servicestatus
	 *            the servicestatus to set
	 */
	public void setServicestatus(String servicestatus) {
		this.servicestatus = servicestatus;
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
	 * @return the service_paiddateOn
	 */
	public Date getService_paiddateOn() {
		return service_paiddateOn;
	}

	/**
	 * @param service_paiddateOn the service_paiddateOn to set
	 */
	public void setService_paiddateOn(Date service_paiddateOn) {
		this.service_paiddateOn = service_paiddateOn;
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
	 * @return the service_paiddate
	 */
	public String getService_paiddate() {
		return service_paiddate;
	}

	/**
	 * @param service_paiddate
	 *            the service_paiddate to set
	 */
	public void setService_paiddate(String service_paiddate) {
		this.service_paiddate = service_paiddate;
	}

	/**
	 * @return the driver_id
	 */
	public Integer getDriver_id() {
		return driver_id;
	}

	/**
	 * @param driver_id
	 *            the driver_id to set
	 */
	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	/**
	 * @return the driver_name
	 */
	public String getDriver_name() {
		return driver_name;
	}

	/**
	 * @param driver_name
	 *            the driver_name to set
	 */
	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	/**
	 * @return the serviceEntries_status
	 */
	public String getServiceEntries_status() {
		return serviceEntries_status;
	}

	/**
	 * @param serviceEntries_status
	 *            the serviceEntries_status to set
	 */
	public void setServiceEntries_status(String serviceEntries_status) {
		this.serviceEntries_status = serviceEntries_status;
	}

	/**
	 * @return the completed_date
	 */
	public String getCompleted_date() {
		return completed_date;
	}

	/**
	 * @param completed_date
	 *            the completed_date to set
	 */
	public void setCompleted_date(String completed_date) {
		this.completed_date = completed_date;
	}

	public Date getService_vendor_paymentdate() {
		return service_vendor_paymentdate;
	}

	public void setService_vendor_paymentdate(Date service_vendor_paymentdate) {
		this.service_vendor_paymentdate = service_vendor_paymentdate;
	}

	public String getService_vendor_paymode() {
		return service_vendor_paymode;
	}

	/**
	 * @return the invoiceDate_On
	 */
	public Date getInvoiceDate_On() {
		return invoiceDate_On;
	}

	/**
	 * @param invoiceDate_On the invoiceDate_On to set
	 */
	public void setInvoiceDate_On(Date invoiceDate_On) {
		this.invoiceDate_On = invoiceDate_On;
	}

	public void setService_vendor_paymode(String service_vendor_paymode) {
		this.service_vendor_paymode = service_vendor_paymode;
	}

	public Long getService_vendor_approvalID() {
		return service_vendor_approvalID;
	}

	public void setService_vendor_approvalID(Long service_vendor_approvalID) {
		this.service_vendor_approvalID = service_vendor_approvalID;
	}

	/**
	 * @return the totalserviceROUND_cost
	 */
	public Double getTotalserviceROUND_cost() {
		return totalserviceROUND_cost;
	}

	/**
	 * @param totalserviceROUNT_cost
	 *            the totalserviceROUNT_cost to set
	 */
	public void setTotalserviceROUND_cost(Double totalserviceROUND_cost) {
		this.totalserviceROUND_cost = Utility.round(totalserviceROUND_cost, 2 );
	}

	/**
	 * @return the service_document
	 */
	public boolean isService_document() {
		return service_document;
	}

	/**
	 * @param service_document
	 *            the service_document to set
	 */
	public void setService_document(boolean service_document) {
		this.service_document = service_document;
	}

	/**
	 * @return the service_document_id
	 */
	public Long getService_document_id() {
		return service_document_id;
	}

	/**
	 * @param service_document_id
	 *            the service_document_id to set
	 */
	public void setService_document_id(Long service_document_id) {
		this.service_document_id = service_document_id;
	}

	/**
	 * @return the service_paymentTypeId
	 */
	public short getService_paymentTypeId() {
		return service_paymentTypeId;
	}

	/**
	 * @param service_paymentTypeId the service_paymentTypeId to set
	 */
	public void setService_paymentTypeId(short service_paymentTypeId) {
		this.service_paymentTypeId = service_paymentTypeId;
	}

	/**
	 * @return the service_paidbyId
	 */
	public Long getService_paidbyId() {
		return service_paidbyId;
	}

	/**
	 * @param service_paidbyId the service_paidbyId to set
	 */
	public void setService_paidbyId(Long service_paidbyId) {
		this.service_paidbyId = service_paidbyId;
	}

	/**
	 * @return the serviceEntries_statusId
	 */
	public short getServiceEntries_statusId() {
		return serviceEntries_statusId;
	}

	/**
	 * @param serviceEntries_statusId the serviceEntries_statusId to set
	 */
	public void setServiceEntries_statusId(short serviceEntries_statusId) {
		this.serviceEntries_statusId = serviceEntries_statusId;
	}

	/**
	 * @return the service_vendor_paymodeId
	 */
	public short getService_vendor_paymodeId() {
		return service_vendor_paymodeId;
	}

	/**
	 * @param service_vendor_paymodeId the service_vendor_paymodeId to set
	 */
	public void setService_vendor_paymodeId(short service_vendor_paymodeId) {
		this.service_vendor_paymodeId = service_vendor_paymodeId;
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
	 * @return the vehicleGroupId
	 */
	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	/**
	 * @param vehicleGroupId the vehicleGroupId to set
	 */
	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getServiceMonth() {
		return serviceMonth;
	}

	public void setServiceMonth(String serviceMonth) {
		this.serviceMonth = serviceMonth;
	}

	public String getServiceYear() {
		return serviceYear;
	}

	public void setServiceYear(String serviceYear) {
		this.serviceYear = serviceYear;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}

	public Double getGpsOdometer() {
		return gpsOdometer;
	}

	public void setGpsOdometer(Double gpsOdometer) {
		this.gpsOdometer = Utility.round(gpsOdometer, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceEntriesDto [serviceEntries_id=");
		builder.append(serviceEntries_id);
		builder.append(", serviceEntries_Number=");
		builder.append(serviceEntries_Number);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", vehicle_Group=");
		builder.append(vehicle_Group);
		builder.append(", vehicle_Odometer=");
		builder.append(vehicle_Odometer);
		builder.append(", service_type=");
		builder.append(service_type);
		builder.append(", service_subtype=");
		builder.append(service_subtype);
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", driver_name=");
		builder.append(driver_name);
		builder.append(", vendor_id=");
		builder.append(vendor_id);
		builder.append(", vendor_name=");
		builder.append(vendor_name);
		builder.append(", vendor_location=");
		builder.append(vendor_location);
		builder.append(", invoiceNumber=");
		builder.append(invoiceNumber);
		builder.append(", invoiceDate=");
		builder.append(invoiceDate);
		builder.append(", jobNumber=");
		builder.append(jobNumber);
		builder.append(", service_paymentType=");
		builder.append(service_paymentType);
		builder.append(", service_vendor_paymentdate=");
		builder.append(service_vendor_paymentdate);
		builder.append(", service_vendor_paymode=");
		builder.append(service_vendor_paymode);
		builder.append(", service_vendor_approvalID=");
		builder.append(service_vendor_approvalID);
		builder.append(", service_PayNumber=");
		builder.append(service_PayNumber);
		builder.append(", service_paidby=");
		builder.append(service_paidby);
		builder.append(", serviceEntries_status=");
		builder.append(serviceEntries_status);
		builder.append(", service_paiddate=");
		builder.append(service_paiddate);
		builder.append(", totalservice_cost=");
		builder.append(totalservice_cost);
		builder.append(", totalserviceROUND_cost=");
		builder.append(totalserviceROUND_cost);
		builder.append(", completed_date=");
		builder.append(completed_date);
		builder.append(", service_document=");
		builder.append(service_document);
		builder.append(", service_document_id=");
		builder.append(service_document_id);
		builder.append(", serviceentriestasks=");
		builder.append(serviceentriestasks != null ? toString(serviceentriestasks, maxLen) : null);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", servicestatus=");
		builder.append(servicestatus);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", typeOfPaymentId=");
		builder.append(typeOfPaymentId);		
		builder.append(", paidAmount=");
		builder.append(paidAmount);		
		builder.append(", balanceAmount=");
		builder.append(balanceAmount);
		builder.append(", tallyCompanyId=");
		builder.append(tallyCompanyId);
		builder.append(", tallyCompanyName=");
		builder.append(tallyCompanyName);
		builder.append("]");
		return builder.toString();
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	public Double getWorkshopInvoiceAmount() {
		return workshopInvoiceAmount;
	}

	public void setWorkshopInvoiceAmount(Double workshopInvoiceAmount) {
		this.workshopInvoiceAmount = Utility.round(workshopInvoiceAmount, 2);
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

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	public Long getISSUES_ID() {
		return ISSUES_ID;
	}

	public void setISSUES_ID(Long iSSUES_ID) {
		ISSUES_ID = iSSUES_ID;
	}

	public String getTallyExpenseName() {
		return tallyExpenseName;
	}

	public void setTallyExpenseName(String tallyExpenseName) {
		this.tallyExpenseName = tallyExpenseName;
	}

	public Integer getTallyExpenseId() {
		return tallyExpenseId;
	}

	public void setTallyExpenseId(Integer tallyExpenseId) {
		this.tallyExpenseId = tallyExpenseId;
	}

	public Long getAccidentId() {
		return accidentId;
	}

	public void setAccidentId(Long accidentId) {
		this.accidentId = accidentId;
	}
	public String getServiceEntriesNumberstr() {
		return serviceEntriesNumberstr;
	}

	public void setServiceEntriesNumberstr(String serviceEntriesNumberstr) {
		this.serviceEntriesNumberstr = serviceEntriesNumberstr;
	}

	public Long getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(Long issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getIssueSummary() {
		return issueSummary;
	}

	public void setIssueSummary(String issueSummary) {
		this.issueSummary = issueSummary;
	}
	
}
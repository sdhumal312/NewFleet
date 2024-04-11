package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ServiceEntries")
public class ServiceEntries implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serviceEntries_id")
	private Long serviceEntries_id;
	
	@Column(name = "serviceEntries_Number", nullable = false)
	private Long serviceEntries_Number;

	@Column(name = "vid", length = 7)
	private Integer vid;

	@Column(name = "vehicle_Odometer", length = 10)
	private Integer vehicle_Odometer;

	@Column(name = "driver_id", length = 10)
	private Integer driver_id;

	@Column(name = "vendor_id")
	private Integer vendor_id;

	@Column(name = "invoiceNumber", length = 150)
	private String invoiceNumber;

	@Column(name = "invoiceDate")
	private Date invoiceDate;

	@Column(name = "jobNumber", length = 25)
	private String jobNumber;

	@Column(name = "service_paymentTypeId")
	private short service_paymentTypeId;

	@Column(name = "service_PayNumber", length = 25)
	private String service_PayNumber;

	@Column(name = "service_paidbyId")
	private Long service_paidbyId;

	@Column(name = "service_paiddate")
	private Date service_paiddate;

	@Column(name = "totalservice_cost", length = 10)
	private Double totalservice_cost;

	@Column(name = "totalserviceROUND_cost")
	private Double totalserviceROUND_cost;

	@Column(name = "serviceEntries_statusId")
	private short serviceEntries_statusId;

	@Column(name = "completed_date")
	private Date completed_date;

	/** The value for the Document available field */
	@Basic(optional = false)
	@Column(name = "service_document", nullable = false)
	private boolean service_document = false;

	@Column(name = "service_document_id")
	private Long service_document_id;
	

	@Column(name = "service_vendor_paymentdate")
	private Date service_vendor_paymentdate;
	
	@Column(name = "service_vendor_paymodeId")
	private short service_vendor_paymodeId;
	
	@Column(name = "workshopInvoiceAmount")
	private Double workshopInvoiceAmount;
	
	@Column(name = "paidAmount")
	private Double paidAmount;
	
	@Column(name = "balanceAmount")
	private Double balanceAmount;
	
	@Column(name = "discountAmount")
	private Double discountAmount;

	@Column(name = "service_vendor_approvalID")
	private Long service_vendor_approvalID;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;

	@OneToMany(mappedBy = "ServiceEntries")
	private Set<ServiceEntriesTasks> ServiceEntriesTasks;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById", updatable = false)
	private Long lastModifiedById;
	
	@Column(name = "expectedPaymentDate")
	private Timestamp	expectedPaymentDate;


	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "gpsOdometer")
	private	Double	gpsOdometer;
	
	@Column(name = "ISSUES_ID")
	private Long ISSUES_ID;
	
	@Column(name = "tallyCompanyId")
	private Long tallyCompanyId;
	
	@Column(name = "tallyExpenseId")
	private Integer tallyExpenseId;
	
	@Column(name = "tripSheetId")
	private Long tripSheetId;
	
	@Column(name = "accidentId")
	private Long accidentId;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;

	public ServiceEntries() {
		super();
	}

	public ServiceEntries(Long serviceEntries_Number, Integer vid, Integer vehicle_Odometer,
			Integer driver_id, String driver_name, Integer vendor_id, String vendor_name, String vendor_location,
			String invoiceNumber, Date invoiceDate, String jobNumber, 
			String service_PayNumber,  Date service_paiddate, Double totalservice_cost,
			 Date completed_date,
			Set<ServiceEntriesTasks> serviceEntriesTasks, String createdBy,
			String lastModifiedBy, Date created, Date lastupdated,  Integer companyId,Long ISSUES_ID) {
		super();
		this.serviceEntries_Number	= serviceEntries_Number;
		this.vid = vid;
		this.vehicle_Odometer = vehicle_Odometer;
		this.driver_id = driver_id;
		this.vendor_id = vendor_id;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.jobNumber = jobNumber;
		this.service_PayNumber = service_PayNumber;
		this.service_paiddate = service_paiddate;
		this.totalservice_cost = totalservice_cost;
		this.completed_date = completed_date;
		ServiceEntriesTasks = serviceEntriesTasks;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
		this.ISSUES_ID = ISSUES_ID;
	}

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

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate
	 *            the invoiceDate to set
	 */
	public void setInvoiceDate(Date invoiceDate) {
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
		this.totalservice_cost = totalservice_cost;
	}

	public Date getService_vendor_paymentdate() {
		return service_vendor_paymentdate;
	}

	public void setService_vendor_paymentdate(Date service_vendor_paymentdate) {
		this.service_vendor_paymentdate = service_vendor_paymentdate;
	}

	
	public Long getService_vendor_approvalID() {
		return service_vendor_approvalID;
	}

	public void setService_vendor_approvalID(Long service_vendor_approvalID) {
		this.service_vendor_approvalID = service_vendor_approvalID;
	}

	

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the serviceEntriesTasks
	 */
	public Set<ServiceEntriesTasks> getServiceEntriesTasks() {
		return ServiceEntriesTasks;
	}

	/**
	 * @param serviceEntriesTasks
	 *            the serviceEntriesTasks to set
	 */
	public void setServiceEntriesTasks(Set<ServiceEntriesTasks> serviceEntriesTasks) {
		ServiceEntriesTasks = serviceEntriesTasks;
	}


	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the service_paiddate
	 */
	public Date getService_paiddate() {
		return service_paiddate;
	}

	/**
	 * @param service_paiddate
	 *            the service_paiddate to set
	 */
	public void setService_paiddate(Date service_paiddate) {
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
	 * @return the completed_date
	 */
	public Date getCompleted_date() {
		return completed_date;
	}

	/**
	 * @param completed_date
	 *            the completed_date to set
	 */
	public void setCompleted_date(Date completed_date) {
		this.completed_date = completed_date;
	}

	/**
	 * @return the totalserviceROUNT_cost
	 */
	public Double getTotalserviceROUND_cost() {
		return totalserviceROUND_cost;
	}

	/**
	 * @param totalserviceROUNT_cost
	 *            the totalserviceROUNT_cost to set
	 */
	public void setTotalserviceROUND_cost(Double totalserviceROUNT_cost) {
		this.totalserviceROUND_cost = totalserviceROUNT_cost;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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


	public Double getGpsOdometer() {
		return gpsOdometer;
	}

	public void setGpsOdometer(Double gpsOdometer) {
		this.gpsOdometer = gpsOdometer;
	}
	
	public Double getWorkshopInvoiceAmount() {
		return workshopInvoiceAmount;
	}

	public void setWorkshopInvoiceAmount(Double workshopInvoiceAmount) {
		this.workshopInvoiceAmount = workshopInvoiceAmount;
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

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getISSUES_ID() {
		return ISSUES_ID;
	}

	public Timestamp getExpectedPaymentDate() {
		return expectedPaymentDate;
	}

	public void setExpectedPaymentDate(Timestamp expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}

	public void setISSUES_ID(Long iSSUES_ID) {
		ISSUES_ID = iSSUES_ID;
	}
	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver_id == null) ? 0 : driver_id.hashCode());
		result = prime * result + ((invoiceNumber == null) ? 0 : invoiceNumber.hashCode());
		result = prime * result + ((jobNumber == null) ? 0 : jobNumber.hashCode());
		result = prime * result + ((vendor_id == null) ? 0 : vendor_id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceEntries other = (ServiceEntries) obj;
		if (driver_id == null) {
			if (other.driver_id != null)
				return false;
		} else if (!driver_id.equals(other.driver_id))
			return false;
		if (invoiceNumber == null) {
			if (other.invoiceNumber != null)
				return false;
		} else if (!invoiceNumber.equals(other.invoiceNumber))
			return false;
		if (jobNumber == null) {
			if (other.jobNumber != null)
				return false;
		} else if (!jobNumber.equals(other.jobNumber))
			return false;
		if (vendor_id == null) {
			if (other.vendor_id != null)
				return false;
		} else if (!vendor_id.equals(other.vendor_id))
			return false;
		return true;
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
		builder.append("ServiceEntries [serviceEntries_id=");
		builder.append(serviceEntries_id);
		builder.append(", serviceEntries_Number=");
		builder.append(serviceEntries_Number);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", vehicle_Odometer=");
		builder.append(vehicle_Odometer);
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", vendor_id=");
		builder.append(vendor_id);
		builder.append(", invoiceNumber=");
		builder.append(invoiceNumber);
		builder.append(", invoiceDate=");
		builder.append(invoiceDate);
		builder.append(", jobNumber=");
		builder.append(jobNumber);
		builder.append(", service_PayNumber=");
		builder.append(service_PayNumber);
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
		builder.append(", service_vendor_paymentdate=");
		builder.append(service_vendor_paymentdate);
		builder.append(", service_vendor_approvalID=");
		builder.append(service_vendor_approvalID);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", ServiceEntriesTasks=");
		builder.append(ServiceEntriesTasks != null ? toString(ServiceEntriesTasks, maxLen) : null);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", ISSUES_ID=");
		builder.append(ISSUES_ID);
		builder.append(", tallyCompanyId=");
		builder.append(tallyCompanyId);
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

}
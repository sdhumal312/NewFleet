package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Fuel")
public class Fuel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fuel_id")
	private Long fuel_id;
	
	@Column(name = "fuel_Number",nullable = false)
	private Long fuel_Number;

	@Column(name = "vid", length = 10)
	private Integer vid;

	@Column(name = "fuel_date", length = 10)
	private Date fuel_date;

	@Column(name = "fuel_meter", length = 10)
	private Integer fuel_meter;

	@Column(name = "fuel_meter_old", length = 10)
	private Integer fuel_meter_old;

	@Column(name = "fuel_meter_attributes", length = 5)
	private Integer fuel_meter_attributes;

	@Column(name = "fuelTypeId", nullable = false)
	private short fuelTypeId;

	@Column(name = "fuel_liters", length = 10)
	private Double fuel_liters;

	@Column(name = "fuel_price", length = 40)
	private Double fuel_price;

	@Column(name = "vendor_id", length = 10)
	private Integer vendor_id;

	@Column(name = "paymentTypeId", nullable = false)
	private short	paymentTypeId;

	@Column(name = "fuel_reference", length = 150)
	private String fuel_reference;

	@Column(name = "driver_id", length = 10)
	private Integer driver_id;
	
	@Column(name = "secDriverID")
	private Integer secDriverID;
	
	@Column(name = "cleanerID")
	private Integer cleanerID;
	
	@Column(name = "routeID")
	private Integer routeID;

	@Column(name = "fuel_personal", length = 5)
	private Integer fuel_personal;

	@Column(name = "fuel_tank", length = 5)
	private Integer fuel_tank;

	@Column(name = "fuel_tank_partial", length = 5)
	private Integer fuel_tank_partial;

	@Column(name = "fuel_usage", length = 10)
	private Integer fuel_usage;

	@Column(name = "fuel_comments", length = 250)
	private String fuel_comments;

	@Column(name = "fuel_amount", length = 10)
	private Double fuel_amount;

	@Column(name = "fuel_kml", length = 10)
	private Double fuel_kml;

	@Column(name = "fuel_cost", length = 10)
	private Double fuel_cost;

	@Column(name = "fuel_vendor_paymentdate", length = 10)
	private Date fuel_vendor_paymentdate;
	
	@Column(name = "fuel_vendor_paymodeId", nullable = false)
	private short fuel_vendor_paymodeId;
	
	@Column(name = "paidAmount")
	private Double paidAmount;

	@Column(name = "balanceAmount")
	private Double balanceAmount;
	
	@Column(name = "discountAmount")
	private Double discountAmount;

	@Column(name = "fuel_vendor_approvalID")
	private Long fuel_vendor_approvalID;

	@Column(name = "fuel_TripsheetID")
	private Long fuel_TripsheetID;
	
	@Column(name = "tallyCompanyId")
	private Long tallyCompanyId;

	/** The value for the fuel Document available field */
	@Basic(optional = false)
	@Column(name = "fuel_document", nullable = false)
	private boolean fuel_document = false;

	/** The value for the fuel Document available field */
	@Column(name = "fuel_document_id")
	private Long fuel_document_id;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", nullable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "fuelDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fuelDateTime;

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
	
	@Column(name = "expectedPaymentDate")
	private Timestamp	expectedPaymentDate;
	
	@Column(name = "gpsOdometer")
	private Double gpsOdometer;
	
	@JoinColumn(name = "latitude")
	private String latitude;
	
	@JoinColumn(name = "latitude")
	private String longitude;

	@JoinColumn(name = "gpsUsageKM")
	private Double	gpsUsageKM;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	@Column(name = "fuelInvoiceId")
	private Long	fuelInvoiceId;
	
	@Column(name = "paidById")
	private Short paidById;
	
	@Column(name ="paidByBranchId")
	private Long paidByBranchId;
	
	public Fuel() {
		super();
	}

	public Fuel(Long fuel_Number, Integer vid, 
			Date fuel_date, Integer fuel_meter, Integer fuel_meter_old, Integer fuel_meter_attributes, 
			Double fuel_liters, Double fuel_price, Integer vendor_id, 
			 String fuel_reference, Integer driver_id,
			Integer fuel_personal, Integer fuel_tank, Integer fuel_tank_partial, Integer fuel_usage,
			String fuel_comments, Double fuel_amount, Double fuel_kml, Double fuel_cost, Date fuel_vendor_paymentdate,
			Long fuel_vendor_approvalID,
			boolean MarkForDelete, Date created, Date lastupdated, Integer companyId, Double paidAmount, 
			Double balanceAmount, Double discountAmount,String latitude,String longitude) {
		super();
		this.fuel_Number = fuel_Number;
		this.vid = vid;
		this.fuel_date = fuel_date;
		this.fuel_meter = fuel_meter;
		this.fuel_meter_old = fuel_meter_old;
		this.fuel_meter_attributes = fuel_meter_attributes;
		this.fuel_liters = fuel_liters;
		this.fuel_price = fuel_price;
		this.vendor_id = vendor_id;
		this.fuel_reference = fuel_reference;
		this.driver_id = driver_id;
		this.fuel_personal = fuel_personal;
		this.fuel_tank = fuel_tank;
		this.fuel_tank_partial = fuel_tank_partial;
		this.fuel_usage = fuel_usage;
		this.fuel_comments = fuel_comments;
		this.fuel_amount = fuel_amount;
		this.fuel_kml = fuel_kml;
		this.fuel_cost = fuel_cost;
		this.fuel_vendor_paymentdate = fuel_vendor_paymentdate;
		this.fuel_vendor_approvalID = fuel_vendor_approvalID;
		this.markForDelete = MarkForDelete;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
		this.paidAmount = paidAmount;
		this.balanceAmount = balanceAmount;
		this.discountAmount = discountAmount;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the fuel_id
	 */
	public Long getFuel_id() {
		return fuel_id;
	}

	/**
	 * @param fuel_id
	 *            the fuel_id to set
	 */
	public void setFuel_id(Long fuel_id) {
		this.fuel_id = fuel_id;
	}

	public Long getFuel_Number() {
		return fuel_Number;
	}

	public void setFuel_Number(Long fuel_Number) {
		this.fuel_Number = fuel_Number;
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
	 * @return the fuel_date
	 */
	public Date getFuel_date() {
		return fuel_date;
	}

	/**
	 * @param fuel_date
	 *            the fuel_date to set
	 */
	public void setFuel_date(Date fuel_date) {
		this.fuel_date = fuel_date;
	}

	/**
	 * @return the fuel_meter
	 */
	public Integer getFuel_meter() {
		return fuel_meter;
	}

	/**
	 * @param fuel_meter
	 *            the fuel_meter to set
	 */
	public void setFuel_meter(Integer fuel_meter) {
		this.fuel_meter = fuel_meter;
	}

	/**
	 * @return the fuel_meter_attributes
	 */
	public Integer getFuel_meter_attributes() {
		return fuel_meter_attributes;
	}

	/**
	 * @param fuel_meter_attributes
	 *            the fuel_meter_attributes to set
	 */
	public void setFuel_meter_attributes(Integer fuel_meter_attributes) {
		this.fuel_meter_attributes = fuel_meter_attributes;
	}

	
	/**
	 * @return the fuel_liters
	 */
	public Double getFuel_liters() {
		return fuel_liters;
	}

	/**
	 * @param fuel_liters
	 *            the fuel_liters to set
	 */
	public void setFuel_liters(Double fuel_liters) {
		this.fuel_liters = fuel_liters;
	}

	/**
	 * @return the fuel_price
	 */
	public Double getFuel_price() {
		return fuel_price;
	}

	/**
	 * @param fuel_price
	 *            the fuel_price to set
	 */
	public void setFuel_price(Double fuel_price) {
		this.fuel_price = fuel_price;
	}

	/**
	 * @return the vendor_id
	 */
	public int getVendor_id() {
		return vendor_id;
	}

	/**
	 * @param vendor_id
	 *            the vendor_id to set
	 */
	public void setVendor_id(int vendor_id) {
		this.vendor_id = vendor_id;
	}


	/**
	 * @return the fuel_reference
	 */
	public String getFuel_reference() {
		return fuel_reference;
	}

	/**
	 * @param fuel_reference
	 *            the fuel_reference to set
	 */
	public void setFuel_reference(String fuel_reference) {
		this.fuel_reference = fuel_reference;
	}

	/**
	 * @return the fuel_personal
	 */
	public Integer getFuel_personal() {
		return fuel_personal;
	}

	/**
	 * @param fuel_personal
	 *            the fuel_personal to set
	 */
	public void setFuel_personal(Integer fuel_personal) {
		this.fuel_personal = fuel_personal;
	}

	/**
	 * @return the fuel_tank
	 */
	public Integer getFuel_tank() {
		return fuel_tank;
	}

	/**
	 * @param fuel_tank
	 *            the fuel_tank to set
	 */
	public void setFuel_tank(Integer fuel_tank) {
		this.fuel_tank = fuel_tank;
	}

	/**
	 * @return the fuel_tank_partial
	 */
	public Integer getFuel_tank_partial() {
		return fuel_tank_partial;
	}

	/**
	 * @param fuel_tank_partial
	 *            the fuel_tank_partial to set
	 */
	public void setFuel_tank_partial(Integer fuel_tank_partial) {
		this.fuel_tank_partial = fuel_tank_partial;
	}

	/**
	 * @return the fuel_usage
	 */
	public Integer getFuel_usage() {
		return fuel_usage;
	}

	/**
	 * @param fuel_usage
	 *            the fuel_usage to set
	 */
	public void setFuel_usage(Integer fuel_usage) {
		this.fuel_usage = fuel_usage;
	}

	/**
	 * @return the fuel_comments
	 */
	public String getFuel_comments() {
		return fuel_comments;
	}

	/**
	 * @param fuel_comments
	 *            the fuel_comments to set
	 */
	public void setFuel_comments(String fuel_comments) {
		this.fuel_comments = fuel_comments;
	}

	/**
	 * @return the fuel_amount
	 */
	public Double getFuel_amount() {
		return fuel_amount;
	}

	/**
	 * @param fuel_amount
	 *            the fuel_amount to set
	 */
	public void setFuel_amount(Double fuel_amount) {
		this.fuel_amount = fuel_amount;
	}

	/**
	 * @return the fuel_kml
	 */
	public Double getFuel_kml() {
		return fuel_kml;
	}

	/**
	 * @param fuel_kml
	 *            the fuel_kml to set
	 */
	public void setFuel_kml(Double fuel_kml) {
		this.fuel_kml = fuel_kml;
	}

	/**
	 * @return the fuel_cost
	 */
	public Double getFuel_cost() {
		return fuel_cost;
	}

	/**
	 * @param fuel_cost
	 *            the fuel_cost to set
	 */
	public void setFuel_cost(Double fuel_cost) {
		this.fuel_cost = fuel_cost;
	}


	/**
	 * @param vendor_id
	 *            the vendor_id to set
	 */
	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	/**
	 * @return the fuel_meter_old
	 */
	public Integer getFuel_meter_old() {
		return fuel_meter_old;
	}

	/**
	 * @param fuel_meter_old
	 *            the fuel_meter_old to set
	 */
	public void setFuel_meter_old(Integer fuel_meter_old) {
		this.fuel_meter_old = fuel_meter_old;
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
	 * @return the fuel_vendor_paymentdate
	 */
	public Date getFuel_vendor_paymentdate() {
		return fuel_vendor_paymentdate;
	}

	/**
	 * @param fuel_vendor_paymentdate
	 *            the fuel_vendor_paymentdate to set
	 */
	public void setFuel_vendor_paymentdate(Date fuel_vendor_paymentdate) {
		this.fuel_vendor_paymentdate = fuel_vendor_paymentdate;
	}

	
	/**
	 * @return the fuel_vendor_approvalID
	 */
	public Long getFuel_vendor_approvalID() {
		return fuel_vendor_approvalID;
	}

	/**
	 * @param fuel_vendor_approvalID
	 *            the fuel_vendor_approvalID to set
	 */
	public void setFuel_vendor_approvalID(Long fuel_vendor_approvalID) {
		this.fuel_vendor_approvalID = fuel_vendor_approvalID;
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
	 * @return the fuel_TripsheetID
	 */
	public Long getFuel_TripsheetID() {
		return fuel_TripsheetID;
	}

	/**
	 * @param fuel_TripsheetID
	 *            the fuel_TripsheetID to set
	 */
	public void setFuel_TripsheetID(Long fuel_TripsheetID) {
		this.fuel_TripsheetID = fuel_TripsheetID;
	}

	/**
	 * @return the fuel_document
	 */
	public boolean isFuel_document() {
		return fuel_document;
	}

	/**
	 * @param fuel_document
	 *            the fuel_document to set
	 */
	public void setFuel_document(boolean fuel_document) {
		this.fuel_document = fuel_document;
	}

	/**
	 * @return the fuel_document_id
	 */
	public Long getFuel_document_id() {
		return fuel_document_id;
	}

	/**
	 * @param fuel_document_id
	 *            the fuel_document_id to set
	 */
	public void setFuel_document_id(Long fuel_document_id) {
		this.fuel_document_id = fuel_document_id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	/**
	 * @return the fuelTypeId
	 */
	public short getFuelTypeId() {
		return fuelTypeId;
	}

	/**
	 * @param fuelTypeId the fuelTypeId to set
	 */
	public void setFuelTypeId(short fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}

	/**
	 * @return the paymentTypeId
	 */
	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	/**
	 * @param paymentTypeId the paymentTypeId to set
	 */
	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	/**
	 * @return the fuel_vendor_paymodeId
	 */
	public short getFuel_vendor_paymodeId() {
		return fuel_vendor_paymodeId;
	}

	/**
	 * @param fuel_vendor_paymodeId the fuel_vendor_paymodeId to set
	 */
	public void setFuel_vendor_paymodeId(short fuel_vendor_paymodeId) {
		this.fuel_vendor_paymodeId = fuel_vendor_paymodeId;
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
	

	public int getSecDriverID() {
		return secDriverID;
	}

	public void setSecDriverID(int secDriverID) {
		this.secDriverID = secDriverID;
	}

	public int getCleanerID() {
		return cleanerID;
	}

	public void setCleanerID(int cleanerID) {
		this.cleanerID = cleanerID;
	}

	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
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


	public Date getFuelDateTime() {
		return fuelDateTime;
	}

	public void setFuelDateTime(Date fuelDateTime) {
		this.fuelDateTime = fuelDateTime;
	}

	public void setSecDriverID(Integer secDriverID) {
		this.secDriverID = secDriverID;
	}

	public void setCleanerID(Integer cleanerID) {
		this.cleanerID = cleanerID;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public Timestamp getExpectedPaymentDate() {
		return expectedPaymentDate;
	}

	public void setExpectedPaymentDate(Timestamp expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public Double getGpsUsageKM() {
		return gpsUsageKM;
	}

	public void setGpsUsageKM(Double gpsUsageKM) {
		this.gpsUsageKM = gpsUsageKM;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	
	public Long getFuelInvoiceId() {
		return fuelInvoiceId;
	}

	public Short getPaidById() {
		return paidById;
	}

	public void setPaidById(Short paidById) {
		this.paidById = paidById;
	}

	public Long getPaidByBranchId() {
		return paidByBranchId;
	}

	public void setPaidByBranchId(Long paidByBranchId) {
		this.paidByBranchId = paidByBranchId;
	}

	public void setFuelInvoiceId(Long fuelInvoiceId) {
		this.fuelInvoiceId = fuelInvoiceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fuel_amount == null) ? 0 : fuel_amount.hashCode());
		result = prime * result + ((fuel_date == null) ? 0 : fuel_date.hashCode());
		result = prime * result + ((fuel_meter == null) ? 0 : fuel_meter.hashCode());
		result = prime * result + paymentTypeId;
		result = prime * result + ((vendor_id == null) ? 0 : vendor_id.hashCode());
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
		Fuel other = (Fuel) obj;
		if (fuel_amount == null) {
			if (other.fuel_amount != null)
				return false;
		} else if (!fuel_amount.equals(other.fuel_amount))
			return false;
		if (fuel_date == null) {
			if (other.fuel_date != null)
				return false;
		} else if (!fuel_date.equals(other.fuel_date))
			return false;
		if (fuel_meter == null) {
			if (other.fuel_meter != null)
				return false;
		} else if (!fuel_meter.equals(other.fuel_meter))
			return false;
		if (paymentTypeId != other.paymentTypeId)
			return false;
		if (vendor_id == null) {
			if (other.vendor_id != null)
				return false;
		} else if (!vendor_id.equals(other.vendor_id))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fuel [fuel_id=" + fuel_id + ", fuel_Number=" + fuel_Number + ", vid=" + vid + ", fuel_date=" + fuel_date
				+ ", fuel_meter=" + fuel_meter + ", fuel_meter_old=" + fuel_meter_old + ", fuel_meter_attributes="
				+ fuel_meter_attributes + ", fuelTypeId=" + fuelTypeId + ", fuel_liters=" + fuel_liters
				+ ", fuel_price=" + fuel_price + ", vendor_id=" + vendor_id + ", paymentTypeId=" + paymentTypeId
				+ ", fuel_reference=" + fuel_reference + ", driver_id=" + driver_id + ", secDriverID=" + secDriverID
				+ ", cleanerID=" + cleanerID + ", routeID=" + routeID + ", fuel_personal=" + fuel_personal
				+ ", fuel_tank=" + fuel_tank + ", fuel_tank_partial=" + fuel_tank_partial + ", fuel_usage=" + fuel_usage
				+ ", fuel_comments=" + fuel_comments + ", fuel_amount=" + fuel_amount + ", fuel_kml=" + fuel_kml
				+ ", fuel_cost=" + fuel_cost + ", fuel_vendor_paymentdate=" + fuel_vendor_paymentdate
				+ ", fuel_vendor_paymodeId=" + fuel_vendor_paymodeId + ", paidAmount=" + paidAmount + ", balanceAmount="
				+ balanceAmount + ", discountAmount=" + discountAmount + ", fuel_vendor_approvalID="
				+ fuel_vendor_approvalID + ", fuel_TripsheetID=" + fuel_TripsheetID + ", tallyCompanyId="
				+ tallyCompanyId + ", fuel_document=" + fuel_document + ", fuel_document_id=" + fuel_document_id
				+ ", companyId=" + companyId + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", markForDelete=" + markForDelete + ", fuelDateTime=" + fuelDateTime + ", created=" + created
				+ ", lastupdated=" + lastupdated + ", expectedPaymentDate=" + expectedPaymentDate + ", gpsOdometer="
				+ gpsOdometer + ", latitude=" + latitude + ", longitude=" + longitude + ", gpsUsageKM=" + gpsUsageKM
				+ ", isPendingForTally=" + isPendingForTally + ", fuelInvoiceId=" + fuelInvoiceId + ", paidById="
				+ paidById + ", paidByBranchId=" + paidByBranchId + "]";
	}
}

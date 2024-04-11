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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="UreaEntries")
public class UreaEntries implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ureaEntriesId")
	private	Long	ureaEntriesId;
	
	@Column(name = "ureaEntriesNumber")
	private Long	ureaEntriesNumber;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "tripSheetId")
	private	Long	tripSheetId;
	
	@Column(name = "ureaInvoiceToDetailsId")
	private Long	ureaInvoiceToDetailsId;
	
	@Column(name = "locationId")
	private Integer	locationId;
	
	@Column(name = "ureaDate")
	private Date	ureaDate;
	
	@Column(name = "ureaOdometer")
	private Double	ureaOdometer;
	
	@Column(name = "ureaOdometerOld")
	private Double	ureaOdometerOld;
	
	@Column(name = "ureaLiters")
	private Double	ureaLiters;
	
	@Column(name = "ureaRate")
	private Double	ureaRate;
	
	@Column(name = "discount")
	private Double	discount;
	
	@Column(name = "gst")
	private Double	gst;
	
	@Column(name = "ureaAmount")
	private Double	ureaAmount;
	
	@Column(name = "ureaManufacturerId")
	private Long	ureaManufacturerId;
	
	@Column(name = "reference", length = 150)
	private String reference;
	
	@Column(name = "driver_id", length = 10)
	private Integer driver_id;
	
	@Column(name = "secDriverID")
	private Integer secDriverID;
	
	@Column(name = "cleanerID")
	private Integer cleanerID;
	
	@Column(name = "routeID")
	private Integer routeID;
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", nullable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "meterWorkingStatus")
	boolean meterWorkingStatus;

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
	
	@Column(name = "filledLocationId")
	private Integer filledLocationId ;
	
	@Column(name = "filledBy")
	private String  filledBy;

	public Long getUreaEntriesId() {
		return ureaEntriesId;
	}

	public void setUreaEntriesId(Long ureaEntriesId) {
		this.ureaEntriesId = ureaEntriesId;
	}

	public Long getUreaEntriesNumber() {
		return ureaEntriesNumber;
	}

	public void setUreaEntriesNumber(Long ureaEntriesNumber) {
		this.ureaEntriesNumber = ureaEntriesNumber;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Long getUreaInvoiceToDetailsId() {
		return ureaInvoiceToDetailsId;
	}

	public void setUreaInvoiceToDetailsId(Long ureaInvoiceToDetailsId) {
		this.ureaInvoiceToDetailsId = ureaInvoiceToDetailsId;
	}

	public Date getUreaDate() {
		return ureaDate;
	}

	public void setUreaDate(Date ureaDate) {
		this.ureaDate = ureaDate;
	}

	public Double getUreaOdometer() {
		return ureaOdometer;
	}

	public void setUreaOdometer(Double ureaOdometer) {
		this.ureaOdometer = ureaOdometer;
	}

	public Double getUreaOdometerOld() {
		return ureaOdometerOld;
	}

	public void setUreaOdometerOld(Double ureaOdometerOld) {
		this.ureaOdometerOld = ureaOdometerOld;
	}

	public Double getUreaLiters() {
		return ureaLiters;
	}

	public void setUreaLiters(Double ureaLiters) {
		this.ureaLiters = ureaLiters;
	}

	public Double getUreaRate() {
		return ureaRate;
	}

	public void setUreaRate(Double ureaRate) {
		this.ureaRate = ureaRate;
	}

	public Double getDiscount() {
		return discount;
	}

	public Double getGst() {
		return gst;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getUreaAmount() {
		return ureaAmount;
	}

	public void setUreaAmount(Double ureaAmount) {
		this.ureaAmount = ureaAmount;
	}

	public Long getUreaManufacturerId() {
		return ureaManufacturerId;
	}

	public void setUreaManufacturerId(Long ureaManufacturerId) {
		this.ureaManufacturerId = ureaManufacturerId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Integer getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	public Integer getSecDriverID() {
		return secDriverID;
	}

	public void setSecDriverID(Integer secDriverID) {
		this.secDriverID = secDriverID;
	}

	public Integer getCleanerID() {
		return cleanerID;
	}

	public void setCleanerID(Integer cleanerID) {
		this.cleanerID = cleanerID;
	}


	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	public boolean isMeterWorkingStatus() {
		return meterWorkingStatus;
	}

	public void setMeterWorkingStatus(boolean meterWorkingStatus) {
		this.meterWorkingStatus = meterWorkingStatus;
	}



	public Integer getFilledLocationId() {
		return filledLocationId;
	}

	public void setFilledLocationId(Integer filledLocationId) {
		this.filledLocationId = filledLocationId;
	}

	public String getFilledBy() {
		return filledBy;
	}

	public void setFilledBy(String filledBy) {
		this.filledBy = filledBy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((ureaEntriesId == null) ? 0 : ureaEntriesId.hashCode());
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
		UreaEntries other = (UreaEntries) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (ureaEntriesId == null) {
			if (other.ureaEntriesId != null)
				return false;
		} else if (!ureaEntriesId.equals(other.ureaEntriesId))
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
		StringBuilder builder = new StringBuilder();
		builder.append("UreaEntries [ureaEntriesId=");
		builder.append(ureaEntriesId);
		builder.append(", ureaEntriesNumber=");
		builder.append(ureaEntriesNumber);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", tripSheetId=");
		builder.append(tripSheetId);
		builder.append(", ureaInvoiceToDetailsId=");
		builder.append(ureaInvoiceToDetailsId);
		builder.append(", ureaDate=");
		builder.append(ureaDate);
		builder.append(", ureaOdometer=");
		builder.append(ureaOdometer);
		builder.append(", ureaOdometerOld=");
		builder.append(ureaOdometerOld);
		builder.append(", ureaLiters=");
		builder.append(ureaLiters);
		builder.append(", ureaRate=");
		builder.append(ureaRate);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", gst=");
		builder.append(gst);
		builder.append(", ureaAmount=");
		builder.append(ureaAmount);
		builder.append(", ureaManufacturerId=");
		builder.append(ureaManufacturerId);
		builder.append(", reference=");
		builder.append(reference);
		builder.append(", driver_id=");
		builder.append(driver_id);
		builder.append(", secDriverID=");
		builder.append(secDriverID);
		builder.append(", cleanerID=");
		builder.append(cleanerID);
		builder.append(", routeID=");
		builder.append(routeID);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", meterWorkingStatus=");
		builder.append(meterWorkingStatus);
		builder.append("]");
		return builder.toString();
	}

}

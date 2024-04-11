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
@Table(name="UreaEntriesHistory")
public class UreaEntriesHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ureaEntriesHistoryId")
	private	Long ureaEntriesHistoryId;
	
	@Column(name = "ureaEntriesId")
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

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	public Long getUreaEntriesHistoryId() {
		return ureaEntriesHistoryId;
	}

	public void setUreaEntriesHistoryId(Long ureaEntriesHistoryId) {
		this.ureaEntriesHistoryId = ureaEntriesHistoryId;
	}

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

	public Long getUreaInvoiceToDetailsId() {
		return ureaInvoiceToDetailsId;
	}

	public void setUreaInvoiceToDetailsId(Long ureaInvoiceToDetailsId) {
		this.ureaInvoiceToDetailsId = ureaInvoiceToDetailsId;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
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

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getGst() {
		return gst;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ureaEntriesHistoryId == null) ? 0 : ureaEntriesHistoryId.hashCode());
		result = prime * result + ((ureaEntriesId == null) ? 0 : ureaEntriesId.hashCode());
		result = prime * result + ((ureaInvoiceToDetailsId == null) ? 0 : ureaInvoiceToDetailsId.hashCode());
		result = prime * result + ((ureaManufacturerId == null) ? 0 : ureaManufacturerId.hashCode());
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
		UreaEntriesHistory other = (UreaEntriesHistory) obj;
		if (ureaEntriesHistoryId == null) {
			if (other.ureaEntriesHistoryId != null)
				return false;
		} else if (!ureaEntriesHistoryId.equals(other.ureaEntriesHistoryId))
			return false;
		if (ureaEntriesId == null) {
			if (other.ureaEntriesId != null)
				return false;
		} else if (!ureaEntriesId.equals(other.ureaEntriesId))
			return false;
		if (ureaInvoiceToDetailsId == null) {
			if (other.ureaInvoiceToDetailsId != null)
				return false;
		} else if (!ureaInvoiceToDetailsId.equals(other.ureaInvoiceToDetailsId))
			return false;
		if (ureaManufacturerId == null) {
			if (other.ureaManufacturerId != null)
				return false;
		} else if (!ureaManufacturerId.equals(other.ureaManufacturerId))
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
		return "UreaEntriesHistory [ureaEntriesHistoryId=" + ureaEntriesHistoryId + ", ureaEntriesId=" + ureaEntriesId
				+ ", ureaEntriesNumber=" + ureaEntriesNumber + ", vid=" + vid + ", tripSheetId=" + tripSheetId
				+ ", ureaInvoiceToDetailsId=" + ureaInvoiceToDetailsId + ", locationId=" + locationId + ", ureaDate="
				+ ureaDate + ", ureaOdometer=" + ureaOdometer + ", ureaOdometerOld=" + ureaOdometerOld + ", ureaLiters="
				+ ureaLiters + ", ureaRate=" + ureaRate + ", discount=" + discount + ", gst=" + gst + ", ureaAmount="
				+ ureaAmount + ", ureaManufacturerId=" + ureaManufacturerId + ", reference=" + reference
				+ ", driver_id=" + driver_id + ", secDriverID=" + secDriverID + ", cleanerID=" + cleanerID
				+ ", routeID=" + routeID + ", comments=" + comments + ", companyId=" + companyId + ", createdById="
				+ createdById + ", markForDelete=" + markForDelete + ", created=" + created + "]";
	}
	
}

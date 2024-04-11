package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class UreaEntriesHistoryDto {
	
	private	Long ureaEntriesHistoryId;
	
	private	Long	ureaEntriesId;
	
	private Long	ureaEntriesNumber;
	
	private Integer	vid;
	
	private	Long	tripSheetId;
	
	private Long	ureaInvoiceToDetailsId;
	
	private Integer	locationId;
	
	private Date	ureaDate;
	
	private Double	ureaOdometer;
	
	private Double	ureaOdometerOld;
	
	private Double	ureaLiters;
	
	private Double	ureaRate;
	
	private Double	discount;
	
	private Double	gst;
	
	private Double	ureaAmount;
	
	private Long	ureaManufacturerId;
	
	private String reference;
	
	private Integer driver_id;
	
	private Integer secDriverID;
	
	private Integer cleanerID;
	
	private Integer routeID;
	
	private String comments;
	
	private Integer companyId;

	private Long createdById;

	boolean markForDelete;

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
		this.ureaOdometer = Utility.round( ureaOdometer, 2);
	}



	public Double getUreaOdometerOld() {
		return ureaOdometerOld;
	}



	public void setUreaOdometerOld(Double ureaOdometerOld) {
		this.ureaOdometerOld = Utility.round(ureaOdometerOld, 2);
	}



	public Double getUreaLiters() {
		return ureaLiters;
	}



	public void setUreaLiters(Double ureaLiters) {
		this.ureaLiters = Utility.round(ureaLiters, 2);
	}



	public Double getUreaRate() {
		return ureaRate;
	}



	public void setUreaRate(Double ureaRate) {
		this.ureaRate = Utility.round(ureaRate, 2);
	}



	public Double getDiscount() {
		return discount;
	}



	public void setDiscount(Double discount) {
		this.discount = Utility.round(discount, 2);
	}



	public Double getGst() {
		return gst;
	}



	public void setGst(Double gst) {
		this.gst = Utility.round(gst, 2) ;
	}



	public Double getUreaAmount() {
		return ureaAmount;
	}



	public void setUreaAmount(Double ureaAmount) {
		this.ureaAmount =  Utility.round(ureaAmount, 2);
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
	public String toString() {
		return "UreaEntriesHistoryDto [ureaEntriesHistoryId=" + ureaEntriesHistoryId + ", ureaEntriesId="
				+ ureaEntriesId + ", ureaEntriesNumber=" + ureaEntriesNumber + ", vid=" + vid + ", tripSheetId="
				+ tripSheetId + ", ureaInvoiceToDetailsId=" + ureaInvoiceToDetailsId + ", locationId=" + locationId
				+ ", ureaDate=" + ureaDate + ", ureaOdometer=" + ureaOdometer + ", ureaOdometerOld=" + ureaOdometerOld
				+ ", ureaLiters=" + ureaLiters + ", ureaRate=" + ureaRate + ", discount=" + discount + ", gst=" + gst
				+ ", ureaAmount=" + ureaAmount + ", ureaManufacturerId=" + ureaManufacturerId + ", reference="
				+ reference + ", driver_id=" + driver_id + ", secDriverID=" + secDriverID + ", cleanerID=" + cleanerID
				+ ", routeID=" + routeID + ", comments=" + comments + ", companyId=" + companyId + ", createdById="
				+ createdById + ", markForDelete=" + markForDelete + ", created=" + created + "]";
	}
	
}
package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Column;

import org.fleetopgroup.web.util.Utility;

public class UreaEntriesDto {

	private	Long	ureaEntriesId;
	
	private Long	ureaEntriesNumber;
	
	private String	ureaEntriesNumberStr;
	
	private Integer	vid;
	
	private	Long	tripSheetId;
	
	private Long	ureaInvoiceToDetailsId;
	
	private Integer	locationId;
	
	private Date	ureaDate;
	
	private Double	ureaOdometer;
	
	private Double	ureaOdometerOld;
	
	private Double	totalOdometer;
	
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
	
	private Long lastModifiedById;

	boolean markForDelete;

	private Date created;

	private Date lastupdated;
	
	private Long	tripSheetNumber;
	
	private String	vehicle_registration;
	
	private String	ureaDateStr;
	
	private String	locationName;
	
	private String firsDriverName;
	
	private String secDriverName;
	
	private String cleanerName;
	
	private String firsDriverLastName;
	
	private String secDriverLastName;
	
	private String cleanerLastName;
	
	private String createdOnStr;
	
	private String lastModifiedStr;
	
	private String createdBy;
	
	private String lastModifiedBy;
	
	private String routeName;
	
	private String vehicleGrpName;
	
	private Integer vehicle_ExpectedOdameter;
	
	private Double 	quantity;
	
	private Double	usedQuantity;
	
	private Double	stockQuantity;
	
	private Long 	ureaInvoiceNumber;
	
	private String	manufacturerName;
	
	private Double	kmpl;
	
	boolean meterWorkingStatus;
	
	private Integer filledLocationId ;
	
	private String   filledLocation ;
	
	private String  filledBy;
	

	public Long getUreaEntriesId() {
		return ureaEntriesId;
	}

	public Long getUreaEntriesNumber() {
		return ureaEntriesNumber;
	}

	public Integer getVid() {
		return vid;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public Long getUreaInvoiceToDetailsId() {
		return ureaInvoiceToDetailsId;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public Date getUreaDate() {
		return ureaDate;
	}

	public Double getUreaOdometer() {
		return ureaOdometer;
	}

	public Double getUreaOdometerOld() {
		return ureaOdometerOld;
	}

	public Double getUreaLiters() {
		return ureaLiters;
	}

	public Double getUreaRate() {
		return ureaRate;
	}

	public Double getDiscount() {
		return discount;
	}

	public Double getGst() {
		return gst;
	}

	public Double getUreaAmount() {
		return ureaAmount;
	}

	public Long getUreaManufacturerId() {
		return ureaManufacturerId;
	}

	public String getReference() {
		return reference;
	}

	public Integer getDriver_id() {
		return driver_id;
	}

	public Integer getSecDriverID() {
		return secDriverID;
	}

	public Integer getCleanerID() {
		return cleanerID;
	}

	public Integer getRouteID() {
		return routeID;
	}

	public String getComments() {
		return comments;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public Date getCreated() {
		return created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setUreaEntriesId(Long ureaEntriesId) {
		this.ureaEntriesId = ureaEntriesId;
	}

	public void setUreaEntriesNumber(Long ureaEntriesNumber) {
		this.ureaEntriesNumber = ureaEntriesNumber;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public void setUreaInvoiceToDetailsId(Long ureaInvoiceToDetailsId) {
		this.ureaInvoiceToDetailsId = ureaInvoiceToDetailsId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public void setUreaDate(Date ureaDate) {
		this.ureaDate = ureaDate;
	}

	public void setUreaOdometer(Double ureaOdometer) {
		this.ureaOdometer = Utility.round(ureaOdometer, 2) ;
	}

	public void setUreaOdometerOld(Double ureaOdometerOld) {
		this.ureaOdometerOld = Utility.round( ureaOdometerOld, 2);
	}

	public void setUreaLiters(Double ureaLiters) {
		this.ureaLiters =  Utility.round(ureaLiters, 2);
	}

	public void setUreaRate(Double ureaRate) {
		this.ureaRate = Utility.round(ureaRate, 2) ;
	}

	public void setDiscount(Double discount) {
		this.discount = Utility.round( discount, 2);
	}

	public void setGst(Double gst) {
		this.gst =  Utility.round(gst, 2);
	}

	public void setUreaAmount(Double ureaAmount) {
		this.ureaAmount =  Utility.round(ureaAmount, 2);
	}

	public void setUreaManufacturerId(Long ureaManufacturerId) {
		this.ureaManufacturerId = ureaManufacturerId;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	public void setSecDriverID(Integer secDriverID) {
		this.secDriverID = secDriverID;
	}

	public void setCleanerID(Integer cleanerID) {
		this.cleanerID = cleanerID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getUreaDateStr() {
		return ureaDateStr;
	}

	public void setUreaDateStr(String ureaDateStr) {
		this.ureaDateStr = ureaDateStr;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getFirsDriverName() {
		return firsDriverName;
	}

	public String getSecDriverName() {
		return secDriverName;
	}

	public String getCleanerName() {
		return cleanerName;
	}

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public String getLastModifiedStr() {
		return lastModifiedStr;
	}

	public void setFirsDriverName(String firsDriverName) {
		this.firsDriverName = firsDriverName;
	}

	public void setSecDriverName(String secDriverName) {
		this.secDriverName = secDriverName;
	}

	public void setCleanerName(String cleanerName) {
		this.cleanerName = cleanerName;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public void setLastModifiedStr(String lastModifiedStr) {
		this.lastModifiedStr = lastModifiedStr;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getVehicleGrpName() {
		return vehicleGrpName;
	}

	public void setVehicleGrpName(String vehicleGrpName) {
		this.vehicleGrpName = vehicleGrpName;
	}

	public String getFirsDriverLastName() {
		return firsDriverLastName;
	}

	public void setFirsDriverLastName(String firsDriverLastName) {
		this.firsDriverLastName = firsDriverLastName;
	}

	public String getSecDriverLastName() {
		return secDriverLastName;
	}

	public void setSecDriverLastName(String secDriverLastName) {
		this.secDriverLastName = secDriverLastName;
	}

	public String getCleanerLastName() {
		return cleanerLastName;
	}

	public void setCleanerLastName(String cleanerLastName) {
		this.cleanerLastName = cleanerLastName;
	}

	public Integer getVehicle_ExpectedOdameter() {
		return vehicle_ExpectedOdameter;
	}

	public void setVehicle_ExpectedOdameter(Integer vehicle_ExpectedOdameter) {
		this.vehicle_ExpectedOdameter = vehicle_ExpectedOdameter;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
	}

	public Double getUsedQuantity() {
		return usedQuantity;
	}

	public void setUsedQuantity(Double usedQuantity) {
		this.usedQuantity = Utility.round( usedQuantity, 2);
	}

	public Double getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Double stockQuantity) {
		this.stockQuantity = Utility.round(stockQuantity, 2);
	}

	public Long getUreaInvoiceNumber() {
		return ureaInvoiceNumber;
	}

	public void setUreaInvoiceNumber(Long ureaInvoiceNumber) {
		this.ureaInvoiceNumber = ureaInvoiceNumber;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cleanerID == null) ? 0 : cleanerID.hashCode());
		result = prime * result + ((driver_id == null) ? 0 : driver_id.hashCode());
		result = prime * result + ((ureaEntriesId == null) ? 0 : ureaEntriesId.hashCode());
		result = prime * result + ((ureaEntriesNumber == null) ? 0 : ureaEntriesNumber.hashCode());
		result = prime * result + ((ureaInvoiceToDetailsId == null) ? 0 : ureaInvoiceToDetailsId.hashCode());
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
		UreaEntriesDto other = (UreaEntriesDto) obj;
		if (cleanerID == null) {
			if (other.cleanerID != null)
				return false;
		} else if (!cleanerID.equals(other.cleanerID))
			return false;
		if (driver_id == null) {
			if (other.driver_id != null)
				return false;
		} else if (!driver_id.equals(other.driver_id))
			return false;
		if (ureaEntriesId == null) {
			if (other.ureaEntriesId != null)
				return false;
		} else if (!ureaEntriesId.equals(other.ureaEntriesId))
			return false;
		if (ureaEntriesNumber == null) {
			if (other.ureaEntriesNumber != null)
				return false;
		} else if (!ureaEntriesNumber.equals(other.ureaEntriesNumber))
			return false;
		if (ureaInvoiceToDetailsId == null) {
			if (other.ureaInvoiceToDetailsId != null)
				return false;
		} else if (!ureaInvoiceToDetailsId.equals(other.ureaInvoiceToDetailsId))
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
		return "UreaEntriesDto [ureaEntriesId=" + ureaEntriesId + ", ureaEntriesNumber=" + ureaEntriesNumber + ", vid="
				+ vid + ", tripSheetId=" + tripSheetId + ", ureaInvoiceToDetailsId=" + ureaInvoiceToDetailsId
				+ ", locationId=" + locationId + ", ureaDate=" + ureaDate + ", ureaOdometer=" + ureaOdometer
				+ ", ureaOdometerOld=" + ureaOdometerOld + ", ureaLiters=" + ureaLiters + ", ureaRate=" + ureaRate
				+ ", discount=" + discount + ", gst=" + gst + ", ureaAmount=" + ureaAmount + ", ureaManufacturerId="
				+ ureaManufacturerId + ", reference=" + reference + ", driver_id=" + driver_id + ", secDriverID="
				+ secDriverID + ", cleanerID=" + cleanerID + ", routeID=" + routeID + ", comments=" + comments
				+ ", companyId=" + companyId + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", markForDelete=" + markForDelete + ", created=" + created + ", lastupdated=" + lastupdated
				+ ", tripSheetNumber=" + tripSheetNumber + ", vehicle_registration=" + vehicle_registration
				+ ", ureaDateStr=" + ureaDateStr + ", locationName=" + locationName + ", firsDriverName="
				+ firsDriverName + ", secDriverName=" + secDriverName + ", cleanerName=" + cleanerName
				+ ", firsDriverLastName=" + firsDriverLastName + ", secDriverLastName=" + secDriverLastName
				+ ", cleanerLastName=" + cleanerLastName + ", createdOnStr=" + createdOnStr + ", lastModifiedStr="
				+ lastModifiedStr + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy + ", routeName="
				+ routeName + ", vehicleGrpName=" + vehicleGrpName + ", vehicle_ExpectedOdameter="
				+ vehicle_ExpectedOdameter + ", quantity=" + quantity + ", usedQuantity=" + usedQuantity
				+ ", stockQuantity=" + stockQuantity + ", ureaInvoiceNumber=" + ureaInvoiceNumber
				+ ", manufacturerName=" + manufacturerName + "]";
	}

	public Double getTotalOdometer() {
		return totalOdometer;
	}

	public void setTotalOdometer(Double totalOdometer) {
		this.totalOdometer = Utility.round(totalOdometer, 2) ;
	}

	public String getUreaEntriesNumberStr() {
		return ureaEntriesNumberStr;
	}

	public void setUreaEntriesNumberStr(String ureaEntriesNumberStr) {
		this.ureaEntriesNumberStr = ureaEntriesNumberStr;
	}

	public Double getKmpl() {
		return kmpl;
	}

	public boolean isMeterWorkingStatus() {
		return meterWorkingStatus;
	}

	public void setMeterWorkingStatus(boolean meterWorkingStatus) {
		this.meterWorkingStatus = meterWorkingStatus;
	}

	public void setKmpl(Double kmpl) {
		this.kmpl = Utility.round(kmpl, 2);
	}

	public Integer getFilledLocationId() {
		return filledLocationId;
	}

	public void setFilledLocationId(Integer filledLocationId) {
		this.filledLocationId = filledLocationId;
	}

	public String getFilledLocation() {
		return filledLocation;
	}

	public void setFilledLocation(String filledLocation) {
		this.filledLocation = filledLocation;
	}

	public String getFilledBy() {
		return filledBy;
	}

	public void setFilledBy(String filledBy) {
		this.filledBy = filledBy;
	}
	
	
	
}

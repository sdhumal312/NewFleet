package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class BusBookingDetailsDto {
	
	private Long	busBookingDetailsId;
	private Long	busBookingNumber;
	private String	bookingRefNumber;
	private Date	busBookingDate;
	private String	nameOfParty;
	private String	partyGSTNo;
	private Long	partyId;
	private String	partyMobileNumber;
	private String  partyAddress;
	private String	reportToName;
	private String	reportToMobileNumber;
	private Long	orderedById;
	private String	billingAddress;
	private Long	vehicleTypeId;
	private Date	tripStartDateTime;
	private Date	tripEndDateTime;
	private String	placeOfVisit;
	private String	pickUpAddress;
	private String	dropAddress;
	private Double	rate;
	private Double	hireAmount;
	private String 	remark;
	private Long	createdById;
	private Long	lastModifiedById;
	private Date	createdOn;
	private Date	lastModifiedOn;
	private Integer	companyId;
	private boolean	markForDelete;
	private Long	tripSheetId;
	private String  corporateName;
	private String  vehicle_registration;
	private String	tripStartDateStr;
	private String  tripEndDateStr;
	private String  bookingDateStr;
	private String	vehicleType;
	private Long	tripSheetNumber;
	private String	createdOnStr;
	private String	lastModifiedOnStr;
	private String  createdBy;
	private String	lastModifiedBy;
	private String fromDate;
	private String toDate;
	private Long 	userId;
	private Long dateWiseCount;

	public Long getBusBookingDetailsId() {
		return busBookingDetailsId;
	}

	public void setBusBookingDetailsId(Long busBookingDetailsId) {
		this.busBookingDetailsId = busBookingDetailsId;
	}

	public Long getBusBookingNumber() {
		return busBookingNumber;
	}

	public void setBusBookingNumber(Long busBookingNumber) {
		this.busBookingNumber = busBookingNumber;
	}


	public Date getBusBookingDate() {
		return busBookingDate;
	}

	public void setBusBookingDate(Date busBookingDate) {
		this.busBookingDate = busBookingDate;
	}

	public String getNameOfParty() {
		return nameOfParty;
	}

	public void setNameOfParty(String nameOfParty) {
		this.nameOfParty = nameOfParty;
	}

	public String getPartyGSTNo() {
		return partyGSTNo;
	}

	public void setPartyGSTNo(String partyGSTNo) {
		this.partyGSTNo = partyGSTNo;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	
	public String getPartyMobileNumber() {
		return partyMobileNumber;
	}

	public void setPartyMobileNumber(String partyMobileNumber) {
		this.partyMobileNumber = partyMobileNumber;
	}

	

	public Long getOrderedById() {
		return orderedById;
	}

	public void setOrderedById(Long orderedById) {
		this.orderedById = orderedById;
	}

	public String getReportToMobileNumber() {
		return reportToMobileNumber;
	}

	public void setReportToMobileNumber(String reportToMobileNumber) {
		this.reportToMobileNumber = reportToMobileNumber;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}


	public String getPlaceOfVisit() {
		return placeOfVisit;
	}

	public void setPlaceOfVisit(String placeOfVisit) {
		this.placeOfVisit = placeOfVisit;
	}

	public String getPickUpAddress() {
		return pickUpAddress;
	}

	public void setPickUpAddress(String pickUpAddress) {
		this.pickUpAddress = pickUpAddress;
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

	public Date getTripStartDateTime() {
		return tripStartDateTime;
	}

	public void setTripStartDateTime(Date tripStartDateTime) {
		this.tripStartDateTime = tripStartDateTime;
	}

	public Date getTripEndDateTime() {
		return tripEndDateTime;
	}

	public void setTripEndDateTime(Date tripEndDateTime) {
		this.tripEndDateTime = tripEndDateTime;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
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

	public String getBookingRefNumber() {
		return bookingRefNumber;
	}

	public void setBookingRefNumber(String bookingRefNumber) {
		this.bookingRefNumber = bookingRefNumber;
	}

	public String getPartyAddress() {
		return partyAddress;
	}

	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}

	public String getReportToName() {
		return reportToName;
	}

	public void setReportToName(String reportToName) {
		this.reportToName = reportToName;
	}

	public String getDropAddress() {
		return dropAddress;
	}

	public void setDropAddress(String dropAddress) {
		this.dropAddress = dropAddress;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = Utility.round(rate, 2);
	}

	public Double getHireAmount() {
		return hireAmount;
	}

	public void setHireAmount(Double hireAmount) {
		this.hireAmount = Utility.round(hireAmount, 2);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getTripStartDateStr() {
		return tripStartDateStr;
	}

	public void setTripStartDateStr(String tripStartDateStr) {
		this.tripStartDateStr = tripStartDateStr;
	}

	public String getTripEndDateStr() {
		return tripEndDateStr;
	}

	public void setTripEndDateStr(String tripEndDateStr) {
		this.tripEndDateStr = tripEndDateStr;
	}

	public String getBookingDateStr() {
		return bookingDateStr;
	}

	public void setBookingDateStr(String bookingDateStr) {
		this.bookingDateStr = bookingDateStr;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public String getCreatedOnStr() {
		return createdOnStr;
	}

	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}

	public String getLastModifiedOnStr() {
		return lastModifiedOnStr;
	}

	public void setLastModifiedOnStr(String lastModifiedOnStr) {
		this.lastModifiedOnStr = lastModifiedOnStr;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDateWiseCount() {
		return dateWiseCount;
	}

	public void setDateWiseCount(Long dateWiseCount) {
		this.dateWiseCount = dateWiseCount;
	}

	@Override
	public String toString() {
		return "BusBookingDetails [busBookingDetailsId=" + busBookingDetailsId + ", busBookingNumber="
				+ busBookingNumber + ", bookingRefNumber=" + bookingRefNumber + ", busBookingDate=" + busBookingDate
				+ ", nameOfParty=" + nameOfParty + ", partyGSTNo=" + partyGSTNo + ", partyId=" + partyId
				+ ", partyMobileNumber=" + partyMobileNumber + ", partyAddress=" + partyAddress + ", reportToName="
				+ reportToName + ", reportToMobileNumber=" + reportToMobileNumber + ", orderedById=" + orderedById
				+ ", billingAddress=" + billingAddress + ", vehicleTypeId=" + vehicleTypeId + ", vehicleType=" + vehicleType + ",  tripStartDateTime="
				+ tripStartDateTime + ", tripEndDateTime=" + tripEndDateTime + ", placeOfVisit=" + placeOfVisit
				+ ", pickUpAddress=" + pickUpAddress + ", dropAddress=" + dropAddress + ", rate=" + rate
				+ ", hireAmount=" + hireAmount + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", createdOn=" + createdOn + ", lastModifiedOn=" + lastModifiedOn + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + "]";
	}

	

}

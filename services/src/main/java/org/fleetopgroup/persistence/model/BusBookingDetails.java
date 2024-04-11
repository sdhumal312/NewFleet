package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BusBookingDetails")
public class BusBookingDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="busBookingDetailsId")
	private Long	busBookingDetailsId;
	
	@Column(name="busBookingNumber")
	private Long		busBookingNumber;
	
	@Column(name="bookingRefNumber")
	private String	bookingRefNumber;
	
	@Column(name="busBookingDate")
	private Date	busBookingDate;
	
	@Column(name="nameOfParty")
	private String		nameOfParty;
	
	@Column(name="partyGSTNo")
	private String		partyGSTNo;
	
	@Column(name="partyId")
	private Long	partyId;
	
	@Column(name="partyMobileNumber")
	private String		partyMobileNumber;
	
	@Column(name="partyAddress")
	private String partyAddress;
	
	@Column(name="reportToName")
	private String		reportToName;
	
	@Column(name="reportToMobileNumber")
	private String		reportToMobileNumber;
	
	@Column(name="billingAddress")
	private String		billingAddress;
	
	@Column(name="vehicleTypeId")
	private Long		vehicleTypeId;
	
	@Column(name="tripStartDateTime")
	private Date	tripStartDateTime;
	
	@Column(name="tripEndDateTime")
	private Date	tripEndDateTime;
	
	@Column(name="placeOfVisit")
	private String		placeOfVisit;
	
	@Column(name="pickUpAddress")
	private String		pickUpAddress;
	
	@Column(name="dropAddress")
	private String	dropAddress;
	
	@Column(name="rate")
	private Double	rate;
	
	@Column(name="hireAmount")
	private Double	hireAmount;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="bookedBy")
	private String bookedBy;
	
	@Column(name="createdById", updatable = false)
	private Long		createdById;
	
	@Column(name="lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name="createdOn" , updatable = false)
	private Date	createdOn;
	
	@Column(name="lastModifiedOn")
	private Date	lastModifiedOn;
	
	@Column(name="companyId")
	private Integer		companyId;
	
	@Column(name="markForDelete")
	private boolean		markForDelete;
	
	@Column(name="tripSheetId")
	private Long	tripSheetId;
	

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
		this.rate = rate;
	}

	public Double getHireAmount() {
		return hireAmount;
	}

	public void setHireAmount(Double hireAmount) {
		this.hireAmount = hireAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBookedBy() {
		return bookedBy;
	}

	public void setBookedBy(String bookedBy) {
		this.bookedBy = bookedBy;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((busBookingDate == null) ? 0 : busBookingDate.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((nameOfParty == null) ? 0 : nameOfParty.hashCode());
		result = prime * result + ((tripEndDateTime == null) ? 0 : tripEndDateTime.hashCode());
		result = prime * result + ((tripStartDateTime == null) ? 0 : tripStartDateTime.hashCode());
		result = prime * result + ((vehicleTypeId == null) ? 0 : vehicleTypeId.hashCode());
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
		BusBookingDetails other = (BusBookingDetails) obj;
		if (busBookingDate == null) {
			if (other.busBookingDate != null)
				return false;
		} else if (!busBookingDate.equals(other.busBookingDate))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (nameOfParty == null) {
			if (other.nameOfParty != null)
				return false;
		} else if (!nameOfParty.equals(other.nameOfParty))
			return false;
		if (tripEndDateTime == null) {
			if (other.tripEndDateTime != null)
				return false;
		} else if (!tripEndDateTime.equals(other.tripEndDateTime))
			return false;
		if (tripStartDateTime == null) {
			if (other.tripStartDateTime != null)
				return false;
		} else if (!tripStartDateTime.equals(other.tripStartDateTime))
			return false;
		if (vehicleTypeId == null) {
			if (other.vehicleTypeId != null)
				return false;
		} else if (!vehicleTypeId.equals(other.vehicleTypeId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BusBookingDetails [busBookingDetailsId=" + busBookingDetailsId + ", busBookingNumber="
				+ busBookingNumber + ", bookingRefNumber=" + bookingRefNumber + ", busBookingDate=" + busBookingDate
				+ ", nameOfParty=" + nameOfParty + ", partyGSTNo=" + partyGSTNo + ", partyId=" + partyId
				+ ", partyMobileNumber=" + partyMobileNumber + ", partyAddress=" + partyAddress + ", reportToName="
				+ reportToName + ", reportToMobileNumber=" + reportToMobileNumber + ""
				+ ", billingAddress=" + billingAddress + ", vehicleTypeId=" + vehicleTypeId + ", tripStartDateTime="
				+ tripStartDateTime + ", tripEndDateTime=" + tripEndDateTime + ", placeOfVisit=" + placeOfVisit
				+ ", pickUpAddress=" + pickUpAddress + ", dropAddress=" + dropAddress + ", rate=" + rate
				+ ", hireAmount=" + hireAmount + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", createdOn=" + createdOn + ", lastModifiedOn=" + lastModifiedOn + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + "]";
	}

	
}

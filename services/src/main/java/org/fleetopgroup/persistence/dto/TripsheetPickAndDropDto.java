package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;


public class TripsheetPickAndDropDto {
	
	private Long tripsheetPickAndDropId;
	
	private Long tripSheetNumber;

	private Integer vid;
	
	private String journeyDateStr;
	
	private String journeyDateStr2;
	
	private String journeyTimeStr;
	
	private int tripFristDriverID;
	
	private Integer vendorId;
	
	private short pickOrDropStatus;
	
	private String pickOrDropStatusStr;
	
	private Long pickOrDropId;
	
	private Integer tripUsageKM;
	
	private Double tripTotalAdvance;
	
	private String remark;
	
	private Date	creationDate;
	
	private Date	lastUpdatedOn;
	
	private Long	createdById;
	
	private Long	lastUpdatedBy;
	
	private Integer	companyId;
	
	private boolean markForDelete;
	
	private String vehicleRegistration;
	
	private String driverName;
	
	private String driverMobileNumber;
	
	private String vendorName;
	
	private String locationName;
	
	private double rate;
	
	private double amount;
	
	private short invoiceStatus;
	
	private Long invoiceNumber;
	
	private String	address;
	
	private String	mobileNumber;
	
	private String	gstNumber;
	
	private String invoiceDateStr;
	
	private String newVendorName;
	
	private String newPickOrDropLocationName;
	
	public TripsheetPickAndDropDto() {
		super();
	}

	public TripsheetPickAndDropDto(Long tripsheetPickAndDropId, Long tripSheetNumber, Integer vid,
			String journeyDateStr, String journeyTimeStr, int tripFristDriverID, Integer vendorId,
			short pickOrDropStatus, Long pickOrDropId, Integer tripUsageKM, Double tripTotalAdvance, String remark,
			Date creationDate, Date lastUpdatedOn, Long createdById, Long lastUpdatedBy, Integer companyId,
			boolean markForDelete) {
		super();
		this.tripsheetPickAndDropId = tripsheetPickAndDropId;
		this.tripSheetNumber = tripSheetNumber;
		this.vid = vid;
		this.journeyDateStr = journeyDateStr;
		this.journeyTimeStr = journeyTimeStr;
		this.tripFristDriverID = tripFristDriverID;
		this.vendorId = vendorId;
		this.pickOrDropStatus = pickOrDropStatus;
		this.pickOrDropId = pickOrDropId;
		this.tripUsageKM = tripUsageKM;
		this.tripTotalAdvance = tripTotalAdvance;
		this.remark = remark;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getTripsheetPickAndDropId() {
		return tripsheetPickAndDropId;
	}

	public void setTripsheetPickAndDropId(Long tripsheetPickAndDropId) {
		this.tripsheetPickAndDropId = tripsheetPickAndDropId;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getJourneyDateStr() {
		return journeyDateStr;
	}

	public void setJourneyDateStr(String journeyDateStr) {
		this.journeyDateStr = journeyDateStr;
	}

	public String getJourneyTimeStr() {
		return journeyTimeStr;
	}

	public void setJourneyTimeStr(String journeyTimeStr) {
		this.journeyTimeStr = journeyTimeStr;
	}

	public int getTripFristDriverID() {
		return tripFristDriverID;
	}

	public void setTripFristDriverID(int tripFristDriverID) {
		this.tripFristDriverID = tripFristDriverID;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public short getPickOrDropStatus() {
		return pickOrDropStatus;
	}

	public void setPickOrDropStatus(short pickOrDropStatus) {
		this.pickOrDropStatus = pickOrDropStatus;
	}

	public Long getPickOrDropId() {
		return pickOrDropId;
	}

	public void setPickOrDropId(Long pickOrDropId) {
		this.pickOrDropId = pickOrDropId;
	}

	public Integer getTripUsageKM() {
		return tripUsageKM;
	}

	public void setTripUsageKM(Integer tripUsageKM) {
		this.tripUsageKM = tripUsageKM;
	}

	public Double getTripTotalAdvance() {
		return tripTotalAdvance;
	}

	public void setTripTotalAdvance(Double tripTotalAdvance) {
		this.tripTotalAdvance = Utility.round(tripTotalAdvance, 2) ;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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

	public String getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(String vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverMobileNumber() {
		return driverMobileNumber;
	}

	public void setDriverMobileNumber(String driverMobileNumber) {
		this.driverMobileNumber = driverMobileNumber;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = Utility.round(rate, 2) ;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = Utility.round( amount, 2);
	}

	public String getPickOrDropStatusStr() {
		return pickOrDropStatusStr;
	}

	public void setPickOrDropStatusStr(String pickOrDropStatusStr) {
		this.pickOrDropStatusStr = pickOrDropStatusStr;
	}

	public String getJourneyDateStr2() {
		return journeyDateStr2;
	}

	public void setJourneyDateStr2(String journeyDateStr2) {
		this.journeyDateStr2 = journeyDateStr2;
	}

	public short getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(short invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		this.invoiceDateStr = invoiceDateStr;
	}

	public String getNewVendorName() {
		return newVendorName;
	}

	public void setNewVendorName(String newVendorName) {
		this.newVendorName = newVendorName;
	}

	public String getNewPickOrDropLocationName() {
		return newPickOrDropLocationName;
	}

	public void setNewPickOrDropLocationName(String newPickOrDropLocationName) {
		this.newPickOrDropLocationName = newPickOrDropLocationName;
	}

	@Override
	public String toString() {
		return "TripsheetPickAndDropDto [tripsheetPickAndDropId=" + tripsheetPickAndDropId + ", tripSheetNumber="
				+ tripSheetNumber + ", vid=" + vid + ", journeyDateStr=" + journeyDateStr + ", journeyDateStr2="
				+ journeyDateStr2 + ", journeyTimeStr=" + journeyTimeStr + ", tripFristDriverID=" + tripFristDriverID
				+ ", vendorId=" + vendorId + ", pickOrDropStatus=" + pickOrDropStatus + ", pickOrDropStatusStr="
				+ pickOrDropStatusStr + ", pickOrDropId=" + pickOrDropId + ", tripUsageKM=" + tripUsageKM
				+ ", tripTotalAdvance=" + tripTotalAdvance + ", remark=" + remark + ", creationDate=" + creationDate
				+ ", lastUpdatedOn=" + lastUpdatedOn + ", createdById=" + createdById + ", lastUpdatedBy="
				+ lastUpdatedBy + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", vehicleRegistration=" + vehicleRegistration + ", driverName=" + driverName
				+ ", driverMobileNumber=" + driverMobileNumber + ", vendorName=" + vendorName + ", locationName="
				+ locationName + ", rate=" + rate + ", amount=" + amount + ", invoiceStatus=" + invoiceStatus
				+ ", invoiceNumber=" + invoiceNumber + ", address=" + address + ", mobileNumber=" + mobileNumber
				+ ", gstNumber=" + gstNumber + ", invoiceDateStr=" + invoiceDateStr + ", newVendorName=" + newVendorName
				+ ", newPickOrDropLocationName=" + newPickOrDropLocationName + "]";
	}
	
	
}
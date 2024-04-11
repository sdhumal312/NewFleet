package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class LoadingSheetToTripSheetDto {

	public static final String		LOADINGSHEET_TO_TRIPSHEET			= "loadingSheetToTripSheet";
	public static final String		VEHICLE_NUMBER_MASTER_ID			= "vehicleNumberMasterId";
	public static final String		FROM_DATE							= "fromDate";
	public static final String		TO_DATE								= "toDate";
	
	
	public static final short		LOADING_SHEET_CHARGES_TYPE_FREIGHT			= 1;
	public static final short		LOADING_SHEET_CHARGES_TYPE_BOOKING_TOTAL	= 2;
	
	private long		dispatchLedgerId;
	
	private long		wayBillId;
	
	private String		wayBillNumber;
	
	private double		bookingTotal;
	
	private double		freight;
	
	private String		lsNumber;
	
	private	int			totalNoOfPackages;
	
	private long		wayBillCrossingId;
	
	private Timestamp	tripDateTime;
	
	private String		tripDateTimeStr;
	
	private Long		tripSheetId;
	
	private short		waybillTypeId;
	
	private String		waybillType;
	
	private Double	totalActualWeight;
	
	private Double	dispatchedWeight;

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public short getWaybillTypeId() {
		return waybillTypeId;
	}

	public void setWaybillTypeId(short waybillTypeId) {
		this.waybillTypeId = waybillTypeId;
	}

	public long getDispatchLedgerId() {
		return dispatchLedgerId;
	}

	public void setDispatchLedgerId(long dispatchLedgerId) {
		this.dispatchLedgerId = dispatchLedgerId;
	}

	public long getWayBillId() {
		return wayBillId;
	}

	public void setWayBillId(long wayBillId) {
		this.wayBillId = wayBillId;
	}

	public String getWayBillNumber() {
		return wayBillNumber;
	}

	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}

	public double getBookingTotal() {
		return bookingTotal;
	}

	public void setBookingTotal(double bookingTotal) {
		this.bookingTotal = Utility.round(bookingTotal,2);
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = Utility.round(freight, 2);
	}

	public String getLsNumber() {
		return lsNumber;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public Double getTotalActualWeight() {
		return totalActualWeight;
	}

	public void setTotalActualWeight(Double totalActualWeight) {
		this.totalActualWeight = totalActualWeight;
	}

	public Double getDispatchedWeight() {
		return dispatchedWeight;
	}

	public void setDispatchedWeight(Double dispatchedWeight) {
		this.dispatchedWeight = dispatchedWeight;
	}

	public void setLsNumber(String lsNumber) {
		this.lsNumber = lsNumber;
	}

	public int getTotalNoOfPackages() {
		return totalNoOfPackages;
	}

	public void setTotalNoOfPackages(int totalNoOfPackages) {
		this.totalNoOfPackages = totalNoOfPackages;
	}

	public long getWayBillCrossingId() {
		return wayBillCrossingId;
	}

	public void setWayBillCrossingId(long wayBillCrossingId) {
		this.wayBillCrossingId = wayBillCrossingId;
	}

	public Timestamp getTripDateTime() {
		return tripDateTime;
	}

	public void setTripDateTime(Timestamp tripDateTime) {
		this.tripDateTime = tripDateTime;
	}

	public String getTripDateTimeStr() {
		return tripDateTimeStr;
	}

	public void setTripDateTimeStr(String tripDateTimeStr) {
		this.tripDateTimeStr = tripDateTimeStr;
	}

	@Override
	public String toString() {
		return "LoadingSheetToTripSheetDto [dispatchLedgerId=" + dispatchLedgerId + ", wayBillId=" + wayBillId
				+ ", wayBillNumber=" + wayBillNumber + ", bookingTotal=" + bookingTotal + ", freight=" + freight
				+ ", lsNumber=" + lsNumber + ", totalNoOfPackages=" + totalNoOfPackages + ", wayBillCrossingId="
				+ wayBillCrossingId + ", tripDateTime=" + tripDateTime + ", tripDateTimeStr=" + tripDateTimeStr + "]";
	}
	
	
}

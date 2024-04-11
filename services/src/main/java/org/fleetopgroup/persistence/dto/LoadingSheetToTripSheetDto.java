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
	
	private Double	    totalActualWeight;
	
	private Double	     dispatchedWeight;
	
	private Long	    lrSourceBranchId;
	
	private Long		lrDestinationBranchId;
	
	private Long		lsSourceBranchId;
	
	private Long		lsDestinationBranchId;
	
	private String		lrSourceBranch;
	
	private String		lsSourceBranch;
	
	private String		lsDestinationBranch;
	
	private String		lrDestinationBranch;
	
	private Double     tripIncome;

	public LoadingSheetToTripSheetDto(long dispatchLedgerId, long wayBillId, String wayBillNumber, double bookingTotal,
			double freight, String lsNumber, int totalNoOfPackages, long wayBillCrossingId, Timestamp tripDateTime,
			String tripDateTimeStr, Long tripSheetId, short waybillTypeId, String waybillType, Double totalActualWeight,
			Double dispatchedWeight, Long lrSourceBranchId, Long lrDestinationBranchId, Long lsSourceBranchId,
			Long lsDestinationBranchId, String lrSourceBranch, String lsSourceBranch, String lsDestinationBranch,
			String lrDestinationBranch, Double tripIncome) {
		super();
		this.dispatchLedgerId = dispatchLedgerId;
		this.wayBillId = wayBillId;
		this.wayBillNumber = wayBillNumber;
		this.bookingTotal = bookingTotal;
		this.freight = freight;
		this.lsNumber = lsNumber;
		this.totalNoOfPackages = totalNoOfPackages;
		this.wayBillCrossingId = wayBillCrossingId;
		this.tripDateTime = tripDateTime;
		this.tripDateTimeStr = tripDateTimeStr;
		this.tripSheetId = tripSheetId;
		this.waybillTypeId = waybillTypeId;
		this.waybillType = waybillType;
		this.totalActualWeight = totalActualWeight;
		this.dispatchedWeight = dispatchedWeight;
		this.lrSourceBranchId = lrSourceBranchId;
		this.lrDestinationBranchId = lrDestinationBranchId;
		this.lsSourceBranchId = lsSourceBranchId;
		this.lsDestinationBranchId = lsDestinationBranchId;
		this.lrSourceBranch = lrSourceBranch;
		this.lsSourceBranch = lsSourceBranch;
		this.lsDestinationBranch = lsDestinationBranch;
		this.lrDestinationBranch = lrDestinationBranch;
		this.tripIncome = tripIncome;
	}

	public LoadingSheetToTripSheetDto() {
		super();
		// TODO Auto-generated constructor stub
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
		this.bookingTotal = bookingTotal;
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	public String getLsNumber() {
		return lsNumber;
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

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public short getWaybillTypeId() {
		return waybillTypeId;
	}

	public void setWaybillTypeId(short waybillTypeId) {
		this.waybillTypeId = waybillTypeId;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
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

	public Long getLrSourceBranchId() {
		return lrSourceBranchId;
	}

	public void setLrSourceBranchId(Long lrSourceBranchId) {
		this.lrSourceBranchId = lrSourceBranchId;
	}

	public Long getLrDestinationBranchId() {
		return lrDestinationBranchId;
	}

	public void setLrDestinationBranchId(Long lrDestinationBranchId) {
		this.lrDestinationBranchId = lrDestinationBranchId;
	}

	public Long getLsSourceBranchId() {
		return lsSourceBranchId;
	}

	public void setLsSourceBranchId(Long lsSourceBranchId) {
		this.lsSourceBranchId = lsSourceBranchId;
	}

	public Long getLsDestinationBranchId() {
		return lsDestinationBranchId;
	}

	public void setLsDestinationBranchId(Long lsDestinationBranchId) {
		this.lsDestinationBranchId = lsDestinationBranchId;
	}

	public String getLrSourceBranch() {
		return lrSourceBranch;
	}

	public void setLrSourceBranch(String lrSourceBranch) {
		this.lrSourceBranch = lrSourceBranch;
	}

	public String getLsSourceBranch() {
		return lsSourceBranch;
	}

	public void setLsSourceBranch(String lsSourceBranch) {
		this.lsSourceBranch = lsSourceBranch;
	}

	public String getLsDestinationBranch() {
		return lsDestinationBranch;
	}

	public void setLsDestinationBranch(String lsDestinationBranch) {
		this.lsDestinationBranch = lsDestinationBranch;
	}

	public String getLrDestinationBranch() {
		return lrDestinationBranch;
	}

	public void setLrDestinationBranch(String lrDestinationBranch) {
		this.lrDestinationBranch = lrDestinationBranch;
	}

	public Double getTripIncome() {
		return tripIncome;
	}

	public void setTripIncome(Double tripIncome) {
		this.tripIncome = tripIncome;
	}

	public static String getLoadingsheetToTripsheet() {
		return LOADINGSHEET_TO_TRIPSHEET;
	}

	public static String getVehicleNumberMasterId() {
		return VEHICLE_NUMBER_MASTER_ID;
	}

	public static String getFromDate() {
		return FROM_DATE;
	}

	public static String getToDate() {
		return TO_DATE;
	}

	public static short getLoadingSheetChargesTypeFreight() {
		return LOADING_SHEET_CHARGES_TYPE_FREIGHT;
	}

	public static short getLoadingSheetChargesTypeBookingTotal() {
		return LOADING_SHEET_CHARGES_TYPE_BOOKING_TOTAL;
	}

	@Override
	public String toString() {
		return "LoadingSheetToTripSheetDto [dispatchLedgerId=" + dispatchLedgerId + ", wayBillId=" + wayBillId
				+ ", wayBillNumber=" + wayBillNumber + ", bookingTotal=" + bookingTotal + ", freight=" + freight
				+ ", lsNumber=" + lsNumber + ", totalNoOfPackages=" + totalNoOfPackages + ", wayBillCrossingId="
				+ wayBillCrossingId + ", tripDateTime=" + tripDateTime + ", tripDateTimeStr=" + tripDateTimeStr
				+ ", tripSheetId=" + tripSheetId + ", waybillTypeId=" + waybillTypeId + ", waybillType=" + waybillType
				+ ", totalActualWeight=" + totalActualWeight + ", dispatchedWeight=" + dispatchedWeight
				+ ", lrSourceBranchId=" + lrSourceBranchId + ", lrDestinationBranchId=" + lrDestinationBranchId
				+ ", lsSourceBranchId=" + lsSourceBranchId + ", lsDestinationBranchId=" + lsDestinationBranchId
				+ ", lrSourceBranch=" + lrSourceBranch + ", lsSourceBranch=" + lsSourceBranch + ", lsDestinationBranch="
				+ lsDestinationBranch + ", lrDestinationBranch=" + lrDestinationBranch + ", tripIncome=" + tripIncome
				+ "]";
	}
	
	
}

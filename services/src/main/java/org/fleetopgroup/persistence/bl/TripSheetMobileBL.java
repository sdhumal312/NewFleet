package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.DriverHaltPlaceType;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.persistence.model.DriverHalt;
import org.fleetopgroup.persistence.model.RouteWiseTripSheetWeight;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetAdvance;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetHistory;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.web.util.DateDiffrentConvert;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.web.multipart.MultipartFile;

public class TripSheetMobileBL {
	
	public TripSheetMobileBL() {
	}

	SimpleDateFormat dateFormat 					= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_Name 				= new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat driverAttendanceFormat		    = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat tripadvaTime 					= new SimpleDateFormat("hh:mm a");
	SimpleDateFormat date_tripadvaTime 				= new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
	SimpleDateFormat dateFormatonlyDateTime 		= new SimpleDateFormat("EEE, d MMM yyyy");
	SimpleDateFormat CreatedDateTime 				= new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	
	public static final long 	TRIP_SHEET_DEFALAT_VALUE 		= 0L;
	public static final Integer TRIP_SHEET_DEFALAT_ZERO	 		= 0;
	public static final Double  TRIP_SHEET_DEFALAT_AMOUNT 		= 0.0;

	DateDiffrentConvert dataconvert = new DateDiffrentConvert();

	public static String REPLACE_STRING = "@srstravels.xyz";

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	public TripSheet saveOrDispatchTripsheet(ValueObject object,MultipartFile file) throws Exception {
			TripSheet 			tripSheet 			= new TripSheet();
			
			tripSheet.setVid(object.getInt("vid"));
			tripSheet.setTripOpeningKM(object.getInt("openOdoMeter"));
			tripSheet.setLoadTypeId(object.getShort(("loadTypeId")));
			tripSheet.setTripOpenDate(dateFormat.parse(object.getString("fromDate")));
			tripSheet.setClosetripDate(dateFormat.parse(object.getString("toDate")));
			tripSheet.setIpAddress(object.getString("ipAddress", null));
			if(object.getLong("tripsheetId",0) > 0) {
				tripSheet.setTripSheetID(object.getLong("tripsheetId"));
			}
			
			if (object.getInt("routeServiceId",0) != TRIP_SHEET_DEFALAT_ZERO && object.getInt("newRouteId", 0) == TRIP_SHEET_DEFALAT_ZERO) {    
				tripSheet.setRouteID(object.getInt("routeServiceId"));
			} else {
				tripSheet.setRouteID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setRouteName(object.getString("newRouteName"));
			}
			tripSheet.setSubRoute(object.getString("subRoute", null));
			

			if (object.getInt("driverId",0) != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripFristDriverID(object.getInt("driverId"));
				tripSheet.setTripFristDriverBata(TRIP_SHEET_DEFALAT_AMOUNT);
			} else {
				tripSheet.setTripFristDriverID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setTripFristDriverBata(TRIP_SHEET_DEFALAT_AMOUNT);
			}
			
			if (object.getInt("driver2Id",0) != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripSecDriverID(object.getInt("driver2Id"));
				tripSheet.setTripSecDriverBata(TRIP_SHEET_DEFALAT_AMOUNT);
			} else {
				tripSheet.setTripSecDriverID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setTripSecDriverBata(TRIP_SHEET_DEFALAT_AMOUNT);
			}
			
			if (object.getInt("cleanerId",0) != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripCleanerID(object.getInt("cleanerId"));
				tripSheet.setTripCleanerBata(TRIP_SHEET_DEFALAT_AMOUNT);
			} else {
				tripSheet.setTripCleanerID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setTripCleanerBata(TRIP_SHEET_DEFALAT_AMOUNT);
			}
			
			tripSheet.setTripStutesId(object.getShort(("tripstatusId"))); 
			tripSheet.setTripBookref(object.getString("bookingReference", null));
			tripSheet.setTripCommentTotal(TRIP_SHEET_DEFALAT_ZERO);

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp createdDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			tripSheet.setCreated(createdDate);
			tripSheet.setLastupdated(createdDate);
			//tripSheet.setIpAddress(Utility.getClientIpAddr(request));
			tripSheet.setTripGpsOpeningKM(object.getDouble("gpsOpenOdoMeter",0));
			
			if(object.getShort("tripstatusId") == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
				tripSheet.setGpsOpeningLocation(object.getString("gpsLocation", null));
			}
			if (object.getString("backDateDispatchTime") != null && !object.getString("backDateDispatchTime").trim().equalsIgnoreCase("")) {
				
				String start_time = "00:00";
				if(object.getString("backDateDispatchTime") != null && object.getString("backDateDispatchTime") != "") {
					start_time	= object.getString("backDateDispatchTime");
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(object.getString("fromDate"), start_time);
				java.sql.Date Reported_Date = new java.sql.Date(date.getTime());
				tripSheet.setDispatchedByTime(Reported_Date);
			}
			
			if(object.getString("numOfPod") != null && !object.getString("numOfPod").trim().equalsIgnoreCase("")) {
				tripSheet.setNoOfPOD(Integer.parseInt(object.getString("numOfPod")));
			} else {
				tripSheet.setNoOfPOD(0);
			}
			
			tripSheet.setTripStartDiesel(object.getDouble("tripStartDiesel", 0));
			tripSheet.setReceivedPOD(0);
			tripSheet.setMeterNotWorking(object.getBoolean("meterNotWorking", false));			
			if(file != null) {
				if (!file.isEmpty()) {
					tripSheet.setTripSheetDocument(true);
				} else {
					tripSheet.setTripSheetDocument(false);
				}
			} else {
				if (object.getString("base64String",null)  != null) {
					tripSheet.setTripSheetDocument(true);
				} else {
					tripSheet.setTripSheetDocument(false);
				}
			}
			System.err.println("lhpvId --"+ object.getInt("lhpvId",0));
			
			tripSheet.setLhpvId(object.getInt("lhpvId",0));
			
			return tripSheet;
		
	}
	
	public TripSheetAdvance saveOrDispatchTripsheetAdvance(ValueObject object) {
		
		TripSheetAdvance  advance  = new TripSheetAdvance();
		
		try {
			advance.setAdvanceAmount(object.getDouble("advanceAmnt",0));
			advance.setAdvanceRefence(object.getString("advanceRef",null));
			advance.setAdvancePaidbyId(object.getLong("advancePaidBy"));
			advance.setAdvancePlaceId(object.getInt("place"));
			advance.setPaymentTypeId(object.getShort("advancePaymentType",(short)0));
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			advance.setCreated(toDate);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return advance;
	}
	
	public TripSheetAdvance saveTripAdvance(ValueObject object) throws Exception {
		TripSheetAdvance tripAdvance = new TripSheetAdvance();

		tripAdvance.setAdvanceAmount(object.getDouble("advanceAmnt",0));
		tripAdvance.setDriverId(object.getInt("driverAdvanceId",0));
		tripAdvance.setAdvancePlaceId(object.getInt("place"));
		tripAdvance.setAdvancePaidbyId(object.getLong("advancePaidBy"));
		tripAdvance.setAdvanceRefence(object.getString("advanceRef"));
		tripAdvance.setAdvanceRemarks(object.getString("remarks"));
		tripAdvance.setPaymentTypeId(object.getShort("paymetTypeId",(short)0));
		if(object.getString("paidDate") != null)
		tripAdvance.setPaidDate(dateFormat.parse(object.getString("paidDate")));
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		tripAdvance.setCreated(toDate);

		return tripAdvance;
	}
	
	@SuppressWarnings("unchecked")
	public TripSheetExpense saveTripExpense(ValueObject object,MultipartFile file) throws Exception{
		HashMap<String, Object> 		configuration	= null;
		
		try {
			configuration	= (HashMap<String, Object>) object.get("config");
			
			
			TripSheetExpense TripExpense = new TripSheetExpense();
			TripExpense.setExpenseId(object.getInt("expenseNameId"));
			TripExpense.setExpenseAmount(object.getDouble("expenseAmount"));
			TripExpense.setExpensePlaceId(object.getInt("expensePlaceId"));
			TripExpense.setExpenseRefence(object.getString("expenseRef"));
			TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
			TripExpense.setCreatedById(object.getLong("userId"));
			TripExpense.setCompanyId(object.getInt("companyId"));
			
			TripExpense.setCredit(object.getBoolean("Credit",false));
			TripExpense.setVendorId(object.getInt("vendorId", 0));
			TripExpense.setTallyCompanyId(object.getLong("tallyCompanyId",0));
			TripExpense.setRemark(object.getString("description", null));
			TripExpense.setPaymentTypeId(object.getShort("paymetTypeId", (short)0));
			if(object.getString("voucherDateStr", null) != null && !object.getString("voucherDateStr", null).trim().equalsIgnoreCase("")) {
				TripExpense.setVoucherDate(dateFormat.parse(object.getString("voucherDateStr", null)));
			}
			
			TripSheet tsheet = new TripSheet();
			tsheet.setTripSheetID(object.getLong("tripsheetId"));
			TripExpense.setTripsheet(tsheet);
			
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			TripExpense.setCreated(toDate);
			
			if(file != null) {
				if (!file.isEmpty()) {
					TripExpense.setTripSheetExpense_document(true);
				} else {
					TripExpense.setTripSheetExpense_document(false);
				}
			} else {
				if (object.getString("base64String",null)  != null) {
					TripExpense.setTripSheetExpense_document(true);
				} else {
					TripExpense.setTripSheetExpense_document(false);
				}
			}
			if((boolean) configuration.get("showPFAmount")) {
				String[]	dailyAllowanceIdsArr	= ((String)configuration.get("dailyAllowanceIds")).split(",");
				
				List<String>  allowanceIdList = new ArrayList<String>(Arrays.asList(dailyAllowanceIdsArr));
				
				if(allowanceIdList.contains(TripExpense.getExpenseId()+"")) {
					TripExpense.setPfAmount(object.getDouble("pfAmount", 0));
					TripExpense.setEsiAmount(object.getDouble("esiAmount", 0));
					TripExpense.setBalanceAmount(TripExpense.getExpenseAmount() - TripExpense.getPfAmount() - TripExpense.getEsiAmount());
				}else {
					TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());
				}
			}else {
				TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());
			}
			
			return TripExpense;
		} catch (Exception e) {
			throw e;
		}finally {
			configuration	= null;
		}
		
	}
	
	public TripSheetExpense saveTripExpense(ValueObject object, ValueObject	valueInObject, MultipartFile file) throws Exception{
		
		TripSheetExpense TripExpense = new TripSheetExpense();
		TripExpense.setExpenseId(object.getInt("expenseNameId"));
		TripExpense.setExpenseAmount(object.getDouble("expenseAmount"));
		TripExpense.setExpensePlaceId(valueInObject.getInt("expensePlaceId"));
		TripExpense.setExpenseRefence(object.getString("expenseRef"));
		TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
		TripExpense.setCreatedById(valueInObject.getLong("userId"));
		TripExpense.setCompanyId(valueInObject.getInt("companyId"));
		
		TripExpense.setCredit(object.getBoolean("Credit",false));
		TripExpense.setVendorId(object.getInt("vendorId", 0));
		TripExpense.setTallyCompanyId(object.getLong("tallyCompanyId",0));
		TripExpense.setRemark(object.getString("description", null));
		if(object.getString("voucherDateStr", null) != null && !object.getString("voucherDateStr", null).trim().equalsIgnoreCase("")) {
			TripExpense.setVoucherDate(dateFormat.parse(object.getString("voucherDateStr", null)));
		}
		TripExpense.setPaymentTypeId(valueInObject.getShort("paymetTypeId",(short)0));
		TripSheet tsheet = new TripSheet();
		tsheet.setTripSheetID(valueInObject.getLong("tripsheetId"));
		TripExpense.setTripsheet(tsheet);
		
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		TripExpense.setCreated(toDate);
		if(file != null) {
			if (!file.isEmpty()) {
				TripExpense.setTripSheetExpense_document(true);
			} else {
				TripExpense.setTripSheetExpense_document(false);
			}
		} else {
			if (object.getString("base64String",null)  != null) {
				TripExpense.setTripSheetExpense_document(true);
			} else {
				TripExpense.setTripSheetExpense_document(false);
			}
		}
		TripExpense.setDriverId((Integer) object.getInt("driverId"));
		if(valueInObject.getString("paidDate", null) != null && !valueInObject.getString("paidDate", null).trim().equalsIgnoreCase("")) {
			TripExpense.setPaidDate(dateFormat.parse(valueInObject.getString("paidDate", null)));;
		}
		return TripExpense;
	}
	
	public TripSheetIncome saveTripIncome(ValueObject object) throws Exception{
		TripSheetIncome TripIncome = new TripSheetIncome();
		TripIncome.setIncomeId(object.getInt("incomeNameId"));
		TripIncome.setIncomeAmount(object.getDouble("incomeAmount"));
		TripIncome.setIncomePlaceId(object.getInt("incomePlaceId"));
		TripIncome.setIncomeRefence(object.getString("incomeRef"));
		
		TripIncome.setRouteID(object.getInt("routeId",0));
		TripIncome.setNetIncomeAmount(object.getDouble("netAmount",0));
		TripIncome.setRemark(object.getString("remark"));
		TripIncome.setGst(object.getDouble("gst",0));
		TripIncome.setCommission(object.getDouble("commission",0));
		TripIncome.setDriverExcluded(object.getBoolean("driverExcluded"));
		if(object.getString("incomeDate") != null && !object.getString("incomeDate").trim().equalsIgnoreCase("")) {
			TripIncome.setTripsheetIncomeDate(dateFormat.parse(object.getString("incomeDate")));
		}
		
		TripIncome.setIncomeCollectedById(object.getLong("userId"));
		TripIncome.setCompanyId(object.getInt("companyId"));
		TripIncome.setCreatedById(object.getLong("userId"));
		TripIncome.setIncomeFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
		
		TripSheet tsheet = new TripSheet();
		tsheet.setTripSheetID(object.getLong("tripsheetId"));
		TripIncome.setTripsheet(tsheet);
		
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		TripIncome.setCreated(toDate);
		
		return TripIncome;
	}
	
public TripSheetIncome saveTripIncome(ValueObject object, ValueObject multiObject) throws Exception{
		
		TripSheetIncome TripIncome = new TripSheetIncome();
		TripIncome.setIncomeId(multiObject.getInt("incomeNameId"));
		TripIncome.setIncomeAmount(multiObject.getDouble("incomeAmount"));
		TripIncome.setIncomeRefence(multiObject.getString("incomeRef"));
		TripIncome.setRouteID(multiObject.getInt("routeId",0));
		TripIncome.setNetIncomeAmount(multiObject.getDouble("netAmount",0));
		TripIncome.setRemark(multiObject.getString("remark"));
		TripIncome.setGst(multiObject.getDouble("gst",0));
		TripIncome.setCommission(multiObject.getDouble("commission",0));
		TripIncome.setDriverExcluded(multiObject.getBoolean("driverExcluded", false));
		if(multiObject.getString("incomeDate") != null && !multiObject.getString("incomeDate").trim().equalsIgnoreCase("")) {
			TripIncome.setTripsheetIncomeDate(dateFormat.parse(multiObject.getString("incomeDate")));
		}
		
		TripIncome.setIncomePlaceId(object.getInt("incomePlaceId"));
		TripIncome.setIncomeCollectedById(object.getLong("userId"));
		TripIncome.setCompanyId(object.getInt("companyId"));
		TripIncome.setCreatedById(object.getLong("userId"));
		TripIncome.setPaymentTypeId(object.getShort("paymetTypeId",(short)0));
		TripSheet tsheet = new TripSheet();
		tsheet.setTripSheetID(object.getLong("tripsheetId"));
		TripIncome.setTripsheet(tsheet);
		
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		TripIncome.setCreated(toDate);
		
		return TripIncome;
	}
	
	public DriverHalt saveDriverHalt(ValueObject object) throws Exception {
		
		DriverHalt halt = new DriverHalt();

		halt.setREFERENCE_NO(object.getString(""));
		halt.setHALT_PAIDBY_ID(object.getLong("haltPaidById"));
		halt.setHALT_PLACE_TYPE_ID(DriverHaltPlaceType.DRIVERHALT_PALCE_TYPE_TRIPHALT);
		halt.setHALT_PLACE_ID(object.getInt("incomePlaceId"));
		halt.setHALT_REASON(object.getString("haltReason"));

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		halt.setCREATED(toDate);
		halt.setLASTUPDATED(toDate);
		
		halt.setCREATED_BY_ID(object.getLong("userId"));
		halt.setLASTUPDATED_BY_ID(object.getLong("userId"));
		halt.setMarkForDelete(false);
		halt.setCOMPANY_ID(object.getInt("companyId"));
		
		return halt;
	}
	
	public TripSheet updateTripDetails(ValueObject object) throws Exception {
		Timestamp CloseByTime 	= null;
		TripSheet tripSheet 	= new TripSheet();
		TripSheet trip      	= (TripSheet)object.get("trip");

		tripSheet.setTripSheetID(object.getLong("tripsheetId"));
		tripSheet.setVid(object.getInt("vid"));
		tripSheet.setTripSheetNumber(trip.getTripSheetNumber());
		tripSheet.setTripOpeningKM(object.getInt("openOdoMeter",0));
		tripSheet.setTripGpsOpeningKM(object.getDouble("gpsOpenOdoMeter",0));
		tripSheet.setTripOpenDate(dateFormat.parse(object.getString("fromDate")));
		tripSheet.setClosetripDate(dateFormat.parse(object.getString("toDate")));

			if (trip.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripFristDriverID(object.getInt("driverId"));
			}
			
			if (object.getInt("driver2Id",0) != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripSecDriverID(object.getInt("driver2Id"));
			} else {
				tripSheet.setTripSecDriverID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setTripSecDriverBata(TRIP_SHEET_DEFALAT_AMOUNT);
			}
			
			if (object.getInt("cleanerId",0) != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripCleanerID(object.getInt("cleanerId"));
			} else {
				tripSheet.setTripCleanerID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setTripCleanerBata(TRIP_SHEET_DEFALAT_AMOUNT);
			}
			

		if (object.getInt("routeServiceId") != TRIP_SHEET_DEFALAT_ZERO) {    // yet to add new route condition
			tripSheet.setRouteID(object.getInt("routeServiceId"));
		} else {
			tripSheet.setRouteID(TRIP_SHEET_DEFALAT_ZERO);
			tripSheet.setRouteName(object.getString("newRouteName",null));
		}
		tripSheet.setSubRoute(object.getString("subRoute", null));
		
		tripSheet.setTripBookref(object.getString("bookingReference", null));
		tripSheet.setTripCommentTotal(TRIP_SHEET_DEFALAT_ZERO);
		tripSheet.setTripTotalAdvance(trip.getTripTotalAdvance());
		tripSheet.setTripTotalexpense(trip.getTripTotalexpense());
		tripSheet.setTripTotalincome(trip.getTripTotalincome());
		tripSheet.setTripStutesId(object.getShort("tripstatusId"));
		tripSheet.setLoadTypeId(object.getShort("loadTypeId",(short) 0));
		
		if (object.getString("dispatchByTime") != null && !object.getString("dispatchByTime").trim().equalsIgnoreCase("")) {
			String start_time = "00:00";
			if(object.getString("backDateDispatchTime") != null && object.getString("backDateDispatchTime") != "") {
				start_time	= object.getString("backDateDispatchTime");
			}
			java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(object.getString("backDateDispatchDate"), start_time);
			tripSheet.setDispatchedByTime(date);
		}else {
			tripSheet.setDispatchedByTime(new Date());
		}
		
		if (object.getString("tripEndTime") != null && !object.getString("tripEndTime").trim().equalsIgnoreCase("")) {
			String end_time = "00:00";
				end_time	= object.getString("tripEndTime");
				java.util.Date closedate = DateTimeUtility.getDateTimeFromDateTimeString(object.getString("toDate"), end_time);
				CloseByTime = new java.sql.Timestamp(closedate.getTime());
				tripSheet.setClosedByTime(CloseByTime);
		} else {
			tripSheet.setClosedByTime(CloseByTime);
		}
		
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp createdDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		tripSheet.setLastupdated(createdDate);
		tripSheet.setMarkForDelete(false);
		
		if(object.getString("numOfPod") != null && object.getString("numOfPod") != "" && !object.getString("numOfPod").isEmpty()) {
			tripSheet.setNoOfPOD(Integer.parseInt(object.getString("numOfPod")));
		} else {
			tripSheet.setNoOfPOD(0);
		}
		tripSheet.setReceivedPOD(0);
		if(object.getDouble("tripStartDiesel", 0) > 0) {
			tripSheet.setTripStartDiesel(object.getDouble("tripStartDiesel", 0));
		} else {
			tripSheet.setTripStartDiesel(0.0);
		}
		tripSheet.setMeterNotWorking(object.getBoolean("meterNotWorking", false));
		System.err.println("object.getBoolean(\"meterNotWorking\", false)"+object.getBoolean("meterNotWorking", false));

		return tripSheet;
	}
	
	public TripSheetHistory prepareTripSheetHistory(TripSheet tripSheet) throws Exception { 
		TripSheetHistory 	tripSheetHistory	= null;
		try {
			tripSheetHistory = new TripSheetHistory();

			tripSheetHistory.setAcclosedById(tripSheet.getAcclosedById());
			tripSheetHistory.setAcclosedByTime(tripSheet.getAcclosedByTime());
			tripSheetHistory.setAcclosedLocationId(tripSheet.getAcclosedLocationId());
			tripSheetHistory.setCloseACCTripAmount(tripSheet.getCloseACCTripAmount());
			tripSheetHistory.setCloseACCtripDate(tripSheet.getCloseACCtripDate());
			tripSheetHistory.setCloseACCTripNameById(tripSheet.getCloseTripNameById());
			tripSheetHistory.setCloseACCTripReference(tripSheet.getCloseACCTripReference());
			tripSheetHistory.setClosedById(tripSheet.getClosedById());
			tripSheetHistory.setClosedByTime(tripSheet.getClosedByTime());
			tripSheetHistory.setClosedLocationId(tripSheet.getClosedLocationId());
			tripSheetHistory.setCloseTripAmount(tripSheet.getCloseTripAmount());
			tripSheetHistory.setClosetripDate(tripSheet.getClosetripDate());
			tripSheetHistory.setCloseTripNameById(tripSheet.getCloseTripNameById());
			tripSheetHistory.setCloseTripReference(tripSheet.getCloseTripReference());
			tripSheetHistory.setCloseTripStatusId(tripSheet.getCloseTripStatusId());
			tripSheetHistory.setCompanyId(tripSheet.getCompanyId());
			tripSheetHistory.setDispatchedById(tripSheet.getDispatchedById());
			tripSheetHistory.setDispatchedByTime(tripSheet.getDispatchedByTime());
			tripSheetHistory.setDispatchedLocationId(tripSheet.getDispatchedLocationId());
			tripSheetHistory.setIpAddress(tripSheet.getIpAddress());
			tripSheetHistory.setLastupdated(tripSheet.getLastupdated());
			tripSheetHistory.setMarkForDelete(tripSheet.isMarkForDelete());
			tripSheetHistory.setModifiedById(tripSheet.getLastModifiedById());
			tripSheetHistory.setRouteID(tripSheet.getRouteID());
			tripSheetHistory.setTripBookref(tripSheet.getTripBookref());
			tripSheetHistory.setTripCleanerBata(tripSheet.getTripCleanerBata());
			tripSheetHistory.setTripCleanerID(tripSheet.getTripCleanerID());
			tripSheetHistory.setTripClosingKM(tripSheet.getTripClosingKM());
			tripSheetHistory.setTripClosingKMStatusId(tripSheet.getTripClosingKMStatusId());
			tripSheetHistory.setTripCommentTotal(tripSheet.getTripCommentTotal());
			tripSheetHistory.setTripFristDriverBata(tripSheet.getTripFristDriverBata());
			tripSheetHistory.setTripFristDriverID(tripSheet.getTripFristDriverID());
			tripSheetHistory.setTripOpenDate(tripSheet.getTripOpenDate());
			tripSheetHistory.setTripOpeningKM(tripSheet.getTripOpeningKM());
			tripSheetHistory.setTripSecDriverBata(tripSheet.getTripSecDriverBata());
			tripSheetHistory.setTripSecDriverID(tripSheet.getTripSecDriverID());
			tripSheetHistory.setTripSheetID(tripSheet.getTripSheetID());
			tripSheetHistory.setTripStutesId(tripSheet.getTripStutesId());
			tripSheetHistory.setTripTotalAdvance(tripSheet.getTripTotalAdvance());
			tripSheetHistory.setTripTotalexpense(tripSheet.getTripTotalexpense());
			tripSheetHistory.setTripTotalincome(tripSheet.getTripTotalincome());
			tripSheetHistory.setTripUsageKM(tripSheet.getTripUsageKM());
			tripSheetHistory.setVid(tripSheet.getVid());
			tripSheetHistory.setTripSheetNumber(tripSheet.getTripSheetNumber());
			tripSheetHistory.setSubRoute(tripSheet.getSubRoute());
			tripSheetHistory.setRouteName(tripSheet.getRouteName());
			tripSheetHistory.setTripGpsOpeningKM(tripSheet.getTripGpsOpeningKM());
			tripSheetHistory.setTripGpsClosingKM(tripSheet.getTripGpsClosingKM());
			tripSheetHistory.setCreatedById(tripSheet.getCreatedById());
			tripSheetHistory.setCreated(tripSheet.getCreated());
			tripSheetHistory.setGpsOpeningLocation(tripSheet.getGpsOpeningLocation());
			tripSheetHistory.setGpsCloseLocation(tripSheet.getGpsCloseLocation());
			tripSheetHistory.setGpsTripUsageKM(tripSheet.getGpsTripUsageKM());
			tripSheetHistory.setLoadTypeId(tripSheet.getLoadTypeId());
			tripSheetHistory.setNoOfPOD(tripSheet.getNoOfPOD());
			tripSheetHistory.setReceivedPOD(tripSheet.getReceivedPOD());
			tripSheetHistory.setTripFristDriverID(tripSheet.getTripFristDriverID());
			tripSheetHistory.setTripSecDriverRoutePoint(tripSheet.getTripSecDriverRoutePoint());
			tripSheetHistory.setTripCleanerRoutePoint(tripSheet.getTripCleanerRoutePoint());
			tripSheetHistory.setTripStartDiesel(tripSheet.getTripStartDiesel());
			tripSheetHistory.setTripEndDiesel(tripSheet.getTripEndDiesel());
			tripSheetHistory.setVoucherDate(tripSheet.getVoucherDate());
			tripSheetHistory.setDeleteRemark(tripSheet.getDeleteRemark());
			tripSheetHistory.setTallyCompanyId(tripSheet.getTallyCompanyId());
			tripSheetHistory.setTripSheetDocument(tripSheet.isTripSheetDocument());
			tripSheetHistory.setMeterNotWorking(tripSheet.isMeterNotWorking());
			tripSheetHistory.setTripSheetDocumentId(tripSheet.getTripSheetDocumentId());
			tripSheetHistory.setDriverBalance(tripSheet.getDriverBalance());
			tripSheetHistory.setPendingForTally(tripSheet.isPendingForTally());
			

			return tripSheetHistory;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public RouteWiseTripSheetWeight saveTripWeight(ValueObject object) throws Exception {
		RouteWiseTripSheetWeight tripweight = new RouteWiseTripSheetWeight();
		
		tripweight.setTripSheetId(object.getLong("tripsheetId"));
		tripweight.setRouteName(object.getString("routeName"));
		tripweight.setActualWeight(object.getDouble("actualWeight"));
		tripweight.setScaleWeight(object.getDouble("scaleWeight"));
		tripweight.setCompanyId(object.getInt("companyId"));
		
		return tripweight;
	}
	
}	

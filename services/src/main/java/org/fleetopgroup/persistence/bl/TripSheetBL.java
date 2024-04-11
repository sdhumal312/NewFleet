package org.fleetopgroup.persistence.bl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.constant.DriverHaltPlaceType;
import org.fleetopgroup.constant.TripCloseStatus;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetLoadType;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.TripsheetDueAmountPaymentTypeConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverHaltDto;
import org.fleetopgroup.persistence.dto.TripSheetCommentDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.model.DriverHalt;
import org.fleetopgroup.persistence.model.IntangleTripDetails;
import org.fleetopgroup.persistence.model.LoadingSheetToTripSheet;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetAdvance;
import org.fleetopgroup.persistence.model.TripSheetComment;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripsheetDueAmount;
import org.fleetopgroup.persistence.model.TripsheetDueAmountPayment;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateDiffrentConvert;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.hibernate.sql.Select;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class TripSheetBL {
	public TripSheetBL() {
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat driverAttendanceFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat tripadvaTime = new SimpleDateFormat("hh:mm a");
	SimpleDateFormat date_tripadvaTime = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
	SimpleDateFormat dateFormatonlyDateTime = new SimpleDateFormat("EEE, d MMM yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat sqlFormat = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	SimpleDateFormat 	dateTimeFormat2 	= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	public static final long TRIP_SHEET_DEFALAT_VALUE 		= 0L;
	public static final Integer TRIP_SHEET_DEFALAT_ZERO	 	= 0;
	public static final Double TRIP_SHEET_DEFALAT_AMOUNT 	= 0.0;

	DateDiffrentConvert dataconvert = new DateDiffrentConvert();
	@Autowired IUserProfileService	userProfileService;

	public static String REPLACE_STRING = "@srstravels.xyz";

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	// save the TripSheet Model
	public TripSheet prepareUpdate(TripSheet TripSheetDto) {
		TripSheet status = new TripSheet();

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		status.setLastupdated(toDate);

		return status;
	}

	// show the List Of Vehicle Status
	public List<TripSheetDto> prepareListofDto(List<TripSheetDto> TripSheet) {
		List<TripSheetDto> Dtos = null;
		if (TripSheet != null && !TripSheet.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto Dto = null;
			for (TripSheetDto Sheet : TripSheet) {
				Dto = new TripSheetDto();
				Dto.setTripSheetID(Sheet.getTripSheetID());
				Dto.setTripSheetNumber(Sheet.getTripSheetNumber());
				Dto.setVehicle_registration(Sheet.getVehicle_registration());
				Dto.setVehicle_Group(Sheet.getVehicle_Group());
				Dto.setVehicleGroupId(Sheet.getVehicleGroupId());
				Dto.setRouteName(Sheet.getRouteName());
				if (Sheet.getTripOpenDateOn() != null) {
					Dto.setTripOpenDate(dateFormat_Name.format(Sheet.getTripOpenDateOn()));
				}
				if (Sheet.getClosetripDate() != null) {
					Dto.setClosetripDate(dateFormat_Name.format(Sheet.getClosetripDate()));
				}
				Dto.setTripOpeningKM(Sheet.getTripOpeningKM());
				Dto.setTripClosingKM(Sheet.getTripClosingKM());
				Dto.setTripUsageKM(Sheet.getTripUsageKM());
				Dto.setTripBookref(Sheet.getTripBookref());
				Dto.setTripStutes(Sheet.getTripStutes());
				Dto.setTripStutesId(Sheet.getTripStutesId());
				Dto.setTripTotalAdvance(Sheet.getTripTotalAdvance());
				Dto.setTripTotalexpense(Sheet.getTripTotalexpense());
				Dto.setTripTotalincome(Sheet.getTripTotalincome());
				Dto.setCloseTripAmount(Sheet.getCloseTripAmount());

				Dto.setCloseACCTripAmount(Sheet.getCloseACCTripAmount());
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}
	
	public List<TripSheetDto> prepareListofTripSheet(List<TripSheetDto> TripSheet) {
		
		
		
		List<TripSheetDto> Dtos = null;
		if (TripSheet != null && !TripSheet.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto Dto = null;
			for (TripSheetDto Sheet : TripSheet) {
				Dto = new TripSheetDto();
				
			
				Sheet.getCloseTripStatusId();
				Dto.setTripSheetID(Sheet.getTripSheetID());
				Dto.setTripSheetNumber(Sheet.getTripSheetNumber());
				Dto.setVehicle_registration(Sheet.getVehicle_registration());
				Dto.setVehicle_Group(Sheet.getVehicle_Group());
				Dto.setVehicleGroupId(Sheet.getVehicleGroupId());
				Dto.setRouteName(Sheet.getRouteName());
				if (Sheet.getTripOpenDateOn() != null) {
					Dto.setTripOpenDate(dateFormat_Name.format(Sheet.getTripOpenDateOn()));
				}
				if (Sheet.getClosetripDateOn() != null) {
					Dto.setClosetripDate(dateFormat_Name.format(Sheet.getClosetripDateOn()));
				}
				Dto.setTripOpeningKM(Sheet.getTripOpeningKM());
				Dto.setTripClosingKM(Sheet.getTripClosingKM());
				Dto.setTripUsageKM(Sheet.getTripUsageKM());
				Dto.setTripBookref(Sheet.getTripBookref());
				Dto.setTripStutesId(Sheet.getTripStutesId());
				Dto.setTripStutes(TripSheetStatus.getTripSheetStatusName(Sheet.getTripStutesId()));
				Dto.setTripTotalAdvance(Sheet.getTripTotalAdvance());
				Dto.setTripTotalexpense(Sheet.getTripTotalexpense());
				Dto.setTripTotalincome(Sheet.getTripTotalincome());
				Dto.setCloseTripAmount(Sheet.getCloseTripAmount());
				Dto.setCloseACCTripAmount(Sheet.getCloseACCTripAmount());
				Dto.setVoucherDateStr(Sheet.getVoucherDateStr());
				Dto.setTallyCompanyId(Sheet.getTallyCompanyId());
				if(Sheet.getDispatchedByTimeOn() != null ) {
				Dto.setDispatchedByTime(date_tripadvaTime.format(Sheet.getDispatchedByTimeOn()));
				}
				Dto.setFuelAmount(Sheet.getFuelAmount());
				Dto.setTollAmount(Sheet.getTollAmount());
				
				Dtos.add(Dto);
		
	
		  }
			
		}
		return Dtos;
	}

	
	
	public TripSheetDto GetTripSheetDetails(TripSheetDto Sheet) {
		TripSheetDto Dto = null;
		if (Sheet != null) {
			Dto = new TripSheetDto();
			Dto.setTripSheetID(Sheet.getTripSheetID());
			Dto.setTripSheetNumber(Sheet.getTripSheetNumber());
			Dto.setVid(Sheet.getVid());
			Dto.setVehicle_registration(Sheet.getVehicle_registration());
			Dto.setVehicle_Group(Sheet.getVehicle_Group());
			Dto.setVehicleGroupId(Sheet.getVehicleGroupId());
			Dto.setRouteID(Sheet.getRouteID());
			Dto.setRouteName(Sheet.getRouteName());
			Dto.setRouteApproximateKM(Sheet.getRouteApproximateKM());
			Dto.setRouteAttendancePoint(Sheet.getRouteAttendancePoint());
			Dto.setRouteTotalLiter(Sheet.getRouteTotalLiter());
			Dto.setTripOpenDateOn(Sheet.getTripOpenDateOn());
			Dto.setClosetripDateOn(Sheet.getClosetripDateOn());
			if (Sheet.getTripOpenDateOn() != null) {
				Dto.setTripOpenDate(dateFormat.format(Sheet.getTripOpenDateOn()));
			}
			System.err.println("date "+ Sheet.getClosetripDateOn());
			if (Sheet.getClosetripDateOn() != null) {
				System.err.println("date " + Sheet.getClosetripDateOn());
				Dto.setClosetripDate(dateFormat.format(Sheet.getClosetripDateOn()));
			}
			Dto.setClosetripDateOn(Sheet.getClosetripDateOn());
			Dto.setTripBookref(Sheet.getTripBookref());
			Dto.setTripOpeningKM(Sheet.getTripOpeningKM());
			Dto.setTripClosingKM(Sheet.getTripClosingKM());
			Dto.setTripUsageKM(Sheet.getTripUsageKM());
			Dto.setTripClosingKMStatusId(Sheet.getTripClosingKMStatusId());
			Dto.setTripClosingKMStatus(TripSheetStatus.getTripKmClosingStatusName(Sheet.getTripClosingKMStatusId()));
			Dto.setTripFristDriverID(Sheet.getTripFristDriverID());
			Dto.setTripFristDriverName(Sheet.getTripFristDriverName());
			if (Sheet.getTripFristDriverMobile() != null && Sheet.getTripFristDriverMobile() != "") {
				Dto.setTripFristDriverMobile(Sheet.getTripFristDriverMobile());
			} else {
				Dto.setTripFristDriverMobile("");
			}
			Dto.setTripSecDriverID(Sheet.getTripSecDriverID());
			Dto.setTripSecDriverName(Sheet.getTripSecDriverName());
			if (Sheet.getTripSecDriverMobile() != null && Sheet.getTripSecDriverMobile() != "") {
				Dto.setTripSecDriverMobile(Sheet.getTripSecDriverMobile());
			} else {
				Dto.setTripSecDriverMobile("");
			}
			Dto.setTripCleanerID(Sheet.getTripCleanerID());
			Dto.setTripCleanerName(Sheet.getTripCleanerName());
			if (Sheet.getTripCleanerMobile() != null && Sheet.getTripCleanerMobile() != "") {
				Dto.setTripCleanerMobile(Sheet.getTripCleanerMobile());
			} else {
				Dto.setTripCleanerMobile("");
			}
			Dto.setTripTotalAdvance(Sheet.getTripTotalAdvance());
			Dto.setTripTotalexpense(Sheet.getTripTotalexpense());
			Dto.setTripTotalincome(Sheet.getTripTotalincome());
			Dto.setCloseTripStatusId(Sheet.getCloseTripStatusId());
			Dto.setCloseTripStatus(TripCloseStatus.getTripSheetCloseStatusName(Sheet.getCloseTripStatusId()));
			Dto.setCloseTripNameBy(Sheet.getCloseTripNameBy());
			Dto.setCloseTripAmount(Sheet.getCloseTripAmount());
			Dto.setCloseTripReference(Sheet.getCloseTripReference());
			Dto.setCloseACCTripNameBy(Sheet.getCloseACCTripNameBy());
			Dto.setCloseACCtripDate(Sheet.getCloseACCtripDate());
			if(Sheet.getCloseACCtripDate() != null) {
				Dto.setCloseACCtripDateStr(dateTimeFormat2.format(Sheet.getCloseACCtripDate()));
			}
			
			Dto.setCloseACCTripAmount(Sheet.getCloseACCTripAmount());
			Dto.setCloseACCTripReference(Sheet.getCloseACCTripReference());
			// new TripSheet show comment is Zero
			Dto.setTripCommentTotal(Sheet.getTripCommentTotal());
			Dto.setTripStutesId(Sheet.getTripStutesId());
			Dto.setTripStutes(TripSheetStatus.getTripSheetStatusName(Sheet.getTripStutesId()));
			

			if (Sheet.getDispatchedBy() != null) {
				Dto.setDispatchedBy(Sheet.getDispatchedBy().replace(REPLACE_STRING, ""));
			}
			Dto.setDispatchedLocation(Sheet.getDispatchedLocation());

			if (Sheet.getClosedBy() != null) {
				Dto.setClosedBy(Sheet.getClosedBy().replace(REPLACE_STRING, ""));
			}

			Dto.setCloesdLocation(Sheet.getCloesdLocation());
			Dto.setAcclosedBy(Sheet.getAcclosedBy());
			Dto.setAccloesdLocation(Sheet.getCloesdLocation());
			Dto.setDispatchedByTimeOn(Sheet.getDispatchedByTimeOn());
			if (Sheet.getDispatchedByTimeOn() != null) {
				Dto.setDispatchedTime(DateTimeUtility.getTimeFromTimeStamp(new Timestamp(Sheet.getDispatchedByTimeOn().getTime()),  DateTimeUtility.KK_MM));
				Dto.setDispatchedByTime(CreatedDateTime.format(Sheet.getDispatchedByTimeOn()));
				Dto.setDispatchedOn(sqlFormat.format(Sheet.getDispatchedByTimeOn()));
			}
			if (Sheet.getClosedByTimeOn() != null) {
				Dto.setClosedTripTime(DateTimeUtility.getTimeFromTimeStamp(new Timestamp(Sheet.getClosedByTimeOn().getTime()),  DateTimeUtility.KK_MM));
				Dto.setClosedByTime(CreatedDateTime.format(Sheet.getClosedByTimeOn()));
				Dto.setClosedByTimeOn(Sheet.getClosedByTimeOn());
				Dto.setClosedOn(sqlFormat.format(Sheet.getClosedByTimeOn()));
			}
			if (Sheet.getAcclosedByTimeOn() != null) {
				Dto.setAcclosedByTime(CreatedDateTime.format(Sheet.getAcclosedByTimeOn()));
			}
			// Create and Last updated Display
			Dto.setCreatedBy(Sheet.getCreatedBy());
			if (Sheet.getCreatedOn() != null) {
				Dto.setCreated(CreatedDateTime.format(Sheet.getCreatedOn()));
			}
			Dto.setLastModifiedBy(Sheet.getLastModifiedBy());
			if (Sheet.getLastupdatedOn() != null) {
				Dto.setLastupdated(CreatedDateTime.format(Sheet.getLastupdatedOn()));
			}
			
			Dto.setRouteRemark(Sheet.getRouteRemark());
			Dto.setSubRouteID(Sheet.getSubRouteID());
			Dto.setSubRouteName(Sheet.getSubRouteName());
			
			Dto.setTripGpsOpeningKM(Sheet.getTripGpsOpeningKM());
			Dto.setTripGpsClosingKM(Sheet.getTripGpsClosingKM());
			Dto.setVehicleGPSId(Sheet.getVehicleGPSId());
			Dto.setGpsOpeningLocation(Sheet.getGpsOpeningLocation());
			Dto.setGpsCloseLocation(Sheet.getGpsCloseLocation());
			Dto.setTripGpsUsageKM(Sheet.getGpsTripUsageKM());
			Dto.setLoadTypeStr(TripSheetLoadType.getTripSheetLoadTypeName(Sheet.getLoadTypeId()));
			Dto.setLoadTypeId(Sheet.getLoadTypeId());
			Dto.setNoOfPOD(Sheet.getNoOfPOD());
			Dto.setReceivedPOD(Sheet.getReceivedPOD());
			Dto.setTripFristDriverRoutePoint(Sheet.getTripFristDriverRoutePoint());
			Dto.setTripSecDriverRoutePoint(Sheet.getTripSecDriverRoutePoint());
			Dto.setTripCleanerRoutePoint(Sheet.getTripCleanerRoutePoint());
			if(Sheet.getLoadTypesId() != null)
			Dto.setLoadTypesId(Sheet.getLoadTypesId());
			if(Sheet.getLoadTypeName() != null)
			Dto.setLoadTypeName(Sheet.getLoadTypeName());
			
			Dto.setTripStartDiesel(Sheet.getTripStartDiesel());
			Dto.setTripEndDiesel(Sheet.getTripEndDiesel());
			Dto.setVoucherDateStr(Sheet.getVoucherDateStr());
			Dto.setTallyCompanyId(Sheet.getTallyCompanyId());
			Dto.setTallyCompanyName(Sheet.getTallyCompanyName());
			if(Sheet.getTripCreatedBy() != null)
			Dto.setTripCreatedBy(Sheet.getTripCreatedBy().replace(REPLACE_STRING, ""));
			Dto.setVehicle_ExpectedOdameter(Sheet.getVehicle_ExpectedOdameter());
			Dto.setTripSheetDocument(Sheet.isTripSheetDocument());
			Dto.setTripSheetDocumentId(Sheet.getTripSheetDocumentId());
			Dto.setTripFristDriverFatherName(Sheet.getTripFristDriverFatherName());
			Dto.setTripFristDriverLastName(Sheet.getTripFristDriverLastName());
			Dto.setTripSecDriverFatherName(Sheet.getTripSecDriverFatherName());
			Dto.setTripSecDriverLastName(Sheet.getTripSecDriverLastName());
			Dto.setTripCleanerMidleName(Sheet.getTripCleanerMidleName());
			Dto.setTripCleanerLastName(Sheet.getTripCleanerLastName());

			Dto.setMeterNotWorking(Sheet.isMeterNotWorking());
			Dto.setDriverBalance(Sheet.getDriverBalance());

		}
	
		return Dto;
	}

	public TripSheetDto Get_TripSheet_To_DriverAttendance(TripSheetDto Sheet) {
		TripSheetDto Dto = new TripSheetDto();
		Dto.setTripSheetID(Sheet.getTripSheetID());
		Dto.setVid(Sheet.getVid());
		Dto.setVehicle_registration(Sheet.getVehicle_registration());
		Dto.setVehicle_Group(Sheet.getVehicle_Group());

		Dto.setRouteID(Sheet.getRouteID());
		Dto.setRouteName(Sheet.getRouteName());

		if (Sheet.getTripOpenDateOn() != null) {
			Dto.setTripOpenDate(driverAttendanceFormat.format(Sheet.getTripOpenDateOn()));
		}
		if (Sheet.getClosetripDateOn() != null) {
			Dto.setClosetripDate(driverAttendanceFormat.format(Sheet.getClosetripDateOn()));
		}
		Dto.setTripBookref(Sheet.getTripBookref());
		Dto.setTripOpeningKM(Sheet.getTripOpeningKM());
		Dto.setTripClosingKM(Sheet.getTripClosingKM());
		Dto.setTripUsageKM(Sheet.getTripUsageKM());
		Dto.setTripClosingKMStatus(TripSheetStatus.getTripKmClosingStatusName(Sheet.getTripClosingKMStatusId()));

		Dto.setTripFristDriverID(Sheet.getTripFristDriverID());
		Dto.setTripFristDriverName(Sheet.getTripFristDriverName());

		Dto.setTripSecDriverID(Sheet.getTripSecDriverID());
		Dto.setTripSecDriverName(Sheet.getTripSecDriverName());

		Dto.setTripCleanerID(Sheet.getTripCleanerID());
		Dto.setTripCleanerName(Sheet.getTripCleanerName());

		Dto.setTripTotalAdvance(Sheet.getTripTotalAdvance());
		Dto.setTripTotalexpense(Sheet.getTripTotalexpense());
		Dto.setTripTotalincome(Sheet.getTripTotalincome());

		Dto.setCloseTripStatus(Sheet.getCloseTripStatus());
		Dto.setCloseTripNameBy(Sheet.getCloseTripNameBy());
		Dto.setCloseTripAmount(Sheet.getCloseTripAmount());
		Dto.setCloseTripReference(Sheet.getCloseTripReference());
		/*
		 * if(Sheet.getClosetripDate() != null)
		 * Dto.setClosetripDate(dateFormat.format(Sheet.getClosetripDate()));
		 */

		Dto.setCloseACCTripNameBy(Sheet.getCloseACCTripNameBy());
		Dto.setCloseACCtripDate(Sheet.getCloseACCtripDate());
		Dto.setCloseACCTripAmount(Sheet.getCloseACCTripAmount());
		Dto.setCloseACCTripReference(Sheet.getCloseACCTripReference());

		Dto.setTripStutes(Sheet.getTripStutes());

		return Dto;
	}

	// save the TripSheet Model
	public TripSheetAdvance prepareTripAdvance(TripSheetAdvance TripSheetDto) {
		TripSheetAdvance tripAdvance = new TripSheetAdvance();

		tripAdvance.setTripAdvanceID(TripSheetDto.getTripAdvanceID());
		tripAdvance.setAdvanceAmount(TripSheetDto.getAdvanceAmount());

		tripAdvance.setAdvancePlaceId(TripSheetDto.getAdvancePlaceId());
		tripAdvance.setAdvancePaidbyId(TripSheetDto.getAdvancePaidbyId());
		tripAdvance.setAdvanceRefence(TripSheetDto.getAdvanceRefence());
		tripAdvance.setAdvanceRemarks(TripSheetDto.getAdvanceRemarks());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		tripAdvance.setCreated(toDate);

		return tripAdvance;
	}

	// save the TripSheet Model
	public TripSheetAdvance UpdateTripAdvance(TripSheetAdvance TripSheetDto) {
		TripSheetAdvance tripAdvance = new TripSheetAdvance();

		tripAdvance.setTripAdvanceID(TripSheetDto.getTripAdvanceID());
		tripAdvance.setAdvanceAmount(TripSheetDto.getAdvanceAmount());
		tripAdvance.setDriverId(TripSheetDto.getDriverId());
		tripAdvance.setAdvancePlaceId(TripSheetDto.getAdvancePlaceId());
		tripAdvance.setAdvancePaidbyId(TripSheetDto.getAdvancePaidbyId());
		tripAdvance.setAdvanceRefence(TripSheetDto.getAdvanceRefence());
		tripAdvance.setAdvanceRemarks(TripSheetDto.getAdvanceRemarks());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		tripAdvance.setCreated(toDate);

		return tripAdvance;
	}

	// save the TripSheet Model
	public TripSheetExpense UpdateTripExpense(TripSheetExpense TripSheetDto) {
		TripSheetExpense tripExpense = new TripSheetExpense();

		tripExpense.setTripExpenseID(TripSheetDto.getTripExpenseID());
		tripExpense.setExpenseAmount(TripSheetDto.getExpenseAmount());

		tripExpense.setExpenseRefence(TripSheetDto.getExpenseRefence());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		tripExpense.setCreated(toDate);

		return tripExpense;
	}

	/**
	 * @param driverHalt
	 * @return
	 */
	public DriverHalt prepare_Driver_Halt_Details(DriverHalt driverHalt) {

		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		DriverHalt halt = new DriverHalt();

		halt.setDHID(driverHalt.getDHID());
		if (driverHalt.getTRIP_ROUTE_ID() != null) {
			halt.setTRIP_ROUTE_ID(driverHalt.getTRIP_ROUTE_ID());
		}else {
			halt.setTRIP_ROUTE_ID(0);
		}
		if (driverHalt.getTRIP_ROUTE_ID() != null) {
			halt.setTRIP_ROUTE_ID(driverHalt.getTRIP_ROUTE_ID());
		}else {
			halt.setTRIP_ROUTE_ID(0);
		}
		halt.setREFERENCE_NO(driverHalt.getREFERENCE_NO());

		halt.setHALT_PAIDBY_ID(userDetails.getId());
		
		halt.setHALT_PLACE_TYPE_ID(DriverHaltPlaceType.DRIVERHALT_PALCE_TYPE_LOCALHALT);
		
		halt.setHALT_PLACE_ID(driverHalt.getHALT_PLACE_ID());
		halt.setHALT_REASON(driverHalt.getHALT_REASON());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		halt.setCREATED(toDate);
		halt.setLASTUPDATED(toDate);
		
		halt.setCREATED_BY_ID(userDetails.getId());
		halt.setLASTUPDATED_BY_ID(userDetails.getId());
		
		halt.setMarkForDelete(false);
		halt.setCOMPANY_ID(userDetails.getCompany_id());
		return halt;
	}
	
	public DriverHalt prepare_TRIPSHEET_Driver_Halt_Details(DriverHalt driverHalt) {

		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		DriverHalt halt = new DriverHalt();

		halt.setREFERENCE_NO(driverHalt.getREFERENCE_NO());

		halt.setHALT_PAIDBY_ID(userDetails.getId());
		
		halt.setHALT_PLACE_TYPE_ID(DriverHaltPlaceType.DRIVERHALT_PALCE_TYPE_TRIPHALT);
		
		halt.setHALT_PLACE_ID(driverHalt.getHALT_PLACE_ID());
		halt.setHALT_REASON(driverHalt.getHALT_REASON());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		halt.setCREATED(toDate);
		halt.setLASTUPDATED(toDate);
		
		halt.setCREATED_BY_ID(userDetails.getId());
		halt.setLASTUPDATED_BY_ID(userDetails.getId());
		
		halt.setMarkForDelete(false);
		halt.setCOMPANY_ID(userDetails.getCompany_id());
		return halt;
	}
	
	public DriverHalt Update_prepare_Driver_Halt_Details(DriverHalt driverHalt) {

		/** Who Login User Email Id */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		DriverHalt halt = new DriverHalt();
		
		halt.setDHID(driverHalt.getDHID());
		if (driverHalt.getVID() != null) {
			halt.setVID(driverHalt.getVID());
		} else {
			halt.setVID(0);
		}
		
		halt.setREFERENCE_NO(driverHalt.getREFERENCE_NO());

		halt.setHALT_REASON(driverHalt.getHALT_REASON());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		halt.setLASTUPDATED(toDate);
		
		halt.setLASTUPDATED_BY_ID(userDetails.getId());
		
		halt.setCOMPANY_ID(userDetails.getCompany_id());
		
		return halt;
	}

	/**
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public Double Driver_Halt_Point(Date fromDate, Date toDate) {
		Double point = 0.0;
		// in milliseconds
		long timeDifferenceMilliseconds = toDate.getTime() - fromDate.getTime();

		long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
		// System.out.println(diffDays);
		if (diffDays == (long) 0) {
			point += 1.0;
		} else {
			point += Double.parseDouble("" + diffDays);
			point += 1.0;
		}
		return point;
	}

	/**
	 * @param find_list_TripSheet_DriverHalt
	 * @return
	 */
	public List<DriverHaltDto> Driver_Halt_Display(List<DriverHaltDto> findHalt) {

		List<DriverHaltDto> Dtos = null;
		if (findHalt != null && !findHalt.isEmpty()) {
			Dtos = new ArrayList<DriverHaltDto>();
			DriverHaltDto Dto = null;
			for (DriverHaltDto Sheet : findHalt) {
				Dto = new DriverHaltDto();

				Dto.setDHID(Sheet.getDHID());
				Dto.setDRIVER_NAME(Sheet.getDRIVER_NAME());
				Dto.setDRIVERID(Sheet.getDRIVERID());
				Dto.setHALT_AMOUNT(Sheet.getHALT_AMOUNT());
				if (Sheet.getHALT_DATE_FROM_ON() != null) {
					Dto.setHALT_DATE_FROM(dateFormat_Name.format(Sheet.getHALT_DATE_FROM_ON()));
				}
				if (Sheet.getHALT_DATE_TO_ON() != null) {
					Dto.setHALT_DATE_TO(dateFormat_Name.format(Sheet.getHALT_DATE_TO_ON()));
				}
				Dto.setHALT_PAIDBY(Sheet.getHALT_PAIDBY());
				Dto.setHALT_PLACE(Sheet.getHALT_PLACE());
				Dto.setHALT_POINT(Sheet.getHALT_POINT());
				Dto.setHALT_REASON(Sheet.getHALT_REASON());
				Dto.setTRIPSHEETID(Sheet.getTRIPSHEETID());
				Dto.setTRIP_ROUTE_NAME(Sheet.getTRIP_ROUTE_NAME());

				Dtos.add(Dto);
			}
		}

		return Dtos;
	}

	public List<DriverHaltDto> Driver_Halt_CreateAttendanccey(List<DriverHaltDto> findHalt) {

		List<DriverHaltDto> Dtos = null;
		if (findHalt != null && !findHalt.isEmpty()) {
			Dtos = new ArrayList<DriverHaltDto>();
			DriverHaltDto Dto = null;
			for (DriverHaltDto Sheet : findHalt) {
				Dto = new DriverHaltDto();

				Dto.setDHID(Sheet.getDHID());
				Dto.setDRIVER_NAME(Sheet.getDRIVER_NAME());
				Dto.setDRIVERID(Sheet.getDRIVERID());
				if (Sheet.getHALT_DATE_FROM_ON() != null) {
					Dto.setHALT_DATE_FROM(driverAttendanceFormat.format(Sheet.getHALT_DATE_FROM_ON()));
				}
				if (Sheet.getHALT_DATE_TO_ON() != null) {
					Dto.setHALT_DATE_TO(driverAttendanceFormat.format(Sheet.getHALT_DATE_TO_ON()));
				}
				Dto.setHALT_POINT(Sheet.getHALT_POINT());
				Dto.setTRIPSHEETID(Sheet.getTRIPSHEETID());

				Dtos.add(Dto);
			}
		}

		return Dtos;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public List<TripSheetDto> prepare_TripsheetAdvance_Date_to_TripSheetDetails(List<TripSheetDto> advanceCollection) {

		List<TripSheetDto> Dtos = null;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto TSTO = null;
			for (TripSheetDto Sheet : advanceCollection) {
				TSTO = new TripSheetDto();

				TSTO.setTripSheetID(Sheet.getTripSheetID());
				TSTO.setTripSheetNumber(Sheet.getTripSheetNumber());
				TSTO.setVehicle_registration(Sheet.getVehicle_registration());
				TSTO.setTripFristDriverName(Sheet.getTripFristDriverName());
				TSTO.setTripFristDriverMobile(Sheet.getTripFristDriverMobile());

				TSTO.setTripSecDriverName(Sheet.getTripSecDriverName());
				TSTO.setTripSecDriverMobile(Sheet.getTripSecDriverMobile());
				TSTO.setTripCleanerName(Sheet.getTripCleanerName());
				TSTO.setTripCleanerMobile(Sheet.getTripCleanerMobile());

				TSTO.setRouteName(Sheet.getRouteName());
				TSTO.setTripTotalAdvance(Sheet.getTripTotalAdvance());
				TSTO.setTripTotalexpense(Sheet.getTripTotalexpense());
				if (Sheet.getTripOpenDateOn() != null) {
					TSTO.setTripOpenDate(tripadvaTime.format(Sheet.getTripOpenDateOn()));
				}
				Dtos.add(TSTO);
			}
		}
		return Dtos;
	}

	public List<TripSheetDto> prepare_DailyTripsheetAdvance_Date_to_TripSheetDetails(
			List<TripSheetDto> advanceCollection) {

		List<TripSheetDto> Dtos = null;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto TSTO = null;
			for (TripSheetDto Sheet : advanceCollection) {
				TSTO = new TripSheetDto();

				TSTO.setTripSheetID(Sheet.getTripSheetID());
				TSTO.setTripSheetNumber(Sheet.getTripSheetNumber());
				TSTO.setVehicle_registration(Sheet.getVehicle_registration());

				TSTO.setRouteName(Sheet.getRouteName());

				TSTO.setTripTotalexpense(Sheet.getTripTotalexpense());
				TSTO.setTripTotalAdvance(Sheet.getTripTotalAdvance());

				if (Sheet.getTripOpenDateOn() != null) {
					TSTO.setTripOpenDate(dateFormat.format(Sheet.getTripOpenDateOn()));
				}

				if (Sheet.getClosetripDateOn() != null) {
					TSTO.setClosetripDate(dateFormat.format(Sheet.getClosetripDateOn()));
				}

				TSTO.setTripStutes(Sheet.getTripStutes());

				Dtos.add(TSTO);
			}
		}
		return Dtos;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public List<TripSheetDto> prepare_Tripsheet_DATE_to_TripSheetDetails(List<TripSheetDto> advanceCollection) {

		List<TripSheetDto> Dtos = null;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto TSTO = null;
			for (TripSheetDto Sheet : advanceCollection) {
				TSTO = new TripSheetDto();

				TSTO.setTripSheetID(Sheet.getTripSheetID());
				TSTO.setTripSheetNumber(Sheet.getTripSheetNumber());
				TSTO.setVehicle_registration(Sheet.getVehicle_registration());
				TSTO.setTripFristDriverName(Sheet.getTripFristDriverName());
				TSTO.setTripFristDriverMobile(Sheet.getTripFristDriverMobile());

				TSTO.setTripSecDriverName(Sheet.getTripSecDriverName());
				TSTO.setTripSecDriverMobile(Sheet.getTripSecDriverMobile());
				TSTO.setTripCleanerName(Sheet.getTripCleanerName());
				TSTO.setTripCleanerMobile(Sheet.getTripCleanerMobile());

				TSTO.setRouteName(Sheet.getRouteName());
				TSTO.setTripTotalAdvance(Sheet.getTripTotalAdvance());
				TSTO.setTripTotalexpense(Sheet.getTripTotalexpense());
				if (Sheet.getTripOpenDateOn() != null) {
					TSTO.setTripOpenDate(dateFormat_Name.format(Sheet.getTripOpenDateOn()));
				}
				
				if(Sheet.getLoadTypesId() != null)
				TSTO.setLoadTypesId(Sheet.getLoadTypesId());
				if(Sheet.getLoadTypeName() != null)
				TSTO.setLoadTypeName(Sheet.getLoadTypeName());
				Dtos.add(TSTO);
			}
		}
		return Dtos;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public Double perpare_advanceTripTotal(List<TripSheet> advanceCollection) {
		Double TotalAdvance = 0.0;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			for (TripSheet Sheet : advanceCollection) {
				if (Sheet.getTripTotalAdvance() != null) {
					TotalAdvance += Sheet.getTripTotalAdvance();
				}
			} // close For loop in total advance
		}
		return TotalAdvance;
	}
	
	public Double getadvanceTripTotal(List<TripSheetDto> advanceCollection) {
		Double TotalAdvance = 0.0;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			for (TripSheetDto Sheet : advanceCollection) {
				if (Sheet.getTripTotalAdvance() != null) {
					TotalAdvance += Sheet.getTripTotalAdvance();
				}
			} // close For loop in total advance
		}
		return TotalAdvance;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public Double perpare_ExpanceTripTotal(List<TripSheetDto> advanceCollection) {

		Double TotalExpance = 0.0;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			for (TripSheetDto Sheet : advanceCollection) {
				if (Sheet.getTripTotalexpense() != null) {
					TotalExpance += Sheet.getTripTotalexpense();
				}
			} // close For loop in total advance
		}
		return TotalExpance;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public List<TripSheetDto> prepare_Tripsheet_expense_Date_HaltBata_details(List<TripSheetDto> advanceCollection) {

		List<TripSheetDto> Dtos = null;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto TSTO = null;
			for (TripSheetDto Sheet : advanceCollection) {
				TSTO = new TripSheetDto();

				TSTO.setTripSheetID(Sheet.getTripSheetID());
				TSTO.setTripSheetNumber(Sheet.getTripSheetNumber());
				TSTO.setVehicle_registration(Sheet.getVehicle_registration());
				TSTO.setVehicle_Group(Sheet.getVehicle_Group());

				TSTO.setRouteName(Sheet.getRouteName());
				TSTO.setTripTotalexpense(Sheet.getTripTotalexpense());
				TSTO.setTripBookref(Sheet.getTripBookref());
				if (Sheet.getTripOpenDateOn() != null) {
					TSTO.setTripOpenDate(date_tripadvaTime.format(Sheet.getTripOpenDateOn()));
				}
				Dtos.add(TSTO);
			}
		}
		return Dtos;
	}

	/**
	 * @param list_TripSheet_ID_TO_TripSheetComment
	 * @return
	 */
	public List<TripSheetCommentDto> TripSheetComment_Display(List<TripSheetComment> tripComment) {

		List<TripSheetCommentDto> Dtos = null;
		if (tripComment != null && !tripComment.isEmpty()) {
			Dtos = new ArrayList<TripSheetCommentDto>();
			TripSheetCommentDto TSTO = null;
			for (TripSheetComment Sheet : tripComment) {
				TSTO = new TripSheetCommentDto();

				TSTO.setTRIPCID(Sheet.getTRIPCID());

				TSTO.setTRIPSHEETID(Sheet.getTRIPSHEETID());
				TSTO.setTRIP_COMMENT(Sheet.getTRIP_COMMENT());

				TSTO.setCREATEDBY(Sheet.getCREATEDBY());
				TSTO.setCREATED_PLACE(Sheet.getCREATED_PLACE());
				TSTO.setCREATED_EMAIL(Sheet.getCREATED_EMAIL());

				/** Set Created Current Date */
				if (Sheet.getCREATED_DATE() != null) {
					TSTO.setCREATED_DATE(CreatedDateTime.format(Sheet.getCREATED_DATE()));
				}

				// System.out.println( diffenceDays);
				try {
					TSTO.setCREATED_DATE_DIFFERENT(dataconvert.DifferentDate(Sheet.getCREATED_DATE()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				Dtos.add(TSTO);
			}
		}
		return Dtos;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public List<TripSheetDto> prepare_Tripsheet_collection_DATE_to_TripSheetDtoDetails(
			List<TripSheetDto> advanceCollection) {
		List<TripSheetDto> Dtos = null;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto TSTO = null;
			for (TripSheetDto Sheet : advanceCollection) {
				TSTO = new TripSheetDto();

				TSTO.setTripSheetID(Sheet.getTripSheetID());
				TSTO.setTripSheetNumber(Sheet.getTripSheetNumber());
				TSTO.setVehicle_registration(Sheet.getVehicle_registration());
				TSTO.setTripFristDriverName(Sheet.getTripFristDriverName());
				TSTO.setTripFristDriverMobile(Sheet.getTripFristDriverMobile());

				TSTO.setTripSecDriverName(Sheet.getTripSecDriverName());
				TSTO.setTripSecDriverMobile(Sheet.getTripSecDriverMobile());
				TSTO.setTripCleanerName(Sheet.getTripCleanerName());
				TSTO.setTripCleanerMobile(Sheet.getTripCleanerMobile());

				TSTO.setRouteName(Sheet.getRouteName());
				TSTO.setTripTotalAdvance(Sheet.getTripTotalAdvance());
				TSTO.setTripTotalexpense(Sheet.getTripTotalexpense());
				TSTO.setTripTotalincome(Sheet.getTripTotalincome());
				Double netpay = 0.0, expense = 0.0, Advance = 0.0;
				if (Sheet.getTripTotalexpense() != null) {
					expense = Sheet.getTripTotalexpense();
				}
				if (Sheet.getTripTotalAdvance() != null) {
					Advance = Sheet.getTripTotalAdvance();
				}
				netpay = Advance - expense;
				TSTO.setCloseTripAmount(round(netpay, 2));
				if (Sheet.getCollectionTripOpenDate() != null) {
					TSTO.setTripOpenDate(dateFormat_Name.format(Sheet.getCollectionTripOpenDate()));
				}
				if (Sheet.getCloseACCtripDate() != null) {
					TSTO.setClosetripDate(dateFormat_Name.format(Sheet.getCloseACCtripDate()));
				}

				if (Sheet.getCollectionFristDriverBata() != null) {
					TSTO.setCollectionFristDriverBata(round(Sheet.getCollectionFristDriverBata(), 2));
				}
				if (Sheet.getCollectionSecondDriverBata() != null) {
					TSTO.setCollectionSecondDriverBata(round(Sheet.getCollectionSecondDriverBata(), 2));
				}
				if (Sheet.getCollectionCleanerBata() != null) {
					TSTO.setCollectionCleanerBata(round(Sheet.getCollectionCleanerBata(), 2));
				}
				if (Sheet.getCollectionTollCharge() != null) {
					TSTO.setCollectionTollCharge(round(Sheet.getCollectionTollCharge(), 2));
				}
				if (Sheet.getCollectionWelfareExpense() != null) {
					TSTO.setCollectionWelfareExpense(round(Sheet.getCollectionWelfareExpense(), 2));
				}
				if (Sheet.getCollectionLuggage() != null) {
					TSTO.setCollectionLuggage(round(Sheet.getCollectionLuggage(), 2));
				}
				if (Sheet.getCollectionWashpark() != null) {
					TSTO.setCollectionWashpark(round(Sheet.getCollectionWashpark(), 2));
				}
				if (Sheet.getCollectionHaltingBata() != null) {
					TSTO.setCollectionHaltingBata(round(Sheet.getCollectionHaltingBata(), 2));
				}
				if (Sheet.getCollectionOtherExpense() != null) {
					TSTO.setCollectionOtherExpense(round(Sheet.getCollectionOtherExpense(), 2));
				}
				if (Sheet.getClosedBy() != null) {
					TSTO.setClosedBy(Sheet.getClosedBy().replace(REPLACE_STRING, ""));
				}
				Dtos.add(TSTO);
			}
		}
		return Dtos;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public Double[] Tota_Tripsheet_collection_DATE(List<TripSheetDto> advanceCollection) {

		Double[] totalCollection = new Double[12];

		Double TotalTollCharge = 0.0, TotalDriverOne = 0.0, TotalDrvierTwo = 0.0, TotalCleaner = 0.0,
				TotalWelfare = 0.0, TotalLuggage = 0.0, TotalWash = 0.0, TotalHalting = 0.0, TotalOtherExpense = 0.0,
				TotalExpense = 0.0, TotalAdvance = 0.0, Totalnetpay = 0.0;
		;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			for (TripSheetDto tripDTo : advanceCollection) {

				if (tripDTo.getCollectionTollCharge() != null) {
					TotalTollCharge += tripDTo.getCollectionTollCharge();
				}
				if (tripDTo.getCollectionFristDriverBata() != null) {
					TotalDriverOne += tripDTo.getCollectionFristDriverBata();
				}
				if (tripDTo.getCollectionSecondDriverBata() != null) {
					TotalDrvierTwo += tripDTo.getCollectionSecondDriverBata();
				}
				if (tripDTo.getCollectionCleanerBata() != null) {
					TotalCleaner += tripDTo.getCollectionCleanerBata();
				}
				if (tripDTo.getCollectionWelfareExpense() != null) {
					TotalWelfare += tripDTo.getCollectionWelfareExpense();
				}
				if (tripDTo.getCollectionLuggage() != null) {
					TotalLuggage += tripDTo.getCollectionLuggage();
				}
				if (tripDTo.getCollectionWashpark() != null) {
					TotalWash += tripDTo.getCollectionWashpark();
				}
				if (tripDTo.getCollectionHaltingBata() != null) {
					TotalHalting += tripDTo.getCollectionHaltingBata();
				}

				if (tripDTo.getCollectionOtherExpense() != null) {
					TotalOtherExpense += tripDTo.getCollectionOtherExpense();
				}

				if (tripDTo.getTripTotalexpense() != null) {
					TotalExpense += tripDTo.getTripTotalexpense();
				}
				if (tripDTo.getTripTotalAdvance() != null) {
					TotalAdvance += tripDTo.getTripTotalAdvance();
				}
			}
		}

		totalCollection[0] = round(TotalTollCharge, 2);
		totalCollection[1] = round(TotalDriverOne, 2);
		totalCollection[2] = round(TotalDrvierTwo, 2);
		totalCollection[3] = round(TotalCleaner, 2);
		totalCollection[4] = round(TotalWelfare, 2);
		totalCollection[5] = round(TotalLuggage, 2);
		totalCollection[6] = round(TotalWash, 2);
		totalCollection[7] = round(TotalHalting, 2);
		totalCollection[8] = round(TotalOtherExpense, 2);
		totalCollection[9] = round(TotalExpense, 2);
		totalCollection[10] = round(TotalAdvance, 2);
		Totalnetpay = TotalAdvance - TotalExpense;
		totalCollection[11] = round(Totalnetpay, 2);

		return totalCollection;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public List<TripSheetDto> prepare_Tripsheet__Dto(List<TripSheetDto> advanceCollection) {

		List<TripSheetDto> Dtos = null;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto TSTO = null;
			for (TripSheetDto Sheet : advanceCollection) {
				TSTO = new TripSheetDto();

				TSTO.setTripSheetID(Sheet.getTripSheetID());
				TSTO.setTripSheetNumber(Sheet.getTripSheetNumber());
				TSTO.setVehicle_registration(Sheet.getVehicle_registration());
				TSTO.setTripFristDriverName(Sheet.getTripFristDriverName());
				TSTO.setTripFristDriverMobile(Sheet.getTripFristDriverMobile());

				TSTO.setTripSecDriverName(Sheet.getTripSecDriverName());
				TSTO.setTripSecDriverMobile(Sheet.getTripSecDriverMobile());
				TSTO.setTripCleanerName(Sheet.getTripCleanerName());
				TSTO.setTripCleanerMobile(Sheet.getTripCleanerMobile());

				TSTO.setRouteName(Sheet.getRouteName());
				TSTO.setTripTotalAdvance(Sheet.getTripTotalAdvance());
				TSTO.setTripTotalexpense(Sheet.getTripTotalexpense());
				if (Sheet.getTripOpenDateOn() != null) {
					TSTO.setTripOpenDate(tripadvaTime.format(Sheet.getTripOpenDateOn()));
				}

				if (Sheet.getDispatchedBy() != null) {
					TSTO.setDispatchedBy(Sheet.getDispatchedBy().replace(REPLACE_STRING, ""));
				}
				if (Sheet.getDispatchedByTimeOn() != null) {
					TSTO.setDispatchedByTime(CreatedDateTime.format(Sheet.getDispatchedByTimeOn()));
				}
				
				TSTO.setDispatchedLocation(Sheet.getDispatchedLocation());
				TSTO.setTripFristDriverLastName(Sheet.getTripFristDriverLastName());
				TSTO.setTripSecDriverLastName(Sheet.getTripSecDriverLastName());
				
				TSTO.setFDriverEmpNo(Sheet.getFDriverEmpNo());
				TSTO.setSDriverEmpNo(Sheet.getSDriverEmpNo());
				
		
				TSTO.setFDdetails(Sheet.getFDdetails());
				TSTO.setSDdetails(Sheet.getSDdetails());
				TSTO.setClenerDetails(Sheet.getClenerDetails());
				Dtos.add(TSTO);
			}
		}
		return Dtos;
	}

	public Double Total_Amount_Today_TRIPSHEET_Advance(List<TripSheet> TripSheet) {

		Double TotalAdvance = 0.0;
		if (TripSheet != null && !TripSheet.isEmpty()) {
			for (TripSheet Sheet : TripSheet) {

				if (Sheet.getTripTotalAdvance() != null) {
					TotalAdvance += Sheet.getTripTotalAdvance();
				}
			}
		}
		return TotalAdvance;
	}
	
	public Double Total_Amount_TRIPSHEET_Advance(List<TripSheetDto> TripSheet) {

		Double TotalAdvance = 0.0;
		if (TripSheet != null && !TripSheet.isEmpty()) {
			for (TripSheetDto Sheet : TripSheet) {

				if (Sheet.getTripTotalAdvance() != null) {
					TotalAdvance += Sheet.getTripTotalAdvance();
				}
			}
		}
		return TotalAdvance;
	}
	
	public TripsheetDueAmount saveDueAmount(ValueObject valueObject) throws Exception {
		
		TripsheetDueAmount dueAmount 	= new TripsheetDueAmount();
		CustomUserDetails userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		dueAmount.setTripSheetID(valueObject.getLong("tripsheetId"));
		dueAmount.setDriver_id(valueObject.getInt("driverId"));
		dueAmount.setDueAmount(valueObject.getDouble("dueAmount"));
		dueAmount.setBalanceAmount(valueObject.getDouble("dueAmount"));
		dueAmount.setDueStatus(TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_NOT_PAID);
		
		if (valueObject.getString("approxDate") != null) {
			java.util.Date date = dateFormat.parse(valueObject.getString("approxDate"));
			Date Reported_Date = new Date(date.getTime());
			dueAmount.setApproximateDate(Reported_Date);
		} else {
			String currentDate = dateFormat.format(new java.util.Date());
			java.util.Date currentDateUpdate = dateFormat.parse(currentDate);
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			dueAmount.setApproximateDate(toDate);
		}
		
		if (valueObject.getString("dueDate") != null) {
			java.util.Date date = dateFormat.parse(valueObject.getString("dueDate"));
			Date Reported_Date = new Date(date.getTime());
			dueAmount.setDueDate(Reported_Date);
		} else {
			String currentDate = dateFormat.format(new java.util.Date());
			java.util.Date currentDateUpdate = dateFormat.parse(currentDate);
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			dueAmount.setDueDate(toDate);
		}
		
		dueAmount.setRemark(valueObject.getString("remark"));
		dueAmount.setCreatedById(userDetails.getId());
		dueAmount.setLastUpdatedBy(userDetails.getId());
		dueAmount.setCompanyId(userDetails.getCompany_id());
		dueAmount.setMarkForDelete(false);
		dueAmount.setBillSelectionId(valueObject.getShort("billselectionId",(short)0));
		
		String currentDate = dateFormat.format(new java.util.Date());
		java.util.Date currentDateUpdate = dateFormat.parse(currentDate);
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		dueAmount.setCreationDate(toDate);
		dueAmount.setLastUpdatedOn(toDate);
		dueAmount.setVid(valueObject.getInt("vid"));
		
		return dueAmount;
	}
	
	@SuppressWarnings("unchecked")
	public List<TripsheetDueAmountPayment> saveDueAmountPaymentByExpense(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject> 	expenseArry = null;
		List<TripsheetDueAmountPayment>  duePaymentlist = new ArrayList<>();
		TripsheetDueAmountPayment      duePayment = null;
		CustomUserDetails userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		expenseArry	=(ArrayList<ValueObject>) valueObject.get("expenseArry");
		if(expenseArry != null && !expenseArry.isEmpty()) {
			for(ValueObject object : expenseArry) {
				duePayment = new TripsheetDueAmountPayment();
				duePayment.setTripsheetDueAmountId(valueObject.getLong("tripsheetDueAmountId"));
				duePayment.setPaymentModeId(valueObject.getShort("paymentModeSettle"));
				duePayment.setPaymentTypeId(valueObject.getShort("paymentTypeSettle"));
				duePayment.setPaidAmount(object.getDouble("expenseAmount",0));
				duePayment.setTransactionMode(valueObject.getShort("transactionMode",(short)0));
				duePayment.setTransactionNo(valueObject.getString("transactionNo",""));
				duePayment.setCreatedById(userDetails.getId());
				duePayment.setLastUpdatedBy(userDetails.getId());
				duePayment.setCompanyId(userDetails.getCompany_id());
				duePayment.setMarkForDelete(false);
				if (object.getString("paidDate") != null) {
					duePayment.setPaidDate(DateTimeUtility.getTimeStamp(object.getString("paidDate"), DateTimeUtility.DDMMYYYY));
				} else {
					String currentDate = dateFormat.format(new java.util.Date());
					java.util.Date currentDateUpdate = dateFormat.parse(currentDate);
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
					duePayment.setPaidDate(toDate);
				}
				String currentDate = dateFormat.format(new java.util.Date());
				java.util.Date currentDateUpdate = dateFormat.parse(currentDate);
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
				duePayment.setCreationDate(toDate);
				duePayment.setLastUpdatedOn(toDate);
				duePayment.setReference(object.getString("reference"));
				duePayment.setBillSelectionId(valueObject.getShort("billselectionId",(short)0));
				duePaymentlist.add(duePayment);
			}
		}
		return duePaymentlist;
	}
	
	@SuppressWarnings("unchecked")
	public List<TripSheetExpense> saveDuePaymentAmountAsTripExpense(ValueObject object) throws Exception{
		
		ArrayList<ValueObject> 	expenseArry = null;
		List<TripSheetExpense>   tripExpenseList = null;
		CustomUserDetails userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		tripExpenseList  = new ArrayList<>();
		expenseArry	=(ArrayList<ValueObject>) object.get("expenseArry");
		
		if(expenseArry != null && !expenseArry.isEmpty()) {
			for(ValueObject valueobject : expenseArry) {
				TripSheetExpense TripExpense = new TripSheetExpense();
				TripExpense.setExpenseId(valueobject.getInt("expenseNameId"));
				TripExpense.setExpenseAmount(valueobject.getDouble("expenseAmount"));
				TripExpense.setExpensePlaceId(object.getInt("expensePlaceId"));
				TripExpense.setExpenseRefence(valueobject.getString("reference"));
				TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
				TripExpense.setCreatedById(userDetails.getId());
				TripExpense.setCompanyId(userDetails.getCompany_id());
				TripSheet tsheet = new TripSheet();
				tsheet.setTripSheetID(object.getLong("tripsheetId"));
				TripExpense.setTripsheet(tsheet);
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
				TripExpense.setCreated(toDate);
				tripExpenseList.add(TripExpense);
			}
		}
    return tripExpenseList;
	}
	
	public ValueObject getDriverBalanceForTrip(TripSheetDto tripSheetDto, HashMap<String, Object> configuration, List<TripSheetIncomeDto> incomeDto, List<TripSheetExpenseDto>  expenseList) throws Exception{
		ValueObject		valueObject		= new ValueObject();
		double totalIncome = 0;
		double totalExpense	= 0;
		double totalAdvance	= 0;
		double balance		= 0;
		DecimalFormat format = new DecimalFormat("##.00");
		try {
			
			if(incomeDto != null && !incomeDto.isEmpty()) {
				for (TripSheetIncomeDto dto :incomeDto) {
					if(dto.getDispatchLedgerId() == null && dto.getlHPVDetailsId() == null && dto.getTicketIncomeApiId() == 0 && !dto.isDriverExcluded()) {
						totalIncome += dto.getIncomeAmount();
					}
				}
			}
			if(expenseList != null && !expenseList.isEmpty()) {
				for (TripSheetExpenseDto dto :expenseList) {
					if(dto.getVendorId() == null ||  dto.getVendorId() <= 0) {
						if((boolean) configuration.get("showPFAmount")) {
							totalExpense += dto.getBalanceAmount();
						}else {
							totalExpense += dto.getExpenseAmount();
						}
						
					}
				}
			}
			
			
			if(tripSheetDto.getTripTotalAdvance() != null && !(boolean)configuration.get("removeAdvanceFromDriverBalance"))
				totalAdvance	= tripSheetDto.getTripTotalAdvance();
			
			 balance	= totalIncome + totalAdvance - totalExpense;
			 
			 if(balance < 0) {
				 valueObject.put("driverBalanceKey", configuration.get("payToDriverText"));
				 valueObject.put("lossInTrip", true);
			 }else {
				 valueObject.put("driverBalanceKey", configuration.get("payToOfficeText"));
			 }
			 valueObject.put("balanceAmount",format.format(Math.abs(balance)));
			 valueObject.put("totalIncome",format.format(Math.abs(totalIncome)));
			 valueObject.put("totalExpense",format.format(Math.abs(totalExpense)));
			 valueObject.put("totalAdvance",format.format(Math.abs(totalAdvance)));
			 
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject	= null;
		}
	}
	
	public static HashMap<String, LoadingSheetToTripSheet> getWayBillTypeWiseLSAmount(List<LoadingSheetToTripSheet> lsDataList){
		HashMap<String, LoadingSheetToTripSheet>	hashMap	= null;
		
		LoadingSheetToTripSheet		loadingSheetToTripSheet	= null;
		try {
			if(lsDataList != null &&  !lsDataList.isEmpty()) {
				hashMap = new HashMap<String, LoadingSheetToTripSheet>();
				for (LoadingSheetToTripSheet sheet : lsDataList) {
					loadingSheetToTripSheet = hashMap.get(sheet.getDispatchLedgerId()+"_"+sheet.getWaybillTypeId());
					if(loadingSheetToTripSheet == null) {
						loadingSheetToTripSheet = new LoadingSheetToTripSheet();
						loadingSheetToTripSheet.setBookingTotal(sheet.getBookingTotal());
						loadingSheetToTripSheet.setDispatchLedgerId(sheet.getDispatchLedgerId());
						loadingSheetToTripSheet.setWaybillTypeId(sheet.getWaybillTypeId());
					}else {
						loadingSheetToTripSheet.setBookingTotal(sheet.getBookingTotal() + loadingSheetToTripSheet.getBookingTotal());
					}
					hashMap.put(sheet.getDispatchLedgerId()+"_"+sheet.getWaybillTypeId(), loadingSheetToTripSheet);
				}
			}
			return hashMap;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}

	public ValueObject preparedTripSheetExpenseFromExcel(ValueObject valueInObject)throws Exception {
		TripSheetExpense 					tripExpense				= null;
		
		ValueObject				valueOutObject				= null;
		TripSheet				tripSheet					= null;
		try {
			valueOutObject		= new ValueObject();
			tripSheet	= (TripSheet) valueInObject.get("tripsheet");
			
			CustomUserDetails userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			tripExpense		= new TripSheetExpense();
			
			tripExpense.setExpenseId(valueInObject.getInt("expenseId"));
			tripExpense.setExpenseAmount(valueInObject.getDouble("cellValue"));
			
			tripExpense.setTripsheet(tripSheet);
			tripExpense.setExpensePlaceId(valueInObject.getInt("branchId"));
			tripExpense.setExpenseRefence("");
			tripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
			tripExpense.setCreatedById(userDetails.getId());
			tripExpense.setCompanyId(userDetails.getCompany_id());
			tripExpense.setCredit(false);
			tripExpense.setVendorId(0);
			tripExpense.setCreated(DateTimeUtility.getCurrentTimeStamp());
			tripExpense.setRemark("");
			
			valueOutObject.put("tripExpense", tripExpense);
			return valueOutObject;
		} catch (Exception e) {
		e.printStackTrace();
		throw e;
		}
		
	}

	
	public IntangleTripDetails prepareIntangleTripDetails(JSONObject	object)throws Exception {
		IntangleTripDetails 	intangleTripDetails = null;
		try {
			
			intangleTripDetails = new IntangleTripDetails();
			intangleTripDetails.setIntangleVid(object.getString("vid"));
			intangleTripDetails.setVehicleNumber(object.getString("vehicleNumber"));
			intangleTripDetails.setMileage(object.getDouble("mileage"));
			intangleTripDetails.setDistance(object.getDouble("distance"));
			intangleTripDetails.setFuelLiter(object.getDouble("fuel"));
			intangleTripDetails.setDuration(object.getLong("dur"));
			intangleTripDetails.setTripDate((Timestamp) object.get("finalDate"));
			intangleTripDetails.setCompanyId(CompanyConstant.COMPANY_CODE_AADINATH);
			intangleTripDetails.setMarkForDelete(false);
			intangleTripDetails.setCreationDate(DateTimeUtility.getCurrentTimeStamp());
			intangleTripDetails.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			return intangleTripDetails;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public TripsheetDueAmountPayment saveDueAmountPayment(ValueObject valueObject) throws Exception {
		TripsheetDueAmountPayment  duePayment = new TripsheetDueAmountPayment();
		CustomUserDetails userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		duePayment.setTripsheetDueAmountId(valueObject.getLong("tripsheetDueAmountId"));
		duePayment.setPaymentModeId(valueObject.getShort("paymentModeSettle"));
		duePayment.setPaymentTypeId(valueObject.getShort("paymentTypeSettle"));
		duePayment.setPaidAmount(valueObject.getDouble("receiveAmtSettle",0));
		duePayment.setTransactionMode(valueObject.getShort("transactionMode",(short)0));
		duePayment.setTransactionNo(valueObject.getString("transactionNo",""));
		
		duePayment.setCreatedById(userDetails.getId());
		duePayment.setLastUpdatedBy(userDetails.getId());
		duePayment.setCompanyId(userDetails.getCompany_id());
		duePayment.setMarkForDelete(false);
		duePayment.setBillSelectionId(valueObject.getShort("billselectionId",(short)0));
		
		if (valueObject.getString("paidDate") != null) {
			duePayment.setPaidDate(DateTimeUtility.getTimeStamp(valueObject.getString("paidDate"), DateTimeUtility.DDMMYYYY));
		} else {
			String currentDate = dateFormat.format(new java.util.Date());
			java.util.Date currentDateUpdate = dateFormat.parse(currentDate);
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			duePayment.setPaidDate(toDate);
		}
		
		String currentDate = dateFormat.format(new java.util.Date());
		java.util.Date currentDateUpdate = dateFormat.parse(currentDate);
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		duePayment.setCreationDate(toDate);
		duePayment.setLastUpdatedOn(toDate);
		duePayment.setReference(valueObject.getString("reference"));
		
		return duePayment;
	}
	
}

package org.fleetopgroup.persistence.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.constant.CashBookBalanceStatus;
import org.fleetopgroup.constant.CashBookPaymentType;
import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.constant.DriverAdvanceType;
import org.fleetopgroup.constant.DriverAttandancePointStatus;
import org.fleetopgroup.constant.DriverAttandancePointType;
import org.fleetopgroup.constant.DriverLedgerTypeConstant;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.FuelConfigurationConstants;
import org.fleetopgroup.constant.GPSConstant;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TollApiConfiguration;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetConfigurationConstant;
import org.fleetopgroup.constant.TripSheetFixedExpenseType;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleGPSConfiguratinConstant;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.constant.WayBillTypeConstant;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.LoadingSheetToTripSheetBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.bl.TripSheetMobileBL;
import org.fleetopgroup.persistence.bl.UreaEntriesBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.dao.DriverAttendanceRepository;
import org.fleetopgroup.persistence.dao.DriverSalaryAdvanceRepository;
import org.fleetopgroup.persistence.dao.DriverTripSheetBalanceRepository;
import org.fleetopgroup.persistence.dao.FuelRepository;
import org.fleetopgroup.persistence.dao.TripSheetAdvanceRepository;
import org.fleetopgroup.persistence.dao.TripSheetExpenseRepository;
import org.fleetopgroup.persistence.dao.TripSheetIncomeRepository;
import org.fleetopgroup.persistence.dao.TripSheetRepository;
import org.fleetopgroup.persistence.dao.UreaEntriesRepository;
import org.fleetopgroup.persistence.dao.VehicleExtraDetailsRepository;
import org.fleetopgroup.persistence.document.TripSheetDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.DriverHaltDto;
import org.fleetopgroup.persistence.dto.DriverTripSheetPointDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.RefreshmentEntryDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TollExpensesDetailsDto;
import org.fleetopgroup.persistence.dto.TripExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.TripsheetDueAmountDto;
import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleMandatoryDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.CashBook;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.CashBookSequenceCounter;
import org.fleetopgroup.persistence.model.DateWiseVehicleExpense;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverAttendance;
import org.fleetopgroup.persistence.model.DriverHalt;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.fleetopgroup.persistence.model.DriverSalaryInsertionDetails;
import org.fleetopgroup.persistence.model.DriverTripSheetBalance;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.LHPVDetails;
import org.fleetopgroup.persistence.model.MonthWiseVehicleExpense;
import org.fleetopgroup.persistence.model.RouteFixedAllowance;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.model.TripRoute;
import org.fleetopgroup.persistence.model.TripRoutefixedExpense;
import org.fleetopgroup.persistence.model.TripRoutefixedIncome;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetAdvance;
import org.fleetopgroup.persistence.model.TripSheetComment;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetExtraReceived;
import org.fleetopgroup.persistence.model.TripSheetHistory;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.model.TripSheetSequenceCounter;
import org.fleetopgroup.persistence.model.TripSheetToLhpvDetails;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAccidentDetails;
import org.fleetopgroup.persistence.model.VehicleExtraDetails;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookVoucherSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverAttendanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverHaltService;
import org.fleetopgroup.persistence.serviceImpl.IDriverLedgerService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryInsertionDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.ILHPVDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ILoadingSheetToTripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IRefreshmentEntryService;
import org.fleetopgroup.persistence.serviceImpl.IRouteFixedAllowanceService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITicketIncomeApiService;
import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ITripCommentService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteExpenseRangeService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetMobileService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetToLhpvDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ITripsheetSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ITyreUsageHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleMandatoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVendorLorryHireDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.fleetopgroup.persistence.model.RouteWiseTripSheetWeight;

import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.dto.CashBookDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import freemarker.template.Configuration;

import com.mashape.unirest.http.HttpResponse;
import org.json.JSONObject;

@Service("TripSheetMobileService")
public class TripSheetMobileService implements ITripSheetMobileService {
	
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired private TripSheetIncomeRepository 									TripSheetIncomeRepository;
	@Autowired private TripSheetExpenseRepository 									TripSheetExpenseRepository;
	@Autowired private ICompanyConfigurationService 								companyConfigurationService;
	@Autowired private IVehicleGPSDetailsService									vehicleGPSDetailsService;
	@Autowired private IVehicleOdometerHistoryService 								vehicleOdometerHistoryService;
	@Autowired private IVehicleService												vehicleService;
	@Autowired private IUserProfileService											userProfileService;
	@Autowired private IVehicleProfitAndLossService									vehicleProfitAndLossService;
	@Autowired private VehicleProfitAndLossIncomeTxnCheckerService					vehicleProfitAndLossIncomeTxnCheckerService;
	@Autowired private IVehicleProfitAndLossTxnCheckerService						vehicleProfitAndLossTxnCheckerService;
	@Autowired private IVehicleMandatoryService 									vehicleMandatoryService;
	@Autowired private IWorkOrdersService 											workOrdersService;
	@Autowired private ILHPVDetailsService 											lhpvDetailsService;
	@Autowired private ITripsheetSequenceService 									tripsheetSequenceService;
	@Autowired private IDriverService 												driverService;
	@Autowired private ITripRouteService 											tripRouteService;
	@Autowired private ITripSheetToLhpvDetailsService 								tripSheetToLhpvDetailsService;
	@Autowired private IServiceReminderService 										serviceReminderService;
	@Autowired private ITollExpensesDetailsService 									tollExpensesDetailsService;
	@Autowired private IDriverHaltService 											driverHaltService;
	@Autowired private IFuelService 												fuelService;
	@Autowired private IUreaEntriesService 											ureaEntriesService;
	@Autowired private ITripCommentService 											tripCommentService;
	@Autowired private ITripSheetService 											tripSheetService;
	@Autowired private ITripSheetHistoryService 									tripSheetHistoryService; 
	@Autowired private FuelRepository 				 								fuelRepository;
	@Autowired private UreaEntriesRepository 										ureaEntriesRepository;
	@Autowired private ITicketIncomeApiService 										ticketIncomeApiService;
	@Autowired private IDriverAttendanceService 									driverAttendanceService; 
	@Autowired private IDriverSalaryInsertionDetailsService 						driverSalaryInsertionDetailsService;
	@Autowired private IDriverSalaryAdvanceService									driverSalaryAdvanceService;
	@Autowired private	VehicleExtraDetailsRepository 								vehicleExtraDetailsRepository;
	@Autowired private IRefreshmentEntryService										refreshmentEntryService;
	@Autowired private DriverSalaryAdvanceRepository								driverSalaryAdvanceRepository;
	@Autowired private 	ITripRouteExpenseRangeService								tripRouteExpenseRangeService;
	@Autowired private ISequenceCounterService										sequenceCounterService;
	@Autowired private DriverAttendanceRepository									DriverAttendanceRepository;
	@Autowired private MongoTemplate												mongoTemplate;
	@Autowired private ILoadingSheetToTripSheetService								loadingSheetToTripSheetService;
	@Autowired private IRouteFixedAllowanceService									routeFixedAllowanceService;
	@Autowired private TripSheetRepository											tripSheetRepository;
	@Autowired private MongoOperations												mongoOperations;
	@Autowired private TripSheetAdvanceRepository									tripSheetAdvanceRepository;
	@Autowired private IVehicleAccidentDetailsService								accidentDetailsService;
	@Autowired private ITyreUsageHistoryService										tyreUsageHistoryService;
	@Autowired private IDriverLedgerService											driverLedgerService;
	@Autowired private IBankPaymentService											bankPaymentService;
	@Autowired private ICashPaymentService											cashPaymentService;
	@Autowired private DriverTripSheetBalanceRepository								driverTripSheetBalanceRepo;
	@Autowired private IVendorLorryHireDetailsService								lorryHireDetailsService;
	@Autowired private ICashBookSequenceService 									cashBookSequenceService;
	@Autowired private ICashBookVoucherSequenceCounterService						bookVoucherSequenceCounterService;
	@Autowired private ICashBookNameService											cashbookNameService;
	@Autowired private ICashBookService 											CashBookService;
	@Autowired private ITripExpenseService											tripExpenseService;
	@Autowired private  ITripExpenseService											ITripExpenseService;
	
	SimpleDateFormat 					dateFormat_Name 			= new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat 					dateFormat 					= new SimpleDateFormat("dd-MM-yyyy");
	
	LoadingSheetToTripSheetBL			loadingSheetToTripSheetBL	= new LoadingSheetToTripSheetBL();
	VehicleProfitAndLossTxnCheckerBL	txnCheckerBL				= new VehicleProfitAndLossTxnCheckerBL();
	TripSheetBL							tripSheetBL					= new TripSheetBL();
	TripSheetMobileBL					tripSheetMobileBL			= new TripSheetMobileBL();
	VehicleOdometerHistoryBL			vehicleOdometerHistoryBL	= new VehicleOdometerHistoryBL();
	VehicleBL							vehicleBL					= new VehicleBL();
	FuelBL								fuelBL						= new FuelBL();
	UreaEntriesBL						ureaEntriesBL				= new UreaEntriesBL();
	DriverBL						    driverBL					= new DriverBL();
	CashBookBL  						cashbookbl   				= new  CashBookBL();
	DriverReminderBL 	        	 		driverReminderBL		    = new DriverReminderBL();
	SimpleDateFormat 					driverAttendanceFormat 		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat 					dateFormatTime 				= new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat 					SQL_DATE_FORMAT 			= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat 					sqlDateFormat 				= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	DecimalFormat 						toFixedTwo                  = new DecimalFormat("#.##");  
	
	public static final long    		TRIP_SHEET_DEFALAT_VALUE 	= 0L;
	public static final Integer 		TRIP_SHEET_DEFALAT_ZERO	 	= 0;
	public static final Double  		TRIP_SHEET_DEFALAT_AMOUNT 	= 0.0;

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	public java.util.Date removeTime(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	@Override
	public ValueObject searchTripsheet(ValueObject object) throws Exception {
		int 							companyId 				= 0;
		long							userId					= 0;
		CustomUserDetails				userDetails				= null;
		TripSheetDto					trip					= null;
		long							tripSheetNumber			= 0;
		try {
			tripSheetNumber = object.getLong("tripSheetNumber");
			companyId   	= object.getInt("companyId");
			userId			= object.getLong("userId");
			userDetails		= new CustomUserDetails(companyId, userId);
			
			int tripCount =	tripSheetService.countTripSheetByNumber(tripSheetNumber, userDetails.getCompany_id());
			
			if(tripCount == 1) {
				trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetByNumber(tripSheetNumber, userDetails));
				object.put("TripSheet", trip);
				object.put("NumberFound", true);
			}	
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject vehicleStatusCheckWhileCreatingTripSheet(ValueObject object) throws Exception {
		int 							companyId 				= 0;
		int								vehicleId				= 0;
		String 							TID 					= null;
		try {
			companyId   = object.getInt("companyId");
			vehicleId	= object.getInt("vehicleId");
			
			List<TripSheetDto> TRIP = tripSheetService.Get_Validate_TripSheet_Vehicle_DriverAcount(vehicleId, companyId);
			
			if (TRIP != null && !TRIP.isEmpty()) {
				TID = " ";
				for (TripSheetDto add : TRIP) {
					TID += "TS- "+ add.getTripSheetNumber() +",";
				}
			}
			object.put("vehicleStatus", TID);
			
			VehicleDto oddmeter = vehicleService.getVehicle_Select_TripSheet_Details(vehicleId);
			if (oddmeter != null) {
				object.put("vehicleStatusId", oddmeter.getvStatusId());
				object.put("currentVehicleStatus", oddmeter.getVehicle_Status());
			}
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject createTripsheet(ValueObject object) throws Exception {
		int 							companyId 				= 0;
		long							userId					= 0;
		String							email					= null;
		HashMap<String, Object> 		configuration			= null;
		HashMap<String, Object> 		gpsConfiguration		= null;
		try {
			
			companyId   = object.getInt("companyId");
			userId		= object.getLong("userId");
			email		= object.getString("email");
			
			UserProfileDto Orderingname = userProfileService.getUserDeatilsByEmail(email,companyId);
			configuration		= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			object.put("paidBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
			object.put("paidById", userId);
			object.put("place", Orderingname.getBranch_name());
			object.put("placeId", Orderingname.getBranch_id());
			
			object.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, 
					companyConfigurationService.getCompanyConfiguration(companyId, 
							PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, false));

			object.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, 
					companyConfigurationService.getCompanyConfiguration(companyId, 
							PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, false));
			
			ObjectMapper objectMapper = new ObjectMapper();
			object.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));
			object.put("configuration", configuration);
			object.put("companyId", companyId);
			object.put("allowGPSIntegration", (boolean)gpsConfiguration.get("allowGPSIntegration"));
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveOrDispatchTripsheet(ValueObject object,HttpServletRequest request,MultipartFile file) throws Exception {
		TripSheet 						trip 					= null;
		TripSheetAdvance  				advance					= null;
		int 							companyId 				= 0;
		long							userId					= 0;
		List<TripSheetToLhpvDetails>	tripToLhpv				= null;
		VehicleDto 						CheckVehicleStatus 		= null;
		boolean validateTripsheet = false;
		ValueObject						getTokenObject			= null;
		HashMap<String, Object> 		configuration			= null;
		boolean 						lhpvAsExpense			= false;
		try {
			long wasTripSaved = object.getLong("tripsheetId",0);
			configuration		= companyConfigurationService.getCompanyConfiguration(object.getInt("companyId"), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			lhpvAsExpense		= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.LHPV_AS_EXPNESE, false);
			object.put("lhpvAsExpense", lhpvAsExpense);
			object.put("configuration", configuration);
			
			if(object.getInt("vid", 0) == 0) {
				getTokenObject = getAccessToken(request);
				object.put("vidNotFound", true);
				object.put("accessToken", getTokenObject.getString("uniqueID"));
				return object;
			}
			

			if(object.getString("openOdoMeter","").equals("")) {
						getTokenObject = getAccessToken(request);
						object.put("odometerNotFound", true);
						object.put("accessToken", getTokenObject.getString("uniqueID"));
						return object;
					}
			if(object.getString("fromDate") == null || object.getString("fromDate").equals("")  || object.getString("toDate") == null || object.getString("toDate")  .equals("") ) {
				getTokenObject = getAccessToken(request);
				object.put("dateNotFound", true);
				object.put("accessToken", getTokenObject.getString("uniqueID"));
				return object;
			}
			
			if(!validateSaveTripSheet(object)) {
				getTokenObject = getAccessToken(request);
				object.put("accessToken", getTokenObject.getString("uniqueID"));
				return object;
			}
			
			companyId   		= object.getInt("companyId");
			userId				= object.getLong("userId");
			CheckVehicleStatus  = vehicleService.Get_Vehicle_Current_Status_TripSheetID(object.getInt("vid",0), companyId);
			
			if(CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
				trip 				= tripSheetMobileBL.saveOrDispatchTripsheet(object,file);
				advance 			= tripSheetMobileBL.saveOrDispatchTripsheetAdvance(object); 
				object.put("trip", trip);
				object.put("advance", advance);
				object.put("companyId", companyId);
				object.put("userId", userId);
				object.put("CheckVehicleStatus", CheckVehicleStatus);
				if(trip.getTripOpenDate().before(DateTimeUtility.getCurrentDate())) {
					object.put("backDateEntry", true);
				}
				
				if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
					dispatchDetails(object);
					validateTripsheet = object.getBoolean("validateTripsheet");
				}else {
					validateTripsheet = true;
				}
				
				if(validateTripsheet) {
					
					saveDetailsOfDispatchOrSaveInTripsheet(object,file);
					
					List<TripSheetAdvance>  tripAdvanceList		 	= (List<TripSheetAdvance>) object.get("tripAdvanceList");
					List<TripSheetIncome>  	tripSheetIncomeList		= (List<TripSheetIncome>) object.get("tripSheetIncomeList");
					List<LHPVDetails> 		lhpvDetails     	 	= (List<LHPVDetails>)object.get("lhpvDetails");
					List<TripSheetExpense> 	tripSheetExpenseList    = (List<TripSheetExpense>)object.get("tripSheetExpenseList");
					advance.setTripsheet(trip);
					advance.setCompanyId(companyId);
					advance.setCreatedById(userId);						
					advance.setDriverId(trip.getTripFristDriverID());
					if (advance.getAdvanceAmount() != 0) {
						tripSheetService.addTripSheetAdvance(advance);
						if(advance.getPaymentTypeId() != null  &&object.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(advance.getPaymentTypeId()) || advance.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH )){
							ValueObject bankPaymentValueObject = JsonConvertor
									.toValueObjectFormSimpleJsonString(object.getString("bankPaymentDetails"));
							bankPaymentValueObject.put("bankPaymentTypeId",advance.getPaymentTypeId());
							bankPaymentValueObject.put("userId", object.getLong("userId"));
							bankPaymentValueObject.put("companyId", object.getInt("companyId"));
							bankPaymentValueObject.put("moduleId", advance.getTripAdvanceID());
							bankPaymentValueObject.put("moduleNo", trip.getTripSheetNumber());
							bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.TRIP_ADVANCE);
							bankPaymentValueObject.put("amount", advance.getAdvanceAmount());
							//bankPaymentValueObject.put("paidDate", TripIncome.getTripsheetIncomeDate());
							if (advance.getPaymentTypeId() > 0) {
								if (PaymentTypeConstant.PAYMENT_TYPE_CASH == advance.getPaymentTypeId())
									cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
								else
									bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
								}
						}

						
					}
					if ((boolean) configuration.getOrDefault("saveDriverLedgerDetails", false)) {
						ValueObject driveLedgerObj = new ValueObject();
						if (wasTripSaved <= 0) {
							if ((boolean) configuration.getOrDefault("transferDriverBalanceToNextTrip", false)) {
								driveLedgerObj.put("tripCreateEntry", true);
								driveLedgerObj.put("advance", advance);
							}
							driveLedgerObj.put("companyId", companyId);
							driveLedgerObj.put("userId", userId);
							driveLedgerObj.put("driverId", trip.getTripFristDriverID());
							driveLedgerObj.put("transactionId", trip.getTripSheetID());
							driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_ADVANCE);
							driveLedgerObj.put("amount", advance.getAdvanceAmount());
							driveLedgerObj.put("subTransactionId", advance.getTripAdvanceID());
							driveLedgerObj.put("description",
									"Advance Given To Driver Rs : " + advance.getAdvanceAmount()
											+ "For TripSheet Number : " + trip.getTripSheetNumber());
							if(trip.getTripOpenDate() != null)
							driveLedgerObj.put("txnTime",new Timestamp(trip.getTripOpenDate().getTime()));
							driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
							tripSheetService.UPDATE_TOTALADVANCE_IN_TRIPSHEET_TRIPSHEETADVANCE_ID(trip.getTripSheetID(), companyId);
						}
					}				
					
					if(tripAdvanceList != null && !tripAdvanceList.isEmpty())
						tripSheetService.saveTripAdvanceList(tripAdvanceList);
					if(tripSheetIncomeList != null && !tripSheetIncomeList.isEmpty())
						tripSheetService.saveTripIncomeList(tripSheetIncomeList);
					if(tripSheetExpenseList != null && !tripSheetExpenseList.isEmpty())
						tripSheetService.saveTripExpenseList(tripSheetExpenseList);
					
					if (trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
						
						vehicleService.Update_Vehicle_Status_TripSheetID(trip.getTripSheetID(), trip.getVid(),
								companyId, VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE);
						
						if (trip.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
							driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE,
									trip.getTripSheetID(), trip.getTripFristDriverID(), companyId);
						}
						if (trip.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
							driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE,
									trip.getTripSheetID(), trip.getTripSecDriverID(), companyId);
						}
						if (trip.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
							driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE,
									trip.getTripSheetID(), trip.getTripCleanerID(), companyId);
						}
					}
					
					
					if (trip.getRouteID() != TRIP_SHEET_DEFALAT_ZERO) {
						addRouteFixedExpenseForDispatchOrSaveTripsheet(object);
					}
					
					if (trip.getRouteID() != TRIP_SHEET_DEFALAT_ZERO) {
						addRouteFixedIncomeForDispatchOrSaveTripsheet(object);
					}
					
					if(object.getString("hexLhpvIds") != null && !object.getString("hexLhpvIds").trim().equalsIgnoreCase("")) 
						lhpvDetailsService.updateTripSheetCreated(object.getString("hexLhpvIds"), trip.getTripSheetID(), companyId);
					
					
					if(lhpvDetails != null && !lhpvDetails.isEmpty()) {
						tripToLhpv			= new ArrayList<>();
						for(LHPVDetails  details : lhpvDetails) {
							TripSheetToLhpvDetails	sheetToLhpvDetails	= new TripSheetToLhpvDetails();
							
							sheetToLhpvDetails.setTripSheetId(trip.getTripSheetID());
							sheetToLhpvDetails.setLhpvId(details.getLhpvId());
							sheetToLhpvDetails.setLhpvNumber(details.getlHPVNumber());
							sheetToLhpvDetails.setLhpvDate(details.getLhpvDateTimeStamp());
							sheetToLhpvDetails.setLhpvAdvance(details.getAdvanceAmount());
							sheetToLhpvDetails.setLhpvLorryHire(details.getLorryHire());
							sheetToLhpvDetails.setCompanyId(companyId);
							
							tripToLhpv.add(sheetToLhpvDetails);
							
						}
						tripSheetToLhpvDetailsService.saveTripSheetToLhpvDetailsList(tripToLhpv);
					}
					
					object.put("saveTripSheet", true);
					
					System.err.println("tripsheetID---- "+ trip.getTripSheetID());
					Update_Current_OdometerinVehicle_getTripDailySheetTo(trip, companyId, userId);
					
				}
			}else {
				object.put("inActiveStatus", true);
				object.put("vehicleStatus", CheckVehicleStatus.getVehicle_Status());
				getTokenObject = getAccessToken(request);
				object.put("accessToken", getTokenObject.getString("uniqueID"));
				return object;
			}
						

			return object;
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			
		}
	}
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	private boolean validateSaveTripSheet(ValueObject	object) throws Exception{
		try {
			java.util.Date date = null;
			TripSheetDto	sheetDto	= null;
			
			if (object.getString("backDateDispatchTime") != null && !object.getString("backDateDispatchTime").trim().equalsIgnoreCase("")) {
				
				String start_time = "00:00";
				if(object.getString("backDateDispatchTime") != null && object.getString("backDateDispatchTime") != "") {
					start_time	= object.getString("backDateDispatchTime");
				}
				date = DateTimeUtility.getDateTimeFromDateTimeString(object.getString("backDateDispatchDate"), start_time);
			}else {
				date	= new java.util.Date();
			}
			
			sheetDto	=	tripSheetService.getInBetweenTripSheetDetails(object.getInt("vid"), sqlDateFormat.format(date));
			
		TripSheetDto	activeTrip	=	tripSheetService.getActiveTripSheetByVid(object.getInt("vid"));
			
			if(sheetDto != null) {
				object.put("inBetweenTrip", sheetDto);
				return false;
			}else if(!validateDriverStatus(object)) {
				return false;
			}else if(activeTrip != null) {
				object.put("activeTripSheet", activeTrip);
				return false;
			}
			
			
			return true;
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}
	}
	
	private boolean validateDriverStatus(ValueObject	object) throws Exception{
		try {
			String driverIds = "";
			if(object.getInt("driverId",0) > 0) {
				driverIds += object.getInt("driverId", 0);
			}
			if(object.getInt("driver2Id",0) > 0) {
				driverIds += ","+object.getInt("driver2Id", 0);
			}
			if(object.getInt("cleanerId",0) > 0) {
				driverIds += ","+object.getInt("cleanerId", 0);
			}
			
			HashMap<Integer, DriverDto>  driverHM = driverService.getDriverStatusHM(driverIds);
			
			if(driverHM != null && !driverHM.isEmpty()) {
				if(driverHM.get(object.getInt("driverId",0)) != null) {
					object.put("driverOneNotActive", true);
				}
				if(driverHM.get(object.getInt("driver2Id",0)) != null) {
					object.put("driverTwoNotActive", true);
				}
				if(driverHM.get(object.getInt("cleanerId",0)) != null) {
					object.put("cleanerNotActive", true);
				}
				return false;
			}
			return true;
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}
	}
	
	@Transactional
	public void saveDetailsOfDispatchOrSaveInTripsheet(ValueObject object,MultipartFile file) throws Exception {
		List<LHPVDetails>				lhpvDetails				= null;
		TripSheet						savedTripsheet			= null;
		try {
			
			TripSheet 			trip 			= (TripSheet)object.get("trip");
			TripSheetAdvance  	advance			= (TripSheetAdvance)object.get("advance"); 	
			int					companyId 	    = object.getInt("companyId");
			long				userId			= object.getLong("userId");

			String lHPVDetailsIds	= object.getString("hexLhpvIds");
			if(lHPVDetailsIds != null && !lHPVDetailsIds.trim().equalsIgnoreCase("")) {
				lhpvDetails		=	lhpvDetailsService.getLHPVDetailsList(lHPVDetailsIds, companyId);
				object.put("lhpvDetails", lhpvDetails);
			}

				if (advance.getAdvanceAmount() != 0) {
					trip.setTripTotalAdvance(advance.getAdvanceAmount());
					trip.setTripTotalexpense(TRIP_SHEET_DEFALAT_AMOUNT);
					trip.setTripTotalincome(TRIP_SHEET_DEFALAT_AMOUNT);
				} else {
					trip.setTripTotalAdvance(TRIP_SHEET_DEFALAT_AMOUNT);
					trip.setTripTotalexpense(TRIP_SHEET_DEFALAT_AMOUNT);
					trip.setTripTotalincome(TRIP_SHEET_DEFALAT_AMOUNT);
				}
					
				if(lhpvDetails != null && !lhpvDetails.isEmpty()) {
					if(object.getBoolean("lhpvAsExpense",false)) {
						addLHPV_AsExpenseDetailsInSaveOrDispatchTripsheet(object);
					}else {
						addLHPVIncomeAndAdvanceDetailsInSaveOrDispatchTripsheet(object);
					}
				}
				
				if (trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {

					trip.setDispatchedLocationId(advance.getAdvancePlaceId());
					trip.setDispatchedById(userId);
					if(trip.getDispatchedByTime() == null) {
						
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp dispatchedByTime = new java.sql.Timestamp(currentDateUpdate.getTime());
						
						trip.setDispatchedByTime(dispatchedByTime);
					}
				}

				trip.setMarkForDelete(false);
				trip.setCreatedById(userId);
				trip.setLastModifiedById(userId);
				trip.setCompanyId(companyId);
				TripSheetSequenceCounter sequenceCounter = tripsheetSequenceService
						.findNextSequenceNumber(companyId);
				trip.setTripSheetNumber(sequenceCounter.getNextVal());
				
				
				if (trip.getRouteID() != TRIP_SHEET_DEFALAT_ZERO) {
					
					TripRoute routePoint = tripRouteService.getTriproutePoint(trip.getRouteID(), companyId);
					
					if (trip.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
						trip.setTripFristDriverRoutePoint(routePoint.getRoutrAttendance());
					} else {
						trip.setTripFristDriverRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
					}
					
					if (trip.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
						trip.setTripSecDriverRoutePoint(routePoint.getRoutrAttendance());
					} else {
						trip.setTripSecDriverRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
					}
					
					if (trip.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
						trip.setTripCleanerRoutePoint(routePoint.getRoutrAttendance());
					} else {
						trip.setTripCleanerRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
					}
				}
				
				savedTripsheet = tripSheetService.addTripSheet(trip);
				
				//if(file != null && !file.isEmpty()|| object.getString("base64String",null) != null) {
				
				if(trip.isTripSheetDocument()) {	
					saveTripDocument(savedTripsheet,object,file);
				}
				
				object.put("tripsheetId", trip.getTripSheetID());
				object.put("tripsheetID", savedTripsheet.getTripSheetID());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void addLHPVIncomeAndAdvanceDetailsInSaveOrDispatchTripsheet(ValueObject object) throws Exception {
		List<TripSheetAdvance>  		tripAdvanceList					= null;
		List<TripSheetIncome>  			tripSheetIncomeList				= null;
		Double							tripTotalAdvance				= 0.0;
		Double							tripTotalIncome					= 0.0;
		try {
			tripSheetIncomeList	= new ArrayList<>();
			tripAdvanceList		= new ArrayList<>();
			
			List<LHPVDetails> 	lhpvDetails     = (List<LHPVDetails>)object.get("lhpvDetails");
			TripSheet 			trip 			= (TripSheet)object.get("trip");
			TripSheetAdvance  	advance			= (TripSheetAdvance)object.get("advance"); 	
			int					companyId 	    = object.getInt("companyId");
			long				userId			= object.getLong("userId");
			
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			for(LHPVDetails  details : lhpvDetails) {
				if(details != null && details.getLorryHire() > 0) {
					TripSheetIncome	tripIncome	= new TripSheetIncome();

					tripIncome.setIncomeId(Integer.parseInt("" + details.getlHPVDetailsId()));
					tripIncome.setIncomeAmount(details.getLorryHire());
					tripIncome.setIncomePlaceId(advance.getAdvancePlaceId());
					tripIncome.setIncomeRefence("LHPV NO : "+details.getlHPVNumber());
					tripIncome.setIncomeCollectedById(userId);
					tripIncome.setCompanyId(companyId);
					tripIncome.setCreatedById(userId);
					tripIncome.setTripsheet(trip);
					tripIncome.setlHPVDetailsId(details.getlHPVDetailsId());
					
					tripIncome.setCreated(toDate);
					
					tripSheetIncomeList.add(tripIncome);
					
					if(details != null && details.getAdvanceAmount() > 0) {
						TripSheetAdvance	sheetAdvance = new TripSheetAdvance();
						sheetAdvance.setAdvanceAmount(details.getAdvanceAmount());

						sheetAdvance.setAdvancePlaceId(advance.getAdvancePlaceId());
						sheetAdvance.setAdvancePaidbyId(advance.getAdvancePaidbyId());
						sheetAdvance.setAdvanceRefence("LHPV NO : "+details.getlHPVNumber());
						sheetAdvance.setAdvanceRemarks("LHPV Advance For Lhpv NO "+details.getlHPVNumber());
						sheetAdvance.setTripsheet(trip);
						sheetAdvance.setCompanyId(companyId);
						sheetAdvance.setCreatedById(userId);

						sheetAdvance.setCreated(toDate);
						tripAdvanceList.add(sheetAdvance);
					}
						
				}
				
				tripTotalAdvance += details.getAdvanceAmount();
				tripTotalIncome  += details.getLorryHire();
			}
			
			trip.setTripTotalAdvance(trip.getTripTotalAdvance() + tripTotalAdvance);
			trip.setTripTotalincome(trip.getTripTotalincome() + tripTotalIncome);
			
			object.put("tripSheetIncomeList", tripSheetIncomeList);
			object.put("tripAdvanceList", tripAdvanceList);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addRouteFixedExpenseForDispatchOrSaveTripsheet (ValueObject object) throws Exception {
		try {
			TripSheet 			trip 			= (TripSheet)object.get("trip");
			int					companyId 	    = object.getInt("companyId");
			long				userId			= object.getLong("userId");
			HashMap<String, Object>		configuration	= (HashMap<String, Object>) object.get("configuration");
			
			List<TripRoutefixedExpense> routefixedExpense = tripRouteService.listTripRouteFixedExpenes(trip.getRouteID(), companyId);
			List<RouteFixedAllowance>	allowanceList	  = routeFixedAllowanceService.getRouteFixedAllowanceList(trip.getRouteID(), companyId);
			if (routefixedExpense != null && !routefixedExpense.isEmpty()) {
				for (TripRoutefixedExpense tripRoutefixedExpense : routefixedExpense) {

					TripSheetExpense TripExpense = new TripSheetExpense(
							tripRoutefixedExpense.getExpenseId(), tripRoutefixedExpense.getExpenseAmount(),
							tripRoutefixedExpense.getExpensePlaceId(),
							tripRoutefixedExpense.getExpenseRefence(),
							tripRoutefixedExpense.getFixedTypeId());
					TripExpense.setTripsheet(trip);
					TripExpense.setExpenseId(tripRoutefixedExpense.getExpenseId());
					TripExpense.setExpensePlaceId(tripRoutefixedExpense.getExpensePlaceId());
					TripExpense.setExpenseFixedId(tripRoutefixedExpense.getFixedTypeId());
					TripExpense.setCreatedById(userId);
					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					TripExpense.setCreated(toDate);
					TripExpense.setCompanyId(companyId);
					TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());
					tripSheetService.addTripSheetExpense(TripExpense);

					Object[] expenseIds = tripSheetService.getDriverAdvanceAndBataExpenseId(companyId);

					// Note : This Update Bata Details in Trip Sheet

					if (expenseIds != null) {
						if (expenseIds[0] != null
								&& expenseIds[0] == tripRoutefixedExpense.getExpenseId()) {

						}
						if (expenseIds[1] != null
								&& expenseIds[1] == tripRoutefixedExpense.getExpenseId()) {
							tripSheetService.Update_TripSheet_FIRST_Driver_BATA(
									tripRoutefixedExpense.getExpenseAmount(), trip.getTripSheetID(), companyId);
						}
						if (expenseIds[2] != null
								&& expenseIds[2] == tripRoutefixedExpense.getExpenseId()) {

						}
						if (expenseIds[3] != null
								&& expenseIds[3] == tripRoutefixedExpense.getExpenseId()) {
							tripSheetService.Update_TripSheet_SECOND_Driver_BATA(
									tripRoutefixedExpense.getExpenseAmount(), trip.getTripSheetID(), companyId);
						}
						if (expenseIds[4] != null
								&& expenseIds[4] == tripRoutefixedExpense.getExpenseId()) {

						}
						if (expenseIds[5] != null
								&& expenseIds[5] == tripRoutefixedExpense.getExpenseId()) {
							tripSheetService.Update_TripSheet_CLEANER_BATA(
									tripRoutefixedExpense.getExpenseAmount(), trip.getTripSheetID(), companyId);
						}
					}

				}


			}
			if(allowanceList != null && !allowanceList.isEmpty()) {
				Double pfPercentage		= Double.parseDouble(configuration.get("pfPercentage")+"");
				Double esiPeercntage	= Double.parseDouble(configuration.get("esiPercetage")+"");
				
				Map<Integer, RouteFixedAllowance> driverJobIdWiseHM =
						allowanceList.stream().collect(Collectors.toMap(RouteFixedAllowance::getDriJobId,
					                                              Function.identity()));
				
				
				if(driverJobIdWiseHM != null && !driverJobIdWiseHM.isEmpty()) {
					
					if(trip.getTripFristDriverID() > 0) {
						TripSheetExpense tripExpense = getTripSheetExpenseDToForAllowance(trip.getTripFristDriverID(), trip, driverJobIdWiseHM, companyId, pfPercentage, esiPeercntage, userId);
						if(tripExpense != null)		
							tripSheetService.addTripSheetExpense(tripExpense);
						
					}
					if(trip.getTripSecDriverID() > 0) {
						TripSheetExpense tripExpense = getTripSheetExpenseDToForAllowance(trip.getTripSecDriverID(), trip, driverJobIdWiseHM, companyId, pfPercentage, esiPeercntage, userId);
						if(tripExpense != null)		
							tripSheetService.addTripSheetExpense(tripExpense);
						
					}
					if(trip.getTripCleanerID() > 0) {
						TripSheetExpense tripExpense = getTripSheetExpenseDToForAllowance(trip.getTripCleanerID(), trip, driverJobIdWiseHM, companyId, pfPercentage, esiPeercntage, userId);
						if(tripExpense != null)		
							tripSheetService.addTripSheetExpense(tripExpense);
						
					}
				}
			}
			tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(trip.getTripSheetID(), companyId);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	private TripSheetExpense getTripSheetExpenseDToForAllowance(Integer driverId, TripSheet trip, Map<Integer, RouteFixedAllowance> driverJobIdWiseHM, Integer companyId, Double pfPercentage, Double esiPeercntage, Long userId) throws Exception {
		try {
			Driver	driver	= driverService.getLimitedDriverDetails(driverId);
			if(driver != null && driver.getSalariedId() == 2 && driverJobIdWiseHM.get(driver.getDriJobId()) != null) {
				TripSheetExpense tripExpense = new TripSheetExpense(driverJobIdWiseHM.get(driver.getDriJobId()).getAmount(),"Fixed Allowance", companyId);
				tripExpense.setTripsheet(trip);
				tripExpense.setExpenseId(driverJobIdWiseHM.get(driver.getDriJobId()).getExpenseId());
				tripExpense.setExpensePlaceId(0);
				tripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_FIXED);
				tripExpense.setCreatedById(userId);
				tripExpense.setPfAmount(tripExpense.getExpenseAmount() * pfPercentage/100);
				tripExpense.setEsiAmount(tripExpense.getExpenseAmount() * esiPeercntage/100);
				tripExpense.setBalanceAmount(tripExpense.getExpenseAmount() - tripExpense.getPfAmount() - tripExpense.getEsiAmount());
				tripExpense.setDriverId(driverId);
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				tripExpense.setCreated(toDate);  
				
				
				return tripExpense;

			}
			
			return null;
		
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}
		
	}
	
	public void addRouteFixedIncomeForDispatchOrSaveTripsheet (ValueObject object) throws Exception {
		try {
			TripSheet 			trip 			= (TripSheet)object.get("trip");
			int					companyId 	    = object.getInt("companyId");
			long				userId			= object.getLong("userId");
			
			List<TripRoutefixedIncome> RoutefixedIncome	=null;
			RoutefixedIncome	= tripRouteService.listTripRouteFixedIncomeList(trip.getRouteID(), companyId);
			if (RoutefixedIncome != null && !RoutefixedIncome.isEmpty()) {
				for (TripRoutefixedIncome tripRoutefixedIncome : RoutefixedIncome) {
					TripSheetIncome TripIncome=null;
					TripIncome = new TripSheetIncome(
					tripRoutefixedIncome.getIncomeAmount(),
					tripRoutefixedIncome.getIncomeRefence(),
					tripRoutefixedIncome.getCompanyId());
					 if(TripIncome!=null) {
					TripIncome.setTripsheet(trip);
					//TripIncome.setTripincomeID((tripRoutefixedIncome.getRoutefixedID()).longValue());
					TripIncome.setIncomePlaceId(tripRoutefixedIncome.getIncomePlaceId());
					TripIncome.setIncomeId(tripRoutefixedIncome.getIncomeId());
					TripIncome.setCreatedById(userId);
					TripIncome.setIncomeFixedId(tripRoutefixedIncome.getIncomeFixedId());
					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
					TripIncome.setCreated(toDate);
					TripIncome.setCompanyId(companyId);
					TripIncome.setIncomeCollectedById(userId);// here we used UserId
					TripIncome.setIncomeRefence(tripRoutefixedIncome.getIncomeRefence());
					 }
					 tripSheetService.addTripSheetIncome(TripIncome);


				}

				tripSheetService.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(trip.getTripSheetID(), companyId);
				}
			
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public void dispatchDetails(ValueObject object) throws Exception {
		TripSheet 			trip 							= null;
		VehicleDto 			CheckVehicleStatus 				= null;
		boolean 			checkFuelMeterStatus 			= false;
		boolean 			isNewVehicleTripsheet 			= false;
		int					companyId						= 0;
		try {
			trip 			 = (TripSheet)object.get("trip");
			companyId		 = object.getInt("companyId");
			
			HashMap<String, Object> gpsConfiguration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.VEHICLE_GPS_CONFIG);
									
			if (trip.getVid() == null || trip.getVid() <= 0) {
				isNewVehicleTripsheet = true;
			}
			
			CheckVehicleStatus = (VehicleDto) object.get("CheckVehicleStatus");
			if (!isNewVehicleTripsheet || (boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION) || CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
				
				checkFuelMeterStatus = true;
			}
			
			object.put("checkFuelMeterStatus", checkFuelMeterStatus);
			object.put("isNewVehicleTripsheet", isNewVehicleTripsheet);
			
			if (checkFuelMeterStatus || isNewVehicleTripsheet) {
				validateDispatchDetailsByDateAndTime(object);
			} else {
				validateDispatchDetailsByVehicleStatus(object);
			}
			
		}catch(Exception e) {
			throw e;
		} finally {
			
		}
	}
	
	public void validateDispatchDetailsByDateAndTime(ValueObject object) throws Exception {
		List<TripSheetDto> 	 	ValidateTSOlD 			= null;
		boolean 				validateTripsheet 		= false;
		String 					TIDMandatory 			= "";
		try {
			
			
			boolean 		isNewVehicleTripsheet = object.getBoolean("isNewVehicleTripsheet");
			TripSheet	 	trip 				  = (TripSheet)object.get("trip");
			int				companyId			  = object.getInt("companyId");
			
			HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
									
			
			if (!isNewVehicleTripsheet) {
				ValidateTSOlD = tripSheetService.Get_Validate_TripSheet_Vehicle_DriverAcount(trip.getVid(),
						companyId);
				
				validateTripsheet = true;
			} else {
				
				ValidateTSOlD = tripSheetService.Get_Validate_TripSheet_Vehicle(
						object.getString("vehicleRegistration"), companyId);
				
				String TID = "";
				for (TripSheetDto add : ValidateTSOlD) {
					TID += " "+add.getTripSheetNumber()+",";
				}
				
				object.put("CloseTS", TID);
				validateTripsheet = false;
			}

			java.sql.Date tripfromDate 	= new Date(trip.getTripOpenDate().getTime());
			java.sql.Date triptoDate 	= new Date(trip.getClosetripDate().getTime());
			java.util.Date currentDate	= new java.util.Date();
			
			if(removeTime(tripfromDate).equals(removeTime(currentDate)) || (boolean)configuration.get(TripSheetConfigurationConstant.ALLOW_BACK_DATE_ENTRY)) {
				
				// Note: Below Check Vehicle Mandatory Compliance
				if (!isNewVehicleTripsheet) {
					
						List<VehicleMandatoryDto> Select = vehicleMandatoryService
								.List_Vehicle_Mandatory_Details_GET_VEHICLEID_NOT_EXISTS(trip.getVid(),
										tripfromDate, triptoDate, companyId);
						
						if (Select != null && !Select.isEmpty()) {
							// Checking the Value Of Mandatory Compliance
							for (VehicleMandatoryDto add : Select) {
								TIDMandatory += "" + add.getVEHICLE_REGISTRATION()
								+ " Mandatory Compliance "
								+ add.getVEHICLE_ID() + ""
								+ add.getMANDATORY_RENEWAL_SUB_NAME()
								+ " is NOT Available On "
								+ tripfromDate + " To " + triptoDate + " ";
							}
							object.put("VMandatory", TIDMandatory);
							validateTripsheet = false;

						} else {
							// Showing Mandatory Compliance to Set Vehicle
							TIDMandatory += "This Vehicle Mandatory Compliances are Available On "
									+ tripfromDate + " To " + triptoDate
									+ " Any Changes Mandatory Compliances ";
							object.put("VMandatory", TIDMandatory);

						}
					
				}

			} else {
				
				TIDMandatory += " This Tripsheet Start Date Not Match Today. You can't Dispatch. Please change Journey Date.";
				object.put("VMandatory", TIDMandatory);
				validateTripsheet = false;
			}

			object.put("validateTripsheet", validateTripsheet);
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public void validateDispatchDetailsByVehicleStatus (ValueObject object) throws Exception {
		String 					Link 					= "";
		String 					TIDMandatory 			= "";
		boolean 				validateTripsheet 		= false;
		try {
			TripSheet 	trip 				  = (TripSheet)object.get("trip");
			VehicleDto	CheckVehicleStatus	  = (VehicleDto) object.get("CheckVehicleStatus");
			
			
			if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
				if(CheckVehicleStatus.getTripSheetNumber()!=null){
				TripSheet tripSheet = tripSheetService.getTripSheet(CheckVehicleStatus.getTripSheetID());
				Link += "  Vehicle is on TripRoute on TS - "+ tripSheet.getTripSheetNumber();
				}
			} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
				if(CheckVehicleStatus.getWorkOrder_Number()!=null) {
				Link += "WO-" + workOrdersService.getWorkOrders(CheckVehicleStatus.getTripSheetID()).getWorkorders_Number();
				}
			} else if (trip.getTripOpeningKM() > CheckVehicleStatus.getVehicle_ExpectedOdameter() && !object.getBoolean("backDateEntry",false)) {

				Link += " Maxminum Allow Odometer is " + CheckVehicleStatus.getVehicle_ExpectedOdameter()
				+ " kindly Check Odometer ";
			}

			TIDMandatory += " This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
					+ CheckVehicleStatus.getVehicle_Status() + " Status you can't dispatch TripSheet. "
					+ " You can save Tripsheet. "+Link;
			object.put("TIDMandatory", TIDMandatory);
			
			
			validateTripsheet = false;
			object.put("validateTripsheet", validateTripsheet);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public void Update_Current_OdometerinVehicle_getTripDailySheetTo(TripSheet Sheet, int compId, long userId) {
		
		List<ServiceReminderDto>		serviceList					= null;
		int 							currentODDMETER;
		try {
			currentODDMETER = vehicleService.updateCurrentOdoMeterGetVehicleCurrentOdometer(Sheet.getVid(), compId);

			// update the Current Odometer vehicle Databases tables
			try {
				if (Sheet.getVid() != TRIP_SHEET_DEFALAT_ZERO) {
					if (currentODDMETER < Sheet.getTripOpeningKM()) {
						
						vehicleService.updateCurrentOdoMeter(Sheet.getVid(), Sheet.getTripOpeningKM(), compId);
						// update current Odometer update Service
						// Reminder
						serviceReminderService.updateCurrentOdometerToServiceReminder(Sheet.getVid(),
								Sheet.getTripOpeningKM(), compId);
						
						serviceList = serviceReminderService.OnlyVehicleServiceReminderList(Sheet.getVid(), compId, userId);
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									serviceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									//emailAlertQueueService.sendEmailServiceOdometer(list);
									//smsAlertQueueService.sendSmsServiceOdometer(list);
								}
							}
						}

						VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL.prepareOdometerGetHistoryToTripsheet(Sheet);
						vehicleUpdateHistory.setCompanyId(compId);
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						
					}else if(currentODDMETER == Sheet.getTripOpeningKM()) {
						VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL.prepareOdometerGetHistoryToTripsheet(Sheet);
						vehicleUpdateHistory.setCompanyId(compId);
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValueObject showTripSheetDetails(ValueObject object) throws Exception {
		HashMap<String, Object>    	config						= null;
		HashMap<String, Object>    	configuration				= null;
		boolean						hideClosedTripSheet			= false;
		CustomUserDetails 			userDetails 				= null;
		TripSheetDto 				trip 						= null;
		double 						fuelAmount					= 0.0;
		double 						ureaAmount					= 0.0;
		double 						tollAmount					= 0.0;
		Double 						TotalExpense				= 0.0;
		List<FuelDto> 				ShowAmount					= null;
		HashMap<String, Object>    	gpsConfig					= null;
		boolean						showCombineTripDetails		= false;
		List<TripSheetExpenseDto> 	finalExpenseList			= null;
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		Double						totalDueAmount				= 0.0;
		boolean						showTripsheetDueAmount		= false;
		Double 						tripTotalincome 			= 0.0;
		List<TripSheetExpenseDto> 	expenseDto			 		= null;
		HashMap<String, Object>    	lorryHireConfig				= null;
		Double  					driverBalance				= null;
		Double 						B_Income					= 0.0;	
		Double 						E_Income					= 0.0;	
		Double 						B_IncomeDue					= 0.0;	
		Double 						E_IncomeDue					= 0.0;	
		Double 						tripbalance					= 0.0;
		
		try {
			
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			configuration			= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.TOLL_API_CONFIG);
			config					= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			gpsConfig				= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.VEHICLE_GPS_CONFIG);
			lorryHireConfig			= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.LORRY_HIRE_CONFIG);
			hideClosedTripSheet		= (boolean) config.getOrDefault(TripSheetConfigurationConstant.HIDE_CLOSED_TRIPSHEET, false);
			showCombineTripDetails	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.SHOW_COMBINE_TRIP_DETAILS, false);
			showTripsheetDueAmount	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.SHOW_TRIPSHEET_DUE_AMOUNT, false);
			
			userDetails				= new CustomUserDetails();
			userDetails.setCompany_id(companyId);
			userDetails.setId(userId);
			
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
			object.put("permissions", permission);
			
			trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			
			if (trip != null) {
				
				if(hideClosedTripSheet && !permission.contains(new SimpleGrantedAuthority("SHOW_TRIPSHEET_CLOSE_STATUS"))){
					if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED || trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
						object.put("NotFound", true);
					}
				}
				
				object.put("gpsConfig", gpsConfig);
				object.put("configuration", config);
				
				
				boolean allowTollApiIntegration = false;
				if((boolean)configuration.get(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION) || (boolean)configuration.get(TollApiConfiguration.ALLOW_KVB_BANK_TOLL_API)) {
					allowTollApiIntegration = true;
				}
				object.put(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION, allowTollApiIntegration);
				
				// Advance Details
				object.put("TripSheetAdvance",tripSheetService.findAllTripSheetAdvance(tripsheetId, userDetails.getCompany_id()));
				
				// Expense Details
				if(showCombineTripDetails) {
					List<TripSheetExpenseDto> expenseCombine = tripSheetService.findAllTripSheetExpense(tripsheetId, userDetails.getCompany_id());
					if(expenseCombine != null && !expenseCombine.isEmpty()) {
						HashMap<Integer, TripSheetExpenseDto> expenseMap = tripSheetService.findAllTripSheetExpenseCombineMap(expenseCombine);
						if(expenseMap != null) {
							finalExpenseList = new ArrayList<TripSheetExpenseDto>();
							finalExpenseList.addAll(expenseMap.values());
						}
						object.put("ExpenseCombineList",finalExpenseList);
					}
				} else {
					expenseDto = tripSheetService.findAllTripSheetExpense(tripsheetId, userDetails.getCompany_id());
					
					object.put("TripSheetExpense",expenseDto);
				}

				// TollExpense Details
				ValueObject objectToll = tollExpensesDetailsService.getTollExpensesDetailsList(tripsheetId, userDetails.getCompany_id());
				tollAmount	=	objectToll.getDouble("totalAmount", 0);
				object.put("TripSheetTollExpense",(List<TollExpensesDetailsDto>)objectToll.get("ExpenseName"));
				object.put("TripSheetTollExpenseName",configuration.get(TollApiConfiguration.TOLL_EXPENSE_NAME));
				object.put("TripSheetTollExpenseDate",objectToll.get("Date"));
				object.put("TripSheetTollExpenseTotalAmount",objectToll.get("totalAmount"));

				// All Extra Details
				object.put("TripSheetExtraOptions",	tripSheetService.findAllTripSheetExtraOptions(tripsheetId, userDetails.getCompany_id()));
				object.put("TripSheetExtraOptionsReceived", tripSheetService.findAllTripSheetExtraOptionsReceived(tripsheetId, userDetails.getCompany_id()));
				
				object.put("TripSheetRouteWiseWeight" , tripSheetService.findAllTripSheetRouteWiseDetails(tripsheetId, userDetails.getCompany_id()));
				
				// Income Details
				List<TripSheetIncomeDto> incomeDto = tripSheetService.findAllTripSheetIncome(tripsheetId, userDetails.getCompany_id());
				
				if(incomeDto!=null && !incomeDto.isEmpty())
					Collections.sort(incomeDto, Comparator.comparing(TripSheetIncomeDto::getBillSelectionId));
				
				if(incomeDto !=null && (boolean)config.get("getBIncomeEIncome")) {
					for(TripSheetIncomeDto income:incomeDto) {
						if(income.getBillSelectionId() == WayBillTypeConstant.WITH_BILL) {	
							B_Income  += income.getIncomeAmount();
						}else if(income.getBillSelectionId() == WayBillTypeConstant.WITHOUT_BILL) {
							E_Income  += income.getIncomeAmount();
						}
					}
				}
				
				object.put("TripSheetIncome",incomeDto);
				if((boolean) config.get(TripSheetConfigurationConstant.ALLOW_IV_LOADING_SHEET_ENTRY)) {
					object.put("wayBillTypeWiseHM",TripSheetBL.getWayBillTypeWiseLSAmount(loadingSheetToTripSheetService.getLoadingSheetToTripSheetByTripSheetId(tripsheetId)));
				}
				
				
				DecimalFormat df = new DecimalFormat("##,##,##0");
				df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
				Double TotalAdvance = trip.getTripTotalAdvance();
				if (trip.getTripTotalAdvance() == null) {
					TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
				}
				object.put("advanceTotal", df.format(TotalAdvance));
				df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
				TotalExpense = trip.getTripTotalexpense();
				if (trip.getTripTotalexpense() == null) {
					TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
				}
				object.put("expenseTotal", df.format(TotalExpense));
				df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
				Double Totalincome = trip.getTripTotalincome();
				if (trip.getTripTotalincome() == null) {
					Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
				}
				object.put("incomeTotal", df.format(Totalincome));
				
				
				Boolean TripStatus = false;
				if (trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED || trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
					TripStatus = true;
				} else if ((boolean) config.get("allTableRequiredBeforeClose")) {
					TripStatus = true;
				}
				if (TripStatus) {
					// show Halt data
					object.put("TripSheetHalt", (driverHaltService.Find_list_TripSheet_DriverHalt(tripsheetId, companyId)));

					VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Fuel_Entries_Show(vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(trip.getVid(), companyId));
					object.put("vehicle", vehicle);
					
					// show fuel entries in this tripsheet
					ShowAmount = fuelService.Get_TripSheet_IN_FuelTable_List(tripsheetId, companyId, userId);
					object.put("fuel", fuelBL.prepareListofFuel(ShowAmount));
					object.put("fTDiesel", fuelBL.prepareTotal_Tripsheet_fuel_details(ShowAmount));
					object.put("fTUsage", fuelBL.prepareTotalUsage_Tripsheet_fuel_details(ShowAmount));
					fuelAmount= fuelBL.prepareTotalAmount_Tripsheet_fuel_details(ShowAmount);
					object.put("fTAmount", fuelAmount);
					
					
					List<UreaEntriesDto> ureaDeatils = ureaEntriesService.getUreaEntriesDetailsByTripSheetId(tripsheetId, userDetails.getCompany_id());
					if(ureaDeatils != null) {
					object.put("urea", ureaDeatils);
					object.put("totalUrea", ureaEntriesBL.prepareTotal_Tripsheet_Urea_details(ureaDeatils));
					ureaAmount = ureaEntriesBL.prepareTotal_Tripsheet_Urea_Amount(ureaDeatils);
					object.put("totalUreaAmnt",ureaAmount);
					tripTotalincome = Totalincome-(fuelAmount + ureaAmount + tollAmount + TotalExpense);
					object.put("tripTotalincome", (toFixedTwo.format(tripTotalincome)));
					}
					
					object.put("TripSheetTollExpense",(List<TollExpensesDetailsDto>)objectToll.get("ExpenseName"));
					object.put("TripSheetTollExpenseName",configuration.get(TollApiConfiguration.TOLL_EXPENSE_NAME));
					object.put("TripSheetTollExpenseDate",objectToll.get("Date"));
					object.put("TripSheetTollExpenseTotalAmount",objectToll.get("totalAmount"));

					ShowAmount = fuelService.Get_TripSheet_IN_FuelTable_List(tripsheetId, companyId, userId);
					fuelAmount= fuelBL.prepareTotalAmount_Tripsheet_Created_fuel_details(ShowAmount);
					
					
					object.put("TripSheetTollExpense",(List<TollExpensesDetailsDto>)objectToll.get("ExpenseName"));
					object.put("TripSheetTollExpenseName",configuration.get(TollApiConfiguration.TOLL_EXPENSE_NAME));
					object.put("TripSheetTollExpenseDate",objectToll.get("Date"));
					object.put("TripSheetTollExpenseTotalAmount",objectToll.get("totalAmount"));
					
					
				}
				 tripTotalincome = Totalincome-(fuelAmount + ureaAmount + tollAmount + TotalExpense);
				
				 object.put("tripTotalincome", (toFixedTwo.format(tripTotalincome)));
				object.put("DriverAdvanvce",driverSalaryAdvanceService.List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(tripsheetId, DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY, companyId));
				
				
				List<TripsheetDueAmountDto> dueAmountList = null;
 				
				if(showTripsheetDueAmount) {
 					dueAmountList  = tripSheetService.getDueAmountListByTripsheetId(tripsheetId, userDetails.getCompany_id());
					 
					object.put("TripsheetDueAmount", dueAmountList);
					
					if(dueAmountList !=null) {
						for(TripsheetDueAmountDto due:dueAmountList) {
							if(due.getBillSelectionId() !=null) {
								if(due.getBillSelectionId() == WayBillTypeConstant.WITH_BILL) {
									B_IncomeDue  += due.getDueAmount();
								}else if(due.getBillSelectionId() == WayBillTypeConstant.WITHOUT_BILL) {
									E_IncomeDue  += due.getDueAmount();
								}
							}
						}
					}
					
					object.put("TotalDueAmount",
							tripSheetService.getTotalDueAmountByTripsheetId(tripsheetId, userDetails.getCompany_id()));
					totalDueAmount = (double) object.get("TotalDueAmount");
				}
				ValueObject	refreshmentObj	= refreshmentEntryService.getRefreshmentEntryListByTripSheetId(trip.getTripSheetID());
				if(refreshmentObj != null) {
					object.put("refreshment",(List<RefreshmentEntryDto>) refreshmentObj.get("refreshment"));
					object.put("grandTotal", refreshmentObj.getDouble("grandTotal"));
					object.put("totalQty", refreshmentObj.getDouble("totalQty"));
					object.put("totalRQty", refreshmentObj.getDouble("totalRQty"));
					object.put("totalConsumption", refreshmentObj.getDouble("totalConsumption"));
					TotalExpense += refreshmentObj.getDouble("grandTotal",0);
				}
				if((boolean) config.get("roundFigureAmount")) {
					TotalExpense = (double) Math.round(TotalExpense);
					Totalincome = (double) Math.round(Totalincome);
					TotalAdvance = (double) Math.round(TotalAdvance);
				}
				
				if((boolean) config.get("reverseDriverBalance")) {
				 	object.put("driverBalance", Utility.round(( TotalExpense - (Totalincome + TotalAdvance)), 2));
				}
				else if((boolean) config.get("getDriverBalance"))
				{
					if((boolean) config.get("IncCashExpenseInDriBal")) {
						List<TripSheetExpense> expenses = TripSheetExpenseRepository.findAllTripSheetExpense(tripsheetId, companyId);
						double expenseAmount = 0.0;
						for(TripSheetExpense  expense: expenses){
							if(expense.getFuel_id() == null){
								if(expense.getPaymentTypeId() !=null) {
									if(expense.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CASH) {
										expenseAmount += expense.getExpenseAmount();
									}
								}
							}
						}
						TotalExpense = TotalExpense - expenseAmount;
						trip.setTripTotalexpense(TotalExpense);
					}
					object.put("driverBalance", Utility.round((TotalAdvance - TotalExpense), 2));
					
				}
				else {
					
					if((boolean) config.get("changedDriverBalance")) {
						driverBalance = TotalAdvance  + B_Income - B_IncomeDue - TotalExpense;
						
						object.put("driverBalance", driverBalance);
						object.put("B_Income", B_Income);
						object.put("B_IncomeDue", B_IncomeDue);
						object.put("TotalExpense", TotalExpense);
						object.put("TotalAdvance", TotalAdvance);
						object.put("E_Income", E_Income);
						
					}else {
						object.put("driverBalance", Utility.round(((Totalincome + TotalAdvance) - TotalExpense - totalDueAmount), 2));
					}
				}
				if((boolean) config.get("driverBalanceWithNarration")) {
					ValueObject	valueObject = tripSheetBL.getDriverBalanceForTrip(trip, config, incomeDto, expenseDto);
					if(valueObject != null) {
						object.put("driverBalanceKey",valueObject.get("driverBalanceKey"));
						object.put("balanceAmount",Utility.round(valueObject.getDouble("balanceAmount",0),2));
					}
				}

				
				if (trip.getTripCommentTotal() != null && trip.getTripCommentTotal() != TRIP_SHEET_DEFALAT_ZERO) {
					object.put("TripComment", tripSheetBL.TripSheetComment_Display(
							tripCommentService.list_TripSheet_ID_TO_TripSheetComment(tripsheetId, companyId)));
				}
				if((boolean)config.getOrDefault("saveDriverLedgerDetails", false) && trip.getTripStutesId() < 3) {
					DriverTripSheetBalance driverTripSheetBalance= 	driverTripSheetBalanceRepo.getDriverTripSheetBalanceByDriverId(trip.getTripFristDriverID(), companyId);
					if(driverTripSheetBalance != null ) {
						object.put("driverWalleteBalance",driverTripSheetBalance.getBalanceAmount());
					}
				}
				
				if((boolean) lorryHireConfig.getOrDefault("linkTripSheetToLorryHire", false)) {
					object.put("lorryHireList", lorryHireDetailsService.getLorryHireListByTripSheetId(tripsheetId));
					
				}
				if((boolean) config.get("changedTripBalance")) {
					object.put("tripbalance", (B_Income + E_Income) - TotalExpense);
				}
				object.put("TripSheet", trip);
			} else {
				object.put("NotFound", true);
			}
			
			object.put("DriverReminder",  driverReminderBL.getDriverReminder_Showing_Details(driverService.listLatestDriverDLReminder(trip.getTripFristDriverID(), userDetails.getCompany_id())));
			return object;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public ValueObject addTripSheetAdvanceDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		String						email						= null;
		CustomUserDetails			userDetails					= null;
		HashMap<String, Object> 	configuration				= null; 
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			email					= object.getString("email");
			
			userDetails				= new CustomUserDetails();
			userDetails.setCompany_id(companyId);
			userDetails.setId(userId);
			
			configuration	  = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG); 
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			
			if(trip != null) {
				
				object.put("TripSheet", trip);
				object.put("TripSheetAdvance",
						tripSheetService.findAllTripSheetAdvance(trip.getTripSheetID(), userDetails.getCompany_id()));
				object.put("configuration", configuration);
				
				UserProfileDto Orderingname = userProfileService.getUserDeatilsByEmail(email,companyId);
				
				object.put("paidBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
				object.put("paidById", userDetails.getId());
				object.put("place", Orderingname.getBranch_name());
				object.put("placeId", Orderingname.getBranch_id());
				
				DecimalFormat df = new DecimalFormat("##,##,##0");
				df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
				Double Total = trip.getTripTotalAdvance();
				if (trip.getTripTotalAdvance() == null) {
					Total = TRIP_SHEET_DEFALAT_AMOUNT;
				}
				object.put("advanceTotal", df.format(Total));
			}
			
			return object;
		
		} catch (Exception e) {
			throw e;
		}
	}	
	
	@Override
	public ValueObject saveTripSheetAdvanceDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		TripSheet					tripSheet					= null;
		double						tripcloseAmount				= 0.0;
		HashMap<String, Object> 	configuration				= null; 
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			tripSheet		= tripSheetService.getTripSheetDetails(tripsheetId);
			TripSheetAdvance TripAdvance = tripSheetMobileBL.saveTripAdvance(object);
			TripAdvance.setCompanyId(companyId);
			TripAdvance.setCreatedById(userId);
			
			TripSheet tsheet = new TripSheet();
			tsheet.setTripSheetID(tripsheetId);
			TripAdvance.setTripsheet(tsheet);

			tripSheetService.addTripSheetAdvance(TripAdvance);
			
			TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(tripsheetId);
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
			
			if(TripAdvance.getPaymentTypeId() != null && object.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(TripAdvance.getPaymentTypeId()) || TripAdvance.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH )){
				ValueObject bankPaymentValueObject = JsonConvertor
						.toValueObjectFormSimpleJsonString(object.getString("bankPaymentDetails"));
				bankPaymentValueObject.put("bankPaymentTypeId",TripAdvance.getPaymentTypeId());
				bankPaymentValueObject.put("userId", object.getLong("userId"));
				bankPaymentValueObject.put("companyId", object.getInt("companyId"));
				bankPaymentValueObject.put("moduleId", TripAdvance.getTripAdvanceID());
				bankPaymentValueObject.put("moduleNo", tripSheet.getTripSheetNumber());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.TRIP_ADVANCE);
				bankPaymentValueObject.put("amount", TripAdvance.getAdvanceAmount());
				//bankPaymentValueObject.put("paidDate", TripIncome.getTripsheetIncomeDate());
				if (TripAdvance.getPaymentTypeId() > 0) {
					if (PaymentTypeConstant.PAYMENT_TYPE_CASH == TripAdvance.getPaymentTypeId())
						cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
					else
						bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
					}
			}

			
			if ((boolean) configuration.getOrDefault("saveDriverLedgerDetails", false)) {
				ValueObject driveLedgerObj = new ValueObject();

				driveLedgerObj.put("companyId", companyId);
				driveLedgerObj.put("userId", userId);
				driveLedgerObj.put("driverId", tripSheet.getTripFristDriverID());
				driveLedgerObj.put("amount", TripAdvance.getAdvanceAmount());
				driveLedgerObj.put("transactionId", tripSheet.getTripSheetID());
				driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_ADVANCE);
				driveLedgerObj.put("subTransactionId", TripAdvance.getTripAdvanceID());
				driveLedgerObj.put("description", "Advance Given To Driver Rs : " + TripAdvance.getAdvanceAmount()
						+ " For TripSheet Number : " + tripSheet.getTripSheetNumber());
				if(TripAdvance.getPaidDate() != null)
				driveLedgerObj.put("txnTime",new Timestamp(TripAdvance.getPaidDate().getTime()));

				driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
			}
			if(tripSheet.getTripStutesId() ==  TripSheetStatus.TRIP_STATUS_CLOSED && (boolean)configuration.get("removeAdvanceAfterClose")) {
				if(tripSheet.getCloseTripAmount() == null) {
					tripcloseAmount 	= TripAdvance.getAdvanceAmount() ;
				}else {
					tripcloseAmount 	= tripSheet.getCloseTripAmount() + TripAdvance.getAdvanceAmount() ;
				}
				tripSheetService.updateCloseTripAmount(tripsheetId, tripcloseAmount,companyId);
			}
			tripSheetService.UPDATE_TOTALADVANCE_IN_TRIPSHEET_TRIPSHEETADVANCE_ID(tripsheetId, companyId);
			
			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
			
			Double driverBalance=null;
			if(tripSheet.getTripStutesId() ==  TripSheetStatus.TRIP_STATUS_CLOSED && (boolean)configuration.get("addInDriverbalanceAfterTripClose")) {
				driverBalance = tripSheetService.getDriverBalance(tripsheetId,companyId);
				if(driverBalance <0) {
					driverBalance = driverBalance + TripAdvance.getAdvanceAmount() ;;
				}
				if(driverBalance>0) {
					driverBalance = driverBalance + TripAdvance.getAdvanceAmount() ;
				}
				tripSheetService.updateDriverBalance(tripsheetId,driverBalance,companyId);
			}
			
			object.put("updateAdvance", true);
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}	
	
	@Override
	public ValueObject removeTripSheetAdvanceDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						tripsheetId					= 0;
		long						tripAdvanceId				= 0;
		TripSheetDto				tripSheetDto				= null;
		HashMap<String, Object> 	configuration				= null;
		double						tripcloseAmount					= 0.0;
		try {
			companyId    			= object.getInt("companyId");
			tripsheetId 			= object.getLong("tripsheetId");
			tripAdvanceId 			= object.getLong("tripAdvanceId");
			
			CustomUserDetails	userDetails	= new CustomUserDetails(companyId, null);
			
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			
			tripSheetDto	= tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
		
			TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(tripsheetId);
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
			
			TripSheetAdvance  tripSheetAdvance = tripSheetAdvanceRepository.getTripSheetAdvance(tripAdvanceId, companyId);
			if(tripSheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && !(boolean)configuration.get("deleteTripSheetAfterClose") && (boolean)configuration.get("removeAdvanceAfterClose")){
				
				if(tripSheetDto.getCloseTripAmount() == null) {
					tripcloseAmount 	= tripSheetAdvance.getAdvanceAmount() ;
				}else {
					tripcloseAmount 	= tripSheetDto.getCloseTripAmount() - tripSheetAdvance.getAdvanceAmount() ;
				}
				tripSheetService.updateCloseTripAmount(tripsheetId, tripcloseAmount,companyId);
				
			}else if(tripSheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && !(boolean)configuration.get("deleteTripSheetAfterClose")) {
				object.put("closed", true);
				return object;
			}else if(tripSheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
				object.put("accountClosed", true);
				return object;
			}
			
				tripSheetService.deleteTripSheetAdvance(tripAdvanceId, companyId);
				tripSheetService.UPDATE_TOTALADVANCE_IN_TRIPSHEET_TRIPSHEETADVANCE_ID(tripsheetId, companyId);
				
				if(tripSheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && (boolean)configuration.get("addInDriverbalanceAfterTripClose"))	{
					Double driverBalance = tripSheetDto.getDriverBalance();
						if(driverBalance < 0) {
						   driverBalance = driverBalance -  tripSheetAdvance.getAdvanceAmount();
						}
						if(driverBalance >0) {
							driverBalance = driverBalance -  tripSheetAdvance.getAdvanceAmount(); 
						}
						tripSheetService.updateDriverBalance(tripSheetDto.getTripSheetID(),driverBalance,companyId);
				}
				
				if ((boolean) configuration.getOrDefault("saveDriverLedgerDetails", false) && (tripSheetAdvance.getFromBalance()== null || !tripSheetAdvance.getFromBalance())) {
					ValueObject driveLedgerObj = new ValueObject();
					driveLedgerObj.put("companyId", companyId);
					driveLedgerObj.put("userId", object.getLong("userId"));
					driveLedgerObj.put("driverId", tripSheetDto.getTripFristDriverID());
					driveLedgerObj.put("amount", tripSheetAdvance.getAdvanceAmount());
					driveLedgerObj.put("transactionId", tripSheetDto.getTripSheetID());
					driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_ADVANCE_REMOVED);
					driveLedgerObj.put("subTransactionId", tripSheetAdvance.getTripAdvanceID());
					driveLedgerObj.put("description", "Advance Removed of : " + tripSheetAdvance.getAdvanceAmount()
							+ " For TripSheet Number : " + tripSheetDto.getTripSheetNumber());
					if(tripSheetAdvance.getPaidDate() != null)
					driveLedgerObj.put("txnTime",new Timestamp(tripSheetAdvance.getPaidDate().getTime()));
					driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
				}			

				bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(tripAdvanceId,ModuleConstant.TRIP_ADVANCE, companyId, object.getLong("userId",0),false);
				
				tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
				
			return object;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject addTripSheetExpenseDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		CustomUserDetails			userDetails					= null;
		HashMap<String, Object>    	config						= null;
		boolean						allowShortCutInTripSheet	= false;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails();
			userDetails.setCompany_id(companyId);
			userDetails.setId(userId);
			
			config	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			object.put("configuration", config);
			
			allowShortCutInTripSheet = (boolean) config.getOrDefault(TripSheetConfigurationConstant.ALLOW_SHORTCUT_IN_TRIPSHEET, false);
			object.put("AllowShortCutInTripSheet", allowShortCutInTripSheet);

			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			object.put("TripSheet", trip);
			
			object.put("TripSheetExpense",tripSheetService.findAllTripSheetExpense(tripsheetId, companyId));
			
			/*DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			object.put("expenseTotal", df.format(TotalExpense));*/
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveTripSheetExpenseDetails(ValueObject object, MultipartFile[] file) throws Exception {
		int													companyId						= 0;
		long												tripsheetId						= 0;
		long												userId							= 0;
		CustomUserDetails									userDetails						= null;
		HashMap<String, Object>    							config							= null;
		boolean												allowMultipleExpensesOfSameType	= false;
		HashMap<Long, VehicleProfitAndLossTxnChecker>		expenseTxnCheckerHashMap		= null;
		HashMap<Long, TripSheetExpense> 					tripSheetExpenseHM				= null;
		ArrayList<ValueObject> 								dataArrayObjColl 				= null;
		TripSheetExpense 									TripExpense  					=  null;
		HashMap<Integer, Double> 							duplicateExpense				= null;
		HashMap<String, Object>    							tripRouteConfig					= null;
		boolean												expenseOutOfRange				= false;
		List<TripSheetExpense> 								validate2						= null;
		double 												maxAmt 							= 0;
		int 												i 								= 0;
		MultipartFile										blankFile						= null;
		double												tripcloseAmount					= 0.0;
		StringBuffer										alreadyExpense 					= new StringBuffer();
		StringBuffer										createdExpense 					= new StringBuffer();
		
		try {
			companyId    			= object.getInt("companyId");
			tripsheetId 			= object.getLong("tripsheetId");
			userId					= object.getLong("userId");
			
			userDetails					= new CustomUserDetails(companyId, userId);
			expenseTxnCheckerHashMap	= new HashMap<>();
			tripSheetExpenseHM			= new HashMap<>();
			duplicateExpense			= new HashMap<>();
			
			CustomUserDetails userDetails2 = new CustomUserDetails(companyId, userId);
			config	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			tripRouteConfig	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIP_ROUTE_CONFIGURATION_CONFIG);
			allowMultipleExpensesOfSameType	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.ALLOW_MULTIPLE_EXPENSES_OF_SAME_TYPE, false);
			
			Collection<GrantedAuthority>	permission	= userDetails2.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("ADD_TRIP_ROUTE_EXPENSE_OUT_OF_RANGE"))) {
				expenseOutOfRange = true;
			}
			object.put("tripRouteExpenseMaxLimitConfig", (boolean) tripRouteConfig.get("tripRouteExpenseMaxLimit"));
			object.put("expenseOutOfRange", expenseOutOfRange);
			
			TripSheet trip = tripSheetService.getTripSheetData(tripsheetId, companyId);
			object.put("TripSheet", trip);
			object.put("config", config);
			object.put("allowMultipleExpensesOfSameType", allowMultipleExpensesOfSameType);
			
			TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(tripsheetId);
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
			
			UserProfileDto Orderingname   = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			Integer 	   expensePlaceId = Orderingname.getBranch_id();
			object.put("expensePlaceId", expensePlaceId);
			double tempAmount = 0;
			
			HashMap<Integer, Double>  expenseLimitHM = tripRouteExpenseRangeService.getExpenseWiseMaxLimit(trip.getRouteID());
			if(object.getBoolean("isMultiple",false)) {
				dataArrayObjColl	= (ArrayList<ValueObject>) object.get("expenseDetails");
				if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					for (ValueObject valueObject : dataArrayObjColl) {
						
						if(valueObject.getString("tripSheetExpenseDocId") == "") {
							TripExpense  = tripSheetMobileBL.saveTripExpense(valueObject, object,blankFile);
						}else {
							TripExpense  = tripSheetMobileBL.saveTripExpense(valueObject, object,file[i]);
						}
						if(expenseLimitHM != null) {
							if(expenseLimitHM.get(TripExpense.getExpenseId()) != null) {
							 maxAmt =	expenseLimitHM.get(TripExpense.getExpenseId());
							}else {
								maxAmt = 0;
							}
						}
						double 	expenseAmount 	= 0;

						if((boolean) tripRouteConfig.get("tripRouteExpenseMaxLimit")) {
							
							validate2 = tripSheetService.ValidateAllTripSheetExpense(TripExpense.getExpenseId(), tripsheetId,TripExpense.getCompanyId());
							
							if(validate2 != null) {
								for(TripSheetExpense dto : validate2) {
									expenseAmount += dto.getExpenseAmount();
								}
							}
							 if( maxAmt > 0 && expenseOutOfRange == false && expenseAmount+TripExpense.getExpenseAmount() > maxAmt) {
								 continue;
							}
	
							if(duplicateExpense.containsKey(TripExpense.getExpenseId())){
								tempAmount += TripExpense.getExpenseAmount();
								 
								if(maxAmt > 0 && expenseOutOfRange == false && tempAmount > maxAmt) {
									TripExpense.setExpenseAmount(0.0);
									continue;
								}
							}else {
								tempAmount = TripExpense.getExpenseAmount();
								duplicateExpense.put(TripExpense.getExpenseId(), tempAmount);
							}
							
						}	
						object.put("TripExpense", TripExpense);
						if(valueObject.getString("tripSheetExpenseDocId") == "") {
							object	= saveTripSheetExpenses(object,blankFile);
						}else {
							object	= saveTripSheetExpenses(object,file[i]);
						}
						if(object.getBoolean("save")) {
							object.put("ExpenseSave", true);
							createdExpense.append(", "+ TripExpense.getExpenseAmount());
						}
						if(object.getBoolean("alreadyExpense")) {
							object.put("already", true);
							alreadyExpense.append(", " +TripExpense.getExpenseAmount());
						}
						object.remove("save");
						object.remove("alreadyExpense");
						
						if((boolean) config.getOrDefault("saveDriverLedgerDetails", true)) {
							
						if(TripExpense.getPaymentTypeId() != null && TripExpense.getPaymentTypeId() > 0 && !PaymentTypeConstant.getPaymentTypeIdList().contains(TripExpense.getPaymentTypeId())){
						ValueObject	driveLedgerObj	= new ValueObject();
						driveLedgerObj.put("companyId", companyId);
						driveLedgerObj.put("userId", userId);
						driveLedgerObj.put("driverId", trip.getTripFristDriverID());
						driveLedgerObj.put("amount", TripExpense.getExpenseAmount());
						driveLedgerObj.put("transactionId", trip.getTripSheetID());
						driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_EXPENSE);
						driveLedgerObj.put("subTransactionId", TripExpense.getTripExpenseID());
						driveLedgerObj.put("description", "Expense added of Rs : "+ TripExpense.getExpenseAmount()+"For TripSheet Number : "+trip.getTripSheetNumber());
						if(TripExpense.getPaidDate() !=null)
						driveLedgerObj.put("txnTime",new Timestamp(TripExpense.getPaidDate().getTime()));
						driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
						}
						
						}
						
						Double driverBalance = null;
						if((trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) && TripExpense.getPaymentTypeId()==PaymentTypeConstant.PAYMENT_TYPE_CASH && (boolean)config.get("addInDriverbalanceAfterTripClose")) {
							driverBalance = tripSheetService.getDriverBalance(tripsheetId,companyId);
							
							if(driverBalance <0) {
								driverBalance = driverBalance - TripExpense.getExpenseAmount();
							}
							if(driverBalance>0) {
								driverBalance = driverBalance - TripExpense.getExpenseAmount();
							}
							
							tripSheetService.updateDriverBalance(tripsheetId,driverBalance,companyId);
						}
						
						if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
							HashMap<Long, VehicleProfitAndLossTxnChecker>		tempTxn		= (HashMap<Long, VehicleProfitAndLossTxnChecker>) object.get("expenseTxnCheckerHashMap");
							HashMap<Long, TripSheetExpense> 					tempExpense	= (HashMap<Long, TripSheetExpense>) object.get("tripSheetExpenseHM");
							if(tempExpense != null && !tempExpense.isEmpty()) {
								tripSheetExpenseHM.putAll(tempExpense);
							}
							if(tempTxn != null && !tempTxn.isEmpty()) {
								expenseTxnCheckerHashMap.putAll(tempTxn);
							}
						}
						i++;
					
					}
					
					
				}

				object.put("createdExpense", createdExpense);
				object.put("alreadyExpense", alreadyExpense);
				
			 }else {
				if(object.getString("tripExpenseDocument","").equals("")) {
					TripExpense  = tripSheetMobileBL.saveTripExpense(object,blankFile);
				}else {
					if (object.getString("base64String",null)  != null) {
						TripExpense  = tripSheetMobileBL.saveTripExpense(object,blankFile);
					} else {
						TripExpense  = tripSheetMobileBL.saveTripExpense(object,file[0]);
					}
				}
				validate2 = tripSheetService.ValidateAllTripSheetExpense(TripExpense.getExpenseId(), tripsheetId,TripExpense.getCompanyId());
				double 	 expenseAmount = 0;
				if(expenseLimitHM != null) {
					if(expenseLimitHM.get(TripExpense.getExpenseId()) != null) {
					 maxAmt = expenseLimitHM.get(TripExpense.getExpenseId());
					}else {
						maxAmt = 0;
					}
				}
				if(validate2 != null) {
					for(TripSheetExpense dto : validate2) {
						expenseAmount += dto.getExpenseAmount();
					}
				}
				
				 if( maxAmt > 0 && !expenseOutOfRange && expenseAmount+TripExpense.getExpenseAmount() > maxAmt) {
					 object.put("outOfRange", true);
					 return object;
				}
				
				object.put("TripExpense", TripExpense);
				if(object.getString("tripExpenseDocument","").equals("")) {
					object	= saveTripSheetExpenses(object,blankFile); 
				}else {
					if (object.getString("base64String",null)  != null) {
						object	= saveTripSheetExpenses(object,blankFile);
					} else {
						object	= saveTripSheetExpenses(object,file[0]);
					}
				}
				if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
					HashMap<Long, VehicleProfitAndLossTxnChecker>		tempTxn		= (HashMap<Long, VehicleProfitAndLossTxnChecker>) object.get("expenseTxnCheckerHashMap");
					HashMap<Long, TripSheetExpense> 					tempExpense	= (HashMap<Long, TripSheetExpense>) object.get("tripSheetExpenseHM");
					
					if(tempExpense != null && !tempExpense.isEmpty()) {
						tripSheetExpenseHM.putAll(tempExpense);
					}
					
					if(tempTxn != null && !tempTxn.isEmpty()) {
						expenseTxnCheckerHashMap.putAll(tempTxn);
					}
				}
				if((boolean) config.getOrDefault("saveDriverLedgerDetails", true)) {
					ValueObject driveLedgerObj = new ValueObject();
					driveLedgerObj.put("companyId", companyId);
					driveLedgerObj.put("userId", userId);
					driveLedgerObj.put("driverId", trip.getTripFristDriverID());
					driveLedgerObj.put("amount", TripExpense.getExpenseAmount());
					driveLedgerObj.put("transactionId", trip.getTripSheetID());
					driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_EXPENSE);
					driveLedgerObj.put("subTransactionId", TripExpense.getTripExpenseID());
					driveLedgerObj.put("description", "Expense Added Of Rs : " + TripExpense.getExpenseAmount()
							+ "For TripSheet Number : " + trip.getTripSheetNumber());
					if(TripExpense.getPaidDate() != null)
					driveLedgerObj.put("txnTime",new Timestamp(TripExpense.getPaidDate().getTime()));
					driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
				}
			}
			
			if(trip.getCloseTripAmount() == null) {
				trip.setCloseTripAmount(0.0);
			}
			if(trip.getTripTotalAdvance() == null) {
				trip.setTripTotalAdvance(0.0);
			}
			
			if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && (boolean)config.get("removeAdvanceAfterClose")) {
				if(trip.getCloseTripAmount() == null ) {
					tripcloseAmount 	= object.getDouble("expenseAmount",0) ;
				}else {
					tripcloseAmount 	= trip.getCloseTripAmount() - object.getDouble("expenseAmount",0) ;
				}
				tripSheetService.updateCloseTripAmount(tripsheetId, tripcloseAmount,companyId);
			
			}
			tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripsheetId, companyId);
			
			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);

			if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && expenseTxnCheckerHashMap != null && !expenseTxnCheckerHashMap.isEmpty()) {
					ValueObject	valueObject	= new ValueObject();
					valueObject.put("tripSheet", trip);
					valueObject.put("userDetails", userDetails);
					valueObject.put("tripSheetExpenseHM", tripSheetExpenseHM);
					valueObject.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);
					valueObject.put("isTripSheetClosed", true);
					
					vehicleProfitAndLossService.runThreadForTripSheetExpenses(valueObject);
			}
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	private ValueObject saveTripSheetExpenses(ValueObject object,MultipartFile file)throws Exception{
		List<TripSheetExpense> 			validate		= null;
		TripSheetExpense 				TripExpense  	= null;
		TripSheet					 	trip 			= null;
		HashMap<Long, VehicleProfitAndLossTxnChecker>		expenseTxnCheckerHashMap		= null;
		HashMap<Long, TripSheetExpense> 					tripSheetExpenseHM				= null;
		object.put("TripSheet", trip);
		TripSheetExpense 				savedTripExpense  	= null;
		StringBuffer										createdExpense 					= new StringBuffer();
		StringBuffer										alreadyExpense 					= new StringBuffer();
		
		try {
			CustomUserDetails	userDetails	= new CustomUserDetails(object.getInt("companyId"), object.getLong("userId"));
			TripExpense = (TripSheetExpense)object.get("TripExpense");
			
			if(!object.getBoolean("allowMultipleExpensesOfSameType",false) ) {

				validate = tripSheetService.ValidateAllTripSheetExpense(object.getInt("expenseNameId"), object.getLong("tripsheetId"), object.getInt("companyId"));

				alreadyExpense.append("," +TripExpense.getExpenseAmount());
			}
			
				if (validate != null && !validate.isEmpty()) {
					object.put("alreadyExpense", true);
				} else {
					trip		= (TripSheet)object.get("TripSheet");
					TripSheetDto	sheetDto	= tripSheetService.getTripSheetDetails(trip.getTripSheetID(), userDetails);
					
					expenseTxnCheckerHashMap	= new HashMap<Long, VehicleProfitAndLossTxnChecker>();
					tripSheetExpenseHM			= new HashMap<Long, TripSheetExpense>();
					savedTripExpense = tripSheetService.addTripSheetExpense(TripExpense);
					object.put("save", true);
					
					object.put("updateExpense", true);

					Object[] expenseIds = tripSheetService.getDriverAdvanceAndBataExpenseId(object.getInt("companyId"));

					// Note : This Update Bata Details in Trip Sheet
					if (expenseIds != null) {
						if (expenseIds[0] != null && (Integer) expenseIds[0] == object.getInt("expenseNameId")) {
						}
						
						if (expenseIds[1] != null && (Integer) expenseIds[1] == object.getInt("expenseNameId")) {
							tripSheetService.Update_TripSheet_FIRST_Driver_BATA(object.getDouble("expenseAmount"), object.getLong("tripsheetId"), object.getInt("companyId"));
						}
						
						if (expenseIds[2] != null && (Integer) expenseIds[2] == object.getInt("expenseNameId")) {
						}
						
						if (expenseIds[3] != null && (Integer) expenseIds[3] == object.getInt("expenseNameId")) {
							tripSheetService.Update_TripSheet_SECOND_Driver_BATA(object.getDouble("expenseAmount"), object.getLong("tripsheetId"), object.getInt("companyId"));
						}
						
						if (expenseIds[4] != null && (Integer) expenseIds[4] == object.getInt("expenseNameId")) {
						}
						
						if (expenseIds[5] != null && (Integer) expenseIds[5] == object.getInt("expenseNameId")) {
							tripSheetService.Update_TripSheet_CLEANER_BATA(object.getDouble("expenseAmount"), object.getLong("tripsheetId"), object.getInt("companyId"));
						}
					}
					
					if(file != null && !file.isEmpty()|| object.getString("base64String",null) != null) {
						saveTripExpenseDocument(savedTripExpense, file,object);
					}
					
					if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
						ValueObject		expenseObject	= new ValueObject();

						expenseObject.put(VehicleProfitAndLossDto.COMPANY_ID, object.getInt("companyId"));
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, object.getLong("tripsheetId"));
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						expenseObject.put(VehicleProfitAndLossDto.COMPANY_ID, object.getInt("companyId"));
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, trip.getVid());
						expenseObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, TripExpense.getExpenseId());
						expenseObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, TripExpense.getTripExpenseID());

						VehicleProfitAndLossTxnChecker profitAndLossExpenseTxnChecker = txnCheckerBL.getVehicleProfitAndLossTxnChecker(expenseObject);
						vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);

						expenseTxnCheckerHashMap.put(profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossExpenseTxnChecker);
						tripSheetExpenseHM.put(TripExpense.getTripExpenseID(), TripExpense);
						
						object.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);
						object.put("tripSheetExpenseHM", tripSheetExpenseHM);
					}
					
					if(savedTripExpense.getPaymentTypeId() != null&& object.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(savedTripExpense.getPaymentTypeId()))){
						ValueObject bankPaymentValueObject = JsonConvertor
								.toValueObjectFormSimpleJsonString(object.getString("bankPaymentDetails"));
						bankPaymentValueObject.put("bankPaymentTypeId",savedTripExpense.getPaymentTypeId());
						bankPaymentValueObject.put("userId", object.getLong("userId"));
						bankPaymentValueObject.put("companyId", object.getInt("companyId"));
						bankPaymentValueObject.put("moduleId", savedTripExpense.getTripExpenseID());
						bankPaymentValueObject.put("moduleNo", trip.getTripSheetNumber());
						bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.TRIP_EXPENSE);
						bankPaymentValueObject.put("amount", savedTripExpense.getExpenseAmount());
						//bankPaymentValueObject.put("paidDate", TripIncome.getTripsheetIncomeDate());
						bankPaymentValueObject.put("remark", "Driver Name "+sheetDto.getTripFristDriverName()+", Vehicle Number : "+sheetDto.getVehicle_registration()+" ");
						if (savedTripExpense.getPaymentTypeId() > 0) {
//							if (PaymentTypeConstant.PAYMENT_TYPE_CASH == savedTripExpense.getPaymentTypeId())
//								cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
//							else
								bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
							}
					}
				  }


			return object;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject removeTripSheetExpenseDetails(ValueObject object) throws Exception{
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		long						tripExpenseId				= 0;
		CustomUserDetails			userDetails					= null;
		HashMap<String, Object>    	config						= null;
		double						tripcloseAmount				= 0.0;
		double						tripExpenseAmount			= 0.0;
		HashMap<String, Object> 	companyConfiguration		= null;				
		try {
			companyId    			= object.getInt("companyId");
			tripsheetId 			= object.getLong("tripsheetId");
			tripExpenseId 			= object.getLong("tripExpenseID");
			userId					= object.getLong("userId");
			userDetails				= new CustomUserDetails(companyId, userId);
			config	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			companyConfiguration 	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			TripSheet trip = tripSheetService.getTripSheetData(tripsheetId, companyId);
			object.put("TripSheet", trip);
			
			TripSheetExpense TripExpense = tripSheetService.getTripSheetExpense(tripExpenseId, companyId);
			
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(trip);
			
			

			// Remove the Trip Expense value details to Bata value to zero
			Object[] expenseIds = tripSheetService.getDriverAdvanceAndBataExpenseId(companyId);

			if (expenseIds != null) {
				if (expenseIds[0] != null && expenseIds[0] == TripExpense.getExpenseId()) {
				}
				
				if (expenseIds[1] != null && expenseIds[1] == TripExpense.getExpenseId()) {
					tripSheetService.Update_TripSheet_FIRST_Driver_BATA(TRIP_SHEET_DEFALAT_AMOUNT, tripsheetId, companyId);
				}
				
				if (expenseIds[2] != null && expenseIds[2] == TripExpense.getExpenseId()) {
				}
				
				if (expenseIds[3] != null && expenseIds[3] == TripExpense.getExpenseId()) {
					tripSheetService.Update_TripSheet_SECOND_Driver_BATA(TRIP_SHEET_DEFALAT_AMOUNT, tripsheetId, companyId);
				}
				
				if (expenseIds[4] != null && expenseIds[4] == TripExpense.getExpenseId()) {
				}
				
				if (expenseIds[5] != null && expenseIds[5] == TripExpense.getExpenseId()) {
					tripSheetService.Update_TripSheet_CLEANER_BATA(TRIP_SHEET_DEFALAT_AMOUNT, tripsheetId, companyId);
				}
			}
			
			if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && (boolean)config.get("removeAdvanceAfterClose") ) {
				if(trip.getCloseTripAmount() == null) {
					tripcloseAmount = 0;
				}else {
					tripcloseAmount = trip.getCloseTripAmount();
				}
				if(TripExpense.getExpenseAmount() == null) {
					tripExpenseAmount = 0;
				}else {
					tripExpenseAmount = TripExpense.getExpenseAmount();
				}
				
				tripcloseAmount 	= tripcloseAmount + tripExpenseAmount ;
				tripSheetService.updateCloseTripAmount(tripsheetId, tripcloseAmount,companyId);
			}
			
			tripSheetService.deleteTripSheetExpense(tripExpenseId, companyId);
			
			if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && (boolean)config.get("addInDriverbalanceAfterTripClose"))
			{
				Double driverBalance = trip.getDriverBalance();
				
				if(TripExpense.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
					
					if(driverBalance < 0) {
					   driverBalance = driverBalance + TripExpense.getExpenseAmount();
					}
					if(driverBalance >0) {
						driverBalance = driverBalance + TripExpense.getExpenseAmount(); 
					}
					tripSheetService.updateDriverBalance(trip.getTripSheetID(),driverBalance,companyId);
				}
			}
			
			tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripsheetId, companyId);
			bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(tripExpenseId, ModuleConstant.TRIP_EXPENSE, companyId, userId, false);
			
			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
			
			if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
				
				ValueObject		valueObject	= new ValueObject();
				valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
				valueObject.put("txnAmount", TripExpense.getExpenseAmount());
				valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(trip.getClosetripDate()));
				valueObject.put("txnTypeId", tripsheetId);
				valueObject.put("expenseId", TripExpense.getExpenseId());
				valueObject.put("vid", trip.getVid());
				valueObject.put("companyId", userDetails.getCompany_id());
				
				new Thread() {
					public void run() {
						try {
							vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(valueObject);
							vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(valueObject);
							vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(valueObject);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}		
				}.start();
				
			}
			if ((boolean) config.getOrDefault("saveDriverLedgerDetails", false)) {
			ValueObject	driveLedgerObj	= new ValueObject();
			driveLedgerObj.put("companyId", companyId);
			driveLedgerObj.put("userId", userId);
			driveLedgerObj.put("driverId", trip.getTripFristDriverID());
			driveLedgerObj.put("amount", TripExpense.getExpenseAmount());
			driveLedgerObj.put("transactionId", trip.getTripSheetID());
			driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_EXPENSE_REMOVED);
			driveLedgerObj.put("subTransactionId", TripExpense.getTripExpenseID());
			driveLedgerObj.put("description", "Expense Removed of : "+ TripExpense.getExpenseAmount()+" For TripSheet Number : "+trip.getTripSheetNumber());
			if(TripExpense.getPaidDate() != null)
			driveLedgerObj.put("txnTime",TripExpense.getPaidDate());
			driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
			}
			// remove expense from cargo
			if((boolean) companyConfiguration.get("allowBankPaymentDetails") && (boolean) companyConfiguration.get("sendExpenseToIvCargo") ) {
					removeExpenseDataToCargo(object);
		}			
			return object;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject addTripSheetIncomeDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		CustomUserDetails			userDetails					= null;
		HashMap<String, Object>    	config						= null;
		boolean						allowShortCutInTripSheet	= false;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails(companyId, userId);
			
			config	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			allowShortCutInTripSheet = (boolean) config.getOrDefault(TripSheetConfigurationConstant.ALLOW_SHORTCUT_IN_TRIPSHEET, false);
			object.put("AllowShortCutInTripSheet", allowShortCutInTripSheet);

			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			object.put("TripSheet", trip);

			object.put("TripSheetIncome", tripSheetService.findAllTripSheetIncome(tripsheetId, companyId));

			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject saveTripSheetIncomeDetails(ValueObject object) throws Exception {
		int														companyId						= 0;
		long													tripsheetId						= 0;
		long													userId							= 0;
		CustomUserDetails										userDetails						= null;
		HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>		incomeTxnCheckerHashMap			= null;
		HashMap<Long, TripSheetIncome> 							tripSheetIncomeDtoHM			= null;
		HashMap<String, Object> 								configuration					= null;
		boolean													allowMultipleIncomeOfSameType	= false;
		TripSheetIncome 										TripIncome 						= null;
		ArrayList<ValueObject> 									dataArrayObjColl 				= null;
		try {
			companyId    			= object.getInt("companyId");
			tripsheetId 			= object.getLong("tripsheetId");
			userId					= object.getLong("userId");
			userDetails				= new CustomUserDetails(companyId, userId);
			tripSheetIncomeDtoHM	= new HashMap<>();
			incomeTxnCheckerHashMap	= new HashMap<>();
			
			configuration					= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			allowMultipleIncomeOfSameType	= (boolean) configuration.getOrDefault("allowMultipleIncomeOfSameType", false);
			
			
			UserProfileDto Orderingname   = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			Integer 	   incomePlaceId  = Orderingname.getBranch_id();
			object.put("incomePlaceId", incomePlaceId);
			
			TripSheet trip = tripSheetService.getTripSheetData(tripsheetId, companyId);
			object.put("TripSheet", trip);
			object.put("allowMultipleIncomeOfSameType", allowMultipleIncomeOfSameType);
			
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(trip);
			
			if(object.getBoolean("isMultiple", false)) {
				dataArrayObjColl	= (ArrayList<ValueObject>) object.get("incomeDetails");
				
				if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					for (ValueObject valueObject : dataArrayObjColl) {
						TripIncome  = tripSheetMobileBL.saveTripIncome(object, valueObject);
						if((boolean) configuration.get("addtripIncomeAsBIncome")) {
							TripIncome.setBillSelectionId(WayBillTypeConstant.WITH_BILL);
						}
						object.put("TripIncome", TripIncome);
						object	=	saveTripIncome(object);
						if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
							HashMap<Long, TripSheetIncome>	tempIncomeDtoHM 		= (HashMap<Long, TripSheetIncome>) object.get("tripSheetIncomeDtoHM");
							HashMap<Long, VehicleProfitAndLossIncomeTxnChecker> tempIncomeTxnChecker 	= (HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>) object.get("incomeTxnCheckerHashMap");
							if(tempIncomeDtoHM != null && !tempIncomeDtoHM.isEmpty()) {
								tripSheetIncomeDtoHM.putAll(tempIncomeDtoHM);
								incomeTxnCheckerHashMap.putAll(tempIncomeTxnChecker);
							}
						}
					}
				}
			
				}else {
					TripIncome  = tripSheetMobileBL.saveTripIncome(object);
					object.put("TripIncome", TripIncome);
					object	=	saveTripIncome(object);
					if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
						HashMap<Long, TripSheetIncome>	tempIncomeDtoHM 		= (HashMap<Long, TripSheetIncome>) object.get("tripSheetIncomeDtoHM");
						HashMap<Long, VehicleProfitAndLossIncomeTxnChecker> tempIncomeTxnChecker 	= (HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>) object.get("incomeTxnCheckerHashMap");
						if(tempIncomeDtoHM != null && !tempIncomeDtoHM.isEmpty()) {
							tripSheetIncomeDtoHM.putAll(tempIncomeDtoHM);
							incomeTxnCheckerHashMap.putAll(tempIncomeTxnChecker);
						}
					}
				}
			
			tripSheetService.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(tripsheetId, companyId);
			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
			
			
			if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
				if(tripSheetIncomeDtoHM != null && tripSheetIncomeDtoHM.size() > 0) {
					ValueObject	valueObject	= new ValueObject();
					valueObject.put("tripSheet", trip);
					valueObject.put("userDetails", userDetails);
					valueObject.put("tripSheetIncomeHM", tripSheetIncomeDtoHM);
					valueObject.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap);
					valueObject.put("isTripSheetClosed", true);

					vehicleProfitAndLossService.runThreadForTripSheetIncome(valueObject);
				}
			}
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	private ValueObject saveTripIncome(ValueObject object) throws Exception{
		List<TripSheetIncome> 		validate		= null;
		TripSheetIncome				TripIncome		= null;
		TripSheet					trip			= null;
		HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>		incomeTxnCheckerHashMap			= null;
		HashMap<Long, TripSheetIncome> 							tripSheetIncomeDtoHM			= null;
		try {
			validate = tripSheetService.ValidateAllTripSheetIncome(object.getInt("incomeNameId"), object.getLong("tripsheetId"), object.getInt("companyId"));
			
			if (validate != null && !validate.isEmpty()) {
				object.put("alreadyIncome", true);
			} else {
				
				trip		= (TripSheet) object.get("TripSheet");
				TripIncome	= (TripSheetIncome) object.get("TripIncome");
				incomeTxnCheckerHashMap	= new HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>();
				tripSheetIncomeDtoHM	= new HashMap<Long, TripSheetIncome>();
				
				tripSheetService.addTripSheetIncome(TripIncome);
				object.put("updateIncome", true);

				if(TripIncome.getPaymentTypeId() != null && object.getBoolean("allowBankPaymentDetails",false)&& TripIncome.getPaymentTypeId() != null && (PaymentTypeConstant.getPaymentTypeIdList().contains(TripIncome.getPaymentTypeId()) || TripIncome.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH )){
					ValueObject bankPaymentValueObject = JsonConvertor
							.toValueObjectFormSimpleJsonString(object.getString("bankPaymentDetails"));
					bankPaymentValueObject.put("bankPaymentTypeId",TripIncome.getPaymentTypeId());
					bankPaymentValueObject.put("userId", object.getLong("userId"));
					bankPaymentValueObject.put("companyId", object.getInt("companyId"));
					bankPaymentValueObject.put("moduleId", TripIncome.getTripincomeID());
					bankPaymentValueObject.put("moduleNo", trip.getTripSheetNumber());
					bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.TRIP_INCOME);
					bankPaymentValueObject.put("amount", TripIncome.getIncomeAmount());
					//bankPaymentValueObject.put("paidDate", TripIncome.getTripsheetIncomeDate());
					bankPaymentValueObject.put("fromIncome", true);
					if (TripIncome.getPaymentTypeId() > 0) {
						if (PaymentTypeConstant.PAYMENT_TYPE_CASH == TripIncome.getPaymentTypeId())
							cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
						else
							bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
						}
				}				
				if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
					ValueObject		incomeObject	= new ValueObject();

					incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, object.getInt("companyId"));
					incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, object.getLong("tripsheetId"));
					incomeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
					incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
					incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, object.getInt("companyId"));
					incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, trip.getVid());
					incomeObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, TripIncome.getIncomeId());
					incomeObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, TripIncome.getTripincomeID());

					VehicleProfitAndLossIncomeTxnChecker profitAndLossIncomeTxnChecker	= txnCheckerBL.getVehicleProfitAndLossIncomeTxnChecker(incomeObject);
					vehicleProfitAndLossIncomeTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);

					incomeTxnCheckerHashMap.put(profitAndLossIncomeTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossIncomeTxnChecker);
					tripSheetIncomeDtoHM.put(TripIncome.getTripincomeID(), TripIncome);
					
					object.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap);
					object.put("tripSheetIncomeDtoHM", tripSheetIncomeDtoHM);
				}	
				
			}
			
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			validate		= null;
			TripIncome		= null;
			trip			= null;
			incomeTxnCheckerHashMap			= null;
			tripSheetIncomeDtoHM			= null;
		}
	}
	
	@Override
	public ValueObject removeTripSheetIncomeDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		long						tripIncomeId				= 0;
		CustomUserDetails			userDetails					= null;
		try {
			companyId    			= object.getInt("companyId");
			tripsheetId 			= object.getLong("tripsheetId");
			tripIncomeId 			= object.getLong("tripIncomeId");
			userId					= object.getLong("userId");
			userDetails				= new CustomUserDetails(companyId, userId);
			
			TripSheet trip = tripSheetService.getTripSheetData(tripsheetId, companyId);
			object.put("TripSheet", trip);
			

			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(trip);
			
			if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
				
				TripSheetIncome tripIncome = tripSheetService.getTripSheetIncome(tripIncomeId, companyId);
				if(tripIncome != null) {
					if(tripIncome.getIncomeAmount() != null && tripIncome.getIncomeAmount() > 0) {
					
						ValueObject		valueObject	= new ValueObject();
						valueObject.put("tripSheet", trip);
						valueObject.put("userDetails", userDetails);
						valueObject.put("txnTypeId", trip.getTripSheetID());
						valueObject.put("tripIncome", tripIncome);
						valueObject.put("vid", trip.getVid());
						valueObject.put("incomeId", tripIncome.getIncomeId());
						valueObject.put("incomeAmount", tripIncome.getIncomeAmount());
						valueObject.put("transactionDate", DateTimeUtility.geTimeStampFromDate(trip.getClosetripDate()));
						
						vehicleProfitAndLossService.runThreadForDeleteIncome(valueObject);
					}
				}
			}
			tripSheetService.deleteTripSheetIncome(tripIncomeId, companyId);
			tripSheetService.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(tripsheetId, companyId);
			bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(tripIncomeId,ModuleConstant.TRIP_INCOME, companyId, userId,true);
			
			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
			
			return object;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject addTripSheetDriverHaltDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		String						email						= null;
		CustomUserDetails			userDetails					= null;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			email					= object.getString("email");
			
			userDetails				= new CustomUserDetails();
			userDetails.setCompany_id(companyId);
			userDetails.setId(userId);
			
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			object.put("TripSheet", trip);

			object.put("TripSheetHalt", driverHaltService.Find_list_TripSheet_DriverHalt(tripsheetId, companyId));

			UserProfileDto Orderingname   = userProfileService.getUserDeatilsByEmail(email,companyId);
			object.put("haltBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
			object.put("haltPaidById", Orderingname.getUserprofile_id());
			object.put("place", Orderingname.getBranch_name());
			object.put("placeId", Orderingname.getBranch_id());
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject saveTripSheetDriverHaltDetails(ValueObject object) throws Exception {
		int							companyId						= 0;
		long						tripsheetId						= 0;
		long						userId							= 0;
		DriverDto 					driverDto						= null;
		String 						expenseRefence 					= "";
		Double 						ExpenseTotalPoint 				= TRIP_SHEET_DEFALAT_AMOUNT;
		HashMap<String, Object> 		configuration				= null;
		boolean							driverBataFinalAmount		= false;
		TripSheet					tripSheet						= null;
		List<DriverHalt> 			validateHalt					= null;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			java.util.Date date		= dateFormat.parse(object.getString("fromDate"));
			java.sql.Date fromDate 	= new Date(date.getTime());

			java.util.Date todate = dateFormat.parse(object.getString("toDate"));
			java.sql.Date toDate = new Date(todate.getTime());
			configuration			= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			driverBataFinalAmount	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.DRIVER_HALT_BATA_FINAL_AMOUNT, false);

			validateHalt = driverHaltService.Validate_list_Date_DriverHalt(object.getInt("driverHaltId"),
					driverAttendanceFormat.format(date), driverAttendanceFormat.format(toDate), companyId);
			
			if (validateHalt != null && !validateHalt.isEmpty()) {
				object.put("AlreadyHalt", true);
				return object;
			}else {
				tripSheet = tripSheetService.getTripSheetDetails(tripsheetId);
				
				TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(tripSheet);
				
				UserProfileDto Orderingname   = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
				Integer 	   incomePlaceId  = Orderingname.getBranch_id();
				object.put("incomePlaceId", incomePlaceId);
				
				DriverHalt tHalt = tripSheetMobileBL.saveDriverHalt(object);
				
				driverDto = driverBL.GetDriverID(driverService.getDriver_Details_Firstnamr_LastName(object.getInt("driverHaltId"), companyId));
				expenseRefence = "" + driverDto.getDriver_firstname() + "_" + driverDto.getDriver_Lastname()+ "";
				tHalt.setDRIVERID(driverDto.getDriver_id());
				
				
				tHalt.setHALT_DATE_FROM(fromDate);
				tHalt.setHALT_DATE_TO(toDate);
				Double Point = tripSheetBL.Driver_Halt_Point(fromDate, toDate);
				ExpenseTotalPoint = Point;
				
				tHalt.setHALT_POINT(Point);
				if(driverBataFinalAmount) {               
					tHalt.setHALT_AMOUNT(object.getDouble("haltAmount", 0));
				}else {
					tHalt.setHALT_AMOUNT(Point * object.getDouble("haltAmount", 0));
				}
				tHalt.setTRIPSHEETID(tripsheetId);
				tHalt.setCOMPANY_ID(companyId);
				
				driverHaltService.register_New_DriverHalt(tHalt);
				
				
				// below Driver trip Halt amount Add expense Halt Bata
				TripSheetExpense TripExpense = new TripSheetExpense();
				TripExpense.setExpenseId((int) TripSheetFixedExpenseType.EXPENSE_TYPE_HALT_BATA);
				if(driverBataFinalAmount) {
					if(tripSheet.getVoucherDate() != null) {
						TripExpense.setVoucherDate(tripSheet.getVoucherDate());
					}
					TripExpense.setExpenseAmount(tHalt.getHALT_AMOUNT());
				}else {
					TripExpense.setExpenseAmount(ExpenseTotalPoint * tHalt.getHALT_AMOUNT());
				}
				TripExpense.setExpensePlaceId(tHalt.getHALT_PLACE_ID());
				TripExpense.setExpenseRefence(expenseRefence);
				TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
				TripExpense.setCreatedById(userId);
				TripExpense.setCompanyId(companyId);
				TripExpense.setDHID(tHalt.getDHID());
				TripExpense.setTallyCompanyId(object.getLong("tallyCompanyId",0));
				
				TripSheet tsheet = new TripSheet();
				tsheet.setTripSheetID(tripsheetId);
				TripExpense.setTripsheet(tsheet);
				
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp expenseDate = new java.sql.Timestamp(currentDateUpdate.getTime());
				TripExpense.setCreated(expenseDate);
				TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());
				
				tripSheetService.addTripSheetExpense(TripExpense);
				
				tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripsheetId, companyId);
				
				tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
			
			} 
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject removeTripSheetDriverHaltDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						tripsheetId					= 0;
		long						driverHaltId				= 0;
		MonthWiseVehicleExpense		monthWiseVehicleExpense		= null;
		DateWiseVehicleExpense		dateWiseVehicleExpense		= null;
		short						tripStatusId				= 0;
		double 						expenseAmount 				= 0;
		
		TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(tripsheetId);
		TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
		
		try {
			companyId    			= object.getInt("companyId");
			tripsheetId 			= object.getLong("tripsheetId");
			driverHaltId 			= object.getLong("driverHaltId");
			tripStatusId 			= object.getShort("tripStatusId");
			
			
			ValueObject data		= vehicleProfitAndLossService.getMonthAndDateWiseVehicleExpenseOfTripExpense(object);
			
			monthWiseVehicleExpense = (MonthWiseVehicleExpense) data.get("monthWiseVehicleExpense");
			dateWiseVehicleExpense 	= (DateWiseVehicleExpense) data.get("dateWiseVehicleExpense");
			
			if(tripStatusId == TripSheetStatus.TRIP_STATUS_CLOSED && monthWiseVehicleExpense != null && dateWiseVehicleExpense != null) {
				if(monthWiseVehicleExpense.getExpenseAmount() > 0) {
					expenseAmount = monthWiseVehicleExpense.getExpenseAmount() - object.getDouble("expenseAmount");
				}else {
					expenseAmount = 0;
				}
				object.put("expenseAmount", expenseAmount);
				vehicleProfitAndLossService.updateExpenseAmountOfTripExpense(object);
			}

			// Note: Delete Halt TripSheet Bata
			driverHaltService.delete_DriverHalt(driverHaltId, companyId);

			// Note : Delete TripSheet Halt Expense Entries
			tripSheetService.DELETE_TRIPSHEETEXPENSE_DRIVERHALT_ID(driverHaltId, companyId);

			// Note : This Update TripSheet Total Expense Updating The Value of Total Expense amount to TripSheet
			tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripsheetId, companyId);
			
			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
		
			return object;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject addTripSheetFuelDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		CustomUserDetails			userDetails					= null;
		HashMap<String, Object> 	configuration				= null;
		HashMap<String, Object> 	gpsConfiguration 			= null;
		HashMap<String, Object> 	fuelPriceConfig 			= null;
		String 						defaultFuelPrice 			= null;
		int                         maxFuelCapacity             = 0;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails();
			userDetails.setCompany_id(companyId);
			userDetails.setId(userId);
			
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			object.put("TripSheet", trip);
			
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			gpsConfiguration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_GPS_CONFIG);
			fuelPriceConfig 	= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.FUEL_PRICE_CONFIG);
			maxFuelCapacity     = (int) fuelPriceConfig.getOrDefault(FuelConfigurationConstants.MAX_FUEL_CAPACITY, 0);
			defaultFuelPrice	= (String) fuelPriceConfig.getOrDefault(FuelConfigurationConstants.FUEL_PRICE, 0);
			object.put("defaultFuelPrice", Double.parseDouble(defaultFuelPrice));
			
			ObjectMapper objectMapper = new ObjectMapper();
			object.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));
			object.put(FuelConfigurationConstants.MAX_FUEL_CAPACITY, maxFuelCapacity);
			List<FuelDto>	ShowAmount 	 = fuelService.Get_TripSheet_IN_FuelTable_List(tripsheetId, userDetails.getCompany_id(), userDetails.getId());
			List<FuelDto>	ShowAmountBL = fuelBL.prepareListofFuel(ShowAmount);
			object.put("fuel", ShowAmountBL);
			object.put("fTDiesel", fuelBL.prepareTotal_Tripsheet_fuel_details(ShowAmount));
			
			object.put("configuration",configuration);
			object.put("companyId",userDetails.getCompany_id());
			object.put("userId",userDetails.getId());
			object.put("allowGPSIntegration",(boolean)gpsConfiguration.get("allowGPSIntegration"));
			
			object.put(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, true));
			object.put(FuelConfigurationConstants.SHOW_REFERENCE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_REFERENCE_COL, true));
			object.put(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, configuration.getOrDefault(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, true));
			object.put(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, true));
			object.put(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, true));
			object.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, false));
			object.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL, false));
			object.put(FuelConfigurationConstants.VALIDATE_DRIVER1,configuration.getOrDefault(FuelConfigurationConstants.VALIDATE_DRIVER1, false));
			object.put(FuelConfigurationConstants.SHOW_MARK_AS_VOID_AUTO_SELECTED,configuration.getOrDefault(FuelConfigurationConstants.SHOW_MARK_AS_VOID_AUTO_SELECTED, false));
			
			String minBackDate = DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("noOfDaysForBackDate"));
			object.put("minBackDate",minBackDate);
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject removeTripSheetFuelDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		long						fuelId						= 0;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			fuelId 					= object.getLong("fuelId");


			List<Fuel> validateApproval = fuelService.getFuelValidate_APPROVAL_Status(fuelId, companyId);

			if (validateApproval != null && !validateApproval.isEmpty()) {
				object.put("ApprovedValidate", true);
			} else {
				
				java.util.Date    currentDateUpdate =  new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				Fuel fuel = fuelService.getFuel(fuelId, companyId);
				
				ValueObject		valueObject	= new ValueObject();
				valueObject.put("fuel", fuel);
				valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
				valueObject.put("txnAmount", fuel.getFuel_amount());
				valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(fuel.getFuel_date()));
				valueObject.put("txnTypeId", fuel.getFuel_id());
				valueObject.put("vid", fuel.getVid());
				valueObject.put("companyId", fuel.getCompanyId());
				

				//Fuel fueltoDelete = DeleteTosaveFuel(fuel);

				fuelService.deleteFuel(fuelId, toDate, userId, companyId);

				// Note : Delete TripSheet Halt Expense Entries
				tripSheetService.DELETE_TRIPSHEETEXPENSE_FUEL_ID(fuelId);

				// Note : This Update TripSheet Total Expense Updating The Value Of Total Expense amount to TripSheet
				tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripsheetId, companyId);
				
				object.put("deleteFuel", true);
				
				new Thread() {
					public void run() {
						try {
							vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(valueObject);
							vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(valueObject);
							vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(valueObject);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}		
				}.start();
			}
		
			return object;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject editTripSheetDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		CustomUserDetails			userDetails					= null;
		HashMap<String, Object> 	configuration				= null;
		Integer 					minAllowed 					= 0;
		Integer 					maxAllowed 					= 0;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails();
			userDetails.setCompany_id(companyId);
			userDetails.setId(userId);
			
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			object.put("TripSheet", trip);
			
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			object.put("configuration", configuration);
			
			VehicleDto CheckVehicleStatus 	= vehicleService.Get_Vehicle_Current_Status_TripSheetID(trip.getVid(), userDetails.getCompany_id());
			TripSheetDto	tripSheetDto	= tripSheetService.getLastClosedTripSheetByDateTimeById(trip.getVid(), trip.getDispatchedOn(), tripsheetId);
			TripSheetDto	nextTripSheet	= tripSheetService.getNextClosedTripSheetByDateTime(trip.getVid(), trip.getDispatchedOn(), tripsheetId);
			VehicleOdometerHistory	history = vehicleOdometerHistoryService.getLastOdometerHistory(trip.getVid(), trip.getTripOpeningKM());
			
			if(tripSheetDto != null) {
				minAllowed = tripSheetDto.getTripClosingKM();
			}else {
				if(history != null) {
					minAllowed = history.getVehicle_Odometer();
				}else {
					minAllowed = 0;
				}
			}
			
			if(tripSheetDto != null && nextTripSheet != null) {
				if(nextTripSheet.getTripOpeningKM() > tripSheetDto.getTripClosingKM()) {
					maxAllowed = nextTripSheet.getTripOpeningKM();
				}else {
					maxAllowed = minAllowed + CheckVehicleStatus.getVehicleExpectedKm();
				}
			} else if(nextTripSheet != null) {
				maxAllowed = nextTripSheet.getTripOpeningKM();
			}else {
				maxAllowed = minAllowed + CheckVehicleStatus.getVehicleExpectedKm();
			}

			object.put("minAllowed", minAllowed);	
			object.put("maxAllowed", maxAllowed);
			object.put("vehicle_ExpectedOdameter", CheckVehicleStatus.getVehicleExpectedKm());
			
			HashMap<String, Object> vehicleCnfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
							
			object.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, 
					vehicleCnfiguration.getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, false));
			object.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, 
					vehicleCnfiguration.getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, false));
			
			if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
				object.put("TripClosed", true);	
			} else {
				object.put("TripClosed", false);
			}
			
			object.put("TripSheetAdvance", tripSheetService.findAllTripSheetAdvance(tripsheetId, userDetails.getCompany_id()));
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateTripSheetDetails(ValueObject object) throws Exception {
		int														companyId						= 0;
		long													userId							= 0;
		long													tripsheetId						= 0;
		String													email							= null;
		CustomUserDetails										userDetails						= null;
		TripSheetAdvance  										advance							= null;
		int														oldVid							= 0;
		int														oldFirstDriId					= 0;
		int														oldSecondDriId					= 0;
		int														oldCleanerId					= 0;
		int														oldRouteId						= 0;
		double													oldFirstDriRoutePoint			= 0;
		double													oldSecDriRoutePoint				= 0;
		double													oldCleanerRoutePoint			= 0;
		Double													gpsUssageKM 					= 0.0;
		String													oldCloseDate					= null;
		HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>		incomeTxnCheckerHashMap			= null;
		HashMap<Long, VehicleProfitAndLossTxnChecker>			expenseTxnCheckerHashMap		= null;
		HashMap<Long, TripSheetExpenseDto> 						tripSheetExpenseHM				= null;
		HashMap<Long, TripSheetIncomeDto> 						tripSheetIncomeDtoHM			= null;
		HashMap<String, Object>									 config							= null;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			email					= object.getString("email");
			
			userDetails				= new CustomUserDetails();
			userDetails.setCompany_id(companyId);
			userDetails.setId(userId);
			userDetails.setEmail(email);
			
			incomeTxnCheckerHashMap		= new HashMap<>();
			expenseTxnCheckerHashMap	= new HashMap<>();
			tripSheetExpenseHM			= new HashMap<>();
			tripSheetIncomeDtoHM		= new HashMap<>();
			
			HashMap<String, Object> gpsConfig = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			config	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(tripsheetId);
			object.put("trip", SheetOLDData);
			
			
			if(SheetOLDData.getTripStutesId() == TripSheetStatus.TRIP_STATUS_SAVED) {
				
			VehicleDto	vehicleDto  = vehicleService.Get_Vehicle_Current_Status_TripSheetID(object.getInt("vid",0), companyId);
				if(vehicleDto.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
					object.put("inActiveStatus", true);
					object.put("vehicleStatus", vehicleDto.getVehicle_Status());
					return object;
				}else if(!validateSaveTripSheet(object)) {
					return object;
				}
			}
			
			
			
			
			oldVid					= SheetOLDData.getVid();
			oldFirstDriId 			= SheetOLDData.getTripFristDriverID();
			oldSecondDriId 			= SheetOLDData.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO ? SheetOLDData.getTripSecDriverID() : 0;
			oldCleanerId 			= SheetOLDData.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO ? SheetOLDData.getTripCleanerID() : 0;
			oldRouteId				= SheetOLDData.getRouteID();
			oldFirstDriRoutePoint 	= SheetOLDData.getTripFristDriverRoutePoint() != null ? SheetOLDData.getTripFristDriverRoutePoint() : 0;
			oldSecDriRoutePoint 	= SheetOLDData.getTripSecDriverRoutePoint() != null ? SheetOLDData.getTripSecDriverRoutePoint() : 0;
			oldCleanerRoutePoint 	= SheetOLDData.getTripCleanerRoutePoint() != null ? SheetOLDData.getTripCleanerRoutePoint() : 0;
			
			
			if(!SheetOLDData.getVid().equals(object.getInt("vid",0))) {
				
				VehicleDto	vehicleDto  = vehicleService.Get_Vehicle_Current_Status_TripSheetID(object.getInt("vid",0), companyId);
				TripSheetDto	activeTrip	=	tripSheetService.getActiveTripSheetByVid(object.getInt("vid"));
					if(vehicleDto.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
						object.put("inActiveStatus", true);
						object.put("vehicleStatus", vehicleDto.getVehicle_Status());
						return object;
					}else if(activeTrip != null) {
						object.put("activeTripSheet", activeTrip);
						return object;
					}
				}
			
			if(SheetOLDData.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
				oldCloseDate = driverAttendanceFormat.format(SheetOLDData.getClosetripDate());
			}
			
				updateDispatchDetailsOfEdit(object);
			
			boolean validateTripsheet = object.getBoolean("validateTripsheet");
						
			if (validateTripsheet) {
				TripSheet 			tripSheet 			= tripSheetMobileBL.updateTripDetails(object);
				TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
				
				if (tripSheet.getRouteID() != TRIP_SHEET_DEFALAT_ZERO) {
					
					TripRoute routePoint = tripRouteService.getTriproutePoint(tripSheet.getRouteID(), companyId);
					
					if (tripSheet.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO && routePoint.getRoutrAttendance() != null) {
						tripSheet.setTripFristDriverRoutePoint(routePoint.getRoutrAttendance());
					} else {
						tripSheet.setTripFristDriverRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
					}
					
					if (tripSheet.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO && routePoint.getRoutrAttendance() != null) {
						tripSheet.setTripSecDriverRoutePoint(routePoint.getRoutrAttendance());
					} else {
						tripSheet.setTripSecDriverRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
					}
					
					if (tripSheet.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO && routePoint.getRoutrAttendance() != null) {
						tripSheet.setTripCleanerRoutePoint(routePoint.getRoutrAttendance());
					} else {
						tripSheet.setTripCleanerRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
					}
				}
				
				if (SheetOLDData.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
					tripSheet.setDispatchedLocationId(SheetOLDData.getDispatchedLocationId());
					tripSheet.setDispatchedById(SheetOLDData.getDispatchedById());
				}else {
					UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
					tripSheet.setDispatchedLocationId(Orderingname.getBranch_id());
					tripSheet.setDispatchedById(userDetails.getId());
				}
				
				tripSheet.setClosedById(SheetOLDData.getClosedById());
				tripSheet.setClosedLocationId(SheetOLDData.getClosedLocationId());
				tripSheet.setAcclosedById(SheetOLDData.getAcclosedById());
				tripSheet.setAcclosedLocationId(SheetOLDData.getAcclosedLocationId());
				tripSheet.setAcclosedByTime(SheetOLDData.getAcclosedByTime());
				
				tripSheet.setTripFristDriverBata(SheetOLDData.getTripFristDriverBata());
				tripSheet.setTripSecDriverBata(SheetOLDData.getTripSecDriverBata());
				tripSheet.setTripCleanerBata(SheetOLDData.getTripCleanerBata());
				tripSheet.setCompanyId(userDetails.getCompany_id());
				tripSheet.setCreatedById(SheetOLDData.getCreatedById());
				tripSheet.setLastModifiedById(userDetails.getId());
				tripSheet.setCreated(SheetOLDData.getCreated());
				
				tripSheet.setTripSheetDocument(SheetOLDData.isTripSheetDocument());
				tripSheet.setTripSheetDocumentId(SheetOLDData.getTripSheetDocumentId());
				
				if(SheetOLDData.getTripStutesId() ==  TripSheetStatus.TRIP_STATUS_CLOSED) {
					tripSheet.setTripUsageKM(object.getInt("closetripKm",0) - tripSheet.getTripOpeningKM());
					tripSheet.setTripClosingKM(object.getInt("closetripKm",0));
					tripSheet.setDispatchedById(SheetOLDData.getDispatchedById());
					tripSheet.setDispatchedLocationId(SheetOLDData.getDispatchedLocationId());
					
					if((boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
						if(SheetOLDData.getTripGpsOpeningKM() != null && SheetOLDData.getTripGpsOpeningKM() > 0 && SheetOLDData.getTripGpsClosingKM() != null && SheetOLDData.getTripGpsClosingKM() > 0) {
							gpsUssageKM	= SheetOLDData.getTripGpsClosingKM() - SheetOLDData.getTripGpsOpeningKM();
						}else {
							try {
								gpsUssageKM = vehicleGPSDetailsService.getGPSRunKMBetweenTwoDates(tripSheet.getVid(), DateTimeUtility.getDateFromTimeStamp(new Timestamp(tripSheet.getDispatchedByTime().getTime()), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), 
																		DateTimeUtility.getDateFromTimeStamp(new Timestamp(tripSheet.getClosedByTime().getTime()), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), userDetails.getCompany_id());
							} catch (Exception e) {
								System.err.println("Exception while Edit/update getting gps run km  "+e);
								gpsUssageKM = SheetOLDData.getGpsTripUsageKM();
							}
						}
					}
					
					tripSheet.setGpsTripUsageKM(gpsUssageKM);
				}
				
				tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
				
				tripSheetService.updateTripSheet(tripSheet);
				
				TripSheetDto tripDto 	 = tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
				java.util.Date date 	 = dateFormat.parse(tripDto.getTripSheetCloseDateStr());
				java.sql.Date Close_date = new java.sql.Date(date.getTime());
				
				if(oldFirstDriId != tripSheet.getTripFristDriverID()) {
					if(tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE, (long) 0, oldFirstDriId, userDetails.getCompany_id());
					if(tripSheet.getTripFristDriverID() > 0 ) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE,
								SheetOLDData.getTripSheetID(), tripSheet.getTripFristDriverID(), userDetails.getCompany_id());
					}
				}
					
					if(tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
						Add_TripSheet_TO_driver_Attendance_Edit(tripSheet.getTripFristDriverID(),
								tripDto.getTripFristDriverName(), Close_date, tripSheet.getTripFristDriverRoutePoint(), tripDto, userDetails);
					}
					
					DriverAttendanceRepository.deleteDriverAttendanceRoutePoint(tripsheetId, oldFirstDriId, userDetails.getCompany_id());
					
					if ((boolean) config.getOrDefault("saveDriverLedgerDetails", false))
					driverLedgerService.updateDriverLedgerWhenDriverUpdated(tripsheetId, tripSheet.getTripSheetNumber(), tripSheet.getTripFristDriverID(), oldFirstDriId,tripSheet.getTripOpenDate());
					}

				
				if(oldSecondDriId != tripSheet.getTripSecDriverID()) {
					if(tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE, (long) 0, oldSecondDriId, userDetails.getCompany_id());				
						if(tripSheet.getTripSecDriverID() > 0) {
							driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE,
								SheetOLDData.getTripSheetID(), tripSheet.getTripSecDriverID(), userDetails.getCompany_id());
						}
					}
					
					if(tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
						Add_TripSheet_TO_driver_Attendance_Edit(tripSheet.getTripSecDriverID(),
								tripDto.getTripSecDriverName(), Close_date, tripSheet.getTripSecDriverRoutePoint(), tripDto, userDetails);
					}
					
					DriverAttendanceRepository.deleteDriverAttendanceRoutePoint(tripsheetId, oldSecondDriId, userDetails.getCompany_id());
				}
				
				if(oldCleanerId != tripSheet.getTripCleanerID()) {
					if(tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE, (long) 0, oldCleanerId, userDetails.getCompany_id());
					if(tripSheet.getTripCleanerID() > 0 ) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE,
							SheetOLDData.getTripSheetID(), tripSheet.getTripCleanerID(), userDetails.getCompany_id());
					}
				}
					
					if(tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
						Add_TripSheet_TO_driver_Attendance_Edit(tripSheet.getTripCleanerID(),
								tripDto.getTripCleanerName(), Close_date, tripSheet.getTripCleanerRoutePoint(), tripDto, userDetails);
					}
					
					DriverAttendanceRepository.deleteDriverAttendanceRoutePoint(tripsheetId, oldCleanerId, userDetails.getCompany_id());
				
				}
				
				if(tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
					if(tripSheet.getRouteID() > 0 && oldRouteId != tripSheet.getRouteID()) {
						
						if(oldFirstDriRoutePoint != tripSheet.getTripFristDriverRoutePoint()) {
							DriverAttendanceRepository.updateDriverAttendanceRoutePoint(tripSheet.getTripFristDriverRoutePoint(), tripSheet.getRouteID(),
									tripsheetId, tripSheet.getTripFristDriverID(), userDetails.getCompany_id());
						}
						
						if(oldSecDriRoutePoint != tripSheet.getTripSecDriverRoutePoint()) {
							DriverAttendanceRepository.updateDriverAttendanceRoutePoint(tripSheet.getTripSecDriverRoutePoint(), tripSheet.getRouteID(),
									tripsheetId, tripSheet.getTripSecDriverID(), userDetails.getCompany_id());
						}
						
						if(oldCleanerRoutePoint != tripSheet.getTripCleanerRoutePoint()) {
							DriverAttendanceRepository.updateDriverAttendanceRoutePoint(tripSheet.getTripCleanerRoutePoint(), tripSheet.getRouteID(),
									tripsheetId, tripSheet.getTripCleanerID(), userDetails.getCompany_id());
						}
						
					}
				}
				
				if(tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
					if(oldCloseDate.compareTo(driverAttendanceFormat.format(tripSheet.getClosetripDate())) != 0){
						
						if(tripSheet.getTripFristDriverID() > 0) {
							DriverAttendanceRepository.deleteDriverAttendanceRoutePoint(tripsheetId, tripSheet.getTripFristDriverID(), userDetails.getCompany_id());
							
							Add_TripSheet_TO_driver_Attendance_Edit(tripSheet.getTripFristDriverID(),
									tripDto.getTripFristDriverName(), Close_date, tripSheet.getTripFristDriverRoutePoint(), tripDto, userDetails);
						}
						
						if(tripSheet.getTripSecDriverID() > 0) {
							DriverAttendanceRepository.deleteDriverAttendanceRoutePoint(tripsheetId, tripSheet.getTripSecDriverID(), userDetails.getCompany_id());
							
							Add_TripSheet_TO_driver_Attendance_Edit(tripSheet.getTripSecDriverID(),
									tripDto.getTripSecDriverName(), Close_date, tripSheet.getTripSecDriverRoutePoint(), tripDto, userDetails);
						}
						
						if(tripSheet.getTripCleanerID() > 0) {
							DriverAttendanceRepository.deleteDriverAttendanceRoutePoint(tripsheetId, tripSheet.getTripCleanerID(), userDetails.getCompany_id());
							
							Add_TripSheet_TO_driver_Attendance_Edit(tripSheet.getTripCleanerID(),
									tripDto.getTripCleanerName(), Close_date, tripSheet.getTripCleanerRoutePoint(), tripDto, userDetails);
						}
					}
				}
				
				if(tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
					
					if(tripSheet.getVid() > 0 && oldVid != tripSheet.getVid()) {
						
						List<TripSheetExpenseDto> tripSheetExpenseList	=	tripSheetService.findAllTripSheetExpense(tripSheet.getTripSheetID(), userDetails.getCompany_id());
						
						VehicleProfitAndLossTxnChecker		profitAndLossExpenseTxnChecker	= null;
						if(tripSheetExpenseList != null) {
							ValueObject		expenseObject	=	null;
							for(TripSheetExpenseDto	expenseDto : tripSheetExpenseList) {
								if(expenseDto.getExpenseAmount() > 0.0 && (expenseDto.getFuel_id() == null || expenseDto.getFuel_id() == 0) ) {
									
									//add new vehicle Expense details in Profit And Loss
									expenseObject	= new ValueObject();
									expenseObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
									expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, tripSheet.getTripSheetID());
									expenseObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
									expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
									expenseObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
									expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, tripSheet.getVid());
									expenseObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, expenseDto.getExpenseId());
									expenseObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, expenseDto.getTripExpenseID());

									profitAndLossExpenseTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(expenseObject);
									vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);
									
									expenseTxnCheckerHashMap.put(profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossExpenseTxnChecker);
									tripSheetExpenseHM.put(expenseDto.getTripExpenseID(), expenseDto);
									
									//delete previous vehicle Expense details in Profit And Loss
									ValueObject	deleteExpense = new ValueObject();
									TripSheet tripExpense = tripSheetService.getTripSheetData(tripsheetId, companyId);
									deleteExpense.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
									deleteExpense.put("txnAmount", expenseDto.getExpenseAmount());
									deleteExpense.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(tripExpense.getClosetripDate()));
									deleteExpense.put("txnTypeId", tripsheetId);
									deleteExpense.put("expenseId", expenseDto.getExpenseId());
									deleteExpense.put("vid", oldVid);
									deleteExpense.put("companyId", userDetails.getCompany_id());
									
									new Thread() {
										public void run() {
											try {
												vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(deleteExpense);
												vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(deleteExpense);
												vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(deleteExpense);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}		
									}.start();
								}
							}
						}
						
						List<TripSheetIncomeDto> tripSheetIncomeList = tripSheetService.findAllTripSheetIncome(tripSheet.getTripSheetID(), userDetails.getCompany_id());
						if(tripSheetIncomeList != null) {

							for(TripSheetIncomeDto	incomeDto : tripSheetIncomeList) {
								if(incomeDto.getIncomeAmount() > 0.0) {
									
									//add new vehicle Income details in Profit And Loss
									ValueObject		incomeObject	= new ValueObject();
									incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
									incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, tripSheet.getTripSheetID());
									incomeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
									incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
									incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
									incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, tripSheet.getVid());
									incomeObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, incomeDto.getIncomeId());
									incomeObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, incomeDto.getTripincomeID());

									VehicleProfitAndLossIncomeTxnChecker profitAndLossIncomeTxnChecker	= txnCheckerBL.getVehicleProfitAndLossIncomeTxnChecker(incomeObject);
									vehicleProfitAndLossIncomeTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);
									
									incomeTxnCheckerHashMap.put(profitAndLossIncomeTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossIncomeTxnChecker);
									tripSheetIncomeDtoHM.put(incomeDto.getTripincomeID(), incomeDto);
									
									//delete previous vehicle Income details in Profit And Loss
									ValueObject	 deleteIncome = new ValueObject();
									TripSheet	deleteIncomeTripSheet = tripSheetService.getTripSheet(tripSheet.getTripSheetID());
									deleteIncome.put("tripSheet", deleteIncomeTripSheet);
									deleteIncome.put("userDetails", userDetails);
									deleteIncome.put("txnTypeId", tripSheet.getTripSheetID());
									deleteIncome.put("tripIncome", incomeDto);
									deleteIncome.put("vid", oldVid);
									deleteIncome.put("incomeId", incomeDto.getIncomeId());
									deleteIncome.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, incomeDto.getTripincomeID());
									deleteIncome.put("incomeAmount", incomeDto.getIncomeAmount());
									deleteIncome.put("transactionDate", DateTimeUtility.geTimeStampFromDate(tripSheet.getClosetripDate()));
									
									vehicleProfitAndLossService.runThreadForDeleteIncome(deleteIncome);
								}
							}
						}
						
					}
					
				}
				
				advance 	= tripSheetMobileBL.saveOrDispatchTripsheetAdvance(object); 
				if (advance.getAdvanceAmount() != null && advance.getAdvanceAmount() > 0) {
					advance.setTripsheet(tripSheet);
					advance.setCompanyId(companyId);
					advance.setCreatedById(userId);						
					advance.setDriverId(SheetOLDData.getTripFristDriverID());
					tripSheetService.addTripSheetAdvance(advance);
				}
				
				if (tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
					
					vehicleService.Update_Vehicle_Status_TripSheetID(tripSheet.getTripSheetID(), tripSheet.getVid(),
							userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE);
					
					if(tripSheet.getVid() > 0 && oldVid != tripSheet.getVid()) {
						VehicleAccidentDetails	details = accidentDetailsService.getVehicleAccidentDetailsByTripSheetId(tripSheet.getTripSheetID());
						
						if(details == null) {
							vehicleService.Update_Vehicle_Status_TripSheetID(tripSheet.getTripSheetID(), oldVid,
									userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
						}
					}

					if (tripSheet.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE,
								tripSheet.getTripSheetID(), tripSheet.getTripFristDriverID(), userDetails.getCompany_id());
					}
					if (tripSheet.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE,
								tripSheet.getTripSheetID(), tripSheet.getTripSecDriverID(), userDetails.getCompany_id());
					}
					if (tripSheet.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE,
								tripSheet.getTripSheetID(), tripSheet.getTripCleanerID(), userDetails.getCompany_id());
					}
				}
				
				object.put("saveTripSheet", true);
				Update_Current_OdometerinVehicle_getTripDailySheetTo(tripSheet, companyId, userId);
				
				if(tripSheetExpenseHM != null && tripSheetExpenseHM.size() > 0) {

					ValueObject	valueObject	= new ValueObject();
					valueObject.put("tripSheet", tripSheetService.getTripSheet(tripSheet.getTripSheetID()));
					valueObject.put("userDetails", userDetails);
					valueObject.put("tripSheetExpenseHM", tripSheetExpenseHM);
					valueObject.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);

					vehicleProfitAndLossService.runThreadForTripSheetExpenses(valueObject);
				}
				
				if(tripSheetIncomeDtoHM != null && tripSheetIncomeDtoHM.size() > 0) {
					ValueObject	valueObject	= new ValueObject();
					valueObject.put("tripSheet", tripSheetService.getTripSheet(tripSheet.getTripSheetID()));
					valueObject.put("userDetails", userDetails);
					valueObject.put("tripSheetIncomeHM", tripSheetIncomeDtoHM);
					valueObject.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap);
					//	valueObject.put(VehicleExpenseTypeConstant.INCOME_TYPE, incomeTxnCheckerHashMap);

					vehicleProfitAndLossService.runThreadForTripSheetIncome(valueObject);
				}
								
			}	
								
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void updateDispatchDetailsOfEdit(ValueObject object) throws Exception {
		VehicleDto				CheckVehicleStatus			= null;
		boolean 				checkFuelMeterStatus 		= false;
		boolean 				isNewVehicleTripsheet 		= false;
		try {
			
			TripSheet	trip 				  = (TripSheet)object.get("trip");
			int			companyId			  = object.getInt("companyId");
			
			CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(trip.getVid(), companyId);
			object.put("CheckVehicleStatus", CheckVehicleStatus);
			
			if (CheckVehicleStatus != null) {
				if (trip.getTripOpeningKM() <= CheckVehicleStatus.getVehicle_ExpectedOdameter()) {
					checkFuelMeterStatus = true;
				}
			} else {
				isNewVehicleTripsheet = true;
			}
		
			object.put("checkFuelMeterStatus", checkFuelMeterStatus);
			object.put("isNewVehicleTripsheet", isNewVehicleTripsheet);
			
			if (checkFuelMeterStatus || isNewVehicleTripsheet) {
				validateDispatchDetailsByDateAndTime(object);
			} else {
				updateDispatchDetailsByVehicleStatusWhileEdit(object);
			}
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public void updateDispatchDetailsByVehicleStatusWhileEdit (ValueObject object) throws Exception {
		String 					Link 					= "";
		String 					TIDMandatory 			= "";
		boolean 				validateTripsheet 		= false;
		try {
			TripSheet 	trip 				  = (TripSheet)object.get("trip");
			VehicleDto	CheckVehicleStatus	  = (VehicleDto) object.get("CheckVehicleStatus");
			
			if (CheckVehicleStatus != null) {
				
				if (trip.getTripOpeningKM() > CheckVehicleStatus.getVehicle_ExpectedOdameter()) {
					Link += " Maxminum Allow Odometer is " + CheckVehicleStatus.getVehicle_ExpectedOdameter()
					+ " kindly Check Odometer ";
				}
				
				TIDMandatory += " This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status. "
						+ " You can't create Service Entires. "+Link;
				object.put("TIDMandatory", TIDMandatory);
				
				validateTripsheet = false;
				object.put("validateTripsheet", validateTripsheet);
			}
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	// Below update code is duplicate and is not used in mobile or web. Please ignore
	@Override
	public ValueObject updateTripSheetEditData(ValueObject object) throws Exception {
		int							companyId						= 0;
		long						userId							= 0;
		long						tripsheetId						= 0;
		String						email							= null;
		CustomUserDetails			userDetails						= null;
		boolean						allowToEditTripSheetCloseDate	= false;
		boolean						allowToEditVehicleInTripSheet	= false;
		boolean						CheckFuelMeterStatus 			= false;
		boolean 					isNewVehicleTripSheet 			= false;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			email					= object.getString("email");
			
			userDetails				= new CustomUserDetails();
			userDetails.setCompany_id(companyId);
			userDetails.setId(userId);
			userDetails.setEmail(email);
			object.put("userDetails", userDetails);
			
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			object.put("trip", trip);
			
			HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			allowToEditTripSheetCloseDate	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.ALLOW_TO_EDIT_TRIP_SHEET_CLOSE_DATE, false);
			allowToEditVehicleInTripSheet	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.ALLOW_TO_EDIT_VEHICLE_IN_TRIPSHEET, false);
			object.put("allowToEditVehicleInTripSheet", allowToEditVehicleInTripSheet);			
			
			if (trip.getVid() == null || trip.getVid() <= 0) {
				isNewVehicleTripSheet = true;
			}
			
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(trip.getVid(), companyId);
			
			if (isNewVehicleTripSheet || trip.getTripOpeningKM() <= CheckVehicleStatus.getVehicle_ExpectedOdameter()) {
				CheckFuelMeterStatus = true;
			}
			
			if (CheckFuelMeterStatus || isNewVehicleTripSheet) {
				TripSheet 			Sheet 				= tripSheetMobileBL.updateTripDetails(object);
				TripSheet 			SheetOLDData 		= tripSheetService.getTripSheetData(tripsheetId, companyId);
				TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
				
				object.put("Sheet", Sheet);
				object.put("SheetOLDData", SheetOLDData);
				
				Sheet.setTripClosingKM(SheetOLDData.getTripClosingKM());
				Sheet.setTripUsageKM(SheetOLDData.getTripUsageKM());
				// Sheet.setCloseTripStatus(SheetOLDData.getCloseTripStatus());
				Sheet.setCloseTripStatusId(SheetOLDData.getCloseTripStatusId());
				Sheet.setCloseTripReference(SheetOLDData.getCloseTripReference());
				Sheet.setCloseTripAmount(SheetOLDData.getCloseTripAmount());
				// Sheet.setCloseTripNameBy(SheetOLDData.getCloseTripNameBy());
				Sheet.setCloseTripNameById(SheetOLDData.getCloseTripNameById());
				Sheet.setTripStutesId(SheetOLDData.getTripStutesId());
				Sheet.setTripCommentTotal(SheetOLDData.getTripCommentTotal());
				Sheet.setDispatchedById(SheetOLDData.getDispatchedById());
				Sheet.setDispatchedLocationId(SheetOLDData.getDispatchedLocationId());
				Sheet.setDispatchedByTime(SheetOLDData.getDispatchedByTime());
				Sheet.setClosedById(SheetOLDData.getClosedById());
				Sheet.setClosedLocationId(SheetOLDData.getClosedLocationId());
				Sheet.setClosedByTime(SheetOLDData.getClosedByTime());
				Sheet.setAcclosedById(SheetOLDData.getAcclosedById());
				Sheet.setAcclosedLocationId(SheetOLDData.getAcclosedLocationId());
				Sheet.setAcclosedByTime(SheetOLDData.getAcclosedByTime());
				Sheet.setCompanyId(userDetails.getCompany_id());
				Sheet.setCreatedById(SheetOLDData.getCreatedById());
				Sheet.setLastModifiedById(userDetails.getId());
				Sheet.setCreated(SheetOLDData.getCreated());
				
				if(allowToEditTripSheetCloseDate) {
					
					if(Sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
						Sheet.setClosetripDate(dateFormat.parse(object.getString("toDate")));	
					} else {
						Sheet.setClosetripDate(SheetOLDData.getClosetripDate());
					}
					
				} else {
					Sheet.setClosetripDate(SheetOLDData.getClosetripDate());		
				}
				
				if (trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
					UserProfileDto Orderingname = userProfileService.getUserDeatilsByEmail(userDetails.getEmail(), userDetails.getCompany_id());
					Sheet.setDispatchedLocationId(Orderingname.getBranch_id());
					Sheet.setDispatchedById(userDetails.getId());
					Sheet.setDispatchedByTime(SheetOLDData.getDispatchedByTime());
				}
				
				// Note: Old Select Frist Driver Detail TRIPROUTE TO ACTIVE
				Update_Driver_Status_IN_TripSheetEdit_ACTIVE_TRIPROUTE(Sheet.getTripFristDriverID(),
						SheetOLDData.getTripFristDriverID(), Sheet.getTripSheetID(), userDetails.getCompany_id());

				Update_Driver_Status_IN_TripSheetEdit_ACTIVE_TRIPROUTE(Sheet.getTripSecDriverID(), SheetOLDData.getTripSecDriverID(),
						Sheet.getTripSheetID(), userDetails.getCompany_id());

				Update_Driver_Status_IN_TripSheetEdit_ACTIVE_TRIPROUTE(Sheet.getTripCleanerID(), SheetOLDData.getTripCleanerID(),
						Sheet.getTripSheetID(), userDetails.getCompany_id());

				tripSheetService.updateTripSheet(Sheet);
				tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
				
				// Change Route expense Details
				Update_Route_fixed_expense_Amount_Old_Route(Sheet.getRouteID(), SheetOLDData.getRouteID(), Sheet, userDetails);
				Update_Route_fixed_income_Amount_Old_Route(Sheet.getRouteID(), SheetOLDData.getRouteID(), Sheet, userDetails);
				
				/*if(SheetOLDData.getsu != null && OldSubRouteID > 0)
					Update_SubRoute_fixed_expense_Amount_Old_Route(Sheet.getRouteID(), OldSubRouteID, Sheet);*/
				
				object.put("saveTripSheet", true);
				Update_Current_OdometerinVehicle_getTripDailySheetTo(Sheet, companyId, userId);
				
				
				if(allowToEditVehicleInTripSheet) {
					allowEditVehicleInTripsheet(object);
				}
				
			} else {
				updateDispatchDetailsByVehicleStatusWhileEdit(object);
			}
						
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void Update_Driver_Status_IN_TripSheetEdit_ACTIVE_TRIPROUTE(Integer new_DriverSelect_ID,
			Integer Old_Driverselect_ID, Long TripSheet_ID, int compId) throws Exception {
		
		if (new_DriverSelect_ID.equals(Old_Driverselect_ID)) {
		} else {
			if (Old_Driverselect_ID != TRIP_SHEET_DEFALAT_ZERO) {
				driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
						TRIP_SHEET_DEFALAT_VALUE, Old_Driverselect_ID, compId);
			}
			if (new_DriverSelect_ID != TRIP_SHEET_DEFALAT_ZERO) {
				driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE, TripSheet_ID,
						new_DriverSelect_ID, compId);
			}
		}
		
	}
	
	public void Update_Route_fixed_expense_Amount_Old_Route(Integer New_RouteID, Integer Old_RouteID, TripSheet Sheet, CustomUserDetails userDetails)
			throws Exception {
	 try {
			
			if (New_RouteID.equals(Old_RouteID)) {
				// Old Route ID and New TripSheet Edit Route ID same No Change
			} else {
				
				if (New_RouteID != TRIP_SHEET_DEFALAT_ZERO) {
					// Delete all Old Expense in TripSheet Details To Remove
					tripSheetService.Delete_TSID_to_TripSheetExpence(Sheet.getTripSheetID(),
							userDetails.getCompany_id());
	
					List<TripRoutefixedExpense> RoutefixedExpense = tripRouteService
							.listTripRouteFixedExpenes(New_RouteID, userDetails.getCompany_id());
					if (RoutefixedExpense != null && !RoutefixedExpense.isEmpty()) {
						for (TripRoutefixedExpense tripRoutefixedExpense : RoutefixedExpense) {

							TripSheetExpense TripExpense = new TripSheetExpense();
							TripExpense.setExpenseId(tripRoutefixedExpense.getExpenseId());
							TripExpense.setExpenseAmount(tripRoutefixedExpense.getExpenseAmount());
							TripExpense.setExpensePlaceId(tripRoutefixedExpense.getExpensePlaceId());
							TripExpense.setExpenseRefence(tripRoutefixedExpense.getExpenseRefence());
							TripExpense.setExpenseFixedId(tripRoutefixedExpense.getFixedTypeId());
							TripExpense.setCreatedById(userDetails.getId());
							TripExpense.setTripsheet(Sheet);
							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

							TripExpense.setCreated(toDate);
							TripExpense.setCompanyId(userDetails.getCompany_id());
							TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());

							tripSheetService.addTripSheetExpense(TripExpense);

							Object[] expenseIds = tripSheetService
									.getDriverAdvanceAndBataExpenseId(userDetails.getCompany_id());

							if (expenseIds != null) {
								if (expenseIds[0] != null && expenseIds[0] == tripRoutefixedExpense.getExpenseId()) {

								}
								if (expenseIds[1] != null && expenseIds[1] == tripRoutefixedExpense.getExpenseId()) {
									tripSheetService.Update_TripSheet_FIRST_Driver_BATA(
											tripRoutefixedExpense.getExpenseAmount(), Sheet.getTripSheetID(), userDetails.getCompany_id());
								}
								if (expenseIds[2] != null && expenseIds[2] == tripRoutefixedExpense.getExpenseId()) {

								}
								if (expenseIds[3] != null && expenseIds[3] == tripRoutefixedExpense.getExpenseId()) {
									tripSheetService.Update_TripSheet_SECOND_Driver_BATA(
											tripRoutefixedExpense.getExpenseAmount(), Sheet.getTripSheetID(), userDetails.getCompany_id());
								}
								if (expenseIds[4] != null && expenseIds[4] == tripRoutefixedExpense.getExpenseId()) {

								}
								if (expenseIds[5] != null && expenseIds[5] == tripRoutefixedExpense.getExpenseId()) {
									tripSheetService.Update_TripSheet_CLEANER_BATA(
											tripRoutefixedExpense.getExpenseAmount(), Sheet.getTripSheetID(), userDetails.getCompany_id());
								}
							}

						}
						// Note : This Update TripSheet Total Expense Updating
						// The Value Of
						// Total Expense amount to TripSheet
						tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(Sheet.getTripSheetID(), userDetails.getCompany_id());
					}

				} else {
					// Because Select Route in Enter Route Details
					// Delete all Old Expense in TripSheet Details To Remove
					tripSheetService.Delete_TSID_to_TripSheetExpence(Sheet.getTripSheetID(),
							userDetails.getCompany_id());

					// Note : This Update TripSheet Total Expense Updating The
					// Value Of
					// Total Expense amount to TripSheet
					tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(Sheet.getTripSheetID(), userDetails.getCompany_id());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Update_Route_fixed_income_Amount_Old_Route(Integer New_RouteID, Integer Old_RouteID, TripSheet Sheet, CustomUserDetails userDetails)
			throws Exception {
		try {
			
			if (New_RouteID.equals(Old_RouteID)) {
				
			} else {

				if (New_RouteID != TRIP_SHEET_DEFALAT_ZERO) {
					// Delete all Old Expense in TripSheet Details To Remove
					tripSheetService.Delete_TSTD_to_TripSheetIncome(Sheet.getTripSheetID(),
							userDetails.getCompany_id());

					// not equal to zreo get all fixed expense

					List<TripRoutefixedIncome> RoutefixedIncome = tripRouteService.listTripRouteFixedIncomeList(New_RouteID,
							userDetails.getCompany_id());
					if (RoutefixedIncome != null && !RoutefixedIncome.isEmpty()) {
						for (TripRoutefixedIncome tripRoutefixedIncome : RoutefixedIncome) {

							TripSheetIncome TripIncome = new TripSheetIncome();
							TripIncome.setIncomeId(tripRoutefixedIncome.getIncomeId());
							TripIncome.setIncomeAmount(tripRoutefixedIncome.getIncomeAmount());
							TripIncome.setIncomePlaceId(tripRoutefixedIncome.getIncomePlaceId());
							TripIncome.setIncomeRefence(tripRoutefixedIncome.getIncomeRefence());
							TripIncome.setIncomeFixedId(tripRoutefixedIncome.getIncomeFixedId());
							TripIncome.setIncomeCollectedById(userDetails.getId());// here we used UserId
							TripIncome.setIncomeRefence(tripRoutefixedIncome.getIncomeRefence());
							TripIncome.setCreatedById(userDetails.getId());
							TripIncome.setTripsheet(Sheet);
							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

							TripIncome.setCreated(toDate);
							TripIncome.setCompanyId(userDetails.getCompany_id());

							tripSheetService.addTripSheetIncome(TripIncome);
/*
							Object[] expenseIds = tripSheetService
									.getDriverAdvanceAndBataExpenseId(userDetails.getCompany_id());

							if (expenseIds != null) {}*/

						}

						// Note : This Update TripSheet Total Expense Updating
						// The Value Of
						// Total Expense amount to TripSheet
						tripSheetService.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(Sheet.getTripSheetID(), userDetails.getCompany_id());
					}

				} else {

					// Because Select Route in Enter Route Details
					// Delete all Old Expense in TripSheet Details To Remove
					tripSheetService.Delete_TSTD_to_TripSheetIncome(Sheet.getTripSheetID(),
							userDetails.getCompany_id());

					// Note : This Update TripSheet Total Expense Updating The
					// Value Of
					// Total Expense amount to TripSheet
					tripSheetService.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(Sheet.getTripSheetID(), userDetails.getCompany_id());

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void allowEditVehicleInTripsheet(ValueObject object) throws Exception {
		CustomUserDetails			userDetails						= null;
		int							oldVehicleId					= 0;
		List<Fuel>					fuel							= null;
		List<UreaEntries>			urea							= null;
		List<TripSheetIncome>		tripSheetIncome					= null;
		List<TripSheetExpense>		tripSheetExpense				= null;
		TripSheet 					Sheet							= null;
		TripSheet 					SheetOLDData					= null;
		ValueObject					newVehicleFuelEntry				= null;
		ValueObject					newVehicleUreaEntry				= null;
		ValueObject					tripSheetIncomeProfitLoss		= null;
		ValueObject					tripSheetExpenseProfitLoss		= null;
		try {
			userDetails 					= (CustomUserDetails) object.get("userDetails");
			Sheet 							= (TripSheet) object.get("Sheet");
			SheetOLDData 					= (TripSheet) object.get("SheetOLDData");
			oldVehicleId					= SheetOLDData.getVid();
									
			if(Sheet.getVid() != SheetOLDData.getVid()) {   // if vehicle has been changed
					
				ValueObject					plObject = null;
					fuel = fuelRepository.Get_TripSheet_IN_FuelTable_List(Sheet.getTripSheetID(), userDetails.getCompany_id());
					if(fuel != null && !fuel.isEmpty()) {
						for(Fuel fuelDetails : fuel) {
							fuelService.deletePreviousVehicleFuelEntries(fuelDetails);
						}
					}
					
					fuelService.updateVidOfFuelEntries(Sheet.getTripSheetID(), Sheet.getVid(), userDetails.getCompany_id());
					if(fuel != null && !fuel.isEmpty()) {
						for(Fuel newFuelDetails : fuel) {
							newVehicleFuelEntry = new ValueObject();
							newVehicleFuelEntry.put("fuel", newFuelDetails);
							plObject =	fuelService.saveVehicleProfitAndLossTxnChecker(newVehicleFuelEntry);
						}
					}
					
					urea = ureaEntriesRepository.getUreaEntryDeatilsByTripsheetId(Sheet.getTripSheetID(), userDetails.getCompany_id());
					if(urea != null && !urea.isEmpty()) {
						for(UreaEntries ureaDetails : urea) {
							ureaEntriesService.deletePreviousVehicleUreaEntries(ureaDetails);
						}
					}
					
					ureaEntriesService.updateVidOfUreaEntries(Sheet.getTripSheetID(), Sheet.getVid(), userDetails.getCompany_id());
					
					if(urea != null && !urea.isEmpty()) {
						for(UreaEntries ureaDetails : urea) {
							newVehicleUreaEntry = new ValueObject();
							newVehicleUreaEntry.put("ureaEntries", ureaDetails);
							ureaEntriesService.saveVehicleProfitAndLossTxnCheckerForUrea(newVehicleUreaEntry);
						}
					}
					
					if (Sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
						
							tripSheetIncome = TripSheetIncomeRepository.findAllTripSheetIncome(Sheet.getTripSheetID(), userDetails.getCompany_id());
							if(tripSheetIncome != null && !tripSheetIncome.isEmpty()) {
								for(TripSheetIncome tripSheetIncm : tripSheetIncome) {
									tripSheetService.deletePreviousVehicleTripSheetIncome(tripSheetIncm, oldVehicleId);
								}
							}
							
							if(tripSheetIncome != null && !tripSheetIncome.isEmpty()) {
								for(TripSheetIncome tripSheetIncm : tripSheetIncome) {
									tripSheetIncomeProfitLoss = new ValueObject();
									tripSheetIncomeProfitLoss.put("TripSheetIncm", tripSheetIncm);
									tripSheetIncomeProfitLoss.put("TripSheet", Sheet);
									tripSheetService.saveVehicleProfitAndLossTxnCheckerForTripSheetIncome(tripSheetIncomeProfitLoss);
								}
							}
							
							tripSheetExpense = TripSheetExpenseRepository.findAllTripSheetExpense(Sheet.getTripSheetID(), userDetails.getCompany_id());
							if(tripSheetExpense != null && !tripSheetExpense.isEmpty()) {
								for(TripSheetExpense tripSheetExpn : tripSheetExpense) {
									tripSheetService.deletePreviousVehicleTripSheetExpense(tripSheetExpn, oldVehicleId);
								}
							}
							
							if(tripSheetExpense != null && !tripSheetExpense.isEmpty()) {
								for(TripSheetExpense tripSheetExpn : tripSheetExpense) {
									tripSheetExpenseProfitLoss = new ValueObject();
									tripSheetExpenseProfitLoss.put("TripSheetExpn", tripSheetExpn);
									tripSheetExpenseProfitLoss.put("TripSheet", Sheet);
									tripSheetService.saveVehicleProfitAndLossTxnCheckerForTripSheetExpense(tripSheetExpenseProfitLoss);
								}
							}
							
							if(plObject != null && plObject.containsKey(VehicleProfitAndLossDto.TXN_CHECKER_ID)) {
								ValueObject		valueObject = plObject;
								new Thread() {
									public void run() {
										try {
											vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueObject);
											vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueObject);
											vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueObject);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}		
								}.start();
							}
						
					}
			  }	
								
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails						= null;
			oldVehicleId					= 0;
			fuel							= null;
			urea							= null;
			tripSheetIncome					= null;
			tripSheetExpense				= null;
			Sheet							= null;
			SheetOLDData					= null;
			newVehicleFuelEntry				= null;
			newVehicleUreaEntry				= null;
			tripSheetIncomeProfitLoss		= null;
			tripSheetExpenseProfitLoss		= null;
		}
	}
	
	@Override
	public ValueObject addTripSheetCloseDetails(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		String						email								= null;
		CustomUserDetails			userDetails							= null;
		boolean						showTimeDuringCloseTripsheet		= false;
		HashMap<String, Object> 	configuration						= null;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			email					= object.getString("email");
			
			userDetails				= new CustomUserDetails();
			userDetails.setCompany_id(companyId);
			userDetails.setId(userId);
			userDetails.setEmail(email);
			
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			showTimeDuringCloseTripsheet = (boolean) configuration.get(TripSheetConfigurationConstant.SHOW_TIME_DURING_CLOSE_TRIPSHEET);
			object.put("ShowTime", showTimeDuringCloseTripsheet);
			
			HashMap<String, Object> vehicleonfig = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			HashMap<String, Object> gpsConfig	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			TripSheetDto trip = tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
			object.put("TripSheet", trip);
			
			TripSheetDto finalTripDto = tripSheetBL.GetTripSheetDetails(trip);
			object.put("finalTripDto", finalTripDto);
			
			object.put("vehicleonfig", vehicleonfig);
			object.put("companyId", userDetails.getCompany_id());
			object.put("allowedKMForExtendedTrip", (int)configuration.getOrDefault("allowedKMForExtendedTrip", 2000));
			
			ObjectMapper objectMapper = new ObjectMapper();
			object.put("allowGPSIntegration", (boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION));
			object.put("gpsConfig", objectMapper.writeValueAsString(gpsConfig));

			object.put("TripSheetExtraOptions",
					tripSheetService.findAllTripSheetExtraOptions(tripsheetId, userDetails.getCompany_id()));
			object.put("configuration", configuration);
			object.put("driverBalanceWithNarration",(boolean) configuration.get("driverBalanceWithNarration"));
			
			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(trip.getVid(), companyId);
			
			ValueObject	gpsObject	= null;
			if((boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
				ValueObject	gps	= new ValueObject();
				gps.put("companyId", userDetails.getCompany_id());
				gps.put("vehicleId", trip.getVid());
				gps.put("fromDate", DateTimeUtility.getTimeStampFromDate(trip.getDispatchedByTimeOn()));
				gps.put("toDate", DateTimeUtility.getCurrentTimeStamp());
				gps.put("fromTripSheetClose", true);
				gps.put("openKM", trip.getTripOpeningKM());
				gps.put("openGPSKM", finalTripDto.getTripGpsOpeningKM());
				
				gpsObject	=	vehicleGPSDetailsService.getVehicleGPSData(gps);
				if(gpsObject != null) {
					
								
					object.put("gpsObject", objectMapper.writeValueAsString(gpsObject.getHtData()));
					
					if(gpsObject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME)) {
						object.put("GPSKM", gpsObject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME));
					}else {
						object.put("GPSKM", CheckVehicleStatus.getVehicle_Odometer());
					}
				}
			}
			if (CheckVehicleStatus != null) {
				object.put("ExpectedOdameter", CheckVehicleStatus.getVehicle_ExpectedOdameter());
				object.put("ExpectedOdameterKm", CheckVehicleStatus.getVehicleExpectedKm());
				if((boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION) && (int)gpsConfig.get(VehicleGPSConfiguratinConstant.GPS_FLAVOR) == 2) {
					if(gpsObject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME) && gpsObject.getBoolean(GPSConstant.VEHICLE_GPS_WORKING, false)) {
						Double gpsKM = gpsObject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME);
						object.put("TripSheetKm", gpsKM.intValue());
					}else {
						if((boolean)configuration.get(TripSheetConfigurationConstant.SHOW_CLOSINGKM_ON_ROUTE) && finalTripDto.getRouteApproximateKM() != null) {
							object.put("TripSheetKm", finalTripDto.getTripOpeningKM() + finalTripDto.getRouteApproximateKM());
						}else {
							object.put("TripSheetKm", finalTripDto.getTripOpeningKM());
						}
					}
					
				}else {
					if((boolean)configuration.get(TripSheetConfigurationConstant.SHOW_CLOSINGKM_ON_ROUTE) && finalTripDto.getRouteApproximateKM() != null) {
						object.put("TripSheetKm", finalTripDto.getTripOpeningKM() + finalTripDto.getRouteApproximateKM());
					}else {
						object.put("TripSheetKm", finalTripDto.getTripOpeningKM());
					}
				}
				if((boolean)configuration.get(TripSheetConfigurationConstant.SHOW_CLOSINGKM_ON_ROUTE) && finalTripDto.getRouteApproximateKM() != null) {
					int maxAllowedKM = (((int) configuration.getOrDefault("allowedPerDiffInOdometer", 10) * finalTripDto.getRouteApproximateKM())/100);
					object.put("maxAllowedKM", maxAllowedKM + finalTripDto.getTripOpeningKM() + finalTripDto.getRouteApproximateKM());
					object.put("minAllowedKM", finalTripDto.getTripOpeningKM() + finalTripDto.getRouteApproximateKM() - maxAllowedKM);
				}else {
					object.put("maxAllowedKM", CheckVehicleStatus.getVehicleExpectedKm() + finalTripDto.getTripOpeningKM());
					object.put("minAllowedKM", finalTripDto.getTripOpeningKM() + 1);
					
				}
			}
			
			if (trip.getTripOpenDate() != null) {
				object.put("StartDate", trip.getTripOpenDate());
			}
			if (trip.getClosetripDate() != null) {
				object.put("EndDate", trip.getClosetripDate());
			}
			
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalAdvance = trip.getTripTotalAdvance();
			if (trip.getTripTotalAdvance() == null) {
				TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			object.put("advanceTotal", df.format(TotalAdvance));

			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			object.put("expenseTotal", df.format(TotalExpense));

			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double Totalincome = trip.getTripTotalincome();
			if (trip.getTripTotalincome() == null) {
				Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			object.put("incomeTotal", df.format(Totalincome));
			
			
			driverHaltAndRoutePointDuringCloseTripsheet(object);
			
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public void driverHaltAndRoutePointDuringCloseTripsheet(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		String						email								= null;
		CustomUserDetails			userDetails							= null;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			email					= object.getString("email");
			
			userDetails				= new CustomUserDetails();
			userDetails.setCompany_id(companyId);
			userDetails.setId(userId);
			userDetails.setEmail(email);
			
			TripSheetDto 	trip 	= (TripSheetDto)object.get("TripSheet");
			
			Double RouteAttendancePoint = TRIP_SHEET_DEFALAT_AMOUNT, DriverTotalPoint = TRIP_SHEET_DEFALAT_AMOUNT,
					DriverSECTotalPoint = TRIP_SHEET_DEFALAT_AMOUNT, ClennerTotalPoint = TRIP_SHEET_DEFALAT_AMOUNT,
					FDriverHaltPoint = TRIP_SHEET_DEFALAT_AMOUNT, SDriverHaltPoint = TRIP_SHEET_DEFALAT_AMOUNT,
					ClennerHaltPoint = TRIP_SHEET_DEFALAT_AMOUNT;
			// Find TripSheet Id to get Route Attendance value point and display
			if (trip.getRouteID() != TRIP_SHEET_DEFALAT_ZERO) {
				TripRoute TripRoute = tripRouteService.getTriproutePoint(trip.getRouteID(), companyId);
				if (TripRoute.getRoutrAttendance() != null) {
					RouteAttendancePoint += TripRoute.getRoutrAttendance();
				}
			}
			
			List<DriverHaltDto> DriverHalt = driverHaltService.Find_list_TripSheet_DriverHalt(tripsheetId, userDetails.getCompany_id());
			if (DriverHalt != null && !DriverHalt.isEmpty()) {
				for (DriverHaltDto dHalt : DriverHalt) {

					if (dHalt.getDRIVERID() == trip.getTripFristDriverID()) {
						FDriverHaltPoint += dHalt.getHALT_POINT();
					} else if (dHalt.getDRIVERID() == trip.getTripSecDriverID()) {
						SDriverHaltPoint += dHalt.getHALT_POINT();
					} else if (dHalt.getDRIVERID() == trip.getTripCleanerID()) {
						ClennerHaltPoint += dHalt.getHALT_POINT();
					}
				}
			}
			
			List<DriverTripSheetPointDto> DisplayPoint = new ArrayList<DriverTripSheetPointDto>();
			// frist Driver Total
			if (trip.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				DriverTripSheetPointDto Dto = new DriverTripSheetPointDto();
				Dto.setDRIVERID(trip.getTripFristDriverID());
				Dto.setDRIVER_NAME(trip.getTripFristDriverName());
				Dto.setTRIP_ROUTE_POINT(RouteAttendancePoint);
				Dto.setHALT_POINT(FDriverHaltPoint);
				DriverTotalPoint += RouteAttendancePoint + FDriverHaltPoint;
				Dto.setPOINT_TOTAL(DriverTotalPoint);
				DisplayPoint.add(Dto);
			}
			
			// second Driver Total
			if (trip.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				DriverTripSheetPointDto Dto = new DriverTripSheetPointDto();
				Dto.setDRIVERID(trip.getTripSecDriverID());
				Dto.setDRIVER_NAME(trip.getTripSecDriverName());
				Dto.setTRIP_ROUTE_POINT(RouteAttendancePoint);
				Dto.setHALT_POINT(SDriverHaltPoint);
				DriverSECTotalPoint += RouteAttendancePoint + SDriverHaltPoint;
				Dto.setPOINT_TOTAL(DriverSECTotalPoint);
				DisplayPoint.add(Dto);
			}
			
			// Clenner Driver Total
			if (trip.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
				DriverTripSheetPointDto Dto = new DriverTripSheetPointDto();
				Dto.setDRIVERID(trip.getTripCleanerID());
				Dto.setDRIVER_NAME(trip.getTripCleanerName());
				Dto.setTRIP_ROUTE_POINT(RouteAttendancePoint);
				Dto.setHALT_POINT(ClennerHaltPoint);
				ClennerTotalPoint += RouteAttendancePoint + ClennerHaltPoint;
				Dto.setPOINT_TOTAL(ClennerTotalPoint);
				DisplayPoint.add(Dto);
			}

			object.put("DisplayPoint", DisplayPoint);
						
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject saveTripSheetCloseDetails(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		HashMap<String, Object> 	gpsConfig							= null;
		Integer						ClosingKM 							= TRIP_SHEET_DEFALAT_ZERO;
		Integer 					UsageKM 							= TRIP_SHEET_DEFALAT_ZERO;
		Double						gpsUssageKM 						= 0.0;
		Timestamp 					CloseByTime 						= null;
		HashMap<String, Object> 	configuration						= null;
		HashMap<String, Object> 	companyConfiguration				= null;
		HashMap<String, Object> 	CashBookconfig						= null;
		
		try {
			if(object.getString("tripClosingDate") == null || object.getString("tripClosingDate").trim() == "") {
				object.put("closeDateNotFound", true);
				return object;
			}
				
			if(object.getBoolean("validateTripCloseTimeAsCurrentTime",false) &&  DateTimeUtility.checkDateAfterDate(DateTimeUtility.getTimeStampFromDateTimeString(object.getString("closeDateTime")),DateTimeUtility.getCurrentTimeStamp())) {
				object.put("closeDateTimeAfterCurrentDateTime", true);
				return object;
			}
				
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			//driverAdvance			= object.getDouble("driverAdvance");
			//driverExpense			= object.getDouble("driverExpense");
			
			//driverBalance			= driverAdvance - driverExpense - object.getDouble("closeTripAmount",0);
			//System.err.println("driverBalance "+driverBalance);
						
			//userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userDetails                 =  new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			gpsConfig 		 		 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			configuration	 		 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			companyConfiguration 	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);;
			CashBookconfig	 		 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
			
			java.util.Date date 	 = dateFormat.parse(object.getString("tripClosingDate"));
			java.sql.Date Close_date = new java.sql.Date(date.getTime());
			short ClosingKMStatus    = object.getShort("tripClosingKMStatusId");
			object.put("Close_date", Close_date);
		
			TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(tripsheetId);
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
			
			TripSheetDto trip = tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
			if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
				
				object.put("TripSheet", trip);
				object.put("configuration", configuration);
				object.put("tripOpenKm", trip.getTripOpeningKM());
				
				VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(trip.getVid(), companyId);
				object.put("CheckVehicleStatus", CheckVehicleStatus);
				
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp      LASTUPDATED_DATE  = new java.sql.Timestamp(currentDateUpdate.getTime());
				
				String start_time = "00:00";
				if(object.getString("tripCloseTime") != null && object.getString("tripCloseTime") != "") {
					start_time				 = object.getString("tripCloseTime");
					java.util.Date closed    = DateTimeUtility.getDateTimeFromDateTimeString(dateFormat.format(date), start_time);
					CloseByTime 			 = new java.sql.Timestamp(closed.getTime());
					object.put("closed", closed);
				}else {
					CloseByTime 			 = DateTimeUtility.getCurrentTimeStamp();
					object.put("closed", CloseByTime);
				}
				object.put("CloseByTime", CloseByTime);
				
				if((boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
					if(trip.getTripGpsOpeningKM() != null && trip.getTripGpsOpeningKM() > 0 && object.getString("tripGPSClosingKM") != null && object.getDouble("tripGPSClosingKM", 0) > 0) {
						gpsUssageKM	= object.getDouble("tripGPSClosingKM", 0) - trip.getTripGpsOpeningKM();
					}else {
						try {
							gpsUssageKM = vehicleGPSDetailsService.getGPSRunKMBetweenTwoDates(trip.getVid(), trip.getDispatchedByTimeOn()+"", CloseByTime+"", companyId);
						} catch (Exception e) {
							System.err.println("Exception while getting gps run km "+e);
						}
						
					}
					if(object.getInt("tripClosingKM", 0) > 0) {
						ClosingKM = object.getInt("tripClosingKM");
						UsageKM = ClosingKM - trip.getTripOpeningKM();
					}else {
						UsageKM = gpsUssageKM.intValue();
					}
				}else {
					ClosingKM = object.getInt("tripClosingKM", 0);
					UsageKM = ClosingKM - trip.getTripOpeningKM();
				}
				
				if (CheckVehicleStatus.getVehicleExpectedKm() == null || CheckVehicleStatus.getVehicleExpectedKm() == 0
						|| UsageKM > 0  || object.getBoolean("meterNotWorking", false)
						|| (boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
					
					java.util.Date currentDate = new java.util.Date();
					
					if (removeTime(Close_date).equals(removeTime(currentDate))
							|| removeTime(Close_date).before(removeTime(currentDate)) || (boolean)configuration.get("allowToCloseTripSheetBeforeDate")) {
						
						apiCallOnCloseTripSheet(object);
						
						Integer	 closingKMToUpdate	= ClosingKM;
						if((boolean) configuration.get("updateOpeningClosingOnBackEntry")) {
							List<TripSheetDto>	sheetDtoList	=	tripSheetService.getNextTripSheetsByVidDate(trip.getVid(), trip.getDispatchedByTimeOn()+"");
							Integer  openingKMToUpdate  = 0;
							if(sheetDtoList != null && !sheetDtoList.isEmpty()) {
								for (TripSheetDto tripSheetDto : sheetDtoList) {
									openingKMToUpdate	= closingKMToUpdate;
									closingKMToUpdate	= tripSheetDto.getTripUsageKM() + openingKMToUpdate;
									tripSheetService.updateOpeningClosingKM(tripSheetDto.getTripSheetID(), openingKMToUpdate, closingKMToUpdate);
								}
								
							}
						}
						object.put("vid", trip.getVid());
						tyreUsageHistoryService.saveTripTyreUsageHistory(object);
						addExpenseAndIncomeProfitAndLoss(object);
						
						UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
						
						tripSheetService.updateCloseTripSheet(trip.getTripSheetID(), ClosingKM, UsageKM,
								ClosingKMStatus, object.getShort("closeTripStatusId"), object.getString("closeTripRefNo"),
								object.getDouble("closeTripAmount",0), object.getLong("closeTripNameById",0), Close_date,
								userDetails.getId(), LASTUPDATED_DATE, userDetails.getId(), Orderingname.getBranch_id(),
								CloseByTime, companyId, object.getDouble("tripGPSClosingKM",0), 
								object.getString("gpsCloseLocation"), gpsUssageKM, object.getDouble("driverBalance", 0));

						if (object.getDouble("closeTripAmount", 0) > 0 && (boolean) configuration.getOrDefault("saveDriverLedgerDetails", false)) {

							ValueObject driveLedgerObj = new ValueObject();

							driveLedgerObj.put("companyId", companyId);
							driveLedgerObj.put("userId", userId);
							driveLedgerObj.put("driverId", trip.getTripFristDriverID());
							driveLedgerObj.put("amount", object.getDouble("closeTripAmount", 0));
							driveLedgerObj.put("transactionId", trip.getTripSheetID());
							if(date != null)
							driveLedgerObj.put("txnTime", date);
							if (object.getShort("closeTripStatusId") == 1) {
								driveLedgerObj.put("description",
										" Amount paid to Office By driver : " + object.getDouble("closeTripAmount", 0)
												+ " For TripSheet Number : " + trip.getTripSheetNumber());
								driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_CLOSE_PAID_BY_DRIVER);
							} else {
								driveLedgerObj.put("description",
										" Amount paid to Driver By Office : " + object.getDouble("closeTripAmount", 0)
												+ " For TripSheet Number : " + trip.getTripSheetNumber());
								driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_CLOSE_PAID_TO_DRIVER);
							}

							driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
						}
						/*
						if(object.getDouble("closeTripAmount", 0) > 0 && (boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)){
							ValueObject bankPaymentValueObject=new ValueObject();
							bankPaymentValueObject.put("currentPaymentTypeId",PaymentTypeConstant.PAYMENT_TYPE_CASH);
							bankPaymentValueObject.put("userId", userDetails.getId());
							bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
							bankPaymentValueObject.put("moduleId",trip.getTripSheetID());
							bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.TRIP_CLOSE);
							bankPaymentValueObject.put("moduleNo", trip.getTripSheetNumber());
							bankPaymentValueObject.put("amount",object.getDouble("closeTripAmount", 0));
							bankPaymentValueObject.put("paidDate", date);
							bankPaymentValueObject.put("remark", "Driver : "+trip.getTripFristDriverName() +", Vehicle : "+trip.getVehicle_registration());
							if (object.getShort("closeTripStatusId") == 1) 
								bankPaymentValueObject.put("fromIncome", true);
							
							cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
						}*/

						if((boolean) configuration.get("tripOpenCloseFuelRequired") && object.getDouble("tripEndDiesel",0) > 0) {
							tripSheetService.updateBalanceFuel(trip.getTripSheetID(), object.getDouble("tripEndDiesel",0));
						}
						
						object.put("closeTripSheet", true);
						
						if(!(boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION) || (int)gpsConfig.get(VehicleGPSConfiguratinConstant.GPS_FLAVOR) != GPSConstant.VEHICLE_GPS_FLAVOR_GPS_ONLY) {
							Update_Current_OdometerinVehicle_getTripDailySheetTo_DTO(trip, closingKMToUpdate, userDetails, CheckVehicleStatus.getVehicle_Odometer());
						}else {
							Update_Current_OdometerinVehicle_GpsUsage(trip, gpsUssageKM, userDetails);
						}
						
						VehicleAccidentDetails	details = accidentDetailsService.getVehicleAccidentDetailsByTripSheetId(trip.getTripSheetID());
						
						if(details == null || !details.getVid().equals(trip.getVid())  ) {
							vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, trip.getVid(),
									companyId, VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
						}
						
						updateDriverAttendanceAndRoutePoint(object);
						
						updateDriverHaltDetails(object);
						
						if(trip.getTripsheetextraname() != null || trip.getTripSheetExtraQuantityReceived() != null 
								|| trip.getTripSheetExtraDescription() != null) {
							
							updateTripExtraReceivedDetails(object);
						}
						
						updateDriverSalary(object);
						
						runThreadForVehicleProfitAndLoss(object);
						
				    TripSheetDto updatedtrip = tripSheetService.getTripSheetDetails(tripsheetId,userDetails);
					
					if((boolean) configuration.get("pushTripsheetStatusToIvCargo")) {
						sendDataToCargo(updatedtrip);
					}
						
						if((boolean) companyConfiguration.get("allowBankPaymentDetails") && (boolean) companyConfiguration.get("sendExpenseToIvCargo") ) {			
							sendExpenseDataToCargo(object);		
						}						
					}else {
						object.put("closeDateNotMatching", true);
					}
					
				}else {
					object.put("checkOdometer", true);
				}
			}else {
				object.put("alreadyClosed", true);
			}
			
			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}finally {
			companyId							= 0;
			userId								= 0;
			tripsheetId							= 0;
			userDetails							= null;
			gpsConfig							= null;
			ClosingKM 							= 0;
			UsageKM 							= null;
			gpsUssageKM 						= 0.0;
			CloseByTime 						= null;
			configuration						= null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void apiCallOnCloseTripSheet(ValueObject object) throws Exception {
		HashMap<String, Object> 	configuration						= null;
		HashMap<String, Object> 	tollConfig							= null;
		ValueObject					valueTollObject						= null;
		VehicleExtraDetails 		busDeatils							= null;
		try {
			long 					tripsheetId 		= object.getLong("tripsheetId");
			CustomUserDetails 		userDetails 		= (CustomUserDetails)object.get("userDetails");
			TripSheetDto 			trip 				= (TripSheetDto)object.get("TripSheet");
			VehicleDto 				CheckVehicleStatus 	= (VehicleDto)object.get("CheckVehicleStatus");
			Timestamp 				CloseByTime 		= (Timestamp)object.get("CloseByTime");
			 
			tollConfig	  = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TOLL_API_CONFIG);
			configuration = (HashMap<String, Object>) object.get("configuration");
			
			if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_ITS_GATEWAY_BUS_INCOME)) {
				 busDeatils = vehicleExtraDetailsRepository.getBusId(trip.getVid(), userDetails.getCompany_id());
				 
				 if(busDeatils == null) {
						System.err.println("Bus Id Not Configured.");
				 }
			}
			
			if((boolean) tollConfig.get(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION) 
					|| (boolean) tollConfig.get(TollApiConfiguration.ALLOW_KVB_BANK_TOLL_API)
					|| (boolean) tollConfig.get(TollApiConfiguration.ALLOW_KVB_BANK_TOLL_API)
					|| (boolean) tollConfig.get(TollApiConfiguration.ALLOW_YES_BANK_FASTTAG)
					|| (boolean) tollConfig.get(TollApiConfiguration.ALLOW_KARINS_FASTTAG)) {
				
				valueTollObject	= new ValueObject();
				valueTollObject.put("StartTransactionDate", trip.getDispatchedByTimeOn()+"");
				valueTollObject.put("EndTransactionDate", CloseByTime+"");
				valueTollObject.put("VehicleNumber", CheckVehicleStatus.getVehicle_registration().replaceAll("-", ""));
				valueTollObject.put("tripSheetId", tripsheetId);
				valueTollObject.put("vid", CheckVehicleStatus.getVid());
				valueTollObject.put("vehicle_registration", CheckVehicleStatus.getVehicle_registration());
				valueTollObject.put("userDetails", userDetails);
				valueTollObject.put("tollid", CheckVehicleStatus.getVehicleTollId());
				
				valueTollObject	=	tollExpensesDetailsService.addVehicleTripTollExpenses(valueTollObject);
			}
			
			object.put("valueTollObject", valueTollObject);
			
			if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_AUTO_LS_ON_TRIP_CLOSE) && (boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_IV_LOADING_SHEET_ENTRY)) {
				System.err.println("inside if");
				ValueObject		valueObject	= new ValueObject();
				TripSheetDto	lastTrip	= tripSheetService.getLastTripSheetLimitedData(CheckVehicleStatus.getVid(), CloseByTime+"", tripsheetId);
				
				valueObject.put("fromDate", trip.getDispatchedByTimeOn()+"");
				
				if((boolean) configuration.get("substractTimeFromDate")) {
					valueObject.put("fromDate", DateTimeUtility.substractTimeFromDate(trip.getDispatchedByTimeOn()+"", DateTimeUtility.YYYY_MM_DD_HH_MM_SS, (int) configuration.get("noOfHourToSubstract")));
				}
				
				if(lastTrip != null && DateTimeUtility.isSameDate(lastTrip.getClosetripDateOn(), trip.getTripOpenDateOn())) {
					valueObject.put("fromDate", lastTrip.getClosedByTime());
				}
				valueObject.put("toDate", CloseByTime+"");
				valueObject.put("vehicleNumber", CheckVehicleStatus.getVehicle_registration());
				valueObject.put("tripSheetId", tripsheetId);
				valueObject.put("vid", CheckVehicleStatus.getVid());
				valueObject.put("vehicle_registration", CheckVehicleStatus.getVehicle_registration());
				valueObject.put("userDetails", userDetails);
				valueObject.put("fromTripSheetClose", true);
				
				tripSheetService.getIVLoadingSheetDataForTrip(valueObject);
			}
			
			if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_AUTO_INCOME_ON_TRIP_CLOSE) && (boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_TKT_INCOME_API_INTEGERATION)) {
				ValueObject		valueTktIncmObject	= new ValueObject();
				valueTktIncmObject.put("FromDate", trip.getDispatchedByTimeOn()+"");
				valueTktIncmObject.put("ToDate", CloseByTime+"");
				valueTktIncmObject.put("startDate", trip.getDispatchedByTimeOn());
				valueTktIncmObject.put("endDate", CloseByTime);
				valueTktIncmObject.put("tripSheetId", tripsheetId);
				valueTktIncmObject.put("vid", trip.getVid());
				valueTktIncmObject.put("BusNumber", trip.getVehicle_registration());
				valueTktIncmObject.put("companyId", userDetails.getCompany_id());
				valueTktIncmObject.put("userId", userDetails.getId());
				
				ticketIncomeApiService.addVehicleTicketIncome(valueTktIncmObject);
				
			}
			
			if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_ITS_GATEWAY_BUS_INCOME) && busDeatils != null) {
				ValueObject		valueTktIncmObject	= new ValueObject();
				
				
				valueTktIncmObject.put("FromDate", trip.getDispatchedByTimeOn()+"");
				valueTktIncmObject.put("ToDate", DateTimeUtility.getDateFromTimeStamp(CloseByTime, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
				valueTktIncmObject.put("tripSheetId", trip.getTripSheetID());
				valueTktIncmObject.put("vid", trip.getVid());
				valueTktIncmObject.put("BusID", busDeatils.getBusId());
				valueTktIncmObject.put("companyId", userDetails.getCompany_id());
				valueTktIncmObject.put("userId", userDetails.getId());
				valueTktIncmObject.put("configuration", configuration);
				
				ticketIncomeApiService.addITSGatewayBusIncome(valueTktIncmObject);
			} 
			
			if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_BITLA_API_INCOME)) {
				ValueObject		valueTktIncmObject	= new ValueObject();
				valueTktIncmObject.put("FromDate", trip.getDispatchedByTimeOn()+"");
				valueTktIncmObject.put("ToDate", CloseByTime+"");
				valueTktIncmObject.put("tripSheetId", tripsheetId);
				valueTktIncmObject.put("vid", trip.getVid());
				valueTktIncmObject.put("BusNumber", trip.getVehicle_registration());
				valueTktIncmObject.put("companyId", userDetails.getCompany_id());
				valueTktIncmObject.put("userId", userDetails.getId());
				valueTktIncmObject.put("configuration", configuration);
				
				ticketIncomeApiService.addBitlaTicketIncome(valueTktIncmObject);
				
			}
			
			if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_MANTIS_DISPATCH_INCOME)) {
				ValueObject		valueTktIncmObject	= new ValueObject();
				valueTktIncmObject.put("FromDate", trip.getDispatchedByTimeOn()+"");
				valueTktIncmObject.put("ToDate", CloseByTime+"");
				valueTktIncmObject.put("tripSheetId", tripsheetId);
				valueTktIncmObject.put("vid", trip.getVid());
				valueTktIncmObject.put("BusNumber", trip.getVehicle_registration());
				valueTktIncmObject.put("companyId", userDetails.getCompany_id());
				valueTktIncmObject.put("userId", userDetails.getId());
				valueTktIncmObject.put("configuration", configuration);
				
				ticketIncomeApiService.addMantisDispatchIncome(valueTktIncmObject);
				
			}
			
			
			object.put("configuration", configuration);
			
			} catch (Exception e) {
				throw e;
			}finally {
				configuration						= null;
				tollConfig							= null;
				valueTollObject						= null;
				busDeatils							= null;
			}
		}
	
	public void addExpenseAndIncomeProfitAndLoss(ValueObject object) throws Exception {
		List<TripSheetExpenseDto> 								tripSheetExpenseList   			= null;
		HashMap<Long, VehicleProfitAndLossTxnChecker>			expenseTxnCheckerHashMap		= null;
		HashMap<Long, TripSheetExpenseDto> 						tripSheetExpenseHM				= null;
		List<TripSheetIncomeDto>								tripSheetIncomeList				= null;
		HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>	    incomeTxnCheckerHashMap			= null;
		HashMap<Long, TripSheetIncomeDto> 						tripSheetIncomeDtoHM			= null;
		try {
			expenseTxnCheckerHashMap	= new HashMap<>();
			tripSheetExpenseHM			= new HashMap<>();
			incomeTxnCheckerHashMap		= new HashMap<>();
			tripSheetIncomeDtoHM		= new HashMap<>();
			
			long 				tripsheetId 		= object.getLong("tripsheetId");
			CustomUserDetails 	userDetails 		= (CustomUserDetails)object.get("userDetails");
			TripSheetDto 		trip 				= (TripSheetDto)object.get("TripSheet");
			
			tripSheetExpenseList = tripSheetService.findAllTripSheetExpense(tripsheetId, userDetails.getCompany_id());
			if(tripSheetExpenseList != null) {
				ValueObject		expenseObject	=	null;
				for(TripSheetExpenseDto	expenseDto : tripSheetExpenseList) {
					if(expenseDto.getExpenseAmount() > 0.0 && (expenseDto.getFuel_id() == null || expenseDto.getFuel_id() == 0) ) {

						expenseObject	= new ValueObject();
						
						expenseObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, tripsheetId);
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						expenseObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, trip.getVid());
						expenseObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, expenseDto.getExpenseId());
						expenseObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, expenseDto.getTripExpenseID());

						VehicleProfitAndLossTxnChecker profitAndLossExpenseTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(expenseObject);
						vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);

						expenseTxnCheckerHashMap.put(profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossExpenseTxnChecker);
						tripSheetExpenseHM.put(expenseDto.getTripExpenseID(), expenseDto);
					}
				}
			}
			
			object.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);
			object.put("tripSheetExpenseHM", tripSheetExpenseHM);
			
			tripSheetIncomeList	= tripSheetService.findAllTripSheetIncome(tripsheetId, userDetails.getCompany_id());
			if(tripSheetIncomeList != null) {
				for(TripSheetIncomeDto	incomeDto : tripSheetIncomeList) {
					if(incomeDto.getIncomeAmount() > 0.0) {

						ValueObject		incomeObject	= new ValueObject();
						
						incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, tripsheetId);
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, trip.getVid());
						incomeObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, incomeDto.getIncomeId());
						incomeObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, incomeDto.getTripincomeID());

						VehicleProfitAndLossIncomeTxnChecker profitAndLossIncomeTxnChecker	= txnCheckerBL.getVehicleProfitAndLossIncomeTxnChecker(incomeObject);
						vehicleProfitAndLossIncomeTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);

						incomeTxnCheckerHashMap.put(profitAndLossIncomeTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossIncomeTxnChecker);
						tripSheetIncomeDtoHM.put(incomeDto.getTripincomeID(), incomeDto);
					}
				}

			}
			
			object.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap);
			object.put("tripSheetIncomeDtoHM", tripSheetIncomeDtoHM);
	
		} catch (Exception e) {
			throw e;
		}finally {
			tripSheetExpenseList   			= null;
			expenseTxnCheckerHashMap		= null;
			tripSheetExpenseHM				= null;
			tripSheetIncomeList				= null;
			incomeTxnCheckerHashMap			= null;
			tripSheetIncomeDtoHM			= null;
		}
	}
	
	public void Update_Current_OdometerinVehicle_getTripDailySheetTo_DTO(TripSheetDto Sheet, Integer ClosingKM, CustomUserDetails userDetails, Integer currentODDMETER) {
		List<ServiceReminderDto>		serviceList					= null;
		try {
			
			if (Sheet.getVid() != TRIP_SHEET_DEFALAT_ZERO) {
				if (currentODDMETER < ClosingKM) {
					
					vehicleService.updateCurrentOdoMeter(Sheet.getVid(), ClosingKM, userDetails.getCompany_id());
					// update current Odometer update Service Reminder
					serviceReminderService.updateCurrentOdometerToServiceReminder(Sheet.getVid(),
							ClosingKM, userDetails.getCompany_id());
					
					serviceList = serviceReminderService.OnlyVehicleServiceReminderList(Sheet.getVid(), userDetails.getCompany_id(), userDetails.getId());
					if(serviceList != null) {
						for(ServiceReminderDto list : serviceList) {
							
							if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
								serviceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
								//emailAlertQueueService.sendEmailServiceOdometer(list);
								//smsAlertQueueService.sendSmsServiceOdometer(list);
							}
						}
					}

					VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL.prepareOdometerGetHistoryToTripsheet(Sheet);
					vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
					vehicleUpdateHistory.setVehicle_Odometer(ClosingKM);
					vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void Update_Current_OdometerinVehicle_GpsUsage(TripSheetDto Sheet, Double ClosingKM, CustomUserDetails userDetails) {
		List<ServiceReminderDto>		serviceList					= null;
		Integer 						currentODDMETER;
		try {
			currentODDMETER = vehicleService.updateCurrentOdoMeterGetVehicleCurrentOdometer(Sheet.getVid(), userDetails.getCompany_id());

			if (Sheet.getVid() != TRIP_SHEET_DEFALAT_ZERO) {
				if (ClosingKM != null && ClosingKM > 0) {
					
					vehicleService.updateCurrentOdoMeter(Sheet.getVid(), (ClosingKM.intValue() + currentODDMETER), userDetails.getCompany_id());
					// update current Odometer update Service Reminder
					serviceReminderService.updateCurrentOdometerToServiceReminder(Sheet.getVid(),
							(ClosingKM.intValue() + currentODDMETER), userDetails.getCompany_id());
					
					serviceList = serviceReminderService.OnlyVehicleServiceReminderList(Sheet.getVid(), userDetails.getCompany_id(), userDetails.getId());
					if(serviceList != null) {
						for(ServiceReminderDto list : serviceList) {
							
							if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
								serviceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
								//emailAlertQueueService.sendEmailServiceOdometer(list);
								//smsAlertQueueService.sendSmsServiceOdometer(list);
							}
						}
					}

					VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL.prepareOdometerGetHistoryToTripsheet(Sheet);
					vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
					vehicleUpdateHistory.setVehicle_Odometer((ClosingKM.intValue() + currentODDMETER));
					vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void updateDriverAttendanceAndRoutePoint(ValueObject object) throws Exception {
		Double 				FristDriverRoutePoint 					= TRIP_SHEET_DEFALAT_AMOUNT;
		Double 				SecDriverRoutePoint 					= TRIP_SHEET_DEFALAT_AMOUNT;
		Double 				CleanerRoutePoint 						= TRIP_SHEET_DEFALAT_AMOUNT;
		try {
			CustomUserDetails 	userDetails 		= (CustomUserDetails)object.get("userDetails");
			TripSheetDto 		trip 				= (TripSheetDto)object.get("TripSheet");
			java.sql.Date 		Close_date			= (java.sql.Date) object.get("Close_date");
			
			ArrayList<ValueObject> 	dataObject		=  JsonConvertor.toValueObjectFromJsonString(object.getString("routePointList"));
			int[] 					ROUTE_DRIVERID  =  new int[dataObject.size()];
			double[] 				ROUTE_POINT     =  new double[dataObject.size()];
			
			for (int i =0; i<dataObject.size() ; i++) {
				ROUTE_DRIVERID[i] = dataObject.get(i).getInt("driverid"); 
				ROUTE_POINT[i]    = dataObject.get(i).getDouble("trip_ROUTE_POINT"); 
			}
			
			TripSheetDto sheet = tripSheetBL.Get_TripSheet_To_DriverAttendance(trip);
			object.put("sheet", sheet);
			
			
			if(object.getBoolean("isWebRequest",false)) {
				FristDriverRoutePoint 	=  object.getDouble("driver1RoutePoint",0);
				SecDriverRoutePoint		=  object.getDouble("driver2RoutePoint",0);
				CleanerRoutePoint 		=  object.getDouble("cleanerRoutePoint",0);
			}else {
				if (ROUTE_DRIVERID != null || ROUTE_POINT != null) {
					
					for (int i = TRIP_SHEET_DEFALAT_ZERO; i < ROUTE_DRIVERID.length; i++) {
						Integer DriverRouteID = ROUTE_DRIVERID[i];
						if (DriverRouteID.equals(trip.getTripFristDriverID())) {
							FristDriverRoutePoint += ROUTE_POINT[i];
						} else if (DriverRouteID.equals(trip.getTripSecDriverID())) {
							SecDriverRoutePoint += ROUTE_POINT[i];
						} else if (DriverRouteID.equals(trip.getTripCleanerID())) {
							CleanerRoutePoint += ROUTE_POINT[i];
						}
					} 
				}
			}
			
			if (trip.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				Add_TripSheet_TO_driver_Attendance(trip.getTripFristDriverID(),
						trip.getTripFristDriverName(), Close_date, FristDriverRoutePoint, sheet, userDetails);
				
				driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
						TRIP_SHEET_DEFALAT_VALUE, trip.getTripFristDriverID(), userDetails.getCompany_id());
				
				tripSheetService.updateFirstDriverRoutePoint(sheet.getTripSheetID(), FristDriverRoutePoint, userDetails.getCompany_id());
			}
			
			
			if (trip.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				Add_TripSheet_TO_driver_Attendance(trip.getTripSecDriverID(), trip.getTripSecDriverName(),
						Close_date, SecDriverRoutePoint, sheet, userDetails);
				
				driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
						TRIP_SHEET_DEFALAT_VALUE, trip.getTripSecDriverID(), userDetails.getCompany_id());
				
				tripSheetService.updateSecDriverRoutePoint(sheet.getTripSheetID(), SecDriverRoutePoint, userDetails.getCompany_id());
			}

			if (trip.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
				Add_TripSheet_TO_driver_Attendance(trip.getTripCleanerID(), trip.getTripCleanerName(),
						Close_date, CleanerRoutePoint, sheet, userDetails);

				driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
						TRIP_SHEET_DEFALAT_VALUE, trip.getTripCleanerID(), userDetails.getCompany_id());
				
				tripSheetService.updateCleanerRoutePoint(sheet.getTripSheetID(), CleanerRoutePoint, userDetails.getCompany_id());
			}
			
	
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void Add_TripSheet_TO_driver_Attendance(int trip_DRIVER_ID, String trip_DRIVER_NAME, Date Close_date,
			Double FristDriverRoutePoint, TripSheetDto trip, CustomUserDetails userDetails) {
		try {
			LocalDate start = LocalDate.parse(trip.getTripOpenDate()), end = LocalDate.parse(driverAttendanceFormat.format(Close_date));
			LocalDate next  = start.minusDays(1);
		
			while ((next = next.plusDays(1)).isBefore(end.plusDays(1))) {
					
				if (next != null) {
						DriverAttendance attendance = driverBL.Driver_Attendance_to_tripSheet(trip_DRIVER_ID, trip_DRIVER_NAME,
								trip, userDetails);
						java.util.Date attendanceDate = driverAttendanceFormat.parse("" + next);
						java.sql.Date ATTENDANCE_DATE = new Date(attendanceDate.getTime());
						attendance.setATTENDANCE_DATE(ATTENDANCE_DATE);
	
						if (ATTENDANCE_DATE.equals(Close_date)) {
							attendance.setPOINT_STATUS_ID(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT);
							attendance.setPOINT_TYPE_ID(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIP);
							attendance.setDRIVER_POINT(FristDriverRoutePoint);
						} else {
							attendance.setPOINT_STATUS_ID(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_NO);
							attendance.setDRIVER_POINT(TRIP_SHEET_DEFALAT_AMOUNT);
						}
						
						driverAttendanceService.addDriverAttendance(attendance);
					}
				
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void Add_TripSheet_TO_driver_Attendance_Edit(int trip_DRIVER_ID, String trip_DRIVER_NAME, Date Close_date,
			Double FristDriverRoutePoint, TripSheetDto trip, CustomUserDetails userDetails) {
		try {
			LocalDate start = LocalDate.parse(trip.getTripOpenDateStr()), end = LocalDate.parse(driverAttendanceFormat.format(Close_date));
			LocalDate next  = start.minusDays(1);
		
			while ((next = next.plusDays(1)).isBefore(end.plusDays(1))) {
					
				if (next != null) {
						DriverAttendance attendance = driverBL.Driver_Attendance_to_tripSheet(trip_DRIVER_ID, trip_DRIVER_NAME,
								trip, userDetails);
						java.util.Date attendanceDate = driverAttendanceFormat.parse("" + next);
						java.sql.Date ATTENDANCE_DATE = new Date(attendanceDate.getTime());
						attendance.setATTENDANCE_DATE(ATTENDANCE_DATE);
	
						if (ATTENDANCE_DATE.equals(Close_date)) {
							attendance.setPOINT_STATUS_ID(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT);
							attendance.setPOINT_TYPE_ID(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIP);
							attendance.setDRIVER_POINT(FristDriverRoutePoint);
						} else {
							attendance.setPOINT_STATUS_ID(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_NO);
							attendance.setDRIVER_POINT(TRIP_SHEET_DEFALAT_AMOUNT);
						}
						
						driverAttendanceService.addDriverAttendance(attendance);
					}
				
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateDriverHaltDetails(ValueObject object) throws Exception {
		try {
			CustomUserDetails 	userDetails = (CustomUserDetails)object.get("userDetails");
			TripSheetDto 		sheet 		= (TripSheetDto)object.get("sheet");
			
			List<DriverHaltDto> DriverHalt = tripSheetBL.Driver_Halt_CreateAttendanccey(
					driverHaltService.Find_list_TripSheet_DriverHalt(sheet.getTripSheetID(), userDetails.getCompany_id()));
			
			if (DriverHalt != null && !DriverHalt.isEmpty()) {
				for (DriverHaltDto dHalt : DriverHalt) {
					
					if (dHalt.getDRIVERID() == sheet.getTripFristDriverID()) {
						updateFirstDriverHaltDetails(object, dHalt);
					} else if (dHalt.getDRIVERID() == sheet.getTripSecDriverID()) {
						updateSecondDriverHaltDetails(object, dHalt);
					} else if (dHalt.getDRIVERID() == sheet.getTripCleanerID()) {
						updateCleanerHaltDetails(object, dHalt);
					}
				}
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateFirstDriverHaltDetails(ValueObject object, DriverHaltDto dHalt) throws Exception {
		try {
			CustomUserDetails 	userDetails 		= (CustomUserDetails)object.get("userDetails");
			TripSheetDto 		sheet 				= (TripSheetDto)object.get("sheet");
			
			LocalDate dHaltstartCle = LocalDate.parse(dHalt.getHALT_DATE_FROM());
			LocalDate dHaltendCle   = LocalDate.parse(dHalt.getHALT_DATE_TO());

			LocalDate nextCle = dHaltstartCle.minusDays(1);
			while ((nextCle = nextCle.plusDays(1)).isBefore(dHaltendCle.plusDays(1))) {
				if (nextCle != null) {
					
					DriverAttendance attendanceCle = driverBL.Driver_HALT_Attendance_to_tripSheet(dHalt.getDRIVERID(), dHalt.getDRIVER_NAME(), sheet, userDetails);

					java.util.Date attendanceDate = driverAttendanceFormat.parse("" + nextCle);
					java.sql.Date Reported_DateCle = new Date(attendanceDate.getTime());
					attendanceCle.setATTENDANCE_DATE(Reported_DateCle);
					attendanceCle.setPOINT_STATUS_ID(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT);
					attendanceCle.setPOINT_TYPE_ID(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIPHALT);
					attendanceCle.setDRIVER_POINT(1.0);
					attendanceCle.setCOMPANY_ID(userDetails.getCompany_id());
					attendanceCle.setCREATED_BY_ID(userDetails.getId());
					
					DriverAttendance validateHalt = driverAttendanceService.validate_DriverAttendance_Halt_details(dHalt.getDRIVERID(),
							driverAttendanceFormat.format(attendanceCle.getATTENDANCE_DATE()), attendanceCle.getPOINT_TYPE_ID(), userDetails.getCompany_id());
					if (validateHalt != null) {
						driverAttendanceService.Update_DriverAttendance_Halt_point(
								attendanceCle.getATTENDANCE_STATUS_ID(),
								attendanceCle.getPOINT_STATUS_ID(),
								attendanceCle.getPOINT_TYPE_ID(), 1.0,
								validateHalt.getDAID(), userDetails.getCompany_id());
					} else {
						driverAttendanceService.addDriverAttendance(attendanceCle);
					}
					
				}
				
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateSecondDriverHaltDetails(ValueObject object, DriverHaltDto dHalt) throws Exception {
		try {
			CustomUserDetails 	userDetails 		= (CustomUserDetails)object.get("userDetails");
			TripSheetDto 		sheet 				= (TripSheetDto)object.get("sheet");
			
			LocalDate d2HaltstartCle = LocalDate.parse(dHalt.getHALT_DATE_FROM());
			LocalDate d2HaltendCle = LocalDate.parse(dHalt.getHALT_DATE_TO());

			LocalDate nextCle = d2HaltstartCle.minusDays(1);
			while ((nextCle = nextCle.plusDays(1)).isBefore(d2HaltendCle.plusDays(1))) {
				if (nextCle != null) {

					DriverAttendance attendanceSEDR = driverBL.Driver_HALT_Attendance_to_tripSheet(dHalt.getDRIVERID(), dHalt.getDRIVER_NAME(), sheet, userDetails);
					
					java.util.Date attendanceDate = driverAttendanceFormat.parse("" + nextCle);
					java.sql.Date Reported_DateCle = new Date(attendanceDate.getTime());
					attendanceSEDR.setATTENDANCE_DATE(Reported_DateCle);
					attendanceSEDR.setPOINT_STATUS_ID(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT);
					attendanceSEDR.setPOINT_TYPE_ID(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIPHALT);
					attendanceSEDR.setDRIVER_POINT(1.0);
					
					DriverAttendance validateHalt = driverAttendanceService.validate_DriverAttendance_Halt_details(dHalt.getDRIVERID(),
							driverAttendanceFormat.format(attendanceSEDR.getATTENDANCE_DATE()),attendanceSEDR.getPOINT_TYPE_ID(), userDetails.getCompany_id());
					if (validateHalt != null) {
						driverAttendanceService.Update_DriverAttendance_Halt_point(
								attendanceSEDR.getATTENDANCE_STATUS_ID(),
								attendanceSEDR.getPOINT_STATUS_ID(),
								attendanceSEDR.getPOINT_TYPE_ID(), 1.0,
								validateHalt.getDAID(), userDetails.getCompany_id());
					} else {
						driverAttendanceService.addDriverAttendance(attendanceSEDR);
					}
				}
			} 
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateCleanerHaltDetails(ValueObject object, DriverHaltDto dHalt) throws Exception {
		try {
			CustomUserDetails 	userDetails 		= (CustomUserDetails)object.get("userDetails");
			TripSheetDto 		sheet 				= (TripSheetDto)object.get("sheet");

			LocalDate CLHaltstartCle = LocalDate.parse(dHalt.getHALT_DATE_FROM());
			LocalDate CLHaltendCle   = LocalDate.parse(dHalt.getHALT_DATE_TO());

			LocalDate nextCle = CLHaltstartCle.minusDays(1);
			while ((nextCle = nextCle.plusDays(1)).isBefore(CLHaltendCle.plusDays(1))) {
				if (nextCle != null) {

					DriverAttendance attendanceCL = driverBL.Driver_HALT_Attendance_to_tripSheet(dHalt.getDRIVERID(), dHalt.getDRIVER_NAME(), sheet, userDetails);

					java.util.Date attendanceDate = driverAttendanceFormat.parse("" + nextCle);
					java.sql.Date Reported_DateCle = new Date(attendanceDate.getTime());
					attendanceCL.setATTENDANCE_DATE(Reported_DateCle);
					attendanceCL.setPOINT_STATUS_ID(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT);
					attendanceCL.setPOINT_TYPE_ID(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIPHALT);
					attendanceCL.setDRIVER_POINT(1.0);
					
					DriverAttendance validateHalt = driverAttendanceService.validate_DriverAttendance_Halt_details(dHalt.getDRIVERID(),
							driverAttendanceFormat.format(attendanceCL.getATTENDANCE_DATE()),attendanceCL.getPOINT_TYPE_ID(), userDetails.getCompany_id());
					if (validateHalt != null) {
						driverAttendanceService.Update_DriverAttendance_Halt_point(
								attendanceCL.getATTENDANCE_STATUS_ID(),
								attendanceCL.getPOINT_STATUS_ID(),
								attendanceCL.getPOINT_TYPE_ID(), 1.0,
								validateHalt.getDAID(), userDetails.getCompany_id());
					} else {
						driverAttendanceService.addDriverAttendance(attendanceCL);
					}
				}
			} 
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateTripExtraReceivedDetails(ValueObject object) {
		try {
			CustomUserDetails 	userDetails   = (CustomUserDetails)object.get("userDetails");
			TripSheetDto 		sheet 		  = (TripSheetDto)object.get("sheet");
			
			TripSheet tsheet = new TripSheet();
			tsheet.setTripSheetID(sheet.getTripSheetID());
						
			for (int i = TRIP_SHEET_DEFALAT_ZERO; i < sheet.getTripsheetextraname().length; i++) {

				TripSheetExtraReceived TripExtraOptionsReceived = new TripSheetExtraReceived();

				TripExtraOptionsReceived.setTripsheetoptionsID(Long.parseLong(sheet.getTripsheetextraname()[i] + ""));
				TripExtraOptionsReceived.setTripSheetExtraQuantityReceived(Double.parseDouble("" + sheet.getTripSheetExtraQuantityReceived()[i]));
				TripExtraOptionsReceived.setTripSheetExtraDescription(sheet.getTripSheetExtraDescription()[i]);
				TripExtraOptionsReceived.setCreatedById(userDetails.getId());
				TripExtraOptionsReceived.setTripsheet(tsheet);
				TripExtraOptionsReceived.setCompanyId(userDetails.getCompany_id());

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp 	   toDate            = new java.sql.Timestamp(currentDateUpdate.getTime());
				TripExtraOptionsReceived.setCreated(toDate);

				List<TripSheetExtraReceived> validate = tripSheetService.ValidateAllTripSheetExtraOptionsReceived(
						Long.parseLong("" + sheet.getTripsheetextraname()[i]), sheet.getTripSheetID(), userDetails.getCompany_id());

				if (validate != null && !validate.isEmpty()) {
					object.put("alreadyExta", true);
				} else {
					tripSheetService.addTripSheetExtraOptionReceived(TripExtraOptionsReceived);	
					object.put("updateExtra", true);
				}
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateDriverSalary(ValueObject object) {
	 List<DriverAttendanceDto>   		attendanceList       			= null;	
	 VehicleProfitAndLossDto			driverSalaryExpense				= null;
		try {
			CustomUserDetails 	userDetails   = (CustomUserDetails)object.get("userDetails");
			TripSheetDto 		sheet 		  = (TripSheetDto)object.get("sheet");
			TripSheetDto 		trip 		  = (TripSheetDto)object.get("TripSheet");
			
			 attendanceList = driverAttendanceService.getDriverAttandanceListOfTripSheet(sheet.getTripSheetID(), userDetails.getCompany_id());
				
			 if (sheet.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				 
				 driverSalaryInsertionDetailsService.saveDriverSalaryInsertionDetails(new DriverSalaryInsertionDetails(sheet.getTripFristDriverID(), DateTimeUtility.geTimeStampFromDate(new java.util.Date()), userDetails.getCompany_id()));
			 }
			 
			 if (sheet.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				 driverSalaryInsertionDetailsService.saveDriverSalaryInsertionDetails(new DriverSalaryInsertionDetails(sheet.getTripSecDriverID(), DateTimeUtility.geTimeStampFromDate(new java.util.Date()), userDetails.getCompany_id()));
			 }
			 
			 if (sheet.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
				 driverSalaryInsertionDetailsService.saveDriverSalaryInsertionDetails(new DriverSalaryInsertionDetails(sheet.getTripCleanerID(), DateTimeUtility.geTimeStampFromDate(new java.util.Date()), userDetails.getCompany_id()));
			 }
			 
			 if(attendanceList != null && !attendanceList.isEmpty()) {
				 				 
			 	driverSalaryExpense	= new VehicleProfitAndLossDto();
				
				driverSalaryExpense.setTxnTypeId(sheet.getTripSheetID());
				driverSalaryExpense.setExpenseId(sheet.getTripSheetID());
				driverSalaryExpense.setVid(sheet.getVid());
				driverSalaryExpense.setTransactionDateTime(new Timestamp(trip.getClosetripDateOn().getTime()));
									 	
				 for(DriverAttendanceDto	attendanceDto : attendanceList) {
						Double	salary	= 0.0;
						
						if(attendanceDto.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_DAY) {
							salary	= attendanceDto.getPerDaySalary();
						}else if(attendanceDto.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_TRIP) {
							salary	= attendanceDto.getPerDaySalary();
						}else if(attendanceDto.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_KM) {
							salary	= attendanceDto.getPerDaySalary() * attendanceDto.getTripUsageKM();
						}else {
							salary	= attendanceDto.getPerDaySalary();
						}
						
						if(salary != null) {
							driverSalaryExpense.setTxnAmount((salary + driverSalaryExpense.getTxnAmount()));
						}
				}
				 
				 object.put("driverSalaryExpense", driverSalaryExpense);
				 
				 if(driverSalaryExpense.getTxnAmount() > 0) {
					 ValueObject salaryExpenseObject = new ValueObject();
					 
					 salaryExpenseObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					 salaryExpenseObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, sheet.getTripSheetID());
					 salaryExpenseObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					 salaryExpenseObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY);
					 salaryExpenseObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					 salaryExpenseObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, sheet.getVid());
					 salaryExpenseObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, Integer.parseInt(sheet.getTripSheetID()+""));
	
					 VehicleProfitAndLossTxnChecker driverSalaryTxn	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(salaryExpenseObject);
					 vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(driverSalaryTxn);
					 
					 object.put("driverSalaryTxn", driverSalaryTxn);
				 }
				 
				 
			}
								
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void runThreadForVehicleProfitAndLoss(ValueObject object) {
		try {
			CustomUserDetails 				userDetails   		= (CustomUserDetails)object.get("userDetails");
			TripSheetDto 					sheet 		  		= (TripSheetDto)object.get("sheet");
			ValueObject						valueTollObject		= (ValueObject)object.get("valueTollObject");
			VehicleProfitAndLossDto	    	driverSalaryExpense	= (VehicleProfitAndLossDto)object.get("driverSalaryExpense");
			VehicleProfitAndLossTxnChecker  driverSalaryTxn		= (VehicleProfitAndLossTxnChecker)object.get("driverSalaryTxn");
			
			HashMap<Long, VehicleProfitAndLossTxnChecker> expenseTxnCheckerHashMap = (HashMap<Long, VehicleProfitAndLossTxnChecker>)object.get("expenseTxnCheckerHashMap");
			HashMap<Long, TripSheetExpenseDto> 			  tripSheetExpenseHM	   = (HashMap<Long, TripSheetExpenseDto>)object.get("tripSheetExpenseHM");
			
			HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>	incomeTxnCheckerHashMap	= (HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>)object.get("incomeTxnCheckerHashMap");
			HashMap<Long, TripSheetIncomeDto> 					tripSheetIncomeDtoHM	= (HashMap<Long, TripSheetIncomeDto>)object.get("tripSheetIncomeDtoHM");
			
			if(tripSheetExpenseHM != null && tripSheetExpenseHM.size() > 0) {
				ValueObject	valueObject	= new ValueObject();
				valueObject.put("tripSheet", tripSheetService.getTripSheetData(sheet.getTripSheetID(), userDetails.getCompany_id()));
				valueObject.put("userDetails", userDetails);
				valueObject.put("tripSheetExpenseHM", tripSheetExpenseHM);
				valueObject.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);
				
				vehicleProfitAndLossService.runThreadForTripSheetExpenses(valueObject);
			}
			
			if(tripSheetIncomeDtoHM != null && tripSheetIncomeDtoHM.size() > 0) {
				ValueObject	valueObject	= new ValueObject();
				valueObject.put("tripSheet", tripSheetService.getTripSheetData(sheet.getTripSheetID(), userDetails.getCompany_id()));
				valueObject.put("userDetails", userDetails);
				valueObject.put("tripSheetIncomeHM", tripSheetIncomeDtoHM);
				valueObject.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap);
				
				vehicleProfitAndLossService.runThreadForTripSheetIncome(valueObject);
			}
			
			if(valueTollObject != null) {
				if(valueTollObject.containsKey("objectForPlICICI") && valueTollObject.get("objectForPlICICI") != null) {
					vehicleProfitAndLossService.runThreadForTripSheetExpenses((ValueObject)valueTollObject.get("objectForPlICICI"));
				}
				if(valueTollObject.containsKey("objectForPlKVB") && valueTollObject.get("objectForPlKVB") != null) {
					vehicleProfitAndLossService.runThreadForTripSheetExpenses((ValueObject)valueTollObject.get("objectForPlKVB"));
				}
				
				if(valueTollObject.containsKey("objectForPlHDFC") && valueTollObject.get("objectForPlHDFC") != null) {
					vehicleProfitAndLossService.runThreadForTripSheetExpenses((ValueObject)valueTollObject.get("objectForPlHDFC"));
				}
				if(valueTollObject.containsKey("objectForPlYesBank") && valueTollObject.get("objectForPlYesBank") != null) {
					vehicleProfitAndLossService.runThreadForTripSheetExpenses((ValueObject)valueTollObject.get("objectForPlYesBank"));
				}
			}
			
			if(driverSalaryTxn != null && driverSalaryExpense.getTxnAmount() > 0) {
				
				ValueObject	valueObject	= new ValueObject();
				valueObject.put("driverSalaryExpense", driverSalaryExpense);
				valueObject.put("userDetails", userDetails);
				valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, driverSalaryTxn.getVehicleProfitAndLossTxnCheckerId());
				valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY);
				valueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				
				new Thread() {
					public void run() {
						try {
							vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueObject);
							vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueObject);
							vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueObject);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}		
				}.start();
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public ValueObject saveTripComment(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		HashMap<String, Object> 	configuration						= null;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails(companyId, userId);
			
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
											PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			
			TripSheetComment saveComment = new TripSheetComment();
			saveComment.setTRIPSHEETID(tripsheetId);
			saveComment.setTRIP_COMMENT(object.getString("comment"));
			object.put("configuration", configuration);
			
			UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
			saveComment.setCREATEDBY(Orderingname.getFirstName());
			saveComment.setCREATED_PLACE(Orderingname.getBranch_name());
			saveComment.setCREATED_EMAIL(userDetails.getEmail());
			saveComment.setCOMPANY_ID(userDetails.getCompany_id());
			saveComment.setMarkForDelete(false);
		
			TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(tripsheetId);
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
			
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			saveComment.setCREATED_DATE(toDate);

			tripCommentService.add_TripSheet_Comment(saveComment);

			tripSheetService.update_Total_TripSheet_comment_Add(tripsheetId, userDetails.getCompany_id());
			
			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
			
			return object;
			
			} catch (Exception e) {
				throw e;
			}
		}
	
	@Override
	@Transactional
	public ValueObject removeTripComment(ValueObject object) throws Exception {
		try {
			tripCommentService.removeTripSheetCommentById(object.getLong("commentId",0));
			tripSheetService.update_Total_TripSheet_comment_Add(object.getLong("tripsheetId",0), object.getInt("companyId",0));
			return object;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject addTripSheetPODDetails(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			object.put("TripSheet", trip);
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject saveTripSheetPODDetails(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			tripSheetService.updatePODDetails(tripsheetId, object.getInt("noOfPOD"), userDetails.getCompany_id());
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteTripSheetDetails(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		Boolean 					validateTripsheet 					= false;
		HashMap<String, Object> 	configuration						= null;
		HashMap<String, Object> 	tollConfiguration					= null;
		boolean						deleteTripSheetAfterClose			= false;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails(companyId, userId);
			configuration			= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			tollConfiguration		= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TOLL_API_CONFIG);
			deleteTripSheetAfterClose	= (boolean) configuration.get("deleteTripSheetAfterClose");
			
			object.put("userDetails", userDetails);
			
			TripSheetDto sheet = tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
			
			VehicleAccidentDetails	details = accidentDetailsService.getVehicleAccidentDetailsByTripSheetId(tripsheetId);
			
			TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(tripsheetId);
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
			
			if(details != null) {
				object.put("accidentId", details.getAccidentDetailsId());
				return object;
			}
			
			object.put("TripSheet", sheet);
			
			if(sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED){
				object.put("accountClosed", true);
				return object;
			}
			if(sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && !deleteTripSheetAfterClose){
				object.put("Closed", true);
				return object;
			}
			
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(sheet.getVid(), companyId);
			object.put("CheckVehicleStatus", CheckVehicleStatus);
				
			List<DriverHaltDto> DriverHalt = driverHaltService.Find_list_TripSheet_DriverHalt(tripsheetId, userDetails.getCompany_id());
				if (DriverHalt != null && !DriverHalt.isEmpty()) {
					String TID = "";
					for (DriverHaltDto add : DriverHalt) {
						TID += " "+ add.getDRIVERID() +"  ";
					}
					object.put("CloseTS", TID);
					object.put("deleteDH", true);
					validateTripsheet = false;

				} 
				
					List<FuelDto> ShowFuelTS = fuelService.Get_TripSheet_IN_FuelTable_List(tripsheetId, userDetails.getCompany_id(), userDetails.getId());
					
					if (ShowFuelTS != null && !ShowFuelTS.isEmpty()) {
						String TID = "";
						for (FuelDto add : ShowFuelTS) {
							TID += "F- "+ add.getFuel_Number() +" " ;
						}
						object.put("CloseTS", TID);
						object.put("deleteFuel", true);
						validateTripsheet = false;
					} else {
						validateTripsheet = true;
					}
					
				List<DriverSalaryAdvance> penalty =	driverSalaryAdvanceService.getDriverSalaryAdvanceByTripSheetId(tripsheetId, userDetails.getCompany_id());
				if(penalty != null && !penalty.isEmpty()) {
					object.put("deletePenalty", true);
					validateTripsheet = false;
					return object;
				} 
				
				
				if (validateTripsheet) {
					if ((boolean) configuration.getOrDefault("saveDriverLedgerDetails", false)) 
					driverLedgerService.updateDriverLedgerWhenTripDeleted(tripsheetId, sheet.getTripFristDriverID(),sheet.getTripSheetNumber());
					
					bankPaymentService.deleteAllStatmentDetailsFromTrip(tripsheetId, userDetails.getCompany_id(), userId);
					tripSheetService.Delete_TSID_to_TripSheetAdvance(tripsheetId, userDetails.getCompany_id());
					tripSheetService.Delete_TSID_to_TripSheetExpence(tripsheetId, userDetails.getCompany_id());
					tripSheetService.Delete_TSTD_to_TripSheetIncome(tripsheetId, userDetails.getCompany_id());
					tripCommentService.Delete_TripSheet_to_TripSheetComment(tripsheetId, userDetails.getCompany_id());
					driverSalaryAdvanceRepository.removeDriverPenalty(tripsheetId);
					// Note : When Vehicle Created TRIPROUTE That time Vehicle Status update to 'WORKSHOP'
					vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, sheet.getVid(),
							userDetails.getCompany_id(),VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
					
					if (sheet.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
								TRIP_SHEET_DEFALAT_VALUE, sheet.getTripFristDriverID(), userDetails.getCompany_id());
					}
					if (sheet.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
								TRIP_SHEET_DEFALAT_VALUE, sheet.getTripSecDriverID(), userDetails.getCompany_id());
					}
					if (sheet.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
								TRIP_SHEET_DEFALAT_VALUE, sheet.getTripCleanerID(), userDetails.getCompany_id());
					}
					
					
					if(CheckVehicleStatus.getVehicle_Odometer().equals(sheet.getTripOpeningKM())) { 
						VehicleOdometerHistory odometerHistoryLsit	= vehicleOdometerHistoryService.getVehicleOdometerHistoryByVidExceptCurrentEntry(sheet.getTripSheetID(),sheet.getVid(), userDetails.getCompany_id());
						if(odometerHistoryLsit != null ) {
							if(odometerHistoryLsit.getVehicle_Odometer() < sheet.getTripOpeningKM()) { //  found either pre entry or post entry
								vehicleService.updateCurrentOdoMeter(sheet.getVid(), odometerHistoryLsit.getVehicle_Odometer(), userDetails.getCompany_id());
								vehicleOdometerHistoryService.deleteVehicleOdometerHistory(sheet.getTripSheetID(), userDetails.getCompany_id());
							}
						
						}
						
					}
					java.util.Date currentDate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
					tripSheetService.deleteTripSheet(tripsheetId, userDetails.getCompany_id(),userDetails.getId(),toDate);
					driverAttendanceService.DELETE_DRIVERATTENDANCE_TRIPSHEET_ID(tripsheetId, userDetails.getCompany_id());
					
					object.put("deleteTripSheet", true);
					
				}
				if(sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && deleteTripSheetAfterClose) {
					if(sheet.getTripTotalexpense() != null && sheet.getTripTotalexpense() > 0) {
						object.put("deleteExpense", true);
						return object;
					}
					if(sheet.getTripTotalincome() != null && sheet.getTripTotalincome() > 0) {
						object.put("deleteIncome", true);
						return object;
					}
					if(sheet.getTripTotalAdvance() != null && sheet.getTripTotalAdvance() > 0) {
						object.put("deleteAdvance", true);
						return object;
					}
					if(((boolean) tollConfiguration.get(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION) || (boolean) tollConfiguration.get(TollApiConfiguration.ALLOW_KVB_BANK_TOLL_API))) {
						Double totalTollAmt = tollExpensesDetailsService.getTripSheetTollAmount(tripsheetId);
						if(totalTollAmt != null && totalTollAmt > 0) {
							ValueObject		valueObject	= new ValueObject();
							valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
							valueObject.put("txnAmount", totalTollAmt);
							valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(sheet.getClosetripDateOn()));
							valueObject.put("txnTypeId", tripsheetId);
							valueObject.put("expenseId", (Integer) tollConfiguration.get("TollExpenseId"));
							valueObject.put("vid", sheet.getVid());
							valueObject.put("companyId", userDetails.getCompany_id());
							valueObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, Long.parseLong((Integer) tollConfiguration.get("TollExpenseId")+""));
							
							new Thread() {
								public void run() {
									try {
										vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(valueObject);
										vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(valueObject);
										vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(valueObject);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}		
							}.start();
						}
					}
				}
				
			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject addcloseAccountTripSheet(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		String						email								= null;
		CustomUserDetails			userDetails							= null;
		HashMap<String, Object>    	config								= null;
		boolean						showCombineTripDetails				= false;
		List<TripSheetExpenseDto> 	finalExpenseList					= null;
		boolean						allowAccountCloseWithoutIncome		= true;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			email					= object.getString("email");
			
			userDetails				= new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			config			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			showCombineTripDetails	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.SHOW_COMBINE_TRIP_DETAILS, false);
			allowAccountCloseWithoutIncome = (boolean) config.getOrDefault(TripSheetConfigurationConstant.ALLOW_ACC_CLOSE_WITHOUT_INCOME, true);
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			
			
			if(!allowAccountCloseWithoutIncome && trip.getTripTotalincome() == null) {
				object.put("noIncomeFound", true);
				return object;
			}
			
			object.put("TripSheet", trip);
			
			object.put("TripSheetAdvance",tripSheetService.findAllTripSheetAdvance(tripsheetId, userDetails.getCompany_id()));
			
			if(showCombineTripDetails) {
				List<TripSheetExpenseDto> expenseCombine = tripSheetService.findAllTripSheetExpense(tripsheetId, userDetails.getCompany_id());
				if(expenseCombine != null && !expenseCombine.isEmpty()) {
					HashMap<Integer, TripSheetExpenseDto> expenseMap = tripSheetService.findAllTripSheetExpenseCombineMap(expenseCombine);
					if(expenseMap != null) {
						finalExpenseList = new ArrayList<TripSheetExpenseDto>();
						finalExpenseList.addAll(expenseMap.values());
					}
					object.put("ExpenseCombineList",finalExpenseList);
				}
			} else {
				List<TripSheetExpenseDto> expenseDto = tripSheetService.findAllTripSheetExpense(tripsheetId, userDetails.getCompany_id());
				object.put("TripSheetExpense",expenseDto);
			}
			
			List<TripSheetIncomeDto> incomeDto = tripSheetService.findAllTripSheetIncome(tripsheetId, userDetails.getCompany_id());
			object.put("TripSheetIncome",incomeDto);
			
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalAdvance = trip.getTripTotalAdvance();
			if (trip.getTripTotalAdvance() == null) {
				TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			object.put("advanceTotal", df.format(TotalAdvance));
			
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			object.put("expenseTotal", df.format(TotalExpense));
			
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double Totalincome = trip.getTripTotalincome();
			if (trip.getTripTotalincome() == null) {
				Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			object.put("incomeTotal", df.format(Totalincome));
			
			
			UserProfileDto Orderingname = userProfileService.getUserDeatilsByEmail(email, companyId);
			object.put("paidBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
			object.put("paidById", userDetails.getId());
			object.put("place", Orderingname.getBranch_name());
			object.put("placeId", Orderingname.getBranch_id());
			
			
			if (trip.getTripCommentTotal() != null && trip.getTripCommentTotal() != TRIP_SHEET_DEFALAT_ZERO) {
				object.put("TripComment", tripSheetBL.TripSheetComment_Display(
						tripCommentService.list_TripSheet_ID_TO_TripSheetComment(tripsheetId, companyId)));
			}
						
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject savecloseAccountTripSheet(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		HashMap<String, Object>		companyConfiguration				= null;
		HashMap<String, Object> 	configuration						= null;
		CashBookDto 				cashbook 							= null;
		CashBookSequenceCounter 	sequenceCounter 					= null;
		HashMap<String, Object> 	CashBookconfig						= null;
		ValueObject 				valueObject							= null;
		MultipartFile 				file 								= null;
		boolean 					saveDriverBalanceInCashBook			= false;
		Vehicle 					vehicle								= null;
		
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			configuration	 		 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			companyConfiguration	 = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			CashBookconfig	 		 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
			TripSheetDto trip = tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
			UserProfileDto Orderingname 	 = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate 				 = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(tripsheetId);
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
			
			tripSheetService.updateCloseAccountTrip(userDetails.getId(), TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED,
					object.getDouble("accountClosedAmount"), object.getString("accountClosedRef"), toDate,
					userDetails.getId(), toDate, tripsheetId, userDetails.getCompany_id(),
					Orderingname.getBranch_id());

			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
			
			if(trip.getDriverBalance() != 0 && (boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)){
				ValueObject bankPaymentValueObject=new ValueObject();
				bankPaymentValueObject.put("currentPaymentTypeId",PaymentTypeConstant.PAYMENT_TYPE_CASH);
				bankPaymentValueObject.put("userId", userDetails.getId());
				bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId",trip.getTripSheetID());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.TRIP_CLOSE);
				bankPaymentValueObject.put("moduleNo", trip.getTripSheetNumber());
				bankPaymentValueObject.put("amount",trip.getDriverBalance());
				bankPaymentValueObject.put("paidDate", new java.util.Date());
				bankPaymentValueObject.put("remark", "Driver : "+trip.getTripFristDriverName() +", Vehicle : "+trip.getVehicle_registration());
				if (object.getShort("closeTripStatusId") == 1) 
					bankPaymentValueObject.put("fromIncome", true);
				
				cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
			}
			
			TripSheetDto updateTrip = tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
			
			try {
				if((boolean) configuration.get("pushTripsheetStatusToIvCargo")) {
					sendDataToCargo(updateTrip);
				}
			}catch(Exception e){
				
			}
			//cashbook entry Trip LS income starts here 
			if((boolean) CashBookconfig.get("addTripIncomeInCashBook")) {
				saveTripsheetIncomeTocashBook(tripsheetId,companyId,userDetails);
			}
			//ends 

			object.put("accountClosed", true);
						
			return object;
			
			} catch (Exception e) {
				throw e;
			}
		}
	
	@Override
	public ValueObject showCloseAccountTripSheet(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		HashMap<String, Object> 	config								= null;
		HashMap<String, Object> 	configuration	          			= null;
		boolean						hideClosedTripSheet	      			= false;
		boolean						showCombineTripDetails				= false;
		boolean 					allowTollApiIntegration 			= false;
		List<TripSheetExpenseDto> 	finalExpenseList					= null;
		List<FuelDto> 				ShowAmount							= null;
		double 						fuelAmount							= 0.0;
		double 						ureaAmount							= 0.0;
		double 						tollAmount							= 0.0;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
						
			userDetails				= new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			DecimalFormat 					numberFormat = new DecimalFormat("#.00");
			Collection<GrantedAuthority>	permission	 = userDetails.getGrantedAuthoritiesList();	
			object.put("permissions", permission);
			
			config	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TOLL_API_CONFIG);
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			hideClosedTripSheet		= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.HIDE_CLOSED_TRIPSHEET, false);
			showCombineTripDetails	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.SHOW_COMBINE_TRIP_DETAILS, false);
			
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(tripsheetId, userDetails));
			object.put("TripSheet", trip);
			
			if(hideClosedTripSheet && !permission.contains(new SimpleGrantedAuthority("SHOW_TRIPSHEET_CLOSE_STATUS"))){
				if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED || trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED ) {
					object.put("NotFound", true);
				}
			}
						
			if((boolean)config.get(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION) || (boolean)config.get(TollApiConfiguration.ALLOW_KVB_BANK_TOLL_API)) {
				allowTollApiIntegration = true;
			}
			object.put(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION, allowTollApiIntegration);
			object.put("allowTicketIncomeApiIntegeration",(boolean) configuration.get("allowTicketIncomeApiIntegeration"));
			object.put("companyId",userDetails.getCompany_id());
			object.put("userId", userDetails.getId());
			object.put("TripSheet", trip);
			object.put("configuration", configuration);
			
			VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Fuel_Entries_Show(vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(trip.getVid(), userDetails.getCompany_id()));
			object.put("vehicle", vehicle);
			
			object.put("TripSheetHalt", tripSheetBL.Driver_Halt_Display(driverHaltService.Find_list_TripSheet_DriverHalt(tripsheetId, userDetails.getCompany_id())));
						
			object.put("TripSheetAdvance",tripSheetService.findAllTripSheetAdvance(tripsheetId, userDetails.getCompany_id()));
			
			if(showCombineTripDetails) {
				List<TripSheetExpenseDto> expenseCombine = tripSheetService.findAllTripSheetExpense(tripsheetId, userDetails.getCompany_id());
				if(expenseCombine != null && !expenseCombine.isEmpty()) {
					HashMap<Integer, TripSheetExpenseDto> expenseMap = tripSheetService.findAllTripSheetExpenseCombineMap(expenseCombine);
					if(expenseMap != null) {
						finalExpenseList = new ArrayList<TripSheetExpenseDto>();
						finalExpenseList.addAll(expenseMap.values());
					}
					object.put("ExpenseCombineList",finalExpenseList);
				}
			} else {
				List<TripSheetExpenseDto> expenseDto = tripSheetService.findAllTripSheetExpense(tripsheetId, userDetails.getCompany_id());
				object.put("TripSheetExpense",expenseDto);
			}
			
			List<TripSheetIncomeDto> incomeDto = tripSheetService.findAllTripSheetIncome(tripsheetId, userDetails.getCompany_id());
			object.put("TripSheetIncome", incomeDto);
			
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalAdvance = trip.getTripTotalAdvance();
			if (trip.getTripTotalAdvance() == null) {
				TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			object.put("advanceTotal", df.format(TotalAdvance));
			
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			object.put("expenseTotal", df.format(TotalExpense));
			
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double Totalincome = trip.getTripTotalincome();
			if (trip.getTripTotalincome() == null) {
				Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			object.put("incomeTotal", df.format(Totalincome));
			
			ValueObject tollObject = tollExpensesDetailsService.getTollExpensesDetailsList(tripsheetId, userDetails.getCompany_id());
			tollAmount = tollObject.getDouble("totalAmount", 0);
			object.put("TripSheetTollExpense",(tollObject.get("ExpenseName")));
			object.put("TripSheetTollExpenseName", config.get(TollApiConfiguration.TOLL_EXPENSE_NAME));
			object.put("TripSheetTollExpenseDate", tollObject.get("Date"));
			object.put("TripSheetTollExpenseTotalAmount", tollObject.get("totalAmount"));
			
			ShowAmount = fuelService.Get_TripSheet_IN_FuelTable_List(tripsheetId, userDetails.getCompany_id(), userDetails.getId());
			fuelAmount= fuelBL.prepareTotalAmount_Tripsheet_Created_fuel_details(ShowAmount);
			object.put("fuel", fuelBL.prepareListofFuel(ShowAmount));
			object.put("fTDiesel", fuelBL.prepareTotal_Tripsheet_fuel_details(ShowAmount));
			object.put("fTUsage", fuelBL.prepareTotalUsage_Tripsheet_fuel_details(ShowAmount));
			object.put("fTAmount",numberFormat.format(fuelBL.prepareTotalAmount_Tripsheet_fuel_details(ShowAmount)));
			
			List<UreaEntriesDto> ureaDeatils = ureaEntriesService.getUreaEntriesDetailsByTripSheetId(tripsheetId, userDetails.getCompany_id());
			if(ureaDeatils != null) {
			ureaAmount = ureaEntriesBL.prepareTotal_Tripsheet_Urea_Amount(ureaDeatils);	
			object.put("urea", ureaDeatils);
			object.put("totalUrea", ureaEntriesBL.prepareTotal_Tripsheet_Urea_details(ureaDeatils));
			object.put("totalUreaAmnt",ureaAmount);
			}
						
			if (trip.getTripCommentTotal() != null && trip.getTripCommentTotal() != TRIP_SHEET_DEFALAT_ZERO) {
				object.put("TripComment", tripSheetBL.TripSheetComment_Display(
						tripCommentService.list_TripSheet_ID_TO_TripSheetComment(tripsheetId, userDetails.getCompany_id())));
			}
			
			object.put("tripTotalincome", numberFormat.format(Totalincome-(fuelAmount + ureaAmount + tollAmount + TotalExpense)));
			
						
			return object;
			
			} catch (Exception e) {
				throw e;
			}
		}
	@Override
	public ValueObject getTripSheetAdvanceList(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("TripSheetAdvance",tripSheetService.findAllTripSheetAdvance(valueObject.getLong("tripSheetId",0), userDetails.getCompany_id()));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
		}
	}
	
	@Override
	public ValueObject getTripSheetExpenseList(ValueObject object) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object.put("TripSheetExpense",tripSheetService.findAllTripSheetExpense(object.getLong("tripSheetId",0), userDetails.getCompany_id()));
			
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
		}
	}
	
	@Override
	public ValueObject searchTripSheetByNumber(ValueObject object) throws Exception {
		try {
			Long tripSheetId =	tripSheetService.getTripSheetIdByNumber(object.getLong("tripSheetNumber",0), object.getInt("companyId",0), object.getLong("userId",0));
			object.put("tripSheetId", tripSheetId);
			return object;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject searchTripSheetDiffStatus(ValueObject object) throws Exception {
		try {
			object.put("TripSheet", tripSheetBL.prepareListofTripSheet(tripSheetService.Search_TripSheet_Status(object.getString("search"), object.getShort("status"))));
			return object;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public ValueObject searchVehCurTSShow(ValueObject object) throws Exception {
		try {
			object.put("tripSheet", tripSheetBL.prepareListofTripSheet(tripSheetService.Get_Vehicle_Current_TripSheet_Id(object.getInt("vid",0), object.getInt("companyId",0))));
			return object;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	@Transactional
	public ValueObject superUserTripSheetDelete(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		HashMap<String, Object> 	configuration						= null;
		HashMap<String, Object> 	tollConfiguration					= null;
		Integer						tripCompanyId						= 0;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			tripCompanyId			= object.getInt("tripCompanyId");
			userDetails				= new CustomUserDetails(tripCompanyId, userId);
			configuration			= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			tollConfiguration		= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TOLL_API_CONFIG);
			object.put("userDetails", userDetails);
			
			TripSheet sheet = tripSheetService.getTripSheetData(tripsheetId, tripCompanyId);
		    VehicleDto	vehicleDto	=	vehicleService.Get_Vehicle_Current_Status_TripSheetID(sheet.getVid(), tripCompanyId);
			if(sheet != null) {
				
				if(sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED){
					object.put("accountClosed", true);
					return object;
				}
					List<FuelDto> ShowFuelTS = fuelService.listFuelEntriesByTripSheetId(tripsheetId);
					
					if (ShowFuelTS != null && !ShowFuelTS.isEmpty()) {
						ValueObject		valueObject	=  null;
						for (FuelDto add : ShowFuelTS) {
							if(add.getFuel_amount() != null && add.getFuel_amount() > 0) {
								ValueObject fValueObject	=	new ValueObject();
								fValueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
								fValueObject.put("txnAmount", add.getFuel_amount());
								fValueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(add.getFuel_D_date()));
								fValueObject.put("txnTypeId", add.getFuel_id());
								fValueObject.put("vid", add.getVid());
								fValueObject.put("companyId", tripCompanyId);
								new Thread() {
									public void run() {
										try {
											vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(fValueObject);
											vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(fValueObject);
											vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(fValueObject);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}		
								}.start();
							}
						}
						fuelService.deleteFuelByTripSheetId(tripsheetId);
						tripSheetService.deleteFuelExpenseByTripId(tripsheetId);
						tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripsheetId, tripCompanyId);
			
					}
					
					
					driverSalaryAdvanceService.DELETE_PENALTY_IN_TRIPDailySheet_Delete_ID(tripsheetId);
					tripSheetService.Delete_TSID_to_TripSheetAdvance(tripsheetId, tripCompanyId);
					
					if((boolean) configuration.get("showTripsheetDueAmount")) {
						tripSheetService.deleteDueAmountByTripSheetId(tripsheetId);
					}
					
					
					if(sheet.getTripTotalexpense() != null && sheet.getTripTotalexpense() > 0) {
						List<TripSheetExpenseDto> tripSheetExpenseList	=	tripSheetService.findAllTripSheetExpense(tripsheetId, tripCompanyId);
						for(TripSheetExpenseDto	expenseDto : tripSheetExpenseList) {
							if(expenseDto.getExpenseAmount() > 0) {
							ValueObject		tValueObject	= new ValueObject();
								tValueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
								tValueObject.put("txnAmount", expenseDto.getExpenseAmount());
								tValueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(sheet.getClosetripDate()));
								tValueObject.put("txnTypeId", tripsheetId);
								tValueObject.put("expenseId", expenseDto.getExpenseId());
								tValueObject.put("vid", sheet.getVid());
								tValueObject.put("companyId", tripCompanyId);
								tValueObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, expenseDto.getTripExpenseID());
								
								new Thread() {
									public void run() {
										try {
											vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(tValueObject);
											vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(tValueObject);
											vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(tValueObject);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}		
								}.start();
							}
						}
						tripSheetService.Delete_TSID_to_TripSheetExpence(tripsheetId, tripCompanyId);
					}
					if(sheet.getTripTotalincome() != null && sheet.getTripTotalincome() > 0) {
						
						List<TripSheetIncomeDto> tripSheetExpenseList	=	tripSheetService.findAllTripSheetIncome(tripsheetId, tripCompanyId);
						for(TripSheetIncomeDto	tripIncome : tripSheetExpenseList) {
							if(tripIncome.getIncomeAmount() != null && tripIncome.getIncomeAmount() > 0) {
								ValueObject		valueObject	= new ValueObject();
								valueObject.put("userDetails", userDetails);
								valueObject.put("txnTypeId", tripsheetId);
								valueObject.put("tripIncome", tripIncome);
								valueObject.put("vid", sheet.getVid());
								valueObject.put("incomeId", tripIncome.getIncomeId());
								valueObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, tripIncome.getTripincomeID());
								valueObject.put("incomeAmount", tripIncome.getIncomeAmount());
								valueObject.put("transactionDate", DateTimeUtility.geTimeStampFromDate(sheet.getClosetripDate()));
								
								vehicleProfitAndLossService.runThreadForDeleteIncome(valueObject);
							}
						}
						
						tripSheetService.Delete_TSTD_to_TripSheetIncome(tripsheetId, tripCompanyId);
						
					}
					
					if(((boolean) tollConfiguration.get(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION) || (boolean) tollConfiguration.get(TollApiConfiguration.ALLOW_KVB_BANK_TOLL_API))) {
						Double totalTollAmt = tollExpensesDetailsService.getTripSheetTollAmount(tripsheetId);
						if(totalTollAmt != null && totalTollAmt > 0) {
							ValueObject		valueObject	= new ValueObject();
							valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
							valueObject.put("txnAmount", totalTollAmt);
							valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(sheet.getClosetripDate()));
							valueObject.put("txnTypeId", tripsheetId);
							valueObject.put("expenseId", (Integer) tollConfiguration.get("TollExpenseId"));
							valueObject.put("vid", sheet.getVid());
							valueObject.put("companyId", userDetails.getCompany_id());
							valueObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, Long.parseLong((Integer) tollConfiguration.get("TollExpenseId")+""));
							
							new Thread() {
								public void run() {
									try {
										vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(valueObject);
										vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(valueObject);
										vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(valueObject);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}		
							}.start();
						}
					}
					
					
					
					tripCommentService.Delete_TripSheet_to_TripSheetComment(tripsheetId, userDetails.getCompany_id());
					driverSalaryAdvanceRepository.removeDriverPenalty(tripsheetId);
					driverHaltService.deleteDriverHaltByTripSheetId(tripsheetId);
					
					// Note : When Vehicle Created TRIPROUTE That time Vehicle Status update to 'WORKSHOP'
					if(sheet.getTripStutesId() != TripSheetStatus.TRIP_STATUS_CLOSED) {
						vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, sheet.getVid(),
								userDetails.getCompany_id(),VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
						
						if (sheet.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
							driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
									TRIP_SHEET_DEFALAT_VALUE, sheet.getTripFristDriverID(), userDetails.getCompany_id());
						}
						if (sheet.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
							driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
									TRIP_SHEET_DEFALAT_VALUE, sheet.getTripSecDriverID(), userDetails.getCompany_id());
						}
						if (sheet.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
							driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
									TRIP_SHEET_DEFALAT_VALUE, sheet.getTripCleanerID(), userDetails.getCompany_id());
						}
					}
					
					if(sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED && vehicleDto.getVehicle_Odometer().equals(sheet.getTripOpeningKM())) {	
						VehicleOdometerHistory odometerHistoryLsit	= vehicleOdometerHistoryService.getVehicleOdometerHistoryByVidExceptCurrentEntry(sheet.getTripSheetID(),sheet.getVid(), userDetails.getCompany_id());
						if(odometerHistoryLsit != null && odometerHistoryLsit.getVehicle_Odometer() < sheet.getTripOpeningKM()) {
							vehicleService.updateCurrentOdoMeter(sheet.getVid(), odometerHistoryLsit.getVehicle_Odometer(), userDetails.getCompany_id());
							vehicleOdometerHistoryService.deleteVehicleOdometerHistory(sheet.getTripSheetID(), userDetails.getCompany_id());
						}
					}else if(sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED && vehicleDto.getVehicle_Odometer().equals(sheet.getTripClosingKM())) {
						VehicleOdometerHistory odometerHistoryLsit	= vehicleOdometerHistoryService.getVehicleOdometerHistoryByVidExceptCurrentEntry(sheet.getTripSheetID(),sheet.getVid(), userDetails.getCompany_id());
						if(odometerHistoryLsit != null && odometerHistoryLsit.getVehicle_Odometer() < sheet.getTripClosingKM() ) {
							vehicleService.updateCurrentOdoMeter(sheet.getVid(), odometerHistoryLsit.getVehicle_Odometer(), userDetails.getCompany_id());
							vehicleOdometerHistoryService.deleteVehicleOdometerHistory(sheet.getTripSheetID(), userDetails.getCompany_id());
						
						}
							
					}
					
					tripSheetService.superUserdeleteTripSheet(tripsheetId, userId , object.getString("reason"));
					driverAttendanceService.DELETE_DRIVERATTENDANCE_TRIPSHEET_ID(tripsheetId, userDetails.getCompany_id());
					
					object.put("deleteTripSheet", true);
				}else {
					object.put("notFoundToDelete", true);
				}
					
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	@Override
	public void saveTripExpenseDocument(TripSheetExpense tripSheetExpense, MultipartFile file, ValueObject valueObject) throws Exception {
		try {
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			org.fleetopgroup.persistence.document.TripSheetExpenseDocument tripSheetExpenseDoc = new org.fleetopgroup.persistence.document.TripSheetExpenseDocument();
			tripSheetExpenseDoc.setTripSheetExpenseId(tripSheetExpense.getTripExpenseID());
			if(file != null) {
				byte[] bytes = file.getBytes();
				tripSheetExpenseDoc.setTripSheetExpenseFileName(file.getOriginalFilename());
				tripSheetExpenseDoc.setTripSheetExpenseContent(bytes);
				tripSheetExpenseDoc.setTripSheetExpenseContentType(file.getContentType());
			} else {
				if (valueObject.getString("base64String",null) != null) {
					byte[] bytes = ByteConvertor.base64ToByte(valueObject.getString("base64String",null));
					
					tripSheetExpenseDoc.setTripSheetExpenseFileName(valueObject.getString("imageName",null));
					tripSheetExpenseDoc.setTripSheetExpenseContent(bytes);
					tripSheetExpenseDoc.setTripSheetExpenseContentType(valueObject.getString("imageExt",null));
				}
			}	
			tripSheetExpenseDoc.setMarkForDelete(false);
			tripSheetExpenseDoc.setCreatedById(tripSheetExpense.getCreatedById());
			tripSheetExpenseDoc.setLastModifiedById(tripSheetExpense.getCreatedById());
			tripSheetExpenseDoc.setCreated(toDate);
			tripSheetExpenseDoc.setLastupdated(toDate);
			tripSheetExpenseDoc.setCompanyId(tripSheetExpense.getCompanyId());

			addTripSheetExpenseDocument(tripSheetExpenseDoc);
			updateTripSheetExpenseDocumentId(tripSheetExpenseDoc.get_id(), true, tripSheetExpenseDoc.getTripSheetExpenseId(), tripSheetExpenseDoc.getCompanyId());

		} catch (Exception e) {
			throw e;
		}
	}
	@Transactional
	public void addTripSheetExpenseDocument(org.fleetopgroup.persistence.document.TripSheetExpenseDocument tripSheetExpenseDoc) {
		tripSheetExpenseDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_TRIP_SHEET_EXPENSE_DOCUMENT));
		mongoTemplate.save(tripSheetExpenseDoc);
	}
	
	@Transactional
	public void updateTripSheetExpenseDocumentId(Long tripSheetExpenseDocId, boolean tripSheetExpenseDocument, Long tripSheetExpenseId, Integer companyId) {
		TripSheetExpenseRepository.updateTripSheetExpenseDocumentId(tripSheetExpenseDocId, tripSheetExpenseDocument, tripSheetExpenseId, companyId);

	}
	
	@Override
	public org.fleetopgroup.persistence.document.TripSheetExpenseDocument getTripSheetExpenseDocumentDetails(long tripSheetExpenseDocumentId, Integer company_id)
			throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(tripSheetExpenseDocumentId).and("companyId").is(company_id).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.TripSheetExpenseDocument.class);
	
	}
	
	@Override
	public ValueObject getAccessToken(HttpServletRequest request) throws Exception {
		ValueObject 		object 		= null;
		try {
			object = new ValueObject();
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			object.put("uniqueID", uniqueID);
			return object; 
		} catch (Exception e) {
			throw e;
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public void addLHPV_AsExpenseDetailsInSaveOrDispatchTripsheet(ValueObject object) throws Exception {
		List<TripSheetExpense>  		tripSheetExpenseList			= null;
		Double							tripTotalExpense				= 0.0;
		TripSheetExpense				tripSheetExpense				= null;
		try {
			tripSheetExpenseList	= new ArrayList<>();
			
			List<LHPVDetails> 	lhpvDetails     = (List<LHPVDetails>)object.get("lhpvDetails");
			TripSheet 			trip 			= (TripSheet)object.get("trip");
			TripSheetAdvance  	advance			= (TripSheetAdvance)object.get("advance"); 	
			int					companyId 	    = object.getInt("companyId");
			long				userId			= object.getLong("userId");
			
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			for(LHPVDetails  details : lhpvDetails) {
				if(details != null && details.getLorryHire() > 0) {
					
					tripSheetExpense = new TripSheetExpense(); 
					tripSheetExpense.setExpenseId(Integer.parseInt("" + details.getlHPVDetailsId()));
					tripSheetExpense.setExpenseAmount(details.getLorryHire());
					tripSheetExpense.setExpensePlaceId(advance.getAdvancePlaceId());
					tripSheetExpense.setExpenseRefence("LHPV NO : "+details.getlHPVNumber());
					tripSheetExpense.setCompanyId(companyId);
					tripSheetExpense.setCreatedById(userId);
					tripSheetExpense.setTripsheet(trip);
					tripSheetExpense.setlHPVDetailsId(details.getlHPVDetailsId());
					tripSheetExpense.setCreated(toDate);
					tripSheetExpense.setBalanceAmount(tripSheetExpense.getExpenseAmount());
				//	tripSheetExpense.setIncomeCollectedById(userId);
					tripSheetExpenseList.add(tripSheetExpense);
						
				}
				
				tripTotalExpense  += details.getLorryHire();
			}
			if(trip.getTripTotalexpense() == null) {
				trip.setTripTotalexpense(tripTotalExpense);
			}else {
				trip.setTripTotalexpense(trip.getTripTotalexpense() + tripTotalExpense);
			}
			
			object.put("tripSheetExpenseList", tripSheetExpenseList);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getManualVsGpsKMComparison(ValueObject valueObject) throws Exception {
		int								vid						= 0;
		int								routeId					= 0;
		long							groupId					= 0;
		double 							percentAllowed			= 0.0;
		String							dateRange				= null;
		CustomUserDetails				userDetails				= null;
		List<TripSheetDto>				trip					= null;
		HashMap<String, Object> 		configuration			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			vid				= valueObject.getInt("vid", 0);
			groupId			= valueObject.getInt("groupId", 0);
			routeId			= valueObject.getInt("routeId", 0);
			dateRange		= valueObject.getString("dateRange");
			percentAllowed  = (double)configuration.get("manualVsGpsKmDifference");
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;
				From_TO_DateRange = dateRange.split(" to ");
				
				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				
				String vehicleName = "", groupName = "", routeName = "", Date = "";
				
				if(vid > 0)
				{
					vehicleName = " AND TS.vid = "+vid+" ";
				}
				
				if(groupId > 0)
				{
					groupName = " AND V.vehicleGroupId = "+groupId+" ";
				}
				
				if(routeId > 0)
				{
					routeName = " AND TS.routeID = "+routeId+" ";
				}
				
				Date =  " AND TS.closedByTime between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
				String query = " "+vehicleName+" "+groupName+" "+routeName+" "+Date+" ";
				
				trip = getManualVsGpsKMComparisonList(query, userDetails.getCompany_id(), percentAllowed);
				
				if(trip != null && !trip.isEmpty()) {
					valueObject.put("TripDetails", trip);
				}
				
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			trip				= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	public List<TripSheetDto> getManualVsGpsKMComparisonList(String querry, Integer companyId, double percentAllowed) throws Exception {
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TS.routeID,"
						+ " TR.routeName, TS.dispatchedByTime, TS.closedByTime, TS.tripUsageKM, TS.gpsTripUsageKM, TS.routeName , TR.routeApproximateKM"
						+ " from TripSheet TS " 
						+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
						+ " WHERE TS.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+", "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") "+querry+"  "
						+ " AND TS.gpsTripUsageKM != NULL AND TS.gpsTripUsageKM >= 1 AND  "
						+ " TS.companyId = "+companyId+" AND TS.markForDelete = 0 order by TS.tripSheetID desc",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto select = null;
			for (Object[] vehicle : results) {
				
					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setVehicle_registration((String) vehicle[3]);
					select.setVehicle_Group((String) vehicle[4]);
					select.setVehicleGroupId((long) vehicle[5]);
					select.setRouteID((Integer) vehicle[6]);
					select.setRouteName((String) vehicle[7]);
					select.setDispatchedByTimeOn((java.util.Date) vehicle[8]);
					select.setClosedByTimeOn((java.util.Date) vehicle[9]);
					select.setDispatchedTime(dateFormatTime.format(select.getDispatchedByTimeOn()));
					select.setClosedByTime(dateFormatTime.format(select.getClosedByTimeOn()));
					select.setTripUsageKM((Integer) vehicle[10]);
					select.setTripGpsUsageKM((double) vehicle[11]);
					if(select.getRouteName() == null && vehicle[12] != null) {
						select.setRouteName((String) vehicle[12]);
						
					}
					
					if(select.getTripUsageKM() > select.getTripGpsUsageKM().intValue()) {
						select.setDifferenceKm((select.getTripUsageKM()* 1.0) - select.getTripGpsUsageKM());
						if(select.getTripGpsUsageKM() > 0) {
							double percentDiff = ((select.getDifferenceKm()/select.getTripGpsUsageKM()) * 100);
							select.setDifferenceKmPercentage(percentDiff);
						}else {
							select.setDifferenceKmPercentage(0);
						}
						
						if(select.getDifferenceKmPercentage() > percentAllowed) {
							select.setDiffKMPercentDanger(true);
						} else {
							select.setDiffKMPercentDanger(false);
						}
						
					} else {
						select.setDifferenceKm(select.getTripGpsUsageKM() - (select.getTripUsageKM() * 1.0));
						if(select.getTripUsageKM() > 0) {
						double percentDiff = ((select.getDifferenceKm()/(select.getTripUsageKM() * 1.0)) * 100);
						select.setDifferenceKmPercentage(percentDiff);
						}else {
							select.setDifferenceKmPercentage(0);
						}
						if(select.getDifferenceKmPercentage() > percentAllowed) {
							select.setDiffKMPercentDanger(true);
						} else {
							select.setDiffKMPercentDanger(false);
						}
					}
				if(select.getTripGpsUsageKM() != null && select.getTripGpsUsageKM() > 0) {
					Dtos.add(select);
				}	

				select.setRouteApproximateKM((Integer) vehicle[13]);
			}
		}
		return Dtos;

	}

	@Transactional
	@Override
	public void saveTripDocument(TripSheet tripSheet,  ValueObject valueObject, MultipartFile file) throws Exception {
		try {
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			org.fleetopgroup.persistence.document.TripSheetDocument tripSheetDoc = new org.fleetopgroup.persistence.document.TripSheetDocument();
			tripSheetDoc.setTripSheetId(tripSheet.getTripSheetID());
		
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(tripSheet);
			
			if(file != null) {
				byte[] bytes = file.getBytes();
				tripSheetDoc.setTripSheetFileName(file.getOriginalFilename());
				tripSheetDoc.setTripSheetContent(bytes);
				tripSheetDoc.setTripSheetContentType(file.getContentType());
			} else {
				if (valueObject.getString("base64String",null) != null) {
					byte[] bytes = ByteConvertor.base64ToByte(valueObject.getString("base64String",null));
					
					tripSheetDoc.setTripSheetFileName(valueObject.getString("imageName",null));
					tripSheetDoc.setTripSheetContent(bytes);
					tripSheetDoc.setTripSheetContentType(valueObject.getString("imageExt",null));
				}
			}	
			tripSheetDoc.setMarkForDelete(false);
			tripSheetDoc.setCreatedById(tripSheet.getCreatedById());
			tripSheetDoc.setLastModifiedById(tripSheet.getCreatedById());
			tripSheetDoc.setCreated(toDate);
			tripSheetDoc.setLastupdated(toDate);
			tripSheetDoc.setCompanyId(tripSheet.getCompanyId());
			
			try {
				org.fleetopgroup.persistence.document.TripSheetDocument doc = getTripSheetDocumentDetails(tripSheet.getTripSheetDocumentId(), userDetails.getCompany_id());
				if (doc != null) {
					tripSheetDoc.set_id(doc.get_id());
					mongoOperations.save(tripSheetDoc);
					updateTripSheetDocumentId(tripSheetDoc.get_id(), true,tripSheetDoc.getTripSheetId(), userDetails.getCompany_id());
					
				}else {
					addTripSheetDocument(tripSheetDoc);
					updateTripSheetDocumentId(tripSheetDoc.get_id(), true,tripSheetDoc.getTripSheetId(), userDetails.getCompany_id());
					
				}
				
				tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
				
			} catch (ConverterNotFoundException e) {
				e.printStackTrace();
			}


		} catch (Exception e) {
			throw e;
		}
	}
	@Transactional
	public void addTripSheetDocument(org.fleetopgroup.persistence.document.TripSheetDocument tripSheetDoc) {
		tripSheetDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_TRIP_SHEET_DOCUMENT));
		mongoTemplate.save(tripSheetDoc);
	}
	
	@Transactional
	public void updateTripSheetDocumentId(Long tripSheetDocId, boolean tripSheetDocument, Long tripSheetId, Integer companyId) {
		tripSheetRepository.updateTripSheetDocumentId(tripSheetDocId, tripSheetDocument, tripSheetId, companyId);

	}
	@Override
	public org.fleetopgroup.persistence.document.TripSheetDocument getTripSheetDocumentDetails(Long id, Integer companyId) throws Exception {// checking already exists or not
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(id).and("companyId").is(companyId).and("markForDelete").is(false));

		return mongoTemplate.findOne(query, TripSheetDocument.class);
	}
	@Transactional
	@Override
	public ValueObject updateTripSheetDocument(ValueObject object,MultipartFile file) throws Exception {
		CustomUserDetails				userDetails				= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripSheet tripsheet = tripSheetRepository.getTripSheet(object.getLong("tripSheetId"), userDetails.getCompany_id());
			saveTripDocument(tripsheet,object,file);
			object.put("updated", true);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject saveChangeStatusTripSheet(ValueObject object) throws Exception {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		try {
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
						
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate 				 = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			Short closeAcctripNameById   = null;
			Double closeAccTripAmount    = null;
			String closeAccTripRef 		 = null;
			Timestamp closeAccTripDate	 = null;
			Integer closeAcclonId		 = null;

			TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(tripsheetId);
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
			
			tripSheetService.updateTripSheetStatusToClose(closeAcctripNameById, TripSheetStatus.TRIP_STATUS_CLOSED,
					closeAccTripAmount, closeAccTripRef, closeAccTripDate, userDetails.getId(), toDate, tripsheetId,
					userDetails.getCompany_id(), closeAcclonId);
			
			object.put("StatusChanged", true);
			
			tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
						
			return object;
			
			} catch (Exception e) {
				throw e;
			}
		}
	
	@Override
	public ValueObject saveTripSheetRouteWiseWeightDetails(ValueObject object) throws Exception {
		int							companyId					= 0;
		long						userId						= 0;
		long						tripsheetId					= 0;
		TripSheetDto				tripSheet					= null;
		double						tripcloseAmount				= 0.0;
		HashMap<String, Object> 	configuration				= null; 
		CustomUserDetails			userDetails					= null;
		try {
			System.err.println("inside method.. ");
			companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			
			userDetails				= new CustomUserDetails(companyId, userId);
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			tripSheet		= tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
			
			
			RouteWiseTripSheetWeight TripWeight = tripSheetMobileBL.saveTripWeight(object);
			System.err.println("Tripweight data ... "+ TripWeight);

			tripSheetService.addTripSheetRouteWiseWeight(TripWeight);

			
			object.put("success", true);
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}	
	
	@Override
	public ValueObject getTripSheetRouteWiseWeightList(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("TripSheetRouteWiseWight",tripSheetService.findAllTripSheetRouteWiseDetails(valueObject.getLong("tripSheetId",0), userDetails.getCompany_id()));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
		}
	}

	@Override
	public ValueObject removeTripSheetRouteWiseWightDetails(ValueObject object) throws Exception {
		// TODO Auto-generated method stub
		int							companyId					= 0;
		long						tripsheetId					= 0;
		long						routewiseWeightId			= 0;
		try {
			companyId    			= object.getInt("companyId");
			tripsheetId 			= object.getLong("tripsheetId");
			routewiseWeightId 		= object.getLong("routeWiseTripSheetWeightId");
			tripSheetService.deleteTripSheetRouteWiseWeight(routewiseWeightId, companyId);
			
		} catch (Exception e) {
			throw e;
		}
		return object;
	}

	@Override
	public ValueObject saveTripsheetIncomeTocashBook(Long tripsheetId, Integer companyId, CustomUserDetails	userDetails) throws Exception {
		// TODO Auto-generated method stub
		
		CashBookDto 				cashbook 							= null;
		ValueObject 				valueObject							= null;
		MultipartFile 				file 								= null;
		boolean 					saveDriverBalanceInCashBook			= false;
		Vehicle 					vehicle								= null;
		Double  					B_Income 							= 0.0;
		Double 						E_Income 							= 0.0;
		Double 						B_IncomeDue 						= 0.0;
		Double  					E_IncomeDue 						= 0.0;
		Double  					B_IncomeBal						    = 0.0;
		Double  					E_IncomeBal							= 0.0;
		
		try {
		
		    //cashbook entry Trip LS income starts here 
		    TripSheetDto updatedtrip = tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
		    vehicle  = vehicleService.getVehicel(updatedtrip.getVid(), userDetails.getCompany_id());
		    
		    String tripcloseDate = DateTimeUtility.getStringDateFromDate(updatedtrip.getClosetripDateOn(),DateTimeUtility.DD_MM_YYYY);
			
			List<TripSheetIncomeDto> incomeDto = tripSheetService.findAllTripSheetIncome(tripsheetId, userDetails.getCompany_id());
			
			if(incomeDto !=null) {
				for(TripSheetIncomeDto income:incomeDto) {
					if(income.getBillSelectionId() == WayBillTypeConstant.WITH_BILL) {	
						B_Income  += income.getIncomeAmount();
					}else if(income.getBillSelectionId() == WayBillTypeConstant.WITHOUT_BILL) {
						E_Income  += income.getIncomeAmount();
					}
				}
			}
		
			List<TripsheetDueAmountDto> dueAmountList = null;
			dueAmountList  = tripSheetService.getDueAmountListByTripsheetId(tripsheetId, userDetails.getCompany_id());
		
			if(dueAmountList !=null) {
				for(TripsheetDueAmountDto due:dueAmountList) {
					if(due.getBillSelectionId() !=null) {
						if(due.getBillSelectionId() == WayBillTypeConstant.WITH_BILL) {
							B_IncomeDue  += due.getDueAmount();
						}else if(due.getBillSelectionId() == WayBillTypeConstant.WITHOUT_BILL) {
							E_IncomeDue  += due.getDueAmount();
						}
					}
				}
			}
			B_IncomeBal  = B_Income - B_IncomeDue;
			E_IncomeBal  = E_Income - E_IncomeDue;
			
			Double TotalExpense = updatedtrip.getTripTotalexpense();
			if (updatedtrip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}

			HashMap<String, Double> 	cashbookEntries			=  new HashMap<String, Double>();
			
			if(TotalExpense >0){
				cashbookEntries.put("SSTS CASH BOOK_EXPENSE_1", TotalExpense);
				//cashbookEntries.put("JBL CASH BOOK_EXPENSE_1", TotalExpense);
				//new
				cashbookEntries.put("COMBINED CASH BOOK_EXPENSE_1", TotalExpense);     
			}
			if(B_IncomeBal >0){
				cashbookEntries.put("SSTS CASH BOOK_INCOME_1", B_IncomeBal);
				//cashbookEntries.put("JBL CASH BOOK_INCOME _1", B_IncomeBal);
				//new
				cashbookEntries.put("COMBINED CASH BOOK_INCOME_1", B_IncomeBal);
			}
			if(E_IncomeBal >0){
				//cashbookEntries.put("JBL CASH BOOK_INCOME_2", E_IncomeBal);{
			   	//new
				cashbookEntries.put("JBL CASH BOOK_INCOME_2", E_IncomeBal);
			    cashbookEntries.put("COMBINED CASH BOOK_INCOME_2", E_IncomeBal);
			}
			
			if(cashbookEntries.isEmpty() || cashbookEntries !=null) {
				String cashbookName= null;
				for (Map.Entry<String, Double> entry : cashbookEntries.entrySet()) {
					
					cashbookName = entry.getKey().split("\\_")[0];
					cashbook = cashbookbl.PrepareCashBook(entry.getValue(),tripcloseDate,updatedtrip.getTripFristDriverID(),companyId);
					
					try {
						saveDriverBalanceInCashBook= true;
						cashbook.setCASH_AMOUNT(entry.getValue());
						
						if(entry.getKey().split("\\_")[1].matches("EXPENSE")) {
							cashbook.setPAYMENT_TYPE_ID(CashBookPaymentType.PAYMENT_TYPE_DEBIT);
						}else {
							cashbook.setPAYMENT_TYPE_ID(CashBookPaymentType.PAYMENT_TYPE_CREDIT);
						}
						
						if(entry.getKey().split("\\_")[2].equals("2")) {
							cashbook.setINCOME_TYPE("E_INCOME");
						}
						CashBookName	bookName =  cashbookNameService.getCashBookByName(cashbookName,companyId);
						cashbook.setCASH_BOOK_ID(bookName.getNAMEID());
						cashbook.setCASH_BOOK_NO(bookName.getCASHBOOK_NAME());
					 	cashbook.setCASH_VOUCHER_NO(bookVoucherSequenceCounterService.getCashVoucherNumberAndUpdateNext(cashbook.getCASH_BOOK_ID(),companyId));
					 	
					 	cashbook.setCASH_PAID_BY(userDetails.getEmail());
					 	cashbook.setCASH_REFERENCENO("<a href='/showVehicle?vid="+updatedtrip.getVid()+"'>"+vehicle.getVehicle_registration()+"</a><br><a href='/showTripSheet.in?tripSheetID=" + updatedtrip.getTripSheetID() + "'> TS - " + updatedtrip.getTripSheetNumber()+ " </a>");
					 	
					 	valueObject = CashBookService.saveCashBookMissingEntry(cashbook,file, saveDriverBalanceInCashBook);
					 	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		//cashbook entry end
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return valueObject;
	}
	
	public void sendDataToCargo(TripSheetDto updatedtrip) {
		
			JSONObject jsonPayload = new JSONObject();
			HashMap<String, Object>						configuration					= null;
			CustomUserDetails		userDetails		= null;
	        try {
	        	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id() , PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
	        	
	        	String apiUrl = (String)configuration.get("pushTripsheetStatusToIvCargoApiLink");
	        	
	             jsonPayload.put("tripsheetId", updatedtrip.getTripSheetID());
	             jsonPayload.put("tripsheetNumber", updatedtrip.getTripSheetNumber());
	             jsonPayload.put("status", updatedtrip.getTripStutesId());
	             jsonPayload.put("statusStr", TripSheetStatus.getTripSheetStatusName(updatedtrip.getTripStutesId()));
	             jsonPayload.put("vehicleNumber", updatedtrip.getVehicle_registration().replace("-", " "));
	             jsonPayload.put("tokenNumber", "afe80426b5fb9d637489gh6f2aa4ed89f50612832a");
	            
	            if(apiUrl!= null || apiUrl != "") {
	                HttpResponse<String>  response = Unirest.post(apiUrl)
		                    .header("Content-Type", "application/json")
		                    .body(jsonPayload)
		                    .asString();
	                
	                System.err.println("response -- "+ response.getBody());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
    }
	
	//new
	@Override
	public ValueObject saveTripsheetExpensesFromClosTripApi(ArrayList<ValueObject> expenseArray, ValueObject object)
	        throws Exception {
	    java.util.Date 			currentDateUpdate 			= new java.util.Date();
	    Timestamp 				toDate 						= new java.sql.Timestamp(currentDateUpdate.getTime());
	    MultipartFile[] 		expenseUploadFiles 			= null;
	    ArrayList<ValueObject> expenseDetails 				= expenseArray.stream()
	            .map(exp -> {
	                String expenseName = exp.keySet().toString().replaceAll("[\\[\\]]", "");

	                TripExpense expense = null;
					try {
						expense = ITripExpenseService.getTripExpenseByName(expenseName, object.getInt("companyId"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                
	                if (expense == null) {
	                    expense = createNewExpense(expenseName, object, toDate);
	                }
					ValueObject expenseObject = constructExpenseObject(expense, exp, object, expenseName);
					return expenseObject;
	            })
	            .collect(Collectors.toCollection(ArrayList::new));

	    object.put("isMultiple", true);
	    object.put("expenseDetails", expenseDetails);
	    saveTripSheetExpenseDetails(object, expenseUploadFiles);  
	  
	    return object;
	}
	
	// The other methods remain the same as before
	private TripExpense createNewExpense(String expenseName, ValueObject object, Timestamp toDate) {
	    TripExpense expense = new TripExpense();
	   try {
	    expense.setExpenseName(expenseName);
	    expense.setCreated(toDate);
	    expense.setLastupdated(toDate);
	    expense.setExpenseRemarks("Expense Created");
	    expense.setCompanyId(object.getInt("companyId"));
	    expense.setCreatedById(object.getLong("userId"));
	    expense.setLastModifiedById(object.getLong("userId"));
	    return tripExpenseService.saveTripExpenses(expense);
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
	    return expense;
	}

	private ValueObject constructExpenseObject(TripExpense expense, ValueObject exp, ValueObject object, String expenseName) {
	    ValueObject expenseObject = new ValueObject();
	    try {
		    expenseObject.put("expenseNameId", expense.getExpenseID());
		    expenseObject.put("expenseAmount", Double.parseDouble(exp.get(expenseName).toString()));
		    expenseObject.put("expenseRef", "Expense Entry for vehicle " + object.getString("vehicleReg"));
		    expenseObject.put("tripSheetExpenseDocId", "");
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    return expenseObject;
	}
	
	public void sendExpenseDataToCargo(ValueObject object) {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		HashMap<String, Object> 	configuration						= null;
		HashMap<String, Object> 	companyConfiguration				= null;
		TripSheetDto				tripSheet					        = null;
		
        try {
        	companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
						
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object.put("userDetails", userDetails);
			configuration	 		 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			companyConfiguration 	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);;
			
			tripSheet		= tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
			
				List<TripSheetExpense> tripSheetExpenseList =  TripSheetExpenseRepository.findAllTripSheetExpense(tripsheetId,userDetails.getCompany_id());
				
				ValueObject expensValueObject = new ValueObject();
				
			 	for(TripSheetExpense tripExpense: tripSheetExpenseList) {
			 		
					expensValueObject.put("bankPaymentTypeId",PaymentTypeConstant.PAYMENT_TYPE_CASH);
					expensValueObject.put("userId", object.getLong("userId"));
					expensValueObject.put("companyId", object.getInt("companyId"));
					expensValueObject.put("moduleId", tripExpense.getTripExpenseID());
					expensValueObject.put("moduleNo", tripSheet.getTripSheetNumber());
					expensValueObject.put("moduleIdentifier", ModuleConstant.TRIP_EXPENSE);
					expensValueObject.put("amount", tripExpense.getExpenseAmount());
					expensValueObject.put("remark", "Driver Name "+tripSheet.getTripFristDriverName()+", Vehicle Number : "+tripSheet.getVehicle_registration()+" ");

				    cashPaymentService.saveCashPaymentSatement(expensValueObject);
			 	}		    
        
        } catch (Exception e) {
            e.printStackTrace();
        }

}

	
	public void removeExpenseDataToCargo(ValueObject object) {
		int							companyId							= 0;
		long						userId								= 0;
		long						tripsheetId							= 0;
		CustomUserDetails			userDetails							= null;
		HashMap<String, Object> 	configuration						= null;
		HashMap<String, Object> 	companyConfiguration				= null;
		TripSheetDto				tripSheet					        = null;
		long						tripExpenseId			        	= 0;
		
        try {
        	companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			tripsheetId 			= object.getLong("tripsheetId");
			tripExpenseId 			= object.getLong("tripExpenseID");
						
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object.put("userDetails", userDetails);
			configuration	 		 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			companyConfiguration 	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);;
			
			tripSheet		= tripSheetService.getTripSheetDetails(tripsheetId, userDetails);
			
			  bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(tripExpenseId,ModuleConstant.TRIP_EXPENSE, companyId, object.getLong("userId",0),false);
				    
        	} catch (Exception e) {
        		e.printStackTrace();
        }

}
	
}

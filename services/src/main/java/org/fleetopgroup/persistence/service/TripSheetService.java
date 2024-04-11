 package org.fleetopgroup.persistence.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fleetopgroup.constant.CashBookBalanceStatus;
import org.fleetopgroup.constant.CashBookPaymentType;
import org.fleetopgroup.constant.DriverAdvanceType;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.FuelConfigurationConstants;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.constant.TripCloseStatus;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetConfigurationConstant;
import org.fleetopgroup.constant.TripSheetFixedExpenseType;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.TripsheetDueAmountPaymentTypeConstant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleExtraDetailsConstant;
import org.fleetopgroup.constant.VehicleFuelType;
import org.fleetopgroup.constant.WayBillTypeConstant;
import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.LoadingSheetToTripSheetBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.bl.TripSheetMobileBL;
import org.fleetopgroup.persistence.bl.UreaEntriesBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.dao.CashBookVoucherSequenceCounterRepository;
import org.fleetopgroup.persistence.dao.CompanyMapperRepository;
import org.fleetopgroup.persistence.dao.DriverRepository;
import org.fleetopgroup.persistence.dao.FuelRepository;
import org.fleetopgroup.persistence.dao.IntangleTripDetailsRepository;
import org.fleetopgroup.persistence.dao.TollExpensesDetailsRepository;
import org.fleetopgroup.persistence.dao.TripRouteRepository;
import org.fleetopgroup.persistence.dao.TripSheetAdvanceRepository;
import org.fleetopgroup.persistence.dao.TripSheetExpenseRepository;
import org.fleetopgroup.persistence.dao.TripSheetExtraReceivedRepository;
import org.fleetopgroup.persistence.dao.TripSheetIncomeRepository;
import org.fleetopgroup.persistence.dao.TripSheetOptionsExtraRepository;
import org.fleetopgroup.persistence.dao.TripSheetRepository;
import org.fleetopgroup.persistence.dao.TripSheetRouteWiseWeightRepository;
import org.fleetopgroup.persistence.dao.TripsheetDueAmountPaymentRepository;
import org.fleetopgroup.persistence.dao.TripsheetDueAmountRepository;
import org.fleetopgroup.persistence.dao.VehicleExtraDetailsRepository;
import org.fleetopgroup.persistence.dto.CashBookDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.LoadingSheetReportDto;
import org.fleetopgroup.persistence.dto.LoadingSheetToTripSheetDto;
import org.fleetopgroup.persistence.dto.RouteWiseTripSheetWeightDto;
import org.fleetopgroup.persistence.dto.TripSheetAdvanceDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.TripSheetOptionsDto;
import org.fleetopgroup.persistence.dto.TripsheetDueAmountDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.CashBookSequenceCounter;
import org.fleetopgroup.persistence.model.CompanyMapper;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.IntangleTripDetails;
import org.fleetopgroup.persistence.model.LoadingSheetToTripSheet;
import org.fleetopgroup.persistence.model.RouteWiseTripSheetWeight;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.model.TripIncome;
import org.fleetopgroup.persistence.model.TripRoute;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetAdvance;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetExtraReceived;
import org.fleetopgroup.persistence.model.TripSheetHistory;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.model.TripSheetOptionsExtra;
import org.fleetopgroup.persistence.model.TripSheetSequenceCounter;
import org.fleetopgroup.persistence.model.TripsheetDueAmount;
import org.fleetopgroup.persistence.model.TripsheetDueAmountPayment;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleExtraDetails;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.report.dao.TripSheetDao;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookVoucherSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverHaltService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.ILHPVDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ILoadingSheetToTripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ITripCommentService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.ITripIncomeService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetMobileService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetToLhpvDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ITripsheetSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleMandatoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.body.MultipartBody;

@Service("TripSheetService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripSheetService implements ITripSheetService {
	
	@Autowired
	private TripSheetDao TripSheetDaoImpl;
	
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TripSheetRepository TripSheetDao;

	@Autowired
	private TripSheetAdvanceRepository TripSheetAdvanceRepository;

	@Autowired
	private TripSheetIncomeRepository TripSheetIncomeRepository;

	@Autowired
	private TripSheetExpenseRepository TripSheetExpenseRepository;
	
	@Autowired
	private TripSheetOptionsExtraRepository TripSheetOptionsExtraRepository;
	
	@Autowired
	private TripSheetExtraReceivedRepository TripSheetExtraReceivedRepository;
	
	@Autowired
	private TripRouteRepository tripRouteRepository;
	
	@Autowired
	private ICompanyConfigurationService companyConfigurationService;
	
	@Autowired
	private IntangleTripDetailsRepository intangleTripDetailsRepository;
	
	@Autowired
	private TripSheetRouteWiseWeightRepository TripSheetRouteWiseWeightRepository;
	
	@Autowired 
	private TripSheetRepository           tripSheetRepository;
	
	@Autowired	CashBookVoucherSequenceCounterRepository		cashBookVoucherSequenceCounterRepository;
	
	@Autowired IDriverSalaryAdvanceService 									DriverSalaryAdvanceService;
	@Autowired IVehicleGPSDetailsService									vehicleGPSDetailsService;
	@Autowired IVehicleOdometerHistoryService 								vehicleOdometerHistoryService;
	@Autowired IVehicleService												vehicleService;
	@Autowired IUserProfileService											userProfileService;
	@Autowired ILoadingSheetToTripSheetService								loadingSheetToTripSheetService;
	@Autowired IVehicleProfitAndLossService									vehicleProfitAndLossService;
	@Autowired VehicleProfitAndLossIncomeTxnCheckerService					vehicleProfitAndLossIncomeTxnCheckerService;
	@Autowired IVehicleProfitAndLossTxnCheckerService						vehicleProfitAndLossTxnCheckerService;
	@Autowired IVehicleMandatoryService 									vehicleMandatoryService;
	@Autowired IWorkOrdersService 											workOrdersService;
	@Autowired ILHPVDetailsService 											lhpvDetailsService;
	@Autowired ITripsheetSequenceService 									tripsheetSequenceService;
	@Autowired IDriverService 												driverService;
	@Autowired ITripRouteService 											tripRouteService;
	@Autowired ITripSheetToLhpvDetailsService 								tripSheetToLhpvDetailsService;
	@Autowired IServiceReminderService 										serviceReminderService;
	@Autowired ITollExpensesDetailsService 									tollExpensesDetailsService;
	@Autowired IDriverHaltService 											driverHaltService;
	@Autowired IFuelService 												fuelService;
	@Autowired IUreaEntriesService 											ureaEntriesService;
	@Autowired ITripCommentService 											tripCommentService;
	@Autowired VehicleExtraDetailsRepository 								vehicleExtraDetailsRepository;
	@Autowired DriverRepository 											driverRepository;
	@Autowired TripsheetDueAmountRepository 								tripsheetDueAmountRepository;
	@Autowired TripsheetDueAmountPaymentRepository 							tripsheetDueAmountPaymentRepository;
	@Autowired ITripExpenseService 											tripExpenseService;
	@Autowired ITripSheetMobileService 										tripSheetMobileService;
	@Autowired ITripIncomeService 											tripIncomeService;
	@Autowired ITripSheetService 											tripSheetService;
	@Autowired ITripSheetHistoryService 									tripSheetHistoryService; 
	@Autowired private ICashBookNameService									cashbookNameService;
	@Autowired private ICashBookService 									CashBookService;
	@Autowired private ICashBookVoucherSequenceCounterService				bookVoucherSequenceCounterService;
	@Autowired private CompanyMapperRepository				                companyMapperRepo;
	@Autowired private FuelRepository				                        fuelRepository;
	@Autowired private TollExpensesDetailsRepository				        tollExpensesDetailsRepository;
	@Autowired private HttpServletRequest					request;

	
	private static final int 			PAGE_SIZE 					= 10;
	private static final int 			PAGE_SIZE_TWENTY 			= 20;

	SimpleDateFormat 					dateFormat_Name 			= new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat 					dateFormat		 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 					dateFormat2		 			= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat					tallyFormat					= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat 					dateTimeFormat 				= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat 					dateTimeFormat2 			= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat 					displayFormat 				= new SimpleDateFormat("HH:mm");
    SimpleDateFormat 					parseFormat 				= new SimpleDateFormat("hh:mm a");
	
	LoadingSheetToTripSheetBL			loadingSheetToTripSheetBL	= new LoadingSheetToTripSheetBL();
	VehicleProfitAndLossTxnCheckerBL	txnCheckerBL				= new VehicleProfitAndLossTxnCheckerBL();
	TripSheetBL							tripSheetBL					= new TripSheetBL();
	VehicleOdometerHistoryBL			vehicleOdometerHistoryBL	= new VehicleOdometerHistoryBL();
	VehicleBL							vehicleBL					= new VehicleBL();
	FuelBL								fuelBL						= new FuelBL();
	UreaEntriesBL						ureaEntriesBL				= new UreaEntriesBL();
	DriverBL 							DBL 						= new DriverBL();
	DecimalFormat 						toFixedTwo					= new DecimalFormat("##.##");
	DecimalFormat	 					amountFormatDF 				= new DecimalFormat("##,##,##0");
	TripSheetMobileBL					tripSheetMobileBL			= new TripSheetMobileBL();
	CashBookBL  						cashbookbl   			    = new  CashBookBL();
	
	public static final long TRIP_SHEET_DEFALAT_VALUE 		= 0L;
	public static final Integer TRIP_SHEET_DEFALAT_ZERO	 	= 0;
	public static final Double TRIP_SHEET_DEFALAT_AMOUNT 	= 0.0;

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

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public TripSheet addTripSheet(TripSheet TripSheet) throws Exception {
		TripSheet 	tripSheet = null;
		try {
			tripSheet = TripSheetDao.save(TripSheet);
			return tripSheet;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTripSheetAdvance(TripSheetAdvance TripSheetAdvance) throws Exception {

		TripSheetAdvanceRepository.save(TripSheetAdvance);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public TripSheetExpense addTripSheetExpense(TripSheetExpense TripSheetExpense) throws Exception {

		return TripSheetExpenseRepository.save(TripSheetExpense);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTripSheetRouteWiseWeight(RouteWiseTripSheetWeight RouteWiseTripSheetWeight) throws Exception {
		
		TripSheetRouteWiseWeightRepository.save(RouteWiseTripSheetWeight);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTripSheetExtraOption(TripSheetOptionsExtra tripExtraOptions) throws Exception {
		TripSheetOptionsExtraRepository.save(tripExtraOptions);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTripSheetExtraOptionReceived(TripSheetExtraReceived tripSheetExtraReceived) throws Exception {
		TripSheetExtraReceivedRepository.save(tripSheetExtraReceived);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTripSheetIncome(TripSheetIncome TripSheetIncome) throws Exception {

		TripSheetIncomeRepository.save(TripSheetIncome);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateTripSheet(TripSheet TripSheet) throws Exception {
		TripSheetDao.save(TripSheet);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateTripSheetAdvance(TripSheetAdvance tripSheetAdvance) throws Exception {

		TripSheetAdvanceRepository.save(tripSheetAdvance);
	}

	@Transactional
	public TripSheet getTripSheet(Long TripSheet_id) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return TripSheetDao.getTripSheet(TripSheet_id, userDetails.getCompany_id());
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TripSheet getTripSheetData(Long TripSheet_id, int compId) throws Exception {
		
		return TripSheetDao.getTripSheet(TripSheet_id, compId);
	}
	
	@Override
	public TripSheet getTripSheetDetails(Long TripSheet_id) throws Exception {
		
		return TripSheetDao.getTripSheetDetails(TripSheet_id);
	}

	@Override
	public TripSheetDto getTripSheetDetails(Long TripSheet_id, CustomUserDetails userDetails) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
						+ " TR.routeName, TR.routeApproximateKM, TR.routrAttendance, TR.routeTotalLiter, T.tripOpenDate, T.closetripDate,"
						+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripClosingKMStatusId, T.tripFristDriverID, D1.driver_empnumber, D1.driver_firstname,"
						+ " D1.driver_mobnumber, T.tripSecDriverID, D2.driver_empnumber, D2.driver_firstname,  D2.driver_mobnumber, T.tripCleanerID, D3.driver_empnumber, D3.driver_firstname, "
						+ " D3.driver_mobnumber, T.tripTotalAdvance, T.tripTotalexpense, T.tripTotalincome, T.closeTripStatusId, D.driver_firstname,"
						+ " T.closeTripAmount, T.closeTripReference, U.firstName, U.lastName , T.closeACCtripDate, T.closeACCTripAmount, T.closeACCTripReference,"
						+ " T.tripCommentTotal, T.tripStutesId, U2.email, B.branch_name, U3.firstName, B2.branch_name, U4.email, B3.branch_name,"
						+ " T.dispatchedByTime, T.closedByTime, T.acclosedByTime, U5.email, T.created, U6.email, T.lastupdated, T.routeName, TR.routeRemarks, T.subRoute, "
						+ " T.tripGpsOpeningKM, T.tripGpsClosingKM, V.vehicleGPSId, T.gpsOpeningLocation, T.gpsCloseLocation, T.gpsTripUsageKM, T.loadTypeId, T.noOfPOD, T.receivedPOD, "
						+ " T.tripFristDriverRoutePoint, T.tripSecDriverRoutePoint, T.tripCleanerRoutePoint, LTM.loadTypesId, LTM.loadTypeName, T.tripStartDiesel, T.tripEndDiesel,"
						+ " T.voucherDate, TC.companyName, T.tallyCompanyId, V.vehicle_ExpectedOdameter, U5.firstName, D1.driver_fathername , D1.driver_Lastname,  D2.driver_fathername , D2.driver_Lastname, T.tripSheetDocument, T.tripSheetDocumentId, D3.driver_fathername "
						+ ", D3.driver_Lastname ,D.driver_Lastname,D.driver_fathername,T.meterNotWorking ,T.driverBalance "
						+ " FROM TripSheet T " 
						+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
						+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
						+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
						+ " LEFT JOIN Driver D ON D.driver_id = T.closeTripNameById AND T.closeTripStatusId = "+TripSheetDto.TRIP_PAY_TO_OFFICE+" "
						+ " LEFT JOIN User U On U.id = T.closeACCTripNameById"
						+ " LEFT JOIN User U2 On U2.id = T.dispatchedById"
						+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
						+ " LEFT JOIN User U3 On U3.id = T.closedById"
						+ " LEFT JOIN Branch AS B2 ON B2.branch_id = T.closedLocationId "
						+ " LEFT JOIN User U4 On U4.id = T.acclosedById"
						+ " LEFT JOIN Branch AS B3 ON B3.branch_id = T.acclosedLocationId "
						+ " LEFT JOIN User U5 On U5.id = T.createdById"
						+ " LEFT JOIN User U6 On U6.id = T.lastModifiedById"
						+ " LEFT JOIN LoadTypeMaster AS LTM ON LTM.loadTypesId = T.loadTypeId"
						+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = T.tallyCompanyId "
						+ " WHERE T.tripSheetID =:id AND T.companyId =:companyId AND T.markForDelete = 0");

		query.setParameter("id", TripSheet_id);
		query.setParameter("companyId", userDetails.getCompany_id());
		
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		
		TripSheetDto select;
		if (vehicle != null) {
			select = new TripSheetDto();
			select.setTripSheetID((Long) vehicle[0]);
			select.setTripSheetNumber((Long) vehicle[1]);
			select.setVid((Integer) vehicle[2]);
			select.setVehicle_registration((String) vehicle[3]);
			select.setVehicle_Group((String) vehicle[4]);
			select.setVehicleGroupId((long) vehicle[5]);
			select.setRouteID((Integer) vehicle[6]);
			select.setRouteName((String) vehicle[7]);
			select.setRouteApproximateKM((Integer) vehicle[8]);
			select.setRouteAttendancePoint((Double) vehicle[9]);
			select.setRouteTotalLiter((Double) vehicle[10]);
			select.setTripOpenDateOn((java.util.Date) vehicle[11]);
			select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[11]));
			select.setTripOpenDateStr(dateFormat2.format((java.util.Date) vehicle[11]));
			select.setClosetripDateOn((java.util.Date) vehicle[12]);
			select.setTripEndDateTimeStr(dateFormat_Name.format((java.util.Date) vehicle[12]));
			select.setTripSheetCloseDateStr(dateFormat.format((java.util.Date) vehicle[12]));
			select.setTripBookref((String) vehicle[13]);
			select.setTripOpeningKM((Integer) vehicle[14]);
			select.setTripClosingKM((Integer) vehicle[15]);
			select.setTripUsageKM((Integer) vehicle[16]);
			select.setTripClosingKMStatusId((short) vehicle[17]);
			select.setTripFristDriverID((int) vehicle[18]);
			select.setTripFristDriverName((String) vehicle[19] +" "+ (String) vehicle[20] );
			select.setTripFristDriverMobile((String) vehicle[21]);
			select.setTripSecDriverID((int) vehicle[22]);
			if(vehicle[23] != null || vehicle[24] != null ) {
				select.setTripSecDriverName((String) vehicle[23] +" "+ (String) vehicle[24]);
			}else {
				select.setTripSecDriverName("");
			}
			select.setTripSecDriverMobile((String) vehicle[25]);
			select.setTripCleanerID((int) vehicle[26]);
			if(vehicle[27] != null || vehicle[28] != null) {
			select.setTripCleanerName((String) vehicle[27] +"  "+ (String) vehicle[28]);
			}else {
				select.setTripCleanerName("");
			}
			select.setTripCleanerMobile((String) vehicle[29]);
			select.setTripTotalAdvance((Double) vehicle[30]);
			select.setTripTotalexpense((Double) vehicle[31]);
			select.setTripTotalincome((Double) vehicle[32]);
			select.setCloseTripStatusId((short) vehicle[33]);
			select.setCloseTripNameBy((String) vehicle[34]);
			select.setCloseTripAmount((Double) vehicle[35]);
			select.setCloseTripReference((String) vehicle[36]);
			select.setCloseACCTripNameBy((String) vehicle[37] + " " + (String) vehicle[38]);
			select.setCloseACCtripDate((java.util.Date) vehicle[39]);
			select.setCloseACCTripAmount((Double) vehicle[40]);
			select.setCloseACCTripReference((String) vehicle[41]);
			select.setTripCommentTotal((Integer) vehicle[42]);
			select.setTripStutesId((short) vehicle[43]);
			select.setDispatchedBy((String) vehicle[44]);
			select.setDispatchedLocation((String) vehicle[45]);
			select.setClosedBy((String) vehicle[46]);
			select.setCloesdLocation((String) vehicle[47]);
			select.setAcclosedBy((String) vehicle[48]);
			select.setAccloesdLocation((String) vehicle[49]);
			select.setDispatchedByTimeOn((java.util.Date) vehicle[50]);
			select.setClosedByTimeOn((java.util.Date) vehicle[51]);
			select.setAcclosedByTimeOn((java.util.Date) vehicle[52]);
			select.setCreatedBy((String) vehicle[53]);
			select.setCreatedOn((java.util.Date) vehicle[54]);
			select.setLastModifiedBy((String) vehicle[55]);
			select.setLastupdatedOn((java.util.Date) vehicle[56]);
			
			if(select.getRouteName() == null || select.getRouteName().equals("")) {
				select.setRouteName((String) vehicle[57]);
			}
			select.setRouteRemark((String) vehicle[58]);
			select.setSubRouteName((String) vehicle[59]);
			//select.setSubRouteName((String) vehicle[60]);
			select.setTripGpsOpeningKM((Double) vehicle[60]);
			select.setTripGpsClosingKM((Double) vehicle[61]);
			select.setVehicleGPSId((String) vehicle[62]);
			select.setGpsOpeningLocation((String) vehicle[63]);
			select.setGpsCloseLocation((String) vehicle[64]);
			if(vehicle[65] != null)
				select.setGpsTripUsageKM((Double) vehicle[65]);
			if(vehicle[66] != null)
				select.setLoadTypeId((short) vehicle[66]);
			
			if(select.getCloseTripStatusId() == TripSheetDto.TRIP_PAY_TO_DRIVER) {
				select.setCloseTripNameBy(TripSheetDto.TRIP_PAY_TO_OFFICE_NAME);
			}
			
			if(vehicle[67] != null)
			select.setNoOfPOD((int) vehicle[67]);
			if(vehicle[68] != null)
			select.setReceivedPOD((int) vehicle[68]);
			if(vehicle[69] != null)
			select.setTripFristDriverRoutePoint((double) vehicle[69]);
			if(vehicle[70] != null)
			select.setTripSecDriverRoutePoint((double) vehicle[70]);
			if(vehicle[71] != null)
			select.setTripCleanerRoutePoint((double) vehicle[71]);
			if(vehicle[72] != null)
			select.setLoadTypesId((Long) vehicle[72] );			
			if(vehicle[73]!=null)
			select.setLoadTypeName((String) vehicle[73]);
			if(vehicle[74] != null)
				select.setTripStartDiesel((Double) vehicle[74]);			
			if(vehicle[75]!=null)
				select.setTripEndDiesel((Double) vehicle[75]);
			if(vehicle[76] !=null)
				select.setVoucherDateStr(dateFormat.format((Timestamp)vehicle[76]));
			if(vehicle[77] != null)
				select.setTallyCompanyName((String) vehicle[77]);
			if(vehicle[78] != null)
				select.setTallyCompanyId((Long) vehicle[78]);
			if(vehicle[79] != null)
				select.setVehicle_ExpectedOdameter((Integer)vehicle[79] );
			
				select.setTripCreatedBy((String) vehicle[80]);
			if(vehicle[81] != null) {
				select.setTripFristDriverFatherName((String) vehicle[81]);
			}else {
				select.setTripFristDriverFatherName(" ");
			}
			if(vehicle[82] != null) {
				select.setTripFristDriverLastName((String) vehicle[82]);
			}else {
				select.setTripFristDriverLastName(" ");
			}
			if(vehicle[83] != null) {
				select.setTripSecDriverFatherName((String) vehicle[83]);
			}else {
				select.setTripSecDriverFatherName(" ");
			}
			if(vehicle[84] != null) {
				select.setTripSecDriverLastName((String) vehicle[84]);
			}else {
				select.setTripSecDriverLastName(" ");
			}
				
			if(vehicle[85] != null) {
				select.setTripSheetDocument((boolean) vehicle[85]);
				select.setTripSheetDocumentId((Long) vehicle[86]);
			}
				
				
			if(vehicle[87] != null) {
				select.setTripCleanerMidleName((String) vehicle[87]);
				}else {
					select.setTripCleanerMidleName(" ");
				}
			if(vehicle[88] != null) {
				select.setTripCleanerLastName((String) vehicle[88]);
				}else {
					select.setTripCleanerLastName(" ");
				}
			if(vehicle[89] != null)
			select.setCloseTripNameBy(select.getCloseTripNameBy()+" "+ vehicle[89]);
			
			if(vehicle[90] != null)
			select.setCloseTripNameBy(select.getCloseTripNameBy()+" - "+ vehicle[90]);
			select.setMeterNotWorking((boolean) vehicle[91]);
			select.setDriverBalance((Double) vehicle[92]);
			
		} else {
			return null;
		}

		return select;
	}

	@Transactional
	public TripSheet getTripSheet_ID_FUEL_SHOW_NUMBER(Long TripSheet_id) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query query = entityManager.createQuery(
				"SELECT T.tripSheetID, T.tripSheetNumber FROM TripSheet T WHERE T.tripSheetID =:id AND T.companyId =:companyId AND T.markForDelete = 0");

		query.setParameter("id", TripSheet_id);
		query.setParameter("companyId", userDetails.getCompany_id());
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripSheet select;
		if (vehicle != null) {
			select = new TripSheet();
			select.setTripSheetID((Long) vehicle[0]);
			select.setTripSheetNumber((Long) vehicle[1]);

		} else {
			return null;
		}
		return select;
	}

	@Transactional
	public TripSheetAdvance getTripSheetAdvance(Long TripSheet_Advanceid, Integer companyId) throws Exception {

		return TripSheetAdvanceRepository.getTripSheetAdvance(TripSheet_Advanceid, companyId);
	}

	@Transactional
	public TripSheetExpense getTripSheetExpense(Long TripSheet_Expenseid, Integer companyId) throws Exception {

		return TripSheetExpenseRepository.getTripSheetExpense(TripSheet_Expenseid, companyId);
	}
	
	@Override
	public TripSheetExpense getTripSheetExpenseByFuelId(Long fuelId) throws Exception {
		
		return TripSheetExpenseRepository.getTripSheetExpenseByFuelId(fuelId);
	}
	
	@Transactional
	public TripSheetOptionsExtra getTripSheetExtra(Long tripExtraID, Integer companyId) throws Exception {

		return TripSheetOptionsExtraRepository.getTripSheetExtra(tripExtraID, companyId);
	}

	@Transactional
	public TripSheetIncome getTripSheetIncome(Long TripSheet_Incomeid, Integer companyId) throws Exception {

		return TripSheetIncomeRepository.getTripSheetIncome(TripSheet_Incomeid, companyId);
	}

	@Transactional
	public List<TripSheet> findAllTripSheetList() throws Exception {

		return null;
	}
	
	
	

	@Transactional
	public List<TripSheet> listTripSheet(CustomUserDetails userDetails) throws Exception {
		TypedQuery<TripSheet> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery("FROM TripSheet as v " + " WHERE v.companyId = "
					+ userDetails.getCompany_id() + " AND v.markForDelete = 0" + " ORDER BY v.tripSheetID desc",
					TripSheet.class);
		} else {
			query = entityManager.createQuery("FROM TripSheet as v "
					+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " WHERE v.companyId = " + userDetails.getCompany_id()
					+ " AND v.markForDelete = 0" + " ORDER BY v.tripSheetID desc", TripSheet.class);
		}
		query.setFirstResult(0);
		query.setMaxResults(5);
		return query.getResultList();
	}

	@Transactional
	public List<TripSheetDto> listTripSheet(String ReportQuery) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {

			// return TripSheetDao.listTodayTripSheet(userDetails.getId(),
			// userDetails.getCompany_id());
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount , D1.driver_firstname, D2.driver_firstname,"
								+ " D3.driver_firstname, U.email, B.branch_name,T.dispatchedByTime, T.routeName, D1.driver_Lastname,"
								+ " D2.driver_Lastname, FD.driver_empnumber, SD.driver_empnumber,D3.driver_Lastname, CD.driver_empnumber  FROM TripSheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " LEFT JOIN User U ON U.id = T.dispatchedById"
								+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
								+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
								+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
								+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
								+ " LEFT JOIN Driver AS FD ON FD.driver_id = T.tripFristDriverID "
								+ " LEFT JOIN Driver AS SD ON SD.driver_id = T.tripSecDriverID "
								+ " LEFT JOIN Driver AS CD ON SD.driver_id = T.tripCleanerID "
								+ " where ( T.markForDelete=0 " + ReportQuery + " ) AND T.companyId = "
								+ userDetails.getCompany_id() + " ORDER BY T.tripSheetID desc",
						Object[].class);

			} else {
				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount , D1.driver_firstname, D2.driver_firstname,"
								+ " D3.driver_firstname, U.email, B.branch_name, T.dispatchedByTime, T.routeName, D1.driver_Lastname,"
								+ " D2.driver_Lastname, FD.driver_empnumber, SD.driver_empnumber, D3.driver_Lastname,CD.driver_empnumber  FROM TripSheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " LEFT JOIN User U ON U.id = T.dispatchedById"
								+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
								+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
								+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
								+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
								+ " LEFT JOIN Driver AS FD ON FD.driver_id = T.tripFristDriverID "
								+ " LEFT JOIN Driver AS SD ON SD.driver_id = T.tripSecDriverID "
								+ " LEFT JOIN Driver AS CD ON SD.driver_id = T.tripCleanerID "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " where ( T.markForDelete=0 " + ReportQuery
								+ " ) AND T.companyId = " + userDetails.getCompany_id()
								+ " ORDER BY T.tripSheetID desc",
						Object[].class);
			}
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
					select.setTripOpenDateOn((java.util.Date) vehicle[8]);
					select.setClosetripDateOn((java.util.Date) vehicle[9]);
					select.setTripBookref((String) vehicle[10]);
					select.setTripOpeningKM((Integer) vehicle[11]);
					select.setTripClosingKM((Integer) vehicle[12]);
					select.setTripUsageKM((Integer) vehicle[13]);
					select.setTripStutesId((short) vehicle[14]);
					select.setTripTotalAdvance((Double) vehicle[15]);
					select.setTripTotalexpense((Double) vehicle[16]);
					select.setTripTotalincome((Double) vehicle[17]);
					select.setCloseTripAmount((Double) vehicle[18]);
					select.setCloseACCTripAmount((Double) vehicle[19]);
					select.setTripFristDriverName((String) vehicle[20]);
					select.setTripSecDriverName((String) vehicle[21]);
					select.setTripCleanerName((String) vehicle[22]);
					select.setDispatchedBy((String) vehicle[23]);
					select.setDispatchedLocation((String) vehicle[24]);
					select.setDispatchedByTimeOn((java.util.Date) vehicle[25]);
					
					if(select.getRouteName() == null || select.getRouteName() == "") {
						select.setRouteName((String) vehicle[26]);
					}
					
					select.setTripFristDriverLastName((String) vehicle[27]);
					select.setTripSecDriverLastName((String) vehicle[28]);
					
					select.setFDriverEmpNo((String) vehicle[29]);
					select.setSDriverEmpNo((String) vehicle[30]);
					
					
					String empNo1;
					if(vehicle[29]!=null)
						empNo1 = (String) vehicle[29];
					else 
						empNo1 = "";
					String FnameD1;
					if(vehicle[20]!=null)
						FnameD1 = (String) vehicle[20];
					else
						FnameD1 = "";
					String LnameD1;
					if(vehicle[27] !=null)
						LnameD1  = (String) vehicle[27];
					else
						LnameD1 = " ";
					
					
					if(!empNo1.isEmpty() && !FnameD1.isEmpty() && !LnameD1.isEmpty())
						select.setFDdetails(empNo1+"_"+ FnameD1+"_"+LnameD1);
					else if(!empNo1.isEmpty() && !FnameD1.isEmpty() && LnameD1.isEmpty())
						select.setFDdetails(empNo1+"_"+ FnameD1);
					else if(empNo1.isEmpty() && !FnameD1.isEmpty() && !LnameD1.isEmpty())
						select.setFDdetails(FnameD1+"_"+LnameD1);
					else if(!empNo1.isEmpty() && FnameD1.isEmpty() && !LnameD1.isEmpty())
						select.setFDdetails(empNo1+"_"+LnameD1);
					else
						select.setFDdetails("");
					
					
					String empNo2;
					if(vehicle[30]!=null)
						empNo2 = (String) vehicle[30];
					else
						empNo2 = "";
					String FnameD2;
					if(vehicle[21]!=null)
						FnameD2 = (String) vehicle[21];
					else
						FnameD2 = "";
					String LnameD2; 
					if(vehicle[28]!=null)
						LnameD2 = (String) vehicle[28];
					else
						LnameD2="";
					
					if(!empNo2.isEmpty() && !FnameD2.isEmpty() && !LnameD2.isEmpty())
						select.setSDdetails(empNo2+"_"+FnameD2+"_"+LnameD2);
					else if(!empNo2.isEmpty() && !FnameD2.isEmpty() && LnameD2.isEmpty())
						select.setSDdetails(empNo2+"_"+FnameD2);
					else if(!empNo2.isEmpty() && FnameD2.isEmpty() && !LnameD2.isEmpty())
						select.setSDdetails(empNo2+"_"+LnameD2);
					else if(empNo2.isEmpty() && !FnameD2.isEmpty() && !LnameD2.isEmpty())
						select.setSDdetails(FnameD2+"_"+LnameD2);
					else
						select.setSDdetails("");
					
					
					String emp3;
					if(vehicle[32] != null)
						emp3 = (String) vehicle[32];
					else
						emp3="";
					String Fname3;
					if(vehicle[22] != null)
						Fname3= (String) vehicle[22];
					else
						Fname3="";
					String Lname3;
					if(vehicle[31] != null)
						Lname3= (String)vehicle[31];
					else
						Lname3="";
					if(!emp3.isEmpty() && !Fname3.isEmpty() && !Lname3.isEmpty())
						select.setClenerDetails(emp3+"_"+Fname3+"_"+Lname3);
					else if(!emp3.isEmpty() && !Fname3.isEmpty() && Lname3.isEmpty())
						select.setClenerDetails(emp3+"_"+Fname3);
					else if(!emp3.isEmpty() && Fname3.isEmpty() && !Lname3.isEmpty())
						select.setClenerDetails(emp3+"_"+Lname3);
					else if(emp3.isEmpty() && !Fname3.isEmpty() && !Lname3.isEmpty())
						select.setClenerDetails(Fname3+"_"+Lname3);
					else
						select.setClenerDetails("");
					
					Dtos.add(select);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	public List<TripSheetDto> list_Report_TripSheet_TripAdvance(String ReportQuery, CustomUserDetails userDetails)
			throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"Select T.tripSheetID, V.vehicle_registration, D1.driver_firstname, D1.driver_mobnumber, D2.driver_firstname, D2.driver_mobnumber, D3.driver_firstname,"
						+ " D3.driver_mobnumber, TR.routeName, TA.AdvanceAmount, TA.created, T.tripSheetNumber, T.routeName,"
						+ "D1.driver_Lastname,D1.driver_fathername,D2.driver_Lastname,D2.driver_fathername "
						+ " From TripSheet AS T "
						+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " INNER JOIN TripSheetAdvance AS TA ON TA.tripsheet.tripSheetID =T.tripSheetID "
						+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
						+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
						+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
						+ " where "
						+ ReportQuery + " AND T.companyId = " + userDetails.getCompany_id()
						+ " AND T.markForDelete = 0 AND TA.markForDelete = 0 ORDER BY T.tripSheetID asc",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setVehicle_registration((String) result[1]);
				list.setTripFristDriverName((String) result[2]);
				list.setTripFristDriverMobile((String) result[3]);

				list.setTripSecDriverName((String) result[4]);
				list.setTripSecDriverMobile((String) result[5]);
				list.setTripCleanerName((String) result[6]);
				list.setTripCleanerMobile((String) result[7]);

				list.setRouteName((String) result[8]);
				if(result[9] != null)
				list.setTripTotalAdvance(Double.parseDouble(toFixedTwo.format(result[9])));
				
				list.setTripOpenDateOn((java.util.Date) result[10]);
				list.setTripSheetNumber((Long) result[11]);

				if(list.getRouteName() == null || list.getRouteName() == "") {
					list.setRouteName((String) result[12]);
				}
				
				if(result[13]!= null &&!((String)result[13]).trim().equals(""))
					list.setTripFristDriverName(list.getTripFristDriverName()+" "+ result[13]);	
				
				if(result[14]!= null &&!((String)result[14]).trim().equals(""))
					list.setTripFristDriverName(list.getTripFristDriverName()+"-"+ result[14]);	
				
				if(result[15]!= null &&!((String)result[15]).trim().equals(""))
					list.setTripSecDriverName(list.getTripSecDriverName()+" "+ result[15]);
				
				if(result[16]!= null &&!((String)result[16]).trim().equals(""))
					list.setTripSecDriverName(list.getTripSecDriverName()+"-"+ result[16]);
				Dtos.add(list);
			}

		}
		return Dtos;

	}

	@Transactional
	public List<TripSheetDto> list_Report_Daily_TripSheet_Report(String ReportQuery, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"Select T.tripSheetID, V.vehicle_registration, T.tripOpenDate, T.closetripDate, "
						+ " TR.routeName, T.tripTotalAdvance, T.tripTotalexpense,"
						+ " T.tripStutesId, T.tripSheetNumber, T.routeName From TripSheet AS T"
						+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ "  where " + ReportQuery
						+ " AND T.companyId = " + companyId + " AND T.markForDelete = 0 ORDER BY T.tripSheetID asc",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setVehicle_registration((String) result[1]);
				if (result[2] != null) {
					list.setTripOpenDateOn((java.util.Date) result[2]);
				}
				if (result[3] != null) {
					list.setClosetripDateOn((java.util.Date) result[3]);
				}

				list.setRouteName((String) result[4]);
				if(result[5] != null)
				list.setTripTotalAdvance(Double.parseDouble(toFixedTwo.format(result[5])));
				if(result[6] != null)
				list.setTripTotalexpense(Double.parseDouble(toFixedTwo.format(result[6])));
				
				list.setTripStutes(TripSheetStatus.getTripSheetStatusName((short) result[7]));
				list.setTripSheetNumber((Long) result[8]);
				
				if(list.getRouteName() == null || list.getRouteName() == "") {
					list.setRouteName((String) result[9]);
				}

				Dtos.add(list);
			}

		}
		return Dtos;

	}

	@Transactional
	public List<TripSheetDto> list_Report_TripSheet_DATE(String TRIP_DATE_RANGE, String TripsheetGroup,
			Integer companyId) throws Exception {
		TypedQuery<Object[]> query = entityManager.createQuery(
				"Select T.tripSheetID, V.vehicle_registration, D1.driver_firstname, D1.driver_mobnumber, D2.driver_firstname, D2.driver_mobnumber, D3.driver_firstname,"
						+ " D3.driver_mobnumber, TR.routeName, T.tripOpenDate, T.tripSheetNumber, T.routeName, LTM.loadTypesId, LTM.loadTypeName,"
						+ " D1.driver_Lastname,D1.driver_fathername,D2.driver_Lastname,D2.driver_fathername,VG.vGroup  From TripSheet AS T "
						+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
						+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
						+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "						
						+ " LEFT JOIN LoadTypeMaster AS LTM ON  LTM.loadTypesId = T.loadTypeId "						
						+ " where ( T.tripOpenDate between '"
						+ TRIP_DATE_RANGE + "' AND '" + TRIP_DATE_RANGE + "' " + TripsheetGroup + ") AND T.companyId = "
						+ companyId + " AND T.markForDelete = 0 ORDER BY T.tripSheetID asc",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setVehicle_registration((String) result[1]);
				list.setTripFristDriverName((String) result[2]);
				list.setTripFristDriverMobile((String) result[3]);

				list.setTripSecDriverName((String) result[4]);
				list.setTripSecDriverMobile((String) result[5]);
				list.setTripCleanerName((String) result[6]);
				list.setTripCleanerMobile((String) result[7]);

				list.setRouteName((String) result[8]);
				list.setTripOpenDateOn((java.util.Date) result[9]);
				list.setTripSheetNumber((Long) result[10]);

				if(list.getRouteName() == null || list.getRouteName() == "") {
					list.setRouteName((String) result[11]);
				}
				
				if((Long) result[12] != null)
				list.setLoadTypesId((Long) result[12]);
				if(result[13] != null)
				list.setLoadTypeName((String) result[13]);
				
				if(result[14]!= null &&!((String)result[14]).trim().equals(""))
					list.setTripFristDriverName(list.getTripFristDriverName()+" "+ result[14]);	
				
				if(result[15]!= null &&!((String)result[15]).trim().equals(""))
					list.setTripFristDriverName(list.getTripFristDriverName()+"-"+ result[15]);	
				
				if(result[16]!= null &&!((String)result[16]).trim().equals(""))
					list.setTripSecDriverName(list.getTripSecDriverName()+" "+ result[16]);
				
				if(result[17]!= null &&!((String)result[17]).trim().equals(""))
					list.setTripSecDriverName(list.getTripSecDriverName()+"-"+ result[17]);
				
				list.setVehicle_Group((String) result[18]);
				list.setTripSheetNumberStr("<a target=\"_blank\" style=\"color: blue; background: #ffc;\"  href=\"showTripSheet.in?tripSheetID="+list.getTripSheetID()+" \"> TS-"+list.getTripSheetNumber()+" </a>");
				Dtos.add(list);
			}

		}
		return Dtos;

	}

	@Transactional
	public List<Double> ReportTripAdvanceTotal(String ReportQuery) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Double> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery(
							"select sum(T.tripTotalAdvance) from TripSheet T"
									+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
									+ " where ( T.markForDelete=0 "
									+ ReportQuery + " ) AND T.companyId = " + userDetails.getCompany_id() + " ",
									Double.class);

		} else {
			query = entityManager.createQuery("select sum(T.tripTotalAdvance) from TripSheet T "
					+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + " " + " where ( T.markForDelete=0 " + ReportQuery + " ) AND T.companyId = "
					+ userDetails.getCompany_id() + " ", Double.class);
		}

		return query.getResultList();
	}

	@Transactional
	public List<Double> ReportTripExpenseTotal(String ReportQuery) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Double> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery(
							"select sum(T.tripTotalexpense) from TripSheet T" + " where ( T.markForDelete=0 "
									+ ReportQuery + " ) AND T.companyId = " + userDetails.getCompany_id() + " ",
									Double.class);
		} else {

			query = entityManager.createQuery("select sum(T.tripTotalexpense) from TripSheet T "
					+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + " " + " where ( T.markForDelete=0 " + ReportQuery + " ) AND T.companyId = "
					+ userDetails.getCompany_id() + " ", Double.class);
		}

		return query.getResultList();
	}

	@Transactional
	public List<Double> ReportTripIncomeTotal(String ReportQuery) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Double> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery(
							"select sum(T.tripTotalincome) from TripSheet T" + " where ( T.markForDelete=0 "
									+ ReportQuery + " ) AND T.companyId = " + userDetails.getCompany_id() + " ",
									Double.class);
		} else {

			query = entityManager.createQuery("select sum(T.tripTotalincome) from TripSheet T "
					+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + " " + " where ( T.markForDelete=0 " + ReportQuery + " ) AND T.companyId = "
					+ userDetails.getCompany_id() + " ", Double.class);
		}

		return query.getResultList();


	}

	@Transactional
	public List<Double> ReportTripAccBalanceTotal(String ReportQuery) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Double> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery(
							"select sum(T.closeACCTripAmount) from TripSheet T" + " where ( T.markForDelete=0 "
									+ ReportQuery + " ) AND T.companyId = " + userDetails.getCompany_id() + " ",
									Double.class);
		} else {

			query = entityManager.createQuery("select sum(T.closeACCTripAmount) from TripSheet T "
					+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + " " + " where ( T.markForDelete=0 " + ReportQuery + " ) AND T.companyId = "
					+ userDetails.getCompany_id() + " ", Double.class);
		}

		return query.getResultList();

	}

	@Transactional
	public List<TripSheetDto> listTodayTripSheet(String Today, CustomUserDetails userDetails) throws Exception {
		try {

			// return TripSheetDao.listTodayTripSheet(userDetails.getId(),
			// userDetails.getCompany_id());
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
								+ " TR.routeNo, TR.routeName,  T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount, T.routeName , T.dispatchedByTime "
								+ " FROM TripSheet T " 
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " WHERE T.companyId =:companyId AND T.markForDelete = 0 AND T.tripOpenDate= '" + Today
								+ "' ORDER BY T.tripSheetID desc",
								Object[].class);

			} else {
				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
								+ " TR.routeNo, TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount, T.routeName, T.dispatchedByTime "
								+ " FROM TripSheet T " 
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " WHERE T.companyId =:companyId AND T.markForDelete = 0 AND T.tripOpenDate= '" + Today
								+ "' ORDER BY T.tripSheetID desc",
								Object[].class);
			}
			query.setParameter("companyId", userDetails.getCompany_id());
			
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
					select.setRouteName((String) vehicle[7] + " "+ (String) vehicle[8]);
					select.setTripOpenDateOn((java.util.Date) vehicle[9]);
					select.setClosetripDateOn((java.util.Date) vehicle[10]);
					select.setTripBookref((String) vehicle[11]);
					select.setTripOpeningKM((Integer) vehicle[12]);
					select.setTripClosingKM((Integer) vehicle[13]);
					select.setTripUsageKM((Integer) vehicle[14]);
					select.setTripStutesId((short) vehicle[15]);
					if(vehicle[16] != null)
					select.setTripTotalAdvance(Double.parseDouble(toFixedTwo.format(vehicle[16])));
					if(vehicle[17] != null)
					select.setTripTotalexpense(Double.parseDouble(toFixedTwo.format(vehicle[17])));
					if(vehicle[18] != null)
					select.setTripTotalincome(Double.parseDouble(toFixedTwo.format(vehicle[18])));
					if(vehicle[19] != null)
					select.setCloseTripAmount(Double.parseDouble(toFixedTwo.format(vehicle[19])));
					if(vehicle[20] != null)
					select.setCloseACCTripAmount(Double.parseDouble(toFixedTwo.format(vehicle[20])));
					
					if(vehicle[8] == null || vehicle[8] == "") {
						select.setRouteName((String) vehicle[21]);
					}	
					if(vehicle[22] != null) {
						select.setDispatchedByTimeOn((java.util.Date)vehicle[22]);
					}
					
					Dtos.add(select);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public List<TripSheetDto> listTripSheetDispatch(CustomUserDetails userDetails) throws Exception {

		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			// return TripSheetDao.listTripSheetDispatch(userDetails.getCompany_id());
			query = entityManager.createQuery(
					"Select T.tripSheetID, T.tripSheetNumber, V.vehicle_registration, VG.vGroup, TR.routeNumber, TR.routeName, T.tripOpenDate, T.tripBookref, T.routeName"
							+ " From TripSheet AS T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " where  T.tripStutesId=1 AND T.companyId = " + userDetails.getCompany_id()
							+ " AND T.markForDelete = 0 ORDER BY T.tripSheetID desc",
							Object[].class);

		} else {
			// return TripSheetDao.listTripSheetDispatch(userDetails.getId(),
			// userDetails.getCompany_id());

			query = entityManager.createQuery(
					"Select T.tripSheetID, T.tripSheetNumber, V.vehicle_registration, VG.vGroup, TR.routeNumber, TR.routeName, T.tripOpenDate, T.tripBookref, T.routeName"
							+ " From TripSheet AS T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id ="
							+ userDetails.getId() + "" + " where  T.tripStutesId=1 AND T.companyId = "
							+ userDetails.getCompany_id() + " AND T.markForDelete = 0 ORDER BY T.tripSheetID desc",
							Object[].class);
		}
		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setTripSheetNumber((Long) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setVehicle_Group((String) result[3]);
				list.setRouteName((String) result[4]);
				if ((Date) result[5] != null)
					list.setTripOpenDateOn((Date) result[5]);
				list.setTripBookref((String) result[6]);

				if(list.getRouteName() == null || list.getRouteName() == "") {
					list.setRouteName((String) result[7]);
				}
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	@Transactional
	public List<TripSheetDto> listTripSheetManage(CustomUserDetails userDetails) throws Exception {

		//return TripSheetDao.listTripSheetManage();

		TypedQuery<Object[]> query = entityManager.createQuery(
				"Select T.tripSheetID, T.tripSheetNumber, V.vehicle_registration, VG.vGroup, TR.routeNumber, TR.routeName, T.tripOpenDate, T.tripBookref, T.routeName"
						+ " From TripSheet AS T "
						+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " where  T.tripStutesId=2 AND T.companyId = " + userDetails.getCompany_id()
						+ " AND T.markForDelete = 0 ORDER BY T.tripSheetID desc",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setTripSheetNumber((Long) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setVehicle_Group((String) result[3]);
				list.setRouteName((String) result[4]);
				if ((Date) result[5] != null)
					list.setTripOpenDateOn((Date) result[5]);
				list.setTripBookref((String) result[6]);

				if(list.getRouteName() == null || list.getRouteName() == "") {
					list.setRouteName((String) result[7]);
				}

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	@Transactional
	public List<TripSheetDto> listTripSheetClose(CustomUserDetails userDetails) throws Exception {

		//return TripSheetDao.listTripSheetClose();

		TypedQuery<Object[]> query = entityManager.createQuery(
				"Select T.tripSheetID, T.tripSheetNumber, V.vehicle_registration, VG.vGroup, TR.routeNumber, TR.routeName, T.tripOpenDate, T.tripBookref, T.routeName"
						+ " From TripSheet AS T "
						+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " where  T.tripStutesId=3 AND T.companyId = " + userDetails.getCompany_id()
						+ " AND T.markForDelete = 0 ORDER BY T.tripSheetID desc",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setTripSheetNumber((Long) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setVehicle_Group((String) result[3]);
				list.setRouteName((String) result[4]);
				if ((Date) result[5] != null)
					list.setTripOpenDateOn((Date) result[5]);
				list.setTripBookref((String) result[6]);

				if(list.getRouteName() == null || list.getRouteName() == "") {
					list.setRouteName((String) result[7]);
				}

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	@Transactional
	public List<TripSheet> listTripSheetCloseAccount() throws Exception {

		// return TripSheetDao.listTripSheetCloseAccount();

		TypedQuery<TripSheet> query = entityManager.createQuery(
				"FROM TripSheet as v  Where v.TripStutes='A/C CLOSED' ORDER BY v.tripSheetID desc", TripSheet.class);
		query.setFirstResult(0);
		query.setMaxResults(100);
		return query.getResultList();
	}

	@Transactional
	public void deleteTripSheet(Long Approval_ID, Integer companyId ,Long userId,Timestamp date) throws Exception {

		TripSheetDao.deleteTripSheet(Approval_ID, companyId ,userId,date);
	}

	@Transactional
	public void deleteTripSheetAdvance(Long TripSheet_Advanceid, Integer companyId) throws Exception {

		TripSheetAdvanceRepository.deleteTripSheetAdvance(TripSheet_Advanceid, companyId);
	}

	@Transactional
	public void deleteTripSheetExpense(Long TripSheet_Expenseid, Integer companyId) throws Exception {

		TripSheetExpenseRepository.deleteTripSheetExpense(TripSheet_Expenseid, companyId);
	}
	
	@Transactional
	public void deleteTripSheetRouteWiseWeight(Long routeWiseWeightId, Integer companyId) throws Exception {

		TripSheetRouteWiseWeightRepository.deleteTripSheetRouteWiseWeight(routeWiseWeightId, companyId);
	}
	
	@Transactional
	public void deleteTripSheetExtra(Long tripExtraID, Integer companyId) throws Exception {

		TripSheetOptionsExtraRepository.deleteTripSheetExtra(tripExtraID, companyId);
	}

	@Transactional
	public void deleteTripSheetIncome(Long TripSheet_Incomeid, Integer companyId) throws Exception {

		TripSheetIncomeRepository.deleteTripSheetIncome(TripSheet_Incomeid, companyId);
	}
	
	@Override
	@Transactional
	public void deleteTripSheetIncomeById(Long TripSheet_Incomeid, Integer companyId) throws Exception {
		TripSheetIncomeRepository.deleteTripSheetIncomeById(TripSheet_Incomeid, companyId);
	}

	@Transactional
	public Long countTripSheet(CustomUserDetails userDetails) throws Exception {
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return TripSheetDao.countTripSheet(userDetails.getCompany_id());
		}
		return TripSheetDao.countTripSheet(userDetails.getId(), userDetails.getCompany_id());

	}

	@Transactional
	public Long countTripSheetToday(String Today) throws Exception {

		TypedQuery<Long> query = entityManager
				.createQuery("select count(*) from TripSheet where tripOpenDate='" + Today + "' ", Long.class);
		return query.getSingleResult();
	}

	@Transactional
	public List<TripSheetDto> SearchTripSheet(String Search) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		try {

			// return TripSheetDao.listTodayTripSheet(userDetails.getId(),
			// userDetails.getCompany_id());
			List<TripSheetDto> Dtos = null;
			TypedQuery<Object[]> query = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
								+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount, T.routeName "
								+ " FROM TripSheet T " 
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " where ( lower(T.tripSheetNumber) Like ('%" + Search
								+ "%') OR lower(V.vehicle_registration) Like ('%" + Search + "%')"
								+ " OR lower(TR.routeName) Like ('%" + Search + "%')  OR lower(T.tripBookref) Like ('%"
								+ Search + "%') ) AND T.companyId = " + userDetails.getCompany_id()
								+ " AND T.markForDelete = 0",
								Object[].class);

			} else {
				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
								+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount, T.routeName "
								+ " FROM TripSheet T " 
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " where ( lower(T.tripSheetNumber) Like ('%" + Search
								+ "%') OR lower(V.vehicle_registration) Like ('%" + Search + "%')"
								+ " OR lower(TR.routeName) Like ('%" + Search + "%')  OR lower(T.tripBookref) Like ('%"
								+ Search + "%') ) AND T.companyId = " + userDetails.getCompany_id()
								+ " AND T.markForDelete = 0",
								Object[].class);
			}

			List<Object[]> results = query.getResultList();

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
					select.setTripOpenDateOn((java.util.Date) vehicle[8]);
					select.setClosetripDateOn((java.util.Date) vehicle[9]);
					select.setTripBookref((String) vehicle[10]);
					select.setTripOpeningKM((Integer) vehicle[11]);
					select.setTripClosingKM((Integer) vehicle[12]);
					select.setTripUsageKM((Integer) vehicle[13]);
					select.setTripStutesId((short) vehicle[14]);
					select.setTripTotalAdvance((Double) vehicle[15]);
					select.setTripTotalexpense((Double) vehicle[16]);
					select.setTripTotalincome((Double) vehicle[17]);
					select.setCloseTripAmount((Double) vehicle[18]);
					select.setCloseACCTripAmount((Double) vehicle[19]);

					if(select.getRouteName() == null || select.getRouteName() == "") {
						select.setRouteName((String) vehicle[20]);
					}

					Dtos.add(select);
				}
			}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	public List<TripSheetDto> Search_TripSheet_Status(String vid, short status) throws Exception {
		CustomUserDetails userDetails = null;
		List<TripSheetDto> Dtos = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> query = null;
			if(vid != null && !vid.trim().equalsIgnoreCase("") && vid.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, TS.tripOpenDate, TS.closetripDate,"
								+ " TS.tripBookref, TS.tripOpeningKM, TS.tripClosingKM, TS.tripUsageKM, TS.tripStutesId, TS.tripTotalAdvance,"
								+ " TS.tripTotalexpense, TS.tripTotalincome, TS.closeTripAmount, TS.closeACCTripAmount, TS.routeName "
								+ " from TripSheet TS " 
								+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
								+ " where ( lower(V.vehicle_registration) LIKE ('%" + vid
								+ "%') OR lower(TR.routeName)  LIKE ('%" + vid + "%')) AND TS.tripStutesId=" + status
								+ " AND TS.companyId = " + userDetails.getCompany_id() + " AND TS.markForDelete = 0 ",
								Object[].class);
			} else {
				query = entityManager.createQuery(
						"SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
								+ " TR.routeName, TS.tripOpenDate, TS.closetripDate,"
								+ " TS.tripBookref, TS.tripOpeningKM, TS.tripClosingKM, TS.tripUsageKM, TS.tripStutesId, TS.tripTotalAdvance,"
								+ " TS.tripTotalexpense, TS.tripTotalincome, TS.closeTripAmount, TS.closeACCTripAmount, TS.routeName "
								+ " from TripSheet TS" 
								+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid AND VGP.user_id = "
								+ userDetails.getId() + "" + " where ( lower(V.vehicle_registration) LIKE ('%" + vid
								+ "%')  OR lower(TR.routeName)  LIKE ('%" + vid + "%')) AND TS.tripStutesId=" + status
								+ "  AND TS.companyId = " + userDetails.getCompany_id() + " AND TS.markForDelete = 0 ",
								Object[].class);
			}
			List<Object[]> results = query.getResultList();

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
					select.setTripOpenDateOn((java.util.Date) vehicle[8]);
					select.setClosetripDateOn((java.util.Date) vehicle[9]);
					select.setTripBookref((String) vehicle[10]);
					select.setTripOpeningKM((Integer) vehicle[11]);
					select.setTripClosingKM((Integer) vehicle[12]);
					select.setTripUsageKM((Integer) vehicle[13]);
					select.setTripStutesId((short) vehicle[14]);
					select.setTripTotalAdvance((Double) vehicle[15]);
					select.setTripTotalexpense((Double) vehicle[16]);
					select.setTripTotalincome((Double) vehicle[17]);
					select.setCloseTripAmount((Double) vehicle[18]);
					select.setCloseACCTripAmount((Double) vehicle[19]);
					if(select.getRouteName() == null || select.getRouteName() == "") {
						select.setRouteName((String) vehicle[20]);
					}
					Dtos.add(select);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Transactional
	public List<TripSheetAdvance> getTripSheetAdvanceList(Long tripSheetID,int companyId) throws Exception {
		return TripSheetAdvanceRepository.findAllTripSheetAdvance(tripSheetID, companyId);
	}
	
	@Override
	@Transactional
	public void updateDriverInTripSheetAdvance(int driverId,long tripSheetId,int companyId) throws Exception {
		TripSheetAdvanceRepository.updateDriverInTripSheetAdvance(driverId, tripSheetId, companyId);
	}
	
	@Override
	@Transactional
	public void updateDriverInTripSheetExpense(int driverId,long tripSheetId,int companyId) throws Exception {
		TripSheetExpenseRepository.updateDriverInTripSheetExpense(driverId, tripSheetId, companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetAdvanceDto> findAllTripSheetAdvance(Long tripSheetID, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT T.tripAdvanceID, T.AdvanceAmount, B.branch_name, U.firstName,U.lastName, T.advanceRefence, T.advanceRemarks,"
						+ " U2.email, T.created, T.advancePlaceId, T.advancePaidbyId, T.createdById ,D.driver_firstname,T.paymentTypeId "
						+ " FROM TripSheetAdvance T "
						+ "	LEFT JOIN Driver AS D ON  D.driver_id = T.driverId "																											
						+ " LEFT JOIN Branch AS B ON B.branch_id = T.advancePlaceId"
						+ " LEFT JOIN User AS U ON U.id = T.advancePaidbyId"
						+ " LEFT JOIN User AS U2 ON U2.id = T.createdById"
						+ " WHERE T.tripsheet.tripSheetID =:id AND T.companyId =:companyId AND T.markForDelete = 0 AND T.AdvanceAmount > 0");

		query.setParameter("id", tripSheetID);
		query.setParameter("companyId", companyId);
		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetAdvanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetAdvanceDto>();
			TripSheetAdvanceDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new TripSheetAdvanceDto();
					select.setTripAdvanceID((Long) vehicle[0]);
					if(vehicle[1] != null)
					select.setAdvanceAmount((Double) vehicle[1]);
					select.setAdvancePlace((String) vehicle[2]);
					select.setAdvancePaidby((String) vehicle[3] + "_" + (String) vehicle[4]);
					select.setAdvanceRefence((String) vehicle[5]);
					select.setAdvanceRemarks((String) vehicle[6]);
					select.setCreatedBy((String) vehicle[7]);
					select.setCreated((java.util.Date) vehicle[8]);
					select.setAdvancePlaceId((Integer) vehicle[9]);
					select.setAdvancePaidbyId((Long) vehicle[10]);
					select.setCreatedById((Long) vehicle[11]);
					select.setDriverName((String)  vehicle[12]); 
					select.setCreatedStr(dateTimeFormat2.format(select.getCreated()));
					if(vehicle[13] != null) {
					select.setPaymentTypeId((short) vehicle[13]);
					select.setPaymentTypeName(PaymentTypeConstant.getPaymentTypeName(select.getPaymentTypeId()));
					}
					Dtos.add(select);
				}
			}
		}

		return Dtos;
	}

	@Transactional
	public List<TripSheetExpense> getTripSheetExpenseList(Long tripSheetID,int companyId) throws Exception {
		return TripSheetExpenseRepository.findAllTripSheetExpense(tripSheetID, companyId);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> findAllTripSheetExpense(Long tripSheetID, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT T.tripExpenseID, TE.expenseName, T.expenseId, T.expenseAmount, B.branch_name, T.expensePlaceId, T.expenseRefence,"
						+ " T.expenseFixedId, T.fuel_id, T.DHID, U.email, T.createdById, T.created, T.fuel_id, T.DHID, T.isCredit, "
						+ " T.vendorId, V.vendorName, T.voucherDate, T.remark, T.tripSheetExpense_document, T.tripSheetExpense_document_id,"
						+ " T.pfAmount, T.esiAmount, T.balanceAmount "
						+ " FROM TripSheetExpense T "
						+ " LEFT JOIN TripExpense TE ON TE.expenseID = T.expenseId AND (T.fuel_id IS NULL OR T.fuel_id = 0) AND (T.DHID IS NULL OR T.DHID = 0)"
						+ " LEFT JOIN Branch AS B ON B.branch_id = T.expensePlaceId"
						+ " LEFT JOIN User AS U ON U.id = T.createdById"
						+ " LEFT JOIN Vendor AS V ON V.vendorId = T.vendorId"
						+ " WHERE T.tripsheet.tripSheetID =:id AND T.companyId =:companyId AND T.markForDelete = 0");

		query.setParameter("id", tripSheetID);
		query.setParameter("companyId", companyId);
		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetExpenseDto>();
			TripSheetExpenseDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new TripSheetExpenseDto();
					select.setTripExpenseID((Long) vehicle[0]);
					select.setExpenseName((String) vehicle[1]);
					select.setExpenseId((Integer) vehicle[2]);
					select.setExpenseAmount(Utility.round((Double) vehicle[3], 2));
					select.setExpensePlace((String) vehicle[4]);
					select.setExpensePlaceId((Integer) vehicle[5]);
					select.setExpenseRefence((String) vehicle[6]);
					select.setExpenseFixedId((short) vehicle[7]);
					select.setExpenseFixed(TripRouteFixedType.getFixedTypeName((short) vehicle[7]));
					select.setFuel_id((Long) vehicle[8]);
					select.setDHID((Long) vehicle[9]);
					select.setCreatedBy((String) vehicle[10]);
					select.setCreatedById((Long) vehicle[11]);
					select.setCreated((java.util.Date) vehicle[12]);
					select.setFuel_id((Long) vehicle[13]);
					select.setDHID((Long) vehicle[14]);
					select.setCredit((boolean) vehicle[15]);
					if(vehicle[16] != null)
						select.setVendorId((Integer) vehicle[16]);
					if(vehicle[17] != null)
						select.setVendorName((String) vehicle[17]);
					if(vehicle[18] != null)
						select.setVoucherDate(dateFormat.format((java.util.Date)vehicle[18]));
					if(vehicle[19] != null) {
						select.setRemark((String) vehicle[19]);
					}else {
						select.setRemark("--");
					}
					select.setTripSheetExpense_document((boolean) vehicle[20]);
					if(vehicle[21] != null) {
						select.setTripSheetExpense_document_id((Long) vehicle[21]);
					}
					
					select.setPfAmount((Double) vehicle[22]);
					select.setEsiAmount((Double) vehicle[23]);
					select.setBalanceAmount((Double) vehicle[24]);
					
					if(select.getPfAmount() == null) {
						select.setPfAmount(0.0);
					}
					if(select.getEsiAmount() == null) {
						select.setEsiAmount(0.0);
					}
					if(select.getBalanceAmount() == null) {
						select.setBalanceAmount(0.0);
					}

					if ((select.getExpenseName() == "" || select.getExpenseName() == null)
							&& select.getFuel_id() != null && select.getFuel_id() > 0) {
						select.setExpenseName(
								VehicleFuelType.getVehicleFuelName(Short.parseShort((Integer) vehicle[2] + "")));
					} else if ((select.getExpenseName() == "" || select.getExpenseName() == null)
							&& select.getDHID() != null && select.getDHID() > 0) {
						select.setExpenseName(TripSheetFixedExpenseType
								.getTripExpenseName(Short.parseShort((Integer) vehicle[2] + "")));
					}
					
					if(select.getVendorName() == null ) {
						select.setVendorName("--");
					}
					
					if(select.getCreated()  != null) {
						select.setCreatedStr(dateTimeFormat2.format(select.getCreated()));
					}
					
					
					if(select.getVoucherDate() != null)
						select.setCreated((java.util.Date) vehicle[18]);
					
					select.setAvoidToDelete(TripRouteFixedType.isListToAvoidDelete(select.getExpenseFixedId()));
					
					if(select.getTripSheetExpense_document_id() != null) {
						
						org.fleetopgroup.persistence.document.TripSheetExpenseDocument document = tripSheetMobileService.getTripSheetExpenseDocumentDetails(select.getTripSheetExpense_document_id(), companyId);
						if(document != null) {
						select.setTripsheetExpenseBase64Document(ByteConvertor.byteArraytoBase64(document.getTripSheetExpenseContent()));
						select.setFileExtension(document.getTripSheetExpenseContentType());
						}
					}
						
					Dtos.add(select);
				}
			}
		}

		return Dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> findAllTripSheetExpense(String fromDate, String toDate, Integer companyId, String tallyCompany)
			throws Exception {
		Query query = entityManager.createQuery(
				"SELECT T.tripExpenseID, TE.expenseName, T.expenseId, T.expenseAmount,  T.expensePlaceId, T.expenseRefence,"
						+ " T.expenseFixedId, T.fuel_id, T.DHID, T.createdById, T.created, T.isCredit, T.vendorId, V.vendorName,"
						+ " VH.ledgerName, TC.companyName, T.remark, T.voucherDate, VH.vid, TS.tripSheetID, VH.vehicle_registration, "
						+ " TS.tripSheetNumber, T.isPendingForTally "
						+ " FROM TripSheetExpense T "
						+ " INNER JOIN TripSheet TS ON TS.tripSheetID = T.tripsheet.tripSheetID AND TS.markForDelete = 0 AND TS.voucherDate is not null "
						+ " INNER JOIN Vehicle VH ON VH.vid = TS.vid "
						+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = TS.tallyCompanyId AND  TC.companyName = '"+tallyCompany+"'"
						+ " LEFT JOIN TripExpense TE ON TE.expenseID = T.expenseId AND (T.fuel_id IS NULL OR T.fuel_id = 0) AND TE.markForDelete = 0"
						+ " LEFT JOIN Vendor AS V ON V.vendorId = T.vendorId"
						+ " WHERE TS.voucherDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
						+ " AND T.companyId = "+companyId+" AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+" AND T.markForDelete = 0");

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetExpenseDto>();
			TripSheetExpenseDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new TripSheetExpenseDto();
					select.setTripExpenseID((Long) vehicle[0]);
					select.setExpenseName((String) vehicle[1]);
					select.setExpenseId((Integer) vehicle[2]);
					select.setExpenseAmount((Double) vehicle[3]);
					select.setExpensePlaceId((Integer) vehicle[4]);
					select.setExpenseRefence((String) vehicle[5]);
					select.setExpenseFixedId((short) vehicle[6]);
					select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName((short) 1));
					select.setFuel_id((Long) vehicle[7]);
					select.setDHID((Long) vehicle[8]);
					select.setCreatedById((Long) vehicle[9]);
					select.setCreated((java.util.Date) vehicle[10]);
					select.setCredit((boolean) vehicle[11]);
					if(vehicle[12] != null)
						select.setVendorId((Integer) vehicle[12]);
					if(vehicle[13] != null)
						select.setVendorName((String) vehicle[13]);
					
					select.setLedgerName((String) vehicle[14]);
					if(vehicle[16] != null)
					select.setTallyCompanyName((String) vehicle[15]);
					if(vehicle[17] != null)
					select.setRemark((String) vehicle[16]);
					if(vehicle[18] != null)
						select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[17]));//tallyFormat.format((java.util.Date)vehicle[17])
					select.setVid((Integer) vehicle[18]);
					select.setTripSheetId((Long) vehicle[19]);
					select.setVehicle_registration((String) vehicle[20]);
					select.setTripSheetNumber((Long) vehicle[21]);
					select.setPendingForTally((boolean) vehicle[22]);
					select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_TRIPSHEET);
					select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
					
					
					
					if ((select.getExpenseName() == "" || select.getExpenseName() == null)
							&& select.getFuel_id() != null && select.getFuel_id() > 0) {
						select.setExpenseName(
								VehicleFuelType.getVehicleFuelName(Short.parseShort((Integer) vehicle[2] + "")));
					} else if ((select.getExpenseName() == "" || select.getExpenseName() == null)
							&& select.getDHID() != null && select.getDHID() > 0) {
						select.setExpenseName(TripSheetFixedExpenseType
								.getTripExpenseName(Short.parseShort((Integer) vehicle[2] + "")));
					}
					
					if(select.getVendorName() == null ) {
						select.setVendorName("--");
					}
					if(select.getCreated() != null ) {
						select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[17]));
					}
					
					
					if(select.getRemark() == null) {
						select.setRemark("--");
					}
					if(select.getTallyCompanyName() == null) {
						select.setTallyCompanyName("--");
					}
					if(select.getLedgerName() == null) {
						select.setLedgerName("--");
					}
					select.setTripSheetNumberStr("TS-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
					
					Dtos.add(select);
				}
			}
			
		}
		return Dtos;
	}
	
	@Override
	public List<TripSheetExpenseDto> findAllTripSheetExpenseForTally(String fromDate, String toDate, Integer companyId, 
			String tallyCompany, String ledgerName, Long tallyId) throws Exception {
		HashMap<String, TripSheetExpenseDto>   expenseHM		= null;
		TripSheetExpenseDto						expenseDto		= null;
		
		Query query = entityManager.createQuery(
				"SELECT T.tripExpenseID, TE.expenseName, T.expenseId, T.expenseAmount,  T.expensePlaceId, T.expenseRefence,"
						+ " T.expenseFixedId, T.fuel_id, T.DHID, T.createdById, T.created, T.isCredit, T.vendorId, V.vendorName,"
						+ " VH.ledgerName, T.remark, TS.closetripDate, VH.vid, TS.tripSheetID, VH.vehicle_registration, "
						+ " TS.tripSheetNumber, T.isPendingForTally "
						+ " FROM TripSheetExpense T "
						+ " INNER JOIN TripSheet TS ON TS.tripSheetID = T.tripsheet.tripSheetID AND TS.markForDelete = 0 AND TS.closetripDate is not null "
						+ " INNER JOIN Vehicle VH ON VH.vid = TS.vid "
						+ " LEFT JOIN TripExpense TE ON TE.expenseID = T.expenseId AND (T.fuel_id IS NULL OR T.fuel_id = 0) AND TE.markForDelete = 0"
						+ " LEFT JOIN Vendor AS V ON V.vendorId = T.vendorId"
						+ " WHERE TS.closetripDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
						+ " AND T.companyId = "+companyId+" AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+" AND T.markForDelete = 0");

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			expenseHM	= new HashMap<>();
			Dtos = new ArrayList<TripSheetExpenseDto>();
			TripSheetExpenseDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new TripSheetExpenseDto();
					select.setTripExpenseID((Long) vehicle[0]);
					select.setExpenseName(ledgerName);
					select.setExpenseId((Integer) vehicle[2]);
					select.setExpenseAmount((Double) vehicle[3]);
					select.setExpensePlaceId((Integer) vehicle[4]);
					select.setExpenseRefence((String) vehicle[5]);
					select.setExpenseFixedId((short) vehicle[6]);
					select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName((short) 1));
					select.setFuel_id((Long) vehicle[7]);
					select.setDHID((Long) vehicle[8]);
					select.setCreatedById((Long) vehicle[9]);
					select.setCreated((java.util.Date) vehicle[10]);
					select.setCredit((boolean) vehicle[11]);
					if(vehicle[12] != null)
						select.setVendorId((Integer) vehicle[12]);
					if(vehicle[13] != null)
						select.setVendorName((String) vehicle[13]);
					
					select.setLedgerName((String) vehicle[14]);
					select.setTallyCompanyName(tallyCompany);
					//if(vehicle[15] != null)
				//	select.setRemark((String) vehicle[15]);
					if(vehicle[16] != null)
						select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[16]));//tallyFormat.format((java.util.Date)vehicle[17])
					select.setVid((Integer) vehicle[17]);
					select.setTripSheetId((Long) vehicle[18]);
					select.setVehicle_registration((String) vehicle[19]);
					select.setTripSheetNumber((Long) vehicle[20]);
					select.setPendingForTally((boolean) vehicle[21]);
					select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_TRIPSHEET);
					select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
					
					
					
					if ((select.getExpenseName() == "" || select.getExpenseName() == null)
							&& select.getFuel_id() != null && select.getFuel_id() > 0) {
						select.setExpenseName(
								VehicleFuelType.getVehicleFuelName(Short.parseShort((Integer) vehicle[2] + "")));
					} else if ((select.getExpenseName() == "" || select.getExpenseName() == null)
							&& select.getDHID() != null && select.getDHID() > 0) {
						select.setExpenseName(TripSheetFixedExpenseType
								.getTripExpenseName(Short.parseShort((Integer) vehicle[2] + "")));
					}
					
					if(select.getVendorName() == null ) {
						select.setVendorName("--");
					}
					if(select.getCreated() != null ) {
						select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[16]));
					}
					
					
					select.setRemark("TripSheet Expenses for TS-"+(Long) vehicle[20]+" Vehicle No : "+select.getVehicle_registration()+" Tripsheet Completed On  "+dateFormat.format((java.util.Date)vehicle[16]));
					
					if(select.getTallyCompanyName() == null) {
						select.setTallyCompanyName("--");
					}
					if(select.getLedgerName() == null) {
						select.setLedgerName("--");
					}
					select.setTripSheetNumberStr("TS-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
					
					expenseDto	= expenseHM.get(select.getTripSheetNumber()+"_"+select.getExpenseName());
					
					if(expenseDto != null) {
						select.setExpenseAmount(select.getExpenseAmount() + expenseDto.getExpenseAmount());
					}
					
					expenseHM.put(select.getTripSheetNumber()+"_"+select.getExpenseName(), select);
					
				}
			}
			
		}
		
		if(expenseHM != null && !expenseHM.isEmpty()) {
			Dtos	= new ArrayList<>(expenseHM.values());
		}
		
		return Dtos;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetOptionsDto> findAllTripSheetExtraOptions(Long tripSheetID, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT T.tripExtraID, TE.tripsheetextraname, T.tripsheetoptionsID, T.TripSheetExtraQuantity, T.TripSheetExtraDescription,"
						+ " T.createdById, T.created FROM TripSheetOptionsExtra T "
						+ " LEFT JOIN TripSheetOptions TE ON TE.tripsheetoptionsId = T.tripsheetoptionsID "						
						+ " WHERE T.tripsheet.tripSheetID =:id AND T.companyId =:companyId AND T.markForDelete = 0");

		query.setParameter("id", tripSheetID);
		query.setParameter("companyId", companyId);


		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetOptionsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetOptionsDto>();
			TripSheetOptionsDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new TripSheetOptionsDto();

					select.setTripExtraID((Long) vehicle[0]);
					select.setTripsheetextraname((String) vehicle[1]);
					select.setTripsheetoptionsId((Long) vehicle[2]);
					select.setTripSheetExtraQuantity((Double) vehicle[3]);
					select.setTripsheetextradescription((String) vehicle[4]);
					select.setCreatedById((Long) vehicle[5]);
					select.setCreated((java.util.Date) vehicle[6]);


					Dtos.add(select);
				}
			}
		}

		return Dtos;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetOptionsDto> findAllTripSheetExtraOptionsReceived(Long tripSheetID, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT TR.tripExtraID, TE.tripsheetextraname, TR.tripsheetoptionsID, TR.TripSheetExtraQuantityReceived, TR.TripSheetExtraDescription,"
						+ " TR.createdById, TR.created FROM TripSheetExtraReceived TR "
						+ " LEFT JOIN TripSheetOptions TE ON TE.tripsheetoptionsId = TR.tripsheetoptionsID "						
						+ " WHERE TR.tripsheet.tripSheetID =:id AND TR.companyId =:companyId AND TR.markForDelete = 0");

		query.setParameter("id", tripSheetID);
		query.setParameter("companyId", companyId);


		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetOptionsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetOptionsDto>();
			TripSheetOptionsDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new TripSheetOptionsDto();

					select.setTripExtraID((Long) vehicle[0]);
					select.setTripsheetextraname((String) vehicle[1]);
					select.setTripsheetoptionsId((Long) vehicle[2]);
					select.setTripSheetExtraQuantityReceived((Double) vehicle[3]);
					select.setTripsheetextradescription((String) vehicle[4]);
					select.setCreatedById((Long) vehicle[5]);
					select.setCreated((java.util.Date) vehicle[6]);


					Dtos.add(select);
				}
			}
		}

		return Dtos;
	}

	@Transactional
	public List<TripSheetIncome> findAllTripSheetIncomeList(Long tripSheetID,int companyId) throws Exception {
		return TripSheetIncomeRepository.findAllTripSheetIncome(tripSheetID, companyId);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetIncomeDto> findAllTripSheetIncome(Long tripSheetID, Integer companyId) throws Exception {
		
		HashMap<String, Object>configuration = null;
	    try{
	    	configuration= companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG); 
		 }catch(Exception e){
			
		 }
		String join="";
		String condn ="";
		String cols ="";
		if((boolean) configuration.get("showLsDestination")) {
			condn = " WHERE T.tripsheet.tripSheetID =:id AND T.companyId =:companyId AND T.markForDelete = 0";
	        join  = " LEFT JOIN LoadingSheetToTripSheet L ON L.dispatchLedgerId = T.dispatchLedgerId AND L.tripSheetId = T.tripsheet.tripSheetID ";
	        cols = " ,L.tripDateTime, L.lrSourceBranch, L.lsDestinationBranch ";
		}else {
	        condn = " WHERE T.tripsheet.tripSheetID =:id AND T.companyId =:companyId AND T.markForDelete = 0 "; 
		    join  = " ";
		    cols = " ";
		}
		
		List<Long> dispatchId = null;
		Query query = entityManager.createQuery(
			"SELECT T.tripincomeID, TI.incomeName, T.incomeAmount, B.branch_name ,T.incomeRefence, U.firstName, U.lastName, T.incomeFixedId,"
						+ " U2.email, T.incomeId, T.incomePlaceId, T.incomeCollectedById, T.createdById, T.created, T.lHPVDetailsId,"
						+ " T.dispatchLedgerId, T.ticketIncomeApiId, T.netIncomeAmount, T.routeID, T.tripsheetIncomeDate, T.gst,T.commission, TR.routeName,"
						+ " T.isDriverExcluded, T.billSelectionId "+cols+ " "
						+ " FROM TripSheetIncome T " 
						+ " LEFT JOIN TripIncome TI ON TI.incomeID = T.incomeId AND (T.lHPVDetailsId = "+null+" OR T.lHPVDetailsId = 0 )"
						+ " LEFT JOIN LHPVDetails lhpv ON lhpv.lHPVDetailsId = T.lHPVDetailsId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID"
						+ " LEFT JOIN Branch AS B ON B.branch_id = T.incomePlaceId"
						+ " LEFT JOIN User AS U ON U.id = T.incomeCollectedById"
						+ " LEFT JOIN User AS U2 ON U2.id = T.createdById"
						+  join 
						+  condn );

		query.setParameter("id", tripSheetID);
		query.setParameter("companyId", companyId);
		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetIncomeDto>();
			
			dispatchId = new ArrayList<>();
			
			TripSheetIncomeDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new TripSheetIncomeDto();
					if(select != null) {
					select.setTripincomeID((Long) vehicle[0]);
					select.setIncomeName((String) vehicle[1]);
					select.setIncomeAmount((Double) Double.parseDouble(toFixedTwo.format(vehicle[2])));
					
					select.setIncomePlace((String) vehicle[3]);
					select.setIncomeRefence((String) vehicle[4]);
					select.setIncomeCollectedBy((String) vehicle[5] + "_" + (String) vehicle[6]);
					if(vehicle[7]!=null) {
						
					select.setIncomeFixedId((Short) vehicle[7]);
					select.setIncomeFixed(TripRouteFixedType.getFixedTypeName((short) vehicle[7]));
					}
					select.setCreatedBy((String) vehicle[8]);
					select.setIncomeId((Integer) vehicle[9]);
					select.setIncomePlaceId((Integer) vehicle[10]);
					select.setIncomeCollectedById((Long) vehicle[11]);
					select.setCreatedById((Long) vehicle[12]);
					select.setCreated((java.util.Date) vehicle[13]);
					select.setlHPVDetailsId((Long) vehicle[14]);
					select.setDispatchLedgerId((Long) vehicle[15]);
					
					
					
					if(vehicle[24] != null) {
						//System.err.println("bill id "+ vehicle[24]);
						select.setBillType(WayBillTypeConstant.getIncomeTypeName((short)vehicle[24]));
						select.setBillSelectionId((short) vehicle[24]);
					}
					
					if(vehicle[16] != null)
					select.setTicketIncomeApiId((long) vehicle[16]);
					
					if(select.getlHPVDetailsId() != null && select.getlHPVDetailsId() > 0){
						select.setIncomeName("LORRY HIRE");
					}else if(select.getDispatchLedgerId() != null && select.getDispatchLedgerId() > 0) {
						select.setIncomeName("Loading Sheet");
						select.setLoadingSheetIncome(true);
					}
					if((Double) vehicle[17] != null) {
						select.setNetIncomeAmount((Double) vehicle[17]);
					}
					if((Integer) vehicle[18] != null) {
						select.setRouteID((Integer) vehicle[18]);
					}
					if((Timestamp) vehicle[19] != null) {
						select.setTripsheetIncomeDateStr(dateFormat.format((Timestamp) vehicle[19]));
					}
					if(vehicle[20] != null ) {
						select.setGst((Double) vehicle[20]);
					}
					if(vehicle[21] != null) {
						select.setCommission((Double) vehicle[21]);
					}
					if(vehicle[22] != null ) {
						select.setRouteName((String)vehicle[22]);
					}
					if(vehicle[23] != null ) {
						select.setDriverExcluded((boolean) vehicle[23]);
						if(select.isDriverExcluded()) {
							select.setDriverExcludedStr("Yes");
						}else {
							select.setDriverExcludedStr("No");
						}
						
					}
					
					select.setTripSheetId(tripSheetID);
					
					if(select.getCreated()  != null) {
						select.setCreatedStr(dateTimeFormat2.format(select.getCreated()));
					}
					
					if((boolean) configuration.get("showLsDestination")) {
						if(vehicle[25] != null) {
							select.setTripDateTime(dateFormat.format((Timestamp)vehicle[25]));
						}
						if(vehicle[26] != null)
							select.setLsSourceBranch((String)vehicle[26]);
						if(vehicle[27] != null)
							select.setLsDestinationBranch((String)vehicle[27]);	
					}
					if(!dispatchId.contains(vehicle[15])) {
						Dtos.add(select);
					}
					if(vehicle[15]!= null) 
						dispatchId.add((Long)vehicle[15]);
					}
				}
		}
	}

		return Dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetIncomeDto> findAllTripSheetIncomeForPL(Long tripSheetID, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
			"SELECT T.tripincomeID, TI.incomeName, T.incomeAmount, B.branch_name ,T.incomeRefence, U.firstName, U.lastName, T.incomeFixedId,"
						+ " U2.email, T.incomeId, T.incomePlaceId, T.incomeCollectedById, T.createdById, T.created, T.lHPVDetailsId, "
						+ " T.dispatchLedgerId, T.ticketIncomeApiId"
						+ " FROM TripSheetIncome T " 
						+ " LEFT JOIN TripIncome TI ON TI.incomeID = T.incomeId "
						+ " LEFT JOIN LHPVDetails lhpv ON lhpv.lHPVDetailsId = T.lHPVDetailsId"
						+ " LEFT JOIN Branch AS B ON B.branch_id = T.incomePlaceId"
						+ " LEFT JOIN User AS U ON U.id = T.incomeCollectedById"
						+ " LEFT JOIN User AS U2 ON U2.id = T.createdById"
						+ " WHERE T.tripsheet.tripSheetID =:id AND T.companyId =:companyId "
						+ " AND (T.lHPVDetailsId IS NULL OR T.lHPVDetailsId = 0) AND (T.ticketIncomeApiId IS NULL OR T.ticketIncomeApiId = 0)"
						+ " AND T.markForDelete = 0");

		query.setParameter("id", tripSheetID);
		query.setParameter("companyId", companyId);
		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetIncomeDto>();
			TripSheetIncomeDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new TripSheetIncomeDto();
					if(select != null) {
					select.setTripincomeID((Long) vehicle[0]);
					select.setIncomeName((String) vehicle[1]);
					select.setIncomeAmount((Double) vehicle[2]);
					select.setIncomePlace((String) vehicle[3]);
					select.setIncomeRefence((String) vehicle[4]);
					select.setIncomeCollectedBy((String) vehicle[5] + "_" + (String) vehicle[6]);
					if(vehicle[7]!=null) {
						
					select.setIncomeFixedId((Short) vehicle[7]);
					select.setIncomeFixed(TripRouteFixedType.getFixedTypeName((short) vehicle[7]));
					}
					select.setCreatedBy((String) vehicle[8]);
					select.setIncomeId((Integer) vehicle[9]);
					select.setIncomePlaceId((Integer) vehicle[10]);
					select.setIncomeCollectedById((Long) vehicle[11]);
					select.setCreatedById((Long) vehicle[12]);
					select.setCreated((java.util.Date) vehicle[13]);
					select.setlHPVDetailsId((Long) vehicle[14]);
					select.setDispatchLedgerId((Long) vehicle[15]);
					
					if(vehicle[16] != null)
					select.setTicketIncomeApiId((long) vehicle[16]);
					
					if(select.getlHPVDetailsId() != null && select.getlHPVDetailsId() > 0){
						select.setIncomeName("LORRY HIRE");
					}else if(select.getDispatchLedgerId() != null && select.getDispatchLedgerId() > 0) {
						select.setIncomeName("Loading Sheet");
						select.setLoadingSheetIncome(true);
					}

					Dtos.add(select);
					}
				}
			}
		}

		return Dtos;
	}

	@Transactional
	public List<TripSheetExpense> ValidateAllTripSheetExpense(Integer ExpenseName, Long tripSheetID, Integer companyId)
			throws Exception {

		return TripSheetExpenseRepository.ValidateAllTripSheetExpense(ExpenseName, tripSheetID, companyId);
	}



	public List<TripSheetOptionsExtra> ValidateAllTripSheetExtraOptions(Long tripsheetextraname, Long tripSheetID, Integer companyId) throws Exception
	{

		return TripSheetOptionsExtraRepository.ValidateAllTripSheetExtraOptions(tripsheetextraname, tripSheetID, companyId);
	}

	public List<TripSheetExtraReceived> ValidateAllTripSheetExtraOptionsReceived(Long tripsheetextraname, Long tripSheetID, Integer companyId) throws Exception
	{

		return TripSheetExtraReceivedRepository.ValidateAllTripSheetExtraOptionsReceived(tripsheetextraname, tripSheetID, companyId);
	}


	@Transactional
	public List<TripSheetIncome> ValidateAllTripSheetIncome(Integer IncomeName, Long tripSheetID, Integer companyId)
			throws Exception {

		return TripSheetIncomeRepository.ValidateAllTripSheetIncome(IncomeName, tripSheetID, companyId);
	}

	@Transactional
	public void UPDATE_TOTALADVANCE_IN_TRIPSHEET_TRIPSHEETADVANCE_ID(Long tripSheetID, Integer companyId)
			throws Exception {

		entityManager.createQuery(
				"Update TripSheet AS T Set T.tripTotalAdvance= (SELECT SUM(AdvanceAmount) FROM TripSheetAdvance WHERE tripSheetID ="
						+ tripSheetID + " AND companyId = " + companyId
						+ " AND markForDelete = 0 ) WHERE T.tripSheetID = " + tripSheetID + " ")
		.executeUpdate();
	}

	@Transactional
	public void UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(Long tripSheetID, Integer companyId) throws Exception {
		entityManager.createQuery(
				"Update TripSheet AS T Set T.tripTotalexpense= (SELECT SUM(expenseAmount) FROM TripSheetExpense WHERE tripSheetID ="
						+ tripSheetID + " AND companyId = " + companyId
						+ " AND markForDelete = 0 ) WHERE T.tripSheetID = " + tripSheetID + " ")
		.executeUpdate();
	}

	@Transactional
	public void UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(Long tripSheetID, Integer companyId) throws Exception {
		
		entityManager.createQuery(
				"Update TripSheet AS T Set T.tripTotalincome= (SELECT SUM(incomeAmount) FROM TripSheetIncome WHERE tripSheetID ="
						+ tripSheetID + " AND companyId = " + companyId
						+ " AND markForDelete = 0) WHERE T.tripSheetID = " + tripSheetID + "")
		.executeUpdate();
	}
	

	@Transactional
	public void updateCloseAccountTrip(Long closeAccountBY, short closeAccountStatus, Double amount,
			String closeReference, Timestamp toDate, Long LASTUPDATED, Timestamp LASTUPDATED_DATE, Long tripSheetID,
			Integer companyId, Integer acclocation) throws Exception {
		
		TripSheetDao.updateCloseAccountTrip(closeAccountBY, closeAccountStatus, amount, closeReference,
				LASTUPDATED_DATE, LASTUPDATED, LASTUPDATED_DATE, tripSheetID, companyId, acclocation);
	}

	@Transactional
	public void updateCloseTripSheet(Long TripSheetID, Integer CloseingKm, Integer UsageKM, short CloseingKmStatus,
			short paidto, String CloseRefenceNo, Double amount, Long paidby, Date Closed_data, Long LASTUPDATED,
			java.util.Date LASTUPDATED_DATE, Long ClosedBy, Integer ClosedLocation, java.util.Date ClosedByTime,
			Integer companyId, Double closingGPSKM, String gpsClosingLocation, Double gpsUsageKM, Double driverBalance) throws Exception {

		TripSheetDao.updateCloseTripSheet(TripSheetID, CloseingKm, UsageKM, CloseingKmStatus, paidto, CloseRefenceNo,
				amount, paidby, Closed_data, LASTUPDATED, LASTUPDATED_DATE, ClosedBy, ClosedLocation, ClosedByTime,
				companyId, closingGPSKM, gpsClosingLocation, gpsUsageKM, driverBalance);
	}

	@Transactional
	public List<TripSheetDto> VehicleTOlistTripSheet(Integer vid, Integer pageNumber) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Double 		    		    TotalExpense	 = null;
		Double  		            fuelWithoutCash  = null;
		HashMap<String, Object>    	config			 = null;
		Double 						B_Income		 = null;	
		Double 						B_IncomeDue		 = null;	
		Double						balance			 = null;
		config					= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);

		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

			query = entityManager.createQuery(
					"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
							+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
							+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
							+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount, T.routeName "
							+ " FROM TripSheet T " 
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " where T.vid = " + vid + " AND T.companyId = " + userDetails.getCompany_id()
							+ " AND T.markForDelete = 0 order by T.tripSheetID DESC",
							Object[].class);

		} else {
			query = entityManager.createQuery(
					"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
							+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
							+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
							+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount, T.routeName "
							+ " FROM TripSheet T " 
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" + " where T.vid = " + vid + " AND T.companyId = "
							+ userDetails.getCompany_id() + " AND T.markForDelete = 0 order by T.tripSheetID DESC",
							Object[].class);
		}
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);

		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto select = null;
			for (Object[] vehicle : results) {
				
				B_Income       = 0.0;
				B_IncomeDue    = 0.0;
				TotalExpense   = 0.0;
				select = new TripSheetDto();
				select.setTripSheetID((Long) vehicle[0]);
				select.setTripSheetNumber((Long) vehicle[1]);
				select.setVid((Integer) vehicle[2]);
				select.setVehicle_registration((String) vehicle[3]);
				select.setVehicle_Group((String) vehicle[4]);
				select.setVehicleGroupId((long) vehicle[5]);
				select.setRouteID((Integer) vehicle[6]);
				select.setRouteName((String) vehicle[7]);
				select.setTripOpenDateOn((java.util.Date) vehicle[8]);
				select.setClosetripDateOn((java.util.Date) vehicle[9]);
				select.setTripBookref((String) vehicle[10]);
				select.setTripOpeningKM((Integer) vehicle[11]);
				select.setTripClosingKM((Integer) vehicle[12]);
				select.setTripUsageKM((Integer) vehicle[13]);
				select.setTripStutesId((short) vehicle[14]);
				if(vehicle[15] != null)
				select.setTripTotalAdvance(Double.parseDouble(toFixedTwo.format(vehicle[15])));
				if(vehicle[16] != null)
				select.setTripTotalexpense(Double.parseDouble(toFixedTwo.format(vehicle[16])));
				if(vehicle[17] != null)
				select.setTripTotalincome(Double.parseDouble(toFixedTwo.format(vehicle[17])));
				if(vehicle[18] != null)
				select.setCloseTripAmount(Double.parseDouble(toFixedTwo.format(vehicle[18])));
				if( vehicle[19] != null)
				select.setCloseACCTripAmount(Double.parseDouble(toFixedTwo.format( vehicle[19])));
				if(select.getRouteName() == null || select.getRouteName() == "") {
					select.setRouteName((String) vehicle[20]);
				}
				
				//Double totalFuelAmount = 
				
				//fuelRepository.getTotalFuelAmount((Long) vehicle[0], userDetails.getCompany_id());
				//List<Fuel> fuelList = fuelRepository.getTotalFuelAmount((Long) vehicle[0], userDetails.getCompany_id());
				
				List<FuelDto>  fuelList = fuelService.Get_TripSheet_IN_FuelTable_List((Long)vehicle[0], userDetails.getCompany_id(), userDetails.getId());
				
				Double totalFuelAmount = (fuelList != null)  ? fuelList.stream()
				        .mapToDouble(FuelDto::getFuel_amount) // Assuming Fuel class has getFuelAmount method
				        .sum() : 0.0;
				
				fuelWithoutCash = fuelBL.prepareTotalAmount_Tripsheet_Created_fuel_details_New(fuelList);
				
				Double tollAmount = tollExpensesDetailsRepository.getTripSheetTollAmount((Long) vehicle[0]);
				
				if (totalFuelAmount != 0.0 && totalFuelAmount != null) {
				    select.setFuelAmount(Double.parseDouble(toFixedTwo.format(totalFuelAmount)));
				}else {
					select.setFuelAmount(0.0);
				}
				
				if (tollAmount != null) {
					select.setTollAmount(Double.parseDouble(toFixedTwo.format(tollAmount)));
				}else {
					select.setTollAmount(0.0);
				}
				
				//select.setBalance( ((vehicle[17] != null) ? (Double) vehicle[17] : 0.0) - TotalExpense);
				
				if((boolean)config.get("getBIncomeEIncome")) {
//	                List<TripSheetIncomeDto> incomeDto = tripSheetService.findAllTripSheetIncome((Long)vehicle[0], userDetails.getCompany_id());
//	                List<TripsheetDueAmountDto> dueAmountList = null;
//	                
//					if(incomeDto !=null && (boolean)config.get("getBIncomeEIncome")) {
//						for(TripSheetIncomeDto income:incomeDto) {
//							if(income.getBillSelectionId() == WayBillTypeConstant.WITH_BILL) {	
//								B_Income  += income.getIncomeAmount();
//							}
//						}
//	 					dueAmountList  = tripSheetService.getDueAmountListByTripsheetId((Long)vehicle[0], userDetails.getCompany_id());
//	 					System.err.println("dueAmountList -- "+ dueAmountList);
//	 					if(dueAmountList !=null) {
//							for(TripsheetDueAmountDto due:dueAmountList) {
//								if(due.getBillSelectionId() !=null) {
//									if(due.getBillSelectionId() == WayBillTypeConstant.WITH_BILL) {
//										B_IncomeDue  += due.getDueAmount();
//									}
//								}
//							}
//						}
//	 					balance = ((vehicle[15] != null) ? (Double) vehicle[15] : 0.0)  + B_Income - B_IncomeDue - ((vehicle[16] != null) ? (Double) vehicle[16] : 0.0);
//						select.setBalance(balance);
//					}
					select.setBalance(0.0);
				}else {
					TotalExpense = fuelWithoutCash + tollAmount + ((vehicle[16] != null) ? (Double) vehicle[16] : 0.0);
					select.setBalance( ((vehicle[17] != null) ? (Double) vehicle[17] : 0.0) - TotalExpense);
				}
				
				Dtos.add(select);
			}
		}
		return Dtos;

	}


	@Override
	public ValueObject getRouteWiseTripSheetReport(ValueObject valueInObject) throws Exception {

		ValueObject				valueOutObject				= null;
		String					dateRange					= null;
		long					routeId						= 0;

		try {


			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			dateRange	= valueInObject.getString("tripDateRange");
			routeId		= valueInObject.getLong("routeId");

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;

			From_TO_DateRange = dateRange.split(" to ");

			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			

			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
								+ " T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount, T.routeName "
								+ " FROM TripSheet T " 
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " where T.routeID = " + routeId + " AND T.companyId = " + userDetails.getCompany_id()
								+ "  AND T.tripOpenDate between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' "
								+ " AND T.markForDelete = 0 order by T.tripSheetID DESC",
								Object[].class);

			} else {
				query = entityManager.createQuery(
						"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
								+ " T.tripOpenDate, T.closetripDate,"
								+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
								+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount, T.routeName "
								+ " FROM TripSheet T " 
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " where T.routeID = " + routeId + " AND T.companyId = "
								+ userDetails.getCompany_id()
								+ "  AND T.tripOpenDate between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' "
								+ " AND T.markForDelete = 0 order by T.tripSheetID DESC",
								Object[].class);
			}

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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[7]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[8]));
					select.setTripBookref((String) vehicle[9]);
					select.setTripOpeningKM((Integer) vehicle[10]);
					select.setTripClosingKM((Integer) vehicle[11]);
					select.setTripUsageKM((Integer) vehicle[12]);
					select.setTripStutes(TripSheetStatus.getTripSheetStatusName((short) vehicle[13]));
					select.setTripTotalAdvance((Double) vehicle[14]);
					select.setTripTotalexpense((Double) vehicle[15]);
					select.setTripTotalincome((Double) vehicle[16]);
					select.setCloseTripAmount((Double) vehicle[17]);
					select.setCloseACCTripAmount((Double) vehicle[18]);
					select.setRouteName((String) vehicle[19]);

					Dtos.add(select);
				}
			}

			valueOutObject	= new ValueObject();
			valueOutObject.put("TripSheetList", Dtos);

			return	valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * getDeployment_Page_TripSheet(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public Page<TripSheet> getDeployment_Page_TripSheet(short status, Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {

		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return TripSheetDao.getDeployment_Page_TripSheet(status, userDetails.getCompany_id(), pageable);
		}
		return TripSheetDao.getDeployment_Page_TripSheet(status, userDetails.getId(), userDetails.getCompany_id(),
				pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * listTrip_Page_Status(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public List<TripSheetDto> listTrip_Page_Status(short status, Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {
		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
							+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
							+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
							+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount, T.routeName, T.voucherDate, T.tallyCompanyId, T.dispatchedByTime  "
							+ " FROM TripSheet T " 
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " Where T.tripStutesId=" + status + " AND T.companyId = " + userDetails.getCompany_id()
							+ " AND T.markForDelete = 0 ORDER BY T.tripSheetID desc",
							Object[].class);
		} else {
			query = entityManager.createQuery(
					"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
							+ " TR.routeName, T.tripOpenDate, T.closetripDate,"
							+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripStutesId, T.tripTotalAdvance,"
							+ " T.tripTotalexpense, T.tripTotalincome, T.closeTripAmount, T.closeACCTripAmount, T.routeName, T.voucherDate, T.tallyCompanyId, T.dispatchedByTime "
							+ " FROM TripSheet T " 
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" + " Where T.tripStutesId=" + status + " AND T.companyId = "
							+ userDetails.getCompany_id() + " AND T.markForDelete = 0 ORDER BY T.tripSheetID desc",
							Object[].class);
		}
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
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
				select.setTripOpenDateOn((java.util.Date) vehicle[8]);
				select.setClosetripDateOn((java.util.Date) vehicle[9]);
				select.setTripBookref((String) vehicle[10]);
				select.setTripOpeningKM((Integer) vehicle[11]);
				select.setTripClosingKM((Integer) vehicle[12]);
				select.setTripUsageKM((Integer) vehicle[13]);
				select.setTripStutesId((short) vehicle[14]);
				select.setTripTotalAdvance((Double) vehicle[15]);
				select.setTripTotalexpense((Double) vehicle[16]);
				select.setTripTotalincome((Double) vehicle[17]);
				select.setCloseTripAmount((Double) vehicle[18]);
				select.setCloseACCTripAmount((Double) vehicle[19]);
			
				
				if(select.getRouteName() == null || select.getRouteName() == "") {
					select.setRouteName((String) vehicle[20]);
				}
				if(vehicle[21] != null) {
					select.setVoucherDateStr(dateFormat.format((Timestamp)vehicle[21]));
				}
				if(vehicle[22] != null) {
					select.setTallyCompanyId((Long) vehicle[22]);
				}
				if(vehicle[23] != null) {
					select.setDispatchedByTimeOn((java.util.Date)vehicle[23]);
				}
				
				Dtos.add(select);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * update_Vehicle_Group_USING_Vehicle_Id(java.lang.String, java.lang.Integer)

	@Transactional
	public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group, Integer vehicle_id, long vehicleGroupId) {

		TripSheetDao.update_Vehicle_Group_USING_Vehicle_Id(Vehicle_Group, vehicle_id, vehicleGroupId);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * Delete_TSID_to_TripSheetAdvance(java.lang.Long)
	 */
	@Transactional
	public void Delete_TSID_to_TripSheetAdvance(Long tripSheetID, Integer companyId) {

		TripSheetAdvanceRepository.Delete_TSID_to_TripSheetAdvance(tripSheetID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * Delete_TSID_to_TripSheetExpence(java.lang.Long)
	 */
	@Transactional
	public void Delete_TSID_to_TripSheetExpence(Long tripSheetID, Integer companyId) {

		TripSheetExpenseRepository.Delete_TSID_to_TripSheetExpence(tripSheetID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * Delete_TSTD_to_TripSheetIncome(java.lang.Long)
	 */
	@Transactional
	public void Delete_TSTD_to_TripSheetIncome(Long tripSheetID, Integer companyId) {

		TripSheetIncomeRepository.Delete_TSID_to_TripSheetIncome(tripSheetID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * list_Report_TripSheet_HALT_AMOUNT(java.lang.String)
	 */
	@Transactional
	public List<TripSheetDto> list_Report_TripSheet_HALT_AMOUNT(String ReportQuery, CustomUserDetails userDetails)
			throws Exception {

		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery("Select T.tripSheetID, V.vehicle_registration, VG.vGroup, "
					+ "TR.routeName, TA.expenseAmount,TA.expenseRefence, TA.created, T.tripSheetNumber, T.routeName From TripSheet AS T"
					+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
					+ " INNER JOIN  TripSheetExpense AS TA ON  T.tripSheetID=TA.tripsheet.tripSheetID " + " where "
					+ ReportQuery + " AND T.companyId = " + userDetails.getCompany_id()
					+ " AND T.markForDelete = 0  ORDER BY T.tripSheetID asc", Object[].class);
		} else {
			query = entityManager.createQuery("Select T.tripSheetID, V.vehicle_registration, VG.vGroup, "
					+ "TR.routeName, TA.expenseAmount,TA.expenseRefence, TA.created , T.tripSheetNumber, T.routeName From TripSheet AS T"
					+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
					+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
					+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
					+ " INNER JOIN  TripSheetExpense AS TA ON  T.tripSheetID=TA.tripsheet.tripSheetID"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + " " + " where " + ReportQuery + " AND T.companyId = "
					+ userDetails.getCompany_id() + " AND T.markForDelete = 0  ORDER BY T.tripSheetID asc",
					Object[].class);
		}
		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setVehicle_registration((String) result[1]);
				list.setVehicle_Group((String) result[2]);
				list.setRouteName((String) result[3]);

				// This TripTotalexpense is expenseAmount in showing propose
				// only
				list.setTripTotalexpense((Double) result[4]);
				// This TripBookref is expenseRefence in showing propose only
				list.setTripBookref((String) result[5]);
				// This TripOpenDate is created in showing propose only
				list.setTripOpenDateOn((java.util.Date) result[6]);
				list.setTripSheetNumber((Long) result[7]);

				if(list.getRouteName() == null || list.getRouteName() == "") {
					list.setRouteName((String) result[8]);
				}

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * Get_Validate_TripSheet_Vehicle_DriverAcount(java.lang.Integer)
	 */
	@Transactional
	public List<TripSheetDto> Get_Validate_TripSheet_Vehicle_DriverAcount(Integer vehicleID, Integer companyId) {

		// return TripSheetDao.Get_Validate_TripSheet_Vehicle_DriverAcount(vehicleID,
		// companyId);

		TypedQuery<Object[]> query = entityManager
				.createQuery("Select T.tripSheetID, T.tripSheetNumber From TripSheet AS T" 
						+ " where T.vid=" + vehicleID
						+ " AND T.tripStutesId=2 AND T.companyId = " + companyId
						+ " AND T.markForDelete = 0 ORDER BY T.tripSheetID desc", Object[].class);

		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setTripSheetNumber((Long) result[1]);

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	@Override
	public List<TripSheetDto> Get_Validate_TripSheet_Vehicle(String vehicleID, Integer companyId) {
		// return TripSheetDao.Get_Validate_TripSheet_Vehicle(vehicleID, companyId);

		TypedQuery<Object[]> query = entityManager
				.createQuery("Select T.tripSheetID, T.tripSheetNumber "
						+ " From TripSheet AS T "
						+ " INNER JOIN Vehicle V ON V.vid = T.vid" 
						+ " where V.vehicle_registration=" + vehicleID
						+ " AND T.tripStutesId= 2 AND T.companyId = " + companyId
						+ " AND T.markForDelete = 0 ORDER BY T.tripSheetID desc", Object[].class);

		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setTripSheetNumber((Long) result[1]);

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * update_Total_TripSheet_comment(java.lang.Long)
	 */
	@Transactional
	public void update_Total_TripSheet_comment_Add(Long tripSheet_ID, Integer companyId) {

		entityManager.createQuery("Update TripSheet SET tripCommentTotal = (SELECT COUNT(TRIPCID) FROM TripSheetComment where "
				+ " TRIPSHEETID = "+tripSheet_ID+" AND markForDelete = 0) "
				+ " where  tripSheetID="
				+ tripSheet_ID + " AND companyId = " + companyId + " ").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * update_Total_TripSheet_comment_SubTrack(java.lang.Long)
	 */
	@Transactional
	public void update_Total_TripSheet_comment_SubTrack(Long tripSheet_ID, Integer companyId) {

		entityManager.createQuery("Update TripSheet SET tripCommentTotal = tripCommentTotal - 1  where  tripSheetID="
				+ tripSheet_ID + " AND companyId = " + companyId + " ").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * Update_TripSheet_FIRST_Driver_BATA(double, java.lang.Long)
	 */
	@Transactional
	public void Update_TripSheet_FIRST_Driver_BATA(double parseDouble, Long tripSheetID, Integer companyId) {
		
		TripSheetDao.Update_TripSheet_FIRST_Driver_BATA(parseDouble, tripSheetID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * Update_TripSheet_SECOND_Driver_BATA(double, java.lang.Long)
	 */
	@Transactional
	public void Update_TripSheet_SECOND_Driver_BATA(double parseDouble, Long tripSheetID, Integer companyId) {
		
		TripSheetDao.Update_TripSheet_SECOND_Driver_BATA(parseDouble, tripSheetID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * Update_TripSheet_CLEANER_BATA(double, java.lang.Long)
	 */
	@Transactional
	public void Update_TripSheet_CLEANER_BATA(double parseDouble, Long tripSheetID, Integer companyId) {
		
		TripSheetDao.Update_TripSheet_CLEANER_BATA(parseDouble, tripSheetID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * list_Of_DriverID_to_Driver_BATA_Details(java.lang.Integer, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<TripSheetDto> list_Of_DriverID_to_Driver_BATA_Details(Integer driver_id, String driverStart,
			String driverEnd, Integer companytId) {

		TypedQuery<Object[]> query = entityManager
				.createQuery("Select T.tripSheetID, T.tripSheetNumber, V.vehicle_registration, TR.routeNumber, TR.routeName, "
						+ " T.tripOpenDate, T.closetripDate, T.tripFristDriverBata, T.tripSecDriverBata, T.tripCleanerBata, T.routeName From TripSheet AS T "
						+ " INNER JOIN Vehicle V ON V.vid = T.vid "
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " where ( T.tripFristDriverID=" + driver_id + " AND  T.closetripDate between '"
						+ driverStart + "' AND '" + driverEnd + "' OR T.tripSecDriverID=" + driver_id
						+ " AND  T.closetripDate between '" + driverStart + "' AND '" + driverEnd
						+ "' OR T.tripCleanerID=" + driver_id + " AND  T.closetripDate between '" + driverStart
						+ "' AND '" + driverEnd + "' ) AND T.companyId = " + companytId + " AND T.markForDelete = 0"
						, Object[].class);

		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setTripSheetNumber((Long) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setRouteName((String) result[3] + " "+(String) result[4]);

				if ((Date) result[5] != null) {
					list.setTripOpenDate(dateFormat_Name.format((Date) result[5]));
				}
				if ((Date) result[6] != null) {
					list.setClosetripDate(dateFormat_Name.format((Date) result[6]));
				}

				Double FristDriverBata = 0.0;
				if ((Double) result[7] != null && !(((Double) result[7]).equals(0.0))) {

					FristDriverBata = (Double) result[7];

				} else if ((Double) result[8] != null && !(((Double) result[7]).equals(0.0))) {

					FristDriverBata = (Double) result[8];

				} else if ((Double) result[9] != null && !(((Double) result[9]).equals(0.0))) {

					FristDriverBata = (Double) result[9];
				}

				list.setTripFristDriverBata(FristDriverBata);
				if(result[4] == null || result[4] == "") {
					list.setRouteName((String) result[10]);
				}
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * Get_FuelVendor_SearchTo_TripSheetDetailsIN_Select(java.lang.Long)
	 */
	@Transactional
	public TripSheetDto Get_FuelVendor_SearchTo_TripSheetDetailsIN_Select(Long tripSheetID, Integer companyId) {

		Query query = entityManager.createQuery(
				"SELECT T.tripSheetID, T.vid, V.vehicle_registration, VG.vGroup, T.tripFristDriverID, FD.driver_empnumber, FD.driver_firstname, "
						+ " T.tripSecDriverID, SD.driver_empnumber, SD.driver_firstname, V.vehicle_Odometer, V.vehicleFuelId, V.vehicleGroupId, T.tripSheetNumber from TripSheet AS T "
						+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver AS FD ON FD.driver_id = T.tripFristDriverID "
						+ " LEFT JOIN Driver AS SD ON SD.driver_id = T.tripSecDriverID " + " WHERE T.tripSheetNumber="
						+ tripSheetID + " AND T.markForDelete = 0 AND T.companyId = " + companyId + "");

		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		TripSheetDto select = new TripSheetDto();
		if (vehicle != null) {
			select.setTripSheetID((Long) vehicle[0]);
			select.setVid((Integer) vehicle[1]);
			select.setVehicle_registration((String) vehicle[2]);
			select.setVehicle_Group((String) vehicle[3]);
			select.setTripFristDriverID((Integer) vehicle[4]);
			select.setTripFristDriverName((String) vehicle[5] + " " + (String) vehicle[6]);
			select.setTripSecDriverID((Integer) vehicle[7]);
			select.setTripSecDriverName((String) vehicle[8] + " " + (String) vehicle[9]);
			select.setTripOpeningKM((Integer) vehicle[10]);
			select.setDispatchedBy(VehicleFuelType.getVehicleFuelTypeName((String) vehicle[11]));
			select.setVehicleGroupId((long) vehicle[12]);
			select.setTripSheetNumber((Long) vehicle[13]);
		} else {
			return null;
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * list_Report_TripSheet_Collection_DATE_Report(java.lang.String)
	 */
	@Transactional
	public List<TripSheetDto> list_Report_TripSheet_Collection_DATE_Report(String tRIP_DATE_FROM, String tRIP_DATE_TO,
			String TripsheetGroup, Integer companyId) {
		// Note: Collection Report tripsheet

		TypedQuery<Object[]> query = entityManager.createQuery(
				"Select T.tripSheetID, V.vehicle_registration, D1.driver_firstname, D1.driver_mobnumber, D2.driver_firstname, D2.driver_mobnumber, D3.driver_firstname,"
						+ " D3.driver_mobnumber, TR.routeName, T.tripOpenDate, T.closetripDate, T.tripTotalexpense, T.tripTotalincome,T.tripTotalAdvance, T.closeTripAmount , U.email "
						+ ", (SELECT b1.expenseAmount FROM TripSheetExpense AS b1 "
						+ " INNER JOIN TripExpense TE1 ON TE1.expenseID = b1.expenseId"
						+ " WHERE b1.tripsheet.tripSheetID = T.tripSheetID AND TE1.expenseName ='FIRST_DRIVER_ADVANCE' AND b1.companyId = "
						+ companyId + " AND b1.markForDelete = 0 " + " ORDER BY TE1.expenseName  ) AS FIRST_DRIVER_BATA "
						+ " , (SELECT b2.expenseAmount FROM TripSheetExpense AS b2 "
						+ " INNER JOIN TripExpense TE2 ON TE2.expenseID = b2.expenseId"
						+ "  WHERE b2.tripsheet.tripSheetID = T.tripSheetID  AND TE2.expenseName ='SECOND_DRIVER_ADVANCE' AND b2.companyId = "
						+ companyId + " AND b2.markForDelete = 0 "
						+ "  ORDER BY TE2.expenseName  ) AS SECOND_DRIVER_BATA "
						+ " , (SELECT b3.expenseAmount FROM TripSheetExpense AS b3"
						+ " INNER JOIN TripExpense TE3 ON TE3.expenseID = b3.expenseId"
						+ "  WHERE b3.tripsheet.tripSheetID = T.tripSheetID   AND TE3.expenseName ='CLEANER_ADVANCE' AND b3.companyId = "
						+ companyId + " AND b3.markForDelete = 0 " + "   ORDER BY TE3.expenseName ) AS CLEANER_ADVANCE "
						+ " , (SELECT b4.expenseAmount FROM TripSheetExpense AS b4 "
						+ " INNER JOIN TripExpense TE4 ON TE4.expenseID = b4.expenseId"
						+ "  WHERE b4.tripsheet.tripSheetID = T.tripSheetID   AND TE4.expenseName ='TOLL CHARGES' AND b4.companyId = "
						+ companyId + " AND b4.markForDelete = 0 " + "   ORDER BY TE4.expenseName ) AS TOLL_CHARGES "
						+ " , (SELECT b5.expenseAmount FROM TripSheetExpense AS b5 "
						+ " INNER JOIN TripExpense TE5 ON TE5.expenseID = b5.expenseId"
						+ "  WHERE b5.tripsheet.tripSheetID = T.tripSheetID   AND TE5.expenseName ='WELFARE EXPENSES'  AND b5.companyId = "
						+ companyId + " AND b5.markForDelete = 0" + "   ORDER BY TE5.expenseName ) AS WELFARE_EXPENSES "
						+ " , (SELECT b6.expenseAmount FROM TripSheetExpense AS b6 "
						+ " INNER JOIN TripExpense TE6 ON TE6.expenseID = b6.expenseId"
						+ "  WHERE b6.tripsheet.tripSheetID = T.tripSheetID   AND TE6.expenseName ='LUGGAGE BATA' AND b6.companyId = "
						+ companyId + " AND b6.markForDelete = 0 " + "   ORDER BY TE6.expenseName ) AS LUGGAGE_BATA "
						+ " , (SELECT SUM(b7.expenseAmount) FROM TripSheetExpense AS b7 "
						+ " INNER JOIN TripExpense TE7 ON TE7.expenseID = b7.expenseId"
						+ "  WHERE b7.tripsheet.tripSheetID = T.tripSheetID   AND TE7.expenseName IN ('VEHICLE WASHING CHARGES', 'PARKING CHARGES')  AND b7.companyId = "
						+ companyId + " AND b7.markForDelete = 0" + "   ORDER BY TE7.expenseName ) AS WASHING_PARKING "
						+ " , (SELECT b8.expenseAmount FROM TripSheetExpense AS b8 "
						+ " INNER JOIN TripExpense TE8 ON TE8.expenseID = b8.expenseId"
						+ "  WHERE b8.tripsheet.tripSheetID = T.tripSheetID   AND TE8.expenseName ='HALTING BATA' AND b8.companyId = "
						+ companyId + " AND b8.markForDelete = 0" + "   ORDER BY TE8.expenseName ) AS HALTING_BATA "
						+ " , (SELECT SUM(b9.expenseAmount) FROM TripSheetExpense AS b9 "
						+ " INNER JOIN TripExpense TE9 ON TE9.expenseID = b9.expenseId"
						+ "  WHERE b9.tripsheet.tripSheetID = T.tripSheetID  AND NOT TE9.expenseName IN ('FIRST_DRIVER_ADVANCE','SECOND_DRIVER_ADVANCE','CLEANER_ADVANCE', 'TOLL CHARGES','WELFARE EXPENSES', 'LUGGAGE BATA','VEHICLE WASHING CHARGES', 'PARKING CHARGES','HALTING BATA') "
						+ "  AND b9.companyId = " + companyId
						+ " AND b9.markForDelete = 0   ORDER BY TE9.expenseName ) AS OTHER_EXPENSES "
						+ ",  T.tripSheetNumber, T.routeName From TripSheet AS T"
						+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
						+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
						+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
						+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
						+ " LEFT JOIN User U ON U.id = T.closedById"
						+ " where ( T.tripStutesId="
						+ TripSheetStatus.TRIP_STATUS_CLOSED + " AND  T.closetripDate between '" + tRIP_DATE_FROM
						+ "' AND '" + tRIP_DATE_TO + "'  " + TripsheetGroup + " OR T.tripStutesId="
						+ TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED + " AND  T.closetripDate between '"
						+ tRIP_DATE_FROM + "' AND '" + tRIP_DATE_TO + "' " + TripsheetGroup + ") AND T.companyId = "
						+ companyId + " AND T.markForDelete = 0 ORDER BY T.tripSheetID asc",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setVehicle_registration((String) result[1]);
				list.setTripFristDriverName((String) result[2]);
				list.setTripFristDriverMobile((String) result[3]);

				list.setTripSecDriverName((String) result[4]);
				list.setTripSecDriverMobile((String) result[5]);
				list.setTripCleanerName((String) result[6]);
				list.setTripCleanerMobile((String) result[7]);

				list.setRouteName((String) result[8]);
				list.setCollectionTripOpenDate((java.util.Date) result[9]);
				list.setCloseACCtripDate((java.util.Date) result[10]);
				if(result[11] != null)
				list.setTripTotalexpense(Double.parseDouble(toFixedTwo.format(result[11])));
				if(result[12] != null)
				list.setTripTotalincome(Double.parseDouble(toFixedTwo.format(result[12])));
				if(result[13] != null)
				list.setTripTotalAdvance(Double.parseDouble(toFixedTwo.format(result[13])));
				if(result[14] != null)
				list.setCloseTripAmount(Double.parseDouble(toFixedTwo.format(result[14])));
				list.setClosedBy((String) result[15]);
				// collection Report Show
				if(result[16] != null)
				list.setCollectionFristDriverBata(Double.parseDouble(toFixedTwo.format(result[16])));
				if(result[17] != null)
				list.setCollectionSecondDriverBata(Double.parseDouble(toFixedTwo.format( result[17])));
				if(result[18] != null)
				list.setCollectionCleanerBata(Double.parseDouble(toFixedTwo.format(result[18])));
				if(result[19] != null)
				list.setCollectionTollCharge(Double.parseDouble(toFixedTwo.format(result[19])));
				if(result[20] != null)
				list.setCollectionWelfareExpense(Double.parseDouble(toFixedTwo.format(result[20])));
				if(result[21] != null)
				list.setCollectionLuggage(Double.parseDouble(toFixedTwo.format(result[21])));
				if(result[22] != null)
				list.setCollectionWashpark(Double.parseDouble(toFixedTwo.format(result[22])));
				if(result[23] != null)
				list.setCollectionHaltingBata(Double.parseDouble(toFixedTwo.format(result[23])));
				if(result[24] != null)
				list.setCollectionOtherExpense(Double.parseDouble(toFixedTwo.format(result[24])) );
				list.setTripSheetNumber((Long) result[25]);

				if(list.getRouteName() == null || list.getRouteName() == "") {
					list.setRouteName((String) result[26]);
				}
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * DELETE_TRIPSHEETEXPENSE_DRIVERHALT_ID(java.lang.Long)
	 */
	@Transactional
	public void DELETE_TRIPSHEETEXPENSE_DRIVERHALT_ID(Long dhid, Integer companyId) {
		// Delete Expense In TripSheet
		TripSheetExpenseRepository.DELETE_TRIPSHEETEXPENSE_DRIVERHALT_ID(dhid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripSheetService#
	 * DELETE_TRIPSHEETEXPENSE_FUEL_ID(java.lang.Long)
	 */
	@Transactional
	public void DELETE_TRIPSHEETEXPENSE_FUEL_ID(Long fuel_id) {
		// Delete Fuel In TripSheet
		TripSheetExpenseRepository.DELETE_TRIPSHEETEXPENSE_FUEL_ID(fuel_id);
	}

	@Transactional
	public Object[] count_TOTAL_TRIPSHEET_FIVEDAYS(String dayOne, String dayTwo, String dayThree, String dayFour,
			String dayFive, CustomUserDetails userDetails) throws Exception {

		Query queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

			queryt = entityManager.createQuery("SELECT COUNT(co),(SELECT COUNT(Pho) FROM TripSheet AS Pho "
					+ " where  Pho.tripOpenDate='" + dayOne + "' AND Pho.companyId = " + userDetails.getCompany_id()
					+ " AND Pho.markForDelete = 0), " + " (SELECT  COUNT(Pur) FROM TripSheet AS Pur "
					+ " where  Pur.tripOpenDate='" + dayTwo + "' AND Pur.companyId = " + userDetails.getCompany_id()
					+ " AND Pur.markForDelete = 0), " + " (SELECT  COUNT(RR) FROM TripSheet AS RR "
					+ " where  RR.tripOpenDate='" + dayThree + "' AND RR.companyId = " + userDetails.getCompany_id()
					+ " AND RR.markForDelete = 0), " + " (SELECT  COUNT(FE) FROM TripSheet AS FE "
					+ " where  FE.tripOpenDate='" + dayFour + "' AND FE.companyId = " + userDetails.getCompany_id()
					+ " AND FE.markForDelete = 0) " + " FROM TripSheet As co " + " Where co.tripOpenDate='" + dayFive
					+ "' AND co.companyId = " + userDetails.getCompany_id() + " AND co.markForDelete = 0");

		} else {
			queryt = entityManager.createQuery("SELECT COUNT(co),(SELECT COUNT(Pho) FROM TripSheet AS Pho "
					+ " INNER JOIN Vehicle V ON V.vid = Pho.vid "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " where  Pho.tripOpenDate='" + dayOne + "' AND Pho.companyId = "
					+ userDetails.getCompany_id() + " AND Pho.markForDelete = 0), "
					+ " (SELECT  COUNT(Pur) FROM TripSheet AS Pur "
					+ " INNER JOIN Vehicle VH ON VH.vid = Pur.vid "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VH.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " where  Pur.tripOpenDate='" + dayTwo + "' AND Pur.companyId = "
					+ userDetails.getCompany_id() + " AND Pur.markForDelete = 0), "
					+ " (SELECT  COUNT(RR) FROM TripSheet AS RR "
					+ " INNER JOIN Vehicle VI ON VI.vid = RR.vid "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VI.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " where  RR.tripOpenDate='" + dayThree + "' AND RR.companyId = "
					+ userDetails.getCompany_id() + " AND RR.markForDelete = 0), "
					+ " (SELECT  COUNT(FE) FROM TripSheet AS FE "
					+ " INNER JOIN Vehicle VJ ON VJ.vid = FE.vid "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VJ.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " where  FE.tripOpenDate='" + dayFour + "' AND FE.companyId = "
					+ userDetails.getCompany_id() + " AND FE.markForDelete = 0) " + " FROM TripSheet As co "
					+ " INNER JOIN Vehicle VK ON VK.vid = co.vid "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VK.vehicleGroupId AND VGP.user_id = "
					+ userDetails.getId() + "" + " Where co.tripOpenDate='" + dayFive + "' AND co.companyId = "
					+ userDetails.getCompany_id() + " AND co.markForDelete = 0");
		}
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	@Override
	public TripSheetDto getTripSheetByNumber(Long TripSheet_id, CustomUserDetails userDetails) throws Exception {
		Query query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

			query = entityManager.createQuery(
					"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
							+ " TR.routeName, TR.routeApproximateKM, TR.routrAttendance, TR.routeTotalLiter, T.tripOpenDate, T.closetripDate,"
							+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripClosingKMStatusId, T.tripFristDriverID, D1.driver_firstname,"
							+ " D1.driver_mobnumber, T.tripSecDriverID, D2.driver_firstname, D2.driver_mobnumber, T.tripCleanerID, D3.driver_firstname,"
							+ " D3.driver_mobnumber, T.tripTotalAdvance, T.tripTotalexpense, T.tripTotalincome, T.closeTripStatusId, D.driver_firstname,"
							+ " T.closeTripAmount, T.closeTripReference, U.firstName, U.lastName , T.closeACCtripDate, T.closeACCTripAmount, T.closeACCTripReference,"
							+ " T.tripCommentTotal, T.tripStutesId, U2.email, B.branch_name, U3.firstName, B2.branch_name, U4.email, B3.branch_name,"
							+ " T.dispatchedByTime, T.closedByTime, T.acclosedByTime, U5.email, T.created, U6.email, T.lastupdated, T.routeName "
							+ " FROM TripSheet T " 
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " LEFT JOIN Driver D ON D.driver_id = T.closeTripNameById  AND T.closeTripStatusId = "+TripSheetDto.TRIP_PAY_TO_OFFICE+ ""
							+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
							+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
							+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
							+ " LEFT JOIN User U On U.id = T.closeACCTripNameById"
							+ " LEFT JOIN User U2 On U2.id = T.dispatchedById"
							+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
							+ " LEFT JOIN User U3 On U3.id = T.closedById"
							+ " LEFT JOIN Branch AS B2 ON B2.branch_id = T.closedLocationId "
							+ " LEFT JOIN User U4 On U4.id = T.acclosedById"
							+ " LEFT JOIN Branch AS B3 ON B3.branch_id = T.acclosedLocationId "
							+ " LEFT JOIN User U5 On U5.id = T.createdById"
							+ " LEFT JOIN User U6 On U6.id = T.lastModifiedById"
							+ " WHERE T.tripSheetNumber =:id AND T.companyId =:companyId AND T.markForDelete = 0");
		} else {
			query = entityManager.createQuery(
					"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
							+ " TR.routeName, TR.routeApproximateKM, TR.routrAttendance, TR.routeTotalLiter, T.tripOpenDate, T.closetripDate,"
							+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripClosingKMStatusId, T.tripFristDriverID, D1.driver_firstname,"
							+ " D1.driver_mobnumber, T.tripSecDriverID, D2.driver_firstname, D2.driver_mobnumber, T.tripCleanerID, D3.driver_firstname,"
							+ " D3.driver_mobnumber, T.tripTotalAdvance, T.tripTotalexpense, T.tripTotalincome, T.closeTripStatusId, D.driver_firstname,"
							+ " T.closeTripAmount, T.closeTripReference, U.firstName, U.lastName , T.closeACCtripDate, T.closeACCTripAmount, T.closeACCTripReference,"
							+ " T.tripCommentTotal, T.tripStutesId, U2.email, B.branch_name, U3.firstName, B2.branch_name, U4.email, B3.branch_name,"
							+ " T.dispatchedByTime, T.closedByTime, T.acclosedByTime, U5.email, T.created, U6.email, T.lastupdated, T.routeName "
							+ " FROM TripSheet T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" 
							+ " LEFT JOIN Driver D ON D.driver_id = T.closeTripNameById  AND T.closeTripStatusId = "+TripSheetDto.TRIP_PAY_TO_OFFICE+ ""
							+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
							+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
							+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
							+ " LEFT JOIN User U On U.id = T.closeACCTripNameById"
							+ " LEFT JOIN User U2 On U2.id = T.dispatchedById"
							+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
							+ " LEFT JOIN User U3 On U3.id = T.closedById"
							+ " LEFT JOIN Branch AS B2 ON B2.branch_id = T.closedLocationId "
							+ " LEFT JOIN User U4 On U4.id = T.acclosedById"
							+ " LEFT JOIN Branch AS B3 ON B3.branch_id = T.acclosedLocationId "
							+ " LEFT JOIN User U5 On U5.id = T.createdById"
							+ " LEFT JOIN User U6 On U6.id = T.lastModifiedById"
							+ " WHERE T.tripSheetNumber =:id AND T.companyId =:companyId AND T.markForDelete = 0");

		}

		query.setParameter("id", TripSheet_id);
		query.setParameter("companyId", userDetails.getCompany_id());
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripSheetDto select;
		if (vehicle != null) {
			select = new TripSheetDto();
			select.setTripSheetID((Long) vehicle[0]);
			select.setTripSheetNumber((Long) vehicle[1]);
			select.setVid((Integer) vehicle[2]);
			select.setVehicle_registration((String) vehicle[3]);
			select.setVehicle_Group((String) vehicle[4]);
			select.setVehicleGroupId((long) vehicle[5]);
			select.setRouteID((Integer) vehicle[6]);
			select.setRouteName((String) vehicle[7]);
			select.setRouteApproximateKM((Integer) vehicle[8]);
			select.setRouteAttendancePoint((Double) vehicle[9]);
			select.setRouteTotalLiter((Double) vehicle[10]);
			select.setTripOpenDateOn((java.util.Date) vehicle[11]);
			select.setClosetripDateOn((java.util.Date) vehicle[12]);
			select.setTripBookref((String) vehicle[13]);
			select.setTripOpeningKM((Integer) vehicle[14]);
			select.setTripClosingKM((Integer) vehicle[15]);
			select.setTripUsageKM((Integer) vehicle[16]);
			select.setTripClosingKMStatusId((short) vehicle[17]);
			select.setTripFristDriverID((int) vehicle[18]);
			select.setTripFristDriverName((String) vehicle[19]);
			select.setTripFristDriverMobile((String) vehicle[20]);
			select.setTripSecDriverID((int) vehicle[21]);
			select.setTripSecDriverName((String) vehicle[22]);
			select.setTripSecDriverMobile((String) vehicle[23]);
			select.setTripCleanerID((int) vehicle[24]);
			select.setTripCleanerName((String) vehicle[25]);
			select.setTripCleanerMobile((String) vehicle[26]);
			select.setTripTotalAdvance((Double) vehicle[27]);
			select.setTripTotalexpense((Double) vehicle[28]);
			select.setTripTotalincome((Double) vehicle[29]);
			select.setCloseTripStatusId((short) vehicle[30]);
			select.setCloseTripNameBy((String) vehicle[31]);
			select.setCloseTripAmount((Double) vehicle[32]);
			select.setCloseTripReference((String) vehicle[33]);
			select.setCloseACCTripNameBy((String) vehicle[34] + " " + (String) vehicle[35]);
			select.setCloseACCtripDate((java.util.Date) vehicle[36]);
			select.setCloseACCTripAmount((Double) vehicle[37]);
			select.setCloseACCTripReference((String) vehicle[38]);
			select.setTripCommentTotal((Integer) vehicle[39]);
			select.setTripStutesId((short) vehicle[40]);
			select.setDispatchedBy((String) vehicle[41]);
			select.setDispatchedLocation((String) vehicle[42]);
			select.setClosedBy((String) vehicle[43]);
			select.setCloesdLocation((String) vehicle[44]);
			select.setAcclosedBy((String) vehicle[45]);
			select.setAccloesdLocation((String) vehicle[46]);
			select.setDispatchedByTimeOn((java.util.Date) vehicle[47]);
			select.setClosedByTimeOn((java.util.Date) vehicle[48]);
			select.setAcclosedByTimeOn((java.util.Date) vehicle[49]);
			select.setCreatedBy((String) vehicle[50]);
			select.setCreatedOn((java.util.Date) vehicle[51]);
			select.setLastModifiedBy((String) vehicle[52]);
			select.setLastupdatedOn((java.util.Date) vehicle[53]);
			if(select.getRouteName() == null || select.getRouteName() == "") {
				select.setRouteName((String) vehicle[54]);
			}

			if(select.getCloseTripStatusId() == TripSheetDto.TRIP_PAY_TO_DRIVER) {
				select.setCloseTripNameBy(TripSheetDto.TRIP_PAY_TO_OFFICE_NAME);
			}


		} else {
			return null;
		}

		return select;
	}

	@Override
	public TripSheetDto Get_Vehicle_Current_TripSheet_Id(Integer vehicle_ID, CustomUserDetails userDetails)
			throws Exception {
		Query query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

			query = entityManager.createQuery(
					"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
							+ " TR.routeName, TR.routeApproximateKM, TR.routrAttendance, TR.routeTotalLiter, T.tripOpenDate, T.closetripDate,"
							+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripClosingKMStatusId, T.tripFristDriverID, D1.driver_firstname,"
							+ " D1.driver_mobnumber, T.tripSecDriverID, D2.driver_firstname, D2.driver_mobnumber, T.tripCleanerID, D3.driver_firstname,"
							+ " D3.driver_mobnumber, T.tripTotalAdvance, T.tripTotalexpense, T.tripTotalincome, T.closeTripStatusId, D.driver_firstname,"
							+ " T.closeTripAmount, T.closeTripReference, U.firstName, U.lastName , T.closeACCtripDate, T.closeACCTripAmount, T.closeACCTripReference,"
							+ " T.tripCommentTotal, T.tripStutesId, U2.email, B.branch_name, U3.firstName, B2.branch_name, U4.email, B3.branch_name,"
							+ " T.dispatchedByTime, T.closedByTime, T.acclosedByTime, U5.email, T.created, U6.email, T.lastupdated, T.routeName "
							+ " FROM TripSheet T " 
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " LEFT JOIN Driver D ON D.driver_id = T.closeTripNameById  AND T.closeTripStatusId = "+TripSheetDto.TRIP_PAY_TO_OFFICE+ ""
							+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
							+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
							+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
							+ " LEFT JOIN User U On U.id = T.closeACCTripNameById"
							+ " LEFT JOIN User U2 On U2.id = T.dispatchedById"
							+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
							+ " LEFT JOIN User U3 On U3.id = T.closedById"
							+ " LEFT JOIN Branch AS B2 ON B2.branch_id = T.closedLocationId "
							+ " LEFT JOIN User U4 On U4.id = T.acclosedById"
							+ " LEFT JOIN Branch AS B3 ON B3.branch_id = T.acclosedLocationId "
							+ " LEFT JOIN User U5 On U5.id = T.createdById"
							+ " LEFT JOIN User U6 On U6.id = T.lastModifiedById"
							+ " WHERE V.vid =:id AND T.companyId =:companyId AND T.markForDelete = 0");
		} else {
			query = entityManager.createQuery(
					"SELECT T.tripSheetID, T.tripSheetNumber, T.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, T.routeID,"
							+ " TR.routeName, TR.routeApproximateKM, TR.routrAttendance, TR.routeTotalLiter, T.tripOpenDate, T.closetripDate,"
							+ " T.tripBookref, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, T.tripClosingKMStatusId, T.tripFristDriverID, D1.driver_firstname,"
							+ " D1.driver_mobnumber, T.tripSecDriverID, D2.driver_firstname, D2.driver_mobnumber, T.tripCleanerID, D3.driver_firstname,"
							+ " D3.driver_mobnumber, T.tripTotalAdvance, T.tripTotalexpense, T.tripTotalincome, T.closeTripStatusId, D.driver_firstname,"
							+ " T.closeTripAmount, T.closeTripReference, U.firstName, U.lastName , T.closeACCtripDate, T.closeACCTripAmount, T.closeACCTripReference,"
							+ " T.tripCommentTotal, T.tripStutesId, U2.email, B.branch_name, U3.firstName, B2.branch_name, U4.email, B3.branch_name,"
							+ " T.dispatchedByTime, T.closedByTime, T.acclosedByTime, U5.email, T.created, U6.email, T.lastupdated, T.routeName "
							+ " FROM TripSheet T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" 
							+ " LEFT JOIN Driver D ON D.driver_id = T.closeTripNameById  AND T.closeTripStatusId = "+TripSheetDto.TRIP_PAY_TO_OFFICE+ ""
							+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
							+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
							+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
							+ " LEFT JOIN User U On U.id = T.closeACCTripNameById"
							+ " LEFT JOIN User U2 On U2.id = T.dispatchedById"
							+ " LEFT JOIN Branch AS B ON B.branch_id = T.dispatchedLocationId "
							+ " LEFT JOIN User U3 On U3.id = T.closedById"
							+ " LEFT JOIN Branch AS B2 ON B2.branch_id = T.closedLocationId "
							+ " LEFT JOIN User U4 On U4.id = T.acclosedById"
							+ " LEFT JOIN Branch AS B3 ON B3.branch_id = T.acclosedLocationId "
							+ " LEFT JOIN User U5 On U5.id = T.createdById"
							+ " LEFT JOIN User U6 On U6.id = T.lastModifiedById"
							+ " WHERE V.vid =:id AND T.companyId =:companyId AND T.markForDelete = 0");

		}

		query.setParameter("id", vehicle_ID);
		query.setParameter("companyId", userDetails.getCompany_id());
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripSheetDto select;
		if (vehicle != null) {
			select = new TripSheetDto();
			select.setTripSheetID((Long) vehicle[0]);
			select.setTripSheetNumber((Long) vehicle[1]);
			select.setVid((Integer) vehicle[2]);
			select.setVehicle_registration((String) vehicle[3]);
			select.setVehicle_Group((String) vehicle[4]);
			select.setVehicleGroupId((long) vehicle[5]);
			select.setRouteID((Integer) vehicle[6]);
			select.setRouteName((String) vehicle[7]);
			select.setRouteApproximateKM((Integer) vehicle[8]);
			select.setRouteAttendancePoint((Double) vehicle[9]);
			select.setRouteTotalLiter((Double) vehicle[10]);
			select.setTripOpenDateOn((java.util.Date) vehicle[11]);
			select.setClosetripDateOn((java.util.Date) vehicle[12]);
			select.setTripBookref((String) vehicle[13]);
			select.setTripOpeningKM((Integer) vehicle[14]);
			select.setTripClosingKM((Integer) vehicle[15]);
			select.setTripUsageKM((Integer) vehicle[16]);
			select.setTripClosingKMStatusId((short) vehicle[17]);
			select.setTripFristDriverID((int) vehicle[18]);
			select.setTripFristDriverName((String) vehicle[19]);
			select.setTripFristDriverMobile((String) vehicle[20]);
			select.setTripSecDriverID((int) vehicle[21]);
			select.setTripSecDriverName((String) vehicle[22]);
			select.setTripSecDriverMobile((String) vehicle[23]);
			select.setTripCleanerID((int) vehicle[24]);
			select.setTripCleanerName((String) vehicle[25]);
			select.setTripCleanerMobile((String) vehicle[26]);
			select.setTripTotalAdvance((Double) vehicle[27]);
			select.setTripTotalexpense((Double) vehicle[28]);
			select.setTripTotalincome((Double) vehicle[29]);
			select.setCloseTripStatusId((short) vehicle[30]);
			select.setCloseTripNameBy((String) vehicle[31]);
			select.setCloseTripAmount((Double) vehicle[32]);
			select.setCloseTripReference((String) vehicle[33]);
			select.setCloseACCTripNameBy((String) vehicle[34] + " " + (String) vehicle[35]);
			select.setCloseACCtripDate((java.util.Date) vehicle[36]);
			select.setCloseACCTripAmount((Double) vehicle[37]);
			select.setCloseACCTripReference((String) vehicle[38]);
			select.setTripCommentTotal((Integer) vehicle[39]);
			select.setTripStutesId((short) vehicle[40]);
			select.setDispatchedBy((String) vehicle[41]);
			select.setDispatchedLocation((String) vehicle[42]);
			select.setClosedBy((String) vehicle[43]);
			select.setCloesdLocation((String) vehicle[44]);
			select.setAcclosedBy((String) vehicle[45]);
			select.setAccloesdLocation((String) vehicle[46]);
			select.setDispatchedByTimeOn((java.util.Date) vehicle[47]);
			select.setClosedByTimeOn((java.util.Date) vehicle[48]);
			select.setAcclosedByTimeOn((java.util.Date) vehicle[49]);
			select.setCreatedBy((String) vehicle[50]);
			select.setCreatedOn((java.util.Date) vehicle[51]);
			select.setLastModifiedBy((String) vehicle[52]);
			select.setLastupdatedOn((java.util.Date) vehicle[53]);
			if(select.getRouteName() == null || select.getRouteName() == "") {
				select.setRouteName((String) vehicle[54]);
			}

			if(select.getCloseTripStatusId() == TripSheetDto.TRIP_PAY_TO_DRIVER) {
				select.setCloseTripNameBy(TripSheetDto.TRIP_PAY_TO_OFFICE_NAME);
			}

		} else {
			return null;
		}

		return select;
	}

	@Override
	public List<TripSheetDto> list_Report_TripSheet_Difference_Km_Liter_List(String tRIP_ROUTE, String dateRangeFrom,
			String dateRangeTo, CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"Select T.tripSheetID, V.vehicle_registration, D1.driver_firstname, D2.driver_firstname, "
							+ "  TR.routeName, T.tripOpenDate, T.closetripDate, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, TR.routeApproximateKM,"
							+ " TR.routeTotalLiter, ROUND(SUM(F.fuel_liters),2), TR.routeName , T.tripSheetNumber, T.routeName,"
							+ " D1.driver_Lastname,D1.driver_fathername, D2.driver_Lastname,D2.driver_fathername From TripSheet AS T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " LEFT JOIN Driver D ON D.driver_id = T.closeTripNameById  AND T.closeTripStatusId = "+TripSheetDto.TRIP_PAY_TO_OFFICE+ ""
							+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
							+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
							+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
							+ " LEFT JOIN Fuel AS F ON T.tripSheetID = F.fuel_TripsheetID AND F.markForDelete = 0 AND T.companyId = F.companyId"
							+ " where  T.closetripDate between '"
							+ dateRangeFrom + "' AND '" + dateRangeTo + "' "
							+ " AND T.tripStutesId = "+TripSheetStatus.TRIP_STATUS_CLOSED+" AND T.companyId = " + userDetails.getCompany_id()
							+ " AND T.markForDelete = 0 " + tRIP_ROUTE + "  GROUP BY T.tripSheetID ",
							Object[].class);
		} else {
			query = entityManager.createQuery(
					"Select T.tripSheetID, V.vehicle_registration, D1.driver_firstname, D2.driver_firstname, "
							+ "  TR.routeName, T.tripOpenDate, T.closetripDate, T.tripOpeningKM, T.tripClosingKM, T.tripUsageKM, TR.routeApproximateKM,"
							+ " TR.routeTotalLiter, ROUND(SUM(F.fuel_liters),2), TR.routeName , T.tripSheetNumber, T.routeName,"
							+ "D1.driver_Lastname,D1.driver_fathername, D2.driver_Lastname,D2.driver_fathername From TripSheet AS T "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + " "
							+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID "
							+ " LEFT JOIN Driver D ON D.driver_id = T.closeTripNameById  AND T.closeTripStatusId = "+TripSheetDto.TRIP_PAY_TO_OFFICE+ ""
							+ " LEFT JOIN Driver D1 ON D1.driver_id = T.tripFristDriverID "
							+ " LEFT JOIN Driver D2 ON D2.driver_id = T.tripSecDriverID "
							+ " LEFT JOIN Driver D3 ON D3.driver_id = T.tripCleanerID "
							+ " LEFT JOIN Fuel AS F ON T.tripSheetID = F.fuel_TripsheetID AND F.markForDelete = 0 AND T.companyId = F.companyId "
							+ " where  T.closetripDate between '"
							+ dateRangeFrom + "' AND '" + dateRangeTo + "' " 
							+ " AND T.tripStutesId="
							+ TripSheetStatus.TRIP_STATUS_CLOSED + " AND T.companyId = " + userDetails.getCompany_id()
							+ " AND T.markForDelete = 0 " + tRIP_ROUTE + "  GROUP BY T.tripSheetID ",
							Object[].class);
		}

		List<Object[]> results = query.getResultList();

		List<TripSheetDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetDto>();
			TripSheetDto list = null;
			for (Object[] result : results) {
				list = new TripSheetDto();

				list.setTripSheetID((Long) result[0]);
				list.setVehicle_registration((String) result[1]);
				list.setTripFristDriverName((String) result[2]);
				list.setTripSecDriverName((String) result[3]);

				list.setRouteName((String) result[4]);
				if ((java.util.Date) result[5] != null) {
					list.setTripOpenDate(dateFormat_Name.format((java.util.Date) result[5]));
				}
				if ((java.util.Date) result[6] != null) {
					list.setClosetripDate(dateFormat_Name.format((java.util.Date) result[6]));
				}

				list.setTripOpeningKM((Integer) result[7]);
				list.setTripClosingKM((Integer) result[8]);

				Integer TripUsageKM = 0, RouteApproximateKM = 0, TripDiffernceKM = 0;
				if ((Integer) result[9] != null) {
					TripUsageKM = (Integer) result[9];
				}

				if ((Integer) result[10] != null) {
					RouteApproximateKM = (Integer) result[10];
				}

				list.setTripUsageKM(TripUsageKM);
				list.setRouteApproximateKM(RouteApproximateKM);

				TripDiffernceKM = TripUsageKM - RouteApproximateKM;
				list.setTripDiffernceKM(TripDiffernceKM);

				Double TripUsageLiter = 0.0, RouteTotalLiter = 0.0, TripDiffernceLiter = 0.0;

				if ((Double) result[11] != null) {
					RouteTotalLiter = (Double) result[11];
				}
				if ((Double) result[12] != null) {
					TripUsageLiter = (Double) result[12];
				}
				list.setTripUsageLiter(TripUsageLiter);
				list.setRouteTotalLiter(RouteTotalLiter);

				TripDiffernceLiter = TripUsageLiter - RouteTotalLiter;
				list.setTripDiffernceLiter(round(TripDiffernceLiter, 2));

				list.setRouteName((String) result[13]);
				list.setTripSheetNumber((Long) result[14]);

				if(list.getRouteName() == null || list.getRouteName() == "") {
					list.setRouteName((String) result[15]);
				}
				
				if(result[16]!= null &&!((String)result[16]).trim().equals(""))
					list.setTripFristDriverName(list.getTripFristDriverName()+" "+ result[16]);	
				
				if(result[17]!= null &&!((String)result[17]).trim().equals(""))
					list.setTripFristDriverName(list.getTripFristDriverName()+"-"+ result[17]);	
				
				if(result[18]!= null &&!((String)result[18]).trim().equals(""))
					list.setTripSecDriverName(list.getTripSecDriverName()+" "+ result[18]);
				
				if(result[19]!= null &&!((String)result[19]).trim().equals(""))
					list.setTripSecDriverName(list.getTripSecDriverName()+"-"+ result[19]);
				
				if(list.getCloseTripStatusId() == TripSheetDto.TRIP_PAY_TO_DRIVER) {
					list.setCloseTripNameBy(TripSheetDto.TRIP_PAY_TO_OFFICE_NAME);
				}
				Dtos.add(list);
			}

		}
		return Dtos;
	}

	@Override
	public Object[] getDriverAdvanceAndBataExpenseId(Integer companyId) throws Exception {
		try {
			Query queryt = entityManager.createQuery(
					"SELECT co.expenseID , (SELECT Pho.expenseID FROM TripExpense AS Pho where  Pho.expenseName ='FIRST_DRIVER_BATA' AND Pho.markForDelete = 0 AND Pho.companyId = "
							+ companyId + "), "
							+ " (SELECT  Pur.expenseID FROM TripExpense AS Pur where  Pur.expenseName ='SECOND_DRIVER_ADVANCE' AND Pur.markForDelete = 0 AND Pur.companyId = "
							+ companyId + "),"
							+ " (SELECT  RR.expenseID FROM TripExpense AS RR where  RR.expenseName ='SECOND_DRIVER_BATA'  and RR.companyId = "
							+ companyId + " and RR.markForDelete = 0),"
							+ " (SELECT  RRA.expenseID FROM TripExpense AS RRA where  RRA.expenseName ='CLEANER_ADVANCE'  and RRA.companyId = "
							+ companyId + " and RRA.markForDelete = 0),"
							+ " (SELECT  RRB.expenseID FROM TripExpense AS RRB where  RRB.expenseName ='CLEANER_BATA'  and RRB.companyId = "
							+ companyId + " and RRB.markForDelete = 0)"
							+ " FROM TripExpense As co Where co.expenseName = 'FIRST_DRIVER_ADVANCE' AND co.markForDelete = 0 AND co.companyId = "
							+ companyId + "");
			Object[] count = null;
			try {
				count = (Object[]) queryt.getSingleResult();
			} catch (NoResultException nre) {
				return null;
			}
			return count;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Object[] getExpenseIdWithName(Integer companyId) throws Exception {
		try {
			Query queryt = entityManager.createQuery(
					"SELECT co.expenseID , (SELECT Pho.expenseID FROM TripExpense AS Pho where  Pho.expenseName ='SECOND_DRIVER_ADVANCE' AND Pho.markForDelete = 0 AND Pho.companyId = "
							+ companyId + "), "
							+ " (SELECT  Pur.expenseID FROM TripExpense AS Pur where  Pur.expenseName ='CLEANER_ADVANCE' AND Pur.markForDelete = 0 AND Pur.companyId = "
							+ companyId + "),"
							+ " (SELECT  RR.expenseID FROM TripExpense AS RR where  RR.expenseName ='TOLL CHARGES'  and RR.companyId = "
							+ companyId + " and RR.markForDelete = 0),"
							+ " (SELECT  RRA.expenseID FROM TripExpense AS RRA where  RRA.expenseName ='WELFARE EXPENSES'  and RRA.companyId = "
							+ companyId + " and RRA.markForDelete = 0),"
							+ " (SELECT  RRB.expenseID FROM TripExpense AS RRB where  RRB.expenseName ='LUGGAGE BATA'  and RRB.companyId = "
							+ companyId + " and RRB.markForDelete = 0),"
							+ " (SELECT  RRB.expenseID FROM TripExpense AS RRB where  RRB.expenseName ='VEHICLE WASHING CHARGES'  and RRB.companyId = "
							+ companyId + " and RRB.markForDelete = 0),"
							+ " (SELECT  RRB.expenseID FROM TripExpense AS RRB where  RRB.expenseName ='PARKING CHARGES'  and RRB.companyId = "
							+ companyId + " and RRB.markForDelete = 0),"
							+ " (SELECT  RRB.expenseID FROM TripExpense AS RRB where  RRB.expenseName ='HALTING BATA'  and RRB.companyId = "
							+ companyId + " and RRB.markForDelete = 0)"
							+ " FROM TripExpense As co Where co.expenseName = 'FIRST_DRIVER_ADVANCE' AND co.markForDelete = 0 AND co.companyId = "
							+ companyId + "");
			Object[] count = null;
			try {
				count = (Object[]) queryt.getSingleResult();
			} catch (NoResultException nre) {
				return null;
			}
			return count;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public List<TripSheetDto> Get_Vehicle_Current_TripSheet_Id(Integer vehicle_ID, Integer companyId) throws Exception {
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
						+ " TR.routeName, TS.tripOpenDate, TS.closetripDate,"
						+ " TS.tripBookref, TS.tripOpeningKM, TS.tripClosingKM, TS.tripUsageKM, TS.tripStutesId, TS.tripTotalAdvance,"
						+ " TS.tripTotalexpense, TS.tripTotalincome, TS.closeTripAmount, TS.closeACCTripAmount, TS.routeName, TS.dispatchedByTime "
						+ " from TripSheet TS " 
						+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
						+ " where TS.vid = "+vehicle_ID+" AND TS.companyId = " + companyId + " AND TS.markForDelete = 0 order by TS.tripSheetID desc",
						Object[].class);
		
		query.setMaxResults(PAGE_SIZE_TWENTY);
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
				select.setTripOpenDateOn((java.util.Date) vehicle[8]);
				select.setClosetripDateOn((java.util.Date) vehicle[9]);
				select.setTripBookref((String) vehicle[10]);
				select.setTripOpeningKM((Integer) vehicle[11]);
				select.setTripClosingKM((Integer) vehicle[12]);
				select.setTripUsageKM((Integer) vehicle[13]);
				select.setTripStutesId((short) vehicle[14]);
				select.setTripTotalAdvance((Double) vehicle[15]);
				select.setTripTotalexpense((Double) vehicle[16]);
				select.setTripTotalincome((Double) vehicle[17]);
				select.setCloseTripAmount((Double) vehicle[18]);
				select.setCloseACCTripAmount((Double) vehicle[19]);
				if(select.getRouteName() == null || select.getRouteName().trim().equals("")) {
					select.setRouteName((String) vehicle[20]);
				}
				if(vehicle[21] != null) {
					select.setDispatchedByTimeOn((Timestamp)vehicle[21]);
					select.setDispatchedByTime(dateTimeFormat2.format((Timestamp)vehicle[21]));
				}
				
				Dtos.add(select);
			}
		}
		return Dtos;

	}

	@Override
	public List<TripSheetDto> getTripSheetDetailsOfSelectedDate(String Date, Integer companyId) throws Exception {
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
						+ " TR.routeName, TS.tripOpenDate, TS.closetripDate,"
						+ " TS.tripBookref, TS.tripOpeningKM, TS.tripClosingKM, TS.tripUsageKM, TS.tripStutesId, TS.tripTotalAdvance,"
						+ " TS.tripTotalexpense, TS.tripTotalincome, TS.closeTripAmount, TS.closeACCTripAmount, TS.routeName "
						+ " from TripSheet TS " 
						+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
						+ " where TS.tripOpenDate = '"+Date+"' AND TS.companyId = " + companyId + " AND TS.markForDelete = 0 ",
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
				select.setTripOpenDateOn((java.util.Date) vehicle[8]);
				select.setClosetripDateOn((java.util.Date) vehicle[9]);
				select.setTripBookref((String) vehicle[10]);
				select.setTripOpeningKM((Integer) vehicle[11]);
				select.setTripClosingKM((Integer) vehicle[12]);
				select.setTripUsageKM((Integer) vehicle[13]);
				select.setTripStutesId((short) vehicle[14]);
				select.setTripTotalAdvance((Double) vehicle[15]);
				select.setTripTotalexpense((Double) vehicle[16]);
				select.setTripTotalincome((Double) vehicle[17]);
				select.setCloseTripAmount((Double) vehicle[18]);
				select.setCloseACCTripAmount((Double) vehicle[19]);
				if(select.getRouteName() == null || select.getRouteName() == "") {
					select.setRouteName((String) vehicle[20]);
				}

				Dtos.add(select);
			}
		}
		return Dtos;

	}

	@Override
	public List<TripSheetDto> Get_TripSheetListByNumber(Long vehicle_ID, Integer companyId) throws Exception {
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
						+ " TR.routeName, TS.tripOpenDate, TS.closetripDate,"
						+ " TS.tripBookref, TS.tripOpeningKM, TS.tripClosingKM, TS.tripUsageKM, TS.tripStutesId, TS.tripTotalAdvance,"
						+ " TS.tripTotalexpense, TS.tripTotalincome, TS.closeTripAmount, TS.closeACCTripAmount, TS.routeName "
						+ " from TripSheet TS " 
						+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
						+ " where TS.tripSheetNumber = "+vehicle_ID+" AND TS.companyId = " + companyId + " AND TS.markForDelete = 0 ",
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
				select.setTripOpenDateOn((java.util.Date) vehicle[8]);
				select.setClosetripDateOn((java.util.Date) vehicle[9]);
				select.setTripBookref((String) vehicle[10]);
				select.setTripOpeningKM((Integer) vehicle[11]);
				select.setTripClosingKM((Integer) vehicle[12]);
				select.setTripUsageKM((Integer) vehicle[13]);
				select.setTripStutesId((short) vehicle[14]);
				select.setTripTotalAdvance((Double) vehicle[15]);
				select.setTripTotalexpense((Double) vehicle[16]);
				select.setTripTotalincome((Double) vehicle[17]);
				select.setCloseTripAmount((Double) vehicle[18]);
				select.setCloseACCTripAmount((Double) vehicle[19]);
				if(select.getRouteName() == null || select.getRouteName() == "") {
					select.setRouteName((String) vehicle[20]);
				}
				Dtos.add(select);
			}
		}
		return Dtos;

	}

	@Override
	public int countTripSheetByNumber(Long tripSheetNumber, Integer companyId) throws Exception {

		return TripSheetDao.countTripSheetByNumber(tripSheetNumber, companyId);
	}
	
	@Override
	public TripSheet getTripSheet(Long TripSheet_id, Integer companyId) throws Exception {
		
		return TripSheetDao.getTripSheet(TripSheet_id, companyId);
	}
	
	@Override
	public TripSheetExpense getTripSheetExpensebyExpenseId(Long tripExpenseId, Integer companyId, Long tripSheetID) throws Exception {
		
		TypedQuery<Object[]>	query = entityManager.createQuery(
					"SELECT expenseAmount, expenseId from TripSheetExpense where tripSheetID = "+tripSheetID+" AND tripExpenseID = "+tripSheetID+" "
							+ " AND companyId = "+companyId+" AND markForDelete = 0",
					Object[].class);

		List<Object[]> results = query.getResultList();
		TripSheetExpense select = new TripSheetExpense();
		if (results != null && !results.isEmpty()) {
			
			for (Object[] vehicle : results) {
				select.setExpenseAmount((Double) vehicle[0]);
				select.setExpenseId((Integer) vehicle[1]);
			}
		}		
		
		return select;
		//return TripSheetIncomeRepository.getTripSheetIncomeByTripIdAndIncomeId(tripSheetID, incomeId);
	
	}
	
	@Override
	public TripSheetIncome getTripSheetIncomeByTripIdAndIncomeId(Long tripSheetID, Integer incomeId) throws Exception {
		
		TypedQuery<Object[]>	query = entityManager.createQuery(
					"SELECT SUM(incomeAmount), incomeId from TripSheetIncome where tripSheetID = "+tripSheetID+" AND incomeId = "+incomeId+" AND markForDelete = 0",
					Object[].class);

		List<Object[]> results = query.getResultList();
		TripSheetIncome select = new TripSheetIncome();
		if (results != null && !results.isEmpty()) {
			
			for (Object[] vehicle : results) {
				if(select.getIncomeAmount() == null) {
					select.setIncomeAmount((Double) vehicle[0]);
				}else {
					select.setIncomeAmount((Double) vehicle[0]+ select.getIncomeAmount());
				}
				
				select.setIncomeId((Integer) vehicle[1]);
			}
		}		
		
		return select;
	}
	
	@Override
	public List<TripSheetDto> getTripSheetDetailsInMonthByVid(Integer vid, Timestamp fromDate, Timestamp toDate)
			throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate,"
							+ " TS.tripBookref, TS.tripOpeningKM, TS.tripClosingKM, TS.tripUsageKM, TS.tripStutesId, TS.tripTotalAdvance,"
							+ " TS.tripTotalexpense, TS.tripTotalincome, TS.closeTripAmount, TS.closeACCTripAmount "
							+ " from TripSheet TS " 
							+ " where TS.vid = "+vid+" AND TS.closetripDate between '"+fromDate+"' AND '"+toDate+"' AND TS.tripStutesId IN (3, 4) "
									+ " AND TS.markForDelete = 0 ",
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
					select.setTripOpenDateOn((java.util.Date) vehicle[3]);
					select.setClosetripDateOn((java.util.Date) vehicle[4]);
					select.setTripBookref((String) vehicle[5]);
					select.setTripOpeningKM((Integer) vehicle[6]);
					select.setTripClosingKM((Integer) vehicle[7]);
					select.setTripUsageKM((Integer) vehicle[8]);
					select.setTripStutesId((short) vehicle[9]);
					select.setTripTotalAdvance((Double) vehicle[10]);
					select.setTripTotalexpense((Double) vehicle[11]);
					select.setTripTotalincome((Double) vehicle[12]);
					select.setCloseTripAmount((Double) vehicle[13]);
					select.setCloseACCTripAmount((Double) vehicle[14]);
					
					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetDto> getTripSheetDetailsInMonthByRouteId(Integer vid, Timestamp fromDate, Timestamp toDate,
			Integer routeId) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate,"
							+ " TS.tripBookref, TS.tripOpeningKM, TS.tripClosingKM, TS.tripUsageKM, TS.tripStutesId, TS.tripTotalAdvance,"
							+ " TS.tripTotalexpense, TS.tripTotalincome, TS.closeTripAmount, TS.closeACCTripAmount "
							+ " from TripSheet TS " 
							+ " where TS.routeID = "+routeId+" AND TS.vid = "+vid+" AND TS.closetripDate between '"+fromDate+"' AND '"+toDate+"' AND TS.tripStutesId IN (3, 4) "
									+ " AND TS.markForDelete = 0 ",
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
					select.setTripOpenDateOn((java.util.Date) vehicle[3]);
					select.setClosetripDateOn((java.util.Date) vehicle[4]);
					select.setTripBookref((String) vehicle[5]);
					select.setTripOpeningKM((Integer) vehicle[6]);
					select.setTripClosingKM((Integer) vehicle[7]);
					select.setTripUsageKM((Integer) vehicle[8]);
					select.setTripStutesId((short) vehicle[9]);
					select.setTripTotalAdvance((Double) vehicle[10]);
					select.setTripTotalexpense((Double) vehicle[11]);
					select.setTripTotalincome((Double) vehicle[12]);
					select.setCloseTripAmount((Double) vehicle[13]);
					select.setCloseACCTripAmount((Double) vehicle[14]);
					
					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetDto> getTripSheetDetailsInMonthByVehicleGroupId(long vehicleGroupId, Timestamp fromDate,
			Timestamp toDate) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate,"
							+ " TS.tripBookref, TS.tripOpeningKM, TS.tripClosingKM, TS.tripUsageKM, TS.tripStutesId, TS.tripTotalAdvance,"
							+ " TS.tripTotalexpense, TS.tripTotalincome, TS.closeTripAmount, TS.closeACCTripAmount "
							+ " from TripSheet TS "
							+ " INNER JOIN Vehicle V ON V.vid = TS.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId" 
							+ " where VG.gid = "+vehicleGroupId+" AND TS.closetripDate between '"+fromDate+"' AND '"+toDate+"' AND TS.tripStutesId IN (3,4)"
							+ " AND TS.markForDelete = 0 ORDER BY TS.vid ",
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
					select.setTripOpenDateOn((java.util.Date) vehicle[3]);
					select.setClosetripDateOn((java.util.Date) vehicle[4]);
					select.setTripBookref((String) vehicle[5]);
					select.setTripOpeningKM((Integer) vehicle[6]);
					select.setTripClosingKM((Integer) vehicle[7]);
					select.setTripUsageKM((Integer) vehicle[8]);
					select.setTripStutesId((short) vehicle[9]);
					select.setTripTotalAdvance((Double) vehicle[10]);
					select.setTripTotalexpense((Double) vehicle[11]);
					select.setTripTotalincome((Double) vehicle[12]);
					select.setCloseTripAmount((Double) vehicle[13]);
					select.setCloseACCTripAmount((Double) vehicle[14]);
					
					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Integer getVehicleRunKMByVid(Integer vid, Timestamp fromDate, Timestamp toDate) throws Exception {
		
		return TripSheetDao.getVehicleRunKMByVid(vid, fromDate, toDate);
	}
	
	@Override
	public HashMap<Integer, Long> getVehicleRunKMByGroupId(Long groupId, Timestamp fromDate, Timestamp toDate)
			throws Exception {
		HashMap<Integer, Long>		hashMap		= null;
		try {
			TypedQuery<Object[]> query = null;
			hashMap	= new HashMap<>();
			query = entityManager.createQuery(
					" SELECT TS.vid, SUM(TS.tripUsageKM)"
							+ "FROM TripSheet TS "
							+ "INNER JOIN Vehicle V ON V.vid = TS.vid"
							+ " where V.vehicleGroupId = "+groupId+" AND TS.closetripDate between '"+fromDate+"' AND '"+toDate+"' AND TS.markForDelete = 0 AND TS.tripStutesId IN (3,4) "
							+ " GROUP BY TS.vid",
							Object[].class);
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				for (Object[] vehicle : results) {
					hashMap.put((Integer)vehicle[0], (Long)vehicle[1]);
				}
			}
			return hashMap;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetDto> getDriverPerformanceOnTrip(Integer did, String dateRangeFrom,
			String dateRangeTo) throws Exception {
		
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT T.tripFristDriverID, T.tripUsageKM, T.created, T.closetripDate, T.tripSheetID, D.driver_firstname, D.driver_Lastname, "
							+ " D.driver_empnumber, D.driver_city, V.vehicle_ExpectedMileage, D.driver_perdaySalary FROM TripSheet as T "
							+ " INNER JOIN Driver AS D ON D.driver_id = T.tripFristDriverID "
							+ " INNER JOIN Vehicle AS V ON V.vid = T.vid "
							+ " Where T.markForDelete=0 AND T.tripFristDriverID = "+did+" "
							+ " AND T.companyId = " + userDetails.getCompany_id() + " AND T.created BETWEEN '" + dateRangeFrom + "' AND  '"
							+ dateRangeTo + "'  ORDER BY T.created desc ",		
							Object[].class);
			
			
			List<Object[]> results = queryt.getResultList();

			List<TripSheetDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				Dtos = new ArrayList<TripSheetDto>();
				TripSheetDto list = null;
				for (Object[] result : results) {
					list = new TripSheetDto();
					list.setTripFristDriverID((Integer) result[0]);
					list.setTripUsageKM((Integer) result[1]);
					list.setCreatedOn((Date) result[2]);
					list.setClosetripDateOn((Date) result[3]);
					list.setTripSheetID((long) result[4]);
					list.setTripFristDriverName((String) result[5]);
					list.setTripFristDriverLastName((String) result[6]);
					list.setDriverEmpNumber((String) result[7]);
					list.setDriverCity((String) result[8]);
					list.setVehicle_ExpectedMileage((double) result[9]);
					list.setDriverSalary((Double) result[10]);
					Dtos.add(list);
				}
			}
			
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetIncomeDto> getVehicleIncomeDetailsOfMonthByIncomeId(TripSheetIncomeDto incomeDto)
			throws Exception {

		Query query = entityManager.createQuery(
			"SELECT T.tripincomeID, TI.incomeName, T.incomeAmount, TS.tripOpenDate, TS.closetripDate, TS.tripSheetID, TS.tripSheetNumber"
						+ "  FROM TripSheetIncome T " 
						+ " INNER JOIN TripIncome TI ON TI.incomeID = T.incomeId "
						+ " INNER JOIN TripSheet TS ON TS.tripSheetID = T.tripsheet.tripSheetID"
						+ " WHERE T.incomeId = "+incomeDto.getIncomeId()+" AND TS.vid = "+incomeDto.getVid()+" AND TS.closetripDate between '"+incomeDto.getFromDate()+"'"
								+ " AND '"+incomeDto.getToDate()+"' AND T.companyId = "+incomeDto.getCompanyId()+" AND T.markForDelete = 0 AND TS.markForDelete = 0");

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetIncomeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetIncomeDto>();
			TripSheetIncomeDto select;
			for (Object[] vehicle : results) {
				
				if (vehicle != null) {
					
					select = new TripSheetIncomeDto();
					
					if(select != null) {
					select.setTripincomeID((Long) vehicle[0]);
					select.setIncomeName((String) vehicle[1]);
					select.setIncomeAmount((Double) vehicle[2]);
					if(vehicle[3] != null)
						select.setFromDate(dateFormat_Name.format(vehicle[3]));
					if(vehicle[4] != null)
						select.setToDate(dateFormat_Name.format(vehicle[4]));
					
					select.setTripSheetId((Long) vehicle[5]);
					select.setTripSheetNumber((Long) vehicle[6]);
					

					Dtos.add(select);
					}
				}
			}
		}

		return Dtos;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getVehicleExpenseDetailsOfMonthByExpenseId(TripSheetIncomeDto incomeDto)
			throws Exception {

		Query query = entityManager.createQuery(
			"SELECT T.tripExpenseID, TI.expenseName, T.expenseAmount, TS.tripOpenDate, TS.closetripDate, TS.tripSheetID, TS.tripSheetNumber"
						+ "  FROM TripSheetExpense T " 
						+ " INNER JOIN TripExpense TI ON TI.expenseID = T.expenseId AND (T.fuel_id IS NULL OR T.fuel_id = 0) AND (T.DHID IS NULL OR T.DHID = 0)"
						+ " INNER JOIN TripSheet TS ON TS.tripSheetID = T.tripsheet.tripSheetID"
						+ " WHERE T.expenseId = "+incomeDto.getIncomeId()+" AND TS.vid = "+incomeDto.getVid()+" AND TS.closetripDate between '"+incomeDto.getFromDate()+"'"
								+ " AND '"+incomeDto.getToDate()+"' AND T.companyId = "+incomeDto.getCompanyId()+" AND T.markForDelete = 0 AND TS.markForDelete = 0");

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetExpenseDto>();
			TripSheetExpenseDto select;
			for (Object[] vehicle : results) {
				
				if (vehicle != null) {
					
					select = new TripSheetExpenseDto();
					
					if(select != null) {
					select.setTripExpenseID((Long) vehicle[0]);
					select.setExpenseName((String) vehicle[1]);
					select.setExpenseAmount((Double) vehicle[2]);
					if(vehicle[3] != null)
						select.setFromDate(dateFormat_Name.format(vehicle[3]));
					if(vehicle[4] != null)
						select.setToDate(dateFormat_Name.format(vehicle[4]));
					
					select.setTripSheetId((Long) vehicle[5]);
					select.setTripSheetNumber((Long) vehicle[6]);
					
					Dtos.add(select);
					}
				}
			}
		}

		return Dtos;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getVehicleFastTagDetailsOfMonthByExpenseId(TripSheetIncomeDto incomeDto)
			throws Exception {

		Query query = entityManager.createQuery(
			"SELECT T.tollExpensesDetailsId, TI.expenseName, T.transactionAmount, TS.tripOpenDate, TS.closetripDate, TS.tripSheetID, TS.tripSheetNumber"
						+ "  FROM TollExpensesDetails T " 
						+ " INNER JOIN TripExpense TI ON TI.expenseID = "+incomeDto.getIncomeId()+""
						+ " INNER JOIN TripSheet TS ON TS.tripSheetID = T.tripSheetId"
						+ " WHERE TS.vid = "+incomeDto.getVid()+" AND TS.closetripDate between '"+incomeDto.getFromDate()+"'"
								+ " AND '"+incomeDto.getToDate()+"' AND T.companyId = "+incomeDto.getCompanyId()+" AND T.markForDelete = 0 AND TS.markForDelete = 0");

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetExpenseDto>();
			TripSheetExpenseDto select;
			for (Object[] vehicle : results) {
				
				if (vehicle != null) {
					
					select = new TripSheetExpenseDto();
					
					if(select != null) {
					select.setTripExpenseID((Long) vehicle[0]);
					select.setExpenseName((String) vehicle[1]);
					select.setExpenseAmount((Double) vehicle[2]);
					if(vehicle[3] != null)
						select.setFromDate(dateFormat_Name.format(vehicle[3]));
					if(vehicle[4] != null)
						select.setToDate(dateFormat_Name.format(vehicle[4]));
					
					select.setTripSheetId((Long) vehicle[5]);
					select.setTripSheetNumber((Long) vehicle[6]);
					
					Dtos.add(select);
					}
				}
			}
		}

		return Dtos;
	}
	
	@Override
	public Double getGPSUsageKM(HashMap<String, Object> configuration, ValueObject valueObject) throws Exception {
		try {
				
			
			return vehicleGPSDetailsService.getVehicleGpsUsageKM(configuration, valueObject);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public Long getALLEmailTripDailyWorkStatus(String startDate, String endDate,Integer companyId)
			throws Exception {
		Query queryt = 	null;
		int flavor = companyConfigurationService.getTripSheetFlavor(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		
		if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			queryt = entityManager.createQuery(
					"SELECT COUNT(T.tripSheetID) "
							+ " From TripSheet as T "
							+ " WHERE T.tripOpenDate between '"+startDate+"' AND '"+endDate+"' AND T.companyId = "+companyId+" AND T.markForDelete = 0 ");
		}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {

			queryt = entityManager.createQuery(
					"SELECT COUNT(T.TRIPCOLLID) "
							+ " From TripCollectionSheet as T "
							+ " WHERE T.TRIP_OPEN_DATE between '"+startDate+"' AND '"+endDate+"' AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0 ");
		
		}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

			queryt = entityManager.createQuery(
					"SELECT COUNT(T.TRIPDAILYID) "
							+ " From TripDailySheet as T "
							+ " WHERE T.TRIP_OPEN_DATE between '"+startDate+"' AND '"+endDate+"' AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0 ");
		
		}else {

			queryt = entityManager.createQuery(
					"SELECT COUNT(T.tripSheetID) "
							+ " From TripSheet as T "
							+ " WHERE T.created between '"+startDate+"' AND '"+endDate+"' AND T.companyId = "+companyId+" AND T.markForDelete = 0 ");
		
		}
		
		
		
		
		Long count = (long) 0;

		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}	
	
	@Override
	public Long getTripSheetCreatedCount(String startDate, String endDate,Integer companyId) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				" SELECT COUNT(T.tripSheetID) "
						+ " From TripSheet as T "
						+ " WHERE T.tripOpenDate between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId+" "						
						+ " AND T.markForDelete = 0 ");
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}
	
	
	@Override
	public HashMap<Integer, Long> getALLEmailTripDailyWorkStatusHM(String startDate, String endDate, Integer companyId)
			throws Exception {
		TypedQuery<Object[]> 	typedQuery = null;
		HashMap<Integer, Long>	map		   = null;
		
		int flavor = companyConfigurationService.getTripSheetFlavor(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		
		if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			typedQuery = entityManager.createQuery(
					"SELECT COUNT(T.tripSheetID),T.companyId "
							+ " From TripSheet as T "
							+ " WHERE T.tripOpenDate between '"+startDate+"' AND '"+endDate+"' AND T.companyId = "+companyId+" AND T.markForDelete = 0 ", Object[].class);
		}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {

			typedQuery = entityManager.createQuery(
					"SELECT COUNT(T.TRIPCOLLID), T.COMPANY_ID "
							+ " From TripCollectionSheet as T "
							+ " WHERE T.TRIP_OPEN_DATE between '"+startDate+"' AND '"+endDate+"' AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0 ", Object[].class);
		
		}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {

			typedQuery = entityManager.createQuery(
					"SELECT COUNT(T.TRIPDAILYID), T.COMPANY_ID "
							+ " From TripDailySheet as T "
							+ " WHERE T.TRIP_OPEN_DATE between '"+startDate+"' AND '"+endDate+"' AND T.COMPANY_ID = "+companyId+" AND T.markForDelete = 0 ", Object[].class);
		
		}else {

			typedQuery = entityManager.createQuery(
					"SELECT COUNT(T.tripSheetID), T.companyId "
							+ " From TripSheet as T "
							+ " WHERE T.created between '"+startDate+"' AND '"+endDate+"' AND T.companyId = "+companyId+" AND T.markForDelete = 0 ", Object[].class);
		
		}
		
		List<Object[]> results = typedQuery.getResultList();

		if (results != null && !results.isEmpty()) {
			
			map	= new HashMap<>();
			
			for (Object[] result : results) {
				
				map.put((Integer)result[1], (Long)result[0]);
				
			}
		}
		return map;
	}
	
	/**********/
	
	//Trip In Open State Start
	@Override
	public Long getTripInOpenState(String startDate, String endDate,Integer companyId)	throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				" SELECT COUNT(T.tripSheetID) "
						+ " From TripSheet as T "
						+ " WHERE T.tripOpenDate between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId						
						+ " AND T.tripStutesId IN("+TripSheetStatus.TRIP_STATUS_DISPATCHED+", "+TripSheetStatus.TRIP_STATUS_SAVED+") AND T.markForDelete = 0 ");
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}
	//Trip In Open State Stop
	
	/**********/
	//Trip Close Count Logic Start
	@Override
	public TripSheetDto getTripClosed(String startDate, String endDate,Integer companyId)
			throws Exception {
		Query queryt = 	null;
		int flavor = companyConfigurationService.getTripSheetFlavor(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		
		if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
			queryt = entityManager.createQuery(
					" SELECT COUNT(T.tripSheetID), SUM(T.tripUsageKM) "
					+ " From TripSheet as T "					
					+ " WHERE T.tripOpenDate between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId
					+ " AND T.tripStutesId = " + TripSheetStatus.TRIP_STATUS_CLOSED + " AND T.markForDelete = 0 ");
		}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
			
		
		}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
			queryt = entityManager.createQuery(
					"SELECT COUNT(T.TRIPDAILYID), SUM(T.TRIP_USAGE_KM) "
							+ " From TripDailySheet as T "
							+ " WHERE T.TRIP_OPEN_DATE between '"+startDate+"' AND '"+endDate+"' AND T.COMPANY_ID = "+companyId
							+ " AND T.TRIP_STATUS_ID = " + TripSheetStatus.TRIP_STATUS_CLOSED + " AND T.markForDelete = 0 ");
		
		}else {
			queryt = entityManager.createQuery(
					" SELECT COUNT(T.tripSheetID), SUM(T.tripUsageKM) "
							+ " From TripSheet as T "					
							+ " WHERE T.closetripDate between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId
							+ " AND T.tripStutesId = " + TripSheetStatus.TRIP_STATUS_CLOSED + " AND T.markForDelete = 0 ");
		
		}
		
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) queryt.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripSheetDto select;
		if (vehicle != null) {
			select = new TripSheetDto();
			select.setTripSheetID((Long) vehicle[0]);
			select.setTripSheetNumber((Long) vehicle[1]);
			
		} else {
			return null;
		}

		return select;
	}	
	//Trip Close Count Logic Stop
	
	
	@Override
	public Long getTripAccountClosedCount(String startDate, String endDate,Integer companyId)
			throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				" SELECT COUNT(T.tripSheetID) "
				+ " From TripSheet as T "					
				+ " WHERE T.tripOpenDate between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId
				+ " AND T.tripStutesId = " + TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED + " AND T.markForDelete = 0 ");
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
		
	}
	
	@Override
	public Long getAllTripSheetCount(String startDate, Integer companyId) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				" SELECT COUNT(T.tripSheetID) "
				+ " From TripSheet as T "					
				+ " WHERE T.tripOpenDate < '"+ startDate +"'  AND T.companyId = "+companyId+" "
				+ " AND T.markForDelete = 0 ");
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
		
	}
	
	//Trip Missed Count Logic Start
		@Override
		public Long getTripMissedClosing(String startDate, String endDate,Integer companyId) throws Exception {
			Query queryt = 	null;
				
				queryt = entityManager.createQuery(
						" SELECT COUNT(T.tripSheetID) "
								+ " From TripSheet as T "							
								+ " WHERE T.tripStutesId  IN ("+TripSheetStatus.TRIP_STATUS_SAVED
								+ " , " + TripSheetStatus.TRIP_STATUS_DISPATCHED
								+ " ) AND T.closetripDate < '"+ startDate +"' AND T.companyId = " +companyId 
								+ "AND T.markForDelete = 0 ");
			
			Long count = (long) 0;
			try {
				
				if((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
				}
				
			} catch (NoResultException nre) {
				}

			return count;
		}	
	
	@Override
	public Long getTodaysTripOpenStatusCount(String startDate, String endDate, Integer companyId) throws Exception {
		Query queryt = 	null;
			
			queryt = entityManager.createQuery(
					" SELECT COUNT(T.tripSheetID) "
							+ " From TripSheet as T "							
							+ " WHERE T.tripStutesId  IN ("+TripSheetStatus.TRIP_STATUS_SAVED
							+ " , " + TripSheetStatus.TRIP_STATUS_DISPATCHED
							+ " ) AND T.closetripDate between '"+ startDate +"' AND '"+ endDate +"' AND T.companyId = " +companyId 
							+ "AND T.markForDelete = 0 ");
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}
	
	
	//Trip Missed Count Logic Start
	@Override
	public Long getTripSheetDispatchedCount(String endDate, Integer companyId) throws Exception {
		Query queryt = 	null;
			
			queryt = entityManager.createQuery(
					" SELECT COUNT(T.tripSheetID) "
							+ " From TripSheet as T "							
							+ " WHERE T.tripStutesId = "+TripSheetStatus.TRIP_STATUS_DISPATCHED+" "
							+ " AND T.tripOpenDate <= '"+ endDate +"' AND T.companyId = " +companyId 
							+ "AND T.markForDelete = 0 ");
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}	
	//Trip Missed Count Logic Stop
	
	@Override
	public Long getTripSheetSavedCount(String endDate, Integer companyId) throws Exception {
		Query queryt = 	null;
			
			queryt = entityManager.createQuery(
					" SELECT COUNT(T.tripSheetID) "
							+ " From TripSheet as T "							
							+ " WHERE T.tripStutesId =  "+TripSheetStatus.TRIP_STATUS_SAVED+" "
							+ " AND T.tripOpenDate <= '"+ endDate +"' AND T.companyId = " +companyId 
							+ "AND T.markForDelete = 0 ");
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}
	
	
	//Oldest Open TriSheet Count Logic Start
	@Override
	public TripSheetDto getTripOldestOpenFlavorOne(Integer companyId, String startDate)
			throws Exception {
			
		TypedQuery<Object[]> query = entityManager.createQuery(
					" SELECT T.tripSheetID, T.tripOpenDate, T.tripSheetNumber, T.tripStutesId, T.tripOpenDate, T.closetripDate From TripSheet as T "			
							+ " WHERE T.companyId = " +companyId				
							+ " AND T.tripStutesId  IN ( " +TripSheetStatus.TRIP_STATUS_SAVED
							+ " , " + TripSheetStatus.TRIP_STATUS_DISPATCHED
							+ " ) AND T.vid > 0  "
							+ " AND T.markForDelete = 0  order by T.tripOpenDate, T.tripSheetID asc ",
							Object[].class);
			query.setFirstResult(0);
			query.setMaxResults(1);
			
			Object[] results = null;
			
			try {
				results = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				
			}
			
			TripSheetDto select;
			if (results != null) {
				
				select = new TripSheetDto();				
				
				select.setTripSheetID((Long) results[0]);
				
				if(results[1] != null)				
				select.setTripOpenDateOn((Timestamp) results[1]);
				
				if(results[2] != null)
				select.setTripSheetNumber((Long) results[2]);
				if(results[3] != null) 
				select.setTripStutesId((short) results[3]);
				
				if(results[4] != null)
				select.setTripOpenDateOn((java.util.Date) results[4]);
				if(results[5] != null)				
				select.setClosetripDateOn((java.util.Date) results[5] );
				
			}else {
				return null;
			}
			return select;		
	}	
	//Oldest Open TriSheet Count Logic end
	
	@Override
	public TripSheetDto getTripIncomeAndExpense (String startDate, String endDate,Integer companyId) throws Exception {
		Query queryt = 	null;
		
			queryt = entityManager.createQuery(
					" SELECT COUNT(T.tripSheetID), SUM(T.tripTotalincome), SUM(T.tripTotalexpense), SUM(T.tripTotalAdvance) "
					+ " From TripSheet as T "					
					+ " WHERE T.tripOpenDate between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId
					+ " AND T.tripStutesId = "+TripSheetStatus.TRIP_STATUS_CLOSED+"   "
					+ " AND T.markForDelete = 0 ");
		
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) queryt.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		TripSheetDto select;
		if (vehicle != null) {
			select = new TripSheetDto();
			
			if(vehicle[0] != null)
				select.setTripSheetID((Long) vehicle[0]);
			if(vehicle[1] != null)
				select.setTripTotalincome((double) vehicle[1]);
			if(vehicle[2] != null)
				select.setTripTotalexpense((double) vehicle[2]);
			if(vehicle[3] != null)
				select.setTripTotalAdvance((double) vehicle[3]);
			
		} else {
			return null;
		}

		return select;
	}
	
	@Override
	public long getTripTotalRunKM(String startDate, String endDate,Integer companyId) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				" SELECT SUM(tripUsageKM) "
				+ " From TripSheet as T "					
				+ " WHERE T.tripOpenDate between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId
				+ " AND T.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+","+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") "
				+ " AND T.markForDelete = 0 ");
		
		Long KM = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
				KM = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return KM;
		
	}
	
	@Override
	@Transactional
	public ValueObject updateTripSheetClosingKM(ValueObject valueObject) throws Exception {
		TripSheet		tripSheet		= null;
		Integer			tripUsaseKm		= null;
		Vehicle			vehicle			= null;
		Integer			tripClosingKM	= null;
		try {
			
			tripSheet		=	getTripSheet(valueObject.getLong("tripSheetID"));
			vehicle			=   vehicleService.getVehicel1(valueObject.getInt("vid"));
			tripClosingKM	= 	valueObject.getInt("tripClosingKM",0);
			if(tripSheet != null) {
				tripUsaseKm	= tripClosingKM - tripSheet.getTripOpeningKM();
				
				entityManager.createQuery(
						" UPDATE TripSheet SET tripClosingKM = "+tripClosingKM+", tripUsageKM = "+tripUsaseKm+" "
								+ " WHERE tripSheetID = "+valueObject.getLong("tripSheetID")+" AND vid = "+valueObject.getInt("vid")+"  ")
				.executeUpdate();
				
				if(vehicle.getVehicle_Odometer() < tripClosingKM) {
					vehicleService.updateCurrentOdoMeter(vehicle.getVid(), tripClosingKM, vehicle.getCompany_Id());
					
					VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
					odometerHistory.setVid(tripSheet.getVid());
					odometerHistory.setVehicle_Odometer(tripClosingKM);
					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
					odometerHistory.setVoh_date(toDate);
					odometerHistory.setVoh_updatelocation("TripSheet Closing Entry");
					odometerHistory.setVoh_updateId(tripSheet.getTripSheetID());
					odometerHistory.setCompanyId(vehicle.getCompany_Id());
					vehicleOdometerHistoryService.addVehicleOdometerHistory(odometerHistory);
				}
			}
			valueObject.put("tripUsaseKm", tripUsaseKm);
		return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tripSheet		= null;
			tripUsaseKm		= null;
			tripClosingKM	= null;
			vehicle			= null;
		}
	}
	
	@Override
	public HashMap<Integer,TripSheetExpenseDto> findAllTripSheetExpenseCombineMap (List<TripSheetExpenseDto> expenseList) throws Exception {
		HashMap<Integer,TripSheetExpenseDto>  dtosMap  				= null;
		TripSheetExpenseDto					  tripSheetExpenseDto	= null;
		try {
			dtosMap = new HashMap<Integer, TripSheetExpenseDto>();
			for(int i=0; i<expenseList.size(); i++) {
				
				if(dtosMap.containsKey(expenseList.get(i).getExpenseId())) {
					tripSheetExpenseDto = dtosMap.get(expenseList.get(i).getExpenseId());
					
					tripSheetExpenseDto.setExpenseAmount(tripSheetExpenseDto.getExpenseAmount() + expenseList.get(i).getExpenseAmount());
					
				} else {
				   dtosMap.put(expenseList.get(i).getExpenseId(), expenseList.get(i));	
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtosMap;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject findAllTripSheetExpenseCombineList(ValueObject valueInObject) throws Exception {
		CustomUserDetails 			userDetails 	 = null;
		userDetails 								 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long 						tripSheetID 	 = valueInObject.getLong("tripSheetId");
		int  						expenseId   	 = valueInObject.getInt("expenseId");
		ValueObject					valueOutObject	 = null;
		
		Query query = entityManager.createQuery(
				"SELECT T.tripExpenseID, TE.expenseName, T.expenseId, T.expenseAmount, B.branch_name, T.expensePlaceId, T.expenseRefence,"
						+ " T.expenseFixedId, T.fuel_id, T.DHID, U.email, T.createdById, T.created, T.fuel_id, T.DHID FROM TripSheetExpense T "
						+ " LEFT JOIN TripExpense TE ON TE.expenseID = T.expenseId AND (T.fuel_id IS NULL OR T.fuel_id = 0) AND (T.DHID IS NULL OR T.DHID = 0)"
						+ " LEFT JOIN Branch AS B ON B.branch_id = T.expensePlaceId"
						+ " LEFT JOIN User AS U ON U.id = T.createdById"
						+ " WHERE T.tripsheet.tripSheetID =:id AND T.expenseId =:expId AND T.companyId =:companyId AND T.markForDelete = 0");

		query.setParameter("id", tripSheetID);
		query.setParameter("expId", expenseId);
		query.setParameter("companyId", userDetails.getCompany_id());
		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<TripSheetExpenseDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<TripSheetExpenseDto>();
			TripSheetExpenseDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new TripSheetExpenseDto();
					select.setTripExpenseID((Long) vehicle[0]);
					select.setExpenseName((String) vehicle[1]);
					select.setExpenseId((Integer) vehicle[2]);
					select.setExpenseAmount((Double) vehicle[3]);
					select.setExpensePlace((String) vehicle[4]);
					select.setExpensePlaceId((Integer) vehicle[5]);
					select.setExpenseRefence((String) vehicle[6]);
					select.setExpenseFixedId((short) vehicle[7]);
					select.setExpenseFixed(TripRouteFixedType.getFixedTypeName((short) vehicle[7]));
					select.setFuel_id((Long) vehicle[8]);
					select.setDHID((Long) vehicle[9]);
					select.setCreatedBy((String) vehicle[10]);
					select.setCreatedById((Long) vehicle[11]);
					select.setCreatedStr(dateFormat_Name.format((Timestamp) vehicle[12]));
					select.setFuel_id((Long) vehicle[13]);
					select.setDHID((Long) vehicle[14]);

					if ((select.getExpenseName() == "" || select.getExpenseName() == null)
							&& select.getFuel_id() != null && select.getFuel_id() > 0) {
						select.setExpenseName(
								VehicleFuelType.getVehicleFuelName(Short.parseShort((Integer) vehicle[2] + "")));
					} else if ((select.getExpenseName() == "" || select.getExpenseName() == null)
							&& select.getDHID() != null && select.getDHID() > 0) {
						select.setExpenseName(TripSheetFixedExpenseType
								.getTripExpenseName(Short.parseShort((Integer) vehicle[2] + "")));
					}

					Dtos.add(select);
				}
			}
			valueOutObject	= new ValueObject();
			valueOutObject.put("ExpenseList", Dtos);
		}

		return valueOutObject;
	}
	
	@Override
	@Transactional
	public void saveTripAdvanceList(List<TripSheetAdvance> advances) throws Exception {
		
		TripSheetAdvanceRepository.saveAll(advances);
	}
	
	@Override
	@Transactional
	public void saveTripIncomeList(List<TripSheetIncome> incomes) throws Exception {
		
		TripSheetIncomeRepository.saveAll(incomes);
	}
	
	@Override
	@Transactional
	public void saveTripExpenseList(List<TripSheetExpense> expense) throws Exception {
		
		TripSheetExpenseRepository.saveAll(expense);
	}
	
	@Override
	@Transactional
	public void updateBookRefToLhpvIntripSheet(String bookRef, Long tripSheetId) throws Exception {
		
		entityManager.createQuery(
				" UPDATE TripSheet  SET tripBookref = '"+bookRef+"' where tripSheetID = "+tripSheetId+" ")
				.executeUpdate();
		
	}
	
	
	//Create Day Wise Expense Report Start
		@Override
		public ValueObject getCreateDayWiseExpenseReport(ValueObject valueObject) throws Exception {
			String						dateRange				= null;		
			CustomUserDetails			userDetails				= null;
			List<TripSheetDto>			expense					= null;		
			ValueObject					tableConfig			    = null;
			int 						expenseId            	= 0 ;
			boolean 					dateRangeConfig         = false ;
			String						startDate				= "";
			String						endDate					= "";
			int 						vid            			= 0 ;
			String 						vehicle            		= "" ;
			int 						driverId            	= 0 ;
			String 						driver            		= "" ;
			short						driJobId				= 0;
			List<TripSheetDto>			tollExpense				= null;	
					 
			try {
				userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				dateRange			= valueObject.getString("dateRange");
				expenseId			= valueObject.getInt("ReportExpenseName", 0);
				dateRangeConfig		= valueObject.getBoolean("dateRangeConfig",false);
				vid 				= valueObject.getInt("vehicleId",0);
				driverId 			= valueObject.getInt("driverId",0);
				driJobId 			= valueObject.getShort("driJobId",(short)0);
				if(dateRange != null) {
					
					String Expense_Name = "" , Date = "";				
					
					if(expenseId > 0 )
					{
						Expense_Name = " AND TE.expenseID = "+ expenseId +" ";
					}
					if(dateRangeConfig) {
						startDate = DateTimeUtility.getStrDateFromStrDate(dateRange.split("to")[0], DateTimeUtility.DD_MM_YYYY);
						endDate   = DateTimeUtility.getStrDateFromStrDate(dateRange.split("to")[1], DateTimeUtility.DD_MM_YYYY, DateTimeUtility.YYYY_MM_DD);

						Date = "  AND (( TSE .created between '" + startDate + "' AND '"
								+ endDate + " 23:59:59')) ";
					}else {
						dateRange = DateTimeUtility.getSqlStrDateFromStrDate(dateRange, DateTimeUtility.YYYY_MM_DD);
						Date = "  AND (TSE .created between '" + dateRange + "' AND '"
								+ dateRange + " 23:59:59 ') ";
					}
					
					if(vid > 0) {
						vehicle = " AND T.vid = "+ vid +" ";
					}
					
					if(driverId > 0) {
						if(driJobId == 1) {
							driver = " AND T.tripFristDriverID = "+ driverId +" ";
						}else if(driJobId == 2) {
							driver = " AND T.tripSecDriverID = "+ driverId +" ";
						}
					}
					String query       = " " + Expense_Name + " " + Date + " "+vehicle+" "+driver+" ";
					
					tableConfig		= new ValueObject();
					tableConfig.put("companyId", userDetails.getCompany_id());
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.CREATE_DAY_WISE_EXPENSE_TABLE_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);				
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);				
					expense	        = TripSheetDaoImpl.getCreateDayWiseExpenseReportList(query, userDetails.getCompany_id());
					String fasttagQuery = "";
					if(dateRangeConfig) {
						startDate = DateTimeUtility.getStrDateFromStrDate(dateRange.split("to")[0], DateTimeUtility.DD_MM_YYYY);
						endDate   = DateTimeUtility.getStrDateFromStrDate(dateRange.split("to")[1], DateTimeUtility.DD_MM_YYYY, DateTimeUtility.YYYY_MM_DD);

						fasttagQuery += "  AND (( T.closetripDate between '" + startDate + "' AND '"
								+ endDate + " 23:59:59')) ";
					}else {
						dateRange = DateTimeUtility.getSqlStrDateFromStrDate(dateRange, DateTimeUtility.YYYY_MM_DD);
						fasttagQuery += "  AND (T.closetripDate between '" + dateRange + "' AND '"
								+ dateRange + " 23:59:59 ') ";
					}
					
					fasttagQuery += ""+ Expense_Name + " "+vehicle+" "+driver+" ";
					
					
					tollExpense		= TripSheetDaoImpl.getCreateDayWiseFasttagExpenseReportList(fasttagQuery, userDetails.getCompany_id());
				
					if(expense == null) {
						expense	= new ArrayList<TripSheetDto>();
					}
					if(tollExpense == null) {
						tollExpense	= new ArrayList<TripSheetDto>();
					}
					
					expense.addAll(tollExpense);
				}
				
				valueObject.put("expense", expense);
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				tableConfig			= null;
				expense	= null;			
				userDetails			= null;
				dateRange			= null;
			}
		}
	
		@Override
		public ValueObject getVoucherDateWiseExpenseReport(ValueObject valueObject) throws Exception {
			ValueObject					dateRange				= null;		
			CustomUserDetails			userDetails				= null;
			List<TripSheetDto>			expense					= null;		
			ValueObject					tableConfig			    = null;
			int 						expenseId            	= 0 ;
			String 						dateRangeFrom 			= "";
			String 						dateRangeTo 			= "";
			Long						tallyCompanyId			= null;
			int							vendorId				= 0;
			try {
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				expenseId		= valueObject.getInt("ReportExpenseName", 0);
				dateRange		= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
				dateRangeFrom 	= dateRange.getString(DateTimeUtility.FROM_DATE);
				dateRangeTo 	= dateRange.getString(DateTimeUtility.TO_DATE);
				tallyCompanyId	= valueObject.getLong("tallyCompanyId",0);
				vendorId		= valueObject.getInt("vendorId",0);
				
				dateRangeFrom	= DateTimeUtility.getStrDateFromStrDate(dateRangeFrom, DateTimeUtility.DD_MM_YYYY);
				dateRangeTo		= DateTimeUtility.getStrDateFromStrDate(dateRangeTo, DateTimeUtility.DD_MM_YYYY, DateTimeUtility.YYYY_MM_DD);
				
				if(dateRange != null) {
					
					String Expense_Name = "" , Date = "", tallyCompany ="", vendor = "";				
					
					if(expenseId > 0 )
					{
						Expense_Name = " AND TE.expenseID = "+ expenseId +" ";
					}
					if(tallyCompanyId > 0 ){
						tallyCompany = " AND TC.tallyCompanyId = "+ tallyCompanyId +" ";
					}
					if(vendorId > 0 ){
						vendor = " AND TSE.vendorId = "+ vendorId +" ";
					}
					
					Date = "  AND (( TSE .voucherDate between '" + dateRangeFrom + "' AND '"
							+ dateRangeTo + " 23:59:59')) ";
										
					String query       = " " + Expense_Name + " " + Date + " "+tallyCompany+" "+vendor+" ";
					
					
					tableConfig		= new ValueObject();
					tableConfig.put("companyId", userDetails.getCompany_id());
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VOUCHER_DATE_WISE_EXPENSE_TABLE_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);				
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);				
					expense	        = TripSheetDaoImpl.getVoucherDateWiseExpenseReportList(query, userDetails.getCompany_id());
					
				}
				
				valueObject.put("expense", expense);
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				tableConfig			= null;
				expense	= null;			
				userDetails			= null;
				dateRange			= null;
			}
		}
		
		@Transactional
		public void updatePODDetails(Long tripSheetID, int noOfPOD, int companyId) throws Exception {
			
			entityManager.createQuery(
					"Update TripSheet AS T Set T.noOfPOD = "+noOfPOD+" "
						+ " WHERE T.tripSheetID = " + tripSheetID + " AND companyId = "+companyId+" ")
			.executeUpdate();
		}
		
		@Transactional
		public void updateReceivedPODDetails(Long tripSheetID, int receivedPOD) throws Exception {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			entityManager.createQuery(
					"Update TripSheet AS T Set T.receivedPOD = "+receivedPOD+" "
							+ " WHERE T.tripSheetID = " + tripSheetID + " AND companyId = "+userDetails.getCompany_id()+" ")
			.executeUpdate();
		}
		
		@Transactional
		public void updateFirstDriverRoutePoint(Long tripSheetID, double routePoint, int companyId) throws Exception {
			
			entityManager.createQuery(
					"Update TripSheet AS T Set T.tripFristDriverRoutePoint = "+routePoint+" "
							+ " WHERE T.tripSheetID = " + tripSheetID + " AND companyId = "+companyId+" ")
			.executeUpdate();
		}
		
		@Transactional
		public void updateSecDriverRoutePoint(Long tripSheetID, double routePoint, int companyId) throws Exception {
			
			entityManager.createQuery(
					"Update TripSheet AS T Set T.tripSecDriverRoutePoint = "+routePoint+" "
							+ " WHERE T.tripSheetID = " + tripSheetID + " AND companyId = "+companyId+" ")
			.executeUpdate();
		}
		
		@Transactional
		public void updateCleanerRoutePoint(Long tripSheetID, double routePoint, int companyId) throws Exception {
			
			entityManager.createQuery(
					"Update TripSheet AS T Set T.tripCleanerRoutePoint = "+routePoint+" "
							+ " WHERE T.tripSheetID = " + tripSheetID + " AND companyId = "+companyId+" ")
			.executeUpdate();
		}
	
		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public ValueObject getIVLoadingSheetDataForTrip(ValueObject valueObject) throws Exception {
			JSONArray									jsonArray						= null;
			List<LoadingSheetToTripSheet>				loadingSheetToTripSheetList		= null;
			CustomUserDetails							userDetails						= null;
			HashMap<String, Object>						configuration					= null;
			CompanyMapper                               companyMapper                   = null;   
			TripSheet									trip							= null;
			Integer 									lhpvId							= 0;
			try {
				userDetails		= (CustomUserDetails) valueObject.get("userDetails");
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
				
				companyMapper = companyMapperRepo.findGroupIdByFleetCompanyId(userDetails.getCompany_id());				
				valueObject.put("configuration", configuration);
				valueObject.put("loadingSheetIncomeId", (int)configuration.get("loadingSheetIncomeId"));
	    		trip		 =  getTripSheetDetails(valueObject.getLong("tripSheetId"));
				
	    		if(trip.getLhpvId()!=null)
					lhpvId = trip.getLhpvId();

				/*
				 * HttpResponse<JsonNode> response =
				 * Unirest.post((String)configuration.get("loadingSheetSyncLink"))
				 * .header("Content-Type", "application/x-www-form-urlencoded")
				 * .field("fromDate", valueObject.getString("fromDate")) .field("toDate",
				 * valueObject.getString("toDate")) .field("vehicleNumber",
				 * valueObject.getString("vehicleNumber")) .field("accountGroupId",
				 * companyMapper.getIvGroupId()) .asJson();
				 */
	    		
	    		
	    		Integer accGroupId = 0;
				
				if (companyMapper != null) {
				    accGroupId = companyMapper.getIvGroupId();
				}

				MultipartBody request = Unirest.post((String)configuration.get("loadingSheetSyncLink"))
				    .header("Content-Type", "application/x-www-form-urlencoded")
				    .field("fromDate", valueObject.getString("fromDate"))
				    .field("toDate", valueObject.getString("toDate"))
				    .field("vehicleNumber", valueObject.getString("vehicleNumber"))
					.field("lhpvId", lhpvId);

				// Conditionally add the "accountGroupId" field if accGroupId is not 0
				if (accGroupId != 0) {
				    request.field("accountGroupId", accGroupId);
				}

				HttpResponse<JsonNode> response = request.asJson();
				
				TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(valueObject.getLong("tripSheetId"));
				TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
	    		
	            if(response.getStatus() == 200) {
					
					if( response.getBody().getArray().getJSONObject(0).has("loadingSheetToTripSheetList")) {
						
						jsonArray	=	 (JSONArray) response.getBody().getArray().getJSONObject(0).get("loadingSheetToTripSheetList");
						
						HashMap<String, LoadingSheetToTripSheet>	validateHm	=	loadingSheetToTripSheetService.validateLoadingSheetAdded(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());
						
						ValueObject	object	=	loadingSheetToTripSheetBL.getLoadingSheetToTripSheetDto(jsonArray, valueObject, validateHm);
						if(object != null && object.checkExistanceInVO("loadingSheetToTripSheetList")) {
							
							loadingSheetToTripSheetList	= (List<LoadingSheetToTripSheet>) object.get("loadingSheetToTripSheetList");
							
							loadingSheetToTripSheetService.saveAllLoadingSheetToTripSheet(loadingSheetToTripSheetList);
							
							List<Long> 	validate	=	findAllTripSheetIncomeForLS(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());
							
							List<LoadingSheetToTripSheet> tripLsList	= loadingSheetToTripSheetService.getLoadingSheetToTripSheetByTripSheetId(valueObject.getLong("tripSheetId"));
							
							if(!(boolean) configuration.get("dontAddIVCargoIncomeToTripSheet")) {
								List<TripSheetIncome>	incomeList	=	loadingSheetToTripSheetBL.getLoadingSheetToTripSheetIncome(tripLsList, valueObject, validate);
								
								if(incomeList != null && !incomeList.isEmpty()) {
									
									addTripSheetIncomeList(incomeList, valueObject);
									UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());
									
									tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
								}
							}
						}
						
					}
					
					
				}
				
				
			} catch (ConnectTimeoutException e) {
				System.err.println("connection time out exception : ");
			}catch (Exception e) {
				System.err.println("Exception : "+ e);
			}finally {
				jsonArray						= null;
				loadingSheetToTripSheetList		= null;
				userDetails						= null;
				configuration					= null;
			}
			return valueObject;
		}
		
		@Override
		@Transactional
		public void addAllTripSheetExpense(List<TripSheetExpense> TripSheetExpense) throws Exception {
			
			TripSheetExpenseRepository.saveAll(TripSheetExpense);
		}
		
		@Override
		@Transactional
		public void addTripSheetIncomeList(List<TripSheetIncome> TripSheetIncome, ValueObject valueObject) throws Exception {
			HashMap<Long, TripSheetIncome>  tripSheetIncomeDtoHM = null;
			HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>  incomeTxnCheckerHashMap = null;
			TripSheet sheet = null;
			try {
				CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				tripSheetIncomeDtoHM = new HashMap<Long, TripSheetIncome>();
				incomeTxnCheckerHashMap = new HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>();
				
				sheet = getTripSheetData(valueObject.getLong("tripSheetId"), userDetails.getCompany_id());
				
				for (TripSheetIncome tcktIncm : TripSheetIncome) {
					TripSheetIncomeRepository.save(tcktIncm);
					if(tcktIncm.getIncomeAmount() > 0.0 && !valueObject.getBoolean("fromTripSheetClose",false)) {
						
						ValueObject incomeObject = new ValueObject();
						
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, valueObject.getLong("tripSheetId ",0));
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID,VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID,VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID,userDetails.getCompany_id());
						incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, valueObject.getInt("vid",0));
						incomeObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, tcktIncm.getIncomeId());
						incomeObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID,tcktIncm.getTripincomeID());
						
						VehicleProfitAndLossIncomeTxnChecker profitAndLossIncomeTxnChecker =
								txnCheckerBL.getVehicleProfitAndLossIncomeTxnChecker(incomeObject);
						vehicleProfitAndLossIncomeTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);
						
						incomeTxnCheckerHashMap.put(profitAndLossIncomeTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossIncomeTxnChecker);
						tripSheetIncomeDtoHM.put(tcktIncm.getTripincomeID(), tcktIncm); 
						
					}
				}
				if(tripSheetIncomeDtoHM != null && tripSheetIncomeDtoHM.size() > 0) {
					ValueObject valObj = new ValueObject(); 
					valObj.put("tripSheet", sheet);
					valObj.put("userDetails", userDetails); 
					valObj.put("tripSheetIncomeHM", tripSheetIncomeDtoHM); 
					valObj.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap); 
					valObj.put("isTripSheetClosed", true);
					
					vehicleProfitAndLossService.runThreadForTripSheetIncome(valObj); 
				}
			} catch (Exception e) {
			}
			
			
			TripSheetIncomeRepository.saveAll(TripSheetIncome);
		}
		
		@Override
		@Transactional
		public List<Long> findAllTripSheetIncomeForLS(Long tripSheetID, Integer companyId) throws Exception {

			return TripSheetIncomeRepository.findAllTripSheetIncomeForLS(tripSheetID, companyId);
		}
		
		@Override
		public ValueObject getLoadingSheetIncomeDetails(ValueObject valueObject) throws Exception {
			Long						dispatchLedgerId	= null;
			try {
				
				
				dispatchLedgerId	= valueObject.getLong("dispatchLedgerId");
				
				List<LoadingSheetToTripSheet> loadingSheetToTripSheetList	=	loadingSheetToTripSheetService.getLoadingSheetToTripSheetByDispatchLedgerId(dispatchLedgerId, valueObject.getLong("tripSheetId", 0));
				
				valueObject.put("loadingSheetToTripSheetList", loadingSheetToTripSheetList);
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				dispatchLedgerId		= null;
			}
		}
		
		@Override
		public List<TripSheetDto> getRecentTripListByVid(Integer	vid, Date uptoDate) throws Exception {
			try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT  T.tripSheetID, T.tripSheetNumber, T.tripOpenDate, T.closetripDate "
								+ " FROM TripSheet as T "
								+ " Where T.markForDelete=0 AND T.vid = "+vid+" "
								+ " AND T.tripOpenDate >= '"+uptoDate+"' ORDER BY T.tripSheetID desc ",		
								Object[].class);
				
				
				List<Object[]> results = queryt.getResultList();

				List<TripSheetDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					
					Dtos = new ArrayList<TripSheetDto>();
					TripSheetDto list = null;
					for (Object[] result : results) {
						
						list = new TripSheetDto();
						list.setTripSheetID((long) result[0]);
						list.setTripSheetNumber((long) result[1]);
						list.setTripOpenDateOn((java.util.Date) result[2]);
						list.setClosetripDateOn((java.util.Date) result[3]);
						
						if(list.getTripOpenDateOn() != null) {
							list.setTripOpenDate(dateFormat_Name.format(list.getTripOpenDateOn()));
						}
						
						if(list.getClosetripDateOn() != null) {
							list.setClosetripDate(dateFormat_Name.format(list.getClosetripDateOn()));
						}
						
						Dtos.add(list);
					}
				}
				
				return Dtos;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public void deletePreviousVehicleTripSheetIncome(TripSheetIncome tripIncome, Integer oldVid) throws Exception{
			CustomUserDetails							userDetails						= null;
			try {
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				ValueObject		valueObject	= new ValueObject();
				TripSheet	tripSheet	= getTripSheet(tripIncome.getTripsheet().getTripSheetID());
				valueObject.put("tripSheet", tripSheet);
				valueObject.put("userDetails", userDetails);
				valueObject.put("txnTypeId", tripSheet.getTripSheetID());
				valueObject.put("tripIncome", tripIncome);
				valueObject.put("vid", oldVid);
				valueObject.put("incomeId", tripIncome.getIncomeId());
				valueObject.put("incomeAmount", tripIncome.getIncomeAmount());
				valueObject.put("transactionDate", DateTimeUtility.geTimeStampFromDate(tripSheet.getClosetripDate()));
				
				vehicleProfitAndLossService.runThreadForDeleteIncome(valueObject);
				
			} catch (Exception e) {
				throw e;
			}
			
		}
		
		@Override
		public HashMap<Integer, Long> getTripSheetCountHM(String startDate, String endDate) throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(T.tripSheetID), T.companyId "
								+ " From TripSheet as T "
								+ " WHERE T.tripOpenDate between '"+startDate+"' AND '"+endDate+"' AND "
								+ " T.markForDelete = 0 GROUP BY T.companyId ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();

				if (results != null && !results.isEmpty()) {
					
					map	= new HashMap<>();
					
					for (Object[] result : results) {
						
						map.put((Integer)result[1], (Long)result[0]);
						
					}
				}
				
				return map;
			} catch (Exception e) {
				throw e;
			}
		}
		@Override
		public void saveVehicleProfitAndLossTxnCheckerForTripSheetIncome(ValueObject valueObject) throws Exception {
			CustomUserDetails										userDetails					= null;
			TripSheetIncome 										tripSheetIncm				= null;
			TripSheet 												sheet						= null;
			HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>		incomeTxnCheckerHashMap		= null;
			HashMap<Long, TripSheetIncome> 							tripSheetIncomeDtoHM		= null;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				tripSheetIncm					= (TripSheetIncome) valueObject.get("TripSheetIncm");
				sheet							= (TripSheet) 		valueObject.get("TripSheet");
				
				tripSheetIncomeDtoHM			= new HashMap<>();
				incomeTxnCheckerHashMap			= new HashMap<>();
				ValueObject		incomeObject	= new ValueObject();

				incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, sheet.getTripSheetID());
				incomeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
				incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
				incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, sheet.getVid());
				incomeObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, tripSheetIncm.getIncomeId());
				incomeObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, tripSheetIncm.getTripincomeID());

				VehicleProfitAndLossIncomeTxnChecker profitAndLossIncomeTxnChecker = txnCheckerBL.getVehicleProfitAndLossIncomeTxnChecker(incomeObject);
				vehicleProfitAndLossIncomeTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);
				
				incomeTxnCheckerHashMap.put(profitAndLossIncomeTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossIncomeTxnChecker);
				tripSheetIncomeDtoHM.put(tripSheetIncm.getTripincomeID(), tripSheetIncm);
				
				ValueObject	valObj	= new ValueObject();
				valObj.put("tripSheet", sheet);
				valObj.put("userDetails", userDetails);
				valObj.put("tripSheetIncomeHM", tripSheetIncomeDtoHM);
				valObj.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap);
				valObj.put("isTripSheetClosed", true);

				vehicleProfitAndLossService.runThreadForTripSheetIncome(valObj);
				
			} catch (Exception e) {
				throw e;
			}
			
		}
		@Override
		public
		TripSheetDto	getHighyestKMRunDetails(String startDate, String endDate,Integer companyId) throws Exception {
			try {
				Query queryt = 	null;
				int flavor = companyConfigurationService.getTripSheetFlavor(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				
				if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
					queryt = entityManager.createQuery(
							" SELECT T.tripSheetID, T.tripSheetNumber, V.vehicle_registration  "
							+ " From TripSheet as T "
							+ " INNER JOIN Vehicle V ON V.vid = T.vid"					
							+ " WHERE T.closetripDate between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId
							+ " AND T.tripStutesId = " + TripSheetStatus.TRIP_STATUS_CLOSED + " AND T.markForDelete = 0 ORDER BY T.tripUsageKM DESC ");
				}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
					
				
				}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
					queryt = entityManager.createQuery(
							"SELECT T.TRIPDAILYID, T.TRIPDAILYNUMBER, V.vehicle_registration "
									+ " From TripDailySheet as T "
									+ " INNER JOIN Vehicle V ON V.vid = T.VEHICLEID"	
									+ " WHERE T.TRIP_OPEN_DATE between '"+startDate+"' AND '"+endDate+"' AND T.COMPANY_ID = "+companyId
									+ " AND T.TRIP_STATUS_ID = " + TripSheetStatus.TRIP_STATUS_CLOSED + " AND T.markForDelete = 0 ORDER BY T.TRIP_USAGE_KM DESC");
				
				}else {
					queryt = entityManager.createQuery(
							" SELECT T.tripSheetID, T.tripSheetNumber, V.vehicle_registration  "
							+ " From TripSheet as T "
							+ " INNER JOIN Vehicle V ON V.vid = T.vid"					
							+ " WHERE T.closetripDate between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId
							+ " AND T.tripStutesId = " + TripSheetStatus.TRIP_STATUS_CLOSED + " AND T.markForDelete = 0 ORDER BY T.tripUsageKM DESC ");
				
				}
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}

				TripSheetDto select;
				if (vehicle != null) {
					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVehicle_registration((String) vehicle[2]);
					
				} else {
					return null;
				}

				return select;
			} catch (Exception e) {
				return null;
			}
		}
		@Override
		public void deletePreviousVehicleTripSheetExpense(TripSheetExpense tripSheetExpense, Integer oldVid) throws Exception{
			CustomUserDetails							userDetails						= null;
			try {
				userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				TripSheet	tripSheet	= getTripSheet(tripSheetExpense.getTripsheet().getTripSheetID());
				
				ValueObject		valueObject	= new ValueObject();
				valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
				valueObject.put("txnAmount", tripSheetExpense.getExpenseAmount());
				valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(tripSheet.getClosetripDate()));
				valueObject.put("txnTypeId", tripSheet.getTripSheetID());
				valueObject.put("expenseId", tripSheetExpense.getExpenseId());
				valueObject.put("vid", oldVid);
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
				
				
			} catch (Exception e) {
				throw e;
			}
			
		}
		
		@Override
		public void saveVehicleProfitAndLossTxnCheckerForTripSheetExpense(ValueObject valueObject) throws Exception {
			CustomUserDetails										userDetails						= null;
			TripSheetExpense 										tripSheetExpense				= null;
			TripSheet 												sheet							= null;
			VehicleProfitAndLossTxnChecker							profitAndLossExpenseTxnChecker	= null;
			HashMap<Long, VehicleProfitAndLossTxnChecker>			expenseTxnCheckerHashMap		= null;
			HashMap<Long, TripSheetExpense> 						tripSheetExpenseHM				= null;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				tripSheetExpense				= (TripSheetExpense) valueObject.get("TripSheetExpn");
				sheet							= (TripSheet) 		 valueObject.get("TripSheet");
				
				expenseTxnCheckerHashMap	= new HashMap<>();
				tripSheetExpenseHM			= new HashMap<>();
				ValueObject		object		= new ValueObject();

				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_ID, sheet.getTripSheetID());
				object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_VID, sheet.getVid());
				object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, tripSheetExpense.getExpenseId());
				object.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, tripSheetExpense.getTripExpenseID());

				profitAndLossExpenseTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
				vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);

				expenseTxnCheckerHashMap.put(profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossExpenseTxnChecker);
				tripSheetExpenseHM.put(tripSheetExpense.getTripExpenseID(), tripSheetExpense);
				
				ValueObject	valObj	= new ValueObject();
				valObj.put("tripSheet", sheet);
				valObj.put("userDetails", userDetails);
				valObj.put("tripSheetExpenseHM", tripSheetExpenseHM);
				valObj.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);
				valObj.put("isTripSheetClosed", true);

				vehicleProfitAndLossService.runThreadForTripSheetExpenses(valObj);
				
			} catch (Exception e) {
				throw e;
			}
			
		}
		
		@Override
		public List<TripSheetDto> getTotalTripSheetCreatedDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
							+ " TR.routeName, TS.tripOpenDate, TS.closetripDate, TS.tripStutesId "
							+ " from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
							+ " where TS.tripOpenDate between '"+startDate+"' AND '"+endDate+"' AND TS.companyId = " + companyId + " "
							+ " AND TS.markForDelete = 0 ORDER BY TS.tripSheetID DESC ",Object[].class);
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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[8]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[9]));
					select.setTripStutesId((short) vehicle[10]);
					select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[10]));

					Dtos.add(select);
				}
			}
			return Dtos;

		}
		
		@Override
		public List<TripSheetDto> getTotalTripSheetOpenDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
							+ " TR.routeName, TS.tripOpenDate, TS.closetripDate, TS.tripStutesId "
							+ " from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
							+ " where TS.tripOpenDate between '"+startDate+"' AND '"+endDate+"' AND TS.companyId = " + companyId + " "
							+ " AND TS.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_SAVED+","+TripSheetStatus.TRIP_STATUS_DISPATCHED+") "
							+ " AND TS.markForDelete = 0 ORDER BY TS.tripSheetID DESC ",
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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[8]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[9]));
					select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[10]));

					Dtos.add(select);
				}
			}
			return Dtos;

		}
		
		@Override
		public List<TripSheetDto> getTotalTripSheetClosedDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
							+ " TR.routeName, TS.tripOpenDate, TS.closetripDate, TS.tripStutesId "
							+ " from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
							+ " where TS.tripOpenDate between '"+startDate+"' AND '"+endDate+"' AND TS.companyId = " + companyId + " "
							+ " AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_CLOSED+" AND TS.markForDelete = 0 ORDER BY TS.tripSheetID DESC ",
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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[8]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[9]));
					select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[10]));
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		}
					
		@Override
		public List<TripSheetDto> getTotalTripSheetAcntClosedDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
							+ " TR.routeName, TS.tripOpenDate, TS.closetripDate, TS.tripStutesId "
							+ " from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
							+ " where TS.tripOpenDate between '"+startDate+"' AND '"+endDate+"' AND TS.companyId = " + companyId + " "
							+ " AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+" AND TS.markForDelete = 0 ORDER BY TS.tripSheetID DESC ",
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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[8]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[9]));
					select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[10]));
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		}
		
		@Override
		public List<TripSheetDto> getTotalRunKmList(String startDate, String endDate, Integer companyId) throws Exception {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
							+ " TR.routeName, TS.tripOpenDate, TS.closetripDate, TS.tripStutesId, TS.tripUsageKM, TS.tripOpeningKM, "
							+ " TS.tripClosingKM from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
							+ " where TS.tripOpenDate between '"+startDate+"' AND '"+endDate+"' AND TS.companyId = " + companyId + " "
							+ " AND TS.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+","+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") "
							+ " AND TS.markForDelete = 0 ORDER BY TS.tripSheetID DESC ",
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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[8]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[9]));
					select.setTripStutesId((short) vehicle[10]);
					select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[10]));
					if(vehicle[11] != null)
						select.setTripUsageKM((int) vehicle[11]);
					if(vehicle[12] != null)
						select.setTripOpeningKM((int) vehicle[12]);
					if(vehicle[13] != null)
						select.setTripClosingKM((int) vehicle[13]);
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		}
		
		@Override
		public ValueObject getToDaysTripSheetList(ValueObject valueObject) throws Exception {
			CustomUserDetails			userDetails					= null;
			List<TripSheetDto> 			tripSheet 					= null;
			HashMap<String, Object> 	configuration				= null;
			try {
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG);
				Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
				String today = dateFormat2.format(dateFormat.parse(valueObject.getString("selectedDate")));
				tripSheet =	listTodayTripSheet(today, userDetails);
				
				valueObject.put("configuration", configuration);
				valueObject.put("permissions", permission);
				valueObject.put("tripSheet", tripSheetBL.prepareListofTripSheet(tripSheet));
				valueObject.put("TripSheetAdvance", tripSheetBL.Total_Amount_TRIPSHEET_Advance(tripSheet));
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				tripSheet 					= null;
				userDetails					= null;
				configuration				= null;
			}
		}
		
		@Override
		public ValueObject getDispatchTripSheetList(ValueObject valueObject) throws Exception {
			CustomUserDetails 			userDetails		= null;
			HashMap<String, Object> 	configuration	= null;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
				
				valueObject.put("configuration", configuration);
				
				Page<TripSheet> page = getDeployment_Page_TripSheet(TripSheetStatus.TRIP_STATUS_SAVED,
						valueObject.getInt("pageNumber"), userDetails);
				
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("TripDispatch", page.getTotalElements());

				List<TripSheetDto> pageList = tripSheetBL.prepareListofTripSheet(listTrip_Page_Status(TripSheetStatus.TRIP_STATUS_SAVED, valueObject.getInt("pageNumber"), userDetails));

				valueObject.put("TripSheet", pageList);

			} catch (NullPointerException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}finally {
				userDetails		= null;
				configuration	= null;
			}
			return valueObject;
		}
		
		@Override
		public ValueObject getManageTripSheetList(ValueObject valueObject) throws Exception {
			HashMap<String, Object> 	configuration	= null;
			CustomUserDetails 			userDetails 	= null;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
				valueObject.put("configuration", configuration);
				Page<TripSheet> page = getDeployment_Page_TripSheet(TripSheetStatus.TRIP_STATUS_DISPATCHED,
						valueObject.getInt("pageNumber"), userDetails);
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("TripManage", page.getTotalElements());
				List<TripSheetDto> pageList = tripSheetBL.prepareListofTripSheet(listTrip_Page_Status(TripSheetStatus.TRIP_STATUS_DISPATCHED, valueObject.getInt("pageNumber"), userDetails));

				valueObject.put("TripSheet", pageList);

			} catch (NullPointerException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}finally {
				userDetails	= null;
			}
			return valueObject;
		}
		
		@Override
		public ValueObject getAdvCloseTripSheetList(ValueObject valueObject) throws Exception {
			HashMap<String, Object> 	configuration	= null;
			CustomUserDetails userDetails = null;
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
				valueObject.put("configuration", configuration);
				Page<TripSheet> page = getDeployment_Page_TripSheet(TripSheetStatus.TRIP_STATUS_DISPATCHED,
						valueObject.getInt("pageNumber"), userDetails);
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("TripClose", page.getTotalElements());
				List<TripSheetDto> pageList = tripSheetBL.prepareListofTripSheet(listTrip_Page_Status(TripSheetStatus.TRIP_STATUS_DISPATCHED, valueObject.getInt("pageNumber"), userDetails));

				valueObject.put("TripSheet", pageList);

			} catch (NullPointerException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}
			return valueObject;
		}
		
		@Override
		public ValueObject getTripSheetAccountList(ValueObject valueObject) throws Exception {
			CustomUserDetails 			userDetails 	= null;
			try {

				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				Page<TripSheet> page = getDeployment_Page_TripSheet(TripSheetStatus.TRIP_STATUS_CLOSED,
						valueObject.getInt("pageNumber"), userDetails);
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("TripSheetCount", page.getTotalElements());

				List<TripSheetDto> pageList = tripSheetBL.prepareListofTripSheet(listTrip_Page_Status(TripSheetStatus.TRIP_STATUS_CLOSED, valueObject.getInt("pageNumber"), userDetails));

				valueObject.put("TripSheet", pageList); 

			} catch (NullPointerException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}
			return valueObject;
		}
		
		@Override
		public ValueObject getTripSheetAccountCloseList(ValueObject valueObject) throws Exception {
			CustomUserDetails 			userDetails 	= null;
			try {

				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				Page<TripSheet> page = getDeployment_Page_TripSheet(TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED,
						valueObject.getInt("pageNumber"), userDetails);
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("TripSheetCount", page.getTotalElements());

				List<TripSheetDto> pageList = tripSheetBL.prepareListofTripSheet(listTrip_Page_Status(TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED, valueObject.getInt("pageNumber"), userDetails));

				valueObject.put("TripSheet", pageList); 

			} catch (NullPointerException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}finally {
				userDetails	= null;
			}
			return valueObject;
		}
		
		@Override
		public List<TripSheetDto> getTodaysTripOpenStatusList(String startDate, String endDate, Integer companyId) throws Exception {
			
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
							+ " TR.routeName, TS.tripOpenDate, TS.closetripDate, TS.tripStutesId "
							+ " from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
							+ " WHERE TS.tripStutesId  IN ("+TripSheetStatus.TRIP_STATUS_SAVED
							+ " , " + TripSheetStatus.TRIP_STATUS_DISPATCHED
							+ " ) AND TS.closetripDate BETWEEN '"+ startDate +"' AND '"+ endDate +"' AND TS.companyId = " +companyId 
							+ "AND TS.markForDelete = 0 ",
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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[8]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[9]));
					select.setTripStutesId((short) vehicle[10]);
					select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[10]));
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		}
		
		@Override
		public List<TripSheetDto> getTripSheetDispatchedOverdueList(String todaysDate, Integer companyId) throws Exception {
			
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
							+ " TR.routeName, TS.tripOpenDate, TS.closetripDate, TS.tripStutesId "
							+ " from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
							+ " WHERE TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_DISPATCHED+" "
							+ " AND TS.tripOpenDate <= '"+ todaysDate +"' AND TS.companyId = " +companyId 
							+ "AND TS.markForDelete = 0 ",
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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[8]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[9]));
					select.setTripStutesId((short) vehicle[10]);
					select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[10]));
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		}
		
		@Override
		public List<TripSheetDto> getTripSheetSavedOverdueList(String todaysDate, Integer companyId) throws Exception {
			
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
							+ " TR.routeName, TS.tripOpenDate, TS.closetripDate, TS.tripStutesId "
							+ " from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
							+ " WHERE TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_SAVED+" "
							+ " AND TS.tripOpenDate <= '"+ todaysDate +"' AND TS.companyId = " +companyId 
							+ "AND TS.markForDelete = 0 ",
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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[8]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[9]));
					select.setTripStutesId((short) vehicle[10]);
					select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[10]));
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		}

		@Override
		public ValueObject getRecentTripListByVehicleId(ValueObject valueObjIn) throws Exception {
			
			HashMap<String, Object>  configuration				= null;
			int						 companyId					= 0;
			int						 vehicleId					= 0;
			List<TripSheetDto>		 tripFinalList				= null;
			int 					 noOfDaysToShowTripSheet	= 0;
			LocalDate 			     date					 	= null;
			List<TripSheetDto>		 tripList					= null;

			try {
				
				companyId		= valueObjIn.getInt("companyId",0);
				vehicleId		= valueObjIn.getInt("vehicleId",0);
				tripFinalList	= new ArrayList<>();
				
				if(companyId == 0) {
					valueObjIn.put("message", "Unauthorized Access !!");
					return valueObjIn;
				}
				
				configuration			= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
				noOfDaysToShowTripSheet = (int) configuration.get(FuelConfigurationConstants.NO_OF_DAYS_TOSHOW_TRIPSHEET);
				date 					= LocalDate.now().minusDays(noOfDaysToShowTripSheet);
				java.sql.Date sqlDate 	= java.sql.Date.valueOf( date );
			    tripList				= getRecentTripListByVid(vehicleId, sqlDate);
				
				if (tripList != null && !tripList.isEmpty()) {
					for (TripSheetDto tripSheetDto : tripList) {

						TripSheetDto Dto = new TripSheetDto();
						Dto.setTripSheetID(tripSheetDto.getTripSheetID());
						Dto.setTripSheetNumber(tripSheetDto.getTripSheetNumber());
						Dto.setTripOpenDate(tripSheetDto.getTripOpenDate());
						Dto.setClosetripDate(tripSheetDto.getClosetripDate());
						
						tripFinalList.add(Dto);
					}
				}
				valueObjIn.put("tripFinalList",tripFinalList);
				
				return valueObjIn;
				
			} catch (Exception e) {
				throw e;
			} finally {
				configuration		= null;
				companyId			= 0;   
				vehicleId			= 0;   
			}
		}
		@Override
		public ValueObject getTripsheetByTripsheetId(ValueObject valueObject) throws Exception {
			CustomUserDetails			userDetails						= null;
			TripSheetDto				tripsheetDto					= null;
			Double						B_IncomeTotal					= 0.0;
			Double						E_IncomeTotal					= 0.0;
			Double  				    B_IncomeDue 					= 0.0;
			Double                      E_IncomeDue 					= 0.0;
			HashMap<String, Object> 	configuration					= null;
			try {
				userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
				
				tripsheetDto = tripSheetBL.GetTripSheetDetails(getTripSheetDetails(valueObject.getLong("tripsheetId"), userDetails));
				
				List<TripSheetIncomeDto> incomeDto = tripSheetService.findAllTripSheetIncome(valueObject.getLong("tripsheetId"), userDetails.getCompany_id());
				List<TripsheetDueAmountDto> dueAmountList = tripSheetService.getDueAmountListByTripsheetId(valueObject.getLong("tripsheetId"), userDetails.getCompany_id());
				
				
				System.err.println("configuration - "+ configuration);
				System.err.println("property "+ configuration.get("validateBillTypeIncome"));
				
				
				if((boolean) configuration.get("validateBillTypeIncome"))
				{			
					if(incomeDto != null) {
						for(TripSheetIncomeDto income : incomeDto) {
							if(income.getBillSelectionId() == WayBillTypeConstant.WITH_BILL)
								B_IncomeTotal += income.getIncomeAmount();
							if(income.getBillSelectionId() == WayBillTypeConstant.WITHOUT_BILL)
								E_IncomeTotal += income.getIncomeAmount();
						}
					}
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
				}
				
				valueObject.put("tripsheet", tripsheetDto);
				valueObject.put("B_IncomeTotal",B_IncomeTotal-B_IncomeDue);
				valueObject.put("E_IncomeTotal", E_IncomeTotal-E_IncomeDue);
				
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				userDetails						= null;     
			}
		}	
		@Override
		public ValueObject saveDriverPenalty(ValueObject valueObject) throws Exception {
			try {
				DriverSalaryAdvance Advance = DBL.prepareDriverPenalty(valueObject);
				DriverSalaryAdvanceService.register_New_DriverSalaryAdvance(Advance);
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
			}
		}	
		@Override
		public ValueObject getDriverPenaltyByTripsheetId(ValueObject valueObject) throws Exception {
			CustomUserDetails			userDetails						= null;
			try {
				userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				List<DriverSalaryAdvanceDto> DriverAdvanvce = DriverSalaryAdvanceService.List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(valueObject.getLong("tripsheetId"),
						DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY, userDetails.getCompany_id());
				
				valueObject.put("DriverAdvanvce", DriverAdvanvce);
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
			}
		}	
		@Override
		public ValueObject deleteDriverPenalty(ValueObject valueObject) throws Exception {
			try {
				DriverSalaryAdvanceService.DELETE_PENALTY_IN_TRIPSHEET_AID(valueObject.getLong("dsaId"));		
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
			}
		}	
		
		@Transactional
		public void updateTotalNetIncomeInTripsheet(Long tripSheetID, Integer companyId) throws Exception {
			
			entityManager.createQuery(
					"Update TripSheet AS T Set T.tripTotalincome= (SELECT SUM(netIncomeAmount) FROM TripSheetIncome WHERE tripSheetID ="
							+ tripSheetID + " AND companyId = " + companyId
							+ " AND markForDelete = 0) WHERE T.tripSheetID = " + tripSheetID + "")
			.executeUpdate();
		}	
		/*public ValueObject getTripRouteSerachList(ValueObject valueObject) throws Exception {
			CustomUserDetails 		userDetails 				= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);

			int noOfDaysToShowTripSheet = (int) configuration.get(FuelConfigurationConstants.NO_OF_DAYS_TOSHOW_TRIPSHEET);
			LocalDate date = LocalDate.now().minusDays(noOfDaysToShowTripSheet);
			
			java.sql.Date sqlDate = java.sql.Date.valueOf( date ); 
			
		    
		    List<TripSheetDto>	tripFinalList	=  new ArrayList<>();

		    List<TripSheetDto>	tripList	=   tripSheetService.getRecentTripListByVid(vid, sqlDate);
			
			if (tripList != null && !tripList.isEmpty()) {
				for (TripSheetDto tripSheetDto : tripList) {

					TripSheetDto Dto = new TripSheetDto();
					Dto.setTripSheetID(tripSheetDto.getTripSheetID());
					Dto.setTripSheetNumber(tripSheetDto.getTripSheetNumber());
					Dto.setTripOpenDate(tripSheetDto.getTripOpenDate());
					Dto.setClosetripDate(tripSheetDto.getClosetripDate());
					
					
					tripFinalList.add(Dto);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(tripFinalList));
			
		} catch (Exception e) {
			throw e;
		}
	}*/
		
		@Override
		@Transactional
		public void removeTripSheetFuel(Long fuel_id) throws Exception {

			entityManager.createQuery(
					"Update TripSheetExpense SET markForDelete = 1  WHERE fuel_id = " + fuel_id + "")
			.executeUpdate();
		
		}
		
		@Override
		public TripSheetDto getLastTripSheetDetails(Integer vid, String date) throws Exception {
			try {

				Query queryt = 	null;
				
					queryt = entityManager.createQuery(
							" SELECT T.tripSheetID, T.tripSheetNumber, T.tripStartDiesel, T.tripEndDiesel, T.tripClosingKM "
							+ " from TripSheet as T where T.vid = "+vid+" and T.dispatchedByTime < '"+date+"' "
							+ " AND T.markForDelete = 0 order by T.tripSheetID desc");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}

				TripSheetDto select;
				if (vehicle != null) {
					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					if(vehicle[2] != null)
						select.setTripStartDiesel((Double) vehicle[2]);
					if(vehicle[3] != null)
						select.setTripEndDiesel((Double) vehicle[3]);
					if(vehicle[4] != null)
						select.setTripClosingKM((Integer) vehicle[4]);
					
				} else {
					return null;
				}

				return select;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		
		@Override
		@Transactional
		public void updateBalanceFuel(Long tripSheetId, Double balanceFuel) throws Exception {
			entityManager.createQuery(
					"Update TripSheet SET tripEndDiesel = "+balanceFuel+"  WHERE tripSheetID = " + tripSheetId + "")
			.executeUpdate();
		}
		
		@Override
		@Transactional
		public void saveVoucherDate(ValueObject object) throws Exception {
			try {
				
				TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(object.getLong("tripSheetId"));
				
				String voucherDate = object.getString("voucherDate");
				
				Date date = new java.sql.Date(dateFormat.parse(voucherDate).getTime());
				
				entityManager.createQuery(
						"Update TripSheet SET voucherDate = '"+date+"'  WHERE tripSheetID = " + object.getLong("tripSheetId") + "")
				.executeUpdate();
				
				entityManager.createQuery(
						"Update TripSheetExpense SET voucherDate = '"+date+"'  WHERE tripsheet.tripSheetID = " + object.getLong("tripSheetId") + "")
				.executeUpdate();
			
				TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
				tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
				
			} catch (Exception e) {
				throw e;
			}
			
		}
		@SuppressWarnings("unchecked")
		@Override
		public ValueObject getDriverDetailsFromItsGatewayApi(ValueObject valueObject) throws Exception {
			HashMap<String, Object> 					configuration 					= null;
			JSONObject 									inputObj 						= null;
			String 										startTime 						= "00:00";
			String 										verifyCall 						= null;
			String 										apiURL 							= "";
			String										driverName						= null;
			String										driverPhone						= null;
			String										routeTime						= null;
			String										dispatchTime					= null;
			String										routeName						= null;
			java.util.Date 								startDate 						= null;
			java.util.Date 								endDate 						= null;
			String 										fromDate						= null;
			String 										toDate							= null;
			String[] 									From_TO_Array 					= null;
			VehicleExtraDetails 						busDeatils						= null; 
			Driver 										diverDetails					= null; 
			ValueObject 								incomeDetails					= null;
			LinkedHashMap<Object, Object> 				incomeValue						= null;
			ArrayList<LinkedHashMap<Object, Object>> 	income							= null;
			try {
				inputObj 		= new JSONObject();
				configuration	= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
				
				From_TO_Array 	= valueObject.getString("journeyDate").split("  to  ");
				startDate 		= DateTimeUtility.getDateTimeFromDateTimeString(From_TO_Array[0], startTime);
				endDate 		= DateTimeUtility.getDateTimeFromDateTimeString(From_TO_Array[1], startTime);
				fromDate 		= dateTimeFormat.format(startDate);
				toDate 			= dateTimeFormat.format(endDate);
				
				if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_ITS_GATEWAY_BUS_INCOME)) {
					 busDeatils = vehicleExtraDetailsRepository.getBusId(valueObject.getInt("vehicleId"), valueObject.getInt("companyId"));
				}
				
				if(busDeatils != null) {
					
					verifyCall 		= (String) configuration.get(TripSheetConfigurationConstant.VERIFY_CALL_FOR_ITS_GATEWAY);
					apiURL 			= (String) configuration.get(TripSheetConfigurationConstant.URl_FOR_DRIVER_DETAILS_ITSGATEWAY_API);

					inputObj.put(VehicleExtraDetailsConstant.FROM_DATE,   (Object)fromDate);
					inputObj.put(VehicleExtraDetailsConstant.TO_DATE,     (Object)toDate);
					inputObj.put(VehicleExtraDetailsConstant.VERIFY_CALL, (Object)verifyCall);
					inputObj.put(VehicleExtraDetailsConstant.BUS_ID,      (Object)busDeatils.getBusId());

					final HttpResponse<String> response = (HttpResponse<String>)Unirest.post(apiURL).header("Content-Type", "application/json").body(inputObj).asString();
					
					if (response.getStatus() == 200) {
						incomeDetails = JsonConvertor.toValueObjectFormSimpleJsonString((String)response.getBody());
						
						if(incomeDetails.get("data") != null) {
		                	incomeValue = (LinkedHashMap<Object, Object>) incomeDetails.get("data");
		                	
		                	if(!incomeValue.isEmpty() && incomeValue.get("Data") != null) {
		                	    income	= (ArrayList<LinkedHashMap<Object, Object>>) incomeValue.get("Data");
		                		
		                		for (Map<Object, Object> entry : income) {
		                			driverName  	= (String) entry.get("DriverName");
		                			driverPhone 	= (String) entry.get("DriverPhoneNo");
		                			routeTime 		= (String) entry.get("RouteTime");
		                			routeName 		= (String) entry.get("RouteName");
		                			dispatchTime 	= displayFormat.format((java.util.Date)parseFormat.parse(routeTime));
		                			
		                			valueObject.put("driverName", driverName);
		                			valueObject.put("routeTime", routeTime);
		                			valueObject.put("dispatchTime", dispatchTime);
		                			valueObject.put("routeName", routeName);
		                			
		                			diverDetails = driverRepository.getDriverFromMobileNumber(driverPhone, valueObject.getInt("companyId"));
		                			
		                			if(diverDetails != null) {
		                				valueObject.put("diverDetails", diverDetails);
			                			valueObject.put("diverDetailsFound", true);
		                			} else {
		                				valueObject.put("diverDetailsNotFound", true);
		                			}
		                		}	 
		                		
		                	} else {
		        				valueObject.put("noRecordFound", true);
		        				System.err.println("Driver Data is Empty");
		                	}
		                }
					} else {
						valueObject.put("noRecordFound", true);
						System.err.println("Driver Response is not 200");
					}
					
				} else {
					valueObject.put("noBusIdConfiguredForThisVehicle", true);
				}
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("Exception get ITS " + e);
				e.printStackTrace();
				return valueObject;
			} finally {
				configuration 		= null;
				inputObj 			= null;
				verifyCall 			= null;
				apiURL 				= null;
			}
		}
		
		public ValueObject getVehicleTripSheetDetails(ValueObject valueObject) throws Exception {
			Integer				vid 			= 0;
			String 				invoiceDate		= null;
			try {
						vid			= valueObject.getInt("vid", 0);
						invoiceDate	= valueObject.getString("invoicestartDate");
				Timestamp	invoice	= DateTimeUtility.getTimeStamp(invoiceDate, DateTimeUtility.DD_MM_YYYY);
				
						invoiceDate	= DateTimeUtility.getDateFromTimeStamp(invoice, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				
				TypedQuery<Object[]> queryt = 	null;
				
				queryt = entityManager.createQuery(
						" SELECT T.tripSheetID, T.tripSheetNumber, T.tripOpenDate, T.closetripDate "
						+ " from TripSheet as T where T.vid = "+vid+" and '"+invoiceDate+"' between T.tripOpenDate AND T.closetripDate "
						+ " AND T.markForDelete = 0 order by T.tripSheetID desc", Object[].class);
			
				List<Object[]> results = queryt.getResultList();

				List<TripSheetDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					
					Dtos = new ArrayList<TripSheetDto>();
					TripSheetDto list = null;
					for (Object[] result : results) {
						
						list = new TripSheetDto();
						list.setTripSheetID((long) result[0]);
						list.setTripSheetNumber((long) result[1]);
						list.setTripOpenDateOn((java.util.Date) result[2]);
						list.setClosetripDateOn((java.util.Date) result[3]);
						
						if(list.getTripOpenDateOn() != null) {
							list.setTripOpenDate(dateFormat_Name.format(list.getTripOpenDateOn()));
						}
						
						if(list.getClosetripDateOn() != null) {
							list.setClosetripDate(dateFormat_Name.format(list.getClosetripDateOn()));
						}
						
						Dtos.add(list);
					}
				}
				
				valueObject.put("tripSheet", Dtos);
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		
		@Override
		@Transactional
		public ValueObject updateTripSheetOpeningKM(ValueObject valueObject) throws Exception {
			TripSheet		tripSheet		= null;
			Integer			tripUsaseKm		= null;
			Integer			tripOpeningKM	= null;
			Integer			tripClosingKM	= null;
			try {
				
				tripSheet		=	getTripSheet(valueObject.getLong("tripSheetID"));
				tripOpeningKM	= 	valueObject.getInt("tripOpeningKM");
				tripClosingKM	= 	valueObject.getInt("tripClosingKM");
				if(tripSheet != null) {
					tripUsaseKm	= tripClosingKM - tripOpeningKM ;
					
					entityManager.createQuery(
							" UPDATE TripSheet SET tripOpeningKM = "+tripOpeningKM+", tripUsageKM = "+tripUsaseKm+" "
									+ " WHERE tripSheetID = "+valueObject.getLong("tripSheetID")+" AND vid = "+valueObject.getInt("vid")+"  ")
					.executeUpdate();
					
					/*if(vehicle.getVehicle_Odometer() < tripClosingKM) {
						vehicleService.updateCurrentOdoMeter(vehicle.getVid(), tripClosingKM, vehicle.getCompany_Id());
						
						VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
						odometerHistory.setVid(tripSheet.getVid());
						odometerHistory.setVehicle_Odometer(tripClosingKM);
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
						odometerHistory.setVoh_date(toDate);
						odometerHistory.setVoh_updatelocation("TripSheet Closing Entry");
						odometerHistory.setVoh_updateId(tripSheet.getTripSheetID());
						odometerHistory.setCompanyId(vehicle.getCompany_Id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(odometerHistory);
					}*/
				}
				valueObject.put("tripUsaseKm", tripUsaseKm);
			return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				tripSheet		= null;
				tripUsaseKm		= null;
				tripClosingKM	= null;
			}
		}
		
		
		
		@Override
		@Transactional
		public ValueObject saveDueAmount(ValueObject valueObject) throws Exception {
			TripsheetDueAmount 		dueAmount			= null;
			try {
				dueAmount = tripSheetBL.saveDueAmount(valueObject);
				tripsheetDueAmountRepository.save(dueAmount);
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			} finally {
				dueAmount = null;
			}
		}
		
		@Override
		public ValueObject getDueAmountList(ValueObject valueObject) throws Exception {
			CustomUserDetails    			userDetails    			= null;
			List<TripsheetDueAmountDto> 	dueAmountList			= null;
			try {
				userDetails	  = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				dueAmountList = getDueAmountListByTripsheetId(valueObject.getLong("tripsheetId"),
									userDetails.getCompany_id());
				
				if(dueAmountList != null) {
					valueObject.put("dueAmountList", dueAmountList);
				}
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			} finally {
				userDetails   = null;
				dueAmountList = null;
			}
		}
		
		@Transactional
		@Override
		public List<TripsheetDueAmountDto> getDueAmountListByTripsheetId(Long tripsheetId, int companyId) {
			
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT TD.tripsheetDueAmountId, TD.tripSheetID, TD.driver_id, TD.dueAmount, TD.approximateDate, "
							+ " TD.dueDate, TD.remark, D.driver_firstname, D.driver_Lastname, TD.balanceAmount, "
							+ " DJT.driJobType , D.driver_fathername, TD.billSelectionId "
							+ " FROM TripsheetDueAmount AS TD "
							+ " INNER JOIN TripSheet T ON T.tripSheetID = TD.tripSheetID "
							+ " LEFT JOIN Driver D ON D.driver_id = TD.driver_id "
							+ " LEFT JOIN DriverJobType DJT ON DJT.driJobId = D.driJobId "
							+ " WHERE TD.tripSheetID = "+tripsheetId+" AND TD.markForDelete = 0 "
							+ " AND TD.companyId = "+companyId+" ORDER BY TD.tripsheetDueAmountId desc ",
					Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<TripsheetDueAmountDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripsheetDueAmountDto>();
				TripsheetDueAmountDto list = null;
				for (Object[] result : results) {
					list = new TripsheetDueAmountDto();

					list.setTripsheetDueAmountId((long) result[0]);
					list.setTripSheetID((long) result[1]);
					list.setDriver_id((int) result[2]);
					if(result[3] != null)
					list.setDueAmount(Double.parseDouble(toFixedTwo.format(result[3])));
					list.setApproximateDateStr(dateFormat.format((java.sql.Timestamp) result[4]));
					list.setDueDateStr(dateFormat.format((java.sql.Timestamp) result[5]));
					list.setRemark((String) result[6]);
					list.setDriver_firstname((String) result[7]);
					if(result[8] != null) {
						list.setDriver_Lastname((String) result[8]);
					}else {
						list.setDriver_Lastname(" ");
					}
					if(result[9] != null)
					list.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[9])));
					list.setDriJobType((String) result[10]);
					if(result[11] != null) {
						list.setDriver_Lastname(list.getDriver_Lastname()+" - "+ result[11]);
					}
					if(result[12] !=null) {
						list.setBillSelectionId((short)result[12]);
						list.setBillType((WayBillTypeConstant.getIncomeTypeName((short)result[12])));
					}
					Dtos.add(list);
				}
			}
			return Dtos;
		}
		
		@Override
		public double getTotalDueAmountByTripsheetId (Long tripsheetId, int companyId) throws Exception {
			List<TripsheetDueAmountDto> 	dueAmountList			= null;
			double							finalDueAmount			= 0.0;			
			try {
				dueAmountList = getDueAmountListByTripsheetId(tripsheetId, companyId);
				
				if(dueAmountList != null) {
					for(TripsheetDueAmountDto due : dueAmountList) {
						finalDueAmount += due.getBalanceAmount(); 
					}
				}
				
				return Double.parseDouble(toFixedTwo.format(finalDueAmount));
				
			} catch (Exception e) {
				throw e;
			} finally {
				dueAmountList = null;
			}
		}
		
		@Override
		@Transactional
		public ValueObject removeDueAmount(ValueObject valueObject) throws Exception {
			try {
				tripsheetDueAmountRepository.deleteremoveDueAmountById(valueObject.getLong("tripsheetDueAmountId"));
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
		
		@Override
		public ValueObject getDueAmountReport(ValueObject valueObject) throws Exception {
			String										dateRange				= null;
			int											driverId				= 0;
			CustomUserDetails							userDetails				= null;
			List<TripsheetDueAmountDto> 				dueAmountList			= null;
			int billselectionId                                                 = 0;
			try {
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				driverId		= valueObject.getInt("driverId", 0);
				dateRange		= valueObject.getString("dueAmountRange");
				billselectionId = valueObject.getInt("billselectionId",0);
					
				
				if(dateRange != null) {
					String dateRangeFrom = "", dateRangeTo = "";
					String[] From_TO_DateRange = null;

					From_TO_DateRange = dateRange.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

					String driverName = "", Date = "", billselection = "";
					
					if(driverId > 0)
					{
						driverName = " AND TD.driver_id = "+ driverId +" ";
					}
					
					Date =  " AND TD.dueDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
					
					if(billselectionId > 0) {
						billselection = " AND TD.billSelectionId = "+ billselectionId +" ";
					}
					String query = " "+driverName+"  "+Date+" " +billselection+ " ";
					
					System.err.println("query -- "+ query);
					
					dueAmountList = TripSheetDaoImpl.getDueAmountReportList(query, userDetails.getCompany_id());
					if(dueAmountList != null) {
						valueObject.put("dueAmountList", dueAmountList);
					}
				}
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			}finally {
				dueAmountList		= null;
				userDetails			= null;
				dateRange			= null;
			}
		}
		
		@Override
		public ValueObject getDueAmountPaymentById (ValueObject valueObject) throws Exception {
			TripsheetDueAmountDto 				dueAmountDetails		= null;
			CustomUserDetails					userDetails				= null;
			ArrayList<TripsheetDueAmountDto>	paymentType				= new ArrayList<>();
			ArrayList<TripsheetDueAmountDto>	paymentMode				= new ArrayList<>();
			List<TripExpense> 					TripExpenseList 		= new ArrayList<TripExpense>();
			try {
				userDetails	     = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				dueAmountDetails = TripSheetDaoImpl.getDueAmountPaymentById(valueObject.getLong("tripsheetDueAmountId"), userDetails.getCompany_id());
				
				if(dueAmountDetails != null) {
					valueObject.put("dueAmountDetails", dueAmountDetails);
					
					paymentMode.add(new TripsheetDueAmountDto(TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_AMOUNT, TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_AMOUNT_NAME));
					paymentMode.add(new TripsheetDueAmountDto(TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_EXPENSE, TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_EXPENSE_NAME));
					
					paymentType.add(new TripsheetDueAmountDto(TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PARTIALLY_PAID, TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PARTIALLY_PAID_NAME));
					//paymentType.add(new TripsheetDueAmountDto(TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_NEGOTIABLE_PAID, TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_NEGOTIABLE_PAID_NAME));
					paymentType.add(new TripsheetDueAmountDto(TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PAID, TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PAID_NAME));
					
					for (TripExpense Expense : tripExpenseService.listTripExpense(userDetails.getCompany_id())) {

						TripExpense bean = new TripExpense();
						bean.setExpenseID(Expense.getExpenseID());
						bean.setExpenseName(Expense.getExpenseName());

						TripExpenseList.add(bean);
					}
					
					valueObject.put("paymentMode", paymentMode);
					valueObject.put("paymentType", paymentType);
					valueObject.put("TripExpenseList", TripExpenseList);
					valueObject.put("TransactionType",PaymentTypeConstant.getPaymentTypeList2());
				}
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			} finally {
				dueAmountDetails = null;
			}
		}
		
		@Override
		@Transactional
		public ValueObject saveDueAmountPaymentDetails(ValueObject valueObject) throws Exception {
			try {
				short paymentModeId = valueObject.getShort("paymentModeSettle");
				
				if(paymentModeId == TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_AMOUNT) {
					saveDueAmountPaymentByAmount(valueObject);
				} else {
					saveDueAmountPaymentByExpense(valueObject);
				}
				
				valueObject.put("accessToken", Utility.getUniqueToken(request));
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
		
		@Override
		@Transactional
		public ValueObject saveDueAmountPaymentByAmount(ValueObject valueObject) throws Exception {
			TripsheetDueAmountPayment   		duePayment      			= null;
			short 								dueStatus					= 0;
			double 								finalBalance				= 0.0;
			CustomUserDetails					userDetails				    = null;
			HashMap<String,Object>				CashBookconfig				= null;
			CashBookDto 						cashbook 					= null;
			CashBookSequenceCounter 			sequenceCounter 			= null;
			boolean 					        saveExepenseInCashBook	    = false;
			MultipartFile 						file 						= null;
			ValueObject							cashbookObj					= null;
			List<String>						cashbookEntries				= new ArrayList<>();
			Vehicle 							vehicle						= null;
			try {
				userDetails	    			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				CashBookconfig              = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
				duePayment = tripSheetBL.saveDueAmountPayment(valueObject);
				tripsheetDueAmountPaymentRepository.save(duePayment);
				
				TripSheet trip = getTripSheetData(valueObject.getLong("tripsheetId"), userDetails.getCompany_id());
				vehicle  = vehicleService.getVehicel(trip.getVid(), userDetails.getCompany_id());
				 
				if((boolean) CashBookconfig.get("addDueExpenseInCashBook") && duePayment.getPaidAmount()>0) {
					try {
						saveExepenseInCashBook= true;

						if(valueObject.getShort("billselectionId") == WayBillTypeConstant.WITH_BILL){
							cashbookEntries.add(CashBookBalanceStatus.SSTS_CASH_BOOK_NAME);
							//cashbookEntries.add(CashBookBalanceStatus.JBL_CASH_BOOK_NAME);
							cashbookEntries.add(CashBookBalanceStatus.COMBINED_CASHBOOK_NAME);
							
						}else if(valueObject.getShort("billselectionId") == WayBillTypeConstant.WITHOUT_BILL) {
							//cashbookEntries.add(CashBookBalanceStatus.JBL_CASH_BOOK_NAME);
							//new
							cashbookEntries.add(CashBookBalanceStatus.JBL_CASH_BOOK_NAME);
							cashbookEntries.add(CashBookBalanceStatus.COMBINED_CASHBOOK_NAME);
						}
						for (String entry : cashbookEntries) {

						    cashbook = cashbookbl.PrepareCashBook(duePayment.getPaidAmount() ,  dateFormat.format(duePayment.getCreationDate()),trip.getTripFristDriverID(),userDetails.getCompany_id());
						    CashBookName	bookName =  cashbookNameService.getCashBookByName(entry,userDetails.getCompany_id());
							
						    if(valueObject.getShort("billselectionId") == WayBillTypeConstant.WITHOUT_BILL) {
						    	cashbook.setINCOME_TYPE("E_INCOME");
						    }
						    
						    cashbook.setCASH_AMOUNT(duePayment.getPaidAmount());
							cashbook.setPAYMENT_TYPE_ID(CashBookPaymentType.PAYMENT_TYPE_CREDIT);
							cashbook.setCASH_BOOK_ID(bookName.getNAMEID());
							cashbook.setCASH_BOOK_NO(bookName.getCASHBOOK_NAME());
						 	cashbook.setCASH_VOUCHER_NO(bookVoucherSequenceCounterService.getCashVoucherNumberAndUpdateNext(cashbook.getCASH_BOOK_ID(),userDetails.getCompany_id()));
						 	cashbook.setCASH_REFERENCENO("<a href='/showVehicle?vid="+trip.getVid()+"'>"+vehicle.getVehicle_registration()+"</a><br><a href='/showTripSheet.in?tripSheetID=" + trip.getTripSheetID() + "'> TS - " + trip.getTripSheetNumber()+ " </a>");
					 	
						 	Driver driver = driverService.getDriver(valueObject.getInt("driverId"),userDetails);
						 	
						 	cashbook.setCASH_PAID_RECEIVED(driver.getDriver_firstname() +" "+driver.getDriver_Lastname());
						 	cashbook.setCASH_PAID_BY(userDetails.getEmail());
						 	cashbookObj = CashBookService.saveCashBookMissingEntry(cashbook,file, saveExepenseInCashBook);
						}
					 	
					 	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
				//cashbook ends
				
				finalBalance = valueObject.getDouble("amountBalance") - valueObject.getDouble("receiveAmtSettle");
				if(finalBalance == 0) {
					dueStatus = TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PAID;
				} else {
					dueStatus = TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PARTIALLY_PAID;
				}
				
				entityManager.createQuery(
						"Update TripsheetDueAmount AS TD "
								+ " SET TD.balanceAmount = TD.balanceAmount - "+valueObject.getDouble("receiveAmtSettle")+", "
								+ " TD.dueStatus = "+dueStatus+" "
								+ " WHERE TD.tripsheetDueAmountId = "+valueObject.getLong("tripsheetDueAmountId")+" ")
				.executeUpdate();
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public ValueObject saveDueAmountPaymentByExpense(ValueObject object) throws Exception {
			List<TripsheetDueAmountPayment>   				duePayment      				= null;
			CustomUserDetails									userDetails						= null;
			int 												companyId						= 0;
			long												tripsheetId						= 0;
			HashMap<Long, VehicleProfitAndLossTxnChecker>		expenseTxnCheckerHashMap		= null;
			HashMap<Long, TripSheetExpense> 					tripSheetExpenseHM				= null;
			short 												dueStatus						= 0;
			double 												finalBalance					= 0.0;
			double												amountBalance					= 0.0;
			CashBookDto 										cashbook 						= null;
			CashBookSequenceCounter 						    sequenceCounter 				= null;
			HashMap<String, Object> 							CashBookconfig					= null;
			CashBookBL  										cashbookbl   				    = new  CashBookBL();
			boolean 					                        saveExepenseInCashBook	        = false;
			MultipartFile 										file 							= null;
			ValueObject											cashbookObj						= null;
			List<String>										cashbookEntries				    = new ArrayList<>();
			Vehicle 											vehicle						    = null;
			try {
				userDetails	    			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				CashBookconfig              = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
				companyId		 			= userDetails.getCompany_id();
				tripsheetId		 			= object.getLong("tripsheetId");
				expenseTxnCheckerHashMap	= new HashMap<>();
				tripSheetExpenseHM			= new HashMap<>();
				
				TripSheet trip = getTripSheetData(tripsheetId, companyId);
				vehicle  = vehicleService.getVehicel(trip.getVid(),userDetails.getCompany_id());
				object.put("TripSheet", trip);
				
				object.put("expenseArry", JsonConvertor.toValueObjectFromJsonString(object.getString("expenseArry")));
				ArrayList<ValueObject> expenseArry	=(ArrayList<ValueObject>) object.get("expenseArry");
				
				duePayment = tripSheetBL.saveDueAmountPaymentByExpense(object);
				if(!duePayment.isEmpty()) {
					for(TripsheetDueAmountPayment duePay : duePayment) {
					tripsheetDueAmountPaymentRepository.save(duePay);
					}
				}
				UserProfileDto Orderingname = userProfileService
						.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
				Integer expensePlaceId = Orderingname.getBranch_id();
				object.put("expensePlaceId", expensePlaceId);
				
				short []  paymentType 			= {CashBookPaymentType.PAYMENT_TYPE_DEBIT,CashBookPaymentType.PAYMENT_TYPE_CREDIT};
				if(object.getShort("billselectionId") == WayBillTypeConstant.WITH_BILL){
					cashbookEntries.add(CashBookBalanceStatus.SSTS_CASH_BOOK_NAME);
					//cashbookEntries.add(CashBookBalanceStatus.JBL_CASH_BOOK_NAME);
					cashbookEntries.add(CashBookBalanceStatus.COMBINED_CASHBOOK_NAME);
					
				}else if(object.getShort("billselectionId") == WayBillTypeConstant.WITHOUT_BILL) {
					//cashbookEntries.add(CashBookBalanceStatus.JBL_CASH_BOOK_NAME);
					//new
					cashbookEntries.add(CashBookBalanceStatus.JBL_CASH_BOOK_NAME);
					cashbookEntries.add(CashBookBalanceStatus.COMBINED_CASHBOOK_NAME);
				}


				List<TripSheetExpense> TripExpenseList = tripSheetBL.saveDuePaymentAmountAsTripExpense(object);
				if(TripExpenseList != null && !TripExpenseList.isEmpty()) {
					for(TripSheetExpense tripExpense : TripExpenseList) {
						tripExpense.setBalanceAmount(tripExpense.getExpenseAmount());
						addTripSheetExpense(tripExpense);
						
						//cashbook code..
						if((boolean) CashBookconfig.get("addDueExpenseInCashBook") && tripExpense.getExpenseAmount()> 0) {
							try {
								saveExepenseInCashBook= true;
	
								
								for (String entry : cashbookEntries) {
									
								    for(short pyament : paymentType) {
										CashBookName	bookName =  cashbookNameService.getCashBookByName(entry,companyId);
										cashbook = cashbookbl.PrepareCashBook(tripExpense.getExpenseAmount(),  dateFormat.format(tripExpense.getCreated()),trip.getTripFristDriverID(),companyId);
										
										if(object.getShort("billselectionId") == WayBillTypeConstant.WITHOUT_BILL) {
									    	cashbook.setINCOME_TYPE("E_INCOME");
									    }
										cashbook.setPAYMENT_TYPE_ID(pyament);
										
										cashbook.setCASH_BOOK_ID(bookName.getNAMEID());
										cashbook.setCASH_BOOK_NO(bookName.getCASHBOOK_NAME());
										cashbook.setCASH_VOUCHER_NO(bookVoucherSequenceCounterService.getCashVoucherNumberAndUpdateNext(cashbook.getCASH_BOOK_ID(),companyId));
										cashbook.setCASH_REFERENCENO("<a href='/showVehicle?vid="+trip.getVid()+"'>" +vehicle.getVehicle_registration()+"</a><br><a href='/showTripSheet.in?tripSheetID=" + trip.getTripSheetID() + "'> TS - " + trip.getTripSheetNumber()+ " </a>");
									 	Driver driver = driverService.getDriver(object.getInt("driverId"),userDetails);
									 	cashbook.setCASH_PAID_RECEIVED(driver.getDriver_firstname() +" "+driver.getDriver_Lastname());
									 	cashbook.setCASH_PAID_BY(userDetails.getEmail());
									 	cashbook.setCASH_NATURE_PAYMENT_ID(tripExpense.getExpenseId());
									 	
									 	TripExpense expense = tripExpenseService.getTripExpense(tripExpense.getExpenseId(), userDetails.getCompany_id());
									 	cashbook.setCASH_NATURE_PAYMENT(expense.getExpenseName());
									 	
									 	cashbookObj = CashBookService.saveCashBookMissingEntry(cashbook,file, saveExepenseInCashBook);
									
								   }
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//cashbook ends
					 	}
					}
				}
				object.put("updateExpense", true);
				
				Object[] expenseIds = getDriverAdvanceAndBataExpenseId(companyId);
				
				if (expenseIds != null) {
					for(ValueObject arryObj : expenseArry) {
						if (expenseIds[0] != null && (Integer) expenseIds[0] == object.getInt("paymentTypeExpenseSettle")) {
						}
						
						if (expenseIds[1] != null && (Integer) expenseIds[1] == object.getInt("paymentTypeExpenseSettle")) {
							Update_TripSheet_FIRST_Driver_BATA(arryObj.getDouble("expenseAmount"), tripsheetId, companyId);
						}
						
						if (expenseIds[2] != null && (Integer) expenseIds[2] == object.getInt("paymentTypeExpenseSettle")) {
						}
						
						if (expenseIds[3] != null && (Integer) expenseIds[3] == object.getInt("paymentTypeExpenseSettle")) {
							Update_TripSheet_SECOND_Driver_BATA(arryObj.getDouble("expenseAmount"), tripsheetId, companyId);
						}
						
						if (expenseIds[4] != null && (Integer) expenseIds[4] == object.getInt("paymentTypeExpenseSettle")) {
						}
						
						if (expenseIds[5] != null && (Integer) expenseIds[5] == object.getInt("paymentTypeExpenseSettle")) {
							Update_TripSheet_CLEANER_BATA(arryObj.getDouble("expenseAmount"), tripsheetId, companyId);
						}
				    }
				 }
				
				if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
					ValueObject		expenseObject = null;
					for(TripSheetExpense TripExpense : TripExpenseList) {
						expenseObject	= new ValueObject();
	
						expenseObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, tripsheetId);
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						expenseObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						expenseObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, trip.getVid());
						expenseObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, TripExpense.getExpenseId());
						expenseObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, TripExpense.getTripExpenseID());
	
						VehicleProfitAndLossTxnChecker profitAndLossExpenseTxnChecker = txnCheckerBL.getVehicleProfitAndLossTxnChecker(expenseObject);
						vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);
	
						expenseTxnCheckerHashMap.put(profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossExpenseTxnChecker);
						tripSheetExpenseHM.put(TripExpense.getTripExpenseID(), TripExpense);
					}	
				}
				
				UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripsheetId, companyId);
				
				finalBalance = object.getDouble("amountBalance");
				
				for(ValueObject arryObj : expenseArry) {
					finalBalance = finalBalance - arryObj.getDouble("expenseAmount");
					if(finalBalance == 0) {
						dueStatus = TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PAID;
					} else {
						dueStatus = TripsheetDueAmountPaymentTypeConstant.PAYMENT_MODE_PARTIALLY_PAID;
					}
					entityManager.createQuery(
							"Update TripsheetDueAmount AS TD "
									+ " SET TD.balanceAmount = TD.balanceAmount - "+arryObj.getDouble("expenseAmount")+", "
									+ " TD.dueStatus = "+dueStatus+" "
									+ " WHERE TD.tripsheetDueAmountId = "+object.getLong("tripsheetDueAmountId")+" ")
					.executeUpdate();
				}
				
				if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
					
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
			} finally {
			}
		}
		
		@Override
		public ValueObject getDueAmountPaymentReport(ValueObject valueObject) throws Exception {
			String										dateRange					= null;
			int											driverId					= 0;
			CustomUserDetails							userDetails					= null;
			List<TripsheetDueAmountDto> 				dueAmountPaymentList		= null;
			int billselectionId                                                     = 0;
			try {
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				driverId		= valueObject.getInt("driverId", 0);
				dateRange		= valueObject.getString("dueAmountRange");
				billselectionId         = valueObject.getInt("billselectionId",0);
				
				if(dateRange != null) {
					String dateRangeFrom = "", dateRangeTo = "", billselection = "";
					String[] From_TO_DateRange = null;

					From_TO_DateRange = dateRange.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

					String driverName = "", Date = "";
					
					if(driverId > 0)
					{
						driverName = " AND TD.driver_id = "+ driverId +" ";
					}
					
					Date =  " AND TP.paidDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;

					if(billselectionId > 0) {
						billselection = " AND TD.billSelectionId = "+ billselectionId +" ";
					}
					String query = " "+driverName+"  "+Date+" " +billselection+ " ";
					
					dueAmountPaymentList = TripSheetDaoImpl.getDueAmountPaymentReportList(query, userDetails.getCompany_id());
					if(dueAmountPaymentList != null) {
						valueObject.put("dueAmountPaymentList", dueAmountPaymentList);
					}
				}
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			}finally {
				dueAmountPaymentList	= null;
				userDetails				= null;
				dateRange				= null;
			}
		}
		
		@Override
		@Transactional(readOnly = false)
		public ValueObject updateExpenseRemark(ValueObject valueObject) throws Exception {
			try {
				entityManager.createQuery(
						"Update TripSheetExpense AS T Set T.remark= '"+valueObject.getString("remark")+"' WHERE T.tripExpenseID = " + valueObject.getLong("tripSheetExpenseId") + " ")
				.executeUpdate();
				valueObject.put("success", true);
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<TripSheetDto> getTripSheetForDate(Integer vid, String fromDate, String toDate) throws Exception {
			
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, V.vehicleGroupId,"
							+ " TS.tripOpenDate, TS.closetripDate "
							+ " from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
							+ " WHERE (TS.closetripDate between '"+ fromDate +"' AND '"+toDate+"' OR TS.tripOpenDate between '"+ fromDate +"' AND '"+toDate+"' ) "
							+ " AND TS.vid = " +vid+ " AND TS.markForDelete = 0 ",Object[].class);
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
					select.setVehicleGroupId((long) vehicle[4]);
					select.setTripOpenDate(dateFormat.format((java.util.Date) vehicle[5]));
					select.setClosetripDate(dateFormat.format((java.util.Date) vehicle[6]));
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		}
		
		@Override
		public ValueObject getTripSheetDataForRefreshmentAdd(ValueObject	valueObject) throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, V.vehicleGroupId,"
									+ " TS.tripOpenDate, TS.closetripDate, D.driver_firstname , T.routeName, TS.tripFristDriverID, TS.routeID "
									+ " from TripSheet TS " 
									+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
									+ " LEFT JOIN Driver D ON D.driver_id = TS.tripFristDriverID"
									+ " LEFT JOIN TripRoute T ON T.routeID = TS.routeID"
									+ " WHERE TS.tripSheetID = "+valueObject.getLong("tripSheetId",0)+" AND TS.markForDelete = 0 ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}

				TripSheetDto select;
				if (vehicle != null) {
					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setVehicle_registration((String) vehicle[3]);
					select.setVehicleGroupId((long) vehicle[4]);
					select.setTripOpenDate(dateFormat.format((java.util.Date) vehicle[5]));
					select.setClosetripDate(dateFormat.format((java.util.Date) vehicle[6]));
					select.setTripFristDriverName((String) vehicle[7]);
					select.setRouteName((String) vehicle[8]);
					select.setTripFristDriverID((int) vehicle[9]);
					select.setRouteID((Integer) vehicle[10]);
					
					
				} else {
					return  valueObject;
				}
				valueObject.put("tripSheet", select);
				return valueObject ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public TripSheetDto getLastClosedTripSheetByDateTime(Integer vid, String fromDate) throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate, TS.tripClosingKM,"
							+ " D.driver_empnumber, D.driver_firstname, D.driver_id, D2.driver_empnumber, D2.driver_firstname, D2.driver_id,"
							+ " D3.driver_empnumber, D3.driver_firstname, D3.driver_id, "
							+ " TS.tripStartDiesel, TS.tripEndDiesel, TS.routeID, TR.routeName, TS.closedByTime ,D.driver_Lastname ,D.driver_fathername,"
							+ " D2.driver_Lastname , D2.driver_fathername , D3.driver_Lastname ,D3.driver_fathername "
							+ " from TripSheet TS "
							+ " LEFT JOIN Driver D ON D.driver_id   = TS.tripFristDriverID" 
							+ " LEFT JOIN Driver D2 ON D2.driver_id  = TS.tripSecDriverID" 
							+ " LEFT JOIN Driver D3 ON D3.driver_id  = TS.tripCleanerID"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID"
							+ " WHERE TS.vid = "+vid+" and TS.closedByTime <= '"+fromDate+"' AND TS.tripStutesId >= "+TripSheetStatus.TRIP_STATUS_CLOSED+" AND TS.markForDelete = 0 order by TS.closedByTime desc ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					// e.driver_empnumber+" "+e.driver_firstname+"_"+e.driver_Lastname
				}

				TripSheetDto select;
				if (vehicle != null) {
					
					String driverName = "";
					select = new TripSheetDto();
					
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setTripOpenDate(dateFormat.format((java.util.Date) vehicle[3]));
					select.setClosetripDate(dateFormat.format((java.util.Date) vehicle[4]));
					select.setTripClosingKM((Integer) vehicle[5]);
					 
					driverName += (String)vehicle[6];
					
					if(vehicle[7] != null) {
						driverName += " "+ (String)vehicle[7];
					}
					
					select.setTripFristDriverName(driverName);
					select.setTripFristDriverID((int) vehicle[8]);
					driverName = "";
					
					if(vehicle[11] != null) {
						if(vehicle[9] != null) {
							driverName += (String)vehicle[9];
						}
						if(vehicle[10] != null) {
							driverName += " "+ (String)vehicle[10];
						}
						
						select.setTripSecDriverName(driverName);
						select.setTripSecDriverID((int) vehicle[11]);
						
						driverName = "";
					}
					
					if(vehicle[14] != null) {
						
						if(vehicle[12] != null) {
							driverName += (String)vehicle[12];
						}
						if(vehicle[13] != null) {
							driverName += " "+ (String)vehicle[13];
						}
						
						select.setTripCleanerName(driverName); 
						select.setTripCleanerID((int) vehicle[14]);
						driverName = "";
					}
					
					if(vehicle[15] != null)
						select.setTripStartDiesel((Double) vehicle[15]);
					if(vehicle[16] != null)
						select.setTripEndDiesel((Double) vehicle[16]);
					
					select.setRouteID((Integer) vehicle[17]);
					select.setRouteName((String) vehicle[18]);
					
					if(vehicle[19] != null) {
						String str = dateTimeFormat2.format((java.util.Date) vehicle[19]);
						java.util.Date dt = dateTimeFormat2.parse(str);
						select.setClosedTripTime(dateTimeFormat.format(dt));
					}
					if(vehicle[20] != null)
					select.setTripFristDriverName(select.getTripFristDriverName() +" "+vehicle[20]);
					
					if(vehicle[21] != null && !((String)vehicle[21]).trim().equals(""))
						select.setTripFristDriverName(select.getTripFristDriverName() +" - "+vehicle[21]);
					
					if(vehicle[22] != null)
						select.setTripSecDriverName(select.getTripSecDriverName() +"  "+vehicle[22]);
					
					if(vehicle[23] != null && !((String)vehicle[23]).trim().equals(""))
						select.setTripSecDriverName(select.getTripSecDriverName() +" - "+vehicle[23]);
					
				} else {
					return null;
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public TripSheetDto getTripSheetLimitedDetails(Long TripSheet_id) throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid"
								+ " from TripSheet TS "
								+ " WHERE TS.tripSheetID = "+TripSheet_id+" AND TS.markForDelete = 0 ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					// e.driver_empnumber+" "+e.driver_firstname+"_"+e.driver_Lastname
				}

				TripSheetDto select;
				if (vehicle != null) {
					//String driverName = "";
					select = new TripSheetDto();
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					
				} else {
					return null;
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public ValueObject getLastNextTripSheetDetails(ValueObject valueObject) throws Exception {
			String						dispatchDate			= null;
			String						dispatchedToDate		= null;
			String						dispatchTime			= null;
			TripSheetDto				tripSheetDto			= null;
			Timestamp					fromDate				= null;
			Timestamp					toDate					= null;
			TripSheetDto				nextTripSheet			= null;
			TripSheetDto				inBetweenTripSheet		= null;
			boolean						validateOnTripClose		= false;
			String						betweenQuery			= "";
			try {
				dispatchDate			= valueObject.getString("dispatchedByTime");
				dispatchedToDate 		= valueObject.getString("dispatchedToByTime");
				dispatchTime			= valueObject.getString("dispatchTime","");
				validateOnTripClose		= valueObject.getBoolean("validateOnTripClose",false);
				
				fromDate				= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchDate, dispatchTime).getTime());
				toDate					= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchedToDate, dispatchTime).getTime());
				
				if(validateOnTripClose == true) {
					betweenQuery = "AND '"+DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' between TS.dispatchedByTime  AND TS.closedByTime  ";
				}else {
					betweenQuery = "AND (('"+DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' between TS.dispatchedByTime  AND TS.closedByTime) OR ('"+DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' between TS.dispatchedByTime  AND TS.closedByTime) OR ('"+DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' <= TS.dispatchedByTime AND '"+DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' >= TS.closedByTime )) ";
				}
				inBetweenTripSheet		= getInBetweentimeTripSheetByDateTime(valueObject.getInt("vehicleId",0), betweenQuery);
				
				
				if(inBetweenTripSheet != null ) {
					valueObject.put("inBetweenTripSheet", inBetweenTripSheet);
					return valueObject;
				}
				
				if(validateOnTripClose == true) {
					tripSheetDto		= getLastClosedTripSheetByDateTime(valueObject.getInt("vehicleId",0), DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
					nextTripSheet	  	= getNextClosedTripSheetByDateTime(valueObject.getInt("vehicleId",0), DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
				}else {
					tripSheetDto		= getLastClosedTripSheetByDateTime(valueObject.getInt("vehicleId",0), DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
					nextTripSheet	  	= getNextClosedTripSheetByDateTime(valueObject.getInt("vehicleId",0), DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
				}
				
				if(tripSheetDto != null) {
					valueObject.put("tripSheet", tripSheetDto);
				}
				
				if(nextTripSheet != null) {
					valueObject.put("nextTripSheet", nextTripSheet);
				}
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public ValueObject getLastNextTripSheetDetailsForEdit(ValueObject valueObject) throws Exception {
			String						dispatchDate		= null;
			String						dispatchedToDate	= null;
			String						dispatchTime		= null;
			String						tripEndTime			= null;
			TripSheetDto				tripSheetDto		= null;
			Timestamp					fromDate			= null;
			Timestamp					toDate				= null;
			TripSheetDto				nextTripSheet		= null;
			TripSheetDto				inBetweenTripSheet	= null;
			String						betweenQuery		= "";
			try {
				dispatchDate		= valueObject.getString("dispatchedByTime");
				dispatchedToDate 	= valueObject.getString("dispatchedToByTime");
				tripEndTime			= valueObject.getString("dispatchTime");
				dispatchTime		= valueObject.getString("realDispatchTime",valueObject.getString("dispatchTime"));
				fromDate			= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchDate, dispatchTime).getTime());
				toDate				= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchedToDate, tripEndTime).getTime());
				
				betweenQuery = "AND (('"+DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' between TS.dispatchedByTime  AND TS.closedByTime) OR ('"+DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' between TS.dispatchedByTime  AND TS.closedByTime) OR ('"+DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' <= TS.dispatchedByTime AND '"+DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' >= TS.closedByTime )) ";
				
				if(valueObject.getLong("tripSheetId",0)>0)
					betweenQuery+=" AND TS.tripSheetID <> "+valueObject.getLong("tripSheetId",0)+" ";
				inBetweenTripSheet		= getInBetweentimeTripSheetByDateTime(valueObject.getInt("vehicleId",0), betweenQuery);
				
				if(inBetweenTripSheet != null ) {
					valueObject.put("inBetweenTripSheet", inBetweenTripSheet);
				}
				
				if(valueObject.getLong("tripSheetId",0) <= 0) {
					tripSheetDto		= getLastClosedTripSheetByDateTime(valueObject.getInt("vehicleId",0), DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
					nextTripSheet	  	= getNextClosedTripSheetByDateTime(valueObject.getInt("vehicleId",0), DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
				}else {
					tripSheetDto		= getLastClosedTripSheetByDateTimeById(valueObject.getInt("vehicleId",0), DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), valueObject.getLong("tripSheetId"));
					nextTripSheet	  	= getNextClosedTripSheetByDateTime(valueObject.getInt("vehicleId",0), DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS), valueObject.getLong("tripSheetId"));
				}
				
				if(tripSheetDto != null) {
					valueObject.put("tripSheet", tripSheetDto);
				}
				
				if(nextTripSheet != null) {
					valueObject.put("nextTripSheet", nextTripSheet);
				}
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public TripSheetDto getNextClosedTripSheetByDateTime(Integer vid, String fromDate) throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate, TS.tripClosingKM,"
							+ " TS.tripOpeningKM, TS.tripStartDiesel, TS.tripEndDiesel, TS.dispatchedByTime, TS.closedByTime "
							+ " from TripSheet TS "
							+ " WHERE TS.vid = "+vid+" and TS.dispatchedByTime >= '"+fromDate+"'  AND TS.markForDelete = 0 ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					// Do nothing
				}

				TripSheetDto select;
				if (vehicle != null) {
					
					select = new TripSheetDto();
					
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setTripOpenDate(dateFormat.format((java.util.Date) vehicle[3]));
					select.setClosetripDate(dateFormat.format((java.util.Date) vehicle[4]));
					select.setTripClosingKM((Integer) vehicle[5]);
					select.setTripOpeningKM((Integer) vehicle[6]);
					
					if(vehicle[7] != null)
						select.setTripStartDiesel((Double) vehicle[7]);
					if(vehicle[8] != null)
						select.setTripEndDiesel((Double) vehicle[8]);
					if(vehicle[9] != null)
						select.setDispatchedByTime(dateTimeFormat.format((Timestamp) vehicle[9]));
					if(vehicle[10] != null)
						select.setClosedByTime(dateTimeFormat.format((Timestamp) vehicle[10]));
					
					
				} else {
					return null;
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public Long getTripSheetIdByNumber(Long tripsheetNumber, Integer companyId, Long userId) throws Exception {
			Query query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				query = entityManager.createQuery(
						"SELECT T.tripSheetID "
								+ " FROM TripSheet T "
								+ " WHERE T.tripSheetNumber = "+tripsheetNumber+" AND T.companyId = "+companyId+" AND T.markForDelete = 0");
			} else {
				query = entityManager.createQuery(
						"SELECT T.tripSheetID "
								+ " FROM TripSheet T "
								+ " INNER JOIN Vehicle V ON V.vid = T.vid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userId+" "
								+ " WHERE T.tripSheetNumber = "+tripsheetNumber+" AND T.companyId = "+companyId+" AND T.markForDelete = 0");

			}

			try {
				query.setMaxResults(1);
				return (Long) query.getSingleResult();
			} catch (NoResultException nre) {
				return null;
			}
		}
		
		@Override
		public Long getAllRouteCount(String startDate, String toDate, Integer vid) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					" SELECT COUNT(DISTINCT T.routeID) "
							+ " From TripSheet as T "
							+ " WHERE T.closetripDate between '"+ startDate +"' AND '"+ toDate + "' AND T.vid = " +vid+" "						
							+ " AND T.markForDelete = 0 ");
			
			Long count = (long) 0;
			try {
				
				if((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
				}
				
			} catch (NoResultException nre) {
				}

			return count;
		}
		
		@Override
		@Transactional
		public void deleteDueAmountByTripSheetId(Long tripsheetId) throws Exception {
			
			tripsheetDueAmountRepository.deleteDueAmountByTripSheetId(tripsheetId);
		}
		
		@Override
		@Transactional
		public void superUserdeleteTripSheet(Long tripSheetID, Long userId, String remark) throws Exception {
			
			TripSheetDao.superUserdeleteTripSheet(tripSheetID, userId, remark);
		}
		
		@Override
		public List<TripSheetDto> searchTripSheetNumberList(Integer companyId, String term) throws Exception {
			try {
				List<TripSheetDto> Dtos = null;
				if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
				TypedQuery<Object[]> queryt = null;
					queryt = entityManager.createQuery("SELECT v.tripSheetID, v.tripSheetNumber, "
							+ " v.vid, VH.vehicle_registration, D.driver_firstname, D.driver_Lastname, v.tripFristDriverID  "
							+ " FROM TripSheet AS v "
							+ " INNER JOIN Vehicle VH ON VH.vid = v.vid "
							+ " INNER JOIN Driver D ON D.driver_id = v.tripFristDriverID"
							+ " where  lower(v.tripSheetNumber) Like ('%" + term
							+ "%') and v.companyId = " + companyId + " and v.markForDelete = 0",
							Object[].class);
				List<Object[]> results = queryt.getResultList();

				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<TripSheetDto>();
					TripSheetDto dropdown = null;
					for (Object[] result : results) {
						dropdown = new TripSheetDto();

						dropdown.setTripSheetID((Long) result[0]);
						dropdown.setTripSheetNumberStr("TS-"+(Long) result[1]);
						dropdown.setVid((Integer) result[2]);
						dropdown.setVehicle_registration((String) result[3]);
						dropdown.setTripFristDriverName((String) result[4]);
						if(result[5] != null)
							dropdown.setTripFristDriverName(dropdown.getTripFristDriverName() + (String) result[5]);
						dropdown.setTripFristDriverID((Integer) result[6]);

						Dtos.add(dropdown);
					}
				}
				}
				return Dtos;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		@Transactional
		public void deleteFuelExpenseByTripId(Long tripSheetId) throws Exception {
			try {
				entityManager.createQuery(
						"Update TripSheetExpense SET markForDelete = 1  WHERE (fuel_id IS NOT NULL OR fuel_id = 0) AND tripsheet.tripSheetID = "+tripSheetId+" ")
				.executeUpdate();
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject updateTallyCompanyToTripSheet(ValueObject valueObject) throws Exception {
			try {
				TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(valueObject.getLong("tripSheetId"));
				TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
				
				entityManager.createQuery(
						"Update TripSheet SET tallyCompanyId = '"+valueObject.getLong("tallyCompanyId",0)+"'  WHERE tripSheetID = " + valueObject.getLong("tripSheetId") + "")
				.executeUpdate();
				
				tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public TripSheetDto getInBetweentimeTripSheetByDateTime(Integer vid, String query) throws Exception {
			try {
				
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate, TS.tripClosingKM,"
							+ " TS.tripOpeningKM, TS.tripStartDiesel, TS.tripEndDiesel, TS.dispatchedByTime, TS.closedByTime "
							+ " from TripSheet TS "
							+ " WHERE TS.vid = "+vid+"  "+query+" AND TS.markForDelete = 0 ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					// Do nothing
				}

				TripSheetDto select;
				if (vehicle != null) {
					
					select = new TripSheetDto();
					
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setTripOpenDate(dateFormat.format((java.util.Date) vehicle[3]));
					select.setClosetripDate(dateFormat.format((java.util.Date) vehicle[4]));
					select.setTripClosingKM((Integer) vehicle[5]);
					select.setTripOpeningKM((Integer) vehicle[6]);
					
					if(vehicle[7] != null)
						select.setTripStartDiesel((Double) vehicle[7]);
					if(vehicle[8] != null)
						select.setTripEndDiesel((Double) vehicle[8]);
					if(vehicle[9] != null)
						select.setDispatchedByTime(dateTimeFormat.format((Timestamp) vehicle[9]));
					if(vehicle[10] != null)
						select.setClosedByTime(dateTimeFormat.format((Timestamp) vehicle[10]));
					
					
				} else {
					return null;
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		
		@Override
		public TripSheetDto getLastClosedTripSheetByDateTimeById(Integer vid, String fromDate, Long tripSheetId)
				throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate, TS.tripClosingKM,"
							+ " D.driver_empnumber, D.driver_firstname, D.driver_id, D2.driver_empnumber, D2.driver_firstname, D2.driver_id,"
							+ " D3.driver_empnumber, D3.driver_firstname, D3.driver_id, "
							+ " TS.tripStartDiesel, TS.tripEndDiesel, TS.routeID, TR.routeName, TS.closedByTime "
							+ " from TripSheet TS "
							+ " LEFT JOIN Driver D ON D.driver_id   = TS.tripFristDriverID" 
							+ " LEFT JOIN Driver D2 ON D2.driver_id  = TS.tripSecDriverID" 
							+ " LEFT JOIN Driver D3 ON D3.driver_id  = TS.tripCleanerID"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID"
							+ " WHERE TS.vid = "+vid+" and TS.closedByTime <= '"+fromDate+"' AND TS.tripStutesId >= "+TripSheetStatus.TRIP_STATUS_CLOSED+" AND TS.tripSheetID <> "+tripSheetId+" AND TS.markForDelete = 0 order by TS.closedByTime desc ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					// e.driver_empnumber+" "+e.driver_firstname+"_"+e.driver_Lastname
				}

				TripSheetDto select;
				if (vehicle != null) {
					
					String driverName = "";
					select = new TripSheetDto();
					
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setTripOpenDate(dateFormat.format((java.util.Date) vehicle[3]));
					select.setClosetripDate(dateFormat.format((java.util.Date) vehicle[4]));
					select.setTripClosingKM((Integer) vehicle[5]);
					 
					driverName += (String)vehicle[6];
					
					if(vehicle[7] != null) {
						driverName += " "+ (String)vehicle[7];
					}
					
					select.setTripFristDriverName(driverName);
					select.setTripFristDriverID((int) vehicle[8]);
					driverName = "";
					
					if(vehicle[11] != null) {
						if(vehicle[9] != null) {
							driverName += (String)vehicle[9];
						}
						if(vehicle[10] != null) {
							driverName += " "+ (String)vehicle[10];
						}
						
						select.setTripSecDriverName(driverName);
						select.setTripSecDriverID((int) vehicle[11]);
						
						driverName = "";
					}
					
					if(vehicle[14] != null) {
						
						if(vehicle[12] != null) {
							driverName += (String)vehicle[12];
						}
						if(vehicle[13] != null) {
							driverName += " "+ (String)vehicle[13];
						}
						
						select.setTripCleanerName(driverName); 
						select.setTripCleanerID((int) vehicle[14]);
						driverName = "";
					}
					
					if(vehicle[15] != null)
						select.setTripStartDiesel((Double) vehicle[15]);
					if(vehicle[16] != null)
						select.setTripEndDiesel((Double) vehicle[16]);
					
					select.setRouteID((Integer) vehicle[17]);
					select.setRouteName((String) vehicle[18]);
					
					if(vehicle[19] != null) {
						String str = dateTimeFormat2.format((java.util.Date) vehicle[19]);
						if(!str.trim().equalsIgnoreCase("")) {
							try {
								java.util.Date dt = dateTimeFormat2.parse(str);
								select.setClosedTripTime(dateTimeFormat.format(dt));
							} catch (Exception e) {
								
							}
							
						}
					}
					
					
					
				} else {
					return null;
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public TripSheetDto getNextClosedTripSheetByDateTime(Integer vid, String fromDate, Long tripSheetId)
				throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate, TS.tripClosingKM,"
							+ " TS.tripOpeningKM, TS.tripStartDiesel, TS.tripEndDiesel, TS.dispatchedByTime, TS.closedByTime "
							+ " from TripSheet TS "
							+ " WHERE TS.vid = "+vid+" and TS.dispatchedByTime >= '"+fromDate+"' AND TS.tripSheetID <> "+tripSheetId+"  AND TS.markForDelete = 0 ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					// Do nothing
				}

				TripSheetDto select;
				if (vehicle != null) {
					
					select = new TripSheetDto();
					
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setVid((Integer) vehicle[2]);
					select.setTripOpenDate(dateFormat.format((java.util.Date) vehicle[3]));
					select.setClosetripDate(dateFormat.format((java.util.Date) vehicle[4]));
					select.setTripClosingKM((Integer) vehicle[5]);
					select.setTripOpeningKM((Integer) vehicle[6]);
					
					if(vehicle[7] != null)
						select.setTripStartDiesel((Double) vehicle[7]);
					if(vehicle[8] != null)
						select.setTripEndDiesel((Double) vehicle[8]);
					if(vehicle[9] != null)
						select.setDispatchedByTime(dateTimeFormat.format((Timestamp) vehicle[9]));
					if(vehicle[10] != null)
						select.setClosedByTime(dateTimeFormat.format((Timestamp) vehicle[10]));
					
					
				} else {
					return null;
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public TripSheet getTripSheetCreatedBetweenDates(String startDate, String endDate,Integer companyId) throws Exception {
				TypedQuery<TripSheet> query = entityManager.createQuery(
					" SELECT T  "
							+ " From TripSheet as T "
							+ " WHERE T.created between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId+" "						
							+ " AND T.markForDelete = 0 ",TripSheet.class);
			
				TripSheet	tripSheet = null;
				try {
						query.setMaxResults(1);
						tripSheet = query.getSingleResult();
					
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}

				return tripSheet;
		}
		@Override
		@Transactional
		public ValueObject updateTripSheetUsageKM(ValueObject valueObject) throws Exception {
			TripSheet		tripSheet		= null;
			Double			gpsUssageKM     = null;
			
			try {
				tripSheet		= getTripSheet(valueObject.getLong("tripSheetID"));
				gpsUssageKM = vehicleGPSDetailsService.getGPSRunKMBetweenTwoDates(tripSheet.getVid(), tripSheet.getDispatchedByTime()+"", tripSheet.getClosedByTime()+"", tripSheet.getCompanyId());
					valueObject.put("tripUsaseKm", gpsUssageKM);
					
					if(gpsUssageKM != null && gpsUssageKM > 0) {
						updateGPSUsageKM(gpsUssageKM, valueObject.getLong("tripSheetID"), tripSheet.getVid(), tripSheet.getCompanyId());
						valueObject.put("updated", true);
						valueObject.put("notFound", true);
					
						TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(tripSheet);
						tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
						
						return valueObject;
					}
					
					valueObject.put("notFound", true);
			return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				tripSheet		= null;
				gpsUssageKM     = null;
			}
		}
		
		private void updateGPSUsageKM(Double gpsUssageKM, Long tripSheetId, Integer vid, Integer companyId) {

			entityManager.createQuery(
					" UPDATE TripSheet SET gpsTripUsageKM = "+gpsUssageKM+" "
							+ " WHERE tripSheetID = "+tripSheetId+" AND vid = "+vid+" AND companyId = "+companyId+" AND markForDelete = 0")
			.executeUpdate();
			
		}
		
		@Override
		@Transactional
		public void getVehicleGpsDetailAndUpdateTripSheetUsageKM() throws Exception {
			ValueObject 		object 					= null;
			CustomUserDetails 	userDetails 			=  null;
			String 				yesterdayDate			= "";
			List<TripSheetDto>	tripSheetList			= null;
			String []			yesterdaySplitDate		= null;
			String 				year					= "";
			String 				month					= "";
			String 				date					= "";
			String 				yesterdayNewDate		= "";
			try {
				userDetails 				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				yesterdayDate 				= DateTimeUtility.getYesterdayDate();
				object 						= new ValueObject();
				yesterdaySplitDate 			= yesterdayDate.split("-");
				year						= yesterdaySplitDate[2];
				month						= yesterdaySplitDate[1];
				date						= yesterdaySplitDate[0];
				yesterdayNewDate			= year+"-"+month+"-"+date;
				
				tripSheetList 		= getTotalGpsTripSheetClosedDetailsBetweenDates(yesterdayNewDate+" "+DateTimeUtility.DAY_START_TIME,yesterdayNewDate+" "+DateTimeUtility.DAY_END_TIME,userDetails.getCompany_id());
				if(tripSheetList != null && !tripSheetList.isEmpty()) {
					for(TripSheetDto dto : tripSheetList) {
						object.put("tripSheetID", dto.getTripSheetID());
						updateTripSheetUsageKM(object);
					}
						
				}
				
				
			} catch (Exception e) {
				throw e;
			}finally {
			}
		}
		
		
		@Override
   public List<TripSheetDto> getTotalGpsTripSheetClosedDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
			TypedQuery<Object[]> query = null;
			
			
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid,"
							+ " TS.tripOpenDate, TS.closetripDate, TS.tripStutesId "
							+ " from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid AND V.vehicleGPSId IS NOT NULL AND V.vehicleGPSId > 0"
							+ " where TS.closedByTime between '"+startDate+"' AND '"+endDate+"' AND TS.companyId = " + companyId + " "
							+ " AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_CLOSED+" AND TS.markForDelete = 0 ORDER BY TS.tripSheetID DESC ",
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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[3]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[4]));
					select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[5]));
					
					Dtos.add(select);
				}
			}
			
			return Dtos;
			
		}	
		
		@Override
		public TripSheetDto getLastTripSheetLimitedData(Integer vid, String fromDate, Long tripSheetId) throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.closedByTime, TS.dispatchedByTime, TS.closetripDate "
							+ " from TripSheet TS "
							+ " WHERE TS.vid = "+vid+" and TS.closedByTime <= '"+fromDate+"' AND TS.tripSheetID <> "+tripSheetId+" AND TS.markForDelete = 0 order by TS.closedByTime desc ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					
				}

				TripSheetDto select;
				if (vehicle != null) {
					
					select = new TripSheetDto();
					
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setClosedByTimeOn((java.util.Date) vehicle[2]);
					select.setDispatchedByTimeOn((java.util.Date) vehicle[3]);
					select.setClosetripDateOn((java.util.Date) vehicle[4]);
					select.setClosedByTime(dateTimeFormat.format((java.util.Date) vehicle[2]));
					select.setDispatchedByTime(dateTimeFormat.format((java.util.Date) vehicle[3]));
				} else {
					return null;
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject updateTripRoutePoint(ValueObject object) throws Exception {

			entityManager.createQuery(
					" UPDATE TripSheet SET tripFristDriverRoutePoint = "+object.getDouble("driver1RoutePoint",0)+", tripSecDriverRoutePoint = "+object.getDouble("driver2RoutePoint",0)+", tripCleanerRoutePoint ="+object.getDouble("cleanerRoutePoint",0)+" "
							+ " WHERE tripSheetID = "+object.getLong("tripSheetId",0)+" ")
			.executeUpdate();
			
			return object;
			
		}
		
		@Override
		public ValueObject getPreviousDueAmountData(ValueObject valueObject) throws Exception {
			int			vid				= 0;
			int 		compId			= 0;
			try {
				vid 	= valueObject.getInt("vehId");
				compId  = valueObject.getInt("companyId");
				
				Query queryt = null;
				queryt = entityManager.createQuery(
					" SELECT SUM(TDA.balanceAmount) " 
						+ " From TripsheetDueAmount as TDA "
						+ " WHERE TDA.vid = "+vid+" AND TDA.companyId = "+compId+" "
						+ " AND TDA.markForDelete = 0 ORDER BY TDA.tripsheetDueAmountId DESC ");

				Double count = (double) 0;
				try {

					if ((Double) queryt.getSingleResult() != null) {
						count = (Double) queryt.getSingleResult();
					}

				} catch (NoResultException nre) {
				}
				
				valueObject.put("previousDueAmount", count);
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			} finally {
				valueObject = null;
			}
		}
		
		@Override
		public ValueObject getPreviousDueAmountDataList(ValueObject valueObject) throws Exception {
			int											vehicleId				= 0;
			CustomUserDetails							userDetails				= null;
			List<TripsheetDueAmountDto> 				dueAmountList			= null;
			try {
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				vehicleId		= valueObject.getInt("vehId", 0);
					
				String vehicleName = "";
				
				if(vehicleId > 0)
				{
					vehicleName = " AND TD.vid = "+vehicleId+" ";
				}
				
				String query = " "+vehicleName+"  ";
				
				dueAmountList = TripSheetDaoImpl.getDueAmountReportList(query, userDetails.getCompany_id());
				if(dueAmountList != null) {
					valueObject.put("dueAmountList", dueAmountList);
				}
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			}finally {
				dueAmountList		= null;
				userDetails			= null;
			}
		}
		
		
		@Override
		public TripSheetDto getSavedTripSheetByVid(Integer vid) throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber,TS.tripClosingKM"
							+ " from TripSheet TS "
							+ " WHERE TS.vid = "+vid+" AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_SAVED+" AND TS.markForDelete = 0 ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					
				}

				TripSheetDto select;
				if (vehicle != null) {
					
					select = new TripSheetDto();
					
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					
				} else {
					return null;
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public TripSheetDto getInBetweenTripSheetDetails(Integer vid, String dispatchByTime) throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber"
							+ " from TripSheet TS "
							+ " WHERE TS.vid = "+vid+" AND '"+dispatchByTime+"' between dispatchedByTime and closedByTime and markForDelete = 0  AND TS.markForDelete = 0 ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					
				}

				TripSheetDto select;
				if (vehicle != null) {
					
					select = new TripSheetDto();
					
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					
				} else {
					return null;
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<TripSheetDto> getNextTripSheetsByVidDate(Integer vid, String dispatchByTime) throws Exception {
			TypedQuery<Object[]> query = null;
			
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.tripOpeningKM, TS.tripClosingKM, TS.tripUsageKM, TS.tripStutesId "
							+ " from TripSheet TS " 
							+ " where TS.dispatchedByTime > '"+dispatchByTime+"' AND TS.vid = "+vid+" "
							+ " AND TS.tripStutesId  > "+TripSheetStatus.TRIP_STATUS_DISPATCHED+" AND TS.markForDelete = 0 ORDER BY TS.dispatchedByTime ASC ",
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
					select.setTripOpeningKM((Integer) vehicle[2]);
					select.setTripClosingKM((Integer) vehicle[3]);
					select.setTripUsageKM((Integer) vehicle[4]);
					select.setTripStutesId((short) vehicle[5]);
					
					Dtos.add(select);
				}
			}
			
			return Dtos;
			
		}
		
		@Override
		@Transactional
		public void updateOpeningClosingKM(Long tripSheetId, Integer openingKM, Integer closingKM) throws Exception {
			entityManager.createQuery(
					" UPDATE TripSheet SET tripOpeningKM = "+openingKM+", tripClosingKM = "+closingKM+" "
							+ " WHERE tripSheetID = "+tripSheetId+" ")
			.executeUpdate();
			
		}
		
		@Override
		public TripSheetDto getLimitedTripSheetDetails(Long tripSheetId) throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.tripStutesId, TS.driverBalance "
							+ " from TripSheet TS "
							+ " WHERE TS.tripSheetID = "+tripSheetId+"  AND TS.markForDelete = 0 ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					
				}

				TripSheetDto select;
				if (vehicle != null) {
					select = new TripSheetDto();
					
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
					select.setTripStutesId((short) vehicle[2]);
					select.setDriverBalance((Double) vehicle[3]);
				} else {
					return null;
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public  XSSFSheet getHssfSheet(DataValidationHelper validationHelper, XSSFSheet hssfSheet) throws Exception {
			CustomUserDetails							userDetails				= null;
			List<Vehicle>  	 							vehicleList				= null;
			List<Driver>  	 							driverList				= null;
			try {
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				vehicleList		= vehicleService.getVehicleListByCompanyId(userDetails.getCompany_id());
				driverList		= driverService.getActiveDriverList(userDetails.getCompany_id(), DriverStatus.DRIVER_STATUS_ACTIVE);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return hssfSheet;
		}
		
		 private String []  getVehicleArray(List<Vehicle> list) {
				List<String>  				maStrings 			= null;	
				try {
						maStrings = new ArrayList<>();
						for(Vehicle 	vehicle : list){
							  maStrings.add(vehicle.getVehicle_registration());
						}
						return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
					} catch (Exception e) {
						throw e;
					}finally {
						maStrings 			= null;
					}
				}
		 private String []  getDriverArray(List<Driver> list) {
			 List<String>  				maStrings 			= null;	
			 try {
				 maStrings = new ArrayList<>();
				 for(Driver 	driver : list){
					 maStrings.add(driver.getDriver_firstname()+" "+driver.getDriver_fathername()+" "+ driver.getDriver_Lastname());
				 }
				 return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
			 } catch (Exception e) {
				 throw e;
			 }finally {
				 maStrings 			= null;
			 }
		 }
		 
			private DataValidation  getDropDownList(int firstRow, int lastRow, int firstCol, int lastCol, String[] myarray, DataValidationHelper validationHelper) throws Exception{
				DataValidation 				dataValidation 					= null;
				CellRangeAddressList		addressList 					= null;
				DataValidationConstraint 	constraint 						= null;
				try {
					addressList 	= new  CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);
				    constraint 		= validationHelper.createExplicitListConstraint(myarray);
				    dataValidation  = validationHelper.createValidation(constraint, addressList);
				    dataValidation.setSuppressDropDownArrow(true);   
					
					return dataValidation;
				} catch (Exception e) {
					throw e;
				}
			}
			
		//	@SuppressWarnings({ "deprecation", "unlikely-arg-type", "unchecked" })
			@SuppressWarnings("unchecked")
			@Override
			@Transactional
			public  void saveTripsheetExcel(XSSFWorkbook myWorkBook) throws Exception {
				ValueObject							tripSheetValueObject		= null;
				TripSheet 							tripsheet 					= null;
				ValueObject							expenseValueObject			= null;
				ValueObject							incomeValueObject			= null;
				XSSFSheet 							mySheet 					= null;
				Iterator<Row> 						rowIterator 				= null; 
				int 								noOfRows2					= 0;
				TripSheetAdvance					tripsheetAdvance			= null;
				List<TripSheetExpense>				tripsheetExpenseList		= null;
				List<TripSheetIncome>				tripsheetIncomeList			= null;
				List<TripSheetDto>					tripSheetDtoList			= null;
				HashMap<String, TripSheetDto> 		tripSheetDtoHM				= null;
				CustomUserDetails					userDetails					= null;
				TripSheetDto	 					tripsheetDto 			= null;
				HashMap<Integer, TripSheetDto> 		vehicleHM				= null;
				Integer								tripClosingKM			= 0;
				try {
					tripSheetValueObject	= new ValueObject();
					expenseValueObject		= new ValueObject();
					incomeValueObject		= new ValueObject();
					vehicleHM				= new HashMap<>();
					tripSheetDtoHM			= new HashMap<>();
					mySheet 				= myWorkBook.getSheetAt(0);
					rowIterator 			= mySheet.iterator();
					userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					
					tripSheetDtoList	= getCreatedTripsheetDetails(userDetails.getCompany_id());
					if(tripSheetDtoList != null && !tripSheetDtoList.isEmpty()) {
						for(TripSheetDto dto :tripSheetDtoList) {
							tripSheetDtoHM.put(dto.getTripBookref(), dto);
						}
					}
					
					while (rowIterator.hasNext()) {
						Row myrow = rowIterator.next();
						noOfRows2 += 1;
						
						
						if(myrow.getCell(1) == null) {
							if(tripSheetDtoList != null && !tripSheetDtoList.isEmpty()) {
								for(TripSheetDto dto :tripSheetDtoList) {
									if(vehicleHM.containsKey(dto.getVid())) {
										if(dto.getTripUsageKM() == 0 ) {
											updateOpeningClosingKM(dto.getTripSheetID(), dto.getTripUsageKM(), dto.getTripUsageKM());
										}else {
											updateOpeningClosingKM(dto.getTripSheetID(), tripClosingKM, (dto.getTripUsageKM()+tripClosingKM));
										}
										
										tripClosingKM = dto.getTripUsageKM()+tripClosingKM;
									}else {
										updateOpeningClosingKM(dto.getTripSheetID(), 0, dto.getTripUsageKM());
										tripClosingKM = dto.getTripUsageKM();
										vehicleHM.put(dto.getVid(), dto);
									}
								}
							}
							break;
						}
						
						
						if(noOfRows2 == 1 || (noOfRows2 > 1 && tripSheetDtoHM.containsKey(myrow.getCell(1).getStringCellValue()))) {
							continue;
						}
							/*Add TripSheet and TripAdvance*/
						tripSheetValueObject 	= addTripsheetExcel(myrow);
						
						if(tripSheetValueObject.containsKey("tripsheetBL")) {
							tripsheet 				= TripSheetDao.save((TripSheet) tripSheetValueObject.get("tripsheetBL"));
							
							if(tripsheet != null ) {
								tripsheetAdvance 		= (TripSheetAdvance) tripSheetValueObject.get("tripsheetAdvanceBL");
								
								if(tripsheetAdvance != null && tripsheetAdvance.getAdvanceAmount() > 0 ) {
									tripsheetAdvance.setTripsheet(tripsheet);
									TripSheetAdvanceRepository.save(tripsheetAdvance);
								}
								
								/*Add TripSheet Expense */
								expenseValueObject 		= addTripsheetExpenseExcel(tripsheet,myrow);
								
								if(expenseValueObject.containsKey("TripExpenseList")) {
									tripsheetExpenseList	=  (List<TripSheetExpense>) expenseValueObject.get("TripExpenseList");
									
									if(tripsheetExpenseList != null && !tripsheetExpenseList.isEmpty()) {
										TripSheetExpenseRepository.saveAll(tripsheetExpenseList);
										UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripsheet.getTripSheetID(), userDetails.getCompany_id());
									}
								}
								
								/*Add TripSheet Income */
								incomeValueObject 	= 	addTripsheetIncomeExcel(tripsheet,myrow);
								tripsheetIncomeList	= (List<TripSheetIncome>) incomeValueObject.get("TripIncomeList");
								
								if(tripsheetIncomeList != null && !tripsheetIncomeList.isEmpty()) {
									TripSheetIncomeRepository.saveAll(tripsheetIncomeList);
									UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(tripsheet.getTripSheetID(), userDetails.getCompany_id());
								}
								
							}
						}


					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
			
		//	@SuppressWarnings({ "deprecation", "unchecked" })
			@SuppressWarnings("deprecation")
			@Override
			public  ValueObject addTripsheetExcel(Row myrow) throws Exception {
				String 								cellValue				= null;
				TripSheet							tripsheetBL				= null;
				CustomUserDetails					userDetails				= null;
				List<Vehicle>  	 					vehicleList				= null;
				HashMap<String, Vehicle> 			vehicleHM				= null;
				List<Driver>  	 					driverList				= null;
				HashMap<String, Driver> 			driverHM				= null;
				List<TripRoute>  	 				tripRouteList			= null;
				HashMap<String, TripRoute> 			tripRouteHM				= null;
				ValueObject							valueObject				= null;
				TripSheetAdvance					tripsheetAdvanceBL		= null;
				UserProfileDto 						orderingname 			= null;
				TripSheetSequenceCounter 			sequenceCounter 		= null;		 
				
				
				try {
					valueObject 		= new ValueObject();
					vehicleHM			= new HashMap<>();
					driverHM			= new HashMap<>();
					tripRouteHM			= new HashMap<>();
					userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					
					
					orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
					sequenceCounter = tripsheetSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
					
					vehicleList			= vehicleService.getVehicleListByCompanyId(userDetails.getCompany_id());
					tripRouteList		= tripRouteRepository.getAllTripRouteByCompanyId(userDetails.getCompany_id());
					driverList			= driverService.getActiveDriverList(userDetails.getCompany_id(), DriverStatus.DRIVER_STATUS_ACTIVE);
					
					
					for(Vehicle dto : vehicleList) {
						vehicleHM.put(dto.getVehicle_registration(), dto);
					}
					
					for(Driver driverDto : driverList) {
						driverHM.put(driverDto.getDriver_mobnumber(), driverDto);
					}
					
					for(TripRoute tripRouteDto : tripRouteList) {
						tripRouteHM.put(tripRouteDto.getRouteName(), tripRouteDto);
					}
					
					tripsheetBL 				= new TripSheet();
					for (int cn = 0; cn < 17; cn++ ) {
						
						Cell cellVal = myrow.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						
						switch (cellVal.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							cellValue	= cellVal.getStringCellValue().toUpperCase();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							cellValue	= Long.toString((long)cellVal.getNumericCellValue());
							break;
						default:
							cellValue	= null;
							break;
						}
						
						
					if(cellValue != null && !cellValue.isEmpty()) {
						
						tripsheetBL.setTripSheetNumber(sequenceCounter.getNextVal());
						if(cn == 1) {
							tripsheetBL.setTripBookref("LHPV-"+cellValue);
						}
						if(cn == 2 && vehicleHM.containsKey(cellValue) ) {
							tripsheetBL.setVid(vehicleHM.get(cellValue).getVid());
						}
						if(cn == 3) {
							
							tripsheetBL.setTripOpenDate(DateTimeUtility.getTimeStamp(cellValue));
							tripsheetBL.setDispatchedByTime(DateTimeUtility.getTimeStamp(cellValue));
						}
						if(cn == 4) {
							tripsheetBL.setClosetripDate(DateTimeUtility.getTimeStamp(cellValue));
							tripsheetBL.setClosedByTime(DateTimeUtility.getTimeStamp(cellValue));
						}
						if(cn == 6 && driverHM.containsKey(cellValue)) {
							tripsheetBL.setTripFristDriverID(driverHM.get(cellValue).getDriver_id());
							
						}
						if(cn == 7 ) {
							if( tripRouteHM.containsKey(cellValue) ) {
								tripsheetBL.setRouteID(tripRouteHM.get(cellValue).getRouteID());
							}else {
								tripsheetBL.setRouteName(cellValue);
							}
						}
						if(cn == 8) {
							tripsheetBL.setTripOpeningKM(0);
							tripsheetBL.setTripClosingKM(Integer.parseInt(cellValue));
							tripsheetBL.setTripUsageKM(Integer.parseInt(cellValue));
						}
						if(cn == 9) {
							if(cellValue != null && !cellValue.isEmpty()) {
								tripsheetAdvanceBL	= new TripSheetAdvance();
								tripsheetAdvanceBL.setAdvanceAmount(Double.parseDouble(cellValue));
								tripsheetBL.setTripTotalAdvance(Double.parseDouble(cellValue));
								tripsheetAdvanceBL.setDriverId(tripsheetBL.getTripFristDriverID());
								tripsheetAdvanceBL.setAdvancePaidbyId(userDetails.getId());
								tripsheetAdvanceBL.setAdvancePlaceId(orderingname.getBranch_id());
								tripsheetAdvanceBL.setMarkForDelete(false);
								tripsheetAdvanceBL.setCreatedById(userDetails.getId());
								tripsheetAdvanceBL.setCompanyId(userDetails.getCompany_id());
								tripsheetAdvanceBL.setCreated(DateTimeUtility.getCurrentTimeStamp());
							}else {
								tripsheetAdvanceBL.setAdvanceAmount(0.0);
							}
						}
						if(cn == 10) {
							tripsheetBL.setTripTotalincome(Double.parseDouble(cellValue));
						}
						if(cn == 16) {
							tripsheetBL.setTripTotalexpense(Double.parseDouble(cellValue));
						}
						
					
						tripsheetBL.setDispatchedLocationId(orderingname.getBranch_id()); 
						tripsheetBL.setClosedLocationId(orderingname.getBranch_id());
						tripsheetBL.setCloseTripStatusId(TripCloseStatus.TRIP_CLOSE_STATUS_OFFICE);
						tripsheetBL.setNoOfPOD(0);
						tripsheetBL.setReceivedPOD(0);
						tripsheetBL.setTripStartDiesel(0.0);
						tripsheetBL.setTripGpsOpeningKM(0.0);
						tripsheetBL.setTripGpsClosingKM(0.0);
						tripsheetBL.setGpsTripUsageKM(0.0);
						tripsheetBL.setTripStutesId(TripSheetStatus.TRIP_STATUS_CLOSED);
						tripsheetBL.setDispatchedById(userDetails.getId());
						tripsheetBL.setClosedById(userDetails.getId());
						tripsheetBL.setCloseTripNameById(orderingname.getUser_id());
						tripsheetBL.setCreated(DateTimeUtility.getCurrentTimeStamp());
						tripsheetBL.setCreatedById(userDetails.getId());
						tripsheetBL.setLastupdated(DateTimeUtility.getCurrentTimeStamp());
						tripsheetBL.setLastModifiedById(userDetails.getId());
						tripsheetBL.setMarkForDelete(false);
						tripsheetBL.setCompanyId(userDetails.getCompany_id());
						tripsheetBL.setTripCleanerRoutePoint(0.0);
						tripsheetBL.setTripFristDriverRoutePoint(0.0);
						tripsheetBL.setTripSecDriverRoutePoint(0.0);
						tripsheetBL.setTripFristDriverBata(0.0);
						tripsheetBL.setTripSecDriverBata(0.0);
						tripsheetBL.setTripCleanerBata(0.0);
						tripsheetBL.setTripCommentTotal(0);
						tripsheetBL.setCloseTripReference("");
						
						
						valueObject.put("tripsheetAdvanceBL", tripsheetAdvanceBL);	
						valueObject.put("tripsheetBL", tripsheetBL);	
					}
				}
			return 	valueObject;
			
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
			
			

			@SuppressWarnings("deprecation")
			@Override
			public  ValueObject addTripsheetExpenseExcel(TripSheet tripsheet, Row myrow) throws Exception {
				TripSheetExpense 					tripExpense				= null;
				TripExpense 						tripSheetExpense 		= null;
				List<TripSheetExpense> 				tripExpenseList			= null;
				String 								cellValue				= null;
				ValueObject 						finalValueObject		= null;
				ValueObject 						valueInObject			= null;
				ValueObject 						valueOutObject			= null;
				CustomUserDetails 					userDetails				= null;
				UserProfileDto 						orderingname	 		= null;
				try {
					finalValueObject	= new ValueObject();
					valueInObject		= new ValueObject();
					valueOutObject		= new ValueObject();
					
					tripExpenseList		= new ArrayList<>();
					userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					orderingname 		= userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
					for (int cn = 0; cn < 17; cn++ ) {
						
						Cell cellVal = myrow.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						
						switch (cellVal.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							cellValue	= cellVal.getStringCellValue().toUpperCase();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							cellValue	= Long.toString((long)cellVal.getNumericCellValue());
							break;
						default:
							cellValue	= null;
							break;
						}
						
						valueInObject.put("branchId", orderingname.getBranch_id());
						if(cn == 12 && cellValue != null && Double.parseDouble(cellValue) > 0) {
							tripSheetExpense = tripExpenseService.getTripExpenseByName("Diesel Expense", userDetails.getCompany_id());
							valueInObject.put("expenseId", tripSheetExpense.getExpenseID());
							valueInObject.put("cellValue", cellValue);
							valueInObject.put("tripsheet", tripsheet);
							valueOutObject 		= tripSheetBL.preparedTripSheetExpenseFromExcel(valueInObject);
							tripExpense		 	= (TripSheetExpense) valueOutObject.get("tripExpense");
							if(tripExpense != null) {
								tripExpenseList.add(tripExpense);
							}
						}
						if(cn == 13 && cellValue != null && Double.parseDouble(cellValue) > 0) {
							tripSheetExpense = tripExpenseService.getTripExpenseByName("Toll Expense", userDetails.getCompany_id());
							valueInObject.put("expenseId", tripSheetExpense.getExpenseID());
							valueInObject.put("cellValue", cellValue);
							valueInObject.put("tripsheet", tripsheet);
							valueOutObject 		= tripSheetBL.preparedTripSheetExpenseFromExcel(valueInObject);
							tripExpense		 	= (TripSheetExpense) valueOutObject.get("trip Expense");
							if(tripExpense != null) {
								tripExpenseList.add(tripExpense);
							}
						}
						
						if(cn == 14 && cellValue != null && Double.parseDouble(cellValue) > 0) {
							tripSheetExpense = tripExpenseService.getTripExpenseByName("Driver Expense", userDetails.getCompany_id());
							valueInObject.put("expenseId", tripSheetExpense.getExpenseID());
							valueInObject.put("cellValue", cellValue);
							valueInObject.put("tripsheet", tripsheet);
							valueOutObject 		= tripSheetBL.preparedTripSheetExpenseFromExcel(valueInObject);
							tripExpense		 	= (TripSheetExpense) valueOutObject.get("tripExpense");
							if(tripExpense != null) {
								tripExpenseList.add(tripExpense);
							}
							
						}
						if(cn == 15 && cellValue != null && Double.parseDouble(cellValue) > 0) {
							tripSheetExpense = tripExpenseService.getTripExpenseByName("Misc Expense", userDetails.getCompany_id());
							
							valueInObject.put("expenseId", tripSheetExpense.getExpenseID());
							valueInObject.put("cellValue", cellValue);
							valueInObject.put("tripsheet", tripsheet);
							
							valueOutObject 		= tripSheetBL.preparedTripSheetExpenseFromExcel(valueInObject);
							tripExpense		 	= (TripSheetExpense) valueOutObject.get("tripExpense");
							if(tripExpense != null) {
								tripExpenseList.add(tripExpense);
							}
						}
					}
					
					finalValueObject.put("TripExpenseList", tripExpenseList);
					
					return finalValueObject;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				
		}
			
			@SuppressWarnings("deprecation")
			@Override
			public  ValueObject addTripsheetIncomeExcel(TripSheet tripsheet, Row myrow) throws Exception {
				TripSheetIncome 					TripIncome			= null;
				List<TripSheetIncome> 				TripIncomeList				= null;
				String 								cellValue				= null;
				ValueObject 						valueObject				= null;
				try {
					java.util.Date 		currentDateUpdate 	= new java.util.Date();
					Timestamp 			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
					CustomUserDetails 	userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					UserProfileDto 		Orderingname 		= userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
					valueObject								= new ValueObject();
					TripIncomeList							= new ArrayList<>();
					
					for (int cn = 0; cn < 17; cn++ ) {
						TripIncome 	= new TripSheetIncome();
						Cell cellVal = myrow.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						
						switch (cellVal.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							cellValue	= cellVal.getStringCellValue().toUpperCase();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							cellValue	= Long.toString((long)cellVal.getNumericCellValue());
							break;
						default:
							cellValue	= null;
							break;
						}
						if(cellValue != null && !cellValue.isEmpty() ) {
							
							if(cn == 10 && cellValue != null && Double.parseDouble(cellValue) > 0) {
								List<TripIncome> income = tripIncomeService.getTripIncomeByName("Total Freight", userDetails.getCompany_id());
									TripIncome.setIncomeId(income.get(0).getIncomeID());
									TripIncome.setIncomeAmount(Double.parseDouble(cellValue));
									TripIncome.setTripsheet(tripsheet);
									TripIncome.setIncomePlaceId(Orderingname.getBranch_id());
									TripIncome.setIncomeCollectedById(userDetails.getId());
									TripIncome.setCompanyId(userDetails.getCompany_id());
									TripIncome.setCreatedById(userDetails.getId());
									TripIncome.setIncomeFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
									TripIncome.setCreated(toDate);
									TripIncome.setMarkForDelete(false);
									TripIncome.setDriverExcluded(false);
									TripIncomeList.add(TripIncome);
							}
							if(cn == 11 && cellValue != null && Double.parseDouble(cellValue) > 0) {
								List<TripIncome> income = tripIncomeService.getTripIncomeByName("Other Income", userDetails.getCompany_id());
									TripIncome.setIncomeId(income.get(0).getIncomeID());
									TripIncome.setIncomeAmount(Double.parseDouble(cellValue));
									TripIncome.setTripsheet(tripsheet);
									TripIncome.setIncomePlaceId(Orderingname.getBranch_id());
									TripIncome.setIncomeCollectedById(userDetails.getId());
									TripIncome.setCompanyId(userDetails.getCompany_id());
									TripIncome.setCreatedById(userDetails.getId());
									TripIncome.setIncomeFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
									TripIncome.setCreated(toDate);
									TripIncome.setMarkForDelete(false);
									TripIncome.setDriverExcluded(false);
									TripIncomeList.add(TripIncome);
							}

							valueObject.put("TripIncomeList", TripIncomeList);
						}
					}
					
					return valueObject;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				
		}
	
			@Override
			public List<TripSheetDto> getCreatedTripsheetDetails(Integer companyId) throws Exception {
				TypedQuery<Object[]> query = null;
				query = entityManager.createQuery(
						" SELECT TS.tripSheetID, TS.tripUsageKM, TS.tripOpeningKM, TS.tripClosingKM ,TS.tripBookref , TS.vid"
								+ " from TripSheet TS " 
								+ " where TS.companyId = " + companyId + " "
								+ " AND TS.markForDelete = 0 ORDER BY TS.vid ,TS.dispatchedByTime ASC ",Object[].class);
				List<Object[]> results = query.getResultList();

				List<TripSheetDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<TripSheetDto>();
					TripSheetDto select = null;
					for (Object[] vehicle : results) {

						select = new TripSheetDto();
						select.setTripSheetID((Long) vehicle[0]);
						if(vehicle[1] != null) {
							select.setTripUsageKM((Integer) vehicle[1]);
						}else {
							select.setTripUsageKM(0);
						}
						if(vehicle[2] != null) {
							select.setTripOpeningKM((Integer) vehicle[2]);
						}
						if(vehicle[3] != null) {
							select.setTripClosingKM((Integer) vehicle[3]);
						}
						if(vehicle[4] != null) {
							select.setTripBookref((String) vehicle[4]);
						}
						select.setVid((Integer) vehicle[5]);

						Dtos.add(select);
					}
				}
				return Dtos;

			}	
				
		
		@Transactional
		public void updateCloseTripAmount(long tripsheetId, double tripcloseAmount, Integer companyId)
				throws Exception {
			entityManager.createQuery(
					"Update TripSheet AS T Set  T.closeTripAmount = "+tripcloseAmount+" WHERE T.tripSheetID = " + tripsheetId + "  AND companyId = " + companyId+ " ").executeUpdate();
		}
		
		@Override
		public ValueObject getTripSheetDetailsByNumber(String vehicleNumber) throws Exception {
			ValueObject        object     = new ValueObject();
            Query queryt =     null;
            try {
                if(vehicleNumber != null && !vehicleNumber.trim().equalsIgnoreCase("") && vehicleNumber.indexOf('\'') != 0  ) {
                    queryt = entityManager.createQuery(
                            " SELECT TS.tripSheetID, TS.tripSheetNumber, TS.dispatchedByTime, TS.tripOpenDate, TS.closetripDate,"
                            + " TR.routeName, TS.routeName, D1.driver_firstname, D1.driver_Lastname, D2.driver_firstname, D2.driver_Lastname,"
                            + " D3.driver_firstname, D3.driver_Lastname,D1.driver_mobnumber,D2.driver_mobnumber,D3.driver_mobnumber,D1.driver_dlnumber,D2.driver_dlnumber "
                            + " from TripSheet TS "
                            + " INNER JOIN Vehicle V ON V.vid = TS.vid "
                            + " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
                            + " LEFT JOIN Driver D1 ON D1.driver_id = TS.tripFristDriverID "
                            + " LEFT JOIN Driver D2 ON D2.driver_id = TS.tripSecDriverID "
                            + " LEFT JOIN Driver D3 ON D3.driver_id = TS.tripCleanerID "
                            + " WHERE V.vehicle_registration = '"+vehicleNumber+"'  AND TS.markForDelete = 0 AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_DISPATCHED+"  ");
                Object[] vehicle = null;
                try {
                    queryt.setMaxResults(1);
                    vehicle = (Object[]) queryt.getSingleResult();
                    
                } catch (NoResultException nre) {
                //ignore
                };

                TripSheetDto select;
                if (vehicle != null) {
                    select = new TripSheetDto();
                    
                    select.setTripSheetID((Long) vehicle[0]);
                    select.setTripSheetNumber((Long) vehicle[1]);
                    select.setDispatchedByTime(dateTimeFormat2.format((java.util.Date)vehicle[2]));
                    select.setTripOpenDate(dateTimeFormat2.format((java.util.Date)vehicle[3]));
                    select.setClosetripDate(dateTimeFormat2.format((java.util.Date)vehicle[4]));
                    select.setRouteName((String) vehicle[5]);
                    if(select.getRouteName() == null || select.getRouteName().equals(""))
                        select.setRouteName((String) vehicle[6]);
                    
                    if(vehicle[7]!= null &&!((String)vehicle[7]).trim().equals(""))
                        select.setTripFristDriverName((String) vehicle[7]);    
                    
                    if(vehicle[8]!= null &&!((String)vehicle[8]).trim().equals(""))
                        select.setTripFristDriverName(select.getTripFristDriverName()+"-"+ vehicle[8]);    
                    
                    if(vehicle[9]!= null &&!((String)vehicle[9]).trim().equals(""))
                        select.setTripSecDriverName((String) vehicle[9]);    
                    
                    if(vehicle[10]!= null &&!((String)vehicle[10]).trim().equals(""))
                        select.setTripSecDriverName(select.getTripSecDriverName()+"-"+ vehicle[10]);
                    
                    if(vehicle[11]!= null &&!((String)vehicle[11]).trim().equals(""))
                        select.setTripCleanerName((String)vehicle[11]);    
                    
                    if(vehicle[12]!= null &&!((String)vehicle[12]).trim().equals(""))
                        select.setTripCleanerName(select.getTripCleanerName()+"-"+ vehicle[12]);
                    
                    if(vehicle[13] != null)
                    	select.setTripFristDriverMobile((String) vehicle[13]);
                    
                    if(vehicle[14] != null)
                    	select.setTripSecDriverMobile((String) vehicle[14]);
					
					if(vehicle[15] != null)
                    	select.setTripCleanerMobile((String) vehicle[15]);
					
					if(vehicle[16] != null)
						select.setTripFristDriverDL((String) vehicle[16]);
					
					if(vehicle[17] != null)
						select.setTripSecDriverDL((String) vehicle[17]);
					
                    object.put("tripSheet", select);
                	}  
                }
               
                return object ;
            
            } catch (Exception e) {
                throw e;
            }		
        }

		
		@Override
		public TripSheetDto getActiveTripSheetByVid(Integer vid) throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber"
							+ " from TripSheet TS "
							+ " INNER JOIN Vehicle V ON V.vid = TS.vid "
							+ " WHERE TS.vid = '"+vid+"'  AND TS.markForDelete = 0 AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_DISPATCHED+"  ");
				
				Object[] vehicle = null;
				try {
					queryt.setMaxResults(1);
					vehicle = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					
				}
				
				TripSheetDto select;
				if (vehicle != null) {
					select = new TripSheetDto();
					
					select.setTripSheetID((Long) vehicle[0]);
					select.setTripSheetNumber((Long) vehicle[1]);
				} else {
					return null;
				}
				
				return select;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public ValueObject getTripSheetDetailsAtTime(ValueObject valueObject) throws Exception {
			try {
				
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("accidentDate"), valueObject.getString("accidentTime"));
				String tripActiveDateAndTime = dateTimeFormat.format(date);
				
				TripSheetDto tripActive = fuelService.getTripsheetDataAtTime(tripActiveDateAndTime, valueObject.getInt("vid",0), valueObject.getInt("companyId",0));
			
				valueObject.put("tripActive", tripActive);
				
				return valueObject;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public ValueObject getLastTripsheetFuelMileage(ValueObject valueObject) throws Exception {
			Long tripSheetId = getLastTripSheetIdOfDriver(valueObject.getInt("driverId", 0));
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(tripSheetId != null && tripSheetId > 0) {
				valueObject.put("Fuel", getTripSheetFuelMilage(tripSheetId, userDetails.getCompany_id())); 
			}
			return valueObject;
		}
		
		
		@Override
		public Long getLastTripSheetIdOfDriver(Integer driverId) throws Exception {
			Query query = null;
				query = entityManager.createQuery(
						"SELECT T.tripSheetID "
								+ " FROM TripSheet T "
								+ " INNER JOIN Fuel F ON F.fuel_TripsheetID = T.tripSheetID AND F.companyId = T.companyId"
								+ " WHERE (T.tripFristDriverID = "+driverId+" OR T.tripSecDriverID = "+driverId+") AND T.markForDelete = 0 order by T.dispatchedByTime desc");


			try {
				query.setMaxResults(1);
				return (Long) query.getSingleResult();
			} catch (NoResultException nre) {
				return null;
			}
		}
		
		private FuelDto getTripSheetFuelMilage(Long	tripSheetId, Integer companyId) throws Exception {
			return fuelService.getTripsheetFuelMileage(tripSheetId, companyId);
		}
		

		@Override
		public List<TripSheetDto> getUserWiseTSActivityList(String queryStr, String innerQuery) throws Exception {
			List<TripSheetDto>        	tripSheetDto		= null;
			TypedQuery<Object[]> 		query 				= null;
			try {
				
				tripSheetDto = new ArrayList<TripSheetDto>();
				query = entityManager.createQuery(
						" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid,"
								+ " TS.tripOpenDate, TS.closetripDate ,V.vehicle_registration ,TR.routeID ,TR.routeName ,TS.routeName ,VG.vGroup "
								+ ", TS.created ,TS.lastupdated ,TS.createdById, TS.lastModifiedById ,U.firstName, U.lastName ,U.id , TS.markForDelete"
								+ " from TripSheet TS " 
								+ " "+innerQuery+" "
								+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
								+ " "+queryStr+" "
								 ,Object[].class);
				List<Object[]> results = query.getResultList();
				
				if (results != null && !results.isEmpty()) {
					TripSheetDto select = null;
					for (Object[] vehicle : results) {
						
						select = new TripSheetDto();
						select.setTripSheetID((Long) vehicle[0]);
						select.setTripSheetNumber((Long) vehicle[1]);
						select.setVid((Integer) vehicle[2]);
						select.setTripOpenDate(dateFormat.format((java.util.Date) vehicle[3]));
						select.setClosetripDate(dateFormat.format((java.util.Date) vehicle[4]));
						select.setVehicle_registration((String) vehicle[5]);
						select.setRouteID((Integer) vehicle[6]);
						select.setRouteName((String) vehicle[7]);
						if(select.getRouteName() == null || select.getRouteName() == "")
						select.setRouteName((String) vehicle[8]);
						select.setVehicle_Group((String) vehicle[9]);
						if(vehicle[10] != null)
						select.setCreated(dateFormat.format(vehicle[10]));
						if(vehicle[11] != null)
						select.setLastupdated(dateFormat.format(vehicle[11]));
						select.setCreatedById((Long)vehicle[12]);
						select.setLastModifiedById((Long)vehicle[13]);
						select.setUserName(vehicle[14]+" "+vehicle[15]);
						select.setUserId((Long)vehicle[16]);
						select.setMarkForDelete((boolean)vehicle[17]);
						if(!select.isMarkForDelete()) {
							select.setTripSheetNumberStr("<a target=\"_blank\" style=\"color: blue; background: #ffc;\"  href=\"showTripSheet.in?tripSheetID="+select.getTripSheetID()+" \"> TS-"+select.getTripSheetNumber()+" </a>");
						}else {
							
							select.setTripSheetNumberStr("<a  style=\"color: red; background: #ffc;\"  href=\"#\" data-toggle=\"tip\" data-original-title=\"Deleted Data\"> TS-"+select.getTripSheetNumber()+" </a>");
						}
						tripSheetDto.add(select);
					}
				}
				return tripSheetDto;
				
			} catch (Exception e) {
			throw e;
			}
		}	
		
		@SuppressWarnings("null")
		@Override
		@Transactional
		public void saveIntangleTripPullAPI() throws Exception {
			long							fromDate						= 0;//unix timestamp will return long
			long							toDate							= 0;//unix timestamp will return long
			String							accountId						= "";
			List<IntangleTripDetails> 		intangleTripDetailsList 		= null;
			int								noOfBackDate					= 1;
			HashMap<String, String> 		vehicleHM						= null;
			JSONArray						intangleAccountingJsonArray		= null;
			JSONArray						intangleVehicleListJsonArray		= null;
			HashMap<String, Object> 		configuration					= null;
			JSONObject						object							= null;
			
			try {
				intangleTripDetailsList			= new ArrayList<>();
				intangleVehicleListJsonArray 	= new JSONArray();	
				
				fromDate					= DateTimeUtility.getUnixEpochStartTimeTimestampInMilliseconds(noOfBackDate);
				toDate						= DateTimeUtility.getUnixEpochEndTimeTimestampInMilliseconds(noOfBackDate);
				configuration				= companyConfigurationService.getCompanyConfiguration(0, PropertyFileConstants.VEHICLE_GPS_CONFIG);
				
				intangleVehicleListJsonArray = vehicleService.getIntangleVehicleList(configuration);
				if(intangleVehicleListJsonArray != null && intangleVehicleListJsonArray.length() > 0) {
					vehicleHM 					= new HashMap<>();
					for(int i=0; i<intangleVehicleListJsonArray.length(); i++) {
						object	= (JSONObject) intangleVehicleListJsonArray.get(i);
						if(!vehicleHM.containsKey(object.getString("id"))) {
							vehicleHM.put(object.getString("id"), object.getString("tag"));
						}
					}
				} 
				
				intangleAccountingJsonArray = getIntangleAccountListing(configuration);
				if(intangleAccountingJsonArray != null && intangleAccountingJsonArray.length() > 0) {
					for(int i=0; i<intangleAccountingJsonArray.length(); i++) {
						object	= (JSONObject) intangleAccountingJsonArray.get(i);
						accountId = (String) object.get("id");
						
						intangleTripDetailsList = getIntangleTripMileageList(configuration,accountId,fromDate,toDate,vehicleHM);
						if(intangleTripDetailsList != null && !intangleTripDetailsList.isEmpty()) {
							intangleTripDetailsRepository.saveAll(intangleTripDetailsList);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}
		
		public JSONArray getIntangleAccountListing(HashMap<String, Object> 	configuration) throws Exception {
			HttpResponse<String> 			intangleAccountingResponse		= null;
			JSONObject						intangleAccountingJsonObject 	= null;
			JSONObject						intangleAccountingResult 		= null;
			JSONArray						intangleAccountingJsonArray		= null;
			String 							intangleAccountingURL 			= "";
			String 							intangleFuelApiAsscessToken 	= "";
			try {
				intangleAccountingURL 			= (String) configuration.getOrDefault("intangleAccountingURL", "");
				intangleFuelApiAsscessToken 	= (String) configuration.getOrDefault("intangleFuelAPI_AsscessToken", "");
				
				Unirest.setTimeouts(0, 0);
				intangleAccountingResponse 		= Unirest.get(""+intangleAccountingURL+"").header("Api-access-token", ""+intangleFuelApiAsscessToken+"").asString();
				
				if(intangleAccountingResponse.getStatus() == 200) {
					intangleAccountingJsonObject	= new JSONObject(intangleAccountingResponse.getBody());
					if(intangleAccountingJsonObject.has("result")) {
						intangleAccountingResult 	= (JSONObject) intangleAccountingJsonObject.get("result");
						intangleAccountingJsonArray	= intangleAccountingResult.getJSONArray("accounts");
					}
				}
				return intangleAccountingJsonArray;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		public List<IntangleTripDetails> getIntangleTripMileageList(HashMap<String, Object> configuration,String accountId, long fromDate, long toDate,HashMap<String, String> vehicleHM) throws Exception {
			List<IntangleTripDetails> 		intangleTripDetailsList 		= null;
			String 							intangleTripMileageURL 			= "";
			String 							intangleFuelApiAsscessToken 	= "";
			JSONObject						jsonObject 						= null;
			JSONObject						result 							= null;
		
			
			try {
				if(!accountId.equals("") && fromDate > 0 && toDate > 0 && vehicleHM != null && !vehicleHM.isEmpty()) {
					intangleTripMileageURL 		= (String) configuration.getOrDefault("intangleTripMileageURL", "");
					intangleFuelApiAsscessToken = (String) configuration.getOrDefault("intangleFuelAPI_AsscessToken", "");
					intangleTripDetailsList		= new ArrayList<>();
					Unirest.setTimeouts(0, 0);
					HttpResponse<String> response = Unirest.get(""+intangleTripMileageURL+""+accountId+"/"+fromDate+"/"+toDate+"")
							.header("Api-access-token", ""+intangleFuelApiAsscessToken+"")
							.asString();
					
					if(response.getStatus() == 200) {
						result		= new JSONObject();
						jsonObject	= new JSONObject(response.getBody());

						if(jsonObject.has("result")) {
							result 		= (JSONObject) jsonObject.get("result");
							JSONArray	jSONArray	= result.getJSONArray("vehicles");
							
							if (jSONArray != null) { 
								intangleTripDetailsList = 	getIntangleTripMileage(jSONArray,vehicleHM);
									
								}
								
							}
						}
					}
				
				return intangleTripDetailsList;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		public List<IntangleTripDetails> getIntangleTripMileage(JSONArray jSONArray,HashMap<String, String> vehicleHM) throws Exception {
			IntangleTripDetails				validateIntangleTripDetails		= null;
			IntangleTripDetails				intangleTripDetailsBL 			= null;
			List<IntangleTripDetails> 		intangleTripDetailsList 		= null;
			try {
				if (jSONArray != null) { 
					intangleTripDetailsList		= new ArrayList<>();
					JSONObject	object	= null;
					for (int i=0; i<jSONArray.length(); i++){ 
						object	= (JSONObject) jSONArray.get(i);
						if(vehicleHM.containsKey(object.get("vid"))){
							object.put("vehicleNumber", vehicleHM.get(object.get("vid")));
						}else {
							object.put("vehicleNumber", "");
						}
						String intangleDate = (String) object.get("time");
						String dateStr = (intangleDate.split("T"))[0];
						String TimeStr = (intangleDate.split("T"))[1].substring(0, 10);
						String dateAndTime = dateStr+" "+TimeStr;
						Timestamp finalDate = DateTimeUtility.getTimeStamp(dateAndTime, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
						object.put("finalDate",finalDate);
						
						validateIntangleTripDetails = intangleTripDetailsRepository.validateIntangleTripMileageDetails(object.getString("vehicleNumber"),finalDate);
						
						if(validateIntangleTripDetails != null) {
							continue;
						}
						intangleTripDetailsBL =  tripSheetBL.prepareIntangleTripDetails(object);
						intangleTripDetailsList.add(intangleTripDetailsBL);
					}
				}
				return intangleTripDetailsList;		
					
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}
		
		@Override
		public ValueObject getAllGroupTripsheetDateReport(ValueObject valueObject) throws Exception {
			String						dateRange				= "";
			CustomUserDetails			userDetails				= null;
			int 						groupId					= 0;
			ValueObject					tableConfig				= null;
			List<TripSheetDto> 			advanceCollection		= null;
			
			
			try {
				userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
				groupId							= valueObject.getInt("group", 0);
				dateRange						= valueObject.getString("date");
			

				if(dateRange != null) {

					String group = " ",dateRangeSQL="";
					
					dateRangeSQL	= DateTimeUtility.getSqlStrDateFromStrDate(dateRange, DateTimeUtility.YYYY_MM_DD);
					if (groupId != 0) {
						group = "  AND ( V.vehicleGroupId=" + groupId + ") ";
					}
					
					 advanceCollection		= list_Report_TripSheet_DATE(dateRangeSQL, group, userDetails.getCompany_id()) ;
					 
					 if (groupId != 0) {
						 if(advanceCollection !=null && !advanceCollection.isEmpty())
						 valueObject.put("groupKey",advanceCollection.get(0).getVehicle_Group()); 
					 }else {
						 valueObject.put("groupKey","All"); 
						 
					 }
				
				}	

				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_GROUP_TRIPSHEET_DATEREPORT);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

		
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("tripSheetDto", advanceCollection);
				
				valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			

				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				userDetails			= null;			
			}
		}
		@Override
		public ValueObject getTripsheetDetailsithoutLoadingSheet(ValueObject valueObject) throws Exception {
			String						dateRange				= "";
			CustomUserDetails			userDetails				= null;
			int 						groupId					= 0;
			ValueObject					tableConfig				= null;
			List<TripSheetDto> 			advanceCollection		= null;
			try {
				userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
				groupId							= valueObject.getInt("group", 0);
				dateRange						= valueObject.getString("dateRange");
			
				if(dateRange != null) {
					
					String dateRangeFrom = "", dateRangeTo = "";
					String[] From_TO_DateRange = null;
					From_TO_DateRange = dateRange.split(" to ");
					
					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

					String group = " ";
					
					group = "AND T.companyId = "+userDetails.getCompany_id()+" ";
					
					if (groupId != 0) {
						group = "  AND ( V.vehicleGroupId=" + groupId + ") ";
					}
					if(valueObject.getLong("vehicleId",0) != 0) {
						group = "  AND ( T.vid=" + valueObject.getLong("vehicleId",0) + ") ";
					}
					
					valueObject.put("tripSheetList", getTripsheetDetailsithoutLoadingSheet(dateRangeFrom,  dateRangeTo , group, userDetails.getCompany_id()));
					 
					/*
					 * if (groupId != 0) { if(advanceCollection !=null &&
					 * !advanceCollection.isEmpty())
					 * valueObject.put("groupKey",advanceCollection.get(0).getVehicle_Group());
					 * }else { valueObject.put("groupKey","All");
					 * 
					 * }
					 */				
				}	

				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.TRIPSHEET_WITHOUT_LOADINGSHEET);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

		
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("tripSheetDto", advanceCollection);
				
				valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				userDetails			= null;			
			}
		}
			

		@Override
		public Long getLastTripSheetFromGivenDate(java.util.Date date,int companyId ,int driverId) {
			long result = 0 ;
			TypedQuery<Long> query = entityManager.createQuery(" select tripSheetID from TripSheet where dispatchedByTime < '"+date+"' AND ( tripFristDriverID = "+driverId+" OR tripSecDriverID ="+driverId+" ) AND companyId = "+companyId+" order by dispatchedByTime desc ",Long.class);
			try {
				query.setMaxResults(1);
				result =query.getSingleResult();
			} catch (Exception e) {
				
			}
			return result;
		}
		
	private List<LoadingSheetReportDto> getTripsheetDetailsithoutLoadingSheet(String fromDate, String toDate, String filter, Integer companyId) throws Exception {
			Set<Long>   						  tripSheetHM			= null;
			try {
		TypedQuery<Object[]> query = entityManager.createQuery(
						"Select  T.tripSheetID, T.tripSheetNumber, T.tripOpenDate, T.closedByTime, T.dispatchedByTime , V.vid, V.vehicle_registration"
						+ ", T.routeName, TR.routeName, COUNT(distinct TI.dispatchLedgerId), COUNT(distinct TI.wayBillId), SUM(TI.freight),"
						+ " SUM(TI.bookingTotal), SUM(TI.totalNoOfPackages), SUM(TI.dispatchedWeight) ,T.tripTotalexpense ,T.tripStutesId "
								+ " From TripSheet T "
								+ " INNER JOIN Vehicle AS V ON V.vid = T.vid AND V.company_Id = "+companyId+" "
								+ " LEFT JOIN TripRoute TR ON TR.routeID = T.routeID"
								+ " LEFT JOIN LoadingSheetToTripSheet TI ON TI.tripSheetId = T.tripSheetID AND TI.markForDelete = 0 AND TI.companyId = "+companyId+" "
								+ " where ( T.tripOpenDate between '"
								+ fromDate + "' AND '" + toDate + "' " + filter + ") "
								+ " AND T.markForDelete = 0 AND T.tripStutesId >= "+TripSheetStatus.TRIP_STATUS_CLOSED+" group by T.tripSheetID",
								Object[].class);
				
				List<Object[]> results = query.getResultList();
				StringJoiner tripSheetIds = new StringJoiner(",");
				List<LoadingSheetReportDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					tripSheetHM	= new HashSet<>();
					Dtos = new ArrayList<LoadingSheetReportDto>();
					LoadingSheetReportDto select = null;
					for (Object[] vehicle : results) {
						
							select = new LoadingSheetReportDto();
							
							select.setTripSheetID((Long) vehicle[0]);
							select.setTripSheetNumber((Long) vehicle[1]);
							select.setTripOpenDate(dateTimeFormat2.format((java.util.Date)vehicle[2]));
							select.setClosedByTime(dateTimeFormat2.format((java.util.Date)vehicle[3]));
							select.setDispatchedByTime(dateTimeFormat2.format((java.util.Date)vehicle[4]));
							select.setVid((Integer) vehicle[5]);
							select.setVehicle_registration("<a style='color: blue; background: #ffc;' target='_blank' href='showVehicle.in?vid="+select.getVid()+"'>"+(String) vehicle[6]+"</a>");
							select.setRouteName((String) vehicle[7]);
							if(select.getRouteName() == null || select.getRouteName().trim().equals(""))
								select.setRouteName((String) vehicle[8]);
							
							if((Long) vehicle[9] != null && (Long) vehicle[9] > 0)
								select.setNoOfLS("<a style='color: blue; background: #ffc;' onclick='showLSDetails("+select.getTripSheetID()+")' href='#'>"+(Long) vehicle[9]+"</a>");
							else
								select.setNoOfLS("0");
							
							select.setNoOfWayBill((Long) vehicle[10]);
							
							if(vehicle[11] != null)
							    select.setTotalFrieght(Double.parseDouble(vehicle[11]+""));
							
							if(vehicle[12] != null)
							    select.setTotalBooking(Double.parseDouble(vehicle[12]+""));
							else
								select.setTotalBooking(0.0);
							
							if(vehicle[13] != null)
							select.setTotalNoOfPackages(Double.parseDouble(vehicle[13]+""));
							
							if(vehicle[14] != null)
								select.setDispatchedWeight(Double.parseDouble(vehicle[14]+""));
							else
								select.setDispatchedWeight(0.0);
							
							select.setTripSheetNumberStr("<a style='color: blue; background: #ffc;' target='_blank' href='showTripSheet.in?tripSheetID="+select.getTripSheetID()+"'>TS-"+select.getTripSheetNumber()+"</a>");
							select.setTripExpense((Double) vehicle[15]);
							if(!tripSheetHM.contains(select.getTripSheetID())) {
								Dtos.add(select);
								tripSheetHM.add(select.getTripSheetID());
								tripSheetIds.add(""+select.getTripSheetID());
							}
							
							if(vehicle[3]!=null && vehicle[4]!=null){
								select.setNoOfDays(DateTimeUtility.getExactDayDiffBetweenTwoDates((Timestamp) vehicle[4], (Timestamp) vehicle[3])+ "");
							}
							
							select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[16]));

					}
					Map<Long, Double> tollExpenseHm  	=	getTollExpensesDetailsByTripIds(tripSheetIds.toString(), companyId);
					Map<Long, Double> fuelExpenseHm 	= 	getCreditFuelExpensesDetailsByTripIds(tripSheetIds.toString(), companyId);
					if(tollExpenseHm != null || fuelExpenseHm !=  null)
					Dtos.forEach((e)->{
						if(tollExpenseHm != null && tollExpenseHm.get(e.getTripSheetID())!= null)
						e.setTripExpense(e.getTripExpense()+tollExpenseHm.get(e.getTripSheetID()));
						if(fuelExpenseHm != null && fuelExpenseHm.get(e.getTripSheetID())!= null)
							e.setTripExpense(e.getTripExpense()+fuelExpenseHm.get(e.getTripSheetID()));
						
						if(e.getTripExpense() == null)
							e.setTripExpense(0.0);
						

						if(e.getTotalFrieght() == null)
							e.setTotalFrieght(0.0);
						
						e.setBalanceAmount(e.getTotalFrieght() - e.getTripExpense());
					});
				}
				
					
				return Dtos;

			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public ValueObject showLSDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails		userDetails			= null;
			ValueObject				tableConfig			= null;
			try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.LOADING_SHEET_DETAILS_MODAL);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

		
				valueObject.put("tableConfig", tableConfig.getHtData());

				List<LoadingSheetToTripSheetDto>  list	= getLSDetailsByTripSheetId(valueObject.getLong("tripsheetId",0), userDetails.getCompany_id());
				
				valueObject.put("dispatchList", list);
				if(list != null && !list.isEmpty())
					valueObject.put("dispatchListHM", list.stream().collect(Collectors.groupingBy(LoadingSheetToTripSheetDto :: getLsNumber)));
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		private List<LoadingSheetToTripSheetDto>  getLSDetailsByTripSheetId(Long tripsheetId, Integer companyId)throws Exception{
			try {
				TypedQuery<Object[]> query = entityManager.createQuery(
						"Select  T.tripSheetId, T.dispatchLedgerId, T.wayBillId, T.wayBillNumber, T.bookingTotal, T.freight, T.lsNumber,"
						+ " T.totalNoOfPackages, T.tripDateTime, T.waybillTypeId, T.dispatchedWeight "
								+ " From LoadingSheetToTripSheet T "
								+ " where T.tripSheetId = "+tripsheetId+" AND T.companyId = "+companyId+" AND T.markForDelete = 0  ",
								Object[].class);
				
				List<Object[]> results = query.getResultList();
				List<LoadingSheetToTripSheetDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<LoadingSheetToTripSheetDto>();
					LoadingSheetToTripSheetDto select = null;
					for (Object[] result : results) {
						
							select = new LoadingSheetToTripSheetDto();
							
							select.setTripSheetId((Long) result[0]);
							select.setDispatchLedgerId((long) result[1]);
							select.setWayBillId((long) result[2]);
							select.setWayBillNumber((String) result[3]);
							select.setBookingTotal((double) result[4]);
							select.setFreight((double) result[5]);
							select.setLsNumber((String) result[6]);
							select.setTotalNoOfPackages((int) result[7]);
							select.setTripDateTime((Timestamp) result[8]);
							select.setWaybillTypeId((short) result[9]);
							if(result[10] != null)
								select.setDispatchedWeight((Double) result[10]);
							else
								select.setDispatchedWeight(0.0);
							
							select.setWaybillType(WayBillTypeConstant.getVehicleAcTypeName(select.getWaybillTypeId()));
							select.setTripDateTimeStr(dateTimeFormat2.format(select.getTripDateTime()));
							
							Dtos.add(select);
					}
				}
				return Dtos;
			} catch (Exception e) {
				throw e;
			}
			
		}
		
		public Map<Long, Double> getTollExpensesDetailsByTripIds(String ids, int comapnyId) {
			Map<Long, Double> tollExpensesDetailsMap = null;
			TypedQuery<Object[]> query = entityManager
					.createQuery("select T.tripSheetID, SUM(TE.transactionAmount) FROM TripSheet T "
							+ " INNER JOIN TollExpensesDetails TE ON TE.tripSheetId = T.tripSheetID and TE.markForDelete = 0 "
							+ " where T.tripSheetID IN (" + ids + ") AND TE.companyId=" + comapnyId
							+ " group by T.tripSheetID", Object[].class);
			try {

				List<Object[]> resultList = query.getResultList();
				if (resultList != null && !resultList.isEmpty()) {
					tollExpensesDetailsMap = new HashMap<>();
					for (Object[] result : resultList) {
						tollExpensesDetailsMap.put((Long) result[0], (Double) result[1]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return tollExpensesDetailsMap;
		}
		
		public Map<Long, Double> getCreditFuelExpensesDetailsByTripIds(String ids, int comapnyId) {
			Map<Long, Double> expensesDetailsMap = null;
			TypedQuery<Object[]> query = entityManager
					.createQuery("select T.tripSheetID, SUM(F.fuel_amount) FROM TripSheet T "
							+ " INNER JOIN Fuel F ON F.fuel_TripsheetID = T.tripSheetID and F.markForDelete = 0 AND F.paymentTypeId ="+PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " where T.tripSheetID IN (" + ids + ") AND F.companyId=" + comapnyId
							+ " group by T.tripSheetID", Object[].class);
			
			try {

				List<Object[]> resultList = query.getResultList();
				if (resultList != null && !resultList.isEmpty()) {
					expensesDetailsMap = new HashMap<>();
					for (Object[] result : resultList) {
						expensesDetailsMap.put((Long) result[0], (Double) result[1]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return expensesDetailsMap;
		}


		@Override
		public ValueObject getTripSheetDataOnDateRange(ValueObject valueObjectIn) throws Exception {
			Timestamp										startDateOfMonth			= null;
			String											month						= null;
			Timestamp										fromDate					= null;
			Timestamp										toDate						= null;
			try {

				if (valueObjectIn.getString("dateOfMonth", null) != null) {
					month			= valueObjectIn.getString("dateOfMonth", null);
				
					startDateOfMonth		= DateTimeUtility.getTimeStamp(month, DateTimeUtility.DD_MM_YYYY);
					fromDate				= DateTimeUtility.getFirstDayOfMonth(startDateOfMonth); 
					toDate					= DateTimeUtility.getLastDayOfMonth(startDateOfMonth);

				}
				
					String closeTripStatus = "", tripOpenDate_Close = "";

					closeTripStatus = " AND T.tripStutesId = " + 3;
					
					if(valueObjectIn.getInt("vid") > 0) {
						closeTripStatus += " AND T.vid = "+valueObjectIn.getInt("vid")+" ";
					}

					if (fromDate != null && toDate != null) {
						tripOpenDate_Close = " AND T.closetripDate between '" +
								DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) 
							+ "' AND '" + DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS) + "'";
					}

					String query = " " + closeTripStatus + " " + tripOpenDate_Close + " ";

					valueObjectIn.put("TripSheet", tripSheetBL.prepareListofTripSheet(listTripSheet(query)));

					List<Double> TripAdvanceTotal = ReportTripAdvanceTotal(query);

					for (Double total : TripAdvanceTotal) {
						amountFormatDF.setMaximumFractionDigits(0);

						if (total == null) {
							total = 0.0;
						}

						valueObjectIn.put("TripAdvanceTotal", amountFormatDF.format(total));
						break;
					}

					List<Double> TripExpenseTotal = ReportTripExpenseTotal(query);

					for (Double totalExpense : TripExpenseTotal) {
						amountFormatDF.setMaximumFractionDigits(0);

						if (totalExpense == null) {
							totalExpense = 0.0;
						}

						valueObjectIn.put("TripExpenseTotal", amountFormatDF.format(totalExpense));
						break;
					}

					List<Double> TripIncomeTotal = ReportTripIncomeTotal(query);

					for (Double totalIncome : TripIncomeTotal) {
						amountFormatDF.setMaximumFractionDigits(0);

						if (totalIncome == null) {
							totalIncome = 0.0;
						}

						valueObjectIn.put("TripIncomeTotal", amountFormatDF.format(totalIncome));
						break;
					}
	/*
					List<Double> TripAccBanlanceTotal = ReportTripAccBalanceTotal(query);

					for (Double totalBanlance : TripAccBanlanceTotal) {
						amountFormatDF.setMaximumFractionDigits(0);

						if (totalBanlance == null) {
							totalBanlance = 0.0;
						}

						valueObject.put("TripBalanceTotal", amountFormatDF.format(totalBanlance));
						break;
					} */
				
		
				return valueObjectIn;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public void updateTripSheetStatus(String tripSheetIds, TripSheet tripSheet) {
			try {
				entityManager.createQuery(
						"Update TripSheet AS T Set T.tripStutesId = " + tripSheet.getCloseTripStatusId() 
						+ ", closeACCTripReference = '" + tripSheet.getCloseACCTripReference() + "'"
						+ ", closeACCTripNameById = " + tripSheet.getAcclosedById()
						+ ", closeACCtripDate = '" + tripSheet.getAcclosedByTime() + "'"
						+ ", lastupdated = '" + tripSheet.getAcclosedByTime() + "'"
						+ ", lastModifiedById = " + tripSheet.getAcclosedById()
						+ ", acclosedLocationId = " + tripSheet.getAcclosedLocationId() + " WHERE T.tripSheetID IN (" + tripSheetIds + ") AND companyId = " + tripSheet.getCompanyId() + " ")
				.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public ValueObject closeAccountOfSelectedTripSheet(ValueObject valueObjectIn) throws Exception {
			ArrayList<ValueObject> 			dataArrayObjColl 			= null;
			try {
				
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				UserProfileDto orderingName 	 = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
				
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate 				 = new java.sql.Timestamp(currentDateUpdate.getTime());
				
				dataArrayObjColl	= (ArrayList<ValueObject>) valueObjectIn.get("tripSheetDetails");
				if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					for (ValueObject object : dataArrayObjColl) {

						updateCloseAccountTrip(userDetails.getId(), TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED,
								object.getDouble("balance"), valueObjectIn.getString("tripReference"), toDate,
								userDetails.getId(), toDate, object.getLong("tripSheetId"), userDetails.getCompany_id(),
								orderingName.getBranch_id());
					}
					
					valueObjectIn.clear();
					valueObjectIn.put("success",true);
				}
				
				return valueObjectIn;
			} catch (Exception e) {
				throw e;
			}finally {
				dataArrayObjColl 			= null;
			}
		}
		
		@Transactional
		public void updateTripSheetStatusToClose(Short closeAccountBY, short tripStatusId, Double amount,
				String closeReference, Timestamp closeAccTripDate, Long LASTUPDATED, Timestamp LASTUPDATED_DATE, Long tripsheetId,
				Integer companyId, Integer acclocation) throws Exception {
			
			TripSheetDao.updateTripSheetStatusToClose(closeAccountBY, tripStatusId, amount, closeReference, closeAccTripDate,
					LASTUPDATED, LASTUPDATED_DATE, tripsheetId, companyId, acclocation);
		}
		
		@Override
		public List<TripSheetExpenseDto> getDriverBalanceListForTally(Integer companyId, String fromDate, String toDate, 
				Long tallyCompany, String debitLedger, String tallyName)
				throws Exception {
			List<TripSheetExpenseDto>        	tripSheetDto		= null;
			TypedQuery<Object[]> 				query 				= null;
			try {
				
				tripSheetDto = new ArrayList<TripSheetExpenseDto>();
				query = entityManager.createQuery(
						" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.tripFristDriverID, TS.driverBalance, "
								+ " D.driver_empnumber, TS.closetripDate , V.vehicle_registration, "
								+ " TS.closedByTime, D.driver_firstname, D.driver_Lastname, TS.isPendingForTally, D.driver_fathername"
								+ " from TripSheet TS " 
								+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
								+ " INNER JOIN Driver D ON D.driver_id = TS.tripFristDriverID "
								+ " where TS.closetripDate between '"+fromDate+"' AND '"+toDate+"' AND TS.markForDelete = 0 "
								+ " AND TS.companyId = "+companyId+" AND TS.driverBalance <> 0 AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+""
								 ,Object[].class);
				List<Object[]> results = query.getResultList();
				
				if (results != null && !results.isEmpty()) {
					TripSheetExpenseDto select = null;
					for (Object[] vehicle : results) {
						select = new TripSheetExpenseDto();
						select.setTripExpenseID((Long) vehicle[0]);
						select.setTripSheetNumber((Long) vehicle[1]);
						select.setExpenseId((Integer) vehicle[2]);
						select.setExpenseAmount((Double) vehicle[3]);
						if(select.getExpenseAmount() > 0)
							select.setCredit(true);
						else {
							select.setCredit(false);
						}
						
						select.setLedgerName((String) vehicle[4]);
						select.setExpenseName(debitLedger);
						select.setTallyCompanyName(tallyName);
						
						if(vehicle[5] != null)
							select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[5]));//tallyFormat.format((java.util.Date)vehicle[17])
						
						select.setVehicle_registration((String) vehicle[6]);
						
					//select.setPendingForTally((boolean) vehicle[8]);
						
						if(vehicle[8] != null && !((String)vehicle[8]).trim().equals("")) {
							select.setLedgerName(select.getLedgerName()+" "+(String)vehicle[8]);
						}
						
						if(vehicle[9] != null && !((String)vehicle[9]).trim().equals("")) {
							select.setLedgerName(select.getLedgerName()+" "+(String)vehicle[9]);
						}
						if(vehicle[10] != null)
							select.setPendingForTally((boolean) vehicle[10]);
						
						if(vehicle[11] != null && !((String)vehicle[11]).trim().equals("")) {
							select.setLedgerName(select.getLedgerName()+"-"+(String)vehicle[11]);
						}
							
						
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_DRIVER_BALANCE);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						
						select.setVendorName(select.getLedgerName());
						select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[5]));
						
						if(select.getRemark() == null) {
							select.setRemark("--");
						}
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("TS-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
						
						select.setRemark("Driver Balance Amount For TripSheet "+select.getTripSheetNumberStr()
											+" On Vehicle : "+select.getVehicle_registration());
						select.setExpenseFixed("CASH");
						
						tripSheetDto.add(select);
					}
				}
				return tripSheetDto;
				
			} catch (Exception e) {
			throw e;
			}
		}
	/*	public List<TripSheetExpenseDto> getDriverBalanceListForTally(Integer companyId, String fromDate, String toDate, 
				Long tallyCompany, String debitLedger, String tallyName)
				throws Exception {
			List<TripSheetExpenseDto>        	tripSheetDto		= null;
			TypedQuery<Object[]> 				query 				= null;
			try {
				
				tripSheetDto = new ArrayList<TripSheetExpenseDto>();
				query = entityManager.createQuery(
						" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.tripFristDriverID, TS.driverBalance, "
								+ " D.driver_empnumber, TS.closetripDate , V.vehicle_registration, "
								+ " TS.closedByTime, D.driver_Lastname, D.driver_firstname, TS.isPendingForTally, D.driver_fathername"
								+ " from TripSheet TS " 
								+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
								+ " INNER JOIN Driver D ON D.driver_id = TS.tripFristDriverID "
								+ " where TS.closetripDate between '"+fromDate+"' AND '"+toDate+"' AND TS.markForDelete = 0 "
								+ " AND TS.companyId = "+companyId+" AND TS.driverBalance <> 0 AND TS.tripStutesId = "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+""
								 ,Object[].class);
				List<Object[]> results = query.getResultList();
				
				if (results != null && !results.isEmpty()) {
					TripSheetExpenseDto select = null;
					for (Object[] vehicle : results) {
						select = new TripSheetExpenseDto();
						select.setTripExpenseID((Long) vehicle[0]);
						select.setTripSheetNumber((Long) vehicle[1]);
						select.setExpenseId((Integer) vehicle[2]);
						select.setExpenseAmount((Double) vehicle[3]);
						if(select.getExpenseAmount() > 0)
							select.setCredit(true);
						else {
							select.setCredit(false);
						}
						
						select.setLedgerName((String) vehicle[4]);
						select.setExpenseName(debitLedger);
						select.setTallyCompanyName(tallyName);
						
						if(vehicle[5] != null)
							select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[5]));//tallyFormat.format((java.util.Date)vehicle[17])
						
						select.setVehicle_registration((String) vehicle[6]);
						
					//select.setPendingForTally((boolean) vehicle[8]);
						
						if(vehicle[8] != null && !((String)vehicle[8]).trim().equals("")) {
							select.setLedgerName(select.getLedgerName()+" "+(String)vehicle[8]);
						}
						
						if(vehicle[9] != null && !((String)vehicle[9]).trim().equals("")) {
							select.setLedgerName(select.getLedgerName()+" "+(String)vehicle[9]);
						}
						if(vehicle[10] != null)
							select.setPendingForTally((boolean) vehicle[10]);
						
						if(vehicle[11] != null && !((String)vehicle[11]).trim().equals("")) {
							select.setLedgerName(select.getLedgerName()+"-"+(String)vehicle[11]);
						}
							
						
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_DRIVER_BALANCE);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						
						select.setVendorName(select.getLedgerName());
						select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[5]));
						
						if(select.getRemark() == null) {
							select.setRemark("--");
						}
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("TS-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
						
						select.setRemark("Driver Balance Amount For TripSheet "+select.getTripSheetNumberStr()
											+" On Vehicle : "+select.getVehicle_registration());
						select.setExpenseFixed("CASH");
						
						tripSheetDto.add(select);
					}
				}
				return tripSheetDto;
				
			} catch (Exception e) {
			throw e;
			}
		} */
		
		@SuppressWarnings("unchecked")
		@Override
		public List<RouteWiseTripSheetWeightDto> findAllTripSheetRouteWiseDetails(Long tripSheetID, Integer companyId) throws Exception {

			Query query = entityManager.createQuery(
					"SELECT  RW.routeWiseTripSheetWeightId, RW.routeName, RW.actualWeight, RW.scaleWeight, RW.tripSheetId "
					+ "FROM RouteWiseTripSheetWeight AS RW  WHERE RW.companyId= " + companyId + "  AND " + " RW.tripSheetId= "+ tripSheetID + 
					"  AND markForDelete=0");
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			List<RouteWiseTripSheetWeightDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RouteWiseTripSheetWeightDto>();
				RouteWiseTripSheetWeightDto select = null;
				for (Object[] tripweight : results) {
					if (tripweight != null) {
						select = new RouteWiseTripSheetWeightDto();
						select.setRouteWiseTripSheetWeightId((Long) tripweight[0]);
						select.setRouteName((String) tripweight[1]);
						select.setActualWeight((Double) tripweight[2]);
						select.setScaleWeight((Double) tripweight[3]);
						select.setTripSheetId((Long) tripweight[4]);
					}
				Dtos.add(select);
				}
			}
		return Dtos;
		}
		
		@Transactional
		public Double getDriverBalance(long tripsheetId, int companyId)
				throws Exception {
			return tripSheetRepository.getDriverBalance(tripsheetId, companyId);
		}
		
		@Transactional
		public void updateDriverBalance(long tripsheetId, Double driverBalance, int companyId)
				throws Exception {
			entityManager.createQuery(
					"Update TripSheet AS T Set  T.driverBalance = "+driverBalance+" WHERE T.tripSheetID = " + tripsheetId + "  AND companyId = " + companyId+ " ").executeUpdate();
		}
		
		@Override
		public void updateDriverBalance(long tripsheetId, int companyId) throws Exception {
			entityManager.createQuery(
					"Update TripSheet AS T Set  T.driverBalance = tripTotalAdvance - (tripTotalexpense + closeTripAmount)  WHERE T.tripSheetID = " + tripsheetId + "  AND companyId = " + companyId+ " ").executeUpdate();
		}
		
		@Override
		public ValueObject getLastTripSheetDetailsForDriver(ValueObject valueObject) throws Exception {
			String						dispatchDate			= null;
			String						dispatchedToDate		= null;
			String						dispatchTime			= null;
			Timestamp					fromDate				= null;
			Timestamp					toDate					= null;
			TripSheetDto				inBetweenTripSheet		= null;
			boolean						validateOnTripClose		= false;
			String						betweenQuery			= "";
			try {
				dispatchDate			= valueObject.getString("dispatchedByTime");
				dispatchedToDate 		= valueObject.getString("dispatchedToByTime");
				dispatchTime			= valueObject.getString("dispatchTime","");
				
				fromDate				= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchDate, dispatchTime).getTime());
				toDate					= new Timestamp(DateTimeUtility.getDateTimeFromDateTimeString(dispatchedToDate, dispatchTime).getTime());
				
				if(validateOnTripClose == true) {
					betweenQuery = "AND '"+DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' between TS.dispatchedByTime  AND TS.closedByTime  ";
				}else {
					betweenQuery = "AND (('"+DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' between TS.dispatchedByTime  AND TS.closedByTime) OR ('"+DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' between TS.dispatchedByTime  AND TS.closedByTime) OR ('"+DateTimeUtility.getDateFromTimeStamp(fromDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' <= TS.dispatchedByTime AND '"+DateTimeUtility.getDateFromTimeStamp(toDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' >= TS.closedByTime )) ";
				}
				inBetweenTripSheet		= getInBetweentimeTripSheetByDateTimeForDriver(valueObject.getInt("driverId",0), betweenQuery);
				
				if(inBetweenTripSheet != null ) {
					valueObject.put("inBetweenTripSheet", inBetweenTripSheet);
					return valueObject;
				}
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public TripSheetDto getInBetweentimeTripSheetByDateTimeForDriver(Integer driverId, String query) throws Exception {
			try {
				Query queryt = 	null;
					queryt = entityManager.createQuery(
							" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate, TS.tripClosingKM,"
							+ " TS.tripOpeningKM, TS.tripStartDiesel, TS.tripEndDiesel, TS.dispatchedByTime, TS.closedByTime, TS.tripFristDriverID "
							+ " from TripSheet TS "
							+ " WHERE TS.tripFristDriverID= " + driverId+"  "+query+" AND TS.markForDelete = 0 ");
				Query queryt2 = 	null;
				queryt2 = entityManager.createQuery(
						" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate, TS.tripClosingKM,"
						+ " TS.tripOpeningKM, TS.tripStartDiesel, TS.tripEndDiesel, TS.dispatchedByTime, TS.closedByTime, TS.tripSecDriverID "
						+ " from TripSheet TS "
						+ " WHERE TS.tripSecDriverID=  "+driverId+"  "+query+" AND TS.markForDelete = 0 ");
				Object[] driver1 = null;
				Object[] driver2 = null;
				try {
					queryt.setMaxResults(1);
					driver1 = (Object[]) queryt.getSingleResult();
					driver2 = (Object[]) queryt2.getSingleResult();
					
				} catch (NoResultException nre) {
					// Do nothing
				}
				TripSheetDto select = null;
				if (driver1 != null) {
					select = new TripSheetDto();
					select.setTripSheetID((Long) driver1[0]);
					select.setTripSheetNumber((Long) driver1[1]);
					select.setVid((Integer) driver1[2]);
					select.setTripOpenDate(dateFormat.format((java.util.Date) driver1[3]));
					select.setClosetripDate(dateFormat.format((java.util.Date) driver1[4]));
					select.setTripClosingKM((Integer) driver1[5]);
					select.setTripOpeningKM((Integer) driver1[6]);
					if(driver1[7] != null)
						select.setTripStartDiesel((Double) driver1[7]);
					if(driver1[8] != null)
						select.setTripEndDiesel((Double) driver1[8]);
					if(driver1[9] != null)
						select.setDispatchedByTime(dateTimeFormat.format((Timestamp) driver1[9]));
					if(driver1[10] != null)
						select.setClosedByTime(dateTimeFormat.format((Timestamp) driver1[10]));
					select.setDriverId((int)driver1[11]);
				}
				
				if (driver2 != null) {
					select = new TripSheetDto();
					select.setTripSheetID((Long) driver2[0]);
					select.setTripSheetNumber((Long) driver2[1]);
					select.setVid((Integer) driver2[2]);
					select.setTripOpenDate(dateFormat.format((java.util.Date) driver2[3]));
					select.setClosetripDate(dateFormat.format((java.util.Date) driver2[4]));
					select.setTripClosingKM((Integer) driver2[5]);
					select.setTripOpeningKM((Integer) driver2[6]);
					if(driver2[7] != null)
						select.setTripStartDiesel((Double) driver2[7]);
					if(driver2[8] != null)
						select.setTripEndDiesel((Double) driver2[8]);
					if(driver2[9] != null)
						select.setDispatchedByTime(dateTimeFormat.format((Timestamp) driver2[9]));
					if(driver2[10] != null)
						select.setClosedByTime(dateTimeFormat.format((Timestamp) driver2[10]));
					select.setDriverId((int) driver2[11]);
				}
				return select ;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<Driver> getTripDriverList(Integer companyId, Long tripsheetId) throws Exception {
			List<Driver> driver = null;
			try {
				StringBuilder ids = new StringBuilder();
				Query  query = entityManager.createQuery(
						" SELECT  TS.tripFristDriverID,  TS.tripSecDriverID , TS.tripCleanerID "
						+ "FROM  TripSheet AS TS WHERE TS.tripSheetID= "+tripsheetId + " AND companyId= "+companyId);
				List<Object[]> results = null;
				try {
					results = query.getResultList();
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}
				if (results != null && !results.isEmpty()) {
					for(Object[] result : results){
						ids.append(result[0] + "," + result[1]+ ","+ result[2]);
					}
				}
				Query  query2 = entityManager.createQuery(
							" SELECT D.driver_id, D.driver_firstname, driver_Lastname, D.driver_fathername, D.driver_empnumber "
							+ " FROM Driver AS D WHERE D.driver_id in("+ ids + ") AND companyId = "+ companyId );
				List<Object[]> results2 = null;
				try {
					results2 = query2.getResultList();
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}
				driver = new ArrayList<>();
				Driver  dto = null;
				if (results2 != null && !results2.isEmpty()) {
					for(Object[] result : results2){
						dto = new Driver();
						dto.setDriver_id((Integer)result[0]);
						dto.setDriver_firstname(result[4] + " "+ result[1] + " "+ result[2] + "-"+ result[3]);
						driver.add(dto);
					}
				}
				return driver;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public Long getTripSheetClosedTodayCount(String startDate, String endDate,Integer companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					" SELECT COUNT(T.tripSheetID) "
							+ " From TripSheet as T "
							+ " WHERE T.closetripDate between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId						
							+ " AND T.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+", "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+") AND T.markForDelete = 0 " );
			
			
			Long count = (long) 0;
			try {
				
				if((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
				}
				
			} catch (NoResultException nre) {
				}

			return count;
		}
		
		@Override
		public List<TripSheetDto> getTotalTripSheetClosedTodayDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, V.vehicle_registration, VG.vGroup, V.vehicleGroupId, TR.routeID,"
							+ " TR.routeName, TS.tripOpenDate, TS.closetripDate, TS.tripStutesId "
							+ " from TripSheet TS " 
							+ " INNER JOIN Vehicle AS V ON V.vid = TS.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
							+ " where TS.closetripDate between '"+startDate+"' AND '"+endDate+"' AND TS.companyId = " + companyId + " "
							+ " AND TS.tripStutesId IN ("+TripSheetStatus.TRIP_STATUS_CLOSED+", "+TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED+")  AND TS.markForDelete = 0 ORDER BY TS.tripSheetID DESC ",
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
					select.setTripOpenDate(dateFormat_Name.format((java.util.Date) vehicle[8]));
					select.setClosetripDate(dateFormat_Name.format((java.util.Date) vehicle[9]));
					select.setTripSheetCurrentStatus(TripSheetStatus.getTripSheetStatusName((short) vehicle[10]));
					
					Dtos.add(select);
				}
			}
			return Dtos;
			
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<TripSheetExpenseDto> findAllCashlessTripAdavnceList(String fromDate, String toDate, int companyId,
				String tallyCompany) throws Exception {
			// TODO Auto-generated method stub
			
			Query query = entityManager.createQuery(
					" SELECT TA.tripAdvanceID, T.tripSheetNumber, TA.AdvanceAmount,TA.created, TA.advanceRefence, "
					+ " TA.paymentTypeId, BM.bankName, BA.accountNumber, "
					+ " T.tallyCompanyId, TA.isPendingForTally , VH.vehicle_registration,"
					+ " VH.vid, T.tripSheetID, VH.ledgerName, D.driver_firstname, D.driver_Lastname, BP.transactionNumber, BP.upiId, D.driver_empnumber, D.driver_fathername FROM TripSheetAdvance TA "
					+ " LEFT JOIN TripSheet T ON T.tripSheetID = TA.tripsheet.tripSheetID AND T.markForDelete = 0 "
					+ " INNER JOIN Vehicle VH ON VH.vid = T.vid "
					+ " LEFT JOIN BankPayment BP ON BP.moduleId = TA.tripAdvanceID AND BP.moduleIdentifier = "+ ModuleConstant.TRIP_ADVANCE + " "
								+ "	AND BP.markForDelete = 0 "
					+ " LEFT JOIN BankAccount BA ON BA.bankAccountId = BP.bankAccountId "
					+ " LEFT JOIN BankMaster BM ON BM.bankId = BA.bankId "
					+ " LEFT JOIN Driver D ON D.driver_id = T.tripFristDriverID"
					+ " WHERE TA.created between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND TA.companyId = "+companyId+" AND TA.markForDelete = 0 AND TA.paymentTypeId NOT IN("+PaymentTypeConstant.PAYMENT_TYPE_CASH + ","+ PaymentTypeConstant.PAYMENT_TYPE_CREDIT+ ") ");
			
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			List<TripSheetExpenseDto> Dtos = null;
			
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetExpenseDto>();
				TripSheetExpenseDto select;
				for (Object[] tripAdvance : results) {
					if (tripAdvance != null) {
						select = new TripSheetExpenseDto();
						select.setTripExpenseID((Long)tripAdvance[0]);
						select.setTripSheetNumber((Long)tripAdvance[1]);
						select.setExpenseAmount((Double) tripAdvance[2]);
						select.setCreatedStr(dateFormat.format(tripAdvance[3]));
						select.setVoucherDate(dateFormat.format(tripAdvance[3]));
						select.setExpenseFixedId((short) tripAdvance[5]);
						if(tripAdvance[6] != null)
							select.setLedgerName(tripAdvance[6]+"  A/C NO-"+tripAdvance[7]);
						else
							select.setLedgerName("Punjab National Bank A/C NO-11091131000034");
						select.setTallyCompanyId((Long) tripAdvance[8]);
						select.setPendingForTally((boolean) tripAdvance[9]);
						select.setTallyCompanyName(tallyCompany);
						select.setVendorName(select.getLedgerName());
						select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
						select.setRemark("Payment Type : "+PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId())+" Trip Sheet Advance TS- "+select.getTripSheetNumber()+ "  Driver Name : " +tripAdvance[18] +" " + tripAdvance[14] +" " + tripAdvance[15] +"-"+tripAdvance[19]+" Vehicle : "+ tripAdvance[10] + " UPI ID / Transction ID " + tripAdvance[16] +" / "+ tripAdvance[17] );
						select.setExpenseTypeId(TallyVoucherTypeContant.TRIP_ADVANCE_PAYMENT);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						if(select.getVendorName() == null ) 
							select.setVendorName("--");
						if(select.getTallyCompanyName() == null)
							select.setTallyCompanyName("--");
						select.setVehicle_registration((String) tripAdvance[10]);
						select.setVid((Integer) tripAdvance[11]);
						select.setTripSheetId((Long) tripAdvance[12]);
						select.setTripSheetNumberStr("TS-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
						select.setExpenseName("Trip Advance");
						select.setCredit(true);
						
						Dtos.add(select);
					}
				}
			}
			return Dtos;
		}
		
		
		@SuppressWarnings("unchecked")
		public List<TripSheetDto> FinalAllTodayTripSheet(Integer companyId, String date) {
			List<TripSheetDto>   		triplist 	= null;
			TripSheetDto   				trip  		= null;
			try {
			Query query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TR.routeName, D1.driver_firstname,"
					+ " D1.driver_Lastname, D1.driver_fathername  FROM TripSheet AS TS"
					+ " LEFT JOIN TripRoute TR ON TR.routeID = TS.routeID "
					+ " LEFT JOIN Driver D1 ON D1.driver_id = TS.tripFristDriverID "
				    + " WHERE FUNCTION('DATE', TS.created) = '"+date+"' AND TS.companyId =" +companyId + "  AND TS.markForDelete = 0");
			
			    List<Object[]> results = null;
				results = query.getResultList();
				if (results != null && !results.isEmpty()) {
					triplist = new ArrayList<TripSheetDto>();
					for (Object[] tripsheet : results) {	
						trip       =  new TripSheetDto();
						trip.setTripSheetNumber((Long) tripsheet[1]);
						if(tripsheet[2] !=null)
						   trip.setTRIP_ROUTE_NAME((String) tripsheet[2]);
						else
							trip.setTRIP_ROUTE_NAME("--");
						trip.setTripFristDriverName(tripsheet[3] + " " + tripsheet[4] + "-" + tripsheet[5]);
						triplist.add(trip);
					}
				}	
			}catch(Exception e) {
				
			}
			return triplist;
		}

		@Override
		public String getTripSheetCreatedMailBody(List<TripSheetDto> triplist, String todaysDate) {
			// TODO Auto-generated method stub
			StringBuilder htmlTable = new StringBuilder();
			
			htmlTable.append("<html><body>");
			htmlTable.append("<p>Date : " + todaysDate +  " </p><br>");
			htmlTable.append("<p>Hi there! We are pleased to provide you with the following records:</p>");
			htmlTable.append("<br>");
			htmlTable.append("<p style='font-weight: bold; font-size: 18px;'>Here are the Daily TripSheet Created Records : </p>");
			htmlTable.append("<table style='width: 100%; border-collapse: collapse;'>\n");

			htmlTable.append("<tr style='background-color: #f2f2f2; text-align: center;'>");
			htmlTable.append("<th style='padding: 13px; border: 1px solid #333; font-size: 16px; background-color: #ccc; color: #333;text-align: center;'>TS No.</th>");
			htmlTable.append("<th style='padding: 13px; border: 1px solid #333; font-size: 16px; background-color: #ccc; color: #333;text-align: center;'>Driver Name</th>");
			htmlTable.append("<th style='padding: 13px; border: 1px solid #333; font-size: 16px; background-color: #ccc; color: #333;text-align: center;'>Route Name</th>");
			htmlTable.append("</tr>\n");

			for (int i = 0; i < triplist.size(); i++) {
			    String rowColor = (i % 2 == 0) ? "#f9f9f9" : "#e5e5e5";
			    htmlTable.append("<tr style='background-color: " + rowColor + "; text-align: center;'>");
			    htmlTable.append("<td style='padding: 12px; border: 1px solid #333; color: #333; font-size: 14px;'>TS-" + triplist.get(i).getTripSheetNumber() + "</td>");
			    htmlTable.append("<td style='padding: 12px; border: 1px solid #333; color: #333; font-size: 14px;'>" + triplist.get(i).getTripFristDriverName() + "</td>");
			    htmlTable.append("<td style='padding: 12px; border: 1px solid #333; color: #333; font-size: 14px;'>" + triplist.get(i).getTRIP_ROUTE_NAME() + "</td>");
			    htmlTable.append("</tr>\n");
			}
			// End the table
			htmlTable.append("</table>");
			htmlTable.append("<br><br>");
			htmlTable.append("<p>If you have any Questions or Require Further Information, Please do not Hesitate to contact us. Thank you for your continued support.</p>");
			htmlTable.append("<p>Thanks & Regards.</p>");
			htmlTable.append("</body></html>");
	       
			String tableHtml = htmlTable.toString();
			
			return tableHtml;
		}
		
		@Override
		public TripSheet getTripByTripsheetID(Long trisheetId) throws Exception {
			// TODO Auto-generated method stub
			return   tripSheetRepository.getTripSheetByTripsheetId(trisheetId);
		}
		
		@Override
		public TripSheet getTripsheetByLhpvId(Integer lhpvId) throws Exception {
			// TODO Auto-generated method stub
			return   tripSheetRepository.getTripSheetByLHPV(lhpvId);
		}
		
		@SuppressWarnings("deprecation")
		@Transactional
		public Page<TripSheet> getPaginatedTripSheetsByVehicleId(Integer vid, Integer pageNumber) throws Exception {
			try {
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
				PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "vid");

					return tripSheetRepository.getDeployment_Page_Vehicle_Status(vid, userDetails.getCompany_id(),
							request);

			} catch (Exception e) {
				throw e;
			}
		}
		
		//@Transactional
		public Long countTotalTripSheet(Integer vid,  Integer companyId) throws Exception {
		
			return tripSheetRepository.countTripSheetonVehicle(vid,companyId);
		}

}

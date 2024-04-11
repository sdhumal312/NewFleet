package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.DriverAdvanceType;
import org.fleetopgroup.constant.DriverAttandancePointStatus;
import org.fleetopgroup.constant.DriverAttandancePointType;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.FuelConfigurationConstants;
import org.fleetopgroup.constant.GPSConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TollApiConfiguration;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetConfigurationConstant;
import org.fleetopgroup.constant.TripSheetFixedExpenseType;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleFuelType;
import org.fleetopgroup.constant.VehicleGPSConfiguratinConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.bl.UreaEntriesBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.dao.DriverTripSheetBalanceRepository;
import org.fleetopgroup.persistence.dao.FuelRepository;
import org.fleetopgroup.persistence.dao.TripSheetExpenseRepository;
import org.fleetopgroup.persistence.dao.TripSheetIncomeRepository;
import org.fleetopgroup.persistence.dao.UreaEntriesRepository;
import org.fleetopgroup.persistence.dao.VehicleExtraDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleGroupWiseCompanyNameRepository;
import org.fleetopgroup.persistence.dto.BusBookingDetailsDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.DriverHaltDto;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.dto.DriverTripSheetPointDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.LoadingSheetToTripSheetDto;
import org.fleetopgroup.persistence.dto.RefreshmentEntryDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TollExpensesDetailsDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleMandatoryDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverAttendance;
import org.fleetopgroup.persistence.model.DriverHalt;
import org.fleetopgroup.persistence.model.DriverSalaryInsertionDetails;
import org.fleetopgroup.persistence.model.DriverTripSheetBalance;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.LHPVDetails;
import org.fleetopgroup.persistence.model.SubCompany;
import org.fleetopgroup.persistence.model.LoadingSheetToTripSheet;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.model.TripIncome;
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
import org.fleetopgroup.persistence.model.TripSheetOptionsExtra;
import org.fleetopgroup.persistence.model.TripSheetSequenceCounter;
import org.fleetopgroup.persistence.model.TripSheetToLhpvDetails;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleExtraDetails;
import org.fleetopgroup.persistence.model.VehicleGroupWiseCompanyName;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.report.dao.IServiceEntriesDao;
import org.fleetopgroup.persistence.service.VehicleProfitAndLossIncomeTxnCheckerService;
import org.fleetopgroup.persistence.service.VehicleProfitAndLossTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IBusBookingDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverAttendanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverHaltService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryInsertionDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.ILHPVDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ILoadingSheetToTripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IRefreshmentEntryService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISubCompanyService;
import org.fleetopgroup.persistence.serviceImpl.ITicketIncomeApiService;
import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ITripCommentService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.ITripIncomeService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteExpenseRangeService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetToLhpvDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ITripsheetSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleMandatoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Controller
public class TripSheetController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired private ITripSheetService 										tripSheetService;
	@Autowired private ITripSheetHistoryService 								tripSheetHistoryService;
	@Autowired private IVehicleService 											vehicleService;
	@Autowired private IDriverService 											driverService;
	@Autowired private ITripRouteService 										TripRouteService;
	@Autowired private ITripCommentService 										tripCommentService;
	@Autowired private IUserProfileService 										userProfileService;
	@Autowired private ITripExpenseService 										TripExpenseService;
	@Autowired private ITripIncomeService 										TripIncomeService;
	@Autowired private IServiceReminderService 									ServiceReminderService;
	@Autowired private IVehicleOdometerHistoryService 							vehicleOdometerHistoryService;
	@Autowired private IDriverAttendanceService 								DriverAttendanceService;
	@Autowired private IDriverHaltService 										driverHaltService;
	@Autowired private IVehicleMandatoryService 								vehicleMandatoryService;
	@Autowired private IFuelService 											fuelService;
	@Autowired private ITripsheetSequenceService 								tripsheetSequenceService;
	@Autowired private IVehicleGroupService 									vehicleGroupService;
	@Autowired private IWorkOrdersService 										workOrdersService;
	@Autowired private ICompanyConfigurationService 							companyConfigurationService;
	@Autowired private IVehicleDocumentService									vehicleDocumentService;
	@Autowired private VehicleProfitAndLossTxnCheckerService					vehicleProfitAndLossTxnCheckerService;
	@Autowired private	IVehicleProfitAndLossService							vehicleProfitAndLossService;
	@Autowired private	VehicleProfitAndLossIncomeTxnCheckerService				vehicleProfitAndLossIncomeTxnCheckerService;
	@Autowired private ITollExpensesDetailsService     							tollExpensesDetailsService;
	@Autowired private	ILHPVDetailsService										lhpvDetailsService;
	@Autowired private	IDriverSalaryInsertionDetailsService					driverSalaryInsertionDetailsService;
	@Autowired private	IVehicleGPSDetailsService								vehicleGPSDetailsService;
	@Autowired private	ITripSheetToLhpvDetailsService							tripSheetToLhpvDetailsService;
	@Autowired private	FuelRepository											FuelRepository;
	@Autowired private	UreaEntriesRepository									UreaEntriesRepository;
	@Autowired private	IUreaEntriesService										UreaEntriesService;
	@Autowired private	TripSheetIncomeRepository								TripSheetIncomeRepository;
	@Autowired private	TripSheetExpenseRepository								TripSheetExpenseRepository;
	@Autowired private	ITicketIncomeApiService									TicketIncomeApiService;
	@Autowired private	IVehicleAgentTxnCheckerService							vehicleAgentTxnCheckerService;
	@Autowired private	IVehicleAgentIncomeExpenseDetailsService				vehicleAgentIncomeExpenseDetailsService;
	@Autowired private	IBusBookingDetailsService								busBookingDetailsService;	
	@Autowired private	IDriverSalaryAdvanceService 							DriverSalaryAdvanceService;
	@Autowired private	VehicleExtraDetailsRepository 							VehicleExtraDetailsRepository;
	@Autowired private	IRenewalReminderService									renewalReminderService;
	@Autowired private	IServiceEntriesService									serviceEntriesService;
	@Autowired private	IServiceEntriesDao										serviceEntriesDao;
	@Autowired private	IRefreshmentEntryService								refreshmentEntryService;
	@Autowired private 	ITripRouteExpenseRangeService							tripRouteExpenseRangeService;
	@Autowired private	VehicleGroupWiseCompanyNameRepository					vehicleGroupWiseCompanyNameRepository;
	@Autowired private  DriverTripSheetBalanceRepository						driverTripSheetBalanceRepo;
	@Autowired private IUreaEntriesService 										ureaEntriesService;
	@Autowired private ISubCompanyService										subCompanyService;
	@Autowired private	FuelRepository						                    fuelRepository;
	

	@Autowired private ILoadingSheetToTripSheetService							loadingSheetToTripSheetService;
	FuelBL 									fuelBL 						= new FuelBL();
	TripSheetBL 							tripSheetBL					= new TripSheetBL();
	VehicleOdometerHistoryBL 				vehicleOdometerHistoryBL	= new VehicleOdometerHistoryBL();
	VehicleBL 								vehicleBL					= new VehicleBL();
	DriverBL 								driverBL					= new DriverBL();
	VehicleProfitAndLossTxnCheckerBL		txnCheckerBL 				= new VehicleProfitAndLossTxnCheckerBL();	
	UreaEntriesBL							ureaEntriesBL 				= new UreaEntriesBL();	
	VehicleAgentTxnCheckerBL				agentTxnCheckerBL			= new VehicleAgentTxnCheckerBL();
	
	DecimalFormat tofixedTwo = new DecimalFormat("#.##");

	public java.util.Date removeTime(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	SimpleDateFormat dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat driverAttendanceFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormatTime 		= new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat dateTimeFormat 		= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat SQL_DATE_FORMAT 		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat date1 					= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat date2 					= new SimpleDateFormat("dd-MM-yyyy");

	public static final long TRIP_SHEET_DEFALAT_VALUE 		= 0L;
	public static final Integer TRIP_SHEET_DEFALAT_ZERO	 	= 0;
	public static final Double TRIP_SHEET_DEFALAT_AMOUNT 	= 0.0;

	// Note: This /searchTripSheet Controller is Search TripSheet Details in all
	// Status
	@RequestMapping(value = "/searchTripSheet", method = RequestMethod.POST)
	public ModelAndView searchTripSheet(@RequestParam("tripStutes") final String TripStutes,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			model.put("TripSheet", tripSheetBL.prepareListofTripSheet(tripSheetService.SearchTripSheet(TripStutes)));

		} catch (Exception e) {

			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newTripSheet", model);
	}

	@RequestMapping(value = "/searchTripSheetShow", method = RequestMethod.POST)
	public ModelAndView searchTripSheetShow(@RequestParam("tripStutes") final Long tripSheetNumber,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 	model 						= new HashMap<String, Object>();
		CustomUserDetails 		userDetails 				= null;
		List<TripSheetDto>	 	tripList 					= null;
		TripSheetDto			trip						= null;
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int tripCount	=	tripSheetService.countTripSheetByNumber(tripSheetNumber, userDetails.getCompany_id());

			if(tripCount == 1) {
				trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetByNumber(tripSheetNumber, userDetails));
				return new ModelAndView("redirect:/showTripSheet.in?tripSheetID="+trip.getTripSheetID(), model);

			}else {
				tripList	= tripSheetBL.prepareListofTripSheet(tripSheetService.Get_TripSheetListByNumber(tripSheetNumber, userDetails.getCompany_id()));

				model.put("TripSheet", tripList);
				return new ModelAndView("newTripSheet", model);
			}


		} catch (NullPointerException e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/newTripSheetEntries.in", model);
		} catch (Exception e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/newTripSheetEntries.in", model);
		} finally {
			userDetails = null;
		}
	}

	// Note : Show Vehicle To Current TripSheet
	@RequestMapping(value = "/searchVehCurTSShow", method = RequestMethod.POST)
	public ModelAndView searchTripSheetShow(@RequestParam("vid") final Integer tripSheetNumber,
			final HttpServletRequest request) throws Exception {

		List<TripSheetDto> 				tripSheet		          = null;
		Map<String, Object> 			model			          = new HashMap<String, Object>();
		ModelAndView 					map 			          = new ModelAndView("SearchVehicleTS");
		CustomUserDetails 				userDetails 	          = null;
		HashMap<String, Object> 		configuration	          = null;
		boolean					 		hideClosedTripSheet	      = false;

		try {	
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
			map.addObject("permissions", permission);
			hideClosedTripSheet	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.HIDE_CLOSED_TRIPSHEET, false);
			tripSheet = tripSheetService.Get_Vehicle_Current_TripSheet_Id(tripSheetNumber, userDetails.getCompany_id());

			List<TripSheetDto> tripSheetFinal = null;
			if(hideClosedTripSheet && !permission.contains(new SimpleGrantedAuthority("SHOW_TRIPSHEET_CLOSE_STATUS"))){
				for(TripSheetDto dto  : tripSheet) {
					if (dto.getTripStutesId() != TripSheetStatus.TRIP_STATUS_CLOSED && dto.getTripStutesId() != TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED){
						if(tripSheetFinal == null) {
							tripSheetFinal = new ArrayList<TripSheetDto>();
						}
						tripSheetFinal.add(dto);
					} 
				}

			} else {
				tripSheetFinal = tripSheet;

			}
			model.put("TripSheet", tripSheetBL.prepareListofTripSheet(tripSheetFinal));
			model.put("TripSheetAdvance", tripSheetBL.Total_Amount_TRIPSHEET_Advance(tripSheetFinal));
			model.put("configuration", configuration);
			//model.put("TripSheet", tripSheetFinal);
			return new ModelAndView("newTripSheet", model);
		}catch (Exception e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/newTripSheetEntries.in", model);
		} finally {
			userDetails = null;
		}
		//return new ModelAndView("newTripSheet", model);
	}


	// Note: This /searchTSDispatch Controller is Search TripSheet Only in SAVED
	// Status
	@RequestMapping(value = "/searchTSDispatch", method = RequestMethod.POST)
	public ModelAndView searchTSDispatch(@RequestParam("vid") final String vid, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails 				userDetails 	          = null;
		HashMap<String, Object> 		configuration	          = null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			model.put("TripSheet", tripSheetBL.prepareListofTripSheet(tripSheetService.Search_TripSheet_Status(vid, TripSheetStatus.TRIP_STATUS_SAVED)));
			model.put("configuration", configuration);
		} catch (Exception e) {

			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newDispatchTripSheet", model);
	}

	// Note: This /searchTSManage Controller is Search TripSheet Only in
	// DISPATCHED Status
	@RequestMapping(value = "/searchTSManage", method = RequestMethod.POST)
	public ModelAndView searchTSManage(@RequestParam("vid") final String vid, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			model.put("TripSheet", tripSheetBL.prepareListofTripSheet(tripSheetService.Search_TripSheet_Status(vid, TripSheetStatus.TRIP_STATUS_DISPATCHED)));

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newManageTripSheet", model);
	}

	// Note: This /searchTSClose Controller is Search TripSheet Only in
	// DISPATCHED Status
	@RequestMapping(value = "/searchTSClose", method = RequestMethod.POST)
	public ModelAndView searchTSClose(@RequestParam("vid") final String vid, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			model.put("TripSheet", tripSheetBL.prepareListofTripSheet(tripSheetService.Search_TripSheet_Status(vid, TripSheetStatus.TRIP_STATUS_DISPATCHED)));

		} catch (Exception e) {

			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newCloseTripSheet", model);
	}

	// Note: This /searchTSPayment Controller is Search TripSheet Only in CLOSED
	// Status
	@RequestMapping(value = "/searchTSPayment", method = RequestMethod.POST)
	public ModelAndView searchTSPayment(@RequestParam("vid") final String vid, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<TripSheetDto> 				tripSheet		          = null;
		ModelAndView 					map 			          = new ModelAndView("SearchVehicleTS");
		CustomUserDetails 				userDetails 	          = null;
		HashMap<String, Object> 		configuration	          = null;
		boolean					 		hideClosedTripSheet	      = false;

		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
			map.addObject("permissions", permission);


			hideClosedTripSheet	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.HIDE_CLOSED_TRIPSHEET, false);
			tripSheet = tripSheetBL.prepareListofTripSheet(tripSheetService.Search_TripSheet_Status(vid, TripSheetStatus.TRIP_STATUS_CLOSED));
			List<TripSheetDto> tripSheetFinal = null;
			if(hideClosedTripSheet && !permission.contains(new SimpleGrantedAuthority("SHOW_TRIPSHEET_CLOSE_STATUS"))){
				for(TripSheetDto dto  : tripSheet) {
					if (dto.getTripStutesId() != TripSheetStatus.TRIP_STATUS_CLOSED && dto.getTripStutesId() != TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED){
						if(tripSheetFinal == null) {
							tripSheetFinal = new ArrayList<TripSheetDto>();
						}
						tripSheetFinal.add(dto);
					} 

				}
			} else {
				tripSheetFinal = tripSheet;

			}
			model.put("TripSheet", tripSheetFinal);

		} catch (Exception e) {

			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newTripSheetAccount", model);
	}

	// Note: This /searchTSClosedAC Controller is Search TripSheet Only in A/C
	// CLOSED Status
	@RequestMapping(value = "/searchTSClosedAC", method = RequestMethod.POST)
	public ModelAndView searchTSClosedAC(@RequestParam("vid") final String vid, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			model.put("TripSheet", tripSheetBL.prepareListofTripSheet(tripSheetService.Search_TripSheet_Status(vid, TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED)));

		} catch (Exception e) {

			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newTripSheetClosedAccount", model);
	}

	// Note: This /newTripSheet Controller is show TripSheet Current Date
	// Entries TripSheet
	@RequestMapping(value = "/newTripSheet", method = RequestMethod.GET)
	public ModelAndView NewVehicleType(@ModelAttribute("command") TripSheet TripSheetDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration				= null;
		CustomUserDetails 			userDetails				 	= null;
		ModelAndView 				map 			          	= new ModelAndView();
		boolean					 	hideClosedTripSheet	      	= false;

		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);

			model.put("TripSheetCount", tripSheetService.countTripSheet(userDetails));
			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
			map.addObject("permissions", permission);
			hideClosedTripSheet	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.HIDE_CLOSED_TRIPSHEET, false);
			List<TripSheetDto> tripSheet = tripSheetService.listTodayTripSheet(ft.format(currentDate), userDetails);
			List<TripSheetDto> tripSheetFinal = null;
			if(hideClosedTripSheet && !permission.contains(new SimpleGrantedAuthority("SHOW_TRIPSHEET_CLOSE_STATUS"))){
				if(tripSheet != null) {
					for(TripSheetDto dto  : tripSheet) {
						if (dto.getTripStutesId() != TripSheetStatus.TRIP_STATUS_CLOSED && dto.getTripStutesId() != TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED){
							if(tripSheetFinal == null) {
								tripSheetFinal = new ArrayList<TripSheetDto>();
							}
							tripSheetFinal.add(dto);
						} 
					}
				}
			} else {
				tripSheetFinal = tripSheet;
			}
			model.put("TripSheet", tripSheetBL.prepareListofTripSheet(tripSheetFinal));
			model.put("configuration", configuration);
			model.put("TripSheetAdvance", tripSheetBL.Total_Amount_TRIPSHEET_Advance(tripSheetFinal));

			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			String DayOne = SQL_DATE_FORMAT.format(date);
			String DayTwo = "", DayThree = "", DayFour = "", DayFive = "";
			for (int i = 0; i < 4; i++) {
				cal.add(Calendar.DAY_OF_MONTH, -1);
				date = cal.getTime();
				switch (i) {
				case 0:
					DayTwo = SQL_DATE_FORMAT.format(date);
					break;
				case 1:

					DayThree = SQL_DATE_FORMAT.format(date);
					break;
				case 2:
					DayFour = SQL_DATE_FORMAT.format(date);
					break;
				case 3:
					DayFive = SQL_DATE_FORMAT.format(date);
					break;

				}
			}

			model.put("DayFive", DayFive);
			model.put("DayOne", DayOne);
			model.put("DayTwo", DayTwo);
			model.put("DayThree", DayThree);
			model.put("DayFour", DayFour);
			Object[] count = tripSheetService.count_TOTAL_TRIPSHEET_FIVEDAYS(DayOne, DayTwo, DayThree, DayFour, DayFive,
					userDetails);
			model.put("DayFive_Count", count[0]);
			model.put("DayOne_Count", count[1]);
			model.put("DayTwo_Count", count[2]);
			model.put("DayThree_Count", count[3]);
			model.put("DayFour_Count", count[4]);


		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newTripSheet", model);
	}

	// Note: This /newTripSheet Controller is show TripSheet Current Date
	// Entries TripSheet
	@RequestMapping(value = "/DATETS", method = RequestMethod.GET)
	public ModelAndView DATETS(@RequestParam("DATE") final String Searchdate, final HttpServletRequest request) {
		Map<String, Object> 			model 					  = new HashMap<String, Object>();
		CustomUserDetails 				userDetails 			  = null;
		ModelAndView 					map 			          = new ModelAndView("SearchVehicleTS");
		HashMap<String, Object> 		configuration	          = null;
		boolean					 		hideClosedTripSheet	      = false; 
		List<TripSheetDto>  			trip 				      = null;
		List<TripSheetDto>  			tripSheet 				  = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			trip = tripSheetService.listTodayTripSheet(Searchdate, userDetails);
			tripSheet = tripSheetBL.prepareListofTripSheet(trip);
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
			map.addObject("permissions", permission);

			hideClosedTripSheet	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.HIDE_CLOSED_TRIPSHEET, false);
			List<TripSheetDto> tripSheetFinal = null;
			if(hideClosedTripSheet && !permission.contains(new SimpleGrantedAuthority("SHOW_TRIPSHEET_CLOSE_STATUS"))){
				if(tripSheet!=null) {
					for(TripSheetDto dto  : tripSheet) {
						if (dto.getTripStutesId() != TripSheetStatus.TRIP_STATUS_CLOSED && dto.getTripStutesId() != TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED){
							if(tripSheetFinal == null) {
								tripSheetFinal = new ArrayList<TripSheetDto>();
							}
							tripSheetFinal.add(dto);
						} 

					} 
				}
			}else {
				tripSheetFinal = tripSheet;

			}

			model.put("TripSheetCount", tripSheetService.countTripSheet(userDetails));

			model.put("TripSheet", tripSheetFinal);
			model.put("configuration", configuration);
			model.put("TripSheetAdvance", tripSheetBL.Total_Amount_TRIPSHEET_Advance(tripSheet));



			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			String DayOne = SQL_DATE_FORMAT.format(date);
			String DayTwo = "", DayThree = "", DayFour = "", DayFive = "";
			for (int i = 0; i < 4; i++) {
				cal.add(Calendar.DAY_OF_MONTH, -1);
				date = cal.getTime();
				switch (i) {
				case 0:
					DayTwo = SQL_DATE_FORMAT.format(date);
					break;
				case 1:

					DayThree = SQL_DATE_FORMAT.format(date);
					break;
				case 2:
					DayFour = SQL_DATE_FORMAT.format(date);
					break;
				case 3:
					DayFive = SQL_DATE_FORMAT.format(date);
					break;

				}
			}

			model.put("DayFive", DayFive);
			model.put("DayOne", DayOne);
			model.put("DayTwo", DayTwo);
			model.put("DayThree", DayThree);
			model.put("DayFour", DayFour);
			Object[] count = tripSheetService.count_TOTAL_TRIPSHEET_FIVEDAYS(DayOne, DayTwo, DayThree, DayFour, DayFive,
					userDetails);

			model.put("DayFive_Count", count[0]);
			model.put("DayOne_Count", count[1]);
			model.put("DayTwo_Count", count[2]);
			model.put("DayThree_Count", count[3]);
			model.put("DayFour_Count", count[4]);

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("newTripSheet", model);
	}

	// Note: This /dispatchTripSheet/1 Controller is show TripSheet SAVED Status
	// Entries In List
	@RequestMapping(value = "/dispatchTripSheet/{pageNumber}", method = RequestMethod.GET)
	public String dispatchTripSheet(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails 			userDetails		= null;
		HashMap<String, Object> 	configuration	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			model.addAttribute("configuration", configuration);
			Page<TripSheet> page = tripSheetService.getDeployment_Page_TripSheet(TripSheetStatus.TRIP_STATUS_SAVED,
					pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TripDispatch", page.getTotalElements());

			List<TripSheetDto> pageList = tripSheetBL.prepareListofTripSheet(tripSheetService.listTrip_Page_Status(TripSheetStatus.TRIP_STATUS_SAVED, pageNumber, userDetails));

			model.addAttribute("TripSheet", pageList);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("trip Page:", e);
			e.printStackTrace();
		}
		return "newDispatchTripSheet";
	}

	// Note: This /manageTripSheet/1 Controller is show TripSheet DISPATCHED
	// Status Entries In List
	@RequestMapping(value = "/manageTripSheet/{pageNumber}", method = RequestMethod.GET)
	public String manageTripSheet(@PathVariable Integer pageNumber, Model model) throws Exception {
		HashMap<String, Object> 	configuration	= null;
		CustomUserDetails 			userDetails 	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			model.addAttribute("configuration", configuration);
			Page<TripSheet> page = tripSheetService.getDeployment_Page_TripSheet(TripSheetStatus.TRIP_STATUS_DISPATCHED,
					pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TripManage", page.getTotalElements());
			List<TripSheetDto> pageList = tripSheetBL.prepareListofTripSheet(tripSheetService.listTrip_Page_Status(TripSheetStatus.TRIP_STATUS_DISPATCHED, pageNumber, userDetails));

			model.addAttribute("TripSheet", pageList);

		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("trip Page:", e);
			e.printStackTrace();
		}
		return "newManageTripSheet";
	}

	// Note: This /closeTripSheet/1 Controller is show TripSheet DISPATCHED
	// Status Entries In List
	@RequestMapping(value = "/closeTripSheet/{pageNumber}", method = RequestMethod.GET)
	public String closeTripSheet(@PathVariable Integer pageNumber, Model model) throws Exception {
		HashMap<String, Object> 	configuration	= null;
		CustomUserDetails userDetails = null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			model.addAttribute("configuration", configuration);
			Page<TripSheet> page = tripSheetService.getDeployment_Page_TripSheet(TripSheetStatus.TRIP_STATUS_DISPATCHED,
					pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TripClose", page.getTotalElements());
			List<TripSheetDto> pageList = tripSheetBL.prepareListofTripSheet(tripSheetService.listTrip_Page_Status(TripSheetStatus.TRIP_STATUS_DISPATCHED, pageNumber, userDetails));

			model.addAttribute("TripSheet", pageList);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("trip Page:", e);
			e.printStackTrace();
		}
		return "newCloseTripSheet";
	}

	// Note: This /addTripSheet Controller is Create New TripSheet Page
	@RequestMapping(value = "/addTripSheet", method = RequestMethod.GET)
	public ModelAndView addVehicleType(@ModelAttribute("command") TripSheetDto TripSheetDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration		= null;
		HashMap<String, Object> 	gpsConfiguration	= null;
		List<LHPVDetails>			lhpvDetails			= null;
		Vehicle						vehicle				= null;
		BusBookingDetailsDto		bookingDetailsDto	= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			model.put("paidBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
			model.put("paidById", userDetails.getId());
			model.put("place", Orderingname.getBranch_name());
			model.put("placeId", Orderingname.getBranch_id());
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, 
					companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
							PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, false));

			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, 
					companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
							PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, false));
			ObjectMapper objectMapper = new ObjectMapper();
			model.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));

			model.put("configuration", configuration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("allowGPSIntegration", (boolean)gpsConfiguration.get("allowGPSIntegration"));
			model.put("allowITSGatewayDriverDetails",(boolean) configuration.get("allowITSGatewayDriverDetails"));
			
			if(TripSheetDto.getBusBookingDetailsId() != null && TripSheetDto.getBusBookingDetailsId() > 0) {
				bookingDetailsDto	=	busBookingDetailsService.getBusBookingDetails(TripSheetDto.getBusBookingDetailsId());
				if(bookingDetailsDto != null) {
					bookingDetailsDto.setTripStartDateStr(dateFormat.format(bookingDetailsDto.getTripStartDateTime()));
					bookingDetailsDto.setTripEndDateStr(dateFormat.format(bookingDetailsDto.getTripEndDateTime()));
					model.put("busBookingDate", bookingDetailsDto.getTripStartDateStr()+"  to  "+bookingDetailsDto.getTripEndDateStr());
				}
			}
			
			model.put("busBooking", bookingDetailsDto);
			
			if(TripSheetDto.getHexLhpvIds() != null) {
				model.put("lHPVDetailsIds", Utility.convertHexToString(TripSheetDto.getHexLhpvIds()));
				lhpvDetails	= lhpvDetailsService.getLHPVDetailsList(Utility.convertHexToString(TripSheetDto.getHexLhpvIds()), userDetails.getCompany_id());
				String lhpvNumber = "";
				if(lhpvDetails != null && !lhpvDetails.isEmpty()) {
					for(LHPVDetails details : lhpvDetails) {
						lhpvNumber += details.getlHPVNumber()+", ";
					}
				}
				model.put("lhpvNumber", lhpvNumber);
				vehicle		= vehicleService.getVehicel(lhpvDetails.get(0).getVid(), userDetails.getCompany_id());
				model.put("lHPVDetails", lhpvDetails);
				model.put("vehicle", vehicle);
				model.put("newTripSheet", false);
			}else {
				model.put("newTripSheet", true);
			}
		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}finally {
			lhpvDetails			= null;
			vehicle				= null;
			bookingDetailsDto	= null;
		}
		return new ModelAndView("addTripSheet", model);
	}

	// Note: This /copyTripSheet Controller is Copy Old TS To Create New
	// TripSheet Page
	@RequestMapping(value = "/copyTripSheet", method = RequestMethod.GET)
	public String copyTripSheet(@RequestParam("ID") final Long TripSheetID, Model model, HttpServletRequest result) {
		// Map<String, Object> model = new HashMap<String, Object>();
		try {
			String TID = "";
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheetID, userDetails));
			if (trip != null) {
				VehicleDto Check = vehicleService.Get_Vehicle_Current_Status_TripSheetID(trip.getVid());
				if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					TripSheet tripSheet = tripSheetService.getTripSheet(Check.getTripSheetID());
					TID += " <span> This Vehicle  <a href=\"showVehicle.in?vid=" + trip.getVid()
					+ "\" target=\"_blank\">" + trip.getVehicle_registration()
					+ " <i class=\"fa fa-external-link\"></i></a> In Other TripSheet <a href=\"showTripSheet.in?tripSheetID="
					+ Check.getTripSheetID() + "\" target=\"_blank\">TS-" + tripSheet.getTripSheetNumber()
					+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet, </span>, <br>";

					trip.setVid(TRIP_SHEET_DEFALAT_ZERO);
					trip.setVehicle_registration(null);
					trip.setVehicle_Group(null);
				} else if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					WorkOrders workOrders = workOrdersService.getWorkOrders(Check.getTripSheetID());
					TID += " <span> This Vehicle  <a href=\"showVehicle.in?vid=" + trip.getVid()
					+ "\" target=\"_blank\">" + trip.getVehicle_registration()
					+ " <i class=\"fa fa-external-link\"></i></a> In Work Shop <a href=\"showWorkOrder?woId="
					+ Check.getTripSheetID() + "\" target=\"_blank\">WO-" + workOrders.getWorkorders_Number()
					+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet, </span>, <br>";

					trip.setVid(TRIP_SHEET_DEFALAT_ZERO);
					trip.setVehicle_registration(null);
					trip.setVehicle_Group(null);
				}

				if (trip.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
					DriverDto CheckDriver = driverService
							.Get_Driver_Current_Status_TripSheetID(trip.getTripFristDriverID());
					if (CheckDriver.getDriverStatusId() == DriverStatus.DRIVER_STATUS_TRIPROUTE) {
						TripSheet tripSheet = tripSheetService.getTripSheet(Check.getTripSheetID());
						TID += "<span> This Frist Driver  <a href=\"showDriver.in?driver_id="
								+ trip.getTripFristDriverID() + "\" target=\"_blank\">" + trip.getTripFristDriverName()
								+ " <i class=\"fa fa-external-link\"></i></a> In Other TripSheet <a href=\"showTripSheet.in?tripSheetID="
								+ CheckDriver.getTripSheetID() + "\" target=\"_blank\">TS-"
								+ tripSheet.getTripSheetNumber()
								+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet,</span>, <br>";

						trip.setTripFristDriverID(TRIP_SHEET_DEFALAT_ZERO);
						trip.setTripFristDriverName(null);
						trip.setTripFristDriverMobile(null);
					}
				}
				if (trip.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
					DriverDto CheckDriverSec = driverService
							.Get_Driver_Current_Status_TripSheetID(trip.getTripSecDriverID());
					if (CheckDriverSec.getDriverStatusId() == DriverStatus.DRIVER_STATUS_TRIPROUTE) {
						TripSheet tripSheet = tripSheetService.getTripSheet(Check.getTripSheetID());
						TID += "<span> This Secound Driver  <a href=\"showDriver.in?driver_id="
								+ trip.getTripSecDriverID() + "\" target=\"_blank\">" + trip.getTripSecDriverName()
								+ " <i class=\"fa fa-external-link\"></i></a> In Other TripSheet <a href=\"showTripSheet.in?tripSheetID="
								+ CheckDriverSec.getTripSheetID() + "\" target=\"_blank\">TS-"
								+ tripSheet.getTripSheetNumber()
								+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet, </span>, <br>";

						trip.setTripSecDriverID(TRIP_SHEET_DEFALAT_ZERO);
						trip.setTripSecDriverName(null);
						trip.setTripSecDriverMobile(null);
					}
				}
				if (trip.getTripCleanerID() != 0) {
					DriverDto CheckCleaner = driverService
							.Get_Driver_Current_Status_TripSheetID(trip.getTripCleanerID());
					if (CheckCleaner.getDriverStatusId() == DriverStatus.DRIVER_STATUS_TRIPROUTE) {
						TripSheet tripSheet = tripSheetService.getTripSheet(Check.getTripSheetID());
						TID += "<span> This Cleaner  <a href=\"showDriver.in?driver_id=" + trip.getTripCleanerID()
						+ "\" target=\"_blank\">" + trip.getTripCleanerName()
						+ " <i class=\"fa fa-external-link\"></i></a> In Other TripSheet <a href=\"showTripSheet.in?tripSheetID="
						+ CheckCleaner.getTripSheetID() + "\" target=\"_blank\">TS-"
						+ tripSheet.getTripSheetNumber()
						+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet, </span>, <br>";

						trip.setTripCleanerID(TRIP_SHEET_DEFALAT_ZERO);
						trip.setTripCleanerName(null);
						trip.setTripCleanerMobile(null);
					}
				}
				// get the Trip sheet ID to Details
				model.addAttribute("TripSheet", trip);
				model.addAttribute("InAnotherTrip", TID);
				model.addAttribute("vehiclegroup",
						vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id()));

				UserProfileDto Orderingname = userProfileService
						.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
				model.addAttribute("paidBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
				model.addAttribute("paidById", userDetails.getCompany_id());
				model.addAttribute("place", Orderingname.getBranch_name());
				model.addAttribute("placeId", Orderingname.getBranch_id());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return "copyTripSheet";
	}

	// Note: This /saveTripSheet Controller is TripSheet Save
	@RequestMapping(value = "/saveTripSheet", method = RequestMethod.POST)
	public ModelAndView saveTripSheet(@ModelAttribute("command") TripSheetDto sheetDto,
			TripSheetAdvance TripSheetAdvance, final HttpServletRequest request, 
			RedirectAttributes redir)throws Exception {
		Map<String, Object> 	model 						= new HashMap<String, Object>();
		boolean 					validateTripsheet 			= false;
		boolean 					checkFuelMeterStatus 		= false;
		boolean 					isNewVehicleTripsheet 		= false;
		String						lHPVDetailsIds				= null;
		ValueObject					ownerShipObject				= null;
		HashMap<Long, ValueObject>	ownerShipObjectHM			= null;
		
		try {
			String TIDMandatory = "";
			CustomUserDetails 			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 	gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			HashMap<String, Object> 	configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			VehicleDto CheckVehicleStatus = null;
			// here Check Statues dispatch or save
			if (sheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
				ownerShipObjectHM	= new HashMap<Long, ValueObject>();
				
				// Check Vehicle Status Current IN ACTIVE OR NOT
				if (sheetDto.getVid() == null || sheetDto.getVid() <= 0) {
					isNewVehicleTripsheet = true;
				}
				if (!isNewVehicleTripsheet) {
					CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(sheetDto.getVid());
					
					if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
						/*
						 * ValueObject object = new ValueObject(); object.put("dispatchedByTime",
						 * sheetDto.getDispatchedByTime()); object.put("dispatchTime",
						 * sheetDto.getDispatchTime()); object.put("vehicleId", sheetDto.getVid());
						 * object.put("companyId", userDetails.getCompany_id()); ValueObject gpsObject =
						 * vehicleGPSDetailsService.getVehicleGPSDataAtSpecifiedTime(object);
						 * if(gpsObject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME)) {
						 * 
						 * sheetDto.setGpsOpeningLocation(gpsObject.getString(GPSConstant.
						 * GPS_LOCATION_NAME));
						 * if((int)gpsConfiguration.get(VehicleGPSConfiguratinConstant.GPS_FLAVOR) == 2)
						 * {
						 * 
						 * int gpsKM = (int) gpsObject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME, 0);
						 * if(gpsKM > 0)
						 * CheckVehicleStatus.setVehicle_ExpectedOdameter(CheckVehicleStatus.
						 * getVehicleExpectedKm() + gpsKM); } }else { checkFuelMeterStatus = true; }
						 */
						checkFuelMeterStatus = true;
						
					}
					if(CheckVehicleStatus.getVehicle_ExpectedOdameter() == null) {
						CheckVehicleStatus.setVehicle_ExpectedOdameter(1500);
					}
					if (sheetDto.getTripOpeningKM() <= CheckVehicleStatus.getVehicle_ExpectedOdameter()) {
						if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
							checkFuelMeterStatus = true;
						}
					}
				}

				if (checkFuelMeterStatus || isNewVehicleTripsheet) {
					List<TripSheetDto> ValidateTSOlD = null;
					// Checking validate of Query in TripSheet Account
					if (!isNewVehicleTripsheet) {
						ValidateTSOlD = tripSheetService.Get_Validate_TripSheet_Vehicle_DriverAcount(sheetDto.getVid(),
								userDetails.getCompany_id());
					} else {
						ValidateTSOlD = tripSheetService.Get_Validate_TripSheet_Vehicle(
								sheetDto.getVehicle_registration(), userDetails.getCompany_id());
					}
					if (ValidateTSOlD != null && !ValidateTSOlD.isEmpty()) {
						String TID = "";
						for (TripSheetDto add : ValidateTSOlD) {
							TID += "<a href=\"addCloseTripsheet.in?tripSheetID=" + add.getTripSheetID()
							+ "\" target=\"_blank\">TS-" + add.getTripSheetNumber()
							+ "  <i class=\"fa fa-external-link\"></i></a> ,";
						}
						redir.addFlashAttribute("CloseTS", TID);
						validateTripsheet = false;

					} else {
						validateTripsheet = true;
					}

					String[] From_TO_Array = null;
					try {
						String From_TO = sheetDto.getTripOpenDate();
						From_TO_Array = From_TO.split("  to  ");
						java.util.Date date = dateFormat.parse(From_TO_Array[0]);
						java.sql.Date tripfromDate = new Date(date.getTime());

						// Trip close Apprax
						java.util.Date dateTo = dateFormat.parse(From_TO_Array[1]);
						java.sql.Date triptoDate = new Date(dateTo.getTime());

						// Note: TripSheet Validate Only Current date only Dispatch
						java.util.Date currentDate = new java.util.Date();
						
						if(removeTime(tripfromDate).equals(removeTime(currentDate)) || (boolean)configuration.get(TripSheetConfigurationConstant.ALLOW_BACK_DATE_ENTRY)) {
							// Note: Below Check Vehicle Mandatory Compliance
							// Details
							if (!isNewVehicleTripsheet) {
								try {
									List<VehicleMandatoryDto> Select = vehicleMandatoryService
											.List_Vehicle_Mandatory_Details_GET_VEHICLEID_NOT_EXISTS(sheetDto.getVid(),
													tripfromDate, triptoDate, userDetails.getCompany_id());
									if (Select != null && !Select.isEmpty()) {
										// Checking the Value Of Mandatory Compliance
										for (VehicleMandatoryDto add : Select) {
											TIDMandatory += "<span>" + add.getVEHICLE_REGISTRATION()
											+ " Mandatory Compliance <a href=\"ShowVehicleMandatory.in?vehid="
											+ add.getVEHICLE_ID() + "\" target=\"_blank\">"
											+ add.getMANDATORY_RENEWAL_SUB_NAME()
											+ "  <i class=\"fa fa-external-link\"></i></a> is NOT Available On "
											+ tripfromDate + " To " + triptoDate + " </span>, <br>";
										}
										redir.addFlashAttribute("VMandatory", TIDMandatory);
										validateTripsheet = false;

									} else {
										// Showing Mandatory Compliance to Set Vehicle
										TIDMandatory += "<span>This Vehicle Mandatory Compliances are Available On "
												+ tripfromDate + " To " + triptoDate
												+ "  <a href=\"ShowVehicleMandatory.in?vehid=" + sheetDto.getVid()
												+ "\" target=\"_blank\"> Any Changes Mandatory Compliances <i class=\"fa fa-external-link\"></i></a></span>,<br>";
										redir.addFlashAttribute("VMandatory", TIDMandatory);

									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						} else {

							TIDMandatory += "<span> This Tripsheet Start Date Not Match Today. You can't Dispatch. Please change Journey Date. </span>";
							redir.addFlashAttribute("VMandatory", TIDMandatory);
							validateTripsheet = false;
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				} // checking Vehicle Status
				else {
					
					if (!isNewVehicleTripsheet) {
						String Link = "";
						if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
							if(CheckVehicleStatus.getTripSheetNumber()!=null){
							TripSheet tripSheet = tripSheetService.getTripSheet(CheckVehicleStatus.getTripSheetID());
							Link += "  <a href=\"showTripSheet.in?tripSheetID=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">TS-" + tripSheet.getTripSheetNumber()
							+ "  <i class=\"fa fa-external-link\"></i></a>";
							}
						} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
							if(CheckVehicleStatus.getWorkOrder_Number()!=null) {
							Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">WO-" + workOrdersService
							.getWorkOrders(CheckVehicleStatus.getTripSheetID()).getWorkorders_Number()
							+ "  <i class=\"fa fa-external-link\"></i></a> ";
							}
						} else if (sheetDto.getTripOpeningKM() > CheckVehicleStatus.getVehicle_ExpectedOdameter()) {

							Link += " Maxminum Allow Odometer " + CheckVehicleStatus.getVehicle_ExpectedOdameter()
							+ " kindly Check Odometer ";
						}

						TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
								+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create TripSheet " + " "
								+ Link + "" + " </span> ,";
						redir.addFlashAttribute("VMandatory", TIDMandatory);
						validateTripsheet = false;
						model.put("Close", true);
						return new ModelAndView("redirect:/addTripSheetEntries.in", model);
					}
				}

			} else {
				validateTripsheet = true;
			}
			if (validateTripsheet) {

				lHPVDetailsIds	= sheetDto.getHexLhpvIds();
				TripSheet tripSheet = prepareModel(sheetDto, request);
				List<LHPVDetails>				lhpvDetails				= null;
				List<TripSheetAdvance>  		tripAdvanceList			= null;
				List<TripSheetIncome>  			tripSheetIncomeList		= null;
				List<TripSheetToLhpvDetails>	tripToLhpv				= null;
				Double					tripTotalAdvance		= 0.0;
				Double					tripTotalIncome			= 0.0;

				if(lHPVDetailsIds != null && !lHPVDetailsIds.trim().equalsIgnoreCase("")) {
					lhpvDetails		=	lhpvDetailsService.getLHPVDetailsList(lHPVDetailsIds, userDetails.getCompany_id());
				}

				try {
					if (TripSheetAdvance.getAdvanceAmount() != null) {
						tripSheet.setTripTotalAdvance(TripSheetAdvance.getAdvanceAmount());
						tripSheet.setTripTotalexpense(TRIP_SHEET_DEFALAT_AMOUNT);
						tripSheet.setTripTotalincome(TRIP_SHEET_DEFALAT_AMOUNT);
					} else {
						tripSheet.setTripTotalAdvance(TRIP_SHEET_DEFALAT_AMOUNT);
						tripSheet.setTripTotalexpense(TRIP_SHEET_DEFALAT_AMOUNT);
						tripSheet.setTripTotalincome(TRIP_SHEET_DEFALAT_AMOUNT);
					}

					if(lhpvDetails != null && !lhpvDetails.isEmpty()) {
						tripSheetIncomeList	= new ArrayList<>();
						tripAdvanceList		= new ArrayList<>();
					
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						for(LHPVDetails  details : lhpvDetails) {
							if(details != null && details.getLorryHire() > 0) {
								TripSheetIncome	tripIncome	= new TripSheetIncome();

								tripIncome.setIncomeId(Integer.parseInt("" + details.getlHPVDetailsId()));
								tripIncome.setIncomeAmount(details.getLorryHire());
								tripIncome.setIncomePlaceId(TripSheetAdvance.getAdvancePlaceId());
								tripIncome.setIncomeRefence("LHPV NO : "+details.getlHPVNumber());
								tripIncome.setIncomeCollectedById(userDetails.getId());
								tripIncome.setCompanyId(userDetails.getCompany_id());
								tripIncome.setCreatedById(userDetails.getId());
								tripIncome.setTripsheet(tripSheet);
								tripIncome.setlHPVDetailsId(details.getlHPVDetailsId());
								
								tripIncome.setCreated(toDate);
								
								tripSheetIncomeList.add(tripIncome);
								
								if(details != null && details.getAdvanceAmount() > 0) {
									TripSheetAdvance	sheetAdvance = new TripSheetAdvance();
									sheetAdvance.setAdvanceAmount(details.getAdvanceAmount());

									sheetAdvance.setAdvancePlaceId(TripSheetAdvance.getAdvancePlaceId());
									sheetAdvance.setAdvancePaidbyId(TripSheetAdvance.getAdvancePaidbyId());
									sheetAdvance.setAdvanceRefence("LHPV NO : "+details.getlHPVNumber());
									sheetAdvance.setAdvanceRemarks("LHPV Advance For Lhpv NO "+details.getlHPVNumber());
									sheetAdvance.setTripsheet(tripSheet);
									sheetAdvance.setCompanyId(userDetails.getCompany_id());
									sheetAdvance.setCreatedById(userDetails.getId());

									sheetAdvance.setCreated(toDate);
									tripAdvanceList.add(sheetAdvance);
									//tripSheetService.addTripSheetAdvance(sheetAdvance);
								}
								
								if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED){
									ownerShipObject	= new ValueObject();
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_INCOME);
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, details.getlHPVDetailsId());
									ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, details.getVid());
									ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, 0.0);
									ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, details.getLorryHire());
									ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_LORRY_HIRE);
									ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, details.getCompanyId());
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, tripSheet.getTripOpenDate());
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Lorry Hire");
									ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Lhpv Number :  "+details.getlHPVNumber());
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
									ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, tripSheet.getCreatedById());
									ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, details.getLorryHire());
									
									VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
									vehicleAgentTxnCheckerService.save(agentTxnChecker);
									
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
									ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
									
									ownerShipObjectHM.put(agentTxnChecker.getVehicleAgentTxnCheckerId(), ownerShipObject);
									
								}
								
								
							}
							
							tripTotalAdvance += details.getAdvanceAmount();
							tripTotalIncome  += details.getLorryHire();
						}
						
						tripSheet.setTripTotalAdvance(tripSheet.getTripTotalAdvance() + tripTotalAdvance);
						tripSheet.setTripTotalincome(tripSheet.getTripTotalincome() + tripTotalIncome);
					}

					String name = userDetails.getEmail();
					// this getting in javascript to asses to jsp tripstatus values
					tripSheet.setTripStutesId(sheetDto.getTripStutesId());
					if (sheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
						UserProfileDto Orderingname = userProfileService
								.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(name);

						tripSheet.setDispatchedLocationId(Orderingname.getBranch_id());
						tripSheet.setDispatchedById(userDetails.getId());
						
						if(tripSheet.getDispatchedByTime() == null) {
							
							if((boolean) configuration.get("allowITSGatewayDriverDetails") && sheetDto.getDispatchTime() != null && sheetDto.getDispatchTime() != "") {
							   String opendate = date2.format(tripSheet.getTripOpenDate());
							   java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(opendate, sheetDto.getDispatchTime());
							   java.sql.Date Reported_Date = new java.sql.Date(date.getTime());
								tripSheet.setDispatchedByTime(Reported_Date);
								
							} else {
								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp dispatchedByTime = new java.sql.Timestamp(currentDateUpdate.getTime());
								tripSheet.setDispatchedByTime(dispatchedByTime);
							}
						}
						
					}

					tripSheet.setMarkForDelete(false);

					tripSheet.setCreatedById(userDetails.getId());
					tripSheet.setLastModifiedById(userDetails.getId());
					tripSheet.setCompanyId(userDetails.getCompany_id());
					TripSheetSequenceCounter sequenceCounter = tripsheetSequenceService
							.findNextSequenceNumber(userDetails.getCompany_id());
					tripSheet.setTripSheetNumber(sequenceCounter.getNextVal());
					tripSheetService.addTripSheet(tripSheet);
					// tripsheetSequenceService.updateNextSequenceNumber(sequenceCounter.getNextVal()
					// + 1, sequenceCounter.getSequence_Id());

					if (TripSheetAdvance.getAdvanceAmount() != null) {
						TripSheetAdvance TripAdvance = tripSheetBL.prepareTripAdvance(TripSheetAdvance);
						TripAdvance.setTripsheet(tripSheet);
						TripAdvance.setCompanyId(userDetails.getCompany_id());
						TripAdvance.setCreatedById(userDetails.getId());						
						TripAdvance.setDriverId(sheetDto.getAdvanceDriverId());
						tripSheetService.addTripSheetAdvance(TripAdvance);

					}
					if(tripAdvanceList != null && !tripAdvanceList.isEmpty())
						tripSheetService.saveTripAdvanceList(tripAdvanceList);
					if(tripSheetIncomeList != null && !tripSheetIncomeList.isEmpty())
						tripSheetService.saveTripIncomeList(tripSheetIncomeList);
					
					/*if(lhpvDetails != null && lhpvDetails.getAdvanceAmount() > 0) {
						TripSheetAdvance	sheetAdvance = new TripSheetAdvance();
						sheetAdvance.setAdvanceAmount(lhpvDetails.getAdvanceAmount());

						sheetAdvance.setAdvancePlaceId(TripSheetAdvance.getAdvancePlaceId());
						sheetAdvance.setAdvancePaidbyId(TripSheetAdvance.getAdvancePaidbyId());
						sheetAdvance.setAdvanceRefence("LHPV NO : "+lhpvDetails.getlHPVNumber());
						sheetAdvance.setAdvanceRemarks("LHPV Advance For Lhpv NO "+lhpvDetails.getlHPVNumber());
						sheetAdvance.setTripsheet(tripSheet);
						sheetAdvance.setCompanyId(userDetails.getCompany_id());
						sheetAdvance.setCreatedById(userDetails.getId());
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						sheetAdvance.setCreated(toDate);

						tripSheetService.addTripSheetAdvance(sheetAdvance);
					}*/

					// here Check Statues dispatch or save
					if (tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
						// Note : When Vehicle Created TRIPROUTE That time Vehicle
						// Status update to 'WORKSHOP'
						// kindly
						vehicleService.Update_Vehicle_Status_TripSheetID(tripSheet.getTripSheetID(), tripSheet.getVid(),
								userDetails.getCompany_id() ,VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE);

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
						
						if(sheetDto.getBusBookingDetailsId() != null && sheetDto.getBusBookingDetailsId() > 0) {
							busBookingDetailsService.updateTripSheetNumber(tripSheet.getTripSheetID(), sheetDto.getBusBookingDetailsId());
						}
					}

					

					if (tripSheet.getRouteID() != TRIP_SHEET_DEFALAT_ZERO) {
						// not equal to zreo get all fixed expense
						List<TripRoutefixedExpense> RoutefixedExpense = TripRouteService
								.listTripRouteFixedExpenes(tripSheet.getRouteID(), userDetails.getCompany_id());
						if (RoutefixedExpense != null && !RoutefixedExpense.isEmpty()) {
							for (TripRoutefixedExpense tripRoutefixedExpense : RoutefixedExpense) {

								TripSheetExpense TripExpense = new TripSheetExpense(
										tripRoutefixedExpense.getExpenseId(), tripRoutefixedExpense.getExpenseAmount(),
										tripRoutefixedExpense.getExpensePlaceId(),
										tripRoutefixedExpense.getExpenseRefence(),
										tripRoutefixedExpense.getFixedTypeId());
								TripExpense.setTripsheet(tripSheet);
								TripExpense.setExpenseId(tripRoutefixedExpense.getExpenseId());
								TripExpense.setExpensePlaceId(tripRoutefixedExpense.getExpensePlaceId());
								TripExpense.setExpenseFixedId(tripRoutefixedExpense.getFixedTypeId());
								TripExpense.setCreatedById(userDetails.getId());
								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								TripExpense.setCreated(toDate);
								TripExpense.setCompanyId(userDetails.getCompany_id());
								TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());
								tripSheetService.addTripSheetExpense(TripExpense);

								Object[] expenseIds = tripSheetService
										.getDriverAdvanceAndBataExpenseId(userDetails.getCompany_id());

								// Note : This Update Bata Details in Trip Sheet

								if (expenseIds != null) {
									if (expenseIds[0] != null
											&& expenseIds[0] == tripRoutefixedExpense.getExpenseId()) {

									}
									if (expenseIds[1] != null
											&& expenseIds[1] == tripRoutefixedExpense.getExpenseId()) {
										tripSheetService.Update_TripSheet_FIRST_Driver_BATA(
												tripRoutefixedExpense.getExpenseAmount(), tripSheet.getTripSheetID(), userDetails.getCompany_id());
									}
									if (expenseIds[2] != null
											&& expenseIds[2] == tripRoutefixedExpense.getExpenseId()) {

									}
									if (expenseIds[3] != null
											&& expenseIds[3] == tripRoutefixedExpense.getExpenseId()) {
										tripSheetService.Update_TripSheet_SECOND_Driver_BATA(
												tripRoutefixedExpense.getExpenseAmount(), tripSheet.getTripSheetID(), userDetails.getCompany_id());
									}
									if (expenseIds[4] != null
											&& expenseIds[4] == tripRoutefixedExpense.getExpenseId()) {

									}
									if (expenseIds[5] != null
											&& expenseIds[5] == tripRoutefixedExpense.getExpenseId()) {
										tripSheetService.Update_TripSheet_CLEANER_BATA(
												tripRoutefixedExpense.getExpenseAmount(), tripSheet.getTripSheetID(), userDetails.getCompany_id());
									}
								}

							}

							// Note : This Update TripSheet Total Expense Updating
							// The Value Of Total Expense amount to TripSheet

							tripSheetService
							.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripSheet.getTripSheetID(), userDetails.getCompany_id());

						}

					}

					if (tripSheet.getRouteID() != TRIP_SHEET_DEFALAT_ZERO) {
					// not equal to zreo get all fixed expense
					List<TripRoutefixedIncome> RoutefixedIncome	=null;
					RoutefixedIncome	= TripRouteService.listTripRouteFixedIncomeList(tripSheet.getRouteID(), userDetails.getCompany_id());
					if (RoutefixedIncome != null && !RoutefixedIncome.isEmpty()) {
						for (TripRoutefixedIncome tripRoutefixedIncome : RoutefixedIncome) {
							TripSheetIncome TripIncome=null;
							TripIncome = new TripSheetIncome(
							tripRoutefixedIncome.getIncomeAmount(),
							tripRoutefixedIncome.getIncomeRefence(),
							tripRoutefixedIncome.getCompanyId());
							 if(TripIncome!=null) {
							TripIncome.setTripsheet(tripSheet);
							//TripIncome.setTripincomeID((tripRoutefixedIncome.getRoutefixedID()).longValue());
							TripIncome.setIncomePlaceId(tripRoutefixedIncome.getIncomePlaceId());
							TripIncome.setIncomeId(tripRoutefixedIncome.getIncomeId());
							TripIncome.setCreatedById(userDetails.getId());
							TripIncome.setIncomeFixedId(tripRoutefixedIncome.getIncomeFixedId());
							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
							TripIncome.setCreated(toDate);
							TripIncome.setCompanyId(userDetails.getCompany_id());
							TripIncome.setIncomeCollectedById(userDetails.getId());// here we used UserId
							TripIncome.setIncomeRefence(tripRoutefixedIncome.getIncomeRefence());
							 }
							tripSheetService.addTripSheetIncome(TripIncome);

							/*Object[] incomeIds = tripSheetService
										.getDriverAdvanceAndBataExpenseId(userDetails.getCompany_id());*/

							// Note : This Update Bata Details in Trip Sheet

						}
						// Note : This Update TripSheet Total Expense Updating
						// The Value Of Total Expense amount to TripSheet

						tripSheetService
							.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(tripSheet.getTripSheetID(), userDetails.getCompany_id());
					}
					}
					
					if(lHPVDetailsIds != null && !lHPVDetailsIds.trim().equalsIgnoreCase("")) 
						lhpvDetailsService.updateTripSheetCreated(lHPVDetailsIds, tripSheet.getTripSheetID(), userDetails.getCompany_id());
					
					if(lhpvDetails != null && !lhpvDetails.isEmpty()) {
						tripToLhpv			= new ArrayList<>();
						for(LHPVDetails  details : lhpvDetails) {
							TripSheetToLhpvDetails	sheetToLhpvDetails	= new TripSheetToLhpvDetails();
							
							sheetToLhpvDetails.setTripSheetId(tripSheet.getTripSheetID());
							sheetToLhpvDetails.setLhpvId(details.getLhpvId());
							sheetToLhpvDetails.setLhpvNumber(details.getlHPVNumber());
							sheetToLhpvDetails.setLhpvDate(details.getLhpvDateTimeStamp());
							sheetToLhpvDetails.setLhpvAdvance(details.getAdvanceAmount());
							sheetToLhpvDetails.setLhpvLorryHire(details.getLorryHire());
							sheetToLhpvDetails.setCompanyId(userDetails.getCompany_id());
							
							tripToLhpv.add(sheetToLhpvDetails);
							
						}
						tripSheetToLhpvDetailsService.saveTripSheetToLhpvDetailsList(tripToLhpv);
					}
					
					model.put("saveTripSheet", true);

					// this Update Current Odometer in Vehicle Getting Trip
					// Daily Sheet
					Update_Current_OdometerinVehicle_getTripDailySheetTo(tripSheet);
					if(ownerShipObjectHM != null && ownerShipObjectHM.size() > 0) {
						vehicleAgentIncomeExpenseDetailsService.processVehicleAgentIncomeExpense(ownerShipObjectHM);
					}
					

				} catch (Exception e) {
					//System.err.println("Exception "+ e);
					model.put("alreadyTripSheet", true);

					LOGGER.error("TripSheet Page:", e);
					return new ModelAndView("newTripSheet", model);
				}
				return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + tripSheet.getTripSheetID() + "", model);
			} 
			// close Boolean If condition

			return new ModelAndView("redirect:/addTripSheetEntries.in?Close=true", model);
		} catch (Exception e) {
			System.err.println("Exception "+ e);

			LOGGER.error("TripSheet Page:"+ e);
			throw e;

		}
	}

	// Note: This /showTripSheet Controller is Show TripSheet Id to Show all
	// Details
	@SuppressWarnings("unchecked") 
	@RequestMapping(value = "/showTripSheet2", method = RequestMethod.GET)
	public ModelAndView ShowTripsheet(@RequestParam("tripSheetID") final Long TripSheetID, HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>    	config						= null;
		HashMap<String, Object>    	configuration				= null;
		boolean						hideClosedTripSheet			= false;
		CustomUserDetails 			userDetails 				= null;
		TripSheetDto 				trip 						= null;
		ModelAndView 				map 						= new ModelAndView("AddBatteryInventory");
		double 						fuelAmount					= 0.0;
		double 						ureaAmount					= 0.0;
		double 						tollAmount					= 0.0;
		Double 						TotalExpense				= 0.0;
		List<FuelDto> 				ShowAmount					=null;
		HashMap<String, Object>    	gpsConfig					= null;
		boolean						showCombineTripDetails		= false;
		List<TripSheetExpenseDto> 	finalExpenseList			= null;
		DecimalFormat 				numberFormat				= null;
		double						kmpl						= 0.0;
		double						totalDueAmount				= 0.0;
		boolean						showTripsheetDueAmount		= false;
		List<TripSheetExpenseDto> expenseDto = null;
		try {
			numberFormat 			= new DecimalFormat("#.00");
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TOLL_API_CONFIG);
			config					= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			gpsConfig				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_GPS_CONFIG);
			hideClosedTripSheet		= (boolean) config.getOrDefault(TripSheetConfigurationConstant.HIDE_CLOSED_TRIPSHEET, false);
			showCombineTripDetails	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.SHOW_COMBINE_TRIP_DETAILS, false);
			showTripsheetDueAmount	= (boolean) config.getOrDefault("showTripsheetDueAmount", false);
			
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
			map.addObject("permissions", permission);
			trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheetID, userDetails));
			if (trip != null) {
				if(hideClosedTripSheet && !permission.contains(new SimpleGrantedAuthority("SHOW_TRIPSHEET_CLOSE_STATUS"))){
					if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED || trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
						model.put("NotFound", true);
						return new ModelAndView("redirect:/newTripSheetEntries.in", model);
					}
				}
				model.put("gpsConfig", gpsConfig);
				model.put("configuration", config);
				model.put("tripConfig", config);
				model.put("companyId", userDetails.getCompany_id());
				model.put("userId", userDetails.getId());
				// get the Trip sheet ID to Details
				model.put("TripSheet", trip);
				boolean allowTollApiIntegration = false;
				if((boolean)configuration.get(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION) || (boolean)configuration.get(TollApiConfiguration.ALLOW_KVB_BANK_TOLL_API)) {
					allowTollApiIntegration = true;
				}
				model.put(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION, allowTollApiIntegration);
				model.put("allowTicketIncomeApiIntegeration",(boolean) config.get("allowTicketIncomeApiIntegeration"));
				model.put("allowITSGatewayBusIncome",(boolean) config.get("allowITSGatewayBusIncome"));
				model.put("companyId",userDetails.getCompany_id());
				model.put("userId", userDetails.getId());
				BusBookingDetailsDto	busDetailsDto	=  busBookingDetailsService.getBusBookingDetailsByTripSheetId(trip.getTripSheetID());
				if(busDetailsDto != null && trip.getTripUsageKM() != null & busDetailsDto.getRate() != null) {
					model.put("busBookingAmount", busDetailsDto.getRate() * trip.getTripUsageKM());
				}
				model.put("busBooking", busDetailsDto);
				ValueObject	refreshmentObj	= refreshmentEntryService.getRefreshmentEntryListByTripSheetId(trip.getTripSheetID());
				if(refreshmentObj != null) {
					model.put("refreshment",(List<RefreshmentEntryDto>) refreshmentObj.get("refreshment"));
					model.put("grandTotal", refreshmentObj.getDouble("grandTotal"));
					model.put("totalQty", refreshmentObj.getDouble("totalQty"));
					model.put("totalRQty", refreshmentObj.getDouble("totalRQty"));
					model.put("totalConsumption", refreshmentObj.getDouble("totalConsumption"));
					TotalExpense += refreshmentObj.getDouble("grandTotal",0);
				}
				// get Trip sheet id to get all Advance Details
				model.put("TripSheetAdvance",
						tripSheetService.findAllTripSheetAdvance(TripSheetID, userDetails.getCompany_id()));
				
				// get Trip sheet id to get all Expense Details
				if(showCombineTripDetails) {
					List<TripSheetExpenseDto> expenseCombine = tripSheetService.findAllTripSheetExpense(TripSheetID, userDetails.getCompany_id());
					if(expenseCombine != null && !expenseCombine.isEmpty()) {
						HashMap<Integer, TripSheetExpenseDto> expenseMap = tripSheetService.findAllTripSheetExpenseCombineMap(expenseCombine);
						if(expenseMap != null) {
							finalExpenseList = new ArrayList<TripSheetExpenseDto>();
							finalExpenseList.addAll(expenseMap.values());
						}
						model.put("ExpenseCombineList",finalExpenseList);
					}
				} else {
					expenseDto = tripSheetService.findAllTripSheetExpense(TripSheetID, userDetails.getCompany_id());
					
					model.put("TripSheetExpense",expenseDto);
				}
				/*Driver Penalty Details..*/
				List<DriverSalaryAdvanceDto> DriverAdvanvce = DriverSalaryAdvanceService.List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(TripSheetID, DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY, userDetails.getCompany_id());
				if(DriverAdvanvce != null) {
					double penaltyTotal = 0;
					for(DriverSalaryAdvanceDto dto:DriverAdvanvce) {
						 penaltyTotal += dto.getADVANCE_AMOUNT();
					}
					model.put("penaltyTotal",penaltyTotal);
					model.put("DriverAdvanvce",DriverAdvanvce);
				}
				
				if(showTripsheetDueAmount) {
					model.put("TripsheetDueAmount",
							tripSheetService.getDueAmountListByTripsheetId(TripSheetID, userDetails.getCompany_id()));
					model.put("TotalDueAmount",
							tripSheetService.getTotalDueAmountByTripsheetId(TripSheetID, userDetails.getCompany_id()));
					totalDueAmount = (double) model.get("TotalDueAmount");
				}
				
				// get Trip sheet id to get all TollExpense Details
				ValueObject object = tollExpensesDetailsService.getTollExpensesDetailsList(TripSheetID, userDetails.getCompany_id());
				model.put("TripSheetTollExpense",(List<TollExpensesDetailsDto>)object.get("ExpenseName"));
				model.put("TripSheetTollExpenseName",configuration.get(TollApiConfiguration.TOLL_EXPENSE_NAME));
				model.put("TripSheetTollExpenseDate",object.get("Date"));
				model.put("TripSheetTollExpenseTotalAmount",object.get("totalAmount"));

				// get Trip sheet id to get all Extra Details
				model.put("TripSheetExtraOptions",
						tripSheetService.findAllTripSheetExtraOptions(TripSheetID, userDetails.getCompany_id()));
				model.put("TripSheetExtraOptionsReceived",
						tripSheetService.findAllTripSheetExtraOptionsReceived(TripSheetID, userDetails.getCompany_id()));


				// get Trip sheet id to get all Income Details

				List<TripSheetIncomeDto> incomeDto =tripSheetService.findAllTripSheetIncome(TripSheetID, userDetails.getCompany_id());
				model.put("TripSheetIncome",incomeDto);
				
				if((boolean) config.get("driverBalanceWithNarration")) {
					ValueObject	valueObject = tripSheetBL.getDriverBalanceForTrip(trip, config, incomeDto, expenseDto);
					if(valueObject != null) {
						model.put("driverBalanceKey",valueObject.get("driverBalanceKey"));
						model.put("balanceAmount",valueObject.get("balanceAmount"));
					}
					
				}

				
				DecimalFormat df = new DecimalFormat("##,##,##0");
				df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
				Double TotalAdvance = trip.getTripTotalAdvance();
				if (trip.getTripTotalAdvance() == null) {
					TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
				}
				model.put("advanceTotal", df.format(TotalAdvance));
				df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
				TotalExpense = trip.getTripTotalexpense();
				if (trip.getTripTotalexpense() == null) {
					TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
				}
				model.put("expenseTotal", df.format(TotalExpense));
				df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
				Double Totalincome = trip.getTripTotalincome();
				if (trip.getTripTotalincome() == null) {
					Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
				}
				
				model.put("incomeTotal", df.format(Totalincome));
				model.put("driverBalance", (Totalincome + TotalAdvance) - TotalExpense - totalDueAmount);
				Boolean TripStatus = false;
				if (trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
					TripStatus = true;
				} else if (trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
					TripStatus = true;
				}
				if (TripStatus || (boolean)config.get("tripOpenCloseFuelRequired")) {
					// show Halt data
					model.put("TripSheetHalt", (driverHaltService.Find_list_TripSheet_DriverHalt(TripSheetID,userDetails.getCompany_id())));

					VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Fuel_Entries_Show(vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(trip.getVid(), userDetails.getCompany_id()));
					model.put("vehicle", vehicle);
					
					// show fuel entries in this tripsheet
					ShowAmount = fuelService.Get_TripSheet_IN_FuelTable_List(TripSheetID, userDetails.getCompany_id(), userDetails.getId());
					model.put("fuel", fuelBL.prepareListofFuel(ShowAmount));
					model.put("fTDiesel", fuelBL.prepareTotal_Tripsheet_fuel_details(ShowAmount));
					model.put("fTUsage", fuelBL.prepareTotalUsage_Tripsheet_fuel_details(ShowAmount));
					fuelAmount= fuelBL.prepareTotalAmount_Tripsheet_fuel_details(ShowAmount);
					
				    DecimalFormat f = new DecimalFormat("##.00");
					model.put("fTAmount", f.format(fuelAmount));
					if(ShowAmount != null && !ShowAmount.isEmpty()) {
						
						List<FuelDto> fuelList = fuelBL.prepareListofFuel(ShowAmount);
						int    size 	 = 0;
						double totalKmpl = 0;
						for(FuelDto dto : fuelList) {
							if(dto.getFuel_kml() != null && dto.getFuel_tank() == 0) {
								kmpl += dto.getFuel_kml();
								size ++;
							}
						}
						if(size  > 0) {
							totalKmpl = kmpl/size;
							model.put("totalKmpl", totalKmpl);
						}
						
					}
					tollAmount=	object.getDouble("totalAmount", 0);
					
					List<UreaEntriesDto> ureaDeatils = UreaEntriesService.getUreaEntriesDetailsByTripSheetId(TripSheetID, userDetails.getCompany_id());
					if(ureaDeatils != null) {
					model.put("urea", ureaDeatils);
					model.put("totalUrea", ureaEntriesBL.prepareTotal_Tripsheet_Urea_details(ureaDeatils));
					ureaAmount = ureaEntriesBL.prepareTotal_Tripsheet_Urea_Amount(ureaDeatils);
					model.put("totalUreaAmnt",ureaAmount);
					
					model.put("tripTotalincome", numberFormat.format(Totalincome-(fuelAmount + ureaAmount + tollAmount + TotalExpense)));
					}
					
					model.put("TripSheetTollExpense",(List<TollExpensesDetailsDto>)object.get("ExpenseName"));
					model.put("TripSheetTollExpenseName",configuration.get(TollApiConfiguration.TOLL_EXPENSE_NAME));
					model.put("TripSheetTollExpenseDate",object.get("Date"));
					model.put("TripSheetTollExpenseTotalAmount",object.get("totalAmount"));

				}
				
				if(TripStatus || (boolean)config.get("tripOpenCloseFuelRequired")) {
					ShowAmount = fuelService.Get_TripSheet_IN_FuelTable_List(TripSheetID, userDetails.getCompany_id(), userDetails.getId());
					fuelAmount= fuelBL.prepareTotalAmount_Tripsheet_Created_fuel_details(ShowAmount);
					tollAmount=	object.getDouble("totalAmount", 0);
					model.put("tripTotalincome", (Totalincome-(fuelAmount + ureaAmount + tollAmount + TotalExpense)));
					
					model.put("TripSheetTollExpense",(List<TollExpensesDetailsDto>)object.get("ExpenseName"));
					model.put("TripSheetTollExpenseName",configuration.get(TollApiConfiguration.TOLL_EXPENSE_NAME));
					model.put("TripSheetTollExpenseDate",object.get("Date"));
					model.put("TripSheetTollExpenseTotalAmount",object.get("totalAmount"));
				}
				
				
				
				if (trip.getTripCommentTotal() != null && trip.getTripCommentTotal() != TRIP_SHEET_DEFALAT_ZERO) {
					model.put("TripComment", tripSheetBL.TripSheetComment_Display(
							tripCommentService.list_TripSheet_ID_TO_TripSheetComment(TripSheetID, userDetails.getCompany_id())));
				}
			} else {
				model.put("NotFound", true);
				return new ModelAndView("redirect:/newTripSheetEntries.in", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED || trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {

			return new ModelAndView("showTripSheet2", model);
		}
		else
		{

			return new ModelAndView("showTripSheet2", model);
		}
		/*return new ModelAndView("showTripSheet", model);*/
	}

	@RequestMapping(value = "/getTripsheetDetails", method = RequestMethod.GET)
	public ModelAndView getTripsheetDetails(@RequestParam("tripSheetID") final Long TripSheetID,
			HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			int flavor = companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if (flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheetID + "", model);
			} else if (flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				return new ModelAndView("redirect:/showTripCol.in?ID=" + TripSheetID + "", model);
			} else if (flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				return new ModelAndView("redirect:/showTripDaily.in?ID=" + TripSheetID + "", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("showTripSheet", model);
	}

	// Note: This /showTripSheetPrint Controller is Show TripSheet Id to Print
	// Before Close
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/showTripSheetPrint", method = RequestMethod.GET)
	public ModelAndView showTripSheetPrint(@RequestParam("id") final Long id, final HttpServletRequest request) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		HashMap<String, Object>    	config						= null;
		CustomUserDetails 			userDetails					= null;
		boolean						showCombineTripDetails		= false;
		boolean						showTripsheetDueAmount		= false;
		List<TripSheetExpenseDto> 	finalExpenseList			= null;
		double						totalDueAmount				= 0.0;
		List<RefreshmentEntryDto> refreshmentList				= null;
		DecimalFormat 						numberFormat				= null;
		Double 						tripTotalincome 			= 0.0;
		double 						fuelAmount					= 0.0;
		List<FuelDto> 				         ShowAmount					= null;
		double 						ureaAmount					= 0.0;
		double 						tollAmount					= 0.0;
		Vehicle                     vehicleDetails 					= null;
		SubCompany  				SubCompany 					= null;
		try {	
			
			Double TotalAdvance = 0.0;
			Double TotalExpense = 0.0;
			Double totalIncome = 0.0;
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			config			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			showCombineTripDetails	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.SHOW_COMBINE_TRIP_DETAILS, false);
			showTripsheetDueAmount	= (boolean) config.getOrDefault("showTripsheetDueAmount", false);
			
			HashMap<String, Object> comConfig = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			model.put("configuration", config);
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(id, userDetails));
			
			vehicleDetails  = vehicleService.getVehicel(trip.getVid(), userDetails.getCompany_id());
			if(vehicleDetails.getSubCompanyId() != null )
			{
				SubCompany = subCompanyService.getSubCompanyByVehicle(vehicleDetails.getSubCompanyId());
				System.err.println("SubCompany "+ SubCompany);
				System.err.println("subcompanyName "+ SubCompany.getSubCompanyName());
				model.put("SubCompany", SubCompany);
			}
			
			// get the Trip sheet ID to Details
			UserProfileDto company	= userProfileService.findUserProfileByUser_email(userDetails.getEmail());
			if((boolean) comConfig.get("showVehicleGroupWiseCompany")) {
				VehicleGroupWiseCompanyName	companyName =	vehicleGroupWiseCompanyNameRepository.getGroupWiseCompanyName(trip.getVehicleGroupId());
				if(companyName != null) {
					company.setCompany_name(companyName.getCompanyName());
				}
			}
			model.put("company", company);
			model.put("TripSheet", trip);
			// get Trip sheet id to get all Advance Details
			VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Fuel_Entries_Show(vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(trip.getVid(), userDetails.getCompany_id()));
			model.put("vehicle", vehicle);
			model.put("TripSheetAdvance", tripSheetService.findAllTripSheetAdvance(id, userDetails.getCompany_id()));
			BusBookingDetailsDto	busDetailsDto	=  busBookingDetailsService.getBusBookingDetailsByTripSheetId(trip.getTripSheetID());
			ValueObject	refreshmentObj	=refreshmentEntryService.getRefreshmentEntryListByTripSheetId(trip.getTripSheetID());
			refreshmentList=(List<RefreshmentEntryDto>) refreshmentObj.get("refreshment");
			
			if(refreshmentList !=null && !refreshmentList.isEmpty()) {
				model.put("totalConsumption", refreshmentObj.getDouble("totalConsumption",0));
				model.put("totalRQty", refreshmentObj.getDouble("totalRQty",0));
				model.put("totalQty", refreshmentObj.getDouble("totalQty",0));
				model.put("grandTotal", refreshmentObj.getDouble("grandTotal",0));
				model.put("totalReturnAmount", refreshmentObj.getDouble("totalReturnAmount",0));
				model.put("refreshmentTotalAmount",Utility.round((refreshmentObj.getDouble("grandTotal",0) - refreshmentObj.getDouble("totalReturnAmount",0)), 2));
				model.put("refreshmentList", refreshmentList);
			}
			if(busDetailsDto != null && trip.getTripUsageKM() != null & busDetailsDto.getRate() != null) {
				model.put("busBookingAmount", Utility.round((busDetailsDto.getRate() * trip.getTripUsageKM()), 2));
			}
			model.put("busBooking", busDetailsDto);

			// get Trip sheet id to get all Expense Details
			if(showCombineTripDetails) {
				List<TripSheetExpenseDto> expenseCombine = tripSheetService.findAllTripSheetExpense(trip.getTripSheetID(), userDetails.getCompany_id());
				if(expenseCombine != null && !expenseCombine.isEmpty()) {
					
					HashMap<Integer, TripSheetExpenseDto> expenseMap = tripSheetService.findAllTripSheetExpenseCombineMap(expenseCombine);
					if(expenseMap != null) {
						finalExpenseList = new ArrayList<TripSheetExpenseDto>();
						finalExpenseList.addAll(expenseMap.values());
					}
					model.put("ExpenseCombineList",finalExpenseList);
				}
			} else {
			model.put("TripSheetExpense", tripSheetService.findAllTripSheetExpense(id, userDetails.getCompany_id()));
			}
			
			List<DriverSalaryAdvanceDto> DriverAdvanvce = DriverSalaryAdvanceService.List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(id, DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY, userDetails.getCompany_id());
			if(DriverAdvanvce != null) {
				double penaltyTotal = 0;
				for(DriverSalaryAdvanceDto dto:DriverAdvanvce) {
					 penaltyTotal += dto.getADVANCE_AMOUNT();
				}
				model.put("penaltyTotal",tofixedTwo.format(penaltyTotal));
				model.put("DriverAdvanvce",tofixedTwo.format(DriverAdvanvce));
			}
			
			if(showTripsheetDueAmount) {
				model.put("TripsheetDueAmount",
						tripSheetService.getDueAmountListByTripsheetId(id, userDetails.getCompany_id()));
				model.put("TotalDueAmount",
						tripSheetService.getTotalDueAmountByTripsheetId(id, userDetails.getCompany_id()));
				totalDueAmount = (double) model.get("TotalDueAmount");
			}
			
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			TotalAdvance = trip.getTripTotalAdvance();
			if (trip.getTripTotalAdvance() == null) {
				TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("advanceTotal", df.format(TotalAdvance));
			
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			if(trip.getTripTotalincome() != null) {
				totalIncome	= trip.getTripTotalincome();
			}
			model.put("expenseTotal", df.format(TotalExpense));
			model.put("balance", df.format(TotalAdvance-TotalExpense));
			
			ShowAmount = fuelService.Get_TripSheet_IN_FuelTable_List(id, userDetails.getCompany_id(), userDetails.getId());
			fuelAmount= fuelBL.prepareTotalAmount_Tripsheet_fuel_details(ShowAmount);
			
			fuelAmount= fuelBL.prepareTotalAmount_Tripsheet_Created_fuel_details(ShowAmount);
			
  			 ValueObject objectToll = tollExpensesDetailsService.getTollExpensesDetailsList(id, userDetails.getCompany_id());
			 tollAmount	=	objectToll.getDouble("totalAmount", 0);
			
				Double Totalincome = trip.getTripTotalincome();
				if (trip.getTripTotalincome() == null) {
					Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
				}
				
				List<UreaEntriesDto> ureaDeatils = ureaEntriesService.getUreaEntriesDetailsByTripSheetId(id, userDetails.getCompany_id());
				if(ureaDeatils != null) {
				ureaAmount = ureaEntriesBL.prepareTotal_Tripsheet_Urea_Amount(ureaDeatils);
				tripTotalincome = Totalincome-(fuelAmount + ureaAmount + tollAmount + TotalExpense);
				}
				
			    tripTotalincome = Totalincome-(fuelAmount + ureaAmount + tollAmount + TotalExpense);
			    model.put("tripTotalincome", (tofixedTwo.format(tripTotalincome)));
			
			if((boolean) config.get("reverseDriverBalance")) {
				model.put("driverBalance", df.format(TotalExpense - (totalIncome + TotalAdvance)));
			}else {
				if((boolean) config.get("getDriverBalance"))
				{
					model.put("driverBalance", Utility.round((TotalAdvance - TotalExpense), 2));
				}
				else
				{
					model.put("driverBalance", df.format((totalIncome + TotalAdvance) - TotalExpense - totalDueAmount));
				}
			}
			
			List<TripSheetIncomeDto>  incomeDto	=  tripSheetService.findAllTripSheetIncome(id, userDetails.getCompany_id());

			// get Trip sheet id to get all Income Details
			model.put("TripSheetIncome", incomeDto);
			model.put("preparedBy", trip.getTripCreatedBy());
			model.put("authorizedBy", trip.getClosedBy());
			
			if (trip.getTripCommentTotal() != null && trip.getTripCommentTotal() != TRIP_SHEET_DEFALAT_ZERO) {
				model.put("TripComment", tripSheetBL.TripSheetComment_Display(
						tripCommentService.list_TripSheet_ID_TO_TripSheetComment(id, userDetails.getCompany_id())));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("showTripSheetPrint", model);
	}

	// Note: This /showTripSheetPrint Controller is Show TripSheet Id to Print
	// After Close
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/TSPrint", method = RequestMethod.GET)
	public ModelAndView TSPrint(@RequestParam("ID") final Long id, final HttpServletRequest request) {
		Map<String, Object> 				model 						= new HashMap<String, Object>();
		HashMap<String, Object> 			config						= null;		
		HashMap<String, Object> 			configuration				= null;	
		HashMap<String, Object> 			gpsConfig					= null;
		CustomUserDetails 					userDetails					= null;
		DecimalFormat 						numberFormat				= null;
		TripSheetDto 						trip						= null;
		double								fuelAmount					= 0.0;
		double								ureaAmount					= 0.0;
		double 								tollAmount					= 0.0;
		boolean								showCombineTripDetails		= false;
		boolean								showTripsheetDueAmount		= false;
		List<TripSheetExpenseDto> 			finalExpenseList			= null;
		double 								kmpl 						= 0.0; 
		double 								totalDueAmount 				= 0.0; 
		List<TripSheetExpenseDto>       	expenseDto					= null;
		double 								driverBalance				= 0.0;
		double 								refreshmentTotalAmount			 = 0.0;
		ValueObject							refreshmentObj				= null;
		List<RefreshmentEntryDto> 			refreshmentList				= null;
		Vehicle                            vehicleDetails 				= null;
		SubCompany  				       SubCompany 					= null;
		
		try {
			refreshmentObj 	= new ValueObject();
			numberFormat 	= new DecimalFormat("#.00");
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			config			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TOLL_API_CONFIG);
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			gpsConfig		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			showCombineTripDetails	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.SHOW_COMBINE_TRIP_DETAILS, false);
			showTripsheetDueAmount	= (boolean) configuration.getOrDefault("showTripsheetDueAmount", false);
			HashMap<String, Object> comConfig = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			
			trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(id, userDetails));
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);
			model.put("configuration", configuration);
			model.put("gpsConfig", gpsConfig);
		
			UserProfileDto company	= userProfileService.findUserProfileByUser_email(userDetails.getEmail());
			if((boolean) comConfig.get("showVehicleGroupWiseCompany")) {
				VehicleGroupWiseCompanyName	companyName =	vehicleGroupWiseCompanyNameRepository.getGroupWiseCompanyName(trip.getVehicleGroupId());
				if(companyName != null) {
					company.setCompany_name(companyName.getCompanyName());
				}
			}
			model.put("company", company);
			// get Trip sheet id to get all Advance Details
			model.put("TripSheetAdvance", tripSheetService.findAllTripSheetAdvance(id, userDetails.getCompany_id()));

			// get Trip sheet id to get all Expense Details
			if(showCombineTripDetails) {
				List<TripSheetExpenseDto> expenseCombine = tripSheetService.findAllTripSheetExpense(trip.getTripSheetID(), userDetails.getCompany_id());
				if(expenseCombine != null && !expenseCombine.isEmpty()) {
					
					HashMap<Integer, TripSheetExpenseDto> expenseMap = tripSheetService.findAllTripSheetExpenseCombineMap(expenseCombine);
					if(expenseMap != null) {
						finalExpenseList = new ArrayList<TripSheetExpenseDto>();
						finalExpenseList.addAll(expenseMap.values());
					}
					model.put("ExpenseCombineList",finalExpenseList);
				}
			} else {
				expenseDto	=	tripSheetService.findAllTripSheetExpense(id, userDetails.getCompany_id());
			model.put("TripSheetExpense", expenseDto);
			}
			
			/*Driver Penalty Details..*/
			List<DriverSalaryAdvanceDto> DriverAdvanvce = DriverSalaryAdvanceService.List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(id, DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY, userDetails.getCompany_id());
			if(DriverAdvanvce != null) {
				double penaltyTotal = 0;
				for(DriverSalaryAdvanceDto dto:DriverAdvanvce) {
					 penaltyTotal += dto.getADVANCE_AMOUNT();
				}
				model.put("penaltyTotal",Utility.round(penaltyTotal, 2));
				model.put("DriverAdvanvce",DriverAdvanvce);
			}
			
			vehicleDetails  = vehicleService.getVehicel(trip.getVid(), userDetails.getCompany_id());
			if(vehicleDetails.getSubCompanyId() != null )
			{
				SubCompany = subCompanyService.getSubCompanyByVehicle(vehicleDetails.getSubCompanyId());
				model.put("SubCompany", SubCompany);
			}
			
			List<TripSheetIncomeDto>  incomeDto	=  tripSheetService.findAllTripSheetIncome(id, userDetails.getCompany_id());
			List<LoadingSheetToTripSheetDto> loadingSheetDto = loadingSheetToTripSheetService.getLoadingSheetTotripSheetDetailsByTripSheetId(userDetails.getCompany_id(),id);
			// get Trip sheet id to get all Income Details
			model.put("TripSheetIncome", incomeDto);
			model.put("LoadingSheetDetails", loadingSheetDto);
			
			//Dev Y Code for Printing TripSheetTollExpense Start
			ValueObject object = tollExpensesDetailsService.getTollExpensesDetailsList(id, userDetails.getCompany_id());
			model.put("TripSheetTollExpense",(object.get("ExpenseName")));
			model.put("TripSheetTollExpenseName",config.get(TollApiConfiguration.TOLL_EXPENSE_NAME));		
			model.put("TripSheetTollExpenseTotalAmount",object.get("totalAmount"));
			//Dev Y Code for Printing TripSheetTollExpense End
			BusBookingDetailsDto	busDetailsDto	=  busBookingDetailsService.getBusBookingDetailsByTripSheetId(trip.getTripSheetID());
			if(busDetailsDto != null && trip.getTripUsageKM() != null & busDetailsDto.getRate() != null) {
				model.put("busBookingAmount", Utility.round((busDetailsDto.getRate() * trip.getTripUsageKM()), 2));
			}
			model.put("busBooking", busDetailsDto);
			
			if(showTripsheetDueAmount) {
				model.put("TripsheetDueAmount",
						tripSheetService.getDueAmountListByTripsheetId(id, userDetails.getCompany_id()));
				model.put("TotalDueAmount", tofixedTwo.format(tripSheetService.getTotalDueAmountByTripsheetId(id, userDetails.getCompany_id())));
				totalDueAmount = Double.parseDouble(model.getOrDefault("TotalDueAmount",0)+"");
			}

			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalAdvance = trip.getTripTotalAdvance();
			if (trip.getTripTotalAdvance() == null) {
				TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("advanceTotal", df.format(TotalAdvance));

			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("expenseTotal", df.format(TotalExpense));
			model.put("balance", df.format(TotalAdvance-TotalExpense));

			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double Totalincome = trip.getTripTotalincome();
			if (trip.getTripTotalincome() == null) {
				Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("incomeTotal", tofixedTwo.format(Totalincome));
			
			if((boolean) configuration.get("roundFigureAmount")){
				TotalExpense = (double) Math.round(TotalExpense);
				Totalincome = (double) Math.round(Totalincome);
				TotalAdvance = (double) Math.round(TotalAdvance);
			}
			 
			if((boolean) configuration.get("reverseDriverBalance")) {
				driverBalance = TotalExpense - (Totalincome + TotalAdvance);
			}else {
				if((boolean) configuration.get("getDriverBalance"))
				{
					driverBalance = Utility.round((TotalAdvance - TotalExpense), 2);
				}
				else
				{
				  driverBalance =(Totalincome + TotalAdvance) - TotalExpense - totalDueAmount;
				}
			}
			
			 model.put("driverBalance",tofixedTwo.format(driverBalance));
			 tollAmount=	object.getDouble("totalAmount", 0);
				
				if (trip.getTripStutes().equals("CLOSED") || trip.getTripStutes().equals("A/C CLOSED")) {
				// show Halt data
				model.put("TripSheetHalt",tripSheetBL.Driver_Halt_Display(driverHaltService.Find_list_TripSheet_DriverHalt(id,userDetails.getCompany_id())));
				VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Fuel_Entries_Show(vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(trip.getVid(), userDetails.getCompany_id()));
				model.put("vehicle", vehicle);
				// show fuel entries in this tripsheet
				List<FuelDto> ShowAmount = fuelService.Get_TripSheet_IN_FuelTable_List(id, userDetails.getCompany_id(), userDetails.getId());
				model.put("fuel", fuelBL.prepareListofFuel(ShowAmount));
				fuelAmount= fuelBL.prepareTotalAmount_Tripsheet_Created_fuel_details(ShowAmount);
				model.put("fTDiesel", fuelBL.prepareTotal_Tripsheet_fuel_details(ShowAmount));
				model.put("fTUsage", fuelBL.prepareTotalUsage_Tripsheet_fuel_details(ShowAmount));
				model.put("fTAmount", numberFormat.format(fuelBL.prepareTotalAmount_Tripsheet_fuel_details(ShowAmount)));
				
				if(ShowAmount != null && !ShowAmount.isEmpty()) {
					
					List<FuelDto> fuelList = fuelBL.prepareListofFuel(ShowAmount);
					int    size 	 = 0;
					double totalKmpl = 0;
					double totalCostAvg = 0;
					double totalCost	= 0.0; 
					double diffAvgCost	= 0.0; 
					for(FuelDto dto : fuelList) {
						if(dto.getFuel_kml() != null && dto.getFuel_tank() == 0) {
							kmpl += dto.getFuel_kml();
							totalCost += dto.getFuel_cost();
							diffAvgCost += dto.getFuelPriceDiff();
							size ++;
						}
						
					}
					if(size  > 0) {
						totalKmpl = kmpl/size;
						totalCostAvg = totalCost/size;
						model.put("totalKmpl", Utility.round(totalKmpl, 2));
						model.put("totalCostAvg",Utility.round(totalCostAvg, 2));
						model.put("diffAvgCost", Utility.round(diffAvgCost/size, 2));
					}
					
				}
				if((boolean) configuration.get("driverBalanceWithNarration")) {
					ValueObject	valueObject = tripSheetBL.getDriverBalanceForTrip(trip, configuration, incomeDto, expenseDto);
					if(valueObject != null) {
						model.put("driverBalanceKey",valueObject.get("driverBalanceKey"));
						model.put("balanceAmount",valueObject.get("balanceAmount"));
					}
					
				}
				
				List<UreaEntriesDto> ureaDeatils = UreaEntriesService.getUreaEntriesDetailsByTripSheetId(id, userDetails.getCompany_id());
				if(ureaDeatils != null) {
				model.put("urea", ureaDeatils);
				model.put("totalUrea", ureaEntriesBL.prepareTotal_Tripsheet_Urea_details(ureaDeatils));
				ureaAmount = ureaEntriesBL.prepareTotal_Tripsheet_Urea_Amount(ureaDeatils);
				model.put("totalUreaAmnt",ureaAmount);
				}
				
				if((boolean) configuration.get("roundFigureAmount")){
					Totalincome		=  (double) Math.round(Totalincome);
					fuelAmount		=  (double) Math.round(fuelAmount);
					ureaAmount		=  (double) Math.round(ureaAmount);
					tollAmount		=  (double) Math.round(tollAmount);
					TotalExpense	=  (double) Math.round(TotalExpense);
				}
				
				refreshmentObj		= refreshmentEntryService.getRefreshmentEntryListByTripSheetId(trip.getTripSheetID());
				refreshmentList		= (List<RefreshmentEntryDto>) refreshmentObj.get("refreshment");
				refreshmentTotalAmount 	= refreshmentObj.getDouble("grandTotal",0) - refreshmentObj.getDouble("totalReturnAmount",0);

				if(refreshmentList !=null && !refreshmentList.isEmpty()) {
					model.put("totalConsumption", refreshmentObj.getDouble("totalConsumption",0));
					model.put("totalRQty", refreshmentObj.getDouble("totalRQty",0));
					model.put("totalQty", refreshmentObj.getDouble("totalQty",0));
					model.put("grandTotal", refreshmentObj.getDouble("grandTotal",0));
					model.put("totalReturnAmount", refreshmentObj.getDouble("totalReturnAmount",0));
					model.put("refreshmentTotalAmount",refreshmentTotalAmount);
					
					model.put("refreshmentList", refreshmentList);
				}
				
				if((boolean) configuration.get("roundFigureAmount")){
					model.put("tripTotalincome",  Math.round((Totalincome-(fuelAmount + ureaAmount + tollAmount + TotalExpense + refreshmentTotalAmount ))));
				}else {
					model.put("tripTotalincome", numberFormat.format(Totalincome-(fuelAmount + ureaAmount + tollAmount + TotalExpense + refreshmentTotalAmount)));
				}
				
			}
				
				model.put("preparedBy", trip.getTripCreatedBy());
				model.put("authorizedBy", trip.getClosedBy());	
				
				if (trip.getTripCommentTotal() != null && trip.getTripCommentTotal() != TRIP_SHEET_DEFALAT_ZERO) {
					model.put("TripComment", tripSheetBL.TripSheetComment_Display(
							tripCommentService.list_TripSheet_ID_TO_TripSheetComment(id, userDetails.getCompany_id())));
				}

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return new ModelAndView("showTripSheetPrint", model);
	}

	// Note: This /adddispatchTripSheet Controller Is Trip Sheet SAVED Status TO
	// Make Dispatch Details Show Page To Dispatch
	@RequestMapping(value = "/adddispatchTripSheet", method = RequestMethod.GET)
	public ModelAndView adddispatchTripSheet(@ModelAttribute("command") TripSheet TripSheet,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration	= null;

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails));
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);
			model.put("configuration", configuration);
			// get Trip sheet id to get all Advance Details
			model.put("TripSheetAdvance",
					tripSheetService.findAllTripSheetAdvance(TripSheet.getTripSheetID(), userDetails.getCompany_id()));

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("addDispatchTripSheet", model);
	}

	// Note: This /addAdvance Controller is TripSheet ID to Show Add Advance
	// Page
	@RequestMapping(value = "/addAdvance", method = RequestMethod.GET)
	public ModelAndView addAdvance(@ModelAttribute("command") TripSheetDto TripSheet, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		HashMap<String, Object> 	configuration	= null; 
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG); 
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails));
			// get the Trip sheet ID to Details
			if(trip != null) {
				
				model.put("TripSheet", trip);
				model.put("configuration", configuration);
				model.put("companyId", userDetails.getCompany_id());
				model.put("userId", userDetails.getId());

				UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
				
				model.put("paidBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
				model.put("paidById", userDetails.getId());
				model.put("place", Orderingname.getBranch_name());
				model.put("placeId", Orderingname.getBranch_id());
				
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("addAdvanceTripSheet", model);
	}

	// Note: This /updateAdvance Controller is TripSheet ID to update TripSheet
	// Advance and also Amount
	@RequestMapping(value = "/updateAdvance", method = RequestMethod.POST)
	public ModelAndView updateAdvanceTripsheet(@ModelAttribute("command") TripSheetDto TripSheet,
			TripSheetAdvance TripSheetAdvance, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripSheet tsheet = new TripSheet();
			
			tsheet.setTripSheetID(TripSheet.getTripSheetID());
			
			TripSheetAdvance TripAdvance = tripSheetBL.UpdateTripAdvance(TripSheetAdvance);
			TripAdvance.setCompanyId(userDetails.getCompany_id());
			TripAdvance.setCreatedById(userDetails.getId());
			TripAdvance.setDriverId(TripSheet.getAdvanceDriverId());
			
			
			TripAdvance.setTripsheet(tsheet);

			tripSheetService.addTripSheetAdvance(TripAdvance);
			model.put("updateAdvance", true);

			// Note : This Update TripSheet Total Advance Updating
			// The Value Of Total Advance amount to TripSheet
			tripSheetService.UPDATE_TOTALADVANCE_IN_TRIPSHEET_TRIPSHEETADVANCE_ID(TripSheet.getTripSheetID(),
					userDetails.getCompany_id());

		} catch (Exception e) {
			model.put("notUpdateAdvance", true);
			e.printStackTrace();
		} finally {
			userDetails = null;
		}
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "", model);
	}

	// Note: This /removeAdvance Controller is TripSheet ID to removeAdvance
	// TripSheet and also Amount
	@RequestMapping(value = "/removeAdvance", method = RequestMethod.GET)
	public ModelAndView removeAdvanceTripsheet(@ModelAttribute("command") TripSheetAdvance TripSheetAdvance,
			BindingResult result) {

		Long TripSheetID = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			TripSheetAdvance TripAdvance = tripSheetService.getTripSheetAdvance(TripSheetAdvance.getTripAdvanceID(),
					userDetails.getCompany_id());

			TripSheetID = TripAdvance.getTripsheet().getTripSheetID();
			tripSheetService.deleteTripSheetAdvance(TripAdvance.getTripAdvanceID(), userDetails.getCompany_id());

			// Note : This Update TripSheet Total Advance Updating
			// The Value Of Total Advance amount to TripSheet
			tripSheetService.UPDATE_TOTALADVANCE_IN_TRIPSHEET_TRIPSHEETADVANCE_ID(TripSheetID,
					userDetails.getCompany_id());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addAdvance.in?tripSheetID=" + TripSheetID + "");
	}

	// Note: This /addExpense Controller is TripSheet ID to Show Add Expense
	// Page
	@RequestMapping(value = "/addExpense", method = RequestMethod.GET)
	public ModelAndView addExpense(@ModelAttribute("command") TripSheetDto TripSheet, BindingResult result,HttpServletRequest request) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		HashMap<String, Object>    	config						= null;
		HashMap<String, Object>    	tripRouteConfig				= null;
		HashMap<String, Object>    	driverconfig				= null;
		boolean						allowShortCutInTripSheet	= false;
		boolean  expenseOutOfRange = false;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("ADD_TRIP_ROUTE_EXPENSE_OUT_OF_RANGE"))) {
				expenseOutOfRange = true;
			}
			config	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			tripRouteConfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIP_ROUTE_CONFIGURATION_CONFIG);
			driverconfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			
			model.put("tripRouteConfig", tripRouteConfig);
			model.put("configuration", config);
			model.put("driverconfig", driverconfig);
			
			allowShortCutInTripSheet = (boolean) config.getOrDefault(TripSheetConfigurationConstant.ALLOW_SHORTCUT_IN_TRIPSHEET, false);
			model.put("AllowShortCutInTripSheet", allowShortCutInTripSheet);

			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails));
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);
			ObjectMapper objectMapper = new ObjectMapper();
			if((boolean) tripRouteConfig.get("tripRouteExpenseMaxLimit")) {
				HashMap<Integer, Double>  expenseLimitHM = tripRouteExpenseRangeService.getExpenseWiseMaxLimit(trip.getRouteID());
				model.put("expenseLimitHM", objectMapper.writeValueAsString(expenseLimitHM));
			}
			
			if((boolean) config.getOrDefault("includeServiceEntryAsExpense", false)) {
				model.put("serviceCount", serviceEntriesService.getServiceEntriesTallyPendingCount(userDetails.getCompany_id(), trip.getVid()));
			}
			if((boolean) config.getOrDefault("includeRenewalAsExpense", false)) {
				model.put("renewalCount", renewalReminderService.getRenewalTallyPendingCount(userDetails.getCompany_id(), trip.getVid()));
				
			}
			
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
			model.put("config", config);
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			
			model.put("tripConfiguration", objectMapper.writeValueAsString(config));
			model.put("expenseOutOfRange", expenseOutOfRange);
			
			

		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			config						= null;
			allowShortCutInTripSheet	= false;
		}
		return new ModelAndView("addExpenseTripSheet", model);
	}


	// Note: This /addExpense Controller is TripSheet ID to Show Add Extra Options
	// Page
	@RequestMapping(value = "/addExtraOptions", method = RequestMethod.GET)
	public ModelAndView addExtra(@ModelAttribute("command") TripSheetDto TripSheet, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails));
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);

			// get Trip sheet id to get all Extra Details
			model.put("TripSheetExtraOptions",
					tripSheetService.findAllTripSheetExtraOptions(TripSheet.getTripSheetID(), userDetails.getCompany_id()));




		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("addExtraOptionsTripSheet", model);
	}


	// Note: This /updateExpense Controller is TripSheet ID to update Expense
	// and Amount
	@RequestMapping(value = "/updateExpense", method = RequestMethod.POST)
	public ModelAndView updateExpenseTripsheet(@ModelAttribute("command") TripSheetDto TripSheet,
			TripSheetExpenseDto TripSheetExpense, @RequestParam("expenseName") final String[] expenseName,
			@RequestParam("Amount") final String[] expenseAmount,
			@RequestParam("vendorId") final String[] vendorId,
			@RequestParam("isCredit") final String[] isCredit,
			@RequestParam("tallyCompanyId") final String[] tallyCompanyId,
			@RequestParam("description") final String[] remark,
			@RequestParam("expenseRefence") final String[] expenseRefence, final HttpServletRequest request)
					throws Exception {
		
		Map<String, Object> 		model 							= new HashMap<String, Object>();
		CustomUserDetails 			userDetails 					= null;
		HashMap<String, Object>    	config							= null;
		boolean						allowMultipleExpensesOfSameType	= false;
		List<TripSheetExpense> 		validate						= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			config			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			allowMultipleExpensesOfSameType	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.ALLOW_MULTIPLE_EXPENSES_OF_SAME_TYPE, false);

			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
			Integer expensePlaceId = Orderingname.getBranch_id();

			TripSheet tsheet = new TripSheet();
			tsheet.setTripSheetID(TripSheet.getTripSheetID());

			for (int i = TRIP_SHEET_DEFALAT_ZERO; i < expenseName.length; i++) {
				TripSheetExpense TripExpense = new TripSheetExpense();
				TripExpense.setExpenseId(Integer.parseInt(expenseName[i] + ""));
				TripExpense.setExpenseAmount(Double.parseDouble("" + expenseAmount[i]));
				TripExpense.setExpensePlaceId(expensePlaceId);
				TripExpense.setExpenseRefence(expenseRefence[i]);
				TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
				TripExpense.setCreatedById(userDetails.getId());
				TripExpense.setTripsheet(tsheet);
				TripExpense.setCompanyId(userDetails.getCompany_id());
				if((boolean)config.get("showCreditAndVendorAtExpense")) {
					TripExpense.setVendorId(Integer.parseInt(vendorId[i]+""));
					TripExpense.setCredit(Boolean.parseBoolean(isCredit[i]+""));
					TripExpense.setTallyCompanyId(Long.parseLong(tallyCompanyId[i]+""));
					if(TripSheet.getVoucherDateStr() != null)
						TripExpense.setVoucherDate(DateTimeUtility.getDateTimeFromStr(TripSheet.getVoucherDateStr(), DateTimeUtility.DD_MM_YYYY));
					TripExpense.setRemark(remark[i]);
				}
				
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				TripExpense.setCreated(toDate);
				
				if(!allowMultipleExpensesOfSameType) {
				validate = tripSheetService.ValidateAllTripSheetExpense(
						Integer.parseInt("" + expenseName[i]), tsheet.getTripSheetID(), userDetails.getCompany_id());
				}
				
				if (validate != null && !validate.isEmpty()) {
					model.put("alreadyExpense", true);

				} else {
					TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());
					tripSheetService.addTripSheetExpense(TripExpense);
					model.put("updateExpense", true);

					Object[] expenseIds = tripSheetService
							.getDriverAdvanceAndBataExpenseId(userDetails.getCompany_id());

					// Note : This Update Bata Details in Trip Sheet

					if (expenseIds != null) {
						if (expenseIds[0] != null && (Integer) expenseIds[0] == Integer.parseInt(expenseName[i] + "")) {

						}
						if (expenseIds[1] != null && (Integer) expenseIds[1] == Integer.parseInt(expenseName[i] + "")) {
							tripSheetService.Update_TripSheet_FIRST_Driver_BATA(
									Double.parseDouble("" + expenseAmount[i]), TripSheet.getTripSheetID(), userDetails.getCompany_id());
						}
						if (expenseIds[2] != null && (Integer) expenseIds[2] == Integer.parseInt(expenseName[i] + "")) {

						}
						if (expenseIds[3] != null && (Integer) expenseIds[3] == Integer.parseInt(expenseName[i] + "")) {
							tripSheetService.Update_TripSheet_SECOND_Driver_BATA(
									Double.parseDouble("" + expenseAmount[i]), TripSheet.getTripSheetID(), userDetails.getCompany_id());
						}
						if (expenseIds[4] != null && (Integer) expenseIds[4] == Integer.parseInt(expenseName[i] + "")) {

						}
						if (expenseIds[5] != null && (Integer) expenseIds[5] == Integer.parseInt(expenseName[i] + "")) {
							tripSheetService.Update_TripSheet_CLEANER_BATA(Double.parseDouble("" + expenseAmount[i]),
									TripSheet.getTripSheetID(), userDetails.getCompany_id());
						}
					}

				  }
			   

			}

			// Note : This Update TripSheet Total Expense Updating The Value Of
			// Total Expense amount to TripSheet
			tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(TripSheet.getTripSheetID(), userDetails.getCompany_id());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addExpense.in?tripSheetID=" + TripSheet.getTripSheetID() + "", model);
	}


	// Note: This /removeExpense Controller is TripSheet ID to removeAdvance and
	// also Amount
	@RequestMapping(value = "/removeExpense", method = RequestMethod.GET)
	public ModelAndView removeExpenseTripsheet(@ModelAttribute("command") TripSheetExpense TripSheetExpense,
			BindingResult result) {

		Long TripSheetID = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripSheetExpense TripAdvance = tripSheetService.getTripSheetExpense(TripSheetExpense.getTripExpenseID(),
					userDetails.getCompany_id());

			TripSheetID = TripAdvance.getTripsheet().getTripSheetID();

			// Remove the Trip Expense value details to Bata value to zero

			Object[] expenseIds = tripSheetService.getDriverAdvanceAndBataExpenseId(userDetails.getCompany_id());

			if (expenseIds != null) {
				if (expenseIds[0] != null && expenseIds[0] == TripAdvance.getExpenseId()) {

				}
				if (expenseIds[1] != null && expenseIds[1] == TripAdvance.getExpenseId()) {
					tripSheetService.Update_TripSheet_FIRST_Driver_BATA(TRIP_SHEET_DEFALAT_AMOUNT, TripSheetID, userDetails.getCompany_id());
				}
				if (expenseIds[2] != null && expenseIds[2] == TripAdvance.getExpenseId()) {

				}
				if (expenseIds[3] != null && expenseIds[3] == TripAdvance.getExpenseId()) {
					tripSheetService.Update_TripSheet_SECOND_Driver_BATA(TRIP_SHEET_DEFALAT_AMOUNT, TripSheetID, userDetails.getCompany_id());
				}
				if (expenseIds[4] != null && expenseIds[4] == TripAdvance.getExpenseId()) {

				}
				if (expenseIds[5] != null && expenseIds[5] == TripAdvance.getExpenseId()) {
					tripSheetService.Update_TripSheet_CLEANER_BATA(TRIP_SHEET_DEFALAT_AMOUNT, TripSheetID, userDetails.getCompany_id());
				}
				
			}
			if(TripAdvance.getExpenseFixedId() == TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_RENEWAL && TripAdvance.getTransactionId() != null && TripAdvance.getTransactionId() > 0) {
				renewalReminderService.updatePendingStatus(TripAdvance.getTransactionId(), (short) 0);
			}
			if(TripAdvance.getExpenseFixedId() == TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_SE && TripAdvance.getTransactionId() != null && TripAdvance.getTransactionId() > 0) {
				serviceEntriesDao.updatePendingStatus(TripAdvance.getTransactionId(), (short) 0);
			}
			tripSheetService.deleteTripSheetExpense(TripSheetExpense.getTripExpenseID(), userDetails.getCompany_id());

			// Note : This Update TripSheet Total Expense Updating The Value Of
			// Total Expense amount to TripSheet
			tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(TripSheetID, userDetails.getCompany_id());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addExpense.in?tripSheetID=" + TripSheetID + "");
	}
	
	
	@RequestMapping(value = "/removeExpenseAccount", method = RequestMethod.GET)
	public ModelAndView removeExpenseAccount(@ModelAttribute("command") TripSheetExpense TripSheetExpense,
			BindingResult result) {

		Long TripSheetID = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripSheetExpense TripAdvance = tripSheetService.getTripSheetExpense(TripSheetExpense.getTripExpenseID(),
					userDetails.getCompany_id());

			TripSheetID = TripAdvance.getTripsheet().getTripSheetID();
			
			TripSheet	sheet	= tripSheetService.getTripSheet(TripSheetID);
			
			// Remove the Trip Expense value details to Bata value to zero
			
			
				ValueObject		valueObject	= new ValueObject();
				valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
				valueObject.put("txnAmount", TripAdvance.getExpenseAmount());
				valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(sheet.getClosetripDate()));
				valueObject.put("txnTypeId", TripSheetID);
				valueObject.put("expenseId", TripAdvance.getExpenseId());
				valueObject.put("vid", sheet.getVid());
				valueObject.put("companyId", userDetails.getCompany_id());
				valueObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, TripAdvance.getTripExpenseID());
				

			Object[] expenseIds = tripSheetService.getDriverAdvanceAndBataExpenseId(userDetails.getCompany_id());

			if (expenseIds != null) {
				if (expenseIds[0] != null && expenseIds[0] == TripAdvance.getExpenseId()) {

				}
				if (expenseIds[1] != null && expenseIds[1] == TripAdvance.getExpenseId()) {
					tripSheetService.Update_TripSheet_FIRST_Driver_BATA(TRIP_SHEET_DEFALAT_AMOUNT, TripSheetID, userDetails.getCompany_id());
				}
				if (expenseIds[2] != null && expenseIds[2] == TripAdvance.getExpenseId()) {

				}
				if (expenseIds[3] != null && expenseIds[3] == TripAdvance.getExpenseId()) {
					tripSheetService.Update_TripSheet_SECOND_Driver_BATA(TRIP_SHEET_DEFALAT_AMOUNT, TripSheetID, userDetails.getCompany_id());
				}
				if (expenseIds[4] != null && expenseIds[4] == TripAdvance.getExpenseId()) {

				}
				if (expenseIds[5] != null && expenseIds[5] == TripAdvance.getExpenseId()) {
					tripSheetService.Update_TripSheet_CLEANER_BATA(TRIP_SHEET_DEFALAT_AMOUNT, TripSheetID, userDetails.getCompany_id());
				}
			}
			if(TripAdvance.getExpenseFixedId() == TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_RENEWAL && TripAdvance.getTransactionId() != null && TripAdvance.getTransactionId() > 0) {
				renewalReminderService.updatePendingStatus(TripAdvance.getTransactionId(), (short) 0);
			}
			if(TripAdvance.getExpenseFixedId() == TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_SE && TripAdvance.getTransactionId() != null && TripAdvance.getTransactionId() > 0) {
				serviceEntriesDao.updatePendingStatus(TripAdvance.getTransactionId(), (short) 0);
			}
			tripSheetService.deleteTripSheetExpense(TripSheetExpense.getTripExpenseID(), userDetails.getCompany_id());

			// Note : This Update TripSheet Total Expense Updating The Value Of
			// Total Expense amount to TripSheet
			tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(TripSheetID, userDetails.getCompany_id());
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

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addExpense.in?tripSheetID=" + TripSheetID + "");
	}


	// Note: This /updateExpense Controller is TripSheet ID to update ExtaOptions
	// and Quantity
	@RequestMapping(value = "/updateExtraOptions", method = RequestMethod.POST)
	public ModelAndView updateExtraOptions(@ModelAttribute("command") TripSheetDto TripSheet,
			TripSheetOptionsExtra TripSheetOptionsExtra, @RequestParam("tripsheetextraname") final String[] tripsheetextraname,
			@RequestParam("TripSheetExtraQuantity") final String[] TripSheetExtraQuantity,
			@RequestParam("TripSheetExtraDescription") final String[] TripSheetExtraDescription, final HttpServletRequest request)
					throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			TripSheet tsheet = new TripSheet();
			tsheet.setTripSheetID(TripSheet.getTripSheetID());

			for (int i = TRIP_SHEET_DEFALAT_ZERO; i < tripsheetextraname.length; i++) {



				TripSheetOptionsExtra TripExtraOptions = new TripSheetOptionsExtra();
				TripExtraOptions.setTripsheetoptionsID(Long.parseLong(tripsheetextraname[i] + ""));
				TripExtraOptions.setTripSheetExtraQuantity(Double.parseDouble("" + TripSheetExtraQuantity[i]));
				TripExtraOptions.setTripSheetExtraDescription(TripSheetExtraDescription[i]);
				TripExtraOptions.setCreatedById(userDetails.getId());
				TripExtraOptions.setTripsheet(tsheet);
				TripExtraOptions.setCompanyId(userDetails.getCompany_id());
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				TripExtraOptions.setCreated(toDate);


				List<TripSheetOptionsExtra> validate = tripSheetService.ValidateAllTripSheetExtraOptions(
						Long.parseLong("" + tripsheetextraname[i]), tsheet.getTripSheetID(), userDetails.getCompany_id());

				if (validate != null && !validate.isEmpty()) {
					model.put("alreadyExta", true);

				} else {

					tripSheetService.addTripSheetExtraOption(TripExtraOptions);						
					model.put("updateExtra", true);


				}

			}


		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "", model);
	}


	// Note: This /removeExpense Controller is TripSheet ID to removeAdvance and
	// also Amount
	@RequestMapping(value = "/removeExtra", method = RequestMethod.GET)
	public ModelAndView removeExtraTripsheet(@ModelAttribute("command") TripSheetOptionsExtra TripSheetOptionsExtra,
			BindingResult result) {

		Long TripSheetID = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			TripSheetOptionsExtra TripExtra = tripSheetService.getTripSheetExtra(TripSheetOptionsExtra.getTripExtraID(),
					userDetails.getCompany_id());

			TripSheetID = TripExtra.getTripsheet().getTripSheetID();
			tripSheetService.deleteTripSheetExtra(TripSheetOptionsExtra.getTripExtraID(), userDetails.getCompany_id());

			// Note : This Update TripSheet Total Expense Updating The Value Of
			// Total Expense amount to TripSheet
			//tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(TripSheetID);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addExtraOptions.in?tripSheetID=" + TripSheetID + "");
	}


	// Note: This /addIncome Controller is TripSheet ID to Show Add Income Page
	@RequestMapping(value = "/addIncome", method = RequestMethod.GET)
	public ModelAndView addIncome(@ModelAttribute("command") TripSheetDto TripSheet, BindingResult result) {
		Map<String, Object> 		model						= new HashMap<String, Object>();
		boolean						allowShortCutInTripSheet	= false;
		boolean						showNetIncomeAmount			= false;
		HashMap<String, Object>    	config						= null;
		java.util.Date 				date						= null;
		String 						currentDate					= null;
		CustomUserDetails 			userDetails					= null;
		TripSheetDto 				trip						= null;
		TripSheetDto 				tripBL						= null;
		List<TripSheetIncomeDto>	tripSheetIncomeDto			= null;
		DecimalFormat 				decimalFormat				= null;
		Double 						Totalincome					= null;
		
		
		try {
			date 						= new java.util.Date();
		    currentDate					= dateFormat.format(date);  
		    decimalFormat 				= new DecimalFormat("##,##,##0");
		    decimalFormat.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			userDetails 				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			config						= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			allowShortCutInTripSheet 	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.ALLOW_SHORTCUT_IN_TRIPSHEET, false);
			showNetIncomeAmount 		= (boolean) config.getOrDefault(TripSheetConfigurationConstant.SHOW_TRIPSHEET_NET_INCOME_AMOUNT, false);
			
			trip						= tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails);
			tripBL 						= tripSheetBL.GetTripSheetDetails(trip);
			tripSheetIncomeDto			= tripSheetService.findAllTripSheetIncome(TripSheet.getTripSheetID(), userDetails.getCompany_id());
			
			if (tripBL.getTripTotalincome() == null) {
				Totalincome 			= TRIP_SHEET_DEFALAT_AMOUNT;
			}else {
				Totalincome 			= tripBL.getTripTotalincome();
			}
			
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			model.put("config", config);
			model.put("TripSheet", tripBL);
			model.put("currentDate", currentDate);
			model.put("AllowShortCutInTripSheet", allowShortCutInTripSheet);
			model.put("showNetIncomeAmount", showNetIncomeAmount);
			model.put("TripSheetIncome",tripSheetIncomeDto );
			model.put("incomeTotal", decimalFormat.format(Totalincome));

		} catch (Exception e) {
			LOGGER.error("Err"+e);
			e.printStackTrace();
		}
		return new ModelAndView("addIncomeTripSheet", model);
	}

	// Note: This /updateIncome Controller is TripSheet ID to update InCome and
	// also Amount
	@RequestMapping(value = "/updateIncome", method = RequestMethod.POST)
	public ModelAndView updateIncomeTripsheet(@ModelAttribute("command") TripSheetDto TripSheet,
			TripSheetIncome TripSheetIncome,
			@RequestParam("incomeName") final String[] incomeName,
			@RequestParam("route") final Integer[] route,
			@RequestParam("gst") final Double[] gst,
			@RequestParam("commission") final Double[] commission,
			@RequestParam("incomeDate") final String[] tripsheetIncomeDate,
			@RequestParam("Amount") final String[] incomeAmount,
			@RequestParam("netAmount") final Double[] netIncomeAmount,
			@RequestParam("remark") final String[] remark,
			@RequestParam("incomeRefence") final String[] incomeRefence, final HttpServletRequest request)
					throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean				showNetIncomeAmount			= false; 
		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			String name = userDetails.getEmail();

			showNetIncomeAmount = (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.SHOW_TRIPSHEET_NET_INCOME_AMOUNT, false);
			
			
			UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(name);

			Integer incomePlace = Orderingname.getBranch_id();

			TripSheet tsheet = new TripSheet();
			tsheet.setTripSheetID(TripSheet.getTripSheetID());

			for (int i = TRIP_SHEET_DEFALAT_ZERO; i < incomeName.length; i++) {

				TripSheetIncome TripIncome = new TripSheetIncome();
				
				TripIncome.setIncomeId(Integer.parseInt("" + incomeName[i]));
				if(showNetIncomeAmount) {
					TripIncome.setIncomeAmount(Double.parseDouble("" + netIncomeAmount[i]));
					TripIncome.setGst(gst[i]);
					TripIncome.setCommission(commission[i]);
					TripIncome.setRouteID(route[i]);
					TripIncome.setRemark(""+remark[i]);
					
					TripIncome.setTripsheetIncomeDate(DateTimeUtility.getTimeStamp(tripsheetIncomeDate[i]));
				}else {
					TripIncome.setIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
				}
				
				TripIncome.setIncomePlaceId(incomePlace);
				TripIncome.setIncomeRefence(incomeRefence[i]);
				TripIncome.setIncomeCollectedById(userDetails.getId());
				TripIncome.setCompanyId(userDetails.getCompany_id());
				TripIncome.setCreatedById(userDetails.getId());
				TripIncome.setTripsheet(tsheet);
				if(showNetIncomeAmount) {
					TripIncome.setNetIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
				}else {
					TripIncome.setNetIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
				}
				
				
				//TripIncome.setTripsheetIncomeDate(toDate);
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				TripIncome.setCreated(toDate);
				List<TripSheetIncome> validate =	null;
				if(!(boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_MULTIPLE_INCOME_OF_SAMETYPE)) {
					
					 validate = tripSheetService.ValidateAllTripSheetIncome(
							Integer.parseInt("" + incomeName[i]), tsheet.getTripSheetID(), userDetails.getCompany_id());
				}
				if (validate != null && !validate.isEmpty()) {
					model.put("alreadyIncome", true);

				} else {

					tripSheetService.addTripSheetIncome(TripIncome);
					model.put("updateIncome", true);
				}

			}

			// Note : This Update TripSheet Total Income Updating The Value Of
			// Total Income amount to TripSheet
			tripSheetService.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(TripSheet.getTripSheetID(), userDetails.getCompany_id());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "", model);
	}

	// Note: This /removeIncome Controller is TripSheet ID to remove Income and
	// also Amount
	@RequestMapping(value = "/removeIncome", method = RequestMethod.GET)
	public ModelAndView removeIncomeTripsheet(@ModelAttribute("command") TripSheetIncome TripSheetIncome,
			BindingResult result) {

		Long TripSheetID = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripSheetIncome tripIncome = tripSheetService.getTripSheetIncome(TripSheetIncome.getTripincomeID(),
					userDetails.getCompany_id());

			TripSheetID = tripIncome.getTripsheet().getTripSheetID();
			tripSheetService.deleteTripSheetIncome(tripIncome.getTripincomeID(), userDetails.getCompany_id());

			// Note : This Update TripSheet Total Income Updating The Value Of
			// Total Income amount to TripSheet
			tripSheetService.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(TripSheetID, userDetails.getCompany_id());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addIncome.in?tripSheetID=" + TripSheetID + "");
	}
	
	@RequestMapping(value = "/removeIncomeAccount", method = RequestMethod.GET)
	public ModelAndView removeIncomeAccount(@ModelAttribute("command") TripSheetIncome TripSheetIncome,
			BindingResult result) {
		
		Long TripSheetID = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripSheetIncome tripIncome = tripSheetService.getTripSheetIncome(TripSheetIncome.getTripincomeID(),
					userDetails.getCompany_id());
			if(tripIncome != null) {
				if(tripIncome.getIncomeAmount() != null && tripIncome.getIncomeAmount() > 0) {
				
					ValueObject		valueObject	= new ValueObject();
					TripSheet	tripSheet	= tripSheetService.getTripSheet(tripIncome.getTripsheet().getTripSheetID());
					valueObject.put("tripSheet", tripSheet);
					valueObject.put("userDetails", userDetails);
					valueObject.put("txnTypeId", tripSheet.getTripSheetID());
					valueObject.put("tripIncome", tripIncome);
					valueObject.put("vid", tripSheet.getVid());
					valueObject.put("incomeId", tripIncome.getIncomeId());
					valueObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, tripIncome.getTripincomeID());
					valueObject.put("incomeAmount", tripIncome.getIncomeAmount());
					valueObject.put("transactionDate", DateTimeUtility.geTimeStampFromDate(tripSheet.getClosetripDate()));
					
					vehicleProfitAndLossService.runThreadForDeleteIncome(valueObject);
				}
				
				TripSheetID = tripIncome.getTripsheet().getTripSheetID();
				tripSheetService.deleteTripSheetIncome(tripIncome.getTripincomeID(), userDetails.getCompany_id());
				
				// Note : This Update TripSheet Total Income Updating The Value Of
				// Total Income amount to TripSheet
				tripSheetService.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(TripSheetID, userDetails.getCompany_id());
			}

		} catch (Exception e) {
			LOGGER.error("Error : ", e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addIncome.in?tripSheetID=" + TripSheetID + "");
	}

	// Note: This /addExpenseAccount Controller is TripSheet ID to After Closed
	// Trip Show Account Department Add Expense Page
	@RequestMapping(value = "/addExpenseAccount", method = RequestMethod.GET)
	public ModelAndView addExpenseAccount(@ModelAttribute("command") TripSheetDto TripSheet, BindingResult result) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		HashMap<String, Object> 	configuration				= null;
		boolean						allowShortCutInTripSheet	= false;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			allowShortCutInTripSheet = (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.ALLOW_SHORTCUT_IN_TRIPSHEET, false);
			
			model.put("AllowShortCutInTripSheet", allowShortCutInTripSheet);
			model.put("configuration", configuration); 
			model.put("companyId", userDetails.getCompany_id()); 
			model.put("userId", userDetails.getId()); 
			
			
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails));
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);
			
			if((boolean) configuration.getOrDefault("includeServiceEntryAsExpense", false)) {
				model.put("serviceCount", serviceEntriesService.getServiceEntriesTallyPendingCount(userDetails.getCompany_id(), trip.getVid()));
			}
			if((boolean) configuration.getOrDefault("includeRenewalAsExpense", false)) {
				model.put("renewalCount", renewalReminderService.getRenewalTallyPendingCount(userDetails.getCompany_id(), trip.getVid()));
				
			}
			
			
			model.put("TripSheetExpense",
					tripSheetService.findAllTripSheetExpense(TripSheet.getTripSheetID(), userDetails.getCompany_id()));
			
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("expenseTotal", df.format(TotalExpense));

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("addExpenseAccount", model); 
	}

	// Note: This /updateExpenseAccount Controller is TripSheet ID to After
	// Closed TripSheet Update Account Department Expense Amount
	@RequestMapping(value = "/updateExpenseAccount", method = RequestMethod.POST)
	public ModelAndView updateExpenseAccountTripsheet(@ModelAttribute("command") TripSheetDto TripSheet,
			TripSheetExpenseDto TripSheetExpense, @RequestParam("expenseName")  String[] expenseName,
			@RequestParam("Amount")  String[] expenseAmount,
			@RequestParam("vendorId") final String[] vendorId,
			@RequestParam("isCredit") final String[] isCredit,
			@RequestParam("tallyCompanyId") final String[] tallyCompanyId,
			@RequestParam("description") final String[] remark,
			@RequestParam("expenseRefence")  String[] expenseRefence, final HttpServletRequest request)
					throws Exception {
		CustomUserDetails userDetails = null;
		HashMap<Long, VehicleProfitAndLossTxnChecker>		expenseTxnCheckerHashMap		= null;
		HashMap<Long, TripSheetExpense> 					tripSheetExpenseHM				= null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>    	config			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			expenseTxnCheckerHashMap	= new HashMap<>();
			tripSheetExpenseHM			= new HashMap<>();

			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
			Integer expensePlaceId = Orderingname.getBranch_id();

			TripSheet tsheet = new TripSheet();
			tsheet.setTripSheetID(TripSheet.getTripSheetID());
			
			TripSheet	sheet	= tripSheetService.getTripSheet(TripSheet.getTripSheetID());
			
			VehicleProfitAndLossTxnChecker		profitAndLossExpenseTxnChecker	= null;

			for (int i = TRIP_SHEET_DEFALAT_ZERO; i < expenseName.length; i++) {

				TripSheetExpense TripExpense = new TripSheetExpense();
				TripExpense.setExpenseId(Integer.parseInt(expenseName[i] + ""));
				TripExpense.setExpenseAmount(Double.parseDouble("" + expenseAmount[i]));
				TripExpense.setExpensePlaceId(expensePlaceId);
				TripExpense.setExpenseRefence(expenseRefence[i]);
				TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
				TripExpense.setCreatedById(userDetails.getId());
				
				if((boolean)config.get("showCreditAndVendorAtExpense")) {
					TripExpense.setVendorId(Integer.parseInt(vendorId[i]+""));
					TripExpense.setCredit(Boolean.parseBoolean(isCredit[i]+""));
					TripExpense.setTallyCompanyId(Long.parseLong(tallyCompanyId[i]+""));
					TripExpense.setRemark(remark[i]);
					if(TripSheet.getVoucherDateStr() != null)
						TripExpense.setVoucherDate(DateTimeUtility.getDateTimeFromStr(TripSheet.getVoucherDateStr(), DateTimeUtility.DD_MM_YYYY));
				}
				
				TripExpense.setTripsheet(tsheet);
				TripExpense.setCompanyId(userDetails.getCompany_id());
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				TripExpense.setCreated(toDate);
				TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());
				tripSheetService.addTripSheetExpense(TripExpense);

				Object[] expenseIds = tripSheetService.getDriverAdvanceAndBataExpenseId(userDetails.getCompany_id());

				// Note : This Update Bata Details in Trip Sheet

				if (expenseIds != null) {
					if (expenseIds[0] != null && (Integer) expenseIds[0] == Integer.parseInt(expenseName[i] + "")) {

					}
					if (expenseIds[1] != null && (Integer) expenseIds[1] == Integer.parseInt(expenseName[i] + "")) {
						tripSheetService.Update_TripSheet_FIRST_Driver_BATA(Double.parseDouble("" + expenseAmount[i]),
								TripSheet.getTripSheetID(), userDetails.getCompany_id());
					}
					if (expenseIds[2] != null && (Integer) expenseIds[2] == Integer.parseInt(expenseName[i] + "")) {

					}
					if (expenseIds[3] != null && (Integer) expenseIds[3] == Integer.parseInt(expenseName[i] + "")) {
						tripSheetService.Update_TripSheet_SECOND_Driver_BATA(Double.parseDouble("" + expenseAmount[i]),
								TripSheet.getTripSheetID(), userDetails.getCompany_id());
					}
					if (expenseIds[4] != null && (Integer) expenseIds[4] == Integer.parseInt(expenseName[i] + "")) {

					}
					if (expenseIds[5] != null && (Integer) expenseIds[5] == Integer.parseInt(expenseName[i] + "")) {
						tripSheetService.Update_TripSheet_CLEANER_BATA(Double.parseDouble("" + expenseAmount[i]),
								TripSheet.getTripSheetID(), userDetails.getCompany_id());
					}
				}

				
				if(TripExpense.getExpenseAmount() > 0.0 ) {

					ValueObject		object	= new ValueObject();

					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_ID, TripSheet.getTripSheetID());
					object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_VID, sheet.getVid());
					object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, TripExpense.getExpenseId());
					object.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, TripExpense.getTripExpenseID());


					profitAndLossExpenseTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);

					vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);

					expenseTxnCheckerHashMap.put(profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossExpenseTxnChecker);
					tripSheetExpenseHM.put(TripExpense.getTripExpenseID(), TripExpense);
				}
			}

			// Note : This Update TripSheet Total Expense Updating The Value Of
			// Total Expense amount to TripSheet
			tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(TripSheet.getTripSheetID(), userDetails.getCompany_id());

			ValueObject	valueObject	= new ValueObject();
			valueObject.put("tripSheet", sheet);
			valueObject.put("userDetails", userDetails);
			valueObject.put("tripSheetExpenseHM", tripSheetExpenseHM);
			valueObject.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);
			valueObject.put("isTripSheetClosed", true);

			vehicleProfitAndLossService.runThreadForTripSheetExpenses(valueObject);

		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			expenseName 	= null;
			expenseAmount	= null;
			expenseRefence	= null;
		}
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "");
	}

	// Note: This /addIncomeAccount Controller is TripSheet ID to After Closed
	// TripSheet Account Department Show Add Income Page
	@RequestMapping(value = "/addIncomeAccount", method = RequestMethod.GET)
	public ModelAndView addIncomeAccount(@ModelAttribute("command") TripSheetDto TripSheet, BindingResult result) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		HashMap<String, Object> 	configuration				= null;
		boolean						allowShortCutInTripSheet	= false;
		boolean						showNetIncomeAmount			= false;
		
		try {
			
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			allowShortCutInTripSheet = (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.ALLOW_SHORTCUT_IN_TRIPSHEET, false);
			
			java.util.Date date = new java.util.Date();
		    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
		    String currentDate= formatter.format(date);  

		    showNetIncomeAmount 		= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.SHOW_TRIPSHEET_NET_INCOME_AMOUNT, false);

		    model.put("showNetIncomeAmount", showNetIncomeAmount);
		    model.put("currentDate", currentDate);
			
			model.put("AllowShortCutInTripSheet", allowShortCutInTripSheet);
			model.put("configuration", configuration); 

			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails));
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);

			// get Trip sheet id to get all Income Details
			model.put("TripSheetIncome",
					tripSheetService.findAllTripSheetIncome(TripSheet.getTripSheetID(), userDetails.getCompany_id()));
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double Totalincome = trip.getTripTotalincome();
			if (trip.getTripTotalincome() == null) {
				Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("incomeTotal", df.format(Totalincome));

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("addIncomeAccount", model);
	}

	// Note: This /updateIncomeAccount Controller is TripSheet ID to After
	// Closed TripSheet Account Department update Income and Amount
	@RequestMapping(value = "/updateIncomeAccount", method = RequestMethod.POST)
	public ModelAndView updateIncomeAccountTripsheet(@ModelAttribute("command") TripSheetDto TripSheet,
			TripSheetIncome TripSheetIncome, 
			@RequestParam("incomeName") final String[] incomeName,
			@RequestParam("route") final Integer[] route,
			@RequestParam("gst") final Double[] gst,
			@RequestParam("commission") final Double[] commission,
			@RequestParam("incomeDate") final String[] tripsheetIncomeDate,
			@RequestParam("Amount") final String[] incomeAmount,
			@RequestParam("netAmount") final Double[] netIncomeAmount,
			@RequestParam("remark") final String[] remark,
			@RequestParam("incomeRefence") final String[] incomeRefence, final HttpServletRequest request) throws Exception {
		
		HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>		incomeTxnCheckerHashMap			= null;
		HashMap<Long, TripSheetIncome> 							tripSheetIncomeDtoHM			= null;
		boolean													showNetIncomeAmount				= false;

		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			showNetIncomeAmount = (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.SHOW_TRIPSHEET_NET_INCOME_AMOUNT, false);

			
			tripSheetIncomeDtoHM	= new HashMap<>();
			incomeTxnCheckerHashMap	= new HashMap<>();
			String name = userDetails.getEmail();

			UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(name);

			Integer incomePlace = Orderingname.getBranch_id();

			TripSheet tsheet = new TripSheet();
			tsheet.setTripSheetID(TripSheet.getTripSheetID());

			TripSheet	sheet	= tripSheetService.getTripSheet(TripSheet.getTripSheetID());

			for (int i = TRIP_SHEET_DEFALAT_ZERO; i < incomeName.length; i++) {

				TripSheetIncome TripIncome = new TripSheetIncome();
				TripIncome.setIncomeId(Integer.parseInt("" + incomeName[i])); 
				if(showNetIncomeAmount) {
					TripIncome.setIncomeAmount(Double.parseDouble("" + netIncomeAmount[i]));
					TripIncome.setGst(gst[i]);
					TripIncome.setCommission(commission[i]);
					TripIncome.setRouteID(route[i]);
					TripIncome.setRemark(""+remark[i]);
					TripIncome.setTripsheetIncomeDate(DateTimeUtility.getTimeStamp(tripsheetIncomeDate[i]));
					
					
				}else {
					TripIncome.setIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
				}
				TripIncome.setIncomePlaceId(incomePlace);
				TripIncome.setIncomeRefence(incomeRefence[i]);
				TripIncome.setIncomeCollectedById(userDetails.getId());
				TripIncome.setCompanyId(userDetails.getCompany_id());
				TripIncome.setCreatedById(userDetails.getId());
				TripIncome.setTripsheet(tsheet);
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
				TripIncome.setCreated(toDate);
				if(showNetIncomeAmount) {
					TripIncome.setNetIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
				}else {
					TripIncome.setNetIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
				}
				

				tripSheetService.addTripSheetIncome(TripIncome);

				if(TripIncome.getIncomeAmount() > 0.0) {

					ValueObject		incomeObject	= new ValueObject();
					

					incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, TripSheet.getTripSheetID());
					incomeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
					incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
					incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, sheet.getVid());
					incomeObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, TripIncome.getIncomeId());
					incomeObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, TripIncome.getTripincomeID());

					VehicleProfitAndLossIncomeTxnChecker		profitAndLossIncomeTxnChecker	= txnCheckerBL.getVehicleProfitAndLossIncomeTxnChecker(incomeObject);
					//vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);
					vehicleProfitAndLossIncomeTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);

					incomeTxnCheckerHashMap.put(profitAndLossIncomeTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossIncomeTxnChecker);
					tripSheetIncomeDtoHM.put(TripIncome.getTripincomeID(), TripIncome);
				}
			}

			// Note : This Update TripSheet Total Income Updating The Value Of
			// Total Income amount to TripSheet
			tripSheetService.UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(TripSheet.getTripSheetID(), userDetails.getCompany_id());
			
			if(tripSheetIncomeDtoHM != null && tripSheetIncomeDtoHM.size() > 0) {
				ValueObject	valueObject	= new ValueObject();
				valueObject.put("tripSheet", sheet);
				valueObject.put("userDetails", userDetails);
				valueObject.put("tripSheetIncomeHM", tripSheetIncomeDtoHM);
				valueObject.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap);
				valueObject.put("isTripSheetClosed", true);

				vehicleProfitAndLossService.runThreadForTripSheetIncome(valueObject);
			}

		} catch (Exception e) {
			System.err.println("Exception "+e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "");
	}

	/***************************************************************************************
	 ******************* Close Account Trip Sheet*******************************************
	 ******************************************************************************************/
	// Note: This /addcloseAccountTripSheet Controller is TripSheet ID to After
	// Closed TripSheet Account Department to close Account Page
	@RequestMapping(value = "/addcloseAccountTripSheet", method = RequestMethod.GET)
	public ModelAndView addAccountClose(@ModelAttribute("command") TripSheetDto TripSheet, BindingResult result) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		HashMap<String, Object>    	config						= null;
		HashMap<String, Object>    	configuration					= null;
		boolean						showCombineTripDetails		= false;
		boolean						showTripsheetDueAmount		= false;
		List<TripSheetExpenseDto> 	finalExpenseList			= null;
		double						totalDueAmount				= 0.0;
		boolean						allowAccountCloseWithoutIncome	= true;
		
		boolean fastagFound = false;
		boolean gpsUsageFound = false;
		boolean permissionToClose = false;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TOLL_API_CONFIG);
			config			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			showCombineTripDetails	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.SHOW_COMBINE_TRIP_DETAILS, false);
			showTripsheetDueAmount	= (boolean) config.getOrDefault("showTripsheetDueAmount", false);
			allowAccountCloseWithoutIncome	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.ALLOW_ACC_CLOSE_WITHOUT_INCOME, true);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
		
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails));
			if(!allowAccountCloseWithoutIncome && trip.getTripTotalincome() == null ) {
				return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "");
			}
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);
			model.put("configuration", config);
			// get Trip sheet id to get all Advance Details
			model.put("TripSheetAdvance",
					tripSheetService.findAllTripSheetAdvance(TripSheet.getTripSheetID(), userDetails.getCompany_id()));

			// get Trip sheet id to get all Expense Details
			if(showCombineTripDetails) {
				List<TripSheetExpenseDto> expenseCombine = tripSheetService.findAllTripSheetExpense(trip.getTripSheetID(), userDetails.getCompany_id());
				if(expenseCombine != null && !expenseCombine.isEmpty()) {
					HashMap<Integer, TripSheetExpenseDto> expenseMap = tripSheetService.findAllTripSheetExpenseCombineMap(expenseCombine);
					if(expenseMap != null) {
						finalExpenseList = new ArrayList<TripSheetExpenseDto>();
						finalExpenseList.addAll(expenseMap.values());
					}
					model.put("ExpenseCombineList",finalExpenseList);
				}
			} else {
			
			
				List<TripSheetExpenseDto> tripSheetExpense=tripSheetService.findAllTripSheetExpense(TripSheet.getTripSheetID(), userDetails.getCompany_id());
				model.put("TripSheetExpense",tripSheetExpense);
				if((boolean) config.getOrDefault("allowCloseWithNoGPSandFastag", false)) {
					ValueObject objectToll = tollExpensesDetailsService.getTollExpensesDetailsList(TripSheet.getTripSheetID(), userDetails.getCompany_id());
					if(objectToll.getDouble("totalAmount",0.0) > 0.0) {
						fastagFound =true;
					}
					if(trip.getTripGpsUsageKM() != null && trip.getTripGpsUsageKM() > 0) {
						gpsUsageFound =true;
					}
					if(permission.contains(new SimpleGrantedAuthority("CHECK_GPS_FASTAG_BEFORE_TRIPCLOSE"))) {
						permissionToClose=true;
					}
				}
			}
			model.put("permissionToClose",permissionToClose);
			model.put("fastagFound",fastagFound);
			model.put("gpsUsageFound",gpsUsageFound);

			// get Trip sheet id to get all Income Details
			model.put("TripSheetIncome",
					tripSheetService.findAllTripSheetIncome(TripSheet.getTripSheetID(), userDetails.getCompany_id()));
			
			if(showTripsheetDueAmount) {
				model.put("TripsheetDueAmount",
						tripSheetService.getDueAmountListByTripsheetId(TripSheet.getTripSheetID(), userDetails.getCompany_id()));
				model.put("TotalDueAmount",
						tripSheetService.getTotalDueAmountByTripsheetId(TripSheet.getTripSheetID(), userDetails.getCompany_id()));
				totalDueAmount = (double) model.get("TotalDueAmount");
			}

			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalAdvance = trip.getTripTotalAdvance();
			if (trip.getTripTotalAdvance() == null) {
				TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("advanceTotal", df.format(TotalAdvance));

			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("expenseTotal", df.format(TotalExpense));

			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double Totalincome = trip.getTripTotalincome();
			if (trip.getTripTotalincome() == null) {
				Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("incomeTotal", df.format(Totalincome));
			model.put("driverBalance", (Totalincome + TotalAdvance) - TotalExpense - totalDueAmount);

			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());

			model.put("paidBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
			model.put("paidById", userDetails.getId());
			model.put("companyId", userDetails.getCompany_id());
			model.put("place", Orderingname.getBranch_name());
			model.put("placeId", Orderingname.getBranch_id());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("addCloseAccountTripSheet", model);
	}

	// Note: This /uploadCloseAccountTrip Controller is TripSheet ID to After
	// Closed TripSheet Account Department to Save A/c Account Details
	@RequestMapping(value = "/uploadCloseAccountTrip", method = RequestMethod.POST)
	public ModelAndView uploadCloseAccountTrip(@ModelAttribute("command") TripSheet TripSheet,
			final HttpServletRequest request) throws Exception {

		try {
			/** Who Close Trip Account TripSheet User Email ID */
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			tripSheetService.updateCloseAccountTrip(userDetails.getId(), TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED,
					TripSheet.getCloseACCTripAmount(), TripSheet.getCloseACCTripReference(), toDate,
					userDetails.getId(), toDate, TripSheet.getTripSheetID(), userDetails.getCompany_id(),
					Orderingname.getBranch_id());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "");
	}

	// Note: This /showTripSheet Controller is TripSheet ID to After A/C
	@SuppressWarnings("unchecked")
	// Closed Show Account Department ID
	@RequestMapping(value = "/showAccountTripSheet", method = RequestMethod.GET)
	public ModelAndView showAccountTripSheet(@RequestParam("tripSheetID") final Long TripSheetID,
			HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean					hideClosedTripSheet	      	= false;
		HashMap<String, Object> configuration	          	= null;
		ModelAndView 			map 						= new ModelAndView();
		CustomUserDetails 		userDetails 				= null;
		TripSheetDto 			trip						= null;
		double 					fuelAmount					= 0.0;
		double 					ureaAmount					= 0.0;
		double 					tollAmount					= 0.0;
		List<FuelDto> 			ShowAmount					= null;
		Double 					Totalincome					= 0.0;
		Double 					TotalExpense				= 0.0;
		HashMap<String, Object> config						= null;
		boolean					showCombineTripDetails		= false;
		boolean					showTripsheetDueAmount		= false;
		List<TripSheetExpenseDto> 	finalExpenseList		= null;
		double 					kmpl 						= 0.0;
		double					totalDueAmount				= 0.0;
		List<TripSheetExpenseDto>       	expenseDto  = null;
		try {
			DecimalFormat numberFormat = new DecimalFormat("#.00"); 
			numberFormat			= new DecimalFormat("#.00"); 
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			config					= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TOLL_API_CONFIG);
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			hideClosedTripSheet		= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.HIDE_CLOSED_TRIPSHEET, false);
			showCombineTripDetails	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.SHOW_COMBINE_TRIP_DETAILS, false);
			showTripsheetDueAmount	= (boolean) configuration.getOrDefault("showTripsheetDueAmount", false);
			
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
			map.addObject("permissions", permission);
			trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheetID, userDetails));
			// get the Trip sheet ID to Details
			if(trip == null) {
				model.put("NotFound", true);
				return new ModelAndView("redirect:/newTripSheetEntries.in", model);	
			}
			if(hideClosedTripSheet && !permission.contains(new SimpleGrantedAuthority("SHOW_TRIPSHEET_CLOSE_STATUS"))){
				if(trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED || trip.getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED ) {
					model.put("NotFound", true);
					return new ModelAndView("redirect:/newTripSheetEntries.in", model);	
				}
			}
			
			boolean allowTollApiIntegration = false;
			if((boolean)config.get(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION) || (boolean)config.get(TollApiConfiguration.ALLOW_KVB_BANK_TOLL_API)) {
				allowTollApiIntegration = true;
			}
			model.put(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION, allowTollApiIntegration);
			model.put("allowTicketIncomeApiIntegeration",(boolean) configuration.get("allowTicketIncomeApiIntegeration"));
			model.put("allowITSGatewayBusIncome",(boolean) configuration.get("allowITSGatewayBusIncome"));
			model.put("companyId",userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			
			
			model.put("TripSheet", trip);
			model.put("configuration", configuration);
			
			
			model.put("tripConfig", configuration);
			// get Trip sheet id to get all Advance Details
			model.put("TripSheetAdvance",
					tripSheetService.findAllTripSheetAdvance(TripSheetID, userDetails.getCompany_id()));

			// get Trip sheet id to get all Expense Details
			if(showCombineTripDetails) {
				
				List<TripSheetExpenseDto> expenseCombine = tripSheetService.findAllTripSheetExpense(TripSheetID, userDetails.getCompany_id());
				if(expenseCombine != null && !expenseCombine.isEmpty()) {
					
					HashMap<Integer, TripSheetExpenseDto> expenseMap = tripSheetService.findAllTripSheetExpenseCombineMap(expenseCombine);
					if(expenseMap != null) {
						finalExpenseList = new ArrayList<TripSheetExpenseDto>();
						finalExpenseList.addAll(expenseMap.values());
					}
					model.put("ExpenseCombineList",finalExpenseList);
				}
			} else {
				expenseDto  = tripSheetService.findAllTripSheetExpense(TripSheetID, userDetails.getCompany_id());
				model.put("TripSheetExpense", expenseDto);

			}
			List<TripSheetIncomeDto>  incomeDto	=  tripSheetService.findAllTripSheetIncome(TripSheetID, userDetails.getCompany_id());
			// get Trip sheet id to get all Income Details
			model.put("TripSheetIncome",incomeDto);
			BusBookingDetailsDto	busDetailsDto	=  busBookingDetailsService.getBusBookingDetailsByTripSheetId(trip.getTripSheetID());
			if(busDetailsDto != null && trip.getTripUsageKM() != null & busDetailsDto.getRate() != null) {
				model.put("busBookingAmount", busDetailsDto.getRate() * trip.getTripUsageKM());
			}
			model.put("busBooking", busDetailsDto);
			
			ValueObject	refreshmentObj	= refreshmentEntryService.getRefreshmentEntryListByTripSheetId(trip.getTripSheetID());
			if(refreshmentObj != null) {
				model.put("refreshment",(List<RefreshmentEntryDto>) refreshmentObj.get("refreshment"));
				model.put("grandTotal", refreshmentObj.getDouble("grandTotal"));
				model.put("totalQty", refreshmentObj.getDouble("totalQty"));
				model.put("totalRQty", refreshmentObj.getDouble("totalRQty"));
				model.put("totalConsumption", refreshmentObj.getDouble("totalConsumption"));
				TotalExpense += refreshmentObj.getDouble("grandTotal",0);
			}
			
			if(showTripsheetDueAmount) {
				model.put("TripsheetDueAmount",
						tripSheetService.getDueAmountListByTripsheetId(TripSheetID, userDetails.getCompany_id()));
				model.put("TotalDueAmount",
						tripSheetService.getTotalDueAmountByTripsheetId(TripSheetID, userDetails.getCompany_id()));
				totalDueAmount = (double) model.get("TotalDueAmount");
			}

			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalAdvance = trip.getTripTotalAdvance();
			if (trip.getTripTotalAdvance() == null) {
				TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("advanceTotal", df.format(TotalAdvance));
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("expenseTotal", df.format(TotalExpense));

			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Totalincome = trip.getTripTotalincome();
			if (trip.getTripTotalincome() == null) {
				Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			// get Trip sheet id to get all TollExpense Details
			ValueObject object = tollExpensesDetailsService.getTollExpensesDetailsList(TripSheetID, userDetails.getCompany_id());
			model.put("TripSheetTollExpense",(object.get("ExpenseName")));
			model.put("TripSheetTollExpenseName",config.get(TollApiConfiguration.TOLL_EXPENSE_NAME));
			model.put("TripSheetTollExpenseDate",object.get("Date"));
			model.put("TripSheetTollExpenseTotalAmount",object.get("totalAmount"));
			
			model.put("incomeTotal", df.format(Totalincome));
			
			model.put("driverBalance", (Totalincome + TotalAdvance) - TotalExpense - totalDueAmount);
			// show Halt data
			model.put("TripSheetHalt", tripSheetBL.Driver_Halt_Display(driverHaltService.Find_list_TripSheet_DriverHalt(TripSheetID, userDetails.getCompany_id())));
			VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Fuel_Entries_Show(vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(trip.getVid(), userDetails.getCompany_id()));
			model.put("vehicle", vehicle);
			// show fuel entries in this tripsheet
			ShowAmount = fuelService.Get_TripSheet_IN_FuelTable_List(TripSheetID, userDetails.getCompany_id(), userDetails.getId());
			
			fuelAmount= fuelBL.prepareTotalAmount_Tripsheet_Created_fuel_details(ShowAmount);
			tollAmount=	object.getDouble("totalAmount", 0);
			model.put("fuel", fuelBL.prepareListofFuel(ShowAmount));
			model.put("fTDiesel", fuelBL.prepareTotal_Tripsheet_fuel_details(ShowAmount));
			model.put("fTUsage", fuelBL.prepareTotalUsage_Tripsheet_fuel_details(ShowAmount));
			model.put("fTAmount",numberFormat.format(fuelBL.prepareTotalAmount_Tripsheet_fuel_details(ShowAmount)));
			
			if(ShowAmount != null && !ShowAmount.isEmpty()) {
				
				List<FuelDto> fuelList = fuelBL.prepareListofFuel(ShowAmount);
				int    size 	 = 0;
				double totalKmpl = 0;
				for(FuelDto dto : fuelList) {
					if(dto.getFuel_kml() != null && dto.getFuel_tank() == 0) {
						kmpl += dto.getFuel_kml();
						size ++;
					}
				}
				if(size  > 0) {
					totalKmpl = kmpl/size;
					model.put("totalKmpl", totalKmpl);
				}
				
			}
			
			if((boolean) configuration.get("driverBalanceWithNarration")) {
				ValueObject	valueObject = tripSheetBL.getDriverBalanceForTrip(trip, configuration, incomeDto, expenseDto);
				if(valueObject != null) {
					model.put("driverBalanceKey",valueObject.get("driverBalanceKey"));
					model.put("balanceAmount",valueObject.get("balanceAmount"));
				}
				
			}
			
			
			List<UreaEntriesDto> ureaDeatils = UreaEntriesService.getUreaEntriesDetailsByTripSheetId(TripSheetID, userDetails.getCompany_id());
			if(ureaDeatils != null) {
			model.put("urea", ureaDeatils);
			model.put("totalUrea", ureaEntriesBL.prepareTotal_Tripsheet_Urea_details(ureaDeatils));
			ureaAmount = ureaEntriesBL.prepareTotal_Tripsheet_Urea_Amount(ureaDeatils);
			model.put("totalUreaAmnt",ureaAmount);
			}
			
			model.put("tripTotalincome", numberFormat.format(Totalincome-(fuelAmount + ureaAmount + tollAmount + TotalExpense)));
			
			fuelAmount= fuelBL.prepareTotalAmount_Tripsheet_Created_fuel_details(ShowAmount);
			tollAmount=	object.getDouble("totalAmount", 0);
			if (trip.getTripCommentTotal() != null && trip.getTripCommentTotal() != TRIP_SHEET_DEFALAT_ZERO) {
				model.put("TripComment", tripSheetBL.TripSheetComment_Display(
						tripCommentService.list_TripSheet_ID_TO_TripSheetComment(TripSheetID, userDetails.getCompany_id())));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		if(trip.getTripStutesId() != TripSheetStatus.TRIP_STATUS_CLOSED || trip.getTripStutesId() != TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
			return new ModelAndView("showAccountTripSheet", model);
		}
		else
		{
			return new ModelAndView("showTripSheet", model);
		}
		/*return new ModelAndView("showTripSheet", model);*/
	}

	@RequestMapping(value = "/addCloseTripsheet", method = RequestMethod.GET)
	public ModelAndView addCloseTripsheet(@RequestParam("tripSheetID") final Long TripSheetID,
			final HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean	showTimeDuringCloseTripsheet	= false;

		HashMap<String, Object> 	configuration	= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			showTimeDuringCloseTripsheet = (boolean) configuration.get(TripSheetConfigurationConstant.SHOW_TIME_DURING_CLOSE_TRIPSHEET);
			model.put("ShowTime", showTimeDuringCloseTripsheet);
			
			HashMap<String, Object> 	vehicleonfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			HashMap<String, Object> 	gpsConfig		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);

			TripSheetDto trip = tripSheetService.getTripSheetDetails(TripSheetID,userDetails);
			TripSheetDto	finalTripDto  	= tripSheetBL.GetTripSheetDetails(trip);
			
			if((boolean) configuration.get("IncCashExpenseInDriBal")) {
				List<TripSheetExpense> expenses = TripSheetExpenseRepository.findAllTripSheetExpense(TripSheetID, userDetails.getCompany_id());
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
				finalTripDto.setTripTotalexpense(finalTripDto.getTripTotalexpense()- expenseAmount);
			}
			
			if((boolean)configuration.getOrDefault("showLastTripMileageOnCloseTrip",false)) {
				long lastTripId =tripSheetService.getLastTripSheetFromGivenDate(trip.getDispatchedByTimeOn(), userDetails.getCompany_id(),trip.getTripFristDriverID());// get the Trip sheet ID to Details
				model.put("lastTripId",lastTripId);
				if(lastTripId > 0) {
					FuelDto lastTripFuel= fuelService.getTripsheetFuelMileage(lastTripId,userDetails.getCompany_id());
					model.put("lastTripFuel",lastTripFuel);
				}
			}
			model.put("TripSheet",finalTripDto);
			model.put("vehicleonfig", vehicleonfig);
			model.put("companyId", userDetails.getCompany_id());
			ObjectMapper objectMapper = new ObjectMapper();
			model.put("allowGPSIntegration", (boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION));
			model.put("gpsFlavor", gpsConfig.getOrDefault("gpsFlavor", 1));
			model.put("gpsConfig", objectMapper.writeValueAsString(gpsConfig));
			model.put("allowedKMForExtendedTrip", (int)configuration.getOrDefault("allowedKMForExtendedTrip", 2000));
			
			// get Trip sheet id to get all Extra Details
			model.put("TripSheetExtraOptions",
					tripSheetService.findAllTripSheetExtraOptions(TripSheetID, userDetails.getCompany_id()));

			model.put("configuration", configuration);
			model.put("driverBalanceWithNarration",(boolean) configuration.get("driverBalanceWithNarration"));
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			
			
			
			ValueObject	gpsObject	= null;
			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(trip.getVid());
		
			try {
				model.put("fuel", fuelRepository.getNewLogicForTripsheeet(TripSheetID, userDetails.getCompany_id()) );
				model.put("fuelMileage", CheckVehicleStatus.getVehicle_ExpectedMileage() );
				model.put("fuelCostPerLiter", fuelRepository.getFuelCostPerLiter(TripSheetID,userDetails.getCompany_id()));
				 
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			if((boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
				ValueObject	gps	= new ValueObject();
				gps.put("companyId", userDetails.getCompany_id());
				gps.put("vehicleId", trip.getVid());
				gps.put("fromDate", DateTimeUtility.getTimeStampFromDate(trip.getDispatchedByTimeOn()));
				gps.put("toDate", DateTimeUtility.getCurrentTimeStamp());
				gps.put("fromTripSheetClose", true);
				gps.put("openKM", trip.getTripOpeningKM());
				gps.put("openGPSKM", finalTripDto.getTripGpsOpeningKM());
				try {
					gpsObject	=	vehicleGPSDetailsService.getVehicleGPSData(gps);
				} catch (Exception e) {
					
				}
				
				if(gpsObject != null) {
					model.put("gpsObject", objectMapper.writeValueAsString(gpsObject.getHtData()));
					if(gpsObject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME)) {
						model.put("GPSKM", gpsObject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME));
					}else {
						model.put("GPSKM", CheckVehicleStatus.getVehicle_Odometer());
					}
				}
			}

			if (CheckVehicleStatus != null) {
				model.put("ExpectedOdameter", CheckVehicleStatus.getVehicle_ExpectedOdameter());
				model.put("ExpectedOdameterKm", CheckVehicleStatus.getVehicleExpectedKm());
				
				if((boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION) && (int)gpsConfig.get(VehicleGPSConfiguratinConstant.GPS_FLAVOR) == 2) {
					if(gpsObject != null && gpsObject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME) && gpsObject.getBoolean(GPSConstant.VEHICLE_GPS_WORKING, false)) {
						Double gpsKM = gpsObject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME);
						model.put("TripSheetKm", gpsKM.intValue());
					}else {
						if((boolean)configuration.get(TripSheetConfigurationConstant.SHOW_CLOSINGKM_ON_ROUTE) && finalTripDto.getRouteApproximateKM() != null) {
							model.put("TripSheetKm", finalTripDto.getTripOpeningKM() + finalTripDto.getRouteApproximateKM());
						}else {
							model.put("TripSheetKm", finalTripDto.getTripOpeningKM());
						}
					}
					
				}else {
					if((boolean)configuration.get(TripSheetConfigurationConstant.SHOW_CLOSINGKM_ON_ROUTE) && finalTripDto.getRouteApproximateKM() != null) {
						model.put("TripSheetKm", finalTripDto.getTripOpeningKM() + finalTripDto.getRouteApproximateKM());
					}else {
						model.put("TripSheetKm", finalTripDto.getTripOpeningKM());
					}
				}
					
				if((boolean)configuration.get(TripSheetConfigurationConstant.SHOW_CLOSINGKM_ON_ROUTE) && finalTripDto.getRouteApproximateKM() != null) {
					int maxAllowedKM = (((int) configuration.getOrDefault("allowedPerDiffInOdometer", 10) * finalTripDto.getRouteApproximateKM())/100);
					model.put("maxAllowedKM", maxAllowedKM + finalTripDto.getTripOpeningKM() + finalTripDto.getRouteApproximateKM());
					model.put("minAllowedKM", finalTripDto.getTripOpeningKM() + finalTripDto.getRouteApproximateKM() - maxAllowedKM);
				}else {
					model.put("maxAllowedKM", CheckVehicleStatus.getVehicleExpectedKm() + finalTripDto.getTripOpeningKM());
					model.put("minAllowedKM", finalTripDto.getTripOpeningKM() + 1);
				}
			}
			

			if (trip.getTripOpenDate() != null && !trip.getTripOpenDate().equalsIgnoreCase("")) {
				
				model.put("StartDate", dateFormat.parse(finalTripDto.getTripOpenDate()));
			}
			if (trip.getClosetripDate() != null && !trip.getClosetripDate().equalsIgnoreCase("")) {
				model.put("EndDate", dateFormat.parse(finalTripDto.getClosetripDate()));
			}
			
			

			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalAdvance = trip.getTripTotalAdvance();
			if (trip.getTripTotalAdvance() == null) {
				TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("advanceTotal", df.format(TotalAdvance));

			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("expenseTotal", df.format(TotalExpense));

			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			Double Totalincome = trip.getTripTotalincome();
			if (trip.getTripTotalincome() == null) {
				Totalincome = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("incomeTotal", df.format(Totalincome));

			Double RouteAttendancePoint = TRIP_SHEET_DEFALAT_AMOUNT, DriverTotalPoint = TRIP_SHEET_DEFALAT_AMOUNT,
					DriverSECTotalPoint = TRIP_SHEET_DEFALAT_AMOUNT, ClennerTotalPoint = TRIP_SHEET_DEFALAT_AMOUNT,
					FDriverHaltPoint = TRIP_SHEET_DEFALAT_AMOUNT, SDriverHaltPoint = TRIP_SHEET_DEFALAT_AMOUNT,
					ClennerHaltPoint = TRIP_SHEET_DEFALAT_AMOUNT;
			// Find TripSheet Id to get Route Attendance value point and display
			if (trip.getRouteID() != TRIP_SHEET_DEFALAT_ZERO) {
				TripRoute TripRoute = TripRouteService.getTripRoute(trip.getRouteID());
				if (TripRoute.getRoutrAttendance() != null) {
					RouteAttendancePoint += TripRoute.getRoutrAttendance();
				}
			}
			if((boolean) configuration.get("driverBalanceWithNarration")) {
				List<TripSheetIncomeDto>  incomeDto		=  tripSheetService.findAllTripSheetIncome(TripSheetID, userDetails.getCompany_id());
				List<TripSheetExpenseDto> expenseDto  	= tripSheetService.findAllTripSheetExpense(TripSheetID, userDetails.getCompany_id());
				ValueObject	valueObject = tripSheetBL.getDriverBalanceForTrip(trip, configuration, incomeDto, expenseDto);
				if(valueObject != null) {
					model.put("driverBalanceKey",valueObject.get("driverBalanceKey"));
					model.put("balanceAmount",valueObject.get("balanceAmount"));
					model.put("lossInTrip",valueObject.getBoolean("lossInTrip", false));
					model.put("totalIncome",valueObject.getDouble("totalIncome"));
					model.put("totalExpense",valueObject.getDouble("totalExpense"));
					model.put("totalAdvance",valueObject.getDouble("totalAdvance"));
				}
				
			}
			List<DriverHaltDto> DriverHalt = driverHaltService.Find_list_TripSheet_DriverHalt(TripSheetID, userDetails.getCompany_id());
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
				Dto.setDRIVER_NAME(trip.getTripFristDriverName()+" "+trip.getTripFristDriverLastName() +" - "+ trip.getTripFristDriverFatherName());
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
				Dto.setDRIVER_NAME(trip.getTripSecDriverName()+" "+trip.getTripSecDriverLastName()+" - "+trip.getTripSecDriverFatherName());
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

			model.put("DisplayPoint", DisplayPoint);
			model.put("DefaultDate",DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getCurrentTimeStamp()));
			model.put("DefaultTime",DateTimeUtility.getTimeFromTimeStamp(DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.HHH_MM));
			double driverWalleteBalance =0;
			if((boolean)configuration.getOrDefault("saveDriverLedgerDetails", false) && trip.getTripStutesId() < 3) {
				DriverTripSheetBalance driverTripSheetBalance= 	driverTripSheetBalanceRepo.getDriverTripSheetBalanceByDriverId(trip.getTripFristDriverID(), userDetails.getCompany_id());
				if(driverTripSheetBalance != null ) {
					driverWalleteBalance = driverTripSheetBalance.getBalanceAmount();
				}
			}
			model.put("driverWalleteBalance",driverWalleteBalance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("closeTripSheet", model);
	}

	// Note: This /uploadClose Controller Is After Dispatch To Close Driver
	// Account Details and Route Value Details and Attendance Save To Close
	// Driver Account Close Save Details
	@RequestMapping(value = "/uploadClose", method = RequestMethod.POST)
	public ModelAndView uploadClose(@RequestParam("ROUTE_DRIVERID") final String[] ROUTE_DRIVERID,
			@RequestParam("ROUTE_POINT") final String[] ROUTE_POINT,
			@ModelAttribute("command") TripSheetDto TripSheet,
			TripSheetAdvance TripSheetAdvance, final HttpServletRequest request, RedirectAttributes redir) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration				= null;
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Integer ClosingKM = TRIP_SHEET_DEFALAT_ZERO;
		Integer UsageKM = TRIP_SHEET_DEFALAT_ZERO;
		Double	gpsUssageKM = 0.0;
		HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>		incomeTxnCheckerHashMap			= null;
		HashMap<Long, VehicleProfitAndLossTxnChecker>			expenseTxnCheckerHashMap		= null;
		HashMap<Long, TripSheetExpenseDto> 						tripSheetExpenseHM				= null;
		HashMap<Long, TripSheetIncomeDto> 						tripSheetIncomeDtoHM			= null;
		VehicleProfitAndLossTxnChecker							driverSalaryTxn					= null;
		VehicleProfitAndLossDto									driverSalaryExpense				= null;
		Timestamp 												CloseByTime 					= null;
		ValueObject												valueTollObject					= null; 
		VehicleExtraDetails 									busDeatils						= null; 
		try {

			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			HashMap<String, Object> 	gpsConfig		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			HashMap<String, Object> 	tollConfig		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TOLL_API_CONFIG);
		
			
			java.util.Date date = dateFormat.parse(TripSheet.getClosetripDate());
			java.sql.Date Close_date = new java.sql.Date(date.getTime());
			short ClosingKMStatus = TripSheet.getTripClosingKMStatusId();
			
			incomeTxnCheckerHashMap		= new HashMap<>();
			expenseTxnCheckerHashMap	= new HashMap<>();
			tripSheetExpenseHM			= new HashMap<>();
			tripSheetIncomeDtoHM		= new HashMap<>();
		
			TripSheetDto	sheet	=	tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails);
			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(TripSheet.getVid());
			
			if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_ITS_GATEWAY_BUS_INCOME)) {
				 busDeatils = VehicleExtraDetailsRepository.getBusId(TripSheet.getVid(), userDetails.getCompany_id());
				 
				 if(busDeatils == null) {
						System.err.println("Bus Id Not Configured.");
				 }
			}
			
			if((boolean) configuration.get("validateClosingOdometerOnRoute")) {
				if((boolean)configuration.get(TripSheetConfigurationConstant.SHOW_CLOSINGKM_ON_ROUTE) && sheet.getRouteApproximateKM() != null) {
					int maxAllowedKM = (((int) configuration.getOrDefault("allowedPerDiffInOdometer", 10) * sheet.getRouteApproximateKM())/100);
					maxAllowedKM	= sheet.getRouteApproximateKM() + maxAllowedKM;
					CheckVehicleStatus.setVehicle_ExpectedOdameter(maxAllowedKM + sheet.getTripOpeningKM());
				}
			}
			
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp LASTUPDATED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			
				String start_time = "00:00";
				if(TripSheet.getTripEndDateTimeStr() != null && TripSheet.getTripEndDateTimeStr() != "") {
					start_time	= TripSheet.getTripEndDateTimeStr();
					java.util.Date closedate = DateTimeUtility.getDateTimeFromDateTimeString(dateFormat.format(date), start_time);
					CloseByTime = new java.sql.Timestamp(closedate.getTime());
				}else {
					CloseByTime = DateTimeUtility.getCurrentTimeStamp();
				}
				
				if((boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
					if(sheet.getTripGpsOpeningKM() != null && sheet.getTripGpsOpeningKM() > 0 && TripSheet.getTripGpsClosingKM() != null && TripSheet.getTripGpsClosingKM() > 0) {
						gpsUssageKM	= TripSheet.getTripGpsClosingKM() - sheet.getTripGpsOpeningKM();
					}else {
						try {
							gpsUssageKM = vehicleGPSDetailsService.getGPSRunKMBetweenTwoDates(sheet.getVid(), sheet.getDispatchedByTimeOn()+"", CloseByTime+"", userDetails.getCompany_id());
						} catch (Exception e) {
							System.err.println("Exception while getting gps run km "+e);
						}
						
					}
					if(TripSheet.getTripClosingKM() != null && TripSheet.getTripClosingKM() > 0) {
						ClosingKM = TripSheet.getTripClosingKM();
						UsageKM = ClosingKM - TripSheet.getTripOpeningKM();
					}else {
						UsageKM = gpsUssageKM.intValue();
					}
				}else {
					ClosingKM = TripSheet.getTripClosingKM();
					UsageKM = ClosingKM - TripSheet.getTripOpeningKM();
				}
				
		/*	if((boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
				if(sheet.getTripGpsOpeningKM() != null && TripSheet.getTripGpsClosingKM() != null) {
					
					if(sheet.getTripGpsOpeningKM() > TripSheet.getTripGpsClosingKM()) {
						ValueObject		valueObject	= new ValueObject();
						valueObject.put("tripOpenGPSKM", sheet.getTripGpsOpeningKM());
						valueObject.put("tripClosingGPSKM", TripSheet.getTripGpsClosingKM());
						valueObject.put("vehicleGPSId", sheet.getVehicleGPSId());
						valueObject.put("fromDate", sheet.getDispatchedByTimeOn()+"");
						valueObject.put("toDate", LASTUPDATED_DATE+"");
						gpsUssageKM	= tripSheetService.getGPSUsageKM(gpsConfig, valueObject);
					}else {
						gpsUssageKM	= TripSheet.getTripGpsClosingKM() - sheet.getTripGpsOpeningKM();
					}
					
				if((boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
					
					if(sheet.getTripGpsOpeningKM() != null && sheet.getTripGpsOpeningKM() > 0 && TripSheet.getTripGpsClosingKM() != null && TripSheet.getTripGpsClosingKM() > 0) {
						if(sheet.getTripGpsOpeningKM() > TripSheet.getTripGpsClosingKM()) {
							ValueObject		valueObject	= new ValueObject();
							valueObject.put("tripOpenGPSKM", sheet.getTripGpsOpeningKM());
							valueObject.put("tripClosingGPSKM", TripSheet.getTripGpsClosingKM());
							valueObject.put("vehicleGPSId", sheet.getVehicleGPSId());
							valueObject.put("fromDate", sheet.getDispatchedByTimeOn()+"");
							valueObject.put("toDate", LASTUPDATED_DATE+"");
							if(CheckVehicleStatus.getGpsVendorId() != null && CheckVehicleStatus.getGpsVendorId() == VehicleGPSVendorConstant.GPS_VENDOR_OMNI_TALK) {
								gpsUssageKM	= tripSheetService.getGPSUsageKM(gpsConfig, valueObject);
							}else {
								gpsUssageKM	= TripSheet.getTripGpsClosingKM() - sheet.getTripGpsOpeningKM();
							}
						}else {
							gpsUssageKM	= TripSheet.getTripGpsClosingKM() - sheet.getTripGpsOpeningKM();
						}
					}else {

						if (ClosingKMStatus == TripSheetStatus.TRIP_CLOSING_KM_STATUS_NOTWORKING) {
							ClosingKM = TripSheet.getTripClosingKM();
						} else {
							ClosingKM = TripSheet.getTripClosingKM();
							UsageKM = ClosingKM - TripSheet.getTripOpeningKM();
						}
					}
				}
				
			 }
		} 
			
		if(!(boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION) || (int)gpsConfig.get(VehicleGPSConfiguratinConstant.GPS_FLAVOR) == GPSConstant.VEHICLE_GPS_FLAVOR_BOTH) {
			if (ClosingKMStatus == TripSheetStatus.TRIP_CLOSING_KM_STATUS_NOTWORKING) {
				ClosingKM = TripSheet.getTripClosingKM();
			} else {
				ClosingKM = TripSheet.getTripClosingKM();
				UsageKM = ClosingKM - TripSheet.getTripOpeningKM();
			}

		}else {
				if(sheet.getTripGpsOpeningKM() != null && sheet.getTripGpsOpeningKM() > 0 && TripSheet.getTripGpsClosingKM() != null && TripSheet.getTripGpsClosingKM() > 0) {
					
					if(gpsUssageKM.intValue() > 0) {
						ClosingKM = CheckVehicleStatus.getVehicle_Odometer() + gpsUssageKM.intValue();
						UsageKM = gpsUssageKM.intValue();
					}else {
						ClosingKM = TripSheet.getTripClosingKM();
						UsageKM = ClosingKM - TripSheet.getTripOpeningKM();
					}
				}
		} */
		
		//getVehicleExpectedKm
		boolean isNewVehicleTripSheet = false;
		if (TripSheet.getVid() == null || TripSheet.getVid() <= 0) {
			isNewVehicleTripSheet = true;
		}
			
			if (isNewVehicleTripSheet
					|| CheckVehicleStatus.getVehicleExpectedKm() == null || CheckVehicleStatus.getVehicleExpectedKm() == 0
					|| TripSheet.getTripClosingKM() <= CheckVehicleStatus.getVehicle_ExpectedOdameter() || (boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {

				// Note: TripSheet Validate Only Current date only Closed

				java.util.Date currentDate = new java.util.Date();
				if (removeTime(Close_date).equals(removeTime(currentDate))
						|| removeTime(Close_date).before(removeTime(currentDate)) || (boolean)configuration.get("allowToCloseTripSheetBeforeDate")) {
					
					if((boolean) tollConfig.get(TollApiConfiguration.ALLOW_TOLL_APII_NTEGRATION) || (boolean) tollConfig.get(TollApiConfiguration.ALLOW_KVB_BANK_TOLL_API)) {
						valueTollObject	= new ValueObject();
						valueTollObject.put("StartTransactionDate", sheet.getDispatchedByTimeOn()+"");
						valueTollObject.put("EndTransactionDate", CloseByTime+"");
						valueTollObject.put("VehicleNumber", CheckVehicleStatus.getVehicle_registration().replaceAll("-", ""));
						valueTollObject.put("tripSheetId", TripSheet.getTripSheetID());
						valueTollObject.put("vid", CheckVehicleStatus.getVid());
						valueTollObject.put("vehicle_registration", CheckVehicleStatus.getVehicle_registration());
						valueTollObject.put("userDetails", userDetails);
						
						valueTollObject	=	tollExpensesDetailsService.addVehicleTripTollExpenses(valueTollObject);
						
					}
					
					if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_IV_LOADING_SHEET_ENTRY)) {
							ValueObject		valueObject	= new ValueObject();
				    		
							valueObject.put("fromDate", sheet.getDispatchedByTimeOn()+"");
							valueObject.put("toDate", CloseByTime+"");
							valueObject.put("vehicleNumber", CheckVehicleStatus.getVehicle_registration());
							valueObject.put("tripSheetId", TripSheet.getTripSheetID());
							valueObject.put("vid", CheckVehicleStatus.getVid());
							valueObject.put("vehicle_registration", CheckVehicleStatus.getVehicle_registration());
							valueObject.put("userDetails", userDetails);
							valueObject.put("fromTripSheetClose", true);
							
							tripSheetService.getIVLoadingSheetDataForTrip(valueObject);
					}
					 
					
					
					if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_TKT_INCOME_API_INTEGERATION)) {
						ValueObject		valueTktIncmObject	= new ValueObject();
						valueTktIncmObject.put("FromDate", sheet.getDispatchedByTimeOn()+"");
						valueTktIncmObject.put("ToDate", CloseByTime+"");
						valueTktIncmObject.put("startDate", sheet.getDispatchedByTimeOn());
						valueTktIncmObject.put("endDate", CloseByTime);
						valueTktIncmObject.put("tripSheetId", TripSheet.getTripSheetID());
						valueTktIncmObject.put("vid", sheet.getVid());
						valueTktIncmObject.put("BusNumber", sheet.getVehicle_registration());
						valueTktIncmObject.put("companyId", userDetails.getCompany_id());
						valueTktIncmObject.put("userId", userDetails.getId());
						
						TicketIncomeApiService.addVehicleTicketIncome(valueTktIncmObject);
					}
					
					if((boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_ITS_GATEWAY_BUS_INCOME) && busDeatils != null) {
						ValueObject		valueTktIncmObject	= new ValueObject();
						
						java.util.Date fromDate = sheet.getDispatchedByTimeOn();
						fromDate.setTime(fromDate.getTime() - 1800 * 1000);
						
						valueTktIncmObject.put("FromDate", dateTimeFormat.format(fromDate));
						valueTktIncmObject.put("ToDate", DateTimeUtility.getDateFromTimeStamp(CloseByTime, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
						valueTktIncmObject.put("tripSheetId", TripSheet.getTripSheetID());
						valueTktIncmObject.put("vid", sheet.getVid());
						valueTktIncmObject.put("BusID", busDeatils.getBusId());
						valueTktIncmObject.put("companyId", userDetails.getCompany_id());
						valueTktIncmObject.put("userId", userDetails.getId());
						valueTktIncmObject.put("configuration", configuration);
						
						TicketIncomeApiService.addITSGatewayBusIncome(valueTktIncmObject);
					} 

					/**
					 * Transaction Checker For Expense
					 */
					List<TripSheetExpenseDto> tripSheetExpenseList	=	tripSheetService.findAllTripSheetExpense(TripSheet.getTripSheetID(), userDetails.getCompany_id());
					
					
					VehicleProfitAndLossTxnChecker		profitAndLossExpenseTxnChecker	= null;
					if(tripSheetExpenseList != null) {
						ValueObject		object	=	null;
						for(TripSheetExpenseDto	expenseDto : tripSheetExpenseList) {
							if(expenseDto.getExpenseAmount() > 0.0 && (expenseDto.getFuel_id() == null || expenseDto.getFuel_id() == 0) ) {

								object	= new ValueObject();

								object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
								object.put(VehicleProfitAndLossDto.TRANSACTION_ID, TripSheet.getTripSheetID());
								object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
								object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
								object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
								object.put(VehicleProfitAndLossDto.TRANSACTION_VID, TripSheet.getVid());
								object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, expenseDto.getExpenseId());
								object.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, expenseDto.getTripExpenseID());

								profitAndLossExpenseTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);

								vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);

								expenseTxnCheckerHashMap.put(profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossExpenseTxnChecker);
								tripSheetExpenseHM.put(expenseDto.getTripExpenseID(), expenseDto);
							}
						}
					}

					/**
					 * Transaction Checker For Income
					 */

					List<TripSheetIncomeDto>	tripSheetIncomeList		=		tripSheetService.findAllTripSheetIncome(TripSheet.getTripSheetID(), userDetails.getCompany_id());
					if(tripSheetIncomeList != null) {

						for(TripSheetIncomeDto	incomeDto : tripSheetIncomeList) {
							if(incomeDto.getIncomeAmount() > 0.0) {

								ValueObject		incomeObject	= new ValueObject();

								incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
								incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, TripSheet.getTripSheetID());
								incomeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
								incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
								incomeObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
								incomeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, TripSheet.getVid());
								incomeObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, incomeDto.getIncomeId());
								incomeObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, incomeDto.getTripincomeID());

								VehicleProfitAndLossIncomeTxnChecker		profitAndLossIncomeTxnChecker	= txnCheckerBL.getVehicleProfitAndLossIncomeTxnChecker(incomeObject);
								//vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);
								vehicleProfitAndLossIncomeTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossIncomeTxnChecker);

								incomeTxnCheckerHashMap.put(profitAndLossIncomeTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossIncomeTxnChecker);
								tripSheetIncomeDtoHM.put(incomeDto.getTripincomeID(), incomeDto);
							}
						}

					}
					UserProfileDto Orderingname = userProfileService
							.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());

					tripSheetService.updateCloseTripSheet(TripSheet.getTripSheetID(), ClosingKM, UsageKM,
							ClosingKMStatus, TripSheet.getCloseTripStatusId(), TripSheet.getCloseTripReference(),
							TripSheet.getCloseTripAmount(), TripSheet.getCloseTripNameById(), Close_date,
							userDetails.getId(), LASTUPDATED_DATE, userDetails.getId(), Orderingname.getBranch_id(),
							CloseByTime, userDetails.getCompany_id(), TripSheet.getTripGpsClosingKM(), TripSheet.getGpsCloseLocation(), gpsUssageKM, 0.0);

					if((boolean) configuration.get("tripOpenCloseFuelRequired") && TripSheet.getTripEndDiesel() != null) {
						tripSheetService.updateBalanceFuel(TripSheet.getTripSheetID(), TripSheet.getTripEndDiesel());
					}
					
					
					model.put("saveTripSheet", true);
					model.put("configuration", configuration);

					// this Update Current Odometer in Vehicle Getting Trip
					// Daily Sheet
					if(!(boolean) gpsConfig.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION) || (int)gpsConfig.get(VehicleGPSConfiguratinConstant.GPS_FLAVOR) != GPSConstant.VEHICLE_GPS_FLAVOR_GPS_ONLY) {
						Update_Current_OdometerinVehicle_getTripDailySheetTo_DTO(TripSheet, ClosingKM);
					}else {
						Update_Current_OdometerinVehicle_GpsUsage(TripSheet, gpsUssageKM);
					}

					try {
						TripSheetDto trip = tripSheetBL.Get_TripSheet_To_DriverAttendance(sheet);

						// Note : When Vehicle Created TRIPROUTE That time Vehicle
						// Status update to 'TRIPROUTE'
						vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, trip.getVid(),
							 userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);

						Double FristDriverRoutePoint = TRIP_SHEET_DEFALAT_AMOUNT;
						Double SecDriverRoutePoint = TRIP_SHEET_DEFALAT_AMOUNT;
						Double CleanerRoutePoint = TRIP_SHEET_DEFALAT_AMOUNT;


						if (ROUTE_DRIVERID != null || ROUTE_POINT != null) {

							for (int i = TRIP_SHEET_DEFALAT_ZERO; i < ROUTE_DRIVERID.length; i++) {

								Integer DriverRouteID = Integer.parseInt(ROUTE_DRIVERID[i]);
								if (DriverRouteID.equals(trip.getTripFristDriverID())) {
									FristDriverRoutePoint += Double.parseDouble(ROUTE_POINT[i]);
								} else if (DriverRouteID.equals(trip.getTripSecDriverID())) {
									SecDriverRoutePoint += Double.parseDouble(ROUTE_POINT[i]);
								} else if (DriverRouteID.equals(trip.getTripCleanerID())) {
									CleanerRoutePoint += Double.parseDouble(ROUTE_POINT[i]);
								}
								// close if condition checking
							} // close for loop
						}
						if (trip.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
							// this First Driver Point
							Add_TripSheet_TO_driver_Attendance(trip.getTripFristDriverID(),
									trip.getTripFristDriverName(), Close_date, FristDriverRoutePoint, trip, userDetails);

							// Note : When Vehicle Created TRIPROUTE That time
							// Vehicle
							// Status update to 'TRIPROUTE'
							driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
									TRIP_SHEET_DEFALAT_VALUE, trip.getTripFristDriverID(), userDetails.getCompany_id());
							
							
						}

						if (trip.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {

							// this Second Driver point
							Add_TripSheet_TO_driver_Attendance(trip.getTripSecDriverID(), trip.getTripSecDriverName(),
									Close_date, SecDriverRoutePoint, trip, userDetails);

							// Note : When Vehicle Created TRIPROUTE That time
							// Vehicle
							// Status update to 'TRIPROUTE'
							driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
									TRIP_SHEET_DEFALAT_VALUE, trip.getTripSecDriverID(), userDetails.getCompany_id());
							
				
						}

						if (trip.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {

							// this Cleaner Driver point
							Add_TripSheet_TO_driver_Attendance(trip.getTripCleanerID(), trip.getTripCleanerName(),
									Close_date, CleanerRoutePoint, trip, userDetails);

							// Note : When Vehicle Created TRIPROUTE That time
							// Vehicle
							// Status update to 'TRIPROUTE'
							driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
									TRIP_SHEET_DEFALAT_VALUE, trip.getTripCleanerID(), userDetails.getCompany_id());
							

						}
						
						if (trip.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
							tripSheetService.updateFirstDriverRoutePoint(TripSheet.getTripSheetID(), FristDriverRoutePoint, userDetails.getCompany_id());
						}
						
						if (trip.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
							tripSheetService.updateSecDriverRoutePoint(TripSheet.getTripSheetID(), SecDriverRoutePoint, userDetails.getCompany_id());
						}
						
						if (trip.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
							tripSheetService.updateCleanerRoutePoint(TripSheet.getTripSheetID(), CleanerRoutePoint, userDetails.getCompany_id());
						}

						List<DriverHaltDto> DriverHalt = tripSheetBL.Driver_Halt_CreateAttendanccey(
								driverHaltService.Find_list_TripSheet_DriverHalt(TripSheet.getTripSheetID(), userDetails.getCompany_id()));
						// Halt table to update attendance details
						if (DriverHalt != null && !DriverHalt.isEmpty()) {
							for (DriverHaltDto dHalt : DriverHalt) {

								if (dHalt.getDRIVERID() == trip.getTripFristDriverID()) {

									LocalDate dHaltstartCle = LocalDate.parse(dHalt.getHALT_DATE_FROM()),
											dHaltendCle = LocalDate.parse(dHalt.getHALT_DATE_TO());

									LocalDate nextCle = dHaltstartCle.minusDays(1);
									while ((nextCle = nextCle.plusDays(1)).isBefore(dHaltendCle.plusDays(1))) {
										try {
											if (nextCle != null) {

												DriverAttendance attendanceCle = driverBL.Driver_HALT_Attendance_to_tripSheet(dHalt.getDRIVERID(), dHalt.getDRIVER_NAME(), trip, userDetails);

												java.util.Date attendanceDate = driverAttendanceFormat
														.parse("" + nextCle);
												java.sql.Date Reported_DateCle = new Date(attendanceDate.getTime());
												attendanceCle.setATTENDANCE_DATE(Reported_DateCle);

												attendanceCle.setPOINT_STATUS_ID(
														DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT);
												// attendanceCle.setPOINT_STATUS(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT_NAME);
												attendanceCle.setPOINT_TYPE_ID(
														DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIPHALT);
												// attendanceCle.setPOINT_TYPE(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_HALT_NAME);

												attendanceCle.setDRIVER_POINT(1.0);
												attendanceCle.setCOMPANY_ID(userDetails.getCompany_id());
												attendanceCle.setCREATED_BY_ID(userDetails.getId());
												DriverAttendance validateHalt = DriverAttendanceService
														.validate_DriverAttendance_Halt_details(dHalt.getDRIVERID(),
																driverAttendanceFormat
																.format(attendanceCle.getATTENDANCE_DATE()),
																attendanceCle.getPOINT_TYPE_ID(), userDetails.getCompany_id());
												if (validateHalt != null) {
													DriverAttendanceService.Update_DriverAttendance_Halt_point(
															attendanceCle.getATTENDANCE_STATUS_ID(),
															attendanceCle.getPOINT_STATUS_ID(),
															attendanceCle.getPOINT_TYPE_ID(), 1.0,
															validateHalt.getDAID(), userDetails.getCompany_id());
												} else {
													DriverAttendanceService.addDriverAttendance(attendanceCle);
												}
												
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}

								} else if (dHalt.getDRIVERID() == trip.getTripSecDriverID()) {
									LocalDate d2HaltstartCle = LocalDate.parse(dHalt.getHALT_DATE_FROM()),
											d2HaltendCle = LocalDate.parse(dHalt.getHALT_DATE_TO());

									LocalDate nextCle = d2HaltstartCle.minusDays(1);
									while ((nextCle = nextCle.plusDays(1)).isBefore(d2HaltendCle.plusDays(1))) {
										try {
											if (nextCle != null) {

												DriverAttendance attendanceSEDR = driverBL.Driver_HALT_Attendance_to_tripSheet(dHalt.getDRIVERID(), dHalt.getDRIVER_NAME(), trip, userDetails);

												java.util.Date attendanceDate = driverAttendanceFormat
														.parse("" + nextCle);
												java.sql.Date Reported_DateCle = new Date(attendanceDate.getTime());
												attendanceSEDR.setATTENDANCE_DATE(Reported_DateCle);

												attendanceSEDR.setPOINT_STATUS_ID(
														DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT);
												// attendanceSEDR.setPOINT_STATUS(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT_NAME);
												attendanceSEDR.setPOINT_TYPE_ID(
														DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIPHALT);
												// attendanceSEDR.setPOINT_TYPE(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_HALT_NAME);

												attendanceSEDR.setDRIVER_POINT(1.0);
												DriverAttendance validateHalt = DriverAttendanceService
														.validate_DriverAttendance_Halt_details(dHalt.getDRIVERID(),
																driverAttendanceFormat
																.format(attendanceSEDR.getATTENDANCE_DATE()),
																attendanceSEDR.getPOINT_TYPE_ID(), userDetails.getCompany_id());
												if (validateHalt != null) {
													DriverAttendanceService.Update_DriverAttendance_Halt_point(
															attendanceSEDR.getATTENDANCE_STATUS_ID(),
															attendanceSEDR.getPOINT_STATUS_ID(),
															attendanceSEDR.getPOINT_TYPE_ID(), 1.0,
															validateHalt.getDAID(), userDetails.getCompany_id());
												} else {
													DriverAttendanceService.addDriverAttendance(attendanceSEDR);
												}
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									} // close second driver while loop
								} else if (dHalt.getDRIVERID() == trip.getTripCleanerID()) {

									LocalDate CLHaltstartCle = LocalDate.parse(dHalt.getHALT_DATE_FROM()),
											CLHaltendCle = LocalDate.parse(dHalt.getHALT_DATE_TO());

									LocalDate nextCle = CLHaltstartCle.minusDays(1);
									while ((nextCle = nextCle.plusDays(1)).isBefore(CLHaltendCle.plusDays(1))) {
										try {
											if (nextCle != null) {

												DriverAttendance attendanceCL = driverBL.Driver_HALT_Attendance_to_tripSheet(dHalt.getDRIVERID(), dHalt.getDRIVER_NAME(), trip, userDetails);

												java.util.Date attendanceDate = driverAttendanceFormat
														.parse("" + nextCle);
												java.sql.Date Reported_DateCle = new Date(attendanceDate.getTime());
												attendanceCL.setATTENDANCE_DATE(Reported_DateCle);
												attendanceCL.setPOINT_STATUS_ID(
														DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT);
												// attendanceCL.setPOINT_STATUS(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT_NAME);
												attendanceCL.setPOINT_TYPE_ID(
														DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIPHALT);
												// attendanceCL.setPOINT_TYPE(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_HALT_NAME);

												attendanceCL.setDRIVER_POINT(1.0);
												DriverAttendance validateHalt = DriverAttendanceService
														.validate_DriverAttendance_Halt_details(dHalt.getDRIVERID(),
																driverAttendanceFormat
																.format(attendanceCL.getATTENDANCE_DATE()),
																attendanceCL.getPOINT_TYPE_ID(), userDetails.getCompany_id());
												if (validateHalt != null) {
													DriverAttendanceService.Update_DriverAttendance_Halt_point(
															attendanceCL.getATTENDANCE_STATUS_ID(),
															attendanceCL.getPOINT_STATUS_ID(),
															attendanceCL.getPOINT_TYPE_ID(), 1.0,
															validateHalt.getDAID(), userDetails.getCompany_id());
												} else {
													DriverAttendanceService.addDriverAttendance(attendanceCL);
												}
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									} // close Cleaner driver while loop
								}
								
								
								
							}

						}

						
						if (TripSheet.getTripsheetextraname() != null || TripSheet.getTripSheetExtraQuantityReceived() != null || TripSheet.getTripSheetExtraDescription() != null)
						{	
							TripSheet tsheet = new TripSheet();
							tsheet.setTripSheetID(TripSheet.getTripSheetID());

							for (int i = TRIP_SHEET_DEFALAT_ZERO; i < TripSheet.getTripsheetextraname().length; i++) {

								TripSheetExtraReceived TripExtraOptionsReceived = new TripSheetExtraReceived();

								TripExtraOptionsReceived.setTripsheetoptionsID(Long.parseLong(TripSheet.getTripsheetextraname()[i] + ""));
								TripExtraOptionsReceived.setTripSheetExtraQuantityReceived(Double.parseDouble("" + TripSheet.getTripSheetExtraQuantityReceived()[i]));
								TripExtraOptionsReceived.setTripSheetExtraDescription(TripSheet.getTripSheetExtraDescription()[i]);
								TripExtraOptionsReceived.setCreatedById(userDetails.getId());
								TripExtraOptionsReceived.setTripsheet(tsheet);
								TripExtraOptionsReceived.setCompanyId(userDetails.getCompany_id());

								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								TripExtraOptionsReceived.setCreated(toDate);


								List<TripSheetExtraReceived> validate = tripSheetService.ValidateAllTripSheetExtraOptionsReceived(
										Long.parseLong("" + TripSheet.getTripsheetextraname()[i]), tsheet.getTripSheetID(), userDetails.getCompany_id());

								if (validate != null && !validate.isEmpty()) {

									model.put("alreadyExta", true);


								} else {

									tripSheetService.addTripSheetExtraOptionReceived(TripExtraOptionsReceived);	
									model.put("updateExtra", true);

								}

							}

						}	
						
						 List<DriverAttendanceDto>	attendanceList	=	DriverAttendanceService.getDriverAttandanceListOfTripSheet(trip.getTripSheetID(), userDetails.getCompany_id());
						
						 if (trip.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
							 driverSalaryInsertionDetailsService.saveDriverSalaryInsertionDetails(new DriverSalaryInsertionDetails(trip.getTripFristDriverID(), DateTimeUtility.geTimeStampFromDate(new java.util.Date()), userDetails.getCompany_id()));
						 }
						 if (trip.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
							 driverSalaryInsertionDetailsService.saveDriverSalaryInsertionDetails(new DriverSalaryInsertionDetails(trip.getTripSecDriverID(), DateTimeUtility.geTimeStampFromDate(new java.util.Date()), userDetails.getCompany_id()));
						 }
						 if (trip.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
							 driverSalaryInsertionDetailsService.saveDriverSalaryInsertionDetails(new DriverSalaryInsertionDetails(trip.getTripCleanerID(), DateTimeUtility.geTimeStampFromDate(new java.util.Date()), userDetails.getCompany_id()));
						 }
						 
						 
						 if(attendanceList != null && !attendanceList.isEmpty()) {
							 
							 
							 driverSalaryExpense	= new VehicleProfitAndLossDto();
								
								driverSalaryExpense.setTxnTypeId(TripSheet.getTripSheetID());
								driverSalaryExpense.setExpenseId(TripSheet.getTripSheetID());
								driverSalaryExpense.setVid(TripSheet.getVid());
								driverSalaryExpense.setTransactionDateTime(new Timestamp(sheet.getClosetripDateOn().getTime()));
								
							 	
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
							 
							 if(driverSalaryExpense.getTxnAmount() > 0) {
								 ValueObject		object	= new ValueObject();
									
									object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
									object.put(VehicleProfitAndLossDto.TRANSACTION_ID, TripSheet.getTripSheetID());
									object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
									object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY);
									object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
									object.put(VehicleProfitAndLossDto.TRANSACTION_VID, TripSheet.getVid());
									object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, Integer.parseInt(TripSheet.getTripSheetID()+""));

									driverSalaryTxn	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);

									vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(driverSalaryTxn);
									
							 }
							 
							 	
						 }
						
						if(tripSheetExpenseHM != null && tripSheetExpenseHM.size() > 0) {

							ValueObject	valueObject	= new ValueObject();
							valueObject.put("tripSheet", tripSheetService.getTripSheet(TripSheet.getTripSheetID()));
							valueObject.put("userDetails", userDetails);
							valueObject.put("tripSheetExpenseHM", tripSheetExpenseHM);
							valueObject.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);

							vehicleProfitAndLossService.runThreadForTripSheetExpenses(valueObject);
						}
						if(tripSheetIncomeDtoHM != null && tripSheetIncomeDtoHM.size() > 0) {
							ValueObject	valueObject	= new ValueObject();
							valueObject.put("tripSheet", tripSheetService.getTripSheet(TripSheet.getTripSheetID()));
							valueObject.put("userDetails", userDetails);
							valueObject.put("tripSheetIncomeHM", tripSheetIncomeDtoHM);
							valueObject.put("incomeTxnCheckerHashMap", incomeTxnCheckerHashMap);
							//	valueObject.put(VehicleExpenseTypeConstant.INCOME_TYPE, incomeTxnCheckerHashMap);

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

				} else {
					String TIDMandatory = ""; //
					TIDMandatory += "<span> This Tripsheet End Date Not Match Today. You can't Close Today.  </span>";
					redir.addFlashAttribute("VMandatory", TIDMandatory);
					model.put("notCurrendDate", true);
					return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "",
							model);
				}

			} else {
				String TIDMandatory = ""; //
				TIDMandatory += "<span> This vehicle  Maxminum Allow Odometer "
						+ CheckVehicleStatus.getVehicle_ExpectedOdameter() + " kindly Check Odometer </span>";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("notCurrendDate", true);
				return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "",
						model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {

				model.put("alreadyTripSheet", true);

				LOGGER.error("TripSheet Page:", e);
				return new ModelAndView("redirect:/newTripSheetEntries.in", model);
			} catch (Exception e1) {
				LOGGER.error("TripSheet Page:", e);
			}
		}finally {
			incomeTxnCheckerHashMap			= null;
			expenseTxnCheckerHashMap		= null;
			tripSheetExpenseHM				= null;
			tripSheetIncomeDtoHM			= null;
			driverSalaryTxn					= null;
			driverSalaryExpense				= null;
			CloseByTime 					= null;
			valueTollObject					= null; 
		}
		model.put("ATTSUCESS", true);
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "");
	}

	// Note: This /dispatchTS Controller is show SAVED STATUS TO Change Dispatch
	// Status Details
	@RequestMapping(value = "/dispatchTS", method = RequestMethod.GET)
	public ModelAndView dispatchTS(@RequestParam("ID") final Long TripSheetID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		HashMap<String, Object> 	configuration	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			model.put("configuration", configuration);
			TripSheetDto sheetDto	=	tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheetID, userDetails));
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(sheetDto.getVid());
			
			model.put("maxAllowed", sheetDto.getTripOpeningKM() + CheckVehicleStatus.getVehicleExpectedKm());
			model.put("minAllowed", sheetDto.getTripOpeningKM());
			model.put("vehicle_ExpectedOdameter", CheckVehicleStatus.getVehicleExpectedKm());
			
			model.put("TripSheet", sheetDto);
			// get Trip sheet id to get all Advance Details
			model.put("TripSheetAdvance",
					tripSheetService.findAllTripSheetAdvance(TripSheetID, userDetails.getCompany_id()));
			model.put("vehiclegroup", vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id()));
			
			HashMap<String, Object> vehicleCnfiguration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
					PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, 
					vehicleCnfiguration.getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, 
					vehicleCnfiguration.getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, false));

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		} finally {

		}
		return new ModelAndView("dispatchTripSheet", model);
	}
	// Note: This /editTripSheet Controller is TripSheet Id to Edit the Details
	@RequestMapping(value = "/editTripSheet", method = RequestMethod.GET)
	public ModelAndView editTripSheet(@RequestParam("ID") final Long TripSheetID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration	= null;
		HashMap<String, Object> 	gpsConfiguration	= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			TripSheetDto	sheetDto = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheetID, userDetails));
			model.put("TripSheet", sheetDto);
			// get Trip sheet id to get all Advance Details
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(sheetDto.getVid());
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();

			TripSheetDto	tripSheetDto		= tripSheetService.getLastClosedTripSheetByDateTimeById(sheetDto.getVid(), sheetDto.getDispatchedOn(), TripSheetID);
			TripSheetDto	nextTripSheet	  	= tripSheetService.getNextClosedTripSheetByDateTime(sheetDto.getVid(), sheetDto.getDispatchedOn(), TripSheetID);
			VehicleOdometerHistory	history = vehicleOdometerHistoryService.getLastOdometerHistory(sheetDto.getVid(), sheetDto.getTripOpeningKM());
			Integer minAllowed = 0;
			Integer maxAllowed = 0;
		
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
			model.put("minAllowed", minAllowed);	
			model.put("maxAllowed", maxAllowed);
			
			model.put("vehicle_ExpectedOdameter", CheckVehicleStatus.getVehicleExpectedKm());
			model.put("vehicle_ExpectedMileage", CheckVehicleStatus.getVehicle_ExpectedMileage());
			model.put("TripSheetAdvance",
					tripSheetService.findAllTripSheetAdvance(TripSheetID, userDetails.getCompany_id()));
			ObjectMapper objectMapper = new ObjectMapper();
			model.put("config", objectMapper.writeValueAsString(configuration));
			
			model.put("configuration", configuration);
			model.put("allowGPSIntegration", (boolean)gpsConfiguration.get("allowGPSIntegration"));
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			model.put("vehiclegroup", vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id()));
			
			HashMap<String, Object> vehicleCnfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
					PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, 
					vehicleCnfiguration.getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, 
					vehicleCnfiguration.getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, false));
			
			if(sheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
				model.put("TripClosed", true);	
			} else {
				model.put("TripClosed", false);
			}
			
			if(permission.contains(new SimpleGrantedAuthority("NUM_OF_BACK_DATE_FOR_ADMIN"))){
				model.put("backDateStr", DateTimeUtility.getBackDateStr((int)configuration.get("noOfDaysForBackDateForAdmin")));
			}else {
				model.put("backDateStr", DateTimeUtility.getBackDateStr((int)configuration.get("noOfDaysForBackDate")));
			}


		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("editTripSheet", model);
	}

	@RequestMapping(value = "/dispatchTripSheet", method = RequestMethod.GET)
	public ModelAndView dispatchTripSheet(@RequestParam("ID") final Long TripSheetID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration	= null;
		HashMap<String, Object> 	gpsConfiguration	= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			TripSheetDto	sheetDto = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheetID, userDetails));
			model.put("TripSheet", sheetDto);
			// get Trip sheet id to get all Advance Details
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(sheetDto.getVid());
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if(sheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
				model.put("maxAllowed", sheetDto.getTripClosingKM());
			}else {
				model.put("maxAllowed", sheetDto.getTripOpeningKM() + CheckVehicleStatus.getVehicleExpectedKm());
			}
			model.put("minAllowed", sheetDto.getTripOpeningKM());
			model.put("vehicle_ExpectedOdameter", CheckVehicleStatus.getVehicleExpectedKm());
			model.put("TripSheetAdvance",
					tripSheetService.findAllTripSheetAdvance(TripSheetID, userDetails.getCompany_id()));
			ObjectMapper objectMapper = new ObjectMapper();
			model.put("config", objectMapper.writeValueAsString(configuration));
			model.put("allowGPSIntegration", (boolean)gpsConfiguration.get("allowGPSIntegration"));
			model.put("configuration", configuration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			model.put("vehiclegroup", vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id()));
			HashMap<String, Object> vehicleCnfiguration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
					PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, 
					vehicleCnfiguration.getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, 
					vehicleCnfiguration.getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, false));
			
			
			if(permission.contains(new SimpleGrantedAuthority("NUM_OF_BACK_DATE_FOR_ADMIN"))){
				model.put("backDateStr", DateTimeUtility.getBackDateStr((int)configuration.get("noOfDaysForBackDateForAdmin")));
			}else {
				model.put("backDateStr", DateTimeUtility.getBackDateStr((int)configuration.get("noOfDaysForBackDate")));
			}



		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("dispatchTripSheet", model);
	}

	
	
	@RequestMapping(value = "/editTripSheet2", method = RequestMethod.GET)
	public ModelAndView editTripSheet2(@RequestParam("ID") final Long TripSheetID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration	= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			TripSheetDto	sheetDto = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheetID, userDetails));
			model.put("TripSheet", sheetDto);
			// get Trip sheet id to get all Advance Details
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(sheetDto.getVid());
			
			model.put("maxAllowed", sheetDto.getTripOpeningKM() + CheckVehicleStatus.getVehicleExpectedKm());
			model.put("minAllowed", sheetDto.getTripOpeningKM());
			model.put("vehicle_ExpectedOdameter", CheckVehicleStatus.getVehicleExpectedKm());
			model.put("TripSheetAdvance",
					tripSheetService.findAllTripSheetAdvance(TripSheetID, userDetails.getCompany_id()));
			model.put("configuration", configuration);
			model.put("vehiclegroup", vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id()));
			HashMap<String, Object> vehicleCnfiguration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
					PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, 
					vehicleCnfiguration.getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_TRIP_SHEET, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, 
					vehicleCnfiguration.getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_SHEET, false));



		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("editTripSheet2", model);
	}

	// Note: This /uploadTripSheet Controller is Edit page to Update Tripsheet
	// Details the Value Of TripSheet
	@RequestMapping(value = "/uploadTripSheet", method = RequestMethod.POST)
	public ModelAndView uploadTripSheet(@ModelAttribute("command") TripSheetDto sheetDto,
			TripSheetAdvance TripSheetAdvance, final HttpServletRequest request, RedirectAttributes redir)
					throws Exception {

		Map<String, Object> 	model 						= new HashMap<String, Object>();
		CustomUserDetails 		userDetails 				= null;
		boolean 				validateTripsheet 			= false;
		boolean 				checkFuelMeterStatus 		= false;
		boolean 				isNewVehicleTripsheet 		= false;

		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
		// here Check Statues dispatch or save
		if (sheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
			String TIDMandatory = "";

			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(sheetDto.getVid());
			if (CheckVehicleStatus != null) {
				if (sheetDto.getTripOpeningKM() <= CheckVehicleStatus.getVehicle_ExpectedOdameter()) {

					checkFuelMeterStatus = true;
				}
			} else {
				isNewVehicleTripsheet = true;
			}
			if (checkFuelMeterStatus || isNewVehicleTripsheet) {

				// Checking validate of Query in TripSheet Account
				List<TripSheetDto> ValidateTSOlD = null;
				if (!isNewVehicleTripsheet) {
					ValidateTSOlD = tripSheetService.Get_Validate_TripSheet_Vehicle_DriverAcount(sheetDto.getVid(),
							userDetails.getCompany_id());
				} else {
					ValidateTSOlD = tripSheetService.Get_Validate_TripSheet_Vehicle(sheetDto.getVehicle_registration(),
							userDetails.getCompany_id());
				}
				if (ValidateTSOlD != null && !ValidateTSOlD.isEmpty()) {
					String TID = "";
					for (TripSheetDto add : ValidateTSOlD) {
						TID += "<a href=\"addCloseTripsheet.in?tripSheetID=" + add.getTripSheetID()
						+ "\" target=\"_blank\">TS-" + add.getTripSheetNumber()
						+ "  <i class=\"fa fa-external-link\"></i></a> ,";
					}
					redir.addFlashAttribute("CloseTS", TID);
					validateTripsheet = false;
				} else {
					validateTripsheet = true;
				}

				String[] From_TO_Array = null;
				try {
					String From_TO = sheetDto.getTripOpenDate();
					From_TO_Array = From_TO.split("  to  ");
					java.util.Date date = dateFormat.parse(From_TO_Array[0]);
					java.sql.Date tripfromDate = new Date(date.getTime());

					// Trip close Apprax
					java.util.Date dateTo = dateFormat.parse(From_TO_Array[1]);
					java.sql.Date triptoDate = new Date(dateTo.getTime());

					// Note: TripSheet Validate Only Current date only Dispatch
					java.util.Date currentDate = new java.util.Date();
					if (removeTime(tripfromDate).equals(removeTime(currentDate)) || (boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_BACK_DATE_ENTRY)) {
						if (!isNewVehicleTripsheet) {

							// Note: Below Check Vehicle Mandatory Compliance Details
							try {
								List<VehicleMandatoryDto> Select = vehicleMandatoryService
										.List_Vehicle_Mandatory_Details_GET_VEHICLEID_NOT_EXISTS(sheetDto.getVid(),
												tripfromDate, triptoDate, userDetails.getCompany_id());
								if (Select != null && !Select.isEmpty()) {

									// Checking the Value Of Mandatory Compliance
									for (VehicleMandatoryDto add : Select) {
										TIDMandatory += "<span>" + add.getVEHICLE_REGISTRATION()
										+ " Mandatory Compliance <a href=\"ShowVehicleMandatory.in?vehid="
										+ add.getVEHICLE_ID() + "\" target=\"_blank\">"
										+ add.getMANDATORY_RENEWAL_SUB_NAME()
										+ "  <i class=\"fa fa-external-link\"></i></a> is NOT Available On "
										+ tripfromDate + " To " + triptoDate + " </span>, <br>";
									}
									redir.addFlashAttribute("VMandatory", TIDMandatory);
									validateTripsheet = false;

								} else {

									// Showing Mandatory Compliance to Set Vehicle
									TIDMandatory += "<span>This Vehicle Mandatory Compliances are Available On "
											+ tripfromDate + " To " + triptoDate
											+ "  <a href=\"ShowVehicleMandatory.in?vehid=" + sheetDto.getVid()
											+ "\" target=\"_blank\"> Any Changes Mandatory Compliances <i class=\"fa fa-external-link\"></i></a></span>,<br>";
									redir.addFlashAttribute("VMandatory", TIDMandatory);

								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					} else {

						TIDMandatory += "<span> This Tripsheet Start Date Not Match Today. You can't Dispatch. Please change Journey Date. </span>";
						redir.addFlashAttribute("VMandatory", TIDMandatory);
						validateTripsheet = false;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} // checking Vehicle Status
			else {
				if (!isNewVehicleTripsheet) {
					String Link = "";
					if (CheckVehicleStatus != null) {
						if (sheetDto.getTripOpeningKM() > CheckVehicleStatus.getVehicle_ExpectedOdameter()) {

							Link += " Maxminum Allow Odometer " + CheckVehicleStatus.getVehicle_ExpectedOdameter()
							+ " kindly Check Odometer ";
						}

						TIDMandatory += " <span> This " + sheetDto.getVehicle_registration() + " Vehicle in "
								+ VehicleStatusConstant.getVehicleStatus(CheckVehicleStatus.getvStatusId())
								+ " Status you can't create Service Entires " + " " + Link + "" + " </span> ,";

						redir.addFlashAttribute("VMandatory", TIDMandatory);
						model.put("Close", true);
						return new ModelAndView("redirect:/dispatchTripSheet.in?ID=" + sheetDto.getTripSheetID() + "", model);
					}

				}
			}
		} else {
			validateTripsheet = true;
		}
		if (validateTripsheet) {
			if (sheetDto.getTripOpenDate() != null) {
				TripSheet 			tripSheet 			= prepareUpdate(sheetDto);
				TripSheet 			SheetOLDData 		= tripSheetService.getTripSheet(sheetDto.getTripSheetID());
				TripSheetHistory	tripSheetHistory	= prepareTripSheetHistory(SheetOLDData);

				try {

					if (TripSheetAdvance.getAdvanceAmount() != null) {
						tripSheet.setTripTotalAdvance(TripSheetAdvance.getAdvanceAmount());
						tripSheet.setTripTotalexpense(SheetOLDData.getTripTotalexpense());
						tripSheet.setTripTotalincome(SheetOLDData.getTripTotalincome());
					} else {
						tripSheet.setTripTotalAdvance(SheetOLDData.getTripTotalAdvance());
						tripSheet.setTripTotalexpense(SheetOLDData.getTripTotalexpense());
						tripSheet.setTripTotalincome(SheetOLDData.getTripTotalincome());
					}

					tripSheet.setClosedById(SheetOLDData.getClosedById());
					tripSheet.setClosedLocationId(SheetOLDData.getClosedLocationId());
					tripSheet.setClosedByTime(SheetOLDData.getClosedByTime());

					tripSheet.setAcclosedById(SheetOLDData.getAcclosedById());
					tripSheet.setAcclosedLocationId(SheetOLDData.getAcclosedLocationId());
					tripSheet.setAcclosedByTime(SheetOLDData.getAcclosedByTime());
					if(SheetOLDData.getVoucherDate() != null)
						tripSheet.setVoucherDate(SheetOLDData.getVoucherDate());

					// Check DISPATCHED Get Person Location And email Id
					if (sheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {

						UserProfileDto Orderingname = userProfileService
								.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());

						tripSheet.setDispatchedLocationId(Orderingname.getBranch_id());
						tripSheet.setDispatchedById(userDetails.getId());
						
						System.err.println("dispatch by time "+SheetOLDData.getDispatchedByTime());
						System.err.println("dispatch  time "+sheetDto.getDispatchedByTime());
						System.err.println("dispatch  time 2"+sheetDto.getDispatchedTime());

						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp dispatchedByTime = new java.sql.Timestamp(currentDateUpdate.getTime());
						if(SheetOLDData.getDispatchedByTime() != null) {
							tripSheet.setDispatchedByTime(SheetOLDData.getDispatchedByTime());
						}else {
							tripSheet.setDispatchedByTime(dispatchedByTime);
						}
						
					}

					tripSheet.setTripFristDriverBata(SheetOLDData.getTripFristDriverBata());
					tripSheet.setTripSecDriverBata(SheetOLDData.getTripSecDriverBata());
					tripSheet.setTripCleanerBata(SheetOLDData.getTripCleanerBata());
					tripSheet.setCompanyId(userDetails.getCompany_id());
					tripSheet.setCreatedById(SheetOLDData.getCreatedById());
					tripSheet.setLastModifiedById(userDetails.getId());
					tripSheet.setCreated(SheetOLDData.getCreated());
					tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
					
					tripSheetService.updateTripSheet(tripSheet);

					if (TripSheetAdvance.getAdvanceAmount() != null) {
						TripSheetAdvance TripAdvance = tripSheetBL.prepareTripAdvance(TripSheetAdvance);
						TripAdvance.setCompanyId(userDetails.getCompany_id());
						TripAdvance.setCreatedById(TripSheetAdvance.getCreatedById());
						TripAdvance.setTripsheet(tripSheet);
						tripSheetService.updateTripSheetAdvance(TripAdvance);

					}

					// here Check Statues dispatch or save
					if (tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
						// Note : When Vehicle Created TRIPROUTE That time
						// Vehicle
						// Status update to 'WORKSHOP'
						vehicleService.Update_Vehicle_Status_TripSheetID(tripSheet.getTripSheetID(), tripSheet.getVid(),
								userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE);

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

					model.put("saveTripSheet", true);

					// update the Current Odometer vehicle Databases tables
					try {
						if (tripSheet.getVid() != TRIP_SHEET_DEFALAT_ZERO) {

							// this Update Current Odometer in Vehicle Getting
							// Trip
							// Daily Sheet
							Update_Current_OdometerinVehicle_getTripDailySheetTo(tripSheet);
						}
					} catch (Exception e) {
						LOGGER.error("TripSheet Page:", e);
						e.printStackTrace();

					}

				} catch (Exception e) {
					try {
						System.err.println("error >>"+ e.getLocalizedMessage());
						model.put("alreadyTripSheet", true);

						LOGGER.error("TripSheet Page:", e);
						return new ModelAndView("redirect:/newTripSheetEntries.in", model);
					} catch (Exception e1) {
						LOGGER.error("TripSheet Page:", e);
					}
				}
			} else {
				return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + sheetDto.getTripSheetID() + "",
						model);
			}

			return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + sheetDto.getTripSheetID() + "", model);
		}
		return new ModelAndView("redirect:/dispatchTripSheet.in?ID=" + sheetDto.getTripSheetID() + "&Close=true");
	}

	// Note: This /uploadEditTripSheet controller to Edit
	@RequestMapping(value = "/uploadEditTripSheet", method = RequestMethod.POST)
	public ModelAndView uploadEditTripSheet(@RequestParam("OldFristDriverID") final Integer OldFristDriverID,
			@RequestParam("OldSecDriverID") final Integer OldSecDriverID,
			@RequestParam("OldCleanerID") final Integer OldCleanerID,
			@RequestParam("OldRouteID") final Integer OldRouteID, 
			@RequestParam("OldSubRouteID") final Integer OldSubRouteID,
			@ModelAttribute("command") TripSheetDto TripSheet,
			TripSheetAdvance TripSheetAdvance, final HttpServletRequest request, RedirectAttributes redir)
					throws Exception {
		
		Map<String, Object> 		model 							= new HashMap<String, Object>();
		boolean						allowToEditTripSheetCloseDate	= false;
		boolean						allowToEditVehicleInTripSheet	= false;
		boolean						CheckFuelMeterStatus 			= false;
		boolean 					isNewVehicleTripSheet 			= false;
		int							oldVehicleId					= 0;
		String 						TIDMandatory 					= "";
		List<Fuel>					fuel							= null;
		List<UreaEntries>			urea							= null;
		List<TripSheetIncome>		tripSheetIncome					= null;
		List<TripSheetExpense>		tripSheetExpense				= null;
		ValueObject					newVehicleFuelEntry				= null;
		ValueObject					newVehicleUreaEntry				= null;
		ValueObject					tripSheetIncomeProfitLoss		= null;
		ValueObject					tripSheetExpenseProfitLoss		= null;
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String[] From_TO_Array = null;
		String From_TO = TripSheet.getTripOpenDate();
		From_TO_Array = From_TO.split("  to  ");
		java.util.Date dateTo = dateFormat.parse(From_TO_Array[1]);
		java.sql.Date triptoDate = new Date(dateTo.getTime());
		
		HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
		allowToEditTripSheetCloseDate	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.ALLOW_TO_EDIT_TRIP_SHEET_CLOSE_DATE, false);
		allowToEditVehicleInTripSheet	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.ALLOW_TO_EDIT_VEHICLE_IN_TRIPSHEET, false);

		if (TripSheet.getVid() == null || TripSheet.getVid() <= 0) {
			isNewVehicleTripSheet = true;
		}

		if (TripSheet.getTripOpenDate() != null) {

			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(TripSheet.getVid());
			
			if (isNewVehicleTripSheet || TripSheet.getTripOpeningKM() <= CheckVehicleStatus.getVehicle_ExpectedOdameter()) {
				CheckFuelMeterStatus = true;
			}

			if (CheckFuelMeterStatus || isNewVehicleTripSheet) {
				TripSheet 			Sheet 				= prepareUpdate(TripSheet);
				TripSheet 			SheetOLDData 		= tripSheetService.getTripSheet(TripSheet.getTripSheetID());
				TripSheetHistory	tripSheetHistory	= prepareTripSheetHistory(SheetOLDData);
				oldVehicleId							= SheetOLDData.getVid();
				try {
					// below not there get Old Data
					{
						Sheet.setTripTotalAdvance(SheetOLDData.getTripTotalAdvance());
						Sheet.setTripTotalexpense(SheetOLDData.getTripTotalexpense());
						Sheet.setTripTotalincome(SheetOLDData.getTripTotalincome());

						// here Old data call
						Sheet.setTripClosingKM(SheetOLDData.getTripClosingKM());
						Sheet.setTripUsageKM(SheetOLDData.getTripUsageKM());
						// Sheet.setCloseTripStatus(SheetOLDData.getCloseTripStatus());
						Sheet.setCloseTripStatusId(SheetOLDData.getCloseTripStatusId());
						Sheet.setCloseTripReference(SheetOLDData.getCloseTripReference());
						Sheet.setCloseTripAmount(SheetOLDData.getCloseTripAmount());
						// Sheet.setCloseTripNameBy(SheetOLDData.getCloseTripNameBy());
						Sheet.setCloseTripNameById(SheetOLDData.getCloseTripNameById());
						Sheet.setTripStutesId(SheetOLDData.getTripStutesId());
						
						if(allowToEditTripSheetCloseDate) {
							
							if(Sheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
								Sheet.setClosetripDate(triptoDate);	
							} else {
								Sheet.setClosetripDate(SheetOLDData.getClosetripDate());
							}
							
						} else {
							Sheet.setClosetripDate(SheetOLDData.getClosetripDate());		
						}
						
						// Total Comment details
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

						if (TripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {

							UserProfileDto Orderingname = userProfileService
									.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
							Sheet.setDispatchedLocationId(Orderingname.getBranch_id());
							Sheet.setDispatchedById(userDetails.getId());

							/*java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp dispatchedByTime = new java.sql.Timestamp(currentDateUpdate.getTime());
							if(!(boolean) configuration.get(TripSheetConfigurationConstant.ALLOW_BACK_DATE_ENTRY)) {
								Sheet.setDispatchedByTime(SheetOLDData.getDispatchedByTime());
							}else {
								Sheet.setDispatchedByTime(SheetOLDData.getDispatchedByTime());
							}*/
							Sheet.setDispatchedByTime(SheetOLDData.getDispatchedByTime());
						}
					}

					// Note: Old Select Frist Driver Detail TRIPROUTE TO ACTIVE

					// Trip First Driver ID
					Update_Driver_Status_IN_TripSheetEdit_ACTIVE_TRIPROUTE(Sheet.getTripFristDriverID(),
							OldFristDriverID, Sheet.getTripSheetID(), userDetails.getCompany_id());

					// Trip Second Driver ID
					Update_Driver_Status_IN_TripSheetEdit_ACTIVE_TRIPROUTE(Sheet.getTripSecDriverID(), OldSecDriverID,
							Sheet.getTripSheetID(), userDetails.getCompany_id());

					// Trip Cleaner Driver ID
					Update_Driver_Status_IN_TripSheetEdit_ACTIVE_TRIPROUTE(Sheet.getTripCleanerID(), OldCleanerID,
							Sheet.getTripSheetID(), userDetails.getCompany_id());

					tripSheetService.updateTripSheet(Sheet);
					tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);

					// Change Route expense Details
					Update_Route_fixed_expense_Amount_Old_Route(Sheet.getRouteID(), OldRouteID, Sheet);
					Update_Route_fixed_income_Amount_Old_Route(Sheet.getRouteID(), OldRouteID, Sheet);
					
					if(OldSubRouteID != null && OldSubRouteID > 0)
						Update_SubRoute_fixed_expense_Amount_Old_Route(Sheet.getRouteID(), OldSubRouteID, Sheet);

					model.put("saveTripSheet", true);

					try {
						if (Sheet.getVid() != TRIP_SHEET_DEFALAT_ZERO) {
							Update_Current_OdometerinVehicle_getTripDailySheetTo(Sheet);
						}
					} catch (Exception e) {
						LOGGER.error("TripSheet Page:", e);
						e.printStackTrace();
					}
					
					if(allowToEditVehicleInTripSheet) {   // If vehicle is allowed to edit
						
						if(Sheet.getVid() != SheetOLDData.getVid()) {   // if vehicle has been changed
								
								fuel = FuelRepository.Get_TripSheet_IN_FuelTable_List(Sheet.getTripSheetID(), userDetails.getCompany_id());
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
										fuelService.saveVehicleProfitAndLossTxnChecker(newVehicleFuelEntry);
									}
								}
								
								urea = UreaEntriesRepository.getUreaEntryDeatilsByTripsheetId(Sheet.getTripSheetID(), userDetails.getCompany_id());
								if(urea != null && !urea.isEmpty()) {
									for(UreaEntries ureaDetails : urea) {
										UreaEntriesService.deletePreviousVehicleUreaEntries(ureaDetails);
									}
								}
								
								UreaEntriesService.updateVidOfUreaEntries(Sheet.getTripSheetID(), Sheet.getVid(), userDetails.getCompany_id());
								
								if(urea != null && !urea.isEmpty()) {
									for(UreaEntries ureaDetails : urea) {
										newVehicleUreaEntry = new ValueObject();
										newVehicleUreaEntry.put("ureaEntries", ureaDetails);
										UreaEntriesService.saveVehicleProfitAndLossTxnCheckerForUrea(newVehicleUreaEntry);
									}
								}
								
								if (TripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
									
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
									
								}
						  }	
					}	
					
					
				} catch (Exception e) {
					try {
						model.put("alreadyTripSheet", true);
						
						LOGGER.error("TripSheet Page:", e);
						return new ModelAndView("redirect:/newTripSheetEntries.in", model);
					} catch (Exception e1) {
						LOGGER.error("TripSheet Page:", e);
					}
				}
			} // checking Vehicle Status
			else {
				if (!isNewVehicleTripSheet) {
					String Link = "";
					if (TripSheet.getTripOpeningKM() > CheckVehicleStatus.getVehicle_ExpectedOdameter()) {

						Link += " Maxminum Allow Odometer " + CheckVehicleStatus.getVehicle_ExpectedOdameter()
						+ " kindly Check Odometer ";
					}

					TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
							+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create Service Entires " + " "
							+ Link + "" + " </span> ,";
					redir.addFlashAttribute("VMandatory", TIDMandatory);
					model.put("Close", true);
					return new ModelAndView("redirect:/editTripSheet.in?ID=" + TripSheet.getTripSheetID() + "", model);
				}
			}
		} else {
			return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "");
		}

		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "");
	}
	

	// Note /deleteTripSheet Controller is delete TripSheet ID to delete
	@RequestMapping(value = "/deleteTripSheet", method = RequestMethod.GET)
	public ModelAndView deleteTripSheet(@RequestParam("ID") final Long TripSheetID, final HttpServletRequest request,
			RedirectAttributes redir) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String TIDMandatory = "";
			Boolean validateTripsheet = false;
			TripSheet Sheet = tripSheetService.getTripSheet(TripSheetID);
			boolean newVehicleTripSheet = false;

			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(Sheet.getVid());
			if (CheckVehicleStatus == null) {
				newVehicleTripSheet = true;
			}
			if (newVehicleTripSheet
					|| CheckVehicleStatus != null
					&& CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE
					|| CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {

				List<DriverHaltDto> DriverHalt = driverHaltService.Find_list_TripSheet_DriverHalt(TripSheetID, userDetails.getCompany_id());
				if (DriverHalt != null && !DriverHalt.isEmpty()) {
					String TID = "";
					for (DriverHaltDto add : DriverHalt) {
						TID += "<a href=\"addHaltAmount.in?ID=" + add.getTRIPSHEETID() + "\" target=\"_blank\">"
								+ add.getDRIVERID() + "  <i class=\"fa fa-external-link\"></i></a>";
					}
					redir.addFlashAttribute("CloseTS", TID);
					validateTripsheet = false;

				} else {
					List<FuelDto> ShowFuelTS = fuelService.Get_TripSheet_IN_FuelTable_List(TripSheetID, userDetails.getCompany_id(), userDetails.getId());
					if (ShowFuelTS != null && !ShowFuelTS.isEmpty()) {
						String TID = "";
						for (FuelDto add : ShowFuelTS) {
							TID += "<a href=\"addTSFuel.in?ID=" + add.getFuel_TripsheetID() + "\" target=\"_blank\">F-"
									+ add.getFuel_Number() + "  <i class=\"fa fa-external-link\"></i></a> ,";
						}
						redir.addFlashAttribute("CloseTS", TID);
						validateTripsheet = false;

					} else {
						validateTripsheet = true;
					}
				}

				if (validateTripsheet) {

					tripSheetService.Delete_TSID_to_TripSheetAdvance(TripSheetID, userDetails.getCompany_id());
					tripSheetService.Delete_TSID_to_TripSheetExpence(TripSheetID, userDetails.getCompany_id());
					tripSheetService.Delete_TSTD_to_TripSheetIncome(TripSheetID, userDetails.getCompany_id());
					tripCommentService.Delete_TripSheet_to_TripSheetComment(TripSheetID, userDetails.getCompany_id());

					// Note : When Vehicle Created TRIPROUTE That time Vehicle
					// Status update to 'WORKSHOP'
					vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, Sheet.getVid(),
							userDetails.getCompany_id(),VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
					if (Sheet.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
								TRIP_SHEET_DEFALAT_VALUE, Sheet.getTripFristDriverID(), userDetails.getCompany_id());
					}
					if (Sheet.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
								TRIP_SHEET_DEFALAT_VALUE, Sheet.getTripSecDriverID(), userDetails.getCompany_id());
					}
					if (Sheet.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
						driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
								TRIP_SHEET_DEFALAT_VALUE, Sheet.getTripCleanerID(), userDetails.getCompany_id());
					}
					java.util.Date currentDate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
					tripSheetService.deleteTripSheet(TripSheetID, userDetails.getCompany_id(),userDetails.getId(),toDate);

					// After Remove Attendance Details Code
					DriverAttendanceService.DELETE_DRIVERATTENDANCE_TRIPSHEET_ID(TripSheetID, userDetails.getCompany_id());

					model.put("deleteTripSheet", true);
					return new ModelAndView("redirect:/newTripSheetEntries.in", model);
				}

			} // checking Vehicle Status
			else {
				String Link = "";
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
					+ "\" target=\"_blank\">WO-" + CheckVehicleStatus.getTripSheetID()
					+ "  <i class=\"fa fa-external-link\"></i></a> ";

				}

				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status " + " " + Link + "" + " </span> ,";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);
				return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheetID + "", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				model.put("TripSheet", tripSheetBL.prepareListofDto(tripSheetService.listTripSheetDispatch(userDetails)));
				model.put("TripSheet", true);
			} catch (Exception e1) {
				LOGGER.error("TripSheet Page:", e1);
			}
			LOGGER.error("TripSheet Page:", e);
		}
		model.put("closeStatus", true);
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheetID + "", model);
	}

	/*****************************************************************************
	 * Trip SHeet Account Controller
	 ******************************************************************************/

	/* show main page of Trip */
	@RequestMapping(value = "/newTripSheetAccount/{pageNumber}", method = RequestMethod.GET)
	public String newTripSheetAccount(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails 			userDetails 	= null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			Page<TripSheet> page = tripSheetService.getDeployment_Page_TripSheet(TripSheetStatus.TRIP_STATUS_CLOSED,
					pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("configuration", configuration);
			model.addAttribute("TripSheetCount", page.getTotalElements());

			List<TripSheetDto> pageList = tripSheetBL.prepareListofTripSheet(tripSheetService.listTrip_Page_Status(TripSheetStatus.TRIP_STATUS_CLOSED, pageNumber, userDetails));

			model.addAttribute("TripSheet", pageList);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("trip Page:", e);
			e.printStackTrace();
		}
		return "newTripSheetAccount";
	}

	/* show main page of Trip */
	@RequestMapping(value = "/newtripsheetclosed/{pageNumber}", method = RequestMethod.GET)
	public String newtripsheetclosed(@PathVariable Integer pageNumber, Model model) throws Exception {

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<TripSheet> page = tripSheetService
					.getDeployment_Page_TripSheet(TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED, pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TripSheetCount", page.getTotalElements());
			List<TripSheetDto> pageList = tripSheetBL.prepareListofTripSheet(tripSheetService.listTrip_Page_Status(TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED, pageNumber, userDetails));

			model.addAttribute("TripSheet", pageList);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("trip Page:", e);
			e.printStackTrace();
		}
		return "newTripSheetClosedAccount";
	}

	/*****************************************************************************************************
	 ***************** Vehicle To Trip Sheet Details **********************
	 *****************************************************************************************************/

	// Note: This /VehicleTripDetails Controller is Show Inside Vehicle To
	// TripSheet
	@RequestMapping(value = "/VehicleTripDetails/{vid}/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView VehicleTripDetails(@PathVariable("vid") Integer vid, @PathVariable("pageNumber") Integer pageNumber) {
			
			Map<String, Object> model = new HashMap<String, Object>();
		 
		    long tripSheetCount	         = 0;    
			long totalTripSheetCount     = 0;
		
			try {
				
		        CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		        VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vid));
		        model.put("vehicle", vehicle);
		        
				totalTripSheetCount = tripSheetService.countTotalTripSheet(vid,userDetails.getCompany_id());
				model.put("totalVehicleCount", totalTripSheetCount);
		        
		        // Assuming you have a method to get paginated trip sheets
		        Page<TripSheet> tripSheetPage = tripSheetService.getPaginatedTripSheetsByVehicleId(vid, pageNumber);
		        
		        tripSheetCount	=	 tripSheetPage.getTotalElements();
		        model.put("TripSheet", tripSheetBL.prepareListofTripSheet(tripSheetService.VehicleTOlistTripSheet(vid, pageNumber)));
				
		        // Add pagination attributes
		        model.put("beginIndex", Math.max(1, tripSheetPage.getNumber() - 5));
		        model.put("endIndex", Math.min(tripSheetPage.getNumber() + 5, tripSheetPage.getTotalPages()));
		        model.put("currentIndex", tripSheetPage.getNumber() + 1);
			model.put("tripSheetCount", tripSheetCount);
			model.put("tripSheetCount", totalTripSheetCount);
			model.put("SelectPage", pageNumber);
			model.put("user", userDetails);
			model.put("vid", vid);

			Object[] count = vehicleService
					.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(vid);
			model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(vid, userDetails.getCompany_id()));
			model.put("photo_Count", (Long) count[0]);
			model.put("purchase_Count", (Long) count[1]);
			model.put("reminder_Count", (Long) count[2]);
			model.put("fuelEntrie_Count", (Long) count[3]);
			model.put("serviceEntrie_Count", (Long) count[4]);
			model.put("serviceReminder_Count", (Long) count[5]);
			model.put("tripsheet_Count", (Long) count[6]);
			model.put("workOrder_Count", (Long) count[7]);
			model.put("issues_Count", (Long) count[8]);
			model.put("odometerhis_Count", (Long) count[9]);
			model.put("comment_Count", (Long) count[10]);
			model.put("breakDownCount", ((Long) count[4]+(Long) count[8]));
			model.put("dseCount", (Long) count[12]);

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);

		}

		return new ModelAndView("Show_Vehicle_Trip", model);
	}

	// Note: This /VehicleTripAdd Controller Is Add inside Vehicle TripSheet
	@RequestMapping(value = "/VehicleTripAdd", method = RequestMethod.GET)
	public ModelAndView VehicleTripDetailsAdd(@ModelAttribute("command") Vehicle vehicle_id,
			ServiceReminderDto serviceReminderDto, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vehicle_id.getVid()));
			model.put("vehicle", vehicle);
			// show the driver list in all

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);

		}
		return new ModelAndView("Add_Vehicle_Trip", model);
	}

	// Note: This /addHaltAmount Controller is Add TripSheet Id to Add Driver
	// Halt Page
	@RequestMapping(value = "/addHaltAmount", method = RequestMethod.GET)
	public ModelAndView addHaltAmountt(@RequestParam("ID") final Long TripSheetID, final HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			
			TripSheetDto trip = tripSheetBL
					.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheetID, userDetails));
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);
			model.put("configuration", configuration);

			// get Trip sheet id to get all Expense Details
			model.put("TripSheetHalt", driverHaltService.Find_list_TripSheet_DriverHalt(TripSheetID, userDetails.getCompany_id()));

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName();

			UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(name);

			model.put("haltBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
			model.put("place", Orderingname.getBranch_name());
			model.put("placeId", Orderingname.getBranch_id());
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			model.put("configuration", configuration);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("addDriverHalt", model);
	}

	// Note: This /updateDriverHalt Controller is Update TripSheet Id to Add
	// Driver Halt Amount
	@RequestMapping(value = "/updateDriverHalt", method = RequestMethod.POST) 
	public ModelAndView updateHaltAmountTripsheet(@ModelAttribute("command") DriverHalt DriverHalt,
			@RequestParam("DRIVERID") final String Driver_ID, @RequestParam("TRIPSHEETID") final Long TRIP_ID,
			@RequestParam("voucherDate") final String voucherDate,@RequestParam("HALT_DATE") final String HALT_DATE,@RequestParam("tallyCompanyId") final Long tallyCompanyId,
			final HttpServletRequest request) throws Exception {
		
		Map<String, Object> 			model 						= new HashMap<String, Object>();
		CustomUserDetails 				userDetails 				= null;
		String 							dateRangeFrom 				= "";  
		String							dateRangeTo 				= "";
		String[] 						From_TO_DateRange 			= null;
		Double 							ExpenseTotalPoint			= null;
		String 							expenseRefence 				= "";
		DriverHalt 						tHalt 						= null;
		DriverDto 						driverDto					= null;
		TripSheet 						tsheet						= null;
		TripSheetExpense 				TripExpense 				= null;
		String[] 						DriverID					= null;
		String[] 						vaoucherDateStr				= null;
		java.util.Date 					fromUtilDate				= null;
		java.util.Date 					toUtilDate					= null;
		java.sql.Date 					fromSqlDate					= null;
		java.sql.Date 					toSqlDate					= null;
		Double 							Point						= null;
		java.util.Date 					currentDateUpdate			= null;
		Timestamp 						cuerrentTimeStamp 			= null;
		HashMap<String, Object> 		configuration				= null;
		boolean							driverBataFinalAmount		= false;
		try {	
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			driverBataFinalAmount	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.DRIVER_HALT_BATA_FINAL_AMOUNT, false);
			
			currentDateUpdate 		= new java.util.Date();
			cuerrentTimeStamp 		= new java.sql.Timestamp(currentDateUpdate.getTime());

			if (HALT_DATE != "" && TRIP_ID != null && Driver_ID != null) {
				From_TO_DateRange 	= HALT_DATE.split(" to ");
				dateRangeFrom 		= From_TO_DateRange[0];
				dateRangeTo 		= From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;
				
				DriverID 			= Driver_ID.split(",");
				
				
				
				// check Driver Halt are not
				
				for (String DID : DriverID) {
					ExpenseTotalPoint 	= TRIP_SHEET_DEFALAT_AMOUNT;
					expenseRefence 		= "";
					tHalt 				= tripSheetBL.prepare_TRIPSHEET_Driver_Halt_Details(DriverHalt);

					if (DID != null) {
						driverDto 		= driverBL.GetDriverID(driverService.getDriver_Details_Firstnamr_LastName(Integer.parseInt(DID), userDetails.getCompany_id()));
						tHalt.setDRIVERID(driverDto.getDriver_id());
						expenseRefence 	= "" + driverDto.getDriver_firstname() + "_" + driverDto.getDriver_Lastname() + "";
					}
					
					if (dateRangeFrom != "" && dateRangeTo != "") {
						fromUtilDate 	= dateFormat.parse(dateRangeFrom);
						toUtilDate 		= dateFormat.parse(dateRangeTo);
						
						fromSqlDate 	= new Date(fromUtilDate.getTime());
						toSqlDate 		= new Date(toUtilDate.getTime());
						
						tHalt.setHALT_DATE_FROM(fromSqlDate);
						tHalt.setHALT_DATE_TO(toSqlDate);

						Point 				= tripSheetBL.Driver_Halt_Point(fromSqlDate, toSqlDate);
						ExpenseTotalPoint 	= Point;
						
						tHalt.setHALT_POINT(Point);
						if(driverBataFinalAmount) {
							tHalt.setHALT_AMOUNT(DriverHalt.getHALT_AMOUNT());
						}else {
							tHalt.setHALT_AMOUNT(Point * DriverHalt.getHALT_AMOUNT());
						}
						
					}
					
					tHalt.setTRIPSHEETID(TRIP_ID);
					tHalt.setCOMPANY_ID(userDetails.getCompany_id());
					driverHaltService.register_New_DriverHalt(tHalt);

					// Note : Create TRIPSHEET Expense IN Driver HALT Amount
					tsheet = new TripSheet();
					tsheet.setTripSheetID(TRIP_ID);

					// below Driver trip Halt amount Add expense Halt Bata
					TripExpense = new TripSheetExpense();
					TripExpense.setExpenseId((int) TripSheetFixedExpenseType.EXPENSE_TYPE_HALT_BATA);
					if(driverBataFinalAmount) {
						vaoucherDateStr 		= voucherDate.split("-");
						
						String date = vaoucherDateStr[0];
						String month = vaoucherDateStr[1];
						String year = vaoucherDateStr[2];
						
						String finalDate =""+year+"-"+month+"-"+date+"";
						
						TripExpense.setVoucherDate(DateTimeUtility.getDateFromString(finalDate, DateTimeUtility.YYYY_MM_DD));
						TripExpense.setExpenseAmount(DriverHalt.getHALT_AMOUNT());
					}else {
						TripExpense.setExpenseAmount(ExpenseTotalPoint * DriverHalt.getHALT_AMOUNT());
					}
					TripExpense.setExpensePlaceId(tHalt.getHALT_PLACE_ID());
					TripExpense.setExpenseRefence(expenseRefence);
					TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
					TripExpense.setCreatedById(userDetails.getId());
					TripExpense.setCompanyId(userDetails.getCompany_id());
					TripExpense.setTripsheet(tsheet);
					TripExpense.setDHID(tHalt.getDHID());
					TripExpense.setCreated(cuerrentTimeStamp);
					TripExpense.setTallyCompanyId(tallyCompanyId);
					TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());
					
					tripSheetService.addTripSheetExpense(TripExpense);

					model.put("success", true);

				} // close For loop in driver

				// Note : This Update TripSheet Total Expense Updating The Value
				// Of
				// Total Expense amount to TripSheet
				tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(TRIP_ID, userDetails.getCompany_id());

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/addHaltAmount.in?ID=" + TRIP_ID + "", model);
	}

	// Note: This /removeExpense Controller is TripSheet ID to removeAdvance and
	// also Amount
	@RequestMapping(value = "/removeDriverHalt", method = RequestMethod.GET)
	public ModelAndView removeDriverHalt(@RequestParam("TSID") final Long TripSheetID,
			@RequestParam("DHID") final Long DHID, final HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (DHID != null && TripSheetID != null) {
				// Note: Delete Halt TripSheet Bata
				driverHaltService.delete_DriverHalt(DHID, userDetails.getCompany_id());

				// Note : Delete TripSheet Halt Expense Entries
				tripSheetService.DELETE_TRIPSHEETEXPENSE_DRIVERHALT_ID(DHID, userDetails.getCompany_id());

				// Note : This Update TripSheet Total Expense Updating The Value
				// Of
				// Total Expense amount to TripSheet
				tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(TripSheetID, userDetails.getCompany_id());

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		model.put("delete", true);
		return new ModelAndView("redirect:/addHaltAmount.in?ID=" + TripSheetID + "", model);
	}

	// Note: This /addTSFuel Controller is Add TripSheet Id to Add Fuel Page
	@RequestMapping(value = "/addTSFuel", method = RequestMethod.GET)
	public ModelAndView addTSFuel(@RequestParam("ID") final Long TripSheetID, final HttpServletRequest result) {
		Map<String, Object> 			model 				= new HashMap<String, Object>();
		CustomUserDetails 				userDetails			= null;
		HashMap<String, Object>			gpsConfiguration	= null;
		HashMap<String, Object>			configuration		= null;
		TripSheetDto 					trip 				= null;
		TripSheetDto 					tripBL 				= null;
		List<FuelDto> 					ShowAmount			= null;
		List<FuelDto> 					ShowAmountBL		= null;
		Authentication 					auth				= null;
		UserProfileDto 					Orderingname		= null;
		
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.get(FuelConfigurationConstants.ALLOW_WS_FUEL_ENTRY)) {
				return new ModelAndView("redirect:/addFuelEntries.in?tripSheetID=" + TripSheetID);
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			model.put("configuration",configuration );
			model.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));

			trip		= tripSheetService.getTripSheetDetails(TripSheetID, userDetails);
			tripBL 		= tripSheetBL.GetTripSheetDetails(trip);
			// get the Trip sheet ID to Details
			model.put("TripSheet", tripBL);

			ShowAmount 	= fuelService.Get_TripSheet_IN_FuelTable_List(TripSheetID, userDetails.getCompany_id(), userDetails.getId());
			ShowAmountBL= fuelBL.prepareListofFuel(ShowAmount);
			model.put("fuel", ShowAmountBL );
			model.put("fTDiesel", fuelBL.prepareTotal_Tripsheet_fuel_details(ShowAmount));
			
			auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName();

			Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(name);

			model.put("PAIDBY", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
			model.put("place", Orderingname.getBranch_name());
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL, 
					companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
							PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL, false));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("add_TripSheet_FuelAdd", model);
	}

	// Note: This /saveTripComment Controller is Add TripSheet Id add Comment
	/** This controller request in TripSheet Comment Show Page issues list */
	@RequestMapping(value = "/saveTripComment", method = RequestMethod.POST)
	public ModelAndView saveTripComment(@RequestParam("TRIPSHEETID") final Long tripSheet_ID,
			final TripSheetComment tripComment, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();	
		HashMap<String, Object> 	configuration	= null;
		try {
			/** who Create this Issues get email id to user profile details */
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripSheetComment saveComment = new TripSheetComment();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			saveComment.setTRIPSHEETID(tripSheet_ID);
			saveComment.setTRIP_COMMENT(tripComment.getTRIP_COMMENT());
			model.put("configuration", configuration);
			try {
				UserProfileDto Orderingname = userProfileService
						.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
				saveComment.setCREATEDBY(Orderingname.getFirstName());
				saveComment.setCREATED_PLACE(Orderingname.getBranch_name());
			} catch (Exception e) {
			}

			saveComment.setCREATED_EMAIL(userDetails.getEmail());
			saveComment.setCOMPANY_ID(userDetails.getCompany_id());
			saveComment.setMarkForDelete(false);
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			/** Set Created Current Date */
			saveComment.setCREATED_DATE(toDate);

			tripCommentService.add_TripSheet_Comment(saveComment);

			tripSheetService.update_Total_TripSheet_comment_Add(tripSheet_ID, userDetails.getCompany_id());

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + tripSheet_ID + "&danger=true");
		}
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + tripSheet_ID + "&saveComment=true");

	}

	// Note: This /deleteTripComment controller request in Trip Comment Show
	// Page issues list */
	@RequestMapping(value = "/deleteTripComment", method = RequestMethod.GET)
	public ModelAndView deleteTripComment(@RequestParam("TID") final Long tripSheet_ID,
			@RequestParam("CID") final Long Comment_ID, final TripSheetComment tripComment,
			final HttpServletRequest request) {
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			tripCommentService.deleteTripSheetComment(Comment_ID, userDetails.getCompany_id());

			tripSheetService.update_Total_TripSheet_comment_SubTrack(tripSheet_ID, userDetails.getCompany_id());

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Issues Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + tripSheet_ID + "&danger=true");
		}
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + tripSheet_ID + "&deleteComment=true");
	}

	/*****************************************************************************
	 * Testing Loading select json new waY
	 ******************************************************************************/

	@RequestMapping(value = "/getVehicleListTEST", method = RequestMethod.POST)
	public void getVehicleList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		List<Vehicle> vehicle = vehicleService.SearchOnlyVehicleNAME(term);
		if (vehicle != null && !vehicle.isEmpty()) {
			for (Vehicle add : vehicle) {
				VehicleDto wadd = new VehicleDto();
				wadd.setVid(add.getVid());
				wadd.setVehicle_registration(add.getVehicle_registration());
				wadd.setvStatusId(add.getvStatusId());
				wadd.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(add.getvStatusId()));
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/getDriver1List", method = RequestMethod.POST)
	public void getDriver1List(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> driverName = driverService.getDriverDetailsNotInSuspend(term,userDetails);
		if (driverName != null && !driverName.isEmpty()) {
			for (Driver add : driverName) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_mobnumber(add.getDriver_mobnumber());
				wadd.setDriver_fathername(add.getDriver_fathername());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getDriver2List", method = RequestMethod.POST)
	public void getDriver2List(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> driverDtos = driverService.SearchOnlyDriverNAME(term);
		if (driverDtos != null && !driverDtos.isEmpty()) {
			for (Driver add : driverDtos) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_mobnumber(add.getDriver_mobnumber());
				wadd.setDriver_fathername(add.getDriver_fathername());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getDriverCleanerList", method = RequestMethod.POST)
	public void getDriverCleanerList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> DriverType = driverService.SearchOnlyCleanerNAME(term);
		if (DriverType != null && !DriverType.isEmpty()) {
			for (Driver add : DriverType) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_fathername(add.getDriver_fathername());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getTripRouteList", method = RequestMethod.POST)
	public void getTripRouteList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<TripRoute> TripRouteList = new ArrayList<TripRoute>();
		List<TripRoute> TripRoute = TripRouteService.SearchOnlyRoute_MAIN_ROUTE_NAME(term, userDetails);
		if (TripRoute != null && !TripRoute.isEmpty()) {
			for (TripRoute Route : TripRoute) {

				TripRoute Dto = new TripRoute();
				Dto.setRouteID(Route.getRouteID());
				Dto.setRouteName(Route.getRouteName());
				Dto.setRouteNo(Route.getRouteNo());

				TripRouteList.add(Dto);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(TripRouteList));
	}

	@RequestMapping(value = "/getVehicleTripEditList", method = RequestMethod.GET)
	public void getVehicleTripEditList(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		List<Vehicle> vehicle = vehicleService.findAllVehiclelist();
		if (vehicle != null && !vehicle.isEmpty()) {
			for (Vehicle add : vehicle) {
				VehicleDto wadd = new VehicleDto();
				wadd.setVid(add.getVid());
				wadd.setVehicle_registration(add.getVehicle_registration());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(addresses));
	}

	/*@RequestMapping(value = "/getDriverTripEditList", method = RequestMethod.GET)
	public void getDriverEditList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> driverType = driverService.fildAllDriverList();
		if (driverType != null && !driverType.isEmpty()) {
			for (Driver add : driverType) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_firstname(add.getDriver_firstname());

				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getDriverCleanerEditList", method = RequestMethod.GET)
	public void getDriverCleanerEditList(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> driverType = driverService.fildAllCleanerList();
		if (driverType != null && !driverType.isEmpty()) {
			for (Driver add : driverType) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_firstname(add.getDriver_firstname());

				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}
	 */
	@RequestMapping(value = "/getTripRouteEditList", method = RequestMethod.GET)
	public void getTripRouteEditList(Map<String, Object> map, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<TripRoute> TripRouteList = new ArrayList<TripRoute>();
		List<TripRoute> TripRoute = TripRouteService.listTripRoute(userDetails);
		if (TripRoute != null && !TripRoute.isEmpty()) {
			for (TripRoute Route : TripRoute) {

				TripRoute Dto = new TripRoute();
				Dto.setRouteID(Route.getRouteID());
				Dto.setRouteName(Route.getRouteName());
				Dto.setRouteNo(Route.getRouteNo());

				TripRouteList.add(Dto);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(TripRouteList));
	}

	@RequestMapping(value = "/GetTSVehicleValidate", method = RequestMethod.GET)
	public void GetTSVehicleValidate(Map<String, Object> map, @RequestParam("VID") final Integer VehicleID,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TripSheetDto addresses = new TripSheetDto();
		List<TripSheetDto> TRIP = tripSheetService.Get_Validate_TripSheet_Vehicle_DriverAcount(VehicleID,
				userDetails.getCompany_id());
		if (TRIP != null && !TRIP.isEmpty()) {
			String TID = "";
			for (TripSheetDto add : TRIP) {
				TID += "<a href=\"addCloseTripsheet.in?tripSheetID=" + add.getTripSheetID() + "\" target=\"_blank\">TS-"
						+ add.getTripSheetNumber() + "  <i class=\"fa fa-external-link\"></i></a> ,";
			}
			addresses.setVehicle_registration(TID);
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(addresses));
	}

	/*****************************************************************************
	 * Testing Loading datatable using Gson
	 ******************************************************************************/

	@RequestMapping(value = "/tripExpenseList", method = RequestMethod.GET)
	public void tripExpenseList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<TripExpense> addresses = new ArrayList<TripExpense>();
		List<TripExpense> expense = TripExpenseService.listOnlyExpenseName();
		if (expense != null && !expense.isEmpty()) {
			for (TripExpense add : expense) {
				TripExpense wadd = new TripExpense();

				wadd.setExpenseName(add.getExpenseName());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/getTripVehicleOdoMerete", method = RequestMethod.GET)
	public void getFuelVehicleOdoMerete(@RequestParam(value = "FuelvehicleID", required = true) Integer vehicleID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		ValueObject					valueObject			= null;
		HashMap<String, Object> 	gpsConfiguration	= null;
		ValueObject					gpsobject			= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
		
			VehicleDto oddmeter = vehicleService.getVehicle_Select_TripSheet_Details(vehicleID);
			VehicleDto wadd = new VehicleDto();
			
			if (oddmeter != null) {
				if((boolean) configuration.getOrDefault("tripOpenCloseFuelRequired", false)) {
					TripSheetDto	sheetDto	= tripSheetService.getLastTripSheetDetails(vehicleID, DateTimeUtility.getCurrentTimeStamp()+"");
					if (sheetDto != null) {
						wadd.setTripStartDiesel(sheetDto.getTripStartDiesel());
						wadd.setTripEndDiesel(sheetDto.getTripEndDiesel());
					}
				}
				gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
				
				if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
					ValueObject	gps	= new ValueObject();
					gps.put("companyId", userDetails.getCompany_id());
					gps.put("vehicleId", vehicleID);
					gpsobject	=	vehicleGPSDetailsService.getVehicleGPSData(gps);
					if(gpsobject !=null && gpsobject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME)) {
						wadd.setGpsWorking(true);
						if((int)gpsConfiguration.get(VehicleGPSConfiguratinConstant.GPS_FLAVOR) == 2) {
							wadd.setGpsOdameter(gpsobject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME));
						}else if((int)gpsConfiguration.get(VehicleGPSConfiguratinConstant.GPS_FLAVOR) == 3) {
							wadd.setGpsOdameter(gpsobject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME));
						}
						
						wadd.setVehicle_Location(gpsobject.getString(GPSConstant.GPS_LOCATION_NAME));
						
					}else {
						wadd.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
						wadd.setGpsWorking(false);
					}
					if(gpsobject !=null) {
						wadd.setOdometerReading(gpsobject.getBoolean("isOdometerReading"));
					}
				}else {
					wadd.setGpsWorking(true);
				}
				wadd.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
				
				if((boolean) configuration.get(TripSheetConfigurationConstant.VALIDATE_SERVICE_REMINDER)) {
					valueObject	= ServiceReminderService.getReminderStatusForOverdue(vehicleID);
				}

			
				wadd.setVehicle_Fuel(
						methodRemoveLastComma(VehicleFuelType.getVehicleFuelTypeName(oddmeter.getVehicleFuelId())));
				wadd.setVehicleFuelId(oddmeter.getVehicleFuelId());
				wadd.setVehicle_Group(oddmeter.getVehicle_Group());
				wadd.setVehicle_RouteName(oddmeter.getVehicle_RouteName());
				wadd.setVehicle_ExpectedOdameter(oddmeter.getVehicle_ExpectedOdameter());
				wadd.setVehicleGroupId(oddmeter.getVehicleGroupId());
				wadd.setRouteID(oddmeter.getRouteID());
				if(valueObject != null) {
					wadd.setServiceOverDue(true);
					wadd.setServiceNumbers(valueObject.getString("overDueReminder"));
				}
				wadd.setvStatusId(oddmeter.getvStatusId());
				wadd.setVehicle_Status(oddmeter.getVehicle_Status());
				wadd.setDriverFirstName(oddmeter.getDriverFirstName());
				wadd.setDriverLastName(oddmeter.getDriverLastName());
				wadd.setDriverEmpName(oddmeter.getDriverEmpName());
				wadd.setPartlocation_id(oddmeter.getPartlocation_id());
			}
			
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(wadd));
		} catch (Exception e) {
			throw e;
		}finally {
			gpsConfiguration	= null;
			gpsobject			= null;
		}

	}

	public String methodRemoveLastComma(String str) {
		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	@RequestMapping(value = "/tripIncomeList", method = RequestMethod.GET)
	public void tripIncomeList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<TripIncome> addresses = new ArrayList<TripIncome>();
		List<TripIncome> income = TripIncomeService.listOnlyIncomeName(userDetails.getCompany_id());
		if (income != null && !income.isEmpty()) {
			for (TripIncome add : income) {
				TripIncome wadd = new TripIncome();

				wadd.setIncomeName(add.getIncomeName());
				wadd.setIncomeID(add.getIncomeID());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(addresses));
	}

	/*****************************************************************************
	 * Testing Loading drop down using Gson
	 ******************************************************************************/

	@RequestMapping(value = "/getDispatchList", method = RequestMethod.GET)
	public void getDispatchListDataTable(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<TripSheetDto> DispatchList = new ArrayList<TripSheetDto>();
		List<TripSheetDto> tripsheet = tripSheetService.listTripSheetDispatch(userDetails);
		if (tripsheet != null && !tripsheet.isEmpty()) {
			for (TripSheetDto Sheet : tripsheet) {
				TripSheetDto Dto = new TripSheetDto();

				Dto.setTripSheetID(Sheet.getTripSheetID());
				Dto.setVehicle_registration(Sheet.getVehicle_registration());
				Dto.setVehicle_Group(Sheet.getVehicle_Group());
				Dto.setRouteName(Sheet.getRouteName());
				if (Sheet.getTripOpenDateOn() != null)
					Dto.setTripOpenDate(dateFormat.format(Sheet.getTripOpenDateOn()));
				Dto.setTripBookref(Sheet.getTripBookref());

				DispatchList.add(Dto);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(DispatchList));
	}

	@RequestMapping(value = "/getManageList", method = RequestMethod.GET)
	public void getManageListDataTable(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<TripSheetDto> ManageList = new ArrayList<TripSheetDto>();
		List<TripSheetDto> Manage = tripSheetService.listTripSheetManage(userDetails);
		if (Manage != null && !Manage.isEmpty()) {
			for (TripSheetDto Sheet : Manage) {
				TripSheetDto Dto = new TripSheetDto();

				Dto.setTripSheetID(Sheet.getTripSheetID());
				Dto.setVehicle_registration(Sheet.getVehicle_registration());
				Dto.setVehicle_Group(Sheet.getVehicle_Group());
				Dto.setRouteName(Sheet.getRouteName());

				if (Sheet.getTripOpenDateOn() != null)
					Dto.setTripOpenDate(dateFormat.format(Sheet.getTripOpenDateOn()));

				Dto.setTripBookref(Sheet.getTripBookref());

				ManageList.add(Dto);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(ManageList));
	}

	@RequestMapping(value = "/getCloseList", method = RequestMethod.GET)
	public void getCloseListDataTable(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<TripSheetDto> ManageList = new ArrayList<TripSheetDto>();
		List<TripSheetDto> close = tripSheetService.listTripSheetManage(userDetails);
		if (close != null && !close.isEmpty()) {
			for (TripSheetDto Sheet : close) {
				TripSheetDto Dto = new TripSheetDto();

				Dto.setTripSheetID(Sheet.getTripSheetID());
				Dto.setVehicle_registration(Sheet.getVehicle_registration());
				Dto.setVehicle_Group(Sheet.getVehicle_Group());
				Dto.setRouteName(Sheet.getRouteName());
				Dto.setTripOpenDate(dateFormat.format(Sheet.getTripOpenDate()));
				Dto.setTripBookref(Sheet.getTripBookref());
				Dto.setTripStutes(Sheet.getTripStutes());
				Dto.setTripTotalAdvance(Sheet.getTripTotalAdvance());
				Dto.setTripTotalexpense(Sheet.getTripTotalexpense());
				Dto.setTripTotalincome(Sheet.getTripTotalincome());

				ManageList.add(Dto);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(ManageList));
	}

	@RequestMapping(value = "/getTripSheetAccountList", method = RequestMethod.GET)
	public void getTripSheetAccountList(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<TripSheetDto> ManageList = new ArrayList<TripSheetDto>();
		List<TripSheetDto> Manage = tripSheetService.listTripSheetClose(userDetails);
		if (Manage != null && !Manage.isEmpty()) {
			for (TripSheetDto Sheet : Manage) {
				TripSheetDto Dto = new TripSheetDto();

				Dto.setTripSheetID(Sheet.getTripSheetID());
				Dto.setVehicle_registration(Sheet.getVehicle_registration());
				Dto.setVehicle_Group(Sheet.getVehicle_Group());
				Dto.setRouteName(Sheet.getRouteName());
				if (Sheet.getTripOpenDateOn() != null) {
					Dto.setTripOpenDate(dateFormat.format(Sheet.getTripOpenDateOn()));
				}
				Dto.setTripBookref(Sheet.getTripBookref());
				Dto.setTripStutes(Sheet.getTripStutes());
				Dto.setTripTotalexpense(Sheet.getTripTotalexpense());
				Dto.setTripTotalincome(Sheet.getTripTotalincome());
				Dto.setTripStutes(Sheet.getTripStutes());

				ManageList.add(Dto);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(ManageList));
	}

	/*****************************************************************************
	 * Testing Loading datatable using Gson Close
	 ******************************************************************************/

	/**
	 * @param tripFristDriverID
	 * @param tripFristDriverName
	 * @param close_date
	 * @param trip
	 */
	private void Add_TripSheet_TO_driver_Attendance(int trip_DRIVER_ID, String trip_DRIVER_NAME, Date Close_date,
			Double FristDriverRoutePoint, TripSheetDto trip, CustomUserDetails userDetails) {

		// Monday, February 29 is a leap day in 2016 (otherwise,
		// February only has 28 days)
		// LocalDate start = LocalDate.parse("2016-06-01"), end =
		// LocalDate.parse("2016-06-01");
		
		LocalDate start = LocalDate.parse(trip.getTripOpenDate()), end = LocalDate.parse(driverAttendanceFormat.format(Close_date));
		
		LocalDate next = start.minusDays(1);
		while ((next = next.plusDays(1)).isBefore(end.plusDays(1))) {
			
			try {
				if (next != null) {

					DriverAttendance attendance = driverBL.Driver_Attendance_to_tripSheet(trip_DRIVER_ID, trip_DRIVER_NAME,
							trip, userDetails);
					/**
					 * Reported_Date converted String to date Format
					 */
					java.util.Date attendanceDate = driverAttendanceFormat.parse("" + next);
					java.sql.Date ATTENDANCE_DATE = new Date(attendanceDate.getTime());
					attendance.setATTENDANCE_DATE(ATTENDANCE_DATE);

					if (ATTENDANCE_DATE.equals(Close_date)) {

						attendance.setPOINT_STATUS_ID(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT);
						// attendance.setPOINT_STATUS(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT_NAME);
						attendance.setPOINT_TYPE_ID(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIP);
						// attendance.setPOINT_TYPE(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_TRIP_NAME);
						attendance.setDRIVER_POINT(FristDriverRoutePoint);
					} else {
						attendance.setPOINT_STATUS_ID(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_NO);
						// attendance.setPOINT_STATUS(DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_NO_NAME);
						// attendance.setPOINT_TYPE(null);
						attendance.setDRIVER_POINT(TRIP_SHEET_DEFALAT_AMOUNT);
					}
					DriverAttendanceService.addDriverAttendance(attendance);
					/* } */

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// this Update Current Odometer in Vehicle Getting Trip Daily Sheet
	public void Update_Current_OdometerinVehicle_getTripDailySheetTo(TripSheet Sheet) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<ServiceReminderDto>		serviceList					= null;
		Integer currentODDMETER;
		try {
			currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(Sheet.getVid());

			// update the Current Odometer vehicle Databases tables
			try {
				if (Sheet.getVid() != TRIP_SHEET_DEFALAT_ZERO) {
					if (currentODDMETER < Sheet.getTripOpeningKM()) {
						
						vehicleService.updateCurrentOdoMeter(Sheet.getVid(), Sheet.getTripOpeningKM(), userDetails.getCompany_id());
						// update current Odometer update Service
						// Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(Sheet.getVid(),
								Sheet.getTripOpeningKM(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(Sheet.getVid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									//emailAlertQueueService.sendEmailServiceOdometer(list);
									//smsAlertQueueService.sendSmsServiceOdometer(list);
								}
							}
						}

						VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL.prepareOdometerGetHistoryToTripsheet(Sheet);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}
				}
			} catch (Exception e) {
				LOGGER.error("TripDaily Page:", e);
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void Update_Current_OdometerinVehicle_getTripDailySheetTo_DTO(TripSheetDto Sheet, Integer ClosingKM) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<ServiceReminderDto>		serviceList					= null;
		Integer currentODDMETER;
		try {
			currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(Sheet.getVid());

			// update the Current Odometer vehicle Databases tables
			try {
				if (Sheet.getVid() != TRIP_SHEET_DEFALAT_ZERO) {
					if (currentODDMETER < ClosingKM) {
						
						vehicleService.updateCurrentOdoMeter(Sheet.getVid(), ClosingKM, userDetails.getCompany_id());
						// update current Odometer update Service
						// Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(Sheet.getVid(),
								ClosingKM, userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(Sheet.getVid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
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
			} catch (Exception e) {
				LOGGER.error("TripDaily Page:", e);
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void Update_Current_OdometerinVehicle_GpsUsage(TripSheetDto Sheet, Double ClosingKM) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<ServiceReminderDto>		serviceList					= null;
		Integer currentODDMETER;
		try {
			currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(Sheet.getVid());

			// update the Current Odometer vehicle Databases tables
			try {
				if (Sheet.getVid() != TRIP_SHEET_DEFALAT_ZERO) {
					if (ClosingKM != null && ClosingKM > 0) {
						
						vehicleService.updateCurrentOdoMeter(Sheet.getVid(), (ClosingKM.intValue() + currentODDMETER), userDetails.getCompany_id());
						// update current Odometer update Service
						// Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(Sheet.getVid(),
								(ClosingKM.intValue() + currentODDMETER), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(Sheet.getVid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
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
			} catch (Exception e) {
				LOGGER.error("TripDaily Page:", e);
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// save the TripSheet Model
	public TripSheet prepareModel(TripSheetDto sheetDto, HttpServletRequest request) throws Exception {
		TripSheet 			tripSheet 			= null;
		String[] 			From_TO_Array 		= null;
		java.util.Date 		fromDate 			= null;
		java.util.Date 		toDate 				= null;
		java.sql.Date 		tripOpenDate 		= null;
		java.sql.Date 		closeTripDate 		= null;
		try {
			
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			tripSheet	= new TripSheet();

			tripSheet.setTripSheetID(sheetDto.getTripSheetID());
			tripSheet.setVid(sheetDto.getVid());
			tripSheet.setTripOpeningKM(sheetDto.getTripOpeningKM());
			
			tripSheet.setLoadTypeId(sheetDto.getLoadTypeId());
			From_TO_Array 	= sheetDto.getTripOpenDate().split("  to  ");

			fromDate 		= dateFormat.parse(From_TO_Array[0]);
			toDate 			= dateFormat.parse(From_TO_Array[1]);

			tripOpenDate 	= new Date(fromDate.getTime());
			closeTripDate 	= new Date(toDate.getTime());

			tripSheet.setTripOpenDate(tripOpenDate);
			tripSheet.setClosetripDate(closeTripDate);
			tripSheet.setSubRoute(sheetDto.getSubRouteName());
			

			if (sheetDto.getRouteID() != TRIP_SHEET_DEFALAT_ZERO && sheetDto.getIsNewRoute() == TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setRouteID(sheetDto.getRouteID());
			} else {
				tripSheet.setRouteID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setRouteName(sheetDto.getRouteName());
			}

			if (sheetDto.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripFristDriverID(sheetDto.getTripFristDriverID());
				tripSheet.setTripFristDriverBata(TRIP_SHEET_DEFALAT_AMOUNT);
			} else {
				tripSheet.setTripFristDriverID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setTripFristDriverBata(TRIP_SHEET_DEFALAT_AMOUNT);
			}

			if (sheetDto.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripSecDriverID(sheetDto.getTripSecDriverID());
				tripSheet.setTripSecDriverBata(TRIP_SHEET_DEFALAT_AMOUNT);
			} else {
				tripSheet.setTripSecDriverID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setTripSecDriverBata(TRIP_SHEET_DEFALAT_AMOUNT);
			}
			if (sheetDto.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripCleanerID(sheetDto.getTripCleanerID());
				tripSheet.setTripCleanerBata(TRIP_SHEET_DEFALAT_AMOUNT);
			} else {
				tripSheet.setTripCleanerID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setTripCleanerBata(TRIP_SHEET_DEFALAT_AMOUNT);
			}
			tripSheet.setTripBookref(sheetDto.getTripBookref());
			// new Tripsheet save comment is Zero
			tripSheet.setTripCommentTotal(TRIP_SHEET_DEFALAT_ZERO);

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp createdDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			tripSheet.setCreated(createdDate);
			tripSheet.setLastupdated(createdDate);
			tripSheet.setIpAddress(Utility.getClientIpAddr(request));
			tripSheet.setTripGpsOpeningKM(sheetDto.getTripGpsOpeningKM());
			
			if(sheetDto.getTripStutesId() == TripSheetStatus.TRIP_STATUS_DISPATCHED) {
				tripSheet.setGpsOpeningLocation(sheetDto.getGpsOpeningLocation());
			}
			
			tripSheet.setLoadTypeId(sheetDto.getLoadTypeId());
			
			
			if (sheetDto.getDispatchedByTime() != null && !sheetDto.getDispatchedByTime().trim().equalsIgnoreCase("")) {
				String start_time = "00:00";
				if(sheetDto.getDispatchTime() != null && sheetDto.getDispatchTime() != "") {
					start_time	= sheetDto.getDispatchTime();
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(sheetDto.getDispatchedByTime(), start_time);
				java.sql.Date Reported_Date = new java.sql.Date(date.getTime());
				tripSheet.setDispatchedByTime(Reported_Date);
			}
			
			if(sheetDto.getNoOfPOD() != null) {
				tripSheet.setNoOfPOD(sheetDto.getNoOfPOD());
			} else {
				tripSheet.setNoOfPOD(0);
			}
			 
			tripSheet.setReceivedPOD(0);
			
			if (tripSheet.getRouteID() != TRIP_SHEET_DEFALAT_ZERO) {
				
				TripRoute routePoint = TripRouteService.getTriproutePoint(tripSheet.getRouteID(), userDetails.getCompany_id());
				
				if (sheetDto.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
					tripSheet.setTripFristDriverRoutePoint(routePoint.getRoutrAttendance());
				} else {
					tripSheet.setTripFristDriverRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
				}
				
				if (sheetDto.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
					tripSheet.setTripSecDriverRoutePoint(routePoint.getRoutrAttendance());
				} else {
					tripSheet.setTripSecDriverRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
				}
				
				if (sheetDto.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
					tripSheet.setTripCleanerRoutePoint(routePoint.getRoutrAttendance());
				} else {
					tripSheet.setTripCleanerRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
				}
			}
			tripSheet.setTripStartDiesel(sheetDto.getTripStartDiesel());
			return tripSheet;
		} catch (Exception e) {
			throw e;
		}
	}

	// prepareUpdate the TripSheet Model
	public TripSheet prepareUpdate(TripSheetDto tripSheetDto) throws Exception {
		TripSheet 			tripSheet 			= null;
		String[] 			From_TO_Array 		= null;
		java.util.Date 		fromDate 			= null;
		java.util.Date 		toDate 				= null;
		java.sql.Date 		tripOpenDate 		= null;
		java.sql.Date 		closeTripDate 		= null;
		try {
			
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			tripSheet = new TripSheet();

			tripSheet.setTripSheetID(tripSheetDto.getTripSheetID());
			tripSheet.setVid(tripSheetDto.getVid());
			tripSheet.setTripSheetNumber(tripSheetDto.getTripSheetNumber());
			tripSheet.setTripOpeningKM(tripSheetDto.getTripOpeningKM());
			tripSheet.setTripGpsOpeningKM(tripSheetDto.getTripGpsOpeningKM());

			From_TO_Array 	= tripSheetDto.getTripOpenDate().split("  to  ");

			fromDate 		= dateFormat.parse(From_TO_Array[0]);
			toDate 			= dateFormat.parse(From_TO_Array[1]);

			tripOpenDate 	= new Date(fromDate.getTime());
			closeTripDate 	= new Date(toDate.getTime());

			tripSheet.setTripOpenDate(tripOpenDate);
			tripSheet.setClosetripDate(closeTripDate);

			if (tripSheetDto.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripFristDriverID(tripSheetDto.getTripFristDriverID());
			}

			if (tripSheetDto.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripSecDriverID(tripSheetDto.getTripSecDriverID());
			}

			if (tripSheetDto.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setTripCleanerID(tripSheetDto.getTripCleanerID());
			}

			if (tripSheetDto.getRouteID() != TRIP_SHEET_DEFALAT_ZERO && tripSheetDto.getIsNewRoute() == TRIP_SHEET_DEFALAT_ZERO) {
				tripSheet.setRouteID(tripSheetDto.getRouteID());
			} else {
				tripSheet.setRouteID(TRIP_SHEET_DEFALAT_ZERO);
				tripSheet.setRouteName(tripSheetDto.getRouteName());
			}

			tripSheet.setSubRoute(tripSheetDto.getSubRouteName());
			tripSheet.setTripBookref(tripSheetDto.getTripBookref());
			tripSheet.setTripCommentTotal(TRIP_SHEET_DEFALAT_ZERO);
			tripSheet.setTripTotalAdvance(tripSheetDto.getTripTotalAdvance());
			tripSheet.setTripTotalexpense(tripSheetDto.getTripTotalexpense());
			tripSheet.setTripTotalincome(tripSheetDto.getTripTotalincome());
			tripSheet.setTripStutesId(tripSheetDto.getTripStutesId());
			tripSheet.setLoadTypeId(tripSheetDto.getLoadTypeId());
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp createdDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			tripSheet.setLastupdated(createdDate);
			tripSheet.setMarkForDelete(false);
			tripSheet.setIpAddress(tripSheetDto.getIpAddress());
			
			if(tripSheetDto.getNoOfPOD() != null) {
				tripSheet.setNoOfPOD(tripSheetDto.getNoOfPOD());
			} else {
				tripSheet.setNoOfPOD(0);
			}
			 
			tripSheet.setReceivedPOD(0);
			
			if (tripSheetDto.getRouteID() != TRIP_SHEET_DEFALAT_ZERO) {
				
				TripRoute routePoint = TripRouteService.getTriproutePoint(tripSheetDto.getRouteID(), userDetails.getCompany_id());
				
				if (tripSheetDto.getTripFristDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
					tripSheet.setTripFristDriverRoutePoint(routePoint.getRoutrAttendance());
				} else {
					tripSheet.setTripFristDriverRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
				}
				
				if (tripSheetDto.getTripSecDriverID() != TRIP_SHEET_DEFALAT_ZERO) {
					tripSheet.setTripSecDriverRoutePoint(routePoint.getRoutrAttendance());
				} else {
					tripSheet.setTripSecDriverRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
				}
				
				if (tripSheetDto.getTripCleanerID() != TRIP_SHEET_DEFALAT_ZERO) {
					tripSheet.setTripCleanerRoutePoint(routePoint.getRoutrAttendance());
				} else {
					tripSheet.setTripCleanerRoutePoint(TRIP_SHEET_DEFALAT_AMOUNT);
				}
			}
			tripSheet.setTripStartDiesel(tripSheetDto.getTripStartDiesel());
			tripSheet.setTripEndDiesel(tripSheetDto.getTripEndDiesel());

			return tripSheet;
		} catch (Exception e) {
			throw e;
		}
	}

	// prepareTrip Sheet History Model
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

			return tripSheetHistory;
		} catch (Exception e) {
			throw e;
		}
	}

	
	public void Update_Driver_Status_IN_TripSheetEdit_ACTIVE_TRIPROUTE(Integer new_DriverSelect_ID,
			Integer Old_Driverselect_ID, Long TripSheet_ID, int compId) throws Exception {
		// Note: Old Select Frist Driver Detail TRIPROUTE TO ACTIVE

		if (new_DriverSelect_ID.equals(Old_Driverselect_ID)) {
			// here no update because both in same driver so already driver in
			// TRIPROUTE

		} else {
			// Not equal Update Old Driver in Active
			// Removed Driver in Active
			if (Old_Driverselect_ID != TRIP_SHEET_DEFALAT_ZERO) {
				driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_ACTIVE,
						TRIP_SHEET_DEFALAT_VALUE, Old_Driverselect_ID, compId);
			}
			// Here Select New Driver in TripRoute
			// Select New Driver in TripRoute
			if (new_DriverSelect_ID != TRIP_SHEET_DEFALAT_ZERO) {
				driverService.Update_Driver_Status_TripSheetID(DriverStatus.DRIVER_STATUS_TRIPROUTE, TripSheet_ID,
						new_DriverSelect_ID, compId);
			}

		}
	}

	// Update Route Fixed expense Removed old Route Change Add New Route fixed
	// expense in Tripsheet
	public void Update_Route_fixed_expense_Amount_Old_Route(Integer New_RouteID, Integer Old_RouteID, TripSheet Sheet)
			throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// Check Route Change are not
			if (New_RouteID.equals(Old_RouteID)) {
				// Old Route ID and New TripSheet Edit Route ID same No Change
			} else {

				// Check new Route in fixed Route are New enter Route
				if (New_RouteID != TRIP_SHEET_DEFALAT_ZERO) {

					// Delete all Old Expense in TripSheet Details To Remove
					tripSheetService.Delete_TSID_to_TripSheetExpence(Sheet.getTripSheetID(),
							userDetails.getCompany_id());

					// not equal to zreo get all fixed expense

					List<TripRoutefixedExpense> RoutefixedExpense = TripRouteService
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
	
	
	
	public void Update_Route_fixed_income_Amount_Old_Route(Integer New_RouteID, Integer Old_RouteID, TripSheet Sheet)
			throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// Check Route Change are not
			if (New_RouteID.equals(Old_RouteID)) {
				// Old Route ID and New TripSheet Edit Route ID same No Change
			} else {

				// Check new Route in fixed Route are New enter Route
				if (New_RouteID != TRIP_SHEET_DEFALAT_ZERO) {

					// Delete all Old Expense in TripSheet Details To Remove
					tripSheetService.Delete_TSTD_to_TripSheetIncome(Sheet.getTripSheetID(),
							userDetails.getCompany_id());

					// not equal to zreo get all fixed expense

					List<TripRoutefixedIncome> RoutefixedIncome = TripRouteService.listTripRouteFixedIncomeList(New_RouteID,
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


	@RequestMapping(value = "/searchTripSheetByDate", method = RequestMethod.POST)
	public ModelAndView closeDailyTripDaily(@RequestParam("searchDate") final String TripCollectionDate,
			RedirectAttributes redir,
			final HttpServletRequest request) {
		Map<String, Object> 			model 				= new HashMap<String, Object>();
		CustomUserDetails 				userDetails 		= null;
		HashMap<String, Object> 		configuration	    = null;
		boolean 						hideClosedTripSheet	= false;
		ModelAndView 					map 			    = new ModelAndView("SearchVehicleTS");
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<TripSheetDto> tripSheet = tripSheetBL.prepareListofTripSheet(tripSheetService.getTripSheetDetailsOfSelectedDate(TripCollectionDate, userDetails.getCompany_id()));
			List<TripSheetDto> tripSheetFinal = null;
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
			hideClosedTripSheet	= (boolean) configuration.getOrDefault(TripSheetConfigurationConstant.HIDE_CLOSED_TRIPSHEET, false);

			if(hideClosedTripSheet && !permission.contains(new SimpleGrantedAuthority("SHOW_TRIPSHEET_CLOSE_STATUS"))){
				for(TripSheetDto dto  : tripSheet) {
					if (dto.getTripStutesId() != TripSheetStatus.TRIP_STATUS_CLOSED && dto.getTripStutesId() != TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED){
						if(tripSheetFinal == null) {
							tripSheetFinal = new ArrayList<TripSheetDto>();
						}
						tripSheetFinal.add(dto);
					} 
				}
			}
			else {
				tripSheetFinal = tripSheet;
			}
			map.addObject("permissions", permission);
			model.put("TripSheet", tripSheetFinal);
			model.put("configuration", configuration);
			model.put("TripSheetAdvance", tripSheetBL.Total_Amount_TRIPSHEET_Advance(tripSheetFinal));
		} catch (Exception e) {

			model.put("NotFound", true);
			return new ModelAndView("redirect:/newTripSheetEntries.in", model);
		} finally {
			userDetails = null;
		}
		return new ModelAndView("newTripSheet", model);

	}


	// Update Route Fixed expense Removed old Route Change Add New Route fixed
	// expense in Tripsheet
	public void Update_SubRoute_fixed_expense_Amount_Old_Route(Integer New_SubRouteID, Integer Old_SubRouteID, TripSheet Sheet)
			throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// Check Route Change are not
			if (New_SubRouteID.equals(Old_SubRouteID)) {
				// Old Route ID and New TripSheet Edit Route ID same No Change
			} else {

				// Check new Route in fixed Route are New enter Route
				if (New_SubRouteID != TRIP_SHEET_DEFALAT_ZERO) {

					// Delete all Old Expense in TripSheet Details To Remove
					tripSheetService.Delete_TSID_to_TripSheetExpence(Sheet.getTripSheetID(),
							userDetails.getCompany_id());

					// not equal to zreo get all fixed expense

					List<TripRoutefixedExpense> RoutefixedExpense = TripRouteService
							.listTripRouteFixedExpenes(New_SubRouteID, userDetails.getCompany_id());
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
	
	@RequestMapping(value = "/addPOD", method = RequestMethod.GET)
	public ModelAndView addPOD(@ModelAttribute("command") TripSheetDto TripSheet, BindingResult result) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails));
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("addPODTripSheet", model);
	}
	
	@RequestMapping(value = "/updatePOD", method = RequestMethod.POST)
	public ModelAndView updatePOD(@ModelAttribute("command") TripSheetDto TripSheet,
			 final HttpServletRequest request)throws Exception {
		Map<String, Object> 		model 			= new HashMap<String, Object>();
		CustomUserDetails 			userDetails		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(TripSheet.getNoOfPOD() != null)
			tripSheetService.updatePODDetails(TripSheet.getTripSheetID(), TripSheet.getNoOfPOD(), userDetails.getCompany_id());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "", model);
	}
	
	@RequestMapping(value = "/receivePODAccount", method = RequestMethod.GET)
	public ModelAndView receivePODAccount(@ModelAttribute("command") TripSheetDto TripSheet, BindingResult result) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails));
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("receivePODTripSheet", model);
	}
	
	@RequestMapping(value = "/updateReceivePOD", method = RequestMethod.POST)
	public ModelAndView updateReceivePOD(@ModelAttribute("command") TripSheetDto TripSheet,
			 final HttpServletRequest request)throws Exception {
		try {
			
			if(TripSheet.getReceivedPOD() != null)
			tripSheetService.updateReceivedPODDetails(TripSheet.getTripSheetID(), TripSheet.getReceivedPOD());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TripSheet.getTripSheetID() + "");
	}
	
	@RequestMapping(value = "/getRecentTripListByVid", method = RequestMethod.GET)
	public void getTripRouteSerachList(@RequestParam(value = "vid", required = true) Integer vid,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails userDetails = null;
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
	}
	@RequestMapping(value = "/searchTripSheetNumberList", method = RequestMethod.POST)
	public void getVehicleListServiceEntries(Map<String, Object> map, @RequestParam("term") final String term,
			@RequestParam("tripCompanyId") final Integer companyId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<TripSheetDto> addresses = new ArrayList<TripSheetDto>();
			TripSheetDto wadd = null;
			List<TripSheetDto> DateVehicle = tripSheetService.searchTripSheetNumberList(companyId, term);
			if (DateVehicle != null && !DateVehicle.isEmpty()) {
				for (TripSheetDto add : DateVehicle) {
					wadd = new TripSheetDto();

					wadd.setTripSheetID(add.getTripSheetID());
					wadd.setTripSheetNumberStr(add.getTripSheetNumberStr());
					wadd.setVid(add.getVid());
					wadd.setVehicle_registration(add.getVehicle_registration());
					wadd.setTripFristDriverName(add.getTripFristDriverName());
					wadd.setTripFristDriverID(add.getTripFristDriverID());

					addresses.add(wadd);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value= "/PrintExpenseSheet", method = RequestMethod.GET)
	public ModelAndView showExpensePrint(@RequestParam("id") final Long id,final HttpServletRequest request)
	{	
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		HashMap<String, Object>    	config						= null;
		CustomUserDetails 			userDetails					= null;
		boolean						showCombineTripDetails		= false;
		boolean						showTripsheetDueAmount		= false;
		List<TripSheetExpenseDto> 	finalExpenseList			= null;
		double						totalDueAmount				= 0.0;
		List<RefreshmentEntryDto> refreshmentList				= null;
		try {	
			
			Double TotalAdvance = 0.0;
			Double TotalExpense = 0.0;
			Double totalIncome = 0.0;
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			config			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			showCombineTripDetails	= (boolean) config.getOrDefault(TripSheetConfigurationConstant.SHOW_COMBINE_TRIP_DETAILS, false);
			showTripsheetDueAmount	= (boolean) config.getOrDefault("showTripsheetDueAmount", false);
			
			HashMap<String, Object> comConfig = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			model.put("configuration", config);
			
			
			System.err.println("Config"+config);
			System.err.println("showExpensePrint --" +config.get("showExpensePrint"));
			
			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(id, userDetails));
			// get the Trip sheet ID to Details
			UserProfileDto company	= userProfileService.findUserProfileByUser_email(userDetails.getEmail());
			if((boolean) comConfig.get("showVehicleGroupWiseCompany")) {
				VehicleGroupWiseCompanyName	companyName =	vehicleGroupWiseCompanyNameRepository.getGroupWiseCompanyName(trip.getVehicleGroupId());
				if(companyName != null) {
					company.setCompany_name(companyName.getCompanyName());
				}
			}
			model.put("company", company);
			model.put("TripSheet", trip);
			// get Trip sheet id to get all Advance Details
			VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Fuel_Entries_Show(vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(trip.getVid(), userDetails.getCompany_id()));
			model.put("vehicle", vehicle);
			model.put("TripSheetAdvance", tripSheetService.findAllTripSheetAdvance(id, userDetails.getCompany_id()));
			BusBookingDetailsDto	busDetailsDto	=  busBookingDetailsService.getBusBookingDetailsByTripSheetId(trip.getTripSheetID());
			ValueObject	refreshmentObj	=refreshmentEntryService.getRefreshmentEntryListByTripSheetId(trip.getTripSheetID());
			refreshmentList=(List<RefreshmentEntryDto>) refreshmentObj.get("refreshment");
			
			if(refreshmentList !=null && !refreshmentList.isEmpty()) {
				model.put("totalConsumption", refreshmentObj.getDouble("totalConsumption",0));
				model.put("totalRQty", refreshmentObj.getDouble("totalRQty",0));
				model.put("totalQty", refreshmentObj.getDouble("totalQty",0));
				model.put("grandTotal", refreshmentObj.getDouble("grandTotal",0));
				model.put("totalReturnAmount", refreshmentObj.getDouble("totalReturnAmount",0));
				model.put("refreshmentTotalAmount",Utility.round((refreshmentObj.getDouble("grandTotal",0) - refreshmentObj.getDouble("totalReturnAmount",0)), 2));
				model.put("refreshmentList", refreshmentList);
			}
			if(busDetailsDto != null && trip.getTripUsageKM() != null & busDetailsDto.getRate() != null) {
				model.put("busBookingAmount", Utility.round((busDetailsDto.getRate() * trip.getTripUsageKM()), 2));
			}
			model.put("busBooking", busDetailsDto);

			// get Trip sheet id to get all Expense Details
			if(showCombineTripDetails) {
				List<TripSheetExpenseDto> expenseCombine = tripSheetService.findAllTripSheetExpense(trip.getTripSheetID(), userDetails.getCompany_id());
				if(expenseCombine != null && !expenseCombine.isEmpty()) {
					
					HashMap<Integer, TripSheetExpenseDto> expenseMap = tripSheetService.findAllTripSheetExpenseCombineMap(expenseCombine);
					if(expenseMap != null) {
						finalExpenseList = new ArrayList<TripSheetExpenseDto>();
						finalExpenseList.addAll(expenseMap.values());
					}
					model.put("ExpenseCombineList",finalExpenseList);
				}
			} else {
			model.put("TripSheetExpense", tripSheetService.findAllTripSheetExpense(id, userDetails.getCompany_id()));
			}
			
			List<DriverSalaryAdvanceDto> DriverAdvanvce = DriverSalaryAdvanceService.List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(id, DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY, userDetails.getCompany_id());
			if(DriverAdvanvce != null) {
				double penaltyTotal = 0;
				for(DriverSalaryAdvanceDto dto:DriverAdvanvce) {
					 penaltyTotal += dto.getADVANCE_AMOUNT();
				}
				model.put("penaltyTotal",tofixedTwo.format(penaltyTotal));
				model.put("DriverAdvanvce",tofixedTwo.format(DriverAdvanvce));
			}
			
			if(showTripsheetDueAmount) {
				model.put("TripsheetDueAmount",
						tripSheetService.getDueAmountListByTripsheetId(id, userDetails.getCompany_id()));
				model.put("TotalDueAmount",
						tripSheetService.getTotalDueAmountByTripsheetId(id, userDetails.getCompany_id()));
				totalDueAmount = (double) model.get("TotalDueAmount");
			}
			
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			TotalAdvance = trip.getTripTotalAdvance();
			if (trip.getTripTotalAdvance() == null) {
				TotalAdvance = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			model.put("advanceTotal", df.format(TotalAdvance));
			
			df.setMaximumFractionDigits(TRIP_SHEET_DEFALAT_ZERO);
			TotalExpense = trip.getTripTotalexpense();
			if (trip.getTripTotalexpense() == null) {
				TotalExpense = TRIP_SHEET_DEFALAT_AMOUNT;
			}
			if(trip.getTripTotalincome() != null) {
				totalIncome	= trip.getTripTotalincome();
			}
			model.put("expenseTotal", df.format(TotalExpense));
			model.put("balance", df.format(TotalAdvance-TotalExpense));
			
			if((boolean) config.get("reverseDriverBalance")) {
				model.put("driverBalance", df.format(TotalExpense - (totalIncome + TotalAdvance)));
			}else {
				model.put("driverBalance", df.format((totalIncome + TotalAdvance) - TotalExpense - totalDueAmount));
			}
			
			model.put("preparedBy", trip.getTripCreatedBy());
			model.put("authorizedBy", trip.getClosedBy());
			
			if (trip.getTripCommentTotal() != null && trip.getTripCommentTotal() != TRIP_SHEET_DEFALAT_ZERO) {
				model.put("TripComment", tripSheetBL.TripSheetComment_Display(
						tripCommentService.list_TripSheet_ID_TO_TripSheetComment(id, userDetails.getCompany_id())));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("showTripSheetExpensePrint", model);
	}
	
	@RequestMapping(value = "/addTripsheetWeight", method = RequestMethod.GET)
	public ModelAndView addTripsheetWeight(@ModelAttribute("command") TripSheetDto TripSheet, BindingResult result,HttpServletRequest request) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		HashMap<String, Object>    	config						= null;
		HashMap<String, Object>    	tripRouteConfig				= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			config	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			tripRouteConfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIP_ROUTE_CONFIGURATION_CONFIG);
			
			
			model.put("tripRouteConfig", tripRouteConfig);
			model.put("configuration", config);

			TripSheetDto trip = tripSheetBL.GetTripSheetDetails(tripSheetService.getTripSheetDetails(TripSheet.getTripSheetID(), userDetails));
			// get the Trip sheet ID to Details
			model.put("TripSheet", trip);
			model.put("config", config);
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());

		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			config						= null;
		}
		return new ModelAndView("addTripSheetWeight", model);
	}
	
		@RequestMapping(value = "/getTripDriverList", method = RequestMethod.GET)
		public void getTripDriverList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Driver> driverList = new ArrayList<Driver>();
			for (Driver driver : tripSheetService.getTripDriverList(userDetails.getCompany_id(), Long.parseLong(request.getParameter("tripsheetId")) )) {
				Driver dto = new Driver();
				dto.setDriver_id(driver.getDriver_id());
				dto.setDriver_firstname(driver.getDriver_firstname());
				driverList.add(dto);
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(driverList));
		}
}

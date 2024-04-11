package org.fleetopgroup.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * @author fleetop
 *
 */
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.fleetopgroup.constant.DriverLedgerTypeConstant;
import org.fleetopgroup.constant.FuelConfigurationConstants;
import org.fleetopgroup.constant.FuelInvoiceConstant;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.GPSConstant;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripRouteFixedType;
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
import org.fleetopgroup.persistence.bl.FuelEntryTxnCheckerBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.bl.TripSheetMobileBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.bl.VendorTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripCollectionSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.model.Branch;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.FuelSequenceCounter;
import org.fleetopgroup.persistence.model.FuelStockDetails;
import org.fleetopgroup.persistence.model.TripDailyExpense;
import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetHistory;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.service.VehicleProfitAndLossTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IBranchService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverLedgerService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IFuelInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IFuelSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.ITripCollectionService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.FileUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import au.com.bytecode.opencsv.CSVReader;
import org.fleetopgroup.persistence.service.BankPaymentService;
@Controller
public class FuelController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private IVendorService vendorService;

	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired
	private IFuelService fuelService;
	@Autowired
	private IDriverService driverService;

	@Autowired
	private ITripSheetService TripSheetService;

	@Autowired
	private IFuelSequenceService fuelSequenceService;

	@Autowired
	private IUserProfileService userProfileService;

	TripSheetBL VehStBL = new TripSheetBL();

	@Autowired
	private IServiceReminderService ServiceReminderService;

	@Autowired
	private IVehicleOdometerHistoryService vehicleOdometerHistoryService;

	@Autowired
	private ITripDailySheetService TripDailySheetService;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;

	@Autowired
	private ITripCollectionService tripCollectionService;
	
	
	@Autowired private IVehicleProfitAndLossService		vehicleProfitAndLossService;
	
	@Autowired	private VehicleProfitAndLossTxnCheckerService	vehicleProfitAndLossTxnCheckerService;
	@Autowired IEmailAlertQueueService 					emailAlertQueueService;
	@Autowired ISmsAlertQueueService 					smsAlertQueueService;
	@Autowired IVehicleGPSDetailsService				vehicleGPSDetailsService;
	@Autowired IVehicleAgentTxnCheckerService			vehicleAgentTxnCheckerService;
	@Autowired private	IVehicleAgentIncomeExpenseDetailsService			vehicleAgentIncomeExpenseDetailsService;
	@Autowired private IPendingVendorPaymentService			pendingVendorPaymentService;
	@Autowired private IFuelInvoiceService					fuelInvoiceService;
	@Autowired	private IDriverLedgerService				driverLedgerService;
	@Autowired ITripSheetService 							tripSheetService;
	@Autowired ITripSheetHistoryService 					tripSheetHistoryService; 
	
	TripSheetMobileBL	tripSheetMobileBL			=      new TripSheetMobileBL();


	@Autowired private 	ICashPaymentService					cashPaymentService;
	@Autowired
	private IBranchService BranchService;
	
	@Autowired IBankPaymentService	bankPaymentService;
	
	VehicleOdometerHistoryBL VOHBL = new VehicleOdometerHistoryBL();

	VendorTypeBL VenType = new VendorTypeBL();
	VendorBL VenBL = new VendorBL();
	FuelBL FuBL = new FuelBL();
	VehicleBL VBL = new VehicleBL();
	DriverBL DBL = new DriverBL();
	FuelEntryTxnCheckerBL	checkerBL = new FuelEntryTxnCheckerBL();
	VehicleProfitAndLossTxnCheckerBL	txnCheckerBL = new VehicleProfitAndLossTxnCheckerBL();	
	FuelBL 						fuelBL 						= new FuelBL();
	TripSheetBL 				tripSheetBL					= new TripSheetBL();
	VehicleAgentTxnCheckerBL	agentTxnCheckerBL			= new VehicleAgentTxnCheckerBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat sqldateTime = new SimpleDateFormat("yyyy-mm-dd");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping(value = "/searchFuel", method = RequestMethod.POST)
	public ModelAndView searchFuel(@RequestParam("Search") final String Fuel_id, final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("fuel", FuBL.prepareListofFuel(fuelService.SearchFuel(Fuel_id, userDetails)));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			userDetails = null;
		}
		
		return new ModelAndView("Fuel_Report", model);
	}

	@RequestMapping(value = "/searchFuelShow", method = RequestMethod.POST)
	public ModelAndView searchFuelShow(@RequestParam("Search") final Long Fuel_id, final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		FuelDto fuel = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			fuel = fuelService.getFuelByNumber(Fuel_id, userDetails);
			model.put("fuel", FuBL.getFuelId(fuel));

		} catch (NullPointerException e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/Fuel/1.in", model);
		} catch (Exception e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/Fuel/1.in", model);
		}

		return new ModelAndView("Fuel_Show", model);
	}

	/* show main page of Issues */
	@RequestMapping(value = "/Fuel/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView FuelList(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>   configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			Page<Fuel> page = fuelService.getDeployment_Page_Fuel(pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("configuration", configuration);

			model.addAttribute("FuelCount", page.getTotalElements());

			List<FuelDto> pageList = FuBL.prepareListofFuel(fuelService.listFuelEntries(pageNumber, userDetails));

			model.addAttribute("fuel", pageList);

		} catch (NullPointerException e) {
			LOGGER.error("VFuel Entries:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Vehicle Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Fuel");
	}

	/* show main page of Issues */
	@RequestMapping(value = "/YesterdayFuel")
	public ModelAndView ShowYesterdayFuel(@ModelAttribute("command") Fuel fuel, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			model.put("FuelCount", fuelService.countFuel(userDetails.getCompany_id()));

			java.util.Date currentDate = new java.util.Date();
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_YEAR, -1);

			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

			model.put("FuelTodayEntriesCount", fuelService.countFuelTodayEntries(toDate, userDetails.getCompany_id()));

			model.put("fuel", (fuelService.listUPYesterdayFuel(toDate)));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("Fuel", model);
	}

	@RequestMapping(value = "/addFuel")
	public ModelAndView addIssues() throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		HashMap<String, Object> 	gpsConfiguration 	= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			gpsConfiguration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			model.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));
			model.put("configuration",configuration);
			model.put(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_REFERENCE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_REFERENCE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, configuration.getOrDefault(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, true));
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL, false));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("New_Fuel", model);
	}

	@RequestMapping(value = "/MultipleFuelEntries")
	public ModelAndView MultipleFuelEntries() throws Exception {

		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		HashMap<String, Object> 	gpsConfiguration 	= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			ObjectMapper objectMapper = new ObjectMapper();
			model.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));
			model.put("configuration",configuration);
			model.put(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_REFERENCE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_REFERENCE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, configuration.getOrDefault(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, true));
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, false));
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("Add_MultipleFuel", model);
	}

	/** Fuel module to save Fuel data */
	// save Fuel in databases
	@RequestMapping(value = "/saveFuel", method = RequestMethod.POST)
	public ModelAndView saveFuel(@ModelAttribute("command") FuelDto fuelDto,
			@RequestParam("input-file-preview") MultipartFile file, BindingResult result, RedirectAttributes redir) {

		Map<String, Object> 			model 						= new HashMap<String, Object>();
		FuelSequenceCounter 			sequenceCounter 			= null;
		boolean 						FuelValidateStatus 			= false;
		HashMap<String, Object> 		gpsConfiguration			= null;
		VehicleDto 						CheckVehicleStatus			= null;
		List<ServiceReminderDto>		serviceList					= null;
		try {
			
			final CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			String TIDMandatory = "";

			CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(fuelDto.getVid());

			if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
				FuelValidateStatus	= true;
			}
			
			
			if (fuelDto.getFuel_meter() <= CheckVehicleStatus.getVehicle_ExpectedOdameter() || CheckVehicleStatus.getVehicleExpectedKm() == null ||  CheckVehicleStatus.getVehicleExpectedKm() <= 0) {
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
					FuelValidateStatus = true;
				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					FuelValidateStatus = true;
				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					FuelValidateStatus = true;
				}
			}

			if (FuelValidateStatus) {
				List<Fuel> validate = fuelService.listFuelValidate(fuelDto.getVid(), fuelDto.getFuel_meter(),
						fuelDto.getFuel_liters(), fuelDto.getFuel_price(), fuelDto.getVendor_name(),
						fuelDto.getFuel_reference(), userDetails.getCompany_id());

				if (validate != null && !validate.isEmpty()) {
					model.put("duplicateFuelEntries", true);
					return new ModelAndView("redirect:/Fuel/1.in?duplicateFuelEntries=true");

				} else {
					Fuel fuel = saveFuel(fuelDto);
					fuel.setCompanyId(userDetails.getCompany_id());
					fuel.setCreatedById(userDetails.getId());
					fuel.setLastModifiedById(userDetails.getId());
					double fuelAmt = fuel.getFuel_amount();
					fuel.setBalanceAmount(fuelAmt);
					sequenceCounter = fuelSequenceService.findNextFuelNumber(userDetails.getCompany_id());
					if (sequenceCounter == null) {
						return new ModelAndView("redirect:/Fuel/1.in?FuelSequenceCounterMissing=true");
					}
					fuel.setFuel_Number(sequenceCounter.getNextVal());
					if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
						fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);

						java.util.Date currentDate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

						fuel.setFuel_vendor_paymentdate(toDate);
					} else {
						fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
					}

					try {
						java.util.Date currentUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentUpdate.getTime());
						fuel.setCreated(toDate);
						fuel.setLastupdated(toDate);

						fuel.setCreatedById(userDetails.getId());
						fuel.setLastModifiedById(userDetails.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (!file.isEmpty()) {

						fuel.setFuel_document(true);

					} else {

						fuel.setFuel_document(false);
					}

					fuel.setMarkForDelete(false);

					if (fuelDto.getVendor_name() != null
							&& FuelDto.getParseVendorID_STRING_TO_ID(fuelDto.getVendor_name()) != 0) {
						fuel.setVendor_id(FuelDto.getParseVendorID_STRING_TO_ID(fuelDto.getVendor_name()));
					} else {

						fuel.setVendor_id(fuelService.saveFuelEntriesToNewVendorCreateAuto(fuelDto.getVendor_name(), userDetails.getCompany_id() , userDetails.getId()));
					}
					
					// Note: Save Fuel Entries Details
					fuelService.addFuel(fuel);
					
				/*	FuelEntryTxnChecker	fuelEntryTxnChecker	= checkerBL.getFuelEntryTxnCheckerDto(fuel);
					fuelEntryTxnCheckerService.saveFuelEntryTxnChecker(fuelEntryTxnChecker);*/
					
					ValueObject		object	= new ValueObject();
					
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_ID, fuel.getFuel_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_VID, fuel.getVid());
					object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, fuel.getFuel_id());
					VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
					
					vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
					
					if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED){
						ValueObject	ownerShipObject	= new ValueObject();
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, fuel.getFuel_id());
						ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, fuel.getVid());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_AMOUNT, fuel.getFuel_amount());
						ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_FUEL_ENTRY);
						ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, fuel.getCompanyId());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, fuel.getFuel_date());
						
						VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
						
						vehicleAgentTxnCheckerService.save(agentTxnChecker);
					}
					
					TIDMandatory += " F-" + fuel.getFuel_Number() + " ,";
					redir.addFlashAttribute("Number", TIDMandatory);
					model.put("Success", true);

					if (!file.isEmpty()) {

						org.fleetopgroup.persistence.document.FuelDocument fuelDoc = new org.fleetopgroup.persistence.document.FuelDocument();
 
						fuelDoc.setFuel_id(fuel.getFuel_id());
						try {

							byte[] bytes = file.getBytes();
							fuelDoc.setFuel_filename(file.getOriginalFilename());
							fuelDoc.setFuel_content(bytes);
							fuelDoc.setFuel_contentType(file.getContentType());

							fuelDoc.setMarkForDelete(false);

							fuelDoc.setCreatedById(userDetails.getId());
							fuelDoc.setLastModifiedById(userDetails.getId());

							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

							fuelDoc.setCreated(toDate);
							fuelDoc.setLastupdated(toDate);
							fuelDoc.setCompanyId(userDetails.getCompany_id());
						} catch (IOException e) {
							e.printStackTrace();
						}
						// Note: Save FuelDocument Details
						fuelService.add_Fuel_To_FuelDocument(fuelDoc);

						// Note: FuelDocument to Save id to Fuel save
						fuelService.Update_FuelDocument_ID_to_Fuel(fuelDoc.get_id(), true, fuel.getFuel_id(),
								userDetails.getCompany_id());
					}

					// show the driver list in all
					model.put("saveFuel", true);
					// update the Current Odometer vehicle Databases tables
					try {
						
						Vehicle	vehicle	= vehicleService.getVehicel1(fuel.getVid());
						
						if (vehicle.getVehicle_Odometer() < fuelDto.getFuel_meter()) {
							
							vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), vehicle.getCompany_Id());
							ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),
									fuel.getFuel_meter(), vehicle.getCompany_Id());
							
							serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(fuel.getVid(), userDetails.getCompany_id(), userDetails.getId());
							if(serviceList != null) {
								for(ServiceReminderDto list : serviceList) {
									
									if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
										
										ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
										//emailAlertQueueService.sendEmailServiceOdometer(list);
										//smsAlertQueueService.sendSmsServiceOdometer(list);
										
									}
								}
							}
							
							VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToFuel(fuel);
							vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
							vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						}
					} catch (Exception e) {
						LOGGER.error("Fuel Page:", e);
						e.printStackTrace();

					}
					if (fuelDto.getFuel_meter_old() <= fuelDto.getFuel_meter()) {
						if (fuel.getFuel_tank() == 0) {

							// save to update partialFuel entry in databases
							fuelService.updatepartialFuel(fuel.getVid(), fuel.getFuel_tank(), userDetails.getCompany_id());
						}
					}
					
					ValueObject		valueObject	= new ValueObject();
					valueObject.put("fuel", fuel);
					valueObject.put("userDetails", userDetails);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
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
			} else {
				String Link = "";
				if (fuelDto.getFuel_meter() > CheckVehicleStatus.getVehicle_ExpectedOdameter()) {

					Link += " Maxminum Allow Odometer " + CheckVehicleStatus.getVehicle_ExpectedOdameter()
							+ " kindly Check Odometer ";
				}

				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create Fuel Entires " + " " + Link
						+ "" + " </span> ,";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);
				return new ModelAndView("redirect:/addFuel.in", model);
			}

		} catch (Exception e) {
			LOGGER.error("Fuel Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/Fuel/1.in?danger=true", model);
		}

		return new ModelAndView("redirect:/addFuel.in", model);
	}

	// save Multiple Fuel Entries in return same page in databases
	@RequestMapping(value = "/saveMultipleFuel", method = RequestMethod.POST)
	public ModelAndView saveMultipleFuel(@ModelAttribute("command") FuelDto fuelDto,
			@RequestParam("input-file-preview") MultipartFile file, BindingResult result, RedirectAttributes redir) {

		Map<String, Object> 			model 				= new HashMap<String, Object>();
		List<ServiceReminderDto>		serviceList			= null;
		FuelSequenceCounter 			sequenceCounter		= null;
		boolean 						FuelValidateStatus 	= false;
		try {
			
			String TIDMandatory = "";
			final CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// Check Vehicle Status Current IN ACTIVE OR NOT
			VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(fuelDto.getVid());

			if (fuelDto.getFuel_meter() <= CheckVehicleStatus.getVehicle_ExpectedOdameter()) {

				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
					FuelValidateStatus = true;
				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					FuelValidateStatus = true;
				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					FuelValidateStatus = true;
				}
			}

			if (FuelValidateStatus) {
				List<Fuel> validate = fuelService.listFuelValidate(fuelDto.getVid(), fuelDto.getFuel_meter(),
						fuelDto.getFuel_liters(), fuelDto.getFuel_price(), fuelDto.getVendor_name(),
						fuelDto.getFuel_reference(), userDetails.getCompany_id());

				if (validate != null && !validate.isEmpty()) {
					model.put("duplicateFuelEntries", true);
					return new ModelAndView("redirect:/MultipleFuelEntries.in", model);

				} else {

					Fuel fuel = saveFuel(fuelDto);

					if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {

						fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
						try {
							java.util.Date currentDate = new java.util.Date();
							DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
							java.util.Date dateTo = dateFormatTime.parse(ft.format(currentDate));
							java.sql.Date toDate = new java.sql.Date(dateTo.getTime());
							fuel.setFuel_vendor_paymentdate(toDate);
						} catch (ParseException e) {

							e.printStackTrace();
						}

					} else {
						fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
					}

					try {
						java.util.Date currentUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentUpdate.getTime());
						fuel.setCreated(toDate);
						fuel.setLastupdated(toDate);

						fuel.setCreatedById(userDetails.getId());
						fuel.setLastModifiedById(userDetails.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (!file.isEmpty()) {

						fuel.setFuel_document(true);
					} else {

						fuel.setFuel_document(false);
					}

					fuel.setMarkForDelete(false);
					sequenceCounter = fuelSequenceService.findNextFuelNumber(userDetails.getCompany_id());
					if (sequenceCounter == null) {
						return new ModelAndView("redirect:/Fuel/1.in?FuelSequenceCounterMissing=true");
					}
					fuel.setCompanyId(userDetails.getCompany_id());
					fuel.setFuel_Number(sequenceCounter.getNextVal());
					fuel.setCreatedById(userDetails.getId());
					fuel.setLastModifiedById(userDetails.getId());

					if (fuelDto.getVendor_name() != null
							&& FuelDto.getParseVendorID_STRING_TO_ID(fuelDto.getVendor_name()) != 0) {
						fuel.setVendor_id(FuelDto.getParseVendorID_STRING_TO_ID(fuelDto.getVendor_name()));
					} else {

						fuel.setVendor_id(fuelService.saveFuelEntriesToNewVendorCreateAuto(fuelDto.getVendor_name(), userDetails.getCompany_id(),userDetails.getId()));
					}

					// save to fuel entry in databases
					fuelService.addFuel(fuel);
					
					ValueObject		object	= new ValueObject();
					
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_ID, fuel.getFuel_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_VID, fuel.getVid());
					object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, fuel.getFuel_id());
					
					VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
					
					vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
					

					TIDMandatory += " F-" + fuel.getFuel_Number() + " ,";
					redir.addFlashAttribute("Number", TIDMandatory);
					model.put("Success", true);

					if (!file.isEmpty()) {

						org.fleetopgroup.persistence.document.FuelDocument fuelDoc = new org.fleetopgroup.persistence.document.FuelDocument();

						fuelDoc.setFuel_id(fuel.getFuel_id());
						try {

							byte[] bytes = file.getBytes();
							fuelDoc.setFuel_filename(file.getOriginalFilename());
							fuelDoc.setFuel_content(bytes);
							fuelDoc.setFuel_contentType(file.getContentType());

							/** Set Status in Issues */
							fuelDoc.setMarkForDelete(false);

							fuelDoc.setCreatedById(userDetails.getId());
							fuelDoc.setLastModifiedById(userDetails.getId());


							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

							/** Set Created Current Date */
							fuelDoc.setCreated(toDate);
							fuelDoc.setLastupdated(toDate);
							fuelDoc.setCompanyId(userDetails.getCompany_id());
						} catch (IOException e) {
							e.printStackTrace();
						}

						// Note: Save FuelDocument Details
						fuelService.add_Fuel_To_FuelDocument(fuelDoc);

						// Note: FuelDocument to Save id to Fuel save
						fuelService.Update_FuelDocument_ID_to_Fuel(fuelDoc.get_id(), true, fuel.getFuel_id(),
								userDetails.getCompany_id());
					}

					model.put("saveFuel", true);
					try {
						Vehicle	vehicle	= vehicleService.getVehicel1(fuel.getVid());
						
						if (vehicle.getVehicle_Odometer() < fuelDto.getFuel_meter()) {
							
							vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), userDetails.getCompany_id());
							ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),
									fuel.getFuel_meter(), userDetails.getCompany_id());
							
							serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(fuel.getVid(), userDetails.getCompany_id(), userDetails.getId());
							if(serviceList != null) {
								for(ServiceReminderDto list : serviceList) {
									
									if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
										
										ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
										//emailAlertQueueService.sendEmailServiceOdometer(list);
										//smsAlertQueueService.sendSmsServiceOdometer(list);
									}
								}
							}

							VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToFuel(fuel);
							vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
							vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						}
					} catch (Exception e) {
						LOGGER.error("Fuel Page:", e);
						e.printStackTrace();

					}
					if (fuelDto.getFuel_meter_old() <= fuelDto.getFuel_meter()) {
						if (fuel.getFuel_tank() == 0) {
							fuelService.updatepartialFuel(fuel.getVid(), fuel.getFuel_tank(), userDetails.getCompany_id());
						}
					}
					ValueObject		valueObject	= new ValueObject();
					valueObject.put("fuel", fuel);
					valueObject.put("userDetails", userDetails);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
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
				
			} // checking Vehicle Status
			else {
				String Link = "";
				if (fuelDto.getFuel_meter() > CheckVehicleStatus.getVehicle_ExpectedOdameter()) {

					Link += " Maxminum Allow Odometer " + CheckVehicleStatus.getVehicle_ExpectedOdameter()
							+ " kindly Check Odometer ";
				}

				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create Fuel Entires " + " " + Link
						+ "" + " </span> ,";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);
				return new ModelAndView("redirect:/addFuel.in", model);
			}

		} catch (Exception e) {
			LOGGER.error("Fuel Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/MultipleFuelEntries.in?danger=true", model);
		}
		return new ModelAndView("redirect:/MultipleFuelEntries.in", model);
	}

	// save in vehicle page to vehicle Details in fuel
	@RequestMapping(value = "/saveVehicleDetailsFuel", method = RequestMethod.POST)
	public ModelAndView saveVehicleDetailsFuel(@ModelAttribute("command") FuelDto fuelDto,
			@RequestParam("input-file-preview") MultipartFile file, BindingResult result) throws Exception {
		Map<String, Object> 			model 					= new HashMap<String, Object>();
		List<ServiceReminderDto>		serviceList				= null;
		Fuel 							fuel 					= saveFuel(fuelDto);
		FuelSequenceCounter 			sequenceCounter 		= null;
		try {
			final CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Fuel> validate = fuelService.listFuelValidate(fuel.getVid(), fuel.getFuel_meter(),
					fuel.getFuel_liters(), fuel.getFuel_price(), fuelDto.getVendor_name(), fuel.getFuel_reference(), userDetails.getCompany_id());

			if (validate != null && !validate.isEmpty()) {
				model.put("duplicateFuelEntries", true);
				return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + fuel.getVid() + "", model);

			} else {

				try {
					java.util.Date currentUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentUpdate.getTime());
					fuel.setCreated(toDate);
					fuel.setLastupdated(toDate);

					fuel.setCreatedById(userDetails.getId());
					fuel.setLastModifiedById(userDetails.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);

					java.util.Date currentDate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

					fuel.setFuel_vendor_paymentdate(toDate);

				} else {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
				}
				if (!file.isEmpty()) {

					fuel.setFuel_document(true);
				} else {

					fuel.setFuel_document(false);
				}

				fuel.setMarkForDelete(false);

				if (fuelDto.getVendor_name() != null
						&& FuelDto.getParseVendorID_STRING_TO_ID(fuelDto.getVendor_name()) != 0) {
					fuel.setVendor_id(FuelDto.getParseVendorID_STRING_TO_ID(fuelDto.getVendor_name()));
				} else {

					fuel.setVendor_id(fuelService.saveFuelEntriesToNewVendorCreateAuto(fuelDto.getVendor_name(), userDetails.getCompany_id(),userDetails.getId()));
				}

				// save to fuel entry in databases
				sequenceCounter = fuelSequenceService.findNextFuelNumber(userDetails.getCompany_id());
				if (sequenceCounter == null) {
					return new ModelAndView("redirect:/Fuel/1.in?FuelSequenceCounterMissing=true");
				}
				fuel.setCompanyId(userDetails.getCompany_id());
				fuel.setFuel_Number(sequenceCounter.getNextVal());

				// save to fuel entry in databases
				fuelService.addFuel(fuel);
				
				ValueObject		object	= new ValueObject();
				
				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_ID, fuel.getFuel_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_VID, fuel.getVid());
				object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, fuel.getFuel_id());
				
				VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
				
				vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);

				if (!file.isEmpty()) {

					org.fleetopgroup.persistence.document.FuelDocument fuelDoc = new org.fleetopgroup.persistence.document.FuelDocument();

					fuelDoc.setFuel_id(fuel.getFuel_id());
					try {

						byte[] bytes = file.getBytes();
						fuelDoc.setFuel_filename(file.getOriginalFilename());
						fuelDoc.setFuel_content(bytes);
						fuelDoc.setFuel_contentType(file.getContentType());

						fuelDoc.setMarkForDelete(false);

						fuelDoc.setCreatedById(userDetails.getId());
						fuelDoc.setLastModifiedById(userDetails.getId());


						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						/** Set Created Current Date */
						fuelDoc.setCreated(toDate);
						fuelDoc.setLastupdated(toDate);
						fuelDoc.setCompanyId(userDetails.getCompany_id());
					} catch (IOException e) {
						e.printStackTrace();
					}

					// Note: Save FuelDocument Details
					fuelService.add_Fuel_To_FuelDocument(fuelDoc);

					// Note: FuelDocument to Save id to Fuel save
					fuelService.Update_FuelDocument_ID_to_Fuel(fuelDoc.get_id(), true, fuel.getFuel_id(),
							userDetails.getCompany_id());
				}

				// update the Current Odometer vehicle Databases tables
				try {

					Vehicle	vehicle	= vehicleService.getVehicel1(fuel.getVid());
					
					if (vehicle.getVehicle_Odometer() < fuelDto.getFuel_meter()) {
						
						vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), userDetails.getCompany_id());
						// update current Odometer update Service Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),
								fuel.getFuel_meter(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(fuel.getVid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									//emailAlertQueueService.sendEmailServiceOdometer(list);
									//smsAlertQueueService.sendSmsServiceOdometer(list);
								}
							}
						}

						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToFuel(fuel);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);

					}

				} catch (Exception e) {
					LOGGER.error("Fuel Page:", e);

				}
				if (fuelDto.getFuel_meter_old() <= fuelDto.getFuel_meter()) {
					if (fuel.getFuel_tank() == 0) {

						// save to update partialFuel entry in databases
						fuelService.updatepartialFuel(fuel.getVid(), fuel.getFuel_tank(), userDetails.getCompany_id());
					}
				}
				model.put("saveFuel", true);
				
				ValueObject		valueObject	= new ValueObject();
				valueObject.put("fuel", fuel);
				valueObject.put("userDetails", userDetails);
				valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
				valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
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
			LOGGER.error("Fuel Page:", e);
			e.printStackTrace();
			model.put("duplicateFuelEntries", true);
			return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + fuel.getVid() + "", model);
		}
		// return new ModelAndView("Show_Vehicle_Fuel", model);
		return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + fuel.getVid() + "", model);
	}

	@RequestMapping(value = "/saveTripSheetFuel", method = RequestMethod.POST)
	public ModelAndView saveTripSheetFuel(@RequestParam("TRIPSHEETID") final Long TRIPSHEET_ID,
		@RequestParam("PAIDBY") final String PAIDBY, @ModelAttribute("command") FuelDto fuelDto,
		@RequestParam("input-file-preview") MultipartFile file, BindingResult result) {

		Map<String, Object> 		model 				= new HashMap<String, Object>();
		FuelSequenceCounter 		sequenceCounter 	= null;
		List<Fuel> 					validate			= null;
		List<ServiceReminderDto>	serviceList			= null;
		try {

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());


			final	CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			validate = fuelService.listFuelValidate(fuelDto.getVid(), fuelDto.getFuel_meter(), fuelDto.getFuel_liters(), fuelDto.getFuel_price(), fuelDto.getVendor_name(),fuelDto.getFuel_reference(), userDetails.getCompany_id());
			
			if (validate != null && !validate.isEmpty()) {
				model.put("duplicateFuelEntries", true);
				return new ModelAndView("redirect:/addTSFuel.in?ID=" + TRIPSHEET_ID + "", model);

			} else {
				
				Fuel fuel = saveFuel(fuelDto);

				if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
					fuel.setFuel_vendor_paymentdate(toDate);

				} else {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
				}

				try {
					fuel.setCreated(toDate);
					fuel.setLastupdated(toDate);
					fuel.setCreatedById(userDetails.getId());
					fuel.setLastModifiedById(userDetails.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (!file.isEmpty()) {
					fuel.setFuel_document(true);
				} else {
					fuel.setFuel_document(false);
				}

				fuel.setFuel_TripsheetID(TRIPSHEET_ID);
				fuel.setMarkForDelete(false);
				sequenceCounter = fuelSequenceService.findNextFuelNumber(userDetails.getCompany_id());
				
				if (sequenceCounter == null) {
					return new ModelAndView("redirect:/Fuel/1.in?FuelSequenceCounterMissing=true");
				}
				
				fuel.setCompanyId(userDetails.getCompany_id());
				fuel.setFuel_Number(sequenceCounter.getNextVal());

				if (fuelDto.getVendor_name() != null && FuelDto.getParseVendorID_STRING_TO_ID(fuelDto.getVendor_name()) != 0) {
					fuel.setVendor_id(FuelDto.getParseVendorID_STRING_TO_ID(fuelDto.getVendor_name()));
				} else {
					fuel.setVendor_id(fuelService.saveFuelEntriesToNewVendorCreateAuto(fuelDto.getVendor_name(), userDetails.getCompany_id(),userDetails.getId()));
				}

				fuelService.addFuel(fuel);
				
				ValueObject		object	= new ValueObject();
				
				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_ID, fuel.getFuel_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_VID, fuel.getVid());
				object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, fuel.getFuel_id());
				VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
				
				vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);

				if (!file.isEmpty()) {

					org.fleetopgroup.persistence.document.FuelDocument fuelDoc = new org.fleetopgroup.persistence.document.FuelDocument();

					fuelDoc.setFuel_id(fuel.getFuel_id());
					try {

						byte[] bytes = file.getBytes();
						fuelDoc.setFuel_filename(file.getOriginalFilename());
						fuelDoc.setFuel_content(bytes);
						fuelDoc.setFuel_contentType(file.getContentType());
						fuelDoc.setMarkForDelete(false);
						fuelDoc.setCreatedById(userDetails.getId());
						fuelDoc.setLastModifiedById(userDetails.getId());
						fuelDoc.setCreated(toDate);
						fuelDoc.setLastupdated(toDate);
						fuelDoc.setCompanyId(userDetails.getCompany_id());
					} catch (IOException e) {
						e.printStackTrace();
					}

					// Note: Save FuelDocument Details
					fuelService.add_Fuel_To_FuelDocument(fuelDoc);

					// Note: FuelDocument to Save id to Fuel save
					fuelService.Update_FuelDocument_ID_to_Fuel(fuelDoc.get_id(), true, fuel.getFuel_id(),userDetails.getCompany_id());
				}

				model.put("saveFuel", true);
				try {
					Vehicle	vehicle	= vehicleService.getVehicel1(fuel.getVid());
					
					if (vehicle.getVehicle_Odometer() < fuelDto.getFuel_meter()) {
						
						vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), userDetails.getCompany_id());
						ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),fuel.getFuel_meter(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(fuel.getVid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									//emailAlertQueueService.sendEmailServiceOdometer(list);
									//smsAlertQueueService.sendSmsServiceOdometer(list);
								}
							}
						}
						
						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToFuel(fuel);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);

					}
				} catch (Exception e) {
					LOGGER.error("Fuel Page:", e);
					e.printStackTrace();

				}
				if (fuelDto.getFuel_meter_old() <= fuelDto.getFuel_meter()) {
					if (fuel.getFuel_tank() == 0) {

						fuelService.updatepartialFuel(fuel.getVid(), fuel.getFuel_tank(), userDetails.getCompany_id());
					}
				}

				TripSheet tsheet = new TripSheet();
				tsheet.setTripSheetID(TRIPSHEET_ID);

				if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {

					UserProfileDto Orderingname = userProfileService
							.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());

					TripSheetExpense TripExpense = new TripSheetExpense();
					TripExpense.setExpenseId((int) fuel.getFuelTypeId());
					TripExpense.setExpenseAmount(fuel.getFuel_amount());
					TripExpense.setExpensePlaceId(Orderingname.getBranch_id());
					TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
					TripExpense.setCreatedById(userDetails.getId());
					TripExpense.setTripsheet(tsheet);
					TripExpense.setFuel_id(fuel.getFuel_id());
					TripExpense.setCreated(toDate);
					TripExpense.setCompanyId(userDetails.getCompany_id());
					TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());
					TripSheetService.addTripSheetExpense(TripExpense);

					TripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(TRIPSHEET_ID, userDetails.getCompany_id());

				}

				ValueObject		valueObject	= new ValueObject();
				valueObject.put("fuel", fuel);
				valueObject.put("userDetails", userDetails);
				valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
				valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
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
			LOGGER.error("Fuel Page:", e);
			e.printStackTrace();
			model.put("danger", true);
			return new ModelAndView("redirect:/addTSFuel.in?ID=" + TRIPSHEET_ID + "", model);
		}

		return new ModelAndView("redirect:/showTripSheet.in?tripSheetID=" + TRIPSHEET_ID + "", model);
	}

	// save Issues in databases
	@RequestMapping(value = "/removeTSFuel")
	public ModelAndView removeTSFuel(@RequestParam("TSID") final Long TripSheetID,
			@RequestParam("FID") final Long Fuel_id, final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			List<Fuel> validateApproval = fuelService.getFuelValidate_APPROVAL_Status(Fuel_id, userDetails.getCompany_id());
			HashMap<String, Object> tripconfig = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);	
		
			TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(TripSheetID);
			TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
			
			if (validateApproval != null && !validateApproval.isEmpty()) {

				model.put("ApprovedValidate", true);

				return new ModelAndView("redirect:/Fuel/1.in", model);
			} else {
				try {
					java.util.Date    currentDateUpdate =  new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					Fuel fuel = fuelService.getFuel(Fuel_id, userDetails.getCompany_id());
					if(fuel.getFuel_TripsheetID() != null && fuel.getFuel_TripsheetID() > 0 && TripSheetService.getLimitedTripSheetDetails(fuel.getFuel_TripsheetID()).getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
						model.put("tripAccClosed", true);
						return new ModelAndView("redirect:/Fuel/1.in", model);
					}
					VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(fuel.getVid());
					
					ValueObject		valueObject	= new ValueObject();
					valueObject.put("fuel", fuel);
					valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					valueObject.put("txnAmount", fuel.getFuel_amount());
					valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(fuel.getFuel_date()));
					valueObject.put("txnTypeId", fuel.getFuel_id());
					valueObject.put("vid", fuel.getVid());
					valueObject.put("companyId", fuel.getCompanyId());
					

					Fuel fueltoDelete = DeleteTosaveFuel(fuel);
					
					if(CheckVehicleStatus.getVehicle_Odometer().equals(fuel.getFuel_meter())) { 
						VehicleOdometerHistory odometerHistoryLsit	= vehicleOdometerHistoryService.getVehicleOdometerHistoryByVidExceptCurrentEntry(fuel.getFuel_id(),fuel.getVid(), userDetails.getCompany_id());
						if(odometerHistoryLsit != null ) {
							if(odometerHistoryLsit.getVehicle_Odometer() < fuel.getFuel_meter()) { //  found either pre entry or post entry 
								vehicleService.updateCurrentOdoMeter(fuel.getVid(), odometerHistoryLsit.getVehicle_Odometer(), userDetails.getCompany_id());
								vehicleOdometerHistoryService.deleteVehicleOdometerHistory(fuel.getFuel_id(), userDetails.getCompany_id());
							}
						
						}
						
					}

					fuelService.deleteFuel(fueltoDelete.getFuel_id(),toDate,userDetails.getId(), userDetails.getCompany_id());
					
					if(fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH && (boolean)tripconfig.get("addInDriverbalanceAfterTripClose")) {
						TripSheetDto tripsheet= TripSheetService.getLimitedTripSheetDetails(fuel.getFuel_TripsheetID());
						Double driverBalance = tripsheet.getDriverBalance();
						if(tripsheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
							if(driverBalance < 0) {
							   driverBalance = driverBalance + fuel.getFuel_amount();
							}
							if(driverBalance >0) {
								driverBalance = driverBalance +  fuel.getFuel_amount(); 
							}
							TripSheetService.updateDriverBalance(tripsheet.getTripSheetID(),driverBalance,userDetails.getCompany_id());
						}
					}
					
					FuelStockDetails	stockDetails	=  fuelInvoiceService.getFuelStockDetailsByPetrolPumpId(fuel.getVendor_id(), userDetails.getCompany_id());
					
					if(stockDetails != null) {
						Double stock  = stockDetails.getStockQuantity() + fuel.getFuel_liters();
						Double amount = stockDetails.getTotalFuelCost() + fuel.getFuel_amount();
						Double rate   = amount/stock;
						fuelInvoiceService.updateFuelStockDetails(fuel.getVendor_id(), rate, fuel.getFuel_liters(), fuel.getFuel_amount());
						if((boolean) configuration.getOrDefault("getStockFromInventory", false) && fuel.getFuelInvoiceId() > 0) {
							ValueObject		fuelInvoiceObject	= new ValueObject();
							fuelInvoiceObject.put("stockTypeId", FuelInvoiceConstant.STOCK_TYPE_FE_DELETE);
							fuelInvoiceObject.put("updatedStock", fuel.getFuel_liters());
							fuelInvoiceObject.put("fuelInvoiceId", fuel.getFuelInvoiceId());
							fuelInvoiceObject.put("remark", FuelInvoiceConstant.getStockType(FuelInvoiceConstant.STOCK_TYPE_FE_DELETE));
							fuelInvoiceService.updateFuelStockDetailsInFuelInvoice(fuelInvoiceObject);
						}
					}
					
					
					if(fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
						pendingVendorPaymentService.deletePendingVendorPayment(fuel.getFuel_id(), PendingPaymentType.PAYMENT_TYPE_FUEL_ENTRIES);
					}
					
					if(fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
						System.err.println("inside cash ");
						cashPaymentService.deleteCashStateMentEntry(fuel.getFuel_id(), ModuleConstant.FUEL_ENTRY, userDetails.getCompany_id(),userDetails.getId(),false);
					}
					if(fuel.getPaymentTypeId() >=0  && (fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CASH || fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT)) {
						System.err.println("inside bankpayment");
						bankPaymentService.delete_Bank_PaymentDetails(fuel.getFuel_id(), ModuleConstant.FUEL_ENTRY,userDetails.getCompany_id(), userDetails.getId());
					}
					// Note : Delete TripSheet Halt Expense Entries
					TripSheetService.DELETE_TRIPSHEETEXPENSE_FUEL_ID(Fuel_id);

					// Note : This Update TripSheet Total Expense Updating The
					// Value
					// Of
					// Total Expense amount to TripSheet
					TripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(TripSheetID, userDetails.getCompany_id());
					
					tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
					
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

					model.put("errorFuel", true);
					return new ModelAndView("redirect:/Fuel/1.in", model);
				}
				model.put("deleteFuel", true);
			}
		} catch (Exception e1) {
			LOGGER.error("Fuel Page:", e1);
			return new ModelAndView("redirect:/addTSFuel.in?ID=" + TripSheetID + "", model);
		}

		return new ModelAndView("redirect:/addTSFuel.in?ID=" + TripSheetID + "", model);
	}

	// This Trip Daily Sheet Fuel entries
	@RequestMapping(value = "/saveTripDailyFuel", method = RequestMethod.POST)
	public ModelAndView saveTripDailyFuel(@RequestParam("TRIPSHEETID") final Long TRIPSHEET_ID,
			@RequestParam("PAIDBY") final String PAIDBY, @ModelAttribute("command") FuelDto fuelDto,
			BindingResult result) {
		List<ServiceReminderDto>		serviceList					= null;
		Map<String, Object> 			model 						= new HashMap<String, Object>();
		FuelSequenceCounter 			sequenceCounter 			= null;
		try {
			final CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Fuel> validate = fuelService.listFuelValidate(fuelDto.getVid(), fuelDto.getFuel_meter(),
					fuelDto.getFuel_liters(), fuelDto.getFuel_price(), fuelDto.getVendor_name(),
					fuelDto.getFuel_reference(), userDetails.getCompany_id());
			

			if (validate != null && !validate.isEmpty()) {
				model.put("duplicateFuelEntries", true);
				return new ModelAndView("redirect:/addTripDailyFuel.in?ID=" + TRIPSHEET_ID + "", model);

			} else {

				Fuel fuel = saveFuel(fuelDto);

				if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);

					java.util.Date currentDate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

					fuel.setFuel_vendor_paymentdate(toDate);

				} else {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
				}

				try {
					java.util.Date currentUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentUpdate.getTime());
					fuel.setCreated(toDate);
					fuel.setLastupdated(toDate);
				} catch (Exception e) {
					e.printStackTrace();
				}

				fuel.setFuel_TripsheetID(TRIPSHEET_ID);
				fuel.setMarkForDelete(false);

				sequenceCounter = fuelSequenceService.findNextFuelNumber(userDetails.getCompany_id());
				if (sequenceCounter == null) {
					return new ModelAndView("redirect:/Fuel/1.in?FuelSequenceCounterMissing=true");
				}
				fuel.setCompanyId(userDetails.getCompany_id());
				fuel.setCreatedById(userDetails.getId());
				fuel.setLastModifiedById(userDetails.getId());
				fuel.setFuel_Number(sequenceCounter.getNextVal());

				if (fuelDto.getVendor_name() != null
						&& FuelDto.getParseVendorID_STRING_TO_ID(fuelDto.getVendor_name()) != 0) {
					fuel.setVendor_id(FuelDto.getParseVendorID_STRING_TO_ID(fuelDto.getVendor_name()));
				} else {

					fuel.setVendor_id(fuelService.saveFuelEntriesToNewVendorCreateAuto(fuelDto.getVendor_name(), userDetails.getCompany_id(),userDetails.getId()));
				}

				// save to fuel entry in databases
				fuelService.addFuel(fuel);
				
				ValueObject		object	= new ValueObject();
				
				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_ID, fuel.getFuel_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_VID, fuel.getVid());
				object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, fuel.getFuel_id());
				
				VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
				
				vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);

				// show the driver list in all
				model.put("saveFuel", true);

				try {
					Vehicle	vehicle	= vehicleService.getVehicel1(fuel.getVid());
					
					if (vehicle.getVehicle_Odometer() < fuelDto.getFuel_meter()) {
						
						vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), userDetails.getCompany_id());
						ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),
								fuel.getFuel_meter(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(fuel.getVid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									//emailAlertQueueService.sendEmailServiceOdometer(list);
									//smsAlertQueueService.sendSmsServiceOdometer(list);
								}
							}
						}

						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToFuel(fuel);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);

					}
				} catch (Exception e) {
					LOGGER.error("Fuel Page:", e);
					e.printStackTrace();

				}
				if (fuelDto.getFuel_meter_old() <= fuelDto.getFuel_meter()) {
					if (fuel.getFuel_tank() == 0) {

						fuelService.updatepartialFuel(fuel.getVid(), fuel.getFuel_tank(), userDetails.getCompany_id());
					}
				}

				TripDailySheet tsheet = new TripDailySheet();
				tsheet.setTRIPDAILYID(TRIPSHEET_ID);

				if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH
						|| fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {

					// below Driver trip Halt amount Add expense Halt Bata
					TripDailyExpense TripExpense = new TripDailyExpense();

					TripExpense.setExpenseAmount(fuel.getFuel_amount());
					TripExpense.setExpenseRefence(fuel.getFuel_reference());
					TripExpense.setTripDailysheet(tsheet);
					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					TripExpense.setCreated(toDate);
					TripExpense.setFuel_id(fuel.getFuel_id());
					TripExpense.setCompanyId(userDetails.getCompany_id());
					TripExpense.setCreatedById(userDetails.getId());
					TripExpense.setExpenseId(Integer.parseInt(fuel.getFuelTypeId() + ""));
					TripExpense.setFixedTypeId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);

					TripDailySheetService.add_TripDailyExpense(TripExpense);

					// update total Expense amount
					TripDailySheetService.update_TripDailySheet_TotalExpense(TRIPSHEET_ID);

				}

				TripDailySheetService.Update_TripDailysheet_Deisel_and_KMPL(TRIPSHEET_ID, userDetails.getCompany_id());
				
				ValueObject		valueObject	= new ValueObject();
				valueObject.put("fuel", fuel);
				valueObject.put("userDetails", userDetails);
				valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
				valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
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
			LOGGER.error("Fuel Page:", e);
			e.printStackTrace();
			model.put("danger", true);
			return new ModelAndView("redirect:/addTripDailyFuel.in?ID=" + TRIPSHEET_ID + "", model);
		}

		return new ModelAndView("redirect:/showTripDaily.in?ID=" + TRIPSHEET_ID + "", model);
	}

	// save Issues in databases
	@RequestMapping(value = "/removeTripDailyFuel")
	public ModelAndView removeTripDailyFuel(@RequestParam("TSID") final Long TripSheetID,
			@RequestParam("FID") final Long Fuel_id, final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;

		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<Fuel> validateApproval = fuelService.getFuelValidate_APPROVAL_Status(Fuel_id, userDetails.getCompany_id());

			if (validateApproval != null && !validateApproval.isEmpty()) {

				model.put("ApprovedValidate", true);

				return new ModelAndView("redirect:/Fuel/1.in", model);
			} else {
				try {
					java.util.Date    currentDateUpdate =  new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
					Fuel fuel = fuelService.getFuel(Fuel_id, userDetails.getCompany_id());
					
					ValueObject		valueObject	= new ValueObject();
					valueObject.put("fuel", fuel);
					valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					valueObject.put("txnAmount", fuel.getFuel_amount());
					valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(fuel.getFuel_date()));
					valueObject.put("txnTypeId", fuel.getFuel_id());
					valueObject.put("vid", fuel.getVid());
					valueObject.put("companyId", fuel.getCompanyId());
					

					Fuel fueltoDelete = DeleteTosaveFuel(fuel);

					fuelService.deleteFuel(fueltoDelete.getFuel_id(),toDate,userDetails.getId(), userDetails.getCompany_id());

					// Note: Delete To Fuel Id Expense Amount
					TripDailySheetService.Delete_Trip_Daily_Expense_Fuel_ID_Value_Amount(Fuel_id,
							userDetails.getCompany_id());

					// Note : update TRIP Daily Update Values Amount
					TripDailySheetService.update_TripDailySheet_TotalExpense(TripSheetID);

					// Note: Update Trip Daily fuel Details and Amount
					TripDailySheetService.Update_TripDailysheet_Deisel_and_KMPL(TripSheetID,
							userDetails.getCompany_id());
					
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

					model.put("errorFuel", true);
					return new ModelAndView("redirect:/Fuel/1.in", model);
				}
				model.put("deleteFuel", true);
			}
		} catch (Exception e1) {
			LOGGER.error("Fuel Page:", e1);
			return new ModelAndView("redirect:/addTripDailyFuel.in?ID=" + TripSheetID + "", model);
		}

		return new ModelAndView("redirect:/addTripDailyFuel.in?ID=" + TripSheetID + "", model);
	}

	/* Should be Download Driver Reminder document */
	@RequestMapping("/download/FuelDocument/{fuel_id}")
	public String FuelDocument(@PathVariable("fuel_id") Long fuel_id, HttpServletResponse response) {
		try {

			if (fuel_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.FuelDocument file = fuelService.get_Fuel_Document_Details(fuel_id, userDetails.getCompany_id());

				if (file != null) {
					if (file.getFuel_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getFuel_filename() + "\"");
						if(file.getFuel_contentType().equals(FileUtility.FILE_EXTENSION_PDF)){
							file.setFuel_contentType(FileUtility.getContentType(file.getFuel_contentType()));
						}
						if(file.getFuel_contentType().equals(FileUtility.FILE_EXTENSION_JPG)){
							file.setFuel_contentType(FileUtility.getContentType(file.getFuel_contentType()));
						}
						if(file.getFuel_contentType().equals(FileUtility.FILE_EXTENSION_EXCEL)){
							file.setFuel_contentType(FileUtility.getContentType(file.getFuel_contentType()));
						}
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getFuel_contentType());
						response.getOutputStream().write(file.getFuel_content());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}

	/*
	 * @RequestMapping(value = "/editFuel") public ModelAndView
	 * editFuel(@RequestParam("FID") final Long Fuel_id, final HttpServletRequest
	 * Result) {
	 * 
	 * Map<String, Object> model = new HashMap<String, Object>(); CustomUserDetails
	 * userDetails = null; FuelDto fuelDto = null; FuelDto fuelDtoBL = null;
	 * List<Fuel> validateApproval = null; HashMap<String, Object> configuration =
	 * null; HashMap<String, Object> gpsConfiguration = null; HashMap<String,
	 * Object> fuelPriceConfig = null; String defaultFuelPrice = null; VehicleDto
	 * nextFuelEntry = null; try { userDetails = (CustomUserDetails)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //
	 * validate the Fuel apprval status or not checking the th validate
	 * validateApproval = fuelService.getFuelValidate_APPROVAL_Status(Fuel_id,
	 * userDetails.getCompany_id());
	 * 
	 * 
	 * fuelPriceConfig =
	 * companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id
	 * (),PropertyFileConstants.FUEL_PRICE_CONFIG);
	 * 
	 * defaultFuelPrice = (String)
	 * fuelPriceConfig.getOrDefault(FuelConfigurationConstants.FUEL_PRICE, 0);
	 * model.put("defaultFuelPrice", Double.parseDouble(defaultFuelPrice));
	 * 
	 * if (validateApproval != null && !validateApproval.isEmpty()) {
	 * 
	 * model.put("ApprovedValidate", true);
	 * 
	 * return new ModelAndView("redirect:/Fuel/1.in", model); } else { try { fuelDto
	 * = fuelService.getFuelDetails(Fuel_id, userDetails.getCompany_id());
	 * if(fuelDto != null) { fuelDtoBL = FuBL.getFuelId(fuelDto);
	 * 
	 * model.put("fuel", fuelDtoBL); configuration =
	 * companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id
	 * (),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG); gpsConfiguration =
	 * companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id
	 * (),PropertyFileConstants.VEHICLE_GPS_CONFIG);
	 * model.put(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL,
	 * configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL,
	 * true)); model.put(FuelConfigurationConstants.SHOW_REFERENCE_COL,
	 * configuration.getOrDefault(FuelConfigurationConstants.SHOW_REFERENCE_COL,
	 * true)); model.put(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK,
	 * configuration.getOrDefault(FuelConfigurationConstants.
	 * SHOW_PERSONAL_EXPENCE_CHECK, true));
	 * model.put(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION,
	 * configuration.getOrDefault(FuelConfigurationConstants.
	 * SHOW_FUEL_DOCUMENT_SELECTION, true));
	 * model.put(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD,
	 * configuration.getOrDefault(FuelConfigurationConstants.
	 * SHOW_FUEL_COMMENT_FIELD, true));
	 * model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL,
	 * (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.
	 * getCompany_id(),
	 * PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(
	 * VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, false));
	 * model.put("vehicle",
	 * vehicleService.Get_Vehicle_Current_Status_TripSheetID(fuelDto.getVid()));
	 * model.put("configuration",configuration);
	 * model.put("gpsConfiguration",gpsConfiguration);
	 * model.put("companyId",userDetails.getCompany_id());
	 * 
	 * nextFuelEntry =
	 * fuelService.getNextFuelEntryDetailsInCaseOfSelectedBackDate(fuelDtoBL.
	 * getFuelDateTimeStr2(), fuelDtoBL.getVid(), userDetails.getCompany_id(),
	 * Fuel_id);
	 * 
	 * if(nextFuelEntry != null) { model.put("nextFuelEntry", nextFuelEntry);
	 * model.put("nextFuelEntryFound", true); }else {
	 * model.put("nextFuelEntryFound", false); } }else { return new
	 * ModelAndView("redirect:/Fuel/1.in?NotFound=true"); }
	 * 
	 * } catch (Exception e) { LOGGER.error("Fuel Page:", e);
	 * System.err.println("Exception : "+e); return new
	 * ModelAndView("redirect:/Fuel/1.in?danger=true"); } } } catch (Exception e) {
	 * LOGGER.error("Fuel Page:", e);
	 * 
	 * } finally { userDetails = null; fuelDto = null; configuration = null;
	 * gpsConfiguration = null; } return new ModelAndView("Fuel_Edit", model); }
	 */
	// save Fuel in databases
	@Transactional
	@RequestMapping(value = "/updateFuel", method = RequestMethod.POST)
	public ModelAndView UpdateFuel(@ModelAttribute("command") FuelDto fuelDto, BindingResult result) {
		// Map<String, Object> model = new HashMap<String, Object>();
		try {
			final CustomUserDetails userDetails			 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final Double			previousFuelAmount	 = fuelDto.getPreviousFuelAmount();
			final String 			previousFuelDate	 = fuelDto.getPreviousFuelDate();
				  boolean			creatingBackDateFuel = fuelDto.isCreatingBackDateFuel();
				  boolean			nextFuelEntryFound   = fuelDto.isNextFuelEntryFound();
			
			// get the issues Dto to save issues
			Fuel fuel = EDITTOsaveFuel(fuelDto);
			
			Fuel oldFuel	=	fuelService.getFuel(fuel.getFuel_id(), userDetails.getCompany_id());
			
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);

			fuel.setCompanyId(userDetails.getCompany_id());
			/*
			 * // get the issues Dto to save issues Fuel fuel =
			 * EDIT_Full_UpdateFuel(fuelDto);
			 */

			try {
				if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
					try {
						java.util.Date currentDate = new java.util.Date();
						DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
						java.util.Date dateTo = dateFormatTime.parse(ft.format(currentDate));
						java.sql.Date toDate = new java.sql.Date(dateTo.getTime());
						fuel.setFuel_vendor_paymentdate(toDate);
						
						
						if(fuel.getFuel_TripsheetID() != null && fuel.getFuel_TripsheetID() > 0 && oldFuel.getPaymentTypeId() != fuel.getPaymentTypeId()) {
							
							UserProfileDto Orderingname = userProfileService
									.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
							TripSheet tsheet = new TripSheet();
							tsheet.setTripSheetID(fuel.getFuel_TripsheetID());
							
							TripSheetExpense TripExpense = new TripSheetExpense();
							TripExpense.setExpenseId((int) fuel.getFuelTypeId());
							TripExpense.setExpenseAmount(fuel.getFuel_amount());
							TripExpense.setExpensePlaceId(Orderingname.getBranch_id());
							TripExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_NEW);
							TripExpense.setCreatedById(userDetails.getId());
							TripExpense.setTripsheet(tsheet);
							TripExpense.setFuel_id(fuel.getFuel_id());
							TripExpense.setCreated(toDate);
							TripExpense.setCompanyId(userDetails.getCompany_id());
							TripExpense.setBalanceAmount(TripExpense.getExpenseAmount());
							
							TripSheetService.addTripSheetExpense(TripExpense);
							
							TripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(fuel.getFuel_TripsheetID(), userDetails.getCompany_id());
						}else {
							if(!fuel.getFuel_amount().equals(previousFuelAmount)) {
								TripSheetExpense TripExpense = TripSheetService.getTripSheetExpenseByFuelId(fuel.getFuel_id());
								TripExpense.setExpenseAmount(fuel.getFuel_amount());
								TripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(fuel.getFuel_TripsheetID(), userDetails.getCompany_id());
							}
						}

					
						
					} catch (ParseException e) {

						e.printStackTrace();
					}

				} else {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
					
					TripSheetService.removeTripSheetFuel(fuel.getFuel_id());
					TripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(oldFuel.getFuel_TripsheetID(), userDetails.getCompany_id());
				}

				try {
					// save to fuel entry in databases
					java.util.Date currentUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentUpdate.getTime());
					fuel.setLastupdated(toDate);

					// fuel.setLastModifiedBy(userDetails.getEmail());
					fuel.setLastModifiedById(userDetails.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(!(boolean) configuration.get("allowToEditFuelRef")) {
					fuel.setFuel_reference(oldFuel.getFuel_reference());
				}
				fuel.setMarkForDelete(false);
				
				if ((boolean) configuration.get("validateUniqueReference") && fuel.getVendor_id() > 0) {
					String fromDate = DateTimeUtility.getFirstDayOfFiscalYear(fuel.getFuel_date(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					String endDate	= DateTimeUtility.getLastDayOfFiscalYear(fuel.getFuel_date(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					
					List<FuelDto>	refValidate = fuelService.validateFuelReferenceInFinancailYear(fromDate, endDate, fuel.getFuel_reference(), fuel.getVendor_id(), oldFuel.getFuel_id());
					
					if (refValidate != null && !refValidate.isEmpty()) {
						return new ModelAndView("redirect:/showFuel.in?FID="+fuel.getFuel_id()+"&refExists=true");
					}
					

				}
				
				// save to fuel entry in databases
				// fuelService.addFuel(fuel);
				fuelService.updateFuel(fuel);
				
				
				VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= null;
				if(!(DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(fuel.getFuel_date().getTime()))) || (fuel.getFuel_amount() - previousFuelAmount) != 0) {
					
					ValueObject		object	= new ValueObject();
					
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_ID, fuel.getFuel_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_VID, fuel.getVid());
					object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, fuel.getFuel_id());
					
					profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
					
					vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
					
				}		

				// update the Current Odometer vehicle Databases tables
				if(creatingBackDateFuel == false && nextFuelEntryFound == false) {
					
					Integer CurrentOdometer = vehicleService
							.updateCurrentOdoMeterGETVehicleToCurrentOdometer(fuelDto.getVid());
					if (CurrentOdometer < fuelDto.getFuel_meter()) {
						vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), userDetails.getCompany_id());

						// update current Odometer update Service Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),
								fuel.getFuel_meter(), userDetails.getCompany_id());

						vehicleOdometerHistoryService.updateVehicleOdometerHistory(fuel.getFuel_meter(),
								fuel.getFuel_id());
					} else {
						vehicleOdometerHistoryService.updateVehicleOdometerHistory(fuel.getFuel_meter(),
								fuel.getFuel_id());
					}
					
				}
			VehicleDto	CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(fuel.getVid(), fuel.getCompanyId());
			ValueObject	ownerShipObject	= null;	
			if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED){
				if((fuel.getFuel_amount() - previousFuelAmount) != 0 || !(DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(fuel.getFuel_date().getTime())))) {
					
					ownerShipObject	= new ValueObject();
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, fuel.getFuel_id());
					ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, fuel.getVid());
					ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, fuel.getFuel_amount());
					ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
					ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_FUEL_ENTRY);
					ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, fuel.getCompanyId());
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, fuel.getFuel_date());
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Fuel Entry");
					ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Fuel Number : "+fuel.getFuel_Number());
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, fuel.getFuel_comments());
					ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, fuel.getCreatedById());
					if(DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(fuel.getFuel_date().getTime()))) {
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, - (fuel.getFuel_amount() - previousFuelAmount));
						ownerShipObject.put("isDateChanged", false);
					}else {
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, - fuel.getFuel_amount());
						ownerShipObject.put("isDateChanged", true);
						
					}
					ownerShipObject.put("previousAmount", -previousFuelAmount);
					ownerShipObject.put("previousDate", DateTimeUtility.getDateFromString(previousFuelDate, DateTimeUtility.DD_MM_YYYY));
					
					
					VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
					vehicleAgentTxnCheckerService.save(agentTxnChecker);
					
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
				}
					
				}

				
				if(!(DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(fuel.getFuel_date().getTime()))) || (fuel.getFuel_amount() - previousFuelAmount) != 0) {
					ValueObject		valueObject	= new ValueObject();
					valueObject.put("fuel", fuel);
					valueObject.put("userDetails", userDetails);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					valueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					
					valueObject.put("isFuelEdit", true);
					valueObject.put("previousFuelAmount", previousFuelAmount);
					valueObject.put("fuelAmountToBeAdded", (fuel.getFuel_amount() - previousFuelAmount));
					
					if(DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(fuel.getFuel_date().getTime()))) {
						valueObject.put("isFuelDateChanged", false);
					}else {
						valueObject.put("isFuelDateChanged", true);
					}
					valueObject.put("previousFuelDate", DateTimeUtility.getDateTimeFromStr(previousFuelDate, DateTimeUtility.DD_MM_YYYY));
					
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
				if(ownerShipObject != null) {
					vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
				}
				
				
			} catch (Exception e) {
				LOGGER.error("Fuel Page:", e);
				e.printStackTrace();

			}
		} catch (Exception e1) {
			LOGGER.error("Fuel Page:", e1);
			e1.printStackTrace();
			return new ModelAndView("redirect:/Fuel/1.in?danger=true");

		}
		return new ModelAndView("redirect:/Fuel/1.in?Update=true");
	}

	@RequestMapping(value = "/showFuel")
	public ModelAndView showFuel(@RequestParam("FID") final Long Fuel_id, final HttpServletResponse result) {
		Map<String, Object> 			model 				= new HashMap<String, Object>();
		HashMap<String, Object> 		configuration		= null;
		CustomUserDetails 				userDetails 		= null;
		FuelDto 						fuel 				= null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			fuel 			= fuelService.getFuelDetails(Fuel_id, userDetails.getCompany_id());
			HashMap<String, Object>  gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_GPS_CONFIG);
			if(fuel != null) {
				configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
				model.put("fuel", FuBL.getFuelId(fuel));
				model.put("configuration", configuration);
				model.put("gpsConfiguration", gpsConfiguration);
			}else {
				model.put("NotFound", true);
				return new ModelAndView("redirect:/Fuel/1.in?NotFound=true", model);
			}

		} catch (Exception e) {
			LOGGER.error("Fuel Page:", e);

		} finally {
			userDetails = null;
			fuel = null;
		}
		return new ModelAndView("Fuel_Show", model);
	}

	// save Issues in databases
	@RequestMapping(value = "/deleteFuel")
	public ModelAndView deleteFuel(@RequestParam("FID") final Long Fuel_id, final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		
		java.util.Date    currentDateUpdate =  new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			// validate the Fuel apprval status or not checking the th validate
			List<Fuel> validateApproval = fuelService.getFuelValidate_APPROVAL_Status(Fuel_id, userDetails.getCompany_id());
			HashMap<String, Object> 		tripConfiguration 		= null;
			if (validateApproval != null && !validateApproval.isEmpty()) {

				model.put("ApprovedValidate", true);

				return new ModelAndView("redirect:/Fuel/1.in", model);
			}
				
				else {
						
				try {
					Fuel	fuel	= fuelService.getFuel(Fuel_id, userDetails.getCompany_id());
					tripConfiguration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
					
					if(fuel.getFuel_TripsheetID() != null && fuel.getFuel_TripsheetID() > 0 && TripSheetService.getLimitedTripSheetDetails(fuel.getFuel_TripsheetID()).getTripStutesId() == TripSheetStatus.TRIP_STATUS_ACCOUNT_CLOSED) {
						model.put("tripAccClosed", true);
						return new ModelAndView("redirect:/Fuel/1.in", model);
					}
					
					ValueObject		valueObject	= new ValueObject();
					valueObject.put("fuel", fuel);
					valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					valueObject.put("txnAmount", fuel.getFuel_amount());
					valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(fuel.getFuel_date()));
					valueObject.put("txnTypeId", fuel.getFuel_id());
					valueObject.put("vid", fuel.getVid());
					valueObject.put("companyId", fuel.getCompanyId());
				
					VehicleDto	CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(fuel.getVid(), fuel.getCompanyId());
					ValueObject	ownerShipObject	= null;
					if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED && fuel.getFuel_amount() > 0){
						ownerShipObject	= new ValueObject();
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, fuel.getFuel_id());
						ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, fuel.getVid());
						ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, fuel.getFuel_amount());
						ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
						ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_FUEL_ENTRY);
						ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, fuel.getCompanyId());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, fuel.getFuel_date());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Fuel Entry");
						ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Fuel Number : "+fuel.getFuel_Number());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, fuel.getFuel_comments());
						ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, fuel.getCreatedById());
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, fuel.getFuel_amount());
						
						
						vehicleAgentIncomeExpenseDetailsService.deleteVehicleAgentIncomeExpense(ownerShipObject);
					}
					
					if(CheckVehicleStatus.getVehicle_Odometer().equals(fuel.getFuel_meter())) { 
						VehicleOdometerHistory odometerHistoryLsit	= vehicleOdometerHistoryService.getVehicleOdometerHistoryByVidExceptCurrentEntry(fuel.getFuel_id(),fuel.getVid(), userDetails.getCompany_id());
						if(odometerHistoryLsit != null ) {
							if(odometerHistoryLsit.getVehicle_Odometer() < fuel.getFuel_meter()) { //  found either pre entry or post entry 
								vehicleService.updateCurrentOdoMeter(fuel.getVid(), odometerHistoryLsit.getVehicle_Odometer(), userDetails.getCompany_id());
								vehicleOdometerHistoryService.deleteVehicleOdometerHistory(fuel.getFuel_id(), userDetails.getCompany_id());
							}
						
						}
						
					}
					
					fuelService.deleteFuel(Fuel_id, toDate,userDetails.getId(),userDetails.getCompany_id());
					
					if ((boolean) tripConfiguration.getOrDefault("saveDriverLedgerDetails", false) 
							&& fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
						ValueObject driveLedgerObj = new ValueObject();

						driveLedgerObj.put("companyId", userDetails.getCompany_id());
						driveLedgerObj.put("userId", userDetails.getId());
						driveLedgerObj.put("driverId", fuel.getDriver_id());
						driveLedgerObj.put("amount", - fuel.getFuel_amount());
						driveLedgerObj.put("transactionId", fuel.getFuel_TripsheetID());
						driveLedgerObj.put("txnType", DriverLedgerTypeConstant.FUEL_ENTRY_DELETE);
						driveLedgerObj.put("subTransactionId", fuel.getFuel_id());
						driveLedgerObj.put("description", "Fuel Entry Delete On Vehicle : " + CheckVehicleStatus.getVehicle_registration()
								+ " For Fuel Number : " + fuel.getFuel_Number());

						driverLedgerService.addDriverLedgerDetails(driveLedgerObj);
					}
					
					
					FuelStockDetails	stockDetails	=  fuelInvoiceService.getFuelStockDetailsByPetrolPumpId(fuel.getVendor_id(), userDetails.getCompany_id());
					
					if(stockDetails != null) {
						Double stock  = stockDetails.getStockQuantity() + fuel.getFuel_liters();
						Double amount = stockDetails.getTotalFuelCost() + fuel.getFuel_amount();
						Double rate   = amount/stock;
						fuelInvoiceService.updateFuelStockDetails(fuel.getVendor_id(), rate, fuel.getFuel_liters(), fuel.getFuel_amount());
						if((boolean) configuration.getOrDefault("getStockFromInventory", false) && fuel.getFuelInvoiceId() > 0) {
							ValueObject		fuelInvoiceObject	= new ValueObject();
							fuelInvoiceObject.put("stockTypeId", FuelInvoiceConstant.STOCK_TYPE_FE_DELETE);
							fuelInvoiceObject.put("updatedStock", fuel.getFuel_liters());
							fuelInvoiceObject.put("fuelInvoiceId", fuel.getFuelInvoiceId());
							fuelInvoiceObject.put("remark", FuelInvoiceConstant.getStockType(FuelInvoiceConstant.STOCK_TYPE_FE_DELETE));
							fuelInvoiceService.updateFuelStockDetailsInFuelInvoice(fuelInvoiceObject);
						}
					}
					if(fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
						pendingVendorPaymentService.deletePendingVendorPayment(fuel.getFuel_id(), PendingPaymentType.PAYMENT_TYPE_FUEL_ENTRIES);
					}
					
					if(fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
						System.err.println("inside cash ");
						cashPaymentService.deleteCashStateMentEntry(fuel.getFuel_id(), ModuleConstant.FUEL_ENTRY, userDetails.getCompany_id(),userDetails.getId(),false);
					}
					if(fuel.getPaymentTypeId() >=0  && (fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CASH || fuel.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT)) {
						System.err.println("inside bankpayment");
						bankPaymentService.delete_Bank_PaymentDetails(fuel.getFuel_id(), ModuleConstant.FUEL_ENTRY,userDetails.getCompany_id(), userDetails.getId());
					}
					
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
					System.err.println("Exception : "+e);
					model.put("errorFuel", true);
					return new ModelAndView("redirect:/Fuel/1.in", model);
				}
				model.put("deleteFuel", true);
			}
		} catch (Exception e1) {
			LOGGER.error("Fuel Page:", e1);
			e1.printStackTrace();
			return new ModelAndView("redirect:/Fuel/1.in?danger=true");
		}

		return new ModelAndView("redirect:/Fuel/1.in?delete=true");
	}

	// save Issues in databases
	@RequestMapping(value = "/deleteVehicleDetailsFuel")
	public ModelAndView vehicledeleteFuel(@RequestParam("vid") final Integer Vehicle_Id,
			@RequestParam("FID") final Long Fuel_id, final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// validate the Fuel approval status or not checking the the
			// validate
			List<Fuel> validateApproval = fuelService.getFuelValidate_APPROVAL_Status(Fuel_id, userDetails.getCompany_id());

			if (validateApproval != null && !validateApproval.isEmpty()) {

				model.put("ApprovedValidate", true);

				return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + Vehicle_Id + "", model);

			} else {
				try {
					java.util.Date    currentDateUpdate =  new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
					// get the issues Dto to save issues
					Fuel fuel = fuelService.getFuel(Fuel_id, userDetails.getCompany_id());
					
					ValueObject		valueObject	= new ValueObject();
					valueObject.put("fuel", fuel);
					valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					valueObject.put("txnAmount", fuel.getFuel_amount());
					valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(fuel.getFuel_date()));
					valueObject.put("txnTypeId", fuel.getFuel_id());
					valueObject.put("vid", fuel.getVid());
					valueObject.put("companyId", fuel.getCompanyId());
					
					Fuel fueltoDelete = DeleteTosaveFuel(fuel);
					
					

					fuelService.deleteFuel(fueltoDelete.getFuel_id(),toDate,userDetails.getId(), userDetails.getCompany_id());
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
					model.put("errorFuel", true);
					return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + Vehicle_Id + "", model);
				}
				model.put("deleteFuel", true);
			}
		} catch (Exception e) {
			LOGGER.error("Fuel Page:", e);
			e.printStackTrace();

		}
		// return new ModelAndView("Show_Vehicle_Fuel", model);
		return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + Vehicle_Id + "", model);
	}

	@RequestMapping(value = "/editVehicleDetailsFuel")
	public ModelAndView editVehicleDetailsFuel(@RequestParam("vid") final Integer Vehicle_Id,
			@RequestParam("FID") final Long Fuel_id, final HttpServletResponse result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// validate the Fuel approval status or not checking the the
			// validate
			List<Fuel> validateApproval = fuelService.getFuelValidate_APPROVAL_Status(Fuel_id, userDetails.getCompany_id());

			if (validateApproval != null && !validateApproval.isEmpty()) {

				model.put("ApprovedValidate", true);

				return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + Vehicle_Id + "", model);
			} else {
				try {
					//
					model.put("fuel", FuBL.getFuelId(fuelService.getFuelDetails(Fuel_id, userDetails.getCompany_id())));

				} catch (Exception e) {
					LOGGER.error("Fuel Page:", e);
					return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + Vehicle_Id + "", model);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Fuel Page:", e);
			e.printStackTrace();

		}
		return new ModelAndView("Fuel_EditVehicleDetails", model);
	}

	// save Fuel in databases
	@RequestMapping(value = "/updateVehicleDetailsFuel", method = RequestMethod.POST)
	public ModelAndView UpdateVehicleDetailsFuel(@ModelAttribute("command") FuelDto fuelDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			// get the issues Dto to save issues
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Fuel fuel = EDITTOsaveFuel(fuelDto);
			fuel.setCompanyId(userDetails.getCompany_id());
			/*
			 * // get the issues Dto to save issues Fuel fuel =
			 * EDIT_Full_UpdateFuel(fuelDto);
			 */

			try {
				if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
					try {
						java.util.Date currentDate = new java.util.Date();
						DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
						java.util.Date dateTo = dateFormatTime.parse(ft.format(currentDate));
						java.sql.Date toDate = new java.sql.Date(dateTo.getTime());
						fuel.setFuel_vendor_paymentdate(toDate);
					} catch (ParseException e) {

						e.printStackTrace();
					}

				} else {
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
				}

				try {
					// save to fuel entry in databases
					java.util.Date currentUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentUpdate.getTime());
					fuel.setCreated(toDate);
					fuel.setLastupdated(toDate);
					// fuel.setCreatedBy(userDetails.getEmail());
					fuel.setCreatedById(userDetails.getId());
					// fuel.setLastModifiedBy(userDetails.getEmail());
					fuel.setLastModifiedById(userDetails.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}

				fuel.setMarkForDelete(false);
				// save to fuel entry in databases
				// fuelService.addFuel(fuel);
				fuelService.updateFuel(fuel);
				// get the vehicle name to all

				try {
					Integer CurrentOdometer = vehicleService
							.updateCurrentOdoMeterGETVehicleToCurrentOdometer(fuel.getVid());
					if (CurrentOdometer < fuel.getFuel_meter()) {
						vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), userDetails.getCompany_id());

						// update current Odometer update Service Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),
								fuel.getFuel_meter(), userDetails.getCompany_id());
						vehicleOdometerHistoryService.updateVehicleOdometerHistory(fuel.getFuel_meter(),
								fuel.getFuel_id());

					} else {
						vehicleOdometerHistoryService.updateVehicleOdometerHistory(fuel.getFuel_meter(),
								fuel.getFuel_id());
					}

				} catch (Exception e) {
					LOGGER.error("Fuel Page:", e);

				}
				// show the driver list in all
				model.put("saveFuel", true);

			} catch (Exception e) {
				LOGGER.error("Fuel Page:", e);
				e.printStackTrace();
				model.put("duplicateFuelEntries", true);
				return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + fuel.getVid() + "", model);

			}
		} catch (Exception e1) {
			LOGGER.error("Fuel Page:", e1);
			e1.printStackTrace();
			model.put("duplicateFuelEntries", true);
			return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + fuelDto.getVid() + "", model);
		}

		// return new ModelAndView("Show_Vehicle_Fuel", model);
		return new ModelAndView("redirect:/VehicleFuelDetails/1.in?vid=" + fuelDto.getVid() + "", model);
	}

	// ***============================================================================================================

	@SuppressWarnings("unused")
	@RequestMapping(value = "/importFuel", method = RequestMethod.POST)
	public ModelAndView saveImport(@ModelAttribute("command") VehicleDocument photo,
			@RequestParam("import") MultipartFile file, HttpServletRequest request) throws Exception {
		CustomUserDetails userDetails = null;
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> model = new HashMap<String, Object>();
		FuelSequenceCounter sequenceCounter = null;
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		List<ServiceReminderDto>		serviceList					= null;
		// List<VehicleOwnerShip> vehicleOwnerShipList =
		// VehicleOwnerShip.getVehicleOwerShipList();
		List<PaymentTypeConstant> paymentTypeConstantList = PaymentTypeConstant.getPaymentTypeList();
		List<VehicleFuelType> fuelTypes = VehicleFuelType.getVehicleFuelTypeList();

		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());

		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
				int i;
				// write file to server
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			/*
			 * model.put("msg", "failed to process file because : " + e.getMessage());
			 * return "mainpage";
			 */
			e.printStackTrace();
		}

		// Count How many Import SuccessFully
		int CountSuccess = 0;
		int CountDuplicate = 0;
		int CountDuplicateOdameter = 0;
		
		 List<VendorDto> vendorList	= vendorService.findAllVendorList(userDetails.getCompany_id());
		
		Map<String, VendorDto> vendorHm =
				vendorList.stream().collect(Collectors.toMap(VendorDto::getVendorName,
			                                              Function.identity()));

		String[] nextLine;
		try {
			// read file
			// CSVReader(fileReader, ';', '\'', 1) means
			// using separator ; and using single quote ' . Skip first line when
			// read

			try (FileReader fileReader = new FileReader(serverFile);
					CSVReader reader = new CSVReader(fileReader, ';', '\'', 1);) {
				while ((nextLine = reader.readNext()) != null) {

					for (int i = 0; i < nextLine.length; i++) {
						Fuel fuel = new Fuel();
						try {
							// System.out.println(nextLine[i]);
							String[] importFuel = nextLine[i].split(",");

							Integer vid = null;
							Integer VehicleOddmeter = null;
							String VehicleFuelType = null;
							String VehicleFuelGroup = null;
							String VehicleOwnerShipType = null;
							long vehicleGroupId = 0;

							try {

								if (importFuel != null && importFuel.length != 0) {
									// getting import CSV file column 1
									String Vehicle_registration = importFuel[0];

									VehicleDto vehiclename = (vehicleService.getVehicel_Details_Fuel_Import(
											Vehicle_registration, userDetails.getCompany_id()));
									// show Vehicle name
									if (vehiclename != null) {
										// System.out.println(vehicle.getVehicle_registration());
										vid = vehiclename.getVid();
										VehicleOddmeter = vehiclename.getVehicle_Odometer();
										// VehicleFuelType = vehiclename.getVehicle_Fuel();
										// VehicleFuelGroup = vehiclename.getVehicle_Group();
										VehicleOwnerShipType = VehicleOwnerShip
												.getVehicleMeterUnitName(vehiclename.getVehicleOwnerShipId());
										vehicleGroupId = vehiclename.getVehicleGroupId();

										fuel.setVid(vid);
										// fuel.setVehicle_registration(Vehicle_registration);
										// fuel.setVehicle_Ownership(VehicleOwnerShipType);
										// fuel.setVehicle_OwnershipId(vehiclename.getVehicleOwnerShipId());
										// fuel.setVehicleGroupId(vehicleGroupId);
										// fuel.setVehicle_group(VehicleFuelGroup);
										try {
											// fuel date converted String to
											// date
											// Format
											// getting import CSV file column 2
											// date
											String importdate = new String(importFuel[1]);
											java.util.Date date = dateFormat.parse(importdate.replaceAll("[/ .]", "-"));
											java.sql.Date FuelDate = new Date(date.getTime());
											fuel.setFuel_date(FuelDate);
										} catch (Exception e) {
											LOGGER.error("Fuel Page:", e);
											String character = "" + Vehicle_registration;
											String errordate = "Vehicle =";
											errordate = errordate + character;
											model.put("dateError", errordate);

											e.printStackTrace();
										}

										if (fuel.getFuel_date() != null) {

											// getting import CSV file column 3
											// odd
											// meter
											fuel.setFuel_meter(Integer.parseInt(importFuel[2]));
											// get enter the fuel meter
											// //getting
											// import
											// CSV file column 3 odd meter
											Integer fuelmeterNow = Integer.parseInt(importFuel[2]);

											try {
												List<Fuel> validate = fuelService.listFuelValidate(vid, fuelmeterNow,
														Double.parseDouble(importFuel[3]),
														Double.parseDouble(importFuel[4]), importFuel[7],
														importFuel[9], userDetails.getCompany_id());

												if (validate != null && !validate.isEmpty()) {
													// show the driver list in
													// all
													model.put("duplicateFuelEntries", true);

												} else {

													// import file missing
													// oddmeter
													// that
													// go else loop
													if (VehicleOddmeter < fuelmeterNow) {

														/***************************************************/
														/*
														 * fuel entry grenthen value off old this true
														 */
														/***************************************************/
														// System.out.println(VehicleOddmeter
														// <=
														// fuelmeterNow);

														fuel.setFuel_meter_old(VehicleOddmeter);
														/*
														 * save to usage km is vehicle
														 */
														// get old meter
														Integer fuelmeterOld = VehicleOddmeter;
														// get usage fuel
														// calculation
														Integer usagefuel = (fuelmeterNow - fuelmeterOld);
														// save usage
														fuel.setFuel_usage(usagefuel);
														/*
														 * save to amount of fuel enter
														 */
														// get fuel volume
														// //getting
														// import CSV
														// file column 4 liter
														Double fuelliter = Double.parseDouble(importFuel[3]);

														// get fuel price
														// //getting
														// import CSV
														// file column 5 fuel
														// price
														Double fuelprice = Double.parseDouble(importFuel[4]);
														// get amount of fuel
														// calculation
														Double amountfuel = fuelliter * fuelprice;
														// save usage
														fuel.setFuel_amount(round(amountfuel, 2));
														/*
														 * save to km/L of fuel enter
														 */
														// getting import CSV
														// file
														// column 6 fuel
														// Tank
														Integer tank = 1;

														if (importFuel[5].equalsIgnoreCase("Full")) {
															tank = 0;
														}

														if (tank == 0) {
															// get Vehicle id to
															// Vehicle
															// name

															Integer vehicleid = vid;

															Integer usagePartial = 0;
															Double litersPartial = 0.0;
															Double AmountPartial = 0.0;

															try {

																// search time
																// full
																// tank stop the
																// loop
																List<Fuel> fuelkmDESC = fuelService
																		.findMissingOddmeterFirstDESC_Vaule_Liter_Amount(
																				vehicleid, fuelmeterNow,
																				fuel.getFuel_date());
																if (fuelkmDESC != null && !fuelkmDESC.isEmpty()) {
																	for (Fuel fuelDtoDESC : fuelkmDESC) {
																		// System.out.println("coming
																		// for
																		// loop");
																		// get
																		// the
																		// Fuel_tank_partial
																		// value
																		// and
																		// equal
																		// to
																		// one
																		// or
																		// not
																		if (fuelDtoDESC.getFuel_tank_partial() == 1) {

																			usagePartial += fuelDtoDESC.getFuel_usage();
																			litersPartial += fuelDtoDESC
																					.getFuel_liters();
																			AmountPartial += fuelDtoDESC
																					.getFuel_amount();

																		} else {
																			// System.out.println("empty
																			// FirstDESC_Vaule_Liter_Amount");
																			break; // stop
																					// the
																					// loop
																					// when
																					// Tank_partial
																					// value
																					// ==
																					// 0
																		}
																	}
																}

																Integer usagefuelTotal = usagePartial + usagefuel;
																Double literTotal = litersPartial
																		+ (Double.parseDouble(importFuel[3]));

																Double km = (usagefuelTotal / literTotal);

																// Double
																// KmDismal=new
																// BigDecimal(km).setScale(2,
																// BigDecimal.ROUND_HALF_UP).doubleValue();

																fuel.setFuel_kml(round(km, 2));

																Double amountfuelTotal = round(AmountPartial, 2)
																		+ round(amountfuel, 2);

																Double cost = (amountfuelTotal / usagefuelTotal);

																// Double
																// costDismal=new
																// BigDecimal(cost).setScale(2,
																// BigDecimal.ROUND_HALF_UP).doubleValue();

																if (km == 0.0) {
																	cost = 0.0;
																}

																fuel.setFuel_cost(round(cost, 2));

															} catch (Exception e) {

																Integer usagefuelTotal = usagePartial + usagefuel;
																Double literTotal = litersPartial
																		+ (Double.parseDouble(importFuel[3]));

																Double km = (usagefuelTotal / literTotal);

																// Double
																// KmDismal=new
																// BigDecimal(km).setScale(2,
																// BigDecimal.ROUND_HALF_UP).doubleValue();

																fuel.setFuel_kml(round(km, 2));

																try {
																	Double amountfuelTotal = round(AmountPartial, 2)
																			+ round(amountfuel, 2);

																	Double cost = (amountfuelTotal / usagefuelTotal);

																	// Double
																	// costDismal=new
																	if (km == 0.0) {
																		cost = 0.0;
																	}
																	fuel.setFuel_cost(round(cost, 2));

																} catch (Exception e1) {
																	// show the
																	// Fuel
																	// Odd
																	// meter
																	// already
																	model.put("alreadyOddmeter", Vehicle_registration);
																	model.put("OddmeterError", true);
																	// show the
																	// Fuel
																	// List
																	model.put("fuel", FuBL.prepareListofFuel(fuelService
																			.listFuelEntries(1, userDetails)));
																	// show the
																	// driver
																	// list in
																	// all
																	model.put("saveFuel", true);
																	return new ModelAndView("Fuel", model);
																}
															}

														} // tank full close

														fuel.setFuel_meter_attributes(0);
														// fuel.setFuel_type(importFuel[6]);
														if (fuelTypes != null) {
															for (VehicleFuelType fuelType : fuelTypes) {
																if (fuelType.getVehicleType()
																		.equalsIgnoreCase(importFuel[6])) {
																	fuel.setFuelTypeId(fuelType.getVehicleFuelTypeId());
																	break;

																}
															}

														}
														// fuel.setVehicle_group(VehicleFuelGroup);
														// fuel.setVehicleGroupId(vehicleGroupId);
														// getting import CSV
														// file
														// column 4
														// liter
														fuel.setFuel_liters(Double.parseDouble(importFuel[3]));
														// getting import CSV
														// file
														// column 5
														// price
														fuel.setFuel_price(Double.parseDouble(importFuel[4]));

														// get Import Vender
														// name to
														// DB vendor ID
														Vendor vendorDto = vendorService
																.GetAllVendorListToFuelImportVendorID(importFuel[7],
																		importFuel[8], userDetails.getCompany_id());
														if (vendorHm.get(importFuel[7].trim()) != null) {

															fuel.setVendor_id(vendorHm.get(importFuel[7].trim()).getVendorId());

														} else {
															fuel.setVendor_id(fuelService.saveFuelEntriesToNewVendorCreateAuto(
																	importFuel[7] + "-" + importFuel[8], userDetails.getCompany_id(),userDetails.getId()));
														}

														// getting import CSV
														// file
														// column 9
														// Reference number
														fuel.setFuel_reference(importFuel[9]);

														// fuel.setDriver_id(fuelDto.getDriver_id());
														Driver driverDto2 = driverService
																.GetDriverEmpNumberToName(importFuel[10]);
														if (driverDto2 != null) {

															fuel.setDriver_id(driverDto2.getDriver_id());
															// getting import
															// CSV
															// file column 10
															// Driver name
															// fuel.setDriver_name(driverDto2.getDriver_firstname());
															// fuel.setDriver_empnumber(driverDto2.getDriver_empnumber());

														} else {
															fuel.setDriver_id(0);
															// getting import
															// CSV
															// file
															// column 10
															// Driver name
															// fuel.setDriver_name(importFuel[10]);
														}

														String FuelTripSheet = "" + importFuel[11];
														if (FuelTripSheet != null && FuelTripSheet != "") {

															fuel.setFuel_TripsheetID(fuelService.getTripSheetIdByNumber(
																	Long.parseLong(FuelTripSheet.replace("TS-", ""))));
														} else {
															fuel.setFuel_TripsheetID((long) 0);
														}
														// getting import CSV
														// file
														// column 11
														// Payment in cash or
														// credit
														// fuel.setFuel_payment(importFuel[12]);
														if (paymentTypeConstantList != null) {
															for (PaymentTypeConstant constant : paymentTypeConstantList) {
																if (constant.getPaymentTypeName().equalsIgnoreCase(importFuel[12].trim())) {
																	fuel.setPaymentTypeId(constant.getPaymentTypeId());
																	break;
																}
															}
														}
														fuel.setFuel_personal(0);

														// getting import CSV
														// file
														// column 6 fuel
														// Tank
														fuel.setFuel_tank(tank);
														// set the tank value of
														// partial
														// or not
														fuel.setFuel_tank_partial(tank);
														try {

															if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
																// fuel.setFuel_vendor_paymode("NOTPAID");
																fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);																
															} else {
																// fuel.setFuel_vendor_paymode("PAID");
																fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
																try {
																	java.util.Date currentDateUpdate = new java.util.Date();
																	Timestamp toDate = new java.sql.Timestamp(
																			currentDateUpdate.getTime());
																	fuel.setFuel_vendor_paymentdate(toDate);
																} catch (Exception e) {

																	e.printStackTrace();
																}
															}
															try {

																try {
																	// save to
																	// fuel
																	// entry in
																	// databases
																	java.util.Date currentUpdate = new java.util.Date();
																	Timestamp toDate = new java.sql.Timestamp(
																			currentUpdate.getTime());
																	fuel.setCreated(toDate);
																	fuel.setLastupdated(toDate);
																	// fuel.setCreatedBy(userDetails.getEmail());
																	// fuel.setLastModifiedBy(userDetails.getEmail());
																	fuel.setCreatedById(userDetails.getId());
																	fuel.setLastModifiedById(userDetails.getId());
																} catch (Exception e) {
																	e.printStackTrace();
																}

																fuel.setFuel_document(false);
																fuel.setFuel_document_id((long) 0);
																fuel.setMarkForDelete(false);
																// save to fuel
																// entry in
																// databases
																sequenceCounter = fuelSequenceService
																		.findNextFuelNumber(
																				userDetails.getCompany_id());
																fuel.setCompanyId(userDetails.getCompany_id());
																if (sequenceCounter == null) {
																	return new ModelAndView(
																			"redirect:/Fuel/1.in?FuelSequenceCounterMissing=true");
																}
																fuel.setFuel_Number(sequenceCounter.getNextVal());
																fuelService.addFuel(fuel);
																// fuelSequenceService.updateNextFuelNumber(sequenceCounter.getNextVal()
																// + 1, userDetails.getCompany_id(),
																// sequenceCounter.getSequence_Id());

																CountSuccess++;
																// update the
																// Current
																// Odometer
																// vehicle
																// Databases
																// tables
																// odameter
																// grenthen
																// only update
																if (VehicleOddmeter < fuel.getFuel_meter()) {
																	try {
																		vehicleService.updateCurrentOdoMeter(vid,
																				Integer.parseInt(importFuel[2]), userDetails.getCompany_id());

																		// update
																		// current
																		// Odometer
																		// update
																		// Service
																		// Reminder
																		ServiceReminderService
																				.updateCurrentOdometerToServiceReminder(
																						fuel.getVid(),
																						fuel.getFuel_meter(), userDetails.getCompany_id());
																		
																		serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(fuel.getVid(), userDetails.getCompany_id(), userDetails.getId());
																		if(serviceList != null) {
																			for(ServiceReminderDto list : serviceList) {
																				
																				if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
																					
																					ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
																					//emailAlertQueueService.sendEmailServiceOdometer(list);
																					//smsAlertQueueService.sendSmsServiceOdometer(list);
																				}
																			}
																		}
																		
																		
																		VehicleOdometerHistory vehicleUpdateHistory = VOHBL
																				.prepareOdometerGetHistoryToFuel(fuel);
																		vehicleUpdateHistory.setCompanyId(
																				userDetails.getCompany_id());
																		vehicleOdometerHistoryService
																				.addVehicleOdometerHistory(
																						vehicleUpdateHistory);
																	} catch (Exception e) {
																		e.printStackTrace();
																		LOGGER.error("Fuel Page:", e);

																	}
																}
																if (VehicleOddmeter <= fuel.getFuel_meter()) {
																	if (tank == 0) {
																		// save
																		// to
																		// update
																		// partialFuel
																		// entry
																		// in
																		// databases
																		fuelService.updatepartialFuel(vid, tank, userDetails.getCompany_id());

																	}
																}

																if (!(VehicleOddmeter < fuel.getFuel_meter())) {
																	++CountDuplicateOdameter;
																	String DuplicateOda = "Vehicle = "
																			+ Vehicle_registration
																			+ " Odameter Reading = " + VehicleOddmeter;
																	model.put("CountDuplicateOdameter",
																			CountDuplicateOdameter);
																	model.put("Duplicateodameter", DuplicateOda);
																	model.put("OddmeterError", true);
																}

															} catch (final Exception e) {
																++CountDuplicate;
																String Duplicate = "Vehicle = " + Vehicle_registration;
																model.put("CountDuplicate", CountDuplicate);
																model.put("Duplicate", Duplicate);
																model.put("importEmpty", true);
																e.printStackTrace();
															}

														} catch (final Exception e) {
															++CountDuplicate;
															String Duplicate = "Vehicle = " + Vehicle_registration;
															model.put("CountDuplicate", CountDuplicate);
															model.put("Duplicate", Duplicate);
															model.put("importEmpty", true);
															e.printStackTrace();
														}

														// this else this
														// greanthan
														// value of
														// false the calculation
													} else {

														/****************************************************
														 * fuel entry less then value off old this false
														 ****************************************************/

														// System.out.println("in
														// the
														// value="+(fuelDto.getFuel_meter_old()
														// <=
														// fuelDto.getFuel_meter()));
														/*
														 * save to usage km is vehicle
														 */

														/*
														 * save to amount of fuel enter
														 */
														// get fuel volume
														// //getting
														// import CSV
														// file column 4 liter
														Double fuelliter = Double.parseDouble(importFuel[3]);

														// get fuel price
														// //getting
														// import CSV
														// file column 5 fuel
														// price
														Double fuelprice = Double.parseDouble(importFuel[4]);
														// get amount of fuel
														// calculation
														Double amountfuel = fuelliter * fuelprice;
														// save usage
														fuel.setFuel_amount(round(amountfuel, 2));

														/*
														 * save to km/L of fuel enter
														 */
														// getting import CSV
														// file
														// column 6 fuel
														// Tank
														Integer tank = 1;
														if (importFuel[5].equalsIgnoreCase("Full")) {
															tank = 0;
														}

														Integer usagePartialMISSING = 0;
														Double litersPartialMISSING = 0.0;
														Double AmountPartialMISSING = 0.0;

														Long FuellFullTank_ID = null;

														Integer OldFullTank_OpringKM = null;

														Long PartialFuel_Tank_ID = null;
														Integer OldPartialTank_closingKM = null;

														try {

															List<Fuel> fuelkmDESC = fuelService
																	.findMissingOddmeterFirstDESC_Vaule_Liter_Amount(
																			vid, fuelmeterNow, fuel.getFuel_date());
															if (fuelkmDESC != null && !fuelkmDESC.isEmpty()) {
																for (Fuel fuelDtoDESC : fuelkmDESC) {

																	// get the
																	// Fuel_tank_partial
																	// value and
																	// equal
																	// to
																	// one or
																	// not
																	if (fuelDtoDESC.getFuel_tank_partial() == 1) {

																		usagePartialMISSING += fuelDtoDESC
																				.getFuel_usage();
																		litersPartialMISSING += fuelDtoDESC
																				.getFuel_liters();
																		AmountPartialMISSING += fuelDtoDESC
																				.getFuel_amount();

																	} else {
																		break;
																	}
																}
															}

															List<Fuel> fuelkmASC_Update = fuelService
																	.findMissingOddmeterSecondASC_Vaule_Liter_Amount(
																			vid, fuelmeterNow, fuel.getFuel_date());
															if (fuelkmASC_Update != null
																	&& !fuelkmASC_Update.isEmpty()) {
																for (Fuel fuel2 : fuelkmASC_Update) {

																	if (fuel2.getFuel_tank_partial() == 1) {

																		OldFullTank_OpringKM = fuel2.getFuel_meter();
																		// System.out.println(OldFullTank_OpringKM);

																		PartialFuel_Tank_ID = fuel2.getFuel_id();
																		OldPartialTank_closingKM = fuel2
																				.getFuel_meter();

																		fuelService
																				.update_Missing_OddMeter_Usage_Partial(
																						fuelmeterNow,
																						OldPartialTank_closingKM
																								- fuel.getFuel_meter(),
																						PartialFuel_Tank_ID, userDetails.getCompany_id());

																		break;
																	} else {
																		OldFullTank_OpringKM = fuel2.getFuel_meter();

																		PartialFuel_Tank_ID = fuel2.getFuel_id();
																		OldPartialTank_closingKM = fuel2
																				.getFuel_meter();
																		// before
																		// enter
																		// update
																		// usage
																		// and
																		// opening
																		// oddmeter
																		fuelService
																				.update_Missing_OddMeter_Usage_Partial(
																						fuelmeterNow,
																						OldPartialTank_closingKM
																								- fuel.getFuel_meter(),
																						PartialFuel_Tank_ID, userDetails.getCompany_id());

																		break;
																	}
																}
															}

															List<Fuel> fuelkmASC = fuelService
																	.findMissingOddmeterSecondASC_Vaule_Liter_Amount(
																			vid, fuelmeterNow, fuel.getFuel_date());
															if (fuelkmASC != null && !fuelkmASC.isEmpty()) {
																for (Fuel fuelDtoASC : fuelkmASC) {
																	/*
																	 * if enter the missing fuel tank is full not
																	 * calution before enter don't cal
																	 */
																	if (tank != 0) {

																		if (fuelDtoASC.getFuel_tank_partial() == 1) {

																			OldFullTank_OpringKM = fuelDtoASC
																					.getFuel_meter();

																			usagePartialMISSING += fuelDtoASC
																					.getFuel_usage();
																			litersPartialMISSING += fuelDtoASC
																					.getFuel_liters();
																			AmountPartialMISSING += fuelDtoASC
																					.getFuel_amount();

																		} else {
																			OldFullTank_OpringKM = fuelDtoASC
																					.getFuel_meter();
																			usagePartialMISSING += fuelDtoASC
																					.getFuel_usage();
																			litersPartialMISSING += fuelDtoASC
																					.getFuel_liters();
																			AmountPartialMISSING += fuelDtoASC
																					.getFuel_amount();

																			FuellFullTank_ID = fuelDtoASC.getFuel_id();
																			break;
																		}
																	} else {

																		FuellFullTank_ID = fuelDtoASC.getFuel_id();
																		OldFullTank_OpringKM = fuelDtoASC
																				.getFuel_meter();
																		FuellFullTank_ID = fuelDtoASC.getFuel_id();
																		break;
																	}
																}
															}

															// get old meter
															Integer fuelmeterOld = 0;

															// get opening
															// oddmeter
															// in
															// enter
															// oddmeter value
															List<Fuel> fuelkmDESC_OPEN_ODDMeter = fuelService
																	.findMissingOddmeterFirstDESC_Vaule_Liter_Amount(
																			vid, fuelmeterNow, fuel.getFuel_date());
															if (fuelkmDESC_OPEN_ODDMeter != null
																	&& !fuelkmDESC_OPEN_ODDMeter.isEmpty()) {
																for (Fuel fuelDtoDESC_OPEN : fuelkmDESC_OPEN_ODDMeter) {

																	fuelmeterOld = fuelDtoDESC_OPEN.getFuel_meter();

																	break;
																	// when
																	// Tank_partial
																	// value ==
																	// 0

																}

															}

															if (OldFullTank_OpringKM == null) {
																OldFullTank_OpringKM = 0;
															}

															//
															Integer OldFullTank_OpringKM_Usage = (OldFullTank_OpringKM
																	- fuel.getFuel_meter());
															// after missing
															// oddmter
															// after get the
															// value
															// of
															// that fuel
															// enterties values
															Integer OldFull_AfterMissingMeterOpring = (OldFullTank_OpringKM)
																	- (OldFullTank_OpringKM_Usage);

															// get usage fuel
															// calculation
															Integer usagefuel = (fuelmeterNow - fuelmeterOld);
															// save usage
															fuel.setFuel_usage(usagefuel);

															// save Opeing Km
															// Meter
															fuel.setFuel_meter_old(fuelmeterOld);

															// getting import
															// CSV
															// file
															// column
															// fuel Tank
															// Why tank i am
															// make
															// zero
															// meaning import is
															// missing
															// oddmeter in
															// not make zero
															// this
															// also
															// calculation doing
															// greater
															// then partial
															// value
															fuel.setFuel_tank(0);

															// set the tank
															// value of
															// partial or not
															fuel.setFuel_tank_partial(tank);
															// tank full mean
															// update
															// that full
															// value row not
															// full
															// search
															// for
															/*****************************************
															 * Import FUll Tank
															 *************************************/
															// full tank entries
															// in
															// full
															// entries
															if (tank == 0) {

																// Missing Tank
																// Full
																// update the
																// oddmeter in
																// same
																// full
																// id that
																// calculation

																Integer usagefuelTotal = usagePartialMISSING
																		+ usagefuel;
																Double literTotal = litersPartialMISSING + (fuelliter);

																Double km = (usagefuelTotal / literTotal);

																// Double
																// KmDismal=new
																// BigDecimal(km).setScale(2,
																// BigDecimal.ROUND_HALF_UP).doubleValue();

																fuel.setFuel_kml(round(km, 2));

																Double amountfuelTotal = round(AmountPartialMISSING, 2)
																		+ round(amountfuel, 2);

																Double cost = (amountfuelTotal / usagefuelTotal);

																// Double
																// costDismal=new
																// BigDecimal(cost).setScale(2,
																// BigDecimal.ROUND_HALF_UP).doubleValue();
																if (km == 0.0) {
																	cost = 0.0;
																}
																// System.out.println("cost"+cost);
																fuel.setFuel_cost(round(cost, 2));

																fuelService.update_Missing_OddMeter_Usage_Partial(
																		fuel.getFuel_meter(),
																		OldFullTank_OpringKM - fuel.getFuel_meter(),
																		FuellFullTank_ID, userDetails.getCompany_id());

																/******************************************************************/
																// this code
																// check
																// get
																// Old Full tank
																// value
																// of the cost
																// and
																// km/l
																// cost update
																Long OldFUELL_id = (long) 0;
																Integer usagePartialOldFUELL = 0;
																Double litersPartialOldFUELL = 0.0;
																Double AmountPartialOldFUELL = 0.0;

																List<Fuel> fuelkmASC_updateOldFuel_cost = fuelService
																		.findMissingOddmeterSecondASC_Vaule_Liter_Amount(
																				vid, fuelmeterNow, fuel.getFuel_date());
																if (fuelkmASC_updateOldFuel_cost != null
																		&& !fuelkmASC_updateOldFuel_cost.isEmpty()) {

																	for (Fuel fuelDtoASC_oldfull : fuelkmASC_updateOldFuel_cost) {
																		// System.out.println("coming
																		// for
																		// loop");
																		// System.out.println("Fuel
																		// id in
																		// Zore="
																		// +
																		// fuelDtoASC_oldfull.getFuel_id());

																		// get
																		// the
																		// Fuel_tank_partial
																		// value
																		// and
																		// equal
																		// to
																		// one
																		// or
																		// not
																		if (fuelDtoASC_oldfull
																				.getFuel_tank_partial() == 1) {

																			// OldFullTank_OpringKM
																			// =
																			// fuelDtoASC.getFuel_meter();
																			// System.out.println("Import
																			// old
																			// full
																			// tank"+OldFullTank_OpringKM);
																			usagePartialOldFUELL += fuelDtoASC_oldfull
																					.getFuel_usage();
																			litersPartialOldFUELL += fuelDtoASC_oldfull
																					.getFuel_liters();
																			AmountPartialOldFUELL += fuelDtoASC_oldfull
																					.getFuel_amount();

																		} else {

																			usagePartialOldFUELL += fuelDtoASC_oldfull
																					.getFuel_usage();
																			litersPartialOldFUELL += fuelDtoASC_oldfull
																					.getFuel_liters();
																			AmountPartialOldFUELL += fuelDtoASC_oldfull
																					.getFuel_amount();
																			// System.out.println("Import
																			// Fuel
																			// id
																			// in
																			// Zore="
																			// +
																			// fuelDtoASC_oldfull.getFuel_id());

																			OldFUELL_id = fuelDtoASC_oldfull
																					.getFuel_id();
																			// System.out.println("Import
																			// asc
																			// old
																			// IF="+OldFUELL_id);
																			break; // stop
																			// the
																			// loop
																			// when
																			// Tank_partial
																			// value
																			// ==
																			// 0
																		}

																	} // for
																		// loop
																		// close
																}

																// this update
																// Missing
																// oddmeter to
																// add
																// in
																// Next Fuel
																// entries
																// Full
																// calculation
																// cost
																// and km/L

																Double KM_OLdNextFULL = (usagePartialOldFUELL
																		/ litersPartialOldFUELL);

																// System.out.println("OLD
																// Fuel FUELL
																// KM/L
																// ="+
																// KM_OLdNextFULL);

																Double cost_OLdNextFULL = (AmountPartialOldFUELL
																		/ usagePartialOldFUELL);

																if (KM_OLdNextFULL == 0.0) {
																	cost_OLdNextFULL = 0.0;
																}

																fuelService.update_Missing_FuelNextFull_KM_cost(
																		OldFUELL_id, round(KM_OLdNextFULL, 2),
																		round(cost_OLdNextFULL, 2), userDetails.getCompany_id());

															} else {

																/*****************************************
																 * Import Partial Tank in missing odd
																 *************************************/
																/*********************************/
																/*
																 * Tank partial missing oddmeter
																 */

																/*
																 * usagePartialMISSING += OldFullTank_OpringKM_Usage;
																 */

																// this update
																// Missing
																// oddmeter
																// calculation
																// that
																// id
																Integer usagefuelTotal = usagePartialMISSING
																		+ usagefuel;
																Double literTotal = litersPartialMISSING + (fuelliter);

																Double km = (usagefuelTotal / literTotal);

																// System.out.println("Partial
																// Feul KM/L ="+
																// km);

																Double amountfuelTotal = round(AmountPartialMISSING, 2)
																		+ round(amountfuel, 2);

																Double cost = (amountfuelTotal / usagefuelTotal);

																// System.out.println("Partial
																// Feul value
																// cost="+
																// (double)Math.round(cost));

																if (km == 0.0) {
																	cost = 0.0;
																}

																fuelService.update_Missing_FuelNextFull_KM_cost(
																		FuellFullTank_ID, round(km, 2), round(cost, 2), userDetails.getCompany_id());

															}

														} catch (Exception e) {
															e.printStackTrace();

														}

														/// import

														fuel.setFuel_meter_attributes(0);
														// fuel.setFuel_type(importFuel[6]);
														if (fuelTypes != null) {
															for (VehicleFuelType fuelType : fuelTypes) {
																if (fuelType.getVehicleType()
																		.equalsIgnoreCase(importFuel[6])) {
																	fuel.setFuelTypeId(fuelType.getVehicleFuelTypeId());
																	break;

																}
															}

														}
														// fuel.setVehicle_group(VehicleFuelGroup);
														// fuel.setVehicleGroupId(vehicleGroupId);
														// getting import CSV
														// file
														// column 4
														// liter
														fuel.setFuel_liters(Double.parseDouble(importFuel[3]));
														// getting import CSV
														// file
														// column 5
														// price
														fuel.setFuel_price(Double.parseDouble(importFuel[4]));

														try {
															// get Import Vender
															// name to
															// DB vendor ID
															Vendor vendorDtoName = vendorService
																	.GetAllVendorListToFuelImportVendorID(importFuel[7],
																			importFuel[8], userDetails.getCompany_id());
															if (vendorDtoName != null) {
																// getting
																// import
																// CSV file
																// column 7
																// Vendor name
																fuel.setVendor_id(vendorDtoName.getVendorId());

															} else {
																fuel.setVendor_id(fuelService.saveFuelEntriesToNewVendorCreateAuto(
																		importFuel[7] + "-" + importFuel[8], userDetails.getCompany_id(),userDetails.getId()));

															}
															/* } */
														} catch (Exception e) {
															e.printStackTrace();
														}

														/*
														 * // set the tank value of partial or not fuel.
														 * setFuel_tank_partial( tank);
														 */

														// getting import CSV
														// file
														// column 9
														// Reference number
														fuel.setFuel_reference(importFuel[9]);

														try {
															Driver driverDto = driverService
																	.GetDriverEmpNumberToName(importFuel[10]);
															if (driverDto != null) {

																fuel.setDriver_id(driverDto.getDriver_id());
																// getting
																// import
																// CSV
																// file column
																// 10
																// Driver name
																// fuel.setDriver_name(driverDto.getDriver_firstname());
																// fuel.setDriver_empnumber(
																// driverDto.getDriver_empnumber());

															} else {
																fuel.setDriver_id(0);
																// getting
																// import CSV
																// file
																// column 10
																// Driver name
																// fuel.setDriver_name(importFuel[10]);
															}
														} catch (Exception e) {
															e.printStackTrace();
														}
														String FuelTripSheet = "" + importFuel[11];
														if (FuelTripSheet != null && FuelTripSheet != "") {
															fuel.setFuel_TripsheetID(fuelService.getTripSheetIdByNumber(
																	Long.parseLong(FuelTripSheet.replace("TS-", ""))));
														} else {
															fuel.setFuel_TripsheetID((long) 0);
														}
														// getting import CSV
														// file
														// column 11
														// Payment in cash or
														// credit
														// fuel.setFuel_payment(importFuel[12]);
														if (paymentTypeConstantList != null) {
															for (PaymentTypeConstant constant : paymentTypeConstantList) {
																if (constant.getPaymentTypeName()
																		.equalsIgnoreCase(importFuel[12].trim())) {
																	fuel.setPaymentTypeId(constant.getPaymentTypeId());
																	break;
																}
															}
														}

														// getting payment mode
														/*
														 * fuel.setFuel_payment( fuelDto. getFuel_payment());
														 */
														fuel.setFuel_personal(0);

														try {

															if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
																// fuel.setFuel_vendor_paymode("PAID");
																fuel.setFuel_vendor_paymodeId(
																		FuelVendorPaymentMode.PAYMENT_MODE_PAID);
																try {
																	java.util.Date currentDateUpdate = new java.util.Date();
																	Timestamp toDate = new java.sql.Timestamp(
																			currentDateUpdate.getTime());
																	fuel.setFuel_vendor_paymentdate(toDate);
																} catch (Exception e) {

																	e.printStackTrace();
																}

															} else {
																// fuel.setFuel_vendor_paymode("NOTPAID");
																fuel.setFuel_vendor_paymodeId(
																		FuelVendorPaymentMode.PAYMENT_MODE_PAID);
															}

															try {

																try {
																	// save to
																	// fuel
																	// entry in
																	// databases
																	java.util.Date currentUpdate = new java.util.Date();
																	Timestamp toDate = new java.sql.Timestamp(
																			currentUpdate.getTime());
																	fuel.setCreated(toDate);
																	fuel.setLastupdated(toDate);
																	// fuel.setCreatedBy(userDetails.getEmail());
																	// fuel.setLastModifiedBy(userDetails.getEmail());
																	fuel.setCreatedById(userDetails.getId());
																	fuel.setLastModifiedById(userDetails.getId());
																} catch (Exception e) {
																	e.printStackTrace();
																}

																fuel.setFuel_document(false);
																fuel.setFuel_document_id((long) 0);
																fuel.setMarkForDelete(false);
																// save to fuel
																// entry in
																// databases
																sequenceCounter = fuelSequenceService
																		.findNextFuelNumber(
																				userDetails.getCompany_id());
																fuel.setCompanyId(userDetails.getCompany_id());
																if (sequenceCounter == null) {
																	return new ModelAndView(
																			"redirect:/Fuel/1.in?FuelSequenceCounterMissing=true");
																}
																fuel.setFuel_Number(sequenceCounter.getNextVal());
																fuelService.addFuel(fuel);
																// fuelSequenceService.updateNextFuelNumber(sequenceCounter.getNextVal()
																// + 1, userDetails.getCompany_id(),
																// sequenceCounter.getSequence_Id());

																CountSuccess++;
																// update the
																// Current
																// Odometer
																// vehicle
																// Databases
																// tables
																// odameter
																// grenthen
																// only update
																if (VehicleOddmeter < fuel.getFuel_meter()) {
																	try {
																		vehicleService.updateCurrentOdoMeter(vid,
																				Integer.parseInt(importFuel[2]), userDetails.getCompany_id());
																		// update
																		// current
																		// Odometer
																		// update
																		// Service
																		// Reminder
																		ServiceReminderService
																				.updateCurrentOdometerToServiceReminder(
																						fuel.getVid(),
																						fuel.getFuel_meter(), userDetails.getCompany_id());
																		
																		serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(fuel.getVid(), userDetails.getCompany_id(), userDetails.getId());
																		if(serviceList != null) {
																			for(ServiceReminderDto list : serviceList) {
																				
																				if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
																					
																					ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
																					//emailAlertQueueService.sendEmailServiceOdometer(list);
																					//smsAlertQueueService.sendSmsServiceOdometer(list);
																				}
																			}
																		}

																		VehicleOdometerHistory vehicleUpdateHistory = VOHBL
																				.prepareOdometerGetHistoryToFuel(fuel);
																		vehicleOdometerHistoryService
																				.addVehicleOdometerHistory(
																						vehicleUpdateHistory);
																	} catch (Exception e) {
																		e.printStackTrace();
																		LOGGER.error("Fuel Page:", e);

																	}
																}
																if (VehicleOddmeter < fuel.getFuel_meter()) {
																	if (tank == 0) {
																		// save
																		// to
																		// update
																		// partialFuel
																		// entry
																		// in
																		// databases
																		fuelService.updatepartialFuel(vid, tank, userDetails.getCompany_id());

																	}
																}

																if (!(VehicleOddmeter < fuel.getFuel_meter())) {
																	++CountDuplicateOdameter;
																	String DuplicateOda = "Vehicle = "
																			+ Vehicle_registration
																			+ " Odameter Reading = " + VehicleOddmeter;
																	model.put("CountDuplicateOdameter",
																			CountDuplicateOdameter);
																	model.put("Duplicateodameter", DuplicateOda);
																	model.put("OddmeterError", true);
																}

															} catch (final Exception e) {
																e.printStackTrace();
																++CountDuplicate;
																String Duplicate = "Vehicle = " + Vehicle_registration;
																model.put("CountDuplicate", CountDuplicate);
																model.put("Duplicate", Duplicate);
																model.put("importEmpty", true);
															}

														} catch (final Exception e) {
															++CountDuplicate;
															String Duplicate = "Vehicle = " + Vehicle_registration;
															model.put("CountDuplicate", CountDuplicate);
															model.put("Duplicate", Duplicate);
															model.put("importEmpty", true);
															e.printStackTrace();
														}

													} // oddmieter greaten close

												} // validate complete

											} catch (Exception e) {
												String Duplicatevehicle = "Vehicle = " + Vehicle_registration;
												model.put("DuplicateVehicle", Duplicatevehicle);
												model.put("importEmptyVehicle", true);
												e.printStackTrace();
											}
										} // for loop if cognitions
										else {

											String Duplicatevehicle = "Vehicle = " + Vehicle_registration;
											model.put("DuplicateVehicle", Duplicatevehicle);
											model.put("importEmptyVehicle", true);

										}

									} else {
										// this date format error show meassage
										String errordate = "Vehicle = " + Vehicle_registration;
										model.put("dateError", errordate);

									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								LOGGER.error("Fuel Page:", e);
							}

						} catch (Exception e) {
							// System.out.println("error while reading csv and
							// put to db : " + e.getMessage());
							e.printStackTrace();
							// show the Fuel List
							model.put("fuel", FuBL.prepareListofFuel(fuelService.listFuelEntries(1, userDetails)));
							// show the driver list in all
							model.put("importEmpty", true);
							return new ModelAndView("Fuel", model);
						}

					} // for loop close

				}
			}
		} catch (Exception e) {
			// System.out.println("error while reading csv and put to db : " +
			// e.getMessage());
			// show the Fuel List
			model.put("fuel", FuBL.prepareListofFuel(fuelService.listFuelEntries(1, userDetails)));
			// show the driver list in all
			model.put("importEmpty", true);
			return new ModelAndView("Fuel", model);
		}

		model.put("CountSuccess", CountSuccess);
		model.put("fuel", FuBL.prepareListofFuel(fuelService.listFuelEntries(1, userDetails)));

		try {

			model.put("FuelCount", fuelService.countFuel(userDetails.getCompany_id()));

			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

			model.put("FuelTodayEntriesCount", fuelService.countFuelTodayEntries(toDate, userDetails.getCompany_id()));

		} catch (Exception e) {

			e.printStackTrace();
		}
		// show the driver list in all
		model.put("importSave", true);
		return new ModelAndView("Fuel", model);
		// return new ModelAndView("redirect:/Fuel/1.in?Success=true" ,model);

	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/importFuel2", method = RequestMethod.POST)
	public ModelAndView saveImport2(@ModelAttribute("command") VehicleDocument photo,
			@RequestParam("import") MultipartFile file, HttpServletRequest request) throws Exception {

		CustomUserDetails userDetails = null;
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> model = new HashMap<String, Object>();
		FuelSequenceCounter sequenceCounter = null;
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		List<ServiceReminderDto>		serviceList					= null;
		// List<VehicleOwnerShip> vehicleOwnerShipList =
		// VehicleOwnerShip.getVehicleOwerShipList();
		List<PaymentTypeConstant> paymentTypeConstantList = PaymentTypeConstant.getPaymentTypeList();
		List<VehicleFuelType> fuelTypes = VehicleFuelType.getVehicleFuelTypeList();

		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());

		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
				int i;
				// write file to server
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			/*
			 * model.put("msg", "failed to process file because : " + e.getMessage());
			 * return "mainpage";
			 */
			e.printStackTrace();
		}

		// Count How many Import SuccessFully
		int CountSuccess = 0;
		int CountDuplicate = 0;
		int CountDuplicateOdameter = 0;
		
		 List<VendorDto> vendorList	= vendorService.findAllVendorList(userDetails.getCompany_id());
		
		Map<String, VendorDto> vendorHm =
				vendorList.stream().collect(Collectors.toMap(VendorDto::getVendorName,Function.identity()));
		
;
		String[] nextLine;
		try {
			// read file
			// CSVReader(fileReader, ';', '\'', 1) means
			// using separator ; and using single quote ' . Skip first line when
			// read
			DecimalFormat	decimalFormat = new DecimalFormat("#.00");
			
			
			HashMap<Integer, Integer>   vehicleOdometerHm	=    new HashMap<Integer, Integer>();
			List<Vehicle>    vehicleList	=  vehicleService.getVehicleListByCompanyId(userDetails.getCompany_id());
			Map<String, Vehicle> vehicleHM =
					vehicleList.stream().collect(Collectors.toMap(Vehicle::getVehicle_registration,
				                                              Function.identity()));
			
			try (FileReader fileReader = new FileReader(serverFile);
					CSVReader reader = new CSVReader(fileReader, ';', '\'', 1);) {
				while ((nextLine = reader.readNext()) != null) {

					for (int i = 0; i < nextLine.length; i++) {
						Fuel fuel = new Fuel();
						try {
							// System.out.println(nextLine[i]);
							String[] importFuel = nextLine[i].split(",");

							Integer vid = null;
							
							String VehicleFuelType = null;
							String VehicleFuelGroup = null;
							String VehicleOwnerShipType = null;
							long vehicleGroupId = 0;
							Integer VehicleOddmeter = null;
							
							try {

								if (importFuel != null && importFuel.length != 0) {
									if(vehicleHM.get(importFuel[0].trim()) != null) {
										Vehicle vehicle = vehicleHM.get(importFuel[0].trim());
										fuel.setVid(vehicle.getVid());
										fuel.setFuelDateTime(dateFormat.parse(importFuel[1].trim()));
										fuel.setFuel_date(dateFormat.parse(importFuel[1].trim()));
										
										VehicleOddmeter	=  vehicleOdometerHm.get(vehicle.getVid());
										if(VehicleOddmeter == null) {
											VehicleOddmeter = vehicle.getVehicle_Odometer();
										}
										vehicleOdometerHm.put(vehicle.getVid(), VehicleOddmeter + Integer.parseInt(importFuel[13].trim()));
										
										fuel.setFuel_meter_old(VehicleOddmeter);
										fuel.setFuel_meter(VehicleOddmeter + Integer.parseInt(importFuel[13].trim()));
										fuel.setFuel_liters(Double.parseDouble(importFuel[3].trim()));
										fuel.setFuel_price(Double.parseDouble(importFuel[4].trim()));
										fuel.setFuel_tank(1);
										fuel.setFuelTypeId((short) 1);
										if(vendorHm.get(importFuel[7].trim()) != null) {
											fuel.setVendor_id(vendorHm.get(importFuel[7].trim()).getVendorId());
										}else {
											fuel.setVendor_id(fuelService.saveFuelEntriesToNewVendorCreateAuto(
													importFuel[7] + "-" + importFuel[8], userDetails.getCompany_id(),userDetails.getId()));
											vendorHm.put(importFuel[7].trim(), new VendorDto(fuel.getVendor_id(), importFuel[7].trim()));
										}
										fuel.setFuel_reference(importFuel[9].trim());
										
										Driver driverDto = driverService
												.GetDriverEmpNumberToName(importFuel[10].trim());
										if(driverDto != null) {
											fuel.setDriver_id(driverDto.getDriver_id());
										}else {
											fuel.setDriver_id(0);
										}
										fuel.setFuel_TripsheetID((long) 0);
										fuel.setPaymentTypeId(PaymentTypeConstant.PAYMENT_TYPE_CREDIT);
										fuel.setFuel_vendor_paymodeId(PaymentTypeConstant.PAYMENT_TYPE_CREDIT);
										fuel.setPaidAmount(0.0);
										fuel.setDiscountAmount(0.0);
										fuel.setBalanceAmount(fuel.getFuel_liters() * fuel.getFuel_price());
										fuel.setFuel_amount(fuel.getFuel_liters() * fuel.getFuel_price());
										fuel.setFuel_usage(fuel.getFuel_meter() - fuel.getFuel_meter_old());
										fuel.setFuel_kml(Double.parseDouble(decimalFormat.format(fuel.getFuel_usage()/fuel.getFuel_liters())));
										fuel.setFuel_cost(Double.parseDouble(decimalFormat.format(fuel.getFuel_amount()/fuel.getFuel_usage())));
										
										
										
										fuel.setFuel_comments("EXCEL Entry");
										fuel.setCreated(new java.util.Date());
										fuel.setLastupdated(new java.util.Date());
										fuel.setCompanyId(userDetails.getCompany_id());
										fuel.setCreatedById(userDetails.getId());
										fuel.setLastModifiedById(userDetails.getId());
										fuel.setFuel_personal(0);
										fuel.setFuel_meter_attributes(0);
										
										sequenceCounter = fuelSequenceService
												.findNextFuelNumber(
														userDetails.getCompany_id());
										fuel.setCompanyId(userDetails.getCompany_id());
										if (sequenceCounter == null) {
											return new ModelAndView(
													"redirect:/Fuel/1.in?FuelSequenceCounterMissing=true");
										}
										fuel.setFuel_Number(sequenceCounter.getNextVal());
										fuelService.addFuel(fuel);
										CountSuccess ++;
										
									}else {
										
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								LOGGER.error("Fuel Page:", e);
							}

						} catch (Exception e) {
							// System.out.println("error while reading csv and
							// put to db : " + e.getMessage());
							e.printStackTrace();
							// show the Fuel List
							model.put("fuel", FuBL.prepareListofFuel(fuelService.listFuelEntries(1, userDetails)));
							// show the driver list in all
							model.put("importEmpty", true);
							return new ModelAndView("Fuel", model);
						}

					} // for loop close
					
						for (Integer vid : vehicleOdometerHm.keySet()) {
						    vehicleService.updateCurrentOdoMeter(vid, vehicleOdometerHm.get(vid), userDetails.getCompany_id());
						}

				}
			}
		} catch (Exception e) {
			// System.out.println("error while reading csv and put to db : " +
			// e.getMessage());
			// show the Fuel List
			model.put("fuel", FuBL.prepareListofFuel(fuelService.listFuelEntries(1, userDetails)));
			// show the driver list in all
			model.put("importEmpty", true);
			return new ModelAndView("Fuel", model);
		}

		model.put("CountSuccess", CountSuccess);
		model.put("fuel", FuBL.prepareListofFuel(fuelService.listFuelEntries(1, userDetails)));

		try {

			model.put("FuelCount", fuelService.countFuel(userDetails.getCompany_id()));

			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

			model.put("FuelTodayEntriesCount", fuelService.countFuelTodayEntries(toDate, userDetails.getCompany_id()));

		} catch (Exception e) {

			e.printStackTrace();
		}
		// show the driver list in all
		model.put("importSave", true);
		return new ModelAndView("Fuel", model);
		// return new ModelAndView("redirect:/Fuel/1.in?Success=true" ,model);

	
	}

	// -----------------------------------------------------------------------------
	// get Save Fuel
	public Fuel saveFuel(FuelDto fuelDto) throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Fuel fuel = new Fuel();
		fuel.setFuel_id(fuelDto.getFuel_id());
		fuel.setVid(fuelDto.getVid());
		fuel.setFuelTypeId(fuelDto.getFuelTypeId());
		fuel.setPaymentTypeId(fuelDto.getPaymentTypeId());
		try {
			java.util.Date date = dateFormat.parse(fuelDto.getFuel_date());
			java.sql.Date FuelDate = new Date(date.getTime());
			fuel.setFuel_date(FuelDate);

			if (fuelDto.getFuel_TripsheetNumber() != null && fuelDto.getFuel_TripsheetNumber() > 0) {
				fuel.setFuel_TripsheetID(fuelService.getTripSheetIdByNumber(fuelDto.getFuel_TripsheetNumber()));
			} else {
				fuel.setFuel_TripsheetID((long) 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		fuel.setFuel_meter(fuelDto.getFuel_meter());
		fuel.setGpsOdometer(fuelDto.getGpsOdometer());

		// Note: fuel document id to value
		fuel.setFuel_document_id((long) 0);

		/**
		 * below checking old odometer greater then current odometer this true
		 */
		if(fuelDto.getFuel_meter_old() == null) {
			fuelDto.setFuel_meter_old(0);
		}
		
		if(fuelDto.getFuel_meter() == null) {
			fuelDto.setFuel_meter(0);
		}
		
		if (fuelDto.getFuel_meter_old() < fuelDto.getFuel_meter()) {

			/* save to usage km is vehicle */
			fuel.setFuel_meter_old(fuelDto.getFuel_meter_old());

			// get enter the fuel meter
			Integer fuelmeterNow = fuelDto.getFuel_meter();
			// get old meter
			Integer fuelmeterOld = fuelDto.getFuel_meter_old();
			// get usage fuel calculation
			Integer usagefuel = (fuelmeterNow - fuelmeterOld);
			// save usage
			fuel.setFuel_usage(usagefuel);

			/* save to amount of fuel enter */
			// get fuel volume
			Double fuelliter = fuelDto.getFuel_liters();
			// get fuel price
			Double fuelprice = fuelDto.getFuel_price();
			// get amount of fuel calculation
			if(fuelliter == null)
				fuelliter = 0.0;
			if(fuelprice == null)
				fuelprice = 0.0;
			Double amountfuel = fuelliter * fuelprice;
			
			// Double amountFuelRound=new BigDecimal(amountfuel).setScale(2,
			// BigDecimal.ROUND_HALF_UP).doubleValue();
			fuel.setFuel_amount(round(amountfuel, 2));
			//fuel.setBalanceAmount(round(amountfuel, 2));
			/* save to km/L of fuel enter */
			Integer tank = fuelDto.getFuel_tank();
			/** below checking fuel tank full or partial this true */
			if (tank == 0) {
				// get Vehicle id to Vehicle name

				// Integer tank1 = 1;

				Integer usagePartial = 0;
				Double litersPartial = 0.0;
				Double AmountPartial = 0.0;

				try {

					// search time full tank stop the loop
					List<Fuel> fuelkmDESC = fuelService.findMissingOddmeterFirstDESC_Vaule_Liter_Amount(
							fuelDto.getVid(), fuelmeterNow, fuel.getFuel_date());
					if (fuelkmDESC != null && !fuelkmDESC.isEmpty()) {
						for (Fuel fuelDtoDESC : fuelkmDESC) {
							// get the Fuel_tank_partial value and equal to one
							// or
							// not
							if (fuelDtoDESC.getFuel_tank_partial() == 1) {

								usagePartial += fuelDtoDESC.getFuel_usage();
								litersPartial += fuelDtoDESC.getFuel_liters();
								AmountPartial += fuelDtoDESC.getFuel_amount();

							} else {
								// FirstDESC_Vaule_Liter_Amount");
								break; // stop the loop when Tank_partial value
										// == 0
							}
						}
					}
					Integer usagefuelTotal = usagePartial + usagefuel;
					Double literTotal = litersPartial + (fuelDto.getFuel_liters());

					Double km = (usagefuelTotal / literTotal);

					fuel.setFuel_kml(round(km, 2));

					Double amountfuelTotal = AmountPartial + amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}
					fuel.setFuel_cost(round(cost, 2));

				} catch (Exception e) {

					Integer usagefuelTotal = usagePartial + usagefuel;
					Double literTotal = litersPartial + (fuelDto.getFuel_liters());

					Double km = (usagefuelTotal / literTotal);

					fuel.setFuel_kml(round(km, 2));

					Double amountfuelTotal = AmountPartial + amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}

					fuel.setFuel_cost(round(cost, 2));
				}

			}

			fuel.setFuel_meter_attributes(fuelDto.getFuel_meter_attributes());
			fuel.setFuel_liters(fuelDto.getFuel_liters());
			fuel.setFuel_price(fuelDto.getFuel_price());

			fuel.setFuel_tank(fuelDto.getFuel_tank());

			// if (fuelDto.getVendor_id() != null && fuelDto.getVendor_id() != 0) {
			// fuel.setVendor_id(fuelDto.getVendor_id());
			// } else {
			// fuel.setVendor_id(0);
			// }

			// this else this greater than value of false the calculation

			/** close If condition greater than fuel meter */
		} else {

			/***************************************************************************
			 * save Fuel Missing Entries
			 ****************************************************************************/
			// missing collection
			/* save to usage km is vehicle */
			// get enter the fuel meter
			Integer fuelmeterNow = fuelDto.getFuel_meter();

			/* save to amount of fuel enter */
			// get fuel volume
			Double fuelliter = fuelDto.getFuel_liters();
			// get fuel price
			Double fuelprice = fuelDto.getFuel_price();
			// get amount of fuel calculation
			Double amountfuel = fuelliter * fuelprice;

			fuel.setFuel_amount(round(amountfuel, 2));
			/* save to km/L of fuel enter */
			Integer tank = fuelDto.getFuel_tank();

			Integer usagePartialMISSING = 0;
			Double litersPartialMISSING = 0.0;
			Double AmountPartialMISSING = 0.0;

			Long FuellFullTank_ID = null;

			Integer OldFullTank_OpringKM = null;

			Long PartialFuel_Tank_ID = null;
			Integer OldPartialTank_closingKM = null;

			try {

				/**
				 * Search descending Order in vid, odometer, date less then time full tank stop
				 * the for loop in fuel entries in database
				 */
				List<Fuel> fuelkmDESC = fuelService.findMissingOddmeterFirstDESC_Vaule_Liter_Amount(fuelDto.getVid(),
						fuelmeterNow, fuel.getFuel_date());
				if (fuelkmDESC != null && !fuelkmDESC.isEmpty()) {
					for (Fuel fuelDtoDESC : fuelkmDESC) {
						// get the Fuel_tank_partial value and equal to one or
						// not
						/** Fuel tank is = 0 , partial is = 1 */
						if (fuelDtoDESC.getFuel_tank_partial() == 1) {

							usagePartialMISSING += fuelDtoDESC.getFuel_usage();
							litersPartialMISSING += fuelDtoDESC.getFuel_liters();
							AmountPartialMISSING += fuelDtoDESC.getFuel_amount();

						} else {
							break; // stop the loop when Tank_partial value == 0
						}
					}
				}

				List<Fuel> fuelkmASC_Update = fuelService.findMissingOddmeterSecondASC_Vaule_Liter_Amount(
						fuelDto.getVid(), fuelmeterNow, fuel.getFuel_date());

				if (fuelkmASC_Update != null && !fuelkmASC_Update.isEmpty()) {
					for (Fuel fuel2 : fuelkmASC_Update) {
						// get the this update before vehicle id and meter value
						if (fuel2.getFuel_tank_partial() == 1) {

							OldFullTank_OpringKM = fuel2.getFuel_meter();

							PartialFuel_Tank_ID = fuel2.getFuel_id();
							OldPartialTank_closingKM = fuel2.getFuel_meter();
							// before enter update usage and opening oddmeter

							fuelService.update_Missing_OddMeter_Usage_Partial(fuelDto.getFuel_meter(),
									OldPartialTank_closingKM - fuel.getFuel_meter(), PartialFuel_Tank_ID, fuel2.getCompanyId());

							break;
						} else {

							OldFullTank_OpringKM = fuel2.getFuel_meter();

							PartialFuel_Tank_ID = fuel2.getFuel_id();
							OldPartialTank_closingKM = fuel2.getFuel_meter();
							// before enter update usage and opening oddmeter

							fuelService.update_Missing_OddMeter_Usage_Partial(fuelDto.getFuel_meter(),
									OldPartialTank_closingKM - fuel.getFuel_meter(), PartialFuel_Tank_ID, fuel2.getCompanyId());

							break;
						}
					}
				}

				// search asc order in vid and odd meter value in asc less than
				// search time full tank stop the loop but get usage and liter
				// and get full tank id and meter value also
				List<Fuel> fuelkmASC = fuelService.findMissingOddmeterSecondASC_Vaule_Liter_Amount(fuelDto.getVid(),
						fuelmeterNow, fuel.getFuel_date());
				if (fuelkmASC != null && !fuelkmASC.isEmpty()) {
					for (Fuel fuelDtoASC : fuelkmASC) {
						/*
						 * if enter the missing fuel tank is full not calution before enter don't cal
						 */
						if (fuelDto.getFuel_tank() != 0) {

							// get the Fuel_tank_partial value and equal to one
							// or
							// not
							if (fuelDtoASC.getFuel_tank_partial() == 1) {

								usagePartialMISSING += fuelDtoASC.getFuel_usage();
								litersPartialMISSING += fuelDtoASC.getFuel_liters();
								AmountPartialMISSING += fuelDtoASC.getFuel_amount();

							} else {
								OldFullTank_OpringKM = fuelDtoASC.getFuel_meter();
								usagePartialMISSING += fuelDtoASC.getFuel_usage();
								litersPartialMISSING += fuelDtoASC.getFuel_liters();
								AmountPartialMISSING += fuelDtoASC.getFuel_amount();

								FuellFullTank_ID = fuelDtoASC.getFuel_id();
								break; // stop the loop when Tank_partial value
										// == 0
							}

						} else {

							FuellFullTank_ID = fuelDtoASC.getFuel_id();
							OldFullTank_OpringKM = fuelDtoASC.getFuel_meter();
							FuellFullTank_ID = fuelDtoASC.getFuel_id();
							break; // stop the loop when Tank_partial value == 0
						}
					}
				}

				// get old meter
				Integer fuelmeterOld = 0;

				// get opening oddmeter in enter oddmeter value
				List<Fuel> fuelkmDESC_OPEN_ODDMeter = fuelService.findMissingOddmeterFirstDESC_Vaule_Liter_Amount(
						fuelDto.getVid(), fuelmeterNow, fuel.getFuel_date());
				if (fuelkmDESC_OPEN_ODDMeter != null && !fuelkmDESC_OPEN_ODDMeter.isEmpty()) {
					for (Fuel fuelDtoDESC_OPEN : fuelkmDESC_OPEN_ODDMeter) {

						fuelmeterOld = fuelDtoDESC_OPEN.getFuel_meter();
						break; // stop the loop when Tank_partial value == 0

					}
				}

				if (OldFullTank_OpringKM == null) {
					OldFullTank_OpringKM = 0;
				}
				//
				Integer OldFullTank_OpringKM_Usage = (OldFullTank_OpringKM - fuel.getFuel_meter());
				// after missing oddmter after get the value of that fuel
				// enterties values
				@SuppressWarnings("unused")
				Integer OldFull_AfterMissingMeterOpring = (OldFullTank_OpringKM) - (OldFullTank_OpringKM_Usage);

				// get usage fuel calculation
				Integer usagefuel = (fuelmeterNow - fuelmeterOld);
				// save usage
				fuel.setFuel_usage(usagefuel);

				// save Opeing Km Meter
				fuel.setFuel_meter_old(fuelmeterOld);

				// tank full mean update that full value row not full search for
				// full tank entries in full entries
				if (tank == 0) {

					// Missing Tank Full update the oddmeter in same full id
					// that calculation

					Integer usagefuelTotal = usagePartialMISSING + usagefuel;
					Double literTotal = litersPartialMISSING + (fuelDto.getFuel_liters());

					Double km = (usagefuelTotal / literTotal);

					fuel.setFuel_kml(round(km, 2));

					Double amountfuelTotal = AmountPartialMISSING + amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}
					fuel.setFuel_cost(round(cost, 2));

					fuelService.update_Missing_OddMeter_Usage_Partial(fuel.getFuel_meter(),
							OldFullTank_OpringKM - fuel.getFuel_meter(), FuellFullTank_ID, userDetails.getCompany_id());

					/******************************************************************/
					// this code check get Old Full tank value of the cost and
					// km/l cost update
					Long OldFUELL_id = (long) 0;
					Integer usagePartialOldFUELL = 0;
					Double litersPartialOldFUELL = 0.0;
					Double AmountPartialOldFUELL = 0.0;

					List<Fuel> fuelkmASC_updateOldFuel_cost = fuelService
							.findMissingOddmeterSecondASC_Vaule_Liter_Amount(fuelDto.getVid(), fuelmeterNow,
									fuel.getFuel_date());
					if (fuelkmASC_updateOldFuel_cost != null && !fuelkmASC_updateOldFuel_cost.isEmpty()) {
						for (Fuel fuelDtoASC_oldfull : fuelkmASC_updateOldFuel_cost) {

							// get the Fuel_tank_partial value and equal to one
							// or
							// not
							if (fuelDtoASC_oldfull.getFuel_tank_partial() == 1) {

								/*
								 * OldFullTank_OpringKM = fuelDtoASC.getFuel_meter();
								 * System.out.println(OldFullTank_OpringKM);
								 */
								usagePartialOldFUELL += fuelDtoASC_oldfull.getFuel_usage();
								litersPartialOldFUELL += fuelDtoASC_oldfull.getFuel_liters();
								AmountPartialOldFUELL += fuelDtoASC_oldfull.getFuel_amount();

							} else {

								usagePartialOldFUELL += fuelDtoASC_oldfull.getFuel_usage();
								litersPartialOldFUELL += fuelDtoASC_oldfull.getFuel_liters();
								AmountPartialOldFUELL += fuelDtoASC_oldfull.getFuel_amount();

								OldFUELL_id = fuelDtoASC_oldfull.getFuel_id();
								break; // stop the loop when Tank_partial value
										// == 0
							}

						} // for loop close

					} // close if
						// this update Missing oddmeter to add in Next Fuel entries
						// Full calculation cost and km/L

					Double KM_OLdNextFULL = (usagePartialOldFUELL / litersPartialOldFUELL);

					Double cost_OLdNextFULL = (AmountPartialOldFUELL / usagePartialOldFUELL);

					if (KM_OLdNextFULL == 0.0) {
						cost_OLdNextFULL = 0.0;
					}

					fuelService.update_Missing_FuelNextFull_KM_cost(OldFUELL_id, round(KM_OLdNextFULL, 2),
							round(cost_OLdNextFULL, 2), userDetails.getCompany_id());

				} else {
					/*********************************
					 * ADD in UI Tank partial missing oddmeter
					 **********************************/

					/* usagePartialMISSING += OldFullTank_OpringKM_Usage; */

					// this update Missing oddmeter calculation that id
					Integer usagefuelTotal = usagePartialMISSING + usagefuel;
					Double literTotal = litersPartialMISSING + (fuelDto.getFuel_liters());

					/// *********************************/
					/* Tank partial missing oddmeter */

					/* usagePartialMISSING += OldFullTank_OpringKM_Usage; */

					Double km = (usagefuelTotal / literTotal);

					// System.out.println("Partial Feul KM/L ="+ km);

					Double amountfuelTotal = AmountPartialMISSING + amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}

					fuelService.update_Missing_FuelNextFull_KM_cost(FuellFullTank_ID, round(km, 2), round(cost, 2), userDetails.getCompany_id());

				}

			} catch (Exception e) {
				e.printStackTrace();

			}

			fuel.setFuel_meter_attributes(fuelDto.getFuel_meter_attributes());
			fuel.setFuel_liters(fuelDto.getFuel_liters());
			fuel.setFuel_price(fuelDto.getFuel_price());
			

			if (fuelDto.getVendor_id() != null && fuelDto.getVendor_id() != 0) {
				fuel.setVendor_id(fuelDto.getVendor_id());
			} else {
				fuel.setVendor_id(0);
			}

			// Why tank value is Zero mean this missing odd meter
			// Next time greater then value add partial this not calculation the
			// that full
			fuel.setFuel_tank(0);
		}

		fuel.setFuel_tank_partial(fuelDto.getFuel_tank());
		fuel.setFuel_reference(fuelDto.getFuel_reference());

		if (fuelDto.getDriver_id() != null && fuelDto.getDriver_id() != 0) { 
			fuel.setDriver_id(fuelDto.getDriver_id());
			// fuel.setDriver_name(null);
		} else {
			fuel.setDriver_id(0);
			// fuel.setDriver_name(fuelDto.getDriver_name());
		}
		
		if(fuelDto.getSecDriverID()!= null && fuelDto.getSecDriverID() > 0) {
			fuel.setSecDriverID(fuelDto.getSecDriverID());
			}else {
				fuel.setSecDriverID(0);
			}
			if(fuelDto.getCleanerID()!= null && fuelDto.getCleanerID()> 0) {
			fuel.setCleanerID(fuelDto.getCleanerID());
			}else {
				fuel.setCleanerID(0);
			}
			if(fuelDto.getRouteID()!= null && fuelDto.getRouteID() >0) {
			fuel.setRouteID(fuelDto.getRouteID());
			}else {
				fuel.setRouteID(0);
			}

		fuel.setFuel_personal(fuelDto.getFuel_personal());

		fuel.setFuel_comments(fuelDto.getFuel_comments());

		return fuel;
	}

	/* list of GetvehicelpartialFuel */
	public List<FuelDto> prepareOfGetvehicelpartialFuel(List<Fuel> fuelList) {

		List<FuelDto> Dtos = null;

		if (fuelList != null && !fuelList.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;

			for (Fuel fuelDto : fuelList) {

				fuel = new FuelDto();

				fuel.setFuel_liters(fuelDto.getFuel_liters());
				fuel.setFuel_usage(fuelDto.getFuel_usage());
				fuel.setFuel_amount(fuelDto.getFuel_amount());

				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	/* list of GetvehicelpartialFuel */
	public List<FuelDto> findMissingOddmeter(List<Fuel> fuelList) {

		List<FuelDto> Dtos = null;

		if (fuelList != null && !fuelList.isEmpty()) {
			Dtos = new ArrayList<FuelDto>();
			FuelDto fuel = null;

			for (Fuel fuelDto : fuelList) {

				fuel = new FuelDto();

				fuel.setFuel_tank_partial(fuelDto.getFuel_tank_partial());
				fuel.setFuel_liters(fuelDto.getFuel_liters());
				fuel.setFuel_usage(fuelDto.getFuel_usage());
				fuel.setFuel_amount(fuelDto.getFuel_amount());

				fuel.setFuel_tank_partial(fuelDto.getFuel_tank_partial());
				Dtos.add(fuel);
			}
		}
		return Dtos;
	}

	@RequestMapping(value = "/getFuelVehicleList", method = RequestMethod.GET)
	public void getAddressList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

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
		System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/getFuelVehicleOdoMerete", method = RequestMethod.GET)
	public void getFuelVehicleOdoMerete(@RequestParam(value = "FuelvehicleID", required = true) Integer vehicleID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			if(vehicleID !=  null && vehicleID > 0){
				
				VehicleDto oddmeter =	null;
				HashMap<String, Object>   configuration		= null;
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
				if(!(boolean) configuration.get(FuelConfigurationConstants.LAST_FUEL_AS_OPEN)) {
					oddmeter = vehicleService.getVehicel_DropDown_Fuel_ADD_entries(vehicleID,userDetails.getCompany_id());
				}else {
					oddmeter = vehicleService.getVehicel_DropDown_Last_Fuel_ADD_entries(vehicleID,userDetails.getCompany_id());
				}
				
				VehicleDto wadd = new VehicleDto();
				if (oddmeter != null) {
					HashMap<String, Object>	gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
					if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
						ValueObject	object	= new ValueObject();
						
						object.put("companyId", userDetails.getCompany_id());
						object.put("vehicleId", vehicleID);
						ValueObject	gpsobject	=	vehicleGPSDetailsService.getVehicleGPSData(object);
						if(gpsobject != null && gpsobject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME)) {
							wadd.setGpsOdameter(gpsobject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME));
						}
						if(gpsobject !=null) {
							wadd.setOdometerReading(gpsobject.getBoolean("isOdometerReading"));
						}
					}
					wadd.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
					
					//wadd.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
					wadd.setVehicle_Fuel(
							methodRemoveLastComma(VehicleFuelType.getVehicleFuelTypeName(oddmeter.getVehicleFuelId())));
					wadd.setVehicle_Group(oddmeter.getVehicle_Group());
					wadd.setVehicleGroupId(oddmeter.getVehicleGroupId());
					wadd.setVehicleFuelId(oddmeter.getVehicleFuelId());
					wadd.setVehicle_Fuel(VehicleFuelType.getVehicleFuelTypeName(oddmeter.getVehicleFuelId()));
					wadd.setVehicle_ExpectedOdameter(oddmeter.getVehicle_ExpectedOdameter());
					wadd.setLastFuelOdometer(oddmeter.getLastFuelOdometer());
					wadd.setVehicle_registration(oddmeter.getVehicle_registration());
					wadd.setVehicle_ExpectedMileage(oddmeter.getVehicle_ExpectedMileage());
					wadd.setVehicle_ExpectedMileage_to(oddmeter.getVehicle_ExpectedMileage_to());
					wadd.setVehicle_FuelTank1(oddmeter.getVehicle_FuelTank1());
				}
				Gson gson = new Gson();
				
				response.getWriter().write(gson.toJson(wadd));
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@RequestMapping(value = "/getLastFuelVehicleOdoMerete", method = RequestMethod.GET)
	public void getLastFuelVehicleOdoMerete(@RequestParam(value = "FuelvehicleID", required = true) Integer vehicleID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleDto oddmeter = vehicleService.getVehicel_DropDown_Urea_ADD_entries(vehicleID,
					userDetails.getCompany_id());
			VehicleDto wadd = new VehicleDto();
			if (oddmeter != null) {
			HashMap<String, Object>	gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
				if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
					ValueObject	object	= new ValueObject();
					
					object.put("companyId", userDetails.getCompany_id());
					object.put("vehicleId", vehicleID);
				ValueObject	gpsobject	=	vehicleGPSDetailsService.getVehicleGPSData(object);
					if(gpsobject != null && gpsobject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME)) {
						wadd.setGpsOdameter(gpsobject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME));
					}
					if(gpsobject !=null) {
						wadd.setOdometerReading(gpsobject.getBoolean("isOdometerReading"));
					}
				}
				wadd.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
				
				//wadd.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
				wadd.setVehicle_Fuel(
						methodRemoveLastComma(VehicleFuelType.getVehicleFuelTypeName(oddmeter.getVehicleFuelId())));
				wadd.setVehicle_Group(oddmeter.getVehicle_Group());
				wadd.setVehicleGroupId(oddmeter.getVehicleGroupId());
				wadd.setVehicleFuelId(oddmeter.getVehicleFuelId());
				wadd.setVehicle_Fuel(VehicleFuelType.getVehicleFuelTypeName(oddmeter.getVehicleFuelId()));
				wadd.setVehicle_ExpectedOdameter(oddmeter.getVehicle_ExpectedOdameter());
				wadd.setvStatusId(oddmeter.getvStatusId());
				wadd.setVehicle_Status(oddmeter.getVehicle_Status());
				wadd.setLastUreaOdometer(oddmeter.getLastUreaOdometer());
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(wadd));
		} catch (Exception e) {
			throw e;
		}
	}
	

	public String methodRemoveLastComma(String str) {
		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	@RequestMapping(value = "/getFuelVendorList", method = RequestMethod.GET)
	public void getFuelVendorList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Vendor> addresses = new ArrayList<Vendor>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		for (VendorDto add : vendorService.listVendor(" lower(v.vendorType) Like ('%FUEL%')",
				userDetails.getCompany_id())) {
			Vendor wadd = new Vendor();
			if (add != null) {
				wadd.setVendorId(add.getVendorId());
				wadd.setVendorName(add.getVendorName());
				wadd.setVendorLocation(add.getVendorLocation());
			}
			addresses.add(wadd);
		}

		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(addresses));
	}

	/*
	 * @RequestMapping(value = "/getFuelDriverList", method = RequestMethod.GET)
	 * public void getDriverList(Map<String, Object> map, HttpServletRequest
	 * request, HttpServletResponse response) throws Exception {
	 * 
	 * List<DriverDto> Driver = new ArrayList<DriverDto>(); List<Driver> drivertype
	 * = driverService.fildAllDriverList(); if (drivertype != null &&
	 * !drivertype.isEmpty()) { for (Driver add : drivertype) { DriverDto wadd = new
	 * DriverDto(); wadd.setDriver_id(add.getDriver_id());
	 * wadd.setDriver_firstname(add.getDriver_firstname());
	 * wadd.setDriver_Lastname(add.getDriver_Lastname());
	 * wadd.setDriver_empnumber(add.getDriver_empnumber()); Driver.add(wadd); } }
	 * Gson gson = new Gson();
	 * 
	 * // System.out.println("jsonStudents = " + gson.toJson(Driver));
	 * 
	 * response.getWriter().write(gson.toJson(Driver)); }
	 */

	/********************************************************************************************************************
	 * Delete to add fuel Entries in full tank and partical
	 * 
	 * @throws Exception
	 *******************************************************************************************************************
	 */
	// get Save Fuel
	@SuppressWarnings("unused")
	public Fuel DeleteTosaveFuel(Fuel fuelDto) throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Fuel fuel = new Fuel();

		fuel.setFuel_id(fuelDto.getFuel_id());
		fuel.setVid(fuelDto.getVid());
		try {
			fuel.setFuel_date(fuelDto.getFuel_date());
		} catch (Exception e) {
			LOGGER.error("Fuel Page:", e);

		}

		fuel.setFuel_meter(fuelDto.getFuel_meter());

		fuel.setFuel_document(fuelDto.isFuel_document());
		fuel.setFuel_document_id(fuelDto.getFuel_document_id());

		{

			// missing collection

			/* save to usage km is vehicle */
			// get enter the fuel meter
			Integer fuelmeterNow = fuelDto.getFuel_meter();

			/* save to amount of fuel enter */
			// get fuel volume
			Double fuelliter = fuelDto.getFuel_liters();
			// get fuel price
			Double fuelprice = fuelDto.getFuel_price();
			// get amount of fuel calculation
			Double amountfuel = fuelliter * fuelprice;

			fuel.setFuel_amount(round(amountfuel, 2));
			/* save to km/L of fuel enter */
			Integer tank = fuelDto.getFuel_tank_partial();

			Integer usagePartialMISSING = 0;
			Double litersPartialMISSING = 0.0;
			Double AmountPartialMISSING = 0.0;

			Long FuellFullTank_ID = null;
			Integer FuellFull_Tank_meter = 0;

			Integer OldFullTank_OpringKM = null;

			Long PartialFuel_Tank_ID = null;
			Integer OldPartialTank_closingKM = null;

			try {

				List<Fuel> fuelkmDESC = fuelService.findMissingOddmeterFirstDESC_Vaule_Liter_Amount(fuelDto.getVid(),
						fuelmeterNow, fuel.getFuel_date());
				if (fuelkmDESC != null && !fuelkmDESC.isEmpty()) {
					for (Fuel fuelDtoDESC : fuelkmDESC) {

						/** Delete Fuel Entries edit id not take the values */
						if (!(fuelDtoDESC.getFuel_id().equals(fuelDto.getFuel_id()))) {
							// 2000,1900,1800...ets
							// get the Fuel_tank_partial value and equal to one
							// or
							// not
							if (fuelDtoDESC.getFuel_tank_partial() == 1) {

								usagePartialMISSING += fuelDtoDESC.getFuel_usage();
								litersPartialMISSING += fuelDtoDESC.getFuel_liters();
								AmountPartialMISSING += fuelDtoDESC.getFuel_amount();

							} else {
								break; // stop the loop when Tank_partial value
										// == 0
							}
						}
					}
				}

				List<Fuel> fuelkmASC = fuelService.findMissingOddmeterSecondASC_Vaule_Liter_Amount_DELETE(
						fuelDto.getVid(), fuelmeterNow, fuel.getFuel_date());
				if (fuelkmASC != null && !fuelkmASC.isEmpty()) {
					for (Fuel fuelDtoASC : fuelkmASC) {
						/** Delete Fuel Entries edit id not take the values */
						if (!(fuelDtoASC.getFuel_id().equals(fuelDto.getFuel_id()))) {

							/*
							 * if enter the missing fuel tank is full not calution before enter don't cal
							 */
							if (fuelDto.getFuel_tank_partial() != 0) {

								// get the Fuel_tank_partial value and equal to
								// one or
								// not
								if (fuelDtoASC.getFuel_tank_partial() == 1) {

									usagePartialMISSING += fuelDtoASC.getFuel_usage();
									litersPartialMISSING += fuelDtoASC.getFuel_liters();
									AmountPartialMISSING += fuelDtoASC.getFuel_amount();

								} else {
									OldFullTank_OpringKM = fuelDtoASC.getFuel_meter_old();

									usagePartialMISSING += fuelDtoASC.getFuel_usage();
									litersPartialMISSING += fuelDtoASC.getFuel_liters();
									AmountPartialMISSING += fuelDtoASC.getFuel_amount();

									FuellFullTank_ID = fuelDtoASC.getFuel_id();

									FuellFull_Tank_meter = fuelDtoASC.getFuel_meter();
									break; // stop the loop when Tank_partial
											// value == 0
								}

							} else {

								FuellFullTank_ID = fuelDtoASC.getFuel_id();

								FuellFull_Tank_meter = fuelDtoASC.getFuel_meter();

								OldFullTank_OpringKM = fuelDtoASC.getFuel_meter();
								FuellFullTank_ID = fuelDtoASC.getFuel_id();

								break; // stop the loop when Tank_partial value
										// == 0
							}

						}
					}
				}

				if (fuelkmASC != null && !fuelkmASC.isEmpty()) {
					for (Fuel fuel2 : fuelkmASC) {

						/** Delete Fuel Entries edit id not take the values */
						if (!(fuel2.getFuel_id().equals(fuelDto.getFuel_id()))) {
							// get the this update before vehicle id and meter
							// value
							if (fuel2.getFuel_tank_partial() == 1) {

								OldFullTank_OpringKM = fuel2.getFuel_meter_old();

								PartialFuel_Tank_ID = fuel2.getFuel_id();
								OldPartialTank_closingKM = fuel2.getFuel_meter();

								// before enter update usage and opening
								// oddmeter in
								// delete the fuel to get old_meter assign next
								// opening
								// km and then next (closing km -delete old
								// meter)=usage

								fuelService.update_Missing_OddMeter_Usage_Partial(fuelDto.getFuel_meter_old(),
										OldPartialTank_closingKM - fuelDto.getFuel_meter_old(), PartialFuel_Tank_ID, (Integer) fuel2.getCompanyId());

								break;
							} else {
								OldFullTank_OpringKM = fuel2.getFuel_meter_old();

								PartialFuel_Tank_ID = fuel2.getFuel_id();
								OldPartialTank_closingKM = fuel2.getFuel_meter();

								// before enter update usage and opening
								// oddmeter in
								// delete the fuel to get old_meter assign next
								// opening
								// km and then next (closing km -delete old
								// meter)=usage

								fuelService.update_Missing_OddMeter_Usage_Partial(fuelDto.getFuel_meter_old(),
										OldPartialTank_closingKM - fuelDto.getFuel_meter_old(), PartialFuel_Tank_ID, fuel2.getCompanyId());

								break;
							}
						}
					}
				}

				// get old meter
				Integer fuelmeterOld = 0;

				// get opening oddmeter in enter oddmeter value
				List<Fuel> fuelkmDESC_OPEN_ODDMeter = fuelService
						.findMissingOddmeterFirstDESC_Vaule_Liter_Amount_DELETE(fuelDto.getVid(), fuelmeterNow,
								fuel.getFuel_date());

				if (fuelkmDESC_OPEN_ODDMeter != null && !fuelkmDESC_OPEN_ODDMeter.isEmpty()) {
					for (Fuel fuelDtoDESC_OPEN : fuelkmDESC_OPEN_ODDMeter) {

						/** Delete Fuel Entries edit id not take the values */
						if (!(fuelDtoDESC_OPEN.getFuel_id().equals(fuelDto.getFuel_id()))) {

							fuelmeterOld = fuelDtoDESC_OPEN.getFuel_meter();
							break; // stop the loop when Tank_partial value == 0
						}
					}
				}

				if (OldFullTank_OpringKM == null) {
					OldFullTank_OpringKM = 0;
				}
				Integer OldFullTank_OpringKM_Usage = (OldFullTank_OpringKM - fuelDto.getFuel_usage());
				// after missing oddmter after get the value of that fuel
				// enterties values
				Integer OldFull_AfterMissingMeterOpring = (OldFullTank_OpringKM - fuelDto.getFuel_usage());

				// get usage fuel calculation
				Integer usagefuel = (fuelmeterNow - fuelmeterOld);
				// save usage
				fuel.setFuel_usage(usagefuel);

				// save Opeing Km Meter
				fuel.setFuel_meter_old(fuelmeterOld);

				/*****************************************
				 * Delete FUll Tank
				 *************************************/
				// tank full mean update that full value row not full search for
				// full tank entries in full entries
				if (tank == 0) {

					// Missing Tank Full update the oddmeter in same full id
					// that calculation

					Integer usagefuelTotal = usagePartialMISSING + usagefuel;
					Double literTotal = litersPartialMISSING;
					Double km = (usagefuelTotal / literTotal);

					fuel.setFuel_kml(round(km, 2));

					Double amountfuelTotal = AmountPartialMISSING + amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}
					fuel.setFuel_cost(round(cost, 2));

					/******************************************************************/
					// this code check get Old Full tank value of the cost and
					// km/l cost update
					Long OldFUELL_id = (long) 0;
					Integer usagePartialOldFUELL = 0;
					Double litersPartialOldFUELL = 0.0;
					Double AmountPartialOldFUELL = 0.0;

					List<Fuel> fuelkmDESC_updateOldFuel_cost = fuelService
							.findMissingOddmeterFirstDESC_Vaule_Liter_Amount_DELETE(fuelDto.getVid(), fuelmeterNow,
									fuel.getFuel_date());
					if (fuelkmDESC_updateOldFuel_cost != null && !fuelkmDESC_updateOldFuel_cost.isEmpty()) {
						for (Fuel fuelDtoDESC_oldfull : fuelkmDESC_updateOldFuel_cost) {
							/**
							 * Delete Fuel Entries edit id not take the values
							 */
							if (!(fuelDtoDESC_oldfull.getFuel_id().equals(fuelDto.getFuel_id()))) {
								// get the Fuel_tank_partial value and equal to
								// one or
								// not
								if (fuelDtoDESC_oldfull.getFuel_tank_partial() == 1) {

									usagePartialOldFUELL += fuelDtoDESC_oldfull.getFuel_usage();
									litersPartialOldFUELL += fuelDtoDESC_oldfull.getFuel_liters();
									AmountPartialOldFUELL += fuelDtoDESC_oldfull.getFuel_amount();

								} else {

									break; // stop the loop when Tank_partial
											// value == 0
								}
							}
						}
					}

					List<Fuel> fuelkmASC_updateOldFuel_cost = fuelService
							.findMissingOddmeterSecondASC_Vaule_Liter_Amount_DELETE(fuelDto.getVid(), fuelmeterNow,
									fuel.getFuel_date());
					if (fuelkmASC_updateOldFuel_cost != null && !fuelkmASC_updateOldFuel_cost.isEmpty()) {
						for (Fuel fuelDtoASC_oldfull : fuelkmASC_updateOldFuel_cost) {

							/**
							 * Delete Fuel Entries edit id not take the values
							 */
							if (!(fuelDtoASC_oldfull.getFuel_id().equals(fuelDto.getFuel_id()))) {

								// get the Fuel_tank_partial value and equal to
								// one or
								// not
								if (fuelDtoASC_oldfull.getFuel_tank_partial() == 1) {

									usagePartialOldFUELL += fuelDtoASC_oldfull.getFuel_usage();
									litersPartialOldFUELL += fuelDtoASC_oldfull.getFuel_liters();
									AmountPartialOldFUELL += fuelDtoASC_oldfull.getFuel_amount();

								} else {

									usagePartialOldFUELL += fuelDtoASC_oldfull.getFuel_usage();
									litersPartialOldFUELL += fuelDtoASC_oldfull.getFuel_liters();
									AmountPartialOldFUELL += fuelDtoASC_oldfull.getFuel_amount();

									OldFUELL_id = fuelDtoASC_oldfull.getFuel_id();
									break; // stop the loop when Tank_partial
											// value == 0
								}
							}

						} // for loop close
					}

					// this update Missing oddmeter to add in Next Fuel entries
					// Full calculation cost and km/L

					Double KM_OLdNextFULL = (usagePartialOldFUELL / litersPartialOldFUELL);

					Double cost_OLdNextFULL = (AmountPartialOldFUELL / usagePartialOldFUELL);

					if (KM_OLdNextFULL == 0.0) {
						cost_OLdNextFULL = 0.0;
					}

					fuelService.update_Missing_FuelNextFull_KM_cost(OldFUELL_id, round(KM_OLdNextFULL, 2),
							round(cost_OLdNextFULL, 2), userDetails.getCompany_id());

				} else {
					/*********************************/
					/* Tank partial missing oddmeter */

					// this update Missing oddmeter calculation that id
					Integer usagefuelTotal = usagePartialMISSING /* + usagefuel */;

					Double literTotal = litersPartialMISSING - (fuelDto.getFuel_liters());

					/// *********************************/
					/* Tank partial missing oddmeter */

					Double km = (usagefuelTotal / literTotal);
					Double amountfuelTotal = AmountPartialMISSING - amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}

					fuelService.update_Missing_FuelNextFull_KM_cost(FuellFullTank_ID, round(km, 2), round(cost, 2), userDetails.getCompany_id());
				}

			} catch (Exception e) {
				e.printStackTrace();

			}

			fuel.setFuel_meter_attributes(fuelDto.getFuel_meter_attributes());
			fuel.setFuel_liters(fuelDto.getFuel_liters());
			fuel.setFuel_price(fuelDto.getFuel_price());

			if (0 != fuelDto.getVendor_id()) {
				fuel.setVendor_id(fuelDto.getVendor_id());
			} else {
				fuel.setVendor_id(0);
			}

			fuel.setFuel_tank(0);
		}

		// fuel.setFuel_payment(fuelDto.getFuel_payment());
		fuel.setFuel_tank_partial(fuelDto.getFuel_tank());
		fuel.setFuel_reference(fuelDto.getFuel_reference());

		if (fuelDto.getDriver_id() != null && fuelDto.getDriver_id() != 0) {
			fuel.setDriver_id(fuelDto.getDriver_id());
			// fuel.setDriver_name(null);
		} else {
			fuel.setDriver_id(0);
			// fuel.setDriver_name(fuelDto.getDriver_name());
		}

		fuel.setFuel_personal(fuelDto.getFuel_personal());

		fuel.setFuel_comments(fuelDto.getFuel_comments());

		return fuel;
	}

	/********************************************************************************************************************
	 * Delete to add fuel Entries in full tank and partical
	 * 
	 * @throws Exception
	 *******************************************************************************************************************
	 */
	// get Save Fuel
	@SuppressWarnings("unused")
	public Fuel DeleteToEDITsaveFuel(FuelDto fuelDto) throws Exception {
		Fuel fuel = new Fuel();

		fuel.setFuel_id(fuelDto.getFuel_id());
		fuel.setVid(fuelDto.getVid());
		CustomUserDetails userDetails = null;
		try {
			// show Vehicle name
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			java.util.Date date = dateFormat.parse(fuelDto.getFuel_date());
			java.sql.Date FuelDate = new Date(date.getTime());
			fuel.setFuel_date(FuelDate);
		} catch (Exception e) {
			LOGGER.error("Fuel Page:", e);

		}

		fuel.setFuel_meter(fuelDto.getFuel_meter());

		{

			// missing collection
			/* save to usage km is vehicle */
			// get enter the fuel meter
			Integer fuelmeterNow = fuelDto.getFuel_meter();

			/* save to amount of fuel enter */
			// get fuel volume
			Double fuelliter = fuelDto.getFuel_liters();
			// get fuel price
			Double fuelprice = fuelDto.getFuel_price();
			// get amount of fuel calculation
			Double amountfuel = fuelliter * fuelprice;

			fuel.setFuel_amount(round(amountfuel, 2));
			/* save to km/L of fuel enter */
			Integer tank = fuelDto.getFuel_tank_partial();

			// System.out.println("Fuel Tank 1=" + tank);

			Integer usagePartialMISSING = 0;
			Double litersPartialMISSING = 0.0;
			Double AmountPartialMISSING = 0.0;

			Long FuellFullTank_ID = null;
			Integer FuellFull_Tank_meter = 0;

			Integer OldFullTank_OpringKM = null;

			Long PartialFuel_Tank_ID = null;
			Integer OldPartialTank_closingKM = null;

			// get old meter
			Integer fuelmeterLessthen = 0;

			// get opening oddmeter in enter oddmeter value off less then
			// oddmeter value off vehicle meter
			List<Fuel> fuelkmDESC_OPEN_ODDMeter_less = fuelService
					.findMissingOddmeterFirstDESC_Vaule_Liter_Amount_DELETE(fuelDto.getVid(),
							fuelDto.getFuel_meter_old(), fuel.getFuel_date());

			for (Fuel fuelDtoDESC_OPEN : fuelkmDESC_OPEN_ODDMeter_less) {
				// This less then value thi delete the fuel enter meter opening
				// less the one entries
				fuelmeterLessthen = fuelDtoDESC_OPEN.getFuel_meter();
				break; // stop the loop when Tank_partial value == 0

			}

			try {

				List<Fuel> fuelkmDESC = fuelService.findMissingOddmeterFirstDESC_Vaule_Liter_Amount(fuelDto.getVid(),
						fuelDto.getFuel_meter_old(), fuel.getFuel_date());

				for (Fuel fuelDtoDESC : fuelkmDESC) {
					// get the Fuel_tank_partial value and equal to one or not
					if (fuelDtoDESC.getFuel_tank_partial() == 1) {

						usagePartialMISSING += fuelDtoDESC.getFuel_usage();
						litersPartialMISSING += fuelDtoDESC.getFuel_liters();
						AmountPartialMISSING += fuelDtoDESC.getFuel_amount();

					} else {
						break; // stop the loop when Tank_partial value == 0
					}
				}

				List<Fuel> fuelkmASC = fuelService.findMissingOddmeterSecondASC_Vaule_Liter_Amount_DELETE(
						fuelDto.getVid(), fuelDto.getFuel_meter_old(), fuel.getFuel_date());

				for (Fuel fuelDtoASC : fuelkmASC) {

					/*
					 * if enter the missing fuel tank is full not calution before enter don't cal
					 */
					if (fuelDto.getFuel_tank_partial() != 0) {

						// get the Fuel_tank_partial value and equal to one or
						// not
						if (fuelDtoASC.getFuel_tank_partial() == 1) {

							usagePartialMISSING += fuelDtoASC.getFuel_usage();
							litersPartialMISSING += fuelDtoASC.getFuel_liters();
							AmountPartialMISSING += fuelDtoASC.getFuel_amount();

						} else {
							OldFullTank_OpringKM = fuelDtoASC.getFuel_meter_old();

							usagePartialMISSING += fuelDtoASC.getFuel_usage();
							litersPartialMISSING += fuelDtoASC.getFuel_liters();
							AmountPartialMISSING += fuelDtoASC.getFuel_amount();

							FuellFullTank_ID = fuelDtoASC.getFuel_id();

							FuellFull_Tank_meter = fuelDtoASC.getFuel_meter();
							break; // stop the loop when Tank_partial value == 0
						}

					} else {

						FuellFullTank_ID = fuelDtoASC.getFuel_id();

						FuellFull_Tank_meter = fuelDtoASC.getFuel_meter();

						OldFullTank_OpringKM = fuelDtoASC.getFuel_meter();
						FuellFullTank_ID = fuelDtoASC.getFuel_id();
						break; // stop the loop when Tank_partial value == 0
					}
				}

				List<Fuel> fuelkmASC_Update = fuelService.findMissingOddmeterSecondASC_Vaule_Liter_Amount_DELETE(
						fuelDto.getVid(), fuelDto.getFuel_meter_old(), fuel.getFuel_date());
				for (Fuel fuel2 : fuelkmASC_Update) {
					// get the this update before vehicle id and meter value
					if (fuel2.getFuel_tank_partial() == 1) {

						OldFullTank_OpringKM = fuel2.getFuel_meter_old();

						PartialFuel_Tank_ID = fuel2.getFuel_id();
						OldPartialTank_closingKM = fuel2.getFuel_meter();

						// before enter update usage and opening oddmeter in
						// delete the fuel to get old_meter assign next opening
						// km and then next (closing km -delete old meter)=usage
						// System.out.println("Fuel Tank 2="+tank);
						fuelService.update_Missing_OddMeter_Usage_Partial(fuelmeterLessthen,
								OldPartialTank_closingKM - fuelmeterLessthen, PartialFuel_Tank_ID, userDetails.getCompany_id());

						break;
					} else {
						OldFullTank_OpringKM = fuel2.getFuel_meter_old();

						PartialFuel_Tank_ID = fuel2.getFuel_id();
						OldPartialTank_closingKM = fuel2.getFuel_meter();

						// before enter update usage and opening oddmeter in
						// delete the fuel to get old_meter assign next opening
						// km and then next (closing km -delete old meter)=usage
						// System.out.println("Fuel Tank 3="+tank);
						fuelService.update_Missing_OddMeter_Usage_Partial(fuelmeterLessthen,
								OldPartialTank_closingKM - fuelmeterLessthen, PartialFuel_Tank_ID, userDetails.getCompany_id());

						break;
					}
				}

				// get old meter
				Integer fuelmeterOld = 0;

				// get opening oddmeter in enter oddmeter value
				List<Fuel> fuelkmDESC_OPEN_ODDMeter = fuelService
						.findMissingOddmeterFirstDESC_Vaule_Liter_Amount_DELETE(fuelDto.getVid(),
								fuelDto.getFuel_meter_old(), fuel.getFuel_date());

				for (Fuel fuelDtoDESC_OPEN : fuelkmDESC_OPEN_ODDMeter) {

					fuelmeterOld = fuelDtoDESC_OPEN.getFuel_meter();
					break; // stop the loop when Tank_partial value == 0

				}

				if (OldFullTank_OpringKM == null) {
					OldFullTank_OpringKM = 0;
				}
				// next full tank opening meter and delete usage subtract
				// 600-100=500
				Integer OldFullTank_OpringKM_Usage = (OldFullTank_OpringKM - fuelDto.getFuel_usage());
				// after missing oddmter after get the value of that fuel
				// enterties values
				Integer OldFull_AfterMissingMeterOpring = (OldFullTank_OpringKM - fuelDto.getFuel_usage());
				// get usage fuel calculation
				Integer usagefuel = (fuelmeterNow - fuelmeterOld);
				// save usage
				fuel.setFuel_usage(usagefuel);

				// save Opeing Km Meter
				fuel.setFuel_meter_old(fuelmeterOld);

				/*****************************************
				 * Delete FUll Tank
				 *************************************/
				// tank full mean update that full value row not full search for
				// full tank entries in full entries
				if (tank == 0) {

					// Missing Tank Full update the oddmeter in same full id
					// that calculation

					Integer usagefuelTotal = usagePartialMISSING + usagefuel;
					Double literTotal = litersPartialMISSING;

					Double km = (usagefuelTotal / literTotal);

					fuel.setFuel_kml(round(km, 2));

					Double amountfuelTotal = AmountPartialMISSING + amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}
					fuel.setFuel_cost(round(cost, 2));

					/******************************************************************/
					// this code check get Old Full tank value of the cost and
					// km/l cost update
					Long OldFUELL_id = (long) 0;
					Integer usagePartialOldFUELL = 0;
					Double litersPartialOldFUELL = 0.0;
					Double AmountPartialOldFUELL = 0.0;

					List<Fuel> fuelkmDESC_updateOldFuel_cost = fuelService
							.findMissingOddmeterFirstDESC_Vaule_Liter_Amount_DELETE(fuelDto.getVid(), fuelmeterNow,
									fuel.getFuel_date());

					for (Fuel fuelDtoDESC_oldfull : fuelkmDESC_updateOldFuel_cost) {
						if (fuelDtoDESC_oldfull.getFuel_tank_partial() == 1) {

							usagePartialOldFUELL += fuelDtoDESC_oldfull.getFuel_usage();
							litersPartialOldFUELL += fuelDtoDESC_oldfull.getFuel_liters();
							AmountPartialOldFUELL += fuelDtoDESC_oldfull.getFuel_amount();

						} else {
							break; // stop the loop when Tank_partial value == 0
						}
					}

					List<Fuel> fuelkmASC_updateOldFuel_cost = fuelService
							.findMissingOddmeterSecondASC_Vaule_Liter_Amount_DELETE(fuelDto.getVid(), fuelmeterNow,
									fuel.getFuel_date());

					for (Fuel fuelDtoASC_oldfull : fuelkmASC_updateOldFuel_cost) {

						// get the Fuel_tank_partial value and equal to one or
						// not
						if (fuelDtoASC_oldfull.getFuel_tank_partial() == 1) {

							usagePartialOldFUELL += fuelDtoASC_oldfull.getFuel_usage();
							litersPartialOldFUELL += fuelDtoASC_oldfull.getFuel_liters();
							AmountPartialOldFUELL += fuelDtoASC_oldfull.getFuel_amount();

						} else {

							usagePartialOldFUELL += fuelDtoASC_oldfull.getFuel_usage();
							litersPartialOldFUELL += fuelDtoASC_oldfull.getFuel_liters();
							AmountPartialOldFUELL += fuelDtoASC_oldfull.getFuel_amount();

							OldFUELL_id = fuelDtoASC_oldfull.getFuel_id();
							break; // stop the loop when Tank_partial value == 0
						}

					} // for loop close

					// this update Missing oddmeter to add in Next Fuel entries
					// Full calculation cost and km/L

					Double KM_OLdNextFULL = (usagePartialOldFUELL / litersPartialOldFUELL);

					Double cost_OLdNextFULL = (AmountPartialOldFUELL / usagePartialOldFUELL);

					if (KM_OLdNextFULL == 0.0) {
						cost_OLdNextFULL = 0.0;
					}

					fuelService.update_Missing_FuelNextFull_KM_cost(OldFUELL_id, round(KM_OLdNextFULL, 2),
							round(cost_OLdNextFULL, 2), userDetails.getCompany_id());

				} else {
					/*********************************/
					/* Tank partial missing oddmeter */

					// this update Missing oddmeter calculation that id
					Integer usagefuelTotal = usagePartialMISSING /* + usagefuel */;

					Double literTotal = litersPartialMISSING - (fuelDto.getFuel_liters());

					/// *********************************/

					Double km = (usagefuelTotal / literTotal);

					Double amountfuelTotal = AmountPartialMISSING - amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}

					fuelService.update_Missing_FuelNextFull_KM_cost(FuellFullTank_ID, round(km, 2), round(cost, 2), userDetails.getCompany_id());

				}

			} catch (Exception e) {
				e.printStackTrace();

			}

			fuel.setFuel_meter_attributes(fuelDto.getFuel_meter_attributes());
			fuel.setFuel_liters(fuelDto.getFuel_liters());
			fuel.setFuel_price(fuelDto.getFuel_price());

			if (0 != fuelDto.getVendor_id()) {
				fuel.setVendor_id(fuelDto.getVendor_id());
			} else {
				fuel.setVendor_id(0);
			}

			fuel.setFuel_tank(0);
		}

		fuel.setFuel_tank_partial(fuelDto.getFuel_tank());
		fuel.setFuel_reference(fuelDto.getFuel_reference());

		if (fuelDto.getDriver_id() != null && fuelDto.getDriver_id() != 0) {
			fuel.setDriver_id(fuelDto.getDriver_id());
			// fuel.setDriver_name(null);
		} else {
			fuel.setDriver_id(0);
			// fuel.setDriver_name(fuelDto.getDriver_name());
		}
		fuel.setFuel_personal(fuelDto.getFuel_personal());

		fuel.setFuel_comments(fuelDto.getFuel_comments());

		return fuel;
	}

	// -----------------------------------------------------------------------------
	// get Save Fuel
	@SuppressWarnings("unused")
	public Fuel EDITTOsaveFuel(FuelDto fuelDto) throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Fuel fuel = new Fuel();

		fuel.setFuel_id(fuelDto.getFuel_id());
		fuel.setFuel_Number(fuelDto.getFuel_Number());
		fuel.setVid(fuelDto.getVid());
		fuel.setCreatedById(fuelDto.getCreatedById());
		fuel.setGpsOdometer(fuelDto.getGpsOdometer());
		fuel.setTallyCompanyId(fuelDto.getTallyCompanyId());
		fuel.setFuel_liters(fuelDto.getFuel_liters());
		
		if(fuelDto.getFuelTime() != null && !fuelDto.getFuelTime().trim().equalsIgnoreCase("")) {
				String start_time = "00:00";
				start_time	= fuelDto.getFuelTime();
				
				java.util.Date fuelDateTime = DateTimeUtility.getDateTimeFromDateTimeString(fuelDto.getFuel_date(), start_time);
				fuel.setFuelDateTime(fuelDateTime);
		}

		Integer Curentvehicle_Oddmeter = 0;
		try {
			VehicleDto vehiclename = vehicleService.getVehicel_Details_Fuel_entries(fuelDto.getVid());
			// show Vehicle name
			Curentvehicle_Oddmeter = vehiclename.getVehicle_Odometer();
			// fuel date converted String to date Format
			java.util.Date date = dateFormat.parse(fuelDto.getFuel_date());
			java.sql.Date FuelDate = new Date(date.getTime());
			fuel.setFuel_date(FuelDate);
		} catch (Exception e) {
			LOGGER.error("Fuel Page:", e);

		}

		fuel.setFuel_meter(fuelDto.getFuel_meter());

		fuel.setFuel_document(fuelDto.isFuel_document());
		fuel.setFuel_document_id(fuelDto.getFuel_document_id());

		// current vehicle oddmeter and edit the oddmeter value
		if (Curentvehicle_Oddmeter < fuelDto.getFuel_meter()) {

			/* save to usage km is vehicle */
			fuel.setFuel_meter_old(fuelDto.getFuel_meter_old());

			// get enter the fuel meter
			Integer fuelmeterNow = fuelDto.getFuel_meter();
			// get old meter
			Integer fuelmeterOld = fuelDto.getFuel_meter_old();
			// get usage fuel calculation
			Integer usagefuel = (fuelmeterNow - fuelmeterOld);
			// save usage
			fuel.setFuel_usage(usagefuel);

			/* save to amount of fuel enter */
			// get fuel volume
			Double fuelliter = fuelDto.getFuel_liters();
			// get fuel price
			Double fuelprice = fuelDto.getFuel_price();
			// get amount of fuel calculation
			Double amountfuel = fuelliter * fuelprice;
			// Double amountFuelRound=new BigDecimal(amountfuel).setScale(2,
			// BigDecimal.ROUND_HALF_UP).doubleValue();
			fuel.setFuel_amount(round(amountfuel, 2));
			/* save to km/L of fuel enter */
			Integer tank = fuelDto.getFuel_tank();
			if (tank == 0) {
				// get Vehicle id to Vehicle name

				Integer vehicleid = fuelDto.getVid();

				Integer tank1 = 1;

				Integer usagePartial = 0;
				Double litersPartial = 0.0;
				Double AmountPartial = 0.0;

				try {
					List<FuelDto> fuelkm = prepareOfGetvehicelpartialFuel(
							fuelService.listOfGetvehicelpartialFuel(vehicleid, tank1));
					if (fuelkm != null && !fuelkm.isEmpty()) {
						for (FuelDto fuelDto2 : fuelkm) {
							/** Edit Fuel Entries edit id not take the values */
							if (!(fuelDto2.getFuel_id().equals(fuelDto.getFuel_id()))) {
								usagePartial += fuelDto2.getFuel_usage();
								litersPartial += fuelDto2.getFuel_liters();
								if(fuelDto2.getFuel_amount() != null)
									AmountPartial += fuelDto2.getFuel_amount();
							}

						}
					}

					Integer usagefuelTotal = usagePartial + usagefuel;
					Double literTotal = litersPartial + (fuelDto.getFuel_liters());

					Double km = (usagefuelTotal / literTotal);

					fuel.setFuel_kml(round(km, 2));

					Double amountfuelTotal = AmountPartial + amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}
					fuel.setFuel_cost(round(cost, 2));

				} catch (Exception e) {

					e.printStackTrace();
				}

			}

			fuel.setFuel_meter_attributes(fuelDto.getFuel_meter_attributes());
			fuel.setFuel_price(fuelDto.getFuel_price());
			fuel.setFuel_tank(fuelDto.getFuel_tank());

			if (0 != fuelDto.getVendor_id()) {
				fuel.setVendor_id(fuelDto.getVendor_id());
			} else {
				fuel.setVendor_id(0);
			}

			// this else this greanthan value of false the calculation
		} else {

			// missing collection
			/* save to usage km is vehicle */
			// get enter the fuel meter
			Integer fuelmeterNow = fuelDto.getFuel_meter();

			/* save to amount of fuel enter */
			// get fuel volume
			Double fuelliter = fuelDto.getFuel_liters();
			// get fuel price
			Double fuelprice = fuelDto.getFuel_price();
			// get amount of fuel calculation
			Double amountfuel = fuelliter * fuelprice;

			fuel.setFuel_amount(round(amountfuel, 2));
			fuel.setBalanceAmount(round(amountfuel, 2));
			/* save to km/L of fuel enter */
			Integer tank = fuelDto.getFuel_tank();

			Integer usagePartialMISSING = 0;
			Double litersPartialMISSING = 0.0;
			Double AmountPartialMISSING = 0.0;

			Long FuellFullTank_ID = null;

			Integer OldFullTank_OpringKM = null;

			Long PartialFuel_Tank_ID = null;
			Integer OldPartialTank_closingKM = null;

			try {

				List<Fuel> fuelkmDESC = fuelService.findMissingOddmeterFirstDESC_Vaule_Liter_Amount(fuelDto.getVid(),
						fuelmeterNow, fuel.getFuel_date());
				if (fuelkmDESC != null && !fuelkmDESC.isEmpty()) {
					for (Fuel fuelDtoDESC : fuelkmDESC) {
							
						/** Edit Fuel Entries edit id not take the values */
						if (!(fuelDtoDESC.getFuel_id().equals(fuelDto.getFuel_id()))) {
							// or not
							if (fuelDtoDESC.getFuel_tank_partial() == 1) {

								usagePartialMISSING += fuelDtoDESC.getFuel_usage();
								litersPartialMISSING += fuelDtoDESC.getFuel_liters();
								AmountPartialMISSING += fuelDtoDESC.getFuel_amount();

							} else {
								break; // stop the loop when Tank_partial value
										// == 0
							}
						}
					}
				}

				List<Fuel> fuelkmASC_Update = fuelService.findMissingOddmeterSecondASC_Vaule_Liter_Amount(
						fuelDto.getVid(), fuelmeterNow, fuel.getFuel_date());

				if (fuelkmASC_Update != null && !fuelkmASC_Update.isEmpty()) {
					for (Fuel fuel2 : fuelkmASC_Update) {
						
						/** Edit Fuel Entries edit id not take the values */
						if (!(fuel2.getFuel_id().equals(fuelDto.getFuel_id()))) {
							// get the this update before vehicle id and meter
							// value
							if (fuel2.getFuel_tank_partial() == 1) {

								OldFullTank_OpringKM = fuel2.getFuel_meter();
								// System.out.println(OldFullTank_OpringKM);

								PartialFuel_Tank_ID = fuel2.getFuel_id();
								OldPartialTank_closingKM = fuel2.getFuel_meter();
								// before enter update usage and opening
								// oddmeter

								fuelService.update_Missing_OddMeter_Usage_Partial(fuelDto.getFuel_meter(),
										OldPartialTank_closingKM - fuel.getFuel_meter(), PartialFuel_Tank_ID, userDetails.getCompany_id());

								break;
							} else {

								OldFullTank_OpringKM = fuel2.getFuel_meter();

								PartialFuel_Tank_ID = fuel2.getFuel_id();
								OldPartialTank_closingKM = fuel2.getFuel_meter();
								// before enter update usage and opening
								// oddmeter

								fuelService.update_Missing_OddMeter_Usage_Partial(fuelDto.getFuel_meter(),
										OldPartialTank_closingKM - fuel.getFuel_meter(), PartialFuel_Tank_ID, userDetails.getCompany_id());

								break;
							}
						}
					}
				}

				List<Fuel> fuelkmASC = fuelService.findMissingOddmeterSecondASC_Vaule_Liter_Amount(fuelDto.getVid(),
						fuelmeterNow, fuel.getFuel_date());

				if (fuelkmASC != null && !fuelkmASC.isEmpty()) {
					for (Fuel fuelDtoASC : fuelkmASC) {
						
						/** Edit Fuel Entries edit id not take the values */
						if (!(fuelDtoASC.getFuel_id().equals(fuelDto.getFuel_id()))) {
							/*
							 * if enter the missing fuel tank is full not calution before enter don't cal
							 */
							if (fuelDto.getFuel_tank() != 0) {

								// get the Fuel_tank_partial value and equal to
								// one or
								// not
								if (fuelDtoASC.getFuel_tank_partial() == 1) {

									usagePartialMISSING += fuelDtoASC.getFuel_usage();
									litersPartialMISSING += fuelDtoASC.getFuel_liters();
									AmountPartialMISSING += fuelDtoASC.getFuel_amount();

								} else {
									OldFullTank_OpringKM = fuelDtoASC.getFuel_meter();
									usagePartialMISSING += fuelDtoASC.getFuel_usage();
									litersPartialMISSING += fuelDtoASC.getFuel_liters();
									AmountPartialMISSING += fuelDtoASC.getFuel_amount();

									FuellFullTank_ID = fuelDtoASC.getFuel_id();
									break;
								}

							} else {

								FuellFullTank_ID = fuelDtoASC.getFuel_id();
								OldFullTank_OpringKM = fuelDtoASC.getFuel_meter();
								FuellFullTank_ID = fuelDtoASC.getFuel_id();
								break; // stop the loop when Tank_partial value
										// == 0
							}
						}
					}
				}

				// get old meter
				Integer fuelmeterOld = 0;

				// get opening oddmeter in enter oddmeter value
				List<Fuel> fuelkmDESC_OPEN_ODDMeter = fuelService.findMissingOddmeterFirstDESC_Vaule_Liter_Amount(
						fuelDto.getVid(), fuelmeterNow, fuel.getFuel_date());
				if (fuelkmDESC_OPEN_ODDMeter != null && !fuelkmDESC_OPEN_ODDMeter.isEmpty()) {
					for (Fuel fuelDtoDESC_OPEN : fuelkmDESC_OPEN_ODDMeter) {
						
						/** Edit Fuel Entries edit id not take the values */
						if (!(fuelDtoDESC_OPEN.getFuel_id().equals(fuelDto.getFuel_id()))) {

							fuelmeterOld = fuelDtoDESC_OPEN.getFuel_meter();
							break; // stop the loop when Tank_partial value == 0
						}
					}

				}
				if(fuelmeterOld == 0) {
					fuelmeterOld =	fuelDto.getFuel_meter_old();
				}
				
				if (OldFullTank_OpringKM == null) {
					OldFullTank_OpringKM = 0;
				}
				//
				Integer OldFullTank_OpringKM_Usage = (OldFullTank_OpringKM - fuel.getFuel_meter());
				// after missing oddmter after get the value of that fuel
				// enterties values
				Integer OldFull_AfterMissingMeterOpring = (OldFullTank_OpringKM) - (OldFullTank_OpringKM_Usage);

				// usagepatal missing oddmeter add next full tank useage meter
				/* usagePartialMISSING +=OldFullTank_OpringKM_Usage; */
				// get usage fuel calculation
				Integer usagefuel = (fuelmeterNow - fuelmeterOld);
				// save usage
				fuel.setFuel_usage(usagefuel);

				// save Opeing Km Meter
				fuel.setFuel_meter_old(fuelmeterOld);

				// tank full mean update that full value row not full search for
				// full tank entries in full entries
				if (tank == 0) {
					// Missing Tank Full update the oddmeter in same full id
					// that calculation

					Integer usagefuelTotal = usagePartialMISSING + usagefuel;
					Double literTotal = litersPartialMISSING + (fuelDto.getFuel_liters());

					Double km = (usagefuelTotal / literTotal);

					fuel.setFuel_kml(round(km, 2));

					Double amountfuelTotal = AmountPartialMISSING + amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}
					fuel.setFuel_cost(round(cost, 2));

					fuelService.update_Missing_OddMeter_Usage_Partial(fuel.getFuel_meter(),
							OldFullTank_OpringKM - fuel.getFuel_meter(), FuellFullTank_ID, userDetails.getCompany_id());

					/******************************************************************/
					// this code check get Old Full tank value of the cost and
					// km/l cost update
					Long OldFUELL_id = (long) 0;
					Integer usagePartialOldFUELL = 0;
					Double litersPartialOldFUELL = 0.0;
					Double AmountPartialOldFUELL = 0.0;

					List<Fuel> fuelkmASC_updateOldFuel_cost = fuelService
							.findMissingOddmeterSecondASC_Vaule_Liter_Amount(fuelDto.getVid(), fuelmeterNow,
									fuel.getFuel_date());
					if (fuelkmASC_updateOldFuel_cost != null && !fuelkmASC_updateOldFuel_cost.isEmpty()) {

						for (Fuel fuelDtoASC_oldfull : fuelkmASC_updateOldFuel_cost) {
							/** Edit Fuel Entries edit id not take the values */
							if (!(fuelDtoASC_oldfull.getFuel_id().equals(fuelDto.getFuel_id()))) {

								// get the Fuel_tank_partial value and equal to
								// one
								// or
								// not
								if (fuelDtoASC_oldfull.getFuel_tank_partial() == 1) {

									usagePartialOldFUELL += fuelDtoASC_oldfull.getFuel_usage();
									litersPartialOldFUELL += fuelDtoASC_oldfull.getFuel_liters();
									AmountPartialOldFUELL += fuelDtoASC_oldfull.getFuel_amount();
								} else {

									usagePartialOldFUELL += fuelDtoASC_oldfull.getFuel_usage();
									litersPartialOldFUELL += fuelDtoASC_oldfull.getFuel_liters();
									AmountPartialOldFUELL += fuelDtoASC_oldfull.getFuel_amount();

									OldFUELL_id = fuelDtoASC_oldfull.getFuel_id();
									break; // stop the loop when Tank_partial
											// value
											// == 0
								}
							}

						} // for loop close
					}

					// this update Missing oddmeter to add in Next Fuel entries
					// Full calculation cost and km/L

					Double KM_OLdNextFULL = (usagePartialOldFUELL / litersPartialOldFUELL);

					Double cost_OLdNextFULL = (AmountPartialOldFUELL / usagePartialOldFUELL);

					if (KM_OLdNextFULL == 0.0) {
						cost_OLdNextFULL = 0.0;
					}
					
					 fuelService.update_Missing_FuelNextFull_KM_cost(OldFUELL_id, round(KM_OLdNextFULL, 2),
								round(cost_OLdNextFULL, 2), userDetails.getCompany_id());
					

				} else {
					/*********************************
					 * ADD in UI Tank partial missing oddmeter
					 **********************************/
					// this update Missing oddmeter calculation that id
					Integer usagefuelTotal = usagePartialMISSING + usagefuel;
					Double literTotal = litersPartialMISSING + (fuelDto.getFuel_liters());

					/// *********************************/
					/* Tank partial missing oddmeter */

					Double km = (usagefuelTotal / literTotal);

					Double amountfuelTotal = AmountPartialMISSING + amountfuel;

					Double cost = (amountfuelTotal / usagefuelTotal);

					if (km == 0.0) {
						cost = 0.0;
					}

					fuelService.update_Missing_FuelNextFull_KM_cost(FuellFullTank_ID, round(km, 2), round(cost, 2), userDetails.getCompany_id());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			fuel.setFuel_meter_attributes(fuelDto.getFuel_meter_attributes());
			
			fuel.setFuel_price(fuelDto.getFuel_price());

			if (fuelDto.getVendor_id() != null && 0 != fuelDto.getVendor_id()) {
				fuel.setVendor_id(fuelDto.getVendor_id());
			} else {
				fuel.setVendor_id(0);
			}

			// that full
			fuel.setFuel_tank(0);
		}

		fuel.setFuel_tank_partial(fuelDto.getFuel_tank());
		fuel.setFuel_reference(fuelDto.getFuel_reference());
		
		if (fuelDto.getDriver_id()!= null && fuelDto.getDriver_id() > 0 ){
			fuel.setDriver_id(fuelDto.getDriver_id());
		} else {
			fuel.setDriver_id(0);
		}
		if (fuelDto.getSecDriverID()!= null && fuelDto.getSecDriverID() != 0){
			fuel.setSecDriverID(fuelDto.getSecDriverID());
		} else {
			fuel.setSecDriverID(0);
		}
		if (fuelDto.getCleanerID()!= null && fuelDto.getCleanerID() != 0){
			fuel.setCleanerID(fuelDto.getCleanerID());
		} else {
			fuel.setCleanerID(0);
		}
		if (fuelDto.getRouteID() !=  null && fuelDto.getRouteID() != 0 ){
			fuel.setRouteID(fuelDto.getRouteID());
		} else {
			fuel.setRouteID(0);
		}
		fuel.setFuel_personal(fuelDto.getFuel_personal());

		fuel.setFuel_comments(fuelDto.getFuel_comments());
		fuel.setFuelTypeId(fuelDto.getFuelTypeId());
		fuel.setPaymentTypeId(fuelDto.getPaymentTypeId());
		
		if (fuelDto.getFuel_TripsheetNumber() != null && fuelDto.getFuel_TripsheetNumber() > 0) {
			fuel.setFuel_TripsheetID(fuelService.getTripSheetIdByNumber(fuelDto.getFuel_TripsheetNumber()));
		} else {
			fuel.setFuel_TripsheetID((long) 0);
		}

		return fuel;
	}

	/***************************************************************************************************************************************
	 ************** Get Vehicle List drop down less Loading to Search
	 ***************************************************************************************************************************************/

	@RequestMapping(value = "/getVehicleListFuel", method = RequestMethod.POST)
	public void getVehicleList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		List<Vehicle> vehicle = vehicleService.SearchOnlyVehicleNAME(term);
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

	@RequestMapping(value = "/getDriverSearchListFuel", method = RequestMethod.POST)
	public void getDriverSearchListFuel(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<DriverDto> Driver = new ArrayList<DriverDto>();
			List<Driver> drivertype = driverService.getDriverDetailsNotInSuspend(term,userDetails);
			if (drivertype != null && !drivertype.isEmpty()) {
				for (Driver add : drivertype) {
					DriverDto wadd = new DriverDto();
					wadd.setDriver_id(add.getDriver_id());
					wadd.setDriver_firstname(add.getDriver_firstname());
					wadd.setDriver_Lastname(add.getDriver_Lastname());
					wadd.setDriver_empnumber(add.getDriver_empnumber());
					wadd.setDriver_mobnumber(add.getDriver_mobnumber());
					wadd.setDriver_fathername(add.getDriver_fathername());
					Driver.add(wadd);
				}
			}
			Gson gson = new Gson();
			// System.out.println("jsonStudents = " + gson.toJson(addresses));
			
			response.getWriter().write(gson.toJson(Driver));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value = "/getDriverSearchAllListFuel", method = RequestMethod.POST)
	public void getDriverSearchAllListFuel(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> drivertype = driverService.getDriverDetailsNotInSuspend(term,userDetails);
		if (drivertype != null && !drivertype.isEmpty()) {
			for (Driver add : drivertype) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_mobnumber(add.getDriver_mobnumber());
				wadd.setDriver_fathername(add.getDriver_fathername());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(Driver));
	}
	
	@RequestMapping(value = "/SearchOnlyAllCleanerNAME", method = RequestMethod.POST)
	public void SearchOnlyAllCleanerNAME(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		List<Driver> drivertype = driverService.SearchOnlyAllCleanerNAME(term);
		if (drivertype != null && !drivertype.isEmpty()) {
			for (Driver add : drivertype) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_mobnumber(add.getDriver_mobnumber());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(Driver));
	}

	@RequestMapping(value = "/getVendorSearchListFuel", method = RequestMethod.POST)
	public void getVendorSearchListFuel(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<Vendor> addresses = new ArrayList<Vendor>();
		List<Vendor> vendor = vendorService.SearchOnlyFuelVendorName(term, userDetails.getCompany_id());
		if (vendor != null && !vendor.isEmpty()) {
			for (Vendor add : vendor) {
				Vendor wadd = new Vendor();
				wadd.setVendorId(add.getVendorId());
				wadd.setVendorName(add.getVendorName());
				wadd.setVendorLocation(add.getVendorLocation());
				wadd.setOwnPetrolPump(add.getOwnPetrolPump());

				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(addresses));
	}

	// This Below All Fuel Vendor Entries
	// This /FuelVendor/1.in Controller is show the Only Vendor Fuel Entries
	@RequestMapping(value = "/FuelVendor/{pageNumber}", method = RequestMethod.GET)
	public String FuelVendorList(@PathVariable Integer pageNumber, Model model) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Vendor UserVendor = vendorService.Get_UserEmailId_To_vendor_Details(userDetails.getId());

		if (UserVendor != null) {
			Page<Fuel> page = fuelService.getDeployment_Page_FuelVendor_Page(pageNumber, UserVendor.getVendorId());
			try {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);

				model.addAttribute("FuelCount", page.getTotalElements());

				List<FuelDto> pageList = FuBL.prepareListofFuel(
						fuelService.list_FuelVendor_ListOf_FuelEntries(pageNumber, UserVendor.getVendorId()));

				model.addAttribute("fuel", pageList);

				/*
				 * Object[] count = vehicleService.countTotalVehicle_AND_Ownership();
				 * 
				 * model.put("VehicleCount", (Long) count[0]); model.put("VehicleOwnedCount",
				 * (Long) count[1]);
				 */

			} catch (NullPointerException e) {
				return "redirect:/NotFound.in";
			} catch (Exception e) {
				LOGGER.error("Vehicle Page:", e);
				e.printStackTrace();
			}
		}
		return "FuelVendor";
	}

	// This /searchFuelVendor Controller is Search Trip Sheet Details to Get
	// Vehicle TS Details
	@RequestMapping(value = "/searchFuelVendor", method = RequestMethod.POST)
	public ModelAndView searchFuelVendor(@RequestParam("tripSheetID") final Long TripSheetID,
			HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			int tripSheetFlavor = companyConfigurationService.getTripSheetFlavor(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				TripSheetDto trip = TripSheetService.Get_FuelVendor_SearchTo_TripSheetDetailsIN_Select(TripSheetID,
						userDetails.getCompany_id());
				model.put("TripSheet", trip);
			} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				TripCollectionSheetDto sheet = tripCollectionService
						.Get_FuelVendor_SearchTo_TripSheetDetailsIN(TripSheetID, userDetails.getCompany_id());
				model.put("TripSheet", sheet);
			} else if (tripSheetFlavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				TripDailySheetDto sheet = TripDailySheetService.Get_FuelVendor_SearchTo_TripSheetDetailsIN(TripSheetID,
						userDetails.getCompany_id());
				model.put("TripSheet", sheet);
			}

			if (model.get("TripSheet") != null) {
				// get the Trip sheet ID to Details
				Vendor UserVendor = vendorService.Get_UserEmailId_To_vendor_Details(userDetails.getId());

				model.put("Vendor", UserVendor);
				model.put("tripSheetFlavor", tripSheetFlavor);
			} else {
				model.put("NotFound", true);
				return new ModelAndView("redirect:/FuelVendor/1.in", model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("Vendor_FuelAdd", model);
	}

	/** Fuel module to save Fuel data */
	// save Fuel in databases
	@RequestMapping(value = "/saveFuelVendor", method = RequestMethod.POST)
	public ModelAndView saveFuelVendor(@ModelAttribute("command") FuelDto fuelDto,
			@RequestParam("input-file-preview") MultipartFile file, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails 				userDetails 				= null;
		FuelSequenceCounter 			sequenceCounter 			= null;
		List<ServiceReminderDto>		serviceList					= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Fuel> validate = fuelService.listFuelValidate(fuelDto.getVid(), fuelDto.getFuel_meter(),
					fuelDto.getFuel_liters(), fuelDto.getFuel_price(), fuelDto.getVendor_id(), fuelDto.getVendor_name(),
					fuelDto.getFuel_reference());

			if (validate != null && !validate.isEmpty()) {
				// show the driver list in all
				model.put("duplicateFuelEntries", true);
				return new ModelAndView("redirect:/FuelVendor/1.in?duplicateFuelEntries=true");

			} else {

				// get the fuel Dto to save fuel
				Fuel fuel = saveFuel(fuelDto);
				sequenceCounter = fuelSequenceService.findNextFuelNumber(userDetails.getCompany_id());
				if (sequenceCounter == null) {
					return new ModelAndView("redirect:/Fuel/1.in?FuelSequenceCounterMissing=true");
				}
				fuel.setCompanyId(userDetails.getCompany_id());
				fuel.setCreatedById(userDetails.getId());
				fuel.setLastModifiedById(userDetails.getId());
				fuel.setFuel_Number(sequenceCounter.getNextVal());

				if (fuel.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
					// fuel.setFuel_vendor_paymode("PAID");
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);

					java.util.Date currentDate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

					fuel.setFuel_vendor_paymentdate(toDate);

				} else {
					// fuel.setFuel_vendor_paymode("NOTPAID");
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
				}

				try {
					// save to fuel entry in databases
					java.util.Date currentUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentUpdate.getTime());
					fuel.setCreated(toDate);
					fuel.setLastupdated(toDate);

					// fuel.setCreatedBy(userDetails.getEmail());
					// fuel.setLastModifiedBy(userDetails.getEmail());
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (!file.isEmpty()) {

					fuel.setFuel_document(true);
					fuel.setFuel_document_id((long) 0);
				} else {

					fuel.setFuel_document(false);
					fuel.setFuel_document_id((long) 0);
				}

				fuel.setMarkForDelete(false);
				fuelService.addFuel(fuel);
				// fuelSequenceService.updateNextFuelNumber(sequenceCounter.getNextVal() + 1,
				// userDetails.getCompany_id(), sequenceCounter.getSequence_Id());

				if (!file.isEmpty()) {

					org.fleetopgroup.persistence.document.FuelDocument fuelDoc = new org.fleetopgroup.persistence.document.FuelDocument();

					fuelDoc.setFuel_id(fuel.getFuel_id());
					try {

						byte[] bytes = file.getBytes();
						fuelDoc.setFuel_filename(file.getOriginalFilename());
						fuelDoc.setFuel_content(bytes);
						fuelDoc.setFuel_contentType(file.getContentType());

						/** Set Status in Issues */
						fuelDoc.setMarkForDelete(false);

						/**
						 * who Create this Issues get email id to user profile details
						 */

						fuelDoc.setCreatedById(userDetails.getId());
						fuelDoc.setLastModifiedById(userDetails.getId());


						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						/** Set Created Current Date */
						fuelDoc.setCreated(toDate);
						fuelDoc.setLastupdated(toDate);
						fuelDoc.setCompanyId(userDetails.getCompany_id());
					} catch (IOException e) {
						e.printStackTrace();
					}

					// Note: Save FuelDocument Details
					fuelService.add_Fuel_To_FuelDocument(fuelDoc);

					// Note: FuelDocument to Save id to Fuel save
					fuelService.Update_FuelDocument_ID_to_Fuel(fuelDoc.get_id(), true, fuel.getFuel_id(),
							userDetails.getCompany_id());
				}

				// show the driver list in all
				model.put("saveFuel", true);
				// update the Current Odometer vehicle Databases tables
				try {
					Vehicle	vehicle	= vehicleService.getVehicel1(fuel.getVid());
					
					if (vehicle.getVehicle_Odometer() < fuelDto.getFuel_meter()) {
						
						vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), userDetails.getCompany_id());
						// update current Odometer update Service Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),
								fuel.getFuel_meter(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(fuel.getVid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									//emailAlertQueueService.sendEmailServiceOdometer(list);
									//smsAlertQueueService.sendSmsServiceOdometer(list);
								}
							}
						}

						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToFuel(fuel);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);

					}
				} catch (Exception e) {
					LOGGER.error("Fuel Page:", e);
					e.printStackTrace();

				}
				if (fuelDto.getFuel_meter_old() <= fuelDto.getFuel_meter()) {
					if (fuel.getFuel_tank() == 0) {

						// save to update partialFuel entry in databases
						fuelService.updatepartialFuel(fuel.getVid(), fuel.getFuel_tank(), userDetails.getCompany_id());
					}
				}

			}

		} catch (Exception e) {
			LOGGER.error("Fuel Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/FuelVendor/1.in?danger=true");
		}

		return new ModelAndView("redirect:/FuelVendor/1.in?Success=true");
	}

	public List<FuelDto> prepareListofFuelBean(List<Fuel> fuelList) throws Exception {
		List<FuelDto> beans = null;
		if (fuelList != null && !fuelList.isEmpty()) {
			SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");

			beans = new ArrayList<FuelDto>();
			FuelDto fuel = null;

			for (Fuel fuelBean : fuelList) {

				fuel = new FuelDto();
				fuel.setFuel_id(fuelBean.getFuel_id());
				fuel.setFuel_Number(fuelBean.getFuel_Number());
				if (fuelBean.getFuel_TripsheetID() != null && fuelBean.getFuel_TripsheetID() > 0) {
					TripSheet TripID = TripSheetService
							.getTripSheet_ID_FUEL_SHOW_NUMBER(fuelBean.getFuel_TripsheetID());
					if (TripID != null) {
						fuel.setFuel_TripsheetNumber(TripID.getTripSheetNumber());
					}
				}
				fuel.setFuel_TripsheetID(fuelBean.getFuel_TripsheetID());

				fuel.setVid(fuelBean.getVid());
				// get Vehicle id to Vehicle name
				// fuel.setVehicle_registration(fuelBean.getVehicle_registration());
				// fuel.setVehicle_Ownership(fuelBean.getVehicle_Ownership());

				if (fuelBean.getFuel_date() != null) {
					fuel.setFuel_date(dateFormat_Name.format(fuelBean.getFuel_date()));
				}
				fuel.setFuel_meter(fuelBean.getFuel_meter());
				fuel.setFuel_meter_old(fuelBean.getFuel_meter_old());
				fuel.setFuel_meter_attributes(fuelBean.getFuel_meter_attributes());
				// fuel.setFuel_type(fuelBean.getFuel_type());
				fuel.setFuel_liters(fuelBean.getFuel_liters());
				fuel.setFuel_price(fuelBean.getFuel_price());

				// fuel.setVehicle_group(fuelBean.getVehicle_group());
				fuel.setDriver_id(fuelBean.getDriver_id());
				// fuel.setDriver_name(fuelBean.getDriver_name());
				// fuel.setDriver_empnumber(fuelBean.getDriver_empnumber());

				fuel.setVendor_id(fuelBean.getVendor_id());
				// fuel.setVendor_name(fuelBean.getVendor_name());
				// fuel.setVendor_location(fuelBean.getVendor_location());
				// fuel.setFuel_payment(fuelBean.getFuel_payment());

				fuel.setFuel_reference(fuelBean.getFuel_reference());
				fuel.setFuel_personal(fuelBean.getFuel_personal());
				fuel.setFuel_tank(fuelBean.getFuel_tank());
				fuel.setFuel_tank_partial(fuelBean.getFuel_tank_partial());

				fuel.setFuel_comments(fuelBean.getFuel_comments());
				fuel.setFuel_usage(fuelBean.getFuel_usage());
				fuel.setFuel_amount(fuelBean.getFuel_amount());
				double fuelAmt=fuelBean.getFuel_amount();
				fuel.setBalanceAmount((long)fuelAmt);
				fuel.setFuel_kml(fuelBean.getFuel_kml());
				fuel.setFuel_cost(fuelBean.getFuel_cost());

				// fuel.setFuel_vendor_paymode(fuelBean.getFuel_vendor_paymode());

				fuel.setFuel_document(fuelBean.isFuel_document());
				fuel.setFuel_document_id(fuelBean.getFuel_document_id());

				beans.add(fuel);
			}
		}
		return beans;
	}

	
	
	@RequestMapping("/dateWiseFuelEntryReport")
	public ModelAndView dateWiseFuelEntryReport() {
		HashMap<String, Object> 	configuration		= null;
		CustomUserDetails 			userDetails			= null;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new 	ModelAndView("dateWiseFuelEntryReport",model);	
	}
	
	@RequestMapping("/userWiseFuelEntryReport")
	public ModelAndView userWiseFuelEntryReport() throws Exception {
		Map<String, Object> model=new HashMap<String, Object>();
		CustomUserDetails		userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object>	configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
		
		model.put("configuration", configuration);
		
		return new 	ModelAndView("userWiseFuelEntryReport",model);	
	}
	
	@RequestMapping(value = "/addFuelEntries")
	public ModelAndView addFuelEntries(@ModelAttribute("command") TripSheetDto TripSheetDto,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		HashMap<String, Object> 	gpsConfiguration 	= null;
		HashMap<String, Object> 	fuelPriceConfig 	= null;
		HashMap<String, Object> 	vendorConfig 		= null;
		String 						defaultFuelPrice 	= null;
		Integer 					maxFuelCapacity 	= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			gpsConfiguration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_GPS_CONFIG);
			fuelPriceConfig 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_PRICE_CONFIG);
			vendorConfig 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			
			defaultFuelPrice		= fuelPriceConfig.getOrDefault(FuelConfigurationConstants.FUEL_PRICE, 0)+"";
			maxFuelCapacity		= (Integer) fuelPriceConfig.getOrDefault(FuelConfigurationConstants.MAX_FUEL_CAPACITY, 0);
			model.put("defaultFuelPrice", Double.parseDouble(defaultFuelPrice));
			model.put("maxFuelCapacity",maxFuelCapacity);
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			model.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));
			if(TripSheetDto.getTripSheetID() != null) {
				
				model.put("tripSheetId",TripSheetDto.getTripSheetID());
				
				TripSheetDto	trip		= TripSheetService.getTripSheetDetails(TripSheetDto.getTripSheetID(), userDetails);
				TripSheetDto	tripBL 		= tripSheetBL.GetTripSheetDetails(trip);
				model.put("TripSheet", tripBL);
				List<FuelDto>	ShowAmount 	 = fuelService.Get_TripSheet_IN_FuelTable_List(TripSheetDto.getTripSheetID(),
												userDetails.getCompany_id(), userDetails.getId());
				List<FuelDto>	ShowAmountBL = fuelBL.prepareListofFuel(ShowAmount);
				model.put("fuel", ShowAmountBL );
				model.put("fTDiesel", fuelBL.prepareTotal_Tripsheet_fuel_details(ShowAmount));
			}else {
				model.put("noTripSheet", true);
			}
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
			model.put("configuration",configuration);
			model.put("companyId",userDetails.getCompany_id());
			model.put("userId",userDetails.getId());
			model.put("allowGPSIntegration",(boolean)gpsConfiguration.get("allowGPSIntegration"));
			model.put("vendorConfig",vendorConfig);
			
			model.put(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_REFERENCE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_REFERENCE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, configuration.getOrDefault(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, true));
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL, false));
			model.put(FuelConfigurationConstants.VALIDATE_DRIVER1,configuration.getOrDefault(FuelConfigurationConstants.VALIDATE_DRIVER1, false));
			model.put(FuelConfigurationConstants.SHOW_MARK_AS_VOID_AUTO_SELECTED,configuration.getOrDefault(FuelConfigurationConstants.SHOW_MARK_AS_VOID_AUTO_SELECTED, false));
			model.put(FuelConfigurationConstants.ADD_FUEL_DOCUMENT,configuration.getOrDefault(FuelConfigurationConstants.ADD_FUEL_DOCUMENT, false));
			
			String minBackDate = DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("noOfDaysForBackDate"));
			model.put("minBackDate",minBackDate);
			
			model.put("serverDate",DateTimeUtility.getDateBeforeNoOfDays(0));
			model.put("UserProfile",userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId()));
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("FuelEntriesAdd", model);
	}

	@RequestMapping(value = "/addFuelEntriesNew")
	public ModelAndView addFuelEntriesNew(@ModelAttribute("command") TripSheetDto TripSheetDto,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		HashMap<String, Object> 	gpsConfiguration 	= null;
		HashMap<String, Object> 	fuelPriceConfig 	= null;
		String 						defaultFuelPrice 	= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			gpsConfiguration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_GPS_CONFIG);
			fuelPriceConfig 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_PRICE_CONFIG);
			
			defaultFuelPrice		= fuelPriceConfig.getOrDefault(FuelConfigurationConstants.FUEL_PRICE, 0)+"";
			model.put("defaultFuelPrice", Double.parseDouble(defaultFuelPrice));
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			model.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));
			if(TripSheetDto.getTripSheetID() != null) {
				
				model.put("tripSheetId",TripSheetDto.getTripSheetID());
				
				TripSheetDto	trip		= TripSheetService.getTripSheetDetails(TripSheetDto.getTripSheetID(), userDetails);
				TripSheetDto	tripBL 		= tripSheetBL.GetTripSheetDetails(trip);
				model.put("TripSheet", tripBL);
				List<FuelDto>	ShowAmount 	 = fuelService.Get_TripSheet_IN_FuelTable_List(TripSheetDto.getTripSheetID(),
												userDetails.getCompany_id(), userDetails.getId());
				List<FuelDto>	ShowAmountBL = fuelBL.prepareListofFuel(ShowAmount);
				model.put("fuel", ShowAmountBL );
				model.put("fTDiesel", fuelBL.prepareTotal_Tripsheet_fuel_details(ShowAmount));
			}else {
				model.put("noTripSheet", true);
			}
			
			model.put("configuration",configuration);
			model.put("companyId",userDetails.getCompany_id());
			model.put("userId",userDetails.getId());
			model.put("allowGPSIntegration",(boolean)gpsConfiguration.get("allowGPSIntegration"));
			
			model.put(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_REFERENCE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_REFERENCE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, configuration.getOrDefault(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, true));
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL, false));
			model.put(FuelConfigurationConstants.VALIDATE_DRIVER1,configuration.getOrDefault(FuelConfigurationConstants.VALIDATE_DRIVER1, false));
			model.put(FuelConfigurationConstants.SHOW_MARK_AS_VOID_AUTO_SELECTED,configuration.getOrDefault(FuelConfigurationConstants.SHOW_MARK_AS_VOID_AUTO_SELECTED, false));
			
			String minBackDate = DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("noOfDaysForBackDate"));
			model.put("minBackDate",minBackDate);
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("FuelEntriesAddNew", model);
	}

	
	
	@RequestMapping("/tripSheetWiseFuelEntryReport")
	public ModelAndView tripSheetWiseFuelEntryReport() {
		HashMap<String, Object> 	configuration		= null;
		CustomUserDetails 			userDetails			= null;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new 	ModelAndView("tripSheetWiseFuelEntryReport",model);	
	}
	
	@RequestMapping(value = "/FuelEntriesEdit")
	public ModelAndView fuelEntriesEdit(@RequestParam("Id") final Long fuelId,final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		HashMap<String, Object> 	gpsConfiguration 	= null;
		HashMap<String, Object> 	fuelPriceConfig 	= null;
		String 						defaultFuelPrice 	= null;
		Integer 					maxFuelCapacity 	= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			gpsConfiguration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_GPS_CONFIG);
			fuelPriceConfig 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_PRICE_CONFIG);
			
			defaultFuelPrice		= fuelPriceConfig.getOrDefault(FuelConfigurationConstants.FUEL_PRICE, 0)+"";
			maxFuelCapacity			= (Integer) fuelPriceConfig.getOrDefault(FuelConfigurationConstants.MAX_FUEL_CAPACITY, 0);
			model.put("defaultFuelPrice", Double.parseDouble(defaultFuelPrice));
			model.put("maxFuelCapacity",maxFuelCapacity);
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			model.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));
			model.put("configuration",configuration);
			model.put("fuel_id",fuelId);
			model.put("companyId",userDetails.getCompany_id());
			model.put("userId",userDetails.getId());
			model.put("allowGPSIntegration",(boolean)gpsConfiguration.get("allowGPSIntegration"));
			
			model.put(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_REFERENCE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_REFERENCE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, configuration.getOrDefault(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, true));
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, false));
			model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_TRIP_FUEL, false));
			model.put(FuelConfigurationConstants.VALIDATE_DRIVER1,configuration.getOrDefault(FuelConfigurationConstants.VALIDATE_DRIVER1, false));
			model.put(FuelConfigurationConstants.SHOW_MARK_AS_VOID_AUTO_SELECTED,configuration.getOrDefault(FuelConfigurationConstants.SHOW_MARK_AS_VOID_AUTO_SELECTED, false));
			model.put(FuelConfigurationConstants.GET_FUEL_STOCK_FROM_INVENTORY,configuration.getOrDefault(FuelConfigurationConstants.GET_FUEL_STOCK_FROM_INVENTORY, false));
			
			String minBackDate = DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("noOfDaysForBackDate"));
			model.put("minBackDate",minBackDate);
			model.put("serverDate",DateTimeUtility.getDateBeforeNoOfDays(0));
			
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("FuelEntriesEdit", model);
	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/importFuelEntry", method = RequestMethod.POST)
	public ModelAndView saveFuelEntry(@ModelAttribute("command") VehicleDocument photo,
			@RequestParam("import") MultipartFile file, HttpServletRequest request) throws Exception {

		Map<String, Object> 			model 				= null;
		CustomUserDetails				userDetails 		= null;
		String 							rootPath 			= null;
		File 							dir 				= null;
		File 							serverFile 			= null;
		FileInputStream 				fis 				= null;
		HSSFWorkbook 					myWorkBook			= null;
		HSSFSheet						mySheet				= null;
		Iterator<Row> 					rowIterator			= null;
		int 							noOfRows2 			= 0;
		FuelSequenceCounter 			sequenceCounter		= null;
		Fuel 							fuel 				= null;
		Map<String, VendorDto> 			vendorHm			= null;
		String							vendorName			= "";
		String  						failedFuelList		= "";
		String  						duplicateFuelList		= "";
		try {
			model 				= new HashMap<String, Object>();
			rootPath 			= request.getSession().getServletContext().getRealPath("/");
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
			List<Vehicle> vehicleList 		= vehicleService.getVehicleListByCompanyId(userDetails.getCompany_id());
			Map<String, Vehicle> vehicleHM 	= vehicleList.stream()
					.collect(Collectors.toMap(Vehicle::getVehicle_registration, Function.identity()));

			List<VendorDto> vendorList 		= vendorService.findAllVendorList(userDetails.getCompany_id());
			if(vendorList != null) {
				vendorHm = vendorList.stream()
						.collect(Collectors.toMap(VendorDto::getVendorName, Function.identity()));
			}
			

			dir = new File(rootPath + File.separator + "uploadedfile");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());

			try {
				try (InputStream is = file.getInputStream();
						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
					int i;
					// write file to server
					while ((i = is.read()) != -1) {
						stream.write(i);
					}
					stream.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			fis = new FileInputStream(serverFile);

			// Finds the workbook instance for XLSX file
			myWorkBook 	= new HSSFWorkbook(fis);

			// Return first sheet from the XLSX workbook
			mySheet 	= myWorkBook.getSheetAt(0);

			int noOfColumns = mySheet.getRow(0).getPhysicalNumberOfCells();

			// Get iterator to all the rows in current sheet
			rowIterator = mySheet.iterator();

			Vehicle vehicle = null;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				noOfRows2 += 1;
				if (noOfRows2 == 1) {
					continue;
				}
				fuel = new Fuel();
				Cell cell = null;
				String cellValue 	= null;
				String vehicleNo 	= null;
				
				for (int i = 0; i < noOfColumns; i++) {
					
					cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					switch (cell.getCellType()) {
					
					case Cell.CELL_TYPE_STRING:
						cellValue = cell.getStringCellValue().toUpperCase();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellValue = Long.toString((long) cell.getNumericCellValue());
						break;
					default:
						cellValue = null;
						break;
						
					}
					
					if (i == 0) {
						if (cellValue != null && !cellValue.isEmpty()) {
							vehicleNo = cellValue.trim();

							if (vehicleHM.get(vehicleNo) != null) {
								vehicle = vehicleHM.get(vehicleNo.trim());
								fuel.setVid(vehicle.getVid());
							}

						}
						continue;
					}
					if(fuel.getVid() != null) {
						if (i == 1) {
							if (cellValue != null && !cellValue.isEmpty()) {
								try {
									fuel.setFuel_date(dateFormat.parse(cellValue.trim()));
									fuel.setFuelDateTime(dateFormatTime.parse(cellValue.trim()));
								} catch (Exception e) {
									continue;
								}
							}
							continue;
						}
						if (i == 2) {
							if (cellValue != null && !cellValue.isEmpty() && Integer.parseInt(cellValue) > 0 ) {
								try {
									fuel.setFuel_meter(Integer.parseInt(cellValue));
								} catch (Exception e) {
									fuel.setFuel_meter(0);
								}
								
							}else {
								fuel.setFuel_meter(0);
							}
							continue;
						}
						if (i == 3) {
							if (cellValue != null && !cellValue.isEmpty()) {
								try {
									fuel.setFuel_usage(Integer.parseInt(cellValue));
								} catch (Exception e) {
									fuel.setFuel_usage(0);
								}
								
							}else {
								fuel.setFuel_usage(0);
							}
							continue;
						}

						if (i == 4) {
							if (cellValue != null && !cellValue.isEmpty()) {
								try {
									fuel.setFuel_liters(Double.parseDouble(cellValue));
								} catch (Exception e) {
									fuel.setFuel_liters(0.0);
								}
								
							}
							continue;
						}
						if (i == 5) {
							if (cellValue != null && !cellValue.isEmpty()) {
								try {
									fuel.setFuel_price(Double.parseDouble(cellValue));
								} catch (Exception e) {
									fuel.setFuel_price(0.0);
								}
								
							}
							continue;
						}
						if (i == 6) {
							if (cellValue != null && !cellValue.isEmpty()) {
								if (cellValue.equalsIgnoreCase("full")) {
									fuel.setFuel_tank(0);
									fuel.setFuel_tank_partial(0);
								} else if (cellValue.equalsIgnoreCase("partial")) {
									fuel.setFuel_tank(1);
									fuel.setFuel_tank_partial(1);
								}
							}
							continue;
						}
						if (i == 7) {
							if (cellValue != null && !cellValue.isEmpty()) {

								if (vendorHm != null && vendorHm.get(cellValue.trim()) != null) {
									fuel.setVendor_id(vendorHm.get(cellValue.trim()).getVendorId());
									vendorName = cellValue.trim();
								} else {
									fuel.setVendor_id(fuelService.saveFuelEntriesToNewVendorCreateAuto(cellValue,
											userDetails.getCompany_id(), userDetails.getId()));
									vendorHm.put(cellValue.trim(), new VendorDto(fuel.getVendor_id(), cellValue.trim()));
								}
							}
							continue;
						}
					}	
				}
				if(fuel.getVid() != null) {
				
					fuel.setPaymentTypeId(PaymentTypeConstant.PAYMENT_TYPE_CASH);
					fuel.setFuel_vendor_paymodeId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
					
					if (!file.isEmpty()) {
						fuel.setFuel_document(true);
					} else {
						fuel.setFuel_document(false);
					}

					fuel.setFuelTypeId((short) 1);
					if(fuel.getFuel_liters() != null && fuel.getFuel_liters() > 0 && fuel.getFuel_price() != null && fuel.getFuel_price() > 0) {
						fuel.setBalanceAmount(fuel.getFuel_liters() * fuel.getFuel_price());
						fuel.setFuel_amount(fuel.getFuel_liters() * fuel.getFuel_price());
					}else {
						fuel.setBalanceAmount(0.00);
						fuel.setFuel_amount(0.00);
					}
					fuel.setCompanyId(userDetails.getCompany_id());
					fuel.setCreated(new java.util.Date());
					fuel.setLastupdated(new java.util.Date());
					fuel.setCreatedById(userDetails.getId());
					fuel.setLastModifiedById(userDetails.getId());
					fuel.setMarkForDelete(false);
					fuel.setPendingForTally(false);
					if(fuel.getFuel_usage() != null && fuel.getFuel_liters() != null && fuel.getFuel_liters() > 0) {
						fuel.setFuel_kml(Double.parseDouble(decimalFormat.format(fuel.getFuel_usage() / fuel.getFuel_liters())));
					}else {
						fuel.setFuel_kml(0.00);
					}
					if(fuel.getFuel_amount() != null && fuel.getFuel_usage() != null && fuel.getFuel_usage() > 0) {
						fuel.setFuel_cost(Double.parseDouble(decimalFormat.format(fuel.getFuel_amount() / fuel.getFuel_usage())));
					}else {
						fuel.setFuel_cost(0.00);
					}
					fuel.setFuel_personal(0);
					fuel.setFuel_meter_attributes(0);
					fuel.setFuel_TripsheetID((long) 0);
					fuel.setDriver_id(0);
					if(fuel.getFuel_meter() > 0) {
						fuel.setFuel_meter_old(fuel.getFuel_meter()-fuel.getFuel_usage());
					}else {
						fuel.setFuel_meter_old(0);
					}
					
					Boolean	validated	=	validateFuelEntry(fuel, vehicle, fuelService.getVehicleLastFuelDetailsByVidDate(
												DateTimeUtility.getStringDateFromDate(fuel.getFuel_date(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS)
												, fuel.getVid()));
					
					if(validated) {

						sequenceCounter = fuelSequenceService.findNextFuelNumber(userDetails.getCompany_id());
						fuel.setCompanyId(userDetails.getCompany_id());
						if (sequenceCounter == null) {
							return new ModelAndView("redirect:/Fuel/1.in?FuelSequenceCounterMissing=true");
						}
						fuel.setFuel_Number(sequenceCounter.getNextVal());
						
						List<Fuel> validateFuel = fuelService.listFuelValidate(fuel.getVid() , fuel.getFuel_meter(),
								fuel.getFuel_liters(), fuel.getFuel_price(), vendorName,
								fuel.getFuel_reference(), fuel.getCompanyId());
						if(validateFuel == null || validateFuel.isEmpty()) {
							fuelService.addFuel(fuel);
							
							if (vehicle.getVehicle_Odometer() < fuel.getFuel_meter()) {
								
									try {
										vehicleService.updateCurrentOdoMeter(fuel.getVid(), fuel.getFuel_meter(), fuel.getCompanyId());
										ServiceReminderService.updateCurrentOdometerToServiceReminder(fuel.getVid(),fuel.getFuel_meter(), fuel.getCompanyId());

										List<ServiceReminderDto>	serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(fuel.getVid(), fuel.getCompanyId(), userDetails.getId());
										if(serviceList != null) {
											for(ServiceReminderDto list : serviceList) {

												if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {

													ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
												}
											}
										}
										
									} catch (Exception e) {
										System.err.println("Exception SR "+e);
									}
									
								VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToFuel(fuel);
								vehicleUpdateHistory.setCompanyId(fuel.getCompanyId());
								vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
							}else if(vehicle.getVehicle_Odometer() == fuel.getFuel_meter()) {
								VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToFuel(fuel);
								vehicleUpdateHistory.setCompanyId(fuel.getCompanyId());
								vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
							}
						}else {
							duplicateFuelList += vehicle.getVehicle_registration()+" : "+DateTimeUtility.getStringDateFromDate(fuel.getFuel_date(), DateTimeUtility.DD_MM_YYYY) +", ";
							model.put("duplicate", duplicateFuelList);
						}
					}else {
						failedFuelList += vehicle.getVehicle_registration()+" : "+DateTimeUtility.getStringDateFromDate(fuel.getFuel_date(), DateTimeUtility.DD_MM_YYYY) +", ";
					}


				}
			}
			model.put("importSave", true);
			if(!failedFuelList.trim().equals("")) {
				model.put("failedFuelList", failedFuelList);
			}
			return new ModelAndView("redirect:/Fuel/1.in", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model 		= null;
			userDetails = null;
			rootPath 	= null;
			dir 		= null;
			serverFile  = null;
			fis			= null;
			myWorkBook  = null;
			mySheet 	= null;
			rowIterator = null;
			noOfRows2 	= 0;
		}

	}

	
	private boolean validateFuelEntry(Fuel	fuel, Vehicle vehicle, FuelDto preFuel) {
		if(preFuel != null && vehicle.getVehicle_ExpectedOdameter() != null &&
				(fuel.getFuel_meter() - preFuel.getFuel_meter()) > vehicle.getVehicle_ExpectedOdameter()) {
			return false;
		}
		
		if(preFuel != null && fuel != null && (fuel.getFuel_meter() - preFuel.getFuel_meter()) <= 0) {
			return false;
		} 
		
		return true;
	}
	
	@RequestMapping(value = "/getBranchList", method = RequestMethod.POST)
	public void getbrancgList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		List<Branch> branchlist = new ArrayList<Branch>();
		List<Branch> branch = BranchService.SearchBranchListByCompanyId(term, userDetails.getCompany_id());
		
		if (branch != null && !branch.isEmpty()) {
			for (Branch add : branch) {
				Branch wadd = new Branch();
				wadd.setBranch_id(add.getBranch_id());
				wadd.setBranch_name(add.getBranch_name());
				branchlist.add(wadd);
			}
		}
		Gson gson = new Gson();
		System.out.println("jsonStudents = " + gson.toJson(branchlist));

		response.getWriter().write(gson.toJson(branchlist));
	}
}

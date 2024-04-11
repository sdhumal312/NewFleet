package org.fleetopgroup.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.FuelConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleAcType;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleFuelType;
import org.fleetopgroup.constant.VehicleFuelUnit;
import org.fleetopgroup.constant.VehicleGPSConfiguratinConstant;
import org.fleetopgroup.constant.VehicleMeterUnit;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.bl.TripRouteBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleFuelBL;
import org.fleetopgroup.persistence.bl.VehicleGroupBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleStatusBL;
import org.fleetopgroup.persistence.bl.VehicleTypeBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dao.TripsheetPickAndDropRepository;
import org.fleetopgroup.persistence.dao.VehicleGPSCredentialRepository;
import org.fleetopgroup.persistence.dao.VehicleServiceProgramRepository;
import org.fleetopgroup.persistence.dao.VehicleTollDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleFuelDto;
import org.fleetopgroup.persistence.dto.VehicleGroupDto;
import org.fleetopgroup.persistence.dto.VehicleModelDto;
import org.fleetopgroup.persistence.dto.VehicleOdometerHistoryDto;
import org.fleetopgroup.persistence.dto.VehicleStatusDto;
import org.fleetopgroup.persistence.dto.VehicleTypeDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.TripRoute;
import org.fleetopgroup.persistence.model.TripsheetPickAndDrop;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.model.VehicleManufacturer;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleStatus;
import org.fleetopgroup.persistence.model.VehicleType;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IServiceProgramService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExtraDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleFuelService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleManufacturerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleStatusService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTypeService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
import org.fleetopgroup.persistence.dto.SubCompanyDto;

/*import com.fleetop.service.Vid_tableService;*/

import au.com.bytecode.opencsv.CSVReader;

@Controller
public class VehicleController extends MainActivity {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleTypeService vehicletypeService;

	@Autowired
	private IVehicleStatusService vehicleStatusService;

	@Autowired
	private IVehicleGroupService vehicleGroupService;

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private IVehicleFuelService vehicleFuelService;
	@Autowired
	private IVehicleService vehicleService;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;

	@Autowired
	private IServiceReminderService ServiceReminderService;

	
	@Autowired
	private IDriverService driverService;

	@Autowired
	private IWorkOrdersService workOrdersService;

	 @Autowired private ITripRouteService tripRouteService;
	 
	 @Autowired private IVehicleDocumentService vehicleDocumentService;
	

	@Autowired
	private IFuelService fuelService;
	@Autowired
	private IVehicleOdometerHistoryService vehicleOdometerHistoryService;
	
	@Autowired	private IVehicleGPSDetailsService		vehicleGPSDetailsService;
	@Autowired	private	VehicleTollDetailsRepository	vehicleTollDetailsRepository;
	@Autowired	private IVehicleExtraDetailsService		vehicleExtraDetailsService;
	@Autowired	private VehicleGPSCredentialRepository	vehicleGPSCredentialRepository;
	@Autowired	private VehicleServiceProgramRepository	vehicleServiceProgramRepository;
	@Autowired	private TripsheetPickAndDropRepository	TripsheetPickAndDropRepository;
	@Autowired	private IServiceProgramService			serviceProgramService;
	@Autowired	private IVehicleManufacturerService		vehicleManufacturerService;
	@Autowired	private IVehicleModelService			vehicleModelService;
	
	TripRouteBL RouteBL = new TripRouteBL();
	RenewalTypeBL DriDT = new RenewalTypeBL();
	VendorBL VenBL = new VendorBL();
	DriverBL DBL = new DriverBL();

	VehicleBL VBL = new VehicleBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();
	VehicleTypeBL v = new VehicleTypeBL();
	VehicleStatusBL vs = new VehicleStatusBL();
	VehicleGroupBL vg = new VehicleGroupBL();
	VehicleFuelBL vf = new VehicleFuelBL();
	VehicleDocTypeController vd = new VehicleDocTypeController();
	VehiclePhoTypeController vp = new VehiclePhoTypeController();
	VehiclePurchaseInfoTypeController vpur = new VehiclePurchaseInfoTypeController();

	FuelBL FuBL = new FuelBL();

	VehicleOdometerHistoryBL VOHBL = new VehicleOdometerHistoryBL();

	SimpleDateFormat dateFormat_SQL = new SimpleDateFormat("YYYY-MM-dd");
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	
	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");

	public static final Integer VEHICLE_DEFALAT_ZERO = 0;

	// search Vehicle List
	@RequestMapping(value = "/searchVehicle", method = RequestMethod.POST)
	public ModelAndView searchVehicle(@RequestParam(value = "searchvehicle") final String searchvehicle,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (searchvehicle != null) {
				// model.put("user", userService.getUserByID(userID));
				model.put("vehicles", VBL.prepareList_OF_Vehicle_ReCent(vehicleService.SearchVehicle(searchvehicle)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Page:", e);
		}
		return new ModelAndView("Vehicle_Report", model);
	}
	// search Vehicle List
	@RequestMapping(value = "/searchVehicleAll", method = RequestMethod.POST)
	public ModelAndView searchVehicleAll(@RequestParam(value = "searchvehicle") final String searchvehicle,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (searchvehicle != null) {
				// model.put("user", userService.getUserByID(userID));
				model.put("vehicles", VBL.prepareList_OF_Vehicle_ReCent(vehicleService.SearchVehicle_Registration_Chassis(searchvehicle)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Page:", e);
		}
		return new ModelAndView("Vehicle_Report", model);
	}

	// search Vehicle List
	@RequestMapping(value = "/searchVehicleName", method = RequestMethod.POST)
	public ModelAndView searchVehicle_Id(@ModelAttribute("command") VehicleDto vehicleDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("vehicles",
					VBL.prepareListofDto(vehicleService.SearchOnlyVehicleNAME(vehicleDto.getVehicle_registration())));
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Page:", e);
		}
		return new ModelAndView("Vehicle_Report", model);
	}

	// Show Vehicle Main Page Status Pages
	@RequestMapping(value = "/vehicle/{Status}/{pageNumber}", method = RequestMethod.GET)
	public String VehicleList(@PathVariable("Status") short Status, @PathVariable("pageNumber") Integer pageNumber,
			Model model, HttpServletRequest request) throws Exception {
		CustomUserDetails	userDetails	=		(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long vehicleCount		= 0;
		long totalVehicleCount = 0;
		HashMap<String, Object> 		configuration	          = null;
		try { 
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			Page<Vehicle> page 	= vehicleService.getDeployment_Page_Vehicle(Status, pageNumber);
			totalVehicleCount = vehicleService.totalCountVehicles(userDetails.getCompany_id());
			if(page != null) {
				int current = page.getNumber() + 1;
				int begin 	= Math.max(1, current - 5);
				int end 	= Math.min(begin + 10, page.getTotalPages());
				
				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				
				vehicleCount	=	 page.getTotalElements();
				List<VehicleDto> pageList = VBL.prepareList_OF_Vehicle(vehicleService.listVehicel(Status, pageNumber, userDetails));
				
				model.addAttribute("vehicles", pageList);
			}
			model.addAttribute("VehicleCount", vehicleCount);
			model.addAttribute("totalVehicleCount", totalVehicleCount);
			model.addAttribute("vehiclestatus", vehicleStatusService.findAll());
			model.addAttribute("SelectStatus", VehicleStatusConstant.getVehicleStatus(Status));
			model.addAttribute("SelectPage", pageNumber);
			model.addAttribute("user", userDetails);
			model.addAttribute("Status", Status);
			model.addAttribute("configuration", configuration);
			
		} catch (NullPointerException e) {
			LOGGER.error("Vehicle Page NullPointerException:", e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("Vehicle Page:", e);
			e.printStackTrace();
		}
		
		
		return "vehiclepage_Status";
	}

	// Create Vehicle page
	@RequestMapping(value = "/addVehicle", method = RequestMethod.GET)
	public ModelAndView addVehicleTypes(VehicleTypeDto vehicletypeDto, VehicleStatusDto vehiclestatusDto,
			VehicleGroupDto vehiclegroupDto, VehicleFuelDto vehiclefuelDto, final HttpServletRequest request) {
			Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
			model.put("vehiclestatus", vs.prepareListofBean(vehicleStatusService.findAll()));
			model.put("vehiclefuel", vf.prepareListofDto(vehicleFuelService.findAll()));
			model.put("configuration", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG));
			model.put("gpsConfiguration", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG));

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Page:", e);
		}
		return new ModelAndView("addVehicle", model);
	}

	// Save Vehicle Details to Database
	@RequestMapping(value = "/saveNewVehicle", method = RequestMethod.POST)
	public ModelAndView saveVehicle(@RequestParam("userID") final Long userID, Vehicle vehicleDto,
			final HttpServletRequest request) {
		Map<String, Object> 		model 					= new HashMap<String, Object>();
		CustomUserDetails			userDetails 			= null;
		Vehicle 					vehicle					= null;
		List<Vehicle> 				validate 				= null;
		VehicleOdometerHistory 		vehicleUpdateHistory 	= null;
		
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle	 	= VBL.prepareModel(vehicleDto);
			validate 	= vehicleService.listVehicleVaildateOLD(vehicle.getVehicle_registration(), vehicle.getVehicle_chasis(), vehicle.getVehicle_engine());
			
			if (validate != null && !validate.isEmpty()) {
				return new ModelAndView("redirect:/vehicle/1/1.in?danger=true");

			} else {
				vehicleService.addVehicle(vehicle);
				vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToVehicle(vehicle);
				vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);

				model.put("vehicle", VBL.prepareVehicleDto(vehicleService.getVehicelDetails(vehicle.getVid(),userDetails)));
				model.put("SaveSuccess", true);
				return new ModelAndView("redirect:/showVehicle?id=" + userDetails.getId() + "&vid=" + vehicle.getVid() + "", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("save Vehicle Status Page:"+e);
			return new ModelAndView("redirect:/vehicle/1/1?id=" + userID + "&danger=true");
		}

	}

	// Show vehicle controller To Show Vehicle If you go Back to select page
	@RequestMapping(value = "/{Status}/{pageNumber}/showVehicle", method = RequestMethod.GET)
	public ModelAndView VehicleStatusPageShowVehicle(@PathVariable("Status") String Status,
			@PathVariable("pageNumber") Integer pageNumber, @RequestParam("vid") final Integer VEHICLE_VID,
			Model modelAdd, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		VehicleDto		vehicle			= null;
		HashMap<String, Object>		tollConfig					= null;
		HashMap<String, Object>		configDriverSalary			= null;
		HashMap<String, Object>		configuration				= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configDriverSalary	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
		
			tollConfig			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TOLL_API_CONFIG);
			
			HashMap<String, Object>  inspConfig		=	companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			
			model.put(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION,(boolean) configuration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION));

			vehicle	= VBL.prepareVehicleDto(vehicleService.getVehicelDetails(VEHICLE_VID, userDetails));
			
			model.put("inspConfig", inspConfig);
			model.put("configuration", configuration);
			model.put("configDriverSalary", configDriverSalary);
			model.put("vehicle", vehicle);
			model.put("SelectStatus", Status);
			model.put("SelectPage", pageNumber);
			model.put("configuration", configuration);
			model.put("tollConfig", tollConfig);
			if((boolean) tollConfig.get("multipleAccountForSameVendor")) {
				model.put("customerList", vehicleTollDetailsRepository.getVehicleTollDetails(userDetails.getCompany_id()));
				model.put("customer", vehicleExtraDetailsService.getVehicleExtraDetailsByVid(vehicle.getVid()));
			}
			Calendar aCalendar = Calendar.getInstance();

			// read it
			Date CurrentDate = aCalendar.getTime();
			// add -1 month to current month
			aCalendar.add(Calendar.MONTH, -3);
			// set DATE to 1, so first date of previous month
			aCalendar.set(Calendar.DATE, 1);

			Date firstDateOfPreviousMonth = aCalendar.getTime();

			try {
				Object[] LastMonthcount = vehicleService.countTotal_LAST_MONTH_RR_FE_SE_TS_or_TC_or_TD_WO_REPORT(
						VEHICLE_VID, dateFormat_SQL.format(firstDateOfPreviousMonth),
						dateFormat_SQL.format(CurrentDate));
				if (LastMonthcount != null) {
					Double RR = 0.0, FE = 0.0, SE = 0.0, TS = 0.0, TC = 0.0, TD = 0.0, WO = 0.0;
					if (LastMonthcount[0] != null) {
						RR = (Double) LastMonthcount[0];
					}

					if (LastMonthcount[1] != null) {
						FE = (Double) LastMonthcount[1];
					}

					if (LastMonthcount[2] != null) {
						SE = (Double) LastMonthcount[2];
					}

					if (LastMonthcount[3] != null) {
						TS = (Double) LastMonthcount[3];
					}
					if (LastMonthcount[4] != null) {
						TC = (Double) LastMonthcount[4];
					}
					if (LastMonthcount[5] != null) {
						TD = (Double) LastMonthcount[5];
					}

					if (LastMonthcount[6] != null) {
						WO = (Double) LastMonthcount[6];
					}

					String LastRRCost = AMOUNTFORMAT.format(round(RR, 0));
					model.put("LastRRCost", LastRRCost);
					String LastFECost = AMOUNTFORMAT.format(FE);
					model.put("LastFECost", LastFECost);
					model.put("companyId", userDetails.getCompany_id());

					String LastSECost = AMOUNTFORMAT.format(SE);
					model.put("LastSECost", LastSECost);

					String LastTSCost = AMOUNTFORMAT.format(TS + TC + TD);
					model.put("LastTSCost", LastTSCost);

					String LastWOCost = AMOUNTFORMAT.format(WO);
					model.put("LastWOCost", LastWOCost);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(VEHICLE_VID);
			model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(VEHICLE_VID, userDetails.getCompany_id()));
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
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Page:");

		}
		
		return new ModelAndView("showVehicle", model);
	}

	// Show vehicle controller
	@RequestMapping(value = "/showVehicle", method = RequestMethod.GET)
	public ModelAndView addVehicleTypes(@RequestParam("vid") final Integer VEHICLE_VID, Model modelAdd, final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		VehicleDto					vehicle						= null;
		CustomUserDetails			userDetails					= null;
		HashMap<String, Object>		configDriverSalary			= null;
		HashMap<String, Object>		configuration				= null;
		HashMap<String, Object>		tollConfig					= null;
		
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configDriverSalary	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			tollConfig			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TOLL_API_CONFIG);
			if(VEHICLE_VID == null) {
				return new ModelAndView("redirect:/vehicle/1/1.in?danger=true");
			}
			vehicle				= VBL.prepareVehicleDto(vehicleService.getVehicelDetails(VEHICLE_VID, userDetails));
			
			model.put(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION,(boolean) configuration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION));
			model.put("vehicle", vehicle);
			model.put("configuration", configuration);
			model.put("configDriverSalary", configDriverSalary);
			model.put("SelectStatus", 1);
			model.put("SelectPage", VEHICLE_MAIN_STATUS_PAGE);
			model.put("companyId", userDetails.getCompany_id());
			model.put("tollConfig", tollConfig);
			model.put("showTollIdFeild", (boolean) configDriverSalary.get("showTollIdFeild"));
			if((boolean) tollConfig.get("multipleAccountForSameVendor")) {
				model.put("customerList", vehicleTollDetailsRepository.getVehicleTollDetails(userDetails.getCompany_id()));
			}
			model.put("customer", vehicleExtraDetailsService.getVehicleExtraDetailsByVid(vehicle.getVid()));
			
			if((boolean) configuration.get("multipleAccountForSameVendor")) {
				model.put("credentialList", vehicleGPSCredentialRepository.getVehicleGPSCredentialListByCompanyId(userDetails.getCompany_id()));
			}
			
			if((vehicle.getvStatusId()==VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) || (vehicle.getvStatusId()==VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) || (vehicle.getvStatusId()==VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) )
			{
				model.put("checkVehicleStatus", true);
			}

			Calendar aCalendar = Calendar.getInstance();

			// read it
			Date CurrentDate = aCalendar.getTime();
			// add -1 month to current month
			aCalendar.add(Calendar.MONTH, -3);
			// set DATE to 1, so first date of previous month
			aCalendar.set(Calendar.DATE, 1);

			Date firstDateOfPreviousMonth = aCalendar.getTime();

			try {
				Object[] LastMonthcount = vehicleService.countTotal_LAST_MONTH_RR_FE_SE_TS_or_TC_or_TD_WO_REPORT(
						VEHICLE_VID, dateFormatSQL.format(firstDateOfPreviousMonth),
						dateFormatSQL.format(CurrentDate));
				if (LastMonthcount != null) {
					Double RR = 0.0, FE = 0.0, SE = 0.0, TS = 0.0, TC = 0.0, TD = 0.0, WO = 0.0;
					if (LastMonthcount[0] != null) {
						RR = (Double) LastMonthcount[0];
					}

					if (LastMonthcount[1] != null) {
						FE = (Double) LastMonthcount[1];
					}

					if (LastMonthcount[2] != null) {
						SE = (Double) LastMonthcount[2];
					}

					if (LastMonthcount[3] != null) {
						TS = (Double) LastMonthcount[3];
					}
					if (LastMonthcount[4] != null) {
						TC = (Double) LastMonthcount[4];
					}
					if (LastMonthcount[5] != null) {
						TD = (Double) LastMonthcount[5];
					}

					if (LastMonthcount[6] != null) {
						WO = (Double) LastMonthcount[6];
					}
					
					if(LastMonthcount[7] != null)
						SE = (Double)LastMonthcount[7];

					String LastRRCost = AMOUNTFORMAT.format(round(RR, 0));
					model.put("LastRRCost", LastRRCost);
					String LastFECost = AMOUNTFORMAT.format(FE);
					model.put("LastFECost", LastFECost);

					String LastSECost = AMOUNTFORMAT.format(SE);
					model.put("LastSECost", LastSECost);

					String LastTSCost = AMOUNTFORMAT.format(TS + TC + TD);
					model.put("LastTSCost", LastTSCost);

					String LastWOCost = AMOUNTFORMAT.format(WO);
					model.put("LastWOCost", LastWOCost);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(VEHICLE_VID);
			model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(VEHICLE_VID, userDetails.getCompany_id()));
			model.put("photo_Count", vehicleDocumentService.getVehiclePhotoCount(VEHICLE_VID, userDetails.getCompany_id()));
			model.put("pickUpDropCount", TripsheetPickAndDropRepository.countTripSheet(VEHICLE_VID, userDetails.getCompany_id()));
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
			model.put("inspectionCount", (Long) count[11]);
			model.put("breakDownCount", ((Long) count[4]+(Long) count[8]));
			model.put("dseCount", (Long) count[12]);
		} catch (Exception e) {
			throw e;

		}
		return new ModelAndView("showVehicle", model);
	}

	@RequestMapping(value = "/PrintVehicle", method = RequestMethod.GET)
	public ModelAndView showUserProfilePrint(@RequestParam("id") final Integer id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			model.put("vehicle", VBL.prepareVehicleDto(vehicleService.getVehicelDetails(id, userDetails)));

			model.put("SelectStatus", VEHICLE_MAIN_STATUS);
			model.put("SelectPage", VEHICLE_MAIN_STATUS_PAGE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showVehiclePrint", model);
	}

	// edit vehicle page show
	@RequestMapping(value = "/editVehicle", method = RequestMethod.GET)
	public ModelAndView editVehicle(@RequestParam("vid") final Integer VEHICLE_VID, Model modelAdd,
			final HttpServletRequest request, RedirectAttributes redir) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		String TID = "";
		VehicleDto		vehicle			= null;
		boolean						vehicleConfig					= false;
		StringBuffer fuelType = new StringBuffer();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>	 configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			vehicleConfig				= (boolean) configuration.getOrDefault(VehicleConfigurationContant.ADD_VEHICLE_DETAILS_AJAX, false); // this will perform when this method will allow for all group

			if (VEHICLE_VID != VEHICLE_DEFALAT_ZERO && VEHICLE_VID != null) {
				VehicleDto Check = vehicleService.Get_Vehicle_Current_Status_TripSheetID(VEHICLE_VID);
				if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					TID += " <span> This Vehicle  <a href=\"../../showVehicle.in?vid=" + VEHICLE_VID
							+ "\" target=\"_blank\">"
							+ Check.getVehicle_registration()
							+ " <i class=\"fa fa-external-link\"></i></a> In Other TripSheet <a href=\"../../showTripSheet.in?tripSheetID="
							+ Check.getTripSheetID() + "\" target=\"_blank\">TS-" + driverService.getCurrentTripSheetNumber(Check.getTripSheetID(), userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet, </span>, <br>";

					redir.addFlashAttribute("InAnother", TID);
					model.put("InAnotherTrip", true);
					return new ModelAndView("redirect:/vehicle/1/1", model);

				} else if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					if(Check.getWorkOrder_Number()!=null) {
					TID += " <span> This Vehicle  <a href=\"../../showVehicle.in?vid=" + VEHICLE_VID
							+ "\" target=\"_blank\">"
							+ Check.getVehicle_registration()
							+ " <i class=\"fa fa-external-link\"></i></a> In Work Shop <a href=\"../../showWorkOrder?woId="
							+ Check.getTripSheetID() + "\" target=\"_blank\">WO-" + workOrdersService.getWorkOrders(Check.getTripSheetID()).getWorkorders_Number()
							+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet, </span>, <br>";

					redir.addFlashAttribute("InAnother", TID);
					model.put("InAnotherTrip", true);
					return new ModelAndView("redirect:/vehicle/1/1", model);
					}
				} else if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE
						|| Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE
						|| Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER
						|| Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT) {
					vehicle	= VBL.prepareVehicleDto(vehicleService.getVehicelDetails(VEHICLE_VID, userDetails));
					if(vehicle.getServiceProgramId() != null && vehicle.getServiceProgramId() > 0) {
						model.put("serviceProgram", vehicleServiceProgramRepository.getServiceProgramById(vehicle.getServiceProgramId()));
					}
					
					model.put("serviceProgramList", serviceProgramService.getServiceProgramListByCompanyId(userDetails.getCompany_id()));
					model.put("vehicle", vehicle);
					model.put("vehiclestatus", vs.prepareListofBean(vehicleStatusService.findAll()));
					model.put("vehiclefuel", vf.prepareListofDto(vehicleFuelService.findAll()));
					fuelType = 	fuelType.append(vehicle.getVehicleFuelId());
					model.put("fuelType", fuelType);
					model.put("SelectStatus", VEHICLE_MAIN_STATUS);
					model.put("SelectPage", VEHICLE_MAIN_STATUS_PAGE);
					model.put("configuration", configuration);
					model.put("gpsConfiguration", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG));
					model.put("companyId", userDetails.getCompany_id());
					model.put("userId", userDetails.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/vehicle/1/1", model);
		}
		if(vehicleConfig) {
			return new ModelAndView("editVehicleDetails", model);
		}else {
			return new ModelAndView("editVehicle", model);
		}
	}

	// update vehicle for Db
	@RequestMapping(value = "/updateNewVehicle", method = RequestMethod.POST)
	public ModelAndView updateVehicle(@RequestParam("vid") final Integer VEHICLE_VID, VehicleDto vehicleDto,
			final HttpServletRequest request, RedirectAttributes redir) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails				userDetails			= null;
		List<ServiceReminderDto>		serviceList			= null;
		String TID = "";
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (VEHICLE_VID != VEHICLE_DEFALAT_ZERO && VEHICLE_VID != null) {

				Vehicle Check = vehicleService.getVehicel1(VEHICLE_VID);
				if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					TID += " <span> This Vehicle  <a href=\"../../showVehicle.in?vid=" + VEHICLE_VID
							+ "\" target=\"_blank\">"
							+ Check.getVehicle_registration()
							+ " <i class=\"fa fa-external-link\"></i></a> In Other TripSheet <a href=\"../../showTripSheet.in?tripSheetID="
							+ Check.getTripSheetID() + "\" target=\"_blank\">TS-" + driverService.getCurrentTripSheetNumber(Check.getTripSheetID(), userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet, </span>, <br>";

					redir.addFlashAttribute("InAnother", TID);
					model.put("InAnotherTrip", true);
					return new ModelAndView("redirect:/vehicle/1/1", model);

				} else if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					TID += " <span> This Vehicle  <a href=\"../../showVehicle.in?vid=" + VEHICLE_VID
							+ "\" target=\"_blank\">"
							+ Check.getVehicle_registration()
							+ " <i class=\"fa fa-external-link\"></i></a> In Work Shop <a href=\"../../showWorkOrder?woId="
							+ Check.getTripSheetID() + "\" target=\"_blank\">WO-" + workOrdersService.getWorkOrders(Check.getTripSheetID()).getWorkorders_Number()
							+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet, </span>, <br>";

					redir.addFlashAttribute("InAnother", TID);
					model.put("InAnotherTrip", true);
					return new ModelAndView("redirect:/vehicle/1/1", model);

				} else if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE
						|| Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE
						|| Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD
						|| Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER) {

					Vehicle vehicle = VBL.update_prepareModel(vehicleDto, Check);
					vehicle.setCompany_Id(userDetails.getCompany_id());
					/*
					 * this line we will remove after replacing all dependencies Vehicle_Group with vehicleGroupId
					 */
					if(vehicle.getVehicleGroupId() > 0)
						//vehicle.setVehicle_Group(vehicleGroupService.getVehicleGroupByID(vehicle.getVehicleGroupId()).getvGroup());
					vehicleService.updateVehicle(vehicle);

					if (Check.getVehicleGroupId() != vehicle.getVehicleGroupId()) {
						// THis vehicle Edit to update all Fuel Entries Group
						// and
						// Vehicle
						// Owner_ship
//						fuelService.update_Vehicle_Group_AND_Vehicle_Ownership(vehicle.getVehicle_Group(),
//								vehicle.getVehicleOwnerShipId(), VEHICLE_VID, vehicle.getVehicleGroupId());

//						ServiceReminderService.update_Vehicle_Group_USING_Vehicle_Id(vehicle.getVehicle_Group(),
//								VEHICLE_VID, vehicle.getVehicleGroupId(), userDetails.getCompany_id());

//						ServiceEntriesService.update_Vehicle_Group_USING_Vehicle_Id(vehicle.getVehicle_Group(),
//								VEHICLE_VID, vehicle.getVehicleGroupId(), userDetails.getCompany_id());

						//issuesService.update_Vehicle_Group_USING_Vehicle_Id(vehicle.getVehicle_Group(), VEHICLE_VID, vehicle.getVehicleGroupId());

						//TripSheetService.update_Vehicle_Group_USING_Vehicle_Id(vehicle.getVehicle_Group(), VEHICLE_VID, vehicle.getVehicleGroupId());
					}

					VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToVehicle(vehicle);
					vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
					vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);

					// update Odomerter in Service Reminder Current
					ServiceReminderService.updateCurrentOdometerToServiceReminder(vehicle.getVid(),
							vehicle.getVehicle_Odometer(), userDetails.getCompany_id());
					
					serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(vehicle.getVid(), userDetails.getCompany_id(), userDetails.getId());
					if(serviceList != null) {
						for(ServiceReminderDto list : serviceList) {
							
							if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
								
								ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
								//emailAlertQueueService.sendEmailServiceOdometer(list);
								//smsAlertQueueService.sendSmsServiceOdometer(list);
							}
						}
					}

					// massages
					model.put("uploadVehicle", true);

				}
			}

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Page:", e);
			return new ModelAndView("redirect:/vehicle/1/1?danger=true");
		}

		return new ModelAndView("redirect:/showVehicle?vid=" + vehicleDto.getVid() + "", model);
	}

	/*// show Vehicle Reg
	@RequestMapping(value = "/showVehicleReg", method = RequestMethod.GET)
	public ModelAndView showvehicleReg(@ModelAttribute("command") VehicleDto vehicleDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("vehicle",
					VBL.prepareVehicleDto(vehicleService.getVehicelReg(vehicleDto.getVehicle_registration())));
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Page:", e);

		}
		return new ModelAndView("showVehicle", model);
	}
*/
	@RequestMapping(value = "/VehicleFuelDetails/{pageNumber}", method = RequestMethod.GET)
	public String VehicleFuelDetails(@PathVariable Integer pageNumber, @RequestParam("vid") Integer Vid, Model model) {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<Fuel> page = fuelService.getlistVehicleFuelReport_Page_Fuel(Vid, pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("fuelEntrie_Count", page.getTotalElements());

			VehicleDto vehicle = VBL.prepare_Vehicle_Header_Fuel_Entries_Show(
					vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(Vid, userDetails.getCompany_id()));
			model.addAttribute("vehicle", vehicle);
			// show the driver list in all

			model.addAttribute("fuel", FuBL.prepareListofFuel(fuelService.listVehicleFuelReport(Vid, pageNumber, userDetails.getCompany_id())));

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Vehicle Page:", e);
			e.printStackTrace();
		}

		return "Show_Vehicle_Fuel";
	}

	@RequestMapping(value = "/VehicleFuelGraph", method = RequestMethod.GET)
	public String VehicleFuelGraph(@RequestParam("vid") Integer Vid, Model model) {
		CustomUserDetails		userDetails		= null;
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			VehicleDto vehicle = VBL.prepare_Vehicle_Header_Fuel_Entries_Show(
					vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(Vid, userDetails.getCompany_id()));
			model.addAttribute("vehicle", vehicle);
			// show the driver list in all

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("Vehicle Page:", e);
			e.printStackTrace();
		}

		return "Show_Vehicle_FuelGraph";
	}

	@RequestMapping(value = "/VehicleFuelDetailsAdd", method = RequestMethod.GET)
	public ModelAndView VehicleFuelDetailsAdd(@ModelAttribute("command") Vehicle vehicle_id, FuelDto fuelDto,BindingResult result) {

		Map<String, Object> 			model 				= new HashMap<String, Object>();
		CustomUserDetails				userDetails			= null;
		VehicleDto 						vehicle				= null;
		VehicleDto 						vehicleBL			= null;
		HashMap<String, Object> 		configuration		= null;
		HashMap<String, Object> 		gpsConfiguration	= null;
		ValueObject 					gpsObject			= null;
		try {
			userDetails		 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicle			 = vehicleService.Get_Vehicle_Header_Fuel_ADD_Entries_Details(vehicle_id.getVid(), userDetails.getCompany_id());
			vehicleBL 		 = VBL.prepare_Vehicle_Header_Fuel_ADD_Entries_Show(vehicle);
			
			model.put("vehicle", vehicleBL);
			
			configuration 	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			gpsConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
				ValueObject	object	= new ValueObject();
				
				object.put("companyId", userDetails.getCompany_id());
				object.put("vehicleId", vehicle_id.getVid());
				gpsObject	=	vehicleGPSDetailsService.getVehicleGPSData(object);
				
				model.put("gpsObject", gpsObject);
				model.put("VEHICLE_TOTAL_KM", gpsObject.getDouble("VEHICLE_TOTAL_KM"));
			}
			ObjectMapper	mapper	= new ObjectMapper();
			model.put("configuration", configuration);
			model.put("gpsConfiguration", mapper.writeValueAsString(gpsConfiguration) );
			model.put(FuelConfigurationConstants.SHOW_VEHICLE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_VEHICLE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_VEHICLE_DEPOT_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_VEHICLE_DEPOT_COL, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_TYPE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_REFERENCE_COL, configuration.getOrDefault(FuelConfigurationConstants.SHOW_REFERENCE_COL, true));
			model.put(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, configuration.getOrDefault(FuelConfigurationConstants.SHOW_PERSONAL_EXPENCE_CHECK, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_DOCUMENT_SELECTION, true));
			model.put(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, configuration.getOrDefault(FuelConfigurationConstants.SHOW_FUEL_COMMENT_FIELD, true));
			model.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_FUEL, false));

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Page:", e);
		}

		return new ModelAndView("Add_Vehicle_Fuel", model);
	}

	@RequestMapping(value = "/deleteVehicle", method = RequestMethod.GET)
	public ModelAndView deleteVehicleTypes(@RequestParam("vid") final Integer VEHICLE_VID, Model modelAdd,
			final HttpServletRequest request, RedirectAttributes redir) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		String TID = "";
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (VEHICLE_VID != VEHICLE_DEFALAT_ZERO && VEHICLE_VID != null) {

				VehicleDto Check = vehicleService.Get_Vehicle_Current_Status_TripSheetID(VEHICLE_VID);
				if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					TID += " <span> This Vehicle  <a href=\"../../showVehicle.in?vid=" + VEHICLE_VID
							+ "\" target=\"_blank\">"
							+ Check.getVehicle_registration()
							+ " <i class=\"fa fa-external-link\"></i></a> In Other TripSheet <a href=\"../../showTripSheet.in?tripSheetID="
							+ Check.getTripSheetID() + "\" target=\"_blank\">TS-" + driverService.getCurrentTripSheetNumber(Check.getTripSheetID(), userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet, </span>, <br>";

					redir.addFlashAttribute("InAnother", TID);
					model.put("InAnotherTrip", true);
					return new ModelAndView("redirect:/vehicle/1/1", model);

				} else if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					TID += " <span> This Vehicle  <a href=\"../../showVehicle.in?vid=" + VEHICLE_VID
							+ "\" target=\"_blank\">"
							+ Check.getVehicle_registration()
							+ " <i class=\"fa fa-external-link\"></i></a> In Work Shop <a href=\"../../showWorkOrder?woId="
							+ Check.getTripSheetID() + "\" target=\"_blank\">WO-" + Check.getTripSheetID()
							+ "  <i class=\"fa fa-external-link\"></i></a> you can not Create New TripSheet, </span>, <br>";

					redir.addFlashAttribute("InAnother", TID);
					model.put("InAnotherTrip", true);
					return new ModelAndView("redirect:/vehicle/1/1", model);

				} else if (Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE
						|| Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE
						|| Check.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER) {

					if (VEHICLE_VID != null) {

						boolean validate = true;
						Object[] count = vehicleService
								.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(VEHICLE_VID);
						for (Object object : count) {
							Long cou = (Long) object;
							if (cou != 0) {
								validate = false;
							}
						}
						Date currentDateUpdate = new Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
						if (validate) {
							vehicleService.deleteVehicle(VEHICLE_VID,userDetails.getId(),toDate, userDetails.getCompany_id());
							model.put("deleteVehicle", true);
						} else {
							model.put("deleteInside", true);
						}
					}

				}
			}

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/vehicle/1/1.in?danger=true");
		}
		return new ModelAndView("redirect:/vehicle/1/1.in", model);
	}

	@RequestMapping(value = "/importVehicle", method = RequestMethod.POST)
	public ModelAndView saveImport(@ModelAttribute("command") VehicleDocument photo,
			@RequestParam("import") MultipartFile file, HttpServletRequest request) throws Exception {
		List<VehicleGroup>		vehicleGroups				= null;
		CustomUserDetails		userDetails					= null;
		List<VehicleStatus>  	vehicleStatus				= null;
		boolean					vehicleGroupExist			= false;
		boolean					vehicleStatusExist			= false;
		List<VehicleType> 		vehicleTypeList				= null;
		List<TripRoute>			tripRoutes					= null;
		List<VehicleOwnerShip>  vehicleOwnerShipList		= null;
		List<VehicleAcType>		acTypes						= null;
		List<VehicleFuelType>	vehicleFuels				= null;
		List<VehicleFuelUnit>	vehicleFuelUnits			= null;
		List<VehicleMeterUnit>	vehicleMeterUnits			= null;
		try {
			Map<String, Object> model = new HashMap<String, Object>();

			String rootPath = request.getSession().getServletContext().getRealPath("/");
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleGroups	= vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id());
			vehicleStatus	= vehicleStatusService.findAll();
			vehicleTypeList	= vehicletypeService.findAllVehileTypeByCompanyId(userDetails.getCompany_id());
			vehicleOwnerShipList	= VehicleOwnerShip.getVehicleOwerShipList();
			tripRoutes		= tripRouteService.listTripRoute(userDetails);
			acTypes				= VehicleAcType.getVehicleAcTypeList();
			vehicleFuels		= VehicleFuelType.getVehicleFuelTypeList();
			vehicleFuelUnits	= VehicleFuelUnit.getVehicleFuelUnitList();
			vehicleMeterUnits	= VehicleMeterUnit.getVehicleMeterUnitList();
			
			List<VehicleManufacturer>	mlist	=	vehicleManufacturerService.getAllVehiclManufacturer(userDetails.getCompany_id());
			List<VehicleModelDto> 	modelList	=	vehicleModelService.getVehicleModelListByCompanyId(userDetails.getCompany_id());
			List<Vehicle> vehicleList			=	vehicleService.getVehicleListByCompanyId(userDetails.getCompany_id());
			Map<String, Vehicle>  vHM			= null;
			
			if(vehicleList != null && !vehicleList.isEmpty()) {
				vHM	= vehicleList.stream().collect(Collectors.toMap(Vehicle :: getVehicle_registration, Function.identity()));
			}
			
			
			ArrayList<VehicleDto>  duplicateList	= new ArrayList<>();
					
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
				 * * model.put("msg", "failed to process file because : " +
				 * e.getMessage()); return "mainpage";
				 */

				e.printStackTrace();
			}

			// Count How many Import SuccessFully
			int CountSuccess = 0;
			int CountDuplicate = 0;

			String[] nextLine;
			try {
				// read file
				// CSVReader(fileReader, ';', '\'', 1) means
				// using separator ; and using single quote ' . Skip first line when
				// read

				try (FileReader fileReader = new FileReader(serverFile);
						CSVReader reader = new CSVReader(fileReader, ';', '\'', 1);) {
					while ((nextLine = reader.readNext()) != null) {

						Vehicle vehicle = new Vehicle();
						Integer Oddmeter = 0;

						for (int i = 0; i < nextLine.length; i++) {
							try {

								String[] importVehicle = nextLine[i].split(",");
								
								vehicle.setVehicle_registration(importVehicle[0]);
								vehicle.setVehicle_chasis(importVehicle[1]);
								vehicle.setVehicle_engine(importVehicle[2]);
								

								if(vHM != null && !vHM.isEmpty()) {
									vehicle.setVid(vHM.get(vehicle.getVehicle_registration()).getVid());
								}
								
								for(VehicleType vehicleType : vehicleTypeList) {
									if(vehicleType.getVtype().equalsIgnoreCase(importVehicle[3])) {
										vehicle.setVehicleTypeId(vehicleType.getTid());
										break;
									}
								}
								vehicle.setVehicle_year(Integer.parseInt(importVehicle[4]));
								vehicle.setVehicle_maker(importVehicle[5]);
								vehicle.setVehicle_Model(importVehicle[6]);
								vehicle.setVehicle_registrationState(importVehicle[7]);
								vehicle.setVehicle_RegisterDate(importVehicle[8].replaceAll("[/ .]", "-"));
								vehicle.setVehicle_Registeredupto(importVehicle[9].replaceAll("[/ .]", "-"));
								for(VehicleStatus status : vehicleStatus) {
									   if(status.getvStatus().equalsIgnoreCase(importVehicle[10].trim())) {
										   vehicle.setvStatusId(Short.parseShort(status.getSid()+""));
										   vehicleStatusExist = true;
										   break;
									   }
									}
								
								
								for(VehicleGroup vehicleGroup : vehicleGroups) {
									if(vehicleGroup.getvGroup().equalsIgnoreCase(importVehicle[11].trim())) {
										vehicle.setVehicleGroupId(vehicleGroup.getGid());
										vehicleGroupExist = true;
										break;
									}
								}
								
								
								for(TripRoute tripRoute : tripRoutes) {
									if(tripRoute.getRouteName().equalsIgnoreCase(importVehicle[12])) {
										vehicle.setRouteID(tripRoute.getRouteID());
										break;
									}
								}
								for(VehicleOwnerShip ownerShip : vehicleOwnerShipList) {
									if(ownerShip.getName().equalsIgnoreCase(importVehicle[13])) {
										vehicle.setVehicleOwnerShipId(ownerShip.getOwnerShipId());
										break;
									}
								}
								vehicle.setVehicle_Location(importVehicle[14]);
								vehicle.setVehicle_Color(importVehicle[15]);
								vehicle.setVehicle_Class(importVehicle[16]);
								vehicle.setVehicle_body(importVehicle[17]);
								vehicle.setVehicle_Cylinders(importVehicle[18]);
								vehicle.setVehicle_CubicCapacity(importVehicle[19]);
								vehicle.setVehicle_Power(importVehicle[20]);
								vehicle.setVehicle_wheelBase(importVehicle[21]);
								vehicle.setVehicle_SeatCapacity(importVehicle[22]);
								vehicle.setVehicle_UnladenWeight(importVehicle[23]);
								vehicle.setVehicle_LadenWeight(importVehicle[24]);
								
								for(VehicleFuelType vehicleFuelType : vehicleFuels) {
									if(vehicleFuelType.getVehicleType().equalsIgnoreCase(importVehicle[25])) {
										vehicle.setVehicleFuelId(vehicleFuelType.getVehicleFuelTypeId()+"");
										break;
									}
								}
								vehicle.setVehicle_FuelTank1(Integer.parseInt(importVehicle[26]));
								vehicle.setVehicle_Oil(Integer.parseInt(importVehicle[27]));
								
								for(VehicleMeterUnit meterUnit : vehicleMeterUnits) {
									if(meterUnit.getVehicleMeterUnit().equalsIgnoreCase(importVehicle[28])) {
										vehicle.setVehicleMeterUnitId(meterUnit.getVehicleMeterUnitId());
										break;
									}
								}

								Oddmeter = Integer.parseInt(importVehicle[29]);
								vehicle.setVehicle_Odometer(Oddmeter);
								for(VehicleFuelUnit vehicleFuelUnit : vehicleFuelUnits) {
									if(vehicleFuelUnit.getVehicleFuelUnit().equalsIgnoreCase(importVehicle[30])) {
										vehicle.setVehicleFuelUnitId(vehicleFuelUnit.getVehicleFuelUnitId());
										break;
									}
								}
								for(VehicleAcType acType : acTypes) {
									if(acType.getAcTypeName().equalsIgnoreCase(importVehicle[31])) {
										vehicle.setAcTypeId(acType.getAcTypeId());
									}
								}

								// this Default photo ID Set in import File
								vehicle.setVehicle_photoid(1);
								/*
								 * if(importVehicle[32] != null)
								 * vehicle.setVehicleGPSId(importVehicle[32].trim());
								 */

								/**
								 * who Create this vehicle get email id to user
								 * profile details
								 */
								vehicle.setCreatedById(userDetails.getId());
								vehicle.setLastModifiedById(userDetails.getId());

								/** who Create this date details */
								Date currentDateUpdate = new Date();
								Timestamp currentDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								vehicle.setCreated(currentDate);
								vehicle.setLastupdated(currentDate);
								vehicle.setMarkForDelete(false);
								vehicle.setCompany_Id(userDetails.getCompany_id());
								vehicle.setVehicle_ExpectedOdameter(5000);
								
								
								if(mlist != null && !mlist.isEmpty()) {
									for (VehicleManufacturer	manufacturer : mlist) {
										if(manufacturer.getVehicleManufacturerName().equalsIgnoreCase(vehicle.getVehicle_maker().trim())) {
											vehicle.setVehicleMakerId(manufacturer.getVehicleManufacturerId());
											break;
										}
									}
								}
								
								if(modelList != null && !modelList.isEmpty()) {
									for (VehicleModelDto veModelDto : modelList) {
										if(veModelDto.getVehicleModelName().equalsIgnoreCase(vehicle.getVehicle_Model().trim())) {
											vehicle.setVehicleModalId(veModelDto.getVehicleModelId());
											break;
										}
									}
								}
								
								if(!vehicleGroupExist) {
									model.put("vehicleGroupNotExist", vehicleGroupExist);
								}
								if(!vehicleStatusExist) {
									model.put("vehicleStatusNotExist", vehicleStatusExist);
								}
								
								if(!vehicleGroupExist || !vehicleStatusExist) {
									LOGGER.error("Vehicle Group Not Exist OR Vehicl Status Not Exists OR Adding Vehicle Permission not For Vehicle Group..");
									continue;
								}
								
								try {
									List<Vehicle> validate = vehicleService.listVehicleVaildateOLD(
											vehicle.getVehicle_registration(), vehicle.getVehicle_chasis(),
											vehicle.getVehicle_engine());
									if (validate != null && !validate.isEmpty() && vehicle.getVid() == null) {
										++CountDuplicate;
										String Duplicate = "Vehicle =" + vehicle.getVehicle_registration() + " Engine NO ="
												+ vehicle.getVehicle_engine() + " Chassis No" + vehicle.getVehicle_chasis();
										model.put("CountDuplicate", CountDuplicate);
										model.put("Duplicate", Duplicate);
										model.put("importSaveAlreadyError", true);
										VehicleDto dto = new VehicleDto();
										dto.setVehicle_registration(vehicle.getVehicle_registration());
										dto.setVehicle_chasis(vehicle.getVehicle_chasis());
										dto.setVehicle_engine(vehicle.getVehicle_engine());
										duplicateList.add(dto);
									} else {
										try {
											vehicleService.addVehicle(vehicle);
											CountSuccess++;
										} catch (final Exception e) {
											++CountDuplicate;
											String Duplicate = "Vehicle =" + vehicle.getVehicle_registration()
													+ " Engine NO =" + vehicle.getVehicle_engine() + " Chassis No"
													+ vehicle.getVehicle_chasis();
											model.put("CountDuplicate", CountDuplicate);
											model.put("Duplicate", Duplicate);
											model.put("importSaveAlreadyError", true);
											System.err.println("inside exe "+e);
											VehicleDto dto = new VehicleDto();
											dto.setVehicle_registration(vehicle.getVehicle_registration());
											dto.setVehicle_chasis(vehicle.getVehicle_chasis());
											dto.setVehicle_engine(vehicle.getVehicle_engine());
											duplicateList.add(dto);
										}


										/*try {
											List<VehicleType> ListType = vehicletypeService.findAll();
											if (ListType != null && !ListType.isEmpty()) {
												Boolean Dup = true;
												for (VehicleType vehicleType : ListType) {

													if ((vehicleType.getVtype()
															.equalsIgnoreCase(importVehicle[3]))) {
														Dup = false;
													}
												}
												if (Dup == true) {
													VehicleType type = new VehicleType();
													type.setVtype(importVehicle[3]);

													Date currentDateType = new Date();
													Timestamp toDate = new java.sql.Timestamp(currentDateType.getTime());

													type.setCreated(toDate);
													type.setLastupdated(toDate);
													type.setMarkForDelete(false);
													type.setCompany_Id(userDetails.getCompany_id());
													vehicletypeService.registerNewVehicleType(type);
												}

											} else {

												VehicleType type = new VehicleType();
												type.setVtype(importVehicle[3]);
												Date currentDateTy = new Date();
												Timestamp toDate = new java.sql.Timestamp(currentDateTy.getTime());

												type.setCreated(toDate);
												type.setLastupdated(toDate);
												type.setMarkForDelete(false);
												type.setCompany_Id(userDetails.getCompany_id());
												vehicletypeService.registerNewVehicleType(type);
											}
										} catch (Exception e) {
										}*/

									}
								} catch (final Exception e) {
									++CountDuplicate;
									String Duplicate = "Vehicle =" + vehicle.getVehicle_registration() + " Engine NO ="
											+ vehicle.getVehicle_engine() + " Chassis No" + vehicle.getVehicle_chasis();
									model.put("CountDuplicate", CountDuplicate);
									model.put("Duplicate", Duplicate);
									model.put("importSaveAlreadyError", true);
								}

							} catch (Exception e) { // catch for For loop in get
								// System.out.println("error while reading csv and
								// put to db : " + e.getMessage());
								e.printStackTrace();
								try {
									model.put("importSaveError", true);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								return new ModelAndView("redirect:/vehicle/1/1.in", model);
							}

						} // for loop close
						
					}
				}
				System.err.println("inside duplicateList "+duplicateList);
			} catch (Exception e) {
				// System.out.println("error while reading csv and put to db : " +
				// e.getMessage());
				try {
					model.put("importSaveError", true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return new ModelAndView("redirect:/vehicle/1/1.in", model);
			}

			// show the Vehicle List
			try {
				model.put("CountSuccess", CountSuccess);
				model.put("importSave", true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/vehicle/1/1.in", model);

		} catch (Exception e) {
			throw e;
		}
			}

	/***************************************************************************************************************************************
	 ************** Get Vehicle List drop down less Loading to Search
	 ***************************************************************************************************************************************/

	@RequestMapping(value = "/getVehicleFuelGraph", method = RequestMethod.GET)
	public void getVehicleList(Map<String, Object> map, @RequestParam("vehicle_id") final Integer vehicle_id,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Fuel> fuelGrahp = new ArrayList<Fuel>();

		List<Fuel> fuel = fuelService.listVehicleFuelReport(vehicle_id);
		if (fuel != null && !fuel.isEmpty()) {
			for (Fuel add : fuel) {
				Fuel wadd = new Fuel();
				// System.out.println(add.getFuel_date());
				wadd.setFuel_date(add.getFuel_date());
				wadd.setFuel_kml(add.getFuel_kml());
				// System.out.println(add.getVid());
				fuelGrahp.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(fuelGrahp));
	}

	@RequestMapping(value = "/getVehicleOdometerGraph", method = RequestMethod.GET)
	public void getVehicleOdometerGraph(Map<String, Object> map, @RequestParam("vehicle_id") final Integer vehicle_id,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<VehicleOdometerHistory> vehicleGrahp = new ArrayList<VehicleOdometerHistory>();
		
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<VehicleOdometerHistoryDto> vehicleHis = vehicleOdometerHistoryService.listVehicleOdometerHistory(vehicle_id, userDetails.getCompany_id());
		if (vehicleHis != null && !vehicleHis.isEmpty()) {
			for (VehicleOdometerHistoryDto addGraph : vehicleHis) {
				VehicleOdometerHistory wadd = new VehicleOdometerHistory();
				// System.out.println(add.getFuel_date());
				wadd.setVoh_date(addGraph.getVoh_date_On());
				wadd.setVehicle_Odometer(addGraph.getVehicle_Odometer());
				// System.out.println(add.getVid());
				vehicleGrahp.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(vehicleGrahp));
	}

	@RequestMapping(value = "/getVehicleListALLStatus", method = RequestMethod.POST)
	public void getVehicleListALLStatus(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		List<Vehicle> vehicle = vehicleService.SearchOnlyVehicleNAME_ALL_STATUS(term);
		if (vehicle != null && !vehicle.isEmpty()) {
			for (Vehicle add : vehicle) {
				VehicleDto wadd = new VehicleDto();
				wadd.setVid(add.getVid());
				wadd.setVehicle_registration(add.getVehicle_registration());
				// System.out.println(add.getVid());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/GetVehicleFuelChart", method = RequestMethod.GET)
	public void GetVehicleFuelChart(Map<String, Object> map, @RequestParam("dateFrom") final String dateFrom,
			@RequestParam("dateTo") final String dateTo, @RequestParam("vid") final Integer vehicle_id,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Fuel> chart = fuelService.Vehicel_Fuel_Chart_InDate(dateFrom, dateTo, vehicle_id);

		List<Fuel> fuelGrahp = new ArrayList<Fuel>();

		if (chart != null && !chart.isEmpty()) {

			for (Fuel chartFuel : chart) {
				Fuel wadd = new Fuel();
				if (chartFuel.getFuel_kml() != null) {

					wadd.setFuel_id(chartFuel.getFuel_id());
					wadd.setFuel_kml(chartFuel.getFuel_kml());
					wadd.setFuel_date(chartFuel.getFuel_date());

				}
				fuelGrahp.add(wadd);
			}

		}

		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(fuelGrahp));
	}
	
	@RequestMapping(value = "/getVehicleByVehicleGroupId", method = RequestMethod.GET)
	public void getVehicleByVehicleGroupId(@RequestParam(value = "vehicleGroupId", required = true) Long vehicleGroupId,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		if(vehicleGroupId != null) {
			List<Vehicle> vehicle = vehicleService.getVehicleListByVehicleGroupId(vehicleGroupId, userDetails.getCompany_id());
			if (vehicle != null && !vehicle.isEmpty()) {
				for (Vehicle add : vehicle) {
					VehicleDto wadd = new VehicleDto();
					wadd.setVid(add.getVid());
					wadd.setVehicle_registration(add.getVehicle_registration());
					// System.out.println(add.getVid());
					addresses.add(wadd);
				}
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonODDMETER = " + gson.toJson(wadd));

		response.getWriter().write(gson.toJson(addresses));
	}
	
	@RequestMapping(value = "/getVehicleListByVehicleGroupId", method = RequestMethod.POST)
	public void getVehicleListByVehicleGroupId(@RequestParam(value = "vehicleGroupId", required = true) Long vehicleGroupId,
			@RequestParam("term") final String term,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<VehicleDto> addresses = new ArrayList<VehicleDto>();
		List<Vehicle> vehicle = vehicleService.SearchOnlyVehicleNAME_ALL_STATUS(term, vehicleGroupId);
		if (vehicle != null && !vehicle.isEmpty()) {
			for (Vehicle add : vehicle) {
				VehicleDto wadd = new VehicleDto();
				wadd.setVid(add.getVid());
				wadd.setVehicle_registration(add.getVehicle_registration());
				// System.out.println(add.getVid());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonODDMETER = " + gson.toJson(wadd));

		response.getWriter().write(gson.toJson(addresses));
	}
	
	@RequestMapping("/LinkVehicleToTollAccount")
	public ModelAndView LinkVehicleToTollAccount() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("LinkVehicleToTollAccount", model);
	}
	
	@RequestMapping(value = "/getVehicleListComparison", method = RequestMethod.POST)
	public void getVehicleListComparison(Map<String, Object> map, @RequestParam("term") final String term,
			 @RequestParam("vid") final Integer  vid,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<VehicleDto> addresses = new ArrayList<VehicleDto>();
			VehicleDto wadd = null;
			List<Vehicle> DateVehicle = vehicleService.searchVehicleListCompare(term, vid);
			if (DateVehicle != null && !DateVehicle.isEmpty()) {
				for (Vehicle add : DateVehicle) {
					wadd = new VehicleDto();

					wadd.setVid(add.getVid());
					wadd.setVehicle_registration(add.getVehicle_registration());

					addresses.add(wadd);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/vehicleWiseTripSheetPickAndDrop", method = RequestMethod.GET)
	public ModelAndView vehicleWiseTripSheetPickAndDrop(@ModelAttribute("command") TripsheetPickAndDrop pickAndDrop, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleDto vehicle = VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(pickAndDrop.getVid()));
			model.put("vehicle", vehicle);
			model.put("vid", pickAndDrop.getVid());

			Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(pickAndDrop.getVid());
			model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(pickAndDrop.getVid(), userDetails.getCompany_id()));
			model.put("pickUpDropCount", TripsheetPickAndDropRepository.countTripSheet(pickAndDrop.getVid(), userDetails.getCompany_id()));
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

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);

		}

		return new ModelAndView("vehicleWiseTripSheetPickAndDrop", model);
	}
	
	@RequestMapping(value = "/getSubCompany" , method = RequestMethod.GET)
	public void getSubCompany(Map<String, Object> map, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			CustomUserDetails 	currentUser		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<SubCompanyDto> 	group			= new ArrayList<>();
			List<SubCompanyDto> 	finalList		= new ArrayList<>();
			group = vehicleService.getSubCompany(currentUser.getCompany_id());
			if(group != null && !group.isEmpty()) {
				for (SubCompanyDto add : group) {
					SubCompanyDto wadd = new SubCompanyDto();
					wadd.setSubCompanyId(add.getSubCompanyId());
					wadd.setSubCompanyName(add.getSubCompanyName());
					finalList.add(wadd);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(group));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}

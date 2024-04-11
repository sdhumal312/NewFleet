package org.fleetopgroup.rest.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleFuelBL;
import org.fleetopgroup.persistence.bl.VehicleStatusBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleFuelDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleStatus;
import org.fleetopgroup.persistence.service.ConfiguarationCacheService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IServiceProgramService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleBreakDownCalenderService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleFuelService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelTyreLayoutService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleStatusService;
import org.fleetopgroup.security.LoginAttemptService;
import org.fleetopgroup.web.controller.MainActivity;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class VehicleRestController extends MainActivity {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired	private IVehicleService		vehicleService;
	
	@Autowired	IVehicleDocumentService	vehicleDocumentService;
	
	@Autowired	private IVehicleBreakDownCalenderService	vehicleBreakDownCalenderService;
	@Autowired private ConfiguarationCacheService 			configurationCache;
	@Autowired private	IVehicleModelTyreLayoutService   	vehicleModelTyreLayoutService   ;
	@Autowired private	IVehicleOdometerHistoryService   	vehicleOdometerHistoryService   ;
	
	@Autowired
    private LoginAttemptService loginAttemptService;
	
	@Autowired
	private ICompanyConfigurationService companyConfigurationService;
	
	@Autowired
	private IVehicleStatusService vehicleStatusService;
	
	@Autowired
	private IVehicleFuelService vehicleFuelService;
	
	@Autowired IServiceProgramService	serviceProgramService;
	
	public static final Integer VEHICLE_DEFAULAT_ZERO = 0;
	

	
	VehicleBL VBL = new VehicleBL();
	
	VehicleStatusBL vehicleStatus = new VehicleStatusBL();
	VehicleFuelBL 	vehicleFuel = new VehicleFuelBL();
	
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	@RequestMapping("/ShowVehicleBreakDownCalender")
	public ModelAndView ShowDriverAd(@RequestParam("vehid") final Integer VehicleID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		VehicleDto vehicle = null;
		try {
			vehicle = vehicleService.Get_Vehicle_Header_Details(VehicleID);
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
			
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = 1;
			c.set(year, month, day);
			int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			// System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
			String driverStart = dateFormatSQL.format(c.getTime());
			// get date starting date
			model.put("startDate", "" + driverStart + "");
			// get date ending date
			c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
			String driverEnd = dateFormatSQL.format(c.getTime());
			model.put("startEnd", "" + driverEnd + "");
			model.put("startDateNew", "" + dateFormat.format(dateFormatSQL.parse(driverStart)) + "");
			model.put("startEndNew", "" +dateFormat.format(dateFormatSQL.parse( driverEnd)) + "");
			
			

			Object[] count = vehicleService
					.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(VehicleID);
			model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(VehicleID, userDetails.getCompany_id()));
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
			e.printStackTrace();
		}
		return new ModelAndView("ShowVehicleBreakDownCalender", model);
	}

	@RequestMapping(value="/getVehicleBreakDownDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  dailyMonthlyYearlyTripCollectionReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= vehicleBreakDownCalenderService.getVehicleBreakDownDetails(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("/ShowServiceEntriesNext")
	public ModelAndView ShowServiceEntriesNext(@RequestParam("vehid") final Integer VehicleID,
			@RequestParam("end") final String endDate, final HttpServletRequest request) {
		Map<String, Object> 		model 			= null;
		CustomUserDetails			userDetails		= null;
		VehicleDto 					vehicle		 	= null;
		
		try {
			model 		= new HashMap<String, Object>();
			vehicle 	= vehicleService.Get_Vehicle_Header_Details(VehicleID);
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
			Date date 	= dateFormatSQL.parse(endDate);// all done
			Calendar c 	= dateFormatSQL.getCalendar();
			c.add(Calendar.DATE, 1);
			int year 	= c.get(Calendar.YEAR);
			int month 	= c.get(Calendar.MONTH);
			int day 	= 1;
			c.set(year, month, day);
			int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			// System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
			// driver Starting date
			String driverStart = dateFormatSQL.format(c.getTime());
			model.put("startDate", "" + driverStart + "");
			c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
			// driver Ending Date
			String driverEnd = dateFormatSQL.format(c.getTime());
			model.put("startEnd", "" + driverEnd + "");
			model.put("startDateNew", "" + dateFormat.format(dateFormatSQL.parse(driverStart)) + "");
			model.put("startEndNew", "" +dateFormat.format(dateFormatSQL.parse( driverEnd)) + "");
			

			Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(VehicleID);
			model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(VehicleID, userDetails.getCompany_id()));
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
		}
		return new ModelAndView("ShowVehicleBreakDownCalender", model);
	}

	@RequestMapping("/ShowServiceEntriesPrevious")
	public ModelAndView ShowServiceEntriesPrevious(@RequestParam("vehid") final Integer VehicleID,
			@RequestParam("start") final String startDate, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		VehicleDto				vehicle			= null;
		try {
			vehicle = vehicleService.Get_Vehicle_Header_Details(VehicleID);
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(vehicle != null) {
				
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
				
				@SuppressWarnings("unused")
				Date date = dateFormatSQL.parse(startDate);// all done
				Calendar c = dateFormatSQL.getCalendar();
				c.add(Calendar.DATE, -1);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = 1;
				c.set(year, month, day);
				int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				// System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
				// driver Starting Day
				String driverStart = dateFormatSQL.format(c.getTime());
				model.put("startDate", "" + driverStart + "");
				c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
				// driver Ending Date
				String driverEnd = dateFormatSQL.format(c.getTime());
				model.put("startEnd", "" + driverEnd + "");
				
				model.put("startDateNew", "" + dateFormat.format(dateFormatSQL.parse(driverStart)) + "");
				model.put("startEndNew", "" +dateFormat.format(dateFormatSQL.parse( driverEnd)) + "");
				
				Object[] count = vehicleService
						.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(VehicleID);
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(VehicleID, userDetails.getCompany_id()));
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
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowVehicleBreakDownCalender", model);
	}
	
	@RequestMapping(value="/releaseBlockedIps", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  releaseBlockedIps(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		try {
			loginAttemptService.invalidateAll();
			
			return new HashMap<Object, Object>() ;
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/VehicleWiseBatteryReport")
	public ModelAndView VehicleWiseBatteryReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new 	ModelAndView("VehicleWiseBatteryReport",model);	
	}	
	
	
	@RequestMapping(value = "/getVehicleWiseBatteryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleWiseBatteryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.getVehicleWiseBatteryReport(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/VehicleWiseTyreReport")
	public ModelAndView VehicleWiseTyreReport() throws Exception {
		Map<String, Object> 			model = new HashMap<String, Object>();
		try {
			
			CustomUserDetails userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			model.put("configuration", configuration);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return new 	ModelAndView("VehicleWiseTyreReport",model);	
	}
	
	@RequestMapping(value = "/getVehicleWiseTyreReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleWiseTyreReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.getVehicleWiseTyreReport(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/saveDriverMonthlySalary", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveDriverMonthlySalary(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return  vehicleService.saveDriverMonthlySalary(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}

	
	@RequestMapping(value="/getDriverMonthlySalary", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getDriverMonthlySalary(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject		valueOutObject 		= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject.put("DriverMonthlySalary",vehicleService.getVehicel(valueOutObject.getInt("vehicleId"),userDetails.getCompany_id()));

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping("/bankWiseVehicleEmiDetails")
	public ModelAndView bankWiseVehicleEmiDetails() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new 	ModelAndView("BankWiseEmiDetailsReport",model);	
	}
	
	@RequestMapping(value = "/getBankWiseVehicleEmiDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getBankWiseEmiDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.getBankWiseVehicleEmiDetailsReport(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		} 
	}
	@RequestMapping("/vehicleWiseEmiDetails")
	public ModelAndView vehicleWiseEmiDetails() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new 	ModelAndView("VehicleWiseEmiDetailsReport",model);	
	}
	
	@RequestMapping(value = "/getVehicleWiseEmiDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleWiseEmiDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.getVehicleWiseEmiDetailsReport(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveVehicleKmplDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVehicleKmplDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 			object 					= null;
		CustomUserDetails		userDetails				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object.put("companyId", userDetails.getCompany_id());
			
			return vehicleService.saveVehicleKmplDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value="/refreshConfiguration", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  refreshConfiguration(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configurationCache.refreshConfiguration(userDetails.getCompany_id());
			return new HashMap<Object, Object>() ;
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addVehicleDetails", method = RequestMethod.GET)
	public ModelAndView addVehicleDetails(final HttpServletRequest request) throws Exception {
		ModelAndView 					map 		= null;
		CustomUserDetails		userDetails				= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map = new ModelAndView("addVehicleDetails");
			map.addObject("configuration", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG));
			map.addObject("gpsConfiguration", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG));
			map.addObject("vehiclestatus", vehicleStatus.prepareListofBean(vehicleStatusService.findAll()));
			map.addObject("vehiclefuel", vehicleFuel.prepareListofDto(vehicleFuelService.findAll()));
			map.addObject("serviceProgramList", serviceProgramService.getServiceProgramListByCompanyId(userDetails.getCompany_id()));
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	

	@RequestMapping(value = "/getBusDetailsForFalconITSApi", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getBusDetailsForFalconITSApi(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.getBusDetailsForFalconITSApi(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}

	@RequestMapping(value="/saveVehicleDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveVehicleDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= vehicleService.saveVehicleDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/updateVehicleDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateVehicleDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vehicleService.updateVehicleDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value = "/vehicleFuelAutocomplete", method = RequestMethod.GET)
	public void vehicleFuelAutocomplete(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<VehicleFuelDto> 		vehicleFuelDtoList		= null;
		try {
			vehicleFuelDtoList 		= new ArrayList<VehicleFuelDto>();
			vehicleFuelDtoList 		= vehicleFuel.prepareListofDto(vehicleFuelService.findAll());

			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(vehicleFuelDtoList));
		}catch(Exception e) {
			LOGGER.error("Err", e);
		}finally {
			vehicleFuelDtoList		= null;
		}
	}
	
	@RequestMapping(value = "/vehicleStatusAutocomplete", method = RequestMethod.GET)
	public void vehicleStatusAutocomplete(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<VehicleStatus> 		vehicleStatusList		= null;
		try {
			vehicleStatusList 		= new ArrayList<VehicleStatus>();
			vehicleStatusList 		= vehicleStatus.prepareListofBean(vehicleStatusService.findAll());

			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(vehicleStatusList));
		}catch(Exception e) {
			LOGGER.error("Err", e);
		}finally {
			vehicleStatusList		= null;
		}
	}
	
	
	@RequestMapping(value = "/saveVehicleTollDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVehicleToll(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.saveVehicleTollDetails(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveVehicleGPSAccount", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVehicleGPSAccount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.saveVehicleGPSAccount(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveBusDetailsForFalconITSApi", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveBusDetailsForFalconITSApi(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.saveBusDetailsForFalconITSApi(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} 
	}
	
	@RequestMapping(value="/getVehicleDetailsOnTime", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleDetailsOnTime(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			object = vehicleService.getVehicleDetailsOnTime(object);
			if(object != null) {
				return object.getHtData();
			}else {
				return null;
			}
			
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			System.err.println("Exception "+e);
			throw e;
		} finally {
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getVehicleAutoCompleteByVehicleGroup", method = RequestMethod.POST)
	public void companyWiseTyreExpenseAutocomplete2(Map<String, Object> map, @RequestParam("term") final String term,@RequestParam("vGroup") final String vGroup,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValueObject					valueObject 			= null;
		List<Vehicle> 				vehicleList				= null;
		List<Vehicle> 				finalVehicleList		= null;
		
		try {
			valueObject				= new ValueObject();
			vehicleList 			= new ArrayList<Vehicle>();
			finalVehicleList 		= new ArrayList<Vehicle>();
			
			valueObject			= vehicleService.getVehicleAutoCompleteByVehicleGroup(term,vGroup);
			vehicleList			= (List<Vehicle>) valueObject.get("vehicleList");
			
			if(vehicleList != null && !vehicleList.isEmpty()) {	
				for (Vehicle dto : vehicleList) {
					Vehicle 	vehicle 	= new Vehicle();
					vehicle.setVid(dto.getVid());
					vehicle.setVehicle_registration(dto.getVehicle_registration());

					finalVehicleList.add(vehicle);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalVehicleList));
		} catch (Exception e) {
			LOGGER.error("Err", e);
		}finally {
			valueObject 			= null;  
			vehicleList				= null;  
			finalVehicleList		= null;  
		}
	}
	
	
	@RequestMapping(value = "/getActiveVehicleListInRange", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getActiveVehicleListInRange(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.getActiveVehicleListInRange(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleIdFromVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleIdFromVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.getVehicleIdFromVehicle(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} 
	}
		@RequestMapping(value = "/getIntangleByVehicleNumber", method = RequestMethod.POST)
		public HashMap<Object, Object> getIntangleByVehicleNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
				ValueObject 					model 						 = null;
				JSONArray						intangleVehicleListJsonArray = null;
				JSONObject						object						 = null;
				JSONObject						healthInfoObj			     = null;
				HashMap<String, Object> 		vehicleConfiguration	 	 = null;
				ValueObject 					valueObject 				= null;
				String 							healthStatus 				= null;
			
			try {
				valueObject 				= new ValueObject(allRequestParams); 
				vehicleConfiguration		 = companyConfigurationService.getCompanyConfiguration(0, PropertyFileConstants.VEHICLE_GPS_CONFIG);
				intangleVehicleListJsonArray = vehicleService.getIntangleByVehicleNumber(vehicleConfiguration,valueObject);
				
				if(intangleVehicleListJsonArray != null && intangleVehicleListJsonArray.length() > 0) {
					for(int i=0; i<intangleVehicleListJsonArray.length(); i++) {
						object				= (JSONObject) intangleVehicleListJsonArray.get(i);
						healthInfoObj 		= (JSONObject) object.get("health_info");
						if(healthInfoObj.getString("health") != null) {
							healthStatus = (healthInfoObj.getString("health"));
						}
					}
					
				}
				
				model = new ValueObject();
				model.put("healthStatus", healthStatus);
					
				return model.getHtData();
			} catch (Exception e) {
				throw e;
			}
			
		}
		
		@RequestMapping(value = "/getVehicleListForCreateServiceProgram", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getVehicleListForCreateServiceProgram(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 		object 					= null;
			try {
				object 				= new ValueObject(allRequestParams);
				return vehicleService.getVehicleListForCreateServiceProgram(object).getHtData();
			} catch (Exception e) {
				LOGGER.error("Err"+e);
				throw e;
			} 
		}
		@RequestMapping(value = "/checkVehicleStatusByVid", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  checkVehicleStatusByVid(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 		object 					= null;
			try {
				object 				= new ValueObject(allRequestParams);
				return vehicleService.checkVehicleStatusByVid(object).getHtData();
			} catch (Exception e) {
				LOGGER.error("Err"+e);
				throw e;
			} 
		}
	
		/*exclude inactive surrneder sold accident status vehicle*/
		@PostMapping(value = "/getActiveVehicleDropdown")
		public void getVehicleListServiceEntries(Map<String, Object> map, @RequestParam("term") final String term,
				final HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<Vehicle>		vehicleList 		= null;
			List<VehicleDto>	vehicleFinalList 	= null;
			VehicleDto 			vehicleDto 			= null;
			try {
				vehicleList 		= new ArrayList<Vehicle>();
				vehicleFinalList 	= new ArrayList<VehicleDto>();
				
				vehicleList = vehicleService.searchActiveVehicle(term);
				if (vehicleList != null && !vehicleList.isEmpty()) {
					for (Vehicle vehicle : vehicleList) {
						vehicleDto = new VehicleDto();

						vehicleDto.setVid(vehicle.getVid());
						vehicleDto.setVehicle_registration(vehicle.getVehicle_registration());
						vehicleDto.setVehicleModelId(vehicle.getVehicleModalId());
						vehicleFinalList.add(vehicleDto);
					}
				}
				Gson gson = new Gson();

				response.getWriter().write(gson.toJson(vehicleFinalList));
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@PostMapping(value = "/getActiveVehicleDropdownByVehicleModel")
		public void getActiveVehicleDropdownByVehicleModel(Map<String, Object> map, @RequestParam("term") final String term,
				@RequestParam("vehicleModelId") final Long vehicleModelId, final HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<Vehicle>		vehicleList 		= null;
			List<VehicleDto>	vehicleFinalList 	= null;
			VehicleDto 			vehicleDto 			= null;
			try {
				vehicleList 		= new ArrayList<Vehicle>();
				vehicleFinalList 	= new ArrayList<VehicleDto>();
				
				vehicleList = vehicleService.searchActiveVehicleByVehicleModel(term,vehicleModelId);
				if (vehicleList != null && !vehicleList.isEmpty()) {
					for (Vehicle vehicle : vehicleList) {
						vehicleDto = new VehicleDto();

						vehicleDto.setVid(vehicle.getVid());
						vehicleDto.setVehicle_registration(vehicle.getVehicle_registration());
						vehicleDto.setVehicleModelId(vehicle.getVehicleModalId());
						vehicleFinalList.add(vehicleDto);
					}
				}
				Gson gson = new Gson();

				response.getWriter().write(gson.toJson(vehicleFinalList));
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@RequestMapping(value = "/getDateWiseVehicleOdometer", method = RequestMethod.GET)
		public void getDateWiseVehicleOdometer(@RequestParam(value = "vid", required = true) Integer vid,
				@RequestParam(value = "date", required = true) String  date, Map<String, Object> map, HttpServletRequest request, 
				HttpServletResponse response) throws Exception {
			
			VehicleDto wadd = new VehicleDto();
			if(vid != null) {
				VehicleOdometerHistory vehicleOdometerHistory = vehicleOdometerHistoryService.getOdoMeterAtSpecifiedTime(vid, DateTimeUtility.getTimeStamp(date));
				if (vehicleOdometerHistory != null) {
					wadd.setVehicle_Odometer(vehicleOdometerHistory.getVehicle_Odometer());
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(wadd));
			
		}
		
		@RequestMapping("/VehicleWiseTyreAssignReport")
		public ModelAndView vehicleWiseTyreAssignReport() throws Exception {
			Map<String, Object> 			model = new HashMap<String, Object>();
			try {
				
				CustomUserDetails userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				HashMap<String, Object> 		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
				model.put("configuration", configuration);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return new 	ModelAndView("VehicleWiseTyreAssignReport",model);	
		}
		@RequestMapping(value = "/getVehicleWiseTyreAssignReport", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  vehicleWiseTyreAssignReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 		object 					= null;
			
			try {
				object 				= new ValueObject(allRequestParams);
				return vehicleService.getVehicleWiseTyreAssignReport(object).getHtData();
				
			} catch (Exception e) {
				throw e;
			} 
		}	
		
		@PostMapping(value="/getVehicleExpenseDetails",produces=MediaType.APPLICATION_JSON_VALUE)
		public HashMap<Object,Object> getVehicleExpenseDetails (@RequestParam HashMap<Object,Object> allRequestParams){
			ValueObject object = null;
			try {
				object = new ValueObject(allRequestParams);
				return vehicleService.getVehicleExpenseDetails(object).getHtData();
			} catch (Exception e) {
				throw e;
			}
		}
		
		@RequestMapping("/BatteryWiseHistoryReport")
		public ModelAndView BatteryWiseHistoryReport() {
			Map<String, Object> model = new HashMap<String, Object>();
			return new 	ModelAndView("BatteryWiseHistoryReport",model);	
		}	
		
		@RequestMapping(value = "/saveDriverMonthlyBhatta", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  saveDriverMonthlyBhatta(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 		object 					= null;
			
			try {
				object 				= new ValueObject(allRequestParams);
				return  vehicleService.saveDriverMonthlyBhatta(object).getHtData();
				
			} catch (Exception e) {
				throw e;
			} 
		}
		
}

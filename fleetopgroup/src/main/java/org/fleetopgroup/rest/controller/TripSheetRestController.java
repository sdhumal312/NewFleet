package org.fleetopgroup.rest.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.dao.UserRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.LHPVDetails;
import org.fleetopgroup.persistence.model.LoadTypeMaster;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.ILHPVDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ILoadTypeService;
import org.fleetopgroup.persistence.serviceImpl.ITicketIncomeApiService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetMobileService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IUserService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RestController
public class TripSheetRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	 
	@Autowired private	ITripSheetService						tripSheetService;
	@Autowired private	ILoadTypeService        				loadTypeService;
	@Autowired private	ITicketIncomeApiService        			TicketIncomeApiService;
	@Autowired private 	ICompanyConfigurationService 			companyConfigurationService;
	@Autowired private 	ICompanyService 						companyService;
	@Autowired private	ITripSheetMobileService					tripSheetMobileService;
	@Autowired private	IUserProfileService						userProfileService;
	@Autowired private	IVehicleService							vehicleService;
	@Autowired private	ILHPVDetailsService						lhpvDetailsService;
	@Autowired	private IFuelService 							fuelService;
	@Autowired  private  IDriverService							driverService;
	@Autowired private 	IUserService							userService;
	
	TripSheetBL 							tripSheetBL			= new TripSheetBL();
	ObjectMapper							 objectMapper 		= new ObjectMapper();
	
	SimpleDateFormat SQL_DATE_FORMAT 		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat             = new SimpleDateFormat("dd-MM-yyyy");
	
	@RequestMapping(value = "/updateTripSheetClosingKM", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateTripSheetClosingKM(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.updateTripSheetClosingKM(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/updateTripSheetOpeningKM", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateTripSheetOpeningKM(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.updateTripSheetOpeningKM(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value="/getExpenseCombineDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getExpenseCombineDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.findAllTripSheetExpenseCombineList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	//Create Day Wise Expense Report Start
		@RequestMapping(value = "/getCreateDayWiseExpenseReport", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getCreateDayWiseExpenseReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			
			
			
			ValueObject 	object 			= null;
			ValueObject		valueOutObject 	= null;
			try {
				object              = new ValueObject(allRequestParams);			
				
				valueOutObject      = tripSheetService.getCreateDayWiseExpenseReport(object);
				return valueOutObject.getHtData();
			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
		@RequestMapping(value = "/getVoucherDateWiseExpenseReport", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getVoucherDateWiseExpenseReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			
			
			
			ValueObject 	object 			= null;
			ValueObject		valueOutObject 	= null;
			try {
				object              = new ValueObject(allRequestParams);			
				
				valueOutObject      = tripSheetService.getVoucherDateWiseExpenseReport(object);
				return valueOutObject.getHtData();
			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
	//Create Day Wise Expense Report Stop		
	
		@RequestMapping(value = "/addLoadTypes", method = RequestMethod.GET)
		public ModelAndView addLoadTypes(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
			ModelAndView map = new ModelAndView("addLoadTypes");
			try {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
				map.addObject("permissions", permission);
			}  catch (Exception e) {
				LOGGER.error("Exception addLoadTypes : ", e);
				throw e;
			}
			return map;
		}			

		@RequestMapping(value = "/saveLoadTypes", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  saveLoadTypes(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			
			ValueObject 		object 					= null;
			
			try {
				object 				= new ValueObject(allRequestParams);				
				return loadTypeService.saveLoadTypes(object).getHtData();					
			} catch (Exception e) {
				throw e;
			} 
		}
		
	@RequestMapping(value = "/getLoadTypesList", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getLoadTypesList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
			ValueObject 		object 					= null;
			
			try {
				object 				= new ValueObject(allRequestParams);
				
				return loadTypeService.getLoadTypesList(object).getHtData();
				
			} catch (Exception e) {
				throw e;
			} 
		}

	@RequestMapping(value = "/getLoadTypesById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getLoadTypesById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return loadTypeService.getLoadTypesById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}	

	@RequestMapping(value = "/editLoadTypes", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editLoadTypes(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return loadTypeService.editLoadTypes(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}	
	
	@RequestMapping(value = "/deleteLoadType", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteLoadType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return loadTypeService.deleteLoadType(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}		
	
	//Drop Down
	@RequestMapping(value = "/getLoadListInfo", method = RequestMethod.POST)
	public void getClothTypesList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<LoadTypeMaster>   LoadTypesList		= new ArrayList<>();
		List<LoadTypeMaster>   loadTypesListService	= loadTypeService.getLoadTypesListDropDown(term, userDetails.getCompany_id());
			
		if(loadTypesListService != null && !loadTypesListService.isEmpty()) {
			for(LoadTypeMaster clothTypes : loadTypesListService) {
				LoadTypesList.add(clothTypes);
			}
		}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(LoadTypesList));
	}
	
	@RequestMapping(value="/getIVLoadingSheetDataForTrip", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getIVLoadingSheetDataForTrip(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		CustomUserDetails	userDetails				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object.put("userDetails", userDetails);
			object.put("fromTripSheetClose", false);
			
			return tripSheetService.getIVLoadingSheetDataForTrip(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	@RequestMapping(value="/getLoadingSheetIncomeDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getLoadingSheetIncomeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getLoadingSheetIncomeDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	
	@RequestMapping(value="/getTicketIncomeApiDeatils", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTicketIncomeApiDeatils(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return TicketIncomeApiService.getTicketIncomeApiDeatilsById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}
	
	@RequestMapping(value = "/newTripSheetEntries", method = RequestMethod.GET)
	public ModelAndView NewVehicleType(@ModelAttribute("command") TripSheet TripSheetDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration				= null;
		CustomUserDetails 			userDetails				 	= null;
		ModelAndView 				map 			          	= new ModelAndView();

		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);

			model.put("TripSheetCount", tripSheetService.countTripSheet(userDetails));
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
			map.addObject("permissions", permission);
			model.put("configuration", configuration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			if(TripSheetDto != null && TripSheetDto.getLoadTypeId() > 0 ) {
				model.put("loadTypeId", TripSheetDto.getLoadTypeId());
			}

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

			model.put("DayFive",dateFormat.format(SQL_DATE_FORMAT.parse(DayFive)));
			model.put("DayOne", dateFormat.format(SQL_DATE_FORMAT.parse(DayOne)));
			model.put("DayTwo", dateFormat.format(SQL_DATE_FORMAT.parse(DayTwo)));
			model.put("DayThree", dateFormat.format(SQL_DATE_FORMAT.parse(DayThree)));
			model.put("DayFour",dateFormat.format(SQL_DATE_FORMAT.parse( DayFour)));
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
		return new ModelAndView("newTripSheetEntries", model);
	}
	
	@RequestMapping(value = "/getToDaysTripSheetList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getToDaysTripSheetList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.getToDaysTripSheetList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getDispatchTripSheetList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDispatchTripSheetList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.getDispatchTripSheetList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getManageTripSheetList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getManageTripSheetList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.getManageTripSheetList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getAdvCloseTripSheetList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getAdvCloseTripSheetList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.getAdvCloseTripSheetList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTripSheetAccountList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetAccountList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.getTripSheetAccountList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTripSheetAccountCloseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetAccountCloseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.getTripSheetAccountCloseList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/printDispatchTripsheet", method = RequestMethod.GET)
	public ModelAndView PrintBatteryInventory(@RequestParam("id") final Integer tripsheetId,
			final HttpServletResponse resul) {
 			Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("tripsheetId", tripsheetId);
			
			
		} catch (Exception e) {
			
			/*LOGGER.error("Work Order:", e);*/
		}
		return new ModelAndView("printDispatchTripsheet", model);
	}
	
	@RequestMapping(value="/getDispatchTripsheetDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getDispatchTripsheetDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		CustomUserDetails			userDetails						= null;
		long						tripsheetId						= 0;

		try {
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tripsheetId			= object.getLong("tripsheetId", 0);
			
			valueOutObject.put("company_id", userDetails.getCompany_id());
			valueOutObject.put("companyDetails", companyService.getCompanyByID(userDetails.getCompany_id()));
			valueOutObject.put("tripsheetDetails", tripSheetService.getTripSheetDetails(tripsheetId,userDetails));
		
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
			userDetails						= null;
			tripsheetId						= 0;   
		}
	}
	
	@RequestMapping(value = "/driverPenalty", method = RequestMethod.GET)
	public ModelAndView driverPenalty(@RequestParam("ID") final Long tripsheetId, final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("addDriverPenalty");
		try {
			CustomUserDetails				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("tripsheetId", tripsheetId);
			map.addObject("permissions", permission);
			
		}  catch (Exception e) {
			LOGGER.error("Exception addLoadTypes : ", e);
			throw e;
		}
		return map;
	}	
	
	
	@RequestMapping(value = "/getTripSheetByTripsheetId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetByTripsheetId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.getTripsheetByTripsheetId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveDriverPenalty", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveDriverPenalty(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.saveDriverPenalty(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/getDriverPenaltyByTripsheetId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDriverPenaltyByTripsheetId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.getDriverPenaltyByTripsheetId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteDriverPenalty", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteDriverPenalty(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.deleteDriverPenalty(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	/*
	@RequestMapping(value = "/saveDriverPenalty", method = RequestMethod.GET)
	public ModelAndView saveDriverPenalty(@RequestParam("ID") final Long tripdailyid, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (tripdailyid != null) {

				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				TripDailySheetDto TripCol = TripDailySheetService.Get_Showing_TripDaily_Sheet(tripdailyid, userDetails);
				// get the Trip sheet ID to Details
				model.put("TripDaily", TDBL.Show_TripDailySheet_page(TripCol));

				List<DriverSalaryAdvanceDto> DriverAdvanvce = DriverSalaryAdvanceService
						.List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(tripdailyid,
								DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY, userDetails.getCompany_id());

				model.put("DriverAdvanvce", DriverAdvanvce);

				*//**
				 * who Create this get email id to user profile details
				 *//*
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();
				model.put("userName", userName.getFirstName());

				HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id()
						,PropertyFileConstants.PENALTY_CONFIGURATION_CONFIG);
				
				model.put(PenaltyConfigurationConstants.SHOW_MODE_OF_PAYMENT_COL, configuration.getOrDefault(PenaltyConfigurationConstants.SHOW_MODE_OF_PAYMENT_COL, true));
				model.put(PenaltyConfigurationConstants.SHOW_CASH_RECEIPT_COL, configuration.getOrDefault(PenaltyConfigurationConstants.SHOW_CASH_RECEIPT_COL, true));
				model.put(PenaltyConfigurationConstants.SHOW_PENALTY_DATE_COL, configuration.getOrDefault(PenaltyConfigurationConstants.SHOW_PENALTY_DATE_COL, true));
				model.put(PenaltyConfigurationConstants.SHOW_PENALTY_PAID_BY_COL, configuration.getOrDefault(PenaltyConfigurationConstants.SHOW_PENALTY_PAID_BY_COL, true));
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("TripSheet Page:", e);
		}
		return new ModelAndView("addDailyPenalty", model);
	}
	*/
	
	
	@RequestMapping(value = "/getDriverDetailsFromItsGatewayApi", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDriverDetailsFromItsGatewayApi(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getDriverDetailsFromItsGatewayApi(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
		
		
	@RequestMapping(value = "/saveVoucherDate", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVoucherDate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {	
		ValueObject 	object 			= null;
		try {
			object              = new ValueObject(allRequestParams);			
			
			 tripSheetService.saveVoucherDate(object);
			 
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	@RequestMapping(value = "/updateTallyCompanyToTripSheet", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateTallyCompanyToTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {	
		ValueObject 	object 			= null;
		try {
			object              = new ValueObject(allRequestParams);			
			
			object	= tripSheetService.updateTallyCompanyToTripSheet(object);
			 
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/addDueAmount", method = RequestMethod.GET)
	public ModelAndView addDueAmount(@RequestParam("ID") final Long tripsheetId, final HttpServletRequest request) throws Exception {
		ModelAndView map = new ModelAndView("addDueAmount");
		try {
			CustomUserDetails				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			HashMap<String, Object> 	    configuration   = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			
			map.addObject("tripsheetId", tripsheetId);
			map.addObject("permissions", permission);
			map.addObject("configuration",configuration);
		}  catch (Exception e) {
			LOGGER.error("Exception addDueAmount : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/saveDueAmount", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveDueAmount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.saveDueAmount(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getDueAmountList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDueAmountList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getDueAmountList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeDueAmount", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeDueAmount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.removeDueAmount(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/tripsheetDueAmount")
	public ModelAndView tripsheetDueAmount(final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration		= null;
		HashMap<String, Object> 	tripConfiguration		= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration  = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			tripConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			
			model.put("tripConfiguration",tripConfiguration);
			tripConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			
			model.put("tripConfiguration",tripConfiguration);
			model.put("configuration", configuration);
			
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			
			model.put("accessToken", uniqueID);
			
			
		}catch(Exception e) {
			throw e;
		}
		return new ModelAndView("tripsheetDueAmount", model);
	}
	
	@RequestMapping(value = "/getDueAmountReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDueAmountReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getDueAmountReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getDueAmountPaymentById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDueAmountPaymentById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getDueAmountPaymentById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveDueAmountPaymentDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveDueAmountPaymentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.saveDueAmountPaymentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/tripsheetDueAmountPayment")
	public ModelAndView tripsheetDueAmountPayment() {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration		= null;
		HashMap<String, Object> 	tripConfiguration	= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			
			tripConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			
			model.put("tripConfiguration",tripConfiguration);
			
			model.put("configuration",configuration );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new ModelAndView("tripsheetDueAmountPayment", model);
	}
	
	@RequestMapping(value = "/getDueAmountPaymentReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDueAmountPaymentReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getDueAmountPaymentReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getVehicleTripSheetDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleTripSheetDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object              = new ValueObject(allRequestParams);			
			
			object	=	 tripSheetService.getVehicleTripSheetDetails(object);
			 
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/updateExpenseRemark", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateExpenseRemark(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object              = new ValueObject(allRequestParams);			
			
			object	=	 tripSheetService.updateExpenseRemark(object);
			 
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/addTripSheetEntries", method = RequestMethod.GET)
	public ModelAndView addVehicleType(@ModelAttribute("command") TripSheetDto TripSheetDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration		= null;
		HashMap<String, Object> 	gpsConfiguration	= null;
		List<LHPVDetails>			lhpvDetails			= null;
		Vehicle						vehicle				= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
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
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
			ObjectMapper objectMapper = new ObjectMapper();
			model.put("gpsConfiguration", objectMapper.writeValueAsString(gpsConfiguration));
			model.put("config", objectMapper.writeValueAsString(configuration));
			model.put("configuration", configuration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			model.put("branchId", Orderingname.getBranch_id());
			model.put("allowGPSIntegration", (boolean)gpsConfiguration.get("allowGPSIntegration"));
			model.put("allowITSGatewayDriverDetails",(boolean) configuration.get("allowITSGatewayDriverDetails"));
			model.put("backDate", DateTimeUtility.substractDayFromDate(new Date(), (int)configuration.get("noOfDaysForBackDate")));
			if(permission.contains(new SimpleGrantedAuthority("NUM_OF_BACK_DATE_FOR_ADMIN"))){
				model.put("backDateStr", DateTimeUtility.getBackDateStr((int)configuration.get("noOfDaysForBackDateForAdmin")));
			}else {
				model.put("backDateStr", DateTimeUtility.getBackDateStr((int)configuration.get("noOfDaysForBackDate")));
			}
			
			model.put("serverDate",DateTimeUtility.getDateAfterNoOfDays(0));
			model.put("DefaultTime",DateTimeUtility.getTimeFromTimeStamp(DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.HHH_MM));
			
		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
		}finally {
			lhpvDetails			= null;
			vehicle				= null;
		}
		return new ModelAndView("addTripSheetEntries", model);
	}
	
	@PostMapping(value = "/saveTripSheetData", produces="application/json")
	public HashMap<Object, Object>  saveTripSheetData(@RequestParam("tripDetails") String allRequestParams, HttpServletRequest request,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		ValueObject 	object 			= null;
		try {
			
			object = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			return tripSheetMobileService.saveOrDispatchTripsheet(object,request,uploadfile).getHtData();
			 
		} catch (Exception e) {
			object = new ValueObject();
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			object.put("accessToken", uniqueID);
			return object.getHtData();
		} finally {
		}
	}
	
	@RequestMapping(value = "/updateTripSheetData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateTripSheetData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object              = new ValueObject(allRequestParams);			
			
			return tripSheetMobileService.updateTripSheetDetails(object).getHtData();
			 
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value="/getLastNextTripSheetDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getLastNextTripSheetDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object = tripSheetService.getLastNextTripSheetDetails(object);
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
	
	@RequestMapping(value="/getLastNextTripSheetDetailsForEdit", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getLastNextTripSheetDetailsForEdit(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object = tripSheetService.getLastNextTripSheetDetailsForEdit(object);
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
	
	@RequestMapping(value = "/saveTripSheetCloseDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetCloseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripSheetCloseDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetCloseDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTripSheetAdvanceList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetAdvanceList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.getTripSheetAdvanceList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getTripSheetAdvanceList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripAdvance", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripAdvance(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripSheetAdvanceDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getTripSheetAdvanceList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripSheetAdvanceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetAdvanceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripSheetAdvanceDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetAdvanceDetails:"+ e);
			throw e;
		} 
	}

	@RequestMapping(value = "/getTripSheetExpenseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetExpenseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object              = new ValueObject(allRequestParams);			
			
			object	=	 tripSheetMobileService.getTripSheetExpenseList(object);
			 
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/saveTripSheetExpenseDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetExpenseDetails(@RequestParam("tripSheetExpenseData") String allRequestParams,
			@RequestParam("input-file-preview") MultipartFile[] uploadfile) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject();
			object = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			
			if(object.getBoolean("isMultiple",false)) {
				object.put("expenseDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("expenseDetails")));
			}
			return tripSheetMobileService.saveTripSheetExpenseDetails(object,uploadfile).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetExpenseDetails:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripSheetExpenseDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripSheetExpenseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripSheetExpenseDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("removeTripSheetExpenseDetails:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripSheetIncomeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetIncomeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			if(object.getBoolean("isMultiple",false)) {
				object.put("incomeDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("incomeDetails")));
			}
			return tripSheetMobileService.saveTripSheetIncomeDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetIncomeDetails:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripSheetIncomeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripSheetIncomeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripSheetIncomeDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("removeTripSheetIncomeDetails:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripSheetDriverHaltDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetDriverHaltDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripSheetDriverHaltDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetDriverHaltDetails:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripSheetDriverHaltDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripSheetDriverHaltDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripSheetDriverHaltDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("removeTripSheetDriverHaltDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripComment", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripComment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripComment(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripComment:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripComment", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripComment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripComment(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripComment:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteTripSheetDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteTripSheetDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.deleteTripSheetDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("deleteTripSheetDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/superUserTripSheetDelete", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  superUserTripSheetDelete(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.superUserTripSheetDelete(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("deleteTripSheetDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchTripSheetByNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchTripSheetByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetMobileService.searchTripSheetByNumber(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("deleteTripSheetDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchTripSheetDiffStatus", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchTripSheetDiffStatus(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetMobileService.searchTripSheetDiffStatus(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("deleteTripSheetDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchVehCurTSShow", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchVehCurTSShow(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.searchVehCurTSShow(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("deleteTripSheetDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/savecloseAccountTripSheet", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  savecloseAccountTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.savecloseAccountTripSheet(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("savecloseAccountTripSheet:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/showTripSheet", method = RequestMethod.GET)
	public ModelAndView ShowTripsheet(@RequestParam("tripSheetID") final Long tripSheetId, HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);

			model.put("configuration", configuration);
			model.put("tripSheetId", tripSheetId);
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newTripSheetEntries.in", model);
		}

			return new ModelAndView("showTripSheet", model);
	}
	
	@RequestMapping(value = "/showTripSheetDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  showTripSheetDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.showTripSheetDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("showTripSheetDetails:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping("/download/TripsheetExpenseDocument/{tripSheetExpenseDocumentId}")
	public String  downloadTripsheetExpenseDocument(@PathVariable("tripSheetExpenseDocumentId") Long tripSheetExpenseDocumentId, HttpServletResponse response) throws Exception {
		CustomUserDetails 	userDetails 	=  null;
		try {
			
			if (tripSheetExpenseDocumentId <= 0) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.TripSheetExpenseDocument document = tripSheetMobileService.getTripSheetExpenseDocumentDetails(tripSheetExpenseDocumentId, userDetails.getCompany_id());
				
				
				if (document != null) {
					if (document.getTripSheetExpenseContent() != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + document.getTripSheetExpenseFileName() + "\"");
						OutputStream  out = response.getOutputStream();
						response.setContentType(document.getTripSheetExpenseContentType());
						response.getOutputStream().write(document.getTripSheetExpenseContent());
						out.flush();
						out.close();

					}
				}
			}

		
		}catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		}catch (Exception e) {
			throw e;
		}
		return null;
	}
	
	@PostMapping(path = "/updateTripSheetUsageKM", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  updateTripSheetUsageKM(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.updateTripSheetUsageKM(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/updateTripRoutePoint", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  updateTripRoutePoint(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return tripSheetService.updateTripRoutePoint(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getPreviousDueAmountData", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getPreviousDueAmountData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getPreviousDueAmountData(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getPreviousDueAmountDataList", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getPreviousDueAmountDataList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getPreviousDueAmountDataList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(value = "/downloadTripsheetdocument")
	public String downloadTripsheetdocument(HttpServletResponse response) {

		XSSFWorkbook 						hssfWorkbook			= null;
		XSSFSheet 							hssfSheet				= null;
		XSSFRow 							rowZero 				= null;
		XSSFRow 							hssfRow					= null; 
		DataValidationHelper 				validationHelper 		= null;
		try {
			hssfWorkbook				= new XSSFWorkbook();
			hssfSheet					= hssfWorkbook.createSheet("sheet1");
			rowZero 					= hssfSheet.createRow((short) 0);
			hssfRow 					= hssfSheet.createRow((short) 1);
		
		    validationHelper  		= new XSSFDataValidationHelper(hssfSheet);
		   
		   
		    hssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
		    
		    rowZero.createCell(0).setCellValue("Note : (*) marked filled are mandatory.Please Do not Add Duplicate Vehicle Or Driver");
		    hssfRow.createCell(0).setCellValue("Vehicle Number (*)");
		    hssfRow.createCell(1).setCellValue("Open Date Time (dd-MM-yyyy HH:mm:ss ) (*)");
			hssfRow.createCell(2).setCellValue("Trip Close Date Time(dd-MM-yyyy HH:mm:ss) (*)");
			hssfRow.createCell(3).setCellValue("Dispatch Time (*)");
			hssfRow.createCell(4).setCellValue("Driver Name (*)");
			hssfRow.createCell(5).setCellValue("Route Name (*)");
			hssfRow.createCell(6).setCellValue("Open Odometer (*)");
			hssfRow.createCell(7).setCellValue("Second Driver Name");
			
			
			hssfSheet = tripSheetService.getHssfSheet(validationHelper,hssfSheet);
			    
		    for (int i=0; i < hssfRow.getLastCellNum(); i++){
		    	hssfSheet.autoSizeColumn(i);
		    }

			FileOutputStream fileOut = new FileOutputStream(new File("TripsheetImport.xlsx"));
			hssfWorkbook.write(fileOut);
			fileOut.close();

			//Code to download
			File fileToDownload = new File("TripsheetImport.xlsx");
			InputStream in = new FileInputStream(fileToDownload);

			// Gets MIME type of the file
			String mimeType = new MimetypesFileTypeMap().getContentType("TripsheetImport.xlsx");

			if (mimeType == null) {
				// Set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			// Modifies response
			response.setContentType(mimeType);
			response.setContentLength((int) fileToDownload.length());

			// Forces download
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileToDownload.getName());
			response.setHeader(headerKey, headerValue);

			// obtains response's output stream
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = in.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			in.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//@SuppressWarnings({ "resource", "deprecation", "unchecked" })
	@PostMapping(value = "/importTripsheet")
	public ModelAndView importTripsheet(@RequestParam("import") MultipartFile file, HttpServletRequest request) throws Exception {
		Map<String, Object> 				model 						= null; 
		String 								rootPath 					= null;
		File 								dir 						= null;
		File 								serverFile 					= null;
		FileInputStream 					fis 						= null;
		XSSFWorkbook 						myWorkBook 					= null; 
		try {
			
			rootPath 				= request.getSession().getServletContext().getRealPath("/");
			dir 					= new File(rootPath + File.separator + "uploadedfile");
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
			myWorkBook = new XSSFWorkbook (fis);
			
			// Return first sheet from the XLSX workbook
			tripSheetService.saveTripsheetExcel(myWorkBook);
			
		} catch (Exception e) {
			LOGGER.error("Excetion : ", e);
			throw e;
		}
		return new ModelAndView("redirect:/newTripSheetEntries.in", model);
	}
	
	@RequestMapping("/download/tripSheetDocument/{tripSheetDocumentId}")
	public String  downloadUreaInvoiceDocument(@PathVariable("tripSheetDocumentId") Long tripSheetDocumentId, HttpServletResponse response) throws Exception {
		CustomUserDetails 	userDetails 	=  null;
		try {
			if (tripSheetDocumentId <= 0) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.TripSheetDocument document = tripSheetMobileService.getTripSheetDocumentDetails(tripSheetDocumentId, userDetails.getCompany_id());
				if (document != null) {
					if (document.getTripSheetContent() != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + document.getTripSheetFileName() + "\"");
						OutputStream  out = response.getOutputStream();
						response.setContentType(document.getTripSheetContentType());
						response.getOutputStream().write(document.getTripSheetContent());
						out.flush();
						out.close();

					}
				}
			}

		}catch (Exception e) {
			throw e;
		}
		return null;
	}
	
	@PostMapping(value = "/updateTripSheetDocument", produces="application/json")
	public HashMap<Object, Object>  updateTripSheetDocument(@RequestParam("updateDocument") String allRequestParams, HttpServletRequest request,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			return tripSheetMobileService.updateTripSheetDocument(object,uploadfile).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@PostMapping(path = "/getTripSheetDetailsAtTime", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getTripSheetDetailsAtTime(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getTripSheetDetailsAtTime(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getLastTripsheetFuelMileage", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getLastTripsheetFuelMileage(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getLastTripsheetFuelMileage(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}

	
	@RequestMapping(value = "/getTripSheetDataOnDateRange", method = RequestMethod.POST, produces="application/json")
	public Map<Object, Object>  getTripSheetDataOnDateRange(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			return tripSheetService.getTripSheetDataOnDateRange(new ValueObject(allRequestParams)).getHtData();
		} catch (Exception e) {
			throw e;
		}
	}	
	
	@RequestMapping(value = "/closeAccountOfSelectedTripSheet", method = RequestMethod.POST, produces="application/json")
	public Map<Object, Object>  closeAccountOfSelectedTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		valueObject 					= null;
		try {
				valueObject =  new ValueObject(allRequestParams);
				valueObject.put("tripSheetDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("tripSheetDetails")));
			
			return tripSheetService.closeAccountOfSelectedTripSheet(new ValueObject(allRequestParams)).getHtData();
		} catch (Exception e) {
			throw e;
		}
	}	

	
	
	@RequestMapping(value = "/saveChangeStatusTripSheet", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveChangeStatusTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveChangeStatusTripSheet(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("savecloseAccountTripSheet:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/SaveTripsheetWeight", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  SaveTripsheetWeightDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			System.err.println("inside controller ");
			object 				= new ValueObject(allRequestParams);
			System.err.println("valueObject. "+ object);
			return tripSheetMobileService.saveTripSheetRouteWiseWeightDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetAdvanceDetails:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTripSheetRouteWiseWeightList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetRouteWiseWeightList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.getTripSheetRouteWiseWeightList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getTripSheetAdvanceList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripWeight", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripWeight(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripSheetRouteWiseWightDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getTripSheetAdvanceList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value="/getLastTripSheetDetailsForDriver", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getLastTripSheetDetailsForDriver(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object = tripSheetService.getLastTripSheetDetailsForDriver(object);
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

	@SuppressWarnings("unused")
	@RequestMapping(value = "/TripsheetAdd_API", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  CreateTripsheet(@RequestBody HashMap<Object, Object> requestBody, HttpServletRequest request) throws Exception {
		ValueObject 					object 					= null;
		HashMap<Object, Object>   		reponse 				= new  HashMap<Object, Object>();
		MultipartFile					uploadfile 				= null;
		String							Registration			= null;
		Vehicle							vehicle					= null;
		ValueObject						valueObject 			= null;
		Integer 						companyId				= null;
		Integer							driverId				= null;
		Integer							driver2Id				= null;
		User						    user 					= null;
		HashMap<String, Object> 		configuration			= null;
		

		try {
			System.err.println("requestBody -- "+ requestBody);
			
			object 				= new ValueObject(requestBody);
			Registration = object.getString("vehicleNumber");
			vehicle = vehicleService.GetVehicleByRegNo(Registration);

			driverId  = (object.getString("driver1DL") !="" && StringUtils.isNotEmpty(object.getString("driver1DL")) && object.getString("driver1DL") !=null) ? driverService.getDriverByDriverLicense(object.getString("driver1DL")) : 0;
			driver2Id = (object.getString("driver2DL") !="" && StringUtils.isNotEmpty(object.getString("driver2DL")) && object.getString("driver1DL") !=null) ? driverService.getDriverByDriverLicense(object.getString("driver2DL")) : 0;
			
			if(vehicle !=null) {
				companyId = vehicle.getCompany_Id();
				object.put("vid", vehicle.getVid());
				object.put("tripstatusId", TripSheetStatus.TRIP_STATUS_DISPATCHED);
				object.put("companyId", vehicle.getCompany_Id());
				object.put("backDateDispatchDate", object.getString("fromDate"));
				object.put("backDateDispatchTime", object.getString("dispatchByTime"));
				
				configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				user = userService.findUserByemailId((String)configuration.get("LMTautoUser"), companyId);
				object.put("userId", user.getId());
				
				
				if((driverId != null && driverId >0) || (driver2Id >0 && driver2Id != null) ) {
					object.put("driverId", driverId);
					object.put("driver2Id",driver2Id);
					
					try {
						valueObject = tripSheetMobileService.saveOrDispatchTripsheet(object,request,uploadfile);
						reponse.put(
							    (valueObject.getBoolean("saveTripSheet")) ? "TripsheetSucess" : "TripsheetError",
							    (valueObject.getBoolean("saveTripSheet")) ? "Tripsheet Created Successfully" : "Couldn't Create Tripsheet"
							);
						reponse.put("tripsheetID",valueObject.getInt("tripsheetID"));
					   }catch(Exception e) {
							e.printStackTrace();
					   }
				}else{
					reponse.put("DriverError" , "Driver Not Found with DL number "+ object.getString("driver1DL") + " , " +object.getString("driver2DL"));
					reponse.put("TripsheetError", "Coludn't Created Tripsheet");
					return reponse;
				}
			}else {
				reponse.put("VehicleError" , "This " +object.getString("vehicleNumber") + " vehicle Not Found");
				return reponse;
			}
			
		} catch (Exception e) {
			System.err.println("e");
			
			throw e;
		} 
		return reponse;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tripSheetClose_API", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  CloseTripsheetMethod(@RequestBody HashMap<Object, Object> requestBody, HttpServletRequest request) throws Exception {
		HashMap<Object, Object>   		reponse 				= new  HashMap<Object, Object>();
		String							Registration			= null;
		Vehicle							vehicle					= null;
		Integer 						companyId				= null;
		ArrayList<ValueObject>			fulearray 				= null;
		ArrayList<ValueObject>			expensearray 			= null;
		ValueObject						fuelObject			    = null;	
		ValueObject 					object					= null;
		User						    user 					= null;
		HashMap<String, Object> 		configuration			= null;
		Fuel 							fuel					= null;
		TripSheet						trip 					= null;
		
		try {
		    object 				= new ValueObject(requestBody);
			Registration 		= object.getString("vehicleNumber");
			vehicle 			= vehicleService.GetVehicleByRegNo(Registration);
			trip 				= tripSheetService.getTripsheetByLhpvId(object.getInt("lhpvId"));
			
			if(vehicle != null) {
				companyId = vehicle.getCompany_Id();
				object.put("vid", vehicle.getVid());
				object.put("vehicleReg", vehicle.getVehicle_registration());
				object.put("companyId", companyId);
				
				configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				user = userService.findUserByemailId((String)configuration.get("LMTautoUser"), companyId);
				object.put("userId", user.getId());
				
				//exp starts
				if(object.containsKey("charges") && object.get("charges") != null) {
					object.put("expenseArray", JsonConvertor.toValueObjectFromJsonString(objectMapper.writeValueAsString(object.get("charges"))));
					expensearray 	= (ArrayList<ValueObject>) object.get("expenseArray");;
					
					if(!expensearray.isEmpty()) {
						object = tripSheetMobileService.saveTripsheetExpensesFromClosTripApi(expensearray,object);
	
						if(object.getBoolean("ExpenseSave"))
							reponse.put("ExpenseSuccess", "Expenses Added Successfully ! For Amount"+object.getString("createdExpense"));
						if(object.getBoolean("already"))
							reponse.put("ExpenseError", "Couldn't Add Expenses For Amount"+ object.getString("alreadyExpense") + " Expense Already Exist" );
					}
				}
				//expense ends
				
				
				//fuel starts
				
				if(object.containsKey("fuelDetails") && object.get("fuelDetails") != null) {	
					object.put("FuelArray", JsonConvertor.toValueObjectFromJsonString(objectMapper.writeValueAsString(object.get("fuelDetails"))));
					fulearray = (ArrayList<ValueObject>) object.get("FuelArray");
					
					if(!fulearray.isEmpty()){
						
						fuelObject = fuelService.saveTripsheetFuelDetailsFromClosTripApi(fulearray,object, vehicle);

						if(fuelObject.getString("alreadyFuel").length() >0 && fuelObject.getString("alreadyFuel") !=null){
							reponse.put("FuleError", "Coudn't Add Fuel For Amount"+ fuelObject.get("alreadyFuel") + " Fuel Entry Already Exits");
						}
						if(fuelObject.getString("createdFuel").length() >0 && fuelObject.getString("createdFuel") !=null){
							reponse.put("FuleSucces", "Fuel Added Successfully ! For Amount"+fuelObject.get("createdFuel"));
						}
						//get last fuel entry.. for closing km
						fuel 	= fuelService.getFuelMeterByTripsheetIdByDateTime(object.getLong("tripsheetId"), companyId);
						object.put("tripClosingKM", fuel.getFuel_meter());
					}
				}
				//fuel ends
				
				if(object.getInt("tripClosingKM") ==0) {
					object.put("tripClosingKM", trip.getTripOpeningKM());
				}
				
				//close tripsheet 
				object = tripSheetMobileService.saveTripSheetCloseDetails(object);
				if(object.getBoolean("closeTripSheet")) {
					reponse.put("TripsheetSuccess", "Tripsheet Closed Successfully !!");
				}else {
					reponse.put("TripsheetError", "Couldn't Close Tripsheet !!");
				}
				
			}else {
				reponse.put("VehicleError" , "This " +object.getString("vehicleNumber") + " vehicle Not Found");
			}
			
			System.err.println("response "+ reponse);	
			
		} catch (Exception e) {
			LOGGER.error("TripSheet :"+ e);
			throw e;
		} 
		return reponse;
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/addBlhpvCharges", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addBlhpvCharges(@RequestBody HashMap<Object, Object> requestBody, HttpServletRequest request) throws Exception {
		HashMap<Object, Object>   		response 				= new  HashMap<Object, Object>();
		Integer 						companyId				= null;
		ArrayList<ValueObject>			expensearray 			= null;
		ValueObject 					object					= null;
		TripSheet						trip 					= null;
		User						    user 					= null;
		HashMap<String, Object> 		configuration			= null;
		
		try {
		    object 				= new ValueObject(requestBody);
			trip = tripSheetService.getTripsheetByLhpvId(object.getInt("lhpvId"));
			
				companyId = trip.getCompanyId();
				
				object.put("vid", trip.getVid());
				object.put("companyId", companyId);
				object.put("tripsheetId", trip.getTripSheetID());
				
				configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				user = userService.findUserByemailId((String)configuration.get("LMTautoUser"), companyId);
				object.put("userId", user.getId());
				
				if(trip != null ) {
					//exp starts
					if(object.containsKey("blhpvCharges") && object.get("blhpvCharges") != null) {
						object.put("expenseArray", JsonConvertor.toValueObjectFromJsonString(objectMapper.writeValueAsString(object.get("blhpvCharges"))));
						expensearray 	= (ArrayList<ValueObject>) object.get("expenseArray");;
						
						if(!expensearray.isEmpty()) {
							object = tripSheetMobileService.saveTripsheetExpensesFromClosTripApi(expensearray,object);
							
							if(object.getBoolean("ExpenseSave"))
								response.put("ExpenseSuccess", "Expenses Added Successfully ! For Amount"+object.getString("createdExpense"));
							if(object.getBoolean("already"))
								response.put("ExpenseError", "Couldn't Add Expenses For Amount"+ object.getString("alreadyExpense") + " Expense Already Exist" );
						}
					}
				}else {
					response.put("TripsheetError", "Couldn`t Add Charges , Tripsheet Not Found for LhpvId "+ object.getInt("lhpvId"));
				}
				//expense ends
			
			System.err.println("response "+ response);	
			
		} catch (Exception e) {
			LOGGER.error("TripSheet :"+ e);
			throw e;
		} 
		return response;
	}
}

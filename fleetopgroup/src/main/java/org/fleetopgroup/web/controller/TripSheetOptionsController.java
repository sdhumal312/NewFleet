package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.bl.TripSheetOptionsBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetOptionsDto;
import org.fleetopgroup.persistence.model.TripSheetOptions;
import org.fleetopgroup.persistence.model.TripSheetOptionsHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverAttendanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverHaltService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITripCommentService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.ITripIncomeService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetOptionsHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetOptionsService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripsheetSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleMandatoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class TripSheetOptionsController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired ITripSheetService 				tripSheetService;
	@Autowired ITripSheetHistoryService 		tripSheetHistoryService;
	@Autowired IVehicleService 					vehicleService;
	@Autowired IDriverService 					driverService;
	@Autowired ITripRouteService 				TripRouteService;
	@Autowired ITripCommentService 				tripCommentService;
	@Autowired IUserProfileService 				userProfileService;
	@Autowired ITripExpenseService 				TripExpenseService;
	@Autowired ITripIncomeService 				TripIncomeService;
	@Autowired IServiceReminderService 			ServiceReminderService;
	@Autowired IVehicleOdometerHistoryService 	vehicleOdometerHistoryService;
	@Autowired IDriverAttendanceService 		DriverAttendanceService;
	@Autowired IDriverHaltService 				driverHaltService;
	@Autowired IVehicleMandatoryService 		vehicleMandatoryService;
	@Autowired IFuelService 					fuelService;
	@Autowired ITripsheetSequenceService 		tripsheetSequenceService;
	@Autowired IVehicleGroupService 			vehicleGroupService;
	@Autowired IWorkOrdersService 				workOrdersService;
	@Autowired ICompanyConfigurationService 	companyConfigurationService;
	@Autowired IVehicleDocumentService			vehicleDocumentService;

	/*FuelBL 						fuelBL 						= new FuelBL();
	VehicleOdometerHistoryBL 	vehicleOdometerHistoryBL	= new VehicleOdometerHistoryBL();
	VehicleBL 					vehicleBL					= new VehicleBL();
	DriverBL 					driverBL					= new DriverBL();*/
	TripSheetBL 				tripSheetBL					= new TripSheetBL();

	public java.util.Date removeTime(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat driverAttendanceFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static final long TRIP_SHEET_DEFALAT_VALUE = 0L;
	public static final Integer TRIP_SHEET_DEFALAT_ZERO = 0;
	public static final Double TRIP_SHEET_DEFALAT_AMOUNT = 0.0;
	
	@Autowired
	private ITripSheetOptionsService tripSheetOptionsService;
	
	@Autowired
	private ITripSheetOptionsHistoryService tripSheetOptionsHistoryService;
	
	/*@Autowired
	private TripSheetOptionsDto tripsheetOptionsDto;*/
	
	TripSheetOptionsBL tripSheetOptionsBL = new TripSheetOptionsBL();
	
	
	@RequestMapping(value = "/addTripSheetOptions", method = RequestMethod.GET)
	public ModelAndView addTripSheetOptions(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration				= null;
		
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
				model.put("tripsheetoptions", tripSheetOptionsService.findAllByCompanyId(userDetails.getCompany_id()));
				model.put("configuration", configuration);

		} catch (Exception e) {
			LOGGER.error("add tripsheet", e);
		}finally {
		}
		return new ModelAndView("tripsheet_option", model);

      }
	
	
	@RequestMapping(value = "/saveTripSheetOptions", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveTripSheetOptions(final TripSheetOptionsDto tripsheetOptionsDto, final HttpServletRequest request) {

		LOGGER.error("Registering saveTripSheetOptions account with information: {}", tripsheetOptionsDto);
		Map<String, Object> model = new HashMap<String, Object>();
		TripSheetOptions 	 	 trip_options 		= null;
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			final TripSheetOptions getvehicleType = tripSheetOptionsBL.prepareModel(tripsheetOptionsDto);

				trip_options = tripSheetOptionsService.findBytripsheetextraname(getvehicleType.getTripsheetextraname(), userDetails.getCompany_id());
				
			if (trip_options == null) {
				getvehicleType.setCreatedById(userDetails.getId());
				getvehicleType.setLastModifiedById(userDetails.getId());
				getvehicleType.setCompanyId(userDetails.getCompany_id());
				
				tripSheetOptionsService.registerNewTripSheetOpionstype(getvehicleType);
				model.put("saveTripSheetOptions", true);
			} else {
				model.put("alreadyTripSheetOptions", true);
				return new ModelAndView("redirect:/addTripSheetOptions.html", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add TripSheet Type Page:", e);
			model.put("alreadyTripSheetOptions", true);
			return new ModelAndView("redirect:/addTripSheetOptions.html", model);
		}finally {
			
		}
		return new ModelAndView("redirect:/addTripSheetOptions.html", model);
	}
	
	
	@RequestMapping(value = "/editTripSheetOptions", method = RequestMethod.GET)
	public ModelAndView editTripSheetOptions(final Locale locale, @RequestParam("Id") final Long tripsheetoptionsId, final HttpServletRequest request) {
	
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
					model.put("OptionsId", tripSheetOptionsService.get_TripSheet_Options_Id(tripsheetoptionsId, userDetails.getCompany_id()));
				
			/*model.put("TyreSizeCount", vehicleTyreSizeService.count());*/

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Trip Sheet Options Page:", e);
			model.put("alreadyTripSheetOptions", true);
			return new ModelAndView("redirect:/addTripSheetOptions.html", model);
		}finally {
			userDetails			= null;
			
		}
		return new ModelAndView("edit_TripSheetOptions", model);
	}
	
	
	
	@RequestMapping(value = "/updateTripSheetOptions", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateTripSheetOptions(final Locale locale, final TripSheetOptionsDto tripSheetOptionsDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		TripSheetOptions 	 	 validate_type 		= null;
		
		TripSheetOptions		tripSheetOptions 	= null;
		try {
			
			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            tripSheetOptions = tripSheetOptionsService.get_TripSheet_Options_Id(tripSheetOptionsDto.getTripsheetoptionsId(), userDetails.getCompany_id());
					
			if(!tripSheetOptions.getTripsheetextraname().equalsIgnoreCase(tripSheetOptionsDto.getTripsheetextraname())) {
				
					validate_type = tripSheetOptionsService.findBytripsheetextraname(tripSheetOptionsDto.getTripsheetextraname(), userDetails.getCompany_id());
				
				if(validate_type != null) {
					model.put("alreadyTripSheetOptions", true);
					return new ModelAndView("redirect:/addTripSheetOptions.in?alreadyTripSheetOptions=true", model);
					
				}
				Date currentDateUpdate = new Date(0);
				Date toDate = new java.sql.Date(currentDateUpdate.getTime());
				
				TripSheetOptionsHistory		tripSheetOptionsHistory	= tripSheetOptionsBL.prepareHistoryModel(tripSheetOptions);
				
				tripSheetOptionsService.update_TripSheet_Options_Name(tripSheetOptionsDto.getTripsheetextraname(), tripSheetOptionsDto.getTripsheetextradescription(), userDetails.getId(), toDate, tripSheetOptionsDto.getTripsheetoptionsId(), userDetails.getCompany_id());
				
				tripSheetOptionsHistoryService.registerNewTripSheetOptionsHistory(tripSheetOptionsHistory);
				model.put("updateTripSheetOptions", true);
			}else {
				return new ModelAndView("redirect:/addTripSheetOptions.in?alreadyTripSheetOptions=true", model);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Edit TripSheetOptions Page:", e);
			model.put("alreadyTripSheetOptions", true);
			return new ModelAndView("redirect:/addTripSheetOptions.html?alreadyTripSheetOptions=true", model);
			
		}finally {
			 validate_type 		= null;
			
			 tripSheetOptions 	= null;
		}
		return new ModelAndView("redirect:/addTripSheetOptions.in?Update=true", model);
	}
	
	
	
	@RequestMapping(value = "/deleteTripSheetOptions", method = RequestMethod.GET)
	public ModelAndView deleteTyreSize(final Locale locale, @RequestParam("Id") final Long tripsheetoptionsId) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		TripSheetOptionsHistory		tripSheetOptionsHistory	=	null;
		
		CustomUserDetails			userDetails				= null;
		try {
				userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				tripSheetOptionsHistory	= tripSheetOptionsBL.prepareHistoryModel(tripSheetOptionsService.get_TripSheet_Options_Id(tripsheetoptionsId, userDetails.getCompany_id()));

				tripSheetOptionsService.delete_Tripsheet_Options(tripsheetoptionsId, userDetails.getCompany_id());
				
				tripSheetOptionsHistoryService.registerNewTripSheetOptionsHistory(tripSheetOptionsHistory);
				//tripSheetOptionsHistoryService.registerNewTripSheetOptionsHistory(tripSheetOptionsHistory);
			    model.put("deleteTripSheetOptions", true);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Delete TripSheet Options Page:", e);
			model.put("alreadyTripSheetOptions", true);
			return new ModelAndView("redirect:/addTripSheetOptions.html", model);
		}finally {
			tripSheetOptionsHistory	=	null;
			
			userDetails				= null;
		}
		return new ModelAndView("redirect:/addTripSheetOptions.html", model);
	}
	
	
	@RequestMapping(value = "/getTripExtraOptionsList", method = RequestMethod.GET)
	public void getTripExtraOptionsList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<TripSheetOptions> TripExtraOptionsList = new ArrayList<TripSheetOptions>();

		for (TripSheetOptions Extra : tripSheetOptionsService.listTripExtraOptions(userDetails.getCompany_id())) {

			TripSheetOptions bean = new TripSheetOptions();
			bean.setTripsheetoptionsId(Extra.getTripsheetoptionsId());
			bean.setTripsheetextraname(Extra.getTripsheetextraname());
			
			TripExtraOptionsList.add(bean);
			
		}

		Gson gson = new Gson();

		// System.out.println("jsonApproval List = " +
		// gson.toJson(ApprovalList));

		response.getWriter().write(gson.toJson(TripExtraOptionsList));
	}

	
}
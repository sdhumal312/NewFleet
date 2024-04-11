package org.fleetopgroup.web.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverCommentBL;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.bl.ServiceEntriesBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleCommentBL;
import org.fleetopgroup.persistence.bl.VehicleFuelBL;
import org.fleetopgroup.persistence.bl.VehicleGroupBL;
import org.fleetopgroup.persistence.bl.VehicleStatusBL;
import org.fleetopgroup.persistence.bl.VehicleTypeBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.bl.VendorTypeBL;
import org.fleetopgroup.persistence.bl.WorkOrdersBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class VehicleReportController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired
	private ICompanyConfigurationService companyConfigurationService;

	@Autowired
	private ITripSheetService TripSheetService;
	TripSheetBL TSBL = new TripSheetBL();

	@Autowired
	private IServiceEntriesService ServiceEntriesService;
	ServiceEntriesBL SEBL = new ServiceEntriesBL();

	PartLocationsBL PartBL = new PartLocationsBL();

	VendorTypeBL VenType = new VendorTypeBL();

	RenewalTypeBL DriDT = new RenewalTypeBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();
	DriverReminderBL DriverRem = new DriverReminderBL();
	DriverCommentBL DriverCom = new DriverCommentBL();
	VehicleBL VBL = new VehicleBL();
	ReportBL RBL = new ReportBL();
	FuelBL FuBL = new FuelBL();

	VehicleTypeBL v = new VehicleTypeBL();
	VehicleStatusBL vs = new VehicleStatusBL();
	VehicleGroupBL vg = new VehicleGroupBL();
	VehicleFuelBL vf = new VehicleFuelBL();

	DriverBL DBL = new DriverBL();

	VendorBL VenBL = new VendorBL();

	TripSheetBL TripBL = new TripSheetBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("dd-MMM-yyyy");

	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");


	@Autowired
	private IWorkOrdersService WorkOrdersService;
	WorkOrdersBL WOBL = new WorkOrdersBL();

	@Autowired
	private IUserProfileService userProfileService;

	VehicleCommentBL VCBL = new VehicleCommentBL();

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping("/VR")
	public ModelAndView VehicleReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>     configuration	= null;
		CustomUserDetails userDetails = null;		
		
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		return new ModelAndView("VR", model);
	}

	@RequestMapping("/RepairReport")
	public ModelAndView RepairReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_REPORT_CONFIGURATION_CONFIG);
		model.put("configuration", configuration);
		return new ModelAndView("Repair_WorkOrderReport", model);
	}
	


	@RequestMapping(value = "/RepairReport", method = RequestMethod.POST)
	public ModelAndView Vehicle_Wise_Repair_Report(
			@ModelAttribute("command") @RequestParam("repair_vid")  String repair_vid,
			@RequestParam("repair_servicetype") final String repair_servicetype,
			@RequestParam("userId") final Long userId,
			@RequestParam("repair_daterange") final String repair_daterange, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (repair_daterange != "") {
				HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_REPORT_CONFIGURATION_CONFIG);
				model.put("configuration", configuration);
				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = repair_daterange.split(" to ");
					
					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				} catch (Exception e) {
					LOGGER.error("Renewal Reminder:", e);
				}
				
				model.put("SEARCHDATE", repair_daterange);
				if(userId != null && userId > 0) {
					UserProfileDto userDto = userProfileService.getUserProfileByUser_id(userId);
					if(userDto != null) {
						model.put("userName", userDto.getFirstName()+"_"+userDto.getLastName());
					}
				}else {
					model.put("userName", "ALL");
				}

				switch (repair_servicetype) {
				case "WORK_ORDER":
					
					List<WorkOrdersDto> Mult_vehicle_workorder = null;
					if(repair_vid != "" && (userId == null || userId == 0)) {
						
						Mult_vehicle_workorder = WorkOrdersService.vehicle_WiseRepair_Report(dateRangeFrom,
								dateRangeTo, repair_vid, userDetails.getCompany_id());
					}else if(repair_vid == ""){
						Mult_vehicle_workorder = WorkOrdersService.vehicle_WiseRepair_Report(dateRangeFrom,
								dateRangeTo, userId, userDetails.getCompany_id());
					}else if(repair_vid != "" && userId != null && userId > 0 ) {

						Mult_vehicle_workorder = WorkOrdersService.vehicle_WiseRepair_Report(dateRangeFrom,
								dateRangeTo, userId, repair_vid, userDetails.getCompany_id());
					
					}
					
					String workOrder_id = "";
					if (Mult_vehicle_workorder != null && !Mult_vehicle_workorder.isEmpty()) {
						int i = 1;
						repair_vid = "";
						for (WorkOrdersDto workOrders : Mult_vehicle_workorder) {
							
							if (i != Mult_vehicle_workorder.size()) {
								workOrder_id += workOrders.getWorkorders_id() + ",";
									repair_vid += workOrders.getVehicle_vid() + ",";
							} else {
								workOrder_id += workOrders.getWorkorders_id() + "";
									repair_vid += workOrders.getVehicle_vid() + "";
							}
							i++;
						}
					
					model.put("vehicles", vehicleService.vehicle_wise_select_multiple_vehicle(repair_vid, userDetails.getCompany_id()));

					model.put("WorkOrder", WOBL.prepareListWorkOrders(Mult_vehicle_workorder));

					if (workOrder_id != "") {
						try {
							model.put("workordertask", WorkOrdersService.getWorkOrdersTasks_vehicle_WiseRepair_Report(
									workOrder_id, userDetails.getCompany_id()));
							model.put("WorkOrderTaskPart",
									WorkOrdersService.getWorkOrdersTasksToParts_vehicle_WiseRepair_Report(workOrder_id,
											userDetails.getCompany_id()));
						} catch (Exception e) {
							e.printStackTrace();
						}
						// Report Search WorkOrderTask Total amount Multiple
						// Vehicle
						// to workOrders_ID
						List<Double> workO_task_Total = WorkOrdersService
								.WorkOrdersTasks_vehicle_WiseRepair_TotalAmount(workOrder_id, userDetails.getCompany_id());

						for (Double TotalAmount : workO_task_Total) {
							AMOUNTFORMAT.setMaximumFractionDigits(0);
							if (TotalAmount == null) {
								TotalAmount = 0.0;
							}
							String TotalIncomeShow = AMOUNTFORMAT.format(TotalAmount);
							model.put("TotalWOAmount", TotalIncomeShow);
							break;
						}
					}

					model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
					}
					return new ModelAndView("Repair_WorkOrderReport", model);

				case "SERVICE_ENTRIES":

					// System.out.println(repair_vid);
					List<ServiceEntriesDto> Mult_vehicle_ServiceEntries = null;
					
					if(repair_vid != "" && ( userId == null || userId == 0)) {
						Mult_vehicle_ServiceEntries = ServiceEntriesService
								.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, repair_vid, userDetails.getCompany_id());
					}else if(repair_vid == "" ){
						Mult_vehicle_ServiceEntries = ServiceEntriesService
								.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, userId, userDetails.getCompany_id());
					}else if(repair_vid != "" && userId != null && userId > 0) {

						Mult_vehicle_ServiceEntries = ServiceEntriesService
								.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, userId, repair_vid, userDetails.getCompany_id());
					
					}

					String ServiceEntries_id = "";
					if (Mult_vehicle_ServiceEntries != null && !Mult_vehicle_ServiceEntries.isEmpty()) {
						int i = 1;
						repair_vid = "";
						// System.out.println(Mult_vehicle_workorder.size());
						for (ServiceEntriesDto serviceEnt : Mult_vehicle_ServiceEntries) {
							if (i != Mult_vehicle_ServiceEntries.size()) {
								ServiceEntries_id += serviceEnt.getServiceEntries_id() + ",";
								repair_vid += serviceEnt.getVid() + ",";
							} else {
								ServiceEntries_id += serviceEnt.getServiceEntries_id() + "";
								repair_vid += serviceEnt.getVid() + "";
							}
							i++;
						}
					
					model.put("vehicles", vehicleService.vehicle_wise_select_multiple_vehicle(repair_vid, userDetails.getCompany_id()));

					model.put("ServiceEntries", SEBL.prepareListServiceEntries(Mult_vehicle_ServiceEntries));

					if (ServiceEntries_id != "") {
						model.put("ServiceEntriesTask", ServiceEntriesService
								.getServiceEntriesTasks_vehicle_WiseRepair_Report(ServiceEntries_id, userDetails.getCompany_id()));
						model.put("ServiceEntriesTaskPart", ServiceEntriesService
								.getServiceEntriesTasksToParts_vehicle_WiseRepair_Report(ServiceEntries_id, userDetails.getCompany_id()));
						// Report Search WorkOrderTask Total amount Multiple
						// Vehicle
						// to workOrders_ID
						List<Double> Service_task_Total = ServiceEntriesService
								.ServiceEntriesTasks_vehicle_WiseRepair_TotalAmount(ServiceEntries_id, userDetails.getCompany_id());

						for (Double TotalSEAmount : Service_task_Total) {
							AMOUNTFORMAT.setMaximumFractionDigits(0);
							if (TotalSEAmount == null) {
								TotalSEAmount = 0.0;
							}
							String TotalSE_Amount = AMOUNTFORMAT.format(TotalSEAmount);
							model.put("TotalSEAmount", TotalSE_Amount);
							break;
						}
					}

						model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
					}
					return new ModelAndView("Repair_WO_SE_Report", model);

				case "ALL":

					Authentication authALL = SecurityContextHolder.getContext().getAuthentication();
					String nameALL = authALL.getName(); // get logged in
					// username
					Double Total_WOSE_ALL = 0.0;
					String tempVehicle = repair_vid;
					String allVehcle = "";

					model.put("company", userProfileService.findUserProfileByUser_email(nameALL));
				/*	List<WorkOrdersDto> Mult_vehicle_workorderALL = null;
					if(repair_vid != "" && (userId == null || userId == 0)) {
						Mult_vehicle_workorderALL = WorkOrdersService
								.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, repair_vid, userDetails.getCompany_id());

					}else if(repair_vid == ""){
						 Mult_vehicle_workorderALL = WorkOrdersService
								.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, userId, userDetails.getCompany_id());

					}else if(repair_vid != "" && userId != null && userId > 0 ) {

						Mult_vehicle_workorder = WorkOrdersService.vehicle_WiseRepair_Report(dateRangeFrom,
								dateRangeTo, userId, repair_vid, userDetails.getCompany_id());
					
					}
					
					
					String workOrder_idALL = "";
					if (Mult_vehicle_workorderALL != null && !Mult_vehicle_workorderALL.isEmpty()) {
						int i = 1;
						repair_vid = "";
						// System.out.println(Mult_vehicle_workorder.size());
						for (WorkOrdersDto workOrdersALL : Mult_vehicle_workorderALL) {
							if (i != Mult_vehicle_workorderALL.size()) {
								workOrder_idALL += workOrdersALL.getWorkorders_id() + ",";
								repair_vid += workOrdersALL.getVehicle_vid() + ",";
							} else {
								workOrder_idALL += workOrdersALL.getWorkorders_id() + "";
								repair_vid += workOrdersALL.getVehicle_vid() + "";
							}
							i++;
						}
						allVehcle = repair_vid;
					// System.out.println(workOrder_id);

					

					model.put("WorkOrder", WOBL.prepareListWorkOrders(Mult_vehicle_workorderALL));

					

					if (workOrder_idALL != "") {
						model.put("workordertask",
								WorkOrdersService.getWorkOrdersTasks_vehicle_WiseRepair_Report(workOrder_idALL, userDetails.getCompany_id()));
						model.put("WorkOrderTaskPart",
								WorkOrdersService.getWorkOrdersTasksToParts_vehicle_WiseRepair_Report(workOrder_idALL, userDetails.getCompany_id()));
						// Report Search WorkOrderTask Total amount Multiple
						// Vehicle
						// to workOrders_ID
						List<Double> workO_task_TotalALL = WorkOrdersService
								.WorkOrdersTasks_vehicle_WiseRepair_TotalAmount(workOrder_idALL, userDetails.getCompany_id());
						Total_WOSE_ALL = 0.0;

						for (Double TotalAmountALL : workO_task_TotalALL) {
							// count WorkOrder Total
							Total_WOSE_ALL += TotalAmountALL;
							AMOUNTFORMAT.setMaximumFractionDigits(0);
							if (TotalAmountALL == null) {
								TotalAmountALL = 0.0;
							}
							String TotalIncomeShowALL = AMOUNTFORMAT.format(TotalAmountALL);
							model.put("TotalWOAmount", TotalIncomeShowALL);
							break;
						}
					 }
					} */
					List<WorkOrdersDto> Mult_vehicle_workorder2 = null;
					if(repair_vid != "" && (userId == null || userId == 0)) {
						
						Mult_vehicle_workorder2 = WorkOrdersService.vehicle_WiseRepair_Report(dateRangeFrom,
								dateRangeTo, repair_vid, userDetails.getCompany_id());
					}else if(repair_vid == ""){
						Mult_vehicle_workorder2 = WorkOrdersService.vehicle_WiseRepair_Report(dateRangeFrom,
								dateRangeTo, userId, userDetails.getCompany_id());
					}else if(repair_vid != "" && userId != null && userId > 0 ) {

						Mult_vehicle_workorder2 = WorkOrdersService.vehicle_WiseRepair_Report(dateRangeFrom,
								dateRangeTo, userId, repair_vid, userDetails.getCompany_id());
					
					}
					
					String workOrder_id2 = "";
					if (Mult_vehicle_workorder2 != null && !Mult_vehicle_workorder2.isEmpty()) {
						int i = 1;
						repair_vid = "";
						for (WorkOrdersDto workOrders : Mult_vehicle_workorder2) {
							
							if (i != Mult_vehicle_workorder2.size()) {
								workOrder_id2 += workOrders.getWorkorders_id() + ",";
									repair_vid += workOrders.getVehicle_vid() + ",";
							} else {
								workOrder_id2 += workOrders.getWorkorders_id() + "";
									repair_vid += workOrders.getVehicle_vid() + "";
							}
							i++;
						}
					
					model.put("vehicles", vehicleService.vehicle_wise_select_multiple_vehicle(repair_vid, userDetails.getCompany_id()));

					model.put("WorkOrder", WOBL.prepareListWorkOrders(Mult_vehicle_workorder2));

					if (workOrder_id2 != "") {
						try {
							model.put("workordertask", WorkOrdersService.getWorkOrdersTasks_vehicle_WiseRepair_Report(
									workOrder_id2, userDetails.getCompany_id()));
							model.put("WorkOrderTaskPart",
									WorkOrdersService.getWorkOrdersTasksToParts_vehicle_WiseRepair_Report(workOrder_id2,
											userDetails.getCompany_id()));
						} catch (Exception e) {
							e.printStackTrace();
						}
						// Report Search WorkOrderTask Total amount Multiple
						// Vehicle
						// to workOrders_ID
						List<Double> workO_task_Total = WorkOrdersService
								.WorkOrdersTasks_vehicle_WiseRepair_TotalAmount(workOrder_id2, userDetails.getCompany_id());

						for (Double TotalAmount : workO_task_Total) {
							AMOUNTFORMAT.setMaximumFractionDigits(0);
							if (TotalAmount == null) {
								TotalAmount = 0.0;
							}
							String TotalIncomeShow = AMOUNTFORMAT.format(TotalAmount);
							model.put("TotalWOAmount", TotalIncomeShow);
							break;
						}
					}

					model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
					}
					List<ServiceEntriesDto> Mult_vehicle_ServiceEntriesALL = null;
					if(tempVehicle != "" && ( userId == null || userId == 0)) {
						Mult_vehicle_ServiceEntriesALL = ServiceEntriesService
								.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, tempVehicle, userDetails.getCompany_id());
					}else if(tempVehicle == "" ){
						Mult_vehicle_ServiceEntriesALL = ServiceEntriesService
								.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, userId, userDetails.getCompany_id());
					}else if(tempVehicle != "" && userId != null && userId > 0) {

						Mult_vehicle_ServiceEntriesALL = ServiceEntriesService
								.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, userId, tempVehicle, userDetails.getCompany_id());
					
					}
					

					String ServiceEntries_idALL = "";
					if (Mult_vehicle_ServiceEntriesALL != null && !Mult_vehicle_ServiceEntriesALL.isEmpty()) {
						int i = 1;
						repair_vid = "";
						// System.out.println(Mult_vehicle_workorder.size());
						for (ServiceEntriesDto serviceEntALL : Mult_vehicle_ServiceEntriesALL) {
							if (i != Mult_vehicle_ServiceEntriesALL.size()) {
								ServiceEntries_idALL += serviceEntALL.getServiceEntries_id() + ",";
								repair_vid += serviceEntALL.getVid() + ",";
							} else {
								ServiceEntries_idALL += serviceEntALL.getServiceEntries_id() + "";
								repair_vid += serviceEntALL.getVid() + "";
							}
							i++;
						}
						if(repair_vid != "" && !allVehcle.trim().equals(""))
							allVehcle += ","+repair_vid;
						else
							allVehcle = repair_vid;
					model.put("ServiceEntries",SEBL .prepareListServiceEntries(Mult_vehicle_ServiceEntriesALL));

					if (ServiceEntries_idALL != "") {
						model.put("ServiceEntriesTask", ServiceEntriesService
								.getServiceEntriesTasks_vehicle_WiseRepair_Report(ServiceEntries_idALL, userDetails.getCompany_id()));
						model.put("ServiceEntriesTaskPart", ServiceEntriesService
								.getServiceEntriesTasksToParts_vehicle_WiseRepair_Report(ServiceEntries_idALL, userDetails.getCompany_id()));
						// Report Search WorkOrderTask Total amount Multiple
						// Vehicle
						// to workOrders_ID
						List<Double> Service_task_TotalALL = ServiceEntriesService
								.ServiceEntriesTasks_vehicle_WiseRepair_TotalAmount(ServiceEntries_idALL, userDetails.getCompany_id());

						for (Double TotalSEAmountALL : Service_task_TotalALL) {
							// count WorkOrder Total
							Total_WOSE_ALL += TotalSEAmountALL;
							AMOUNTFORMAT.setMaximumFractionDigits(0);
							if (TotalSEAmountALL == null) {
								TotalSEAmountALL = 0.0;
							}
							String TotalSE_AmountALL = AMOUNTFORMAT.format(TotalSEAmountALL);
							model.put("TotalSEAmount", TotalSE_AmountALL);
							break;
						}
					}
					// change format values

					String TotalWOSE_AmountALL = AMOUNTFORMAT.format(Total_WOSE_ALL);
					model.put("TotalWOSEAmount", TotalWOSE_AmountALL);
					model.put("vehicles", vehicleService.vehicle_wise_select_multiple_vehicle(allVehcle, userDetails.getCompany_id()));
					}

					return new ModelAndView("Repair_WO_SE_Report", model);

				}

				/*
				 * model.put("WorkOrder", WOBL.prepareListofWorkOrders(WorkOrdersService.
				 * ReportWorkOrders(WorkOrdersReport)));
				 */
			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Repair_WorkOrderReport", model);
	}



	@RequestMapping(value = "/VehicleWisePartConsumptionAndUsageReport", method = RequestMethod.POST)
	public ModelAndView VehicleWisePartConsumptionAndUsageReport(
			@ModelAttribute("command") @RequestParam("repair_vid") final String repair_vid,
			@RequestParam("repair_servicetype") final String repair_servicetype,
			@RequestParam("locationId") final String locationId,
			@RequestParam("repair_daterange") final String repair_daterange, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (repair_daterange != "" && repair_vid != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = repair_daterange.split(" to ");
					
					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("Renewal Reminder:", e);
				}

				model.put("SEARCHDATE", repair_daterange);
				List<WorkOrdersDto> Mult_vehicle_workorder =	null;

				switch (repair_servicetype) {
				case "1":

					if (locationId != "")
					{		
					
					Mult_vehicle_workorder = WorkOrdersService.vehicle_WiseRepair_Report_location(dateRangeFrom,
							dateRangeTo, repair_vid, locationId, userDetails.getCompany_id());
					}else
					{
						Mult_vehicle_workorder = WorkOrdersService.vehicle_WiseRepair_Report(dateRangeFrom,
								dateRangeTo, repair_vid, userDetails.getCompany_id());
					}	

					String workOrder_id = "" ;
					
					if (Mult_vehicle_workorder != null && !Mult_vehicle_workorder.isEmpty()) {
						int i = 1;
						for (WorkOrdersDto workOrders : Mult_vehicle_workorder) {
							if (i != Mult_vehicle_workorder.size()) {
								workOrder_id += workOrders.getWorkorders_id() + ",";
							} else {
								workOrder_id += workOrders.getWorkorders_id() + "";
							}
							i++;
						}
					}
					
					
					
					if((( vehicleService.vehicle_wise_select_multiple_vehicle(repair_vid, userDetails.getCompany_id())) == null)) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("VehicleWisePartConsumptionAndUsageReport", model);
					}
					
					
					
					model.put("vehicles", vehicleService.vehicle_wise_select_multiple_vehicle(repair_vid, userDetails.getCompany_id()));

					model.put("WorkOrder", WOBL.prepareListWorkOrders(Mult_vehicle_workorder));

					if (workOrder_id != "") {
						try {
							model.put("workordertask", WorkOrdersService.getWorkOrdersTasks_vehicle_WiseRepair_Report(
									workOrder_id, userDetails.getCompany_id()));

							model.put("WorkOrderTaskPart",
									WorkOrdersService.getWorkOrdersTasksToParts_vehicle_WiseRepair_Report(workOrder_id,
											userDetails.getCompany_id()));
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						List<Double> workO_task_Total = WorkOrdersService
								.WorkOrdersTasks_vehicle_WiseRepair_TotalAmount(workOrder_id, userDetails.getCompany_id());
								for (Double TotalAmount : workO_task_Total) {
								AMOUNTFORMAT.setMaximumFractionDigits(0);
								if (TotalAmount == null) {
								TotalAmount = 0.0;
								}
								String TotalIncomeShow = AMOUNTFORMAT.format(TotalAmount);
								model.put("TotalWOAmount", TotalIncomeShow);
								break;
								}
						
					}
				
					

					model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

					return new ModelAndView("VehicleWisePartConsumptionAndUsageReport", model);

				case "2":

					// System.out.println(repair_vid);

					List<ServiceEntriesDto> Mult_vehicle_ServiceEntries = ServiceEntriesService
					.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, repair_vid, userDetails.getCompany_id());

					String ServiceEntries_id = "";
					if (Mult_vehicle_ServiceEntries != null && !Mult_vehicle_ServiceEntries.isEmpty()) {
						int i = 1;
						// System.out.println(Mult_vehicle_workorder.size());
						for (ServiceEntriesDto serviceEnt : Mult_vehicle_ServiceEntries) {
							if (i != Mult_vehicle_ServiceEntries.size()) {
								ServiceEntries_id += serviceEnt.getServiceEntries_id() + ",";
							} else {
								ServiceEntries_id += serviceEnt.getServiceEntries_id() + "";
							}
							i++;
						}
					}
					// System.out.println(workOrder_id);

					model.put("vehicles", vehicleService.vehicle_wise_select_multiple_vehicle(repair_vid, userDetails.getCompany_id()));

					model.put("ServiceEntries", SEBL.prepareListServiceEntries(Mult_vehicle_ServiceEntries));

					if (ServiceEntries_id != "") {
						model.put("ServiceEntriesTask", ServiceEntriesService
								.getServiceEntriesTasks_vehicle_WiseRepair_Report(ServiceEntries_id, userDetails.getCompany_id()));
						model.put("ServiceEntriesTaskPart", ServiceEntriesService
								.getServiceEntriesTasksToParts_vehicle_WiseRepair_Report(ServiceEntries_id, userDetails.getCompany_id()));
						// Report Search WorkOrderTask Total amount Multiple
						// Vehicle
						// to workOrders_ID
						List<Double> Service_task_Total = ServiceEntriesService
								.ServiceEntriesTasks_vehicle_WiseRepair_TotalAmount(ServiceEntries_id, userDetails.getCompany_id());
								for (Double TotalSEAmount : Service_task_Total) {
								AMOUNTFORMAT.setMaximumFractionDigits(0);
								if (TotalSEAmount == null) {
								TotalSEAmount = 0.0;
								}
								String TotalSE_Amount = AMOUNTFORMAT.format(TotalSEAmount);
								model.put("TotalSEAmount", TotalSE_Amount);
								break;
								}
					}
					model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

					return new ModelAndView("ServiceEntryVehicleWisePartConsumptionReport", model);


				}

				/*
				 * model.put("WorkOrder", WOBL.prepareListofWorkOrders(WorkOrdersService.
				 * ReportWorkOrders(WorkOrdersReport)));
				 */
			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("VehicleWisePartConsumptionAndUsageReport", model);
	}


	@RequestMapping("/ALLVehicleRepairReport")
	public ModelAndView ALLVehicleRepairReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Repair_ServiceEntriesReport", model);
	}

/* Here Real code Vehicle Wise Repair Report */

	@RequestMapping(value = "/ALLVehicleRepairReport", method = RequestMethod.POST)
	public ModelAndView ALLVehicleRepairReport(
			@ModelAttribute("command") @RequestParam("VEHICLE_GROUP_ID") final long VEHICLE_GROUP_ID,
			@RequestParam("VEHICLE_DATERANGE") final String repair_daterange, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (repair_daterange != "" && VEHICLE_GROUP_ID != 0) {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = repair_daterange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			} catch (Exception e) {
				LOGGER.error("Renewal Reminder:", e);
			}

			model.put("SEARCHDATE", repair_daterange);

			// System.out.println(repair_vid);
			String repair_vid = "";
			if (VEHICLE_GROUP_ID != 0) {
				repair_vid = " SELECT V.vid FROM Vehicle AS V where V.vehicleGroupId=" + VEHICLE_GROUP_ID + " AND V.company_Id = "+userDetails.getCompany_id()+" AND V.markForDelete = 0";
			}

			List<ServiceEntriesDto> Mult_vehicle_ServiceEntries = ServiceEntriesService
					.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, repair_vid, userDetails.getCompany_id());

			String ServiceEntries_id = "";
			if (Mult_vehicle_ServiceEntries != null && !Mult_vehicle_ServiceEntries.isEmpty()) {
				int i = 1;
				// System.out.println(Mult_vehicle_workorder.size());
				for (ServiceEntriesDto serviceEnt : Mult_vehicle_ServiceEntries) {
					if (i != Mult_vehicle_ServiceEntries.size()) {
						ServiceEntries_id += serviceEnt.getServiceEntries_id() + ",";
					} else {
						ServiceEntries_id += serviceEnt.getServiceEntries_id() + "";
					}
					i++;
				}
			} else {
				model.put("NotFound", true);
			}
			// System.out.println(workOrder_id);

			// model.put("vehicles",
			// vehicleService.vehicle_wise_select_multiple_vehicle(repair_vid));

			model.put("ServiceEntries", SEBL.prepareListServiceEntries(Mult_vehicle_ServiceEntries));

			if (ServiceEntries_id != "") {
				model.put("ServiceEntriesTask",
						ServiceEntriesService.getServiceEntriesTasks_vehicle_WiseRepair_Report(ServiceEntries_id, userDetails.getCompany_id()));
				model.put("ServiceEntriesTaskPart", ServiceEntriesService
						.getServiceEntriesTasksToParts_vehicle_WiseRepair_Report(ServiceEntries_id, userDetails.getCompany_id()));
				// Report Search WorkOrderTask Total amount Multiple
				// Vehicle
				// to workOrders_ID
				List<Double> Service_task_Total = ServiceEntriesService
						.ServiceEntriesTasks_vehicle_WiseRepair_TotalAmount(ServiceEntries_id, userDetails.getCompany_id());

				for (Double TotalSEAmount : Service_task_Total) {
					AMOUNTFORMAT.setMaximumFractionDigits(0);
					if (TotalSEAmount == null) {
						TotalSEAmount = 0.0;
					}
					String TotalSE_Amount = AMOUNTFORMAT.format(TotalSEAmount);
					model.put("TotalSEAmount", TotalSE_Amount);
					break;
				}
			} 

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			return new ModelAndView("Vehicle_AllServiceEntries_Report", model);

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Repair_ServiceEntriesReport", model);
	}

	@RequestMapping("/VehicleReport")
	public ModelAndView VehicleReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails=Utility.getObject(null);
		HashMap<String, Object> configuration = null;
		try {
			configuration=companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_REPORT_CONFIGURATION_CONFIG);
			model.put("configuration", configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ReportVehicle", model);
	}

	/* Vehicle Report */
	@RequestMapping(value = "/vehicleReport", method = RequestMethod.POST)
	public ModelAndView addVehicle(@ModelAttribute("command") Vehicle vehicleDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		// Vehicle vehicle = RBL.prepareReportVehicle(vehicleDto);
		CustomUserDetails userDetails=Utility.getObject(null);
		try {
		
			HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
					userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			HashMap<String, Object>  confi=companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_REPORT_CONFIGURATION_CONFIG);

			String Vehicle_Status = "", Vehicle_Group = "", Vehicle_Type = "", Vehicle_Location = "",
					Vehicle_Ownership = "", Vehicle_Fuel = "", Vehicle_year = "", Vehicle_maker = "",
					Vehicle_Model = "", Vehicle_SeatCapacity = "", Vehicle_actype = "", vehicleMakerId="",vehicleBodyMakerId="";

			if (vehicleDto.getvStatusId() != 0) {
				Vehicle_Status = "AND R.vStatusId=" + vehicleDto.getvStatusId() + "";
			}

			if (vehicleDto.getVehicleGroupId() != 0) {
				Vehicle_Group = "AND R.vehicleGroupId=" + vehicleDto.getVehicleGroupId() + "";
			}
			if (vehicleDto.getVehicleTypeId() > 0) {
				Vehicle_Type = "AND R.vehicleTypeId=" + vehicleDto.getVehicleTypeId() + "";
			}
			if (vehicleDto.getVehicle_Location() != "") {
				Vehicle_Location = "AND R.Vehicle_Location='" + vehicleDto.getVehicle_Location() + "'";
			}
			if (vehicleDto.getVehicleOwnerShipId() > 0) {
				Vehicle_Ownership = "AND R.vehicleOwnerShipId=" + vehicleDto.getVehicleOwnerShipId() + "";
			}

			if (vehicleDto.getVehicleFuelId() != null && !vehicleDto.getVehicleFuelId().trim().equals("")) {
				Vehicle_Fuel = "AND R.vehicleFuelId='" + vehicleDto.getVehicleFuelId() + "'";
			}
			if (vehicleDto.getVehicle_year() != null) {
				Vehicle_year = "AND R.vehicle_year='" + vehicleDto.getVehicle_year() + "'";
			}

			if (vehicleDto.getVehicle_maker() != null) {
				Vehicle_maker = "AND R.vehicle_maker='" + vehicleDto.getVehicle_maker() + "'";
			}

			if (vehicleDto.getVehicle_Model() != null) {
				Vehicle_Model = "AND R.vehicle_Model='" + vehicleDto.getVehicle_Model() + "'";
			}

			if (vehicleDto.getVehicle_SeatCapacity() != null && !vehicleDto.getVehicle_SeatCapacity().trim().equals("")) {
				Vehicle_SeatCapacity = "AND R.vehicle_SeatCapacity='" + vehicleDto.getVehicle_SeatCapacity() + "'";
			}

			if (vehicleDto.getAcTypeId() > 0) {
				Vehicle_actype = "AND R.acTypeId=" + vehicleDto.getAcTypeId() + "";
            }
			
			if(vehicleDto.getVehicleMakerId() != null && vehicleDto.getVehicleMakerId() > 0) {
				vehicleMakerId =" AND R.vehicleMakerId="+vehicleDto.getVehicleMakerId();
			}

			if(vehicleDto.getVehicleBodyMakerId() != null && vehicleDto.getVehicleBodyMakerId() > 0) {
				vehicleMakerId =" AND R.vehicleBodyMakerId="+vehicleDto.getVehicleBodyMakerId();
			}

			String query = " " + Vehicle_Status + " " + Vehicle_Group + " " + Vehicle_Type + " " + Vehicle_Location
					+ " " + Vehicle_Ownership + " " + Vehicle_Fuel + " " + Vehicle_year + " " + Vehicle_maker + " "
					+ Vehicle_Model + " " + Vehicle_SeatCapacity + " " + Vehicle_actype + " "  +vehicleMakerId +" ";

			model.put("vehicles", VBL.prepareList_OF_Vehicle_ReCent(vehicleService.listVehicleReport(query)));
			model.put("configuration",configuration);
			model.put("config",confi);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return new ModelAndView("Vehicle_Report", model);
	}

	/* Here code Vehicle Wise Repair Report */
	@RequestMapping(value = "/VehicleCurrentRouteReport")
	public ModelAndView VehicleCurrentRouteRport(final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			String query = " AND  T.tripSheetID IN (SELECT V.tripSheetID FROM Vehicle AS V "
					+ " WHERE vStatusId="+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+" AND company_Id = "+userDetails.getCompany_id()+" AND markForDelete = 0) ";

			model.put("TripSheet", TripBL.prepareListofTripSheet(TripSheetService.listTripSheet(query)));

		} catch (Exception e) {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

		return new ModelAndView("Vehicle_CurrentRoute_Report", model);
	}


	@RequestMapping(value = "/VehicleTripSheetHistoryReport", method = RequestMethod.POST)
	public ModelAndView Vehicle_TripSheet_History_Report(
			@ModelAttribute("command") @RequestParam("VEHICLE_ID") final Integer VEHICLE_ID,
			@RequestParam("TRIP_DATERANGE") final String TRIP_DATERANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			if (VEHICLE_ID != null && TRIP_DATERANGE != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("Renewal Reminder:", e);
				}

				Authentication authALL = SecurityContextHolder.getContext().getAuthentication();
				String nameALL = authALL.getName();
				// get logged in user name

				model.put("company", userProfileService.findUserProfileByUser_email(nameALL));

				String vehicleId = "", DateRange = "";
				if (VEHICLE_ID != null && VEHICLE_ID != 0) {
					vehicleId = "AND T.vid =" + VEHICLE_ID + "";
				}

				if (dateRangeFrom != "" && dateRangeTo != "") {
					DateRange = "AND T.tripOpenDate BETWEEN '" + dateRangeFrom + "' AND  '" + dateRangeTo + "' ";
				}

				String query = " " + vehicleId + " " + DateRange + " ";
				model.put("TripSheet", TripBL.prepareListofTripSheet(TripSheetService.listTripSheet(query)));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Vehicle_CurrentRoute_Report", model);
	}

	@RequestMapping("/PartsConsumedReport")
	public ModelAndView PartsConsumedReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("part_workorder", model);
	}

	@RequestMapping(value = "/PartsConsumedReport", method = RequestMethod.POST)
	public ModelAndView Depot_wise_Parts_consumed_report(
			@ModelAttribute("command") @RequestParam("WORKORDER_GROUP") final long WORKORDER_GROUP,
			@RequestParam("repair_servicetype") final short repair_servicetype,
			@RequestParam("locationId") final String locationId,
			@RequestParam("repair_daterange") final String repair_daterange, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (repair_daterange != "" && WORKORDER_GROUP != 0) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;
				try {
					From_TO_DateRange = repair_daterange.split(" to ");
					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				} catch (Exception e) {
					LOGGER.error("Renewal Reminder:", e);
				}
				model.put("SEARCHDATE", repair_daterange);
				String repair_vid = "";
				if (WORKORDER_GROUP != 0) {
					repair_vid = " SELECT V.vid FROM Vehicle AS V where V.vehicleGroupId=" + WORKORDER_GROUP + " AND V.company_Id = "+userDetails.getCompany_id()+" AND V.markForDelete = 0";
				}
				List<WorkOrdersDto> Mult_vehicle_workorder = null;
				switch (repair_servicetype) {
				
				case WorkOrdersDto.WORK_ORDER:
					if(locationId!="")
					{
					 Mult_vehicle_workorder = WorkOrdersService.depot_wise_Report(dateRangeFrom,
							 dateRangeTo, repair_vid, locationId, userDetails.getCompany_id());
					}
					else
					{
						Mult_vehicle_workorder = WorkOrdersService.vehicle_WiseRepair_Report(dateRangeFrom,
								 dateRangeTo, repair_vid, userDetails.getCompany_id());
					}
					String workOrder_id = "";
					if (Mult_vehicle_workorder != null && !Mult_vehicle_workorder.isEmpty()) {
						int i = 1;
						for (WorkOrdersDto workOrders : Mult_vehicle_workorder) {
							if (i != Mult_vehicle_workorder.size()) {
								workOrder_id += workOrders.getWorkorders_id() + ",";
							} else {
								workOrder_id += workOrders.getWorkorders_id() + "";
							}
							i++;
						}
					}
					
					if(((vehicleService.vehicle_wise_select_multiple_vehicle(repair_vid, userDetails.getCompany_id())) == null)) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("part_workorder", model);
					}
					
					
					
					model.put("vehicles", vehicleService.vehicle_wise_select_multiple_vehicle(repair_vid, userDetails.getCompany_id()));
					model.put("WorkOrder", WOBL.prepareListWorkOrders(Mult_vehicle_workorder));
					if (workOrder_id != "") {
						try {
							model.put("workordertask", WorkOrdersService.getWorkOrdersTasks_vehicle_WiseRepair_Report(
									workOrder_id, userDetails.getCompany_id()));
							model.put("WorkOrderTaskPart",
									WorkOrdersService.getWorkOrdersTasksToParts_vehicle_WiseRepair_Report(workOrder_id,
											userDetails.getCompany_id()));
						} catch (Exception e) {
							e.printStackTrace();
						}
						List<Double> workO_task_Total = WorkOrdersService
								.WorkOrdersTasks_vehicle_WiseRepair_TotalAmount(workOrder_id, userDetails.getCompany_id());
						for (Double TotalAmount : workO_task_Total) {
							AMOUNTFORMAT.setMaximumFractionDigits(0);
							if (TotalAmount == null) {
								TotalAmount = 0.0;
							}
							String TotalIncomeShow = AMOUNTFORMAT.format(TotalAmount);
							model.put("TotalWOAmount", TotalIncomeShow);
							break;
						}
					}
			
				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
					return new ModelAndView("part_workorder", model);

				case WorkOrdersDto.SERVICE_ENTRIES:
					
					String repair_vid2 = "";
					if (WORKORDER_GROUP != 0) {
						repair_vid2 = " SELECT V.vid FROM Vehicle AS V where V.vehicleGroupId=" + WORKORDER_GROUP + " AND V.company_Id = "+userDetails.getCompany_id()+" AND V.markForDelete = 0";
					}
					List<ServiceEntriesDto> Mult_vehicle_ServiceEntries = ServiceEntriesService
							.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, repair_vid2, userDetails.getCompany_id());
					String ServiceEntries_id = "";
					if (Mult_vehicle_ServiceEntries != null && !Mult_vehicle_ServiceEntries.isEmpty()) {
						int i = 1;
						for (ServiceEntriesDto serviceEnt : Mult_vehicle_ServiceEntries) {
							if (i != Mult_vehicle_ServiceEntries.size()) {
								ServiceEntries_id += serviceEnt.getServiceEntries_id() + ",";
							} else {
								ServiceEntries_id += serviceEnt.getServiceEntries_id() + "";
							}
							i++;
						}
					}
					model.put("ServiceEntries", SEBL.prepareListServiceEntries(Mult_vehicle_ServiceEntries));
					if (ServiceEntries_id != "") {
						model.put("ServiceEntriesTask", ServiceEntriesService
								.getServiceEntriesTasks_vehicle_WiseRepair_Report(ServiceEntries_id, userDetails.getCompany_id()));
						model.put("ServiceEntriesTaskPart", ServiceEntriesService
								.getServiceEntriesTasksToParts_vehicle_WiseRepair_Report(ServiceEntries_id, userDetails.getCompany_id()));

						List<Double> Service_task_Total = ServiceEntriesService
								.ServiceEntriesTasks_vehicle_WiseRepair_TotalAmount(ServiceEntries_id, userDetails.getCompany_id());
						for (Double TotalSEAmount : Service_task_Total) {
							AMOUNTFORMAT.setMaximumFractionDigits(0);
							if (TotalSEAmount == null) {
								TotalSEAmount = 0.0;
							}
							String TotalSE_Amount = AMOUNTFORMAT.format(TotalSEAmount);
							model.put("TotalSEAmount", TotalSE_Amount);
							break;
						}
					}
					model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
					return new ModelAndView("part_consumedservice", model);
				case WorkOrdersDto.ALL:
					Authentication authALL = SecurityContextHolder.getContext().getAuthentication();
					String nameALL = authALL.getName(); // get logged in
					// username

					model.put("company", userProfileService.findUserProfileByUser_email(nameALL));

					List<WorkOrdersDto> Mult_vehicle_workorderALL = WorkOrdersService
							.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, repair_vid, userDetails.getCompany_id());

					String workOrder_idALL = "";
					if (Mult_vehicle_workorderALL != null && !Mult_vehicle_workorderALL.isEmpty()) {
						int i = 1;

						for (WorkOrdersDto workOrdersALL : Mult_vehicle_workorderALL) {
							if (i != Mult_vehicle_workorderALL.size()) {
								workOrder_idALL += workOrdersALL.getWorkorders_id() + ",";
							} else {
								workOrder_idALL += workOrdersALL.getWorkorders_id() + "";
							}
							i++;
						}
					}


					model.put("vehicles", vehicleService.vehicle_wise_select_multiple_vehicle(repair_vid, userDetails.getCompany_id()));

					model.put("WorkOrder", WOBL.prepareListWorkOrders(Mult_vehicle_workorderALL));

					Double Total_WOSE_ALL = 0.0;

					if (workOrder_idALL != "") {
						model.put("workordertask",
								WorkOrdersService.getWorkOrdersTasks_vehicle_WiseRepair_Report(workOrder_idALL, userDetails.getCompany_id()));
						model.put("WorkOrderTaskPart",
								WorkOrdersService.getWorkOrdersTasksToParts_vehicle_WiseRepair_Report(workOrder_idALL, userDetails.getCompany_id()));
						// Report Search WorkOrderTask Total amount Multiple
						// Vehicle
						// to workOrders_ID
						List<Double> workO_task_TotalALL = WorkOrdersService
								.WorkOrdersTasks_vehicle_WiseRepair_TotalAmount(workOrder_idALL, userDetails.getCompany_id());
						Total_WOSE_ALL = 0.0;

						for (Double TotalAmountALL : workO_task_TotalALL) {
							// count WorkOrder Total
							Total_WOSE_ALL += TotalAmountALL;
							AMOUNTFORMAT.setMaximumFractionDigits(0);
							if (TotalAmountALL == null) {
								TotalAmountALL = 0.0;
							}
							String TotalIncomeShowALL = AMOUNTFORMAT.format(TotalAmountALL);
							model.put("TotalWOAmount", TotalIncomeShowALL);
							break;
						}
					}
					List<ServiceEntriesDto> Mult_vehicle_ServiceEntriesALL = ServiceEntriesService
							.vehicle_WiseRepair_Report(dateRangeFrom, dateRangeTo, repair_vid, userDetails.getCompany_id());

					String ServiceEntries_idALL = "";
					if (Mult_vehicle_ServiceEntriesALL != null && !Mult_vehicle_ServiceEntriesALL.isEmpty()) {
						int i = 1;
						// System.out.println(Mult_vehicle_workorder.size());
						for (ServiceEntriesDto serviceEntALL : Mult_vehicle_ServiceEntriesALL) {
							if (i != Mult_vehicle_ServiceEntriesALL.size()) {
								ServiceEntries_idALL += serviceEntALL.getServiceEntries_id() + ",";
							} else {
								ServiceEntries_idALL += serviceEntALL.getServiceEntries_id() + "";
							}
							i++;
						}
					}
					// System.out.println(workOrder_id);

					model.put("ServiceEntries", SEBL.prepareListServiceEntries(Mult_vehicle_ServiceEntriesALL));

					if (ServiceEntries_idALL != "") {
						model.put("ServiceEntriesTask", ServiceEntriesService
								.getServiceEntriesTasks_vehicle_WiseRepair_Report(ServiceEntries_idALL, userDetails.getCompany_id()));
						model.put("ServiceEntriesTaskPart", ServiceEntriesService
								.getServiceEntriesTasksToParts_vehicle_WiseRepair_Report(ServiceEntries_idALL, userDetails.getCompany_id()));
						// Report Search WorkOrderTask Total amount Multiple
						// Vehicle
						// to workOrders_ID
						List<Double> Service_task_TotalALL = ServiceEntriesService
								.ServiceEntriesTasks_vehicle_WiseRepair_TotalAmount(ServiceEntries_idALL, userDetails.getCompany_id());

						for (Double TotalSEAmountALL : Service_task_TotalALL) {
							// count WorkOrder Total
							Total_WOSE_ALL += TotalSEAmountALL;
							AMOUNTFORMAT.setMaximumFractionDigits(0);
							if (TotalSEAmountALL == null) {
								TotalSEAmountALL = 0.0;
							}
							String TotalSE_AmountALL = AMOUNTFORMAT.format(TotalSEAmountALL);
							model.put("TotalSEAmount", TotalSE_AmountALL);
							break;
						}
					}
					String TotalWOSE_AmountALL = AMOUNTFORMAT.format(Total_WOSE_ALL);
					model.put("TotalWOSEAmount", TotalWOSE_AmountALL);

					return new ModelAndView("Part_WO_SE_Report", model);


				}

				/*
				 * model.put("WorkOrder", WOBL.prepareListofWorkOrders(WorkOrdersService.
				 * ReportWorkOrders(WorkOrdersReport)));
				 */
			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("part_workorder", model);
	}

}

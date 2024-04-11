package org.fleetopgroup.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.WorkOrdersBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WorkOrderReportController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IWorkOrdersService WorkOrdersService;
	
	@Autowired
	private IUserProfileService userProfileService;
	
	@Autowired
	private IPartLocationsService PartLocationsService;
	PartLocationsBL PLBL = new PartLocationsBL();
	@Autowired private ICompanyConfigurationService companyConfigurationService;

	
	RenewalReminderBL RRBL = new RenewalReminderBL();
	
	WorkOrdersBL WOBL = new WorkOrdersBL();


	@RequestMapping("/WOR")
	public ModelAndView WorkOrderReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("WOR", model);
	}
	
	@RequestMapping("/LocationWorkOrderReport")
	public ModelAndView LocationWorkOrderReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Location_Wise_WorkOrder_Report", model);
	}
	
	/*Vehicle Code Start */
	
	/*Vehicle Code End */
	
@RequestMapping(value = "/LocationWorkOrderReport", method = RequestMethod.POST)
public ModelAndView LocationWorkOrderReport(
		@ModelAttribute("command") @RequestParam("WORKORDER_LOCATION") final Integer WORKORDER_LOCATION,
		@RequestParam("WORKORDER_DATERANGE") final String WORKORDER_DATERANGE, final HttpServletRequest request) {

	Map<String, Object> model = new HashMap<String, Object>();
	HashMap<String, Object> 		configuration	          = null;
	List<WorkOrdersDto> locationWiseWorkOrderReportList=null; //No Record Found Validation  Logic Y
	CustomUserDetails		userDetails		= null;
	try {
		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
		if (WORKORDER_DATERANGE != "" && WORKORDER_LOCATION != null  && WORKORDER_LOCATION > 0) {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = WORKORDER_DATERANGE.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				String Warehouselocation = "";

				if (WORKORDER_LOCATION != null  && WORKORDER_LOCATION > 0) {
					Warehouselocation = "AND WO.workorders_location_ID =" + WORKORDER_LOCATION + "";
				}

				String WorkOrderQuery = " " + Warehouselocation + " ";

				List<WorkOrdersDto> WorkOrder = WorkOrdersService.ReportWorkOrders(WorkOrderQuery, dateRangeFrom,
						dateRangeTo, userDetails);
				
				locationWiseWorkOrderReportList= WOBL.prepareListWorkOrders(WorkOrder);
				//No Record Found Validation  Logic Start Y
				if(locationWiseWorkOrderReportList == null) 
				{
					model.put("NotFound", true); 
					return new ModelAndView("Location_Wise_WorkOrder_Report", model);
				}
				//No Record Found Validation  Logic End  Y			
				
				model.put("WorkOrder",locationWiseWorkOrderReportList );
				//model.put("WorkOrder", WOBL.prepareListWorkOrders(WorkOrder)); //Original Code Before No Record Found

				// RenewalReminder Total Amount Sum Query
				model.put("TotalAmount", RRBL.Total_WorkOrder_Amount(WorkOrder));

			} catch (Exception e) {
				e.printStackTrace();
			}

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
			model.put("configuration",configuration);

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

	} catch (Exception e) {

		e.printStackTrace();
	}

	return new ModelAndView("Location_Wise_WorkOrder_Report", model);
}

@RequestMapping("/VehicleWorkOrderReport")
public ModelAndView VehicleWorkOrderReport() {
	Map<String, Object> model = new HashMap<String, Object>();

	return new ModelAndView("Vehicle_Wise_WorkOrder_Report", model);
}


@RequestMapping(value = "/VehicleWorkOrderReport", method = RequestMethod.POST)
public ModelAndView VehicleWorkOrderReport(
		@ModelAttribute("command") @RequestParam("VEHICLE_ID") final String VEHICLE_ID,
		@RequestParam("WORKORDER_DATERANGE") final String WORKORDER_DATERANGE, final HttpServletRequest request) {

	Map<String, Object> model = new HashMap<String, Object>();
	List<WorkOrdersDto> vehicleWiseWorkOrderReportList=null; //No Record Found Validation  Logic Y
	try {

		if (WORKORDER_DATERANGE != "" && VEHICLE_ID != "") {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			CustomUserDetails		userDetails		= null;
			try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				From_TO_DateRange = WORKORDER_DATERANGE.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				String vehicle_id = "";

				if (VEHICLE_ID != "") {
					vehicle_id = "AND WO.vehicle_vid =" + VEHICLE_ID + " ";
				}

				String WorkOrderQuery = " " + vehicle_id + " ";

				List<WorkOrdersDto> WorkOrder = WorkOrdersService.ReportWorkOrders(WorkOrderQuery, dateRangeFrom,
						dateRangeTo, userDetails);
				
				vehicleWiseWorkOrderReportList= WOBL.prepareListWorkOrders(WorkOrder);
				//No Record Found Validation  Logic Start Y
				if(vehicleWiseWorkOrderReportList == null) 
				{
					model.put("NotFound", true); 
					return new ModelAndView("Vehicle_Wise_WorkOrder_Report", model);
				}
				//No Record Found Validation  Logic End  Y
				
				model.put("WorkOrder",vehicleWiseWorkOrderReportList );
				
				//model.put("WorkOrder", WOBL.prepareListWorkOrders(WorkOrder)); //Original Code Before No Record Found

				// RenewalReminder Total Amount Sum Query
				model.put("TotalAmount", RRBL.Total_WorkOrder_Amount(WorkOrder));

			} catch (Exception e) {
				e.printStackTrace();
			}


			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

	} catch (Exception e) {

		e.printStackTrace();
	}

	return new ModelAndView("Vehicle_Wise_WorkOrder_Report", model);
}

@RequestMapping("/GroupWorkOrderReport")
public ModelAndView GroupWorkOrderReport() {
	Map<String, Object> model = new HashMap<String, Object>();

	return new ModelAndView("Group_Wise_WorkOrder_Report", model);
}




@RequestMapping(value = "/GroupWorkOrderReport", method = RequestMethod.POST)
public ModelAndView GroupWorkOrderReport(
		@ModelAttribute("command") @RequestParam("WORKORDER_GROUP") final long WORKORDER_GROUP,
		@RequestParam("WORKORDER_DATERANGE") final String WORKORDER_DATERANGE, final HttpServletRequest request) {

	Map<String, Object> model = new HashMap<String, Object>();
	List<WorkOrdersDto> groupWiseWorkOrderReportList=null; //No Record Found Validation  Logic Y
	CustomUserDetails		userDetails		= null;
	try {
		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (WORKORDER_DATERANGE != "" && WORKORDER_GROUP != 0) {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = WORKORDER_DATERANGE.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				String vehicle_id = "";

				if (WORKORDER_GROUP != 0) {
					vehicle_id = "AND WO.vehicle_vid IN (SELECT w.vid FROM Vehicle AS w where w.vehicleGroupId="
							+ WORKORDER_GROUP + " AND w.company_Id = "+userDetails.getCompany_id()+" AND w.markForDelete = 0) ";
				}

				String WorkOrderQuery = " " + vehicle_id + " ";

				List<WorkOrdersDto> WorkOrder = WorkOrdersService.ReportWorkOrders(WorkOrderQuery, dateRangeFrom,
						dateRangeTo, userDetails);
				
				groupWiseWorkOrderReportList= WOBL.prepareListWorkOrders(WorkOrder);
				//No Record Found Validation  Logic Start Y
				if(groupWiseWorkOrderReportList == null) 
				{
					model.put("NotFound", true); 
					return new ModelAndView("Group_Wise_WorkOrder_Report", model);
				}
				//No Record Found Validation  Logic End  Y
				
				
				model.put("WorkOrder", groupWiseWorkOrderReportList);
				//model.put("WorkOrder", WOBL.prepareListWorkOrders(WorkOrder)); //Original Code Before No Record Found

				// RenewalReminder Total Amount Sum Query
				model.put("TotalAmount", RRBL.Total_WorkOrder_Amount(WorkOrder));

			} catch (Exception e) {
				e.printStackTrace();
			}


			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

	} catch (Exception e) {

		e.printStackTrace();
	}

	return new ModelAndView("Group_Wise_WorkOrder_Report", model);
}

/*Start Source code added  by dev y fleetop for work order part consuming report*/
@RequestMapping("/WorkOrderPartConsumingReport")
public ModelAndView WorkOrderPartConsumingReport() {
	Map<String, Object> model = new HashMap<String, Object>();

	return new ModelAndView("WorkOrder_Part_ConsumingReport", model);
}
/*Start Source code added  by dev y fleetop for work order part consuming report*/


@RequestMapping(value = "/WorkOrderPartConsumingReport", method = RequestMethod.POST)
public ModelAndView WorkOrderPartConsumingReport(@ModelAttribute("command") WorkOrdersDto WORK_LOCATION,
		@RequestParam("WORKORDER_DATERANGE") final String WORKORDER_DATERANGE, final HttpServletRequest request) {

	Map<String, Object> model = new HashMap<String, Object>();
	List<WorkOrdersTasksToPartsDto> workOrderPartConsumingReportList=null; //No Record Found Validation  Logic Y
	CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	try {

		if (WORKORDER_DATERANGE != "") {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = WORKORDER_DATERANGE.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				String location = "";
				if (WORK_LOCATION.getWorkorders_location_ID() != null
						&& WORK_LOCATION.getWorkorders_location_ID() > 0) {
					location = "AND p.workorders_location_ID =" + WORK_LOCATION.getWorkorders_location_ID() + " ";
				}

				String WorkOrderQuery = " " + location + " AND p.companyId = "+userDetails.getCompany_id()+" AND p.markForDelete = 0 ";

				List<WorkOrdersTasksToPartsDto> WorkOrder = WorkOrdersService
						.WorkOrders_Part_Consuming_WorkOrdersTasksToParts(WorkOrderQuery, dateRangeFrom,
								dateRangeTo);
				
				workOrderPartConsumingReportList= WOBL.prepare_Part_Consuming_WorkOrdersTasksToParts(WorkOrder);   //No Record Found Validation  Logic Y
				//No Record Found Validation  Logic Start Y
				if(workOrderPartConsumingReportList == null) 
				{
					model.put("NotFound", true); 
					return new ModelAndView("WorkOrder_Part_ConsumingReport", model);
				}
				//No Record Found Validation  Logic End  Y
				
				
				model.put("WOPart",workOrderPartConsumingReportList );
				//model.put("WOPart", WOBL.prepare_Part_Consuming_WorkOrdersTasksToParts(WorkOrder)); //Original Code Before No Record Found
				PartLocations	locations	= PartLocationsService.getPartLocations(WORK_LOCATION.getWorkorders_location_ID());
				if(locations != null)
					model.put("Location", WORK_LOCATION.getWorkorders_location());
				model.put("PartDate", WORKORDER_DATERANGE);

			} catch (Exception e) {
				e.printStackTrace();
			}

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

	} catch (Exception e) {
		System.err.println("Exception : "+e);
		e.printStackTrace();
	}

	return new ModelAndView("WorkOrder_Part_ConsumingReport", model);
}


@RequestMapping("/WorkOrdersReport")
public ModelAndView WorkOrderReport() {
	Map<String, Object> model = new HashMap<String, Object>();
	CustomUserDetails	userDetails		= null;
	try {
		userDetails	 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.put("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

	} catch (Exception e) {

	}
	return new ModelAndView("ReportWorkOrders", model);
}


@RequestMapping(value = "/ReportWorkOrder", method = RequestMethod.POST)
public ModelAndView ReportWorkOrder(@RequestParam("WorkOrder_daterange") final String WorkOrder_daterange,
		@ModelAttribute("command") WorkOrdersDto WorkOrdersReport, BindingResult result) {

	// PurchaseOrders POReport = RBL.preparePurchaseOrder(purchaseOrders);

	Map<String, Object> model = new HashMap<String, Object>();

	String dateRangeFrom = "", dateRangeTo = "";

	if (WorkOrder_daterange != "") {

		String[] From_TO_DateRange = null;
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			From_TO_DateRange = WorkOrder_daterange.split(" to ");

			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
			
		} catch (Exception e) {
			LOGGER.error("fuelmileage_vid:", e);
		}
		try {
			String WorkOrderQuery = "", vid = "", assigned = "", location = "", priority = "";

			if (WorkOrdersReport.getVehicle_vid() != null) {
				vid = "AND WO.vehicle_vid = " + WorkOrdersReport.getVehicle_vid() + " ";
			}

			if (WorkOrdersReport.getAssigneeId() != null && WorkOrdersReport.getAssigneeId() > 0) {
				assigned = "AND WO.assigneeId =" + WorkOrdersReport.getAssigneeId() + " ";
			}
			if (WorkOrdersReport.getWorkorders_location_ID() != null && WorkOrdersReport.getWorkorders_location_ID() > 0) {
				location = "AND WO.workorders_location_ID =" + WorkOrdersReport.getWorkorders_location() + " ";
			}
			if (WorkOrdersReport.getPriorityId() > 0) {
				priority = "AND WO.priorityId =" + WorkOrdersReport.getPriorityId() + " ";
			}

			WorkOrderQuery = vid + " " + assigned + " " + location + " " + priority + " ";

			model.put("WorkOrder", WOBL.prepareListWorkOrders(
					WorkOrdersService.ReportWorkOrders(WorkOrderQuery, dateRangeFrom, dateRangeTo, userDetails)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	return new ModelAndView("WorkOrders_Report", model);
}

	@RequestMapping("/VehicleWiseWorkOrderReport")
	public ModelAndView VehicleWiseWorkOrderReport() {
		Map<String, Object> model = new HashMap<String, Object>();
	
		return new ModelAndView("Latest_Vehicle_Wise_Work_Order_Report", model);
	}

}

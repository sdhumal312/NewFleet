package org.fleetopgroup.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RenewalReminderReportController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IRenewalReminderService RenewalReminderService;
	
	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired
	private CompanyConfigurationService companyConfigurationService;

	
	ReportBL RBL = new ReportBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping("/RRR")
	public ModelAndView RenewalReminderReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("RRR", model);
	}
	
	@RequestMapping("/RRComplianceReport")
	public ModelAndView RRComplianceReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("RenewalReminder_Compliance_Report", model);
	}
	
	@RequestMapping(value = "/RRComplianceReport", method = RequestMethod.POST)
	public ModelAndView RRComplianceReport(@RequestParam("RR_COMPLIANCE_DATE") final String RR_COMPLIANCE_DATE,
			@ModelAttribute("command") RenewalReminderDto ReminderDto, final HttpServletRequest request) {

		RenewalReminderDto RRReport = RBL.prepareRenewalReminder(ReminderDto);

		Map<String, Object> model = new HashMap<String, Object>();
		ValueObject object = null;

		try {

			String RenewalType = "", RenewaSublType = "", Renewal_to = "";

			if (RR_COMPLIANCE_DATE != "" && RR_COMPLIANCE_DATE != " ") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = RR_COMPLIANCE_DATE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}
				if (RRReport.getRenewalTypeId() > 0) {
					RenewalType = " AND RR.renewalTypeId=" + RRReport.getRenewalTypeId() + " ";
				}

				if (RRReport.getRenewal_Subid() > 0) {
					RenewaSublType = " AND  RR.renewal_Subid=" + RRReport.getRenewal_Subid() + " ";
				}

				if (dateRangeFrom != "" && dateRangeTo != "") {
					Renewal_to = " AND RR.renewal_to between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' ";

					String query = " " + RenewalType + " " + RenewaSublType + " " + Renewal_to + " ";

					List<RenewalReminderDto> Renewal = RenewalReminderService.listRenewalReminder(query,object);
					
					List<RenewalReminderDto> renewalReminderComplianceReport =RRBL.Report_Show_ListofRenewalDto(Renewal);
					//No Record Found Validation  Logic Start Y					
					if(renewalReminderComplianceReport == null)
					{
						model.put("NotFound", true);
						return new ModelAndView("RenewalReminder_Compliance_Report", model);
					}
					//No Record Found Validation  Logic End  Y
					model.put("renewal",renewalReminderComplianceReport);
					/*model.put("renewal", RRBL.Report_Show_ListofRenewalDto(Renewal));*/ //Original Code Before No Record 

					
					
					model.put("RRAmount", String.format("%.0f", round(RRBL.Total_RenewalReminder_Amount(Renewal), 2)));
				}

			}

			Authentication authALL = SecurityContextHolder.getContext().getAuthentication();
			String nameALL = authALL.getName(); // get logged in
												// username

			model.put("company", userProfileService.findUserProfileByUser_email(nameALL));

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("RenewalReminder_Compliance_Report", model);
	}

	@RequestMapping("/RRVehicleComplianceReport")
	public ModelAndView RRVehicleComplianceReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("RenewalReminder_Vehicle_Report", model);
	}
	
	/* Here code RRVehicleComplianceReport Select Radio Vehicle Fuel Range */
	@RequestMapping(value = "/RRVehicleComplianceReport", method = RequestMethod.POST)
	public ModelAndView RRVehicleComplianceReport(
			@ModelAttribute("command") @RequestParam("RR_VID") final String RR_VID,
			@RequestParam("RR_COMPLIANCE_DATE") final String RR_COMPLIANCE_DATE, RenewalReminderDto ReminderDto,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<RenewalReminderDto> totalVehicleWiseComplianceReportList=null; //No Record Found Validation  Logic Y
		RenewalReminderDto RRReport = RBL.prepareRenewalReminder(ReminderDto);
		ValueObject object = null;

		if (RR_COMPLIANCE_DATE != "" && RR_VID != "") {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = RR_COMPLIANCE_DATE.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				// remove Search vehicle ID without fuel entries vehicle id
				// remove
				if (RR_VID != null && !RR_VID.isEmpty()) {

					try {

						String Vehicle_registration = "", Renewal_type = "", Renewal_subtype = "", Renewal_to = "";
						if (RR_VID != "" && !RR_VID.isEmpty()) {
							Vehicle_registration = " AND RR.vid IN (" + RR_VID + ") ";
						}
						if (RRReport.getRenewalTypeId() > 0) {
							Renewal_type = " AND RR.renewalTypeId=" + RRReport.getRenewalTypeId() + "";
						}
						if (RRReport.getRenewal_Subid() > 0) {
							Renewal_subtype = " AND RR.renewal_Subid=" + RRReport.getRenewal_Subid() + "";
						}

						if (dateRangeFrom != "" && dateRangeTo != "") {
							Renewal_to = " AND RR.renewal_to between '" + dateRangeFrom + "' AND '" + dateRangeTo
									+ "' ";

							String query = " " + Vehicle_registration + " " + Renewal_type + " " + Renewal_subtype + " "
									+ Renewal_to + " ";

							// RenewalReminder Filter Query
							List<RenewalReminderDto> Renewal = RenewalReminderService.listRenewalReminder(query,object);
							
							totalVehicleWiseComplianceReportList= RRBL.Only_Show_ListofRenewalDto(Renewal); //No Record Found Validation  Logic Y
							//No Record Found Validation  Logic Start Y
							if(totalVehicleWiseComplianceReportList == null) 
							{
								model.put("NotFound", true); 
								return new ModelAndView("RenewalReminder_Vehicle_Report", model);
							}
							//No Record Found Validation  Logic End  Y
							model.put("renewal", totalVehicleWiseComplianceReportList);
							
							//model.put("renewal", RRBL.Only_Show_ListofRenewalDto(Renewal)); //Original Code Before NO Record Found Validation

							// RenewalReminder Total Amount Sum Query
							model.put("RRAmount", round(RRBL.Total_RenewalReminder_Amount(Renewal), 2));

							// vehicle get expert mileage
							List<Vehicle> VehicleReport = vehicleService
									.vehicle_wise_Fuel_mileage_multiple_vehicle(methodRemoveLastComma(RR_VID));
							// System.out.println(fuelRange);
							model.put("vehicles", VehicleReport);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				Authentication authALL = SecurityContextHolder.getContext().getAuthentication();
				String nameALL = authALL.getName(); // get logged in
													// username

				model.put("company", userProfileService.findUserProfileByUser_email(nameALL));

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

		return new ModelAndView("RenewalReminder_Vehicle_Report", model);
	}
	public String methodRemoveLastComma(String str) {
		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	@RequestMapping("/RRGroupComplianceReport")
	public ModelAndView RRGroupComplianceReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("RenewalReminder_Group_wise_compliance_Report", model);
	}
	
	/* Here code RRGroupComplianceReport Report */

	@RequestMapping(value = "/RRGroupComplianceReport", method = RequestMethod.POST)
	public ModelAndView RRGroupComplianceReport(
			@ModelAttribute("command") @RequestParam("vehicleGroupId") final long vehicleGroupId,
			@RequestParam("RR_COMPLIANCE_DATE") final String RR_COMPLIANCE_DATE, RenewalReminderDto ReminderDto,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<RenewalReminderDto> totalGroupWiseComplianceReportList=null; //No Record Found Validation  Logic Y
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RenewalReminderDto RRReport = RBL.prepareRenewalReminder(ReminderDto);
		ValueObject object = null;

		if (RR_COMPLIANCE_DATE != "" && vehicleGroupId != 0) {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = RR_COMPLIANCE_DATE.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {

				try {

					String Vehicle_registration = "", Renewal_type = "", Renewal_subtype = "", Renewal_to = "";

					if (vehicleGroupId != 0) {
						Vehicle_registration = " AND RR.vid IN (SELECT c.vid from Vehicle AS c where c.vehicleGroupId = "
								+ vehicleGroupId + " AND c.company_Id = "+userDetails.getCompany_id()+"  AND c.markForDelete = 0) ";
					}
					if (RRReport.getRenewalTypeId() > 0) {
						Renewal_type = " AND RR.renewalTypeId=" + RRReport.getRenewalTypeId() + "";
					}
					if (RRReport.getRenewal_Subid() > 0) {
						Renewal_subtype = " AND RR.renewal_Subid=" + RRReport.getRenewal_Subid() + "";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						Renewal_to = " AND RR.renewal_to between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' ";

						String query = " " + Vehicle_registration + " " + Renewal_type + " " + Renewal_subtype + " "
								+ Renewal_to + " ORDER BY RR.vid";

						// RenewalReminder Filter Query
						List<RenewalReminderDto> Renewal = RenewalReminderService.listRenewalReminder(query,object);
						
						
						totalGroupWiseComplianceReportList=RRBL.Only_Show_ListofRenewalDto(Renewal);
						//No Record Found Validation  Logic Start Y
						if(totalGroupWiseComplianceReportList == null) 
						{
							model.put("NotFound", true); 
							return new ModelAndView("RenewalReminder_Group_wise_compliance_Report", model);
						}
						//No Record Found Validation  Logic End  Y
						model.put("renewal",totalGroupWiseComplianceReportList);
						
						
						
						//model.put("renewal", RRBL.Only_Show_ListofRenewalDto(Renewal));  //Original Code Before No Record

						// RenewalReminder Total Amount Sum Query
						model.put("RRAmount", round(RRBL.Total_RenewalReminder_Amount(Renewal), 2));

						// vehicle get expert mileage filter Group wise
						// List<Vehicle> VehicleReport =
						// vehicleService.vehicle_wise_GroupFuelRange_Group(RR_GROUP);

						// System.out.println(fuelRange);
						// model.put("vehicles", VehicleReport);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

													// username

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

		return new ModelAndView("RenewalReminder_Group_wise_compliance_Report", model);
	}

	@RequestMapping("/RRApprovalReport")
	public ModelAndView RRApprovalReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("RenewalReminder_Approval_Report", model);
	}
	
	/* Here code RRGroupComplianceReport Report */

	@RequestMapping(value = "/RRApprovalReport", method = RequestMethod.POST)
	public ModelAndView RRApprovalReport(
			@ModelAttribute("command") @RequestParam("vehicleGroupId") final long vehicleGroupId,
			@RequestParam("RR_COMPLIANCE_DATE") final String RR_COMPLIANCE_DATE, RenewalReminderDto ReminderDto,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<RenewalReminderDto> approvalRenewalReminderReportList=null; //No Record Found Validation  Logic Y
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RenewalReminderDto RRReport = RBL.prepareRenewalReminder(ReminderDto);
		ValueObject object = null;

		if (RR_COMPLIANCE_DATE != "" && vehicleGroupId != 0) {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = RR_COMPLIANCE_DATE.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {

				try {

					String Vehicle_registration = "", Renewal_type = "", Renewal_subtype = "", Renewal_to = "";

					if (vehicleGroupId != 0) {
						Vehicle_registration = " AND RR.vid IN (SELECT c.vid from Vehicle AS c where c.vehicleGroupId = "
								+ vehicleGroupId + " AND c.company_Id = "+userDetails.getCompany_id()+"  AND c.markForDelete = 0) ";
					}
					if (RRReport.getRenewalTypeId() > 0) {
						Renewal_type = " AND RR.renewalTypeId=" + RRReport.getRenewalTypeId() + "";
					}
					if (RRReport.getRenewal_Subid() > 0) {
						Renewal_subtype = " AND RR.renewal_Subid=" + RRReport.getRenewal_Subid() + "";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						Renewal_to = " AND RR.renewal_to between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' ";

						String query = " " + Vehicle_registration + " " + Renewal_type + " " + Renewal_subtype + " "
								+ Renewal_to + " ORDER BY RR.vid";

						// RenewalReminder Filter Query
						List<RenewalReminderDto> Renewal = RenewalReminderService.listRenewalReminder(query,object);
						
						approvalRenewalReminderReportList= RRBL.Only_Show_ListofRenewalDto(Renewal);//No Record Found Validation  Y
						//No Record Found Validation  Logic Start Y
						if(approvalRenewalReminderReportList == null) 
						{
							model.put("NotFound", true); 
							return new ModelAndView("RenewalReminder_Approval_Report", model);
						}
						//No Record Found Validation  Logic End  Y	
						
						model.put("renewal", RRBL.Only_Show_ListofRenewalDto(Renewal));
						//model.put("renewal", RRBL.Only_Show_ListofRenewalDto(Renewal)); //Original Code Before No Record Found Y

						// RenewalReminder Total Amount Sum Query
						model.put("RRAmount", round(RRBL.Total_RenewalReminder_Amount(Renewal), 2));

						// vehicle get expert mileage filter Group wise
						// List<Vehicle> VehicleReport =
						// vehicleService.vehicle_wise_GroupFuelRange_Group(RR_GROUP);

						// System.out.println(fuelRange);
						// model.put("vehicles", VehicleReport);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

													// username

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

		return new ModelAndView("RenewalReminder_Approval_Report", model);
	}
	@RequestMapping("/RenewalReminderReport")
	public ModelAndView RenewalReminderReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		// show the Group service of the driver

		return new ModelAndView("ReportRenewalReminder", model);
	}
	
	@RequestMapping(value = "/RenewalReportFilters", method = RequestMethod.POST)
	public ModelAndView PeportFilter(@RequestParam("Renewal_daterange") final String RenewalRange_daterange,
			@ModelAttribute("command") RenewalReminderDto renewalReminderDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		String dateRangeFrom = "", dateRangeTo = "";
		ValueObject object = null;
		if (RenewalRange_daterange != "") {

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = RenewalRange_daterange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}
			try {

				String Vehicle_registration = "", Renewal_type = "", Renewal_subtype = "", Renewal_to = "",
						Renewal_from = "";
				if (renewalReminderDto.getVehicle_registration() != "") {
					Vehicle_registration = " AND RR.vid='" + renewalReminderDto.getVehicle_registration() + "'";
				}
				if (renewalReminderDto.getRenewalTypeId() != null && renewalReminderDto.getRenewalTypeId() != 0) {
					Renewal_type = " AND RR.renewalTypeId=" + renewalReminderDto.getRenewalTypeId() + "";
				}
				if (renewalReminderDto.getRenewal_Subid() != null && renewalReminderDto.getRenewal_Subid() != 0) {
					Renewal_subtype = " AND RR.renewal_Subid=" + renewalReminderDto.getRenewal_Subid() + "";
				}
				if (dateRangeFrom != "" && dateRangeTo != "") {
					Renewal_to = " AND RR.renewal_to between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' ";
				}

				String query = " " + Vehicle_registration + " " + Renewal_type + " " + Renewal_subtype + " "
						+ Renewal_to + " " + Renewal_from + "";

				model.put("renewal", RRBL.prepareListofRenewalDto(RenewalReminderService.listRenewalReminder(query,object)));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ModelAndView("Renewal_Reminder_Report", model);
	}
	
	@RequestMapping("/getRenewalExpiryReport")
	public ModelAndView getRenewalExpiryReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("RenewalExpiryReport", model);
	}
	
	@RequestMapping("/getRenewalOverDueReport")
	public ModelAndView getRenewalOverDueReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails 				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
		
		model.put("configuration", configuration);
		return new ModelAndView("RenewalOverDueReport", model);
	}

	@RequestMapping(value = "/getRenewalExpiryDataReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalExpiryDataReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return RenewalReminderService.getRenewalExpiryDataReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/getRenewalOverDueReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalOverDueReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return RenewalReminderService.getRenewalOverDueReport(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping("/viewRenewalReminderReport")
	public ModelAndView viewRenewalReminderReport() {
		boolean						createApprovalPermission					= false;
		Map<String, Object> model = null;
		try {
			model = new HashMap<String, Object>();
			CustomUserDetails 				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("CREATE_RR_APPROVAL"))) {
				createApprovalPermission = true;
			}
			model.put("createApprovalPermission", createApprovalPermission);
		} catch (Exception e) {
			
		}
		return new ModelAndView("ViewRenewalReminderReport", model);
	}
	
	
	@RequestMapping("/mandatoryRenewalPendingReport")
	public ModelAndView mandatoryRenewalPendingReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("mandatoryRenewalPendingReport", model);
	}
	
	@RequestMapping(value = "/getMandatoryRenewalPendingReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getMandatoryRenewalPendingReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.getMandatoryRenewalPendingReport(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(path="renwalReminderReport")
	public ModelAndView renwalReminderReport(){
		ModelAndView view = new ModelAndView("renwalReminderReport");
		CustomUserDetails userDetails = Utility.getObject(null);
		view.addObject("companyId", userDetails.getCompany_id());
		return view;
	}
	@PostMapping(path="getRenewalReminderReport",produces=MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object,Object> getRenewalReminderReport(@RequestParam HashMap<Object,Object> allReqParam){
		return 	RenewalReminderService.getRenewalReminderReport(new ValueObject(allReqParam)).getHtData();
	}
	
}

package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.bl.ServiceEntriesBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
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
public class ServiceEntriesReportController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IServiceEntriesService ServiceEntriesService;
	
	@Autowired
	private IUserProfileService userProfileService;
	
	@Autowired
	private IPartLocationsService PartLocationsService;
	PartLocationsBL PLBL = new PartLocationsBL();


	ServiceEntriesBL SEBL = new ServiceEntriesBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();
	ReportBL RBL = new ReportBL();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping("/SER")
	public ModelAndView ServiceEntriesReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("SER", model);
	}
	
	@RequestMapping("/VendorServiceEntriesReport")
	public ModelAndView OldPartReceived() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Vendor_Wise_ServiceEntries_Report", model);
	}
	

	@RequestMapping(value = "/VendorServiceEntriesReport", method = RequestMethod.POST)
	public ModelAndView VendorServiceEntriesReport(
			@ModelAttribute("command") @RequestParam("VENDOR_ID") final Integer VENDOR_ID,
			@RequestParam("PAYMENT_TYPE") final short PAYMENT_TYPE,
			@RequestParam("VEHICLE_ID") final Integer VEHICLE_ID,
			@RequestParam("SERVICE_DATERANGE") final String SERVICE_DATERANGE, final HttpServletRequest request) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<ServiceEntriesDto> vendorWiseServiceEntriesReportList=null; //No Record Found Validation  Logic Y
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (SERVICE_DATERANGE != "" && VENDOR_ID != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = SERVICE_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String vendor = "", payment = "", dateRange = "", vehicle = "";

					if (VENDOR_ID != null) {
						vendor = " AND SR.vendor_id=" + VENDOR_ID + " ";
					}
					if (VEHICLE_ID != null && VEHICLE_ID > 0) {
						vehicle = " AND SR.vid=" + VEHICLE_ID + " ";
					}

					if (PAYMENT_TYPE > 0) {
						payment = " AND SR.service_paymentTypeId=" + PAYMENT_TYPE + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						dateRange = " AND SR.invoiceDate between '" + dateRangeFrom + "'  AND '" + dateRangeTo + "' ";
					}

					String query = " " + vendor + " " + payment + " " + dateRange + " "+vehicle+" ";

					List<ServiceEntriesDto> ServiceEnt = ServiceEntriesService.ReportServiceEntries(query, userDetails);
					
					vendorWiseServiceEntriesReportList=SEBL.prepareListServiceEntries(ServiceEnt); //No Record Found Validation  Logic Y
					//No Record Found Validation  Logic Start Y
					if(vendorWiseServiceEntriesReportList == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Vendor_Wise_ServiceEntries_Report", model);
					}
					//No Record Found Validation  Logic End  Y			
					
					
					model.put("ServiceEntries",vendorWiseServiceEntriesReportList);
					//model.put("ServiceEntries", SEBL.prepareListServiceEntries(ServiceEnt)); //Original Code Before Validation
					// RenewalReminder Total Amount Sum Query
					model.put("TotalAmount", round(RRBL.Total_ServiceEntries_Amount(ServiceEnt), 2));

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

		return new ModelAndView("Vendor_Wise_ServiceEntries_Report", model);
	}

	/*SRS Travels Vehicle Wise Service Entries Report By Dev Yogi Start */
	@RequestMapping("/VehicleServiceEntriesReport")
	public ModelAndView VehicleServiceEntriesReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Vehicle_Wise_ServiceEntries_Report", model);
	}
	/*SRS Travels Vehicle Wise Service Entries Report By Dev Yogi End  */

	@RequestMapping(value = "/VehicleServiceEntriesReport", method = RequestMethod.POST)
	public ModelAndView VehicleServiceEntriesReport(
			@ModelAttribute("command") @RequestParam("VEHICLE_ID") final Integer VEHICLE_ID,
			@RequestParam("SERVICE_DATERANGE") final String SERVICE_DATERANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<ServiceEntriesDto> vehicleWiseServiceEntriesReport=null; //No Record Found Validation  Logic Y		
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (SERVICE_DATERANGE != "" && VEHICLE_ID != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = SERVICE_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String vehicleID = "", dateRange = "";

					if (VEHICLE_ID != null) {
						vehicleID = " AND SR.vid =" + VEHICLE_ID + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						dateRange = " AND SR.invoiceDate between '" + dateRangeFrom + "'  AND '" + dateRangeTo + "' ";
					}

					String query = " " + vehicleID + " " + dateRange + " ";

					List<ServiceEntriesDto> ServiceEnt = ServiceEntriesService.ReportServiceEntries(query, userDetails);
					
					
					vehicleWiseServiceEntriesReport= SEBL.prepareListServiceEntries(ServiceEnt); //No Record Found Validation
					//No Record Found Validation  Logic Start Y
					if(vehicleWiseServiceEntriesReport == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Vehicle_Wise_ServiceEntries_Report", model);
					}
					//No Record Found Validation  Logic End  Y	
					
					model.put("ServiceEntries",vehicleWiseServiceEntriesReport );
					//model.put("ServiceEntries", SEBL.prepareListServiceEntries(ServiceEnt)); //Original Code Before No Record Found
					// RenewalReminder Total Amount Sum Query
					model.put("TotalAmount", round(RRBL.Total_ServiceEntries_Amount(ServiceEnt), 2));

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

		return new ModelAndView("Vehicle_Wise_ServiceEntries_Report", model);
	}

	@RequestMapping("/ServiceEntriesReport")
	public ModelAndView ServiceEntriesReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

		} catch (Exception e) {

		}
		return new ModelAndView("ReportServiceEntries", model);
	}
	

	@RequestMapping(value = "/ReportServiceEntries", method = RequestMethod.POST)
	public ModelAndView ReportServiceEntries(@RequestParam("Service_daterange") final String serviceRange_daterange,
			@ModelAttribute("command") ServiceEntriesDto serviceDto, BindingResult result) {

		ServiceEntriesDto serviceReport = RBL.prepareServiceEntries(serviceDto);

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String Vid = "", Driver_id = "", Driver_name = "", Vendor_id = "", Vendor_name = "", InvoiceDate = "",
				Service_paymentType = "", Service_paiddate = "";
		String dateRangeFrom = "", dateRangeTo = "";

		if (serviceRange_daterange != "") {

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = serviceRange_daterange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}
			try {

				if (serviceReport.getVid() != null) {
					Vid = " AND  SR.vid=" + serviceReport.getVid() + " ";
				}

				if (serviceReport.getDriver_id() != null) {
					Driver_id = " AND SR.driver_id=" + serviceReport.getDriver_id() + " ";
				}

//				if (serviceReport.getDriver_name() != "") {
//					Driver_name = " AND lower(SR.driver_name) Like ('%" + serviceReport.getDriver_name() + "%') ";
//				}

				if (serviceReport.getVendor_id() != null) {
					Vendor_id = " AND SR.vendor_id=" + serviceReport.getVendor_id() + " ";
				}

//				if (serviceReport.getVendor_name() != "") {
//					Vendor_name = " AND lower(SR.vendor_name) Like ('%" + serviceReport.getVendor_name() + "%') ";
//				}

				if (serviceReport.getInvoiceDate() != "") {
					// fuel date converted String to date Format
					java.util.Date date = dateFormat.parse(serviceReport.getInvoiceDate());
					java.sql.Date InDate = new Date(date.getTime());

					InvoiceDate = " AND SR.invoiceDate='" + InDate + "' ";
				}

				if (serviceReport.getService_paymentTypeId() > 0) {
					Service_paymentType = " AND SR.service_paymentTypeId=" + serviceReport.getService_paymentTypeId()
							+ " ";
				}

				if (dateRangeFrom != "" && dateRangeTo != "") {
					Service_paiddate = " AND SR.service_paiddate between '" + dateRangeFrom + "'  AND '" + dateRangeTo
							+ "' ";
				}

				String query = " " + Vid + " " + Driver_id + " " + Driver_name + " " + Vendor_id + " " + Vendor_name
						+ " " + InvoiceDate + " " + Service_paymentType + " " + Service_paiddate + "";

				model.put("ServiceEntries",
						SEBL.prepareListServiceEntries(ServiceEntriesService.ReportServiceEntries(query, userDetails)));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("ServiceEntries_Report", model);
	}

}

package org.fleetopgroup.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.bl.ServiceReminderBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
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
public class ServiceReminderReportController {
	
	@Autowired
	private IServiceReminderService ServiceReminderService;
	
	@Autowired
	private IUserProfileService userProfileService;

	ServiceReminderBL SRBL = new ServiceReminderBL();

	ReportBL RBL = new ReportBL();

	@RequestMapping("/SRR")
	public ModelAndView ServiceReminderReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("SRR", model);
	}

	@RequestMapping("/OverdueServiceReport")
	public ModelAndView OverdueServiceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Overdue_ServiceReminder_Report", model);
	}

	@RequestMapping(value = "/OverdueServiceReport", method = RequestMethod.POST)
	public ModelAndView OverdueServiceReport(
			@ModelAttribute("command") @RequestParam("SERVICE_DATE") String SERVICE_DATE, ServiceReminder service,
			final HttpServletRequest request) {

		ServiceReminderDto serviceReport = RBL.prepareServiceReminder(service);

		Map<String, Object> model = new HashMap<String, Object>();

		String Vid = "", Service_type = "", Service_subtype = "";

		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (SERVICE_DATE != "") {
				SERVICE_DATE	= DateTimeUtility.getSqlStrDateFromStrDate(SERVICE_DATE, DateTimeUtility.YYYY_MM_DD);
				try {

					if (serviceReport.getVid() != null) {
						Vid = " AND v.vid=" + serviceReport.getVid() + " ";
					}
					if (serviceReport.getServiceTypeId() != null && serviceReport.getServiceTypeId() > 0) {
						Service_type = " AND v.serviceTypeId=" + serviceReport.getServiceTypeId() + " ";
					}
					if (serviceReport.getServiceSubTypeId() != null
							&& serviceReport.getServiceSubTypeId() > 0) {
						Service_subtype = " AND v.serviceSubTypeId=" + serviceReport.getServiceSubTypeId() + " ";
					}

					String query = "  AND v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 " + Vid + " " + Service_type + " " + Service_subtype + "";
					
					if(((SRBL.prepareListofServiceReminder(
							ServiceReminderService.ReportOverDueServiceReminder(query, SERVICE_DATE, userDetails.getCompany_id()))) == null)||((SRBL.prepareListofServiceReminder(
									ServiceReminderService.ReportOverDueServiceReminder(query, SERVICE_DATE, userDetails.getCompany_id()))).isEmpty())) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Overdue_ServiceReminder_Report", model);
					}
					
					
					model.put("Service", SRBL.prepareListofServiceReminder(
							ServiceReminderService.ReportOverDueServiceReminder(query, SERVICE_DATE, userDetails.getCompany_id())));

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

		return new ModelAndView("Overdue_ServiceReminder_Report", model);
	}

	@RequestMapping("/DuesoonServiceReport")
	public ModelAndView DuesoonServiceReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Duesoon_ServiceReminder_Report", model);
	}

	@RequestMapping(value = "/DuesoonServiceReport", method = RequestMethod.POST)
	public ModelAndView DuesoonServiceReport(
			@ModelAttribute("command") @RequestParam("SERVICE_DATE") String SERVICE_DATE, ServiceReminder service,
			final HttpServletRequest request) {

		ServiceReminderDto serviceReport = RBL.prepareServiceReminder(service);

		Map<String, Object> model = new HashMap<String, Object>();

		String Vid = "", Service_type = "", Service_subtype = "";
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (SERVICE_DATE != "") {
				SERVICE_DATE	= DateTimeUtility.getSqlStrDateFromStrDate(SERVICE_DATE, DateTimeUtility.YYYY_MM_DD);
				try {

					if (serviceReport.getVid() != null) {
						Vid = " AND v.vid=" + serviceReport.getVid() + " ";
					}
					if (serviceReport.getServiceTypeId() != null && serviceReport.getServiceTypeId() > 0) {
						Service_type = " AND v.serviceTypeId=" + serviceReport.getServiceTypeId() + " ";
					}
					if (serviceReport.getServiceSubTypeId() != null
							&& serviceReport.getServiceSubTypeId() > 0) {
						Service_subtype = " AND v.serviceSubTypeId=" + serviceReport.getServiceSubTypeId() + " ";
					}

					String query = " AND v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0  " + Vid + " " + Service_type + " " + Service_subtype + "";

					
					
					if(((SRBL.prepareListofServiceReminder(
							ServiceReminderService.ReportDueSoonServiceReminder(query, SERVICE_DATE, userDetails.getCompany_id()))) == null) ||(SRBL.prepareListofServiceReminder(
									ServiceReminderService.ReportDueSoonServiceReminder(query, SERVICE_DATE, userDetails.getCompany_id())).isEmpty())) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Duesoon_ServiceReminder_Report", model);
					}
					
					model.put("Service", SRBL.prepareListofServiceReminder(
							ServiceReminderService.ReportDueSoonServiceReminder(query, SERVICE_DATE, userDetails.getCompany_id())));

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

		return new ModelAndView("Duesoon_ServiceReminder_Report", model);
	}


	@RequestMapping("/ServiceReminderReport")
	public ModelAndView ServiceReminderReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		// show the Group service of the driver

		return new ModelAndView("ReportServiceReminder", model);
	}



	@RequestMapping(value = "/ServiceReminderReport", method = RequestMethod.POST)
	public ModelAndView ServiceReminderReport(@ModelAttribute("command") ServiceReminder service,
			BindingResult result) {
		ServiceReminderDto serviceReport = RBL.prepareServiceReminder(service);

		Map<String, Object> model = new HashMap<String, Object>();

		String Vid = "", Service_type = "", Service_subtype = "";
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

			short serviceOver = 0;
			if (serviceReport.getServiceStatusId() > 0) {
				serviceOver = serviceReport.getServiceStatusId();
			}
			switch (serviceOver) {
			case ServiceReminderDto.SERVICE_REMINDER_STATUS_ACTIVE:

				if (serviceReport.getVid() != null) {
					Vid = " AND v.vid=" + serviceReport.getVid() + " ";
				}
				if (serviceReport.getServiceTypeId() != null && serviceReport.getServiceTypeId() > 0) {
					Service_type = " AND v.serviceTypeId=" + serviceReport.getServiceTypeId() + " ";
				}
				if (serviceReport.getServiceSubTypeId() != null && serviceReport.getServiceSubTypeId() > 0) {
					Service_subtype = " AND v.serviceSubTypeId=" + serviceReport.getServiceSubTypeId() + " ";
				}

				String query = " AND v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 " + Vid + " " + Service_type + " " + Service_subtype + "";

				model.put("Service", SRBL.prepareListofServiceReminder(
						ServiceReminderService.ReportOverDueServiceReminder(query, ft.format(currentDate), userDetails.getCompany_id())));

				break;
			case ServiceReminderDto.SERVICE_REMINDER_STATUS_INACTIVE:

				if (serviceReport.getVid() != null) {
					Vid = " AND v.vid=" + serviceReport.getVid() + " ";
				}
				if (serviceReport.getServiceTypeId() != null && serviceReport.getServiceTypeId() > 0) {
					Service_type = " AND v.serviceTypeId=" + serviceReport.getServiceTypeId() + " ";
				}
				if (serviceReport.getServiceSubTypeId() != null && serviceReport.getServiceSubTypeId() > 0) {
					Service_subtype = " AND v.serviceSubTypeId=" + serviceReport.getServiceSubTypeId() + " ";
				}

				String queryDUESOON = " AND v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0  " + Vid + " " + Service_type + " " + Service_subtype + "";

				model.put("Service", SRBL.prepareListofServiceReminder(
						ServiceReminderService.ReportDueSoonServiceReminder(queryDUESOON, ft.format(currentDate), userDetails.getCompany_id())));

				break;

			default:

				if (serviceReport.getVid() != null) {
					Vid = " AND v.vid=" + serviceReport.getVid() + " ";
				}
				if (serviceReport.getServiceTypeId() != null && serviceReport.getServiceTypeId() > 0) {
					Service_type = " AND v.serviceTypeId=" + serviceReport.getServiceTypeId() + " ";
				}
				if (serviceReport.getServiceSubTypeId() != null && serviceReport.getServiceSubTypeId() > 0) {
					Service_subtype = " AND v.serviceSubTypeId=" + serviceReport.getServiceSubTypeId() + " ";
				}

				String queryALL = " AND v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0  " + Vid + " " + Service_type + " " + Service_subtype + "";

				model.put("Service",
						SRBL.prepareListofServiceReminder(ServiceReminderService.ReportServiceReminder(queryALL, userDetails.getCompany_id())));

				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Service_Reminder_ReportTo", model);
	}

	@RequestMapping("/vehicleWiseActiveServiceReminderReport")
	public ModelAndView vehicleWiseActiveServiceReminderReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("vehicleWiseActiveServiceReminderReport", model);
	}
	

	
}

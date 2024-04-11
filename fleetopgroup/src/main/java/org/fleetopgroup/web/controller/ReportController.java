package org.fleetopgroup.web.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.constant.VendorTypeConfigurationConstants;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverCommentBL;
import org.fleetopgroup.persistence.bl.DriverJobTypeBL;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.DriverTrainingTypeBL;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.InventoryTyreInvoiceBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.bl.TripDailyBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleCommentBL;
import org.fleetopgroup.persistence.bl.VehicleFuelBL;
import org.fleetopgroup.persistence.bl.VehicleGroupBL;
import org.fleetopgroup.persistence.bl.VehicleStatusBL;
import org.fleetopgroup.persistence.bl.VehicleTypeBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.bl.VendorTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.GraphDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.RenewalSubTypeDto;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.fleetopgroup.persistence.model.RenewalSubType;
import org.fleetopgroup.persistence.model.RenewalType;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeService;
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

import com.google.gson.Gson;

/**
 * @author fleetop
 *
 */

@Controller
@Scope("session")
public class ReportController {

	@Autowired IVehicleService vehicleService;
	@Autowired IRenewalReminderService RenewalReminderService;

	@Autowired IVehicleGroupService vehicleGroupService;

	@Autowired IVendorService vendorService;
	
	@Autowired IRenewalTypeService RenewalTypeService;

	
	@Autowired IRenewalSubTypeService RenewalSubTypeService;
	@Autowired IVendorTypeService VendorTypeService;


	
	DriverTrainingTypeBL TrainingDT = new DriverTrainingTypeBL();

	PartLocationsBL PLBL = new PartLocationsBL();

	
	DriverJobTypeBL DriverJOBDT = new DriverJobTypeBL();

	
	@Autowired IInventoryTyreService iventoryTyreService;
	InventoryTyreInvoiceBL ITBL = new InventoryTyreInvoiceBL();
	InventoryBL INVBL = new InventoryBL();

	
	
	TripDailyBL TDBL = new TripDailyBL();

	
	@Autowired ITripDailySheetService TripDailySheetService;

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

	
	@Autowired IUserProfileService userProfileService;
	@Autowired CompanyConfigurationService companyConfigurationService;

	VehicleCommentBL VCBL = new VehicleCommentBL();

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping("/Dashboard")
	public ModelAndView Dashboard() {
		Map<String, Object> model = new HashMap<String, Object>();

		// show the Group service of the driver
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// vehicle Document Master Type
			model.put("vehicleComment",
					VCBL.prepare_VehicleComment_ListofDto(vehicleService.Get_Recent_Comment_Details(userDetails.getCompany_id())));

			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

			Object[] count = vehicleService
					.countTotal_Dashboard_IOpen_IOverdue_SROverdue_SRDuesoon_RRToday_DLRToday_PUReq_PUOrdered(
							ft.format(currentDate), userDetails.getCompany_id());

			model.put("Iopen_Count", (Long) count[0]);
			model.put("IOverdue_Count", (Long) count[1]);
			model.put("SROverdue_Count", (Long) count[2]);
			model.put("SRDuesoon_Count", (Long) count[3]);
			model.put("RRToday_Count", (Long) count[4]);
			model.put("DLRToday_Count", (Long) count[5]);
			model.put("PUReq_Count", (Long) count[6]);
			model.put("PUOrdered_Count", (Long) count[7]);

		} catch (Exception e) {

		}
		return new ModelAndView("dashboard", model);
	}

	@RequestMapping(value = "/getBarGraph", method = RequestMethod.GET)
	public void getVehicleOdometerGraph(Map<String, Object> map, @RequestParam("dateFrom") final String dateFrom,
			@RequestParam("dateTo") final String dateTo, final HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Object[] countSum = vehicleService.countTotal_Grap_Cost_Fuel_RenewalRe_SE_TS_PO_WO_Amount(dateFrom, dateTo, userDetails.getCompany_id());

		List<GraphDto> fuelGrahp = new ArrayList<GraphDto>();

		String[] nameGrap = { "Fuel", "RenewalReminder", "ServiceEntries", "TripSheet", "PurchaseOrder", "WorkOrder", "Urea" };
		if (countSum != null) {

			for (int j = 0; j < nameGrap.length; j++) {
				GraphDto wadd = new GraphDto();
				wadd.setName(nameGrap[j]);
				if (countSum[j] != null) {
					if (j == 1) {
						wadd.setAmount((Double) countSum[j]);
					} else {
						wadd.setAmount((Double) countSum[j]);
						// System.out.println((Double) countSum[j]);
					}
				} else {
					wadd.setAmount(0.0);
				}
				fuelGrahp.add(wadd);
			}

		}

		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(fuelGrahp));
	}

	@RequestMapping("/Report")
	public ModelAndView Report(@ModelAttribute("command") RenewalReminderDto renewalReminderDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Report", model);
	}

	@RequestMapping("/ImportantReport")
	public ModelAndView ImportantReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("ImportantReport", model);
	}


	@RequestMapping("/VendorReport")
	public ModelAndView VendorReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration				= null;
		CustomUserDetails				userDetails 				= null;
		Integer							companyId					= 0;
		Boolean							isCommonMaster				= true;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
				isCommonMaster		= true;
			} else {
				companyId			= userDetails.getCompany_id();
				isCommonMaster		= false;
			}
			
			// show the Vendor List
			model.put("vendorlist", VenBL.prepareListofVendor(vendorService.findAllVendorList(userDetails.getCompany_id())));
			model.put("vendorType", VenType.VendorListofDto(VendorTypeService.findAllByCompanyId(companyId, isCommonMaster)));

		} catch (Exception e) {

		}
		return new ModelAndView("ReportVendor", model);
	}

	@RequestMapping("/BookingTSReport")
	public ModelAndView BookingTSReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		// show the Group service of the driver
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// show the vehicle list List
			model.put("vehiclegroup", vg.prepareListofDto(vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id())));

		} catch (Exception e) {

		}
		return new ModelAndView("ReportBookingTS", model);
	}

	

	@RequestMapping("/InventoryTyreReport")
	public ModelAndView InventoryTyreReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			/*
			 * model.put("PartCategories",
			 * PCBL.prepareListofBean(PartCategoriesService.listOnlyStatus()));
			 * model.put("PartManufacturer",
			 * PMBL.prepareListofBean(PartManufacturerService.listOnlyStatus())) ;
			 * model.put("PartLocations",
			 * PLBL.prepareListofBean(PartLocationsService.listOnlyStatus()));
			 */
		} catch (Exception e) {

		}
		return new ModelAndView("ReportInventoryTyre", model);
	}

	
	@RequestMapping(value = "/VendorReport", method = RequestMethod.POST)
	public ModelAndView ReportVendor(@ModelAttribute("command") VendorDto vendor, BindingResult result) {

		VendorDto vendorReport = RBL.prepareVendor(vendor);
		Map<String, Object> model = new HashMap<String, Object>();

		String query = "";
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		

			if ((vendorReport.getVendorId() != 0) && (vendorReport.getVendorTypeId() != 0)
					&& (vendorReport.getVendorLocation() != "")) {

				query = " v.vendorId=" + vendorReport.getVendorId() + " AND v.vendorTypeId="
						+ vendorReport.getVendorTypeId() + "  AND v.vendorLocation='" + vendorReport.getVendorLocation()
						+ "'";
				model.put("vendor", VenBL.prepareListofVendor(vendorService.listVendor(query, userDetails.getCompany_id())));

			} else if ((vendorReport.getVendorId() != 0) && (vendorReport.getVendorTypeId() != 0)) {

				query = " v.vendorId=" + vendorReport.getVendorId() + " AND v.vendorTypeId="
						+ vendorReport.getVendorTypeId() + "";
				model.put("vendor", VenBL.prepareListofVendor(vendorService.listVendor(query, userDetails.getCompany_id())));

			} else if ((vendorReport.getVendorTypeId() != 0) && (vendorReport.getVendorLocation() != "")) {

				query = " v.vendorTypeId=" + vendorReport.getVendorTypeId() + " AND v.vendorLocation='"
						+ vendorReport.getVendorLocation() + "'";
				model.put("vendor", VenBL.prepareListofVendor(vendorService.listVendor(query, userDetails.getCompany_id())));

			} else if ((vendorReport.getVendorId() != 0) && (vendorReport.getVendorLocation() != "")) {

				query = " v.vendorId=" + vendorReport.getVendorId() + " AND v.vendorLocation='"
						+ vendorReport.getVendorLocation() + "'";
				model.put("vendor", VenBL.prepareListofVendor(vendorService.listVendor(query, userDetails.getCompany_id())));

			} else if (vendorReport.getVendorId() != 0) {

				query = " vendorId=" + vendorReport.getVendorId() + " ";
				model.put("vendor", VenBL.prepareListofVendor(vendorService.listVendor(query, userDetails.getCompany_id())));

			} else if (vendorReport.getVendorTypeId() != 0) {

				query = " v.vendorTypeId=" + vendorReport.getVendorTypeId() + " ";
				model.put("vendor", VenBL.prepareListofVendor(vendorService.listVendor(query, userDetails.getCompany_id())));

			} else if (vendorReport.getVendorLocation() != "") {

				query = " v.vendorLocation='" + vendorReport.getVendorLocation() + "' ";
				model.put("vendor", VenBL.prepareListofVendor(vendorService.listVendor(query, userDetails.getCompany_id())));

			} else {
				model.put("vendor", VenBL.prepareListofVendor(vendorService.findAllVendorList(userDetails.getCompany_id())));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("SelectPage", 1);
		return new ModelAndView("Vendor_Report", model);
	}

	

	@RequestMapping(value = "/ReportInventoryTyre", method = RequestMethod.POST)
	public ModelAndView ReportInventoryTyre(@ModelAttribute("command") InventoryTyre Tyre,
			InventoryTyreInvoice TyreInvoice, BindingResult result) {

		// PurchaseOrders POReport = RBL.preparePurchaseOrder(purchaseOrders);

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String MANUFACTURER = "", MODEL = "", SIZE = "", location = "", invoice = "", vendor = "";

			if (Tyre.getTYRE_MANUFACTURER_ID() != null && Tyre.getTYRE_MANUFACTURER_ID() > 0) {
				MANUFACTURER = "AND R.TYRE_MANUFACTURER_ID =" + Tyre.getTYRE_MANUFACTURER_ID() + "";
			}
			if (Tyre.getTYRE_MODEL_ID() != null && Tyre.getTYRE_MODEL_ID() > 0) {
				MODEL = "AND R.TYRE_MODEL_ID =" + Tyre.getTYRE_MODEL_ID() + "";
			}
			if (Tyre.getTYRE_SIZE_ID() != null && Tyre.getTYRE_SIZE_ID() > 0) {
				SIZE = "AND R.TYRE_SIZE_ID =" + Tyre.getTYRE_SIZE_ID() + "";
			}
			if (Tyre.getWAREHOUSE_LOCATION_ID() != null && Tyre.getWAREHOUSE_LOCATION_ID() > 0) {
				location = "AND R.WAREHOUSE_LOCATION_ID =" + Tyre.getWAREHOUSE_LOCATION_ID() + "";
			}
			if (TyreInvoice.getINVOICE_NUMBER() != "") {
				invoice = "AND R.ITYRE_INVOICE_ID IN ( SELECT ITYRE_ID FROM InventoryTyreInvoice WHERE INVOICE_NUMBER ='"
						+ TyreInvoice.getINVOICE_NUMBER() + "' AND COMPANY_ID = "+userDetails.getCompany_id()+" AND markForDelete = 0 )";
			}
			if (TyreInvoice.getVENDOR_ID() != null && TyreInvoice.getVENDOR_ID() > 0) {
				vendor = "AND R.ITYRE_INVOICE_ID IN ( SELECT ITYRE_ID FROM InventoryTyreInvoice WHERE VENDOR_ID ="
						+ TyreInvoice.getVENDOR_ID() + " AND COMPANY_ID = "+userDetails.getCompany_id()+" AND markForDelete = 0 ) ";
			}

			String Query = MANUFACTURER + " " + MODEL + " " + SIZE + " " + location + " " + invoice + " " + vendor;
			List<InventoryTyreDto> pageList = iventoryTyreService.Report_List_InventoryTyre(Query, userDetails.getCompany_id());

			model.put("Tyre", pageList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Report_TyreInventory", model);

	}

	/*******************************************************************************************************
	 ************* Less loading Dropdown
	 *******************************************************************************************************/

	@RequestMapping(value = "/getReportRenewalType", method = RequestMethod.POST)
	public void getVehicleTripEditList(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustomUserDetails		userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<RenewalType> addresses = new ArrayList<RenewalType>();
		for (RenewalType add : RenewalTypeService.findAllByCompanyId(userDetails.getCompany_id())) {
			RenewalType wadd = new RenewalType();
			 wadd.setRenewal_id(add.getRenewal_id());
			wadd.setRenewal_Type(add.getRenewal_Type());
			addresses.add(wadd);
		}

		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));
		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/getReportRenewalSubType", method = RequestMethod.POST)
	public void getRenewalSubType(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails		userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<RenewalSubType> addresses = new ArrayList<RenewalSubType>();
		for (RenewalSubTypeDto add : RenewalSubTypeService.findAllByCompanyId(userDetails.getCompany_id())) {
			RenewalSubType wadd = new RenewalSubType();
			wadd.setRenewal_Subid(add.getRenewal_Subid());
			wadd.setRenewal_SubType(add.getRenewal_SubType());
			// System.out.println(add.getVid());
			addresses.add(wadd);
		}

		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));
		response.getWriter().write(gson.toJson(addresses));
	}


	/* Here code Vehicle Wise Repair Report */
	@RequestMapping(value = "/SRIVehicleCurrentRouteReport")
	public ModelAndView SRIVehicleCurrentRouteRport(final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			String query = " AND  TRIPDAILYID IN (SELECT V.tripSheetID FROM Vehicle AS V "
					+ " WHERE vStatusId="+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+" AND company_Id = "+userDetails.getCompany_id()+" AND markForDelete = 0) ";

			model.put("TripDaily",
					TDBL.prepare_TripDailySheetList(TripDailySheetService.List_TripDailySheet_Report(query, userDetails)));

		} catch (Exception e) {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

		return new ModelAndView("Vehicle_CurrentRoute_Report", model);
	}

		
	// Note : This Controller DailyTripReport and Cash Book Details
	@RequestMapping(value = "/VRRPERMITTAXReport", method = RequestMethod.POST)
	public ModelAndView VRRPERMITTAXReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATERANGE") final String TRIP_DATERANGE,
			@RequestParam("VEHICLEGROUP") final String VEHICLEGROUP, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null) {

				Authentication authALL = SecurityContextHolder.getContext().getAuthentication();
				String nameALL = authALL.getName();

				model.put("company", userProfileService.findUserProfileByUser_email(nameALL));

				try {
					java.util.Date currentDate = new java.util.Date();
					DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
					// show the RenewalReminder in Current value only dispaly
					// list in
					// all

					ArrayList<RenewalReminderDto> renewalReminder = RenewalReminderService
							.RENEWAL_REMINDER_GROUP_WISE_REPORT(VEHICLEGROUP, ft.format(currentDate));

					model.put("renewal", RRBL.Only_Show_ListofRenewalDto(renewalReminder));

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Daily_TripDaily_CashBook_Report", model);
	}

	@RequestMapping(value = "/comparisionReport", method = RequestMethod.GET)
	public ModelAndView pickAndDropReport(final HttpServletRequest request) {
		ModelAndView 					map 			= null;
		try {
			map = new ModelAndView("comparisionReport");
			return map;
			
		}  catch (Exception e) {
			throw e;
		}
	}
}
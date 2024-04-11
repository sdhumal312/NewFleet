package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.bl.VehicleFuelBL;
import org.fleetopgroup.persistence.bl.VehicleGroupBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleFuelService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
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
public class FuelReportController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	

	@Autowired
	CompanyConfigurationService companyConfigurationService;

	@Autowired
	private IFuelService fuelService;

	@Autowired
	private IVehicleService vehicleService;

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private IVehicleGroupService vehicleGroupService;
	@Autowired
	private IVehicleFuelService vehicleFuelService;

	FuelBL FuBL = new FuelBL();
	ReportBL RBL = new ReportBL();
	VehicleGroupBL vg = new VehicleGroupBL();
	VehicleFuelBL vf = new VehicleFuelBL();

	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");

	@RequestMapping("/FR")
	public ModelAndView FuelReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("FR", model);
	}
	
	/*Dev Y Start : Adding New Module  Fuel Date Range Cash/Credit Wise Report*/
	@RequestMapping(value = "/FuelDateRangeCashOrCreditWiseReport")
	public ModelAndView FuelDateRangeCashOrCreditWiseReport() throws Exception {
		Map<String, Object> 		model 				= null;
		try {
			model				= new HashMap<String, Object>();
			
			return new ModelAndView("Fuel_DateRange_CashOrCredit_Wise_Report", model); 
		} catch (Exception e) {
			throw e;
		} finally {
			model				= null;
		}
	}	
	/*Dev Y End : Adding New Module  Fuel Date Range Cash/Credit Wise Report*/
	
	@RequestMapping("/FuelMileageReport")
	public ModelAndView FuelMileageReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Fuel_mileage_Report", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FuelMileageReport", method = RequestMethod.POST)
	public ModelAndView Vehicle_Wise_Fuel_Mileage_Report(
			@ModelAttribute("command") @RequestParam("fuelmileage_vid") String fuelmileage_vid,
			@RequestParam("vehicleGrpId7") final Long groupId,
			@RequestParam("fuelmileage_daterange") final String fuelmileage_daterange,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		

		if (fuelmileage_daterange != "") {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {
				HashMap<String, Object>	 configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
				model.put("configuration",configuration);
				From_TO_DateRange = fuelmileage_daterange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				// Report Fuel Vehicle Wise Fuel_Mileage Report select Multiple
				List<FuelDto> fuelReport = null;
				
				
				if(fuelmileage_vid != null && !fuelmileage_vid.trim().equalsIgnoreCase("") && Integer.parseInt(fuelmileage_vid) > 0) {
					fuelReport = fuelService.Vehicle_wise_Fuel_Mileage_Report(dateRangeFrom, dateRangeTo,
							fuelmileage_vid, userDetails.getCompany_id());
				}else if(groupId != null && groupId > 0) {
				ValueObject	valueObject = fuelService.Group_wise_Fuel_Mileage_Report(dateRangeFrom, dateRangeTo,
							groupId, userDetails.getCompany_id());
					if(valueObject != null) {
						fuelReport	= (List<FuelDto>) valueObject.get("fuelList");
						fuelmileage_vid	= valueObject.getString("vehicleIds");
					}
				
				}
				if(fuelReport  != null && !fuelReport.isEmpty()) {
					
					
					model.put("fuel", FuBL.prepareListofFuel(fuelReport));
					Double liter = 0.0, fuelamount = 0.0, kmpl = 0.0;
					Double totalUsageKM = 0.0,gpsTotal=0.0;
					if (fuelReport != null && !fuelReport.isEmpty()) {
						for (FuelDto fuelBean : fuelReport) {
							liter += fuelBean.getFuel_liters();
							fuelamount += fuelBean.getFuel_amount();
							totalUsageKM += fuelBean.getFuel_usage();
							gpsTotal+=fuelBean.getGpsUsageKM();
						}
						kmpl	= totalUsageKM/liter;
					}
					
					String Total_AmountALL = AMOUNTFORMAT.format(fuelamount);
					model.put("TotalFuelAmount", Total_AmountALL);
					model.put("totalUsageKM", Utility.round(totalUsageKM, 2));
					model.put("gpsTotal", Utility.round(gpsTotal, 2));
					NumberFormat formatter = new DecimalFormat("#0.00");     
					model.put("kmpl", formatter.format(kmpl));
					
					String Total_literALL = AMOUNTFORMAT.format(liter);
					model.put("TotalLiterAmount", Total_literALL);
					
					try {
						// select vehicle to split string array list
						if(fuelmileage_vid != null && !fuelmileage_vid.trim().equalsIgnoreCase("")) {
							
						
							model.put("vehicles", vehicleService
									.vehicle_wise_Fuel_mileage_multiple_vehicle(methodRemoveLastComma(fuelmileage_vid)));
						}
						
					} catch (Exception e) {
						LOGGER.error("fuelmileage_vid:", e);
					}
					
					
				}else {
					model.put("NotFound", true); 
					return new ModelAndView("Fuel_mileage_Report", model);
				
				}
				
				
				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

		return new ModelAndView("Fuel_mileage_Report", model);
	}

	public String methodRemoveLastComma(String str) {
		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	@RequestMapping("/VehicleFuelRange")
	public ModelAndView VehicleFuelRange() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Vehicle_with_Fuel_Range_Report", model);
	}
	/* Here code Vehicle Wise Fuel Report Select Radio Vehicle Fuel Range */
	@RequestMapping(value = "/VehicleFuelRange", method = RequestMethod.POST)
	public ModelAndView Vehicle_Wise_Fuel_Range_Report(
			@ModelAttribute("command") @RequestParam("fuelRange_vid") final String fuelRange_vid,
			@RequestParam("fuelRange") final short fuelRange,
			@RequestParam("fuelRange_daterange") final String fuelRange_daterange, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		if (fuelRange_daterange != "" && fuelRange_vid != "") {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = fuelRange_daterange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				// Report Fuel Vehicle Wise Fuel_Mileage Report select Multiple
				List<FuelDto> fuelReport = null;
				HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
				if(!(boolean) configuration.get("showKmplOnPartialEntry")) {
					fuelReport = fuelService.Fuel_Range_with_Vehicle_wise__Report(dateRangeFrom, dateRangeTo,
							fuelRange_vid, userDetails.getCompany_id());
				}else {
					fuelReport = fuelService.Fuel_Range_with_Vehicle_wise__ReportWithPartial(dateRangeFrom, dateRangeTo,
							fuelRange_vid, userDetails.getCompany_id());
				}
				

				// remove Search vehicle ID without fuel entries vehicle id
				// remove
				if(fuelReport != null) {
					
					String[] vehicle_fuel = null;
					
					String vehicle_in_fuel_id = new String();
					try {
						// select vehicle to split string array list
						vehicle_fuel = fuelRange_vid.split(",");
						if (vehicle_fuel != null) {
							for (String fuelID : vehicle_fuel) {
								// array list to check vehicle ID True are not in
								// Fuel Array liat
								if (fuelReport.stream().anyMatch(ti -> ti.getVid() == Integer.parseInt(fuelID))) {
									// contains the id
									
									vehicle_in_fuel_id += fuelID + ",";
									
								}
							}
						}
						
					} catch (Exception e) {
						LOGGER.error("fuelmileage_vid:", e);
					}
					if (vehicle_in_fuel_id != null && !vehicle_in_fuel_id.isEmpty()) {
						// vehicle get expert mileage
						List<Vehicle> VehicleReport = vehicleService
								.vehicle_wise_Fuel_mileage_multiple_vehicle(methodRemoveLastComma(vehicle_in_fuel_id));
						// this below code link to business layer to filter with
						// vehicle
						// Report values
						List<FuelDto> fuelReportFilter_withRange = FuBL.Report_Vehicle_wise_Fuel_RangeFuel(VehicleReport,
								fuelReport, fuelRange);
						model.put("vehicles", VehicleReport);
						// this model show switch condition which select filter only
						// display
						
						model.put("fuel", fuelReportFilter_withRange);
						
					}
				}


				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

		return new ModelAndView("Vehicle_with_Fuel_Range_Report", model);
	}

	/* Here code Vehicle Wise Repair Report */

	@RequestMapping(value = "/GroupFuelRange", method = RequestMethod.POST)
	public ModelAndView Vehicle_Wise_GroupFuelRange_Report(
			@ModelAttribute("command") @RequestParam("fuelRange_group") final long fuelRange_group,
			@RequestParam("fuelRange") final short fuelRange,
			@RequestParam("fuelRange_daterange") final String fuelRange_daterange, final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
		if (fuelRange_daterange != "" && fuelRange_group != 0) {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = fuelRange_daterange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				// Report Fuel Vehicle Wise Fuel_Mileage Report select Multiple
				// vehicle get expert mileage filter Group wise
				List<Vehicle> VehicleReport = vehicleService.vehicle_wise_GroupFuelRange_Group(fuelRange_group,
						userDetails.getCompany_id());
				List<FuelDto> fuelReportList = null;
				// get select Group wise Reporting list
				if(!(boolean) configuration.get("showKmplOnPartialEntry")) {
					
					fuelReportList = fuelService.Vehicle_wise_Group_FuelRange_Report(dateRangeFrom,
							dateRangeTo, fuelRange_group, userDetails.getCompany_id());
				}else {
					
					fuelReportList	=	fuelService.Vehicle_wise_Group_FuelRange_ReportWithPartial(dateRangeFrom,
							dateRangeTo, fuelRange_group, userDetails.getCompany_id());
				}
				

				// Report values
				List<FuelDto> fuelReportFilter_withRange = FuBL.Report_Vehicle_wise_Fuel_RangeFuel(VehicleReport,
						fuelReportList, fuelRange);
				// System.out.println(fuelRange);

				
				
				// remove Search vehicle ID without fuel entries vehicle id
				// remove
				String vehicle_in_fuel_id = new String();
				try {
					if (VehicleReport != null && !VehicleReport.isEmpty()) {
						for (Vehicle vehicleID : VehicleReport) {

							// System.out.println("Checking" +
							// vehicleID.getVid());
							// array list to check vehicle ID True are not in
							// Fuel Array liat
							String vehicle = vehicleID.getVid() + "";
							if (fuelReportFilter_withRange.stream()
									.anyMatch(ti -> ti.getVid() == Integer.parseInt(vehicle))) {
								// contains the id

								vehicle_in_fuel_id += vehicleID.getVid() + ",";

							} /*
								 * else { System.out.println("false"); }
								 */
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("fuelmileage_vid:", e);
				}

				if (vehicle_in_fuel_id != null && !vehicle_in_fuel_id.isEmpty()) {
					// vehicle get expert mileage
					List<Vehicle> VehicleShow_vid = vehicleService
							.vehicle_wise_Fuel_mileage_multiple_vehicle(methodRemoveLastComma(vehicle_in_fuel_id));
					// this below code link to business layer to filter with
					// vehicle

					model.put("vehicles", VehicleShow_vid);
					// this model show switch condition which select filter only
					// display
					model.put("fuel", fuelReportFilter_withRange);
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/Report", model);
		}

		return new ModelAndView("FuelRange_Group_Report", model);
	}

	@RequestMapping("/DailyFuelConsumption")
	public ModelAndView DailyFuelConsumption() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("FuelRange_Daily_Consumption", model);
	}
	@RequestMapping(value = "/DailyFuelConsumption", method = RequestMethod.POST)
	public ModelAndView Daily_Fuel_Consumption_Report(@ModelAttribute("command") FuelDto fuelDto,
			BindingResult result) {

		FuelDto fuelReport = RBL.prepareFuel(fuelDto);

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		ValueObject out = null;
		try {
			
			HashMap<String, Object>	 configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			model.put("configuration", configuration);

			String Vehicle_Ownership = "", Vehicle_group = "", Driver_id = "", Vendor_name = "",
					Fuel_vendor_paymode = "", Fuel_from_AND_To = "";
			
			long VehicleGroupId = (long) 0;
			short Vehicle_OwnershipId = 0;
			Integer Vendor_id = 0;
			short Fuel_vendor_paymodeId = 0;
			

			if (fuelReport.getVehicleGroupId() != 0) {
				Vehicle_group = " AND V.vehicleGroupId=" + fuelReport.getVehicleGroupId() + " ";
				VehicleGroupId = fuelReport.getVehicleGroupId();
			}

			if (fuelReport.getVehicle_OwnershipId() > 0) {
				Vehicle_Ownership = " AND  V.vehicleOwnerShipId=" + fuelReport.getVehicle_OwnershipId() + " ";
				Vehicle_OwnershipId = fuelReport.getVehicle_OwnershipId();
			}
			if (fuelReport.getVendor_id() != null && fuelReport.getVendor_id() > 0) {
				Vendor_name = " AND F.vendor_id=" + fuelReport.getVendor_id() + " ";
				Vendor_id =fuelReport.getVendor_id();
			}

			if (fuelReport.getFuel_vendor_paymodeId() > 0) {
				Fuel_vendor_paymode = " AND  F.fuel_vendor_paymodeId=" + fuelReport.getFuel_vendor_paymodeId() + " ";
				Fuel_vendor_paymodeId = fuelReport.getFuel_vendor_paymodeId();
			}

			if (fuelReport.getFuel_to() != "") {
				Fuel_from_AND_To = " AND F.fuel_date = '" + dateFormatSQL.format(dateFormat.parse(fuelReport.getFuel_to()))  + "' ";
			}
			String query = " " + Vehicle_Ownership + " " + Vehicle_group + " " + Driver_id + " " + Vendor_name + " "
					+ Fuel_vendor_paymode + " " + Fuel_from_AND_To + " ";
			

			List<FuelDto>   fuelList	= FuBL.prepareListofFuel(fuelService.listFuelReport(query,out));
			
			if((fuelList == null)||(fuelList.isEmpty())) 
			{
				model.put("NotFound", true); 
				return new ModelAndView("FuelRange_Daily_Consumption", model);
			}
			
			
			model.put("fuel", fuelList);
			
			double fuelTotalVolume = 0;
			double Total = 0;
			
			if(fuelList != null) {
				for (FuelDto dto : fuelList) {
					fuelTotalVolume += dto.getFuel_liters();
					Total			+= dto.getFuel_amount();
				}				
			}
			model.put("fuelTotalVolume", Utility.round(fuelTotalVolume, 2));
			
			DecimalFormat toFixedTwo = new DecimalFormat("#.##");
			String TotalShow = AMOUNTFORMAT.format(Double.parseDouble(toFixedTwo.format(Total)));
			model.put("fuelTotalAmount", TotalShow);

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			if (fuelReport.getFuel_to() != null) {
				java.util.Date date = dateFormatSQL.parse(fuelReport.getFuel_to());

				model.put("VehicleGroupId", VehicleGroupId);
				model.put("Vehicle_OwnershipId", Vehicle_OwnershipId);
				model.put("Vendor_id", Vendor_id);
				model.put("Fuel_vendor_paymodeId", Fuel_vendor_paymodeId);
				
				model.put("SEARCHDATE", fuelReport.getFuel_to());
				model.put("FUEL_DATE_CURRENT", dateFormatonlyDate.format(date));

				Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
				model.put("YESTERDAY", dateFormatSQL.format(yesterday));
				Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
				model.put("TOMORROW", dateFormatSQL.format(tomorrow));

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("FuelRange_Daily_Consumption", model);
	}

	@RequestMapping("/FuelReport")
	public ModelAndView FuelReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		// show the Group service of the driver
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// model.put("vehiclelist",
			// VBL.prepareListofDto(vehicleService.findAllVehiclelist()));
			model.put("vehiclegroup",
					vg.prepareListofDto(vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id())));
			model.put("vehiclefuel", vf.prepareListofDto(vehicleFuelService.findAll()));

		} catch (Exception e) {

		}
		return new ModelAndView("ReportFuel", model);
	}

	@RequestMapping(value = "/FuelReport", method = RequestMethod.POST)
	public ModelAndView FuelReport(@RequestParam("Fuel_daterange") final String fuel_daterange,
			@ModelAttribute("command") FuelDto fuelDto, BindingResult result) {

		FuelDto fuelReport = RBL.prepareFuel(fuelDto);

		Map<String, Object> model = new HashMap<String, Object>();

		String dateRangeFrom = "", dateRangeTo = "";
		ValueObject out = null;
		if (fuel_daterange != "") {

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = fuel_daterange.split(" to ");

				
				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}
			try {

				String Vid = "", Fuel_type = "", Vehicle_Ownership = "", Vehicle_group = "", Driver_id = "",
						Vendor_name = "", Fuel_vendor_paymode = "", Fuel_from_AND_To = "";

				if (fuelReport.getVid() != null) {
					Vid = " AND F.vid=" + fuelReport.getVid() + " ";
				}

				if (fuelReport.getFuelTypeId() > 0) {
					Fuel_type = " AND F.fuelTypeId=" + fuelReport.getFuelTypeId() + " ";
				}

				if (fuelReport.getVehicle_OwnershipId() > 0) {
					Vehicle_Ownership = " AND  V.vehicleOwnerShipId =" + fuelReport.getVehicle_OwnershipId() + " ";
				}

				if (fuelReport.getVehicleGroupId() > 0) {
					Vehicle_group = " AND V.vehicleGroupId=" + fuelReport.getVehicleGroupId() + " ";
				}

				if (fuelReport.getDriver_id() != null) {
					Driver_id = " AND F.driver_id=" + fuelReport.getDriver_id() + " ";
				}

				if (fuelReport.getVendor_name() != "") {
					Vendor_name = " AND F.vendor_id=" + fuelReport.getVendor_name() + " ";
				}

				if (fuelReport.getFuel_vendor_paymodeId() > 0) {
					Fuel_vendor_paymode = " AND  F.fuel_vendor_paymodeId=" + fuelReport.getFuel_vendor_paymodeId()
							+ " ";
				}

				if (dateRangeFrom != "" && dateRangeTo != "") {
					Fuel_from_AND_To = " AND   F.fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' ";
				} else {

					Fuel_from_AND_To = " AND   F.fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' ";
				}

				String query = " " + Vid + " " + Fuel_type + " " + Vehicle_Ownership + " " + Vehicle_group + " "
						+ Driver_id + " " + Vendor_name + " " + Fuel_vendor_paymode + " " + Fuel_from_AND_To + " ";

				model.put("fuel", FuBL.prepareListofFuel(fuelService.listFuelReport(query,out)));

				List<Double> fuelAmount = fuelService.ReportFuelTotalCount(query);
				for (Double Total : fuelAmount) {

					AMOUNTFORMAT.setMaximumFractionDigits(0);
					if (Total == null) {
						Total = 0.0;
					}
					String TotalShow = AMOUNTFORMAT.format(Total);
					model.put("fuelTotalAmount", TotalShow);
					break;
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return new ModelAndView("Fuel_Report", model);
	}
	
	
	@RequestMapping("/VendorWiseFuelMileageReport")
	public ModelAndView VendorWiseFuelMileageReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Vendor_Wise_Fuel_mileage_Report", model);
	}

	@RequestMapping(value = "/VendorWiseFuelMileageReport", method = RequestMethod.POST)
	public ModelAndView VendorWiseFuelMileageReport(
		@ModelAttribute("command") @RequestParam("Vendor_id") final String Vendor_id,
		@RequestParam("fuelmileage_daterange") final String fuelmileage_daterange,
		@RequestParam("vid") final Integer vid,
		final HttpServletRequest request) {
		
		Map<String, Object> 			model 				= null;
		CustomUserDetails 				userDetails			= null;
		HashMap<String, Object>  		configuration		= null;  
		
		try {
			model = new HashMap<String, Object>();
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			
			
			if (fuelmileage_daterange != "" && Vendor_id != "") {
				String dateRangeFrom = "", dateRangeTo = "";
				
				String[] From_TO_DateRange = null;
				try {
					
					From_TO_DateRange = fuelmileage_daterange.split(" to ");
					
					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

					
				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}
				
				try {
					// Report Fuel Vehicle Wise Fuel_Mileage Report select Multiple
					String  Date = "",Vehicle="";
					
					if(vid != 0  ){					
						Vehicle = " AND F.vid = "+ vid +" ";
					}
					Date =  " AND F.fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
					
					String query = " " + Vehicle +" "+Date;
					
					List<FuelDto> fuelReport = fuelService.Vendor_wise_Fuel_Mileage_Report(query,Vendor_id, userDetails.getCompany_id());
					
					model.put("fuel", FuBL.prepareListofFuel(fuelReport));
					Double liter = 0.0, fuelamount = 0.0,totalUsageKm=0.0;
					if (fuelReport != null && !fuelReport.isEmpty()) {
						for (FuelDto fuelBean : fuelReport) {
							liter += fuelBean.getFuel_liters();
							fuelamount += fuelBean.getFuel_amount();
							totalUsageKm+=fuelBean.getFuel_usage();
						}
					}
					
					String Total_AmountALL = AMOUNTFORMAT.format(fuelamount);
					model.put("TotalFuelAmount", Total_AmountALL);
					
					String Total_literALL = AMOUNTFORMAT.format(liter);
					model.put("TotalLiterAmount", Total_literALL);
					
					
					
					if((fuelReport == null)||(fuelReport.isEmpty())) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Vendor_Wise_Fuel_mileage_Report", model);
					}
					
					
					model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				model.put("configuration", configuration);
				
			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}  

		return new ModelAndView("Vendor_Wise_Fuel_mileage_Report", model);
	}
	
	@RequestMapping("/MonthlyVehicleWiseFuelReport")
	public ModelAndView MonthlyVehicleWiseFuelReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("Monthly_Vehicle_Fuel_Report", model);
	}
	@RequestMapping("/AllVehiclesMileageReport")
	public ModelAndView AllVehiclesMileageReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("All_Vehicles_Mileage_Report", model);
	}
	
	@RequestMapping("/FuelEfficiencyDataReport")
	public ModelAndView FuelEfficiencyDataReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("FuelEfficiencyDataReport", model);
	}
	
	@RequestMapping("/creationWiseFuelEntryReport")
	public ModelAndView creationWiseFuelEntryReport() {
		Map<String, Object> model=new HashMap<String, Object>();
		return new 	ModelAndView("creationWiseFuelEntryReport",model);	
	}
	
	
	@RequestMapping("/driverWiseFuelEntryReport")
	public ModelAndView driverWiseFuelEntryReport() {
		HashMap<String, Object> 	configuration		= null;
		CustomUserDetails 			userDetails			= null;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new 	ModelAndView("driverWiseFuelEntryReport",model);	
	}
		

}

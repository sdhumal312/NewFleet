package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.VehicleFuelBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.bl.VendorTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.dto.VendorFixedFuelPriceDto;
import org.fleetopgroup.persistence.model.VendorFixedFuelPrice;
import org.fleetopgroup.persistence.serviceImpl.IVehicleFuelService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class VendorFixedFuelPriceController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVendorService vendorService;

	@Autowired
	private IVehicleFuelService vehicleFuelService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static final long VENDOR_DEFALAT_VALUE = 0;
	public static final Integer VENDOR_DEFALAT_ZERO = 0;
	public static final Double VENDOR_DEFALAT_AMOUNT = 0.0;
	public static final Double VENDOR_DEFALAT_FIXED_QUANTITY = 1.0;

	VendorTypeBL VenType = new VendorTypeBL();
	VendorBL VenBL = new VendorBL();
	VehicleFuelBL vf = new VehicleFuelBL();

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/* show main page of Issues */
	@RequestMapping(value = "/VendorFuelPrice/{vehicleID}/{pageNumber}", method = RequestMethod.GET)
	public String VendorFuelPrice(@PathVariable("vehicleID") final Integer vehicleID,
			@PathVariable("pageNumber") final Integer pageNumber, Model model) throws Exception {

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Page<VendorFixedFuelPrice> page = vendorService.Get_Deployment_Page_VendorFixedFuelPrice(vehicleID,
					pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("VendorCount", page.getTotalElements());

			List<VendorFixedFuelPriceDto> pageList = (vendorService.list_VendorFixedFuelPrice_VehicleID(vehicleID,
					pageNumber, userDetails.getCompany_id()));

			model.addAttribute("vendorFixed", pageList);

			VendorDto vendor = VenBL
					.getVendorDetails(vendorService.getVendorDetails(vehicleID, userDetails.getCompany_id()));

			model.addAttribute("vendor", vendor);

			model.addAttribute("SelectPage", pageNumber);
			if (vendor != null) {
				model.addAttribute("SelectType", vendor.getVendorTypeId());
			}

			model.addAttribute("vehiclefuelPermission", vehicleFuelService.findAll());

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("vendors Page:", e);
			e.printStackTrace();
		}
		return "vendor_Fixed_FuelPrice";
	}

	// Note : This Controller IS Save vendor Fixed Price Details
	@RequestMapping(value = "/saveVendorFuelPrice", method = RequestMethod.POST)
	public ModelAndView saveVendorFuelPrice(@RequestParam("VENDORID") final Integer VENDOR_ID,
			VendorFixedFuelPriceDto VendorFuelPrice, final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (VENDOR_ID != null && VendorFuelPrice.getFUEL_FIXED_DATE() != null && VendorFuelPrice.getFID() != null) {

				VendorFixedFuelPrice FuelPrice = VenBL.prepare_VendorFixedFuelPrice_Bean(VendorFuelPrice);

				VendorFixedFuelPrice ValidateFuel = vendorService.Validate_Vendor_Fixed_Fuel_value(FuelPrice.getFID(),
						FuelPrice.getFUEL_FIXED_DATE(), FuelPrice.getVENDORID(), userDetails.getCompany_id());
				if (ValidateFuel != null) {
					model.put("already", true);
				} else {
					FuelPrice.setCOMPANY_ID(userDetails.getCompany_id());
					// Note : this Save New Vendor Fixed Price Table
					vendorService.ADD_VendorFixed_FuelPrice_IN_DB(FuelPrice);

					// show the driver list in all
					model.put("saveVendor", true);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/VendorFuelPrice/" + VENDOR_ID + "/1.in", model);
	}

	// Note : This Controller IS Save vendor Fixed Price Details
	@RequestMapping(value = "/{vendorID}/{pageNumber}/deleteVendorFuel", method = RequestMethod.GET)
	public ModelAndView deleteVendorFuelPrice(@PathVariable("vendorID") final Integer vendorID,
			@PathVariable("pageNumber") final Integer pageNumber, @RequestParam("VPID") final Long VPID,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			// Note : this Dave New Vendor Fixed Price Table
			vendorService.Delete_VendorFixed_FuelPrice_ID(VPID);
			model.put("delete", true);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/VendorFuelPrice/" + vendorID + "/" + pageNumber + ".in", model);
	}

	@RequestMapping(value = "/getFixedFuelPrice", method = RequestMethod.GET)
	public void getFixedFuelPrice(@RequestParam(value = "Vendor_id", required = true) String Vendor_idAnd_Name,
			@RequestParam(value = "fuel_ID", required = true) Long fuel_ID,
			@RequestParam(value = "fuel_date", required = true) String fuel_date, Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails userDetails = null;
		boolean ValidateOldvendorId = false;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			VendorFixedFuelPrice wadd = new VendorFixedFuelPrice();
			if (Vendor_idAnd_Name != null && FuelDto.getParseVendorID_STRING_TO_ID(Vendor_idAnd_Name) != 0) {
				ValidateOldvendorId = true;
			} else {
				ValidateOldvendorId = false;
			}

			if (ValidateOldvendorId && fuel_date != null && !fuel_date.trim().equalsIgnoreCase("")) {
				
				java.sql.Date FuelDate = null;
				try {
					java.util.Date date = dateFormat.parse(fuel_date);
					FuelDate = new Date(date.getTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
				VendorFixedFuelPrice oddmeter = vendorService.GET_VENDOR_DROPDOWN_FUEL_ADD_FIXED_DETAILS(FuelDto.getParseVendorID_STRING_TO_ID(Vendor_idAnd_Name),
						fuel_ID, FuelDate, userDetails.getCompany_id());

				if (oddmeter != null) {
					wadd.setVFFID(oddmeter.getVFFID());
					wadd.setFUEL_PERDAY_COST(oddmeter.getFUEL_PERDAY_COST());

				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(wadd));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}

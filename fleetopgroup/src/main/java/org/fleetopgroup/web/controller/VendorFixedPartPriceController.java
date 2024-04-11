package org.fleetopgroup.web.controller;
/**
 * @author fleetop
 *
 */

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.bl.VendorTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.dto.VendorFixedPartPriceDto;
import org.fleetopgroup.persistence.model.DriverDocumentHistory;
import org.fleetopgroup.persistence.model.VendorFixedPartPrice;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class VendorFixedPartPriceController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVendorService vendorService;

	/*
	 * @Autowired private IUserProfileService userProfileService;
	 */

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static final long VENDOR_DEFALAT_VALUE = 0;
	public static final Integer VENDOR_DEFALAT_ZERO = 0;
	public static final Double VENDOR_DEFALAT_AMOUNT = 0.0;
	public static final Double VENDOR_DEFALAT_FIXED_QUANTITY = 1.0;

	VendorTypeBL VenType = new VendorTypeBL();
	VendorBL VenBL = new VendorBL();

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/* show main page of Issues */
	@RequestMapping(value = "/VendorPartPrice/{vehicleID}/{pageNumber}", method = RequestMethod.GET)
	public String vendorList(@PathVariable("vehicleID") final Integer vehicleID,
			@PathVariable("pageNumber") final Integer pageNumber, Model model) throws Exception {
		VendorDto		vendor		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendor	= vendorService.getVendorDetails(vehicleID, userDetails.getCompany_id());
			if(vendor != null) {
				Page<VendorFixedPartPrice> page = vendorService.Get_Deployment_Page_VendorFixedPartPrice(vehicleID, pageNumber, userDetails.getCompany_id());
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());
				
				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				
				model.addAttribute("VendorCount", page.getTotalElements());
				
				List<VendorFixedPartPriceDto> pageList = VenBL.prepare_Listof_VendorFixedPartPrice_Bean(
						vendorService.list_VendorFixedPartPrice_VehicleID(vehicleID, pageNumber, userDetails.getCompany_id()));
				
				model.addAttribute("vendorFixed", pageList);
				
				VendorDto vendorDto = VenBL.getVendorDetails(vendor);
				
				model.addAttribute("vendor", vendorDto);
				
				model.addAttribute("SelectPage", pageNumber);
				model.addAttribute("SelectType", vendorDto.getVendorTypeId());
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("vendors Page:", e);
			e.printStackTrace();
		}
		return "vendor_FixedPrice";
	}

	// This Controller is add Vendor Part fixed Price Details
	@RequestMapping(value = "/addVendorPartPrice")
	public ModelAndView addVendorPartPrice(@RequestParam("VENID") final Integer VENDOR_ID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			VendorDto vendor = VenBL.getVendorDetails(vendorService.getVendorDetails(VENDOR_ID, userDetails.getCompany_id()));

			model.put("vendor", vendor);

			model.put("SelectType", vendor.getVendorTypeId());
			model.put("SelectPage", 1);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Add_VendorPartPrice", model);
	}

	// Note : This Controller IS Save vendor Fixed Price Details
	@RequestMapping(value = "/saveVendorPartPrice", method = RequestMethod.POST)
	public ModelAndView saveVendorPartPrice(@RequestParam("VENDORID") final Integer VENDOR_ID,
			@RequestParam("partid") final String[] partid, @RequestParam("parteachcost") final String[] parteachcost,
			@RequestParam("discount") final String[] discount, @RequestParam("tax") final String[] tax,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (VENDOR_ID != null) {
				for (int i = VENDOR_DEFALAT_ZERO; i < partid.length; i++) {

					VendorFixedPartPrice FixedPrice = new VendorFixedPartPrice(Long.parseLong("" + partid[i]),
							VENDOR_DEFALAT_FIXED_QUANTITY, Double.parseDouble("" + parteachcost[i]),
							Double.parseDouble("" + discount[i]), Double.parseDouble("" + tax[i]), userDetails.getCompany_id());

					Double Quantity = 1.0;
					Double eaccost = 0.0;
					Double PartDiscount = 0.0;
					Double GST = 0.0;

					try {
						if (FixedPrice.getPARTEACHCOST() != null) {
							eaccost = FixedPrice.getPARTEACHCOST();
						}
						if (FixedPrice.getPARTDISCOUNT() != null) {
							PartDiscount = FixedPrice.getPARTDISCOUNT();
						}
						if (FixedPrice.getPARTGST() != null) {
							GST = FixedPrice.getPARTGST();
						}
						Double discountCost = 0.0;
						Double TotalCost = 0.0;

						discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (PartDiscount / 100));
						TotalCost = round(((discountCost) + (discountCost * (GST / 100))), 2);

						FixedPrice.setPARTTOTAL(TotalCost);
					} catch (Exception e) {
						e.printStackTrace();
					}

					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					FixedPrice.setCREATEDDATE(toDate);
					FixedPrice.setCREATEDBYID(userDetails.getId());

					FixedPrice.setLASTMODIFIEDBYID(userDetails.getId());
					FixedPrice.setLASTUPDATED(toDate);

					FixedPrice.setVENDORID(VENDOR_ID);

					VendorFixedPartPrice validate = vendorService
							.Validate_Vendor_Fixed_Part_value(Long.parseLong("" + partid[i]), VENDOR_ID, userDetails.getCompany_id());
					if (validate != null) {
						// Note In all Ready Created To Update Save New Price

						FixedPrice.setVPPID(validate.getVPPID());

					}

					// Note : this Save New Vendor Fixed Price Table
					vendorService.ADD_VendorFixedPartPrice_IN_DB(FixedPrice);

					// show the driver list in all
					model.put("saveVendor", true);

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/VendorPartPrice/" + VENDOR_ID + "/1.in", model);
	}

	// Note : This Controller IS Save vendor Fixed Price Details
	@RequestMapping(value = "/{vendorID}/{pageNumber}/deleteVendorPart", method = RequestMethod.GET)
	public ModelAndView deleteVendorPartPrice(@PathVariable("vendorID") final Integer vendorID,
			@PathVariable("pageNumber") final Integer pageNumber, @RequestParam("VPID") final Long VPID,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// Note : this Dave New Vendor Fixed Price Table
			vendorService.Delete_VendorFixedPartPrice_ID(VPID, userDetails.getCompany_id());
			model.put("delete", true);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/VendorPartPrice/" + vendorID + "/" + pageNumber + ".in", model);
	}

	@RequestMapping(value = "/getVendorFixedPricePO", method = RequestMethod.GET)
	public void getVendorFixedPricePO(@RequestParam(value = "PARTID", required = true) Long PART_ID,
			@RequestParam(value = "VENDOTID", required = true) Integer VENDOTID, Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		VendorFixedPartPrice wadd = new VendorFixedPartPrice();
		VendorFixedPartPrice invent = vendorService.Get_VendorFixedPrice_Validate_VendorID_PartId(PART_ID, VENDOTID, userDetails.getCompany_id());
		if (invent != null) {

			wadd.setVPPID(invent.getVPPID());
			wadd.setPARTEACHCOST(invent.getPARTEACHCOST());
			wadd.setPARTDISCOUNT(invent.getPARTDISCOUNT());
			wadd.setPARTGST(invent.getPARTGST());
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(wadd));
	}

	/*
	 *******************************************************************************************************************************************************************	*/
	/* Vendor Document */
	/*
	 *******************************************************************************************************************************************************************	*/

	// This Controller is add Vendor Part fixed Price Details
	@RequestMapping(value = "/addVendorDoc")
	public ModelAndView addVendorDocument(@RequestParam("vendorId") final Integer VENDOR_ID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VendorDto vendor = VenBL.getVendorDetails(vendorService.getVendorDetails(VENDOR_ID, userDetails.getCompany_id()));

			model.put("vendor", vendor);

			model.put("vendorDoc", vendorService.list_VendorDocument_IN_VendorId(VENDOR_ID, userDetails.getCompany_id()));

			model.put("SelectType", vendor.getVendorTypeId());
			model.put("SelectPage", 1);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Add_VendorDocument", model);
	}

	/* save Vendor Document */
	@RequestMapping(value = "/uploadVendorDocument")
	public String uploadDriverDocument() {
		return "uploadVendorDocument";
	}

	@RequestMapping(value = "/uploadVendorDocument", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveVendorDocumentUpload(@ModelAttribute("command") org.fleetopgroup.persistence.document.VendorDocument vendorDoc,
			DriverDocumentHistory dochistory, @RequestParam("input-file-preview") MultipartFile file) {

		Map<String, Object> model = new HashMap<String, Object>();

		if (!file.isEmpty()) {
			try {

				byte[] bytes = file.getBytes();
				vendorDoc.setVENDOR_FILENAME(file.getOriginalFilename());
				vendorDoc.setVENDOR_CONTENT(bytes);
				vendorDoc.setVENDOR_CONTENTTYPE(file.getContentType());
			} catch (IOException e) {

			}
			vendorDoc.setVENDORID(vendorDoc.getVENDORID());

			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			/** Set Status in Issues */
			vendorDoc.setMarkForDelete(false);
			/** Set Created by email Id */

			vendorDoc.setCREATEDBYID(userDetails.getId());
			vendorDoc.setLASTMODIFIEDBYID(userDetails.getId());
			vendorDoc.setCOMPANY_ID(userDetails.getCompany_id());

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			/** Set Created Current Date */
			vendorDoc.setCREATED_DATE(toDate);
			vendorDoc.setLASTUPDATED_DATE(toDate);

			vendorService.saveVendorDocument(vendorDoc);
			// this message alert of show method
			model.put("SaveVenDoc", true);

			return new ModelAndView("redirect:/addVendorDoc.in?vendorId=" + vendorDoc.getVENDORID() + "", model);
		} else {
			// messages
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/addVendorDoc.in?vendorId=" + vendorDoc.getVENDORID() + "", model);

		}

	}

	/* Should be Download Driver Document */
	@RequestMapping("/download/vendorDocument/{vendorDocid}")
	public String download(@PathVariable("vendorDocid") Long documentid, HttpServletResponse response) {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			org.fleetopgroup.persistence.document.VendorDocument file = vendorService.getVendorDocuemnt(documentid, userDetails.getCompany_id());
			if (documentid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				if (file != null) {
					if (file.getVENDOR_CONTENT() != null) {
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getVENDOR_FILENAME() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getVENDOR_CONTENTTYPE());
						response.getOutputStream().write(file.getVENDOR_CONTENT());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (IOException e) {

		}
		return null;
	}

	/* Delete the Driver Document */
	@RequestMapping("/deleteVendorDocument")
	public ModelAndView deleteVendorDocument(@RequestParam("vendorId") final Integer VENDOR_ID,
			@RequestParam("VDID") final Long VDID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (VDID != null) {
			// delete method
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendorService.deleteVendorDocument(VDID, userDetails.getCompany_id());
			// this message alert of show method
			model.put("deleteDocument", true);
			return new ModelAndView("redirect:/addVendorDoc.in?vendorId=" + VENDOR_ID + "", model);
		} else {
			// messages
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/addVendorDoc.in?vendorId=" + VENDOR_ID + "", model);
		}
	}

}
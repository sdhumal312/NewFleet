package org.fleetopgroup.web.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PurchaseOrderType;
import org.fleetopgroup.persistence.bl.PurchaseOrdersBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.PurchaseOrdersDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToPartsDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
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
public class PurchaseOrderReportController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IPurchaseOrdersService PurchaseOrdersService;

	@Autowired
	private IUserProfileService userProfileService;

	PurchaseOrdersBL POBL = new PurchaseOrdersBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();

	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping("/POR")
	public ModelAndView PurchaseOrderReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("POR", model);
	}
	
	
	/*Vendor Wise Purchase Order Report Module Controller by Dev Yogi Starting*/
	@RequestMapping("/VendorPurchaseReport")
	public ModelAndView VendorPurchaseReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Vendor_Wise_PurchaseOrder_Report", model);
	}
	/*Vendor Wise Purchase Order Report Module Controller by Dev Yogi Ending*/
	
	

	@RequestMapping(value = "/VendorPurchaseReport", method = RequestMethod.POST)
	public ModelAndView VendorPurchaseReport(
			@ModelAttribute("command") @RequestParam("PURCHASE_TYPE") final short PURCHASE_TYPE,
			@RequestParam("PURCHASE_VENDOR") final String PURCHASE_VENDOR,
			@RequestParam("PURCHASE_DATERANGE") final String PURCHASE_DATERANGE, 
			@RequestParam("INVOICE_NO") final String INVOICE_NO, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<PurchaseOrdersDto> vendorWisePOReportList=null; //No Record Found Validation  Logic Y
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {

			if (PURCHASE_DATERANGE != "" && PURCHASE_VENDOR != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = PURCHASE_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String type = "", vendor = "", PurchaseQuery = "" , invoice = "";

					if (PURCHASE_VENDOR != "") {
						vendor = "AND p.purchaseorder_vendor_id =" + PURCHASE_VENDOR + " ";
					}
					if (PURCHASE_TYPE > 0) {
						type = "AND p.purchaseorder_typeId =" + PURCHASE_TYPE + " ";
					}
					
					if (INVOICE_NO != null && INVOICE_NO != "") {
						invoice = "AND p.purchaseorder_invoiceno LIKE '%" + INVOICE_NO + "%' ";
					}

					PurchaseQuery = type + " " + vendor + " " +invoice+" ";

					List<PurchaseOrdersDto> Purchase = PurchaseOrdersService.Report_PurchaseOrders(PurchaseQuery,
							dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					
					
					vendorWisePOReportList=  POBL.getReport_ListofPurchaseOrders(Purchase);
					//No Record Found Validation  Logic Start Y
					if(vendorWisePOReportList == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Vendor_Wise_PurchaseOrder_Report", model);
					}
					//No Record Found Validation  Logic End  Y
					
					model.put("PurchaseOrder",vendorWisePOReportList);
					//model.put("PurchaseOrder", POBL.getReport_ListofPurchaseOrders(Purchase)); //Original Code Before No Record Found

					// RenewalReminder Total Amount Sum Query
					model.put("TotalAmount", round(RRBL.Total_PurchaseOrder_Amount(Purchase), 2));

					model.put("TotalBalanceAmount", round(RRBL.Total_PurchaseOrder_Balance_Amount(Purchase), 2));

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

		return new ModelAndView("Vendor_Wise_PurchaseOrder_Report", model);
	}

	@RequestMapping(value = "/VendorPurchasePartReport", method = RequestMethod.POST)
	public ModelAndView VendorPurchasePartReport(@RequestParam("PURCHASE_VENDOR") final String PURCHASE_VENDOR,
			@RequestParam("PURCHASE_DATERANGE") final String PURCHASE_DATERANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {

			if (PURCHASE_DATERANGE != "" && PURCHASE_VENDOR != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = PURCHASE_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String vendor = "", PurchaseQuery = "";

					if (PURCHASE_VENDOR != "") {
						vendor = "AND PO.purchaseorder_vendor_id =" + PURCHASE_VENDOR + " ";
					}
					PurchaseQuery = " " + vendor + " ";

					List<PurchaseOrdersToPartsDto> PurchasePart = PurchaseOrdersService
							.ReportPart_PurchaseOrdersPartsToPurchaseOrdersId(
									PurchaseOrderType.PURCHASEORDER_TYPE_PART_PO, PurchaseQuery, dateRangeFrom,
									dateRangeTo, userDetails.getCompany_id());
					model.put("PurchaseOrderPart", PurchasePart);

					model.put("TotalAmount", round(RRBL.Total_PurchaseOrderToParts_Amount(PurchasePart), 2));

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

		return new ModelAndView("Vendor_Wise_PurchaseOrder_Part_Report", model);
	}
	
	
	@RequestMapping("/DatePurchaseReport")
	public ModelAndView DatePurchaseReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Date_Range_Wise_PurchaseOrder_Report", model);
	}
	
	

	@RequestMapping(value = "/DatePurchaseReport", method = RequestMethod.POST)
	public ModelAndView DatePurchaseReport(
			@ModelAttribute("command") @RequestParam("PURCHASE_TYPE") final short PURCHASE_TYPE,
			@RequestParam("PURCHASE_DATERANGE") final String PURCHASE_DATERANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<PurchaseOrdersDto> dateRangeWisePOReportList=null; //No Record Found Validation  Logic Y
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {

			if (PURCHASE_DATERANGE != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = PURCHASE_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String type = "", vendor = "", PurchaseQuery = "";

					if (PURCHASE_TYPE > 0) {
						type = "AND p.purchaseorder_typeId =" + PURCHASE_TYPE + " ";
					}

					PurchaseQuery = type + " " + vendor + " ";

					List<PurchaseOrdersDto> Purchase = PurchaseOrdersService.Report_PurchaseOrders(PurchaseQuery,
							dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					
					dateRangeWisePOReportList= POBL.getReport_ListofPurchaseOrders(Purchase);
					//No Record Found Validation  Logic Start Y
					if(dateRangeWisePOReportList == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Date_Range_Wise_PurchaseOrder_Report", model);
					}
					//No Record Found Validation  Logic End  Y
					
					
					model.put("PurchaseOrder",dateRangeWisePOReportList );					
					//model.put("PurchaseOrder", POBL.getReport_ListofPurchaseOrders(Purchase)); //Original Code Before No Record Found

					// RenewalReminder Total Amount Sum Query
					model.put("TotalAmount", round(RRBL.Total_PurchaseOrder_Amount(Purchase), 2));

					model.put("TotalBalanceAmount", round(RRBL.Total_PurchaseOrder_Balance_Amount(Purchase), 2));

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

		return new ModelAndView("Date_Range_Wise_PurchaseOrder_Report", model);
	}

	/* Purchase Total Parts Report Code By Dev Yogi Starting*/
	@RequestMapping("/PartConsumingReport")
	public ModelAndView PartConsumingReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Part_Consuming_Report", model);
	}	
	/* Purchase Total Parts Report Code By Dev Yogi Ending */
	
	@RequestMapping(value = "/PartConsumingReport", method = RequestMethod.POST)
	public ModelAndView PartConsumingReport(
			@ModelAttribute("command") @RequestParam("PURCHASE_DATERANGE") final String PURCHASE_DATERANGE,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		
		List<PurchaseOrdersToPartsDto> purchaseTotalPartsReportList=null; //No Record Found Validation  Logic Y
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {

			if (PURCHASE_DATERANGE != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = PURCHASE_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {

					List<PurchaseOrdersToPartsDto> Purchase = PurchaseOrdersService.Part_consuming_Report(dateRangeFrom,
							dateRangeTo, userDetails.getCompany_id());
					
					purchaseTotalPartsReportList= POBL.prepare_Part_consuming_Report(Purchase);
					//No Record Found Validation  Logic Start Y
					if(purchaseTotalPartsReportList == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Part_Consuming_Report", model);
					}
					//No Record Found Validation  Logic End  Y
					
					
					model.put("POPart",purchaseTotalPartsReportList );					
					//model.put("POPart", POBL.prepare_Part_consuming_Report(Purchase)); //Original Code Before No Record Found

					model.put("PartDate", PURCHASE_DATERANGE);

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

		return new ModelAndView("Part_Consuming_Report", model);
	}

	@RequestMapping(value = "/ReportPurchaseOrder", method = RequestMethod.POST)
	public ModelAndView PurchaseOrderReport(@RequestParam("Purchase_daterange") final String purchaseRange_daterange,
			@ModelAttribute("command") PurchaseOrdersDto purchaseOrders, BindingResult result) {

		// PurchaseOrders POReport = RBL.preparePurchaseOrder(purchaseOrders);

		Map<String, Object> model = new HashMap<String, Object>();
		String dateRangeFrom = "", dateRangeTo = "";
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (purchaseRange_daterange != "") {

			String[] From_TO_DateRange = null;
			try {

				From_TO_DateRange = purchaseRange_daterange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}
			try {
				String quoteNum = "", vendor = "", buyer = "", terms = "", shipvia = "", shipto = "",
						PurchaseQuery = "";

				if (purchaseOrders.getPurchaseorder_quotenumber() != "") {
					quoteNum = "AND p.purchaseorder_quotenumber ='" + purchaseOrders.getPurchaseorder_quotenumber()
							+ "' ";
				}
				if (purchaseOrders.getPurchaseorder_vendor_name() != "") {
					vendor = "AND p.purchaseorder_vendor_id =" + purchaseOrders.getPurchaseorder_vendor_name() + " ";
				}
				if (purchaseOrders.getPurchaseorder_buyer() != "") {
					buyer = "AND C.company_name ='" + purchaseOrders.getPurchaseorder_buyer() + "' ";
				}
				if (purchaseOrders.getPurchaseorder_termsId() > 0) {
					terms = "AND p.purchaseorder_termsId =" + purchaseOrders.getPurchaseorder_termsId() + " ";
				}
				if (purchaseOrders.getPurchaseorder_shipviaId() > 0) {
					shipvia = "AND p.purchaseorder_shipviaId =" + purchaseOrders.getPurchaseorder_shipviaId() + " ";
				}
				if (purchaseOrders.getPurchaseorder_shiplocation_id() != null) {
					shipto = "AND p.purchaseorder_shiplocation_id =" + purchaseOrders.getPurchaseorder_shiplocation_id()
							+ " ";
				}

				PurchaseQuery = quoteNum + " " + vendor + " " + buyer + " " + terms + " " + shipvia + " " + shipto + "";
				model.put("PurchaseOrder",
						POBL.getListofPurchaseOrders(PurchaseOrdersService.Report_PurchaseOrders(PurchaseQuery,
								dateRangeFrom, dateRangeTo, userDetails.getCompany_id())));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("PurchaseOrder_Report", model);
	}

	@RequestMapping("/PurchaseOrderReport")
	public ModelAndView PurchaseOrderReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

		} catch (Exception e) {

		}
		return new ModelAndView("ReportPurchaseOrder", model);
	}
	
	@RequestMapping("/PurchaseOrderStatusWise_Report")
	public ModelAndView PurchaseOrder_Report() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("PurchaseOrder_Status_Wise_Report", model);
	}	
}

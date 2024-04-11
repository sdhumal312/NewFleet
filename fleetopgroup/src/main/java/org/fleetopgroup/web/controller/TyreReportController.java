package org.fleetopgroup.web.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.InventoryTyreInvoiceBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
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
public class TyreReportController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IInventoryTyreService iventoryTyreService;
	InventoryTyreInvoiceBL ITBL = new InventoryTyreInvoiceBL();
	
	@Autowired
	private IUserProfileService userProfileService;
	@Autowired private ICompanyConfigurationService 							companyConfigurationService;
	
	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");

	@RequestMapping("/TR")
	public ModelAndView TyreReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("TR", model);
	}
	
	@RequestMapping(value = "/TyrePurchaseReport", method = RequestMethod.POST)
	public ModelAndView TyrePurchaseReport(
			@ModelAttribute("command") @RequestParam("TYRE_MODEL_ID") final Integer TYRE_MODEL,
			@RequestParam("STATUS_OF_TYRE") final Short STATUS_OF_TYRE,
			@RequestParam("TYRE_SIZE_ID") final Integer TYRE_SIZE,@RequestParam("WAREHOUSE_LOCATION_ID") final Integer WAREHOUSE_LOCATION,
			@RequestParam("Vendor_id")  final Integer Vendor_id,
			@RequestParam("TyrePurchase_daterange") final String TyrePurchase_daterange,
			final HttpServletRequest request) {

		// PurchaseOrders POReport = RBL.preparePurchaseOrder(purchaseOrders);

		Map<String, Object> model = new HashMap<String, Object>();
		List<InventoryTyreInvoiceDto> tyrePurchaseReportList=null; //No Record Found Validation  Logic Y
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			String MODEL = "", SIZE = "", wareHouseLocation="", vendor = "", statusOfTyre = "";

			if (TyrePurchase_daterange != "" && TyrePurchase_daterange != " ") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TyrePurchase_daterange.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}
				if (STATUS_OF_TYRE != null && STATUS_OF_TYRE >= 0) {
					statusOfTyre = "AND R.STATUS_OF_TYRE = "+STATUS_OF_TYRE+"";
				}				
				if (TYRE_MODEL != null && TYRE_MODEL > 0) {
					MODEL = "AND R.ITYRE_ID  IN ( SELECT A.InventoryTyreInvoice FROM InventoryTyreAmount as A WHERE A.TYRE_MODEL_ID="
							+ TYRE_MODEL + " AND A.COMPANY_ID = "+userDetails.getCompany_id()+"  AND A.markForDelete = 0)  ";
				}
				if (TYRE_SIZE != null && TYRE_SIZE > 0) {
					SIZE = "AND R.ITYRE_ID  IN ( SELECT A.InventoryTyreInvoice FROM InventoryTyreAmount as A WHERE A.TYRE_SIZE_ID ="
							+ TYRE_SIZE + " AND A.COMPANY_ID = "+userDetails.getCompany_id()+"  AND A.markForDelete = 0)";
				}
				if(WAREHOUSE_LOCATION != null && WAREHOUSE_LOCATION > 0) {
					wareHouseLocation = " AND R.WAREHOUSE_LOCATION_ID = "+WAREHOUSE_LOCATION+"";
				}
				if(Vendor_id != null && Vendor_id > 0) {
					vendor ="AND R.VENDOR_ID = "+Vendor_id+"";
				}			
				String Query = MODEL + " " + SIZE + " " + wareHouseLocation + " " +vendor+" "+statusOfTyre+" AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ";
				
				
				List<InventoryTyreInvoiceDto> Mult_Invoice_TyreID = iventoryTyreService.Tyre_Purchase_Report(dateRangeFrom,
						dateRangeTo, Query);
				
				
				tyrePurchaseReportList= ITBL.getInventoryTyreInvoice_list(Mult_Invoice_TyreID);
				//No Record Found Validation  Logic Start Y
				if(tyrePurchaseReportList == null) 
				{
					model.put("NotFound", true); 
					return new ModelAndView("Tyre_PurchaseReport_Report", model);
				}
				//No Record Found Validation  Logic End  Y			
				
				model.put("InventoryTyreInvoice",tyrePurchaseReportList );

				//model.put("InventoryTyreInvoice", ITBL.getInventoryTyreInvoice_list(Mult_Invoice_TyreID)); //Original Code Before No Record Found

				String Tyre_idALL = "";
				if (Mult_Invoice_TyreID != null && !Mult_Invoice_TyreID.isEmpty()) {
					int i = 1;
					// System.out.println(Mult_vehicle_workorder.size());
					for (InventoryTyreInvoiceDto Tyre_INVOICE_ID : Mult_Invoice_TyreID) {
						if (i != Mult_Invoice_TyreID.size()) {
							Tyre_idALL += Tyre_INVOICE_ID.getITYRE_ID() + ",";
						} else {
							Tyre_idALL += Tyre_INVOICE_ID.getITYRE_ID() + "";
						}
						i++;
					}
				}
				Double Total_Tyre = 0.0;

				if (Tyre_idALL != null && Tyre_idALL != "") {
					model.put("InventoryTyreAmount", iventoryTyreService.Tyre_Purchase_Report_TyreAmount(Tyre_idALL, userDetails.getCompany_id()));
					model.put("Tyre", iventoryTyreService.Tyre_Purchase_Report_InventoryTyre(Tyre_idALL, userDetails.getCompany_id()));

					// Report Search WorkOrderTask Total amount Multiple
					// Vehicle
					// to workOrders_ID
					List<Double> Tyre_TotalALL = iventoryTyreService
							.Tyre_Purchase_Report_InventoryTyre_TotalAmount(Tyre_idALL, userDetails.getCompany_id());

					for (Double TotalAmountALL : Tyre_TotalALL) {
						// count WorkOrder Total
						Total_Tyre += TotalAmountALL;
						AMOUNTFORMAT.setMaximumFractionDigits(0);
						if (TotalAmountALL == null) {
							TotalAmountALL = 0.0;
						}
						String TotalIncomeShowALL = AMOUNTFORMAT.format(TotalAmountALL);
						model.put("TotalTyreAmount", TotalIncomeShowALL);
						break;
					}
				}
				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Tyre_PurchaseReport_Report", model);

	}
	/* Tyre Stock Report Code By Dev Yogi  Starting*/
	@RequestMapping("/TyreStockReport")
	public ModelAndView TyreStockReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>    	config						= null;
		
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			config	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_REPORT_CONFIGURATION_CONFIG);
			model.put("configuration", config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//model.put("UserDetails", userDetails);
		return new ModelAndView("Tyre_Stock_Report", model);
		
	}	
	/* Tyre Stock Report Code By Dev Yogi Ending */	
	
	
	@RequestMapping(value = "/TyreStockReport", method = RequestMethod.POST)
	public ModelAndView TyreStockReport(@ModelAttribute("command") InventoryTyre invTyre,
			final HttpServletRequest request) {

		// PurchaseOrders POReport = RBL.preparePurchaseOrder(purchaseOrders);

		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>    	config						= null;
		
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
				config	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_REPORT_CONFIGURATION_CONFIG);
				model.put("configuration", config);
			
			String TYRE_MANUFACTURER = "", TYRE_MODEL = "", TYRE_SIZE = "", WAREHOUSE_LOCATION = "",
					TYRE_ASSIGN_STATUS = "", TYRE_RETREAD_COUNT = "", VEHICLE_NO = " ";
			
			if (invTyre != null) {

				if (invTyre.getTYRE_MANUFACTURER_ID() != null && invTyre.getTYRE_MANUFACTURER_ID() > 0) {
					TYRE_MANUFACTURER = "AND R.TYRE_MANUFACTURER_ID =" + invTyre.getTYRE_MANUFACTURER_ID() + " ";
				}
				if (invTyre.getTYRE_MODEL_ID() != null && invTyre.getTYRE_MODEL_ID() > 0) {
					TYRE_MODEL = "AND R.TYRE_MODEL_ID  =" + invTyre.getTYRE_MODEL_ID() + "";
				}
				if (invTyre.getTYRE_SIZE_ID() != null && invTyre.getTYRE_SIZE_ID() > 0) {
					TYRE_SIZE = "AND R.TYRE_SIZE_ID  =" + invTyre.getTYRE_SIZE_ID() + "";
				}
				if (invTyre.getWAREHOUSE_LOCATION_ID() != null && invTyre.getWAREHOUSE_LOCATION_ID() > 0) {
					WAREHOUSE_LOCATION = "AND R.WAREHOUSE_LOCATION_ID  =" + invTyre.getWAREHOUSE_LOCATION_ID() + "";
				}
				if (invTyre.getTYRE_ASSIGN_STATUS_ID() > 0) {
					TYRE_ASSIGN_STATUS = "AND R.TYRE_ASSIGN_STATUS_ID  =" + invTyre.getTYRE_ASSIGN_STATUS_ID() + "";
				}
				if (invTyre.getTYRE_RETREAD_COUNT() != null && invTyre.getTYRE_RETREAD_COUNT() >= 0) {
					TYRE_RETREAD_COUNT = "AND R.TYRE_RETREAD_COUNT  ='" + invTyre.getTYRE_RETREAD_COUNT() + "'";
				}
				if(invTyre.getVEHICLE_NO() != null && invTyre.getVEHICLE_NO() >= 0)
				{
					VEHICLE_NO = " AND R.VEHICLE_ID = '"+ invTyre.getVEHICLE_NO()+ "'";
				}
				
				String Query = TYRE_MANUFACTURER + " " + TYRE_MODEL + " " + TYRE_SIZE + " " + WAREHOUSE_LOCATION + " "
						+ TYRE_ASSIGN_STATUS + " " + TYRE_RETREAD_COUNT + " " + VEHICLE_NO + " " + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" ";
				List<InventoryTyreDto> pageList = iventoryTyreService.Tyre_Stock_Report(Query);

				
				if((pageList == null) || (pageList.isEmpty())) 
				{
					model.put("NotFound", true); 
					return new ModelAndView("Tyre_Stock_Report", model);
				}
				
				
				
				model.put("Tyre", pageList);
			
				//model.put("Tyre1", ITBL.Show_InventoryTyre_Details(inventoryTyreDto));


				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Tyre_Stock_Report", model);

	}
	
	/*  Tyre Retread Report Code Modification By Dev Yogi Starting  */
	@RequestMapping("/TyreRetreadStockReport")
	public ModelAndView TyreRetreadStockReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("TyreRetread_Stock_Report", model);
	}	
	/*  Tyre Retread Report Code Modification By Dev Yogi  Ending */
	
	
	
	

	@RequestMapping(value = "/TyreRetreadStockReport", method = RequestMethod.POST)
	public ModelAndView TyreRetreadStockReport(
			@ModelAttribute("command") @RequestParam("TYRE_STATUS") final short TYRE_STATUS,
			@RequestParam("RETREAD_STATUS") final short RETREAD_STATUS,
			@RequestParam("TR_VENDOR_ID") final Integer TR_VENDOR_ID, @RequestParam("WAREHOUSE_LOCATION_ID") final Integer WAREHOUSE_LOCATION_ID,
			@RequestParam("TyreRetread_daterange") final String TyreRetread_daterange,
			final HttpServletRequest request) {

		// PurchaseOrders POReport = RBL.preparePurchaseOrder(purchaseOrders);

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			String STATUS = "", RETREAD = "", VENDOR = "", DATE_RANGE = "", WAREHOUSE_LOCATION = "";

			if (TyreRetread_daterange != "" && TyreRetread_daterange != " ") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TyreRetread_daterange.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				if (TYRE_STATUS > 0) {
					STATUS = "AND R.TYRE_ASSIGN_STATUS_ID =" + TYRE_STATUS + "";
				}
				if (RETREAD_STATUS > 0) {
					RETREAD = "AND R.TYRE_ID  IN ( SELECT A.TYRE_ID FROM InventoryTyreRetreadAmount as A WHERE A.TRA_STATUS_ID="
							+ RETREAD_STATUS + " AND A.COMPANY_ID = "+userDetails.getCompany_id()+" AND A.markForDelete = 0 )  ";
				}
				if (TR_VENDOR_ID != null) {
					VENDOR = "AND R.TYRE_ID  IN ( SELECT A.TYRE_ID FROM InventoryTyreRetreadAmount as A WHERE A.inventoryTyreRetread IN ( SELECT AR.TRID FROM InventoryTyreRetread as AR WHERE AR.TR_VENDOR_ID="
							+ TR_VENDOR_ID + " AND AR.COMPANY_ID = "+userDetails.getCompany_id()+" AND AR.markForDelete = 0 ) AND A.COMPANY_ID = "+userDetails.getCompany_id()+" AND A.markForDelete = 0 )";
				}

				if (dateRangeFrom != "" && dateRangeTo != "") {
					DATE_RANGE = "AND R.TYRE_ID  IN ( SELECT A.TYRE_ID FROM InventoryTyreRetreadAmount as A WHERE A.inventoryTyreRetread IN ( SELECT AR.TRID FROM InventoryTyreRetread as AR WHERE AR.TR_OPEN_DATE BETWEEN '"
							+ dateRangeFrom + "' AND '" + dateRangeTo + "' AND AR.COMPANY_ID = "+userDetails.getCompany_id()+" AND AR.markForDelete = 0 ) AND A.COMPANY_ID = "+userDetails.getCompany_id()+" AND A.markForDelete = 0)";
				}
				if(WAREHOUSE_LOCATION_ID != null && WAREHOUSE_LOCATION_ID > 0) {
					WAREHOUSE_LOCATION = " AND R.WAREHOUSE_LOCATION_ID  =" + WAREHOUSE_LOCATION_ID + "";
				}
				model.put("SearchFrom", "   -  " + RETREAD_STATUS + " " + TyreRetread_daterange + " ");

				String Query = DATE_RANGE + " " + STATUS + " " + RETREAD + " " + VENDOR + " " +WAREHOUSE_LOCATION+ " AND  R.COMPANY_ID = "+userDetails.getCompany_id()+"";
			
				
				List<InventoryTyreDto> pageList = iventoryTyreService.Tyre_Stock_Report(Query);
				
				
				if((pageList == null)||(pageList.isEmpty())) 
				{
					model.put("NotFound", true); 
					return new ModelAndView("TyreRetread_Stock_Report", model);
				}
				
				
				
				model.put("Tyre", pageList);


				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("TyreRetread_Stock_Report", model);

	}
	
	@RequestMapping("/TyreTransferedReport")
	public ModelAndView TyreTransferedReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("TyreTransferedReport", model);
	}
	
	@RequestMapping("/TyreSentForRethreadingReport")
	public ModelAndView TyreSentForRethreadingReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("TyreSentForRethreadingReport", model);
	}
	
	@RequestMapping("/TyreRetreadInvoiceReport")
	public ModelAndView TyreRetreadInvoiceReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("TyreRetreadInvoiceReport", model);
	}
	
}

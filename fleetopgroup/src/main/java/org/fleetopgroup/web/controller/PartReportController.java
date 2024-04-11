package org.fleetopgroup.web.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.MasterPartsBL;
import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.bl.PartManufacturerBL;
import org.fleetopgroup.persistence.bl.PurchaseOrdersBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.ReportBL;
import org.fleetopgroup.persistence.bl.ServiceEntriesBL;
import org.fleetopgroup.persistence.bl.WorkOrdersBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryTransferDto;
import org.fleetopgroup.persistence.dto.MasterPartsSearchDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IPartManufacturerService;
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
public class PartReportController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IInventoryService InventoryService;
	
	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private IPartCategoriesService PartCategoriesService;
	
	@Autowired	ICompanyConfigurationService	companyConfigurationService;
	
	
	WorkOrdersBL WOBL = new WorkOrdersBL();
	
	
	ServiceEntriesBL SEBL = new ServiceEntriesBL();
	
	
	@Autowired
	private IMasterPartsService MasterPartsService;
	MasterPartsBL MPBL = new MasterPartsBL();

	PartCategoriesBL PCBL = new PartCategoriesBL();
	PurchaseOrdersBL POBL = new PurchaseOrdersBL();

	@Autowired
	private IPartManufacturerService PartManufacturerService;
	PartManufacturerBL PMBL = new PartManufacturerBL();
	InventoryBL InBL = new InventoryBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();
	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");
	InventoryBL INVBL = new InventoryBL();
	ReportBL RBL = new ReportBL();
	

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}


	@RequestMapping("/PR")
	public ModelAndView PartReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("PR", model);
	}
	
	/* Part Stock Report Code Manipulations By Dev Yogi Starting */
	@RequestMapping("/PartStockReport")
	public ModelAndView PartStockReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Part_Stock_Report", model);
	}	
	/* Part Stock Report Code Manipulations By Dev Yogi Ending */
	
	// This controller has chnaged now On ajax
/*	@RequestMapping(value = "/PartStockReport", method = RequestMethod.POST)
	public ModelAndView PartStockReport(@RequestParam("locationId") Integer partLocation,@RequestParam("multipleParts") final String[] multiplePartIds, final HttpServletRequest request) {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails			userDetails			= null;
		List<InventoryLocationDto> 	Inven				= null;
		List<InventoryLocationDto> 	finalInven			= null;
		String 						partids 			= null;
		String 						query				= null;
		String						Inventoryquery 		= null;
		String 						TotalValue 			= null;
		
		try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				finalInven = new ArrayList<InventoryLocationDto>();
				
				if(multiplePartIds !=  null) {
					for(String dto :multiplePartIds) {
						 partids = dto;
						 if (partLocation != null && partids != null ) {
							 query 				= " R.location_quantity > 0.0 AND R.partid = " + partids + " AND R.locationId = " + partLocation + " ";
							 Inventoryquery 	= " R.quantity > 0.0 AND R.partid =  " + partids + " AND R.locationId =  " + partLocation + " ";
							}else if(partids != null && partLocation == null) {
								query 			= " R.location_quantity > 0.0 AND R.partid = " + partids + "  ";
								Inventoryquery 	= " R.quantity > 0.0 AND R.partid =  " + partids + " ";
							}
						
						 		Inven 			= InventoryService.Report_list_Of_Location_Inventory(query, userDetails.getCompany_id());
							if(Inven != null) {
							 finalInven.addAll(Inven);
							}
						
						 Double Total 	= InventoryService.Report_list_Of_Location_Inventory_Total_Inventory_Amount(Inventoryquery, userDetails.getCompany_id());
						 TotalValue 	= AMOUNTFORMAT.format(round(Total, 0));
						
					}
					if(partLocation == null && partids == null) {
						query 			= " R.location_quantity > 0.0 ";
						Inventoryquery 	= " R.quantity > 0.0 ";	
						
						Inven = InventoryService.Report_list_Of_Location_Inventory(query, userDetails.getCompany_id());
						finalInven.addAll(Inven);
						
						Double Total 	= InventoryService.Report_list_Of_Location_Inventory_Total_Inventory_Amount(Inventoryquery, userDetails.getCompany_id());
						TotalValue 		= AMOUNTFORMAT.format(round(Total, 0));
					}else if(partLocation != null && partids == null){
						query 			= " R.location_quantity > 0.0 AND R.locationId = " + partLocation + "  ";
						Inventoryquery 	= " R.quantity > 0.0 AND R.locationId =  " + partLocation + " ";
						
						Inven = InventoryService.Report_list_Of_Location_Inventory(query, userDetails.getCompany_id());
						finalInven.addAll(Inven);
						
						Double Total 	= InventoryService.Report_list_Of_Location_Inventory_Total_Inventory_Amount(Inventoryquery, userDetails.getCompany_id());
						TotalValue 		= AMOUNTFORMAT.format(round(Total, 0));
					}

					model.put("InventoryLocation", finalInven);
					model.put("TotalQuantity", round(RRBL.Total_PartInventoryLocation_Quantity(finalInven), 2));
					model.put("TotalLiter", round(RRBL.Total_PartInventoryLocation_Liter(finalInven), 2));
					model.put("TotalValue", TotalValue);
					model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
				}
				
				if((Inven == null) || (Inven.isEmpty())) 
				{
					model.put("NotFound", true); 
					return new ModelAndView("Part_Stock_Report", model);
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Part_Stock_Report", model);
	}*/
	
	/* Date Wise Inventory Stock Report Code By Dev Yogi  Start */
	@RequestMapping("/DateWiseInventoryStockReport")
	public ModelAndView DateWiseInventoryStockReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Date_WiseInventory_Stock_Report", model);
	}	
	/* Date Wise Inventory Stock Report Code By Dev Yogi  end */
	
	@RequestMapping(value = "/DateWiseInventoryStockReport", method = RequestMethod.POST)
	public ModelAndView DateWiseInventoryStockReport(@RequestParam("PART_DATERANGE") final String PART_DATERANGE,
			@ModelAttribute("command") Inventory partLocation, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			if (PART_DATERANGE != null && PART_DATERANGE != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				model.put("SEARCHDATE", PART_DATERANGE);

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = PART_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("Renewal Reminder:", e);
				}
				try {
					String vendor = "", location = "";

					if (partLocation.getLocationId() != null && partLocation.getLocationId() > 0) {
						location = "AND I.locationId =" + partLocation.getLocationId() + "";
					}

					if (partLocation.getVendor_id() != null && partLocation.getVendor_id() > 0) {
						vendor = "AND I.vendor_id =" + partLocation.getVendor_id() + "";
					}

					String query = " AND I.invoice_date between '" + dateRangeFrom + "' AND '" + dateRangeTo + "'  "
							+ vendor + " " + location + " ";

					List<InventoryDto> Inven = InventoryService
							.Report_Part_Inventory_Stock_Invoice_DateRange_Report(query, userDetails.getCompany_id());
					
					if((Inven == null) || (Inven.isEmpty())) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Date_WiseInventory_Stock_Report", model);
					}
					
					
					
					model.put("Inventory", (Inven));

					Double[] total = RRBL.Total_Part_AVAILABLE_Inventory_Quantity(Inven);
					// RenewalReminder Total Amount Sum Query
					model.put("TotalQuantity", total[0]);
					model.put("TotalHistoryQuantity", total[1]);

					Double Total = InventoryService.Report_Part_Inventory_Stock_Invoice_DateRange_Report_Amount(query);

					String TotalValue = AMOUNTFORMAT.format(round(Total, 0));
					model.put("TotalValue", TotalValue);

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

		return new ModelAndView("Date_WiseInventory_Stock_Report", model);
	}

	/*Part Purchase Report Code By Dev Yogi*/
	@RequestMapping("/PartPurchaseReport")
	public ModelAndView PartPurchaseReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Part_Purcahse_Report", model);
	}	
	/*Part Purchase Report Code By Dev Yogi*/
	
	
	
	@RequestMapping(value = "/PartPurchaseReport", method = RequestMethod.POST)
	public ModelAndView PartPurchaseReport(
			@ModelAttribute("command") @RequestParam("PART_RANGE_DATE") final String PART_RANGE_DATE, InventoryDto part,
			final HttpServletRequest request) {

		InventoryDto location = RBL.prepareInventory(part);

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (PART_RANGE_DATE != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = PART_RANGE_DATE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String partid = "", Warehouselocation = "", vendor = "", Invoice_date = "";

					if (location.getPartid() != null) {
						partid = " AND I.partid = " + location.getPartid() + "";
					}

					if (location.getLocation() != "") {
						Warehouselocation = " AND I.locationId = " + location.getLocation() + "";
					}
					if (location.getVendor_id() != null) {
						vendor = " AND I.vendor_id = " + location.getVendor_id() + "";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						Invoice_date = " AND I.invoice_date between '" + dateRangeFrom + "' AND '" + dateRangeTo
								+ "' ";

						String query = "" + partid + " " + Warehouselocation + " " + vendor + " " + Invoice_date + " ";
						
						List<InventoryDto> Inven = InventoryService.Report_Purchase_Inventory(query, userDetails.getCompany_id());
						
						
						if((Inven == null) || (Inven.isEmpty())) 
						{
							model.put("NotFound", true); 
							return new ModelAndView("Part_Purcahse_Report", model);
						}
						
						
						
						model.put("Inventory", (Inven));

						// RenewalReminder Total Amount Sum Query
						model.put("TotalQuantity", round(RRBL.Total_PartInventory_Quantity(Inven), 2));

					}

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

		return new ModelAndView("Part_Purcahse_Report", model);
	}

	/*Part Transfer Report Code By Dev Yogi Start */
	@RequestMapping("/PartTransferLocReport")
	public ModelAndView PartTransferLocReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Part_Transfer_Report", model);
	}	
	/*Part Transfer Report Code By Dev Yogi End  */
	
	@RequestMapping(value = "/PartTransferLocReport", method = RequestMethod.POST)
	public ModelAndView PartTransferLocReport(
			@ModelAttribute("command") @RequestParam("WORKORDER_LOCATION_FROM") final String WORKORDER_LOCATION_FROM,
			@RequestParam("WORKORDER_LOCATION_TO") final String WORKORDER_LOCATION_TO,
			@RequestParam("TRANSFER_STATUS") final short TRANSFER_STATUS,
			@RequestParam("PART_RANGE_DATE") final String PART_RANGE_DATE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			if (PART_RANGE_DATE != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {
					
					From_TO_DateRange = PART_RANGE_DATE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String locationFROM = "", locationTO = "", Status = "";

					if (WORKORDER_LOCATION_FROM != null && WORKORDER_LOCATION_FROM != "") {
						locationFROM = "AND R.transfer_from_locationId =" + WORKORDER_LOCATION_FROM + " ";
					}

					if (WORKORDER_LOCATION_TO != null && WORKORDER_LOCATION_TO != "") {
						locationTO = "AND R.transfer_to_locationId =" + WORKORDER_LOCATION_TO + " ";
					}

					if (TRANSFER_STATUS != 0 ) {
						Status = "AND R.TRANSFER_STATUS_ID=" + TRANSFER_STATUS + "";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {

						String query = "" + locationFROM + " " + locationTO + " " + Status + " ";

						List<InventoryTransferDto> show_Location = InventoryService
								.Get_Transfer_Inventory_Report_Wise_location(dateRangeFrom, dateRangeTo, query, userDetails.getCompany_id());
						if((show_Location == null) || (show_Location.isEmpty())) 
						{
							model.put("NotFound", true); 
							return new ModelAndView("Part_Transfer_Report", model);
						}
						
						
						model.put("InventoryTransfer", (show_Location));

						// RenewalReminder Total Amount Sum Query
						model.put("TotalQuantity", round(RRBL.Total_PartInventoryTranfer_Quantity(show_Location), 2));

					}

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

		return new ModelAndView("Part_Transfer_Report", model);
	}

	@RequestMapping("/PartReport")
	public ModelAndView MasterPartReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
															PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			model.put("PartCategories", PCBL.prepareListofBean(PartCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			model.put("PartManufacturer", PMBL.prepareListofBean(PartManufacturerService.listPartManufacturerByCompanyId(userDetails.getCompany_id())));
			model.put("configuration", configuration);
			
		} catch (Exception e) {

		}
		return new ModelAndView("ReportMasterPartSheet", model);
	}


	@RequestMapping(value = "/MasterPartReport", method = RequestMethod.POST)
	public ModelAndView MasterPartReport(@ModelAttribute("command") MasterPartsSearchDto searchDetails
			, BindingResult result) {
		
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String Partid = "", Make = "", Category = "";

			if (searchDetails.getPartid() != null) {
				Partid = " AND mp.partid = " + searchDetails.getPartid() + " ";
			}

			if (searchDetails.getMakerId() != 0) {
				Make = " AND makerId='" + searchDetails.getMakerId() + "' ";
			}

			if (searchDetails.getCategoryId() != 0) {
				Category = " AND categoryId='" + searchDetails.getCategoryId() + "' ";
			}

			String Qurey = " " + Partid + " " + Make + " " + Category + " ";
			
			if (searchDetails.getPartTypeCatgory() > 0) {
				Qurey += " AND partTypeCategoryId = "+searchDetails.getPartTypeCatgory() +" ";
			}
			if (searchDetails.getWarranty() != null) {
				if(searchDetails.getWarranty().equals("true")) {
					Qurey += " AND isWarrantyApplicable = 1 ";
				}else if(searchDetails.getWarranty().equals("false")){
					Qurey += " AND isWarrantyApplicable = 0 ";
				}
			}
			if (searchDetails.getRepairable() != null) {
				if(searchDetails.getRepairable().equals("true")) {
					Qurey += " AND isRepairable = 1 ";
				}else if(searchDetails.getRepairable().equals("false")){
					Qurey += " AND isRepairable = 0 ";
				}
			}
			if (searchDetails.getOemId() != null && searchDetails.getOemId() > 0) {
				Qurey = " AND ME.originalBrand =" + searchDetails.getOemId() + " ";
			}
			if (searchDetails.getVehicleMake() != null && !searchDetails.getVehicleMake().equals("")) {
					Qurey += " AND MM.vehicleManufacturerId IN ("+searchDetails.getVehicleMake()+") ";
			}
			if (searchDetails.getVehicleModel() != null && !searchDetails.getVehicleModel().equals("")) {
				Qurey += " AND VM.vehicleModelId IN ("+searchDetails.getVehicleModel()+") ";
			}
			model.put("MasterParts", MPBL.prepareListofBean(MasterPartsService.ReportMasterParts(Qurey, userDetails.getCompany_id())));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("MasterPart_Report", model);
	}
	
	@RequestMapping("/MostFrequentlyConsumedPart")
	public ModelAndView TyreSentForRethreadingReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Most_Frequently_Consumed_Part", model);
	}

	@RequestMapping("/partWiseConsumptionReport")
	public ModelAndView PartWiseConsumptionReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("partWiseConsumptionReport", model);
	}
	
	@RequestMapping("/partWiseConsumptionReport2")
	public ModelAndView PartWiseConsumptionReport2() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("partWiseConsumptionReport2", model);
	}
	
	@RequestMapping("/partPurchaseInvoiceReport")
	public ModelAndView partPurchaseInvoiceReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object>		configuration = null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
															PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.put("configuration", configuration);
		return new ModelAndView("partPurchaseInvoiceReport", model);
	}
	@RequestMapping("/TechnicianWisePartReport")
	public ModelAndView TechnicianWisePartReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("TechnicianWisePartReport", model);
	}
	
	@RequestMapping("/PartRequisitionStatusReport")
	public ModelAndView PartRequisitionStatusReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("PartRequisitionStatusReport", model);
	}
}

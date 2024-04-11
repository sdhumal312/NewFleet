package org.fleetopgroup.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.constant.PartInventoryConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PartManufacturerBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartManufacturerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InventoryReportController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IPartCategoriesService PartCategoriesService;
	PartCategoriesBL PCBL = new PartCategoriesBL();

	@Autowired
	private IPartManufacturerService PartManufacturerService;
	PartManufacturerBL PMBL = new PartManufacturerBL();

	@Autowired
	private IInventoryService InventoryService;
	
	@Autowired 
	CompanyConfigurationService			companyConfigurationService;
	
	InventoryBL InBL = new InventoryBL();
	PartLocationsBL PLBL = new PartLocationsBL();

	@Autowired
	private IPartLocationPermissionService partLocationPermissionService;

	@RequestMapping(value = "/InventoryReport", method = RequestMethod.GET)
	public ModelAndView InventoryReport() {
		Map<String, Object> 		model = new HashMap<String, Object>();
		CustomUserDetails 			userDetails = null;
		HashMap<String, Object> 	configuration				= null;
		boolean						showSubLocation				= false;
		String						mainLocationIds				= "";
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			showSubLocation 		= (boolean)configuration.getOrDefault(PartInventoryConfigurationConstants.SHOW_SUB_LOCATION, false);
			mainLocationIds 		= configuration.getOrDefault(PartInventoryConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
			if(showSubLocation) {
				model.put("showSubLocation",showSubLocation);
				model.put("mainLocationIds",mainLocationIds);
			}
			model.put("PartCategories", PCBL.prepareListofBean(
					PartCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			model.put("PartManufacturer", PMBL.prepareListofBean(
					PartManufacturerService.listPartManufacturerByCompanyId(userDetails.getCompany_id())));
			// model.put("PartLocations",
			// PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			model.put("PartLocationPermission", PLBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));

		} catch (Exception e) {
			LOGGER.error("Inventory GET Controller");
		}
		return new ModelAndView("ReportInventory", model);
	}

	@RequestMapping(value = "/ReportInventory", method = RequestMethod.POST)
	public ModelAndView ReportInventory(@ModelAttribute("command") InventoryDto inventory, BindingResult result) {

		// PurchaseOrders POReport = RBL.preparePurchaseOrder(purchaseOrders);
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<InventoryDto> show_Inventory = InventoryService.Report_listInventory(inventory,
					userDetails.getCompany_id());
			model.put("Inventory", show_Inventory);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			userDetails = null;
		}
		return new ModelAndView("Inventory_Report", model);
	}

}

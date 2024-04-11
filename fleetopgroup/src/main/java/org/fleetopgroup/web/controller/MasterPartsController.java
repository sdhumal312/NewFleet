package org.fleetopgroup.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.MasterPartsConfigurationConstants;
import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PartType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.MasterPartsBL;
import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PartManufacturerBL;
import org.fleetopgroup.persistence.bl.PartMeasurementUnitBL;
import org.fleetopgroup.persistence.dao.MasterPartRateHistoryRepository;
import org.fleetopgroup.persistence.dao.MasterPartRateRepository;
import org.fleetopgroup.persistence.dao.MasterPartsRepository;
import org.fleetopgroup.persistence.dao.VehicleManufacturerRepository;
import org.fleetopgroup.persistence.dao.VehicleModelRepository;
import org.fleetopgroup.persistence.dao.VendorRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.MasterPartsDto;
import org.fleetopgroup.persistence.dto.MasterPartsLocationDto;
import org.fleetopgroup.persistence.model.LowStockInventory;
import org.fleetopgroup.persistence.model.MasterPartRate;
import org.fleetopgroup.persistence.model.MasterPartRateHistory;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.MasterPartsLocation;
import org.fleetopgroup.persistence.model.MasterPartsPhoto;
import org.fleetopgroup.persistence.model.PartCategories;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.model.PartManufacturer;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.model.VehicleManufacturer;
import org.fleetopgroup.persistence.model.VehicleModel;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPartManufacturerService;
import org.fleetopgroup.persistence.serviceImpl.IPartMeasurementUnitService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import au.com.bytecode.opencsv.CSVReader;

@Controller
public class MasterPartsController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	IMasterPartsService 			masterPartsService;

	@Autowired 
	IPartCategoriesService 			partCategoriesService;

	@Autowired 
	IPartMeasurementUnitService 	partMeasurementUnitService;
	
	@Autowired 
	ICompanyConfigurationService	companyConfigurationService;

	@Autowired 
	IPartManufacturerService 		partManufacturerService;

	@Autowired 
	IPartLocationsService 			partLocationsService;
	
	@Autowired	private VehicleManufacturerRepository	vehicleManufacturerRepository;
	@Autowired	private VehicleModelRepository			vehicleModelDao;
	@Autowired	private VendorRepository				vendorService;
	@Autowired	private MasterPartsRepository			masterPartsRepository;
	@Autowired	private MasterPartRateRepository		masterPartRateRepository;
	@Autowired	private MasterPartRateHistoryRepository	masterPartRateHistoryRepository;

	PartMeasurementUnitBL 			partMeasurementUnitBL 			= new PartMeasurementUnitBL();
	PartManufacturerBL 				partManufacturerBL	 			= new PartManufacturerBL();
	PartCategoriesBL 				partCategoriesBL	 			= new PartCategoriesBL();
	PartLocationsBL 				partLocationsBL 				= new PartLocationsBL();
	MasterPartsBL 					masterPartsBL			 		= new MasterPartsBL();

	// show all parts in main page
	@RequestMapping(value = "/newMasterParts/{pageNumber}", method = RequestMethod.GET)
	public String newMasterPartsList(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails	userDetails	= null;
		HashMap<String, Object>   configuration		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			HashMap<String, Object>  config	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			Page<MasterParts> page = masterPartsService.getDeployment_Page_MasterParts(pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("MasterPartsCount", page.getTotalElements());

			List<MasterPartsDto> pageList = masterPartsBL.prepareListofBean(masterPartsService.listMasterParts(pageNumber, userDetails.getCompany_id()));

			model.addAttribute("MasterParts", pageList);
			model.addAttribute("configuration", configuration);
			model.addAttribute("config", config);
			
			model.addAttribute(MastersConfigurationConstants.COMMON_MASTER_PARTS, (boolean)configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS));

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newMasterParts Page:", e);
			e.printStackTrace();
		}finally {
			configuration		= null;
			userDetails			= null;
		}
		return "newMasterParts";
	}

	// Create new parts page
	@RequestMapping(value = "/addMasterParts", method = RequestMethod.GET)
	public ModelAndView addMasterParts(@ModelAttribute("command") MasterParts MasterPartsBean,
			final HttpServletRequest request) {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			
			model.put("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			model.put("PartManufacturer", partManufacturerBL.prepareListofBean(partManufacturerService.listPartManufacturerByCompanyId(userDetails.getCompany_id())));
			model.put("PartLocations", partLocationsBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			model.put("PartType", PartType.getPartTypeList());
			model.put("PartMeasurementUnit", partMeasurementUnitBL.prepareListofBean(partMeasurementUnitService.listPartMeasurementUnit()));
			model.put("configuration", configuration);

		} catch (Exception e) {
			LOGGER.error("Master Part Page:", e);
		}
		return new ModelAndView("addMasterParts", model);
	}

	// save the Master part page
	@RequestMapping(value = "/saveMasterParts", method = RequestMethod.POST)
	public ModelAndView saveMasterParts(@ModelAttribute("command") MasterParts masterPartDTO,
			@RequestParam("refreshment") final short refreshment,
			@RequestParam("location_ID") final String[] location, @RequestParam("Aisle") final String[] Aisle,
			@RequestParam("row") final String[] row, @RequestParam("bin") final String[] bin,
			final HttpServletRequest request) {
		
		MasterParts 				masterParts 		= null; 
		MasterParts					masterPartsFromDB	= null;
		CustomUserDetails			userDetails			= null;
		HashMap<String, Object>		configuration		= null;
		MasterPartsLocation 		inven 				= null;
		
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.get("showRefreshmentOption")) {
				if(masterPartDTO.getPartnumber() == null || masterPartDTO.getPartnumber().trim().equals("")) {
					masterPartDTO.setPartnumber(masterPartDTO.getPartname());
					if(refreshment == 1) {
						masterPartDTO.setRefreshment(true);
					}else {
						masterPartDTO.setRefreshment(false);
					}
					
				}
			}
			masterParts 		= masterPartsBL.prepareModel(masterPartDTO);
			masterPartsFromDB	= masterPartsService.validatePartNumber(masterParts.getPartnumber(), masterParts.getPartname(),masterParts.getMakerId(), masterParts.getCompanyId());
			if(masterPartsFromDB == null) {
				if(!((boolean) configuration.getOrDefault(MasterPartsConfigurationConstants.SHOW_PART_MANUFACTURER_TYPE_COL, false)) 
						&& masterParts.getPartManufacturerType() == 0) {
					masterParts.setPartManufacturerType(PartType.PART_MANUFACTURER_TYPE_LOCAL);
				}
				
				masterPartsService.addMasterParts(masterParts);
				
				for (int i = 0; i < location.length; i++) {
					inven = new MasterPartsLocation();
					
					inven.setLocationId(Integer.parseInt("" + location[i]));
					if(Aisle.length > i)
						inven.setAisle(Aisle[i] + "");
					if(row.length > i)
						inven.setRow(row[i] + "");
					if(bin.length > i)
						inven.setBin(bin[i] + "");
					inven.setMasterparts(masterParts);
					inven.setCompanyId(masterParts.getCompanyId());
					
					masterPartsService.addMasterPartsLocation(inven);
				}
			}else {
				return new ModelAndView("redirect:/newMasterParts/1.in?alreadyCreate=true");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newMasterParts/1.in?alreadyCreate=true");
		}
		return new ModelAndView("redirect:/newMasterParts/1.in?Save=true");
	}

	// edit Master Parts
	@RequestMapping(value = "/editMasterParts", method = RequestMethod.GET)
	public ModelAndView editMasterParts(@ModelAttribute("command") MasterParts MasterPartsBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		MasterPartsDto		masterParts		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			masterParts	= masterPartsService.getMasterParts(MasterPartsBean.getPartid(), userDetails.getCompany_id());
			
			if(masterParts != null) {
				HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
				model.put("MasterParts",masterPartsBL.prepareMasterPartsBean(masterParts));
				
				List<MasterPartsLocationDto> PartLocations = masterPartsService.getMasterPartsLocation(masterParts.getPartid(), userDetails.getCompany_id());
				List<PartLocations> Locations = partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id());
				
				List<MasterPartsLocationDto> allpartLocation = masterPartsBL.prepareNoTisEmpty(PartLocations, Locations);
				model.put("PartLocations", allpartLocation);
				model.put("configuration", configuration);
				
				model.put("PartType", PartType.getPartTypeList());
				model.put("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
				model.put("PartManufacturer", partManufacturerBL.prepareListofBean(partManufacturerService.listPartManufacturerByCompanyId(userDetails.getCompany_id())));
				model.put("PartMeasurementUnit",partMeasurementUnitBL.prepareListofBean(partMeasurementUnitService.listPartMeasurementUnit()));
			}
			

		} catch (Exception e) {
			LOGGER.error("Master Part Page:", e);
		}
		return new ModelAndView("editMasterParts", model);
	}

	// update of
	@RequestMapping(value = "/uploadMasterParts", method = RequestMethod.POST)
	public ModelAndView uploadMasterParts(@ModelAttribute("command") MasterParts MasterParts,
			@RequestParam("partlocationid") final String[] partlocationid,
			@RequestParam("refreshment") final short refreshment,
			@RequestParam("locationId") final String[] location, @RequestParam("Aisle") final String[] Aisle,
			@RequestParam("row") final String[] row, @RequestParam("bin") final String[] bin,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>	configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			if((boolean) configuration.get("showRefreshmentOption")) {
				if(MasterParts.getPartnumber() == null || MasterParts.getPartnumber().trim().equals("")) {
					MasterParts.setPartnumber(MasterParts.getPartname());
					if(refreshment == 1) {
						MasterParts.setRefreshment(true);
					}else {
						MasterParts.setRefreshment(false);
					}
					
				}
			}
			MasterParts status = masterPartsBL.prepareModel(MasterParts);
			status.setCompanyId(userDetails.getCompany_id());
			
			
			
			masterPartsService.updateMasterParts(status);

			// save the inventory id to FK of the Quantity
			for (int i = 0; i < location.length; i++) {
				// System.out.println(quantity.length);
				if(partlocationid.length > 0) {
					if (partlocationid[i] != null) {
						/*MasterPartsLocation inven = new MasterPartsLocation(Integer.parseInt(partlocationid[i]), Integer.parseInt("" + location[i] ),
								"" + Aisle[i] + "", "" + row[i] + "", "" + bin[i] + "");*/
						MasterPartsLocation inven = new MasterPartsLocation();
						inven.setLocationId(Integer.parseInt("" + location[i]));
						if(Aisle.length > i)
							inven.setAisle(Aisle[i] + "");
						if(row.length > i)
							inven.setRow(row[i] + "");
						if(bin.length > i)
							inven.setBin(bin[i] + "");
						inven.setMasterparts(status);
						inven.setCompanyId(status.getCompanyId());
						masterPartsService.updateMasterPartsLocation(inven);
					} else {
						MasterPartsLocation inven = new MasterPartsLocation(Integer.parseInt("" + location[i] ), "" + Aisle[i] + "",
								"" + row[i] + "", "" + bin[i] + "");
						inven.setMasterparts(status);
						inven.setCompanyId(status.getCompanyId());
						masterPartsService.addMasterPartsLocation(inven);
					}
				}
			}

			
			model.put("Update", true);

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newMasterParts/1.in?NotUpdate=true");
		}
		return new ModelAndView("redirect:/showMasterParts.in?partid=" + MasterParts.getPartid() + "", model);
	}

	@RequestMapping(value = "/saveLowStockLevel", method = RequestMethod.POST)
	public ModelAndView addLowStockLevel(@ModelAttribute("command") MasterPartsDto MasterParts,
	final HttpServletRequest request) {
	Map<String, Object> model = new HashMap<String, Object>();
	CustomUserDetails	userDetails			= null;

	try {
		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	LowStockInventory lowStockInventory = masterPartsBL.getLowStockInventory(MasterParts);
	LowStockInventory	validate 		= masterPartsService.validateLowStockInventory(MasterParts.getPartid(), 
														userDetails.getCompany_id(), MasterParts.getLocationId());
	if(validate == null) {
		masterPartsService.saveLowStockLevel(lowStockInventory);
	}else{
		
		validate.setLowstocklevel(MasterParts.getLowstocklevel());
		validate.setReorderquantity(MasterParts.getReorderquantity());
		validate.setLocationId(MasterParts.getLocationId());
		masterPartsService.saveLowStockLevel(validate);
	}
	model.put("addLowStock", true);

	} catch (Exception e) {
	e.printStackTrace();
	return new ModelAndView("redirect:/newMasterParts/1.in?NotUpdate=true");
	}
	return new ModelAndView("redirect:/showMasterParts.in?partid=" + MasterParts.getPartid() + "", model);
	}
	
	@RequestMapping(value = "/savePrice", method = RequestMethod.POST)
	public ModelAndView addPrice(@ModelAttribute("command") MasterPartsDto MasterParts,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails			= null;

		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			MasterPartRate lowStockInventory = masterPartsBL.getPartPrice(MasterParts);
			
			MasterPartRate	validate = masterPartRateRepository.validateMasterPartRate(MasterParts.getPartid(), userDetails.getCompany_id());
			
			if(validate == null) {
				masterPartRateRepository.save(lowStockInventory);
			} else {
				
				MasterPartRateHistory	history	= masterPartsBL.getPartPriceHistoryDto(validate, userDetails);
				
				history.setPartRateId(validate.getPartRateId());
				
				masterPartRateHistoryRepository.save(history);
				
				validate.setUnitCost(MasterParts.getUnitCost());
				validate.setDiscount(MasterParts.getDiscount());
				validate.setTax(MasterParts.getTax());
				validate.setLastModifiedById(userDetails.getId());
				validate.setLastupdated(new Timestamp(System.currentTimeMillis()));
				
				masterPartRateRepository.save(validate);
				
			}
			model.put("addPrice", true);

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newMasterParts/1.in?NotUpdate=true");
		}
		return new ModelAndView("redirect:/showMasterParts.in?partid=" + MasterParts.getPartid() + "", model);
	}

	
	@RequestMapping(value = "/savePartLocationDetails", method = RequestMethod.POST)
	public ModelAndView savePartLocationDetails(@ModelAttribute("command") MasterPartsLocationDto masterParts,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		MasterPartsLocation 		locations	= null;
		try {
			
				MasterPartsLocation masterPartsLocation = masterPartsBL.getMasterPartsLocation(masterParts);
				locations	=		masterPartsService.validateMasterPartLocation(masterParts.getPartid(), masterPartsLocation.getLocationId(), masterPartsLocation.getCompanyId());
				if(locations == null) {
					masterPartsService.addMasterPartsLocation(masterPartsLocation);
				}else {
					masterPartsLocation.setPartlocationid(locations.getPartlocationid());
					masterPartsService.addMasterPartsLocation(masterPartsLocation);
				}

			model.put("addLocationDetails", true);

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newMasterParts/1.in?error=true");
		}
		return new ModelAndView("redirect:/showMasterParts.in?partid=" + masterParts.getPartid() + "", model);
	}
	
	// MasterParts
	@RequestMapping(value = "/showMasterParts", method = RequestMethod.GET)
	public ModelAndView showMasterParts(@ModelAttribute("command") MasterParts MasterPartsBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			HashMap<String, Object> config	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			
			MasterPartsDto	masterPartsDto = masterPartsBL.prepareMasterPartsBean(masterPartsService.getMasterParts(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
			
			model.put("MasterParts", masterPartsDto);
			
			List<MasterPartsLocationDto> MasterPartsLocation = masterPartsService.getMasterPartsLocation(MasterPartsBean.getPartid(), userDetails.getCompany_id());
			model.put("PartLocations", MasterPartsLocation);
			model.put(MastersConfigurationConstants.COMMON_MASTER_PARTS, (boolean)configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS));
			model.put("Locations", partLocationsBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			
			model.put("photoCount", masterPartsService.getPhotoCount(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
			model.put("config", config);
			model.put("lowStockList", masterPartsService.getLowStockInventoryByPartId(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
			model.put("partRate", masterPartRateRepository.validateMasterPartRate(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
			model.put("partRateHistory", masterPartRateHistoryRepository.getMasterPartRateHistoryByPartId(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
			
			if((boolean) config.get("showExtendedPartSave")) {
				model.put("extraDetails", masterPartsService.getMasterPartsExtraDetailsByPartId(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
				model.put("purchaseVendors", masterPartsService.getPurchaseVendorDetailsByPartId(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
				model.put("repairableVendors", masterPartsService.getRepairableVendorDetailsByPartId(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
				if(masterPartsDto.getPartTypeCategoryId() == PartType.PART_TYPE_CATEGORY_CHILD) {
					model.put("parentParts", masterPartsService.getParentPartDetailsByPartId(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
				}else {
					model.put("childParts", masterPartsService.getChildPartDetailsByPartId(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
				}
				
				
				model.put("substituDeParts", masterPartsService.getSubstitudePartDetailsByPartId(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
				model.put("vehicleMake", masterPartsService.getMasterPartsToVehicleMakerList(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
				model.put("vehicleModal", masterPartsService.getMasterPartsToVehicleModalList(MasterPartsBean.getPartid(), userDetails.getCompany_id()));
			}
			
		} catch (Exception e) {
			LOGGER.error("Master Part Page:", e);
		}
		return new ModelAndView("showMasterParts", model);
	}

	@RequestMapping(value = "/deleteMasterParts", method = RequestMethod.GET)
	public ModelAndView deleteMasterParts(@RequestParam("ID") final Long Partid, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			masterPartsService.deleteMasterPartsLocation(Partid, userDetails.getCompany_id());

			masterPartsService.deleteMasterParts(Partid, userDetails.getCompany_id());

			model.put("Delete", true);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Master Part Page:", e);
			return new ModelAndView("redirect:/newMasterParts/1.in?danger=true");

		}
		return new ModelAndView("redirect:/newMasterParts/1.in", model);
	}

	@RequestMapping(value = "/searchMasterParts", method = RequestMethod.POST)
	public ModelAndView searchInventory(@ModelAttribute("command") MasterParts searchMasterParts,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		HashMap<String, Object>   configuration		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			HashMap<String, Object> config = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			model.put("MasterParts",
					masterPartsBL.prepareListofBean(masterPartsService.SearchMasterParts(searchMasterParts.getPartnumber(), userDetails.getCompany_id())));
			model.put("MasterPartsCount", masterPartsService.countpart(userDetails.getCompany_id()));
			model.put(MastersConfigurationConstants.COMMON_MASTER_PARTS, (boolean)configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS));
			model.put("config", config);
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Master Part Page:", e);
		}
		return new ModelAndView("newMasterPartsReport", model);
	}

	/********************************************************************************************************************************************************************
	 * MasterParts Photo
	 *******************************************************************************************************************************************************************/
	/* MasterParts Photo */
	@RequestMapping("/ShowMasterPartsPhoto")
	public ModelAndView ShowMasterPartsphoto(@ModelAttribute("command") MasterParts MasterPartsBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		MasterPartsDto		masterParts		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			masterParts	= masterPartsService.getMasterParts(MasterPartsBean.getPartid(), userDetails.getCompany_id());
			if(masterParts != null) {
				
				model.put("MasterParts", masterPartsBL.prepareMasterPartsBean(masterParts));
				
				// List of the MasterPartsDocument
				model.put("MasterPartsPhoto", masterPartsBL.prepareListMasterPartsPhoto(masterPartsService.listMasterPartsPhoto(MasterPartsBean.getPartid(), userDetails.getCompany_id())));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			masterParts		= null;
		}
		return new ModelAndView("showMasterPartsPhoto", model);
	}

	/* save MasterParts Document */
	@RequestMapping(value = "/uploadMasterPartsPhoto", method = RequestMethod.POST)
	public ModelAndView saveMasterPartsPhoto(@ModelAttribute("command") org.fleetopgroup.persistence.document.MasterPartsPhoto MasterPartsPhoto,
			@RequestParam("file") MultipartFile file) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!file.isEmpty()) {
				try {

					byte[] bytes = file.getBytes();
					MasterPartsPhoto.setPart_filename(file.getOriginalFilename());
					MasterPartsPhoto.setPart_content(bytes);
					MasterPartsPhoto.setPart_contentType(file.getContentType());
					MasterPartsPhoto.setCompanyId(userDetails.getCompany_id());
				} catch (IOException e) {

				}

				MasterPartsPhoto.setPartid(MasterPartsPhoto.getPartid());

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				MasterPartsPhoto.setCreated(toDate);
				MasterPartsPhoto.setLastupdated(toDate);
				MasterPartsPhoto.setCreatedById(userDetails.getId());
				MasterPartsPhoto.setLastModifiedById(userDetails.getId());

				try {
					masterPartsService.addMasterPartsPhoto(MasterPartsPhoto);
				} catch (Exception e) {
					e.printStackTrace();
					model.put("danger", true);
					return new ModelAndView(
							"redirect:/ShowMasterPartsPhoto.in?partid=" + MasterPartsPhoto.getPartid() + "", model);

				}

			} else {
				// messages
				return new ModelAndView("redirect:/ShowMasterPartsPhoto.in?partid=" + MasterPartsPhoto.getPartid() + "",
						model);

			}
			// this message alert of show method
			model.put("addMasterPartsPhoto", true);
		} catch (Exception e) {
			return new ModelAndView("redirect:/ShowMasterPartsPhoto.in?partid=" + MasterPartsPhoto.getPartid() + "",
					model);

		}
		return new ModelAndView("redirect:/ShowMasterPartsPhoto.in?partid=" + MasterPartsPhoto.getPartid() + "", model);
	}

	/* show the image of the MasterParts photo */
	@RequestMapping("/getPartImage/{MasterParts_photoid}")
	public String getImage(@PathVariable("MasterParts_photoid") Long MasterParts_photoid, HttpServletResponse response)
			throws Exception {
		try {
			if (MasterParts_photoid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.MasterPartsPhoto file = masterPartsService.getMasterPartsPhoto(MasterParts_photoid);
				if (file != null) {
					if (file.getPart_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getPart_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getPart_contentType());
						response.getOutputStream().write(file.getPart_content());
						out.flush();
						out.close();

					}
				}
			}
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}

	/* Delete the MasterParts Photo */
	@RequestMapping(value = "deleteMasterPartsPhoto")
	public ModelAndView deletePhoto(@ModelAttribute("command") MasterPartsPhoto MasterPartsPhotoBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		// First get the MasterParts_remid all information

		Long Partid = null;
		org.fleetopgroup.persistence.document.MasterPartsPhoto MasterPartsPhoto;
		try {
			MasterPartsPhoto = masterPartsService.getMasterPartsPhoto(MasterPartsPhotoBean.getPart_photoid());

			// show the MasterParts information in header
			MasterParts MasterParts = masterPartsService.getMasterParts(MasterPartsPhoto.getPartid());

			Partid = MasterPartsPhoto.getPartid();

			if ((MasterParts.getPart_photoid()).equals(MasterPartsPhoto.get_id())) {
				// this message alert of show method
				model.put("alreadyProfilePhoto", true);

			} else {
				// delete method
				masterPartsService.deleteMasterPartsPhoto(MasterPartsPhoto.get_id(), MasterPartsPhoto.getCompanyId());
				// this message alert of show method
				model.put("Delete", true);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("redirect:/ShowMasterPartsPhoto.in?partid=" + Partid + "", model);
	}

	/* Set MasterParts Profile Photo */
	@RequestMapping("/setPartPhoto")
	public ModelAndView MasterPartsPhotoSHow(@ModelAttribute("command") MasterPartsPhoto MasterPartsPhotoBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		// First get the MasterParts_remid all information
		Long PartId = (long) 0;
		try {
			org.fleetopgroup.persistence.document.MasterPartsPhoto MasterPartsPhoto = masterPartsService
					.getMasterPartsPhoto(MasterPartsPhotoBean.getPart_photoid());
			PartId = MasterPartsPhoto.getPartid();
			// delete method
			masterPartsService.setMasterPartProfilePhoto(MasterPartsPhoto.get_id(),
					MasterPartsPhoto.getPartid());

		} catch (Exception e) {
			e.printStackTrace();
			model.put("dangerProfile", true);
			return new ModelAndView("redirect:/ShowMasterPartsPhoto.in?partid=" + PartId + "", model);

		}

		// this message alert of show method
		model.put("setProfilePhoto", true);

		return new ModelAndView("redirect:/ShowMasterPartsPhoto.in?partid=" + PartId + "", model);
	}

	@RequestMapping("/setRemovePartPhoto")
	public ModelAndView setRemovePartPhoto(@ModelAttribute("command") MasterPartsPhoto MasterPartsPhotoBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		// First get the MasterParts_remid all information
		Long PartId = (long) 0;
		try {
			org.fleetopgroup.persistence.document.MasterPartsPhoto MasterPartsPhoto = masterPartsService
					.getMasterPartsPhoto(MasterPartsPhotoBean.getPart_photoid());
			PartId = MasterPartsPhoto.getPartid();
			// delete method
			masterPartsService.setMasterPartProfilePhoto(null,
					MasterPartsPhoto.getPartid());

		} catch (Exception e) {
			e.printStackTrace();
			model.put("dangerProfile", true);
			return new ModelAndView("redirect:/ShowMasterPartsPhoto.in?partid=" + PartId + "", model);

		}

		// this message alert of show method
		model.put("setProfilePhoto", true);

		return new ModelAndView("redirect:/ShowMasterPartsPhoto.in?partid=" + PartId + "", model);
	}
	
	java.util.Date currentDate = new java.util.Date();
	DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");

	@RequestMapping(value = "/importMasterPart", method = RequestMethod.POST)
	public ModelAndView importMasterPart(@ModelAttribute("command") VehicleDocument photo,
			@RequestParam("import") MultipartFile file, HttpServletRequest request) throws Exception {
		
		CustomUserDetails			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<PartManufacturer> 		partManufacturers	= partManufacturerService.listPartManufacturerByCompanyId(userDetails.getCompany_id());
		List<PartMeasurementUnit>	measurementUnits	= partMeasurementUnitService.listPartMeasurementUnit();
		HashMap<String, Object> 	configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
		HashMap<String, Object> 	masterPartconfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
		
		Map<String, Object> model = new HashMap<String, Object>();

		String rootPath = request.getSession().getServletContext().getRealPath("/");

		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());

		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
				int i;
				// write file to server
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Count How many Import SuccessFully
		int CountSuccess = 0;
		int CountDuplicate = 0;

		String[] nextLine;
		try {
			try (FileReader fileReader = new FileReader(serverFile);
					CSVReader reader = new CSVReader(fileReader, ';', '\'', 1);) {
				while ((nextLine = reader.readNext()) != null) {
					MasterParts part = new MasterParts();

					for (int i = 0; i < nextLine.length; i++) {
						try {

							String[] importVehicle = nextLine[i].split(",");

							part.setPartnumber(importVehicle[0]);
							part.setPartname(importVehicle[1]);
							part.setDescription(importVehicle[4]);
							
							if(((boolean) masterPartconfig.getOrDefault(MasterPartsConfigurationConstants.SHOW_PART_MANUFACTURER_TYPE_COL, false))) {
								part.setPartManufacturerType(PartType.PART_MANUFACTURER_TYPE_ORIGINAL);								
							} else {
								part.setPartManufacturerType(PartType.PART_MANUFACTURER_TYPE_LOCAL);
							}

							/** Set Created by email Id */
							part.setCreatedById(userDetails.getId());
							part.setLastModifiedById(userDetails.getId());
							part.setCompanyId(userDetails.getCompany_id());
							
							if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
								part.setCompanyId(0);
							}else {
								part.setOwnMaterParts(true);
							}

							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp Created = new java.sql.Timestamp(currentDateUpdate.getTime());

							part.setCreated(Created);
							part.setLastupdated(Created);

							part.setMarkForDelete(false);

							part.setPart_photoid((long) 1);

							try {
								List<PartCategories> CategoriesValidate = partCategoriesService
										.ValidateCategoriesName(importVehicle[2], userDetails.getCompany_id());
								if (CategoriesValidate != null && !CategoriesValidate.isEmpty()) {
									part.setCategoryId(CategoriesValidate.get(0).getPcid());
									/*for(PartCategories categories : partCategories) {
										if(importVehicle[2].trim().equalsIgnoreCase(categories.getPcName())) {
											part.setCategoryId(categories.getPcid());
											break;
										}
									}*/
								} else {
									PartCategories status = new PartCategories();
									status.setPcName(importVehicle[2]);
									status.setPcdescription(importVehicle[2]);
									status.setCompanyId(userDetails.getCompany_id());
									status.setCreatedById(userDetails.getId());
									status.setLastModifiedById(userDetails.getId());
									status.setCreatedOn(new Timestamp(System.currentTimeMillis()));
									
									if((boolean) configuration.get(MastersConfigurationConstants.COMMON_MASTER_PARTS)) {
										status.setCompanyId(0);
									}else {
										status.setOwnPartCategory(true);
									}
									
									partCategoriesService.addPartCategories(status);
									part.setCategoryId(status.getPcid());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								List<PartManufacturer> ManufacturerValidate = partManufacturerService
										.ValidatePartManufacturerName(importVehicle[3], userDetails.getCompany_id());
								if (ManufacturerValidate != null && !ManufacturerValidate.isEmpty()) {
									for(PartManufacturer manufacturer : partManufacturers) {
										if(importVehicle[3].trim().equalsIgnoreCase(manufacturer.getPmName())) {
											part.setMakerId(manufacturer.getPmid());
											//part.setMake(manufacturer.getPmName());
											break;
										}
									}
								} else {
									PartManufacturer status = new PartManufacturer();
									status.setPmName(importVehicle[3]);
									status.setPmdescription(importVehicle[3]);
									status.setCompanyId(userDetails.getCompany_id());
									status.setCreatedBy(userDetails.getEmail());
									status.setCreatedById(userDetails.getId());
									status.setCreatedOn(new Timestamp(System.currentTimeMillis()));
									partManufacturerService.addPartManufacturer(status);
									part.setMakerId(status.getPmid());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							try {
								PartMeasurementUnit	validate =	partMeasurementUnitService.ValidatePartMeasurementUnit(importVehicle[7]);
								if(validate != null) {
									for(PartMeasurementUnit measurementUnit : measurementUnits) {
										if(importVehicle[7].trim().equalsIgnoreCase(measurementUnit.getPmuName())) {
											part.setUnitTypeId(measurementUnit.getPmuid());
											break;
										}
									}
								}else {
									PartMeasurementUnit	unit = new PartMeasurementUnit(importVehicle[7],importVehicle[7], "");
									partMeasurementUnitService.addPartMeasurementUnit(unit);
									part.setUnitTypeId(unit.getPmuid());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							/**
							 * setting Part Type quantity as default because in csv no column for Part Type
							 */
							part.setPartTypeId(PartType.PART_TYPE_QUANTITY);
							try {
								List<MasterParts> validate = masterPartsService
										.GetMasterPartValidate(part.getPartnumber(), part.getPartname(), userDetails.getCompany_id());
								if (validate != null && !validate.isEmpty()) {
									++CountDuplicate;
									String Duplicate = "Part NO =" + part.getPartnumber() + " Part Name ="
											+ part.getPartname() + "";
									model.put("CountDuplicate", CountDuplicate);
									model.put("Duplicate", Duplicate);
									model.put("importSaveAlreadyError", true);

								} else {
									try {
										masterPartsService.addMasterParts(part);
										CountSuccess++;
									} catch (final Exception e) {
										e.printStackTrace();
										++CountDuplicate;
										String Duplicate = "Part NO =" + part.getPartnumber() + " Part Name ="
												+ part.getPartname() + "";
										model.put("CountDuplicate", CountDuplicate);
										model.put("Duplicate", Duplicate);
										model.put("importSaveAlreadyError", true);
									}


								}
							} catch (final Exception e) {
								++CountDuplicate;
								String Duplicate = "Part NO =" + part.getPartnumber() + " Part Name ="
										+ part.getPartname() + "";
								model.put("CountDuplicate", CountDuplicate);
								model.put("Duplicate", Duplicate);
								model.put("importSaveAlreadyError", true);
							}

						} catch (Exception e) { 
							e.printStackTrace();
							try {
								model.put("MasterParts", masterPartsBL.prepareListofBean(masterPartsService.listMasterParts(1, userDetails.getCompany_id())));
								model.put("MasterPartsCount", masterPartsService.countpart(userDetails.getCompany_id()));
								model.put("importSaveError", true);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							return new ModelAndView("newMasterParts", model);
						}

					} // for loop close

				}
			}
		} catch (Exception e) {
			// System.out.println("error while reading csv and put to db : " +
			// e.getMessage());
			try {
				model.put("MasterParts", masterPartsBL.prepareListofBean(masterPartsService.listMasterParts(1, userDetails.getCompany_id())));
				model.put("MasterPartsCount", masterPartsService.countpart(userDetails.getCompany_id()));
				model.put("importSaveError", true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return new ModelAndView("newMasterParts", model);
		}

		// show the Vehicle List
		try {
			model.put("CountSuccess", CountSuccess);
			model.put("MasterParts", masterPartsBL.prepareListofBean(masterPartsService.listMasterParts(1, userDetails.getCompany_id())));
			model.put("MasterPartsCount", masterPartsService.countpart(userDetails.getCompany_id()));
			model.put("importSave", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("newMasterParts", model);
	}
	
	@RequestMapping(value = "/importMasterPartExtended", method = RequestMethod.POST)
	public ModelAndView importMasterPartExtended(@ModelAttribute("command") VehicleDocument photo,
			@RequestParam("import") MultipartFile file, HttpServletRequest request) throws Exception {
		
		CustomUserDetails			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<PartManufacturer> 		partManufacturers	= partManufacturerService.listPartManufacturerByCompanyId(userDetails.getCompany_id());
		List<PartMeasurementUnit>	measurementUnits	= partMeasurementUnitService.listPartMeasurementUnit();
		List<PartType>				partTypeList		= PartType.getPartTypeList();
		List<PartCategories> 		partCategories		= partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id());
		List<VehicleManufacturer>   vehicleManList		= vehicleManufacturerRepository.findBycompanyId(userDetails.getCompany_id());
		List<VehicleModel> 			vehicleModalList	= vehicleModelDao.findBycompanyId(userDetails.getCompany_id());
		List<Vendor>				vendorList			= vendorService.findByCompanyId(userDetails.getCompany_id());
		
		Map<String, Object> model = new HashMap<String, Object>();

		String rootPath = request.getSession().getServletContext().getRealPath("/");

		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());

		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
				int i;
				// write file to server
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Count How many Import SuccessFully
		int CountSuccess = 0;
		int CountDuplicate = 0;
		int CountFailed = 0;
		List<String>  duplicateList = new ArrayList<String>();

		String[] nextLine;
		ValueObject	inputObj	= new ValueObject();
		try {
			try (FileReader fileReader = new FileReader(serverFile);
					CSVReader reader = new CSVReader(fileReader, ';', '\'', 1);) {
				while ((nextLine = reader.readNext()) != null) {

					for (int i = 0; i < nextLine.length; i++) {
						try {

							String[] importVehicle = nextLine[i].split(",");
							
							inputObj.put("userId", userDetails.getId());
							inputObj.put("companyId", userDetails.getCompany_id());
							
							if(importVehicle[0].trim().equalsIgnoreCase(PartType.PART_TYPE_CATEGORY_STANDARD_NAME)) {
								inputObj.put("partTypeCatgory", PartType.PART_TYPE_CATEGORY_STANDARD);
							}else if(importVehicle[0].trim().equalsIgnoreCase(PartType.PART_TYPE_CATEGORY_PARENT_NAME)) {
								inputObj.put("partTypeCatgory", PartType.PART_TYPE_CATEGORY_PARENT);
							}else if(importVehicle[0].trim().equalsIgnoreCase(PartType.PART_TYPE_CATEGORY_CHILD_NAME)) {
								inputObj.put("partTypeCatgory", PartType.PART_TYPE_CATEGORY_CHILD);
							}else {
								inputObj.put("partTypeCatgory", PartType.PART_TYPE_CATEGORY_STANDARD);
							}
							
							inputObj.put("partNumber", importVehicle[1].trim());
							inputObj.put("partName", importVehicle[2].trim());
							inputObj.put("partNameLocal", importVehicle[3].trim());
							inputObj.put("partNameOnBill", importVehicle[4].trim());
							
							for (PartType partType : partTypeList) {
								if(importVehicle[5].trim().equalsIgnoreCase(partType.getPartTypeName())) {
									inputObj.put("partType", partType.getPartTypeId());
									break;
								}
							}
							
							
							if(partManufacturers != null && !partManufacturers.isEmpty()) {
								for (PartManufacturer measurement : partManufacturers) {
									if(importVehicle[5].trim().equalsIgnoreCase(measurement.getPmName())) {
										inputObj.put("manufacturer", measurement.getPmid());
										break;
									}
								}
							}
							
							
							
							if(partManufacturers != null && !partManufacturers.isEmpty()) {
								for (PartManufacturer measurement : partManufacturers) {
									if(importVehicle[6].trim().equalsIgnoreCase(measurement.getPmName())) {
										inputObj.put("originalBrand", measurement.getPmid());
										break;
									}
								}
							}
							
							if(partCategories != null && !partCategories.isEmpty()) {
								for (PartCategories measurement : partCategories) {
									if(importVehicle[7].trim().equalsIgnoreCase(measurement.getPcName())) {
										inputObj.put("partCategory", measurement.getPcid());
										break;
									}
								}
							}
							
							inputObj.put("partSubCategory", importVehicle[8]);
							
							if(vehicleManList != null && !vehicleManList.isEmpty()) {
								String vehicleMake = "";
								for (VehicleManufacturer measurement : vehicleManList) {
									
									String[] vehicleManArr = importVehicle[9].trim().split("/");
									for (int j = 0; j < vehicleManArr.length; j++) {
										if(vehicleManArr[j].trim().equalsIgnoreCase(measurement.getVehicleManufacturerName())) {
											vehicleMake +=  measurement.getVehicleManufacturerId()+",";
											break;
										}
									}
								}
								inputObj.put("vehicleMake",Utility.removeLastComma(vehicleMake));
							}
							
							if(vehicleModalList != null && !vehicleModalList.isEmpty()) {
								String vehicleModel = "";
								for (VehicleModel measurement : vehicleModalList) {
									String[] vehicleMoArr = importVehicle[10].trim().split("/");
									for (int j = 0; j < vehicleMoArr.length; j++) {
										if(vehicleMoArr[j].trim().equalsIgnoreCase(measurement.getVehicleModelName())) {
											vehicleModel += measurement.getVehicleModelId()+",";
											break;
										}
									}
								}
								inputObj.put("vehicleModel", Utility.removeLastComma(vehicleModel));
							}
							
							if(importVehicle[11].trim() != "" && importVehicle[11].trim().equalsIgnoreCase("Yes")) {
								inputObj.put("warranty", true);
								inputObj.put("warrantyInMonth", importVehicle[12]);
							}else {
								inputObj.put("warranty", false);
							}
							
							if(importVehicle[13].trim() != "" && importVehicle[13].trim().equalsIgnoreCase("Yes")) {
								inputObj.put("couponAvailable", true);
								inputObj.put("couponDetails", importVehicle[14]);
							}else {
								inputObj.put("couponAvailable", false);
							}
							
							if(importVehicle[15].trim() != "" && importVehicle[15].trim().equalsIgnoreCase("Yes")) {
								inputObj.put("scrapAvailable", true);
							}else {
								inputObj.put("scrapAvailable", false);
							}
							
							inputObj.put("mainPacking", importVehicle[16].trim());
							
							if(measurementUnits != null && !measurementUnits.isEmpty()) {
								for (PartMeasurementUnit measurement : measurementUnits) {
									if(importVehicle[17].trim().equalsIgnoreCase(measurement.getPmuName())) {
										inputObj.put("unitTypeId", measurement.getPmuid());
										break;
									}
								}
							}
							
							inputObj.put("looseItem", importVehicle[18].trim());
							inputObj.put("uomLoose", importVehicle[19].trim());
							inputObj.put("barCodeNumber", importVehicle[20].trim());
							
							if(importVehicle[21].trim() != "" && importVehicle[21].trim().contains("custom")) {
								inputObj.put("itemType", 2);
							}else {
								inputObj.put("itemType", 1);
							}
							
							inputObj.put("Dimension", importVehicle[22].trim());
							
							if(importVehicle[23].trim() != "" && vendorList != null && !vendorList.isEmpty()) {
								String vendorIds = "";
								for (Vendor vendor : vendorList) {
									String[] vendorArr = importVehicle[23].trim().split("/");
									for (int j = 0; j < vendorArr.length; j++) {
										if( vendorArr[j].equalsIgnoreCase(vendor.getVendorName())) {
											vendorIds += vendor.getVendorId()+",";
											break;
										}
									}
								}
								inputObj.put("purchaseVendor", vendorIds);
							}
							
							if(importVehicle[24].trim() != "" && importVehicle[24].trim().equalsIgnoreCase("Yes")) {
								inputObj.put("repairable", true);
								if(importVehicle[25].trim() != "" && vendorList != null && !vendorList.isEmpty()) {
									String vendorIds = "";
									for (Vendor vendor : vendorList) {
										String[] vendorArr = importVehicle[25].trim().split("/");
										for (int j = 0; j < vendorArr.length; j++) {
											if( vendorArr[j].equalsIgnoreCase(vendor.getVendorName())) {
												vendorIds += vendor.getVendorId()+",";
												break;
											}
										}
									}
									inputObj.put("repairingVendor", vendorIds);
								}
							}else {
								inputObj.put("repairable", false);
							}
							
							if(inputObj.getShort("partTypeCatgory") > 0 && inputObj.getShort("partTypeCatgory") != PartType.PART_TYPE_CATEGORY_STANDARD) {
								
								
								List<MasterParts> mainPartList	=	masterPartsRepository.findAllPartBYCompanyId(userDetails.getCompany_id());
								
								
								if(importVehicle[26].trim() != "" && mainPartList != null && !mainPartList.isEmpty()) {
									String vendorIds = "";
									for (MasterParts vendor : mainPartList) {
										String[] vendorArr = importVehicle[26].trim().split("/");
										for (int j = 0; j < vendorArr.length; j++) {
											if(vendorArr[j].trim().equalsIgnoreCase(vendor.getPartnumber().trim()) || vendorArr[j].trim().equalsIgnoreCase(vendor.getPartname().trim())) {
												vendorIds += vendor.getPartid()+",";
												break;
											}
										}
									}
									inputObj.put("childPartDetails", vendorIds);
								}
							}else {
								inputObj.put("childPart", false);
							}
							
							if(importVehicle[27].trim() != "") {
								List<MasterParts> mainPartList	=	masterPartsRepository.findAllPartBYCompanyId(userDetails.getCompany_id());
								String vendorIds = "";
								for (MasterParts vendor : mainPartList) {
									String[] vendorArr = importVehicle[27].trim().split("/");
									for (int j = 0; j < vendorArr.length; j++) {
										if( vendorArr[j].equalsIgnoreCase(vendor.getPartnumber())) {
											vendorIds += vendor.getPartid()+",";
											break;
										}
									}
								}
								inputObj.put("subtituteParts", vendorIds);
							}
							
							if(importVehicle[28].trim() != "" && importVehicle[28].trim().equalsIgnoreCase("Yes")) {
								inputObj.put("assetIdRequired", true);
							}else {
								inputObj.put("assetIdRequired", false);
							}
							
							inputObj.put("description", importVehicle[29].trim());
							
							
							
						if(importVehicle.length > 30) {
							
							inputObj.put("unitPrice", importVehicle[30].trim());
							if(importVehicle.length > 31) {
								inputObj.put("discount", importVehicle[31].trim());
							}
							if(importVehicle.length > 32) {
								inputObj.put("tax", importVehicle[32].trim());
							}
						
						}
						System.err.println("manufacturer : "+inputObj.getInt("manufacturer",0));
						System.err.println("partCategory : "+inputObj.getInt("partCategory",0));
						System.err.println("unitTypeId : "+inputObj.getInt("unitTypeId",0));
						
						if(inputObj.getInt("manufacturer",0) <= 0 || inputObj.getInt("partCategory",0) <= 0 || inputObj.getInt("unitTypeId",0) <= 0) {
							CountFailed ++;
							System.err.println("not adedddddd....");
							continue;
						}
						
						inputObj.put("fromExcelEntry", true);
						ValueObject	outObj	=	masterPartsService.saveNewMasterPartsDetails(inputObj);
						
						if(outObj.containsKey("saved")) {
							CountSuccess ++; 
						}else {
							CountDuplicate ++;
							duplicateList.add(inputObj.getString("partNumber"));
						}
						
						model.put("CountSuccess", CountSuccess);
						model.put("CountDuplicate", CountDuplicate);
						model.put("CountFailed", CountFailed);
						if(CountDuplicate > 0) {
							model.put("importSaveAlreadyError", true);
							model.put("Duplicate", duplicateList);
							
						}
						
						} catch (Exception e) { 
							e.printStackTrace();
							try {
								model.put("MasterParts", masterPartsBL.prepareListofBean(masterPartsService.listMasterParts(1, userDetails.getCompany_id())));
								model.put("MasterPartsCount", masterPartsService.countpart(userDetails.getCompany_id()));
								model.put("importSaveError", true);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							return new ModelAndView("redirect:/newMasterParts/1.in", model);
						}

					} // for loop close

				}
			}
		} catch (Exception e) {
			try {
				model.put("MasterParts", masterPartsBL.prepareListofBean(masterPartsService.listMasterParts(1, userDetails.getCompany_id())));
				model.put("MasterPartsCount", masterPartsService.countpart(userDetails.getCompany_id()));
				model.put("importSaveError", true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return new ModelAndView("redirect:/newMasterParts/1.in", model);
		}

		// show the Vehicle List
		try {
			model.put("CountSuccess", CountSuccess);
			model.put("CountFailed", CountFailed);
			model.put("MasterParts", masterPartsBL.prepareListofBean(masterPartsService.listMasterParts(1, userDetails.getCompany_id())));
			model.put("MasterPartsCount", masterPartsService.countpart(userDetails.getCompany_id()));
			model.put("importSave", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return new ModelAndView("newMasterParts", model);
		return new ModelAndView("redirect:/newMasterParts/1.in", model);
	}

}
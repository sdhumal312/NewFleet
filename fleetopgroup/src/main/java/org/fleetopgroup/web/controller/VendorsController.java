package org.fleetopgroup.web.controller;
/**
 * @author fleetop
 *
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.MasterDocumentsConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.PurchaseOrderType;
import org.fleetopgroup.constant.VehicleFuelType;
import org.fleetopgroup.constant.VendorTypeConfigurationConstants;
import org.fleetopgroup.persistence.bl.BatteryBL;
import org.fleetopgroup.persistence.bl.ClothInventoryBL;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.InventoryPartInvoiceBL;
import org.fleetopgroup.persistence.bl.InventoryTyreInvoiceBL;
import org.fleetopgroup.persistence.bl.PurchaseOrdersBL;
import org.fleetopgroup.persistence.bl.ServiceEntriesBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.bl.VendorTypeBL;
import org.fleetopgroup.persistence.dao.VendorRepository;
import org.fleetopgroup.persistence.document.MasterDocuments;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.dto.ClothInvoiceDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadDto;
import org.fleetopgroup.persistence.dto.PartInvoiceDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorSequenceCounter;
import org.fleetopgroup.persistence.model.VendorType;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IMasterDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVendorMarketLorryHireDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeService;
import org.fleetopgroup.web.util.DateTimeUtility;
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

import com.google.gson.Gson;

@Controller
public class VendorsController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired 
	IVendorService 				vendorService;
	
	@Autowired 
	IVendorPaymentService   	vendorPaymentService;
	
	@Autowired 
	IMasterDocumentService 		masterDocumentService;
	
	@Autowired 
	IVendorTypeService 			VendorTypeService;
	
	@Autowired 
	IUserProfileService 		userProfileService;

	@Autowired 
	IFuelService 				fuelService;

	@Autowired 
	IServiceEntriesService 		ServiceEntriesService;
	
	@Autowired 
	IInventoryTyreService 		iventoryTyreService;

	@Autowired 
	IVendorSequenceService 		vendorSequenceService;

	@Autowired 
	IVendorTypeService 			vendorTypeService;

	@Autowired 
	CompanyConfigurationService companyConfigurationService;
	
	@Autowired 
	IPurchaseOrdersService 		PurchaseOrdersService;
	
	@Autowired
	IBatteryInvoiceService		batteryInvoiceService;
	
	@Autowired
	IPartInvoiceService			partInvoiceService;
	
	@Autowired
	IClothInventoryService		clothInventoryService;
	
	@Autowired	IVendorMarketLorryHireDetailsService	lorryHireDetailsService;
	
	@Autowired	VendorRepository						vendorRepository;
	
	@Autowired	IPendingVendorPaymentService			pendingVendorPaymentService;
	
	ServiceEntriesBL 			SEBL 				= new ServiceEntriesBL();
	FuelBL 						FuBL 				= new FuelBL();
	InventoryTyreInvoiceBL 		ITBL 				= new InventoryTyreInvoiceBL();
	BatteryBL 					bbl 				= new BatteryBL();
	PurchaseOrdersBL 			POBL 				= new PurchaseOrdersBL();
	VendorTypeBL 				VenType 			= new VendorTypeBL();
	VendorBL 					VenBL 				= new VendorBL();
	InventoryPartInvoiceBL		PartBL				= new InventoryPartInvoiceBL(); 
	ClothInventoryBL			ClothBl				= new ClothInventoryBL();
	SimpleDateFormat 			dateFormat	 		= new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat 				AMOUNT_FORMAT 		= new DecimalFormat("##,##,##0");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping(value = "/searchVendor", method = RequestMethod.POST)
	public ModelAndView searchVehicle(@ModelAttribute("command") VendorDto vendorDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			model.put("vendor", VenBL.prepareListofVendor(
					vendorService.SearchVendor(vendorDto.getVendorName(), userDetails.getCompany_id())));

			model.put("SelectType", "FUEL-VENDOR");
			model.put("SelectPage", 1);
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Vendor :", e);

		}
		return new ModelAndView("Vendor_Report", model);
	}

	/* show main page of Issues */
	@RequestMapping(value = "/vendor/{vendorType}/{pageNumber}", method = RequestMethod.GET)
	public String vendorList(@PathVariable("vendorType") Long vendorType,
			@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {
		HashMap<String, Object> 		configuration				= null;
		CustomUserDetails				userDetails 				= null;
		Integer							companyId					= 0;
		Boolean							isCommonMaster				= true;
		VendorType 						type 						= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
				isCommonMaster		= true;
			} else {
				companyId			= userDetails.getCompany_id();
				isCommonMaster		= false;
			}
			Page<Vendor> page = vendorService.getDeployment_Page_Vendor(vendorType, pageNumber,
					userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("VendorCount", page.getTotalElements());

			List<VendorDto> pageList = VenBL
					.prepareListofVendor(vendorService.listVendor(vendorType, pageNumber, userDetails.getCompany_id()));

			model.addAttribute("vendor", pageList);
			model.addAttribute("vendorType",
					VenType.VendorListofDto(VendorTypeService.findAllByCompanyId(companyId, isCommonMaster)));
			type = VendorTypeService.getVendorTypeByID(vendorType);
			model.addAttribute("SelectType", type.getVendor_TypeName());
			model.addAttribute("SelectTypeId", type.getVendor_Typeid());
			model.addAttribute("SelectPage", pageNumber);
			model.addAttribute("noVendorTypeFound", false);
			/*
			 * Object[] count = vehicleService.countTotalVehicle_AND_Ownership();
			 * 
			 * model.put("VehicleCount", (Long) count[0]); model.put("VehicleOwnedCount",
			 * (Long) count[1]);
			 */

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("vendors Page:", e);
			e.printStackTrace();
		}
		return "vendors";
	}

	@RequestMapping(value = "/addVendor")
	public ModelAndView addIssues() {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration				= null;
		CustomUserDetails				userDetails 				= null;
		Integer							companyId					= 0;
		Boolean							isCommonMaster				= true;
		HashMap<String, Object> 		vendorConfig				= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			vendorConfig		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
				isCommonMaster		= true;
			} else {
				companyId			= userDetails.getCompany_id();
				isCommonMaster		= false;
			}
			model.put("vendorConfig", vendorConfig);
			model.put("vendorType",
					VenType.VendorListofDto(VendorTypeService.findAllByCompanyId(companyId, isCommonMaster)));

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			userDetails	 	= null;
			configuration 	= null;
		}
		return new ModelAndView("New_vendor", model);
	}

	@RequestMapping(value = "/addVendorPaymentSheet")
	public ModelAndView addVendorPaymentSheet() throws Exception {
		Map<String, Object> 		model 				= null;
		CustomUserDetails 			userDetails 		= null;
		HashMap<String, Object>		configuration		= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			model				= new HashMap<String, Object>();
			model.put("Configuration", configuration);			
			return new ModelAndView("addVendorPaymentSheet", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model				= null;
		}
	}
	
	
	@RequestMapping(value = "/viewVendorPaymentList", method = RequestMethod.GET)
	public ModelAndView  viewVendorPaymentList() throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails 		= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			model.put(VendorTypeConfigurationConstants.VEHICLE_GROUP, 
					companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), 
					PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG).getOrDefault(VendorTypeConfigurationConstants.VEHICLE_GROUP, false));
			return new ModelAndView("viewVendorPaymentList", model);
		} catch (NullPointerException e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/vendorHome", method = RequestMethod.GET)
	public ModelAndView vendorHome() {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails 			userDetails 		= null;
		VendorType 					vendorType 			= null;
		HashMap<String, Object>  	configuration		= null;
		Integer						companyId			= 0;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
			} else {
				companyId			= userDetails.getCompany_id();
			}
			
			vendorType = VendorTypeService.getTopVendor(companyId);
			
			if (vendorType != null) {
				return new ModelAndView("redirect:/vendor/" + vendorType.getVendor_Typeid() + "/1.in");
			} else {
				model.put("noVendorTypeFound", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			userDetails 	= null;
			vendorType 		= null;
		}
		return new ModelAndView("vendors", model);
	}

	// save Issues in databases
	@RequestMapping(value = "/saveVendor", method = RequestMethod.POST)
	public ModelAndView saveVendor(@ModelAttribute("command") VendorDto vendorDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		VendorSequenceCounter sequenceCounter = null;
		// VendorType vendorType = null;
		Vendor vendor =	null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			List<Vendor> validate = vendorService.getVendorNametoall(vendorDto.getVendorName(),
					userDetails.getCompany_id());
			List<Vendor> ValidatePan = vendorService.getVendorPanNo(vendorDto.getVendorPanNO(),userDetails.getCompany_id());
			if (validate != null && !validate.isEmpty()) {
				return new ModelAndView("redirect:/addVendor.in?danger=true");
			} 
			else if(ValidatePan != null && !ValidatePan.isEmpty() && (boolean)configuration.get("validatePanNO"))
			{
				return new ModelAndView("redirect:/addVendor.in?PanNo=true");
			}
			else {
				// get the issues Dto to save issues
				vendor = VenBL.prepareVendor(vendorDto);
				
				if((int)configuration.get("fuelTypeId") != VendorBL.FUEL_TYPE_ID_STA) {
					vendor.setOwnPetrolPump((short) 0);
				}
				 
				sequenceCounter = vendorSequenceService.findNextVendorNumber(userDetails.getCompany_id());
				vendor.setVendorNumber((int) sequenceCounter.getNextVal());
				// save to databases
				vendorService.addVendor(vendor);

				// show the driver list in all
				model.put("saveVendor", true);
				
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/vendor/"+vendor.getVendorTypeId()+"/1.in?danger=true");
		}

		// return new ModelAndView("vendors", model);
		return new ModelAndView("redirect:/vendor/"+vendor.getVendorTypeId()+"/1.in?saveVendor=true");
	}

	@RequestMapping(value = "/updateVendor", method = RequestMethod.POST)
	public ModelAndView updateVendor(@ModelAttribute("command") VendorDto vendorDto, BindingResult result) throws Exception {
		CustomUserDetails 			userDetails 			= null;
		Vendor	 					vendorBL				= null;
		Vendor	 					findById				= null;
		List<Vendor>	 			findByName				= null;
		Map<String, Object> 		model 					= null;
		Object[] 					vendorCreditAmount 		= null; 
		double 						vendorCredit 			= 0;
		double 						serviceEntries 			= 0;
		double 						purchaseOrder 			= 0;
		double 						fuelCredit 				= 0;
		double 						batteryCredit 			= 0;
		double						tyreInventoryCredit		= 0;
		double						partCredit				= 0;
		double						upholsteryCredit		= 0;
		try {
			model 			= new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			HashMap<String, Object> 	configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			
			findById			= vendorRepository.findById(vendorDto.getVendorId(), userDetails.getCompany_id());
			findByName			= vendorRepository.findByNameList(vendorDto.getVendorName(), userDetails.getCompany_id());
			
			List<Vendor> ValidatePan = vendorService.getVendorPanNo(vendorDto.getVendorPanNO(),userDetails.getCompany_id());
			if(ValidatePan != null && !ValidatePan.isEmpty() && (boolean)configuration.get("validatePanNO"))
			{
				model.put("PanNo", "true");
				return new ModelAndView("edit_vendor", model);
			}
			//checking if any transaction of credit is done or not. If found then can not edit vendor type.
			vendorCreditAmount = vendorService.GET_VENDOR_CREDIT_AMOUNT_CountTotal_Cost_NotPaid(vendorDto.getVendorId());
			
			if (vendorCreditAmount != null) {
				if (vendorCreditAmount[0] != null) {
					serviceEntries = (Double) vendorCreditAmount[0];
				}

				if (vendorCreditAmount[1] != null) {
					purchaseOrder = (Double) vendorCreditAmount[1];
				}

				if (vendorCreditAmount[2] != null) {
					fuelCredit = (Double) vendorCreditAmount[2];
				}

				if (vendorCreditAmount[3] != null) {
					batteryCredit = (Double) vendorCreditAmount[3];
				}
				
				if (vendorCreditAmount[4] != null) {
					tyreInventoryCredit = (Double) vendorCreditAmount[4];
				}
				
				if (vendorCreditAmount[5] != null) {
					partCredit = Double.parseDouble(vendorCreditAmount[5]+"") ;
				}
				if (vendorCreditAmount[6] != null) {
					upholsteryCredit = Double.parseDouble(vendorCreditAmount[6]+"") ;
				}
				vendorCredit = vendorCredit + serviceEntries + purchaseOrder + fuelCredit + batteryCredit + tyreInventoryCredit+ partCredit + upholsteryCredit;
			}
			
			
			if(vendorCredit > 0 && findById.getVendorTypeId() != vendorDto.getVendorTypeId()) {
				model.put("alreadyExist", true);
				return new ModelAndView("redirect:/vendor/" + findById.getVendorTypeId() + "/1.in?vendorType=true");
			}
			
			if(vendorDto.getVendorName().equalsIgnoreCase(findById.getVendorName().trim())) {
				vendorBL = VenBL.prepareVendorForEdit(vendorDto,findById.getVendorName());
			}else {
				
				if(findByName != null && !findByName.isEmpty()) {
					model.put("alreadyExist", true);
					return new ModelAndView("redirect:/vendor/" + findById.getVendorTypeId() + "/1.in?alreadyExist=true");
				}else {
					vendorBL 	= VenBL.prepareVendorForEdit(vendorDto,vendorDto.getVendorName());
					model.put("successfullyUpdated", true);
				}
			}

			if((int)configuration.get("fuelTypeId") != VendorBL.FUEL_TYPE_ID_STA) {
				vendorBL.setOwnPetrolPump((short) 0);
			}
			vendorService.updateVendor(vendorBL);
			
			model.put("VendorCount", vendorService.countVendor(userDetails.getCompany_id()));
			model.put("VendorTypeCount", VendorTypeService.countByCompanyId(userDetails.getCompany_id()));
			
			return new ModelAndView("redirect:/vendor/" + vendorBL.getVendorTypeId() + "/1.in?updateVendor=true");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("err"+e);
			throw e;
		}

		// show the driver list in all
	}

	// save Issues in databases
	@RequestMapping(value = "/{vendorType}/ShowVendor")
	public ModelAndView ShowVendor_Page(@PathVariable("vendorType") String vendorType, @RequestParam("page") Integer pageNumber
			, @RequestParam("vendorId") final Integer vendorId, final HttpServletResponse result) {
		
		Map<String, Object> 			model 						= null;
		CustomUserDetails 				userDetails 				= null;
		VendorDto 						vendorDTO 					= null;
		VendorDto 						vendor 						= null; 
		Object[] 						vendorCreditAmount 			= null; 
		HashMap<String, Object>  		configuration				= null;
		Double 							vendorCredit 				= null;
		double 							serviceEntries 				= 0;
		double 							purchaseOrder 				= 0;
		double 							fuelCredit 					= 0;
		double 							batteryCredit 				= 0;
		double							tyreInventoryCredit			= 0;
		double							partCredit					= 0;
		double							upholsteryCredit			= 0;
		boolean							isLorryHireVendor			= false;
		HashMap<String, Object>  		vendorConfig				= null;
		try {
			model 			= new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendorDTO 		= vendorService.getVendorDetails(vendorId, userDetails.getCompany_id());
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			vendorConfig	=  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			model.put("vendorConfig", vendorConfig);
			model.put("SelectType", vendorType);
			model.put("SelectPage", pageNumber);
			if (vendorDTO != null) {
				// this show total amount in top
				vendor = VenBL.getVendorDetails(vendorDTO);
				model.put("vendor", vendor);
				model.put("configuration", configuration);
				
			Vendor	vendor2 =	vendorService.getLorryHireVendor(vendorId);
				
			if(vendor2 != null) {
				isLorryHireVendor	= true;
				model.put("isLorryHireVendor", isLorryHireVendor);
				model.put("amountToGet", lorryHireDetailsService.getLorryHireBalanceByVendorId(vendorId));
			}
			
				if(!isLorryHireVendor) {
					vendorCredit = pendingVendorPaymentService.getVendorPendingPaymentDetails(vendorId, userDetails.getCompany_id());
					if(vendorCredit != null) {
						if(vendorCredit > 0) {
							String TotalShow = AMOUNT_FORMAT.format(vendorCredit);
							AMOUNT_FORMAT.setMaximumFractionDigits(0);
							model.put("fuelPayVendorTotal", TotalShow);
						}
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			vendorDTO = null;
		}
		return new ModelAndView("show_vendor", model);
	}

	@RequestMapping(value = "/ShowVendor")
	public ModelAndView ShowVendors_Page(@RequestParam("vendorId") final Integer vendorId,
			final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		VendorDto vendorDTO = null;
		HashMap<String, Object>			configuration				= null;
		Object[] 						vendorCreditAmount 			= null; 
		Integer							companyId					= 0;
		double 							vendorCredit 				= 0;
		double 							serviceEntries 				= 0;
		double 							purchaseOrder 				= 0;
		double 							fuelCredit 					= 0;
		double 							batteryCredit 				= 0;
		double 							partCredit 					= 0;
		double							tyreInventoryCredit			= 0;
		double							upholsteryCredit			= 0;
		HashMap<String, Object>  		vendorConfig				= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
			} else {
				companyId			= userDetails.getCompany_id();
			}
			vendorConfig	=  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			model.put("vendorConfig", vendorConfig);
			vendorDTO = vendorService.getVendorDetails(vendorId, userDetails.getCompany_id());
			if (vendorDTO != null) {
				VendorType vendorType = VendorTypeService.getTopVendor(companyId);
				model.put("SelectType", vendorType.getVendor_Typeid());
				model.put("SelectPage", 1);
				// this show total amount in top
				VendorDto vendor = VenBL.getVendorDetails(vendorDTO);
				model.put("vendor", vendor);
				model.put("configuration", configuration);
					
				
					vendorCredit = pendingVendorPaymentService.getVendorPendingPaymentDetails(vendorId, userDetails.getCompany_id());
					if(vendorCredit > 0) {
						String TotalShow = AMOUNT_FORMAT.format(vendorCredit);
						AMOUNT_FORMAT.setMaximumFractionDigits(0);
						model.put("fuelPayVendorTotal", TotalShow);
					}
				}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			vendorDTO = null;
		}
		return new ModelAndView("show_vendor", model);
	}

	@RequestMapping(value = "/PrintVendor", method = RequestMethod.GET)
	public ModelAndView showvendorProfilePrint(@RequestParam("id") final Integer id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			VendorDto vendor = VenBL.getVendorDetails(vendorService.getVendorDetails(id, userDetails.getCompany_id()));
			model.put("vendor", vendor);
			// this show total amount in top
			List<Double> CreditAmount = fuelService.listOFCountTotal_Cost_NotPaid(vendor.getVendorId(),
					userDetails.getCompany_id());
			for (Double double1 : CreditAmount) {
				DecimalFormat df = new DecimalFormat("##,##,##0");
				df.setMaximumFractionDigits(0);
				if (double1 == null) {
					double1 = 0.0;
				}
				String TotalShow = df.format(double1);
				model.put("fuelPayVendorTotal", TotalShow);
				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showVendorPrint", model);
	}

	// save Issues in databases
	@RequestMapping(value = "/ShowVendorFuelPayment")
	public ModelAndView ShowVendorFuelPayment(@ModelAttribute("command") VendorDto vendorDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VendorDto vendor = VenBL.getVendorDetails(
					vendorService.getVendorDetails(vendorDto.getVendorId(), userDetails.getCompany_id()));
			model.put("vendor", vendor);
			// this show total amount in top
			/*
			 * List<Double> CreditAmount =
			 * fuelService.listOFCountTotal_Cost_NotPaid(vendor.getVendorName()) ; for
			 * (Double double1 : CreditAmount) { DecimalFormat df = new
			 * DecimalFormat("##,##,##0"); df.setMaximumFractionDigits(0); if (double1 ==
			 * null) { double1 = 0.0; } String TotalShow = df.format(double1);
			 * model.put("fuelPayVendorTotal", TotalShow); break;
			 * 
			 * }
			 */
			// this Show Recent 50 Entries in credit Fuel Vendor
			model.put("fuel", FuBL.prepareListofFuel(
					fuelService.listFuel_vendor_Credit_To_NotPaid(vendor.getVendorId(), userDetails.getCompany_id())));

			/*
			 * List<Double> paidAmount =
			 * fuelService.listOFCountTotal_Cost_Paid(vendor.getVendorName()); for (Double
			 * Paid : paidAmount) { DecimalFormat df = new DecimalFormat("##,##,##0");
			 * df.setMaximumFractionDigits(0); if (Paid == null) { Paid = 0.0; } String
			 * TotalPaid = df.format(Paid); model.put("fuelTotal", TotalPaid); break; }
			 */

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			userDetails = null;
		}

		return new ModelAndView("show_vendor_FuelPayment", model);
	}

	@RequestMapping(value = "/ShowVendorFuelCredit")
	public ModelAndView ShowVendorFuelCredit(@ModelAttribute("command") VendorDto vendorDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VendorDto vendor = VenBL.getVendorDetails(
					vendorService.getVendorDetails(vendorDto.getVendorId(), userDetails.getCompany_id()));

			model.put("vendor", vendor);

			// this Show Recent 50 Entries in credit Fuel Vendor
			model.put("fuel", FuBL.prepareListofFuel(
					fuelService.listFuel_vendor_Credit_To_NotPaid(vendor.getVendorId(), userDetails.getCompany_id())));

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			userDetails = null;
		}

		return new ModelAndView("show_vendor_FuelPayment", model);
	}

	@RequestMapping(value = "/ShowVendorFuelCash")
	public ModelAndView ShowVendorFuelCash(@ModelAttribute("command") VendorDto vendorDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VendorDto vendor = VenBL.getVendorDetails(
					vendorService.getVendorDetails(vendorDto.getVendorId(), userDetails.getCompany_id()));

			model.put("vendor", vendor);

			// this Get Recent Vendor Fuel amount Paid Entries
			model.put("fuel", FuBL.prepareListofFuel(
					fuelService.listFuel_vendor_Cash_To_Paid(vendor.getVendorId(), userDetails.getCompany_id())));

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			userDetails = null;
		}

		return new ModelAndView("show_vendor_FuelPayment", model);
	}

	@RequestMapping(value = "/ShowApprovalList")
	public ModelAndView ShowApprovalList(@ModelAttribute("command") VendorDto vendorDto, BindingResult result) {
		Map<String, Object> 			model 						= new HashMap<String, Object>();
		HashMap<String, Object>  		configuration				= null;
		CustomUserDetails 				userDetails					= null;
		VendorDto 						vendor						= null;
		VendorDto 						vendorBL						= null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			vendor 			= vendorService.getVendorDetails(vendorDto.getVendorId(), userDetails.getCompany_id());
			vendorBL 		= VenBL.getVendorDetails(vendor);
			model.put("vendor", vendorBL);
			model.put("configuration", configuration);
			

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return new ModelAndView("show_vendor_ApprovalList", model);
		
	}
	
	@RequestMapping(value = "/FilterApprovalList", method = RequestMethod.POST) // Fuel Vendor Approval List Flavor 2 srs
	public ModelAndView FilterApprovalList(@ModelAttribute("command") VendorDto vendorDto, FuelDto ApprovalToFuel,
			@RequestParam("fuelRange_daterange") final String fuelRange_daterange, final HttpServletRequest request) {
			Map<String, Object> 		model 				= null;
			CustomUserDetails 			userDetails 		= null;
			VendorDto 					vendor				= null;
			String 						vehicleGroupId 		= "";
			String						Vehicle_Ownership 	= ""; 
			String 						dateRangeFrom 		= ""; 
			String 						dateRangeTo 		= "";
			String 						ApprvalQuery 		= "";
			String[] 					From_TO_DateRange 	= null;
		try {
			model 			= new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vendor 			= VenBL.getVendorDetails(vendorService.getVendorDetails(vendorDto.getVendorId(), userDetails.getCompany_id()));
			
			if (fuelRange_daterange != "") {
				try {
					From_TO_DateRange = fuelRange_daterange.split(" to ");
					dateRangeFrom 		= From_TO_DateRange[0];
					dateRangeTo 		= From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				if (ApprovalToFuel.getVehicleGroupId() > 0) {
					vehicleGroupId = " AND V.vehicleGroupId=" + ApprovalToFuel.getVehicleGroupId() + "";
				}

				if (ApprovalToFuel.getVehicle_OwnershipId() > 0) {
					Vehicle_Ownership = " AND V.vehicleOwnerShipId=" + ApprovalToFuel.getVehicle_OwnershipId() + " ";
				}

				ApprvalQuery = " fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' " + vehicleGroupId
						+ " " + Vehicle_Ownership + "";

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/" + vendor.getVendorTypeId() + "/ShowVendor?vendorId=" + vendor.getVendorId() + "",model);
			}
			
			model.put("vendor", vendor);
			model.put("invoiceId", vendorDto.getSelectService_id());

			// This Show all Credit and Approval list to all Filter of the query
			model.put("fuel", FuBL.prepareListofFuel(fuelService.listFuel_vendor_APPROVALLISTFilter(vendor.getVendorId(), ApprvalQuery, userDetails.getCompany_id())));
			model.put("SelectType", vendor.getVendorTypeId());
			model.put("SelectPage", 1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("show_Filter_ApprovalList", model);
	}

	// THis Controller Tyre Filer Approval List
	@RequestMapping(value = "/FilterAllApprovalList", method = RequestMethod.POST)
	public ModelAndView FilterAllApprovalList(@ModelAttribute("command") VendorDto vendorDto,
			@RequestParam("vendorId") final Integer VendorId, 
			@RequestParam("AllSEARCH") final short AllSEARCH,
			@RequestParam("fuelRange_daterange") final String fuelRange_daterange, final HttpServletRequest request) {
		
			Map<String, Object> 			model 						= new HashMap<String, Object>();
			CustomUserDetails 				userDetails 				= null;
			VendorDto 						vendor						= null;
			String 							dateRangeFrom 				= "";
			String 							dateRangeTo 				= "";
			String 							ApprvalQuery 				= "";
			String 							ApprvalQueryAll 			= "";
			String 							PurchaseQuery 				= "";
			String[] 						From_TO_DateRange 			= null;
			List<ServiceEntriesDto>			ServiceEntriesDtoBL			= null;
			List<ServiceEntriesDto>			ServiceEntriesDto			= null;
			List<PurchaseOrdersDto> 		Purchase					= null;
			List<PurchaseOrdersDto> 		PurchaseBL					= null;
			List<InventoryTyreInvoiceDto> 	pageList					= null;
			List<InventoryTyreInvoiceDto> 	pageListBL					= null;
			List<InventoryTyreRetreadDto> 	pageListRetread				= null;
			List<InventoryTyreRetreadDto> 	pageListRetreadBL			= null;
			List<BatteryInvoiceDto> 		batteryInvoiceList			= null;
			List<BatteryInvoiceDto> 		batteryInvoiceListBL		= null;
			List<PartInvoiceDto> 			partInvoiceList				= null;
			List<PartInvoiceDto> 			partInvoiceListBL			= null;
			List<ClothInvoiceDto> 			clothInvoiceList			= null;
			List<ClothInvoiceDto> 			clothInvoiceListBL			= null;
		try {
			
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendor 		= VenBL.getVendorDetails(vendorService.getVendorDetails(VendorId, userDetails.getCompany_id()));
			model.put("vendor", vendor);
			model.put("invoiceID",vendorDto.getSelectService_id());
			// this show total amount in top

			if (fuelRange_daterange != "") {
				try {
					From_TO_DateRange = fuelRange_daterange.split(" to ");
					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				switch (AllSEARCH) {
				case ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES:
					ApprvalQuery = " SR.invoiceDate between '" + dateRangeFrom + "' AND '" + dateRangeTo
							+ "'  AND SR.companyId = " + userDetails.getCompany_id() + "" + " AND SR.markForDelete = 0";
					
					ServiceEntriesDto	= ServiceEntriesService.listServiceEntries_vendor_APPROVALLISTFilter(vendor.getVendorId(), ApprvalQuery, userDetails.getCompany_id());
					ServiceEntriesDtoBL = SEBL.prepareListofServiceEntries_Filter_Approval(ServiceEntriesDto);
					
					model.put("ServiceEntries",ServiceEntriesDtoBL);
					break;

				case ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER:

					PurchaseQuery = "AND p.purchaseorder_vendor_id=" + vendor.getVendorId()
							+ " AND p.purchaseorder_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID
							+ " AND p.purchaseorder_statusId ="+PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+"";
					Purchase 			= PurchaseOrdersService.Report_PurchaseOrders(PurchaseQuery,dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					PurchaseBL			= POBL.Vendor_Approal_ListofPurchaseOrders(Purchase);
					
					model.put("PurchaseOrder", PurchaseBL);

					break;

				case ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE:
					
					pageList			= iventoryTyreService.Filter_Vendor_creditList_InventoryTyreInvoice(VendorId, dateRangeFrom,dateRangeTo, userDetails.getCompany_id());
					pageListBL 			= ITBL.prepare_InventoryTyreInvoice_list(pageList);

					model.put("TyreInvoice", pageListBL);
					break;
				case ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD:

					pageListRetread		= iventoryTyreService.Filter_Vendor_creditList_InventoryTyreRetread(VendorId, dateRangeFrom,dateRangeTo, userDetails.getCompany_id());
					pageListRetreadBL 	= ITBL.FilterprepareListof_InventoryTyreRetreadDto(pageListRetread);

					model.put("TyreRetread", pageListRetreadBL);

					break;
				case ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE:
					
					batteryInvoiceList	= batteryInvoiceService.FilterVendorCreditListInventoryBatteryInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					batteryInvoiceListBL= bbl.prepareInventoryBatteryInvoiceList(batteryInvoiceList);
					
					model.put("BatteryInvoice", batteryInvoiceListBL);
					break;
				case ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE:
					partInvoiceList 	= partInvoiceService.FilterVendorCreditListInventoryPartInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					partInvoiceListBL 	= PartBL.prepareInventoryPartInvoiceList(partInvoiceList);
					model.put("PartInvoice", partInvoiceListBL);
					break;
				case ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE:
					clothInvoiceList 	= clothInventoryService.FilterVendorCreditListInventoryClothInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					clothInvoiceListBL 	= ClothBl.prepareInventoryClothInvoiceList(clothInvoiceList);
					model.put("ClothInvoice", clothInvoiceListBL);
					break;
				case ApprovalType.APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE:
					model.put("laundryInvoice", clothInventoryService.FilterVendorLaundryInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id()));
					break;
				default:
					ApprvalQueryAll = " SR.invoiceDate between '" + dateRangeFrom + "' AND '" + dateRangeTo
					+ "'  AND SR.companyId = " + userDetails.getCompany_id() + "" + " AND SR.markForDelete = 0";
					
					ServiceEntriesDto	= ServiceEntriesService.listServiceEntries_vendor_APPROVALLISTFilter(vendor.getVendorId(), ApprvalQueryAll, userDetails.getCompany_id());
					ServiceEntriesDtoBL = SEBL.prepareListofServiceEntries_Filter_Approval(ServiceEntriesDto);
					model.put("ServiceEntries",ServiceEntriesDtoBL);

					PurchaseQuery = "AND p.purchaseorder_vendor_id=" + vendor.getVendorId()
					+ " AND p.purchaseorder_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID
					+ " AND p.purchaseorder_statusId ="+PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+"";
					Purchase 			= PurchaseOrdersService.Report_PurchaseOrders(PurchaseQuery,dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					PurchaseBL			= POBL.Vendor_Approal_ListofPurchaseOrders(Purchase);
					model.put("PurchaseOrder", PurchaseBL);
					
					pageList			= iventoryTyreService.Filter_Vendor_creditList_InventoryTyreInvoice(VendorId, dateRangeFrom,dateRangeTo, userDetails.getCompany_id());
					pageListBL 			= ITBL.prepare_InventoryTyreInvoice_list(pageList);
					model.put("TyreInvoice", pageListBL);
					
					pageListRetread		= iventoryTyreService.Filter_Vendor_creditList_InventoryTyreRetread(VendorId, dateRangeFrom,dateRangeTo, userDetails.getCompany_id());
					pageListRetreadBL 	= ITBL.FilterprepareListof_InventoryTyreRetreadDto(pageListRetread);
					model.put("TyreRetread", pageListRetreadBL);

					batteryInvoiceList	= batteryInvoiceService.FilterVendorCreditListInventoryBatteryInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					batteryInvoiceListBL= bbl.prepareInventoryBatteryInvoiceList(batteryInvoiceList);
					model.put("BatteryInvoice", batteryInvoiceListBL);

					partInvoiceList 	= partInvoiceService.FilterVendorCreditListInventoryPartInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					partInvoiceListBL 	= PartBL.prepareInventoryPartInvoiceList(partInvoiceList);
					model.put("PartInvoice", partInvoiceListBL);
					
					clothInvoiceList 	= clothInventoryService.FilterVendorCreditListInventoryClothInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					clothInvoiceListBL 	= ClothBl.prepareInventoryClothInvoiceList(clothInvoiceList);
					model.put("ClothInvoice", clothInvoiceListBL);
					
					model.put("laundryInvoice", clothInventoryService.FilterVendorLaundryInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id()));
					
					break;
				}

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/ShowVendor?vendorId=" + vendor.getVendorId() + "", model);
			}

			model.put("SelectType", vendor.getVendorTypeId());
			model.put("SelectPage", 1);

			// This Show all Credit and Approval list to all Filter of the query

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("show_All_Filter_ApprovalList", model);
	}

	// Inventory List to json
	@RequestMapping(value = "/getVendorFuelPaymentList", method = RequestMethod.GET)
	public void getInventoryList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<FuelDto> Driver = new ArrayList<FuelDto>();
		List<Fuel> fueltype = fuelService.listFuel(1, userDetails);
		if (fueltype != null && !fueltype.isEmpty()) {
			for (Fuel fuelDto : fueltype) {
				FuelDto fuel = new FuelDto();
				/* wadd.setDriver_id(add.getDriver_id()); */
				fuel.setFuel_id(fuelDto.getFuel_id());

				fuel.setVid(fuelDto.getVid());
				// get Vehicle id to Vehicle name
				// fuel.setVehicle_registration(fuelDto.getVehicle_registration());
				fuel.setFuel_date(dateFormat.format(fuelDto.getFuel_date()));
				fuel.setFuel_meter(fuelDto.getFuel_meter());
				fuel.setFuel_type(VehicleFuelType.getVehicleFuelName(fuelDto.getFuelTypeId()));
				fuel.setFuel_liters(fuelDto.getFuel_liters());
				fuel.setFuel_price(fuelDto.getFuel_price());

				// fuel.setVehicle_group(fuelDto.getVehicle_group());
				fuel.setVendor_id(fuelDto.getVendor_id());
				// fuel.setVendor_name(fuelDto.getVendor_name());
				// fuel.setVendor_location(fuelDto.getVendor_location());
				fuel.setFuel_payment(PaymentTypeConstant.getPaymentTypeName(fuelDto.getPaymentTypeId()));

				fuel.setFuel_tank_partial(fuelDto.getFuel_tank_partial());

				fuel.setFuel_usage(fuelDto.getFuel_usage());
				fuel.setFuel_amount(fuelDto.getFuel_amount());

				Driver.add(fuel);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}

	// save Issues in databases
	@RequestMapping(value = "/{vendorType}/editVendor")
	public ModelAndView editVendor(@PathVariable("vendorType") String vendorType,
			@RequestParam("page") Integer pageNumber, @RequestParam("vendorId") final Integer vendorId,
			final HttpServletResponse result) throws Exception {
		HashMap<String, Object> 		configuration				= null;
		CustomUserDetails				userDetails 				= null;
		Integer							companyId					= 0;
		Boolean							isCommonMaster				= true;
		VendorDto 						vendor 						= null;
		HashMap<String, Object> 		vendorConfig				= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			vendorConfig		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
				isCommonMaster		= true;
			} else {
				companyId			= userDetails.getCompany_id();
				isCommonMaster		= false;
			}
			Map<String, Object> model = new HashMap<String, Object>();
			vendor = vendorService.getVendorDetails(vendorId, userDetails.getCompany_id());
			// show the Issues List
			model.put("vendorConfig", vendorConfig);
			if (vendor != null) {

				model.put("vendor", VenBL.getVendorDetails(vendor));
				
				// show the Vendor Type List
				model.put("vendorType",
						VenType.VendorListofDto(VendorTypeService.findAllByCompanyId(companyId, isCommonMaster)));
			}

			model.put("SelectType", vendorType);
			model.put("SelectPage", pageNumber);
			return new ModelAndView("edit_vendor", model);
		} catch (Exception e) {
			throw e;
		}
	}

	// save Issues in databases
	@RequestMapping(value = "/{vendorType}/deleteVendor")
	public ModelAndView deleteVendor(@PathVariable("vendorType") String vendorType,
			@RequestParam("page") Integer pageNumber, @RequestParam("vendorId") final Integer vendorId,
			final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			boolean validate = true;
			// save to databases
			List<Fuel> ValidateVendor = fuelService.Vendor_delete_ValidateIn_Fuel(vendorId,
					userDetails.getCompany_id());

			if (ValidateVendor != null && !ValidateVendor.isEmpty()) {
				validate = false;
				model.put("RemoveFuel", true);
			}
			if (validate) {
				vendorService.deleteVendor(vendorId, userDetails.getCompany_id());
				// show the driver list in all
				model.put("deleteVendor", true);
			}
		} catch (Exception e) {
			// show the driver list in all
			e.printStackTrace();
			model.put("errorVendor", true);
			return new ModelAndView("redirect:/vendor/" + vendorType + "/" + pageNumber + ".in", model);

		}

		return new ModelAndView("redirect:/vendor/" + vendorType + "/" + pageNumber + ".in", model);
	}
	
	// Image Document the Document id
	@RequestMapping(value = "/downloadvendordocument", method = RequestMethod.GET)
	public String download(HttpServletResponse response) {
		try {
			// Lookup file by fileId in database.
			MasterDocuments file = masterDocumentService.getMasterDocuemntByDocumentTypeId(MasterDocumentsConstant.MASTER_DOCUMENT_VENDOR);
			// Check if file is actually retrieved from database.
			if (file != null) {
				if (file.getContent() != null) {
					response.setHeader("Content-Disposition", "inline;filename=\"" + file.getName() + "\"");
					OutputStream out = response.getOutputStream();
					response.setContentType(file.getContentType());
					response.getOutputStream().write(file.getContent());
					out.flush();
					out.close();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return null;
	}
	
	@SuppressWarnings({ "resource", "deprecation" })
	@RequestMapping(value = "/importVendors", method = RequestMethod.POST)
	public ModelAndView saveImport(@ModelAttribute("command") VehicleDocument photo,
			@RequestParam("import") MultipartFile file, HttpServletRequest request) throws Exception {

		Map<String, Object> 				model 						= null; 
		CustomUserDetails					userDetails					= null;
		String 								rootPath 					= null;
		File 								dir 						= null;
		File 								serverFile 					= null;
		FileInputStream 					fis 						= null;
		XSSFWorkbook 						myWorkBook 					= null; 
		XSSFSheet 							mySheet 					= null;
		Iterator<Row> 						rowIterator 				= null; 
		VendorSequenceCounter 				sequenceCounter 			= null;
		Vendor 								vendor 						= null;
		HashMap<String, Object>				configuration				= null;
		Integer								companyId					= 0;
		Boolean								isCommonMaster				= true;
		List<VendorType>					vendorTypeList				= null;
		ArrayList<PaymentTypeConstant>		paymentTypeConstantList		= null;
		int 								noOfRows					= 0;

		try {
			model 			= new HashMap<String, Object>();
			rootPath 		= request.getSession().getServletContext().getRealPath("/");
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);

			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
				isCommonMaster		= true;
			} else {
				companyId			= userDetails.getCompany_id();
				isCommonMaster		= false;
			}

			vendorTypeList	= vendorTypeService.findAllByCompanyId(companyId, isCommonMaster);
			paymentTypeConstantList	= PaymentTypeConstant.getPaymentTypeList();

			dir 			= new File(rootPath + File.separator + "uploadedfile");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());

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

			fis = new FileInputStream(serverFile);

			// Finds the workbook instance for XLSX file
			myWorkBook = new XSSFWorkbook (fis);

			// Return first sheet from the XLSX workbook
			mySheet = myWorkBook.getSheetAt(0);

			// Get iterator to all the rows in current sheet
			rowIterator = mySheet.iterator();
			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			// Traversing over each row of XLSX file
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				noOfRows += 1;
				if(noOfRows == 1 || noOfRows == 2) {
					continue;
				}

				vendor	= new Vendor();
				sequenceCounter = vendorSequenceService.findNextVendorNumber(userDetails.getCompany_id());
				vendor.setVendorNumber((int) sequenceCounter.getNextVal());
				vendor.setCreatedById(userDetails.getId());
				vendor.setLastModifiedById(userDetails.getId());
				vendor.setCreated(toDate);
				vendor.setLastupdated(toDate);
				vendor.setCompanyId(userDetails.getCompany_id());
				vendor.setMarkForDelete(false);
				vendor.setAutoVendor(false);

				Cell cell	= null;
				String cellValue	= null;
				for(int i=0; i < 28; i++) {
					cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						cellValue	= cell.getStringCellValue().toUpperCase();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellValue	= Long.toString((long)cell.getNumericCellValue());
						break;
					default:
						cellValue	= null;
						break;
					}
					
					if(i == 0) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorName(cellValue);
						} else {
							vendor	= null;
							break;
						}
					}
					if(i == 1) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorPhone(cellValue);
						} else {
							vendor	= null;
							break;
						}
					}
					if(i == 2) {
						if(cellValue != null && !cellValue.isEmpty()) {
							for(VendorType vendorType : vendorTypeList) {
								if(vendorType.getVendor_TypeName().equalsIgnoreCase(cellValue)) {
									vendor.setVendorTypeId(vendorType.getVendor_Typeid());
								}
							}
						} else {
							vendor	= null;
							break;
						}
					}
					if(i == 3) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorLocation(cellValue);
						} else {
							vendor	= null;
							break;
						}
					}
					if(i == 4) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorWebsite(cellValue);
						} else {
							vendor.setVendorWebsite(null);
						}
					}
					if(i == 5) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorAddress1(cellValue);
						} else {
							vendor.setVendorAddress1(null);
						}
					}
					if(i == 6) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorAddress2(cellValue);
						} else {
							vendor.setVendorAddress2(null);
						}
					}
					if(i == 7) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorCountry(cellValue);
						} else {
							vendor.setVendorCountry(null);
						}
					}
					if(i == 8) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorState(cellValue);
						} else {
							vendor.setVendorState(null);
						}
					}
					if(i == 9) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorCity(cellValue);
						} else {
							vendor.setVendorCity(null);
						}
					}
					if(i == 10) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorPincode(cellValue);
						} else {
							vendor.setVendorPincode(null);
						}
					}
					if(i == 11) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorRemarks(cellValue);
						} else {
							vendor.setVendorRemarks(null);
						}
					}
					if(i == 12) {
						if(cellValue != null && !cellValue.isEmpty()) {
							for(PaymentTypeConstant paymentTypeConstant	: paymentTypeConstantList) {
								if(paymentTypeConstant.getPaymentTypeName().equalsIgnoreCase(cellValue)) {
									vendor.setVendorTermId(paymentTypeConstant.getPaymentTypeId());
								}
							}
						} else {
							vendor	= null;
							break;
						}
					}
					if(i == 13) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorPanNO(cellValue);
						} else {
							vendor.setVendorPanNO(null);
						}
					}
					if(i == 14) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorGSTNO(cellValue);
						} else {
							vendor.setVendorGSTNO(null);
						}
					}
					if(i == 15) {
						if(cellValue != null && !cellValue.isEmpty()) {
							if(cellValue.equalsIgnoreCase("Turnover Below 25 lakhs")) {
								vendor.setVendorGSTRegisteredId((short)1);
							} else {
								vendor.setVendorGSTRegisteredId((short)2);
							}
						} else {
							vendor	= null;
							break;
						}
					}
					if(i == 16) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorCreditLimit(cellValue);
						} else {
							vendor.setVendorCreditLimit(null);
						}
					}
					if(i == 17) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorAdvancePaid(cellValue);
						} else {
							vendor.setVendorAdvancePaid(null);
						}
					}
					if(i == 18) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorFirName(cellValue);
						} else {
							vendor	= null;
							break;
						}
					}
					if(i == 19) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorFirPhone(cellValue);
						} else {
							vendor	= null;
							break;
						}
					}
					if(i == 20) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorFirEmail(cellValue);
						} else {
							vendor	= null;
							break;
						}
					}
					if(i == 21) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorSecName(cellValue);
						} else {
							vendor.setVendorSecName(null);
						}
					}
					if(i == 22) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorSecPhone(cellValue);
						} else {
							vendor.setVendorSecPhone(null);
						}
					}
					if(i == 23) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorSecEmail(cellValue);
						} else {
							vendor.setVendorSecEmail(null);
						}
					}
					if(i == 24) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorBankName(cellValue);
						} else {
							vendor.setVendorBankName(null);
						}
					}
					if(i == 25) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorBankBranch(cellValue);
						} else {
							vendor.setVendorBankBranch(null);
						}
					}
					if(i == 26) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorBankAccno(cellValue);
						} else {
							vendor.setVendorBankAccno(null);
						}
					}
					if(i == 27) {
						if(cellValue != null && !cellValue.isEmpty()) {
							vendor.setVendorBankIfsc(cellValue);
						} else {
							vendor.setVendorBankIfsc(null);
						}
					}
				}
				
				if(vendor	!= null) {
					List<Vendor> validate = vendorService.getVendorNametoall(vendor.getVendorName(),userDetails.getCompany_id());
					if (validate == null || validate.isEmpty()) {
						vendorService.addVendor(vendor);
					} 
				}
			}
			return new ModelAndView("redirect:/vendorHome.in", model);
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			throw e;
		}
	}
	
	//Dev M Code
	/*@RequestMapping(value = "/VENREP")
	public ModelAndView VendorReport() throws Exception {
		Map<String, Object> 		model 				= null;
		try {
			model				= new HashMap<String, Object>();
			
			return new ModelAndView("VENREP", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model				= null;
		}
	}*/
	
	
	@RequestMapping(value = "/VENREP")
	public ModelAndView VendorReport() throws Exception {
		Map<String, Object> 		model 				= null;
		try {
			model				= new HashMap<String, Object>();
			
			return new ModelAndView("VENREP", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model				= null;
		}
	}

	@RequestMapping(value = "/VendorPaymentReport")
	public ModelAndView VendorPaymentReport() throws Exception {
		Map<String, Object> 		model 				= null;
		
		CustomUserDetails userDetails = null;
		HashMap<String, Object>     configuration	= null;
		
		try {
			model				= new HashMap<String, Object>();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			
			
			model.put("configuration",configuration);
			return new ModelAndView("VendorPaymentReport", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model				= null;
		}
	}
	
	//Dev M Code
	/*@RequestMapping(value = "/VendorGstReport")
	public ModelAndView VendorGstReport() throws Exception {
		Map<String, Object> 		model 				= null;
		try {
			model				= new HashMap<String, Object>();
			
			return new ModelAndView("VendorGstReport", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model				= null;
		}
	}*/
	
	@RequestMapping(value = "/VendorGstReport")
	public ModelAndView VendorGstReport() throws Exception {
		Map<String, Object> 		model 				= null;
		try {
			model				= new HashMap<String, Object>();
			
			return new ModelAndView("VendorGstReport", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model				= null;
		}
	}
	
	/* show main page of View All Vendor Payment */
	@RequestMapping(value = "/editVendorPayment", method = RequestMethod.GET)
	public String editVendorPayment(@RequestParam("vendorPaymentId") Long vendorPaymentId, Model model) throws Exception {
		CustomUserDetails 			userDetails 		= null;
		HashMap<String, Object>		configuration		= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			model.addAttribute("configuration",configuration);
			model.addAttribute("vendorPaymentId", vendorPaymentId);
			return "editVendorPayment";
		} catch (NullPointerException e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/FilterAllApprovalPaymentList", method = RequestMethod.POST)
	public ModelAndView FilterApprovalPaymnentList(@ModelAttribute("command") VendorDto vendorDto,
			@RequestParam("vendorId") final Integer VendorId, 
			@RequestParam("AllSEARCH") final short AllSEARCH,
			@RequestParam("fuelRange_daterange") final String fuelRange_daterange, final HttpServletRequest request) {
		
			Map<String, Object> 			model 						= new HashMap<String, Object>();
			CustomUserDetails 				userDetails 				= null;
			VendorDto 						vendor						= null;
			String 							dateRangeFrom 				= "";
			String 							dateRangeTo 				= "";
			String 							ApprvalQuery 				= "";
			String 							ApprvalQueryAll 			= "";
			String 							PurchaseQuery 				= "";
			String[] 						From_TO_DateRange 			= null;
			List<ServiceEntriesDto>			ServiceEntriesDtoBL			= null;
			List<ServiceEntriesDto>			ServiceEntriesDto			= null;
			List<PurchaseOrdersDto> 		Purchase					= null;
			List<PurchaseOrdersDto> 		PurchaseBL					= null;
			List<InventoryTyreInvoiceDto> 	pageList					= null;
			List<InventoryTyreInvoiceDto> 	pageListBL					= null;
			List<InventoryTyreRetreadDto> 	pageListRetread				= null;
			List<InventoryTyreRetreadDto> 	pageListRetreadBL			= null;
			List<BatteryInvoiceDto> 		batteryInvoiceList			= null;
			List<BatteryInvoiceDto> 		batteryInvoiceListBL		= null;
			List<PartInvoiceDto> 			partInvoiceList				= null;
			List<PartInvoiceDto> 			partInvoiceListBL			= null;
			HashMap<String, Object>			configuration				= null;
			List<ClothInvoiceDto> 			clothInvoiceList			= null;
			List<ClothInvoiceDto> 			clothInvoiceListBL			= null;
		try {
			
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			model.put("configuration", configuration);
			vendor 			= VenBL.getVendorDetails(vendorService.getVendorDetails(VendorId, userDetails.getCompany_id()));
			model.put("vendor", vendor);
			// this show total amount in top

			if (fuelRange_daterange != "") {
				try {
					From_TO_DateRange = fuelRange_daterange.split(" to ");
					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;
					
					model.put("VendorId", VendorId);
					model.put("dateRangeFrom", dateRangeFrom);
					model.put("dateRangeTo", dateRangeTo);

				} catch (Exception e) {
					LOGGER.debug("fuelmileage_vid:", e);
				}

				switch (AllSEARCH) {
				case ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES:
					ApprvalQuery = " SR.invoiceDate between '" + dateRangeFrom + "' AND '" + dateRangeTo
							+ "'  AND SR.companyId = " + userDetails.getCompany_id() + "" + " AND SR.markForDelete = 0";
					
					ServiceEntriesDto	= ServiceEntriesService.listServiceEntries_vendor_APPROVALLISTFilter(vendor.getVendorId(), ApprvalQuery, userDetails.getCompany_id());
					ServiceEntriesDtoBL = SEBL.prepareListofServiceEntries_Filter_Approval(ServiceEntriesDto);
					
					model.put("ServiceEntries",ServiceEntriesDtoBL);
					break;

				case ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER:

					PurchaseQuery = "AND p.purchaseorder_vendor_id=" + vendor.getVendorId()
							+ " AND (p.purchaseorder_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " OR "
							+ " p.purchaseorder_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID + ")  "
							+ " AND p.purchaseorder_statusId ="+PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+"";
					Purchase 			= PurchaseOrdersService.Report_PurchaseOrders(PurchaseQuery,dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					PurchaseBL			= POBL.Vendor_Approal_ListofPurchaseOrders(Purchase);
					
					model.put("PurchaseOrder", PurchaseBL);

					break;

				case ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE:
					
					pageList			= iventoryTyreService.Filter_Vendor_creditList_InventoryTyreInvoice(VendorId, dateRangeFrom,dateRangeTo, userDetails.getCompany_id());
					pageListBL 			= ITBL.prepare_InventoryTyreInvoice_list(pageList);

					model.put("TyreInvoice", pageListBL);
					break;
				case ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD:

					pageListRetread		= iventoryTyreService.Filter_Vendor_creditList_InventoryTyreRetread(VendorId, dateRangeFrom,dateRangeTo, userDetails.getCompany_id());
					
					pageListRetreadBL 	= ITBL.FilterprepareListof_InventoryTyreRetreadDto(pageListRetread);

					model.put("TyreRetread", pageListRetreadBL);

					break;
				case ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE:
					
					batteryInvoiceList	= batteryInvoiceService.FilterVendorCreditListInventoryBatteryInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					batteryInvoiceListBL= bbl.prepareInventoryBatteryInvoiceList(batteryInvoiceList);
					
					model.put("BatteryInvoice", batteryInvoiceListBL);
					break;
				case ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE:
					partInvoiceList 	= partInvoiceService.FilterVendorCreditListInventoryPartInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					partInvoiceListBL 	= PartBL.prepareInventoryPartInvoiceList(partInvoiceList);
					model.put("PartInvoice", partInvoiceListBL);
					break;
				
				case ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE:
					clothInvoiceList 	= clothInventoryService.FilterVendorCreditListInventoryClothInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					clothInvoiceListBL 	= ClothBl.prepareInventoryClothInvoiceList(clothInvoiceList);
					model.put("ClothInvoice", clothInvoiceListBL);
					break;
					
				default:
					ApprvalQueryAll = " SR.invoiceDate between '" + dateRangeFrom + "' AND '" + dateRangeTo
					+ "'  AND SR.companyId = " + userDetails.getCompany_id() + "" + " AND SR.markForDelete = 0";
					
					ServiceEntriesDto	= ServiceEntriesService.listServiceEntries_vendor_APPROVALLISTFilter(vendor.getVendorId(), ApprvalQueryAll, userDetails.getCompany_id());
					ServiceEntriesDtoBL = SEBL.prepareListofServiceEntries_Filter_Approval(ServiceEntriesDto);
					model.put("ServiceEntries",ServiceEntriesDtoBL);

					PurchaseQuery = "AND p.purchaseorder_vendor_id=" + vendor.getVendorId()
					+ " AND (p.purchaseorder_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " OR "
					+ " p.purchaseorder_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID + " AND p.balanceAmount > 0) "
					+ " AND p.purchaseorder_statusId ="+PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+"";
					Purchase 			= PurchaseOrdersService.Report_PurchaseOrders(PurchaseQuery,dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					PurchaseBL			= POBL.Vendor_Approal_ListofPurchaseOrders(Purchase);
					model.put("PurchaseOrder", PurchaseBL);
					
					pageList			= iventoryTyreService.Filter_Vendor_creditList_InventoryTyreInvoice(VendorId, dateRangeFrom,dateRangeTo, userDetails.getCompany_id());
					pageListBL 			= ITBL.prepare_InventoryTyreInvoice_list(pageList);
					model.put("TyreInvoice", pageListBL);
					
					pageListRetread		= iventoryTyreService.Filter_Vendor_creditList_InventoryTyreRetread(VendorId, dateRangeFrom,dateRangeTo, userDetails.getCompany_id());
					pageListRetreadBL 	= ITBL.FilterprepareListof_InventoryTyreRetreadDto(pageListRetread);
					model.put("TyreRetread", pageListRetreadBL);

					batteryInvoiceList	= batteryInvoiceService.FilterVendorCreditListInventoryBatteryInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					batteryInvoiceListBL= bbl.prepareInventoryBatteryInvoiceList(batteryInvoiceList);
					model.put("BatteryInvoice", batteryInvoiceListBL);

					partInvoiceList 	= partInvoiceService.FilterVendorCreditListInventoryPartInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					partInvoiceListBL 	= PartBL.prepareInventoryPartInvoiceList(partInvoiceList);
					model.put("PartInvoice", partInvoiceListBL);
					
					clothInvoiceList 	= clothInventoryService.FilterVendorCreditListInventoryClothInvoice(VendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					clothInvoiceListBL 	= ClothBl.prepareInventoryClothInvoiceList(clothInvoiceList);
					model.put("ClothInvoice", clothInvoiceListBL);
					
					break;
				}

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/ShowVendor?vendorId=" + vendor.getVendorId() + "", model);
			}

			model.put("SelectType", vendor.getVendorTypeId());
			model.put("SelectPage", 1);

			// This Show all Credit and Approval list to all Filter of the query

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("show_All_Filter_ApprovalPaymentList", model);
	}
	
	
	@RequestMapping(value = "/FilterFuelApprovalList", method = RequestMethod.POST)
	public ModelAndView FilterFuelApprovalList(@ModelAttribute("command") VendorDto vendorDto, FuelDto ApprovalToFuel,
		@RequestParam("fuelRange_daterange") final String fuelRange_daterange, final HttpServletRequest request) {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails 		= null;
		Integer 					VendorId			= null;
		VendorDto 					vendor				= null;
		VendorDto 					vendorBL			= null;
		String					 	vehicleGroupId 		= "";
		String						Vehicle_Ownership 	= "";
		String						dateRangeFrom 		= "";
		String						dateRangeTo 		= "";
		String 						ApprvalQuery 		= "";
		List<FuelDto>				fuelDto				= null;
		List<FuelDto>				fuelDtoBL			= null;
		try {	
			VendorId= vendorDto.getVendorId();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendor		= vendorService.getVendorDetails(vendorDto.getVendorId(), userDetails.getCompany_id());
			vendorBL 	= VenBL.getVendorDetails(vendor);

			model.put("vendor", vendorBL);
			// this show total amount in top
			if (fuelRange_daterange != "") {
				String[] From_TO_DateRange = null;
				try {
					From_TO_DateRange 	= fuelRange_daterange.split(" to ");

					dateRangeFrom 		= From_TO_DateRange[0];
					dateRangeTo 		= From_TO_DateRange[1];

				} catch (Exception e) {
					LOGGER.debug("fuelmileage_vid:", e);
				}
				
				model.put("VendorId", VendorId);
				model.put("dateRangeFrom", dateRangeFrom);
				model.put("dateRangeTo", dateRangeTo);
				
				if(ApprovalToFuel.getVehicleGroupIdStr() != null && ApprovalToFuel.getVehicleGroupIdStr() != "") {
					ApprovalToFuel.setVehicleGroupId(Long.parseLong(ApprovalToFuel.getVehicleGroupIdStr()));
				}
				
				if (ApprovalToFuel.getVehicleGroupId() > 0) {
					vehicleGroupId = " AND V.vehicleGroupId=" + ApprovalToFuel.getVehicleGroupId() + "";
				}

				if (ApprovalToFuel.getVehicle_OwnershipId() > 0) {
					Vehicle_Ownership = " AND V.vehicleOwnerShipId=" + ApprovalToFuel.getVehicle_OwnershipId() + " ";
				}

			ApprvalQuery = " fuel_date between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' " + vehicleGroupId + " " + Vehicle_Ownership + "";

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/" + vendor.getVendorTypeId() + "/ShowVendor?vendorId=" + vendor.getVendorId() + "", model);
			}

			// This Show all Credit and Approval list to all Filter of the query
			fuelDto 	= fuelService.listFuel_vendor_APPROVALLISTFilter(vendor.getVendorId(),ApprvalQuery, userDetails.getCompany_id());
			fuelDtoBL 	= FuBL.prepareListofFuel(fuelDto);
			model.put("fuel",fuelDtoBL);

			model.put("SelectType", vendor.getVendorTypeId());
			model.put("SelectPage", 1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("show_Filter_Fuel_ApprovalList", model);
	}
	
	//Vendor Payment History Report Step 1
	@RequestMapping("/VendorPaymentHistoryReport")
	public ModelAndView VendorPaymentHistoryReport() {
		
		Map<String, Object> model = new HashMap<String, Object>();
				
		return new ModelAndView("LorryHirePaymenReport", model);
	}
	//Vendor Payment History Report Step 1
	
	@RequestMapping(value = "/getOtherVendorSearchList", method = RequestMethod.POST)
	public void getOtherVendorSearchList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Vendor> addresses = new ArrayList<Vendor>();
			List<Vendor> vendor = vendorService.SearchOnly_Other_VendorName(term, userDetails.getCompany_id());
			if (vendor != null && !vendor.isEmpty()) {
				for (Vendor add : vendor) {
					Vendor wadd = new Vendor();
					wadd.setVendorId(add.getVendorId());
					wadd.setVendorName(add.getVendorName());
					wadd.setVendorLocation(add.getVendorLocation());

					addresses.add(wadd);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}

	}
	
	@RequestMapping(value = "/VendorWisePaymentStatusReport", method = RequestMethod.GET)
	public ModelAndView VendorWisePaymentStatusReport(final HttpServletRequest request) {
		ModelAndView 					map 			= null;
		try {
			map = new ModelAndView("VendorWisePaymentStatusReport");
			return map;
			
		}  catch (Exception e) {
			//LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/getAllTypeOfVendorAutoComplete", method = RequestMethod.POST)
	public void getAllTypeOfVendorAutoComplete(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Vendor> addresses = new ArrayList<Vendor>();
			List<Vendor> vendor = vendorService.SearchOnly_All_VendorName(term, userDetails.getCompany_id());
			if (vendor != null && !vendor.isEmpty()) {
				for (Vendor add : vendor) {
					Vendor wadd = new Vendor();
					wadd.setVendorId(add.getVendorId());
					wadd.setVendorName(add.getVendorName());
					wadd.setVendorLocation(add.getVendorLocation());

					addresses.add(wadd);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}

	}
	
	
}

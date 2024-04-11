package org.fleetopgroup.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.InventoryStockTypeConstant;
import org.fleetopgroup.constant.InventoryTransferStatus;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PartInventoryConfigurationConstants;
import org.fleetopgroup.constant.PartLocationsType;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceTypeContant;
import org.fleetopgroup.constant.UserAlertNOtificationsConstant;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.MasterPartsBL;
import org.fleetopgroup.persistence.bl.PartInvoiceBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PartMeasurementUnitBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.dao.UserAlertNotificationsRepository;
import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.InventoryAllDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryLocationDto;
import org.fleetopgroup.persistence.dto.InventoryRequisitionDto;
import org.fleetopgroup.persistence.dto.InventoryRequisitionPartsDto;
import org.fleetopgroup.persistence.dto.InventoryTransferDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.MasterPartsDto;
import org.fleetopgroup.persistence.dto.PartInvoiceDto;
import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryAll;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.InventoryTransfer;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.PartInvoice;
import org.fleetopgroup.persistence.model.PartInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.UserAlertNotifications;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorSequenceCounter;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IDayWiseInventoryStockService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryRequisitionService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPartDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPartMeasurementUnitService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

@Controller
public class InventoryController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired private IInventoryService 					inventoryService;
	@Autowired private IPartDocumentService 					partDocumentService;
	@Autowired private	IMasterPartsService 				masterPartsService;
	@Autowired private	IPartMeasurementUnitService 		partMeasurementUnitService;
	@Autowired private	IPartLocationPermissionService 		partLocationPermissionService;
	@Autowired private	IPartLocationsService 				partLocationsService;
	@Autowired private	IInventoryRequisitionService 		inventoryRequisitionService;
	@Autowired private	IPartInvoiceService					partInvoiceService;
	@Autowired private	IVendorService 						vendorService;
	@Autowired private	IVendorSequenceService 				vendorSequenceService;
	@Autowired private	CompanyConfigurationService			companyConfigurationService;
	@Autowired private	IPartInvoiceSequenceService			partInvoiceSequenceService;
	@Autowired private 	UserAlertNotificationsRepository	userAlertNotificationsRepository;
	@Autowired private	IDayWiseInventoryStockService		dayWiseInventoryStockService;
	@Autowired private	IPendingVendorPaymentService		pendingVendorPaymentService;
	@Autowired private  IBankPaymentService					bankPaymentService; 
	@Autowired private 	ICashPaymentService 				cashPaymentService;	
	
	PartMeasurementUnitBL 				partMeasurementUnitBL 			= new PartMeasurementUnitBL();
	PartLocationsBL 					partLocationsBL		 			= new PartLocationsBL();
	InventoryBL 						inventoryBL			 			= new InventoryBL();
	MasterPartsBL 						masterPartsBL			 		= new MasterPartsBL();
	PartInvoiceBL						PIBL							= new PartInvoiceBL();
	SimpleDateFormat 					sqlDateFormat 					= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 					dateFormatTime 					= new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	DecimalFormat						toFixedTwo						= new DecimalFormat("##.##");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static Long DEFAULT_PURCHAGE_ORDER_VALUE = (long) 0;
	
	
	@RequestMapping(value = "/getLowStockDetails", method = RequestMethod.GET)
	public ModelAndView vendorHome(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, Object> 		model 			= new HashMap<String, Object>();
		CustomUserDetails			userDetails		= null;
		List<PartLocations>			partLocation 	= null;
		try {
			userDetails	= (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			partLocation	= partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id());
			if(partLocation != null) {
				redirectAttributes.addAllAttributes(request.getParameterMap());
				return new ModelAndView("redirect:/getLowStockDetails/"+partLocation.get(0).getPartlocation_id()+"/1.in");
			}else {
				model.put("noLocationPermission", true);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			userDetails		= null;
			partLocation 	= null;
		}
		return new ModelAndView("lowStockInventory", model);
	}

	// Show the the Vehicle Status of
	@RequestMapping(value = "/getLowStockDetails/{locationId}/{pageNumber}", method = RequestMethod.GET)
	public String getLowStockDetails(@PathVariable("locationId") Integer locationId, @PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails			userDetails 	= null;
		List<PartLocations>			partLocation 	= null;		
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			partLocation	= partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id());
			if(partLocation != null) {
				model.addAttribute("PartLocations", partLocationsBL.prepareListofBean(partLocation));

				Page<InventoryLocation> page = inventoryService.getDeployment_Page_LowStockInventoryLocation(pageNumber, userDetails, locationId);
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);

				model.addAttribute("InventoryCount", page.getTotalElements());

				List<InventoryLocationDto> inventoryLocationList 	= inventoryService.GetLowStockInventorLocationDetails(pageNumber, userDetails, locationId);
				model.addAttribute("inventoryLocationList", inventoryLocationList);
				model.addAttribute("activeLocation", locationId);
			}
			
			
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newInventory Page:", e);
			e.printStackTrace();
		} finally {
			userDetails = null;
		}
		return "lowStockInventory";
	}

	
	@RequestMapping(value = "/NewInvoiceList/{pageNumber}", method = RequestMethod.GET)
	public String NewInvoiceList(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails userDetails = null;
		HashMap<String, Object> 	configuration				= null;
		boolean						showSubLocation				= false;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<PartInvoice> page = inventoryService.getDeployment_Page_PartInvoice(pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			showSubLocation = (boolean)configuration.getOrDefault(PartInventoryConfigurationConstants.SHOW_SUB_LOCATION, false);
			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("showSubLocation", showSubLocation);
			
			model.addAttribute("InventoryCount", page.getTotalElements());
			model.addAttribute("configuration", configuration);

			
			List<PartInvoiceDto> pageListInvoice = partInvoiceService.list_Of_All_PartInvoice(pageNumber, userDetails.getCompany_id());
			
			model.addAttribute("InventoryAllInvoice", pageListInvoice);
			
			model.addAttribute("PartLocations", partLocationsBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			model.addAttribute("PartLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
			
			Object[] count = inventoryService.countTotal_Inventory_Qty_Liter(userDetails.getCompany_id());

			if (count[0] != null) {
				model.addAttribute("TOTALLITRECOUNT", round((Double) count[0], 2));
			}
			if (count[1] != null) {
				model.addAttribute("TOTALQUANTITYCOUNT", round((Double) count[1], 2));
			}
			model.addAttribute("PartLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
		} catch (NullPointerException e) {
			LOGGER.error("newInventory Page:", e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newInventory Page:", e);
			e.printStackTrace();
		} finally {
			userDetails = null;
		}
		return "newPartInvoice";
	}

	
	@RequestMapping(value = "/NewInventory/{pageNumber}", method = RequestMethod.GET)
	public String NewInventoryList(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			
			Page<InventoryAll> page = inventoryService.getDeployment_Page_InventoryAll(pageNumber, userDetails.getCompany_id(), configuration);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("InventoryCount", page.getTotalElements());
			
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();

			List<InventoryAllDto> pageList = inventoryService.list_Of_All_Inventory_Quantity(pageNumber, userDetails.getCompany_id(), configuration);
			
			pageList	= inventoryBL.getFinalInventoryDetailDto(pageList, measermentHM);

			model.addAttribute("InventoryAll", pageList);

			model.addAttribute("PartLocations", partLocationsBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			model.addAttribute("CountName", "All");

			Object[] count = inventoryService.countTotal_Inventory_Qty_Liter(userDetails.getCompany_id());

			if (count[0] != null) {
				model.addAttribute("TOTALLITRECOUNT", round((Double) count[0], 2));
			}
			if (count[1] != null) {
				model.addAttribute("TOTALQUANTITYCOUNT", round((Double) count[1], 2));
			}
			model.addAttribute("PartLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
		} catch (NullPointerException e) {
			LOGGER.error(" exception ", e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newInventory Page:", e);
			e.printStackTrace();
		} finally {
			userDetails = null;
		}
		return "newInventory";
	}

	
	
	@GetMapping(value = "/locationInventory/{pageNumber}" )
	public String locationVehicleType(@PathVariable Integer pageNumber, Model model,
			@RequestParam("loc") final Integer locationId, final HttpServletRequest request) {

		CustomUserDetails 			userDetails 							= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			HashMap<String, Object>  partMasterConfi	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			Page<InventoryLocation> page = inventoryService.getDeployment_Page_InventoryLocation(pageNumber, locationId, userDetails.getCompany_id(), configuration);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("showPartLocationOption", partMasterConfi.getOrDefault("showPartLocationOption",false));

			model.addAttribute("InventoryCount", page.getTotalElements());
			
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			
			List<InventoryLocationDto> 	locationList	= inventoryService.list_Of_Location_Inventory_Quantity(pageNumber, locationId, userDetails.getCompany_id(), configuration);
			locationList	=	inventoryBL.getFinalInventoryLocationDto(locationList, measermentHM);
			
			model.addAttribute("PartLocations", partLocationsBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			model.addAttribute("InventoryLocation", locationList);
			model.addAttribute("location", partLocationsService.getPartLocations((int) locationId).getPartlocation_name());
			Object[] count = inventoryService.countTotal_Inventory_Qty_AND_Location_Qty(locationId, userDetails.getCompany_id());

			if (count[0] != null) {
				model.addAttribute("TOTALLITRECOUNT", round((Double) count[0], 2));
			}
			if (count[1] != null) {
				model.addAttribute("TOTALQUANTITYCOUNT", round((Double) count[1], 2));
			}
			model.addAttribute("PartLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
			model.addAttribute("locationId", locationId);

		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newInventory Page:", e);
			e.printStackTrace();
		}
		return "newLocationInventory";
	}

	@RequestMapping(value = "/addInventory", method = RequestMethod.GET)
	public ModelAndView addVehicleType(@ModelAttribute("command") Inventory InventoryDto, final HttpServletRequest request) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		CustomUserDetails 			userDetails 				= null;
		HashMap<String, Object> 	configuration				= null;
		boolean						showSubLocation				= false;
		String						mainLocationIds				= "";
		
		try {
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			model.put("PartMeasurementUnit",partMeasurementUnitBL.prepareListofBean(partMeasurementUnitService.listPartMeasurementUnit()));
			model.put("PartLocationPermission", partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
		//	model.put("subLocation", partLocationsBL.prepareListofPartLocation(partLocationsService.getPartLocationListByType(PartLocationsType.PART_LOCATION_TYPE_SUB_LOCATION)));
			
			if(configuration != null) {
				showSubLocation = (boolean)configuration.getOrDefault(PartInventoryConfigurationConstants.SHOW_SUB_LOCATION, false);
				mainLocationIds 	= configuration.getOrDefault(PartInventoryConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
				
				if(showSubLocation) {
					model.put("showSubLocation",showSubLocation);
					model.put("mainLocationIds",mainLocationIds);
				}
				
				model.put("NoOfPartsAllowedToAdd", configuration.getOrDefault(PartInventoryConfigurationConstants.NO_OF_PARTS_ALLOWED_TO_ADD, 10));
				model.put("addMorePartsAtBottom", (boolean)configuration.getOrDefault(PartInventoryConfigurationConstants.ADD_MORE_PARTS_AT_BOTTOM, false));
				model.put("configuration", configuration);
			}
			model.put("accessToken", Utility.getUniqueToken(request));

		} catch (Exception e) {
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("addInventory", model);
	}
	
	

	/* save the vehicle Status */
	@RequestMapping(value = "/saveInventory", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView saveInventory(@ModelAttribute("command") InventoryDto Inventory,
			@RequestParam("partid_many") final String[] partid, 
			@RequestParam("make_many") final String[] make,
			@RequestParam("finalDiscountTaxTypId") final String[] discTaxTypeId,
			@RequestParam("quantity_many") final String[] quantity,
			@RequestParam("unitprice_many") final String[] unitprice,
			@RequestParam("discount_many") final String[] discount, 
			@RequestParam("tax_many") final String[] tax,
			@RequestParam("input-file-preview") MultipartFile file,
			@ModelAttribute BankPaymentDto bankPaymentDto,
			final HttpServletRequest request) {
		
		
			Map<String, Object> 				model 			= new HashMap<>();
			PartInvoice 						partInvoice 	= null;
			CustomUserDetails 					userDetails		= null;
			PartInvoiceSequenceCounter 			seqCounter 		= null;
			HashMap<String, Object> 			configuration	= null;
		try {
			
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			//userDetails.get
			// code to save part invoice details in PartInvoice table
			seqCounter 			= partInvoiceSequenceService.findNextInventoryNumber(userDetails.getCompany_id(), SequenceTypeContant.SEQUENCE_TYPE_PART_INVENTORY);
			
			if (seqCounter == null) {
				model.put("sequenceNotFound", true);
				return new ModelAndView("redirect:/NewInventory/1.in", model);
			}
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			partInvoice			= new PartInvoice();
			
			if (seqCounter.getNextVal() != 0) {
				partInvoice.setPartInvoiceNumber(seqCounter.getNextVal());
			}
			
			if (Inventory.getInvoice_number() != null) {
				partInvoice.setInvoiceNumber(Inventory.getInvoice_number());
			} else {
				partInvoice.setInvoiceNumber("Excel Entry");
			}
			if (Inventory.getInvoice_date() != null) {
				java.util.Date date = sqlDateFormat.parse(Inventory.getInvoice_date());
				partInvoice.setInvoiceDate(DateTimeUtility.geTimeStampFromDate(date));
			}
			partInvoice.setInvoiceAmount(Inventory.getInvoice_amount());
			partInvoice.setBalanceAmount(Double.parseDouble(Inventory.getInvoice_amount()));
			partInvoice.setPaymentTypeId(Inventory.getPAYMENT_TYPE_ID());
			partInvoice.setPAYMENT_NUMBER(Inventory.getPAYMENT_NUMBER());
			partInvoice.setLabourCharge(Inventory.getLabourCharge());
			
			if(partInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				partInvoice.setVendorPaymentStatus(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
			}else {
				partInvoice.setVendorPaymentStatus(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID);
			}
			if (Inventory.getDescription() != null) {
				partInvoice.setDescription(Inventory.getDescription());
			} else {
				partInvoice.setDescription("Excel Entry");
			}
			partInvoice.setCreatedById(userDetails.getId());
			partInvoice.setLastModifiedById(userDetails.getId());
			partInvoice.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			partInvoice.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
			partInvoice.setCompanyId(userDetails.getCompany_id());
			partInvoice.setTallyCompanyId(Inventory.getTallyCompanyId());
			
			
			if (Inventory.getVendorIdStr() != null
					&& FuelDto.getParseVendorID_STRING_TO_ID(Inventory.getVendorIdStr()) != 0) {
				partInvoice.setVendorId(FuelDto.getParseVendorID_STRING_TO_ID(Inventory.getVendorIdStr()));
				Inventory.setVendor_id(FuelDto.getParseVendorID_STRING_TO_ID(Inventory.getVendorIdStr()));
			} else {
					Vendor vendor = vendorService.getVendorIdFromNew(Inventory.getVendorIdStr(), userDetails.getCompany_id(), "PART-VENDOR", "Part Inventory");
				partInvoice.setVendorId(vendor.getVendorId());
				Inventory.setVendor_id(vendor.getVendorId());
			}
			
			partInvoice.setWareHouseLocation(Inventory.getLocationId());
			
			Double Qty = 0.0;
			for (int i = 0; i < partid.length; i++) {
				Qty += Double.parseDouble(quantity[i]);
			}
			partInvoice.setQuantity(Qty);
			
			final Long invoiceId = partInvoice.getPartInvoiceId();
			partInvoice.setPartInvoiceId(invoiceId);
			
			if (!file.isEmpty()) {
				partInvoice.setPart_document(true);
			} else {
				partInvoice.setPart_document(false);
			}
			
			partInvoice.setSubLocationId(Inventory.getSubLocationId());

			partInvoiceService.saveInvoice(partInvoice);
			
			if(partInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && Double.parseDouble(partInvoice.getInvoiceAmount()) > 0) {
				PendingVendorPayment	payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForPI(partInvoice);
				pendingVendorPaymentService.savePendingVendorPayment(payment);
			}
			
			if (bankPaymentDto.isAllowBankPaymentDetails()) {
				if(PaymentTypeConstant.getPaymentTypeIdList().contains(partInvoice.getPaymentTypeId()) || partInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
					ValueObject bankPaymentValueObject=new ValueObject();
					bankPaymentValueObject.put("currentPaymentTypeId",partInvoice.getPaymentTypeId());
					bankPaymentValueObject.put("userId", userDetails.getId());
					bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
					bankPaymentValueObject.put("moduleId",partInvoice.getPartInvoiceId());
					bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.PART_INVENTORY);
					bankPaymentValueObject.put("moduleNo", partInvoice.getPartInvoiceNumber());
					bankPaymentValueObject.put("amount",Double.parseDouble(partInvoice.getInvoiceAmount()));
					bankPaymentValueObject.put("remark", "Part Inventory Created PI-"+partInvoice.getPartInvoiceNumber());
					//bankPaymentValueObject.put("paidDate", partInvoice.getInvoiceDate());
					if(partInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
						cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
					else
						bankPaymentService.addBankPaymentDetailsAndSendToIv(bankPaymentDto,bankPaymentValueObject);

				}
			}
			
			if (!file.isEmpty()) {
				org.fleetopgroup.persistence.document.PartDocument partDoc = new org.fleetopgroup.persistence.document.PartDocument();

				partDoc.setPart_id(partInvoice.getPartInvoiceId());
				try {

					byte[] bytes = file.getBytes();
					partDoc.setPart_filename(file.getOriginalFilename());
					partDoc.setPart_content(bytes);
					partDoc.setPart_contentType(file.getContentType());
					partDoc.setMarkForDelete(false);
					partDoc.setCreatedById(userDetails.getId());
					partDoc.setLastModifiedById(userDetails.getId());

					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					partDoc.setCreated(toDate);
					partDoc.setLastupdated(toDate);
					partDoc.setCompanyId(userDetails.getCompany_id());
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Note: Save FuelDocument Details
				partDocumentService.add_Part_To_PartDocument(partDoc);

				// Note: FuelDocument to Save id to Fuel save
				inventoryService.Update_PartDocument_ID_to_Part(partDoc.get_id(), true, partInvoice.getPartInvoiceId(),
						userDetails.getCompany_id());
			
			}
			
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			MasterParts	masterParts	= null;
			PartMeasurementUnit	measurementUnit	= null;
			for (int i = 0; i < partid.length; i++) {
				try {
					Long MasterPart_ID = Long.parseLong(partid[i]);
					masterParts		= masterPartsService.getMasterParts(MasterPart_ID);
					
					measurementUnit	= measermentHM.get(Integer.parseInt(masterParts.getUnitTypeId()+""));
					
					System.err.println("measurementUnit : "+measurementUnit);
					
					Double originalQuantity		= Double.parseDouble(quantity[i]);
					Double originalUnitPrice	= Double.parseDouble(unitprice[i]);
					
					if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
						quantity[i] = (Double.parseDouble(quantity[i]) * measurementUnit.getConversionRate())+"";
						unitprice[i] = (Double.parseDouble(unitprice[i]) / measurementUnit.getConversionRate())+"";
					}
					
					InventoryAll Inventoryall = inventoryBL.prepareInventoryAll(MasterPart_ID);
					InventoryLocation InventoryLocation = inventoryBL.prepareInventoryLocation(Inventory, MasterPart_ID);
					InventoryLocation.setCompanyId(Inventoryall.getCompanyId());
					Inventory Part = prepareModel(Inventory, MasterPart_ID);
					Part.setPartInvoiceId(partInvoice.getPartInvoiceId());
					Part.setCompanyId(Inventoryall.getCompanyId());
					Part.setMainQuantity(originalQuantity);
					Part.setMainUnitprice(originalUnitPrice);

					// save part Manufacturers Service in Master part
					try {
						// first validate part_id is there are not in Inventory
						// All Table
						List<InventoryAll> validate = inventoryService.Validate_InventoryAll(MasterPart_ID,	Inventoryall.getCompanyId());
						if (validate != null && !validate.isEmpty()) {

							Long Inventory_all_id = (long) 0;
							Long Inventory_Location_id = (long) 0;

							for (InventoryAll updateInventory : validate) {
								// get part and AllQuantity in all_Ready in db
								// Add New Quantity
								Inventory_all_id = updateInventory.getInventory_all_id();

								break;
							}

							// first validate part_Location is there are not in
							// Inventory All Table
							List<InventoryLocation> validate_Location = inventoryService.Validate_Inventory_Location(MasterPart_ID, Inventory.getLocationId());
							if (validate_Location != null && !validate_Location.isEmpty()) {

								for (InventoryLocation validateLocation : validate_Location) {
									// get part and Location Quantity in
									// all_Ready in db Add New Quantity

									Inventory_Location_id = validateLocation.getInventory_location_id();
									break;
								}

							} else {

								// get part and Location Quantity Search that
								// location part not in db Create new
								InventoryLocation.setLocation_quantity(Double.parseDouble(quantity[i]));

								InventoryAll nEWInventoryall = new InventoryAll();
								nEWInventoryall.setInventory_all_id(Inventory_all_id);

								InventoryAll NewAll = new InventoryAll();
								NewAll.setInventory_all_id(Inventory_all_id);
								InventoryLocation.setInventoryall(NewAll);

								inventoryService.add_InventoryLocation(InventoryLocation);

								Inventory_Location_id = InventoryLocation.getInventory_location_id();

							}
							
							// Quantity all ready save to update in all ,
							// Location Quantity details
							
							System.err.println("unitprice[i] : "+unitprice[i]);
							System.err.println("discount[i] : "+discount[i]);
							System.err.println("tax[i] : "+tax[i]);
							
							Part.setHistory_quantity(Double.parseDouble(quantity[i]));
							Part.setQuantity(Double.parseDouble(quantity[i]));
							Part.setUnitprice(Double.parseDouble(unitprice[i]));
							Part.setDiscount(Double.parseDouble(discount[i]));
							Part.setTax(Double.parseDouble(tax[i]));
							Part.setDiscountTaxTypeId(Short.parseShort(discTaxTypeId[i]));

							Double Quantity = Double.parseDouble(quantity[i]);
							Double eaccost = Double.parseDouble(unitprice[i]);
							Double dis_Ca = Double.parseDouble(discount[i]);
							Double tax_Ca = Double.parseDouble(tax[i]);

							Double discountCost = 0.0;
							Double TotalCost = 0.0;
							
							if(Part.getDiscountTaxTypeId() == 1) {
								discountCost 	= (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
								TotalCost 		= round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
							} else {
								discountCost 	= ((Quantity * eaccost) - dis_Ca);
								TotalCost 		= round((discountCost + tax_Ca), 2);
							}
							
							Part.setTotal(TotalCost);

							if (make[i] != null) {

								Part.setMake(make[i]);
							}
							Part.setInventory_all_id(Inventory_all_id);
							Part.setInventory_location_id(Inventory_Location_id);
							Part.setSerialNoAddedForParts(0);
							
					
							
							inventoryService.save_Inventory(Part);

							// Insert Inventory QUANTITY Details to Update
							// LOCATION AND ALL LOCATION QUANTITY DETAILS

							inventoryService.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(MasterPart_ID, Inventory.getLocationId());

							inventoryService.update_InventoryAll_PARTID_PARTNAME_GENRAL(MasterPart_ID);

						} else {
							// get part and AllQuantity save
							Inventoryall.setAll_quantity(Double.parseDouble(quantity[i]));
							inventoryService.add_InventoryAll(Inventoryall);

							// get part and Location Quantity save
							InventoryLocation.setLocation_quantity(Double.parseDouble(quantity[i]));
							InventoryLocation.setInventoryall(Inventoryall);
							inventoryService.add_InventoryLocation(InventoryLocation);

							Part.setHistory_quantity(Double.parseDouble(quantity[i]));
							Part.setQuantity(Double.parseDouble(quantity[i]));
							Part.setUnitprice(Double.parseDouble(unitprice[i]));

							Part.setDiscount(Double.parseDouble(discount[i]));
							Part.setTax(Double.parseDouble(tax[i]));
							Part.setDiscountTaxTypeId(Short.parseShort(discTaxTypeId[i]));

							Double Quantity = Double.parseDouble(quantity[i]);
							Double eaccost = Double.parseDouble(unitprice[i]);
							Double dis_Ca = Double.parseDouble(discount[i]);
							Double tax_Ca = Double.parseDouble(tax[i]);

							Double discountCost = 0.0;
							Double TotalCost = 0.0;

							if(Part.getDiscountTaxTypeId() == 1) {
								discountCost 	= (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
								TotalCost 		= round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
							} else {
								discountCost 	= ((Quantity * eaccost) - dis_Ca);
								TotalCost 		= round((discountCost + tax_Ca), 2);
							}
							
							Part.setTotal(TotalCost);

							if (make[i] != null) {

								Part.setMake(make[i]);
							}
							Part.setInventory_all_id(Inventoryall.getInventory_all_id());
							Part.setInventory_location_id(InventoryLocation.getInventory_location_id());
							Part.setSerialNoAddedForParts(0);
							
							
							inventoryService.save_Inventory(Part);
						}
						model.put("saveInventory", true);

					} catch (Exception e) {
						LOGGER.error("Error"+ e);
						e.printStackTrace();
						return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
					}
					
					if((boolean) configuration.get("showRefreshmentOption")) {
						if(masterParts != null && masterParts.isRefreshment()) {
							ValueObject	valueObject	= new ValueObject();
							if(Part.getInvoice_date() != null) {
								valueObject.put("transactionDate", Part.getInvoice_date());
							}else {
								valueObject.put("transactionDate", DateTimeUtility.getCurrentDate());
							}
							
							valueObject.put("partId", Part.getPartid());
							valueObject.put("locationId", Part.getLocationId());
							valueObject.put("quantity", Part.getQuantity());
							valueObject.put("companyId", userDetails.getCompany_id());
							valueObject.put("addedQuantity", Part.getQuantity());
							valueObject.put("usedQuantity", 0.0);
							valueObject.put("numberType", "I-"+ Part.getInventory_Number()+" Part Add");
							valueObject.put("transactionId", Part.getInventory_id());
							valueObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
							valueObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
							dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject);	
						}
					}

				} catch (Exception e) {
					LOGGER.error("Error"+ e);
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("Error"+ e);
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
		}
		return new ModelAndView("redirect:/NewInventory/1.in", model);
	}

	@RequestMapping(value = "/editInventory", method = RequestMethod.GET)
	public ModelAndView editInventory(@ModelAttribute("command") Inventory InventoryDto,
			final HttpServletRequest request) {
		Map<String, Object>			 	model 						= new HashMap<String, Object>();
		CustomUserDetails 				userDetails 				= null; 
		HashMap<String, Object> 		configuration				= null;
		boolean							showSubLocation				= false;
		String							mainLocationIds				= "";
		PartInvoice        partInvoice    = null;
		String				vendorPaymentStatus = "";
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			InventoryDto FROM_EditInventory = inventoryService.getInventory(InventoryDto.getInventory_id());
			showSubLocation = (boolean)configuration.getOrDefault(PartInventoryConfigurationConstants.SHOW_SUB_LOCATION, false);
			mainLocationIds = configuration.getOrDefault(PartInventoryConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
			partInvoice	= partInvoiceService.getPartInvoiceDetails(FROM_EditInventory.getPartInvoiceId(), userDetails.getCompany_id());

			if(partInvoice != null) {
				if(partInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && partInvoice.getVendorPaymentStatus() != FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID){
					if(partInvoice.getVendorPaymentStatus() == FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID || partInvoice.getVendorPaymentStatus() == FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID)
					{
						vendorPaymentStatus = FuelVendorPaymentMode.getPaymentMode(partInvoice.getVendorPaymentStatus())+" PAID";
					}else {
						vendorPaymentStatus = FuelVendorPaymentMode.getPaymentMode(partInvoice.getVendorPaymentStatus());
						
					}
					model.put("vendorApproval", true);
					model.put("vendorPaymentStatus",vendorPaymentStatus);
					return new ModelAndView( "redirect:/showInventory.in?inventory_all_id=" + FROM_EditInventory.getInventory_all_id() + "", model);
				}
			}
			List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));
			model.put("showSubLocation", showSubLocation);
			model.put("mainLocationIds", mainLocationIds);
			model.put("subLocation", partLocationsBL.prepareListofPartLocation(partLocationsService.getPartLocationListByType(PartLocationsType.PART_LOCATION_TYPE_SUB_LOCATION)));
			// check user branch location and workOrder location if same
			if (inventoryBL.isFilled_GET_Permission(FROM_EditInventory.getLocationId(), PartLocPermission)) {
				Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
				FROM_EditInventory	= inventoryBL.getFinalInventoryDto(FROM_EditInventory, measermentHM);
				
				model.put("Inventory", FROM_EditInventory);

				model.put("PartLocationPermission", PartLocPermission);

			} else {
				model.put("NoAuthen", true);
				return new ModelAndView(
						"redirect:/showInventory.in?inventory_all_id=" + FROM_EditInventory.getInventory_all_id() + "",
						model);
			}
		} catch (Exception e) {
			LOGGER.error("Inventory Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("editInventory", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/updateInventory", method = RequestMethod.POST)
	public ModelAndView updateInventory(@ModelAttribute("command") InventoryDto Inventory,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			InventoryDto getOLd_Inventory = inventoryService.getInventory(Inventory.getInventory_id());

			if (getOLd_Inventory != null) {

				List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

				// check user branch location and workOrder location if same
				if (inventoryBL.isFilled_GET_Permission(getOLd_Inventory.getLocationId(), PartLocPermission)) {
					HashMap<String, Object>  configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
					InventoryAll Inventoryall = inventoryBL.prepareInventoryAll(Inventory.getPartid());
					InventoryLocation InventoryLocation = inventoryBL.prepareInventoryLocation(Inventory,
							Inventory.getPartid());
					InventoryLocation.setCompanyId(Inventoryall.getCompanyId());
					Inventory Part = prepareUpdateInventoryModel(Inventory, Inventory.getPartid());

					// save part Manufacturers Service in Master part
					try {
						// first validate part_id is there are not
						List<InventoryAll> validate = inventoryService.Validate_InventoryAll(Inventory.getPartid(),	Inventoryall.getCompanyId());
						if (validate != null && !validate.isEmpty()) {

							Long Inventory_all_id = (long) 0;
							@SuppressWarnings("unused")
							Long Inventory_Location_id = (long) 0;

							for (InventoryAll updateInventory : validate) {
								// get part and AllQuantity in all_Ready in db

								Inventory_all_id = updateInventory.getInventory_all_id();

								break;
							}
							List<InventoryLocation> validate_Location = inventoryService.Validate_Inventory_Location(Inventory.getPartid(), Inventory.getLocationId());
							if (validate_Location != null && !validate_Location.isEmpty()) {
								for (InventoryLocation validateLocation : validate_Location) {
									// get part and Location Quantity in
									// all_Ready in db Add New Quantity
									Inventory_Location_id = validateLocation.getInventory_location_id();
									break;
								}
							} else {

								// get part and Location Quantity Search that
								// location part not in db Create new
								InventoryLocation.setLocation_quantity(Inventory.getQuantity());
								InventoryAll NewAll = new InventoryAll();
								NewAll.setInventory_all_id(Inventory_all_id);
								InventoryLocation.setInventoryall(NewAll);
								inventoryService.add_InventoryLocation(InventoryLocation);

								Inventory_Location_id = InventoryLocation.getInventory_location_id();

							}

							// Quantity all ready save to update in all ,
							// Location Quantity details

							// update Inventory location All Details and
							// Quantity
							Part.setInventory_all_id(getOLd_Inventory.getInventory_all_id());
							Part.setInventory_location_id(getOLd_Inventory.getInventory_location_id());
							inventoryService.updateInventory(Part);

							// Insert Inventory QUANTITY Details to Update
							// LOCATION AND ALL LOCATION QUANTITY DETAILS

							inventoryService.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(Inventory.getPartid(), Inventory.getLocationId());

							inventoryService.update_InventoryAll_PARTID_PARTNAME_GENRAL(Inventory.getPartid());
							
							
							PartInvoice getPartInvoiceDetails	= partInvoiceService.getPartInvoiceDetails(Inventory.getPartInvoiceId(), userDetails.getCompany_id());
							
							PartInvoice PartInvoice 			= PIBL.prepareDirectUpdatePartInvoiceFromPart(Inventory);
							
							double quantity = getPartInvoiceDetails.getQuantity()-getOLd_Inventory.getQuantity();
							double amount	= (Double.parseDouble(getPartInvoiceDetails.getInvoiceAmount()) - getOLd_Inventory.getTotal());
							
							PartInvoice.setQuantity(Inventory.getQuantity()+quantity);
							PartInvoice.setInvoiceAmount(((Double.parseDouble(Inventory.getInvoice_amount()))+amount)+"");
							PartInvoice.setPaymentTypeId(getPartInvoiceDetails.getPaymentTypeId());
							PartInvoice.setAnyPartNumberAsign(getPartInvoiceDetails.isAnyPartNumberAsign());
							PartInvoice.setLabourCharge(getPartInvoiceDetails.getLabourCharge());
							inventoryService.update_Part_Inventory_Invoice(PartInvoice);

						} else {

							// get part and AllQuantity save
							Inventoryall.setAll_quantity(Inventory.getQuantity());
							inventoryService.add_InventoryAll(Inventoryall);

							// get part and Location Quantity save
							InventoryLocation.setLocation_quantity(Inventory.getQuantity());
							InventoryLocation.setInventoryall(Inventoryall);
							inventoryService.add_InventoryLocation(InventoryLocation);

							inventoryService.add_Inventory(Part);
						}
						model.put("updateInventory", true);

					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
					}
					
					if((boolean) configuration.get("showRefreshmentOption")) {
						MasterParts	masterParts	=	masterPartsService.getMasterParts(getOLd_Inventory.getPartid());
						if(masterParts != null && masterParts.isRefreshment()) {
							
							if((Part.getQuantity() - getOLd_Inventory.getQuantity()) != 0 || !(Part.getInvoice_date().equals(getOLd_Inventory.getInvoiceDate()))) {
								
								ValueObject	valueObject	= new ValueObject();
								if(getOLd_Inventory.getInvoiceDate() != null) {
									valueObject.put("transactionDate", Part.getInvoice_date());
								}else {
									valueObject.put("transactionDate", DateTimeUtility.getCurrentDate());
								}
								
								valueObject.put("partId", getOLd_Inventory.getPartid());
								valueObject.put("locationId",  getOLd_Inventory.getLocationId());
								valueObject.put("companyId", userDetails.getCompany_id());
								valueObject.put("addedQuantity", Part.getQuantity());
								valueObject.put("usedQuantity", 0.0);
								valueObject.put("numberType", "I-"+ getOLd_Inventory.getInventory_Number()+" Part Edit");
								valueObject.put("transactionId", getOLd_Inventory.getInventory_id());
								valueObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
								valueObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
								if(Part.getInvoice_date().equals(getOLd_Inventory.getInvoiceDate())) {
									valueObject.put("quantity", - (getOLd_Inventory.getQuantity() - Part.getQuantity()));
									valueObject.put("isDateChanged", false);
								}else {
									valueObject.put("quantity", Part.getQuantity());
									valueObject.put("isDateChanged", true);
									
								}
								valueObject.put("previousQuantity", getOLd_Inventory.getQuantity());
								valueObject.put("previousDate", getOLd_Inventory.getInvoiceDate());
								
								dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject);	
							}
							
						}
					}
					
					

				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/showInventory.in?inventory_all_id="
							+ getOLd_Inventory.getInventory_all_id() + "", model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
		}
		return new ModelAndView("redirect:/NewInventory/1.in", model);
	}
	
	
	@RequestMapping(value = "/showInvoice", method = RequestMethod.GET)
	public ModelAndView showInvoice(@RequestParam("Id") final Long PartInvoice_id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails		= null;
		boolean 					showSubLocation = false;
		try {
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			model.put("PartInvoice",partInvoiceService.details_of_PartInvoice(PartInvoice_id, userDetails.getCompany_id()));
			showSubLocation 	= (boolean)configuration.getOrDefault(PartInventoryConfigurationConstants.SHOW_SUB_LOCATION, false);
			List<InventoryDto> inventoryDto = partInvoiceService.details_of_Inventory(PartInvoice_id, userDetails.getCompany_id());
			
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			
			inventoryDto	= inventoryBL.getFinalInventoryDto(inventoryDto, measermentHM);
			
			model.put("configuration",configuration);
			model.put("PartAmount",inventoryDto);
			model.put("showSubLocation",showSubLocation);
			
			Double total = 0.0;
			if(inventoryDto != null) {
			for (InventoryDto inv : inventoryDto ) {
				
				total += inv.getTotal();
			}
			}
			model.put("TotalPartCost",toFixedTwo.format(total));
			
		} catch (Exception e) {
			LOGGER.error("Add Part Inventory Page"+ e);
		}finally {
			userDetails	= null;
		}
		return new ModelAndView("showPartInvoice", model);
	}


	@RequestMapping(value = "/showInventory", method = RequestMethod.GET)
	public ModelAndView showInventory(@ModelAttribute("command") InventoryAll InventoryAll,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null; 
		HashMap<String, Object>  configuration =  null;
		boolean showSubLocation = false;
		InventoryAllDto inventoryall = null;
		try { 
			// get the InventoryAll id to value	
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			showSubLocation 	= (boolean)configuration.getOrDefault(PartInventoryConfigurationConstants.SHOW_SUB_LOCATION, false);
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			
			inventoryall = inventoryService.getInventoryAll(InventoryAll.getInventory_all_id(),	userDetails.getCompany_id());
		
		
			
			model.put("showSubLocation", showSubLocation);
			
			if (inventoryall != null) {
				List<InventoryLocationDto> show_Location = null;
				ValueObject	valueObject	=  null;
				
				if((boolean) configuration.get("groupInventoryOnPartName")) {
					List<InventoryAllDto>	tempList	=	inventoryService.getInventoryAllGPONName(inventoryall.getPartname(), userDetails.getCompany_id());
					valueObject	= inventoryBL.getInventoryAllDtoForUI(inventoryall, tempList);
					inventoryall	= (InventoryAllDto) valueObject.get("inventoryAllDto");
				}
				
				inventoryall	= inventoryBL.getFinalInventoryDetailDto(inventoryall, measermentHM);
				
				model.put("InventoryAll", inventoryall);
				
				if(!(boolean) configuration.get("groupInventoryOnPartName")) {
					show_Location = inventoryService.Get_InventoryAll_id_To_Inventory_Location(InventoryAll.getInventory_all_id(), userDetails.getCompany_id());
				}else {
					show_Location = inventoryService.Get_InventoryAll_id_To_Inventory_LocationOnName(valueObject.getString("inventoryAllIds"), 
														userDetails.getCompany_id());
					
					show_Location	= inventoryBL.getLocationInventoryForUI(show_Location);
				}
				
				
				if(show_Location != null && !show_Location.isEmpty()) {
					show_Location	= inventoryBL.getFinalInventoryLocationDto(show_Location, measermentHM);
				}
				
				List<InventoryDto> show_Inventory = null;
				
				model.put("InventoryLocation", show_Location);
				if(!(boolean) configuration.get("groupInventoryOnPartName")) {
						show_Inventory = inventoryService.Show_AvailableQYT_InventoryAll_id_To_Inventory(InventoryAll.getInventory_all_id(), userDetails.getCompany_id());
				}else {
						show_Inventory = inventoryService.Show_AvailableQYT_InventoryAll_id_To_InventoryOnName(valueObject.getString("inventoryAllIds"), userDetails.getCompany_id());
				}
				
				
				if(show_Inventory != null && !show_Inventory.isEmpty()) {
					show_Inventory	= inventoryBL.getFinalInventoryDto(show_Inventory, measermentHM);
				}
				
				model.put("Inventory", show_Inventory);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		} finally {
			userDetails = null;
		}
		return new ModelAndView("showInventory", model);
	}

	@RequestMapping(value = "/showLocationInventory", method = RequestMethod.GET)
	public ModelAndView showLocation(@ModelAttribute("command") InventoryLocation InventoryAll,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		HashMap<String, Object>  configuration =  null;
		boolean showSubLocation = false;
		
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			showSubLocation 	= (boolean)configuration.getOrDefault(PartInventoryConfigurationConstants.SHOW_SUB_LOCATION, false);
			model.put("showSubLocation", showSubLocation);
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			InventoryLocationDto InventoryLocation = inventoryService.getInventoryLocationDtoByLocation(InventoryAll.getInventory_location_id(), userDetails.getCompany_id());
			// get the InventoryAll id to value
			
			
			InventoryAllDto inventoryall = inventoryService.getInventoryAll(InventoryLocation.getInventory_all_id(), userDetails.getCompany_id());
			

			
			model.put("showSubLocation", showSubLocation);
			
			if (inventoryall != null) {
				List<InventoryLocationDto> show_Location = null;
				ValueObject	valueObject	=  null;
				
				if((boolean) configuration.get("groupInventoryOnPartName")) {
					List<InventoryAllDto>	tempList	=	inventoryService.getInventoryAllGPONName(inventoryall.getPartname(), userDetails.getCompany_id());
					valueObject	= inventoryBL.getInventoryAllDtoForUI(inventoryall, tempList);
					inventoryall	= (InventoryAllDto) valueObject.get("inventoryAllDto");
				}
				
				inventoryall	= inventoryBL.getFinalInventoryDetailDto(inventoryall, measermentHM);
				
				model.put("InventoryAll", inventoryall);
				
				if(!(boolean) configuration.get("groupInventoryOnPartName")) {
					show_Location = inventoryService.Get_InventoryAll_id_To_Inventory_Location(InventoryLocation.getInventory_all_id(), userDetails.getCompany_id());
				}else {
					show_Location = inventoryService.Get_InventoryAll_id_To_Inventory_LocationOnName(valueObject.getString("inventoryAllIds"), 
														userDetails.getCompany_id());
					
					show_Location	= inventoryBL.getLocationInventoryForUI(show_Location);
				}
				
				
				if(show_Location != null && !show_Location.isEmpty()) {
					show_Location	= inventoryBL.getFinalInventoryLocationDto(show_Location, measermentHM);
				}
				
				List<InventoryDto> show_Inventory = null;
				
				model.put("InventoryLocation", show_Location);
				if(!(boolean) configuration.get("groupInventoryOnPartName")) {
						show_Inventory = inventoryService.Show_AvailableQYT_InventoryAll_id_To_Inventory(InventoryLocation.getInventory_all_id(), userDetails.getCompany_id());
				}else {
						show_Inventory = inventoryService.Show_AvailableQYT_InventoryAll_id_To_InventoryOnName(valueObject.getString("inventoryAllIds"), userDetails.getCompany_id());
				}
				
				if(show_Inventory != null && !show_Inventory.isEmpty()) {
					show_Inventory	= inventoryBL.getFinalInventoryDto(show_Inventory, measermentHM);
				}
				
				model.put("Inventory", show_Inventory);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		} finally {
			userDetails = null;
		}
		return new ModelAndView("showInventory", model);
	}

	@RequestMapping(value = "/showDetailsInventory", method = RequestMethod.GET)
	public ModelAndView showDetailsInventory(@ModelAttribute("command") Inventory Inventory,
			final HttpServletRequest request) {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails 			userDetails 		= null;
		InventoryDto 				inventory			= null;
		try {
			 userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// get the InventoryAll id to value
			 inventory		= inventoryService.getInventory(Inventory.getInventory_id());
			 if(inventory != null) {
				 model.put("Inventory", inventory);
				 model.put("PartLocationRow", masterPartsService.getInventoryMasterPartsLocation(inventory.getPartid(), inventory.getLocationId(), userDetails.getCompany_id()));
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("showDetailsInventory", model);
	}

	@RequestMapping(value = "/deleteInventory", method = RequestMethod.GET)
	public ModelAndView deleteInventory(@ModelAttribute("command") Inventory InventoryDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		long inventory_all_id = 0;
		String				vendorPaymentStatus = "";
		try {
			InventoryDto getOLd_Inventory = inventoryBL.prepareInventoryDto(inventoryService.getInventory(InventoryDto.getInventory_id()));

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));
			
			

			if (getOLd_Inventory != null) {
				
				PartInvoice partInvoice	= partInvoiceService.getPartInvoiceDetails(getOLd_Inventory.getPartInvoiceId(), userDetails.getCompany_id());

				if(partInvoice != null) {
					if(partInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && partInvoice.getVendorPaymentStatus() != FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID){
						if(partInvoice.getVendorPaymentStatus() == FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID || partInvoice.getVendorPaymentStatus() == FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID)
						{
							vendorPaymentStatus = FuelVendorPaymentMode.getPaymentMode(partInvoice.getVendorPaymentStatus())+" PAID";
						}else {
							vendorPaymentStatus = FuelVendorPaymentMode.getPaymentMode(partInvoice.getVendorPaymentStatus());
							
						}
						model.put("vendorApproval", true);
						model.put("vendorPaymentStatus",vendorPaymentStatus);
						return new ModelAndView( "redirect:/showInventory.in?inventory_all_id=" + getOLd_Inventory.getInventory_all_id() + "", model);
					}
				}

				// check user branch location and workOrder location if same
				if (inventoryBL.isFilled_GET_Permission(getOLd_Inventory.getLocationId(), PartLocPermission)) {

					HashMap<String, Object>  configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
						
					inventory_all_id = getOLd_Inventory.getInventory_all_id();

					// save part Manufacturers Service in Master part
					try {
						// first validate part_id is there are not

						inventoryService.deleteInventory(InventoryDto.getInventory_id(), userDetails.getCompany_id());

						// Insert Inventory QUANTITY Details to Update
						// LOCATION AND ALL LOCATION QUANTITY DETAILS

						inventoryService.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(getOLd_Inventory.getPartid(), getOLd_Inventory.getLocationId());

						inventoryService.update_InventoryAll_PARTID_PARTNAME_GENRAL(getOLd_Inventory.getPartid());
						
						double quantity = partInvoice.getQuantity()-getOLd_Inventory.getQuantity();
						double amount	= (Double.parseDouble(partInvoice.getInvoiceAmount()) - getOLd_Inventory.getTotal());
						
						partInvoice.setQuantity(quantity);
						partInvoice.setInvoiceAmount(""+amount);
						inventoryService.update_Part_Inventory_Invoice(partInvoice);
						model.put("deleteInventory", true);

					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
					}
					
					if((boolean) configuration.get("showRefreshmentOption")) {
						MasterParts	masterParts	=	masterPartsService.getMasterParts(getOLd_Inventory.getPartid());
						if(masterParts != null && masterParts.isRefreshment()) {
							ValueObject	valueObject	= new ValueObject();
							if(getOLd_Inventory.getInvoiceDate() != null) {
								valueObject.put("transactionDate", getOLd_Inventory.getInvoiceDate());
							}else {
								valueObject.put("transactionDate", DateTimeUtility.getCurrentDate());
							}
							
							valueObject.put("partId", getOLd_Inventory.getPartid());
							valueObject.put("locationId",  getOLd_Inventory.getLocationId());
							valueObject.put("quantity", - getOLd_Inventory.getQuantity());
							valueObject.put("companyId", userDetails.getCompany_id());
							valueObject.put("addedQuantity", getOLd_Inventory.getQuantity());
							valueObject.put("usedQuantity", 0.0);
							valueObject.put("numberType", "I-"+ getOLd_Inventory.getInventory_Number()+" Part Delete");
							valueObject.put("transactionId", getOLd_Inventory.getInventory_id());
							valueObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
							valueObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_DELETED);
							
							dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject);	
						}
					}
					
				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/showInventory.in?inventory_all_id="
							+ getOLd_Inventory.getInventory_all_id() + "", model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
		}

		return new ModelAndView("redirect:/showInventory.in?inventory_all_id=" + inventory_all_id + "", model);
	}

	@RequestMapping(value = "/deleteLocationInventory", method = RequestMethod.GET)
	public ModelAndView deleteLocationInventory(@ModelAttribute("command") InventoryLocation Inventory_Location,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<InventoryDto> validate_Inventory = inventoryService.Get_InventoryAll_id_To_Inventory(Inventory_Location.getInventory_location_id(), userDetails.getCompany_id());
			if (validate_Inventory != null && !validate_Inventory.isEmpty()) {

				// if not equal to null show massage please delete that location
				// all quantity
				return new ModelAndView("redirect:/NewInventory/1.in?dangerLocation=true");
			} else {

				InventoryLocation getOLd_LocationInventory = inventoryService.getInventoryLocation(Inventory_Location.getInventory_location_id());

				List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

				if (getOLd_LocationInventory != null) {

					// check user branch location and workOrder location if same
					if (inventoryBL.isFilled_GET_Permission(getOLd_LocationInventory.getLocationId(), PartLocPermission)) {

						// save part Manufacturers Service in Master part
						try {
							inventoryService.deleteLocationInventory(Inventory_Location.getInventory_location_id(),	userDetails.getCompany_id());

							// Insert Inventory QUANTITY Details to Update
							// LOCATION AND ALL LOCATION QUANTITY DETAILS

							inventoryService.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(getOLd_LocationInventory.getPartid(), getOLd_LocationInventory.getLocationId());

							inventoryService.update_InventoryAll_PARTID_PARTNAME_GENRAL(getOLd_LocationInventory.getPartid());

							model.put("deleteInventory", true);

						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
						}

					} else {
						model.put("NoAuthen", true);
						return new ModelAndView("redirect:/showInventory.in?inventory_all_id="
								+ getOLd_LocationInventory.getInventoryall().getInventory_all_id() + "", model);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
		}

		return new ModelAndView("redirect:/NewInventory/1.in", model);
	}

	@RequestMapping(value = "/deleteAllInventory", method = RequestMethod.GET)
	public ModelAndView deleteAllInventory(@ModelAttribute("command") Inventory Inventory_all,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<InventoryLocationDto> validate_Inventory = inventoryService.Get_InventoryAll_id_To_Inventory_Location(Inventory_all.getInventory_all_id(), userDetails.getCompany_id());
			if (validate_Inventory != null && !validate_Inventory.isEmpty()) {

				// if not equal to null show massage please delete that all
				// quantity
				return new ModelAndView("redirect:/NewInventory/1.in?dangerAllInventory=true");
			} else {

				inventoryService.deleteInventoryAll(Inventory_all.getInventory_all_id(), userDetails.getCompany_id());

				model.put("deleteInventory", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
		}

		return new ModelAndView("redirect:/NewInventory/1.in", model);
	}

	@RequestMapping(value = "/searchAllInventory", method = RequestMethod.POST)
	public ModelAndView searchInventory(@RequestParam("SearchAllInv") String SearchAllInv,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<InventoryAllDto> InventoryAll = inventoryService.SearchlistInventoryAll(SearchAllInv, userDetails.getCompany_id());
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			InventoryAll	= inventoryBL.getFinalInventoryDetailDto(InventoryAll, measermentHM);
			
			model.put("InventoryAll", InventoryAll);
			model.put("PartLocations", partLocationsBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			model.put("CountName", "All");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("newInventory", model);
	}

	@RequestMapping(value = "/searchAllInventoryInvoice", method = RequestMethod.POST)
	public ModelAndView searchAllInventoryInvoice(@RequestParam("SearchAllInvInvoice") String SearchAllInvInvoice,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<InventoryDto> Inventory = inventoryService.SearchlistInventoryINVoice(SearchAllInvInvoice,	userDetails.getCompany_id());
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			Inventory = inventoryBL.getFinalInventoryDto(Inventory, measermentHM);
			model.put("Inventory", Inventory);
			model.put("PartLocations", partLocationsBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			model.put("CountName", "All");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("newInventoryINVOICE", model);
	}

	@RequestMapping(value = "/searchLocationInventory", method = RequestMethod.POST)
	public ModelAndView searchLocationInventory(@RequestParam("SearchLocInve") String SearchLocInve,
			@ModelAttribute("command") InventoryLocationDto Inventory_all,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<InventoryLocationDto> InventoryAll = inventoryService.SearchlistInventoryLocationOnLocation(SearchLocInve,
														userDetails.getCompany_id(), Inventory_all.getLocationId());
			HashMap<String, Object>  partMasterConfi	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
			model.put("InventoryLocation", InventoryAll);
			model.put("PartLocations", partLocationsBL.prepareListofBean(partLocationsService.listOnlyStatus()));
			model.put("CountName", "All");
			model.put("showPartLocationOption", partMasterConfi.getOrDefault("showPartLocationOption", false));
			model.put("location", partLocationsService.getPartLocations((int) Inventory_all.getLocationId()).getPartlocation_name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("newLocationInventory", model);
	}

	// Transfer Inventory move one location to another Location
	@RequestMapping(value = "/transferInventory", method = RequestMethod.GET)
	public ModelAndView transferInventory(@RequestParam("INVID") final Long INVENTORY_ID,
			@RequestParam("REQPID") final Long REQ_PART_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// get the Inventory id to value
			model.put("Inventory", inventoryService.getInventory(INVENTORY_ID));

			model.put("PartLocations", partLocationsBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			InventoryRequisitionPartsDto Part = inventoryRequisitionService.GET_Inventory_Requisition_ID_AND_Inventory_Requisition_PART(REQ_PART_ID,
							userDetails.getCompany_id());
			model.put("REQLOC", Part);
		} catch (Exception e) {
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("addTransferInventory", model);
	}

	@RequestMapping(value = "/saveTIOPEN", method = RequestMethod.POST)
	public ModelAndView savetransferInventoryOpen(@ModelAttribute("command") InventoryTransferDto InventoryTransfer,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;

		Long Inventory_all_id = (long) 0;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// get Transfer Inventory To all Details Create New Transfer
			// Location Quantity
			InventoryDto FROM_transferInventory = inventoryService.getInventory(InventoryTransfer.getTransfer_inventory_id());

			Inventory_all_id = FROM_transferInventory.getInventory_all_id();

			List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

			// check user branch location and workOrder location if same
			if (inventoryBL.isFilled_GET_Permission(FROM_transferInventory.getLocationId(), PartLocPermission)) {

				InventoryRequisitionPartsDto REQUISITION_PART = inventoryRequisitionService.GET_Inventory_Requisition_ID_AND_Inventory_Requisition_PART(InventoryTransfer.getINVRPARTID(),
								userDetails.getCompany_id());

				if (userDetails.getId() == REQUISITION_PART.getREQUISITION_RECEIVER_ID()) {

					Double Transfer_quantity = 0.0, PART_REQUITED_QTY = 0.0, PART_TRANSFER_QTY = 0.0,
							RequisitionPendingQTY = 0.0, Total_PART_TRANSFER_QTY = 0.0;

					if (InventoryTransfer.getTransfer_quantity() != null) {
						Transfer_quantity = round(InventoryTransfer.getTransfer_quantity(), 2);
					}

					if (REQUISITION_PART.getPART_REQUITED_QTY() != null) {
						PART_REQUITED_QTY = round(REQUISITION_PART.getPART_REQUITED_QTY(), 2);
					}

					if (REQUISITION_PART.getPART_TRANSFER_QTY() != null) {
						PART_TRANSFER_QTY = round(REQUISITION_PART.getPART_TRANSFER_QTY(), 2);
					}

					RequisitionPendingQTY = round((PART_REQUITED_QTY - PART_TRANSFER_QTY), 2);

					Total_PART_TRANSFER_QTY = PART_TRANSFER_QTY + Transfer_quantity;

					if (Transfer_quantity <= RequisitionPendingQTY) {
						InventoryTransfer createTransferIn = inventoryBL.prepareCreateInventory_To_TransferInventory(FROM_transferInventory, InventoryTransfer);
						try {

							createTransferIn.setTransfer_by_ID(userDetails.getId());
							createTransferIn.setTransfer_receivedby_ID(REQUISITION_PART.getREQUITED_SENDER_ID());
							createTransferIn.setTransfer_to_locationId(REQUISITION_PART.getREQUITED_LOCATION_ID());
							createTransferIn.setTRANSFER_STATUS_ID(InventoryTransferStatus.TRANSFERED);
							if(REQUISITION_PART.getINVRID() != null ) {
							createTransferIn.setInventoryRequisitionId(REQUISITION_PART.getINVRID());
							}

							inventoryService.add_InventoryTransfer(createTransferIn);

							model.put("saveTransferInventory", true);

							inventoryRequisitionService.UPDATE_TRANSFER_QTY_IN_PART(round((Total_PART_TRANSFER_QTY), 2), InventoryTransfer.getINVRPARTID(), userDetails.getCompany_id());

						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
						}

					} else {
						model.put("NoQty", true);
						return new ModelAndView(
								"redirect:/1/1/showInventoryReq.in?ID=" + InventoryTransfer.getINVRID() + "", model);
					}
				} else {
					model.put("NoEmail", true);
					return new ModelAndView("redirect:/showInventory.in?inventory_all_id=" + Inventory_all_id + "",
							model);
				}

			} else {
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/showInventory.in?inventory_all_id=" + Inventory_all_id + "", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
		}

		return new ModelAndView("redirect:/1/1/showInventoryReq.in?ID=" + InventoryTransfer.getINVRID() + "", model);
	}

	/* save the vehicle Status */
	@RequestMapping(value = "/savetransferInventoryRec", method = RequestMethod.POST)
	public ModelAndView savetransferInventory(@ModelAttribute("command") InventoryRequisitionDto InventoryDto, @RequestParam("inventory_transfer_id") final Long inventory_transfer_id,
			@RequestParam("transfer_receivedby_ID") final Long transfer_receivedby_ID,
			@RequestParam("transfer_receivedReason") final String Transfer_receivedReason,
			final HttpServletRequest request) {
		
		Map<String, Object> 			model 					= new HashMap<String, Object>();
		Long 							Inventory_all_id 		= (long) 0;
		Long 							Inventory_Location_id 	= (long) 0;
		CustomUserDetails 				userDetails 			= null;
		InventoryTransfer 				InventoryTransfer		= null;
		InventoryDto 					FROM_transferInventory	= null;
		InventoryAll 					Inventoryall 			= null;
		InventoryLocation 				InventoryLocation 		= null; 
		Inventory 						Part					= null;

		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			InventoryTransfer = inventoryService.Find_InventoryTranfer_Details_ID(inventory_transfer_id, userDetails.getCompany_id());

			Inventory_all_id = InventoryTransfer.getTransfer_inventory_all_id();
			/** check user branch location and workOrder location if same */
			if (userDetails.getId() == InventoryTransfer.getTransfer_receivedby_ID()) {
				
				HashMap<String, Object>  configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);

				// get Transfer Inventory To all Details Create New Transfer
				// Location Quantity
				FROM_transferInventory 	= inventoryService.getInventory(InventoryTransfer.getTransfer_inventory_id());

				Inventoryall 			= inventoryBL.prepareInventoryAll_To_TransferInventory(FROM_transferInventory);
				InventoryLocation 		= inventoryBL.prepareInventoryLocation_To_TransferInventory(FROM_transferInventory, Inventoryall);
				Part 					= inventoryBL.prepareModel_To_TransferInventory(FROM_transferInventory);

				// save Inventory Transfer Part Quantity
				try {

					/** Sub track Transfer part quantity value */
					inventoryService.Subtract_update_Inventory_from_Workorder(InventoryTransfer.getTransfer_quantity(),	FROM_transferInventory.getInventory_id());

					/** This Transfer location sub track value to update */
					inventoryService.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(InventoryTransfer.getTransfer_partid(), InventoryTransfer.getTransfer_from_locationId());

					
					if((boolean) configuration.get("showRefreshmentOption")) {
						MasterParts	masterParts	=	masterPartsService.getMasterParts(FROM_transferInventory.getPartid());
						if(masterParts != null && masterParts.isRefreshment()) {
							
								
								ValueObject	valueObject	= new ValueObject();
								if(FROM_transferInventory.getInvoiceDate() != null) {
									valueObject.put("transactionDate", InventoryTransfer.getTransfer_date());
								}else {
									valueObject.put("transactionDate", DateTimeUtility.getCurrentDate());
								}
								
								valueObject.put("partId", FROM_transferInventory.getPartid());
								valueObject.put("locationId",  FROM_transferInventory.getLocationId());
								valueObject.put("companyId", userDetails.getCompany_id());
								valueObject.put("addedQuantity", - InventoryTransfer.getTransfer_quantity());
								valueObject.put("usedQuantity", 0.0);
								valueObject.put("numberType", "I-"+ FROM_transferInventory.getInventory_Number()+" Part Edit");
								valueObject.put("transactionId", FROM_transferInventory.getInventory_id());
								valueObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_TRANSFER);
								valueObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_TRANSFER);
								valueObject.put("quantity", - InventoryTransfer.getTransfer_quantity());
								valueObject.put("isDateChanged", false);
								
								dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject);	
							
						}
					}
					if((boolean) configuration.get("showRefreshmentOption")) {
						MasterParts	masterParts	=	masterPartsService.getMasterParts(FROM_transferInventory.getPartid());
						if(masterParts != null && masterParts.isRefreshment()) {
							ValueObject	valueObject	= new ValueObject();
							if(Part.getInvoice_date() != null) {
								valueObject.put("transactionDate", InventoryTransfer.getTransfer_date());
							}else {
								valueObject.put("transactionDate", DateTimeUtility.getCurrentDate());
							}
							
							valueObject.put("partId", FROM_transferInventory.getPartid());
							valueObject.put("locationId", InventoryTransfer.getTransfer_to_locationId());
							valueObject.put("quantity", InventoryTransfer.getTransfer_quantity());
							valueObject.put("companyId", userDetails.getCompany_id());
							valueObject.put("addedQuantity", InventoryTransfer.getTransfer_quantity());
							valueObject.put("usedQuantity", 0.0);
							valueObject.put("numberType", "I-"+ FROM_transferInventory.getInventory_Number()+" Part transfer");
							valueObject.put("transactionId", FROM_transferInventory.getInventory_id());
							valueObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_TRANSFER);
							valueObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_TRANSFER);
							dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject);	
						}
					}
					Inventory_all_id = FROM_transferInventory.getInventory_all_id();

					// Add in InventoryLocation Quantity So Transfer Quantity
					// Search InventoryLocation Quantity to Transfer Quantity
					// and
					// Location there or not
					List<InventoryLocation> validate_Location = inventoryService.Validate_Inventory_Location(FROM_transferInventory.getPartid(), InventoryTransfer.getTransfer_to_locationId());
					if (validate_Location != null && !validate_Location.isEmpty()) {
						for (InventoryLocation validateLocation : validate_Location) {
							// get part and Location Quantity in

							Inventory_Location_id = validateLocation.getInventory_location_id();
							break;
						}
					} else {

						// get part and Location Quantity Search that
						// location part not in db Create new
						InventoryLocation.setLocation_quantity(InventoryTransfer.getTransfer_quantity());
						// InventoryLocation.setLocation(InventoryTransfer.getTransfer_to_location());
						InventoryLocation.setLocationId(InventoryTransfer.getTransfer_to_locationId());
						InventoryAll NewAll = new InventoryAll();
						NewAll.setInventory_all_id(Inventory_all_id);
						InventoryLocation.setInventoryall(NewAll);
						inventoryService.add_InventoryLocation(InventoryLocation);

						Inventory_Location_id = InventoryLocation.getInventory_location_id();

					}

					// add New Create Transfer From Quantity Location to
					// Transfer
					// location
					Part.setHistory_quantity(InventoryTransfer.getTransfer_quantity());
					Part.setQuantity(InventoryTransfer.getTransfer_quantity());
					// Part.setLocation(InventoryTransfer.getTransfer_to_location());
					Part.setLocationId(InventoryTransfer.getTransfer_to_locationId());

					// calculation to inventory total cost
					Double Quantity = 0.0;
					if (InventoryTransfer.getTransfer_quantity() != null) {
						Quantity = InventoryTransfer.getTransfer_quantity();
					}
					Double eaccost = 0.0;
					if (FROM_transferInventory.getUnitprice() != null) {
						eaccost = FROM_transferInventory.getUnitprice();
					}
					Double dis_Ca = 0.0;
					if (FROM_transferInventory.getDiscount() != null) {
						dis_Ca = FROM_transferInventory.getDiscount();
					}
					Double tax_Ca = 0.0;
					if (FROM_transferInventory.getTax() != null) {
						tax_Ca = FROM_transferInventory.getTax();
					}

					Double discountCost = 0.0;
					Double TotalCost = 0.0;

					discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
					TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
					Part.setTotal(TotalCost);

					Part.setInventory_all_id(Inventory_all_id);
					Part.setInventory_location_id(Inventory_Location_id);
					Part.setSerialNoAddedForParts(0);

					inventoryService.save_Inventory(Part);

					model.put("saveTransferInventory", true);

					// Insert Inventory QUANTITY Details to Update
					// LOCATION AND ALL LOCATION QUANTITY DETAILS

					inventoryService.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(InventoryTransfer.getTransfer_partid(), InventoryTransfer.getTransfer_to_locationId());

					inventoryService.update_InventoryAll_PARTID_PARTNAME_GENRAL(InventoryTransfer.getTransfer_partid());

					// get Current days
					java.util.Date currentDate = new java.util.Date();
					Timestamp Transfer_receiveddate = new java.sql.Timestamp(currentDate.getTime());

					inventoryService.Update_InventoryTransfer_ReceivedBy_Details(Transfer_receiveddate,	Transfer_receivedReason, userDetails.getEmail(), Transfer_receiveddate,
						 InventoryTransferStatus.RECEIVED,	InventoryTransfer.getInventory_transfer_id(), userDetails.getCompany_id());
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
				}
				
				if(InventoryDto.getUserId() != null) {
					UserAlertNotifications	notifications = new UserAlertNotifications();
					notifications.setUserId(InventoryDto.getUserId());
					notifications.setAlertMsg(InventoryDto.getAlertMsg());
					notifications.setCompanyId(userDetails.getCompany_id());
					notifications.setTxnTypeId(UserAlertNOtificationsConstant.ALERT_TYPE_PART_REQUISITION);
					notifications.setTransactionId(InventoryDto.getINVRID());
					notifications.setCreatedById(userDetails.getId());
					notifications.setCreatedOn(new Date());
					notifications.setStatus((short) 1);
					userAlertNotificationsRepository.save(notifications);
				}
				
			} else {
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/showInventory.in?inventory_all_id=" + Inventory_all_id + "", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
		}
		return new ModelAndView("redirect:/showInventory.in?inventory_all_id=" + Inventory_all_id + "", model);
	}

	@RequestMapping(value = "/transferInventoryHistory", method = RequestMethod.GET)
	public ModelAndView transferInventoryHistory(@ModelAttribute("command") InventoryAll InventoryAll,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// get the InventoryAll id to value
			InventoryAllDto inventoryall = inventoryService.getInventoryAll(InventoryAll.getInventory_all_id(),	userDetails.getCompany_id());
			model.put("InventoryAll", inventoryall);

			List<InventoryTransferDto> show_Location = inventoryService.Get_InventoryAll_id_To_InventoryTransfer(InventoryAll.getInventory_all_id(), userDetails.getCompany_id());
			model.put("InventoryTransfer", show_Location);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		} finally {
			userDetails = null;
		}
		return new ModelAndView("showTransferInventory", model);
	}

	@RequestMapping(value = "/transferInventoryDelete", method = RequestMethod.GET)
	public ModelAndView transferInventoryDelete(@RequestParam("TIALLID") final Long inventory_all_id,
			@RequestParam("ITID") final Long inventory_transfer_id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			// get the InventoryAll id to value
			inventoryService.Delete_InventoryTransfer_History_Delete_By_ID(inventory_transfer_id, userDetails.getCompany_id());

			model.put("delete", true);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("redirect:/transferInventoryHistory.in?inventory_all_id=" + inventory_all_id + "",
				model);
	}

	@RequestMapping(value = "/YTIHISTORY/{pageNumber}", method = RequestMethod.GET)
	public String YourTransferPart(@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		try {
			Page<InventoryTransfer> page = inventoryService.getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_byEmail_Status(userDetails.getId(),
							pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("InventoryCount", page.getTotalElements());

			List<InventoryTransferDto> show_Location = inventoryService.Get_InventoryAll_id_To_InventoryTransfer_transfer_byEmail_Status(userDetails.getId(), pageNumber,
							userDetails.getCompany_id());
			model.addAttribute("InventoryTransfer", show_Location);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newInventory Page:", e);
			e.printStackTrace();
		}
		return "YourTransferPart";
	}

	@RequestMapping(value = "/YTIRECEIVEDHISTORY/{pageNumber}", method = RequestMethod.GET)
	public String YourReceivedPart(@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);

		Page<InventoryTransfer> page = inventoryService.getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_receivedbyEmail(userDetails.getId(),
						pageNumber, userDetails.getCompany_id());
		try {
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("configuration", configuration);

			model.addAttribute("InventoryCount", page.getTotalElements());

			List<InventoryTransferDto> show_Location = inventoryService.Get_InventoryAll_id_To_InventoryTransfer_transfer_receivedbyEmail(userDetails.getId(), pageNumber,
							userDetails.getCompany_id());
			model.addAttribute("InventoryTransfer", show_Location);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newInventory Page:", e);
			e.printStackTrace();
		}
		return "YourReceivedPart";
	}

	@RequestMapping(value = "/transferInventoryReq", method = RequestMethod.GET)
	public ModelAndView transferInventoryReq(@RequestParam("PARTID") final Long PART_ID,
			@RequestParam("REQPID") final Long REQ_PART_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// get the InventoryAll id to value
			InventoryAllDto InventoryAll = inventoryService.Get_InventoryAll_USEING_PART_ID(PART_ID, userDetails.getCompany_id());
			if (InventoryAll != null) {
				model.put("InventoryAll", InventoryAll);
				List<InventoryLocationDto> show_Location = inventoryService.Get_InventoryAll_id_To_Inventory_Location(InventoryAll.getInventory_all_id(), userDetails.getCompany_id());
				model.put("InventoryLocation", show_Location);
				List<InventoryDto> show_Inventory = inventoryService.Show_AvailableQYT_InventoryAll_id_To_Inventory(InventoryAll.getInventory_all_id(), userDetails.getCompany_id());
				model.put("Inventory", show_Inventory);
			}

			model.put("REQPID", REQ_PART_ID);

		} catch (Exception e) {
			System.err.println("Exception : "+e);
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("showInventory", model);
	}

	/*****************************************************************************************************
	 ***************** Select drop down List **********************
	 *****************************************************************************************************/

	@RequestMapping(value = "/getSearchMasterPart", method = RequestMethod.POST)
	public void getSearchMasterPart(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<MasterPartsDto> Part = new ArrayList<MasterPartsDto>();
		List<MasterPartsDto> Parttype = masterPartsService.SearchInventorySelectParts(term, userDetails.getCompany_id());
		
		if (Parttype != null && !Parttype.isEmpty()) {
			for (MasterPartsDto add : Parttype) {

				MasterPartsDto wadd = new MasterPartsDto();

				wadd.setPartid(add.getPartid());
				wadd.setPartname(add.getPartname());
				wadd.setPartnumber(add.getPartnumber());
				wadd.setCategory(add.getCategory());
				wadd.setMake(add.getMake());

				Part.add(wadd);
				
			}
		}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Part));
	}
	
	@RequestMapping(value = "/getSearchMasterPartOnType", method = RequestMethod.POST)
	public void getSearchMasterPartOnType(Map<String, Object> map, @RequestParam("term") final String term,
			 @RequestParam("partTypeCategory") final short partTypeCategory,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<MasterPartsDto> Part = new ArrayList<MasterPartsDto>();
		
		short categoryToFetch = 0;
		if(partTypeCategory == 3) {
			categoryToFetch = 2;
		}else {
			categoryToFetch = 3;
		}
		
		List<MasterPartsDto> Parttype = masterPartsService.SearchInventorySelectPartsOnType(term, 
										userDetails.getCompany_id(), categoryToFetch);
		
		if (Parttype != null && !Parttype.isEmpty()) {
			for (MasterPartsDto add : Parttype) {

				MasterPartsDto wadd = new MasterPartsDto();

				wadd.setPartid(add.getPartid());
				wadd.setPartname(add.getPartname());
				wadd.setPartnumber(add.getPartnumber());
				wadd.setCategory(add.getCategory());
				wadd.setMake(add.getMake());

				Part.add(wadd);
				
			}
		}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Part));
	}

	// get Vehicle Drop down To Current Odometer
	@RequestMapping(value = "/getMasterPartShow", method = RequestMethod.GET)
	public void getMasterPartShow(@RequestParam(value = "PartID", required = true) Long MasterPart_id,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MasterPartsDto add = masterPartsService.getMasterParts(MasterPart_id, userDetails.getCompany_id());
		MasterPartsDto wadd = new MasterPartsDto();
		if (add != null) {
			wadd.setPartname(add.getPartname());
			wadd.setPartnumber(add.getPartnumber());
			wadd.setMake(add.getMake());
			wadd.setPart_photoid(add.getPart_photoid());
			wadd.setUnitCost(add.getUnitCost());
			wadd.setDiscount(add.getDiscount());
			wadd.setTax(add.getTax());
		}
		Gson gson = new Gson();
		// System.out.println("jsonODDMETER = " + gson.toJson(wadd));

		response.getWriter().write(gson.toJson(wadd));
	}

	/** load search vendor part and service vendor name */
	@RequestMapping(value = "/getVendorSearchListInventory", method = RequestMethod.POST)
	public void getVendorSearchListInventory(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = null;
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Vendor> addresses = new ArrayList<Vendor>();
		List<Vendor> vendor = vendorService.SearchOnly_PART_VendorName(term, userDetails.getCompany_id());
		if (vendor != null && !vendor.isEmpty()) {
			for (Vendor add : vendor) {
				Vendor wadd = new Vendor();
				if (add != null) {
					wadd.setVendorId(add.getVendorId());
					wadd.setVendorName(add.getVendorName());
					wadd.setVendorLocation(add.getVendorLocation());
					addresses.add(wadd);
				}
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/getPurchaseOrderStock", method = RequestMethod.GET)
	public void getFuelVehicleOdoMerete(@RequestParam(value = "PARTID", required = true) Long PART_ID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

	CustomUserDetails userDetails = null;
	HashMap<String, Object> poConfiguration = null;
		InventoryAllDto wadd = new InventoryAllDto();
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		poConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
		Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
		
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		InventoryAllDto invent = inventoryService.Purchase_Order_Validate_InventoryAll(PART_ID);
		Double TotalSustituteQnty = 0.0;
		if((boolean) poConfiguration.getOrDefault("showSustitutePartCount", false)) {
			List<InventoryLocationDto> substitudePart= inventoryService.getSubstitudePartList(PART_ID+"", userDetails.getCompany_id());
			if(substitudePart != null && !substitudePart.isEmpty())
				for(InventoryLocationDto part:substitudePart) {
					TotalSustituteQnty+=part.getLocation_quantity();
				}
		}
			
		invent	= inventoryBL.getFinalInventoryDetailDto(invent, measermentHM);
		
		//PurchaseOrdersToPartsDto purchaseOrdersToPartsDto =purchaseOrdersService.getLeatestPartFromPO(PART_ID);
		if (invent != null) {
			wadd.setInventory_all_id(invent.getInventory_all_id());
			wadd.setPartnumber(invent.getPartnumber());
			wadd.setPartname(invent.getPartname());
			wadd.setAll_quantity(invent.getAll_quantity());
			wadd.setUnitCost(invent.getUnitCost());
			wadd.setDiscount(invent.getDiscount());
			wadd.setTax(invent.getTax());
			if(invent.getConvertToStr() != null) {
				wadd.setConvertToStr(invent.getConvertToStr());
			}else {
				wadd.setConvertToStr(" ");
			}
		}else {
			wadd.setAll_quantity(0.0);
			wadd.setConvertToStr(" ");
		}
		wadd.setTotalSubstitudeQty(TotalSustituteQnty);
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(wadd));
	}

	java.util.Date currentDate = new java.util.Date();
	DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");

	// save the Inventory Model
	public Inventory prepareModel(InventoryDto InventoryDto, Long Master_Part_ID) throws ParseException {
		Inventory part = new Inventory();

		part.setInventory_id(InventoryDto.getInventory_id());
		part.setPartid(Master_Part_ID);
		part.setLocationId(InventoryDto.getLocationId());
		part.setSubLocationId(InventoryDto.getSubLocationId());
		part.setPurchaseorder_id(DEFAULT_PURCHAGE_ORDER_VALUE);
		part.setInvoice_number(InventoryDto.getInvoice_number());
		part.setInvoice_amount(InventoryDto.getInvoice_amount());
		if (InventoryDto.getInvoice_date() != null) {
			java.util.Date date = sqlDateFormat.parse(InventoryDto.getInvoice_date());
			java.sql.Date start_date = new java.sql.Date(date.getTime());
			part.setInvoice_date(start_date);
		}

		if (InventoryDto.getVendor_id() != 0) {
			part.setVendor_id(InventoryDto.getVendor_id());
		} else {
			part.setVendor_id(0);
		}

		part.setDescription(InventoryDto.getDescription());

		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		part.setCreatedById(userDetails.getId());
		part.setLastModifiedById(userDetails.getId());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		part.setCreated(toDate);
		part.setLastupdated(toDate);

		part.setMarkForDelete(false);
		
		return part;
	}

	// update the Inventory Model
	public Inventory prepareUpdateInventoryModel(InventoryDto InventoryDto, Long Master_part_ID) throws ParseException {
		Inventory part = new Inventory();

		part.setInventory_id(InventoryDto.getInventory_id());
		part.setPartInvoiceId(InventoryDto.getPartInvoiceId());

		part.setInventory_Number(InventoryDto.getInventory_Number());
		// get search data to get part details
		part.setPartid(Master_part_ID);
		// part.setPartnumber(Master.getPartnumber());
		// part.setPartname(Master.getPartname());
		// part.setParttype(Master.getParttype());
		// part.setPartTypeId(Master.getPartTypeId());
		// part.setUnittype(Master.getUnittype());
		// part.setCategory(Master.getCategory());
		// part.setPart_photoid(Master.getPart_photoid());
		// part.setUnittype(Master.getUnittype());

		// part.setLocation(InventoryDto.getLocation());
		part.setLocationId(InventoryDto.getLocationId());

		part.setHistory_quantity(InventoryDto.getQuantity());
		part.setQuantity(InventoryDto.getQuantity());
		part.setUnitprice(InventoryDto.getUnitprice());

		part.setDiscount(InventoryDto.getDiscount());
		part.setTax(InventoryDto.getTax());

		Double Quantity = InventoryDto.getQuantity();
		Double eaccost = InventoryDto.getUnitprice();
		Double dis_Ca = InventoryDto.getDiscount();
		Double tax_Ca = InventoryDto.getTax();

		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
		TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
		part.setTotal(TotalCost);

		if (InventoryDto.getMake() != null) {

			part.setMake(InventoryDto.getMake());
		}

		part.setPurchaseorder_id(InventoryDto.getPurchaseorder_id());
		part.setInvoice_number(InventoryDto.getInvoice_number());
		part.setInvoice_amount(InventoryDto.getInvoice_amount());
		if (InventoryDto.getInvoice_date() != null) {
			java.util.Date date = sqlDateFormat.parse(InventoryDto.getInvoice_date());
			java.sql.Date start_date = new java.sql.Date(date.getTime());
			part.setInvoice_date(start_date);
		}

		if (InventoryDto.getVendor_id() != 0) {
			part.setVendor_id(InventoryDto.getVendor_id());
		} else {
			part.setVendor_id(0);
		}

		part.setDescription(InventoryDto.getDescription());

		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		part.setCreatedById(userDetails.getId());
		part.setLastModifiedById(userDetails.getId());
		part.setCompanyId(userDetails.getCompany_id());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		part.setCreated(toDate);
		part.setLastupdated(toDate);
		part.setMarkForDelete(false);
		part.setSubLocationId(InventoryDto.getSubLocationId());
		return part;
	}

	// Image Document the Document id
	@RequestMapping(value = "/downloadinventorypartdocument", method = RequestMethod.GET)
	public String download(HttpServletResponse response) {

		List<MasterPartsDto>		masterPartsDtoList				= null;
		CustomUserDetails 			userDetails 					= null;
		HSSFWorkbook 				hssfWorkbook					= null;
		HSSFSheet 					hssfSheet						= null;
		HSSFRow 					hssfRow							= null; 

		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();   
			masterPartsDtoList	= masterPartsService.listMasterPartsByCompanyId(userDetails.getCompany_id());
			
			hssfWorkbook 		= new HSSFWorkbook();
			hssfSheet 			= hssfWorkbook.createSheet();
			hssfRow 			= hssfSheet.createRow((short) 0);
			
			hssfRow.createCell(0).setCellValue("Manufacturer");
			hssfRow.createCell(1).setCellValue("Part No.");
			hssfRow.createCell(2).setCellValue("Part Name");
			hssfRow.createCell(3).setCellValue("Quantity");
			hssfRow.createCell(4).setCellValue("Amount");

			if(masterPartsDtoList != null) {
				for (int i = 0; i < masterPartsDtoList.size(); i++) {
					hssfRow 	= hssfSheet.createRow((short) i+1);
					hssfRow.createCell(0).setCellValue(masterPartsDtoList.get(i).getMake());
					hssfRow.createCell(1).setCellValue(masterPartsDtoList.get(i).getPartnumber());
					hssfRow.createCell(2).setCellValue(masterPartsDtoList.get(i).getPartname());
					hssfRow.createCell(3).setCellValue(0);
					hssfRow.createCell(4).setCellValue(0);
				}
			}


			FileOutputStream fileOut = new FileOutputStream(new File("Part_Inventory.xlsx"));
			hssfWorkbook.write(fileOut);
			fileOut.close();

			//Code to download
			File fileToDownload = new File("Part_Inventory.xlsx");
			InputStream in = new FileInputStream(fileToDownload);

			// Gets MIME type of the file
			String mimeType = new MimetypesFileTypeMap().getContentType("Part_Inventory.xlsx");

			if (mimeType == null) {
				// Set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			// Modifies response
			response.setContentType(mimeType);
			response.setContentLength((int) fileToDownload.length());

			// Forces download
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileToDownload.getName());
			response.setHeader(headerKey, headerValue);

			// obtains response's output stream
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = in.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			in.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return null;
	}
	
	@SuppressWarnings({ "resource", "deprecation" })
	@RequestMapping(value = "/importPartInventory", method = RequestMethod.POST)
	public ModelAndView saveImport(@ModelAttribute("command") VehicleDocument photo,
			@RequestParam("import") MultipartFile file,
			@RequestParam("locationId") final Integer locationId,HttpServletRequest request) throws Exception {

		Map<String, Object> 				model 						= null; 
		CustomUserDetails					userDetails					= null;
		String 								rootPath 					= null;
		File 								dir 						= null;
		File 								serverFile 					= null;
		FileInputStream 					fis 						= null;
		Workbook	 						myWorkBook 					= null; 
		org.apache.poi.ss.usermodel.Sheet	 							mySheet 					= null;
		Iterator<Row> 						rowIterator 				= null; 
		Inventory							partInventory				= null;
		ArrayList<Inventory>				partInventoryList			= null;
		List<MasterPartsDto> 				masterPartsList				= null;
		Vendor 								vendor 						= null;
		VendorSequenceCounter   			sequenceCounter 			= null;
		int 								noOfRows2					= 0;
		List<String> 						notAddedParts				= null;

		try {
			model 					= new HashMap<String, Object>();
			partInventoryList		= new ArrayList<>();
			notAddedParts			= new ArrayList<>();
			rootPath 				= request.getSession().getServletContext().getRealPath("/");
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			masterPartsList			= masterPartsService.listMasterPartsByCompanyId(userDetails.getCompany_id());

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
			myWorkBook = WorkbookFactory.create(fis);
		    
			mySheet = myWorkBook.getSheetAt(0);

			// Return first sheet from the XLSX workbook
			mySheet = myWorkBook.getSheetAt(0);

			// Get iterator to all the rows in current sheet
			rowIterator = mySheet.iterator();
			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			vendor		= new Vendor();
			
			vendor.setVendorName("Dummy");
			vendor.setCreated(toDate);
			vendor.setCreatedById(userDetails.getId());
			vendor.setLastupdated(toDate);
			vendor.setLastModifiedById(userDetails.getId());
			vendor.setMarkForDelete(false);
			vendor.setCompanyId(userDetails.getCompany_id());
			vendor.setVendorTypeId(22);
			vendor.setVendorGSTRegisteredId((short) 1);
			vendor.setVendorTermId((short) 1);
			vendor.setAutoVendor(true);
			
			sequenceCounter = vendorSequenceService.findNextVendorNumber(userDetails.getCompany_id());
			vendor.setVendorNumber((int) sequenceCounter.getNextVal());
			// save to databases
			vendorService.addVendor(vendor);
			// Traversing over each row of XLSX file
			rowIterator = mySheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				noOfRows2 += 1;
				if(noOfRows2 == 1) {
					continue;
				}
				
				partInventory	= new Inventory();
				
				partInventory.setCompanyId(userDetails.getCompany_id());
				partInventory.setPurchaseorder_id(DEFAULT_PURCHAGE_ORDER_VALUE);
				partInventory.setVendor_id(vendor.getVendorId());
				partInventory.setInvoice_date(toDate);
				partInventory.setDescription("Dummy Part inventory");
				partInventory.setCreatedById(userDetails.getId());
				partInventory.setLastModifiedById(userDetails.getId());
				partInventory.setCreated(toDate);
				partInventory.setLastupdated(toDate);
				partInventory.setMarkForDelete(false);
				partInventory.setLocationId(locationId);
				
				Cell cell	= null;
				String cellValue	= null;
				String manufacturer	= null;
				for(int i=0; i < 6; i++) {
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
							partInventory.setMake(cellValue);
							manufacturer	= cellValue;
						} else {
							partInventory	= null;
							break;
						}
					}
					if(i == 1) {
						if(cellValue != null && !cellValue.isEmpty()) {
							for (int j = 0; j < masterPartsList.size(); j++) {
								if(masterPartsList.get(j).getPartnumber().equalsIgnoreCase(cellValue.trim()) && masterPartsList.get(j).getMake().equalsIgnoreCase(manufacturer.trim())) {
									partInventory.setPartid(masterPartsList.get(j).getPartid());
									break;
								}
							}
						} else {
							partInventory	= null;
							break;
						}
					}
					if(i == 3) {
						if(cellValue != null && !cellValue.isEmpty() && Double.parseDouble(cellValue) > 0) {
							partInventory.setQuantity(Double.parseDouble(cellValue));
						} else {
							partInventory	= null;
							break;
						}
					}
					if(i == 4) {
						if(cellValue != null && !cellValue.isEmpty()) {
							partInventory.setUnitprice(Double.parseDouble(cellValue));
						}
					}
					
				}
				if(partInventory	!= null) {
					if(partInventory.getPartid() != null) 
						partInventoryList.add(partInventory);
						
				}
			}
			
			if(partInventoryList != null && partInventoryList.size() > 0) {
				saveMultiplePartInventory(partInventoryList);
			}
			
			System.err.println("notAddedParts : "+notAddedParts);
			model.put("partsAdded", partInventoryList.size());
			return new ModelAndView("redirect:/NewInventory/1.in", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model 						= null;
			userDetails					= null;
			rootPath 					= null;
			dir 						= null;
			serverFile 					= null;
			fis 						= null;
			myWorkBook 					= null;
			mySheet 					= null;
			rowIterator 				= null;
			partInventory				= null;
			partInventoryList			= null;
			masterPartsList				= null;
			noOfRows2					= 0;   
		}
	}

	private void saveMultiplePartInventory(ArrayList<Inventory> partInventoryList) throws Exception {
		
		Double Quantity 	= 0.0;
		Double eaccost 		= 0.0; 
		Double dis_Ca 		= 0.0;
		Double tax_Ca 		= 0.0;
		Double discountCost = 0.0;
		Double TotalCost 	= 0.0;
		
		try {
			for (int i = 0; i < partInventoryList.size(); i++) {
				Long MasterPart_ID = partInventoryList.get(i).getPartid();

				InventoryAll Inventoryall = inventoryBL.prepareInventoryAll(MasterPart_ID);
				InventoryLocation InventoryLocation = new InventoryLocation();
				
				InventoryLocation.setPartid(MasterPart_ID);
				InventoryLocation.setLocationId(partInventoryList.get(i).getLocationId());
				InventoryLocation.setCompanyId(Inventoryall.getCompanyId());
				Inventory Part = new Inventory();

				Part.setPartid(MasterPart_ID);
				Part.setLocationId(partInventoryList.get(i).getLocationId());
				Part.setPurchaseorder_id(DEFAULT_PURCHAGE_ORDER_VALUE);
				Part.setVendor_id(partInventoryList.get(i).getVendor_id());
				Part.setInvoice_date(partInventoryList.get(i).getInvoice_date());
				Part.setDescription("Dummy Part inventory");
				Part.setCompanyId(partInventoryList.get(i).getCompanyId());
				Part.setCreatedById(partInventoryList.get(i).getCreatedById());
				Part.setLastModifiedById(partInventoryList.get(i).getLastModifiedById());
				Part.setCreated(partInventoryList.get(i).getCreated());
				Part.setLastupdated(partInventoryList.get(i).getLastupdated());
				Part.setMarkForDelete(false);
				
				List<InventoryAll> validate = inventoryService.Validate_InventoryAll(MasterPart_ID,	Inventoryall.getCompanyId());
				if (validate != null && !validate.isEmpty()) {

					Long Inventory_all_id = (long) 0;
					Long Inventory_Location_id = (long) 0;

					for (InventoryAll updateInventory : validate) {
						Inventory_all_id = updateInventory.getInventory_all_id();
						break;
					}
					List<InventoryLocation> validate_Location = inventoryService.Validate_Inventory_Location(MasterPart_ID, partInventoryList.get(i).getLocationId());
					if (validate_Location != null && !validate_Location.isEmpty()) {
						for (InventoryLocation validateLocation : validate_Location) {
							Inventory_Location_id = validateLocation.getInventory_location_id();
							break;
						}
					} else {
						InventoryLocation.setLocation_quantity(partInventoryList.get(i).getQuantity());

						InventoryAll nEWInventoryall = new InventoryAll();
						nEWInventoryall.setInventory_all_id(Inventory_all_id);

						InventoryAll NewAll = new InventoryAll();
						NewAll.setInventory_all_id(Inventory_all_id);
						InventoryLocation.setInventoryall(NewAll);

						inventoryService.add_InventoryLocation(InventoryLocation);

						Inventory_Location_id = InventoryLocation.getInventory_location_id();
					}

					Part.setHistory_quantity(partInventoryList.get(i).getQuantity());
					Part.setQuantity(partInventoryList.get(i).getQuantity());

					Quantity 		= partInventoryList.get(i).getQuantity();
					
					if(partInventoryList.get(i).getUnitprice() != null && partInventoryList.get(i).getUnitprice() > 0) {
						eaccost 		= partInventoryList.get(i).getUnitprice();						
						Part.setUnitprice(partInventoryList.get(i).getUnitprice());
					}
					
					if(partInventoryList.get(i).getDiscount() != null && partInventoryList.get(i).getDiscount() > 0) {
						dis_Ca 			= partInventoryList.get(i).getDiscount();
						Part.setDiscount(partInventoryList.get(i).getDiscount());
					}
					
					if(partInventoryList.get(i).getTax() != null && partInventoryList.get(i).getTax() > 0) {
						tax_Ca 			= partInventoryList.get(i).getTax();
						Part.setTax(partInventoryList.get(i).getTax());
					}

					discountCost 	= (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
					TotalCost 		= round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
					
					Part.setTotal(TotalCost);

					if (partInventoryList.get(i).getMake() != null) {
						Part.setMake(partInventoryList.get(i).getMake());
					}
					
					Part.setInventory_all_id(Inventory_all_id);
					Part.setInventory_location_id(Inventory_Location_id);
					Part.setSerialNoAddedForParts(0);

					inventoryService.save_Inventory(Part);

					inventoryService.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(MasterPart_ID, partInventoryList.get(i).getLocationId());

					inventoryService.update_InventoryAll_PARTID_PARTNAME_GENRAL(MasterPart_ID);
				} else {
					// get part and AllQuantity save
					Inventoryall.setAll_quantity(partInventoryList.get(i).getQuantity());
					inventoryService.add_InventoryAll(Inventoryall);

					// get part and Location Quantity save
					InventoryLocation.setLocation_quantity(partInventoryList.get(i).getQuantity());
					InventoryLocation.setInventoryall(Inventoryall);
					inventoryService.add_InventoryLocation(InventoryLocation);

					Part.setHistory_quantity(partInventoryList.get(i).getQuantity());
					Part.setQuantity(partInventoryList.get(i).getQuantity());
					
					Quantity 		= partInventoryList.get(i).getQuantity();
					
					if(partInventoryList.get(i).getUnitprice() != null && partInventoryList.get(i).getUnitprice() > 0) {
						eaccost 		= partInventoryList.get(i).getUnitprice();						
						Part.setUnitprice(partInventoryList.get(i).getUnitprice());
					}
					
					if(partInventoryList.get(i).getDiscount() != null && partInventoryList.get(i).getDiscount() > 0) {
						dis_Ca 			= partInventoryList.get(i).getDiscount();
						Part.setDiscount(partInventoryList.get(i).getDiscount());
					}
					
					if(partInventoryList.get(i).getTax() != null && partInventoryList.get(i).getTax() > 0) {
						tax_Ca 			= partInventoryList.get(i).getTax();
						Part.setTax(partInventoryList.get(i).getTax());
					}

					discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
					TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
					Part.setTotal(TotalCost);

					if (partInventoryList.get(i).getMake() != null) {
						Part.setMake(partInventoryList.get(i).getMake());
					}
					Part.setInventory_all_id(Inventoryall.getInventory_all_id());
					Part.setInventory_location_id(InventoryLocation.getInventory_location_id());
					Part.setSerialNoAddedForParts(0);

					inventoryService.save_Inventory(Part);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/editPartInventory", method = RequestMethod.GET)
	public ModelAndView editPartInventory(@RequestParam("Id") final Long partInvoice_id,
			final PartInvoiceDto partInvoiceDto, final HttpServletRequest request) {
		Map<String, Object> 			model 						= new HashMap<String, Object>();
		HashMap<String, Object> 		configuration				= null;
		CustomUserDetails				userDetails					= null;
		ValueObject	 					valueObject 				= null;
		boolean							showSubLocation				= false;
		String							mainLocationIds				= "";
		try {
			valueObject		= new ValueObject();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			showSubLocation = (boolean)configuration.getOrDefault(PartInventoryConfigurationConstants.SHOW_SUB_LOCATION, false);
			mainLocationIds = configuration.getOrDefault(PartInventoryConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
			valueObject.put("partInvoice_id", partInvoice_id);
			
			model			=	inventoryService.editPartInventory(valueObject);
			model.put("configuration", configuration);
			model.put("showSubLocation", showSubLocation);
			model.put("mainLocationIds", mainLocationIds);
			model.put("subLocation", partLocationsBL.prepareListofPartLocation(partLocationsService.getPartLocationListByType(PartLocationsType.PART_LOCATION_TYPE_SUB_LOCATION)));
		} catch (Exception e) {
			LOGGER.error("Add Part Inventory Page:", e);
		}finally {
			
		}
		return new ModelAndView("Edit_PartInvoiceInventory", model);
	}

	/** This controller request in Add Part Inventory list */
	@RequestMapping(value = "/updatePartInventoryInvoice", method = RequestMethod.POST)
	public ModelAndView updatePartInventoryInvoice(@ModelAttribute("command") PartInvoiceDto PartInvoiceDto,
			@RequestParam("anyPartNumberAsign") final boolean anyPartNumberAsign,
			@RequestParam("input-file-preview") MultipartFile file,
			@ModelAttribute BankPaymentDto bankPaymentDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			ValueObject	 valueObject = new ValueObject();
			valueObject.put("PartInvoiceDto", PartInvoiceDto);
			valueObject.put("inventory_id", PartInvoiceDto.getInventory_id());
			valueObject.put("partid", PartInvoiceDto.getPartid());
			valueObject.put("make", PartInvoiceDto.getMake());
			valueObject.put("disTaxTypeId", PartInvoiceDto.getFinalDiscountTaxTypId());
			valueObject.put("quantity", PartInvoiceDto.getQuantity_many());
			valueObject.put("unitprice", PartInvoiceDto.getUnitprice_many());
			valueObject.put("discount", PartInvoiceDto.getDiscount_many());
			valueObject.put("tax", PartInvoiceDto.getTax_many());
			valueObject.put("anyPartNumberAsign", anyPartNumberAsign);
			valueObject.put("bankPaymentDto", bankPaymentDto);
			
			model	=	partInvoiceService.updatePartInventoryInvoice(valueObject,file);
			
		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
		}
		return new ModelAndView("redirect:/NewInventory/1.in", model);
	}
	
	@RequestMapping(value = "/deletePartInvoiceInventory", method = RequestMethod.GET)
	public ModelAndView deletePartInvoiceInventory(@RequestParam("Id") final Long inventory_id, Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model	=	null;
		try {
			
			ValueObject	 valueObject = new ValueObject();
			valueObject.put("inventory_id", inventory_id);
			
			model	=	partInvoiceService.deletePartInvoiceInventory(valueObject);

		} catch (Exception e) {
			LOGGER.error("Add Part Inventory Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
		}
		return new ModelAndView("redirect:/showInvoice?Id=" +(Long) model.get("InvoiceID"), model);

	}
	
	@RequestMapping(value = "/deletePartInvoice", method = RequestMethod.GET)
	public ModelAndView deletePartInvoice(@RequestParam("Id") final Long InvoiceID, Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			ValueObject	 valueObject = new ValueObject();
			valueObject.put("InvoiceID", InvoiceID);
			
			model	=	partInvoiceService.deletePartInvoice(valueObject);
			
		} catch (Exception e) {
			LOGGER.error("Add Part Inventory Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInvoiceList/1.in?danger=true", model);
		}
		return new ModelAndView("redirect:/NewInvoiceList/1.in", model);

	}
	
	/** This controller request in Add Part Inventory list */
	@RequestMapping(value = "/updateAddMorePartInventoryInvoice", method = RequestMethod.POST)
	public ModelAndView updateAddMorePartInventoryInvoice(@ModelAttribute("command") InventoryDto InventoryDto,
			@RequestParam("partid_many") final String[] partid, 
			@RequestParam("make_many") final String[] make,
			@RequestParam("quantity_many") final String[] quantity,
			@RequestParam("unitprice_many") final String[] unitprice,
			@RequestParam("discount_many") final String[] discount, 
			@RequestParam("tax_many") final String[] tax,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			ValueObject	 valueObject = new ValueObject();
			valueObject.put("InventoryDto", InventoryDto);
			valueObject.put("partid", partid);
			valueObject.put("make", make);
			valueObject.put("quantity", quantity);
			valueObject.put("unitprice", unitprice);
			valueObject.put("discount", discount);
			valueObject.put("tax", tax);
			
			model	=	inventoryService.updateAddMorePartInventoryInvoice(valueObject);
			
		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInvoiceList/1.in?danger=true");
		}
		return new ModelAndView("redirect:/showInvoice?Id=" +InventoryDto.getPartInvoiceId() , model);
	}
	
	@RequestMapping("/download/PartDocument/{part_document_id}")
	public String downloadReminder(@PathVariable("part_document_id") Long part_document_id,
			HttpServletResponse response) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (part_document_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				//ServiceEntriesDocument file = ServiceEntriesService.getServiceEntriesDocument(serviceEntries_id);
				org.fleetopgroup.persistence.document.PartDocument	file =		partDocumentService.getPartDocument(part_document_id, userDetails.getCompany_id());
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
			System.err.println("Exception : "+e);
		}
		return null;
	}
	
	@RequestMapping(value = "/getPartForRefreshment", method = RequestMethod.POST)
	public void getPartForRefreshment(Map<String, Object> map, @RequestParam("term") final String term,
			@RequestParam("locationId") final  Integer locationId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<InventoryLocationDto> inventory = new ArrayList<InventoryLocationDto>();
		List<InventoryLocationDto> location = inventoryService.getPartForRefreshment(term, locationId, userDetails.getCompany_id());
		
		if (location != null && !location.isEmpty()) {
			for (InventoryLocationDto add : location) {
				InventoryLocationDto wadd = new InventoryLocationDto();

				wadd.setInventory_location_id(add.getInventory_location_id());
				wadd.setLocation(add.getLocation());
				wadd.setLocationId(add.getLocationId());
				wadd.setPartname(add.getPartname());
				wadd.setPartnumber(add.getPartnumber());
				wadd.setLocation_quantity(add.getLocation_quantity());
				wadd.setPartManufacturer(add.getPartManufacturer());

				inventory.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(inventory));
	}

	@RequestMapping(value = "/getPartNameForRefreshment", method = RequestMethod.POST)
	public void getPartNameForRefreshment(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<InventoryLocationDto> inventory = new ArrayList<InventoryLocationDto>();
		List<InventoryLocationDto> location = inventoryService.getPartForRefreshment(term, userDetails.getCompany_id());
		
		if (location != null && !location.isEmpty()) {
			for (InventoryLocationDto add : location) {
				InventoryLocationDto wadd = new InventoryLocationDto();
					
					wadd.setPartid(add.getPartid());
					wadd.setPartname(add.getPartname());
					wadd.setPartnumber(add.getPartnumber());

				inventory.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(inventory));
	}
	
	@PostMapping(value="/getsubLocationPartDetails", produces="application/json")
	public void getsubLocationPartDetails(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletResponse response) throws Exception {
		ValueObject 				valueOutObject 					= null;
		try {
			HashMap<Integer, InventoryDto>  inventoryHM				= new HashMap<>();
			List<InventoryDto> 				inventoryList 			= new ArrayList<>();
			valueOutObject		= new ValueObject(allRequestParams);
			inventoryList 		= inventoryService.getsubLocationPartDetails(valueOutObject);
			if(inventoryList != null && !inventoryList.isEmpty()) {
				for(InventoryDto dto : inventoryList) {
					if(inventoryHM.containsKey(dto.getSubLocationId())){
						dto.setQuantity(dto.getQuantity()+inventoryHM.get(dto.getSubLocationId()).getQuantity());
						inventoryHM.put(dto.getSubLocationId(), dto);
					}else {
						dto.setQuantity(dto.getQuantity());
						inventoryHM.put(dto.getSubLocationId(), dto);
					}
				}
			}
			 valueOutObject.put("subLocationPartDetails", inventoryHM.values());
			 Gson gson = new Gson();
			response.getWriter().write(gson.toJson(valueOutObject.getHtData()));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@PostMapping(path ="/getInventoryListBypartId",produces="application/json")
	public void getInventoryListBypartId(@RequestParam HashMap<Object, Object> map,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		ValueObject object = new ValueObject(map);
			String query =" AND I.partid ="+object.getLong("partId",0)+" AND I.locationId="+object.getLong("locationId",0)+" ";		
			List<InventoryDto> inventoryList= inventoryService.Report_Purchase_Inventory(query, userDetails.getCompany_id());
			List<InventoryDto>finalInventoryList = new ArrayList<>();
			if (inventoryList != null && !inventoryList.isEmpty()) {
			for (InventoryDto add : inventoryList) {
				InventoryDto wadd = new InventoryDto();
				wadd.setInventory_id(add.getInventory_id());
				wadd.setPartid(add.getPartid());
				wadd.setQuantity(add.getQuantity());
				wadd.setUnitprice(add.getUnitprice());
				wadd.setTax(wadd.getTax());
				wadd.setDiscount(wadd.getDiscount());
				wadd.setPartname(add.getPartname());
				wadd.setPartnumber(add.getPartnumber());
				wadd.setCategory(add.getCategory());
				wadd.setMake(add.getMake());

				finalInventoryList.add(wadd);
			}
		}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(finalInventoryList));
	}
	
}

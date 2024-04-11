/**
 * 
 */
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.InventoryTyreConfiguration;
import org.fleetopgroup.constant.MasterDocumentsConstant;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceTypeContant;
import org.fleetopgroup.constant.TyreInventoryConfiguration;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.InventoryTyreInvoiceBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dao.PurchaseOrdersRepository;
import org.fleetopgroup.persistence.document.MasterDocuments;
import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreAmountDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.InventoryTyreTransferDto;
import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.model.InventorySequenceCounter;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreAmount;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.fleetopgroup.persistence.model.InventoryTyreLifeHistory;
import org.fleetopgroup.persistence.model.InventoryTyreTransfer;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.model.VehicleTyreModelSubType;
import org.fleetopgroup.persistence.model.VehicleTyreModelType;
import org.fleetopgroup.persistence.model.VehicleTyreSize;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventorySequenceService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IMasterDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.persistence.serviceImpl.ITyreDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreSizeService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * @author fleetop
 *
 */
@Controller
public class InventoryTyreController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired IVendorService vendorService;
	VendorBL VenBL = new VendorBL();

	@Autowired IInventoryTyreService iventoryTyreService;
	@Autowired ITyreDocumentService tyreDocumentService;
	@Autowired IInventorySequenceService	inventorySequenceService;

	@Autowired IPartLocationsService PartLocationsService;
	
	@Autowired IPartLocationPermissionService partLocationPermissionService;
	
	@Autowired IVehicleService	vehicleService;

	@Autowired 			MongoOperations						mongoOperations;

	@Autowired IUserProfileService userProfileService;

	@Autowired IMasterDocumentService masterDocumentService;
	@Autowired IVehicleTyreModelTypeService vehicleTyreModelTypeService;
	@Autowired IVehicleTyreModelSubTypeService vehicleTyreModelSubTypeService;
	@Autowired IVehicleTyreSizeService vehicleTyreSizeService;
	@Autowired IPartLocationsService partLocationsService;
	@Autowired ICompanyConfigurationService		companyConfigurationService;
	@Autowired private IPendingVendorPaymentService	pendingVendorPaymentService;
	@Autowired private  IBankPaymentService					bankPaymentService; 
	@Autowired private  ICashPaymentService 				cashPaymentService;
	@Autowired private  IPurchaseOrdersService             iPurchaseOrdersRepository;
	
	InventoryTyreInvoiceBL ITBL = new InventoryTyreInvoiceBL();
	PartLocationsBL PLBL = new PartLocationsBL();

	SimpleDateFormat SQLdateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat SQLdate 		= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	InventoryBL INVBL = new InventoryBL();

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/** This controller request in open Tyre list */
	@RequestMapping(value = "/TyreInventory/{pageNumber}", method = RequestMethod.GET)
	public String TyreInventory(@PathVariable Integer pageNumber, Model model) throws Exception {
		Page<InventoryTyreInvoice> 		page						= null;
		List<InventoryTyreInvoiceDto> 	pageList					= null;
		CustomUserDetails				userDetails					= null;
		long							availableCount				= 0;
		long							serviceCount				= 0;
		long							scrapedCount				= 0;
		HashMap<String,Object>			configuration				= null;
		boolean							showSubLocation				= false;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			page 			= iventoryTyreService.getDeployment_Page_TyreInvoice(pageNumber, userDetails.getCompany_id());
			showSubLocation	= (boolean)configuration.getOrDefault(InventoryTyreConfiguration.SHOW_SUB_LOCATION, false);
			int current 	= page.getNumber() + 1;
			int begin 		= Math.max(1, current - 5);
			int end 		= Math.min(begin + 10, page.getTotalPages());
			
			availableCount	= iventoryTyreService.getInventoryTyreCountByStatus(userDetails,InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE);
			serviceCount	= iventoryTyreService.getInventoryTyreCountByStatus(userDetails,InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE);
			scrapedCount	= iventoryTyreService.getInventoryTyreCountByStatus(userDetails,InventoryTyreDto.TYRE_ASSIGN_STATUS_SCRAPED);
			
			model.addAttribute("configuration", configuration);
			model.addAttribute("availableCount", availableCount);
			model.addAttribute("serviceCount", serviceCount);
			model.addAttribute("scrapedCount", scrapedCount);
			
			
			model.addAttribute("showSubLocation", showSubLocation);
			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TyreCount", page.getTotalElements());

			pageList 	= iventoryTyreService.find_list_InventoryTyreInvoice(pageNumber, userDetails.getCompany_id());

			model.addAttribute("TyreInvoice", ITBL.get_InventoryTyreInvoice_list(pageList));

			model.addAttribute("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			model.addAttribute("CountName", "All");

		} catch (NullPointerException e) {
			LOGGER.error("Tyre Invoice Page :", e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("Tyre Page:", e);
		}
		return "Open_TyreInventory";
	}

	/** This controller request in open Tyre list */
	@RequestMapping(value = "/TyreInventoryNew/{pageNumber}", method = RequestMethod.GET)
	public String getRunbookPage(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails	userDetails	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<InventoryTyre> page = iventoryTyreService.getDeploymentLog(pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("TyreQuentity", page.getTotalElements());

			List<InventoryTyreDto> pageList = iventoryTyreService.find_List_InventoryTyre(pageNumber, userDetails.getCompany_id());

			model.addAttribute("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			model.addAttribute("location", "TYRE");

			model.addAttribute("Tyre", pageList);
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Tyre Page:", e);
		}
		return "New_TyreInventory";
	}

	/** This controller request in open Tyre list */
	@RequestMapping(value = "/locationTyreInventory/{pageNumber}", method = RequestMethod.GET)
	public String locationTyreInventory(@PathVariable Integer pageNumber, @RequestParam("loc") final Integer Location,
			Model model, final HttpServletRequest request) throws Exception {
		CustomUserDetails	userDetails	= null;
		long							availableCount						= 0;
		long							serviceCount						= 0;
		long							scrapedCount						= 0;
		HashMap<String,Object>			configuration						= null;
		boolean							showSubLocation						= false;
		String							mainLocationIds						= "";
		String[]						mainLocationIdsArr					= null;
		boolean							showSubLocationForMainLocation		= false;
		
		
		try {
			userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(InventoryTyreConfiguration.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(InventoryTyreConfiguration.MAIN_LOCATION_IDS, "")+"";
			mainLocationIdsArr 	= mainLocationIds.split(",");
			if(showSubLocation == true){
				for(int i=0; i<mainLocationIdsArr.length; i++) {
					if(Location == Integer.parseInt(mainLocationIdsArr[i])) {
						showSubLocationForMainLocation = true;
					}
				}
			}
			Page<InventoryTyre> page = iventoryTyreService.getDeploymentLog_Location(Location, pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			availableCount	= iventoryTyreService.getLocationWiseInventoryTyreCount(userDetails,InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE,Location);
			serviceCount	= iventoryTyreService.getLocationWiseInventoryTyreCount(userDetails,InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE,Location);
			scrapedCount	= iventoryTyreService.getLocationWiseInventoryTyreCount(userDetails,InventoryTyreDto.TYRE_ASSIGN_STATUS_SCRAPED,Location);
			
			model.addAttribute("availableCount", availableCount);
			model.addAttribute("serviceCount", serviceCount);
			model.addAttribute("scrapedCount", scrapedCount);
			
			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("showSubLocationForMainLocation", showSubLocationForMainLocation);
			

			model.addAttribute("TyreQuentity", page.getTotalElements());

			List<InventoryTyreDto> pageList = iventoryTyreService.find_List_InventoryTyre_Location(Location, pageNumber, userDetails.getCompany_id());

			model.addAttribute("Tyre", pageList);

			model.addAttribute("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			model.addAttribute("location", PartLocationsService.getPartLocations(Location).getPartlocation_name());
			model.addAttribute("locationId", Location);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("Tyre Page:", e);
		}finally {
			userDetails = null;
		}
		return "NewLocation_TyreInventory";
	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/addTyreInventory", method = RequestMethod.GET)
	public ModelAndView addTyreInventory(@ModelAttribute("command") InventoryTyreInvoice TyreInvoice,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String,Object>		configuration		= null;
		CustomUserDetails			userDetails			= null;
		boolean						showSubLocation		= false;
		String						mainLocationIds		= "";
			
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(InventoryTyreConfiguration.SHOW_SUB_LOCATION, false);
			mainLocationIds 	=  configuration.getOrDefault(InventoryTyreConfiguration.MAIN_LOCATION_IDS, "")+"";
			
			if(showSubLocation) {
				model.put("showSubLocation",showSubLocation);
				model.put("mainLocationIds",mainLocationIds);
			}
			
			model.put("companyId", userDetails.getCompany_id());
			model.put("configuration", configuration);
			model.put("accessToken", Utility.getUniqueToken(request));
		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
		}finally {
			userDetails		= null;
			configuration	= null;
		}
		return new ModelAndView("Add_TyreInventory", model);
	}

	/** This Controller save the saveTyreInventory Method */
	@RequestMapping(value = "/saveTyreInventory", method = RequestMethod.POST)
	public ModelAndView saveTyreInventory(@ModelAttribute("command") InventoryTyreInvoiceDto TyreInvoiceDto,
			@RequestParam("TYRE_MANUFACTURER_ID") final String[] Tyre_Manufacturer,

			@RequestParam("TYRE_MODEL_ID") final String[] Tyre_Model, 
			@RequestParam("TYRE_SIZE_ID") final String[] Tyre_Size,
			@RequestParam("finalDiscountTaxTypId") final String[] disTaxTypeId,
			@RequestParam("quantity_many") final String[] quantity,
			@RequestParam("unitprice_many") final String[] unitprice,
			@RequestParam("discount_many") final String[] discount, 
			@RequestParam("tax_many") final String[] tax,
			@RequestParam("input-file-preview") MultipartFile file,
			@ModelAttribute BankPaymentDto bankPaymentDto,
			final HttpServletRequest request) {
		Map<String, Object> 			model 				= new HashMap<String, Object>();
		InventorySequenceCounter		sequenceCounter		= null;
		CustomUserDetails				userDetails			= null;
		Long 							TyreInvoice_id;
		InventoryTyreInvoice 			TyreInvoice			= null;
		Double 							Quantity			= 0.0;
		Double 							eaccost 			= 0.0;
		Double 							dis_Ca 				= 0.0;
		Double 							tax_Ca 				= 0.0;
		Double 							discountCost 		= 0.0;
		Double 							TotalCost 			= 0.0;
		HashMap<String, Object>			configuration		= null;
		InventoryTyreInvoice			validateTyreInvoice	= null;
		PurchaseOrders                  purchaseOrders      = null;
			
		try {
			
			System.err.println("*****" + TyreInvoiceDto.getINVOICE_NUMBER());
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			
			
			if((boolean) configuration.getOrDefault("validateInvoiceNumber", false)
					&& TyreInvoiceDto.getINVOICE_NUMBER() != null && !TyreInvoiceDto.getINVOICE_NUMBER().isEmpty()) {
				validateTyreInvoice = iventoryTyreService.getInventoryTyreInvoiceByInvoiceNumber(TyreInvoiceDto.getINVOICE_NUMBER(), userDetails.getCompany_id());
				
				if(validateTyreInvoice != null) {
					model.put("duplicateInvoiceNumber", true);
					return new ModelAndView("redirect:/addTyreInventory",model);
				}
				
			}
			TyreInvoice 	= prepare_TyreInvoice(TyreInvoiceDto);
			sequenceCounter	= inventorySequenceService.findNextInventoryNumber(userDetails.getCompany_id(), SequenceTypeContant.SEQUENCE_TYPE_TYRE_INVENTORY);
			TyreInvoice.setITYRE_NUMBER(sequenceCounter.getNextVal());
			
			if (!file.isEmpty()) {

				TyreInvoice.setTyre_document(true);

			} else {

				TyreInvoice.setTyre_document(false);
			}

			int countSAVE = 0;
			

			if(TyreInvoice.getPO_NUMBER()!= "" && TyreInvoice.getPO_NUMBER() != null && !TyreInvoice.getPO_NUMBER().isEmpty()) {
				purchaseOrders = iPurchaseOrdersRepository.getPurchaseOrderId(Long.parseLong(TyreInvoice.getPO_NUMBER()) , userDetails.getCompany_id());

				if(purchaseOrders != null) {
					TyreInvoice.setPO_NUMBER(purchaseOrders.getPurchaseorder_id()+""); 
				}else {
					TyreInvoice.setPO_NUMBER(""); 
				}
			}
			iventoryTyreService.save_Tyre_Inventory_Invoice(TyreInvoice);
			
			if(TyreInvoice.getPAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && TyreInvoice.getINVOICE_AMOUNT() > 0) {
				 PendingVendorPayment	payment	=PendingVendorPaymentBL.createPendingVendorPaymentDTOForTI(TyreInvoice);
				pendingVendorPaymentService.savePendingVendorPayment(payment);
			}
			if (bankPaymentDto.isAllowBankPaymentDetails()) {
				 if(TyreInvoice.getPAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CASH || PaymentTypeConstant.getPaymentTypeIdList().contains(TyreInvoice.getPAYMENT_TYPE_ID())) {
					ValueObject bankPaymentValueObject=new ValueObject();
					bankPaymentValueObject.put("currentPaymentTypeId", TyreInvoice.getPAYMENT_TYPE_ID());
					bankPaymentValueObject.put("userId", userDetails.getId());
					bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
					bankPaymentValueObject.put("moduleId",TyreInvoice.getITYRE_ID());
					bankPaymentValueObject.put("moduleNo", TyreInvoice.getITYRE_NUMBER());
					bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.TYRE_INVENTORY);
					bankPaymentValueObject.put("amount",TyreInvoice.getINVOICE_AMOUNT());
					bankPaymentValueObject.put("remark", "Type Inventory Created TI-"+TyreInvoice.getITYRE_NUMBER());
					//bankPaymentValueObject.put("paidDate", TyreInvoice.getINVOICE_DATE());
					if(TyreInvoice.getPAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
						cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
					else
						bankPaymentService.addBankPaymentDetailsAndSendToIv(bankPaymentDto,bankPaymentValueObject);
					}
			}
			
			if (!file.isEmpty()) {
				org.fleetopgroup.persistence.document.TyreDocument tyreDoc = new org.fleetopgroup.persistence.document.TyreDocument();
				tyreDoc.setTyre_id(TyreInvoice.getITYRE_ID());
				try {

					byte[] bytes = file.getBytes();
					tyreDoc.setTyre_filename(file.getOriginalFilename());
					tyreDoc.setTyre_content(bytes);
					tyreDoc.setTyre_contentType(file.getContentType());

					tyreDoc.setMarkForDelete(false);

					tyreDoc.setCreatedById(userDetails.getId());
					tyreDoc.setLastModifiedById(userDetails.getId());

					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					tyreDoc.setCreated(toDate);
					tyreDoc.setLastupdated(toDate);
					tyreDoc.setCompanyId(userDetails.getCompany_id());
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Note: Save FuelDocument Details
				tyreDocumentService.add_Tyre_To_TyreDocument(tyreDoc);

				// Note: FuelDocument to Save id to Fuel save
				iventoryTyreService.Update_TyreDocument_ID_to_Tyre(tyreDoc.get_id(), true, TyreInvoice.getITYRE_ID(),
						userDetails.getCompany_id());
			}

			if (Tyre_Manufacturer != null) {
				for (int i = 0; i < Tyre_Manufacturer.length; i++) {
					InventoryTyreAmount TyreAmount = new InventoryTyreAmount();
					TyreAmount.setTYRE_MANUFACTURER_ID(Integer.parseInt(Tyre_Manufacturer[i]+""));
					TyreAmount.setTYRE_MODEL_ID(Integer.parseInt(Tyre_Model[i]+""));
					TyreAmount.setTYRE_SIZE_ID(Integer.parseInt(Tyre_Size[i]+""));
					TyreAmount.setCOMPANY_ID(userDetails.getCompany_id());

					Quantity 		= Double.parseDouble(quantity[i]);
					eaccost  		= Double.parseDouble(unitprice[i]);
					dis_Ca 	 		= Double.parseDouble(discount[i]);
					tax_Ca 	 		= Double.parseDouble(tax[i]);

					TyreAmount.setTYRE_QUANTITY(Quantity);
					TyreAmount.setTYRE_ASSIGN_NO(Integer.parseInt(quantity[i]));
					TyreAmount.setUNIT_COST(eaccost);
					TyreAmount.setDISCOUNT(dis_Ca);
					TyreAmount.setTAX(tax_Ca);
					TyreAmount.setDiscountTaxTypeId(Short.parseShort(disTaxTypeId[i]));
					
					if(TyreAmount.getDiscountTaxTypeId() == 1) {
						discountCost 	= (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
						TotalCost 		= round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
					} else {
						discountCost 	= ((Quantity * eaccost) - dis_Ca);
						TotalCost 		= round((discountCost + tax_Ca), 2);
					}
					
					TyreAmount.setTOTAL_AMOUNT(TotalCost);
					TyreAmount.setWAREHOUSE_LOCATION_ID(TyreInvoiceDto.getWAREHOUSE_LOCATION_ID());
					TyreAmount.setInventoryTyreInvoice(TyreInvoice);
					
					countSAVE += 1;

					iventoryTyreService.add_Tyre_Inventory_Amount(TyreAmount);
					model.put("successTyre", countSAVE);
					model.put("saveTyre", true);
				}
			}

			TyreInvoice_id = TyreInvoice.getITYRE_ID();
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
		}finally {
			userDetails = null;
		}
		return new ModelAndView("redirect:/showTyreInventory?Id=" + TyreInvoice_id, model);
	}

	/** This Controller save the saveTyreInventory Method */
	@RequestMapping(value = "/saveTyreInfo", method = RequestMethod.POST)
	public ModelAndView saveTyreInfo(@ModelAttribute("command") InventoryTyreInvoiceDto TyreInvoiceDto,
			@RequestParam("TyreSerialno") final String[] TyreSerialno,
			@RequestParam("ITYRE_INVOICE_ID") final Long ITYRE_INVOICE_ID,
			@RequestParam("ITYRE_AMOUNT_ID") final Long ITYRE_AMOUNT_ID, Model modelAdd,
			@RequestParam("TYRE_USEAGE") final String[] tyreUses,
			@RequestParam("TYRE_RETREAD_COUNT") final String[] TYRE_RETREAD_COUNT,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails		= null;
		HashMap<String, Object>		configuration	= null;
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			InventoryTyreAmountDto TyreAmount = iventoryTyreService.Get_AMount_ID_InventoryTyreAmount(ITYRE_AMOUNT_ID, userDetails.getCompany_id());

			if (TyreSerialno != null) {
				Integer countSAVE = 0;
				Integer countDuplicate = 0;
				for (int i = 0; i < TyreSerialno.length; i++) {
					InventoryTyre Tyre = prepare_TyreAmount_TO_Tyre(TyreAmount);

					Tyre.setCOMPANY_ID(userDetails.getCompany_id());
					Tyre.setTYRE_NUMBER(TyreSerialno[i]);
					Tyre.setITYRE_AMOUNT_ID(ITYRE_AMOUNT_ID);
					Tyre.setITYRE_INVOICE_ID(ITYRE_INVOICE_ID);
					Tyre.setTYRE_ASSIGN_STATUS_ID(InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE);
					if((boolean) configuration.get(InventoryTyreConfiguration.SHOW_TYRE_USES_COLUMN))
						Tyre.setTYRE_USEAGE(Integer.parseInt(tyreUses[i]+""));
					if((boolean) configuration.get(InventoryTyreConfiguration.SHOW_TYRE_STATUS_AT_ADD) && TyreInvoiceDto.getSTATUS_OF_TYRE() == InventoryTyreInvoiceDto.STATUS_OF_TYRE_RETREAD) {
						Tyre.setTYRE_RETREAD_COUNT(Integer.parseInt(TYRE_RETREAD_COUNT[i]+""));
					}else {
						Tyre.setTYRE_RETREAD_COUNT(0);
					}
					Tyre.setDismountedTyreStatusId((short)0);
					Tyre.setSubLocationId(TyreInvoiceDto.getSubLocationId());	
					InventoryTyre validate = iventoryTyreService.Validate_InventoryTyre(TyreSerialno[i], userDetails.getCompany_id());
					if (validate == null && Tyre.getTYRE_NUMBER() != null && Tyre.getTYRE_NUMBER() != "") {
						iventoryTyreService.save_Inventory_TYRE(Tyre);
						countSAVE += 1;
					} else {
						countDuplicate += 1;
					}
				}

				iventoryTyreService.Update_Inventory_Amount_Complete_Tyre_Number(countSAVE, ITYRE_AMOUNT_ID, userDetails.getCompany_id());
				iventoryTyreService.updateTyreInvoice(ITYRE_INVOICE_ID, true);
				modelAdd.addAttribute("successTyre", "" + countSAVE);
				modelAdd.addAttribute("DuplicateTyre", "" + countDuplicate);
				//model.put("successTyre", countSAVE);
				//model.put("DuplicateTyre", countDuplicate);
				model.put("saveTyre", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
		}finally {
			userDetails		= null;
		}

		return new ModelAndView("redirect:/showTyreInventory?Id=" + ITYRE_INVOICE_ID, model);
	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/showTyreInventory", method = RequestMethod.GET)
	public ModelAndView showTyreInventory(@RequestParam("Id") final Long TyreInvoice_id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails		= null;
		HashMap<String, Object> 	configuration	= null; 
		boolean						showSubLocation = false;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			showSubLocation = (boolean)configuration.getOrDefault(InventoryTyreConfiguration.SHOW_SUB_LOCATION, false);
			List<InventoryTyreAmountDto> tadto= iventoryTyreService.Get_List_InventoryTyreAmount(TyreInvoice_id, userDetails.getCompany_id());

			Double total =0.0;
			if(tadto != null) {
				for(InventoryTyreAmountDto dts : tadto) {
					total+=dts.getTOTAL_AMOUNT();
				}
			}
			model.put("totalAmount",Utility.round(total, 2));
			model.put("TyreInvoice", ITBL.Show_inventory_Tyre_invoice(iventoryTyreService.Get_list_InventoryTyreInvoiceDetails(TyreInvoice_id, userDetails.getCompany_id())));
			model.put("TyreAmount", iventoryTyreService.Get_List_InventoryTyreAmount(TyreInvoice_id, userDetails.getCompany_id()));
			model.put("Tyre", iventoryTyreService.Get__List_InventoryTyre(TyreInvoice_id, userDetails.getCompany_id()));
			model.put("configuration", configuration);
			model.put("showSubLocation", showSubLocation);

		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
		}finally {
			userDetails	= null;
		}
		return new ModelAndView("Show_TyreInventory", model);
	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/PrintTyreInvoice", method = RequestMethod.GET)
	public ModelAndView PrintTyreInvoice(@RequestParam("Id") final Long TyreInvoice_id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String,Object>	 configuration=null;
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration=companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TYRE_INVENTORY_CONFIG );

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in username

			
			List<InventoryTyreAmountDto> itad=iventoryTyreService.Get_List_InventoryTyreAmount(TyreInvoice_id, userDetails.getCompany_id());
			
			Double total=0.0;
			 if(itad!=null) {
				 for(InventoryTyreAmountDto ita:itad) {
					 total+=ita.getTOTAL_AMOUNT();					 
					 
				 }
			 }
			 model.put("totalAmount",toFixedTwo.format(total));
			 model.put("company", userProfileService.findUserProfileByUser_email(name));
			 model.put("configuration",configuration);

			model.put("TyreInvoice", iventoryTyreService.Get_list_InventoryTyreInvoiceDetails(TyreInvoice_id, userDetails.getCompany_id()));
			model.put("TyreAmount", iventoryTyreService.Get_List_InventoryTyreAmount(TyreInvoice_id, userDetails.getCompany_id()));
			model.put("Tyre", iventoryTyreService.Get__List_InventoryTyre(TyreInvoice_id, userDetails.getCompany_id()));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Tyre Inventory Page:", e);
		}finally {
			userDetails	= null;
		}
		return new ModelAndView("Print_TyreInventory", model);
	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/showTyreInfo", method = RequestMethod.GET)
	public ModelAndView showTyreInfo(@RequestParam("Id") final Long Tyre_id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean						showSubLocation		= false;
		HashMap<String, Object> 	configuration	= null; 
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(InventoryTyreConfiguration.SHOW_SUB_LOCATION, false);
			model.put("Tyre", ITBL.Show_InventoryTyre_Details(iventoryTyreService.Get_TYRE_ID_InventoryTyre(Tyre_id, userDetails.getCompany_id())));
			model.put("showSubLocation", showSubLocation);

		} catch (Exception e) {
			System.err.println("Excxeption "+e);
			LOGGER.error("Add Tyre Inventory Page:", e);
		}
		return new ModelAndView("Show_TyreInfo", model);
	}

	/** This controller request in Add TyreInventoryHistorylist */
	@RequestMapping(value = "/TyreInventoryHistory", method = RequestMethod.GET)
	public ModelAndView TyreInventoryHistory(@RequestParam("ID") final Long Tyre_id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("Tyre", ITBL.Show_InventoryTyre_Details(iventoryTyreService.Get_TYRE_ID_InventoryTyre(Tyre_id, userDetails.getCompany_id())));

			model.put("TyreHistory",iventoryTyreService.find_List_InventoryTyreHistory(Tyre_id, userDetails.getCompany_id()));

		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Add Tyre Inventory Page:", e);
		}
		return new ModelAndView("Show_TyreInfoHistory", model);
	}

	/** This controller request in Add TyreInventoryHistorylist */
	@RequestMapping(value = "/TyreInventoryLife", method = RequestMethod.GET)
	public ModelAndView TyreInventoryLife(@RequestParam("ID") final Long Tyre_id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			InventoryTyreDto TyreShow = iventoryTyreService.Get_TYRE_ID_InventoryTyre(Tyre_id, userDetails.getCompany_id());
			model.put("Tyre", ITBL.Show_InventoryTyre_Details(TyreShow));
			List<InventoryTyreLifeHistory> History = iventoryTyreService.find_list_InventoryTyreLifeHistory(Tyre_id, userDetails.getCompany_id());

			model.put("TyreLife", ITBL.prepareListof_InventoryTyreLifeHistory(History, TyreShow));

			model.put("TotalCost", ITBL.prepareTOTAL_COST_InventoryTyreLifeHistory(History, TyreShow));

		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
		}
		return new ModelAndView("Show_TyreLifeCycle", model);
	}

	@RequestMapping(value = "/searchTyreInventory", method = RequestMethod.POST)
	public ModelAndView searchTyreInventory(@RequestParam("Search") final String Search,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("search", Search);
			List<InventoryTyreDto> pageList = iventoryTyreService.Search_List_InventoryTyre(Search, userDetails.getCompany_id());

			model.put("Tyre", pageList);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in", model);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Tyre Page:", e);
		}
		return new ModelAndView("Search_TyreInventory", model);
	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/updateTyreSerialNo", method = RequestMethod.POST)
	public ModelAndView updateTyreSerialNo(@RequestParam("Id") final Long Tyre_id,
			@RequestParam("InvoiceID") final Long ITYRE_INVOICE_ID,
			@RequestParam("TyreSerialNo") final String TyreSerialNo, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Date currentDateUpdate = new Date();
			Timestamp LASTUPDATE_Date = new java.sql.Timestamp(currentDateUpdate.getTime());

			InventoryTyre validate = iventoryTyreService.Validate_InventoryTyre(TyreSerialNo, userDetails.getCompany_id());

			if (validate != null) {
				if (!(validate.getTYRE_NUMBER().equalsIgnoreCase(TyreSerialNo))
						&& (validate.getTYRE_ID().equals(Tyre_id))) {
					iventoryTyreService.update_Tyre_Inventory(TyreSerialNo, userDetails.getId(), LASTUPDATE_Date, Tyre_id, userDetails.getCompany_id());
					model.put("UpdateSuccess", true);
				} else {
					model.put("alreadyTyre", true);
				}

			} else {
				iventoryTyreService.update_Tyre_Inventory(TyreSerialNo, userDetails.getId(), LASTUPDATE_Date, Tyre_id, userDetails.getCompany_id());
				model.put("UpdateSuccess", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("alreadyTyre", true);
			return new ModelAndView("redirect:/showTyreInventory?Id=" + ITYRE_INVOICE_ID + "", model);
		}

		return new ModelAndView("redirect:/showTyreInventory?Id=" + ITYRE_INVOICE_ID + "", model);
	}

	@RequestMapping(value = "/searchInvoiceInventory", method = RequestMethod.POST)
	public ModelAndView searchInvoiceInventory(@RequestParam("Search") final String Search,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("TyreSearch", Search);
			List<InventoryTyreInvoiceDto> pageList = iventoryTyreService.Search_List_InventoryTyreInvoice(Search, userDetails.getCompany_id());

			model.put("TyreInvoice", pageList);
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in", model);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Tyre Page:", e);
		}
		return new ModelAndView("Search_TyreInventory", model);
	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/editTyreInventory", method = RequestMethod.GET)
	public ModelAndView editTyreInventory(@RequestParam("Id") final Long TyreInvoice_id,
			final InventoryTyreInvoiceDto TyreInvoiceDto, final HttpServletRequest request) {
		Map<String, Object> 			model 				= new HashMap<String, Object>();
		CustomUserDetails				userDetails			= null;
		InventoryTyreInvoiceDto			invoiceDto			= null;
		HashMap<String, Object> 		configuration		= null;
		boolean							showSubLocation		= false;
		String							mainLocationIds		= "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			invoiceDto			= iventoryTyreService.Get_list_InventoryTyreInvoiceDetails(TyreInvoice_id, userDetails.getCompany_id());
			showSubLocation	 	= (boolean)configuration.getOrDefault(InventoryTyreConfiguration.SHOW_SUB_LOCATION, false);
			mainLocationIds 	=  configuration.getOrDefault(InventoryTyreConfiguration.MAIN_LOCATION_IDS, "")+"";
			
			model.put("configuration", configuration);
			model.put("bpmsn", objectMapper.writeValueAsString(iventoryTyreService.getInventoryAmountDetailsToEdit(TyreInvoice_id, userDetails.getCompany_id())));
			model.put("TyreAmount", iventoryTyreService.getInventoryAmountDetailsToEdit(TyreInvoice_id, userDetails.getCompany_id()));
			model.put("TyreInvoice", ITBL.Edit_TyreInventory(invoiceDto));
			model.put("anyTyreNumberAsign",invoiceDto.isAnyTyreNumberAsign());
			model.put("editAllTyreInventory", configuration.get(TyreInventoryConfiguration.EDIT_ALL_TYRE_INVENTORY));
			model.put("showSubLocation", showSubLocation);
			model.put("mainLocationIds", mainLocationIds);
		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
		}finally {
			userDetails		= null;
			invoiceDto		= null;
			configuration	= null;
		}
		return new ModelAndView("Edit_TyreInventory", model);
	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/updateTyreInventory", method = RequestMethod.POST)
	public ModelAndView updateTyreInventory(@ModelAttribute("command") InventoryTyreInvoiceDto TyreInvoiceDto,
			@RequestParam("TYRE_MANUFACTURER_ID") final String[] Tyre_Manufacturer,
			@RequestParam("TYRE_AMOUNT_ID") final String[] TYRE_AMOUNT_ID,
			@RequestParam("TYRE_MODEL_ID") final String[] Tyre_Model,
			@RequestParam("TYRE_SIZE_ID") final String[] Tyre_Size,
			@RequestParam("quantity_many") final String[] quantity,
			@RequestParam("unitprice_many") final String[] unitprice,
			@RequestParam("discount_many") final String[] discount,
			@RequestParam("tax_many") final String[] tax,
			@RequestParam("anyTyreNumberAsign") final boolean anyTyreNumberAsign,
			 @RequestParam("input-file-preview") MultipartFile file,
			 @ModelAttribute BankPaymentDto bankPaymentDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		InventoryTyreInvoice			validateTyreInvoice	= null;
		HashMap<String, Object> 		configuration		= null;
		try {
			
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			if (TyreInvoiceDto.getITYRE_ID() != null) {
				InventoryTyreInvoice TyreInvoice = prepare_UpdateTyreInvoice(TyreInvoiceDto);
				if (!file.isEmpty()) {
					TyreInvoice.setTyre_document(true);
				}
				InventoryTyreInvoice getTyreInvoice = iventoryTyreService.Get_list_InventoryTyreInvoice(TyreInvoiceDto.getITYRE_ID(),userDetails.getCompany_id());
				
				if((boolean) configuration.getOrDefault("validateInvoiceNumber", false)) {
					validateTyreInvoice = iventoryTyreService.getInventoryTyreInvoiceByInvoiceNumber(TyreInvoiceDto.getINVOICE_NUMBER(), userDetails.getCompany_id());
					
					if(!getTyreInvoice.getINVOICE_NUMBER().equalsIgnoreCase(TyreInvoiceDto.getINVOICE_NUMBER()) && validateTyreInvoice != null) {
						model.put("duplicateInvoiceNumber", true);
						return new ModelAndView("redirect:/editTyreInventory?Id=" + TyreInvoiceDto.getITYRE_ID(), model);
					}
					
				}
				
				
				short oldPaymentTypeId = getTyreInvoice.getPAYMENT_TYPE_ID();
				boolean isInvoiceChanged = false;
				
				if(!TyreInvoice.equals(getTyreInvoice)) {
					isInvoiceChanged = true;
				}
				
				if (!file.isEmpty()) {
					org.fleetopgroup.persistence.document.TyreDocument tyreDoc = new org.fleetopgroup.persistence.document.TyreDocument();
					tyreDoc.setTyre_id(TyreInvoice.getITYRE_ID());
					try {
							byte[] bytes = file.getBytes();
							tyreDoc.setTyre_filename(file.getOriginalFilename());
							tyreDoc.setTyre_content(bytes);
							tyreDoc.setTyre_contentType(file.getContentType());

							tyreDoc.setMarkForDelete(false);

							tyreDoc.setCreatedById(userDetails.getId());
							tyreDoc.setLastModifiedById(userDetails.getId());

							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

							tyreDoc.setCreated(toDate);
							tyreDoc.setLastupdated(toDate);
							tyreDoc.setCompanyId(userDetails.getCompany_id());
						} catch (IOException e) {
							e.printStackTrace();
						}
					
					org.fleetopgroup.persistence.document.TyreDocument doc = tyreDocumentService.getTyreDocByTyreInvoiceId(TyreInvoice.getITYRE_ID(), userDetails.getCompany_id());
						// Note: Save FuelDocument Details
					
						if (doc != null) {
						
							tyreDoc.set_id(doc.get_id());
						mongoOperations.save(tyreDoc);
						
						// Note: FuelDocument to Save id to Fuel save
						iventoryTyreService.Update_TyreDocument_ID_to_Tyre(tyreDoc.get_id(), true, TyreInvoice.getITYRE_ID(),
								userDetails.getCompany_id());
						
					}else {
						tyreDocumentService.add_Tyre_To_TyreDocument(tyreDoc);

						iventoryTyreService.Update_TyreDocument_ID_to_Tyre(tyreDoc.get_id(), true, TyreInvoice.getITYRE_ID(),
								userDetails.getCompany_id());
					}
					}
				if (Tyre_Manufacturer != null && !anyTyreNumberAsign) {
					for (int i = 0; i < Tyre_Manufacturer.length; i++) {
						InventoryTyreAmount TyreAmount = new InventoryTyreAmount();

						TyreAmount.setITYRE_AMD_ID(Long.parseLong(TYRE_AMOUNT_ID[i]+""));
						TyreAmount.setTYRE_MANUFACTURER_ID(Integer.parseInt(Tyre_Manufacturer[i]+""));
						TyreAmount.setTYRE_MODEL_ID(Integer.parseInt(Tyre_Model[i]+""));
						TyreAmount.setTYRE_SIZE_ID(Integer.parseInt(Tyre_Size[i]+""));
						TyreAmount.setCOMPANY_ID(userDetails.getCompany_id());

						Double Quantity = Double.parseDouble(quantity[i]);
						Double eaccost = Double.parseDouble(unitprice[i]);
						Double dis_Ca = Double.parseDouble(discount[i]);
						Double tax_Ca = Double.parseDouble(tax[i]);

						TyreAmount.setTYRE_QUANTITY(Quantity);

						TyreAmount.setTYRE_ASSIGN_NO(Integer.parseInt(quantity[i]));

						TyreAmount.setUNIT_COST(eaccost);
						TyreAmount.setDISCOUNT(dis_Ca);
						TyreAmount.setTAX(tax_Ca);

						Double discountCost = 0.0;
						Double TotalCost = 0.0;

						discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
						TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
						TyreAmount.setTOTAL_AMOUNT(TotalCost);

						TyreAmount.setWAREHOUSE_LOCATION_ID(TyreInvoiceDto.getWAREHOUSE_LOCATION_ID());

						TyreAmount.setInventoryTyreInvoice(TyreInvoice);

						iventoryTyreService.add_Tyre_Inventory_Amount(TyreAmount);
					}
				}
				
				iventoryTyreService.update_Tyre_Inventory_Invoice(TyreInvoice);
				if(getTyreInvoice != null ) {
					if(getTyreInvoice.getSubLocationId() != TyreInvoiceDto.getSubLocationId() ) {
						iventoryTyreService.updateSublocationInInventoryTyre(getTyreInvoice.getITYRE_ID() ,TyreInvoiceDto.getSubLocationId(),userDetails.getCompany_id() );
					}
				}
				PendingVendorPayment	payment		= null;
				if(oldPaymentTypeId != TyreInvoice.getPAYMENT_TYPE_ID() && TyreInvoice.getPAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForTI(TyreInvoice);
					pendingVendorPaymentService.savePendingVendorPayment(payment);
				}else if(oldPaymentTypeId == TyreInvoice.getPAYMENT_TYPE_ID() && TyreInvoice.getPAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && isInvoiceChanged) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForTI(TyreInvoice);
					pendingVendorPaymentService.updatePendingVendorPayment(payment);
				}else if(oldPaymentTypeId != TyreInvoice.getPAYMENT_TYPE_ID() && TyreInvoice.getPAYMENT_TYPE_ID() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					pendingVendorPaymentService.deletePendingVendorPayment(TyreInvoice.getITYRE_ID(), PendingPaymentType.PAYMENT_TYPE_TYRE_INVOICE);
				}
				
				if(bankPaymentDto.isAllowBankPaymentDetails()) {
				ValueObject bankPaymentValueObject=new ValueObject();
				bankPaymentValueObject.put("oldPaymentTypeId", oldPaymentTypeId);
				bankPaymentValueObject.put("currentPaymentTypeId", TyreInvoice.getPAYMENT_TYPE_ID());
				bankPaymentValueObject.put("userId", userDetails.getId());
				bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId", TyreInvoice.getITYRE_ID());
				bankPaymentValueObject.put("moduleNo", TyreInvoice.getITYRE_NUMBER());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.TYRE_INVENTORY);
				bankPaymentValueObject.put("amount",TyreInvoice.getINVOICE_AMOUNT());
				
				Vendor	vendor	= vendorService.getVendor(TyreInvoice.getVENDOR_ID());
				
				bankPaymentValueObject.put("remark","Tyre Invoice Edit On TI-"+TyreInvoice.getITYRE_NUMBER()+" Vendor : "+vendor.getVendorName()+"  ");
				
				bankPaymentService.updatePaymentDetails(bankPaymentValueObject, bankPaymentDto);
				}
				model.put("UpdateSuccess", true);
			} else {
				return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
			}

		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
		}
		return new ModelAndView("redirect:/showTyreInventory?Id=" + TyreInvoiceDto.getITYRE_ID(), model);
	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/deleteTyreInventory", method = RequestMethod.GET)
	public ModelAndView deleteTyreInventory(@RequestParam("Id") final Long Tyre_id, Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		Long InvoiceID;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			InventoryTyre TyreValidate = iventoryTyreService.Get_TYRE_ID_InventoryTyre(Tyre_id);

			InvoiceID = TyreValidate.getITYRE_INVOICE_ID();
			if (TyreValidate.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE) {

				iventoryTyreService.delete_Tyre_Inventory(Tyre_id, userDetails.getCompany_id());

				iventoryTyreService.update_Add_InventoryAmount_to_tyreRemove(1, TyreValidate.getITYRE_AMOUNT_ID(), userDetails.getCompany_id());
				model.put("deleteSuccess", true);
			} else {
				model.put("AssignVehicle", true);
				modelAdd.addAttribute("vehicleName", vehicleService.getVehicel1(TyreValidate.getVEHICLE_ID()).getVehicle_registration());
			}

		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
		}
		return new ModelAndView("redirect:/showTyreInventory?Id=" + InvoiceID, model);

	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/deleteTyreAmount", method = RequestMethod.GET)
	public ModelAndView deleteTyreAmountInventory(@RequestParam("Id") final Long Tyre_Amount_Id, Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		Long InvoiceID;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			InventoryTyreAmountDto TyreAmount = iventoryTyreService.Get_AMount_ID_InventoryTyreAmount(Tyre_Amount_Id, userDetails.getCompany_id());
			InvoiceID = TyreAmount.getITYRE_ID();
			
			List<InventoryTyre> tyreValidate = iventoryTyreService.Validate_Amount_InventoryTyre(Tyre_Amount_Id, userDetails.getCompany_id());
			if (tyreValidate != null && !tyreValidate.isEmpty()) {
				model.put("deleteFrist", true);
			} else {

				iventoryTyreService.update_Subtrack_InventoryAmount_to_InvoiceAmount(TyreAmount.getTOTAL_AMOUNT(),
						InvoiceID, userDetails.getCompany_id());
				iventoryTyreService.delete_Tyre_Inventory_Amount(Tyre_Amount_Id, userDetails.getCompany_id());
				
				pendingVendorPaymentService.updatePendingVendorPaymentAmt(InvoiceID, PendingPaymentType.PAYMENT_TYPE_TYRE_INVOICE, (TyreAmount.getInvoiceAmount()-TyreAmount.getTOTAL_AMOUNT()));
				InventoryTyreInvoice getTyreInvoice = iventoryTyreService.Get_list_InventoryTyreInvoice(InvoiceID,userDetails.getCompany_id());
				HashMap<String, Object>  companyConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				if((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)) {
					ValueObject bankPaymentValueObject=new ValueObject();
					bankPaymentValueObject.put("oldPaymentTypeId",getTyreInvoice.getPAYMENT_TYPE_ID());
					bankPaymentValueObject.put("bankPaymentTypeId", getTyreInvoice.getPAYMENT_TYPE_ID());
					bankPaymentValueObject.put("currentPaymentTypeId", getTyreInvoice.getPAYMENT_TYPE_ID());
					bankPaymentValueObject.put("userId",userDetails.getId());
					bankPaymentValueObject.put("companyId",userDetails.getCompany_id());
					bankPaymentValueObject.put("moduleId",getTyreInvoice.getITYRE_ID());
					bankPaymentValueObject.put("moduleNo", getTyreInvoice.getITYRE_NUMBER());
					bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.TYRE_INVENTORY);
					bankPaymentValueObject.put("amount",getTyreInvoice.getINVOICE_AMOUNT());
					Vendor	vendor	=  vendorService.getVendor(getTyreInvoice.getVENDOR_ID());
					bankPaymentValueObject.put("remark", "Tyre Delete On Tyre Invoice TI-"+getTyreInvoice.getITYRE_NUMBER()+" vendor : "+vendor.getVendorName());
					bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
					}
				model.put("deleteSuccess", true);
			}
		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
		}
		return new ModelAndView("redirect:/showTyreInventory?Id=" + InvoiceID, model);

	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/deleteTyreInvoice", method = RequestMethod.GET)
	public ModelAndView deleteTyreInvoiceInventory(@RequestParam("Id") final Long InvoiceID, Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<InventoryTyreAmountDto> TyreAmountValidate = iventoryTyreService.Get_List_InventoryTyreAmount(InvoiceID, userDetails.getCompany_id());

			if (TyreAmountValidate != null && !TyreAmountValidate.isEmpty()) {

				model.put("deleteFrist", true);
			} else {
				iventoryTyreService.delete_Tyre_Inventory_Invoice(InvoiceID, userDetails.getCompany_id());
				pendingVendorPaymentService.deletePendingVendorPayment(InvoiceID, PendingPaymentType.PAYMENT_TYPE_TYRE_INVOICE);
				bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(InvoiceID,ModuleConstant.TYRE_INVENTORY, userDetails.getCompany_id(),userDetails.getId(),false);
				model.put("deleteSuccess", true);

			}

		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true", model);
		}
		return new ModelAndView("redirect:/TyreInventory/1.in", model);

	}

	@RequestMapping(value = "/showAvailableTyre", method = RequestMethod.GET)
	public ModelAndView showAvailableTyre(@RequestParam("size") final Integer size, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("search", size);
			List<InventoryTyreDto> pageList = iventoryTyreService.Show_PurchaseOrderTyre_to_List_InventoryTyre(size, userDetails.getCompany_id());

			model.put("Tyre", pageList);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in", model);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Tyre Page:", e);
		}
		return new ModelAndView("Search_TyreInventory", model);
	}

	/************************************************************************/
	/************ TRANSFER TYRE HISTORY ************************************/
	/**********************************************************************/

	// Transfer Inventory move one location to another Location
	@RequestMapping(value = "/transferTyreInventory", method = RequestMethod.GET)
	public ModelAndView transferTyreInventory(@RequestParam("Id") final Long Tyre_id, Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			InventoryTyreDto transfer = iventoryTyreService.Get_TYRE_ID_InventoryTyre(Tyre_id, userDetails.getCompany_id());
			if (transfer.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE) {
				// get the Inventory id to value
				model.put("Tyre", ITBL.Show_InventoryTyre_Details(transfer));
				model.put("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			} else {
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/showTyreInfo.in?Id=" + Tyre_id + "", model);
			}

		} catch (Exception e) {
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("addTransferTyreInventory", model);
	}
	
	@RequestMapping(value = "/multiTransferTyreInventory", method = RequestMethod.GET)
	public ModelAndView multiTransferTyreInventory(Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				model.put("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

		} catch (Exception e) {
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("addMultiTransferTyreInventory", model);
	}

	@RequestMapping(value = "/saveTITYREOPEN", method = RequestMethod.POST)
	public ModelAndView savetransferTyreInventoryOpen(
			@ModelAttribute("command") InventoryTyreTransferDto InventoryTransfer, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// get Transfer Inventory To all Details Create New Transfer
			// Location Quantity
			
			InventoryTyreDto FROM_transferInventory = iventoryTyreService
					.Get_TYRE_ID_InventoryTyre(InventoryTransfer.getTYRE_ID(), userDetails.getCompany_id());
			if(FROM_transferInventory != null) {
				
				//String Transfer_byEmail = userDetails.getEmail();
				//UserProfileDto userName = userProfileService.get_UserProfile_WORKORDER_LOCATION_Details(Transfer_byEmail);
				
				
				List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService
						.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

				// check user branch location and workOrder location if same
				//if (INVBL.isFilled_GET_Permission(FROM_transferInventory.getLocationId(), PartLocPermission)) {

				// check user branch location and workOrder location if same
				if (INVBL.isFilled_GET_Permission(InventoryTransfer.getTRA_FROM_LOCATION_ID(), PartLocPermission)
						&& FROM_transferInventory.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE) {
					
					if(InventoryTransfer.getTRANSFER_RECEIVEDBY_ID() == null)	{

						model.put("NoAuthen", true);
						return new ModelAndView("redirect:/showTyreInfo.in?Id=" + InventoryTransfer.getTYRE_ID() + "",
								model);
					
					}
					if (userDetails.getId() != InventoryTransfer.getTRANSFER_RECEIVEDBY_ID()) {
						
						InventoryTyreTransfer createTransferIn = ITBL
								.prepareCreateInventory_To_TransferInventory(FROM_transferInventory, InventoryTransfer);
						try {
							
							createTransferIn.setTRANSFER_BY_ID(userDetails.getId());
							createTransferIn.setTRANSFER_RECEIVEDBYID(InventoryTransfer.getTRANSFER_RECEIVEDBY_ID());
							createTransferIn.setTRANSFER_STATUS_ID(InventoryTyreTransferDto.TRANSFER_STATUS_TRANSFER);
							
							iventoryTyreService.add_Inventory_Tyre_Transfer(createTransferIn);
						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
						}
					} else {
						model.put("NoAuthen", true);
						return new ModelAndView("redirect:/showTyreInfo.in?Id=" + InventoryTransfer.getTYRE_ID() + "",
								model);
					}
					
				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/showTyreInfo.in?Id=" + InventoryTransfer.getTYRE_ID() + "", model);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
		}finally {
			userDetails	= null;
		}
		return new ModelAndView("redirect:/showTyreInfo.in?Id=" + InventoryTransfer.getTYRE_ID() + "", model);
	}
	
	@RequestMapping(value = "/saveMultiTransferTyreInventoryOpen", method = RequestMethod.POST)
	public ModelAndView saveMultiTransferTyreInventoryOpen(
			@ModelAttribute("command") InventoryTyreTransferDto InventoryTransfer,
			@RequestParam("TYRE_MUITPLE") final String TYRE_MUITPLE,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails		= null;
		String[]					tyreIdArr		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			tyreIdArr	= TYRE_MUITPLE.split(",");
			if(tyreIdArr != null) {
			for(int i = 0 ; i< tyreIdArr.length ; i++) {
					InventoryTyreDto FROM_transferInventory = iventoryTyreService
				.Get_TYRE_ID_InventoryTyre(Long.parseLong(tyreIdArr[i]+""), userDetails.getCompany_id());
			if(FROM_transferInventory != null) {
			
			List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));


			// check user branch location and workOrder location if same
			if (INVBL.isFilled_GET_Permission(InventoryTransfer.getTRA_FROM_LOCATION_ID(), PartLocPermission)
					&& FROM_transferInventory.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE) {
					
				if (userDetails.getId() != InventoryTransfer.getTRANSFER_RECEIVEDBY_ID()) {
					
					InventoryTyreTransfer createTransferIn = ITBL
							.prepareCreateInventory_To_TransferInventory(FROM_transferInventory, InventoryTransfer);
					try {
						
						createTransferIn.setTRANSFER_BY_ID(userDetails.getId());
						createTransferIn.setTRANSFER_RECEIVEDBYID(InventoryTransfer.getTRANSFER_RECEIVEDBY_ID());
						createTransferIn.setTRANSFER_STATUS_ID(InventoryTyreTransferDto.TRANSFER_STATUS_TRANSFER);
						
						iventoryTyreService.add_Inventory_Tyre_Transfer(createTransferIn);
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
					}
				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/TyreInventory/1.in");
				}
				
			} else {
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/TyreInventory/1.in?NoAuthen=true");
			}
		}
				}

			}
			// get Transfer Inventory To all Details Create New Transfer
			// Location Quantity
			
			

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
		}finally {
			userDetails	= null;
		}
		return new ModelAndView("redirect:/multiTransferTyreInventory.in?saveTransferInventory=true");
	}

	/* save the vehicle Status */
	@RequestMapping(value = "/savetransferTyreInventory", method = RequestMethod.POST)
	public ModelAndView savetransferTyreInventory(@RequestParam("TYRE_ID") final Long TYRE_ID,
			@RequestParam("ITTID") final Long ITTID,
			@RequestParam("TRANSFER_RECEIVEDBYEMAIL") final String transfer_receivedbyEmail,
			@RequestParam("TRANSFER_RECEIVEDREASON") final String Transfer_receivedReason,
			@RequestParam("TRANSFER_RECEIVEDBYID") final Long transfer_receivedbyId,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			InventoryTyreTransfer InventoryTransfer = iventoryTyreService.Find_InventoryTyreTranfer_Details_ID(ITTID, userDetails.getCompany_id());
			
			if(InventoryTransfer != null) {
				
				// check user branch location and workOrder location if same
				if (userDetails.getId() == InventoryTransfer.getTRANSFER_RECEIVEDBYID()) {
					
					try {
						
						iventoryTyreService.Update_Transfer_Location_InventoryTyre(InventoryTransfer.getTRA_TO_LOCATION_ID(),
								InventoryTransfer.getTYRE_ID(), userDetails.getCompany_id());
						// get Current days
						java.util.Date currentDate = new java.util.Date();
						Timestamp Transfer_receiveddate = new java.sql.Timestamp(currentDate.getTime());
						
						iventoryTyreService.Update_InventoryTyreTransfer_ReceivedBy_Details(Transfer_receiveddate,
								Transfer_receivedReason, userDetails.getId(), Transfer_receiveddate,
								InventoryTransfer.getITTID(), userDetails.getCompany_id());
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
					}
					
				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/showTyreInfo.in?Id=" + InventoryTransfer.getTYRE_ID() + "", model);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
		}finally {
			userDetails = null;
		}
		return new ModelAndView("redirect:/showTyreInfo.in?Id=" + TYRE_ID + "", model);
	}
	
	
	@RequestMapping(value = "/saveMultiTransferTyreInventory", method = RequestMethod.POST)
	public ModelAndView saveMultiTransferTyreInventory(@RequestParam("TYRE_ID") final Long TYRE_ID,
			@RequestParam("ITTID") final Long ITTID,
			@RequestParam("TRANSFER_RECEIVEDBYEMAIL") final String transfer_receivedbyEmail,
			@RequestParam("TRANSFER_RECEIVEDREASON") final String Transfer_receivedReason,
			@RequestParam("TRANSFER_RECEIVEDBYID") final Long transfer_receivedbyId,
			final HttpServletRequest request) {
		CustomUserDetails	userDetails		= null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			InventoryTyreTransfer InventoryTransfer = iventoryTyreService.Find_InventoryTyreTranfer_Details_ID(ITTID, userDetails.getCompany_id());
			
			if(InventoryTransfer != null) {
				
				// check user branch location and workOrder location if same
				if (userDetails.getId() == InventoryTransfer.getTRANSFER_RECEIVEDBYID()) {
					
					try {
						
						iventoryTyreService.Update_Transfer_Location_InventoryTyre(InventoryTransfer.getTRA_TO_LOCATION_ID(),
								InventoryTransfer.getTYRE_ID(), userDetails.getCompany_id());
						// get Current days
						java.util.Date currentDate = new java.util.Date();
						Timestamp Transfer_receiveddate = new java.sql.Timestamp(currentDate.getTime());
						
						iventoryTyreService.Update_InventoryTyreTransfer_ReceivedBy_Details(Transfer_receiveddate,
								Transfer_receivedReason, userDetails.getId(), Transfer_receiveddate,
								InventoryTransfer.getITTID(), userDetails.getCompany_id());
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/receiveMultipleTyreTransfered.in?danger=true");
					}
					
				} else {
					return new ModelAndView("redirect:/receiveMultipleTyreTransfered.in?danger=true");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
		}finally {
			userDetails = null;
		}
		return new ModelAndView("redirect:/receiveMultipleTyreTransfered.in?received=true");
	}

	@RequestMapping(value = "/transferTyreInventoryHistory", method = RequestMethod.GET)
	public ModelAndView transferTyreInventoryHistory(@RequestParam("Id") final Long Tyre_id, Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// get the Inventory id to value
			model.put("Tyre", ITBL.Show_InventoryTyre_Details(iventoryTyreService.Get_TYRE_ID_InventoryTyre(Tyre_id, userDetails.getCompany_id())));

			List<InventoryTyreTransferDto> show_Location = iventoryTyreService
					.Get_Tyre_id_To_InventoryTyreTransfer(Tyre_id, userDetails.getCompany_id());
			model.put("InventoryTransfer", ITBL.prepareListofDto_Inventory_Tyre_Transfer(show_Location));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("showTransferTyreInventory", model);
	}

	private InventoryTyreInvoice prepare_TyreInvoice(InventoryTyreInvoiceDto TyreInvoiceDto) {

		InventoryTyreInvoice Tyre = new InventoryTyreInvoice();
		double balanceAmt=0;

		//Tyre.setWAREHOUSE_LOCATION(TyreInvoiceDto.getWAREHOUSE_LOCATION());
		Tyre.setWAREHOUSE_LOCATION_ID(TyreInvoiceDto.getWAREHOUSE_LOCATION_ID());
		Tyre.setPO_NUMBER(TyreInvoiceDto.getPO_NUMBER());
		Tyre.setINVOICE_NUMBER(TyreInvoiceDto.getINVOICE_NUMBER());
		Tyre.setINVOICE_AMOUNT(TyreInvoiceDto.getINVOICE_AMOUNT());
		//use for vendor Creadit Payment
		balanceAmt=TyreInvoiceDto.getINVOICE_AMOUNT();
		Tyre.setBalanceAmount((balanceAmt));
		
		Tyre.setSTATUS_OF_TYRE(TyreInvoiceDto.getSTATUS_OF_TYRE());
		
		if (TyreInvoiceDto.getINVOICE_DATE() != null) {
			try {
				java.util.Date 	date 		= SQLdateFormat.parse(TyreInvoiceDto.getINVOICE_DATE());
				java.sql.Date 	start_date 	= new java.sql.Date(date.getTime());
				Tyre.setINVOICE_DATE(start_date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (TyreInvoiceDto.getVENDOR_ID() != 0) {
			Tyre.setVENDOR_ID(TyreInvoiceDto.getVENDOR_ID());
		} else {
			Tyre.setVENDOR_ID(0);
		}

		// payment Type is credit set vendor pay mode is NOTPAID
		Tyre.setPAYMENT_TYPE_ID(TyreInvoiceDto.getPAYMENT_TYPE_ID());
		
		Tyre.setPAYMENT_NUMBER(TyreInvoiceDto.getPAYMENT_NUMBER());
		
		if (Tyre.getPAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
			Tyre.setVENDOR_PAYMODE_STATUS_ID(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
		} else {
			Tyre.setVENDOR_PAYMODE_STATUS_ID(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID);
			try {
				java.util.Date currentDate3 = new java.util.Date();
				java.util.Date dateTo3 		= dateFormatTime.parse(ft.format(currentDate3));
				Tyre.setVENDOR_PAYMODE_DATE(dateTo3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Tyre.setDESCRIPTION(TyreInvoiceDto.getDESCRIPTION());

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Tyre.setCOMPANY_ID(auth.getCompany_id());
		Tyre.setCREATEDBYID(auth.getId());
		Tyre.setLASTMODIFIEDBYID(auth.getId());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Tyre.setCREATED_DATE(toDate);
		Tyre.setLASTUPDATED_DATE(toDate);

		Tyre.setMarkForDelete(false);
		Tyre.setTallyCompanyId(TyreInvoiceDto.getTallyCompanyId());
		Tyre.setSubLocationId(TyreInvoiceDto.getSubLocationId());
		return Tyre;
	}

	private InventoryTyreInvoice prepare_UpdateTyreInvoice(InventoryTyreInvoiceDto TyreInvoiceDto) {

		InventoryTyreInvoice Tyre = new InventoryTyreInvoice();
		Tyre.setITYRE_ID(TyreInvoiceDto.getITYRE_ID());
		Tyre.setITYRE_NUMBER(TyreInvoiceDto.getITYRE_NUMBER());
		
		//Tyre.setWAREHOUSE_LOCATION(TyreInvoiceDto.getWAREHOUSE_LOCATION());
		Tyre.setWAREHOUSE_LOCATION_ID(TyreInvoiceDto.getWAREHOUSE_LOCATION_ID());

		Tyre.setPO_NUMBER(TyreInvoiceDto.getPO_NUMBER());
		Tyre.setINVOICE_NUMBER(TyreInvoiceDto.getINVOICE_NUMBER());
		Tyre.setINVOICE_AMOUNT(TyreInvoiceDto.getINVOICE_AMOUNT());
		Tyre.setBalanceAmount(TyreInvoiceDto.getINVOICE_AMOUNT());
		if (TyreInvoiceDto.getINVOICE_DATE() != null) {
			try {
				java.util.Date date = SQLdate.parse(TyreInvoiceDto.getINVOICE_DATE());
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				Tyre.setINVOICE_DATE(start_date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (TyreInvoiceDto.getVENDOR_ID() != 0) {
			Tyre.setVENDOR_ID(TyreInvoiceDto.getVENDOR_ID());
			// get Vendor id to Vendor name
			//Vendor vendorname = vendorService.getVendor_Details_Fuel_entries(TyreInvoiceDto.getVENDOR_ID());
			// show Vehicle name
			//Tyre.setVENDOR_NAME(vendorname.getVendorName());
			//Tyre.setVENDOR_LOCATION(vendorname.getVendorLocation());
		} else {
			Tyre.setVENDOR_ID(0);
		//	Tyre.setVENDOR_NAME(TyreInvoiceDto.getVENDOR_NAME());
			//Tyre.setVENDOR_LOCATION(TyreInvoiceDto.getVENDOR_LOCATION());
		}

		Tyre.setDESCRIPTION(TyreInvoiceDto.getDESCRIPTION());

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//Tyre.setCREATEDBY(userDetails.getEmail());
		//Tyre.setLASTMODIFIEDBY(userDetails.getEmail());
		Tyre.setCOMPANY_ID(userDetails.getCompany_id());
		Tyre.setCREATEDBYID(userDetails.getId());
		Tyre.setLASTMODIFIEDBYID(userDetails.getId());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		Tyre.setCREATED_DATE(toDate);
		Tyre.setLASTUPDATED_DATE(toDate);
		Tyre.setTallyCompanyId(TyreInvoiceDto.getTallyCompanyId());
		Tyre.setPO_NUMBER(TyreInvoiceDto.getPO_NUMBER());
		Tyre.setSubLocationId(TyreInvoiceDto.getSubLocationId());
		Tyre.setPAYMENT_TYPE_ID(TyreInvoiceDto.getPAYMENT_TYPE_ID());
		if(TyreInvoiceDto.getPAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
			Tyre.setVENDOR_PAYMODE_STATUS_ID(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
		}else {
			Tyre.setVENDOR_PAYMODE_STATUS_ID(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
		}

		return Tyre;
	}

	private InventoryTyre prepare_TyreAmount_TO_Tyre(InventoryTyreAmountDto TyreAmount) {

		InventoryTyre Tyre = new InventoryTyre();
		if (TyreAmount != null) {

			Double Tyreamount = 0.0;

			if (TyreAmount.getTYRE_QUANTITY() != 0.0 && TyreAmount.getTYRE_QUANTITY() != null) {
				Tyreamount = TyreAmount.getTOTAL_AMOUNT() / TyreAmount.getTYRE_QUANTITY();
			}
			Tyre.setTYRE_AMOUNT(round(Tyreamount, 0));
			Tyre.setTYRE_MANUFACTURER_ID(TyreAmount.getTYRE_MANUFACTURER_ID());
			Tyre.setTYRE_MODEL_ID(TyreAmount.getTYRE_MODEL_ID());
			Tyre.setTYRE_SIZE_ID(TyreAmount.getTYRE_SIZE_ID());
			//Tyre.setTYRE_SIZE(TyreAmount.getTYRE_SIZE());
			Tyre.setTYRE_TREAD(TyreAmount.getTYRE_TREAD());
			Tyre.setWAREHOUSE_LOCATION_ID(TyreAmount.getWAREHOUSE_LOCATION_ID());

			Tyre.setTYRE_RETREAD_COUNT(0);
			Tyre.setTYRE_USEAGE(0);

			try {
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp CREATED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());
				Tyre.setCREATED_DATE(CREATED_DATE);
				Tyre.setLASTUPDATED_DATE(CREATED_DATE);

				/* Tyre.setTYRE_ASSIGN_DATE(toDate); */
				Tyre.setTYRE_PURCHASE_DATE(CREATED_DATE);
			} catch (Exception e) {

				e.printStackTrace();
			}

			/** who Create this Issues get email id to user profile details */
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			
		//	Tyre.setCREATEDBY(userDetails.getEmail());
			//Tyre.setLASTMODIFIEDBY(userDetails.getEmail());
			Tyre.setCOMPANY_ID(userDetails.getCompany_id());
			Tyre.setCREATEDBYID(userDetails.getId());
			Tyre.setLASTMODIFIEDBYID(userDetails.getId());

			Tyre.setMarkForDelete(false);
		}
		return Tyre;
	}

	/** load search vendor part and service vendor name */
	@RequestMapping(value = "/getTyreMountList", method = RequestMethod.POST)
	public void getTyreMountList(Map<String, Object> map, @RequestParam("term") final String term,
			@RequestParam("Size") final Integer Size, final HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails	userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<InventoryTyre> addresses = new ArrayList<InventoryTyre>();
		List<InventoryTyre> Tyre = null;
		HashMap<String, Object> 		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
		
		if(!(boolean) configuration.get(VehicleConfigurationContant.ASIGN_ANY_SIZE_TYRE)) {
			Tyre = iventoryTyreService.Tyre_Mount_InVehicle(term, Size, userDetails.getCompany_id());
		}else {
			Tyre = iventoryTyreService.Tyre_Mount_InVehicleAllSize(term, userDetails.getCompany_id());
		}
		if (Tyre != null && !Tyre.isEmpty()) {
			for (InventoryTyre add : Tyre) {
				InventoryTyre wadd = new InventoryTyre();
				if (add != null) {
					wadd.setTYRE_ID(add.getTYRE_ID());
					wadd.setTYRE_NUMBER(add.getTYRE_NUMBER());

					addresses.add(wadd);
				}
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(addresses));
	}

	/** load search vendor part and service vendor name */
	@RequestMapping(value = "/getTyreVendorSearchList", method = RequestMethod.POST)
	public void getTyreVendorSearchList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Vendor> addresses = new ArrayList<Vendor>();
			List<Vendor> vendor = vendorService.SearchOnly_TYRE_VendorName(term, userDetails.getCompany_id());
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

			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}
	}

	/** Tyre id To Show Details in Vehicle */
	@RequestMapping(value = "/getTyreAssignDetails", method = RequestMethod.GET)
	public void getTyreAssignDetails(Map<String, Object> map, @RequestParam("position") final Long TYRE_ID,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		InventoryTyreDto wadd = new InventoryTyreDto();
		InventoryTyreDto Tyre = iventoryTyreService.Get_TYRE_ID_InventoryTyre(TYRE_ID, userDetails.getCompany_id());
		if (Tyre != null) {
			wadd.setTYRE_ID(Tyre.getTYRE_ID());
			wadd.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());
			wadd.setTYRE_SIZE(Tyre.getTYRE_SIZE());

			wadd.setTYRE_MANUFACTURER(Tyre.getTYRE_MANUFACTURER());
			wadd.setTYRE_MODEL(Tyre.getTYRE_MODEL());
			wadd.setTYRE_SIZE(Tyre.getTYRE_SIZE());
			wadd.setTYRE_SIZE_ID(Tyre.getTYRE_SIZE_ID());
			wadd.setRemark(Tyre.getRemark());
			if (Tyre.getTYRE_AMOUNT() != null) {
				wadd.setTYRE_AMOUNT(round(Tyre.getTYRE_AMOUNT(), 2));
			} else {
				wadd.setTYRE_AMOUNT(0.0);
			}
			if (Tyre.getTYRE_USEAGE() != null) {
				wadd.setTYRE_USEAGE(Tyre.getTYRE_USEAGE());
			} else {
				wadd.setTYRE_USEAGE(0);
			}

			wadd.setOPEN_ODOMETER(Tyre.getOPEN_ODOMETER());

			wadd.setTYRE_ASSIGN_DATE(dateFormatTime.format(Tyre.getTYRE_ASSIGN_DATE_ON()));
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(wadd));
	}

	/** This below Search Tyre Size Type Search Details */
	@RequestMapping(value = "/getTyreID", method = RequestMethod.POST)
	public void getTyreID(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<InventoryTyre> Tyre = new ArrayList<InventoryTyre>();
		List<InventoryTyre> Size = iventoryTyreService.Search_Only_InventoryTyre_ID(term, userDetails.getCompany_id());
		if (Size != null && !Size.isEmpty()) {
			for (InventoryTyre add : Size) {

				InventoryTyre wadd = new InventoryTyre();

				// wadd.setTS_ID(add.getTS_ID());
				wadd.setTYRE_ID(add.getTYRE_ID());
				wadd.setTYRE_NUMBER(add.getTYRE_NUMBER());
				wadd.setTYRE_IN_NUMBER(add.getTYRE_IN_NUMBER());

				Tyre.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Tyre));
	}
	
	@RequestMapping(value = "/getAvailableTyreID", method = RequestMethod.POST)
	public void getAvailableTyreID(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<InventoryTyre> Tyre = new ArrayList<InventoryTyre>();
		List<InventoryTyre> Size = iventoryTyreService.Search_Only_AvailableTyre_ID(term, userDetails.getCompany_id());
		if (Size != null && !Size.isEmpty()) {
			for (InventoryTyre add : Size) {

				InventoryTyre wadd = new InventoryTyre();

				// wadd.setTS_ID(add.getTS_ID());
				wadd.setTYRE_ID(add.getTYRE_ID());
				wadd.setTYRE_NUMBER(add.getTYRE_NUMBER());
				wadd.setTYRE_IN_NUMBER(add.getTYRE_IN_NUMBER());

				Tyre.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Tyre));
	}
	
	/** This below Search Tyre Size Type Search Details */
	@RequestMapping(value = "/getTyreIDByLocation", method = RequestMethod.POST)
	public void getTyreIDByLocation(Map<String, Object> map, @RequestParam("term") final String term,
			@RequestParam("fromLocation") final Integer location,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<InventoryTyre> Tyre = new ArrayList<InventoryTyre>();
		List<InventoryTyre> Size = iventoryTyreService.Search_Only_InventoryTyre_ID(term, location, userDetails.getCompany_id());
		if (Size != null && !Size.isEmpty()) {
			for (InventoryTyre add : Size) {

				InventoryTyre wadd = new InventoryTyre();

				// wadd.setTS_ID(add.getTS_ID());
				wadd.setTYRE_ID(add.getTYRE_ID());
				wadd.setTYRE_NUMBER(add.getTYRE_NUMBER());
				wadd.setTYRE_IN_NUMBER(add.getTYRE_IN_NUMBER());

				Tyre.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Tyre));
	}

	@RequestMapping(value = "/getPurchaseOrderTyreStock", method = RequestMethod.GET)
	public void getPurchaseOrderTyreStock(@RequestParam(value = "TYRESIZE", required = true) Integer TYRE_SIZE,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		InventoryTyre wadd = new InventoryTyre();
		List<InventoryTyreDto> invent = iventoryTyreService.Purchase_Order_Validate_InventoryTyre(TYRE_SIZE);
		Integer TotalTyreAvailable = 0, NewTyre = 0, RtTyre = 0;
		if (invent != null && !invent.isEmpty()) {
			for (InventoryTyreDto inventoryTyre : invent) {
				TotalTyreAvailable += 1;
				if (inventoryTyre.getTYRE_RETREAD_COUNT() == 0) {
					NewTyre += 1;
				} else {
					RtTyre += 1;
				}
			}
			wadd.setTYRE_NUMBER("" + TotalTyreAvailable);
			wadd.setTYRE_RETREAD_COUNT(RtTyre);
			wadd.setTYRE_TREAD("" + NewTyre);

			wadd.setTYRE_SIZE_ID(TYRE_SIZE);
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(wadd));
	}
	
	// Image Document the Document id
	@RequestMapping(value = "/downloadinventorytyredocument2", method = RequestMethod.GET)
	public String download(HttpServletResponse response) {
		try {
			// Lookup file by fileId in database.
			MasterDocuments file = masterDocumentService.getMasterDocuemntByDocumentTypeId(MasterDocumentsConstant.MASTER_DOCUMENT_INVENTORY_TYRE);
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
	@RequestMapping(value = "/downloadinventorytyredocument", method = RequestMethod.GET)
	public String downloadbatteryinventorydocument(HttpServletResponse response) {

		XSSFWorkbook 						hssfWorkbook			= null;
		XSSFSheet 							hssfSheet				= null;
		XSSFRow 							hssfRow					= null; 
		DataValidationHelper 				validationHelper 		= null;
		List<VehicleTyreSize>  	 			VehicleTyreSize			= null;
		List<VehicleTyreModelType>  		VehicleTyreModelType	= null;
		List<VehicleTyreModelSubType> 		VehicleTyreModelSubType	= null;
		CustomUserDetails					userDetails				= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			hssfWorkbook				= new XSSFWorkbook();
			hssfSheet					=(XSSFSheet) hssfWorkbook.createSheet("sheet1");
			XSSFRow rowZero 			= hssfSheet.createRow((short) 0);
			hssfRow 					= hssfSheet.createRow((short) 1);
			VehicleTyreSize				= vehicleTyreSizeService.findAll();
			VehicleTyreModelType		= vehicleTyreModelTypeService.findAll();
			VehicleTyreModelSubType		= vehicleTyreModelSubTypeService.findAll();
			
			

			    validationHelper=new XSSFDataValidationHelper(hssfSheet);
			    hssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,10));
			    
			    rowZero.createCell(0).setCellValue("Note : (*) marked filled are mandatory. Please Add All Tyre Of Same Manufacuture Do not mix with multiple Manufacuture! ");
			    
			    hssfRow.createCell(0).setCellValue("Tyre Manufacturer (*)");
			    hssfRow.createCell(1).setCellValue("Tyre Model (*)");
				hssfRow.createCell(2).setCellValue("Tyre size (*)");
				hssfRow.createCell(3).setCellValue("Unit Cost (*)");
				hssfRow.createCell(4).setCellValue("Warehouse Location (*)");
				hssfRow.createCell(5).setCellValue("Tyre Number (*)");
				hssfRow.createCell(6).setCellValue("Retread Count");
				
				
			    /*
			     * this line will add Tyre manufacturer dropdown in excel from 2 to 50 row first column
			     */
			    hssfSheet.addValidationData(getDropDownList(2,2,0,0, getVehicleTyreTypeManufacturerArray(VehicleTyreModelType), validationHelper));
				
			    /*
			     * this line will add Tyre Model dropdown in excel from 2 to 50 row second column
			     */
			    hssfSheet.addValidationData(getDropDownList(2,2,1,1, getTyreModleArray(VehicleTyreModelSubType), validationHelper));
				  
			    /*
			     * this line will add Tyre Size dropdown in excel from 2 to 50 row third column
			     */
			    hssfSheet.addValidationData(getDropDownList(2,2,2,2, getTyreSizeArray(VehicleTyreSize), validationHelper));
			    
			   
			    hssfSheet.addValidationData(getDropDownList(2,2,4,4, getPartLocationArray(PartLocationsService.listAllPartLocationsByCompanyId(userDetails.getCompany_id())), validationHelper));
					
				
			    
			    for (int i=0; i<10; i++){
			    	hssfSheet.autoSizeColumn(i);
			    }

			FileOutputStream fileOut = new FileOutputStream(new File("TyreInventoryImport.xlsx"));
			hssfWorkbook.write(fileOut);
			fileOut.close();

			//Code to download
			File fileToDownload = new File("TyreInventoryImport.xlsx");
			InputStream in = new FileInputStream(fileToDownload);

			// Gets MIME type of the file
			String mimeType = new MimetypesFileTypeMap().getContentType("TyreInventoryImport.xlsx");

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
		}
		return null;
	}
	
	private DataValidation  getDropDownList(int firstRow, int lastRow, int firstCol, int lastCol, String[] myarray, DataValidationHelper validationHelper) throws Exception{
		DataValidation 				dataValidation 					= null;
		CellRangeAddressList		addressList 					= null;
		DataValidationConstraint 	constraint 						= null;
		try {
			
			addressList 	= new  CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);
		    constraint 		= validationHelper.createExplicitListConstraint(myarray);
		    dataValidation  = validationHelper.createValidation(constraint, addressList);
		    dataValidation.setSuppressDropDownArrow(true);   
			
			return dataValidation;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private String []  getVehicleTyreTypeManufacturerArray(List<VehicleTyreModelType> list) {
		List<String>  				maStrings 			= null;	
		try {
				maStrings = new ArrayList<>();
				for(VehicleTyreModelType 	manufacturer : list){
					  maStrings.add(manufacturer.getTYRE_MODEL());
				}
				return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
			} catch (Exception e) {
				throw e;
			}finally {
				maStrings 			= null;
			}
 }
	
	private String []  getTyreModleArray(List<VehicleTyreModelSubType> list) {
		List<String>  				maStrings 			= null;	
		try {
				maStrings = new ArrayList<>();
				for(VehicleTyreModelSubType 	tyremodel : list){
					  maStrings.add(tyremodel.getTYRE_MODEL_SUBTYPE());
				}
				return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
			} catch (Exception e) {
				throw e;
			}finally {
				maStrings 			= null;
			}
}

	
 private String []  getTyreSizeArray(List<VehicleTyreSize> list) {
	List<String>  				maStrings 			= null;	
	try {
			maStrings = new ArrayList<>();
			for(VehicleTyreSize 	tyresize : list){
				  maStrings.add(tyresize.getTYRE_SIZE());
			}
			return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
		} catch (Exception e) {
			throw e;
		}finally {
			maStrings 			= null;
		}
	}
 
  
 private String []  getPartLocationArray(List<PartLocations> list) {
		List<String>  				maStrings 			= null;	
		try {
				maStrings = new ArrayList<>();
				for(PartLocations 	tyremodel : list){
					  maStrings.add(tyremodel.getPartlocation_name());
				}
				return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
			} catch (Exception e) {
				throw e;
			}finally {
				maStrings 			= null;
			}
 }
 
 
	
	@SuppressWarnings({ "resource", "deprecation" })
	@RequestMapping(value = "/importInventoryTyre", method = RequestMethod.POST)
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
		InventoryTyreInvoice 				inventoryTyreInvoice		= null;
		InventoryTyre 						inventoryTyre				= null;
		ArrayList<InventoryTyre>			inventoryTyreList			= null;
		InventoryTyreAmount 				inventoryTyreAmount			= null;
		List<VehicleTyreModelType>			tyreManufacturerList		= null;
		List<VehicleTyreModelSubType>		tyreModelList				= null;
		List<VehicleTyreSize>				tyreSizeList				= null;
		List<PartLocations>					warehouseLocList			= null;
		Double 								totalCost 					= 0.0;
		int 								noOfRows					= 0;
		int 								noOfRows2					= 0;
		int 								valuesInserted				= 0;
		int 								valuesRejected				= 0;

		try {
			model 					= new HashMap<String, Object>();
			inventoryTyreList		= new ArrayList<>();
			rootPath 				= request.getSession().getServletContext().getRealPath("/");
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			tyreManufacturerList	= vehicleTyreModelTypeService.findAll();
			tyreModelList			= vehicleTyreModelSubTypeService.findAll();
			tyreSizeList			= vehicleTyreSizeService.findAll();
			warehouseLocList		= partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id());

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

				inventoryTyreInvoice	= new InventoryTyreInvoice();
				inventoryTyreAmount		= new InventoryTyreAmount();

				inventoryTyreInvoice.setPAYMENT_NUMBER(null);
				inventoryTyreInvoice.setPO_NUMBER(null);
				inventoryTyreInvoice.setINVOICE_NUMBER(null);
				inventoryTyreInvoice.setINVOICE_DATE(toDate);
				inventoryTyreInvoice.setINVOICE_AMOUNT((double) 0);
				inventoryTyreInvoice.setPAYMENT_TYPE_ID(PaymentTypeConstant.PAYMENT_TYPE_CASH);
				inventoryTyreInvoice.setDESCRIPTION("Dummy Invoice");
				inventoryTyreInvoice.setCREATEDBYID(userDetails.getId());
				inventoryTyreInvoice.setLASTMODIFIEDBYID(userDetails.getId());
				inventoryTyreInvoice.setCREATED_DATE(toDate);
				inventoryTyreInvoice.setLASTUPDATED_DATE(toDate);
				inventoryTyreInvoice.setCOMPANY_ID(userDetails.getCompany_id());
				inventoryTyreInvoice.setMarkForDelete(false);
				inventoryTyreInvoice.setVENDOR_ID(0);

				inventoryTyreAmount.setCOMPANY_ID(userDetails.getCompany_id());
				inventoryTyreAmount.setDISCOUNT((double) 0);
				inventoryTyreAmount.setTAX((double) 0);
				
				Cell cell	= null;
				String cellValue	= null;
				for(int i=0; i < 5; i++) {
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
							for (int j = 0; j < tyreManufacturerList.size(); j++) {
								if(tyreManufacturerList.get(j).getTYRE_MODEL().equalsIgnoreCase(cellValue)) {
									inventoryTyreAmount.setTYRE_MANUFACTURER_ID(tyreManufacturerList.get(j).getTYRE_MT_ID());
								}
							}
							if(inventoryTyreAmount.getTYRE_MANUFACTURER_ID() == null || inventoryTyreAmount.getTYRE_MANUFACTURER_ID() <= 0) {
								inventoryTyreInvoice	= null;
								inventoryTyreAmount		= null;
								break;	
							}
						} else {
							inventoryTyreInvoice	= null;
							inventoryTyreAmount		= null;
							break;
						}
					}
					if(i == 1) {
						if(cellValue != null && !cellValue.isEmpty()) {
							for (int j = 0; j < tyreModelList.size(); j++) {
								if(tyreModelList.get(j).getTYRE_MODEL_SUBTYPE().equalsIgnoreCase(cellValue)) {
									inventoryTyreAmount.setTYRE_MODEL_ID(tyreModelList.get(j).getTYRE_MST_ID());
								}
							}
							if(inventoryTyreAmount.getTYRE_MODEL_ID() == null || inventoryTyreAmount.getTYRE_MODEL_ID() <= 0) {
								inventoryTyreInvoice	= null;
								inventoryTyreAmount		= null;
								break;	
							}
						} else {
							inventoryTyreInvoice	= null;
							inventoryTyreAmount		= null;
							break;
						}
					}
					if(i == 2) {
						if(cellValue != null && !cellValue.isEmpty()) {
							for (int j = 0; j < tyreSizeList.size(); j++) {
								if(tyreSizeList.get(j).getTYRE_SIZE().equalsIgnoreCase(cellValue)) {
									inventoryTyreAmount.setTYRE_SIZE_ID(tyreSizeList.get(j).getTS_ID());
								}
							}
							if(inventoryTyreAmount.getTYRE_SIZE_ID() == null || inventoryTyreAmount.getTYRE_SIZE_ID() <= 0) {
								inventoryTyreInvoice	= null;
								inventoryTyreAmount		= null;
								break;	
							}
						} else {
							inventoryTyreInvoice	= null;
							inventoryTyreAmount		= null;
							break;
						}
					}
					if(i == 3) {
						if(cellValue != null && !cellValue.isEmpty()) {
							inventoryTyreAmount.setUNIT_COST(Double.parseDouble(cellValue));
						} else {
							inventoryTyreInvoice	= null;
							inventoryTyreAmount		= null;
							break;
						}
					}
					if(i == 4) {
						if(cellValue != null && !cellValue.isEmpty()) {
							for (int j = 0; j < warehouseLocList.size(); j++) {
								if(warehouseLocList.get(j).getPartlocation_name().equalsIgnoreCase(cellValue)) {
									inventoryTyreAmount.setWAREHOUSE_LOCATION_ID(warehouseLocList.get(j).getPartlocation_id());
									inventoryTyreInvoice.setWAREHOUSE_LOCATION_ID(warehouseLocList.get(j).getPartlocation_id());
								}
							}
							if(inventoryTyreAmount.getWAREHOUSE_LOCATION_ID() == null || inventoryTyreAmount.getWAREHOUSE_LOCATION_ID() <= 0) {
								inventoryTyreInvoice	= null;
								inventoryTyreAmount		= null;
								break;
							}
						} else {
							inventoryTyreInvoice	= null;
							inventoryTyreAmount		= null;
							break;
						}
					}
				}
				
				break;
			}

			rowIterator = mySheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				noOfRows2 += 1;
				if(noOfRows2 == 1 || noOfRows2 == 2) {
					continue;
				}
				
				inventoryTyre	= new InventoryTyre();
				
				inventoryTyre.setTYRE_TREAD(null);
				inventoryTyre.setTYRE_RETREAD_COUNT(0);
				inventoryTyre.setTYRE_USEAGE(0);

				try {
					inventoryTyre.setCREATED_DATE(toDate);
					inventoryTyre.setLASTUPDATED_DATE(toDate);
					inventoryTyre.setTYRE_PURCHASE_DATE(toDate);
				} catch (Exception e) {
					e.printStackTrace();
				}

				inventoryTyre.setCOMPANY_ID(userDetails.getCompany_id());
				inventoryTyre.setCREATEDBYID(userDetails.getId());
				inventoryTyre.setLASTMODIFIEDBYID(userDetails.getId());
				inventoryTyre.setMarkForDelete(false);
				inventoryTyre.setTYRE_ASSIGN_STATUS_ID(InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE);
				
				Cell cell	= null;
				String cellValue	= null;
				for(int i=0; i < 7; i++) {
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
							for (int j = 0; j < tyreManufacturerList.size(); j++) {
								if(tyreManufacturerList.get(j).getTYRE_MODEL().equalsIgnoreCase(cellValue)) {
									inventoryTyre.setTYRE_MANUFACTURER_ID(tyreManufacturerList.get(j).getTYRE_MT_ID());
									break;
								}
							}
							if(inventoryTyre.getTYRE_MANUFACTURER_ID() == null || inventoryTyre.getTYRE_MANUFACTURER_ID() <= 0) {
								inventoryTyre		= null;
								valuesRejected	+= 1;
								break;								
							}
						} else {
							inventoryTyre		= null;
							valuesRejected	+= 1;
							break;
						}
					}
					if(i == 1) {
						if(cellValue != null && !cellValue.isEmpty()) {
							for (int j = 0; j < tyreModelList.size(); j++) {
								if(tyreModelList.get(j).getTYRE_MODEL_SUBTYPE().equalsIgnoreCase(cellValue)) {
									inventoryTyre.setTYRE_MODEL_ID(tyreModelList.get(j).getTYRE_MST_ID());
								}
							}
							if(inventoryTyre.getTYRE_MODEL_ID() == null || inventoryTyre.getTYRE_MODEL_ID() <= 0) {
								inventoryTyre		= null;
								valuesRejected	+= 1;
								break;								
							}
						} else {
							inventoryTyre		= null;
							valuesRejected	+= 1;
							break;
						}
					}
					if(i == 2) {
						if(cellValue != null && !cellValue.isEmpty()) {
							for (int j = 0; j < tyreSizeList.size(); j++) {
								if(tyreSizeList.get(j).getTYRE_SIZE().equalsIgnoreCase(cellValue)) {
									inventoryTyre.setTYRE_SIZE_ID(tyreSizeList.get(j).getTS_ID());
								}
							}
							if(inventoryTyre.getTYRE_SIZE_ID() == null || inventoryTyre.getTYRE_SIZE_ID() <= 0) {
								inventoryTyre		= null;
								valuesRejected	+= 1;
								break;								
							}
						} else {
							inventoryTyre		= null;
							valuesRejected	+= 1;
							break;
						}
					}
					if(i == 3) {
						if(cellValue != null && !cellValue.isEmpty()) {
							inventoryTyre.setTYRE_AMOUNT(Double.parseDouble(cellValue));
						} else {
							inventoryTyre		= null;
							valuesRejected	+= 1;
							break;
						}
					}
					if(i == 4) {
						if(cellValue != null && !cellValue.isEmpty()) {
							for (int j = 0; j < warehouseLocList.size(); j++) {
								if(warehouseLocList.get(j).getPartlocation_name().equalsIgnoreCase(cellValue)) {
									inventoryTyre.setWAREHOUSE_LOCATION_ID(warehouseLocList.get(j).getPartlocation_id());
								}
							}
							if(inventoryTyre.getWAREHOUSE_LOCATION_ID() == null || inventoryTyre.getWAREHOUSE_LOCATION_ID() <= 0) {
								inventoryTyre			= null;
								valuesRejected	+= 1;
								break;								
							}
						} else {
							inventoryTyre			= null;
							valuesRejected	+= 1;
							break;
						}
					}
					if(i == 5) {
						if(cellValue != null && !cellValue.isEmpty()) {
							inventoryTyre.setTYRE_NUMBER(cellValue);
						} else {
							inventoryTyre			= null;
							valuesRejected	+= 1;
							break;
						}
					}
					if(i == 6) {
						if(cellValue != null && !cellValue.isEmpty()) {
							inventoryTyre.setTYRE_RETREAD_COUNT(Integer.parseInt(cellValue));
						} else {
							inventoryTyre.setTYRE_RETREAD_COUNT(0);
							break;
						}
					}
				}
				if(inventoryTyre	!= null) {
					InventoryTyre validate = iventoryTyreService.Validate_InventoryTyre(inventoryTyre.getTYRE_NUMBER(), userDetails.getCompany_id());
					if (validate == null && inventoryTyre.getTYRE_NUMBER() != null && inventoryTyre.getTYRE_NUMBER() != "") {
						valuesInserted	+= 1;
						totalCost	= totalCost + inventoryTyre.getTYRE_AMOUNT();
						inventoryTyreList.add(inventoryTyre);
					} else {
						valuesRejected	+= 1;
					}
				}
			}
			if(inventoryTyreInvoice	!= null && inventoryTyreAmount	!= null && inventoryTyreList != null) {
				if (inventoryTyreInvoice.getPAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					inventoryTyreInvoice.setVENDOR_PAYMODE_STATUS_ID(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
				} else {
					inventoryTyreInvoice.setVENDOR_PAYMODE_STATUS_ID(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID);
					try {
						java.util.Date currentDate3 = new java.util.Date();
						java.util.Date dateTo3 = dateFormatTime.parse(ft.format(currentDate3));
						inventoryTyreInvoice.setVENDOR_PAYMODE_DATE(dateTo3);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				inventoryTyreInvoice.setITYRE_NUMBER((long) -100000);
				
				iventoryTyreService.save_Tyre_Inventory_Invoice(inventoryTyreInvoice);
				
				inventoryTyreAmount.setTOTAL_AMOUNT(totalCost);
				inventoryTyreAmount.setInventoryTyreInvoice(inventoryTyreInvoice);
				inventoryTyreAmount.setMarkForDelete(false);
				inventoryTyreAmount.setTYRE_QUANTITY((double) inventoryTyreList.size());
				inventoryTyreAmount.setTYRE_ASSIGN_NO(inventoryTyreList.size());
				
				iventoryTyreService.add_Tyre_Inventory_Amount(inventoryTyreAmount);
				
				for(InventoryTyre inventoryTyreModel : inventoryTyreList) {
					inventoryTyreModel.setITYRE_AMOUNT_ID(inventoryTyreAmount.getITYRE_AMD_ID());
					inventoryTyreModel.setITYRE_INVOICE_ID(inventoryTyreInvoice.getITYRE_ID());
				}
				iventoryTyreService.save_Multiple_Inventory_TYRE(inventoryTyreList);
				iventoryTyreService.Update_Inventory_Amount_Complete_Tyre_Number(inventoryTyreList.size(), inventoryTyreAmount.getITYRE_AMD_ID(), userDetails.getCompany_id());
			}
			
			model.put("valuesInserted", valuesInserted);
			model.put("valuesRejected", valuesRejected);
			model.put("tyreSaved", true);
			
			return new ModelAndView("redirect:/TyreInventory/1.in", model);
		} catch (Exception e) {
			LOGGER.error("Excetion : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/receiveMultipleTyreTransfered", method = RequestMethod.GET)
	public ModelAndView receiveMultipleTyreTransfered(Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// get the Inventory id to value
			//model.put("Tyre", ITBL.Show_InventoryTyre_Details(iventoryTyreService.Get_TYRE_ID_InventoryTyre((long) 43434, userDetails.getCompany_id())));

			List<InventoryTyreTransferDto> show_Location = iventoryTyreService
					.Get_Tyre_id_To_InventoryTyreTransfer(userDetails);
			model.put("InventoryTransfer", ITBL.prepareListofDto_Inventory_Tyre_Transfer(show_Location));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("showMultiTransferTyreInventory", model);
	}
	
	@RequestMapping(value = "/TyreCountDetails", method = RequestMethod.GET)
	public ModelAndView VehicleCreationReport(@RequestParam ("Id") final short statusId) throws Exception{
		Map<String, Object> 		model 					= new HashMap<String, Object>();
		
		try {
			
			
			return new ModelAndView("VehicleCreation_Report", model);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping("/download/TyreInventoryDocument/{tyre_document_id}")
	public String downloadTyreDocument(@PathVariable("tyre_document_id") Long tyre_document_id,
			HttpServletResponse response) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (tyre_document_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				//ServiceEntriesDocument file = ServiceEntriesService.getServiceEntriesDocument(serviceEntries_id);
				org.fleetopgroup.persistence.document.TyreDocument	file =		tyreDocumentService.getTyreDocument(tyre_document_id, userDetails.getCompany_id());
				if (file != null) {
					if (file.getTyre_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getTyre_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getTyre_contentType());
						response.getOutputStream().write(file.getTyre_content());
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
	
	@RequestMapping(value = "/getTyreByTyreModel", method = RequestMethod.POST)
	public void getTyreByTyreModel(Map<String, Object> map, @RequestParam("term") final String term,
			@RequestParam("tyreModelId") final Long tyreModelId,@RequestParam("tyreStatusId") final short tyreStatusId,@RequestParam("locationId") final Integer locationId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<InventoryTyre> Tyre = new ArrayList<InventoryTyre>();
		List<InventoryTyre> Size = iventoryTyreService.getTyreByTyreModel(term, tyreModelId,tyreStatusId,userDetails.getCompany_id(),locationId);
		if (Size != null && !Size.isEmpty()) {
			for (InventoryTyre add : Size) {

				InventoryTyre wadd = new InventoryTyre();

				// wadd.setTS_ID(add.getTS_ID());
				wadd.setTYRE_ID(add.getTYRE_ID());
				wadd.setTYRE_NUMBER(add.getTYRE_NUMBER());
				wadd.setTYRE_IN_NUMBER(add.getTYRE_IN_NUMBER());

				Tyre.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Tyre));
	}
	
	@PostMapping(value = "/getTyreIDsForTransfer")
	public void getTyreIDsForTransfer( @RequestParam HashMap<Object, Object> map,HttpServletResponse response) throws Exception {
		ValueObject object = new ValueObject(map);
		 List<InventoryTyre> tyre = iventoryTyreService.tyreListDropDown(object.getString("term",""),  object.getInt("fromLocation",0), object.getInt("companyId",0), object.getLong("model",0));
		if (tyre == null || tyre.isEmpty()) {
			tyre = new ArrayList<>();
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(tyre));
	}
	
	@GetMapping(value = "/getMoveToRepairTyre")
	public void getMoveToRepairTyre(@RequestParam("fromLocationId") final Integer fromLocationId, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<InventoryTyre> Tyre = new ArrayList<InventoryTyre>();
		List<InventoryTyre> Size = iventoryTyreService.getMoveToRepairTyre(userDetails.getCompany_id(), fromLocationId);
		if (Size != null && !Size.isEmpty()) {
			for (InventoryTyre add : Size) {

				InventoryTyre wadd = new InventoryTyre();
				wadd.setTYRE_ID(add.getTYRE_ID());
				wadd.setTYRE_NUMBER(add.getTYRE_NUMBER());
				wadd.setTYRE_IN_NUMBER(add.getTYRE_IN_NUMBER());

				Tyre.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Tyre));
	}
	
	@GetMapping(value = "/getLocationWiseTyreList")
	public void getLocationWiseTyreList(@RequestParam("fromLocationId") final Integer fromLocationId, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<InventoryTyre> Tyre = new ArrayList<InventoryTyre>();
		List<InventoryTyre> Size = iventoryTyreService.getLocationWiseTyreList(userDetails.getCompany_id(), fromLocationId);
		if (Size != null && !Size.isEmpty()) {
			for (InventoryTyre add : Size) {

				InventoryTyre wadd = new InventoryTyre();
				wadd.setTYRE_ID(add.getTYRE_ID());
				wadd.setTYRE_NUMBER(add.getTYRE_NUMBER());
				wadd.setTYRE_IN_NUMBER(add.getTYRE_IN_NUMBER());

				Tyre.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Tyre));
	}

}

package org.fleetopgroup.rest.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.MasterDocumentsConstant;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.document.MasterDocuments;
import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.dto.BatteryTransferDto;
import org.fleetopgroup.persistence.dto.BatteryTypeDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.InventoryTyreTransferDto;
import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.BatteryAmount;
import org.fleetopgroup.persistence.model.BatteryCapacity;
import org.fleetopgroup.persistence.model.BatteryHistory;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.BatteryManufacturer;
import org.fleetopgroup.persistence.model.BatteryTransfer;
import org.fleetopgroup.persistence.model.BatteryType;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryAmountService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryCapacityService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryManufacturerService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryTransferService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryTypeService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IMasterDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class BatteryInventoryController {
	
	@Autowired private IBatteryManufacturerService		batteryManufacturerService;	
	@Autowired private IBatteryTypeService				batteryTypeService;
	@Autowired private IVendorService					vendorService;
	@Autowired private IBatteryService					batteryService;
	@Autowired private IBatteryAmountService			batteryAmountService;
	@Autowired private IBatteryInvoiceService			batteryInvoiceService;
	@Autowired private IPartLocationsService			PartLocationsService;
	@Autowired private IBatteryCapacityService			batteryCapacityService;
	@Autowired private IBatteryHistoryService			batteryHistoryService;
	@Autowired private IPartLocationPermissionService 	partLocationPermissionService;
	@Autowired private IBatteryTransferService			batteryTransferService;
	@Autowired private IMasterDocumentService			masterDocumentService;
	@Autowired private ICompanyConfigurationService		companyConfigurationService;
	@Autowired private MongoOperations					mongoOperations;
	@Autowired private IPendingVendorPaymentService		pendingVendorPaymentService;
	@Autowired private IBankPaymentService              bankPaymentService;
	
	PartLocationsBL PLBL = new PartLocationsBL();
	InventoryBL INVBL = new InventoryBL();
	
	@GetMapping(path = "/BatteryInventory")
	public ModelAndView batteryInventory(HttpServletRequest		request) throws Exception {
		ModelAndView map = new ModelAndView("AddBatteryInventory");
		boolean						showSubLocation		= false;
		String						mainLocationIds		= "";
		try {
			CustomUserDetails		 userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.BATTERY_CONFIGURATION_CONFIG);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			showSubLocation	 	= (boolean)configuration.getOrDefault(BatteryConstant.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(BatteryConstant.MAIN_LOCATION_IDS,"") +"";
			
			map.addObject("showSubLocation",showSubLocation);
			map.addObject("mainLocationIds",mainLocationIds);
			map.addObject("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			map.addObject("permissions", permission);
			map.addObject("configuration", configuration);
			map.addObject("accessToken", Utility.getUniqueToken(request));
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	
	@PostMapping(path = "/getBatteryManufacturer")
	public void getBatteryManufacturer(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BatteryManufacturer>   batteryManufacturers	= new ArrayList<>();
			List<BatteryManufacturer>   manufacturers	= batteryManufacturerService.getBatteryManufacturerList(term);
			if(manufacturers != null && !manufacturers.isEmpty()) {
				for(BatteryManufacturer batteryManufacturer : manufacturers) {
					batteryManufacturers.add(batteryManufacturer);
				}
			}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(batteryManufacturers));
	}
	@GetMapping(path = "/getBatteryType")
	public void getTyreModelSubTypeChangeTyreModel(@RequestParam(value = "ModelType", required = true) Long manufactureId,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BatteryTypeDto>    dtoList	= null;
		 List<BatteryType>	list	=	batteryTypeService.getBatteryType(manufactureId);
		 if(list != null && !list.isEmpty()) {
			 dtoList	= new ArrayList<>();
			 BatteryTypeDto	batteryTypeDto	= null;
			 for(BatteryType batteryType : list) {
				 batteryTypeDto	= new BatteryTypeDto();
				 
				 batteryTypeDto.setBatteryTypeId(batteryType.getBatteryTypeId());
				 if(batteryType.getPartNumber() != null) {
					 batteryTypeDto.setBatteryType(batteryType.getBatteryType() + "-"+batteryType.getPartNumber()+"("+batteryType.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryType.getWarrantyTypeId())+")");
				 }else {
					 batteryTypeDto.setBatteryType(batteryType.getBatteryType() +"("+batteryType.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryType.getWarrantyTypeId())+")");
				 }
				 
				 dtoList.add(batteryTypeDto);
			 }
		 }
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(dtoList));
	}
	
	@PostMapping(path = "/getBatteryVendorSearchList")
	public void getTyreVendorSearchList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= null;
		try {
			List<Vendor> vendorList =	new ArrayList<>();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Vendor> vendor = vendorService.SearchOnly_Battery_VendorName(term, userDetails.getCompany_id());
			if(vendor != null && !vendor.isEmpty()) {
				for(Vendor vendor2 : vendor) {
					vendorList.add(vendor2);
				}
			}
			
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(vendorList));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(path = "/getVendorSearchList")
	public void getVendorSearchList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= null;
		try {
			List<Vendor> vendorList =	new ArrayList<>();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Vendor> vendor = vendorService.SearchOnly_All_VendorName(term, userDetails.getCompany_id());
			if(vendor != null && !vendor.isEmpty()) {
				for(Vendor vendor2 : vendor) {
					vendorList.add(vendor2);
				}
			}
			
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(vendorList));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(path = "/saveBatteryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveBatteryDetails(@RequestParam("batteryInvoiceData") String allRequestParams,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		ValueObject valueObject = null;

		try {
			valueObject =  new ValueObject();
			valueObject = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			valueObject.put("batteryDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("batteryDetails")));
			return batteryService.saveBatteryDetails(valueObject,uploadfile).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@GetMapping(path = "/showBatteryInvoice")
	public ModelAndView showTyreInventory(@RequestParam("Id") final Long invoiceId,
			final HttpServletRequest request) throws Exception {
		ModelAndView map = new ModelAndView("Show_BatteryInventory");
		boolean		showSubLocation = false;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.BATTERY_CONFIGURATION_CONFIG);
			showSubLocation = (boolean)configuration.getOrDefault(BatteryConstant.SHOW_SUB_LOCATION, false);
			map.addObject("invoiceId",invoiceId);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("configuration", configuration);
			map.addObject("showSubLocation", showSubLocation);
		} catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@PostMapping(path = "/getBatteryInvoiceDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getBatteryInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return batteryService.get_list_BatteryInvoiceDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/saveBatterySerialNumber", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveBatterySerialNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			object.put("batteryDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("batteryDetails")));
			
			return batteryService.saveBatterySerialNumber(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/deleteBatteryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deleteBatteryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			return batteryService.deleteBatteryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/deleteBatteryAmountDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deleteBatteryAmountDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			return batteryAmountService.deleteBatteryAmountDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/deleteBatteryInventory", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deleteBatteryInventory(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return batteryService.deleteBatteryInvoice(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(path = "/viewBatteryInvoice/{pageNumber}")
	public ModelAndView viewVendorPaymentList(@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {
		ModelAndView map = new ModelAndView("View_Battery_Invoice");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("SelectPage", pageNumber);
			model.addAttribute("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			return map;
		} catch (NullPointerException e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getPageWiseBatteryInvoiceDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getPageWiseBatteryInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return batteryInvoiceService.getBatteryInvoiceList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(path = "/editBatteryInvoice")
	public ModelAndView editBatteryInvoice(@RequestParam("Id") final Long Invoice_id,
			final InventoryTyreInvoiceDto TyreInvoiceDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		boolean						showSubLocation		= false;
		String						mainLocationIds		= "";
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.BATTERY_CONFIGURATION_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(BatteryConstant.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(BatteryConstant.MAIN_LOCATION_IDS, "")+"";
			BatteryInvoiceDto	batteryInvoiceDto = new BatteryInvoiceDto();
			batteryInvoiceDto.setCompanyId(userDetails.getCompany_id());
			batteryInvoiceDto.setBatteryInvoiceId(Invoice_id);
			if(showSubLocation) {
				model.put("showSubLocation",showSubLocation);
				model.put("mainLocationIds",mainLocationIds);
			}
			model.put("batteryInvoice",batteryInvoiceService.getBatteryInvoice(batteryInvoiceDto));
			model.put("configuration",configuration);
		} catch (Exception e) {
			System.err.println("Exception "+e);
		}finally {
			userDetails	= null;
		}
		return new ModelAndView("Edit_BatteryInvoice", model);
	}
	
	@PostMapping(path = "/updateBatteryInventory")
	public ModelAndView updateTyreInventory(@ModelAttribute BatteryInvoiceDto batteryInvoiceDto
			,  @RequestParam("input-file-preview") MultipartFile file,
			@ModelAttribute BankPaymentDto bankPaymentDto,final HttpServletRequest request) {
		Map<String, Object> 			model 						= new HashMap<String, Object>();
		CustomUserDetails				userDetails					= null;
		BatteryInvoice					batteryInvoice				= null;
		HashMap<String, Object> 		configuration				= null;
		//HashMap<String, Object> 		companyConfiguration				= null;
		BatteryInvoice					validateBatteryInvoice		= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.BATTERY_CONFIGURATION_CONFIG);
			//companyConfiguration= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if (batteryInvoiceDto.getBatteryInvoiceId() != null) {
				batteryInvoice 	= batteryInvoiceService.getBatteryInvoiceByBatteryInvoiceId(batteryInvoiceDto.getBatteryInvoiceId(), userDetails.getCompany_id());
				
				if((boolean) configuration.getOrDefault("validateInvoiceNumber", false)) {
					if(!batteryInvoice.getInvoiceNumber().equalsIgnoreCase(batteryInvoiceDto.getInvoiceNumber())) {
						validateBatteryInvoice = batteryInvoiceService.getBatteryInvoiceByInvoiceNumber(batteryInvoiceDto.getInvoiceNumber(), userDetails.getCompany_id());
						if(validateBatteryInvoice != null) {
							model.put("duplicateInvoiceNumber", true);
							return new ModelAndView("redirect:/editBatteryInvoice?Id=" + batteryInvoiceDto.getBatteryInvoiceId(), model);
						}
					}
				}
				
				short	oldPaymentTypeId = batteryInvoice.getPaymentTypeId();
				
				batteryInvoiceDto.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
				batteryInvoiceDto.setLastModifiedById(userDetails.getId());
				batteryInvoiceDto.setInvoiceDate(DateTimeUtility.getDateTimeFromStr(batteryInvoiceDto.getInvoiceDateStr(), DateTimeUtility.DD_MM_YYYY));
				batteryInvoiceDto.setCompanyId(userDetails.getCompany_id());
				
				if (file != null && !file.isEmpty() && file.getSize() > 0) {
					batteryInvoiceDto.setBattery_document(true);
				}else if(batteryInvoice.isBattery_document()) {
					batteryInvoiceDto.setBattery_document(true);
				}else {
					batteryInvoiceDto.setBattery_document(false);
				}
				batteryInvoiceDto.setPaymentTypeId(batteryInvoiceDto.getPaymentTypeId());
				if(batteryInvoiceDto.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					batteryInvoiceDto.setVendorPaymentStatus(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
				}else {
					batteryInvoiceDto.setVendorPaymentStatus(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
				}
				batteryInvoiceService.updateBatteryInvoice(batteryInvoiceDto);
				
				if(batteryInvoice != null ) {
					if(batteryInvoice.getSubLocationId() != batteryInvoiceDto.getSubLocationId()) {
						batteryInvoiceService.updateSublocationInBattery(batteryInvoice.getBatteryInvoiceId() ,batteryInvoiceDto.getSubLocationId(),userDetails.getCompany_id() );
					}
				}
				
				PendingVendorPayment	payment		= null;
				if(oldPaymentTypeId != batteryInvoiceDto.getPaymentTypeId() && batteryInvoiceDto.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForBIEDIT(batteryInvoiceDto);
					pendingVendorPaymentService.savePendingVendorPayment(payment);
				}else if(oldPaymentTypeId == batteryInvoiceDto.getPaymentTypeId() && batteryInvoiceDto.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForBIEDIT(batteryInvoiceDto);
					pendingVendorPaymentService.updatePendingVendorPayment(payment);
				}else if(oldPaymentTypeId != batteryInvoiceDto.getPaymentTypeId() && batteryInvoiceDto.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					pendingVendorPaymentService.deletePendingVendorPayment(batteryInvoice.getBatteryInvoiceId(), PendingPaymentType.PAYMENT_TYPE_BATTERY_INVOICE);
				}
				if(bankPaymentDto.isAllowBankPaymentDetails()) {
				ValueObject bankPaymentValueObject=new ValueObject();
				bankPaymentValueObject.put("oldPaymentTypeId", oldPaymentTypeId);
				bankPaymentValueObject.put("currentPaymentTypeId",batteryInvoiceDto.getPaymentTypeId());
				bankPaymentValueObject.put("userId", userDetails.getId());
				bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId", batteryInvoice.getBatteryInvoiceId());
				bankPaymentValueObject.put("moduleNo", batteryInvoice.getBatteryInvoiceNumber());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.BATTRRY_INVENTORY);
				bankPaymentValueObject.put("amount",batteryInvoiceDto.getInvoiceAmount());
				Vendor	vendor	= vendorService.getVendor(batteryInvoice.getVendorId());
				bankPaymentValueObject.put("remark","Battery Invoice Edit On BI-"+batteryInvoice.getBatteryInvoiceNumber()+" Vendor : "+vendor.getVendorName()+" ");
				
				bankPaymentService.updatePaymentDetails(bankPaymentValueObject, bankPaymentDto);
				}
				
				if (file != null && !file.isEmpty() && file.getSize() > 0) {
					org.fleetopgroup.persistence.document.BatteryInvoiceDocument batteryDoc = new org.fleetopgroup.persistence.document.BatteryInvoiceDocument();
					batteryDoc.setBatteryInvoiceId(batteryInvoiceDto.getBatteryInvoiceId());
					try {
							byte[] bytes = file.getBytes();
							batteryDoc.setBatteryInvoiceFileName(file.getOriginalFilename());
							batteryDoc.setBatteryInvoiceContent(bytes);
							batteryDoc.setBatteryInvoiceContentType(file.getContentType());

							batteryDoc.setMarkForDelete(false);

							batteryDoc.setCreatedById(userDetails.getId());
							batteryDoc.setLastModifiedById(userDetails.getId());

							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

							batteryDoc.setCreated(toDate);
							batteryDoc.setLastupdated(toDate);
							batteryDoc.setCompanyId(userDetails.getCompany_id());
						} catch (IOException e) {
							e.printStackTrace();
						}
					try {
						org.fleetopgroup.persistence.document.BatteryInvoiceDocument doc = batteryService.getBatteryInvoiceDocumentDetailsByInvoiceId(batteryInvoiceDto.getBatteryInvoiceId(), userDetails.getCompany_id());
						if (doc != null) {
							batteryDoc.set_id(doc.get_id());
							mongoOperations.save(batteryDoc);
							batteryService.updateBatteryInvoiceDocumentId(batteryDoc.get_id(), true,batteryInvoiceDto.getBatteryInvoiceId(), userDetails.getCompany_id());
							
						}else {
							batteryService.addBatteryInvoiceDocument(batteryDoc);
							batteryService.updateBatteryInvoiceDocumentId(batteryDoc.get_id(), true,batteryInvoiceDto.getBatteryInvoiceId(), userDetails.getCompany_id());
						}
						
					} catch (ConverterNotFoundException e) {
						System.err.println("catch");
						return new ModelAndView("redirect:/BatteryInventory.in", model);
					}
				}
				model.put("UpdateSuccess", true);
			} else {
				return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/viewBatteryInvoice/1.in?danger=true");
		}
		return new ModelAndView("redirect:/showBatteryInvoice?Id=" + batteryInvoiceDto.getBatteryInvoiceId(), model);
	}
	
	@PostMapping(path = "/locationBatteryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  locationBatteryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return batteryService.getLocationBatteryList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getSearchBatteryCapacity")
	public void getSearchBatteryCapacity(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BatteryCapacity> Size = null;
		try {
			List<BatteryCapacity> Tyre = new ArrayList<BatteryCapacity>();
			
			Size = batteryCapacityService.searchBatteryCapacity(term);
			if (Size != null && !Size.isEmpty()) {
				for (BatteryCapacity add : Size) {

					BatteryCapacity wadd = new BatteryCapacity();
						
					wadd.setBatteryCapacityId(add.getBatteryCapacityId());
					wadd.setBatteryCapacity(add.getBatteryCapacity());

					Tyre.add(wadd);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(Tyre));
		} catch (Exception e) {
			throw e;
		}finally {
			Size = null;
		}
		
	}
	
	@GetMapping(path = "/showBatteryInformation")
	public ModelAndView showBatteryInformation(@RequestParam("Id") final Long batteryId,
			final HttpServletRequest request) throws Exception {
		ModelAndView map = new ModelAndView("Show_BatteryInfo");
		boolean      showSubLocation	= false;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.BATTERY_CONFIGURATION_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(BatteryConstant.SHOW_SUB_LOCATION, false);
			map.addObject("batteryId",batteryId);
			map.addObject("showSubLocation",showSubLocation);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
		} catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@PostMapping(path = "/getBatteryInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getBatteryInfo(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return batteryService.getBatteryInfo(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/updateBatterySerialNumber", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  updateBatterySerialNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return batteryService.updateBatterySerialNumber(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/searchBatteryInvoice", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  searchBatteryInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return batteryInvoiceService.searchBatteryInvoice(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/searchBatteryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  searchBatteryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return batteryService.getSearchBatteryList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getBatteryForMount")
	public void getBatteryForMount(Map<String, Object> map, @RequestParam("term") final String term,@RequestParam("capacityId") final Long capacityId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BatteryDto>   batteryList	=	new ArrayList<>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			List<BatteryDto>   battery	= 	null;
			if(!(boolean)configuration.get("asignAnyCapacityBattery")) {
				battery	= batteryService.getBatteryListForDropdown(term, userDetails.getCompany_id(), capacityId, userDetails.getId());
			}else {
				battery	= batteryService.getBatteryListForDropdown(term, userDetails.getCompany_id(), userDetails.getId());
			}
			
			if(battery != null && !battery.isEmpty()) {
				for(BatteryDto batteryDto : battery) {
					batteryList.add(batteryDto);
				}
			}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(batteryList));
	}
	@PostMapping(path = "/getBatteryForScrap")
	public void getBatteryForScrap(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BatteryDto>   batteryList	=	new ArrayList<>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<BatteryDto> 	 battery	= batteryService.getBatteryListForDropdown(term, userDetails.getCompany_id(), userDetails.getId());
			if(battery != null && !battery.isEmpty()) {
				for(BatteryDto batteryDto : battery) {
					batteryList.add(batteryDto);
				}
			}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(batteryList));
	}
	@PostMapping(path = "/getBatteryForTransfer")
	public void getBatteryForTransfer(Map<String, Object> map, @RequestParam("term") final String term,@RequestParam("fromLocation") final Integer fromLocation,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BatteryDto>   batteryList	=	new ArrayList<>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<BatteryDto>   battery	= batteryService.getBatteryListForDropdown(term, userDetails.getCompany_id(), fromLocation);
			if(battery != null && !battery.isEmpty()) {
				for(BatteryDto batteryDto : battery) {
					batteryList.add(batteryDto);
				}
			}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(batteryList));
	}
	
	@GetMapping(path = "/getBatteryCapacity")
	public void getBatteryCapacity(@RequestParam(value = "ModelType", required = true) Long BatteryId,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BatteryDto	batteryDto	= new BatteryDto();
		BatteryDto	list	=	batteryService.getBatteryCapacity(BatteryId);
		 if(list != null) {
			 batteryDto  = list;
			}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(batteryDto));
	}
	
	@PostMapping(path = "/viewBatteryHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  viewBatteryHistory(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return batteryService.viewBatteryHistory(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	/** This controller request in Add Tyre Inventory list */
	@GetMapping(path = "/ScrapBatteryFilter")
	public ModelAndView ScrapFilter(Model modelAdd, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("ScrapBatteryFilter", model);
	}

	@PostMapping(path = "/SearchBatteryScrapFilter")
	public ModelAndView SearchScrapFilter(@RequestParam("batteryIds") final String batteryIds,
			@RequestParam("usesNoOfTimes") final String batteryUsage,
			@RequestParam("wareHouseLocationId") final Integer WAREHOUSE_LOCATION, BatteryDto batteryDto,
			BindingResult result) {

		// PurchaseOrders POReport = RBL.preparePurchaseOrder(purchaseOrders);

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (batteryUsage != "" && batteryUsage != null) {
				String TYREID_Mutiple = "", location = "", usage = "";
				if (batteryIds != "") {
					TYREID_Mutiple = "AND BA.batteryId IN (" + batteryIds + ")";
				}

				if (WAREHOUSE_LOCATION != null && WAREHOUSE_LOCATION > 0) {
					location = "AND BA.wareHouseLocationId =" + WAREHOUSE_LOCATION + "";
				}
				if (batteryUsage != "" && batteryUsage != null) {
					String[] usagerFromTo = batteryUsage.split(",");
					usage = "AND BA.usesNoOfTime BETWEEN " + usagerFromTo[0] + " AND " + usagerFromTo[1] + "";
				}

				String Query = TYREID_Mutiple + " " + location + " " + usage;
				List<BatteryDto> pageList = batteryService.getBatteryListToScrap(Query, userDetails.getCompany_id());

				model.put("Battery", pageList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("Report_BatteryScrapFilter", model);

	}
	
	@PostMapping(path = "/saveBatteryScrapInfo")
	public ModelAndView saveBatteryScrapInfo(@RequestParam("ScrapDescription") final String ScrapDescription,
			@RequestParam("batteryIds") final String[] batteryIds,@RequestParam("ScrapReason") final String[] ScrapReasons,
			Model modelAdd, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// getting Fuel ID in Multiple Format
			String Multiple_TYRE_id = "";
			if (batteryIds != null) {
				int i 	= 1;
				int j	= 0;
				String scrapReasons = "";
				// save to select Tyre to Scraped process
				for (String batteryIdSelect : batteryIds) {

					if (batteryIdSelect != null) {
						BatteryDto battery = batteryService.getBatteryInfo(Long.parseLong(batteryIdSelect), userDetails.getCompany_id());
						
						
							if(ScrapReasons[j] != null) {
								scrapReasons	= ScrapReasons[j];
							} else {
								
								scrapReasons	= "";
							}
							
							j++;
						BatteryHistory batteryHistory = prepareBatteryHistoryToScrap(battery,ScrapDescription,scrapReasons);
						batteryHistory.setCompanyId(userDetails.getCompany_id());
						
						batteryHistoryService.save(batteryHistory);

						if (i != batteryIds.length) {
							Multiple_TYRE_id += batteryIdSelect + ",";
						} else {
							Multiple_TYRE_id += batteryIdSelect + "";
						}
						i++;

					}
					
					batteryService.updateScrapRemark(Long.parseLong(batteryIdSelect), scrapReasons);

				} // close For loop to save Retread Amount

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp scropedDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			

				batteryService.Update_Inventory_Battery_ScropStatus(userDetails.getId(), scropedDate, BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SCRAPED,
						Multiple_TYRE_id, userDetails.getCompany_id());
				/*
				 * modelAdd.addAttribute("successTyre", "" + countSAVE);
				 * modelAdd.addAttribute("DuplicateTyre", "" + countDuplicate);
				 * 
				 */
				model.put("ScrapSuccess", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/BatteryInventory.in?danger=true");
		}finally {
			userDetails = null;
		}

		return new ModelAndView("redirect:/BatteryInventory.in", model);
	}

	private BatteryHistory prepareBatteryHistoryToScrap(BatteryDto batteryDto, String decription,String ScrapReason) {

		BatteryHistory batteryHistory = new BatteryHistory();

		batteryHistory.setBatteryId(batteryDto.getBatteryId());
		batteryHistory.setBatterySerialNumber(batteryDto.getBatterySerialNumber());

		batteryHistory.setVid(0);

		batteryHistory.setLayoutPosition((short) 0);
		batteryHistory.setBatteryStatusId(BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SCRAPED);
		batteryHistory.setOpenOdometer(null);
		batteryHistory.setBatteryUseage(batteryDto.getBatteryUsesOdometer());
		batteryHistory.setUsesNoOfDay(batteryDto.getUsesNoOfTime());
		batteryHistory.setBatteryAsignDate(DateTimeUtility.getCurrentTimeStamp());
		batteryHistory.setBatteryComment(decription);
		batteryHistory.setScrapreason(ScrapReason);

		return batteryHistory;
	}

	@GetMapping(path = "/multiTransferBatteryInventory")
	public ModelAndView multiTransferBatteryInventory(Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				model.put("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

		} catch (Exception e) {
		}
		return new ModelAndView("addMultiTransferBatteryInventory", model);
	}
	
	@PostMapping(path = "/saveMultiTransferBatteryInventory")
	public ModelAndView saveMultiTransferBatteryInventory(
			@ModelAttribute("command") BatteryTransferDto batteryTransfer,
			@RequestParam("batteryIds") final String batteryIds,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails			= null;
		String[]					batteryIdArr		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			batteryIdArr	= batteryIds.split(",");
			if(batteryIdArr != null) {
			for(int i = 0 ; i< batteryIdArr.length ; i++) {
					Battery battery = batteryService.getBattery(Long.parseLong(batteryIdArr[i]+""));

			if(battery != null) {
			
			List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));


			// check user branch location and workOrder location if same
			if (INVBL.isFilled_GET_Permission(batteryTransfer.getFromLocationId(), PartLocPermission)
					&& battery.getBatteryStatusId() == InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE) {
					
				if (userDetails.getId() != batteryTransfer.getReceiveById()) {
					
					BatteryTransfer createTransferIn = prepareBatteryTransfer(battery, batteryTransfer);
					try {
						
						batteryTransferService.registerTransfer(createTransferIn);
					
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/multiTransferBatteryInventoryin?danger=true");
					}
				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/multiTransferBatteryInventory.in");
				}
				
			} else {
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/multiTransferBatteryInventory.in?NoAuthen=true");
			}
		}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/multiTransferBatteryInventory.in?danger=true");
		}finally {
			userDetails	= null;
		}
		return new ModelAndView("redirect:/multiTransferBatteryInventory.in?saveTransferInventory=true");
	}
	public BatteryTransfer prepareBatteryTransfer(Battery batteryTransfer,
			BatteryTransferDto batteryTransferDto) {

		BatteryTransfer transfer = new BatteryTransfer();

		transfer.setBatteryId(batteryTransfer.getBatteryId());

		transfer.setFromLocationId(batteryTransferDto.getFromLocationId());
		transfer.setToLocationId(batteryTransferDto.getToLocationId());
		transfer.setTransferQuantity(1);
		transfer.setTransferViaId(batteryTransferDto.getTransferViaId());

		transfer.setTransferDate(DateTimeUtility.getCurrentTimeStamp());

		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		transfer.setTransferReason(batteryTransferDto.getTransferReason());
		transfer.setBatteryInvoiceId(batteryTransfer.getBatteryInvoiceId());
		transfer.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
		transfer.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
		transfer.setCreatedById(userDetails.getId());
		transfer.setLastModifiedById(userDetails.getId());
		transfer.setCompanyId(userDetails.getCompany_id());
		transfer.setTransferById(userDetails.getId());
		transfer.setReceiveById(batteryTransferDto.getReceiveById());
		transfer.setTransferStausId(InventoryTyreTransferDto.TRANSFER_STATUS_TRANSFER);


		return transfer;
	}
	
	@GetMapping(path = "/receiveMultipleBatteryTransfered")
	public ModelAndView receiveMultipleBatteryTransfered(Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<BatteryTransferDto> show_Location = batteryTransferService.getBatteryTransferListToReceive(userDetails);
			model.put("batteryTransfer", show_Location);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showMultiTransferBatteryInventory", model);
	}

	@PostMapping(path = "/receiveTransferBatteryInventory")
	public ModelAndView receiveTransferBatteryInventory(@RequestParam("batteryId") final Long batteryId,
			@RequestParam("batteryTransferId") final Long batteryTransferId,
			@RequestParam("receiveBy") final String receiveBy,
			@RequestParam("receiveRemark") final String receiveRemark,
			@RequestParam("receiveById") final Long receiveById,
			final HttpServletRequest request) {
		CustomUserDetails	userDetails		= null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			BatteryTransfer batteryTransfer = batteryTransferService.getBatteryTransfer(batteryTransferId, userDetails.getCompany_id());
			
			if(batteryTransfer != null) {
				
				// check user branch location and workOrder location if same
				if (userDetails.getId() == batteryTransfer.getReceiveById()) {
					
					try {
						
						batteryService.Update_Transfer_Location_InventoryBattery(batteryTransfer.getToLocationId(),
								batteryTransfer.getBatteryId(), userDetails.getCompany_id());
						// get Current days
						java.util.Date currentDate = new java.util.Date();
						Timestamp Transfer_receiveddate = new java.sql.Timestamp(currentDate.getTime());
						
						batteryTransferService.Update_BatteryTransfer_ReceivedBy_Details(Transfer_receiveddate,
								receiveRemark, userDetails.getId(), Transfer_receiveddate,
								batteryTransferId, userDetails.getCompany_id());
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/receiveMultipleBatteryTransfered.in?danger=true");
					}
					
				} else {
					return new ModelAndView("redirect:/receiveMultipleBatteryTransfered.in?danger=true");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/BatteryInventory.in?danger=true");
		}finally {
			userDetails = null;
		}
		return new ModelAndView("redirect:/receiveMultipleBatteryTransfered.in?received=true");
	}
	
	@GetMapping(path = "/downloadinventoryBatterydocument")
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
		}
		return null;
	}
	
	@GetMapping(path = "/downloadbatteryinventorydocument")
	public String downloadbatteryinventorydocument(HttpServletResponse response) {

		XSSFWorkbook 				hssfWorkbook					= null;
		XSSFSheet 					hssfSheet						= null;
		XSSFRow 					hssfRow							= null; 
		DataValidationHelper 		validationHelper 				= null;
		List<BatteryManufacturer>   manufacturerList				= null;
		List<BatteryType>           batteryTypeList					= null;
		List<BatteryCapacity> 		batteryCapacityList				= null;
		try {
			hssfWorkbook				= new XSSFWorkbook();
			hssfSheet					=(XSSFSheet) hssfWorkbook.createSheet("sheet1");
			XSSFRow rowZero 			= hssfSheet.createRow((short) 0);
			hssfRow 					= hssfSheet.createRow((short) 1);
			manufacturerList			= batteryManufacturerService.findAll();
			batteryTypeList				= batteryTypeService.findAll();
			batteryCapacityList			= batteryCapacityService.getBatteryCapacityList();
			
			

			    validationHelper=new XSSFDataValidationHelper(hssfSheet);
			    hssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,10));
			    
			    rowZero.createCell(0).setCellValue("Note : (*) marked filled are mandatory. Please Add All Battery Of Same Manufacuture Do not mix with multiple Manufacuture! ");
			    
			    hssfRow.createCell(0).setCellValue("Battery Number (*)");
			    hssfRow.createCell(1).setCellValue("Battery Manufacturer (*)");
				hssfRow.createCell(2).setCellValue("Battery Model (*)");
				hssfRow.createCell(3).setCellValue("Capacity (*)");
			//	hssfRow.createCell(4).setCellValue("Warehouse Location (*)");
				hssfRow.createCell(4).setCellValue("Unit Cost (*)");
				
				
			    /*
			     * this line will add battery manufacturer dropdown in excel from 2 to 50 row first column
			     */
			    hssfSheet.addValidationData(getDropDownList(2,50,1,1, getBatteryManufacturerArray(manufacturerList), validationHelper));
				
			    /*
			     * this line will add battery type dropdown in excel from 2 to 50 row second column
			     */
			    hssfSheet.addValidationData(getDropDownList(2,50,2,2, getBatteryTypeArray(batteryTypeList), validationHelper));
				  
			    /*
			     * this line will add battery capacity dropdown in excel from 2 to 50 row third column
			     */
			    hssfSheet.addValidationData(getDropDownList(2,50,3,3, getBatteryCapacityArray(batteryCapacityList), validationHelper));
			    
			   
			 //   hssfSheet.addValidationData(getDropDownList(2,50,4,4, getPartLocationArray(partLocation), validationHelper));
					
				
			    
			    for (int i=0; i<10; i++){
			    	hssfSheet.autoSizeColumn(i);
			    }

			FileOutputStream fileOut = new FileOutputStream(new File("BatteryInventory.xlsx"));
			hssfWorkbook.write(fileOut);
			fileOut.close();

			//Code to download
			File fileToDownload = new File("BatteryInventory.xlsx");
			InputStream in = new FileInputStream(fileToDownload);

			// Gets MIME type of the file
			String mimeType = new MimetypesFileTypeMap().getContentType("BatteryInventory.xlsx");

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
	
 private String []  getBatteryManufacturerArray(List<BatteryManufacturer> list) {
	List<String>  				maStrings 			= null;	
	try {
			maStrings = new ArrayList<>();
			for(BatteryManufacturer 	manufacturer : list){
				  maStrings.add(manufacturer.getManufacturerName());
			}
			return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
		} catch (Exception e) {
			throw e;
		}finally {
			maStrings 			= null;
		}
	}
 
 private String []  getBatteryTypeArray(List<BatteryType> list) {
		List<String>  				maStrings 			= null;	
		try {
				maStrings = new ArrayList<>();
				for(BatteryType 	batteryType : list){
					  maStrings.add(batteryType.getBatteryType());
				}
				return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
			} catch (Exception e) {
				throw e;
			}finally {
				maStrings 			= null;
			}
 }
 
 private String []  getBatteryCapacityArray(List<BatteryCapacity> list) {
		List<String>  				maStrings 			= null;	
		try {
				maStrings = new ArrayList<>();
				for(BatteryCapacity 	batteryCapacity : list){
					  maStrings.add(batteryCapacity.getBatteryCapacity());
				}
				return  Arrays.copyOf(maStrings.toArray(), maStrings.size(), String[].class);
			} catch (Exception e) {
				throw e;
			}finally {
				maStrings 			= null;
			}
 }
 
 	@SuppressWarnings({ "resource", "deprecation" })
 	@PostMapping(path = "/importInventoryBattery")
	public ModelAndView importInventoryBattery(@ModelAttribute("command") VehicleDocument photo,
			@RequestParam("import") MultipartFile file,@RequestParam("location") Integer locationId, HttpServletRequest request) throws Exception {

		Map<String, Object> 				model 						= null; 
		CustomUserDetails					userDetails					= null;
		String 								rootPath 					= null;
		File 								dir 						= null;
		File 								serverFile 					= null;
		FileInputStream 					fis 						= null;
		XSSFWorkbook 						myWorkBook 					= null; 
		XSSFSheet 							mySheet 					= null;
		Iterator<Row> 						rowIterator 				= null; 
		BatteryInvoice 						batteryInvoice				= null;
		Double 								totalCost 					= 0.0;
		int 								noOfRows					= 0;
		int 								valuesInserted				= 0;
		int 								valuesRejected				= 0;
		List<BatteryManufacturer>   		manufacturerList				= null;
		List<BatteryType>           		batteryTypeList					= null;
		List<BatteryCapacity> 				batteryCapacityList				= null;
		List<Battery> 						batteryList						= null;
		Battery								battery							= null;
		HashMap<String, List<Battery>>      batteryHM						= null;
		List<Battery>						dummyList						= null;
		try {
			model 					= new HashMap<String, Object>();
			batteryList				= new ArrayList<>();
			batteryHM				= new HashMap<>();
			rootPath 				= request.getSession().getServletContext().getRealPath("/");
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			manufacturerList			= batteryManufacturerService.findAll();
			batteryTypeList				= batteryTypeService.findAll();
			batteryCapacityList			= batteryCapacityService.getBatteryCapacityList();

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
			
			batteryInvoice			= new BatteryInvoice();

			
			batteryInvoice.setPaymentNumber("Excel Entry");
			batteryInvoice.setPoNumber("Excel Entry");
			batteryInvoice.setInvoiceNumber("Excel Entry");
			batteryInvoice.setInvoiceDate(DateTimeUtility.getCurrentTimeStamp());
			batteryInvoice.setInvoiceAmount((double) 0);
			batteryInvoice.setPaymentTypeId(PaymentTypeConstant.PAYMENT_TYPE_CASH);
			batteryInvoice.setDescription("Excel Import Entry..");
			batteryInvoice.setCreatedById(userDetails.getId());
			batteryInvoice.setLastModifiedById(userDetails.getId());
			batteryInvoice.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			batteryInvoice.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
			batteryInvoice.setCompanyId(userDetails.getCompany_id());
			batteryInvoice.setVendorId(0);
			batteryInvoice.setBatteryInvoiceNumber((long) 0000);
			batteryInvoice.setWareHouseLocation(locationId);
			


			// Traversing over each row of XLSX file
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				noOfRows += 1;
				if(noOfRows == 1 || noOfRows == 2) {
					continue;
				}

								
				Cell cell	= null;
				String cellValue	= null;
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
						List<Battery>  validate	=	batteryService.validateBatterySerialNumber(cellValue.trim(), userDetails.getCompany_id());
						if(validate == null || validate.isEmpty()) {
							
							battery		= new Battery();
							
							battery.setBatterySerialNumber(cellValue.trim());
							battery.setBatteryUsesOdometer(0);
							battery.setUsesNoOfTime((long) 0);
							battery.setBatteryPurchaseDate(DateTimeUtility.getCurrentTimeStamp());
							battery.setBatteryStatusId(BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE);
							battery.setBatteryUsesStatusId(BatteryConstant.BATTERY_USES_STATUS_NEW);
							battery.setCreatedById(userDetails.getId());
							battery.setLastModifiedById(userDetails.getId());
							battery.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
							battery.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
							battery.setCompanyId(userDetails.getCompany_id());
							battery.setWareHouseLocationId(locationId);
							
							
						}else {
							valuesRejected ++;
							battery = null;
							continue;
						}
						} else {
							valuesRejected ++;
							battery = null;
							continue;
						}
					}
					
					if(battery != null) {

						if(i == 1) {
							if(cellValue != null && !cellValue.isEmpty()) {
								for (int j = 0; j < manufacturerList.size(); j++) {
									if(manufacturerList.get(j).getManufacturerName().equalsIgnoreCase(cellValue.trim())) {
										battery.setBatteryManufacturerId(manufacturerList.get(j).getBatteryManufacturerId());
										break;
									}
								}
								if(battery.getBatteryManufacturerId() == null || battery.getBatteryManufacturerId() <= 0) {
									battery		= null;
									break;
								}
							} else {
								battery		= null;
								break;
							}
						}
						if(i == 2) {
							if(cellValue != null && !cellValue.isEmpty()) {
								for (int j = 0; j < batteryTypeList.size(); j++) {
									if(batteryTypeList.get(j).getBatteryType().equalsIgnoreCase(cellValue.trim())) {
										battery.setBatteryTypeId(batteryTypeList.get(j).getBatteryTypeId());
										break;
									}
								}
								if(battery.getBatteryTypeId() == null || battery.getBatteryTypeId() <= 0) {
									battery		= null;
									break;
								}
							} else {
								battery		= null;
								break;
							}
						}
						if(i == 3) {
							if(cellValue != null && !cellValue.isEmpty()) {
								for (int j = 0; j < batteryCapacityList.size(); j++) {
									if(batteryCapacityList.get(j).getBatteryCapacity().equalsIgnoreCase(cellValue.trim())) {
										battery.setBatteryCapacityId(batteryCapacityList.get(j).getBatteryCapacityId());
										break;
									}
								}
								if(battery.getBatteryCapacityId() == null || battery.getBatteryCapacityId() <= 0) {
									battery		= null;
									break;
								}
							} else {
								battery		= null;
								break;
							}
						}
						
						if(i == 4) {
							if(cellValue != null && !cellValue.isEmpty()) {
								battery.setBatteryAmount(Double.parseDouble(cellValue.trim()));
							} else {
								battery.setBatteryAmount((double) 0);
							}
							
							totalCost += battery.getBatteryAmount();
						}
					}
					
				}
				if(battery != null) {
					batteryList.add(battery);
					valuesInserted ++;
				}
				
			}
			if(batteryList != null && batteryList.size() > 0) {
				batteryInvoice.setInvoiceAmount(totalCost);
				
				batteryInvoiceService.registerInvoice(batteryInvoice);
				
				final Long invoiceId = batteryInvoice.getBatteryInvoiceId();
				
				for(Battery battery2 : batteryList) {
					dummyList	= batteryHM.get(battery2.getBatteryManufacturerId()+"_"+battery2.getBatteryTypeId()+"_"+battery2.getBatteryCapacityId());
					
					if(dummyList == null) {
						dummyList = new ArrayList<>();
						dummyList.add(battery2);
					}else {
						dummyList.add(battery2);
					}
					batteryHM.put(battery2.getBatteryManufacturerId()+"_"+battery2.getBatteryTypeId()+"_"+battery2.getBatteryCapacityId(), dummyList);
				}
				batteryHM.forEach((key, value) -> {
					BatteryAmount		batteryAmount;
					List<Battery>   list	= value;
					batteryAmount		= new BatteryAmount();
					batteryAmount.setBatteryAsignNumber(0);
					batteryAmount.setBatteryManufacturerId(list.get(0).getBatteryManufacturerId());
					batteryAmount.setBatteryTypeId(list.get(0).getBatteryTypeId());
					batteryAmount.setBatteryCapacityId(list.get(0).getBatteryCapacityId());
					batteryAmount.setBatteryQuantity(Long.parseLong(list.size()+""));
					batteryAmount.setUnitCost(list.get(0).getBatteryAmount());
					batteryAmount.setDiscount((double) 0);
					batteryAmount.setTax((double) 0);
					batteryAmount.setTotalAmount((double) 0);
					batteryAmount.setWareHouseLocation(locationId);
					batteryAmount.setBatteryInvoiceId(invoiceId);
					batteryAmount.setCompanyId(list.get(0).getCompanyId());
					
					try {
						batteryAmountService.registerBatteryAmount(batteryAmount);
					} catch (Exception e) {
						e.printStackTrace();
					}
					for(Battery battery3 : value) {
						try {
							batteryAmount.setTotalAmount(battery3.getBatteryAmount() + batteryAmount.getTotalAmount());
							battery3.setBatteryAmountId(batteryAmount.getBatteryAmountId());
							battery3.setBatteryInvoiceId(invoiceId);
							batteryService.saveBattery(battery3);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					try {
						batteryAmountService.updateTotalAmountToBatteryAmount(batteryAmount.getTotalAmount(), batteryAmount.getBatteryAmountId(), batteryAmount.getCompanyId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
			
			
			model.put("valuesInserted", valuesInserted);
			model.put("valuesRejected", valuesRejected);
			model.put("tyreSaved", true);
			
			return new ModelAndView("redirect:/BatteryInventory.in", model);
		} catch (Exception e) {
			throw e;
		}
	}
 	
 	@GetMapping(path = "/PrintBatteryInventory")
	public ModelAndView PrintBatteryInventory(@RequestParam("batteryId") final Integer batteryId,
			final HttpServletResponse resul) {
 			Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("batteryId", batteryId);
			
			
		} catch (Exception e) {
			
			/*LOGGER.error("Work Order:", e);*/
		}
		return new ModelAndView("PrintBatteryInventoryPrint", model);
	}
 	
 	@PostMapping(path = "/getBatteryCountList", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getBatteryCountList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return batteryService.getBatteryCountListDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
 	
 	@PostMapping(path = "/getVendorSearchListOnTripExpense")
	public void getVendorSearchListOnTripExpense(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= null;
		try {
			List<Vendor> vendorList =	new ArrayList<>();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Vendor> vendor = vendorService.SearchOnly_All_VendorNameOnTripExpense(term, userDetails.getCompany_id());
			if(vendor != null && !vendor.isEmpty()) {
				for(Vendor vendor2 : vendor) {
					vendorList.add(vendor2);
				}
			}
			
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(vendorList));
		} catch (Exception e) {
			throw e;
		}
	}
 	
 	@GetMapping(path = "/download/BatteryInvoiceDocument/{batteryDocumentId}")
	public String  downloadBatteryInvoiceDocument(@PathVariable("batteryDocumentId") Long batteryDocumentId, HttpServletResponse response) throws Exception {
		CustomUserDetails 	userDetails 	=  null;
		try {
			if (batteryDocumentId <= 0) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.BatteryInvoiceDocument document = batteryService.getBatteryInvoiceDocumentDetails(batteryDocumentId, userDetails.getCompany_id());
				if (document != null) {
					if (document.getBatteryInvoiceContent() != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + document.getBatteryInvoiceFileName() + "\"");
						OutputStream  out = response.getOutputStream();
						response.setContentType(document.getBatteryInvoiceContentType());
						response.getOutputStream().write(document.getBatteryInvoiceContent());
						out.flush();
						out.close();

					}
				}
			}

		}catch (Exception e) {
			throw e;
		}
		return null;
	}
 	
 	@PostMapping(path = "/getBatteryListTransfer")
 	public HashMap<Object, Object> getBatteryListForTransfer(@RequestParam HashMap <Object, Object> map) throws Exception {
 		try {
 			ValueObject object =  new ValueObject(map);
 			ValueObject valueOutobject = new ValueObject();
 			List<BatteryDto> battary = batteryService.getBatteryListDropdown(object.getString("term",""), object.getInt("companyId",0) , object.getInt("fromLocation",0),object.getLong("size",0)); 
 			if( battary != null && !battary.isEmpty())
 				valueOutobject.put("data",battary);
 			return valueOutobject.getHtData();
 		} catch (Exception e) {
 			e.printStackTrace();
 			throw e;
 		}
 	}
 	
 	@GetMapping(path = "/getMoveToRepairBattery")
	public void getMoveToRepairBattery(@RequestParam("fromLocationId") final Integer fromLocationId, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
 		List<BatteryDto>   batteryList	= null;
 		CustomUserDetails	userDetails	= null;
 		try {
 			 batteryList	=	new ArrayList<>();
 	 		 userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 	 		 batteryList	= batteryService.getMoveToRepairBattery(userDetails.getCompany_id(),fromLocationId);
 	 		 Gson gson = new Gson();
 	 		 response.getWriter().write(gson.toJson(batteryList));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
 	@GetMapping(path = "/getLocationWiseBatteryList")
 	public void getTyreLocationWiseBatteryList(@RequestParam("fromLocationId") final Integer fromLocationId, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
 		List<BatteryDto>   batteryList	= null;
 		CustomUserDetails	userDetails	= null;
 		try {
 			batteryList	=	new ArrayList<>();
 			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 			batteryList	= batteryService.getLocationWiseBatteryList(userDetails.getCompany_id(),fromLocationId);
 			Gson gson = new Gson();
 			response.getWriter().write(gson.toJson(batteryList));
 		} catch (Exception e) {
 			e.printStackTrace();
 			throw e;
 		}
 	}
}

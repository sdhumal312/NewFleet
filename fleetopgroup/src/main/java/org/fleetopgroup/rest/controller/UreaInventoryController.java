package org.fleetopgroup.rest.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.UreaConfigurationConstant;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.UreaInvoiceDto;
import org.fleetopgroup.persistence.dto.UreaInvoiceToDetailsDto;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.UreaInvoice;
import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceToDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IUreaManufacturerService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class UreaInventoryController {
	
	@Autowired IPartLocationsService					partLocationsService;
	@Autowired IUreaInventoryService					ureaInventoryService;
	@Autowired IUreaManufacturerService					ureaManufacturerService;
	@Autowired IUreaInvoiceToDetailsService				ureaInvoiceToDetailsService;
	@Autowired ICompanyConfigurationService				companyConfigurationService;
	@Autowired MongoOperations							mongoOperations;
	@Autowired private  IPendingVendorPaymentService	pendingVendorPaymentService;
	@Autowired IBankPaymentService  					bankPaymentService;
	@Autowired IVendorService							vendorService;
	
	PartLocationsBL PLBL = new PartLocationsBL();
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/UreaInventory", method = RequestMethod.GET)
	public ModelAndView UreaInventory(HttpServletRequest request) throws Exception {
		ModelAndView map = new ModelAndView("AddUreaInventory");
		boolean						showSubLocation		= false;
		String						mainLocationIds		= "";
		try {
			
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.UREA_CONFIGURATION_CONFIG);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			showSubLocation	 	= (boolean)configuration.getOrDefault(UreaConfigurationConstant.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(UreaConfigurationConstant.MAIN_LOCATION_IDS, "")+"";
			map.addObject("PartLocations", PLBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			map.addObject("permissions", permission);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("configuration", configuration);
			map.addObject("showSubLocation", showSubLocation);
			map.addObject("mainLocationIds", mainLocationIds);
			map.addObject("accessToken", Utility.getUniqueToken(request));
		
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/saveUreaInventoryDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveUreaInventoryDetails(@RequestParam("ureaInvoiceData") String allRequestParams,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject();
			object = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			object.put("ureaDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("ureaDetails")));
			return ureaInventoryService.saveUreaInventoryDetails(object,uploadfile).getHtData();
		//	object.put("ureaDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("ureaDetails")));
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getPageWiseUreaInvoiceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPageWiseUreaInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return ureaInventoryService.getUreaInvoiceList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/showUreaInvoice", method = RequestMethod.GET)
	public ModelAndView showClothInvoice(@RequestParam("Id") final Long invoiceId,final HttpServletRequest request) {
		ModelAndView 					map 			= new ModelAndView("Show_UreaInventory");//Jsp
		UreaInvoice	 					ureaInvoice		= null;
		CustomUserDetails				userDetails		= null;
		Collection<GrantedAuthority>	permission		= null;
		boolean							showSubLocation = false;
		try {
			
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.UREA_CONFIGURATION_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(UreaConfigurationConstant.SHOW_SUB_LOCATION, false);
			permission		= userDetails.getGrantedAuthoritiesList();
			ureaInvoice 	= ureaInventoryService.getUreaInvoiceByInvoiceId(invoiceId,userDetails.getCompany_id());
			
			map.addObject("permissions", permission);
			map.addObject("invoiceId",invoiceId);
			map.addObject("ureaInvoice",ureaInvoice);
			map.addObject("configuration", configuration);
			map.addObject("showSubLocation", showSubLocation);
			map.addObject("companyId", userDetails.getCompany_id());
			
			
			
		}catch (Exception e) {
			System.err.println("ERR"+e);
		}
		return map;
	}
	
	@RequestMapping(value = "/getUreaInvoiceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUreaInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return ureaInventoryService.getUreaInvoiceDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/deleteUreaInvoice", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deleteUreaInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return ureaInventoryService.deleteUreaInvoice(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/deleteUreaInventoryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deleteUreaInventoryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			return ureaInventoryService.deleteUreaInventoryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchUreaInvoiceByNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchUreaInvoiceByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return ureaInventoryService.searchUreaInvoiceByNumber(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}

	@RequestMapping(value = "/searchUreaInvoice", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchUreaInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return ureaInventoryService.searchUreaInvoice(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/locationStockDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  locationStockDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return ureaInventoryService.locationStockDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/editUreaInvoice", method = RequestMethod.GET)
	public ModelAndView editUreaInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ModelAndView map = new ModelAndView("EditUreaInventory");
		ValueObject 		object 					= null;
		UreaInvoiceDto		ureaInvoiceDto			= null;
		CustomUserDetails	userDetails				= null;
		boolean						showSubLocation		= false;
		String						mainLocationIds		= "";
		try { 
			object 				= new ValueObject(allRequestParams);
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.UREA_CONFIGURATION_CONFIG);
			ureaInvoiceDto = ureaInventoryService.getUreaInvoiceDetails(object.getLong("Id"), userDetails.getCompany_id());
			map.addObject("ureaInvoiceDto", ureaInvoiceDto);
			map.addObject("configuration", configuration);
			showSubLocation	 	= (boolean)configuration.getOrDefault(UreaConfigurationConstant.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(UreaConfigurationConstant.MAIN_LOCATION_IDS, "")+"";
			map.addObject("showSubLocation", showSubLocation);
			map.addObject("mainLocationIds", mainLocationIds);
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/updateUreaInventory", method = RequestMethod.POST)
	public ModelAndView updateUreaInventory(@ModelAttribute UreaInvoiceDto ureaInvoiceDto, @RequestParam("input-file-preview") MultipartFile file, 
			@ModelAttribute BankPaymentDto bankPaymentDto,final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails					= null;
		UreaInvoice 				ureaInvoice					= null;
		List<UreaInvoiceToDetails> 	ureaInvoiceToDetailsLsit	= null;	
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (ureaInvoiceDto.getUreaInvoiceId()!= null) {
				ureaInvoice = ureaInventoryService.getUreaInvoiceByInvoiceId(ureaInvoiceDto.getUreaInvoiceId(), userDetails.getCompany_id());
				
				short oldPaymentTypeId = ureaInvoice.getPaymentTypeId();
				
				ureaInvoiceDto.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
				ureaInvoiceDto.setLastModifiedById(userDetails.getId());
				ureaInvoiceDto.setInvoiceDate(DateTimeUtility.getDateTimeFromStr(ureaInvoiceDto.getInvoiceDateStr(), DateTimeUtility.DD_MM_YYYY));
				ureaInvoiceDto.setCompanyId(userDetails.getCompany_id());
				if (file != null && !file.isEmpty() && file.getSize() > 0) {
					ureaInvoiceDto.setUrea_document(true);
				}else if(ureaInvoice.isUrea_document()) {
					ureaInvoiceDto.setUrea_document(true);
				}else {
					ureaInvoiceDto.setUrea_document(false);
				}
				if(ureaInvoiceDto.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					ureaInvoiceDto.setVendorPaymentStatus(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
				}else {
					ureaInvoiceDto.setVendorPaymentStatus(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
				}
				
				ureaInventoryService.updateUreaInvoice(ureaInvoiceDto);
				
				ureaInvoiceToDetailsLsit = ureaInvoiceToDetailsService.getUreaInvoiceToDetailsByUreaInvoiceId(ureaInvoice.getUreaInvoiceId());
				
				if(ureaInvoiceToDetailsLsit != null && !ureaInvoiceToDetailsLsit.isEmpty()) {
					ureaInvoiceToDetailsService.updateSubLocationInUreaInvoiceToDetails(ureaInvoiceDto.getSubLocationId(),ureaInvoice.getUreaInvoiceId(),userDetails.getCompany_id());
				}
				
				PendingVendorPayment	payment		= null;
				if(oldPaymentTypeId != ureaInvoiceDto.getPaymentTypeId() && ureaInvoiceDto.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForUIEdit(ureaInvoiceDto);
					pendingVendorPaymentService.savePendingVendorPayment(payment);
				}else if(oldPaymentTypeId == ureaInvoiceDto.getPaymentTypeId() && ureaInvoiceDto.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForUIEdit(ureaInvoiceDto);
					pendingVendorPaymentService.updatePendingVendorPayment(payment);
				}else if(oldPaymentTypeId != ureaInvoiceDto.getPaymentTypeId() && ureaInvoiceDto.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					pendingVendorPaymentService.deletePendingVendorPayment(ureaInvoice.getUreaInvoiceId(), PendingPaymentType.PAYMENT_TYPE_UREA_INVOICE);
				}
				
				if(bankPaymentDto.isAllowBankPaymentDetails()) {
				ValueObject bankPaymentValueObject=new ValueObject();
				bankPaymentValueObject.put("oldPaymentTypeId", oldPaymentTypeId);
				bankPaymentValueObject.put("currentPaymentTypeId",ureaInvoiceDto.getPaymentTypeId());
				bankPaymentValueObject.put("userId", userDetails.getId());
				bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId",ureaInvoice.getUreaInvoiceId());
				bankPaymentValueObject.put("moduleNo", ureaInvoice.getUreaInvoiceNumber());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.UREA_INVENTORY);
				bankPaymentValueObject.put("amount",ureaInvoiceDto.getInvoiceAmount());
				
				Vendor	vendor	= vendorService.getVendor(ureaInvoice.getVendorId());
				
				bankPaymentValueObject.put("remark","Urea Invoice Edit On UI-"+ureaInvoice.getUreaInvoiceNumber()+" Vendor : "+vendor.getVendorName()+"  ");
				
				bankPaymentService.updatePaymentDetails(bankPaymentValueObject, bankPaymentDto);
				}
				
				if (file != null && !file.isEmpty() && file.getSize() > 0) {
					org.fleetopgroup.persistence.document.UreaInvoiceDocument ureaDoc = new org.fleetopgroup.persistence.document.UreaInvoiceDocument();
					ureaDoc.setUreaInvoiceId(ureaInvoiceDto.getUreaInvoiceId());
					try {
							byte[] bytes = file.getBytes();
							ureaDoc.setUreaInvoiceFileName(file.getOriginalFilename());
							ureaDoc.setUreaInvoiceContent(bytes);
							ureaDoc.setUreaInvoiceContentType(file.getContentType());

							ureaDoc.setMarkForDelete(false);

							ureaDoc.setCreatedById(userDetails.getId());
							ureaDoc.setLastModifiedById(userDetails.getId());

							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

							ureaDoc.setCreated(toDate);
							ureaDoc.setLastupdated(toDate);
							ureaDoc.setCompanyId(userDetails.getCompany_id());
						} catch (IOException e) {
							e.printStackTrace();
						}
					try {
						org.fleetopgroup.persistence.document.UreaInvoiceDocument doc = ureaInventoryService.getUreaInvoiceDocumentDetailsByInvoiceId(ureaInvoiceDto.getUreaInvoiceId(), userDetails.getCompany_id());
						if (doc != null) {
							ureaDoc.set_id(doc.get_id());
							mongoOperations.save(ureaDoc);
							ureaInventoryService.updateUreaInvoiceDocumentId(ureaDoc.get_id(), true,ureaInvoiceDto.getUreaInvoiceId(), userDetails.getCompany_id());
							
						}else {
							ureaInventoryService.addUreaInvoiceDocument(ureaDoc);
							ureaInventoryService.updateUreaInvoiceDocumentId(ureaDoc.get_id(), true,ureaInvoiceDto.getUreaInvoiceId(), userDetails.getCompany_id());
						}
						
					} catch (ConverterNotFoundException e) {
						System.err.println("catch");
						return new ModelAndView("redirect:/UreaInventory.in", model);
					}
				}

				model.put("UpdateSuccess", true);
			} else {
				return new ModelAndView("redirect:/UreaInventory.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/UreaInventory.in?danger=true");
		}
		return new ModelAndView("redirect:/showUreaInvoice?Id=" + ureaInvoiceDto.getUreaInvoiceId(), model);
	}
	
	@RequestMapping(value = "/addMoreUrea", method = RequestMethod.POST)
	public ModelAndView addMoreUrea(@ModelAttribute("command") UreaInvoiceDto ureaInvoiceDto,
			@RequestParam("InvoiceId") final Long invoiceId, 
			@RequestParam("vendorId") final Long vendorId, 
			@RequestParam("locationId") final Long locationId, 
			@RequestParam("ureaManufacturer") final String[] ureaManufacturer, 
			@RequestParam("quantity_many") final String[] quantity,
			@RequestParam("unitprice_many") final String[] unitprice,
			@RequestParam("discount_many") final String[] discount, 
			@RequestParam("tax_many") final String[] tax,
			@RequestParam("tatalcost") final String[] tatalcost,
			final HttpServletRequest request) {
		
		ValueObject	 					valueObject		= null;
		try {
			valueObject = new ValueObject();
			
			valueObject.put("InvoiceId", invoiceId);
			valueObject.put("vendorId", vendorId);
			valueObject.put("locationId", locationId);
			valueObject.put("ureaInvoiceDto", ureaInvoiceDto);
			valueObject.put("ureaManufacturer", ureaManufacturer);
			valueObject.put("quantity", quantity);
			valueObject.put("unitprice", unitprice);
			valueObject.put("discount", discount);
			valueObject.put("tax", tax);
			valueObject.put("tatalcost", tatalcost);
			
			
			ureaInventoryService.addMoreUreaInventoryInvoice(valueObject);

		} catch (Exception e) {
			//LOGGER.error("Add Tyre Inventory Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInvoiceList/1.in?danger=true");
		}
		return new ModelAndView("redirect:/UreaInventory.in?danger=true");
	}
	//check
	@RequestMapping(value = "/addUreaTypes", method = RequestMethod.GET)
	public ModelAndView addUreaTypes(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		
		ModelAndView map = new ModelAndView("addUreaTypes");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
		}  catch (Exception e) {
			LOGGER.error("Exception addUreaTypes : ", e);
			throw e;
		}
		return map;
	}	

	@RequestMapping(value = "/saveUreaTypes", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveUreaTypes(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);				
			return ureaManufacturerService.saveUreaTypes(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getUreaTypesList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUreaTypesList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return ureaManufacturerService.getUreaTypesList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getUreaTypesById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUreaTypesById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return ureaManufacturerService.getUreaTypesById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
		}
	}	
	
	@RequestMapping(value = "/editUreaTypes", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editUreaTypes(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return ureaManufacturerService.editUreaTypes(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteUreaType", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteUreaType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return ureaManufacturerService.deleteUreaType(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getUreastockQuantity", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUreastockQuantity(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return ureaInvoiceToDetailsService.getUreastockDetailsByManufactureIdAndLocation(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/getOtherUreastockQuantity", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getOtherUreastockQuantity(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return ureaInvoiceToDetailsService.getUreastockDetailsOfOtherLocationByManufactureId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getLocationWiseUreaInvoiceDetails")
	public void getLocationWiseUreaInvoiceDetails(Map<String, Object> map, @RequestParam("term") final String term,@RequestParam("locationId") final Integer locationId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<UreaInvoiceToDetailsDto> inventory = new ArrayList<UreaInvoiceToDetailsDto>();
		List<UreaInvoiceToDetailsDto> location = ureaInvoiceToDetailsService.getLocationWiseUreaInvoiceDetails(term, locationId,userDetails.getCompany_id());
		
		if (location != null && !location.isEmpty()) {
			for (UreaInvoiceToDetailsDto add : location) {
				UreaInvoiceToDetailsDto wadd = new UreaInvoiceToDetailsDto();

				wadd.setUreaInvoiceToDetailsId(add.getUreaInvoiceToDetailsId());
				wadd.setQuantity(add.getQuantity());
				wadd.setUsedQuantity(add.getUsedQuantity());
				wadd.setManufacturerId(add.getManufacturerId());
				wadd.setManufacturerName(add.getManufacturerName());
				wadd.setStockQuantity(add.getStockQuantity());
				wadd.setUreaInvoiceNumber(add.getUreaInvoiceNumber());
				wadd.setUreaInvoiceId(add.getUreaInvoiceId());
				wadd.setWareHouseLocation(add.getWareHouseLocation());
				wadd.setLocationName(add.getLocationName());
				wadd.setUnitprice(add.getUnitprice());
				wadd.setDiscount(add.getDiscount());
				wadd.setTax(add.getTax());

				inventory.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(inventory));
	}
	  
	@RequestMapping("/download/UreaInvoiceDocument/{ureaDocumentId}")
	public String  downloadUreaInvoiceDocument(@PathVariable("ureaDocumentId") Long ureaDocumentId, HttpServletResponse response) throws Exception {
		CustomUserDetails 	userDetails 	=  null;
		try {
			if (ureaDocumentId <= 0) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.UreaInvoiceDocument document = ureaInventoryService.getUreaInvoiceDocumentDetails(ureaDocumentId, userDetails.getCompany_id());
				if (document != null && document.getUreaInvoiceContent() != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + document.getUreaInvoiceFileName() + "\"");
						OutputStream  out = response.getOutputStream();
						response.setContentType(document.getUreaInvoiceContentType());
						response.getOutputStream().write(document.getUreaInvoiceContent());
						out.flush();
						out.close();

				}
			}

		}catch (Exception e) {
			throw e;
		}
		return null;
	}
	
	@PostMapping(value = "/getsubLocationUreaDetails", produces="application/json")
	public HashMap<Object, Object>  getsubLocationUreaDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 						object 							= null;
		List<UreaInvoiceToDetailsDto>		ureaInvoiceToDetailsDtoList 	= null;
		try {
			object 									= new ValueObject(allRequestParams);
			HashMap<Integer, UreaInvoiceToDetailsDto>	ureaInvoiceToDetailsHM = new HashMap<>();
			ureaInvoiceToDetailsDtoList				= ureaInvoiceToDetailsService.getsubLocationUreaDetails(object.getInt("mainLocationId"));
			
			if(ureaInvoiceToDetailsDtoList != null && !ureaInvoiceToDetailsDtoList.isEmpty()) {
				for(UreaInvoiceToDetailsDto dto : ureaInvoiceToDetailsDtoList) {
					if(ureaInvoiceToDetailsHM.containsKey(dto.getSubLocationId())){
						dto.setStockQuantity(dto.getStockQuantity()+ureaInvoiceToDetailsHM.get(dto.getSubLocationId()).getStockQuantity());
						ureaInvoiceToDetailsHM.put(dto.getSubLocationId(), dto);
					}else {
						dto.setStockQuantity(dto.getStockQuantity());
						ureaInvoiceToDetailsHM.put(dto.getSubLocationId(), dto);
					}
				}
			}
			object.put("sublocationUreaDetails", ureaInvoiceToDetailsHM.values());
			return object.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
}

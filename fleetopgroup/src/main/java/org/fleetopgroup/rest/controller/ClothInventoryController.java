package org.fleetopgroup.rest.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.UpholsteryConfigurationConstant;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.dto.ClothInvoiceDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.ClothInvoice;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryUpholsteryTransferService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ClothInventoryController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());


	@Autowired IPartLocationsService					partLocationsService;
	@Autowired IClothInventoryService					clothInventoryService;
	@Autowired IInventoryUpholsteryTransferService		inventoryUpholsteryTransferService;
	@Autowired ICompanyConfigurationService				companyConfigurationService;
	@Autowired MongoOperations							mongoOperations;
	@Autowired IPendingVendorPaymentService				pendingVendorPaymentService;
	@Autowired Environment environment;
	@Autowired IBankPaymentService     					bankPaymentService;
	@Autowired IVendorService							vendorService;
	PartLocationsBL PLBL = new PartLocationsBL();

	
	@RequestMapping(value = "/ClothInventory", method = RequestMethod.GET)
	public ModelAndView clothInventory(HttpServletRequest	request) throws Exception {
		ModelAndView map = new ModelAndView("AddClothInventory");
		boolean						showSubLocation		= false;
		String						mainLocationIds		= "";
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CLOTH_CONFIGURATION_CONFIG);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			showSubLocation	 	= (boolean)configuration.getOrDefault(UpholsteryConfigurationConstant.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(UpholsteryConfigurationConstant.MAIN_LOCATION_IDS, "")+"";
			
			if(showSubLocation) {
				map.addObject("showSubLocation",showSubLocation);
				map.addObject("mainLocationIds",mainLocationIds);
			}
			map.addObject("PartLocations", PLBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			map.addObject("permissions", permission);
			map.addObject("configuration", configuration);
			map.addObject("accessToken", Utility.getUniqueToken(request));
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/saveClothInventoryDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveClothInventoryDetails(@RequestParam("clothInvoiceData") String allRequestParams,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			object.put("clothDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("clothDetails")));
			
			return clothInventoryService.saveClothInventoryDetails(object,uploadfile).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getPageWiseClothInvoiceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPageWiseClothInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.getClothInvoiceList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/showClothInvoice", method = RequestMethod.GET)
	public ModelAndView showClothInvoice(@RequestParam("Id") final Long invoiceId,
			final HttpServletRequest request) throws Exception {
		ModelAndView map = new ModelAndView("Show_ClothInventory");
		boolean	showSubLocation = false;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CLOTH_CONFIGURATION_CONFIG);
			showSubLocation = (boolean)configuration.getOrDefault(UpholsteryConfigurationConstant.SHOW_SUB_LOCATION, false);
			map.addObject("invoiceId",invoiceId);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("configuration", configuration);
			map.addObject("showSubLocation", showSubLocation);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/locationClothDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  locationClothDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getlocationClothDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getClothInvoiceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getClothInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.get_list_ClothInvoiceDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editClothInvoice", method = RequestMethod.GET)
	public ModelAndView editClothInvoice(@RequestParam("Id") final Long Invoice_id,
			final ClothInvoiceDto ClothInvoiceDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		boolean				showSubLocation	= false;
		String				mainLocationIds	= "";
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CLOTH_CONFIGURATION_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(UpholsteryConfigurationConstant.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(UpholsteryConfigurationConstant.MAIN_LOCATION_IDS, "")+"";
			model.put("clothInvoice", clothInventoryService.getClothInvoiceDetails(Invoice_id, userDetails.getCompany_id()));
			model.put("configuration",configuration);
			model.put("showSubLocation",showSubLocation);
			model.put("mainLocationIds",mainLocationIds);
		} catch (Exception e) {
			System.err.println("Exception "+e);
		}finally {
			userDetails	= null;
		}
		return new ModelAndView("Edit_ClothInvoice", model);
	}
	
	@RequestMapping(value = "/updateClothInventory", method = RequestMethod.POST)
	public ModelAndView updateClothInventory(@ModelAttribute ClothInvoiceDto clothInvoiceDto,@RequestParam("input-file-preview") MultipartFile file, 
			@ModelAttribute BankPaymentDto bankPaymentDto,final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails				userDetails				= null;
		ClothInvoice					clothInvoice			= null;
		HashMap<String, Object> 		configuration			= null;
		ClothInvoice					validateClothInvoice	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CLOTH_CONFIGURATION_CONFIG);
			if (clothInvoiceDto.getClothInvoiceId() != null) {
				clothInvoice = clothInventoryService.getClothInvoiceByClothInvoiceId(clothInvoiceDto.getClothInvoiceId(), userDetails.getCompany_id());
				
				if((boolean) configuration.getOrDefault("validateInvoiceNumber", false)) {
					if(!clothInvoice.getInvoiceNumber().equalsIgnoreCase(clothInvoiceDto.getInvoiceNumber())) {
						validateClothInvoice = clothInventoryService.getClothInvoiceByInvoiceNumber(clothInvoiceDto.getInvoiceNumber(), userDetails.getCompany_id());
						if(validateClothInvoice != null) {
							model.put("duplicateInvoiceNumber", true);
							return new ModelAndView("redirect:/editClothInvoice?Id=" +clothInvoiceDto.getClothInvoiceId(), model);
						}
					}
				}
				
				
				short oldPaymentTypeId = clothInvoice.getPaymentTypeId();
				
				clothInvoiceDto.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
				clothInvoiceDto.setLastModifiedById(userDetails.getId());
				clothInvoiceDto.setInvoiceDate(DateTimeUtility.getDateTimeFromStr(clothInvoiceDto.getInvoiceDateStr(), DateTimeUtility.DD_MM_YYYY));
				clothInvoiceDto.setCompanyId(userDetails.getCompany_id());
				if (file != null && !file.isEmpty() && file.getSize() > 0) {
					clothInvoiceDto.setCloth_document(true);
				}else if(clothInvoice.isCloth_document() == true) {
					clothInvoiceDto.setCloth_document(true);
				}else {
					clothInvoiceDto.setCloth_document(false);
				}
				if(clothInvoiceDto.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					clothInvoiceDto.setVendorPaymentStatus(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
				}else {
					clothInvoiceDto.setVendorPaymentStatus(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
				}
				clothInventoryService.updateClothInvoice(clothInvoiceDto);
				
				PendingVendorPayment	payment		= null;
				if(oldPaymentTypeId != clothInvoiceDto.getPaymentTypeId() && clothInvoiceDto.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForCIEdit(clothInvoiceDto);
					pendingVendorPaymentService.savePendingVendorPayment(payment);
				}else if(oldPaymentTypeId == clothInvoiceDto.getPaymentTypeId() && clothInvoiceDto.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForCIEdit(clothInvoiceDto);
					pendingVendorPaymentService.updatePendingVendorPayment(payment);
				}else if(oldPaymentTypeId != clothInvoiceDto.getPaymentTypeId() && clothInvoiceDto.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					pendingVendorPaymentService.deletePendingVendorPayment(clothInvoice.getClothInvoiceId(), PendingPaymentType.PAYMENT_TYPE_CLOTH_INVOICE);
				}
				if(bankPaymentDto.isAllowBankPaymentDetails()) {
				ValueObject bankPaymentValueObject=new ValueObject();
				bankPaymentValueObject.put("oldPaymentTypeId", oldPaymentTypeId);
				bankPaymentValueObject.put("currentPaymentTypeId",clothInvoiceDto.getPaymentTypeId());
				bankPaymentValueObject.put("userId", userDetails.getId());
				bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId", clothInvoiceDto.getClothInvoiceId());
				bankPaymentValueObject.put("moduleNo", clothInvoiceDto.getClothInvoiceNumber());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.BATTRRY_INVENTORY);
				bankPaymentValueObject.put("amount",clothInvoiceDto.getInvoiceAmount());
				
				Vendor	vendor	= vendorService.getVendor(clothInvoiceDto.getVendorId());
				bankPaymentValueObject.put("remark","Cloth Invoice Edit On CI-"+clothInvoiceDto.getClothInvoiceNumber()+" Vendor : "+vendor.getVendorName()+" ");
				
				bankPaymentService.updatePaymentDetails(bankPaymentValueObject, bankPaymentDto);
				}
				
				if (file != null && !file.isEmpty() && file.getSize() > 0) {
					org.fleetopgroup.persistence.document.ClothInvoiceDocument clothDoc = new org.fleetopgroup.persistence.document.ClothInvoiceDocument();
					clothDoc.setClothInvoiceId(clothInvoiceDto.getClothInvoiceId());
					try {
							byte[] bytes = file.getBytes();
							clothDoc.setClothInvoiceFileName(file.getOriginalFilename());
							clothDoc.setClothInvoiceContent(bytes);
							clothDoc.setClothInvoiceContentType(file.getContentType());

							clothDoc.setMarkForDelete(false);

							clothDoc.setCreatedById(userDetails.getId());
							clothDoc.setLastModifiedById(userDetails.getId());

							java.util.Date currentDateUpdate = new java.util.Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

							clothDoc.setCreated(toDate);
							clothDoc.setLastupdated(toDate);
							clothDoc.setCompanyId(userDetails.getCompany_id());
						} catch (IOException e) {
							e.printStackTrace();
						}
					try {
						org.fleetopgroup.persistence.document.ClothInvoiceDocument doc = clothInventoryService.getClothInvoiceDocumentDetailsByInvoiceId(clothInvoiceDto.getClothInvoiceId(), userDetails.getCompany_id());
						if (doc != null) {
							clothDoc.set_id(doc.get_id());
							mongoOperations.save(clothDoc);
							clothInventoryService.updateClothInvoiceDocumentId(clothDoc.get_id(), true,clothInvoiceDto.getClothInvoiceId(), userDetails.getCompany_id());
							
						}else {
							clothInventoryService.addClothInvoiceDocument(clothDoc);
							clothInventoryService.updateClothInvoiceDocumentId(clothDoc.get_id(), true,clothInvoiceDto.getClothInvoiceId(), userDetails.getCompany_id());
						}
						
					} catch (ConverterNotFoundException e) {
						return new ModelAndView("redirect:/ClothInventory.in", model);
					}
				}

				model.put("UpdateSuccess", true);
			} else {
				return new ModelAndView("redirect:/ClothInventory.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/ClothInventory.in?danger=true");
		}
		return new ModelAndView("redirect:/showClothInvoice?Id=" + clothInvoiceDto.getClothInvoiceId(), model);
	}
	
	@GetMapping("/editClothLaundryInvoice")
	public ModelAndView editClothLaundryInvoice(@RequestParam("Id") final Long Invoice_id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		boolean				showSubLocation	= false;
		String				mainLocationIds	= "";
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CLOTH_CONFIGURATION_CONFIG);
			showSubLocation	 	= (boolean)configuration.getOrDefault(UpholsteryConfigurationConstant.SHOW_SUB_LOCATION, false);
			mainLocationIds 	= configuration.getOrDefault(UpholsteryConfigurationConstant.MAIN_LOCATION_IDS, "")+"";
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			model.put("permissions", permission);
			model.put("configuration",configuration);
			model.put("showSubLocation",showSubLocation);
			model.put("mainLocationIds",mainLocationIds);
			model.put("invoiceId",Invoice_id);
		} catch (Exception e) {
			System.err.println("Exception "+e);
		}finally {
			userDetails	= null;
		}
		return new ModelAndView("EditLaundryInvoice", model);
	}
	
	@RequestMapping(value = "/deleteClothInventoryDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteClothInventoryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.deleteClothInventoryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteClothInventory", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteClothInventory(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.deleteClothInventory(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchClothInvoice", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchClothInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.searchClothInvoice(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchClothInvoiceByNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchClothInvoiceByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.searchClothInvoiceByNumber(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping("/VehiclePartInventoryDetails")
	public ModelAndView VehiclePartInventoryDetails(@RequestParam("Id") final Integer Vehicle_ID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			model.put("vid", Vehicle_ID);
		} catch (Exception e) {
			LOGGER.error("VehiclePartInventoryDetails :", e);
		}
		return new ModelAndView("Show_Vehicle_ClothInventory", model);
	}
	
	@PostMapping(path = "/getVehicleCLothAsignmentDetails", produces="application/json")
	public HashMap<Object, Object>  getVehicleCLothAsignmentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.getVehicleCLothAsignmentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/saveVehicleClothInventoryDetails", produces="application/json")
	public HashMap<Object, Object>  saveVehicleClothInventoryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.saveVehicleClothInventoryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getVehicleClothInventoryDetails", produces="application/json")
	public HashMap<Object, Object>  getVehicleClothInventoryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.getVehicleClothInventoryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/removeClothInventoryDetails", produces="application/json")
	public HashMap<Object, Object>  removeClothInventoryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.removeClothInventoryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping("/SendClothToLaundry")
	public ModelAndView sendClothToLaundry(HttpServletRequest request) throws Exception {
		ModelAndView map = new ModelAndView("SendClothToLaundry");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("accessToken", Utility.getUniqueToken(request));
			map.addObject("configuration", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CLOTH_CONFIGURATION_CONFIG));
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	
	@PostMapping(path = "/saveSentClothToLaundry", produces="application/json")
	public HashMap<Object, Object>  saveSentClothToLaundry(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			object.put("clothDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("clothDetails")));
			
			return clothInventoryService.saveSentClothToLaundry(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/updateClothToLaundry", produces="application/json")
	public HashMap<Object, Object>  updateClothToLaundry(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.updateClothToLaundry(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getClothLaundryDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getClothLaundryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.getClothLaundryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(path = "/showLaundryInvoice")
	public ModelAndView showLaundryInvoice(@RequestParam("Id") final Long invoiceId,
			final HttpServletRequest request) throws Exception {
		ModelAndView map = new ModelAndView("Show_LaundryInvoice");
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("invoiceId",invoiceId);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
			map.addObject("configuration", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CLOTH_CONFIGURATION_CONFIG));
		} catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getLaundryInvoiceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getLaundryInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.getLaundryInvoiceDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/receiveClothFromLaundry", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  receiveClothFromLaundry(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.receiveClothFromLaundry(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getReceivedClothDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getReceivedClothDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.getReceivedClothDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getShowClothAssignDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getShowClothAssignDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getShowClothAssignDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getInServiceVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getInServiceVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getInServiceVehicle(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getDamageWashingQtyDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDamageWashingQtyDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothInventoryService.getDamageWashingQtyDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/saveDamageWashingQty", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveDamageWashingQty(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.saveDamageWashingQty(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveLostWashingQty", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveLostWashingQty(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.saveLostWashingQty(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveClothDamageDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveClothDamageDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.saveClothDamageDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveClothLostDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveClothLostDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.saveClothLostDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/getInDamageDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getInDamageDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getInDamageDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getInLostDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getInLostDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getInLostDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getInWashingDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getInWashingDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getInWashingDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/upholsteryTransfer", method = RequestMethod.GET)
	public ModelAndView upholsteryTransfer() throws Exception {
		ModelAndView map = new ModelAndView("UpholsteryTransfer");
		try {
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/saveUpholsteryTransfer", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveUpholsteryTransfer(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("UpholsteryTransfer", JsonConvertor.toValueObjectFromJsonString(object.getString("UpholsteryTransfer")));
			
			return inventoryUpholsteryTransferService.saveUpholsteryTransfer(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/upholsteryReceive", method = RequestMethod.GET)
	public ModelAndView upholsteryReceive() throws Exception {
		ModelAndView map = new ModelAndView("UpholsteryReceive");
		try {
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getTransferReceivedDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTransferReceivedDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return inventoryUpholsteryTransferService.getTransferReceivedDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getReceiveDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getReceiveDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return inventoryUpholsteryTransferService.getReceiveDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveReceiveUpholstery", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateReceiveUpholstery(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return inventoryUpholsteryTransferService.saveReceiveUpholstery(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveRejectUpholstery", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveRejectUpholstery(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return inventoryUpholsteryTransferService.saveRejectUpholstery(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleAssignUpholstery", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleAssignUpholstery(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getAllUpholsteryAssigndToVehicleByDate(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/download/ClothInvoiceDocument/{clothDocumentId}")
	public String  downloadClothInvoiceDocument(@PathVariable("clothDocumentId") Long clothDocumentId, HttpServletResponse response) throws Exception {
		CustomUserDetails 	userDetails 	=  null;
		try {
			if (clothDocumentId <= 0) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.ClothInvoiceDocument document = clothInventoryService.getClothInvoiceDocumentDetails(clothDocumentId, userDetails.getCompany_id());
				if (document != null) {
					if (document.getClothInvoiceContent() != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + document.getClothInvoiceFileName() + "\"");
						OutputStream  out = response.getOutputStream();
						response.setContentType(document.getClothInvoiceContentType());
						response.getOutputStream().write(document.getClothInvoiceContent());
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
	
	@PostMapping(path = "/saveVehicleLaundryDetails", produces="application/json")
	public HashMap<Object, Object>  saveVehicleLaundryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.saveVehicleLaundryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getVehicleLaundryDetailsList", produces="application/json")
	public HashMap<Object, Object>  getVehicleLaundryDetailsList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getVehicleLaundryDetailsList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/removeVehicleLaundry", produces="application/json")
	public HashMap<Object, Object>  removeVehicleLaundry(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.removeVehicleLaundry(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/deleteClothLaundryInvoice", produces="application/json")
	public HashMap<Object, Object>  deleteClothLaundryInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.deleteClothLaundryInvoice(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}

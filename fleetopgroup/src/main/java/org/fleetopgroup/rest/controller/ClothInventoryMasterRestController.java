package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dao.VehicleClothMaxAllowedSettingRepository;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetConfigurationConstant;
import org.fleetopgroup.constant.UpholsteryConfigurationConstant;
import org.fleetopgroup.persistence.dao.VehicleClothInventoryDetailsRepository;
import org.fleetopgroup.persistence.dto.ClothInventoryStockTypeDetailsDto;
import org.fleetopgroup.persistence.dto.ClothTypesDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.VendorFixedLaundryRateDto;
import org.fleetopgroup.persistence.model.ClothTypes;
import org.fleetopgroup.persistence.model.VehicleClothInventoryDetails;
import org.fleetopgroup.persistence.model.VehicleClothMaxAllowedSetting;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryStockTypeDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IClothTypeService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IVendorFixedLaundryRateService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class ClothInventoryMasterRestController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	IClothTypeService							clothTypeService;	
	@Autowired	IVendorService								vendorService;
	@Autowired	IClothInventoryStockTypeDetailsService		clothInventoryStockTypeDetailsService;
	@Autowired  IVendorFixedLaundryRateService				vendorFixedLaundryRateService;
	@Autowired  IClothInventoryService						clothInventoryService;
	@Autowired  VehicleClothMaxAllowedSettingRepository		VehicleClothMaxAllowedSettingRepository;
	@Autowired  VehicleClothInventoryDetailsRepository		VehicleClothInventoryDetailsRepository;
	@Autowired 	ICompanyConfigurationService 				companyConfigurationService;

	@Autowired 
	IMasterPartsService 				masterPartsService;
	
	
	@RequestMapping(value = "/addClothTypes", method = RequestMethod.GET)
	public ModelAndView addTripSheetOptions(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("addClothTypes");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
		}  catch (Exception e) {
			LOGGER.error("Exception addClothTypes : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getClothTypesList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getClothTypesList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothTypeService.getClothTypesList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveClothTypes", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveClothTypes(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothTypeService.saveClothTypes(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editClothTypes", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editClothTypes(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothTypeService.editClothTypes(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getClothTypesById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getClothTypesById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothTypeService.getClothTypesById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteClothType", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteClothType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return clothTypeService.deleteClothType(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/getClothTypesList", method = RequestMethod.POST)
	public void getClothTypesList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<ClothTypes>   ClothTypesList	= new ArrayList<>();
			List<ClothTypes>   manufacturers	= clothTypeService.getClothTypesList(term, userDetails.getCompany_id());
			
			if(manufacturers != null && !manufacturers.isEmpty()) {
				for(ClothTypes clothTypes : manufacturers) {
					ClothTypesList.add(clothTypes);
				}
			}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(ClothTypesList));
	}
	
	@RequestMapping(value = "/getClothTypesListNotSelected", method = RequestMethod.POST)
	public void getClothTypesListNotSelected(Map<String, Object> map, @RequestParam("term") final String term, @RequestParam("clothTypeIds") final String clothTypeIds,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<ClothTypes>   ClothTypesList	= new ArrayList<>();
		List<ClothTypes>   manufacturers	= null;
			if(clothTypeIds != null && !clothTypeIds.equalsIgnoreCase("")) {
				manufacturers	= clothTypeService.getClothTypesListNotSelected(term, methodRemoveLastComma(clothTypeIds), userDetails.getCompany_id());
			}else {
				manufacturers	= clothTypeService.getClothTypesList(term, userDetails.getCompany_id());
			}
			
			if(manufacturers != null && !manufacturers.isEmpty()) {
				for(ClothTypes clothTypes : manufacturers) {
					ClothTypesList.add(clothTypes);
				}
			}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(ClothTypesList));
	}
	
	@RequestMapping(value = "/getClothTypesListByClothTypesId", method = RequestMethod.POST)
	public void getClothTypesListByClothTypesId(Map<String, Object> map, @RequestParam("term") final String term, 
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<ClothTypes>   manufacturers	=	null;
		List<ClothTypes>   ClothTypesList	= new ArrayList<>();
		manufacturers	= clothTypeService.getClothTypesList(term,userDetails.getCompany_id());
			
			
			if(manufacturers != null && !manufacturers.isEmpty()) {
				for(ClothTypes clothTypes : manufacturers) {
					ClothTypesList.add(clothTypes);
				}
			}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(ClothTypesList));
	}
	
	@RequestMapping(value = "/getLocationClothCount", method = RequestMethod.GET)
	public void getFuelVehicleOdoMerete(@RequestParam(value = "locationId", required = true) Integer locationId,
			@RequestParam(value = "clothTypesId", required = true) Long clothTypesId,
			@RequestParam(value = "vehicleId", required = true) int vehicleId,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails						userDetails								= null;
		ValueObject								valueOutobject							= null;
		double									defaultquantity							= 0.0;
		HashMap<String, Object> 				configuration	    					= null;
		boolean									allowClothDetailsToSingleLocation		= false;
		List<VehicleClothInventoryDetails>		validateClothDetails					= null;
		boolean 								allowClothDeatils						= false;
		int 									previousLocation						= 0;
		
		try {
			valueOutobject = new ValueObject();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.UPHOLSTERY_CONFIG);
			allowClothDetailsToSingleLocation	= (boolean) configuration.getOrDefault(UpholsteryConfigurationConstant.ASSIGN_CLOTH_DETAILS_TO_SINGLE_LOCATION, false);
			
			ClothInventoryStockTypeDetailsDto	detailsDto	=	clothInventoryStockTypeDetailsService.getClothLocationQuantity(clothTypesId, locationId, userDetails.getCompany_id());
			if(detailsDto != null) {
				valueOutobject.put("detailsDto", detailsDto);
			} else {
				valueOutobject.put("StockNotFound", true);
			}
			
			VehicleClothMaxAllowedSetting val = VehicleClothMaxAllowedSettingRepository.validateVehicleClothMaxAllowed(clothTypesId, vehicleId);
			if(val != null) {
				valueOutobject.put("MaxQuantity", val.getMaxAllowedQuantity());	
			} else {
				valueOutobject.put("MaxQuantity", defaultquantity);	
			}
			
			VehicleClothInventoryDetails remainingQuantity = VehicleClothInventoryDetailsRepository.validateVehicleClothInventoryDetails(clothTypesId, vehicleId, locationId);
			if(remainingQuantity != null) 
			valueOutobject.put("RemainingQuantity", remainingQuantity.getQuantity());
			
			
			if(allowClothDetailsToSingleLocation) {
				
				validateClothDetails = VehicleClothInventoryDetailsRepository.checkVehicleClothInventoryDetails(clothTypesId, vehicleId);
				for(VehicleClothInventoryDetails validate : validateClothDetails) {
					
					if(validate.getQuantity() > 0) {
						allowClothDeatils = true;
						previousLocation  = validate.getLocationId();
					}
					
				}
				
				if(allowClothDeatils) {
					if(previousLocation != locationId) {
						valueOutobject.put("MultipleLocationDenied", true);
					}
				}
				
			}
			
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(valueOutobject));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/geLaundryVendorRateAndStockCount", method = RequestMethod.GET)
	public void geLaundryVendorRateAndStockCount(@RequestParam(value = "locationId") Integer locationId,
			@RequestParam(value = "clothTypesId") Long clothTypesId,
			@RequestParam(value = "vendorId") Integer vendorId,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			
			VendorFixedLaundryRateDto	detailsDto =	vendorFixedLaundryRateService.getVendorRateAndLocationQuantity(locationId, clothTypesId, vendorId);
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(detailsDto));
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String methodRemoveLastComma(String str) {
		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	@RequestMapping(value = "/getClothVendorSearchList", method = RequestMethod.POST)
	public void getTyreVendorSearchList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= null;
		try {
			List<Vendor> vendorList =	new ArrayList<>();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Vendor> vendor = vendorService.SearchOnly_Cloth_VendorName(term, userDetails.getCompany_id());
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
	
	@RequestMapping(value = "/getLaundryVendorSearchList", method = RequestMethod.POST)
	public void getLaundryVendorSearchList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= null;
		try {
			List<Vendor> vendorList =	new ArrayList<>();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Vendor> vendor = vendorService.SearchOnly_Laundry_VendorName(term, userDetails.getCompany_id());
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
	
	@RequestMapping(value = "/getUreaVendorSearchList", method = RequestMethod.POST)
	public void getUreaVendorSearchList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= null;
		try {
			List<Vendor> vendorList =	new ArrayList<>();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Vendor> vendor = vendorService.SearchOnly_Urea_VendorName(term, userDetails.getCompany_id());
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
	
	@RequestMapping(value = "/getClothTypesListWithRate", method = RequestMethod.POST)
	public void getClothTypesListWithRate(Map<String, Object> map, @RequestParam("term") final String term, @RequestParam("vendorId") final Integer vendorId,
			@RequestParam("locationId") final Integer locationId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<ClothTypesDto>   ClothTypesList	= new ArrayList<>();
			List<ClothTypesDto>   manufacturers	= clothTypeService.getClothTypesListByClothTypesIdWithRate(term, locationId, vendorId);
			
			if(manufacturers != null && !manufacturers.isEmpty()) {
				for(ClothTypesDto clothTypes : manufacturers) {
					ClothTypesList.add(clothTypes);
				}
			}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(ClothTypesList));
		} catch (Exception e) {
			System.err.println("Exception : "+ e);
			throw e;
		}
		
	}
	
	@RequestMapping(value = "/addVehicleClothMaxAllowed", method = RequestMethod.GET)
	public ModelAndView addVehicleClothMaxAllowed(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("addVehicleClothMaxAllowed");
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
		}  catch (Exception e) {
			LOGGER.error("Exception addClothTypes : ", e);
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getVehicleClothMaxAllowed", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleClothMaxAllowed(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getVehicleClothMaxAllowed(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveVehicleClothMaxAllowed", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVehicleClothMaxAllowed(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("VehicleClothMaxAllowed", JsonConvertor.toValueObjectFromJsonString(object.getString("VehicleClothMaxAllowed")));
			
			return clothInventoryService.saveVehicleClothMaxAllowed(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleClothMaxAllowedById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleClothMaxAllowedById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getVehicleClothMaxAllowedById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateVehicleClothMaxAllowedById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateVehicleClothMaxAllowedById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.updateVehicleClothMaxAllowedById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteVehicleClothMaxAllowedById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteVehicleClothMaxAllowedById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.deleteVehicleClothMaxAllowedById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getLocationClothCountDetails", method = RequestMethod.GET)
	public void getLocationClothCountDetails(@RequestParam(value = "locationId", required = true) Integer locationId,
			@RequestParam(value = "clothTypesId", required = true) Long clothTypesId,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails			userDetails			= null;
		ValueObject					valueOutobject		= null;
		try {
			valueOutobject = new ValueObject();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			ClothInventoryStockTypeDetailsDto	detailsDto	=	clothInventoryStockTypeDetailsService.getClothLocationQuantity(clothTypesId, locationId, userDetails.getCompany_id());
			if(detailsDto != null) {
				valueOutobject.put("detailsDto", detailsDto);
			} else {
				valueOutobject.put("StockNotFound", true);
			}
			
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(valueOutobject));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/getVehicleClothListBySearch", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleClothListBySearch(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return clothInventoryService.getVehicleClothListBySearch(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}

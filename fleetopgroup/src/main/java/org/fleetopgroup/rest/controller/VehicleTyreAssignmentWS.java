package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.persistence.dao.VehicleManufacturerRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DealerServiceEntriesDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelTyreLayoutService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreAssignmentService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VehicleTyreAssignmentWS {
	@Autowired 	ICompanyConfigurationService			companyConfigurationService;
	@Autowired 	IVehicleTyreAssignmentService			vehicleTyreAssignmentService;
	@Autowired	IVehicleService   						vehicleService;
	@Autowired	IIssuesService   						issueService;
	@Autowired	IVehicleModelService   					vehicleModelService;
	@Autowired 	VehicleManufacturerRepository 			manufacturerRepository;
	@Autowired 	IVehicleModelTyreLayoutService   		vehicleModelTyreLayoutService;
	@Autowired 	IInventoryTyreService   				inventoryTyreService;
	@Autowired	IWorkOrdersService   					workorderService;
	@Autowired	IDealerServiceEntriesService   			dealerServiceEntriesService;
	
	
	
	
	@GetMapping(value = "/vehicleTyreAssignment")
	public ModelAndView vehicleTyreAssignment (@RequestParam("data") final String data,final HttpServletRequest request) throws Exception {
		ModelAndView 					map 			= null;
		CustomUserDetails				userDetails		= null;
		HashMap<String, Object> 		configuration 	= null;
		Vehicle 						vehicle			= null;
		String[]						dataArr			= null;
		VehicleTyreLayoutPositionDto		vehicleTyreLayoutPosition = null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			map 			= new ModelAndView("vehicleTyreAssignment");
			if(data != null && data != "") {
				dataArr 		= data.split(",");
				
				if(dataArr.length > 1) {
					vehicleTyreLayoutPosition	= vehicleTyreAssignmentService.getTyreModelByLP_ID(Integer.parseInt(data.split(",")[0]),Long.parseLong(data.split(",")[1]),userDetails.getCompany_id());
					map.addObject("position",vehicleTyreLayoutPosition.getPOSITION());
					map.addObject("tyreModel", vehicleTyreLayoutPosition.getTyreModel());
					map.addObject("lpid", vehicleTyreLayoutPosition.getLP_ID());
					map.addObject("positionStr", data.split(",")[2]);
					
				}
				vehicle 		= vehicleService.getVehicel(Integer.parseInt(data.split(",")[0]), userDetails.getCompany_id());
				map.addObject("vid", data.split(",")[0]);
				map.addObject("vehicleRegistration", vehicle.getVehicle_registration());
				map.addObject("showVehicleModelId", vehicle.getVehicleModalId());
				
			}
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			map.addObject("accessToken", uniqueID);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@GetMapping(value = "/vehicleTyreAssignmentFromIssue")
	public ModelAndView vehicleTyreAssignmentFromIssue (@RequestParam("issueId") final Long issueId,final HttpServletRequest request) throws Exception {
		ModelAndView 					map 			= null;
		CustomUserDetails				userDetails		= null;
		HashMap<String, Object> 		configuration 	= null;
		Vehicle 						vehicle			= null;
		IssuesDto 						issue			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			map 			= new ModelAndView("vehicleTyreAssignment");
			if(issueId != null ) {
				issue 		= issueService.get_IssuesDetails(issueId, userDetails.getCompany_id());
				
				if(issue.getISSUES_VID() != null) {
					
					vehicle 	= vehicleService.getVehicel(issue.getISSUES_VID(), userDetails.getCompany_id());
					
					map.addObject("vid", vehicle.getVid());
					map.addObject("vehicleRegistration", vehicle.getVehicle_registration());
					map.addObject("showVehicleModelId", vehicle.getVehicleModalId());
					map.addObject("transactionTypeId", TyreAssignmentConstant.TRANSACTION_TYPE_ISSUE);
					map.addObject("issueId", issueId);
					map.addObject("encIssueId", AESEncryptDecrypt.encryptBase64("" + issueId));
					map.addObject("transactionId", issueId);
					map.addObject("transactionNumber", issue.getISSUES_NUMBER());
				}
				
				
			}
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			map.addObject("accessToken", uniqueID);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@GetMapping(value = "/vehicleTyreAssignmentFromWO")
	public ModelAndView vehicleTyreAssignmentFromWO (@RequestParam("workorderTaskId") final Long workorderTaskId,final HttpServletRequest request) throws Exception {
		ModelAndView 					map 			= null;
		CustomUserDetails				userDetails		= null;
		HashMap<String, Object> 		configuration 	= null;
		WorkOrdersDto 					workorder			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			map 			= new ModelAndView("vehicleTyreAssignment");
			if(workorderTaskId != null ) {
				workorder 		= workorderService.getWorkOrdersTasksByTaskId(workorderTaskId, userDetails.getCompany_id());
				map.addObject("vid", workorder.getVehicle_vid());
				map.addObject("vehicleRegistration", workorder.getVehicle_registration());
				map.addObject("showVehicleModelId", workorder.getVehicleModelId());
				map.addObject("transactionTypeId", TyreAssignmentConstant.TRANSACTION_TYPE_WO);
				map.addObject("transactionId", workorder.getWorkorders_id());
				map.addObject("transactionSubId", workorderTaskId);
				map.addObject("transactionNumber", workorder.getWorkorders_Number());
				
				
			}
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			map.addObject("accessToken", uniqueID);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@GetMapping(value = "/vehicleTyreAssignmentFromDSE")
	public ModelAndView vehicleTyreAssignmentFromDSE (@RequestParam("dseId") final Long dseId,final HttpServletRequest request) throws Exception {
		ModelAndView 					map 					= null;
		CustomUserDetails				userDetails				= null;
		HashMap<String, Object> 		configuration 			= null;
		ValueObject 					valueObject				= null;
		List<DealerServiceEntriesDto>	dealerServiceEntries	= null;
		try {
			valueObject		= new ValueObject();
			map 			= new ModelAndView("vehicleTyreAssignment");
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			if(dseId != null ) {
			String	query = "AND DSE.dealerServiceEntriesId = "+dseId+"";
			valueObject.put("query", query);
			valueObject.put("companyId", userDetails.getCompany_id());
			dealerServiceEntries 		= dealerServiceEntriesService.getDealerServiceEntriesDetailList(valueObject);
			
				map.addObject("vid", dealerServiceEntries.get(0).getVid());
				map.addObject("vehicleRegistration", dealerServiceEntries.get(0).getVehicleNumber());
				map.addObject("showVehicleModelId", dealerServiceEntries.get(0).getVehicleModelId());
				map.addObject("transactionTypeId", TyreAssignmentConstant.TRANSACTION_TYPE_DSE);
				map.addObject("transactionId", dealerServiceEntries.get(0).getDealerServiceEntriesId());
				map.addObject("transactionNumber", dealerServiceEntries.get(0).getDealerServiceEntriesNumberStr());
				
				
			}
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			map.addObject("accessToken", uniqueID);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@PostMapping(value = "/getVehicleTyreLayoutPosition",  produces="application/json")
	public HashMap<Object, Object>  getVehicleTyreLayoutPosition(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleTyreAssignmentService.getVehicleTyreLayoutPosition(object).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value = "/getTyreAssignToVehicleDetails", produces="application/json")
	public HashMap<Object, Object>  getTyreAssignToVehicleDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleTyreAssignmentService.getTyreAssignedToVehicleDetails(object).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(value = "/tyreAssignToVehicle", produces="application/json")
	public HashMap<Object, Object>  tyreAssignToVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("tyreDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("tyreDetails")));
			if(object.getShort("assignFromId",(short)0) == TyreAssignmentConstant.ASSIGN_FROM_OWN_SPARE) {
				return vehicleTyreAssignmentService.ownSpareAssignment(object).getHtData();
			}else {
				return vehicleTyreAssignmentService.tyreAssignToVehicle(object).getHtData();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@GetMapping(value = "/showVehicleTyreAssignedDetails")
	public ModelAndView vehicleModelTyreLayout(@RequestParam("id") final Integer vid, final HttpServletRequest request) throws Exception {
		ModelAndView 					map 			= null;
		CustomUserDetails				userDetails		= null;
		HashMap<String, Object> 		configuration 	= null;
		Vehicle							vehicle			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			vehicle 		= vehicleService.getVehicel(vid, userDetails.getCompany_id());
			map 			= new ModelAndView("showVehicleTyreAssignedDetails");
			map.addObject("vid", vid);
			map.addObject("vehicleRegistration", vehicle.getVehicle_registration());
			map.addObject("vehicleModelId", vehicle.getVehicleModalId());
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@PostMapping(value = "/getTyreDetailsOfPosition",  produces="application/json")
	public HashMap<Object, Object>  getTyreDetailsOfPosition(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleTyreAssignmentService.getTyreDetailsOfPosition(object).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value = "/tyreRemoveFromVehicle",produces="application/json")
	public HashMap<Object, Object>  tyreRemoveFromVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleTyreAssignmentService.singleTyreRemoveFromVehicle(object,object).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value = "/getAvailabeTyreForAssignment",  produces="application/json")
	public HashMap<Object, Object>  getAvailabeTyreForAssignment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return inventoryTyreService.getAvailabeTyreForAssignment(object).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(value = "/tyreRotation", produces="application/json")
	public HashMap<Object, Object>  tyreRotation(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("tyreRotationDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("tyreRotationDetails")));
			return vehicleTyreAssignmentService.tyreRotation(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value = "/getTyreGuageByTyreId", produces="application/json")
	public HashMap<Object, Object>  getTyreGuageByTyreId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return inventoryTyreService.getTyreGuageByTyreId(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	/*@PostMapping(value = "/flipTyre", produces="application/json")
	public HashMap<Object, Object>  flipTyre(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleTyreAssignmentService.flipTyre(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}*/
	
}

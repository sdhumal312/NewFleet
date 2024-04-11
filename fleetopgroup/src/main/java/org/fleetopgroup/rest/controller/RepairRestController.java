package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.RepairConstant;
import org.fleetopgroup.persistence.bl.RepairBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryLocationDto;
import org.fleetopgroup.persistence.dto.RepairStockDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IRepairService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class RepairRestController {

	@Autowired 	IRepairService 					repairService;
	@Autowired 	ICompanyConfigurationService 	companyConfigurationService;
	@Autowired 	IInventoryService 				inventoryService;
	RepairBL	repairBL 		= new RepairBL();
	
	@GetMapping(value = "/repairViewList")
	public ModelAndView repairViewList(final HttpServletRequest request) throws Exception {
		ModelAndView 		map 			= new ModelAndView("repairViewList");
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	
	@PostMapping(value="/getRepairStockList", produces="application/json")
	public HashMap<Object, Object> getRepairStockList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.getRepairStockList(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@GetMapping(value = "/createRepairInvoice")
	public ModelAndView createRepairInvoice(final HttpServletRequest request) throws Exception {
		ModelAndView 		map 			= new ModelAndView("createRepairInvoice");
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}

	@PostMapping(value="/saveRepairStockInvoice", produces="application/json")
	public HashMap<Object, Object> saveRepairStockInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.saveRepairStockInvoice(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@GetMapping(value = "/editRepairInvoice")
	public ModelAndView editRepairInvoice(@RequestParam("repairStockId") final Integer repairStockId,final HttpServletRequest request) throws Exception {
		ModelAndView 		map 			= new ModelAndView("editRepairInvoice");
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("repairStockId", repairStockId);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	@PostMapping(value="/deleteRepairInvoice", produces="application/json")
	public HashMap<Object, Object> deleteRepairInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.deleteRepairInvoice(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@GetMapping(value = "/showRepairInvoice")
	public ModelAndView showRepairInvoice(@RequestParam("repairStockId") final Long repairStockId, final HttpServletRequest request) throws Exception {
		ModelAndView 					map 				= null;
		CustomUserDetails				userDetails			= null;
		RepairStockDto 					repairStock 		= null;
		ValueObject						valueObject			= null;
		HashMap<String, Object> 		configuration	          = null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	=  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.REPAIR_CONFIG);
			valueObject 	= new ValueObject();
			
			valueObject.put("repairStockId", repairStockId);
			valueObject.put("companyId", userDetails.getCompany_id());
			repairStock = repairService.getRepairInvoiceById(valueObject);
		switch (repairStock.getRepairStatusId()) {
		case RepairConstant.REPAIR_STATUS_CREATED:
			map 			= new ModelAndView("showCreatedRepairInvoice");
			break;
		case RepairConstant.REPAIR_STATUS_SENT_TO_REPAIR:
			map 			= new ModelAndView("showSentRepairInvoice");
			break;
		case RepairConstant.REPAIR_STATUS_COMPLETE:
			map 			= new ModelAndView("showCompletedRepairInvoice");
			break;
		default:
			return new ModelAndView("redirect:/repairViewList.in");
		}
		
			map.addObject("configuration", configuration);
			map.addObject("repairStock", repairStock);
			map.addObject("repairStockId", repairStockId);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	
	@GetMapping(value = "/showSentRepairInvoice")
	public ModelAndView showSentRepairInvoice(@RequestParam("repairStockId") final Integer repairStockId, final HttpServletRequest request) throws Exception {
		ModelAndView 		map 			= new ModelAndView("showSentRepairInvoice");
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("repairStockId", repairStockId);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	
	
	@PostMapping(value="/getRepairInvoice", produces="application/json")
	public HashMap<Object, Object> getRepairInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.getRepairInvoice(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value="/getDismountTyreDetails", produces="application/json")
	public HashMap<Object, Object> getDismountTyreDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.getDismountTyreDetails(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/getDismountBatteryDetails", produces="application/json")
	public HashMap<Object, Object> getDismountBatteryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.getDismountBatteryDetails(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@PostMapping(value="/saveRepairToStockDetails", produces="application/json")
	public HashMap<Object, Object>  saveRepairToStockDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject.put("repairStockDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("repairStockDetails")));
			valueObject		= repairService.saveRepairToStockDetails(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@PostMapping(value="/getRepairToStockDetails", produces="application/json")
	public HashMap<Object, Object> getRepairToStockDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.getRepairToStockDetails(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/saveRepairStockToPartDetails", produces="application/json")
	public HashMap<Object, Object>  saveRepairStockToPartDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject.put("partDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("partDetails")));
			valueObject		= repairService.saveRepairStockToPartDetails(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/saveRepairStockToLabourDetails", produces="application/json")
	public HashMap<Object, Object>  saveRepairStockToLabourDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject.put("labourDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("labourDetails")));
			valueObject		= repairService.saveRepairToLabourDetails(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	
	@PostMapping(value="/getRepairStockToPartAndLabourDetails", produces="application/json")
	public HashMap<Object, Object> getRepairStockToPartDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.getRepairStockToPartAndLabourDetails(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@PostMapping(value="/removeAdditionalPart", produces="application/json")
	public HashMap<Object, Object> removeAdditionalPart(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.removeAdditionalPart(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@PostMapping(value="/removeLabour", produces="application/json")
	public HashMap<Object, Object> removeLabour(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.removeLabour(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/moveToCreatedRepairInvoice", produces="application/json")
	public HashMap<Object, Object> moveToCreatedRepairInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.moveToCreatedRepairInvoice(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/completeRepair", produces="application/json")
	public HashMap<Object, Object> completeRepair(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.completeRepair(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/reopenRepair", produces="application/json")
	public HashMap<Object, Object> reopenRepair(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.reopenRepair(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/rejectStock", produces="application/json")
	public HashMap<Object, Object> rejectStock(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.rejectStock(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@PostMapping(value = "/getRepairablePartListByLocation")
	public void getRepairablePartListByLocation(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		HashMap<String, Object> 	configuration 	= null ;
		List<InventoryLocationDto> 	inventory 		= new ArrayList<>();
		List<InventoryLocationDto> 	location 		= inventoryService.searchRepairablePartByLocation(term, userDetails.getCompany_id());
		
		configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);

		if((boolean) configuration.getOrDefault("showSubstitutePartsInList", false)) {
			List<InventoryLocationDto> substitudePartList = inventoryService.allpartList(term, userDetails.getCompany_id());
			if(substitudePartList != null && !substitudePartList.isEmpty()) {
				List <InventoryLocationDto> finalSubstitudePartList = inventoryService.preparePartIdsString(substitudePartList,  userDetails.getCompany_id());
				if(location == null) {
					location = new ArrayList<>();
				}
				if(finalSubstitudePartList != null && !finalSubstitudePartList.isEmpty())
					location.addAll(finalSubstitudePartList);
			}
		}
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
				wadd.setWarrantyApplicable(add.isWarrantyApplicable());
				wadd.setRepairable(add.isRepairable());
				wadd.setPartid(add.getPartid());
				wadd.setLocationId(add.getLocationId());
				wadd.setUnitTypeId(add.getUnitTypeId());
				wadd.setInventory_id(add.getInventory_id());
				/*if(partHash.get(add.getPartid()) == null) {
					partHash.put(add.getPartid(), wadd);
				}*/
				inventory.add(wadd);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(inventory));
	}
	
	
	@PostMapping(value="/searchRepairByNumber", produces="application/json")
	public HashMap<Object, Object> searchRepairByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.searchRepairByNumber(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/getAssetNumber", produces="application/json")
	public HashMap<Object, Object> getAssetNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= repairService.getAssetNumber(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@PostMapping(value="/sentAssetNumber", produces="application/json")
	public HashMap<Object, Object> sentAssetNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject.put("assetDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("assetDetails")));
			valueObject		= repairService.sentAssetNumber(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(value="/removeAsset", produces="application/json")
	public HashMap<Object, Object> removeAsset(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject		= repairService.removeAsset(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@PostMapping(value="/getAssetNumberForAdditionalPart", produces="application/json")
	public HashMap<Object, Object> getAssetNumberForAdditionalPart(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject		= repairService.getAssetNumberForAdditionalPart(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
	@PostMapping(value="/saveAdditionalAsset", produces="application/json")
	public HashMap<Object, Object> saveAdditionalAsset(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject.put("assetDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("assetDetails")));
			valueObject		= repairService.saveAdditionalAsset(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
	@PostMapping(value="/removeAdditionalAsset", produces="application/json")
	public HashMap<Object, Object> removeAdditionalAsset(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject		= repairService.removeAdditionalAsset(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	
	@GetMapping(value = "/repairStockReport")
	public ModelAndView repairStockReport(final HttpServletRequest request) throws Exception {
		ModelAndView 		map 			= new ModelAndView("repairStockReport");
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	@GetMapping(value = "/viewRepairStockReport")
	public ModelAndView viewRepairStockReport(final HttpServletRequest request) throws Exception {
		ModelAndView 		map 			= new ModelAndView("viewRepairStockReport");
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	
	@PostMapping(value="/getRepairStockReportData", produces="application/json")
	public HashMap<Object, Object> getRepairStockReportData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject		= repairService.getRepairStockReportData(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
	@PostMapping(value="/getAdditionalPartDetails", produces="application/json")
	public HashMap<Object, Object> getAdditionalPartDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject		= repairService.getAdditionalPartDetails(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
	@PostMapping(value="/getAdditionalLabourDetails", produces="application/json")
	public HashMap<Object, Object> getAdditionalLabourDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject		= repairService.getAdditionalLabourDetails(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
}

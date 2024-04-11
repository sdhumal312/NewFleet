package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.TyreTypeConstant;
import org.fleetopgroup.persistence.bl.InventoryTyreInvoiceBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.ITyreUsageHistoryService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class InventoryTyreRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired IInventoryTyreService   		tyreService;
	PartLocationsBL PLBL = new PartLocationsBL();
	@Autowired IPartLocationsService partLocationsService;
	InventoryTyreInvoiceBL ITBL = new InventoryTyreInvoiceBL();
	@Autowired IInventoryTyreService iventoryTyreService;
	@Autowired ITyreUsageHistoryService tyreUsageHistoryService;
	
	@RequestMapping(value = "/getTyreCountList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTyreCountList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			//object.put("clothDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("clothDetails")));
			return tyreService.getTyreCountListDetails(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("ERR"+e);
			throw e;
		} 
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllTyreListByStatus", method = RequestMethod.POST)
	public void getAllTyreListByStatus(Map<String, Object> map, @RequestParam("term") final String term,
			 @RequestParam("status") final String status,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValueObject					valueObject 			= null;
		List<InventoryTyreDto> 		tyreList				= null;
		List<InventoryTyreDto> 		finalTyreList			= null;
		
		try {
			
			valueObject				= new ValueObject();
			tyreList 				= new ArrayList<InventoryTyreDto>();
			finalTyreList 			= new ArrayList<InventoryTyreDto>();
			
			valueObject			= tyreService.getAllTyreListByStatus(term,status);
			tyreList	= (List<InventoryTyreDto>) valueObject.get("tyreList");
			
			if(tyreList != null && !tyreList.isEmpty()) {	
				for (InventoryTyreDto dto : tyreList) {
					InventoryTyreDto 	InventoryTyreDto 	= new InventoryTyreDto();
					InventoryTyreDto.setTYRE_ID(dto.getTYRE_ID());
					InventoryTyreDto.setTYRE_NUMBER(dto.getTYRE_NUMBER());

					finalTyreList.add(InventoryTyreDto);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalTyreList));
		} catch (Exception e) {
			LOGGER.error("Err", e);
		}finally {
			valueObject 		= null;  
			tyreList			= null;  
			finalTyreList		= null;  
		}
	}
	
	@RequestMapping(value = "/soldFilter", method = RequestMethod.GET)
	public ModelAndView addTripSheetOptions(final HttpServletRequest request) {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("soldFilter");
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	
	
	@RequestMapping(value = "/tyreSoldInvoice", method = RequestMethod.POST)
	public ModelAndView tyreSoldInvoice(@RequestParam("tyreNumber") final String TYRE_ID,
			@RequestParam("soldType") final String soldTypeStr) {
		short 					tyreStatus 				= 0 ;
		short 					soldType 				= 0 ;
		List<InventoryTyreDto> 	InventoryTyreDtoList 	= null; 
		Map<String, Object> 	model 					= new HashMap<String, Object>();
		try {
			soldType = Short.parseShort(soldTypeStr);
			
			if(soldType == TyreTypeConstant.AVAILABLE_TO_SOLD) {
				tyreStatus = InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE; // search available tyre
			}else if(soldType == TyreTypeConstant.SCRAPED_TO_SOLD) {
				tyreStatus = InventoryTyreDto.TYRE_ASSIGN_STATUS_SCRAPED;// search scraped tyre
			}
			
			InventoryTyreDtoList = 	tyreService.getTyreSoldDetails(TYRE_ID,tyreStatus);
			model.put("Tyre", InventoryTyreDtoList);
			model.put("soldType", soldType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("addTyreSoldInvoice", model);

	}
	
	@RequestMapping(value="/saveTyreSoldInvoice", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveTyreSoldInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			System.err.println("inside save tyresold ");
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreService.saveTyreSoldInvoice(valueInObject);
			return valueOutObject.getHtData();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueInObject 					= null;
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value = "/addTyreSoldDetails", method = RequestMethod.GET)
	public ModelAndView addTyreSoldDetails(@RequestParam("ID") final Long invoiceId, final HttpServletRequest request) throws Exception {
		ModelAndView 			map 				= null;
		ValueObject				valueInObject 		= null;
		ValueObject 			valueOutObject 		= null;
		try {
			valueInObject		= new ValueObject();
			valueInObject.put("invoiceId", invoiceId);
			valueOutObject		= tyreService.getTyreSoldDetailsByTyreSoldInvoiceId(valueInObject);
			
			if(valueOutObject.getString("tyreStatus") == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_PROCESS_NAME) {
				map = new ModelAndView("tyreSoldAddDetails");
			}else if(valueOutObject.getString("tyreStatus") == InventoryTyreDto.TYRE_ASSIGN_STATUS_SOLD_NAME) {
				map = new ModelAndView("tyreSoldCompleteDetails");
			}else {
				map = new ModelAndView("tyreSoldAddDetails");
			}
			map.addObject("invoiceId",invoiceId);
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	@RequestMapping(value="/getTyreSoldDetailsByTyreSoldInvoiceId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTyreSoldDetailsByTyreSoldInvoiceId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreService.getTyreSoldDetailsByTyreSoldInvoiceId(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/saveSoldTyreCost", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveSoldTyreCost(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			tyreService.saveSoldTyreCost(valueInObject);
			valueOutObject.put("updated", true);
			return valueOutObject.getHtData();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueInObject 					= null;
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value="/updateTyreSoldInvoiceDetils", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateTyreSoldInvoiceDetils(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			tyreService.updateTyreSoldInvoiceDetils(valueInObject);
			valueOutObject.put("updated", true);
			return valueOutObject.getHtData();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueInObject 					= null;
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value = "/allTyreSoldInvoice", method = RequestMethod.GET)
	public ModelAndView allTyreSoldInvoice(final HttpServletRequest request) throws Exception {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("allTyreSoldInvoice");
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("PartLocations", PLBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/getPageWiseTyreSoldInvoiceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPageWiseTyreSoldInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		valueInObject 					= null;
		ValueObject 		valueOutObject 					= null;
		
		try {
			valueInObject 				= new ValueObject(allRequestParams);
			valueOutObject 				= new ValueObject();
			
			valueOutObject		= tyreService.getPageWiseTyreSoldInvoiceDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value="/deleteTyreSoldInvoiceDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteTyreExpense(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreService.deleteTyreSoldInvoiceDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value = "/showTyreUsageHistory", method = RequestMethod.GET)
	public ModelAndView showTyreUsageHistory(@RequestParam("ID") final Long Tyre_id,final HttpServletRequest request) throws Exception {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("showTyreUsageHistory");
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("Tyre", ITBL.Show_InventoryTyre_Details(iventoryTyreService.Get_TYRE_ID_InventoryTyre(Tyre_id, userDetails.getCompany_id())));
			map.addObject("TyreHistory", tyreUsageHistoryService.getTyreUsageHistory(Tyre_id, userDetails.getCompany_id()));
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
}

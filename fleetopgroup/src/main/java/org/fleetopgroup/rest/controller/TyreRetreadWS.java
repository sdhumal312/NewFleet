package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.ITyreExpenseDetailsService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value = "/TyreRetreadWS")
public class TyreRetreadWS {
	
	@Autowired IInventoryTyreService   	inventoryTyreService;
	@Autowired ICompanyService 			companyService;
	@Autowired 	
	private ITyreExpenseDetailsService   	tyreExpensesDetailsService;
	
	@RequestMapping(value="/getTyreRetreadPrint", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getWorkOrderPrint(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		

		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		CustomUserDetails			userDetails						= null;
		long						TRID							= 0;
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TRID				= object.getLong("TRID", 0);
			
			valueOutObject.put("company_id", userDetails.getCompany_id());
			valueOutObject.put("companyDetails", companyService.getCompanyByID(userDetails.getCompany_id()));
			valueOutObject.put("InventoryTyreRetread", inventoryTyreService.Get_InventoryTyreRetread(TRID,userDetails.getCompany_id()));
			valueOutObject.put("InventoryTyreRetread_Amount",inventoryTyreService.Get_InventoryTyreRetread_Amount(TRID,userDetails.getCompany_id()));
			
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
			userDetails						= null;
			TRID							= 0;   
		}
	}
	
	@RequestMapping(value = "/getTyreRetreadInvoiceReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getConductorHistoryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = inventoryTyreService.getTyreRetreadInvoiceReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	@RequestMapping(value = "/updateTyreRetreadInvoice", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateTyreRetreadInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;
		try {
			object = new ValueObject(allRequestParams);
			//inventoryTyreService.updateTyreRetreadInvoice(object);
			return inventoryTyreService.updateTyreRetreadInvoice(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/addMultipleRetreadTyre", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveMultipleRetreadTyre(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

	ValueObject object = null;
	try {
	object = new ValueObject(allRequestParams);
	object.put("finalRetreadObject", JsonConvertor.toValueObjectFromJsonString(object.getString("finalRetreadObject")));
	return inventoryTyreService.saveMultipleRetreadTyre(object).getHtData();
	
	} catch (Exception e) {
	throw e;
	} 
	}
	
}

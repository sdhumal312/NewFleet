package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.TyreExpensesBL;
import org.fleetopgroup.persistence.model.TyreExpenses;
import org.fleetopgroup.persistence.serviceImpl.ITyreExpensesService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
@RestController
public class TyreExpensesWS {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired 	ITyreExpensesService   	tyreExpensesService   ;
	
	TyreExpensesBL	tyreExpensesBL 		= new TyreExpensesBL();
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/companyWiseTyreExpenseAutocomplete", method = RequestMethod.GET)
	public void companyWiseTyreExpenseAutocomplete(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValueObject					valueObject 			= null;
		List<TyreExpenses> 			tyreExpensesList		= null;
		List<TyreExpenses> 			finalTyreExpensesList	= null;
		try {
			valueObject				= new ValueObject();
			tyreExpensesList 		= new ArrayList<TyreExpenses>();
			finalTyreExpensesList 	= new ArrayList<TyreExpenses>();
			
			valueObject			= tyreExpensesService.getAllTyreExpenses();
			tyreExpensesList	= (List<TyreExpenses>) valueObject.get("tyreExpensesList");
			
			if(tyreExpensesList != null && !tyreExpensesList.isEmpty()) {	
				for (TyreExpenses dto : tyreExpensesList) {
					TyreExpenses 	tyreExpenses 	= new TyreExpenses();
					tyreExpenses.setTyreExpenseId(dto.getTyreExpenseId());
					tyreExpenses.setTyreExpenseName(dto.getTyreExpenseName());

					finalTyreExpensesList.add(tyreExpenses);
				}
			}

			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalTyreExpensesList));
		}catch(Exception e) {
			LOGGER.error("Err", e);
		}finally {
			valueObject 			= null;  
			tyreExpensesList		= null;  
			finalTyreExpensesList	= null;  
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/companyWiseTyreExpenseAutocomplete2", method = RequestMethod.POST)
	public void companyWiseTyreExpenseAutocomplete2(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValueObject					valueObject 			= null;
		List<TyreExpenses> 			tyreExpensesList		= null;
		List<TyreExpenses> 			finalTyreExpensesList	= null;
		
		try {
			valueObject				= new ValueObject();
			tyreExpensesList 		= new ArrayList<TyreExpenses>();
			finalTyreExpensesList 	= new ArrayList<TyreExpenses>();
			
			valueObject			= tyreExpensesService.getAllTyreExpenses2(term);
			tyreExpensesList	= (List<TyreExpenses>) valueObject.get("tyreExpensesList");
			
			if(tyreExpensesList != null && !tyreExpensesList.isEmpty()) {	
				for (TyreExpenses dto : tyreExpensesList) {
					TyreExpenses 	tyreExpenses 	= new TyreExpenses();
					tyreExpenses.setTyreExpenseId(dto.getTyreExpenseId());
					tyreExpenses.setTyreExpenseName(dto.getTyreExpenseName());

					finalTyreExpensesList.add(tyreExpenses);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finalTyreExpensesList));
		} catch (Exception e) {
			LOGGER.error("Err", e);
		}finally {
			valueObject 			= null;  
			tyreExpensesList		= null;  
			finalTyreExpensesList	= null;  
		}
	}
	@RequestMapping(value = "/TyreExpense", method = RequestMethod.GET)
	public ModelAndView addTripSheetOptions(final HttpServletRequest request) {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("addTyreExpense");
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value="/getAllTyreExpenses", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getAllTyreExpenses(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject();
			valueOutObject		= tyreExpensesService.getAllTyreExpenses();
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@RequestMapping(value="/addTyreExpense", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveVendorPayment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreExpensesService.saveTyreExpense(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/getTyreExpenseByTyreExpenseId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTyreExpenseByTyreExpenseId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreExpensesService.getTyreExpenseByTyreExpenseId(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/updateTyreExpense", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateTyreExpense(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreExpensesService.updateTyreExpense(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/deleteTyreExpense", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteTyreExpense(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreExpensesService.deleteTyreExpense(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
}

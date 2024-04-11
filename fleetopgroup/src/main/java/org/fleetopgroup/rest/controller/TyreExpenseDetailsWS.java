package org.fleetopgroup.rest.controller;

import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.TyreExpenseDetailsBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.ITyreExpenseDetailsService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
@RestController
public class TyreExpenseDetailsWS {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired 	ITyreExpenseDetailsService   	tyreExpensesDetailsService;
	@Autowired 	IPartLocationsService 			PartLocationsService;
	
	TyreExpenseDetailsBL	tyreExpensesDetailsBL 		= new TyreExpenseDetailsBL();
	
	
	@RequestMapping(value = "/TyreExpenseDetails", method = RequestMethod.GET)
	public ModelAndView addTripSheetOptions(final HttpServletRequest request) {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("AddTyreExpenseDetails");
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value="/getAllTyreExpenseDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getAllTyreExpenses(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject();
			valueOutObject		= tyreExpensesDetailsService.getAllTyreExpenseDetails();
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@RequestMapping(value="/getAllTyreExpenseDetailsByTyreTypeId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getAllTyreExpenseDetailsByTyreTypeId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		ValueObject 				valueInObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueInObject		= new ValueObject();
			valueOutObject		= tyreExpensesDetailsService.getAllTyreExpenseDetailsByTyreTypeId(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueInObject 					= null;
			valueOutObject 					= null;
		}
	}
	
	
	@PostMapping(value="/addTyreExpenseDetails", produces="application/json")
	public HashMap<Object, Object>  addTyreExpenseDetails(@RequestParam("tyreExpenseDetailsList") String allRequestParams,
			@RequestParam("input-file-preview") MultipartFile[] uploadfile) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject					valueOutObject 					= null;

		try {
			valueInObject		= new ValueObject();
			valueOutObject		= new ValueObject();
			valueInObject 		= JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			valueInObject.put("tyreExpenseDetailsList", JsonConvertor.toValueObjectFromJsonString(valueInObject.getString("tyreExpense")));
			
			valueOutObject 		= tyreExpensesDetailsService.saveTyreExpenseDetails(valueInObject,uploadfile);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@RequestMapping(value="/getAllExpensesByTyreId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getAllExpensesByTyreId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreExpensesDetailsService.getTyreExpenseDetailsByTyreId(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/getTyreExpenseDetailsByTyreExpenseDetailsId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTyreExpenseDetailsByTyreExpenseDetailsId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreExpensesDetailsService.getTyreExpenseDetailsByTyreExpenseDetailsId(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/updateTyreExpenseDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateTyreExpenseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreExpensesDetailsService.updateTyreExpenseDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/deleteTyreExpenseDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteTyreExpenseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= tyreExpensesDetailsService.deleteTyreExpenseDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/getTyreExpenseDetailsReport", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTyreExpenseDetailsReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= tyreExpensesDetailsService.getTyreExpenseDetailsReport(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/addRetreadTyreExpenseDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  addRetreadTyreExpenseDetails(@RequestParam("tyreExpenseDetailsList") String allRequestParams,
			@RequestParam("input-file-preview") MultipartFile[] uploadfile) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			valueInObject 		= JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			valueOutObject		= new ValueObject();
			valueInObject.put("tyreExpenseDetailsList", JsonConvertor.toValueObjectFromJsonString(valueInObject.getString("tyreExpenseDetailsList")));

			valueOutObject		= tyreExpensesDetailsService.saveRetreadTyreExpenseDetails(valueInObject,uploadfile);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/reOpenTyreRetreadAndDeleteTyreExpense", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  reOpenTyreRetreadAndDeleteTyreExpense(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueInObject.put("tyreExpenseDetailsList", JsonConvertor.toValueObjectFromJsonString(valueInObject.getString("tyreExpenseDetailsList")));
			
			valueOutObject		= tyreExpensesDetailsService.reOpenTyreRetreadAndDeleteTyreExpense(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@RequestMapping("/download/tyreExpenseDetailsDoc/{tyreExpenseDetailsDocumentId}")
	public String  downloadUreaInvoiceDocument(@PathVariable("tyreExpenseDetailsDocumentId") Long tyreExpenseDetailsDocumentId, HttpServletResponse response) throws Exception {
		CustomUserDetails 	userDetails 	=  null;
		try {
			if (tyreExpenseDetailsDocumentId <= 0) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.TyreExpenseDetailsDocument document = tyreExpensesDetailsService.getTyreExpenseDetailsDocumentId(tyreExpenseDetailsDocumentId, userDetails.getCompany_id());
				if (document != null) {
					if (document.getTyreExpenseDetailsContent() != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + document.getTyreExpenseDetailsFileName() + "\"");
						OutputStream  out = response.getOutputStream();
						response.setContentType(document.getTyreExpenseDetailsContentType());
						response.getOutputStream().write(document.getTyreExpenseDetailsContent());
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
	
}

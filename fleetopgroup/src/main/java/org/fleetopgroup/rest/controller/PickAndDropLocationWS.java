package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.TyreExpensesBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.PickAndDropLocation;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPickAndDropLocationService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
@RestController
public class PickAndDropLocationWS {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired private IPickAndDropLocationService   							pickAndDropLocationService;
	@Autowired private ICompanyConfigurationService 							companyConfigurationService;   
	
	TyreExpensesBL	tyreExpensesBL 		= new TyreExpensesBL();
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pickAndDropLocationAutoComplete", method = RequestMethod.POST)
	public void pickAndDropLocationAutoComplete(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValueObject						valueObject 			= null;
		List<PickAndDropLocation> 		locationList			= null;
		List<PickAndDropLocation> 		finallocationList		= null;
		
		try {
			valueObject				= new ValueObject();
			locationList 		= new ArrayList<PickAndDropLocation>();
			finallocationList 	= new ArrayList<PickAndDropLocation>();
			
			valueObject			= pickAndDropLocationService.getPickAndDropLocationAutoComplete(term);
			locationList	= (List<PickAndDropLocation>) valueObject.get("locationList");
			
			if(locationList != null && !locationList.isEmpty()) {	
				for (PickAndDropLocation dto : locationList) {
					PickAndDropLocation 	pickAndDropLocation 	= new PickAndDropLocation();
					pickAndDropLocation.setPickAndDropLocationId(dto.getPickAndDropLocationId());
					pickAndDropLocation.setLocationName(dto.getLocationName());

					finallocationList.add(pickAndDropLocation);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finallocationList));
		} catch (Exception e) {
			LOGGER.error("Err", e);
		}finally {
			valueObject 			= null;  
			locationList		= null;  
			finallocationList	= null;  
		}
	}
	@RequestMapping(value = "/PickAndDropLocation", method = RequestMethod.GET)
	public ModelAndView PickAndDropLocation(final HttpServletRequest request) {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("pickAndDropLocation");
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value="/getAllPickAndDropLocation", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getAllPickAndDropLocation(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject();
			valueOutObject		= pickAndDropLocationService.getAllPickAndDropLocation();
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@RequestMapping(value="/savePickAndDropLocation", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  savePickAndDropLocation(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= pickAndDropLocationService.savePickAndDropLocation(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/getPickAndDropLocationById", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTyreExpenseByTyreExpenseId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= pickAndDropLocationService.getPickAndDropLocationById(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/updatePickAndDropLocation", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updatePickAndDropLocation(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= pickAndDropLocationService.updatePickAndDropLocation(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	@RequestMapping(value="/deletePickAndDropLocation", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deletePickAndDropLocation(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= pickAndDropLocationService.deletePickAndDropLocation(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value = "/newTripSheetPickAndDrop", method = RequestMethod.GET)
	public ModelAndView newTripSheetPickAndDrop(final HttpServletRequest request) throws Exception {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("newTripSheetPickAndDrop");
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/searchTripsheetNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchTripsheetNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 					object 					= null;
		CustomUserDetails				userDetails				= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object 				= new ValueObject(allRequestParams);
			
			object.put("userDetails", userDetails);
			return pickAndDropLocationService.searchTripsheetNumber(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getTripSheetPickAndDropDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripSheetPickAndDropDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.getTripSheetPickAndDropDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleWiseTripSheetPickAndDropDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleWiseTripSheetPickAndDropDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.getVehicleWiseTripSheetPickAndDropDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/createPickAndDropTrip", method = RequestMethod.GET)
	public ModelAndView createPickAndDropTrip(final HttpServletRequest request) throws Exception {
		ModelAndView 					map 					  = null;
		HashMap<String, Object> 		configuration	          = null;
		CustomUserDetails 				userDetails 	          = null;
		Map<String, Object> 			model 					  = new HashMap<String, Object>();
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PICK_DROP_CONFIGURATION_CONFIG);
			
			model.put("configuration",configuration);
			model.put("numberOfDaysForBackDate",configuration.get("numberOfDaysForBackDate"));
			
			String minBackDate = DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("numberOfDaysForBackDate"));
			model.put("minBackDate",minBackDate);
			
			map = new ModelAndView("createPickAndDropTrip", model);
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/dispatchPickAndDropTrip", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  dispatchPickAndDropTrip(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.dispatchPickAndDropTrip(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/showDispatchedPickAndDropTrip", method = RequestMethod.GET)
	public ModelAndView showDispatchedPickAndDropTrip(@RequestParam("dispatchPickAndDropId") final Long dispatchPickAndDropId, 
			final HttpServletRequest request) {
		ModelAndView 					map 			= null;
		Map<String, Object> 			model 			= new HashMap<String, Object>();
		try {
			model.put("dispatchPickAndDropId", dispatchPickAndDropId);
			map = new ModelAndView("dispatchPickAndDropTrip", model);
			
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/getTripsheetPickDropDispatchDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getTripsheetPickDropDispatchDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 					object 						= null;
		CustomUserDetails				userDetails					= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object 				= new ValueObject(allRequestParams);
			
			object.put("userDetails", userDetails);
			return pickAndDropLocationService.getTripsheetPickDropDispatchDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editPickAndDropTrip", method = RequestMethod.GET)
	public ModelAndView editPickAndDropTrip(@RequestParam("editPickAndDropId") final Long editPickAndDropId, 
			final HttpServletRequest request) throws Exception {
		ModelAndView 					map 					  = null;
		Map<String, Object> 			model 					  = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	          = null;
		CustomUserDetails 				userDetails 	          = null;
		try {
			model.put("editPickAndDropId", editPickAndDropId);
			
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PICK_DROP_CONFIGURATION_CONFIG);
			
			model.put("configuration",configuration);
			model.put("numberOfDaysForBackDate",configuration.get("numberOfDaysForBackDate"));
			
			String minBackDate = DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("numberOfDaysForBackDate"));
			model.put("minBackDate",minBackDate);
			
			map = new ModelAndView("editPickAndDropTrip", model);
			
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/updatePickAndDropTrip", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updatePickAndDropTrip(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.updatePickAndDropTrip(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteTripsheetPickAndDrop", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteTripsheetPickAndDrop(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.deleteTripsheetPickAndDrop(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/pickAndDropReport", method = RequestMethod.GET)
	public ModelAndView pickAndDropReport(final HttpServletRequest request) {
		ModelAndView 					map 			= null;
		try {
			map = new ModelAndView("pickAndDropReport");
			return map;
			
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/partyWiseTripReort", method = RequestMethod.GET)
	public ModelAndView partyWiseTripReort(final HttpServletRequest request) {
		ModelAndView 					map 			= null;
		try {
			map = new ModelAndView("partyWiseTripReort");
			return map;
			
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	} 
	
	@RequestMapping(value = "/getPartyWiseTripReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartyWiseTripReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.getPartyWiseTripReport(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/createPickOrDropInvoice", method = RequestMethod.GET)
	public ModelAndView createPickOrDropInvoice(final HttpServletRequest request) {
		ModelAndView 					map 			= null;
		try {
			map = new ModelAndView("createPickOrDropInvoice");
			return map;
			
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/getInvoiceList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getInvoiceList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.getInvoiceList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/savePickOrDropInvoice", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  savePickOrDropInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("invoiceDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("invoiceDetails")));
			
			return pickAndDropLocationService.savePickOrDropInvoice(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/showPickAndDropInvoice", method = RequestMethod.GET)
	public ModelAndView showPickAndDropInvoice(@RequestParam("invoiceSummaryId") final Long invoiceSummaryId, 
			final HttpServletRequest request) {
		ModelAndView 					map 			= null;
		Map<String, Object> 			model 			= new HashMap<String, Object>();
		try {
			model.put("invoiceSummaryId", invoiceSummaryId);
			map = new ModelAndView("showPickAndDropInvoice", model);
			
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/getInvoiceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.getInvoiceDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/createPickOrDropInvoicePayment", method = RequestMethod.GET)
	public ModelAndView createPickOrDropInvoicePayment(final HttpServletRequest request) {
		ModelAndView 					map 			= null;
		try {
			map = new ModelAndView("createPickOrDropInvoicePayment");
			return map;
			
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/getInvoicePaymentList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getInvoicePaymentList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.getInvoicePaymentList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/savePickOrDropInvoicePayment", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  savePickOrDropInvoicePayment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("invoicePaymentDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("invoicePaymentDetails")));
			
			return pickAndDropLocationService.savePickOrDropInvoicePayment(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/partyWiseInvoicePaymentReort", method = RequestMethod.GET)
	public ModelAndView partyWiseInvoicePaymentReort(final HttpServletRequest request) {
		ModelAndView 					map 			= null;
		try {
			map = new ModelAndView("partyWiseInvoicePaymentReort");
			return map;
			
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/getPartyWiseInvoicePaymentReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartyWiseInvoicePaymentReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.getPartyWiseInvoicePaymentReport(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteInvoice", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteInvoice(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return pickAndDropLocationService.deleteInvoice(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
}

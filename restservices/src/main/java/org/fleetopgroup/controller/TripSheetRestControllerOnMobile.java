package org.fleetopgroup.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetMobileService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TripSheetRestControllerOnMobile {
	
	@Autowired	private	ITripSheetService								tripSheetService;
	@Autowired	private	ITripSheetMobileService							tripSheetMobileService;
	@Autowired  private ICompanyConfigurationService 					companyConfigurationService;
	@Autowired  private IVehicleService 								vehicleService;
	
	private final Logger LOGGER	= LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "/mobileVersionCheck", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileVersionCheck(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		int 							companyId 				= 0;
		ValueObject 					object 					= null;
		HashMap<String, Object> 		configuration			= null;
		try {
			object 				= new ValueObject(allRequestParams);
			companyId   		= object.getInt("companyId");
			
			configuration		= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.MOBILE_APP_VERSION_CHECK_CONFIG);
			object.put("configuration", configuration);
			
			return object.getHtData();
			
		} catch (Exception e) {
			LOGGER.error("createTripsheet:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchTripsheet", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchTripsheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.searchTripsheet(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("searchTripsheet:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value="/vehicleStatusCheckWhileCreatingTripSheet", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  vehicleStatusCheckWhileCreatingTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			
			valueOutObject = tripSheetMobileService.vehicleStatusCheckWhileCreatingTripSheet(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}

	@RequestMapping(value="/mobileAppRecentTripListByVehicleId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getRecentTripListByVehicleId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			object = new ValueObject(allRequestParams);
			
			valueOutObject = tripSheetService.getRecentTripListByVehicleId(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value = "/mobileCreateTripsheet", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileCreateTripsheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.createTripsheet(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("createTripsheet:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobileSaveOrDispatchTripsheet", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileSaveOrDispatchTripsheet(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		MultipartFile      uploadfile               = null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveOrDispatchTripsheet(object,request,uploadfile).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveOrDispatchTripsheet:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/showTripSheetDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  showTripSheetDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.showTripSheetDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("showTripSheetDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addTripSheetAdvanceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addTripSheetAdvanceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.addTripSheetAdvanceDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("addTripSheetAdvanceDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripSheetAdvanceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetAdvanceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripSheetAdvanceDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetAdvanceDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripSheetAdvanceDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripSheetAdvanceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripSheetAdvanceDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("removeTripSheetAdvanceDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addTripSheetExpenseDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addTripSheetExpenseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.addTripSheetExpenseDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("addTripSheetExpenseDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripSheetExpenseDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetExpenseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripSheetExpenseDetails(object,null).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetExpenseDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripSheetExpenseDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripSheetExpenseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripSheetExpenseDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("removeTripSheetExpenseDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addTripSheetIncomeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addTripSheetIncomeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.addTripSheetIncomeDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("addTripSheetIncomeDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripSheetIncomeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetIncomeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripSheetIncomeDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetIncomeDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripSheetIncomeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripSheetIncomeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripSheetIncomeDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("removeTripSheetIncomeDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addTripSheetDriverHaltDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addTripSheetDriverHaltDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.addTripSheetDriverHaltDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("addTripSheetDriverHaltDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripSheetDriverHaltDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetDriverHaltDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripSheetDriverHaltDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetDriverHaltDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripSheetDriverHaltDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripSheetDriverHaltDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripSheetDriverHaltDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("removeTripSheetDriverHaltDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addTripSheetFuelDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addTripSheetFuelDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.addTripSheetFuelDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("addTripSheetFuelDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeTripSheetFuelDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeTripSheetFuelDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.removeTripSheetFuelDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("removeTripSheetFuelDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editTripSheetDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editTripSheetDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.editTripSheetDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("editTripSheetDetails:", e);
			throw e;
		} 
	}
	
	// Duplicate Method
	/*
	 * @RequestMapping(value = "/updateTripSheetEditData", method =
	 * RequestMethod.POST, produces="application/json") public HashMap<Object,
	 * Object> updateTripSheetEditData(@RequestParam HashMap<Object, Object>
	 * allRequestParams) throws Exception { ValueObject object = null; try { object
	 * = new ValueObject(allRequestParams); return
	 * tripSheetMobileService.updateTripSheetEditData(object).getHtData();
	 * 
	 * } catch (Exception e) { LOGGER.error("updateTripSheetEditData:", e); throw e;
	 * } }
	 */
	
	@RequestMapping(value = "/updateTripSheetDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateTripSheetDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.updateTripSheetDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("updateTripSheetDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addTripSheetCloseDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addTripSheetCloseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.addTripSheetCloseDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("addTripSheetCloseDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripSheetCloseDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetCloseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripSheetCloseDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetCloseDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripComment", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripComment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripComment(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripComment:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addTripSheetPODDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addTripSheetPODDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.addTripSheetPODDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("addTripSheetPODDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveTripSheetPODDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripSheetPODDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.saveTripSheetPODDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("saveTripSheetPODDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteTripSheetDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteTripSheetDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.deleteTripSheetDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("deleteTripSheetDetails:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addcloseAccountTripSheet", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addcloseAccountTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.addcloseAccountTripSheet(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("addcloseAccountTripSheet:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/savecloseAccountTripSheet", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  savecloseAccountTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.savecloseAccountTripSheet(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("savecloseAccountTripSheet:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/showCloseAccountTripSheet", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  showCloseAccountTripSheet(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.showCloseAccountTripSheet(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("showCloseAccountTripSheet:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchTripSheetByVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchTripSheetByVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetMobileService.searchVehCurTSShow(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("showCloseAccountTripSheet:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getLastTripSheetDetailsToCopy", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getLastTripSheetDetailsToCopy(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.getVehicleDetailsOnTime(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getLastTripSheetDetailsToCopy:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getLastNextTripSheetDetailsFromMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getLastNextTripSheetDetailsFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getLastNextTripSheetDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("showCloseAccountTripSheet:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getLastNextTripSheetDetailsForEditMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getLastNextTripSheetDetailsForEditMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return tripSheetService.getLastNextTripSheetDetailsForEdit(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("showCloseAccountTripSheet:", e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveVoucherDateFromMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveVoucherDateFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {	
		ValueObject 	object 			= null;
		try {
			object              = new ValueObject(allRequestParams);			
			
			 tripSheetService.saveVoucherDate(object);
			 
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/saveTripsheetTallyCompanyFromMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveTripsheetTallyCompanyFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {	
		ValueObject 	object 			= null;
		try {
			object              = new ValueObject(allRequestParams);			
			
			 tripSheetService.updateTallyCompanyToTripSheet(object);
			 
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
}

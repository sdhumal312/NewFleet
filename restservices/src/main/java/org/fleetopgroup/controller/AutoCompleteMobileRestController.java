package org.fleetopgroup.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IBranchService;
import org.fleetopgroup.persistence.serviceImpl.ICorporateAccountService;
import org.fleetopgroup.persistence.serviceImpl.IDepartmentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.ILoadTypeService;
import org.fleetopgroup.persistence.serviceImpl.IPickAndDropLocationService;
import org.fleetopgroup.persistence.serviceImpl.ITallyIntegrationService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.ITripIncomeService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleFuelService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutoCompleteMobileRestController{
	
	@Autowired private IDriverService						DriverService;
	@Autowired private ITripRouteService					TripRouteService;
	@Autowired private ILoadTypeService						LoadTypeService;
	@Autowired private IVehicleService						vehicleService;
	@Autowired private ITripExpenseService					tripExpenseService;
	@Autowired private ITripIncomeService					tripIncomeService;
	@Autowired private IVehicleFuelService					vehicleFuelService;
	@Autowired private ICorporateAccountService				CorporateAccountService;
	@Autowired private IPickAndDropLocationService			PickAndDropLocationService;
	@Autowired private IVendorService						vendorService;
	@Autowired private ITallyIntegrationService				TallyIntegrationService;
	@Autowired private IBranchService						BranchService;
	@Autowired private IDepartmentService					DepartmentService;
	@Autowired private IUserProfileService					UserProfileService;
	
	private final Logger LOGGER	= LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping(value = "/mobileDriverNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileDriverNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return DriverService.getDriverNameWiseList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("DriverNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobileCleanerNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileCleanerNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return DriverService.getCleanerNameWiseList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("CleanerNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobileTripRouteNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileTripRouteNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return TripRouteService.getTripRouteNameWiseList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("TripRouteNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobileLoadTypeNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileLoadTypeNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return LoadTypeService.getLoadTypeNameWiseList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("TripRouteNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value="/mobileAppGetVehicleList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleListForFuelEntry(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		ValueObject		object		 		= null;
		try {
			
			object = new ValueObject(allRequestParams);
			valueOutObject = vehicleService.getVehicleList(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value = "/getFuelTypeNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getFuelTypeNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return vehicleFuelService.getFuelTypeNameWiseList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("FuelTypeNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobileTripExpenseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileTripExpenseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return tripExpenseService.getTripExpenseList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("mobileTripExpenseList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobileTripIncomeList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileTripIncomeList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return tripIncomeService.mobileTripIncomeList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("mobileTripIncomeList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobilePartyNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobilePartyNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return CorporateAccountService.mobilePartyNameList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("TripRouteNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobilePickOrDropLocationNameWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobilePickOrDropLocationNameWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return PickAndDropLocationService.locationNameList(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("TripRouteNameList:"+ e);
			throw e;
		} 
	}
	@RequestMapping(value = "/getOtherVendorSearchListForMobile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getOtherVendorSearchListForMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			return vendorService.getOtherVendorSearchListForMobile(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("VendorSearchList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/mobileTallyCompanyWiseList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  mobileTallyCompanyWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return TallyIntegrationService.getTallyComapnyListForMobile(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("mobileTallyCompanyWiseList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getBranchNameList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getBranchNameList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return BranchService.searhByBranchNameInMobile(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getBranchNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getDpartmentNameList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDpartmentNameList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return DepartmentService.searhByDepartmentNameInMobile(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getDpartmentNameList:"+ e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getUserNameList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUserNameList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return UserProfileService.searchUserListInMobile(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("getDpartmentNameList:"+ e);
			throw e;
		} 
	}
	
	
}

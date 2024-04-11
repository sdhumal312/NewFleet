package org.fleetopgroup.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetStatus;
import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IPickAndDropLocationService;
import org.fleetopgroup.persistence.serviceImpl.ITicketIncomeApiService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetMobileService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TripSheetRestController {

	@Autowired	ITicketIncomeApiService			ticketIncomeApiService;
	@Autowired	IPickAndDropLocationService		PickAndDropLocationService;
	@Autowired	private ITripSheetService		tripSheetService;
	@Autowired  private	IVehicleService							vehicleService;
	@Autowired  private  IDriverService							driverService;
	@Autowired  private	ITripSheetMobileService					tripSheetMobileService;
	@Autowired	private IFuelService 							fuelService;
	@Autowired private 	ICompanyConfigurationService 			companyConfigurationService;
	@Autowired private 	IUserService							userService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/addMantisTripSheetIncome", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  addMantisTripSheetIncome(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		ValueObject 		outobject 				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			outobject	=  ticketIncomeApiService.getVehicleTicketIncome(object);
			
			return outobject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
			outobject 				= null;
		}
	}
	
	@RequestMapping(value="/savePickOrDropDispatchMobileData", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  savePickOrDropDispatchMobileData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		ValueObject 		outobject 				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			outobject	=  PickAndDropLocationService.savePickOrDropDispatchMobileData(object);
			
			return outobject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
			outobject 				= null;
		}
	}
	
	@RequestMapping(value="/getPickOrDropDispatchMobileData", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getPickOrDropDispatchMobileData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 					object 					= null;
		ValueObject 					outobject 				= null;
		int								companyId				= 0;
		long							userId					= 0;
		CustomUserDetails				userDetails				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			companyId    		= object.getInt("companyId");
			userId				= object.getLong("userId");
			userDetails			= new CustomUserDetails(companyId, userId);
			
			object.put("userDetails", userDetails);
			outobject	=  PickAndDropLocationService.getTripsheetPickDropDispatchDetails(object);
			
			return outobject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
			outobject 				= null;
		}
	}
	
	@RequestMapping(value="/editTripsheetPickDropDispatchDetailsFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  editTripsheetPickDropDispatchDetailsFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 					object 					= null;
		ValueObject 					outobject 				= null;
		int								companyId				= 0;
		long							userId					= 0;
		CustomUserDetails				userDetails				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			companyId    		= object.getInt("companyId");
			userId				= object.getLong("userId");
			userDetails			= new CustomUserDetails(companyId, userId);
			
			object.put("userDetails", userDetails);
			outobject	=  PickAndDropLocationService.editTripsheetPickDropDispatchDetailsFromMobile(object);
			
			return outobject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
			outobject 				= null;
		}
	}
	
	@RequestMapping(value="/updateTripsheetPickDropDispatchDetailsFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateTripsheetPickDropDispatchDetailsFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 					object 					= null;
		ValueObject 					outobject 				= null;
		int								companyId				= 0;
		long							userId					= 0;
		CustomUserDetails				userDetails				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			companyId    		= object.getInt("companyId");
			userId				= object.getLong("userId");
			userDetails			= new CustomUserDetails(companyId, userId);
			
			object.put("userDetails", userDetails);
			outobject	=  PickAndDropLocationService.updateTripsheetPickDropDispatchDetailsFromMobile(object);
			
			return outobject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
			outobject 				= null;
		}
	}
	
	@RequestMapping(value="/searchPickOrDropTripsheetFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  searchPickOrDropTripsheetFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 					object 					= null;
		ValueObject 					outobject 				= null;
		int								companyId				= 0;
		long							userId					= 0;
		CustomUserDetails				userDetails				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			companyId    		= object.getInt("companyId");
			userId				= object.getLong("userId");
			userDetails			= new CustomUserDetails(companyId, userId);
			
			object.put("userDetails", userDetails);
			outobject	=  PickAndDropLocationService.searchTripsheetNumber(object);
			
			return outobject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
			outobject 				= null;
		}
	}
	
	@RequestMapping(value="/searchPickAndDropTripByVehicle", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  searchPickAndDropTripByVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		ValueObject 		outobject 				= null;
		try {
			object 		= new ValueObject(allRequestParams);
			outobject	=  PickAndDropLocationService.searchPickAndDropTripByVehicle(object);
			
			return outobject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
			outobject 				= null;
		}
	}
	
	@RequestMapping(value="/getTripSheetDetailsByVehicleNumber", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTripSheetDetailsByVehicleNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			String vehicleNumber = object.getString("vehicleNumber");
			
			String date=null;
			if(object.getString("date")!=null && !object.getString("date").isEmpty())
			{
			   date = DateTimeUtility.getSqlStrDateFromStrDate(object.getString("date"), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);	
			    object =  tripSheetService.getTripSheetDetailsByVehicleNoAndTripOpenCloseDate(vehicleNumber, date);
			    if(object.containsKey("tripsheet"))
			    	return tripSheetService.getTripSheetDetailsByVehicleNoAndTripOpenCloseDate(vehicleNumber, date).getHtData();
			    else
			    	return  tripSheetService.getTripSheetDetailsByNumber(new ValueObject(allRequestParams).getString("vehicleNumber")).getHtData();
			}
			else
			{
				return  tripSheetService.getTripSheetDetailsByNumber(new ValueObject(allRequestParams).getString("vehicleNumber")).getHtData();
			}
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value="/testing", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  testingMethode(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		try {
			System.err.println("  allRequestParams  *** "+allRequestParams);
			return allRequestParams;
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/TripsheetAdd_API", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  CreateTripsheet(@RequestBody HashMap<Object, Object> requestBody, HttpServletRequest request) throws Exception {
		ValueObject 					object 					= null;
		HashMap<Object, Object>   		reponse 				= new  HashMap<Object, Object>();
		MultipartFile					uploadfile 				= null;
		String							Registration			= null;
		Vehicle							vehicle					= null;
		ValueObject						valueObject 			= null;
		Integer 						companyId				= null;
		Integer							driverId				= null;
		Integer							driver2Id				= null;
		User						    user 					= null;
		HashMap<String, Object> 		configuration			= null;
		

		try {
			System.err.println("requestBody -- "+ requestBody);
			
			object 				= new ValueObject(requestBody);
			Registration = object.getString("vehicleNumber");
			vehicle = vehicleService.GetVehicleByRegNo(Registration);

			driverId  = (object.getString("driver1DL") !="" && StringUtils.isNotEmpty(object.getString("driver1DL")) && object.getString("driver1DL") !=null) ? driverService.getDriverByDriverLicense(object.getString("driver1DL")) : 0;
			driver2Id = (object.getString("driver2DL") !="" && StringUtils.isNotEmpty(object.getString("driver2DL")) && object.getString("driver1DL") !=null) ? driverService.getDriverByDriverLicense(object.getString("driver2DL")) : 0;
			
			if(vehicle !=null) {
				companyId = vehicle.getCompany_Id();
				object.put("vid", vehicle.getVid());
				object.put("tripstatusId", TripSheetStatus.TRIP_STATUS_DISPATCHED);
				object.put("companyId", vehicle.getCompany_Id());
				object.put("backDateDispatchDate", object.getString("fromDate"));
				object.put("backDateDispatchTime", object.getString("dispatchByTime"));
				
				configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				user = userService.findUserByemailId((String)configuration.get("LMTautoUser"), companyId);
				object.put("userId", user.getId());
				
				
				if((driverId != null && driverId >0) || (driver2Id >0 && driver2Id != null) ) {
					object.put("driverId", driverId);
					object.put("driver2Id",driver2Id);
					
					try {
						valueObject = tripSheetMobileService.saveOrDispatchTripsheet(object,request,uploadfile);
						reponse.put(
							    (valueObject.getBoolean("saveTripSheet")) ? "TripsheetSucess" : "TripsheetError",
							    (valueObject.getBoolean("saveTripSheet")) ? "Tripsheet Created Successfully" : "Couldn't Create Tripsheet"
							);
						reponse.put("tripsheetID",valueObject.getInt("tripsheetID"));
					   }catch(Exception e) {
							e.printStackTrace();
					   }
				}else{
					reponse.put("DriverError" , "Driver Not Found with DL number "+ object.getString("driver1DL") + " , " +object.getString("driver2DL"));
					reponse.put("TripsheetError", "Coludn't Created Tripsheet");
					return reponse;
				}
			}else {
				reponse.put("VehicleError" , "This " +object.getString("vehicleNumber") + " vehicle Not Found");
				return reponse;
			}
			
		} catch (Exception e) {
			System.err.println("e");
			
			throw e;
		} 
		return reponse;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tripSheetClose_API", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  CloseTripsheetMethod(@RequestBody HashMap<Object, Object> requestBody, HttpServletRequest request) throws Exception {
		HashMap<Object, Object>   		reponse 				= new  HashMap<Object, Object>();
		String							Registration			= null;
		Vehicle							vehicle					= null;
		Integer 						companyId				= null;
		ArrayList<ValueObject>			fulearray 				= null;
		ArrayList<ValueObject>			expensearray 			= null;
		ValueObject						fuelObject			    = null;	
		ValueObject 					object					= null;
		User						    user 					= null;
		HashMap<String, Object> 		configuration			= null;
		Fuel 							fuel					= null;
		TripSheet						trip 					= null;
		
		try {
		    object 				= new ValueObject(requestBody);
			Registration 		= object.getString("vehicleNumber");
			vehicle 			= vehicleService.GetVehicleByRegNo(Registration);
			trip 				= tripSheetService.getTripsheetByLhpvId(object.getInt("tripsheetId"));
			
			if(vehicle != null) {
				companyId = vehicle.getCompany_Id();
				object.put("vid", vehicle.getVid());
				object.put("vehicleReg", vehicle.getVehicle_registration());
				object.put("companyId", companyId);
				
				configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				user = userService.findUserByemailId((String)configuration.get("LMTautoUser"), companyId);
				object.put("userId", user.getId());
				
				//exp starts
				if(object.containsKey("charges") && object.get("charges") != null) {
					object.put("expenseArray", JsonConvertor.toValueObjectFromJsonString(objectMapper.writeValueAsString(object.get("charges"))));
					expensearray 	= (ArrayList<ValueObject>) object.get("expenseArray");;
					
					if(!expensearray.isEmpty()) {
						object = tripSheetMobileService.saveTripsheetExpensesFromClosTripApi(expensearray,object);
	
						if(object.getBoolean("ExpenseSave"))
							reponse.put("ExpenseSuccess", "Expenses Added Successfully ! For Amount"+object.getString("createdExpense"));
						if(object.getBoolean("already"))
							reponse.put("ExpenseError", "Couldn't Add Expenses For Amount"+ object.getString("alreadyExpense") + " Expense Already Exist" );
					}
				}
				//expense ends
				
				
				//fuel starts
				
				if(object.containsKey("fuelDetails") && object.get("fuelDetails") != null) {	
					object.put("FuelArray", JsonConvertor.toValueObjectFromJsonString(objectMapper.writeValueAsString(object.get("fuelDetails"))));
					fulearray = (ArrayList<ValueObject>) object.get("FuelArray");
					
					if(!fulearray.isEmpty()){
						fuelObject = fuelService.saveTripsheetFuelDetailsFromClosTripApi(fulearray,object, vehicle);

						if(fuelObject.getString("alreadyFuel").length() >0 && fuelObject.getString("alreadyFuel") !=null){
							reponse.put("FuleError", "Coudn't Add Fuel For Amount"+ fuelObject.get("alreadyFuel") + " Fuel Entry Already Exits");
						}
						if(fuelObject.getString("createdFuel").length() >0 && fuelObject.getString("createdFuel") !=null){
							reponse.put("FuleSucces", "Fuel Added Successfully ! For Amount"+fuelObject.get("createdFuel"));
						}
						//get last fuel entry.. for closing km
						fuel 	= fuelService.getFuelMeterByTripsheetIdByDateTime(object.getLong("tripsheetId"), companyId);
						object.put("tripClosingKM", fuel.getFuel_meter());
					}
				}
				//fuel ends
				
				if(object.getInt("tripClosingKM") ==0) {
					object.put("tripClosingKM", trip.getTripOpeningKM());
				}
				
				//close tripsheet 
				object = tripSheetMobileService.saveTripSheetCloseDetails(object);
				if(object.getBoolean("closeTripSheet")) {
					reponse.put("TripsheetSuccess", "Tripsheet Closed Successfully !!");
				}else {
					reponse.put("TripsheetError", "Couldn't Close Tripsheet !!");
				}
				
			}else {
				reponse.put("VehicleError" , "This " +object.getString("vehicleNumber") + " vehicle Not Found");
			}
			
			System.err.println("response "+ reponse);	
			
		} catch (Exception e) {
			LOGGER.error("TripSheet :"+ e);
			throw e;
		} 
		return reponse;
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/addBlhpvCharges", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  addBlhpvCharges(@RequestBody HashMap<Object, Object> requestBody, HttpServletRequest request) throws Exception {
		HashMap<Object, Object>   		response 				= new  HashMap<Object, Object>();
		Integer 						companyId				= null;
		ArrayList<ValueObject>			expensearray 			= null;
		ValueObject 					object					= null;
		TripSheet						trip 					= null;
		User						    user 					= null;
		HashMap<String, Object> 		configuration			= null;
		
		try {
		    object 				= new ValueObject(requestBody);
			trip = tripSheetService.getTripsheetByLhpvId(object.getInt("lhpvId"));
			
				companyId = trip.getCompanyId();
				
				object.put("vid", trip.getVid());
				object.put("companyId", companyId);
				object.put("tripsheetId", trip.getTripSheetID());
				
				configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				user = userService.findUserByemailId((String)configuration.get("LMTautoUser"), companyId);
				object.put("userId", user.getId());
				
				if(trip != null ) {
					//exp starts
					if(object.containsKey("blhpvCharges") && object.get("blhpvCharges") != null) {
						object.put("expenseArray", JsonConvertor.toValueObjectFromJsonString(objectMapper.writeValueAsString(object.get("blhpvCharges"))));
						expensearray 	= (ArrayList<ValueObject>) object.get("expenseArray");;
						
						if(!expensearray.isEmpty()) {
							object = tripSheetMobileService.saveTripsheetExpensesFromClosTripApi(expensearray,object);
							
							if(object.getBoolean("ExpenseSave"))
								response.put("ExpenseSuccess", "Expenses Added Successfully ! For Amount"+object.getString("createdExpense"));
							if(object.getBoolean("already"))
								response.put("ExpenseError", "Couldn't Add Expenses For Amount"+ object.getString("alreadyExpense") + " Expense Already Exist" );
						}
					}
				}else {
					response.put("TripsheetError", "Couldn`t Add Charges , Tripsheet Not Found for LhpvId "+ object.getInt("lhpvId"));
				}
				//expense ends
			
			System.err.println("response "+ response);	
			
		} catch (Exception e) {
			LOGGER.error("TripSheet :"+ e);
			throw e;
		} 
		return response;
	}
	
}

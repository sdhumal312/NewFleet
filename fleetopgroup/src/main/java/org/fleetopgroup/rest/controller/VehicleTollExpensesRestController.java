package org.fleetopgroup.rest.controller;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.TollExpenseData;
import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;

@RestController
public class VehicleTollExpensesRestController {
	
	@Autowired	ITollExpensesDetailsService		tollExpensesDetailsService;
	@Autowired	IVehicleProfitAndLossService	vehicleProfitAndLossService;
	@Autowired	IVehicleService					vehicleService;

	@RequestMapping(value="/getVehicleTollExpenseDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleTollExpenseDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		ValueObject 		outobject 				= null;
		CustomUserDetails 	userDetails 			= null;
		try {
			object 				= new ValueObject(allRequestParams);
			userDetails 	    = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object.put("userDetails", userDetails);
			object.put("calledAfterClose", true);
			
			VehicleDto	vehicle = vehicleService.Get_Vehicle_Current_Status_TripSheetID(object.getInt("vid"), userDetails.getCompany_id());
			
			object.put("vehicle", vehicle);
			object.put("tollid", vehicle.getVehicleTollId());
			
			
			outobject	=  tollExpensesDetailsService.addVehicleTripTollExpenses(object);
			
			if(outobject != null) {
				if(outobject.containsKey("objectForPlICICI") && outobject.get("objectForPlICICI") != null) {
					vehicleProfitAndLossService.runThreadForTripSheetExpenses((ValueObject)outobject.get("objectForPlICICI"));
				}
				if(outobject.containsKey("objectForPlKVB") && outobject.get("objectForPlKVB") != null) {
					vehicleProfitAndLossService.runThreadForTripSheetExpenses((ValueObject)outobject.get("objectForPlKVB"));
				}
				if(outobject.containsKey("objectForPlHDFC") && outobject.get("objectForPlHDFC") != null) {
					vehicleProfitAndLossService.runThreadForTripSheetExpenses((ValueObject)outobject.get("objectForPlHDFC"));
				}
				if(outobject.containsKey("objectForPlYesBank") && outobject.get("objectForPlYesBank") != null) {
					vehicleProfitAndLossService.runThreadForTripSheetExpenses((ValueObject)outobject.get("objectForPlYesBank"));
				}
				if(outobject.containsKey("objectForPlKarins") && outobject.get("objectForPlKarins") != null) {
					vehicleProfitAndLossService.runThreadForTripSheetExpenses((ValueObject)outobject.get("objectForPlKarins"));
				}
			}
			
			return outobject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 					= null;
			outobject 				= null;
		}
	}
	
	@RequestMapping(value="/getTollExpensesDetailsList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getTollExpensesDetailsList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		long 				tripSheetId				= 0;
		CustomUserDetails 			userDetails 		= null;
		try {
			object 				= new ValueObject(allRequestParams);
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tripSheetId			= object.getLong("tripSheetId");
			
			return tollExpensesDetailsService.getTollExpensesDetailsList(tripSheetId,userDetails.getCompany_id()).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@PostMapping(value = "/karinsFasttagExpenses", produces="application/json")
	public ResponseEntity<Object> karinsfasttagexpenses(@RequestBody List<TollExpenseData>  tollExpenseList, HttpServletRequest	request) throws Exception {
		ValueObject 		object 					= null;
		try {
			if(request.getHeader("API_KEY") != null && request.getHeader("API_KEY").equalsIgnoreCase("a9b5f86e-44ed-4b2c-8a6e-017a57ca7702")) {
				object = tollExpensesDetailsService.karinsFasttagExpenses(tollExpenseList);
				
			}else
				new ResponseEntity<Object>("UnAuthorized Access!", HttpStatus.UNAUTHORIZED);
			
			return new ResponseEntity<Object>(object.getHtData(), HttpStatus.OK);
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value="timeZoneTest", produces="application/json", method=RequestMethod.GET)
	public String  timeZoneTest(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest	request,
			TimeZone	timeZone) throws Exception {
		try {
			
	        String timezoneOffset = request.getHeader("X-TIMEZONE-OFFSET");

	        System.err.println("timezoneOffset : "+timezoneOffset);
	        
	        if (timezoneOffset == null) {
	            OffsetDateTime offsetDateTime = OffsetDateTime.now();
	            ZoneOffset zoneOffset = offsetDateTime.getOffset();
	            int totalSeconds = zoneOffset.getTotalSeconds();
	            int hours = totalSeconds / 3600;
	            int minutes = (totalSeconds % 3600) / 60;
	            timezoneOffset = String.format("%+03d:%02d", hours, minutes);
	        }
	        System.err.println("timezoneOffset after  : "+timezoneOffset);
	        
	        System.err.println("timeZone : "+timeZone.getDisplayName());
	        
	        TimeZone timezone2 = RequestContextUtils.getTimeZone(request);

	        
	        System.err.println("timeZone   2 : "+timezone2.getDisplayName());
	        
			return "Bank Statement Pojo Completed Succesfully !";
			
		} catch (Exception e) {
			throw e;
		}
	}
}

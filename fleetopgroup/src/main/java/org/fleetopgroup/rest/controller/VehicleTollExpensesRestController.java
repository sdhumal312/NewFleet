package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

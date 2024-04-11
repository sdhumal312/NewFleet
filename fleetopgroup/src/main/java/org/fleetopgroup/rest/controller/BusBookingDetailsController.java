package org.fleetopgroup.rest.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.IBusBookingDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BusBookingDetailsController {
	
	@Autowired	IBusBookingDetailsService		busBookingDetailsService;
	@Autowired	ICompanyConfigurationService	companyConfigurationService;
	
	SimpleDateFormat	dateFormatSQL 			= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat	dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
	
	@RequestMapping(value = "/viewBusBookingDetails", method = RequestMethod.GET)
	public ModelAndView  viewVendorPaymentList() throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		try {
			
			return new ModelAndView("viewBusBookingDetails", model);
		} catch (NullPointerException e) {
			throw e;
		} 
	}

	@RequestMapping(value = "/getBusBookingDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getBusBookingDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return busBookingDetailsService.getBusBookingDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/addBusBookingDetails")
	public ModelAndView addBusBookingDetails() throws Exception {
		Map<String, Object> 		model 				= null;
		try {
			model				= new HashMap<String, Object>();
			
			CustomUserDetails	userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.BUS_BOOKING_CONFIGURATION_CONFIG);
			model.put("configuration", configuration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			
			return new ModelAndView("addBusBookingDetails", model);
		} catch (Exception e) {
			throw e;
		} finally {
			model				= null;
		}
	}
	
	@RequestMapping(value="/saveBusBookingDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveBusBookingDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			
			return busBookingDetailsService.saveBusBookingDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value="/deleteBusBooking", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteBusBooking(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= busBookingDetailsService.deleteBusBookingDetails(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}

	@RequestMapping(value = "/showBusBookingDetails", method = RequestMethod.GET)
	public ModelAndView showBusBookingDetails(@RequestParam("Id") final Long busBookingDetailsId,
			final HttpServletRequest request) {
		ModelAndView map = new ModelAndView("showBusBookingDetails");
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map.addObject("busBookingDetailsId",busBookingDetailsId);
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
		} catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	
	@RequestMapping(value="/getShowBusBookingDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getShowBusBookingDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= busBookingDetailsService.getShowBusBookingDetails(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	
	
	@GetMapping(path="/BusBookingCalendar")
	public ModelAndView ShowDriverAd(
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = 1;
			c.set(year, month, day);
			int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			String driverStart = dateFormat.format(c.getTime());
			// get date starting date
			model.put("startDate", "" + driverStart + "");
			// get date ending date
			c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
			String driverEnd = dateFormat.format(c.getTime());
			model.put("startEnd", "" + driverEnd + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowBusBookingCalendar", model);
	}
	
	
	
	
	@RequestMapping(value="/getBusBookingDetailsCelender", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getBusBookingDetailsCelender(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

	try {
		
		
		object 				= new ValueObject(allRequestParams);
		valueOutObject = busBookingDetailsService.getBusBookingCalendarDetails(object);
		return valueOutObject.getHtData();
		
	} catch (Exception e) {
		throw e;
	}
	
	}
	
	@RequestMapping(value="/getBusBookingVehicleTypewiseCount", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object,Object> getBusBookingVehicleTypewiseCount(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject valueOutObject = null;
		ValueObject object = null;
		
		try {
			object =new ValueObject(allRequestParams);
			valueOutObject= busBookingDetailsService.getMonthlyVehicleWiseCount(object);
			return valueOutObject.getHtData();
			
		}catch (Exception e) {
			throw e;
		}
		
		
	}
	
	@RequestMapping("/ShowBusBookingCalenderPre")
	public ModelAndView ShowBusBookingCalenderPre(
			@RequestParam("start") final String startDate, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			@SuppressWarnings("unused")
			java.util.Date date = dateFormatSQL.parse(startDate);// all done
			Calendar c = dateFormatSQL.getCalendar();
			c.add(Calendar.DATE, -1);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = 1;
			c.set(year, month, day);
			int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			// System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
			// driver Starting Day
			String driverStart = dateFormat.format(c.getTime());
			model.put("startDate", "" + driverStart + "");
			c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
			// driver Ending Date
			String driverEnd = dateFormat.format(c.getTime());
			model.put("startEnd", "" + driverEnd + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowBusBookingCalendar", model);
	}
	
	@RequestMapping("/ShowBusBookingCalenderNext")
	public ModelAndView ShowBusBookingCalenderNext(
			@RequestParam("end") final String endDate, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			@SuppressWarnings("unused")
			java.util.Date date = dateFormatSQL.parse(endDate);// all done
			Calendar c = dateFormatSQL.getCalendar();
			c.add(Calendar.DATE, 1);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = 1;
			c.set(year, month, day);
			int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			// System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
			// driver Starting date
			String driverStart = dateFormat.format(c.getTime());
			model.put("startDate", "" + driverStart + "");
			c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
			// driver Ending Date
			String driverEnd = dateFormat.format(c.getTime());
			model.put("startEnd", "" + driverEnd + "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowBusBookingCalendar", model);
	}
	
	
	@RequestMapping(value="/getBusBookingListOftheDay", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getBusBookingListOftheDay(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject = busBookingDetailsService.getBusBookingListOftheDay(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value="/getVehicleTypeWiseList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleTypeWiseList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject valueOutObject=null;
		ValueObject object=null;
		try {
		object=new ValueObject(allRequestParams);
		
		valueOutObject= busBookingDetailsService.getVehicleTypeWiseBusBookingList(object);
		return valueOutObject.getHtData();
		}catch (Exception e) {
				throw e;
		}
	
	}
	
}

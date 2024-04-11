package org.fleetopgroup.web.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDailyInspectionService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VehicleReportRestController {
	
	@Autowired	private IVehicleService	vehicleService;
	@Autowired	private IVehicleDailyInspectionService		vehicleDailyInspectionService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@RequestMapping("/VehicleCommentReport")
	public ModelAndView DepotWiseAttReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Vehicle_Comment_Report", model);
	}
	
	
	@RequestMapping("/depotwisePartsconsumedreport")
	public ModelAndView depotwisePartsconsumedreport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("depot_wise_Parts_consumed_report", model);
	}
	
	@RequestMapping(value = "/getVehicleCommentReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleCommentReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject	=	vehicleService.getVehicleCommentList(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping("/VehicleWisePartConsumptionAndUsageReport")
	public ModelAndView VehicleWisePartConsumptionAndUsageReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("VehicleWisePartConsumptionAndUsageReport", model);
	}
	
	/*Vehicle Creation Report By Dev Yogi Start Part 1 */
	@RequestMapping(value = "/VehicleCreationReport")
	public ModelAndView VehicleCreationReport() throws Exception {
		Map<String, Object> 		model 				= null;
		try {
			model				= new HashMap<String, Object>();
			
			return new ModelAndView("VehicleCreationReport", model); 
		} catch (Exception e) {
			throw e;
		}
		finally {
			model				= null;
		}
	}	
	/*For Office Use  Vehicle Creation Report By Dev Yogi End Part 1 */
	
	
	/*Vehicle Creation Report By Dev Yogi Start Part 2 */
			@SuppressWarnings("unchecked")
			@RequestMapping(value = "/VehicleCreationReportInfo", method = RequestMethod.POST, produces="application/json")
			public HashMap<Object, Object>  VehicleCreationReportInfo(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
				
				
				ValueObject 				object 					= null;
				ValueObject					valueOutObject 			= null;
				List<VehicleDto> 			DtosActive 				= null;
				List<VehicleDto> 			DtosInactive 			= null;
				List<VehicleDto> 			DtosSold 				= null;
				List<VehicleDto> 			finalListOfVehicle 		= null;
				Map<Integer,VehicleDto> 	finalMapOfVehicle 		= null;
				VehicleDto 					vehicleDto 				= null;
				
				try {
				
					object 				= new ValueObject(allRequestParams);
					valueOutObject 		= vehicleService.getVehicleCreationReport(object);
					
					
					DtosActive		= (List<VehicleDto>) valueOutObject.get("Active");
					DtosInactive	= (List<VehicleDto>) valueOutObject.get("Inactive");
					DtosSold		= (List<VehicleDto>) valueOutObject.get("Sold");
					
					finalListOfVehicle	= new ArrayList<VehicleDto>();
					if(DtosActive!=null)
					finalListOfVehicle.addAll(DtosActive);
					if(DtosInactive!=null)
					finalListOfVehicle.addAll(DtosInactive);
					if(DtosSold!=null)
					finalListOfVehicle.addAll(DtosSold);
					
					finalMapOfVehicle	= new HashMap<Integer, VehicleDto>();
					for(int i = 0; i < finalListOfVehicle.size(); i++) {
						
						if(finalMapOfVehicle.containsKey(finalListOfVehicle.get(i).getVehicleCompanyId())) {
							vehicleDto			= finalMapOfVehicle.get(finalListOfVehicle.get(i).getVehicleCompanyId());
							
							vehicleDto.setVehicleActiveCount(vehicleDto.getVehicleActiveCount() + finalListOfVehicle.get(i).getVehicleActiveCount());
							vehicleDto.setVehicleInActiveCount(vehicleDto.getVehicleInActiveCount() + finalListOfVehicle.get(i).getVehicleInActiveCount());
							vehicleDto.setVehicleSoldCount(vehicleDto.getVehicleSoldCount() + finalListOfVehicle.get(i).getVehicleSoldCount());
							vehicleDto.setCompanyName(finalListOfVehicle.get(i).getCompanyName() != null ? finalListOfVehicle.get(i).getCompanyName() :vehicleDto.getCompanyName());
						} else {
							finalMapOfVehicle.put(finalListOfVehicle.get(i).getVehicleCompanyId(), finalListOfVehicle.get(i));
						}
					}
					valueOutObject.put("finalMapOfVehicle", finalMapOfVehicle);
					return valueOutObject.getHtData();
					
				} catch (Exception e) {
					LOGGER.error("Error In Controller : ", e);
					throw e;
				} finally {
				}
			}
	/* Vehicle Creation Report By Dev Yogi End Part 2 */
	
	
	/*Vehicle Creation Report By Dev Yogi Start Part 3 */
	
	@RequestMapping(value = "/VehicleCreationReportDetails", method = RequestMethod.GET)
	public ModelAndView VehicleCreationReport(@RequestParam("companyId") long cId, @RequestParam("vehicleStatusId") 
	long vStatusId, @RequestParam("date") String date ) throws Exception{
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
		List<VehicleDto> vehDetails = vehicleService.getVehicleCreationReportDetails(cId,vStatusId,date);
		if(vehDetails == null) {
		model.put("NotFound",true );	
		}
		model.put("CountDetails",vehDetails);
		return new ModelAndView("VehicleCreation_Report", model);
		} catch (Exception e) {
			throw e;
		}
	}
	/*Vehicle Creation Report By Dev Yogi End Part 3 */
	
	@RequestMapping(value = "/vehicleRepairAndPartConsumptionReport", method = RequestMethod.GET)
	public ModelAndView addTripSheetOptions(final HttpServletRequest request) {
		ModelAndView 					map 		= null;
		try {
			map = new ModelAndView("vehicleRepairAndPartConsumptionReport");
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/getVehicleRepairAndPartConsumptionDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleRepairAndPartConsumptionDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.getVehicleRepairAndPartConsumptionDetails(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleDetailsFromVehicleGroup", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleDetailsFromVehicleGroup(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleService.getVehicleDetailsFromVehicleGroup(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/runDailyInspection", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  runDailyInspection(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleDailyInspectionService.runDailyInspection(object).getHtData();
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		} 
	}
	
	
	@RequestMapping("/VehicleRouteChangeReport")
	public ModelAndView VehicleRouteChangeReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("VehicleRouteChangeReport", model);
	}
	
	
	@RequestMapping(value = "/getVehicleRouteChangeReportList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object> getVehicleRouteChangeReportList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			System.err.println("INside controller");
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= vehicleService.getVehicleRouteChangeReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@GetMapping(path="/vehicleIncidentReport")
	public ModelAndView getVehicleIncidentDetails(){
		CustomUserDetails userDetails = Utility.getObject(null);
		ModelAndView model = new ModelAndView("vehicleIncidentReport");
		model.addObject("companyId",userDetails.getCompany_id());
		return model;
	}
	
	@PostMapping(path="/getVehicleIncidentReport",produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object,Object> getVehicleIncidentDetails(@RequestParam HashMap<Object,Object> allReqParam){
		return vehicleService.getVehicleIncidentReport(new ValueObject(allReqParam)).getHtData();
	}
	@PostMapping(path="/getVehicleWiseIncidentDetails",produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object,Object> getVehicleWiseIncidentDetails(@RequestParam HashMap<Object,Object> allReqParam){
		return vehicleService.getVehicleWiseIncidentDetails(new ValueObject(allReqParam)).getHtData();
	}
	
}

package org.fleetopgroup.rest.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.IssuesConfigurationContant;
import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.persistence.bl.IssuesBL;
import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.IntangleTripDetailsDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.controller.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class IssuesRestController {
	
	
	@Autowired CompanyConfigurationService companyConfigurationService;
	
	@Autowired IJobTypeService JobTypeService;
	
	@Autowired IVehicleService vehicleService;
	
	@Autowired IServiceEntriesService ServiceEntriesService;
	
	@Autowired private IVehicleGroupService	vehicleGroupService;
	
	@Autowired private IIssuesService issuesService;
	
	@Autowired IPartCategoriesService partCategoriesService;
	
	IssuesBL			IBL 				= new IssuesBL();
	PartCategoriesBL 	partCategoriesBL	= new PartCategoriesBL();

	@RequestMapping(value = "/createIssuesServiceEntries", method = RequestMethod.POST, produces="application/json")
	public  HashMap<Object, Object>  createIssuesServiceEntries(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			object.put("serviceEntriesDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("serviceEntriesDetails")));
			
			return issuesService.saveIssuesServiceEntries(object).getHtData();
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@RequestMapping(value = "/addIssuesDetails", method = RequestMethod.GET)
	public ModelAndView addIssuesDetails(@ModelAttribute("command")IssuesDto issuedto , final HttpServletRequest request) throws Exception {
		ModelAndView 				map 		= null;
		HashMap<String, Object>   	configuration			= null;
		HashMap<String, Object>   	gpsConfiguration		= null;
		HashMap<String, Object>   	masterConfiguration		= null;
		CustomUserDetails			userDetails 			= null;
		boolean					 	customerIssue	      	= false;
		try {
			map = new ModelAndView("addIssuesDetails");
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			gpsConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			masterConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			
			map.addObject("userName", userDetails.getFirstName());
			map.addObject("userId", userDetails.getId());
			map.addObject("gpsConfiguration", gpsConfiguration);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("vehiclegroup", vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id()));
			
				map.addObject("VID", issuedto.getISSUES_VID());
				map.addObject("healthStatus", issuedto.getHealthStatus());
				map.addObject("vehicleRegistration", issuedto.getISSUES_VEHICLE_REGISTRATION());
			
			customerIssue	= (boolean) configuration.getOrDefault(IssuesConfigurationContant.SHOW_CUSTOMER_NAME, false);
			if(customerIssue == true) {
				map.addObject("IssueType", IssuesTypeConstant.getIssuesTypeList());  
			}
			else {
				map.addObject("IssueType", IssuesTypeConstant.getIssuesTypeListWithoutCustomerIssue());
			}
			
			map.addObject(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).get(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES));
			map.addObject(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_ISSUES,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_ISSUES, false));
			map.addObject(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, (boolean) configuration.getOrDefault(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, false));
			map.addObject("configuration", configuration);
			map.addObject("serverDate",DateTimeUtility.getDateAfterNoOfDays(0));
			map.addObject("serverDateStr",DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getCurrentTimeStamp()));
			map.addObject("serverTimeStr",DateTimeUtility.getTimeFromTimeStamp(DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.HHH_MM));
			
			if((boolean) masterConfiguration.get("inIssueCategory")) {
				map.addObject("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayIdAncIncIssue(userDetails.getCompany_id())));
			}
			else {
				map.addObject("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			}
			
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
		//	LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value="/saveIssuesDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveIssuesDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		CustomUserDetails			userDetails						= null;
		List<ValueObject>			multiIssues						= null;
		HashMap<String, Object>     configuration					= null;

		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			valueInObject.put("userDetails", userDetails);
			if((boolean) configuration.getOrDefault("multipleIssues", false)) 
			 multiIssues= (List<ValueObject>) JsonConvertor.toValueObjectFromJsonString(valueInObject.getString("multiIssueDetails"));
			if(multiIssues != null && !multiIssues.isEmpty()) {
				valueInObject.put("multiIssueDetails", multiIssues);
				valueOutObject = issuesService.saveMultiIssues(valueInObject);
			}else{
				valueOutObject		= issuesService.saveIssuesDetails(valueInObject);
			}
			 
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	
	@RequestMapping(value = "/editIssuesDetails", method = RequestMethod.GET)
	public ModelAndView editIssuesDetails(@RequestParam("Id") final String issuesId,final HttpServletRequest request) throws Exception {
		ModelAndView 				map 					= null;
		HashMap<String, Object>   	configuration			= null;
		HashMap<String, Object>   	gpsConfiguration		= null;
		HashMap<String, Object>   	masterConfiguration		= null;
		CustomUserDetails			userDetails 			= null;
		IssuesDto 					issuesDto 				= null;
		StringBuffer 				assignTo 				= new StringBuffer();
		try {
			map 			= new ModelAndView("editIssuesDetails");
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			gpsConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			masterConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			if (issuesId != null && issuesId != "") {
				issuesDto = IBL.Edit_Issues_Details(issuesService.get_IssuesDetails(Long.parseLong(AESEncryptDecrypt.decryptBase64(issuesId)), userDetails.getCompany_id()));
				assignTo = 	assignTo.append(issuesDto.getISSUES_ASSIGN_TO_ID());
				map.addObject("assignTo", assignTo);
				
				map.addObject("Issues", issuesDto);
				map.addObject("userName", userDetails.getFirstName());
				map.addObject("userId", userDetails.getId());
				map.addObject("gpsConfiguration", gpsConfiguration);
				map.addObject("configuration", configuration);
				map.addObject("companyId", userDetails.getCompany_id());
				map.addObject("vehiclegroup", vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id()));
				if((boolean) masterConfiguration.get("inIssueCategory")) {
					map.addObject("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayIdAncIncIssue(userDetails.getCompany_id())));
				}
				else {
					map.addObject("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
				}
				
				if(issuesDto.getISSUES_VID() != null) {
					map.addObject("vehicle", vehicleService.Get_Vehicle_Current_Status_TripSheetID(issuesDto.getISSUES_VID()));
					map.addObject(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).get(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES));
					map.addObject(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_ISSUES,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_ISSUES, false));
					map.addObject(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, (boolean) configuration.getOrDefault(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, false));
					
					map.addObject("configuration", configuration);
					map.addObject("serverDate",DateTimeUtility.getDateAfterNoOfDays(0));
					
					
				}
			} 

			return map;
	}catch(Exception e) {
		e.printStackTrace();
		throw e;
	}
}
	
	@RequestMapping(value="/updateIssuesDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateIssuesDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		CustomUserDetails			userDetails						= null;
//		List<ValueObject>			multiIssues						= null;
//		HashMap<String, Object>  	configuration 					= null;
		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			
			valueInObject.put("userDetails", userDetails);
			valueOutObject		= issuesService.updateIssuesDetails(valueInObject);
//			if((boolean) configuration.getOrDefault("multipleIssues", false)) {
//				multiIssues= (List<ValueObject>) JsonConvertor.toValueObjectFromJsonString(valueInObject.getString("multiIssueDetails"));
//				if(multiIssues != null && !multiIssues.isEmpty()) {
//					valueInObject.put("multiIssueDetails", multiIssues);
//					issuesService.saveMultiIssues(valueInObject);
//				}
//			}
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/getlastOpenIssue", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getlastOpenIssue(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= issuesService.getlastOpenIssue(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/getIssueMaxOdometer", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getIssueMaxOdometer(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= issuesService.getIssueMaxOdometerRange(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@GetMapping(value = "/getVehicleWiseIssueDropdown")
	public void getVehicleWiseIssueDropdown(@RequestParam("term") final Integer vid,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<IssuesDto> 				issuesList				= null;
		CustomUserDetails				userDetails 			= null;
		long 							issueId					= 0;
		ValueObject						valueObject				= null;
		try {
			valueObject		= new ValueObject();
			Gson gson 		= new Gson();
			issuesList 		= new ArrayList<>();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			issuesList		= issuesService.getOpenIssueDetailsByVehicle(vid,userDetails,issueId, " ",valueObject.getString("invoiceDate",""));
			response.getWriter().write(gson.toJson(issuesList));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value="/getVehicleWiseIssueDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleWiseIssueDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		List<IssuesDto> 			issuesList						= null;
		List<UserProfileDto> 					subsciberList						= null;
		CustomUserDetails				userDetails 			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			if(valueInObject.getInt("vid",0) > 0) {
				String	conditions = "";
				if(valueInObject.getLong("issueId",0) > 0) {
					conditions = " AND I.ISSUES_ID = "+valueInObject.getLong("issueId",0)+" ";
				}else {
					String issueIds =valueInObject.getString("issueId","");
					if(!issueIds.trim().equals("") && !issueIds.equals("0")) {
						conditions = " AND I.ISSUES_ID IN("+issueIds+") ";
					}
				}
				if(valueInObject.getLong("isEdit",0)==0) {
				
					conditions +=" AND I.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_OPEN+" ";
					
				}
				issuesList		= issuesService.getOpenIssueDetailsByVehicle(valueInObject.getInt("vid"),userDetails,valueInObject.getLong("issueId",0), conditions,valueInObject.getString("invoiceDate",""));
				}
			if(issuesList != null && !issuesList.isEmpty()) {
				subsciberList =issuesService.getAssigneeListByRespectiveIssueId(issuesList);
				valueOutObject.put("issueDetails", issuesList);
				valueOutObject.put("subsciberList", subsciberList);
			}
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value = "/getVehicleHealthData", method = RequestMethod.POST)
	public HashMap<Object, Object> vehicleHealthData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
			ValueObject 					model 						 = null;
			JSONArray						intangleVehicleListJsonArray = null;
			JSONObject						object						 = null;
			JSONObject						healthInfoObj			     = null;
			HashMap<String, Object> 		vehicleConfiguration	 	 = null;
			List<IntangleTripDetailsDto> 	intangleTripDetailsList		 = null;
			IntangleTripDetailsDto			intangleTripDetailsObj		 = null;
		
		try {

			vehicleConfiguration				= companyConfigurationService.getCompanyConfiguration(40, PropertyFileConstants.VEHICLE_GPS_CONFIG);
			intangleVehicleListJsonArray = vehicleService.getIntangleVehicleList(vehicleConfiguration);
			
			
			if(intangleVehicleListJsonArray != null && intangleVehicleListJsonArray.length() > 0) {
				intangleTripDetailsList = new ArrayList<IntangleTripDetailsDto>();	
				
				for(int i=0; i<intangleVehicleListJsonArray.length(); i++) {
					intangleTripDetailsObj = new IntangleTripDetailsDto();
					object				= (JSONObject) intangleVehicleListJsonArray.get(i);
					healthInfoObj 		= (JSONObject) object.get("health_info");
					if(!healthInfoObj.getString("health").equals("GOOD")) {
						try {
							Date date = new Date(Long.parseLong(healthInfoObj.get("last_update")+""));
							Timestamp tripDate = DateTimeUtility.getTimeStampFromDate(date);
							intangleTripDetailsObj.setLast_update_Str(DateTimeUtility.getDateFromTimeStamp(tripDate));
						} catch (NumberFormatException e) {
							intangleTripDetailsObj.setLast_update_Str("--");
						}							
						intangleTripDetailsObj.setHealth(healthInfoObj.getString("health"));
						intangleTripDetailsObj.setPlate((object.getString("tag")));
						Vehicle vehicle= vehicleService.getVehicelReg(object.getString("tag"));
						 if(vehicle != null) {
							 intangleTripDetailsObj.setVid(vehicle.getVid()); 
						 }else {
							 intangleTripDetailsObj.setVid(0);
						 }
						intangleTripDetailsList.add(intangleTripDetailsObj);
					}
				}
				
			}
			model = new ValueObject();
			if(intangleTripDetailsList != null && !intangleTripDetailsList.isEmpty()) {
				model.put("tripDetailsCount", intangleTripDetailsList.size());
				model.put("intangleTripDetailsList", intangleTripDetailsList);
			}
			return model.getHtData();
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@RequestMapping("/NewIssuesReportedReport")
	public ModelAndView IssuesReportedReport() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object>	configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
		model.put("configuration", configuration);


		return new ModelAndView("NewIssuesReportedReport", model);
	}
	
	@RequestMapping(value = "/getNewIssueReportData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getNewIssueReportData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {

			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= issuesService.getNewIssueReportData(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value="/getIssueBreakDownDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getIssueBreakDownDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= issuesService.getIssueBreakDownDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@PostMapping(path="/changeIssueType",produces="application/json")
	public HashMap<Object, Object> updateIssueType(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			valueObject=issuesService.updateIssueType(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
		}
	}
	
	@PostMapping(path="/breakDownAnalysisReport",produces="application/json")
	public HashMap<Object, Object> breakDownAnalysisReport(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			valueObject=issuesService.getBreakDownAnalysisReport(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
			
		}
	}
	@PostMapping(path="/backdateOdometerValidation",produces="application/json")
	public HashMap<Object, Object> backdateOdometerValidation(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			valueObject=issuesService.backdateOdometerValidation(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
			
		}
	}

	@PostMapping(path="/changedTobreakDownReport",produces="application/json")
	public HashMap<Object, Object> changedTobreakDownReport(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			valueObject=issuesService.getIssueTypeChangedReport(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
			
		}
	}
	@PostMapping(path="/getIssueAssignTyre",produces="application/json")
	public HashMap<Object, Object> getIssueAssignTyre(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			valueObject=issuesService.getIssueAssignTyre(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
			
		}
		
		
	}
	
	@PostMapping(path="/getVehicleAndBreakDownReportDetails",produces="application/json")
	public HashMap<Object, Object> getVehicleAndBreakDownReportDetails(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			valueObject=issuesService.getVehicleAndBreakDownReportDetails(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
			
		}
	}
	
	@GetMapping("/showIssuePrint")
	public ModelAndView showIssuePrint(@RequestParam("issueId") final String issueIncry) {
		ModelAndView view = new ModelAndView("showIssuePrint");
		Long issueId =null;
		try {
			issueId=Long.parseLong(AESEncryptDecrypt.decryptBase64(issueIncry));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(issueId != null && issueId >0) {
			Map<String,Object> map = issuesService.getPrintDataForIssue((issueId));
			view.addAllObjects(map);
		}
		return view;
	}
	
	@GetMapping("/showMultiIssuePrint")
	public ModelAndView showMultiIssuePrint(@RequestParam("issueIds") final String issueIds) {
		ModelAndView view = new ModelAndView("showMultiIssuePrint");
		if(issueIds != null && !issueIds.trim().equals("")) {
			Map<String,Object> map = issuesService.getMultiPrintDataForIssue(issueIds);
			view.addAllObjects(map);
		}
		return view;
	}
	
	
}

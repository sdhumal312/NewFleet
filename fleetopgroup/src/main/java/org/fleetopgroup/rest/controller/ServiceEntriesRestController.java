package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.ServiceEntriesConfigurationConstants;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.persistence.bl.ServiceEntriesBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesTasksDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesTasksToPartsDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.VehicleAccidentDetailsDto;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.ServiceEntriesTasksToLabor;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ExceptionProcess;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ServiceEntriesRestController {
	
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private IServiceEntriesService										serviceEntriesService;
	@Autowired private CompanyConfigurationService 									companyConfigurationService;
	@Autowired private IVehicleService 												vehicleService;
	@Autowired private IServiceReminderService										serviceReminderService;
	@Autowired private	IIssuesService 												issuesService;
	@Autowired private IVehicleAccidentDetailsService								accidentDetailsService;
	
	ServiceEntriesBL seEntriesBL = new ServiceEntriesBL();
	
	
	@PostMapping(path = "/saveServEntInvDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveServEntInvDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;

		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceEntriesService.saveServEntInvDetails(object);

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@PostMapping(path = "/getInvoiceDeatils", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getInvoiceDeatils(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= serviceEntriesService.getSEInvoiceDeatils(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@GetMapping(path = "/createServiceEntries")
	public ModelAndView createServiceEntries(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<>();
		CustomUserDetails 			userDetails 			= null;
		HashMap<String, Object>     configuration			= null;
		HashMap<String, Object>     config					= null;
		HashMap<String, Object>     gpsConfiguration		= null;
		boolean						tallyConfig				= false;
		Integer						noOfBackDays			= null;
		String 						minBackDate				= null; 
		Vehicle						vehicle					= null;
		int 						vid						= 0;
		long						issueId					= 0;
		long						serviceReminderId		= 0;
		ServiceReminderDto			serviceReminderDto		= null;
		Issues						issues					= null;
		String 						issue					= null;
		try {
			
			issue		= tripsheetdto.getIssue();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			config	 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
			tallyConfig				= (boolean) config.getOrDefault(ServiceEntriesConfigurationConstants.TALLY_COMPANY_MASTER_IN_SE, false);
			noOfBackDays			= (Integer) config.getOrDefault(ServiceEntriesConfigurationConstants.NO_OF_BACK_DAYS, 0);
			minBackDate 			= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
			if(issue != null) {
				vid 					= Integer.parseInt(issue.split(",")[0]);
				issueId 				= Integer.parseInt(issue.split(",")[1]);
				serviceReminderId 		= Long.parseLong(issue.split(",")[1]);
				vehicle					= vehicleService.getVehicel(vid, userDetails.getCompany_id());
				if(vehicle != null) {
					model.put("vid",vid);
					model.put("vehicleReg",vehicle.getVehicle_registration());
				}
				
				issues 		= issuesService.getIssueDetailsByIssueId(issueId, userDetails.getCompany_id());
				
				if(issues != null) {
					model.put("issueId",issueId);
				}
				
				serviceReminderDto = serviceReminderService.getServiceReminderByVidAndServiceId(vid, serviceReminderId, userDetails.getCompany_id());// here issueId is servicereminderid
				
			}
			
			String	accident = tripsheetdto.getAccident();
			if(accident != null) {
				Long	accidentId = Long.parseLong(AESEncryptDecrypt.decryptBase64(accident)); 
				VehicleAccidentDetailsDto	detailsDto = accidentDetailsService.getVehicleAccidentDetailsById(accidentId, userDetails.getCompany_id(), userDetails.getId());
				if(detailsDto != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					model.put("detailsDto", objectMapper.writeValueAsString(detailsDto));
					model.put("accidentDetailsDto", detailsDto);
				}
			}
			
			if(serviceReminderDto != null) {
				model.put("fromSR", true);
				model.put("serviceReminderId", serviceReminderId);
				model.put("jobTypeId", serviceReminderDto.getServiceTypeId());
				model.put("subJobTypeId", serviceReminderDto.getServiceSubTypeId());
				model.put("jobType", serviceReminderDto.getServiceReminderType());
				model.put("subJobType", serviceReminderDto.getService_subtype());
				
			}
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
			model.put("minBackDate",minBackDate);
			model.put("tallyConfig", tallyConfig);
			model.put("config", config);
			model.put("configuration", configuration);
			model.put("userName", userDetails.getFirstName());
			model.put("userId", userDetails.getId());
			model.put("gpsConfiguration", gpsConfiguration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("serverDate",DateTimeUtility.getDateAfterNoOfDays(0));
			
		}  catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NotFound.in");
		}
		
		return new ModelAndView("createServiceEntries", model);
	}	
	
	@PostMapping(path = "/saveServiceEntriesDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveServiceEntriesDetails(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest  request) throws Exception {
		ValueObject 		object 					= null;
		try {
			
			object 				= new ValueObject(allRequestParams);
			object.put("serviceEntriesTaskDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("serviceEntriesTaskDetails")));
			
			return serviceEntriesService.saveServiceEntriesDetails(object).getHtData();
		} catch (Exception e) {
			logger.error("Exception While Saving service entries ", e);
			if(object == null) {
				object	= new ValueObject();
			}
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			object.put("accessToken", uniqueID);
			object.put("hasError", true);
			return object.getHtData();
		} 
	}
	
	@GetMapping("/showServiceEntryDetails")
	public ModelAndView showServiceEntryDetails(@RequestParam("serviceEntryId") final Integer serviceEntryId, final HttpServletRequest request) {
		Map<String, Object> 					model 									= new HashMap<>();
		HashMap<String, Object>     			configuration							= null;
		HashMap<String, Object>     			config									= null;
		CustomUserDetails 						userDetails								= null;
		List<ServiceEntriesTasksDto>			serviceEntriesTasksDtoList				= null;
		List<ServiceEntriesTasksToPartsDto>		serviceEntriesTasksToPartsDtoList		= null;
		List<ServiceEntriesTasksToLabor>		serviceEntriesTasksToLabor				= null;
		ServiceEntriesDto 						serviceEntries							= null;
		int										noOfBackDaysToReopenSE					= 0;
		boolean									reOpenSE								= true;
		long									differenceBetTwoDate					= 0;
		try {
			
			if (serviceEntryId != null) {
				userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				config	 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
				noOfBackDaysToReopenSE	= (Integer) config.getOrDefault(ServiceEntriesConfigurationConstants.NO_OF_BACK_DAYS_TO_REOPEN_SE, 0);
				
				serviceEntries 						= serviceEntriesService.getServiceEntriesDetails(serviceEntryId, userDetails.getCompany_id());
				serviceEntriesTasksDtoList 			= serviceEntriesService.getServiceEntriesTasks(serviceEntryId, userDetails.getCompany_id());
				serviceEntriesTasksToPartsDtoList 	= serviceEntriesService.getServiceEntriesTasksToParts(serviceEntryId);
				serviceEntriesTasksToLabor			= serviceEntriesService.getServiceEntriesTasksToLabor(serviceEntryId);

				ServiceEntriesDto work = seEntriesBL.Show_ServiceEntries(serviceEntries);
				
				String uniqueID  = UUID.randomUUID().toString();
				String uniqueID2 = UUID.randomUUID().toString();
				String uniqueID3 = UUID.randomUUID().toString();
				request.getSession().setAttribute(uniqueID, uniqueID);
				request.getSession().setAttribute(uniqueID2, uniqueID2);
				request.getSession().setAttribute(uniqueID3, uniqueID3);
				model.put("addLabourToken", Utility.getUniqueToken(request));
				model.put("addPartToken", Utility.getUniqueToken(request));
				model.put("completeSEToken", Utility.getUniqueToken(request));
				model.put("ServiceEntries", work);
				model.put("serviceEntryId", serviceEntryId);
				model.put("ServiceEntriesTask",serviceEntriesTasksDtoList);
				model.put("ServiceEntriesTaskPart", serviceEntriesTasksToPartsDtoList);
				model.put("ServiceEntriesTaskLabor", serviceEntriesTasksToLabor);
				model.put("configuration", configuration);
				model.put("config", config);
				model.put("ValidateInvoiceDetails", ServiceEntriesBL.getInvoiceDateToValidate(serviceEntries, config));
				
				if (work.getServiceEntries_statusId() == ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE) {
						differenceBetTwoDate 				= DateTimeUtility.getExactDayDiffBetweenTwoDates(DateTimeUtility.getTimeStampFromDate(work.getCompleted_dateOn()),DateTimeUtility.getCurrentTimeStamp());
					
					if(differenceBetTwoDate > noOfBackDaysToReopenSE || serviceEntries.isPendingForTally()) {
						reOpenSE	= false;
					}
					
					model.put("reOpenSE", reOpenSE);
					return new ModelAndView("showCompleteServiceEntryDetails", model);
				}

			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("showServiceEntryDetails", model);
	}
	
	@PostMapping(path = "/getServiceEntryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getServiceEntryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.getServiceEntryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/saveServiceEntryPartDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveServiceEntryPartDetails(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object = new ValueObject(allRequestParams);
			object =  serviceEntriesService.saveServiceEntryPartDetails(object);
			
			return object.getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	
	@PostMapping(path = "/saveServiceEntryLabourDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveServiceEntryLabourDetails(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.saveServiceEntryLabourDetails(object).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	
	@PostMapping(path = "/saveServiceEntryNewTaskDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveServiceEntryNewTaskDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.saveServiceEntryNewTaskDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/deletePartDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deletePartDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.deletePartDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/deleteLabourDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deleteLabourDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.deleteLabourDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/deleteTaskDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deleteTaskDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.deleteTaskDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/changeStatusToInProgress", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  changeStatusToInProgress(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.changeStatusToInProgress(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/completeServiceEntry", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  completeServiceEntry(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.completeServiceEntry(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/showCompleteServiceEntryDetails")
	public ModelAndView showCompleteServiceEntryDetails(@RequestParam("serviceEntryId") final Integer serviceEntryId, final HttpServletRequest request) {
		Map<String, Object> 					model 									= new HashMap<>();
		HashMap<String, Object>     			configuration							= null;
		HashMap<String, Object>     			config									= null;
		CustomUserDetails 						userDetails								= null;
		List<ServiceEntriesTasksDto>			serviceEntriesTasksDtoList				= null;
		List<ServiceEntriesTasksToPartsDto>		serviceEntriesTasksToPartsDtoList		= null;
		List<ServiceEntriesTasksToLabor>		serviceEntriesTasksToLabor				= null;
		ServiceEntriesDto 						serviceEntries							= null;
		boolean									reOpenSE								= true;
		try {

			if (serviceEntryId != null) {
				
				userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				config	 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
				
				serviceEntries 						= serviceEntriesService.getServiceEntriesDetails(serviceEntryId, userDetails.getCompany_id());
				serviceEntriesTasksDtoList 			= serviceEntriesService.getServiceEntriesTasks(serviceEntryId, userDetails.getCompany_id());
				serviceEntriesTasksToPartsDtoList 	= serviceEntriesService.getServiceEntriesTasksToParts(serviceEntryId);
				serviceEntriesTasksToLabor			= serviceEntriesService.getServiceEntriesTasksToLabor(serviceEntryId);

				ServiceEntriesDto work = seEntriesBL.Show_ServiceEntries(serviceEntries);
				
				if(serviceEntries.isPendingForTally()) {
					reOpenSE	= false;
				}
				
				if((boolean) config.get("addSECOmpletionRemark")) {
					model.put("SERemarkList", serviceEntriesService.getServiceEntryRemarkDtoList(serviceEntryId));
				}
				
				model.put("ServiceEntries", work);
				model.put("serviceEntryId", serviceEntryId);
				model.put("ServiceEntriesTask",serviceEntriesTasksDtoList);
				model.put("ServiceEntriesTaskPart", serviceEntriesTasksToPartsDtoList);
				model.put("ServiceEntriesTaskLabor", serviceEntriesTasksToLabor);
				model.put("reOpenSE", reOpenSE);
				model.put("configuration", configuration);
				model.put("config", config);
				
				return new ModelAndView("showCompleteServiceEntryDetails", model);
					
			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (Exception e) {
			return new ModelAndView("redirect:/NotFound.in");
		}
	}
	
	@PostMapping(path = "/saveRoundOfDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveRoundOfDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.saveRoundOfDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/uploadServiceEntryDocument", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  uploadServiceEntryDocument(@RequestParam HashMap<Object, Object> allRequestParams,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {

		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= serviceEntriesService.uploadServiceEntryDocument(JsonConvertor.toValueObjectFormSimpleJsonString(valueInObject.getString("serviceEntryData")),uploadfile);
			
			return valueOutObject.getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@GetMapping("/serviceEntriesEditAjax")
	public ModelAndView serviceEntriesEditAjax(@RequestParam("SEID") final Long ServiceEntries_id, final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 				= new HashMap<>();
		HashMap<String, Object>     configuration		= null;
		HashMap<String, Object>     config				= null;
		HashMap<String, Object>     gpsConfiguration	= null;
		CustomUserDetails 			userDetails			= null;
		ServiceEntriesDto 			serviceEntriesBL	= null;
		ServiceEntriesDto 			serviceEntriesDto	= null;
		boolean						tallyConfig			= false;
		String 						minBackDate			= null;
		Integer						noOfBackDays		= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			config	 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			tallyConfig			= (boolean) config.getOrDefault(ServiceEntriesConfigurationConstants.TALLY_COMPANY_MASTER_IN_SE, false);
			serviceEntriesDto 	= serviceEntriesService.getServiceEntriesDetails(ServiceEntries_id, userDetails.getCompany_id());
			serviceEntriesBL 	= seEntriesBL.getServiceEntriesDetails(serviceEntriesDto);
			noOfBackDays		= (Integer) config.getOrDefault(ServiceEntriesConfigurationConstants.NO_OF_BACK_DAYS, 0);
			minBackDate 		= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
			
			model.put("ServiceEntries_id",ServiceEntries_id);
			model.put("minBackDate",minBackDate);
			model.put("ServiceEntries", serviceEntriesBL);
			if(serviceEntriesBL.getISSUES_ID() > 0) {
				model.put("issueId", serviceEntriesBL.getISSUES_ID());
			}
			model.put("tallyConfig", tallyConfig);
			model.put("config", config);
			model.put("configuration", configuration);
			model.put("companyId", userDetails.getCompany_id());
			model.put("gpsConfiguration", gpsConfiguration);
			model.put("serverDate",DateTimeUtility.getDateAfterNoOfDays(0));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("serviceEntriesEditAjax", model);
	}
	
	@PostMapping(path = "/updateServiceEntryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  updateServiceEntryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.updateServiceEntryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/deleteServiceEntryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deleteServiceEntryDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.deleteServiceEntryDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping("/viewServiceEntries")
	public ModelAndView viewServiceEntries(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		
		return new ModelAndView("viewServiceEntries");
	}
	
	@PostMapping(path = "/getServiceEntriesList", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getServiceEntriesList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.getServiceEntriesList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/searchServiceEntriesByNumber", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  searchServiceEntriesByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.searchServiceEntriesByNumber(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping("/searchServiceEntries")
	public ModelAndView searchServiceEntries(final TripSheetDto tripsheetdto, final HttpServletRequest request) {
		
		return new ModelAndView("searchServiceEntries");
	}
	
	@PostMapping(path = "/searchServiceEntriesList", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  searchServiceEntriesList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.searchServiceEntries(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/searchServiceEntriesDateWise", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  searchServiceEntriesDateWise(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.searchServiceEntriesDateWise(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/searchServiceEntriesByDifferentFilters", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  searchServiceEntriesByDifferentFilters(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return serviceEntriesService.searchServiceEntriesByDifferentFilters(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}

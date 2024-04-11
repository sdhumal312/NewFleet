package org.fleetopgroup.rest.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DealerServiceEntriesDto;
import org.fleetopgroup.persistence.dto.DealerServiceExtraIssueDto;
import org.fleetopgroup.persistence.dto.DealerServiceRemarkDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.MasterPartsDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleAccidentDetailsDto;
import org.fleetopgroup.persistence.model.DealerServiceExtraIssue;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RestController
public class DealerServiceEntriesWS {
	
	@Autowired  IDealerServiceEntriesService 		dealerService;
	@Autowired  IMasterPartsService 				masterPartsService;
	@Autowired 	IUserProfileService 				userProfileService;
	@Autowired  IIssuesService 						issuesService;
	@Autowired  ICompanyConfigurationService 		companyConfigurationService;
	@Autowired  IServiceReminderService 			serviceReminderService;
	@Autowired  IVehicleAccidentDetailsService		accidentDetailsService;
	Date 		currentDateUpdate 	= new Date();                                             
	Timestamp 	currentTimestamp 	= new java.sql.Timestamp(currentDateUpdate.getTime());    
	
	@RequestMapping("/DSEREPORT")
	public ModelAndView dseReport() {
		Map<String, Object> model = new HashMap<>();

		return new ModelAndView("DSEREPORT", model);
	}
	@RequestMapping("/dealerServiceReport")
	public ModelAndView dealerServiceReport() {
		Map<String, Object> model = new HashMap<>();
		
		return new ModelAndView("dealerServiceReport", model);
	}
	
	
	@GetMapping(value = "/dealerServiceEntries")
	public ModelAndView dealerServiceEntries(final HttpServletRequest request) throws Exception {
		ModelAndView 				map 			= null;
		CustomUserDetails			userDetails		= null;
		HashMap<String, Object>     configuration  = null;
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			map 		= new ModelAndView("dealerServiceEntries");
			map.addObject("currentTimestamp", currentTimestamp);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@RequestMapping(value="/getDealerServiceEntriesList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getDealerServiceEntriesList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= dealerService.getDealerServiceEntriesList(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	
	@GetMapping(value = "/createDealerServiceEntries")
	public ModelAndView createDealerServiceEntries(@ModelAttribute("command") DealerServiceEntriesDto	dto, final HttpServletRequest request) throws Exception {
		ModelAndView 				map 			= null;
		CustomUserDetails			userDetails		= null;
		IssuesDto					issue			= null;
		HashMap<String, Object> 	configuration 	= null;
		ServiceReminderDto          serviceReminderDto = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			map 		= new ModelAndView("createDealerServiceEntries");
			map.addObject("currentTimestamp", currentTimestamp);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			
			if(dto.getIssue() != null && !dto.getIssue().isEmpty()) {
				issue	= issuesService.getIssueBasicDetails(Long.parseLong(dto.getIssue()), userDetails.getCompany_id());
				map.addObject("issueDetails", issue);
				map.addObject("issueId", issue.getISSUES_ID());
				map.addObject("issueVid", issue.getISSUES_VID());
				map.addObject("issueNumber", issue.getIssuesNumberStr());
				map.addObject("issueSummary", issue.getISSUES_SUMMARY());
				map.addObject("issueVehicleNumber", issue.getISSUES_VEHICLE_REGISTRATION());
			}
			if(dto.getServiceReminder() != null && dto.getServiceReminder() > 0) {
				serviceReminderDto=serviceReminderService.getServiceReminder(dto.getServiceReminder(),  userDetails.getCompany_id());
				map.addObject("srVid", serviceReminderDto.getVid());
				map.addObject("srVehicleNumber", serviceReminderDto.getVehicle_registration());
				map.addObject("srNumber",dto.getServiceReminder());
				map.addObject("programId",serviceReminderDto.getServiceProgramId() );
				map.addObject("programName",serviceReminderDto.getServiceProgram() );
			}else {
				map.addObject("srNumber",0);
			}
			
			String	accident = dto.getAccident();
			if(accident != null) {
				Long	accidentId = Long.parseLong(AESEncryptDecrypt.decryptBase64(accident)); 
				VehicleAccidentDetailsDto	detailsDto = accidentDetailsService.getVehicleAccidentDetailsById(accidentId, userDetails.getCompany_id(), userDetails.getId());
				if(detailsDto != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					map.addObject("detailsDto", objectMapper.writeValueAsString(detailsDto));
				}
			}

			
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/saveDealerServiceEntries", produces="application/json")
	public HashMap<Object, Object>  saveDealerServiceEntries(@RequestParam HashMap<Object, Object> allRequestParams,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject 	= JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("dealerServiceEntryData"));
					
			valueObject.put("labourDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("labourDetails")));
			valueObject.put("partDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("partDetails")));
			valueObject.put("issueDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("issueDetails")));
			
			valueObject		= dealerService.saveDealerServiceEntries(valueObject,uploadfile);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	
	@GetMapping(value = "/showDealerServiceEntries")
	public ModelAndView showDealerServiceEntries(@RequestParam("dealerServiceEntriesId") final Integer dealerServiceEntriesId, final HttpServletRequest request) throws Exception {
		ModelAndView 					map 			= null;
		CustomUserDetails				userDetails		= null;
		HashMap<String, Object> 		configuration 	= null;
		Collection<GrantedAuthority>	permission		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			map 		= new ModelAndView("showDealerServiceEntries");
			permission	= userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("EDIT_DSE_PART"))) {
				map.addObject("editDealerServicePart", true);
			}else {
				map.addObject("editDealerServicePart", false);
			}
			if(permission.contains(new SimpleGrantedAuthority("EDIT_DSE_LABOUR"))) {
				map.addObject("editDealerServiceLabour", true);
			}else {
				map.addObject("editDealerServiceLabour", false);
			}
			map.addObject("currentTimestamp", currentTimestamp);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("dealerServiceEntriesId", dealerServiceEntriesId);
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/getDealerServiceEntriesWithPartAndLabourDetails", produces="application/json")
	public HashMap<Object, Object>  getDealerServiceEntriesWithPartAndLabourDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= dealerService.getDealerServiceEntriesWithPartAndLabourDetails(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/searchDealerServiceEntriesByNumber", produces="application/json")
	public HashMap<Object, Object>  searchDealerServiceEntriesByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= dealerService.searchDealerServiceEntriesByNumber(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@PostMapping(value="/searchDealerServiceEntriesByFilter", produces="application/json")
	public HashMap<Object, Object>  searchDealerServiceEntriesByFilter(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= dealerService.searchDealerServiceEntriesByFilter(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/completeDealerServiceEntries", produces="application/json")
	public HashMap<Object, Object>  completeDealerServiceEntries(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			
			return  dealerService.completeDealerServiceEntries(valueOutObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(value="/deleteDealerServiceEntriesPart", produces="application/json")
	public HashMap<Object, Object>  deleteDealerServiceEntriesPart(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			 dealerService.deleteDealerServiceEntriesPart(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/deleteDealerServiceEntriesLabour", produces="application/json")
	public HashMap<Object, Object>  deleteDealerServiceEntriesLabour(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			dealerService.deleteDealerServiceEntriesLabour(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/deleteDealerServiceEntries", produces="application/json")
	public HashMap<Object, Object>  deleteDealerServiceEntries(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			dealerService.deleteDealerServiceEntries(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/addDealerServiceEntriesPartDetails", produces="application/json")
	public HashMap<Object, Object>  addDealerServiceEntriesPartDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			dealerService.addDealerServiceEntriesPartDetails(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@PostMapping(value="/addDealerServiceEntriesLabourDetails", produces="application/json")
	public HashMap<Object, Object>  addDealerServiceEntriesLabourDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			dealerService.addDealerServiceEntriesLabourDetails(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@PostMapping(value="/searchDealerServiceEntriesDateWise", produces="application/json")
	public HashMap<Object, Object>  searchDealerServiceEntriesDateWise(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject		= dealerService.searchDealerServiceEntriesDateWise(valueOutObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@GetMapping(value = "/editDealerServiceEntries")
	public ModelAndView editDealerServiceEntries(@RequestParam("dealerServiceEntriesId") final Integer dealerServiceEntriesId,final HttpServletRequest request) throws Exception {
		ModelAndView 				map 			= null;
		CustomUserDetails			userDetails		= null;
		ValueObject					object			= null;
		String						query			= "";
		HashMap<String, Object>     configuration   = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			map 		= new ModelAndView("editDealerServiceEntries");
			object		= new ValueObject();
			map.addObject("currentTimestamp", currentTimestamp);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("configuration",configuration);
			map.addObject("userId", userDetails.getId());
			map.addObject("dealerServiceEntriesId", dealerServiceEntriesId);
			query = "AND DSE.dealerServiceEntriesId = "+dealerServiceEntriesId+"";
			object.put("companyId", userDetails.getCompany_id());
			object.put("query", query);
			map.addObject("dealerServiceEntriesPartList", dealerService.getDealerServiceEntriesPartDetailList(object));
			map.addObject("dealerServiceEntriesIdLabourList", dealerService.getDealerServiceEntriesLabourDetailList(object));
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/updateDealerServiceEntries", produces="application/json")
	public HashMap<Object, Object>  updateDealerServiceEntries(@RequestParam HashMap<Object, Object> allRequestParams,@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		
		ValueObject 				valueObject 					= null;
		try {
			valueObject		= new ValueObject(allRequestParams);
			valueObject 	= JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("dealerServiceEntryData"));
					
			valueObject.put("labourDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("labourDetails")));
			valueObject.put("partDetails", JsonConvertor.toValueObjectFromJsonString(valueObject.getString("partDetails")));
			
			valueObject		= dealerService.updateDealerServiceEntries(valueObject, uploadfile);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueObject 					= null;
		}
	}
	
	@PostMapping(value = "/searchAllMasterParts")
	public void searchAllMasterParts(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<MasterPartsDto> Part = new ArrayList<MasterPartsDto>();
		List<MasterPartsDto> Parttype = masterPartsService.searchAllMasterParts(term, userDetails.getCompany_id());
		
		if (Parttype != null && !Parttype.isEmpty()) {
			for (MasterPartsDto add : Parttype) {

				MasterPartsDto wadd = new MasterPartsDto();

				wadd.setPartid(add.getPartid());
				wadd.setPartname(add.getPartname());
				wadd.setPartnumber(add.getPartnumber());
				wadd.setCategory(add.getCategory());
				wadd.setMake(add.getMake());
				wadd.setWarrantyApplicable(add.isWarrantyApplicable());
				wadd.setWarrantyInMonths(add.getWarrantyInMonths());
				Part.add(wadd);
				
			}
		}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Part));
	}
	
	@PostMapping(path = "/uploadDealerServiceEntryDocument", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  uploadDealerServiceEntryDocument(@RequestParam HashMap<Object, Object> allRequestParams,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {

		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= dealerService.uploadDealerServiceEntryDocument(JsonConvertor.toValueObjectFormSimpleJsonString(valueInObject.getString("dealerServiceEntryData")),uploadfile);
			
			return valueOutObject.getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/printDealerServiceEntries")
	public ModelAndView printDealerServiceEntries(@RequestParam("dealerServiceEntriesId") final Integer dealerServiceEntriesId,final HttpServletRequest request) throws Exception {
		ModelAndView 						map 							= null;
		CustomUserDetails					userDetails						= null;
		String								query							= "";
		ValueObject							valueObject						= null;
		ValueObject							srValueObject					= null;
		ValueObject			 				dealerServiceRemarkObject		= null;
		ValueObject			 				dealerServiceExtraObject		= null;
		List<DealerServiceRemarkDto> 		dealerServiceRemarkList			= null;
		List<DealerServiceExtraIssueDto> 	dealerServiceExtraIssueList		= null;
		List<ServiceReminderDto> 			scheduleList					= null;
		try {
			dealerServiceExtraIssueList = new ArrayList<>();
			dealerServiceRemarkList 	= new ArrayList<>();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject = new  ValueObject();
			srValueObject = new  ValueObject();
			if(dealerServiceEntriesId > 0) {
				query = "AND DSE.dealerServiceEntriesId = "+dealerServiceEntriesId+"";
				valueObject.put("query", query);
				valueObject.put("companyId",  userDetails.getCompany_id());
			}
			map 		= new ModelAndView("printDealerServiceEntries");
			map.addObject("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
			map.addObject("currentTimestamp", currentTimestamp);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("dealerServiceEntriesId", dealerServiceEntriesId);
			List<DealerServiceEntriesDto>		dealerServiceEntries = dealerService.getDealerServiceEntriesDetailList(valueObject);
			if(dealerServiceEntries != null && !dealerServiceEntries.isEmpty()) {
				map.addObject("dealerServiceEntries", dealerServiceEntries.get(0));
				if(dealerServiceEntries.get(0).getVendorAddress().length() > 70) {
					map.addObject("vendorAddress1",dealerServiceEntries.get(0).getVendorAddress().substring(0, 70));
					map.addObject("vendorAddress2",dealerServiceEntries.get(0).getVendorAddress().substring(71));
				}else {
					map.addObject("vendorAddress1",dealerServiceEntries.get(0).getVendorAddress());
				}
			}
			valueObject.put("dealerServiceEntriesId", dealerServiceEntriesId);
			valueObject.put("companyId",  userDetails.getCompany_id());
			
			dealerServiceRemarkObject 	= dealerService.getDseRemarks(valueObject);
			dealerServiceExtraObject 	= dealerService.getDseExtraIssue(valueObject);
			
			dealerServiceRemarkList	 	= (List<DealerServiceRemarkDto>) dealerServiceRemarkObject.get("remarkList");
			dealerServiceExtraIssueList = (List<DealerServiceExtraIssueDto>) dealerServiceExtraObject.get("extraIssueList");

			if(!dealerServiceEntries.get(0).getServiceReminderIds().equals("")) {
				srValueObject.put("serviceReminderIds", dealerServiceEntries.get(0).getServiceReminderIds());
				srValueObject.put("companyId",  userDetails.getCompany_id());
				srValueObject = serviceReminderService.getServiceReminderByServiceId(srValueObject);
				scheduleList  = (List<ServiceReminderDto>) srValueObject.get("serviceSchedules");
			}
			
			map.addObject("scheduleList",scheduleList);
			map.addObject("dealerServiceRemarkList",dealerServiceRemarkList);
			map.addObject("dealerServiceExtraIssueList",dealerServiceExtraIssueList);
			map.addObject("dealerServiceEntriesPart", dealerService.getDealerServiceEntriesPartDetailList(valueObject));
			map.addObject("dealerServiceEntriesLabour", dealerService.getDealerServiceEntriesLabourDetailList(valueObject));
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path = "/getLastOccurredDsePartDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getLastOccurredDsePartDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= dealerService.getLastOccurredDsePartDetails(valueInObject);
			
			return valueOutObject.getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@PostMapping(path = "/saveInvoiceDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveInvoiceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= dealerService.saveInvoiceDetails(valueInObject);
			
			return valueOutObject.getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/saveNewDealerServicePartInMasterPart", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveNewDealerServicePartInMasterPart(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;
		
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= dealerService.saveNewDealerServicePartInMasterPart(valueInObject);
			
			return valueOutObject.getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}

	
	@PostMapping(value="/addDealerServiceExtraIssue", produces="application/json")
	public HashMap<Object, Object>  addDealerServiceExtraIssue(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			
			return  dealerService.addDealerServiceExtraIssue(valueOutObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	
	@GetMapping(value = "/desExtraIssueDropdown")
	public void desExtraIssueDropdown(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		DealerServiceExtraIssue 		desExtraIssue 				= null;
		List<DealerServiceExtraIssue> 	desExtraIssueDropDownList 	= null;
		List<DealerServiceExtraIssue> 	dealerServiceExtraIssueList = null;
		CustomUserDetails				userDetails					= null;
		Map<String, String> 			dealerServiceExtraIssueHM	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			desExtraIssueDropDownList 	= new ArrayList<>();
			dealerServiceExtraIssueHM	= new HashMap<>();
			dealerServiceExtraIssueList = dealerService.getAllDealerServiceExtraIssue(userDetails.getCompany_id());
			if (dealerServiceExtraIssueList != null && !dealerServiceExtraIssueList.isEmpty()) {
				for (DealerServiceExtraIssue add : dealerServiceExtraIssueList) {
					if(!dealerServiceExtraIssueHM.containsKey(add.getDescription())) {
						desExtraIssue = new DealerServiceExtraIssue();
						desExtraIssue.setDealerServiceExtraIssueId(add.getDealerServiceExtraIssueId());
						desExtraIssue.setDescription(add.getDescription());
						desExtraIssueDropDownList.add(desExtraIssue);
						dealerServiceExtraIssueHM.put(add.getDescription(), add.getDescription());
					}
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(desExtraIssueDropDownList));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(value="/saveNewVendorFromDse", produces="application/json")
	public HashMap<Object, Object>  saveNewVendorFromDse(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			
			return  dealerService.saveNewVendorFromDse(valueOutObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value="/changeDseStatus", produces="application/json")
	public HashMap<Object, Object>  changeDseStatus(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			
			return  dealerService.changeDseStatus(valueOutObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value="/reopenDealerService", produces="application/json")
	public HashMap<Object, Object>  reopenDealerService(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			
			return  dealerService.reopenDealerService(valueOutObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value="/updateDealerServicePart", produces="application/json")
	public HashMap<Object, Object>  updateDealerServicePart(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			
			return  dealerService.updateDealerServicePart(valueOutObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value="/updateDealerServiceLabour", produces="application/json")
	public HashMap<Object, Object>  updateDealerServiceLabour(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			
			return  dealerService.updateDealerServiceLabour(valueOutObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value="/getVendorWisePreviousDsePartRate", produces="application/json")
	public HashMap<Object, Object>  getVendorWisePreviousDsePartRate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			
			return  dealerService.getVendorWisePreviousDsePartRate(valueOutObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value="/getDseRemarks", produces="application/json")
	public HashMap<Object, Object>  getDseRemarks(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			
			return  dealerService.getDseRemarks(valueOutObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value="/getDseExtraIssue", produces="application/json")
	public HashMap<Object, Object>  getDseExtraIssue(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 				valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			
			return  dealerService.getDseExtraIssue(valueOutObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(path="/backdateDSEOdometerValidation",produces="application/json")
	public HashMap<Object, Object> backdateOdometerValidation(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			valueObject=dealerService.backdateOdometerValidation(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
			
		}
	}
	@PostMapping(path="/getDealerServiceReport",produces="application/json")
	public HashMap<Object, Object> getDealerServiceReport(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			valueObject=dealerService.getDSEReport(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
		}
	}
	
	@PostMapping(path="/getpartLabourDetails",produces="application/json")
	public HashMap<Object, Object> getpartLabourDetails(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			valueObject=dealerService.getpartLabourList(valueObject);
			
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
		}
	}
	
	@GetMapping(path="/getVehicleDealerServiceDetails")
	public ModelAndView vehicleDealerServiceDetails(@RequestParam("id") final Integer id) {
		HashMap<String,Object> map = new HashMap<>();
		try {
			if(id > 0) {
				 map=dealerService.getVehicleWiseDealerServiceDetails(id);
			}else {
				return new ModelAndView("redirect:/NotFound.in");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NotFound.in");
		}
		return new ModelAndView("vehicleDealerServiceDetails",map);
	}
}

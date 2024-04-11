package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.RenealReminderStatus;
import org.fleetopgroup.constant.RenewalReminderConfiguration;
import org.fleetopgroup.persistence.document.RenewalReminderDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/RenewalReminderWS")
public class RenewalReminderWS {

	@Autowired private IRenewalReminderService 			RenewalReminderService;
	@Autowired private IRenewalSubTypeService  			RenewalSubTypeService;
	@Autowired private IRenewalTypeService     			RenewalTypeService;
	@Autowired private ICompanyConfigurationService 	companyConfigurationService;
	@Autowired private IRenewalReminderDocumentService 	iRenewalReminderDocumentService;


	@RequestMapping(value="/saveEmailId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveEmailId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject.put("Email", RenewalReminderService.saveEmailRenewalReminder(valueOutObject));

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}

	@RequestMapping(value="/get_All_Email", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  get_All_Email(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			valueOutObject		= new ValueObject();
			valueOutObject.put("Email",RenewalReminderService.getAllEmail_ById(userDetails.getCompany_id()));

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}


	@RequestMapping(value="/update_EmailId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  update_EmailId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueOutObject 					= null;
		Long 						configurationId	;
		String 						emailId							= "";
		Integer 					companyId						= 0;
		long 						userId							= 0;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			valueOutObject 				= new ValueObject(allRequestParams);
			configurationId 			= valueOutObject.getLong("configurationId")	;	
			emailId						= valueOutObject.getString("emailId")	;
			companyId					= userDetails.getCompany_id();
			userId						= userDetails.getId();

			RenewalReminderService.update_Email_ById(emailId,configurationId,userId,companyId );
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	@RequestMapping(value = "/saveRenewalReminderDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveRenewalReminderDetails(@RequestParam("RenewalData") String allRequestParams, 
			@RequestParam("file") MultipartFile[] uploadfile) throws Exception {
		ValueObject					valueObject 					= null;
		CustomUserDetails 			userDetails 					= null;
		String[] 					From_TO_Array 					= null;
		HashMap<String, Object> 	configuration	          		= null;
		boolean						createApproval					= false;
		try {
			valueObject 	= new ValueObject();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG); 
			valueObject = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			valueObject.put("companyId", userDetails.getCompany_id());
			valueObject.put("userId", userDetails.getId());
			
			String dateRange = valueObject.getString("dateRange");
			From_TO_Array = dateRange.split("  to  ");
			valueObject.put("validityFrom", From_TO_Array[0]);
			valueObject.put("validityTo", From_TO_Array[1]);
			
			createApproval	= (boolean) configuration.getOrDefault(RenewalReminderConfiguration.CREATE_APPROVAL, false);
			valueObject.put("createApproval", createApproval);
			
			return RenewalReminderService.saveRenewalReminderFromMobile(valueObject,uploadfile).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateRenewalReminderDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateRenewalReminderDetails(@RequestParam("RenewalData") String allRequestParams, 
			@RequestParam("file") MultipartFile[] uploadfile) throws Exception {
		ValueObject					valueObject 					= null;
		CustomUserDetails 			userDetails 					= null;
		String[] 					From_TO_Array 					= null;
		boolean						createApproval					= false;
		HashMap<String, Object> 	configuration	          		= null;
		try {
			valueObject 	= new ValueObject();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			createApproval	= (boolean) configuration.getOrDefault(RenewalReminderConfiguration.CREATE_APPROVAL, false);
			valueObject = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			valueObject.put("companyId", userDetails.getCompany_id());
			valueObject.put("userId", userDetails.getId());
			
			valueObject.put("createApproval", createApproval);

			String dateRange = valueObject.getString("dateRange");
			From_TO_Array = dateRange.split("  to  ");
			valueObject.put("validityFrom", From_TO_Array[0]);
			valueObject.put("validityTo", From_TO_Array[1]);
			
			return RenewalReminderService.updateRenewalReminderDetails(valueObject,uploadfile).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/reviseRenewalReminderDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  reviseRenewalReminderDetails(@RequestParam("RenewalData") String allRequestParams, 
			@RequestParam("file") MultipartFile[] uploadfile) throws Exception {
		ValueObject					valueObject 					= null;
		CustomUserDetails 			userDetails 					= null;
		String[] 					From_TO_Array 					= null;
		boolean	  					createApproval					= false;
		HashMap<String, Object> 	configuration	          		= null;
		try {
			valueObject 	= new ValueObject();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			valueObject = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			valueObject.put("companyId", userDetails.getCompany_id());
			valueObject.put("userId", userDetails.getId());
			
			String dateRange = valueObject.getString("dateRange");
			From_TO_Array = dateRange.split("  to  ");
			valueObject.put("validityFrom", From_TO_Array[0]);
			valueObject.put("validityTo", From_TO_Array[1]);
			createApproval	= (boolean) configuration.getOrDefault(RenewalReminderConfiguration.CREATE_APPROVAL, false);
			valueObject.put("createApproval", createApproval);
			
			return RenewalReminderService.reviseRenewalReminderDetails(valueObject,uploadfile).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateRenewalDocumentsDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateRenewalDocumentsDetails(@RequestParam("RenewalData") String allRequestParams, 
			@RequestParam("file") MultipartFile[] uploadfile) throws Exception {
		ValueObject					valueObject 					= null;
		try {
			valueObject = new ValueObject();
			valueObject = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams);
			
			return RenewalReminderService.updateRenewalDocumentsDetails(valueObject, uploadfile).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalMandatoryList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalMandatoryList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		CustomUserDetails 			userDetails 			= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object 				= new ValueObject(allRequestParams);
			object.put("companyId", userDetails.getCompany_id());
			
			return RenewalReminderService.getRenewalMandatoryList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleAndRenewalTypeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleAndRenewalTypeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.getVehicleAndRenewalTypeDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalReminderByVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderByVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.getRenewalReminderListByVehicle(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalDetailsByRenewalId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalDetailsByRenewalId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.getRenewalDetailsByRenewalId(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteRenewalReminderById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteRenewalReminderById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.deleteRenewalReminderById(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalReminderDetailsOpenList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderDetailsOpenList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.getRenewalReminderDetailsOpenList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalReminderDetailsOverdueList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderDetailsOverdueList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.getRenewalReminderDetailsOverdueList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalReminderDetailsDueSoonList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderDetailsDueSoonList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.getRenewalReminderDetailsDueSoonList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/serachRenewalReminderByNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  serachRenewalReminderByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.serachRenewalReminderByNumber(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchRRByDifferentFilter", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchRRByDifferentFilter(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.searchRRByDifferentFilter(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchRRDateWise", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchRRDateWise(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.searchRRDateWise(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/searchRenRemndReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchRenRemndReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.searchRenRemndReport(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateRenewalPeriodDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateRenewalPeriodDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.updateRenewalPeriodDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveRenewalSubTypeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveRenewalSubTypeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalSubTypeService.saveRenewalSubTypeDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/uploadRenewalCSVFile", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  uploadRenewalCSVFile(@RequestParam("RenewalData") String allRequestParams, 
			@RequestParam("input-file-preview") MultipartFile uploadfile, HttpServletRequest request) throws Exception {
		try {
			return RenewalReminderService.uploadRenewalCSVFile(JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams), uploadfile, request).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleWiseRenewalReminder", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleWiseRenewalReminder(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.getVehicleWiseRenewalReminder(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalReminderHistoryByVehicle", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalReminderHistoryByVehicle(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.getRenewalReminderHistoryByVehicle(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalSubTypeList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalSubTypeList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalSubTypeService.getRenewalSubTypeList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalSubTypeById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalSubTypeById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalSubTypeService.getRenewalSubTypeById(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateRenewalSubTypeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateRenewalSubTypeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalSubTypeService.updateRenewalSubTypeDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteRenewalSubTypeById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteRenewalSubTypeById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalSubTypeService.deleteRenewalSubTypeById(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalTypeList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalTypeList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalTypeService.getRenewalTypeList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveRenewalTypeList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveRenewalTypeList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalTypeService.saveRenewalTypeList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRenewalTypeById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRenewalTypeById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalTypeService.getRenewalTypeById(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateRenewalType", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateRenewalType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalTypeService.updateRenewalType(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteRenewalTypeById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteRenewalTypeById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalTypeService.deleteRenewalTypeById(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}

	@RequestMapping(value = "/createRRApproval", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  createRRApproval(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			CustomUserDetails 	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 	RenewalReminderService.updateApproedBy(object.getLong("renewal_id"),RenealReminderStatus.APPROVED,userDetails.getId(),object.getString("approvalRemark") , new Date(), userDetails.getCompany_id());
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getRRListByDate", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRRListByDate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			CustomUserDetails 	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object 				= new ValueObject(allRequestParams);
			object.put("companyId", userDetails.getCompany_id());
			object.put("userId", userDetails.getId());
		 	RenewalReminderService.getRRListByDate(object);
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/removeRenewalDocument", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  removeRenewalDocument(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return RenewalReminderService.removeRenewalDocument(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path="/ignoreRenewalReminder",  produces="application/json")
	public HashMap<Object, Object> setIgnoreRenewalReminder(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		try {
			ValueObject object = new ValueObject(allRequestParam);
			return RenewalReminderService.ignoreRenewalReminder(object).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value="/fetch-documents", produces="application/json", method=RequestMethod.GET)
	public List<Long> fetchDocuments(@RequestParam HashMap<Object, Object> allRequestParams) {
		
		ValueObject              object                      = null;
		List<Long>               documentsData               = new ArrayList<>();
	    
	    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    try {
	    	object 				= new ValueObject(allRequestParams);
	    			
	        List<org.fleetopgroup.persistence.document.RenewalReminderDocument> documents = iRenewalReminderDocumentService.getRenewalDocumentsByRenewalId(object.getLong("renewalId"),userDetails.getCompany_id() );
	       
	        for (org.fleetopgroup.persistence.document.RenewalReminderDocument document : documents) {
	            documentsData.add(document.get_id());
	            
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

	    return documentsData;
	}

}	

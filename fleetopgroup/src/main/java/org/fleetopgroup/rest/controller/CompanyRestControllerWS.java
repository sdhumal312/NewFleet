package org.fleetopgroup.rest.controller;

import java.sql.Timestamp;//new
import java.util.Date;//new
import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.RenewalReminderEmailConfiguration;
import org.fleetopgroup.persistence.bl.CompanyBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping(value = "/CompanyRestControllerWS")
public class CompanyRestControllerWS {
	
	@Autowired private ICompanyService CompanyService;
	
	@Autowired private ICompanyConfigurationService		companyConfigurationService;
	
	CompanyBL	 		companyBL 			= new CompanyBL();
	
	// 1  Fetching emails logic start
	@RequestMapping(value="/get_All_Company_Email", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  get_All_Company_Email(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject		valueOutObject 		= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			valueOutObject		= new ValueObject();
			valueOutObject.put("Email",CompanyService.getAllCompanyEmail_ById(userDetails.getCompany_id()));

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	// 1  Fetching emails logic end
	
	//2 Save work for email start
	@RequestMapping(value="/saveCompanyEmailId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveCompanyEmailId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueOutObject 					= null;
		try {
			valueOutObject		= new ValueObject(allRequestParams);
			valueOutObject.put("Email", CompanyService.saveCompanyEmailRenewalReminder(valueOutObject));

			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}	
	//2 Save work for email end
	
	//3 Update work for email start 
	@RequestMapping(value="/updateCompany_EmailId", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateCompany_EmailId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueOutObject 					= null;
		Long 						configurationId	;
		String 						emailId							= "";
		Integer 					companyId						= 0;
		long 						userId							= 0;
		Short 						emailType						= 0;
		

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			valueOutObject 				= new ValueObject(allRequestParams);
			configurationId 			= valueOutObject.getLong("configurationId")	;	
			emailId						= valueOutObject.getString("emailId")	;
			companyId					= userDetails.getCompany_id();
			userId						= userDetails.getId();
			emailType					= RenewalReminderEmailConfiguration.ADMIN_DAILY_WORK_STATUS_EMAIL;
			
			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			

			CompanyService.updateCompany_Email_ById(emailId,configurationId,userId,companyId,emailType,toDate);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	//3 Update work for email start 

	@GetMapping(path="/getCompanyConfiguration",produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String,Object> getCompanyConfiguration(){
		CustomUserDetails userDetails = Utility.getObject(null);
		HashMap<String, Object>  configuration = null;
		  try {
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}
	
	@GetMapping(path="/getIvLoginUrlByCompanyId",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<Object,Object> getIvLoginUrlByCompanyId() throws Exception{
		  try {
			  return  CompanyService.getIvLoginUrlByCompanyId();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(path="/companyNameAndLogoController",produces="application/json", method=RequestMethod.GET)
	public Map<String, Object> companyNameAndLogoController() {
	    Map<String, Object> model = null;
	    CustomUserDetails	userDetails	= null; 
	    try {
	    	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	model = new HashMap<>();
	    	
	    	if (userDetails.getCompany_id() != null) {
	    		model.put("Company", companyBL.ShowCompanyID_Encode(CompanyService.getCompanyByID(userDetails.getCompany_id())));
	        } else {
	            model.put("error", "User or company ID not found.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.put("error", "An error occurred while fetching company information.");
	    }
	    return model;
	}
}

package org.fleetopgroup.controller;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.IssuesBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IIssueAnalysisService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueMobileRestController {
	
	@Autowired private IIssuesService issuesService;
	@Autowired private IUserProfileService	userProfileService;
	@Autowired CompanyConfigurationService companyConfigurationService;
	@Autowired  IIssueAnalysisService 				issueAnalysisService;
	IssuesBL			IBL 				= new IssuesBL();

	@RequestMapping(value="/createIssueFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  createIssueFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return issuesService.createIssueFromMobile(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/saveIssueFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveIssueFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject						object 					= null;
		int 							companyId 				= 0;
		long							userId					= 0;
		CustomUserDetails				userDetails				= null;
		HashMap<String, Object>         configuration			= null;
		List<ValueObject>			    multiIssues		    	= null;
		ValueObject 				   valueOutObject 		    = null;
		try {
			object		= new ValueObject(allRequestParams);
			companyId   	= object.getInt("companyId");
			userId			= object.getLong("userId",0);

			UserProfileDto		userProfile =  userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			userDetails		 = new CustomUserDetails(companyId, userId, userProfile.getUser_email());
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			if((boolean) configuration.getOrDefault("multipleIssues", false)){
				multiIssues= (List<ValueObject>) JsonConvertor.toValueObjectFromJsonString(object.getString("multiIssueDetails"));
			}
			if(multiIssues != null && !multiIssues.isEmpty()) {
				object.put("userDetails", userDetails);
				object.put("multiIssueDetails", multiIssues);
				valueOutObject = issuesService.saveMultiIssues(object);
			}else{
				object.put("userDetails", userDetails);
				valueOutObject =  issuesService.saveIssuesDetails(object);
			}
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/showIssueFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  showIssueFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return issuesService.showIssueFromMobile(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/editIssueFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  editIssueFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return issuesService.editIssueFromMobile(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/updateIssueFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateIssueFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject						object 					= null;
		int 							companyId 				= 0;
		long							userId					= 0;
		CustomUserDetails				userDetails				= null;
		try {
			object			= new ValueObject(allRequestParams);
			companyId   	= object.getInt("companyId");
			userId			= object.getLong("userId");
			UserProfileDto		userProfile =  userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			userDetails		 = new CustomUserDetails(companyId, userId, userProfile.getUser_email());
			object.put("userDetails", userDetails);
			return issuesService.updateIssuesDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/searchIssueFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  searchIssueFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			
			return issuesService.searchIssueFromMobile(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/resolveIssueFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  resolveIssueFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			
			return issuesService.resolveIssueFromMobile(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/rejectIssueFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  rejectIssueFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return issuesService.rejectIssueFromMobile(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/reOpenIssueFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  reOpenIssueFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return issuesService.reOpenIssueFromMobile(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/closeIssueFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  closeIssueFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return issuesService.closeIssueFromMobile(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/getIssueImageForMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getIssueImageForMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return issuesService.getIssueImageForMobile(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/saveIssueImageFromMobile", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveIssueImageFromMobile(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return issuesService.saveIssueImageFromMobile(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
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
	@PostMapping(path="/getIssueAnalysisDetails",produces="application/json")
	public HashMap<Object, Object> getIssueAnalysisDetails(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			return issueAnalysisService.getIssueAnalysisDetails(valueObject).getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
		}
	}
	@PostMapping(path="/saveIssueAnalysis",produces="application/json")
	public HashMap<Object, Object> saveIssueAnalysis(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(allRequestParam);
			return issueAnalysisService.saveIssueAnalysis(valueObject).getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
		}
	}
	@PostMapping(path="/getIssueDetailsForAnalysisByIssueId",produces="application/json")
	public HashMap<Object, Object> getIssueDetailsForAnalysisByIssueId(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		ValueObject valueObject = null;
		IssuesDto	issuesDto	= null;
		try {
			valueObject = new ValueObject(allRequestParam);
			issuesDto = IBL.Showing_Issues_Details(issuesService.get_IssuesDetails(valueObject.getLong("issueId"),valueObject.getInt("companyId")));
			valueObject.put("issueDetails", issuesDto);
			return valueObject.getHtData();
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject = null;
		}
	}
	@RequestMapping(value="/getIssueBreakDownDetailsFromMob", produces="application/json", method=RequestMethod.POST)
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
	@RequestMapping(value="getVehicleWiseIssueDetailsFromMob", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleWiseIssueDetailsFromMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= issuesService.getVehicleWiseIssueDetails(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
}
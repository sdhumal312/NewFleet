package org.fleetopgroup.rest.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.IssuesBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.serviceImpl.IIssueAnalysisService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.web.controller.AESEncryptDecrypt;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IssueAnalysisWS {
	
	@Autowired  IIssueAnalysisService 				issueAnalysisService;
	@Autowired  IIssuesService 						issuesService;
	IssuesBL IBL = new IssuesBL();
	Date 		currentDateUpdate 	= new Date();                                             
	Timestamp 	currentTimestamp 	= new java.sql.Timestamp(currentDateUpdate.getTime());    
	
	@GetMapping(value = "/addIssueAnalysis")
	public ModelAndView addIssueAnalysis(@RequestParam("issueId") final String issueId, final HttpServletRequest request) throws Exception {
		ModelAndView 					map 			= null;
		CustomUserDetails				userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map 		= new ModelAndView("addIssueAnalysis");
			map.addObject("currentTimestamp", currentTimestamp);
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("issueEncId", issueId);
			map.addObject("issueId", Long.parseLong(AESEncryptDecrypt.decryptBase64(issueId)));
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
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
	
}

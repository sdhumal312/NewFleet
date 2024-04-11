package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.serviceImpl.ICommentTypeService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CommentTypeController {
	
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired IPartCategoriesService 				partCategoriesService;
	@Autowired private ICommentTypeService 			commentTypeService;
	
				PartCategoriesBL 				partCategoriesBL	 			= new PartCategoriesBL();
	
	@RequestMapping(value = "/viewCommentType", method = RequestMethod.GET)
	public ModelAndView viewCommentType(@ModelAttribute("command") MasterParts MasterPartsBean,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails			userDetails			= null;
		try{
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("accessToken", Utility.getUniqueToken(request));
			model.put("userDetails", userDetails);
		}  catch (Exception e) {
			LOGGER.error("Exception viewCommentType : ", e);
			throw e;
		}
		return new ModelAndView("viewCommentType", model);
	}
	
	@RequestMapping(value = "/saveCommentType", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveCommentType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return commentTypeService.saveCommentTypeDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getPageWiseCommentTypeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPageWiseCommentTypeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return commentTypeService.getcommentTypeListByCompanyId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getCommentTypeById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getCommentTypeById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return commentTypeService.getCommentTypeById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editCommentType", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editCommentType(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return commentTypeService.editCommentType(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteCommentTypeById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteCommentTypeById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return commentTypeService.deleteCommentTypeById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
}
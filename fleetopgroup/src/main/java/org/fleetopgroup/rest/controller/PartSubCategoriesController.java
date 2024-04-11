package org.fleetopgroup.rest.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IPartSubCategoriesService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PartSubCategoriesController {
	
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired IPartCategoriesService 				partCategoriesService;
	@Autowired private IPartSubCategoriesService 	PartSubCategoriesService;
	
				PartCategoriesBL 				partCategoriesBL	 			= new PartCategoriesBL();
	
	@RequestMapping(value = "/addPartSubCategories", method = RequestMethod.GET)
	public ModelAndView addPartSubCategories(@ModelAttribute("command") MasterParts MasterPartsBean,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		CustomUserDetails			userDetails			= null;
		try{
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			model.put("permissions", permission);
			model.put("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
		}  catch (Exception e) {
			LOGGER.error("Exception addPartSubCategories : ", e);
			throw e;
		}
		return new ModelAndView("addPartSubCategories", model);
	}
	
	@RequestMapping(value = "/savePartSubCategories", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  savePartSubCategories(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return PartSubCategoriesService.savePartSubCatrgories(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getPartSubCategoryList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartSubCategoryList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return PartSubCategoriesService.getPartSubCatrgoryList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getPartSubCategoryById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartSubCategoryById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return PartSubCategoriesService.getPartSubCategoryById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editPartSubCategory", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editPartSubCategory(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return PartSubCategoriesService.editPartSubCategory(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/deletePartSubCategoryById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deletePartSubCategoryById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return PartSubCategoriesService.deletePartSubCategoryById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
}
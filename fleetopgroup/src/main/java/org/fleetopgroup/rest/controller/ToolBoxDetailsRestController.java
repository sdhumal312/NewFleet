package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.serviceImpl.IToolBoxDetailsService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ToolBoxDetailsRestController 
{
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	private IToolBoxDetailsService toolBoxDetailsService;
	
	@RequestMapping(value="/ToolBoxDetails", method = RequestMethod.GET)
	public ModelAndView addToolBox(@RequestParam("Id") final Integer vid ,final HttpServletRequest request){
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			map.put("vid", vid);
			return new ModelAndView("ToolBoxDetails", map);
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/saveToolBoxDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveToolBox(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return toolBoxDetailsService.saveToolBoxDetails(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} 
	}
	
	/*	@RequestMapping(value = "/getToolBoxDetailsList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getToolBoxList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception 
	{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return toolBoxDetailsService.getToolBoxDetailsList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}*/
		
		@RequestMapping(value = "/getToolBoxDetailsByVid", method = RequestMethod.POST, produces="application/json")
		public HashMap<Object, Object>  getToolBoxDetailsByVid(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception 
		{
			ValueObject 		object 					= null;
			try {
				object 				= new ValueObject(allRequestParams);
				return toolBoxDetailsService.getToolBoxDetailsByVid(object).getHtData();
			} catch (Exception e) {
				throw e;
			} 
		}
	
	/*@RequestMapping(value = "/getToolBoxDetailsByToolBoxDetailsId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getToolBoxListById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return toolBoxDetailsService.getToolBoxDetailsByToolBoxDetailsId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}*/
	
	/*@RequestMapping(value = "/editToolBoxDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editToolBox(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return toolBoxDetailsService.editToolBoxDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}*/
	
	@RequestMapping(value = "/deleteToolBoxDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteToolBox(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return toolBoxDetailsService.deleteToolBoxDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
}

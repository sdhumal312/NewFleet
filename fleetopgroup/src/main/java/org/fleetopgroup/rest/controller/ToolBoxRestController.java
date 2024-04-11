package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.model.ToolBox;
import org.fleetopgroup.persistence.serviceImpl.IToolBoxService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class ToolBoxRestController 
{
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	private IToolBoxService toolBoxService;
	
	@RequestMapping(value="/addToolBox", method = RequestMethod.GET)
	public ModelAndView addToolBox(final HttpServletRequest request){
		try {
			ModelAndView map = new ModelAndView("addToolBox");
			return map;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	@RequestMapping(value = "/saveToolBox", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveToolBox(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return toolBoxService.saveToolBox(object).getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getToolBoxList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getToolBoxList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception 
	{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return toolBoxService.getToolBoxList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getToolBoxById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getToolBoxListById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return toolBoxService.getToolBoxListById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/editToolBox", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  editToolBox(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			return toolBoxService.editToolBox(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteToolBox", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteToolBox(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return toolBoxService.deleteToolBox(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/toolBoxAutoComplete", method = RequestMethod.POST)
	public void getVehicleListServiceEntries(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValueObject					valueObject 			= null;
		List<ToolBox> 				toolBoxList				= null;
		List<ToolBox> 				finaltoolBoxList		= null;
		ToolBox 					toolBox 				= null;
		try {
			valueObject				= new ValueObject();
			toolBoxList 			= new ArrayList<ToolBox>();
			finaltoolBoxList 		= new ArrayList<ToolBox>();
			
			valueObject				= toolBoxService.getAllToolBoxForAutoComplete(term);
			toolBoxList				= (List<ToolBox>) valueObject.get("toolBoxList");
			
			if(toolBoxList != null && !toolBoxList.isEmpty()) {	
				for (ToolBox dto : toolBoxList) {
					toolBox 			= new ToolBox();
					toolBox.setToolBoxId(dto.getToolBoxId());
					toolBox.setToolBoxName(dto.getToolBoxName());

					finaltoolBoxList.add(toolBox);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(finaltoolBoxList));
		} catch (Exception e) {
			LOGGER.error("Err", e);
		}finally {
			valueObject 			= null;  
			toolBoxList				= null;  
			finaltoolBoxList		= null;  
		}
	}
	
	
}

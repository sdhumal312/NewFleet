package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.DriverJobTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.DriverJobType;
import org.fleetopgroup.persistence.model.DriverJobTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class DriverJobTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDriverJobTypeService driverJobTypeService;

	@Autowired
	private IDriverJobTypeHistoryService driverJobTypeHistoryService;
	
	DriverJobTypeBL driverJobTypeBL = new DriverJobTypeBL();

	@RequestMapping(value = "/addDriverJobType", method = RequestMethod.GET)
	public ModelAndView addDriverJobType(@ModelAttribute("command") DriverJobType DriverJobType, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("driverJobType", driverJobTypeBL.DriJobTypeListofBean(driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Driver JOB TYPE Page:", e);
		}
		return new ModelAndView("addDriverJobType", model);
	}

	@RequestMapping(value = "/saveDriJobType", method = RequestMethod.POST)
	public ModelAndView saveDriverJobType(@ModelAttribute("command") DriverJobType driverJobType,BindingResult result) {
		Map<String, Object> 	model 				= new HashMap<String, Object>();
		CustomUserDetails		userDetails			= null;
		DriverJobType 			GET_JobType 		= null; 
		DriverJobType 			Validate 			= null;
		
		try {
			userDetails			=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			GET_JobType 		= driverJobTypeBL.prepareDriJobTypeModel(driverJobType);
			Validate 			= driverJobTypeService.validateDriverJobType(driverJobType.getDriJobType(), userDetails.getCompany_id());
			
			if (Validate == null) {
				GET_JobType.setCompanyId(userDetails.getCompany_id());
				GET_JobType.setCreatedById(userDetails.getId());
				GET_JobType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				GET_JobType.setLastModifiedById(userDetails.getId());
				GET_JobType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				
				driverJobTypeService.addDriverJobType(GET_JobType);
				model.put("saveDriverJobType", true);
			} else {
				model.put("alreadyDriverJobType", true);
				return new ModelAndView("redirect:/addDriverJobType.html", model);
			}
		} catch (Exception e) {

			LOGGER.error("Driver JOB TYPE Page:", e);
			model.put("alreadyDriverJobType", true);
			return new ModelAndView("redirect:/addDriverJobType.html", model);
		} finally {
			userDetails			= null;
			GET_JobType 		= null;
			Validate 			= null;
		}
		return new ModelAndView("redirect:/addDriverJobType.html", model);
	}

	@RequestMapping(value = "/updateDriJobType", method = RequestMethod.POST)
	public ModelAndView updateDriverJobType(@ModelAttribute("command") DriverJobType driverJobType, BindingResult result) {
		Map<String, Object> 		model 					= new HashMap<String, Object>();
		CustomUserDetails			userDetails				= null;
		DriverJobType 				Validate 				= null;
		DriverJobTypeHistory		driverJobTypeHistory	= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Validate 			= driverJobTypeService.getDriverJobType(driverJobType.getDriJobId(), userDetails.getCompany_id());
			
			DriverJobType JobType = driverJobTypeBL.prepareDriJobTypeModel(driverJobType);
			JobType.setLastModifiedById(userDetails.getId());
			JobType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
			JobType.setCompanyId(userDetails.getCompany_id());
			
			if(!Validate.getDriJobType().equalsIgnoreCase(JobType.getDriJobType())) {
				DriverJobType jobValidate = driverJobTypeService.validateDriverJobType(JobType.getDriJobType(), userDetails.getCompany_id());
				if(jobValidate != null) {
					model.put("alreadyDriverJobType", true);
					return new ModelAndView("redirect:/addDriverJobType.html", model);
				}
			}
			
			driverJobTypeHistory	= driverJobTypeBL.prepareHistoryModel(driverJobTypeService.getDriverJobType(JobType.getDriJobId(), userDetails.getCompany_id()));
			
			driverJobTypeService.updateDriverJobType(JobType);
			driverJobTypeHistoryService.addDriverJobTypeHistory(driverJobTypeHistory);
			
			model.put("updateDriverJobType", true);
		} catch (Exception e) {
			LOGGER.error("Driver JOB TYPE Page:", e);
			model.put("alreadyDriverJobType", true);
			return new ModelAndView("redirect:/addDriverJobType.html", model);
		}
		return new ModelAndView("redirect:/addDriverJobType.html", model);
	}

	@RequestMapping(value = "/editDriverJobType", method = RequestMethod.GET)
	public ModelAndView editdriver(@ModelAttribute("command") DriverJobType DriverJobType, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DriverJobType editDriverType = driverJobTypeService.getDriverJobType(DriverJobType.getDriJobId(), userDetails.getCompany_id());
			if (editDriverType != null) {

				model.put("driverJobType", driverJobTypeBL.prepareDriverJobTypeBean(editDriverType));
			} else {
				return new ModelAndView("redirect:/addDriverJobType.html?alreadyDriverJobType=ture", model);
			}

		} catch (Exception e) {
			LOGGER.error("Driver JOB TYPE Page:", e);
			model.put("alreadyDriverJobType", true);
			return new ModelAndView("redirect:/addDriverJobType.html", model);
		}
		return new ModelAndView("editDriverJobType", model);
	}

	@RequestMapping(value = "/deleteDriverJobType", method = RequestMethod.GET)
	public ModelAndView deleteDriverJobType(@ModelAttribute("command") DriverJobType driverJobType,
			BindingResult result) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		CustomUserDetails			userDetails					= null; 
		DriverJobType 				deleteDriverType 			= null; 
		DriverJobTypeHistory		driverJobTypeHistory		= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			deleteDriverType 	= driverJobTypeService.getDriverJobType(driverJobType.getDriJobId(), userDetails.getCompany_id());
			
			if (deleteDriverType != null) {
				driverJobTypeHistory	= driverJobTypeBL.prepareHistoryModel(deleteDriverType);
				
				driverJobTypeService.deleteDriverJobType(driverJobType.getDriJobId(), userDetails.getCompany_id());
				driverJobTypeHistoryService.addDriverJobTypeHistory(driverJobTypeHistory);
				
				model.put("deleteDriverJobType", true);
			} else {
				return new ModelAndView("redirect:/addDriverJobType.html?alreadyDriverJobType=ture", model);
			}
		} catch (Exception e) {
			LOGGER.error("Driver JOB TYPE Page:", e);
			model.put("alreadyDriverJobType", true);
			return new ModelAndView("redirect:/addDriverJobType.html", model);
		}
		return new ModelAndView("redirect:/addDriverJobType.html", model);
	}

	// select Ajax Drop down value
	@RequestMapping(value = "/getDriverJobType", method = RequestMethod.POST)
	public void getDriverJobType(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DriverJobType> group = new ArrayList<DriverJobType>();
		for (DriverJobType add : driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id())) {
			DriverJobType wadd = new DriverJobType();
			wadd.setDriJobId(add.getDriJobId());
			wadd.setDriJobType(add.getDriJobType());
			group.add(wadd);
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));
		response.getWriter().write(gson.toJson(group));
	}
}
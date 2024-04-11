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

import org.fleetopgroup.persistence.bl.DriverTrainingTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverTrainingTypeDto;
import org.fleetopgroup.persistence.model.DriverTrainingType;
import org.fleetopgroup.persistence.model.DriverTrainingTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IDriverTrainingTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IDriverTrainingTypeService;
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
public class DriverTrainingTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDriverTrainingTypeService driverTrainingTypeService;

	@Autowired
	private IDriverTrainingTypeHistoryService driverTrainingTypeHistoryService;
	
	DriverTrainingTypeBL driverTrainingTypeBL = new DriverTrainingTypeBL();

	@RequestMapping(value = "/addDriverTrainingType", method = RequestMethod.GET)
	public ModelAndView addDriverTrainingType(@ModelAttribute("command") DriverTrainingTypeDto DriverTrainingTypeBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("driverTrainingType",
					driverTrainingTypeBL.DriTrainingTypeListofBean(driverTrainingTypeService.listDriverTrainingTypeByCompanyId(userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Driver Training Type Page:", e);
		}
		return new ModelAndView("addDriverTrainingType", model);
	}

	@RequestMapping(value = "/saveDriTrainingType", method = RequestMethod.POST)
	public ModelAndView saveDriverTrainingType(@ModelAttribute("command") DriverTrainingTypeDto driverTrainingTypeBean, BindingResult result) {
		Map<String, Object> 	model 					= new HashMap<String, Object>();
		CustomUserDetails		userDetails				= null;
		DriverTrainingType 		GET_TrainingType 		= null;
		DriverTrainingType 		Validate 				= null;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			GET_TrainingType 		= driverTrainingTypeBL.prepareDriTrainingTypeModel(driverTrainingTypeBean);
			Validate 				= driverTrainingTypeService.ValidateDriverTrainingType(driverTrainingTypeBean.getDri_TrainingType(), userDetails.getCompany_id());
			
			if (Validate == null) {
				GET_TrainingType.setCompanyId(userDetails.getCompany_id());
				GET_TrainingType.setCreatedById(userDetails.getId());
				GET_TrainingType.setLastModifiedById(userDetails.getId());
				GET_TrainingType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				GET_TrainingType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				
				driverTrainingTypeService.addDriverTrainingType(GET_TrainingType);
				model.put("saveDriverTrainingType", true);
			} else {
				model.put("alreadyDriverTrainingType", true);
				return new ModelAndView("redirect:/addDriverTrainingType.html", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Driver Training Type Page:", e);
			model.put("alreadyDriverTrainingType", true);
			return new ModelAndView("redirect:/addDriverTrainingType.html", model);
		} finally {
			userDetails				= null;
			GET_TrainingType 		= null;
			Validate 				= null;
		}
		return new ModelAndView("redirect:/addDriverTrainingType.html", model);
	}

	@RequestMapping(value = "/updateDriTrainingType", method = RequestMethod.POST)
	public ModelAndView updateDriverTrainingType(@ModelAttribute("command") DriverTrainingTypeDto driverTrainingTypeBean, BindingResult result) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		CustomUserDetails			userDetails					= null;
		DriverTrainingType 			Validate 					= null;
		DriverTrainingTypeHistory	driverTrainingTypeHistory	= null;
		
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Validate 			= driverTrainingTypeService.ValidateDriverTrainingType(driverTrainingTypeBean.getDri_TrainingType(), userDetails.getCompany_id());
			
			if(Validate == null) {
				driverTrainingTypeHistory	= driverTrainingTypeBL.prepareHistoryModel(driverTrainingTypeService.getDriverTrainingType(driverTrainingTypeBean.getDri_id(), userDetails.getCompany_id()));
				
				driverTrainingTypeService.updateDriverTrainingType(driverTrainingTypeBean.getDri_TrainingType(),userDetails.getId(), new Timestamp(System.currentTimeMillis())
						,driverTrainingTypeBean.getDri_id(), userDetails.getCompany_id());
				driverTrainingTypeHistoryService.addDriverTrainingTypeHistory(driverTrainingTypeHistory);
				
				model.put("updateDriverTrainingType", true);
			}else {
				model.put("alreadyDriverTrainingType", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Driver Training Type Page:", e);
			model.put("alreadyDriverTrainingType", true);
			return new ModelAndView("redirect:/addDriverTrainingType.html", model);
		}
		return new ModelAndView("redirect:/addDriverTrainingType.html", model);
	}

	@RequestMapping(value = "/editDriverTrainingType", method = RequestMethod.GET)
	public ModelAndView editdriver(@ModelAttribute("command") DriverTrainingTypeDto DriverTrainingTypeBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("driverTrainingType", driverTrainingTypeBL.prepareDriverTrainingTypeBean(driverTrainingTypeService.getDriverTrainingType(DriverTrainingTypeBean.getDri_id(), userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Driver Training Type Page:", e);
			return new ModelAndView("redirect:/addDriverTrainingType.html", model);
		}
		return new ModelAndView("editDriverTrainingType", model);
	}

	@RequestMapping(value = "/deleteDriverTrainingType", method = RequestMethod.GET)
	public ModelAndView deleteDriverTrainingType(
			@ModelAttribute("command") DriverTrainingTypeDto driverTrainingTypeBean, BindingResult result) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		CustomUserDetails			userDetails					= null;
		DriverTrainingTypeHistory	driverTrainingTypeHistory	= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverTrainingTypeHistory	= driverTrainingTypeBL.prepareHistoryModel(driverTrainingTypeService.getDriverTrainingType(driverTrainingTypeBean.getDri_id(), userDetails.getCompany_id()));
			
			driverTrainingTypeService.deleteDriverTrainingType(driverTrainingTypeBean.getDri_id(), userDetails.getCompany_id());
			driverTrainingTypeHistoryService.addDriverTrainingTypeHistory(driverTrainingTypeHistory);
			
			model.put("deleteDriverTrainingType", true);
		} catch (Exception e) {
			LOGGER.error("Driver Training Type Page:", e);
			model.put("alreadyDriverTrainingType", true);
			return new ModelAndView("redirect:/addDriverTrainingType.html", model);
		}
		return new ModelAndView("redirect:/addDriverTrainingType.html", model);
	}

	// select Ajax Drop down value
	@RequestMapping(value = "/getDriverTrainingType", method = RequestMethod.POST)
	public void getDriverTrainingType(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<DriverTrainingType> group = new ArrayList<DriverTrainingType>();
			for (DriverTrainingType add : driverTrainingTypeService.listDriverTrainingTypeByCompanyId(userDetails.getCompany_id())) {
				DriverTrainingType wadd = new DriverTrainingType();
				wadd.setDri_TrainingType(add.getDri_TrainingType());
				group.add(wadd);
			}
			Gson gson = new Gson();
			// System.out.println("jsonStudents = " + gson.toJson(addresses));
			response.getWriter().write(gson.toJson(group));
		} catch (Exception e) {
			throw e;
		}
		
	}
}
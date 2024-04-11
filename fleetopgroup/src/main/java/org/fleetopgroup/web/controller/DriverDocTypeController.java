package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.persistence.bl.DriverDocTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverDocTypeDto;
import org.fleetopgroup.persistence.model.DriverDocType;
import org.fleetopgroup.persistence.model.DriverDocTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocTypeHIstoryService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocTypeService;
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


@Controller
public class DriverDocTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDriverDocTypeService driverDocTypeService;

	@Autowired
	private IDriverDocTypeHIstoryService driverDocTypeHIstoryService;
	
	DriverDocTypeBL driverDocTypeBL = new DriverDocTypeBL();

	@RequestMapping(value = "/addDriverDocType", method = RequestMethod.GET)
	public ModelAndView addDriverDocType(@ModelAttribute("command") DriverDocTypeDto DriverDocTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("driverDocType", driverDocTypeBL.DriDocTypeListofDto(driverDocTypeService.findAllByCompanyId(userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("addDriverDocType", model);
	}
	
	@RequestMapping(value = "/saveDriDocType", method = RequestMethod.POST)
	public ModelAndView saveDriverDocType(@ModelAttribute("command") DriverDocTypeDto driverDocTypeDto,
			BindingResult result) {
		Map<String, Object> 	model 					= new HashMap<String, Object>();
		CustomUserDetails		userDetails				= null;	
		DriverDocType 			GET_Type 				= null;
		DriverDocType 			validate 				= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			GET_Type 		= driverDocTypeBL.prepareDriDocTypeModel(driverDocTypeDto);
			validate 		= driverDocTypeService.validateDriDocType(driverDocTypeDto.getDri_DocType(), userDetails.getCompany_id());
			
			if(validate == null){
					GET_Type.setCompany_Id(userDetails.getCompany_id());
					GET_Type.setCreatedById(userDetails.getId());
					GET_Type.setCreatedOn(new Timestamp(System.currentTimeMillis()));
					driverDocTypeService.registerNewDriverDocType(GET_Type);
					model.put("saveDriverDocType", true);
			}else{
				model.put("alreadyDriverDocType", true);
				return new ModelAndView("redirect:/addDriverDocType.html", model);
			}
			
		} catch (Exception e) {
			model.put("alreadyDriverDocType", true);
			LOGGER.error("Driver Document Page:", e);
			return new ModelAndView("redirect:/addDriverDocType.html", model);
		} finally {
			userDetails				= null;
			GET_Type 				= null;
			validate 				= null;
		}
		return new ModelAndView("redirect:/addDriverDocType.html", model);
	}

	@RequestMapping(value = "/updateDriDocType", method = RequestMethod.POST)
	public ModelAndView updateDriverDocType(@ModelAttribute("command") DriverDocTypeDto driverDocTypeDto,
			BindingResult result) {
		Map<String, Object> 	model 						= new HashMap<String, Object>();
		CustomUserDetails		userDetails					= null;
		DriverDocType 			validate 					= null;
		DriverDocType 			DocType 					= null;
		DriverDocTypeHistory	driverDocTypeHistory		= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validate 		= driverDocTypeService.validateDriDocType(driverDocTypeDto.getDri_DocType(), userDetails.getCompany_id());
			
			if(validate == null) {
				DocType 				= driverDocTypeBL.prepareDriDocTypeModel(driverDocTypeDto);
				driverDocTypeHistory	= driverDocTypeBL.prepareHistoryModel(driverDocTypeService.getDriverDocTypeByID(DocType.getDri_id(), userDetails.getCompany_id()));
				
				driverDocTypeService.updateDriverDocType(DocType.getDri_DocType(),userDetails.getId(), new Timestamp(System.currentTimeMillis()), DocType.getDri_id(), userDetails.getCompany_id());
				driverDocTypeHIstoryService.registerNewDriverDocTypeHistory(driverDocTypeHistory);
				
				model.put("updateDriverDocType", true);
			}else {
				model.put("alreadyDriverDocType", true);
			}
			
			
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			model.put("alreadyDriverDocType", true);
			LOGGER.error("Driver Document Page:", e);
			return new ModelAndView("redirect:/addDriverDocType.html", model);
		} finally {
			userDetails					= null;
			validate 					= null;
			DocType 					= null;
			driverDocTypeHistory		= null;
		}
		return new ModelAndView("redirect:/addDriverDocType.html", model);
	}

	
	@RequestMapping(value = "/editDriverDocType", method = RequestMethod.GET)
	public ModelAndView editdriver(@ModelAttribute("command") DriverDocTypeDto DriverDocTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("driverDocType", driverDocTypeBL
					.prepareDriverDocTypeDto(driverDocTypeService.getDriverDocTypeByID(DriverDocTypeDto.getDri_id(), userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("editDriverDocType", model);
	}

	@RequestMapping(value = "/deleteDriverDocType", method = RequestMethod.GET)
	public ModelAndView deleteDriverDocType(@ModelAttribute("command") DriverDocTypeDto driverDocTypeDto,
			BindingResult result) {
		Map<String, Object> 	model 					= new HashMap<String, Object>();
		DriverDocTypeHistory	driverDocTypeHistory	= null;
		CustomUserDetails		userDetails				= null;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverDocTypeHistory	= driverDocTypeBL.prepareHistoryModel(driverDocTypeService.getDriverDocTypeByID(driverDocTypeDto.getDri_id(), userDetails.getCompany_id()));
			
			driverDocTypeService.deleteDriverDocType(driverDocTypeDto.getDri_id(), userDetails.getCompany_id());
			driverDocTypeHIstoryService.registerNewDriverDocTypeHistory(driverDocTypeHistory);
			
			model.put("deleteDriverDocType", true);
			
		} catch (Exception e) {
			model.put("alreadyDriverDocType", true);
			LOGGER.error("Driver Document Page:", e);
			return new ModelAndView("redirect:/addDriverDocType.html", model);
		} finally {
			driverDocTypeHistory	= null;
			userDetails				= null;
		}
		return new ModelAndView("redirect:/addDriverDocType.html", model);
	}
}
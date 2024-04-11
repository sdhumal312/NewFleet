package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.Collection;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalSubTypeDto;
import org.fleetopgroup.persistence.model.RenewalSubType;
import org.fleetopgroup.persistence.model.RenewalSubTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RenewalSubTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IRenewalTypeService RenewalTypeService;
	RenewalTypeBL renewalTypeBL = new RenewalTypeBL();

	@Autowired
	private IRenewalSubTypeService renewalSubTypeService;

	@Autowired
	private IRenewalSubTypeHistoryService renewalSubTypeHistoryService;

	@RequestMapping(value = "/addRenewalSubType", method = RequestMethod.GET)
	public ModelAndView addRenewalSubType(@ModelAttribute("command") RenewalSubTypeDto renewalSubTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("renewalType", renewalTypeBL.RenewalListofDto(RenewalTypeService.findAllByCompanyId(userDetails.getCompany_id())));
			model.put("renewalSubType", renewalTypeBL.RenewalSubListofDto(renewalSubTypeService.findAllByCompanyId(userDetails.getCompany_id())));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Renewal Sub Type Page:", e);
		}
		return new ModelAndView("addRenewalSubType", model);
	}

	@RequestMapping(value = "/saveRenewalSubType", method = RequestMethod.POST)
	public ModelAndView saveRenewalSubType(@ModelAttribute("command") RenewalSubTypeDto renewalTypeDto,	BindingResult result) {
		Map<String, Object> model 			= new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null; 
		RenewalSubType 		GET_DocType 	= null;
		RenewalSubType 		renewalSubType 	= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			GET_DocType 	= renewalTypeBL.prepareRenewalSubTypeModel(renewalTypeDto);
			renewalSubType 	= renewalSubTypeService.getRenewalSubType(renewalTypeDto.getRenewal_SubType(), userDetails.getCompany_id());
			
			if (renewalSubType == null) {
				GET_DocType.setCompanyId(userDetails.getCompany_id());
				GET_DocType.setCreatedById(userDetails.getId());
				GET_DocType.setLastModifiedById(userDetails.getId());
				GET_DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				GET_DocType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				
				renewalSubTypeService.registerNewRenewalSubType(GET_DocType);
				model.put("saveRenewalSubType", true);
			} else {
				model.put("alreadyRenewalSubType", true);
				return new ModelAndView("redirect:/addRenewalSubType.in", model);
			}
		} catch (Exception e) {
			model.put("alreadyRenewalSubType", true);
			LOGGER.error("Renewal Sub Type Page:", e);
			return new ModelAndView("redirect:/addRenewalSubType.in", model);
		}
		return new ModelAndView("redirect:/addRenewalSubType.in", model);
	}

	@RequestMapping(value = "/updateRenewalSubType", method = RequestMethod.POST)
	public ModelAndView updateRenewalSubType(@ModelAttribute("command") RenewalSubTypeDto renewalTypeDto,BindingResult result) {
		Map<String, Object> 		model 						= new HashMap<String, Object>();
		CustomUserDetails			userDetails					= null;
		RenewalSubTypeDto			renewalSubType				= null;
		RenewalSubType 				subType 					= null;
		RenewalSubTypeHistory		renewalSubTypeHistory		= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			renewalSubType	= renewalSubTypeService.getRenewalSubTypeByID(renewalTypeDto.getRenewal_Subid(), userDetails.getCompany_id());
			
			if(!renewalSubType.getRenewal_SubType().equalsIgnoreCase(renewalTypeDto.getRenewal_SubType())) {
				RenewalSubType Validate = renewalSubTypeService.getRenewalSubType(renewalTypeDto.getRenewal_SubType(), userDetails.getCompany_id());
				if(Validate != null) {
					model.put("alreadyRenewalSubType", true);
					return new ModelAndView("redirect:/addRenewalSubType.html", model);
				}
			}
			
			subType 				= renewalTypeBL.prepareRenewalSubTypeModel(renewalTypeDto);
			renewalSubTypeHistory	= renewalTypeBL.prepareRenewalSubTypeHistoryModel(renewalSubTypeService.getRenewalSubTypeById(renewalTypeDto.getRenewal_Subid(), userDetails.getCompany_id()));
			
			renewalSubTypeService.updateRenewalSubType( subType.getRenewal_SubType(),
					subType.getRenewal_Subid(), userDetails.getId(),new Timestamp(System.currentTimeMillis()), userDetails.getCompany_id(), subType.getRenewal_id());
			renewalSubTypeHistoryService.registerNewRenewalSubTypeHistory(renewalSubTypeHistory);
			
			model.put("updateRenewalSubType", true);
			
		} catch (Exception e) {
			model.put("alreadyRenewalSubType", true);
			LOGGER.error("Renewal Sub Type Page:", e);
			return new ModelAndView("redirect:/addRenewalSubType.in", model);
		} finally {
			userDetails					= null;
			renewalSubType				= null;
			subType 					= null;
			renewalSubTypeHistory		= null;
		}
		return new ModelAndView("redirect:/addRenewalSubType.in", model);
	}

	@RequestMapping(value = "/editRenewalSubType", method = RequestMethod.GET)
	public ModelAndView editRenewalSub(@ModelAttribute("command") RenewalSubTypeDto RenewalSubTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("renewalSubType", renewalTypeBL.prepareRenewalSubTypeDto(
					renewalSubTypeService.getRenewalSubTypeByID(RenewalSubTypeDto.getRenewal_Subid(), userDetails.getCompany_id())));
			model.put("renewalType", renewalTypeBL.RenewalListofDto(RenewalTypeService.findAllByCompanyId(userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Renewal Sub Type Page:", e);
		}
		return new ModelAndView("editRenewalSubType", model);
	}

	@RequestMapping(value = "/deleteRenewalSubType", method = RequestMethod.GET)
	public ModelAndView deleteRenewalType(@ModelAttribute("command") RenewalSubTypeDto RenewalSubTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails					= null;
		RenewalSubTypeHistory		renewalSubTypeHistory		= null;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			renewalSubTypeHistory	= renewalTypeBL.prepareRenewalSubTypeHistoryModel(renewalSubTypeService.getRenewalSubTypeById(RenewalSubTypeDto.getRenewal_Subid(), userDetails.getCompany_id()));
			
			renewalSubTypeService.deleteRenewalSubType(RenewalSubTypeDto.getRenewal_Subid(), userDetails.getCompany_id());
			renewalSubTypeHistoryService.registerNewRenewalSubTypeHistory(renewalSubTypeHistory);
			
			model.put("deleteRenewalSubType", true);
		} catch (Exception e) {
			model.put("alreadyRenewalSubType", true);
			LOGGER.error("Renewal Sub Type Page:", e);
			return new ModelAndView("redirect:/addRenewalSubType.in", model);
		} finally {
			userDetails					= null;
			renewalSubTypeHistory		= null;
		}
		return new ModelAndView("redirect:/addRenewalSubType.in", model);
	}
	
	@RequestMapping(value = "/renewalSubTypeAjax", method = RequestMethod.GET)
	public ModelAndView renewalSubTypeAjax(@ModelAttribute("command") RenewalSubTypeDto renewalSubTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean editMandatorySubRenewal 		= false;
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();	
			if(permission.contains(new SimpleGrantedAuthority("EDIT_MANDATORY_SUB_RENEWAL"))) {
				editMandatorySubRenewal = true;
			}
			model.put("editMandatorySubRenewal", editMandatorySubRenewal);
			model.put("renewalType", renewalTypeBL.RenewalListofDto(RenewalTypeService.findAllByCompanyId(userDetails.getCompany_id())));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Renewal Sub Type Page:", e);
		}
		return new ModelAndView("RenewalSubTypeAjax", model);
	}

}

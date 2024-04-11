package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalTypeDto;
import org.fleetopgroup.persistence.model.RenewalType;
import org.fleetopgroup.persistence.model.RenewalTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RenewalTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IRenewalTypeService renewalTypeService;

	@Autowired
	private IRenewalTypeHistoryService renewalTypeHistoryService;
	
	@Autowired ICompanyConfigurationService	companyConfigurationService;
	
	RenewalTypeBL renewalTypeBL = new RenewalTypeBL();

	// Show main page Renewal type and create also
	@RequestMapping(value = "/addRenewalType", method = RequestMethod.GET)
	public ModelAndView addRenewalType(@ModelAttribute("command") RenewalTypeDto renewalTypeDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
		HashMap<String, Object>  configuration	=	companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
		model.put("configuration",configuration);	
		model.put("renewalType",renewalTypeService.findAllListByCompanyId(userDetails.getCompany_id()));
		} catch (Exception e) {
			LOGGER.error("Renewal Type Page:", e);
		}

		return new ModelAndView("addRenewalType", model);
	}

	// save Renewal type
	@RequestMapping(value = "/saveRenewalType", method = RequestMethod.POST)
	public ModelAndView saveRenewalType(@ModelAttribute("command") RenewalTypeDto renewalTypeDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			RenewalType GET_DocType = renewalTypeBL.prepareRenewalTypeModel(renewalTypeDto);
			List<RenewalType> validate = renewalTypeService.getRenewalType(renewalTypeDto.getRenewal_Type(),
					userDetails.getCompany_id());
			if (validate != null && !validate.isEmpty()) {
				return new ModelAndView("redirect:/addRenewalType.in?alreadyRenewalType=true");
			} else {
				GET_DocType.setCompanyId(userDetails.getCompany_id());
				GET_DocType.setCreatedById(userDetails.getId());
				GET_DocType.setLastModifiedById(userDetails.getId());
				GET_DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				GET_DocType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				renewalTypeService.registerNewRenewalType(GET_DocType);
				model.put("saveRenewalType", true);

			}
		} catch (Exception e) {

			LOGGER.error("Renewal Type Page:", e);
			return new ModelAndView("redirect:/addRenewalType.in?alreadyRenewalType=true");
		}
		/* return new ModelAndView("addRenewalType", model); */
		return new ModelAndView("redirect:/addRenewalType.in", model);
	}

	// update Renewal type
	@RequestMapping(value = "/updateRenewalType", method = RequestMethod.POST)
	public ModelAndView updateRenewalType(@ModelAttribute("command") RenewalTypeDto renewalTypeDto, final HttpServletRequest request) {
		
		CustomUserDetails 		userDetails 			= null;
		List<RenewalType> 		renewalTypeList 		= null;
		RenewalType 			renewalType				= null;
		RenewalTypeHistory		renewalTypeHistory		= null;
		RenewalType				preRenewalType			= null;
		
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			preRenewalType		= renewalTypeService.getRenewalTypeByID(renewalTypeDto.getRenewal_id());
			if(!preRenewalType.getRenewal_Type().equalsIgnoreCase(renewalTypeDto.getRenewal_Type().trim())) {
				renewalTypeList 	= renewalTypeService.getRenewalType(renewalTypeDto.getRenewal_Type(), userDetails.getCompany_id());
			}
			if (renewalTypeList == null || renewalTypeList.size() <= 0) {
				renewalType 	= renewalTypeBL.prepareRenewalTypeModel(renewalTypeDto);
				renewalType.setLastModifiedById(userDetails.getId());
				renewalType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				renewalType.setCompanyId(userDetails.getCompany_id());
				
				renewalTypeHistory	= renewalTypeBL.prepareHistoryModel(renewalTypeService.getRenewalTypeByID(renewalTypeDto.getRenewal_id()));
				
				renewalTypeService.updateRenewalType(renewalType);
				renewalTypeHistoryService.registerNewRenewalTypeHistory(renewalTypeHistory);
				
			} else {
				return new ModelAndView("redirect:/addRenewalType.in?alreadyRenewalType=true");
			}

		} catch (Exception e) {
			LOGGER.error("Renewal Type Page:", e);
			return new ModelAndView("redirect:/addRenewalType.in?alreadyRenewalType=true");
		} finally {
			userDetails 			= null;
			renewalTypeList 		= null;
			renewalType				= null;
			renewalTypeHistory		= null;
		}
		return new ModelAndView("redirect:/addRenewalType.in?Update=true");
	}

	// Edit Renewal type
	@RequestMapping(value = "/editRenewalTypes", method = RequestMethod.GET)
	public ModelAndView editRenewal(@ModelAttribute("command") RenewalTypeDto RenewalTypeDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	=	companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			model.put("configuration", configuration);
			model.put("renewalType", renewalTypeService.getRenewalTypeByID(RenewalTypeDto.getRenewal_id(), userDetails.getCompany_id()));
		} catch (Exception e) {
			LOGGER.error("Renewal Type Page:", e);
		}
		return new ModelAndView("editRenewalType", model);
	}

	// Delete Renewal type
	@RequestMapping(value = "/deleteRenewalType", method = RequestMethod.GET)
	public ModelAndView deleteRenewalType(@ModelAttribute("command") RenewalTypeDto renewalTypeDto,final HttpServletRequest request) {
		RenewalTypeHistory		renewalTypeHistory		= null;
		CustomUserDetails 		userDetails 			= null; 
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			renewalTypeHistory	= renewalTypeBL.prepareHistoryModel(renewalTypeService.getRenewalTypeByID(renewalTypeDto.getRenewal_id()));
			
			renewalTypeService.deleteRenewalType(renewalTypeDto.getRenewal_id(), userDetails.getCompany_id());
			renewalTypeHistoryService.registerNewRenewalTypeHistory(renewalTypeHistory);
			
		} catch (Exception e) {
			return new ModelAndView("redirect:/addRenewalType.in?alreadyRenewalType=true");
		} finally {
			renewalTypeHistory		= null;
			userDetails 			= null;
		}
		return new ModelAndView("redirect:/addRenewalType.in?Delete=true");

	}
	
	@RequestMapping(value = "/renewalTypeAjax", method = RequestMethod.GET)
	public ModelAndView renewalTypeAjax(@ModelAttribute("command") RenewalTypeDto renewalTypeDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			model.put("configuration",configuration);	
		} catch (Exception e) {
			LOGGER.error("Renewal Type Page:", e);
		}

		return new ModelAndView("RenewalTypeAjax", model);
	}
}
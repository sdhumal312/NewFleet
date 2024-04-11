package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.PartCategories;
import org.fleetopgroup.persistence.model.PartCategoriesHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class PartCategoriesController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IPartCategoriesService PartCategoriesService;

	@Autowired
	private IPartCategoriesHistoryService partCategoriesHistoryService;
	
	@Autowired private ICompanyConfigurationService	companyConfigurationService;

	PartCategoriesBL VehStBL = new PartCategoriesBL();

	// Show the the Vehicle Status of
	@RequestMapping(value = "/addPartCategories", method = RequestMethod.GET)
	public ModelAndView addVehicleType(@ModelAttribute("command") PartCategories PartCategoriesBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>   configuration		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			model.put("configuration", configuration);
			model.put("PartCategories", VehStBL.prepareListofBean(PartCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			model.put(MastersConfigurationConstants.COMMON_PART_CATEGORY, (boolean)configuration.get(MastersConfigurationConstants.COMMON_PART_CATEGORY));
		} catch (Exception e) {
			LOGGER.error("PartCategoriesService Type Page:", e);

		}finally {
			configuration		= null;
		}
		return new ModelAndView("addPartCategories", model);
	}

	/* save the vehicle Status */
	@RequestMapping(value = "/savePartCategories", method = RequestMethod.POST)
	public ModelAndView savePartCategories(@ModelAttribute("command") PartCategories PartCategories,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PartCategories GET_UI = VehStBL.prepareModel(PartCategories);
			
			PartCategories validate = PartCategoriesService.ValidatePCName(GET_UI.getPcName().trim(), userDetails.getCompany_id());
			if (validate == null) {
				GET_UI.setCompanyId(userDetails.getCompany_id());
				GET_UI.setCreatedById(userDetails.getId());
				GET_UI.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				PartCategoriesService.addPartCategories(GET_UI);
				model.put("savePartCategories", true);
			} else {
				model.put("alreadyPartCategories", true);
				return new ModelAndView("redirect:/addPartCategories.in", model);
			}

		} catch (Exception e) {
			model.put("alreadyPartCategories", true);
			LOGGER.error("PartCategoriesService Type Page:", e);
			return new ModelAndView("redirect:/addPartCategories.in", model);
		}
		return new ModelAndView("redirect:/addPartCategories.in", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/uploadPartCategories", method = RequestMethod.POST)
	public ModelAndView uploadPartCategories(@ModelAttribute("command") PartCategories PartCategories,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PartCategories		categories	= PartCategoriesService.getPartCategories(PartCategories.getPcid(), userDetails.getCompany_id());
	
			if(!categories.getPcName().trim().equalsIgnoreCase(PartCategories.getPcName().trim())) {
				PartCategories validate = PartCategoriesService.ValidatePCName(PartCategories.getPcName().trim(), userDetails.getCompany_id());
				if(validate != null) {
					model.put("alreadyPartCategories", true);
					return new ModelAndView("redirect:/addPartCategories.in", model);
				}
			}
			
			PartCategories status = VehStBL.prepareModel(PartCategories);
			status.setLastModifiedById(userDetails.getId());
			status.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
			status.setCompanyId(userDetails.getCompany_id());
			
			PartCategoriesHistory		categoriesHistory		= VehStBL.prepareHistoryModel(categories);
			
			PartCategoriesService.updatePartCategories(status);
			partCategoriesHistoryService.addPartCategoriesHistory(categoriesHistory);
			
			model.put("uploadPartCategories", true);
		
		} catch (Exception e) {
			model.put("alreadyPartCategories", true);
			LOGGER.error("PartCategoriesService Type Page:", e);
			return new ModelAndView("redirect:/addPartCategories.in", model);
		}
		return new ModelAndView("redirect:/addPartCategories.in", model);
	}

	@RequestMapping(value = "/editPartCategories", method = RequestMethod.GET)
	public ModelAndView editPartCategories(@ModelAttribute("command") PartCategories PartCategoriesBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>   configuration		= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			model.put("PartCategories", VehStBL
					.preparePartCategoriesBean(PartCategoriesService.getPartCategories(PartCategoriesBean.getPcid(), userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("PartCategoriesService Type Page:", e);
		}
		return new ModelAndView("editPartCategories", model);
	}

	@RequestMapping(value = "/deletePartCategories", method = RequestMethod.GET)
	public ModelAndView deletePartCategories(@ModelAttribute("command") PartCategories PartCategoriesBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PartCategoriesHistory		categoriesHistory	= VehStBL.prepareHistoryModel(PartCategoriesService.getPartCategories(PartCategoriesBean.getPcid(), userDetails.getCompany_id()));
			
			PartCategoriesService.deletePartCategories(PartCategoriesBean.getPcid(), userDetails.getCompany_id());
			partCategoriesHistoryService.addPartCategoriesHistory(categoriesHistory);
			
			model.put("deletePartCategories", true);
		} catch (Exception e) {
			model.put("alreadyPartCategories", true);
			LOGGER.error("PartCategoriesService Type Page:", e);
			return new ModelAndView("redirect:/addPartCategories.in", model);
		}
		return new ModelAndView("redirect:/addPartCategories.in", model);
	}
	
	@PostMapping(path="/searchPartCategory")
	public void getPartCategoryList(@RequestParam("term") String term ,HttpServletResponse response) {
		
		try {
			 List<PartCategories> partCategories = new ArrayList<>();
		partCategories = VehStBL.prepareListofBean(PartCategoriesService.searchPartCategories(term));
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(partCategories));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
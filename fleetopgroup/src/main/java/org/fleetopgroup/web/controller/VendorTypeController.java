package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VendorTypeConfigurationConstants;
import org.fleetopgroup.persistence.bl.VendorTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VendorTypeDto;
import org.fleetopgroup.persistence.model.VendorType;
import org.fleetopgroup.persistence.model.VendorTypeHistory;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VendorTypeController {

	@Autowired IVendorTypeService 			vendorTypeService;
	@Autowired IVendorTypeHistoryService 	vendorTypeHistoryService;
	@Autowired CompanyConfigurationService 	companyConfigurationService;

	VendorTypeBL vendorTypeBL = new VendorTypeBL();

	@RequestMapping(value = "/addVendorType", method = RequestMethod.GET)
	public ModelAndView addVendorType(@ModelAttribute("command") VendorTypeDto vendorTypeDto, final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration				= null;
		CustomUserDetails				userDetails 				= null;
		Integer							companyId					= 0;
		Boolean							isCommonMaster				= true;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
				isCommonMaster		= true;
			} else {
				companyId			= userDetails.getCompany_id();
				isCommonMaster		= false;
			}
			
			model.put("vendorType", vendorTypeBL.VendorListofDto(vendorTypeService.findAllByCompanyId(companyId, isCommonMaster)));

			return new ModelAndView("addVendorType", model);
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping(value = "/saveVendorType", method = RequestMethod.POST)
	public ModelAndView saveVendorType(@ModelAttribute("command") VendorTypeDto vendorTypeDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		HashMap<String, Object> 		configuration				= null;
		CustomUserDetails				userDetails 				= null;
		VendorType 						vendorType 					= null; 
		VendorType 						vendorTypeDB				= null;
		Integer							companyId					= 0;
		Boolean							isCommonMaster				= true;
		try {

			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
				isCommonMaster		= true;
			} else {
				companyId			= userDetails.getCompany_id();
				isCommonMaster		= false;
			}
			
			vendorType 			= vendorTypeBL.prepareVendorTypeModel(vendorTypeDto);
			vendorTypeDB 		= vendorTypeService.getVendorType(vendorTypeDto.getVendor_TypeName(), companyId);
			if (vendorTypeDB == null) {
				vendorType.setCompanyId(companyId);
				vendorType.setCreatedById(userDetails.getId());
				vendorType.setLastModifiedById(userDetails.getId());
				vendorType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				vendorType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				vendorType.setCommonMaster(isCommonMaster);
				vendorTypeService.registerNewVendorType(vendorType);
				
				model.put("saveVendorType", true);
			} else {
				return new ModelAndView("redirect:/addVendorType.in?danger=true");
			}

		} catch (Exception e) {

			return new ModelAndView("redirect:/addVendorType.in?danger=true");
		}
		return new ModelAndView("redirect:/addVendorType.in", model);

	}
	
	@RequestMapping(value = "/editVendorType", method = RequestMethod.GET)
	public ModelAndView editRenewal(@ModelAttribute("command") VendorTypeDto vendorTypeDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("vendorType", vendorTypeService.getVendorTypeByID(vendorTypeDto.getVendor_Typeid()));
		
		return new ModelAndView("editVendorType", model);
	}

	// edit Update
	@RequestMapping(value = "/updateVendorType", method = RequestMethod.POST)
	public ModelAndView updateVendorType(@ModelAttribute("command") VendorTypeDto vendorTypeDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails		userDetails 			= null;
		VendorType 				vendorTypeDB 			= null;
		VendorType 				docType 				= null;
		VendorType				vendorType				= null;
		VendorTypeHistory		vendorTypeHistory		= null;
		HashMap<String, Object> configuration			= null;
		Integer					companyId				= 0;
		Boolean					isCommonMaster			= true;
				
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
				isCommonMaster		= true;
			} else {
				companyId			= userDetails.getCompany_id();
				isCommonMaster		= false;
			}

			vendorTypeDB 	= vendorTypeService.getVendorType(vendorTypeDto.getVendor_TypeName(), companyId);
			docType 		= vendorTypeBL.prepareVendorTypeModel(vendorTypeDto);
			vendorType		= vendorTypeService.getVendorTypeByID(docType.getVendor_Typeid());
			
			if(vendorTypeDB == null) {
				docType.setCompanyId(companyId);
				docType.setCreatedById(vendorType.getCreatedById());
				docType.setCreatedOn(vendorType.getCreatedOn());
				docType.setLastModifiedById(userDetails.getId());
				docType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				docType.setCommonMaster(isCommonMaster);
				
				vendorTypeHistory	= vendorTypeBL.prepareHistoryModel(vendorType);
				
				vendorTypeService.registerNewVendorType(docType);
				vendorTypeHistoryService.registerNewVendorTypeHistory(vendorTypeHistory);
				
			}else {
				return new ModelAndView("redirect:/addVendorType.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/addVendorType.in?danger=true");
		} finally {
			userDetails 			= null;
			vendorTypeDB			= null;
			docType 				= null;
			vendorType				= null;
			vendorTypeHistory		= null;
		}
		
		model.put("updateVendorType", true);
		return new ModelAndView("redirect:/addVendorType.in", model);
	}

	@RequestMapping(value = "/deleteVendorType", method = RequestMethod.GET)
	public ModelAndView deleteVendorType(@ModelAttribute("command") VendorTypeDto vendorTypeDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails		userDetails				= null;
		VendorTypeHistory		vendorTypeHistory		= null;
		HashMap<String, Object> configuration			= null;
		Integer					companyId				= 0;
				
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
			} else {
				companyId			= userDetails.getCompany_id();
			}

			vendorTypeHistory	= vendorTypeBL.prepareHistoryModel(vendorTypeService.getVendorTypeByID(vendorTypeDto.getVendor_Typeid()));
			
			vendorTypeService.deleteVendorType(vendorTypeDto.getVendor_Typeid(), companyId);
			if(vendorTypeHistory != null) {
				vendorTypeHistoryService.registerNewVendorTypeHistory(vendorTypeHistory);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/addVendorType.in?danger=true");
		} finally {
			userDetails				= null;
			vendorTypeHistory		= null;
		}
		model.put("deleteVendorType", true);
		return new ModelAndView("redirect:/addVendorType.in", model);
	}

	

}

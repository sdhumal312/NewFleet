package org.fleetopgroup.rest.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PriviledgeType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.CompanyWisePrivilegesRepository;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.model.CompanyConfiguration;
import org.fleetopgroup.persistence.model.CompanyWisePrivileges;
import org.fleetopgroup.persistence.model.Privilege;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyWisePrivilegesService;
import org.fleetopgroup.persistence.serviceImpl.IPrivilegesService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class CompanyRoleRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	ICompanyWisePrivilegesService		companyWisePrivilegesService;
	@Autowired	IPrivilegesService					privilegesService;
	@Autowired	private CompanyWisePrivilegesRepository			companyWisePrivilegesRepository;
	@Autowired  ICompanyConfigurationService					companyConfigurationService;
	@Autowired  ICompanyService                                  CompanyService;
	
	@RequestMapping(value = "/getCompanyPriviledgeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getCompanyPriviledgeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return companyWisePrivilegesService.getCompanyWisePrivilegesList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/AddModulePrivileges", method = RequestMethod.GET)
	public ModelAndView ClothInventory(@RequestParam("CID") final String Company_Encode,final HttpServletRequest request) throws Exception {
		ModelAndView map = new ModelAndView("AddModulePrivileges");
		try {
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(Company_Encode);
			Integer Company_id = Integer.parseInt(new String(decodedByteArray));
			map.addObject("companyId", Company_id);
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping(value = "/getCompanyModulePriviledgeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getCompanyModulePriviledgeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return companyWisePrivilegesService.getCompanyWiseModulePrivilegesList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveCompanyModulePriviledgeDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveCompanyModulePriviledgeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return companyWisePrivilegesService.saveCompanyWiseModulePrivilegesList(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/asignCompanyPrivilege", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ModelAndView updateRole(@RequestParam("privileges") final List<Privilege> privileges, @RequestParam("companyId") final Integer companyId,
			final HttpServletRequest request) {
		LOGGER.error("Registering Role account with information: {}");

		Map<String, Object> model = new HashMap<String, Object>();
		String encodedString = null;
		try {
			
			HashMap<String, Privilege> privilegeHM	=	privilegesService.getPrivilegeHM();
			
			ArrayList<CompanyWisePrivileges> arraylist = new ArrayList<>();
			CompanyWisePrivileges	companyWisePrivileges	= null;
			Privilege				privilegeObj			= null;
			
			for (Privilege privilege : privileges) {
				//arraylist.add((privilegeRepository.findByName(privilege.getName())));
				companyWisePrivileges	= new CompanyWisePrivileges();
				privilegeObj	= privilegeHM.get(privilege.getName());	
				if(privilegeObj != null) {
					companyWisePrivileges.setPriviledgeId(privilegeObj.getId());
					companyWisePrivileges.setPriviledgeName(privilege.getName());
					companyWisePrivileges.setCompanyId(companyId);
					companyWisePrivileges.setPriviledgeType(PriviledgeType.PRIVILEDGE_TYPE_FEILD);
					arraylist.add(companyWisePrivileges);
				}
			}
			companyWisePrivilegesRepository.deleteFeildPrivileges(companyId);
			companyWisePrivilegesRepository.saveAll(arraylist);
			
			Base64.Encoder encoder = Base64.getEncoder();
			String normalString = "" + companyId;
					encodedString = encoder.encodeToString(normalString.getBytes(StandardCharsets.UTF_8));

			
			
			/*Role genre = roleService.findRole(role.getId());
				if(genre != null) {
					role.setCreatedBy(genre.getCreatedBy());
					role.setCreatedOn(genre.getCreatedOn());
				}
				if(!genre.getName().equalsIgnoreCase(role.getName())) {
					Role rolevalid = roleService.getRole(role.getName(), currentUser.getCompany_id());
					if(rolevalid != null) {
						model.put("alreadyRole", true);
						return new ModelAndView("redirect:/newRole.html", model);
					}
				}
				
				roleService.UpdateRoleAccount(role, arraylist, currentUser.getCompany_id(), currentUser.getEmail());*/

		} catch (Exception e) {
			e.printStackTrace();
			model.put("alreadyCompany", true);
			return new ModelAndView("redirect:/masterCompany/1.html", model);
		}

		model.put("saveRole", true);
		return new ModelAndView("redirect:/showMasterCompany.html?CID=" + encodedString + "", model);
	}
	
	@RequestMapping(value = "/getConfigurationModules", method = RequestMethod.POST, produces = "application/json")
	public HashMap<Object, Object> getConfigurationModules(@RequestBody(required = false) String requestBody) throws Exception {
	    try {
	    	ValueObject 		object 		= new ValueObject();
	    	object.put("Modules", PropertyFileConstants.getModulesListHM());
	        return object.getHtData();
	    } catch (Exception e) {
	        throw e;
	    }
	}

	@RequestMapping(value = "/saveModuleConfiguration", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveModuleConfiguration(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		boolean             savedDataToDB           = false;
		try {
			object 				= new ValueObject(allRequestParams);
			savedDataToDB = companyConfigurationService.loadConfigFileAndSaveToDB(object.getInt("moduleId"));
			if(savedDataToDB) {
				object.put("savedDataToDB", true);
			}
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
		
	}
	
	@RequestMapping(value = "/getCompanyConfigurationData", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getCompanyConfigurationData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		HashMap<String, Object>		configuration			= null;
		try {
			object 				= new ValueObject(allRequestParams);
			configuration = companyConfigurationService.getCompanyConfiguration(object.getInt("companyId"), object.getInt("moduleId"));
			object.put("configurationList", companyConfigurationService.getCompanyConfigurationList(object.getInt("companyId"), object.getInt("moduleId")));
			object.put("configuration", configuration);
			
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
		
	}
	
	@RequestMapping(value = "/updateCompanyConfigurationValue", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateCompanyConfigurationValue(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 					= null;
		try {
			object 		= new ValueObject(allRequestParams);
			CompanyService.updateCompanyConfigurationValue(object);
			object.put("Success", true);
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
		
	}
	
}

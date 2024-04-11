package org.fleetopgroup.web.controller;

import java.util.ArrayList;
import java.util.Base64;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dao.CompanyWisePrivilegesRepository;
import org.fleetopgroup.persistence.dao.PrivilegeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CompanyWisePrivileges;
import org.fleetopgroup.persistence.model.Privilege;
import org.fleetopgroup.persistence.model.Role;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.persistence.service.RoleDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyWisePrivilegesService;
import org.fleetopgroup.persistence.serviceImpl.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Satheesh kumar
 *
 */
@Controller
public class RolePrivilegesController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IRoleService roleService;

	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired	private CompanyWisePrivilegesRepository			companyWisePrivilegesRepository;
	@Autowired	private ICompanyService							companyService;
	@Autowired	private ICompanyWisePrivilegesService			companyWisePrivilegesService;

	public RolePrivilegesController() {
		super();
	}

	@RequestMapping(value = "/newRole", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView NewRole(final RoleDto RoleDto, final HttpServletRequest request) {
		Map<String, Object> 		model 		= 	new HashMap<String, Object>();
		List<Role> 					nameRole	=	null;
		List<UserProfile> 			countRole	=	null;
		
		try {
			CustomUserDetails currentUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			nameRole 	=roleService.findAllRolesOfCompany(currentUser.getCompany_id());
			countRole 	=roleService.findUserCount(currentUser.getCompany_id());
			
			
			model.put("Role", nameRole);
			model.put("Role1", countRole);
			//model.put("RoleCount", roleService.count());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("RoleNew", model);
	}


	@RequestMapping(value = "/addRole", method = RequestMethod.GET)
	public ModelAndView AddRole(final RoleDto RoleDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
			if (currentUser != null) {
				List<CompanyWisePrivileges>  companyFeildPrivileges	= null;
				List<String>                 feildPrivilege			= null;
				
				companyFeildPrivileges	= companyWisePrivilegesService.getCompanyWiseFeildPrivilegesList(currentUser.getCompany_id());
				
				feildPrivilege	= new ArrayList<>();
				for(CompanyWisePrivileges privileges : companyFeildPrivileges) {
					feildPrivilege.add(privileges.getPriviledgeName());
				}
				
				model.put("companyId", currentUser.getCompany_id());
				
				model.put("feildPrivilege", feildPrivilege);
				model.put("feildPrivileges", companyFeildPrivileges);
								

			}

			
		} catch (Exception e) {
			LOGGER.error("Branch add Page:", e);
		}
		return new ModelAndView("RoleAdd", model);
	}
	
	@RequestMapping(value = "/addRoleToCompany", method = RequestMethod.GET)
	public ModelAndView addRoleToCompany(@RequestParam("CID") final String Company_Encode, final RoleDto RoleDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(Company_Encode);
			Integer Company_id = Integer.parseInt(new String(decodedByteArray));
			model.put("companyId", Company_id);
			model.put("company", companyService.getCompanyByID(Company_id));
			List<String> privileges	= new ArrayList<>();
			List<CompanyWisePrivileges> 	companyWisePrivileges	= companyWisePrivilegesRepository.getCompanyWisePrivilegesList(Company_id);
			for (CompanyWisePrivileges artist : companyWisePrivileges) {
				// System.out.println(String.format("Genre Privilege: %s",
				// artist));
				privileges.add("'" + artist.getPriviledgeName() + "'");
				// System.out.println("'" + artist.getName() + "'");
			}
			model.put("privileges", privileges);
			model.put("companyId", Company_id);
			model.put("company_id_encode", Company_Encode);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("RoleAddToCompany", model);
	}

	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveRole(@RequestParam("name") final String name,
			@RequestParam("privileges") final List<Privilege> privileges, final HttpServletRequest request) {
		LOGGER.error("Registering Role account with information: {}");

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails currentUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// System.out.println(name);
			// List<Privilege> privileges2 ;

			ArrayList<Privilege> arraylist = new ArrayList<Privilege>();

			for (Privilege privilege : privileges) {
				arraylist.add((privilegeRepository.findByName(privilege.getName())));
				// System.out.println(arraylist);
			}
			roleService.registerNewRoleAccount(name, arraylist, currentUser.getCompany_id(), currentUser.getEmail());

		} catch (Exception e) {
			e.printStackTrace();
			model.put("alreadyRole", true);
			return new ModelAndView("redirect:/newRole.html", model);
		}

		model.put("saveRole", true);
		return new ModelAndView("redirect:/newRole.html", model);
	}

	@RequestMapping(value = "/editRole", method = RequestMethod.GET)
	public ModelAndView editRole(final Locale locale, @RequestParam("RID") final long Role_id) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			final List<String> privileges = new ArrayList<String>();
			CustomUserDetails	currentUser	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<CompanyWisePrivileges>  companyFeildPrivileges	= null;
			List<String>                 feildPrivilege			= null;
			
			companyFeildPrivileges	= companyWisePrivilegesService.getCompanyWiseFeildPrivilegesList(currentUser.getCompany_id());
			
			feildPrivilege	= new ArrayList<>();
			for(CompanyWisePrivileges privileges2 : companyFeildPrivileges) {
				feildPrivilege.add(privileges2.getPriviledgeName());
			}
			
			model.put("companyId", currentUser.getCompany_id());
			
			model.put("feildPrivilege", feildPrivilege);
			model.put("feildPrivileges", companyFeildPrivileges);
							

			
			Role genre = roleService.findRole(Role_id);
			// System.out.println(genre.getId());
			// System.out.println(genre.getName());
			model.put("role", genre);
			// System.out.println(String.format("Found Role: %s", genre));
			for (Privilege artist : genre.getPrivileges()) {
				// System.out.println(String.format("Genre Privilege: %s",
				// artist));
				privileges.add("'" + artist.getName() + "'");
				// System.out.println("'" + artist.getName() + "'");
			}
			model.put("privileges", privileges);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("RoleEdit", model);
	}

	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateRole(@RequestParam("privileges") final List<Privilege> privileges, Role role,
			final HttpServletRequest request) {
		LOGGER.error("Registering Role account with information: {}");

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails currentUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ArrayList<Privilege> arraylist = new ArrayList<Privilege>();

			for (Privilege privilege : privileges) {
				arraylist.add((privilegeRepository.findByName(privilege.getName())));
				 //System.out.println(privilege.getName());
				// System.out.println(arraylist);
			}
			Role genre = roleService.findRole(role.getId());
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
				
				roleService.UpdateRoleAccount(role, arraylist, currentUser.getCompany_id(), currentUser.getEmail());

		} catch (Exception e) {
			e.printStackTrace();
			model.put("alreadyRole", true);
			return new ModelAndView("redirect:/newRole.html", model);
		}

		model.put("saveRole", true);
		return new ModelAndView("redirect:/newRole.html", model);
	}
	
	@RequestMapping(value = "/viewRole", method = RequestMethod.GET)
	public ModelAndView viewRole(final Locale locale, @RequestParam("RID") final long Role_id) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			final List<String> privileges = new ArrayList<String>();
			
			CustomUserDetails	currentUser	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<CompanyWisePrivileges>  companyFeildPrivileges	= null;
			List<String>                 feildPrivilege			= null;
			
			companyFeildPrivileges	= companyWisePrivilegesService.getCompanyWiseFeildPrivilegesList(currentUser.getCompany_id());
			
			feildPrivilege	= new ArrayList<>();
			for(CompanyWisePrivileges privileges2 : companyFeildPrivileges) {
				feildPrivilege.add(privileges2.getPriviledgeName());
			}
			
			model.put("companyId", currentUser.getCompany_id());
			
			model.put("feildPrivilege", feildPrivilege);
			model.put("feildPrivileges", companyFeildPrivileges);

			Role genre = roleService.findRole(Role_id);
			// System.out.println(genre.getId());
			// System.out.println(genre.getName());
			model.put("role", genre);
			// System.out.println(String.format("Found Role: %s", genre));
			for (Privilege artist : genre.getPrivileges()) {
				// System.out.println(String.format("Genre Privilege: %s",
				// artist));
				privileges.add("'" + artist.getName() + "'");
				// System.out.println("'" + artist.getName() + "'");
			}
			model.put("privileges", privileges);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("RoleView", model);
	}

	@RequestMapping(value = "/deleteRole", method = RequestMethod.GET)
	public ModelAndView deleteRole(final Locale locale, @RequestParam("RID") final long Role_id) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			roleService.deleteRole(Role_id, userDetails.getCompany_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("deleteRole", true);
		return new ModelAndView("redirect:/newRole.html", model);
	}

	public Role prepareRoleDetails(Role role) {
		Role roleDto = new Role();
		roleDto.setName(role.getName());
		return roleDto;
	}
	
	

}
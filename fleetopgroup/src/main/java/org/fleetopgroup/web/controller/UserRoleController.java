/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.UserProfileBL;
import org.fleetopgroup.persistence.dao.PrivilegeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleGroupPermissionDto;
import org.fleetopgroup.persistence.model.Branch;
import org.fleetopgroup.persistence.model.CashBookPermission;
import org.fleetopgroup.persistence.model.Department;
import org.fleetopgroup.persistence.model.PartLocationPermission;
import org.fleetopgroup.persistence.model.Privilege;
import org.fleetopgroup.persistence.model.Role;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.persistence.model.UserProfileDocument;
import org.fleetopgroup.persistence.model.UserProfilePhoto;
import org.fleetopgroup.persistence.model.VehicleGroupPermision;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.service.RoleDto;
import org.fleetopgroup.persistence.service.UserDto;
import org.fleetopgroup.persistence.serviceImpl.IBranchService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookPermissionService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDepartmentService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IRoleService;
import org.fleetopgroup.persistence.serviceImpl.ITallyCompanyPermissionService;
import org.fleetopgroup.persistence.serviceImpl.ITallyIntegrationService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IUserService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.validation.EmailExistsException;
import org.fleetopgroup.web.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/**
 * @author Satheesh kumar
 *
 */
@Controller
public class UserRoleController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserService UserService;

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private IVendorService vendorService;

	/*
	 * @Autowired private MessageSource messages;
	 */
	@Autowired
	private IRoleService RoleService;

	@Autowired
	private IDepartmentService departmentService;

	@Autowired
	private IBranchService branchService;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ICashBookNameService cashBookNameService;

	@Autowired
	private IPartLocationsService PartLocationsService;
	
	@Autowired
	private ICashBookPermissionService cashBookPermissionService;

	@Autowired
	private IVehicleGroupPermissionService vehicleGroupPermissionService;

	@Autowired
	private IPartLocationPermissionService partLocationPermissionService;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;
	
	@Autowired private ITallyIntegrationService			tallyIntegrationService;
	@Autowired private ITallyCompanyPermissionService	tallyCompanyPermissionService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	UserProfileBL userProfileBL = new UserProfileBL();

	ByteImageConvert ByteImageConvert = new ByteImageConvert();

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	private IVehicleGroupService vehicleGroupService;

	public UserRoleController() {
		super();
	}

	// Show Vehicle Main Page

	@RequestMapping(value = "/newUserList/{pageNumber}", method = RequestMethod.GET)
	public String VehicleList(@PathVariable Integer pageNumber, Model model) throws Exception {

		Page<UserProfile> page = userProfileService.getDeployment_Page_UserProfile(pageNumber);
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("logedInUser", userDetails.getEmail().toLowerCase());
			

			model.addAttribute("UserCount", page.getTotalElements());

			List<UserProfileDto> pageList = userProfileService.list_UserProfile(pageNumber);
	        

			model.addAttribute("userprofile", pageList);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("UserNew Page:", e);
			e.printStackTrace();
		}
		return "UserNew";
	}

	// Show Vehicle Main Page
	@RequestMapping(value = "/searchUserList", method = RequestMethod.POST)
	public String VehicleList(@RequestParam("UserName") String SearchName, Model model) throws Exception {

		try {
			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			List<UserProfileDto> pageList = userProfileService.Search_list_UserProfile(SearchName,
					currentUser.getCompany_id());

			model.addAttribute("userprofile", pageList);
			model.addAttribute("logedInUser", currentUser.getEmail());

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("UserNew Page:", e);
			e.printStackTrace();
		}
		return "UserNew";
	}

	@RequestMapping(value = "/user/registrationFleetop", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse registerUserAccount(@Valid final UserDto accountDto, UserProfileDto userProfile,
			@RequestParam("Roles") final List<Role> role, final HttpServletRequest request)
			throws EmailExistsException {
		CustomUserDetails currentUser = null;
		User validate = null;
		System.err.println("enter here"+ accountDto);
		System.err.println("enter 2 "+ userProfile);
		System.err.println("enter role "+ role);
		try {
			currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validate 	= UserService.findByUserNameAndCompanyCode(accountDto.getEmail(),currentUser.getCompanyCode());
			
			if (validate == null) {
				
				final User registered = prepareModel(accountDto);
				userProfile.setCompany_id(currentUser.getCompany_id());
				final UserProfile saveUSERProfiel = prepareModelUserProfile(userProfile, accountDto);
				ArrayList<Role> arraylist = new ArrayList<Role>();
				for (Role Rolelist : role) {
					arraylist.add((RoleService.findByName(Rolelist.getName())));
				}

				registered.setRoles(arraylist);
				registered.setCompany_id(currentUser.getCompany_id());
				if (arraylist != null) {
						
					try {
						User user = UserService.registerNewUserAccount(registered);
						saveUSERProfiel.setUser_id(user.getId());
						saveUSERProfiel.setCompany_id(currentUser.getCompany_id());
						saveUSERProfiel.setRole_id(arraylist.get(0).getId());
						//saveUSERProfiel.setFirstName(currentUser.getFirstName()); 
						userProfileService.registerNewUserProfile(saveUSERProfiel);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} else {
				return new GenericResponse("already");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GenericResponse("success");

	}

	
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView AddRole(final RoleDto RoleDto, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	    = null;
		try {
			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(currentUser.getCompany_id(), PropertyFileConstants.USER_CONFIGURATION_CONFIG);
			
			model.put("configuration",configuration);
			model.put("Role", RoleService.findAllRolesOfCompany(currentUser.getCompany_id()));
			model.put("department", departmentService.SearchDepartmentLisrCompanyID(currentUser.getCompany_id()));
			model.put("Branch", branchService.SearchBranchLisrCompanyID(currentUser.getCompany_id()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("UserAdd", model);
	}

	


	
	@RequestMapping(value = "/editUserProfile", method = RequestMethod.GET)
	public ModelAndView editUserProfile(@RequestParam("id") final Long id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	    = null;

		try {
			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(currentUser.getCompany_id(), PropertyFileConstants.USER_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			/*
			 * model.put("userprofile",
			 * userProfileBL.GetUserProfileDtoDetails(cacheService.getUserProfileByUserId(id
			 * )));
			 */
			model.put("userprofile", (userProfileService.getUserProfileByUser_id(id)));
			model.put("department", departmentService.SearchDepartmentLisrCompanyID(currentUser.getCompany_id()));
			model.put("Role", RoleService.findAllRolesOfCompany(currentUser.getCompany_id()));
			model.put("Branch", branchService.SearchBranchLisrCompanyID(currentUser.getCompany_id()));
			String role = null;
			User genre = UserService.findUser(id);
			model.put("user", genre);

			for (Role artist : genre.getRoles()) {
				role = artist.getName();
			}

			model.put("rolesDB", role);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("editUserProfile", model);
	}

	@RequestMapping(value = "/updateUserProfile", method = RequestMethod.POST)
	public ModelAndView updateUserProfile(@Valid User registered, UserProfileDto UserProfileDto,
			@RequestParam("Roles") final List<Role> role, final HttpServletRequest request)
			throws EmailExistsException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			System.out.println("*** " + UserProfileDto);
		
			
			User updateUser = UserService.getUserByID(registered.getId());
			ArrayList<Role> arraylist = new ArrayList<Role>();
			for (Role Rolelist : role) {
				arraylist.add((RoleService.findByName(Rolelist.getName())));
			}

			updateUser.setRoles(arraylist);

			try {
				UserService.updateUserAccount(updateUser);

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newUserList/1.html", model);
			}
			UserProfileDto.setCompany_id(updateUser.getCompany_id());
			//UserProfileDto.setFirstName(updateUser.getFirstName()); 
			//UserProfileDto.setLastName(updateUser.getLastName());
			UserProfileDto.setRole_id(arraylist.get(0).getId());
			UserProfile saveUSERProfiel = prepareUpdateUserProfile(UserProfileDto);
			try {
				userProfileService.registerNewUserProfile(saveUSERProfiel);
				model.put("updateUserProfile", true);

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newUserList/1.html", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newUserList/1.html", model);
		}
		return new ModelAndView("redirect:/newUserList/1.html", model);
	}

	@RequestMapping(value = "/showUserProfile", method = RequestMethod.GET)
	public ModelAndView showUserProfile(@RequestParam("id") final Long id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		StringBuffer vehicleGroupId = new StringBuffer();
		StringBuffer vehicleGroupName = new StringBuffer();
		List<VehicleGroupPermissionDto> vehicleGroupPermissionList = null;
		HashMap<String, Object> 		userConfig	    = null;
		try {
			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			userConfig	= companyConfigurationService.getCompanyConfiguration(currentUser.getCompany_id(), PropertyFileConstants.USER_CONFIGURATION_CONFIG);
			
			model.put("userConfig",userConfig);
			
			model.put("userprofile", userProfileService.getUserProfileByUser_id(id));
			model.put("vehicleGroup", vehicleGroupService.findAllVehicleGroupByCompanyId(currentUser.getCompany_id()));
			model.put("tallyCompanyList", tallyIntegrationService.getTallyCompanyListForCompany(currentUser.getCompany_id()));
			model.put("cashBook", cashBookNameService.list_CashBookName(currentUser.getCompany_id()));
			model.put("PartLocations",
					PartLocationsService.listAllPartLocationsByCompanyId(currentUser.getCompany_id()));

			final List<String> role = new ArrayList<String>();

			User genre = UserService.findUser(id);
			model.put("user", genre);
			for (Role artist : genre.getRoles()) {
				role.add("" + artist.getName() + "");
			}
			model.put("roles", role);

			vehicleGroupPermissionList = vehicleGroupPermissionService.getVehicleGroupPrmissionByUserId(id,
					currentUser.getCompany_id());
			if (vehicleGroupPermissionList != null) {
				for (int i = 0; i < vehicleGroupPermissionList.size(); i++) {
					if (i < vehicleGroupPermissionList.size() - 1) {
						vehicleGroupId = vehicleGroupId
								.append(vehicleGroupPermissionList.get(i).getVehicleGroupId() + ",");
						vehicleGroupName = vehicleGroupName
								.append(vehicleGroupPermissionList.get(i).getvGroupName() + ",");
					} else {
						vehicleGroupId = vehicleGroupId.append(vehicleGroupPermissionList.get(i).getVehicleGroupId());
						vehicleGroupName = vehicleGroupName.append(vehicleGroupPermissionList.get(i).getvGroupName());

					}
				}
			}
			model.put("vehicleGroupId", vehicleGroupId);
			model.put("logedInUser", currentUser.getEmail());
			model.put("vehicleGroupName", vehicleGroupName);
			
			model.put("companyId", currentUser.getCompany_id());
			
			HashMap<String, Object> configuration	=	companyConfigurationService.getCompanyConfiguration(currentUser.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			model.put("vehicleGroupWisePermission", (boolean)configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION));
			model.put("cashBookWisePermission", (boolean)configuration.get(ICompanyConfigurationService.CASH_BOOK_WISE_PERMISSION));
			model.put("partLocationWisePermission", (boolean)configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION));
			model.put("isOTPValidationAtLogin", (boolean)configuration.get(ICompanyConfigurationService.IS_OTP_VALIDATION_AT_LOGIN));
			model.put("configuration", configuration);
			model.put("tallyCompanyWisePermission", (boolean)configuration.get("tallyCompanyWisePermission"));
			model.put("cashBookIds", cashBookPermissionService.getCashBookPermission(id, currentUser.getCompany_id()));

			model.put("tallyCompanyIds", tallyCompanyPermissionService.getTallyCompanyPermission(id, currentUser.getCompany_id()));

			model.put("partLocationIds",
					partLocationPermissionService.getPartLocationPermission(id, currentUser.getCompany_id()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showUserProfile", model);
	}

	@RequestMapping(value = "/PrintUserProfile", method = RequestMethod.GET)
	public ModelAndView showUserProfilePrint(@RequestParam("id") final Long id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in username

			model.put("company", userProfileService.findUserProfileByUser_email(name));

			/*
			 * model.put("userprofile",
			 * userProfileBL.GetUserProfileDtoDetails(cacheService.getUserProfileByUserId(id
			 * )));
			 */
			model.put("userprofile", (userProfileService.getUserProfileByUser_id(id)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showUserProfilePrint", model);
	}

//yogi task
	@RequestMapping(value = "/resetUserPassword", method = RequestMethod.GET)
	public ModelAndView resetUserPassword(final User user, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (user.getId() != null) {
				model.put("user", UserService.getUserByID(user.getId()));
				
			}

			// yogi code
			/*
			 * if(user.getPassword().equals("wasim")) { model.remove(key,va); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newUser.html", model);
		}
		return new ModelAndView("resetUserPassword", model);
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveRole(@RequestParam("name") final String name,
			@RequestParam("privileges") final List<Privilege> privileges, final HttpServletRequest request) {
		LOGGER.error("Registering Role account with information: {}");

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			

			ArrayList<Privilege> arraylist = new ArrayList<Privilege>();

			for (Privilege privilege : privileges) {
				arraylist.add((privilegeRepository.findByName(privilege.getName())));
			}

			RoleService.registerNewRoleAccount(name, arraylist, currentUser.getCompany_id(), currentUser.getEmail());

		} catch (Exception e) {
			e.printStackTrace();
			model.put("alreadyRole", true);
			return new ModelAndView("redirect:/newUser.html", model);
		}

		model.put("saveRole", true);
		return new ModelAndView("redirect:/newUser.html", model);
	}

	// this change password in user
	@RequestMapping(value = "/UserChangePassword", method = RequestMethod.GET)
	public ModelAndView UserChangePassword(final Locale locale) throws Exception {
		CustomUserDetails	currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> configuration	=	companyConfigurationService.getCompanyConfiguration(currentUser.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("configuration", configuration);
		return new ModelAndView("UserChangePassword", model);
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public ModelAndView editRole(final Locale locale, @RequestParam("RID") final long Role_id) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			RoleService.deleteRole(Role_id, userDetails.getCompany_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("deleteRole", true);
		return new ModelAndView("redirect:/newUser.html", model);
	}

	@RequestMapping(value = "/getDepartmentList", method = RequestMethod.GET)
	public void getDepartment(@RequestParam(value = "companyID", required = true) Integer companyID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Department> subJob = new ArrayList<Department>();
		for (Department add : departmentService.SearchDepartmentLisrCompanyID(companyID)) {

			Department depart = new Department();

			depart.setDepart_id(add.getDepart_id());
			depart.setDepart_name(add.getDepart_name());
			depart.setDepart_code(add.getDepart_code());

			subJob.add(depart);
		}

		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(subJob));
	}

	@RequestMapping(value = "/getBranchList", method = RequestMethod.GET)
	public void getBranch(@RequestParam(value = "companyID", required = true) Integer companyID,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Branch> subJob = new ArrayList<Branch>();
		for (Branch add : branchService.findAll(companyID)) {

			Branch branch = new Branch();

			branch.setBranch_id(add.getBranch_id());
			branch.setBranch_name(add.getBranch_name());
			branch.setBranch_city(add.getBranch_city());

			subJob.add(branch);
		}

		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(subJob));
	}

	/* UserPhoto Upload */

	/* save UserProfile Photo */
	@RequestMapping(value = "/uploadUserProfilePhoto", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveDriverPhoto(
			@ModelAttribute("command") org.fleetopgroup.persistence.document.UserProfilePhoto userProfilePhoto,
			UserProfile userProfile, @RequestParam("file") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (!file.isEmpty()) {

				try {
					byte[] bytes = file.getBytes();
					userProfilePhoto.setUserprofile_filename(file.getOriginalFilename());

					userProfilePhoto.setUserprofile_content(ByteImageConvert.scale(bytes, 250, 250));
					userProfilePhoto.setUserprofile_contentType(file.getContentType());
				} catch (IOException e) {

				}

				java.util.Date currentDate = new java.util.Date();
				Timestamp toDateUpdte = new java.sql.Timestamp(currentDate.getTime());

				userProfilePhoto.setUploaddate(toDateUpdte);

				org.fleetopgroup.persistence.document.UserProfilePhoto validate = userProfileService
						.findUserProfilePhotoByUserProfile_id(userProfilePhoto.getUserprofile_id());
				if (validate == null) {
					userProfileService.addUserProfilePhoto(userProfilePhoto);

					userProfileService.UpdateUserProfile_photo_id(userProfilePhoto.getUserprofile_id(),
							userProfilePhoto.get_id(), userDetails.getCompany_id());

					model.put("saveUserPhoto", true);
				} else {

					userProfilePhoto.set_id(validate.get_id());
					userProfileService.updateUserProfilePhoto(userProfilePhoto);
					model.put("updateUserPhoto", true);
				}

				return new ModelAndView("redirect:/showUserProfile?id=" + userProfile.getUser_id() + "", model);
			} else {
				// messages
				model.put("emptyDocument", true);
				return new ModelAndView("redirect:/showUserProfile?id=" + userProfile.getUser_id() + "", model);

			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/showUserProfile?id=" + userProfile.getUser_id() + "", model);

		}
	}

	/* show the image of the driver photo */
	@RequestMapping("/getUserProfileImage/{photoid}")
	public String getImage(@PathVariable("photoid") Long photoid, HttpServletResponse response) {
		try {
			if (photoid == null) {
				//response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				org.fleetopgroup.persistence.document.UserProfilePhoto file = userProfileService
						.getUserProfilePhoto(photoid);

				if (file != null) {
					if (file.getUserprofile_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getUserprofile_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getUserprofile_contentType());
						response.getOutputStream().write(file.getUserprofile_content());
						out.flush();
						out.close();

						/*
						 * response.setContentType( "image/jpeg, image/jpg, image/png, image/gif");
						 * response.getOutputStream().write(file. getDriver_content());
						 * response.getOutputStream().close();
						 */

					}
				}
			}
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}

	/* show the image of the driver photo */
	@RequestMapping("/ProfilePicture/{email}")
	public String ProfilePicture(@PathVariable("email") String email, HttpServletResponse response) {
		try {
			if (email == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				UserProfilePhoto file = userProfileService.show_user_profile_picture(email);

				if (file != null) {
					if (file.getUserprofile_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getUserprofile_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getUserprofile_contentType());
						response.getOutputStream().write(file.getUserprofile_content());
						out.flush();
						out.close();

						/*
						 * response.setContentType( "image/jpeg, image/jpg, image/png, image/gif");
						 * response.getOutputStream().write(file. getDriver_content());
						 * response.getOutputStream().close();
						 */

					}
				}
			}
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}

	/* User Document Show */

	@RequestMapping(value = "/showUserProfileDocument", method = RequestMethod.GET)
	public ModelAndView showUserProfileDocument(@RequestParam("id") final Long id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			model.put("userprofile", userProfileBL
					.GetUserProfileDtoDetails(userProfileService.getUserProfileByID(id, userDetails.getCompany_id())));

			model.put("userprofileDocument",
					userProfileService.listUserProfileDocument(id, userDetails.getCompany_id()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showUserProfileDocument", model);
	}

	@RequestMapping(value = "/uploadUserProfileDocument", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadUserProfileDocument(
			@ModelAttribute("command") org.fleetopgroup.persistence.document.UserProfileDocument userDocument,
			@RequestParam("file") MultipartFile file) {

		Map<String, Object> model = new HashMap<String, Object>();

		if (!file.isEmpty()) {
			try {
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				byte[] bytes = file.getBytes();
				userDocument.setUserprofile_filename(file.getOriginalFilename());
				userDocument.setUserprofile_content(bytes);
				userDocument.setUserprofile_contentType(file.getContentType());
				userDocument.setCompanyId(userDetails.getCompany_id());
			} catch (IOException e) {

			}

			java.util.Date currentDate = new java.util.Date();
			Timestamp toDateUpdte = new java.sql.Timestamp(currentDate.getTime());

			userDocument.setUploaddate(toDateUpdte);

			userProfileService.saveUserProfileDocument(userDocument);
			// this message alert of show method
			model.put("saveUserDocument", true);

			return new ModelAndView("redirect:/showUserProfileDocument.in?id=" + userDocument.getUserprofile_id() + "",
					model);
		} else {
			// messages
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/showUserProfileDocument.in?id=" + userDocument.getUserprofile_id() + "",
					model);

		}

	}

	/* Should be Download Driver Document */
	@RequestMapping("/download/UserDocument/{documentid}")
	public String download(@PathVariable("documentid") Long documentid, HttpServletResponse response) {
		try {
			if (documentid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.UserProfileDocument file = userProfileService
						.getUserProfileDocuemnt(documentid, userDetails.getCompany_id());
				if (file != null) {
					if (file.getUserprofile_content() != null) {
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getUserprofile_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getUserprofile_contentType());
						response.getOutputStream().write(file.getUserprofile_content());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (IOException e) {

		}
		return null;
	}

	/* Delete the Driver Document */
	@RequestMapping("/deleteUserDocument")
	public ModelAndView deleteUserDocument(@RequestParam("id") final Long id, UserProfileDocument DocumentID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (id != null) {
				// delete method
				try {
					CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
							.getAuthentication().getPrincipal();
					userProfileService.deleteUserProfileDocument(DocumentID.getDocumentid(),
							userDetails.getCompany_id());
				} catch (Exception e) {

					e.printStackTrace();
				}
				// this message alert of show method
				model.put("deleteUserDocument", true);
				return new ModelAndView("redirect:/showUserProfileDocument.in?id=" + id + "", model);
			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {

		}
		return new ModelAndView("redirect:/showUserProfileDocument.in?id=" + id + "", model);
	}

	// This /saveUserFuelVendor Controller is Add User To Vendor Details Create
	// Fuel entries Vendor Details things
	@RequestMapping(value = "/saveUserFuelVendor", method = RequestMethod.POST)
	public ModelAndView saveUserFuelVendor(@RequestParam("user_id") final Long user_id,
			@RequestParam("vendorId") final Integer vendorId, final HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (vendorId != null && 0 != vendorId) {
				// get Vendor id to Vendor name
				Vendor vendorname = vendorService.getVendor_Details_Fuel_entries(vendorId);

				userProfileService.Update_UserID_To_VendorLink_Details(vendorname.getVendorId(),
						vendorname.getVendorName(), user_id, userDetails.getCompany_id());

			} else {

				userProfileService.Update_UserID_To_VendorLink_Details(0, null, user_id, userDetails.getCompany_id());
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Vehicle Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/showUserProfile?id=" + user_id + "", model);
		}

		// return new ModelAndView("Show_Vehicle_Fuel", model);
		return new ModelAndView("redirect:/showUserProfile?id=" + user_id + "", model);
	}

	// This /saveUserFuelVendor Controller is Add User To Vendor Details Create
	// Fuel entries Vendor Details things
	@RequestMapping(value = "/saveUserVehicleGroup", method = RequestMethod.POST)
	public ModelAndView saveUserVehicleGroup(@RequestParam("user_id") final Long user_id,
			@RequestParam("group_Permissions") final String group_Permissions, final HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (group_Permissions != null && user_id != null) {
				vehicleGroupPermissionService.deleteVehicleGroupPrmissionByUserId(user_id, userDetails.getCompany_id());
				for (int i = 0; i < group_Permissions.split(",").length; i++) {
					VehicleGroupPermision vehicleGroup = new VehicleGroupPermision();
					vehicleGroup.setVg_per_id(Long.parseLong(group_Permissions.split(",")[i]));
					vehicleGroup.setUser_id(user_id);
					vehicleGroup.setCompanyId(userDetails.getCompany_id());
					vehicleGroupPermissionService.registerVehicleGroupPrmissionByUserId(vehicleGroup);
				}
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Vehicle Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/showUserProfile?id=" + user_id + "", model);
		}

		// return new ModelAndView("Show_Vehicle_Fuel", model);
		return new ModelAndView("redirect:/showUserProfile?id=" + user_id + "", model);
	}

	@RequestMapping(value = "/saveUserCashBookPermossion", method = RequestMethod.POST)
	public ModelAndView saveUserCashBookPermossion(@RequestParam("user_id") final Long user_id,
			@RequestParam("cashBookPermission") final String cashBookPermission, final HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (cashBookPermission != null && user_id != null) {
				cashBookPermissionService.deleteCashBookPermissionByUserId(user_id, userDetails.getCompany_id());
				for (int i = 0; i < cashBookPermission.split(",").length; i++) {
					CashBookPermission permission = new CashBookPermission();
					permission.setCashBookId(Long.parseLong(cashBookPermission.split(",")[i]));
					permission.setUser_Id(user_id);
					permission.setCompanyId(userDetails.getCompany_id());
					cashBookPermissionService.registerCashBookPrmissionByUserId(permission);
				}
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Vehicle Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/showUserProfile?id=" + user_id + "", model);
		} finally {
			userDetails = null;
		}

		// return new ModelAndView("Show_Vehicle_Fuel", model);
		return new ModelAndView("redirect:/showUserProfile?id=" + user_id + "", model);
	}

	// Note: this Controller save Part Location Permission
	@RequestMapping(value = "/saveUserPartLocationPermission", method = RequestMethod.POST)
	public ModelAndView saveUserPartLocationPermission(@RequestParam("user_id") final Long user_id,
			@RequestParam("partLocationPermission") final String partLocationPermission,
			final HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (partLocationPermission != null && user_id != null) {

				partLocationPermissionService.deletePartLocationPermissionByUserId(user_id,
						userDetails.getCompany_id());

				for (int i = 0; i < partLocationPermission.split(",").length; i++) {
					PartLocationPermission permission = new PartLocationPermission();
					permission.setPartLocationId(Long.parseLong(partLocationPermission.split(",")[i]));
					permission.setUser_Id(user_id);
					permission.setCompanyId(userDetails.getCompany_id());
					partLocationPermissionService.registerPartLocationPermissionByUserId(permission);
				}
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Vehicle Page:", e);
			e.printStackTrace();
			return new ModelAndView("redirect:/showUserProfile?id=" + user_id + "", model);
		} finally {
			userDetails = null;
		}
		model.put("linkSuccess", true);
		return new ModelAndView("redirect:/showUserProfile?id=" + user_id + "", model);
	}

	/* Get User email id to Show Details in page */
	@RequestMapping(value = "/UserProfileName", method = RequestMethod.GET)
	public void UserProfileName(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String eamil = userDetails.getEmail();
		UserProfileDto userDB = userProfileService.findUserProfileByUser_email(eamil);

		UserProfileDto user = new UserProfileDto();
		if (userDB != null) {
			user.setFirstName(userDB.getFirstName());
			user.setLastName(userDB.getLastName());
			user.setDesignation(userDB.getDesignation());
			user.setPhoto_id(userDB.getPhoto_id());
			user.setDepartment_name(userDB.getDepartment_name());
			user.setBranch_name(userDB.getBranch_name());
		} else {
			user.setFirstName("Fleetop");
			user.setLastName(".in");
			user.setDesignation("fleetop");
			user.setPhoto_id((long) 0);
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(user));
	}

	@RequestMapping(value = "/UserProfile", method = RequestMethod.GET)
	public ModelAndView UserProfile(final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String eamil = auth.getName();
			UserProfileDto userDB = userProfileService.findUserProfileByUser_email(eamil);

			model.put("userprofile", userDB);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("mainUserProfile", model);
	}

	// save the VehicleStatus Model
	public User prepareModel(UserDto userBean) {
		User user = new User();
		
		user.setFirstName(userBean.getFirstName());
		user.setLastName(userBean.getLastName()); 
		user.setPassword(passwordEncoder.encode(userBean.getPassword()));
		user.setEmail(userBean.getEmail());
		user.setEnabled(true);

		return user;
	}

	// save the UserProfile Model
	public UserProfile prepareModelUserProfile(UserProfileDto userBean, UserDto user) {

		UserProfile userProfile = new UserProfile();
		
		if (userBean.getCompany_id() != null) {
			userProfile.setCompany_id(userBean.getCompany_id());
		}

		if (userBean.getDepartment_id() != null) {
			userProfile.setDepartment_id(userBean.getDepartment_id());
		}

		if (userBean.getBranch_id() != null) {
			userProfile.setBranch_id(userBean.getBranch_id());
		}

		userProfile.setDesignation(userBean.getDesignation());

		userProfile.setSex(userBean.getSex());
		userProfile.setPersonal_email(userBean.getPersonal_email());
		userProfile.setHome_number(userBean.getHome_number());
		userProfile.setMobile_number(userBean.getMobile_number());
		userProfile.setWork_number(userBean.getWork_number());
		userProfile.setAddress_line1(userBean.getAddress_line1());
		userProfile.setCountry(userBean.getCountry());
		userProfile.setState(userBean.getState());
		userProfile.setCity(userBean.getCity());
		userProfile.setPincode(userBean.getPincode());
		
		userProfile.setEmergency_person(userBean.getEmergency_person());
		userProfile.setEmergency_number(userBean.getEmergency_number());

		userProfile.setEmployes_id(userBean.getEmployes_id());
		userProfile.setWorking_time_from(userBean.getWorking_time_from());
		userProfile.setWorking_time_to(userBean.getWorking_time_to());
		userProfile.setEsi_number(userBean.getEsi_number());
		userProfile.setPf_number(userBean.getPf_number());
		userProfile.setInsurance_number(userBean.getInsurance_number());
		userProfile.setSubscribe(userBean.getSubscribe());
		userProfile.setFirstName(userBean.getFirstName());
		userProfile.setLastName(userBean.getLastName());
		
		if(userBean.getRfidCardNo() != null) {
			userProfile.setRfidCardNo(userBean.getRfidCardNo());
		}
		//userProfile.setRole_id(userBean.getRole_id());
		
		if (userBean.getDateofbirth() != null) {
			try {
				java.util.Date dateTo = dateFormat.parse(userBean.getDateofbirth());
				java.sql.Date dateofbirth = new Date(dateTo.getTime());
				userProfile.setDateofbirth(dateofbirth);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// This Vendor_Id Link To User Profile
		userProfile.setVendorId(0);
		// userProfile.setVendorName(null);

		/** who Create this vehicle get email id to user profile details */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String CreatedBy = auth.getName();

		userProfile.setCreatedBy(CreatedBy);
		userProfile.setLastModifiedBy(CreatedBy);

		java.util.Date currentDate = new java.util.Date();
		Timestamp toDateUpdte = new java.sql.Timestamp(currentDate.getTime());

		userProfile.setCreated(toDateUpdte);
		userProfile.setLastupdated(toDateUpdte);

		userProfile.setMarkForDelete(false);
		// userProfile.setVehicleGroupPermission(userBean.getGroup_Permissions());

		return userProfile;
	}

	// save the UserProfile Model
	public UserProfile prepareUpdateUserProfile(UserProfileDto userBean) {

		UserProfile userProfile = new UserProfile();

		userProfile.setUserprofile_id(userBean.getUserprofile_id());
		userProfile.setUser_id(userBean.getUser_id());
		userProfile.setRole_id(userBean.getRole_id());
		 userProfile.setFirstName(userBean.getFirstName());
		 userProfile.setLastName(userBean.getLastName());
		// userProfile.setUser_email(userBean.getUser_email());
		if (userBean.getCompany_id() != null) {
			userProfile.setCompany_id(userBean.getCompany_id());
		}

		if (userBean.getDepartment_id() != null) {
			userProfile.setDepartment_id(userBean.getDepartment_id());
		}

		if (userBean.getBranch_id() != null) {
			userProfile.setBranch_id(userBean.getBranch_id());
		}

		userProfile.setDesignation(userBean.getDesignation());

		userProfile.setSex(userBean.getSex());
		userProfile.setPersonal_email(userBean.getPersonal_email());
		userProfile.setHome_number(userBean.getHome_number());
		userProfile.setMobile_number(userBean.getMobile_number());
		userProfile.setWork_number(userBean.getWork_number());
		userProfile.setAddress_line1(userBean.getAddress_line1());
		userProfile.setCountry(userBean.getCountry());
		userProfile.setState(userBean.getState());
		userProfile.setCity(userBean.getCity());
		userProfile.setPincode(userBean.getPincode());

		userProfile.setEmergency_person(userBean.getEmergency_person());
		userProfile.setEmergency_number(userBean.getEmergency_number());

		userProfile.setEmployes_id(userBean.getEmployes_id());
		userProfile.setWorking_time_from(userBean.getWorking_time_from());
		userProfile.setWorking_time_to(userBean.getWorking_time_to());
		userProfile.setEsi_number(userBean.getEsi_number());
		userProfile.setPf_number(userBean.getPf_number());
		userProfile.setInsurance_number(userBean.getInsurance_number());
		
		if(userBean.getRfidCardNo() != null) {
			userProfile.setRfidCardNo(userBean.getRfidCardNo());
		}

		userProfile.setSubscribe(userBean.getSubscribe());

		if (userBean.getDateofbirth() != null) {
			try {
				java.util.Date dateTo = dateFormat.parse(userBean.getDateofbirth());
				java.sql.Date dateofbirth = new Date(dateTo.getTime());
				userProfile.setDateofbirth(dateofbirth);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// This Vendor_Id Link To User Profile
		userProfile.setVendorId(userBean.getVendorId());
		// userProfile.setVendorName(userBean.getVendorName());

		/** who Create this vehicle get email id to user profile details */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String CreatedBy = auth.getName();

		userProfile.setCreatedBy(CreatedBy);
		userProfile.setLastModifiedBy(CreatedBy);

		java.util.Date currentDate = new java.util.Date();
		Timestamp toDateUpdte = new java.sql.Timestamp(currentDate.getTime());

		userProfile.setLastupdated(toDateUpdte);
		userProfile.setMarkForDelete(userBean.isMarkForDelete());
		userProfile.setPhoto_id(userBean.getPhoto_id());
		// userProfile.setVehicleGroupPermission(userBean.getGroup_Permissions());

		return userProfile;
	}

	/*
	 * private boolean emailExist(final String email) { final User user =
	 * UserService.findByEmail(email); if (user != null) { return true; } return
	 * false; }
	 */
	
	@PostMapping(path = "/getBranchWiseUserList")
	public void getBranchWiseUserList( @RequestParam("term") final String term,@RequestParam("branchId") final Long branchId, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserProfileDto> userName = userProfileService.getBranchWiseUserList(term, userDetails.getCompany_id(),branchId);
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(userName));
	}
}
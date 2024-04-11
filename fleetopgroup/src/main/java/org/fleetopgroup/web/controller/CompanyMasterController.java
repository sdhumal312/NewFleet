package org.fleetopgroup.web.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.fleetopgroup.persistence.bl.BranchBL;
import org.fleetopgroup.persistence.bl.CompanyBL;
import org.fleetopgroup.persistence.bl.DepartmentBL;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverJobTypeBL;
import org.fleetopgroup.persistence.bl.UserProfileBL;
import org.fleetopgroup.persistence.dao.PrivilegeRepository;
import org.fleetopgroup.persistence.dto.BranchDto;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DepartmentDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.Branch;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.model.CompanyWisePrivileges;
import org.fleetopgroup.persistence.model.Department;
import org.fleetopgroup.persistence.model.DriverDocType;
import org.fleetopgroup.persistence.model.DriverJobType;
import org.fleetopgroup.persistence.model.Privilege;
import org.fleetopgroup.persistence.model.Role;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.persistence.service.UserDto;
import org.fleetopgroup.persistence.serviceImpl.IBranchService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyWisePrivilegesService;
import org.fleetopgroup.persistence.serviceImpl.IDepartmentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocTypeService;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IRoleService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IUserService;
import org.fleetopgroup.validation.EmailExistsException;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Satheesh kumar
 *
 */
@Controller
@PropertySource({ "classpath:email.properties" })
public class CompanyMasterController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ICompanyService CompanyService;

	@Autowired
	private IBranchService branchService;
	BranchBL bbl = new BranchBL();

	@Autowired	ISequenceService				sequenceService;
	@Autowired	IDriverJobTypeService			driverJobTypeService;
	@Autowired	IDriverDocTypeService			driverDocTypeService;
	@Autowired	ICompanyWisePrivilegesService	companyWisePrivilegesService;
	

	@Autowired
	private IDepartmentService DepartmentService;
	DepartmentBL DepartBL = new DepartmentBL();

	@Autowired
	private IRoleService roleService;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private IUserService UserService;

	@Autowired
	private IUserProfileService userProfileService;
	UserProfileBL UPBL = new UserProfileBL();

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messages;
	
	@Autowired JavaMailSender				javaMailSender;
	
	@Autowired private Environment env;


	
	CompanyBL DriDT = new CompanyBL();

	DriverJobTypeBL DriJOb = new DriverJobTypeBL();

	DriverBL DBL = new DriverBL();

	// This Controller is view All Company Details
	@RequestMapping(value = "/masterCompany/{pageNumber}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_COM_VIEW_PRIVILEGE')")
	public String masterCompany(@PathVariable("pageNumber") Integer pageNumber, Model model,
			HttpServletRequest request) {

		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
			if (currentUser != null) {
				long companyCount = 0;
				try {
					Page<Company> page = CompanyService.getDeployment_Find_Page_MasterCompany_Details(pageNumber);
					if (page != null) {
						int current = page.getNumber() + 1;
						int begin = Math.max(1, current - 5);
						int end = Math.min(begin + 10, page.getTotalPages());

						model.addAttribute("deploymentLog", page);
						model.addAttribute("beginIndex", begin);
						model.addAttribute("endIndex", end);
						model.addAttribute("currentIndex", current);

						companyCount = page.getTotalElements();

						List<CompanyDto> companyList = CompanyService.Find_MasterCompany_Details(pageNumber);
						model.addAttribute("company", companyList);

					}
					model.addAttribute("CompanyCount", companyCount);

				} catch (NullPointerException e) {
					LOGGER.error("Company NullPointerException:", e);
					return "redirect:/NotFound.in";
				} catch (Exception e) {
					LOGGER.error("Company Page:", e);
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			LOGGER.error("Company Master Page:", e);
		}
		return "newMasterCompany";
	}

	@RequestMapping(value = "/showMasterCompany", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_COM_VIEW_PRIVILEGE')")
	public ModelAndView showMasterCompany(@RequestParam("CID") final String Company_Encode,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
			if (currentUser != null) {

				Base64.Decoder decoder = Base64.getDecoder();
				byte[] decodedByteArray = decoder.decode(Company_Encode);
				Integer Company_id = Integer.parseInt(new String(decodedByteArray));
				model.put("Company", DriDT.ShowCompanyID_Encode(CompanyService.getCompanyByID(Company_id)));

				model.put("branch", branchService.SearchBranchLisrCompanyID(Company_id));

				model.put("department",
						DepartBL.DriDocTypeListofDto(DepartmentService.SearchDepartmentLisrCompanyID(Company_id)));

				model.put("Role", roleService.findAllRolesOfCompany(Company_id));
				
				
				List<UserProfileDto> pageList = userProfileService.list_UserProfile_Master_User(Company_id);
				
				model.put("modulePrivileges", companyWisePrivilegesService.getCompanyWiseModulePrivilegesList(Company_id));
				model.put("feildPrivileges", companyWisePrivilegesService.getCompanyWiseFeildPrivilegesList(Company_id));
				
				model.put("userprofile", pageList);
			}

		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("showMasterCompany", model);
	}

	@RequestMapping(value = "/masterCompanyCreate", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public ModelAndView masterCompanyCreate(@ModelAttribute("command") CompanyDto CompanyDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			String name = auth.getName(); // get logged in username

			model.put("createdBy", name);
		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("addMasterCompany", model);
	}

	// save Main Company
	@RequestMapping(value = "/savemasterCompany")
	public String singleUpload() {
		return "saveMasterCompany";
	}

	@RequestMapping(value = "/saveMasterCompany", method = RequestMethod.POST)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public @ResponseBody ModelAndView saveCompany(@ModelAttribute("command") CompanyDto CompanyDto,
			@RequestParam("fileUpload") MultipartFile file, org.fleetopgroup.persistence.document.Companylogo logo, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		String encodedString_CompanyID = null;
		try {
			Company GET_Type = DriDT.prepareCompanyModel(CompanyDto);
			Company validate = CompanyService.validateCompanyName(CompanyDto.getCompany_name());
			if (validate == null) {
				Company companyDB = CompanyService.registerNewCompany(GET_Type);
				Create_Company_Auto_ID_Sequence_Counter(companyDB.getCompany_id());
				addCommonMasterTableEntries(companyDB.getCompany_id());
				model.put("saveCompany", true);

				if (!file.isEmpty()) {
					try {
						byte[] bytes = file.getBytes();
						logo.setFilename(file.getOriginalFilename());
						logo.setLog_content(bytes);
						logo.setLog_contentType(file.getContentType());
						logo.setCompany_id(companyDB.getCompany_id());

						CompanyService.registerNewCompanylogo(logo);

					} catch (Exception e) {
						e.printStackTrace();
						// messages
						model.put("alreadyPhoto", true);
						LOGGER.error("Company Create Page:", e);
						return new ModelAndView("redirect:/masterCompany/1.html", model);
					}
				} else {
					// messages
					model.put("emptyPhoto", true);
					return new ModelAndView("redirect:/masterCompany/1.html", model);

				}
				if (companyDB.getCompany_id() > 0) {
					
					String email = "--";
					String mobileNo = "--";
					String city = "--";
					
					if(companyDB.getCompany_email() != null && !companyDB.getCompany_email().isEmpty()) {
						email = companyDB.getCompany_email();					
					}
					if(companyDB.getCompany_mobilenumber() != null && !companyDB.getCompany_mobilenumber().isEmpty()) {
						mobileNo = companyDB.getCompany_mobilenumber();					
					}
					if(companyDB.getCompany_city() != null && companyDB.getCompany_city() != "") {
						city = companyDB.getCompany_city();					
					}
					
					final String sendHTML_Email_PO = "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
					+"<head>"
					+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
					+"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
					+"</head>"
					+"<body>"
					+"<div class=\"block\">"
					+"<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;border-collapse: collapse;width: 100%;\" st-sortable=\"preheader\">"
					+"<tr>"
					+"<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;\">Company Name</th>"
					+"<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;\">Company Code</th>"
					+"<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;\">Company Type</th>"
					+"<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;\">Company Email</th>"
					+"<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;\">Company Mobile No.</th>"
					+"<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;\">Company City</th>"
					+"<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;border: 1px solid #ddd;padding: 8px;\">Created By</th>"
					+"</tr>"
					+"<tr>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+companyDB.getCompany_name()+"</td>"
							+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+companyDB.getCompanyCode()+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+companyDB.getCompany_type()+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+email+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+mobileNo+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+city+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+companyDB.getCreatedBy()+"</td>"
					+"</tr>"
					+"</table>"
					+"</div>"
					+"</body>"
					+"</html>";

					javaMailSender.send(new MimeMessagePreparator() {
						@Override
						public void prepare(MimeMessage mimeMessage) throws Exception {
							MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
							messageHelper.setTo(env.getProperty("managers.email"));
							messageHelper.setSubject("New Company Registered");
							messageHelper.setReplyTo(env.getProperty("managers.email"));
							messageHelper.setBcc(env.getProperty("developer.email"));

							/* sent html email text */
							messageHelper.setText(sendHTML_Email_PO, true);
						}
					});
				}
				

				Base64.Encoder encoder = Base64.getEncoder();
				String normalString = "" + companyDB.getCompany_id();
				encodedString_CompanyID = encoder.encodeToString(normalString.getBytes(StandardCharsets.UTF_8));

			} else {
				model.put("alreadyCompany", true);
				return new ModelAndView("redirect:/masterCompany/1.html", model);
			}

		} catch (Exception e) {
			System.err.println("Exception "+e);
			model.put("alreadyCompany", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/masterCompany/1.html", model);
		}
		return new ModelAndView("redirect:/showMasterCompany.html?CID=" + encodedString_CompanyID + "", model);
	}

	private void Create_Company_Auto_ID_Sequence_Counter(Integer company_id) {
		try {
			// Create All Sequence Details
			sequenceService.insertSequenceTableEntries(company_id);
		} catch (Exception e) {
			LOGGER.error("Company Create Page:", e);
			e.printStackTrace();
		}
	}
	
	private void addCommonMasterTableEntries(Integer company_id) {
		try {
			driverJobTypeService.addDriverJobType(getDriverJobType("DRIVER", company_id));
			driverJobTypeService.addDriverJobType(getDriverJobType("CONDUCTOR", company_id));
			driverJobTypeService.addDriverJobType(getDriverJobType("CLEANER", company_id));
			driverJobTypeService.addDriverJobType(getDriverJobType("MECHANIC", company_id));
			
			driverDocTypeService.registerNewDriverDocType(getDriverDocType("DL", company_id));
			driverDocTypeService.registerNewDriverDocType(getDriverDocType("BADGE", company_id));
			
		} catch (Exception e) {
			LOGGER.error("Company Create Page:", e);
			e.printStackTrace();
		}
	}
	
	private DriverJobType getDriverJobType(String job, Integer companyId) {
		DriverJobType	driverJobType = new DriverJobType();
			driverJobType.setCompanyId(companyId);
			driverJobType.setDriJobType(job);
			driverJobType.setDriJobRemarks("FIXED");
			driverJobType.setDriJob_deleteable(false);
			driverJobType.setDriJob_editable(false);
			driverJobType.setCreatedById((long) 0);
			driverJobType.setLastModifiedById((long) 0);
			driverJobType.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			driverJobType.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
		return driverJobType;
		
	}
	
	private DriverDocType getDriverDocType(String job, Integer companyId) {
		DriverDocType	docType = new DriverDocType();
		
		docType.setCompany_Id(companyId);
		docType.setDri_DocType(job);
		docType.setCreatedById((long) 0);
		docType.setLastModifiedById((long) 0);
		docType.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
		docType.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
		
		return docType;
	}

	// This Controller Edit Master Company Details Only Sales Man
	@RequestMapping(value = "/editMasterCompany", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_COM_EDIT_PRIVILEGE')")
	public ModelAndView editMasterCompany(@RequestParam("CID") final String subID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in username

			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(subID);
			Integer Id = Integer.parseInt(new String(decodedByteArray));

			model.put("lastModifiedBy", name);
			model.put("Company", DriDT.prepareCompanyDto(CompanyService.getCompanyByID(Id)));

		} catch (Exception e) {
			LOGGER.error("Company Edit Page:", e);
		}

		return new ModelAndView("editMasterCompany", model);
	}

	@RequestMapping(value = "/updateMasterCompany", method = RequestMethod.POST)
	@PreAuthorize("hasRole('MASTER_COM_EDIT_PRIVILEGE')")
	public @ResponseBody ModelAndView updateCompany(@ModelAttribute("command") CompanyDto CompanyDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Company GET_Type = DriDT.prepareCompanyModel_Update(CompanyDto);
			if (GET_Type != null) {
				CompanyService.registerNewCompany(GET_Type);
				model.put("updateCompany", true);

			} else {
				model.put("alreadyCompany", true);
				return new ModelAndView("redirect:/masterCompany/1.html", model);
			}

		} catch (Exception e) {
			model.put("alreadyCompany", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/masterCompany/1.html", model);
		}

		return new ModelAndView("redirect:/showMasterCompany.html?CID=" + CompanyDto.getCompany_id_encode() + "",
				model);

	}

	// add Master Branch
	@RequestMapping(value = "/addMasterBranch", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public ModelAndView addBranch(@RequestParam("CID") final String Company_Encode, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
			if (currentUser != null) {
				
				Base64.Decoder decoder = Base64.getDecoder();
				byte[] decodedByteArray = decoder.decode(Company_Encode);
				Integer Company_id = Integer.parseInt(new String(decodedByteArray));
				model.put("Company", DriDT.ShowCompanyID_Encode(CompanyService.getCompanyByID(Company_id)));

			}

		} catch (Exception e) {
			LOGGER.error("Branch add Page:", e);
		}
		return new ModelAndView("addMasterBranch", model);
	}

	@RequestMapping(value = "/saveMasterBranch", method = RequestMethod.POST)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public @ResponseBody ModelAndView saveMasterBranch(@ModelAttribute("command") BranchDto branchDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Branch GET_branch = bbl.prepareMaster_Company_BranchModel_add(branchDto);

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			GET_branch.setCreatedById(userDetails.getId());
			GET_branch.setLastModifiedById(userDetails.getId());

			List<Branch> validate = branchService.validateBranchName(GET_branch.getBranch_name(), GET_branch.getCompany_id());
			if (validate == null || validate.isEmpty()) {

				branchService.registerNewBranch(GET_branch);
				model.put("saveBranch", true);
			} else {
				model.put("alreadyBranch", true);
				return new ModelAndView("redirect:/masterCompany/1.html", model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("alreadyBranch", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/masterCompany/1.html", model);
		}
		return new ModelAndView("redirect:/showMasterCompany.html?CID=" + branchDto.getCompany_id_encode() + "", model);
	}

	// add Master Department
	@RequestMapping(value = "/addMasterDepartment", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public ModelAndView addMasterDepartment(@RequestParam("CID") final String Company_Encode,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
			if (currentUser != null) {

				Base64.Decoder decoder = Base64.getDecoder();
				byte[] decodedByteArray = decoder.decode(Company_Encode);
				Integer Company_id = Integer.parseInt(new String(decodedByteArray));
				model.put("Company", DriDT.ShowCompanyID_Encode(CompanyService.getCompanyByID(Company_id)));

			}

		} catch (Exception e) {
			LOGGER.error("Branch add Page:", e);
		}
		return new ModelAndView("addMasterDepartment", model);
	}

	@RequestMapping(value = "/saveMasterDepartment", method = RequestMethod.POST)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public @ResponseBody ModelAndView saveMasterDepartment(@ModelAttribute("command") DepartmentDto DepartmentDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Department GET_Type = DepartBL.prepareMaster_Company_DepatmentModel_add(DepartmentDto);

			List<Department> validate = DepartmentService.validateDepartmentName(GET_Type.getDepart_name(),
					GET_Type.getCompany_id());
			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (validate == null || validate.isEmpty()) {
				try {
					GET_Type.setCreatedBy(currentUser.getEmail());
					GET_Type.setCreatedOn(new Timestamp(System.currentTimeMillis()));
					DepartmentService.registerNewDepartment(GET_Type);
					model.put("saveDepartment", true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				model.put("alreadyDepartment", true);
				return new ModelAndView("redirect:/masterCompany/1.html", model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("alreadyBranch", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/masterCompany/1.html", model);
		}
		return new ModelAndView("redirect:/showMasterCompany.html?CID=" + DepartmentDto.getCompany_id_encode() + "",
				model);
	}

	// add Master Role
	@RequestMapping(value = "/addMasterRole", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public ModelAndView addMasterRole(@RequestParam("CID") final String Company_Encode,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
			if (currentUser != null) {
				List<CompanyWisePrivileges>  companyFeildPrivileges	= null;
				List<String>                 feildPrivilege			= null;
				Base64.Decoder decoder = Base64.getDecoder();
				byte[] decodedByteArray = decoder.decode(Company_Encode);
				Integer Company_id = Integer.parseInt(new String(decodedByteArray));
				model.put("Company", DriDT.ShowCompanyID_Encode(CompanyService.getCompanyByID(Company_id)));
				
				companyFeildPrivileges	= companyWisePrivilegesService.getCompanyWiseFeildPrivilegesList(Company_id);
				
				feildPrivilege	= new ArrayList<>();
				for(CompanyWisePrivileges privileges : companyFeildPrivileges) {
					feildPrivilege.add(privileges.getPriviledgeName());
				}
				
				model.put("companyId", Company_id);
				
				model.put("feildPrivilege", feildPrivilege);
				model.put("feildPrivileges", companyFeildPrivileges);
								

			}

			
		} catch (Exception e) {
			LOGGER.error("Branch add Page:", e);
		}
		return new ModelAndView("addMasterRole", model);
	}

	@RequestMapping(value = "/saveMasterRole", method = RequestMethod.POST)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public @ResponseBody ModelAndView saveMasterRole(@RequestParam("companyId") final Integer companyId,
			@RequestParam("name") final String name, @RequestParam("privileges") final List<Privilege> privileges,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CompanyDto	company	= null;
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
			if (currentUser != null) {

				ArrayList<Privilege> arraylist = new ArrayList<Privilege>();

				for (Privilege privilege : privileges) {
					arraylist.add((privilegeRepository.findByName(privilege.getName())));
				}
				roleService.registerNewRoleAccount(name, arraylist, companyId, currentUser.getEmail());

			}
			
			company	=	DriDT.ShowCompanyID_Encode(CompanyService.getCompanyByID(companyId));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("alreadyBranch", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/masterCompany/1.html", model);
		}
		return new ModelAndView("redirect:/showMasterCompany.html?CID=" + company.getCompany_id_encode() + "", model);
	}

	// add Master User
	@RequestMapping(value = "/addMasterUser", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public ModelAndView addMasterUser(@RequestParam("CID") final String Company_Encode,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
			if (currentUser != null) {

				Base64.Decoder decoder = Base64.getDecoder();
				byte[] decodedByteArray = decoder.decode(Company_Encode);
				Integer Company_id = Integer.parseInt(new String(decodedByteArray));
				model.put("Company", DriDT.ShowCompanyID_Encode(CompanyService.getCompanyByID(Company_id)));

				model.put("Role", roleService.findAllRolesOfCompany(Company_id));
				model.put("department", DepartmentService.SearchDepartmentLisrCompanyID(Company_id));
				model.put("Branch", branchService.SearchBranchLisrCompanyID(Company_id));

			}

		} catch (Exception e) {
			LOGGER.error("Branch add Page:", e);
		}
		return new ModelAndView("addMasterUser", model);
	}

	@RequestMapping(value = "/saveMasterUser", method = RequestMethod.POST)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	@ResponseBody
	public GenericResponse saveMasterUser(@RequestParam("CID") final String Company_Encode,
			@RequestParam("COMPANY_CODE") final String COMPANY_CODE,
			@Valid final UserDto accountDto, UserProfileDto userProfile, @RequestParam("Roles") final List<Role> role,
			final HttpServletRequest request) throws EmailExistsException {
		try {
			
			if (COMPANY_CODE != null) {
				User validate = UserService.findByUserNameAndCompanyCode(accountDto.getEmail(), COMPANY_CODE);
				if (validate == null) {
					final User registered = prepareModel(accountDto);

					Base64.Decoder decoder = Base64.getDecoder();
					byte[] decodedByteArray = decoder.decode(Company_Encode);
					Integer Company_id = Integer.parseInt(new String(decodedByteArray));

					final UserProfile saveUSERProfiel = UPBL.prepareUpdateUserProfile_Master_User(userProfile,
							accountDto);
					ArrayList<Role> arraylist = new ArrayList<Role>();
					for (Role Rolelist : role) {
						arraylist.add((roleService.Find_Role_Master_Company(Rolelist.getName(), Company_id)));
					}
					
					
					registered.setRoles(arraylist);
					registered.setCompany_id(Company_id);
					if (arraylist != null) {

						try {
							User user = UserService.registerNewUserAccount(registered);
							saveUSERProfiel.setUser_id(user.getId());
							saveUSERProfiel.setCompany_id(Company_id);
							saveUSERProfiel.setRole_id(arraylist.get(0).getId());
							userProfileService.registerNewUserProfile(saveUSERProfiel);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				} else {
					return new GenericResponse("already");

				} 
			}else {
				return new GenericResponse("already");

			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GenericResponse("success");

	}

	@RequestMapping(value = "/resetMasterUserPassword", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_RESET_PWD_PRIVILEGE')")
	public ModelAndView resetMasterUserPassword(@RequestParam("CID") final String Company_Encode, final User user,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();
			if (currentUser != null) {

				Base64.Decoder decoder = Base64.getDecoder();
				byte[] decodedByteArray = decoder.decode(Company_Encode);
				Integer Company_id = Integer.parseInt(new String(decodedByteArray));
				model.put("Company", DriDT.ShowCompanyID_Encode(CompanyService.getCompanyByID(Company_id)));

				if (user.getId() != null) {
					model.put("user", UserService.getUserByID(user.getId()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newUser.html", model);
		}
		return new ModelAndView("resetMasterUserPassword", model);
	}

	@RequestMapping(value = "/master/savePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('MASTER_RESET_PWD_PRIVILEGE')")
	@ResponseBody
	public GenericResponse AdminsavePassword(final Locale locale, @RequestParam("password") final String password,
			@RequestParam("id") final Long id) {
		try {
			// System.out.println(id);
			final User user = UserService.getUserByID(id);

			UserService.changeUserPassword(user, password);
			userProfileService.UpdateLAstPasswordReset(new Date(), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
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
	
	@RequestMapping(value = "/masterEnableConfiguration", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public ModelAndView masterEnableConfiguration(@ModelAttribute("command") CompanyDto CompanyDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			String name = auth.getName(); // get logged in username

			model.put("createdBy", name);
		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("addCompanyConfiguration", model);
	}
	
	@RequestMapping(value = "/masterEnableCompanyConfiguration", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MASTER_COM_ADD_PRIVILEGE')")
	public ModelAndView masterEnableCompanyConfiguration(@ModelAttribute("command") CompanyDto CompanyDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			String name = auth.getName(); // get logged in username

			model.put("createdBy", name);
		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("setCompanyConfiguration", model);
	}
	
}
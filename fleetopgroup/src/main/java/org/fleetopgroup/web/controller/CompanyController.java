package org.fleetopgroup.web.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.CompanyBL;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverJobTypeBL;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CompanybankDto;
import org.fleetopgroup.persistence.dto.CompanydirectorDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.model.CompanyFixedAllowance;
import org.fleetopgroup.persistence.model.Companybank;
import org.fleetopgroup.persistence.model.Companydirector;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@PropertySource({ "classpath:email.properties" })
public class CompanyController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired IDriverJobTypeService 		driverJobTypeService;
	@Autowired IVehicleGroupService 		vehicleGroupService;
	@Autowired ICompanyService 				companyService;
	@Autowired JavaMailSender				javaMailSender;
	@Autowired private MongoOperations		mongoOperations;
	@Autowired private Environment env;
	
	CompanyBL	 		companyBL 			= new CompanyBL();
	DriverJobTypeBL 	driverJobTypeBL 	= new DriverJobTypeBL();
	DriverBL 			driverBL 			= new DriverBL();

	@RequestMapping(value = "/newCompany", method = RequestMethod.GET)
	public ModelAndView newCompany(@ModelAttribute("command") CompanyDto CompanyDto, BindingResult result) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails currentUser = (CustomUserDetails)auth.getPrincipal();
			if(currentUser != null) {
				
				model.put("Company", companyBL.ShowCompanyID_Encode(companyService.getCompanyByID(currentUser.getCompany_id())));
				
				model.put("CompanyDirector", companyService.getCompanydirectorList(currentUser.getCompany_id()));
				
				model.put("CompanyBank", companyService.getCompanybankList(currentUser.getCompany_id()));
				
				model.put("CompanyFixed", companyService.get_CompanyFixedAllowance_List(currentUser.getCompany_id()));
			}

		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("showCompany", model);
	}

	@RequestMapping(value = "/SubCompany", method = RequestMethod.GET)
	public ModelAndView SubCompany(@RequestParam("id") final String subID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(subID);
			// Verify the decoded string

			Integer Id = Integer.parseInt(new String(decodedByteArray));

			model.put("Company", companyBL.ShowCompanyID_Encode(companyService.getCompanyByID(Id)));

			model.put("CompanyDirector", companyService.getCompanydirectorList(Id));

			model.put("CompanyBank", companyService.getCompanybankList(Id));

		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("showSubCompany", model);
	}

	@RequestMapping(value = "/CreateCompany", method = RequestMethod.GET)
	public ModelAndView CreateCompany(@ModelAttribute("command") CompanyDto CompanyDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in username

			model.put("createdBy", name);
		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("addCompany", model);
	}

	// save Main Company
	@RequestMapping(value = "/saveCompany")
	public String singleUpload() {
		return "saveCompany";
	}

	@RequestMapping(value = "/saveCompany", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveCompany(@ModelAttribute("command") CompanyDto CompanyDto,
			@RequestParam("fileUpload") MultipartFile file, org.fleetopgroup.persistence.document.Companylogo logo,
			BindingResult result, @RequestParam("ipAddress") final String ipAddress) {
		
		Map<String, Object> 	model 				= new HashMap<String, Object>();
		Company 				company 			= null;
		Company 				companyfrDB 		= null;
		
		try {
			company 		= companyBL.prepareCompanyModel(CompanyDto);
			companyfrDB 	= companyService.validateCompanyName(CompanyDto.getCompany_name());
			
			if (companyfrDB == null) {
				companyService.registerNewCompany(company);
				model.put("saveCompany", true);

				if (!file.isEmpty()) {
					try {
						byte[] bytes = file.getBytes();
						logo.setFilename(file.getOriginalFilename());
						logo.setLog_content(bytes);
						logo.setLog_contentType(file.getContentType());
						logo.setCompany_id(company.getCompany_id());

						companyService.registerNewCompanylogo(logo);

					} catch (Exception e) {
						e.printStackTrace();
						// messages
						model.put("alreadyPhoto", true);
						LOGGER.error("Company Create Page:", e);
						return new ModelAndView("redirect:/newCompany.html", model);
					}
				} else {
					// messages
					model.put("emptyPhoto", true);
					return new ModelAndView("redirect:/newCompany.html", model);

				}
				
				if (company.getCompany_id() > 0) {
					String email = "--";
					String mobileNo = "--";
					String city = "--";
					
					if(company.getCompany_email() != null && !company.getCompany_email().isEmpty()) {
						email = company.getCompany_email();					
					}
					if(company.getCompany_mobilenumber() != null && !company.getCompany_mobilenumber().isEmpty()) {
						mobileNo = company.getCompany_mobilenumber();					
					}
					if(company.getCompany_city() != null && company.getCompany_city() != "") {
						city = company.getCompany_city();					
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
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+company.getCompany_name()+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+company.getCompanyCode()+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+company.getCompany_type()+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+email+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+mobileNo+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+city+"</td>"
					+"<td style=\"border: 1px solid #ddd;padding: 8px;\">"+company.getCreatedBy()+"</td>"
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
							messageHelper.setCc(env.getProperty("developer.email"));

							/* sent html email text */
							messageHelper.setText(sendHTML_Email_PO, true);
						}
					});
				}
			} else {
				model.put("alreadyCompany", true);
				return new ModelAndView("redirect:/newCompany.html", model);
			}

		} catch (Exception e) {
			model.put("alreadyCompany", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		return new ModelAndView("redirect:/newCompany.html", model);
	}

	// Image Document the Document id
	@RequestMapping("/downloadlogo/{logoId}")
	public String download(@PathVariable("logoId") String logoId, HttpServletResponse response) {
		try {
			if (logoId == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				Base64.Decoder decoder = Base64.getDecoder();
				byte[] decodedByteArray = decoder.decode(logoId);
				Integer Company_id = Integer.parseInt(new String(decodedByteArray));
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.Companylogo file = companyService.getCompanyLogo(Company_id);
				// Check if file is actually retrieved from database.
				if (file != null) {
					if (file.getLog_content() != null) {

						response.setHeader("Content-Disposition", "inline;filename=\"" + file.getFilename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getLog_contentType());
						response.getOutputStream().write(file.getLog_content());
						out.flush();
						out.close();

					}
				}
			}
		} catch (Exception e1) {
			LOGGER.error("Add Vehicle Photo Page:", e1);
		}
		return null;
	}

	@RequestMapping(value = "/editCompany", method = RequestMethod.GET)
	public ModelAndView editdriver(@RequestParam("id") final String subID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in username

			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(subID);
			Integer Id = Integer.parseInt(new String(decodedByteArray));

			model.put("lastModifiedBy", name);
			model.put("Company", companyBL.prepareCompanyDto(companyService.getCompanyByID(Id)));

		} catch (Exception e) {
			LOGGER.error("Company Edit Page:", e);
		}

		return new ModelAndView("editCompany", model);
	}

	@RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
	public @ResponseBody ModelAndView updateCompany(@ModelAttribute("command") CompanyDto CompanyDto,
			@RequestParam("fileUpload") MultipartFile file,  org.fleetopgroup.persistence.document.Companylogo logo, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Company company = companyBL.prepareCompanyModel_Update(CompanyDto);
			if (company != null) {
				
				companyService.registerNewCompany(company);
				model.put("updateCompany", true);
				
				if (!file.isEmpty()) {
					try {
						byte[] bytes = file.getBytes();
						logo.setFilename(file.getOriginalFilename());
						logo.setLog_content(bytes);
						logo.setLog_contentType(file.getContentType());
						logo.setCompany_id(company.getCompany_id());
						org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
						query.addCriteria(Criteria.where("company_id").is(company.getCompany_id()));
						mongoOperations.findAndRemove(query, org.fleetopgroup.persistence.document.Companylogo.class);
						
						if(logo.get_id() == null) {
							companyService.registerNewCompanylogo(logo);
						}else {
							mongoOperations.save(logo);
						}

					} catch (Exception e) {
						e.printStackTrace();
						// messages
						model.put("alreadyPhoto", true);
						LOGGER.error("Company Create Page:", e);
						return new ModelAndView("redirect:/masterCompany/1.html", model);
					}
				}

			} else {
				model.put("alreadyCompany", true);
				return new ModelAndView("redirect:/newCompany.html", model);
			}

		} catch (Exception e) {
			model.put("alreadyCompany", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		return new ModelAndView("redirect:/newCompany.html", model);
	}

	/* Sub Company Details */
	@RequestMapping(value = "/CreateSubCompany", method = RequestMethod.GET)
	public ModelAndView CreateSubCompany(@RequestParam("id") final String subID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(subID);
			Integer Id = Integer.parseInt(new String(decodedByteArray));

			model.put("Company", companyBL.prepareCompanyDto(companyService.getCompanyByID(Id)));
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in username

			model.put("createdBy", name);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("addSubCompany", model);
	}

	@RequestMapping(value = "/editSubCompany", method = RequestMethod.GET)
	public ModelAndView EditSubCompany(@RequestParam("id") final String subID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(subID);
			Integer Id = Integer.parseInt(new String(decodedByteArray));

			model.put("Company", companyBL.prepareCompanyDto(companyService.getCompanyByID(Id)));

		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("editSubCompany", model);
	}

	@RequestMapping(value = "/updateSubCompany", method = RequestMethod.POST)
	public @ResponseBody ModelAndView updateSubCompany(@ModelAttribute("command") CompanyDto CompanyDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Company company = companyBL.updateSubCompanyDetails(CompanyDto);

			if (company != null) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String name = auth.getName(); // get logged in username
				company.setLastModifiedBy(name);

				companyService.registerNewCompany(company);
				model.put("updateCompany", true);

			} else {
				model.put("alreadyCompany", true);
				return new ModelAndView("redirect:/newCompany.html", model);
			}

		} catch (Exception e) {
			model.put("alreadyCompany", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		return new ModelAndView("redirect:/newCompany.html", model);
	}

	@RequestMapping(value = "/deleteSubCompany", method = RequestMethod.GET)
	public ModelAndView deleteSubCompany(@RequestParam("id") final String subID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// companyService.deleteCompany(CompanyDto.getCompany_id());
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(subID);
			Integer Id = Integer.parseInt(new String(decodedByteArray));
			
			List<Companybank> companybanklist = companyService.getCompanybankList(Id);
			List<Companydirector> companydirectorList = companyService.getCompanydirectorList(Id);

			if (companybanklist.isEmpty() && companydirectorList.isEmpty()) {
				companyService.deleteCompanyDetails(Id);
				model.put("deleteSubCompany", true);
			} else {
				model.put("deleteCompanyDetails", true);
			}

		} catch (Exception e) {
			model.put("alreadyCompany", true);
			LOGGER.error("Driver Document Page:", e);
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		return new ModelAndView("redirect:/newCompany.html", model);
	}

	/* Company Owner / Directors */

	@RequestMapping(value = "/DirectorCompany", method = RequestMethod.GET)
	public ModelAndView DirectorCompany(@RequestParam("id") final String subID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(subID);
			Integer Id = Integer.parseInt(new String(decodedByteArray));
			model.put("Company", companyBL.prepareCompanyDto(companyService.getCompanyByID(Id)));
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in username

			model.put("createdBy", name);
		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("addCompanyDirector", model);
	}

	@RequestMapping(value = "/saveDirectorCompany", method = RequestMethod.POST)
	public ModelAndView saveDirectorCompany(@ModelAttribute("command") CompanydirectorDto companydirectorDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Companydirector companydirector = companyBL.prepareCompanydirector(companydirectorDto);
			Companydirector validate = companyService
					.validateCompanydirectorName(companydirectorDto.getCom_directors_name(), companydirector.getCompany_id());
			if (validate == null) {
				companyService.registerNewCompanydirector(companydirector);
				model.put("updateCompany", true);

			} else {
				model.put("alreadyCompany", true);
				return new ModelAndView("redirect:/newCompany.html", model);
			}

		} catch (Exception e) {
			model.put("alreadyCompany", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		return new ModelAndView("redirect:/newCompany.html", model);
	}

	@RequestMapping(value = "/editDirectorCompany", method = RequestMethod.GET)
	public ModelAndView editDirectorCompany(@RequestParam("id") final Integer directorID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Companydirector companydirector = companyService.getCompanydirectorByID(directorID, userDetails.getCompany_id());
			model.put("director", companydirector);

			model.put("Company", companyBL.prepareCompanyDto(companyService.getCompanyByID(companydirector.getCompany_id())));

		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("editCompanyDirector", model);
	}

	@RequestMapping(value = "/updateDirectorCompany", method = RequestMethod.POST)
	public ModelAndView updateDirectorCompany(@ModelAttribute("command") CompanydirectorDto companydirectorDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Companydirector companydirector = companyBL.updateCompanydirector(companydirectorDto);
			if (companydirector != null) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String name = auth.getName();
				companydirector.setLastModifiedBy(name);

				companyService.registerNewCompanydirector(companydirector);
				model.put("updateCompany", true);

			} else {
				model.put("alreadyCompany", true);
				return new ModelAndView("redirect:/newCompany.html", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("alreadyCompany", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		return new ModelAndView("redirect:/newCompany.html", model);
	}

	@RequestMapping(value = "/deleteDirectorCompany", method = RequestMethod.GET)
	public ModelAndView deleteDirectorCompany(@RequestParam("id") final Integer directorID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			companyService.deleteCompanydirector(directorID, userDetails.getCompany_id());

			model.put("deleteDirectorCompany", true);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("alreadyCompany", true);
			LOGGER.error("Driver Document Page:", e);
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		return new ModelAndView("redirect:/newCompany.html", model);
	}

	/* Company Bank Details */

	@RequestMapping(value = "/BankCompany", method = RequestMethod.GET)
	public ModelAndView BankCompany(@RequestParam("id") final String subID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(subID);
			Integer Id = Integer.parseInt(new String(decodedByteArray));

			model.put("Company", companyBL.prepareCompanyDto(companyService.getCompanyByID(Id)));
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in username

			model.put("createdBy", name);
		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("addCompanyBank", model);
	}

	@RequestMapping(value = "/saveBankCompany", method = RequestMethod.POST)
	public ModelAndView saveBankCompany(@ModelAttribute("command") CompanybankDto companybankDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Companybank companybank = companyBL.prepareCompanybank(companybankDto);
			companybank.setCompany_id(userDetails.getCompany_id());
			Companybank validate = companyService.validateCompanybankAccount(companybankDto.getCom_bank_account(), userDetails.getCompany_id());
			if (validate == null) {
				companyService.registerNewCompanybank(companybank);
				model.put("saveCompany", true);

			} else {
				model.put("alreadyCompany", true);
				return new ModelAndView("redirect:/newCompany.html", model);
			}

		} catch (Exception e) {
			model.put("alreadyCompany", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		return new ModelAndView("redirect:/newCompany.html", model);
	}

	@RequestMapping(value = "/editBankCompany", method = RequestMethod.GET)
	public ModelAndView editBankCompany(@RequestParam("id") final Integer bank_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Companybank companybank = companyService.getCompanybankByID(bank_ID, userDetails.getCompany_id());
			if (companybank != null) {
				model.put("bank", companybank);
				model.put("Company", companyBL.prepareCompanyDto(companyService.getCompanyByID(companybank.getCompany_id())));
			}

		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("editCompanyBank", model);
	}

	@RequestMapping(value = "/updateBankCompany", method = RequestMethod.POST)
	public ModelAndView updateBankCompany(@ModelAttribute("command") CompanybankDto companybankDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Companybank companybank = companyBL.UpdateCompanybankDetails(companybankDto);
			if (companybank != null) {

				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String name = auth.getName();
				companybank.setLastModifiedBy(name);

				companyService.registerNewCompanybank(companybank);
				model.put("updateCompany", true);

			} else {
				model.put("alreadyCompany", true);
				return new ModelAndView("redirect:/newCompany.html", model);
			}

		} catch (Exception e) {
			model.put("alreadyCompany", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		return new ModelAndView("redirect:/newCompany.html", model);
	}

	@RequestMapping(value = "/deleteBankCompany", method = RequestMethod.GET)
	public ModelAndView deleteBankCompany(@RequestParam("id") final Integer bank_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			companyService.deleteCompanybank(bank_ID, userDetails.getCompany_id());
			model.put("deleteBankCompany", true);

		} catch (Exception e) {
			e.printStackTrace();
			model.put("alreadyCompany", true);
			LOGGER.error("Driver Document Page:", e);
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		return new ModelAndView("redirect:/newCompany.html", model);
	}
	
	
	//Fixed Allowance Details
	
		@RequestMapping(value = "/FixedComAllow", method = RequestMethod.GET)
		public ModelAndView FixedComAllow(@RequestParam("id") final String subID, final HttpServletRequest request) {
			Map<String, Object> model = new HashMap<String, Object>();
			try {

				Base64.Decoder decoder = Base64.getDecoder();
				byte[] decodedByteArray = decoder.decode(subID);
				Integer Id = Integer.parseInt(new String(decodedByteArray));

				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				model.put("Company", companyBL.prepareCompanyDto(companyService.getCompanyByID(Id)));
				
				model.put("vehiclegroup", driverBL.prepareListofDto(vehicleGroupService.findAllVehicleGroupByCompanyId(userDetails.getCompany_id())));
				model.put("CompanyID", Id);

				model.put("driverJobType", driverJobTypeBL.DriJobTypeListofBean(driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id())));

			} catch (Exception e) {
				LOGGER.error("Comapny Allowance Details", e);
			}
			return new ModelAndView("addCompanyFixedAllowance", model);
		}
		
		
		@RequestMapping(value = "/saveCompanyFixedAllowance", method = RequestMethod.POST)
		public ModelAndView saveCompanyFixedAllowance(@ModelAttribute("command") CompanyFixedAllowance FixedAllowance,
				BindingResult result) {
			Map<String, Object> model = new HashMap<String, Object>();
			try {
				CompanyFixedAllowance companyFixedAllowance = companyBL.prepare_CompanyFixedAllowance(FixedAllowance);
				CompanyFixedAllowance validate = companyService.validate_CompanyFixedAllowance(FixedAllowance.getVEHICLEGROUP_ID(), FixedAllowance.getDRIVER_JOBTYPE_ID());
				if (validate == null) {
					companyService.registerNewCompanyFixedAllowance(companyFixedAllowance);
					model.put("saveCompany", true);

				} else {
					model.put("alreadyCompany", true);
					return new ModelAndView("redirect:/newCompany.html", model);
				}

			} catch (Exception e) {
				model.put("alreadyCompany", true);
				LOGGER.error("Company Create Page:", e);
				return new ModelAndView("redirect:/newCompany.html", model);
			}
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		
		
		@RequestMapping(value = "/deleteFixedAllowance", method = RequestMethod.GET)
		public ModelAndView deleteFixedAllowance(@RequestParam("id") final Long FixedAllowance_ID, final HttpServletRequest request) {
			Map<String, Object> model = new HashMap<String, Object>();
			try {
				companyService.delete_CompanyFixedAllowance(FixedAllowance_ID);
				model.put("deleteBankCompany", true);

			} catch (Exception e) {
				e.printStackTrace();
				model.put("alreadyCompany", true);
				LOGGER.error("Driver Document Page:", e);
				return new ModelAndView("redirect:/newCompany.html", model);
			}
			return new ModelAndView("redirect:/newCompany.html", model);
		}
		
		//Devy start code for company field ajax
		@RequestMapping(value = "/getCompanyInformationDetails", method = RequestMethod.POST)
		public void getCompanyInformationDetails(Map<String, Object> map, @RequestParam("term") final String term,
				final HttpServletRequest request, HttpServletResponse response) throws Exception {
			try {
				List<CompanyDto> addresses = new ArrayList<CompanyDto>();
				CompanyDto company = null;
				List<Company> CompanyDetails = companyService.findAll();
				
				if (CompanyDetails != null && !CompanyDetails.isEmpty()) {
					for (Company add : CompanyDetails) {
						company = new CompanyDto();

						company.setCompany_id(add.getCompany_id());
						company.setCompany_name(add.getCompany_name());
						addresses.add(company);
					}
				}
				Gson gson = new Gson();

				response.getWriter().write(gson.toJson(addresses));
			} catch (Exception e) {
				throw e;
			}
		}

		@RequestMapping(value = "/getCompanyList", method = RequestMethod.GET)
		public void getVehicleGroup(Map<String, Object> map, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try {
				List<CompanyDto> addresses = new ArrayList<CompanyDto>();
				CompanyDto company = null;
				List<Company> CompanyDetails = companyService.findAll();
				
				if (CompanyDetails != null && !CompanyDetails.isEmpty()) {
					for (Company add : CompanyDetails) {
						company = new CompanyDto();

						company.setCompany_id(add.getCompany_id());
						company.setCompany_name(add.getCompany_name());
						addresses.add(company);
					}
				}
				Gson gson = new Gson();

				response.getWriter().write(gson.toJson(addresses));
			} catch (Exception e) {
				throw e;
			}
		}

		@RequestMapping(value = "/searchCompanyList", method = RequestMethod.GET)
		public void getCompanyList(@RequestParam(value = "term") String term,
				Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
			try {
				List<CompanyDto> addresses = new ArrayList<CompanyDto>();
				CompanyDto company = null;
				List<Company> CompanyDetails = companyService.searchCompanyList(term);
				
				if (CompanyDetails != null && !CompanyDetails.isEmpty()) {
					for (Company add : CompanyDetails) {
						company = new CompanyDto();

						company.setCompany_id(add.getCompany_id());
						company.setCompany_name(add.getCompany_name());
						addresses.add(company);
					}
				}
				Gson gson = new Gson();

				response.getWriter().write(gson.toJson(addresses));
			} catch (Exception e) {
				throw e;
			}
		}

		
		@RequestMapping(value = "/getCompanyNameList", method = RequestMethod.GET)
		public void getTripExpenseList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			System.err.println("inside controller");
			List<Company> companyList = new ArrayList<Company>();

			for (Company company : companyService.findAll()) {

				Company dto = new Company();
				dto.setCompany_id(company.getCompany_id());
				dto.setCompanyCode(company.getCompanyCode().toUpperCase());
				companyList.add(dto);
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(companyList));
		}
		
		//getCompanyConfigurationData
		
}
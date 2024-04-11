package org.fleetopgroup.web.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.fleetopgroup.constant.MasterDocumentsConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.MailboxDto;
import org.fleetopgroup.persistence.dto.MarqueeMasterDto;
import org.fleetopgroup.persistence.model.Mailbox;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.service.RoleDto;
import org.fleetopgroup.persistence.service.UserDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IMailBoxService;
import org.fleetopgroup.persistence.serviceImpl.IMarqueeMasterService;
import org.fleetopgroup.persistence.serviceImpl.IOTPService;
import org.fleetopgroup.persistence.serviceImpl.IRoleService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IUserService;
import org.fleetopgroup.web.error.InvalidOldPasswordException;
import org.fleetopgroup.web.util.GenericResponse;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class RegistrationController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private IRoleService RoleService;

	@Autowired
	private MessageSource messages;

	@Autowired
	private IMailBoxService mailboxService;
	
	@Autowired
	private IMarqueeMasterService marqueeMasterService;
	
	@Autowired private	IOTPService	otpService;
	
	
	@Autowired	ICompanyConfigurationService	companyConfigurationService;

	public RegistrationController() {
		super();
	}

	
	@RequestMapping(value = "/open", method = RequestMethod.GET)
	public ModelAndView open(final Locale locale, final HttpServletRequest request, UserDto UserDto) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("masterDocumentList", MasterDocumentsConstant.getMaterDocumentList());
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			HashMap<String, Object> configurationAlert		=companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.FUEL_INVOICE_CONFIG);		
			model.put("configurationAlert", configurationAlert);
			if((boolean)configuration.get(ICompanyConfigurationService.IS_OTP_VALIDATION_AT_LOGIN) && !userDetails.isOTPValidated()) {
				if(userProfileService.isOTPValidationRequired(userDetails.getId())) {
					if(otpService.getOtp(userDetails.getId()) > 0) {
						return new ModelAndView("redirect:/otpvalidate.in?id="+userDetails.getId()+"&otpValidated=false", model);
					}
					return new ModelAndView("redirect:/otpvalidate.in?id="+userDetails.getId(), model);
				}
			}
			
			if((boolean)configuration.get("forceUserToResetPassword")) {
			 ValueObject	valueObject	=	userProfileService.checkIfPasswordResetRequired(userDetails.getId(), configuration, userDetails.getCompany_id());
			  if(valueObject.getBoolean("passwordChangeRequired") && !valueObject.getBoolean("firstTimeForceReset")) {
				  return new ModelAndView("redirect:/UserChangePassword.in?forceWithDays=true");
			  }	else if(valueObject.getBoolean("firstTimeForceReset")){
				  return new ModelAndView("redirect:/UserChangePassword.in?force=true");
			  }
			
			}
		}
		
		return new ModelAndView("vehicle", model);
	}
	
	@RequestMapping(value = "/otpvalidate", method = RequestMethod.GET)
	public ModelAndView otpvalidate(final Locale locale, final HttpServletRequest request, @RequestParam("id") final Long id) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		HttpSession session= request.getSession(false);
        SecurityContextHolder.clearContext();
        if(session != null) {
            session.invalidate();
        }
	
		if(otpService.getOtp(id) <= 0) {
			int otp =   otpService.generateOTP(id);
			System.err.println("your otp is : "+ otp);
		}else {
			model.put("otpValidated", false);
			System.err.println("your otp is : "+ otpService.getOtp(id));
		}
		return new ModelAndView("otpvalidate", model);
	}
	
	@RequestMapping(value = "/validateOtp", method = RequestMethod.POST)
	public ModelAndView validateOtp(final Locale locale, final HttpServletRequest request, UserDto	userDto,
			@RequestParam("companyCode") final String companyCode,
			@RequestParam("validateotp") final int validateotp
			) throws ExecutionException {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> model = new HashMap<String, Object>();
		if(otpService.getOtp(userDetails.getId()) == validateotp) {
			userDetails.setOTPValidated(true);
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			model.put("otpValidated", true);
			
			return new ModelAndView("redirect:/open.in", model);
		}else {
			model.put("otpValidated", false);
		}
		
		return new ModelAndView("redirect:/otpvalidate.in", model);
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(final Locale locale, final HttpServletRequest request) {
		
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		 if (!(auth instanceof AnonymousAuthenticationToken)) {
		     return new ModelAndView("redirect:/open");
		 }
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("masterDocumentList", MasterDocumentsConstant.getMaterDocumentList());
		return new ModelAndView("login", model);
	}
	
	@RequestMapping(value = "/contactus", method = RequestMethod.GET)
	public ModelAndView contactus(final Locale locale, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("masterDocumentList", MasterDocumentsConstant.getMaterDocumentList());
		return new ModelAndView("contactus", model);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(final Locale locale, final HttpServletRequest request) {
		HttpSession session= request.getSession(false);
        SecurityContextHolder.clearContext();
        if(session != null) {
            session.invalidate();
        }

		return new ModelAndView("logout");
	}

	@RequestMapping(value = "/invalidSession")
	public ModelAndView invalidSession(final Locale locale, final HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 if (!(auth instanceof AnonymousAuthenticationToken)) {
		     return new ModelAndView("redirect:/open");
		 }
		
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("invalidSession", model);
	}

	@RequestMapping(value = "/console", method = RequestMethod.GET)
	public ModelAndView AdminAccount(final Locale locale, final HttpServletRequest request, UserDto UserDto) {

		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("admin", model);
	}

	@RequestMapping(value = "/successRegister")
	public ModelAndView successRegister(final Locale locale, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("successRegister", model);
	}

	// Show New Role List and Create Role
	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView NewRole(final @RequestParam("id") Long user_id, final RoleDto RoleDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// model.put("user", userService.findUserByEmail(email));
			model.put("RoleCount", RoleService.count());
			model.put("user", userService.getUserByID(user_id));
		} catch (Exception e) {

			LOGGER.error("Registering New user account with information: {}", e.getLocalizedMessage());

		}
		return new ModelAndView("RoleNew", model);
	}

	@RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	@ResponseBody
	public GenericResponse savePassword(final Locale locale, @RequestParam("password") final String password) throws Exception {
		final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userService.changeUserPassword(user, password);
		userProfileService.UpdateLAstPasswordReset(new Date(), user.getId());
		return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
	}

	@RequestMapping(value = "/admin/savePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	@ResponseBody
	public GenericResponse AdminsavePassword(final Locale locale, @RequestParam("password") final String password,
			@RequestParam("id") final Long id) {
		try {
			// System.out.println(id);
			final User user = userService.getUserByID(id);

			userService.changeUserPassword(user, password);
			userProfileService.UpdateLAstPasswordReset(new Date(), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
	}

	@RequestMapping(value = "/adminActive", method = RequestMethod.GET)
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	@ResponseBody
	public ModelAndView adminActive(final Locale locale, @RequestParam("id") final Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// System.out.println(id);
			final User user = userService.getUserByID(id);

			userService.changeUserActive(user);
			userProfileService.changeUserActive(id, false, userDetails.getCompany_id());

			model.put("changetoActive", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/newUserList/1.html", model);
	}

	@RequestMapping(value = "/adminInActive", method = RequestMethod.GET)
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	@ResponseBody
	public ModelAndView adminInActive(final Locale locale, @RequestParam("id") final Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// System.out.println(id);
			final User user = userService.getUserByID(id);
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userService.changeUserInActive(user);
			userProfileService.changeUserInActive(id, true, userDetails.getCompany_id());

			model.put("changetoInActive", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/newUserList/1.html", model);
	}

	// change user password

	@RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	@ResponseBody
	public GenericResponse changeUserPassword(final Locale locale, @RequestParam("password") final String password,
			@RequestParam("oldpassword") final String oldPassword) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName(), userDetails.getCompanyCode());

			if (!userService.checkIfValidOldPassword(user, oldPassword)) {
				throw new InvalidOldPasswordException();
			}
			userService.changeUserPassword(user, password);
			userProfileService.UpdateLAstPasswordReset(new Date(), userDetails.getId());
			return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			throw e;
		}
	}

	// API IN UNREAD MESSAGE DETAILS
	@RequestMapping(value = "/GetUnReadMail", method = RequestMethod.GET)
	public void GetUnReadMail(Map<String, Object> map, final HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails currentUser = (CustomUserDetails) auth.getPrincipal();

		Mailbox addresses = new Mailbox();
		String TID = "";
		
		Long count = mailboxService.countTotal_Unread_MailBox(currentUser.getEmail());
		if (count != null && currentUser != null) {

			TID += "" + count + "";
			addresses.setMAILBOX_FROM_EMAIL(TID);
			addresses.setMAILBOX_FROM_USER_NAME(currentUser.getFirstName() + " " + currentUser.getLastName());
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/GetUnReadMailMessage", method = RequestMethod.GET)
	public void GetUnReadMailMessage(Map<String, Object> map, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<MailboxDto> UnReadMail = new ArrayList<MailboxDto>();
		List<Mailbox> count = mailboxService.Only_Unread_Mailbox(userDetails.getEmail());
		if (count != null && !count.isEmpty()) {

			for (Mailbox mailbox : count) {

				MailboxDto Dto = new MailboxDto();

				Base64.Encoder encoder = Base64.getEncoder();
				String normalMailID = "" + mailbox.getMAILBOX_ID();
				String encodedMailId = encoder.encodeToString(normalMailID.getBytes(StandardCharsets.UTF_8));
				/** encode long Mailbox-Id value */
				Dto.setMAILBOX_ID_Encode(encodedMailId);
				Dto.setMAILBOX_FROM_EMAIL(mailbox.getMAILBOX_FROM_EMAIL());
				Dto.setMAILBOX_FROM_USER_NAME(mailbox.getMAILBOX_FROM_USER_NAME());
				Dto.setMAILBOX_NAMESPACE(mailbox.getMAILBOX_NAMESPACE());
				if (mailbox.getMAIL_DATE() != null) {
					Dto.setMAIL_DATE(getDateDifferenceInDDMMYYYY(mailbox.getMAIL_DATE()));
				}
				UnReadMail.add(Dto);
			}
		}

		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(UnReadMail));
	}

	public static String getDateDifferenceInDDMMYYYY(java.util.Date date) {

		java.util.Date currentDate = new java.util.Date();
		// in milliseconds
		long timeDifferenceMilliseconds = currentDate.getTime() - date.getTime();

		long diffSeconds = timeDifferenceMilliseconds / 1000;
		long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
		long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
		long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
		long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
		long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
		long diffYears = timeDifferenceMilliseconds / ((long) 60 * 60 * 1000 * 24 * 365);

		if (diffSeconds < 1) {
			return "less than a second";
		} else if (diffMinutes < 1) {
			return diffSeconds + " seconds";
		} else if (diffHours < 1) {
			return diffMinutes + " minutes";
		} else if (diffDays < 1) {
			return diffHours + " hours";
		} else if (diffWeeks < 1) {
			return diffDays + " days";
		} else if (diffMonths < 1) {
			return diffWeeks + " weeks";
		} else if (diffYears < 1) {
			return diffMonths + " months";
		} else {
			return diffYears + " years";
		}
	}
	
	@RequestMapping(value = "/GetMarqueeMessage", method = RequestMethod.GET)
	public void GetMarqueeMessage(Map<String, Object> map, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			List<MarqueeMasterDto>	marqueeMasterMessage  = marqueeMasterService.getCompayWiseMarqueeMessage();
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(marqueeMasterMessage));
	}

}

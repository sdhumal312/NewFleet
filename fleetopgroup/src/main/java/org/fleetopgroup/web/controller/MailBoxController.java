/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.MailboxBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.MessagePropertyDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.Mailbox;
import org.fleetopgroup.persistence.model.MessageProperty;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IMailBoxService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISubscribeBoxService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.error.MailSentExistException;
import org.fleetopgroup.web.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * @author fleetop
 *
 */
@Controller
public class MailBoxController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IMailBoxService mailboxService;

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private ISubscribeBoxService SubscribeboxService;

	@Autowired
	private IServiceReminderService ServiceReminderService;

	@Autowired
	private IRenewalReminderService RenewalReminderService;

	@Autowired
	private IDriverService driverService;

	ByteImageConvert ByteImageConvert = new ByteImageConvert();

	RenewalReminderBL RRBL = new RenewalReminderBL();
	MailboxBL MBL = new MailboxBL();
	DriverReminderBL DriverRem = new DriverReminderBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@RequestMapping(value = "/mailbox/{pageNumber}", method = RequestMethod.GET)
	public String Mailbox(@PathVariable Integer pageNumber, Model model, final HttpServletRequest request) {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


			Page<Mailbox> page = mailboxService.get_Inbox_Mailbox_Page_Mail(pageNumber, userDetails.getEmail());
			try {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				model.addAttribute("InboxCount", page.getTotalElements());

				/** Showing last 50 mail box */
				model.addAttribute("mail",
						MBL.prepareMailboxDto(mailboxService.List_Inbox_Mailbox(pageNumber, userDetails.getEmail())));

				/** count unread mail */
				model.addAttribute("unread", mailboxService.countTotal_Unread_MailBox(userDetails.getEmail()));

				/** count Inbox Both Read Unread mail */
				// model.addAttribute("InboxCount",
				// mailboxService.countTotal_Inbox_MailBox(userName));

				java.util.Date currentDate = new java.util.Date();
				DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
				UserProfileDto user = userProfileService.getUser_email_to_subscribe_Detils(userDetails.getEmail());

				Object[] count = SubscribeboxService.countTotal_Subscrible_MailBox(userDetails.getEmail(), ft.format(currentDate), userDetails.getCompany_id());
				Long cnt = (long) 0;
				if (user != null && user.getSubscribe() != null) {
					String temp = user.getSubscribe();
					if (temp.indexOf("SERVICE_REMINDER") != -1) {
						cnt += (Long) count[0];
					}
					if (temp.indexOf("RENEWAL_REMINDER") != -1) {
						cnt += (Long) count[1];
					}
					if (temp.indexOf("DRIVER_REMINDER") != -1) {
						cnt += (Long) count[2];
					}
				}

				/** count Inbox Both Read Unread mail */
				model.addAttribute("SubscribeCount", cnt);

				String name = "INBOX";
				model.addAttribute("displayname", name);
				
			} catch (NullPointerException e) {
				return "redirect:/NotFound.in";
			} catch (Exception e) {
				LOGGER.error("Mail Box Page:", e);
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Mail Page:", e);
		}
		return "NewMailBox";
	}

	@RequestMapping(value = "/compose", method = RequestMethod.GET)
	public ModelAndView compose(@ModelAttribute("command") Mailbox mail, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

		} catch (Exception e) {
			LOGGER.error("Driver Page:", e);
		}
		return new ModelAndView("ComposeMailBox", model);
	}

	/** this save mail in to own and also sent person */
	@RequestMapping(value = "/savecompose", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView savecompose(@Valid final Mailbox mailbox, @RequestParam("TO") String emailTo,
			@RequestParam("SUBJECT") String SUBJECT, @RequestParam("MESSAGE") String MESSAGE,
			@RequestParam("ATTACHMENT_FILE") MultipartFile file, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName(); // get logged in username

		UserProfileDto User = userProfileService.findUserProfileByUser_email(userName);

		Mailbox sent = sentMail_To_Own(User, SUBJECT, MESSAGE, emailTo);

		MessageProperty message = sentMail_To_Another_MessageProperty(User, MESSAGE);

		if (sent != null) {
			try {
				final Long sentMail_ID;

				if (!file.isEmpty()) {
					sent.setMAIL_DOCUMENT(true);
				} else {
					sent.setMAIL_DOCUMENT(false);
				}
				
				sent.setCOMPANY_ID(userDetails.getCompany_id());
				/** This create Own Sent Code here we not store senter_mailID */
				Mailbox savemailbox = mailboxService.insert_SENT_MailBox(sent);
				
				
				sentMail_ID = savemailbox.getMAILBOX_ID();
				message.setMAILBOX_SENT_ID(savemailbox.getMAILBOX_ID());
				message.setMailbox(savemailbox);
				mailboxService.insert_SENT_MeassageProperty(message);

				Long MAILDOCID = (long) 0;
				// Note Sent Person Mail Document Seeing
				if (!file.isEmpty()) {

					org.fleetopgroup.persistence.document.MailboxDocument MailDoc = new org.fleetopgroup.persistence.document.MailboxDocument();

					// Note: Mail Create ID IS
					MailDoc.setMAILBOX_ID(savemailbox.getMAILBOX_ID());
					try {

						byte[] bytes = file.getBytes();
						MailDoc.setMAIL_FILENAME(file.getOriginalFilename());
						MailDoc.setMAIL_CONTENT(bytes);
						MailDoc.setMAIL_CONTENT_TYPE(file.getContentType());

						/** Set Status in Issues */
						MailDoc.setMarkForDelete(false);

						/**
						 * who Create this Issues get email id to user profile
						 * details
						 */
						Authentication authed = SecurityContextHolder.getContext().getAuthentication();
						String create_name = authed.getName();

						/** Set Created by email Id */
						MailDoc.setCREATEDBY(create_name);
						MailDoc.setLASTMODIFIEDBY(create_name);

						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						/** Set Created Current Date */
						MailDoc.setCREATED_DATE(toDate);
						MailDoc.setLASTUPDATED_DATE(toDate);
					} catch (IOException e) {
						e.printStackTrace();
					}

					// Note: Save cashDocDocument Details
					mailboxService.save(MailDoc);

					// Note This Mail Id Document Id Update in MailBox
					mailboxService.Update_MailBoxDocuemnt_ID_To_MailBox(MailDoc.get_id(), true,
							savemailbox.getMAILBOX_ID());

					MAILDOCID = MailDoc.get_id();

				}

				String[] emailId = emailTo.split(",");
				for (String sentPersonEmail : emailId) {

					Mailbox sent_Other_Inbox = sentMail_To_Another_INBOX(User, SUBJECT, MESSAGE, emailTo);
					MessageProperty sent_Other_message = sentMail_To_Another_MessageProperty(User, MESSAGE);

					/** this select email name */
					sent_Other_Inbox.setMAILBOX_TO_EMAIL(sentPersonEmail);

					/**
					 * sentMail_ID code in sender mail_id save in replay to ID
					 */
					sent_Other_Inbox.setMAILBOX_SENTER_MAILBOX_ID(sentMail_ID);

					if (!file.isEmpty()) {
						sent_Other_Inbox.setMAIL_DOCUMENT(true);
						sent_Other_Inbox.setMAIL_DOCUMENT_ID(MAILDOCID);
					} else {
						sent_Other_Inbox.setMAIL_DOCUMENT(false);
						sent_Other_Inbox.setMAIL_DOCUMENT_ID(MAILDOCID);
					}
					
					
					sent_Other_Inbox.setCOMPANY_ID(userDetails.getCompany_id());

					/** This create Another Inbox Sent Code */
					Mailbox sentOthermailbox = mailboxService.insert_SENT_MailBox(sent_Other_Inbox);
					

					/** MeassageProperty Save mail_ID and Message */
					// sentMail_ID = savemailbox.getMAILBOX_ID();
					sent_Other_message.setMAILBOX_SENT_ID(sentMail_ID);
					sent_Other_message.setMailbox(sentOthermailbox);
					mailboxService.insert_SENT_MeassageProperty(sent_Other_message);

				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new MailSentExistException();
			}
		} else {
			throw new MailSentExistException();
		}

		model.put("success", true);
		return new ModelAndView("redirect:/compose", model);
	}

	/** The Value code id Send Mail box Show */
	@RequestMapping(value = "/sentmailbox/{pageNumber}", method = RequestMethod.GET)
	public String sentmailbox(@PathVariable Integer pageNumber, Model model, final HttpServletRequest request) {

		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userName = auth.getName();

			Page<Mailbox> page = mailboxService.get_Mailbox_Page_Mail(pageNumber, userName, "SENT");
			try {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				model.addAttribute("InboxCount", page.getTotalElements());

				model.addAttribute("mail",
						MBL.prepareMailboxDto(mailboxService.List_Sent_Mailbox(pageNumber, userName)));

				/** count Inbox Both Read Unread mail */
				// model.addAttribute("InboxCount",
				// mailboxService.countTotal_Sent_MailBox(userName));

				String name = "SENT";
				model.addAttribute("displayname", name);
			} catch (NullPointerException e) {
				return "redirect:/NotFound.in";
			} catch (Exception e) {
				LOGGER.error("Mail Box Page:", e);
				e.printStackTrace();
			}

		} catch (Exception e) {
			LOGGER.error("Mail Page:", e);
		}
		return "SentMailBox";
	}

	/** read mail box */
	@RequestMapping(value = "/readmailbox", method = RequestMethod.GET)
	public ModelAndView readmailbox(@ModelAttribute("command") @RequestParam("id") final String Mail_id_encode,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(Mail_id_encode);
			// Verify the decoded string
			// System.out.println(new String(decodedByteArray));
			Long Mail_id = Long.parseLong(new String(decodedByteArray));

			Mailbox mail = mailboxService.find_Read_Mailbox(Mail_id);
			model.put("mailbox", MBL.prepareReadEncodeDto(mail));
			List<MessagePropertyDto> beansMessage = null;
			if (mail != null) {
				beansMessage = new ArrayList<MessagePropertyDto>();

				MessagePropertyDto mailProperty = null;
				for (MessageProperty message : mail.getProperties()) {
					mailProperty = new MessagePropertyDto();

					mailProperty.setId(message.getId());

					mailProperty.setMAILBOX_FROM_USER_ID(message.getMAILBOX_FROM_USER_ID());
					mailProperty.setMAILBOX_FROM_USER_NAME(message.getMAILBOX_FROM_USER_NAME());
					mailProperty.setMAILBOX_FROM_EMAIL(message.getMAILBOX_FROM_EMAIL());

					mailProperty.setPROPERTY_MESSAGE(message.getPROPERTY_MESSAGE());
					mailProperty.setMAILBOX_SENT_ID(message.getMAILBOX_SENT_ID());

					/** Date diffrent Date */
					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					long diff = toDate.getTime() - message.getPROPERTY_DATE().getTime();// as
					Integer diffInDays = (int) TimeUnit.MILLISECONDS.toDays(diff);// given

					String diffenceDays = null;
					switch (diffInDays) {
					case 0:

						DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
						// System.out.println(outputFormat.format(mailDto.getMAIL_DATE()));
						diffenceDays = (outputFormat.format(message.getPROPERTY_DATE()) + " Today");

						break;

					default:
						String format = "EEE, MMM dd, yyyy h:mm a ";
						SimpleDateFormat sdf = new SimpleDateFormat(format);
						try {
							diffenceDays = sdf.format(message.getPROPERTY_DATE());
							// System.out.println(sdf.format(message.getPROPERTY_DATE()));
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						break;
					}

					mailProperty.setPROPERTY_DATE(diffenceDays);

					beansMessage.add(mailProperty);
				}

				/** The value code Update Seen in true */
				if (!mail.isMAIL_IS_SEEN()) {
					mailboxService.update_seen_True(Mail_id);
				}

			}

			model.put("message", beansMessage);

		} catch (Exception e) {
			LOGGER.error("readmailbox Page:", e);
		}
		return new ModelAndView("ReadMailBox", model);
	}

	/** this save mail in to own and also replay */
	@RequestMapping(value = "/replycompose", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse replycompose(@Valid final Mailbox mailbox,
			@RequestParam("OWN_REPLY_MAILBOX_ID") Long OWN_REPLY_MAILBOX_ID,
			@RequestParam("MAILBOX_SENTER_MAILBOX_ID") Long MAILBOX_SENTER_MAILBOX_ID,
			@RequestParam("MESSAGE_REPLY") String MESSAGE, final HttpServletRequest request) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName(); // get logged in username

		UserProfileDto User = userProfileService.findUserProfileByUser_email(userName);

		try {

			/**
			 * This create Own Reply code here we not store senter_mailID
			 */
			Mailbox replayOwnmail = new Mailbox(OWN_REPLY_MAILBOX_ID);

			MessageProperty message = sentMail_To_Another_MessageProperty(User, MESSAGE);

			message.setMAILBOX_SENT_ID(OWN_REPLY_MAILBOX_ID);
			message.setMailbox(replayOwnmail);
			mailboxService.insert_SENT_MeassageProperty(message);

			/** This create Reply code other */

			Mailbox replayOthermail = new Mailbox(MAILBOX_SENTER_MAILBOX_ID);

			MessageProperty sent_Other_message = sentMail_To_Another_MessageProperty(User, MESSAGE);

			/** MeassageProperty Save Replay mail_ID and Message */
			// sentMail_ID = savemailbox.getMAILBOX_ID();
			sent_Other_message.setMAILBOX_SENT_ID(OWN_REPLY_MAILBOX_ID);
			sent_Other_message.setMailbox(replayOthermail);
			mailboxService.insert_SENT_MeassageProperty(sent_Other_message);

			/** Mailbox Update Replay mail_ID and Message */
			mailboxService.update_replay_mailID_seen_false(OWN_REPLY_MAILBOX_ID, MAILBOX_SENTER_MAILBOX_ID);

		} catch (Exception e) {
			e.printStackTrace();
			throw new MailSentExistException();
		}

		return new GenericResponse("success");
	}

	/** this save mail in to own and also sent person */
	@RequestMapping(value = "/forwordcompose", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse forwordcompose(@Valid final Mailbox mailbox, @RequestParam("TO_FORWARD") String emailTo,
			@RequestParam("SUBJECT_FORWARD") String SUBJECT, @RequestParam("MESSAGE_FORWARD") String MESSAGE,
			final HttpServletRequest request) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName(); // get logged in username

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserProfileDto User = userProfileService.findUserProfileByUser_email(userName);

		Mailbox sent = sentMail_To_Own(User, SUBJECT, MESSAGE, emailTo);

		MessageProperty message = sentMail_To_Another_MessageProperty(User, MESSAGE);

		if (sent != null) {
			try {
				final Long sentMail_ID, MAIL_DOCUMENT_ID;

				
				sent.setCOMPANY_ID(userDetails.getCompany_id());

				/** This create Own Sent Code here we not store senter_mailID */
				Mailbox savemailbox = mailboxService.insert_SENT_MailBox(sent);
				sentMail_ID = savemailbox.getMAILBOX_ID();
				MAIL_DOCUMENT_ID = savemailbox.getMAIL_DOCUMENT_ID();
				boolean MAIL_DOCUMENT = savemailbox.isMAIL_DOCUMENT();
				message.setMAILBOX_SENT_ID(savemailbox.getMAILBOX_ID());
				message.setMailbox(savemailbox);
				mailboxService.insert_SENT_MeassageProperty(message);

				String[] emailId = emailTo.split(",");
				for (String sentPersonEmail : emailId) {

					Mailbox sent_Other_Inbox = sentMail_To_Another_INBOX(User, SUBJECT, MESSAGE, emailTo);
					MessageProperty sent_Other_message = sentMail_To_Another_MessageProperty(User, MESSAGE);

					/** this select email name */
					sent_Other_Inbox.setMAILBOX_TO_EMAIL(sentPersonEmail);

					/**
					 * sentMail_ID code in sender mail_id save in replay to ID
					 */
					sent_Other_Inbox.setMAILBOX_SENTER_MAILBOX_ID(sentMail_ID);

					sent_Other_Inbox.setMAIL_DOCUMENT(MAIL_DOCUMENT);
					sent_Other_Inbox.setMAIL_DOCUMENT_ID(MAIL_DOCUMENT_ID);
					
					sent_Other_Inbox.setCOMPANY_ID(userDetails.getCompany_id());

					/** This create Another Inbox Sent Code */
					Mailbox sentOthermailbox = mailboxService.insert_SENT_MailBox(sent_Other_Inbox);

					/** MeassageProperty Save mail_ID and Message */
					// sentMail_ID = savemailbox.getMAILBOX_ID();
					sent_Other_message.setMAILBOX_SENT_ID(sentMail_ID);
					sent_Other_message.setMailbox(sentOthermailbox);
					mailboxService.insert_SENT_MeassageProperty(sent_Other_message);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new MailSentExistException();
			}
		} else {
			throw new MailSentExistException();
		}

		return new GenericResponse("success");
	}

	/** The Value code id Trash Mail box Show */
	@RequestMapping(value = "/trashmailbox/{pageNumber}", method = RequestMethod.GET)
	public String trashmailbox(@PathVariable Integer pageNumber, Model model, final HttpServletRequest request) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userName = auth.getName();

			Page<Mailbox> page = mailboxService.get_Mailbox_Page_Mail(pageNumber, userName, "TRASH");
			try {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				model.addAttribute("InboxCount", page.getTotalElements());

				model.addAttribute("mail",
						MBL.prepareMailboxDto(mailboxService.List_Trash_Mailbox(pageNumber, userName)));

				/** count Inbox Both Read Unread mail */
				// model.put("InboxCount",
				// mailboxService.countTotal_Trash_MailBox(userName));

				String name = "TRASH";
				model.addAttribute("displayname", name);
			} catch (NullPointerException e) {
				return "redirect:/NotFound.in";
			} catch (Exception e) {
				LOGGER.error("Mail Box Page:", e);
				e.printStackTrace();
			}
		} catch (Exception e) {
			LOGGER.error("Mail Page:", e);
		}
		return "TrashMailBox";
	}

	/** The Value code id Trash Mail box Show */
	@RequestMapping(value = "/deleteinboxbox", method = RequestMethod.POST)
	public ModelAndView deleteinboxbox(@RequestParam("SelectMailId") final String[] select_all,
			@RequestParam("showReturn") final String showReturn, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			if (select_all != null) {

				String MailId = "";
				for (String string : select_all) {

					Base64.Decoder decoder = Base64.getDecoder();
					byte[] decodedByteArray = decoder.decode(string);
					// Verify the decoded string
					// System.out.println(new String(decodedByteArray));
					String MailId_encode = new String(decodedByteArray);

					MailId += MailId_encode + ",";
				}
				if (MailId != "") {
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					String userName = auth.getName();
					switch (showReturn) {
					case "INBOX":

						mailboxService.Trash_update_MailBox_TrashStatus(methodRemoveLastComma(MailId), userName);

						return new ModelAndView("redirect:/mailbox/1.in", model);

					case "SENT":

						mailboxService.Trash_update_MailBox_TrashStatus(methodRemoveLastComma(MailId), userName);

						return new ModelAndView("redirect:/sentmailbox/1", model);

					case "TRASH":

						mailboxService.Delete_update_MailBox_DeleteStatus(methodRemoveLastComma(MailId), userName);

						return new ModelAndView("redirect:/trashmailbox/1", model);

					default:

						mailboxService.Trash_update_MailBox_TrashStatus(methodRemoveLastComma(MailId), userName);

						return new ModelAndView("redirect:/mailbox/1.in", model);

					}
				}

			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (Exception e) {
			LOGGER.error("Mail Page:", e);
		}
		return new ModelAndView("redirect:/mailbox/1.in", model);
	}

	/** The Value code id Trash Mail box Show */
	@RequestMapping(value = "/deletereadMail", method = RequestMethod.POST)
	public ModelAndView deletereadMail(@RequestParam("SelectMailId") final String deletemail,
			@RequestParam("showReturn") final String showReturn, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			if (deletemail != null) {

				String MailId = "";
				Base64.Decoder decoder = Base64.getDecoder();
				byte[] decodedByteArray = decoder.decode(deletemail);
				// Verify the decoded string
				// System.out.println(new String(decodedByteArray));
				String MailId_encode = new String(decodedByteArray);

				MailId += MailId_encode + ",";

				if (MailId != "") {
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					String userName = auth.getName();
					switch (showReturn) {
					case "INBOX":

						mailboxService.Trash_update_MailBox_TrashStatus(methodRemoveLastComma(MailId), userName);

						return new ModelAndView("redirect:/mailbox/1.in", model);

					case "SENT":

						mailboxService.Trash_update_MailBox_TrashStatus(methodRemoveLastComma(MailId), userName);

						return new ModelAndView("redirect:/sentmailbox/1", model);

					case "TRASH":

						mailboxService.Delete_update_MailBox_DeleteStatus(methodRemoveLastComma(MailId), userName);

						return new ModelAndView("redirect:/trashmailbox/1", model);

					default:

						mailboxService.Trash_update_MailBox_TrashStatus(methodRemoveLastComma(MailId), userName);

						return new ModelAndView("redirect:/mailbox/1.in", model);

					}
				}

			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (Exception e) {
			LOGGER.error("Mail Page:", e);
		}
		return new ModelAndView("redirect:/mailbox/1.in", model);
	}

	/** The Value code id Flagged save true box Show */
	@RequestMapping(value = "/mailflagged", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse mailflagged(@RequestParam("id") final String MailId_encode,
			@RequestParam("fl") final String flagged, final HttpServletRequest request) {

		try {

			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(MailId_encode);
			// Verify the decoded string
			// System.out.println(new String(decodedByteArray));
			Long mail_id = Long.parseLong(new String(decodedByteArray));

			switch (flagged) {
			case "true":

				mailboxService.update_flagged_Importent_False(mail_id);

				break;
			case "false":

				mailboxService.update_flagged_Importent_True(mail_id);

				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MailSentExistException();
		}

		return new GenericResponse("success");
	}

	/** The Value code id Important Mail box Show */
	@RequestMapping(value = "/importantmailbox", method = RequestMethod.GET)
	public ModelAndView importantmailbox(@ModelAttribute("command") Mailbox mail, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userName = auth.getName();

			String name = "IMPORTANT";
			model.put("displayname", name);

			model.put("mail", MBL.prepareMailboxDto(mailboxService.List_Important_Mailbox(userName)));

			/** count Important Both Read Unread mail */
			model.put("InboxCount", mailboxService.countTotal_Important_MailBox(userName));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Mail Page:", e);
		}
		return new ModelAndView("NewMailBox", model);
	}

	/** The Value code id Important Mail box Show */
	@RequestMapping(value = "/searchmailbox", method = RequestMethod.POST)
	public ModelAndView searchmailbox(@RequestParam("searchmail") final String SearchMail,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userName = auth.getName();

			String name = "SEARCH";
			model.put("displayname", name);

			model.put("mail", MBL.prepareMailboxDto(mailboxService.List_Search_Mailbox(userName, SearchMail)));

			/** count Important Both Read Unread mail */
			model.put("InboxCount", 50);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Mail Page:", e);
		}
		return new ModelAndView("NewMailBox", model);
	}

	public Mailbox sentMail_To_Own(UserProfileDto User, String SUBJECT, String MESSAGE, String to) {

		// create obj on Company
		Mailbox mail = new Mailbox();

		mail.setMAILBOX_FROM_USER_ID(User.getUser_id());
		mail.setMAILBOX_FROM_EMAIL(User.getUser_email());
		mail.setMAILBOX_FROM_USER_NAME(User.getFirstName() + " " + User.getLastName());

		mail.setMAILBOX_NAMESPACE(SUBJECT);
		/** This own sent to email type */
		mail.setMAIL_MIME_TYPE("SENT");
		/** This own sent to email seen true */
		mail.setMAIL_IS_SEEN(true);
		/** This own sent to email Id */
		mail.setMAILBOX_TO_EMAIL(User.getUser_email());

		/** This own sent to User names Id */
		mail.setMAILBOX_TO_USER_NAME(to);

		/** This own sent to Document Id */
		mail.setMAIL_DOCUMENT_ID((long) 0);
		
		return mail;
	}

	public Mailbox sentMail_To_Another_INBOX(UserProfileDto User, String SUBJECT, String MESSAGE, String To) {

		// create obj on Company
		Mailbox mail = new Mailbox();

		mail.setMAILBOX_FROM_USER_ID(User.getUser_id());
		mail.setMAILBOX_FROM_EMAIL(User.getUser_email());
		mail.setMAILBOX_FROM_USER_NAME(User.getFirstName() + " " + User.getLastName());

		mail.setMAILBOX_NAMESPACE(SUBJECT);
		/** This other person sent to email type */
		mail.setMAIL_MIME_TYPE("INBOX");

		/** This Sent User To type */
		mail.setMAILBOX_TO_USER_NAME(To);

		return mail;
	}

	public MessageProperty sentMail_To_Another_MessageProperty(UserProfileDto User, String MESSAGE) {

		// create obj on Company
		MessageProperty mail = new MessageProperty();

		mail.setMAILBOX_FROM_USER_ID(User.getUser_id());
		mail.setMAILBOX_FROM_EMAIL(User.getUser_email());
		mail.setMAILBOX_FROM_USER_NAME(User.getFirstName() + " " + User.getLastName());

		mail.setPROPERTY_MESSAGE(MESSAGE);

		return mail;
	}

	/** get UserProfile Drop down List user search */
	@RequestMapping(value = "/getUserEmailId", method = RequestMethod.POST)
	public void getUserEmailId(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserProfileDto> user = new ArrayList<UserProfileDto>();
		List<UserProfileDto> userName = userProfileService.SearchUserEmail_id_and_Name(term, userDetails.getCompany_id());
		if (userName != null && !userName.isEmpty()) {
			for (UserProfileDto add : userName) {
				UserProfileDto wadd = new UserProfileDto();

				wadd.setUser_email(add.getUser_email());
				wadd.setFirstName(add.getFirstName());
				wadd.setLastName(add.getLastName());
				user.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(user));
	}

	public String methodRemoveLastComma(String str) {
		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/** Subscribe box show details in to the mailbox using the email */

	/** The Value code id Send Mail box Show */

	@RequestMapping(value = "/subscribebox", method = RequestMethod.GET)
	public ModelAndView subscribebox(@ModelAttribute("command") Mailbox mail, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date dateWithoutTime = sdf.parse(sdf.format(new java.util.Date()));

			model.put("Subscribe", MBL.prepareListof_Subscribe_List(
					SubscribeboxService.list_SubscribeBox_today(dateWithoutTime, userDetails.getEmail(), userDetails.getCompany_id())));

			UserProfileDto user = userProfileService.getUser_email_to_subscribe_Detils(userDetails.getEmail());

			if (user != null && user.getSubscribe() != null) {
				String temp = user.getSubscribe();
				if (temp.indexOf("SERVICE_REMINDER") != -1) {
					// System.out.println("there is 'SERVICE_REMINDER' in temp
					// string");

					model.put("Service", MBL.prepareListofServiceDto(
							ServiceReminderService.OverDue_DueSoon_Subcribe_ServiceReminder(toDate)));

				}
				if (temp.indexOf("RENEWAL_REMINDER") != -1) {
					// System.out.println("there is 'RENEWAL_REMINDER' in temp
					// string");

					DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
					model.put("renewal", RRBL.Only_Show_ListofRenewal(
							RenewalReminderService.TodayRenewalReminderList(ft.format(currentDate))));

				}

				if (temp.indexOf("DRIVER_REMINDER") != -1) {
					// System.out.println("there is 'RENEWAL_REMINDER' in temp
					// string");

					DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");

					model.put("driverReminder", DriverRem.prepareListDriverReminderBean(
							driverService.listof_ToDay_DL_Renewal(ft.format(currentDate))));

				}
			}
			/** count Inbox Both Read Unread mail */
			model.put("InboxCount", 5);

			String name = "SUBSCRIBE";
			model.put("displayname", name);

		} catch (Exception e) {
			System.err.println("exception : "+e);
			e.printStackTrace();
			LOGGER.error("SUBSCRIBE Page:", e);
		}
		return new ModelAndView("SubscribeBox", model);
	}

	/* Should be Download Driver Reminder document */
	@RequestMapping("/download/Mailbox/{MAILDOCID}")
	public String downloadReminder(@PathVariable("MAILDOCID") Long MAILDOCID, HttpServletResponse response) {

		try {
			if (MAILDOCID == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.MailboxDocument file = mailboxService.get_Mailbox_Document(MAILDOCID);

				if (file != null) {
					if (file.getMAIL_CONTENT() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getMAIL_FILENAME() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getMAIL_CONTENT_TYPE());
						response.getOutputStream().write(file.getMAIL_CONTENT());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}
	
	@RequestMapping("/download/MailboxDoc/{MAILDOCID}")
	public String downloadMailBoxDoc(@PathVariable("MAILDOCID") Long MAILDOCID, HttpServletResponse response) {

		try {
			if (MAILDOCID == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.MailboxDocument file = mailboxService.download_Mailbox_Document(MAILDOCID);

				if (file != null) {
					if (file.getMAIL_CONTENT() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getMAIL_FILENAME() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getMAIL_CONTENT_TYPE());
						response.getOutputStream().write(file.getMAIL_CONTENT());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}
}

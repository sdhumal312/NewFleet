
package org.fleetopgroup.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SMSConstant;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.UserAlertNOtificationsConstant;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.BatteryBL;
import org.fleetopgroup.persistence.bl.ClothInventoryBL;
import org.fleetopgroup.persistence.bl.InventoryTyreInvoiceBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.WorkOrdersBL;
import org.fleetopgroup.persistence.dao.DailyTripCountMailConfigRepository;
import org.fleetopgroup.persistence.dao.RenewalMailConfigurationRepository;
import org.fleetopgroup.persistence.dao.RenewalNotificationRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderRepository;
import org.fleetopgroup.persistence.dao.UserAlertNotificationsRepository;
import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.DriverReminderDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.dto.RenewalNotificationConfigurationDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryHistoryDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.model.BatteryHistory;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.model.DailyTripCountMailConfiguration;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.RenewalMailConfiguration;
import org.fleetopgroup.persistence.model.RenewalNotificationConfiguration;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.UserAlertNotifications;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.service.IEmailSenderService;
import org.fleetopgroup.persistence.serviceImpl.EmailSenderServiceImpl;
import org.fleetopgroup.persistence.serviceImpl.IBatteryHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDailyInspectionService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;



@Controller
@PropertySource({ "classpath:email.properties" })
public class EmailAlertQueuePOJO {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired private Environment env;
	

	SimpleDateFormat 			dateFormat 				= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 		    dateFormat2				= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
	
	public static final String 	DAY_START_TIME 			= " 00:00:00";
	SimpleDateFormat formatSQL 	 	 = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

	@Autowired IRenewalReminderService 			RenewalReminderService;
	@Autowired IEmailAlertQueueService 			emailAlertQueueService;
	@Autowired ISmsAlertQueueService 			smsAlertQueueService;
	@Autowired IServiceReminderService 			serviceReminderService;
	@Autowired ICompanyConfigurationService 	companyConfigurationService;
	@Autowired IUserProfileService 				userProfileService;
	@Autowired public JavaMailSender 			emailSender;	
	@Autowired ITripSheetService 				tripSheetService;
	@Autowired IServiceEntriesService			serviceEntriesService;
	@Autowired IWorkOrdersService			    workOrdersService;
	@Autowired IFuelService						fuelService;
	@Autowired IRenewalReminderService			renewalReminderService;	
	@Autowired ICompanyService					CompanyService;	
	@Autowired ICashBookService					cashBookService;
	@Autowired IIssuesService					issuesService;
	@Autowired IVehicleService					vehicleService;
	@Autowired IInventoryTyreService			tyreService;
	@Autowired IBatteryHistoryService			batteryService;
	@Autowired IUreaEntriesService				ureaService;
	@Autowired IClothInventoryService			clothInventoryService;
	@Autowired IDriverService 			    	driverService;
	@Autowired IEmailSenderService				emailSenderService;
	@Autowired IUreaEntriesService				ureaEntriesService;
	@Autowired
	private RenewalMailConfigurationRepository 						renewalReminderMail;
	@Autowired private IVehicleDailyInspectionService				vehicleDailyInspectionService;
	@Autowired private 	UserAlertNotificationsRepository			userAlertNotificationsRepository;
	@Autowired private      RenewalNotificationRepository           renewalNotificationRepository;   
	@Autowired private      DailyTripCountMailConfigRepository      dailyTripCountMailConfigRepository;
	
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ServiceReminderRepository 		serviceReminderRepository;
	
	RenewalReminderBL 	RRBL 			= new RenewalReminderBL();
	WorkOrdersBL		workOrdersBL	= new WorkOrdersBL();
	BatteryBL			batteryBL		= new BatteryBL();
	InventoryTyreInvoiceBL	ITIB		= new InventoryTyreInvoiceBL();
	ClothInventoryBL	CIB				= new ClothInventoryBL();
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	

	@Scheduled(cron = "0 12 08 * * *")
	public void doScheduledWork() {
		try {
			LOGGER.warn("Configure E-mail & SMS Started..");

			java.util.Date  currentDate = new java.util.Date();
			Timestamp		timestamp	= DateTimeUtility.geTimeStampFromDate(currentDate);

			configureEmailAndSms(timestamp);


			LOGGER.warn("Configure E-mail & SMS Ended..");
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}

	public void configureEmailAndSms(Date currentDate) {

		List<EmailAlertQueue>  	pendingList		= null;
		//String[]				mailSendTo 		= null;
		//CustomUserDetails		userDetails		= null;

		try {
			/**
			 * For EmailAlertQueue Table
			 */
			//userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pendingList				= emailAlertQueueService.getAllEmailAlertQueueDetails(currentDate);
			if(pendingList != null && !pendingList.isEmpty()) {

				for(EmailAlertQueue	emailAlertQueue : pendingList) {
					SimpleDateFormat	simpleDateFormat 	= new SimpleDateFormat("dd-MM-yyyy");
					ServiceReminder  	sr 					= serviceReminderRepository.getServiceReminder(emailAlertQueue.getTransactionId(), emailAlertQueue.getCompanyId());
				
					if(sr == null) {
						continue;
					}
					
					int 				serviceTypeId		= sr.getServiceTypeId();
					int 				serviceSubTypeId	= sr.getServiceSubTypeId();
					String 				serviceDate			= simpleDateFormat.format(emailAlertQueue.getServiceDate());
					Query query1 = entityManager.createQuery(
							"SELECT v.vehicle_registration, v.vid" + 
									" FROM Vehicle as v" +
									" WHERE v.vid="+sr.getVid()+" ");
					Object[] result1 = null;
					try {
						result1 = (Object[]) query1.getSingleResult();
					} catch (NoResultException nre) {
						// Ignore this because as per your logic this is ok!
					}
					VehicleDto list1 = null;
					if (result1 != null) {
						list1 = new VehicleDto();
						list1.setVehicle_registration((String) result1[0]);
						list1.setVid((Integer) result1[1]);
					}


					Query query2 = entityManager.createQuery(
							"SELECT c.company_name, c.company_id" + 
									" FROM Company as c" +
									" WHERE c.company_id="+sr.getCompanyId()+" ");
					Object[] result2 = null;
					try {
						result2 = (Object[]) query2.getSingleResult();
					} catch (NoResultException nre) {
						// Ignore this because as per your logic this is ok!
					}
					CompanyDto list2 = null;
					if (result2 != null) {
						list2 = new CompanyDto();
						list2.setCompany_name((String) result2[0]);
						list2.setCompany_id((Integer) result2[1]);
					}


					Query query = entityManager.createQuery(
							"SELECT j.Job_Type, j.Job_id, js.Job_Subid, js.Job_ROT" + 
									" FROM JobType as j" + 
									" INNER JOIN JobSubType as js on j.Job_id = js.Job_TypeId" + 
									" WHERE j.Job_id="+serviceTypeId+" and js.Job_Subid="+serviceSubTypeId+"");

					Object[] result = null;
					try {
						result = (Object[]) query.getSingleResult();
					} catch (NoResultException nre) {
						// Ignore this because as per your logic this is ok!
					}

					JobSubTypeDto list = null;
					if (result != null) {
						list = new JobSubTypeDto();
						list.setJob_Type((String) result[0]);
						list.setJob_TypeId((Integer) result[1]);
						list.setJob_Subid((Integer) result[2]);
						list.setJob_ROT((String) result[3]);

					}


					final String sendHTML_Email_PO = "<html>\r\n" + 
							"<head>\r\n" + 
							"</head>\r\n" + 
							"\r\n" + 
							"<body>\r\n" + 
							"<table bgcolor=\"#ff3333\" width=\"100%\">\r\n" + 
							"<tr>\r\n" + 
							"<td>\r\n" + 
							"<h2><font color=\"#ffffff\" ><b><center>"+list2.getCompany_name()+"</center></b></font></h2>\r\n" + 
							"</td>\r\n" + 
							"</tr>\r\n" + 
							"</table>\r\n" + 
							"<br>\r\n" + 
							"<center><b><p style=\"color:black;\"> Service Reminder No: "+emailAlertQueue.getTransactionNumber()+"</p> </b></center>\r\n" + 
							"<center><b><p style=\"color:black;\">"+list1.getVehicle_registration()+"</p></b></center>\r\n" + 
							"<br>\r\n" + 
							"<p style=\"color:black;\">We hope you've had a great time navigating our product. This is a gentle service Reminder for your Vehicle "+list1.getVehicle_registration()+".</p>\r\n" + 
							"<p style=\"color:black;\">Please find below  Service Reminder Details Pending :</p> \r\n" +
							"<table style=\"width:100%\" border=1px>\r\n" + 
							"  <tr>\r\n" + 
							"    <th><p style=\"color:black;\">Task</p></th>\r\n" + 
							"    <th><p style=\"color:black;\">Subtask</p></th> \r\n" + 
							"    <th><p style=\"color:black;\">Service Date</p></th>\r\n" + 
							"  </tr>\r\n" + 
							"  <tr>\r\n" + 
							"    <td><center><p style=\"color:black;\">"+list.getJob_Type()+"</p></center></td>\r\n" + 
							"    <td><center><p style=\"color:black;\">"+list.getJob_ROT()+"</p></center></td>\r\n" + 
							"    <td><center><p style=\"color:black;\">"+serviceDate+"</p></center></td>\r\n" + 
							"  </tr>\r\n" + 
							"  \r\n" + 
							"</table>\r\n" + 
							"<br>\r\n" + 
							"\r\n" + 
							"<table bgcolor=\"#ff3333\" width=\"100%\">\r\n" + 
							"<tr>\r\n" + 
							"<td>\r\n" + 
							"<h2><font color=\"#ffffff\" ><b> </b></font></h2>\r\n" + 
							"</td>\r\n" + 
							"</tr>\r\n" + 
							"</table>\r\n" + 
							"\r\n" + 
							"\r\n" + 
							"</body>\r\n" + 
							"</html>";


					final	String[]	mailSendTo 	 = emailAlertQueue.getEmailId().split(",");


					mailSender.send(new MimeMessagePreparator() {

						@Override
						public void prepare(MimeMessage mimeMessage) throws MailParseException  {
							try {
								MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
								//messageHelper.setTo("meet@ivgroup.in,yogendra@ivgroup.in,hemant@ivgroup.in");
								messageHelper.setTo(mailSendTo);
								//messageHelper.setCc("meet@ivgroup.in");
								messageHelper.setSubject("Service Reminder");
								//messageHelper.setReplyTo(env.getProperty("support.email"));

								/* sent html email text */
								messageHelper.setText(sendHTML_Email_PO, true);
							}catch (MessagingException ex) {
								throw new MailParseException(ex);
							}

						}

					});


				}

			}
		}	catch (Exception e) {
			LOGGER.error("Exception " + e);
			e.printStackTrace();
		}

	}
	
	
	
	//@Scheduled(cron = "0 16 08 * * *")
	public void sendSMS() {
		try {
			LOGGER.warn("Configure SMS Started..");

			java.util.Date currentDate = new java.util.Date();
			Timestamp	timestamp	= DateTimeUtility.geTimeStampFromDate(currentDate);

			//configureEmailAndSms(timestamp);

			sendingSMS(timestamp);

			LOGGER.warn("Configure SMS Ended..");
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}

	public void sendingSMS(Date currentDate) throws Exception {

		List<SmsAlertQueue>  pendingList   = null;
		String[]			 mobNumber     = null;
		String 				 content	   = null;

		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");	
			pendingList = smsAlertQueueService.getAllSmsAlertQueueService(currentDate);
			
			if(pendingList != null && !pendingList.isEmpty()) {
				
				for(SmsAlertQueue smsAlertQueue : pendingList) {
				
				
				ServiceReminder sr = serviceReminderRepository.getServiceReminder(smsAlertQueue.getTransactionId(), smsAlertQueue.getCompanyId());
				int serviceTypeId= sr.getServiceTypeId();
				int serviceSubTypeId= sr.getServiceSubTypeId();
				String serviceDate		= simpleDateFormat.format(smsAlertQueue.getServiceDate());
				
				Query query1 = entityManager.createQuery(
						"SELECT v.vehicle_registration, v.vid" + 
						" FROM Vehicle as v" +
						" WHERE v.vid="+sr.getVid()+" ");
				Object[] result1 = null;
				try {
					result1 = (Object[]) query1.getSingleResult();
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}
				VehicleDto list1 = null;
				if (result1 != null) {
					list1 = new VehicleDto();
					list1.setVehicle_registration((String) result1[0]);
					list1.setVid((Integer) result1[1]);
				}
				
				Query query = entityManager.createQuery(
						"SELECT j.Job_Type, j.Job_id, js.Job_Subid, js.Job_ROT" + 
						" FROM JobType as j" + 
						" INNER JOIN JobSubType as js on j.Job_id = js.Job_TypeId" + 
						" WHERE j.Job_id="+serviceTypeId+" and js.Job_Subid="+serviceSubTypeId+"");
				
				Object[] result = null;
				try {
					result = (Object[]) query.getSingleResult();
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}
			
			
					JobSubTypeDto list = null;
					if (result != null) {
						list = new JobSubTypeDto();
						list.setJob_Type((String) result[0]);
						list.setJob_TypeId((Integer) result[1]);
						list.setJob_Subid((Integer) result[2]);
						list.setJob_ROT((String) result[3]);

					}

					String content1 = "Hi,We hope you've had a great time navigating our product. This is a gentle service Reminder for your Vehicle "+list1.getVehicle_registration()+"."
							+ " Service "+smsAlertQueue.getTransactionNumber()+" for "+list.getJob_Type()+" - "+list.getJob_ROT()+" needs to be performed by "+serviceDate+". Thank You ";


					mobNumber = smsAlertQueue.getMobileNumber().split(",");
					//content = smsAlertQueue.getContent();
					content1 = content1.trim();
					content1 = content1.replaceAll("\\s", "%20");
					for(int i=0; i<mobNumber.length; i++) {
						HttpURLConnection urlconnection	= null;
						String retval = "";
						String postData = "";
						String msgUrl  = "http://api-alerts.solutionsinfini.com/v3/?method=sms&api_key=A2936335d5bf59f706e47b96f2bdcd82f&to="+mobNumber[i]+"&sender=IVCRGO&message="+content1+"";
						postData += URLEncoder.encode(msgUrl,"UTF-8");
						URL url = new URL(msgUrl);
						urlconnection = (HttpURLConnection) url.openConnection();

						urlconnection.setRequestMethod("POST");
						urlconnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
						urlconnection.setDoOutput(true);
						OutputStreamWriter out = new OutputStreamWriter(urlconnection.getOutputStream());
						out.write(postData);
						out.close();
						BufferedReader in = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
						String decodedString;
						while ((decodedString = in.readLine()) != null) {
							retval += decodedString;
						}
						in.close();
					}

				}
			}
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}
		

	/***************RenewalReminder Mail first Day of the current Month***********/

//	@Scheduled(cron = "0 08 08 * * *")
	public void renewalReminderMonthStartDayE_Mail() {
		ValueObject										valueObject					= null;
		String											startDateOfMonth			= null;
		String											endDateOfMonth				= null;
		String											todaysDate					= null;
		java.util.Date 									currentDate					= null;
		SimpleDateFormat								sdf							= null;
		try {
			LOGGER.warn("Configure E-mail ...");
			sdf 							= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
			currentDate 					= new java.util.Date();
			todaysDate 						= sdf.format(currentDate);

			valueObject						= DateTimeUtility.getCurrentMonthStartAndEndDate();
			startDateOfMonth				= valueObject.getString(DateTimeUtility.START_DATE_OF_MONTH);
			endDateOfMonth					= valueObject.getString(DateTimeUtility.END_DATE_OF_MONTH);
			if(todaysDate.equals(startDateOfMonth)) {
				configureRenewalReminderEmailOnFirstDayOfMonth(startDateOfMonth, endDateOfMonth);
			}

			LOGGER.warn("Configure E-mail & SMS Ended..");
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}

	public void configureRenewalReminderEmailOnFirstDayOfMonth(String startDateOfMonth, String endDateOfMonth) {

		List<RenewalMailConfiguration>  				pendingList					= null;
		List<RenewalReminderDto>						renewalReminderDto			= null;
		long 											renewalNumber				= 0;
		Date 											renewalToDate				= null;
		double 											renewalAmount				= 0.0;
		StringBuffer									sendHTML_Email_PO = new StringBuffer();

		try {
			pendingList					= RenewalReminderService.getAllEmailRenewalReminder();
			if(pendingList != null && !pendingList.isEmpty()) {
				for(RenewalMailConfiguration renewalMailConfiguration : pendingList) {
					renewalReminderDto=RenewalReminderService.get_listRenewalReminderMonthWise(startDateOfMonth , endDateOfMonth ,renewalMailConfiguration.getCompanyId());
					Query query2 = entityManager.createQuery(
							"SELECT c.company_name, c.company_id" + 
									" FROM Company as c" +
									" WHERE c.company_id="+renewalMailConfiguration.getCompanyId()+" ");
					Object[] result2 = null;
					try {
						result2 = (Object[]) query2.getSingleResult();
					} catch (NoResultException nre) {
						// Ignore this because as per your logic this is ok!
					}
					CompanyDto list2 = null;
					if (result2 != null) {
						list2 = new CompanyDto();
						list2.setCompany_name((String) result2[0]);
						list2.setCompany_id((Integer) result2[1]);
					}

					sendHTML_Email_PO.append("<html>\r\n" + 
							"<head>\r\n" + 
							"</head>\r\n" + 
							"\r\n" + 
							"<body>\r\n" + 
							"<table bgcolor=\"#ff3333\" width=\"100%\">\r\n" + 
							"<tr>\r\n" + 
							"<td>\r\n" + 
							"<h2><font color=\"#ffffff\" ><b><center>"+list2.getCompany_name()+"</center></b></font></h2>\r\n" + 
							"</td>\r\n" + 
							"</tr>\r\n" + 
							"</table>\r\n" + 
							"<br>\r\n" + 
							"<br>\r\n" + 
							"<p style=\"color:black;\">We hope you've had a great time navigating our product. This is a gentle Renewal Reminder for your Vehicle </p>\r\n" + 
							"<p style=\"color:black;\">Please find below  Renewal Reminder List For This Month  :</p> \r\n" +
							"<table style=\"width:100%\" border=1px>\r\n" + 
							"  <tr>\r\n" + 
							"    <th><p style=\"color:black;\">Renewal Reminder Number</p></th>\r\n" + 
							"    <th><p style=\"color:black;\">Vehicle</p></th>\r\n" + 
							"    <th><p style=\"color:black;\">Vehicle Status</p></th>\r\n" + 
							"    <th><p style=\"color:black;\">Renewal Date</p></th> \r\n" + 
							"    <th><p style=\"color:black;\">Renewal Amount</p></th>\r\n" + 
							"  </tr>\r\n");

					
					if(renewalReminderDto != null && !renewalReminderDto.isEmpty()) {	
						for(RenewalReminderDto dto:renewalReminderDto) {
							renewalNumber 		= dto.getRenewal_R_Number();
							renewalToDate		= dto.getRenewal_To_Date();
							renewalAmount		= dto.getRenewal_Amount();
							sendHTML_Email_PO.append("<tr>\r\n" +
									"    <td><center><p style=\"color:black;\">RR-"+renewalNumber+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+dto.getVehicle_registration()+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+dto.getVehicleStatus()+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+renewalToDate+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+renewalAmount+"</p></center></td>\r\n" + 
									"  </tr>\r\n");
						}
					}
					sendHTML_Email_PO.append("  \r\n </table>\r\n" + 
							"<br>\r\n" + 
							"\r\n" + 
							"<table bgcolor=\"#ff3333\" width=\"100%\">\r\n" + 
							"<tr>\r\n" + 
							"<td>\r\n" + 
							"<h2><font color=\"#ffffff\" ><b> </b></font></h2>\r\n" + 
							"</td>\r\n" + 
							"</tr>\r\n" + 
							"</table>\r\n" + 
							"\r\n" + 
							"\r\n" + 
							"</body>\r\n" + 
							"</html>");

					final	String[]	mailSendTo 	 = renewalMailConfiguration.getEmailIds().split(",");;

					mailSender.send(new MimeMessagePreparator() {

						@Override
						public void prepare(MimeMessage mimeMessage) throws MailParseException  {
							try {
								MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
								messageHelper.setTo(mailSendTo);
								//messageHelper.setCc("meet@ivgroup.in");
								messageHelper.setSubject("Renewal Reminder");
								//messageHelper.setReplyTo(env.getProperty("support.email"));

								/*sent html email text */
								messageHelper.setText(sendHTML_Email_PO.toString(), true);
							}catch (MessagingException ex) {
								throw new MailParseException(ex);
							}

						}

					});
				}
			}
		}catch (Exception e) {
			LOGGER.error("Exception " + e);
			e.printStackTrace();
		}

	}
	/*********This Will Call On The Last Day Of The Month***********/

	//@Scheduled(cron = "0 00 0,21 * * *")
	public void renewalReminderMonthEndDateE_Mail() {
		ValueObject										valueObject					= null;
		String											startDateOfMonth			= null;
		String											endDateOfMonth				= null;
		String											todaysDate					= null;
		java.util.Date 									currentDate					= null;
		SimpleDateFormat								sdf							= null;
		try {
			LOGGER.warn("Configure E-mail ...");
			sdf 							= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
			currentDate 					= new java.util.Date();
			todaysDate 						= sdf.format(currentDate);

			valueObject						= DateTimeUtility.getCurrentMonthStartAndEndDate();
			startDateOfMonth				= valueObject.getString(DateTimeUtility.START_DATE_OF_MONTH);
			endDateOfMonth					= valueObject.getString(DateTimeUtility.END_DATE_OF_MONTH);
			if(todaysDate.equals(endDateOfMonth)) {
				configureRenewalReminderEmailOnLastDayOfMonth(startDateOfMonth, endDateOfMonth);
			}
			LOGGER.warn("Configure E-mail & SMS Ended..");
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}
	
	
	//@Scheduled(cron = "0 24 08 * * *")
	public void sendEmailToAssigneInServiceReminder() {
		try {
			LOGGER.warn("Configure E-mail & SMS Started..");
			
			java.util.Date currentDate = new java.util.Date();
			Timestamp	timestamp	= DateTimeUtility.geTimeStampFromDate(currentDate);
			 
			configureEmailAndSmsToAssigneInServiceReminder(timestamp);
			
			
			LOGGER.warn("Configure E-mail & SMS Ended..");
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}
	
	public void configureEmailAndSmsToAssigneInServiceReminder(Date currentDate) {

		List<ServiceReminderDto>  		  pendingList						= null;
		List<String> 					  mailList							= null;	
		List<String> 					  smsList							= null;	
		ValueObject						  valueOutObject 					= null;
		ValueObject						  valueInObjectEmail 				= null;
		ValueObject						  valueInObjectSms 					= null;
		ValueObject						  statusValueobj 					= null;
		String[]			 			  mobNumber     					= null;
		HashMap<String, Object> 	 	  configuration	            		= null;
		HashMap<Long, UserProfileDto> 	  dtosMap 							= null;
		UserProfileDto					  userProfileDto					= null;
		String					 		  sendSMSforServRemindByCompId		= null;
		String					 		  working_key						= null;
		String					 		  masking_Key						= null;
		boolean					 		  anyProblem						= true;
		boolean					 		  sendSMS							= false;

		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

			configuration				  = companyConfigurationService.getCompanyConfiguration(0, PropertyFileConstants.SMS_CONFIG);
			working_key 				  = (String) configuration.get(SMSConstant.SMS_WORKING_KEY);
			masking_Key 				  = (String) configuration.get(SMSConstant.SMS_MASKING_KEY);
			sendSMSforServRemindByCompId  = configuration.get(SMSConstant.SEND_SMS_FOR_SERV_REMID_BYCOMPANYID)+"";
			sendSMS  					  = (boolean) configuration.get(SMSConstant.SEND_SMS_FOR_SERVICE_REMINDER);


			valueOutObject = new ValueObject();
			String todaysDate = simpleDateFormat.format(currentDate);

			pendingList	= serviceReminderService.getServiceReminderByThresholdDate(todaysDate,sendSMSforServRemindByCompId);
			if(pendingList != null && !pendingList.isEmpty()) {

				for(ServiceReminderDto	servRemind : pendingList) {
					valueOutObject.put("servRemind", servRemind);

					valueInObjectEmail 					 = emailAlertQueueService.bodyContentForEmail(valueOutObject);	
					final String sendHTML_Email_Date     = (String) valueInObjectEmail.get("sendHTML_Email_Date");
					final String sendHTML_Email_Odometer = (String) valueInObjectEmail.get("sendHTML_Email_Odometer");

					if(servRemind.getService_subScribedUserId() != null) {
						String subScribedUserId[] = servRemind.getService_subScribedUserId().split(",");	
						dtosMap = userProfileService.getUserEmailIdFromUserId(servRemind.getService_subScribedUserId());

						mailList = new ArrayList<String>();
						smsList = new ArrayList<String>();
						for (int j = 0; j < subScribedUserId.length; j++) {
							userProfileDto	= dtosMap.get(Long.parseLong(subScribedUserId[j]));
							mailList.add(userProfileDto.getPersonal_email());
							smsList.add(userProfileDto.getMobile_number());
						}

						final	String	 emailTo 	 = mailList.toString().replaceAll("(\\[|\\])", "");
						final	String[] emailSendTo = emailTo.split(",");

						mailSender.send(new MimeMessagePreparator() {

							@Override
							public void prepare(MimeMessage mimeMessage) throws MailParseException  {
								try {
									MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
									messageHelper.setTo(emailSendTo);
									messageHelper.setSubject("Service Reminder");
									if(servRemind.getVehicle_currentOdometer() >= servRemind.getMeter_servicethreshold_odometer() && servRemind.getVehicle_currentOdometer() <= servRemind.getMeter_serviceodometer()) {
										messageHelper.setText(sendHTML_Email_Odometer, true);
									}else {
										messageHelper.setText(sendHTML_Email_Date, true);	
									}

								}catch (MessagingException ex) {
									throw new MailParseException(ex);
								}

							}

						});

						// SMS Code Starts Here	
						if(sendSMS) {

							valueInObjectSms = smsAlertQueueService.bodyContentForSMS(valueOutObject);	
							final String contentThresholdDate = 	(String) valueInObjectSms.get("contentThresholdDate");
							final String contentThresholdOdometer = (String) valueInObjectSms.get("contentThresholdOdometer");

							final	String	 smsTo 	     = smsList.toString().replaceAll("(\\[|\\])", "");
							mobNumber = smsTo.split(",");			
							for(int i=0; i<mobNumber.length; i++) {

								if(servRemind.getVehicle_currentOdometer() >= servRemind.getMeter_servicethreshold_odometer() && servRemind.getVehicle_currentOdometer() <= servRemind.getMeter_serviceodometer()) {
									statusValueobj = smsAlertQueueService.sendServiceReminderSMS(working_key, masking_Key, mobNumber[i],contentThresholdOdometer);
								} else {
									statusValueobj = smsAlertQueueService.sendServiceReminderSMS(working_key, masking_Key, mobNumber[i],contentThresholdDate);
								}

								if(statusValueobj.containsKey("retval")) {
									String retval	= statusValueobj.getString("retval");
									String removeBrackets = retval.replaceAll("\\{","");
									String[] statusarr = removeBrackets.split(",");

									for (int y = 0; y < statusarr.length; y++) {
										if(statusarr[y].startsWith("\"status\"")) {
											if(statusarr[y].contains("OK") || statusarr[y].contains("ok") || statusarr[y].contains("Success") || statusarr[y].contains("success")) {
												anyProblem = false;
											}
										}
									}
								}
							}
						}

					}

				}

			}

		} catch (Exception e) {
			LOGGER.error("Exception " + e);
			e.printStackTrace();
		}
	}
	

	public void configureRenewalReminderEmailOnLastDayOfMonth(String startDateOfMonth, String endDateOfMonth) {
		
		List<RenewalMailConfiguration>  				pendingList					= null;
		List<RenewalReminderDto>						renewalReminderDto			= null;
		long 											renewalNumber				= 0;
		Date 											renewalToDate				= null;
		double 											renewalAmount				= 0.0;
		StringBuffer									sendHTML_Email_PO = new StringBuffer();

		try {
			pendingList					= RenewalReminderService.getAllEmailRenewalReminder();
			if(pendingList != null && !pendingList.isEmpty()) {
				for(RenewalMailConfiguration renewalMailConfiguration : pendingList) {
					renewalReminderDto=RenewalReminderService.get_listRenewalReminderMonthWise(startDateOfMonth , endDateOfMonth ,renewalMailConfiguration.getCompanyId());
					Query query2 = entityManager.createQuery(
							"SELECT c.company_name, c.company_id" + 
									" FROM Company as c" +
									" WHERE c.company_id="+renewalMailConfiguration.getCompanyId()+" ");
					Object[] result2 = null;
					try {
						result2 = (Object[]) query2.getSingleResult();
					} catch (NoResultException nre) {
						// Ignore this because as per your logic this is ok!
					}
					CompanyDto list2 = null;
					if (result2 != null) {
						list2 = new CompanyDto();
						list2.setCompany_name((String) result2[0]);
						list2.setCompany_id((Integer) result2[1]);
					}

					sendHTML_Email_PO.append("<html>\r\n" + 
							"<head>\r\n" + 
							"</head>\r\n" + 
							"\r\n" + 
							"<body>\r\n" + 
							"<table bgcolor=\"#ff3333\" width=\"100%\">\r\n" + 
							"<tr>\r\n" + 
							"<td>\r\n" + 
							"<h2><font color=\"#ffffff\" ><b><center>"+list2.getCompany_name()+"</center></b></font></h2>\r\n" + 
							"</td>\r\n" + 
							"</tr>\r\n" + 
							"</table>\r\n" + 
							"<br>\r\n" + 
							"<br>\r\n" + 
							"<p style=\"color:black;\">We hope you've had a great time navigating our product. This is a gentle Renewal Reminder for your Vehicle </p>\r\n" + 
							"<p style=\"color:black;\">The Following Renewal Are No longer Availabe to Renew </p>\r\n" + 
							"<p style=\"color:black;\">Please find below Outdated-Renewal-Reminder List For This Month :</p> \r\n" +
							"<table style=\"width:100%\" border=1px>\r\n" + 
							"  <tr>\r\n" + 
							"    <th><p style=\"color:black;\">Renewal Reminder Number</p></th>\r\n" + 
							"    <th><p style=\"color:black;\">Vehicle</p></th>\r\n" + 
							"    <th><p style=\"color:black;\">Vehicle Status</p></th>\r\n" + 
							"    <th><p style=\"color:black;\">Renewal Date</p></th> \r\n" + 
							"    <th><p style=\"color:black;\">Renewal Amount</p></th>\r\n" + 
							"  </tr>\r\n");
					if(renewalReminderDto != null && !renewalReminderDto.isEmpty()) {
						for(RenewalReminderDto dto:renewalReminderDto) {
							renewalNumber 		= dto.getRenewal_R_Number();
							renewalToDate		= dto.getRenewal_To_Date();
							renewalAmount		= dto.getRenewal_Amount();
							sendHTML_Email_PO.append("<tr>\r\n" +
									"    <td><center><p style=\"color:black;\">"+renewalNumber+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+dto.getVehicle_registration()+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+dto.getVehicleStatus()+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+renewalToDate+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+renewalAmount+"</p></center></td>\r\n" + 
									"  </tr>\r\n");
						}
					}

					sendHTML_Email_PO.append("  \r\n </table>\r\n" + 
							"<br>\r\n" + 
							"\r\n" + 
							"<table bgcolor=\"#ff3333\" width=\"100%\">\r\n" + 
							"<tr>\r\n" + 
							"<td>\r\n" + 
							"<h2><font color=\"#ffffff\" ><b> </b></font></h2>\r\n" + 
							"</td>\r\n" + 
							"</tr>\r\n" + 
							"</table>\r\n" + 
							"\r\n" + 
							"\r\n" + 
							"</body>\r\n" + 
							"</html>");

					final	String[]	mailSendTo 	 = renewalMailConfiguration.getEmailIds().split(",");

					mailSender.send(new MimeMessagePreparator() {

						@Override
						public void prepare(MimeMessage mimeMessage) throws MailParseException  {
							try {
								MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
								messageHelper.setTo(mailSendTo);
								messageHelper.setSubject("Renewal Reminder");

								/*sent html email text */
								messageHelper.setText(sendHTML_Email_PO.toString(), true);
							}catch (MessagingException ex) {
								throw new MailParseException(ex);
							}

						}

					});
				}
			}
		}catch (Exception e) {
			LOGGER.error("Exception " + e);
			e.printStackTrace();
		}

	}
	
			
		@Scheduled(cron = "0 08 08 * * *") 		
		public void doScheduledDailyWorkStatus() {
			try {
				LOGGER.warn("Configure E-mail Started..");

				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DATE, -1);
				Date currentDate = cal.getTime();
				configureDailyWorkStatusEmail(currentDate);

				LOGGER.warn("Configure E-mail Ended..");
			} catch (Exception e) {
				LOGGER.debug("Auto Update Halt to Active", e);
				e.printStackTrace();
			}
		}
		
		
		public void configureDailyWorkStatusEmail(Date  currentDate) {		
			List<RenewalMailConfiguration>  	pendingList											= null;
			SimpleDateFormat 					sdf 												= null;
			SimpleDateFormat 					sdf1 												= null;
			String								todaysDate											= null;
			String								emailDate											= null;
			String								startDate											= null;
			String								endDate												= null;
			ValueObject						  	valueOutObject 										= null;
			ValueObject						  	valInObjectEmailBody								= null;
			TripSheetDto                        tripSheetCountsOldestOpenFlavorOne                  = null;
			Long 								renewalReminderOverDueCounts						= null;
			long								dayDiff												= 0;
			TripSheetDto 						tripSheetCountsClosed							    = null;
			TripSheetDto						highyestTripDetail									= null;
			long 								tripSheetCountsMissedClosing						= 0;
			long 								tripSheetCountsInOpenState							= 0;
			String 								sevenDaysBackDate									= null;
			String 								eightDaysBackDate									= null;
			String 								fifteenDaysBackDate									= null;
			String 								forteenDaysBackDate									= null;
			String 								todaysStrDate										= null;
			try {
				
				
				sdf = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
				sdf1 = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
				
				sevenDaysBackDate	= LocalDate.now().minusDays(7)+"";
				eightDaysBackDate	= LocalDate.now().minusDays(8)+"";
				forteenDaysBackDate	= LocalDate.now().minusDays(14)+"";
				fifteenDaysBackDate	= LocalDate.now().minusDays(15)+"";
				todaysStrDate		= LocalDate.now()+"";
				
				todaysDate = sdf.format(currentDate);
				emailDate = sdf1.format(currentDate);
				startDate = todaysDate+" "+DateTimeUtility.DAY_START_TIME;
				endDate   = todaysDate+" "+DateTimeUtility.DAY_END_TIME;
				
				pendingList										= RenewalReminderService.getAllEmailTripSheetDailyWorkStatus();	
				
				if(pendingList != null && !pendingList.isEmpty()) {	
					
					HashMap<Integer, Long> seCompleteCount			= serviceEntriesService.getALLCompletedServiceEntriesByDate(startDate, endDate);
					HashMap<Integer, Long> serviceEntriesCounts  	= serviceEntriesService.getALLEmailServiceEntriesDailyWorkStatus(startDate, endDate);
					HashMap<Integer, Long> allOPenSEData		  	= serviceEntriesService.getALLOpenServiceEntriesByDate();
					HashMap<Integer, Long> sevenDaysSEData		  	= serviceEntriesService.getALLOpenSevenDaysServiceEntriesByDate(sevenDaysBackDate + DateTimeUtility.DAY_START_TIME, todaysStrDate+ DateTimeUtility.DAY_END_TIME );
					HashMap<Integer, Long> fifteenDaysSEData		= serviceEntriesService.getALLOpenSevenDaysServiceEntriesByDate(forteenDaysBackDate + DateTimeUtility.DAY_START_TIME, eightDaysBackDate+ DateTimeUtility.DAY_END_TIME );
					HashMap<Integer, Long> monthSEData		  		= serviceEntriesService.getALLOpen15MoreDaysServiceEntriesByDate(fifteenDaysBackDate+ DateTimeUtility.DAY_END_TIME );
					
					HashMap<Integer, Long>  serviceReminder		   = serviceReminderService.getALLEmailServiceReminderDailyWorkStatus(startDate, endDate);
					HashMap<Integer, Long>  serviceReminderOD	   = serviceReminderService.getALLServiceReminderOverDueCOunt(todaysStrDate + DateTimeUtility.DAY_END_TIME );
					HashMap<Integer, Long>  serviceReminder7OD	   = serviceReminderService.getALLServiceReminderOverDueCOunt(sevenDaysBackDate + DateTimeUtility.DAY_START_TIME, todaysStrDate+ DateTimeUtility.DAY_END_TIME );
					HashMap<Integer, Long>  serviceReminder15OD	   = serviceReminderService.getALLServiceReminderOverDueCOunt(forteenDaysBackDate + DateTimeUtility.DAY_START_TIME, eightDaysBackDate+ DateTimeUtility.DAY_END_TIME );
					HashMap<Integer, Long>  serviceReminder30OD	   = serviceReminderService.getALLServiceReminderOverDueMore15COunt(fifteenDaysBackDate+ DateTimeUtility.DAY_END_TIME);
					
					 HashMap<Integer, Long>  workOrderCounts 	   = workOrdersService.getALLEmailWorkOrderDailyWorkStatus(startDate, endDate);
					 HashMap<Integer, Long>  AllOpenworkOrder 	   = workOrdersService.getALLOpenWorkOrder();
					 HashMap<Integer, Long>  ONhOLDWO 	   		   = workOrdersService.getALLOnHoldWorkOrder();
					 HashMap<Integer, Long>  completedWO   		   = workOrdersService.getALLCOMPLETEDWorkOrder(startDate, endDate);
					 HashMap<Integer, Long>  woOpenFrom7Days 	   = workOrdersService.getALLOpenWorkOrderFromDateRange(sevenDaysBackDate + DateTimeUtility.DAY_START_TIME, todaysStrDate+ DateTimeUtility.DAY_END_TIME);
					 HashMap<Integer, Long>  woOpenFrom15Days 	   = workOrdersService.getALLOpenWorkOrderFromDateRange(forteenDaysBackDate + DateTimeUtility.DAY_START_TIME, eightDaysBackDate+ DateTimeUtility.DAY_END_TIME);
					 HashMap<Integer, Long>  woOpenFrom30Days 	   = workOrdersService.getALLOpenWorkOrderFrom15Days(fifteenDaysBackDate+ DateTimeUtility.DAY_END_TIME);

					 
					 HashMap<Integer, Long>  issuesCreatedHM 	   = issuesService.issuesCreatedOnDate(startDate, endDate);
					 HashMap<Integer, Long>  issuesResolvedHM 	   = issuesService.issuesResolvedOnDate(startDate, endDate);
					 HashMap<Integer, Long>  issuesAllOpenHM 	   = issuesService.issuesAllopenData();
					 HashMap<Integer, Long>  issuesOpen7DaysHM 	   = issuesService.issuesOpenWithinDays(sevenDaysBackDate + DateTimeUtility.DAY_START_TIME, todaysStrDate+ DateTimeUtility.DAY_END_TIME);
					 HashMap<Integer, Long>  issuesOpen15DaysHM    = issuesService.issuesOpenWithinDays(forteenDaysBackDate + DateTimeUtility.DAY_START_TIME, eightDaysBackDate+ DateTimeUtility.DAY_END_TIME);
					 HashMap<Integer, Long>  issuesOpen30DaysHM    = issuesService.issuesOpen15MoreDays(fifteenDaysBackDate+ DateTimeUtility.DAY_END_TIME);
					
					 HashMap<Integer, Long>  vehicleCountHM	= vehicleService.getActiveVehicleCountMap();
					 HashMap<Integer, Long>  fuelCountHM	= fuelService.getFuelEntriesCountHMOnCreated(startDate, endDate);
					 HashMap<Integer, Long>  ureaCountHM	= ureaEntriesService.getUreaEntriesCountHMOnCreated(startDate, endDate);
					 
					 HashMap<Integer, Long>   renewalCounts 	   		= renewalReminderService.getALLEmailRenewalRemindersDailyWorkStatus(startDate, endDate);
					 HashMap<Integer, Long>   renewalOdCounts 	   		= renewalReminderService.getAllOverDueRenewalReminders(todaysStrDate + DateTimeUtility.DAY_START_TIME);
					 HashMap<Integer, Long>   renewalOd7Counts 	   		= renewalReminderService.getAllOverDueRenewalReminders(sevenDaysBackDate + DateTimeUtility.DAY_END_TIME, todaysStrDate+ DateTimeUtility.DAY_START_TIME);
					 HashMap<Integer, Long>   renewalOd15Counts 	   	= renewalReminderService.getAllOverDueRenewalReminders(forteenDaysBackDate + DateTimeUtility.DAY_END_TIME, sevenDaysBackDate+ DateTimeUtility.DAY_START_TIME);
					 HashMap<Integer, Long>   renewalOd15PlusCounts 	= renewalReminderService.getAllOverDueRenewalReminders(forteenDaysBackDate+ DateTimeUtility.DAY_START_TIME);
					 
					 
					
					for(RenewalMailConfiguration renewalMailConfiguration : pendingList) {
							
							valueOutObject = new ValueObject();
							
							valueOutObject.put("seCompleteCount", seCompleteCount);
							valueOutObject.put("serviceEntriesCounts", serviceEntriesCounts);
							valueOutObject.put("sevenDaysSEData", sevenDaysSEData);
							valueOutObject.put("fifteenDaysSEData", fifteenDaysSEData);
							valueOutObject.put("allOPenSEData", allOPenSEData);
							valueOutObject.put("monthSEData", monthSEData);
							valueOutObject.put("serviceReminder", serviceReminder);
							valueOutObject.put("serviceReminderOD", serviceReminderOD);
							valueOutObject.put("serviceReminder7OD", serviceReminder7OD);
							valueOutObject.put("serviceReminder15OD", serviceReminder15OD);
							valueOutObject.put("serviceReminder30OD", serviceReminder30OD);
							
							valueOutObject.put("workOrderCounts", workOrderCounts);
							valueOutObject.put("AllOpenworkOrder", AllOpenworkOrder);
							valueOutObject.put("ONhOLDWO", ONhOLDWO);
							valueOutObject.put("completedWO", completedWO);
							valueOutObject.put("woOpenFrom7Days", woOpenFrom7Days);
							valueOutObject.put("woOpenFrom15Days", woOpenFrom15Days);
							valueOutObject.put("woOpenFrom30Days",woOpenFrom30Days);
							
							valueOutObject.put("issuesCreatedHM", issuesCreatedHM);
							valueOutObject.put("issuesResolvedHM", issuesResolvedHM);
							valueOutObject.put("issuesAllOpenHM", issuesAllOpenHM);
							valueOutObject.put("issuesOpen7DaysHM", issuesOpen7DaysHM);
							valueOutObject.put("issuesOpen15DaysHM", issuesOpen15DaysHM);
							valueOutObject.put("issuesOpen30DaysHM", issuesOpen30DaysHM);
							
							valueOutObject.put("renewalMailConfiguration", renewalMailConfiguration);
							HashMap<Integer, Long>  tripCountHM	   = tripSheetService.getALLEmailTripDailyWorkStatusHM(startDate, endDate,renewalMailConfiguration.getCompanyId());
							tripSheetCountsClosed	   = tripSheetService.getTripClosed(startDate, endDate,renewalMailConfiguration.getCompanyId());
							highyestTripDetail		   = tripSheetService.getHighyestKMRunDetails(startDate, endDate,renewalMailConfiguration.getCompanyId());
							
							int flavor = companyConfigurationService.getTripSheetFlavor(renewalMailConfiguration.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
							if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
								tripSheetCountsMissedClosing 		= tripSheetService.getTripMissedClosing(startDate, endDate,renewalMailConfiguration.getCompanyId());					
								renewalReminderOverDueCounts        = RRBL.Only_Show_ListofRenewalOverdue(renewalReminderService.getRenewalReminderOverDue(renewalMailConfiguration.getCreatedById(), renewalMailConfiguration.getCompanyId()));
								tripSheetCountsOldestOpenFlavorOne  = tripSheetService.getTripOldestOpenFlavorOne(renewalMailConfiguration.getCompanyId(), startDate);
								if(tripSheetCountsOldestOpenFlavorOne != null)
									dayDiff =	DateTimeUtility.getDayDiffBetweenTwoDates(new Timestamp(tripSheetCountsOldestOpenFlavorOne.getTripOpenDateOn().getTime()),new Timestamp(currentDate.getTime()));
								tripSheetCountsInOpenState			= tripSheetService.getTripInOpenState(startDate, endDate,renewalMailConfiguration.getCompanyId());
							}
							
							
							
							
							valueOutObject.put("vehicleCountHM", vehicleCountHM);
							
							
							valueOutObject.put("highyestTripDetail", highyestTripDetail);
							valueOutObject.put("tripCountHM", tripCountHM);
							valueOutObject.put("serviceEntriesCounts", serviceEntriesCounts);
							valueOutObject.put("workOrderCounts", workOrderCounts);
							valueOutObject.put("fuelCountHM", fuelCountHM);
							valueOutObject.put("ureaCountHM", ureaCountHM);
							valueOutObject.put("renewalCounts", renewalCounts);
							valueOutObject.put("renewalOdCounts", renewalOdCounts);
							valueOutObject.put("renewalOd7Counts", renewalOd7Counts);
							valueOutObject.put("renewalOd15Counts", renewalOd15Counts);
							valueOutObject.put("renewalOd15PlusCounts", renewalOd15PlusCounts);
							valueOutObject.put("emailDate", emailDate);						
							valueOutObject.put("tripSheetCountsClosed", tripSheetCountsClosed);
							valueOutObject.put("tripSheetCountsMissedClosing", tripSheetCountsMissedClosing);											
							valueOutObject.put("dayDiff", dayDiff);
							if(tripSheetCountsOldestOpenFlavorOne != null)
								valueOutObject.put("tripSheetNumber", tripSheetCountsOldestOpenFlavorOne.getTripSheetNumber());						
							valueOutObject.put("renewalReminderOverDueCounts", renewalReminderOverDueCounts);
							valueOutObject.put("tripSheetCountsInOpenState", tripSheetCountsInOpenState);
							
							
							valInObjectEmailBody = renewalReminderService.configureDailyWorkStatusEmailBody(valueOutObject);
							
							if(valInObjectEmailBody != null) {
								final String flavourOneMessage     	  = (String) valInObjectEmailBody.get("flavourOneMessage");
								final String withoutFlavorMessage     = (String) valInObjectEmailBody.get("withoutFlavorMessage");
								
								final	String[]	mailSendTo 	 = renewalMailConfiguration.getEmailIds().split(",");
								
								
								try {
									mailSender.send(new MimeMessagePreparator() { 
										
										@Override
										public void prepare(MimeMessage mimeMessage) throws Exception  {
											try {
												MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
												messageHelper.setTo(mailSendTo);  
												
												messageHelper.setSubject("Fleetop➕ :  FleetOP Daily work status");
												if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
													messageHelper.setText(flavourOneMessage, true);
												} else {
													messageHelper.setText(withoutFlavorMessage, true);
												}
												
											}catch (Exception e) {
												throw e;
											} 
										}
										
									});
								}catch(Exception e) {
									System.err.println("Exception : "+ e);
									continue;
								}
								
							}
					}

				}
				
			}   catch (Exception e) {
				LOGGER.error("Exception " + e);
				e.printStackTrace();
			}finally {
				pendingList											= null;
				sdf 												= null;
				sdf1 												= null;
				todaysDate											= null;
				emailDate											= null;
				startDate											= null;
				endDate												= null;
				valueOutObject 										= null;
				valInObjectEmailBody								= null;
				
				sevenDaysBackDate									= null;
				eightDaysBackDate									= null;
				fifteenDaysBackDate									= null;
				forteenDaysBackDate									= null;
				todaysStrDate										= null;
			}

		}	
	
		//Email System for Manager Start
		@Scheduled(cron = "0 01 08 * * *")		
		public void doScheduledDailyWorkStatusForManagers() {
			SimpleDateFormat 					sdf 						= null;
			SimpleDateFormat 					sdf1 						= null;
			String								todaysDate					= null;
			String								emailDate					= null;
			String								startDate					= null;
			String								endDate						= null;
			try {
				LOGGER.warn("Configure E-mail Started..");
				

				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DATE, -1);
				Date currentDate = cal.getTime();			
				
				sdf = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
				sdf1 = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
				todaysDate = sdf.format(currentDate);
				emailDate = sdf1.format(currentDate);
				startDate = todaysDate+" "+EmailAlertQueuePOJO.DAY_START_TIME;
				endDate   = todaysDate+" "+DateTimeUtility.DAY_END_TIME;
				
			String[] emails = 	env.getProperty("managers.email").split(",");
				
				configureDailyWorkStatusEmailForManagers(startDate, endDate, emailDate, "Fleetop➕ :  FleetOP Daily work status", emails);
				LOGGER.warn("Configure E-mail Ended..");
			} catch (Exception e) {
				LOGGER.debug("Auto Update Halt to Active", e);
				e.printStackTrace();
			}
		}
		
		@Scheduled(cron = "0 15 00 1 * ?") 	
		public void doScheduledMonthlyEmailForAccounts() {
			String								emailDate					= null;
			String								startDate					= null;
			String								endDate						= null;
			try {
				LOGGER.warn("Configure E-mail Started..");
				
				Timestamp	firstDate = DateTimeUtility.getFirstDayOfPreviousMonth(DateTimeUtility.getCurrentTimeStamp());
				Timestamp	lastDate = DateTimeUtility.getLastDayOfMonth(firstDate);
				
				
				emailDate = ""+ DateTimeUtility.getDateFromTimeStamp(firstDate, DateTimeUtility.DD_MM_YYYY)+ " to "+DateTimeUtility.getDateFromTimeStamp(lastDate, DateTimeUtility.DD_MM_YYYY);
				startDate = DateTimeUtility.getDateFromTimeStamp(firstDate, DateTimeUtility.YYYY_MM_DD)+" "+EmailAlertQueuePOJO.DAY_START_TIME;
				endDate   = DateTimeUtility.getDateFromTimeStamp(lastDate, DateTimeUtility.YYYY_MM_DD)+" "+DateTimeUtility.DAY_END_TIME;
				
				String address = env.getProperty("managers.email")+",alpesh@ivgroup.in,aarti@ivgroup.in";
				
				String[] emails = 	address.split(",");
				
				configureDailyWorkStatusEmailForManagers(startDate, endDate, emailDate,"Fleetop➕ :  FleetOP Monthly Work Status Of Clients", emails );
				
				LOGGER.warn("Configure E-mail Ended..");
			} catch (Exception e) {
				LOGGER.debug("Auto Update Halt to Active", e);
				e.printStackTrace();
			}
		}
		
		public void configureDailyWorkStatusEmailForManagers(String startDate, String endDate, String emailDate, String subject, String [] emails) {		
			
			ValueObject						  	valueOutObject 				= null;
			ValueObject						  	valInObjectEmailBody		= null;
			CompanyDto							tripSheetCountObj			= null;
			List<CompanyDto>					serviceEntriesCounts		= null;
			List<CompanyDto>					workOrderCounts				= null;			
			List<CompanyDto>					fuelEntriesCounts			= null;
			List<CompanyDto>					renewalCounts				= null;
			List<CompanyDto>					serviceReminderCounts		= null;
			List<CompanyDto>					tripSheetCounts				= null;
			List<CompanyDto>					finalListOfCount			= null;
			Map<Integer,CompanyDto> 			finalMapOfCount				= null;
			CompanyDto							companyDto					= null;
			List<Company>						companyPendingList			= null;
			
			
			try {
				
				valueOutObject = new ValueObject();
				
					 
				
				serviceEntriesCounts 	= serviceEntriesService.getALLEmailServiceEntriesDailyWorkForManagers(startDate, endDate);
				workOrderCounts			= workOrdersService.getALLEmailWorkOrderDailyWorkForManagers(startDate, endDate);				
				fuelEntriesCounts		= fuelService.getALLEmailFuelEntriesDailyWorkForManagers(startDate, endDate);
				renewalCounts			= renewalReminderService.getALLEmailRenewalRemindersDailyWorkForManagers(startDate, endDate);
				serviceReminderCounts	= serviceReminderService.getALLEmailServiceReminderDailyWorkForManagers(startDate, endDate); 
				
				
				companyPendingList = CompanyService.findAll();
				tripSheetCounts=new  ArrayList<CompanyDto>();
				for(Company comp :companyPendingList ) {
					
					Long  tripSheetCount	   = tripSheetService.getALLEmailTripDailyWorkStatus(startDate, endDate,comp.getCompany_id());
					
					tripSheetCountObj	= new CompanyDto();
					
					tripSheetCountObj.setTripSheetCount(tripSheetCount);
					tripSheetCountObj.setCompany_id(comp.getCompany_id());
					tripSheetCountObj.setCompany_name(comp.getCompany_name());
					
					if(tripSheetCountObj != null)
					tripSheetCounts.add(tripSheetCountObj);
				}
				
				
				finalListOfCount	= new ArrayList<CompanyDto>();
				if(serviceEntriesCounts!=null) {
					finalListOfCount.addAll(serviceEntriesCounts);
				}
				if(workOrderCounts!=null) {
					finalListOfCount.addAll(workOrderCounts);
				}
				if(fuelEntriesCounts!=null) {
					finalListOfCount.addAll(fuelEntriesCounts);
				}
				if(renewalCounts!=null) {
					finalListOfCount.addAll(renewalCounts);
				}
				if(serviceReminderCounts!=null) {
					finalListOfCount.addAll(serviceReminderCounts);
				}
				if(tripSheetCounts!=null){
					finalListOfCount.addAll(tripSheetCounts);
				}
				
				
				finalMapOfCount	= new HashMap<Integer, CompanyDto>();
				for(int i = 0; i < finalListOfCount.size(); i++) {
					
					if(finalMapOfCount!= null) {
					if(finalMapOfCount.containsKey(finalListOfCount.get(i).getCompany_id())) {
						companyDto			= finalMapOfCount.get(finalListOfCount.get(i).getCompany_id());
						
						companyDto.setServiceEntriesCount(companyDto.getServiceEntriesCount() + finalListOfCount.get(i).getServiceEntriesCount());
						companyDto.setWorkOrderCount(companyDto.getWorkOrderCount() + finalListOfCount.get(i).getWorkOrderCount());
						companyDto.setFuelEntriesCount(companyDto.getFuelEntriesCount() + finalListOfCount.get(i).getFuelEntriesCount());
						companyDto.setRenewalReminderCount(companyDto.getRenewalReminderCount() + finalListOfCount.get(i).getRenewalReminderCount());
						companyDto.setServiceReminderCount(companyDto.getServiceReminderCount() + finalListOfCount.get(i).getServiceReminderCount());						
						companyDto.setTripSheetCount(companyDto.getTripSheetCount()+finalListOfCount.get(i).getTripSheetCount());
						companyDto.setCompany_name(finalListOfCount.get(i).getCompany_name() != null ? finalListOfCount.get(i).getCompany_name() : companyDto.getCompany_name());
						
					} else {
						finalMapOfCount.put(finalListOfCount.get(i).getCompany_id(), finalListOfCount.get(i));
					}
					}
				}
				
				
				valueOutObject.put("finalMapOfCount", finalMapOfCount);
				valueOutObject.put("emailDate", emailDate);
								
				
				valInObjectEmailBody = CompanyService.configureDailyWorkStatusEmailBody(valueOutObject);
				final String DailyWorkStatusEmailBody_Manager  = (String) valInObjectEmailBody.get("DailyWorkStatusEmailBody_Manager");


						mailSender.send(new MimeMessagePreparator() {
							
								@Override
								public void prepare(MimeMessage mimeMessage) throws MailParseException  {
									try {
										MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
										messageHelper.setTo(emails);										
										messageHelper.setSubject(subject);
										messageHelper.setText(DailyWorkStatusEmailBody_Manager, true);
										messageHelper.setBcc(env.getProperty("developer.email"));
	
									}catch (Exception e) {
										LOGGER.error("Exception " + e);
										e.printStackTrace();
									}
	
								}

						  });

				}
			   catch (Exception e) {
				LOGGER.error("Exception " + e);
				e.printStackTrace();
			}

		}	
		
		//part Consumption 
		@Scheduled(cron = "0 12 08 * * *") 		
		public void doScheduledPartConsumtion() {
			
			try {
				LOGGER.warn("Configure E-mail Started..");

				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DATE, -1);
				Date currentDate = cal.getTime();
				configurePartConsumtionEmail(currentDate);

				LOGGER.warn("Configure E-mail Ended..");
			} catch (Exception e) {
				LOGGER.debug("Auto Update Halt to Active", e);
				e.printStackTrace();
			}
		}
		
		public void configurePartConsumtionEmail(Date  currentDate) {		
			
			Map<Integer, List<WorkOrdersTasksToPartsDto>>			companyWiseParConsumedHM				= null;
			List<WorkOrdersTasksToPartsDto>							companywiseParConsumedList				= null;
			Map<Long, List<WorkOrdersTasksToPartsDto>>				partWiseParConsumedHM					= null;
			Map<Integer, List<BatteryHistoryDto>>					companyWiseBatteryAssignmentHM			= null;
			List<BatteryHistoryDto>									companywiseBatteryAssignmentList		= null;
			Map<Integer, List<InventoryTyreHistoryDto>>				companyWiseTyreAssignmentHM				= null;
			List<InventoryTyreHistoryDto>							companywiseTyreAssignmentList			= null;
			Map<Integer, List<VehicleClothInventoryHistoryDto>>		companyWiseUpholsteryAssignmentHM		= null;
			List<VehicleClothInventoryHistoryDto>					companywiseUpholsteryAssignmentList		= null;
			Map<Integer, Map<Long,VehicleClothInventoryHistoryDto>>	vehiclewiseUpholsteryAssignmentHM		= null;
			Map<Integer, List<UreaEntriesDto>>						companyWiseUreaConsumptionHM			= null;
			List<UreaEntriesDto>									companywiseUreaConsumptionList			= null;
			List<RenewalMailConfiguration>  						pendingList								= null;
			SimpleDateFormat 										sdf 									= null;
			SimpleDateFormat 										sdf1 									= null;
			ValueObject						  						valueOutObject 							= null;
			ValueObject						  						valInObjectEmailBody					= null;
			String													todaysDate								= null;
			String													startDate								= null;
			String													endDate									= null;
			String													emailDate								= null;
			boolean													flag									= false;
			
			try {
				
				
				sdf 					= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
				sdf1 					= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
				todaysDate 				= sdf.format(currentDate);
				emailDate 				= sdf1.format(currentDate);	 
				startDate 				= todaysDate+" "+EmailAlertQueuePOJO.DAY_START_TIME;
				endDate   				= todaysDate+" "+DateTimeUtility.DAY_END_TIME;
				pendingList				= RenewalReminderService.getAllEmailTripSheetDailyWorkStatus();	
				
				if(pendingList != null && !pendingList.isEmpty()) {	
					
					companyWiseParConsumedHM 			= workOrdersService.getAllPartsInWorkorders(startDate,endDate);
					companyWiseTyreAssignmentHM			= tyreService.getAllTyreAssignmentDetails(startDate,endDate);
					companyWiseBatteryAssignmentHM		= batteryService.getAllBatteryAssignmentDetails(startDate,endDate);
					companyWiseUreaConsumptionHM		= ureaService.getAllUreaEntriesDetails(startDate,endDate);
					companyWiseUpholsteryAssignmentHM	= clothInventoryService.getAllUpholsteryDetails(startDate,endDate);
					
					for(RenewalMailConfiguration renewalMailConfiguration : pendingList) {
						valueOutObject 			= new ValueObject();
						flag									= false;
						if(companyWiseParConsumedHM != null && companyWiseParConsumedHM.get(renewalMailConfiguration.getCompanyId()) != null) {
							companywiseParConsumedList 	= companyWiseParConsumedHM.get(renewalMailConfiguration.getCompanyId());
						}
						if(companyWiseTyreAssignmentHM != null && companyWiseTyreAssignmentHM.get(renewalMailConfiguration.getCompanyId()) != null) {
							companywiseTyreAssignmentList	= companyWiseTyreAssignmentHM.get(renewalMailConfiguration.getCompanyId());
						}
						if(companyWiseBatteryAssignmentHM  != null &&  companyWiseBatteryAssignmentHM.get(renewalMailConfiguration.getCompanyId()) != null) {
							companywiseBatteryAssignmentList	= companyWiseBatteryAssignmentHM.get(renewalMailConfiguration.getCompanyId());
						}
						if(companyWiseUreaConsumptionHM  != null && companyWiseUreaConsumptionHM.get(renewalMailConfiguration.getCompanyId()) != null) {
							companywiseUreaConsumptionList	= companyWiseUreaConsumptionHM.get(renewalMailConfiguration.getCompanyId());
						}
						if(companyWiseUpholsteryAssignmentHM != null &&  companyWiseUpholsteryAssignmentHM.get(renewalMailConfiguration.getCompanyId()) != null) {
							companywiseUpholsteryAssignmentList	= companyWiseUpholsteryAssignmentHM.get(renewalMailConfiguration.getCompanyId());
						}
						
						
						
						if(companywiseUpholsteryAssignmentList != null) {
							vehiclewiseUpholsteryAssignmentHM = CIB.getUpholsteryAssignHM(companywiseUpholsteryAssignmentList);
						}
						
						Query query2 = entityManager.createQuery(
								"SELECT c.company_name, c.company_id" + 
										" FROM Company as c" +
										" WHERE c.company_id="+renewalMailConfiguration.getCompanyId()+" ");
						Object[] result2 = null;
						try {
							result2 = (Object[]) query2.getSingleResult();
						} catch (NoResultException nre) {
							// Ignore this because as per your logic this is ok!
						}
						CompanyDto list2 = null;
						if (result2 != null) {
							list2 = new CompanyDto();
							list2.setCompany_name((String) result2[0]);
							list2.setCompany_id((Integer) result2[1]);
						}
						
						valueOutObject.put("companyName", list2.getCompany_name());
						valueOutObject.put("emailDate", emailDate);
						
						
						if(companywiseParConsumedList != null && companywiseParConsumedList.size() >0) {
							valueOutObject.put("companywiseParConsumedList", companywiseParConsumedList);
							flag = true;
						}
						if(companywiseTyreAssignmentList != null  && companywiseTyreAssignmentList.size() >0)	{
							valueOutObject.put("companywiseTyreAssignmentList", companywiseTyreAssignmentList);
							flag = true;
						}
						if(companywiseBatteryAssignmentList != null  && companywiseBatteryAssignmentList.size() >0) {
							valueOutObject.put("companywiseBatteryAssignmentList", companywiseBatteryAssignmentList);
							flag = true;
						}
						if(companywiseUreaConsumptionList != null  && companywiseUreaConsumptionList.size() >0) {
							valueOutObject.put("companywiseUreaConsumptionList", companywiseUreaConsumptionList);
							flag = true;
						}
						if(companywiseUpholsteryAssignmentList != null  && companywiseUpholsteryAssignmentList.size() >0) {
							valueOutObject.put("vehiclewiseUpholsteryAssignmentHM", vehiclewiseUpholsteryAssignmentHM);
							flag = true;
						}
							valInObjectEmailBody = workOrdersService.configureDailyPartConsumedEmailBody(valueOutObject);
							
							final String DailyPartCousumed_EmailBody   	= (String) valInObjectEmailBody.get("DailyPartCousumed_EmailBody");
						
							
							if(companywiseParConsumedList != null)
								companywiseParConsumedList.clear();
							
							if(companywiseTyreAssignmentList != null)
								companywiseTyreAssignmentList.clear();
							
							if(companywiseBatteryAssignmentList != null)
								companywiseBatteryAssignmentList.clear();
							
							if(companywiseUreaConsumptionList != null)	
								companywiseUreaConsumptionList.clear();
							
							if(companywiseUpholsteryAssignmentList != null)
								companywiseUpholsteryAssignmentList.clear();
							
						if(flag != false) {
								
							final	String[]	mailSendTo 	 			= renewalMailConfiguration.getEmailIds().split(",");
							String subject = "Fleetop➕ :Daily Consumption Details";
							emailSenderService.sendFleetopPlusMail(mailSendTo, subject, DailyPartCousumed_EmailBody.toString());
						
						}else {
							
							System.err.println("No mail For "+renewalMailConfiguration.getCompanyId());
						}
					}
				}
				
			}
		   catch (Exception e) {
			LOGGER.error("Exception " + e);
			e.printStackTrace();
		}
		finally {
			companyWiseParConsumedHM				= null; 
			companywiseParConsumedList				= null; 
			partWiseParConsumedHM					= null; 
			companyWiseBatteryAssignmentHM			= null; 
			companywiseBatteryAssignmentList		= null; 
			companyWiseTyreAssignmentHM				= null; 
			companywiseTyreAssignmentList			= null; 
			//newVehicleClothHistoryBL				= null; 
			companyWiseUpholsteryAssignmentHM		= null; 
			companywiseUpholsteryAssignmentList		= null; 
			vehiclewiseUpholsteryAssignmentHM		= null; 
			companyWiseUreaConsumptionHM			= null; 
			companywiseUreaConsumptionList			= null; 
			pendingList								= null; 
			sdf 									= null; 
			sdf1 									= null; 
			valueOutObject 							= null; 
			valInObjectEmailBody					= null; 
			
		}
	}	

			
		
		//Renewal Reminder On Start Day Of Every Month 
		
		@Scheduled(cron = "0 15 08 1 * ?")    
		public void sendReminderOnStartDayOfMonth() {
			
			ValueObject										valueObject					= null;
			String											startDateOfMonth			= null;
			String											endDateOfMonth				= null;
			
			try {
				
				LOGGER.warn("Configure E-mail Started..");

				valueObject						= DateTimeUtility.getCurrentMonthStartAndEndDate();
				startDateOfMonth				= valueObject.getString(DateTimeUtility.START_DATE_OF_MONTH);
				endDateOfMonth					= valueObject.getString(DateTimeUtility.END_DATE_OF_MONTH);
				
				configureReminderOnStartDayOfMonth(startDateOfMonth,endDateOfMonth);   
				
				LOGGER.warn("Configure E-mail Ended..");
				
			} catch (Exception e) {
				
				LOGGER.debug("Auto Update Halt to Active", e);
				e.printStackTrace();
			}
		}
		
		
		private void configureReminderOnStartDayOfMonth(String startDateOfMonth,String endDateOfMonth) {
			
			List<RenewalMailConfiguration>  				pendingList					= null;
			List<RenewalReminderDto>						renewalReminderDto			= null; 
			List<ServiceReminderDto>                        serviceReminderDto			= null;
			ValueObject						  				valueOutObject 				= null;
			ValueObject						  	          valInObjectEmailBody		    = null;
			List<DriverReminderDto>							driverReminderDto			= null;
			try {
				
				valueOutObject = new ValueObject();
				
				pendingList					= RenewalReminderService.getAllEmailTripSheetDailyWorkStatus();
				
				
				if(pendingList != null && !pendingList.isEmpty()) {
				
					for(RenewalMailConfiguration renewalMailConfiguration : pendingList) {
						
						renewalReminderDto = RenewalReminderService.get_listRenewalReminderMonthWise(startDateOfMonth+""+EmailAlertQueuePOJO.DAY_START_TIME , endDateOfMonth+""+DateTimeUtility.DAY_END_TIME,renewalMailConfiguration.getCompanyId());
						
						serviceReminderDto = serviceReminderService.getListServiceReminderMonthWiseDueSoon(startDateOfMonth+""+EmailAlertQueuePOJO.DAY_START_TIME  , endDateOfMonth+""+DateTimeUtility.DAY_END_TIME ,renewalMailConfiguration.getCompanyId());
						
						driverReminderDto = driverService.getListDriverReminderMonthWise(startDateOfMonth+""+EmailAlertQueuePOJO.DAY_START_TIME , endDateOfMonth+""+DateTimeUtility.DAY_END_TIME ,renewalMailConfiguration.getCompanyId());
						
						Query query2 = entityManager.createQuery(
								"SELECT c.company_name, c.company_id" + 
										" FROM Company as c" +
										" WHERE c.company_id="+renewalMailConfiguration.getCompanyId()+" ");
						
						Object[] result2 = null;
						
						try {
							result2 = (Object[]) query2.getSingleResult();
						} catch (NoResultException nre) {
							// Ignore this because as per your logic this is ok!
						}
						
						CompanyDto list2 = null;
						if (result2 != null) {
							list2 = new CompanyDto();
							list2.setCompany_name((String) result2[0]);
							list2.setCompany_id((Integer) result2[1]);
						}
						
						
						if(renewalReminderDto != null) {
							valueOutObject.put("renewalReminderDto", renewalReminderDto);
						}
						
						if(serviceReminderDto != null) {
							valueOutObject.put("serviceReminderDto", serviceReminderDto);
						}
						if(driverReminderDto != null) {
							valueOutObject.put("driverReminderDto", driverReminderDto);
						}
						
						if(startDateOfMonth != null) {
							valueOutObject.put("startDateOfMonth", startDateOfMonth);
						}
						
						if(endDateOfMonth != null) {
							valueOutObject.put("endDateOfMonth", endDateOfMonth);
						}
						
						if(list2 != null) {
							valueOutObject.put("companyInfo", list2);
						}
							
						valInObjectEmailBody = serviceReminderService.configureReminderOnStartDayOfMonthEmailBody(valueOutObject);
						
						if(renewalReminderDto != null ) {
							renewalReminderDto.clear();
						}
						if(serviceReminderDto != null ) {
							serviceReminderDto.clear();
						}
						if(driverReminderDto != null ) {
							driverReminderDto.clear();
						}
						
						final String ReminderOnStartDayOfEveryMonth = (String) valInObjectEmailBody.get("ReminderOnStartDayOfEveryMonth"); 
						final	String[]	mailSendTo 	 = renewalMailConfiguration.getEmailIds().split(",");
						
						mailSender.send(new MimeMessagePreparator() {
							
							@Override
							public void prepare(MimeMessage mimeMessage) throws MailParseException  {
								try {
									
									MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
									messageHelper.setTo(mailSendTo);  
									messageHelper.setSubject(" Fleetop➕ : Reminder Of Month ");
									messageHelper.setText(ReminderOnStartDayOfEveryMonth.toString(), true);

								}catch (Exception e) {
									LOGGER.error("Exception " + e);
									e.printStackTrace();
								}

							}

					  });
						
				    }
				}
			}
			catch (Exception e) {
				LOGGER.error("Exception " + e);
				e.printStackTrace();
			}
		}
		
		@Scheduled(cron = "0 45 08 1 * ?") 
		public void doScheduledMonthlyActiveVehicleCountForManagers() {
			try {
				LOGGER.warn("Configure E-mail Started..");

				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DATE, -1);
				Date currentDate = cal.getTime();
				configureMonthlyActiveVehicleCountEmailForManagers(currentDate);
				LOGGER.warn("Configure E-mail Ended..");
			} catch (Exception e) {
				LOGGER.debug("Auto Update Halt to Active", e);
				e.printStackTrace();
			}
		}
		
		@SuppressWarnings({ "unused", "unchecked" })
		public void configureMonthlyActiveVehicleCountEmailForManagers(Date  currentDate) {	
			ValueObject						valueOutObject				= null;
			ValueObject						emailBody		  		  	= null;
			SimpleDateFormat				simpleDateFormat			= null;
			SimpleDateFormat				simpleDateFormat2			= null;
			String							emailDate					= null;
			String 							startOfMonth				= "";
			String 							endOfMonth					= "";
			List<Company>					companyList					= null;
			Fuel							fuelEntriesDetails			= null;
			TripSheet						tripsheetDetails			= null;
			ServiceEntries					serviceEntriesDetails		= null;
			WorkOrders						workOrderDetails			= null;
			long							renewalDetails				= 0;
			BatteryHistory					battery						= null;
			InventoryTyreHistory			tyre						= null;
			long							serviceReminderDetails		= 0;
			CompanyDto						companyDto					= null;
			ArrayList<CompanyDto> 			activeGroupList				= null;
			String							companyIds					= "";
			List<CompanyDto> 				DtosActive 					= null;
			List<CompanyDto> 				DtosInactive 				= null;
			List<CompanyDto> 				DtosSold 					= null;
			List<CompanyDto> 				finalListOfVehicle 			= null;
			Map<Integer,CompanyDto> 		finalMapOfVehicle 			= null;
			CompanyDto						finalCompanyDto				= null;
			
			try {
				
				activeGroupList 		= new ArrayList<CompanyDto>();
				simpleDateFormat 		= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
				simpleDateFormat2 		= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
				valueOutObject 			= new ValueObject();
				
				valueOutObject 			= DateTimeUtility.getPreviousMonthStartAndEndDate();
				
				startOfMonth 			= valueOutObject.getString("startOfMonth");
				endOfMonth 				= valueOutObject.getString("endOfMonth");
				emailDate 				= simpleDateFormat.format(currentDate);
				
				companyList = CompanyService.findAll();
				
				
			String startDate[] 	= startOfMonth.split("-");
			String endDate[] 	= endOfMonth.split("-");
				
			String sDate 	= startDate[0];
			String sMonth 	= startDate[1];
			String sYear 	= startDate[2];
			
			
			String eDate 	= endDate[0];
			String eMonth 	= endDate[1];
			String eYear 	= endDate[2];
			
			String sStartDate = sYear+"-"+sMonth+"-"+sDate;
			String sEndDate	  = eYear+"-"+eMonth+"-"+eDate;
				
			String finalStartDate 		= sStartDate+" "+DateTimeUtility.DAY_END_TIME;
			String finalEndDate 		= sEndDate+" "+DateTimeUtility.DAY_END_TIME;
			String finalMonthAndYear  	= DateTimeUtility.getMonthStringByDate(DateTimeUtility.getTimeStamp(startOfMonth))+"-"+sYear;
			
				for(Company company : companyList ) {
					companyDto = new CompanyDto();
					
					fuelEntriesDetails		= fuelService.getFuelCreatedBetweenDates(finalStartDate, finalEndDate,company.getCompany_id());
					if(fuelEntriesDetails != null) {
						companyDto.setCompany_id(company.getCompany_id());
						activeGroupList.add(companyDto);
						continue;
					}
					
					tripsheetDetails		= tripSheetService.getTripSheetCreatedBetweenDates(finalStartDate, finalEndDate,company.getCompany_id());
					if(tripsheetDetails != null) {
						companyDto.setCompany_id(company.getCompany_id());
						activeGroupList.add(companyDto);
						continue;
					}
					
					workOrderDetails		= workOrdersService.getWorkordersCreatedBetweenDates(finalStartDate, finalEndDate,company.getCompany_id());				
					
					if(workOrderDetails != null) {
						companyDto.setCompany_id(company.getCompany_id());
						activeGroupList.add(companyDto);
						continue;
					}
					
					serviceEntriesDetails 	= serviceEntriesService.getServiceEntriesCreatedBetweenDates(finalStartDate, finalEndDate,company.getCompany_id());
					
					if(serviceEntriesDetails != null) {
						companyDto.setCompany_id(company.getCompany_id());
						activeGroupList.add(companyDto);
						continue;
					}
					
					battery			= batteryService.getBatteryMountCreatedBetweenDates(finalStartDate, finalEndDate,company.getCompany_id());
					
					if(battery != null) {
						companyDto.setCompany_id(company.getCompany_id());
						activeGroupList.add(companyDto);
						continue;
					}
					
					tyre			= tyreService.getTyreMountCreatedBetweenDates(finalStartDate, finalEndDate,company.getCompany_id());
					if(tyre != null ) {
						companyDto.setCompany_id(company.getCompany_id());
						activeGroupList.add(companyDto);
						continue;
					}
					
					renewalDetails			= renewalReminderService.getRenewalReminderCount(finalStartDate, finalEndDate,company.getCompany_id());
					if(renewalDetails > 0) {
						companyDto.setCompany_id(company.getCompany_id());
						activeGroupList.add(companyDto);
						continue;
					}
					
					serviceReminderDetails	= serviceReminderService.getTotalServiceReminderCount(finalStartDate, finalEndDate,company.getCompany_id());
					if(serviceReminderDetails > 0) {
						companyDto.setCompany_id(company.getCompany_id());
						activeGroupList.add(companyDto);
						continue;
					}
				}
				 
				StringBuilder sb = new StringBuilder();
				
				if(activeGroupList != null)
				for(CompanyDto company : activeGroupList) {
					sb.append(","+company.getCompany_id());
				}
				
				valueOutObject.put("vehicleActiveStatus",VehicleStatusConstant.VEHICLE_STATUS_ACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE +","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP );
				valueOutObject.put("vehicleInActiveStatus",VehicleStatusConstant.VEHICLE_STATUS_INACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_SURRENDER +","+VehicleStatusConstant.VEHICLE_STATUS_SOLD );
				valueOutObject.put("finalStartDate", finalStartDate);
				valueOutObject.put("finalEndDate", finalEndDate);
				valueOutObject.put("companyId", sb.substring(1));
				valueOutObject.put("emailDate", emailDate);
				valueOutObject.put("companyList", companyList);
				valueOutObject.put("finalMonthAndYear", finalMonthAndYear);
				
				
				
				valueOutObject  = vehicleService.getActiveVehicleDetailsOfTheMonth(valueOutObject);
				
				DtosActive		= (List<CompanyDto>) valueOutObject.get("Active");
				DtosInactive	= (List<CompanyDto>) valueOutObject.get("Inactive");
				DtosSold		= (List<CompanyDto>) valueOutObject.get("Sold");
				
				finalListOfVehicle	= new ArrayList<CompanyDto>();
				if(DtosActive!=null)
				finalListOfVehicle.addAll(DtosActive);
				if(DtosInactive!=null)
				finalListOfVehicle.addAll(DtosInactive);
				if(DtosSold!=null)
				finalListOfVehicle.addAll(DtosSold);
				
				
				finalMapOfVehicle	= new HashMap<Integer, CompanyDto>();
				
				if(finalListOfVehicle != null && !finalListOfVehicle.isEmpty()) {
					for(int i = 0; i < finalListOfVehicle.size(); i++) {
						
						if(finalMapOfVehicle.containsKey(finalListOfVehicle.get(i).getCompany_id())) {
							finalCompanyDto			= finalMapOfVehicle.get(finalListOfVehicle.get(i).getCompany_id());
							
							finalCompanyDto.setTotalActiveVehicleCount(finalCompanyDto.getTotalActiveVehicleCount() + finalListOfVehicle.get(i).getTotalActiveVehicleCount());
							finalCompanyDto.setTotalInActiveVehicleCount(finalCompanyDto.getTotalInActiveVehicleCount() + finalListOfVehicle.get(i).getTotalInActiveVehicleCount());
							finalCompanyDto.setTotalSoldVehicleCount(finalCompanyDto.getTotalSoldVehicleCount() + finalListOfVehicle.get(i).getTotalSoldVehicleCount());
							finalCompanyDto.setCompany_name(finalListOfVehicle.get(i).getCompany_name() != null ? finalListOfVehicle.get(i).getCompany_name() :finalCompanyDto.getCompany_name());
						} else {
							finalMapOfVehicle.put(finalListOfVehicle.get(i).getCompany_id(), finalListOfVehicle.get(i));
						}
					}
				}
				valueOutObject.put("vehicleCountHM", finalMapOfVehicle);
				
				emailBody = CompanyService.configureActiveVehicleDetailsOfTheMonthEmailBody(valueOutObject);
				final String activeVehicleDetailsEmailBody  = (String) emailBody.get("activeVehicleDetailsEmailBody");
				final	String		mailSend 	 			= env.getProperty("managers.email") +", aarti@ivgroup.in";
				final	String[]	mailSendTo 	 			= mailSend.split(",");
						mailSender.send(new MimeMessagePreparator() {
							@Override
							public void prepare(MimeMessage mimeMessage) throws MailParseException  {
								try {
									
									MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
									messageHelper.setTo(mailSendTo);	
									messageHelper.setSubject("Fleetop➕ :  Fleetop Monthly Active Client For Month "+finalMonthAndYear+"");
									messageHelper.setText(activeVehicleDetailsEmailBody, true);
									messageHelper.setBcc(env.getProperty("developer.email"));

								}catch (Exception e) {
									LOGGER.error("Exception " + e);
									e.printStackTrace();
								}
							}
					  });
				}
			   catch (Exception e) {
				LOGGER.error("Exception " + e);
				e.printStackTrace();
			}

		}	
		

		//@Scheduled(cron = "0 45 23 * * *")
		public void scheduledTripGpsOdomter() {
			try {
				tripSheetService.getVehicleGpsDetailAndUpdateTripSheetUsageKM();
			} catch (Exception e) {
				LOGGER.debug("Auto Update Halt to Active", e);
				e.printStackTrace();
			}
		}
		
	// MissingeFuelEntryAndUreaEntryAlert	@Scheduled(cron = "0 00 09 * * 1")
		public void fuel_Urea_Reminder() {
			try {
				LOGGER.warn("Configure E-mail & SMS Started..");
				configureMissingFuelEntryAlert();
				configureMissingUreaEntryAlert();
				LOGGER.warn("Configure E-mail & SMS Ended..");
			} catch (Exception e) {
				LOGGER.error("Auto Update Halt to Active", e);
				e.printStackTrace();
			}
		}

@SuppressWarnings({ "unused", "unchecked" })
public void configureMissingFuelEntryAlert() {	
	ValueObject							valueOutObject				= null;
	ValueObject							emailBody		  		  	= null;
	List<CompanyDto> 					activeGroupList				= null;
	List<RenewalMailConfiguration>  	pendingList					= null;
	try {
		valueOutObject			= new ValueObject();
		activeGroupList			= CompanyService.getActiveGroupList();
		
		if(activeGroupList != null && !activeGroupList.isEmpty()) {
			
			for(CompanyDto company : activeGroupList) {
				pendingList					= renewalReminderMail.getAllCompanyInfEmail_Ids(company.getCompany_id());
				valueOutObject				= fuelService.getMissingFuelEntryAlertByCompanyId(company.getCompany_id());	
				valueOutObject.put("company", company);
				
				emailBody 					= CompanyService.configureMissingFuelEntryAlertEmailBody(valueOutObject);
				if(emailBody.getBoolean("noRecordFound")) {
					continue;
				}
				final String activeVehicleDetailsEmailBody  = (String) emailBody.get("activeVehicleDetailsEmailBody");
				
				for(RenewalMailConfiguration dto :pendingList) {
						final	String[]	mailSendTo 	 = dto.getEmailIds().split(",");
						
						mailSender.send(new MimeMessagePreparator() {
								@Override
								public void prepare(MimeMessage mimeMessage) throws MailParseException  {
									try {
										MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
										messageHelper.setTo(mailSendTo);	
										messageHelper.setSubject("Missing Fuel Entry Alert !!!");
										messageHelper.setText(activeVehicleDetailsEmailBody, true);
										messageHelper.setBcc(env.getProperty("developer.email"));
	
									}catch (Exception e) {
									LOGGER.error("Exception " + e);
									e.printStackTrace();
								}
							}
					  });
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception " + e);
			e.printStackTrace();
	}

}


@SuppressWarnings({ "unused", "unchecked" })
public void configureMissingUreaEntryAlert() {	
	ValueObject							valueOutObject				= null;
	ValueObject							emailBody		  		  	= null;
	List<CompanyDto> 					activeGroupList				= null;
	List<RenewalMailConfiguration>  	pendingList					= null;
	
	try {
		valueOutObject			= new ValueObject();
		activeGroupList			= CompanyService.getActiveGroupList();
		
		if(activeGroupList != null && !activeGroupList.isEmpty()) {
				
			for(CompanyDto company : activeGroupList) {
				pendingList					= renewalReminderMail.getAllCompanyInfEmail_Ids(company.getCompany_id());
				
				valueOutObject = ureaService.getMissingUreaEntryAlertByCompanyId(company.getCompany_id());
				valueOutObject.put("company", company);
				
				emailBody = CompanyService.configureMissingUreaEntryAlertEmailBody(valueOutObject);
				if(emailBody.getBoolean("noRecordFound")) {
					continue;
				}
			
				final String activeVehicleDetailsEmailBody  = (String) emailBody.get("activeVehicleDetailsEmailBody");
				for(RenewalMailConfiguration dto :pendingList) {
				
					final	String[]	mailSendTo 	 = dto.getEmailIds().split(",");
				
				
				mailSender.send(new MimeMessagePreparator() {
							@Override
							public void prepare(MimeMessage mimeMessage) throws MailParseException  {
								try {
									MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
									messageHelper.setTo(mailSendTo);	
									messageHelper.setSubject("Missing Urea Entry Alert !!!");
									messageHelper.setText(activeVehicleDetailsEmailBody, true);
									messageHelper.setBcc(env.getProperty("developer.email"));

								}catch (Exception e) {
								LOGGER.error("Exception " + e);
								e.printStackTrace();
							}
						}
				  });
				}
				
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception " + e);
			e.printStackTrace();
	}

}

//@Scheduled(cron = "0 01 01 * * *")
public void dailyInspection() {
	try {
		LOGGER.warn("Daily Inspection Started..");

		vehicleDailyInspectionService.getDailyInspectionList();

		LOGGER.warn("Daily Inspection Ended..");
	} catch (Exception e) {
		LOGGER.error("Daily Inspection", e);
		e.printStackTrace();
	}
}

//@Scheduled(cron = "0 45 23 * * *")
public void saveIntangleFuelPullAPI() {
	try {
		fuelService.saveIntangleFuelPullAPI();
	} catch (Exception e) {
		LOGGER.debug("Auto Update Halt to Active", e);
		e.printStackTrace();
	}
}
//@Scheduled(cron = "0 45 23 * * *")
public void saveIntangleTripPullAPI() {
	try {
		tripSheetService.saveIntangleTripPullAPI();;
	} catch (Exception e) {
		LOGGER.debug("Auto Update Halt to Active", e);
		e.printStackTrace();
	}
}

    @Scheduled(cron = "0 0 9 * * *")  //runs every day 9 AM
	public void SendRenewalReminderNotification() {
		List<RenewalNotificationConfiguration> renewalList = null;
		RenewalNotificationConfiguration   renewalNotification = null;
		Long count  = null;
		String Ids = null;
		
		try {
			renewalList = renewalNotificationRepository.findAll();
			for(int i=0; i<renewalList.size(); i++)
			{
				renewalNotification = renewalList.get(i);
				Ids = renewalNotification.getNotificationsToIds();
				String[] values = Ids.split(",");
				count = RenewalReminderService.getRenewalReminderListBeforeFiveDaysDue(renewalNotification.getCompanyId(),renewalNotification.getThreshold());
				if(count >0)
				{
					Long id =null;
					for(int j=0; j<values.length; j++)
					{
						UserAlertNotifications	notifications = new UserAlertNotifications();
						id = Long.parseLong(values[j]);
						String hyperlink = "<a href=\"/viewRenewalReminder.in\"/\"><br>Renewal Reminders</a>";
						notifications.setUserId(id);
						notifications.setAlertMsg("Dear User You have " + count  + " Renewal Reminders  about to Expire  After " +  renewalNotification.getThreshold()  + " Days " + hyperlink);
						notifications.setCompanyId(renewalNotification.getCompanyId());
						notifications.setTxnTypeId(UserAlertNOtificationsConstant.ALERT_TYPE_RENWAL_REMINDER);
						notifications.setCreatedById(id);
						notifications.setCreatedOn(new Date());
						notifications.setStatus((short) 1);
						userAlertNotificationsRepository.save(notifications);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.debug("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}

    
    @Scheduled(cron = "0 0 18 * * ?")
    public void sendEmailForTripSheetCount() {
    	List<DailyTripCountMailConfiguration>  tripconfigList = null;
    	List<TripSheetDto> 					   triplist 	  = null;
    	String								   todaysDate	  = null;
    	try {
    		Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE,0);
			Date currentDate = cal.getTime();
			todaysDate = dateFormat2.format(currentDate);
			
    		tripconfigList  =  dailyTripCountMailConfigRepository.findAll();
    		
    		for(DailyTripCountMailConfiguration tripConfig : tripconfigList) {
    			System.err.println("email "+ tripConfig.getEmailId());
    			
    			triplist = tripSheetService.FinalAllTodayTripSheet(tripConfig.getCompanyId(), todaysDate);
    		
    			if(triplist != null && !triplist.isEmpty()) {
    				
	    			final String  MailBody = tripSheetService.getTripSheetCreatedMailBody(triplist,todaysDate);

	    			try {
	    			    MimeMessagePreparator preparator = new MimeMessagePreparator() {
	    			    	@Override
	    			        public void prepare(MimeMessage msg) throws MailParseException {
	    			            try {
	    			                MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
	    			                messageHelper.setSubject("Daily TripSheet Created List !!!");
	    			                messageHelper.setText(MailBody,true);
	    			                
	    			                String[] emailIds = tripConfig.getEmailId().split(",");
	    			                for (String email : emailIds) {
	    			                	System.err.println("email-- "+ email);
	    			                    messageHelper.addTo(email.trim());
	    			                }
	    			                
	    			            } catch (Exception e) {
	    			                LOGGER.error("Exception while preparing email: " + e.getMessage(), e);
	    			                throw new MailParseException(e);
	    			            }
	    			        }
	    			    };
	    			    mailSender.send(preparator);
	    			} catch (MailException  e) {
	    			    LOGGER.error("MailException while sending email: " + e.getMessage(), e);
	    			    e.printStackTrace();
	    			}
    			}
    			//need to send date also
    		}
    		
    	}catch(Exception e) {
    		 LOGGER.error("Exception -- " + e.getMessage(), e);
    	}
    }
    
}


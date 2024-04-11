package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.fleetopgroup.persistence.dao.EmailAlertQueueRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderWorkOrderHistoryRepository;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.report.dao.ServiceReminderDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderSequenceService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("EmailAlertQueueService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EmailAlertQueueService implements IEmailAlertQueueService {

	@PersistenceContext
	EntityManager entityManager;

	
	
	@Autowired
	private EmailAlertQueueRepository 		emailAlertQueueRepository;
	
	@Autowired
	private ServiceReminderRepository 		serviceReminderRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	

	@Autowired
	private ICompanyConfigurationService					companyConfigurationService;
	
	@Autowired ServiceReminderDao							ServiceReminderDaoImpl;
	@Autowired ServiceReminderWorkOrderHistoryRepository	historyRepository;
	@Autowired public JavaMailSender 						emailSender;
	
	@Autowired 
	IServiceReminderSequenceService serviceReminderSequenceService;
	

	
	SimpleDateFormat 						dateFormatAtt 		= new SimpleDateFormat("yyyy, MM, dd");
	SimpleDateFormat 						dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 						AttendanceYear 		= new SimpleDateFormat("yyyy");
	SimpleDateFormat 						AttendanceMonth 	= new SimpleDateFormat("MM");
	SimpleDateFormat 						AttendanceDay 		= new SimpleDateFormat("dd");
	SimpleDateFormat 						sqlDateFormat 		= new SimpleDateFormat("yyyy-MM-dd");
	

	private static final int PAGE_SIZE = 10;
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ValueObject saveEmailServiceReminder(ValueObject	valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		EmailAlertQueue					emailAlertQueue					= null;
		EmailAlertQueue					emailAlertQueue1				= null;
		ValueObject						valOutObject					= null;
		Timestamp 						createdDateTime 				= null;
		Timestamp 						timestamp 						= null;
		Timestamp 						timestamp1 						= null;
		java.util.Date 					date 							= null;
		java.sql.Date 					serviceDate 					= null;
		java.util.Date 					alertAfterDate 					= null;
		java.util.Date 					alertBeforDate 					= null;

		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			createdDateTime 	= new java.sql.Timestamp(new Date().getTime());
			
			String before[]					= valueObject.getString("alertBeforeDate").split(",");
			
			emailAlertQueueRepository.deleteEmailAlertQueueById(valueObject.getLong("serviceId"));
			
			for(int j=0; j<before.length;j++) {
			
				emailAlertQueue = new EmailAlertQueue();
				
				emailAlertQueue.setVid(valueObject.getInt("vehicleId"));
				emailAlertQueue.setContent("Service Task" + valueObject.getString("serviceTask") + "-" + valueObject.getString("serviceSubTask") + "need to be performed On " + valueObject.getString("serviceDate"));
				emailAlertQueue.setAlertType((short)1);
				emailAlertQueue.setEmailId(valueObject.getString("emailId"));
				emailAlertQueue.setCompanyId(userDetails.getCompany_id());
				emailAlertQueue.setTransactionId(valueObject.getLong("serviceId"));
				emailAlertQueue.setTransactionNumber(valueObject.getLong("serviceNumber"));
				emailAlertQueue.setOverDueAlert(false);
				emailAlertQueue.setEmailSent(false);
				emailAlertQueue.setAlertBeforeValues(Integer.parseInt(before[j]));
				
				
				timestamp = DateTimeUtility.getTimeStampFromDateTimeString(valueObject.getString("serviceDate"));
				emailAlertQueue.setServiceDate(timestamp);
				
				
				final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(timestamp);
				
				calendar.add(Calendar.DAY_OF_YEAR, -Integer.parseInt(before[j]));
				java.util.Date alertDate = format.parse(format.format(calendar.getTime()));
				alertBeforDate =  new Date(alertDate.getTime());
				emailAlertQueue.setAlertBeforeDate(alertBeforDate+"");
				emailAlertQueue.setAlertScheduleDate(alertBeforDate);
				
				
				
				emailAlertQueueRepository.save(emailAlertQueue);
			
			}
			
			if(valueObject.getString("alertAfterDate") != null && valueObject.getString("alertAfterDate") != "") {
				String after[]	= valueObject.getString("alertAfterDate").split(",");
				
				for(int k=0; k<after.length;k++) {
					
					emailAlertQueue1 = new EmailAlertQueue();
					emailAlertQueue1.setVid(valueObject.getInt("vehicleId"));
					emailAlertQueue1.setContent("Service Task" + valueObject.getString("serviceTask") + "-" + valueObject.getString("serviceSubTask") + "need to be performed " + valueObject.getString("serviceDate"));
					emailAlertQueue1.setAlertType((short)1);
					emailAlertQueue1.setEmailId(valueObject.getString("emailId"));
					emailAlertQueue1.setCompanyId(userDetails.getCompany_id());
					emailAlertQueue1.setTransactionId(valueObject.getLong("serviceId"));
					emailAlertQueue1.setTransactionNumber(valueObject.getLong("serviceNumber"));
					emailAlertQueue1.setOverDueAlert(false);
					emailAlertQueue1.setEmailSent(false);
					if(after[k] != null && !after[k].trim().equalsIgnoreCase(""))
						emailAlertQueue1.setAlertAfterValues(Integer.parseInt(after[k]));
					
				timestamp1 = DateTimeUtility.getTimeStampFromDateTimeString(valueObject.getString("serviceDate"));
				emailAlertQueue1.setServiceDate(timestamp1);
				
					
				final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(timestamp1);
				if(after[k] != null && !after[k].trim().equalsIgnoreCase("")) {
					calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(after[k]));
					java.util.Date alertDate1 = format.parse(format.format(calendar.getTime()));
					alertAfterDate =  new Date(alertDate1.getTime());
					emailAlertQueue1.setAlertAfterDate(alertAfterDate+"");
					emailAlertQueue1.setAlertScheduleDate(alertAfterDate);
				}
				
				emailAlertQueueRepository.save(emailAlertQueue1);
				}
			}
			
			
			
				valOutObject	= new ValueObject();
				if(emailAlertQueue != null) {
				valOutObject.put("AlertBefore", emailAlertQueue.getQueueId());
				}
				if(emailAlertQueue1 != null) {
				valOutObject.put("AlertAfter", emailAlertQueue1.getQueueId());
				}
		
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails						= null;
			emailAlertQueue					= null;
			valOutObject					= null;
			createdDateTime 				= null;
		}
	}
	
	
	@Override
	public List<EmailAlertQueue> getAllEmailAlertQueueDetails(Date currentDate)
			throws Exception {
		
		return emailAlertQueueRepository.getAllEmailAlertQueueDetails(currentDate);
	}
	
	@Override
	@Transactional
	public void updateEmailSent(Date currentDate)
			throws Exception {
		 emailAlertQueueRepository.updateEmailSent(currentDate);
	}
	
	@Override
	public List<EmailAlertQueue> getAllEmailAlertQueueDetailsById(long serviceReminderId)
			throws Exception {
		
		return emailAlertQueueRepository.getAllEmailAlertQueueDetailsById(serviceReminderId);
	}
	
	@Override
	public void updateEmailAlertQueue(EmailAlertQueue email) {
		emailAlertQueueRepository.save(email);
	} 
	
	@Override
	@Transactional
	public void deleteEmailAlertQueue(long serviceId) throws Exception {
		emailAlertQueueRepository.deleteEmailAlertQueueById(serviceId);
	} 
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public void sendEmailServiceOdometer(ServiceReminderDto list) throws Exception {
		List<EmailAlertQueue>  		pendingList			= null;
		try {
				if(list != null) {
					pendingList	= getAllEmailAlertQueueDetailsById(list.getService_id());
					if(pendingList != null && !pendingList.isEmpty()) {
						
						for(EmailAlertQueue	emailAlertQueue : pendingList) {

							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
							ServiceReminder  sr 	= serviceReminderRepository.getServiceReminder(emailAlertQueue.getTransactionId(), emailAlertQueue.getCompanyId());
							int serviceTypeId		= sr.getServiceTypeId();
							int serviceSubTypeId	= sr.getServiceSubTypeId();
							String serviceDate		= simpleDateFormat.format(emailAlertQueue.getServiceDate());
							
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
							if (result1 != null) {
								list2 = new CompanyDto();
								list2.setCompany_name((String) result2[0]);
								list2.setCompany_id((Integer) result2[1]);
							}
							
							
							Query query3 = entityManager.createQuery(
									"SELECT j.Job_Type, j.Job_id, js.Job_Subid, js.Job_ROT" + 
									" FROM JobType as j" + 
									" INNER JOIN JobSubType as js on j.Job_id = js.Job_TypeId" + 
									" WHERE j.Job_id="+serviceTypeId+" and js.Job_Subid="+serviceSubTypeId+"");
							
							Object[] result3 = null;
							try {
								result3 = (Object[]) query3.getSingleResult();
							} catch (NoResultException nre) {
								// Ignore this because as per your logic this is ok!
							}

							JobSubTypeDto list3 = null;
							if (result3 != null) {
								list3 = new JobSubTypeDto();
								list3.setJob_Type((String) result3[0]);
								list3.setJob_TypeId((Integer) result3[1]);
								list3.setJob_Subid((Integer) result3[2]);
								list3.setJob_ROT((String) result3[3]);
							
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
									"    <th><p style=\"color:black;\">Current Odometer</p></th>\r\n" + 
									"    <th><p style=\"color:black;\">Service Odometer</p></th>\r\n" + 
									"  </tr>\r\n" + 
									"  <tr>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+list3.getJob_Type()+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+list3.getJob_ROT()+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+serviceDate+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+list.getVehicle_currentOdometer()+"</p></center></td>\r\n" + 
									"    <td><center><p style=\"color:black;\">"+list.getMeter_serviceodometer()+"</p></center></td>\r\n" + 
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
										messageHelper.setTo(mailSendTo);
										messageHelper.setSubject("Service Reminder");
										messageHelper.setText(sendHTML_Email_PO, true);
										
										}catch (MessagingException ex) {
											throw new MailParseException(ex);
										}

									}

								});
							
						   }
					 }	
					
				}
		
		} catch(Exception e) {
			LOGGER.error("Exception " + e);
		}

	}
	
	@Override
	public ValueObject bodyContentForEmail(ValueObject valueInObject) throws Exception {
		
		ValueObject					valueOutObject 				= null;
		try {
			
			ServiceReminderDto servRemind 			=  (ServiceReminderDto) valueInObject.get("servRemind");
			SimpleDateFormat simpleDateFormat   = new SimpleDateFormat("dd-MM-yyyy");
			String          serviceDate			= simpleDateFormat.format(servRemind.getTime_servicedate());
			
			final String sendHTML_Email_Date = "<html>\r\n" + 
					"<head>\r\n" + 
					"</head>\r\n" + 
					"\r\n" + 
					"<body>\r\n" + 
					"<table bgcolor=\"#ff3333\" width=\"100%\">\r\n" + 
					"<tr>\r\n" + 
					"<td>\r\n" + 
					"<h2><font color=\"#ffffff\" ><b><center>"+servRemind.getCompanyName()+"</center></b></font></h2>\r\n" + 
					"</td>\r\n" + 
					"</tr>\r\n" + 
					"</table>\r\n" + 
					"<br>\r\n" + 
					"<center><b><p style=\"color:black;\"> Service Reminder No: "+servRemind.getService_Number()+"</p> </b></center>\r\n" + 
					"<center><b><p style=\"color:black;\">"+servRemind.getVehicle_registration()+"</p></b></center>\r\n" + 
					"<br>\r\n" + 
					"<p style=\"color:black;\">We hope you've had a great time navigating our product. This is a gentle service Reminder for your Vehicle "+servRemind.getVehicle_registration()+".</p>\r\n" + 
					"<p style=\"color:black;\">Please find below  Service Reminder Details Pending :</p> \r\n" +
					"<table style=\"width:100%\" border=1px>\r\n" + 
					"  <tr>\r\n" + 
					"    <th><p style=\"color:black;\">Task</p></th>\r\n" + 
					"    <th><p style=\"color:black;\">Subtask</p></th> \r\n" + 
					"    <th><p style=\"color:black;\">Service Date</p></th>\r\n" + 
					"  </tr>\r\n" + 
					"  <tr>\r\n" + 
					"    <td><center><p style=\"color:black;\">"+servRemind.getService_type()+"</p></center></td>\r\n" + 
					"    <td><center><p style=\"color:black;\">"+servRemind.getService_subtype()+"</p></center></td>\r\n" + 
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
			
			
			final String sendHTML_Email_Odometer = "<html>\r\n" + 
					"<head>\r\n" + 
					"</head>\r\n" + 
					"\r\n" + 
					"<body>\r\n" + 
					"<table bgcolor=\"#ff3333\" width=\"100%\">\r\n" + 
					"<tr>\r\n" + 
					"<td>\r\n" + 
					"<h2><font color=\"#ffffff\" ><b><center>"+servRemind.getCompanyName()+"</center></b></font></h2>\r\n" + 
					"</td>\r\n" + 
					"</tr>\r\n" + 
					"</table>\r\n" + 
					"<br>\r\n" + 
					"<center><b><p style=\"color:black;\"> Service Reminder No: "+servRemind.getService_Number()+"</p> </b></center>\r\n" + 
					"<center><b><p style=\"color:black;\">"+servRemind.getVehicle_registration()+"</p></b></center>\r\n" + 
					"<br>\r\n" + 
					"<p style=\"color:black;\">We hope you've had a great time navigating our product. This is a gentle service Reminder for your Vehicle "+servRemind.getVehicle_registration()+".</p>\r\n" + 
					"<p style=\"color:black;\">Please find below  Service Reminder Details Pending :</p> \r\n" +
					"<table style=\"width:100%\" border=1px>\r\n" + 
					"  <tr>\r\n" + 
					"    <th><p style=\"color:black;\">Task</p></th>\r\n" + 
					"    <th><p style=\"color:black;\">Subtask</p></th> \r\n" + 
					"    <th><p style=\"color:black;\">Current Odometer</p></th>\r\n" + 
					"    <th><p style=\"color:black;\">Service Threshold Odometer</p></th>\r\n" + 
					"    <th><p style=\"color:black;\">Service Odometer</p></th>\r\n" + 
					"  </tr>\r\n" + 
					"  <tr>\r\n" + 
					"    <td><center><p style=\"color:black;\">"+servRemind.getService_type()+"</p></center></td>\r\n" + 
					"    <td><center><p style=\"color:black;\">"+servRemind.getService_subtype()+"</p></center></td>\r\n" + 
					"    <td><center><p style=\"color:black;\">"+servRemind.getVehicle_currentOdometer()+"</p></center></td>\r\n" + 
					"    <td><center><p style=\"color:black;\">"+servRemind.getMeter_servicethreshold_odometer()+"</p></center></td>\r\n" + 
					"    <td><center><p style=\"color:black;\">"+servRemind.getMeter_serviceodometer()+"</p></center></td>\r\n" + 
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
			
			
			valueOutObject	= new ValueObject();
			valueOutObject.put("sendHTML_Email_Date", sendHTML_Email_Date);
			valueOutObject.put("sendHTML_Email_Odometer", sendHTML_Email_Odometer);
			
			return valueOutObject;
			
		}catch (Exception e) {
			throw e;
		}
		
	}
	
	
}
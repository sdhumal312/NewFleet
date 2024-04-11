package org.fleetopgroup.persistence.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.fleetopgroup.persistence.dao.ServiceReminderRepository;
import org.fleetopgroup.persistence.dao.SmsAlertQueueRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("SmsAlertQueueService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SmsAlertQueueService implements ISmsAlertQueueService {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired private SmsAlertQueueRepository 								smsAlertQueueRepository;
	@Autowired private ServiceReminderRepository 							serviceReminderRepository;
	
	SimpleDateFormat 						dateFormatAtt 					= new SimpleDateFormat("yyyy, MM, dd");
	SimpleDateFormat 						dateFormat 						= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 						AttendanceYear 					= new SimpleDateFormat("yyyy");
	SimpleDateFormat 						AttendanceMonth 				= new SimpleDateFormat("MM");
	SimpleDateFormat 						AttendanceDay 					= new SimpleDateFormat("dd");
	SimpleDateFormat 						sqlDateFormat 					= new SimpleDateFormat("yyyy-MM-dd");
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ValueObject saveSmsServiceReminder(ValueObject	valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		SmsAlertQueue					smsAlertQueue					= null;
		SmsAlertQueue					smsAlertQueue1					= null;
		ValueObject						valOutObject1					= null;
		Timestamp 						timestamp 						= null;
		Timestamp 						timestamp1 						= null;
		java.util.Date 					alertAfterDate 					= null;
		java.util.Date 					alertBeforDate 					= null;

		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			String before[]					= valueObject.getString("alertBeforeDate").split(",");
			
			for(int j=0; j<before.length;j++) {
			
				smsAlertQueue = new SmsAlertQueue();
				
				smsAlertQueue.setVid(valueObject.getInt("vehicleId"));
				smsAlertQueue.setContent("Service Task" + valueObject.getString("serviceTask") + "-" + valueObject.getString("serviceSubTask") + "need to be performed On " + valueObject.getString("serviceDate"));
				smsAlertQueue.setAlertType((short)1);
				smsAlertQueue.setMobileNumber(valueObject.getString("mobileNumber"));
				smsAlertQueue.setCompanyId(userDetails.getCompany_id());
				smsAlertQueue.setTransactionId(valueObject.getLong("serviceId"));
				smsAlertQueue.setTransactionNumber(valueObject.getLong("serviceNumber"));
				smsAlertQueue.setOverDueAlert(false);
				smsAlertQueue.setSmsSent(false);
				smsAlertQueue.setAlertBeforeValues(Integer.parseInt(before[j]));
				
				
				timestamp = DateTimeUtility.getTimeStampFromDateTimeString(valueObject.getString("serviceDate"));
				smsAlertQueue.setServiceDate(timestamp);
				
				
				final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(timestamp);
				
				calendar.add(Calendar.DAY_OF_YEAR, -Integer.parseInt(before[j]));
				java.util.Date alertDate = format.parse(format.format(calendar.getTime()));
				alertBeforDate =  new Date(alertDate.getTime());
				smsAlertQueue.setAlertBeforeDate(alertBeforDate+"");
				smsAlertQueue.setAlertScheduleDate(alertBeforDate);
				
				smsAlertQueueRepository.save(smsAlertQueue);
			
			}
			if(valueObject.getString("alertAfterDate") != null && valueObject.getString("alertAfterDate") != "") {
				String after[]	= valueObject.getString("alertAfterDate").split(",");
				
				for(int k=0; k<after.length;k++) {
					
					smsAlertQueue1 = new SmsAlertQueue();
					smsAlertQueue1.setVid(valueObject.getInt("vehicleId"));
					smsAlertQueue1.setContent("Service Task" + valueObject.getString("serviceTask") + "-" + valueObject.getString("serviceSubTask") + "need to be performed " + valueObject.getString("serviceDate"));
					smsAlertQueue1.setAlertType((short)1);
					smsAlertQueue1.setMobileNumber(valueObject.getString("mobileNumber"));
					smsAlertQueue1.setCompanyId(userDetails.getCompany_id());
					smsAlertQueue1.setTransactionId(valueObject.getLong("serviceId"));
					smsAlertQueue1.setTransactionNumber(valueObject.getLong("serviceNumber"));
					smsAlertQueue1.setOverDueAlert(false);
					smsAlertQueue1.setSmsSent(false);
					smsAlertQueue1.setAlertAfterValues(Integer.parseInt(after[k]));
					
					
				timestamp1 = DateTimeUtility.getTimeStampFromDateTimeString(valueObject.getString("serviceDate"));
				smsAlertQueue1.setServiceDate(timestamp1);	
					
				final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(timestamp1);
				
				calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(after[k]));
				java.util.Date alertDate1 = format.parse(format.format(calendar.getTime()));
				alertAfterDate =  new Date(alertDate1.getTime());
				smsAlertQueue1.setAlertAfterDate(alertAfterDate+"");
				smsAlertQueue1.setAlertScheduleDate(alertAfterDate);
				
				smsAlertQueueRepository.save(smsAlertQueue1);
				}
			}
			
			
			
				valOutObject1	= new ValueObject();
				if(smsAlertQueue != null) {
				valOutObject1.put("AlertBefore", smsAlertQueue.getQueueId());
				}
				if(smsAlertQueue1 != null) {
					valOutObject1.put("AlertAfter", smsAlertQueue1.getQueueId());
				}
		
			return valOutObject1;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails						= null;
			smsAlertQueue					= null;
			valOutObject1					= null;
			smsAlertQueue1					= null;
		}
	}
	
	@Override
	public List<SmsAlertQueue> getAllSmsAlertQueueService(Date currentDate)
			throws Exception {
		
		return smsAlertQueueRepository.getAllSmsAlertQueueService(currentDate);
	}
	
	@Override
	public List<SmsAlertQueue> getAllSmsAlertQueueDetailsById(long serviceReminderId)
			throws Exception {
		
		return smsAlertQueueRepository.getAllSmsAlertQueueDetailsById(serviceReminderId);
	}
	
	@Override
	@Transactional
	public void deleteSmsAlertQueue(long serviceId) throws Exception {
		smsAlertQueueRepository.deletesmsAlertQueueById(serviceId);
	} 
	
	@Override
	public void updateSmsAlertQueue(SmsAlertQueue sms) {
		
		smsAlertQueueRepository.save(sms);
	} 
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Override
	public void sendSmsServiceOdometer(ServiceReminderDto list) throws Exception {
		List<SmsAlertQueue> pendingList	 = null;
		String[]			mobNumber 	 = null;
		try {
				if(list != null) {
					pendingList	= getAllSmsAlertQueueDetailsById(list.getService_id());
					if(pendingList != null && !pendingList.isEmpty()) {
						
						for(SmsAlertQueue smsAlertQueue : pendingList) {
							
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");	
							ServiceReminder sr = serviceReminderRepository.getServiceReminder(smsAlertQueue.getTransactionId(), 6);
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
							
							Query query2 = entityManager.createQuery(
									"SELECT j.Job_Type, j.Job_id, js.Job_Subid, js.Job_ROT" + 
									" FROM JobType as j" + 
									" INNER JOIN JobSubType as js on j.Job_id = js.Job_TypeId" + 
									" WHERE j.Job_id="+serviceTypeId+" and js.Job_Subid="+serviceSubTypeId+"");
							
							Object[] result2 = null;
							try {
								result2 = (Object[]) query2.getSingleResult();
							} catch (NoResultException nre) {
								// Ignore this because as per your logic this is ok!
							}

							JobSubTypeDto list2 = null;
							if (result2 != null) {
								list2 = new JobSubTypeDto();
								list2.setJob_Type((String) result2[0]);
								list2.setJob_TypeId((Integer) result2[1]);
								list2.setJob_Subid((Integer) result2[2]);
								list2.setJob_ROT((String) result2[3]);
							
							}
						
						String content1 = "Hi,We hope you've had a great time navigating our product. This is a gentle service Reminder for your Vehicle "+list1.getVehicle_registration()+"."
								+ " Service "+smsAlertQueue.getTransactionNumber()+" for "+list2.getJob_Type()+" - "+list2.getJob_ROT()+" needs to be performed by "+serviceDate+". Thank You ";
							
							
							
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
					
				}
		
		} catch(Exception e) {
			LOGGER.error("Exception " + e);
		}

	}
	
	@Override
	public ValueObject sendServiceReminderSMS(String working_key, String masking_key, String mobileNumber, String content) throws Exception {
		ValueObject					valueOutObject 				= null;
		try {
		
			HttpURLConnection urlconnection	= null;
			 String retval = "";
			 String postData = "";
			 String msgUrl  = "http://api-alerts.solutionsinfini.com/v3/?method=sms&api_key="+working_key+"&to="+mobileNumber+"&sender="+masking_key+"&message="+content+"";
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
			 
			 valueOutObject	= new ValueObject();
			 valueOutObject.put("retval", retval);
			 
			 return valueOutObject;
			
		}catch (Exception e) {
			throw e;
		}
	
	}
	
	@Override
	public ValueObject bodyContentForSMS (ValueObject valueInObject) throws Exception {
		
		ValueObject					valueOutObject 				= null;
		try {
			
			ServiceReminderDto servRemind 			=  (ServiceReminderDto) valueInObject.get("servRemind");
			SimpleDateFormat simpleDateFormat   = new SimpleDateFormat("dd-MM-yyyy");
			String          serviceDate			= simpleDateFormat.format(servRemind.getTime_servicedate());
			
			
			String contentThresholdDate = "Hi,We hope you've had a great time navigating our product. This is a gentle service Reminder for your Vehicle "+servRemind.getVehicle_registration()+"."
					+ " Service "+servRemind.getService_Number()+" for "+servRemind.getService_type()+" - "+servRemind.getService_subtype()+" needs to be performed by "+serviceDate+". Thank You ";
				
			contentThresholdDate = contentThresholdDate.trim();
			contentThresholdDate = contentThresholdDate.replaceAll("\\s", "%20");
			
			
			String contentThresholdOdometer = "Hi,We hope you've had a great time navigating our product. This is a gentle service Reminder for your Vehicle "+servRemind.getVehicle_registration()+"."
					+ " Service "+servRemind.getService_Number()+" for "+servRemind.getService_type()+" - "+servRemind.getService_subtype()+" needs to be performed. "
					+ " Current odometer reading is "+servRemind.getVehicle_currentOdometer()+" and threshold odometer is "+servRemind.getMeter_servicethreshold_odometer()+". "
					+ " Service odometer for this vehicle is "+servRemind.getMeter_serviceodometer()+" "
					+ "Thank You ";
				
			contentThresholdOdometer = contentThresholdOdometer.trim();
			contentThresholdOdometer = contentThresholdOdometer.replaceAll("\\s", "%20");
			
			
			valueOutObject	= new ValueObject();
			valueOutObject.put("contentThresholdDate", contentThresholdDate);
			valueOutObject.put("contentThresholdOdometer", contentThresholdOdometer);
			
			return valueOutObject;
			
		}catch (Exception e) {
			throw e;
		}
		
	}
	
}
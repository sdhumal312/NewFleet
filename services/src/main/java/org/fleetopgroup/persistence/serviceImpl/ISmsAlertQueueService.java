package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.web.util.ValueObject;

public interface ISmsAlertQueueService {
	
	public ValueObject saveSmsServiceReminder(ValueObject valueObject) throws Exception;

	public List<SmsAlertQueue> getAllSmsAlertQueueService(Date currentDate) throws Exception;
	
	public List<SmsAlertQueue> getAllSmsAlertQueueDetailsById(long serviceReminderId) throws Exception;
	
	public void deleteSmsAlertQueue(long serviceId) throws Exception;
	
	public void updateSmsAlertQueue(SmsAlertQueue sms) throws Exception;

	public void sendSmsServiceOdometer(ServiceReminderDto list) throws Exception;
	
	public ValueObject sendServiceReminderSMS(String working_key, String masking_key, String mobileNumber, String content) throws Exception;
	
	public ValueObject bodyContentForSMS (ValueObject valueInObject) throws Exception;
	
}
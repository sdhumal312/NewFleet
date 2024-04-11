package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderWorkOrderHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IEmailAlertQueueService {
	
	public ValueObject saveEmailServiceReminder(ValueObject valueObject) throws Exception;
	
	public List<EmailAlertQueue>  getAllEmailAlertQueueDetails(Date currentDate) throws Exception;
	
	public void updateEmailSent(Date currentDate) throws Exception;
	
	public List<EmailAlertQueue> getAllEmailAlertQueueDetailsById(long serviceReminderId) throws Exception;

	public void updateEmailAlertQueue(EmailAlertQueue email) throws Exception;
	
	public void deleteEmailAlertQueue(long serviceId) throws Exception;

	public void sendEmailServiceOdometer(ServiceReminderDto list) throws Exception;
	
	public ValueObject bodyContentForEmail(ValueObject valueInObject) throws Exception;
	
}
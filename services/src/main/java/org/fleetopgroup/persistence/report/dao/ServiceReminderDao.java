package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.model.ServiceReminderWorkOrderHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceReminderDao {

	List<ServiceReminderDto>  getServiceReminderList(ServiceReminderDto serviceReminderDto) throws Exception;
	
	List<ServiceReminderDto> getServiceReminderListByOdometer(ServiceReminderDto serviceReminderDto) throws Exception ;
	
	List<ServiceReminderWorkOrderHistory>  getServiceWorkOrderList(ServiceReminderDto serviceReminderDto) throws Exception;
	
	List<ServiceReminderDto>  getServiceReminderListOfDay(ServiceReminderDto serviceReminderDto) throws Exception;
	
	List<ServiceReminderDto> getServiceReminderListByOdometerOfDay(ServiceReminderDto serviceReminderDto) throws Exception ;
	
	List<ServiceReminderDto>  getServiceWorkOrderListOfDay(ServiceReminderDto serviceReminderDto) throws Exception;
	
	List<ServiceReminderDto> getReminderStatusForOverdue(Integer vid, String fromDate, String toDate) throws Exception;
}

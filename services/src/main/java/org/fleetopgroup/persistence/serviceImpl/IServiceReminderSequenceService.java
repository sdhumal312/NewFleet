package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.ServiceReminderSequenceCounter;

public interface IServiceReminderSequenceService {
	
	public ServiceReminderSequenceCounter findNextServiceReminderNumber(Integer companyId) throws Exception;
	
	public void updateNextServiceReminderNumber(long nextVal, Integer companyId) throws Exception;
}

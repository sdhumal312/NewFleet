package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.RenewalReminderSequenceCounter;

public interface IRenewalReminderSequenceService {

	public RenewalReminderSequenceCounter findNextRenewal_R_Number(Integer companyId) throws Exception;
	
	public void updateNextRenewal_R_Number(long nextVal, Integer companyId) throws Exception;
}

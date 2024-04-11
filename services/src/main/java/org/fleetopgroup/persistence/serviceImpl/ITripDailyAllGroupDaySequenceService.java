package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.TripDailyAllGroupDaySequenceCounter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public interface ITripDailyAllGroupDaySequenceService {

	public TripDailyAllGroupDaySequenceCounter findNextSequenceNumber(Integer companyId) throws Exception;
	
	public void updateNextSequenceNumber(long nextVal, long sequence_Id) throws Exception;
	
}

package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.TripRouteSequenceCounter;

public interface ITripRouteSequenceService {

	public TripRouteSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception;
	
	public void updateNextSequenceNumber(Integer nextVal, long sequence_Id) throws Exception;
	


}

package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.BusBookingSequenceCounter;

public interface IBusBookingSequenceService {

	public BusBookingSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	public void updateNextNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;

}

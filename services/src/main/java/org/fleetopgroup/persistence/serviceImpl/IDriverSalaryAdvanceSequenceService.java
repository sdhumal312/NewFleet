package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.DriverSalaryAdvanceSequenceCounter;

public interface IDriverSalaryAdvanceSequenceService {

	public DriverSalaryAdvanceSequenceCounter findNextSequenceNumber(Integer companyId)throws Exception;
	
	public void updateNextSequenceCounter(long nextVal , long sequence_id)throws Exception;
	
}

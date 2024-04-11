package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.CashBookSequenceCounter;

public interface ICashBookSequenceService {

	public CashBookSequenceCounter findNextSequenceNumber(Integer companyId)throws Exception;
	
	public void updateNextSequenceCounter(long nextVal , long sequence_id)throws Exception;
}

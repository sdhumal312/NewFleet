package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.RefreshmentSequenceCounter;

public interface IRefreshmentSequenceCounterService {

	public RefreshmentSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	public void updateNextNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;

}

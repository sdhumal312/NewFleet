package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.LorryHirePaymentSequenceCounter;

public interface ILorryHirePaymentSequenceService {

	public LorryHirePaymentSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	public void updateNextNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;

}

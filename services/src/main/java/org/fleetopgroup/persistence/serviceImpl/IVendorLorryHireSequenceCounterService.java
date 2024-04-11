package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.VendorLorryHireSequenceCounter;

public interface IVendorLorryHireSequenceCounterService {

	public VendorLorryHireSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	public void updateNextNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
}

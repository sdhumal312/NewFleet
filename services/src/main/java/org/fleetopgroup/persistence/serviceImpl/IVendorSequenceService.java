package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.VendorSequenceCounter;

public interface IVendorSequenceService {

	public VendorSequenceCounter findNextVendorNumber(Integer companyId) throws Exception;
	
	public void updateNextVendorNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
}

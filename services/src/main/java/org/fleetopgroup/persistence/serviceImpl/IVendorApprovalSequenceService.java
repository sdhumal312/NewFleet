package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.VendorApprovalSequenceCounter;

public interface IVendorApprovalSequenceService {

	public VendorApprovalSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception;
	
	public void updateNextSequenceNumber(long nextVal, long sequece_id)throws Exception;
}

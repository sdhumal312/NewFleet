package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.VendorSubApprovalDetailsSequenceCounter;
import org.springframework.stereotype.Service;

@Service
public interface IVendorSubApprovalDetailsSequenceService {

public VendorSubApprovalDetailsSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	public void updateNextInvoiceNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
}

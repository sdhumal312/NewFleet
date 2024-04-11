package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.VendorPaymentSequenceCounter;

public interface IVendorPaymentSequenceService {

	public VendorPaymentSequenceCounter findNextVendorPaymentNumber(Integer companyId) throws Exception;	
}

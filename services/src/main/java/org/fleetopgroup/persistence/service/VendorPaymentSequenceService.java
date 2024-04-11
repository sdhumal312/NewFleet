package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.VendorPaymentSequenceRepository;
import org.fleetopgroup.persistence.model.VendorPaymentSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IVendorPaymentSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VendorPaymentSequenceService implements IVendorPaymentSequenceService {

	@Autowired private VendorPaymentSequenceRepository		vendorPaymentSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized VendorPaymentSequenceCounter findNextVendorPaymentNumber(Integer companyId) throws Exception {
		VendorPaymentSequenceCounter	counter = vendorPaymentSequenceRepository.findNextVendorPaymentNumber(companyId);
		if(counter != null)
			vendorPaymentSequenceRepository.updateNextVendorPaymentNumber(counter.getNextVal() + 1, companyId, counter.getSequence_Id());
		return counter;
	}
}

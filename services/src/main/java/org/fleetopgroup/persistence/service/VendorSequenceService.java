package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.VendorSequenceRepository;
import org.fleetopgroup.persistence.model.VendorSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IVendorSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VendorSequenceService implements IVendorSequenceService {

	@Autowired private VendorSequenceRepository		vendorSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized VendorSequenceCounter findNextVendorNumber(Integer companyId) throws Exception {
		VendorSequenceCounter	counter = vendorSequenceRepository.findNextVendorNumber(companyId);
		if(counter != null)
			vendorSequenceRepository.updateNextVendorNumber(counter.getNextVal() + 1, companyId, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextVendorNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception {

		vendorSequenceRepository.updateNextVendorNumber(nextVal, companyId, sequence_Id);
	}
	
}

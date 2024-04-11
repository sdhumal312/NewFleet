package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.VendorLorryHireSequenceCounterRepository;
import org.fleetopgroup.persistence.model.VendorLorryHireSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IVendorLorryHireSequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendorLorryHireSequenceCounterService implements IVendorLorryHireSequenceCounterService {

	@Autowired	VendorLorryHireSequenceCounterRepository		vendorLorryHireSequenceCounterRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public VendorLorryHireSequenceCounter findNextNumber(Integer companyId) throws Exception {
		VendorLorryHireSequenceCounter counter = vendorLorryHireSequenceCounterRepository.findNextNumber(companyId);
		if(counter != null)
			vendorLorryHireSequenceCounterRepository.updateNextNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception {
		
		vendorLorryHireSequenceCounterRepository.updateNextNumber(nextVal, sequence_Id);
	}

	
}

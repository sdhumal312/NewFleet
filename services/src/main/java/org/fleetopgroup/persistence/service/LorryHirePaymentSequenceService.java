package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.LorryHirePaymentSequenceRepository;
import org.fleetopgroup.persistence.model.LorryHirePaymentSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ILorryHirePaymentSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LorryHirePaymentSequenceService implements ILorryHirePaymentSequenceService {

	@Autowired	LorryHirePaymentSequenceRepository		lorryHirePaymentSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public LorryHirePaymentSequenceCounter findNextNumber(Integer companyId) throws Exception {
		LorryHirePaymentSequenceCounter counter = lorryHirePaymentSequenceRepository.findNextNumber(companyId);
		if(counter != null)
			lorryHirePaymentSequenceRepository.updateNextNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception {
		
		lorryHirePaymentSequenceRepository.updateNextNumber(nextVal, sequence_Id);
	}

	
}

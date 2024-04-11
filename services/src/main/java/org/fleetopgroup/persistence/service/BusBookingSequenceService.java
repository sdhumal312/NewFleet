package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.BusBookingSequenceRepository;
import org.fleetopgroup.persistence.model.BusBookingSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IBusBookingSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusBookingSequenceService implements IBusBookingSequenceService {

	@Autowired	BusBookingSequenceRepository		busBookingSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public BusBookingSequenceCounter findNextNumber(Integer companyId) throws Exception {
		BusBookingSequenceCounter counter = busBookingSequenceRepository.findNextNumber(companyId);
		if(counter != null)
			busBookingSequenceRepository.updateNextNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception {
		
		busBookingSequenceRepository.updateNextNumber(nextVal, sequence_Id);
	}

	
}

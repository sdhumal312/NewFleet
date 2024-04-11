package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.VehicleAccidentSequenceCounterRepository;
import org.fleetopgroup.persistence.model.VehicleAccidentSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentSequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleAccidentSequenceCounterService implements IVehicleAccidentSequenceCounterService {
	
	@Autowired private	VehicleAccidentSequenceCounterRepository		sequenceRepository;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public VehicleAccidentSequenceCounter findNextNumber(Integer companyId) throws Exception {
		VehicleAccidentSequenceCounter counter = sequenceRepository.findNextNumber(companyId);
		if(counter != null)
			sequenceRepository.updateNextNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}
	
	@Override
	@Transactional
	public void updateNextNumber(long nextVal, long sequence_Id) throws Exception {
		
		sequenceRepository.updateNextNumber(nextVal, sequence_Id);
	}
}

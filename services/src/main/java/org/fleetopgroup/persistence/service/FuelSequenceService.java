package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.FuelSequenceRepository;
import org.fleetopgroup.persistence.model.FuelSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IFuelSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FuelSequenceService implements IFuelSequenceService {

	@Autowired
	private FuelSequenceRepository		fuelSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized FuelSequenceCounter findNextFuelNumber(Integer companyId) throws Exception {
		FuelSequenceCounter	counter = fuelSequenceRepository.findNextFuelNumber(companyId);
		if(counter != null)
			fuelSequenceRepository.updateNextFuelNumber(counter.getNextVal() + 1, companyId, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateNextFuelNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception {
		
		fuelSequenceRepository.updateNextFuelNumber(nextVal, companyId, sequence_Id);
	}

}

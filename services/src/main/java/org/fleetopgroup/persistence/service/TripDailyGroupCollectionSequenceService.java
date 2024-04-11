package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.TripDailyGroupCollectionSequenceRepository;
import org.fleetopgroup.persistence.model.TripDailyGroupCollectionSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ITripDailyGroupCollectionSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional
public class TripDailyGroupCollectionSequenceService implements ITripDailyGroupCollectionSequenceService {

	@Autowired
	TripDailyGroupCollectionSequenceRepository	tripDailyGroupCollectionSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized TripDailyGroupCollectionSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception {
		TripDailyGroupCollectionSequenceCounter	counter = tripDailyGroupCollectionSequenceRepository.findNextSequenceNumber(companyId);
		if(counter != null)
			tripDailyGroupCollectionSequenceRepository.updateNextSequenceNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextSequenceNumber(long nextVal, long sequence_Id) throws Exception {

		tripDailyGroupCollectionSequenceRepository.updateNextSequenceNumber(nextVal, sequence_Id);
	}

}

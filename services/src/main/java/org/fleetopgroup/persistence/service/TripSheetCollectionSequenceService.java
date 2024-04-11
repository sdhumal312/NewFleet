package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.TripSheetCollectionSequenceRepository;
import org.fleetopgroup.persistence.model.TripCollectionSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetCollectionSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TripSheetCollectionSequenceService implements ITripSheetCollectionSequenceService {

	@Autowired
	private TripSheetCollectionSequenceRepository		tripSheetCollectionSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized TripCollectionSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception {
		TripCollectionSequenceCounter	counter = tripSheetCollectionSequenceRepository.findNextSequenceNumber(companyId);
		if(counter != null)
			tripSheetCollectionSequenceRepository.updateNextSequenceNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextSequenceNumber(long nextVal, long sequence_Id) throws Exception {

		tripSheetCollectionSequenceRepository.updateNextSequenceNumber(nextVal, sequence_Id);
	}

}

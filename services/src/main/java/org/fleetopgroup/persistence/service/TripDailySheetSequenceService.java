package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.TripDailySheetSequenceRepository;
import org.fleetopgroup.persistence.model.TripDailySheetSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetSequenceService;
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
public class TripDailySheetSequenceService implements ITripDailySheetSequenceService {

	@Autowired
	private TripDailySheetSequenceRepository 	tripDailySheetSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized TripDailySheetSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception {
		TripDailySheetSequenceCounter	counter = tripDailySheetSequenceRepository.findNextSequenceNumber(companyId);
		if(counter != null)
			tripDailySheetSequenceRepository.updateNextSequenceNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextSequenceNumber(long nextVal, long sequence_Id) throws Exception {

		tripDailySheetSequenceRepository.updateNextSequenceNumber(nextVal, sequence_Id);
	}

}

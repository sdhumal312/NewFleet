package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.TripDailyAllGroupDaySequenceRepository;
import org.fleetopgroup.persistence.model.TripDailyAllGroupDaySequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ITripDailyAllGroupDaySequenceService;
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
public class TripDailyAllGroupDaySequenceService implements ITripDailyAllGroupDaySequenceService {

	@Autowired
	private TripDailyAllGroupDaySequenceRepository		tripDailyAllGroupDaySequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized TripDailyAllGroupDaySequenceCounter findNextSequenceNumber(Integer companyId) throws Exception {
		TripDailyAllGroupDaySequenceCounter	counter = tripDailyAllGroupDaySequenceRepository.findNextSequenceNumber(companyId);
		if(counter != null)
			tripDailyAllGroupDaySequenceRepository.updateNextSequenceNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextSequenceNumber(long nextVal, long sequence_Id) throws Exception {

		tripDailyAllGroupDaySequenceRepository.updateNextSequenceNumber(nextVal, sequence_Id);
	}

}

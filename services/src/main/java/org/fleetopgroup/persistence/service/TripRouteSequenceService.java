package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.TripRouteSequenceRepository;
import org.fleetopgroup.persistence.model.TripRouteSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteSequenceService;
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
public class TripRouteSequenceService implements ITripRouteSequenceService {

	@Autowired
	private TripRouteSequenceRepository		tripRouteSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized TripRouteSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception {
		TripRouteSequenceCounter	counter = tripRouteSequenceRepository.findNextSequenceNumber(companyId);
		if(counter != null)
			tripRouteSequenceRepository.updateNextSequenceNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextSequenceNumber(Integer nextVal, long sequence_Id) throws Exception {

		tripRouteSequenceRepository.updateNextSequenceNumber(nextVal, sequence_Id);
	}

}

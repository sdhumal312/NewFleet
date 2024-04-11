package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.TripsheetSequenceRepository;
import org.fleetopgroup.persistence.model.TripSheetSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ITripsheetSequenceService;
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
public class TripsheetSequenceService implements ITripsheetSequenceService {
	
	@Autowired	private TripsheetSequenceRepository	tripsheetSequenceRepository;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized TripSheetSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception {
		TripSheetSequenceCounter	counter = tripsheetSequenceRepository.findNextSequenceNumber(companyId);
		if(counter != null)
			tripsheetSequenceRepository.updateNextSequenceNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextSequenceNumber(long nextVal, long sequence_Id) throws Exception {

		tripsheetSequenceRepository.updateNextSequenceNumber(nextVal, sequence_Id);
	}

}

package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.CashBookSequenceRepository;
import org.fleetopgroup.persistence.model.CashBookSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ICashBookSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CashBookSequenceService implements ICashBookSequenceService {

	@Autowired
	private CashBookSequenceRepository		cashBookSequenceRepository;	
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized CashBookSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception {
		CashBookSequenceCounter counter = cashBookSequenceRepository.findNextSequenceNumber(companyId);
		if(counter != null)
			cashBookSequenceRepository.updateNextSequenceCounter(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextSequenceCounter(long nextVal, long sequence_id) throws Exception {
		
		cashBookSequenceRepository.updateNextSequenceCounter(nextVal, sequence_id);
	}

}

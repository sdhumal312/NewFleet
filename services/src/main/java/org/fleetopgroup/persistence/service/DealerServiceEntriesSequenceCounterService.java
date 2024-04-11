package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.DealerServiceEntriesSequenceCounterRepository;
import org.fleetopgroup.persistence.model.DealerServiceEntriesSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesSequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DealerServiceEntriesSequenceCounterService implements IDealerServiceEntriesSequenceCounterService {

	@Autowired
	private DealerServiceEntriesSequenceCounterRepository		DSE_SequenceCounterRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized DealerServiceEntriesSequenceCounter findNextDSENumber(Integer companyId) throws Exception {
		DealerServiceEntriesSequenceCounter	counter = DSE_SequenceCounterRepository.findNextDSENumber(companyId);
		if(counter != null)
			DSE_SequenceCounterRepository.updateNextDSENumber(counter.getNextVal() + 1, companyId, counter.getSequence_Id());
		return counter;
	}

	

}

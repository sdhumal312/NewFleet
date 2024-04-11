package org.fleetopgroup.persistence.service;



import org.fleetopgroup.persistence.dao.ServiceEntriesSequenceRepository;
import org.fleetopgroup.persistence.model.ServiceEntriesSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceEntriesSequenceService implements IServiceEntriesSequenceService {
	
	@Autowired
	ServiceEntriesSequenceRepository		serviceEntriesSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized ServiceEntriesSequenceCounter findNextServiceEntries_Number(Integer companyId) throws Exception {
		ServiceEntriesSequenceCounter	counter = serviceEntriesSequenceRepository.findNextServiceEntries_Number(companyId);
		if(counter != null)
			serviceEntriesSequenceRepository.updateNextServiceEntries_Number(counter.getNextVal() + 1, companyId);
		return counter;
	}

	@Override
	public void updateNextServiceEntries_Number(long nextVal, Integer companyId) throws Exception {
		
		serviceEntriesSequenceRepository.updateNextServiceEntries_Number(nextVal, companyId);
	}

}

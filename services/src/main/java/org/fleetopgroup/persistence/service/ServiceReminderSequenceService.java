package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.ServiceReminderSequenceRepository;
import org.fleetopgroup.persistence.model.ServiceReminderSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceReminderSequenceService implements IServiceReminderSequenceService {

	@Autowired
	ServiceReminderSequenceRepository	serviceReminderSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized ServiceReminderSequenceCounter findNextServiceReminderNumber(Integer companyId) throws Exception {
		ServiceReminderSequenceCounter	counter = serviceReminderSequenceRepository.findNextServiceReminderNumber(companyId);
		if(counter != null)
			serviceReminderSequenceRepository.updateNextServiceReminderNumber(counter.getNextVal() + 1, companyId);
		return counter;
	}

	@Override
	public void updateNextServiceReminderNumber(long nextVal, Integer companyId) throws Exception {
		
		serviceReminderSequenceRepository.updateNextServiceReminderNumber(nextVal, companyId);
	}

}

package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.RenewalReminderSequenceRepository;
import org.fleetopgroup.persistence.model.RenewalReminderSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RenewalReminderSequenceService implements IRenewalReminderSequenceService {

	@Autowired
	private RenewalReminderSequenceRepository		renewalReminderSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized RenewalReminderSequenceCounter findNextRenewal_R_Number(Integer companyId) throws Exception {
		try {
			RenewalReminderSequenceCounter	counter = renewalReminderSequenceRepository.findNextRenewal_R_Number(companyId);
			if(counter != null)
				renewalReminderSequenceRepository.updateNextRenewal_R_Number(counter.getNextVal() + 1, companyId);
			return counter;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public void updateNextRenewal_R_Number(long nextVal, Integer companyId) throws Exception {
		try {
			renewalReminderSequenceRepository.updateNextRenewal_R_Number(nextVal, companyId);
		} catch (Exception e) {
			throw e;
		}
	}

}

package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.RenewalApprovalSequenceRepository;
import org.fleetopgroup.persistence.model.RenewalApprovalSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IRenewalApprovalSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RenewalApprovalSequenceService implements IRenewalApprovalSequenceService {

	@Autowired
	RenewalApprovalSequenceRepository	renewalApprovalSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized RenewalApprovalSequenceCounter findNextRenewalApproval_Number(Integer companyId) throws Exception {
		RenewalApprovalSequenceCounter	counter = renewalApprovalSequenceRepository.findNextRenewalApproval_Number(companyId);
		if(counter != null)
			renewalApprovalSequenceRepository.updateNextRenewalApproval_Number(counter.getNextVal() + 1, companyId);
		return counter;
	}

	@Override
	@Transactional
	public void updateNextRenewalApproval_Number(long nextVal, Integer companyId) throws Exception {

		renewalApprovalSequenceRepository.updateNextRenewalApproval_Number(nextVal, companyId);
	}

	
}

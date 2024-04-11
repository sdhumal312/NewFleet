package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.IssuesSequenceRepository;
import org.fleetopgroup.persistence.model.IssuesSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IIssuesSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IssuesSequenceService implements IIssuesSequenceService {

	@Autowired
	private IssuesSequenceRepository		issuesSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized IssuesSequenceCounter findNextIssuesSequence_Number(Integer companyId) throws Exception {
		IssuesSequenceCounter	counter = 	issuesSequenceRepository.findNextIssuesSequence_Number(companyId);
		if(counter != null)
			issuesSequenceRepository.updateNextIssuesSequence_Number(counter.getNextVal() + 1, companyId);
		return counter;
	}

	@Override
	public void updateNextIssuesSequence_Number(long nextVal, Integer companyId) throws Exception {
		
		issuesSequenceRepository.updateNextIssuesSequence_Number(nextVal, companyId);
	}

}

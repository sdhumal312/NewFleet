package org.fleetopgroup.persistence.service;
import org.fleetopgroup.persistence.dao.RequisitionSequenceRepository;
import org.fleetopgroup.persistence.model.RequisitionSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionsequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequisitionsequenceService implements IRequisitionsequenceService {
	
	@Autowired RequisitionSequenceRepository requisitionSequenceRepository;
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized RequisitionSequenceCounter findNextNumber(Integer companyId) throws Exception {
		RequisitionSequenceCounter	counter = requisitionSequenceRepository.findNextNumber(companyId);
		if(counter != null)
			requisitionSequenceRepository.updateNextNumber(counter.getNextVal() + 1, companyId, counter.getSequenceId());
		return counter;
	}

}

package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.WorkOrderSequenceRepository;
import org.fleetopgroup.persistence.model.WorkOrderSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrderSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkOrderSequenceService implements IWorkOrderSequenceService{
	
	@Autowired
	private WorkOrderSequenceRepository		workOrderSequenceRepository;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized WorkOrderSequenceCounter findNextWorkOrderNumber(Integer companyId) throws Exception {
		WorkOrderSequenceCounter	counter = workOrderSequenceRepository.findNextWorkOrderNumber(companyId);
		if(counter != null)
			workOrderSequenceRepository.updateNextWorkOrderNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextWorkOrderNumber(long nextVal, long sequence_Id) throws Exception {

		workOrderSequenceRepository.updateNextWorkOrderNumber(nextVal, sequence_Id);
	}

}

package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.InventoryRequisitionSequenceRepository;
import org.fleetopgroup.persistence.model.InventoryRequisitionSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IInventoryRequisitionSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InventoryRequisitionSequenceService implements IInventoryRequisitionSequenceService{
	
	@Autowired
	private InventoryRequisitionSequenceRepository		inventoryRequisitionSequenceRepository;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized InventoryRequisitionSequenceCounter findNextInventoryRequisitionNumber(Integer companyId) throws Exception {
		InventoryRequisitionSequenceCounter	counter = inventoryRequisitionSequenceRepository.findNextInventoryRequisitionNumber(companyId);
		if(counter != null)	
			inventoryRequisitionSequenceRepository.updateNextInventoryRequisitionNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextInventoryRequisitionNumber(long nextVal, long sequence_Id) throws Exception {

		inventoryRequisitionSequenceRepository.updateNextInventoryRequisitionNumber(nextVal, sequence_Id);
	}

}

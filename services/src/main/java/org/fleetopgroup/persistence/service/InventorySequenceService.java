package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.InventorySequenceRepository;
import org.fleetopgroup.persistence.model.InventorySequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IInventorySequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InventorySequenceService implements IInventorySequenceService {

	@Autowired private InventorySequenceRepository		inventorySequenceRepository;
	@Override
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized InventorySequenceCounter findNextInventoryNumber(Integer companyId, short type) throws Exception {
		InventorySequenceCounter	counter = inventorySequenceRepository.findNextInventoryNumber(companyId, type);
		if(counter != null)
			inventorySequenceRepository.updateNextInventoryNumber(counter.getNextVal() + 1, companyId, counter.getSequence_Id(), type);
		return counter;
	}

	@Override
	@Transactional
	public void updateNextInventoryNumber(long nextVal, Integer companyId, long sequence_Id, short type) throws Exception {
		
		inventorySequenceRepository.updateNextInventoryNumber(nextVal, companyId, sequence_Id, type);
	}

}

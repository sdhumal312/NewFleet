package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.InventoryTyreSequenceRepository;
import org.fleetopgroup.persistence.model.InventoryTyreSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InventoryTyreSequenceService implements IInventoryTyreSequenceService {

	@Autowired
	InventoryTyreSequenceRepository		inventoryTyreSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized InventoryTyreSequenceCounter findNextTyreNumber(Integer companyId) throws Exception {
		try {
			InventoryTyreSequenceCounter	counter = inventoryTyreSequenceRepository.findNextTyreNumber(companyId);
			if(counter != null)
				inventoryTyreSequenceRepository.updateNextTyreNumber(counter.getNextVal() + 1, companyId, counter.getSequence_Id());
			return counter;
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public void updateNextTyreNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception {
		
		inventoryTyreSequenceRepository.updateNextTyreNumber(nextVal, companyId, sequence_Id);
	}

}

package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.PurchaseOrderSequenceRepository;
import org.fleetopgroup.persistence.model.PurchaseOrdersSequeceCounter;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PurchaseOrdersSequenceService implements IPurchaseOrdersSequenceService {

	@Autowired
	private PurchaseOrderSequenceRepository	purchaseOrdersSequenceService;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized PurchaseOrdersSequeceCounter findNextPurchaseOrderNumber(Integer companyId) throws Exception {
		PurchaseOrdersSequeceCounter	counter = purchaseOrdersSequenceService.findNextPurchaseOrderNumber(companyId);
		if(counter != null)
			purchaseOrdersSequenceService.updateNextPurchaseOrderNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextPurchaseOrderNumber(long nextVal, long sequence_Id) throws Exception {
		
		purchaseOrdersSequenceService.updateNextPurchaseOrderNumber(nextVal, sequence_Id);
	}

}

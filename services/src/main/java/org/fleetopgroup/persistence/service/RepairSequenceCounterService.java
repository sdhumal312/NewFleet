package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.RepairSequenceCounterRepository;
import org.fleetopgroup.persistence.model.RepairSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IRepairSequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RepairSequenceCounterService implements  IRepairSequenceCounterService{

	@Autowired
	private RepairSequenceCounterRepository		repairSequenceCounterRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized RepairSequenceCounter findNextNumber(Integer companyId) throws Exception {
		RepairSequenceCounter	counter = null;
		try {
			counter = repairSequenceCounterRepository.findNextRepairNumber(companyId);
			if(counter != null) {
				repairSequenceCounterRepository.updateNextRepairNumber(counter.getNextVal() + 1, companyId, counter.getSequence_Id());
			}else {
				counter = new RepairSequenceCounter();
				counter.setCompanyId(companyId);
				counter.setMarkForDelete(false);
				counter.setNextVal((Integer)1);
				repairSequenceCounterRepository.save(counter);
			}
			return counter;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
}

package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.PartInvoiceSequenceRepository;
import org.fleetopgroup.persistence.model.PartInvoiceSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PartInvoiceSequenceService implements IPartInvoiceSequenceService {

	@Autowired private PartInvoiceSequenceRepository		partInvoiceSequenceRepository;
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized PartInvoiceSequenceCounter findNextInventoryNumber(Integer companyId, short type) throws Exception {
		PartInvoiceSequenceCounter	counter = partInvoiceSequenceRepository.findNextInventoryNumber(companyId, type);
		if(counter != null)
			partInvoiceSequenceRepository.updateNextInventoryNumber(counter.getNextVal() + 1, companyId, counter.getSequence_Id(), type);
		return counter;
	}
	
	@Override
	@Transactional
	public void updateNextInventoryNumber(long nextVal, Integer companyId, long sequence_Id, short type) throws Exception {
		
		partInvoiceSequenceRepository.updateNextInventoryNumber(nextVal, companyId, sequence_Id, type);
	}

}	
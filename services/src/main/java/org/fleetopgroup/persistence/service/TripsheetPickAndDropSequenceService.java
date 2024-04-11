package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.TripsheetPickAndDropSequenceRepository;
import org.fleetopgroup.persistence.model.TripsheetPickAndDropSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ITripsheetPickAndDropSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripsheetPickAndDropSequenceService implements ITripsheetPickAndDropSequenceService {

	@Autowired private TripsheetPickAndDropSequenceRepository		sequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public TripsheetPickAndDropSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception {
		TripsheetPickAndDropSequenceCounter counter = sequenceRepository.findNextInvoiceNumber(companyId);
		if(counter != null)
			sequenceRepository.updateNextInvoiceNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextInvoiceNumber(long nextVal, Integer companyId, long sequence_Id)
			throws Exception {
		
		sequenceRepository.updateNextInvoiceNumber(nextVal, sequence_Id);
	}

}

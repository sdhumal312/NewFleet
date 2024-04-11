package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.TyreSoldInvoiceSequenceRepository;
import org.fleetopgroup.persistence.model.TyreSoldInvoiceSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ITyreSoldInvoiceSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TyreSoldInvoiceSequenceService implements ITyreSoldInvoiceSequenceService {


	@Autowired private TyreSoldInvoiceSequenceRepository		sequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public TyreSoldInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception {
		TyreSoldInvoiceSequenceCounter counter = sequenceRepository.findNextInvoiceNumber(companyId);
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

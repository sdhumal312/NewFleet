package org.fleetopgroup.persistence.service;


import org.fleetopgroup.persistence.dao.UreaInvoiceSequenceRepository;
import org.fleetopgroup.persistence.model.UreaInvoiceSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UreaInvoiceSequenceService implements IUreaInvoiceSequenceService {


	@Autowired private UreaInvoiceSequenceRepository		sequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public UreaInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception {
		UreaInvoiceSequenceCounter counter = sequenceRepository.findNextInvoiceNumber(companyId);
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

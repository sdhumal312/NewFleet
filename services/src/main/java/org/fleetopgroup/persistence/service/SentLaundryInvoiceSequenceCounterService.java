package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.SentLaundryInvoiceSequenceCounterRepository;
import org.fleetopgroup.persistence.model.SentLaundryInvoiceSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ISentLaundryInvoiceSequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SentLaundryInvoiceSequenceCounterService implements ISentLaundryInvoiceSequenceCounterService {

	@Autowired SentLaundryInvoiceSequenceCounterRepository			counterRepository;

	@Override
	@Transactional
	public synchronized SentLaundryInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception {
		SentLaundryInvoiceSequenceCounter	counter = counterRepository.findNextInvoiceNumber(companyId);
		if(counter != null)
			counterRepository.updateNextInvoiceNumber(counter.getNextVal() + 1, companyId);
		return counter;
	}

	@Override
	public void updateNextInvoiceNumber(long nextVal, Integer companyId) throws Exception {

		counterRepository.updateNextInvoiceNumber(nextVal, companyId);
	
	}
	
	
	
}

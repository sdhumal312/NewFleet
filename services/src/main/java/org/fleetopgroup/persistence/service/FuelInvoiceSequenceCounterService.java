package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.FuelInvoiceSequenceCounterRepository;
import org.fleetopgroup.persistence.model.FuelInvoiceSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IFuelInvoiceSequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.antkorwin.xsync.XMutexFactoryImpl;

@Service
public class FuelInvoiceSequenceCounterService implements IFuelInvoiceSequenceCounterService {

	
	@Autowired private FuelInvoiceSequenceCounterRepository		fuelInvoiceSequenceCounterRepository;
	
	XMutexFactoryImpl<Integer> xMutexFactory = new XMutexFactoryImpl<Integer>();

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public FuelInvoiceSequenceCounter findNextNumber(Integer companyId) throws Exception {
		FuelInvoiceSequenceCounter	counter = null;
		synchronized (xMutexFactory.getMutex(companyId)) {
			counter = fuelInvoiceSequenceCounterRepository.findNextNumber(companyId);
			if(counter != null)	
				fuelInvoiceSequenceCounterRepository.updateNextNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		}
		return counter;
	}

	@Override
	@Transactional
	public void updateNextNumber(long nextVal, long sequenceId) throws Exception {

		fuelInvoiceSequenceCounterRepository.updateNextNumber(nextVal, sequenceId);
	}


}

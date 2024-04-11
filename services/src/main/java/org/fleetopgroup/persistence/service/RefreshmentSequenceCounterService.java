package org.fleetopgroup.persistence.service;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.RefreshmentSequenceCounterRepository;
import org.fleetopgroup.persistence.model.RefreshmentSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IRefreshmentSequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshmentSequenceCounterService implements IRefreshmentSequenceCounterService {


	@Autowired	RefreshmentSequenceCounterRepository		refreshmentSequenceCounterRepository;
	
	@Override
	@Transactional
	public RefreshmentSequenceCounter findNextNumber(Integer companyId) throws Exception {
		RefreshmentSequenceCounter counter = refreshmentSequenceCounterRepository.findNextNumber(companyId);
		if(counter != null)
			refreshmentSequenceCounterRepository.updateNextNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception {
		
		refreshmentSequenceCounterRepository.updateNextNumber(nextVal, sequence_Id);
	}

	

}

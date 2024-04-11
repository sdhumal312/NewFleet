package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.DriverSalaryAdvanceSequenceRepository;
import org.fleetopgroup.persistence.model.DriverSalaryAdvanceSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional
public class DriverSalaryAdvanceSequenceService implements IDriverSalaryAdvanceSequenceService {

	@Autowired
	private DriverSalaryAdvanceSequenceRepository	driverSalaryAdvanceSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized DriverSalaryAdvanceSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception {
		DriverSalaryAdvanceSequenceCounter	counter = driverSalaryAdvanceSequenceRepository.findNextSequenceNumber(companyId);
		if(counter != null)
			driverSalaryAdvanceSequenceRepository.updateNextSequenceCounter(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextSequenceCounter(long nextVal, long sequence_id) throws Exception {

		driverSalaryAdvanceSequenceRepository.updateNextSequenceCounter(nextVal, sequence_id);
	}

}

package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.RepairSequenceCounter;

public interface IRepairSequenceCounterService {

	public RepairSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
}

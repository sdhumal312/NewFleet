package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.DealerServiceEntriesSequenceCounter;

public interface IDealerServiceEntriesSequenceCounterService {

	public DealerServiceEntriesSequenceCounter findNextDSENumber(Integer companyId) throws Exception;
	
}

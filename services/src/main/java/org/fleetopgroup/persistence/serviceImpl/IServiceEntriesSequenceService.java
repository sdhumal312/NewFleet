package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.ServiceEntriesSequenceCounter;

public interface IServiceEntriesSequenceService {


	public ServiceEntriesSequenceCounter findNextServiceEntries_Number(Integer companyId) throws Exception;
	
	public void updateNextServiceEntries_Number(long nextVal, Integer companyId) throws Exception;

}

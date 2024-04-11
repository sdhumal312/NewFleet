package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.InventoryTyreSequenceCounter;

public interface IInventoryTyreSequenceService {


	public InventoryTyreSequenceCounter findNextTyreNumber(Integer companyId) throws Exception;
	
	public void updateNextTyreNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;

}

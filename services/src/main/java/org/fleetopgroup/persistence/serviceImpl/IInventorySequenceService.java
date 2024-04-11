package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.InventorySequenceCounter;

public interface IInventorySequenceService {

	public InventorySequenceCounter findNextInventoryNumber(Integer companyId, short type) throws Exception;
	
	public void updateNextInventoryNumber(long nextVal, Integer companyId, long sequence_Id, short type) throws Exception;
}

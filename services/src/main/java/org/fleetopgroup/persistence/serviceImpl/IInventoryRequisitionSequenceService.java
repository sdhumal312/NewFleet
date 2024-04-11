package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.InventoryRequisitionSequenceCounter;

public interface IInventoryRequisitionSequenceService {

	public InventoryRequisitionSequenceCounter findNextInventoryRequisitionNumber(Integer companyId) throws Exception;
	
	public void updateNextInventoryRequisitionNumber(long nextVal, long sequence_Id) throws Exception;
}

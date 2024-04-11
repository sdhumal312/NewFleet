package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.WorkOrderSequenceCounter;

public interface IWorkOrderSequenceService {

	public WorkOrderSequenceCounter findNextWorkOrderNumber(Integer companyId) throws Exception;
	
	public void updateNextWorkOrderNumber(long nextVal, long sequence_Id) throws Exception;
}

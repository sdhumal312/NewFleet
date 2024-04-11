package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.FuelSequenceCounter;

public interface IFuelSequenceService {

	public FuelSequenceCounter findNextFuelNumber(Integer companyId) throws Exception;
	
	public void updateNextFuelNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
}

package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.VehicleAccidentSequenceCounter;

public interface IVehicleAccidentSequenceCounterService {

	public VehicleAccidentSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	public void updateNextNumber(long nextVal, long sequence_Id) throws Exception;
}

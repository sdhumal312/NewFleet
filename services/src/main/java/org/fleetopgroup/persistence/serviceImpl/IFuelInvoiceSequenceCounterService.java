package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.FuelInvoiceSequenceCounter;

public interface IFuelInvoiceSequenceCounterService {

	public FuelInvoiceSequenceCounter findNextNumber(Integer companyId) throws Exception;
	
	public void updateNextNumber(long nextVal, long sequence_Id) throws Exception;
}

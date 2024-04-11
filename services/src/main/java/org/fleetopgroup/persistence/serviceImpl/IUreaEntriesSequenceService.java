package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.UreaEntriesSequenceCounter;

public interface IUreaEntriesSequenceService {

	public UreaEntriesSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	public void updateNextInvoiceNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;


}

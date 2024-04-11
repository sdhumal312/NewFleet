package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.UreaInvoiceSequenceCounter;

public interface IUreaInvoiceSequenceService {

	public UreaInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	public void updateNextInvoiceNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;

}

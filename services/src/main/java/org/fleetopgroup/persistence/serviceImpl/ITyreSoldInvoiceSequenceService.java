package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.TyreSoldInvoiceSequenceCounter;

public interface ITyreSoldInvoiceSequenceService {

	public TyreSoldInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	public void updateNextInvoiceNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;

}
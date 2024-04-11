package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.SentLaundryInvoiceSequenceCounter;

public interface ISentLaundryInvoiceSequenceCounterService {
	
	public SentLaundryInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	public void updateNextInvoiceNumber(long nextVal, Integer companyId) throws Exception;

}

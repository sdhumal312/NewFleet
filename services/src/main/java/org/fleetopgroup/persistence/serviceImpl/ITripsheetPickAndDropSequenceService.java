package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.TripsheetPickAndDropSequenceCounter;
import org.springframework.stereotype.Service;

@Service
public interface ITripsheetPickAndDropSequenceService {

	public TripsheetPickAndDropSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	public void updateNextInvoiceNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
}
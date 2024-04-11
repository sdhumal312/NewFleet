package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.BatteryInvoiceSequenceCounter;
import org.springframework.stereotype.Service;

@Service
public interface IBatteryInvoiceSequenceService {

	public BatteryInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	public void updateNextInvoiceNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;

}

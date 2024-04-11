package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.ClothInvoiceSequenceCounter;
import org.springframework.stereotype.Service;

@Service
public interface IClothInvoiceSequenceService {

public ClothInvoiceSequenceCounter findNextInvoiceNumber(Integer companyId) throws Exception;
	
	public void updateNextInvoiceNumber(long nextVal, Integer companyId, long sequence_Id) throws Exception;
}

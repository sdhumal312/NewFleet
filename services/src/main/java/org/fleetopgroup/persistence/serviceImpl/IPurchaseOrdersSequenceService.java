package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.PurchaseOrdersSequeceCounter;

public interface IPurchaseOrdersSequenceService {


	public PurchaseOrdersSequeceCounter findNextPurchaseOrderNumber(Integer companyId) throws Exception;
	
	public void updateNextPurchaseOrderNumber(long nextVal,long sequence_Id) throws Exception;

}

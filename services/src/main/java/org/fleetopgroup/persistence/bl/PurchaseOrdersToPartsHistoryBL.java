package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.model.PurchaseOrdersToParts;
import org.fleetopgroup.persistence.model.PurchaseOrdersToPartsHistory;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrdersToPartsHistoryBL {
	
	public PurchaseOrdersToPartsHistory purchaseOrdersToPartsHistory (PurchaseOrdersToParts  purchaseOrders, PurchaseOrdersToParts currPurchaseOrdersTasksToParts)  {
		PurchaseOrdersToPartsHistory 	purchaseOrdersHistory 	= null;
		
		try {
			
			purchaseOrdersHistory		= new PurchaseOrdersToPartsHistory();
			
			purchaseOrdersHistory.setPurchaseorderto_partid(purchaseOrders.getPurchaseorderto_partid());
			purchaseOrdersHistory.setPartid(purchaseOrders.getPartid());
			purchaseOrdersHistory.setCompanyId(purchaseOrders.getCompanyId());
			purchaseOrdersHistory.setRemark(currPurchaseOrdersTasksToParts.getReceived_quantity_remark());
			purchaseOrdersHistory.setReceived_quantity(purchaseOrders.getReceived_quantity());
			

		} catch (Exception e) {
			throw e;
		}
		
		return purchaseOrdersHistory ;
	}

}

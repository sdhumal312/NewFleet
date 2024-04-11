package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;

public interface IRequisitionReceivelService {
	
	public ValueObject receiveTransferByApprovalId(ValueObject object);
	
	public Double calculateInventoryTotal(short discountTaxTypeId,Double quantity,Double eachCost,double disCa,Double taxCa);
	
	public ValueObject rejectReceivalApprove(ValueObject object);
	
	public ValueObject createPOFromRequisition(ValueObject object);
	
	public ValueObject getTransactionName(Long subRequisitionId,int companyId);

	public void sendNotification(ValueObject object);
	
	public boolean validateUserPermission(ValueObject object);
	
	public ValueObject markRequisitionAsComplete(ValueObject object);
	
	public void updateRequisitionStatus(ValueObject object);
	
	public ValueObject getTyreBattaryReceiveList(ValueObject object,short type) throws Exception;
	
	public ValueObject receiveAllPartApprovals(ValueObject object) throws Exception;
}

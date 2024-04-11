package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToDebitNoteDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToPartsDto;
import org.fleetopgroup.persistence.model.PurchaseOrdersToUrea;
import org.fleetopgroup.web.util.ValueObject;

public interface IPurchaseOrdersToPartsService {

	public List<PurchaseOrdersToPartsDto> getPurchaseOrdersPartDetailsByType(ValueObject valueObject, CustomUserDetails userDetails)throws Exception;

	public void savePurchaseOrderPartDetails(ValueObject valueObject, CustomUserDetails userDetails)throws Exception;

	public void deletePurchaseOrderPartDetails(ValueObject valueObject, CustomUserDetails userDetails)throws Exception;

	public void updateReceivedQuantityOfPart(ValueObject valueObject, CustomUserDetails userDetails)throws Exception;

	public List<PurchaseOrdersToDebitNoteDto> getPurchaseOrdersDebitNoteDetailsByPurchaseType(ValueObject valueInObject, CustomUserDetails userDetails)throws Exception;

	public List<PurchaseOrdersToUrea> getPurchaseOrdersToUreaByPurchaseOrderId(Long purchaseOrdersId, Integer companyId) throws Exception;
	
	public void purchaseOrdersUpdateFromReceivedToOrderedStatus(Long purchaseOrdersId, Integer companyId)throws Exception;
	
}

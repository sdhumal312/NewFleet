package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryUpholsteryTransferDto;
import org.fleetopgroup.persistence.model.InventoryUpholsteryTransfer;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IInventoryUpholsteryTransferService {
	
	public ValueObject saveUpholsteryTransfer(ValueObject valueObject) throws Exception;
	
	public void updateInTransferQtyInStockTypeDetails(InventoryUpholsteryTransfer inTransfer) throws Exception;
	
	public ValueObject getTransferReceivedDetails(ValueObject valueObject) throws Exception;
	
	public List<InventoryUpholsteryTransferDto> getTransferReceivedDetailsList(Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	public ValueObject getReceiveDetails(ValueObject valueObject) throws Exception;
	
	public InventoryUpholsteryTransferDto getReceiveDetailsList(long upholsteryTransferId, Integer companyId) throws Exception;
	
	public void updateReceiveUpholstery(ValueObject valueObject) throws Exception;
	
	public ValueObject saveReceiveUpholstery(ValueObject valueObject) throws Exception;
	
	public ValueObject saveRejectUpholstery(ValueObject valueObject) throws Exception;
	
	public void updateRejectUpholstery(ValueObject valueObject) throws Exception;
	
	public void updateStockQuantity(Double quantity ,long location, int companyId,long clothTypesId);
	
	public void addRejectedStockQuantity(Double quantity, int companyId,long stockTypeDetailsId);
}
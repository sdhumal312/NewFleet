package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.InventoryDamageAndLostHistoryDto;
import org.fleetopgroup.persistence.model.InventoryDamageAndLostHistory;


public interface IInventoryDamageAndLostHistoryService {
	
	public void saveInventoryDamageAndLostHistory(InventoryDamageAndLostHistory inventoryDamageHistory) throws Exception;
	
	public List<InventoryDamageAndLostHistoryDto> getInDamageDetailsList(Integer pageNumber, long clothTypeId, Integer locationId) throws Exception;
	
	public List<InventoryDamageAndLostHistoryDto> getInLostDetailsList(Integer pageNumber, long clothTypeId, Integer locationId) throws Exception;
	
	public void updateDamageQty(Long laundryInvoiceId, Long	clothTypesId, double damagedQuantity) throws Exception;
	
	public void updateLostQty(Long laundryInvoiceId, Long clothTypesId, double losedQuantity) throws Exception;
}
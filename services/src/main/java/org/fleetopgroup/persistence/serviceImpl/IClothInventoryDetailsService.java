package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.ClothInventoryDetailsDto;

public interface IClothInventoryDetailsService {

	List<ClothInventoryDetailsDto>   getClothInventoryDetailsListById(Long	invoiceId, Integer companyId) throws Exception;
	
	public void updateClothInventoryQuantity(Double quantity,int locationId,long inventoryId);
}

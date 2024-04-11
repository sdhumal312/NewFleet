package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.ClothInventoryStockTypeDetailsDto;
import org.fleetopgroup.web.util.ValueObject;

public interface IClothInventoryStockTypeDetailsService {

	List<ClothInventoryStockTypeDetailsDto>  getLocationClothDetails(Integer locationId, Integer pageNumber) throws Exception;
	
	public void updateLocationStockDetails(ValueObject	valueObject) throws Exception;
	
	public void removeVehicleClothToStockDetails(ValueObject valueObject) throws Exception;
	
	ClothInventoryStockTypeDetailsDto		getClothLocationQuantity(Long clothesTypeId, Integer locationId, Integer companyId) throws Exception;
	
	public void updateLocationInWashingStockDetails(Integer	locationId, Long clothTypesId, Double quantity) throws Exception;
	
	public void updateLocationStockDetailsToReceive(ValueObject valueObject) throws Exception;
	
	public void updateLocationStockDetailsToDamage(ValueObject valueObject) throws Exception;
	
	public void updateLocationStockDetailsToLost(ValueObject valueObject) throws Exception; 
	
	public void updateClothDamageDetails(ValueObject valueObject) throws Exception; 
	
	public void updateClothLostDetails(ValueObject valueObject) throws Exception;
	
	public void removeVehicleUpdateStockDetailsOfUsedStckQty(ValueObject valueObject) throws Exception;
	
	public void removeVehicleUpdateStockDetailsOfInService(ValueObject valueObject) throws Exception;
	
	public void updateUsedStckQty(long clothTypeId, int locationId, double quantity) throws Exception;
	
	public void updateNewStckQty(long clothTypeId, int locationId, double quantity) throws Exception; 
	
	public void updateTransferQty(long clothTypeId, int locationId, double quantity) throws Exception;
	
	public void updateRejectTransferQty(long clothTypeId, int locationId, double quantity, short stockTypeId) throws Exception;
	
	public void updateStockDetailsForLaundryDelete(long clothTypeId, int locationId, double quantity) throws Exception;

	public ValueObject getLocationWiseUpholsteryQuantity(ValueObject object)throws Exception;
	
}

package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleClothInventoryDetailsDto;
import org.fleetopgroup.persistence.model.VehicleClothInventoryDetails;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleClothInventoryDetailsService {

	public void saveVehicleClothInventoryDetails(VehicleClothInventoryDetails 	inventoryDetails) throws Exception;
	
	List<VehicleClothInventoryDetailsDto>  getVehicleClothInventoryDetailsList(Integer	vid) throws Exception;

	VehicleClothInventoryDetailsDto  getVehicleClothInventoryDetails(Long vehicleClothInventoryDetailsId) throws Exception;
	
	public void removeClothDetailsFromVehicle(VehicleClothInventoryDetailsDto	clothInventoryDetailsDto) throws Exception;
	
	public VehicleClothInventoryDetails	validateVehicleClothInventoryDetails(Long clothTypesId, Integer vehicleId, Integer locationId) throws Exception;
}

package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleClothInventoryHistoryDto;
import org.fleetopgroup.persistence.model.VehicleClothInventoryHistory;
import org.fleetopgroup.web.util.ValueObject;

public interface IVehicleClothInventoryHistoryService {

	public void saveVehicleClothInventoryHistory(VehicleClothInventoryHistory	vehicleClothInventoryHistory) throws Exception;

	public List<VehicleClothInventoryHistoryDto> getUpholsteryConsumptionList(ValueObject object) throws Exception;
}

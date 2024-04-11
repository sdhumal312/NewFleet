package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.dto.VehicleModelTyreLayoutDto;
import org.fleetopgroup.persistence.model.VehicleModelTyreLayout;
import org.fleetopgroup.web.util.ValueObject;


public interface IVehicleModelTyreLayoutService {

	public ValueObject saveVehicleModelTyreLayout(ValueObject valueObject) throws Exception;

	public ValueObject getVehicleModelTyreLayout(ValueObject valueInObject)throws Exception;

	public ValueObject getVehicleModelTyreLayoutPositionByPosition(ValueObject valueOutObject) throws Exception;

	public ValueObject deleteVehicleModelTyreLayout(ValueObject valueOutObject) throws Exception;

	public ValueObject getVehicleModelTyreDetails(ValueObject object)throws Exception;

	public ValueObject getVehicleModelTyreLayoutPositionByVehicleModelId(ValueObject valueObject) throws Exception;

	public VehicleModelTyreLayoutDto getVehicleModelTyreLayoutDetails(ValueObject valueObject) throws Exception;
	

	public VehicleModelTyreLayout validateVehicleModelTyreLayout(ValueObject valueObject) throws Exception;

	public Long checkAssignTyre(ValueObject valueObject) throws Exception;

	//public ValueObject tyreAssignToVehicle(ValueObject object)throws Exception;

	//public ValueObject getTyreAssignedToVehicleDetails(ValueObject object)throws Exception;

	//public ValueObject validateTyreAssignToVehicle(ValueObject valueObject) throws Exception;

	
}
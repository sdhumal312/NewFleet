package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleBodyMaker;
import org.fleetopgroup.web.util.ValueObject;

public interface IVehicleBodyMakerService {
	
	public List<VehicleBodyMaker>getVehicleBodyMaker(int companyId);
	
	public ValueObject validateVehicleBodyMaker(ValueObject object);
	
	public ValueObject deleteVehicleBodyMakerById(ValueObject object);
	
	public ValueObject updateVehicleBodyMaker(ValueObject object);
	
	public List<VehicleBodyMaker> getVehicleBodyMakerList(String search,int companyId);

}

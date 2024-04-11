package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.VehicleTollDetails;
import org.fleetopgroup.web.util.ValueObject;

public interface IVehicleTollService {

	ValueObject  getVehicleTollDetailsList(ValueObject	valueObject)throws Exception;
	
	ValueObject  saveVehicleToll(ValueObject	valueObject)throws Exception;
	
	ValueObject  getVehicleTollById(ValueObject	valueObject)throws Exception;
	
	ValueObject  editVehicleToll(ValueObject	valueObject)throws Exception;
	
	VehicleTollDetails  getVehicleTollByVid(Integer vid)throws Exception;
	
	ValueObject  getDeleteVehicleToll(ValueObject	valueObject)throws Exception;
}

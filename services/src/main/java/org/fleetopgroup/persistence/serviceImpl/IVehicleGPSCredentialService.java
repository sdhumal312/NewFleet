package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;

public interface IVehicleGPSCredentialService {

	ValueObject	getVehicleGPSCredentialList(ValueObject	valueObject)throws Exception;
	
	ValueObject	saveVehicleGPSCredential(ValueObject	valueObject)throws Exception;
	
	ValueObject  getVehicleGPSCredentialById(ValueObject	valueObject)throws Exception;
	
	ValueObject  editVehicleGPSCredential(ValueObject	valueObject)throws Exception;
	
	ValueObject  deleteVehicleGPSCredential(ValueObject	valueObject)throws Exception;
}

package org.fleetopgroup.persistence.report.dao.impl;

import org.fleetopgroup.web.util.ValueObject;

public interface IVehicleAccidentTypeMasterService {

	public ValueObject getAllVehicleAccidentTypeMaster(ValueObject valueObject)throws Exception;
	
	public ValueObject saveVehicleAccidentTypeMaster(ValueObject valueOutObject)throws Exception;

	public ValueObject getVehicleAccidentTypeMasterById(ValueObject valueObject) throws Exception;

	public ValueObject updateVehicleAccidentTypeMaster(ValueObject valueInObject)throws Exception;

	public ValueObject deleteVehicleAccidentTypeMaster(ValueObject valueInObject)throws Exception;

}

package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;

public interface IStatusChangeRemarkService {
	
	ValueObject saveVehicleStatusRemark(ValueObject valueObject)throws Exception;

	ValueObject getStatusChangeRemarkHistory(ValueObject valueObject) throws Exception;

}

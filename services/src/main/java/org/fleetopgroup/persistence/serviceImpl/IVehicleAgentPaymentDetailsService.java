package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.VehicleAgentPaymentDetails;
import org.fleetopgroup.web.util.ValueObject;

public interface IVehicleAgentPaymentDetailsService {

	public ValueObject	saveVehicleAgentPayment(ValueObject	valueObject) throws Exception;
	
	public VehicleAgentPaymentDetails	validatePaymentAfterDate(String date, Integer vid)throws Exception;
}

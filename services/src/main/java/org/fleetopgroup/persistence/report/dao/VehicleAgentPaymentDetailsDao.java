package org.fleetopgroup.persistence.report.dao;

import org.fleetopgroup.persistence.model.VehicleAgentPaymentDetails;

public interface VehicleAgentPaymentDetailsDao {

	public void saveVehicleAgentPayment(VehicleAgentPaymentDetails	vehicleAgentPaymentDetails) throws Exception;
	
	public VehicleAgentPaymentDetails validatePaymentAfterDate(String date, Integer vid) throws Exception ;
}

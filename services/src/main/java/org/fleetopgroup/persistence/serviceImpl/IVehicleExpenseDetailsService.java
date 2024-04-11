package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleExpenseDetails;

public interface IVehicleExpenseDetailsService {

	public abstract VehicleExpenseDetails	getVehicleExpenseDetails(Integer fuelId, Integer companyId, Integer vid, short type) throws Exception;
	
	public abstract VehicleExpenseDetails	getVehicleExpenseDetails(Long fuelId, Integer companyId, Integer vid, short type) throws Exception;
	
	public abstract List<VehicleExpenseDetails>	getVehicleExpenseDetailsList(Long fuelId, Integer companyId, Integer vid) throws Exception;
}

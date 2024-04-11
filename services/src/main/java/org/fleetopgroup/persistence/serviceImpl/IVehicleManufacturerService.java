package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleManufacturer;
import org.fleetopgroup.web.util.ValueObject;

//import com.fleetop.dto.UploadFile;

public interface IVehicleManufacturerService {
	
	public ValueObject getVehicleManufacturerAutocomplete(String term)throws Exception;
	
	public ValueObject getAllVehicleManufacturer()throws Exception;

	public VehicleManufacturer validateVehicleManufacturer(String vehicleManufacturerName, Integer companyId) throws Exception;
	
	public ValueObject saveVehicleManufacturer(ValueObject valueObject) throws Exception;

	public ValueObject getVehicleManufacturerById(ValueObject valueInObject)throws Exception;

	public ValueObject updateVehicleManufacturer(ValueObject valueInObject)throws Exception;

	public ValueObject deleteVehicleManufacturer(ValueObject valueInObject)throws Exception;
	
	List<VehicleManufacturer>  getAllVehiclManufacturer(Integer	companyId) throws Exception;

	

	
}
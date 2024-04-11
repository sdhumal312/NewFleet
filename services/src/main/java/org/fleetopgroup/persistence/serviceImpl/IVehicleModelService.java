package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleModelDto;
import org.fleetopgroup.persistence.model.VehicleModel;
import org.fleetopgroup.web.util.ValueObject;

//import com.fleetop.dto.UploadFile;

public interface IVehicleModelService {
	
	public ValueObject getVehicleModelAutocomplete(String term)throws Exception;
	
	public ValueObject getAllVehicleModel()throws Exception;

	public VehicleModel validateVehicleModel(String vehicleManufacturerName, Integer companyId) throws Exception;
	
	public ValueObject saveVehicleModel(ValueObject valueObject) throws Exception;

	public ValueObject getVehicleModelById(ValueObject valueInObject)throws Exception;

	public ValueObject updateVehicleModel(ValueObject valueInObject)throws Exception;

	public ValueObject deleteVehicleModel(ValueObject valueInObject)throws Exception;
	
	List<VehicleModelDto>  getVehicleModelListByCompanyId(Integer companyId) throws Exception;
	
	List<VehicleModelDto>  getVehicleModelListByCompanyIdAndManufacturer(Integer companyId, String query) throws Exception;

	

	
}
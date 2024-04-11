package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleFuelDto;
import org.fleetopgroup.persistence.model.VehicleFuel;
import org.fleetopgroup.persistence.model.VehicleFuelPermission;
import org.fleetopgroup.web.util.ValueObject;

public interface IVehicleFuelService {

    VehicleFuel registerNewVehicleFuel(VehicleFuelDto accountDto) throws Exception;
    
    VehicleFuelPermission	addNewVehicleFuel(VehicleFuelPermission fuelPermission) throws Exception;
    
    void updateVehicleFuel(String vFuel, long fid) throws Exception;
    
    List<VehicleFuel> findAll();
    
    List<VehicleFuel> findAllVehicleFuel();
    
    VehicleFuel getVehicleFuel(String verificationToken);

    void deleteVehicleFuel(long fid);
    
    void deleteVehicleFuelPermission(long fid, Integer companyId);

    VehicleFuel getVehicleFuelByID(long fid);
    
    VehicleFuelPermission getVehicleFuelPermissionByID(long fid, Integer companyId);
    
    VehicleFuel findByVFuel(String vFuel);

    long count();
    
    public List<VehicleFuel> getFuelTypeListDropDown(String term, Integer companyId) throws Exception;
    
    public ValueObject getFuelTypeNameWiseList(ValueObject object) throws Exception;
   
}

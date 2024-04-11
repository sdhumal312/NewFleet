package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleStatus;
import org.fleetopgroup.persistence.model.VehicleStatusPermission;

public interface IVehicleStatusService {

    VehicleStatus registerNewVehicleStatus(VehicleStatus accountDto) throws Exception;
    
    VehicleStatusPermission addNewVehicleStatusPermission(VehicleStatusPermission permission) throws Exception;
    
    void updateVehicleStatus(String vStatus, String updateBy, Date updateDate, long sid) throws Exception;
    
    List<VehicleStatus> findAll();
    
    List<VehicleStatus> findAllVehicleStatus();
    
    VehicleStatus findByVStatus(String vStatus) throws Exception;
    
    VehicleStatus getVehicleStatus(String verificationToken);

    void saveRegisteredVehicleStatus(VehicleStatus VehicleStatus);

    void deleteVehicleStatus(long sid);
    void deleteVehicleStatusPermission(long sid, Integer companyId);

    VehicleStatus getVehicleStatusByID(long sid);
    
    VehicleStatusPermission getVehicleStatusPermissionByID(long sid, Integer companyId);

    long count();

   
}

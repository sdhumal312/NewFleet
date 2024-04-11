package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleGroup;

public interface IVehicleGroupService {

    VehicleGroup registerNewVehicleGroup(VehicleGroup accountDto) throws Exception;
    
    void updateVehicleGroup(String vGroup,  Long updateById, Date updateDate,  long gid, Integer companyId) throws Exception;
    
    List<VehicleGroup> findAll();
    
    VehicleGroup findByVGroup(String vGroup, Integer company_Id) throws Exception;
    
    VehicleGroup getVehicleGroup(String verificationToken);

    void deleteVehicleGroup(long gid, Integer companyId);

    VehicleGroup getVehicleGroupByID(long gid);

    long count();
    
    List<VehicleGroup> findAllVehicleGroupByCompanyId(Integer company_Id) throws Exception;
    
    long findCountOfVehicleGroupByCompanyId(Integer company_Id) throws Exception;
    
    List<VehicleGroup> getVehiclGroupByPermission(Integer company_Id) throws Exception;
    
    public List<VehicleGroup> getVehiclGroupByPermissionForMobile(Integer company_Id, long userId) throws Exception;
    
    HashMap<Long, VehicleGroup>  getVehicleGroupHMByCompanyId(Integer	companyId)	throws Exception; 
    

}

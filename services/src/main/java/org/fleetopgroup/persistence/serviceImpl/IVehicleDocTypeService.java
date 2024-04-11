package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleDocType;

public interface IVehicleDocTypeService {

    VehicleDocType registerNewVehicleDocType(VehicleDocType accountDto) throws Exception;
    
    void updateVehicleDocType(String vDocType, Long modifiedById, Timestamp modifiedOn,  long dtid, Integer companyId) throws Exception;
    
    List<VehicleDocType> findAll();
    
    VehicleDocType getVehicleDocType(String verificationToken);

    void saveRegisteredVehicleDocType(VehicleDocType VehicleDocType);

    void deleteVehicleDocType(long dtid, Integer companyId);

    VehicleDocType getVehicleDocTypeByID(long dtid, Integer companyId);

    long count();
    
    VehicleDocType findByVDocType(String vDocType);

    List<VehicleDocType> findAllVehicleDocTypeByCompanyId(Integer company_Id) throws Exception;
   
    long	getVehicleDocTypeCountByCompanyId(Integer company_Id);
    
    List<VehicleDocType> findByVDocTypeAndCompanyId(Integer company_Id, String vDocType) throws Exception;
}

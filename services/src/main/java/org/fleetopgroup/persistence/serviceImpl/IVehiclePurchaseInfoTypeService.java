package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehiclePurchaseInfoTypeDto;
import org.fleetopgroup.persistence.model.VehiclePurchaseInfoType;

public interface IVehiclePurchaseInfoTypeService {

    VehiclePurchaseInfoType registerNewVehiclePurchaseInfoType(VehiclePurchaseInfoTypeDto accountDto ,Integer company_Id, Long createdById, Timestamp createdOn) throws Exception;
    
    void updateVehiclePurchaseInfoType(String vPurchaseInfoType, Long modifiedById, Timestamp modifiedOn, long ptid, Integer company_Id) throws Exception;
    
    List<VehiclePurchaseInfoType> findAll();
    
    VehiclePurchaseInfoType findByVPurchaseInfoType(String vPurchaseInfoType, Integer company_Id) throws Exception;

    void deleteVehiclePurchaseInfoType(long ptid, Integer companyId);

    VehiclePurchaseInfoType getVehiclePurchaseInfoTypeByID(long ptid, Integer companyId);

    long count();

    List<VehiclePurchaseInfoType> findAllByCompanyId(Integer company_Id) throws Exception;
    
    long getCountByCompanyId(Integer company_Id) throws Exception;
   
}

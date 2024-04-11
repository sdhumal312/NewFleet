package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.VehiclePhoType;
import org.fleetopgroup.web.util.ValueObject;

public interface IVehiclePhoTypeService {

    VehiclePhoType registerNewVehiclePhoType(VehiclePhoType accountDto) throws Exception;
    
    void updateVehiclePhoType(String vPhoType, String modifiedBy, Timestamp  modifiedOn, short vehiclePhotoTypeId, long ptid, Integer companyId) throws Exception;
    
    List<VehiclePhoType> findAll();
    
    VehiclePhoType getVehiclePhoType(String verificationToken);

    void deleteVehiclePhoType(long ptid, Integer companyId);

    VehiclePhoType getVehiclePhoTypeByID(long ptid, Integer companyId);

    long count();

    List<VehiclePhoType> findByVPhoTypeByCompanyId(String vPhoType, Integer company_Id) throws Exception;
    
    List<VehiclePhoType> findAllVehiclePhoToTypeByCompanyId(Integer company_Id) throws Exception;
    
    long	countVehiclePhotoTypeByCompanyId(Integer company_Id) throws Exception;

	List<VehiclePhoType> validateUpdateVehiclePhotoType(String vPhoType, Integer company_Id, long ptid)throws Exception;

	List<VehiclePhoType> getAllVehicleAccidentDocumentType(ValueObject valueObject)throws Exception;

}

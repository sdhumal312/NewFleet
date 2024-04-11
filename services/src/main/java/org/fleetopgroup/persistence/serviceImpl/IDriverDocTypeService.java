package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.DriverDocType;

public interface IDriverDocTypeService {

    DriverDocType registerNewDriverDocType(DriverDocType accountDto) throws Exception;
    
    void updateDriverDocType(String dri_DocType, Long modifiedBy, Timestamp modifiedOn, long dri_id, Integer companyId) throws Exception;
    
    List<DriverDocType> findAll();
    
    DriverDocType getDriverDocType(String verificationToken);

    void deleteDriverDocType(long dri_id, Integer companyId);

    DriverDocType getDriverDocTypeByID(long dri_id, Integer companyid);
    
    DriverDocType validateDriDocType(String dri_DocType, Integer company_Id);
    
    List<DriverDocType> findAllByCompanyId(Integer company_Id) throws Exception;

    long count();

    DriverDocType findByDocType(Integer company_Id)throws Exception ;

   
}

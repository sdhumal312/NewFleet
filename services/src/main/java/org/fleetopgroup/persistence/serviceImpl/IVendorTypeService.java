package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.VendorType;
import org.fleetopgroup.web.util.ValueObject;

public interface IVendorTypeService {

    public VendorType registerNewVendorType(VendorType GET_DocType) throws Exception;
    
    void updateVendorType(String vendor_TypeName, long vendor_Typeid) throws Exception;
    
    List<VendorType> findAll();
    
    VendorType getVendorType(String verificationToken, Integer companyId) throws Exception;

    void deleteVendorType(long vendor_Typeid, Integer companyId);

    VendorType getVendorTypeByID(long vendor_Typeid);
    
    List<VendorType> findAllByCompanyId(Integer companyId, Boolean isCommonMaster)throws Exception;

    long count();

    VendorType	getTopVendor(Integer companyId) throws Exception;
    
    long countByCompanyId(Integer companyId) throws Exception;

	public ValueObject getVendorTypeByName(ValueObject valueInObject)throws Exception;
    
}

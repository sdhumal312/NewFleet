package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.VendorTypeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VendorType;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VendorTypeService implements IVendorTypeService {
    @Autowired
    private VendorTypeRepository VendorTyperepository;
    
    @PersistenceContext
	EntityManager entityManager;


    // API

    @Transactional
    public VendorType registerNewVendorType(final VendorType accountDto) throws Exception {
        
        return VendorTyperepository.save(accountDto);
    }

    @Transactional
	public void updateVendorType(String vendor_TypeName, long vendor_Typeid) throws Exception {
     
        VendorTyperepository.updateVendorType(vendor_TypeName, vendor_Typeid);
    }


    @Transactional
	public List<VendorType> findAll() {
    	
		return VendorTyperepository.findAll();
	}
    
    @Transactional
	public VendorType getVendorType(String verificationToken, Integer companyId) {
		return VendorTyperepository.getVendorType(verificationToken, companyId);
	}


	@Transactional
	public void deleteVendorType(long vendor_Typeid, Integer companyId) {
		VendorTyperepository.deleteVendorType(vendor_Typeid, companyId);
	}



	@Transactional
	public VendorType getVendorTypeByID(long vendor_Typeid) {
		return VendorTyperepository.getVendorTypeByID(vendor_Typeid);
	}

	@Transactional
	public long count() {
		
		return VendorTyperepository.count();
	}

	@Override
	public List<VendorType> findAllByCompanyId(Integer companyId, Boolean isCommonMaster) throws Exception {
		try {
			return VendorTyperepository.findAllByCompanyId(companyId, isCommonMaster);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public VendorType getTopVendor(Integer companyId) throws Exception {
		
		return VendorTyperepository.getTopVendor(companyId);
	}
	
	@Override
	public long countByCompanyId(Integer companyId) throws Exception {
		
		return VendorTyperepository.countByCompanyId(companyId);
	}
	
	 @Transactional
		public ValueObject getVendorTypeByName(ValueObject valueInObject) throws Exception {
		 CustomUserDetails 	userDetails = null;	
		List<VendorType>			vendorTypeList	= null;
		VendorType					vendorType	= null;
		TypedQuery<Object[]> 		query							= null;
		List<Object[]> 				results							= null;	
		ValueObject					valueOutObject					= null;
		 try {
			 valueOutObject = new ValueObject ();
			 userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 if(valueInObject.getString("purchaseType",null) != null && !valueInObject.getString("purchaseType").trim().equalsIgnoreCase("") && valueInObject.getString("purchaseType").indexOf('\'') != 0 ) {
			 query = entityManager.createQuery(
						"SELECT VT.vendor_Typeid, VT.vendor_TypeName FROM VendorType AS VT "
						+ " WHERE lower(VT.vendor_TypeName) Like ('%"+valueInObject.getString("purchaseType")+"%') AND (VT.companyId = "+userDetails.getCompany_id()+" OR VT.isCommonMaster = 1) AND VT.markForDelete = 0", Object[].class);
				
			 results = query.getResultList();
				
				if (results != null && !results.isEmpty()) {
					vendorTypeList = new ArrayList<VendorType>();
					
					for (Object[] result : results) {
						vendorType = new VendorType();
						vendorType.setVendor_Typeid((Long) result[0]);
						vendorType.setVendor_TypeName((String) result[1]);
						vendorTypeList.add(vendorType);
					}
				}

			 valueOutObject.put("vendorTypeList", vendorTypeList);
			 }
		 		return valueOutObject;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		 	
		 	
			
		}


}
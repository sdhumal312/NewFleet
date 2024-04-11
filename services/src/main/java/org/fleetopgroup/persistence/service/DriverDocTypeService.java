package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.DriverDocTypeRepository;
import org.fleetopgroup.persistence.model.DriverDocType;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DriverDocTypeService implements IDriverDocTypeService {
    @Autowired
    private DriverDocTypeRepository driverDocTyperepository;


    // API

    @Override
    public DriverDocType registerNewDriverDocType(final DriverDocType accountDto) throws Exception {
        
      
        return driverDocTyperepository.save(accountDto);
    }

    @Override
	public void updateDriverDocType(String dri_DocType, Long modifiedBy, Timestamp modifiedOn, long dri_id, Integer companyId) throws Exception {
     
        driverDocTyperepository.updateDriverDocType(dri_DocType, modifiedBy, modifiedOn, dri_id, companyId);
    }


    @Override
	public List<DriverDocType> findAll() {
    	
		return driverDocTyperepository.findAll();
	}
    
	@Override
	public DriverDocType getDriverDocType(String verificationToken) {
		
		return null;
	}


	@Override
	public void deleteDriverDocType(long dri_id, Integer companyId) {
		
		driverDocTyperepository.deleteDriverDocType(dri_id, companyId);
	}



	@Override
	public DriverDocType getDriverDocTypeByID(long dri_id, Integer companyid) {
		
		return driverDocTyperepository.getDriverDocTypeByID(dri_id, companyid);
	}

	@Override
	public long count() {
		
		return driverDocTyperepository.count();
	}

	@Override
	public DriverDocType validateDriDocType(String dri_DocType, Integer company_Id) {
		
		return driverDocTyperepository.validateDriDocType(dri_DocType, company_Id);
	}

	@Override
	public List<DriverDocType> findAllByCompanyId(Integer company_Id) throws Exception {
		
		return driverDocTyperepository.findAllByCompanyId(company_Id);
	}
	
	@Override
	public DriverDocType findByDocType(Integer company_Id) throws Exception {
		
		return driverDocTyperepository.findByDocType(company_Id);
	}


	
   /*private Sort sortByIDAsc() {
	   
	   return new Sort(Sort.Direction.ASC, "id");
   }
   */

}
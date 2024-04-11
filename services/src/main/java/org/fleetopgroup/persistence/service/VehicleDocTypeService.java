package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleDocTypeRepository;
import org.fleetopgroup.persistence.model.VehicleDocType;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehicleDocTypeService implements IVehicleDocTypeService {
    @Autowired
    private VehicleDocTypeRepository vehicleDocTyperepository;


    // API

    @Override
    public VehicleDocType registerNewVehicleDocType(final VehicleDocType accountDto) throws Exception {
        
        return vehicleDocTyperepository.save(accountDto);
    }

    @Override
	public void updateVehicleDocType(String vDocType, Long modifiedById, Timestamp modifiedOn,  long dtid, Integer companyId) throws Exception {
     
        vehicleDocTyperepository.updateVehicleDocType(vDocType, modifiedById, modifiedOn, dtid, companyId);
    }


    @Override
	public List<VehicleDocType> findAll() {
    	
		return vehicleDocTyperepository.findAll();
	}
    
	@Override
	public VehicleDocType getVehicleDocType(String verificationToken) {
		return null;
	}


	@Override
	public void saveRegisteredVehicleDocType(VehicleDocType VehicleDocType) {
		
	}


	@Override
	public void deleteVehicleDocType(long dtid, Integer companyId) {
		
		vehicleDocTyperepository.deleteVehicleDocType(dtid, companyId);
	}



	@Override
	public VehicleDocType getVehicleDocTypeByID(long dtid, Integer companyId) {
		
		return vehicleDocTyperepository.getVehicleDocTypeByID(dtid, companyId);
	}

	@Override
	public long count() {
		
		return vehicleDocTyperepository.count();
	}

	@Override
	public VehicleDocType findByVDocType(String vDocType) {
		
		return vehicleDocTyperepository.findByVDocType(vDocType);
	}

	@Override
	public List<VehicleDocType> findAllVehicleDocTypeByCompanyId(Integer company_Id) throws Exception {
		try {
			return vehicleDocTyperepository.findAllVehicleDocTypeByCompanyId(company_Id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public long getVehicleDocTypeCountByCompanyId(Integer company_Id) {
		return vehicleDocTyperepository.getVehicleDocTypeCountByCompanyId(company_Id);
	}

	@Override
	public List<VehicleDocType> findByVDocTypeAndCompanyId(Integer company_Id, String vDocType) throws Exception{
		try {
			return vehicleDocTyperepository.findByVDocTypeAndCompanyId(company_Id, vDocType);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	
   /*private Sort sortByIDAsc() {
	   
	   return new Sort(Sort.Direction.ASC, "id");
   }
   */

}
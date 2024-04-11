package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehiclePurchaseInfoTypeRepository;
import org.fleetopgroup.persistence.dto.VehiclePurchaseInfoTypeDto;
import org.fleetopgroup.persistence.model.VehiclePurchaseInfoType;
import org.fleetopgroup.persistence.serviceImpl.IVehiclePurchaseInfoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehiclePurchaseInfoTypeService implements IVehiclePurchaseInfoTypeService {
    @Autowired
    private VehiclePurchaseInfoTypeRepository vehiclePurchaseInfoTyperepository;


    // API

    @Override
    public VehiclePurchaseInfoType registerNewVehiclePurchaseInfoType(final VehiclePurchaseInfoTypeDto accountDto ,Integer company_Id, Long createdById, Timestamp createdOn) throws Exception {
        
        final VehiclePurchaseInfoType VehiclePurchaseInfoType = new VehiclePurchaseInfoType();

        VehiclePurchaseInfoType.setvPurchaseInfoType(accountDto.getvPurchaseInfoType());
        VehiclePurchaseInfoType.setCompany_Id(company_Id);
        VehiclePurchaseInfoType.setCreatedById(createdById);
        VehiclePurchaseInfoType.setCreatedOn(createdOn);
       
        return vehiclePurchaseInfoTyperepository.save(VehiclePurchaseInfoType);
    }

    @Override
	public void updateVehiclePurchaseInfoType(String vPurchaseInfoType, Long modifiedById, Timestamp modifiedOn, long ptid, Integer company_Id) throws Exception {
     
        vehiclePurchaseInfoTyperepository.updateVehiclePurchaseInfoType(vPurchaseInfoType,modifiedById,modifiedOn, ptid, company_Id);
    }


    @Override
	public List<VehiclePurchaseInfoType> findAll() {
    	
		return vehiclePurchaseInfoTyperepository.findAll();
	}
    
	@Override
	public VehiclePurchaseInfoType findByVPurchaseInfoType(String vPurchaseInfoType, Integer company_Id){
		
		return vehiclePurchaseInfoTyperepository.findByVPurchaseInfoType(vPurchaseInfoType, company_Id);
	}


	@Override
	public void deleteVehiclePurchaseInfoType(long ptid, Integer companyId) {
		
		vehiclePurchaseInfoTyperepository.deleteVehiclePurchaseInfoType(ptid, companyId);
	}



	@Override
	public VehiclePurchaseInfoType getVehiclePurchaseInfoTypeByID(long ptid, Integer companyId) {
		
		return vehiclePurchaseInfoTyperepository.getVehiclePurchaseInfoTypeByID(ptid, companyId);
	}

	@Override
	public long count() {
		
		return vehiclePurchaseInfoTyperepository.count();
	}

	@Override
	public List<VehiclePurchaseInfoType> findAllByCompanyId(Integer company_Id) throws Exception {
		try {
			return vehiclePurchaseInfoTyperepository.findAllByCompanyId(company_Id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public long getCountByCompanyId(Integer company_Id) throws Exception {
		try {
			return vehiclePurchaseInfoTyperepository.getCountByCompanyId(company_Id);
		} catch (Exception e) {
			throw e;
		}
	}


}
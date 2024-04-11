package org.fleetopgroup.persistence.service;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehiclePurchaseInfoTypeHistoryRepository;
import org.fleetopgroup.persistence.model.VehiclePurchaseInfoType;
import org.fleetopgroup.persistence.model.VehiclePurchaseInfoTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehiclePurchaseInfoTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehiclePurchaseInfoTypeHistoryService implements IVehiclePurchaseInfoTypeHistoryService {
    @Autowired
    private VehiclePurchaseInfoTypeHistoryRepository vehiclePurchaseInfoTypeHistoryRepository;


    // API

    @Override
    public void registerNewVehiclePurchaseInfoTypeHIstory(VehiclePurchaseInfoType purchaseInfoType) throws Exception {
        
    	VehiclePurchaseInfoTypeHistory		vehiclePurchaseInfoTypeHistory	= new VehiclePurchaseInfoTypeHistory();
    	
    	vehiclePurchaseInfoTypeHistory.setPtid(purchaseInfoType.getPtid());
    	vehiclePurchaseInfoTypeHistory.setvPurchaseInfoType(purchaseInfoType.getvPurchaseInfoType());
    	vehiclePurchaseInfoTypeHistory.setCompany_Id(purchaseInfoType.getCompany_Id());
    	vehiclePurchaseInfoTypeHistory.setLastModifiedById(purchaseInfoType.getLastModifiedById());
    	vehiclePurchaseInfoTypeHistory.setLastModifiedOn(purchaseInfoType.getLastModifiedOn());
    	vehiclePurchaseInfoTypeHistory.setMarkForDelete(purchaseInfoType.isMarkForDelete());
		
    	vehiclePurchaseInfoTypeHistoryRepository.save(vehiclePurchaseInfoTypeHistory);
    }
}
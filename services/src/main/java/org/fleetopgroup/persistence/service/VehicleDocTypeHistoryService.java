package org.fleetopgroup.persistence.service;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleDocTypeHistoryRepository;
import org.fleetopgroup.persistence.model.VehicleDocTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehicleDocTypeHistoryService implements IVehicleDocTypeHistoryService {
    @Autowired
    private VehicleDocTypeHistoryRepository vehicleDocTypeHistoryRepository;


    // API

    @Override
    public void registerNewVehicleDocTypeHistory(VehicleDocTypeHistory docTypeHistory) throws Exception {
        vehicleDocTypeHistoryRepository.save(docTypeHistory);
    }
}
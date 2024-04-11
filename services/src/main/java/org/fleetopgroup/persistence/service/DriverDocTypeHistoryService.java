package org.fleetopgroup.persistence.service;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.DriverDocTypeHistoryRepository;
import org.fleetopgroup.persistence.model.DriverDocTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocTypeHIstoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DriverDocTypeHistoryService implements IDriverDocTypeHIstoryService {
    @Autowired
    private DriverDocTypeHistoryRepository driverDocTypeHistoryRepository;

    @Override
    public void registerNewDriverDocTypeHistory(DriverDocTypeHistory driverDocTypeHistory){
    	driverDocTypeHistoryRepository.save(driverDocTypeHistory);
    }
}
package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.VendorTypeHistoryRepository;
import org.fleetopgroup.persistence.model.VendorTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VendorTypeHistoryService implements IVendorTypeHistoryService {
    @Autowired
    private VendorTypeHistoryRepository vendorTypeHistoryRepository;

    @Transactional
    public void registerNewVendorTypeHistory(VendorTypeHistory vendorTypeHistory) throws Exception {
    	vendorTypeHistoryRepository.save(vendorTypeHistory);
    }
}
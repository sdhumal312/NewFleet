package org.fleetopgroup.persistence.service;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehiclePhoTypeHistoryRepository;
import org.fleetopgroup.persistence.model.VehiclePhoTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehiclePhoTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehiclePhoTypeHistoryService implements IVehiclePhoTypeHistoryService {
	@Autowired
	private VehiclePhoTypeHistoryRepository vehiclePhoTypeHistoryRepository;

	// API

	@Transactional
	public void registerNewVehiclePhoTypeHistory(VehiclePhoTypeHistory vehiclePhoTypeHistory) throws Exception {

		vehiclePhoTypeHistoryRepository.save(vehiclePhoTypeHistory);
	}
}
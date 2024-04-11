package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleGroupHistoryRepository;
import org.fleetopgroup.persistence.model.VehicleGroupHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehicleGroupHistoryService implements IVehicleGroupHistoryService {
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	private VehicleGroupHistoryRepository vehicleGroupHistoryRepository;

	@Transactional
	public void registerNewVehicleGroupHistyory(VehicleGroupHistory vehicleGroupHistory) throws Exception {

		vehicleGroupHistoryRepository.save(vehicleGroupHistory);
	}
}
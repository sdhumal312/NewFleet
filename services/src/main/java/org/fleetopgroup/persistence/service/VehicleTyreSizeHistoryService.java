/**
 * 
 */
package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleTyreSizeHistoryRepository;
import org.fleetopgroup.persistence.model.VehicleTyreSizeHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreSizeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("VehicleTyreSizeHistoryService")
@Transactional
public class VehicleTyreSizeHistoryService implements IVehicleTyreSizeHistoryService {

	@Autowired
	private VehicleTyreSizeHistoryRepository vehicleTyreSizeHistoryRepository;

	@PersistenceContext
	EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreSize#
	 * registerNewVehicleType(org.fleetop.persistence.model.VehicleTyreSize)
	 */
	@Transactional
	public void registerNewVehicleTyreSizeHistory(VehicleTyreSizeHistory vehicleTyreSizeHistory) throws Exception {
		vehicleTyreSizeHistoryRepository.save(vehicleTyreSizeHistory);
	}
}

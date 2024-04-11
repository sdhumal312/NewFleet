/**
 * 
 */
package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleTyreModelTypeHistoryRepository;
import org.fleetopgroup.persistence.model.VehicleTyreModelTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("VehicleTyreModelTypeHistoryService")
@Transactional
public class VehicleTyreModelTypeHistoryService implements IVehicleTyreModelTypeHistoryService {

	@Autowired
	private VehicleTyreModelTypeHistoryRepository vehicleTyreModelTypeHistoryRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void registerNew_VehicleTyreModelTypeHistory(VehicleTyreModelTypeHistory tyreModelTypeHistory) throws Exception {

		vehicleTyreModelTypeHistoryRepository.save(tyreModelTypeHistory);
	}
}
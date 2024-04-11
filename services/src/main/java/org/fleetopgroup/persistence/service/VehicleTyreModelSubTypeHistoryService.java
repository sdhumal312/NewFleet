/**
 * 
 */
package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleTyreModelSubTypeHistoryRepository;
import org.fleetopgroup.persistence.model.VehicleTyreModelSubTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelSubTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("VehicleTyreModelSubTypeHistoryService")
@Transactional
public class VehicleTyreModelSubTypeHistoryService implements IVehicleTyreModelSubTypeHistoryService {

	@Autowired
	private VehicleTyreModelSubTypeHistoryRepository vehicleTyreModelSubTypeHistoryRepository;

	@PersistenceContext
	EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreModelSubTypeService#
	 * registerNew_VehicleTyreModelType(org.fleetop.persistence.model.
	 * VehicleTyreModelSubType)
	 */
	@Transactional
	public void registerNew_VehicleTyreModelTypeHistory(VehicleTyreModelSubTypeHistory tyreModelSubTypeHistory) throws Exception {
		vehicleTyreModelSubTypeHistoryRepository.save(tyreModelSubTypeHistory);
	}
}

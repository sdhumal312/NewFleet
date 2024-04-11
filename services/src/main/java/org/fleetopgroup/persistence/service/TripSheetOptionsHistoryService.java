/**
 * 
 */
package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.TripSheetOptionsHistoryRepository;
import org.fleetopgroup.persistence.model.TripSheetOptionsHistory;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetOptionsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("TripSheetOptionsHistoryService")
@Transactional
public class TripSheetOptionsHistoryService implements ITripSheetOptionsHistoryService {

	@Autowired
	private TripSheetOptionsHistoryRepository tripSheetOptionsHistoryRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void registerNewTripSheetOptionsHistory(TripSheetOptionsHistory tripSheetOptionsHistory) throws Exception {
		tripSheetOptionsHistoryRepository.save(tripSheetOptionsHistory);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreSize#
	 * registerNewVehicleType(org.fleetop.persistence.model.VehicleTyreSize)
	 */
	
}

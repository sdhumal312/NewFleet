package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.TripRouteHistoryRepository;
import org.fleetopgroup.persistence.model.TripRouteHistory;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("TripRouteHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripRouteHistoryService implements ITripRouteHistoryService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TripRouteHistoryRepository tripRouteHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTripRouteHistory(TripRouteHistory tripRouteHistory) throws Exception {
		tripRouteHistoryRepository.save(tripRouteHistory);
	}	
}
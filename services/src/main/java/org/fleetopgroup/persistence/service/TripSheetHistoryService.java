package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.TripSheetHistoryRepository;
import org.fleetopgroup.persistence.model.TripSheetHistory;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripSheetHistoryService implements ITripSheetHistoryService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TripSheetHistoryRepository tripSheetHistoryRepository;
	
	@Override
	public void addTripSheetHistory(TripSheetHistory tripSheetHistory) throws Exception {
		tripSheetHistoryRepository.save(tripSheetHistory);
	}
}
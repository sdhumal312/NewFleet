package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.TripExpenseHistoryRepository;
import org.fleetopgroup.persistence.model.TripExpenseHistory;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("TripExpenseHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripExpenseHistoryService implements ITripExpenseHistoryService {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private TripExpenseHistoryRepository tripExpenseHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTripExpenseHistory(TripExpenseHistory tripExpenseHistory) throws Exception {
		tripExpenseHistoryRepository.save(tripExpenseHistory);
	}
}
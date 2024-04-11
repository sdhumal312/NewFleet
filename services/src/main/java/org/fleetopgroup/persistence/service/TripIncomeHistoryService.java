package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.TripIncomeHistoryRepository;
import org.fleetopgroup.persistence.model.TripIncomeHistory;
import org.fleetopgroup.persistence.serviceImpl.ITripIncomeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("TripIncomeHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripIncomeHistoryService implements ITripIncomeHistoryService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TripIncomeHistoryRepository tripIncomeHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTripIncomeHistory(TripIncomeHistory tripIncomeHistory) throws Exception {
		tripIncomeHistoryRepository.save(tripIncomeHistory);
	}
}
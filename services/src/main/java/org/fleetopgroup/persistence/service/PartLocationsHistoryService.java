package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.PartLocationsHistoryRepository;
import org.fleetopgroup.persistence.model.PartLocationsHistory;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("PartLocationsHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PartLocationsHistoryService implements IPartLocationsHistoryService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private PartLocationsHistoryRepository partLocationsHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPartLocationsHistory(PartLocationsHistory partLocationsHistory) throws Exception {
		partLocationsHistoryRepository.save(partLocationsHistory);
	}
}
package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.RenewalSubTypeHistoryRepository;
import org.fleetopgroup.persistence.model.RenewalSubTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RenewalSubTypeHistoryService implements IRenewalSubTypeHistoryService {
	@PersistenceContext
	EntityManager entityManager;

	
	@Autowired
	private RenewalSubTypeHistoryRepository renewalSubTypeHistoryRepository;

	@Transactional
	public void registerNewRenewalSubTypeHistory(RenewalSubTypeHistory renewalSubTypeHistory) throws Exception {
		renewalSubTypeHistoryRepository.save(renewalSubTypeHistory);
	}
}
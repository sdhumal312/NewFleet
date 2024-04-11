package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.JobTypeHistoryRepository;
import org.fleetopgroup.persistence.dao.ReasonTypeHistoryRepository;
import org.fleetopgroup.persistence.model.JobTypeHistory;
import org.fleetopgroup.persistence.model.ReasonTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IReasonTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("ReasonTypeHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReasonTypeHistoryService implements IReasonTypeHistoryService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private ReasonTypeHistoryRepository reasonTypeHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addReasonTypeHistory(ReasonTypeHistory reasonTypeHistory) throws Exception {
		reasonTypeHistoryRepository.save(reasonTypeHistory);
	}
	
}

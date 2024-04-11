package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.JobSubTypeHistoryRepository;
import org.fleetopgroup.persistence.model.JobSubTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("JobSubTypeHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class JobSubTypeHistoryService implements IJobSubTypeHistoryService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private JobSubTypeHistoryRepository jobSubTypeHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addJobSubTypeHistory(JobSubTypeHistory jobSubTypeHistory) throws Exception {
		jobSubTypeHistoryRepository.save(jobSubTypeHistory);
	}
}
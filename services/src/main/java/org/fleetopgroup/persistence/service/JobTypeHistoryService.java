package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.JobTypeHistoryRepository;
import org.fleetopgroup.persistence.model.JobTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("JobTypeHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class JobTypeHistoryService implements IJobTypeHistoryService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private JobTypeHistoryRepository jobTypeHistoryRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addJobTypeHistory(JobTypeHistory jobTypeHistory) throws Exception {
		jobTypeHistoryRepository.save(jobTypeHistory);
	}
}
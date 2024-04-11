package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.BranchHistoryRepository;
import org.fleetopgroup.persistence.model.BranchHistory;
import org.fleetopgroup.persistence.serviceImpl.IBranchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BranchHistoryService implements IBranchHistoryService {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private BranchHistoryRepository branchHistoryRepository;

	@Transactional
	public void registerNewBranchHistory(BranchHistory branchHistory) throws Exception {
		branchHistoryRepository.save(branchHistory);
	}
}
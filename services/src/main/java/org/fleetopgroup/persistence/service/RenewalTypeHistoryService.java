package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.RenewalTypeHistoryRepository;
import org.fleetopgroup.persistence.model.RenewalTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RenewalTypeHistoryService implements IRenewalTypeHistoryService {
	@Autowired
	private RenewalTypeHistoryRepository renewalTypeHistoryRepository;

	@Transactional
	public void registerNewRenewalTypeHistory(RenewalTypeHistory renewalTypeHistory) throws Exception {

		renewalTypeHistoryRepository.save(renewalTypeHistory);
	}
}
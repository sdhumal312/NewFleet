/**
 * 
 */
package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.CashBookNameHistoryRepository;
import org.fleetopgroup.persistence.model.CashBookNameHistory;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ashish Tiwari
 */
@Service("CashBookNameHIstoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CashBookNameHIstoryService implements ICashBookNameHistoryService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private CashBookNameHistoryRepository cashBookNameHistoryRepository;
	
	@Transactional
	public void add_CashBookNameHistory(CashBookNameHistory cashBookNameHistory) throws Exception {

		cashBookNameHistoryRepository.save(cashBookNameHistory);
	}
}

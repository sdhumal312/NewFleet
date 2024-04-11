package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.DepartmentHistoryRepository;
import org.fleetopgroup.persistence.model.DepartmentHistory;
import org.fleetopgroup.persistence.serviceImpl.IDepartmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DepartmentHistoryService implements IDepartmentHistoryService {
	
	@PersistenceContext
	EntityManager entityManager;
	
    @Autowired
    private DepartmentHistoryRepository departmentHistoryRepository;

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#registerNewDepartment(org.fleetop.persistence.model.Department)
	 */
	@Transactional
	public void registerNewDepartmentHistory(DepartmentHistory departmentHistory) throws Exception {
		departmentHistoryRepository.save(departmentHistory);
	}
}
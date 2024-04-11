package org.fleetopgroup.persistence.service;

/**
 * @author fleetop
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.JobTypeRepository;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("IJobTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class JobTypeService implements IJobTypeService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private JobTypeRepository JobTypeDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addJobType(JobType DocType) throws Exception {
		JobTypeDao.save(DocType);
	}

	@Transactional
	public List<JobType> listJobType() throws Exception {
		return JobTypeDao.findAll();
	}

	@Transactional
	public JobType getJobType(int dtid, Integer companyId) throws Exception {
		return JobTypeDao.getJobType(dtid, companyId);
	}

	@Transactional
	public void deleteJobType(Integer DocType, Integer companyid) throws Exception {
		JobTypeDao.deleteJobType(DocType, companyid);
	}

	@Transactional
	public List<JobType> SearchOnlyJobType(String term, Integer companyId) throws Exception {
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<JobType> query = entityManager
				.createQuery("from JobType  where (companyId = "+companyId+" OR companyId = 0) AND markForDelete = 0 AND  lower(Job_Type) Like ('%" + term + "%')", JobType.class);
		return query.getResultList();
		}else {
			return null;	
		}
		// return JobTypeDao.SearchOnlyJobType(term);
	}

	@Transactional
	public JobType validateJobType(String Job_Type) throws Exception {

		return JobTypeDao.validateJobType(Job_Type);
	}

	@Override
	public List<JobType> listJobTypeByCompanyId(Integer companyId) throws Exception {
		try {
			return JobTypeDao.listJobTypeByCompanyId(companyId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public JobType validateJobType(String Job_Type, Integer companyId) throws Exception {
		try {
			return JobTypeDao.validateJobType(Job_Type, companyId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public void updateJobType(JobType DocType) throws Exception {
		try {
			 JobTypeDao.updateJobType(DocType.getJob_Type(), DocType.getLastModifiedById(), DocType.getLastModifiedOn(), DocType.getJob_id(), DocType.getCompanyId());
		} catch (Exception e) {
			throw e;
		}
	}

}
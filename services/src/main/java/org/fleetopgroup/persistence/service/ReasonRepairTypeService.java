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
import org.fleetopgroup.persistence.dao.ReasonTypeRepository;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.ReasonForRepairType;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IReasonRepairTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("IReasonRepairTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReasonRepairTypeService implements IReasonRepairTypeService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private ReasonTypeRepository ReasonTypeDao;

	@Override
	public ReasonForRepairType validateReasonRepairType(String Reason_Type, Integer companyId) throws Exception {
		try {
			return ReasonTypeDao.validateReasonRepairType(Reason_Type, companyId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addResonRepairType(ReasonForRepairType reasonType) throws Exception {
		ReasonTypeDao.save(reasonType);
	}
	
	@Override
	public List<ReasonForRepairType> listReasonTypeByCompanyId(Integer companyId) throws Exception {
		try {
			return ReasonTypeDao.listReasonTypeByCompanyId(companyId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public ReasonForRepairType getReasonType(int dtid, Integer companyId) throws Exception {
		return ReasonTypeDao.getReasonType(dtid, companyId);
	}
	
	@Override
	public ReasonForRepairType validateReasonType(String Reason_Type, Integer companyId) throws Exception {
		try {
			return ReasonTypeDao.validateReasonType(Reason_Type, companyId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateReasonType(ReasonForRepairType DocType) throws Exception {
		try {
		
			ReasonTypeDao.updateReasonType(DocType.getReason_Type() , DocType.getLastModifiedById(), DocType.getLastModifiedOn(), DocType.getReason_id() , DocType.getCompanyId());
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void deleteReasonType(Integer DocType, Integer companyid) throws Exception {
		System.err.println("DocType --"+DocType);
		ReasonTypeDao.deleteReasonType(DocType, companyid);
	}

	@Override
	public List<ReasonForRepairType> SearchOnlyReasonType(String term, Integer companyId) throws Exception {

		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<ReasonForRepairType> query = entityManager
				.createQuery("from ReasonForRepairType  where (companyId = "+companyId+" OR companyId = 0) AND markForDelete = 0 AND  lower(Reason_Type) Like ('%" + term + "%')", ReasonForRepairType.class);
		return query.getResultList();
		}else {
			return null;	
		}
	
	}

	
}

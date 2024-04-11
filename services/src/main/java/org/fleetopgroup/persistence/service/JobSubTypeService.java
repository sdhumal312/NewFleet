package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
/**
 * @author fleetop
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.JobSubTypeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("IJobSubTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class JobSubTypeService implements IJobSubTypeService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private JobSubTypeRepository JobTypeDao;

	private static final int PAGE_SIZE = 10;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addJobSubType(JobSubType DocType) throws Exception {
		JobTypeDao.save(DocType);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateJobSubType(JobSubType DocType) throws Exception {
		JobTypeDao.save(DocType);
	}

	@Transactional
	public List<JobSubType> listJobSubType() throws Exception {
		return JobTypeDao.findAll();
	}

	@Transactional
	public JobSubType getJobSubType(int dtid) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return JobTypeDao.getJobSubType(dtid, userDetails.getCompany_id());
	}
	
	@Override
	public JobSubTypeDto getJobSubType(int dtid, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT JST.Job_Subid, JT.Job_Type, JST.Job_TypeId, JST.Job_ROT, JST.Job_ROT_number, JST.Job_ROT_hour,"
				+ " JST.Job_ROT_amount, JST.Job_ROT_Service_Reminder"
				+ "  From JobSubType AS JST "
				+ " LEFT JOIN JobType JT ON JT.Job_id = JST.Job_TypeId"
				+ " WHERE JST.Job_Subid = "+dtid+" AND JST.companyId = "+companyId+" AND JST.markForDelete = 0");

		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		JobSubTypeDto select;
		if (vehicle != null) {
			select = new JobSubTypeDto();
			select.setJob_Subid((Integer) vehicle[0]);
			select.setJob_Type((String) vehicle[1]);
			select.setJob_TypeId((Integer) vehicle[2]);
			select.setJob_ROT((String) vehicle[3]);
			select.setJob_ROT_number((String) vehicle[4]);
			select.setJob_ROT_hour((String) vehicle[5]);
			select.setJob_ROT_amount((String) vehicle[6]);
			if((Boolean) vehicle[7] != null) {
			select.setROT_Service_Reminder((Boolean) vehicle[7]);
			}
			
		} else {
			return null;
		}

		return select;
	}

	@Transactional
	public void deleteJobSubType(Integer DocType, Integer companyId) throws Exception {
		JobTypeDao.deleteJobSubType(DocType, companyId);
	}

	@Transactional
	public List<JobSubTypeDto> SearchOnlyJobSubType(String term, Integer companyId) throws Exception {

		//return JobTypeDao.SearchOnlyJobSubType(term, companyId);

		TypedQuery<Object[]> query = entityManager.createQuery("SELECT J.Job_Subid, JT.Job_Type, J.Job_ROT, J.Job_ROT_number,"
				+ "J.Job_ROT_hour, J.Job_ROT_amount, J.Job_TypeId "
				+ " FROM JobSubType J "
				+ " LEFT JOIN JobType JT ON JT.Job_id = J.Job_TypeId"
				+ " where J.Job_Type = "+term+" AND J.companyId = "+companyId+" and J.markForDelete = 0 ORDER BY J.Job_Subid desc",Object[].class);
		
		List<Object[]> results = query.getResultList();

		List<JobSubTypeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<JobSubTypeDto>();
			JobSubTypeDto list = null;
			for (Object[] result : results) {
				list = new JobSubTypeDto();
				list.setJob_Subid((Integer) result[0]);
				list.setJob_Type((String) result[1]);
				list.setJob_ROT((String) result[2]);
				list.setJob_ROT_number((String) result[3]);
				list.setJob_ROT_hour((String) result[4]);
				list.setJob_ROT_amount((String) result[5]);
				list.setJob_TypeId((Integer) result[6]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	
	}

	@Override
	public List<JobSubTypeDto> SearchOnlyJobSubType(Integer term, Integer companyId) throws Exception {
		TypedQuery<Object[]> query = entityManager.createQuery("SELECT J.Job_Subid, JT.Job_Type, J.Job_ROT, J.Job_ROT_number,"
				+ "J.Job_ROT_hour, J.Job_ROT_amount, J.Job_TypeId, J.Job_ROT_Service_Reminder "
				+ " FROM JobSubType J "
				+ " LEFT JOIN JobType JT ON JT.Job_id = J.Job_TypeId"
				+ " where J.Job_TypeId = "+term+" AND (J.companyId = "+companyId+" OR J.companyId = 0) AND J.Job_ROT_Service_Reminder = 1 and J.markForDelete = 0 ORDER BY J.Job_Subid desc",Object[].class);
		
		List<Object[]> results = query.getResultList();

		List<JobSubTypeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<JobSubTypeDto>();
			JobSubTypeDto list = null;
			for (Object[] result : results) {
				list = new JobSubTypeDto();
				list.setJob_Subid((Integer) result[0]);
				list.setJob_Type((String) result[1]);
				list.setJob_ROT((String) result[2]);
				list.setJob_ROT_number((String) result[3]);
				list.setJob_ROT_hour((String) result[4]);
				list.setJob_ROT_amount((String) result[5]);
				list.setJob_TypeId((Integer) result[6]);
				list.setROT_Service_Reminder((Boolean) result[7]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	
	}
	
	@Transactional
	public JobSubType validateJobSubType(String Job_SubType) throws Exception {
		return JobTypeDao.validateJobSubType(Job_SubType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IJobSubTypeService#ValidateJobSubType
	 * (java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<JobSubType> ValidateJobSubType(String Type, String Rotnumber, Integer companyId) throws Exception {

		return JobTypeDao.ValidateJobSubType(Type, Rotnumber, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IJobSubTypeService#ValidateJobSubType
	 * (java.lang.String)
	 */
	@Transactional
	public List<JobSubType> ValidateJobRotnumber(String Rotnumber, Integer companyId) throws Exception {

		return JobTypeDao.ValidateJobRotnumber(Rotnumber, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IJobSubTypeService#
	 * SearchJobSubType_ROT(java.lang.String)
	 */
	@Transactional
	public List<JobSubType> SearchJobSubType_ROT(String term, Integer companyId) throws Exception {
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<JobSubType> query = entityManager.createQuery("from JobSubType where ( lower(Job_Type) Like ('%" + term
				+ "%') OR lower(Job_ROT) Like ('%" + term + "%') OR lower(Job_ROT_number) Like ('%" + term + "%')) AND companyId = "+companyId+" AND markForDelete = 0",
				JobSubType.class);
		return query.getResultList();
		}else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IJobSubTypeService#
	 * getJobSub_ID_Only_TypeName(int)
	 */
	@Transactional
	public JobSubTypeDto getJobSub_ID_Only_TypeName(int dtid, Integer companyId) throws Exception {

		Query query = entityManager
				.createQuery("SELECT DDT.Job_Subid, JT.Job_Type, DDT.Job_TypeId FROM JobSubType DDT "
						+ " LEFT JOIN JobType JT ON JT.Job_id = DDT.Job_TypeId"
						+ " where DDT.Job_Subid=:id AND DDT.companyId = "+companyId+" AND DDT.markForDelete = 0");

		query.setParameter("id", dtid);
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		JobSubTypeDto select = new JobSubTypeDto();
		if (vehicle != null) {

			select.setJob_Subid((Integer) vehicle[0]);
			select.setJob_Type((String) vehicle[1]);
			select.setJob_TypeId((Integer) vehicle[2]);
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IJobSubTypeService#JobSubType(java.
	 * lang.Integer)
	 */
	@Transactional
	public Page<JobSubType> getDeployment_Page_JobSubType(Integer pageNumber) throws Exception {
		CustomUserDetails currentUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return JobTypeDao.findAllByCompanyId(currentUser.getCompany_id(), request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IJobSubTypeService#listJobSubType(
	 * java.lang.Integer)
	 */
	@Transactional
	public List<JobSubType> listJobSubType(Integer pageNumber) throws Exception {
		CustomUserDetails currentUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TypedQuery<JobSubType> query = entityManager.createQuery("From JobSubType as J where J.companyId = "+currentUser.getCompany_id()+" and J.markForDelete = 0 ORDER BY J.Job_Subid desc",JobSubType.class);
		
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		return query.getResultList();
	}

	@Override
	public List<JobSubTypeDto> listJobSubType(Integer pageNumber, Integer companyId) throws Exception {
		TypedQuery<Object[]> query = entityManager.createQuery("SELECT J.Job_Subid, JT.Job_Type, J.Job_ROT, J.Job_ROT_number,"
				+ "J.Job_ROT_hour, J.Job_ROT_amount, J.Job_TypeId, J.companyId "
				+ " FROM JobSubType J "
				+ " INNER JOIN JobType JT ON JT.Job_id = J.Job_TypeId"
				+ " where (J.companyId = "+companyId+" OR J.companyId = 0) and J.markForDelete = 0 ORDER BY J.Job_Subid desc",Object[].class);
		
	//	query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
	//	query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<JobSubTypeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<JobSubTypeDto>();
			JobSubTypeDto list = null;
			for (Object[] result : results) {
				list = new JobSubTypeDto();
				list.setJob_Subid((Integer) result[0]);
				list.setJob_Type((String) result[1]);
				list.setJob_ROT((String) result[2]);
				list.setJob_ROT_number((String) result[3]);
				list.setJob_ROT_hour((String) result[4]);
				list.setJob_ROT_amount((String) result[5]);
				list.setJob_TypeId((Integer) result[6]);
				list.setCompanyId((Integer) result[7]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	
	@Override
	public JobSubType validateJobSubType(String Job_SubType, Integer companyId) throws Exception {
		try {
			return JobTypeDao.validateJobSubType(Job_SubType, companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<JobSubTypeDto> listJobSubTypeByCompanyId(Integer companyId) throws Exception {
		TypedQuery<Object[]> query = entityManager.createQuery("SELECT J.Job_Subid, JT.Job_Type, J.Job_ROT, J.Job_ROT_number,"
				+ "J.Job_ROT_hour, J.Job_ROT_amount, J.Job_TypeId "
				+ " FROM JobSubType J "
				+ " LEFT JOIN JobType JT ON JT.Job_id = J.Job_TypeId"
				+ " where J.companyId = "+companyId+" and J.markForDelete = 0 ORDER BY J.Job_Subid desc",Object[].class);
		
		List<Object[]> results = query.getResultList();

		List<JobSubTypeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<JobSubTypeDto>();
			JobSubTypeDto list = null;
			for (Object[] result : results) {
				list = new JobSubTypeDto();
				list.setJob_Subid((Integer) result[0]);
				list.setJob_Type((String) result[1]);
				list.setJob_ROT((String) result[2]);
				list.setJob_ROT_number((String) result[3]);
				list.setJob_ROT_hour((String) result[4]);
				list.setJob_ROT_amount((String) result[5]);
				list.setJob_TypeId((Integer) result[6]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Transactional // Validating JobSubType (If It is Already Exists )
	public JobSubType getJobSubTypeByJobROTAndJobType(String job_Rot, String jobType, Integer companyId) throws Exception {

		return JobTypeDao.getJobSubTypeByJobROTAndJobType(job_Rot, jobType, companyId);
	}
	@Transactional
	public JobSubType getJobSubTypeForMob(int dtid,int companyId) throws Exception { 
		try{
			return JobTypeDao.getJobSubType(dtid, companyId);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override 
	public List<JobSubType> listJobSubTypeListByCompanyId(Integer companyId) throws Exception {
		
		return JobTypeDao.listJobSubTypeByCompanyId(companyId);
	}
	
	
}
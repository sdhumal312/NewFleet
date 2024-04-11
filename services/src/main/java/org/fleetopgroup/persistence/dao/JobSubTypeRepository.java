package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.JobSubType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JobSubTypeRepository extends JpaRepository<JobSubType, Integer> {

	/* Sub Type */
	@Modifying
	@Query("update JobSubType T SET T.Job_Type = ?1, T.Job_ROT = ?2 where T.Job_Subid = ?3")
	void updateJobSubType(String DocType, String Job_SubType, Integer Job_Subid) throws Exception;

	public List<JobSubType> findAll();

	@Query("FROM JobSubType DDT  where DDT.Job_Subid = ?1 AND (DDT.companyId = ?2 OR DDT.companyId = 0) AND DDT.markForDelete = 0")
	public JobSubType getJobSubType(int dtid, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE FROM JobSubType DDT SET DDT.markForDelete = 1 where DDT.Job_Subid = ?1 AND DDT.companyId = ?2")
	public void deleteJobSubType(Integer DocType, Integer companyId) throws Exception;

	@Query("FROM JobSubType DDT  where DDT.Job_Type = ?1 AND (DDT.companyId = ?2 OR DDT.companyId = 0) AND DDT.markForDelete = 0")
	public List<JobSubType> SearchOnlyJobSubType(String term, Integer companyId) throws Exception;
	
	@Query("FROM JobSubType DDT  where DDT.Job_Subid = ?1 AND (DDT.companyId = ?2 OR DDT.companyId = 0) AND DDT.markForDelete = 0")
	public List<JobSubType> SearchOnlyJobSubType(Integer term, Integer companyId) throws Exception;

	@Query("FROM JobSubType DDT  where DDT.Job_ROT = ?1 AND DDT.markForDelete = 0")
	public JobSubType validateJobSubType(String Job_SubType) throws Exception;
	
	@Query("FROM JobSubType DDT  where DDT.Job_ROT = ?1 AND Job_Type = ?2 AND (companyId = ?3 OR companyId = 0) AND markForDelete = 0")
	public List<JobSubType> ValidateJobSubType(String Type, String Rotnumber, Integer companyId) throws Exception;
	
	@Query(" from JobSubType where Job_ROT= ?1 AND (companyId = ?2 OR companyId = 0) AND markForDelete = 0")
	public List<JobSubType> ValidateJobRotnumber(String Rotnumber, Integer companyId) throws Exception;
	
	@Query("FROM JobSubType JST "
			+ " INNER JOIN JobType JT ON JT.Job_id = JST.Job_TypeId"
			+ " where (JST.companyId= ?1 OR JST.companyId = 0) and JST.markForDelete = 0")
	public Page<JobSubType> findAllByCompanyId(Integer companyId, Pageable pageable) throws Exception;
	
	@Query("FROM JobSubType DDT  where DDT.Job_ROT = ?1 and (DDT.companyId = ?2 OR DDT.companyId = 0) and DDT.markForDelete = 0")
	public JobSubType validateJobSubType(String Job_SubType, Integer companyId) throws Exception;
	
	@Query("FROM JobSubType DDT  where (DDT.companyId = ?1 OR DDT.companyId = 0) and DDT.markForDelete = 0")
	public List<JobSubType> listJobSubTypeByCompanyId(Integer companyId) throws Exception;
	
	// Validating JobSubType (If It is Already Exists )
	@Query("FROM JobSubType DDT  where DDT.Job_ROT = ?1 AND Job_Type = ?2 AND (companyId = ?3 OR companyId = 0) AND markForDelete = 0")
	public JobSubType getJobSubTypeByJobROTAndJobType(String job_Rot, String jobType, Integer companyId) throws Exception;
	
	
}
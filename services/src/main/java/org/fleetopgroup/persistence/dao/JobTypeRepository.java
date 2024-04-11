package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JobTypeRepository extends JpaRepository<JobType, Integer> {

	@Modifying
	@Query("update JobType T SET T.Job_Type = ?1, T.lastModifiedById = ?2 , T.lastModifiedOn = ?3 where T.Job_id = ?4 AND (T.companyId = ?5 OR T.companyId = 0) ")
	public void updateJobType(String DocType, Long modifiedById, Timestamp modifiedOn, Integer Job_id, Integer companyId) throws Exception;

	public List<JobType> findAll();

	@Query("FROM JobType DDT  where DDT.Job_id = ?1 AND (DDT.companyId = ?2 OR DDT.companyId = 0) AND DDT.markForDelete = 0")
	public JobType getJobType(int dtid, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE FROM JobType DDT SET DDT.markForDelete = 1 where DDT.Job_id = ?1  AND (DDT.companyId = ?2 OR DDT.companyId = 0)")
	public void deleteJobType(Integer DocType, Integer companyid) throws Exception;

	/*@Query("FROM JobType DDT  where DDT.Job_id = ?1")
	public List<JobType> SearchOnlyJobType(String term) throws Exception;*/

	@Query("FROM JobType DDT  where DDT.Job_Type = ?1")
	public JobType validateJobType(String Job_Type) throws Exception;
	
	@Query("FROM JobType DDT  where (DDT.companyId = ?1 OR DDT.companyId = 0) and DDT.markForDelete = 0")
	public List<JobType> listJobTypeByCompanyId(Integer companyId) throws Exception;
	
	@Query("FROM JobType DDT  where DDT.Job_Type = ?1 and (DDT.companyId = ?2 OR DDT.companyId = 0) and DDT.markForDelete = 0")
	public JobType validateJobType(String Job_Type, Integer companyId) throws Exception;
}
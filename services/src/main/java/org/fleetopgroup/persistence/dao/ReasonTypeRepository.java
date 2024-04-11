package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.ReasonForRepairType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReasonTypeRepository extends JpaRepository<ReasonForRepairType, Integer> {

	@Modifying
	@Query("update ReasonForRepairType T SET T.Reason_Type = ?1, T.lastModifiedById = ?2 , T.lastModifiedOn = ?3 where T.Reason_id = ?4 AND (T.companyId = ?5 OR T.companyId = 0) ")
	public void updateReasonType(String DocType, Long modifiedById, Timestamp modifiedOn, Integer Job_id, Integer companyId) throws Exception;

	@Query("FROM ReasonForRepairType DDT  where DDT.Reason_Type = ?1 and (DDT.companyId = ?2 OR DDT.companyId = 0) and DDT.markForDelete = 0")
	public ReasonForRepairType validateReasonRepairType(String Reason_Type, Integer companyId) throws Exception;
	
	@Query("FROM ReasonForRepairType DDT  where (DDT.companyId = ?1 OR DDT.companyId = 0) and DDT.markForDelete = 0")
	public List<ReasonForRepairType> listReasonTypeByCompanyId(Integer companyId) throws Exception;
	
	@Query("FROM ReasonForRepairType DDT  where DDT.Reason_id = ?1 AND (DDT.companyId = ?2 OR DDT.companyId = 0) AND DDT.markForDelete = 0")
	public ReasonForRepairType getReasonType(int dtid, Integer companyId) throws Exception;
	
	@Query("FROM ReasonForRepairType DDT  where DDT.Reason_Type = ?1 and (DDT.companyId = ?2 OR DDT.companyId = 0) and DDT.markForDelete = 0")
	public ReasonForRepairType validateReasonType(String Reason_Type, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE FROM ReasonForRepairType DDT SET DDT.markForDelete = 1 where DDT.Reason_id = ?1  AND (DDT.companyId = ?2 OR DDT.companyId = 0)")
	public void deleteReasonType(Integer DocType, Integer companyid) throws Exception;
	
}
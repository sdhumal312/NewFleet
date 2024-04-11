package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverTrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;



public interface DriverTrainingTypeRepository  extends JpaRepository<DriverTrainingType, Integer>{
	
	@Modifying
	@Query("update DriverTrainingType T SET T.dri_TrainingType = ?1, T.lastModifiedById = ?2, T.lastModifiedOn = ?3 where T.dri_id = ?4 and T.companyId = ?5")
	@Transactional
	void updateDriverTrainingType(String dri_TrainingType, Long modifiedBy, Timestamp modifiedOn, Integer dri_id, Integer companyId);
	
	public List<DriverTrainingType> findAll();

	@Query("FROM DriverTrainingType DDT  where DDT.dri_id = ?1 and DDT.companyId = ?2 AND DDT.markForDelete = 0")
	public DriverTrainingType getDriverTrainingType(int dtid, Integer companyid) throws Exception;

	@Modifying
	@Query("UPDATE FROM DriverTrainingType DDT set DDT.markForDelete = 1 where DDT.dri_id = ?1 and DDT.companyId = ?2")
	@Transactional
	public void deleteDriverTrainingType(Integer dri_id, Integer companyId) throws Exception;
	
	long count();
	
	@Query("FROM DriverTrainingType DDT where DDT.dri_TrainingType = ?1 and DDT.companyId = ?2 and DDT.markForDelete = 0")
	public DriverTrainingType ValidateDriverTrainingType(String dri_TrainingType, Integer companyId) throws Exception;
	
	@Query("FROM DriverTrainingType DDT where DDT.companyId = ?1 and DDT.markForDelete = 0")
	public List<DriverTrainingType> findAllByCompanyId(Integer companyId);

}
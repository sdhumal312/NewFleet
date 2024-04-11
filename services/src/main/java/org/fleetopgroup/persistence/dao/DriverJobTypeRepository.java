package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverJobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface DriverJobTypeRepository  extends JpaRepository<DriverJobType, Integer>{
	
	
	@Modifying
	@Query("update DriverJobType DDT set DDT.driJobType = ?1, DDT.driJobRemarks = ?2, DDT.lastModifiedById = ?3, DDT.lastModifiedOn = ?4 where DDT.driJobId = ?5 and DDT.companyId = ?6")
	public void updateDriverJobType(String driJobType, String Remark,Long modifiedBy, Timestamp modifeidOn, Integer JobType, Integer companyid) throws Exception;

	public List<DriverJobType> findAll();

	@Query("From DriverJobType DDT where DDT.driJobId = ?1 and DDT.companyId = ?2")
	public DriverJobType getDriverJobType(int dtid, Integer companyId) throws Exception;

	@Query("From DriverJobType DDT where DDT.driJobId = ?1 AND DDT.driJob_editable=?2 and DDT.companyId = ?3")
	public DriverJobType get_EditableDriverJobType(int dtid, Integer companyid) throws Exception;

	
	@Modifying
	@Query("Update  FROM DriverJobType J set J.markForDelete = 1 where J.driJobId = ?1 and J.companyId = ?2")
	public void deleteDriverJobType(Integer JobType, Integer companyId) throws Exception;
	
	@Query("From DriverJobType DDT where DDT.driJobType = ?1 and DDT.companyId = ?2 and DDT.markForDelete = 0")
	public DriverJobType validateDriverJobType(String driJobType, Integer  companyId) throws Exception;
	
	@Query("From DriverJobType DDT where DDT.companyId = ?1 and DDT.markForDelete = 0")
	public List<DriverJobType> findAllByCompanyId(Integer  companyId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM driverJobtype VT where VT.companyId = ?1 AND VT.markForDelete = 0 ORDER BY VT.driJobId ASC LIMIT 1")
	DriverJobType	getTopDriverJobType(Integer companyId) throws Exception;
	
}
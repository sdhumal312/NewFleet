/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.DriverBasicDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface DriverBasicDetailsRepository extends JpaRepository<DriverBasicDetails, Integer> {

	@Query("From DriverBasicDetails DBD where DBD.companyId = ?1 and DBD.markForDelete = 0 ")
	List<DriverBasicDetails> findBycompanyId(Integer companyId);

	@Query("From DriverBasicDetails DBD where DBD.detailsTypeId =?1 AND DBD.driverId = ?2 AND DBD.assignDate = ?3 AND DBD.companyId = ?4 AND DBD.markForDelete = 0 ")
	public DriverBasicDetails findByDetailTypeId(Long detailsTypeId,  Integer driverId, Timestamp assignDate, Integer companyId);
	
	@Query("From DriverBasicDetails DBD where DBD.driverBasicDetailsId = ?1 AND DBD.companyId = ?2 AND DBD.markForDelete = 0 ")
	public DriverBasicDetails findByDriverBasicDetailsId(Long driverBasicDetailsId , Integer companyId);
	
	@Modifying
	@Query("UPDATE DriverBasicDetails DBD SET DBD.markForDelete = 1  where DBD.driverBasicDetailsId = ?1 AND DBD.companyId = ?2 ")
	public void deleteDriverBasicDetails(Long driverBasicDetailsId,  Integer company_id);
	
	@Query("From DriverBasicDetails DBD where DBD.detailsTypeId =?1  AND DBD.companyId = ?2 AND DBD.markForDelete = 0 ")
	public List<DriverBasicDetails> getDetailListByTypeId(Long detailsTypeId, Integer companyId);
}

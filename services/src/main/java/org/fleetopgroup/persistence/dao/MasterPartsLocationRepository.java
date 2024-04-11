package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */


import org.fleetopgroup.persistence.model.MasterPartsLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MasterPartsLocationRepository extends JpaRepository<MasterPartsLocation, Long> {


	@Modifying
	@Query("UPDATE MasterPartsLocation SET markForDelete = 1 WHERE partid= ?1 AND companyId = ?2")
	public void deleteMasterPartsLocation(Long partid, Integer companyId) throws Exception;

	@Query("FROM MasterPartsLocation  WHERE partid= ?1 AND locationId= ?2 AND companyId = ?3 AND markForDelete = 0 ")
	public MasterPartsLocation validateMasterPartLocation(Long partid, Integer locationId, Integer companyId) throws Exception;

}